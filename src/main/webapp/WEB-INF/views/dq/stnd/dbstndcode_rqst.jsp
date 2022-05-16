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
<title><s:message code="STRD.WORD.EXCEL.UPLOAD"/></title> <!-- 표준단어엑셀업로드 -->
<!-- <link rel="stylesheet" type="text/css" href="css/design.css"> -->

<script type="text/javascript">

$(document).ready(function() {


	mstFrmReset("DMP");
	
	//업무구분상세 초기화...
	$("#mstFrm #bizDtlCd").val("${waqMstr.bizDtlCd}");
	
	$("[id$='-${waqMstr.bizDtlCd}'] a").click();	

	$("#Msthidden").hide();

	// 엑셀내리기 Event Bind
    $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
	//엑셀 LOAD
    $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } );
	
	
	//추가
    $("#btnNew").click( function(){
    	doAction("New");
    } );
	
	//조회
    $("#btnSearch").click( function(){
    	doAction("Search"); 
    } ).show();
	
	//변경대상 추가
	$("#btnChangAdd").click(function(){
		doAction("AddWam");
	}).hide();
	
	//저장
    $("#btnSave").click( function(){
    	var bizDtlCd = $("form[name=mstFrm] #bizDtlCd").val();
    	doAction("Save")
    	    	
    } ).show();
	
    //삭제
    $("#btnDelete").click( function(){
		//체크박스가 입력상태인 경우 삭제...
		if(!grid_sheet.CheckedRows("ibsCheck")) {
			//삭제할 대상이 없습니다...
			showMsgBox("ERR", "<s:message code="ERR.CHKDEL" />");
			return;
		}
    	showMsgBox("CNF","<s:message code="CNF.DEL" />"+"</br>","Delete");
    } ).show();
    
    
    
    //화면리로드
    $("#btnBlank").click( function(){
		location.href = '<c:url value="/dq/stnd/codedfnt_rqst.do" />';
    } );
    
		
 // 결재 Event Bind
	$("#btnRegRqst").click(function(){
	}).hide();

	doAction("Search"); 
});

$(window).load(function() {

	var bizDtlCd = $("form[name=mstFrm] #bizDtlCd").val();

	var rqststep = $("#mstFrm #rqstStepCd").val();
	setDispRqstMainButton(rqststep, grid_sheet);

	//검토처리 버튼 보여주기....
	checkApproveYn($("#mstFrm"));

	
	initGrid();
// 	$(window).resize();
	//페이지 호출시 처리할 액션...
});


$(window).resize(
    
);


