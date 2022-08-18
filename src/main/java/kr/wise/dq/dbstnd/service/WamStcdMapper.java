package kr.wise.dq.dbstnd.service;

import java.util.List;

import kr.wise.dq.stnd.service.WamDmn;
import kr.wise.dq.stnd.service.WamSditm;
import kr.wise.dq.stnd.service.WamStwd;
import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : WamStcdMapper.java
 * 3. Package  : kr.wise.meta.stnd.service
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 27. 오후 2:08:12
 * </PRE>
 */ 
@Mapper
public interface WamStcdMapper {
    int deleteByPrimaryKey(String stwdId);

    int insert(WamDbStcd record);

    int insertSelective(WamDbStcd record);

    WamDbStcd selectByPrimaryKey(String stwdId);

    List<WamDbStcd> selectList(WamDbStcd record);
   

    int updateByPrimaryKeySelective(WamDbStcd record);

    int updateByPrimaryKey(WamDbStcd record);

    //register mapper
    int insertWamDbStcd(String rqstNo);

    int deleteWamDbStcd(String rqstNo);

	List<WamDbStcd> selectListTop30(WamDbStcd record);
	
	List<WamDbStcd> selectWordChangeList(String stwdId);


	WamDbStcd selectWordDetail(String stwdId);


	
	/** yeonho */
	List<WamDbStcd> selectByLnmPnm(@Param("sbswdLnm") String sbswdLnm, @Param("sbswdPnm") String sbswdPnm);
	
	//사전비교 리스트
	List<WamDbStcd> selectStndWordComparisonList(String stwdId);
	
    int bulkInsert(List<WamDbStcd> insertList);

    int bulkUpdate(List<WamDbStcd> updateList);

    int bulkUpdateConfirm(List<WamDbStcd> updateList);

    int bulkDelete(List<WamDbStcd> deleteList);

    int selectDupSdCodeCount(WamDbStcd data);


    List<WamDbStcd> selectBatchStcdList();

    int updateStcdSchedule(List<WamDbStcd> updateList);


}