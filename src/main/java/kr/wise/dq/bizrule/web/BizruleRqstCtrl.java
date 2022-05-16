/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : BizruleRqstCtrl.java
 * 2. Package : kr.wise.dq.bizrule.web
 * 3. Comment : 
 * 4. 작성자  : meta
 * 5. 작성일  : 2014. 5. 17. 오후 1:56:30
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    meta : 2014. 5. 17. :            : 신규 개발.
 */
package kr.wise.dq.bizrule.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.advisor.prepare.dataset.service.WadDataSet;
import kr.wise.advisor.prepare.dataset.service.WadDataSetMapper;
import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.damgmt.approve.service.WaaRqstBizApr;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.ConnectionHelper;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.bizrule.service.BizruleRqstService;
import kr.wise.dq.bizrule.service.BizruleService;
import kr.wise.dq.bizrule.service.WamBrMstr;
import kr.wise.dq.bizrule.service.WaqBrMstr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : BizruleRqstCtrl.java
 * 3. Package  : kr.wise.dq.bizrule.web
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 5. 17. 오후 1:56:30
 * </PRE>
 */
@Controller
public class BizruleRqstCtrl {

	
	Logger logger = LoggerFactory.getLogger(getClass());

	static class WaqBrMstrs extends HashMap<String, ArrayList<WaqBrMstr>> { }
	static class WamBrMstrs extends HashMap<String, ArrayList<WamBrMstr>> { }

	private Map<String, Object> codeMap;
	
	@Inject
	private CodeListService codelistService;

	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private EgovIdGnrService requestIdGnrService;
	
	@Inject
	private RequestMstService requestMstService;
	
	@Inject
	private BizruleRqstService bizruleRqstService;
	
	@Inject
	private BizruleService bizruleService;
	
	@Inject
	private MessageSource message;
	
	@Inject
	private WadDataSetMapper targetDbms;
	
	@Value("#{configure['wiseda.user.div.yn']}")     
	String userDivYn;
	
	@Value("#{configure['wiseda.cmn.mng.user.id']}")     
	String cmnMngUserId;
	
