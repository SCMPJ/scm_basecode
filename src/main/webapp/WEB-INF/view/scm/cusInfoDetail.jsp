<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div style="height:35px; display:flex; justify-content:center; background-color: #DCE1E5; margin-bottom:20px; vertical-align:text-bottom;">
    <p style="margin-top:10px; font-size: 23px;">
        상세정보
    </p>
</div>
<form id="modifyActiveFrom">
	 <table class="row" style="text-align:center;">
	    <colgroup>
		    <col width="40px">
		    <col width="40px">
		    <col width="40px">
		    <col width="40px">
	    </colgroup>
	     <tbody>
	         <c:forEach items="${cusDetailInfo}" var="list">
	         <tr>
	           <th scope="row">로그인ID</th>
	           <td><input type="hidden" name="loginID" value="${list.loginID}"/>${list.loginID}</td>
	           <th scope="row">기업코드</th>
	           <td>${list.company_cd}</td>  
	         </tr>
	         <tr>
	           <th scope="row">기업명</th>
	           <td>${list.company_nm}</td>
	           <th scope="row">담당자명</th>
	           <td>${list.company_mng_nm}</td>
	         </tr>
	         <tr>
	           <th scope="row">연락처</th>
	           <td>${list.mng_tel}</td>
	           <th scope="row">메일</th>
	           <td>${list.mail}</td>
	         </tr>
	         <tr>
	           <th scope="row">은행</th>
	           <td>${list.bank}</td>
	           <th scope="row">계좌번호</th>
	           <td>${list.account}</td>
	         </tr>
	         <tr>
	           <th scope="row">우편번호</th>
	           <td>${list.zip_code}</td>
	           <th scope="row">가입일자</th>
	           <td>${list.join_date}</td>
	         </tr>
	         <tr>
	           <th scope="row">주소</th>
	           <td>${list.addr}</td>
	           <th scope="row">상세주소</th>
	           <td>${list.addr_detail}</td>
	         </tr>
	         <tr>
	           <th scope="row">활성화여부 변경</th>
	           <td>
	               <c:if test="${list.active eq 'Y'}">
		               <select name="active" style="width:40px; height:30px;">
		                  <option value="Y" disabled>Y</option>
		                  <option value="N" selected>N</option>
		               </select>
	               </c:if>
	               <c:if test="${list.active eq 'N'}">
                       <select name="active" style="width:40px; height:30px;">
                          <option value="Y" selected>Y</option>
                          <option value="N" disabled>N</option>
                       </select>
                   </c:if>
	           </td>
	         </tr>
	         </c:forEach>
	     </tbody>
	 </table>
 </form>
