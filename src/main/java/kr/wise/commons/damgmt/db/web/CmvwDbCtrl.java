package kr.wise.commons.damgmt.db.web;

/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : CmvwDbController.java
 * 2. Package : kr.wise.cmvw.db.controller
 * 3. Comment :
 * 4. 작성자  : jwoolee(이정우)
 * 5. 작성일  : 2013. 5. 28.
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    jwoolee 	: 2013. 5. 28.	: 신규 개발.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseConfig;
import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.db.service.CmvwDbService;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.commons.damgmt.db.service.WaaTblSpac;
import kr.wise.commons.damgmt.schedule.service.ScheduleManagerService;
import kr.wise.commons.damgmt.schedule.service.WamShd;
import kr.wise.commons.damgmt.schedule.service.WamShdJob;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.SchedulerUtils;
import kr.wise.commons.util.UtilDate;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgTblVO;
import kr.wise.dq.criinfo.anatrg.service.WaaDbSch;

import org.apache.ibatis.annotations.Param;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName : CmvwDbController
 * 2. Package  : kr.wise.commons.damgmt.db.controller
 * 3. Comment  :
 * 4. 작성자   : jwoolee(이정우)
 * 5. 작성일   : 2013. 5. 28.
 * </PRE>
 */
@Controller
public class CmvwDbCtrl {

	Logger logger = LoggerFactory.getLogger(getClass());

	static class WaaDbConnTrgVOs extends HashMap<String, ArrayList<WaaDbConnTrgVO>> { }

	static class WaaDbSchs extends HashMap<String, ArrayList<WaaDbSch>> { }

	private Map<String, Object> codeMap;

	@Inject
	private CmvwDbService cmvwDbService;

	@Inject
	private MessageSource message;

	@Inject
	private CodeListService codeListService;

	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	ScheduleManagerService scheduleManagerService;
	
	static class WamShdJobVO extends HashMap<String, ArrayList<WamShdJob>> { }


	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	/**
	 * <PRE>
	 * 1. MethodName : getcodeMap
	 * 2. Comment    : 공통코드 맵 모델 생성 for View(JSP)
	 * 3. 작성자       : jwoolee(이정우)
	 * 4. 작성일       : 2013. 4. 17.
	 * </PRE>
	 *   @return Map<String,String>
	 *   @return
	 */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//목록성 코드(시스템영역 코드리스트)
		String dbmstypvers 		= UtilJson.convertJsonString(codeListService.getCodeList("dbmstypvers"));
       // tblSpacPnm = UtilJson.convertJsonString(codeListService.getCodeListIBS("tblSpacPnm"));
        //String idxSpacPnm = UtilJson.convertJsonString(codeListService.getCodeListIBS("idxSpacPnm"));
		
		codeMap.put("dbmstypvers", dbmstypvers);
	
		//공통 코드(요청구분 코드리스트)
		String dbmsverscdibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DBMS_VERS_CD"));
		String dbmstypcdibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DBMS_TYP_CD"));
		String regTypCdibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		

		codeMap.put("dbmsverscdibs", dbmsverscdibs);
		codeMap.put("dbmstypcdibs", dbmstypcdibs);
		codeMap.put("regTypCdibs", regTypCdibs);
		

		codeMap.put("dbmsTypCd", cmcdCodeService.getCodeList("DBMS_TYP_CD"));
		
		//진단대상(DB_CONN_TRG_ID) 정보
		List<CodeListVo> connTrgDbms   = codeListService.getCodeList("connTrgDbms");
		codeMap.put("connTrgDbmsibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(connTrgDbms)));
		codeMap.put("connTrgDbms", connTrgDbms);
		
		return codeMap;
	}

	@RequestMapping("/commons/damgmt/db/dbconntrg_lst.do")
	public String formpage() {
		return "/commons/damgmt/db/dbconntrg_lst";
	}

	@RequestMapping("/commons/damgmt/db/popup/dbschema_pop.do")
	public String godbschemapop(@ModelAttribute("search") WaaDbSch search ) {
        
		return "/commons/damgmt/db/popup/dbschema_pop";
	}

