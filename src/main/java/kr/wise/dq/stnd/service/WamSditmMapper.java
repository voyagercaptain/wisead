package kr.wise.dq.stnd.service;

import java.util.List;

import kr.wise.dq.dbstnd.service.WamDbStcd;
import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamSditmMapper {
    int deleteByPrimaryKey(String sditmId);

    int insert(WamSditm record);

    int insertSelective(WamSditm record);

    WamSditm selectByPrimaryKey(String sditmId);

    int updateByPrimaryKeySelective(WamSditm record);

    int updateByPrimaryKey(WamSditm record);

	List<WamSditm> selectSditmList(WamSditm data);

	int selectDupSditmCount(WamSditm data);
	
	List<WamSditm> selectSditmChangeList(@Param("sditmId") String sditmId);
	
	List<WamSditm> getSditmTransList(WamSditm record);
	
	int saveSditmTransYnPrc(WamSditm record);
	
	List<WamSditm> selectSditmComparisonList(String sditmId);
	
	int checkStwdAbr();
	
	int checkDmnYnExsits();
	
	int checkDmnYnExsitsLnm();
	
	int updateVrfRmkNull();

	int bulkInsert(List<WamSditm> insertList);
	int bulkUpdate(List<WamSditm> updateList);

	int bulkUpdateConfirm(List<WamSditm> updateList);

	int bulkUpdateConfirm2(WamSditm data);

	int bulkDelete(List<WamSditm> deleteList);


	List<WamSditm> selectBatchSditmList();

	int updateSditmSchedule(List<WamSditm> updateList);


	Integer selectStndItemTotalCnt(WamSditm data);

	Integer selectSditmTotalCnt(WamSditm data);
}