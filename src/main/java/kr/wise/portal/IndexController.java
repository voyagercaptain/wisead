package kr.wise.portal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.crypto.Cipher;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.wise.commons.WiseConfig;
import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.bbs.service.BBSAttributeManageService;
import kr.wise.commons.bbs.service.BBSManagerService;
import kr.wise.commons.bbs.service.BoardVO;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.security.License;
import kr.wise.commons.cmm.security.RSA;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.commons.handler.MailHandler;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaaBizInfo;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.menu.service.MenuManageService;
import kr.wise.commons.sysmgmt.menu.service.UsergMenuMapService;
import kr.wise.commons.user.service.UserLoginService;
import kr.wise.commons.user.service.UserService;
import kr.wise.commons.user.service.WaaUser;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.dashboard.service.DqDashService;
import kr.wise.portal.dashboard.service.DqMainChartVO;
import kr.wise.portal.dashboard.service.TotalCountVO;
import kr.wise.portal.dashboard.service.TotalDashService;
import kr.wise.portal.myjob.service.ApprReqService;
import kr.wise.portal.myjob.service.RequestJobService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class IndexController {

	private final Logger log = LoggerFactory.getLogger(getClass());


	@Inject
	private UserLoginService loginService;

	@Inject
	private RequestJobService reqJobService;

	@Inject
	private ApprReqService apprReqService;

	@Inject
	private BBSAttributeManageService bbsAttrbService ;

	@Inject
	private BBSManagerService bbsMngService;

	@Inject
	private RequestMstService requestMstService;

	

	@Inject
	private DqDashService dqdashService;
	
	
	@Inject
	private UserService userService;
	

	@Inject
	private BasicInfoLvlService basicInfoLvlService;
	
	@Inject
	private MenuManageService menuManageService;
	
	@Inject
	private MessageSource message;
	
	@Inject
	private UsergMenuMapService usergMenuMapService;
	
	/*
	 * @Inject private JavaMailSender mailSender;
	 */
	
	@Inject
	private TotalDashService totalDashService;
	
	@Inject
	private CodeListService codelistService;

	@Inject
	private CmcdCodeService cmcdCodeService;
	
	
	@Value("#{configure['wiseda.langDcd']}")     
	private String langDcd;
	
	private Map<String, Object> codeMap;

	
	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();
		//진단대상
		List<CodeListVo> connTrgDbms = codelistService.getCodeList(CodeListAction.connTrgDbms);

		codeMap.put("connTrgDbmsibs", UtilJson.convertJsonString(codelistService.getCodeListIBS(connTrgDbms)));
		codeMap.put("connTrgDbmsCd", connTrgDbms); 
	
		return codeMap;
	}

	@RequestMapping("/")
	public String goLogin(HttpSession session, Model model) throws Exception {
		if(UserDetailHelper.isAuthenticated()) {
			
			return mainForward();
			
		} else {
			//RSA 암호화 추가
	        /*KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
	        generator.initialize(512);
	        KeyPair keyPair = generator.genKeyPair();
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        PublicKey publicKey = keyPair.getPublic();
	        PrivateKey privateKey = keyPair.getPrivate();

	        session.setAttribute("_RSA_WEB_Key_", privateKey);   //세션에 RSA 개인키를 세션에 저장한다.
	        RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
	        String publicKeyModulus = publicSpec.getModulus().toString(16);
	        String publicKeyExponent = publicSpec.getPublicExponent().toString(16);

	        model.addAttribute("RSAModulus", publicKeyModulus);
	        model.addAttribute("RSAExponent", publicKeyExponent);

	        log.debug("RSAModulus[{}], RSAExponent[{}]", publicKeyModulus, publicKeyExponent);*/

	        /*request.setAttribute("RSAModulus", publicKeyModulus);  //로그인 폼에 Input Hidden에 값을 셋팅하기위해서
	        request.setAttribute("RSAExponent", publicKeyExponent);   //로그인 폼에 Input Hidden에 값을 셋팅하기위해서
	        rest = "login";*/

			//SSO 로그인 연계
//			return "/ssologin";
			
			String sLangDcd = UtilString.null2Blank(langDcd);
			
			log.debug("LOCALE_SESSION_ATTRIBUTE_NAME : {}", langDcd);
			
			if("en".equals(langDcd)) {
			
				session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.ENGLISH);
			} else if ("kr".equals(langDcd)) {
				session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.KOREAN);
				
			} else {
				session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.getDefault());
				
			}

			//일반 로그인일 경우
			return "redirect:/loginform.do";
