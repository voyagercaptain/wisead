package kr.wise.dq.dbstnd.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamDbSditmMapper {
    int deleteByPrimaryKey(String sditmId);

    int insert(WamDbSditm record);

    int insertSelective(WamDbSditm record);

    WamDbSditm selectByPrimaryKey(String sditmId);

    int updateByPrimaryKeySelective(WamDbSditm record);

    int updateByPrimaryKey(WamDbSditm record);

	List<WamDbSditm> selectSditmList(WamDbSditm data);
	
	List<WamDbSditm> selectSditmChangeList(@Param("sditmId") String sditmId);
	
	List<WamDbSditm> getSditmTransList(WamDbSditm record);
	
	int saveSditmTransYnPrc(WamDbSditm record);
	
	List<WamDbSditm> selectSditmComparisonList(String sditmId);
	
	
	List<WamDbSditm> selectUserDbList(String userId);
	
	int checkStwdAbr();
	
	int checkDmnYnExsits();
	
	int checkDmnYnExsitsLnm();
	
	int updateVrfRmkNull();
	
}