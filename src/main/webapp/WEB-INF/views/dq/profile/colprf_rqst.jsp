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
<title></title>

<script type="text/javascript">
var interval = "";

$(document).ready(function() {
	
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code='VALID.PRFREQUIRED' />";
	//폼검증
	$("#frmAnaTrg").validate({
		rules: {
			dbConnTrgPnm		: "required",
			dbSchPnm		: "required",
			dbcTblNm 	: "required",
			dbcColNm		: "required"
		},
		messages: {
			dbConnTrgPnm		: requiremessage,
			dbSchPnm		: requiremessage,
			dbcTblNm 	: requiremessage,
			dbcColNm		: requiremessage
		}
	});
	
	//그리드 초기화 
	initGrid();
	
	//그리드 사이즈 조절 초기화...		
//  	bindibsresize();
	
	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
	//탭 초기화....
// 	$( "#tabs" ).tabs().click(function(event){
	    //event.preventDefault();	//브라우저 기본 이벤트 제거...
// 	});
	
	//프로파일 종류 컬럼분석CD 강제 셋팅
	//$("#prfKndCd").val("PC01");
	
    // 조회 Event Bind
    $("#btnAnaTrgSearch").click(function(){ doAction("SearchAnaTrgTbl");  });
    

    $("#btnPrfSchRqst").click(function(){
    	doAction("PrfSchRqst");
    });
    
    $("#btnPrfSchRqst").hide();
    
    $("#btnPc01Save").click(function() {
    	saveColAna();
    });
    
    $("#btnExpColSave").click(function() {
    	saveExpCol();
    });
    
    $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );    
    
    //div size 정의
//     divSize();
    
    //진단대상 상세정보 READONLY SETTING
	$("#frmAnaTrg input[type=text]").css("border-color","transparent").css("width", "47%");
	//컬럼분석 input 요소 objNm 추가
	//prf_dtl 추가 시 테이블분석 키명 input 요소 objNm 존재 하기때문에 배열로 인식됨
	$("div#div_objNm").html("<input type='hidden'  class='wd50p' name='objNm' id='objNm' />");
	//컬럼관련 진단대상 요소 show
	$("form[name=frmAnaTrg] #colPrfTrLayer").show();
	
	$("#btnLogPop").click(function(){
		var param ="?shdLnm="+encodeURIComponent("컬럼분석");
		var url   = "<c:url value="/dq/scheduler/popup/schedulelog_pop.do"/>";
    	var popup = OpenWindow(url+param,"SQL","1200","750","yes");
	});
	
	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
	setautoComplete($("#frmSearch #schDbSchNm"), "DBSCH");
	setautoComplete($("#frmSearch #schDbcTblNm"), "DBCTBL");
	setautoComplete($("#frmSearch #schDbcColNm"), "DBCCOL");
});



$(window).load(function() {
});

$(window).resize(function() {
		//div size 정의
		divSize();
});

EnterkeyProcess("SearchAnaTrgTbl");
	
function initGrid()
{
   
	//진단대상 테이블 grid
    with(grid_tbl){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='DQ.HEADER.COLPRF_RQST'/>", Align:"Center"}
                ];
        //No|주제영역ID|주제영역명|진단대상ID|진단대상명|스키마ID|스키마명|테이블명|테이블한글명|메타연계분석

        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                    {Type:"Text",   Width:100,  SaveName:"subjId",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"subjLnm",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"dbConnTrgId",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:80,   SaveName:"dbConnTrgPnm",    	Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"dbSchId",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:80,   SaveName:"dbSchPnm",    	Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"dbcTblNm",    	Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"dbcTblKorNm",    	Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   Width:100,  SaveName:"metaAsscAnly"  , Align:"Left", Edit:0, Hidden:1}, 
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);

	     //콤보 목록 설정...
