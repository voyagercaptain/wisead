package kr.wise.dq.model.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface WamPdmTblMapper {
    int insert(WamPdmTbl record);

    int insertSelective(WamPdmTbl record);

	List<WamPdmTbl> selectPdmTblList(WamPdmTbl search);
    
	int updateSelective(WamPdmTbl search);
	
	int deleteByPrimaryKey(WamPdmTbl search);

	WamPdmTbl selectByPrimaryKey(@Param("pdmTblId") String pdmTblId);
	
	List<WamPdmTbl> selectList(WamPdmTbl search);

	List<WamPdmTbl> selectHisTbl(WamPdmTbl search);
}