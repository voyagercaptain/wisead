<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<c:set var="toplogo"><s:message code="wiseda.site.name" /></c:set>

<html>
<head>
<title></title>

<script language="JavaScript" type="text/javascript" src='<c:url value="/js/security/jsbn.js" />'></script>
<script language="JavaScript" type="text/javascript" src='<c:url value="/js/security/rsa.js" />'></script>
<script language="JavaScript" type="text/javascript" src='<c:url value="/js/security/prng4.js" />'></script>
<script language="JavaScript" type="text/javascript" src='<c:url value="/js/security/rng.js" />'></script>
<script language="JavaScript" type="text/javascript" src='<c:url value="/js/security/base64.js" />'></script>

<script type="text/javascript">
// alert("${toplogo}");
$(document).ready(function() {
	//window.open('http://localhost:8080/wisead/loginform.do','WDQ','width=1024,height=1280,status=no,toolbar=no,scrollbars=no');
	OpenModal("<c:url value='loginform.do'/>", 'WDQ', 1280, 1024, "yes");
	var win = window.open('', '_self');
	win.close(); 
});
</script>

</head>

<body>

	자동으로 닫히지 않을 경우 창을 닫아주세요.
</body>
</html>