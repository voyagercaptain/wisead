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
        
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.DBREGISTSTAT.LST'/>", Align:"Center"}
                    /* 기관명|DB명|DB표준용어|DB표준도메인|DB표준단어|DB표준코드 */
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
        	{Type:"Text",  Width:60,  SaveName:"orgNm",     	 Align:"Left", 	     			 Edit:0}, //기관명
        	{Type:"Text",  Width:40,  SaveName:"dbNm",   	 	 Align:"Left", 	  				 Edit:0}, //DB명
        	{Type:"Text",  Width:20,  SaveName:"dbItemYn",   	 Align:"Left", 				     Edit:0}, //DB표준용어YN
        	{Type:"Int",   Width:30,  SaveName:"dbItemCount",    Align:"Left", 	Format:"#,##0",  Edit:0}, //DB표준용어
        	{Type:"Text",  Width:20,  SaveName:"dbDomainYn", 	 Align:"Left", 				     Edit:0}, //DB표준도메인YN
        	{Type:"Int",   Width:30,  SaveName:"dbDomainCount",  Align:"Left", 	Format:"#,##0",  Edit:0}, //DB표준도메인
        	{Type:"Text",  Width:20,  SaveName:"dbWordYn",   	 Align:"Left", 				     Edit:0}, //DB표준단어YN
        	{Type:"Int",   Width:30,  SaveName:"dbWordCount",    Align:"Left", 	Format:"#,##0",  Edit:0}, //DB표준단어
        	{Type:"Text",  Width:20,  SaveName:"dbCodeYn",   	 Align:"Left", 				     Edit:0}, //DB표준코드YN
        	{Type:"Int",   Width:30,  SaveName:"dbCodeCount",    Align:"Left", 	Format:"#,##0",  Edit:0}, //DB표준코드
        	
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
        	grid_sheet.DoSearch('<c:url value="/commons/user/DbRegistStatSelectlist.do" />', param);
        	
        	break;
       
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            
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
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='USER.DB.REG.INQ' />"> <!-- DB별 등록 현황 조회 -->
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
                            <th scope="row"><label for=""></label></th> <!-- 사전유형 -->
                            <td >
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