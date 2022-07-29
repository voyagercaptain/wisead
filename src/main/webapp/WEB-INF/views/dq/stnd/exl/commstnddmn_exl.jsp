<!DOCTYPE html>
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

$(document).ready(function(){
	//그리드 초기화
	grid_DMN_init();
});


$(window).load(function(){
    $(window).resize();
});


$(window).resize( function(){
    	//그리드 가로 스크롤 방지
    	grid_DMN.FitColWidth();
    }
);

function grid_DMN_init() {

    with(grid_DMN){
    	
     	//var cfg = {SearchMode:2,Page:100};
        var cfg = {SearchMode:1,Page:300,UseHeaderSortCancel:1};
        SetConfig(cfg);
        SetCountPosition(1);
        SetPagingPosition(2);

        /*
		var headtext  = "<s:message code='META.HEADER.STNDDMN.RQST.IFM.1'/>";
		headtext += "|<s:message code='META.HEADER.STNDDMN.RQST.IFM.5'/>";
		headtext += "|<s:message code='META.HEADER.STNDDMN.RQST.IFM.4'/>";
		*/
		var headtext  = "No.|상태|선택|번호|제정차수|검토상태|검토내용|요청구분|등록유형|검증결과";
		headtext += "|도메인ID|공통표준도메인그룹명|공통표준도메인분류명|공통표준도메인명|공통표준도메인설명|데이터타입|데이터길이|데이터소수점길이|저장형식|표현형식|단위|허용값|관리부서명|행정표준코드명";
		headtext += "|담당자ID|담당자명|제정일자|요청자ID|요청자명|요청번호|요청일련번호|특이사항|제개정내용|사용여부";
		

	var headers = [
				{Text:headtext, Align:"Center"}
			];
	
	var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
	
	InitHeaders(headers, headerInfo); 

	var cols = [						
				{Type:"Seq",		Width:50,   SaveName:"ibsSeq",	    Align:"Center", Edit:0, Hidden:1},
				{Type:"Status", 	Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
				
				{Type:"CheckBox", 	Width:45,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
				{Type:"Int",	Width:80,   SaveName:"seq",	      Align:"Center", Edit:1, Hidden:0},
				{Type:"Text",   	Width:100,  SaveName:"ord",  Align:"Left", Edit:1, Hidden:0, KeyField:1},
				{Type:"Combo",      Width:80,   SaveName:"rvwStsCd",	Align:"Center", Edit:0, Hidden:1},						
				{Type:"Text",       Width:80,   SaveName:"rvwConts",	Align:"Left", Edit:0, Hidden:1},						
				{Type:"Combo",      Width:80,   SaveName:"rqstDcd",	    Align:"Center", Edit:0, KeyField:0,Hidden:1},						
				{Type:"Combo",      Width:80,   SaveName:"regTypCd",	Align:"Center", Edit:0,Hidden:1},						
				{Type:"Combo",      Width:100,  SaveName:"vrfCd",		Align:"Center", Edit:0,Hidden:1},							
				
				{Type:"Text",       Width:10,  SaveName:"dmnId",	 	    Align:"Left", Edit:1, Hidden:1, KeyField:0},
				
				
				{Type:"Text",       Width:100,  SaveName:"dmngLnm",	 	    Align:"Left", Edit:1, Hidden:0, KeyField:0}, //도메인그룹명
				{Type:"Text",       Width:150,  SaveName:"dmnLnm",   	    Align:"Left", Edit:1, KeyField:0}, //도메인분류명
				
				{Type:"Text",       Width:100,  SaveName:"infotpLnm",	 	Align:"Left", Edit:1, Hidden:0, KeyField:1}, //도메인명
				{Type:"Text",       Width:150,  SaveName:"objDescn",	    Align:"Left", Edit:1, KeyField:1},
				{Type:"Text",       Width:120,  SaveName:"dataType",	 	Align:"Left", Edit:1, Hidden:0, KeyField:1},
				{Type:"Int",       Width:100,  SaveName:"dataLen",	 	    Align:"Left", Edit:1, Hidden:0, KeyField:0},
				
				
// 				{Type:"Text",       Width:180,  SaveName:"dmnPnm",   	    Align:"Left", Edit:1, KeyField:1, Hidden:0}, 
				
				
				{Type:"Int",       Width:100,  SaveName:"dataScal",	 	Align:"Left", Edit:1, Hidden:0},
				{Type:"Text",       Width:100,  SaveName:"saveFrm", 	 	Align:"Left", Edit:1, Hidden:0, KeyField:0},
				{Type:"Text",       Width:100,  SaveName:"exprsnFrm",	 	Align:"Left", Edit:1, Hidden:0},
				{Type:"Text",       Width:100,  SaveName:"unit",    	 	Align:"Left", Edit:1, Hidden:0},
				{Type:"Text",       Width:100,  SaveName:"cdVal",   	 	Align:"Left", Edit:1, Hidden:0},
				{Type:"Text",   	Width:100,  SaveName:"ownrOrg",      Align:"Left", Edit:1, Hidden:1, KeyField:0},  // 소관기관명
				
				{Type:"Text",       Width:100,  SaveName:"admnStndCd",	 	Align:"Left", Edit:1, Hidden:1},
				                    
				{Type:"Text",       Width:60,   SaveName:"crgUserId",	Align:"Left",   Edit:1, Hidden:1},
				{Type:"Text",       Width:60,   SaveName:"crgUserNm",	Align:"Left",   Edit:1, Hidden:1},
				{Type:"Text",       Width:60,   SaveName:"rqstDtm",  	Align:"Center", Edit:1, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
				{Type:"Text",       Width:60,   SaveName:"rqstUserId",  Align:"Center", Edit:1, Hidden:1},
				{Type:"Text",       Width:60,   SaveName:"rqstUserNm",  Align:"Center", Edit:1, Hidden:1}, 
				{Type:"Text",       Width:60,   SaveName:"rqstNo",      Align:"Center", Edit:1, Hidden:1}, 
				{Type:"Int",        Width:60,   SaveName:"rqstSno",     Align:"Center", Edit:1, Hidden:1},
				{Type:"Text",   	Width:150,  SaveName:"spclNt",    Align:"Center", Edit:1, Hidden:1},
                {Type:"Text",   	Width:150,  SaveName:"revisionDescn",    Align:"Center", Edit:1, Hidden:0},
                {Type:"Combo",   	Width:150,  SaveName:"useYn",    Align:"Center", Edit:1, Hidden:0, KeyField:0},

			];
				
	InitColumns(cols);
	
	//콤보 목록 설정
	SetColProperty("rvwStsCd", 	${codeMap.rvwStsCdibs});
	SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
	SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
	SetColProperty("vrfCd", 	${codeMap.vrfCdibs});
    SetColProperty("useYn", 	{ComboCode:"N|Y", ComboText:"N|Y"}); /* 아니요|예 */

// 	SetColProperty("dmngId", 	${codeMap.dmngibs});
// 	SetColProperty("infotpId",	${codeMap.infotpibs});
// 	SetColProperty("cdValTypCd", 	${codeMap.cdValTypCdibs});
// 	SetColProperty("cdValIvwCd", 	${codeMap.cdValIvwCdibs});
// 	SetColProperty("sditmAutoCrtYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
// 	SetColProperty("encYn", 	 {ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
// 	SetColProperty("dupYn", 	 {ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
// 	SetColProperty("stndAsrt", 	${codeMap.stndAsrtibs});
	
//		SetSendComboData(0,"dmngLnm", "Text");
	//SetSendComboData(0,"infotpLnm", "Text");
	
	InitComboNoMatchText(1, "");
	
// 	SetColHidden("dmngId"	,1);
// 	SetColHidden("infotpId"	,1);
// 	SetColHidden("mdlLnm"	,1);
// 	SetColHidden("uppSubjLnm",1);
// 	SetColHidden("subjId"	,1);
// 	SetColHidden("rqstNo"	,1);
// 	SetColHidden("rqstSno"	,1);
// 	SetColHidden("fullPath"	,1);
// 	SetColHidden("dataScal"	,null);
	

	
  
		FitColWidth();  
    //SetSheetHeight(200);
	SetExtendLastCol(1);	
}
    
    init_sheet(grid_DMN);    
    
}


function grid_DMN_OnLoadExcel() {
}

function grid_DMN_OnDblClick(row, col, value, cellx, celly) {
	if(row < 1) return;
}

function grid_DMN_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;

	//선택한 상세정보를 가져온다...
	var param =  grid_DMN.GetRowJson(row);

	//선택한 그리드의 row 내용을 보여준다.....
// 	var tmphtml = '도메인' + ':' +  infotpLnm;
// 	$('#DMN_sel_title').html(tmphtml);
	
	//검증결과 검증오류일경우가 아닐경우 RETURN
	if( grid_DMN.GetCellValue(row, "vrfCd") != "2")  return;
	
	//var param = grid_DMN.GetRowJson(row);
	var param1 = $("#mstFrm").serialize();
	param1 += "&rqstSno=" + param.rqstSno;
	
	//검증결과 조회
// 	getRqstVrfLst(param1);
	
}


function grid_DMN_OnSaveEnd(code, message) {
	doAction("Search"); 
}

function grid_DMN_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		//조회 성공....
	}
}

</script>

<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 입력 입력 -->
<div id="grid_DMN" class="grid_DMN">
     <script type="text/javascript">createIBSheet("grid_DMN", "100%", "600px");</script>
</div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<!-- <div class="selected_title_area" id="selected_title_area"> -->
<!-- 	<div class="selected_title" id="DMN_sel_title"></div> -->
<!-- </div> -->



