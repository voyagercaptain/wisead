/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndWordRqstServieImpl.java
 * 2. Package : kr.wise.dq.stnd.web.service.impl
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 4. 4. 오후 2:54:03
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 4. 4. :            : 신규 개발.
 */
package kr.wise.dq.stnd.service.impl;

import kr.wise.commons.WiseConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.exception.WiseBizException;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.damgmt.approve.service.RequestApproveService;
import kr.wise.commons.error.ErrorCode;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilString;
import kr.wise.commons.util.ValidationCheck;
import kr.wise.dq.stnd.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Math.min;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : StndWordRqstServieImpl.java
 * 3. Package  : kr.wise.dq.stnd.web.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 4. 4. 오후 2:54:03
 * </PRE>
 */
@Service("stndWordRqstService")
public class StndWordRqstServiceImpl implements StndWordRqstService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private RequestMstService requestMstService;

    @Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private WamStwdMapper wammapper;
	
	@Inject
	private RequestApproveService requestApproveService;

    @Inject
    private EgovIdGnrService objectIdGnrService;

    @Inject
	private StndDmnRqstService stndDmnRqstService;

	@Inject
	private StndItemRqstService stndItemRqstService;


	@Override
	public int check(WaqMstr mstVo) throws Exception {
		return 0;
	}
	/** insomnia 여기
	 * @throws Exception */
	/*
	@Override
	public int check(WaqMstr mstVo) throws Exception {

		logger.debug("StndWordRqstServiceImpl check");
		int result = 0;

		//요청서번호 가져온다.
		String rqstNo = mstVo.getRqstNo();
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		
		String adminYn = UtilString.null2Blank(user.getIsAdminYn());
		
		//String stwdKeyDcd = UtilString.null2Blank(mstVo.getStwdKeyDcd());
		
		//검증 초기화
		WaqRqstVrfDtls waqRqstVrfDtls = new WaqRqstVrfDtls();
		waqRqstVrfDtls.setBizDtlCd("STWD");
		waqRqstVrfDtls.setRqstNo(rqstNo);
		//검증테이블 삭제
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);
		

		//검증 시작
		//검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm"	, "WAQ_STWD");
		checkmap.put("rqstNo"	, rqstNo);
		checkmap.put("bizDtlCd"	, "STWD");
		//checkmap.put("stwdKeyDcd", stwdKeyDcd);
		
		//등록유형코드(C/U/D), 검증결과코드 초기화 ('4') 업데이트
		waqmapper.updateCheckInit(checkmap);
		
		//삭제일경우 모든 컬럼 업데이트
		waqmapper.updateStwdDelInfo(rqstNo);

		//등록요청중인 표준단어 검증(WD011)
		checkmap.put("vrfDtlCd", "WD011");
		waqmapper.checkRequestStwd(checkmap);

		//요청서내 중복자료 검증(WD001)
		checkmap.put("vrfDtlCd", "WD001");
		waqmapper.checkDupStwd(checkmap);
		//미존재 표준단어(삭제시)(WD002)
		checkmap.put("vrfDtlCd", "WD002");
		waqmapper.checkNotExistStwd(checkmap);
		
		//논리명,물리명 Unique 
		//if(stwdKeyDcd.equals("U")){
						
			//표준단어 한글명 중복(WD003)   
			checkmap.put("vrfDtlCd", "WD003");
			waqmapper.checkDupStwdLnm(checkmap);
			
			//표준단어 영문명 중복(WD004)
			checkmap.put("vrfDtlCd", "WD004");
			waqmapper.checkDupStwdPnm(checkmap);
		//}
				
		//표준단어 동음이의어 존재
		checkmap.put("vrfDtlCd", "WD012");
		waqmapper.checkHomonym(checkmap);
		
		//표준단어 금칙어 존재
		checkmap.put("vrfDtlCd", "WD015");
		waqmapper.checkNtChek(checkmap);
		
		//표준단어 유사어 존재  //190111 코드 미존재(사용할수 없음)
//		checkmap.put("vrfDtlCd", "WD016");
//		waqmapper.checkSwChek(checkmap);
			
		//TODO : 유사어 존재(WD006)
		checkmap.put("vrfDtlCd", "WD006");
		waqmapper.checkExistSymn(checkmap);
		
		
		//사용중 표준단어(삭제시)(WD005)
		checkmap.put("vrfDtlCd", "WD005");
		waqmapper.checkUseStwd(checkmap);

		
		//표준단어 한글명 공백 존재(WD007)
		checkmap.put("vrfDtlCd", "WD007");
		waqmapper.checkLnmExistSpac(checkmap);
		//표준단어 영문명 공백 존재(WD008)
		checkmap.put("vrfDtlCd", "WD008");
		waqmapper.checkPnmExistSpac(checkmap);
		//표준단어 한글명 길이 초과(WD009)
//		checkmap.put("vrfDtlCd", "WD009");
//		waqmapper.checkLnmMaxLen(checkmap);
		//표준단어 영문명 길이 초과(WD010)
//		checkmap.put("vrfDtlCd", "WD010");
//		waqmapper.checkPnmMaxLen(checkmap);
				
		//표준단어 변경된 데이터 없음(WD000)
		checkmap.put("vrfDtlCd", "WD000");
		waqmapper.checkNoChg(checkmap);

		//표준단어 물리명 존재하지 않음
		checkmap.put("vrfDtlCd", "WD013");
		waqmapper.checkPnmExists(checkmap);
		//등록가능여부(검증코드) 업데이트
		
		//표준단어 설명 등록 여부 체크
		checkmap.put("vrfDtlCd", "WD014");
		waqmapper.checkObjDesn(checkmap);
		
		result += waqRqstVrfDtlsMapper.updateVrfCd("WAQ_STWD", rqstNo, "STWD");
//		waqmapper.updateVrfCd(rqstNo);

		//도메인 검증 체크
//		stndDmnRqstService.check(mstVo);

		//마스터 정보 업데이트...
		requestMstService.updateRqstPrcStep(mstVo);

		//요청서명 업데이트
		requestMstService.updateRequestMsterNm(mstVo);


		return result;
	}
	*/

	/** 삭제예정 insomnia */
	public int submit(WaqMstr mstVo) {
		// TODO Auto-generated method stub
		return 0;
	}


	/** 표준단어 요청 리스트 조회 insomnia */
	public List<WaqStwd> getStndWordRqstist(WaqMstr search) {

		//return waqmapper.selectrqstStndWordListbyMst(search);
		return null;
	}

	/** insomnia */
	public int regStndWordRqstlist(ArrayList<WaqStwd> list) {
		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		for (WaqStwd saveVo : list) {
			saveVo.setWritUserId(userid);

//			result += saveStndWordRqst(saveVo);
		}

		return result;
	}

	/**표준단어 요청 상세조회 insomnia */
	public WaqStwd getStndWordRqstDetail(WaqStwd searchVO) {
		//return waqmapper.selectRqstStndWord(searchVO);
		return null;
	}

	/** 표준단어 리스트 등록 insomnia */
	/** 요청서 내용을 저장한다.(완료 후 임시저장 상태로 변경) insomnia
	 * @throws Exception */
	public int register(WaqMstr mstVo, List<?> reglist) throws Exception {

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//마스터 정보 확인 : 상태정보가 작성전("N")일 경우 신규 등록 처리
		if( "N".equals(mstVo.getRqstStepCd())) {
			requestMstService.insertWaqMst(mstVo);
		}

		String rqstNo = mstVo.getRqstNo();

		int result = 0;

		if(reglist != null) {
			for (WaqStwd saveVo : (ArrayList<WaqStwd>)reglist) {
				//요청번호 셋팅...
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);

				//단건 저장...
				result += saveRqstStndWord(saveVo);
			}
		}

		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);

		//요청서명 업데이트
		//requestMstService.updateRequestMsterNm(mstVo);
