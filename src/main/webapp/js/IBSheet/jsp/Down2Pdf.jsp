<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"
%><%@ page import="java.io.*" 
%><%@ page import="com.ibleaders.ibsheet7.ibsheet.pdf.Down2Pdf" 
%><%@ page import="com.ibleaders.ibsheet7.util.Synchronizer"
%><%
	Down2Pdf ibPdf = new Down2Pdf(request, response);
	
	//====================================================================================================
	// [ 사용자 환경 설정 #1 ]
	//====================================================================================================
	// Html 페이지의 인코딩이 utf-8 로 구성되어 있으면 "ibPdf.setPageEncoding("utf-8");" 로 설정하십시오.
	//====================================================================================================
	ibPdf.setPageEncoding("utf-8");

	//====================================================================================================
	// [ 사용자 환경 설정 #2 ]
	//====================================================================================================
	// ttf 파일이 위치한 폴더를 설정하십시오.
	//====================================================================================================
	ibPdf.setFontFolder("C:/ide/basemeta/wiseda_base/src/main/webapp/font");

	boolean bToken = false;
	
	
	
	try {
	    
	    response.reset(); 

        // 서버에서 병행처리를 허용할 최대 동시 작업 갯수를 설정한다.
        Synchronizer.init(5);
        
        // 싱크 처리 객체로 부터 처리권한을 확인한다.
        // 인자를 true로 설정하는 경우 : 싱크 처리 객체에서 자원을 사용가능해질때까지 최대 30초 동안 기다렸다가 자원 사용이 가능해졌을때 권한을 할당 후 true를 반환한다.
        // 인자를 false로 설정하는 경우 : 자원 사용여부를 확인후 즉시 반환. 사용 가능하면 할당 후 true를 반환하고, 사용이 불가능한 경우 false를 반환한다.
        bToken = Synchronizer.use(false);
//         bToken = false;

 		// 싱크 객체로 부터 권한을 정상 할당 받은 경우에만 엑셀 작업을 진행한다.
        if (bToken) {

			ibPdf.down2pdf();
		
			out.clear();
			out = pageContext.pushBody();
			
			// 다운 완료 후 싱크 객체로 할당받은 권한을 반환한다.
            Synchronizer.release();
            bToken = false;
			
        } else {
//          response.setHeader("Content-Type", "text/html;charset=utf-8");
	        response.setContentType("text/html;charset=utf-8");
	        response.setCharacterEncoding("utf-8");
	        response.setHeader("Content-Disposition", "");
	        
         out.println("<script>alert('파일 다운로드중 에러가 발생하였습니다.[Server Busy]'); </script>");
     }

	} catch (Exception e) {
		out.println("<script>try{var targetWnd = null; if(opener!=null) {targetWnd = opener;} else {targetWnd = parent;} targetWnd.Grids[targetWnd.gTargetExcelSheetID].finishDownload(); targetWnd.Grids[targetWnd.gTargetExcelSheetID].ShowAlert('PDF 다운로드중 에러가 발생하였습니다.', 'U');}catch(e){}</script>");
		
		e.printStackTrace();
	} catch (Error e) {
		out.println("<script>try{var targetWnd = null; if(opener!=null) {targetWnd = opener;} else {targetWnd = parent;} targetWnd.Grids[targetWnd.gTargetExcelSheetID].finishDownload(); targetWnd.Grids[targetWnd.gTargetExcelSheetID].ShowAlert('PDF 다운로드중 에러가 발생하였습니다.', 'U');}catch(e){}</script>");

		e.printStackTrace();
	} finally {
	    if (bToken) {
            Synchronizer.release();
            bToken = false;
	    }
	}
%>