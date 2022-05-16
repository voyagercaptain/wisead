/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ColProfileCtrl.java
 * 2. Package : kr.wise.dq.criinfo.profile.web
 * 3. Comment :
 * 4. 작성자  : hwang
 * 5. 작성일  : 2014. 3. 24. 오후 1:29:21
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    hwang : 2014. 3. 24. :            : 신규 개발.
 */
package kr.wise.dq.profile.mstr.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseConfig;
import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.schedule.service.ScheduleManagerService;
import kr.wise.commons.damgmt.schedule.service.WamShd;
import kr.wise.commons.damgmt.schedule.service.WamShdJob;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.util.SchedulerUtils;
import kr.wise.commons.util.UtilDate;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.bizrule.service.WaqBrMstr;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgColVO;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgService;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgTblVO;
import kr.wise.dq.metadmn.service.MetaDmnCdValItfVO;
import kr.wise.dq.metadmn.service.MetaDmnItfVO;
import kr.wise.dq.metadmn.service.MetaInterfaceService;
import kr.wise.dq.profile.anares.service.ProfileResultService;
import kr.wise.dq.profile.anares.service.WamPrfResultVO;
import kr.wise.dq.profile.colana.service.ProfileColAnaService;
import kr.wise.dq.profile.colana.service.WamPrfColAnaVO;
import kr.wise.dq.profile.coldtfrmana.service.ProfilePC03Service;
import kr.wise.dq.profile.coldtfrmana.service.WamPrfDtfrmAnaVO;
import kr.wise.dq.profile.colefvaana.service.ProfilePC02Service;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaAnaVO;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaUserDfVO;
import kr.wise.dq.profile.colptrana.service.ProfilePC05Service;
import kr.wise.dq.profile.colptrana.service.WamPrfPtrAnaVO;
import kr.wise.dq.profile.colrngana.service.ProfilePC04Service;
import kr.wise.dq.profile.colrngana.service.WamPrfRngAnaVO;
import kr.wise.dq.profile.mstr.service.ProfileMstrService;
import kr.wise.dq.profile.mstr.service.WamPrfMstrCommonVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.dq.profile.reac.service.ProfileReacService;
import kr.wise.dq.profile.reac.service.WamPrfReacColVO;
import kr.wise.dq.profile.reac.service.WamPrfReacTblVO;
import kr.wise.dq.profile.sqlgenerator.SqlGeneratorMng;
import kr.wise.dq.profile.sqlgenerator.SqlGeneratorVO;
import kr.wise.dq.profile.tblrel.service.ProfilePT01Service;
import kr.wise.dq.profile.tblrel.service.WamPrfRelColVO;
import kr.wise.dq.profile.tblrel.service.WamPrfRelTblVO;
import kr.wise.dq.profile.tblunq.service.ProfilePT02Service;
import kr.wise.dq.profile.tblunq.service.WamPrfUnqColVO;

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
 * 2. FileName  : ColProfileCtrl.java
 * 3. Package  : kr.wise.dq.criinfo.profile.web
 * 4. Comment  :
 * 5. 작성자   : hwang
 * 6. 작성일   : 2014. 3. 24. 오후 1:29:21
 * </PRE>
 */
@Controller
public class ProfileMstrCtrl {

	private final  Logger logger = LoggerFactory.getLogger(getClass());


	/** JSON 2 ArrayList 변환 - 프로그램 리스트를 받을 클래스 선언 */
	static class WamPrfMstrVOs extends HashMap<String, ArrayList<WamPrfMstrVO>> { }

	//사용자정의 유효값
	static class WamPrfEfvaUserDfVOs extends HashMap<String, ArrayList<WamPrfEfvaUserDfVO>> { }

	//사용자정의 패턴
	static class WamPrfPtrAnaVOs extends HashMap<String, ArrayList<WamPrfPtrAnaVO>> { }

	//관계분석
	static class WamPrfRelColVOs extends HashMap<String, ArrayList<WamPrfRelColVO>> { }

	//중복분석
	static class WamPrfUnqColVOs extends HashMap<String, ArrayList<WamPrfUnqColVO>> { }
	
	//관계분석
	static class WamPrfReacColVOs extends HashMap<String, ArrayList<WamPrfReacColVO>> { }
	
	static class WamShdJobVO extends HashMap<String, ArrayList<WamShdJob>> { }
	
	static class AnaTrgTblVOs extends HashMap<String, ArrayList<AnaTrgTblVO>> { }

	private Map<String, Object> codeMap;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private MessageSource message;

	@Inject
	private CodeListService codelistService;

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private AnaTrgService anaTrgService;

	//프로파일 마스터
	@Inject
	private ProfileMstrService profileMstrService;

	//프로파일 결과
	@Inject
	private ProfileResultService profileResultService;

	//컬럼분석
	@Inject
	private ProfileColAnaService pc01Service;

	//유효값분석
	@Inject
	private ProfilePC02Service pc02Service;

	//날짜형식분석
	@Inject
	private ProfilePC03Service pc03Service;

	//범위분석
	@Inject
	private ProfilePC04Service pc04Service;

	//패턴분석
	@Inject
	private ProfilePC05Service pc05Service;

	//관계분석
	@Inject
	private ProfilePT01Service pt01Service;

	//중복분석
	@Inject
	private ProfilePT02Service pt02Service;

	//메타도메인정보조회
	@Inject
	private MetaInterfaceService metaItfService;
	
	//관계분석
	@Inject
	private ProfileReacService reacService;
	
	@Inject
	private ScheduleManagerService scheduleManagerService;

	@Value("#{configure['wiseda.user.div.yn']}")     
	String userDivYn;
	
	@Value("#{configure['wiseda.cmn.mng.user.id']}")     
	String cmnMngUserId;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//시스템영역 코드리스트 JSON
		String rqstDcd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_DCD"));
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		String dbmsTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DBMS_TYP_CD"));
		String dbmsVersCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DBMS_VERS_CD"));
		//프로파일종류코드
		String prfKndCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PRF_KND_CD"));

