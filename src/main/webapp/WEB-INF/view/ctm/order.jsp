<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>주문 :: Chain Maker</title>
<jsp:include page="/WEB-INF/view/common/common_include.jsp"></jsp:include>
<link rel="stylesheet" href="${CTX_PATH}/css/view/ctm/order.css">
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
								<a href="/system/notice.do" class="btn_set home">메인으로</a> 
								<a class="btn_nav">주문</a> 
								<span class="btn_nav bold">주문</span> 
								<a href="/ctm/order.do" class="btn_set refresh">새로고침</a>
							</p>
							<p class="conTitle">
								<span>주문</span>
							</p>
							<div class="ProductList">
								<div class="conTitle" style="margin: 0 25px 10px 0; float: left; width: 100%">
									<select id="searchKey" name="searchKey" style="width: 10%;" v-model="searchKey">
										<option value="all" selected="selected">전체</option>
										<option value="product_l_ct">품목명</option>
										<option value="product_m_ct">상호명</option>
										<option value="product_nm">제품명</option>
										<option value="product_cd">제품코드</option>
									</select> 
									<input type="text" style="width: 75%; height: 30px;" id="sname" name="sname"> 
									<a href="" class="btnType blue" id="searchBtn" name="btn"> 
										<span>검 색</span>
									</a>
								</div>
								<div id="productList"></div>
							</div>
							<div class="paging_area" id="productPagination"></div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
	<script src="${CTX_PATH}/js/view/ctm/order.js"></script>
</body>
</html>