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
<title><s:message code="STRD.DATA.DEMD" /></title> <!-- 표준데이터요청 -->
<style type="text/css">
	#divTabs-rqstvrf {display: block; margin-top:440px;}
</style>

<script type="text/javascript">

var grid_name ;

//쿠키 불러오기
function getCookie(name)
{
	var obj = name + "=";
	var x = 0;
	while ( x <= document.cookie.length )
	{
		var y = (x+obj.length);
		if ( document.cookie.substring( x, y ) == obj )
		{
			if ((endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
				endOfCookie = document.cookie.length;
			return unescape( document.cookie.substring( y, endOfCookie ) );
		}
		x = document.cookie.indexOf( " ", x ) + 1;

		if ( x == 0 ) break;
	}
	return "";
}


$(document).ready(function() {
	
	//탭 초기화....
	//$( "#tabs" ).tabs();

	$( "#divTabs-rqstvrf" ).tabs();

	if(getCookie("divpop1") !="Y"){
		openLayerPop("<c:url value='/commons/user/goNoticePop.do' />", 550, 190);
	}

	//정보변경 처리...
	var	flag = '${sessionScope.loginVO.chgPwd}';
	if (flag === 'N') {
		openLayerPop("<c:url value='/commons/user/gousrInfoChngPop.do' />", 550, 290);
	};

	//업무구분상세 초기화...
	//$("#mstFrm #bizDtlCd").val("${waqMstr.bizDtlCd}");
	$("#mstFrm #bizDtlCd").val("SDITM");
	
	$("[id$='-${waqMstr.bizDtlCd}'] a").click();
	
	$( "#tab-SDITM a" ).click(function(){
	      mstFrmReset("SDITM");
	      var retapproveItem =  grid_SDITM.RowCount();
// 	      console.log(retapproveItem);
	      if(retapproveItem <= 0){
	         setTimeout(function(){doAction("Search")},500);
	      }
	   });
	   $( "#tab-DMN a" ).click(function(){
	      mstFrmReset("DMN");
	      var retapproveDmn  =  grid_DMN.RowCount();
	      console.log(retapproveDmn);
	      if(retapproveDmn <= 0){
// 	         console.log("dd2");
// 	         console.log(retapproveDmn);
	         setTimeout(function(){doAction("Search")},500);
	      }
	   });
	   $( "#tab-STWD a" ).click(function(){
	      mstFrmReset("STWD");
	      var retapproveWord =  grid_STWD.RowCount();
// 	      console.log(retapproveWord);
	      if(retapproveWord <= 0){
// 	         console.log("dd");
	         setTimeout(function(){doAction("Search")},500);
	       }
	   });	
	$("#Msthidden").hide();

	
	// 엑셀내리기 Event Bind
    $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
	//엑셀 LOAD
    $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } );
	
	
	//추가
    $("#btnNew").click( function(){
    	doAction("New");
    } );
	
	//조회
    $("#btnSearch").click( function(){
    	doAction("Search"); 
    } ).show();
	
	//변경대상 추가
	$("#btnChangAdd").click(function(){
		doAction("AddWam");
	}).hide();
	
	//저장
    $("#btnSave").click( function(){
    	var bizDtlCd = $("form[name=mstFrm] #bizDtlCd").val();
    	if(bizDtlCd == "STWD"){
//         	//표준단어명 중복 저장 확인
// 			var row = grid_name.ColValueDup("stwdLnm");
// 			var rows = grid_name.ColValueDupRows("stwdLnm");
// 			if(row > -1){
// 			    showMsgBox("CNF", rows+"행에 "+"<s:message code="CNF.DUP.STWDLNM" />"+"</br>","Save");
// 				return;
// 			}
// 			//표준단어 영문약어 중복 저장 확인
// 			row = grid_name.ColValueDup("stwdPnm");
// 			rows = grid_name.ColValueDupRows("stwdPnm");
// 			if(row > -1){
// 			    showMsgBox("CNF", rows+"행에 "+"<s:message code="CNF.DUP.STWDPNM" />"+"</br>","Save");
// 				return;
// 			}

			doAction("Save");
    	}else{doAction("Save");}
    	    	
    } ).show();
	//검증
    $("#btnInspect").click( function(){
    	doAction("Save");
    } ).show();
	
 	 //확정
    $("#btnDecide").click( function(){
    	doAction("Decide");
    } ).show();

	 //초기화
    $("#btnInit").click( function(){
   		if(confirm("초기화 하시겠습니까?")){
   			doAction("Delete");
   		}
    } ).show();
    
    //삭제
    $("#btnDelete").click( function(){
    	doAction("Delete"); 
    } ).show();
    
    
    document.getElementById('btnDecide').disabled = true;
    document.getElementById('btnInit').disabled = true;
    //화면리로드
    $("#btnBlank").click( function(){
		location.href = '<c:url value="/dq/dbstnd/stndtot_rqst.do" />';
    } );
    
		
 // 결재 Event Bind
	$("#btnRegRqst").click(function(){
	
	}).hide();
 
	$("#divTabs-rqstvrf").hide();
	
	// 기관명 onChange Event
    //$("#orgNm").change(function(){

	setautoComplete($("#frmSearch #orgNm"), "ORGNM", 10);

	$("#frmSearch #orgNm").autocomplete({
		select: function (event, ui) {
			getOrgDbList();
		}
	});
	
});

