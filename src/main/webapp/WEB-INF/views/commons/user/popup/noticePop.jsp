<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><s:message code="USER.INFO.CHG" /></title> <!-- 사용자 정보변경 -->
    <script language="JavaScript" type="text/javascript" src='<c:url value="/js/security/rsa.js" />'></script>
    <script type="text/javascript">
        //쿠키설정
        function setCookie( name, value, expiredays ) {
            var todayDate = new Date();
            todayDate.setDate( todayDate.getDate() + expiredays );
            document.cookie = name + '=' + escape( value ) + '; path=/; expires=' + todayDate.toGMTString() + ';'
        }

        //쿠키 불러오기
        function getCookie(name)
        {
            var obj = name + "=";
            var x = 0;
            while ( x <= document.cookie.length )
            {
                var y = (x+obj.length);
                if ( document.cookie.substring( x, y ) == obj )
                {
                    if ((endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
                        endOfCookie = document.cookie.length;
                    return unescape( document.cookie.substring( y, endOfCookie ) );
                }
                x = document.cookie.indexOf( " ", x ) + 1;

                if ( x == 0 ) break;
            }
            return "";
        }

        //닫기 버튼 클릭시
        function closeWin(key)
        {
            if($("#todaycloseyn").prop("checked"))
            {
                console.log("aaaaa");
                setCookie('divpop'+key, 'Y' , 1 );
            }

            parent.closeLayerPop();
            //$("#divpop"+key+"").hide();
        }



        var interval = "";
        // var usergJson = ${codeMap.userglist} ;	//시스템영역 코드 리스트 JSON...

        $(document).ready(function() {

// 		alert("document.ready");
                //alert(usergTypCd);
                //마우스 오버 이미지 초기화
                //imgConvert($('div.tab_navi a img'));

                //버튼 초기화...
                //$("#btnTreeNew, #btnSave, #btnDelete").hide();

                //그리드 초기화
//         initGrid();
                // 조회 Event Bind
                // $("#btnSearch").click(function(){ doAction("Search");  });

                // 추가 Event Bind
                // $("#btnNew").click(function(){ doAction("New");  });

                // 저장 Event Bind
                $("#btnSave").click(function(){ doAction("Save");  });

                // 삭제 Event Bind
                //$("#btnDelete").click(function(){ doAction("Delete");  });

                // 엑셀내리기 Event Bind
                //$("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

                // 엑셀업로 Event Bind
                // $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } );

                //======================================================
                // 셀렉트 박스 초기화
                //======================================================
                // 시스템영역
//         create_selectbox(usergJson, $("#usergId"));

                $("#divInputBtn").show();

                //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
                $("div.pop_tit_close").click(function(){

                    //alert("15.10.29");
                    parent.closeLayerPop();
                    //iframe 형태의 팝업일 경우
                    /* if ("${search.popType}" == "I") {
        		parent.closeLayerPop();
        	} else {
        		window.close();
        	} */

                });
                //파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
                //  setautoComplete($("#frmSearch #userNm"), "USRNM");

                //폼 저장 버튼 초기화...
                $('#btnGridSave').click(function(event){
                    event.preventDefault();  //브라우저 기본 이벤트 제거...
                    parent.closeLayerPop();

                }).show();

                //폼 초기화 버튼 초기화...
                $('#btnReset').click(function(event){
                    event.preventDefault();  //브라우저 기본 이벤트 제거...
                    var url  = "<c:url value='/commons/user/initPwd.do'/>";
                    var param =  $("#frmSearch").serialize();
                    ajax2Json(url, param, callback);
                });

            }
        );


        function callback(){

            var	flag = '${sessionScope.loginVO.chgPwd}';
            if (flag === 'N') {
                alert("비밀번호가 변경되었습니다.\n로그인 화면으로 이동합니다. 다시 로그인해주세요."); /* 수정되었습니다. */
                parent.location.href="<c:url value="/logout" />";
            }
            else {
                alert("<s:message code='MSG.REVS' />"); /* 수정되었습니다. */
                parent.closeLayerPop();
            }

        }

        function doAction(sAction)
        {

            switch(sAction)
            {

                case "Save" :
                    //TODO 공통으로 처리...

                    /* var rows = grid_sheet.FindStatusRow("I|U|D");
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
            IBSpostJson(url, param, ibscallback); */
                    break;


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



        /*
            row : 행의 index
            col : 컬럼의 index
            value : 해당 셀의 value
            x : x좌표
            y : y좌표
        */



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
    <div class="pop_tit_txt">공지사항</div>

    <div class="pop_tit_close"><a href="#"><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->

    <div class="pop_content">


        <!-- 메뉴 메인 제목 -->
        <%-- <div style="clear:both; height:5px;"><span></span></div> --%>

        <!-- 검색조건 입력폼 -->
        <div id="search_div">
            <div class="stit">현재 임시저장 상태이며 검증은 8월부터 진행예정입니다.</div>
            <div style="clear:both; height:5px;"><span></span></div>

            <form id="frmSearch" name="frmSearch" method="post">
                <fieldset>
                    <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
                    <div class="tb_basic2">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="">
                            <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                            <colgroup>
                                <col style="width:20%;" />
                                <col style="width:30%;" />
                                <col style="width:20%;" />
                                <col style="width:30%;" />
                            </colgroup>

                            <tbody>


                            </tbody>
                        </table>

                </fieldset>
                <!-- 문자, 숫자, 특수문자를 포함한 9자리 이상여부 -->
                <div class="tb_comment">
                    <br/>  <br/><br/> <!-- 처음 로그인하는 사용자의 경우 사용할 비밀번호를 변경하여야 합니다. -->

                </div>
            </form>
            <form id="secureFrm" name="secureFrm" method="post">
                <input type="hidden" name="securedUsername" id="securedUsername" />
                <input type="hidden" name="securedPassword" id="securedPassword" />
            </form>
            <!-- 조회버튼영역  -->
            <div style="clear:both; height:20px;"><span></span></div>
            <%-- <tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstInput.jsp" /> --%>
            <div id="divInputBtn" style="text-align: center; display: none;">
                <input type='checkbox' name='chkbox' id='todaycloseyn' value='Y'>오늘 하루 이 창을 열지 않음
                <button class="btn_frm_save" type="button" id="btnClosebtn" name="btnClosebtn" onclick="javascript:closeWin(1);">닫기</button>
            </div>

        </div>
</body>
</html>