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
<title>DB정보</title> <!-- 주제영역조회 -->

<script type="text/javascript">
//엔터키 이벤트 처리
// EnterkeyProcess("Search");

var searchFlag = 'All';

$(document).ready(function() {
		$("#pdmArea").hide();
		$("#colArea").hide();
	
        // 조회 Event Bind
        $("#btnSearch").click(function(){ searchFlag = 'All'; doAction("Search");  }).show();
        $("#btnAnaPdm").click(function(){ searchFlag = 'Pdm'; doAction("AnaPdm");  }).show();
        $("#btnAnaCol").click(function(){ searchFlag = 'Col'; doAction("AnaCol");  }).show();

        // 저장 Event Bind
        $("#btnSave").click(function(){ 
        	//var rows = grid_sheet.FindStatusRow("I|U|D");
//         	var rows = grid_sheet.IsDataModified();
//         	if(!rows) {
// //         		alert("저장할 대상이 없습니다...");
//         		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
//         		return;
//         	}
        	
        	//저장할래요? 확인창...
    		var message = "<s:message code="CNF.SAVE" />";
    		showMsgBox("CNF", message, 'Save');	
        	//doAction("Save");
        	
        }).show();
        
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

});

$(window).load(function() {
	//그리드 초기화 
	initGrid();
	initPdmGrid();
	initColGrid();
	$(window).resize();
});


$(window).resize(function(){
    //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
// 	setibsheight($("#grid_01"));
	// grid_sheet.SetExtendLastCol(1);    
});


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='NIA.HEADER.PDM.COL.RQST'/>", Align:"Center"}
                    /* No.|상태|선택|기관명|DB명|테이블영문명|테이블한글명|컬럼영문명|컬럼한글명|공통코드여부|공통코드명|컬럼설명|데이터타입|데이터길이|소수점|데이터형식|NotNull여부|PK정보|FK정보|제약조건|개인정보여부|암호화여부|공개비공개여부 */
                ];
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",      Width:40,    SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status",   Width:40,    SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:50,    SaveName:"ibsCheck",     Align:"Center", Edit:0, Hidden:1, Sort:0},
                    {Type:"Text",     Width:100,   SaveName:"colId",    	Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:100,   SaveName:"orgNm",    	Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",     Width:100,   SaveName:"dbNm",    	    Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",     Width:200,   SaveName:"tblPnm",    	Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",     Width:150,   SaveName:"tblLnm",    	Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",     Width:100,   SaveName:"colPnm",    	Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",     Width:100,   SaveName:"colLnm",    	Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",     Width:100,   SaveName:"stndCdYn",    	Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",     Width:100,   SaveName:"stndCdNm",    	Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",     Width:200,   SaveName:"colDescn",    	Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",     Width:100,   SaveName:"dataType",    	Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",     Width:100,   SaveName:"dataLen",    	Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",     Width:100,   SaveName:"dataScal",    	Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",     Width:100,   SaveName:"dataFrm",    	Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:100,   SaveName:"notNullYn",    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:100,   SaveName:"pkYn",    	    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:100,   SaveName:"fkYn",    	    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:100,   SaveName:"constrnt",    	Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:100,   SaveName:"prsnYn",    	Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:100,   SaveName:"encYn",    	Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:100,   SaveName:"openYn",    	Align:"Left",   Edit:0, Hidden:1},
                ];
                    
        InitColumns(cols);
      
        FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}

function initPdmGrid()
{
    
    with(pdm_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='NIA.HEADER.PDM.ANA'/>", Align:"Center"}
                    /* No.|상태|선택|진단내용|진단일자|테이블 총 건수|테이블 누락 건수|테이블 누락율(%)|컬럼 총 건수|컬럼 누락 건수|컬럼 누락율(%) */
                ];
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",      Width:40,    SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status",   Width:40,    SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:50,    SaveName:"ibsCheck",     Align:"Center", Edit:0, Hidden:1, Sort:0},
                    {Type:"Text",     Width:400,   SaveName:"anaCtns",    	Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",     Width:150,   SaveName:"anaDtm",    	Align:"Right",  Edit:0, Hidden:0},
                    {Type:"Text",     Width:150,   SaveName:"tblCnt",    	Align:"Right",  Edit:0, Hidden:0},
                    {Type:"Text",     Width:150,   SaveName:"tblErrCnt",    Align:"Right",  Edit:0, Hidden:0},
                    {Type:"Text",     Width:150,   SaveName:"tblErrRt",    	Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",     Width:150,   SaveName:"colCnt",    	Align:"Right",  Edit:0, Hidden:0},
                    {Type:"Text",     Width:150,   SaveName:"colErrCnt",    Align:"Right",  Edit:0, Hidden:0},
                    {Type:"Text",     Width:150,   SaveName:"colErrRt",    	Align:"Center", Edit:0, Hidden:0},
                ];
                    
        InitColumns(cols);
      
        FitColWidth();
        
        SetExtendLastCol(1);
        SetCountPosition(0);
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(pdm_sheet);    
    //===========================
   
}

