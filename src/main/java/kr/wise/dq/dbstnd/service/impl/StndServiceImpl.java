package kr.wise.dq.dbstnd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import kr.wise.commons.WiseConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.user.service.WaaUserMapper;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.dbstnd.service.DbStndService;
import kr.wise.dq.dbstnd.service.StndService;
import kr.wise.dq.dbstnd.service.WamDbDmn;
import kr.wise.dq.dbstnd.service.WamDbDmnMapper;
import kr.wise.dq.dbstnd.service.WamDbSditm;
import kr.wise.dq.dbstnd.service.WamDbSditmMapper;
import kr.wise.dq.dbstnd.service.WamDbStcd;
import kr.wise.dq.dbstnd.service.WamDbStcdMapper;
import kr.wise.dq.dbstnd.service.WamDbStwd;
import kr.wise.dq.dbstnd.service.WamDbStwdMapper;
import kr.wise.dq.dbstnd.service.WamStcdMapper;
import kr.wise.dq.dbstnd.service.WapDbDvCanAsm;
import kr.wise.dq.dbstnd.service.WapDbDvCanAsmMapper;
import kr.wise.dq.dbstnd.service.WapDbDvCanDic;
import kr.wise.dq.dbstnd.service.WapDbDvCanDicMapper;


import kr.wise.dq.stnd.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static java.lang.Math.min;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : StndSditmServiceImpl.java
 * 3. Package  : kr.wise.dq.stnd.service.impl
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 3. 오후 2:49:30
 * </PRE>
 */ 
@Service("stndService")
public class StndServiceImpl implements StndService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Inject
	private WamStcdMapper wamStcdMapper;

	@Inject
	private WamStwdMapper wamStwdMapper;

	@Inject
	private WamSditmMapper wamSditmMapper;

	@Inject
	private WamDmnMapper wamDmnMapper;

	@Inject
	private WaaUserMapper waaUserMapper;

    @Inject
    private EgovIdGnrService objectIdGnrService;

	
	@Override
	public List<WamDbStcd> getStndCodelist(WamDbStcd data) {
		return wamStcdMapper.selectList(data);
	}



	
	public int registerStcdWam(List<WamDbStcd> reglist) throws Exception {

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		int result = 0;

		/**
		 * List에서 Insert List와 Update List를 분리해서 별도 리스트로 생성
		 * Insert List는 채번 로직이 필요함
		 */
		List<WamDbStcd> insertList = reglist.stream()
				.filter(s -> s.getIbsStatus().equals("I"))
				.collect(Collectors.toList());

		List<WamDbStcd> updateList = reglist.stream()
				.filter(s -> s.getIbsStatus().equals("U"))
				.collect(Collectors.toList());

		List<WamDbStcd> deleteList = reglist.stream()
				.filter(s -> s.getIbsStatus().equals("D"))
				.collect(Collectors.toList());

		logger.info("Bulk-Insert insert list size : " + insertList.size());

		if(insertList != null) {
			for (WamDbStcd saveVo : insertList) {
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
			}
		}

		for (int id = 0; id < insertList.size(); id += WiseConfig.FETCH_SIZE){
			result = wamStcdMapper.bulkInsert(new ArrayList<WamDbStcd>(insertList.subList(id, min(id + WiseConfig.FETCH_SIZE, insertList.size()))));
		}

		if (updateList != null) {
			for (WamDbStcd saveVo : updateList) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRegTypCd("U");
			}
		}

		for (int id = 0; id < updateList.size(); id += WiseConfig.FETCH_SIZE){
			result = wamStcdMapper.bulkUpdate(new ArrayList<WamDbStcd>(updateList.subList(id, min(id + WiseConfig.FETCH_SIZE, updateList.size()))));
		}

		for (int id = 0; id < deleteList.size(); id += WiseConfig.FETCH_SIZE){
			result = wamStcdMapper.bulkDelete(new ArrayList<WamDbStcd>(deleteList.subList(id, min(id + WiseConfig.FETCH_SIZE, deleteList.size()))));
		}

