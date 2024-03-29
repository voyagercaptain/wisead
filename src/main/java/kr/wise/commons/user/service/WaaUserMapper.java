package kr.wise.commons.user.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.user.WaaUserg;

import kr.wise.commons.cmm.annotation.Mapper;
import org.apache.commons.logging.Log;


@Mapper
public interface WaaUserMapper {

	int deleteAll();

	int deleteByPrimaryKey(String userId);

    int insert(WaaUser record);

    int insertSelective(WaaUser record);

    WaaUser selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(WaaUser record);

    int updateByPrimaryKey(WaaUser record);

    int deleteRegTypCd(WaaUser record);

    List<WaaUser> selectList(WaaUser record);

    List<WaaUser> selectListbyDept(WaaUser record);

    List<WaaUser> selectListOrderByDeptNm(WaaUser record);

	int updateExpDtm(WaaUser record);

	/** @param userid
	/** @return meta */
	WaaUser selectUserInfo(String userid);

    /** 15.10.29 pOOh */
    int updateUserInfo(WaaUser record);
    
	//??? ???
    int idCheck(String userId);
    
    //??? ????
    int register(WaaUser record);
    
    //????? ????
    void updateVerify(String userId);
    
    // 데이터베이스 정보 추가 by voyager 2022.05.23
    void registerDbName(List<Map<String, String>> dbList);

    int deleteDbNameByUserId(String userId);
    
    // 기관 정보 추가 by voyager 2022.05.30
    void registOrgCd(WaaUser orgInfo);

    int deleteOrgCdByUserId(String userId);
    
    public List<WaaOrg> selectOrgList(WaaOrg waaOrg);
    
    int regOrgList(WaaOrg record);

    int initPwd(WaaUser waaUser);

    int regTargetOrg();

    List<Map> selectTargetOrg();

    int deleteTargetOrgDb(List<Map> orgList);

    int regTargetOrgDb();

    int updateTargetDbYn();
}