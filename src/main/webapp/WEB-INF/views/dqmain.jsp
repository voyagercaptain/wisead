<!DOCTYPE html>
<%@page import="kr.wise.commons.WiseConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link href="css/design.css" rel="stylesheet" type="text/css">

<script type="text/javascript"
	src='<c:url value="/js/IBChart/ibchart.js"/>'></script>
<script type="text/javascript"
	src='<c:url value="/js/IBChart/ibchartinfo.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/IBChart/ibchart-lite.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/IBChart/ibchart-migr.js"/>'></script>
<script type="text/javascript">

$(document).ready(function(){
	

	var con1 = document.getElementById("approveChart");
	var con2 = document.getElementById("analyzeChart");
	
	createIBChart(con1,"approveChart", {width:"auto", height:"150px"});
	createIBChart(con2,"analyzeChart", {width:"auto", height:"150px"});
	
	analyzeChartDraw();
	approveChartDraw();	
	
	sumVal();
	
	$("#dbConnTrgId").change(function(){
		drawTable();
		approveChartDraw();
		analyzeChartDraw();
	});
});

function drawTable() {
	var param = $("#dbConnTrgId").val();
	
	var ajaxurl = '<c:url value="/portal/dashboard/dqPrfTable.do"/>'+"?dbConnTrgId=" +param;
	var syn = false;
	var result;
	$.ajax({
		url: ajaxurl,
		async: syn,
		type: "POST",
		dataType: 'json',
		complete: function (data) {
			result = JSON.parse(data.responseText);
			
			reDraw(result);
		}
	});

// 	if(param != ""){
// 		var ajaxurl = '<c:url value="/portal/dashboard/dqPrfTable.do"/>'+"?dbConnTrgId=" +param;
// 		var syn = false;
// 		var result;
// 		$.ajax({
// 			url: ajaxurl,
// 			async: syn,
// 			type: "POST",
// 			dataType: 'json',
// 			complete: function (data) {
// 				result = JSON.parse(data.responseText);
				
// 			}
// 		});
// 	}
// 	else{
// 		result = ${result};
// 	}
	
}

function reDraw(result) {
	var isNull = 0;
	for(var i=0; i<result.length; i++) {
		if(result[i].totCnt !== null && result[i].totCnt !== undefined) isNull++;
	}
	
	var length = $("#resultTable").find("tr").length;
	if(isNull <= 0){
		for(var i=2; i<length+1; i++){
			var col = 1;
			var colLen = $("#resultTable tbody > tr:nth-child("+i+") > td").length;
			if($("#resultTable tbody > tr:nth-child("+i+") > td").length == 8){
				col=2;
			}
			$("#resultTable tbody > tr:nth-child("+i+") > td:nth-child("+(col+1)+")").text('');
			$("#resultTable tbody > tr:nth-child("+i+") > td:nth-child("+(col+2)+")").text('');
			$("#resultTable tbody > tr:nth-child("+i+") > td:nth-child("+(col+3)+")").text('');
			$("#resultTable tbody > tr:nth-child("+i+") > td:nth-child("+(col+4)+")").text('');
			$("#resultTable tbody > tr:nth-child("+i+") > td:nth-child("+(col+5)+")").text('0.00%');
			$("#resultTable tbody > tr:nth-child("+i+") > td:nth-child("+(col+6)+")").text('0.00%');
		}

		$("#resultTable tbody > tr:nth-child("+length+") > td:nth-child(6)").text('');
		$("#resultTable tbody > tr:nth-child("+length+") > td:nth-child(7)").text('');
		
		
		return;
	}
		
	var idx = 0;
	var tabCnt = 0;
	var colCnt = 0;
	var totCnt = 0;
	var errCnt = 0;
	var errRate = 0;
	var errWeight = 0;
	var len = result.length;
	for(var i=2;i<length;i++){
		var col = 2;
		var colLen = $("#resultTable tbody > tr:nth-child("+i+") > td").length;
		if($("#resultTable tbody > tr:nth-child("+i+") > td").length == 8){
			col=3;
		}
		if($("#resultTable tbody > tr:nth-child("+i+") > td:nth-child("+(--col)+")").text()!=result[idx].dqiLnm){
			$("#resultTable tbody > tr:nth-child("+i+") > td:nth-child("+(col+1)+")").text('');
			$("#resultTable tbody > tr:nth-child("+i+") > td:nth-child("+(col+2)+")").text('');
			$("#resultTable tbody > tr:nth-child("+i+") > td:nth-child("+(col+3)+")").text('');
			$("#resultTable tbody > tr:nth-child("+i+") > td:nth-child("+(col+4)+")").text('');
			$("#resultTable tbody > tr:nth-child("+i+") > td:nth-child("+(col+5)+")").text('0.00%');
			$("#resultTable tbody > tr:nth-child("+i+") > td:nth-child("+(col+6)+")").text('0.00%');
			continue;
		}
		if(result[idx].totCnt == null){
			idx++;
			continue;
		}
		$("#resultTable tbody > tr:nth-child("+i+") > td:nth-child("+(col+1)+")").text(result[idx].tblCnt);
		$("#resultTable tbody > tr:nth-child("+i+") > td:nth-child("+(col+2)+")").text(result[idx].colCnt);
		$("#resultTable tbody > tr:nth-child("+i+") > td:nth-child("+(col+3)+")").text(result[idx].totCnt);
		$("#resultTable tbody > tr:nth-child("+i+") > td:nth-child("+(col+4)+")").text(result[idx].errCnt);
		$("#resultTable tbody > tr:nth-child("+i+") > td:nth-child("+(col+5)+")").text(result[idx].errRate+"%");
		$("#resultTable tbody > tr:nth-child("+i+") > td:nth-child("+(col+6)+")").text(result[idx].sumRate+"%");

		if(idx<len-1)
			idx++;	
	}
	sumVal();
}


