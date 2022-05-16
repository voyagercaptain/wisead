package kr.wise.portal.dashboard.service;

import java.util.List;

import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;

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
public interface DqMainChartService {

	List<DqMainChartVO> approveChart();

	List<DqMainChartVO> analyzeChart(WaaDbConnTrgVO vo);

	List<DqMainChartVO> qualityChart();

}
