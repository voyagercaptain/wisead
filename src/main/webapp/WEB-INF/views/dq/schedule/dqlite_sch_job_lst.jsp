<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<script type="text/javascript">
$(document).ready(function() {

	
	//추가 버튼
	$("#btnSubSearch").click(function(){ 

		var shdKndCd = $("#shdKndCd").val();
    	var url = "";
    	var popwin = "";
    	var shdId = $("#shdId").val();
    	var shdKndNm = encodeURIComponent(grid_sub.GetCellText(1, "shdKndCd"));
		var param = "shdKndCd="+shdKndCd+"&shdKndNm="+shdKndNm+"&shdId="+shdId;
		
		if (shdKndCd == "GN") {
    		url = "<c:url value='/dq/schedule/selectJobGnPop.do' />";
    		popwin = OpenModal(url+"?"+param, "scheduleJopGnPop",  600, 200, "no"); 	
		/* } else if (shdKndCd == "PY") {
    		url = "<c:url value='/commons/damgmt/schedule/selectJobPyPop.do' />";
    		popwin = OpenModal(url+"?"+param, "scheduleJopGnPop",  900, 700, "no"); */ 	
    	} else {
	    	url = "<c:url value='/dq/schedule/dqlite_sch_job_pop.do' />";
	    	popwin = OpenWindow(url+"?"+param, "scheduleJopPop",  900, 750, "no"); 	
    	}
		popwin.focus();
		
	}).find(".ui-button-text").text("추가");

	//저장 버튼
	$('#btnJobSave').click(function(){
		//$("#btnSave").click();
		btnSaveClick();
 	});
	
	//삭제 버튼
	$("#btnSubDelete").click(function(){
		if(checkDelIBS (grid_sub, "<s:message code="ERR.CHKDEL" />")) {
			showMsgBox("CNF", "<s:message code="MSG.DEL.YN" />", "DeleteJob"); /* 삭제 하시겠습니까? */
		}
	});
	
});

$(window).load(function(){
	initsubgrid();
// 	doAction('SearchAllDtl');
	grid_sub.RemoveAll();
	
});

$(window).resize(
	    function(){
	    	//그리드 가로 스크롤 방지
// 	    	grid_sheet.FitColWidth();
	    }
	);

function initsubgrid()
{
    
    with(grid_sub){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"No.|상태|선택|스케줄ID|스케줄작업ID|작업형태|진단대상명|작업명|작업설명|작업유형|등록여부 ", Align:"Center"}
                    /* No.|상태|선택|스케줄ID|스케줄작업ID|작업형태|진단대상명|작업명|작업설명|작업유형|등록여부 */
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    	Width:100,   SaveName:"ibsSeq",      Align:"Center", Edit:0, Hidden:0},
                    {Type:"Status", 	Width:100,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:0},
                    {Type:"CheckBox", 	Width:100,   SaveName:"ibsCheck",   Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Text", 		Width:100,   SaveName:"shdId",  		Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text", 		Width:100,   SaveName:"shdJobId",       Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo",  	Width:200,   SaveName:"shdKndCd",      Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text", 		Width:100,   SaveName:"dbConnTrgLnm",  Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:300,   SaveName:"shdJobNm",      Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:300,   SaveName:"etcJobDtls",    Align:"Left", Edit:0, Hidden:0},
                    {Type:"Combo",   	Width:200,   SaveName:"etcJobKndCd",   Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:50,    SaveName:"regYn",    		Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:300,   SaveName:"etcJobNm",      Align:"Left", Edit:0, Hidden:1},
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);
        SetColProperty("shdKndCd", ${codeMap.schdKndCdibs});
        SetColProperty("etcJobKndCd", ${codeMap.etcJobKndCdibs});
        
        InitComboNoMatchText(1, "");
      
        FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub);    
    //===========================
   
}


	
</script>

<div id="search_div">    
	<div class="stit"><s:message code="JOB.DTL.INFO" /></div> <!-- 작업 상세정보 -->
		<div style="clear:both; height:5px;"><span></span></div>
	 	<!-- 조회버튼영역  -->
	    <tiles:insertTemplate template="/WEB-INF/decorators/buttonSub.jsp" />
	    <div id="divJobbtn" style="text-align: center;">
			<button class="btn_search" id="btnJobSave" name="btnJobSave" style="margin-left:4px; float:left;"><s:message code="STRG" /></button> <!-- 저장 -->
		</div>
	    <!-- 조회버튼영역  -->
	    <div style="clear:both; height:5px;"><span></span></div>
		<!-- 그리드 입력 입력 -->
		<div class="grid_01">
		     <script type="text/javascript">createIBSheet("grid_sub", "98%", "250px");</script>            
		</div>
		<!-- 그리드 입력 입력 -->
</div>
<!-- </body> -->

<!-- </html> -->