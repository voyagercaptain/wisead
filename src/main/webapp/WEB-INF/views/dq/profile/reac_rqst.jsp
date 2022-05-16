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
<title></title>

<script type="text/javascript">
var interval = "";

$(document).ready(function() {
	
	//그리드 초기화 
	initGrid();
	//그리드 사이즈 조절 초기화...		
 	bindibsresize();
	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
	//탭 초기화....
	$( "#tabs" ).tabs().click(function(event){
	    //event.preventDefault();	//브라우저 기본 이벤트 제거...
	});
	
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code="VALID.PRFREQUIRED" />";
	//폼검증
	$("#frmAnaTrg").validate({
		rules: {
			dbConnTrgPnm		: "required",
			dbSchPnm		: "required",
			dbcTblNm 	: "required"
		},
		messages: {
			dbConnTrgPnm		: requiremessage,
			dbSchPnm		: requiremessage,
			dbcTblNm 	: requiremessage
		}
	});

	//프로파일 종류 테이블분석CD 강제 셋팅
	$("#prfKndCd").val("PT01");
	
    // 조회 Event Bind
    $("#btnAnaTrgSearch").click(function(){ doAction("SearchAnaTrgTbl");  });
    
    $("#btnDelete").click( function(){ doAction("Delete"); }).show();
    
    //div size 정의
//     divSize();
    
    //컬럼관련 진단대상 요소 hide
	$("form[name=frmAnaTrg] #colPrfTrLayer").hide();
    
	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
	setautoComplete($("#frmSearch #schDbSchNm"), "DBSCH");
	setautoComplete($("#frmSearch #schDbcTblNm"), "DBCTBL");
	setautoComplete($("#frmSearch #schDbcColNm"), "DBCCOL");
	
	//일괄등록 버튼 Hide...
    $("#btnPrfSchRqst").click(function(){
    	doAction("PrfSchRqst");
    }).hide();	//시연때만 잠시 막아둠.. 화면이 깨지는데 못잡겠음;;
    
	
	//임시 메뉴목록 등장 함수
	var val = $("#dbConnTrgId option:selected").val();
	var trgId = ${codeMap.connTrgSch} ;
	//$("#frmSearch #dbConnTrgId").append('<option value=""></option>');
	
	for(i=0; i<trgId.length; i++) {
		if(trgId[i].upcodeCd == val) {
			$("#frmSearch #dbConnTrgId").append('<option value="' + trgId[i].codeCd + '">' + trgId[i].codeLnm + '</option>');
		}
	}
	
	
	$("#frmSearch #dbConnTrgId").change(function() {
		$("#frmSearch #dbSchId").find("option").remove().end();
		var val = $("#dbConnTrgId option:selected").val();

		$("#frmSearch #dbSchId").append('<option value=""><s:message code="CHC" /></option> ');
		
		for(i=0; i<trgId.length; i++) {
			if(trgId[i].upcodeCd == val && val!="") {
				$("#frmSearch #dbSchId").append('<option value="' + trgId[i].codeCd + '">' + trgId[i].codeLnm + '</option>');
			}
		}
	});
	
	doAction("SearchAnaTrgTbl");

	//분석실행
	$("#btnExec").click(function(){
		regScheduler();
	}).show();
});



$(window).load(function() {
});

$(window).resize(function() {
		//div size 정의
		divSize();
});

EnterkeyProcess("SearchAnaTrgTbl");
	
