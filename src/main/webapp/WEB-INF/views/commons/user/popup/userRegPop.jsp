<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원가입</title>
<style>

/*테이블 왼쪽 항목 글자*/
.col{	
		color: #107ec1;
    	font-size: 14px;
    	display: inline-block;
    	width: 100px;
    	padding-left: 40px;
	}
</style>

<script>
$(document).ready(function() {
	//아이디 중복확인
	$("#idCheck").click(function(){
		 var userId =  $("#userId").val();
		 var param = "userId=" + userId;
		
		 if(userId ==""){
			return ;
		 }
		 
		 var url = "<c:url value='/idCheck.do' />";
		 //alert(url);
		 $.ajax({
		  url : url,
		  type : "post",
		  data : param,
		  success : function(data) {
		  	//alert(data);
			//console.log(data);
			//console.log()
		   if(data == 1) {		//같은 아이디가 존재하는 경우			
		    $("#msgIdCheck").text("중복된 ID입니다.");
		    $("#msgIdCheck").attr("style", "color:#f00");
		   } else {				//같은 아이디가 존재하지 않는 경우
		    $("#msgIdCheck").text("사용 가능한 ID입니다.");
		    $("#msgIdCheck").attr("style", "color:#00f");
		   }
		  },
		  error:function(request,status,error){		//error로그찍기
		        alert("code = "+ request.status + " message = " + request.responseText + " error = " + error); 
		       }

		 }); 
	});
	
	
	//가입 버튼
	$("#submit").click(function(){
		if($("#userId").val()=="" ||  $("#msgIdCheck").text()!="사용 가능한 ID입니다."){
			$("#userId").focus();
			return ;
		}else if($("#userNm").val()==""){
			$("#userNm").focus();
			return ;
		}else if($("#emailAddr").val()=="" || $("#msgEmailCheck").text()!="이메일이 확인되었습니다."){
			$("#emailAddr").focus();
			return ;
		}else if($("#loginAcPwd").val()=="" || $("#msgPwdValidation").text()!=""){
			$("#loginAcPwd").focus();
			return ;
		}else if($("#loginAcPwd2").val()=="" || $("#msgPwdCheck").text()!="비밀번호가 확인되었습니다."){
			$("#loginAcPwd2").focus();
			return ;
		}else{//빈항목이 없고 모든 형식이 제대로 입력되면.
			
			 var param =  $("#login_form").serialize();
			 var url = "<c:url value='/join.do'/>";
			 //alert(url);
			 $.ajax({
			  url : url,
			  type : "post",
			  data : param,
			  beforeSend: function () {
					// 처리중이니 잠시 기다려 주십시요.
					showMsgBox("PRC", gMSG_PRC_WAIT);
			  },
			  success : function(data) {
				  alert("회원가입이 완료되었습니다. E-MAIL 인증 후 로그인 가능합니다.");
				  window.close();
			  },
			  error:function(request,status,error){		//error로그찍기
			        alert("code = "+ request.status + " message = " + request.responseText + " error = " + error); 
			       }

			 });  
		}
		
	});
		
	//팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
    $("div.pop_tit_close").click(function(){
//      	if ("${headerVO.popType}" == "I") {
//      		parent.closeLayerPop();
//      	} else {
     		window.close();
//      	}
     });
});


$(window).load(function() {
});



$(window).resize(function(){
});

	
function pwValidation() {
	//문자, 숫자, 특수문자를 포함한 9자리 이상여부 체크    		
	if(!isValidPassword($("#loginAcPwd").val())) {
		$("#msgPwdValidation").attr("style", "display:block;");
		$("#msgPwdValidation").text("비밀번호 형식은 문자, 숫자, 특수문자를 포함한 9자리 이상입니다.");
		$("#msgPwdValidation").attr("style", "color:#f00;");
    } else {
    	$("#msgPwdValidation").attr("style", "display:none;");
    	$("#msgPwdValidation").text("");
		$("#msgPwdValidation").attr("style", "color:#00f;");
    }
}

//문자, 숫자, 특수문자를 포함한 9자리 이상여부
function isValidPassword(pw){
	var check = /^(?=^.{9,}$)(?=.*\d)(?=.*[a-zA-Z])/;
	
	// 문자+숫자+9자리이상 여부
	if(!check.test(pw)){
		return false;
	}
	
	// 특수문자 여부
	if(!(new RegExp(/[^a-z|^0-9]/gi)).test(pw) && !(new RegExp(/[\^]/)).test(pw)){
		return false;
	}
	
	return true;
}

//비밀번호 확인
function pwdCheck(){
	
	if(isValidPassword($("#loginAcPwd").val())){//유효한 비밀번호 형식일 경우
		if($("#loginAcPwd").val() != $("#loginAcPwd2").val()){		//두비밀번호가 다른경우
			$("#msgPwdCheck").text("비밀번호를 다시 확인해주십시오.");
			$("#msgPwdCheck").attr("style", "color:#f00");
		}else{													//두비밀번호가 같은경우
			$("#msgPwdCheck").text("비밀번호가 확인되었습니다.");
			$("#msgPwdCheck").attr("style", "color:#00f");
		}
	}else{//유효한 비밀번호 형식이 아닌경우
		$("#msgPwdCheck").text("비밀번호 형식은 문자, 숫자, 특수문자를 포함한 9자리 이상입니다.");
		$("#msgPwdCheck").attr("style", "color:#f00");
	}
	
}

