/*장바구니 페이징 설정*/

var pageSizeCart = 5;
var pageBlockSizeCart = 5;

/* 화면에 뿌려주는 총 금액 */
var totalAmount = 0;

/* 체크박스 선택된 ID 리스트 저장 */

var btnId;
var pdcdarr = Array();
var pdcd;
var checkCnt;

/** OnLoad event */

$(document).ready(function() {
  fListCart(); // 장바구니 목록 조회

});

/** 장바구니 조회 */
function fListCart(currentPage) {

  currentPage = currentPage || 1;

  console.log("currentPage : " + currentPage);

  var param = {
  currentPage : currentPage,
  pageSize : pageSizeCart
  }

  var resultCallback = function(data) {
    fCartResult(data, currentPage);
  };

  callAjax("/ctm/listCart.do", "post", "text", true, param, resultCallback);
}

/** 장바구니 목록 콜백 함수 */
function fCartResult(data, currentPage) {

  //alert(data);
  console.log(data);

  // 기존 목록 삭제
  $('#listCart').empty();

  // 신규 목록 생성
  $("#listCart").append(data);

  // 총 개수 추출
  var totalCntCart = $("#totalCntCart").val();

  // 페이지 네비게이션 생성
  var paginationHtml = getPaginationHtml(currentPage, totalCntCart, pageSizeCart, pageBlockSizeCart, 'fListCart');
  console.log("paginationHtml : " + paginationHtml);
  //alert(paginationHtml);
  $("#CartPagination").empty().append(paginationHtml);

  // 현재 페이지 설정
  $("#currentPageCart").val(currentPage);
  console.log("totalCntCart:: " + totalCntCart);

  /* 장바구니 수량 변경 */
  var inputList = $("input[name=qtyCount]");
  var inputCnt = $("input[name=qtyCount]").length;

  /* 장바구니 부분 선택 구매 */
  var checkedCount = $("input[name='selectCartItem']:checked").length;
  var checkArr = $("input[name=selectCartItem]");

  /* 수량변경, 체크박스 동기화 */
  for (i = 0; i < inputCnt; i++) {
    console.log(inputList[i]);
    inputList[i].addEventListener('input', updateValue);
    checkArr[i].addEventListener('input', checkValue);
  }

  /* 총 가격 초기화 */
  totalAmount = Math.floor($("#totalAmount").val());
}

function updateValue(e) {
  console.log("작동함");
  var qty = e.target.value;
  var prodId = e.target.id;
  console.log(prodId);
  
  var param = {
    shopping_cart_qty : qty,
    product_cd : prodId
  }

  var resultCallback = function(data) {
    fchangeQtyResult(data);
  };

  callAjax("/ctm/changeQty.do", "post", "json", true, param, resultCallback);
}

/* 체크박스 선택 제품 지정 */
function checkValue(e) {

  var checkedEachPrice = Math.round(e.target.value.split(' ')[0]);

  console.log("checkedEachPrice", checkedEachPrice);

  if (e.target.checked == true) {

    totalAmount += checkedEachPrice
    $("#totalPrice").empty().text(totalAmount.toLocaleString('ko-KR') + "원");

  } else {

    totalAmount -= checkedEachPrice
    $("#totalPrice").empty().text(totalAmount.toLocaleString('ko-KR') + "원");

  }

}

function fchangeQtyResult(data) {
  var currentPage = $("#currentPageCart").val();

  if (data.result == "SUCCESS") {

    // 장바구니 목록 조회
    fListCart(currentPage);
    location.href = "/ctm/cart.do";

  } else {
    swal(data.resultMsg);
  }
}

// 장바구니 삭제하기 클릭시 모달창
var deleteModal = new tingle.modal({
footer : true,
stickyFooter : false,
closeMethods : [ 'overlay', 'button', 'escape' ],
closeLabel : "Close",
cssClass : [ 'custom-class-1', 'custom-class-2' ],
onOpen : function() {
  console.log('modal open');
},
onClose : function() {
  console.log('modal closed');
},
beforeClose : function() {
  // here's goes some logic
  // e.g. save content before closing the modal
  return true; // close the modal
  return false; // nothing happens
}
});

// set content
deleteModal.setContent('<h1>정말 삭제하시겠습니까?</h1>');

// add a button
deleteModal.addFooterBtn('삭제', 'tingle-btn tingle-btn--primary', function() {

  var resultCallback = function(data) {
    fDeleteCartItemResult(data);
  };

  callAjax("/ctm/deleteCartItem.do", "post", "json", true, $("#myForm").serialize(), resultCallback);

});

function fDeleteCartItemResult(data) {

  var currentPage = $("#currentPageCart").val();

  if (data.result == "SUCCESS") {

    // 모달 닫기
    deleteModal.close();

    // 새로고침
    window.location.reload();

  } else {
    swal(data.resultMsg);
  }
}

// add another button
deleteModal.addFooterBtn('취소', 'tingle-btn tingle-btn--danger', function() {
  deleteModal.close();
});

