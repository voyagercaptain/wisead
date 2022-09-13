package kr.wise.dq.stnd.web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.ExcelDownUtil;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;
import kr.wise.dq.stnd.service.StndCommSditmService;
import kr.wise.dq.stnd.service.StndSditmService;
import kr.wise.dq.stnd.service.WamSditm;
import kr.wise.dq.stnd.service.WamStwdCnfg;
import kr.wise.dq.stnd.service.WamWhereUsed;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : StndSdtimCtrl.java
 * 3. Package  : kr.wise.dq.stnd.web
 * 4. Comment  :
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 3. 오후 2:10:35
 * </PRE>
 */
@Controller
public class StndSditmCtrl {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private StndSditmService stndSditmService;
	
	@Inject
	private StndCommSditmService stndCommSditmService;

	@Inject
	private CodeListService codeListService;

	@Inject
	private CmcdCodeService cmcdCodeService;

	private Map<String, Object> codeMap;



	/** @param binder request 변수를 매핑시 빈값을 NULL로 처리 insomnia */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@RequestMapping("/dq/stnd/getsditmlist.do")
	@ResponseBody
	public IBSheetListVO<WamSditm> getStndItemList(@ModelAttribute WamSditm data, Locale locale, HttpSession session) {

		logger.debug("req vo:{}", data);
		
		data.setUserId(((LoginVO)session.getAttribute("loginVO")).getId());
		data.setUsergId(((LoginVO)session.getAttribute("loginVO")).getUsergId());

		/**2022.08.19 페이징 처리 추가*/
		Integer endNum   = data.getPageNum()*300;
		Integer startNum = endNum - 299;
		data.setEndNum(endNum);
		data.setStartNum(startNum);
		Integer totalCnt 		= stndSditmService.getStndItemTotalCnt(data);
		List<WamSditm> list = stndSditmService.getStndItemList(data);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);
		return new IBSheetListVO<WamSditm>(list, totalCnt);
	}
	
	
	@RequestMapping("/dq/stnd/getCommsditmlist.do")
	@ResponseBody
	public IBSheetListVO<WamSditm> getStndCommItemList(@ModelAttribute WamSditm data, Locale locale, HttpSession session) {

		logger.debug("req vo:{}", data);
		
		data.setUserId(((LoginVO)session.getAttribute("loginVO")).getId());
		data.setUsergId(((LoginVO)session.getAttribute("loginVO")).getUsergId());

		List<WamSditm> list = stndCommSditmService.getStndItemList(data);

//		ibsJson.MESSAGE = message.getMessage("MSG.SAVE", null, locale);

		return new IBSheetListVO<WamSditm>(list, list.size());

	}



	/** 표준 상세정보 조회 */
	/** yeonho */
	@RequestMapping("/dq/stnd/ajaxgrid/sditminfo_dtl.do")
	public String selectWordInfoDetail(String sditmId, ModelMap model) {
		logger.debug(" {}", sditmId);

		// 메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if (!UtilObject.isNull(sditmId)) {

			WamSditm result = stndSditmService.selectSditmDetail(sditmId);
			model.addAttribute("result", result);
		}
		model.addAttribute("codeMap",getcodeMap());
		return "/dq/stnd/sditminfo_dtl";
	}
	
	/** 표준 상세정보 조회 */
	/** yeonho */
	@RequestMapping("/dq/stnd/ajaxgrid/notsditminfo_dtl.do")
	public String selectNotWordInfoDetail(String sditmId, ModelMap model) {
		logger.debug(" {}", sditmId);
		
		// 메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if (!UtilObject.isNull(sditmId)) {
			
			WamSditm result = stndSditmService.selectSditmDetail(sditmId);
			model.addAttribute("result", result);
		}
		model.addAttribute("codeMap",getcodeMap());
		return "/dq/stnd/notsditminfo_dtl";
	}

	/** 표준항목 검색 팝업.... @return insomnia */
	@RequestMapping("/dq/stnd/popup/stnditem_pop.do")
	public String goStndItemPopup(@ModelAttribute("search") WamSditm searchvo, ModelMap model) {
		logger.debug("search:{}", searchvo);

		model.addAttribute("codeMap",getcodeMap());
		
		return "/dq/stnd/popup/stnditem_pop";
	}

	/** 표준항목 변경이력 조회 -IBSheet json */
	/** yeonho */
	@RequestMapping("/dq/stnd/ajaxgrid/sditmchange_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamSditm> selectMenuList(@ModelAttribute("searchVO") WamSditm searchVO, String sditmId) throws Exception {

		logger.debug("{}", sditmId);
		List<WamSditm> list = stndSditmService.selectSditmChangeList(sditmId);
		return new IBSheetListVO<WamSditm>(list, list.size());
	}

	/** 표준항목 구성항목 조회 -IBSheet json */
	/** yeonho */
	@RequestMapping("/dq/stnd/ajaxgrid/sditminit_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamStwdCnfg> selectSditmInitList(@ModelAttribute("searchVO") WamSditm searchVO, String sditmId) throws Exception {

		logger.debug("{}", sditmId);
		List<WamStwdCnfg> list = stndSditmService.selectSditmInitList(sditmId);
		return new IBSheetListVO<WamStwdCnfg>(list, list.size());
	}

	/** 표준항목 Where Used 조회 -IBSheet json */
	/** yeonho */
	@RequestMapping("/dq/stnd/ajaxgrid/sditmwhereused_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamWhereUsed> selectSditmWhereUsedList(@ModelAttribute("searchVO") WamSditm searchVO, String sditmId) throws Exception {

		logger.debug("{}", sditmId);
		List<WamWhereUsed> list = stndSditmService.selectSditmWhereUsedList(sditmId);
		return new IBSheetListVO<WamWhereUsed>(list, list.size());
	}

