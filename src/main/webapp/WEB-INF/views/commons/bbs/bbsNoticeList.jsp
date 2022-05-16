<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
<title>${brdMstrVO.bbsNm}</title>
<!-- <link rel="stylesheet" type="text/css" href="css/design.css"> -->

<script type="text/javascript">

$(document).ready(function(){
	
		//alert("${brdMstrVO.bbsTyCode}");
		
		$('#searchCnd').val("${searchVO.searchCnd}");
		$('#cateCode').val("${searchVO.cateCode}");
		<c:if test="${brdMstrVO.bbsTyCode == 'BBST05' }">
			$('#prjId').val("${searchVO.prjId}");
			$('#reportType').val("${searchVO.reportType}");
		</c:if>
		
	
		// 조회 버튼 Event Bind
		$("#btn_read").click(function(){ 
			
			
			select_noticeList(1);
		});

		// 글쓰기 버튼 Event Bind
		$("#btn_create").click(function(){ 
			addNotice();
		});
		
		
		$(".btn_subForm").click(function(){
			var idx = $(".btn_subForm").index(this);
// 			alert(idx);
			var useYN = $('form[name="subForm"]').eq(idx).find('input[name=useAt]').val();
			if(useYN == 'N') {
				var msg = "<s:message code='INF.DEL' />";
				showMsgBox("INF", msg); return false;
			}
			
			$('form[name="subForm"]').eq(idx).submit();
			return false;
		});
		
		//달력팝업 추가...
		$( "#searchBgnDe" ).datepicker();
		$( "#searchEndDe" ).datepicker();
		
		
		//기간 버튼 클릭 체크
		$(".bd_none a").click(function(){
			var btna = $(".bd_none a"); 
			var idx = btna.index(this);
			btna.removeClass('tb_bt_select').addClass('tb_bt');
			btna.eq(idx).removeClass('tb_bt').addClass('tb_bt_select');

			//alert(idx);
			setBetweenDtm( idx, $( "#searchBgnDe" ), $( "#searchEndDe" ));
			
		});
	
	
});


//엔터키 이벤트 처리
EnterkeyProcess("Search");

function doAction(action) {
	switch(action) {
	case "Search":
		select_noticeList(1);
		break;
	}
}
	

// function press(event) {
// 	if (event.keyCode==13) {
// 		fn_egov_select_noticeList('1');
// 	}
// }


//글쓰기
function addNotice() {
	document.frm.action = "<c:url value='/commons/bbs/addBoardArticle.do'/>";
	document.frm.submit();
}

//목록조회
function select_noticeList(pageNo) {
	document.frm.pageIndex.value = pageNo;
	document.frm.action = "<c:url value='/commons/bbs/selectBoardList.do'/>";
	document.frm.submit();
}


//게시물상세조회
function inqire_notice(i, nttId, bbsId) {
	document.subForm.nttId.value = nttId;
	document.subForm.bbsId.value = bbsId;
	document.subForm.action = "<c:url value='/commons/bbs/selectBoardArticle.do'/>";
	document.subForm.submit();
}


$(window).resize(function(){
                
        // Window Resize 시  컬럼  Width%
//         var interval = "5|10|10|10|15|15|35";
        
//         chgSize(mySheet, interval);
});


