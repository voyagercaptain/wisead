package kr.wise.commons.damgmt.db.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaaTblSpacMapper {
    int deleteByPrimaryKey(String dbTblSpacId);

    int insert(WaaTblSpac record);

    int insertSelective(WaaTblSpac record);

    WaaTblSpac selectByPrimaryKey(String dbTblSpacId);

    int updateByPrimaryKeySelective(WaaTblSpac record);

    int updateByPrimaryKey(WaaTblSpac record);

	/** @param record meta */
	int updateExpDtm(WaaTblSpac record);

	/** @param search
	/** @return meta */
	List<WaaTblSpac> getDbTblSpacList(WaaTblSpac search);

	/** @param waaTblSpac
	/** @return meta */
	int deleteDbTblSpac(WaaTblSpac record);

	/** @param dbTblSpacPnm
	/** @return meta */
	WaaTblSpac selectByPnm(String dbTblSpacPnm);
}