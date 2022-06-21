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
<title><s:message code="USER.MNG" /></title> <!-- 사용자관리 -->

<script type="text/javascript">


//엔터키 이벤트 처리
EnterkeyProcess("Search");

var interval = "";
// var usergJson = ${codeMap.usergroup} ;	//시스템영역 코드 리스트 JSON...

$(document).ready(function() {
		$("#poiDown").hide();
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                      
        // 추가 Event Bind
        $("#btnNew").click(function(){ doAction("New");  });

        // 저장 Event Bind
        $("#btnSave").click(function(){
        	//var rows = grid_sheet.FindStatusRow("I|U|D");
        	var rows = grid_sheet.IsDataModified();
        	if(!rows) {
//         		alert("저장할 대상이 없습니다...");
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}		
        	var dbNames = '';
        	
        	for(var i = 1; i <= grid_sub.RowCount(); i++) {
        		 
        		var status =  grid_sub.GetCellValue(i,"ibsStatus");
        		 
        		if(status == 'U') {
        			dbNames = dbNames + grid_sub.GetCellValue(i,"dbNm") + ","; 
        		}
        	}
        	
        	grid_sheet.SetCellValue(grid_sheet.GetSelectRow(), "dbName", dbNames);
        	
        	//저장할래요? 확인창...
    		var message = "<s:message code="CNF.SAVE" />";
    		showMsgBox("CNF", message, 'Save');	
        	//doAction("Save"); 	
		}).show();

        // 삭제 Event Bind
        $("#btnDelete").click(function(){ 

        	//선택체크박스 확인 : 삭제할 대상이 없습니다..
    		if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
    			//삭제 확인 메시지
    			//alert("삭제하시겠어요?");
    			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
//             	showMsgBox("CNF", "<s:message code='MSG.CHC.TBL.CLMN.DEL.DEL' />", "Delete"); /* 선택한 테이블에 속한 컬럼도 삭제됩니다.<br>삭제 하시겠습니까? */
        	}
        //	doAction("Delete");  
        });
        
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

        // 엑셀업로 Event Bind
         $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ); 
       
        //======================================================
        // 셀렉트 박스 초기화
        //======================================================
        // 시스템영역
//         create_selectbox(usergJson, $("#usergId"));
        
         doAction("Search");
    }
);

/*
$(window).load(function() {
// 	alert('window.load');
	initGrid();
});
*/

$(document).ready(function(){
	//그리드 초기화
	initGrid();
});