// 	    SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
        
        InitComboNoMatchText(1, "");
        
        FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
        SetExtendLastCol(1);    
    }
    //==시트설정 후 아래에 와야함===
	init_sheet(grid_tbl);    
    //===========================	
    
	//진단대상 컬럼 grid
   	with(grid_col){
   		
   		var cfg = {SearchMode:2,Page:100};
           SetConfig(cfg);
           
           var headers = [
                       {Text:"<s:message code='DQ.HEADER.COLPRF_RQST2'/>", Align:"Center"}
                   ];
           //Position|선택|프로파일Id|주제영역ID|주제영역명|진단대상ID|진단대상명|스키마ID|스키마명|테이블명|컬럼명|컬럼한글명|Pk여부|Data Type|Null여부|Default|메타연계분석|최소값|최대값|제외여부|제외사유

           var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
           
           InitHeaders(headers, headerInfo); 

           var cols = [                        
                   {Type:"Text",   Width:25,   SaveName:"ord",    	Align:"Center", Edit:0},
                   {Type:"Status", 	Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:1, Hidden:1},
                   {Type:"CheckBox",   Width:30,  SaveName:"ibsCheck",    	Align:"Left", Edit:1, Hidden:0}, 
                   {Type:"Text",   Width:100,  SaveName:"prfId",    	Align:"Left", Edit:0, Hidden:1}, 
                   {Type:"Text",   Width:100,  SaveName:"subjId",    	Align:"Left", Edit:0, Hidden:1}, 
                   {Type:"Text",   Width:100,  SaveName:"subjLnm",    	Align:"Left", Edit:0, Hidden:1}, 
                   {Type:"Text",   Width:100,  SaveName:"dbConnTrgId",    	Align:"Left", Edit:0, Hidden:1}, 
                   {Type:"Text",   Width:100,  SaveName:"dbConnTrgPnm",    	Align:"Left", Edit:0, Hidden:1}, 
                   {Type:"Text",   Width:100,  SaveName:"dbSchId",    	Align:"Left", Edit:0, Hidden:1}, 
                   {Type:"Text",   Width:100,  SaveName:"dbSchPnm",    	Align:"Left", Edit:0, Hidden:1},                    
                   {Type:"Text",   Width:100,  SaveName:"dbcTblNm",    	Align:"Left", Edit:0, Hidden:1},
                   {Type:"Text",   Width:100,  SaveName:"dbcColNm",    	Align:"Left", Edit:0}, 
                   {Type:"Text",   Width:100,  SaveName:"dbcColKorNm",    	Align:"Left", Edit:0, Hidden:0},
                   {Type:"Text",   Width:70,   SaveName:"pkYn",    	Align:"Center", Edit:0, Hidden:1},
                   {Type:"Text",   Width:80,  SaveName:"dataType",    	Align:"Left", Edit:0},
                   {Type:"Text",   Width:40,   SaveName:"nullYn",    	Align:"Center", Edit:0, Hidden:0},
                   {Type:"Text",   Width:80,   SaveName:"defltVal",    	Align:"Left", Edit:0, Hidden:1},
                   {Type:"Text",   Width:80,   SaveName:"metaAsscAnly",    	Align:"Left", Edit:0, Hidden:1},
                   {Type:"Text",   Width:80,   SaveName:"dmnMinVal",    	Align:"Left", Edit:0, Hidden:1},
                   {Type:"Text",   Width:80,   SaveName:"dmnMaxVal",    	Align:"Left", Edit:0, Hidden:1},
                   {Type:"Date",   Width:10,   SaveName:"anaStrDtm",    Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
                   {Type:"Combo",   Width:40,   SaveName:"expYn",    	Align:"Center", Edit:1, Hidden:0},
                   {Type:"Text",   Width:120,   SaveName:"expRsnCntn",    	Align:"Left", Edit:1, Hidden:0},
                   {Type:"Popup",   Width:30,   SaveName:"logSearch",    	Align:"Center", Edit:1, Hidden:0}
               ];
       
       //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
       InitColumns(cols);

       InitComboNoMatchText(1, "");
       
       SetColProperty("expYn", 	{ComboCode:"N|Y", ComboText:"N|Y"}); /* 아니요|예 */
       InitComboNoMatchText('expYn', 'N');
       
       
       //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
       
       FitColWidth();
       SetExtendLastCol(1); 
   
   	}
	
    //==시트설정 후 아래에 와야함===
    init_sheet(grid_col);    
    //===========================	
}	

