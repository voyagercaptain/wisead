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
<title><s:message code="DIAG.TRGT.DB.MS.INQ" /></title><!--진단대상DBMS 조회-->



<script type="text/javascript">

var interval = "";
var connTrgSchJson = ${codeMap.devConnTrgSch} ;
//엔터키 이벤트 처리
EnterkeyProcess("Search");
$(document).ready(function() {
	
	//그리드 초기화 
	initGrid();
	
	//그리드 사이즈 조절 초기화...		
	bindibsresize();
	
	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
	//탭 초기화....
	//$( "#tabs" ).tabs();
	//그리드 초기화 
	// 조회 Event Bind
	$("#btnSearch").click(function(){ doAction("Search");  });
	// 엑셀내리기 Event Bind
	$("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

	$("#btnExcelLoad").click( function(){ doAction("Load2Excel"); } ).hide(); 
	
	
	//조회버튼 hidden
	$("#btnSave").click( function(){ doAction("Save"); }).show();
	$("#btnDelete").click( function(){ doAction("Delete"); }).show();

	$("#btnExec").click(function(){ doAction("Exec");  }).show();
	
	//tree 추가 버튼 hidden
	$("#btnTreeNew").hide();
	//상세 페이지
	loadDetail();
	
	//임시 메뉴목록 등장 함수
	var val = $("#dbConnTrgId option:selected").val();
	var trgId = ${codeMap.devConnTrgSch} ;
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
	
	$("#btnLogPop").click(function(){
		var param = "";
		var url   = "<c:url value="/dq/scheduler/popup/schedulelog_pop.do"/>";
    	var popup = OpenWindow(url+param,"SQL","1200","750","yes");
	});
	//doAction("Search");
	
	$("#btnPc01Save").click(function() {
    	saveColAna();
    });
	
	$("#searchAnaResDtl_div .stit").hide();
	
});

$(window).load(function() {

});


$(window).resize(
    function(){

//     	setibsheight($("#grid_01"));

    	//그리드 가로 스크롤 방지
    	//grid_sheet.FitColWidth();
    }
);


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100, VScrollMode:1};
        SetConfig(cfg);

        //No.|상태|선택|DB접속대상ID|만료일시|시작일시|DB접속대상물리명|DB접속대상논리명|DBMS종류|DBMS버전|접속대상DB연결문자열|접속대상연결URL|접속대상드라이버명|DB접속계정ID|DB접속계정비밀번호|DB접속상태|담당자명|담당자연락처|객체설명|객체버전|등록유형코드|작성일시|작성사용자ID
        
        var headerText = "No.|상태|선택|RULE_REL_ID|DB_SCH_ID|DB_CONN_TRG_ID|DBMS명|스키마명|테이블명|테이블한글명|컬럼명|컬럼한글명|DataType|데이터건수|검증룰ID|검증룰|검증룰검색|코드분류ID|shdJobId|shdKndCd|etcJobNm|shdJobNm|프로파일ID|분석실행시간|LOG|검증룰명";
        
        var headers = [
                    {Text: headerText, Align:"Center"}
                ];
            
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",      Width:60,   SaveName:"ibsSeq",        Align:"Center", Edit:0},
                    {Type:"Status",   Width:80,   SaveName:"ibsStatus",     Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:60,   SaveName:"ibsCheck",         Align:"Center", Edit:1, Sort:0},                    
                    {Type:"Text",     Width:100,  SaveName:"ruleRelId",    	Align:"Left", Edit:0, Hidden:1},                    
                    {Type:"Text",     Width:100,  SaveName:"dbSchId",    	Align:"Left", Edit:0, Hidden:1},                   
                    {Type:"Text",     Width:100,  SaveName:"dbConnTrgId",  Align:"Left", Edit:1, Hidden:1},
                    {Type:"Text",     Width:100,  SaveName:"dbConnTrgPnm",  Align:"Left", Edit:0},
                    {Type:"Text",     Width:100,  SaveName:"dbSchPnm",      Align:"Left", Edit:0},
                    {Type:"Text",     Width:200,  SaveName:"dbcTblNm",    	Align:"Left", Edit:0}, 
                    {Type:"Text",     Width:200,  SaveName:"dbcTblKorNm",   Align:"Left", Edit:0},
                    {Type:"Text",     Width:200,  SaveName:"dbcColNm",    	Align:"Left", Edit:0}, 
                    {Type:"Text",     Width:200,  SaveName:"dbcColKorNm",   Align:"Left", Edit:0},
                    {Type:"Text",     Width:200,  SaveName:"dataType",      Align:"Left", Edit:0},
                    {Type:"Int",      Width:100,  SaveName:"anaCnt",      	Align:"Right", Edit:0},
                    {Type:"Text",     Width:180,  SaveName:"vrfcId",        Align:"Left", Edit:1, Hidden:1},
                    {Type:"Combo",    Width:180,  SaveName:"vrfcCd",        Align:"Left", Edit:1, Hidden:0},
                    {Type:"Popup",    Width:80,  SaveName:"vrfcSearchPop",  Align:"Center", Edit:1},  
                    {Type:"Text",     Width:200,  SaveName:"cdClsId",       Align:"Left", Edit:0},
                    {Type:"Text",     Width:200,  SaveName:"shdJobId",      Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",     Width:200,  SaveName:"shdKndCd",      Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",     Width:200,  SaveName:"etcJobNm",      Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",     Width:200,  SaveName:"shdJobNm",      Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",     Width:200,  SaveName:"prfId",         Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",     Width:10,   SaveName:"anaStrDtm",     Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
                    {Type:"Popup",   Width:50,   SaveName:"logSearch",    	Align:"Center", Edit:1, Hidden:0},
                    {Type:"Text",    Width:180,  SaveName:"vrfcNm",        Align:"Left", Edit:1, Hidden:1}
                   
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);
        SetColProperty("vrfcCd", 	${codeMap.vrfcIdsibs});
        
        InitComboNoMatchText(1, "");
        FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}