//텝 클릭시 요청정보 재조회
//단 등록완료 후 재조회 시 재조회 요청정보 셋팅
function mstFrmReset(bizDtlCd){
	$("form[name=mstFrm] #bizDtlCd").val(bizDtlCd);
	if("${waqMstr.bizDtlCd}" == $("form[name=mstFrm] #bizDtlCd").val() ){
		$("form[name=mstFrm]")[0].reset();
		$("form[name=mstFrm] #bizDcd").val("${waqMstr.bizDcd}");
		$("form[name=mstFrm] #rqstStepCd").val("${waqMstr.rqstStepCd}");
		$("form[name=mstFrm] #bizDtlCd").val("${waqMstr.bizDtlCd}");
	}else{
	}
}


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext = "<s:message code='META.HEADER.DB.CD.DFNT.RQST1'/>";
        headtext += "<s:message code='META.HEADER.DB.CD.DFNT.RQST2'/>";
        headtext += "<s:message code='META.HEADER.DB.CD.DFNT.RQST3'/>";

        //No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과|
        //코드값ID|기관명|공통코드명|코드|코드명|
        //등록일자|요청자ID|요청자명|요청번호|요청일련번호
        
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
					{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	    Align:"Center", Edit:0},
					{Type:"Status", Width:60,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
					{Type:"CheckBox", Width:50, SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
					{Type:"Combo",  Width:100,  SaveName:"rvwStsCd",	Align:"Center", Edit:0, Hidden:1},						
					{Type:"Text",   Width:100,  SaveName:"rvwConts",	Align:"Left",   Edit:0, Hidden:1},						
					{Type:"Combo",  Width:80,   SaveName:"rqstDcd",	    Align:"Center", Edit:1, Hidden:1},						
					{Type:"Combo",  Width:80,   SaveName:"regTypCd",	Align:"Center", Edit:0, Hidden:1},						
					{Type:"Combo",  Width:120,  SaveName:"vrfCd",		Align:"Center", Edit:0, Hidden:1},
					
					{Type:"Text",   Width:150,  SaveName:"cdValId",   	Align:"Left", Edit:0, KeyField:0, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"orgNm",   	Align:"Left", Edit:1, KeyField:1},
					{Type:"Text",   Width:100,  SaveName:"dbNm",   	Align:"Left", Edit:1, KeyField:1},
					{Type:"Text",   Width:100,  SaveName:"commCdNm",   	Align:"Left", Edit:1, KeyField:1},
					{Type:"Text",   Width:100,  SaveName:"cdVal",   	Align:"Left", Edit:1, KeyField:1}, 
					{Type:"Text",   Width:150,  SaveName:"cdValNm", 	Align:"Left", Edit:1, Hidden:0, KeyField:1},
					
					{Type:"Text",   Width:80,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
					{Type:"Text",   Width:60,  SaveName:"rqstUserId",   Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:60,  SaveName:"rqstUserNm",   Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:60,  SaveName:"rqstNo",       Align:"Center", Edit:0, Hidden:1}, 
					{Type:"Int",   Width:60,   SaveName:"rqstSno",      Align:"Center", Edit:0, Hidden:1}
                    
                ];
                    
        InitColumns(cols);
	     
        //콤보 목록 설정...
        SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});

        InitComboNoMatchText(1, "");
      
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
        
		case "New":  //그리드추가
			grid_sheet.DataInsert(0);
	    	break;

// 		case "AddWam"://?
// 			var url='<c:url value="/dq/stnd/codedfnt_rqst.do"/>';
// 			var "?popRqst=Y"
// 			var popup = OpenWindow(url+param,"AddWam","1000","600","yes");

    	case "LoadExcel":  //엑셀업로드
    		grid_sheet.LoadExcel({Mode:'HeaderMatch', Append:1});
        	break;
        
    	case "Down2Excel":  //엑셀다운로드
