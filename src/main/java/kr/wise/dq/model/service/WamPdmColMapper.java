package kr.wise.dq.model.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamPdmColMapper {
    int insert(WamPdmCol record);

    int insertSelective(WamPdmCol record);

	List<WamPdmCol> selectPdmColList(WamPdmCol search);
	
    List<WamPdmCol> selectList(WamPdmCol search);

	List<WamPdmCol> selectcolhisttList(WamPdmTbl search);

	WamPdmCol selectByPrimaryKey(String pdmTblId);

	int deleteByPrimaryKey(WamPdmCol pdmCol);

	int updateTblId(WamPdmCol pdmCol);
}