//		if(reglist != null) {
//			for (WamDbStcd saveVo : (ArrayList<WamDbStcd>)reglist) {
//				//요청번호 셋팅...
//				saveVo.setFrsRqstUserId(userid);
//				saveVo.setRqstUserId(userid);
//				saveVo.setRqstNo("REQ_01");
//
//				//단건 저장...
//				result += saveWamStcd(saveVo);
//			}
//		}

		return result;
	}

	/** @param saveVo
	/** @return insomnia
	 * @throws Exception */
	private int saveWamStcd(WamDbStcd saveVo) throws Exception {

		int result  = 0;

		String tmpstatus = saveVo.getIbsStatus();

//		Integer sno = saveVo.getRqstSno();
//
//		logger.debug("rqstsno:{}", sno);
		if(UtilString.null2Blank(saveVo.getCommCdId()).equals("")){
			tmpstatus = "I";
		}
		if ("I".equals(tmpstatus)) {
			//신규 등록 : 나중에 적재를 위해 미리 오브젝트 ID를 셋팅한다...
			String objid = objectIdGnrService.getNextStringId();
			saveVo.setCommCdId(objid);
			saveVo.setRegTypCd("C");
			result = wamStcdMapper.insertSelective(saveVo);
		} else if ("U".equals(tmpstatus)){
			//업데이트
			saveVo.setRegTypCd("U");
			result = wamStcdMapper.updateByPrimaryKey(saveVo);
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
			result = wamStcdMapper.deleteByPrimaryKey(saveVo.getCommCdId());
		}

		return result;
	}

	/** 표준항목 - 확정 */
	public int decideStndCode(List<WamDbStcd> reglist, WaqMstr reqmst ) throws Exception {
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		int result = 0;

		for (WamDbStcd saveVo : reglist) {
			saveVo.setFrsRqstUserId(userid);
			saveVo.setRqstUserId(userid);
			saveVo.setRegTypCd("U");
		}

		for (int id = 0; id < reglist.size(); id += WiseConfig.FETCH_SIZE){
			result = wamStcdMapper.bulkUpdateConfirm(new ArrayList<WamDbStcd>(reglist.subList(id, min(id + WiseConfig.FETCH_SIZE, reglist.size()))));
		}

		return result;
	}

	/** 표준항목 - 초기화 */
	public int initStndCode(List<WamDbStcd> reglist, WaqMstr reqmst ) throws Exception {
		//LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		//String userid = user.getUniqId();
		int result = 0;

		for (int id = 0; id < reglist.size(); id += WiseConfig.FETCH_SIZE) {
			result = wamStcdMapper.bulkDelete(new ArrayList<WamDbStcd>(reglist.subList(id, min(id + WiseConfig.FETCH_SIZE, reglist.size()))));
		}

		return result;
	}

	public int selectDupSdCodeCount(WamDbStcd data) throws Exception {
		return wamStcdMapper.selectDupSdCodeCount(data);
	}


	//배치관련
	public List<WamSditm> selectBatchSditmList() { //표준용어
		return wamSditmMapper.selectBatchSditmList();
	}
	public List<WamDmn>   selectBatchDmnList() {   //표준도메인
		return wamDmnMapper.selectBatchDmnList();
	}

	public List<WamStwd>  selectBatchStwdList() {  //표준단어
		return wamStwdMapper.selectBatchStwdList();
	}

	public List<WamDbStcd>  selectBatchStcdList()  {  //표준코드
		return wamStcdMapper.selectBatchStcdList();
	}


	@Override
	public void updateStndTotInspect(List<WamSditm> sditmList, List<WamDmn> dmnList, List<WamStwd> stwdList,
									   List<WamDbStcd> stcdList) throws Exception {
		logger.info("LOG_TRACE 기관 표준 검증결과 UPDATE START!!!!!!");

		if (sditmList != null && sditmList.size() > 0) {
			//for (int id = 0; id < sditmList.size(); id += WiseConfig.FETCH_SIZE){
			//	wamDbSditmMapper.updateDbSdimSchedule(new ArrayList<WamDbSditm>(sditmList.subList(id, min(id + WiseConfig.FETCH_SIZE, sditmList.size()))));
			//}
			wamSditmMapper.updateSditmSchedule(sditmList);
			logger.info("LOG_TRACE updateSdimSchedule UPDATE END!!!!!!");
		}

		if (dmnList != null && dmnList.size() > 0) {
			//for (int id = 0; id < dmnList.size(); id += WiseConfig.FETCH_SIZE){
			//	wamDbDmnMapper.updateDbDmnSchedule(new ArrayList<WamDbDmn>(dmnList.subList(id, min(id + WiseConfig.FETCH_SIZE, dmnList.size()))));
			//}
			wamDmnMapper.updateDmnSchedule(dmnList);
			logger.info("LOG_TRACE updateDmnSchedule UPDATE END!!!!!!");
		}

		if (stwdList != null && stwdList.size() > 0) {
			//for (int id = 0; id < stwdList.size(); id += WiseConfig.FETCH_SIZE){
			//	wamDbStwdMapper.updateDbStwdSchedule(new ArrayList<WamDbStwd>(stwdList.subList(id, min(id + WiseConfig.FETCH_SIZE, stwdList.size()))));
			//}
			wamStwdMapper.updateStwdSchedule(stwdList);
			logger.info("LOG_TRACE updateStwdSchedule UPDATE END!!!!!!");
		}

		if (stcdList != null && stcdList.size() > 0) {
			//for (int id = 0; id < stcdList.size(); id += WiseConfig.FETCH_SIZE){
			//	wamDbStcdMapper.updateDbStcdSchedule(new ArrayList<WamDbStcd>(stcdList.subList(id, min(id + WiseConfig.FETCH_SIZE, stcdList.size()))));
			//}
			wamStcdMapper.updateStcdSchedule(stcdList);
			logger.info("LOG_TRACE updateStcdSchedule UPDATE END!!!!!!");
		}

		logger.info("LOG_TRACE 기관표준 검증결과 UPDATE END!!!!!!");
	}

	/**
	 * 수준평가시스템에 등록된 기간 조회 후 등록
	 * @return 처리건수
	 */
	public int regTargetOrg() {

		int regCount = waaUserMapper.regTargetOrg();
		logger.info("LOG_TRACE 검증대상 기관 현행화 {} 건 완료}", regCount);
		return regCount;

	}

	/**
	 * 수준평가시스템에 등록된 대상 DB조회
	 * 표준관리시스템에 등록된 DB삭제 후 재등록
	 * @return 처리건수
	 */
	public int regTargetDb() {

		// 대상 DB가 있는 기관 조회
		List<Map> targetOrgList = waaUserMapper.selectTargetOrg();

		// 해당 기관의 DB 삭제
		for (int idx = 0; idx < targetOrgList.size(); idx += WiseConfig.FETCH_SIZE){
			waaUserMapper.deleteTargetOrgDb(new ArrayList<Map>(targetOrgList.subList(idx, min(idx + WiseConfig.FETCH_SIZE, targetOrgList.size()))));
		}

		// WAA_ORG_DB 테이블 TARGET_YN 컬럼 전체 N 으로 업데이트
		waaUserMapper.updateTargetDbYn();

		// 수준평가시스템 등록 DB로 재등록
		int regCount = waaUserMapper.regTargetOrgDb();

		logger.info("LOG_TRACE 검증대상 DB현행화 {} 건 완료}", regCount);

		return regCount;
	}
}
