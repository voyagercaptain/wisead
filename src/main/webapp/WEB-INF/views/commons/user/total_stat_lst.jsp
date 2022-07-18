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
<title><s:message code="USER.GRP.MNG" /></title> <!-- 사용자그룹관리 -->

<script type="text/javascript">

//엔터키 이벤트 처리

var interval = "";

$(document).ready(function() {
		$('#btnSearch').css('display', 'none');
	 	$('#btnTreeNew').css('display', 'none');
	 	$('#btnDelete').css('display', 'none');
	 	doAction("Search");
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
        
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
      
    }
);

$(window).load(function() {
	initGrid();
	initGrid2();
});


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
                
        SetMergeSheet(msHeaderOnly);
        
        var headers = [
					{Text:"<s:message code='COMMON.HEADER.TOTALSTSTAT.LST'/>", Align:"Center"},
					 //기관분류명|평가대상 기관수|기관표준 제출 현황(단위:기관 수)|기관표준 제출 현황(단위:기관 수)|기관표준 제출 현황(단위:기관 수)|기관표준 제출 현황(단위:기관 수)|기관표준 제출 현황(단위:기관 수)|기관표준 제출 현황(단위:기관 수)|기관표준 제출 현황(단위:기관 수)|기관표준 제출 현황(단위:기관 수)|평가DB수|DB표준 제출 현황(단위:DB 수)|DB표준 제출 현황(단위:DB 수)|DB표준 제출 현황(단위:DB 수)|DB표준 제출 현황(단위:DB 수)|DB표준 제출 현황(단위:DB 수)|DB표준 제출 현황(단위:DB 수)|DB표준 제출 현황(단위:DB 수)|DB표준 제출 현황(단위:DB 수)
                    {Text:"<s:message code='COMMON.HEADER.TOTALSTSTAT1.LST'/>", Align:"Center"}
					 //기관분류명|평가대상 기관수|용어|제출용|단어|제출율|도메인|제출율|코드|제출율|평가DB수|용어|제출율|단어|제출율|도메인|제출율|코드|제출율
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
        			{Type:"Text",   				     Width:80,   SaveName:"orgType",       Align:"Center", 	Edit:0}, //기관명                    
                    {Type:"Int",   Format:"#,##0",       Width:70,   SaveName:"orgCount", 	   Align:"Center",  Edit:0}, //평가대상 기관수                   
                    {Type:"Int",   Format:"#,##0",       Width:50,   SaveName:"orgItemCount",  Align:"Center",  Edit:0}, //기관표준 > 용어
                    {Type:"Float", Format:"#,##0.##\\%", Width:50,   SaveName:"orgItemRate",   Align:"Center",  Edit:0}, //기관표준 > 제출율
                    {Type:"Int",   Format:"#,##0",       Width:50,   SaveName:"orgWordCount",  Align:"Center",  Edit:0}, //기관표준 > 단어
                    {Type:"Float", Format:"#,##0.##\\%", Width:50,   SaveName:"orgWordRate",   Align:"Center",  Edit:0}, //기관표준 > 제출율
                    {Type:"Int",   Format:"#,##0",       Width:50,   SaveName:"orgDomainCount",Align:"Center",  Edit:0}, //기관표준 > 도메인
                    {Type:"Float", Format:"#,##0.##\\%", Width:50,   SaveName:"orgDomainRate", Align:"Center",  Edit:0}, //기관표준 > 제출율
                    {Type:"Int",   Format:"#,##0",       Width:50,   SaveName:"orgCodeCount",  Align:"Center",  Edit:0}, //기관표준 > 코드
                    {Type:"Float", Format:"#,##0.##\\%", Width:50,   SaveName:"orgCodeRate",   Align:"Center",  Edit:0}, //기관표준 > 제출율
                    {Type:"Int",   Format:"#,##0",       Width:60,   SaveName:"orgDbCount",    Align:"Center",  Edit:0}, //평가db수
                    {Type:"Int",   Format:"#,##0",       Width:50,   SaveName:"dbItemCount",   Align:"Center",  Edit:0}, //db표준 > 용어
                    {Type:"Float", Format:"#,##0.##\\%", Width:50,   SaveName:"dbItemRate",    Align:"Center",  Edit:0}, //db표준 > 제출율
                    {Type:"Int",   Format:"#,##0",       Width:50,   SaveName:"dbWordCount",   Align:"Center",  Edit:0}, //db표준 > 단어
                    {Type:"Float", Format:"#,##0.##\\%", Width:50,   SaveName:"dbWordRate",    Align:"Center",  Edit:0}, //db표준 > 제출율
                    {Type:"Int",   Format:"#,##0",       Width:50,   SaveName:"dbDomainCount", Align:"Center",  Edit:0}, //db표준 > 도메인
                    {Type:"Float", Format:"#,##0.##\\%", Width:50,   SaveName:"dbDomainRate",  Align:"Center",  Edit:0}, //db표준 > 제출율
                    {Type:"Int",   Format:"#,##0",       Width:50,   SaveName:"dbCodeCount",   Align:"Center",  Edit:0}, //db표준 > 코드
                    {Type:"Float", Format:"#,##0.##\\%", Width:50,   SaveName:"dbCodeRate",    Align:"Center",  Edit:0}, //db표준 > 제출율
                    
                ];
                    
        InitColumns(cols);
      
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
                
        SetMergeSheet(msHeaderOnly);
        
        var headers = [
					{Text:"<s:message code='COMMON.HEADER.TOTALSTSTAT.SUB.LST'/>", Align:"Center"},
					 //|전체기관|등록기관|용어|단어|도메인|코드
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
        			{Type:"Text",   			   Width:50,   SaveName:"title",         Align:"Center",  Edit:0}, //기관,DB                    
                    {Type:"Int",  Format:"#,##0",  Width:50,   SaveName:"totalOrg",      Align:"Center",  Edit:0}, //전체기관
                    {Type:"Int",  Format:"#,##0",  Width:50,   SaveName:"regOrg",   	 Align:"Center",  Edit:0}, //등록기관
                    {Type:"Int",  Format:"#,##0",  Width:50,   SaveName:"item", 		 Align:"Center",  Edit:0}, //용어
                    {Type:"Int",  Format:"#,##0",  Width:50,   SaveName:"word",  		 Align:"Center",  Edit:0}, //단어
                    {Type:"Int",  Format:"#,##0",  Width:50,   SaveName:"dmn", 		     Align:"Center",  Edit:0}, //도메인
                    {Type:"Int",  Format:"#,##0",  Width:50,   SaveName:"code", 	     Align:"Center",  Edit:0}, //코드
                    
                ];
                    
        InitColumns(cols);
      
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
        
        case "Search":
        	grid_sheet.DoSearch('<c:url value="/commons/user/TotalStatSelectlist.do" />');
        	grid_sheet2.DoSearch('<c:url value="/commons/user/TotalStatSubSelectlist.do" />');
        	break;
       
        case "Down2Excel":  //엑셀내려받기
        
        	grid_sheet.Down2ExcelBuffer(true);  
            grid_sheet.Down2Excel({FileName:'종합 등록 현황',HiddenColumn:1, Merge:1});
            grid_sheet2.Down2Excel({FileName:'종합 등록 현황',HiddenColumn:1, Merge:1});
            grid_sheet.Down2ExcelBuffer(false);
            break;
    
    }       
}
 

</script>
</head>

<body>

<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="USER.DB.REG.STS" /></div> 
	</div>
</div>
<!-- 메뉴 메인 제목 -->

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <!-- 조회버튼영역  -->         
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />          
</div>

<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "500px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:10px;"><span></span></div>

<!-- 그리드 입력 입력 -->
	<div class="grid_02">
	     <script type="text/javascript">createIBSheet("grid_sheet2", "50%", "120px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

</body>
</html>