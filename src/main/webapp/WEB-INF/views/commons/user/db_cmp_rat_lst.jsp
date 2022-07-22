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
EnterkeyProcess("Search");

var interval = "";

$(document).ready(function() {
	 	$('#btnTreeNew').css('display', 'none');
	 	$('#btnDelete').css('display', 'none');
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
        
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
      
    }
);

$(window).load(function() {
	initGrid();
});


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        SetMergeSheet(msHeaderOnly);
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.DBCMPRAT1.LST'/>", Align:"Center"},
                    /* |용어|용어|용어|도메인|도메인|도메인|단어|단어|단어 */
                    {Text:"<s:message code='COMMON.HEADER.DBCMPRAT.LST'/>", Align:"Center"}
                    /* 기관명|공통표준|기관표준|DB표준|공통표준|기관표준|DB표준|공통표준|기관표준|DB표준 */
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
        	{Type:"Text",  Width : 60,  	  SaveName:"orgNm",            Align:"Left",  Edit:0}, //기관명
        	{Type:"Int",   Format:"#,##0",    SaveName:"commItemCount",    Align:"Left",   Edit:0}, //용어  > 공통표준
        	{Type:"Int",   Format:"#,##0",    SaveName:"orgItemCount",     Align:"Left",   Edit:0}, //용어  > 기관표준
        	{Type:"Int",   Format:"#,##0",    SaveName:"dbItemCount",      Align:"Left",   Edit:0}, //용어  > db표준
        	{Type:"Int",   Format:"#,##0",    SaveName:"commDomainCount",  Align:"Left",   Edit:0}, //도메인 > 공통표준 
        	{Type:"Int",   Format:"#,##0",    SaveName:"orgDomainCount",   Align:"Left",   Edit:0}, //도메인 > 기관표준
        	{Type:"Int",   Format:"#,##0",    SaveName:"dbDomainCount",    Align:"Left",   Edit:0}, //도메인 > db표준
        	{Type:"Int",   Format:"#,##0",    SaveName:"commWordCount",    Align:"Left",   Edit:0}, //단어  > 공통표준
        	{Type:"Int",   Format:"#,##0",    SaveName:"orgWordCount",     Align:"Left",   Edit:0}, //단어  > 기관표준
        	{Type:"Int",   Format:"#,##0",    SaveName:"dbWordCount",      Align:"Left",   Edit:0}, //단어  > db표준
        	
        ];
                    
        InitColumns(cols);
      
        FitColWidth();  
        
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
        	var param = $('#frmSearch').serialize();
        	//alert(param);
        	grid_sheet.DoSearch('<c:url value="/commons/user/DbCmpRatSelectlist.do" />', param);
        	
        	break;
       
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({FileName:'DB표준 준수율',HiddenColumn:1, Merge:1});
            
            break;
    
    }       
}
 

</script>
</head>

<body>

<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="USER.REG.STS" /></div> <!-- 기관표준 등록 현황 -->
	</div>
</div>
<!-- 메뉴 메인 제목 -->

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='USER.REG.INQ' />"> <!--  -->
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row"><label for="orgNm">기관명</label></th> <!-- 사전유형 -->
                                <td >
                                <input type="text" id="orgNm" name="orgNm" class="wd98p" placeholder="기관명을 입력해주세요"  />
								</td>
                            <th scope="row"><label for=""></label></th> <!-- 사용자그룹유형 -->
                                <td>
                                </td>
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
   
        <!-- 조회버튼영역  -->         
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />          
</div>

<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "500px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

</body>
</html>