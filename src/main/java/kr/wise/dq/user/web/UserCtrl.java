package kr.wise.dq.user.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.cmm.CommonVo;
import kr.wise.commons.cmm.SearchVO;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.sysmgmt.menu.service.MenuStatVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;
import kr.wise.dq.vrfcrule.service.VrfcruleService;
import kr.wise.dq.vrfcrule.service.VrfcruleVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : UserCtrl.java
 * 3. Package  : kr.wise.commons.sysmgmt.menu.web
 * 4. Comment  : 메뉴 관리 컨트롤러
 * 5. 작성자   : insomnia(장명수)
 * 6. 작성일   : 2013. 12. 24. 오후 2:05:16
 * </PRE>
 */ 
@Controller

public class UserCtrl {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, Object> codeMap;
	
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private CodeListService codelistService;
	
	@Inject
	private VrfcruleService vrfcruleService;
	
	@Inject
	private MessageSource message;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/** 공통코드 및 목록성 코드 조회  */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//시스템영역 코드리스트 JSON
//		String sysarea = codelistService.getCodeListJson("sysarea");

		List<CodeListVo> userglist = codelistService.getCodeList(WiseMetaConfig.CodeListAction.usergroup);
//		String usergroup2 = UtilJson.convertJsonString(codelistService.getCodeList("usergroup"));
		String usergroup1 = UtilJson.convertJsonString(codelistService.getCodeListIBS(userglist));
		List<CodeListVo> userOrgList = codelistService.getCodeList(WiseMetaConfig.CodeListAction.orgCd);
		
//		codeMap.put("usergroup", usergroup2);
		codeMap.put("userglist", userglist);
		codeMap.put("usergp", usergroup1);
		codeMap.put("userorg", userOrgList);
		
		//공통코드
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		codeMap.put("regTypCdibs", regTypCd);
		

		return codeMap;
	}
	
	/** 검증룰리스트 조회 폼 */
	@RequestMapping("/dq/user/user_lst.do")
	public String selectVrfc(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model) throws Exception {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {

    	}
		return "/dq/user/user_lst";
	}
	
	
}
