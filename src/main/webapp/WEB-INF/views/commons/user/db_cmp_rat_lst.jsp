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

        setautoComplete($("#frmSearch #orgNm"), "ORGNM", 10);

        $("#frmSearch #orgNm").autocomplete({
            select: function (event, ui) {
                getOrgDbList();
            }
        });
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
                    /* |용어|용어|용어|용어|도메인|도메인|도메인|도메인|단어|단어|단어|단어 */
                    {Text:"<s:message code='COMMON.HEADER.DBCMPRAT.LST'/>", Align:"Center"}
                    /* 기관명|공통표준|기관표준|DB표준|공통표준|기관표준|DB표준|공통표준|기관표준|DB표준 */
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
        	{Type:"Text",  Width : 60,    SaveName:"orgNm",            Align:"Left",  Edit:0}, //기관명
            {Type:"Text",  Width : 60,    SaveName:"dbNm",            Align:"Left",  Edit:0}, //DB명
        	{Type:"Text",   Format:"",    SaveName:"commItemCount",    Align:"Left",   Edit:0}, //용어  > 공통표준
        	{Type:"Text",   Format:"",    SaveName:"orgItemCount",     Align:"Left",   Edit:0}, //용어  > 기관표준
            {Type:"Text",   Format:"",    SaveName:"orgItemRqstDtm",     Align:"Left",   Edit:0, Format:"yyyy-MM-dd"}, //용어  > 기관표준제정일자
        	{Type:"Text",   Format:"",    SaveName:"dbItemCount",      Align:"Left",   Edit:0}, //용어  > db표준
        	{Type:"Text",   Format:"",    SaveName:"dbItemRqstDtm",    Align:"Left",   Edit:0, Format:"yyyy-MM-dd"}, //용어  > 제정일자
        	{Type:"Text",   Format:"",    SaveName:"commDomainCount",  Align:"Left",   Edit:0}, //도메인 > 공통표준
        	{Type:"Text",   Format:"",    SaveName:"orgDomainCount",   Align:"Left",   Edit:0}, //도메인 > 기관표준
            {Type:"Text",   Format:"",    SaveName:"orgDomainRqstDtm",     Align:"Left",   Edit:0, Format:"yyyy-MM-dd"}, //용어  > 기관표준제정일자
        	{Type:"Text",   Format:"",    SaveName:"dbDomainCount",    Align:"Left",   Edit:0}, //도메인 > db표준
        	{Type:"Text",   Format:"",    SaveName:"dbDomainRqstDtm",  Align:"Left",   Edit:0, Format:"yyyy-MM-dd"}, //도메인 > 제정일자
        	{Type:"Text",   Format:"",    SaveName:"commWordCount",    Align:"Left",   Edit:0}, //단어  > 공통표준
        	{Type:"Text",   Format:"",    SaveName:"orgWordCount",     Align:"Left",   Edit:0}, //단어  > 기관표준
            {Type:"Text",   Format:"",    SaveName:"orgWordRqstDtm",     Align:"Left",   Edit:0, Format:"yyyy-MM-dd"}, //용어  > 기관표준제정일자
        	{Type:"Text",   Format:"",    SaveName:"dbWordCount",      Align:"Left",   Edit:0}, //단어  > db표준
        	{Type:"Text",   Format:"",    SaveName:"dbWordRqstDtm",    Align:"Left",   Edit:0, Format:"yyyy-MM-dd"}, //단어  > 제정일자
            {Type:"Text",   Format:"",    SaveName:"orgCodeCount",     Align:"Left",   Edit:0}, //코드  > 기관코드
            {Type:"Text",   Format:"",    SaveName:"orgCodeRqstDtm",     Align:"Left",   Edit:0, Format:"yyyy-MM-dd"}, //용어  > 기관표준제정일자
            {Type:"Text",   Format:"",    SaveName:"dbCodeCount",      Align:"Left",   Edit:0}, //코드  > db코드
            {Type:"Text",   Format:"",    SaveName:"dbCodeRqstDtm",    Align:"Left",   Edit:0, Format:"yyyy-MM-dd"}, //코드  > 제정일자
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
        	
        	if(frmSearch.orgNm.value == '') {
				showMsgBox("INF", "기관명을 입력하고 검색해 주세요.");
				return;
			}
        	if(frmSearch.dbNm.value == '') {
				//showMsgBox("INF", "DB명을 입력하고 검색해 주세요.");
				return;
			}
        	var param = $('#frmSearch').serialize();
        	//alert(param);
        	grid_sheet.DoSearch('<c:url value="/commons/user/DbCmpRatSelectlist.do" />', param, ibscallback);
        	
        	break;
       
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({FileName:'DB표준 준수율',HiddenColumn:1, Merge:1});
            
            break;
    
    }       
}

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg) {
    var datalist = grid_sheet.GetSaveJson(1);
    for (var i = 0; i <  datalist.data.length; i++) {
        var data = datalist.data[i];
        // 용어
        if (data.commItemCount > 0 && data.dbItemCount > 0) {
            data.commItemCount = data.commItemCount + "(" + parseFloat((data.commItemCount/data.dbItemCount) * 100.0).toFixed(2) + "%)";
        }
        if (data.orgItemCount > 0 && data.dbItemCount > 0) {
            data.orgItemCount = data.orgItemCount + "(" + parseFloat((data.orgItemCount/data.dbItemCount) * 100.0).toFixed(2) + "%)";
        }

        // 도메인
        if (data.commDomainCount > 0 && data.dbDomainCount > 0) {
            data.commDomainCount = data.commDomainCount + "(" + parseFloat((data.commDomainCount/data.dbDomainCount) * 100.0).toFixed(2) + "%)";
        }
        if (data.orgDomainCount > 0 && data.dbItemCount > 0) {
            data.orgDomainCount = data.orgDomainCount + "(" + parseFloat((data.orgDomainCount/data.dbDomainCount) * 100.0).toFixed(2) + "%)";
        }

        // 단어
        if (data.commItemCount > 0 && data.dbWordCount > 0) {
            data.commItemCount = data.commItemCount + "(" + parseFloat((data.commItemCount/data.dbWordCount) * 100.0).toFixed(2) + "%)";
        }
        if (data.orgWordCount > 0 && data.dbWordCount > 0) {
            data.orgWordCount = data.orgWordCount + "(" + parseFloat((data.orgWordCount/data.dbWordCount) * 100.0).toFixed(2) + "%)";
        }

        // 코드
        if (data.orgCodeCount > 0 && data.dbCodeCount > 0) {
            data.orgCodeCount = data.orgCodeCount + "(" + parseFloat((data.orgCodeCount/data.dbCodeCount) * 100.0).toFixed(2) + "%)";
        }

        grid_sheet.SetRowData(i+2, data);
    }
}