function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":
//         	if($('#dbConnTrgId').val()=="" || $('#dbSchId').val()==""){
//         		showMsgBox("ERR", "<s:message code="VALID.PRFREQUIRED" />", '');
//         		break;
//         	}
        		
        	var param = $('#frmSearch').serialize();
        	grid_sheet.DoSearch('<c:url value="/dq/criinfo/anatrg/getCheckRuleTbl.do" />', param);
        	break;
       
        case "Save":
        	//TODO 공통으로 처리...
        	var SaveJson = grid_sheet.GetSaveJson(0); 
        	
        	if(SaveJson.data.length == 0) return;
        	
        	for(var i=0; i<SaveJson.data.length; i++) {
	        	if(SaveJson.data[i].prfId == null || typeof(SaveJson.data[i].prfId) == undefined || SaveJson.data[i].prfId==""){
	        		showMsgBox("ERR", "<s:message code='MSG.PC01.CHC' />", '');
	        		return;
	        	}
        	}
        	
            var url = "<c:url value="/dq/criinfo/anatrg/regChkRuleAply.do"/>";
            
        	var param = "";

        	IBSpostJson2(url, SaveJson, param, ibscallback);
        	
        	break;
        case "Delete":

        	//TODO 공통으로 처리...
        	var SaveJson = grid_sheet.GetSaveJson(0); 
        	
        	if(SaveJson.data.length == 0) return;
        	
            var url = "<c:url value="/dq/criinfo/anatrg/delChkRuleAply.do"/>";
            
        	var param = "";

        	IBSpostJson2(url, SaveJson, param, ibscallback);
        	
        	break;       
        case "Exec":
           
        	//TODO 공통으로 처리...
        	var SaveJson = grid_sheet.GetSaveJson(0); 

	        for(var i = 0; i < SaveJson.data.length ; i++){
	        	
				var bCheck = SaveJson.data[i].ibsCheck;
				var vrfcId = SaveJson.data[i].vrfcId;
				
	        	if(bCheck == 1 && vrfcId == ""){
					showMsgBox("ERR",'검증룰이 적용된 컬럼만 실행 가능합니다.');
					return;
	            }
	        }
			
        	if(SaveJson.data.length == 0) return;
        	
            var url = "<c:url value="/dq/criinfo/anatrg/execItmAna.do"/>";
            
        	var param = "";

        	IBSpostJson2(url, SaveJson, param, ibscallback);
        	
        	break;             
        	
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({Mode:2, HiddenColumn:1, Merge:1});
            break;
        case "Load2Excel":  //엑셀올리기
        	grid_sheet.LoadExcel({Mode:'HeaderMatch', Append:1});
            break;    
    }       
}
 