function doAction(sAction, param)
{
        
    switch(sAction)
    {
       	/*진단대상 테이블 조회*/
        case "SearchAnaTrgTbl":
        	param = "";
        	param += "&tblColGb=PC";
        	param += "&"+$('form[name=frmSearch]').serialize();
        	grid_tbl.DoSearch("<c:url value="/dq/criinfo/anatrg/getPrfTblLstAna.do" />", param);
        	break;
        	
       	/*진단대상 컬럼 조회*/
        case "SearchAnaTrgCol":
        	grid_col.DoSearch("<c:url value="/dq/profile/getPrfColLstCheck.do" />", param);
        	break;
        
       	
        case "DtlReset":
        	//컬럼분석 텝 강제 클릭
//     		$("#tab-pc01 a").click();
    		//컬럼분석 form reset
			//resetPC01();
			//코드분석 RESET
			/* resetPC02();
			//코드분석 RESET
			resetPC03();
			//범위분석 RESET
			resetPC04();
			//패턴분석 RESET
			resetPC05(); */
        	
    	    break;
        
        	
        case "Down2Excel":  //엑셀내려받기
            grid_col.Down2Excel({HiddenColumn:1, Merge:1});
            break;
            
        case "PrfSchRqst":
        	var url   = "<c:url value="/dq/profile/popup/prfschrqst_pop.do"/>";
        	var popup = OpenWindow(url+param,"SQL","1200","750","yes");

        	break;
      	
    }       
}


function divSize(){
	 // 진단대상 전체
//   $("#anatrg").attr("style","width:30%;float:left;");
  // 진단대상 조회조건
//   $("#searchTrg_div").attr("style","width:99%;height:100%;float:left;");
  
  // 프로파일 상세 전체
//   $("#colprf").attr("style","width:69%;height:100%;float:left;");
  // 진단대상 컬럼 상세
//   $("#searchAnaTrgDtl_div").attr("style","width:100%;float:right;");
  //프로파일 텝
//   $("#tabs").attr("style","width:99%;float:right;");
  
  //그리드 가로 스크롤 방지
	grid_tbl.FitColWidth();
	
	grid_col.FitColWidth();
	
	//grid_prf.FitColWidth();
	
}


function loadColDetail(param){
	//ajax2Json('<c:url value="/dq/profile/getAnaTrgColDetail.do"/>', param, setAnaTgtDtl);
}

function grid_tbl_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	
	//화면 RESET
   	//resetPrfInfo("PC");
	
	//상세화면
   	//doAction("DtlReset");
	
  //그리드 선택 데이터 변수 setting
	var param = "&schDbConnTrgId=" + grid_tbl.GetCellValue(row, "dbConnTrgId"); 
	    param += "&schDbSchId="    + grid_tbl.GetCellValue(row,"dbSchId");
	    param += "&schDbcTblNm="   + grid_tbl.GetCellValue(row,"dbcTblNm");
	    param += "&metaAsscAnly="  + grid_tbl.GetCellValue(row,"metaAsscAnly");
 	    param += "&schDbcColNm="   + $("form[name=frmSearch] input[name='schDbcColNm']").val();
 	    param += "&schRegYn="      + $("form[name=frmSearch] select[name='schRegYn']").val();
 	    //테이블 컬럼 프로파일 구분
 	    param += "&tblColGb=PC";
 	     
	 //컬럼목록 조회
	doAction("SearchAnaTrgCol", param);
}

function grid_col_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	
	//프로파일정보 reset
   	//resetPrfInfo("PT");
   	
    //텝 상세화면 RESET
   	//doAction("DtlReset");
    
    var metaAsscAnly = grid_col.GetCellValue(row, "metaAsscAnly");
	
	var param  = "&dbConnTrgId="  + grid_col.GetCellValue(row, "dbConnTrgId"); 
	    param += "&dbSchId="      + grid_col.GetCellValue(row, "dbSchId");
	    param += "&dbcTblNm="     + grid_col.GetCellValue(row, "dbcTblNm");
	    param += "&dbcColNm="     + grid_col.GetCellValue(row, "dbcColNm");
	   
	    //테이블 컬럼 프로파일 구분
	    param += "&tblColGb=PC";
	    
	    param += "&metaAsscAnly=" + metaAsscAnly;
	    
	    
     
 	//loadColDetail(param);
	
}

function grid_col_OnDblClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	
	getDataPattern(row);
}

function grid_col_OnPopupClick(Row,Col) {
	
	var param ="?shdLnm="+encodeURIComponent("컬럼분석")+"&objNm="+grid_col.GetCellValue(Row,"dbConnTrgPnm")+encodeURIComponent("|")+grid_col.GetCellValue(Row,"dbSchPnm")+encodeURIComponent("|")+grid_col.GetCellValue(Row,"dbcTblNm")+encodeURIComponent("|")+grid_col.GetCellValue(Row,"dbcColNm");
	var url   = "<c:url value="/dq/scheduler/popup/schedulelog_pop.do"/>";
	var popup = OpenWindow(url+param,"SQL","1200","750","yes");
}