		String anaStsCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("JOBSTSCD"));

		//진단대상
		List<CodeListVo> connTrgDbms = codelistService.getCodeList(CodeListAction.connTrgDbms);

		//실행차수
		List<CodeListVo> anaDgr = codelistService.getCodeList(CodeListAction.anaDgr);

		//form 태그 사용
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		codeMap.put("dbmsTypCd",cmcdCodeService.getCodeList("DBMS_TYP_CD"));
		codeMap.put("dbmsVersCd",cmcdCodeService.getCodeList("DBMS_VERS_CD"));
		codeMap.put("prfKndCd",cmcdCodeService.getCodeList("PRF_KND_CD"));
		codeMap.put("rngCnc",cmcdCodeService.getCodeList("RNG_CNC_CD"));
		//유효값유형
		codeMap.put("efvaAnaKndCd",cmcdCodeService.getCodeList("EFVA_ANA_KND_CD"));
		//유효값유형
		codeMap.put("efvaAnaKndCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("EFVA_ANA_KND_CD")));
		//날짜형식코드
		codeMap.put("dateFrmCd",cmcdCodeService.getCodeList("DATE_FRM_CD"));
		//범위분석코드
		codeMap.put("rngOprCd",cmcdCodeService.getCodeList("RNG_OPR_CD"));
		codeMap.put("connTrgDbmsCd", connTrgDbms);
		//실행차수
		codeMap.put("anaDgrCd", anaDgr);
		
		//결재방식코드
		codeMap.put("vrfCdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("VRF_CD")));
		codeMap.put("vrfCd", cmcdCodeService.getCodeList("VRF_CD"));

		//공통코드 - IBSheet Combo Code용
		codeMap.put("anaStsCdibs", anaStsCd);
		codeMap.put("rqstDcdibs", rqstDcd);
		codeMap.put("regTypCdibs", regTypCd);
		codeMap.put("dbmsTypCdibs", dbmsTypCd);
		codeMap.put("dbmsVersCdibs", dbmsVersCd);
		//프로파일종류 IB시트 콤보
		codeMap.put("prfKndCdibs", prfKndCd);
		codeMap.put("connTrgDbmsibs", UtilJson.convertJsonString(codelistService.getCodeListIBS(connTrgDbms)));

		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSch   = UtilJson.convertJsonString(codelistService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSch", connTrgSch);

		return codeMap;
	}

	//컬럼 프로파일 관리 요청번호가 없을 경우 채번하여 리턴한다.
	@RequestMapping("/dq/profile/colprf_rqst.do")
	public String goColprfrqstForm(ModelMap model) throws Exception{
		 // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
    	return "/dq/profile/colprf_rqst";
	}

	@RequestMapping("/dq/profile/tblprf_rqst.do")
	public String goTblprfrqstForm(ModelMap model) throws Exception{
		// 0. Spring Security 사용자권한 처리
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
		if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "egovframework/com/uat/uia/EgovLoginUsr";
		}
		return "/dq/profile/tblprf_rqst";
	}
	
	@RequestMapping("/dq/profile/reac_rqst.do")
	public String goReacForm(ModelMap model) throws Exception{
		// 0. Spring Security 사용자권한 처리
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
		if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "egovframework/com/uat/uia/EgovLoginUsr";
		}
		return "/dq/profile/reac_rqst";
	}

	//컬럼명 검색 팝업
	@RequestMapping("/dq/profile/popup/collst_pop.do")
	public String colLstPop(@ModelAttribute("search") AnaTrgTblVO anaTrgTblVO,  ModelMap model ) {
		// 0. Spring Security 사용자권한 처리
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
		if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "egovframework/com/uat/uia/EgovLoginUsr";
		}

		return "/dq/profile/popup/collst_pop";
	}

	/* 진단대상 컬럼 조회 */
	@RequestMapping("/dq/profile/getPrfColLst.do")
	@ResponseBody
	public IBSheetListVO<AnaTrgColVO> getPrfColLst(@ModelAttribute AnaTrgTblVO search, Locale locale) {
		logger.debug("search {}", search);
		List<AnaTrgColVO> list = anaTrgService.getPrfColLst(search);
//		logger.debug("list {}", list);
		return new IBSheetListVO<AnaTrgColVO>(list, list.size());
	}
	
	/* 진단대상 컬럼 조회 */
	@RequestMapping("/dq/profile/getPrfColLstCheck.do")
	@ResponseBody
	public IBSheetListVO<AnaTrgColVO> getPrfColLstCheck(@ModelAttribute AnaTrgTblVO search, Locale locale) {
		logger.debug("search {}", search);
		if("Y".equals(userDivYn)) {
			LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
			search.setMngUserId(UtilString.null2Blank(user.getUniqId()));
		} else {
			search.setMngUserId(cmnMngUserId);
		}
		List<AnaTrgColVO> list = anaTrgService.getPrfColLstCheck(search);
//		logger.debug("list {}", list);
		return new IBSheetListVO<AnaTrgColVO>(list, list.size());
	}
	
	/* 진단대상 컬럼 조회 (코드테이블 조회용 Redline 제거...)  */
	/** @param search
	/** @param locale
	/** @return meta */
	@RequestMapping("/dq/profile/getPrfColLstNotRedline.do")
	@ResponseBody
	public IBSheetListVO<AnaTrgColVO> getPrfColLstNotRedline(@ModelAttribute AnaTrgTblVO search, Locale locale) {
		logger.debug("search {}", search);
		List<AnaTrgColVO> list = anaTrgService.getPrfColLstNotRedline(search);
		logger.debug("list {}", list);
		return new IBSheetListVO<AnaTrgColVO>(list, list.size());
	}

	/* 진단대상 테이블 상세 조회 */
	@RequestMapping("/dq/profile/getAnaTrgTblDetail.do")
	@ResponseBody
	public AnaTrgTblVO getAnaTrgTblDetail(@ModelAttribute("search") AnaTrgTblVO search, Locale locale) {
		String sTblColGb = search.getTblColGb();
		search =  anaTrgService.getAnaTrgTblDtl(search);
		search.setTblColGb(sTblColGb);

		logger.debug(" {} search "+search);
		return search;
	}
	
	/* 진단대상 테이블 상세 조회 */
	@RequestMapping("/dq/profile/getAnaTrgTblReacDetail.do")
	@ResponseBody
	public AnaTrgTblVO getAnaTrgTblReacDetail(@ModelAttribute("search") AnaTrgTblVO search, Locale locale) {
		String sTblColGb = search.getTblColGb();
		if("Y".equals(userDivYn)) {
			LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
			search.setMngUserId(UtilString.null2Blank(user.getUniqId()));
		} else {
			search.setMngUserId(cmnMngUserId);
		}
		
		search =  anaTrgService.getAnaTrgTblReacDtl(search);
		search.setTblColGb(sTblColGb);

		logger.debug(" {} search "+search);
		return search;
	}
	
	@RequestMapping("/dq/profile/delPT01Lst.do")
	@ResponseBody
	public IBSResultVO<AnaTrgTblVO> delPT01Lst(@RequestBody AnaTrgTblVOs data, Locale locale) throws Exception {
		logger.debug("{}", data);
		
		ArrayList<AnaTrgTblVO> list = data.get("data"); 
		
		int result = pt01Service.delPT01Lst(list);    
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();
		return new IBSResultVO<AnaTrgTblVO>(result, resmsg, action);

	}


	/* 진단대상 컬럼 상세 조회 */
	@RequestMapping("/dq/profile/getAnaTrgColDetail.do")
	@ResponseBody
	public AnaTrgTblVO getAnaTrgColDetail(@ModelAttribute AnaTrgTblVO search, Locale locale) {
		String sTblColGb = search.getTblColGb();
		search =  anaTrgService.getAnaTrgColDtl(search);
		search.setTblColGb(sTblColGb);
		return search;
	}

	/* 프로파일 조회 */
	@RequestMapping("/dq/profile/getPrfLst.do")
	@ResponseBody
	public IBSheetListVO<WamPrfResultVO> getPrfLst( WamPrfResultVO search, Locale locale) {

		logger.debug("{}", search);
		List<WamPrfResultVO> list = profileResultService.getPrfLst(search);

		logger.debug("{}", list);

		return new IBSheetListVO<WamPrfResultVO>(list, list.size());

	}

	/* 컬럼프로파일 상세 조회 */
	@RequestMapping("/dq/profile/getPrfDtl.do")
	@ResponseBody
	public Map<String, Object> getPrfDtl(WamPrfResultVO search, Locale locale){
		logger.debug(" {}", search );
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		String prfId = search.getPrfId();
		String prfKndCd = search.getPrfKndCd();
		String schAnaStrDtm = search.getSchAnaStrDtm();

		if(!UtilObject.isNull(prfId)) {
			//컬럼분석 상세조회
			if(prfKndCd.equals("PC01")){
				WamPrfColAnaVO result = pc01Service.getColAnaDtl(prfId);
				rtnMap.put("resultVO", result);
			}
			//유효값 분석 상세 조회
			else if(prfKndCd.equals("PC02")){
				WamPrfEfvaAnaVO result = pc02Service.getPrfPC02Dtl(prfId);
				rtnMap.put("resultVO", result);
			}
			//날짜형식 분석 상세 조회
			else if(prfKndCd.equals("PC03")){
				WamPrfDtfrmAnaVO result = pc03Service.getPrfPC03Dtl(prfId);
				rtnMap.put("resultVO", result);
			}
			//범위 분석 상세 조회
			else if(prfKndCd.equals("PC04")){
				WamPrfRngAnaVO result = pc04Service.getPrfPC04Dtl(prfId);
				rtnMap.put("resultVO", result);
			}
			//패턴 분석 상세 조회
			else if(prfKndCd.equals("PC05")){
				WamPrfPtrAnaVO result = pc05Service.getPrfPC05Dtl(prfId);
				rtnMap.put("resultVO", result);
			}
			//관계분석
			else if(prfKndCd.equals("PT01")){
				//WamPrfRelTblVO result = pt01Service.getPrfPT01Dtl(prfId);
				//rtnMap.put("resultVO", result);
				
				//참조무결성
				WamPrfReacTblVO result = reacService.getPrfReacDtl(prfId);
				rtnMap.put("resultVO", result);
			}

			//중복분석
			else if(prfKndCd.equals("PT02")){
				WamPrfUnqColVO result = pt02Service.getPrfPT02Dtl(prfId);
				rtnMap.put("resultVO", result);
			}
			
			//관계분석
			/*else if(prfKndCd.equals("PT01")){
				WamPrfReacTblVO result = reacService.getPrfReacDtl(prfId);
				rtnMap.put("resultVO", result);
			}*/
		}
		rtnMap.put("prfId", prfId);
		rtnMap.put("prfKndCd", prfKndCd);
		rtnMap.put("schAnaStrDtm", schAnaStrDtm);
		return rtnMap;
	}

	/* 사용자 정의 유효값 조회 */
	@RequestMapping("/dq/profile/getColPrfPC02UserLst.do")
	@ResponseBody
	public IBSheetListVO<WamPrfEfvaUserDfVO> getColPrfPC02UserLst(String prfId, Locale locale) {
		logger.debug("{}", prfId);
		List<WamPrfEfvaUserDfVO> list =pc02Service.getPrfPC02UserDfLst(prfId);
		return new IBSheetListVO<WamPrfEfvaUserDfVO>(list, list.size());

	}
	/* 사용자 정의 패턴 조회 */
	@RequestMapping("/dq/profile/getColPrfPC05UserLst.do")
	@ResponseBody
	public IBSheetListVO<WamPrfPtrAnaVO> getColPrfPC05UserLst(String prfId, Locale locale) {
		logger.debug("{}", prfId);
		List<WamPrfPtrAnaVO> list =pc05Service.getPrfPC05UserDfLst(prfId);
		return new IBSheetListVO<WamPrfPtrAnaVO>(list, list.size());
	}
	/* 관계분석컬럼 조회 */
	@RequestMapping("/dq/profile/getPrfPT01RelColLst.do")
	@ResponseBody
	public IBSheetListVO<WamPrfRelColVO> getPrfPT01RelColLst(String prfId, Locale locale) {
		logger.debug("{}", prfId);
		List<WamPrfRelColVO> list =pt01Service.getPrfPT01RelColLst(prfId);
		return new IBSheetListVO<WamPrfRelColVO>(list, list.size());
	}

	/* 중복컬럼 조회 */
	@RequestMapping("/dq/profile/getPrfUnqColLst.do")
	@ResponseBody
	public IBSheetListVO<WamPrfUnqColVO> getPrfUnqColLst(String prfId, Locale locale) {
		logger.debug("{}", prfId);
		List<WamPrfUnqColVO> list =pt02Service.getPrfPT02UnqColLst(prfId);
		return new IBSheetListVO<WamPrfUnqColVO>(list, list.size());
	}
	
	/* 관계분석컬럼 조회 */
	@RequestMapping("/dq/profile/getPrfReacRelColLst.do")
	@ResponseBody
	public IBSheetListVO<WamPrfReacColVO> getPrfReacRelColLst(String prfId, Locale locale) {
		logger.debug("{}", prfId);
		List<WamPrfReacColVO> list =reacService.getPrfReacRelColLst(prfId);
		return new IBSheetListVO<WamPrfReacColVO>(list, list.size());
	}

	/**  유효값분석 코드 테이블 조회 팝업 */
	@RequestMapping("/dq/profile/popup/anatrgcodetblcol_pop.do")
	public String formPoppage(String sflag,  @ModelAttribute AnaTrgTblVO search, ModelMap model) {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
    	model.addAttribute("anaTrgTblVO", search);
		return "/dq/profile/popup/anatrgcodetblcol_pop";
	}

	/**  관계정보 셋팅 팝업 */
	@RequestMapping("/dq/profile/popup/anatrgreltblcol_pop.do")
	public String formRelColPoppage(@ModelAttribute AnaTrgTblVO search, @ModelAttribute WamPrfRelTblVO wamRelTblVO, ModelMap model) {
		// 0. Spring Security 사용자권한 처리
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
		if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "egovframework/com/uat/uia/EgovLoginUsr";
		}
		model.addAttribute("anaTrgTblVO", search);
		model.addAttribute("wamRelTblVO", wamRelTblVO);
		return "/dq/profile/popup/anatrgreltblcol_pop";
	}
	
	/**  관계정보 셋팅 팝업 */
	@RequestMapping("/dq/profile/popup/anatrgreactblcol_pop.do")
	public String formReacColPoppage(@ModelAttribute AnaTrgTblVO search, @ModelAttribute WamPrfReacTblVO wamReacTblVO, ModelMap model) {
		// 0. Spring Security 사용자권한 처리
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
		if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "egovframework/com/uat/uia/EgovLoginUsr";
		}
		model.addAttribute("anaTrgTblVO", search);
		model.addAttribute("wamReacTblVO", wamReacTblVO);
		return "/dq/profile/popup/anatrgreactblcol_pop";
	}

	@RequestMapping("/dq/profile/registerProfile.do")
	@ResponseBody
	public IBSResultVO<WamPrfMstrVO> registerProfile(WamPrfMstrVO prfmst
																  , WamPrfColAnaVO pc01Vo  //컬럼분석
																  , WamPrfEfvaAnaVO pc02Vo //유효값분석
																  , WamPrfDtfrmAnaVO pc03Vo //날짜형식분석
																  , WamPrfRngAnaVO pc04Vo   //범위분석
															    //  , WamPrfPtrAnaVO pc05Vo    //패턴분석
																//  , @RequestBody WamPrfPtrAnaVOs  pc05VoLst
																//  , @RequestBody WamPrfEfvaUserDfVOs pc02UserVos body 테그때문에 오류남 차후 개발
																  , Locale locale) throws Exception {
		logger.debug("prfmst : {}", prfmst);

		//컬럼분석 [PC01]
		if(UtilString.null2Blank(prfmst.getPrfKndCd()).equals("PC01")){
			logger.debug("data : {}", pc01Vo);
			prfmst.setWamPrfColAnaVO(pc01Vo);
		}
		//유효값분석
		else if(UtilString.null2Blank(prfmst.getPrfKndCd()).equals("PC02")){
			logger.debug("pc02Vo : {}", pc02Vo);
			prfmst.setWamPrfEfvaAnaVO(pc02Vo);
		}
		//날짜형식분석
		else if(UtilString.null2Blank(prfmst.getPrfKndCd()).equals("PC03")){
			logger.debug("pc03Vo : {}", pc03Vo);
			prfmst.setWamPrfDtfrmAnaVO(pc03Vo);
		}
		//범위분석
		else if(UtilString.null2Blank(prfmst.getPrfKndCd()).equals("PC04")){
			logger.debug("pc04Vo : {}", pc04Vo);
			prfmst.setWamPrfRngAnaVO(pc04Vo);
		}

		//패턴분석
		else if(UtilString.null2Blank(prfmst.getPrfKndCd()).equals("PC05")){
//			logger.debug("pc05Vo : {}", pc05Vo);
			prfmst.setWamPrfPtrAnaVO(new ArrayList<WamPrfPtrAnaVO>());
		}

		ArrayList<WamPrfMstrVO> list = new ArrayList<WamPrfMstrVO>() ;
		list.add(prfmst);

		return saveProfile(list, locale);
	}
	
	@RequestMapping("/dq/profile/registerPC01.do")
	@ResponseBody
	public IBSResultVO<WamPrfMstrVO> registerPC01(@RequestBody WamPrfMstrVOs prfmst, Locale locale) throws Exception {
		logger.debug("prfmst : {}", prfmst);

		//컬럼분석 [PC01]
		//logger.debug("data : {}", prfmst.get(0).get(0).getWamPrfColAnaVO());
		//prfmst.setWamPrfColAnaVO(pc01Vo);

		//ArrayList<WamPrfMstrVO> list = new ArrayList<WamPrfMstrVO>() ;
		//list.add(prfmst);
		WamPrfColAnaVO colAnaVo = new WamPrfColAnaVO();
		colAnaVo.setMinMaxValAnaYn("Y");
		colAnaVo.setAonlYn("Y");
		colAnaVo.setLenAnaYn("Y");
		colAnaVo.setCrdAnaYn("Y");

		String mngUserId = "";
		if("Y".equals(userDivYn)) {
			LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
			mngUserId = UtilString.null2Blank(user.getUniqId());
		} else {
			mngUserId = cmnMngUserId;
		}
		
		ArrayList<WamPrfMstrVO> list = prfmst.get("data");
		for (WamPrfMstrVO saveVo : (ArrayList<WamPrfMstrVO>)list) {
			saveVo.setPrfKndCd("PC01");
			saveVo.setWamPrfColAnaVO(colAnaVo);
			saveVo.setMngUserId(mngUserId);
			
			if(saveVo.getObjNm()==null || saveVo.getObjNm().equals("")) {
				saveVo.setObjNm(saveVo.getDbcColNm());
			}
		}

		return saveProfile(list, locale);
	}

	/* 사용자정의 유효값 등록 */
	@RequestMapping("/dq/profile/regColPrfPC02UserDf.do")
	@ResponseBody
	public  IBSResultVO<WamPrfMstrVO> regColPrfPC02UserDf(WamPrfMstrVO prfmst, WamPrfEfvaAnaVO pc02Vo, @RequestBody WamPrfEfvaUserDfVOs pc02UserVos,  Locale locale) throws Exception {

		logger.debug("왜 안오는건데");
		logger.debug("prfmst : {}", prfmst);
		logger.debug("pc02Vo : {}", pc02Vo);
		logger.debug("data : {}", pc02UserVos.get("data"));

		//사용자정의유효값
		pc02Vo.setWamPrfEfvaUserDfVO(pc02UserVos.get("data"));
		//유효값분석 정보
		prfmst.setWamPrfEfvaAnaVO(pc02Vo);

		ArrayList<WamPrfMstrVO> list = new ArrayList<WamPrfMstrVO>() ;
		list.add(prfmst);

		return saveProfile(list, locale);

	}
	/*사용자 정의 패턴 등록*/
	@RequestMapping("/dq/profile/registerProfilePc05UserDf.do")
	@ResponseBody
	public  IBSResultVO<WamPrfMstrVO> registerProfilePc05UserDf(WamPrfMstrVO prfmst, @RequestBody WamPrfPtrAnaVOs  pc05Vos,  Locale locale) throws Exception {

		logger.debug("prfmst : {}", prfmst);
		logger.debug("data : {}", pc05Vos.get("data"));

		//사용자정의유효패턴
		prfmst.setWamPrfPtrAnaVO(pc05Vos.get("data"));

		ArrayList<WamPrfMstrVO> list = new ArrayList<WamPrfMstrVO>() ;
		list.add(prfmst);

		return saveProfile(list, locale);

	}

	@RequestMapping("/dq/profile/profile_list.do")
	public String profile_list(@ModelAttribute("search") WamPrfMstrVO search , ModelMap model,String linkFlag) throws Exception{
		// 0. Spring Security 사용자권한 처리
		logger.debug("search : {}",search);
		logger.debug("linkFlag : {}",linkFlag);
		model.addAttribute("linkFlag", linkFlag);
		Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
		if(!isAuthenticated) {
		}
		return "/dq/profile/profile_lst";
	}

		/* 관계분석 등록 */
	@RequestMapping("/dq/profile/registerProfilePT01RelCol.do")
	@ResponseBody
	public  IBSResultVO<WamPrfMstrVO> registerProfilePT01RelCol(WamPrfMstrVO prfmst
			                                                                    , WamPrfRelTblVO pt01RelTblVo //부모테이블
																				, @RequestBody WamPrfRelColVOs  pt01RelColVos //관계컬럼
																				,  Locale locale) throws Exception {

		logger.debug("prfmst : {}", prfmst);
		logger.debug("pt01RelTblVo : {}", pt01RelTblVo);
		logger.debug("pt01RelColVos data : {}", pt01RelColVos.get("data"));

		//부모테이블 정보
		prfmst.setWamPrfRelTblVO(pt01RelTblVo);
		//관계컬럼
		prfmst.setWamPrfRelColVO(pt01RelColVos.get("data"));

		ArrayList<WamPrfMstrVO> list = new ArrayList<WamPrfMstrVO>() ;
		list.add(prfmst);

		return saveProfile(list, locale);

	}

	/*중복분석*/
	@RequestMapping("/dq/profile/registerProfilePT02.do")
	@ResponseBody
	public  IBSResultVO<WamPrfMstrVO> registerProfilePT02(WamPrfMstrVO prfmst, @RequestBody WamPrfUnqColVOs  pt02Vos,  Locale locale) throws Exception {

		logger.debug("prfmst : {}", prfmst);
		logger.debug("data : {}", pt02Vos.get("data"));

		//중복컬럼
		prfmst.setWamPrfUnqColVO(pt02Vos.get("data"));

		ArrayList<WamPrfMstrVO> list = new ArrayList<WamPrfMstrVO>() ;
		list.add(prfmst);

		return saveProfile(list, locale);

	}
	
	/* 관계분석 등록 */
	@RequestMapping("/dq/profile/registerProfileReacRelCol.do")
	@ResponseBody
	public  IBSResultVO<WamPrfMstrVO> registerProfileReacRelCol(WamPrfMstrVO prfmst
			                                                                    , WamPrfReacTblVO reacRelTblVo //부모테이블
																				, @RequestBody WamPrfReacColVOs  reacRelColVos //관계컬럼
																				,  Locale locale) throws Exception {

		logger.debug("prfmst : {}", prfmst);
		logger.debug("reacRelTblVo : {}", reacRelTblVo);
		logger.debug("reacRelColVos data : {}", reacRelColVos.get("data"));
		
		if("Y".equals(userDivYn)) {
			LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
			prfmst.setMngUserId(UtilString.null2Blank(user.getUniqId()));
		} else {
			prfmst.setMngUserId(cmnMngUserId);
		}
		//부모테이블 정보
		prfmst.setWamPrfReacTblVO(reacRelTblVo);
		//관계컬럼
		prfmst.setWamPrfReacColVO(reacRelColVos.get("data"));

		ArrayList<WamPrfMstrVO> list = new ArrayList<WamPrfMstrVO>() ;
		list.add(prfmst);

		return saveProfile(list, locale);

	}

	//프로파일 등록
	public  IBSResultVO<WamPrfMstrVO> saveProfile(ArrayList<WamPrfMstrVO> list,  Locale locale) throws Exception{
		int result =  profileMstrService.register(list);
		String kndCd = list.get(0).getPrfKndCd();
		
		if(result > 0 && !kndCd.equals("PT01")) {
			ArrayList<WamShdJob> shdList = new ArrayList<WamShdJob>();
			
			for(WamPrfMstrVO prfVo : list) {
				String prfNm = prfVo.getPrfKndCd() + "|" + prfVo.getDbConnTrgPnm() + "|" + prfVo.getDbSchPnm() + "|"+ prfVo.getDbcTblNm() + "|" + prfVo.getDbcColNm() ;
				WamShdJob shdJob = new WamShdJob();
				shdJob.setShdJobId(prfVo.getPrfId());
				shdJob.setEtcJobNm("실시간컬럼분석" + prfNm);
				shdJob.setShdJobNm("실시간컬럼분석" + prfNm);
				shdList.add(shdJob);
			}
			
			result = execItmAna(shdList, locale);
		}
		
		String resultMsg = null;

//		int result = 0;
		//result "0" 이 아닌 경우 실패 메세지 전송...
		
		if(kndCd.equals("PC01")) {
			if(result > 0) {
				result = 0;
				resultMsg = "컬럼분석이 실행되었습니다."; //message.getMessage("MSG.SAVE", null, locale);
			} else{
				result = -1;
				resultMsg = "컬럼분석을 실패하였습니다."; //message.getMessage("ERR.SAVE", null, locale);
			}
		} else {
			if(result > 0) {
				result = 0;
				resultMsg = message.getMessage("MSG.SAVE", null, locale);
			} else {
				result = -1;
				resultMsg = message.getMessage("ERR.SAVE", null, locale);
			}
		}
		
		String action = WiseMetaConfig.IBSAction.REG.getAction();
		
		return new IBSResultVO<WamPrfMstrVO>(result, resultMsg, action);
	}
	
	public int execItmAna(ArrayList<WamShdJob> data, Locale locale) throws Exception {
		logger.debug("{}", data);
		
		int result = 0;    
		
		String resmsg;
		
		WamShd saveVO = new WamShd();  
		
		ArrayList<WamShdJob> list = data;  
		
		
		String shdLnm = "";
		
		shdLnm = "실시간 컬럼분석실행("+UtilDate.getCurrentDateHms()+")";
		    		
		saveVO.setShdLnm(shdLnm);
		saveVO.setShdTypCd("O"); //배치형태  한번만
		saveVO.setShdStrDtm(UtilDate.getCurrentDate()); //배치시작일자  //현재날짜
		saveVO.setShdStrHr("00");  //시
		saveVO.setShdStrMnt("00");  //분
		saveVO.setShdUseYn("Y"); //스케줄사용여부
		saveVO.setShdBprYn("N"); //일괄처리여부
		saveVO.setRegTypCd("C");
		saveVO.setShdKndCd("QP"); 
		
		if("Y".equals(userDivYn)) {
			LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
			saveVO.setMngUserId(UtilString.null2Blank(user.getUniqId()));
		} else {
			saveVO.setMngUserId(cmnMngUserId);
		}
		
		//스케줄관리 필터링 위해
		saveVO.setObjDescn("ONLINE"); 
    	
    	//스케줄 _ HOME full  경로
		String schedulerpath = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.SCHDULER_PATH, null, Locale.getDefault()); 
		//Quartz 등록 shell 경로
		String schedulercmd = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.SCHDULER_CMD, null, Locale.getDefault()); 
    	
		logger.debug("schepath:{}\nschecmd:{}", schedulerpath, schedulercmd);
		
		//Quartz server 구동 확인
		if(SchedulerUtils.testConnectSchedulerServer(schedulerpath)) {
			//스케줄 마스터, 스케줄작업 저장
			result = scheduleManagerService.saveSchedule(list, saveVO);
			
			if(result > 0) {
	    		resmsg = message.getMessage("MSG.QUARTZ", null, locale);
	    	}
	    	else {
	    		result = -1;
	    		resmsg = message.getMessage("ERR.SAVE", null, locale);
	    	}

			//Quartz 등록
			SchedulerUtils.registrySchedule(saveVO, schedulercmd);
		}else{
			result = -1;
			resmsg = message.getMessage("ERR.QUARTZ", null, locale);
		}
		
		return result;

	}

	// 프로파일 조회 관계분석
	@RequestMapping("/dq/profile/profileListRelAna.do")
	@ResponseBody
	public IBSheetListVO<WamPrfMstrCommonVO> profileListRelAna(@ModelAttribute WamPrfMstrCommonVO search
			, Locale locale) {
		logger.debug("{}", search);

		List<WamPrfMstrCommonVO> list = pt01Service.profileListRelAna(search);
		
		return new IBSheetListVO<WamPrfMstrCommonVO>(list, list.size());

	}

	// 프로파일 조회 중복분석
	@RequestMapping("/dq/profile/profileListUnqAna.do")
	@ResponseBody
	public IBSheetListVO<WamPrfMstrCommonVO> profileListUnqAna(@ModelAttribute WamPrfMstrCommonVO search
			, Locale locale) {
		logger.debug("{}", search);

		List<WamPrfMstrCommonVO> list = pt02Service.profileListUnqAna(search);
		return new IBSheetListVO<WamPrfMstrCommonVO>(list, list.size());

	}
	
	// 프로파일 조회 관계분석
	@RequestMapping("/dq/profile/profileListReacAna.do")
	@ResponseBody
	public IBSheetListVO<WamPrfMstrCommonVO> profileListReacAna(@ModelAttribute WamPrfMstrCommonVO search
			, Locale locale) {
		logger.debug("{}", search);

		List<WamPrfMstrCommonVO> list = reacService.profileListReacAna(search);
		
		return new IBSheetListVO<WamPrfMstrCommonVO>(list, list.size());

	}
		
	// 프로파일 조회 컬럼분석
	@RequestMapping("/dq/profile/profileListColAna.do")
	@ResponseBody
	public IBSheetListVO<WamPrfMstrCommonVO> profileListColAna(@ModelAttribute WamPrfMstrCommonVO search
																					, Locale locale) {
		logger.debug("{}", search);

		List<WamPrfMstrCommonVO> list = pc01Service.profileListColAna(search);
		return new IBSheetListVO<WamPrfMstrCommonVO>(list, list.size());

	}
	// 프로파일 조회 유효값분석
	@RequestMapping("/dq/profile/profileListCodeAna.do")
	@ResponseBody
	public IBSheetListVO<WamPrfMstrCommonVO> profileListCodeAna(@ModelAttribute WamPrfMstrCommonVO search, Locale locale) {
		logger.debug("{}", search);

		List<WamPrfMstrCommonVO> list = pc02Service.profileListCodeAna(search);
		return new IBSheetListVO<WamPrfMstrCommonVO>(list, list.size());

	}
	// 프로파일 조회 날짜분석
	@RequestMapping("/dq/profile/profileListDateAna.do")
	@ResponseBody
	public IBSheetListVO<WamPrfMstrCommonVO> profileListDateAna(@ModelAttribute WamPrfMstrCommonVO search, Locale locale) {

		List<WamPrfMstrCommonVO> list = pc03Service.profileListDateAna(search);
		return new IBSheetListVO<WamPrfMstrCommonVO>(list, list.size());

	}
	// 프로파일 조회 범위분석
	@RequestMapping("/dq/profile/profileListRangeAna.do")
	@ResponseBody
	public IBSheetListVO<WamPrfMstrCommonVO> profileListRangeAna(@ModelAttribute WamPrfMstrCommonVO search, Locale locale) {

		List<WamPrfMstrCommonVO> list = pc04Service.profileListRangeAna(search);
		return new IBSheetListVO<WamPrfMstrCommonVO>(list, list.size());

	}
	// 프로파일 조회 패턴분석
	@RequestMapping("/dq/profile/profileListPatternAna.do")
	@ResponseBody
	public IBSheetListVO<WamPrfMstrCommonVO> profileListPatternAna(@ModelAttribute WamPrfMstrCommonVO search, Locale locale){
		List<WamPrfMstrCommonVO> list = pc05Service.profileListPtrAna(search);
		return new IBSheetListVO<WamPrfMstrCommonVO>(list, list.size());
	}

	//프로파일 상세 팝업
	@RequestMapping("/dq/profile/popup/prfdtl_pop.do")
	public String prfDtlPopup(@ModelAttribute("search") WamPrfMstrCommonVO search, ModelMap model) {
		logger.debug("{}", search);
		String prfKndCd = search.getPrfKndCd();
		//프로파일 정보 조회
		search = profileMstrService.getPrfMstrByPrfId(search.getPrfId(), search.getPrfKndCd());
		search.setPrfKndCd(prfKndCd);
		model.addAttribute("search", search);
		return "/dq/profile/popup/prfdtl_pop";
	}


	@RequestMapping("/dq/profile/popup/sql_pop.do")
	//@ResponseBody
	public String goSqlSearchPop(String prfId,  ModelMap model  ) {
		model.addAttribute("prfId", prfId);
    	return "/dq/profile/popup/sql_pop";
	}

	@RequestMapping("/dq/profile/ajaxgrid/sql_dtl.do")
	//@ResponseBody
	public String getSqlDtlSearchPop(String prfId,  ModelMap model  ) {
		
		WamPrfMstrVO  prfmstrVO = profileMstrService.getSqlGenDbmsInfoByPrfId(prfId);
		SqlGeneratorVO sqlVO = getSqlGen(prfmstrVO);
		
		model.addAttribute("prfKndCd", prfmstrVO.getPrfKndCd());
		model.addAttribute("sqlVO", sqlVO);
		
		return "/dq/profile/popup/sql_dtl";
	}

	public SqlGeneratorVO getSqlGen(WamPrfMstrVO  prfmstrVO) {

		String prfId = prfmstrVO.getPrfId();
		String prfKndCd = prfmstrVO.getPrfKndCd();
		
		logger.debug("진단대상 DBMS 유형 : "+prfmstrVO.getDbmsTypCd());
		logger.debug("프로파일 종류 : "+prfKndCd);
		
		Map<String, Object> sqlGenMap = new HashMap<String, Object>();

		if(!UtilObject.isNull(prfId)) {
			//컬럼분석 상세조회
			if(prfKndCd.equals("PC01")){
				WamPrfColAnaVO result = pc01Service.getColAnaDtl(prfId);
				sqlGenMap.put("prfDtlVO", result);
			}
			//유효값 분석 상세 조회
			else if(prfKndCd.equals("PC02")){
				WamPrfEfvaAnaVO result = pc02Service.getPrfPC02Dtl(prfId);

				//사용자정의 유효값
				if(result.getEfvaAnaKndCd().equals("USER")){
					List<WamPrfEfvaUserDfVO>  List =  pc02Service.getPrfPC02UserDfLst(prfId);
					result.setWamPrfEfvaUserDfVO((ArrayList<WamPrfEfvaUserDfVO>) List);
				}

				//메타정보 조회
				if(result.getEfvaAnaKndCd().equals("META")){
					MetaDmnItfVO metaDmnVO = new MetaDmnItfVO();
					metaDmnVO.setDbcColNm(prfmstrVO.getDbcColNm());
					metaDmnVO = metaItfService.getMetaDmnDtl(metaDmnVO);
					result.setMetaDmnItfVO(metaDmnVO);
					// O 단일코드 L 목록성코드 C 복합코드
					//단순코드 이거나 값이 없을 경우...
					if(!StringUtils.hasText(metaDmnVO.getCdValTypCd()) || "O".equals(metaDmnVO.getCdValTypCd())) {
						List<MetaDmnCdValItfVO> list = metaItfService.getMetaDmnCdValLst(metaDmnVO.getDmnId());
						result.setMetaDmnCdValItfVO((ArrayList<MetaDmnCdValItfVO>) list);
					}
					/*if(metaDmnVO.getCdValTypCd().equals("O")){
						List<MetaDmnCdValItfVO> list = metaItfService.getMetaDmnCdValLst(metaDmnVO.getDmnId());
						result.setMetaDmnCdValItfVO((ArrayList<MetaDmnCdValItfVO>) list);
					}*/
				}
				sqlGenMap.put("prfDtlVO", result);
			}
			//날짜형식 분석 상세 조회
			else if(prfKndCd.equals("PC03")){
				WamPrfDtfrmAnaVO result = pc03Service.getPrfPC03Dtl(prfId);
				sqlGenMap.put("prfDtlVO", result);
			}
			//범위 분석 상세 조회
			else if(prfKndCd.equals("PC04")){
				WamPrfRngAnaVO result = pc04Service.getPrfPC04Dtl(prfId);
				sqlGenMap.put("prfDtlVO", result);
			}
			//패턴 분석 상세 조회
			else if(prfKndCd.equals("PC05")){
				//사용자정의패턴
				List<WamPrfPtrAnaVO> list =  pc05Service.getPrfPC05UserDfLst(prfId);
				sqlGenMap.put("prfDtlVO", list);
			}
			//관계분석
			else if(prfKndCd.equals("PT01")){
				//관계분석 상세
				WamPrfRelTblVO result = pt01Service.getPrfPT01Dtl(prfId);
				//관계컬럼
				if(null != result){
					List<WamPrfRelColVO> list =pt01Service.getPrfPT01RelColLst(prfId);
					result.setWamPrfRelColVO((ArrayList<WamPrfRelColVO>) list);
				}
				sqlGenMap.put("prfDtlVO", result);
			}

			//중복분석
			else if(prfKndCd.equals("PT02")){
//				WamPrfUnqColVO result = (WamPrfUnqColVO) pt02Service.getPrfPT02Dtl(prfId);
				List<WamPrfUnqColVO> list  = pt02Service.getPrfPT02UnqColLst(prfId);
				sqlGenMap.put("prfDtlVO", list);
			}
			//관계분석
			else if(prfKndCd.equals("PT01")){
				//관계분석 상세
				WamPrfReacTblVO result = reacService.getPrfReacDtl(prfId);
				//관계컬럼
				if(null != result){
					List<WamPrfReacColVO> list =reacService.getPrfReacRelColLst(prfId);
					result.setWamPrfReacColVO((ArrayList<WamPrfReacColVO>) list);
				}
				sqlGenMap.put("prfDtlVO", result);
			}
		}

		sqlGenMap.put("prfMstrVO", prfmstrVO);

		//SQL 생성
		SqlGeneratorMng sqlGenMng = new SqlGeneratorMng();
		SqlGeneratorVO sqlVO = sqlGenMng.getSql(sqlGenMap);

		return sqlVO;

	}
	
	
    @RequestMapping("/dq/profile/getPrfBrTransfer.do")
	@ResponseBody
	public IBSheetListVO<WaqBrMstr> getPrfBrTransfer(WamPrfMstrVO search) {
		logger.debug(" prfId :{}", search.getPrfId());
		WaqBrMstr waqBrMstr = new WaqBrMstr();
		List<WaqBrMstr> brLst = new ArrayList<WaqBrMstr>();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		String userNm = user.getName();
		
		String prfId = search.getPrfId();
		String[] arrPrfId = prfId.split("[|]");

		logger.debug("ibs:{}", arrPrfId);
		
		for(int i=0; i<arrPrfId.length; i++){
			waqBrMstr = new WaqBrMstr();
			WamPrfMstrVO  prfmstrVO = profileMstrService.getSqlGenDbmsInfoByPrfId(arrPrfId[i]);
			
			waqBrMstr.setDbConnTrgPnm(prfmstrVO.getDbConnTrgPnm());
			waqBrMstr.setDbSchPnm(prfmstrVO.getDbSchPnm());
			waqBrMstr.setDbcTblNm(prfmstrVO.getDbcTblNm());
			waqBrMstr.setDbcColNm(prfmstrVO.getDbcColNm());
			waqBrMstr.setRegTypCd(prfmstrVO.getRegTypCd());
			waqBrMstr.setRqstDcd(prfmstrVO.getRqstDcd());
			waqBrMstr.setBrCrgpUserId(userid);
			waqBrMstr.setBrCrgpUserNm(userNm);
			waqBrMstr.setBrNm(prfmstrVO.getBrNm());
			//dqi정보
			waqBrMstr.setDqiId(prfmstrVO.getDqiId());
			waqBrMstr.setDqiLnm(prfmstrVO.getDqiLnm());
			
			
			//SQL 생성
			SqlGeneratorVO sqlVO =  getSqlGen(prfmstrVO);
			waqBrMstr.setCntSql(sqlVO.getTotalCount());
			
			if(prfmstrVO.getPrfKndCd().equals("PC01")){
				waqBrMstr.setAnaSql(sqlVO.getErrorPattern());
			}else{
				waqBrMstr.setAnaSql(sqlVO.getErrorData());
			}
	         
			brLst.add(waqBrMstr);
		}
		
		logger.debug(" waqBrMstr :{}", waqBrMstr);
		
		return new IBSheetListVO<WaqBrMstr>(brLst, brLst.size());
	}
}
