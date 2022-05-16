package kr.wise.dq.criinfo.anatrg.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaaExpColMapper { 

	List<WaaExpCol> selectList(WaaExpCol search);
	
	List<WaaExpCol> selectconntrglist(WaaExpCol search); 

	List<WaaExpCol> selectListByFK(String dbConnTrgId);

	WaaExpCol selectByPrimaryKey(String dbSchId);

	int updateExpDtm(String dbSchId);

	int insertSelective(WaaExpCol record); 

	int updateByPrimaryKeySelective(WaaExpCol saveVo);
	
	int deleteByPrimaryKeySelective(WaaExpCol saveVo);

	List<WaaExpCol> selectTrgTbl(WaaExpCol vo);

	
}

