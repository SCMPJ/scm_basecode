<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!--  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<style type="text/css">

/*

.ddd{
display:flex;
justify-content: space-around;
}

*/

.btnType{
	float:right;
}

.aaa{
	 margin-bottom:5px;
}

.bbb li{
	float:left;
	margin-right: 30px;
		
}

#EmpDvModal{
	display: none;	
}


</style>

<title>주문</title>

<jsp:include page="/WEB-INF/view/common/common_include.jsp"></jsp:include>

<script type="text/javascript">
		
		/* 조회 조건 파라미터 변수 선언 */
		var from_date;
		var	to_date;
		var account_cd;
		var detail_account_cd;
		var dv_app_yn;

		/*지출 결의서 페이징 설정*/

		var pageSizeEmpDv= 5;
		var pageBlockSizeEmpDv = 5;


		/** OnLoad event */ 

		$(document).ready(function() {
	
			
			fListEmpDv(); // 지출결의서 신청건 조회
		
		});

		/** 지출결의서 신청건 조회 */
		function fListEmpDv(currentPage) {
			
			currentPage = currentPage || 1;
			
			
			console.log("currentPage : " + currentPage);
			
			from_date = $("#from_date").val(); // 신청날짜 - 시작날짜
			to_date	  = $("#to_date").val();	// 신청날짜 - 끝날짜	
			account_cd = $("#account_cd").val(); // 대분류코드
			detail_account_cd = $("#detail_account_cd").val(); //상세분류코드
			dv_app_yn = $("#dv_app_yn").val();   //	승인여부
			
			
			console.log("from_date : " + from_date + "to_date : " + to_date + "account_cd : " + account_cd + 
					"detail_account_cd : "  + detail_account_cd + "dv_app_yn : " + dv_app_yn);
			
			var param = {
						from_date : from_date
					,	to_date   : to_date
					,	account_cd : account_cd
					,	detail_acoount_cd : detail_account_cd 	
					,	dv_app_yn   : dv_app_yn
					,	currentPage : currentPage
					,	pageSize    : pageSizeEmpDv
			}
			alert("account_cd: " +  account_cd + "detail_account_cd: " + detail_account_cd + "dv_app_yn:" + dv_app_yn);
			
			var resultCallback = function(data) {
				fempDvResult(data, currentPage);
			};
			//Ajax실행 방식
			//callAjax("Url",type,return,async or sync방식,넘겨준거,값,Callback함수 이름)
			
			callAjax("/accounting/listEmpDv.do", "post", "text", true, param, resultCallback);
		}

		
		/** 그룹코드 조회 콜백 함수 */
		function fempDvResult(data, currentPage) {
			
			//alert(data);
			console.log(data);
			
			// 기존 목록 삭제
			$('#listEmpDv').empty();
			
			// 신규 목록 생성
			$("#listEmpDv").append(data);
			
			// 총 개수 추출
			var totalCntEmpDv = $("#totalCntEmpDv").val();
			
			// 페이지 네비게이션 생성
			var paginationHtml = getPaginationHtml(currentPage, totalCntEmpDv, pageSizeEmpDv, pageBlockSizeEmpDv, 'fListEmpDv');
			console.log("paginationHtml : " + paginationHtml);
			//alert(paginationHtml);
			$("#empDvPagination").empty().append( paginationHtml );
			
			// 현재 페이지 설정
			$("#currentPageEmpDv").val(currentPage);
		}
		
		function fPopModalEmpDv(){
			gfModalPop('#layer1');
		}

		function fEmpModalShow(){
			document.getElementById("EmpDvModal").style.display = "block";
		}
		
		
		
</script>

