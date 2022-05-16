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
	$(document).ready(function() {


        $("#btnDupDel").click(function(){
            showMsgBox("CNF", "중복데이터를 삭제하시겠습니까?", 'DupDelete'); 
        }).show();

        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("SditmSearch");  });
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown1").click( function(){ doAction("SditmDown2Excel"); } ).show();

//         $("#btnExe").click(function(){
//             var path = String.fromCharCode(34)+'C:\\col_m\\StandardIntegration_v2.exe'+String.fromCharCode(34) ;
//             var WshShell = new ActiveXObject("WScript.Shell");
//             WshShell.Run(path);
//         });
	    	
	});

	
	$(window).load(function() {
			
		initGrid();
		doAction("SditmSearch");
		
	});
	
	function initGrid()
	{
	    
	    with(grid_sditm){
	    	
	    	var cfg = {SearchMode:2,Page:100};
	        SetConfig(cfg);
	        //merge 설정
	        
			var headtext  = "No.|상태|선택|컬럼아이디|기관명|DB명|컬럼한글명|컬럼설명|컬럼영문명|데이터타입|데이터길이|소수점|데이터형식|NotNull여부|PK정보|FK정보|제약조건|개인정보여부|암호화여부|공개비공개여부"
			
			var headers = [
						{Text:headtext, Align:"Center"}
					];
			
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
			
			InitHeaders(headers, headerInfo); 
	
			var cols = [						
						{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	  Align:"Center", Edit:0},
						{Type:"Status", Width:40,   SaveName:"ibsStatus", Align:"Center", Edit:0, Hidden:1},
						{Type:"CheckBox", Width:45, SaveName:"ibsCheck",  Align:"Center", Edit:0, Hidden:0, Sort:0, Hidden:1},
						{Type:"Text",   Width:15,  SaveName:"colId",     Align:"Center", Edit:0, KeyField:0, Hidden:1},
						{Type:"Text",   Width:100,  SaveName:"orgNm",     Align:"Center", Edit:0, KeyField:0},
						{Type:"Text",   Width:130,  SaveName:"dbNm",     Align:"Center", Edit:0, KeyField:0,Hidden:1},
						{Type:"Text",   Width:130,  SaveName:"colLnm",     Align:"Center", Edit:0, KeyField:0},
						{Type:"Text",   Width:300,  SaveName:"colDescn",	 Align:"Left", 	 Edit:0, KeyField:0, ColMerge:0},
						{Type:"Text",   Width:130,  SaveName:"colPnm",     Align:"Center", Edit:0, KeyField:0},
						{Type:"Text",       Width:120,  SaveName:"dataType",	 	Align:"Center", Edit:0, Hidden:0, KeyField:0},
						{Type:"Int",       Width:100,  SaveName:"dataLen",	 	    Align:"Center", Edit:0, Hidden:0, KeyField:0},
						{Type:"Int",       Width:100,  SaveName:"dataScal",	 	Align:"Center", Edit:0, Hidden:0},
	                    {Type:"Text",   Width:100,  SaveName:"dataFrm", 	 	Align:"Center", Edit:0, Hidden:0, KeyField:0},
	                    {Type:"Text",     Width:100,   SaveName:"notNullYn",    Align:"Left", Edit:0, Hidden:1},
	                    {Type:"Text",     Width:100,   SaveName:"pkYn",    	    Align:"Left", Edit:0, Hidden:1},
	                    {Type:"Text",     Width:100,   SaveName:"fkYn",    	    Align:"Left", Edit:0, Hidden:1},
	                    {Type:"Text",     Width:100,   SaveName:"constrnt",    	Align:"Left",   Edit:0, Hidden:1},
	                    {Type:"Text",     Width:80,   SaveName:"prsnYn",    	Align:"Left",   Edit:0, Hidden:0},
	                    {Type:"Text",     Width:80,   SaveName:"encYn",    	Align:"Left", Edit:0, Hidden:0},
	                    {Type:"Text",     Width:80,   SaveName:"openYn",    	Align:"Left", Edit:0, Hidden:0},
					];
						
			InitColumns(cols);
			
			//콤보 목록 설정
			SetColProperty("rvwStsCd", 	${codeMap.rvwStsCdibs});
			SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
			SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
			SetColProperty("vrfCd", 	${codeMap.vrfCdibs});
	//		SetColProperty("stndAsrt", 	${codeMap.stndAsrtibs});
			//SetColProperty("persInfoGrd", 	${codeMap.persInfoGrdibs});
			
	// 		SetColProperty("dmngId", 	${codeMap.dmngibs});
			//SetColProperty("infotpId",	${codeMap.infotpibs});
	// 		SetColProperty("encYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	// 		SetColProperty("dupYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	// 		SetColProperty("persInfoYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
			
			InitComboNoMatchText(1, "");
			
		  
			FitColWidth();  
		    //SetSheetHeight(200);
			SetExtendLastCol(1);	
	    }
	    
	    //==시트설정 후 아래에 와야함=== 
	    init_sheet(grid_sditm);    
	    //===========================
	   
	}
	
	
	
	
	function doAction(sAction)
	{
	        
	    switch(sAction)
	    {
	        case "SditmSearch":
		        param = "";
	        	grid_sditm.DoSearch("<c:url value="/dq/model/getNiaPdmColList.do" />", param);
	        	break;
				
	       
	        case "SditmDown2Excel":  //엑셀내려받기
	        
	//             grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
	        	
// 	        	//보여지는 컬럼들만 엑셀 다운로드          
// 	            var downColNms = "";
// 	           	for(var i=0; i<grid_sheet.LastCol();i++ ){
// 	           		if(grid_sheet.GetColHidden(i) != 1){
// 	           			downColNms += grid_sheet.ColSaveName(0,i)+ "|";
// 	           		}
// 	           	}
// 	            var downColNms2 = "";
// 	       	    for(var i=0; i<grid_sheet2.LastCol();i++ ){
// 	       		  if(grid_sheet2.GetColHidden(i) != 1){
// 	       			  downColNms2 += grid_sheet2.ColSaveName(0,i)+ "|";
// 	       		  }
// 	       	    }
// 	       	 var downColNms3 = "";
// 	    	    for(var i=0; i<grid_sheet3.LastCol();i++ ){
// 	    		  if(grid_sheet3.GetColHidden(i) != 1){
// 	    			  downColNms3 += grid_sheet3.ColSaveName(0,i)+ "|";
// 	    		  }
// 	    	    }
	           	grid_sditm.Down2Excel({HiddenColumn:1, Merge:1, FileName:"현행용어사전.xlsx"});
	            break;


	        case "DupDelete":
	            var url = "<c:url value="/dq/model/delDupData.do"/>";
	            var param = "";
	            grid_sditm.DoSearch(url, param);
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
	function grid_sditm_OnSearchEnd(code, message) {
	//alert(grid_sheet.GetDataBackColor()+":"+ grid_sheet.GetDataAlternateBackColor());
		if(code < 0) {
			showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
			return;
		} else {
			
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
<button class="btn_search" id="btnSearch" 	name="btnSearch"><s:message code="INQ"/></button> <!-- 조회 -->
<button class="btn_search" id="btnDupDel" name="btnDupDel">현행용어사전 생성</button>
<div class="bt02"><button class="btn_excel_down" id="btnExcelDown1" name="btnExcelDown1"><s:message code="EXCL.DOWNLOAD" /></button></div><!-- 엑셀 내리기 -->                       
		<div style="clear:both; height:5px;"><span></span></div>
		<div class="grid_01" id="grid_01">
		
			     <script type="text/javascript">createIBSheet("grid_sditm", "100%", "500px");</script>            
			
			</div>


	<!-- 그리드 입력 입력 End -->
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>
	
</div>
</body>
</html>