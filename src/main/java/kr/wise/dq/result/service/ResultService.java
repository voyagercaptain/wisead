package kr.wise.dq.result.service;

import java.math.BigDecimal;
import java.util.List;

import kr.wise.commons.damgmt.schedule.service.WamShdLogVO;

public interface ResultService {

	List<ResultVO> getResultList(ResultVO search);

	ResultVO getResultTerm(ResultVO search);

	List<ResultVO> getTblList(ResultVO search);

	List<ResultVO> getDmnRule(ResultVO search);

	List<ResultVO> getExecList(ResultVO search);

	List<ResultVO> getErrList(ResultVO search);

	BigDecimal getTotCnt(ResultVO search);

	BigDecimal getRunCnt(ResultVO search);

	int getErrDataMaxColCnt(ResultVO search);

	List<ResultDataVO> getErrListByDqi(ResultVO search);
	
	BigDecimal getErrColCntByDqi(ResultVO search);	

	List<ResultDataVO> getErrDataByDqi(ResultVO search);
	
	ResultVO getStndTerm(ResultVO search);
	
	ResultVO getModelTerm(ResultVO search);

	BigDecimal getStndTotCnt(ResultVO search);
	
	WamShdLogVO getScheduleTotColJobCnt(WamShdLogVO search);

	ResultVO getExecPreCnt(ResultVO search);
	
	BigDecimal getExecTotCnt(ResultVO search);
}