$(window).load(function() {
	$("#btnRegRqst").hide();

	//프로파일별 그리드명 셋팅
	var bizDtlCd = $("form[name=mstFrm] #bizDtlCd").val();
// 	alert(bizDtlCd);
	if(bizDtlCd == "SDITM" || bizDtlCd == null){
		grid_name = grid_SDITM;
	}else if(bizDtlCd == "DMN"){
		grid_name = grid_DMN;
	}else if(bizDtlCd == "STWD"){
		grid_name = grid_STWD;
	}
// 	alert(grid_name);
	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
// 	if(rqststep !=  "A" ){
// 	if($("#mstFrm #aprLvl").val() > 0 ){
		setDispRqstMainButton(rqststep, grid_name);
// 	}

	//검토처리 버튼 보여주기....
// 	checkApproveYn($("#mstFrm"));

// 	setTimeOut
 	//doAction("Search");
 	/*
    setTimeout(function(){
    	 $( "#tab-SDITM a" ).click();
//     	 doAction("Search");
    },500);
 	*/
    	
});


$(window).resize(function() {
});

function getOrgDbList() {
	// 변경된 값으로 비교 후 alert 표출
	//if($(this).val() == ""){
	//    alert("onchange value " + $(this).val());
	//}
	var url = "";
	url = '<c:url value="/dq/dbstnd/getDbList.do"/>';

	var param = $("#frmSearch").serialize();

	$.ajax({
		url: url,
		async: false,
		type: "POST",
		data: param,
		dataType: 'json',
		success: function (data) {
			if(data){

				if (data.length > 0) {
					$('#dbNm').empty();

					var option = $("<option value=''>전체</option>");
					$('#dbNm').append(option);

					for (var i = 0; i < data.length; i++) {
						var option = $("<option value="+data[i].dbNm+">"+data[i].dbNm+"</option>");
						$('#dbNm').append(option);
					}
				}
			}
		},
		error: function (jqXHR, textStatus, errorThrown) {
			var res  = "";
			//시스템을 이용할수 없습니다.<br>관리자에게 문의하세요.
			res = {RESULT : {CODE:-1, MESSAGE: gMSG_SYS_NO_USE}};
			ibscallback(res);
		}
	});

};
//요청정보재조회
function getMstFrm(){
	
// 	var bizDtlCd = $("form[name=mstFrm] #bizDtlCd").val();
// 	if(bizDtlCd == "SDITM"){
// 		var urls = '<c:url value="/dq/dbstnd/getitemrqstlist.do"/>';
// 	}else if(bizDtlCd == "DMN"){
// 		var urls = '<c:url value="/dq/dbstnd/getdmnrqstlist.do"/>';
// 	}else if(bizDtlCd == "STWD"){
// 		var urls = '<c:url value="/dq/dbstnd/getstwdrqstlist.do"/>';
// 	}
	
// 	var param = $('form[name=mstFrm]').serialize();
// 	ajax2Json(urls, param, setMstFrm);
}

