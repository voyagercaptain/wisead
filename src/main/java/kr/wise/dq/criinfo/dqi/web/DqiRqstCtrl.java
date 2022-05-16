package kr.wise.dq.criinfo.dqi.web;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.damgmt.approve.service.WaaRqstBizApr;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilJson;
import kr.wise.dq.criinfo.dqi.service.DqiRqstService;
import kr.wise.dq.criinfo.dqi.service.WamDqiVO;
import kr.wise.dq.criinfo.dqi.service.WaqDqiVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * 2. FileName  : DqiRqstCtrl.java
 * 3. Package  : kr.wise.dq.criinfo.dqi.web
 * 4. Comment  : 
 * 5. 작성자   : meta(김연호)
 * 6. 작성일   : 2014. 5. 9. 오후 5:53:58
 * </PRE>
 */ 
@Controller
public class DqiRqstCtrl {

	private final  Logger logger = LoggerFactory.getLogger(getClass());

	private Map<String, Object> codeMap;
	
	/** JSON 2 ArrayList 변환 - 프로그램 리스트를 받을 클래스 선언 */
	static class WaqDqiVOs extends HashMap<String, ArrayList<WaqDqiVO>> { }

	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private DqiRqstService dqiRqstService;
	
	@Inject
	private EgovIdGnrService requestIdGnrService;
	
	@Inject
	private RequestMstService requestMstService;
	
	/* 결재라인조회*/
	@Inject
	private ApproveLineServie approveLineServie;
	
	@Inject
	private MessageSource message;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@RequestMapping("/dq/criinfo/dqi/dqi_rqst.do")
	public String formpage(WaqMstr reqmst, ModelMap model) throws Exception {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
    	
    	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("DQI");
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
    	
		return "/dq/criinfo/dqi/dqi_rqst";
	}
	
	
	
	
	@RequestMapping("/dq/criinfo/dqi/getVrfDqiListIBS.do")
	@ResponseBody
	public IBSheetListVO<WaqDqiVO> getVrfDqiListIBS(@ModelAttribute WaqMstr search) {
		logger.debug("WaqMstr {} : ", search);
		List<WaqDqiVO> list = dqiRqstService.getVrfDqiListIBS(search);
		
		return new IBSheetListVO<WaqDqiVO>(list, list.size());
	}
	

	/** 데이터품질지표 리스트 저장 -  결과는 IBSResult Json 리턴 */	
	/** meta */
	@RequestMapping("/dq/criinfo/dqi/regVrfDqiList.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> register( @RequestBody WaqDqiVOs saveVOs,  @ModelAttribute WaqMstr reqmst,  Locale locale) throws Exception {
		logger.debug("data : {}", saveVOs);
		
		//사용자 정보
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		logger.debug("USER : {}", user);
		//검증사용자ID 셋팅
		reqmst.setWritUserId(user.getId());
		//요청사용자ID 셋팅
		//결재로직 개발 완료 후 추가 개발 요건 발생
		reqmst.setRqstUserId(user.getId());
				
		//등록 결과를 맵으로 받는다.
		//result : 등록 결과
		//rqstNo : 요청서 번호
		int result = dqiRqstService.register(reqmst, saveVOs.get("data"));
		
		
		//검증로직
		if(result > 0){
			dqiRqstService.check(reqmst);
		}
		
		String resultMsg = null;
		
		//result "0" 이 아닌 경우 실패 메세지 전송...
		if(result > 0) {
			result = 0;
			resultMsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resultMsg = message.getMessage("ERR.SAVE", null, locale);
		}
		
		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		
		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
		reqmst = requestMstService.getrequestMst(reqmst);
				
		return new IBSResultVO<WaqMstr>(reqmst, result, resultMsg, action);
	}
	
	/** 데이터품질지표 요청서 결재처리... @return meta
     * @throws Exception */
    @RequestMapping("/dq/criinfo/dqi/approveDqi.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> approveDqi(@RequestBody WaqDqiVOs data, WaqMstr reqmst, Locale locale) throws Exception {

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

		ArrayList<WaqDqiVO> list = data.get("data");

		int result = dqiRqstService.approve(reqmst, list);

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
	
	@RequestMapping("/dq/criinfo/dqi/delVrfDqiList.do")
	@ResponseBody
	public IBSResultVO<WaqDqiVO> delVrfDqiList(@RequestBody WaqDqiVOs delVOs, @ModelAttribute WaqMstr mstr, Locale locale) {
		
		logger.debug("data : {}\nsearch : {}", delVOs);
		Map<String, String> resultMap = dqiRqstService.delRegDqiList(delVOs.get("data"), mstr);
		int result = Integer.parseInt(resultMap.get("result"));
		
		//검증로직
		if(result > 0){
			dqiRqstService.check(mstr);
		}
		
		//result "0" 이 아닌 경우 실패 메세지 전송...
		String resultMsg = null;
		if(result > 0) {
			result = 0;
			resultMsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			resultMsg = message.getMessage("ERR.DEL", null, locale);
		}
    	
    	String action = WiseMetaConfig.IBSAction.DEL.getAction();
    	return new IBSResultVO<WaqDqiVO>(resultMap, result, resultMsg, action);
    }
	
	/** 데이터품질지표 엑셀업로드 팝업 호출 */
    /** meta */
	@RequestMapping("/dq/criinfo/dqi/popup/dqi_xls.do")
    public String goDqiXls(@ModelAttribute("search") WamDqiVO search) {
		logger.debug("{}", search);
    	return "/dq/criinfo/dqi/popup/dqi_xls";
    }
	
	/** 데이터품질지표 상세내용 조회 */
    /** meta */
    @RequestMapping("/dq/criinfo/dqi/ajaxgrid/dqi_rqst_dtl.do")
    public String getDqiRqstDtl (WaqDqiVO searchVO, ModelMap model) {

    	logger.debug("searchVO:{}", searchVO);

    	if (searchVO.getRqstSno() == null){
    		model.addAttribute("saction", "I");
    	} else {
    		WaqDqiVO resultVO = dqiRqstService.getDqiRqstDetail(searchVO); 
    		model.addAttribute("result", resultVO);
    		model.addAttribute("saction", "U");
    	}

    	return "/dq/criinfo/dqi/dqi_rqst_dtl";
    }
	
	
	
	/** 데이터품질지표 변경대상 추가..(팝업에서 호출함)
	 * meta
	 */
	@RequestMapping("/dq/criinfo/dqi/regWam2WaqDqi.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regWam2WaqlistDqi(@RequestBody WaqDqiVOs data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<WaqDqiVO> list = data.get("data");

		int result = dqiRqstService.regWam2Waq(reqmst, list);
		dqiRqstService.check(reqmst);

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
    
    
	/** 공통코드 및 목록성 코드 조회  */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {
		codeMap = new HashMap<String, Object>();
		
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
//				codeMap.put("approvegroupibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(CodeListAction.approvegroup)));
		//등록유형코드
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		//요청단계코드(RQST_STEP_CD)
		codeMap.put("rqstStepCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_STEP_CD")));
		codeMap.put("rqstStepCd", cmcdCodeService.getCodeList("RQST_STEP_CD"));
		
		return codeMap;
	}
	
}
