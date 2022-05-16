<!DOCTYPE html>
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!-- <html> -->
<!-- <head> -->
<%-- <title><s:message code="FILE.NM.INQ" /></title> <!-- 파일명 검색 --> --%>
<script type="text/javascript">

$(document).ready(function() {
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
                    
        
        $("#popExcelDown").hide();
       //조회 이벤트 처리
		$("#popSearch").click(function(){ 
			doAction('Search'); 
		});
		
		//적용 버튼 이벤트 처리
		$("#popApply").click(function(){
			var cRow = pop_sheet.CheckedRows("chkbox");
			if(cRow < 1){
				showMsgBox("ERR", "<s:message code='MSG.JOB.CHC' />"); /* 작업을 선택해주세요. */
				return false;
			}else{
				var shdId = $("#shdId").val();

				doAction("Apply");
				
				//작업 추가 변경...
// 				doAction("Save");
				//IBS_Sheet2SheetCheck(pop_sheet ,opener.grid_sub, "chkbox", shdId);

				//window.close();
			}
		});
		
		$("#popApply").show();
		$("#popReset").hide();
		
		$("#selPrf").hide();
		$("#selBiz").hide();
		$("#selPy").hide();
		
		if("${shdKndCd}" == "QP"){	//프로파일
			$("#selPrf").show();
		}else if("${shdKndCd}" == "QB"){	//업무규칙
			$("#selBiz").show();
		}else if("${shdKndCd}" == "PY"){	//파이썬 실행
			$("#selPy").hide();
		}else if("${shdKndCd}" == "UO"){	//사용자정의이상값탐지
			$("#selPy").hide();
		}
		
		$("#shdKndCd").val("${shdKndCd}");
		//$("#shdKndCd").attr("disabled", true);
		
		//임시 메뉴목록 등장 함수
		var val = $("#dbConnTrgId option:selected").val();
		var trgId = ${codeMap.devConnTrgSch} ;
		//$("#frmSearch #dbConnTrgId").append('<option value=""></option>');
		
		for(i=0; i<trgId.length; i++) {
			if(trgId[i].upcodeCd == val) {
				$("#frmPopSearch #dbConnTrgId").append('<option value="' + trgId[i].codeCd + '">' + trgId[i].codeLnm + '</option>');
			}
		}
		
		
		$("#frmPopSearch #dbConnTrgId").change(function() {
			$("#frmPopSearch #dbSchId").find("option").remove().end();
			var val = $("#dbConnTrgId option:selected").val();

			$("#frmPopSearch #dbSchId").append('<option value=""><s:message code="CHC" /></option> ');
			
			for(i=0; i<trgId.length; i++) {
				if(trgId[i].upcodeCd == val && val!="") {
					$("#frmPopSearch #dbSchId").append('<option value="' + trgId[i].codeCd + '">' + trgId[i].codeLnm + '</option>');
				}
			}
		});
});

$(window).load(function() {
	//그리드 초기화 
    initPopGrid();
	$(window).resize();
});


$(window).resize(function(){
    //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
	setibsheight($("#grid_01"));
	// grid_sheet.SetExtendLastCol(1);    
});
function initPopGrid(){
	
	with(pop_sheet){
		
		var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"No.|상태|선택|스케줄유형|검증룰ID|작업종류코드|작업종류코드|작업명|DBMS명|스키마명|테이블명|컬럼명|작업명", Align:"Center"}
                    /* No.|상태|선택|검증룰ID|분류명|DBMS명|스키마명|테이별명|컬럼명*/
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    	Width:40,   SaveName:"ibSeq",         Align:"Center", Edit:0, Hidden:0},
                    {Type:"Status", 	Width:100,  SaveName:"ibStatus",      Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", 	Width:50,   SaveName:"chkbox",        Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Combo",      Width:80,   SaveName:"shdKndCd",      Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text", 		Width:100,  SaveName:"shdJobId",      Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text", 		Width:100,  SaveName:"etcJobKndCd",   Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text", 		Width:100,  SaveName:"shdJobKndCd",   Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Text", 		Width:200,  SaveName:"shdJobNm",      Align:"Left", Edit:0, Hidden:0},                    
                    {Type:"Text", 		Width:100,  SaveName:"dbConnTrgPnm",  Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:100,  SaveName:"dbSchPnm",      Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:100,  SaveName:"dbcTblNm",      Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:100,  SaveName:"dbcColNm",      Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text", 		Width:100,  SaveName:"etcJobNm",      Align:"Left", Edit:0, Hidden:1},                      
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);

        SetColProperty("shdKndCd", ${codeMap.schdKndCdibs});

        InitComboNoMatchText(1, "");
      
        FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(pop_sheet);    
    //===========================
		
}
	