	@RequestMapping("/commons/damgmt/db/getdbschemalist.do")
	@ResponseBody
	public IBSheetListVO<WaaDbSch> getDbSchemaList(WaaDbSch search) {
		List<WaaDbSch> list = cmvwDbService.getDbSchemaList(search);

		return new IBSheetListVO<WaaDbSch>(list, list.size());

	}
	
	@RequestMapping("/commons/damgmt/db/popup/dbspac_pop.do")
	public String godbspacpop(@ModelAttribute("search") WaaTblSpac search,Model model, @RequestParam(value="row") String row ) {
         	model.addAttribute("row", row);
		return "/commons/damgmt/db/popup/dbspac_pop";
	}
	
	@RequestMapping("/commons/damgmt/db/getdevsubjdbschemalist.do")
	@ResponseBody
	public IBSheetListVO<WaaDbSch> getDevDbSchemaList(WaaDbSch search) {
		List<WaaDbSch> list = cmvwDbService.getDevSubjDbSchemaList(search);

		return new IBSheetListVO<WaaDbSch>(list, list.size());

	}


	@RequestMapping("/commons/damgmt/db/selectconntrglist.do")
	@ResponseBody
	public IBSheetListVO<WaaDbConnTrgVO> selectConnTrgList(@ModelAttribute WaaDbConnTrgVO search) {
		List<WaaDbConnTrgVO> list = cmvwDbService.getDbConnTrgList(search);
		//List<WaaDbConnTrgVO> list2 = (List<WaaDbConnTrgVO>) new WaaDbConnTrgVO();
//        for(int i=0; i< list.size() ; i++){
//           if(WiseConfig.SECURITY_APPLY.equals("Y")){
//              //list.get(i).setDbConnAcPwd(seed.EncryptUtils.getDecData(list.get(i).getDbConnAcPwd(), list.get(i).getDbConnAcId()));
//           }
//          // list2.add(vo);
//        }
        //logger.debug("ddd"+list2.toString());
		return new IBSheetListVO<WaaDbConnTrgVO>(list, list.size());
	}
	
	@RequestMapping("/commons/damgmt/db/popup/devdbschema_pop.do")
	public String goDevDbSchemaPop(@ModelAttribute("search") WaaDbSch search ) { 

		return "/commons/damgmt/db/popup/devdbschema_pop"; 
	}
	

	@RequestMapping("/commons/damgmt/db/regconntrglist.do")
	@ResponseBody
	public IBSResultVO<WaaDbConnTrgVO> regConnTrgList(@RequestBody WaaDbConnTrgVOs data, Locale locale) throws Exception {
		logger.debug("{}", data);
		ArrayList<WaaDbConnTrgVO> list = data.get("data");
		int result = cmvwDbService.regDbConnTrgList(list);
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		return new IBSResultVO<WaaDbConnTrgVO>(result, resmsg, action);



	}

	@RequestMapping("/commons/damgmt/db/delconntrglist.do")
	@ResponseBody
	public IBSResultVO<WaaDbConnTrgVO> delConnTrgList(@RequestBody WaaDbConnTrgVOs data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WaaDbConnTrgVO> list = data.get("data");

		int result = cmvwDbService.delDbConnTrgList(list);
		
		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		}else if (result == -2) {
			result = -1;
			//스키마 삭제후 DBMS삭제 가능합니다.
			resmsg = message.getMessage("DEL.SCH.AFT.DBMS.AVL", null, locale);		
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();

		return new IBSResultVO<WaaDbConnTrgVO>(result, resmsg, action);
	}

	@RequestMapping("/commons/damgmt/db/chkconntrglist.do")
	@ResponseBody
	public IBSResultVO chkConnTrgList(@RequestBody WaaDbConnTrgVOs data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WaaDbConnTrgVO> list = data.get("data");

		cmvwDbService.chkDbConnTrgList(list);
				
    	int result = 0;
    	String resmsg ;
    	
    	
		IBSResultVO ibsres = new IBSResultVO();
		ibsres.RESULT.CODE = 0;
		ibsres.RESULT.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return ibsres;
	}

