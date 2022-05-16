/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : UserCtrl.java
 * 2. Package : kr.wise.cmvw.user.controller
 * 3. Comment :
 * 4. 작성자  : (ycyoo)유연철
 * 5. 작성일  : 2013. 4. 24
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    유연철 	: 2013. 4. 24. 		: 신규 개발.
 */
package kr.wise.commons.user.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.user.service.LicService;
import kr.wise.commons.user.service.WaaLic;
import kr.wise.commons.util.UtilJson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
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
 * 3. Package  : kr.wise.commons.user.web
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 4. 28. 오후 7:50:12
 * </PRE>
 */ 
@Controller("LicCtrl")
@RequestMapping("/commons/user/*")
public class LicCtrl {

	Logger logger = LoggerFactory.getLogger(getClass());

	static class WaaLics extends HashMap<String, ArrayList<WaaLic>> { }

	private Map<String, Object> codeMap;

	@Inject
	private LicService licService;
	
	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private MessageSource message;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	/** 라이센스 관리 화면 */
	@RequestMapping("lic_lst.do")
	public String formpage() {
		return "/commons/user/lic_lst";
	}
	
	/** 라이센스 리스트 조회 - IBSheet JSON */
	@RequestMapping("licSelectlist.do")
	@ResponseBody
	public IBSheetListVO<WaaLic> selectList(@ModelAttribute WaaLic search) {
		logger.debug("{}", search);
		List<WaaLic> list = licService.getList(search);
		return new IBSheetListVO<WaaLic>(list, list.size());
	}

	/** 라이센스 리스트 등록 - IBSheet JSON */
	@RequestMapping("licReglist.do")
	@ResponseBody
	public IBSResultVO<WaaLic> regList(@RequestBody WaaLics data, Locale locale) {

		logger.debug("{}", data);
		ArrayList<WaaLic> list = data.get("data");

		int result = licService.regList(list);
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();

		return new IBSResultVO<WaaLic>(result, resmsg, action);
	}

	/** 라이센스 리스트 삭제 - IBSheet JSON */
	@RequestMapping("licDellist.do")
	@ResponseBody
	public IBSResultVO<WaaLic> delList(@RequestBody WaaLics data, Locale locale) {

		logger.debug("{}", data);
		ArrayList<WaaLic> list = data.get("data");

		int result = licService.delList(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();


		return new IBSResultVO<WaaLic>(result, resmsg, action);
	}
	
	/** 공통코드 및 목록성 코드 조회  */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//공통코드
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		codeMap.put("regTypCdibs", regTypCd);
		

		return codeMap;
	}

}
