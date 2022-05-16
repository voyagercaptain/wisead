<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title><s:message code="PHYC.MDEL.CLMN.EXCL.UPLOAD" /></title><!-- 물리모델컬럼-엑셀업로드 -->
<!-- <link rel="stylesheet" type="text/css" href="css/design.css"> -->

<script type="text/javascript">

$(document).ready(function() {
	
    //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
    $("div.pop_tit_close").click(function(){
    	
    	//iframe 형태의 팝업일 경우
    	if ("${search.popType}" == "I") {
    		parent.closeLayerPop();
    	} else {
    		window.close();
    	}
    	
    });
	
	
	//엑셀 올리기 버튼 셋팅 및 클릭 이벤트 처리...
	$('#btnExcelUp').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		doAction('LoadExcel');
	});
	
	//엑셀 저장 버튼 초기화...
	$('#btnSaveExl').click(function(event){
		//var rows = grid_sheet.FindStatusRow("I|U|D");
    	var rows = grid_sheet.IsDataModified();
    	if(!rows) {
//     		alert("저장할 대상이 없습니다...");
    		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
    		return;
    	}
    	
    	//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'Save');	
    	//doAction("Save");  
	});
	 // 엑셀내리기 Event Bind
    $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
		
});

$(window).load(function() {
// 	alert('window.load');
	initGrid();
	$(window).resize();
	//페이지 호출시 처리할 액션...
// 	doAction('Search');
});


$(window).resize(
    
    function(){
    	//그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
    	setibsheight($("#grid_01"));       
    	// grid_sheet.SetExtendLastCol(1);    
    }
);


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext  = "<s:message code='META.HEADER.PDMCOL.XLS.1'/>";
	    	headtext += "<s:message code='META.HEADER.PDMCOL.XLS.2'/>";
	    	headtext += "<s:message code='META.HEADER.PDMCOL.XLS.3'/>";
		    headtext += "<s:message code='META.HEADER.PDMCOL.XLS.4'/>";

		    //headtext  = "No.|상태|삭제|요청구분|등록유형";
	    	//headtext += "|주제영역|테이블(물리명)|테이블(논리명)|컬럼(물리명)|컬럼(논리명)|이전컬럼(물리명)";
	    	//headtext += "|컬럼순서|데이터타입|길이|소수점길이|PK 여부|PK 순서|NOT NULL여부|DEFAULT 값|암호화여부";
		   // headtext += "|설명|요청일시|요청자ID|요청자명|요청번호|요청일련번호|요청상세일련번호";
        
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
					{Type:"Seq",	  Width:50,  SaveName:"ibsSeq",	Align:"Center", Edit:0},
					{Type:"Status",   Width:40,  SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:0},
					{Type:"DelCheck", Width:40,  SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0},				
					{Type:"Combo",    Width:80,  SaveName:"rqstDcd",	Align:"Center", Edit:1, KeyField:1},						
					{Type:"Combo",    Width:80,  SaveName:"regTypCd",	Align:"Center", Edit:0},						
                    
					{Type:"Text",     Width:100, SaveName:"fullPath"    ,Align:"Left",   Edit:1, KeyField:1},
                    {Type:"Text",     Width:100, SaveName:"pdmTblPnm"   ,Align:"Left",   Edit:1, KeyField:1},
                    {Type:"Text",     Width:100, SaveName:"pdmTblLnm"   ,Align:"Left",   Edit:1, KeyField:1},
					{Type:"Text",     Width:100, SaveName:"pdmColPnm"   ,Align:"Left",   Edit:1, KeyField:1},
                    {Type:"Text",     Width:100, SaveName:"pdmColLnm"   ,Align:"Left",   Edit:1, KeyField:0},
                    {Type:"Text",     Width:40,  SaveName:"bfPdmColPnm" ,Align:"Left",   Edit:1},
                    {Type:"Int",      Width:60,  SaveName:"colOrd"      ,Align:"Left",   Edit:1, KeyField:1},
                    {Type:"Text",     Width:80,  SaveName:"dataType"    ,Align:"Left",   Edit:1},
                    {Type:"Text",     Width:40,  SaveName:"dataLen"     ,Align:"Left",   Edit:1},
                    {Type:"Text",     Width:80,  SaveName:"dataScal"    ,Align:"Left",   Edit:1},
                    {Type:"Combo",    Width:40,  SaveName:"pkYn"        ,Align:"Left",   Edit:1},
                    {Type:"Int",      Width:60,  SaveName:"pkOrd"       ,Align:"Left",   Edit:1},
                    {Type:"Combo",    Width:80,  SaveName:"nonulYn"     ,Align:"Left",   Edit:1},
                    {Type:"Text",     Width:80,  SaveName:"defltVal"    ,Align:"Left",   Edit:1},
                    {Type:"Combo",    Width:80,  SaveName:"encYn"       ,Align:"Left",   Edit:1},
                    {Type:"Text",     Width:100, SaveName:"objDescn"    ,    Align:"Left",   Edit:1},

                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
		SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});

		SetColProperty("pkYn", 		{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("nonulYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("encYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        
        InitComboNoMatchText(1, "");
        
    	//히든 컬럼 설정...
     	SetColHidden("regTypCd"	,1); 
        
        
        // FitColWidth();
        
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
        	
        	break;
        	
 	 	case "Save" : //엑셀 일괄 저장
 	 	
 	 		var SaveJson = grid_sheet.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일

 	 	   	//데이터 사이즈 확인...
 	 	   	if (SaveJson.Code == "IBS000") return;
 	 	   	if (SaveJson.Code == "IBS010") return;
 	 	   	
 	 	   	//alert(SaveJson.data.length); return;
 	 	    //var url = "<c:url value="/meta/model/regpdmcolxlsrqstlist.do"/>";
 	 	    
 	 	    var url = "<c:url value="/meta/model/regPdmTblColXlsRqstList.do"/>";
 	 	    
	 	 	if ("${search.popType}" == "I") {
	      		param = $("#mstFrm", parent.document).serialize();
	      	} else {
	      		param = $("#mstFrm", opener.document).serialize();
	      	}
	 	 	
 	 	    IBSpostJson2(url, SaveJson, param, ibscallback);
			break;        
 	    case "LoadExcel":  //엑셀업로드
 	      
 	    	grid_sheet.LoadExcel({Mode:'HeaderMatch'});
 	        
 	        break;

 	   case "Down2Excel":  //엑셀내려받기
			grid_sheet.Down2Excel(    { HiddenColumn:1, Merge:1, FileName:'물리모델등록요청'}    );
			break;
    		
    	case 'Detail' : //상세 정보
    		//onSelectRow 그리드 함수에서 처리...
    		break;

    }       
}


/*
    row : 행의 index
    col : 컬럼의 index
    value : 해당 셀의 value
    x : x좌표
    y : y좌표
*/
function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
    if(row < 1) return;
    

}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	

}

