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
<title><s:message code="DATA.QLTY.GIPO.INQ" /></title><!-- 데이터품질지표 조회 -->

<script type="text/javascript">

var interval = "";

$(document).ready(function() {
	//그리드 초기화 
	initGrid();
	//그리드 사이즈 조절 초기화...		
	bindibsresize();
	
	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
	//탭 초기화....
	//$( "#tabs" ).tabs();
	//그리드 초기화 
	// 조회 Event Bind
	$("#btnSearch").click(function(){ doAction("Search");  });
	// 엑셀내리기 Event Bind
	$("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
	//조회버튼 hidden
	$("#btnSave").hide();
	//삭제버튼 hidden
	$("#btnDelete").hide();
	//tree 추가 버튼 hidden
	$("#btnTreeNew").hide();
	//데이터품질지표명 검색 팝업
	$('.btnDqiLnmPop').click(function(event){
		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
		    	var url = '<c:url value="/dq/criinfo/dqi/popup/dqi_pop.do"/>';
		    	var popwin = OpenModal(url+"?sflag=DQILNM", "bizAreaPop",  800, 600, "no");
				popwin.focus();
	});
	//상세 페이지
	loadDetail();
	
	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
	setautoComplete($("#frmSearch #dqiLnm"), "DQILNM");
});

$(window).load(function() {
	//PK값으로 검색
	var dqiId ="";
		dqiId="${search.dqiId}";
		param ="dqiId="+dqiId;
		if(dqiId != null && dqiId != "" && dqiId !="undefined"){
			grid_sheet.DoSearch("<c:url value="/dq/criinfo/dqi/getDqiList.do" />", param);
		}
		
	var linkFlag = "";
	linkFlag = "${linkFlag}";
	if(linkFlag == "1"){
		doAction("Search");
	}
	
});


$(window).resize(
    function(){
    	//그리드 가로 스크롤 방지
    	grid_sheet.FitColWidth();
    }
);

EnterkeyProcess("Search");

function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='DQ.HEADER.DQI.LST'/>", Align:"Center"}
                ];
        //No.|상태|선택|데이터품질지표ID|데이터품질지표명|상위데이터품질지표명|데이터품질지표레벨|업무규칙수|데이터품질지표설명|버전|등록유형|요청일시|요청자ID|요청자명

        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:40,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                    {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0},
                    {Type:"CheckBox", Width:50,   SaveName:"ibsCheck",  Align:"Center", Edit:0, Sort:0},
                    {Type:"Text",   Width:100,  SaveName:"dqiId",    	Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:150,  SaveName:"dqiLnm",   	Align:"Left", Edit:0, TreeCol:1},
                    {Type:"Text",   Width:150,   SaveName:"uppDqiLnm", 	Align:"Left", Edit:0},
                    {Type:"Text",   Width:70,   SaveName:"dqiLvl", 	Align:"Center", Edit:0},
                    {Type:"Text",   Width:60,  SaveName:"bizruleCnt",  Align:"Right", Edit:0},
                    {Type:"Text",   Width:400,  SaveName:"objDescn",    Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:50,  SaveName:"objVers",     Align:"Left",   Edit:0},
                    {Type:"Combo",  Width:40,  SaveName:"regTypCd",    Align:"Center", Edit:0},                        
                    {Type:"Date",   Width:80,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd"},
                    {Type:"Text",   Width:50,  SaveName:"rqstUserId",  Align:"Center", Edit:0},
                    {Type:"Text",   Width:60,  SaveName:"rqstUserNm",  Align:"Center", Edit:0}
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);

	     //콤보 목록 설정...
	    SetColProperty("regTypCd", 	{ComboCode:"C|U|D", ComboText:"<s:message code='NEW' />|<s:message code='CHG' />|<s:message code='DEL' />"}); /*"신규|변경|삭제"*/

        
        InitComboNoMatchText(1, "");
        
        SetColHidden("ibsStatus",1);
        SetColHidden("ibsCheck",1);
        SetColHidden("dqiId",1);
        SetColHidden("objVers",1);
        SetColHidden("regTypCd",1);
        SetColHidden("rqstDtm",1);
        SetColHidden("rqstUserId",1);
        SetColHidden("rqstUserNm",1);
      
        FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
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
        	grid_sheet.DoSearch("<c:url value="/dq/criinfo/dqi/getDqiList.do" />", param);
        	break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
    }       
}
 
//상세정보호출
function loadDetail(param) {
	$('div#detailInfo').load('<c:url value="/dq/criinfo/dqi/detail/getDqiDetail.do"/>', param, function(){
		//$('#tabs').show();
	});
}

//이력조회
function getDqiHstLst(param) {
	grid_hst.DoSearch("<c:url value="/dq/criinfo/dqi/getDqiHstLst.do" />", param);
}

function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	//그리드 선택 데이터 변수 setting
	var param =  grid_sheet.GetRowJson(row);
	var dqiId = "&dqiId="+grid_sheet.GetCellValue(row, "dqiId");
	//caption 
	var tmphtml =  '<s:message code="DATA.QLTY.INDC.NM"/>' + ':' + param.dqiLnm ; /*데이터품질지표명*/
	$('#dqi_sel_title').html(tmphtml);
	
	//상세조회
	loadDetail(dqiId);
	
	//이력조회
	getDqiHstLst(dqiId);
}

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg) {
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
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="DATA.QLTY.GIPO.INQ" /></div><!--데이터품질지표 조회-->

	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div><!--검색조건-->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
       		<input type="hidden" name="ctqId" id="ctqId" />
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='DATA.QLTY.INDC.INQ'/>"><!--데이터품질지표조회-->
                   <caption><s:message code="DATA.QLTY.INDC"/></caption><!--데이터품질지표-->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row"><label for="dqiLnm"><s:message code="DATA.QLTY.INDC.NM"/></label></th><!--데이터품질지표명-->
                           <td>
                               <input type="text" name="dqiLnm" id="dqiLnm" class="wd300"/>
                               <button class="btnDqiLnmPop" id="btnDqiLnmPop"><s:message code="INQ" /></button><!--검색-->
                           </td>
<!--                            <th scope="row"><label for="uppDqiLnm"><s:message code="UPRN.DATA.QLTY.INDC.NM"/></label></th><상위데이터품질지표명-->



 
<!--                            <td> -->
<!--                                <input type="text" name="uppDqiLnm" id="uppDqiLnm" /> -->
<!--                            </td> -->
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
        <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonTree.jsp" />
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "250px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area" style="display: ;">
		    <div class="selected_title" id="dqi_sel_title"><s:message code="DATA.QLTY.INDC.NM"/> : <span></span></div><!--데이터품질지표명-->
	</div>
	
	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1"><s:message code="DTL.INFO" /></a></li><!--상세정보-->

	    <li><a href="#tabs-2"><s:message code="CHG.HSTR" /></a></li><!--변경이력-->
	  </ul>
	  <div id="tabs-1">
			<div id="detailInfo"></div>
	  </div>
	  <div id="tabs-2">
			<div id="detailInfo2"><%@include file="dqi_hst_lst.jsp" %></div>
	  </div>
	 </div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->

<%-- <%= application.getRealPath("/") %> --%>
</body>
</html>