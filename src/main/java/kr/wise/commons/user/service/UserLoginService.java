package kr.wise.commons.user.service;

import kr.wise.commons.cmm.LoginVO;

public interface UserLoginService {
	
	LoginVO actionLogin(LoginVO vo) throws Exception;

	LoginVO actionSsoLogin(LoginVO loginVO) throws Exception;

	LoginVO actionLoginbyAdmin(WaaUser uservo) throws Exception;

}
