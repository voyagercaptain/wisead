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
	grid_SDITM_init();
});


$(window).load(function(){
    $(window).resize();
});


$(window).resize( function(){
    	//그리드 가로 스크롤 방지
    	grid_SDITM.FitColWidth();
    }
);

function grid_SDITM_init() {

    with(grid_SDITM){
    	
     	var cfg = {SearchMode:2,Page:100,UseHeaderSortCancel:1};
        SetConfig(cfg);
        //merge 설정
        SetMergeSheet(0);
        /*
		var headtext  = "<s:message code='META.HEADER.STNDITEM.RQST.IFM.1'/>";
		headtext += "<s:message code='META.HEADER.STNDITEM.RQST.IFM.4'/>";
 		headtext += "<s:message code='META.HEADER.STNDITEM.RQST.IFM.3'/>";
 		*/
 		//<entry key="META.HEADER.STNDITEM.RQST.IFM.1"><![CDATA[No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과]]></entry>
 		//<entry key="META.HEADER.STNDITEM.RQST.IFM.4"><![CDATA[|용어ID|기관명|DB명|표준용어명|용어설명|영문약어명|표준도메인명|데이터타입|데이터길이|소수점길이|허용값|저장형식|표현형식|단위|표준코드명|소관기관명]]></entry>
 		//<entry key="META.HEADER.STNDITEM.RQST.IFM.3"><![CDATA[|검증결과|등록일자|요청자ID|요청자명|요청번호|요청일련번호]]></entry>

 		var headtext  = "No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과|";
 		headtext += "용어ID|표준용어명|영문명|영문약어명|용어설명|표준도메인명|허용값|관리부서명|표준코드명|업무분야|기관명|데이터타입|데이터길이|소수점길이|저장형식|표현형식|단위|행정표준코드명";
 		headtext += "|검증결과|제정일자|요청자ID|요청자명|요청번호|요청일련번호|특이사항";
		
		var headers = [
					{Text:headtext, Align:"Center"}
				];
		
		var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
		
		InitHeaders(headers, headerInfo); 

		var cols = [						
					{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	  Align:"Center", Edit:0},
					{Type:"Status", Width:40,   SaveName:"ibsStatus", Align:"Center", Edit:0, Hidden:1},
					{Type:"CheckBox", Width:45, SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0, Sort:0},
					{Type:"Combo",  Width:80,  SaveName:"rvwStsCd",	  Align:"Center", Edit:0, Hidden:1},						
					{Type:"Text",   Width:80,  SaveName:"rvwConts",	  Align:"Left", Edit:0, Hidden:1},						
					{Type:"Combo",  Width:80,  SaveName:"rqstDcd",	  Align:"Center", Edit:1, KeyField:0, ColMerge:0,Hidden:1},						
					{Type:"Combo",  Width:80,  SaveName:"regTypCd",	  Align:"Center", Edit:0, ColMerge:0,Hidden:1},						
					{Type:"Combo",  Width:120,  SaveName:"vrfCd",	  Align:"Center", Edit:0, ColMerge:0,Hidden:1},
//		  		기관명	용어명	용어 설명	영문약어명	도메인명	허용값	저장형식	표현형식	단위	행정표준코드명	소관기관명	등록일자					
					{Type:"Text",   Width:15,  SaveName:"sditmId",     Align:"Left", Edit:1, KeyField:0, Hidden:1},
					
					
					{Type:"Text",   Width:130,  SaveName:"sditmLnm",     Align:"Left", Edit:1, KeyField:1},
					{Type:"Text",   Width:150,  SaveName:"pnm",     Align:"Left", Edit:1, KeyField:1}, //영문명
					{Type:"Text",   Width:150,  SaveName:"sditmPnm",     Align:"Left", Edit:1, KeyField:1},
					
					{Type:"Text",   Width:120,  SaveName:"objDescn",	 Align:"Left", 	 Edit:1, KeyField:1, ColMerge:0},
					
					{Type:"Text",   Width:100,  SaveName:"infotpLnm",	 Align:"Left", Edit:1, Hidden:0, KeyField:0}, //도메인명
					{Type:"Text",   Width:100,  SaveName:"alwVal",      Align:"Left", Edit:1},   // 허용값
					{Type:"Text",   Width:100,  SaveName:"ownrOrg",      Align:"Left", Edit:1, Hidden:0, KeyField:0},  // 소관기관명
					{Type:"Text",   Width:100,  SaveName:"stndCd", 	      Align:"Left", Edit:1, Hidden:0, ColMerge:0},  //표준코드명
					{Type:"Text",   Width:100,  SaveName:"bsnssFld",      Align:"Left", Edit:1, Hidden:0}, //업무분야
					{Type:"Text",   Width:100,  SaveName:"orgNm",     Align:"Left", Edit:1, KeyField:1},
					
					{Type:"Text",       Width:120,  SaveName:"dataType",	 	Align:"Left", Edit:1, Hidden:0, KeyField:0},
					{Type:"Int",       Width:80,  SaveName:"dataLen",	 	    Align:"Left", Edit:1, Hidden:0, KeyField:0},
					{Type:"Int",       Width:100,  SaveName:"dataScal",	 	Align:"Left", Edit:1, Hidden:1},
					
					
					
					
                    {Type:"Text",   Width:100,  SaveName:"saveFrm", 	 	Align:"Left", Edit:1, Hidden:1, KeyField:0},
				    {Type:"Text",   Width:100,  SaveName:"exprsnFrm",	 	Align:"Left", Edit:1, Hidden:1},
				    {Type:"Text",   Width:100,  SaveName:"unit",    	 	Align:"Left", Edit:1, Hidden:1},
					
				    
				    
				    
					{Type:"Text",   Width:100,  SaveName:"admnStndCd", 	      Align:"Left", Edit:1, Hidden:0, ColMerge:0},  //행정표준코드명
					
					{Type:"Text",   Width:100,  SaveName:"vrfRmk",      Align:"Left", Edit:0, Hidden:1},  // 검증결과
					
					{Type:"Text",   Width:130,  SaveName:"rqstDtm",  	  Align:"Center", Edit:1, Format:"yyyyMMdd", KeyField:0},
					{Type:"Text",   Width:150,  SaveName:"rqstUserId",    Align:"Center", Edit:0, Hidden:1, ColMerge:0},
					{Type:"Text",   Width:150,  SaveName:"rqstUserNm",    Align:"Center", Edit:0, ColMerge:0, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"rqstNo",        Align:"Center", Edit:0, Hidden:1, ColMerge:0}, 
					{Type:"Int",    Width:60,   SaveName:"rqstSno",       Align:"Center", Edit:0, Hidden:1, ColMerge:0},
					{Type:"Text",   Width:150,  SaveName:"spclNt",    Align:"Center", Edit:1, Hidden:0}
				];
					
		InitColumns(cols);
		
		//콤보 목록 설정
		SetColProperty("rvwStsCd", 	${codeMap.rvwStsCdibs});
		SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("vrfCd", 	${codeMap.vrfCdibs});
//		SetColProperty("stndAsrt", 	${codeMap.stndAsrtibs});
		//SetColProperty("persInfoGrd", 	${codeMap.persInfoGrdibs});
		
// 		SetColProperty("dmngId", 	${codeMap.dmngibs});
		//SetColProperty("infotpId",	${codeMap.infotpibs});
// 		SetColProperty("encYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
// 		SetColProperty("dupYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
// 		SetColProperty("persInfoYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		
		InitComboNoMatchText(1, "");
		
	  
		FitColWidth();  
	    //SetSheetHeight(200);
		SetExtendLastCol(1);	
	}
    
    init_sheet(grid_SDITM);    
    
}


