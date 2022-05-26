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
public interface WamDbStcdMapper {
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
	

}