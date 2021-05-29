<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>주문이력 조회</title>
<jsp:include page="/WEB-INF/view/common/common_include.jsp"></jsp:include>
<script type="text/javascript">
  //주문이력 화면 페이징 처리
  var pageSizeProduct = 5; //주문이력 화면 페이지 사이즈
  var pageBlockSizeProduct = 5; //주문이력 화면 페이지 블록 갯수

  //OnLoad event
  $(document).ready(function() {
    //주문이력조회
    fOrderHisList();
    //버튼 이벤트 등록
    fRegisterButtonClickEvent();
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
      case 'btnSubmit':// 등록하기
        fSubmitRefund();
        break;
      case 'btnCloseModal':
        gfCloseModal(); // 모달닫기 
        break;
      }
    });
  }

  /*주문이력 조회*/
  function fOrderHisList(currentPage) {
    currentPage = currentPage || 1;
    var searchKey = document.getElementById("searchKey");

    var param = {
    currentPage : currentPage,
    pageSize : pageSizeProduct
    }
    var resultCallback = function(data) {
      forderHisListResult(data, currentPage);
    };
    callAjax("/ctm/orderHisList.do", "post", "text", true, param, resultCallback);
  }

  /*주문이력 조회 콜백 함수*/
  function forderHisListResult(data, currentPage) {

    console.log("data : " + data);
    //기존 목록 삭제
    $("#orderHisList").empty();
    $("#orderHisList").append(data);
    // 총 개수 추출
    var totalOrder = $("#totalOrder").val();
    //페이지 네비게이션 생성
    var paginationHtml = getPaginationHtml(currentPage, totalOrder, pageSizeProduct, pageBlockSizeProduct, 'fOrderHisList');
    $("#productPagination").empty().append(paginationHtml);
    //현재 페이지 설정
    $("#currentPageProduct").val(currentPage);
  }

  /* 검색 기능*/
  function board_search(currentPage) {
    currentPage = currentPage || 1;
    var searchKey = document.getElementById("searchKey");

    var param = {
    currentPage : currentPage,
    pageSize : pageSizeProduct
    }

    var resultCallback = function(data) {
      forderHisListResult(data, currentPage);
    };
    callAjax("/ctm/orderHisList.do", "post", "text", true, param, resultCallback);
  }

  /* 모달 실행 */
  function fPopModalRefund(order_cd) {
    $("#action").val("R");
    fSelectRefund(order_cd);
  }

  /* 단건 조회*/
  function fSelectRefund(order_cd) {
    var param = {
      order_cd : order_cd
    };
    var resultCallback = function(data) {
      fSelectRefundResult(data);
    };
    callAjax("/ctm/selectRefund.do", "post", "json", true, param, resultCallback);
  }

  // 콜백 함수
  function fSelectRefundResult(data) {
    if (data.result == "SUCCESS") {
      gfModalPop("#layerRefund")
      fInitFormRefund(data.refundInfoModel);
    } else {
      alert(data.resultMsg);
    }
  }

  function fInitFormRefund(object) {
    $("#order_cd").val(object.order_cd);
    $("#prod_nm").val(object.prod_nm);
    $("#product_cd").val(object.product_cd);
    $("#refund_amt").val(object.amount); //amount = refund_amt
    $("#addr").val(object.addr);
    $("#refund_cnt").val(object.order_cnt); //order_cnt = refund_cnt
    $("#refund_reason").val(object.refund_reason);
    $("#order_cd").attr("readonly", true);
    $("#prod_nm").attr("readonly", true);
    $("#product_cd").attr("readonly", true);
    $("#refund_amt").attr("readonly", true);
    $("#addr").attr("readonly", true);
    $("#refund_cnt").attr("readonly", true);

    $("#btnSubmit").show();

  }