//IBS 그리드 리스트 저장(삭제) 처리 후 콜백함수...
function ibscallback(res){
    var result = res.RESULT.CODE;
    if(result == 0) {
        postProcessIBS(res);

    } else {
        //공통메시지 팝업 : 실패 메세지...
        shotMsgBox("ERR", res.RESULT.MESSAGE);
    }
}


function postProcessIBS(res) {

    debugger
    switch(res.action) {
        case "<%=WiseMetaConfig.IBSAction.SEARCH%>" :
            var datalist = grid_sheet.GetSaveJson(1);
            for (var i = 0; i <  datalist.data.length; i++) {
                var data = datalist.data[i];
                // 용어
                if (data.commItemCount > 0 && data.dbItemCount > 0) {
                    data.commItemCount = data.commItemCount + "(" + parseFloat((data.commItemCount/data.dbItemCount) * 100.0).toFixed(2) + "%)";
                }
                if (data.orgItemCount > 0 && data.dbItemCount > 0) {
                    data.orgItemCount = data.orgItemCount + "(" + parseFloat((data.orgItemCount/data.dbItemCount) * 100.0).toFixed(2) + "%)";
                }

                // 도메인
                if (data.commDomainCount > 0 && data.dbDomainCount > 0) {
                    data.commDomainCount = data.commDomainCount + "(" + parseFloat((data.commDomainCount/data.dbDomainCount) * 100.0).toFixed(2) + "%)";
                }
                if (data.orgDomainCount > 0 && data.dbItemCount > 0) {
                    data.orgDomainCount = data.orgDomainCount + "(" + parseFloat((data.orgDomainCount/data.dbDomainCount) * 100.0).toFixed(2) + "%)";
                }

                // 단어
                if (data.commItemCount > 0 && data.dbWordCount > 0) {
                    data.commItemCount = data.commItemCount + "(" + parseFloat((data.commItemCount/data.dbWordCount) * 100.0).toFixed(2) + "%)";
                }
                if (data.orgWordCount > 0 && data.dbWordCount > 0) {
                    data.orgWordCount = data.orgWordCount + "(" + parseFloat((data.orgWordCount/data.dbWordCount) * 100.0).toFixed(2) + "%)";
                }

                grid_sheet.SetRowData(i+2, data);
            }

            break;

        default :
            // 아무 작업도 하지 않는다...
            break;

    }
}
function getOrgDbList() {
    // 변경된 값으로 비교 후 alert 표출
    //if($(this).val() == ""){
    //    alert("onchange value " + $(this).val());
    //}
    var url = "";
    url = '<c:url value="/dq/dbstnd/getDbList.do"/>';

    var param = $("#frmSearch").serialize();

    $.ajax({
        url: url,
        async: false,
        type: "POST",
        data: param,
        dataType: 'json',
        success: function (data) {
            if(data){

                if (data.length > 0) {
                    $('#dbNm').empty();

                    var option = $("<option value=''>전체</option>");
                    $('#dbNm').append(option);

                    for (var i = 0; i < data.length; i++) {
                        var option = $('<option value="'+data[i].dbNm+'">'+data[i].dbNm+'</option>');
                        $('#dbNm').append(option);
                    }
                }
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            var res  = "";
            //시스템을 이용할수 없습니다.<br>관리자에게 문의하세요.
            res = {RESULT : {CODE:-1, MESSAGE: gMSG_SYS_NO_USE}};
            ibscallback(res);
        }
    });

};


function escapeHtml(str) {
    var map = {
        '&': '&amp;',
        '<': '&lt;',
        '>': '&gt;',
        '"': '&quot;',
        "'": '&#039;'
    };
    return str.replace(/[&<>"']/g, function(m) { return map[m]; });
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
                                <input type="text" id="orgNm" name="orgNm" class="wd200" value="${orgNm}"
                                       placeholder="기관명을 입력해주세요"  />
								</td>
                            <th scope="row"><label for="dbNm">DB명</label></th> <!-- 사전유형 -->
                            <td >
                                <select id="dbNm" class="" name="dbNm">
                                	<option value="">전체</option>
	 					 		</select>
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