function sumVal(){
	
	var length = $("#resultTable").find("tr").length;
	
	var tabCnt = 0;
	var colCnt = 0;
	var totCnt = 0;
	var errCnt = 0;
	var errRate = 0;
	var errWeight = 0;
	
	for(var i=2;i<length;i++){
		var col = 2;
		var colLen = $("#resultTable tbody > tr:nth-child("+i+") > td").length;
		if($("#resultTable tbody > tr:nth-child(1) > th").length==colLen){
			col++;
		}
		
		var tempTabCnt = parseInt($("#resultTable tbody > tr:nth-child("+i+") > td:nth-child("+(col)+")").text().replace(',',''));
		var tempColCnt = parseInt($("#resultTable tbody > tr:nth-child("+i+") > td:nth-child("+(col+1)+")").text().replace(',',''));
		var tempTotCnt = parseInt($("#resultTable tbody > tr:nth-child("+i+") > td:nth-child("+(col+2)+")").text().replace(',',''));
		var tempErrCnt = parseInt($("#resultTable tbody > tr:nth-child("+i+") > td:nth-child("+(col+3)+")").text().replace(',',''));
		
		if(isNaN(tempTabCnt) == false) {
			tabCnt += tempTabCnt; 
		}
		if(isNaN(tempColCnt) == false) {
			colCnt += tempColCnt; 
		}
		if(isNaN(tempTotCnt) == false) {
			totCnt += tempTotCnt; 
		}
		if(isNaN(tempErrCnt) == false) {
			errCnt += tempErrCnt; 
		}
		
	}
	
	if(totCnt!=0){
		errRate = (errCnt/totCnt*100).toFixed(2);
	}
	
	tabCnt = numberWithCommas(tabCnt);
	colCnt = numberWithCommas(colCnt);
	totCnt = numberWithCommas(totCnt);
	errCnt = numberWithCommas(errCnt);
	
	$("#resultTable tbody > tr:nth-child("+length+") > td:nth-child(2)").html(tabCnt);
	$("#resultTable tbody > tr:nth-child("+length+") > td:nth-child(3)").html(colCnt);
	$("#resultTable tbody > tr:nth-child("+length+") > td:nth-child(4)").html(totCnt);
	$("#resultTable tbody > tr:nth-child("+length+") > td:nth-child(5)").html(errCnt);
	$("#resultTable tbody > tr:nth-child("+length+") > td:nth-child(6)").html(errRate+" %");
	if($(".n4sm_box14 .num").text()=="")
		$(".n4sm_box14 .num").html(errRate + "%");
}