//			return "/login_pop";
		}
	}

	
	@RequestMapping("/login_pop.do")
	public String goLoginPop(HttpSession session, Model model) throws Exception {
		if(session != null) {
			session.invalidate();
		}	
		return "/login_pop";
	}

	/** @return insomnia */
	private String mainForward() {
		String mainnm = message.getMessage("wiseda.main.name", null, Locale.getDefault());
//		if ("META".equals(mainnm)) {
//			return "redirect:/main.do";
//		} else if ("DQ".equals(mainnm)) {
//			return "redirect:/dqmain.do";
//		} else if ("ADVISOR".equals(mainnm)) {
//			return "redirect:/admain.do";
//		} else {
//			return "redirect:/main.do";
//		}
		
		//임시로 바꿔놓음
		//return "redirect:/dq/model/nia_pdmcol_rqst.do";
		return "redirect:/dq/dbstnd/SDITM/stndtot_rqst.do";
		
	}
	
	private String readLicense() {
		String line = "";
		
		try{
            //파일 객체 생성
            File file = new File(message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.LICENSE_PATH, null, Locale.getDefault()) + "/dqlite_license");
            //입력 스트림 생성
            FileReader filereader = new FileReader(file);
            //입력 버퍼 생성
            BufferedReader bufReader = new BufferedReader(filereader);
            line = "";
            /*while((line = bufReader.readLine()) != null){
                System.out.println(line);
            }*/
            line = bufReader.readLine();
            //.readLine()은 끝에 개행문자를 읽지 않는다.            
            bufReader.close();
        }catch (Exception e) {
            // TODO: handle exception
        }
		
		return line;
	}

	@RequestMapping("/login.do")
	public String checkLogin(HttpSession session, HttpServletRequest request,  @ModelAttribute("loginVO") LoginVO loginVO, Model model) throws Exception {
		String license = readLicense();
		String realLicense = License.getLicense();
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat ( "yyyy-MM-dd");
				
		Date time = new Date();
				
		String time1 = format1.format(time);
		time = format1.parse(time1);
		Date time2 = format2.parse("2022-12-31");
		
		if(!license.equals(realLicense) && !license.equals("8c6976e5b541415bde98bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918")
				&& (time.compareTo(time2) > 0)) {
			
			//log.debug(license);
			//log.debug(License.getLicense());
			//model.addAttribute("licenseError", "error");
			return "forward:/loginform.do?licenseError=error";
		}
		
		if(UserDetailHelper.isAuthenticated()) {
			return mainForward();
		}
		
		// 2. 메인화면 입력값 유무체크(로그인 화면에서 호출되는지 체크)
		if(loginVO != null) {
			String sLangDcd = UtilString.null2Blank(langDcd); 
			
			//국제화 지원 
			log.debug("LOCALE_SESSION_ATTRIBUTE_NAME : {}", langDcd);
			
			if("en".equals(langDcd)) {
			
				session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.ENGLISH);
			} else if ("kr".equals(langDcd)) {
				session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.KOREAN);
				
			} else {
				session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.getDefault());
				
			}
			   
			PrivateKey privateKey = (PrivateKey) session.getAttribute("__rsaPrivateKey__");
	        session.removeAttribute("__rsaPrivateKey__"); // 키의 재사용을 막는다. 항상 새로운 키를 받도록 강제.

	        if (privateKey == null) {
	            throw new RuntimeException("암호화 비밀키 정보를 찾을 수 없습니다.");
	        }

	        //log.debug("loginVO.getSecuredUsername() >>> " + loginVO.getSecuredUsername());
	        //log.debug("loginVO.getSecuredPassword() >>> " + loginVO.getSecuredPassword());
	        
	        String rsausername = decryptRsa(privateKey, loginVO.getSecuredUsername());
            String rsapassword = decryptRsa(privateKey, loginVO.getSecuredPassword());

            loginVO.setId(rsausername);
            loginVO.setPassword(rsapassword);

