<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<script type="text/javascript">

$(document).ready(function() {
	    	  
	//품질지표 검색 팝업
	$("#btnSearchDqiPop").click(function(){
		var url = "<c:url value='/dq/criinfo/dqi/popup/dqi_pop.do' />";
    	var param = "sflag=BRDTL"; 
    		param += "&dqiIds="+$("#frmDetail #dqiId").val();
    	//openLayerPop(url, 800, 600, param);
    	var popup = OpenWindow(url+"?"+param,"dqiSearch","800","600","yes");
	});
	
	//담당자 검색 팝업 호출
	$("#userPop").click(function(){
    	var param = ""; //$("form#frmInput").serialize();
    	openLayerPop("<c:url value='/commons/user/popup/userSearchPop.do' />", 800, 600, param);
    });
	
	//요청서 단계에 따라 저장,초기화 버튼 숨기기...
	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//===============요청상세탭화면 ==================
	//요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리)
	setDispLstButton(rqststep);

	//===========DBMS 세팅=================
	var val = $("#frmDetail #dbConnTrgId option:selected").val();
	var trgId = ${codeMap.devConnTrgSch} ;
	//$("#frmDetail #dbConnTrgId").append('<option value=""></option>');
	
	for(i=0; i<trgId.length; i++) {
		if(trgId[i].upcodeCd == val) {
			$("#frmDetail #dbConnTrgId").append('<option value="' + trgId[i].codeCd + '">' + trgId[i].codeLnm + '</option>');
		}
	} 	
	
	$("#frmDetail #dbConnTrgId").change(function() {
		$("#frmDetail #dbSchId").find("option").remove().end();
		var val = $("#frmDetail #dbConnTrgId option:selected").val();

		$("#frmDetail #dbSchId").append('<option value=""><s:message code="CHC" /></option> ');
		
		for(i=0; i<trgId.length; i++) {
			if(trgId[i].upcodeCd == val && val!="") {
				$("#frmDetail #dbSchId").append('<option value="' + trgId[i].codeCd + '">' + trgId[i].codeLnm + '</option>');
			}
		}
	});
	//==================================

	//테이블명 검색 팝업
	$('#btnSearchTblPop').click(function(event){
    	event.preventDefault();	//브라우저 기본 이벤트 제거...

    	var param = $("form[name=frmDetail]").serialize(); 
    	var url = '<c:url value="/dq/criinfo/anatrg/popup/tbllst_pop.do"/>';

    	var popwin = OpenModal(url + "?sflag=TBLLST&" + param, "tblLstPop",  900, 600, "no");

		popwin.focus();
	});

	//컬럼명 검색 팝업
	$('#btnSearchColPop').click(function(event){
    	event.preventDefault();	//브라우저 기본 이벤트 제거...
    	
    	var param = $("form[name=frmDetail]").serialize(); 

		//alert(param);
    	
    	var url = '<c:url value="/dq/profile/popup/collst_pop.do"/>';
    	
    	var popwin = OpenModal(url+"?sflag=COLLST&" + param, "colLstPop",  900, 700, "no");
    	
		popwin.focus();
	});
	
});


//DQI 팝업 리턴값 처리
function returnDqiPop (ret) {
	//초기화
	$("#frmDetail #dqiLnm").val("");
	$("#frmDetail #dqiId").val("");
	
	for(var i=0; i<ret.data.length; i++){
		var retjson = JSON.stringify(ret.data[i]);
		var parsejson = jQuery.parseJSON(retjson);
		
		if($("#frmDetail #dqiLnm").val() != null && $("#frmDetail #dqiLnm").val() != "undefined" && $("#frmDetail #dqiLnm").val() != "" ){
			$("#frmDetail #dqiLnm").val($("#frmDetail #dqiLnm").val() + "," + parsejson.fullPath);
			$("#frmDetail #dqiId").val($("#frmDetail #dqiId").val() + "," + parsejson.dqiId);
		}else {
			$("#frmDetail #dqiLnm").val(parsejson.fullPath);
			$("#frmDetail #dqiId").val(parsejson.dqiId);
		}
	}
}

//담당자 팝업 리턴값 처리...
function returnUserInfoPop(ret) {
// 	alert(ret);
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmDetail #brCrgpUserId").val(retjson.userId);
	$("#frmDetail #brCrgpUserNm").val(retjson.userNm);
}


