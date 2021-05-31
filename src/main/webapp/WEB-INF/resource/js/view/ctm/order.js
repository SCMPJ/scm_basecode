//주문 화면 페이징 처리
var pageSizeProduct = 6; //주문 화면 페이지 사이즈
var pageBlockSizeProduct = 5; //주문 화면 페이지 블록 갯수

//OnLoad event
$(document).ready(function() {
  //상품 목록 조회
  fProductList();
  //버튼 이벤트 등록
  fRegisterButtonClickEvent();
  //엔터눌렀을때 창고정보 검색되게하기
  $("#sname").keypress(function(e) {
    if (e.which == 13) {
      board_search(); // 실행할 이벤트
    }
  });
});

/*버튼 이벤트 등록*/
function fRegisterButtonClickEvent() {
  $('a[name=btn]').click(function(e) {
    e.preventDefault();
    var btnId = $(this).attr('id');
    switch (btnId) {
      case 'searchBtn':
        board_search(); // 검색하기
        break;
      case 'btnSaveProduct':
        fSaveProduct(); // save 안에 저장,수정  
        break;
      case 'btnDeleteProduct'://삭제하기
        fDeleteProduct();
        break;
      case 'btnCloseProduct':
        gfCloseModal(); // 모달닫기 
        break;
    }
  });
}

/*제품 목록 조회*/
function fProductList(currentPage) {
  currentPage = currentPage || 1;
  var sname = $('#sname');
  var searchKey = document.getElementById("searchKey");
  var oname = searchKey.options[searchKey.selectedIndex].value;

  var param = {
    sname : sname.val(),
    oname : oname,
    currentPage : currentPage,
    pageSize : pageSizeProduct
  }
  var resultCallback = function(data) {
    fproductListResult(data, currentPage);
  };
  callAjax("/ctm/productList.do", "post", "text", true, param, resultCallback);
}

/*제품 조회 콜백 함수*/
function fproductListResult(data, currentPage) {

  console.log("data : " + data);
  //기존 목록 삭제
  $("#productList").empty();
  $("#productList").append(data);
  // 총 개수 추출
  var totalProduct = $("#totalProduct").val();
  //페이지 네비게이션 생성
  var paginationHtml = getPaginationHtml(currentPage, totalProduct, pageSizeProduct, pageBlockSizeProduct, 'board_search');
  $("#productPagination").empty().append(paginationHtml);
  //현재 페이지 설정
  $("#currentPageProduct").val(currentPage);
}

/* 검색 기능*/
function board_search(currentPage) {
  currentPage = currentPage || 1;
  var sname = $('#sname');
  var searchKey = document.getElementById("searchKey");
  var oname = searchKey.options[searchKey.selectedIndex].value;

  var param = {
    sname : sname.val(),
    oname : oname,
    currentPage : currentPage,
    pageSize : pageSizeProduct
  }

  var resultCallback = function(data) {
    fproductListResult(data, currentPage);
  };
  callAjax("/ctm/productList.do", "post", "text", true, param, resultCallback);
}