function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function analyzeChartDraw()	{
	var param = $("#dbConnTrgId").val();
	$.ajaxSetup({cache:false});
	$.getJSON('<c:url value="/portal/dashboard/dqAnalyzeChart.do"/>' + "?dbConnTrgId=" + param, function(data){
		if(data ==  null) return;
		//???????????? ?????? ?????????

		
		analyzeChart.removeAll();
		//????????? ??????
		//var seriesAnaCnt = analyzeChart.CreateSeries();
		
		//??????????????? ??????
		var anaCnt = new Array();
		var errCnt = new Array();
		var category = new Array();
		var maxLen = 0;
		/*
		//???????????? ????????? ??????
		var sumErr = 0;
		for(var i = 0; i < data.length; i++){
			sumErr += parseFloat(data[i].errRate);
		}
		//?????????????????? ??????, DBMS??? ??????
		for(var i = 0; i < data.length; i++){
			anaCnt.push({Y:Math.round(parseFloat(data[i].errRate) / sumErr*10000) / 100, Name:data[i].schLnm});
			category.push(data[i].schLnm);
		}
		
		seriesAnaCnt.setOptions({
			Name:"????????????",
			Type:"column",
			Color:"#FF6633",
			Data:anaCnt
		});
		*/
		//????????? ????????? ??????
		//?????????????????? ??????, DBMS??? ??????
		for(var i = 0; i < data.length; i++){
			var v = parseFloat(data[i].errRate);
			anaCnt.push({Y:Math.round(v * 100)/100, Name:data[i].schLnm});
			category.push(data[i].schLnm);
			if(v > maxLen)
				maxLen = v;
		}
/* 		seriesAnaCnt.setOptions(toCamel({
			Name:"?????????",
			Type:"column",
			Color:"#FF6633",
			Data:anaCnt
		})); */
		//analyzeChart.SetYAxisOptions(toCamel({Max:maxLen, Title : {Text : ""}}));
		//analyzeChart.SetXAxisOptions(toCamel({Categories:category,TickInterval : 0,Labels : {Enabled : true ,StaggerLines : 2}}));
		var seriesAnaCnt = toCamel({
			Name:"<s:message code='EROR.CCNT' />", /* ???????????? */
			Type:"column",
			data: anaCnt
		});
		analyzeChart.setOptions({
			series: [
				seriesAnaCnt
			]}, { append: true, resetData: true })
		//analyzeChart.addSeries(seriesAnaCnt);
		analyzeChart.setOptions(toCamel({
			Chart : {
				BackgroundColor : "#FFFFFF", //?????? ????????? ??????
				Type : "column", //?????? Type ??????
				BorderColor : "#FFFFFF" //?????? ????????? ??? ??????
			},
			Legend : {
				Layout : "horizontal", //Legend ?????? ??????
				Align : "center", //Legend ?????? ?????? ??????
				VerticalAlign : "bottom", //Legend ?????? ?????? ??????
				Enabled : false
			},
			
			PlotOptions : {
				Series : {
					DataLabels : { //???????????? ????????? ????????? ??????
						Enabled : true,
					}
				},
				Column : {
					PointPadding : 0.1,  // ???????????? ?????? ??????
			//		stacking : "normal"
				},
			},
			XAxis : {
				Categories : category,
				TickInterval : 0, //X??? ????????? ?????? ??????
				Labels : { //X??? ????????? ??????
					Enabled : true,
					StaggerLines : 2
				}
			},
			YAxis : {
//	 			TickInterval : 30, //Y??? ????????? ?????? ??????
				Max : maxLen, //Y??? Min??? ??????
				Title : { //Y??? ?????? ??????
					Text : ""
				}
			}
		}));
		analyzeChart.draw();
	});
}
function approveChartDraw()	{
	var errRate = new Array();
	var dqiLnm = new Array();
	var length = $("#resultTable").find("tr").length;
	for(var i=2; i<length; i++){
		var col = 1;
		var colLen = $("#resultTable tbody > tr:nth-child("+i+") > td").length;
		if($("#resultTable tbody > tr:nth-child("+i+") > td").length == 8){
			col=2;
		}
		
		if($("#resultTable tbody > tr:nth-child("+i+") > td:nth-child("+(col+3)+")").text() != ''){
			errRate.push($("#resultTable tbody > tr:nth-child("+i+") > td:nth-child("+(col+6)+")").text());
			dqiLnm.push($("#resultTable tbody > tr:nth-child("+i+") > td:nth-child("+(col)+")").text());
		}
	}

	approveChart.removeAll();
//	var data = ${result};
	//if(data ==  null ) return;
	if(dqiLnm.length == 0 || errRate.length == 0) {
		return;
	}


	//var seriesPrfCnt = approveChart.CreateSeries();

	var category = new Array();
	//???????????? ????????? ??????
	var pointsPrfCnt = new Array();
	
	//????????? ?????? ??????
	var totCnt=0, errCnt=0;
	for(var i = 0; i < errRate.length; i++){
		pointsPrfCnt.push({Y:parseFloat(errRate[i].substr(0,errRate[i].length)), Name:dqiLnm[i]});
		category.push(dqiLnm[i]);
	}
	var seriesPrfCnt = toCamel({
		Name:"<s:message code='EROR.CCNT' />", /* ???????????? */
		Type:"column",
		data: pointsPrfCnt
	});
	
	approveChart.setOptions({
		series: [
			seriesPrfCnt
		]}, { append: true, resetData: true })
	//approveChart.SetXAxisOptions(toCamel({Categories:category,TickInterval : 0,Labels : {Enabled : true ,StaggerLines : 2}}));
	//seriesPrfCnt.AddPoints(toCamel(pointsPrfCnt));
	//approveChart.addSeries(toCamel(seriesPrfCnt));
	
	approveChart.setOptions(toCamel({
		Chart : {
			BackgroundColor : "#FFFFFF", //?????? ????????? ??????
			Type : "column", //?????? Type ??????
			BorderColor : "#FFFFFF" //?????? ????????? ??? ??????
		},
		Legend : {
			Layout : "horizontal", //Legend ?????? ??????
			Align : "center", //Legend ?????? ?????? ??????
			VerticalAlign : "bottom", //Legend ?????? ?????? ??????
			Enabled : false
		},
		PlotOptions : {
			Series : {
				DataLabels : { //???????????? ????????? ????????? ??????
					Enabled : true,
				}
			},
			Column : {
				PointPadding : 0.1,  // ???????????? ?????? ??????
			},
		},
		Colors : ["#7AAAEE","#F06F3E","#AAEE6A","#F0E150","#5DA0A9","#75738B"],

		XAxis : {
			Categories:category,
			TickInterval : 0, //X??? ????????? ?????? ??????
			Labels : { //X??? ????????? ??????
				Enabled : true,
				StaggerLines : 2
			}
		},
		YAxis : {
			Min : 0, //Y??? Min??? ??????
			Title : { //Y??? ?????? ??????
				Text : ""
			}
		}
	}));
	approveChart.draw();
	
}