</script>
</head>
<body>
<div class="">
        	<div style="clear:both; height:5px;"><span></span></div>
       <div class="search_div">    
			<div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
			<div style="clear:both; height:5px;"><span></span></div>
            <form name="frm" action ="<c:url value='/commons/bbs/selectBoardList.do'/>" method="post">
            	<input type="hidden" name="bbsId" value="<c:out value='${boardVO.bbsId}'/>" />
				<input type="hidden" name="nttId"  value="0" />
				<input type="hidden" name="bbsTyCode" value="<c:out value='${brdMstrVO.bbsTyCode}'/>" />
				<input type="hidden" name="bbsAttrbCode" value="<c:out value='${brdMstrVO.bbsAttrbCode}'/>" />
				<input type="hidden" name="authFlag" value="<c:out value='${brdMstrVO.authFlag}'/>" />
				<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
            <table border="0" cellspacing="0" cellpadding="0" class="tb_write" summary="">
                <caption>
                <s:message code="INQ.COND" /> <!-- 조회조건 -->
                </caption>
                <colgroup>
                    <col style="width:15%;">
                    <col style="width:35%;">
                    <col style="width:15%;">
                    <col style="width:35%;">
                </colgroup>
              <c:if test="${brdMstrVO.bbsTyCode == 'BBST05' }">
              <tr>
                <th><s:message code="PRJT" /></th> <!-- 프로젝트 -->
                <td>
                <select id="prjId" name="prjId" title="<s:message code='PRJT.CHC' />"> <!-- 프로젝트선택 -->
                	<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
	                	<c:forEach var="code" items="${codeMap.prjCode}" varStatus="status">
	                    <option value="${code.codeCd}">${code.codeLnm}</option>
	                    </c:forEach>
                </select>
                </td>
                <th><s:message code="CALC.PTRN" /></th> <!-- 산출물유형 -->
                <td>
                <select id="reportType" name="reportType" title="<s:message code='CALC.PTRN' />"> <!-- 산출물유형 -->
                	<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
	                	<c:forEach var="code" items="${codeMap.reportType}" varStatus="status">
	                    <option value="${code.codeCd}">${code.codeLnm}</option>
	                    </c:forEach>
                </select>
                </td>
              </tr>
              </c:if>
              <tr>
                <th><s:message code="DSTC" /></th> <!-- 구분 -->
                <td>
                	<select id="cateCode" name="cateCode">
                		<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
	                	<c:forEach var="code" items="${codeMap.cateCode}" varStatus="status">
	                    <option value="${code.codeCd}">${code.codeLnm}</option>
	                    </c:forEach>
                	</select>
                </td>
                <th><s:message code="BULI.INQ" /></th> <!-- 게시물검색 -->
                <td>
                <select  id="searchCnd" name="searchCnd" title="<s:message code='INQ.COND.CHC' />"> <!-- 검색조건선택 -->
                	<option value="0"><s:message code="TTL" /></option> <!-- 제목 -->
                    <option value="1"><s:message code="CNTN" /></option> <!-- 내용 -->
                    <option value="2"><s:message code="DFTM" /></option> <!-- 작성자 -->
                </select>
                <input type="text" name="searchWrd" value="${searchVO.searchWrd}" title="" style="display: inline; width: 150px;"> <!-- 검색어 입력 -->
                </td>
              </tr>
<%--               <tr>
                <th>검색구분</th>
                <td>
                <select name="searchCnd" title="검색조건선택">
                	<option value="0">제목</option>
                    <option value="1">내용</option>
                    <option value="2">작성자</option>
                </select>
                </td>
                <th>검색단어</th>
                <td><input type="text" name="searchWrd" value="${searchVO.searchWrd}" title="검색어 입력"></td>
              </tr> --%>
              <!-- <tr>
                <th>등록자</th>
                <td><input type="text" name="searchRegNm"></td>
              </tr> -->
              <tr>
                <th class="bd_none"><s:message code="TERM" /></th> <!-- 기간 -->
                <td class="bd_none">
                	<a href="#" class="tb_bt">1<s:message code="DD" /></a> <!-- 일 -->
                    <a href="#" class="tb_bt">3<s:message code="DD" /></a> <!-- 일 -->
                    <a href="#" class="tb_bt">7<s:message code="DD" /></a> <!-- 일 -->
                    <a href="#" class="tb_bt">1<s:message code="MN" /></a> <!-- 개월 -->
                    <a href="#" class="tb_bt">3<s:message code="MN" /></a> <!-- 개월 -->
                    <a href="#" class="tb_bt">6<s:message code="MN" /></a> <!-- 개월 -->
                </td>
                <th><s:message code="REG.DTTM2" /></th> <!-- 등록일 -->
              		   <td><input id="searchBgnDe" name="searchBgnDe" type="text" class="wd80" value="${searchVO.searchBgnDe}" >  - <input id="searchEndDe" name="searchEndDe" type="text" class="wd80" value="${searchVO.searchEndDe}">
              </tr>
            </table>
            </form>
            
            <ul class="bt">
            	<li id="btn_read"><a class="bt_gray"><s:message code="BTN.READ"/></a></li>
            	<c:if test="${brdMstrVO.bbsTyCode != 'BBST03' or sessionScope.loginVO.isAdminYn == 'Y' }">
                <li id="btn_create"><a class="bt_gray"><s:message code="BTN.CREATE"/></a></li>
            	</c:if>
            </ul>
            
            
