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
}