//재조회 된 요청정보 셋팅
function setMstFrm(data){
	json2formmapping($("form[name=mstFrm]"), data);
}

//텝 클릭시 요청정보 재조회
//단 등록완료 후 재조회 시 재조회 요청정보 셋팅
function mstFrmReset(bizDtlCd){
	$("form[name=mstFrm] #bizDtlCd").val(bizDtlCd);
	if("${waqMstr.bizDtlCd}" == $("form[name=mstFrm] #bizDtlCd").val() ){
		$("form[name=mstFrm]")[0].reset();
		$("form[name=mstFrm] #bizDcd").val("${waqMstr.bizDcd}");
		$("form[name=mstFrm] #rqstStepCd").val("${waqMstr.rqstStepCd}");
		$("form[name=mstFrm] #bizDtlCd").val("${waqMstr.bizDtlCd}");
	}else{
		//요청번호 신규 생성
// 		getMstFrm();
	}
}

function getDomainDataType(param, row) {

	$.ajax({
		url : '<c:url value="/dq/stnd/getDomainDataType.do"/>',
		type : "POST",
		async: false,
		dataType : "json",
		data : JSON.stringify(param),
		contentType : "application/json; charset=UTF-8",
		beforeSend : function() {
		},
		success : function(data) {
			console.log(data);
			if(data != null) {
				grid_name.SetCellValue(row,"dataType", data.DATA_TYPE);
				grid_name.SetCellValue(row,"dataLen", data.DATA_LEN);
			} else {
				grid_name.SetCellValue(row,"errChk", "표준도메인 오류");
			}
						
		},
		error : function(request, status, error) {
			alert("fail :: error code: "
			+ request.status + "\n" + "error message: "
			+ error + "\n");
		}
	});
}