//이메일 형식 확인
function emailCheck(){
	var input = $("#emailAddr").val();
	var expression =  /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
	
	if(!expression.test(input)){		//이메일형식이 아닌 경우
		$("#msgEmailCheck").text("이메일 형식을 확인해주십시오.");
		$("#msgEmailCheck").attr("style", "color:#f00");
	}else{							//이메일 형식인 경우
		$("#msgEmailCheck").text("이메일이 확인되었습니다.");
		$("#msgEmailCheck").attr("style", "color:#00f");
	}
		
}
	

</script>
</head>

<body>
<div class="pop_tit" >
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt">WiseDQ 회원가입</div><!--데이터패턴 조회-->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div><!--창닫기-->
</div>


<div style="clear:both; height:10px;"><span></span></div>
<div class="lg_cont" >
<form role="form" method="post" id="login_form" name="login_form" autocomplete="off">

	<div style="clear:both; height:30px;"><span></span></div>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="회원가입" >
		<colgroup>
			<col style="width: 30%" >
			<col style="width: 70%">
		</colgroup>
		<tbody>
			<tr>
				<th  scope="row">
					<label class="col"  >아이디</label>
					<div style="clear:both; height:10px;"><span></span></div>
				</th>
				<td class="lg_form">
					<input  type="text" id="userId" name="userId">
					<button class="btn_rqst_new2" type="button" id="idCheck">중복확인</button> 
					<div style="clear:both; height:1px;"><span></span></div>
					<div id="msgIdCheck">아이디를 확인해주십시오.</div>
					<div style="clear:both; height:10px;"><span></span></div>
				</td>
			</tr>
			
			<tr>
				<th scope="row">
					<label class="col">이름</label>
				</th>
				<td class="lg_form">
					<input type="text" id="userNm" name="userNm">
					<div style="clear:both; height:15px;"><span></span></div>
				</td>
			</tr>
			
			<tr>
				<th scope="row">
					<label class="col">회사명</label>
				</th>
				<td class="lg_form">
					<select id="comCd"  name="comCd" class="wd98p">
						<option value="">선택</option>
						<option value="">개인</option>
						<c:forEach var="code" items="${comCd}" varStatus="status">
					    <option value="${code.codeCd}">${code.codeLnm}</option>
					    </c:forEach>
				    </select>
					<div style="clear:both; height:15px;"><span></span></div>
				</td>
			</tr>
	
	
			<tr>
				<th scope="row">
					<label class="col">이메일주소</label>
					<div style="clear:both; height:10px;"><span></span></div>
				</th>
				<td class="lg_form">
					<input type="text" id="emailAddr" name="emailAddr" onkeyup="emailCheck();"> 
					<div style="clear:both; height:1px;"><span></span></div>
					<div id="msgEmailCheck">이메일을 확인해주십시오.</div>
					<div style="clear:both; height:10px;"><span></span></div>
				</td>
			</tr>
			
			<tr>
				<th scope="row">
					<label class="col">휴대폰번호</label>
					<div style="clear:both; height:10px;"><span></span></div>
				</th>
				<td class="lg_form">
					<input type="text" id="userHtelno" name="userHtelno"> 
					<div style="clear:both; height:10px;"><span></span></div>
				</td>
			</tr>
			
	
			<tr>
				<th scope="row">
					<label class="col" >비밀번호</label>
					<div style="clear:both; height:10px;"><span></span></div>
				</th>
				<td class="lg_form">
					<input type="password" id="loginAcPwd" name="loginAcPwd" onkeyup="pwValidation();">
					<div style="clear:both; height:1px;"><span></span></div>
					<div id="msgPwdValidation">비밀번호를 확인해주십시오.</div>
					<div style="clear:both; height:10px;"><span></span></div>
				</td>
			</tr>
	
	
			<tr>
				<th scope="row">
					<label class="col" >비밀번호확인</label>
					<div style="clear:both; height:10px;"><span></span></div>
				</th>
				<td class="lg_form">
					<input type="password" id="loginAcPwd2" name="loginAcPwd2" onkeyup="pwdCheck();"> 
					<div style="clear:both; height:1px;"><span></span></div>
					<div id="msgPwdCheck">비밀번호를 확인해주십시오.</div>
				</td>
			</tr>
			
		</tbody>
	</table>
	<div style="clear:both; height:30px;"><span></span></div>
	</form>
</div>

	<!-- 가입 버튼 -->
	<div style="clear:both; height:10px;"><span></span></div>
	<p align="center">
		<button class="btn_rqst_new2" id="submit" type="button">가입</button>
	</p>
	

</body>
</html>