function grid_SDITM_OnLoadExcel() {
}

function grid_SDITM_OnDblClick(row, col, value, cellx, celly) {
	if(row < 1) return;
}

function grid_SDITM_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;

	//선택한 상세정보를 가져온다...
	var param =  grid_SDITM.GetRowJson(row);

	//선택한 그리드의 row 내용을 보여준다.....
// 	var tmphtml = '표준용어' + ' : ' +  param.sditmLnm +"["+ param.sditmPnm+"]";
	
// 	$('#SDITM_sel_title').html(tmphtml);
	
	//검증결과 검증오류일경우가 아닐경우 RETURN
	if( grid_SDITM.GetCellValue(row, "vrfCd") != "2")  return;
	
	//var param = grid_SDITM.GetRowJson(row);

	var param1 = $("#mstFrm").serialize();
	param1 += "&rqstSno=" + param.rqstSno;

	//검증결과 조회
// 	getRqstVrfLst(param1);
	
}


function grid_SDITM_OnSaveEnd(code, message) {
	doAction("Search"); 
}

function grid_SDITM_OnSearchEnd(code, message, stCode, stMsg) {
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
<div id="grid_SDITM" class="grid_SDITM">
     <script type="text/javascript">createIBSheet("grid_SDITM", "100%", "600px");</script>
</div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<!-- <div class="selected_title_area" id="selected_title_area"> -->
<!-- 	<div class="selected_title" id="SDITM_sel_title"></div> -->
<!-- </div> -->



