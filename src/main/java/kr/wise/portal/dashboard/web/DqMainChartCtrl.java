package kr.wise.portal.dashboard.web;

import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.dq.dashboard.service.DqDashService;
import kr.wise.dq.dashboard.service.WidgetsChartVO;
import kr.wise.portal.dashboard.service.DqMainChartService;
import kr.wise.portal.dashboard.service.DqMainChartVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : BizruleProgCtrl.java
 * 3. Package  : kr.wise.dq.report.bizrule.web
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 6. 20. 오후 5:48:53
 * </PRE>
 */ 
@Controller
public class DqMainChartCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private DqMainChartService dqMainChartService;
	

	/**
	 * @return meta
	 * @throws Exception 
	 * DQ메인화면 개선현황 (Json) */
	@RequestMapping("/portal/dashboard/dqApproveChart.do")
	@ResponseBody
	public List<DqMainChartVO> approveChartListJson() throws Exception{

		List<DqMainChartVO> list = dqMainChartService.approveChart() ;

		return list;
	}
	
	/**
	 * @return meta
	 * @throws Exception 
	 * DQ메인화면 진단현황 (Json) */
	@RequestMapping("/portal/dashboard/dqAnalyzeChart.do")
	@ResponseBody
	public List<DqMainChartVO> analyzeChartListJson(@ModelAttribute WaaDbConnTrgVO vo){ 
		List<DqMainChartVO> list = dqMainChartService.analyzeChart(vo);
		logger.debug("list: {}", list);
		return list;
	}

	

}
