package kr.wise.dq.dbstnd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.user.service.WaaOrg;
import kr.wise.commons.user.service.WaaUserMapper;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.dbstnd.service.DbStndService;
import kr.wise.dq.dbstnd.service.WamDbDmn;
import kr.wise.dq.dbstnd.service.WamDbDmnMapper;
import kr.wise.dq.dbstnd.service.WamDbSditm;
import kr.wise.dq.dbstnd.service.WamDbSditmMapper;
import kr.wise.dq.dbstnd.service.WamDbStcd;
import kr.wise.dq.dbstnd.service.WamDbStcdMapper;
import kr.wise.dq.dbstnd.service.WamDbStwd;
import kr.wise.dq.dbstnd.service.WamDbStwdMapper;
import kr.wise.dq.dbstnd.service.WapDbDvCanAsm;
import kr.wise.dq.dbstnd.service.WapDbDvCanAsmMapper;
import kr.wise.dq.dbstnd.service.WapDbDvCanDic;
import kr.wise.dq.dbstnd.service.WapDbDvCanDicMapper;


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
@Service("dbStndService")
public class DbStndServiceImpl implements DbStndService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	WamDbSditmMapper wamDbSditmMapper;
	
	@Inject
	WamDbDmnMapper wamDbDmnMapper;
		
	@Inject
	WamDbStwdMapper wamDbStwdMapper;
	
	@Inject
	WamDbStcdMapper wamDbStcdMapper;
	
	@Inject
	WapDbDvCanAsmMapper wapDbDvCanAsmMapper;
	
	@Inject
	WapDbDvCanDicMapper wapDbDvCanDicMapper;
	
	@Inject
	WaaUserMapper waaUserMapper;
	
    @Inject
    private EgovIdGnrService objectIdGnrService;
    
    @Inject
	private EgovIdGnrService requestIdGnrService;

    @Override
    public Map<String, String> selectDomainDataType(Map<String, String> param) {
    	return wamDbDmnMapper.selectDomainDataType(param);
    }
    
    @Override
    public Map<String, String> selectDbDomainDataType(Map<String, String> param) {
    	return wamDbDmnMapper.selectDbDomainDataType(param);
    }
    
	@Override
	public List<WamDbSditm> getStndItemList(WamDbSditm data) {
		
		return wamDbSditmMapper.selectSditmList(data);
		
	}


	@Override
	public List<WamDbDmn> getDomainList(WamDbDmn data) {
			return wamDbDmnMapper.selectList(data);
		
	}


	@Override
	public List<WamDbStwd> getStndWordList(WamDbStwd data) {
		// TODO Auto-generated method stub
		return wamDbStwdMapper.selectList(data);
	}
	
	@Override
	public List<WamDbStcd> getStndCodelist(WamDbStcd data) {
		return wamDbStcdMapper.selectList(data);
	}
	
	@Override
	public List<WamDbSditm> selectUserDbList(String userId) {
		return wamDbSditmMapper.selectUserDbList(userId);
	}
	
	@Override
	public List<WamDbSditm> selectUserOrgList(String userId) {
		return wamDbSditmMapper.selectUserOrgList(userId);
	}
	
	@Override
	public WamDbSditm selectUserOrg(String userId) {
		return wamDbSditmMapper.selectUserOrg(userId);
	}

	
	
	/** 표준항목 요청서 리스트 저장 insomnia */
	public int registerItemWam(List<WamDbSditm> reglist, WaqMstr reqmst) throws Exception {

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		int result = 0;
		/**
		 * List에서 Insert List와 Update List를 분리해서 별도 리스트로 생성
		 * Insert List는 채번 로직이 필요함
 		 */
		List<WamDbSditm> insertList = reglist.stream()
				.filter(s -> s.getIbsStatus().equals("I"))
				.collect(Collectors.toList());

		List<WamDbSditm> updateList = reglist.stream()
				.filter(s -> s.getIbsStatus().equals("U"))
				.collect(Collectors.toList());

		List<WamDbSditm> deleteList = reglist.stream()
				.filter(s -> s.getIbsStatus().equals("D"))
				.collect(Collectors.toList());

		logger.info("Bulk-Insert insert list size : " + insertList.size());

		long beforeTime = System.currentTimeMillis();

		if(insertList != null) {
			for (WamDbSditm saveVo : insertList) {
				//요청번호 셋팅
				String objid = objectIdGnrService.getNextStringId();
				saveVo.setSditmId(objid);
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
			}
		}
		long afterTime = System.currentTimeMillis();
		long secDiffTime = (afterTime - beforeTime)/1000;
		logger.debug("시간차이(m) : "+secDiffTime);

		beforeTime = System.currentTimeMillis();
		Integer limit = 1000;
		for (int id = 0; id < insertList.size(); id += limit){
			result = wamDbSditmMapper.bulkInsert(new ArrayList<WamDbSditm>(insertList.subList(id, min(id + limit, insertList.size()))));
		}

		afterTime = System.currentTimeMillis();
		secDiffTime = (afterTime - beforeTime)/1000;
		logger.debug("시간차이 2(m) : "+secDiffTime);

		if (updateList != null) {
			for (WamDbSditm saveVo : updateList) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRegTypCd("U");
			}
		}

		for (int id = 0; id < updateList.size(); id += limit){
			result = wamDbSditmMapper.bulkUpdate(new ArrayList<WamDbSditm>(updateList.subList(id, min(id + limit, updateList.size()))));
		}

		for (int id = 0; id < deleteList.size(); id += limit){
			result = wamDbSditmMapper.bulkDelete(new ArrayList<WamDbSditm>(deleteList.subList(id, min(id + limit, deleteList.size()))));
		}
		
		if ("1".equals(reqmst.getChkYn())) {
			wamDbSditmMapper.updateVrfRmkNull();
			//영문약어명 체크  올바르게 들어간 약어인지 체크
//			wamDbSditmMapper.checkStwdAbr();
			//형식단어로 끝나는지 체크
			wamDbSditmMapper.checkDmnYnExsits();

			//형식단어로 끝나는지 체크(한글명)
			wamDbSditmMapper.checkDmnYnExsitsLnm();
		}

		return result;
	}

	/** @return insomnia 
	 * @throws Exception */
	public int saveWamStndItem(WamDbSditm saveVo) throws Exception {
		int result = 0;

		String tmpstatus = saveVo.getIbsStatus();

		
		if("I".equals(tmpstatus)) {
			String objid = objectIdGnrService.getNextStringId();
			saveVo.setSditmId(objid);
			saveVo.setRegTypCd("C");
			result = wamDbSditmMapper.insertSelective(saveVo);
		} else if ("U".equals(tmpstatus)) {
			LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
			String userid = user.getUniqId();
			saveVo.setRqstUserId(userid);
			saveVo.setRegTypCd("U");
			result = wamDbSditmMapper.updateByPrimaryKeySelective(saveVo);

		} else if ("D".equals(tmpstatus)) {

			result = wamDbSditmMapper.deleteByPrimaryKey(saveVo.getSditmId());

		}

		return result;
	}



	public int registerDmnWam(List<WamDbDmn> reglist) throws Exception {

//		logger.debug("mstVo:{}\nbizInfo:{}", mstVo, mstVo.getBizInfo());

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

	

		int result = 0;

		if(reglist != null) {
			for (WamDbDmn saveVo : (ArrayList<WamDbDmn>)reglist) {
				
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo("REQ_01");
				//단건 저장...
				result += saveWamStndDmn(saveVo);
			}
		}


		return result;
	}

	/** 표준도메인 요청 내용 (생성/수정/삭제) @return insomnia 
	 * @throws Exception */
	public int saveWamStndDmn(WamDbDmn saveVo) throws Exception {
		int result  = 0;

		String tmpstatus = saveVo.getIbsStatus();

		
		//도메인그룹 매핑...
		logger.debug("{}", saveVo);

		if ("I".equals(tmpstatus)) {
			//신규 등록 : 나중에 적재를 위해 미리 오브젝트 ID를 셋팅한다...
			String objid = objectIdGnrService.getNextStringId();
			saveVo.setDmnId(objid);
			saveVo.setRegTypCd("C");
			result = wamDbDmnMapper.insertSelective(saveVo);

		} else if ("U".equals(tmpstatus)){
			//업데이트
			LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
			String userid = user.getUniqId();
			saveVo.setRqstUserId(userid);
			saveVo.setRegTypCd("U");
			result = wamDbDmnMapper.updateByPrimaryKeySelective(saveVo);
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
			result = wamDbDmnMapper.deleteByPrimaryKey(saveVo.getDmnId());
			
			

		}

		return result;
	}


	/** 표준단어 리스트 등록 insomnia */
	/** 요청서 내용을 저장한다.(완료 후 임시저장 상태로 변경) insomnia
	 * @throws Exception */
	public int registerStwdWam(List<WamDbStwd> reglist) throws Exception {

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		int result = 0;

		if(reglist != null) {
			for (WamDbStwd saveVo : (ArrayList<WamDbStwd>)reglist) {
				//요청번호 셋팅...
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo("REQ_01");

				//단건 저장...
				result += saveWamStndWord(saveVo);
			}
		}

		return result;
	}

	/** @param saveVo
	/** @return insomnia
	 * @throws Exception */
	private int saveWamStndWord(WamDbStwd saveVo) throws Exception {

		int result  = 0;

		String tmpstatus = saveVo.getIbsStatus();

//		Integer sno = saveVo.getRqstSno();
//
//		logger.debug("rqstsno:{}", sno);
		if(UtilString.null2Blank(saveVo.getStwdId()).equals("")){
			tmpstatus = "I";
		}
		if ("I".equals(tmpstatus)) {
			//신규 등록 : 나중에 적재를 위해 미리 오브젝트 ID를 셋팅한다...
			String objid = objectIdGnrService.getNextStringId();
			saveVo.setStwdId(objid);
			saveVo.setRegTypCd("C");
			result = wamDbStwdMapper.insertSelective(saveVo);
		} else if ("U".equals(tmpstatus)){
			//업데이트
			saveVo.setRegTypCd("U");
			result = wamDbStwdMapper.updateByPrimaryKey(saveVo);
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
			result = wamDbStwdMapper.deleteByPrimaryKey(saveVo.getStwdId());
		}

		return result;
	}
	
	
	/** 표준단어 리스트 등록 insomnia */
	/** 요청서 내용을 저장한다.(완료 후 임시저장 상태로 변경) insomnia
	 * @throws Exception */
	public int registerStcdWam(List<WamDbStcd> reglist) throws Exception {

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		int result = 0;

		if(reglist != null) {
			for (WamDbStcd saveVo : (ArrayList<WamDbStcd>)reglist) {
				//요청번호 셋팅...
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo("REQ_01");

				//단건 저장...
				result += saveWamStcd(saveVo);
			}
		}

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
			result = wamDbStcdMapper.insertSelective(saveVo);
		} else if ("U".equals(tmpstatus)){
			//업데이트
			saveVo.setRegTypCd("U");
			result = wamDbStcdMapper.updateByPrimaryKey(saveVo);
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
			result = wamDbStcdMapper.deleteByPrimaryKey(saveVo.getCommCdId());
		}

		return result;
	}
	

	/** 표준항목 자동분할 리스트스 insomnia */
	public List<WapDbDvCanAsm> getItemDvRqstList(WapDbDvCanDic record) {
		//분할정보 조회
		List<WapDbDvCanAsm> list = wapDbDvCanAsmMapper.selectItemDvRqstList(record);
		return list;
	}

	/** 항목 자동 분할  저장한다. insomnia
	 * @throws Exception */
	public Map<String, String> regItemAutoDiv(List<WapDbDvCanAsm> list, WapDbDvCanDic record2) throws Exception {
		Map<String, String> resultMap =  new HashMap<String, String>();
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		
		String userid = user.getUniqId();
		
		String dvrqstno = requestIdGnrService.getNextStringId();
		
		int execCnt = 0;
		int rtnCnt = 0;
		
		WapDbDvCanDic record = new WapDbDvCanDic();
		record.setDvRqstNo(dvrqstno);
		record2.setDvRqstNo(dvrqstno);
		//포함 단어정보 입력
		wapDbDvCanDicMapper.insertStwdAll(record);
		//포함 도메인 정보 입력
//		wapDbDvCanDicMapper.insertDmnAll(record);
		
		
					
		for (WapDbDvCanAsm savevo : list) {
			savevo.setDvRqstNo(dvrqstno);
			savevo.setDvRqstUserId(userid);
			savevo.setDvSeCd("I");
			
			//초기데이터 입력
			execCnt += wapDbDvCanAsmMapper.insertDvListFirst(savevo);
			
			//191015 도메인 대신 분류어를 끝단어로 선택
			WapDbDvCanDic tempDic =  new WapDbDvCanDic();
			tempDic.setDvRqstNo(dvrqstno);
			tempDic.setStndAsrt(savevo.getStndAsrt());
			tempDic.setTrgLnm(savevo.getDvOrgLnm());
			tempDic.setDvOrgDbNm(savevo.getDvOrgDbNm());
			tempDic.setDbNm(savevo.getDvOrgDbNm());
			wapDbDvCanDicMapper.insertClsWd(tempDic);
			

		}
		
		rtnCnt = execCnt;

		//191015 도메인정보는 분류어와 매핑된 도메인을 INSERT 
		wapDbDvCanDicMapper.insertDmnFromClsWdMapAll(record);
		
//		도메인정보로 입력
//		WapDbDvCanAsmMapper.insertAsmDmn(dvrqstno);
		//191015 분류어도메인 매핑을 이용하여 초기 정보 입력
		wapDbDvCanAsmMapper.insertAsmDmnFromClsWdMap(record);
		
		//도메인 정보 존재시 초기데이터 삭제
		//WapDbDvCanAsmMapper.deleteExistDmnAsm(dvrqstno);
		
		while(execCnt > 0) {
			//단어정보로 분할
			wapDbDvCanAsmMapper.insertAsmDic(dvrqstno);
			//미존재단어정보 반영
			wapDbDvCanAsmMapper.insertAsmNotExistDic(dvrqstno);
			//이전데이터 삭제
			wapDbDvCanAsmMapper.deleteNotEndPrcAsmDic(dvrqstno);
			//분할데이터 존재시 분할상태로 코드 업데이트
			execCnt = wapDbDvCanAsmMapper.updateNotEndPrcAsmDic(dvrqstno);
		}
		
		//분할정보삭제
		wapDbDvCanDicMapper.deleteDvCanDicByDvRqstNo(dvrqstno);
		
		
		//분할 결과 검증 UPDATE
		//191104 분류어 미존재
		wapDbDvCanDicMapper.checkDmnYn(dvrqstno);
		
		//단어 미존재
		wapDbDvCanDicMapper.checkSdwd(dvrqstno);
		
		//SNO
//		wapDbDvCanDicMapper.updateSno(dvrqstno);
	
		resultMap.put("result", Integer.toString(rtnCnt) );
    	resultMap.put("dvrqstno", dvrqstno);
		
		return resultMap;
		
	}
	
	
	/** 항목 자동 분할  저장한다. insomnia
	 * @throws Exception */
	public Map<String, String> delItemAutoDiv(List<WapDbDvCanAsm> list) throws Exception {
		Map<String, String> resultMap =  new HashMap<String, String>();
		int rtnCnt = 0;
		String dvrqstno = "";
		for (WapDbDvCanAsm savevo : list) {
			dvrqstno = savevo.getDvRqstNo();
			rtnCnt += wapDbDvCanAsmMapper.delItemAutoDiv(savevo);
		}
		
		
		resultMap.put("result", Integer.toString(rtnCnt) );
		resultMap.put("dvrqstno", dvrqstno);
		
		return resultMap;
		
	}


	@Override
	public List getDbList(WamDbSditm data) {
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		List dbList = new ArrayList();
		
		// TODO : 권한그룹을 코드로 관리해야할지 고민 필요
		if ("2".equals(user.getUsergId()) || "3".equals(user.getUsergId()) || "OBJ_00000034586".equals(user.getUsergId())) {
			WaaOrg waaOrg = new WaaOrg();
			waaOrg.setOrgCd(data.getOrgCd());
			waaOrg.setOrgNm(data.getOrgNm());

			dbList = wamDbSditmMapper.selectOrgDbList(waaOrg);
		} 
		else {
			dbList = wamDbSditmMapper.selectUserDbList(user.getId());
		}
		return dbList;
	}

}
