package kr.wise.commons.user.service;

import java.util.ArrayList;
import java.util.List;

public interface UserService {

	public List<WaaUser> getList(WaaUser search) ;

	public List<WaaUser> getListOrderByDeptNm(WaaUser search) ;

	public WaaUser findUser(WaaUser search) ;

	public int regUser(WaaUser record) ;

	public int regList(ArrayList<WaaUser> list) ;


	public int delList(ArrayList<WaaUser> list) ;

	/** @param search
	/** @return insomnia 
	 * @throws Exception */
	public List<WaaUser> getUserListbyDept(WaaUser search) throws Exception;

	WaaUser getUserInfo(String userid);
	
		/** 15.10.29 pOOh */
	public int chngUserInfo(WaaUser record) ;
	
	/**
	 * 2021-06-15 sohyeonAn
	 */
	int idCheck(String userId) throws Exception;
	
	int register(WaaUser record) throws Exception;
	
	void updateVerify(String userId) throws Exception;
	
	// 기관 조회 추가 by thomas 2022.05.30
	// TODO : 별도의 서비스 패키지 구성 여부 고민
	public List<WaaOrg> getOrgList(WaaOrg waaOrg) throws Exception;
}
