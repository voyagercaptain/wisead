package kr.wise.dq.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseConfig;
import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.damgmt.schedule.service.ScheduleManagerService;
import kr.wise.commons.damgmt.schedule.service.WamShd;
import kr.wise.commons.damgmt.schedule.service.WamShdJob;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.SchedulerUtils;
import kr.wise.commons.util.UtilDate;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilString;
//import kr.wise.dq.criinfo.anatrg.service.AnaTrgTblVO;
import kr.wise.dq.scheduler.service.DqliteSchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("DqliteSchMngCtrl")
public class DqliteSchMngCtrl {

	Logger logger = LoggerFactory.getLogger(getClass());

	static class WamShdVO extends HashMap<String, ArrayList<WamShd>> { }

	private Map<String, Object> codeMap;

	@Inject
	ScheduleManagerService scheduleManagerService;
	
	//@Inject
	//DqliteSchService dqliteSchService;

	@Inject
	private CodeListService codelistService;

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService;

	@Inject
	private MessageSource message;
	
	@Value("#{configure['wiseda.user.div.yn']}")     
	String userDivYn;
	
	@Value("#{configure['wiseda.cmn.mng.user.id']}")     
	String cmnMngUserId;
	
	static class WamShdJobVO extends HashMap<String, ArrayList<WamShdJob>> { }

	/** ???????????? ??? ????????? ?????????????????? ????????????. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//??????????????? JSON IBSheet???
		String schdTypeCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("SCHD_TYPE_CD"));
		String schdKndCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("SHD_KND_CD"));
		String etcJobKndCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("ETC_JOB_KND_CD"));
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		String anaStsCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("JOBSTSCD"));

		//???????????? - IBSheet Combo Code???
		codeMap.put("schdTypeCdibs", schdTypeCd);
		codeMap.put("schdKndCdibs", schdKndCd);
		codeMap.put("etcJobKndCdibs", etcJobKndCd);
		codeMap.put("regTypCdibs", regTypCd);
		codeMap.put("anaStsCdibs", anaStsCd);

		//??????????????? ??????
		String regtypcd = UtilJson.convertJsonString(cmcdCodeService.getCodeList("REG_TYP_CD"));
		String shdtypcd = UtilJson.convertJsonString(cmcdCodeService.getCodeList("SCHD_TYPE_CD"));

		codeMap.put("regtypcd", regtypcd);
		codeMap.put("shdtypcd", shdtypcd);
			
		codeMap.put("shdKndCdibs", schdKndCd); 
			
		//???????????? - selectbox ???
		codeMap.put("shdKndCd", cmcdCodeService.getCodeList("SHD_KND_CD"));
		codeMap.put("schdHourCd", cmcdCodeService.getCodeList("SCHD_HOUR_CD"));
		codeMap.put("schdMinCd", cmcdCodeService.getCodeList("SCHD_MIN_CD"));
		codeMap.put("shdTypeCd", cmcdCodeService.getCodeList("SCHD_TYPE_CD"));
		codeMap.put("shdWeekCd", cmcdCodeService.getCodeList("SCHD_WEEK_CD"));
		codeMap.put("shdMonthCd", cmcdCodeService.getCodeList("SCHD_MONTH_CD"));
		codeMap.put("prfKndCd", cmcdCodeService.getCodeList("PRF_KND_CD"));
		codeMap.put("etcJobKndCd", cmcdCodeService.getCodeList("ETC_JOB_KND_CD"));
		codeMap.put("anaStsCd", cmcdCodeService.getCodeList("JOBSTSCD"));

		codeMap.put("vrfcTyp", cmcdCodeService.getCodeList("VRFC_TYP"));
		//?????????
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("devConnTrgSch", connTrgSch);
		return codeMap;
	}

	//????????? ?????? ??? ????????? ??????
	@RequestMapping("/dq/scheduler/dqlite_sch_lst.do")
	public String formpage() {
		
		return "/dq/schedule/dqlite_sch_lst";
	}
	
	//???????????? ?????? ??????
	@RequestMapping("/dq/schedule/dqlite_sch_job_pop.do")
	public String selectJobPopList(String shdKndCd, String shdKndNm, String shdId, ModelMap model) {

		model.addAttribute("shdKndCd", shdKndCd);
		model.addAttribute("shdKndNm", shdKndNm);
		model.addAttribute("shdId", shdId);

		return "/dq/schedule/popup/dqlite_sch_job_pop";
	}
	
	//???????????? ?????? ??????
	/*
	@RequestMapping("/dq/schedule/getJobPopList.do")
	@ResponseBody
	public IBSheetListVO<WamShdJob> selectJobPopList(@ModelAttribute WamShdJob search) {
		logger.debug("????????????????????????:{}", search);
		
		//List<WamShdJob> list = dqliteSchService.getDqliteJobPopList(search);

		return new IBSheetListVO<WamShdJob>(list, list.size());
	}
	*/

	
	//????????? ?????? ????????????
	@RequestMapping("/dq/schedule/ajaxgrid/dqlite_sch_dtl.do")
	public String detailList(WamShd search, ModelMap model, String saction) {

		model.addAttribute("saction", "I");

		if(StringUtils.hasLength(search.getShdId())) {
			WamShd result = scheduleManagerService.getScheduleDtlList(search);
			model.addAttribute("result", result);
			model.addAttribute("saction", "U");
		}
		return "/dq/schedule/dqlite_sch_dtl";
	}

