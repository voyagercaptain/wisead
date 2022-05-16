package kr.wise.commons.user.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.security.License;
import kr.wise.commons.user.service.UserLoginService;
import kr.wise.commons.user.service.UserMapper;
import kr.wise.commons.user.service.WaaUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;






//import seed.EncryptUtils;
import kr.wise.commons.WiseConfig;

@Service("loginService")
public class UserLoginServiceImpl implements UserLoginService {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Inject
	private UserMapper	userMapper;
	
	public LoginVO actionLogin(LoginVO vo) throws Exception {
		/** 건강보험 포탈용 로그인 */
//		LoginVO loginVO = userMapper.getLoginUser(vo);

		//vo.setPassword(EncryptUtils.getEncData(vo.getPassword(), vo.getId()));
		/** WISE DA 용 로그인 */
		//패스워드 암호화
		if(WiseConfig.SECURITY_APPLY.equals("Y")){
			vo.setPassword(KISA_SHA256.SHA256_Encrpyt(vo.getPassword()));
	    }
		LoginVO loginVO = userMapper.getLoginUserDA(vo);
    	
		// 3. 결과를 리턴한다.
    	if (loginVO != null && !loginVO.getId().equals("") && !loginVO.getPassword().equals("")) {
    		return loginVO;
    	} else {
    		loginVO = new LoginVO();
    	}
    	return loginVO;
	}

	public LoginVO actionSsoLogin(LoginVO vo) throws Exception {
		LoginVO loginVO = userMapper.getLoginUserSso(vo);
		// 3. 결과를 리턴한다.
		if (loginVO != null && !loginVO.getId().equals("") && !loginVO.getPassword().equals("")) {
			return loginVO;
		} else {
			loginVO = new LoginVO();
		}
		return loginVO;
	}

	@Override
	public LoginVO actionLoginbyAdmin(WaaUser uservo) throws Exception {
		return userMapper.selectLoginUserbyAdmin(uservo);
	}

}
