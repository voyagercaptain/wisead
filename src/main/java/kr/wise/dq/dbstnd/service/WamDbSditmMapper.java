package kr.wise.dq.dbstnd.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.user.service.WaaOrg;

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
	
	List<WamDbSditm> selectUserOrgList(String userId);
	
	List selectOrgDbList(WaaOrg waaOrg);
	
	WamDbSditm selectUserOrg(String userId);
	
	int checkStwdAbr();
	
	int checkDmnYnExsits();
	
	int checkDmnYnExsitsLnm();
	
	int updateVrfRmkNull();
	
	
    int bulkInsert(List<WamDbSditm> insertList);

	int bulkUpdate(List<WamDbSditm> updateList);

	int bulkDelete(List<WamDbSditm> deleteList);
	
	 Map<String, String> dupliCheckDbStndItem(Map<String, String> param);
}