/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : BizruleRqstServiceImpl.java
 * 2. Package : kr.wise.dq.bizrule.service.impl
 * 3. Comment : 
 * 4. 작성자  : meta
 * 5. 작성일  : 2014. 5. 17. 오후 1:56:57
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    meta : 2014. 5. 17. :            : 신규 개발.
 */
package kr.wise.dq.bizrule.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.exception.WiseBizException;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.approve.service.RequestApproveService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.rqstmst.service.WaqMstrMapper;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtls;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtlsMapper;
import kr.wise.commons.user.service.WaaUserMapper;
import kr.wise.dq.bizrule.service.BizruleRqstService;
import kr.wise.dq.bizrule.service.WamBrMstr;
import kr.wise.dq.bizrule.service.WamBrMstrMapper;
import kr.wise.dq.bizrule.service.WaqBrCtqMapMapper;
import kr.wise.dq.bizrule.service.WaqBrDqiMapMapper;
import kr.wise.dq.bizrule.service.WaqBrMstr;
import kr.wise.dq.bizrule.service.WaqBrMstrMapper;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : BizruleRqstServiceImpl.java
 * 3. Package  : kr.wise.dq.bizrule.service.impl
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 5. 17. 오후 1:56:57
 * </PRE>
 */
@Service("BizruleRqstService")
public class BizruleRqstServiceImpl implements BizruleRqstService {

	@Inject
	WaqBrMstrMapper waqBrMstrMapper;
	
	@Inject
	private WaqMstrMapper waqMstrMapper;
	
	@Inject
	private WaqBrCtqMapMapper waqBrCtqMapMapper;
	
	@Inject
	private WaqBrDqiMapMapper waqBrDqiMapMapper;
	
	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;  
	
	@Inject
	private RequestMstService requestMstService;
	
	@Inject
	private RequestApproveService requestApproveService;
	
	@Inject
	private WaaUserMapper waaUserMapper;
	
	@Inject
    private EgovIdGnrService objectIdGnrService;
	
	@Inject
	private WamBrMstrMapper wamBrMstrMapper;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	/** meta */
	@Override
	public List<WaqBrMstr> getBizruleRqstist(WaqMstr search) {
		return waqBrMstrMapper.selectrqstBizruleListbyMst(search);
	}