function doAction(sAction)
{
	//프로파일별 그리드명 셋팅
	var bizDtlCd = $("form[name=mstFrm] #bizDtlCd").val();
	
	if(bizDtlCd == "SDITM"){
		grid_name = grid_SDITM;
	}else if(bizDtlCd == "DMN"){
		grid_name = grid_DMN;
	}else if(bizDtlCd == "STWD"){
		grid_name = grid_STWD;
	}
	
    switch(sAction)
    {
    	case "New":  //그리드추가
    		grid_name.DataInsert(0);
    		grid_name.SetCellValue(1,"orgNm", "${userOrg.orgNm}");
        	break;

	
    	case "LoadExcel":  //엑셀업로드
    		grid_name.LoadExcel({Mode:'HeaderMatch', Append:1});
        	break;
        
    	case "Down2Excel":  //엑셀다운로드
//     		if(grid_name.GetTotalRows() == 0 ){
//     			grid_name.DataInsert(0);
//     		}
    	    var fileName="DB";
    	    if(bizDtlCd == "SDITM"){
    	    	fileName = fileName+"표준용어.xlsx";
			}else if(bizDtlCd == "DMN"){
				fileName = fileName+"표준도메인.xlsx";
			}else if(bizDtlCd == "STWD"){
				fileName = fileName+"표준단어.xlsx";
			}
    	    
    	    var SaveJson = grid_name.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...
    	    if(SaveJson.data.length == 0) {
    	    	grid_name.DataInsert(0);
    	    	grid_name.SetCellValue(1,"orgNm", "${userOrg.orgNm}");
    	    }
    	    
    		grid_name.Down2Excel({HiddenColumn:1,Merge:1,Mode:2,FileName : fileName});
        	break;
        
		case "Search":
			if(frmSearch.orgNm.value == '') {
				showMsgBox("INF", "기관명을 입력하고 검색해 주세요.");
				return;
			}

			//프로파일별 url 셋팅
			var url = "";
			if(bizDtlCd == "SDITM"){
// 				url = '<c:url value="/dq/dbstnd/getitemrqstlist.do"/>';
				url = '<c:url value="/dq/dbstnd/getsditmlist.do"/>';
			}else if(bizDtlCd == "DMN"){
// 				url = '<c:url value="/dq/dbstnd/getdmnrqstlist.do"/>';
				url = '<c:url value="/dq/dbstnd/getDomainlist.do"/>';
			}else if(bizDtlCd == "STWD"){
// 				url = '<c:url value="/dq/dbstnd/getstwdrqstlist.do"/>';
				url = '<c:url value="/dq/dbstnd/getStndWordlist.do"/>';
			}
			
// 			var param = $("#mstFrm").serialize();
			var param = $("#frmSearch").serialize();
			grid_name.DoSearch(url, param);
			
			//전체 검증결과 조회 (rqstNo, bizDtlCd)
// 			console.log(param);
// 			getRqstVrfLst(param);
			break;
			
    	case "Save":  //검증
    		var len = grid_name.RowCount();
    		
    		//저장 대상의 데이터를 Json 객체로 반환한다.
			ibsSaveJson = grid_name.GetSaveJson(0);
    	
    		//2. 필수입력 누락인 경우
			if (ibsSaveJson.Code == "IBS010") return;
			
			if(ibsSaveJson.data.length == 0){
				showMsgBox("INF", "<s:message code="ERR.CHKSAVE" />");
				return;
			}

			//프로파일별 url 셋팅
			var url = "";
			
			url = '<c:url value="/dq/dbstnd/regitemWamlist.do"/>';
			
			var param = $('form[name=mstFrm]').serialize();
	        IBSpostJson2(url, ibsSaveJson, param, ibscallback);
	        
        	break;
    	case "Decide":  //확정
    		var len = grid_name.RowCount();
    		
    		//저장 대상의 데이터를 Json 객체로 반환한다.
			ibsSaveJson = grid_name.GetSaveJson(0);
    	
    		//2. 필수입력 누락인 경우
			if (ibsSaveJson.Code == "IBS010") return;
			
			if(ibsSaveJson.data.length == 0){
				showMsgBox("INF", "<s:message code="ERR.CHKSAVE" />");
				return;
			}

			//프로파일별 url 셋팅
			var url = "";
			url = '<c:url value="/dq/dbstnd/decideItemWam.do"/>';
				
			
			
			
			var param = $('form[name=mstFrm]').serialize();
	        IBSpostJson2(url, ibsSaveJson, param, ibscallback);
	        
        	break;
       
    	case "Init":  //초기화
    		//저장 대상의 데이터를 Json 객체로 반환한다.
			ibsSaveJson = grid_name.GetSaveJson(0);
    	
    	
			//프로파일별 url 셋팅
			var url = "";
			url = '<c:url value="/dq/dbstnd/InitItemWam.do"/>';
			
			var param = $('form[name=mstFrm]').serialize();
	        IBSpostJson2(url,ibsSaveJson, param, ibscallback);
	        
        	break;
        	
    	case "Delete" :
			//체크박스가 입력상태인 경우 삭제...
			if(!grid_name.CheckedRows("ibsCheck")) {
				//삭제할 대상이 없습니다...
				showMsgBox("ERR", "<s:message code="ERR.CHKDEL" />");
				return;
			}
        	
			var url = "";
			if(bizDtlCd == "SDITM"){
// 				url = '<c:url value="/dq/dbstnd/delSditmrqstlist.do"/>';
				url = '<c:url value="/dq/dbstnd/delitemWamlist.do"/>';
			}else if(bizDtlCd == "DMN"){
// 				url = '<c:url value="/dq/dbstnd/deldmnrqstlist.do"/>';
				url = '<c:url value="/dq/dbstnd/deldmnWamlist.do"/>';
			}else if(bizDtlCd == "STWD"){
// 				url = '<c:url value="/dq/dbstnd/delstwdrqstlist.do"/>';
				url = '<c:url value="/dq/dbstnd/delstwdwamlist.do"/>';
			}
			
			//삭제로직 김경택
			//모든 Row에서 Check 된 것을 저장한 뒤에
			//(if)체크 된 것 중 입력중인 내용만 있으면 Row만 삭제하고 BackEnd 타지않음
			//(else if)체크 된 것 중 vrfCd! = 4 인 것만 있으면, 즉 한번이라도 검증을 탔으면 Front에서  Param 수집 후 Front상의 Row를 삭제한 뒤 BackEnd로 넘어가서 WAQ에서 삭제
			//(else)체크 된 것 중 입력중인 상태와 vrfCd(검증상태)가 4(검증전)가 아닌 것이 동시에 있으면, 
			//입력중인 상태의 Row를 먼저 삭제하고, Param 수집 후 Front에서 Row를 삭제한 뒤에 BackEnd로 넘어가서 WAQ에서 삭제함
			//이 모든 과정은 화면 새로고침을 하지 않음.
			var empty_Row_Location = new Array();
			var vrfed_Row_Location = new Array();
			
			for(var i = 1 ; i <= grid_name.GetDataLastRow(); i++) {
				if(grid_name.GetCellValue(i,'ibsCheck') == '1' && grid_name.GetCellValue(i,'ibsStatus') == 'I') {
					empty_Row_Location.push(i)
				}else if(grid_name.GetCellValue(i,'ibsCheck') == '1' && grid_name.GetCellValue(i,'ibsStatus') != 'I'){
					vrfed_Row_Location.push(i)
				} 
			}

			if (empty_Row_Location.length != 0 && vrfed_Row_Location.length == 0){
				var delete_Row = '' 
										
				for(var i = 1; i <=empty_Row_Location.length; i++){
					if (delete_Row == ''){
						delete_Row += ''+empty_Row_Location[i-1]
					}else{
						delete_Row += '|'+empty_Row_Location[i-1]
					}
				}
				grid_name.RowDelete(delete_Row, 0);
				
				empty_Row_Location.clear
				vrfed_Row_Location.clear
			}else if(empty_Row_Location.length == 0 && vrfed_Row_Location.length != 0){
				var DelJson = grid_name.GetSaveJson({AllSave:0, StdCol:"ibsCheck", ValidKeyField :0});
				var param = $("#mstFrm").serialize();
				
				var delete_Row = '' 
					
				for(var i = 1; i <=vrfed_Row_Location.length; i++){
					if (delete_Row == ''){
						delete_Row += ''+vrfed_Row_Location[i-1]
					}else{
						delete_Row += '|'+vrfed_Row_Location[i-1]
					}
				}
				grid_name.RowDelete(delete_Row, 0)
				
				empty_Row_Location.clear
				vrfed_Row_Location.clear
				IBSpostJson2(url, DelJson, param, ibscallback)
				
			}else{
				var delete_Row = '' 
					
				for(var i = 1; i <=empty_Row_Location.length; i++){
					if (delete_Row == ''){
						delete_Row += ''+empty_Row_Location[i-1]
					}else{
						delete_Row += '|'+empty_Row_Location[i-1]
					}
				}
				grid_name.RowDelete(delete_Row, 0);
				
				var DelJson = grid_name.GetSaveJson({AllSave:0, StdCol:"ibsCheck", ValidKeyField :0});
				var param = $("#mstFrm").serialize();
				
				var delete_Row = ''
				
				for(var i = 1; i <=grid_name.GetDataLastRow(); i++){
					if(grid_name.GetCellValue(i,'ibsCheck') == '1') {
						if (delete_Row == ''){
							delete_Row += ''+i
						}else{
							delete_Row += '|'+i
						}
					}
				}
				grid_name.RowDelete(delete_Row, 0);				
				
				empty_Row_Location.clear
				vrfed_Row_Location.clear
				IBSpostJson2(url, DelJson, param, ibscallback);
			}
    }       
}
 