	//????????? ?????? ???????????? ????????????
	@RequestMapping("/dq/schedule/ajaxgrid/dqlite_sch_batch_dtl.do")
	public String selecDtltList(WamShd search, ModelMap model, String saction) {

		model.addAttribute("saction", "I");

		if(StringUtils.hasLength(search.getShdId())) {

			WamShd result = scheduleManagerService.getScheduleDtlList(search);
			model.addAttribute("result", result);
			model.addAttribute("saction", "U");
		}
		return "/dq/schedule/dqlite_sch_batch_dtl";
	}
	
	//????????? ?????? ??????
	@RequestMapping("/dq/scheduler/getScheduleList.do") 
	@ResponseBody
	public IBSheetListVO<WamShd> selectList(@ModelAttribute WamShd search) {
		logger.debug("{}", search);
		if("Y".equals(userDivYn)) {
			LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
			search.setMngUserId(UtilString.null2Blank(user.getUniqId()));
		} else {
			search.setMngUserId(cmnMngUserId);
		}
		List<WamShd> list = scheduleManagerService.getScheduleList(search);

		return new IBSheetListVO<WamShd>(list, list.size());
	}
	
	//???????????? ????????????
	@RequestMapping("/dq/scheduler/getScheduleJobList.do")
	@ResponseBody
	public IBSheetListVO<WamShd> selectjobList(@ModelAttribute WamShd search) {
		logger.debug("{}", search);

		List<WamShd> list = null;
		list = scheduleManagerService.getScheduleJobList(search);

		return new IBSheetListVO<WamShd>(list, list.size());
	}