//상세정보호출
function loadDetail(param) {
	$('div#detailInfo').load('<c:url value="/dq/criinfo/ajaxgrid/selectAnaTrgDbmsDetail.do"/>', param, function(){
		//$('#tabs').show();
	});
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


function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	if(grid_sheet.CellSaveName(row, col) == "vrfcSearchPop") return;
	if(grid_sheet.CellSaveName(row, col) == "vrfcCd") return;
	
	getDataPattern(row);
}

function getDataPattern(row){
 	var objId = grid_sheet.GetCellValue(row, "prfId");
 	var anaStrDtm = grid_sheet.GetCellValue(row, "anaStrDtm");
	
	if(objId == ""){
		showMsgBox("ERR", "컬럼분석을 완료한 데이터를 선택하십시오."); /*조회할 데이터를 선택하십시오.*/

		return;
	}
	
 	var param = "?objId="+objId;
	     param += "&objDate="+ anaStrDtm;
	     param += "&objIdCol=PRF_ID";		  
         param += "&objResTbl=WAM_PRF_RESULT";
	     param += "&objErrTbl=WAM_PRF_ERR_DATA";
	     param += "&erDataSnoCol=ESN_ER_DATA_SNO";
         param += "&objGb=PC01";        
         param += "&erDataSno="+0;
         
    var url = '<c:url value="/dq/report/popup/datapattern_pop.do" />';
 	var popup = OpenWindow(url+param, "DATA_PATTERN", "800", "600", "yes"); 
}
/* function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
} */

function grid_sheet_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	//그리드 선택 데이터 변수 setting
	var param =  grid_sheet.GetRowJson(row);
	var dbConnTrgId = "&dbConnTrgId="+grid_sheet.GetCellValue(row, "dbConnTrgId");
	//caption 
// 	var tmphtml = ' <s:message code="DIAG.TRGT.DBMS.NM"/>'+ ' : ' + param.dbConnTrgLnm ; //진단대상DBMS명


// 	$('#anatrg_sel_title').html(tmphtml);
	
	//상세조회
	loadDetail(dbConnTrgId);
	if(grid_sheet.GetCellEditable(row,col)=='1'){
		return;
	}
	var objId = grid_sheet.GetCellValue(row, "prfId");
 	var anaStrDtm = grid_sheet.GetCellValue(row, "anaStrDtm");
// alert(objId);
	var param2 = "objId="+objId;
//     param2 += "&objDate="+ anaStrDtm;
//     param2 += "&objIdCol=PRF_ID";		  
//     param2 += "&objResTbl=WAM_PRF_RESULT";
//     param2 += "&objErrTbl=WAM_PRF_ERR_DATA";
//     param2 += "&erDataSnoCol=ESN_ER_DATA_SNO";
       param2 += "&objGb=PC01";        
//     param2 += "&erDataSno="+0;
	
	$('div#colanaresdtl').load('<c:url value="/dq/report/ajaxgrid/getColAnaResDtl.do"/>', param2, function(){});
	
}

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
}

function grid_sheet_OnChange(Row, Col, Value, OldValue, RaiseFlag) { 
	if ("vrfcCd" == grid_sheet.ColSaveName(Col)) {
		grid_sheet.SetCellValue(Row,"vrfcId",grid_sheet.GetCellValue(Row,Col));
		grid_sheet.SetCellValue(Row,"cdClsId","");
	}
	
}


