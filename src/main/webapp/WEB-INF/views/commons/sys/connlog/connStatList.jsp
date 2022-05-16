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
<title></title>
<!-- <link rel="stylesheet" type="text/css" href="css/design.css"> -->

<script type="text/javascript">
//최근 선택 row
var lastsel;

$(document).ready(function(){
	
	initgrid();
	
	//달력팝업 추가...
 	$( "#searchBgnDe" ).datepicker();
	$( "#searchEndDe" ).datepicker();
	
	
	//기간 버튼 클릭 체크
 	$(".bd_none a").click(function(){
		var btna = $(".bd_none a"); 
		var idx = btna.index(this);
		btna.removeClass('tb_bt_select').addClass('tb_bt');
		btna.eq(idx).removeClass('tb_bt').addClass('tb_bt_select');

		//alert(idx);
		setBetweenDtm( idx, $( "#searchBgnDe" ), $( "#searchEndDe" ));
		
	}); 
	
	//추가 버튼...
	$('button.btn_rqst_new').unbind('click').click(function() {
		doAction('Add');
	}).hide();
	
	//조회 이벤트 처리
	$("#btnSearch").click(function(){ 
		doAction('Search'); 
	});
	
	//추가 이벤트 처리
	$("#btnNew").click(function(){
		doAction('Add');
		//var su = $("#connstatgrid01").jqGrid('addRow');
// 		var su=$("#connstatgrid01").jqGrid('addRowData', 99, {});
// 		alert(su);
// 		$("#connstatgrid01").jqGrid();
// 		if(su) alert("Succes. Write custom code to add data in server"); else alert("Can not update");
	});

	//삭제버튼 이벤트 처리
	$("#btnDelete").click(function(){
		//선택체크박스 확인
		var sel = $("#connstatgrid01").jqGrid('getGridParam','selarrrow');
		if(isBlankStr(sel))	{
			//alert("삭제할 대상을 선택하세요");
			showMsgBox("ERR", "<s:message code="ERR.CHKDEL" />");
			return;
		}
		
		//삭제 확인 메시지
		//alert("삭제하시겠어요?");
		showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
	}).hide();
	
	//엑셀 다운로드 버튼
	$("#btnExcelDown").hide();
	$("#btnExcelDown1").click(function(e){
		//조회된 그리스 수 확인...
		var sel = $("#connstatgrid01").jqGrid('getGridParam','records');
		if(sel < 1)	{
			//alert("다운로드할 대상이 없습니다. 조회를 하세요.");
			showMsgBox("ERR", "<s:message code="ERR.EXCEL.DOWN" />");
			return;
		}
		
		doAction('ExcelDown');
	    //e.preventDefault();

// 		var postd = $("#connstatgrid01").jqGrid('getGridParam', 'postData');
		//$("#connstatgrid01").jqGrid('excelExport', {url : ''});
		//alert(postd);
	});
	$("#btnExcelDown2").click(function(e){
		//조회된 그리스 수 확인...
		var sel = $("#connstatgrid02").jqGrid('getGridParam','records');
		if(sel < 1)	{
			//alert("다운로드할 대상이 없습니다. 조회를 하세요.");
			showMsgBox("ERR", "<s:message code="ERR.EXCEL.DOWN" />");
			return;
		}
		
		doAction('ExcelDown2');
	});
	
	

	
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
	$("#connstatgrid01").jqGrid('setGridWidth', $("#grid_01").width());        
	$("#connstatgrid02").jqGrid('setGridWidth', $("#grid_02").width());        
});


