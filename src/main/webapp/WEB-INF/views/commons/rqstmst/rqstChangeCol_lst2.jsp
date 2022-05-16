<!DOCTYPE html>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- <html> -->
<!-- <head> -->
<!-- <title></title> -->
<script type="text/javascript">

$(document).ready(function(){
	//그리드 초기화
	initChgGridCol2();
	//그리드 사이즈 조절 초기화...		
	//bindibsresize();
});


$(window).load(function(){
    $(window).resize();
});


$(window).resize( function(){
    	//그리드 가로 스크롤 방지
    	grid_changecol2.FitColWidth();
    }
);

function initChgGridCol2()
{
	
	with(grid_changecol2){
		
		var cfg = {SearchMode:2,Page:100};
		SetConfig(cfg);
		
 		SetMergeSheet(msAll);
		
		var headers = [
					{Text:"<s:message code='COMMON.HEADER.RQSTCHANGE.LST'/>", Align:"Center"}
					/* 선택|No.|상태|요청번호|요청일련번호|항목명|변경전|변경후 */
				];
		
		var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
		
		InitHeaders(headers, headerInfo); 
		
		var cols = [						
	                {Type:"CheckBox", Width:60,   SaveName:"ibsCheck",    Align:"Center", ColMerge:0 ,Edit:0, Hidden:1, Sort:0},
					{Type:"Seq",    Width:50,   SaveName:"ibsSeq",       Align:"Center",  ColMerge:0, Edit:0, Hidden:1},
	                {Type:"Status", Width:40,   SaveName:"ibsStatus",    Align:"Center", ColMerge:0, Edit:0, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"rqstNo",  Align:"Center", ColMerge:1, Edit:0, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"rqstSno",  Align:"Center", ColMerge:1, Edit:0, Hidden:1},
					{Type:"Text",   Width:300,  SaveName:"itemNm",	Align:"Left", 	 ColMerge:0, Edit:0, Hidden:0},
					{Type:"Text",   Width:400,  SaveName:"beforeChg",	Align:"Left", 	 ColMerge:0, Edit:0, Hidden:0},
					{Type:"Text",   Width:400,  SaveName:"afterChg",	Align:"Left", 	 ColMerge:0, Edit:0},
				];
		
		InitColumns(cols);
		SetSheetHeight(250);
		FitColWidth();  
		SetExtendLastCol(1);	
	}
	
	//==시트설정 후 아래에 와야함=== 
	init_sheet(grid_changecol2);	
	//===========================
	
	//grid_sheet.SetColHidden(Col, Hidden)
}


/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_changecol2_OnDblClick(row, col, value, cellx, celly) {
	if(row < 1) return;
}

function grid_changecol2_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
}

function grid_changecol2_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
}
</script>

<!-- </head> -->
<!-- <body>     -->

	<div style="clear:both; height:10px;"><span></span></div>
		<div class="stit"><s:message code="CHG.ITEM.DTL.INFO" /></div> <!-- 변경항목 상세정보 -->
		<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 입력 입력 -->
<div class="grid_01">
     <script type="text/javascript">createIBSheet("grid_changecol2", "100%", "200px");</script>
</div>
<!-- 그리드 입력 입력 End -->
	
<!-- </body> -->
<!-- </html> -->