	/** DBMS에 대한 스키마 grid_sub에서 조회 - IBSheet JSON */
	/** meta */
	@RequestMapping("/commons/damgmt/db/dbconntrg_dtl.do")
	@ResponseBody
	public IBSheetListVO<WaaDbSch> selectTrgSchemaList(@Param("dbConnTrgId") String dbConnTrgId) {
		List<WaaDbSch> list = cmvwDbService.getDbSchList(dbConnTrgId);

		return new IBSheetListVO<WaaDbSch>(list, list.size());

	}

	/** 스키마 등록 - IBSheet JSON
	 * @throws Exception */
	/** meta */
	@RequestMapping("/commons/damgmt/db/regSchList.do")
	@ResponseBody
	public IBSResultVO<WaaDbSch> regSchList(@RequestBody WaaDbSchs data, Locale locale) throws Exception {
		logger.debug("{}", data);
		ArrayList<WaaDbSch> list = data.get("data");
		int result = cmvwDbService.regDbSchList(list);
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = "SCH";
		return new IBSResultVO<WaaDbSch>(result, resmsg, action);
	}

	/** 스키마 리스트 삭제 - IBSheet JSON
	 * @throws Exception */
	@RequestMapping("/commons/damgmt/db/delSchList.do")
	@ResponseBody
	public IBSResultVO<WaaDbSch> delSchList(@RequestBody WaaDbSchs data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<WaaDbSch> list = data.get("data");

		int result = cmvwDbService.delDbSchList(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		}else if (result == -2) {
			result = -1;
			//삭제할 스키마에 DDL테이블이 존재합니다.
			resmsg = message.getMessage("DEL.SCH.DDL.EXISTS", null, locale);		
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = "SCH";


		return new IBSResultVO<WaaDbSch>(result, resmsg, action);
	}
	
	/** DBMS 접속 테스트 
	 * @param data
	/** @param locale
	/** @return
	/** @throws Exception meta
	 */
	@RequestMapping("/commons/damgmt/db/dbConnTrgConnTest.do")
	@ResponseBody
	public IBSResultVO<WaaDbConnTrgVO> DbConnTrgConnTest(@RequestBody WaaDbConnTrgVOs data, Locale locale) throws Exception {
		logger.debug("{}", data);
		ArrayList<WaaDbConnTrgVO> list = data.get("data");
		
		int result = cmvwDbService.dbConnTrgConnTest(list);
		
		
		//============DB계정 수집================
		WamShd saveVO = new WamShd(); 
		
		saveVO.setShdLnm("[DB계정수집]");
		saveVO.setShdKndCd("US"); 
		saveVO.setShdTypCd("O"); //배치형태  한번만
		saveVO.setShdStrDtm(UtilDate.getCurrentDate()); //배치시작일자  //현재날짜
		saveVO.setShdStrHr("00");  //시
		saveVO.setShdStrMnt("00");  //분
		saveVO.setShdUseYn("Y"); //스케줄사용여부
		saveVO.setShdBprYn("N"); //일괄처리여부
		saveVO.setRegTypCd("C");     		
		//스케줄관리 필터링 위해
		saveVO.setObjDescn("ONLINE"); 
		
		logger.debug("saveVO:{}", saveVO);
		
		//스케줄 _ HOME full  경로
		String schedulerpath = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.SCHDULER_PATH, null, Locale.getDefault()); 
		//Quartz 등록 shell 경로
		String schedulercmd = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.SCHDULER_CMD, null, Locale.getDefault()); 
    	
		logger.debug("schepath:{}\nschecmd:{}", schedulerpath, schedulercmd);
		
		ArrayList<WamShdJob> jobList = new ArrayList<WamShdJob>(); 
		
