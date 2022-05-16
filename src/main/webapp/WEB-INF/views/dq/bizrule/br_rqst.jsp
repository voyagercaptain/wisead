<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<html>
<head>
<title><s:message code="BZWR.RULE.REG.DEMD" /></title>
<!--업무규칙 등록요청-->


<script type="text/javascript">

$(document).ready(function() {

	//업무구분상세 초기화...
	$("#mstFrm #bizDtlCd").val('BRA');
	
	//그리드 초기화 
 	initGrid();
	
 	//필수입력항목입니다. 내용을 입력해 주세요. 
// 	var requiremessage = "<s:message code="VALID.REQUIRED" />";
	var requiremessage = "<s:message code='VALID.SHORTREQUIRED' />";
	
	//폼검증
	$("#frmDetail").validate({
		rules: {
			brNm		: "required",
			dqiLnm 	    : "required",
			rqstDcd     : "required",
			dbConnTrgId : "required",
			dbSchId     : "required",
			dbcTblNm    : "required",
			dbcColNm    : "required"
		},
		messages: {

			brNm	    : requiremessage,
			dqiLnm 	    : requiremessage,
			rqstDcd     : requiremessage,
			dbConnTrgId : requiremessage,
			dbSchId     : requiremessage,
			dbcTblNm    : requiremessage,
			dbcColNm    : requiremessage
		}
	});
	
	
	//조회
	$("#btnSearch").click(function(){
		
		$('#frmDetail')[0].reset();
		$('#bizrule_sel_title').html(null);
		
		doAction("Search");
	});
	
	
	// 등록요청 Event Bind
	$("#btnSave").click(function(){
		showMsgBox("CNF", "<s:message code='CNF.SUBMIT' />", 'AddRow');
	}).hide();	
	
	//변경대상 추가
	$("#btnChangAdd").click(function(event){
		event.preventDefault();	//브라우저 기본 이벤트 제거...ssss
		//팝업 flag 값 설정
		doAction("AddWam");
	});
	
	$("#btnExcelLoad").hide();
	
	//화면리로드
    $("#btnBlank").click( function(){
		location.href = '<c:url value="/dq/bizrule/bizrule_rqst.do" />';
    } );
	
	
	
	//신규버튼
	$("#btnBlank").click(function(){
		location.href = '<c:url value="/dq/bizrule/bizrule_rqst.do" />';
	});
	
			
	// 추가(신규) Event Bind
	$("#btnRqstNew").click(function(){ doAction("New");  });
	
	// 추가(엑셀업로드)Event Bind
	$("#btnExcelLoad").click( function(){ doAction("LoadExcel"); });
	
	// 삭제 Event Bind
	$("#btnDelete").click(function(){ 
		//선택체크박스 확인 : 삭제할 대상이 없습니다..
		if(checkDelIBS (grid_sheet, "<s:message code='ERR.CHKDEL' />")) {
			//삭제 확인 메시지
			showMsgBox("CNF", "<s:message code='CNF.DEL' />", 'Delete');
    	}
	});
	
	// 엑셀내리기 Event Bind
	$("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
	
		  
	// 폼저장이벤 Event Bind
	$("#btnGridSave").click( function(){ 
		
		//IBSheet 저장용 JSON 전역변수 초기화
		ibsSaveJson = null;

		if(!$("#frmDetail").valid()) return; 
		
		//저장할래요? 확인창...
		var message = "<s:message code='CNF.SAVE' />";
		showMsgBox("CNF", message, 'AddRow');
		
	} );

	// 폼초기화 버튼 Event Bind
	$("#btnReset").click( function(){ 
		/* resetForm($("form#frmDetail"));
		resetForm($("form#frmDbDetail"));
		resetForm($("form#frmVrtDetail")); */
		doAction("New");
	} );

	$("#btnGridSave1").click( function(){ 
		$("#btnGridSave").click(); 
	} );
	

	$("#btnGridSave2").click( function(){ 
		$("#btnGridSave").click(); 
	} );
	
	
	$("#btnReset1").click( function(){ 
		$("#btnReset").click(); 
	} );
	$("#btnReset2").click( function(){ 
		$("#btnReset").click(); 
	} );
	
	
	$("#valid_bizrule").show();
	$("#buttonRqst").show();
	$("#buttonRqstDbDetail").show();
	$("#buttonRqstVrtDetail").show();
	
	//임시 메뉴목록 등장 함수
	var val = $("#frmSearch #dbConnTrgLnm option:selected").val();
	var trgId = ${codeMap.devConnTrgSch} ;
	//$("#frmSearch #dbConnTrgId").append('<option value=""></option>');
	
	for(i=0; i<trgId.length; i++) {
		if(trgId[i].upcodeCd == val) {
			$("#frmSearch #dbConnTrgLnm").append('<option value="' + trgId[i].codeCd + '">' + trgId[i].codeLnm + '</option>');
		}
	}
	
	
	$("#frmSearch #dbConnTrgLnm").change(function() {
		$("#frmSearch #dbSchLnm").find("option").remove().end();
		var val = $("#dbConnTrgLnm option:selected").val();

		$("#frmSearch #dbSchLnm").append('<option value=""><s:message code="CHC" /></option> ');
		
		for(i=0; i<trgId.length; i++) {
			if(trgId[i].upcodeCd == val && val!="") {
				$("#frmSearch #dbSchLnm").append('<option value="' + trgId[i].codeCd + '">' + trgId[i].codeLnm + '</option>');
			}
		}
	});
	
	$("#btnSearch").click();
	
});

$(window).load(function() {
 	initGrid();
	var rqststep = $("#mstFrm #rqstStepCd").val();
	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
	setDispRqstMainButton(rqststep, grid_sheet);
	
	//검토처리 버튼 보여주기....
	checkApproveYn($("#mstFrm"));
	
	//dtl폼의 readonly속성 해제/적용 및 required옵션 설정 수행...
	readonlyCheck(rqststep);
	
	$("#isNew").val("I");

	//분석실행
	$("#btnExec").click(function(){
		regScheduler();
	}).show();
});



$(window).resize(function() {
});

function initGrid()
{
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);

        var headers = [
                    {Text:"<s:message code='DQ.HEADER.BR.RQST'/>"
                    	,Align:"Center"}                    	
                ];
                /* No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과|"
                   	+"업무영역ID|업무영역명|업무규칙ID|업무규칙명|업무규칙담당자ID|업무규칙담당자|"
                    	+"진단대상ID|진단대상명|테이블명|컬럼명|품질지표ID|품질지표명|중요정보항목ID|중요정보항목명|사용여부|설명|건수SQL|분석SQL|"
                    	+"등록번호|등록일련번호|등록요청자 */

        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        
        var cols = [                        
                    {Type:"Seq",    	Width:30,   SaveName:"ibsSeq",      Align:"Center", Edit:0, Hidden:0},
                    {Type:"Status", 	Width:30,   SaveName:"ibsStatus",   Align:"Center", Edit:1, Hidden:0},
                    {Type:"CheckBox", Width:50,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
					{Type:"Combo",  Width:80,  SaveName:"regTypCd",	Align:"Center", Edit:0, Hidden:1},
					
                    {Type:"Text",   	Width:100,  SaveName:"bizAreaId",    	Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Text",   	Width:100,  SaveName:"bizAreaLnm",    	Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Text",   	Width:100,  SaveName:"brId",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,  SaveName:"brNm",   	Align:"Left", Edit:0, Hidden:0, KeyField:1},
                    {Type:"Text",   	Width:100,  SaveName:"brCrgpUserId",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,  SaveName:"brCrgpUserNm",   	Align:"Left", Edit:0, Hidden:1},
                    
                    {Type:"Text",   	Width:50,  SaveName:"dbConnTrgId",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,  SaveName:"dbConnTrgPnm",   	Align:"Center", Edit:0, Hidden:0, KeyField:1},
                    {Type:"Text",   	Width:50,  SaveName:"dbSchId",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,  SaveName:"dbSchPnm",   	Align:"Center", Edit:0, Hidden:0, KeyField:1},
                    {Type:"Text",   	Width:100,   SaveName:"dbcTblNm", 	Align:"Left", Edit:0, Hidden:0, KeyField:1},
                    {Type:"Text",   	Width:100,   SaveName:"dbcColNm", 	Align:"Left", Edit:0, Hidden:0, KeyField:1},
                    {Type:"Text",   	Width:70,   SaveName:"dqiId", 	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,   SaveName:"dqiLnm", 	Align:"Left", Edit:0, Hidden:0, KeyField:1},
                    {Type:"Text",   	Width:70,   SaveName:"ctqId", 	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,   SaveName:"ctqLnm", 	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:60,   SaveName:"useYn", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:30,   SaveName:"objDescn", 	Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:100,   SaveName:"cntSql", 	Align:"Center", Edit:0, Hidden:0, KeyField:1},
                    {Type:"Text",   	Width:100,   SaveName:"anaSql", 	Align:"Center", Edit:0, Hidden:0, KeyField:1},
                    
                    {Type:"Text",   Width:100,  SaveName:"rqstNo",      Align:"Center", Edit:0 ,Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"rqstSno",     Align:"Center", Edit:0 ,Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"rqstUserNm",  Align:"Left", Edit:0 ,Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"shdKndCd",    Align:"Left", Edit:0 ,Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"shdJobId",    Align:"Left", Edit:0 ,Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"etcJobNm",    Align:"Left", Edit:0 ,Hidden:1},
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);
        
        InitComboNoMatchText(1, "");
      
        FitColWidth();
        
        SetColProperty("rqstDcd", ${codeMap.rqstDcdibs});
		SetColProperty("regTypCd", ${codeMap.regTypCdibs});
		SetColProperty("vrfCd", ${codeMap.vrfCdibs});
		SetColProperty("rvwStsCd", ${codeMap.rvwStsCdibs});
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}

/*======================================================================*/
//IBSpostJson2 후처리 함수
/*======================================================================*/
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
//	alert(res.action);
	if ('<%=WiseMetaConfig.RqstAction.APPROVE%>' != res.action) {
		showMsgBox("INF", res.RESULT.MESSAGE);
	}
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
    		
    		/* if ($("#mstFrm #rqstStepCd").val() == "S")  {
    			$("#btnRegRqst").show();
    		} */
    		//setDispRqstMainButton($("#mstFrm #rqstStepCd").val(), grid_sheet);
			doAction("Search");
			break;
			
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			showMsgBox("INF", res.RESULT.MESSAGE);
			doAction("Search");
			break;
		
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			doAction("Search");
			break;
		
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
			//저장완료시 마스터 정보 셋팅...
	    	 if(!isBlankStr(res.resultVO.rqstNo)) {
	    		json2formmapping ($("#mstFrm"), res.resultVO);

	    		/* if ($("#mstFrm #rqstStepCd").val() == "S")  {
	    			$("#btnRegRqst").show();
	    		} */
	    		//setDispRqstMainButton($("#mstFrm #rqstStepCd").val(), grid_sheet);
				doAction("Search");    		
	    	} 
			
			break;
			
		//요청서 결재단계별 승인 완료 후처리
		case "<%=WiseMetaConfig.RqstAction.APPROVE%>":
			var url = '<c:url value="/dq/bizrule/bizrule_rqst.do" />';
			var param = $('form[name=mstFrm]').serialize();
			location.href = url +"?"+param;
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
	
}

