<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
<title>${bdMstr.bbsNm} - <s:message code="BULL.ARTICLE.REVS" /></title> <!-- 게시글 수정 -->
<script type="text/javascript" src='<c:url value="/js/ckeditor/ckeditor.js" />'></script>
<script type="text/javascript">

var loadhtml = '<div class="process_loading">' + 
'<div class="loading_img"><img src="<c:url value="/img/loading.gif"/>" alt="<s:message code="TRTT" />"></div>' + /* 처리중 */
'<div class="loading_txt"><img src="<c:url value="/img/loading_txt.gif"/>" alt="<s:message code="TRTT.ING" />"></div>' + /* 처리중입니다. */
'</div>';

$(document).ready(function(){
	<c:if test="${result.bbsTyCode == 'BBST05' }">
		$('#prjId').val('${result.prjId}');
		$('#reportType').val('${result.reportType}');
	</c:if>
	
	$('#cateCode').val("${result.cateCode}");
	
	<c:if test="${errCode != ''}" >
		var message = "<s:message code='${errCode}' />";
		showMsgBox("ERR", message);
	</c:if>
	
	//목록 버튼 클릭 이벤트....
	$('#btn_list').click(function(){
		select_noticeList();
	});
	//저장 버튼 클릭 이벤트....
	$('#btn_update').click(function(){
		
		//TODO : 폼검증한다. 나중에...
		checkForm(); return false;
	
// 			document.board.action = "<c:url value='/commons/bbs/updateBoardArticle.do'/>";
// 			document.board.submit();
// 		}
	});
	
	//첨부파일 초기화
	<c:if test="${bdMstr.fileAtchPosblAt == 'Y'}">
	
		var existFileNum = document.board.fileListCnt.value;
		var maxFileNum = document.board.posblAtchFileNumber.value;
	
		if (existFileNum=="undefined" || existFileNum ==null) {
			existFileNum = 0;
		}
		if (maxFileNum=="undefined" || maxFileNum ==null) {
			maxFileNum = 0;
		}
		var uploadableFileNum = maxFileNum - existFileNum;
		if (uploadableFileNum<0) {
			uploadableFileNum = 0;
		}
		if (uploadableFileNum != 0) {
			fn_egov_check_file('Y');
			var multi_selector = new MultiSelector( document.getElementById( 'comFileList' ), uploadableFileNum );
			multi_selector.addElement( document.getElementById( 'comFileUploader' ) );
		} else {
			fn_egov_check_file('N');
		}
	</c:if>	
});

function doAction(action) {
	
	switch(action) {
	case 'UPDATE':
		document.board.action = "<c:url value='/commons/bbs/updateBoardArticle.do'/>";
		document.board.submit();
		//처리중 이미지 호출...
		processLoading(loadhtml);
		break;
		
	}
}


function checkForm() {
	<c:if test="${bdMstr.bbsTyCode == 'BBST05' }">
		if($('#prjId').val() == "") {
			var msg = "<s:message code="REQUIRED.PRJT" />' />"; /* 프로젝트 */
			showMsgBox("ERR", msg); $('#prjId').focus(); return false;
		}
		if($('#reportType').val() == "") {
			var msg = "<s:message code="REQUIRED.CALC.PTRN" />"; /* 산출물유형 */
			showMsgBox("ERR", msg); $('#reportType').focus(); return false;
		}
	</c:if>
		if($('#nttSj').val() == "") {
			var msg = "<s:message code="REQUIRED.TTL"/>' />"; /* 제목 */
			showMsgBox("ERR", msg); $('#nttSj').focus(); return false;
		}
		
		var msg = "<s:message code='CNF.UPDATE' />";
		showMsgBox("CNF", msg, 'UPDATE');
		return false;
		
}


//게시물 목록으로 이동
function select_noticeList() {
	document.board.action = "<c:url value='/commons/bbs/selectBoardList.do'/>";
	document.board.submit();
}


function fn_egov_check_file(flag) {
	if (flag=="Y") {
		document.getElementById('file_upload_posbl').style.display = "block";
		document.getElementById('file_upload_imposbl').style.display = "none";
	} else {
		document.getElementById('file_upload_posbl').style.display = "none";
		document.getElementById('file_upload_imposbl').style.display = "block";
	}
}

</script>
</head>
<body>


        <!-- 오른쪽 내용 시작 -->
        <div class="right">
        <div style="clear:both; height:5px;"><span></span></div>
<%--         	<div class="location"><img src='<c:url value="/img/location_home.gif"/>' alt="home">  &gt; ${bdMstr.bbsNm}</div> --%>
            <div class="stit"><s:message code="BULL.ARTICLE.REVS" /></div> <!-- 게시글 수정 -->
            <div style="clear:both; height:5px;"><span></span></div>
