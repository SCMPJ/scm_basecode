<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:if test="${totalProduct eq 0 }">
	<tr>
		<td colspan="1">데이터가 존재하지 않습니다.</td>
	</tr>
</c:if>
<c:if test="${totalProduct > 0 }">
	<c:set var="nRow" value="${pageSize*(currentPageProduct-1)}" />
	<c:forEach items="${productListModel}" var="list">
		<div class="product_container-big">
			<img class="product_img" style="height: 200px;" src="http://img.danawa.com/prod_img/500000/135/584/img/12584135_1.jpg?shrink=360:360&_v=20201030144612" />
			<div class="product_container">
				<div class="product_box">
					<span>제품명</span>
					<span>${list.prod_nm}</span>
					<input type="hidden" name="${list.product_cd}" value="${list.product_cd}"/>
					<input type="hidden" name="${list.product_cd}productName" value="${list.prod_nm}"/>
				</div>
				<div class="product_box">
					<span>품목명</span>
					<span>${list.l_ct_nm}</span>
				</div>
				<div class="product_box">
					<span>상호명</span>
					<span>${list.m_ct_nm}</span>
					<input type="hidden" name="${list.product_cd}middleCategory" value="${list.m_ct_nm}"/>
				</div>
				<div class="product_box">
					<span>가격</span>
					<span><fmt:formatNumber value="${list.price}" pattern="#,###"/></span>
					<input type="hidden" name="${list.product_cd}productPrice" value="${list.price}"/>
				</div>
				<div class="product_box">
					<span>수량</span>
					<input type="number" name="${list.product_cd}Count" value="0" min="0"/>
				</div>
				<div class="product_box button_box">
					<a href="javascript:purchaseItem('${list.product_cd}')" class="btnType blue orderBtn">구매하기</a>
					<a href="javascript:putToCartList('${list.product_cd}')" class="btnType orderBtn">장바구니</a>
				</div>
			</div>
		</div>
		<c:set var="nRow" value="${nRow + 1}" />
	</c:forEach>
</c:if>
<input type="hidden" id="totalProduct" name="totalProduct" value="${totalProduct}" />



