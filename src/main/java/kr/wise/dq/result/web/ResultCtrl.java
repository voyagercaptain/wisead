package kr.wise.dq.result.web;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.schedule.service.WamShdLogVO;
import kr.wise.commons.handler.PoiHandler;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgService;
import kr.wise.dq.criinfo.anatrg.service.WaaExpTbl;
import kr.wise.dq.result.service.ResultService;
import kr.wise.dq.result.service.ResultVO;
import kr.wise.portal.dashboard.service.TotalDashService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : ResultCtrl.java
 * 3. Package  : kr.wise.dq.result.web
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 6. 11. 오전 9:48:36
 * </PRE>
 */ 
@Controller
public class ResultCtrl {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, Object> codeMap;
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private CodeListService codeListService;
	
	@Inject
	private TotalDashService totalDashService;
	
	@Inject
	private MessageSource message;

	@Inject
	private ResultService resultService;
	
	@Inject
	private AnaTrgService anaTrgService;	
	
	@Value("#{configure['wiseda.user.div.yn']}")     
	String userDivYn;
	
	@Value("#{configure['wiseda.cmn.mng.user.id']}")     
	String cmnMngUserId;

	/** 프로파일 품질추이 폼 */
	@RequestMapping("/dq/result/result_lst.do")
	public String formPage() throws Exception {
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {

    	}
		return "/dq/result/result_lst";
	}
	
	
	/** 프로파일 품질추이 조회 - IBSheet JSON */
	@RequestMapping("/dq/result/resultList.do")
	@ResponseBody
	public IBSheetListVO<ResultVO> selectList(@ModelAttribute ResultVO search) {
		logger.debug("{}", search);
		
		if("Y".equals(userDivYn)) {
			LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
			search.setMngUserId(UtilString.null2Blank(user.getUniqId()));
		} else {
			search.setMngUserId(cmnMngUserId);
		}
		
		List<ResultVO> list = resultService.getResultList(search);
		
		return new IBSheetListVO<ResultVO>(list, list.size());
	}
	
	@RequestMapping("/dq/result/poiDown.do")
    public void poiDown (HttpServletResponse response, @RequestParam String dbmsId, @RequestParam String schId
    		           , @RequestParam String dbmsLnm, @RequestParam String schLnm, @RequestParam String poiFlag
    		           , @RequestParam String gap, @RequestParam String tot
    		           , @RequestParam String tblCnt, @RequestParam String tblNcnt
    		           , @RequestParam String colCnt, @RequestParam String colNcnt
    		           , @RequestParam String pdmColCnt, @RequestParam String pdmColNcnt
    		           , @RequestParam String pdmTblCnt, @RequestParam String pdmTblNcnt) throws Exception{
    	logger.debug("poiDown clicked");
    	
    	switch(poiFlag) {
    	case "result":
    		resultDown(response, dbmsId, schId, dbmsLnm, schLnm);
    		break;
    	case "errResult":
    		resultErrDown(response, dbmsId, schId, dbmsLnm, schLnm);
    		break;    		
    	case "stnd":
    		stndDown(response, dbmsId, schId, dbmsLnm, schLnm, gap, tot);
    		break;
    	case "model":
    		modelDown(response, dbmsId, schId, dbmsLnm, schLnm, tblCnt, tblNcnt
 		           , colCnt, colNcnt
 		           , pdmColCnt, pdmColNcnt
 		           , pdmTblCnt, pdmTblNcnt);
    		break;
    	}
    	
    }
	