	/* 결재라인조회*/
	@Inject
	private ApproveLineServie approveLineServie;
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	
	//업무규칙 요청 화면
	@RequestMapping("/dq/bizrule/bizrule_rqst.do")
	public String formpage(WaqMstr reqmst, ModelMap model) throws Exception {
		logger.debug("WaqMstr:{}", reqmst);

    	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("BRA");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else {
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

    	//결재라인 결재레벨 조회
    	//결재레벨 미존재 시 검증 후 등록 버튼 활성화
    	WaaRqstBizApr aprSearch = new WaaRqstBizApr();
    	aprSearch.setBizDcd(reqmst.getBizDcd());
    	List<WaaRqstBizApr> list = approveLineServie.getapprovelinelist(aprSearch);
    	logger.debug("list SIZE : ", list.size());
    	reqmst.setAprLvl(list.size());

    	model.addAttribute("waqMstr", reqmst);
    	
		return "/dq/bizrule/bizrule_rqst";
	}
	
	/** 업무규칙 요청 리스트 조회 - IBSheet JSON @return meta */
    @RequestMapping("/dq/bizrule/getbizrulerqstlist.do")
	@ResponseBody
	public IBSheetListVO<WaqBrMstr> getBizruleRqstlist(@ModelAttribute WaqMstr search) {
		logger.debug("searchVO:{}", search);

		List<WaqBrMstr> list = bizruleRqstService.getBizruleRqstist(search);

		return new IBSheetListVO<WaqBrMstr>(list, list.size());
	}
    
    /** 업무규칙 요청 리스트 조회 - IBSheet JSON @return meta */
    @RequestMapping("/dq/bizrule/getbizrulelist.do")
	@ResponseBody
	public IBSheetListVO<WamBrMstr> getBizrulelist(@ModelAttribute WamBrMstr search) {
		logger.debug("searchVO:{}", search);
		if("Y".equals(userDivYn)) {
			LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
			search.setMngUserId(UtilString.null2Blank(user.getUniqId()));
		} else {
			search.setMngUserId(cmnMngUserId);
		}
		
		List<WamBrMstr> list = bizruleService.getBrList(search);

		return new IBSheetListVO<WamBrMstr>(list, list.size());
	}
    
    /** 메뉴 저장 단건 -  결과는 IBSResult Json 리턴 
	 * @throws Exception */
    @RequestMapping("/dq/bizrule/regBizrule.do")
    @ResponseBody
    public IBSResultVO<WamBrMstr> regBizrule(WamBrMstr saveVO, String saction, Locale locale) throws Exception {
    	
    	logger.debug("saveVO:{}, saction:{}", saveVO, saction);
    	int result = 0;
    	String resmsg = "";
    	
		if("Y".equals(userDivYn)) {
			LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
			saveVO.setMngUserId(UtilString.null2Blank(user.getUniqId()));
		} else {
			saveVO.setMngUserId(cmnMngUserId);
		}
		
    	if(!sqlVrfc(saveVO.getDbSchId(), saveVO.getCntSql())) {
    		result = -1;
    		resmsg = "건수 SQL을 확인해주세요.";
    		logger.debug(resmsg);
    	} else if(!sqlVrfc(saveVO.getDbSchId(), saveVO.getAnaSql())) {
    		result = -1;
    		resmsg = "분석 SQL을 확인해주세요.";
    		logger.debug(resmsg);
    	} else {
    		result = bizruleRqstService.regBr(saveVO, saction);
    		
    		if(result > 0) {
    			result = 0;
    			resmsg = message.getMessage("MSG.SAVE", null, locale);
    		} else {
    			result = -1;
    			resmsg = message.getMessage("ERR.SAVE", null, locale);
    		}
    	}

    	String action = WiseMetaConfig.IBSAction.REG.getAction();

    	return new IBSResultVO<WamBrMstr>(saveVO, result, resmsg, action);
    }
    
    /** 업무규칙 삭제 
	 * @throws Exception */
	@RequestMapping("/dq/bizrule/delBrList.do")
	@ResponseBody
	public IBSResultVO<WamBrMstr> delBrList(@RequestBody WamBrMstrs delVOs, @ModelAttribute WamBrMstr mstr, Locale locale) throws Exception {
		
		logger.debug("data : {}\nsearch : {}", delVOs);
		Map<String, String> resultMap = bizruleRqstService.delBrList(delVOs.get("data"), mstr);
		int result = Integer.parseInt(resultMap.get("result"));
		
		//result "0" 이 아닌 경우 실패 메세지 전송...
		String resultMsg = null;
		if(result > 0) {
			result = 0;
			resultMsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			resultMsg = message.getMessage("ERR.DEL", null, locale);
		}
    	
    	String action = WiseMetaConfig.IBSAction.DEL.getAction();
    	return new IBSResultVO<WamBrMstr>(resultMap, result, resultMsg, action);
    }
    
    /** 업무규칙 요청 저장 - IBSheet JSON @return meta
	 * @throws Exception */
	@RequestMapping("/dq/bizrule/regBizruleRqstlist.do")
	@ResponseBody
	public IBSResultVO<WamBrMstr> regBizruleRqstlist(@RequestBody WamBrMstrs data, WamBrMstr reqmst, Locale locale) throws Exception {
		
		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<WamBrMstr> list = data.get("data");

		logger.debug("사이즈 : {}", list.size());
		int result = bizruleRqstService.regBr(reqmst, list);

		//bizruleRqstService.check(reqmst);

		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = null;//requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WamBrMstr>(reqmst, result, resmsg, action);
	}
    
	/** 업무규칙 요청서 결재처리... @return meta
     * @throws Exception */
    @RequestMapping("/dq/bizrule/approveBizrule.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> approveBizrule(@RequestBody WaqBrMstrs data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);
		String resmsg;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		//결재라인이 없을경우는 자동결재가 처리되야하므로 결재자인지 확인하는 단계를 생략한다...
		if(reqmst.getAprLvl() != 0) {
			//결재자인지 확인한다.
			Boolean checkapprove = approveLineServie.checkapproveuser(reqmst);
			if(!checkapprove) {
				resmsg = message.getMessage("REQ.APPRCHK.ERR", new String[]{userid}, locale);
				return new IBSResultVO<WaqMstr>(-1, resmsg, null);
			}
		}
		
		ArrayList<WaqBrMstr> list = data.get("data");

		int result = bizruleRqstService.approve(reqmst, list);

		//stndWordRqstServie.check(reqmst);

//		int result = stndWordRqstServie.regStndWordRqstlist(list);


		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("REQ.APPROVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("REQ.APPROVE.ERR", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.APPROVE.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}
    
    
    /** 업무규칙 엑셀업로드 팝업 호출 */
    /** meta */
	@RequestMapping("/dq/bizrule/popup/bizrule_xls.do")
    public String goBizruleXls(@ModelAttribute("search") WamBrMstr search) {
		logger.debug("{}", search);
    	return "/dq/bizrule/popup/bizrule_xls";
    }
    
	/** 업무규칙 삭제 
	 * @throws Exception */
	@RequestMapping("/dq/bizrule/delBizruleList.do")
	@ResponseBody
	public IBSResultVO<WaqBrMstr> delVrfBizAreaList(@RequestBody WaqBrMstrs delVOs, @ModelAttribute WaqMstr mstr, Locale locale) throws Exception {
		
		logger.debug("data : {}\nsearch : {}", delVOs);
		Map<String, String> resultMap = bizruleRqstService.delRegBizruleList(delVOs.get("data"), mstr);
		int result = Integer.parseInt(resultMap.get("result"));
		
		//검증로직
		bizruleRqstService.check(mstr);
		
		//result "0" 이 아닌 경우 실패 메세지 전송...
		String resultMsg = null;
		if(result > 0) {
			result = 0;
			resultMsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			resultMsg = message.getMessage("ERR.DEL", null, locale);
		}
    	
    	String action = WiseMetaConfig.IBSAction.DEL.getAction();
    	return new IBSResultVO<WaqBrMstr>(resultMap, result, resultMsg, action);
    }
	
	
	/** 변경대상 추가 팝업 호출 */
	/** meta */
	@RequestMapping("/dq/bizrule/popup/bizrule_pop.do")
	public String formPoppage(@ModelAttribute("search")WamBrMstr search, String sflag,  ModelMap model) {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
    	logger.debug("{}", search);
    	model.addAttribute("sflag", sflag);
		return "/dq/bizrule/popup/bizrule_pop";
	}
	
	/** 업무규칙 변경대상 추가..(팝업에서 호출함)
	 * meta
	 */
	@RequestMapping("/dq/bizrule/regWam2WaqBizrule.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regWam2WaqlistBizrule(@RequestBody WaqBrMstrs data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<WaqBrMstr> list = data.get("data");

		int result = bizruleRqstService.regWam2Waq(reqmst, list);
		bizruleRqstService.check(reqmst);

//		int result = stndWordRqstServie.regStndWordRqstlist(list);
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);

	}
	
	//업무규칙 요청 화면
	@RequestMapping("/dq/bizrule/br_rqst.do")
	public String brformpage(WaqMstr reqmst, ModelMap model) throws Exception {
		logger.debug("WaqMstr:{}", reqmst);

    	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("BRA");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else {
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

    	//결재라인 결재레벨 조회
    	//결재레벨 미존재 시 검증 후 등록 버튼 활성화
    	WaaRqstBizApr aprSearch = new WaaRqstBizApr();
    	aprSearch.setBizDcd(reqmst.getBizDcd());
    	List<WaaRqstBizApr> list = approveLineServie.getapprovelinelist(aprSearch);
    	logger.debug("list SIZE : ", list.size());
    	reqmst.setAprLvl(list.size());

    	model.addAttribute("waqMstr", reqmst);
    	
		return "/dq/bizrule/br_rqst";
	}
	
	//쿼리 검증 메소드
	private boolean sqlVrfc(String schId, String sql) {
		boolean result = false;
		
		Connection con = null;
    	PreparedStatement pstmt = null;
		ResultSet rs = null;
		WadDataSet dataSet = new WadDataSet();
		dataSet.setSchDbSchId(schId);
		WaaDbConnTrgVO targetDbmsDM = targetDbms.selectTgtDbmsbyDs(dataSet);
		
//		StringBuffer sb = new StringBuffer(sql);
//		
//		if(sql.toUpperCase().contains("WHERE")) {
//			sql = sql.concat(" AND 1=2");
//		} else {
//			sql = sql.concat(" WHERE 1=2");
//		}
		
		sql = "SELECT CHK.* FROM ( ".concat(sql).concat(" ) CHK WHERE 1=2 ");
		
		try {
			// 대상 DBMS Connection 을 얻는다.
			String connTrgDrvrNm = UtilString.null2Blank(targetDbmsDM.getConnTrgDrvrNm()); 
			String connTrgLnkUrl = UtilString.null2Blank(targetDbmsDM.getConnTrgLnkUrl());
			String dbConnAcId    = UtilString.null2Blank(targetDbmsDM.getDbConnAcId());
			String dbConnAcPwd   = UtilString.null2Blank(targetDbmsDM.getDbConnAcPwd());
			
			con = ConnectionHelper.getConnection(connTrgDrvrNm, connTrgLnkUrl, dbConnAcId, dbConnAcPwd);
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs != null) {
				result = true;
			} else {
				result = false;
			}
		} catch(Exception e) {
			logger.debug(e.toString());
			return false;
		}
		
		return result;
	}
	
	
	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();
		//진단대상
		List<CodeListVo> connTrgDbms = codelistService.getCodeList(CodeListAction.connTrgDbms);

		codeMap.put("connTrgDbmsibs", UtilJson.convertJsonString(codelistService.getCodeListIBS(connTrgDbms)));
		codeMap.put("connTrgDbmsCd", connTrgDbms);

		//검토상태코드
		codeMap.put("rvwStsCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RVW_STS_CD")));
		codeMap.put("rvwStsCd", cmcdCodeService.getCodeList("RVW_STS_CD"));
		
