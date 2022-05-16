package kr.wise.commons.user.service;

import kr.wise.commons.cmm.LoginVO;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface UserMapper {
	
	LoginVO getLoginUser(LoginVO loginvo) throws Exception;
	
	LoginVO getLoginUserSso(LoginVO loginvo) throws Exception;
	
	LoginVO getLoginUserDA (LoginVO loginvo) throws Exception;

	LoginVO selectLoginUserbyAdmin(WaaUser uservo) throws Exception;

}