// 구매하기 모달
var puchaseModal = new tingle.modal({
  footer : true,
  stickyFooter : false,
  closeMethods : [ 'button' ],
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

var param = {};

puchaseModal.addFooterBtn('구매', 'tingle-btn tingle-btn--primary', function() {
  var wantDate = $('input[name="want_receive_date"]').val();
  var request = $('textarea[name="request"]').val();
  
  console.log(wantDate, request);
  
  if (!wantDate) {
    swal("배송날짜를 입력해주세요.");
    return;
  }
  
  param['wantDate'] = wantDate;
  param['request'] = request || '';
  
  console.log(param);
  
  var resultCallback = function(data) {
    getInsertMessage(data);
  }

  callAjax("/ctm/purchaseItem.do", "post", "json", true, param, resultCallback);
  puchaseModal.close();
});

function getInsertMessage(data) {
  if (data.result === "SUCCESS") {
    swal(data.resultMsg).then(function() {
      location.href = '/ctm/orderList.do';
    });
    console.log("주문 완료");
    return 1;
  } else {
    swal(data.resultMsg).then(function() {
      window.location.reload();
    });
    console.log("주문 실패");
    return 0;
  }
}

puchaseModal.addFooterBtn('취소', 'tingle-btn tingle-btn--danger', function() {
  puchaseModal.close();
});

function purchaseItem(prod_cd) {
  console.log("purchaseItem!!! for ", prod_cd);
  var productCode = $('input[name='+prod_cd+']').val();
  var productCount = $('input[name='+prod_cd+'Count]').val();
  var productName = $('input[name='+prod_cd+'productName]').val();
  var middleCategory = $('input[name='+prod_cd+'middleCategory]').val();
  var productPrice = $('input[name='+prod_cd+'productPrice]').val();
  var totalPrice = productCount * productPrice;
  var totalTax = totalPrice * 0.1;
  var totalPriceWithTax = totalPrice + totalTax;
  // 현재 날짜 가져오기
  var now = new Date();
  var year = now.getFullYear();
  var month = now.getMonth()+1;
  month = month > 9 ? month : '0'+month;
  var date = now.getDate();
  console.log(year, month, date);
  var format_now = year+'-'+month+'-'+date;
  console.log(format_now);
  
  if (productCount === '0') {
    return swal("수량을 1 이상의 값으로 해주세요.");
  }
    
  puchaseModal.setContent('<table class="col">'
                          +'<caption>caption</caption>'
                          +'<thead>'
                          +'<tr>'
                          +'<th scope="col">제품명</th>'
                          +'<th scope="col">상호명</th>'
                          +'<th scope="col">제품금액(원)</th>'
                          +'<th scope="col">갯수</th>'
                          +'<th scope="col">총 단가금액(원)</th>'
                          +'<th scope="col">세금(원)</th>'
                          +'<th scope="col">총 결제금액(원)</th>'
                          +'<th scope="col">희망배송날짜</th>'
                          +'</tr>'
                          +'</thead>'
                          +'<tbody>'
                          +'<tr>'
                          +'<td scope="col">'+productName+'</td>'
                          +'<td scope="col">'+middleCategory+'</td>'
                          +'<td scope="col">'+productPrice+'</td>'
                          +'<td scope="col">'+productCount+'</td>'
                          +'<td scope="col">'+totalPrice+'</td>'
                          +'<td scope="col">'+totalTax+'</td>'
                          +'<td scope="col">'+totalPriceWithTax+'</td>'
                          +'<td scope="col"><input type="date" name="want_receive_date" min="'+format_now+'"/></td>'
                          +'</tr>'
                          +'</tbody>'
                          +'<thead>'
                          +'<tr>'
                          +'<th scope="col" colspan="8">요청사항</th>'
                          +'</tr>'
                          +'</thead>'
                          +'<tbody>'
                          +'<tr>'
                          +'<td scope="col" colspan="8"><textarea name="request" maxlength="1000" rows="10" cols="35"></textarea></td>'                          
                          +'</tr>'
                          +'</tbody>'                         
                          +'</table>'
                          +'<center><p style="font-size: 24px; margin-top: 20px;"><strong>구매 하시겠습니까?</strong></p></center>'
                          );
  
  param = {
      productCount: productCount,
      productName: productName,
      middleCategory: middleCategory,
      productPrice: productPrice,
      totalPrice: totalPrice,
      productCode: productCode,
      tax: totalTax
  };
  
  puchaseModal.open();
};


// 장바구니 모달
var cartModal = new tingle.modal({
  footer : true,
  stickyFooter : false,
  closeMethods : [ 'button' ],
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

function putToCartList(prod_cd) {
  console.log("putToCartList!! for ", prod_cd);
  var productCode = prod_cd;
  var productCount = $('input[name='+prod_cd+'Count]').val();
  
  if (productCount === '0') {
    return swal("수량을 1 이상의 값으로 해주세요.");
  }
  
  param = {
      productCode: productCode,
      productCount: productCount
  };
  
  cartModal.setContent('<center><h1>장바구니에 담으시겠습니까?</h2></center>');
  
  cartModal.open();
};

cartModal.addFooterBtn('담기', 'tingle-btn tingle-btn--primary', function() {
  var resultCallback = function(data) {
    getInsertCartMessage(data);
  }

  callAjax("/ctm/sendItemToCart.do", "post", "json", true, param, resultCallback);
  cartModal.close();
});

function getInsertCartMessage(data) {
  if (data.result === "SUCCESS") {
    swal(data.resultMsg).then(function() {
      window.location.reload();;
    });
    console.log("장바구니 담기 완료");
    return 1;
  } else {
    swal(data.resultMsg).then(function() {
      window.location.reload();
    });
    console.log("장바구니 담기 실패");
    return 0;
  }
}

cartModal.addFooterBtn('취소', 'tingle-btn tingle-btn--danger', function() {
  cartModal.close();
});