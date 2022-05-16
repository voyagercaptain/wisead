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
<script type="text/javascript">
	var flag = 1;
	$(document).ready(function() {

        // 조회 Event Bind
        $("#btnSearch3").click(function(){flag = 1; doAction("Search");  });

        $("#btnSearch").click(function(){flag = 2; doAction("CodeSearch");  });
        
        // 엑셀내리기 Event Bind
        $("#btnExcelDown3").click( function(){ doAction("Down2Excel"); } ).show();

	    	
	});

	
	$(window).load(function() {
			
		initGrid();
	});
	
	function initGrid()
	{
	    
	    with(grid_sheet){
	    	
	    	var cfg = {SearchMode:2,Page:100};
	        SetConfig(cfg);
	        //merge 설정
	        SetMergeSheet(0);
	        
			var headtext  = "<s:message code='NIA.HEADER.PDM.COL.RQST'/>";
	
			
			var headers = [
						{Text:headtext, Align:"Center"}
					];
			
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
			
			InitHeaders(headers, headerInfo); 
	
			var cols = [						
		                   {Type:"Seq",      Width:40,    SaveName:"ibsSeq",       Align:"Center", Edit:0},
		                    {Type:"Status",   Width:40,    SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
		                    {Type:"CheckBox", Width:50,    SaveName:"ibsCheck",     Align:"Center", Edit:0, Hidden:1, Sort:0},
		                    {Type:"Text",     Width:200,   SaveName:"colId",    	Align:"Left",   Edit:0, Hidden:1},
		                    {Type:"Text",     Width:200,   SaveName:"orgNm",    	Align:"Left",   Edit:0, Hidden:0},
		                    {Type:"Text",     Width:100,   SaveName:"dbNm",    	    Align:"Left",   Edit:0, Hidden:0},
		                    {Type:"Text",     Width:200,   SaveName:"tblPnm",    	Align:"Left",   Edit:0, Hidden:0},
		                    {Type:"Text",     Width:200,   SaveName:"tblLnm",    	Align:"Left",   Edit:0, Hidden:0},
		                    {Type:"Text",     Width:200,   SaveName:"colPnm",    	Align:"Left",   Edit:0, Hidden:0},
		                    {Type:"Text",     Width:200,   SaveName:"colLnm",    	Align:"Left",   Edit:0, Hidden:0},
		                    {Type:"Text",     Width:100,   SaveName:"stndCdYn",    	Align:"Left", Edit:0, Hidden:0},
		                    {Type:"Text",     Width:300,   SaveName:"stndCdNm",    	Align:"Left",   Edit:0, Hidden:0},
		                    {Type:"Text",     Width:300,   SaveName:"colDescn",    	Align:"Left",   Edit:0, Hidden:0},
		                    {Type:"Text",     Width:100,   SaveName:"dataType",    	Align:"Left", Edit:0, Hidden:0},
		                    {Type:"Text",     Width:50,   SaveName:"dataLen",    	Align:"Left",   Edit:0, Hidden:0},
		                    {Type:"Text",     Width:50,   SaveName:"dataScal",    	Align:"Left",   Edit:0, Hidden:0},
		                    {Type:"Text",     Width:100,   SaveName:"dataFrm",    	Align:"Left",   Edit:0, Hidden:0},
		                    {Type:"Text",     Width:50,   SaveName:"notNullYn",    Align:"Left", Edit:0, Hidden:0},
		                    {Type:"Text",     Width:100,   SaveName:"pkYn",    	    Align:"Left", Edit:0, Hidden:0},
		                    {Type:"Text",     Width:100,   SaveName:"fkYn",    	    Align:"Left", Edit:0, Hidden:0},
		                    {Type:"Text",     Width:200,   SaveName:"constrnt",    	Align:"Left",   Edit:0, Hidden:0},
		                    {Type:"Text",     Width:50,   SaveName:"prsnYn",    	Align:"Left",   Edit:0, Hidden:0},
		                    {Type:"Text",     Width:50,   SaveName:"encYn",    	Align:"Left", Edit:0, Hidden:0},
		                    {Type:"Text",     Width:50,   SaveName:"openYn",    	Align:"Left", Edit:0, Hidden:0}
						
					];
						
			InitColumns(cols);
			
			//콤보 목록 설정
// 			SetColProperty("rvwStsCd", 	${codeMap.rvwStsCdibs});
// 			SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
			SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
// 			SetColProperty("vrfCd", 	${codeMap.vrfCdibs});
	//		SetColProperty("stndAsrt", 	${codeMap.stndAsrtibs});
			//SetColProperty("persInfoGrd", 	${codeMap.persInfoGrdibs});
			
	// 		SetColProperty("dmngId", 	${codeMap.dmngibs});
			//SetColProperty("infotpId",	${codeMap.infotpibs});
	// 		SetColProperty("encYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	// 		SetColProperty("dupYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	// 		SetColProperty("persInfoYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
			
			InitComboNoMatchText(1, "");
			
		  
// 			FitColWidth();  
		    //SetSheetHeight(200);
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
	       		var param = $("#frmSearch3").serialize();
	       		
				grid_sheet.DoSearch("<c:url value="/dq/stnd/getdbcodegaplist.do" />", param);
				
	        	break;

		    case "CodeSearch":
       		var param = $("#frmSearch3").serialize();
       		
			grid_sheet.DoSearch("<c:url value="/dq/stnd/getdbcodeexistlist.do" />", param);
			
        	break;	        	

	       
	        case "Down2Excel":  //엑셀내려받기
	        	if(flag == 1)
	           		grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:"DB 코드정의서 진단결과.xlsx"});
	        	else
	        		grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:"DB 코드정의서 일치목록.xlsx"});
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
	
	}
	
	function grid_sheet_OnClick(row, col, value, cellx, celly) {
	}
	
	//상세정보호출
	function loadDetail(param) {
	}
	
	
	function grid_sheet_OnSaveEnd(code, message) {
	
	}
	
	function grid_sheet_OnSearchEnd(code, message) {
	//alert(grid_sheet.GetDataBackColor()+":"+ grid_sheet.GetDataAlternateBackColor());
		if(code < 0) {
			showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
			return;
		} else {
			//form 내용을 초기화 한다.....
			//doAction('Add');
			//$('#btnfrmReset').click();
			//alert("Search End");
			//테이블 요청 리스트가 조회되면...
			//첫번째 행을 선택하고 하위 컬럼 요청서를 조회한다...
			
		}
	}
</script>
</head>
<body>

<div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"></div>
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>
<div style="clear:both; height:5px;"><span></span></div>
<input id="tabNm" name="tabNm" type="hidden" value="SDITM"/>
 <div id="search_div3">
<!--         <div class="stit">검색조건</div> -->
        <!-- 조회버튼영역  -->
		<button class="btn_search" id="btnSearch3" 	name="btnSearch3">불일치 DB정보</button>
       &nbsp
       <button class="btn_search" id="btnSearch" 	name="btnSearch">일치 DB정보</button> <!-- 조회 -->
	<div class="bt02"><button class="btn_excel_down" id="btnExcelDown3" name="btnExcelDown3"><s:message code="EXCL.DOWNLOAD" /></button></div><!-- 엑셀 내리기 -->                               <div style="clear:both; height:5px;"><span></span></div>
         <form id="frmSearch3" name="frmSearch3" method="post">
           <fieldset>
           <legend>머리말</legend>
           </fieldset>
        
       </form>
</div>
 <div style="clear:both; height:5px;"><span></span></div>
   <div class="grid_01" id="grid_03">
   
     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "500px");</script>            
</div>
	<!-- 그리드 입력 입력 End -->
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>
	
</div>
</body>
</html>