function initgrid() {

	$("#connstatgrid01").jqGrid({
//	    	url:'<c:url value="/js/jqgrid/test.json" />',
//	 	mtype:"POST",
		datatype: "json",
//	    	colNames:['<input type="checkbox" onclick="checkBox(event)" />', '프로그램파일명', '프로그램경로', '프로그램명', 'URL', '프로그램설명'],
	   	colNames:[<s:message code="COMMON.HEADER.COONNSTATLIST1"/>], /* '접속일자', '접속자', '접속ID',  '접속통계' */
	   	colModel:[
//	    		{name:'chkbox',index:'chkbox', width:55, sortable:false, edittype:'checkbox', editoptions: { value:"Yes:No" }, editable:true},
	   		{name:'creatDt', index:'creatDt' , width:55,  sorttype:'text', editable:true, align:"center", summaryType:'count', summaryTpl : '소계 ({0})'},
	   		{name:'loginNm'  , index:'loginNm', width:55, sorttype:'text', editable:true},
	   		{name:'loginId'  , index:'loginId', width:55, sorttype:'text', editable:true},
	   		{name:'statsCo'  , index:'statsCo', width:90, sorttype:'int', editable:true, align:"right",formatter:"int", summaryType:'sum'},
	   	],
	    rowNum:100,
	   	rowList:[100,500,1000],
	   	pager: '#pager01',
//	    	pagerpos: 'center', 	//페이징 위치
//	    	pgbuttons: false, 		//페이징 버튼 유무
//	     	pginput: false, 			//페이징 인풋입력 유무
	   	rownumbers:true, 		//행 번호 표시 true/false, 전체 row의 줄번호(서버 데이터는 아니다..)를 보여줄까??
	   	rownumWidth:40, 		//줄번호 column의 사이즈..
//	 	emptyrecords: "조회결과가 없습니다.",
	   	sortname: 'creatDt',
	    viewrecords: true,
	    sortorder: "desc",
	    autowidth: true,
//	     width:$("#grid_01").width(),
// 	     height:200,
//	     caption:"JSON Example" 
		jsonReader: {
			repeatitems: false, 	//서버에서 받은 data와 Grid 상의 column 순서를 맞출것인지?
//	 		root:'rows', 			//서버의 결과 내용에서 데이터를 읽어오는 기준점
//	 		page:'page', 			// 현재 페이지 currentPage
//	 		total:'total', 			// 총 페이지 수 totalPage
//	 		records:'records', 		// 보여지는 데이터 갯수(레코드) totalRecord
			id:'' 		// 키값, 꼭 화면 전체에서 겹치는 값이 없는 column을 설정할 것, 안그러면 에러 발생
		}, 
		loadonce: true,
		scroll: true,
		sortable: true,
// 		multiselect: true,
// 		grouping:true,
// 	   	groupingView : {
// 	   		groupField : ['creatDt'],
// 	   		groupSummary : [false],
// 	   		groupColumnShow : [true],
// 	   		groupText : ['<b>{0}</b><div style="display: inline; float: right;">접속자수 : {creatDt}, 합계 : {statsCo}</div>'],
// 	   		groupCollapse : false,
// 			groupOrder: ['desc']
// 	   	},
	   	//footerrow: true,
// 	    userDataOnFooter: true,
	/* 	sortable: true,
	    loadComplete: function () {
	        var $self = $(this);
	        if ($self.jqGrid("getGridParam", "datatype") === "json") {
	            setTimeout(function () {
	                $(this).trigger("reloadGrid"); // Call to fix client-side sorting
	            }, 50);
	        }
	    } */
	/*     loadComplete : function () {
			   $("#connstatgrid01").jqGrid('setGridParam',{datatype:'local'});
		}, */
//	  	onPaging : function(which_button) {
//	 		   $("#connstatgrid01").jqGrid('setGridParam',{datatype:'json'});
//	 		   $("#btnSearch").click();
//	 	}
	   	onSelectRow: function(id){
	 		//alert("onSelectRow - " + id);
	 		//var id = jQuery("#list5").jqGrid('getGridParam','selrow');
	 		/*  if(id && id!==lastsel){
	 			var ret = $("#connstatgrid01").jqGrid('getRowData',id);
//	  			alert("id="+ret.id+" invdate="+ret.invdate+"...");
				loadDetail(ret);
				lastsel=id;
	 		} else {  
	 			//alert("Please select row");
	 		} */
//	  		return false;
	 		
	/* 		if(id && id!==lastsel){
				$('#connstatgrid01').jqGrid('saveRow', lastsel, false, 'clientArray');
				$('#connstatgrid01').jqGrid('editRow', id, true, null, null, 'clientArray');
				lastsel=id;
			} */
		}, 
		onCellSelect : function(id, icol, cont) {
//	 		alert("onCellSelect - " + id + ":" + icol + ":" + cont);
		}
		
		//editurl: ""
	});

	$("#connstatgrid02").jqGrid({
//	    	url:'<c:url value="/js/jqgrid/test.json" />',
//	 	mtype:"POST",
		datatype: "json",
//	    	colNames:['<input type="checkbox" onclick="checkBox(event)" />', '프로그램파일명', '프로그램경로', '프로그램명', 'URL', '프로그램설명'],
	   	colNames:[<s:message code="COMMON.HEADER.COONNSTATLIST2"/>], /* '접속일자', '접속자(수)', '접속통계(합계)' */
	   	colModel:[
//	    		{name:'chkbox',index:'chkbox', width:55, sortable:false, edittype:'checkbox', editoptions: { value:"Yes:No" }, editable:true},
	   		{name:'creatDt', index:'creatDt' , width:55,  sorttype:'text', editable:true, align:"center", summaryType:'count', summaryTpl : '소계 ({0})'},
	   		{name:'loginId'  , index:'loginId', width:55, sorttype:'text', editable:true, align:"right",formatter:"int", summaryType:'count'},
	   		{name:'statsCo'  , index:'statsCo', width:90, sorttype:'int', editable:true, align:"right",formatter:"int", summaryType:'sum'},
	   	],
	    rowNum:100,
	   	rowList:[100,500,1000],
	   	pager: '#pager02',
//	    	pagerpos: 'center', 	//페이징 위치
//	    	pgbuttons: false, 		//페이징 버튼 유무
//	     	pginput: false, 			//페이징 인풋입력 유무
	   	rownumbers:true, 		//행 번호 표시 true/false, 전체 row의 줄번호(서버 데이터는 아니다..)를 보여줄까??
	   	rownumWidth:40, 		//줄번호 column의 사이즈..
//	 	emptyrecords: "조회결과가 없습니다.",
	   	sortname: 'creatDt',
	    viewrecords: true,
	    sortorder: "desc",
	    autowidth: true,
//	     width:$("#grid_01").width(),
// 	     height:200,
//	     caption:"JSON Example" 
		jsonReader: {
			repeatitems: false, 	//서버에서 받은 data와 Grid 상의 column 순서를 맞출것인지?
//	 		root:'rows', 			//서버의 결과 내용에서 데이터를 읽어오는 기준점
//	 		page:'page', 			// 현재 페이지 currentPage
//	 		total:'total', 			// 총 페이지 수 totalPage
//	 		records:'records', 		// 보여지는 데이터 갯수(레코드) totalRecord
			id:'' 		// 키값, 꼭 화면 전체에서 겹치는 값이 없는 column을 설정할 것, 안그러면 에러 발생
		}, 
		loadonce: true,
		scroll: true,
		sortable: true
// 		multiselect: true,
// 		grouping:true,
// 	   	groupingView : {
// 	   		groupField : ['creatDt'],
// 	   		groupSummary : [false],
// 	   		groupColumnShow : [true],
// 	   		groupText : ['<b>{0}</b><div style="display: inline; float: right;">접속자수 : {creatDt}, 합계 : {statsCo}</div>'],
// 	   		groupCollapse : false,
// 			groupOrder: ['desc']
// 	   	},
	   	//footerrow: true,
// 	    userDataOnFooter: true,

	});
	// $("#connstatgrid01").jqGrid('navGrid','#pager01',{edit:false,add:false,del:false});
}

