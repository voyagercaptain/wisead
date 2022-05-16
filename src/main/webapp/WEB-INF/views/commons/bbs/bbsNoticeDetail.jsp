<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
<title>${bdMstr.bbsNm} - <s:message code="ARTICLE.WRITE" /></title> <!-- 글쓰기 -->
<script type="text/javascript" src='<c:url value="/js/ckeditor/ckeditor.js" />'></script>
<script type="text/javascript">

$(document).ready(function(){
	
  <c:if test="${result.bbsTyCode == 'BBST05' }">
    $('#prjId').val('${result.prjId}');
    $('#reportType').val('${result.reportType}');
  </c:if>
  
	//목록 버튼 클릭 이벤트....
	$('#btn_list').click(function(){
		select_noticeList();
	});
	//저장 버튼 클릭 이벤트....
	$('#btn_update').click(function(){
		
		document.frm.action = "<c:url value='/commons/bbs/forUpdateBoardArticle.do'/>";
		document.frm.submit();
	});
	
	//게시물 삭제 이벤트...
	$('#btn_delete').click(function(){
		
		//확인창에서 확인 후 처리...
		checkForm(); return false;
// 			document.frm.action = "<c:url value='/commons/bbs/deleteBoardArticle.do'/>";
// 			document.frm.submit();
// 		}
	});
	
	//답글 작성 이벤트...
	$('#btn_apply').click(function(){
		document.frm.action = "<c:url value='/commons/bbs/addReplyBoardArticle.do'/>";
		document.frm.submit();
	});
	
	
	CKEDITOR.replace( 'nttCn', {readOnly:true, toolbarStartupExpanded:false});
	
});



function doAction(action) {
	
	switch(action) {
	case 'DELETE':
		document.frm.action = "<c:url value='/commons/bbs/deleteBoardArticle.do'/>";
		document.frm.submit();
		break;
		
	}
}

function checkForm() {
		var msg = "<s:message code='CNF.DEL' />";
		showMsgBox("CNF", msg, 'DELETE');
		return false;
}

//게시물 목록으로 이동
function select_noticeList() {
	document.frm.action = "<c:url value='/commons/bbs/selectBoardList.do'/>";
	document.frm.submit();
}


</script>
</head>
<body>
<div style="clear:both; height:5px;"><span></span></div>
        <!-- 오른쪽 내용 시작 -->
        <div class="right">
<%--         	<div class="location"><img src='<c:url value="/img/location_home.gif"/>' alt="home">  &gt; ${result.bbsNm}</div> --%>
            <div class="stit"><s:message code="KRN.INQ" /></div> <!-- 글조회 -->
            <div style="clear:both; height:5px;"><span></span></div>
            <form name="frm" method="post" action="">
			<input type="hidden" name="pageIndex" value="<c:out value='${searchVO.pageIndex}'/>">
			<input type="hidden" name="bbsId" value="<c:out value='${result.bbsId}'/>" >
			<input type="hidden" name="nttId" value="<c:out value='${result.nttId}'/>" >
			<input type="hidden" name="cateCode"  value="<c:out value="${result.cateCode}"/>" />
			<input type="hidden" name="parnts" value="<c:out value='${result.parnts}'/>" >
			<input type="hidden" name="sortOrdr" value="<c:out value='${result.sortOrdr}'/>" >
			<input type="hidden" name="replyLc" value="<c:out value='${result.replyLc}'/>" >
			<input type="hidden" name="nttSj" value="<c:out value='${result.nttSj}'/>" >
			<input type="hidden" name="prjId" value="<c:out value='${result.prjId}'/>" >
			<input type="hidden" name="reportType" value="<c:out value='${result.reportType}'/>" >
            

            <table border="0" cellspacing="0" cellpadding="0" class="tb_write" summary="">
                <caption>
                <s:message code="INPT.FORM" /> <!-- 입력폼 -->
                </caption>
                <colgroup>
                    <col style="width:12%;">
                    <col style="width:38%;">
                    <col style="width:12%;">
                    <col style="width:38%;">
                </colgroup>