//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	//alert(res.action);
	
	switch(res.action) {
	
		//기존 표준단어 요청서에 변경요청 추가 후처리 함수...
		case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
			if(!isBlankStr(res.resultVO.rqstNo)) {
// 	    		alert(res.resultVO.rqstNo);
				if ("${search.popType}" == "I") {
					parent.postProcessIBS(res);
				}else{
					opener.postProcessIBS(res);
				}
				//팝업닫기
				$("div.pop_tit_close").click();
// 	    		json2formmapping ($("#mstFrm", opener.document), res.resultVO);
	    		
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
// 	    		$("#mstFrm #bizDtlCd", opener.document).val(res.resultVO.bizInfo.bizDtlCd);
// 	    		$("#mstFrm #bizDtlCd", opener.document).val("STWD");
	    		
// 	    		$("form#frmSearch input[name=rqstNo]").val(res.resultVO.rqstNo);
// 	    		if ($("#mstFrm #rqstStepCd", opener.document).val() == "S")  {
// 	    			$("#btnRegRqst", opener.document).show();
// 	    		}
// 	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
// 				opener.doAction("Search");    		
	    	} 
			
			break;
	
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
				
				//doActionCol("Search");
		
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :

			
			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			//저장완료시 요청서 번호 셋팅...
	    	/* if(!isBlankStr(res.ETC.rqstNo)) {
	    		//alert(res.ETC.rqstNo);
	    		$("form#frmSearch input[name=rqstNo]").val(res.ETC.rqstNo);
// 	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("Search");    		
	    	} */
			
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
	
}




</script>
</head>

<body>
<div class="pop_tit" >
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="PHYC.MDEL.CLMN.EXCL.UPLOAD" /></div> <!-- 엑셀업로드-물리모델컬럼 -->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->

    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic"  style="display: none;">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MENU.INQ' />"> <!-- 메뉴조회 -->
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                  </colgroup>
                   
                   <tbody>
                   		<tr>                          
                            <th scope="row"><label for="stwdLnm"><s:message code="STRD.WORD.NM" /></label></th> <!-- 표준단어명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="stwdLnm" id="stwdLnm" />
                                </span>
                            </td>  
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
            
        </form>
       <div class="tb_comment"><s:message  code='ETC.EXCEL' /></div>
		<div style="clear:both; height:10px;"><span></span></div>
    <!-- 조회버튼영역  -->
	
	<div class="divLstBtn" >	 
		<div class="bt03">
		<button class="da_default_btn" id="btnExcelUp" name="btnExcelUp"><s:message code="EXCL.UPLOAD" /></button> <!-- 엑셀 올리기 -->
		<button class="da_default_btn" id="btnSaveExl" name="btnSaveExl"><s:message code="STRG" /></button> <!-- 저장 -->
		</div>
		<div class="bt02">
			<button class="btn_excel_down" id="btnExcelDown" name="btnExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                        
		</div>
	</div>
	
</div>       
<div style="clear:both; height:5px;"><span></span></div>

        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

</body>
</html>