<%--             <div class="bbs_num">[${resultCnt}]</div> --%>
            <table border="0" cellspacing="0" cellpadding="0" class="tb_bbslist" summary="">
                <caption>
                <s:message code="BUBD.LST" /> <!-- 게시판리스트 -->
                </caption>
                <colgroup>
                    <col style="width:10%;">
                    <col style="width:10%;">
                    <col style="width:40%;">
                    <col style="width:10%;">
                    <col style="width:10%;">
                    <col style="width:10%;">
                    <col style="width:10%;">
                </colgroup>
              <tr>
                <th>Num</th>
                <th><s:message code="DSTC" /></th> <!-- 구분 -->
                <th><s:message code="TTL" /></th> <!-- 제목 -->
                <th><s:message code="FLIN.DTTM" /></th> <!-- 작성일시 -->
                <th><s:message code="ATFL" /></th> <!-- 첨부파일 -->
                <th><s:message code="DFTM" /></th> <!-- 작성자 -->
                <th><s:message code="HIT" /></th> <!-- 조회수 -->
              </tr>
              <c:forEach var="result" items="${resultList}" varStatus="status">
              <tr>
              	<td><c:out value="${resultCnt - ((searchVO.pageIndex-1) * searchVO.pageSize + status.count)+1}"/></td>
<%--               	<td><c:out value="${result.nttId}"/></td> --%>
              	<td>${result.cateNm}</td>
              	<td class="link_txt">
              		<form name="subForm" method="post" action="<c:url value='/commons/bbs/selectBoardArticle.do'/>">
						<input type="hidden" name="bbsId" value="<c:out value='${result.bbsId}'/>" />
						<input type="hidden" name="nttId"  value="<c:out value="${result.nttId}"/>" />
						<input type="hidden" name="cateCode"  value="<c:out value="${result.cateCode}"/>" />
						<input type="hidden" name="cateNm"  value="<c:out value="${result.cateNm}"/>" />
						<input type="hidden" name="useAt"  value="<c:out value="${result.useAt}"/>" />
						<input type="hidden" name="bbsTyCode" value="<c:out value='${brdMstrVO.bbsTyCode}'/>" />
						<input type="hidden" name="bbsAttrbCode" value="<c:out value='${brdMstrVO.bbsAttrbCode}'/>" />
						<input type="hidden" name="authFlag" value="<c:out value='${brdMstrVO.authFlag}'/>" />
						<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
				    	<a class="btn_subForm">
				    	<c:if test="${result.replyLc!=0}">
				    		<c:forEach begin="1" end="${result.replyLc}" step="1">
				    			&nbsp;
				    		</c:forEach>
				    		<img  src="<c:url value='/img/reply_arrow.gif'/>" alt="reply arrow" >
				    	</c:if>
				    	<c:out value="${result.nttSj}"/></a>
<%-- 				    	<input class="btn_subForm" type="submit" style="background-color:white; cursor:pointer; border:solid 0px black;text-align:left;" value='<c:out value="${result.nttSj}"/>'> --%>
			    	</form>
              	</td>
              	<td><c:out value="${result.frstRegisterPnttm}"/></td>
              	<td>
              		<c:if test="${result.atchFileId != null and  result.atchFileId != ''}">
              		<img src='<c:url value="/img/icon_06.gif"/>' alt="<s:message code='ATFL' />"> <!-- 첨부파일 -->
              		</c:if>
              	</td>
              	<td><c:out value="${result.frstRegisterNm}"/></td>
              	<td><c:out value="${result.inqireCo}"/></td>
              </tr>
              </c:forEach>
              <c:if test="${fn:length(resultList) == 0}">
              	<tr>
              		<td class="bd_none" colspan="7"><s:message code="MSG.NODATA" /></td>
              	</tr>
              </c:if>

            </table>
            
            <c:if test="${pageui != null and pageui != '' }">
            <div class="paging">
	  			${pageui}
            </div>
            </c:if>
            </div>
        </div>

</body>
</html>