function doAction(sAction)
{
        
    switch(sAction)
    {		    
        
        case "Search":
        	$("#shdKndCd").attr("disabled", false);
        	var param = $('#frmPopSearch').serialize();

			if($("#shdKndCd").val() == "") {

				showMsgBox("ERR", "작업유형을 입력하세요.")
				return;
			}
        	
        	pop_sheet.DoSearch('<c:url value="/dq/schedule/getJobPopList.do" />', param);
        	break;
        case "Save":
   			//배치형태
			var shdTypeCd = $("#shdTypCd", opener.document).val();
			//매주
	    	var schWeekOm = "";
	    	if(shdTypeCd == "W"){
				schWeekOm = Number("0");
				for(var i=0;i<7;i++){
					if(schdWkl[i].checked){
						schWeekOm += Number(schdWkl[i].value);
					}
				}
	    	}
			//매달
	    	var schMonOm = "";
	    	if(shdTypeCd == "M"){
				schMonOm = Number("0");
				for(var i=0;i<12;i++){
					if(schdMny[i].checked){
						schMonOm += Number(schdMny[i].value);
					}
				}
	    	}
			
	    	//작업그리드 정보
	    	var SaveJson = pop_sheet.GetSaveJson(0, "chkbox");
	    	
// 	    	if(ibsSaveJson.data.length == 0) return;
	    		
		  	var url = "<c:url value="/commons/damgmt/schedule/ajaxgrid/insertSchedule.do"/>";
		  	var param = $('.frmInput', opener.document).serialize();
		  	   param += "&shdMny="+schMonOm+"&shdWkl="+schWeekOm;
	        IBSpostJson2(url, SaveJson, param, ibscallback);
        	break;
        case "Apply":
        	var shdId = $("#shdId").val();
//         	var param = $('#frmPopSearch').serialize();
//         	pop_sheet.DoSearch('<c:url value="/commons/damgmt/schedule/getJobPopList.do" />', param);
			var SaveJson = pop_sheet.GetSaveJson(0, "chkbox");
	    	if (SaveJson.Code == "IBS000") return;
	    	var cnt = SaveJson.data.length;
	    	for (var i=0;i<cnt;i++) {
// 	    		var fid = SaveJson.data[i].shdJobId;
// 	    		var frow = opener.grid_sub.FindText("shdJobId", fid);
// 	    		if (frow == -1) {
	    			var torow = opener.grid_sub.DataInsert(-1);
	    			opener.grid_sub.SetRowData(torow, SaveJson.data[i]);
// 	    		}
	    	}
        	break;
    		
    }       
}


//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
// 	alert(res.action);
	
	switch(res.action) {
		//삭제 후 처리
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
				doAction("Search");
			break;
		//단건 저장 후 처리
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			$("#frmSearch #shdKndCd", opener.document).val('');
			opener.doAction("Search");
			break;
		case "<%=WiseMetaConfig.IBSAction.DEL_DTL%>" :
			doAction("SearchAllDtl");
			break;
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
	
}

function pop_sheet_OnClick(row, col, value, cellx, celly) {

	if(row < 1) return;
	
	var param =  pop_sheet.GetRowJson(row);
	var shdId = "&shdId="+pop_sheet.GetCellValue(row, "shdId");
}

//작업 조회 오류
function pop_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}else{
		//$("#shdKndCd").attr("disabled", true);
		
		//스케줄관리 작업정보텝 작업ID 와 비교 후 동일한 ID 존재할 경우 삭제
		var cnt = parent.opener.grid_sub.GetTotalRows();
		if(cnt > 0){
			for(var i=1; i<=cnt; i++){
				var findString = parent.opener.grid_sub.GetCellValue(i,"shdJobId");
				var delRow  = pop_sheet.FindText("shdJobId", findString);
				if(delRow > -1){
					pop_sheet.RowDelete(delRow, false);
				}
			}
		}
		
	}
}

//팝업 리턴값 제공
function returnPop(fileNm, url) {
	opener.frmInput.fileNm.value = fileNm;
	opener.frmInput.filePath.value = url;
	window.close();
}


</script>
<!-- </head> -->

