package kr.wise.commons.damgmt.db.service.impl;

/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : CmvwDbService.java
 * 2. Package : kr.wise.cmvw.db.service
 * 3. Comment :
 * 4. 작성자  : jwoolee(이정우)
 * 5. 작성일  : 2013. 5. 28.
 * 6. 변경이력 :
 *    이름		: 일자			: 변경내용
 *    ------------------------------------------------------
 *    jwoolee 	: 2013. 5. 28.	: 신규 개발.
 */

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.db.service.CmvwDbService;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgMapper;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.ConnectionHelper;
import kr.wise.commons.util.UtilObject;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.criinfo.anatrg.service.WaaDbSch;
import kr.wise.dq.criinfo.anatrg.service.WaaDbSchMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

//import seed.*;
import kr.wise.commons.WiseConfig;
/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : CmvwDbServiceImpl.java
 * 3. Package  : kr.wise.commons.damgmt.db.service.impl
 * 4. Comment  :
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 4. 27. 오후 4:36:01
 * </PRE>
 */
@Service("CmvwDbService")
public class CmvwDbServiceImpl implements CmvwDbService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WaaDbConnTrgMapper mapper;

	@Inject
	private WaaDbSchMapper schMapper;
	
	
	@Inject
	private EgovIdGnrService objectIdGnrService;


	public List<WaaDbConnTrgVO> getDbConnTrgList(WaaDbConnTrgVO search) {
		List<WaaDbConnTrgVO> list = mapper.selectList(search);

		return list;
	}

	public WaaDbConnTrgVO findDb(WaaDbConnTrgVO search) {

		return mapper.selectByPrimaryKey(search.getDbConnTrgId());
	}

	/** @param record
	/** @return meta
	 * @throws Exception */
	public int regDbConnTrg(WaaDbConnTrgVO record) throws Exception {
		String tmpStatus = record.getIbsStatus();
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;
        String encPw = "";
        String decPw="";

		//DB ID가 없을경우 신규로, 그렇지 않을경우 수정으로 처리
		if("I".equals(tmpStatus) && !StringUtils.hasText(record.getDbConnTrgId())) {  // 신규...
			record.setDbConnTrgId(objectIdGnrService.getNextStringId());
			record.setRegTypCd("C");
			record.setObjVers(1);
			//신규일 경우패스워드 암호화 20151228 이상익
			if(WiseConfig.SECURITY_APPLY.equals("Y")){
			//   encPw = seed.EncryptUtils.getEncData(record.getDbConnAcPwd(), record.getDbConnAcId());
			   logger.debug("암호화된 패스워드 : "+encPw);
			}
			//record.setDbConnAcPwd(encPw);
			
		} else if("U".equals(tmpStatus) || StringUtils.hasText(record.getDbConnTrgId())) {
//			//DB ID를 기반으로 데이터가 있는지 확인하여 없을경우 신규처리..
//			WaaDbConnTrgVO tmpVO = mapper.selectByPrimaryKey(record.getDbConnTrgId());
//			if (tmpVO == null || !tmpVO.getDbConnTrgId().equals(record.getDbConnTrgId())) {
//				record.setDbConnTrgId(objectIdGnrService.getNextStringId());
//				record.setObjVers(1);
//			} else {
//				if (UtilObject.isNull(record.getObjVers())) {
//					record.setObjVers(1);
//				}
//				else {
//					record.setObjVers(record.getObjVers()+1);
//				}
			if(WiseConfig.SECURITY_APPLY.equals("Y")){
				  //decPw = EncryptUtils.getDecData(record.getDbConnAcPwd(), record.getDbConnAcId());
				  if(decPw==null||decPw.equals("")){
			      //   record.setDbConnAcPwd(seed.EncryptUtils.getEncData(record.getDbConnAcPwd(), record.getDbConnAcId()));
				  }
			}
				record.setObjVers(record.getObjVers()+1);
				record.setRegTypCd("U");
				mapper.updateExpDtm(record);
//			}
		}

		record.setWritUserId(user.getUniqId());
		result = mapper.insertSelective(record);
		
		//신규 등록, 수정등이 이루어질 경우 테스트를 다시 해야하므로, 연결상태를 null로 초기화한다.
		record.setDbLnkSts(null);
		mapper.updateConnTest(record);
		return result;
	}

	public String chkDbConnTrg(WaaDbConnTrgVO record) {
		String result = "성공";
		try {
			Driver driver = (Driver)Class.forName(record.getConnTrgDrvrNm()).newInstance();

	        Properties prop = new Properties();
	        prop.put("user", record.getDbConnAcId());
	        prop.put("password", record.getDbConnAcPwd());
	        prop.put("encoding", "KSC5601");

	    	driver.connect(record.getConnTrgLnkUrl(), prop);

		} catch(Exception e) {
			result = e.getMessage();
		}
		return result;
	}


	/** meta
	 * @throws Exception */
	public int regDbConnTrgList(ArrayList<WaaDbConnTrgVO> list) throws Exception {
		int result = 0;
		for (WaaDbConnTrgVO waaDbConnTrg : list) {
			result += regDbConnTrg(waaDbConnTrg);
		}
		return result;
	}


	/**
	 * <PRE>
	 * 1. MethodName : delDbList
	 * 2. Comment    : 체크 상태인 리스트를 삭제상태로 변경 후 저장
	 * 3. 작성자       : jwoolee(이정우)
	 * 4. 작성일       : 2013. 5. 28.
	 * </PRE>
	 *   @return void
	 *   @param list
	 * @throws Exception
	 */
	public int delDbConnTrgList(ArrayList<WaaDbConnTrgVO> list) throws Exception {
		int result = 0;
		
		for (WaaDbConnTrgVO waaDbConnTrg : list) {
			
			String id = waaDbConnTrg.getDbConnTrgId();
			
			List<WaaDbSch> lstSch = schMapper.selectListByFK(id);
			
			for(WaaDbSch schVo : lstSch){
				
				//스키마 삭제후 DBMS삭제 가능합니다.
				//return -2;
				schMapper.updateExpDtm(schVo.getDbSchId());
			} 
			
			if (id != null && !"".equals(id)) {

				result += mapper.updateExpDtm(waaDbConnTrg);
			}
		}
		
		return result;
	}

	/**
	 * <PRE>
	 * 1. MethodName : delDbList
	 * 2. Comment    : 체크 상태인 리스트를 삭제상태로 변경 후 저장
	 * 3. 작성자       : jwoolee(이정우)
	 * 4. 작성일       : 2013. 5. 28.
	 * </PRE>
	 *   @return void
	 *   @param list
	 * @throws Exception
	 */
	public void chkDbConnTrgList(ArrayList<WaaDbConnTrgVO> list) throws Exception {
		for (WaaDbConnTrgVO waaDbConnTrg : list) {
			String id = waaDbConnTrg.getDbConnTrgId();
			if (id != null && !"".equals(id)) {
				waaDbConnTrg.setIbsStatus("U");
				waaDbConnTrg.setDbLnkSts(chkDbConnTrg(waaDbConnTrg));
				regDbConnTrg(waaDbConnTrg);
			}
		}

	}

	@Override
	public List<WaaDbSch> getDbSchList(String dbConnTrgId) {
		return schMapper.selectListByFK(dbConnTrgId);
	}

	/** meta
	 * @throws Exception */
	@Override
	public int regDbSchList(ArrayList<WaaDbSch> list) throws Exception {
		int result = 0;

		for (WaaDbSch record : list) {

			// 그리드 상태가 있을 경우만 DB에 처리한다...
			if (!UtilString.isBlank(record.getIbsStatus())) {
				result += regDbSch(record);
			}

		}

		return result;
	}

	@Override
	public int regDbSch(WaaDbSch record) throws Exception {
		logger.debug("regDbSch method");
		boolean isNew = true;
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;
		//엑셀업로드시 작성한 스키마 Id에 대한 검증 -- 없으면 신규처리, 있으면 변경처리
		if(record.getDbSchId() != null && !record.getDbSchId().equals("")) {
			WaaDbSch tmpDbSch = schMapper.selectByPrimaryKey(record.getDbSchId());

			if(null == tmpDbSch || !tmpDbSch.getDbSchId().equals(record.getDbSchId())) {
				isNew = true;
				record.setDbSchId(null);
			} else {
				isNew = false;
			}
		} else {
			isNew = true;
			record.setDbSchId(null);
		}
		record.setWritUserId(user.getUniqId());
		//스키마ID가 없을경우 신규로, 그렇지 않을경우 수정으로 처리
		if(isNew) {  // 신규...

			record.setDbSchId(objectIdGnrService.getNextStringId());
			record.setRegTypCd("C");
			record.setObjVers(1);


		} else if(!isNew && record.getIbsStatus().equals("D")) {  // 삭제...
			
			
			schMapper.updateExpDtm(record.getDbSchId());
			return 1;
		} else {  // 변경...

			record.setRegTypCd("U");

			record.setRqstUserId(user.getUniqId());
			if (UtilObject.isNull(record.getObjVers())) {
				record.setObjVers(1);
			}
			else {
				record.setObjVers(record.getObjVers()+1);
			}


			schMapper.updateExpDtm(record.getDbSchId()); //기존 데이터를 삭제...
		}
        if(record.getDbTblSpacPnm()==null||record.getDbTblSpacPnm().equals("")){
           record.setDbTblSpacId(null) ;
        }
        if(record.getDbIdxSpacPnm()==null||record.getDbIdxSpacPnm().equals("")){
           record.setDbIdxSpacId(null) ;
        }
		result = schMapper.insertSelective(record);
		return result;
	}

	@Override
	public int delDbSchList(ArrayList<WaaDbSch> list) throws Exception {
		int result = 0;

		for (WaaDbSch record : list) {

			// 그리드 상태가 있을 경우만 DB에 처리한다...
			if (!UtilString.isBlank(record.getIbsStatus())) {
				record.setIbsStatus("D");
				result += regDbSch(record);
			}

		}

		return result;
	}

	/** insomnia */
	public List<WaaDbSch> getDbSchemaList(WaaDbSch search) {

		return schMapper.selectSchemaList(search);
	}

	/** meta 
	 * @throws Exception 
	 * @throws SQLException */
	@Override
	public int dbConnTrgConnTest(ArrayList<WaaDbConnTrgVO> list) throws SQLException, Exception {
		int result = 0;

		for (WaaDbConnTrgVO record : list) {

			// 그리드 상태가 있을 경우만 DB에 처리한다...
			if (!UtilString.isBlank(record.getIbsStatus())) {
//				chkDbConnTrg(record);
				result += ConnTestDb(record);
			}

		}

		return result;
	}

	/** @param record
	/** @return meta 
	 * @throws Exception 
	 * @throws SQLException */
	private int ConnTestDb(WaaDbConnTrgVO record) throws SQLException, Exception {
		Connection tgtCon = null;
		try{
			//record.setDbConnAcPwd(seed.EncryptUtils.getDecData(record.getDbConnAcPwd(), record.getDbConnAcId()));
			tgtCon = ConnectionHelper.getConnection(record.getConnTrgDrvrNm(), record.getConnTrgLnkUrl(), record.getDbConnAcId(), record.getDbConnAcPwd());
			record.setDbLnkSts("성공");
			mapper.updateConnTest(record);
		} catch(Exception e) {
			record.setDbLnkSts("접속실패: " + e.getLocalizedMessage());  
			mapper.updateConnTest(record);
			logger.debug("errorMessage : {}", e.getMessage());
			
		} finally {
			
		}
		
		return 1;
		
	}

	@Override
	public List<WaaDbSch> getDevSubjDbSchemaList(WaaDbSch search) {
		
		return  schMapper.selectDevSubjSchemaList(search); 
	}

}
