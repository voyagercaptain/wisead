package kr.wise.dq.stnd.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.damgmt.approve.service.RequestApproveService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtlsMapper;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.model.service.WamNiaPdmCol;
import kr.wise.dq.model.service.WamNiaPdmMapper;
import kr.wise.dq.stnd.service.CodeDfntService;
import kr.wise.dq.stnd.service.DbWamCdVal;
import kr.wise.dq.stnd.service.DbWamCdValMapper;
import kr.wise.dq.stnd.service.WamCdVal;
import kr.wise.dq.stnd.service.WamCdValMapper;
import kr.wise.dq.stnd.service.WamDmnMapper;

@Service("codeDfntService")
public class CodeDfntServiceImpl implements CodeDfntService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private DbWamCdValMapper dbmapper;
	
	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;

	@Inject
	private RequestApproveService requestApproveService;

    @Inject
    private EgovIdGnrService objectIdGnrService;
    
	@Inject
	private RequestMstService requestMstService;

    @Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private WamCdValMapper wammapper;
	
	@Inject
	private WamNiaPdmMapper niamapper;
    
	@Override
	public List<WamCdVal> getCodeList(WamCdVal data) {
		// TODO Auto-generated method stub
		logger.debug("searchvo:{}", data);
		
		return wammapper.selectList(data);
	}

	@Override
	public int registerWam(ArrayList<WamCdVal> reglist) throws Exception {
		// TODO Auto-generated method stub
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//마스터 정보 확인 : 상태정보가 작성전("N")일 경우 신규 등록 처리
//		if( "N".equals(mstVo.getRqstStepCd())) {
//			requestMstService.insertWaqMst(mstVo);
//		}

//		String rqstNo = mstVo.getRqstNo();

		int result = 0;

		if(reglist != null) {
			for (WamCdVal saveVo : (ArrayList<WamCdVal>)reglist) {
				//요청번호 셋팅...
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo("REQ_01");

				//단건 저장...
				result += saveWamCdVal(saveVo);
			}
		}

//		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
//		requestMstService.updateRqstPrcStep(mstVo);

		//요청서명 업데이트
		//requestMstService.updateRequestMsterNm(mstVo);
//		String bizdtlnm = cmcdCodeService.getDetailCodeNm("BIZ_DTL_CD", "STWD");

		//요청명 업데이트 (표준단어 aaaa 외 3건)
//		requestMstService.updateWaqMstrqstNm(mstVo.getRqstNo(), "WAQ_STWD", "STWD_LNM",  bizdtlnm);

		return result;
	}

	private int saveWamCdVal(WamCdVal saveVo) throws Exception {
		// TODO Auto-generated method stub
		int result  = 0;

		String tmpstatus = saveVo.getIbsStatus();

//		Integer sno = saveVo.getRqstSno();
//
//		logger.debug("rqstsno:{}", sno);
		if(UtilString.null2Blank(saveVo.getCdValId()).equals("")){
			tmpstatus = "I";
		}
		if ("I".equals(tmpstatus)) {
			//신규 등록 : 나중에 적재를 위해 미리 오브젝트 ID를 셋팅한다...
			String objid = objectIdGnrService.getNextStringId();
			saveVo.setCdValId(objid);
			saveVo.setRegTypCd("C");
			
			result = wammapper.insertSelective(saveVo);
		} else if ("U".equals(tmpstatus)){
			//업데이트
			saveVo.setRegTypCd("U");
			result = wammapper.updateByPrimaryKey(saveVo);
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
			result = wammapper.deleteByPrimaryKey(saveVo.getCdValId());
		}

		return result;
	}

	@Override
	public List<DbWamCdVal> getDbCodeList(DbWamCdVal data) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		logger.debug("searchvo:{}", data);
		
		return dbmapper.selectDbWamList(data);
	}

	@Override
	public int registerDbWam(ArrayList<DbWamCdVal> reglist) throws Exception {
		// TODO Auto-generated method stub
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//마스터 정보 확인 : 상태정보가 작성전("N")일 경우 신규 등록 처리
//		if( "N".equals(mstVo.getRqstStepCd())) {
//			requestMstService.insertWaqMst(mstVo);
//		}

//		String rqstNo = mstVo.getRqstNo();

		int result = 0;

		if(reglist != null) {
			for (DbWamCdVal saveVo : (ArrayList<DbWamCdVal>)reglist) {
				//요청번호 셋팅...
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo("REQ_01");

				//단건 저장...
				result += saveDbWamCdVal(saveVo);
			}
		}

//		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
//		requestMstService.updateRqstPrcStep(mstVo);

		//요청서명 업데이트
		//requestMstService.updateRequestMsterNm(mstVo);
//		String bizdtlnm = cmcdCodeService.getDetailCodeNm("BIZ_DTL_CD", "STWD");

		//요청명 업데이트 (표준단어 aaaa 외 3건)
//		requestMstService.updateWaqMstrqstNm(mstVo.getRqstNo(), "WAQ_STWD", "STWD_LNM",  bizdtlnm);

		return result;
	}

	private int saveDbWamCdVal(DbWamCdVal saveVo) throws Exception {
		// TODO Auto-generated method stub
		int result  = 0;

		String tmpstatus = saveVo.getIbsStatus();

//		Integer sno = saveVo.getRqstSno();
//
//		logger.debug("rqstsno:{}", sno);
		if(UtilString.null2Blank(saveVo.getCdValId()).equals("")){
			tmpstatus = "I";
		}
		if ("I".equals(tmpstatus)) {
			//신규 등록 : 나중에 적재를 위해 미리 오브젝트 ID를 셋팅한다...
			String objid = objectIdGnrService.getNextStringId();
			saveVo.setCdValId(objid);
			saveVo.setRegTypCd("C");
			
			result = dbmapper.dbinsertSelective(saveVo);
		} else if ("U".equals(tmpstatus)){
			//업데이트
			saveVo.setRegTypCd("U");
			result = dbmapper.dbupdateByPrimaryKey(saveVo);
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
			result = dbmapper.dbdeleteByPrimaryKey(saveVo.getCdValId());
		}

		return result;
	}

	@Override
	public List<WamNiaPdmCol> getCodeGapList(WamNiaPdmCol data) throws Exception {
		// TODO Auto-generated method stub
		return niamapper.selectCodeGapList(data);
	}
	
	@Override
	public List<WamNiaPdmCol> getDbCodeGapList(WamNiaPdmCol data) throws Exception {
		// TODO Auto-generated method stub
		return niamapper.selectDbCodeGapList(data);
	}

	@Override
	public List<WamNiaPdmCol> getDbCodeExistList(WamNiaPdmCol data) {
		// TODO Auto-generated method stub
		return niamapper.selectDbCodeExistList(data);
	}

	@Override
	public List<WamNiaPdmCol> getCodeExistList(WamNiaPdmCol data) {
		// TODO Auto-generated method stub
		return niamapper.selectCodeExistList(data);
	}

}
