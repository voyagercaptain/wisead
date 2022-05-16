<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title><s:message code="USER.INQ2" /></title> <!-- 사용자 검색 -->

<script type="text/javascript">

var interval = "";
// var usergJson = ${codeMap.userglist} ;	//시스템영역 코드 리스트 JSON...

$(document).ready(function() {
	
// 		alert("document.ready");
	
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
		
		//버튼 초기화...
		$("#btnTreeNew, #btnSave, #btnDelete").hide();
                    
        //그리드 초기화 
//         initGrid();
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                      
        // 추가 Event Bind
        $("#btnNew").click(function(){ doAction("New");  }); 

        // 저장 Event Bind
         $("#btnSave").click(function(){ doAction("Save");  }); 

        // 삭제 Event Bind
         $("#btnDelete").click(function(){ doAction("Delete");  }); 
        
         // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

        // 엑셀업로 Event Bind
        $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } );  
       
        //======================================================
        // 셀렉트 박스 초기화
        //======================================================
        // 시스템영역
//         create_selectbox(usergJson, $("#usergId"));
        
        //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
        $("div.pop_tit_close").click(function(){
        	
        	//iframe 형태의 팝업일 경우
        	if ("${search.popType}" == "I") {
        		parent.closeLayerPop();
        	} else {
        		window.close();
        	}
        	
        });
      //파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
        setautoComplete($("#frmSearch #userNm"), "USRNM");
    }
);

$(window).load(function() {
// 	alert('window.load');
	initGrid();
	$(window).resize();
});

EnterkeyProcess("Search");

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
        
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.USERSEARCHPOP'/>", Align:"Center"}
                    /* No.|상태|선택|사용자ID|사용자|부서명|사용자그룹|직급 */
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                      {Type:"Seq",    Width:70,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                   	  {Type:"Status", Width:30,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:0},
                   	  {Type:"CheckBox", Width:40,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},              
                   	  {Type:"Text",   Width:60,  SaveName:"userId",    Align:"Center", Edit:0, Hidden:0}, 
                      {Type:"Text",   Width:120,  SaveName:"userNm",    Align:"Center", Edit:0, KeyField:0 }, 
                   	  {Type:"Text",   Width:150,  SaveName:"deptPath",    Align:"Left", Edit:0, KeyField:0},          
                 //	  {Type:"Text",   Width:130,  SaveName:"usergLnm",    Align:"Center", Edit:0, Hidden:0},
                      {Type:"Combo",   Width:100,  SaveName:"usergId",    Align:"Left", Edit:0, KeyField:0},
                      {Type:"Text",   Width:60,  SaveName:"jgdNm",    Align:"Center", Edit:0, KeyField:0 }, 
                 //   {Type:"Text",   Width:50,  SaveName:"objDescn",     Align:"Left", 	 Edit:1},
                 //   {Type:"Text",   Width:50,  SaveName:"objVers",      Align:"Left",   Edit:0, Hidden:0},
                 //   {Type:"Combo",  Width:50,  SaveName:"regTypCd",     Align:"Center", Edit:0, ComboCode:"C|U|D", ComboText:"신규|변경|삭제",},                        
                 //   {Type:"Date",   Width:60,  SaveName:"writDtm",  	 Align:"Center", Edit:0, Format:"yyyy-MM-dd"},
                 //   {Type:"Text",   Width:60,  SaveName:"writUserId",   Align:"Center", Edit:0, Hidden:0},
                 //   {Type:"Text",   Width:60,  SaveName:"writUserNm",   Align:"Center", Edit:0}
                ];
                    
        InitColumns(cols);
	     
        //콤보 목록 설정...
	   	SetColProperty("usergId", ${codeMap.usergp});

        SetColHidden("ibsStatus",1);
        SetColHidden("ibsCheck",1);
        //SetColHidden("arrUsrId",1);
      
        FitColWidth();  
        
        //SetExtendLastCol(1);    
        grid_sheet.DoSearch('<c:url value="/commons/user/getuserlistbydept.do" />');
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
}

