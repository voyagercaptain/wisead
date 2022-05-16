/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : BaseRqstCtrl.java
 * 2. Package : kr.wise.commons.rqstmst.web
 * 3. Comment : 
 * 4. 작성자  : meta
 * 5. 작성일  : 2014. 7. 23. 오후 5:11:53
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    meta : 2014. 7. 23. :            : 신규 개발.
 */
package kr.wise.commons.rqstmst.web;

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
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.BaseRqstService;
import kr.wise.commons.rqstmst.service.RequestChgDtlsService;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.RequestVrfDtlsService;
import kr.wise.commons.rqstmst.service.WaaBizInfo;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.rqstmst.service.WaqRqstChgDtls;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtls;
import kr.wise.commons.user.service.UserService;
import kr.wise.commons.user.service.WaaUser;
import kr.wise.commons.util.UtilJson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : BaseRqstCtrl.java
 * 3. Package  : kr.wise.commons.rqstmst.web
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 7. 23. 오후 5:11:53
 * </PRE>
 */
@Controller
public class BaseRqstCtrl {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService;

	private Map<String, Object> codeMap;
	
	@Inject
	private RequestMstService requestMstService;

	@Inject
	private ApproveLineServie approveLineServie;

	@Inject
	private UserService userService;
	
	@Inject
	private BaseRqstService baseRqstService;
	
	@Inject
	private RequestChgDtlsService requestChgDtlsService;
	
	@Inject
	private RequestVrfDtlsService requestVrfDtlsService;

	@Inject
	private MessageSource message;

    @Inject
	private EgovIdGnrService requestIdGnrService;
    
    

	static class WaqMstrs extends HashMap<String, ArrayList<WaqMstr>> { }

	
	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//코드리스트 JSON
//		List<CodeListVo> sysarea = codeListService.getCodeList(CodeListAction.sysarea);

		//공통코드 - IBSheet Combo Code용
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
//		codeMap.put("approvegroupibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(CodeListAction.approvegroup)));
		//등록유형코드
		codeMap.put("regTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD")));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		//요청단계코드(RQST_STEP_CD)
		codeMap.put("rqstStepCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_STEP_CD")));
		codeMap.put("rqstStepCd", cmcdCodeService.getCodeList("RQST_STEP_CD"));
		//단어구분코드
//		codeMap.put("wdDcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("WD_DCD")));
//		codeMap.put("wdDcd", cmcdCodeService.getCodeList("WD_DCD"));