//			loginVO.setId(decodeBase64(loginVO.getSecuredUsername()));
//			loginVO.setPassword(decodeBase64(loginVO.getSecuredPassword()));

			log.debug("userid[{}], pass[{}]", loginVO.getId(), loginVO.getPassword());
			
			//로그인 화면에서 호출된 경우
			LoginVO resultVO = loginService.actionLogin(loginVO);
	        if (resultVO != null && resultVO.getId() != null && !resultVO.getId().equals("")) {

	        	resultVO.setIp(request.getRemoteAddr());
	        	// 2-1. 로그인 정보를 세션에 저장
	        	session.setAttribute("loginVO", resultVO);

//	        	return "forward:/main.do";
	        } else {
	        	//로그인 오류
	        	return "forward:/loginform.do?loginError=error";
	        }
		} else {
//			return "/login";
		}

		usergMenuMapService.setLangDcdMenu(langDcd);
		
		return mainForward();
	}

	@RequestMapping("ssologinexec.do")
	public String goSsoLoginExec() {
		log.debug("soologinexe start");
		return "/commons/sso/login_exec";
	}

	@RequestMapping("/loginform.do")
	public String goLoginForm(HttpSession session, ModelMap model, String loginError) throws Exception {
		
		String sLangDcd = UtilString.null2Blank(langDcd); 
		
		log.debug("LOCALE_SESSION_ATTRIBUTE_NAME : {}", langDcd);
		
		if("en".equals(langDcd)) {
		
			session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.ENGLISH);
		} else if ("kr".equals(langDcd)) {
			session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.KOREAN);
			
		} else {
			session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.getDefault());
			
		}
		   

		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(WiseConfig.KEY_SIZE);

        KeyPair keyPair = generator.genKeyPair();
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // 세션에 공개키의 문자열을 키로하여 개인키를 저장한다.
        session.setAttribute("__rsaPrivateKey__", privateKey);

        // 공개키를 문자열로 변환하여 JavaScript RSA 라이브러리 넘겨준다.
        RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);

        String publicKeyModulus = publicSpec.getModulus().toString(16);
        String publicKeyExponent = publicSpec.getPublicExponent().toString(16);

        model.addAttribute("publicKeyModulus", publicKeyModulus);
        model.addAttribute("publicKeyExponent", publicKeyExponent);
        
        //로그인 에러시 에러메세지 호출
        log.debug("{}", loginError);
        if(loginError != null && loginError.equals("error")){
        	model.addAttribute("loginError", "error");
        }
        
        String license = readLicense();
		String realLicense = License.getLicense();
		
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat ( "yyyy-MM-dd");
				
		Date time = new Date();
				
		String time1 = format1.format(time);
		time = format1.parse(time1);
		Date time2 = format2.parse("2022-12-31");
		
		if(!license.equals(realLicense) && !license.equals("8c6976e5b541415bde98bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918")
				&& (time.compareTo(time2) > 0)) {
			model.addAttribute("licenseError", "error");
		}

		return "/login";
	}
	
	@RequestMapping("/ssologin.do")
	public String checkSsoLogin(HttpSession session, HttpServletRequest request,  @ModelAttribute("loginVO") LoginVO loginVO) throws Exception {
		String SSOID = (String) session.getAttribute("SSO_ID");
		log.debug("SSOID:{}", SSOID);


	/*	if(UserDetailHelper.isAuthenticated()) {
			return "redirect:/";
		}*/
		// 2. 메인화면 입력값 유무체크(로그인 화면에서 호출되는지 체크)
		if(SSOID != null && !"".equals(SSOID)) {

			//RSA 복호화 기능 추가
//			PrivateKey privateKey = (PrivateKey) session.getAttribute("_RSA_WEB_Key_");
//			session.removeAttribute("_RSA_WEB_Key_"); // 키의 재사용을 막는다. 항상 새로운 키를 받도록 강제.
//			loginVO.setId(decryptRsa(privateKey, loginVO.getSecuredUsername()));
//			loginVO.setPassword(decryptRsa(privateKey, loginVO.getSecuredPassword()));
//			loginVO.setId(decodeBase64(loginVO.getSecuredUsername()));
//			loginVO.setPassword(decodeBase64(loginVO.getSecuredPassword()));

//			log.debug("userid[{}], pass[{}]", loginVO.getId(), loginVO.getPassword());
//			LoginVO loginVO = new LoginVO();
			loginVO.setId(SSOID);
			//로그인 화면에서 호출된 경우
			LoginVO resultVO = loginService.actionSsoLogin(loginVO);
			if (resultVO != null && resultVO.getId() != null && !resultVO.getId().equals("")) {

				// 2-1. 로그인 정보를 세션에 저장
				session.setAttribute("loginVO", resultVO);

//	        	return "forward:/main.do";
			} else {
				//로그인 오류
//	        	return "/login";
			}
		} else {
//			return "/login";
		}

		return "redirect:/";
	}



	public String decodeBase64(String input) throws Exception, IOException {
		 BASE64Decoder decoder = new BASE64Decoder();

//		 String tmpStr = new StringBuffer(input).toString();

		 return new String(decoder.decodeBuffer(input), "UTF-8");

		 /*String decUid, decPwd;

		 byte[] b1, CookieString1, CookieString2;
		 String struid = request.getParameter("userId");
		 String strpasswd = request.getParameter("passwd");
		 decUid =new StringBuffer(struid).toString();
		 decPwd = new StringBuffer(strpasswd).toString();
		 CookieString1 = decoder.decodeBuffer(decUid);
		 CookieString2 = decoder.decodeBuffer(decPwd);
		 uid = new String(CookieString1, "UTF-8");
		 passwd = new String(CookieString2, "UTF-8");
*/
	}


    private String decryptRsa(PrivateKey privateKey, String securedValue) throws Exception {
        log.debug("will decrypt : {}" , securedValue);
        Cipher cipher = Cipher.getInstance("RSA");
        byte[] encryptedBytes = hexToByteArray(securedValue);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String decryptedValue = new String(decryptedBytes, "utf-8"); // 문자 인코딩 주의.
        return decryptedValue;
    }

    /**
     * 16진 문자열을 byte 배열로 변환한다.
     */
    public static byte[] hexToByteArray(String hex) {
        if (hex == null || hex.length() % 2 != 0) {
            return new byte[]{};
        }

        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            byte value = (byte)Integer.parseInt(hex.substring(i, i + 2), 16);
            bytes[(int) Math.floor(i / 2)] = value;
        }
        return bytes;
    }
    /**
     * BigInteger를 사용해 hex를 byte[] 로 바꿀 경우 음수 영역의 값을 제대로 변환하지 못하는 문제가 있다.
     */
    @Deprecated
    public static byte[] hexToByteArrayBI(String hexString) {
        return new BigInteger(hexString, 16).toByteArray();
    }

        public static String base64Encode(byte[] data) throws Exception {
        BASE64Encoder encoder = new BASE64Encoder();
        String encoded = encoder.encode(data);
        return encoded;
    }

    public static byte[] base64Decode(String encryptedData) throws Exception {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] decoded = decoder.decodeBuffer(encryptedData);
        return decoded;
    }

	@RequestMapping("/main.do")
	public ModelAndView goMain(HttpSession session, HttpServletRequest request, @ModelAttribute("loginVO") LoginVO loginVO) throws Exception {
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		log.debug("user = {}",user);
    	ModelAndView mv = new ModelAndView();

/*    	// 1. 로그인 세션 체크
		if(user == null) {
			//세션 미존재시
			log.debug("loginVO = {}",user);
			// 2. 메인화면 입력값 유무체크(로그인 화면에서 호출되는지 체크)
			if(loginVO != null) {
				//로그인 화면에서 호출된 경우
				LoginVO resultVO = loginService.actionLogin(loginVO);
		        if (resultVO != null && resultVO.getId() != null && !resultVO.getId().equals("")) {

		        	// 2-1. 로그인 정보를 세션에 저장
		        	request.getSession().setAttribute("loginVO", resultVO);

					BoardVO vo = new BoardVO();
					vo.setBbsId("BBSMSTR_000000000002");

					vo.setFirstIndex(1);
					vo.setRecordCountPerPage(5);

					//Map<String, Object> map = bbsMngService.selectBoardArticles(boardVO, vo.getBbsAttrbCode());
					Map<String, Object> map = bbsMngService.selectBoardArticles(vo, "");//2011.09.07

					Map<String,Object> param = new HashMap<String,Object>();

		    		param.put("firstIndex", "1");
		    		param.put("recordCountPerPage", "5");
		    		param.put("userId", resultVO.getId());

		    		List<WaqReqMst> reqResult = reqJobService.searchMyRegReq(param);
		    		List<VWaaAprvPrcs> aprvResult = apprReqService.apprReqList(param);
		    		//건수가 모자른 경우 처리
		    		for(int i=reqResult.size(); i<5;i++) {
		      			WaqReqMst waqReqMst = new WaqReqMst();
		      			waqReqMst.setBizNm("&nbsp");
		    			reqResult.add(waqReqMst);
		    		}
		    		for(int i=aprvResult.size(); i<5;i++) {
		    			VWaaAprvPrcs vWaaAprvPrcs = new VWaaAprvPrcs();
		    			vWaaAprvPrcs.setBizNm("&nbsp");
		    			aprvResult.add(vWaaAprvPrcs);
		    		}
		        	mv.addObject("bbsList", map.get("resultList"));
		        	mv.addObject("reqList", reqResult);
		        	mv.addObject("aprvList", aprvResult);
		    		mv.setViewName("/main");

		    		return mv;
		        } else {
		        	//로그인 정보가 틀린 경우
		        	//메세지 추가해도 괜찮을듯..
		    		mv.setViewName("/login");
		        }
			} else {
				//로그인 화면에서 호출되지 않은 경우(세션이 끊긴 경우)
	    		mv.setViewName("/login");
	        	return mv;
			}
		} else {*/
			//세션 존재시
			//게시글 리스트
			BoardVO vo = new BoardVO();
			vo.setBbsId("BBSMSTR_000000000007");

			vo.setFirstIndex(0);
			vo.setRecordCountPerPage(6);

			//Map<String, Object> map = bbsMngService.selectBoardArticles(boardVO, vo.getBbsAttrbCode());
			Map<String, Object> map = bbsMngService.selectBoardArticles(vo, "");//2011.09.07

			Map<String,Object> param = new HashMap<String,Object>();

    		param.put("firstIndex", 0);
    		param.put("recordCountPerPage", 6);
    		param.put("userId", user.getId());


    		WaqMstr record = new WaqMstr() ;
    		record.setRqstStepCd("Q");
    		List<WaqMstr> rqstMyList = requestMstService.getRqstMyListForMain(record);
    		for(int i=rqstMyList.size();i<5;i++) {
    			WaqMstr waqMstr = new WaqMstr();
    			waqMstr.setRqstNm("&nbsp");
    			rqstMyList.add(waqMstr);
    		}

    		List<WaqMstr> rqstToDoList = requestMstService.getRqstToDoListForMain(record);
    		for(int i=rqstToDoList.size();i<5;i++) {
    			WaqMstr waqMstr = new WaqMstr();
    			waqMstr.setRqstNm("&nbsp");
    			rqstToDoList.add(waqMstr);
    		}
        	mv.addObject("bbsList", map.get("resultList"));
        	mv.addObject("reqList", rqstMyList);
        	mv.addObject("aprvList", rqstToDoList);

        	/* 표준데이터 조회 */
    		//List<TotalCountVO> stndresult =  totalDashService.selectTotCntWAMs(userid);
    		 /* 데이터모델 조회 */
    		//List<UpdateCntVO> modelresult = totalDashService.selectUpdateCntStat(userid);

    		//mv.addObject("stndresult", stndresult);
    		//mv.addObject("modelresult", modelresult);


        	//모델 vs DB 일치율
//        	DbcAllErrChartVO chartVO = totalDashService.selectErrChart();
//
//        	StringBuffer errChartSb = new StringBuffer();
//        	errChartSb.append("<chart caption='모델 vs DB 일치율'>");
//        	errChartSb.append("<set name='오류건수' value='" + (chartVO.getTotal() - chartVO.getNoErr()) + "'/>");
//        	errChartSb.append("<set name='정상건수' value='" + chartVO.getNoErr() + "'/>");
//        	errChartSb.append("</chart>");
//
//
//        	mv.addObject("errChartSb", errChartSb);
    		mv.setViewName("/damain");

    		return mv;
//		}

//    	return mv;

/*		//TODO 로그인아이디, 비밀번호 체크
		if(!"".equals(loginId) && !"".equals(loginPwd)) {
			String userId = session.getId(); // userId 는 추후 loginId 를 이용하여 DB에서 사용자 정보 읽어옴.
			SessionListener sessionListener = new SessionListener();
			sessionListener.setUserId(userId);
			session.setAttribute("SESSION_LISTENER", sessionListener);

			//TODO 로그인유저정보 세션 등록

			// 사용자의 메뉴정보 세션 등록
			// session.setAttribute("MENUS", menuService.getMenusByGroupId(""));

			session.setAttribute("ssUsrId", loginId);
			session.setAttribute("ssLoginId", "test");

			return "redirect:/main";
		} else {
			return "redirect:/";
		}*/

	}
	@RequestMapping("/dqmain.do")
	public ModelAndView goDqMain() throws Exception {
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		log.debug("user = {}",user);

		ModelAndView mv = new ModelAndView();


/*		BoardVO vo = new BoardVO();
		vo.setBbsId("BBSMSTR_000000000007");

		vo.setFirstIndex(0);
		vo.setRecordCountPerPage(6);

		//Map<String, Object> map = bbsMngService.selectBoardArticles(boardVO, vo.getBbsAttrbCode());
		Map<String, Object> map = bbsMngService.selectBoardArticles(vo, "");//2011.09.07

		Map<String,Object> param = new HashMap<String,Object>();

		param.put("firstIndex", 0);
		param.put("recordCountPerPage", 6);
		param.put("userId", user.getId());


		//내 요청정보 조회
		WaqMstr record = new WaqMstr() ;
		record.setRqstStepCd("Q");
		List<WaqMstr> rqstMyList = requestMstService.getRqstMyListForMain(record);
		for(int i=rqstMyList.size();i<5;i++) {
			WaqMstr waqMstr = new WaqMstr();
			waqMstr.setRqstNm("&nbsp");
			rqstMyList.add(waqMstr);
		}

		//내 결재정보 조회
		List<WaqMstr> rqstToDoList = requestMstService.getRqstToDoListForMain(record);
		for(int i=rqstToDoList.size();i<5;i++) {
			WaqMstr waqMstr = new WaqMstr();
			waqMstr.setRqstNm("&nbsp");
			rqstToDoList.add(waqMstr);
		}
    	mv.addObject("bbsList", map.get("resultList"));
    	mv.addObject("reqList", rqstMyList);
    	mv.addObject("aprvList", rqstToDoList);



    	//기준정보 현황 조회
		List<TotalCountVO> criresult =  dqdashService.getTotCntDqCri();

		 업무영역별 데이터품질 개선활동 진행현황  
		List<DqdashSystemVO> bizareaImpvResult = dqdashService.getBizAareaImpvList();


		 데이터모델 조회 
		//List<UpdateCntVO> modelresult = totalDashService.selectUpdateCntStat(userid);
		//mv.addObject("modelresult", modelresult);

		mv.addObject("criresult", criresult);
		mv.addObject("bizareaImpvResult", bizareaImpvResult);*/

		mv.setViewName("/dqmain");
		
		//보유현황
    	List<TotalCountVO> mtaTotalCnt = totalDashService.selectDbmsCnt();
    	mv.addObject("mtaTotalCntList", mtaTotalCnt);
    	
    	//프로파일
//    	List<TotalCountVO> prfTotalCnt = totalDashService.selectPrfCnt();
    	WaaDbConnTrgVO vo = new WaaDbConnTrgVO();
    	List<DqMainChartVO> prfTotalCnt = totalDashService.selectPrfCntByDbms(vo);
    	mv.addObject("prfTotalCnt", prfTotalCnt);
    	
    	String json = new ObjectMapper().writeValueAsString(prfTotalCnt);//prfTotalCnt를 json으로 바꿈
    	mv.addObject("result", json );
		
    	List<TotalCountVO> chartData = totalDashService.selectChartCnt();
		mv.addObject("chartCnt",chartData);

		return mv;

	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		//usergMenuMapService.setLangDcdMenu(langDcd);
		
		if(session != null) {
			session.invalidate();
		}
		
		return "redirect:/";
	}

	@RequestMapping(value={"/leftMenu.do", "*/leftMenu.do", "*/*/leftMenu.do"})
	public ModelAndView leftMenu() {
		ModelAndView mv = new ModelAndView("/leftMenu");

		return mv;
	}

	@RequestMapping(value={"/gen_cont.do"})
	public String goGenCont() {

		return "/gen_cont";
	}

	@RequestMapping("/rqstCount.do")
	@ResponseBody
	public List getRqstCountJSON(@ModelAttribute("loginVO") LoginVO loginVO) throws Exception{
		//등록요청건수, 결재대상건수 조회...
//    	int rqstMyCount = requestMstService.getRqstMyListCount(new WaqMstr());
    	WaqMstr waqMstr = requestMstService.getRqstCount(new WaqMstr());
    	int rqstToDoCount = requestMstService.getRqstToDoListCount(new WaqMstr());
		ArrayList list = new ArrayList();
		list.add(0, waqMstr.getRqstTmpCount() );
		list.add(1, waqMstr.getRqstMyCount());
		list.add(2, rqstToDoCount);
		System.out.println(list);
		return list;
	}


	/** 요청목록, 결재대상 더블클릭시 해당 링크페이지 이동 메서드...*/
	/** @param waqmst
	/** @return meta */
	@RequestMapping("/goRqstPage.do")
	public String goRqstPage(WaqMstr reqmst) {
		log.debug("마스터정보 : {}", reqmst);

		WaaBizInfo bizInfo = requestMstService.getBizInfo(reqmst);

		log.debug("bizInfo : {}",bizInfo);
		String url = bizInfo.getUrl() + "?rqstNo=" + reqmst.getRqstNo() + "&bizDcd=" + bizInfo.getBizDcd() + "&bizDtlCd=" + bizInfo.getBizDtlCd();
		return "redirect:" + url;
	}
	
	@RequestMapping("/changePassword.do")
	public String goChangePassword(WaaUser user) {
		log.debug("유저정보 : {}", user);
		
		return "/changePassword";
	}
	
	@RequestMapping("/goChangeLicense.do")
	public String goChangeLicense(Model model) {
		String macAddr = License.getMacAddr();
		model.addAttribute("macAddr", macAddr);
		return "/commons/user/popup/changeLicense";
	}
	
	@RequestMapping("/changeLicense.do")
	@ResponseBody
	public String changeLicense(String licenseKey) throws Exception{
		String fileName = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.LICENSE_PATH, null, Locale.getDefault()) + "/dqlite_license" ;
		String resMsg = "";
        
        try{
        	String path = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.LICENSE_PATH, null, Locale.getDefault()); //폴더 경로
        	File Folder = new File(path);

        	// 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
        	if (!Folder.exists()) {
        		try{
        		    Folder.mkdir(); //폴더 생성합니다.
        		    log.debug("폴더가 생성되었습니다.");
        	        } 
        	        catch(Exception e){
        		    e.getStackTrace();
        	        }        
                 }else {
                	 log.debug("이미 폴더가 생성되어 있습니다.");
        	}
        	
            // BufferedWriter 와 FileWriter를 조합하여 사용 (속도 향상)
            BufferedWriter fw = new BufferedWriter(new FileWriter(fileName, false));
             
            // 파일안에 문자열 쓰기
            fw.write(licenseKey);
            fw.flush();
 
            // 객체 닫기
            fw.close();
            
            resMsg = "save";
        }catch(Exception e){
            e.printStackTrace();
            resMsg = "error";
            //return "forward:/goChangeLicense.do";
        }
		
        //model.addAttribute("resMsg", resMsg);
        
		return resMsg; //"forward:/loginForm.do?loginError=save";
	}
	
	@RequestMapping("/setlang.do")
	public String setLang(HttpSession session, Model model, String pLangDcd) throws Exception {
			
		log.debug("pLangDcd :: ", pLangDcd);
		log.debug("langDcd :: ", langDcd);
		
		if("en".equals(pLangDcd)) {
			session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.ENGLISH);
		} else if ("kr".equals(pLangDcd)) {
			session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.KOREAN);
			
		} else {
			session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.getDefault());
			
		}
		
		usergMenuMapService.setLangDcdMenu(pLangDcd);
		//일반 로그인일 경우
		return mainForward();
	}

	@RequestMapping("/userRegPop.do")		
	public String userRegPop(HttpSession session){
		log.debug("Clicked Reg");
		
//		List<CodeListVo> comCd = cmcdCodeService.getCodeList("COM_CD");
//		log.debug("comCd >>> " + comCd);

		session.setAttribute("comCd", cmcdCodeService.getCodeList("COM_CD"));
		
		return "/commons/user/popup/userRegPop";		
	}
	
	//팝업창에서 가입버튼 클릭시
	@ResponseBody
	@RequestMapping("/join.do")
	public int registered(WaaUser user) throws Exception{
		int result=0;
		log.debug("join");

//		String url ="http://dq.wise.co.kr/verify.do?user_id="+RSA.base64Encode((user.getUserId().getBytes()))+
//				"&user_email="+RSA.base64Encode((user.getEmailAddr().getBytes()));

		String url ="http://169.56.81.9:38080/wisead/verify.do?user_id="+RSA.base64Encode((user.getUserId().getBytes()))+
				"&user_email="+RSA.base64Encode((user.getEmailAddr().getBytes()));

		
		//메일 인증
		MailHandler sendMail = new MailHandler();
		sendMail.setSubject("[WiseDQ 회원가입 이메일 인증]");
		sendMail.setText(new StringBuffer().append("<h3>메일 인증</h3>")
				.append("<a href='"+url)
				.append("'target='_blenk'>이메일 인증 확인</a>").toString());
		sendMail.setFrom("dq@wise.co.kr", "wise");
		sendMail.setTo(user.getEmailAddr());
		result = sendMail.send();//전송실패하면 0 return
		log.debug("mail send: "+result);
		
		//DB에 user등록
		result +=userService.register(user);

		log.debug("result: "+result);
		if(result > 1) 
			result = 1;
		else
			result = 0;
		
		return result;
	}
	
	//인증메일에서 인증확인 버튼 클릭시
	@RequestMapping(value="/verify.do")
	public String verifyEmail(@RequestParam("user_id") String user_id,@RequestParam("user_email") String user_email) throws Exception{

		String userId = new String(RSA.base64Decode(user_id));
		String userEmail = new String(RSA.base64Decode(user_email));

		log.debug(userId+", "+userEmail);

		userService.updateVerify(userId);
		
//		return "redirect:/loginform.do";
		return "/login_pop";
	}
	
	//id중복체크
	//기존방식과 다름
	@ResponseBody
	@RequestMapping(value = "/idCheck.do")
	public int postIdCheck(HttpServletRequest req) throws Exception{

		log.info("post idCheck");

		String userId =req.getParameter("userId");		
		int idCheck =userService.idCheck(userId);

		log.info("idCheck >>> " + idCheck);
		int result=0;		

		if(idCheck>0) {
			result = 1;
		}

		log.debug("" + result);
		return result; 
	}
}