/*======================================================================*/
//IBSpostJson2 후처리 함수
/*======================================================================*/
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
			if(!isBlankStr(res.resultVO.rqstNo)) {
	    		json2formmapping ($("#mstFrm"), res.resultVO);
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
	    		$("#mstFrm #bizDtlCd").val(res.resultVO.bizDtlCd);
	    		
	    		if ($("#mstFrm #rqstStepCd").val() == "S")  {
// 	    			$("#btnRegRqst").show();
	    		}
	    		//doAction("Search"); 		
	    	} 
			break;
			
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			break;
		
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
			//저장완료시 마스터 정보 셋팅...
	    	 if(!isBlankStr(res.resultVO.rqstNo)) {

// 	    		alert(res.resultVO.rqstNo);
	    		json2formmapping ($("#mstFrm"), res.resultVO);
	    		
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
	    		$("#mstFrm #bizDtlCd").val(res.resultVO.bizDtlCd);
	    		
	    		//등록요청 버튼 활성화
	    		if ($("#mstFrm #rqstStepCd").val() == "S")  {
//   					$("#btnRegRqst").show();
	    		} 
  				doAction("Search"); 
	    	} 
			
			break;
				
	
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
}

</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="PROF.EXCL.REG" /></div><!--프로파일 엑셀등록-->

	</div>