//	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//목록성 코드(시스템영역 코드리스트)
		String dmngibs 	= UtilJson.convertJsonString(codeListService.getCodeListIBS("dmng"));
		String infotpibs 	= UtilJson.convertJsonString(codeListService.getCodeListIBS("infotp"));
		String dmnginfotp 		= UtilJson.convertJsonString(codeListService.getCodeList("dmnginfotp"));

		codeMap.put("dmngibs", dmngibs);
		codeMap.put("dmngId", codeListService.getCodeList("dmng"));
		codeMap.put("infotpibs", infotpibs);
		codeMap.put("dmnginfotp", dmnginfotp);
		codeMap.put("infotpId", codeListService.getCodeList("infotp"));

		//공통 코드(요청구분 코드리스트)
		String bizdtlcd = UtilJson.convertJsonString(cmcdCodeService.getCodeList("BIZ_DTL_CD"));
		String bizdtlcdibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("BIZ_DTL_CD"));
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		codeMap.put("bizdtlcd", bizdtlcd);
		codeMap.put("bizdtlcdibs", bizdtlcdibs);
		codeMap.put("regTypCdibs", regTypCd);
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		
//		//표준구분 멀티사전용
//		codeMap.put("stndAsrtibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("STNDASRT")));
//		codeMap.put("stndAsrt", cmcdCodeService.getCodeList("STNDASRT"));
		
		//개인정보등급 이베이코리아
//		codeMap.put("persInfoGrdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PERS_INFO_GRD")));
//		codeMap.put("persInfoGrd", cmcdCodeService.getCodeList("PERS_INFO_GRD"));
		return codeMap;
	}
	
	/** 표준항목 Where Used 조회 -IBSheet json */
	/** yeonho */
	@RequestMapping("/dq/stnd/ajaxgrid/sditmcomparison_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamSditm> selectSditmComparisonList(@ModelAttribute("searchVO") WamSditm searchVO, String sditmId) throws Exception {
		logger.debug("{}", sditmId);
		List<WamSditm> list = stndSditmService.selectSditmComparisonList(sditmId);
		return new IBSheetListVO<WamSditm>(list, list.size());
	}

	@RequestMapping(value = "/dq/stnd/sditmExcel.do")
	public void allSditmExcel(@ModelAttribute("searchVO") WamSditm searchVO, HttpServletResponse response, HttpSession session) throws Exception {

		ExcelDownUtil edu = ExcelDownUtil.getInstance();

		searchVO.setUserId(((LoginVO)session.getAttribute("loginVO")).getId());
		searchVO.setUsergId(((LoginVO)session.getAttribute("loginVO")).getUsergId());

		Integer endNum   = 1000000;
		Integer startNum = 0;
		searchVO.setEndNum(endNum);
		searchVO.setStartNum(startNum);
		List<WamSditm> list = stndSditmService.getStndItemList(searchVO);

		List<String> header = Arrays.asList(new String[]{"No.",	"선택",	"기관명",	"표준용어명",	"영문명",	"영문약어명",	"용어설명", "표준도메인명",
				"허용값",	"관리부서명",	"표준코드명",	"업무분야",	"행정표준코드명",	"제정일자",	"특이사항",	"검증메세지"});

		List<String> fields = Arrays.asList(new String[]{"", "", "orgNm","sditmLnm",	"pnm",	"sditmPnm",	"objDescn", "infotpLnm",
				"alwVal","ownrOrg",	"stndCd",	"bsnssFld",	"admnStndCd",	"rqstDtm",	"spclNt",	"errChk"});

		try (
				SXSSFWorkbook wb = edu.makeExcelFile(header, fields, Collections.singletonList(list));
				OutputStream output = response.getOutputStream();
				FileOutputStream fileOutputStream = new FileOutputStream("/Users/seungjaelee/Documents/data/test.xlsx");
		)
		{
			String fileName = "기관표준용어";										// 파일명
			fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			String fileExtension = ".xlsx";									// 확장자

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + fileExtension + "\"");

			// response 버퍼에 파일 생성
			wb.write(output);
			output.close();
			wb.dispose();

			// 로컬에 파일 생성
			//wb.write(fileOutputStream);

		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}
}