	/** meta */
	@Override
	public int register(WaqMstr mstVo, List<?> reglist) throws Exception {
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		//마스터 정보 확인 : 상태정보가 작성전("N")일 경우 신규 등록 처리
		if( "N".equals(mstVo.getRqstStepCd())) {
			requestMstService.insertWaqMst(mstVo);
		}
		
		//요청서 번호 확인...
		String rqstNo = mstVo.getRqstNo();
		
		int result = 0;
				
		//업무규칙 및 CTQ, DQI 등록....
		if(reglist != null) {
			for (WaqBrMstr record : (ArrayList<WaqBrMstr>)reglist) {
//				logger.debug("등록유형좀 보자 {}", record.getIbsStatus());
				//요청자 셋팅
				record.setFrsRqstUserId(userid);
				record.setRqstUserId(userid);
				record.setRqstNo(rqstNo);
				
				//WAQ_BR_MSTR 등록
				result += saveWaqBrMstrRqst(record);
				//DQI 등록...
				result += saveWaqBrDqiMapRqst(record);
				//CTQ 등록...
				result += waqBrCtqMapMapper.deleteByPrimaryKey(record);
				if(!StringUtils.isEmpty(record.getCtqLnm())){
					result += waqBrCtqMapMapper.insertSelective(record);
				}
			}
		}
		
		//log.debug("WAQ_BIZ_AREA 등록 건수 : "+result);
		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);
		return result;
	}

	public int saveWaqBrMstrRqst(WaqBrMstr record) throws Exception	{
		int result = 0;
		String tmpststus = record.getIbsStatus();
		
		if("I".equals(tmpststus)) {
			result = waqBrMstrMapper.insertSelective(record);
		} else if ("U".equals(tmpststus) || "R".equals(tmpststus)) {
			result = waqBrMstrMapper.updateByPrimaryKeySelective(record);
		} else if ("D".equals(tmpststus)){
			result = waqBrMstrMapper.deleteByPrimaryKey(record);
		}
		
		return result;
	}
	
	
	public int saveWaqBrDqiMapRqst(WaqBrMstr record) throws Exception	{
		int result = 0;
		String tmpststus = record.getIbsStatus();
		
		
		if("I".equals(tmpststus)) {
			String[] dqiLnms = record.getDqiLnm().split("[,]");
			String[] dqiIds = record.getDqiId().split("[,]");
			for(int i=0; i<dqiLnms.length; i++){
				record.setDqiLnm(dqiLnms[i]);
//				record.setDqiId(dqiIds[i]);
				record.setDqiId("");
				result += waqBrDqiMapMapper.insertSelective(record);
			}
		} else if ("U".equals(tmpststus) || "R".equals(tmpststus)) {
			//삭제
			result += waqBrDqiMapMapper.deleteByPrimaryKey(record);
			//INSERT
			String[] dqiLnms = record.getDqiLnm().split("[,]");
			String[] dqiIds = record.getDqiId().split("[,]");
			for(int i=0; i<dqiLnms.length; i++){
				record.setDqiLnm(dqiLnms[i]);
//				record.setDqiId(dqiIds[i]);
				record.setDqiId("");
				result += waqBrDqiMapMapper.insertSelective(record);
			}
			
//			result = waqBrDqiMapMapper.updateByPrimaryKeySelective(record);
		} else if ("D".equals(tmpststus)){
			result = waqBrDqiMapMapper.deleteByPrimaryKey(record);
		}
		
		return result;
	}
	
	/** meta */
	@Override
	public int check(WaqMstr mstVo) {
		
		int result = 0;
		
		//요청서번호 가져온다.
		String rqstNo   = mstVo.getRqstNo();
		String tblnm    = mstVo.getBizInfo().getTblNm();
		String bizdtlcd = mstVo.getBizInfo().getBizDtlCd();
		
		WaqRqstVrfDtls waqRqstVrfDtls = new WaqRqstVrfDtls();
		waqRqstVrfDtls.setRqstNo(rqstNo);
		waqRqstVrfDtls.setBizDtlCd(mstVo.getBizDcd());
		
		//검증코드  검토상태코드  초기화
		result += waqBrMstrMapper.updateCheckInit(rqstNo);
		
		//검증테이블 삭제
		result += waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);
		
		//진단대상ID UPDATE
		result += waqBrMstrMapper.updateDbConnId(rqstNo);
		//업무영역ID UPDATE
		//result += waqBrMstrMapper.updateBizAreaId(rqstNo);
		//품질지표ID UPDATE
		result += waqBrMstrMapper.updateDqiId(rqstNo);
		//중요정보항목ID UPDATE
		//result += waqBrMstrMapper.updateCtqId(rqstNo);
		//타겟진단대상ID UPDATE
		result += waqBrMstrMapper.updateTgtDbConnId(rqstNo);

		
		//등록유형코드, OBJ_ID, OBJ_VERS UPDATE
		result += waqBrMstrMapper.updateObjInfo(rqstNo);
		result += waqBrDqiMapMapper.updateObjInfo(rqstNo);	
		result += waqBrCtqMapMapper.updateObjInfo(rqstNo);
		
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm"	, tblnm);
		checkmap.put("rqstNo"	, rqstNo);
		checkmap.put("bizDtlCd"	, bizdtlcd);
		
		//DQI 테이블
		Map<String, Object> checkmap2 = new HashMap<String, Object>();
		checkmap2.put("tblnm"	, "WAQ_BR_DQI_MAP");
		checkmap2.put("rqstNo"	, rqstNo);
		checkmap2.put("bizDtlCd"	, bizdtlcd);
		
		//CTQ 테이블
		//Map<String, Object> checkmap3 = new HashMap<String, Object>();
		//checkmap3.put("tblnm"	, "WAQ_BR_CTQ_MAP");
		//checkmap3.put("rqstNo"	, rqstNo);
		//checkmap3.put("bizDtlCd"	, bizdtlcd);
		
		//업무규칙 변경된 데이터 없음(BRA00) -- 코드 수정 필요할듯...
		checkmap.put("vrfDtlCd", "BRA00");
		result += waqBrMstrMapper.checkNoChg(checkmap);