</div>


<div style="clear:both; height:10px;"><span></span></div>
<!-- 메뉴 메인 제목 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='BFHD.INTG.INQ' />"> <!-- 사전통합조회 -->
                   <caption><s:message code="BFHD.INTG.INQ.FORM" /></caption> <!-- 사전통합 검색폼 -->
                   <colgroup>
                   <col style="width:10%;" />
                   <col style="width:20%;" />
                   <col style="width:10%;" />
                   <col style="width:10%;" />
                   <col style="width:10%;" />
                   <col style="width:10%;" />
                   <col style="width:10%;" />
                   <col style="width:20%;" />
                   </colgroup>
                   
                   <tbody>      
                            <tr>
                             
					         <th scope="row"><label for="orgNm">기관명</label></th> <!-- 사전유형 -->
                                <td >
									<input type="text" id="orgNm" name="orgNm" class="wd200" value="${orgNm}"
									placeholder="기관명을 입력해주세요."/>

                                <%--<select id="orgNm" class="" name="orgNm">
                                	<option value="">전체</option>
		 							<c:forEach var="userOrgList" items="${userOrgList}" varStatus="status">
		 							  <option value="${userOrgList.codeLnm}">${userOrgList.codeLnm}</option>
		 							</c:forEach> 
	 					 		</select>--%>
	 					 		
								</td>
								
							<th scope="row"><label for="dbNm">DB명</label></th> <!-- 사전유형 -->
                            <td >
                                <select id="dbNm" class="" name="dbNm">
                                	<option value="">전체</option>
	 							<c:forEach var="userDbList" items="${userDbList}" varStatus="status">
	 							  <option value="${userDbList.dbNm}">${userDbList.dbNm}</option>
	 							</c:forEach> 
	 					 		</select> 
							</td>
							
							<th scope="row"><label for="dbNm">검증 여부</label></th> <!-- 사전유형 -->
                            <td >
                                <select id="vcWh" class="" name="vcWh" style ="width:100%;">
                                  <option value="">전체</option>
	 							  <option value="E">검증오류</option>
	 							  <option value="Y">검증성공</option>
	 							  <option value="N">미검증</option>
	 							  <option value="YY">확정</option>
	 					 		</select> 
							</td>
							
                                <th scope="row"><label for="stndNm">표준용어명</label></th> <!-- 표준사전명 -->
                                <td><input type="text" id="stndNm" name="stndNm" class="wd98p" value="${stndNm}" /></td>