</script>
<!-- </head> -->
<!-- <body> -->
	  	<form name="frmDetail" id="frmDetail" method="post" action="">
			<div style="clear:both; height:5px;"><span></span></div>
			<%-- <div id="valid_bizrule" style="display: none;">
			<tiles:insertTemplate template="/WEB-INF/decorators/validreview.jsp" />
			<div style="clear:both; height:10px;"><span></span></div>
			</div> --%>
			<div class="tb_basic">
	  			<input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction }" />
	  			<input type="hidden" id="brId" name="brId" value="${result.brId}" />
				<%-- <input type="hidden" id="dqiId" name="dqiId" value="${result.dqiId}" /> --%>
			
				
				<div class="stit"><s:message code="BZWR.RGR.BSIC.INFO"/></div><!-- 업무규칙 기본정보 -->
				<table border="0" cellspacing="0" cellpadding="0"  summary="">
		        <caption><s:message code="BZWR.RGR.BSIC.INFO"/></caption><!-- 업무규칙 기본정보 -->
		        <colgroup>
		            <col style="width:12%;">
		            <col style="width:38%;">
		            <col style="width:12%;">
		            <col style="width:*;">
		        </colgroup>
			      <tr>
			        <th class="th_require"><s:message code="BZWR.RGR.NM"/></th><!-- 업무규칙명 -->
			        <td>
                    	<input type="text" class="wd60p" id="brNm" name="brNm" value="${result.brNm }" readonly/>
			        </td>
			        
			        <th class="th_require"><s:message code="QLTY.INDC.NM"/></th><!-- 품질지표명 -->			       
			        <td>
				        <select id="dqiId" class="wd60p" name="dqiId">
			               <option value="OBJ_00000084179">시간순서</option>
			               <option value="OBJ_00000084180">컬럼 간 논리관계</option>
			               <option value="OBJ_00000084181">계산 및 집계</option>
			            </select>
		           </td>
			        <%-- <td>
						<input type="text" class="wd60p" id="dqiLnm" name="dqiLnm" value="${result.dqiLnm }" readonly/>
			        	<button class="btnSearchPop" id="btnSearchDqiPop" name="btnSearchDqiPop"><s:message code="SRCH" /></button><!-- 검색 -->
			        </td> --%> 			        
			      </tr>
			      
			      <tr>
			        <th class="th_require"><s:message code="DB.MS.SCHEMA.NM" /></th><!-- 진단대상명 -->
			        <td>
			            <select id="dbConnTrgId"  name="dbConnTrgId">
					       <option value=""><s:message code="WHL" /></option><!--전체-->
					    </select>
		                <select id="dbSchId" class="" name="dbSchId">
		                	<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
		                </select>			           
			        </td>
		         	<th class="th_require">테이블/컬럼명</th><!-- 테이블명 -->
			        <td>
                  		<input type="text" id="dbcTblNm" name="dbcTblNm" value="${result.dbcTblNm }" style="width:40%;" readonly/>
                  		<input type="text" id="dbcColNm" name="dbcColNm" value="${result.dbcColNm }"  style="width:40%;" readonly/>
                  		
                  		<button class="btnSearchPop" id="btnSearchColPop" ><s:message code="SRCH" /></button><!-- 검색 -->
                  		
                  		<%-- <button class="btnSearchPop" id="btnSearchTblPop" ><s:message code="SRCH" /></button><!-- 검색 --> --%>
			        </td> 			      
			      </tr>
			      
			      <tr>
			      	<th scope="row"><s:message code="CONTENT.TXT" /></th><!-- 설명 -->
			        <td colspan="3">
			        	<textarea style="height:20px;width:99%;word-spacing:5px;"  id="objDescn" name="objDescn" accesskey="" readonly>${result.objDescn }</textarea>
			        </td>
			      </tr>
			      
			      <tr>
			         <th><s:message code="CCNT.SQL"/></th><!-- 건수SQL -->
			        <td>
			        	<textarea style="height:150px;width:99%;word-spacing:5px;"  id="cntSql" name="cntSql" accesskey="" placeholder="<s:message code="BR.EXAM.CNT.SQL" />" readonly>${result.cntSql}</textarea>
			        </td>
			        <th><s:message code="ANLY.SQL" /></th><!-- 분석SQL -->
			        <td>
			        	<textarea style="height:150px;width:99%;word-spacing:5px;"  id="anaSql" name="anaSql" accesskey="" placeholder="<s:message code="BR.EXAM.ANA.SQL" />" readonly>${result.anaSql}</textarea>
			        </td>
			      </tr>
			      
			      <%-- <tr>
		          	<th scope="row"><label for="schDbConnTrgId">업무규칙 유형</label></th><!--진단대상명-->
                    <td>
                       <select id="schDbConnTrgId"  name="schDbConnTrgId" class="wd60p" >
                       	   <option value=""></option>
					       <c:forEach var="code" items="${codeMap.connTrgDbmsCd}" varStatus="status">
					       		<option value="${code.codeCd}">${code.codeLnm}</option>
					       </c:forEach>
                       </select>
                    </td>
		          </tr> --%>
			      
			      <%-- <tr>
			        <th><s:message code="BZWR.RGR.CHGR"/></th><!-- 업무규칙담당자 -->
			         <td>
			        	<input type="hidden" class="" id="brCrgpUserId" name="brCrgpUserId" value="${result.brCrgpUserId}" readonly/>
			        	<input type="text" class="wd60p" id="brCrgpUserNm" name="brCrgpUserNm" value="${result.brCrgpUserNm}" readonly/>
			        	<button class="btnSearchPop" id="userPop" name="userPop"><s:message code="SRCH" /></button><!-- 검색 -->
			        </td>
			      </tr>
			      <tr>
			        <th><s:message code="USE.YN"/></th><!-- 사용여부 -->
			        <td>
						<select id="useYn" name="useYn" class="input" value="${result.useYn }" disabled="disabled">
			        	<option value="Y"><s:message code="MSG.YES"/></option><!-- 예 -->
			        	<option value="N"><s:message code="MSG.NO" /></option><!-- 아니오 -->
			        </select>
			        </td>
			      </tr> --%>
			      </table>
			</div>
			    </form>
			<%-- <div style="clear:both; height:10px;"><span></span></div>
			<div id="otherinfo" style="display:none;"><%@include file="../../meta/stnd/otherinfo.jsp" %></div> --%>

			<div style="clear:both; height:10px;"><span></span></div>
			<div id="buttonRqst" style="display: none;">
			<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstInput.jsp" />
			</div>
<!-- </body> -->
<!-- </html> -->