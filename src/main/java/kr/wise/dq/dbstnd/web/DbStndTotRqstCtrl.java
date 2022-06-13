/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndWordRequestCtrl.java
 * 2. Package : kr.wise.dq.stnd.web
 * 3. Comment : 표준단어 등록요청 컨트롤러
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 4. 4. 오후 12:54:05
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 4. 4. :            : 신규 개발.
 */
package kr.wise.dq.dbstnd.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.damgmt.approve.service.MstrAprPrcVO;
import kr.wise.commons.damgmt.approve.service.RequestApproveService;
import kr.wise.commons.damgmt.approve.service.WaaRqstBizApr;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.dbstnd.service.DbStndService;
import kr.wise.dq.dbstnd.service.StndService;
import kr.wise.dq.dbstnd.service.WamDbSditm;
import kr.wise.dq.dbstnd.service.WamDbStcd;
import kr.wise.dq.dbstnd.service.WamDbStwd;
import kr.wise.dq.dbstnd.service.WapDbDvCanAsm;
import kr.wise.dq.dbstnd.service.WapDbDvCanDic;

import kr.wise.dq.stnd.service.WapDvCanDic;
import kr.wise.dq.stnd.service.WaqSditm;

import kr.wise.dq.dbstnd.service.WamDbDmn;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DbStndTotRqstCtrl {

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
	private DbStndService dbStndService;
	
	@Inject
	private StndService stndService;
	
	@Inject
	private RequestMstService requestMstService;

	@Inject
	private ApproveLineServie approveLineServie;

	@Inject
	private BasicInfoLvlService basicInfoLvlService;
	
	@Inject
	private MessageSource message;

    @Inject
	private EgovIdGnrService requestIdGnrService;

	static class WamDbSditms extends HashMap<String, ArrayList<WamDbSditm>> {}
	
	static class WapDbDvCanAsms extends HashMap<String, ArrayList<WapDbDvCanAsm>> {}
	
	static class WapDbDvCanDics extends HashMap<String, ArrayList<WapDbDvCanDic>> {}
	

	static class WamDbDmns extends HashMap<String, ArrayList<WamDbDmn>> {}
	
	static class WamDbStwds extends HashMap<String, ArrayList<WamDbStwd>> {}
	
	static class WamDbStcds extends HashMap<String, ArrayList<WamDbStcd>> {}
	
	
	/** 표준데이터 엑셀등록  화면이동 - 요청번호가 없을 경우 채번하여 리턴한다. @return insomnia */
    @RequestMapping("/dq/dbstnd/{screenGb}/stndtot_rqst.do")
	public String goStndwordrqstForm(WaqMstr reqmst, @PathVariable String screenGb, ModelMap model,HttpSession session) throws Exception {
    	logger.debug("WaqMstr:{}", reqmst);
    	
    	reqmst.setAprLvl(0);		//결재레벨을 지정해줘야 검증부분 확인가능
    	
    	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("DIC");
    		reqmst.setBizDtlCd("SDITM");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else {
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}

//    	logger.debug("reqmst:{}", reqmst);
    	
    	//도메인그룹의 기본정보레벨, SELECT BOX ID값을 불러온다.
		WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("DMNG");
		logger.debug("기본정보레벨 조회 : {}", data);
		if (data != null) {
			model.addAttribute("bscLvl", data.getBscLvl());
			model.addAttribute("selectBoxId", data.getSelectBoxId());
			model.addAttribute("selectBoxNm", data.getSelectBoxNm());
		}

		
    	model.addAttribute("waqMstr", reqmst);

    	//표준요청서의 항목을 체크하여 클릭될 탭을 설정
        //int dmnYn = stndDmnRqstService.checkExistsWaqDmn(reqmst);
        //int itemYn = stndItemRqstService.checkExistsWaqItem(reqmst);
        
//        등록요청 취소 ( 결제가 한차례라도 되었을 경우 등록요청 취소 불가능) 
//        waa_apr_prc테이블에 등록요청번호로 로우가 있고, waq_mstr의 rqst_step_cd가 'A'나 'Q' 인 경우 등록요청 취소 불가능
//        waa_apr_prc테이블에 등록요청번호로 로우가 없고, waq_mstr의 rqst_step_cd가 'Q'인 경우 등록요청 취소 가능
//        필요한 것 
//        1. 로그인 한 사용자와 등록요청한 사용자와 같은 경우 등록요청취소 버튼 출력
//        2. 그 페이지에 필요한것은 요청번호, 로그인한 사용자, 등록요청자, 
//        3. 승인레벨 1인 경우만 체크, 승인레벨 1의 승인자가 빈 경우 - 등록요청중, 안빈 경우 - 승인프로세스타는중이거나 결제완료건.
//        4. 승인레벨 2는 체크할 필요가 없음. 만약 등록요청 취소하는경우 제거만 해주면됨.
//        5. 취소하는경우 'Q'도 'S'로 변경.
        
      	MstrAprPrcVO mapvo = new MstrAprPrcVO();
        System.out.println("요청번호 : " + reqmst.getRqstNo());
        System.out.println("요청구분 : " + reqmst.getBizDcd());
        System.out.println("아이디 : " + ((LoginVO)session.getAttribute("loginVO")).getId());
        mapvo.setRqst_no(reqmst.getRqstNo());
        mapvo.setWrit_user_id(((LoginVO)session.getAttribute("loginVO")).getId());
        
        int mapcount = approveLineServie.checkRequst(mapvo); 
        System.out.println("카운트 : " + mapcount);
        model.addAttribute("checkrequstcount", mapcount);
        model.addAttribute("rqstno",reqmst.getRqstNo());
        model.addAttribute("rqstbizdcd",reqmst.getBizDcd());
        
        model.addAttribute("codeMap",getcodeMap());
        
        model.addAttribute("userDbList", dbStndService.selectUserDbList(((LoginVO)session.getAttribute("loginVO")).getId()));
        //model.addAttribute("userOrgList", dbStndService.selectUserOrgList(((LoginVO)session.getAttribute("loginVO")).getId()));
        model.addAttribute("userOrg", dbStndService.selectUserOrg(((LoginVO)session.getAttribute("loginVO")).getId()));
        
        
        String usergId = ((LoginVO)session.getAttribute("loginVO")).getUsergId();
        
        List<CodeListVo> userOrgList = null;
        
        if ("2".equals(usergId) || "3".equals(usergId)) {
			 userOrgList = codeListService.getCodeList(WiseMetaConfig.CodeListAction.orgCd); 
		 } 
		 else {
			 userOrgList = codeListService.getOrgList(((LoginVO)session.getAttribute("loginVO")).getId()); 
		} 
        
        model.addAttribute("userOrgList", userOrgList);
        
        String strReturn = "";
        if("STWD".equals(screenGb)) {
        	strReturn = "/dq/dbstnd/dbstndstwd_rqst";
        }
        else if("DMN".equals(screenGb)) {
        	strReturn = "/dq/dbstnd/dbstnddmn_rqst";
        }
        else if("SDITM".equals(screenGb)) {
        	strReturn = "/dq/dbstnd/dbstnditem_rqst";
        }
        else if("STCD".equals(screenGb)) {
        	strReturn = "/dq/dbstnd/dbstndstcd_rqst";
        } 
        //else {
        //	strReturn = "/dq/dbstnd/dbstndtot_rqst";
        //}

        return strReturn;	
	} 


	/** 공통코드 및 목록성 코드리스트를 가져온다. */
//	@ModelAttribute("codeMap")
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
		
		//표준구분 멀티사전용
//		codeMap.put("stndAsrtibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("STNDASRT")));
//		codeMap.put("stndAsrt", cmcdCodeService.getCodeList("STNDASRT"));
		
		//개인정보등급 이베이코리아
//		codeMap.put("persInfoGrdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PERS_INFO_GRD")));
//		codeMap.put("persInfoGrd", cmcdCodeService.getCodeList("PERS_INFO_GRD"));

		

//		codeMap.put("usergTypCd", cmcdCodeService.getCodeList("USERG_TYP_CD")); //사용자그룹유형코드

		return codeMap;
	}
	
	
	
	@RequestMapping("/dq/dbstnd/getsditmlist.do")
	@ResponseBody
	public IBSheetListVO<WamDbSditm> getStndItemList(@ModelAttribute WamDbSditm data, Locale locale, HttpSession session) {

		logger.debug("req vo:{}", data);
		
		data.setUserId(((LoginVO)session.getAttribute("loginVO")).getId());
		data.setUsergId(((LoginVO)session.getAttribute("loginVO")).getUsergId());

		List<WamDbSditm> list = dbStndService.getStndItemList(data);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamDbSditm>(list, list.size());

	}
	
	
	/** 도메인 리스트 조회 */
	@RequestMapping("/dq/dbstnd/getDomainlist.do")
	@ResponseBody
	public IBSheetListVO<WamDbDmn> getDomainList(@ModelAttribute WamDbDmn data, Locale locale, HttpSession session) {

		logger.debug("reqvo:{}", data);
		
		data.setUserId(((LoginVO)session.getAttribute("loginVO")).getId());
		data.setUsergId(((LoginVO)session.getAttribute("loginVO")).getUsergId());
		
		List<WamDbDmn> list = dbStndService.getDomainList(data);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamDbDmn>(list, list.size());

	}
	
	
	@RequestMapping("/dq/dbstnd/getStndCodelist.do")
	@ResponseBody
	public IBSheetListVO<WamDbStcd> getStndCodelist(@ModelAttribute WamDbStcd data, Locale locale, HttpSession session) {

		logger.debug("reqvo:{}", data);
		
		data.setUserId(((LoginVO)session.getAttribute("loginVO")).getId());
		data.setUsergId(((LoginVO)session.getAttribute("loginVO")).getUsergId());
		
		List<WamDbStcd> list = null;
		try {
			list = dbStndService.getStndCodelist(data);
		} catch(Exception e) {
			logger.error("", e);
		}

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamDbStcd>(list, list.size());

	}
	
	
	@RequestMapping("/dq/stnd/getStndCodelist.do")
	@ResponseBody
	public IBSheetListVO<WamDbStcd> getOrgStndCodelist(@ModelAttribute WamDbStcd data, Locale locale, HttpSession session) {

		logger.debug("reqvo:{}", data);
		data.setUserId(((LoginVO)session.getAttribute("loginVO")).getId());
		data.setUsergId(((LoginVO)session.getAttribute("loginVO")).getUsergId());
		
		List<WamDbStcd> list = null;
		try {
			list = stndService.getStndCodelist(data);
		} catch(Exception e) {
			logger.error("", e);
		}

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);
		return new IBSheetListVO<WamDbStcd>(list, list.size());
	}
	

	@RequestMapping("/dq/dbstnd/getStndWordlist.do")
	@ResponseBody
	public IBSheetListVO<WamDbStwd> getStndWordList(@ModelAttribute WamDbStwd data, Locale locale, HttpSession session) {

		logger.debug("reqvo:{}", data);
		
		data.setUserId(((LoginVO)session.getAttribute("loginVO")).getId());
		data.setUsergId(((LoginVO)session.getAttribute("loginVO")).getUsergId());

		List<WamDbStwd> list = dbStndService.getStndWordList(data);


//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamDbStwd>(list, list.size());

	}
	
	
	
	/** 표준항목 리스트 등록 @throws Exception insomnia */
    @RequestMapping("/dq/dbstnd/regitemWamlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regStndItemWamList(@RequestBody WamDbSditms data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WamDbSditm> list = data.get("data");


		long beforeTime = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기
        
		int result = dbStndService.registerItemWam(list, reqmst);

		
		String resmsg;

		if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}
		
		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();
		
		long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
		long secDiffTime = (afterTime - beforeTime)/1000; //두 시간에 차 계산
		logger.debug("시간차이(m): {}", secDiffTime);
		
		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}
    
    
    @RequestMapping("/dq/dbstnd/regStndCodeWamlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regStndCodeWamlist(@RequestBody WamDbStcds data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WamDbStcd> list = data.get("data");

		long beforeTime = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기
        
		int result = 0;
		try {
			result = dbStndService.registerStcdWam(list);
		} catch (Exception e) {
			logger.error("", e);
		}
		String resmsg;

		if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}
		
		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();
		
		long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
		long secDiffTime = (afterTime - beforeTime)/1000; //두 시간에 차 계산
		logger.debug("시간차이(m): {}", secDiffTime);
		
		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}
    
    
    
    @RequestMapping("/dq/stnd/regStndCodeWamlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> regOrgStndCodeWamlist(@RequestBody WamDbStcds data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WamDbStcd> list = data.get("data");

		long beforeTime = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기
        
		int result = 0;
		try {
			result = stndService.registerStcdWam(list);
		} catch (Exception e) {
			logger.error("", e);
		}
		String resmsg;

		if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}
		
		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();
		
		long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
		long secDiffTime = (afterTime - beforeTime)/1000; //두 시간에 차 계산
		logger.debug("시간차이(m): {}", secDiffTime);
		
		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}
    

    
    /** 삭제 .... @throws Exception insomnia */
	@RequestMapping("/dq/dbstnd/delitemWamlist.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> delStnitemWamList(@RequestBody WamDbSditms data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WamDbSditm> list = data.get("data");

		for(int i=0;i<list.size();i++) {
			list.get(i).setIbsStatus("D");
		}
		
		int result = dbStndService.registerItemWam(list, reqmst);

		String resmsg;

		if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
//		reqmst = requestMstService.getrequestMst(reqmst);


		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}
	
    
	/** 도메인 요청 리스트 등록.... @throws Exception insomnia */
	@RequestMapping("/dq/dbstnd/regdmnWamlist.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regStnddmnWamList(@RequestBody WamDbDmns data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WamDbDmn> list = data.get("data");

		int result = dbStndService.registerDmnWam(list);


		String resmsg;

		if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		//마지막에 최종 업데이트 된 요청마스터 정보를 가져온다.
//		reqmst = requestMstService.getrequestMst(reqmst);


		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}
	
	/** 도메인 요청 리스트 등록.... @throws Exception insomnia */
	@RequestMapping("/dq/dbstnd/deldmnWamlist.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> delStnddmnWamList(@RequestBody WamDbDmns data, WaqMstr reqmst, Locale locale) throws Exception {

		logger.debug("reqmst:{}\ndata:{}", reqmst, data);
		ArrayList<WamDbDmn> list = data.get("data");

		for(int i=0;i<list.size();i++) {
			list.get(i).setIbsStatus("D");
		}
		
		int result = dbStndService.registerDmnWam(list);

		String resmsg;

		if(result > 0 ){
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.RqstAction.REGISTER.getAction();

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}
	
	
	
	 /** 표준단어 요청 저장 - IBSheet JSON @return insomnia
		 * @throws Exception */
		@RequestMapping("/dq/dbstnd/regStndWordWamlist.do")
		@ResponseBody
		public IBSResultVO<WaqMstr> regStndWordWamlist(@RequestBody WamDbStwds data, WaqMstr reqmst, HttpSession session, Locale locale) throws Exception {
			logger.debug("/dq/dbstnd/regStndWordRqstlist.do");
			logger.debug("reqmst:{} \ndata:{}", reqmst, data);

			ArrayList<WamDbStwd> list = data.get("data");

			int result = dbStndService.registerStwdWam(list);
	
			String resmsg;

			if(result > 0) {
				result = 0;
				resmsg = message.getMessage("MSG.SAVE", null, locale);
			} else {
				result = -1;
				resmsg = message.getMessage("ERR.SAVE", null, locale);
			}

			String action = WiseMetaConfig.RqstAction.REGISTER.getAction();


			return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
		}
		
		
	  /** 표준단어 요청 저장 - IBSheet JSON @return insomnia
		 * @throws Exception */
		@RequestMapping("/dq/dbstnd/delstwdwamlist.do")
		@ResponseBody
		public IBSResultVO<WaqMstr> delstwdwamlist(@RequestBody WamDbStwds data, WaqMstr reqmst, HttpSession session, Locale locale) throws Exception {
			logger.debug("/dq/dbstnd/regStndWordRqstlist.do");
			logger.debug("reqmst:{} \ndata:{}", reqmst, data);

			ArrayList<WamDbStwd> list = data.get("data");
            
			for(int i=0;i<list.size();i++) {
				list.get(i).setIbsStatus("D");
			}
			int result = dbStndService.registerStwdWam(list);
			
			String resmsg;

			if(result > 0) {
				result = 0;
				resmsg = message.getMessage("MSG.SAVE", null, locale);
			} else {
				result = -1;
				resmsg = message.getMessage("ERR.SAVE", null, locale);
			}

			String action = WiseMetaConfig.RqstAction.REGISTER.getAction();


			return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
		}
		
		
		@RequestMapping("/dq/dbstnd/delstcdwamlist.do")
		@ResponseBody
		public IBSResultVO<WaqMstr> delstcdwamlist(@RequestBody WamDbStcds data, WaqMstr reqmst, HttpSession session, Locale locale) throws Exception {
			logger.debug("/dq/dbstnd/delstcdwamlist.do");
			logger.debug("reqmst:{} \ndata:{}", reqmst, data);

			ArrayList<WamDbStcd> list = data.get("data");
            
			for(int i=0;i<list.size();i++) {
				list.get(i).setIbsStatus("D");
			}
			int result = dbStndService.registerStcdWam(list);
			
			String resmsg;

			if(result > 0) {
				result = 0;
				resmsg = message.getMessage("MSG.SAVE", null, locale);
			} else {
				result = -1;
				resmsg = message.getMessage("ERR.SAVE", null, locale);
			}

			String action = WiseMetaConfig.RqstAction.REGISTER.getAction();
			return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
		}
		
		
		@RequestMapping("/dq/stnd/delstcdwamlist.do")
		@ResponseBody
		public IBSResultVO<WaqMstr> delOrgStcdwamlist(@RequestBody WamDbStcds data, WaqMstr reqmst, HttpSession session, Locale locale) throws Exception {
			logger.debug("/dq/stnd/delstcdwamlist.do");
			logger.debug("reqmst:{} \ndata:{}", reqmst, data);

			ArrayList<WamDbStcd> list = data.get("data");
            
			for(int i=0;i<list.size();i++) {
				list.get(i).setIbsStatus("D");
			}
			int result = stndService.registerStcdWam(list);
			
			String resmsg;

			if(result > 0) {
				result = 0;
				resmsg = message.getMessage("MSG.SAVE", null, locale);
			} else {
				result = -1;
				resmsg = message.getMessage("ERR.SAVE", null, locale);
			}

			String action = WiseMetaConfig.RqstAction.REGISTER.getAction();
			return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
		}
		
		
		   //표준항목 분할
	    @RequestMapping("/dq/dbstnd/dbstnditemdiv_lst.do")
	    public String gostnditemxlspage(@ModelAttribute("search") WaqSditm search) {

	    	return "/dq/dbstnd/dbstnditemdiv_lst";
	    }
	    
	    
//		    /** 표준항목 자동분할 조회...  */
	    @RequestMapping("/dq/dbstnd/getItemDvRqstList.do")
	    @ResponseBody
	    public IBSheetListVO<WapDbDvCanAsm> getItemDvRqstList(WapDbDvCanDic data, Locale locale) {
	    	logger.debug("division:{}", data);
	    	
	    	List<WapDbDvCanAsm> list = dbStndService.getItemDvRqstList(data);
	    	
	    	return new IBSheetListVO<WapDbDvCanAsm>(list, list.size());
	    	
	    }
	    
	    
	    

	    @RequestMapping("/dq/dbstnd/regItemAutoDiv.do")
	    @ResponseBody
	    public IBSResultVO<WapDbDvCanAsm> regItemAutoDiv(@RequestBody WapDbDvCanAsms data, Locale locale,WapDbDvCanDic data2) throws Exception {
	    	logger.debug("division:{}", data);
	    	List<WapDbDvCanAsm> list = data.get("data");
	   	
	    	//항목분할요청 ID
	    	Map<String, String> resultMap = dbStndService.regItemAutoDiv(list,data2);
	    	int result = Integer.parseInt(resultMap.get("result"));
	    	String resmsg;
	    	
	    	if(result > 0 ){
	    		result = 0;
	    		resmsg = message.getMessage("REQ.DIV.ITEM", null, locale);
	    	} else {
	    		result = -1;
	    		resmsg = message.getMessage("REQ.DIV.ERR", null, locale);
	    	}
	    	String action = WiseMetaConfig.IBSAction.REG.getAction();
	    	
	    	return new IBSResultVO<WapDbDvCanAsm>(resultMap, result, resmsg, action);
	    }
	//    
	    @RequestMapping("/dq/dbstnd/delItemAutoDiv.do")
	    @ResponseBody
	    public IBSResultVO<WapDbDvCanAsm> delItemAutoDiv(@RequestBody WapDbDvCanAsms data, Locale locale) throws Exception {
	    	logger.debug("division:{}", data);
	    	List<WapDbDvCanAsm> list = data.get("data");
	    	
	    	//항목분할요청 ID
	    	Map<String, String> resultMap = dbStndService.delItemAutoDiv(list);
	    	int result = Integer.parseInt(resultMap.get("result"));
	    	String resmsg;
	    	
	    	if(result > 0 ){
	    		result = 0;
	    		resmsg = message.getMessage("MSG.DEL", null, locale);
	    	} else {
	    		result = -1;
	    		resmsg = message.getMessage("ERR.DEL", null, locale);
	    	}
	    	String action = WiseMetaConfig.IBSAction.DEL.getAction();
	    	
	    	return new IBSResultVO<WapDbDvCanAsm>(resultMap, result, resmsg, action);
	    }
	    
	    @RequestMapping("/dq/dbstnd/getDbList.do")
	    @ResponseBody
	    public List getUserDbList(@ModelAttribute WamDbSditm data, Locale locale, HttpSession session) throws Exception {
	    	
	    	List dbList = new ArrayList();
	    	dbList = dbStndService.getDbList(data);
	    	
	    	return dbList;
	    }
	    
}