function doAction(action) {
	switch(action) {
	
	case 'Search' :	//조회
// 		var param = $('form[name=frmSearch]').serializeArray();
		var param = $('form[name=frmSearch]').serialize();
// 		alert(param);
// 		$("#connstatgrid01").jqGrid('clearGridData'); // 이전 데이터 삭제
		$("#connstatgrid01").jqGrid('setGridParam', {
			url:'<c:url value="/commons/sys/connlog/ajaxgrid/selectConnStatList.do" />'+'?'+param,
// 			postData: param,
			datatype:'json'
		}).trigger("reloadGrid");
		$("#connstatgrid02").jqGrid('setGridParam', {
			url:'<c:url value="/commons/sys/connlog/ajaxgrid/selectConnStatTot.do" />'+'?'+param,
// 			postData: param,
			datatype:'json'
		}).trigger("reloadGrid");
		break;
	case 'Detail' : //상세 정보
		//onSelectRow 그리드 함수에서 처리...
		break;
	case 'Add' : //신규 추가
		loadDetail();
		break;
	case 'SaveRow': //단건 저장
		//saction (I-입력, U-수정)
		var urls = '<c:url value="/commons/sys/program/ajaxgrid/saveProgram.do"/>';
		var param = $('form[name=frmInput]').serialize();
		ajax2Json(urls, param, aftersaveGrid);
		
		break;
	case 'Delete' : //삭제
		var sel = $("#connstatgrid01").jqGrid('getGridParam','selarrrow');
		//url 호출
		var urls = '<c:url value="/commons/sys/program/ajaxgrid/deleteProgram.do"/>';
		var param = "progrmFileNm="+sel;
		ajax2Json(urls, param, afterDelGrid);
		break;
	case 'ExcelDown' : //엑셀다운로드
		//그리드 내용을 가져온다.
		var tbody = $("#connstatgrid01").clone(); //.html();
		//그리드 헤더내용을 가져온다.
		var thead = $("#grid_01 .ui-jqgrid-htable").clone(); //html();
		
		//그리드 헤더에서 순번, 체크박스 컬럼을 제거한다.
		//$('th:eq(0), th:eq(1)', thead).remove();
		//alert(thead.html());
		
		//그리드내용에서 첫번째 더미row를 제거한다.
		$('tr:eq(0)', tbody).remove();
		//그리드 내용에서 각 row별 순번, 체크박스 컬럼을 제거한다.
		/* $('tr', tbody).each(function (){
			$('td:eq(0), td:eq(1)', $(this)).remove();
		}); */
		//alert(tbody.html());
		
		var html = "<table border='1'>" + thead.html()  + tbody.html() + "</table>";
		
		var url = '<c:url value="/commons/excel/exceldown.do" />';
		$("#frmExcel input[name=excelhtml]").val(html);		//엑셀 내용...
		$("#frmExcel input[name=excelname]").val("<s:message code='PEDC.USER.CONN.STAT.XLS' />");	//엑셀 파일 명... /* 기간별사용자별접속통계.xls */
		$("#frmExcel").attr('action', url).submit();
		
		//tableToExcel('connstatgrid01', 'test');
		//window.open('data:application/vnd.ms-excel' , html);
    	//e.preventDefault();

	break;
	case 'ExcelDown2' : //엑셀다운로드
		//그리드 내용을 가져온다.
		var tbody = $("#connstatgrid02").clone(); //.html();
		//그리드 헤더내용을 가져온다.
		var thead = $("#grid_02 .ui-jqgrid-htable").clone(); //html();
		
		//그리드 헤더에서 순번, 체크박스 컬럼을 제거한다.
		//$('th:eq(0), th:eq(1)', thead).remove();
		//alert(thead.html());
		
		//그리드내용에서 첫번째 더미row를 제거한다.
		$('tr:eq(0)', tbody).remove();
		//그리드 내용에서 각 row별 순번, 체크박스 컬럼을 제거한다.
		/* $('tr', tbody).each(function (){
			$('td:eq(0), td:eq(1)', $(this)).remove();
		}); */
		//alert(tbody.html());
		
		var html = "<table border='1'>" + thead.html()  + tbody.html() + "</table>";
		
		var url = '<c:url value="/commons/excel/exceldown.do" />';
		$("#frmExcel input[name=excelhtml]").val(html);		//엑셀 내용...
		$("#frmExcel input[name=excelname]").val("<s:message code='PEDC.CONN.STAT.XLS' />");	//엑셀 파일 명... /* 기간별접속통계.xls */
		$("#frmExcel").attr('action', url).submit();
		
	break;

	}
}