function initColGrid()
{
    
    with(col_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='NIA.HEADER.COL.ANA'/>", Align:"Center"}
                    /* No.|상태|선택|진단내용|진단일자|컬럼 총 건수|특수문자 건수|특수문자 진단율(%)|공백 건수|공백 진단율(%)|오탈자 건수|오탈자 누락율(%) */
                ];
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",      Width:40,    SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status",   Width:40,    SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:50,    SaveName:"ibsCheck",     Align:"Center", Edit:0, Hidden:1, Sort:0},
                    {Type:"Text",     Width:400,   SaveName:"anaCtns",    	Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",     Width:150,   SaveName:"anaDtm",    	Align:"Right",  Edit:0, Hidden:0},
                    {Type:"Text",     Width:150,   SaveName:"colCnt",    	Align:"Right",  Edit:0, Hidden:0},
                    {Type:"Text",     Width:150,   SaveName:"colNcharCnt",  Align:"Right",  Edit:0, Hidden:0},
                    {Type:"Text",     Width:150,   SaveName:"colNcharRt",   Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",     Width:150,   SaveName:"colSpaceCnt",  Align:"Right",  Edit:0, Hidden:0},
                    {Type:"Text",     Width:150,   SaveName:"colSpaceRt",   Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",     Width:150,   SaveName:"colErrCnt",    Align:"Right",  Edit:0, Hidden:0},
                    {Type:"Text",     Width:150,   SaveName:"colErrRt",    	Align:"Center", Edit:0, Hidden:0},
                ];
                    
        InitColumns(cols);
      
        FitColWidth();
        
        SetExtendLastCol(1);
        SetCountPosition(0);
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(col_sheet);    
    //===========================
   
}

function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Save" :
        	if(searchFlag=='Pdm') {
	        	//TODO 공통으로 처리...
	        	var SaveJson = pdm_sheet.GetSaveJson(1); 
	        	
// 	        	if(SaveJson.data.length == 0) return;
	
	            var url = "<c:url value="/dq/model/regNiaPdmAna.do"/>";
	        	var param = "";
	        	IBSpostJson2(url, SaveJson, param, ibscallback);
        	} else if(searchFlag=='Col') {
            	//TODO 공통으로 처리...
            	var SaveJson = col_sheet.GetSaveJson(1); 
            	
//             	if(SaveJson.data.length == 0) return;

                var url = "<c:url value="/dq/model/regNiaColAna.do"/>";
            	var param = "";
            	IBSpostJson2(url, SaveJson, param, ibscallback);        		
        	} else {
        		showMsgBox("ERR", "진단 수행 후 저장을 진행해주세요.");
        	}
        	break;
            
        case "Search":
        	if(searchFlag == 'All') {
        		$("#pdmArea").hide();
        		$("#colArea").hide();
        	}
        	
        	var param = "searchFlag=" + searchFlag;
        	
        	grid_sheet.DoSearch("<c:url value="/dq/model/getNiaPdmColList.do" />", param);
        	break;
        	
        case "AnaPdm":
        	$("#pdmArea").show();
    		$("#colArea").hide();
        	pdm_sheet.DoSearch("<c:url value="/dq/model/getAnaPdm.do" />");
        	break;
        	
        case "AnaCol":
        	$("#pdmArea").hide();
    		$("#colArea").show();
        	col_sheet.DoSearch("<c:url value="/dq/model/getAnaCol.do" />");
        	break;
       
        case "Down2Excel":  //엑셀내려받기
            var fileName;
            if(searchFlag == 'All'){
                fileName = "DB정보 전체목록.xlsx"
            }
            else if(searchFlag == 'Pdm'){
        		fileName = "누락 테이블명_컬럼명 목록.xlsx";
            }
            else
                fileName = "정제 대상 컬럼명 목록.xlsx";
            grid_sheet.Down2Excel({HiddenColumn:1, FileName : fileName});
            break;
            
        case "LoadExcel":  //엑셀업로드
            grid_sheet.LoadExcel({Append:1, Mode:'HeaderMatch'});
            break;
    }       
}
 