function getDataPattern(row){
 	var objId = grid_col.GetCellValue(row, "prfId");
	
	if(objId == ""){
		showMsgBox("ERR", "<s:message code="INQ.DATA.SEL" />"); /*조회할 데이터를 선택하십시오.*/

		return;
	}
	
 	var param = "?objId="+objId;
	     param += "&objDate="+grid_col.GetCellValue(row, "anaStrDtm"); //schAnaStrDtm;
	     param += "&objIdCol=PRF_ID";		  
         param += "&objResTbl=WAM_PRF_RESULT";
	     param += "&objErrTbl=WAM_PRF_ERR_DATA";
	     param += "&erDataSnoCol=ESN_ER_DATA_SNO";
         param += "&objGb=PC01";        
         param += "&erDataSno="+0;
         
    var url = '<c:url value="/dq/report/popup/datapattern_pop.do" />';
 	var popup = OpenWindow(url+param, "DATA_PATTERN", "800", "600", "yes"); 
}

//진단대상 테이블 조회 오류
function grid_tbl_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}else{
// 		resetPrfInfo("PC");
	}
}

//진단대상 컬럼 조회 오류
function grid_col_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code  < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}else{
		
	}
}

function saveColAna(){
	var saveJson = grid_col.GetSaveJson(0, "ibsCheck");
	
	if(saveJson.data.length == 0){
		showMsgBox("ERR", "분석할 컬럼을 선택하십시오."); /*분석할 데이터를 선택하십시오*/

		return;
	}
	
	for(var i = 1; i <= grid_col.GetDataLastRow(); i++){
		if(grid_col.GetCellValue(i,'expYn') == 'Y' && grid_col.GetCellValue(i,'ibsCheck') == '1'){
			showMsgBox("ERR", "제외여부가 Y인 컬럼은 분석을 실행할 수 없습니다.");
			return;
		} 
	}
	//저장
	var urls = '<c:url value="/dq/profile/registerPC01.do"/>';
	
	var param = "&prfKndCd=PC01";
		     
	IBSpostJson2(urls, saveJson, param, ibscallback);
}


function saveExpCol(){
	var SaveJson = grid_col.GetSaveJson();
	
	for(var i = 1; i <= grid_col.GetDataLastRow(); i++){
		if(grid_col.GetCellValue(i,'expYn') == 'Y' && grid_col.GetCellValue(i,'expRsnCntn') == ''){
			showMsgBox("ERR", "제외사유를 입력하세요");
			return;
		} 
	}
	
	
	if(SaveJson.data.length == 0) return;
	
    var url = "<c:url value="/dq/criinfo/anatrg/regExpCol.do"/>";
	var param = "";
	IBSpostJson2(url, SaveJson, param, ibscallback);
	
}

// function postProcessIBS(res) {
	
// 	switch(res.action) {
			
<%-- 	case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" :  --%>
 
// 		doAction("SearchAnaTrgCol",param);
// 		break;
			
// 	}
// }

</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="CLMN.PROF.MNG" /></div><!--컬럼프로파일 관리-->

	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 진단대상 검색 -->
