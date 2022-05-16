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
<title><s:message code="STRD.WORD.EXCEL.UPLOAD"/></title> <!-- 표준단어엑셀업로드 -->
<!-- <link rel="stylesheet" type="text/css" href="css/design.css"> -->

<script type="text/javascript">

$(document).ready(function() {

	// 엑셀내리기 Event Bind
    $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
	
	//조회
    $("#btnSearch").click( function(){
    	doAction("Search"); 
    } ).show();

	// 엑셀내리기 Event Bind
    $("#btnExcelDown2").click( function(){ doAction("AllDown2Excel"); } ).show();
	
	//조회
    $("#btnSearch2").click( function(){
    	doAction("AllSearch"); 
    } ).show();
	

});

$(window).load(function() {
	initGrid2();
	initGrid();


	grid_sheet2.SetCellBackColor(0,"sditmPnm","#6F5747");
	grid_sheet2.SetCellBackColor(0,"sditmLnm","#6F5747");
	grid_sheet2.SetCellBackColor(0,"infotpLnm","#6F5747");
	grid_sheet2.SetCellBackColor(0,"objDescn","#6F5747");
});


$(window).resize(
    
);



function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext = "No.|상태|선택|매핑내용|매핑일자|전체 컬럼수|매핑건수|매핑율(%)"
        
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",      Width:40,    SaveName:"ibsSeq",       Align:"Center", Edit:0,Hidden:1},
                    {Type:"Status",   Width:40,    SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:50,    SaveName:"ibsCheck",     Align:"Center", Edit:0, Hidden:1, Sort:0},
                    {Type:"Combo",     Width:400,   SaveName:"anaCd",    	Align:"Center",   Edit:0, Hidden:0},
                    {Type:"Text",     Width:250,   SaveName:"anaDtm",    	Align:"Center",  Edit:0, Hidden:0},
                    {Type:"Text",     Width:250,   SaveName:"totColCnt",    	Align:"Center",  Edit:0, Hidden:0},
                    {Type:"Text",     Width:250,   SaveName:"mapCnt",    Align:"Center",  Edit:0, Hidden:0},
                    {Type:"Text",     Width:250,   SaveName:"mapRt",    	Align:"Center", Edit:0, Hidden:0}
                ];

        SetCountPosition(0);
        
        InitColumns(cols);

        SetColProperty("anaCd", 	${codeMap.niaStndAnaCdibs});
        
        FitColWidth();
        
        SetExtendLastCol(1);  

    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
}


function initGrid2()
{
    
    with(grid_sheet2){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext = "<s:message code='META.HEADER.MAP.PDMSTND.LST2'/>";
        //No.|상태|선택|매핑여부|mapid|컬럼id|기관명|DB명|테이블영문명|테이블한글명|컬럼영문명|컬럼한글명|데이터타입|데이터길이|소수점|표준용어id|표준용어명|영문약어명|도메인명|표준용어설명
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",         Align:"Center",  Edit:0},        
                    {Type:"Status", Width:50,   SaveName:"ibsStatus",         Align:"Center",  Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:50,    SaveName:"ibsCheck",     Align:"Center", Edit:1, Hidden:1, Sort:0},
                    {Type:"Combo",   Width:80,  SaveName:"mapYn", 	   Align:"Center",  Edit:0, Hidden:0},         
                    {Type:"Text",     Width:150,   SaveName:"mapId",    	Align:"Left",   Edit:0, Hidden:1},                                
                    {Type:"Text",     Width:150,   SaveName:"colId",    	Align:"Left",   Edit:0, Hidden:1},    
                    {Type:"Text",   Width:100,  SaveName:"orgNm",          Align:"Left",    Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"dbNm",           Align:"Left",    Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"tblPnm",         Align:"Left",    Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"tblLnm",         Align:"Left",    Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"colPnm",         Align:"Left",    Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"colLnm",         Align:"Left",    Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"dataType",         Align:"Left",    Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"dataLen",         Align:"Left",    Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"dataScal",         Align:"Left",    Edit:0}, 
                    
                    {Type:"Text",   Width:130,  SaveName:"sditmId",         Align:"Left",    Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"sditmLnm",         Align:"Left",    Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"sditmPnm",         Align:"Left",    Edit:0},
                    {Type:"Text",   Width:100,  SaveName:"infotpLnm",	 Align:"Left", Edit:0, Hidden:0}, //도메인명
                    {Type:"Text",   Width:200,  SaveName:"objDescn",	 Align:"Left", 	 Edit:0, ColMerge:0,Hidden:0} ,
                    {Type:"Combo",   Width:100,  SaveName:"stndNm",	 Align:"Left", Edit:0, Hidden:1}, 
                ];
                    
        InitColumns(cols);
	     
        //콤보 목록 설정...
       SetColProperty("mapYn",{ComboCode:"N|Y", ComboText:"비매핑|매핑"});
        SetColProperty("stndNm",{ComboCode:"ORG|DB", ComboText:"기관표준용어|DB표준용어 "});

        InitComboNoMatchText(1, "");
      
        FitColWidth();  
        
        SetExtendLastCol(1);    

    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet2);    
    //===========================
}