</head>
<body>

	<input type="hidden" id="currentPageEmpDv" value="1">
	<input type="hidden" id="tmpEmpDv" value="">
	<input type="hidden" id="tmpEmpDvNm" value="">
	<input type="hidden" name="action" id="action" value="">
	
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
				</li>lnb
				<li class="contents">
					<!-- contents -->
					<h3 class="hidden">contents 영역</h3> <!-- content -->
					<div class="content">

						<p class="Location">
							<a href="#" class="btn_set home">메인으로</a> <a href="#"
								class="btn_nav">회계</a> <span class="btn_nav bold">
								지출결의서 신청</span> <a href="#" class="btn_set refresh">새로고침</a>
						</p>

						
						
						<p class="conTitle">
							<span>지출결의서 신청</span> <span class="fr">
							  <a class="btnType blue"
										href="javascript:fListEmpDv()" name="search"><span
										id="searchEnter">조회</span></a><br/>
							</span>
						</p>
						
						
						<div>
						<form class = "aaa">
							<Strong>신청일자</Strong> <input type = "date" id="from_date">~<input
										type="date" id="to_date">
							<!--  <a class="btnType blue" href="javascript:fPopModalEmpDv();" name="modal"><span>신규등록</span></a> -->
							<a class="btnType blue" onclick = "fEmpModalShow();" name="modal"><span>신규등록</span></a>
						</form>	
						
						
						<form class = "bbb">
							<ul>
								<li>
								<strong>계정대분류명</strong><select id = "account_cd" name ="account_cd">
									<option value = "A100" selected>온라인지출</option>
									<option value = "B200">영업매출</option>
									<option value = "C300">급여</option>
									<option value = "D400">복리후생비</option>
									<option value = "E500">접대비</option>
									<option value = "F600">통신비</option>
								</select>	
							</li>
							<li>
								<strong>상세분류명</strong><select id = "detail_account_cd" name = "detail_account_cd">
									<option value = "101" selected >장비구매액</option>
									<option value = "102">단가</option>
									<option value = "103">부가세</option>
									<option value = "104">공급가액</option>
								</select>
							</li>
							<li>
								<strong>승인여부</strong><select id = "dv_app_yn" name = "dv_app_yn">
									<option value = "1">승인</option>
									<option value = "2">승인대기</option>
									<option value = "3" selected>반려</option>
								</select>
							</li>	
						</form>
					</div>
						
						
					<div class="divDvList">
						<table class="col">
							<caption>caption</caption>
								<colgroup>
									<col width="8%">
									<col width="10%">
									<col width="10%">
									<col width="10%">
									<col width="10%">
									<col width="10%">
									<col width="10%">
									<col width="*">
									<col width="9%">
									<col width="8%">
									<col width="8%">
								</colgroup>
	
								<thead>
									<tr>
										<th scope="col">결의번호</th>
										<th scope="col">계정대분류명</th>
										<th scope="col">계정과목</th>
										<th scope="col">거래처명</th>
										<th scope="col">신청일자</th>
										<th scope="col">사용일자</th>
										<th scope="col">지출금액</th>
										<th scope="col">승인여부</th>
										<th scope="col">승인일자</th>
										<th scope="col">승인자</th>
										<th scope="col">신청취소</th>
									</tr>
								</thead>
							<tbody id="listEmpDv"></tbody>
						</table>
					</div>
	
					  
					<div id ="EmpDvModal" href="javascript:fEmpDvModal()">
						<form>
						
						아이디<input type = "text" id = "loginID" disabled>
						계정대분류명<select name = "account_cd">
								<option value = "A100">온라인매출</option>
								<option value = "B200">영업매출</option>
								<option value = "C300">급여</option>
								<option	value = "D400">복리후생비</option>
								<option value = "E500">접대비</option>
								<option	value = "F600">통신비</option>
						</select>
						상세코드명<select name="detail_account_cd" id = "detail_account_cd">
								<option value="101">장비구매액</option>
								<option value="102">단가</option>
								<option value="103">부가세</option>
								<option value="104">공급가액</option>
						</select>
						거래처명<input type = "text" id = "loginID">
 						신청일자<input type = "date" id = "dv_app_date" disabled>
 						사용일자<input type = "date" id = "dv_use_date">
 						지출금액<input type = "text" id = "dv_amt">
						<label for = "att_file">첨부파일</label>
						<input type = "file" id = "att_file"/>
						비고<textarea cols="30" rows="10" ></textarea>
						</form>
						<button type = "submit">신청</button>
						<button>닫기</button>
					</div>	
		
	
	
					<div class="paging_area"  id="dvListPagination"> </div>
					</div> <!--// content -->

					<h3 class="hidden">풋터 영역</h3>
						<jsp:include page="/WEB-INF/view/common/footer.jsp"></jsp:include>
				</li>
			</ul>
		</div>
	</div>

</body>
</html> --%>