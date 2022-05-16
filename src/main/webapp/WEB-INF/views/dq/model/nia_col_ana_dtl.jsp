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
        // 조회 Event Bind
        $("#btnColSearch").click(function(){ doColAction("Search");  }).show();
                
        // 엑셀내리기 Event Bind
        $("#btnColExcelDown").click( function(){ doColAction("Down2Excel"); } );

      
});

$(window).load(function() {
// 	initColGrid();
	$(window).resize();
});


$(window).resize(function(){
    //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
// 	setibsheight($("#grid_01"));
	// grid_sheet.SetExtendLastCol(1);    
});

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
                    {Type:"Seq",      Width:60,    SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status",   Width:40,    SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:50,    SaveName:"ibsCheck",     Align:"Center", Edit:0, Hidden:1, Sort:0},
                    {Type:"Text",     Width:300,   SaveName:"anaCtns",    	Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",     Width:150,   SaveName:"anaDtm",    	Align:"Right",  Edit:0, Hidden:0},
                    {Type:"Text",     Width:150,   SaveName:"colCnt",    	Align:"Right",  Edit:0, Hidden:0},
                    {Type:"Text",     Width:150,   SaveName:"colNcharCnt",  Align:"Right",  Edit:0, Hidden:0},
                    {Type:"Text",     Width:150,   SaveName:"colNcharRt",   Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",     Width:150,   SaveName:"colSpaceCnt",  Align:"Right",  Edit:0, Hidden:0},
                    {Type:"Text",     Width:150,   SaveName:"colSpaceRt",   Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",     Width:150,   SaveName:"colErrCnt",    Align:"Right",  Edit:0, Hidden:0},
                    {Type:"Text",     Width:150,   SaveName:"colErrRt",    	Align:"Center", Edit:0, Hidden:0}
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

function doColAction(sAction)
{
        
    switch(sAction)
    {            
        case "Search":
        	col_sheet.DoSearch("<c:url value="/dq/model/getNiaColAnaList.do" />");
        	break;
       
        case "Down2Excel":  //엑셀내려받기
            col_sheet.Down2Excel({HiddenColumn:1, FileName:'컬럼한글명 진단 결과'});
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
function col_sheet_OnDblClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
	
}

function col_sheet_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
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
            	<button class="btn_search" id="btnColSearch" 	name="btnColSearch">진단결과 조회</button> <!-- 조회 -->
			</div>
			<div class="bt02">
				<button class="btn_excel_down" id="btnColExcelDown" name="btnColExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->
	    	</div>
        </div>	
		
</div>
<div style="clear:both; height:5px;"><span></span></div>
	
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("col_sheet", "100%", "650px");</script>
	</div>
	
<div style="clear:both; height:5px;"><span></span></div>
</body>
</html>