//		codeMap.put("usergTypCd", cmcdCodeService.getCodeList("USERG_TYP_CD")); //사용자그룹유형코드

		return codeMap;
	}

    /** 내 요청목록 화면 조회 */
	/** meta */
    @RequestMapping("/dq/stnd/rqstmy_lst.do")
	public String LoadRqstMyPage(String linkFlag,Model model) {
    	logger.debug("linkFlag : {}",linkFlag);
    	model.addAttribute("linkFlag",linkFlag);
    	
    	//로그인 유저의 정보를 화면에 담아 리턴한다. USERG_TYP_CD의 값이 AD(관리자)인 경우만 해당...
    	LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
    	String userid = user.getUniqId();
    	
    	WaaUser tmpUser = userService.getUserInfo(userid);
    	if(tmpUser != null) {
    		model.addAttribute("usergTypCd", tmpUser.getUsergTypCd());
    	}
    	
		return "/dq/stnd/rqstmy_lst";
	}

    /** 내 요청목록 리스트 조회 - IBSheet JSON */
	@RequestMapping("/dq/stnd/selectRqstMyList.do")
	@ResponseBody
	public IBSheetListVO<WaqMstr> selectRqstMyList(@ModelAttribute WaqMstr search) {
		logger.debug("{}", search);
		List<WaqMstr> list = requestMstService.getRqstMyList(search);

		return new IBSheetListVO<WaqMstr>(list, list.size());
	}

	/** 내 결재목록 화면 조회 */
	/** meta */
    @RequestMapping("/dq/stnd/rqsttodo_lst.do")
	public String LoadRqstToDoPage(String linkFlag,Model model) {
    	logger.debug("linkFlag : {}",linkFlag);
    	model.addAttribute("linkFlag",linkFlag);
		return "/dq/stnd/rqsttodo_lst";
	}
    
     /** 내 반려목록 리스트 조회 - IBSheet JSON */
	@RequestMapping("/dq/stnd/selectRejMyList.do")
	@ResponseBody
	public IBSheetListVO<WaqMstr> selectRejMyList(@ModelAttribute WaqMstr search) {
		logger.debug("{}", search);
		List<WaqMstr> list = requestMstService.getRejMyList(search);

		return new IBSheetListVO<WaqMstr>(list, list.size());
	}

    /** 내 결재목록 리스트 조회 - IBSheet JSON */
	@RequestMapping("/dq/stnd/selectRqstToDoList.do")
	@ResponseBody
	public IBSheetListVO<WaqMstr> selectRqstToDoList(@ModelAttribute WaqMstr search) {
		logger.debug("{}", search);
		List<WaqMstr> list = requestMstService.getRqstToDoList(search);

		return new IBSheetListVO<WaqMstr>(list, list.size());
	}
	
	/** 내 결재완료목록 리스트 조회 - IBSheet JSON */
	@RequestMapping("/dq/stnd/selectRqstResultList.do")
	@ResponseBody
	public IBSheetListVO<WaqMstr> selectRqstResultList(@ModelAttribute WaqMstr search) {
		logger.debug("{}", search);
		List<WaqMstr> list = requestMstService.getRqstResultList(search);
		
		return new IBSheetListVO<WaqMstr>(list, list.size());
	}
	
	
	    /** 내 반려목록 화면 조회 */
	/** meta */
    @RequestMapping("/dq/stnd/rejectmy_lst.do")
	public String LoadRqstRejectMyPage(String linkFlag,Model model) {
    	logger.debug("linkFlag : {}",linkFlag);
    	model.addAttribute("linkFlag",linkFlag);
    	
    	//로그인 유저의 정보를 화면에 담아 리턴한다. USERG_TYP_CD의 값이 AD(관리자)인 경우만 해당...
    	LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
    	String userid = user.getUniqId();
    	
    	WaaUser tmpUser = userService.getUserInfo(userid);
    	if(tmpUser != null) {
    		model.addAttribute("usergTypCd", tmpUser.getUsergTypCd());
    	}
    	
		return "/dq/stnd/rejectmy_lst";
	}
    
    	/** 반려목록 더블클릭시 해당 등록요청 재상신 ...*/
	/** @param waqmst
	/** @return 
	 * @throws Exception */
	@RequestMapping("/dq/stnd/regRejectedReq.do")
	public String goRqstPage(WaqMstr reqmst) throws Exception {
		
		logger.debug("재상신정보 : {}", reqmst);

		WaaBizInfo bizInfo = requestMstService.getBizInfo(reqmst);

		logger.debug("재상신bizInfo : {}",bizInfo);
		
		String newRqstNo = requestIdGnrService.getNextStringId();
		String oldRqstNo = reqmst.getRqstNo();
		
		//마스터 입력
		reqmst.setRqstNo(newRqstNo);
		requestMstService.insertWaqMst(reqmst);
		
		//마스터 임시저장상태로 변경
		reqmst.setRqstStepCd("S");
		
		//biz인포 설정
		reqmst.setBizInfo(bizInfo);
		
		/*
		if(bizInfo.getBizDcd().equals("DIC")){
		    stndWordRqstService.regWaqRejected(reqmst, oldRqstNo);//단어
		    stndDmnRqstService.regWaqRejected(reqmst, oldRqstNo);//도메인
		    stndItemRqstService.regWaqRejected(reqmst, oldRqstNo);//용어
		}
		if(bizInfo.getBizDcd().equals("PDP")){
		    pdPdmTblRqstService.regWaqRejected(reqmst, oldRqstNo);  //PD물리모델 연계
		}
		if(bizInfo.getBizDcd().equals("DTT")){
		    //ddlTsfRqstService.regWaqRejected(reqmst, oldRqstNo);  //DDL이관
		}
		if(bizInfo.getBizDcd().equals("DDX")){
			reqmst.setBizDtlCd("DDLIDX");
			bizInfo.setBizDtlCd("DDLIDX");
			//ddlIdxRqstService.regWaqRejected(reqmst, oldRqstNo);  //IDX
		}
		if(bizInfo.getBizDcd().equals("CDT")){
			codeTsfRqstService.regWaqRejected(reqmst, oldRqstNo);  //코드이관
		}
		if(bizInfo.getBizDcd().equals("MST")){
			messageTsfRqstService.regWaqRejected(reqmst, oldRqstNo);  //메시지이관
		}
		*/
		
		String url = bizInfo.getUrl() + "?rqstNo=" + newRqstNo + "&bizDcd=" + bizInfo.getBizDcd() + "&bizDtlCd=" + bizInfo.getBizDtlCd();
		logger.debug("url : " + url);
		return "redirect:" + url;
	}
    
	/** 요청목록 리스트 삭제 - IBSheet JSON 
	 * @throws Exception */
	@RequestMapping("/commons/rqstmst/deleteRqstList.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> delList(@RequestBody WaqMstrs data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WaqMstr> list = data.get("data");

		int result = baseRqstService.deleteRqstList(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();


		return new IBSResultVO<WaqMstr>(result, resmsg, action);
	}
	
	
	/* 검증결과 상세조회 */
	@RequestMapping("/commons/rqstmst/getRqstVrfLst.do")
	@ResponseBody
	public IBSheetListVO<WaqRqstVrfDtls> getRqstVrfLst(@ModelAttribute WaqRqstVrfDtls search) {
		
		logger.debug("getRqstVrfLst >>> {}", search);
		List<WaqRqstVrfDtls> list = requestVrfDtlsService.getRqstVrfLst(search);
		
		return new IBSheetListVO<WaqRqstVrfDtls>(list, list.size());
		
	}
	/* 변경항목 상세조회 */
	@RequestMapping("/commons/rqstmst/getRqstChangeLst.do")
	@ResponseBody
	public IBSheetListVO<WaqRqstChgDtls> getRqstChangeLst(@ModelAttribute WaqMstr search, String subInfo) {
		logger.debug("변경항목조회");
		logger.debug("{}", search);
		logger.debug("{}", subInfo);
		List<WaqRqstChgDtls> list = requestChgDtlsService.getRqstChangeLst(search, subInfo);
		
		return new IBSheetListVO<WaqRqstChgDtls>(list, list.size());
		
	}
	
	

	 /** 내 요청목록 화면 조회(DQ) */
    /** yeonho */
    @RequestMapping("/dq/report/rqstmy_dq_lst.do")
    public String geDqRqstMyPage(String linkFlag,Model model) {
        logger.debug("linkFlag : {}",linkFlag);
        model.addAttribute("linkFlag",linkFlag);
        
        //로그인 유저의 정보를 화면에 담아 리턴한다. USERG_TYP_CD의 값이 AD(관리자)인 경우만 해당...
        LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
        String userid = user.getUniqId();
        
        WaaUser tmpUser = userService.getUserInfo(userid);
        if(tmpUser != null) {
            model.addAttribute("usergTypCd", tmpUser.getUsergTypCd());
        }
        
        return "/dq/report/rqstmy_dq_lst";
    }
	
    /** 내 결재목록 화면 조회(DQ) */
    /** yeonho */
    @RequestMapping("/dq/report/rqsttodo_dq_lst.do")
    public String getDqRqstToDoPage(String linkFlag,Model model) {
        
        logger.debug("linkFlag : {}",linkFlag);
        model.addAttribute("linkFlag",linkFlag);
        
        return "/dq/report/rqsttodo_dq_lst";
    }
	
}