//		String bizdtlnm = cmcdCodeService.getDetailCodeNm("BIZ_DTL_CD", "STWD");

		//요청명 업데이트 (표준단어 aaaa 외 3건)
//		requestMstService.updateWaqMstrqstNm(mstVo.getRqstNo(), "WAQ_STWD", "STWD_LNM",  bizdtlnm);

		return result;
	}

	/** @param saveVo
	/** @return insomnia
	 * @throws Exception */
	private int saveRqstStndWord(WaqStwd saveVo) throws Exception {

		int result  = 0;

		String tmpstatus = saveVo.getIbsStatus();

//		Integer sno = saveVo.getRqstSno();
//
//		logger.debug("rqstsno:{}", sno);

		if ("I".equals(tmpstatus)) {
			//신규 등록 : 나중에 적재를 위해 미리 오브젝트 ID를 셋팅한다...
//			String objid = objectIdGnrService.getNextStringId();
//			saveVo.setStwdId(objid);
			//result = waqmapper.insertSelective(saveVo);
		} else if ("U".equals(tmpstatus)){
			//업데이트
			//result = waqmapper.updateByPrimaryKey(saveVo);
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
			//result = waqmapper.deleteByrqstSno(saveVo);
		}

		return result;
	}



	/** 표준단어 요청 리스트 삭제 insomnia */
	public int delStndWordRqst(WaqMstr reqmst, String delkey) {
		String[] delkeys = delkey.split("[|]");

		int cnt = delkeys.length;
		Integer [] ids = new Integer[cnt];


		for (int i=0; i<cnt; i++) {
			String delk = delkeys[i];
			ids[i] = new Integer(delk);
			logger.debug("delkey:{}", delk);
		}

		logger.debug("ids:{}", ids);

		//return waqmapper.deletebyRqstSno(ids, reqmst.getRqstNo());
		return 1;
	}

	/** 결재 승인 처리 업데이트.... insomnia
	 * @throws Exception */
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {
		int result = 0;

		String rqstNo = mstVo.getRqstNo();
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}",userid, rqstNo );


		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		//ArrayList<WaqStwd>
		for (WaqStwd savevo : (ArrayList<WaqStwd>)reglist) {
			savevo.setRqstNo(rqstNo);
			savevo.setAprvUserId(userid);

			//result  += waqmapper.updatervwStsCd(savevo);

		}

		//업데이트 내용이 없으면 오류 리턴한다.
		if (result <= 0 ) {
			logger.debug("결재 승인 실패 : 요청내용중 업데이트 대상이 없음...결재자:{},요청번호:{}",userid, rqstNo);
			throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : 요청내용중 업데이트 대상이 없음...");
		}

		//2. 결재 진행 테이블을 업데이트 한다. 최초의 결재라인을 업데이트 처리한다. (세션 유저정보와 결재진행의 userid가 동일해야 한다.
		//3.최종 승인인지 아닌지 확인한다. (이건 AOP 방식으로 처리할 수 있을까?....)
