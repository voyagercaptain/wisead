/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ColProfileServiceImpl.java
 * 2. Package : kr.wise.dq.criinfo.profile.service.impl
 * 3. Comment : 
 * 4. 작성자  : hwang
 * 5. 작성일  : 2014. 3. 24. 오후 1:35:18
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    hwang : 2014. 3. 24. :            : 신규 개발.
 */
package kr.wise.dq.scheduler.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.damgmt.db.service.WaaDbConnTrgMapper;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.commons.damgmt.schedule.service.WamShdJob;
import kr.wise.commons.damgmt.schedule.service.WamShdJobMapper;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgColVO;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgMapper;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgService;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgTblVO;
import kr.wise.dq.criinfo.anatrg.service.WaaColRuleRel;
import kr.wise.dq.criinfo.anatrg.service.WaaColRuleRelMapper;
import kr.wise.dq.criinfo.anatrg.service.WaaDbSch;
import kr.wise.dq.criinfo.anatrg.service.WaaDbSchMapper;
import kr.wise.dq.criinfo.anatrg.service.WaaExpTbl;
import kr.wise.dq.criinfo.anatrg.service.WaaExpTblMapper;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.dq.scheduler.service.DqliteSchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : ColProfileServiceImpl.java
 * 3. Package  : kr.wise.dq.criinfo.profile.service.impl
 * 4. Comment  : 
 * 5. 작성자   : hwang
 * 6. 작성일   : 2014. 3. 24. 오후 1:35:18
 * </PRE>
 */
@Service("dqliteSchService")
public class DqliteSchServiceImpl implements DqliteSchService { 
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private AnaTrgMapper mapper;
	
	@Inject
	private WaaDbConnTrgMapper wMapper;
	
	@Inject
	private WaaDbSchMapper sMapper;
	
	@Inject
	private WaaExpTblMapper expMapper;
	
	@Inject
	private WaaColRuleRelMapper relMapper;
	
	@Inject
	private WamShdJobMapper jobMapper; 
	
	@Override
	public List<WamShdJob> getDqliteJobPopList(WamShdJob search) {
		
		List<WamShdJob> list = null;
		
		String shdKndCd = UtilString.null2Blank(search.getShdKndCd());
		
		if("SC".equals(shdKndCd)){			//스키마 수집
			list = jobMapper.selectJobScPopList(search);
		}else if("CR".equals(shdKndCd)){
			list = relMapper.selectDqliteJobPopList(search);
		}else if("QP".equals(shdKndCd)){		//프로파일
			list = jobMapper.selectJobQpPobList(search);
		}else if("QB".equals(shdKndCd)){		//업무규칙
			list = jobMapper.selectJobQbPopList(search); 		
		}

		return list; 
	}  
	
	
	
}
