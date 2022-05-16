package kr.wise.dq.model.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

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

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.dq.model.service.NiaPdmSditmMapService;
import kr.wise.dq.model.service.NiaPdmSditmMapVo;
import kr.wise.dq.model.service.WamNiaPdmCol;
import kr.wise.dq.model.service.WamNiaPdmSditmMap;
import kr.wise.dq.model.service.WamNiaPdmSditmMapAna;
import kr.wise.dq.model.web.NiaModelPdmCtrl.WamNiaPdmCols;

@Controller("NiaPdmSditmMapCtrl")
public class NiaPdmSditmMapCtrl {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	NiaPdmSditmMapService mapService;
	
	@Inject
	private CodeListService codeListService;

	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private MessageSource message;
	
	private Map<String, Object> codeMap;
	
	static class NiaPdmSditmMapVos extends HashMap<String, ArrayList<NiaPdmSditmMapVo>> {}
	static class WamNiaPdmSditmMapAnas extends HashMap<String, ArrayList<WamNiaPdmSditmMapAna>> {}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId2"));
		codeMap.put("connTrgSch", connTrgSch);
		
		String devConnTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("devConnTrgSchId2"));
		codeMap.put("devConnTrgSch", devConnTrgSch);

		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		codeMap.put("regTypCdibs", regTypCd);
		
		
		String niaStndAnaCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("NIA_STND_ANA_CD"));
		codeMap.put("niaStndAnaCd", cmcdCodeService.getCodeList("NIA_STND_ANA_CD"));
		codeMap.put("niaStndAnaCdibs", niaStndAnaCd);


		return codeMap;
	}
	
	@RequestMapping("/dq/model/nia_pdmsditm_map_rqst.do")
	public String goPdmSditmMapRqst() {
		return "/dq/model/nia_pdmsditm_map_rqst";
	}
	
	@RequestMapping("/dq/model/nia_pdmsditm_map_lst.do")
	public String goPdmSditmMapList() {
		return "/dq/model/nia_pdmsditm_map_lst";
	}
	
	@RequestMapping("/dq/model/nia_pdmsditm_map_result.do")
	public String goPdmSditmMapResult() {
		return "/dq/model/nia_pdmsditm_map_result";
	}
	
	@RequestMapping("/dq/model/getPdmStndList.do")
	@ResponseBody
	public IBSheetListVO<NiaPdmSditmMapVo> getPdmSditmList(@ModelAttribute NiaPdmSditmMapVo search, Locale locale) throws Exception {
		logger.debug("search:{}", search.getDbNm());
		
		List<NiaPdmSditmMapVo> list = mapService.getPdmSditmList(search);
		
		return new IBSheetListVO<NiaPdmSditmMapVo>(list, list.size());

	}
	
	@RequestMapping("/dq/model/getPdmStndMapList.do")
	@ResponseBody
	public IBSheetListVO<NiaPdmSditmMapVo> getPdmSditmMapList(@ModelAttribute NiaPdmSditmMapVo search, Locale locale) throws Exception {
		logger.debug("search:{}", search);
		
		List<NiaPdmSditmMapVo> list = mapService.getPdmSditmMapList(search);
		
		return new IBSheetListVO<NiaPdmSditmMapVo>(list, list.size());

	}
	
	@RequestMapping("/dq/model/getPdmStndMapAna.do")
	@ResponseBody
	public IBSheetListVO<WamNiaPdmSditmMapAna> getPdmSditmMapAna(@ModelAttribute NiaPdmSditmMapVo search, Locale locale) throws Exception {
		logger.debug("searchAna:{}", search);
		
		List<WamNiaPdmSditmMapAna> list = mapService.selectPdmSditmMapAna();

		return new IBSheetListVO<WamNiaPdmSditmMapAna>(list, list.size());

	}	
	
	
	@RequestMapping("/dq/model/getWamPdmSditmMapAna.do")
	@ResponseBody
	public IBSheetListVO<WamNiaPdmSditmMapAna> getWamPdmSditmMapAna(@ModelAttribute NiaPdmSditmMapVo search, Locale locale) throws Exception {
		logger.debug("searchAna:{}", search);
		
		List<WamNiaPdmSditmMapAna> list = mapService.getPdmSditmMapAna();

		return new IBSheetListVO<WamNiaPdmSditmMapAna>(list, list.size());

	}	
	
	
	@RequestMapping("/dq/model/getWamNiaPdmAllMapList.do")
	@ResponseBody
	public IBSheetListVO<NiaPdmSditmMapVo> getWamNiaPdmAllMapList(@ModelAttribute NiaPdmSditmMapVo search, Locale locale) throws Exception {
//		logger.debug("searchAna:{}", search);
		
		List<NiaPdmSditmMapVo> list = mapService.getPdmAllMap(search);

		return new IBSheetListVO<NiaPdmSditmMapVo>(list, list.size());

	}	
	
	
	@RequestMapping("/dq/stnd/regPdmSditmMapRqst.do")
	@ResponseBody
	public IBSResultVO<NiaPdmSditmMapVo> regPdmSditmMapRqst(@RequestBody NiaPdmSditmMapVos data, Locale locale) throws Exception {

		logger.debug("data:{}", data.size());

		int result = -1;
		String resmsg = null;

		result = mapService.regPdmSditmMapRqst(data.get("data"));

		if(result >= 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action =  WiseMetaConfig.RqstAction.REGISTER.getAction();

		return new IBSResultVO<NiaPdmSditmMapVo>(result, resmsg, action);
	}
	
	
	@RequestMapping("/dq/stnd/delPdmSditmMapList.do")
	@ResponseBody
	public IBSResultVO<NiaPdmSditmMapVo> delPdmSditmMapList(@RequestBody NiaPdmSditmMapVos data, Locale locale) throws Exception {

		logger.debug("data:{}", data.size());

		int result = -1;
		String resmsg = null;

		result = mapService.delPdmSditmMapList(data.get("data"));

		if(result >= 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action =  WiseMetaConfig.RqstAction.REGISTER.getAction();

		return new IBSResultVO<NiaPdmSditmMapVo>(result, resmsg, action);
	}
	
	
	@RequestMapping("/dq/stnd/regPdmSditmMapAna.do")
	@ResponseBody
	public IBSResultVO<WamNiaPdmSditmMapAna> regPdmSditmMapAna(@RequestBody WamNiaPdmSditmMapAnas data, Locale locale) throws Exception {

		logger.debug("data : {}", data);

		int result = -1;
		String resmsg = null;

		result = mapService.regPdmSditmMapAna(data.get("data"));

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();

		return new IBSResultVO<WamNiaPdmSditmMapAna>(result, resmsg, action);
	}

}