//업무규칙 상세 팝업
function brDtlPopup(param){
	
  	var url = '<c:url value="/dq/bizrule/popup/bizrule_dtl_pop.do"/>';

	var popwin = OpenModal(url+"?"+param, "bizruleDtlPop",  1300, 400, "yes"); 
	
	popwin.focus();
}

function doAction(sAction)
{
        
    switch(sAction)
    {
	    case "Search":
			var param = $("#frmSearch").serialize();
			
	    	grid_sheet.DoSearch("<c:url value="/dq/bizrule/getbizrulelist.do" />", param);
	    	break;
	    	
	    case "LoadExcel":  //엑셀업로드 팝업 호출
			var url = "<c:url value="/dq/bizrule/popup/bizrule_xls.do" />";
			openLayerPop(url, 800, 600);
			
			break;
	        
		case "Down2Excel":  //엑셀내려받기
			grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
			break;
			
		case "AddWam":
			var url = '<c:url value="/dq/bizrule/popup/bizrule_pop.do"/>';
			doAction("New");

			var param = "sflag=CHG"; 
			openLayerPop(url, 800, 600, param);
			break;
            
  		case "New": //새건 추가
  			
  			$("#isNew").val("I");
  			$("#selectRow").val("");
  			
  			$("#frmDetail")[0].reset();
//   			$("#frmDbDetail")[0].reset();
  			
  			//$('#frmVrtDetail')[0].reset();
  			$('#bizrule_sel_title').html(null);
  			
  			break;
  			
  		//등록가능 일 경우 WAM 영역 저장
  			//차후 결재로직 개발 완료 후 요청 구현
		case "submitBizruleLst":  
			//결재자 팝업 호출.....
			var param = $("#mstFrm").serialize();
			doRequest(param);
		        
			break;

		case "Delete" :
			//테크박스가 입력상태인 경우 삭제...
			if(!grid_sheet.CheckedRows("ibsCheck")) {
				//삭제할 대상이 없습니다...
				showMsgBox("ERR", "<s:message code="ERR.CHKDEL" />");
				return;
			}
			
			//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	
			ibsSaveJson = grid_sheet.GetSaveJson(0, "ibsCheck");
			
			var url = "<c:url value="/dq/bizrule/delBrList.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
			
			//폼 초기화
        	$('#frmDetail')[0].reset();
//   			$('#frmDbDetail')[0].reset();
			
			break;
			
		case "Approve" : //결재처리
			
			//프로파일 검토상태코드 승인으로 변경
			//결재프로세스 태울경우 제거
			if($("form[name=mstFrm] #aprLvl").val() == 0){
				doAllApprove(grid_sheet, "1");
			}
		
			var saveJson = grid_sheet.GetSaveJson(0);
			
			//2. 필수입력 누락인 경우
			if (saveJson.Code == "IBS010") return;
			if(saveJson.data.length == 0) return;
			
			var url = "<c:url value="/dq/bizrule/approveBizrule.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, saveJson, param, ibscallback);
		
	   		break;
  			
            
  		case "SaveRow" :
			//선택된 행이 있는지 확인하여 있으면 해당셀에 삽입, 없으면 신규로 행을 추가한다...
			/* if($("#selectRow").val() == "" && $("#isNew").val() == "I") {
				var nrow = grid_sheet.DataInsert(-1);
				form2ibsmapping(nrow, $("#frmDetail"), grid_sheet);
				form2ibsmapping(nrow, $("#frmDbDetail"), grid_sheet);
				form2ibsmapping(nrow, $("#frmVrtDetail"), grid_sheet);
				grid_sheet.SetCellValue(nrow, "ibsStatus", "I");
			} else {
				form2ibsmapping($("#selectRow").val(), $("#frmDetail"), grid_sheet);
				form2ibsmapping($("#selectRow").val(), $("#frmDbDetail"), grid_sheet);
				form2ibsmapping($("#selectRow").val(), $("#frmVrtDetail"), grid_sheet);
				grid_sheet.SetCellValue($("#selectRow").val(), "ibsStatus", "U");
			} */
			
			var ibsSaveJson = grid_sheet.GetSaveJson(0);

			if(ibsSaveJson.data.length == 0) return;
			
			var url = "<c:url value="/dq/bizrule/regBizruleRqstlist.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
			
			break;
			
  		case "AddRow" :
     		//saction (I-입력, U-수정)
     		
     		var urls = '<c:url value="/dq/bizrule/regBizrule.do"/>';

     		var param = $('form[name=frmDetail]').serialize();
     		
     		param += '&saction=' + $('#isNew').val();
     		
     		ajax2Json2(urls, param, ibscallback);
			
			break;
    }
}


