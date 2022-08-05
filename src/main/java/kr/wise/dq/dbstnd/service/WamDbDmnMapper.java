package kr.wise.dq.dbstnd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamDbDmnMapper {
    int deleteByPrimaryKey(String dmnId);

    int insert(WamDbDmn record);

    int insertSelective(WamDbDmn record);

    WamDbDmn selectByPrimaryKey(String dmnId);

    List<WamDbDmn> selectList(WamDbDmn record);
    List<WamDbDmn> selectListSLC(WamDbDmn record);

    int updateByPrimaryKeySelective(WamDbDmn record);

    int updateByPrimaryKey(WamDbDmn record);

    //register mapper
    int insertWamDbDmn(String rqstNo);

    int deleteWamDbDmn(String rqstNo);

	List<WamDbDmn> selectTop30(WamDbDmn record);
	
	List<WamDbDmn> selectDmnChangeList(@Param("dmnId") String dmnId);
	
	Map<String, String> selectDomainDataType(Map<String, String> param);
	
	Map<String, String> selectDbDomainDataType(Map<String, String> param);

    int bulkInsert(List<WamDbDmn> insertList);

    int bulkUpdate(List<WamDbDmn> updateList);

    int bulkDelete(List<WamDbDmn> deleteList);
    
    int dupliCheckDbStndDmn(Map<String, String> param);
    
    List<WamDbDmn> selectDbDmnList();
    
    int updateDbDmnSchedule(List<WamDbDmn> updateList);
}