//     		if(grid_sheet.GetTotalRows() == 0 ){
//     			grid_sheet.DataInsert(0);
//     		}
    	    var fileName="코드정의서(DB표준).xlsx";
    		grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, Mode:2, FileName : fileName});
        	break;

        case "Search":

        	var url = '<c:url value="/dq/stnd/getDbCodelist.do"/>';
        	var param = $("#frmSearch").serialize();
        	grid_sheet.DoSearch(url, param);
        	
        	break;
        	
 	 	case 'Save' : //검증
    		//저장 대상의 데이터를 Json 객체로 반환한다.
			ibsSaveJson = grid_sheet.GetSaveJson(0);
    	
    		//2. 필수입력 누락인 경우
			if (ibsSaveJson.Code == "IBS010") return;
			
			if(ibsSaveJson.data.length == 0){
				showMsgBox("INF", "<s:message code="ERR.CHKSAVE" />");
				return;
			}

			var row = grid_sheet.ColValueDup("orgNm|dbNm|commCdNm|cdVal|cdValNm");
			var rows = grid_sheet.ColValueDupRows("orgNm|dbNm|commCdNm|cdVal|cdValNm");
			
			if(row>0){
			    showMsgBox("INF","<s:message code="ERR.DUP" />"+"</br>"+rows+"행");
			    rows = rows.split(",");
			    for(var i in rows){
			    	grid_sheet.SetRowFontColor(rows[i], "#FF0000");
				 }
				return;
			}				

			var url = '<c:url value="/dq/stnd/regDbCodeWamlist.do"/>';
			
			var param = $('form[name=mstFrm]').serialize();
	        IBSpostJson2(url, ibsSaveJson, param, ibscallback);
	        
        	break; 
			
    	case "Delete" :
			//체크박스가 입력상태인 경우 삭제...
			if(!grid_sheet.CheckedRows("ibsCheck")) {
				//삭제할 대상이 없습니다...
				showMsgBox("ERR", "<s:message code="ERR.CHKDEL" />");
				return;
			}
        	
			var url = '<c:url value="/dq/stnd/delDbCodeWamlist.do"/>';
			
			//삭제로직 김경택
			//모든 Row에서 Check 된 것을 저장한 뒤에
			//(if)체크 된 것 중 입력중인 내용만 있으면 Row만 삭제하고 BackEnd 타지않음
			//(else if)체크 된 것 중 vrfCd! = 4 인 것만 있으면, 즉 한번이라도 검증을 탔으면 Front에서  Param 수집 후 Front상의 Row를 삭제한 뒤 BackEnd로 넘어가서 WAQ에서 삭제
			//(else)체크 된 것 중 입력중인 상태와 vrfCd(검증상태)가 4(검증전)가 아닌 것이 동시에 있으면, 
			//입력중인 상태의 Row를 먼저 삭제하고, Param 수집 후 Front에서 Row를 삭제한 뒤에 BackEnd로 넘어가서 WAQ에서 삭제함
			//이 모든 과정은 화면 새로고침을 하지 않음.
			var empty_Row_Location = new Array();
			var vrfed_Row_Location = new Array();
			
			for(var i = 1 ; i <= grid_sheet.GetDataLastRow(); i++) {
				if(grid_sheet.GetCellValue(i,'ibsCheck') == '1' && grid_sheet.GetCellValue(i,'ibsStatus') == 'I') {
					empty_Row_Location.push(i)
				}else if(grid_sheet.GetCellValue(i,'ibsCheck') == '1' && grid_sheet.GetCellValue(i,'ibsStatus') != 'I'){
					vrfed_Row_Location.push(i)
				} 
			}

			if (empty_Row_Location.length != 0 && vrfed_Row_Location.length == 0){
				var delete_Row = '' 
										
				for(var i = 1; i <=empty_Row_Location.length; i++){
					if (delete_Row == ''){
						delete_Row += ''+empty_Row_Location[i-1]
					}else{
						delete_Row += '|'+empty_Row_Location[i-1]
					}
				}
				grid_sheet.RowDelete(delete_Row, 0);
				
				empty_Row_Location.clear
				vrfed_Row_Location.clear
			}else if(empty_Row_Location.length == 0 && vrfed_Row_Location.length != 0){
				var DelJson = grid_sheet.GetSaveJson({AllSave:0, StdCol:"ibsCheck", ValidKeyField :0});
				var param = $("#mstFrm").serialize();
				
				var delete_Row = '' 
					
				for(var i = 1; i <=vrfed_Row_Location.length; i++){
					if (delete_Row == ''){
						delete_Row += ''+vrfed_Row_Location[i-1]
					}else{
						delete_Row += '|'+vrfed_Row_Location[i-1]
					}
				}
				grid_sheet.RowDelete(delete_Row, 0)
				
				empty_Row_Location.clear
				vrfed_Row_Location.clear
				IBSpostJson2(url, DelJson, param, ibscallback)
				
			}else{
				var delete_Row = '' 
					
				for(var i = 1; i <=empty_Row_Location.length; i++){
					if (delete_Row == ''){
						delete_Row += ''+empty_Row_Location[i-1]
					}else{
						delete_Row += '|'+empty_Row_Location[i-1]
					}
				}
				grid_sheet.RowDelete(delete_Row, 0);
				
				var DelJson = grid_sheet.GetSaveJson({AllSave:0, StdCol:"ibsCheck", ValidKeyField :0});
				var param = $("#mstFrm").serialize();
				
				var delete_Row = ''
				
				for(var i = 1; i <=grid_sheet.GetDataLastRow(); i++){
					if(grid_sheet.GetCellValue(i,'ibsCheck') == '1') {
						if (delete_Row == ''){
							delete_Row += ''+i
						}else{
							delete_Row += '|'+i
						}
					}
				}
				grid_sheet.RowDelete(delete_Row, 0);				
				
				empty_Row_Location.clear
				vrfed_Row_Location.clear
				IBSpostJson2(url, DelJson, param, ibscallback);
			}

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
	
	if(row < 1) return;

}