//주제영역 팝업 리턴값 처리
function returnSubjPop (ret, row, cls) {
// 	alert(ret);
	
	var retjson = jQuery.parseJSON(ret); 

	if(cls == "CD"){

		var vrfcNm = retjson.cdRuleNm + " " + retjson.codeClsVal;   

		grid_sheet.SetCellValue(row, "vrfcId",  retjson.cdRuleId); 
 		grid_sheet.SetCellValue(row, "vrfcNm",  vrfcNm);
		grid_sheet.SetCellValue(row, "cdClsId", retjson.codeClsId);	 
		
	}else{
		grid_sheet.SetCellValue(row, "vrfcId", retjson.vrfcId);
		grid_sheet.SetCellValue(row, "vrfcCd", retjson.vrfcId);
 		grid_sheet.SetCellValue(row, "vrfcNm", retjson.vrfcNm);
		grid_sheet.SetCellValue(row, "cdClsId", "");	 
	}			
}

function grid_sheet_OnPopupClick(Row,Col) {
	var param ="";
	var url = "";
	
	//사용자 검색 팝업 오픈
	if ("vrfcSearchPop" == grid_sheet.ColSaveName(Col)) {
		param +="code=N&row=" +Row;
		param +=  "&dbConnTrgId="+grid_sheet.GetCellValue(Row, "dbConnTrgId");
		url = '<c:url value="/dq/criinfo/anatrg/popup/vrfcrule_pop.do" />';
		openLayerPop(url, 800, 700, param);
	}else if ("logSearch" == grid_sheet.ColSaveName(Col)) {
	    param ="?shdLnm="+encodeURIComponent("진단항목 분석실행")+"&objNm="+grid_sheet.GetCellValue(Row,"dbSchPnm")+"."+grid_sheet.GetCellValue(Row,"dbcTblNm")+"."+grid_sheet.GetCellValue(Row,"dbcColNm");
	    url   = "<c:url value="/dq/scheduler/popup/schedulelog_pop.do"/>";
	    var popup = OpenWindow(url+param,"SQL","1200","750","yes");
	}
}

function saveColAna(){
	var saveJson = grid_sheet.GetSaveJson(0, "ibsCheck");
	
	if(saveJson.data.length == 0){
		showMsgBox("ERR", "분석할 컬럼을 선택하십시오."); /*분석할 데이터를 선택하십시오*/

		return;
	}
	
// 	for(var i = 1; i <= grid_col.GetDataLastRow(); i++){
// 		if(grid_col.GetCellValue(i,'expYn') == 'Y' && grid_col.GetCellValue(i,'ibsCheck') == '1'){
// 			showMsgBox("ERR", "제외여부가 Y인 컬럼은 분석을 실행할 수 없습니다.");
// 			return;
// 		} 
// 	}
	//저장
	var urls = '<c:url value="/dq/profile/registerPC01.do"/>';
	
	var param = "&prfKndCd=PC01";
		     
	IBSpostJson2(urls, saveJson, param, ibscallback);
}



