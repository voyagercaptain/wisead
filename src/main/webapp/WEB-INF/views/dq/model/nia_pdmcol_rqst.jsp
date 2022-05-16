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
<title>DB정보</title> <!-- 주제영역조회 -->

<script type="text/javascript">
//엔터키 이벤트 처리

$(document).ready(function() {
	
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  }).show();

                      
        // 추가 Event Bind
        $("#btnNew").click(function(){ 
        	  doAction("New");
        	
        });

        // 저장 Event Bind
        $("#btnSave").click(function(){ 
        	//var rows = grid_sheet.FindStatusRow("I|U|D");
    		var message = "<s:message code="CNF.SAVE" />";
    		showMsgBox("CNF", message, 'Save');	
        	//doAction("Save");
        	
        }).show();

        // 삭제 Event Bind
        $("#btnDelete").click(function(){ 
    		if(!grid_sheet.CheckedRows("ibsCheck")) {
    			//삭제할 대상이 없습니다...
    			showMsgBox("ERR", "<s:message code="ERR.CHKDEL" />");
    			return;
    		}
        	showMsgBox("CNF","<s:message code="CNF.DEL" />"+"</br>","Delete");
        }); 
        
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

        // 엑셀업로 Event Bind
        $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } );

      
});

$(window).load(function() {
	//그리드 초기화 
	initGrid();
	$(window).resize();
    doAction('Search');
	
});


$(window).resize(function(){
    //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
	setibsheight($("#grid_01"));
	// grid_sheet.SetExtendLastCol(1);    
});


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='NIA.HEADER.PDM.COL.RQST'/>", Align:"Center"}
                    /* No.|상태|선택|기관명|DB명|테이블영문명|테이블한글명|컬럼영문명|컬럼한글명|공통코드여부|공통코드명|컬럼설명|데이터타입|데이터길이|소수점|데이터형식|NotNull여부|PK정보|FK정보|제약조건|개인정보여부|암호화여부|공개비공개여부 */
                ];
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",      Width:40,    SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status",   Width:40,    SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:50,    SaveName:"ibsCheck",     Align:"Center", Edit:1, Sort:0},
                    {Type:"Text",     Width:150,   SaveName:"colId",    	Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:150,   SaveName:"orgNm",    	Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",     Width:150,   SaveName:"dbNm",    	    Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",     Width:200,   SaveName:"tblPnm",    	Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",     Width:150,   SaveName:"tblLnm",    	Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",     Width:200,   SaveName:"colPnm",    	Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",     Width:150,   SaveName:"colLnm",    	Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",     Width:100,   SaveName:"stndCdYn",    	Align:"Left", Edit:1, Hidden:0},
                    {Type:"Text",     Width:100,   SaveName:"stndCdNm",    	Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",     Width:100,   SaveName:"colDescn",    	Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",     Width:100,   SaveName:"dataType",    	Align:"Left", Edit:1, Hidden:0},
                    {Type:"Text",     Width:100,   SaveName:"dataLen",    	Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",     Width:100,   SaveName:"dataScal",    	Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",     Width:100,   SaveName:"dataFrm",    	Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",     Width:100,   SaveName:"notNullYn",    Align:"Left", Edit:1, Hidden:0},
                    {Type:"Text",     Width:100,   SaveName:"pkYn",    	    Align:"Left", Edit:1, Hidden:0},
                    {Type:"Text",     Width:100,   SaveName:"fkYn",    	    Align:"Left", Edit:1, Hidden:0},
                    {Type:"Text",     Width:100,   SaveName:"constrnt",    	Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",     Width:100,   SaveName:"prsnYn",    	Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",     Width:100,   SaveName:"encYn",    	Align:"Left", Edit:1, Hidden:0},
                    {Type:"Text",     Width:100,   SaveName:"openYn",    	Align:"Left", Edit:1, Hidden:0},
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
// 	     SetColProperty("regTypCd", 	{ComboCode:"C|U|D", ComboText:"<s:message code='NEW.CHG.DEL' />"}); /* 신규|변경|삭제 */
        
//         InitComboNoMatchText(1, "");
        
        
      
        // FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}

function checkDelIBS () {
	//체크박스 확인...
	if(!grid_sheet.CheckedRows("ibsCheck")) {
		//삭제할 대상이 없습니다...
		showMsgBox("ERR", "<s:message code="ERR.CHKDEL" />");
		return;
	}
	
	doAction("Delete");
	
// 	showMsgBox("CNF", "<s:message code='MSG.CHC.LWRK.LVL.DEL' />", "Delete"); /* 선택한 행의 하위 레벨도 모두 삭제됩니다.<br>삭제 하시겠습니까? */
	
}

function doAction(sAction)
{
        
    switch(sAction)
    {
        case "New":        //동일레벨 추가...
        	//선택된 행의 다음라인에 현재레벨로 추가한다.
        	var nrow = grid_sheet.DataInsert();
        
            break;
            
        case "Delete" :
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
//         	delCheckIBS(grid_sheet);

        	var url = "<c:url value="/dq/model/delWamNiaPdmColList.do"/>"

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
// 				console.log(DelJson);
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
        	
        case "DBDelete" :
        	var url = "<c:url value="/dq/model/delWamNiaPdmColAll.do"/>";
        	var param = "";
        	IBSpostJson2(url, null, param, ibscallback);
        	
        	break;
        case "Save" :
        	//TODO 공통으로 처리...
        	var SaveJson = grid_sheet.GetSaveJson(0);

	        if(SaveJson.data.length == 0){
				showMsgBox("INF", "<s:message code="ERR.CHKSAVE" />");
				return;
			}

            var url = "<c:url value="/dq/model/regNiaPdmCol.do"/>";
        	var param = "";
        	IBSpostJson2(url, SaveJson, param, ibscallback);
        	break;


        case "Search":
        	var param = $('#frmSearch').serialize();
        	grid_sheet.DoSearch("<c:url value="/dq/model/getNiaPdmColList.do" />", param);
        	break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, FileName:'DB정보등록.xlsx'});
            break;
            
        case "LoadExcel":  //엑셀업로드
            grid_sheet.LoadExcel({Append:0, Mode:'HeaderMatch'});
            break;
    }       
}