function initGrid()
{
   
	//진단대상 테이블 grid
    with(grid_tbl){
    	
    	var cfg = {SearchMode:2,Page:100,VScrollMode:1};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"No|상태|선택|주제영역ID|주제영역명|진단대상ID|진단대상명|스키마ID|스키마명|테이블명|테이블한글명", Align:"Center"}
                ];
        //No|주제영역ID|주제영역명|진단대상ID|진단대상명|스키마ID|스키마명|테이블명|테이블한글명

        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:30,   SaveName:"ibsSeq",        Align:"Center", Edit:0},
                    {Type:"Status", Width:70,   SaveName:"ibsStatus",     Align:"Left", Edit:1, Hidden:1},
                    {Type:"CheckBox", Width:70,  SaveName:"ibsCheck",     Align:"Left", Edit:1},
                    {Type:"Text",   Width:100,  SaveName:"subjId",    	  Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"subjLnm",    	  Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"dbConnTrgId",   Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:80,  SaveName:"dbConnTrgPnm",   Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"dbSchId",    	  Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:80,  SaveName:"dbSchPnm",    	  Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"dbcTblNm",      Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"dbcTblKorNm",   Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"shdKndCd",      Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"shdJobId",      Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"etcJobNm",      Align:"Left", Edit:0, Hidden:1},
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);

	     //콤보 목록 설정...
// 	    SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
        
        InitComboNoMatchText(1, "");
        
        FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
        SetExtendLastCol(1);    
    }
    //==시트설정 후 아래에 와야함===
	init_sheet(grid_tbl);    
   
}	

function doAction(sAction, param)
{
        
    switch(sAction)
    {
       	/*진단대상 테이블 조회*/
        case "SearchAnaTrgTbl":
        	param = "";
        	param += "&tblColGb=PT01";
        	param += "&"+$('form[name=frmSearch]').serialize();
        	param += "&schDbConnTrgId=" + $("#dbConnTrgId").val();
        	param += "&schDbSchId=" + $("#dbSchId").val();
        	
        	grid_tbl.DoSearch("<c:url value="/dq/criinfo/anatrg/getPrfTblLst.do" />", param);
        	break;
        	
        case "DtlReset":
        	//테이블분석 텝 강제 클릭
    		//$("#tab-pt01 a").click();
    		//관계분석 form reset
			resetReac();
        	
    	    break;
        
        case "PrfSchRqst":
        	var url   = "<c:url value="/dq/profile/popup/prfschrqst_pop.do"/>";
        	var param = "" ;
        	var popup = OpenWindow(url+param,"SQL","1200","750","yes");

        	break;
        	
        case "Down2Excel":  //엑셀내려받기
            grid_tbl.Down2Excel({HiddenColumn:1, Merge:1});
            break;
        case "Delete":
        	//TODO 공통으로 처리...
        	var SaveJson = grid_tbl.GetSaveJson(0); 
        	
        	if(SaveJson.data.length == 0) return;
        	
            var url = "<c:url value="/dq/profile/delPT01Lst.do"/>";
            
        	var param = "";

        	IBSpostJson2(url, SaveJson, param, schedulerCallBack);
        	
        	break;
    }       
}
/*======================================================================*/
//IBSpostJson2 후처리 함수
/*======================================================================*/
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	if ('<%=WiseMetaConfig.RqstAction.APPROVE%>' != res.action) {
		showMsgBox("INF", res.RESULT.MESSAGE);
	}
	switch(res.action) {
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			doAction("Search");    		
			break;
		case "<%=WiseMetaConfig.IBSAction.DEL%>" : 
			doAction("Search");
			break;
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}	
}

function divSize(){
	 // 진단대상 전체
//   $("#anatrg").attr("style","width:30%;float:left;");
  // 진단대상 조회조건
//   $("#searchTrg_div").attr("style","width:99%;height:100%;float:left;");
  
  // 프로파일 상세 전체
//   $("#tblprf").attr("style","width:69%;height:100%;float:left;");
  
  //프로파일 텝
//   $("#tabs").attr("style","width:99%;float:right;");
  
  //그리드 가로 스크롤 방지
	grid_tbl.FitColWidth();
	
	//grid_prf.FitColWidth();
	
}

//진단대상 상세정보 화면 셋팅
function setAnaTgtDtl(data){
	json2formmapping($("form[name=frmAnaTrg]"), data);
	
	SearchPrfDtl($("#frmAnaTrg #prfId").val());
}