//상세정보호출
function loadDetail(param) {
	$('div#detailInfo').load('<c:url value="/commons/sys/program/ajaxgrid/selectProgramDetail.do"/>', param, function(){});

}

//데이터 저장후 후처리 함수
function aftersaveGrid(data) {
// 	alert(data.result);
	if(data.result > 0) {
		//alert("저장성공");
		//var message = "<s:message code="MSG.SAVE" />";
		showMsgBox("INF", "<s:message code="MSG.SAVE" />");
		
		//그리드에 추가 또는 업데이트
		if("I" == data.saction) {
			$("#connstatgrid01").addRowData(data.resultVO.progrmFileNm , data.resultVO, 'first');
		} else if ("U" == data.saction) {
			$("#connstatgrid01").setRowData(data.resultVO.progrmFileNm, data.resultVO);
// 			$("#connstatgrid01").addRowData("1", myfirstrow);
		}
		
		//상세정보 초기화
		loadDetail();
	} else {
		//alert("저장실패");
		showMsgBox("ERR", "<s:message code="ERR.SAVE" />");
	}
}

//삭제 후 처리함수
function afterDelGrid(data) {
	//alert(data.result);
	if(data.result > 0) {
		showMsgBox("INF", "<s:message code="MSG.DEL" />");
		//리스트 다시 조회
		$("#btnSearch").click();
		//입력 폼 초기화
		loadDetail();
	} else {
		showMsgBox("ERR", "<s:message code="ERR.DEL" />");
	}
}
</script>
</head>
<body>
<div class="adminContents">
<%-- 	<div class="location"><img src='<c:url value="/img/location_home.gif"/>' alt="home"> &gt; 관리자 &gt; 프로그램관리</div> --%>
    <div class="stit"><s:message code="USER.CONN.STAT" /></div> <!-- 사용자 접속 통계 -->
	<form name="frmSearch" action ="" method="post">
    <table border="0" cellspacing="0" cellpadding="0" class="tb_write" summary="">
        <caption>
        <s:message code="INQ.COND" /> <!-- 조회조건 --> 
        </caption>
        <colgroup>
            <col style="width:12%;">
            <col style="width:38%;">
            <col style="width:12%;">
            <col style="width:38%;">
        </colgroup>