// function function grid_sheet_OnLoadExcel(result, code, msg) {
//     // 실패인 경우 code, msg 출력
//     if (!result) {
//         alert("엑셀업로드 오류: ", code, msg);
//         return;
//     }

//     //빈 서식 지우기
    
// }
 
function deleteCheck(DelJson){
	
	for(var i=0; i<DelJson.data.length; i++) {
		if(DelJson.data[i].reaTblCnt == null || DelJson.data[i].reaTblCnt == '' || DelJson.data[i].reaTblCnt == '0'){
		
		} else {
			return DelJson.data[i].fullPath;  //삭제 불가일 경우 0 리턴
		}
	}
	return 1;
}


//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	switch(res.action) {

		//삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
				doAction("Search");
				//doActionCol("Search");
		
			break;
		//단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
// 			alert (res.resultVO.progrmFileNm);
			//전체 내용을 다시 조회 할 경우 사용...
			doAction("Search");
			break;
			
				//여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			  doAction("Search");
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
	//alert(code);
	if (code == 0) {
		alert("<s:message code='MSG.STRG.SCS' />"); /* 저장 성공했습니다. */
	} else {
		alert("<s:message code='MSG.STRG.FALR' />"); /* 저장 실패했습니다. */
	}
}




</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title">DB정보</div> <!-- 주제영역 관리 -->
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>
<div class="stit"><s:message code="INQ.COND2" /></div> 
<!-- 검색조건 입력폼 -->
<div id="search_div">
        <!-- 조회버튼영역  -->
         <form id="frmSearch" name="frmSearch" method="post">
           <fieldset>
           <legend>머리말</legend>
           <div class="tb_basic2">
               <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="DB정보조회">
                  <caption>용어검색조건</caption>
                  <colgroup>
                  <col style="width:10%;" />
                  <col style="width:15%;" />
                  <col style="width:10%;" />
                  <col style="width:15%;" />
                  <col style="width:10%;" />
                  <col style="width:15%;" />
                  <col style="width:10%;" />
                  <col style="width:15%;" />                  	
                  </colgroup>
                  
                  <tbody>                            
					<tr>
					<th>컬럼영문명</th>
					<td><input class="wd200" id="colPnm" name="colPnm"/></td>
					<th>컬럼한글명</th>
					<td><input class="wd200" id="colLnm" name="colLnm"/></td>
					<th>공통코드여부</th>
					<td>
					<select id="stndCdYn" name="stndCdYn">
						<option value="">전체</option>
						<option value="Y">Y</option>
						<option value="N">N</option>
					</select>
					</td>		
					<th>공통코드명</th>
					<td><input class="wd200" id="stndCdNm" name="stndCdNm"/></td>										
					</tr>
                  </tbody>
                </table>   
           </div>
           </fieldset>
        
       </form>
		<div style="clear:both; height:10px;"><span></span></div>
		
			<div class="divLstBtn" >	 
            <div class="bt03">
     			    <button class="btn_search" id="btnSearch" 	name="btnSearch"><s:message code="INQ"/></button> <!-- 조회 -->
               <button class="btn_rqst_new" id="btnTreeNew" name="btnTreeNew"><s:message code="ADDT" /></button> <!-- 추가 -->                                                         
				  <ul class="add_button_menu" id="addTreeMenu">
				    <li id="btnNew"><a><span class="ui-icon ui-icon-pencil"></span><s:message code="NEW.ADDT" /></a></li> <!-- 신규 추가 -->
				    <li id="btnExcelLoad"><a><span class="ui-icon ui-icon-document"></span><s:message code="EXCL.UPLOAD" /></a></li> <!-- 엑셀 업로드 -->
				  </ul>         
  			    <button class="btn_save" id="btnSave" 	name="btnSave"><s:message code="STRG" /></button> <!-- 저장 --> 
			    <button class="btn_delete" id="btnDelete" 	name="btnDelete"><s:message code="DEL" /></button> <!-- 삭제 --> 
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
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>
</body>
</html>