function loadTblDetail(param){
	ajax2Json('<c:url value="/dq/profile/getAnaTrgTblReacDetail.do"/>', param, setAnaTgtDtl);
}

//프로파일별 상세 조회
function SearchPrfDtl(prfId){	
	if(prfId != '' && prfId != null ){
		//그리드 선택 데이터 변수 setting
		var param = "&prfId="+$("#frmAnaTrg #prfId").val(); 
		     param += "&prfKndCd=PT01";  
	 	     
		var urls = '<c:url value="/dq/profile/getPrfDtl.do"/>';
		ajax2Json(urls, param, ajaxDtlCallback);
	}else{
		$("form[name=frmAnaTrg] #prfId").val("");
		$("form[name=frmAnaTrg] #regTypCd").val("");
		$("form[name=frmAnaTrg] #sheetRow").val("");
		
		//스케줄 작업 정보
		$("form[name=frmAnaTrg] #shdJobId").val("");
		$("form[name=frmAnaTrg] #etcJobNm").val("");
	}
	
	//너무 느림
	$("#divMsgPopup").dialog("close");
}


//프로파일별 상세조회 후 처리 함수
function ajaxDtlCallback(data){
	var vPrfId = data.resultVO.prfId;
	var vPrfKndCd = data.prfKndCd;
	var vSchAnaStrDtm = data.schAnaStrDtm;
	 //프로파일 입력폼 reset
	 doAction("DtlReset");
	 
	//관계분석
	if(vPrfKndCd == "PT01"){
		setDtlReac(data);
	}
	
	//프로파일종류 셋팅
	$("form[name=frmAnaTrg] #prfKndCd").val(vPrfKndCd);
	//프로파일 ID 셋팅
	$("form[name=frmAnaTrg] #prfId").val(vPrfId);
	//분석일시 셋팅
	$("form[name=frmAnaTrg] #schAnaStrDtm").val(vSchAnaStrDtm);
	//REG_TYP_CD 셋팅
	if(vPrfId != ""){
		$("form[name=frmAnaTrg] #regTypCd").val("U");
	}
	
	//스케줄 작업 정보
	$("form[name=frmAnaTrg] #shdJobId").val(vPrfId);
	$("form[name=frmAnaTrg] #etcJobNm").val("["+data.resultVO.prfKndCdNm+"] " + data.resultVO.dbcTblNm+"."+data.resultVO.objNm);
	
}

function grid_tbl_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;

	if(col < 3) return;
	
	//프로파일정보 reset
   	resetPrfInfo("PT01");
   	
    //텝 상세화면 RESET
   	doAction("DtlReset");
    
  //그리드 선택 데이터 변수 setting
	var param = "&dbConnTrgId="+grid_tbl.GetCellValue(row, "dbConnTrgId"); 
	     param += "&dbSchId="+grid_tbl.GetCellValue(row,"dbSchId");
	     param += "&dbcTblNm="+grid_tbl.GetCellValue(row,"dbcTblNm");
 	     param += "&dbcColNm="+$("form[name=frmSearch] input[name='schDbcColNm']").val();
 	     //테이블 컬럼 프로파일 구분
 	     param += "&tblColGb=PT01";
    
   	loadTblDetail(param);
}

//진단대상 테이블 조회 오류
function grid_tbl_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}else{
		resetPrfInfo("PT01");
	}
}

function resetPrfInfo(tblColGb){

	//컬럼 상세정보 초기화
	$('form[name=frmAnaTrg]')[0].reset();
	//hidden 일경우 form reset() 초기화 안됨
	//각 hidden 요소벌 초기화
	$("form[name=frmAnaTrg] #tblColGb").val(tblColGb);
	$("form[name=frmAnaTrg] #prfId").val("");
	$("form[name=frmAnaTrg] #prfKndCd").val("");
	$("form[name=frmAnaTrg] #regTypCd").val("");
	$("form[name=frmAnaTrg] #dbConnTrgId").val("");
	$("form[name=frmAnaTrg] #dbSchId").val("");
	$("form[name=frmAnaTrg] #sheetRow").val("");
	
	//스케줄 작업 정보
	$("form[name=frmAnaTrg] #shdJobId").val("");
	$("form[name=frmAnaTrg] #etcJobNm").val("");
	
}


