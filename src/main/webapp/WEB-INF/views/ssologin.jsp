<!DOCTYPE html>
<%@page language="java"%> 
<%@page contentType="text/html;charset=EUC-KR"%> 
<%@page import="com.initech.util.Base64Util"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.initech.eam.nls.Ticket"%>
<%@page import="com.initech.eam.nls.CookieManager"%>
<%@page import="java.security.SecureRandom"%>
<%@page import="com.initech.eam.nls.NLSHelper"%>

<%@ include file="commons/sso/config.jsp" %>
<%
	/* 
	  #noCacheNonce : ���Ͻÿ����� ĳ���� �����ϱ� ���� Cookie�� RequestParameter�� �̿��
	  1) LoginFormPage�� resession.jsp���� �����ϰ�, �� ���� �������� RequestParameter�� �Ѱ��ش�.
	     -> NLS���� Page�̵��� �׻� �ѱ�� �޵��� �Ѵ�.
	  2) LoginFormPage�� resession.jsp���� �� �̸��� Cookie�� �������ֵ��� �Ѵ�.    
	 */
	 
    String noCacheNonce = Ticket.generateNonce();
	  
    // -- resession.nonce ����
	SecureRandom random = new SecureRandom();
	byte[] nonce = new byte[8];
	random.nextBytes(nonce);
	
	String nonceStr = cutCarriageReturn (new String(Base64Util.encode(nonce))) ;
	
	session.setAttribute("nexess.nls.resession.nonce", nonceStr);
	
%>

<OBJECT ID="NEXESS_API" CLASSID="CLSID:D4F62B67-8BA3-4A8D-94F6-777A015DB612" width=0 height=0></OBJECT>

<script language=javascript>
// 	alert("sso script start");
	try{
		
	var resession = NEXESS_API.PrepareResession("<%=nonceStr%>");
	var ticket = NEXESS_API.GetTicket();
	
	if(ticket.length == 1 || ticket == ""){		
		self.location.replace("<%=NLS_LOGIN_URL%>");
		//�α��� �Ǿ����� ����. NLS �� replace.
	}else{
		while(ticket.indexOf("+") != -1) {
			ticket= ticket.replace("+", "%2b");
		}
		self.location.replace("<%=ASCP_URL%>?ticket=" + escape(ticket));
	}
	}catch (err ){
// 		alert("sso errror");
		self.location.replace("/nhisportal/loginform.do");
	}

</script>