		//요청구분코드
		codeMap.put("rqstDcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_DCD")));
		codeMap.put("rqstDcd", cmcdCodeService.getCodeList("RQST_DCD"));
		//업무구분코드
		codeMap.put("bizDcdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("BIZ_DCD")));
		codeMap.put("bizDcd", cmcdCodeService.getCodeList("BIZ_DCD"));
		//결재방식코드
		codeMap.put("vrfCdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("VRF_CD")));
		codeMap.put("vrfCd", cmcdCodeService.getCodeList("VRF_CD"));
		//결재그룹 코드 리스트
//						codeMap.put("approvegroupibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(CodeListAction.approvegroup)));
		//등록유형코드
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		//요청단계코드(RQST_STEP_CD)
		codeMap.put("rqstStepCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_STEP_CD")));
		codeMap.put("rqstStepCd", cmcdCodeService.getCodeList("RQST_STEP_CD"));
		
		//검증join코드
		codeMap.put("tgtVrfJoinCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("TGT_VRF_JOIN_CD")));
		codeMap.put("tgtVrfJoinCd", cmcdCodeService.getCodeList("TGT_VRF_JOIN_CD"));
		
		//스키마
		String connTrgSch   = UtilJson.convertJsonString(codelistService.getCodeList("connTrgSchId"));
		codeMap.put("devConnTrgSch", connTrgSch);
		
		return codeMap;
	}
	
}