function ajax2Json2(urls, param, callback, syncyn) {
	var ajaxurl = urls;
	var syn = true;
	if (syncyn) syn = syncyn;
	
//	alert(syn);
//	if(param != null && param != "") {
//		ajaxurl = urls + "?" +param;
//	}
//	showMsgBox("PRC", '처리중이니 잠시 기다려 주십시요.');
	
//	setTimeout(function(){
		$.ajax({
			url: ajaxurl,
			async: syn,
			type: "POST",
			data: param,
			dataType: 'json',
		beforeSend: function () {
			//처리중입니다. 메세지
							
			// 처리중이니 잠시 기다려 주십시요.
			showMsgBox("PRC", gMSG_PRC_WAIT);
			
		},
			success: callback
//			error: function (jqXHR, textStatus, errorThrown) {
//				//alert('에러발생...' + textStatus);
//				var res = {RESULT : {CODE:-1, MESSAGE:'시스템을 이용할수 없습니다.<br>관리자에게 문의하세요.'}};
//				ibscallback(res);
//			}
		});
//	}, 10);
	
	
}

function readonlyCheck(rqstStepCd) {
	//$("input[type=text]").attr('readOnly', false);
	$("textarea").attr('readOnly', false);
	$("select").attr('disabled', false);
	/* if (rqstStepCd == "N" || rqstStepCd == "S") {
		$("input[type=text]").attr('readOnly', false);
		$("textarea").attr('readOnly', false);
		$("select").attr('disabled', false);
		
	} else {
		
		$("input[type=text]").attr('readOnly', true);
		$("textarea").attr('readOnly', true);
		$("select").attr('disabled', true);
	} */
	
	$("#dbcTblNm").attr('readOnly', true);
	$("#dbcColNm").attr('readOnly', true);
	$("#brNm").attr('readOnly', false);
	$("#objDescn").attr('readOnly', false);
	
	$("#brNm").parents("tr").find('th:first-child').attr('class', 'th_require');
	$("#dqiLnm").parents("tr").find('th:first-child').attr('class', 'th_require');
	$("#dbConnTrgPnm").parents("tr").children('th').attr('class', 'th_require');
	$("#cntSql").parents("tr").children('th').attr('class', 'th_require');
}