<%--
       <tr>
        <th>구분</th>
        <td>
        	<select id="cateCode" name="cateCode">
        		<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
         	<c:forEach var="code" items="${codeMap.cateCode}" varStatus="status">
             <option value="${code.codeCd}">${code.codeLnm}</option>
             </c:forEach>
        	</select>
        </td>
        <th>게시물검색</th>
        <td>
	        <select  id="searchCnd" name="searchCnd" title="검색조건선택">
	        	<option value="0">제목</option>
	            <option value="1">내용</option>
	            <option value="2">작성자</option>
	        </select>
        <input type="text" name="searchWrd" value="${searchVO.searchWrd}" title="검색어 입력" style="display: inline; width: 150px;">
        </td>
      </tr>
--%>
      <tr>
      	<th><s:message code="TERM.DSTC" /></th> <!-- 기간구분 -->
      	<td>
	        <select  id="pdKind" name="pdKind" title="<s:message code='INQ.COND.CHC' />"> <!-- 검색조건선택 -->
	        	<option value="D"><s:message code="DALY" /></option> <!-- 일별 -->
	            <option value="M"><s:message code="MTHY" /></option> <!-- 월별 -->
	            <option value="Y"><s:message code="YRLY" /></option> <!-- 연도별 -->
	        </select>
      	</td>
      	<th><s:message code="CONN.USER.NM" /></th> <!-- 접속자명 -->
      	<td>
      		<input type="text" name="loginNm" value="${searchVO.loginNm}">
      	</td>
      </tr>
      <tr>
        <th class="bd_none"><s:message code="TERM" /></th> <!-- 기간 -->
        <td class="bd_none">
        	<a href="#" class="tb_bt">1<s:message code="DD" /></a> <!-- 일 -->
            <a href="#" class="tb_bt">3<s:message code="DD" /></a> <!-- 일 -->
            <a href="#" class="tb_bt">7<s:message code="DD" /></a> <!-- 일 -->
            <a href="#" class="tb_bt">1<s:message code="MN" /></a> <!-- 개월 -->
            <a href="#" class="tb_bt">3<s:message code="MN" /></a> <!-- 개월 -->
            <a href="#" class="tb_bt">6<s:message code="MN" /></a> <!-- 개월 -->
        </td>
        <th><s:message code="INQ.TERM" /></th> <!-- 조회기간 -->
      		   <td><input id="searchBgnDe" name="searchBgnDe" type="text" class="wd80" value="${searchVO.searchBgnDe}" >  - <input id="searchEndDe" name="searchEndDe" type="text" class="wd80" value="${searchVO.searchEndDe}">
      </tr> 
    </table>
    </form>
    <div style="clear:both; height:10px;"><span></span></div>
    
     <!-- 조회버튼영역  -->
    <tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />
     <!-- 조회버튼영역  -->
    
    <div style="clear:both; height:10px;"><span></span></div>
  	
  	<!-- 그리드 입력 입력 -->
  	<div class="stit_02" style="display: inline;"><s:message code="PEDC.CONN.STAT" /></div> <!-- 기간별 접속통계 -->
	<div class="bt02">