	private void resultDown(HttpServletResponse response, String dbmsId, String schId
	           , String dbmsLnm, String schLnm) throws Exception {
		//list를 추출하기 위한 vo

    	WamShdLogVO  wamShdLogVO = new WamShdLogVO();
    	ResultVO search = new ResultVO();
    	search.setDbConnTrgId(dbmsId); 
    	search.setDbSchId(schId);
    	
		if("Y".equals(userDivYn)) {
			LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
			search.setMngUserId(UtilString.null2Blank(user.getUniqId()));
		} else {
			search.setMngUserId(cmnMngUserId);
		}
    	

//    	String excelType = "2"; //.xlsx
    	String excelType = "1"; //.xls
//    	String excelType = "3"; //.xlsx
    	PoiHandler poiHandler = new PoiHandler(excelType);
    	
    	ResultVO term = resultService.getResultTerm(search);
    	term.setTotCnt(resultService.getTotCnt(search));
    	term.setErrCnt(resultService.getRunCnt(search));
    	
    	if(dbmsId == null || dbmsId.equals("")) {
    		term.setDbConnTrgLnm("전체");
    		term.setDbSchLnm("전체");
    	} else {
    		term.setDbConnTrgLnm(dbmsLnm);
    		term.setDbSchLnm(schLnm);
    	}
    	
    	List<ResultVO> dataList = null;	//조회 목록
    	BigDecimal totTrgTblCnt = null; //전체 테이블 개수
    	BigDecimal expTrgTblCnt = null; //제외 테이블 개수
    	List<ResultVO> tblList = null;	//테이블 목록
    	List<ResultVO> dmnRule = null;	//도메인규칙
    	List<ResultVO> execList = null;	//실행 목록
    	List<ResultVO> errList = null;	//오류 목록
//    	WamShdLogVO scheLogCnt = null;
//    	BigDecimal exeSchColTotCnt = null;	//진단실행전체 카운트
//    	BigDecimal exeSchColSucessCnt = null;	//진단실행 성공 카운트
    	ResultVO execPreTotCntVo = null;		
    	ResultVO execPreExecCntVo = null;
    	BigDecimal exeSchColTotCnt = null;		//검증 가능 컬럼 수 
    	BigDecimal exeSchColSucessCnt = null;	//검증 성공 컬럼 수
    	
    	dataList=resultService.getResultList(search);
    	tblList=resultService.getTblList(search);
//    	dmnRule=resultService.getDmnRule(search);
    	execList=resultService.getExecList(search);
    	errList=resultService.getErrList(search);
    	
    	
    	
    	if(!UtilString.null2Blank(search.getDbConnTrgId()).equals("")){
    		wamShdLogVO.setDbConnTrgId(search.getDbConnTrgId());
    	}
    	
    	if(!UtilString.null2Blank(search.getDbSchId()).equals("")){
    		wamShdLogVO.setDbSchId(search.getDbSchId());
    	}
    	
//    	scheLogCnt = resultService.getScheduleTotColJobCnt(wamShdLogVO);
//    	exeSchColTotCnt = new BigDecimal(scheLogCnt.getJobCnt(),MathContext.DECIMAL64); //컬럼분석 전체수
//    	exeSchColSucessCnt = new BigDecimal(scheLogCnt.getSucCnt(), MathContext.DECIMAL64);//컬럼분석성공수
    	
    	execPreExecCntVo = resultService.getExecPreCnt(search);
    	exeSchColTotCnt = resultService.getExecTotCnt(search);		//검증 가능 컬럼수
    	exeSchColSucessCnt = execPreExecCntVo.getColCnt();		//검증 성공 컬럼수
    	
    	
    	WaaExpTbl waaExpTbl = new WaaExpTbl();
    	if(!UtilString.null2Blank(search.getDbConnTrgId()).equals("")){
    		waaExpTbl.setDbConnTrgId(search.getDbConnTrgId());
    	}
    	
    	if(!UtilString.null2Blank(search.getDbSchId()).equals("")){
    		waaExpTbl.setDbSchId(search.getDbSchId());
    	}    	
    	totTrgTblCnt = anaTrgService.selectTrgTblCnt(waaExpTbl);
    	waaExpTbl.setExpYn("N"); //제외테이블로 설정
    	expTrgTblCnt = anaTrgService.selectTrgTblCnt(waaExpTbl);
    	
    	List<ResultVO> dqiErrList = null;

    	dqiErrList = new ArrayList<ResultVO>();
    	
    	ResultVO dqiErrVo = null;
    	
//    	String[] dqiList = {"관계키", "필수값", "금액", "날짜", "수량", "여부", "율", "코드"
//    						, "계산 및 집계", "시간순서", "컬럼 간 논리관계", "명칭", "번호"};
    	String[] dqiList = {"참조관계", "날짜 도메인", "코드 도메인", "금액 도메인", "수량 도메인", "율 도메인", "여부 도메인", "시간순서 일관성"
				, "컬럼 간 논리관계 일관성", "선후관계 정확성", "계산식", "번호 도메인"};    	
    	for(int i=0; i<dqiList.length; i++) {
    		search.setDqiLnm(dqiList[i]);
    		dqiErrVo = new ResultVO();
    		dqiErrVo.setErrList(resultService.getErrListByDqi(search));
    		dqiErrVo.setErrColCnt(resultService.getErrColCntByDqi(search));
    		int colCnt = 0;
    		colCnt = resultService.getErrDataMaxColCnt(search);
    		int colCnt2 = colCnt-1;
    		String pcolnm = "";
    		String bcolnm = "";
    		
    		//CONCAT(CONCAT(A, ','), B)
    		for(int j=0; j<(colCnt2-1)*2; j++) {
    			pcolnm += "CONCAT(";
    			bcolnm += "CONCAT(";
    		}
    		for(int j=0; j<colCnt2; j++) {
    			if(j==0) {
    				pcolnm += "PERR.COL_NM" + (j+1);
    				bcolnm += "BERR.COL_NM" + (j+1);
    			} else {
    				pcolnm += ",', '),PERR.COL_NM" + (j+1) + ")";
    				bcolnm += ",', '),BERR.COL_NM" + (j+1) + ")";
    			}
    		}
    		
    		if(colCnt == 0) {
    			pcolnm += "'' AS COL_NM1, 0 AS ERR_CNT";
        		bcolnm += "'' AS COL_NM1, 0 AS ERR_CNT";
    		} else if(colCnt == 2) {
    			pcolnm = "PERR.COL_NM1 AS COL_NM1, PERR.COL_NM2 AS ERR_CNT";
        		bcolnm = "BERR.COL_NM1 AS COL_NM1, BERR.COL_NM2 AS ERR_CNT";
    		} else if(colCnt == 1) {
    			pcolnm += "PERR.COL_NM1 AS COL_NM1, 0 AS ERR_CNT";
        		bcolnm += "BERR.COL_NM1 AS COL_NM1, 0 AS ERR_CNT";
    		} else {
    			pcolnm += " AS COL_NM1, PERR.COL_NM" + colCnt + " AS ERR_CNT";
        		bcolnm += " AS COL_NM1, BERR.COL_NM" + colCnt + " AS ERR_CNT";
    		}
    		
    		search.setPcolnm(pcolnm);
    		search.setBcolnm(bcolnm);
    		
    		BigDecimal cnt = new BigDecimal(colCnt);
    		dqiErrVo.setColCnt(cnt);
    		dqiErrVo.setDqiLnm(dqiList[i]);
    		dqiErrVo.setErrData(resultService.getErrDataByDqi(search));
    		
    		dqiErrList.add(dqiErrVo);
    	}
    	
    	
		poiHandler.createExcelResult(dataList, totTrgTblCnt, expTrgTblCnt, term ,exeSchColSucessCnt,exeSchColTotCnt, tblList, dmnRule, execList, errList, dqiErrList, "result");
		poiHandler.downExcel("진단종합현황", response);
	}
	
	
	private void resultErrDown(HttpServletResponse response, String dbmsId, String schId
	           , String dbmsLnm, String schLnm) throws Exception {
		//list를 추출하기 위한 vo
		 	ResultVO search = new ResultVO();
		 	search.setDbConnTrgId(dbmsId); 
		 	search.setDbSchId(schId);
	    	
			if("Y".equals(userDivYn)) {
				LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
				search.setMngUserId(UtilString.null2Blank(user.getUniqId()));
			} else {
				search.setMngUserId(cmnMngUserId);
			}
			
		// 	String excelType = "2"; //.xlsx
		 	String excelType = "1"; //.xls
		// 	String excelType = "3"; //.xlsx
		 	PoiHandler poiHandler = new PoiHandler(excelType);
		 	
		 	ResultVO term = resultService.getResultTerm(search);
		 	term.setTotCnt(resultService.getTotCnt(search));
		 	term.setErrCnt(resultService.getRunCnt(search));
		 	
		 	if(dbmsId == null || dbmsId.equals("")) {
		 		term.setDbConnTrgLnm("전체");
		 		term.setDbSchLnm("전체");
		 	} else {
		 		term.setDbConnTrgLnm(dbmsLnm);
		 		term.setDbSchLnm(schLnm);
		 	}
		 	
		 	List<ResultVO> dataList = null;	//조회 목록
		 	List<ResultVO> tblList = null;	//테이블 목록
		 	List<ResultVO> dmnRule = null;	//도메인규칙
		 	List<ResultVO> execList = null;	//실행 목록
		 	List<ResultVO> errList = null;	//오류 목록
		 	
		 	dataList=resultService.getResultList(search);
		 	tblList=resultService.getTblList(search);
		// 	dmnRule=resultService.getDmnRule(search);
		 	execList=resultService.getExecList(search);
		 	errList=resultService.getErrList(search);
		 	
		 	List<ResultVO> dqiErrList = null;
		
		 	dqiErrList = new ArrayList<ResultVO>();
		 	
		 	ResultVO dqiErrVo = null;
		 	
		// 	String[] dqiList = {"관계키", "필수값", "금액", "날짜", "수량", "여부", "율", "코드"
		// 						, "계산 및 집계", "시간순서", "컬럼 간 논리관계", "명칭", "번호"};
		 	String[] dqiList = {"참조관계", "날짜 도메인", "코드 도메인", "금액 도메인", "수량 도메인", "율 도메인", "여부 도메인", "시간순서 일관성"
						, "컬럼 간 논리관계 일관성", "선후관계 정확성", "계산식", "번호 도메인"};    	
		 	for(int i=0; i<dqiList.length; i++) {
		 		search.setDqiLnm(dqiList[i]);
		 		dqiErrVo = new ResultVO();
		 		dqiErrVo.setErrList(resultService.getErrListByDqi(search));
		 		int colCnt = 0;
		 		colCnt = resultService.getErrDataMaxColCnt(search);
		 		int colCnt2 = colCnt-1;
		 		String pcolnm = "";
		 		String bcolnm = "";
		 		
		 		//CONCAT(CONCAT(A, ','), B)
		 		for(int j=0; j<(colCnt2-1)*2; j++) {
		 			pcolnm += "CONCAT(";
		 			bcolnm += "CONCAT(";
		 		}
		 		for(int j=0; j<colCnt2; j++) {
		 			if(j==0) {
		 				pcolnm += "PERR.COL_NM" + (j+1);
		 				bcolnm += "BERR.COL_NM" + (j+1);
		 			} else {
		 				pcolnm += ",', '),PERR.COL_NM" + (j+1) + ")";
		 				bcolnm += ",', '),BERR.COL_NM" + (j+1) + ")";
		 			}
		 		}
		 		
		 		if(colCnt == 0) {
		 			pcolnm += "'' AS COL_NM1, 0 AS ERR_CNT";
		     		bcolnm += "'' AS COL_NM1, 0 AS ERR_CNT";
		 		} else if(colCnt == 2) {
		 			pcolnm = "PERR.COL_NM1 AS COL_NM1, PERR.COL_NM2 AS ERR_CNT";
		     		bcolnm = "BERR.COL_NM1 AS COL_NM1, BERR.COL_NM2 AS ERR_CNT";
		 		} else if(colCnt == 1) {
		 			pcolnm += "PERR.COL_NM1 AS COL_NM1, 0 AS ERR_CNT";
		     		bcolnm += "BERR.COL_NM1 AS COL_NM1, 0 AS ERR_CNT";
		 		} else {
		 			pcolnm += " AS COL_NM1, PERR.COL_NM" + colCnt + " AS ERR_CNT";
		     		bcolnm += " AS COL_NM1, BERR.COL_NM" + colCnt + " AS ERR_CNT";
		 		}
		 		
		 		search.setPcolnm(pcolnm);
		 		search.setBcolnm(bcolnm);
		 		
		 		BigDecimal cnt = new BigDecimal(colCnt);
		 		dqiErrVo.setColCnt(cnt);
		 		dqiErrVo.setDqiLnm(dqiList[i]);
		 		dqiErrVo.setErrData(resultService.getErrDataByDqi(search));
		 		
		 		dqiErrList.add(dqiErrVo);
		 	}
 	
 	
		poiHandler.createExcelResult(dataList, null, null, term, null, null, tblList, dmnRule, execList, errList, dqiErrList, "errResult");
		poiHandler.downExcel("진단에러데이터현황", response);
	}	
	
	
	