		for(WaaDbConnTrgVO dbVo : list){
    		
			WamShdJob jobVo = new WamShdJob();
			
			jobVo.setShdJobId(dbVo.getDbConnTrgId());
			jobVo.setShdJobNm("[DB계정수집]" + dbVo.getDbConnTrgPnm());
			jobVo.setEtcJobNm("[DB계정수집]" + dbVo.getDbConnTrgPnm());
			jobVo.setShdKndCd("US");  
			
			jobList.add(jobVo);
    	}
		
		//스케줄 마스터, 스케줄작업 저장
		result = scheduleManagerService.saveSchedule(jobList, saveVO);
		
		//Quartz 등록
		SchedulerUtils.registrySchedule(saveVO, schedulercmd);     
		
		//=======================================================
    	
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.CONNTEST", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("MSG.CONNTEST", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.CONNTEST.getAction();
		return new IBSResultVO<WaaDbConnTrgVO>(result, resmsg, action);
	}

	@RequestMapping("/commons/damgmt/db/dbSchCllt.do")
	@ResponseBody
	public IBSResultVO<WamShd> dbSchCllt(@RequestBody WaaDbConnTrgVOs data, Locale locale) throws Exception {
		
		ArrayList<WaaDbConnTrgVO> list = data.get("data");
		
    	int result = 0;
    	String resmsg = "";
    	
    	
    	//============DB계정 수집================
		WamShd saveVO = new WamShd(); 
		
		saveVO.setShdLnm("[스키마수집]");
		saveVO.setShdKndCd("SC"); 
		saveVO.setShdTypCd("O"); //배치형태  한번만
		saveVO.setShdStrDtm(UtilDate.getCurrentDate()); //배치시작일자  //현재날짜
		saveVO.setShdStrHr("00");  //시
		saveVO.setShdStrMnt("00");  //분
		saveVO.setShdUseYn("Y"); //스케줄사용여부
		saveVO.setShdBprYn("N"); //일괄처리여부
		saveVO.setRegTypCd("C");     		
		//스케줄관리 필터링 위해
		saveVO.setObjDescn("ONLINE"); 
		
		logger.debug("saveVO:{}", saveVO);
		
		//스케줄 _ HOME full  경로
		String schedulerpath = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.SCHDULER_PATH, null, Locale.getDefault()); 
		//Quartz 등록 shell 경로
		String schedulercmd = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.SCHDULER_CMD, null, Locale.getDefault()); 
    	
		logger.debug("schepath:{}\nschecmd:{}", schedulerpath, schedulercmd);
		
		ArrayList<WamShdJob> jobList = new ArrayList<WamShdJob>(); 
		
		//Quartz server 구동 확인
		if(SchedulerUtils.testConnectSchedulerServer(schedulerpath)) {
			for(WaaDbConnTrgVO dbVo : list){
	    		
				WamShdJob jobVo = new WamShdJob();
				
				jobVo.setShdJobId(dbVo.getDbConnTrgId());
				jobVo.setShdJobNm("[스키마수직]" + dbVo.getDbConnTrgPnm());
				jobVo.setEtcJobNm("[스키마수집]" + dbVo.getDbConnTrgPnm());
				jobVo.setShdKndCd("SC");
				
				jobList.add(jobVo);
	    	}
			
			//스케줄 마스터, 스케줄작업 저장
			result = scheduleManagerService.saveSchedule(jobList, saveVO);
			
			//Quartz 등록
			SchedulerUtils.registrySchedule(saveVO, schedulercmd);     
			
			//=======================================================
	
			if(result > 0) {
	    		result = 0;
	    		resmsg = "스키마수집이 시작되었습니다."; //message.getMessage("MSG.QUARTZ", null, locale);
	    	}
	    	else {
	    		result = -1;
	    		resmsg = message.getMessage("ERR.SAVE", null, locale);
	    	}
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.QUARTZ", null, locale);
		}
		
    	String action = WiseMetaConfig.IBSAction.REG.getAction();

    	return new IBSResultVO<WamShd>(saveVO, result, resmsg, action);
	}

}
