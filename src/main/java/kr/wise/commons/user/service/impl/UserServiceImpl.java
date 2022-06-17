/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : UserService.java
 * 2. Package : kr.wise.cmvw.user.service
 * 3. Comment :
 * 4. 작성자  : (ycyoo)유연철
 * 5. 작성일  : 2013. 4. 24.
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    유연철 	: 2013. 4. 24. 		: 신규 개발.
 */
package kr.wise.commons.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.sysmgmt.dept.service.WaaDept;
import kr.wise.commons.sysmgmt.dept.service.WaaDeptMapper;
import kr.wise.commons.user.service.UserService;
import kr.wise.commons.user.service.WaaOrg;
import kr.wise.commons.user.service.WaaUser;
import kr.wise.commons.user.service.WaaUserMapper;
import kr.wise.commons.util.UtilObject;
import kr.wise.dq.dbstnd.service.WamDbSditmMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import kr.wise.commons.WiseConfig;
//import seed.EncryptUtils;

/**
 * <PRE>
 * 1. ClassName : UserService
 * 2. Package  : kr.wise.cmvw.user.service
 * 3. Comment  :
 * 4. 작성자   : (ycyoo)유연철
 * 5. 작성일   : 2013. 4. 24.
 * </PRE>
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WaaUserMapper mapper;
	
	@Inject
	private WaaDeptMapper deptMapper;
	
	@Inject
	private WamDbSditmMapper wamDbSditmMapper;
	
	@Inject
	private BasicInfoLvlService basicInfoLvlService;

	@Override
	public List<WaaUser> getList(WaaUser search) {
		
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		// 2 : 총괄 관리자는 전체 조회
		if (!"2".equals(user.getUsergId())) {
			search.setUsergId(user.getUsergId());
			search.setOrgCd(user.getOrgCd());
		}

		List<WaaUser> list = mapper.selectList(search);
//		for(int i=0; i< list.size(); i++){
//		     list.get(i).setLoginAcPwd(EncryptUtils.getDecData(list.get(i).getLoginAcPwd(), list.get(i).getLoginAcId()));	
//		}
		return list;
	}

	@Override
	public List<WaaUser> getListOrderByDeptNm(WaaUser search) {

		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		search.setUserId(user.getUniqId());
		

		List<WaaUser> list = mapper.selectListOrderByDeptNm(search);
		return list;
	}

	@Override
	public WaaUser findUser(WaaUser search) {

		return mapper.selectByPrimaryKey(search.getUserId());
	}

	@Override
	public int regUser(WaaUser record) {
		String tmpStatus = record.getIbsStatus();
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;
        String decPw="";
		//부서ID를 기반으로 데이터가 있는지 확인하여 없을경우 공백처리...
		if(record.getDeptId() != null && StringUtils.hasText(record.getDeptId())) {
			WaaDept tmpDeptVo = deptMapper.selectByPrimaryKey(record.getDeptId());
			if(tmpDeptVo == null || !tmpDeptVo.getDeptId().equals(record.getDeptId())) {
				record.setDeptId(null);
			}
		}
		
		
		//사용자ID가 없을경우 신규로, 그렇지 않을경우 수정으로 처리
		if("I".equals(tmpStatus) && !StringUtils.hasText(record.getUserId())) {  // 신규...
			
			record.setObjVers(1);
			record.setRegTypCd("C");
			record.setLoginAcPwd("8c6976e5b541415bde98bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918"); // 암호화된 "admin"이 기본 비밀번호
			
//			if(WiseConfig.SECURITY_APPLY.equals("Y")){
//			    record.setLoginAcPwd(EncryptUtils.getEncData(record.getLoginAcPwd(),record.getLoginAcId()));
//		    }
		} else if("U".equals(tmpStatus) || StringUtils.hasText(record.getUserId())) {
			//사용자ID를 기반으로 데이터가 있는지 확인하여 없을경우 신규처리..
			WaaUser tmpVO = mapper.selectByPrimaryKey(record.getUserId());
			if (tmpVO == null || !tmpVO.getUserId().equals(record.getUserId())) { 
				
				record.setObjVers(1);
				record.setRegTypCd("C");
				record.setLoginAcPwd("8c6976e5b541415bde98bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918"); // 암호화된 "admin"이 기본 비밀번호
			} else {
				if (UtilObject.isNull(record.getObjVers())) {
					record.setObjVers(1);
				}
				else { 
					record.setObjVers(record.getObjVers()+1);
				}
//				if(WiseConfig.SECURITY_APPLY.equals("Y")){
//				  decPw = EncryptUtils.getDecData(record.getLoginAcPwd(), record.getLoginAcId());
//				  if(decPw==null||decPw.equals("")){
//			         record.setLoginAcPwd(seed.EncryptUtils.getEncData(record.getLoginAcPwd(), record.getLoginAcId()));
//				  }
//			    }
				// 수정 상태일때 insert시  기존 비밀번호가 전달될 수 있도록
				record.setLoginAcPwd(tmpVO.getLoginAcPwd());
				record.setRegTypCd("U");
				mapper.updateExpDtm(record);
			}
		} 
		
		record.setUserId(record.getLoginAcId());		
		record.setRqstUserId(user.getUniqId());
		record.setAprvUserId(user.getUniqId());
		result = mapper.insertSelective(record);
		
		// 사용자 정보 변경시 데이터베이스 정보 추가 by voyager 2022.05.23
		// delete all
		mapper.deleteDbNameByUserId(record.getUserId());
					
		if (!"".equals(record.getDbName())) {
			
			String[] dbNameArray = record.getDbName().trim().split(",");
			List<Map<String, String>> dbList = new ArrayList<Map<String, String>>();
			
			for (String dbName : dbNameArray) {
				Map<String, String> dbMap = new HashMap<String, String>();
				dbMap.put("userId", record.getUserId());
				dbMap.put("dbName", dbName.trim());
				dbMap.put("rqstUserId", record.getRqstUserId());
				
				dbList.add(dbMap);
			}
			
			// insert all
			mapper.registerDbName(dbList);
		}
		
		// 사용자 기관 정보 삭제
		mapper.deleteOrgCdByUserId(record.getUserId());
		
		// 사용자 기관 정보 등록
		if (!"".equals(record.getOrgCd())) {
			mapper.registOrgCd(record);
		}
		return result;
	}

	@Override
	public int regList(ArrayList<WaaUser> list) {

		int result = 0;

		for (WaaUser waaUser : list) {
			result += regUser(waaUser);
		}

		return result;

	}


	@Override
	public int delList(ArrayList<WaaUser> list) {

		int result = 0;

		for (WaaUser WaaUser : list) {
			String id = WaaUser.getUserId();
			if (id != null && !"".equals(id)) {
				WaaUser.setIbsStatus("D");

				result += mapper.updateExpDtm(WaaUser);
				mapper.deleteOrgCdByUserId(WaaUser.getUserId());
			}
		}

		return result;

	}

	/** insomnia 
	 * @throws Exception */
	public List<WaaUser> getUserListbyDept(WaaUser search) throws Exception {
		String userid = ((LoginVO) UserDetailHelper.getAuthenticatedUser()).getUniqId();
		search.setUserId(userid);

		//부서의 기본정보레벨 값을 불러온다.
		WaaBscLvl bscLvl = basicInfoLvlService.selectBasicInfoList("DEPT");
				
		List<Integer> lvlList = new ArrayList<Integer>();
		for (int i=0; i<bscLvl.getBscLvl(); i++){
			lvlList.add(i);
		}
				
		search.setLvlList(lvlList);
		
		return mapper.selectListbyDept(search);
	}

	/** meta */
	//유저 정보(사용자그룹 포함) 조회
	@Override
	public WaaUser getUserInfo(String userid) {
		return mapper.selectUserInfo(userid);
	}

	/** 15.10.29 pOOh */
	//사용자정보 변경
	@Override
	public int chngUserInfo(WaaUser record) {
		int result = 0;
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		//String loginAcPwdEnc ="";
		
		//* pOOh 15.05.06*/
		// 패스워드 암호화
	//	try {
	//		Hash md = new Hash();
	//		loginAcPwdEnc = md.encrypt("SHA", record.getLoginAcPwd());
	//		record.setLoginAcPwd(loginAcPwdEnc);
	//	} catch (Exception e) {
	//	}
		//* pOOh*/
		
		//패스워드 암호화
		if(WiseConfig.SECURITY_APPLY.equals("Y")){
			record.setLoginAcPwd(KISA_SHA256.SHA256_Encrpyt(record.getLoginAcPwd()));
	    }
		
		record.setUserId(record.getUserId());		
		record.setRqstUserId(record.getUserId());
		record.setAprvUserId(record.getUserId());
		//logger.debug("serviceimpl");
		//logger.debug("{}", record);
		
		result = mapper.updateUserInfo(record);
		return result;
	}
	
	@Override
	public int idCheck(String userId) throws Exception {
		return mapper.idCheck(userId);
	}

	@Override
	public int register(WaaUser record) throws Exception {
		
		//패스워드 암호화
		if(WiseConfig.SECURITY_APPLY.equals("Y")){
			record.setLoginAcPwd(KISA_SHA256.SHA256_Encrpyt(record.getLoginAcPwd()));
	    }
		return mapper.register(record);
	}

	@Override
	public void updateVerify(String userId) throws Exception {
		mapper.updateVerify(userId);
	}
	
	// 기관 조회 추가 by thomas 2022.05.30
	// TODO : 별도의 서비스 패키지 구성 여부 고민
	@Override
	public List<WaaOrg> getOrgList(WaaOrg waaOrg) throws Exception {
		return mapper.selectOrgList(waaOrg);
	}
	
	public int regOrgList(ArrayList<WaaOrg> list) throws Exception {
		int result = 0;

		for (WaaOrg WaaOrg : list) {
			mapper.regOrgList(WaaOrg);
		}
		
		return result;
	}

	@Override
	public List<WaaOrg> getOrgDbList(WaaOrg waaOrg) throws Exception {
		return wamDbSditmMapper.selectOrgDbList(waaOrg);
	}

	@Override
	public int initPwd(WaaUser loginVO) throws Exception {
		return mapper.initPwd(loginVO);
	}
}
