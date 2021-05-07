<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>출하계획</title>

<jsp:include page="/WEB-INF/view/common/common_include.jsp"></jsp:include>

<!-- D3 -->
<style>
//
click-able rows
	.clickable-rows {tbody tr td { cursor:pointer;
	
}

.el-table__expanded-cell {
	cursor: default;
}
}
</style>
<script type="text/javascript">
	var pageSizeinf = 3;
	var pageBlockSizeinquiry = 10;

	/** 공지사항 조회 */
	function fOrderList(currentPage) {

		currentPage = currentPage || 1;

		console.log("currentPage : " + currentPage);

		var param = {
			currentPage : currentPage,
			pageSize : pageSizeinf
		}

		var resultCallback = function(data) {
			fOrderListResult(data, currentPage);
		};

		//Ajax실행 방식
		//callAjax("Url",type,return,async or sync방식,넘겨준거,값,Callback함수 이름)
		//html로 받을거라 text
		callAjax("/search/orderlist.do", "post", "text", true, param, resultCallback);
	}

	/** 공지사항 조회 콜백 함수 */
	function fListInfResult(data, currentPage) {
		//alert(data);
		console.log(data);

		// 기존 목록 삭제
		$('#listInf').empty();
		$("#listInf").append(data);

		// 총 개수 추출
		var totalCntlistInf = $("#totcnt").val();
		var list = $("#selectedInfNo").val();
		// 페이지 네비게이션 생성
		var paginationHtml = getPaginationHtml(currentPage, totalCntlistInf,
				pageSizeinf, pageBlockSizeinquiry, 'fListInf', [ list ]);
		console.log("paginationHtml : " + paginationHtml);

		$("#listInfPagination").empty().append(paginationHtml);

	}

	/*공지사항 상세 조회*/
	function fNoticeModal(noticeNo) {

		var param = {
			noticeNo : noticeNo
		};
		var resultCallback2 = function(data) {
			fdetailResult(data);
		};

		callAjax("/system/detailNotice.do", "post", "json", true, param,
				resultCallback2);
	}

	/*  공지사항 상세 조회 -> 콜백함수   */
	function fdetailResult(data) {

		if (data.resultMsg == "SUCCESS") {
			//모달 띄우기 
			gfModalPop("#notice");

			// 모달에 정보 넣기 
			frealPopModal(data.result);

		} else {
			alert(data.resultMsg);
		}
	}

	/* 팝업 _ 초기화 페이지(신규) 혹은 내용뿌리기  */
	function frealPopModal(object) {

		$("#loginId").val(object.loginId);
		$("#loginId").attr("readonly", true); // 작성자 수정불가 

		$("#write_date").val(object.noticeRegdate);
		$("#write_date").attr("readonly", true); // 처음 작성된 날짜 수정불가 

		$("#noticeTitle").val(object.noticeTitle);
		$("#noticeTitle").attr("readonly", true);

		$("#noticeContent").val(object.noticeContent);
		$("#noticeContent").attr("readonly", true);

		$("#noticeNo").val(object.noticeNo); // 중요한 num 값도 숨겨서 받아온다. 

	}
	
	var picker = new Pikaday({ 
		  field: document.getElementById('startDate'),
		  format: 'yyyy-MM-dd',
		  toString(date, format) {
		    let day = ("0" + date.getDate()).slice(-2);
		    let month = ("0" + (date.getMonth() + 1)).slice(-2);
		    let year = date.getFullYear();
		    return `${year}-${month}-${day}`;
		  }
		});
</script>
</head>
<body>
	<form id="myForm" action="" method="post">
		<input type="hidden" id="currentPage" value="1"> <input
			type="hidden" id="selectedInfNo" value="">
		<!-- 모달 배경 -->
		<div id="mask"></div>
		<div id="wrap_area">
			<h2 class="hidden">컨텐츠 영역</h2>
			<div id="container">
				<ul>
					<li class="lnb">
						<!-- lnb 영역 --> <jsp:include
							page="/WEB-INF/view/common/lnbMenu.jsp"></jsp:include> <!--// lnb 영역 -->
					</li>
					<li class="contents">
						<!-- contents -->
						<h3 class="hidden">contents 영역</h3> <!-- content -->

						<div class="content" style="margin-bottom: 20px;">

							<p class="Location">
								<a href="../dashboard/dashboard.do" class="btn_set home">메인으로</a>
								<span class="btn_nav bold">메인</span> <a
									href="../dashboard/dashboard.do" class="btn_set refresh">새로고침</a>
							</p>

							<p class="conTitle">               
					  <span>출하계획</span>
 					  <span class="fr"> 
 						 <span>
					    	<select id="adm_type" style="width: 100px;">
                               <option value="all" selected="selected">전체</option>
                               <option value="rfs">배송준비중</option>
                               <option value="dfp">배송중</option>
                               <option value="com">배송완료</option>
                            </select>
                         </span> 
                         <input type="date" id="theday" min="2020-01-01" max="2020-12-31"  style="width: 200px; height: 28px;" 
                         onKeyDown="if(event.keyCode == 13) javascript:fAdmList()">
                         <span>~</span>
                         <input type="date" id="theday" min="2020-01-01" max="2020-12-31"  style="width: 200px; height: 28px;" 
                         onKeyDown="if(event.keyCode == 13) javascript:fAdmList()">
 						 <a class="btnType blue" href="javascript:fAdmList()" ><span id="searchEnter">검색</span></a>
 						 <c:if test="${typeCheck.user_type eq 'C'}">
   						 	<a href="javascript:gfModalPop('#layer2');" class="btnType blue" id="submitBtn"><span id="">작성</span></a>
					     </c:if>
 					  </span>
				    </p> 
					<table class="col">
					<%-- <caption>caption</caption>
						<colgroup>
							<col width="15%">
							<col width="15%">
							<col width="15%">
							<col width="15%">
							<col width="5%">
							<col width="5%">
							<col width="5%">
						</colgroup> --%>
						<thead>
							<tr>
								<th scope="col">접수일자</th>
								<th scope="col">도착일자</th>
								<th scope="col">도착예정일자</th>
								<th scope="col">주문코드</th>
								<th scope="col">배송담당자</th>
								<th scope="col">창고명</th>
								<th scope="col">상태</th>
							</tr>
						</thead>
						<tbody id="admNoticeList"></tbody>
					</table>
				</div>

							<div class="paging_area" id="listInfPagination"></div>
							<br> <br>
							<div>
								<p class="subTitle" style="margin-bottom: 1%;">
									<span>상세페이지</span>
								</p>
							</div>
							<div>
								<table class="col">
									<thead>
										<tr>
											<th scope="col">주문코드</th>
											<th scope="col">배송코드</th>
											<th scope="col">제품명</th>
											<th scope="col">카테고리</th>
											<th scope="col">수량</th>
											<th scope="col">고객명</th>
											<th scope="col">고객연락처</th>
											<th scope="col">담당자명</th>
											<th scope="col">배송주소</th>
											<th scope="col">배송사원연락처</th>
											<th scope="col">창고명zzz</th>
										</tr>
									</thead>
								</table>
								
								<button class="btn btn-default" type="submit">등록</button>
								<button class="btn btn-default" type="submit">닫기</button>
							</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</body>
</html>