	private void stndDown(HttpServletResponse response, String dbmsId, String schId
	           , String dbmsLnm, String schLnm, String gap, String tot) throws Exception {
		//list를 추출하기 위한 vo
 	ResultVO search = new ResultVO();
 	search.setDbConnTrgId(dbmsId); 
 	search.setDbSchId(schId);

// 	String excelType = "2"; //.xlsx
 	String excelType = "1"; //.xls
// 	String excelType = "3"; //.xlsx
 	PoiHandler poiHandler = new PoiHandler(excelType);
 	
 	ResultVO term = resultService.getStndTerm(search);
 	term.setTotCnt(new BigDecimal(tot.trim()==null?"0":tot.trim()));
 	term.setErrCnt(new BigDecimal(gap.trim()==null?"0":gap.trim()));
 	
 	if(dbmsId == null || dbmsId.equals("")) {
 		term.setDbConnTrgLnm("전체");
 		term.setDbSchLnm("전체");
 	} else {
 		term.setDbConnTrgLnm(dbmsLnm);
 		term.setDbSchLnm(schLnm);
 	}
 	
 	List<ResultVO> dataList = null;	//조회 목록
 	
// 	dataList=resultService.getResultList(search);
 	
 	
		poiHandler.createExcelStnd(term);
		poiHandler.downExcel("표준준수현황", response);
	}
	
