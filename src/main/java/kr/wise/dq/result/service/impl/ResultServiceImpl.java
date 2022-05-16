package kr.wise.dq.result.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.damgmt.schedule.service.WamShdLogMapper;
import kr.wise.commons.damgmt.schedule.service.WamShdLogVO;
import kr.wise.dq.result.service.ResultDataVO;
import kr.wise.dq.result.service.ResultMapper;
import kr.wise.dq.result.service.ResultService;
import kr.wise.dq.result.service.ResultVO;

import org.springframework.stereotype.Service;

@Service("ResultService")
public class ResultServiceImpl implements ResultService {

	@Inject
	ResultMapper resultMapper;
	
	@Inject
	private WamShdLogMapper schMapper;

	@Override
	public List<ResultVO> getResultList(ResultVO search) {
		return resultMapper.selectResultList(search);
	}

	@Override
	public ResultVO getResultTerm(ResultVO search) {
		// TODO Auto-generated method stub
		return resultMapper.selectDtm(search);
	}
	
	@Override
	public List<ResultVO> getTblList(ResultVO search) {
		return resultMapper.selectTblList(search);
	}
	
	@Override
	public List<ResultVO> getDmnRule(ResultVO search) {
		return resultMapper.selectDmnRule(search);
	}
	
	@Override
	public List<ResultVO> getExecList(ResultVO search) {
		return resultMapper.selectExecList(search);
	}
	
	@Override
	public List<ResultVO> getErrList(ResultVO search) {
		return resultMapper.selectErrList(search);
	}


	@Override
	public BigDecimal getTotCnt(ResultVO search) {
		// TODO Auto-generated method stub
		return resultMapper.selectTotCnt(search);
	}
	
	@Override
	public BigDecimal getStndTotCnt(ResultVO search) {
		// TODO Auto-generated method stub
		return resultMapper.selectStndTotCnt(search);
	}


	@Override
	public BigDecimal getRunCnt(ResultVO search) {
		// TODO Auto-generated method stub
		return resultMapper.selectRunCnt(search);
	}


	@Override
	public int getErrDataMaxColCnt(ResultVO search) {
		// TODO Auto-generated method stub
		return resultMapper.selectErrDataMaxColCnt(search);
	}


	@Override
	public List<ResultDataVO> getErrListByDqi(ResultVO search) {
		// TODO Auto-generated method stub
		return resultMapper.selectErrListByDqi(search);
	}
	
	@Override
	public BigDecimal getErrColCntByDqi(ResultVO search) {
		// TODO Auto-generated method stub
		return resultMapper.selectErrColCntByDqi(search);
	}	


	@Override
	public List<ResultDataVO> getErrDataByDqi(ResultVO search) {
		// TODO Auto-generated method stub
		return resultMapper.selectErrDataByDqi(search);
	}
	
	@Override
	public ResultVO getStndTerm(ResultVO search) {
		// TODO Auto-generated method stub
		return resultMapper.selectStndDtm(search);
	}
	
	@Override
	public ResultVO getModelTerm(ResultVO search) {
		// TODO Auto-generated method stub
		return resultMapper.selectModelDtm(search);
	}
	
	@Override
	public WamShdLogVO getScheduleTotColJobCnt(WamShdLogVO search) {
		return schMapper.selectTotColJobCnt(search);
	}

	@Override
	public ResultVO getExecPreCnt(ResultVO search) {
		return resultMapper.selectExecPreCnt(search);
	}
	
	@Override
	public BigDecimal getExecTotCnt(ResultVO search) {
		return resultMapper.selectExecTotCnt(search);
	}	

}

