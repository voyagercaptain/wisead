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
<title><s:message code="DIAG.TRGT.DB.MS.INQ" /></title><!--진단대상DBMS 조회-->

<script type="text/javascript">

var interval = "";
var connTrgSchJson = ${codeMap.devConnTrgSch} ;
//엔터키 이벤트 처리
EnterkeyProcess("Search");

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

	$("#btnExec").click(function(){ doAction("Exec");  }).show();

	// 엑셀내리기 Event Bind
	$("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).hide();

	$("#btnExcelLoad").click( function(){ doAction("Load2Excel"); } ).hide(); 
	
		
	//임시 메뉴목록 등장 함수
	var val = $("#dbConnTrgId option:selected").val();
	var trgId = ${codeMap.devConnTrgSch} ;
	//$("#frmSearch #dbConnTrgId").append('<option value=""></option>');
	
	for(i=0; i<trgId.length; i++) {
		if(trgId[i].upcodeCd == val) {
			$("#frmSearch #dbConnTrgId").append('<option value="' + trgId[i].codeCd + '">' + trgId[i].codeLnm + '</option>');
		}
	}
	
	
	$("#frmSearch #dbConnTrgId").change(function() {
		$("#frmSearch #dbSchId").find("option").remove().end();
		var val = $("#dbConnTrgId option:selected").val();

		$("#frmSearch #dbSchId").append('<option value=""><s:message code="CHC" /></option> ');
		
		for(i=0; i<trgId.length; i++) {
			if(trgId[i].upcodeCd == val && val!="") {
				$("#frmSearch #dbSchId").append('<option value="' + trgId[i].codeCd + '">' + trgId[i].codeLnm + '</option>');
			}
		}
	});
	
	$("#btnLogPop").click(function(){
		var param = "";
		var url   = "<c:url value="/dq/scheduler/popup/schedulelog_pop.do"/>";
    	var popup = OpenWindow(url+param,"SQL","1200","750","yes");
	});
	
	doAction("Search");
});

$(window).load(function() {

});


$(window).resize(
    function(){

    	setibsheight($("#grid_01"));

    	//그리드 가로 스크롤 방지
    	//grid_sheet.FitColWidth();
    }
);


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100, VScrollMode:1};
        SetConfig(cfg);

        //No.|상태|선택|DB접속대상ID|만료일시|시작일시|DB접속대상물리명|DB접속대상논리명|DBMS종류|DBMS버전|접속대상DB연결문자열|접속대상연결URL|접속대상드라이버명|DB접속계정ID|DB접속계정비밀번호|DB접속상태|담당자명|담당자연락처|객체설명|객체버전|등록유형코드|작성일시|작성사용자ID
        
        var headerText = "No.|상태|선택|RULE_REL_ID|DB_SCH_ID|DB_CONN_TRG_ID|DBMS명|스키마명|테이블명|테이블한글명|컬럼명|컬럼한글명|검증룰ID|검증룰|코드분류ID|shdJobId|shdTypCd";
        
        var headers = [
                    {Text: headerText, Align:"Center"}
                ];
            
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",      Width:60,   SaveName:"ibsSeq",        Align:"Center", Edit:0},
                    {Type:"Status",   Width:80,   SaveName:"ibsStatus",     Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:60,   SaveName:"expYn",         Align:"Center", Edit:1, Sort:0},                    
                    {Type:"Text",     Width:100,  SaveName:"ruleRelId",    	Align:"Left", Edit:0, Hidden:1},                    
                    {Type:"Text",     Width:100,  SaveName:"dbSchId",    	Align:"Left", Edit:0, Hidden:1},                   
                    {Type:"Text",     Width:180,  SaveName:"dbConnTrgId",  Align:"Left", Edit:1, Hidden:1},
                    {Type:"Text",     Width:180,  SaveName:"dbConnTrgPnm",  Align:"Left", Edit:0},
                    {Type:"Text",     Width:180,  SaveName:"dbSchPnm",      Align:"Left", Edit:0},
                    {Type:"Text",     Width:200,  SaveName:"dbcTblNm",    	Align:"Left", Edit:0}, 
                    {Type:"Text",     Width:200,  SaveName:"dbcTblKorNm",   Align:"Left", Edit:0},
                    {Type:"Text",     Width:200,  SaveName:"dbcColNm",    	Align:"Left", Edit:0}, 
                    {Type:"Text",     Width:200,  SaveName:"dbcColKorNm",   Align:"Left", Edit:0},
                    {Type:"Text",     Width:200,  SaveName:"vrfcId",        Align:"Left", Edit:0, Hidden:1},  
                    {Type:"Text",     Width:200,  SaveName:"vrfcNm",        Align:"Left", Edit:0},  
                    {Type:"Text",     Width:200,  SaveName:"cdClsId",       Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",     Width:200,  SaveName:"shdJobId",      Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",     Width:200,  SaveName:"shdKndCd",      Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",     Width:200,  SaveName:"etcJobNm",      Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",     Width:200,  SaveName:"shdJobNm",      Align:"Left", Edit:0, Hidden:1},
                   
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);

           
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
        	/* if($('#dbConnTrgId').val()=="" || $('#dbSchId').val()==""){
        		showMsgBox("ERR", "<s:message code="VALID.PRFREQUIRED" />", '');
        		break;
        	} */
        		
        	var param = $('#frmSearch').serialize();
        	grid_sheet.DoSearch('<c:url value="/dq/criinfo/anatrg/getItemAnaExec.do" />', param);
        	break;
       
        case "Exec":

        	//TODO 공통으로 처리...
        	var SaveJson = grid_sheet.GetSaveJson(0); 
        	
        	if(SaveJson.data.length == 0) return;
        	
            var url = "<c:url value="/dq/criinfo/anatrg/execItmAna.do"/>";
            
        	var param = "";

        	IBSpostJson2(url, SaveJson, param, ibscallback);
        	
        	break;
            
        
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
        case "Load2Excel":  //엑셀올리기
        	grid_sheet.LoadExcel({Mode:'HeaderMatch', Append:1});
            break;    
    }       
}



