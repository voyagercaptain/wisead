/**
 * 0. Project  : WISE DA ������Ʈ
 *
 * 1. FileName : QuartzJob.java
 * 2. Package : kr.wise.commons.schedule.job
 * 3. Comment :
 * 4. �ۼ���  : insomnia
 * 5. �ۼ���  : 2014. 6. 16. ���� 4:55:30
 * 6. �����̷� :
 *                    �̸�     : ����          : �ٰ��ڷ�   : ���泻��
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 6. 16. :            : �ű� ����.
 */
package kr.wise.commons.schedule.job;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import kr.wise.commons.WiseConfig;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Controller;

import kr.wise.commons.ApplicationContextProvider;
import kr.wise.commons.error.ErrorCode;
import kr.wise.commons.schedule.task.TotalSearchTask;
import kr.wise.commons.util.ValidationCheck;
import kr.wise.dq.dbstnd.service.DbStndService;
import kr.wise.dq.dbstnd.service.WamDbDmn;
import kr.wise.dq.dbstnd.service.WamDbSditm;
import kr.wise.dq.dbstnd.service.WamDbStcd;
import kr.wise.dq.dbstnd.service.WamDbStwd;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : QuartzJob.java
 * 3. Package  : kr.wise.commons.schedule.job
 * 4. Comment  : DB 표준 검증 스케줄러
 * </PRE>
 */
@Controller
public class QuartzJob extends QuartzJobBean {

	Logger logger = LoggerFactory.getLogger(getClass());

	private TotalSearchTask totSearchTask;
	
	private final DbStndService dbStndService;
	
	public QuartzJob() {
		ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();

		if( applicationContext == null ) {
			throw new NullPointerException("Spring의 ApplicationContext초기화 안됨");
		}
		
		dbStndService = (DbStndService)applicationContext.getBean("dbStndService");
	}
	/**
	 * @param totSearchTask the totSearchTask to set
	 */
	public void setTotSearchTask(TotalSearchTask totSearchTask) {
		this.totSearchTask = totSearchTask;
	}

