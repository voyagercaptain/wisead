<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="kr.wise.commons.WiseMetaConfig" %>

<!-- <html> -->
<!-- <head> -->
<!-- <title>컬럼 등록</title> -->

<script type="text/javascript">

$(document).ready(function() {
	
		//검색조건 display none
// 		$("div#col_search_div").hide();
// 		$("form#frmColInput").hide();
 		$("#btnColExcelDown").hide();
		
    }
);

$(window).load(function() {
	//그리드 초기화 
// 	initColHlGrid();
	
});


// $(window).resize();


function initColHlGrid()
{
    
    with(colh_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        SetMergeSheet(1);
        var headtext = "<s:message code='META.HEADER.COLTOCOLHIST.DTL'/>";
 
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",     Width:50,  SaveName:"ibsSeq",        Align:"Center", Edit:0},
                    {Type:"Combo",    Width:50,  SaveName:"regTypCd"     ,Align:"Center",   Edit:0, ColMerge:0},
                    {Type:"Text",    Width:50,  SaveName:"objVers"     ,Align:"Center",   Edit:0, ColMerge:0},
                    {Type:"Text",    Width:100,  SaveName:"pdmColId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Date",    Width:100,  SaveName:"expDtm"     ,Align:"Center",   Edit:0, Format:"yyyy-MM-dd", ColMerge:0},
                    {Type:"Date",    Width:100,  SaveName:"strDtm"     ,Align:"Center",   Edit:0 , Format:"yyyy-MM-dd", ColMerge:0},
/*                     {Type:"Text",    Width:150, SaveName:"dbConnTrgLnm",	Align:"Left", 	Edit:0},
                    {Type:"Text",    Width:150, SaveName:"dbConnTrgPnm",	Align:"Left", 	Edit:0}, 
                    {Type:"Text",    Width:150, SaveName:"dbSchLnm",		Align:"Left",   Edit:0},
                    {Type:"Text",    Width:150, SaveName:"dbSchPnm",		Align:"Left",   Edit:0}, */
                    {Type:"Combo",    Width:150, SaveName:"dbConnTrgId",	Align:"Left", 	Edit:0}, 
                    {Type:"Combo",    Width:150, SaveName:"dbSchId",		Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"pdmTblId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:150,  SaveName:"pdmTblLnm"     ,Align:"Left",   Edit:0, ColMerge:1},
                    {Type:"Text",    Width:150,  SaveName:"pdmColPnm"     ,Align:"Left",   Edit:0, ColMerge:0},
                    {Type:"Text",    Width:150,  SaveName:"pdmColLnm"     ,Align:"Left",   Edit:0, ColMerge:0},
                    {Type:"Text",    Width:50,  SaveName:"colOrd"     ,Align:"Center",   Edit:0, ColMerge:0},
                    {Type:"Text",    Width:100,  SaveName:"dataType"     ,Align:"Center",   Edit:0, ColMerge:0},
                    {Type:"Text",    Width:50,  SaveName:"dataLen"     ,Align:"Center",   Edit:0, ColMerge:0},
                    {Type:"Text",    Width:50,  SaveName:"dataScal"     ,Align:"Center",   Edit:0, ColMerge:0},
                    {Type:"Combo",    Width:50,  SaveName:"pkYn"     ,Align:"Center",   Edit:0, ColMerge:0},
                    {Type:"Text",    Width:50,  SaveName:"pkOrd"     ,Align:"Center",   Edit:0, ColMerge:0},
                    {Type:"Combo",    Width:100,  SaveName:"nonulYn"     ,Align:"Center",   Edit:0, ColMerge:0},
                    {Type:"Text",    Width:100,  SaveName:"defltVal"     ,Align:"Center",   Edit:0, ColMerge:0},
                    {Type:"Text",    Width:100,  SaveName:"rqstUserNm"     ,Align:"Left",   Edit:0, ColMerge:0},
                    {Type:"Text",    Width:100,  SaveName:"rqstDtm"     ,Align:"Center",   Edit:0, Format:"yyyy-MM-dd", ColMerge:0},
                    {Type:"Text",    Width:300,  SaveName:"objDescn"     ,Align:"Left",   Edit:0, ColMerge:0}
                ];
                    
        InitColumns(cols);
        
        SetColProperty("regTypCd", 	{ComboCode:"C|U|D", 	ComboText:"<s:message code='NEW.CHG.DEL' />"});/* 신규|변경|삭제 */
        SetColProperty("pkYn", 	{ComboCode:"N|Y", 	ComboText:"|PK"});
        SetColProperty("nonulYn", 	{ComboCode:"N|Y", 	ComboText:"|NOTNULL"});

        SetColProperty("dbConnTrgId", ${codeMap.dbConnTrgibs});
        SetColProperty("dbSchId", ${codeMap.connTrgSchibs});
        InitComboNoMatchText(1, "");
        SetSheetHeight(250);
        FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(colh_sheet);    
    //===========================
   
}

//화면상의 모든 액션은 여기서 처리...
function doActionColH(sAction)
{
        	//요청 마스터 번호가 있는지 확인...
        	
        	//요청서에 저장할 내역이 있는지 확인...
        	
        	//요청서 마스터 번호로 조회한다...
//         	var param = $('#frmSearch').serialize();
//         	var param = $('#colfrmSearch').serialize();
  switch(sAction)
    {
        case "Search":	        	
        	//선택한 상세정보를 가져온다...
        	var param =  "pdmTblId=" + grid_sheet.GetCellValue(grid_sheet.GetSelectRow(), "pdmTblId");
        	
//         	alert(param);
        	colh_sheet.DoSearch("<c:url value="/dq/model/gethistcollist.do" />", param);
//         	colh_sheet.DoSearchScript("testJsonlist");
        break;
        case "SearchOne":
        	var param =  "pdmColId=" + col_sheet.GetCellValue(col_sheet.GetSelectRow(), "pdmColId");
        	colh_sheet.DoSearch("<c:url value="/dq/model/gethisttblcollist.do" />", param);
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
function colh_sheet_OnDblClick(row, col, value, cellx, celly) {
//     alert("colh dblclick event");
    
	if(row < 1) return;
	
	return;
}

function colh_sheet_OnClick(row, col, value, cellx, celly) {
//     alert("colh click event");
    $("#hdnRow").val(row);
    
    if(row < 1) return;
    
}

</script>
<!-- </head> -->

<!-- <body> -->
<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("colh_sheet", "100%", "200px");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "colh_sheet", "100%", "100%");</script>             --%>
	</div>
	<!-- 그리드 입력 입력 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- </body> -->
<!-- </html> -->