	// ????????? ?????? ??????
    @RequestMapping("/dq/scheduler/ajaxgrid/insertSchedule.do")
    @ResponseBody
    public IBSResultVO<WamShd> saveSchedule(@RequestBody WamShdJobVO data, WamShd saveVO, Locale locale) throws Exception {
//    	logger.debug("data:{}", data);
    	ArrayList<WamShdJob> list = data.get("data");
    	int result = 0;
    	String resmsg ;
		if("Y".equals(userDivYn)) {
			LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
			saveVO.setMngUserId(UtilString.null2Blank(user.getUniqId()));
		} else {
			saveVO.setMngUserId(cmnMngUserId);
		}
    	
    	//??????????????? ??????????????? ?????? ??????????????? ?????????????????? ???????????? ???????????? ?????????????????????
    	if(UtilString.null2Blank(saveVO.getShdLnm()).equals("") ){
    		String shdLnm = "";
    		//????????????
    		if(saveVO.getShdKndCd().equals("QP")){
    			shdLnm = "[????????????] ????????? ????????????("+UtilDate.getCurrentDateHms()+")";
    		}
    		//????????????
    		else if(saveVO.getShdKndCd().equals("QB")){
    			shdLnm = "[????????????] ????????? ????????????("+UtilDate.getCurrentDateHms()+")";
    		}
    		    		
    		saveVO.setShdLnm(shdLnm);
    		saveVO.setShdTypCd("O"); //????????????  ?????????
    		saveVO.setShdStrDtm(UtilDate.getCurrentDate()); //??????????????????  //????????????
    		saveVO.setShdStrHr("00");  //???
    		saveVO.setShdStrMnt("00");  //???
    		saveVO.setShdUseYn("Y"); //?????????????????????
    		saveVO.setShdBprYn("N"); //??????????????????
    		saveVO.setRegTypCd("C");
    		
    		//??????????????? ????????? ??????
    		saveVO.setObjDescn("ONLINE"); 
    	}
    	
    	logger.debug("saveVO:{}", saveVO);
    	
    	//????????? _ HOME full  ??????
		String schedulerpath = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.SCHDULER_PATH, null, Locale.getDefault()); 
		//Quartz ?????? shell ??????
		String schedulercmd = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.SCHDULER_CMD, null, Locale.getDefault()); 
    	
		logger.debug("schepath:{}\nschecmd:{}", schedulerpath, schedulercmd);
		
		//Quartz server ?????? ??????
		if(SchedulerUtils.testConnectSchedulerServer(schedulerpath)) {
			//????????? ?????????, ??????????????? ??????
			result = scheduleManagerService.saveSchedule(list, saveVO);
			
			if(result > 0) {
	    		result = 0;
	    		resmsg = message.getMessage("MSG.QUARTZ", null, locale);
	    	}
	    	else {
	    		result = -1;
	    		resmsg = message.getMessage("ERR.SAVE", null, locale);
	    	}
//			Quartz ??????
			SchedulerUtils.registrySchedule(saveVO, schedulercmd);
		}else{
			result = -1;
			resmsg = message.getMessage("ERR.QUARTZ", null, locale);
		}

    	String action = WiseMetaConfig.IBSAction.REG.getAction();

    	return new IBSResultVO<WamShd>(saveVO, result, resmsg, action);
    }

    // ????????? ?????? ??????
	@RequestMapping("/dq/scheduler/delSchedule.do")
	@ResponseBody
	public IBSResultVO<WamShd> delScheduleList(@RequestBody WamShdJobVO data, Locale locale) throws Exception {

		List<WamShdJob> list = data.get("data");
//		logger.debug("delVO:{}", list);
		
		//????????? _ HOME full  ??????
		String schedulerpath = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.SCHDULER_PATH, null, Locale.getDefault()); 
		//Quartz ?????? shell ??????
		String schedulercmd = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.SCHDULER_CMD, null, Locale.getDefault()); 
		
		
		logger.debug("schepath:{}\nschecmd:{}", schedulerpath, schedulercmd);
		
		String resmsg;
		int result= 0;
		//Quartz server ?????? ??????
		if(SchedulerUtils.testConnectSchedulerServer(schedulerpath)) {
			//rep db ??????
			result  = scheduleManagerService.delSchedule(list);
			if(result > 0) {
				result = 0;
				resmsg = message.getMessage("MSG.SAVE", null, locale);
			} else {
				result = -1;
				resmsg = message.getMessage("ERR.SAVE", null, locale);
			}
			
			//Quartz ??????
			for(WamShd saveVO :  list){
				SchedulerUtils.registrySchedule(saveVO, schedulercmd);
			}
			
		}else{
			result = -1;
			resmsg = message.getMessage("ERR.QUARTZ", null, locale);
		}


		String action = WiseMetaConfig.IBSAction.DEL.getAction();

		return new IBSResultVO<WamShd>(result, resmsg, action);
		
	}
	
	//????????? ?????? ??????
	@RequestMapping("/dq/scheduler/delScheduleJob.do")
	@ResponseBody
	public IBSResultVO<WamShd> delScheduleJobList(@RequestBody WamShdJobVO data, Locale locale, String shdId) throws Exception {

		List<WamShdJob> list = data.get("data");
		//logger.debug("delVO:{}", list);

		int result  = scheduleManagerService.delScheduleJob(list, shdId);
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale); 
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL_DTL.getAction();
		return new IBSResultVO<WamShd>(result, resmsg, action);

	}
	
}
