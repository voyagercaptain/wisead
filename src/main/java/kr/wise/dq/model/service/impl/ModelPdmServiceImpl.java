/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : ModelPdmService.java
 * 2. Package : kr.wise.meta.model.service
 * 3. Comment :
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2013. 4. 26. 오후 5:48:57
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    insomnia 	: 2013. 4. 26. 		: 신규 개발.
 */
package kr.wise.dq.model.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.wise.dq.model.service.ModelPdmService;
import kr.wise.dq.model.service.WamPdmCol;
import kr.wise.dq.model.service.WamPdmColMapper;
import kr.wise.dq.model.service.WamPdmTbl;
import kr.wise.dq.model.service.WamPdmTblMapper;

/**
 * <PRE>
 * 1. ClassName : ModelPdmService
 * 2. Package  : kr.wise.meta.model.service
 * 3. Comment  :
 * 4. 작성자   : insomnia(장명수)
 * 5. 작성일   : 2013. 4. 26.
 * </PRE>
 */
@Service("modelPdmService")
public class ModelPdmServiceImpl implements ModelPdmService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WamPdmTblMapper mapper;
	
	@Inject
	private WamPdmColMapper colmapper;

	@Override
	public List<WamPdmTbl> getPdmTblList(WamPdmTbl search) {

		List<WamPdmTbl> list = mapper.selectList(search);

		return list;

	}
	
	@Override
	public List<WamPdmTbl> getPdmTblHist(WamPdmTbl search) {
		if(search.getRegTypCd() == null)		{
			search.setRegTypCd("ALL");}
		List<WamPdmTbl> list = mapper.selectHisTbl(search);
		return list;

	}

	@Override
	public List<WamPdmCol> getPdmColList(WamPdmCol search) {
		return colmapper.selectList(search);
	}

	@Override
	public List<WamPdmTbl> getPdmTblHistList(WamPdmTbl search){
		List<WamPdmTbl> list = mapper.selectHisTbl(search);

		return list;
	}

	@Override
	public List<WamPdmCol> getPdmColHistList(WamPdmTbl search){
		return colmapper.selectcolhisttList(search);
	}
}