function deleteCheck(DelJson){
	
	for(var i=0; i<DelJson.data.length; i++) {
		if(DelJson.data[i].reaTblCnt == null || DelJson.data[i].reaTblCnt == '' || DelJson.data[i].reaTblCnt == '0'){
		
		} else {
			return DelJson.data[i].fullPath;  //삭제 불가일 경우 0 리턴
		}
	}
	return 1;
}


//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	switch(res.action) {
		//삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
// 				doAction("Search");
				//doActionCol("Search");
		
			break;
		//단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			
// 			alert (res.resultVO.progrmFileNm);
			//전체 내용을 다시 조회 할 경우 사용...
			//doAction("Search"); return;  
			
			break;
		//여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
// 			  doAction("Search");
			//저장완료시 요청서 번호 셋팅...
	    	/* if(!isBlankStr(res.ETC.rqstNo)) {
	    		//alert(res.ETC.rqstNo);
	    		$("form#frmSearch input[name=rqstNo]").val(res.ETC.rqstNo);
// 	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("Search");    		
	    	} */
			
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
	
}


/*
    row : 행의 index
    col : 컬럼의 index
    value : 해당 셀의 value
    x : x좌표
    y : y좌표
*/
function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
	
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
}


function grid_sheet_OnSaveEnd(code, message) {
	//alert(code);
	if (code == 0) {
		showMsgBox("INF", "<s:message code='MSG.STRG.SCS' />"); /* 저장 성공했습니다. */
	} else {
		showMsgBox("ERR", "<s:message code='MSG.STRG.FALR' />"); /* 저장 실패했습니다. */
	}
}

function grid_sheet_OnSearchEnd(code, message) {
	if (code == 0) {
// 		if(searchFlag=='Pdm') {
// 			doAction('AnaPdm');
// 		} else if(searchFlag=='Col') {
// 			doAction('AnaCol');
// 		}
	} else {
	}
}

function pdm_sheet_OnSearchEnd(code, message) {
	if (code == 0) {
// 		if(searchFlag=='Pdm') {
			doAction('Search');
// 		} else if(searchFlag=='Col') {
// 			doAction('AnaCol');
// 		}
	} else {
	}
}
function col_sheet_OnSearchEnd(code, message) {
	if (code == 0) {
// 		if(searchFlag=='Pdm') {
			doAction('Search');
// 		} else if(searchFlag=='Col') {
// 			doAction('AnaCol');
// 		}
	} else {
	}
}


</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title">DB정보</div> <!-- 주제영역 관리 -->
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
		<div style="clear:both; height:10px;"><span></span></div>

<%-- 		<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" /> --%>
		<div class="divLstBtn" >	 
            <div class="bt03">
            	<button class="btn_search" id="btnSearch" 	name="btnSearch">전체조회</button> <!-- 조회 -->
			    <button class="btn_search" id="btnAnaPdm" 	name="btnAnaPdm">누락 테이블명/컬럼명 진단</button>
			    <button class="btn_apply" id="btnAnaCol" 	name="btnAnaCol">컬럼한글명 진단</button>
			    <button class="btn_save" id="btnSave" 	name="btnSave">진단결과 <s:message code="STRG" /></button> <!-- 저장 --> 
			</div>
			<div class="bt02">
				<button class="btn_excel_down" id="btnExcelDown" name="btnExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->
	    	</div>
        </div>	
		
</div>
<div style="clear:both; height:5px;"><span></span></div>
	<!-- 그리드 입력 입력 -->
	<div id="pdmArea">
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("pdm_sheet", "100%", "100px");</script>
	</div>
	</div>
	<!-- 그리드 입력 입력 -->
	
	<!-- 그리드 입력 입력 -->
	<div id="colArea">
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("col_sheet", "100%", "100px");</script>
	</div>
	</div>
	<!-- 그리드 입력 입력 -->
        
	<div style="clear:both; height:5px;"><span></span></div>
	
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "600px");</script>
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>
</body>
</html>