<%--                                 <th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 --> --%>
<!--                                 <td><input type="text" id="objDescn" name="objDescn" class="wd98p"/></td> -->
                            </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
<%--			<input type="hidden" id="chgPwd" value="<%=sessionScope.loginVO.chgPwd%>" />--%>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
</div>
<%-- <tiles:insertTemplate template="/WEB-INF/decorators/buttonRqst.jsp" /> --%>
		<div class="divLstBtn" style="display: none;">  
            <div class="bt03">
			    <button class="btn_search" id="btnSearch" 	name="btnSearch"><s:message code="BTN.READ" /></button> <!-- 전체조회 -->
			    
			    <c:if test="${sessionScope.loginVO.usergId eq 'OBJ_00000034587'}">
    				<button class="btn_rqst_new" id="btnRqstNew" name="btnRqstNew"><s:message code="ADDT" /></button> <!-- 추가 -->                                                         
					  <ul class="add_button_menu" id="addButtonMenu">
					    <li class="btn_new" id="btnNew"><a><span class="ui-icon ui-icon-pencil"></span><s:message code="NEW.ADDT" /></a></li> <!-- 신규 추가 -->
					    <li class="btn_chang_add" id="btnChangAdd"><a><span class="ui-icon ui-icon-folder-open"></span><s:message code="CHG.TRGT.ADDT" /></a></li> <!-- 변경대상 추가 -->
					    <li class="btn_excel_load" id="btnExcelLoad"><a><span class="ui-icon ui-icon-document"></span><s:message code="EXCL.UPLOAD" /></a></li> <!-- 엑셀 올리기 -->
					  </ul>         
				    <%-- <button class="btn_save" id="btnSave" 	name="btnSave"><s:message code="STRG" /></button> --%> <!-- 저장 --> 
				    <button class="btn_delete"  id="btnDelete" 	name="btnDelete"><s:message code="DEL" /></button> <!-- 삭제 -->
					<button class="btn_inspect" id="btnInspect" name="btnInspect" >검증</button>
					<button class="btn_decide"  id="btnDecide" 	name="btnDecide">확정</button>
					<button class="btn_init"    id="btnInit" 	name="btnInit">초기화</button>
				</c:if>
				
			</div>
			<div class="bt02"> 
	          <button class="btn_excel_down"  id="btnExcelDown"  name="btnExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                       
	    	</div>
        </div>	
        
<div style="clear:both; height:5px;"><span></span></div>

	<div id="tabs">
	  
	  <div id="tabs-SDITM">
			<div id="detailInfoSDITM"><%@include file="exl/dbstnditem_exl.jsp" %></div>
	  </div>
	  
	 </div>

	<div style="clear:both; height:20px;"><span></span></div>
	<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->
	
	<div id="divTabs-rqstvrf">
		<ul>
			<li id="tabs-rqstvrf"><a href="#tab-rqstvrf"><s:message code="VRFC.RSLT" /></a></li><!--검증결과-->
		</ul>
		<div id="tab-rqstvrf" style="display:none;">
			<%@include file="../../commons/rqstmst/rqstvrf_lst.jsp" %>
		</div>
	</div>
	
	
	<div id = "Msthidden" style="display:none;">
	<form name="mstFrm" id="mstFrm">
				<!-- 전체승인 및 전체반려시 승인/반려 및 반려 사유를 마스터에서 처리한다. -->
				<input type="hidden" name="rvwStsCd" id="rvwStsCd">
				<input type="hidden" name="rvwConts" id="rvwConts">
				<input type="hidden" name="rqstUserId" id="rqstUserId" value="${waqMstr.rqstUserId}" />
				
			    <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
					<caption><s:message code="SUBJ.TRRT.INFO" /></caption> <!-- 주제영역정보 -->
					<colgroup>