//		boolean waq2wam = requestApproveService.setApproveProcess(mstVo, "WAQ_STWD");
		boolean waq2wam = requestApproveService.setApproveProcessWdq(mstVo);

		//4. 최종 결재가 완료이면 waq ==> wam, wah으로 저장처리한다.
		if(waq2wam) {
			//waq2wam을 처리하자...
			logger.debug("waq to wam and wah");

			result = 0;
			//result = regWaq2Wam(mstVo);

			//업데이트 내용이 없으면 오류 리턴한다.
			if (result <= 0 ) {
				logger.debug("결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음..결재자:{},요청번호:{}",userid, rqstNo);
				throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음");
			}

		}

		return result;
	}




	
	//신청목록에 표준단어가 존재할 경우 리턴(표준신청 탭체크용)
	public int checkExistsWaqWord(WaqMstr mstr){
	

			
		    return 3;

	   
	}
	
		/** WAQ에 있는 반려된 건을 재 등록한다. 이상익
	 * @throws Exception */
	public int regWaqRejected(WaqMstr reqmst, String oldRqstNo) throws Exception {

		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		reqmst.setRqstUserId(userid);
		
		
		//waq의 반려내용을 waq로 다시 입력
		//result = waqmapper.insertWaqRejected(reqmst, oldRqstNo);

		//마스터 등록
		register(reqmst, null);
        
		//검증
		check(reqmst);
		
		//임시저장으로 변경
		reqmst.setRqstStepCd("S");
		

		return result;

	}
	
	
	/** 표준단어 리스트 등록 insomnia */
	/** 요청서 내용을 저장한다.(완료 후 임시저장 상태로 변경) insomnia
	 * @throws Exception */
	public int registerWam(List<WamStwd> reglist) throws Exception {

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		int result = 0;

		/**
		 * List에서 Insert List와 Update List를 분리해서 별도 리스트로 생성
		 * Insert List는 채번 로직이 필요함
		 */
		List<WamStwd> insertList = reglist.stream()
				.filter(s -> s.getIbsStatus().equals("I"))
				.collect(Collectors.toList());

		List<WamStwd> updateList = reglist.stream()
				.filter(s -> s.getIbsStatus().equals("U"))
				.collect(Collectors.toList());

		List<WamStwd> deleteList = reglist.stream()
				.filter(s -> s.getIbsStatus().equals("D"))
				.collect(Collectors.toList());

		if(insertList != null) {
			for (WamStwd saveVo : insertList) {
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
			}
		}

		for (int id = 0; id < insertList.size(); id += WiseConfig.FETCH_SIZE){
			result = wammapper.bulkInsert(new ArrayList<WamStwd>(insertList.subList(id, min(id + WiseConfig.FETCH_SIZE, insertList.size()))));
		}

		if (updateList != null) {
			for (WamStwd saveVo : updateList) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRegTypCd("U");
			}
		}

		for (int id = 0; id < updateList.size(); id += WiseConfig.FETCH_SIZE){
			result = wammapper.bulkUpdate(new ArrayList<WamStwd>(updateList.subList(id, min(id + WiseConfig.FETCH_SIZE, updateList.size()))));
		}

		for (int id = 0; id < deleteList.size(); id += WiseConfig.FETCH_SIZE){
			result = wammapper.bulkDelete(new ArrayList<WamStwd>(deleteList.subList(id, min(id + WiseConfig.FETCH_SIZE, deleteList.size()))));
		}