	private void modelDown(HttpServletResponse response, String dbmsId, String schId
	           , String dbmsLnm, String schLnm, String tblCnt, String tblNcnt, String colCnt, String colNcnt, String pdmColCnt, String pdmColNcnt, String pdmTblCnt, String pdmTblNcnt) throws Exception {
		//list를 추출하기 위한 vo
 	ResultVO search = new ResultVO();
 	search.setDbConnTrgId(dbmsId); 
 	search.setDbSchId(schId);

// 	String excelType = "2"; //.xlsx
 	String excelType = "1"; //.xls
// 	String excelType = "3"; //.xlsx
 	PoiHandler poiHandler = new PoiHandler(excelType);
 	
 	ResultVO term = resultService.getModelTerm(search);
 	
 	if(dbmsId == null || dbmsId.equals("")) {
 		term.setDbConnTrgLnm("전체");
 		term.setDbSchLnm("전체");
 	} else {
 		term.setDbConnTrgLnm(dbmsLnm);
 		term.setDbSchLnm(schLnm);
 	}
 	
		poiHandler.createExcelModel(term, tblCnt, tblNcnt, colCnt, colNcnt, pdmColCnt, pdmColNcnt, pdmTblCnt, pdmTblNcnt);
		poiHandler.downExcel("구조품질현황", response);
	}
	
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//시스템영역 코드리스트 JSON
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));

		//공통코드 - IBSheet Combo Code용
//		codeMap.put("regTypCdibs", regTypCd);
//		codeMap.put("vrfcTyp", cmcdCodeService.getCodeList("VRFC_TYP"));
//		codeMap.put("vrfcTypibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("VRFC_TYP")));
		
		//프로파일 종류코드
		codeMap.put("prfKndCd", cmcdCodeService.getCodeList("PRF_KND_CD"));
		codeMap.put("prfKndCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PRF_KND_CD")));
		
		//진단대상
		List<CodeListVo> connTrgDbms = codeListService.getCodeList(CodeListAction.connTrgDbms);

		codeMap.put("connTrgDbmsCd", connTrgDbms);		
		codeMap.put("connTrgDbmsibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(connTrgDbms)));
		
		//스키마
		List<CodeListVo> schDbSchNm = codeListService.getCodeList(CodeListAction.connTrgSch);
		
		codeMap.put("schDbSchNm", schDbSchNm);
		codeMap.put("schDbSchNmibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(schDbSchNm)));
		
		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSch", connTrgSch);
				
		return codeMap;
	}
	
}