<%-- 					<col style="width:15%;" /> --%>
<%-- 					<col style="width:85%;" /> --%>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row"><label for="rqstNo"><s:message code="DEMD.NO" /></label></th> <!-- 요청번호 -->
							<td><input type="text" name="rqstNo" id="rqstNo" class="wd98p" value="${waqMstr.rqstNo}" /></td>
						</tr>
						<tr>
							<th scope="row"><label for="rqstNm"><s:message code="LORQ.NM" /></label></th> <!-- 요청서명 -->
							<td><input type="text" name="rqstNm" id="rqstNm" class="wd98p" value="${waqMstr.rqstNm}" /></td>
						</tr>
						<tr>
							<th scope="row"><label for="bizDcd"><s:message code="BZWR.DSTC" /></label></th> <!-- 업무구분 -->
							<td>
							<!-- bizDcd를 selectBox로만 받을경우 stndtot_rqst에서 null로 인식하여 문제발생. 그래서 hidden값으로 추가 -->
							<input type="hidden" id="bizDcd" 	name="bizDcd" 	value="${waqMstr.bizDcd}" />
							<input type="text" id="bizDcdNm" 	name="bizDcdNm" 	value="${waqMstr.bizDcdNm}" />
<%-- 							<select id="bizDcd" class="" name="bizDcd"> --%>
<%-- 							<c:forEach var="code" items="${codeMap.bizDcd}" varStatus="status"> --%>
<%-- 							  <option value="${code.codeCd}">${code.codeLnm}</option> --%>
<%-- 							</c:forEach> --%>
<%-- 					 		</select> --%>
							</td>
						</tr>
						<tr style="display:none">
							<th scope="row"><label for="bizDtlCd"><s:message code="BZWR.DSTC.DTL" /></label></th> <!-- 업무구분상세 -->
							<td><input type="text" name="bizDtlCd" id="bizDtlCd" class="wd98p" value="" /></td>
						</tr>
						<tr>
							<th scope="row"><label for="rqstStepCd"><s:message code="DEMD.PRGS.STS" /></label></th> <!-- 요청진행상태 -->
							<td>
							<input type="hidden" id="rqstStepCd" 	name="rqstStepCd" 	value="${waqMstr.rqstStepCd}" />
							<input type="text" id="rqstStepCdNm" 	name="rqstStepCdNm" 	value="${waqMstr.rqstStepCdNm}" />
<%-- 							<select id="rqstStepCd" class="" name="rqstStepCd"> --%>
<%-- 							<c:forEach var="code" items="${codeMap.rqstStepCd}" varStatus="status"> --%>
<%-- 							  <option value="${code.codeCd}">${code.codeLnm}</option> --%>
<%-- 							</c:forEach> --%>
<%-- 					 		</select> --%>
					 		</td>
						</tr>
						<tr>
							<th scope="row"><label for="rqstResn"><s:message code="DEMD.RSN" /></label></th> <!-- 요청사유 -->
							<td><input type="text" name="rqstResn" id="rqstResn" class="wd98p" value="${waqMstr.rqstResn}" /></td>
						</tr>
						<tr style="display:none">
							<th scope="row"><label for="rqstResn"><s:message code="APRL.STEP" /></label></th> <!-- 결재단계 -->
							<td><input type="text" id="aprLvl" 	name="aprLvl" 	value="${waqMstr.aprLvl}" /></td>
						</tr>
					</tbody>
				</table>
			</form>
	</div>

</body>
</html>