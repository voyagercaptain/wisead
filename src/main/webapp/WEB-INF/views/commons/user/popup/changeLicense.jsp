<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<html>
<head>
<title>라이센스 등록</title><!-- 데이터품질지표 조회 -->

<script type="text/javascript">

$(document).ready(function() {
	//팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
	$("div.pop_tit_close").click(function(){
	 	//iframe 형태의 팝업일 경우
	 	if ("${search.popType}" == "I") {
	 		parent.closeLayerPop();
	 	} else {
	 		window.close();
	 	}
	});
	
	$("#popApply").click(function() {
		doAction("Apply");
		return false;
	}).show();
	
	$("#macAddr").val('${macAddr}');
});


$(window).load(function() {
});



$(window).resize(function(){
});

function callback(resMsg) {
	$("#divMsgPopup").dialog("close");
	
	if(resMsg == "save") {
		showMsgBox("INF", "라이센스가 등록되었습니다.", "Close");
		//$("div.pop_tit_close").click();
	} else if(resMsg == "error") {
		showMsgBox("ERR", "라이센스 등록에 실패하였습니다.");
	}
}

function ajax2Json_license(urls, param, callback, syncyn) {
	var ajaxurl = urls;
	var syn = false;
	if (syncyn) syn = syncyn;
	$.ajax({
		url: ajaxurl,
		async: syn,
		type: "POST",
		data: param,
		dataType: 'json',
		beforeSend: function () {
			// 처리중이니 잠시 기다려 주십시요.
			showMsgBox("PRC", gMSG_PRC_WAIT);
		},
		success: callback
	});
}

function doAction(sAction)
{
    switch(sAction)
    {
        case "Apply":
        	var param = $("#frmLicense").serialize();
        	var url = "<c:url value='/changeLicense.do' />";

        	ajax2Json_license(url, param, callback);
        	
        	break;
        	
        case "Close":
        	$("div.pop_tit_close").click();
        	break;
    }       
}
</script>
</head>

<body>
<div class="pop_tit" >
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt">라이센스 등록</div><!-- 데이터품질지표 조회 -->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div><!--창닫기-->


</div>
    <!-- 팝업 타이틀 끝 -->

    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
		<!-- 검색조건 입력폼 -->
		<div id="search_div">
	        <div style="clear:both; height:5px;"><span></span></div>
	        
	        <form id="frmLicense" name="frmLicense" method="post">
		        <fieldset>
					<legend>
						라이센스 등록
					</legend>
					<!--머리말-->
					<div class="tb_basic2">
						<table summary="라이센스 등록">
							<caption>
								<s:message code="CLMN.PROF.MNG" />
							</caption>
							<!--컬럼프로파일관리-->
	
							<colgroup>
								<col style="width: 15%;" />
								<col style="width: 70%;" />
								<col style="width: 15%;" />
							</colgroup>
	
							<tbody>
								<tr>
									<th scope="row" class="th_require"><label for="licenseKey">라이센스</label></th>
									<!--진단대상명-->
									<td><input type="text" name="licenseKey" id="licenseKey" style="width:98%;" /></td>
									<td style="text-align:center;"><button class="btn_apply"  id="popApply" name="popApply"><s:message code="APL" /></button> <!-- 적용 --></td>
								</tr>
							</tbody>
						</table>
					</div>
				</fieldset>
				</form>
          </div>
			<div style="clear:both; height:10px;"><span></span></div>
			
			<div class="lg_foot">
				<div class="lg_foot_tit"><s:message code="DQ.USE.NOTI"/></div> <!-- 메타관리시스템  이용관련 안내 -->
			    <div class="lg_foot_cont">
			    	<ul>
			        	<li>관리자에게 Mac Address를 보내 라이센스 관련 문의를 주세요.</li> 
						<li>Mac Address : &nbsp;<input type="text" name="macAddr" style="color:blue;text-align:center;" id="macAddr" readOnly /></li> 
			        </ul>
			    </div>
			</div>
		</div>
</body>
</html>