	private String getLocalServerIp() {

		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();

				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		}
		catch (SocketException ex) {}
		return null;
	}

	protected void executeInternal_ori(JobExecutionContext context)
			throws JobExecutionException {

		String serverIp = getLocalServerIp();

		logger.info("LOG_TRACE BATCH SERVER IP {}", serverIp);
		if (!serverIp.equals(WiseConfig.BATCH_SERVER_IP)) {
			logger.info("LOG_TRACE BATCH SERVER IP NOT MATCH");
			return;
		}

		logger.info("LOG_TRACE DB표준관리 검증시작!!!");
		List<WamDbSditm> dbSditmList = dbStndService.selectDbSditmList(); //DB표준용어
		List<WamDbDmn>   dbDmnList   = dbStndService.selectDbDmnList();   //DB표준도메인
		List<WamDbStwd>  dbStwdList  = dbStndService.selectDbStwdList();  //DB표준단어
		List<WamDbStcd>  dbStcdList  = dbStndService.selectDbStcdList();  //DB표준코드
		
		//검증결과 업데이트 
		try {
			dbSditmList = itemValidCheck(dbSditmList);  //DB표준용어 검증
			dbDmnList   = dmnValidCheck(dbDmnList);	   //DB표준도메인 검증
			dbStwdList  = stwdValidCheck(dbStwdList);  //DB표준단어 검증
			dbStcdList  = stcdValidCheck(dbStcdList);  //DB표준코드 검증
			dbStndService.updateDbStndTotInspect(dbSditmList,dbDmnList,dbStwdList,dbStcdList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {

		String serverIp = getLocalServerIp();

		logger.info("LOG_TRACE BATCH SERVER IP {}", serverIp);
		/*
		if (!serverIp.equals(WiseConfig.BATCH_SERVER_IP)) {
			logger.info("LOG_TRACE BATCH SERVER IP NOT MATCH");
			return;
		}
		*/

		long startRunTime = System.currentTimeMillis();

		logger.info("LOG_TRACE DB표준관리 검증시작!!!");

		while (true) {
			List<WamDbSditm> dbSditmList = dbStndService.selectDbSditmList(); //DB표준용어
			List<WamDbDmn>   dbDmnList   = dbStndService.selectDbDmnList();   //DB표준도메인
			List<WamDbStwd>  dbStwdList  = dbStndService.selectDbStwdList();  //DB표준단어
			List<WamDbStcd>  dbStcdList  = dbStndService.selectDbStcdList();  //DB표준코드

			logger.info("폴링 검증 건 수 - 용어:" + dbSditmList.size() + "건, 도메인:" + dbDmnList.size() + "건, 단어:" +  dbStwdList.size() + "건, 코드:" + dbStcdList.size() + "건");

			if(dbSditmList.size() == 0 && dbDmnList.size() == 0 &&  dbStwdList.size() == 0 && dbStcdList.size() == 0) {
				logger.info("검증배치 모두 완료!!");
				break;
			}

			//검증결과 업데이트
			try {
				dbSditmList = itemValidCheck(dbSditmList);  //DB표준용어 검증
				dbDmnList   = dmnValidCheck(dbDmnList);	   //DB표준도메인 검증
				dbStwdList  = stwdValidCheck(dbStwdList);  //DB표준단어 검증
				dbStcdList  = stcdValidCheck(dbStcdList);  //DB표준코드 검증
				dbStndService.updateDbStndTotInspect(dbSditmList,dbDmnList,dbStwdList,dbStcdList);
			} catch (Exception e) {
				e.printStackTrace();
			}

			long endRunTime = System.currentTimeMillis();
			double diffRunTime = (endRunTime - startRunTime) * 0.001;
			if(diffRunTime > 60 * 60 * 6) {
				logger.info("6시간 초과 검증배치 작업 중단!!");
				break;
			}
		}

		logger.info("LOG_TRACE DB표준관리 검증 종료!!!");

	}
	
	 //표준용어 유효성 검사 체크
    public List<WamDbSditm> itemValidCheck(List<WamDbSditm> reglist) throws ParseException {
    	Map<String, String> params = new HashMap<String, String>();
		String errorMsg = "";
		for (WamDbSditm saveVo : reglist) {
			List<String> errorList = new ArrayList<>();
			// 표준용어 검증
			errorMsg = ValidationCheck.checkSditmName(saveVo.getSditmLnm());
			if(errorMsg != "") {
				errorList.add(errorMsg);
			}
			// 표준용어 영문명 검증
			errorMsg = ValidationCheck.checkSditmEng(saveVo.getPnm());
			if(errorMsg != "") {
				errorList.add(errorMsg);
			}
			// 표준 용어 영문 약어명 검증
			errorMsg = ValidationCheck.checkSditmInit(saveVo.getSditmPnm());
			if(errorMsg != "") {
				errorList.add(errorMsg);
			}
			// 표준 용어 설명 검증
			errorMsg = ValidationCheck.checkSditmDesc(saveVo.getObjDescn(),saveVo.getSditmLnm());
			if(errorMsg != "") {
				errorList.add(errorMsg);
			}
			// 표준 용어 제정일자 검증
			Date date=new SimpleDateFormat("yyyyMMdd").parse(saveVo.getSditmDtm());  
			saveVo.setRqstDtm(date);
			errorMsg = ValidationCheck.checkSditmDate(saveVo.getRqstDtm());
			if(errorMsg != "") {
				errorList.add(errorMsg);
			}
			
			//표준 도메인 검증
			String orgNm     = saveVo.getOrgNm();
			String domainNm  = saveVo.getInfotpLnm();
	    	errorMsg         = "";

	    	if(!"".equals(orgNm) && !"".equals(domainNm)) {
	    		
		    	params.put("domainNm", domainNm);
		    	params.put("orgNm", orgNm);
		    	Map<String, String> domainResult = dbStndService.selectDbDomainDataType(params);
				
		    	if(domainResult != null) {
		    		saveVo.setDataType(domainResult.get("DATA_TYPE"));
		    		saveVo.setDataLen(Integer.parseInt(String.valueOf(domainResult.get("DATA_LEN"))));
		    	}else {
		    		errorMsg = ErrorCode.ERROR_DMN_TYPE_LENGTH_ERROR.getMessage();
		    	}
	    	}
	    	if(errorMsg != "") {
				errorList.add(errorMsg);
			}
	    	
			//DB표준용어 중복 확인 기관명+표준용어명+영문약어명+표준도메인명이 중복이 되면 안된다.
			params = new HashMap<String, String>();
			params.put("orgNm", saveVo.getOrgNm()); 	    // 기관명
			params.put("dbNm", saveVo.getDbNm()); 	    	//DB명
			params.put("sditmLnm", saveVo.getSditmLnm());   //표준용어명
			int count = dbStndService.dupliCheckDbStndItem(params);
			if(count > 0) {
				errorMsg = ErrorCode.ERROR_ITEM_DUP.getMessage();
			}else {
				errorMsg = "";
			}

			if(errorMsg != "") {
				errorList.add(errorMsg);
			}
	    	
	    	if(errorList.size() > 0) {
	    		saveVo.setValidYn("E");
	    		saveVo.setErrChk(errorList.stream()
						.collect(Collectors.joining (",")));
	    	}else {
	    		saveVo.setValidYn("Y");
	    		saveVo.setErrChk(errorMsg);
	    		
	    	}
    	}
		return reglist;
    }
    
    
    
    //표준도메인 유효성 검사 체크
    public List<WamDbDmn> dmnValidCheck(List<WamDbDmn> reglist) throws ParseException {
    	Map<String, String> params = new HashMap<String, String>();
    	String[] dataTypeArr = {"boolean", "date", "time", "timestamp", "datetime", "interval", "datetimeltz", "datetimetz", "timestampltz", "timestamptz", "number", "numeric", "decimal", "smalldatetime", "money", "smallmoney", "long", "bigint", "smallint", "short", "tinyint", "bit", "int", "integer", "double", "double precision", "text", "ntext", "nchar", "nvarchar", "ntext", "binary", "varbinary", "binary_float", "binary_double", "varbinary", "image", "real", "clob", "blob", "nclob", "bfile"};
		String errorMsg = "";
    	for (WamDbDmn saveVo : reglist) {
    		List<String> errorList = new ArrayList<>();
    		// 표준도메인 검증
			errorMsg = ValidationCheck.checkDmnName(saveVo.getInfotpLnm());
			if(errorMsg != "") {
				errorList.add(errorMsg);
			}
			// 데이터 타입 검증
			errorMsg = ValidationCheck.checkDmnType(saveVo.getDataType());
			if(errorMsg != "") {
				errorList.add(errorMsg);
			}
			// 데이터 길이 거증
			//errorMsg = ValidationCheck.checkDmnLength(String.valueOf(saveVo.getDataLen()));
			//dataLen 필수체크(데이터 타입에 따라 필수가 나뉜다)
			String  dataType = saveVo.getDataType();
			Integer dataLen  = saveVo.getDataLen();
			if(Arrays.asList(dataTypeArr).contains(dataType.toLowerCase()) == false && (dataLen == null || dataLen == 0)) {
				errorMsg = ErrorCode.ERROR_DMN_LENGTH_NOTNULL.getMessage();
			}
			if(errorMsg != "") {
				errorList.add(errorMsg);
			}
			
			// 표준 도메인 제정일자 검증
			Date date=new SimpleDateFormat("yyyyMMdd").parse(saveVo.getDmnDtm());  
			saveVo.setRqstDtm(date);
			errorMsg = ValidationCheck.checkDmnDate(saveVo.getRqstDtm());
			if(errorMsg != "") {
				errorList.add(errorMsg);
			}
			
				params = new HashMap<String, String>();
				params.put("orgNm", saveVo.getOrgNm()); 	   		        // 기관명
				params.put("dbNm", saveVo.getDbNm()); 	    				//DB명
				params.put("infotpLnm", saveVo.getInfotpLnm()); 	  	    // 도메인명
				int count = dbStndService.dupliCheckDbStndDmn(params);
				if(count > 0) {
					errorMsg = ErrorCode.ERROR_ITEM_DUP.getMessage();
				}else {
					errorMsg = "";
				}
	    	
				if(errorMsg != "") {
					errorList.add(errorMsg);
				}
	    	
	    	if(errorList.size() > 0) {
	    		saveVo.setValidYn("E");
	    		saveVo.setErrChk(errorList.stream().collect(Collectors.joining(",")));
	    	}else {
	    		saveVo.setValidYn("Y");
	    		saveVo.setErrChk(errorMsg);
	    	}
    	}
		return reglist;
    }

    
    
  //DB표준단어 유효성 검사 체크
    public List<WamDbStwd> stwdValidCheck(List<WamDbStwd> reglist) throws ParseException {
    	Map<String, String> params = new HashMap<String, String>();
		String errorMsg = "";
    	for (WamDbStwd saveVo : reglist) {
    		List<String> errorList = new ArrayList<>();
    		// 표준단어명 검증
			errorMsg = ValidationCheck.checkWordName(saveVo.getStwdLnm());
			if(errorMsg != "") {
				errorList.add(errorMsg);
			}
			// 표준단어 영문 약어명 검증
			errorMsg = ValidationCheck.checkWordEngName(saveVo.getStwdPnm());
			if(errorMsg != "") {
				errorList.add(errorMsg);
			}
			// 표준단어 형식 여부 검증
			errorMsg = ValidationCheck.checkWordFormat(saveVo.getDmnYn());
			if(errorMsg != "") {
				errorList.add(errorMsg);
			}
			
			// 표준 단어 제정일장 검증
			Date date=new SimpleDateFormat("yyyyMMdd").parse(saveVo.getStwdDtm());  
			saveVo.setRqstDtm(date);
			errorMsg = ValidationCheck.checkWordDate(saveVo.getRqstDtm());
			if(errorMsg != "") {
				errorList.add(errorMsg);
			}
	    	
	    	//DB표준 단어 중복 확인 기관명+표준단어명+단어영문약어명  중복이 되면 안된다.
				params = new HashMap<String, String>();
				params.put("orgNm", saveVo.getOrgNm()); 	// 기관명
				params.put("dbNm", saveVo.getDbNm()); 	    //DB명
				params.put("stwdLnm", saveVo.getStwdLnm()); // 표준단어명
				int count = dbStndService.dupliCheckDbStndStwd(params);
				if(count > 0) {
					errorMsg = ErrorCode.ERROR_ITEM_DUP.getMessage();
				}else {
					errorMsg = "";
				}
	    	
				if(errorMsg != "") {
					errorList.add(errorMsg);
				}
	    	
	    	if(errorList.size() > 0) {
	    		saveVo.setValidYn("E");
	    		saveVo.setErrChk(errorList.stream().collect(Collectors.joining(",")));
	    	}else {
	    		saveVo.setValidYn("Y");
	    		saveVo.setErrChk(errorMsg);
	    	}
    	}
		return reglist;
    }

    
    //DB표준코드 유효성 검사 체크
    public List<WamDbStcd> stcdValidCheck(List<WamDbStcd> reglist) throws ParseException {
    	Map<String, String> params = new HashMap<String, String>();
		String errorMsg = "";
    	for (WamDbStcd saveVo : reglist) {
    		List<String> errorList = new ArrayList<>();
    		// 코드명 검증
			errorMsg = ValidationCheck.checkCodeName(saveVo.getCommCdNm());
			if(errorMsg != "") {
				errorList.add(errorMsg);
			}
			// 코드 값 검증
			errorMsg = ValidationCheck.checkCodeValue(saveVo.getCommDtlCdNm());
			if(errorMsg != "") {
				errorList.add(errorMsg);
			}
			// 코드 값 의미 검증
			errorMsg = ValidationCheck.checkCodeValueMean(saveVo.getCommDtlCdMn());
			if(errorMsg != "") {
				errorList.add(errorMsg);
			}
			
			// 표준 코드 제정일자 검증
			Date date=new SimpleDateFormat("yyyyMMdd").parse(saveVo.getStcdDtm());  
			saveVo.setWritDtm(date);
			errorMsg = ValidationCheck.checkCodedDate(saveVo.getWritDtm());
			if(errorMsg != "") {
				errorList.add(errorMsg);
			}
	    	
	    	//DB표준 코드 중복 확인 기관명+표준단어명+단어영문약어명  중복이 되면 안된다.
			if("N".equals(saveVo.getConfirmYn()) || "".equals(saveVo.getConfirmYn())) {
				params = new HashMap<String, String>();
				params.put("orgNm", saveVo.getOrgNm()); 	  //기관명
				params.put("dbNm", saveVo.getDbNm()); 	      //DB명
				params.put("commCdNm", saveVo.getCommCdNm()); //코드명
				int count = dbStndService.dupliCheckDbStndStcd(params);
				if(count > 0) {
					errorMsg = ErrorCode.ERROR_ITEM_DUP.getMessage();
				}else {
					errorMsg = "";
				}
	    	
				if(errorMsg != "") {
					errorList.add(errorMsg);
				}
			}
	    	
	    	if(errorList.size() > 0) {
	    		saveVo.setValidYn("E");
	    		saveVo.setErrChk(errorList.stream().collect(Collectors.joining(",")));
	    	}else {
	    		saveVo.setValidYn("Y");
	    		saveVo.setErrChk(errorMsg);
	    	}
	    	
    	}
		return reglist;
    }

}
