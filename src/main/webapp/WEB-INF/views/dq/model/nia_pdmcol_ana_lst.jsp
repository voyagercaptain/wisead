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
// EnterkeyProcess("Search");

$(document).ready(function() {

});

$(window).load(function() {
	initPdmGrid();
	initColGrid();
	$(window).resize();

	doPdmAction("Search");
	doColAction("Search");
});


$(window).resize(function(){
    //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
// 	setibsheight($("#grid_01"));
	// grid_sheet.SetExtendLastCol(1);    
});

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

<div style="clear:both; height:10px;"><span></span></div>
	<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li id="tab-1" ><a href="#tabs-1">누락 테이블명/컬럼명 진단 결과</a></li> <!-- 누락 테이블명/컬럼명 진단 결과 -->
	    <li id="tab-2" ><a href="#tabs-2">컬럼한글명 진단 결과</a></li> <!-- 컬럼한글명 진단 결과 -->
	  </ul>
	  <div id="tabs-1">
	  <!-- 컬럼 정보 탭 -->
		<%@include file="nia_pdm_ana_dtl.jsp" %>
	  </div>
	  <div id="tabs-2">
		<!-- 컬럼 이력 탭 -->
		<%@include file="nia_col_ana_dtl.jsp" %>
	  </div>  
	</div>
</div>
<div style="clear:both; height:5px;"><span></span></div>
</body>
</html>