//		if(reglist != null) {
//			for (WamStwd saveVo : (ArrayList<WamStwd>)reglist) {
//				//요청번호 셋팅...
//				saveVo.setFrsRqstUserId(userid);
//				saveVo.setRqstUserId(userid);
//				saveVo.setRqstNo("REQ_01");
//
//				//단건 저장...
//				result += saveWamStndWord(saveVo);
//			}
//		}

		return result;
	}

	/** @param saveVo
	/** @return insomnia
	 * @throws Exception */
	private int saveWamStndWord(WamStwd saveVo) throws Exception {

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
			result = wammapper.insertSelective(saveVo);
		} else if ("U".equals(tmpstatus)){
			//업데이트
			saveVo.setRegTypCd("U");
			result = wammapper.updateByPrimaryKey(saveVo);
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
			result = wammapper.deleteByPrimaryKey(saveVo.getStwdId());
		}

		return result;
	}

	/** 검증 */
	public void registerWamCheck(List<WamStwd> reglist, WaqMstr reqmst ) throws Exception {
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		Map<String, String> params = new HashMap<String, String>();
		String[] dataTypeArr = {"boolean", "date", "time", "timestamp", "datetime", "interval", "datetimeltz", "datetimetz", "timestampltz", "timestamptz", "number", "numeric", "decimal", "smalldatetime", "money", "smallmoney", "long", "bigint", "smallint", "short", "tinyint", "bit", "int", "integer", "double", "double precision", "text", "ntext", "nchar", "nvarchar", "ntext", "binary", "varbinary", "binary_float", "binary_double", "varbinary", "image", "real", "clob", "blob", "nclob", "bfile"};
		for (WamStwd checkVo : reglist) {
			List<String> errorList = new ArrayList<String>();

			if(!"Y".equals(checkVo.getConfirmYn())) {
				//중복체크
				String check1_1 = "";
				int dupCnt = wammapper.selectDupSdWordCount(checkVo);
				if (dupCnt > 0) {
					errorList.add(ErrorCode.ERROR_ITEM_DUP.getMessage());
				}
			}

			// 표준 단어명 체크
			errorList.add(ValidationCheck.checkWordName(checkVo.getStwdLnm()));

			// 단어 영문 약어명
			errorList.add(ValidationCheck.checkWordEngName(checkVo.getStwdPnm()));

			// 형식 단어 체크
			errorList.add(ValidationCheck.checkWordFormat(checkVo.getDmnYn()));

			//제정일자
			errorList.add(ValidationCheck.checkWordDate(checkVo.getRqstDtm()));

			String errorMsg = errorList.stream()
					.filter(e -> !e.isEmpty())
					.collect(Collectors.joining (","));

			if (errorMsg.length() > 0) {
				checkVo.setErrChk(errorMsg);
				checkVo.setValidYn("E");
				checkVo.setConfirmYn("N");
			} else {
				checkVo.setErrChk("");
				checkVo.setValidYn("Y");
			}
		}
	}


	/** 표준항목 - 확정 */
	public int decideStndWord(List<WamStwd> reglist, WaqMstr reqmst ) throws Exception {
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		int result = 0;

		for (WamStwd saveVo : reglist) {
			saveVo.setFrsRqstUserId(userid);
			saveVo.setRqstUserId(userid);
			saveVo.setRegTypCd("U");
		}

		for (int id = 0; id < reglist.size(); id += WiseConfig.FETCH_SIZE){
			result = wammapper.bulkUpdateConfirm(new ArrayList<WamStwd>(reglist.subList(id, min(id + WiseConfig.FETCH_SIZE, reglist.size()))));
		}

		return result;
	}

	/** 표준항목 - 초기화 */
	public int initStndWord(List<WamStwd> reglist, WaqMstr reqmst ) throws Exception {
		//LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		//String userid = user.getUniqId();
		int result = 0;

		for (int id = 0; id < reglist.size(); id += WiseConfig.FETCH_SIZE) {
			result = wammapper.bulkDelete(new ArrayList<WamStwd>(reglist.subList(id, min(id + WiseConfig.FETCH_SIZE, reglist.size()))));
		}

		return result;
	}
}