<!-- <body> -->
<div class="pop_tit" >
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="JOB.INQ" /></div> <!-- 작업검색 -->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->
</div>
<!-- 팝업 내용 시작 -->
<div class="pop_content">
<!-- 검색조건 입력폼 -->
	<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmPopSearch" name="frmPopSearch" method="post">
        <input type="hidden" id="shdId" name="shdId" value="${shdId }"/>
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='JOB.INQ' />"> <!-- 작업검색 -->
                   <caption><s:message code="JOB.INQ" /></caption> <!-- 작업검색 -->
                   <colgroup>
                   <col style="width:20%;" />
                   <col style="width:30%;" />
                   <col style="width:20%;" />
                   <col style="width:30%;" />
                  </colgroup>
                   
                   <tbody>   
                   <tr>
						<th scope="row"><label for="dbConnTrgLnm"><s:message code="TRGT.NM" /></label></th> <!-- 대상명 -->
                       	<td colspan="3">
	                        <select id="dbConnTrgId"  name="dbConnTrgId">
							    <option value=""><s:message code="WHL" /></option><!--전체-->
							</select>
				             <select id="dbSchId" class="" name="dbSchId">
				             	<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
				             </select>
                        </td>
                   </tr>
                   <tr> 
	                    <th class="th_require"><s:message code="JOB.PTRN" /></th> <!-- 작업유형 -->
				        <td> 
							<select id="shdKndCd"  name="shdKndCd" class="" >
	                      		<option value="">--- <s:message code="CHC" /> ---</option> <!-- 선택 -->
								<c:forEach var="code" items="${codeMap.shdKndCd}" varStatus="status">
								<option value="${code.codeCd}">${code.codeLnm}</option>
								</c:forEach>
							</select>
				        </td>
                   		<th scope="row"><label for="vrfcTyp">검증룰유형</label></th> <!-- 검증룰유형 -->
                       	<td>
                        	<select id="vrfcTyp"  name="vrfcTyp" class="" >
		                        <option value="">--- <s:message code="CHC" /> ---</option> <!-- 선택 -->
					        	<c:forEach var="code" items="${codeMap.vrfcTyp}" varStatus="status">							
								<option value="${code.codeCd}">${code.codeLnm}</option> 							
								</c:forEach>
								<option value="PT01">참조무결성</option>
				        	</select>
                        </td>
                   </tr>
                   <tr id="selPrf">
                   		<th scope="row"><label for="prfKndCd"><s:message code="PROF.KIND" /></label></th> <!-- 프로파일 종류 -->
                       	<td>
                           	<select id="prfKndCd"  name="prfKndCd" class="" >
                           		<option value="">--- <s:message code="CHC" /> ---</option> <!-- 선택 -->
								<c:forEach var="code" items="${codeMap.prfKndCd}" varStatus="status">
								<option value="${code.codeCd}">${code.codeLnm}</option>
								</c:forEach>
							</select>
                        </td>
                   		<th scope="row"><label for=""><s:message code="ANLY.YN" /></label></th> <!-- 분석여부 -->
                       	<td>
	                        <select id="exeYn"  name="exeYn" class="" >
		                        <option value="">--- <s:message code="CHC" /> ---</option> <!-- 선택 -->
					        	<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
					        	<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니요 -->
				        	</select>
                        </td>
                   </tr>
                   <tr id="selBiz">
						<th scope="row"><label for="progrmFileNm"><s:message code="BZWR.TRRT" /></label></th> <!-- 업무영역 -->
                       	<td colspan="3">
	                        <input type="text" name="progrmFileNm" id="progrmFileNm" />
                        </td>
                   </tr>
                   <tr id="selPy">
                   		<th scope="row"><label for="pyKndCd">파이썬 실행 종류</label></th> <!-- 프로파일 종류 -->
                       	<td>
                           	<select id="pyKndCd"  name="pyKndCd" class="" >
                           		<option value="">--- <s:message code="CHC" /> ---</option> <!-- 선택 -->
								<c:forEach var="code" items="${codeMap.pyKndCd}" varStatus="status">
								<option value="${code.codeCd}">${code.codeLnm}</option>
								</c:forEach>
							</select>
                        </td>
                   </tr>
                   <tr>
                   		<th scope="row"><label for="regYn"><s:message code="REG.YN" /></label></th> <!-- 등록여부 -->
                       	<td>
                        	<select id="regYn"  name="regYn" class="" >
		                        <option value="">--- <s:message code="CHC" /> ---</option> <!-- 선택 -->
					        	<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
								<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니요 -->
				        	</select>
                        </td>
                        
						<th scope="row"><label for="shdJobNm"><s:message code="JOB.NM2" /></label></th> <!-- 작업명 -->
                       	<td>
	                        <input type="text" name="shdJobNm" id="shdJobNm"  class="wd98p"  />
                        </td>
                   </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        </form>
    </div>
	<div style="clear:both; height:10px;"><span></span></div>
    <!-- 조회버튼영역  -->         
	<tiles:insertTemplate template="/WEB-INF/decorators/buttonPop.jsp" />       
	<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("pop_sheet", "100%", "300px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
	<div style="clear:both; height:5px;"><span></span></div>
</div>	
<!-- </body> -->
<!-- </html> -->

