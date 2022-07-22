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
import kr.wise.commons.user.service.CmpRateService;

/**
 * <PRE>
 * 1. ClassName : CmpRateCtrl
 * 2. Package  : kr.wise.cmvw.user.controller
 * 3. Comment  :
 * 4. 작성자   : 김상민
 * 5. 작성일   : 2022. 7. 21
 * </PRE>
 */
@Controller
@RequestMapping("/commons/user/*")
public class CmpRateCtrl {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private CmpRateService service;
	
	/**  기관표준 준수율 */
	@RequestMapping("org_cmp_rat_lst.do")
	public String formpage() {
		return "/commons/user/org_cmp_rat_lst";
	}
	
	/**  DB표준 준수율 */
	@RequestMapping("db_cmp_rat_lst.do")
	public String formpage2() {
		return "/commons/user/db_cmp_rat_lst";
	}
	
	/** 기관표준 준수율 조회 **/
	@RequestMapping("OrgCmpRatSelectlist.do")
	@ResponseBody
	public IBSheetListVO<WaaUserg> OrgCmpRatSelectlist(@ModelAttribute WaaUserg search) {
		logger.debug("{}", search);
		List<WaaUserg> list = service.getOrgCmpRateList(search);

		return new IBSheetListVO<WaaUserg>(list, list.size());
	}
	
	
	/** DB표준 준수율 조회 **/
	@RequestMapping("DbCmpRatSelectlist.do")
	@ResponseBody
	public IBSheetListVO<WaaUserg> dbCmpRatSelectlist(@ModelAttribute WaaUserg search) {
		logger.debug("{}", search);
		List<WaaUserg> list = service.getDbCmpRateList(search);

		return new IBSheetListVO<WaaUserg>(list, list.size());
	}

}
