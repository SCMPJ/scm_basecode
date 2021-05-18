<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="divEmpty">
	<div class="hiddenData"></div>
	<table class="col">
		<thead>
			<tr>
				<th scope="col">주문코드</th>
				<th scope="col">반품번호</th>
				<th scope="col">제품명</th>
				<th scope="col">품목명</th>
				<th scope="col">반품 수량</th>
				<th scope="col">고객 명</th>
				<th scope="col">고객연락처</th>
				<th scope="col">주소</th>
			</tr>
		</thead>
		<tbody id="returnDetailListTop">
			<c:forEach items="${returnDetailList}" var="topList">
				<tr>
					<td><input name="order_cd" type="hidden" value="${topList.order_cd}"/>${topList.order_cd}</td>
					<td><input name="refund_list_no" type="hidden" value="${topList.refund_list_no}"/>${topList.refund_list_no}</td>
					<td>${topList.prod_nm}</td>
					<td>${topList.m_ct_nm}</td>
					<td><input name="refund_list_no" type="hidden" value="${topList.refund_cnt}"/>${topList.refund_cnt}</td>
					<td>${topList.cus_name}</td>
					<td>${topList.cus_tel}</td>
					<td>${topList.cus_addr}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<table class="col">
		<thead>
			<tr>
				<th scope="col">담당자명</th>
				<th scope="col">배송사원</th>
				<th scope="col">배송사원 연락처</th>
				<th scope="col">창고명</th>
				<th scope="col">반품 상태</th>
				<th scope="col">적요</th>
			</tr>
		</thead>
		<tbody id="returnDetailListBottom">
			<c:forEach items="${returnDetailList}" var="bottomList">
				<tr>
					<td>${bottomList.scm_name}</td>
					<c:choose>
						<c:when test="${empty bottomList.dlv_name}">
							<td>
								<c:if test="${!empty dlvStaffNameCombo}">
			                         <select style="width: 75px;" id="getDlvStaffName" name="dlvStaffNameAndLoginId" onchange="javascript:fSelectDlvStaffTel();">
			                             <option>선택</option>
			                             <c:forEach items="${dlvStaffNameCombo}" var="listDlvStaffCombo">
			                                 <option value="${listDlvStaffCombo.dlv_staff_name} ${listDlvStaffCombo.loginID}">${listDlvStaffCombo.dlv_staff_name}</option>
			                             </c:forEach>
			                         </select>
			                    </c:if>
		                    </td>
							<td id="dlvStaffTel"></td>
						</c:when>
						<c:otherwise>
						  <td>${bottomList.dlv_name}</td>
						  <td>${bottomList.dlv_tel}</td>
						</c:otherwise>
					</c:choose>
					<td>${bottomList.warehouse_nm}</td>
					<c:choose>
					   <c:when test="${bottomList.state eq '7'}">
							<td>
							   <select name="state" disabled style="width: 81px;">
									<option value="6" >반품진행중</option>
									<option value="7" selected>반품완료</option>
							   </select>
							</td>
						</c:when>
						<c:otherwise>
							<td>
	                           <select name="state" style="width: 81px;">
	                                <option value="6">반품진행중</option>
	                                <option value="7">반품완료</option>
	                           </select>
	                        </td>
						</c:otherwise>
					</c:choose>
					<td>${bottomList.request}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