</script>
</head>
<body>
  <form id="myForm" action="" method="">
    <input type="hidden" id="currentPageProduct" value="1"> <input type="hidden" id="currentPageProduct" value="1"> <input type="hidden" name="action" id="action" value="">
    <div id="mask"></div>
    <div id="wrap_area">
      <h2 class="hidden">header 영역</h2>
      <jsp:include page="/WEB-INF/view/common/header.jsp"></jsp:include>
      <h2 class="hidden">컨텐츠 영역</h2>
      <div id="container">
        <ul>
          <li class="lnb">
            <!-- lnb 영역 --> <jsp:include page="/WEB-INF/view/common/lnbMenu.jsp"></jsp:include> <!--// lnb 영역 -->
          </li>
          <li class="contents">
            <!-- contents -->
            <h3 class="hidden">contents 영역</h3> <!-- content -->
            <div class="content">
              <p class="Location">
                <a href="/system/notice.do" class="btn_set home">메인으로</a> <a class="btn_nav">주문</a> <span class="btn_nav bold">주문이력 조회</span> <a href="" class="btn_set refresh">새로고침</a>
              </p>
              <p class="conTitle">
                <span>주문이력 조회</span>
              </p>
              <div class="ProductList">
                <div class="conTitle row" style="float: left; width: 100%; display: flex; justify-content: flex-end;">
                  <!-- datepicker -->
                  <div class='col-md-3 col-xs-4'>
                    <div class="form-group">
                      <div class="input-group date" id="datetimepicker1" data-target-input="nearest">
                        <input type="text" class="form-control datetimepicker-input" data-target="#datetimepicker1" value="">
                        <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
                          <div class="input-group-text">
                            <i class="fa fa-calendar"></i>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <span class="divider" style="top: 35%; left: 68.8%;">~</span>
                  <div class='col-md-3 col-xs-4'>
                    <div class="form-group">
                      <div class="input-group date" id="datetimepicker3" data-target-input="nearest">
                        <input type="text" class="form-control datetimepicker-input" data-target="#datetimepicker3" value="">
                        <div class="input-group-append" data-target="#datetimepicker3" data-toggle="datetimepicker">
                          <div class="input-group-text">
                            <i class="fa fa-calendar"></i>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <!-- // datepicker -->
                  <a href="" class="btnType blue" id="searchBtn" name="btn"> <span>검 색</span>
                  </a>
                </div>
                <table class="col">
                  <caption>caption</caption>
                  <colgroup>
                    <col width="12%">
                    <col width="10%">
                    <col width="10%">
                    <col width="5%">
                    <col width="5%">
                    <col width="10%">
                    <col width="13%">
                    <col width="13%">
                    <col width="7%">
                    <col width="5%">
                    <col width="5%">
                    <col width="5%">
                  </colgroup>
                  <thead>
                    <tr>
                      <th scope="col">제품명</th>
                      <th scope="col">제품코드</th>
                      <th scope="col">품목명</th>
                      <th scope="col">주문번호</th>
                      <th scope="col">주문수량</th>
                      <th scope="col">결제금액</th>
                      <th scope="col">구매일자</th>
                      <th scope="col">배송희망날짜</th>
                      <th scope="col">배송상태</th>
                      <th scope="col">입금</th>
                      <th scope="col">반품</th>
                      <th scope="col">수취확인</th>
                    </tr>
                  </thead>
                  <tbody id="orderHisList"></tbody>
                </table>
              </div>
              <div class="paging_area" id="productPagination"></div>
          </li>
        </ul>
      </div>
    </div>
    <!-- 모달 -->
    <div id="layerRefund" class="layerPop layerType2" style="width: 600px;">
      <dl>
        <dt>
          <strong>반 품</strong>
        </dt>
        <dd class="content">
          <table class="row">
            <caption>caption</caption>
            <colgroup>
              <col width="120px">
              <col width="*">
              <col width="120px">
              <col width="*">
            </colgroup>
            <tbody>
              <tr>
                <th scope="row">주문코드</th>
                <td><input type="text" name="order_cd" id="order_cd" /></td>
                <th scope="row">반품사유</th>
              </tr>
              <tr>
                <th scope="row">제품명</th>
                <td><input type="text" name="prod_nm" id="prod_nm" /></td> 
                <td colspan="3" rowspan="5"><textarea class="ui-widget ui-widget-content ui-corner-all" 
                                                      id="refund_reason" maxlength="200" name="detail" style="height: 200px; length: 200px; outline: none; resize: none;" 
                                                      placeholder="여기에  반품사유를 적어주세요.(200자 이내)"></textarea></td>
              </tr>
              <tr>
                <th scope="row">제품코드</th>
                <td><input type="text" name="product_cd" id="product_cd" /></td>
                </tr>
              <tr>
                <th scope="row">환불금액</th>
                <td><input type="text" name="refund_amt" id="refund_amt" /></td>
              </tr>
              <tr>
                <th scope="row">상세주소</th>
                <td><input type="text" name="addr" id="addr" /></td>
              </tr>
              <tr>
                <th scope="row">반품수량</th>
                <td><input type="text" name="refund_cnt" id="refund_cnt" /></td>
              </tr>
            </tbody>
          </table>
          <div class="btn_areaC mt30">
            <a href="" class="btnType blue" id="btnSubmit" name="btn"><span>등록</span></a> <a href="" class="btnType gray" id="btnCloseModal" name="btn"><span>닫기</span></a>
          </div>
        </dd>
      </dl>
      <a href="" class="closePop" id="btnClose" name="btn"><span class="hidden">닫기</span></a>
    </div>
  </form>
</body>
</html>