//실시간실행 스케줄 등록
function regScheduler(){
	var ibsSaveJson = grid_sheet.GetSaveJson(0);
	if(ibsSaveJson.data.length == 0){
		showMsgBox("ERR", "<s:message code='ANLY.DATA.CHC' />"); /*분석할 데이터를 선택하십시오*/

		return;
	}
	
	var urls = '<c:url value="/commons/damgmt/schedule/ajaxgrid/insertSchedule.do"/>';
	var param = "&shdKndCd=QB"; //프로파일

    IBSpostJson2(urls, ibsSaveJson, param, schedulerCallBack);
}

function schedulerCallBack(data){
	//스케줄등록 오류
	if(data.RESULT.CODE < 0){
		showMsgBox("ERR", data.RESULT.MESSAGE); 
	}else{
		showMsgBox("INF", data.RESULT.MESSAGE); 
	}
}



function grid_sheet_OnClick(row, col, value, cellx, celly) {
	
	if(row < 1) return;

	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	
	//var param1 = $("#mstFrm").serialize();
	//param1 += "&rqstSno=" + param.rqstSno;
	//검증결과 조회
	//getRqstVrfLst(param1);
	
	//변경항목 조회
	//grid_change.RemoveAll();
	//if(param.regTypCd == 'U') {
	//	getRqstChg(param1);
	//}
	
	$("#isNew").val("U");
	$("#selectRow").val(grid_sheet.GetSelectRow());
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="BZWR.RGR.NM"/>' + ' : ' + param.brNm;  //업무규칙명
	$('#bizrule_sel_title').html(tmphtml);
	
	ibs2formmapping(row, $("form#frmDetail"), grid_sheet);

	$("#frmDetail #dbConnTrgId").change();

	ibs2formmapping(row, $("form#frmDetail"), grid_sheet);
	
	//ibs2formmapping(row, $("form#frmDetail"), grid_sheet);
	//ibs2formmapping(row, $("form#frmVrtDetail"), grid_sheet);
}