$(window).resize(
    
    function(){
    	grid_sheet.FitColWidth();   		     
    	//setibsheight($("#grid_01"));
    }
);


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100,UseHeaderSortCancel:1};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.USER.LST'/>", Align:"Center"}
                    /* No.|업무대행|상태|선택|사용자ID|만료일시|시작일시|로그인ID|사용자명|사용자그룹명|부서ID|부서명|직급명|사용자전화번호|사용자휴대폰번호|이메일주소|엑셀다운로드권한여부|ID사용만료일자|비밀번호만료일자|설명|버전|등록유형|요청일시|요청사용자ID|승인구분코드|작성일시|승인사용자ID|비밀번호변경 */
                ];
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",      Width:60,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Text",     Width:100,  SaveName:"gologin",  Image:containerPath+"/images/login_go.gif" ,ImgAlign:"Left", Align:"Center", Edit:0, Hidden:1},
                    {Type:"Status",   Width:60,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"CheckBox", Width:80,   SaveName:"ibsCheck",     Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Text",     Width:130,  SaveName:"userId",       Align:"Left", Edit:0, Hidden:1},
                    {Type:"Date",     Width:130,  SaveName:"expDtm",       Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Date",     Width:130,  SaveName:"strDtm",       Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",     Width:130,  SaveName:"loginAcId",    Align:"Left", Edit:1, Hidden:0, KeyField:1},
                    {Type:"Text",     Width:130,  SaveName:"userNm",       Align:"Left", Edit:1, KeyField:1}, 
                    {Type:"Combo",    Width:130,  SaveName:"usergId",      Align:"Left", Edit:1, KeyField:1},
                    {Type:"Popup",    Width:130,  SaveName:"deptId",       Align:"Left", Edit:0, Hidden:1},
                    {Type:"Popup",    Width:130,  SaveName:"deptNm",       Align:"Left", Edit:1, Hidden:1},          
                    {Type:"Text",     Width:120,  SaveName:"jgdNm",        Align:"Left", 	 Edit:1},
                    {Type:"Text",     Width:120,  SaveName:"dbName",        Align:"Left", 	 Edit:1},
                    {Type:"Combo",     Width:120,  SaveName:"orgCd",        Align:"Left", 	 Edit:1, KeyField:1},
                    {Type:"Combo",     Width:120,  SaveName:"orgNm",        Align:"Left", 	 Hidden:1},
                    {Type:"Text",     Width:130,  SaveName:"userTelno",    Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",     Width:130,  SaveName:"userHtelno",   Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",     Width:130,  SaveName:"emailAddr",      Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Combo",    Width:130,  SaveName:"exclDwldAuthYn",      Align:"Center",   Edit:1, Hidden:1},
                    {Type:"Text",     Width:130,  SaveName:"idUseExpDt",      Align:"Center",   Edit:1, Hidden:1},
                    {Type:"Text",     Width:130,  SaveName:"pwdExpDt",      Align:"Center",   Edit:1, Hidden:1},
                    {Type:"Text",     Width:230,  SaveName:"objDescn",      Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",     Width:130,  SaveName:"objVers",      Align:"Right",   Edit:0, Hidden:1},
                    {Type:"Combo",    Width:80,   SaveName:"regTypCd",      Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:130,  SaveName:"rqstDtm",      Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:130,  SaveName:"rqstUserId",      Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Combo",    Width:130,  SaveName:"aprvDcd",      Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:130,  SaveName:"aprvDtm",      Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:130,  SaveName:"aprvUserId",      Align:"Center",   Edit:0, Hidden:1},
                    {Type:"html",     Width:100,  SaveName:"pwChg",  Align:"Center", Edit:0, KeyField:0, Hidden:0}
                   
                ];
                    
        InitColumns(cols);
	     
        //콤보 목록 설정...
	   	SetColProperty("usergId", ${codeMap.usergp});
        SetColProperty("exclDwldAuthYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        SetColProperty("regTypCd", ${codeMap.regTypCdibs});
        SetColProperty("orgCd", ${codeMap.orgCd});
        //SetColProperty("deptId", 	{ComboCode:"부서1|부서2|부서3|부서4|부서5|부서6", 	ComboText:"부서1|부서2|부서3|부서4|부서5|부서6"});   

        InitComboNoMatchText(1, "");

        //히든 컬럼 설정...
        //SetColHidden("writUserNm",1);
        //SetColHidden("arrUsrId",1);
      
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
        
    	case "New":        //추가
        	//첫행에 추가...
        	grid_sheet.DataInsert(0);
        	//마지막 행에 추가..
        	//grid_sheet.DataInsert(-1);

            //var url = "<c:url value="/cmvw/user/user_lst.do" />";

            //$("#frmInput").attr("action", url).submit();
                        
            break;
            
        case "Delete" :
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	
        	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        	var url = '<c:url value="/commons/user/userDellist.do"/>';
        	var param = "";
        	IBSpostJson2(url, DelJson, param, ibscallback);
        	break;
        	
        case "Save" :
        	//TODO 공통으로 처리...
        	var SaveJson = grid_sheet.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
//         	ibsSaveJson = grid_sheet.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...
			
			//alert("SaveJson.data.length:" + SaveJson.data.length);

        	//데이터 사이즈 확인...
        	if(SaveJson.data.length == 0) return;
        	
        	// main grid선택시 설정된 hidden orgCd 값 초기화
        	// 저장 후 조회시 전체 조회하기 위해서 초기화 필요
        	$("#frmSearch #orgCd").val('');
        	
        	var url = "<c:url value="/commons/user/userReglist.do"/>";
         	var param = "";
            IBSpostJson2(url, SaveJson, param, ibscallback);
        	break;
            
        case "Search":
        	var param = $('#frmSearch').serialize();
        	//alert(param);
        	grid_sheet.DoSearch('<c:url value="/commons/user/userSelectlist.do" />', param);
        	
        	//var len = grid_sheet.RowCount();
            //alert("grid_sheet.RowCount():" + len);
    		//for(var i = 0; i < len; i++) {
    		//	grid_sheet.SetCellValue(i+1,"ibsStatus", "I");
            //    alert(grid_sheet.GetCellValue(i+1,"ibsStatus"));
    		//}
        	
        	break;
       
        case "SearchDB":
            if ($('#frmSearch #orgCd').val() == null || $('#frmSearch #orgCd').val() == '') {
            	showMsgBox("ERR", "<s:message code='MSG.INQ.USER.GRP.CHC' />"); /* 조회할 사용자그룹을 먼저 선택하세요. */
            	return;
            }
            var param = $('#frmSearch').serialize();
            	
           	grid_sub.DoSearch('<c:url value="/commons/user/dbSelectList.do" />', param);
            	
           	break;
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            
            break;
        case "LoadExcel":  //엑셀업로
        
          
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            
            break;
    }       
}
 
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	//alert(res.action);
	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
				doAction("Search");
				//doActionCol("Search");
		
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			

			
			break;
		//요청서 여러건 등록 후처리...
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
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	if(grid_sheet.GetColEditable(col)) return;
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	
	//선택한 그리드의 row 내용을 보여준다.....
	// TODO: 메시지 추가
	// var tmphtml = '<s:message code="USER.GRP.NM" /> : ' + param.usergLnm; /* 사용자기관명 */
	var tmphtml = '사용자기관명 : ' + grid_sheet.GetCellText(row, "orgCd"); /* 사용자기관명 */
	$('#userg_sel_title').html(tmphtml);
	
	$("#frmSearch #orgCd").val(param.orgCd);
	
	//메뉴ID를 토대로 조회한다....
	doAction("SearchDB");
	
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {

    $("#hdnRow").val(row);
    
	if ("gologin" == grid_sheet.ColSaveName(col)) {
		var rowjson = grid_sheet.GetRowJson(row);
		var param = "?loginAcId="+rowjson.loginAcId;
		var url = '/wiseda/commons/user/userloginbyadmin.do';
		window.open(url+param, "_blank");
	}
	
	if ("pwChg" == grid_sheet.ColSaveName(col)) {
		/* var url = '<c:url value="/commons/user/gousrInfoChngPop.do"/>';
		var param = grid_sheet.GetRowJson(row);
		var payloadString = Object.entries(param).map(e => e.join('=')).join('&');
		payloadString = payloadString+"&"+"loginAcId="+payloadString.loginAcId; */
		
		var param = grid_sheet.GetRowJson(row);
		var payloadString = "loginAcId=" + param.loginAcId;
		
		openLayerPop("<c:url value='/commons/user/gousrInfoChngPop.do' />", 550, 290, payloadString);
		
		//openLayerPop(url, 700,  300, payloadString);
		//OpenWindow(url+'?'+param,'passwordChange','925','550','yes');
		return false;
	}
	
}

// 그리드 콤보 변경 이벤트
function grid_sheet_OnChange(Row, Col, Value, OldValue, RaiseFlag) { 

	var usergId = "${sessionScope.loginVO.usergId}";
	var orgCd = "${sessionScope.loginVO.orgCd}";
	
	if (RaiseFlag == 1)
		return;
	
	if ("usergId" == grid_sheet.ColSaveName(Col)) {
		
		if ("I" == grid_sheet.GetCellValue(1,"ibsStatus")) {
			if (usergId == "OBJ_00000034586") {
				if ("2" == Value) {
					grid_sheet.SetCellValue(Row, Col, OldValue);
					alert("사용자 그룹 정보를 변경할 수 없습니다.");
					return;
				}
			}
		}
		else {
			if (usergId == "OBJ_00000034586") {
				if ("2" == Value || "OBJ_00000034586" == Value) {
					grid_sheet.SetCellValue(Row, Col, OldValue);
					alert("사용자 그룹 정보를 변경할 수 없습니다.");
					return;
				}
			}
		}
	}
	
	// 관리자 체크
	// 기관 담당자가 관리자의 권한 그룹을 를 변경할 경우
	// 2 : 관리자(총괄)
	// OBJ_00000034586 : 기관담당자
	// OBJ_00000034587 : DB담당자
	if (usergId == 'OBJ_00000034586' || usergId == 'OBJ_00000034587') {
		// 총괄 관리자를 변경하려 하면
		if ("2" == OldValue) {
			grid_sheet.SetCellValue(Row, Col, OldValue);
			alert("관리자의 정보를 변경할 수 없습니다.");
			return;
		}
	}
	
	if ("usergId" == grid_sheet.ColSaveName(Col)) {
		var usergVal = grid_sheet.GetCellValue(Row, "usergId");
		
		// 관리자로 변경하면 관리자 항목이 바로 설정
		if ("2" == usergVal) {
			grid_sheet.SetCellValue(Row, "orgCd", "관리자");
		}
	}
	
	// 로그인 관리자가 권한이 없는데 변경할 경우 알림
	if ("orgCd" == grid_sheet.ColSaveName(Col)) {
		var orgText = grid_sheet.GetCellText(Row, "orgCd");
		
		if (usergId == 'OBJ_00000034586' || usergId == 'OBJ_00000034587') {
			if (orgCd != orgText) {
				grid_sheet.SetCellValue(Row, "orgCd", OldValue);
				alert("다른 기관으로 변경할 수 없습니다.");
				return;
			}
		}
	}
	
	// 기관명 관리자는 관리자만 변경 가능
	if ("orgCd" == grid_sheet.ColSaveName(Col)) {
		
		var orgVal = grid_sheet.GetCellValue(Row, "orgCd");
		var usergVal = grid_sheet.GetCellValue(Row, "usergId");
		
		if ("ORG_00000" == orgVal) {
			if ("2" != usergVal) {
				grid_sheet.SetCellValue(Row, "orgCd", OldValue);
				alert("관리자가 아니면 관리자 기관명을 선택할 수 없습니다.");
				return;
			}
		}
	}
	
}
		
//주제영역 팝업 리턴값 처리
function returnSubjPop (ret, row) {
// 	alert(ret);
	
	var retjson = jQuery.parseJSON(ret);
	
	grid_sheet.SetCellValue(row, "deptId", retjson.deptId);
	grid_sheet.SetCellValue(row, "deptNm", retjson.deptNm);
// 	$("#frmSearch #subjLnm").val(retjson.subjLnm);
// 	$("#frmSearch #fullPath").val(retjson.fullPath);
	
}

function grid_sheet_OnPopupClick(Row,Col) {
	
	//Format이 날짜인 경우 달력 팝업을 오픈한다.
// 	grid_sheet.ShowCalendar();
	var param = "row=" +Row;
	//사용자 검색 팝업 오픈
	if ("deptNm" == grid_sheet.ColSaveName(Col)) {
		var url = '<c:url value="/commons/user/popup/deptlst_pop.do" />';
		openLayerPop(url, 700, 500, param);
	}
}

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	//alert(grid_sheet.GetDataBackColor()+":"+ grid_sheet.GetDataAlternateBackColor());
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		setGridCellAll(grid_sheet,"pwChg", "<a class='BK_btn_grid2' ><s:message code='PWD.CHG' /></a>" ); /* 비밀번호변경 */
	}

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
	    <div class="menu_title"><s:message code="USER.REG" /></div> <!-- 사용자 등록 -->
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
                                <td><select id="usergId" class="usergId" name="usergId">
                                        <option selected="" value="">---<s:message code="WHL" />----</option> <!-- 전체 -->
                                        <c:forEach var="code" items="${codeMap.userglist }" varStatus="status" >
                                        <option value="${code.codeCd }">${code.codeLnm}</option>
                                        </c:forEach>
                                    </select>
                                    </td>   
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
            <input type="hidden" name="saveCls" id="saveCls"  />   
            <input type="hidden" name="usrId"   id="usrId" />
            <input type="hidden" name="orgCd"   id="orgCd" />
            <%-- <input type="hidden" name="hidUsergId" id="hidUsergId"  value="<c:out value='${sessionScope.loginVO.usergId}' escapeXml="true" />" />
            <input type="hidden" name="hidOrgCd" id="hidOrgCd"  value="<c:out value='${sessionScope.loginVO.orgCd}' escapeXml="true" />" /> --%>
            
            
        <div class="tb_comment">- <s:message code="MSG.DTL.INQ.WIT.ATA.COPY.CLMN.CHC" /> <span style="font-weight:bold; color:#444444;">Ctrl + C</span><s:message code="MSG.CHC.USE" /></div> 
        <!-- 클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고 --> <!-- 를 사용하시면 됩니다. -->
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
   
        <!-- 조회버튼영역  -->         
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />   
	<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "250px");</script>            

	</div>
	<!-- 그리드 입력 입력 -->

	<div style="clear:both; height:5px;"><span></span></div>
<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="userg_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1">접근 DB 권한</a></li> <!-- 메뉴권한 정보 -->
	  </ul>
	  <div id="tabs-1">
			<!-- 	상세정보 ajax 로드시 이용 -->
			<%@include file="user_dtl.jsp" %>
<!-- 			<div id="detailInfo"></div> -->
			<!-- 	상세정보 ajax 로드시 이용 END -->
	  </div>
	 </div>
	<!-- 선택 레코드의 내용을 탭처리 END -->
	
</body>
</html>