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
package kr.wise.dq.profile.coldtfrmana.service.impl;


import java.util.List;

import javax.inject.Inject;

import kr.wise.dq.profile.coldtfrmana.service.ProfilePC03Service;
import kr.wise.dq.profile.coldtfrmana.service.WamPrfDtfrmAnaMapper;
import kr.wise.dq.profile.coldtfrmana.service.WamPrfDtfrmAnaVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrCommonVO;


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
@Service("profilePC03Service")
public class ProfilePC03ServiceImpl implements ProfilePC03Service{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	WamPrfDtfrmAnaMapper wamMapper;

	@Override
	public WamPrfDtfrmAnaVO getPrfPC03Dtl(String prfId) {
		WamPrfDtfrmAnaVO result = wamMapper.selectByPrimaryKey(prfId);
		return result;
	}
	/* 날짜분석 프로파일 조회 */
	@Override
	public List<WamPrfMstrCommonVO> profileListDateAna(WamPrfMstrCommonVO search) {
		return wamMapper.profileListDateAna(search);
	}
	
}