//실시간실행 스케줄 등록
function regScheduler(){

	var ibsSaveJson = grid_tbl.GetSaveJson(0);
	
	if(ibsSaveJson.data.length == 0){
		showMsgBox("ERR", "<s:message code='ANLY.DATA.CHC' />"); /*분석할 데이터를 선택하십시오*/

		return;
	}

	for(var i = 0; i <  ibsSaveJson.data.length; i++) {

		if(ibsSaveJson.data[i].shdJobId == ""){

			showMsgBox("ERR", "참조무결성 등록된 테이블만 분석실행 가능합니다."); 
			return;
		}
	}
	
	var urls = '<c:url value="/commons/damgmt/schedule/ajaxgrid/insertSchedule.do"/>';
	var param = "&shdKndCd=QP"; //프로파일

    IBSpostJson2(urls, ibsSaveJson, param, schedulerCallBack);
}


function schedulerCallBack(data){
	//$("#divMsgPopup").dialog("close");
	//스케줄등록 오류
	if(data.RESULT.CODE < 0){
		showMsgBox("ERR", data.RESULT.MESSAGE); 
	}else{
		showMsgBox("INF", data.RESULT.MESSAGE); 
	}
}

</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="TBL.PROF.MNG" /></div><!--테이블프로파일 관리-->

	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 진단대상 검색 -->
<div id="anatrg" style="width: 30%; float: left;" >
	<div id="searchTrg_div" >
	        <div class="stit"><s:message code="INQ.COND2" /></div><!--검색조건-->
	        <div style="clear:both; height:5px;"><span></span></div>
	        
	        <form id="frmSearch" name="frmSearch" method="post">
	            <fieldset>
	            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
	            <div class="tb_basic2" >
	                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='TBL.PROF.MNG'/>"><!-- /*테이블프로파일관리*/ -->

	                   <caption><s:message code="TBL.PROF.MNG"/></caption><!--테이블프로파일관리-->

	                   <colgroup>
	                   <col style="width:15%;" />
	                   <col style="width:35%;" />
<%-- 	                   <col style="width:15%;" /> --%>
<%-- 	                   <col style="width:35%;" /> --%>
	                   </colgroup>
	                   
	                   <tbody>                            
	                       <tr>                               
	                         	<th scope="row"><label for="schDbConnTrgId"><s:message code="DB.MS" /></label></th><!--진단대상명-->
	                         	<td>
				             		<select id="dbConnTrgId"  name="dbConnTrgId">
							    		<option value=""><s:message code="WHL" /></option><!--전체-->
									</select>
	                            	<select id="dbSchId" class="" name="dbSchId">
				             			<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
				             	  	</select>
	                           	</td>
	                       </tr>
	                       <tr>
	                       		<th scope="row"><label for="schDbcTblNm"><s:message code="TBL.NM" /></label></th>	<!--테이블명-->

	                       		<td>
	                       			<input type="text" name="schDbcTblNm" id="schDbcTblNm" class="wd98p"/>
	                       		</td>
	                       </tr>
	                       <tr>                               
	                           <th scope="row"><label for="schDbcColNm"><s:message code="CLMN.NM" /></label></th><!--컬럼명-->

	                           <td>
	                               <input type="text" name="schDbcColNm" id="schDbcColNm" class="wd98p" />
	                           </td>
	                       </tr>
	                       
	                       <tr style="display:none;">                               
	                           <th scope="row"><label for="metaAsscAnly"><s:message code="META.LNKD.ANLY" /></label></th><!--메타연계-->

	                           <td>
	                               <select id="metaAsscAnly" name="metaAsscAnly">
	                               	   <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
	                               	   <option value="Y">Y</option> 	                               	   	                               	   
	                               </select>
	                           </td>
	                       </tr>
	                       
	                       <tr>                               
	                           <th scope="row"><label for="schRegYn"><s:message code="REG.YN" /></label></th><!--등록여부-->
	                           <td>
	                           		<select id="schRegYn"  name="schRegYn">
	                           			<option value=""><s:message code="WHL" /></option><!--전체-->

	                           			<option value="Y">Y</option>
	                           			<option value="N">N</option>
	                           		</select>
	                           </td>
	                       </tr>
	                   </tbody>
	                 </table>   
	            </div>
	            </fieldset>
	            
	        </form>
	</div>
	
    <!-- 조회버튼영역  -->
	<div style="clear:both; height:10px;"><span></span></div>
	<div id="divFrmBtn" style="text-align: left;">
		<button class="btn_search" id="btnAnaTrgSearch" 	name="btnAnaTrgSearch"><s:message code="INQ"/><!--조회--></button>
		<button class="btn_delete" id="btnDelete" 	name="btnDelete"><s:message code="DEL" /></button> <!-- 삭제 -->
		<button class="btn_search" id="btnExec" 	name="btnExec">참조무결성실행</button> 
 
	</div>
	
	<div style="clear:both; height:5px;"><span></span></div>
	        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_tbl", "99%", "480px");</script>            
	</div>
	<div style="clear:both; height:10px;"><span></span></div>
	
	
