package kr.wise.dq.result.service;

import java.math.BigDecimal;
import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : ResultMapper.java
 * 3. Package  : kr.wise.dq.result.service
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2019. 06. 05.
 * </PRE>
 */ 
@Mapper
public interface ResultMapper {

	/**
	 * @param search
	 * @return meta
	 * 진단결과조회 */
	List<ResultVO> selectResultList(ResultVO search);
	
	ResultVO selectDtm(ResultVO search);

	List<ResultVO> selectTblList(ResultVO search);

	List<ResultVO> selectDmnRule(ResultVO search);

	List<ResultVO> selectExecList(ResultVO search);

	List<ResultVO> selectErrList(ResultVO search);
	
	BigDecimal selectTotCnt(ResultVO search);

	BigDecimal selectRunCnt(ResultVO search);

	int selectErrDataMaxColCnt(ResultVO search);

	List<ResultDataVO> selectErrListByDqi(ResultVO search);
	
	BigDecimal selectErrColCntByDqi(ResultVO search);

	List<ResultDataVO> selectErrDataByDqi(ResultVO search);

	ResultVO selectStndDtm(ResultVO search);

	ResultVO selectModelDtm(ResultVO search);

	BigDecimal selectStndTotCnt(ResultVO search);
	
	ResultVO selectExecPreCnt(ResultVO search);
	
	BigDecimal selectExecTotCnt(ResultVO search);

}
