package kr.wise.commons.schedule.job;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import kr.wise.dq.dbstnd.service.*;
import kr.wise.dq.dbstnd.web.DbStndTotRqstCtrl;
import kr.wise.dq.stnd.service.*;
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

	private final StndService stndService;

	private final StndItemRqstService stndItemRqstService;

	private final StndDmnRqstService stndDmnRqstService;

	private final StndWordRqstService stndWordRqstService;

	private final DbStndTotRqstCtrl dbStndTotRqstCtrl;
	
	public QuartzJob() {
		ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();

		if( applicationContext == null ) {
			throw new NullPointerException("Spring의 ApplicationContext초기화 안됨");
		}
		
		dbStndService = (DbStndService)applicationContext.getBean("dbStndService");
		stndService = (StndService)applicationContext.getBean("stndService");
		stndItemRqstService = (StndItemRqstService)applicationContext.getBean("stndItemRqstService");
		stndDmnRqstService  = (StndDmnRqstService)applicationContext.getBean("stndDmnRqstService");
		stndWordRqstService  = (StndWordRqstService)applicationContext.getBean("stndWordRqstService");
		dbStndTotRqstCtrl = (DbStndTotRqstCtrl)applicationContext.getBean("dbStndTotRqstCtrl");
	}
	/**
	 * @param totSearchTask the totSearchTask to set
	 */
	public void setTotSearchTask(TotalSearchTask totSearchTask) {
		this.totSearchTask = totSearchTask;
	}

	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {

		String batchServer = System.getProperty("batch.server");
		if (!"Y".equals(batchServer))
			return;
		orgStndInspectJob();
		dbStndInspectJob();
		orgDbSyncJob();
	}

	protected void orgDbSyncJob() {
		// 대상 기관 동기화
		stndService.regTargetOrg();
		// 대상 DB 동기화
		stndService.regTargetDb();
	}

	protected void dbStndInspectJob() {

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
				logger.error("LOG_TRACE DB표준관리 검증 중 에러!!!", e);
				break;
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


	protected void orgStndInspectJob() {

		long startRunTime = System.currentTimeMillis();

		logger.info("LOG_TRACE 기관 표준관리 검증시작!!!");

		while (true) {
			List<WamSditm> sditmList = stndService.selectBatchSditmList(); //표준용어
			List<WamDmn>   dmnList   = stndService.selectBatchDmnList();   //표준도메인
			List<WamStwd>  stwdList  = stndService.selectBatchStwdList();  //표준단어
			List<WamDbStcd>  stcdList  = stndService.selectBatchStcdList();  //표준코드

			logger.info("폴링 검증 건 수 - 용어:" + sditmList.size() + "건, 도메인:" + dmnList.size() + "건, 단어:" +  stwdList.size() + "건, 코드:" + stcdList.size() + "건");

			if(sditmList.size() == 0 && dmnList.size() == 0 &&  stwdList.size() == 0 && stcdList.size() == 0) {
				logger.info("검증배치 모두 완료!!");
				break;
			}

			//검증결과 업데이트
			try {
				stndItemRqstService.registerWamCheck(sditmList, null);  //표준용어 검증
				stndDmnRqstService.registerWamCheck(dmnList, null);	  //표준도메인 검증
				stndWordRqstService.registerWamCheck(stwdList, null);  //표준단어 검증
				dbStndTotRqstCtrl.stndStcdValidCheck((ArrayList<WamDbStcd>) stcdList, "");  //표준코드 검증
				stndService.updateStndTotInspect(sditmList, dmnList, stwdList, stcdList);
			} catch (Exception e) {
				logger.error("LOG_TRACE 기관 표준관리 검증 중 에러!!!", e);
				break;
			}

			long endRunTime = System.currentTimeMillis();
			double diffRunTime = (endRunTime - startRunTime) * 0.001;
			if(diffRunTime > 60 * 60 * 6) {
				logger.info("6시간 초과 검증배치 작업 중단!!");
				break;
			}
		}

		logger.info("LOG_TRACE 기관 표준관리 검증 종료!!!");

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
			errorMsg = ValidationCheck.checkSditmDate(saveVo.getSditmDtm());
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
					try {
						saveVo.setDataLen(Integer.parseInt(String.valueOf(domainResult.get("DATA_LEN"))));
					} catch (Exception e) {
						saveVo.setDataLen(null);
					}
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
			errorMsg = ValidationCheck.checkDmnDate(saveVo.getDmnDtm());
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
			errorMsg = ValidationCheck.checkWordDate(saveVo.getStwdDtm());
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
			errorMsg = ValidationCheck.checkCodedDate(saveVo.getStcdDtm());
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