function fDeleteModal() {
  console.log(deleteModal);
  deleteModal.open();
}

// 장바구니 주문하기 클릭시 모달창
var orderModal = new tingle.modal({
footer : true,
stickyFooter : false,
closeMethods : [ 'overlay', 'button', 'escape' ],
closeLabel : "Close",
cssClass : [ 'custom-class-1', 'custom-class-2' ],
onOpen : function() {
  console.log('modal open');
},
onClose : function() {
  console.log('modal closed');
},
beforeClose : function() {
  // here's goes some logic
  // e.g. save content before closing the modal
  return true; // close the modal
  return false; // nothing happens
}
});

// 현재 날짜 가져와서 희망날짜가 그 보다 아래로 내려가지 못하게 할 것
var now = new Date();
var year = now.getFullYear();
var month = now.getMonth()+1;
month = month > 9 ? month : '0'+month;
var date = now.getDate();
console.log(year, month, date);
var format_now = year+'-'+month+'-'+date;

// set content
orderModal.setContent('<table class="col">'
                      +'<caption>caption</caption>'
                      +'<thead>'
                      +'<tr>'
                      +'<th scope="col">희망배송날짜</th>'
                      +'<th scope="col" colspan="8">요청사항</th>'
                      +'</tr>'
                      +'</thead>'
                      +'<tbody>'
                      +'<tr>'
                      +'<td scope="col"><input type="date" name="want_receive_date" min="'+format_now+'"/></td>'
                      +'<td scope="col" colspan="8"><textarea name="request" maxlength="1000" rows="10" cols="35"></textarea></td>'   
                      +'</tr>'
                      +'</tbody>'                       
                      +'</table>'
                      +'<center><p style="font-size: 24px; margin-top: 20px;"><strong>구매 하시겠습니까?</strong></p></center>'
                      );


// 전처리한 serialize 값
var DIY_serialize = '';
// 장바구니 주문 버튼 후의 모달 버튼 안에 있는 주문 버튼
orderModal.addFooterBtn('주문', 'tingle-btn tingle-btn--primary', function() {

  var wantDate = $('input[name="want_receive_date"]').val();
  var request = $('textarea[name="request"]').val();
  
  console.log(wantDate, request);
  
  if (!wantDate) {
    swal("배송날짜를 입력해주세요.");
    return;
  }
  
  DIY_serialize += '&wantDate=' + wantDate;
  DIY_serialize += '&request=' + request || '';
  
  // 맨 앞의 필요없는 & 제거
  DIY_serialize = DIY_serialize.substring(1);
  console.log(DIY_serialize);
  
  var resultCallback = function(data) {
    fOrderCartItemResult(data);
  };

  callAjax("/ctm/orderCartItem.do", "post", "json", true, DIY_serialize, resultCallback);
  orderModal.close();
});

// add another button
orderModal.addFooterBtn('취소', 'tingle-btn tingle-btn--danger', function() {
  orderModal.close();
});

function fOrderModal() {
    var myForm = $("#myForm").serialize().split('&');
    var selectedItemList = myForm.filter(function(each) {
      return each.substring(0, 6) === 'select';
    });
    
    console.log(selectedItemList);

    for (i=0; i<selectedItemList.length; i++) {
      var productCode = selectedItemList[i].split('+')[1];
      var productQty = selectedItemList[i].split('+')[2];
      var codeQty = '&PRODUCT' + productCode + '=' + productQty;
      console.log(codeQty);
      DIY_serialize += codeQty;
    }
//  var chkbox = $('.checkSelect');
//
//  pdcdarr.splice(0, pdcdarr.length);
//
//  for (i = 0; i < chkbox.length; i++) {
//
//    if (chkbox[i].checked == true) {
//      btnId = chkbox[i].id;
//      pdcd = btnId.split('_', 2);
//      pdcdarr.push(pdcd[1]);
//    }
//  }
//
//  $("#pdcdArray").val(pdcdarr);
//
//  console.log("pdcd::" + $("#pdcdArray").val());
//
//  var checkValue = {};
//
//  $.each($('#myForm').serializeArray(), function() {
//    checkValue[this.name] = this.value;
//  });
//
//  for (i = 0; i < chkbox.length; i++)
//    if (chkbox[i].checked == true) {
//      console.log("checkValueProdnm  : " + i + checkValue.listprodnm);
//      console.log("checkValueQty  :" + checkValue.qtyCount);
//      console.log("checkValuePrice  : " + checkValue.listprice);
//    }
  orderModal.open();
};

/** 장바구니 주문 콜백 함수 */

function fOrderCartItemResult(data) {
  if (data.result == "SUCCESS") {
    console.log("주문 성공");
    deleteModal.close();
    swal(data.resultMsg).then(function(){
      location.href = '';
    });
  } else {
    swal(data.resultMsg).then(function(){
      location.href = '';
    });
  }
}