/*======================================================================*/
//IBSpostJson2 후처리 함수
/*======================================================================*/
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	if ('<%=WiseMetaConfig.RqstAction.APPROVE%>' != res.action) {
		showMsgBox("INF", res.RESULT.MESSAGE);
	}
	switch(res.action) {
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
				//doAction("Search");    		
			break;
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}	
}



function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	
	
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
	    <div class="menu_title"><s:message code="DIAG.TRGT.DB.MS.INQ" /></div><!--진단대상DBMS 조회-->

	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div><!--검색조건-->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='DIAG.TRGT.DBMS.INQ.2'/>"><!--진단대상DBMS조회-->

                   <caption><s:message code="DIAG.TRGT.DBMS"/></caption><!--진단대상DBMS-->

                   <colgroup>
                   <col style="width:10%;" />
                   <col style="width:20%;" />
                   <col style="width:10%;" />
                   <col style="width:20%;" />
                   <col style="width:10%;" />
                   <col style="width:*;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row"><label for="dbConnTrgId"><s:message code="DIAG.TRGT.DBMS.NM"/></label></th><!--진단대상DBMS명-->
                           <td>
                              <select id="dbConnTrgId"  name="dbConnTrgId">
								    <option value=""><s:message code="WHL" /></option><!--전체-->
								</select>
								<select id="dbSchId" class="" name="dbSchId">
					             	<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
					             </select>
                           </td>
                           
                           <th scope="row"><label for="vrfcTyp">검증분류</label></th>
                           <td>
                           		<select id="vrfcTyp" name="vrfcTyp" >
                           		   <option value="">전체</option> 
                         		   <c:forEach var="code" items="${codeMap.vrfcTyp}" varStatus="status" >
                                   <option value="${code.codeCd}">${code.codeLnm}</option>
                                   </c:forEach>
                                   <option value="PT01">참조무결성</option>
                                   <option value="QB">업무규칙</option>
                           		</select>
                           </td>                 
                           
                           <th scope="row"><label for="dbcTblNm">테이블명</label></th>
                           <td>
                           		<input type="text" name="dbcTblNm" id="dbcTblNm" />
                           </td>                                                      
                       </tr>
                        <tr>
                           <th scope="row"><label for="dbcColNm">컬럼명</label></th>
                           <td>
                           		<input type="text" name="dbcColNm" id="dbcColNm" />
                           </td>
                           
                           <th scope="row"><label for="dbcColKorNm">컬럼한글명</label></th>
                           <td colspan="3">
                           		<input type="text" name="dbcColKorNm" id="dbcColKorNm" />
                           </td>                                                                                                                             
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
<%--         <div class="tb_comment"><s:message  code='ETC.COMM' /></div> --%>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
		<div class="divLstBtn" style="display: none;">	 
            <div class="bt03">
			    <button class="btn_search" id="btnSearch" 	name="btnSearch"><s:message code="INQ"/></button> <!-- 조회 -->
			    <button class="btn_save"   id="btnExec" 	name="btnExec">진단항목실행</button> <!-- 저장 -->
			    <button class="btn_search" id="btnLogPop" name="btnLogPop">LOG</button> <!-- 로그팝업 조회 -->                 			    
			</div>
			<div class="bt02"> 
 	          <button class="btn_excel_down" id="btnExcelLoad" name="btnExcelLoad">엑셀 업로드</button>                       
	          <button class="btn_excel_down" id="btnExcelDown" name="btnExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                       
	    	</div>
        </div>	
         <!-- 조회버튼영역  -->

</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "250px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

	
</body>
</html>