<%--               <tr>
                <th>프로젝트명</th>
                <td><input type="text"></td>
                <th>시스템정보</th>
                <td><input type="text"></td>
              </tr>
              <tr>
                <th>프로젝트기간</th>
                 <td><input type="text" class="wd80"> <a href="#"><img src='<c:url value="/img/icon_05.gif"/>' alt="날짜선택"></a> - <input type="text" class="wd80"> <a href="#"><img src='<c:url value="/img/icon_05.gif"/>' alt="날짜선택"></a></td>
              </tr>
              <tr>
                <th>PM명</th>
                <td><input type="text"></td>
                <th>연락처</th>
                <td><input type="text"></td>
              </tr> 
              <tr>
                <th>등록자</th>
                <td>홍길동</td>
                <th>등록일</th>
                <td>2013.09.02</td>
              </tr> --%>
              <c:if test="${result.bbsTyCode == 'BBST05' }">
              <tr>
                <th><s:message code="PRJT" /></th> <!-- 프로젝트 -->
                <td >
                <select id="prjId" name="prjId" title="<s:message code='PRJT.CHC' />" disabled="disabled"> <!-- 프로젝트선택 -->
                	<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
	                	<c:forEach var="code" items="${codeMap.prjCode}" varStatus="status">
	                    <option value="${code.codeCd}">${code.codeLnm}</option>
	                    </c:forEach>
                </select>
                </td>
                <th><s:message code="CALC.PTRN" /></th> <!-- 산출물유형 -->
                <td>
                <select id="reportType" name="reportType" title="<s:message code='CALC.PTRN' />" disabled="disabled"> <!-- 산출물유형 -->
                	<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
	                	<c:forEach var="code" items="${codeMap.reportType}" varStatus="status">
	                    <option value="${code.codeCd}">${code.codeLnm}</option>
	                    </c:forEach>
                </select>
                </td>
              </tr>
              </c:if>
              <tr>
                <th><s:message code="BBS.SUBJECT" /></th>
                <td ><c:out value="${result.nttSj}" /></td>
                <th><s:message code="DSTC" /></th> <!-- 구분 -->
                <td ><c:out value="${result.cateNm}" /></td>
              </tr>
              <tr>
                <th><s:message code="RGSR" /></th> <!-- 등록자 -->
                <td>	    
                	<c:choose>
			    	<c:when test="${result.ntcrNm == null || result.ntcrNm == ''}">
			    		<c:out value="${result.frstRegisterNm}" />
			    	</c:when>
			    	<c:otherwise>
			    		<c:out value="${result.ntcrNm}" />
			    	</c:otherwise>
			    	</c:choose>
	    	</td>
                <th><s:message code="REG.DTTM2" /></th> <!-- 등록일 -->
                <td>${result.frstRegisterPnttm}</td>
              </tr>
              <tr>
                <th><s:message code="BBS.CONTENTS" /></th>
<%--                 <td colspan="3"><div id="bbs_cn"><c:out value="${result.nttCn}" escapeXml="false" /></div></td> --%>
                <td colspan="3"><textarea  id="nttCn" name="nttCn" accesskey="" style="width:96%; height:200px;">${result.nttCn}</textarea></td>
              </tr>
              <c:if test="${not empty result.atchFileId}">
              <tr>
                <th class="bd_none"><s:message code="FILE.LIST" /></th>
                <td colspan="3" class="bd_none">
                	<c:import url="/commons/fms/selectFileInfs.do" charEncoding="utf-8">
						<c:param name="param_atchFileId" value="${result.atchFileId}" />
					</c:import>
                </td>
              </tr>
              </c:if>
            </table>
            </form>
            <ul class="bt">
            <c:if test="${result.frstRegisterId == sessionUniqId}">
            	<li id="btn_update"><a  class="bt_gray"><s:message code="BTN.UPDATE"/></a></li>
            	<li id="btn_delete"><a  class="bt_gray"><s:message code="BTN.DELETE"/></a></li>
            </c:if>
            <c:if test="${result.replyPosblAt == 'Y'}">
            	<li id="btn_apply"><a  class="bt_gray"><s:message code="BTN.APPLY"/></a></li>
            </c:if>
            	<li id="btn_list"><a  class="bt_gray"><s:message code="BTN.LIST"/></a></li>
            </ul> 
            
        </div>
        <!-- 오른쪽 내용 끝 -->
</body>
</html>