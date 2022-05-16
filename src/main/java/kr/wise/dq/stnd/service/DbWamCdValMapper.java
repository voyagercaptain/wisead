package kr.wise.dq.stnd.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.dq.model.service.WamNiaPdmCol;

@Mapper
public interface DbWamCdValMapper {
	
	List<DbWamCdVal> selectDbWamList(DbWamCdVal data);

	int dbinsertSelective(DbWamCdVal saveVo);

	int dbupdateByPrimaryKey(DbWamCdVal saveVo);

	int dbdeleteByPrimaryKey(String cdValId);

}
