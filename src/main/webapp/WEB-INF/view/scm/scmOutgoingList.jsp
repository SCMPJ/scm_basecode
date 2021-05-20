<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:if test="${scmOutgoingCnt eq 0 }">
  <tr>
    <td colspan="4">데이터가 존재하지 않습니다.</td>
  </tr>
</c:if>
<c:if test="${scmOutgoingCnt > 0 }">
  <c:forEach items="${outgoingList}" var="list">
    <tr>
      <td><a href="javascript:fOutgoingDetailList('${list.refund_list_no}');">${list.refund_list_no}</a></td>
      <td>${list.cus_nm}</td>
      <td>${list.l_ct_nm}</td>
      <td>${list.prod_nm}</td>
      <td>${list.return_cnt}</td>
      <td>${list.scm_nm}</td>
      <td>${list.submit_date}</td>
    </tr>
  </c:forEach>
</c:if>
<input type="hidden" id="totCnt" name="totCnt" value="${scmOutgoingCnt}" />