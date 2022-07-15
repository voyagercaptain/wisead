package kr.wise.commons.user.web;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.user.WaaUserg;
import kr.wise.commons.user.service.RegistStatService;

/**
 * <PRE>
 * 1. ClassName : RegistStatCtrl
 * 2. Package  : kr.wise.cmvw.user.controller
 * 3. Comment  :
 * 4. 작성자   : 김상민
 * 5. 작성일   : 2022. 7. 04.
 * </PRE>
 */
@Controller
@RequestMapping("/commons/user/*")
public class RegistStatCtrl {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private RegistStatService service;
	
	/**  기관별 등록 현황 */
	@RequestMapping("regist_stat_lst.do")
	public String formpage() {
		return "/commons/user/regist_stat_lst";
	}
	
	/**  DB별 등록 현황 */
	@RequestMapping("db_regist_stat_lst.do")
	public String formpage2() {
		return "/commons/user/db_regist_stat_lst";
	}
	
	/**  종합 등록 현황 */
	@RequestMapping("total_stat_lst.do")
	public String formpage3() {
		return "/commons/user/total_stat_lst";
	}
	
	/** 기관표준 등록 현황 조회 **/
	@RequestMapping("RegistStatSelectlist.do")
	@ResponseBody
	public IBSheetListVO<WaaUserg> RegistStatSelectlist(@ModelAttribute WaaUserg search) {
		logger.debug("{}", search);
		List<WaaUserg> list = service.getRegistStatList(search);

		return new IBSheetListVO<WaaUserg>(list, list.size());
	}
	/** DB표준 등록 현황 조회 **/
	@RequestMapping("DbRegistStatSelectlist.do")
	@ResponseBody
	public IBSheetListVO<WaaUserg> DbRegistStatSelectlist(@ModelAttribute WaaUserg search) {
		logger.debug("{}", search);
		List<WaaUserg> list = service.getDbRegistStatList(search);

		return new IBSheetListVO<WaaUserg>(list, list.size());
	}
	
	/** 종합 등록 현황 조회 **/
	@RequestMapping("TotalStatSelectlist.do")
	@ResponseBody
	public IBSheetListVO<WaaUserg> TotalStatSelectlist(@ModelAttribute WaaUserg search) {
		logger.debug("{}", search);
		List<WaaUserg> list = service.getTotalStatList(search);

		return new IBSheetListVO<WaaUserg>(list, list.size());
	}


}