<div id="anatrg" style="width: 30%; float: left;">
	<div id="searchTrg_div" >
	        <div class="stit"><s:message code="INQ.COND2" /></div><!--검색조건-->
	        <div class="tb_comment">- 컬럼분석이 수행된 항목은 파란색으로 표시됩니다.</div>
	        <div style="clear:both; height:5px;"><span></span></div>
	        
	        <form id="frmSearch" name="frmSearch" method="post">
	            <fieldset>
	            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
	            <div class="tb_basic2" >
	                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='CLMN.PROF.MNG'/>">
	                   <caption><s:message code="CLMN.PROF.MNG"/></caption><!--컬럼프로파일관리-->

	                   <colgroup>
	                   <col style="width:15%;" />
	                   <col style="width:35%;" />
	                   <col style="width:15%;" />
	                   <col style="width:35%;" />
	                   </colgroup>
	                   
	                   <tbody>                            
	                       <tr>                               
	                           <th scope="row"><label for="schDbConnTrgId"><s:message code="DB.MS" /></label></th><!--진단대상명-->


	                           <td>
	                           		<select id="schDbConnTrgId"  name="schDbConnTrgId" class="wd98p">
	                           		<option value=""><s:message code="WHL" /></option><!--전체-->

								    <c:forEach var="code" items="${codeMap.connTrgDbmsCd}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
	                           		</select>
	                           </td>	                                                  
	                           <th scope="row"><label for="schDbSchNm"><s:message code="SCHEMA.NM" /></label></th><!--스키마명-->

	                           <td>
	                               <input type="text" name="schDbSchNm" id="schDbSchNm" class="wd98p"/>
	                           </td>
	                       </tr>
	                       <tr>
	                       		<th scope="row"><label for="schDbcTblNm"><s:message code="TBL.NM" /></label></th>	<!--테이블명-->

	                       		<td>
	                       			<input type="text" name="schDbcTblNm" id="schDbcTblNm" class="wd98p"/>
	                       		</td>	                                              
	                           <th scope="row"><label for="schDbcColNm"><s:message code="CLMN.NM" /></label></th><!--컬럼명-->

	                           <td>
	                               <input type="text" name="schDbcColNm" id="schDbcColNm" class="wd98p" />
	                           </td>
	                       </tr>
	                       <tr>
	                           <th scope="row"><label for="anaYn">분석여부</label></th><!--컬럼분석완료여부-->
	                           <td>
	                               <select name="anaYn" id="anaYn" class="wd98p" >
	                                   <option value=""><s:message code="WHL" /></option>
	                                   <option value="Y">Y</option>
	                                   <option value="N">N</option>
	                               </select>
	                           </td>
	                           <th scope="row"><label for=""></label></th><!--컬럼분석완료여부-->
	                           <td>	                               
	                           </td>
	                             
	                       </tr>
	                       <tr style="display:none;">                               
	                           <th scope="row"><label for="schRegYn"><s:message code="REG.YN" /></label></th><!--등록여부-->
	                           <td>
	                           		<select id="schRegYn"  name="schRegYn">
	                           			<option value=""><s:message code="WHL" /></option><!--전체-->

	                           			<option value="Y">Y</option>
	                           			<option value="N">N</option>
	                           		</select>
	                           </td>
	                       </tr>
	                   </tbody>
	                 </table>   
	            </div>
	            </fieldset>
	            
	        </form>
	</div>
	
    <!-- 조회버튼영역  -->
	<div style="clear:both; height:10px;"><span></span></div>
	<div id="divFrmBtn" style="text-align: left;">
		<button class="btn_search" id="btnAnaTrgSearch" name="btnAnaTrgSearch"><s:message code="INQ"/></button><!--조회-->
	</div>
	
	<div style="clear:both; height:5px;"><span></span></div>
	        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_tbl", "99%", "410px");</script>            
	</div>
	<div style="clear:both; height:10px;"><span></span></div>

	
</div>
<div style="width: 1%;float: left;">&nbsp;</div>
<!-- 컬럼프로파일 상세 조회 -->
<div id="colprf" style="width:69%; float: left;">
	<div class="stit">컬럼목록</div><!-- 진단대상 상세정보 -->
    <div class="tb_comment">- 컬럼분석 실행 후 해당 컬럼을 더블클릭하면 해당 컬럼의 데이터패턴 및 상세 정보를 확인할 수 있습니다. 정상수행 여부는 LOG조회를 통해 확인할 수 있습니다.</div>
	<div style="clear:both; height:5px;"><span></span></div>
	<div id="divFrmBtn" style="text-align: left;">
		<button class="btn_search" id="btnPc01Save" name="btnPc01Save">컬럼분석 실행</button><!--조회-->
		<button class="btn_search" id="btnExpColSave" 	name="btnExpColSave"><s:message code="STRG" /></button> <!-- 저장 -->
		<button class="btn_search" id="btnLogPop" name="btnLogPop">LOG</button> <!-- 로그팝업 조회 -->
		
		<button class="btn_excel_down" id="btnExcelDown" name="btnExcelDown" style='float:right'><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->
		
	</div>
	<div style="clear:both; height:5px;"><span></span></div>
	
	
	<script type="text/javascript">createIBSheet("grid_col", "99%", "520px");</script>

	
	
	<div style="clear:both; height:5px;"><span></span></div>       
	
</div>


</body>
</html>