//		
//		//요청서 중복자료 검증(BRA01)
		checkmap.put("vrfDtlCd", "BRA01");
		result += waqBrMstrMapper.checkDupBizrule(checkmap);
//			
//		//미존재 업무규칙(삭제시)(BRA02)
		checkmap.put("vrfDtlCd", "BRA02");
		result += waqBrMstrMapper.checkNotExistBizrule(checkmap);
		
//		//업무영역 미존재 (BRA03)
		checkmap.put("vrfDtlCd", "BRA03");
		result += waqBrMstrMapper.checkNotExistBizarea(checkmap);
		
		//데이터품질지표 미존재 (BRA04)
		checkmap2.put("vrfDtlCd", "BRA04");
		result += waqBrMstrMapper.checkNotExistDqi(checkmap2);
		
		//중요정보항목 미존재 (BRA05)
		//checkmap3.put("vrfDtlCd", "BRA05");
		//result += waqBrMstrMapper.checkNotExistCtq(checkmap3);
		
		//진단대상 미존재 (BRA06)
		checkmap.put("vrfDtlCd", "BRA06");
		result += waqBrMstrMapper.checkNoConnTrg(checkmap);
		
		//진단대상테이블명 검증 (BRA08)
		checkmap.put("vrfDtlCd", "BRA08");
		result += waqBrMstrMapper.checkNoDbcTbl(checkmap);
		
		//진단대상컬럼명 검증 (BRA09)
		checkmap.put("vrfDtlCd", "BRA09");
		result += waqBrMstrMapper.checkNoDbcCol(checkmap);
		
		//타겟진단대상정보 입력검증 (BRA11)
		//checkmap.put("vrfDtlCd", "BRA11");
		//result += waqBrMstrMapper.checkNoTgtInfo(checkmap);
		
		//타겟DB명 검증 (BRA14)
		//checkmap.put("vrfDtlCd", "BRA14");
		//result += waqBrMstrMapper.checkTtgConnTrg(checkmap);
		
		//타겟테이블명 검증 (BRA12)
		//checkmap.put("vrfDtlCd", "BRA12");
		//result += waqBrMstrMapper.checkNoTgtDbcTbl(checkmap);
		
		//타겟컬럼명 검증 (BRA13)
		//checkmap.put("vrfDtlCd", "BRA13");
		//result += waqBrMstrMapper.checkNoTgtDbcCol(checkmap);
		
		//담당자명 중복 체크
		//checkmap.put("vrfDtlCd", "BRA15");
		//int dupCnt = waqBrMstrMapper.checkDupCrgpUserNm(checkmap);
		
		//if(dupCnt == 0){
			//담당자ID UPDATE
			//waqBrMstrMapper.updateBrCrgpUserId(rqstNo);
			//담당자명 검증(BRA07)
			//checkmap.put("vrfDtlCd", "BRA07");
		    //result += waqBrMstrMapper.checkNoCrgpUserId(checkmap);
		//}
		
