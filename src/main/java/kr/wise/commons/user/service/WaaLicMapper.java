package kr.wise.commons.user.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;


@Mapper
public interface WaaLicMapper {
	
	List<WaaLic> selectList(WaaLic search);
	
	int updateExpDtm(WaaLic record);
	
	WaaLic selectByPrimaryKey(String licKey);
	
	int insertSelective(WaaLic record);
	
}