function doAction(sAction)
{
        
    switch(sAction)
    {		    
        

    	case "Down2Excel":  //엑셀다운로드
    	    var fileName="DB항목 매핑율.xlsx";
    		grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, Mode:2, FileName : fileName});
        	break;

        case "Search":
        	var url = '<c:url value="/dq/model/getWamPdmSditmMapAna.do"/>';
        	var param="";
        	grid_sheet.DoSearch(url, param);
        	
        	break;

    	case "AllDown2Excel":  //엑셀다운로드
    	    var fileName="DB항목 매핑_비매핑 목록.xlsx";
    		grid_sheet2.Down2Excel({HiddenColumn:1, FileName : fileName});
        	break;

        case "AllSearch":
        	var url = '<c:url value="/dq/model/getWamNiaPdmAllMapList.do"/>';
        	var param="";
        	grid_sheet2.DoSearch(url, param);
        	
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
}

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg){
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		//조회 성공....
	}	
}
/*======================================================================*/
//IBSpostJson2 후처리 함수
/*======================================================================*/
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	switch(res.action) {
			
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			break;
		
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
			break;
				
		//매핑율 저장 후처리
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
}

</script>
</head>

<body>

<div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title">코드정의서</div> <!-- 항목 자동분할 -->
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>
</div>
	<div id="tabs">
	
		<ul>
		    <li id="tab-ALL"><a href="#tabs-all">전체조회</a></li>
		    <li id="tab-RESULT"><a href="#tabs-result">DB항목 매핑율</a></li>
		</ul>
		
		<div id="tabs-all">
		    <button class="btn_search" id="btnSearch2" 	name="btnSearch2">조회</button> <!-- 조회 -->
			<div class="bt02"><button class="btn_excel_down" id="btnExcelDown2" name="btnExcelDown2"><s:message code="EXCL.DOWNLOAD" /></button></div><!-- 엑셀 내리기 -->
		    <div style="clear:both; height:5px;"><span></span></div>
			<!-- 그리드 입력 입력 -->
			
			<div class="grid_01" id="grid_02">
			     <script type="text/javascript">createIBSheet("grid_sheet2", "100%", "500px");</script>            
			</div>		
		</div>
		<div id="tabs-result">

		    <button class="btn_search" id="btnSearch" 	name="btnSearch">진단결과 조회</button> <!-- 조회 -->
			<div class="bt02"><button class="btn_excel_down" id="btnExcelDown" name="btnExcelDown"><s:message code="EXCL.DOWNLOAD" /></button></div><!-- 엑셀 내리기 -->
		    <div style="clear:both; height:5px;"><span></span></div>
			<!-- 그리드 입력 입력 -->
			
			<div class="grid_01" id="grid_01">
			     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "500px");</script>            
			</div>
		
		</div>
	
	</div> 	
	
</div>


</body>
</html>