</div>
<div style="width: 1%; float: left;">&nbsp;</div>
<!-- 테이블프로파일 상세 조회 -->
<div id="tblprf" style="width: 69%; float:left; ">
	<!-- 진단대상 상세 -->
	<div style="display:none;" id="tgtDtl">
		<%-- <%@include file="dtl/prf_dtl.jsp" %> --%>
		<form id="frmAnaTrg" name="frmAnaTrg" method="post">
	 	<!-- 프로파일 ID, 프로파일 종류 셋팅 -->
			<input type="hidden" name="dbConnTrgId" id="dbConnTrgId"  /> 
			<input type="hidden" name="dbSchId" id="dbSchId"  />
			<input type="hidden" name="tblColGb" id="tblColGb" value="" />     
			<input type="hidden" name="prfKndCd" id="prfKndCd" />
			<input type="hidden" name="prfId" id="prfId" />   
			<input type="hidden" name="regTypCd" id="regTypCd" /> 
			<input type="hidden" name="schAnaStrDtm" id="schAnaStrDtm"  />
			<!--  분석실행 작업ID -->
			<input type="hidden" name="shdJobId" id="shdJobId"  />
			<input type="hidden" name="etcJobNm" id="etcJobNm"  />
			
			<input type="hidden" name="metaAsscAnly" id="metaAsscAnly"  />
			
			<input type="hidden" name="dmnMinVal" id="dmnMinVal"  />
			<input type="hidden" name="dmnMaxVal" id="dmnMaxVal"  />
			<input type="hidden" name="dmnLnm"    id="dmnLnm"  />
			<input type="hidden" name="infotpLnm" id="infotpLnm"  />
			
			<!-- 컬럼프로파일 : 컬럼명 테이블프로파일 : 관계키명, 중복분석명사용 -->
			<div id="div_objNm"></div>
			
	 	<fieldset>
	    <legend><s:message code="FOREWORD" /></legend><!-- 머리말 -->
		<div class="tb_basic" >
			<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='PROF.MNG'/>"> <!-- 프로파일 관리 -->
			   <caption><s:message code='PROF.MNG'/></caption><!-- 프로파일 관리 -->
			   <colgroup>
			   <col style="width:12%;" />
			   <col style="width:38%;" />
			   <col style="width:12%;" />
			   <col style="width:38%;" />
			   </colgroup>
			       <tbody>   
			       		<tr>                               
			               <th scope="row"  class="th_require"><label for="dbConnTrgPnm"><s:message code="DB.MS" /></label></th><!-- 진단대상명 -->
			               <td>
			                   <input  type="text"  name="dbConnTrgPnm" id="dbConnTrgPnm"  class="b0wd98" readonly />
			               </td>
			               <th scope="row"  class="th_require"><label for="dbSchPnm"><s:message code="SCHEMA.NM" /></label></th><!-- 스키마명 -->
			               <td>
			                   <input type="text" name="dbSchPnm" id="dbSchPnm"  class="b0wd98" readonly />
			               </td>
			           </tr>                         
			           <tr>                               
			               <th scope="row"  class="th_require"><label for="dbcTblNm"><s:message code="TBL.NM" /></label></th><!-- 테이블명 -->
			               <td>
			                   <input type="text" name="dbcTblNm" id="dbcTblNm"  class="b0wd98" readonly/>
			               </td>
			               <th scope="row"><label for="dbcTblKorNm"><s:message code="TBL.KRN.NM" /></label></th><!-- 테이블한글명 -->
			               <td>
			                   <input type="text" name="dbcTblKorNm" id="dbcTblKorNm"  class="b0wd98" readonly />
			               </td>
			           </tr>
			           <tr id="colPrfTrLayer">
			           		<th scope="row"  class="th_require"><label for="dbcColNm"><s:message code="CLMN.NM" /></label></th><!-- 컬럼명 -->	
			           		<td>
			           			<input type="text" name="dbcColNm" id="dbcColNm"   readonly />
			           		</td>
			           		 <th scope="row"><label for="dbcColKorNm"><s:message code="CLMN.KRN.NM" /></label></th><!-- 컬럼한글명 -->
			               <td>
			                   <input type="text" name="dbcColKorNm" id="dbcColKorNm"   readonly />
			               </td>
			           </tr>
			           <tr id="colPrfTrLayer">                               
			               <th scope="row"><label for="pkYn">PK</label></th>
			               <td>
			                   <input type="text" name="pkYn" id="pkYn"   readonly/>
			               </td>
			               <th scope="row"><label for="nullYn"><s:message code="NULL.YN" /></label></th><!-- Null여부 -->
			                <td>
			                   <input type="text" name="nullYn" id="nullYn"   readonly />
			               </td>
			           </tr>
			           <tr id="colPrfTrLayer">                               
			                <th scope="row"><label for="dataType"><s:message code="DATA.TY" /></label></th><!-- 데이터타입 -->
			               <td>
			                   <input type="text" name="dataType" id="dataType"  readonly />
			               </td>
			               <th scope="row"><label for="defltVal">DEFAULT</label></th>
			               <td>
			                   <input type="text" name="defltVal" id="defltVal"    readonly/>
			               </td>
			           </tr>
			       </tbody>
			     </table>   
			</div>
			</fieldset>
			</form>
	</div>
	<!--  진단대상 상세 끝 -->

	<div style="clear:both; height:10px;"><span></span></div>
	<!-- 버튼영역  -->
	<%-- <tiles:insertTemplate template="/WEB-INF/decorators/buttonProfile.jsp" /> --%>
	<div style="clear:both; height:5px;"><span></span></div>       
	
	
	<!-- 프로파일 종류별 텝 -->
	<!-- 선택 레코드의 내용을 탭처리... -->
	<%-- <div id="tabs">
	  <ul>
	    <li id="tab-reac"><a href="#tabs-reac"><s:message code="REAC.ANLY"/></a></li><!--관계분석-->

	  </ul>
	  <div id="tabs-reac">
	  	<div id="PT01_div">
	  		<%@include file="dtl/reac_dtl.jsp" %>
	  	</div>
	  </div>
	<!-- 프로파일 종류별 텝 끝 -->
	</div> --%>
	
	<div id="PT01_div">
  		<%@include file="dtl/reac_dtl.jsp" %>
  	</div>
</div>

</body>
</html>