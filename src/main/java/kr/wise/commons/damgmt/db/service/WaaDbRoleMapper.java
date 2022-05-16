package kr.wise.commons.damgmt.db.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaaDbRoleMapper {
    int deleteByPrimaryKey(@Param("roleId") String roleId, @Param("expDtm") Date expDtm);

    int insert(WaaDbRole record);

    int insertSelective(WaaDbRole record);

    WaaDbRole selectByPrimaryKey(@Param("roleId") String roleId);

    int updateByPrimaryKeySelective(WaaDbRole record);

    int updateByPrimaryKey(WaaDbRole record);

	/** @param search
	/** @return meta */
	List<WaaDbRole> getDbRoleList(WaaDbRole search);

	/** @param record
	/** @return meta */
	int deleteDbRole(WaaDbRole record);

	/** @param rolePnm
	/** @return meta */
	WaaDbRole selectByPnm(@Param("rolePnm") String rolePnm, @Param("dbConnTrgId")String dbConnTrgId);

	/** @param record meta */
	int updateExpDtm(WaaDbRole record);
}