</script>
<div class="n4main">
	<c:forEach var="result" items="${mtaTotalCntList}" varStatus="status">

		<c:if test="${result.cntName eq '????????????DBMS'}">
			<!-- ??????11 ?????? -->
			<div class="n4sm_box11">
				<div class="tit">
					<div>???????????? DBMS</div>
					<a href="/wisead/dq/criinfo/anatrg/dbms_rqst.do">????????????</a>
				</div>
				<div class="chart">
					<div class="txt">DBMS COUNT</div>
					<div class="num">${result.totCnt}</div>

				</div>
			</div>
		</c:if>
		<!-- ??????11 ??? -->

		<!-- ??????12 ?????? -->
		<c:if test="${result.cntName eq '????????????'}">
			<div class="n4sm_box12">
				<div class="tit">
					<div>???????????? TABLE</div>
					<a href="/wisead/dq/criinfo/anatrg/trgtbl_rqst.do">????????????</a>
				</div>
				<div class="chart">
					<div class="txt">TABLE COUNT</div>
					<div class="num">
						<fmt:formatNumber value="${result.totCnt}" pattern="#,###" />
					</div>
				</div>
			</div>
		</c:if>
		<!-- ??????12 ??? -->

		<!-- ??????13 ?????? -->
		<c:if test="${result.cntName eq '?????????'}">
			<div class="n4sm_box13">
				<div class="tit">
					<div>???????????? COLUMN</div>
					<a href="/wisead/dq/criinfo/anatrg/chkruleaply_rqst.do">????????????</a>
				</div>
				<div class="chart">
					<div class="txt">COLUMN COUNT</div>
					<div class="num">
						<fmt:formatNumber value="${result.totCnt}" pattern="#,###" />
					</div>
				</div>
			</div>
		</c:if>
		<!-- ??????13 ??? -->
	</c:forEach>
	<!-- ??????14 ?????? -->
	<div class="n4sm_box14">
		<div class="tit">
			<div>?????????</div>
			<a href="/wisead/dq/result/result_lst.do">????????????</a>
		</div>
		<div class="chart">
			<div class="txt">ERROR RATE</div>
			<div class="num"></div>
		</div>
	</div>
	<!-- ??????14 ??? -->
	<div> DBMS??? ?????? ?????? <select id="dbConnTrgId" name="dbConnTrgId">
								<option value="">??????</option>
								<c:forEach var="code" items="${codeMap.connTrgDbmsCd}" varStatus="status">
									<option value="${code.codeCd}">${code.codeLnm}</option>
								</c:forEach>
							</select></div>

	<div id="test" hidden="false">0</div>
	<!-- ?????? 21 ?????? -->
	
	<div class="n4sm_box21">
		<table border="0" cellspacing="0" cellpadding="0" class="n4sm_tb_01"
			id="resultTable" style="margin-top: 3px;">
			<caption>?????????????????? ?????????</caption>
			<colgroup>
				<col style="width: 20%;">
				<col style="width: 20%;">
				<col style="width: 10%;">
				<col style="width: 10%;">
				<col style="width: 10%;">
				<col style="width: 10%;">
				<col style="width: 10%;">
				<col style="width: 10%;">
			</colgroup>
			<tr>
				<th>?????????????????????</th>
				<th>???????????????</th>
				<th>????????????</th>
				<th>?????? ???</th>
				<th>????????????</th>
				<th>????????????</th>
				<th>?????????</th>
				<th>????????????</th>
			</tr>

			<c:set var="rowCnt" value="0"></c:set>
			<c:forEach var="result" items="${prfTotalCnt}" varStatus="status">
				<c:if test="${status.count%2 eq 0}">
					<tr class="even">
				</c:if>
				<c:if test="${status.count%2 eq 1}">
					<tr>
				</c:if>
				<c:if test="${rowCnt eq 0}">
					<td rowspan="${result.uppDqiCnt}">${result.uppDqiNm}</td>
				</c:if>
				<c:set var="rowCnt" value="${rowCnt+1}"></c:set>
				<c:if test="${rowCnt eq result.uppDqiCnt }">
					<c:set var="rowCnt" value="0"></c:set>
				</c:if>
				<td>${result.dqiLnm}</td>
				<td style="text-align: right;">${result.tblCnt}</td>
				<!-- ????????? ?????? ????????? ???????????? -->
				<td style="text-align: right;">${result.colCnt}</td>
				<td style="text-align: right;"><fmt:formatNumber
						value="${result.totCnt}" pattern="#,###" /></td>

				<c:if test="${!empty result.errCnt}">
					<td style="text-align: right;"><fmt:formatNumber
							value="${result.errCnt}" pattern="#,###" /></td>
				</c:if>
				<c:if test="${empty result.errCnt}">
					<td style="text-align: right;"></td>
				</c:if>
				<c:if test="${empty result.errRate}">
					<td style="text-align: right;"></td>
				</c:if>
				<c:if test="${!empty result.errRate}">
					<td style="text-align: right;">${result.errRate}%</td>
				</c:if>
				<c:if test="${empty result.sumRate }">
					<td style="text-align: right;"></td>
				</c:if>
				<c:if test="${!empty result.sumRate }">
					<td style="text-align: right;">${result.sumRate}%</td>
				</c:if>
				</tr>
			</c:forEach>
			<tr id="sumVal" class="bg_blue">
				<td colspan="2" style="text-align: center;">????????? ??????</td>
				<td style="text-align: right;"></td>
				<td style="text-align: right;"></td>
				<td style="text-align: right;"></td>
				<td style="text-align: right;"></td>
				<td style="text-align: right;"></td>
				<td style="text-align: right;"></td>
			</tr>


		</table>

	</div>
	<!-- ?????? 21 ??? -->

	<!-- ?????? 22 ?????? -->
	<div class="n4sm_box22">
		<ul>

			<li>
				<div class="n4sm_box222">
					<div class="tit">
						<div>???????????? ??????</div>
						<a href="/wisead/dq/result/result_lst.do">????????????</a>
					</div>
					<div class="chart" id = "approveChart">
					</div>
				</div>
			</li>
			<li>
				<div class="n4sm_box221">
					<div class="tit">
						<div>
							???????????? ?????? ??????
						</div>
						<a href="/wisead/dq/vrfcrule/vrfcrule_err_lst.do">????????????</a>
					</div>
					<div class="chart" id="analyzeChart">
					</div>
				</div>
			</li>
		</ul>
		<!--         <dl> -->
		<%-- 	  	  	<dd>????????? : ${chartCnt[0].errRate }%</dd> --%>
		<%-- 	    	<dd>?????????????????? : ${chartCnt[0].dtm }</dd> --%>
		<!--      	</dl> -->
	</div>
	<!-- ?????? 22 ??? -->

</div>


