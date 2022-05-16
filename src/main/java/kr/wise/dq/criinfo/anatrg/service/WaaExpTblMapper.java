package kr.wise.dq.criinfo.anatrg.service;

import java.math.BigDecimal;
import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaaExpTblMapper { 

	List<WaaExpTbl> selectList(WaaExpTbl search);
	
	List<WaaExpTbl> selectconntrglist(WaaExpTbl search); 

	List<WaaExpTbl> selectListByFK(String dbConnTrgId);

	WaaExpTbl selectByPrimaryKey(String dbSchId);

	int updateExpDtm(String dbSchId);

	int insertSelective(WaaExpTbl record); 

	int updateByPrimaryKeySelective(WaaExpTbl saveVo);

	List<WaaExpTbl> selectTrgTbl(WaaExpTbl vo);
	
	BigDecimal selectTrgTblCnt(WaaExpTbl vo);	
	
}