</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="DIAG.TRGT.DB.MS.INQ" /></div><!--진단대상DBMS 조회-->

	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

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
                   <col style="width:8%;" />
                   <col style="width:17%;" />
                   <col style="width:8%;" />
                   <col style="width:17%;" />
                   <col style="width:8%;" />
                   <col style="width:17%;" />
                   <col style="width:8%;" />
                   <col style="width:17%;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row"><label for="dbConnTrgId"><s:message code="DIAG.TRGT.DBMS.NM"/></label><%-- <img src='<c:url value="/images/th_require.gif" />'/> --%></th><!--진단대상DBMS명-->
                           <td>
                              <select id="dbConnTrgId"  name="dbConnTrgId">
								    <option value=""><s:message code="WHL" /></option><!--전체-->
								</select>
								<select id="dbSchId" class="" name="dbSchId">
					             	<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
					             </select>
                           </td>
                           <th scope="row"><label for="dbcTblNm">테이블명</label></th>
                           <td>
                           		<input type="text" name="dbcTblNm" id="dbcTblNm" />
                           </td>
                           
                           <th scope="row"><label for="dbcColNm">컬럼명</label></th>
                           <td>
                           		<input type="text" name="dbcColNm" id="dbcColNm" />
                           </td>
                           <th scope="row"><label for="dbcColKorNm">컬럼한글명</label></th>
                           <td>
                           		<input type="text" name="dbcColKorNm" id="dbcColKorNm" />
                           </td>
                           <tr>     
                           <th scope="row"><label for="anaYn">컬럼분석등록여부</label></th>
                           <td>
                           		<select id="anaYn" name="anaYn" class="wd100">
                           			<option value="">전체</option> 
                           			<option value="Y">Y</option>
                           			<option value="N">N</option>
                           		</select>
                           </td>                                                     
                           <th scope="row"><label for="regYn">검증룰등록여부</label></th>
                           <td>
                           		<select id="regYn" name="regYn" class="wd100">
                           			<option value="">전체</option> 
                           			<option value="Y">Y</option>
                           			<option value="N">N</option>
                           		</select>
                           </td>    
                           <th scope="row"><label for="vrfcTyp">검증분류</label></th>
                           <td colspan="3">
                           		<select id="vrfcTyp" name="vrfcTyp" >
                           		   <option value="">전체</option> 
                         		   <c:forEach var="code" items="${codeMap.vrfcTyp}" varStatus="status" >
                                   <option value="${code.codeCd}">${code.codeLnm}</option>
                                   </c:forEach>
                           		</select>
                           </td>                          
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
<%--         <div class="tb_comment"><s:message  code='ETC.COMM' /></div> --%>
        <div class="tb_comment">- 컬럼분석 수행된 컬럼만 검증룰 적용이 가능합니다.(파란색으로 표시됩니다.)</div>
        <div class="tb_comment">- 컬럼을 더블클릭하면 해당 컬럼의 데이터패턴 및 상세 정보를 확인할 수 있습니다. 분석 정상수행 여부는 LOG조회를 통해 확인할 수 있습니다.</div>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
		<div class="divLstBtn" style="display: none;">	 
            <div class="bt03">
			    <button class="btn_search" id="btnSearch" 	name="btnSearch"><s:message code="INQ"/></button> <!-- 조회 -->                 
			    <button class="btn_save" id="btnSave" 	name="btnSave"><s:message code="STRG" /></button> <!-- 저장 --> 
			    <button class="btn_delete" id="btnDelete" 	name="btnDelete"><s:message code="DEL" /></button> <!-- 삭제 -->
			    <button class="btn_save"   id="btnExec" 	name="btnExec">진단항목실행</button> <!-- 저장 --> 
			    <button class="btn_search" id="btnPc01Save" name="btnPc01Save">컬럼분석 실행</button><!--조회-->   
			    <button class="btn_search" id="btnLogPop" name="btnLogPop">LOG</button> <!-- 로그팝업 조회 -->
			</div>
			<div class="bt02"> 
 	          <button class="btn_excel_down" id="btnExcelLoad" name="btnExcelLoad">엑셀 업로드</button>                       
	          <button class="btn_excel_down" id="btnExcelDown" name="btnExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                       
	    	</div>
        </div>	
         <!-- 조회버튼영역  -->

</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "250px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->
<div style="clear:both; height:5px;"><span></span></div>
           <div id="tabs">
				<ul>
					<li id="tab-03"><a href="#tabs-03"><s:message code="CLMN.ANLY.DTL.INQ"/></a></li><!--컬럼분석상세조회-->

				</ul>
				<div id="tabs-03">
					<div id="colanaresdtl"><%@include file="datapattern_dtl.jsp" %></div>
				</div>
			</div>
</body>
</html>