function doAction(sAction)
{

    switch(sAction)
    {		    
        
     	case "New":        //추가
        	//첫행에 추가...
        	grid_sheet.DataInsert(0);
        	//마지막 행에 추가..
        	//grid_sheet.DataInsert(-1);
        
            //var url = "<c:url value="/cmvw/user/user_lst.do" />";
        
            //$("#frmInput").attr("action", url).submit();
                        
            break;
            
        case "Delete" :
        	//체크박스 확인...
        	if(!grid_sheet.CheckedRows("ibsCheck")) {
        		showMsgBox("ERR", "<s:message code="ERR.CHKDEL" />");
        	}
        	
        	//TODO : 입력상태인 경우 삭제하자...
        	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        	var url = "<c:url value="/cmvw/user/userDellist.do"/>";
        	$.postJSON(url, DelJson, ibscallback);
        	break;
        	
        case "Save" :
           	//TODO 공통으로 처리...
        	var rows = grid_sheet.FindStatusRow("I|U|D");
        	if(!rows) {
//         		alert("저장할 대상이 없습니다...");
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	ibsSaveJson = grid_sheet.GetSaveJson(0);
//         	ibsSaveJson = grid_sheet.GetSaveJson(1);
        	//데이터 사이즈 확인...
        	if(ibsSaveJson.data.length == 0) return;
        	
            var url = "<c:url value="/cmvw/user/userReglist.do"/>";
//         	var param = "commDcdNm=test";
            IBSpostJson(url, param, ibscallback);
        	break; 
            
        case "Search":
        	var param = $('#frmSearch').serialize();
        	//alert(param);
        	grid_sheet.DoSearch('<c:url value="/commons/user/getuserlistbydept.do" />', param);
        	
        	break;
       
         case "Down2Excel":  //엑셀내려받기
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
/*        case "LoadExcel":  //엑셀업로
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});         
            break; */
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
 	
	var retjson = grid_sheet.GetRowJson(row);
 	
	//iframe 형태의 팝업일 경우
	if ("${search.popType}" == "I") {
 	
		parent.returnUserInfoPop(JSON.stringify(retjson));
 	
// 		parent.closeLayerPop();
	} else {
		opener.returnUserInfoPop(JSON.stringify(retjson));
		window.close();
	}
	
	//팝업창 닫기 버튼 클릭....
	$(".pop_tit_close").click();
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
    if(row < 1) return;
    
//     $("#sltGb").val(    grid_sheet.GetCellValue(row,"arrRqstDcd"));
//     $("#stwLnm").val(   grid_sheet.GetCellValue(row,"arrStwLnm"));
//     $("#stwPnm").val(   grid_sheet.GetCellValue(row,"arrStwPnm"));
//     $("#stwEfn").val(   grid_sheet.GetCellValue(row,"arrStwEfn"));
//     $("#cchNm").val(    grid_sheet.GetCellValue(row,"arrCchNm"));
//     $("#objDescn").val( grid_sheet.GetCellValue(row,"arrObjDescn"));
    
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

<div class="pop_tit" > <!-- 팝업가로사이즈 여기서 조절하면 됩니다 기본은 100% -->
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="USER.INQ2" /></div> <!-- 사용자 검색 -->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
	  <div class="pop_content">


<!-- 메뉴 메인 제목 -->
<%-- <div style="clear:both; height:5px;"><span></span></div> --%>

<!-- 검색조건 입력폼 -->
<div id="search_div"> 
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='USER.INQ' />"> <!-- 사용자조회 -->
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                  </colgroup>
                   
                   <tbody>                          
                     	<th scope="row"><label for="userNm"><s:message code="USER.NM" /></label></th> <!-- 사용자명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="userNm" id="userNm" />
                                </span>
                            </td>  

                         <th scope="row"><label for="usergId"><s:message code="USER.GRP.NM" /></label></th> <!-- 사용자그룹명 -->
                             <td>
                             	<select id="usergId" class="usergId" name="usergId">
                                      <option selected="" value="">----<s:message code="WHL" />----</option> <!-- 전체 -->
                                      <c:forEach var="code" items="${codeMap.userglist }" varStatus="status" >
                                      <option value="${code.codeCd }">${code.codeLnm}</option>
                                      </c:forEach>
                                  </select>
                            </td>   
                   </tbody>
                 </table>   
            
            </fieldset>
           </form>         
        <div class="tb_comment"><s:message  code='ETC.POP' /></div>
         <!-- 조회버튼영역  -->
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonTree.jsp" />

</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "400px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->
    </div>
    <!-- 팝업 내용 끝 -->
</div>
</body>
</html>