function grid_sheet_OnSaveEnd(code, message) {
	doAction("Search"); 
}

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg){
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
/*======================================================================*/
//IBSpostJson2 후처리 함수
/*======================================================================*/
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
			if(!isBlankStr(res.resultVO.rqstNo)) {
				alert(res.resultVO.rqstNo);
	    		json2formmapping ($("#mstFrm"), res.resultVO);
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
	    		$("#mstFrm #bizDtlCd").val(res.resultVO.bizDtlCd);
	    		
	    		if ($("#mstFrm #rqstStepCd").val() == "S")  {
	    		}
	    	} 
			break;
			
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			break;
		
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
			//저장완료시 마스터 정보 셋팅...
	    	 if(!isBlankStr(res.resultVO.rqstNo)) {


	    		json2formmapping ($("#mstFrm"), res.resultVO);
	    		
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
	    		$("#mstFrm #bizDtlCd").val(res.resultVO.bizDtlCd);
	    		
	    		//등록요청 버튼 활성화
	    		if ($("#mstFrm #rqstStepCd").val() == "S")  {
	    		} 
				doAction("Search"); 
	    	} 
			
			break;
				
		//요청서 결재단계별 승인 완료 후처리
		case "<%=WiseMetaConfig.RqstAction.APPROVE%>":
//			var url = '<c:url value="/dq/stnd/stndtot_rqst.do" />';
//			var param = $('form[name=mstFrm]').serialize();
//			location.href = url+"?"+param;
			var url = '<c:url value="/dq/stnd/codedfnt_rqst.do" />';
//			var param = $('form[name=mstFrm]').serialize();
			location.href = url;
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
}

</script>
</head>

<body>

<div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title">코드정의서</div> <!-- 항목 자동분할 -->
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='BFHD.INTG.INQ' />"> <!-- 사전통합조회 -->
                   <caption><s:message code="BFHD.INTG.INQ.FORM" /></caption> <!-- 사전통합 검색폼 -->
                   <colgroup>
	                   <col style="width:10%;" />
	                   <col style="width:23%;" />
	                   <col style="width:10%;" />
	                   <col style="width:23%;" />
	                   <col style="width:10%;" />
	                   <col style="width:23%;" />	
<%--                    <col style="width:8%;" /> --%>
<%--                    <col style="width:35%;" /> --%>
                   </colgroup>
                   
                   <tbody>      
                            <tr>
					         <th scope="row"><label for="orgNm">기관명</label></th> <!-- 사전유형 -->
                                <td >
                                <input type="text" id="orgNm" name="orgNm" class="wd200"/>
								</td>
                                <th scope="row"><label for="dbNm">DB명</label></th> 
                                <td><input type="text" id="dbNm" name="dbNm" class="wd200" /></td>
                                <th scope="row"><label for="commCdNm">공통코드명</label></th> 
                                <td><input type="text" id="commCdNm" name="commCdNm" class="wd200"/></td>                                
                            </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