//		//등록가능여부(검증코드) 업데이트
		result += waqBrMstrMapper.updateVrfCd(mstVo);
		
		//요청서명 업데이트
		mstVo.setBizDtlCd("BRA");
		requestMstService.updateRequestMsterNm(mstVo);

		return result;
	}

	/** meta */
	@Override
	public int submit(WaqMstr mstVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	/** meta */
	@Override
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {
		int result = 0;

		String rqstNo = mstVo.getRqstNo();
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}",userid, rqstNo );


		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		//ArrayList<WaqStwd>
		for (WaqBrMstr savevo : (ArrayList<WaqBrMstr>)reglist) {
			savevo.setRqstNo(rqstNo);
			savevo.setAprvUserId(userid);

			result  += waqBrMstrMapper.updatervwStsCd(savevo);
//			waqBrCtqMapMapper.updatervwStsCd(savevo);
			waqBrDqiMapMapper.updatervwStsCd(savevo);

		}

		//업데이트 내용이 없으면 오류 리턴한다.
		if (result <= 0 ) {
			logger.debug("결재 승인 실패 : 요청내용중 업데이트 대상이 없음...결재자:{},요청번호:{}",userid, rqstNo);
			throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : 요청내용중 업데이트 대상이 없음...");
		}

		//2. 결재 진행 테이블을 업데이트 한다. 최초의 결재라인을 업데이트 처리한다. (세션 유저정보와 결재진행의 userid가 동일해야 한다.
		//3.최종 승인인지 아닌지 확인한다. (이건 AOP 방식으로 처리할 수 있을까?....)
		boolean waq2wam = false;
		if(mstVo.getAprLvl() > 0){
			// S: 임시저장 Q: 결재요청 A : 승인 
			waq2wam = requestApproveService.setApproveProcess(mstVo);
		}else{
			//요청 마스터를 업데이트 한다.
			mstVo.setRqstStepCd("A");
			//요청상태 승인으로 UPDATE
			result = requestMstService.updateRqstPrcStep(mstVo);
			//승인자, 승인시간 UPDATE
			result = requestMstService.updateApproveInfo(mstVo);
			waq2wam = true;
		}
		//4. 최종 결재가 완료이면 waq ==> wam, wah으로 저장처리한다.
		if(waq2wam) {
			//waq2wam을 처리하자...
			logger.debug("waq to wam and wah");

			result = 0;
			result = regWaq2Wam(mstVo);

			//업데이트 내용이 없으면 오류 리턴한다.
			if (result <= 0 ) {
				logger.debug("결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음..결재자:{},요청번호:{}",userid, rqstNo);
				throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음");
			}

		}

		return result;
	}

	/** @param mstVo insomnia
	 * @throws Exception */
	private int regWaq2Wam(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();
		//신규 대상인 경우 ID 채번한다.
		List<WaqBrMstr> waqclist =  waqBrMstrMapper.selectWaqC(rqstno);
		for (WaqBrMstr savevo : waqclist) {
			String id = objectIdGnrService.getNextStringId();
			savevo.setBrId(id);
//			savevo.setIbsStatus("U");
			//신규 등록건에 대해 id 업데이트 처리한다....
			result += waqBrMstrMapper.updateidByKey(savevo);
			waqBrCtqMapMapper.updateidByKey(savevo);
			result += waqBrDqiMapMapper.updateidByKey(savevo);
		}
		result += waqBrMstrMapper.updateWaqCUD(rqstno);
		result += waqBrMstrMapper.deleteWAM(rqstno);
		result += waqBrMstrMapper.insertWAM(rqstno);
		result += waqBrMstrMapper.updateWAH(rqstno);
		result += waqBrMstrMapper.insertWAH(rqstno);
		
		result += waqBrDqiMapMapper.updateWaqCUD(rqstno);
		result += waqBrDqiMapMapper.deleteWAM(rqstno);
		result += waqBrDqiMapMapper.insertWAM(rqstno);
		result += waqBrDqiMapMapper.updateWAH(rqstno);
		result += waqBrDqiMapMapper.insertWAH(rqstno);
		
		//ctq 정보가 없을경우는 수행되지 않게 한다...
		waqBrCtqMapMapper.updateWaqCUD(rqstno);
		waqBrCtqMapMapper.deleteWAM(rqstno);
		waqBrCtqMapMapper.insertWAM(rqstno);
		waqBrCtqMapMapper.updateWAH(rqstno);
		waqBrCtqMapMapper.insertWAH(rqstno);
		
		return result;

	}

	/** meta */
	@Override
	public Map<String, String> delRegBizruleList(
			ArrayList<WaqBrMstr> list, WaqMstr mstVo) {
		Map<String, String> resultMap =  new HashMap<String, String>();
		int res = 0;
		String sRqstNo = mstVo.getRqstNo();
				
		for (WaqBrMstr record : list) {
			//WAQ 영역 삭제
			res += delRegBizruleList(record);
		}
		
		resultMap.put("result", Integer.toString(res) );
    	resultMap.put("rqstNo", sRqstNo);
    	
		return resultMap;
	}

	public int delRegBizruleList(WaqBrMstr delVO) {
		waqBrDqiMapMapper.deleteByPrimaryKey(delVO);
		waqBrCtqMapMapper.deleteByPrimaryKey(delVO);
		return waqBrMstrMapper.deleteByPrimaryKey(delVO);
		
	}

	/** meta 
	 * @throws Exception */
	@Override
	public int regWam2Waq(WaqMstr reqmst, ArrayList<WaqBrMstr> list) throws Exception {
		int result = 0;
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		reqmst.setRqstUserId(userid);
		result += waqBrMstrMapper.insertwam2waq(reqmst, list);
		for(int i=0; i<list.size(); i++){
			result += waqBrDqiMapMapper.insertwam2waq(reqmst, list.get(i));
		}
		result += waqBrCtqMapMapper.insertwam2waq(reqmst, list);
		register(reqmst, null);
		/*for (WaqStwd saveVo : list) {
			saveVo.setIbsStatus("I");
			saveVo.setRqstDcd("CU");
		}
		return register(reqmst, list);*/

		return result;
	}
	
	public int saveWamBrMstr(WamBrMstr record) throws Exception	{
		int result = 0;
		String tmpststus = record.getIbsStatus();
		
		if("I".equals(tmpststus)) {
			result = wamBrMstrMapper.insertSelective(record);
			result += waqBrDqiMapMapper.insertWamDqi(record);
		} else if ("U".equals(tmpststus) || "R".equals(tmpststus)) {
			result = wamBrMstrMapper.updateByPrimaryKeySelective(record);
			result += waqBrDqiMapMapper.updateWamDqi(record);
		} else if ("D".equals(tmpststus)){
			result = wamBrMstrMapper.deleteByPrimaryKey(record);
			result += waqBrDqiMapMapper.deleteWamDqi(record);
		}
		
		return result;
	}
	
	@Override
	public int regBr(WamBrMstr mstVo, List<?> reglist) throws Exception {
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		int result = 0;
				
		//업무규칙 및 CTQ, DQI 등록....
		if(reglist != null) {
			for (WamBrMstr record : (ArrayList<WamBrMstr>)reglist) {
//				logger.debug("등록유형좀 보자 {}", record.getIbsStatus());
				//요청자 셋팅
				record.setFrsRqstUserId(userid);
				record.setRqstUserId(userid);
				
				//WAQ_BR_MSTR 등록
				result += saveWamBrMstr(record);
				//DQI 등록...
				//result += saveWamBrDqiMap(record);
			}
		}
		
		return result;
	}
	
	@Override
	public int regBr(WamBrMstr saveVO, String saction) throws Exception {
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		String id = objectIdGnrService.getNextStringId();
		
		if(saction == null || saction.equals("") || saction.equals("I")) {
			saction = "I";
			saveVO.setRegTypCd("C");
			saveVO.setBrId(id);
		} else if(saction.equals("U")) {
			saveVO.setRegTypCd("U");
		}
		
		int result = 0;
				
		//요청자 셋팅
		saveVO.setFrsRqstUserId(userid);
		saveVO.setRqstUserId(userid);
		saveVO.setIbsStatus(saction);
		
		//WAQ_BR_MSTR 등록
		result += saveWamBrMstr(saveVO);
		
		return result;
	}
	
	/** meta */
	@Override
	public Map<String, String> delBrList(ArrayList<WamBrMstr> list, WamBrMstr mstVo) throws Exception {
		Map<String, String> resultMap =  new HashMap<String, String>();
		int res = 0;
				
		for (WamBrMstr record : list) {
			record.setIbsStatus("D");
			//WAQ 영역 삭제
			res += saveWamBrMstr(record);
		}
		
		resultMap.put("result", Integer.toString(res) );
    	
		return resultMap;
	}
}
