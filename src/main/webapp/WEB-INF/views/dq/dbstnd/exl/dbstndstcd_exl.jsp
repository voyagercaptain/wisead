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
	grid_STCD_init();

});


$(window).load(function(){
    $(window).resize();
});


$(window).resize( function(){
    	//그리드 가로 스크롤 방지
    	grid_STCD.FitColWidth();
    }
);

var colsCount = 0;
var headerText = [];
function grid_STCD_init() {

    with(grid_STCD){
    	
		//var cfg = {SearchMode:2,Page:100};
        var cfg = {SearchMode:1,Page:300,UseHeaderSortCancel:1};
		SetConfig(cfg);
        SetCountPosition(1);
        SetPagingPosition(2);
		/*
		var headtext  = "<s:message code='META.HEADER.STNDWORD.RQST'/>";
 		*/
		//No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과|STWDID|기관명|DB명|표준단어명|영문약어명|단어영문명|단어설명|형식단어여부|도메인분류명|이음동의어목록|금칙어목록|등록일자|요청자ID|요청자명|요청번호|요청일련번호
		
 		//var headtext  = "No.|상태|선택|commCdId|기관명|DB명|한글코드명|코드설명|데이터타입|데이터길이|영문코드명|관리부서명|제정일자|특이사항|코드값|코드값의미|코드값설명|상위코드값|사용여부";
 		var headtext  = "No.|상태|선택|기관명|DB명|관리부서명|commCdId|코드명(한글)|코드명(영문)|코드설명|데이터타입|데이터길이|코드값|코드값의미|코드값설명|제정일자|특이사항|상위코드값|사용여부|필수항목";
 		headerText = headtext.split("|");
 		
		var headers = [
					{Text:headtext, Align:"Center"}
				];
		
		var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
		
		InitHeaders(headers, headerInfo); 

		var cols = [						
			{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	    Align:"Center", Edit:0},
			{Type:"Status", Width:60,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
			{Type:"CheckBox", Width:50, SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
			
			{Type:"Text",   Width:100,  SaveName:"orgNm",   	Align:"Left", Edit:1, KeyField:1},
			{Type:"Text",   Width:100,  SaveName:"dbNm",     Align:"Left", Edit:1, KeyField:1},
			
			{Type:"Text",   Width:100,  SaveName:"mngDeptCd",	 	Align:"Left", Edit:1, Hidden:0},
			
			{Type:"Text",   Width:150,  SaveName:"commCdId",   	Align:"Left", Edit:0, KeyField:0, Hidden:1},
			
			{Type:"Text",   Width:100,  SaveName:"commCdNm",   	Align:"Left", Edit:1, KeyField:1},
			{Type:"Text",  Width:150,   SaveName:"comnCdEnnm",	 	Align:"Center", Edit:1, Hidden:0, KeyField:1},
			
			{Type:"Text",   Width:150,  SaveName:"commCdDesc", 	Align:"Left", Edit:1, Hidden:0, KeyField:0},
			{Type:"Text",   Width:100,  SaveName:"comnCdDttpNm",   	Align:"Left", Edit:1, KeyField:0}, 
			{Type:"Int",   Width:80,  SaveName:"comnCdDataLen",	Align:"Left", Edit:1, KeyField:0},
			
			{Type:"Text",   Width:100,  SaveName:"commDtlCdNm",	    Align:"Left", Edit:1, Hidden:0, KeyField:1},
			{Type:"Text",   Width:100,  SaveName:"commDtlCdMn",	    Align:"Left", Edit:1, Hidden:0, KeyField:1},
			{Type:"Text",   Width:100,  SaveName:"commDtlCdDesc",	    Align:"Left", Edit:1, Hidden:0, KeyField:0},
			
			{Type:"Text",   Width:80,  SaveName:"writDtm",  	Align:"Center", Edit:1, Format:"yyyyMMdd", KeyField:1},
			{Type:"Text",   Width:150,  SaveName:"pclrMtr",	    Align:"Left", Edit:1, Hidden:0},
			{Type:"Text",   Width:100,  SaveName:"uppCommCdId",	    Align:"Left", Edit:1, Hidden:0, KeyField:0},
			{Type:"Combo",   Width:80,  SaveName:"useYn",	    Align:"Left", Edit:1, Hidden:0},
			{Type:"Text",   Width:150,  SaveName:"errChk",	    Align:"Left", Edit:1, Hidden:0}
		];
		
		colsCount = cols.length;
		
		InitColumns(cols);
		
		//콤보 목록 설정
		SetColProperty("rvwStsCd", 	${codeMap.rvwStsCdibs});
		SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("vrfCd", 	${codeMap.vrfCdibs});
		
		SetColProperty("useYn", 	{ComboCode:"N|Y", ComboText:"N|Y"}); /* 아니요|예 */
		
		
		
// 		InitComboNoMatchText(1, "");
		
		SetColHidden("rqstUserId",1);
	  
		FitColWidth();  
		
		SetExtendLastCol(1);	
		SetComboOpenMode(1);

	}
    
    init_sheet(grid_STCD);    
    
}


function grid_STCD_OnLoadExcel() {
}

function grid_STCD_OnDblClick(row, col, value, cellx, celly) {
	if(row < 1) return;
}

function grid_STCD_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;


	if(grid_STCD.SaveNameCol("dmnYn") == col) {
		grid_STCD.SelectCell(row, "dmnYn", {Edit:1});
		return;
	}

	//선택한 상세정보를 가져온다...
	var param =  grid_STCD.GetRowJson(row);

	//선택한 그리드의 row 내용을 보여준다.....
// 	var tmphtml = '표준단어명 ' + ' : ' +  param.stwdLnm+"["+param.stwdPnm+"]";
	
// 	$('#STWD_sel_title').html(tmphtml);
	
	//검증결과 검증오류일경우가 아닐경우 RETURN
// 	if( grid_STCD.GetCellValue(row, "vrfCd") != "2")  return;
	
	//var param = grid_STCD.GetRowJson(row);

	var param1 = $("#mstFrm").serialize();
	param1 += "&rqstSno=" + param.rqstSno;

	//검증결과 조회
// 	getRqstVrfLst(param1);
	
}


function grid_STCD_OnSaveEnd(code, message) {
	doAction("Search"); 
}

function grid_STCD_OnSearchEnd(code, message, stCode, stMsg) {
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
<div id="grid_STCD" class="grid_STCD">
     <script type="text/javascript">createIBSheet("grid_STCD", "100%", "600px");</script>
</div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<!-- <div class="selected_title_area" id="selected_title_area"> -->
<!-- 	<div class="selected_title" id="STWD_sel_title"></div> -->
<!-- </div> -->