</div>
<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqst.jsp" />

	<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div class="grid_01" id="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            
	</div> 	
	<!-- 그리드 입력 입력 End -->
	
	<div id = "Msthidden">
	<form name="mstFrm" id="mstFrm">
				<!-- 전체승인 및 전체반려시 승인/반려 및 반려 사유를 마스터에서 처리한다. -->
				<input type="hidden" name="rvwStsCd" id="rvwStsCd">
				<input type="hidden" name="rvwConts" id="rvwConts">
				<input type="hidden" name="rqstUserId" id="rqstUserId" value="${waqMstr.rqstUserId}" />
				
			    <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
					<caption><s:message code="SUBJ.TRRT.INFO" /></caption> <!-- 주제영역정보 -->
					<colgroup>
<%-- 					<col style="width:15%;" /> --%>
<%-- 					<col style="width:85%;" /> --%>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row"><label for="rqstNo"><s:message code="DEMD.NO" /></label></th> <!-- 요청번호 -->
							<td><input type="text" name="rqstNo" id="rqstNo" class="wd98p" value="${waqMstr.rqstNo}" /></td>
						</tr>
						<tr>
							<th scope="row"><label for="rqstNm"><s:message code="LORQ.NM" /></label></th> <!-- 요청서명 -->
							<td><input type="text" name="rqstNm" id="rqstNm" class="wd98p" value="${waqMstr.rqstNm}" /></td>
						</tr>
						<tr>
							<th scope="row"><label for="bizDcd"><s:message code="BZWR.DSTC" /></label></th> <!-- 업무구분 -->
							<td>
							<!-- bizDcd를 selectBox로만 받을경우 stndtot_rqst에서 null로 인식하여 문제발생. 그래서 hidden값으로 추가 -->
							<input type="hidden" id="bizDcd" 	name="bizDcd" 	value="${waqMstr.bizDcd}" />
							<input type="text" id="bizDcdNm" 	name="bizDcdNm" 	value="${waqMstr.bizDcdNm}" />
<%-- 							<select id="bizDcd" class="" name="bizDcd"> --%>
<%-- 							<c:forEach var="code" items="${codeMap.bizDcd}" varStatus="status"> --%>
<%-- 							  <option value="${code.codeCd}">${code.codeLnm}</option> --%>
<%-- 							</c:forEach> --%>
<%-- 					 		</select> --%>
							</td>
						</tr>
						<tr style="display:none">
							<th scope="row"><label for="bizDtlCd"><s:message code="BZWR.DSTC.DTL" /></label></th> <!-- 업무구분상세 -->
							<td><input type="text" name="bizDtlCd" id="bizDtlCd" class="wd98p" value="" /></td>
						</tr>
						<tr>
							<th scope="row"><label for="rqstStepCd"><s:message code="DEMD.PRGS.STS" /></label></th> <!-- 요청진행상태 -->
							<td>
							<input type="hidden" id="rqstStepCd" 	name="rqstStepCd" 	value="${waqMstr.rqstStepCd}" />
							<input type="text" id="rqstStepCdNm" 	name="rqstStepCdNm" 	value="${waqMstr.rqstStepCdNm}" />
<%-- 							<select id="rqstStepCd" class="" name="rqstStepCd"> --%>
<%-- 							<c:forEach var="code" items="${codeMap.rqstStepCd}" varStatus="status"> --%>
<%-- 							  <option value="${code.codeCd}">${code.codeLnm}</option> --%>
<%-- 							</c:forEach> --%>
<%-- 					 		</select> --%>
					 		</td>
						</tr>
						<tr>
							<th scope="row"><label for="rqstResn"><s:message code="DEMD.RSN" /></label></th> <!-- 요청사유 -->
							<td><input type="text" name="rqstResn" id="rqstResn" class="wd98p" value="${waqMstr.rqstResn}" /></td>
						</tr>
						<tr style="display:none">
							<th scope="row"><label for="rqstResn"><s:message code="APRL.STEP" /></label></th> <!-- 결재단계 -->
							<td><input type="text" id="aprLvl" 	name="aprLvl" 	value="${waqMstr.aprLvl}" /></td>
						</tr>
					</tbody>
				</table>
		</form>
	</div>
   	
</div>


</body>
</html>

