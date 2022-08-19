package kr.wise.dq.stnd.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.dq.stnd.service.StndSditmService;
import kr.wise.dq.stnd.service.WamSditm;
import kr.wise.dq.stnd.service.WamSditmMapper;
import kr.wise.dq.stnd.service.WamStwdCnfg;
import kr.wise.dq.stnd.service.WamStwdCnfgMapper;
import kr.wise.dq.stnd.service.WamWhereUsed;
import kr.wise.dq.stnd.service.WamWhereUsedMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : StndSditmServiceImpl.java
 * 3. Package  : kr.wise.dq.stnd.service.impl
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 3. 오후 2:49:30
 * </PRE>
 */ 
@Service("stndSditmService")
public class StndSditmServiceImpl implements StndSditmService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	WamSditmMapper mapper;
		
	@Inject
	WamStwdCnfgMapper cnfgMapper;
	
	@Inject
	WamWhereUsedMapper whereUsedMapper;
	
	@Override
	public List<WamSditm> getStndItemList(WamSditm data) {
		
		List<WamSditm> result = null;
		try {
			result = mapper.selectSditmList(data);
		} catch(Exception e) {
			logger.error("", e);
		}
		
		return result;
	}

	@Override
	public WamSditm selectSditmDetail(String sditmId) {
		return mapper.selectByPrimaryKey(sditmId);
	}

	@Override
	public List<WamSditm> selectSditmChangeList(String sditmId) {
			logger.debug("search Id:{}", sditmId);
		
		return mapper.selectSditmChangeList(sditmId);
	}

	@Override
	public List<WamStwdCnfg> selectSditmInitList(String sditmId) {
		logger.debug("{}", sditmId);
		return cnfgMapper.selectSditmInitList(sditmId);
	}

	@Override
	public List<WamWhereUsed> selectSditmWhereUsedList(String sditmId) {
		return whereUsedMapper.selectSditmWhereUsedList(sditmId);
	}

	@Override
	public List<WamSditm> getSditmTransList(WamSditm data) {

		return mapper.getSditmTransList(data);
	}
	
	@Override
	public int saveSditmTransYnPrc(ArrayList<WamSditm> list){
		int result = 0;
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		String userId = user.getUniqId();
		logger.debug("{}", list);
		
		for (WamSditm record : list) {
			result += mapper.saveSditmTransYnPrc(record);			
		}
		return result;	
	
	}

	@Override
	public List<WamSditm> selectSditmComparisonList(String sditmId) {
		
		return mapper.selectSditmComparisonList(sditmId);
	}

	@Override
	public Integer getStndItemTotalCnt(WamSditm data) {
		return mapper.selectSditmTotalCnt(data);
	}
}