<%--             <form name="board" method="post" enctype="multipart/form-data"> --%>
			<form:form commandName="board" name="board" method="post" enctype="multipart/form-data" >
				<input type="hidden" name="pageIndex" value="<c:out value='${searchVO.pageIndex}'/>"/>
				<input type="hidden" name="returnUrl" value="<c:url value='/commons/bbs/forUpdateBoardArticle.do'/>"/>
				
				<input type="hidden" name="bbsId" value="<c:out value='${result.bbsId}'/>" />
				<input type="hidden" name="nttId" value="<c:out value='${result.nttId}'/>" />
				
				<input type="hidden" name="bbsAttrbCode" value="<c:out value='${bdMstr.bbsAttrbCode}'/>" />
				<input type="hidden" name="bbsTyCode" value="<c:out value='${bdMstr.bbsTyCode}'/>" />
				<input type="hidden" name="replyPosblAt" value="<c:out value='${bdMstr.replyPosblAt}'/>" />
				<input type="hidden" name="fileAtchPosblAt" value="<c:out value='${bdMstr.fileAtchPosblAt}'/>" />
				<input type="hidden" name="posblAtchFileNumber" value="<c:out value='${bdMstr.posblAtchFileNumber}'/>" />
				<input type="hidden" name="posblAtchFileSize" value="<c:out value='${bdMstr.posblAtchFileSize}'/>" />
				<input type="hidden" name="tmplatId" value="<c:out value='${bdMstr.tmplatId}'/>" />
				<input type="hidden" name="ntcrNm" value="dummy">	<!-- validator 처리를 위해 지정 -->
				<input type="hidden" name="password" value="dummy">	<!-- validator 처리를 위해 지정 -->
            <table border="0" cellspacing="0" cellpadding="0" class="tb_write" summary="" style="table-layout: fixed;">
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
              <c:if test="${bdMstr.bbsTyCode == 'BBST05' }">
              <tr>
                <th><s:message code="PRJT" /></th> <!-- 프로젝트 -->
                <td >
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
                <td colspan="3">
                	<select id="cateCode" name="cateCode">
                		<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
	                	<c:forEach var="code" items="${codeMap.cateCode}" varStatus="status">
	                    <option value="${code.codeCd}">${code.codeLnm}</option>
	                    </c:forEach>
                	</select>
                </td>
              </tr>
              <tr>
                <th><s:message code="BBS.SUBJECT" /></th>
                <td colspan="3"><input id="nttSj" name="nttSj" type="text" value="${result.nttSj}"></td>
              </tr>
              <tr>
                <th><s:message code="BBS.CONTENTS" /></th>
                <td colspan="3"><textarea class="ckeditor" id="nttCn" name="nttCn" accesskey="" style="width:96%; height:200px;"><c:out value="${result.nttCn}" escapeXml="false" /></textarea></td>
              </tr>
             <c:if test="${not empty result.atchFileId}">
              <tr>
                <th ><s:message code="FILE.LIST" /></th>
                <td colspan="3" >
                	<c:import url="/commons/fms/selectFileInfsForUpdate.do" charEncoding="utf-8">
					<c:param name="param_atchFileId" value="${result.atchFileId}" />
				</c:import>
                </td>
              </tr>
            </c:if>
            <c:if test="${bdMstr.fileAtchPosblAt == 'Y'}">
			  	<c:if test="${result.atchFileId == ''}">
			  		<input type="hidden" name="fileListCnt" value="0" />
			  	</c:if>
				  <tr>
				    <th height="23" class="bd_none"><s:message code="FILE.ATTACH" /></th>
				    <td colspan="3" class="bd_none">
				    <div id="file_upload_posbl"  style="display:none;" >
			            <table width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
						    <tr>
						        <td><input name="file_1" id="comFileUploader" type="file" title="<s:message code='ATFL.INPT' /> <!-- 첨부파일입력 --> "/></td> <!-- 첨부파일입력 -->
						    </tr>
						    <tr>
						        <td>
						        	<div id="comFileList"></div>
						        </td>
						    </tr>
			   	        </table>
					</div>
					<div id="file_upload_imposbl"  style="display:none;" >
			            <table width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
						    <tr>
						        <td><s:message code="FILE.OVERFLOW" /></td>
						    </tr>
			   	        </table>
					</div>
					</td>
				  </tr>
			</c:if>
            </table>
<%--             </form> --%>
            </form:form>
            <ul class="bt">
            	<li id="btn_list"><a  class="bt_gray"><s:message code="BTN.LIST"/></a></li>
            <c:if test="${bdMstr.authFlag == 'Y'}">
                <li id="btn_update"><a  class="bt_gray"><s:message code="BTN.SAVE"/></a></li>
            </c:if>
            </ul> 
            
        </div>
        <!-- 오른쪽 내용 끝 -->
</body>
</html>