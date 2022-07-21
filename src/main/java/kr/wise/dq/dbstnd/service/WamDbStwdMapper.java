package kr.wise.dq.dbstnd.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : WamDbStwdMapper.java
 * 3. Package  : kr.wise.meta.stnd.service
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 27. 오후 2:08:12
 * </PRE>
 */ 
@Mapper
public interface WamDbStwdMapper {
    int deleteByPrimaryKey(String stwdId);

    int insert(WamDbStwd record);

    int insertSelective(WamDbStwd record);

    WamDbStwd selectByPrimaryKey(String stwdId);

    List<WamDbStwd> selectList(WamDbStwd record);

    List<WamDbStwd> selectStndList(WamDbStwd record);

    int updateByPrimaryKeySelective(WamDbStwd record);

    int updateByPrimaryKey(WamDbStwd record);

    //register mapper
    int insertWamDbStwd(String rqstNo);

    int deleteWamDbStwd(String rqstNo);

	List<WamDbStwd> selectListTop30(WamDbStwd record);
	
	List<WamDbStwd> selectWordChangeList(String stwdId);


	WamDbStwd selectWordDetail(String stwdId);

	//사전 변경 이력
	List<WamDbStwd> selectAltHistoryList(WamDbStwd data);

	
	/** yeonho */
	List<WamDbStwd> selectByLnmPnm(@Param("sbswdLnm") String sbswdLnm, @Param("sbswdPnm") String sbswdPnm);
	
	//사전비교 리스트
	List<WamDbStwd> selectStndWordComparisonList(String stwdId);

    int bulkInsert(List<WamDbStwd> insertList);

    int bulkUpdate(List<WamDbStwd> updateList);

    int bulkDelete(List<WamDbStwd> deleteList);
}