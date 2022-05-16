package kr.wise.dq.stnd.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

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

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.damgmt.approve.service.MstrAprPrcVO;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.model.service.WamNiaPdmCol;
import kr.wise.dq.stnd.service.CodeDfntService;
import kr.wise.dq.stnd.service.DbWamCdVal;
import kr.wise.dq.stnd.service.StndWordService;
import kr.wise.dq.stnd.service.WamCdVal;
import kr.wise.dq.stnd.service.WamStwd;
import kr.wise.dq.stnd.service.WaqStwd;
import kr.wise.dq.stnd.web.StndWordRqstCtrl.WamStwds;
import kr.wise.dq.stnd.web.StndWordRqstCtrl.WaqStwds;

@Controller
public class CodeDfntRqstCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	private Map<String, Object> codeMap;
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private CodeDfntService codeDfntService;
	
	@Inject
	private RequestMstService requestMstService;
	
	@Inject
	private ApproveLineServie approveLineServie;
	
	@Inject
	private MessageSource message;	
	
	static class WamCdVals extends HashMap<String, ArrayList<WamCdVal>> { }
	
	static class DbWamCdVals extends HashMap<String, ArrayList<DbWamCdVal>> { }
	
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

		return codeMap;
	}
	
    @RequestMapping("/dq/stnd/stndcode_rqst.do")
	public String goStndCode(WaqMstr reqmst, ModelMap model,HttpSession session) throws Exception {
    	logger.debug("WaqMstr:{}", reqmst);
    	
    	reqmst.setAprLvl(0);		//결재레벨을 지정해줘야 검증부분 확인가능
        
    	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("DIC");
    		reqmst.setBizDtlCd("DMP");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else {
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}
    	
    	model.addAttribute("waqMstr", reqmst);
    	
      	MstrAprPrcVO mapvo = new MstrAprPrcVO();
//      System.out.println("요청번호 : " + reqmst.getRqstNo());
//      System.out.println("요청구분 : " + reqmst.getBizDcd());
//      System.out.println("아이디 : " + ((LoginVO)session.getAttribute("loginVO")).getId());
      	mapvo.setRqst_no(reqmst.getRqstNo());
      	mapvo.setWrit_user_id(((LoginVO)session.getAttribute("loginVO")).getId());
      
      	int mapcount = approveLineServie.checkRequst(mapvo); 
//      System.out.println("카운트 : " + mapcount);
      	model.addAttribute("checkrequstcount", mapcount);
      	model.addAttribute("rqstno",reqmst.getRqstNo());
      	model.addAttribute("rqstbizdcd",reqmst.getBizDcd());
      
      
      	model.addAttribute("codeMap",getcodeMap());
    	
    	
    	
    	return "/dq/stnd/stndcode_rqst";
    	
	} 
    
    /** 코드 조회 */
    @RequestMapping("/dq/stnd/getCodelist.do")
	@ResponseBody
	public IBSheetListVO<WamCdVal> getCodeList(@ModelAttribute WamCdVal data, Locale locale) {

		logger.debug("reqvo:");
		

		List<WamCdVal> list = codeDfntService.getCodeList(data);


//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamCdVal>(list, list.size());

	}

    /** 코드 등록*/
    @RequestMapping("/dq/stnd/regCodeWamlist.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regCodeWamlist(@RequestBody WamCdVals data, WaqMstr reqmst, HttpSession session, Locale locale) throws Exception {
		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<WamCdVal> list = data.get("data");

		int result = codeDfntService.registerWam(list);
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
//		reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}
//    
    @RequestMapping("/dq/stnd/delCodeWamlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> delCodeWamlist(@RequestBody WamCdVals data, WaqMstr reqmst, HttpSession session, Locale locale) throws Exception {
		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<WamCdVal> list = data.get("data");
        
		for(int i=0;i<list.size();i++) {
			list.get(i).setIbsStatus("D");
		}
		int result = codeDfntService.registerWam(list);
		
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
//		reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}
    
    /** 코드정의서 비교 */
    @RequestMapping("/dq/stnd/stndcodepdmgap_lst.do")
	public String goCodePdmGap(WaqMstr reqmst, ModelMap model,HttpSession session) throws Exception {
    	logger.debug("WaqMstr:{}", reqmst);
    	
        
    	return "/dq/stnd/stndcodepdmgap_lst";
    	
	} 
    
    @RequestMapping("/dq/stnd/dbstndcode_rqst.do")
	public String goDbStndCode(WaqMstr reqmst, ModelMap model,HttpSession session) throws Exception {
    	logger.debug("WaqMstr:{}", reqmst);
    	
    	reqmst.setAprLvl(0);		//결재레벨을 지정해줘야 검증부분 확인가능
        
    	//요청번호가 없을 경우 요청번호를 먼저 채번한다.
    	if (!StringUtils.hasText(reqmst.getRqstNo())){
    		reqmst.setBizDcd("DIC");
    		reqmst.setBizDtlCd("DMP");
    		reqmst = requestMstService.getBizInfoInit(reqmst);
    	} else {
    		//요청번호가 있는 경우 해당 마스터 정보를 가져온다.
    		reqmst = requestMstService.getrequestMst(reqmst);
    	}
    	
    	model.addAttribute("waqMstr", reqmst);
    	
      	MstrAprPrcVO mapvo = new MstrAprPrcVO();
//      System.out.println("요청번호 : " + reqmst.getRqstNo());
//      System.out.println("요청구분 : " + reqmst.getBizDcd());
//      System.out.println("아이디 : " + ((LoginVO)session.getAttribute("loginVO")).getId());
      	mapvo.setRqst_no(reqmst.getRqstNo());
      	mapvo.setWrit_user_id(((LoginVO)session.getAttribute("loginVO")).getId());
      
      	int mapcount = approveLineServie.checkRequst(mapvo); 
//      System.out.println("카운트 : " + mapcount);
      	model.addAttribute("checkrequstcount", mapcount);
      	model.addAttribute("rqstno",reqmst.getRqstNo());
      	model.addAttribute("rqstbizdcd",reqmst.getBizDcd());
      
      
      	model.addAttribute("codeMap",getcodeMap());
    	
    	
    	
    	return "/dq/stnd/dbstndcode_rqst";
    	
	} 
    
    /** 코드 조회 */
    @RequestMapping("/dq/stnd/getDbCodelist.do")
	@ResponseBody
	public IBSheetListVO<DbWamCdVal> getDbCodeList(@ModelAttribute DbWamCdVal data, Locale locale) {

		logger.debug("reqvo:{}", data);

		List<DbWamCdVal> list = codeDfntService.getDbCodeList(data);


//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<DbWamCdVal>(list, list.size());

	}

    /** 코드 등록*/
    @RequestMapping("/dq/stnd/regDbCodeWamlist.do")
	@ResponseBody
	public IBSResultVO<WaqMstr> regDbCodeWamlist(@RequestBody DbWamCdVals data, WaqMstr reqmst, HttpSession session, Locale locale) throws Exception {
		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<DbWamCdVal> list = data.get("data");

		int result = codeDfntService.registerDbWam(list);
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
//		reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}
//    
    @RequestMapping("/dq/stnd/delDbCodeWamlist.do")
    @ResponseBody
	public IBSResultVO<WaqMstr> delDbCodeWamlist(@RequestBody DbWamCdVals data, WaqMstr reqmst, HttpSession session, Locale locale) throws Exception {
		logger.debug("reqmst:{} \ndata:{}", reqmst, data);

		ArrayList<DbWamCdVal> list = data.get("data");
        
		for(int i=0;i<list.size();i++) {
			list.get(i).setIbsStatus("D");
		}
		int result = codeDfntService.registerDbWam(list);
		
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
//		reqmst = requestMstService.getrequestMst(reqmst);

		return new IBSResultVO<WaqMstr>(reqmst, result, resmsg, action);
	}
    
    /** 코드정의서 비교 */
    @RequestMapping("/dq/stnd/dbcodepdmgap_lst.do")
	public String goDbCodePdmGap(WaqMstr reqmst, ModelMap model,HttpSession session) throws Exception {
    	logger.debug("WaqMstr:{}", reqmst);
    	
        
    	return "/dq/stnd/dbcodepdmgap_lst";
    	
	}     
    
    @RequestMapping("/dq/stnd/getcodegaplist.do")
    @ResponseBody
	public IBSheetListVO<WamNiaPdmCol> getCodeGapList(@ModelAttribute WamNiaPdmCol data, Locale locale) throws Exception {

		logger.debug("reqvo:{}", data);

		List<WamNiaPdmCol> list = codeDfntService.getCodeGapList(data);


//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamNiaPdmCol>(list, list.size());

	}
    
    @RequestMapping("/dq/stnd/getdbcodegaplist.do")
    @ResponseBody
	public IBSheetListVO<WamNiaPdmCol> getDbCodeGapList(@ModelAttribute WamNiaPdmCol data, Locale locale) throws Exception {

		logger.debug("reqvo:{}", data);

		List<WamNiaPdmCol> list = codeDfntService.getDbCodeGapList(data);


//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamNiaPdmCol>(list, list.size());

	}
    
    @RequestMapping("/dq/stnd/getcodeexistlist.do")
    @ResponseBody
	public IBSheetListVO<WamNiaPdmCol> getCodeExistList(@ModelAttribute WamNiaPdmCol data, Locale locale) throws Exception {

		logger.debug("reqvo:{}", data);

		List<WamNiaPdmCol> list = codeDfntService.getCodeExistList(data);


//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamNiaPdmCol>(list, list.size());

	}
    
    @RequestMapping("/dq/stnd/getdbcodeexistlist.do")
    @ResponseBody
	public IBSheetListVO<WamNiaPdmCol> getDbCodeExistList(@ModelAttribute WamNiaPdmCol data, Locale locale) throws Exception {

		logger.debug("reqvo:{}", data);

		List<WamNiaPdmCol> list = codeDfntService.getDbCodeExistList(data);


//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamNiaPdmCol>(list, list.size());

	}
}
