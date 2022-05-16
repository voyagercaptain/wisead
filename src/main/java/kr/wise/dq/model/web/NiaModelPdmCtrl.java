/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : ModelSubjController.java
 * 2. Package : kr.wise.meta.model
 * 3. Comment :
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2013. 4. 15. 오후 1:14:55
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    insomnia 	: 2013. 4. 15. 		: 신규 개발.
 */
package kr.wise.dq.model.web;

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
import kr.wise.commons.util.UtilJson;
import kr.wise.dq.model.service.NiaModelPdmService;
import kr.wise.dq.model.service.WamNiaPdmCol;

import org.apache.ibatis.annotations.Param;
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

@Controller("NiaModelPdmController")
public class NiaModelPdmCtrl {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private NiaModelPdmService niaModelPdmService;
	
	@Inject
	private CmcdCodeService cmcdCodeService; 

	@Inject
	private MessageSource message;

	private Map<String, String> codeMap;

	static class WamNiaPdmCols extends HashMap<String, ArrayList<WamNiaPdmCol>> {}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@ModelAttribute("codeMap")
	public Map<String, String> getcodeMap() {

		codeMap = new HashMap<String, String>();
		
		String dbmsTypCdibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DBMS_TYP_CD"));
		codeMap.put("dbmsTypCdibs", dbmsTypCdibs);

		//등록유형코드
		String regTypCdibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		codeMap.put("regTypCdibs", regTypCdibs);

		
		return codeMap;
	}


	@RequestMapping("/dq/model/nia_pdmcol_rqst.do")
	public String goPdmColForm() {
		return "/dq/model/nia_pdmcol_rqst";
	}
	
	@RequestMapping("/dq/model/pdmcoldef_lst.do")
	public String goPdmCol() {
		return "/dq/model/pdmdistinct_lst";
	}
	
	@RequestMapping("/dq/model/nia_pdmcol_lst.do")
	public String goPdmColList() {
		return "/dq/model/nia_pdmcol_lst";
	}
	
	@RequestMapping("/dq/model/nia_pdmcol_ana_lst.do")
	public String goPdmColAnaList() {
		return "/dq/model/nia_pdmcol_ana_lst";
	}
	
	@RequestMapping("/dq/model/getNiaPdmColList.do")
	@ResponseBody
	public IBSheetListVO<WamNiaPdmCol> getNiaPdmColList(@ModelAttribute WamNiaPdmCol search, Locale locale) throws Exception {
		logger.debug("search:{}", search);
		
		List<WamNiaPdmCol> list = niaModelPdmService.getNiaPdmColList(search);

		return new IBSheetListVO<WamNiaPdmCol>(list, list.size());

	}
	
	@RequestMapping("/dq/model/regNiaPdmCol.do")
	@ResponseBody
	public IBSResultVO<WamNiaPdmCol> regNiaPdmCol(@RequestBody WamNiaPdmCols data, Locale locale) throws Exception {

//		logger.debug("data : {}", data);

		int result = -1;
		String resmsg = null;

		result = niaModelPdmService.regNiaPdmColList(data.get("data"));

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();

		return new IBSResultVO<WamNiaPdmCol>(result, resmsg, action);
	}
	
	
	@RequestMapping("/dq/model/delDupData.do")
	@ResponseBody
	public IBSheetListVO<WamNiaPdmCol> delDupData(@ModelAttribute WamNiaPdmCol search, Locale locale) throws Exception {
		
	
		List<WamNiaPdmCol> list = niaModelPdmService.delDupData();
		

		return new IBSheetListVO<WamNiaPdmCol>(list, list.size());
	}

	@RequestMapping("/dq/model/delWamNiaPdmColList.do")
	@ResponseBody
	public IBSResultVO<WamNiaPdmCol> delWamNiaPdmColList(@RequestBody WamNiaPdmCols data, Locale locale) throws Exception {
		logger.debug("del:{}", data);

		String resmsg = null;
		
		ArrayList<WamNiaPdmCol> list = data.get("data");
		
		for(int i=0;i<list.size();i++) {
			list.get(i).setIbsStatus("D");
		}
		
		int result = niaModelPdmService.regNiaPdmColList(list);


		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		}
		else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();

		return new IBSResultVO<WamNiaPdmCol>(result, resmsg, action);
	}
	
	@RequestMapping("/dq/model/delWamNiaPdmColAll.do")
	@ResponseBody
	public IBSResultVO<WamNiaPdmCol> delWamNiaPdmColAll(Locale locale) throws Exception {
//		logger.debug("{}", data);

		int result = -1;
		String resmsg = null;

		result = niaModelPdmService.delWamNiaPdmColAll();

		if(result >= 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		}
		else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();

		return new IBSResultVO<WamNiaPdmCol>(result, resmsg, action);
	}
	
	@RequestMapping("/dq/model/getAnaPdm.do")
	@ResponseBody
	public IBSheetListVO<WamNiaPdmCol> getAnaPdm(Locale locale) throws Exception {
//		logger.debug("{}", search);
		
		List<WamNiaPdmCol> list = niaModelPdmService.getAnaPdm();

		return new IBSheetListVO<WamNiaPdmCol>(list, list.size());

	}
	
	@RequestMapping("/dq/model/getAnaCol.do")
	@ResponseBody
	public IBSheetListVO<WamNiaPdmCol> getAnaCol(Locale locale) throws Exception {
//		logger.debug("{}", search);
		
		List<WamNiaPdmCol> list = niaModelPdmService.getAnaCol();
		

		return new IBSheetListVO<WamNiaPdmCol>(list, list.size());

	}
	
	@RequestMapping("/dq/model/regNiaPdmAna.do")
	@ResponseBody
	public IBSResultVO<WamNiaPdmCol> regNiaPdmAna(@RequestBody WamNiaPdmCols data, Locale locale) throws Exception {

//		logger.debug("data : {}", data);

		int result = -1;
		String resmsg = null;

		result = niaModelPdmService.regNiaPdmAna(data.get("data"));

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();

		return new IBSResultVO<WamNiaPdmCol>(result, resmsg, action);
	}
	
	@RequestMapping("/dq/model/regNiaColAna.do")
	@ResponseBody
	public IBSResultVO<WamNiaPdmCol> regNiaColAna(@RequestBody WamNiaPdmCols data, Locale locale) throws Exception {

//		logger.debug("data : {}", data);

		int result = -1;
		String resmsg = null;

		result = niaModelPdmService.regNiaColAna(data.get("data"));

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();

		return new IBSResultVO<WamNiaPdmCol>(result, resmsg, action);
	}
	
	@RequestMapping("/dq/model/getNiaPdmAnaList.do")
	@ResponseBody
	public IBSheetListVO<WamNiaPdmCol> getNiaPdmAnaList(@ModelAttribute WamNiaPdmCol search, Locale locale) throws Exception {
		logger.debug("{}", search);
		
		List<WamNiaPdmCol> list = niaModelPdmService.getNiaPdmAnaList(search);

		return new IBSheetListVO<WamNiaPdmCol>(list, list.size());

	}
	
	@RequestMapping("/dq/model/getNiaColAnaList.do")
	@ResponseBody
	public IBSheetListVO<WamNiaPdmCol> getNiaColAnaList(@ModelAttribute WamNiaPdmCol search, Locale locale) throws Exception {
		logger.debug("{}", search);
		
		List<WamNiaPdmCol> list = niaModelPdmService.getNiaColAnaList(search);

		return new IBSheetListVO<WamNiaPdmCol>(list, list.size());

	}

}
