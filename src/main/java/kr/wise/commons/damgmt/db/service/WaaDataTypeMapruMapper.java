package kr.wise.commons.damgmt.db.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaaDataTypeMapruMapper {
    int deleteByPrimaryKey(String dataTypeMapruId);

    int insert(WaaDataTypeMapru record);

    int insertSelective(WaaDataTypeMapru record);

    WaaDataTypeMapru selectByPrimaryKey(String dataTypeMapruId);

    int updateByPrimaryKeySelective(WaaDataTypeMapru record);

    int updateByPrimaryKey(WaaDataTypeMapru record);

	/** @param search meta 
	 * @return */
	List<WaaDataTypeMapru> selectList(WaaDataTypeMapru search);

	/** @param record meta 
	 * @return */
	int updateExpDtm(WaaDataTypeMapru record);
}