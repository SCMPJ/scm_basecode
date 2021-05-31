<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!--  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link rel="stylesheet" type="text/css" href="${CTX_PATH}/css/tingle/tingle.css" />]
<link rel="stylesheet" href="${CTX_PATH}/css/view/ctm/cartList.css">
<link rel="stylesheet" href="${CTX_PATH}/css/view/ctm/cart.css">
<title>장바구니</title>
<jsp:include page="/WEB-INF/view/common/common_include.jsp"></jsp:include>
</head>
<body>
<form id="myForm" action=""  method="">
  <input type="hidden" id="currentPageCart" value="1">
  <input type="hidden" id="tmpCart" value="">
  <input type="hidden" id="tmpCartNm" value="">
  <input type="hidden" name="action" id="action" value="">
  <input type="hidden" id="totalAmount" value="${totalPrice*1.1}">
  <input type="hidden" id="pdcdArray" value="">
  
  <!-- 모달 배경 -->
  <div id="mask"></div>

  <div id="wrap_area">

    <h2 class="hidden">header 영역</h2>
    <jsp:include page="/WEB-INF/view/common/header.jsp"></jsp:include>

    <h2 class="hidden">컨텐츠 영역</h2>
    <div id="container">
      <ul>
        <li class="lnb">
          <!-- lnb 영역 --> <jsp:include
            page="/WEB-INF/view/common/lnbMenu.jsp"></jsp:include> <!--//  영역 -->
        </li>
        <li class="contents">
          <!-- contents -->
          <h3 class="hidden">contents 영역</h3> <!-- content -->
          <div class="content">

            <p class="Location">
              <a href="#" class="btn_set home">메인으로</a> <a href="#"
                class="btn_nav">주문</a> <span class="btn_nav bold">
                장바구니</span> <a href="javascript:fListCart();" class="btn_set refresh">새로고침</a>
            </p>

            <p class="conTitle" style="margin-bottom: 1%; display: flex; justify-content: center; align-items: space-between;">
              <span>장바구니</span>
            </p>            
            
          <div class="CartListFrame">
            <table class="col">
              <caption>caption</caption>
                <colgroup>
                  <col width="5%">
                  <col width="35%">
                  <col width="10%">
                  <col width="10%">
                  <col width="10%">
                  <col width="10%">
                  <col width="10%">
                  <col width="10%">
                </colgroup>
  
                <thead>
                  <tr>
                    <th scope="col">선택</th>
                    <th scope="col">제품명</th>
                    <th scope="col">품목명</th>
                    <th scope="col">제조사명</th>
                    <th scope="col">제품코드</th>
                    <th scope="col">수량</th>
                    <th scope="col">결제금액</th>
                    <th scope="col">주문관리</th>
                  </tr>
                </thead>
              <tbody id="listCart"></tbody>
            </table>
            </div>
            <div class="paging_area"  id="CartPagination"> </div>
            <div class="amount">
              <div id="totalPrice">
               <fmt:formatNumber type="number" minFractionDigits="0"  value="${totalPrice*1.1}" /> 원
              </div>
            </div>
            <div class="orderBtnContainer">
              <a class="btnType3 color2" href="javascript:fOrderModal();"><span>주문하기</span></a>
            </div>
          <h3 class="hidden">풋터 영역</h3>
            <jsp:include page="/WEB-INF/view/common/footer.jsp"></jsp:include>
        </li>
      </ul>
    </div>
  </div>
  </form>
<script type="text/javascript" charset="utf-8" src="${CTX_PATH}/js/tingle/tingle.js"></script>
<script type="text/javascript" charset="utf-8" src="${CTX_PATH}/js/view/ctm/cart.js"></script>
</body>
</html>
