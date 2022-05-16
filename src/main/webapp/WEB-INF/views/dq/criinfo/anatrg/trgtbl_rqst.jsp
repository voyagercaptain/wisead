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
//엔터키 이벤트 처리
EnterkeyProcess("Search");
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
	$("#btnSave").click( function(){ doAction("Save"); }).show();
	
	//삭제버튼 hidden
	$("#btnDelete").hide();
	//tree 추가 버튼 hidden
	$("#btnTreeNew").hide();
	//상세 페이지
	loadDetail();
	
	/* double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
		double_select(connTrgSchJson, $(this));
	}); */
	
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
	
	doAction("Search");
	
});

$(window).load(function() {

});

EnterkeyProcess("Search");

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
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);

        //No.|상태|선택|DB접속대상ID|만료일시|시작일시|DB접속대상물리명|DB접속대상논리명|DBMS종류|DBMS버전|접속대상DB연결문자열|접속대상연결URL|접속대상드라이버명|DB접속계정ID|DB접속계정비밀번호|DB접속상태|담당자명|담당자연락처|객체설명|객체버전|등록유형코드|작성일시|작성사용자ID
        
        var headerText = "No.|상태|제외대상|DB_SCH_ID|DBMS명|스키마명|테이블명|테이블한글명|추가조건|제외사유";
        
        var headers = [
                    {Text: headerText, Align:"Center"}
                ];
            
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",      Width:30,   SaveName:"ibsSeq",        Align:"Center", Edit:0},
                    {Type:"Status",   Width:30,   SaveName:"ibsStatus",     Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:80,  SaveName:"expYn",         Align:"Center", Edit:1, Sort:0},                    
                    {Type:"Text",     Width:100,  SaveName:"dbSchId",    	Align:"Left", Edit:0, Hidden:1},                   
                    {Type:"Text",     Width:150,  SaveName:"dbConnTrgPnm",  Align:"Left", Edit:0},
                    {Type:"Text",     Width:180,  SaveName:"dbSchPnm",      Align:"Left", Edit:0},
                    {Type:"Text",     Width:180,  SaveName:"dbcTblNm",    	Align:"Left", Edit:0}, 
                    {Type:"Text",     Width:180,  SaveName:"dbcTblKorNm",   Align:"Left", Edit:0},
                    {Type:"Text",     Width:200,  SaveName:"addCnd",   		Align:"Left", Edit:1},
                    {Type:"Text",     Width:200,  SaveName:"expRsnCntn",    Align:"Left", Edit:1} 
                   
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);

        /*
	     //콤보 목록 설정...
	    SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
	    SetColProperty("dbmsTypCd", 	${codeMap.dbmsTypCdibs});
	    SetColProperty("dbmsVersCd", 	${codeMap.dbmsVersCdibs});
        InitComboNoMatchText(1, "");
        */
//         SetColHidden("ibsStatus",1);
//         SetColHidden("ibsCheck",1);
//         SetColHidden("bizAreaId",1);
//         SetColHidden("objVers",1);
//         SetColHidden("regTypCd",1);
//         SetColHidden("rqstDtm",1);
//         SetColHidden("rqstUserId",1);
//         SetColHidden("rqstUserNm",1);
      
        //FitColWidth();
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
        	grid_sheet.DoSearch('<c:url value="/dq/criinfo/anatrg/getTrgTbl.do" />', param);
        	break;
       
        case "Save":

        	//TODO 공통으로 처리...
        	var SaveJson = grid_sheet.GetSaveJson(0); 
        	
        	if(SaveJson.data.length == 0) return;
        	
        	for(var i = 1; i <= grid_sheet.GetDataLastRow(); i++){
        		if(grid_sheet.GetCellValue(i,'expYn') == '1' && grid_sheet.GetCellValue(i,'expRsnCntn') == ''){
        			showMsgBox("ERR", "제외사유를 입력하세요");
        			return;
        		} 
        	}
        	
        	
            var url = "<c:url value="/dq/criinfo/anatrg/regTrgTbl.do"/>";
        	var param = "";
        	IBSpostJson2(url, SaveJson, param, ibscallback);
        	
        	break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
    }       
}
 
//상세정보호출
function loadDetail(param) {
	$('div#detailInfo').load('<c:url value="/dq/criinfo/ajaxgrid/selectAnaTrgDbmsDetail.do"/>', param, function(){
		//$('#tabs').show();
	});
}

//이력조회
function getTrgDbmsHstLst(param) {
	grid_hst.DoSearch("<c:url value="/dq/criinfo/ajaxgrid/getTrgDbmsHstLst.do" />", param);
}

function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	//그리드 선택 데이터 변수 setting
	var param =  grid_sheet.GetRowJson(row);
	var dbConnTrgId = "&dbConnTrgId="+grid_sheet.GetCellValue(row, "dbConnTrgId");
	//caption 
	var tmphtml = ' <s:message code="DIAG.TRGT.DBMS.NM"/>'+ ' : ' + param.dbConnTrgLnm ; //진단대상DBMS명


	$('#anatrg_sel_title').html(tmphtml);
	
	//상세조회
	/* loadDetail(dbConnTrgId); */
	
	//이력조회
	/* getTrgDbmsHstLst(dbConnTrgId); */
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
				doAction("Search");    		
			break;
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
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
                   <col style="width:25%;" />
                   <col style="width:10%;" />
                   <col style="width:25%;" />
                   <col style="width:10%;" />
                   <col style="width:25%;" />
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
                           <th scope="row"><label for="dbcTblNm">테이블명</label></th>
                           <td>
                           		<input type="text" name="dbcTblNm" id="dbcTblNm" class="wd98p"/>
                           </td>
                           <th scope="row"><label for="expYn"><s:message code="EXP.YN"/></label></th><!--제외대상여부-->
                           <td>
                              <select id="expYn"  name="expYn">
								    <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
								    <option value="Y">Y</option>
								    <option value="N">N</option>
								</select>
                           </td>
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
        <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
        <div class="tb_comment">- 테이블을 분석대상에서 제외하려면 제외대상을 체크, 제외사유를 입력하신 후 저장하시면 됩니다.</div>
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

	
</body>
</html>