package kr.wise.portal.dashboard.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.commons.util.UtilString;


import kr.wise.portal.dashboard.service.DqMainChartVO;
import kr.wise.portal.dashboard.service.StandardChartVO;
import kr.wise.portal.dashboard.service.TotalCountVO;
import kr.wise.portal.dashboard.service.TotalDashMapper;
import kr.wise.portal.dashboard.service.TotalDashService;
import kr.wise.portal.dashboard.service.UpdateCntVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service ("totdashService")
public class TotalDashServiceImpl implements TotalDashService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	
	
	@Inject
	private TotalDashMapper totalDashMapper;

	
	@Override
	public List<StandardChartVO> selectstandardChart() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/** 원천데이터 */
	@Override
	public List<TotalCountVO> selectSrcDataRqstCnt(String userId) {
		return totalDashMapper.selectSrcDataRqstCnt(userId);
	}
	
	/** 보유현황 */
	@Override
	public List<TotalCountVO> selectMtaTotalCnt() {
		return totalDashMapper.selectMtaTotalCnt();
	}
	
	/** 시스템모니터링 서버상태 조회 */
	@Override
	public Map<String, Object> selectSvrStatus(List<CodeListVo> orgCdList) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		

		String collectorStatus = totalDashMapper.selectCollectorStatus(orgCdList);
		String esbSvrStatus = totalDashMapper.selectEsbSvrStatus(orgCdList);
		String gpucFsvrStatus = totalDashMapper.selectGpucFsvrStatus(orgCdList);
		//collectorStatus esbSvrStatus gpucFsvrStatus
		map.put("collectorStatus", UtilString.isBlank(collectorStatus) ? "off" : collectorStatus);
		map.put("esbSvrStatus", UtilString.isBlank(esbSvrStatus) ? "off" : esbSvrStatus);
		map.put("gpucFsvrStatus", UtilString.isBlank(gpucFsvrStatus) ? "off" : gpucFsvrStatus);
		
		return map;
	}
	@Override
	public List<TotalCountVO> selectMylistCnt(String userId, String orgCd) {
		return totalDashMapper.selectMyListCnt(userId, orgCd);
	}

	@Override
	public List<TotalCountVO> selectTotCntWAMs(String userid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UpdateCntVO> selectUpdateCntStat(String userid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StandardChartVO> selectDeptStandardChart() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void regTotalUserMetaIndex() {
		totalDashMapper.mergeUsersTotMeta();
//		totalDashMapper.mergeUsersGapMeta();
//		totalDashMapper.mergeUsersSrcData();
	}
	
	@Override
	public List<TotalCountVO> selectDbmsCnt(){
		return totalDashMapper.selectDbmsCnt();
	}
	
	@Override
	public List<TotalCountVO> selectPrfCnt(){
		return totalDashMapper.selectPrfCnt();
	}
	@Override
	public List<TotalCountVO> selectChartCnt(){
		return totalDashMapper.selectChartCnt();
	}

	@Override
	public List<DqMainChartVO> selectPrfCntByDbms(WaaDbConnTrgVO vo) {
		// TODO Auto-generated method stub
		return totalDashMapper.selectPrfCntByDbms(vo);
	}

}