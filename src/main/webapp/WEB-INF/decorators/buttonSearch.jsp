<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <div class="divLstBtn" style="display: none;">	 
            <div class="bt03">
			    <button class="btn_search" id="btnSearch" name="btnSearch" style="display:none"><s:message code="INQ"/></button> <!-- 조회 --> 
			</div>
			<div class="bt02">
				<button class="btn_excel_down" id="poiDown"    name="poiDown" style="display:none">품질평가용 자료 내려받기</button> <!-- 보고서-->
			    <button class="btn_excel_down" id="btnReport"    name="btnReport" style="display:none"><s:message code="REPORT" /></button> <!-- 보고서-->
				<button class="btn_excel_down" id="btnExcelDown" name="btnExcelDown" style="display:none"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                       
	    	</div>
        </div>	

