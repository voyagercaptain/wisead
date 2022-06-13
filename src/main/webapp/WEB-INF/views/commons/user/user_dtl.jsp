<!DOCTYPE html>
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!-- <html> -->
<!-- <head> -->
<!-- <title></title> -->
<script type="text/javascript">
//최근 선택 row

$(document).ready(function(){
	//그리드 초기화
	initsubgrid();	
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub.FitColWidth();
});


function initsubgrid() {

    with(grid_sub){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                       {Text:"No.|상태|권한부여|기관코드|기관명|DB명", Align:"Center"}
                       /* No.|상태|권한부여|기관코드|기관명|DB명 */
                       ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",      Width:70,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status",   Width:30,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:80,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Text",     Width:300,   SaveName:"orgCd",    Align:"Center", Hidden:1},
                    {Type:"Text",     Width:300,   SaveName:"orgNm",    Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",     Width:300,   SaveName:"dbNm",    Align:"Left", Edit:0, Hidden:0}, 
                 
                ];
                    
        InitColumns(cols);
      	//콤보 목록 설정...
	    SetColProperty("dispYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	    SetColProperty("mngrMenuYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	    SetColProperty("menuDcd", 	${codeMap.menuDcdibs});
// 		SetColProperty("aprgId", ${codeMap.approvegroupibs});
		SetColProperty("regTypCd", ${codeMap.regTypCdibs});
      	//콤보코드일때 값이 없는 경우 셋팅값
        InitComboNoMatchText(1, "");
       
      	//히든컬럼 셋팅
//        SetColHidden("ibsStatus",1);
//         SetColHidden("objVers",1);
        FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub);    
    //===========================
  	grid_sub.SetCountPosition(0);
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub.ShowDebugMsg(-1);	
    	
}

$(window).load(function() {
	
});
	 


function grid_sub_OnClick(row, col, value, cellx, celly) {

	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	

}

//체크  이벤트가 발생한다.
function grid_sub_OnChange(Row, Col) {
	 var vChk = grid_sub.GetCellValue(Row,"ibsCheck") ;
	 var orgCd = grid_sub.GetCellValue(Row,"orgCd");      
}


function grid_sub_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		
		var dbName = grid_sheet.GetCellValue(grid_sheet.GetSelectRow(), "dbName");
		
		if (dbName != "undefinded" || dbName != "" ) {
			var arr = dbName.split(',')
			
			// 등록된 DB정보로 check 상태로 변경
			for(var i = 1; i <= grid_sub.RowCount(); i++) {
		 		 
		 		var dbNm =  grid_sub.GetCellValue(i,"dbNm");
		 		 
		 		arr.forEach (
		 			function(item){
		 		    	if (item == dbNm) {
		 		    		grid_sub.SetCellValue(i,"ibsCheck", "1"); 
		 		    	}
		 			});
		 		
		 	 }	
		}
	}
}
</script>

<!-- </head> -->
<!-- <body>     -->
 <!-- 검색조건 입력폼 -->
<div id="search_div">       
    
    
        <form id="frmUsergId" name="frmUsergId" method="post">
      	  <input type="hidden" id="usergId" name="usergId" />
        </form>
	
    
     <!-- 조회버튼영역  -->
    <%-- <tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" /> --%> 
     <!-- 조회버튼영역  -->
</div>
 <!-- 검색조건 입력폼 End -->    
<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 입력 입력 -->
	<div class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sub", "100%", "200px");</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
	
<!-- </body> -->
<!-- </html> -->