<!-- 	          <button class="btn_excel_load" id="btnExcelLoad" name="btnExcelLoad">엑셀 업로드</button>                        -->
       <button class="btn_excel_down" id="btnExcelDown2" name="btnExcelDown2"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                       
 	</div>
    <div style="clear:both; height:5px;"><span></span></div>
    <div  id="grid_02">
    <table id="connstatgrid02"></table>
	<div id="pager02"></div>
    </div>
   <!-- 그리드 입력 입력 -->


    <div style="clear:both; height:10px;"><span></span></div>
  	
  	<!-- 그리드 입력 입력 -->
  	<div class="stit_02" style="display: inline;"><s:message code="PEDC.USER.CONN.STAT" /></div> <!-- 기간별 사용자별 접속통계 -->
  		<div class="bt02">
<!-- 	          <button class="btn_excel_load" id="btnExcelLoad" name="btnExcelLoad">엑셀 업로드</button>                        -->
       <button class="btn_excel_down" id="btnExcelDown1" name="btnExcelDown1"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                       
 	</div>
    <div style="clear:both; height:5px;"><span></span></div>
    <div  id="grid_01" class="grid_01">
    <table id="connstatgrid01"></table>
	<div id="pager01"></div>
    </div>
   <!-- 그리드 입력 입력 -->
   
    <div style="clear:both; height:10px;"><span></span></div>
	
	<div id="detailInfo"></div>
   
</div>
<form id="frmExcel" name="frmExcel" action="" method="post" >
	<input type="hidden" name="excelhtml" id="excelhtml">
	<input type="hidden" name="excelname" id="excelname">
</form>

</body>
</html>