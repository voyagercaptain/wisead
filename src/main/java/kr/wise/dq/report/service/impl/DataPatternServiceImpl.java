/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DataPatternServiceImpl.java
 * 2. Package : kr.wise.commons.damgmt.schedule.service.impl
 * 3. Comment :
 * 4. 작성자  : kchoi
 * 5. 작성일  : 2014. 5. 2. 오후 1:20:23
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    kchoi : 2014. 5. 2. :            : 신규 개발.
 */
package kr.wise.dq.report.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.dq.report.service.DataPatternMapper;
import kr.wise.dq.report.service.DataPatternService;
import kr.wise.dq.report.service.DataPatternVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DataPatternServiceImpl.java
 * 3. Package  : kr.wise.commons.damgmt.schedule.service.impl
 * 4. Comment  :
 * 5. 작성자   : kchoi
 * 6. 작성일   : 2014. 5. 2. 오후 1:20:23
 * </PRE>
 */
@Service("DataPatternService")
public class DataPatternServiceImpl implements  DataPatternService{
	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private DataPatternMapper mapper;

	@Override
	public List<DataPatternVO> getDataPattern(DataPatternVO search) {
		
		String objDate = search.getObjDate();
		if (StringUtils.hasText(objDate))		
			return mapper.dptrnList(search);
		else return mapper.dptrnListNoDate(search);
	}

	@Override
	public DataPatternVO DptrnHeaderInit(DataPatternVO search) {
		return mapper.dptrnHeader(search);
	}

	@Override
	public DataPatternVO DptrnHeaderText(DataPatternVO search) {
		return mapper.DptrnHeaderText(search);
	}

	@Override
	public DataPatternVO getPrfAnaResDtl(DataPatternVO search) {
		return mapper.selectPrfAnaResDtl(search);
	}

	@Override
	public DataPatternVO getBrAnaResDtl(DataPatternVO search) {
		return mapper.selectBrAnaResDtl(search);
	}

	@Override
	public List<DataPatternVO> getPkDataPattern(DataPatternVO search) {
		return mapper.selectPkDptrnList(search);
	}

	@Override
	public DataPatternVO PkDptrnHeaderText(DataPatternVO search) {
		return mapper.PkDptrnHeaderText(search);
	}

}