function  grid_sheet_OnSaveEnd(code, message) {
	doAction("Search"); 
}

function  grid_sheet_OnSearchEnd (code, message) {
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		//조회 성공...
		doAction("New");
	}
}


</script>
</head>
<body>
	<div class="menu_subject">
		<div class="tab">
			<div class="menu_title">
				<s:message code="BZWR.RULE.REG.DEMD" />
			</div>
			<!--업무규칙 등록요청-->

		</div>
	</div>
	<div style="clear: both; height: 5px;">
		<span></span>
	</div>
	
	<!-- 검색조건 입력폼 -->
	<div id="search_div">
	       <div class="stit"><s:message code="INQ.COND2" /></div><!--검색조건-->
	       <div style="clear:both; height:5px;"><span></span></div>
	       
	       <form id="frmSearch" name="frmSearch" method="post">
	           <fieldset>
	           <legend><s:message code="FOREWORD" /></legend><!--머리말-->
	           <div class="tb_basic2">
	               <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='DIAG.TRGT.DBMS.INQ.2'/>"><!--진단대상DBMS조회-->
	
	                  <caption><s:message code="DIAG.TRGT.DBMS"/></caption><!--진단대상DBMS-->
	
	                  <colgroup>
	                  <col style="width:15%;" />
	                  <col style="width:35%;" />
	                  <col style="width:15%;" />
	                  <col style="width:35%;" />
	                  </colgroup>
	                  
	                  <tbody>                            
	                      <tr>                               
	                          <th scope="row"><label for="dbConnTrgLnm"><s:message code="DIAG.TRGT.DBMS.NM"/></label></th><!--진단대상DBMS명-->
	
	                          <td>
					              <select id="dbConnTrgLnm"  name="dbConnTrgLnm">
								     <option value=""><s:message code="WHL" /></option><!--전체-->
								  </select>
					              <select id="dbSchLnm" class="" name="dbSchLnm">
					              	<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
					              </select>
	                          </td>
	                          <th scope="row"><label for="dbcTblLnm">테이블명</label></th>
	                          <td>
	                          		<input type="text" name="dbcTblLnm" id="dbcTblLnm" />
	                          </td>
	                      </tr>
	                      <tr>                               
	                          <th scope="row"><label for="dbcColLnm">컬럼명</label></th>
	                          <td colspan="3">
	                          		<input type="text" name="dbcColLnm" id="dbcColLnm" />
	                          </td>
	                      </tr>
	                  </tbody>
	                </table>   
	           </div>
	           </fieldset>
	           
	       <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
	       </form>
	</div>
	<div style="clear:both; height:5px;"><span></span></div>
	       
    <!-- 조회버튼영역  -->
		<%-- <tiles:insertTemplate template="/WEB-INF/decorators/buttonRqst.jsp" /> --%>
	<div class="bt03">
		<button class="btn_search" id="btnSearch" 	name="btnSearch"><s:message code="INQ" /></button> <!-- 전체조회 -->
		<button class="btn_search" id="btnRqstNew" name="btnRqstNew"><s:message code="ADDT" /></button> <!-- 추가 -->  
		<button class="btn_delete" id="btnDelete" 	name="btnDelete"><s:message code="DEL" /></button> <!-- 삭제 -->
		<button class="btn_search" id="btnExec" 	name="btnExec">업무규칙실행</button> 
		<%-- <button class="btn_excel_down"  id="btnExcelDown"  name="btnExcelDown"><s:message code="EXCL.DOWNLOAD" /></button>  --%>
	</div>
	<div style="clear:both; height:5px;"><span></span></div>
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
		<script type="text/javascript">createIBSheet("grid_sheet", "100%", "250px");</script>
	</div>
	<!-- 그리드 입력 입력 -->
	<div style="clear: both; height: 10px;">
		<span></span>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		<div class="selected_title" id="bizrule_sel_title">
			<span></span>
		</div>
	</div>
	<div style="clear: both; height: 5px;">
		<span></span>
	</div>
	<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabs">
		<ul>
			<li id="tab-1"><a href="#tabs-1" id="tab-1"><s:message code="BSIC.INFO" /></a></li>
			<!--기본정보-->
			
		</ul>
		<div id="tabs-1">
			<%@include file="br_detail.jsp"%>
		</div>
		
	</div>
	<!-- 선택 레코드의 내용을 탭처리 END -->



	<form name="frmHidden" id="frmHidden">
		<input type="hidden" id="selectRow" name="selectRow" /> 
		<input type="hidden" id="isNew" name="isNew" />
	</form>

</body>

</html>