package kr.wise.commons.rqstmst.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaaBizInfo;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.rqstmst.service.WaqMstrMapper;
import kr.wise.commons.user.service.UserService;
import kr.wise.commons.user.service.WaaUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : RequestMstServiceImpl.java
 * 3. Package  : kr.wise.commons.rqstmst.service.impl
 * 4. Comment  :
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 4. 17. 오전 10:39:53
 * </PRE>
 */
/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : RequestMstServiceImpl.java
 * 3. Package  : kr.wise.commons.rqstmst.service.impl
 * 4. Comment  :
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 4. 17. 오전 10:39:55
 * </PRE>
 */
/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : RequestMstServiceImpl.java
 * 3. Package  : kr.wise.commons.rqstmst.service.impl
 * 4. Comment  :
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 4. 17. 오전 10:39:57 
 * </PRE>
 */
@Service("requestMstService")
public class RequestMstServiceImpl implements RequestMstService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@ Inject
	private WaqMstrMapper mapper;

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private UserService userService;

	@Inject
	private EgovIdGnrService requestIdGnrService;


	//==========요청 Mst Sequence 조회============
//	public String selectWaqMstSeq() {
//
//		String requestId = mapper.selectWaqMstSeq();
//
//		return requestId;
//	}

	//==========요청 Mst Insert ============
	@Override
	public int insertWaqMst(WaqMstr record) {
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		record.setWritUserId(userid);
		record.setRqstStepCd("N"); //검증전으로 셋팅....
//		record.setFrsRqstUserId(userid);
//		record.setRqstUserId(userid);
		
		int iRtn = 0; 
		
		iRtn = mapper.updateWaqMstrRequestInfo(record);
		
		if(iRtn == 0){
			
			mapper.insertSelective(record);
		}
		
		return iRtn; 
	}

	//==========요청 Mst Update ============
	@Override
	public int updateWaqMst(WaqMstr record) {

		int iRtn = mapper.updateByPrimaryKeySelective(record);

		return iRtn;
	}

	//==========요청 Mst Delete ============
	@Override
	public int deleteWaqMst(WaqMstr record) {

		int iRtn = mapper.deleteByPrimaryKey(record.getRqstNo());

		return iRtn;
	}

	//=======요청 Mst 요청상태(RQST_PRC_STEP, RQST_USER_ID, RQST_DTM) Update =====
	@Override
	public int updateRqstPrcStep(WaqMstr record) {
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		record.setRqstUserId(userid);
		int iRtn = mapper.updateWaqMstrStepCd(record);

		return iRtn;
	}

	/** 요청마스터의 요청자 정보 업데이트: 요청일자, 요청자 ID @return insomnia */
	@Override
	public int updateRequestInfor(WaqMstr reqmst) {

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		reqmst.setRqstUserId(userid);

		return mapper.updateWaqMstrRequestInfo(reqmst);
	}

	/** 요청마스터의 결재자 정보 업데이트: 승인일자, 승인자 ID @return insomnia */
	/** 결재진행상태도 이곳에서 UPDATE meta */
	@Override
	public int updateApproveInfo(WaqMstr reqmst) {

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		reqmst.setAprvUserId(userid);

		return mapper.updateWaqMstrAprvInfo(reqmst);
	}

	/** 요청서 마스터 정보 조회
	 * @return */
	@Override
	public WaqMstr getrequestMst(WaqMstr mstVo) {

		WaaBizInfo bizInfo = getBizInfo(mstVo);

		WaqMstr mst = mapper.selectrequestMst(mstVo);

		mst.setBizDtlCd(mstVo.getBizDtlCd());

		mst.setBizInfo(bizInfo);

		return mst;

	}

	/** 요청서명 업데이트 (요청서 내용및 갯수로 셋팅- 000 외 0건 insomnia */
	@Override
	public int updateWaqMstrqstNm(String rqstNo, String tblnm, String colnm,  String bizdtlnm) {

		return mapper.updateWaqMstrqstNm(rqstNo, tblnm, colnm,  bizdtlnm);
	}
	
	/** 요청서명 업데이트 (요청서 내용및 갯수로 셋팅- 000 외 0건 승인된 건만*/
	@Override
	public int updateWaqMstrqstNm2(String rqstNo, String tblnm, String colnm,  String bizdtlnm) {

		return mapper.updateWaqMstrqstNm2(rqstNo, tblnm, colnm,  bizdtlnm);
	}


	/** 검증이 등록가능 상태이고 검토상태가 전부 반려인지 확인하여 승인이면 "1" 반려이면 "2" insomnia */
	@Override
	public String getrvwStatus(String rqstNo, String tblnm) {

		return mapper.selectrvwStatus(rqstNo, tblnm);
	}

	/** 요청마스터 요청명 업데이트.... insomnia */
	@Override
	public int updateRequestMsterNm(WaqMstr reqmst) {
		//TODO 요청서명 업데이트 (BIZ_DTL_CD 값 필요)
		logger.debug("reqmst:{}", reqmst);
//		String bizdtlnm = cmcdCodeService.getDetailCodeNm("BIZ_DTL_CD", reqmst.getBizDtlCd());
		String bizdtlnm = cmcdCodeService.getDetailCodeNm("BIZ_DCD", reqmst.getBizDcd());


		WaaBizInfo bizinfo = getBizInfo(reqmst);

		//요청명 업데이트 (표준단어 aaaa 외 3건 , BIZ_DTL_CD 값에 따른  waq테이블, 논리명(물리명)-컬럼명을 가져와야함...)
		if ("DIC".equals(reqmst.getBizDcd())) {
			return mapper.updateWaqMstrqstNmDic(reqmst.getRqstNo(), bizinfo.getTblNm(), bizinfo.getColNm(),  bizdtlnm);
		/*
		} else if("DTT".equals(reqmst.getBizDcd())) {
			if(reqmst.getRqstStepCd().equals("Q")){
			    return mapper.updateWaqMstrqstNmDtt2(reqmst.getRqstNo(), bizinfo.getTblNm(), bizinfo.getColNm(),  bizdtlnm);
			}
			else{
				return mapper.updateWaqMstrqstNmDtt(reqmst.getRqstNo(), bizinfo.getTblNm(), bizinfo.getColNm(),  bizdtlnm);
			}
			*/
		} else if("ADC".equals(reqmst.getBizDcd())) {
			return mapper.updateWaqMstrqstNmAdc(reqmst.getRqstNo(), bizinfo.getTblNm(), bizinfo.getColNm(),  bizdtlnm);
		} else if("DDX".equals(reqmst.getBizDcd()) || "PRL".equals(reqmst.getBizDcd())) {
			
			return updateWaqMstrqstDtlNm(reqmst.getRqstNo(), bizinfo.getTblNm(), bizinfo.getColNm(),  bizdtlnm);	
		}
		
		
		
//		return updateWaqMstrqstNm(reqmst.getRqstNo(), "WAQ_STWD", "STWD_LNM",  bizdtlnm);
		if(reqmst.getRqstStepCd().equals("Q")){
		   return updateWaqMstrqstNm2(reqmst.getRqstNo(), bizinfo.getTblNm(), bizinfo.getColNm(),  bizdtlnm);
		}else{
		   return updateWaqMstrqstNm(reqmst.getRqstNo(), bizinfo.getTblNm(), bizinfo.getColNm(),  bizdtlnm);	
		}
	}

	private int updateWaqMstrqstDtlNm(String rqstNo, String tblNm, String colNm, String bizdtlnm) {
		// TODO Auto-generated method stub
		return mapper.updateWaqMstrqstDtlNm(rqstNo, tblNm, colNm,  bizdtlnm);
	}

	/** 요청마스터 정보에 따라 업무별 관련 정보를 가져온다. (관련 요청 테이블 및 url)
	 * 	업무 추가시 반드시 해당 메소드에 업무별 정보를 추가한다.
	 *  @return insomnia
	 * @throws Exception */
	@Override
	public WaqMstr getBizInfoInit(WaqMstr reqmst) throws Exception {

		String bizDcd = reqmst.getBizDcd();
		String bizDtl = reqmst.getBizDtlCd();

		String reqid = requestIdGnrService.getNextStringId();
		reqmst.setRqstNo(reqid);
		//코드명 조회
		String bizDcdNm = cmcdCodeService.getDetailCodeNm("BIZ_DCD", bizDcd);
		String bizDtlNm = cmcdCodeService.getDetailCodeNm("BIZ_DTL_CD", bizDtl);
		logger.debug(" bizDcd : "+ bizDcd + " ->  bizDcdNm  : "+bizDcdNm);
		logger.debug(" bizDtl : "+ bizDtl + " ->  bizDtlNm  : "+bizDtlNm);
		reqmst.setBizDcdNm(bizDcdNm);

		if ("DIC".equals(bizDcd)) {
			reqmst.setRqstStepCd("N");
			reqmst.setRqstStepCdNm("작성전");
//			reqmst.setBizDtlCd("STWD");
		}else if ("STWD".equals(bizDtl)) {
    		reqmst.setRqstStepCd("N");
    		reqmst.setRqstStepCdNm("작성전");
//    		reqmst.setBizDtlCd("STWD");
		} else if ("DMN".equals(bizDtl)) {
    		reqmst.setRqstStepCd("N");
    		reqmst.setRqstStepCdNm("작성전");
//    		reqmst.setBizDtlCd("DMN");
		} else if ("SDITM".equals(bizDtl)) {
    		reqmst.setRqstStepCd("N");
    		reqmst.setRqstStepCdNm("작성전");
//    		reqmst.setBizDtlCd("SDITM");
		} else if ("SDENG".equals(bizDtl)) {
    		reqmst.setRqstStepCd("N");
    		reqmst.setRqstStepCdNm("작성전");
//    		reqmst.setBizDtlCd("SDITM");
		}else if ("LDM".equals(bizDcd)) {
    		reqmst.setRqstStepCd("N");
    		reqmst.setRqstStepCdNm("작성전");
//    		reqmst.setBizDtlCd("LDM");	
		}else if ("PDM".equals(bizDcd)) {
    		reqmst.setRqstStepCd("N");
    		reqmst.setRqstStepCdNm("작성전");
//    		reqmst.setBizDtlCd("PDM");
		}else if ("R9P".equals(bizDcd)) {
    		reqmst.setRqstStepCd("N");
    		reqmst.setRqstStepCdNm("작성전");
//           reqmst.setBizDtlCd("R9P");
		}else if ("PRL".equals(bizDcd)) {
    		reqmst.setRqstStepCd("N");
    		reqmst.setRqstStepCdNm("작성전");
		
		} else if ("DDL".equals(bizDcd)) {
    		reqmst.setRqstStepCd("N");
    		reqmst.setRqstStepCdNm("작성전");
//    		reqmst.setBizDtlCd("DDL");
		} else if ("DML".equals(bizDcd)) {
			reqmst.setRqstStepCd("N");
			reqmst.setRqstStepCdNm("작성전");
//    		reqmst.setBizDtlCd("DDL");
		} else if ("DDX".equals(bizDcd)) {
			reqmst.setRqstStepCd("N");
			reqmst.setRqstStepCdNm("작성전");
//    		reqmst.setBizDtlCd("DDLIDX");
		} else if ("DTT".equals(bizDcd)) {
			reqmst.setRqstStepCd("N");
			reqmst.setRqstStepCdNm("작성전");
    		reqmst.setBizDtlCd("TSFTBL");     		
		} else if ("DET".equals(bizDcd)) {
			reqmst.setRqstStepCd("N");
			reqmst.setRqstStepCdNm("작성전");
//    		reqmst.setBizDtlCd("DDLETC");	
		} else if ("TMP".equals(bizDcd)) {
    		reqmst.setRqstStepCd("N");
    		reqmst.setRqstStepCdNm("작성전");
//    		reqmst.setBizDtlCd("TMP");
		} else if ("CMP".equals(bizDcd)) {
    		reqmst.setRqstStepCd("N");
    		reqmst.setRqstStepCdNm("작성전");
//    		reqmst.setBizDtlCd("TMP");
		} else if ("DMP".equals(bizDcd)) {
    		reqmst.setRqstStepCd("N");
    		reqmst.setRqstStepCdNm("작성전");
//    		reqmst.setBizDtlCd("TMP");
		} else if ("CCS".equals(bizDcd)) {
    		reqmst.setRqstStepCd("N");
    		reqmst.setRqstStepCdNm("작성전");
//    		reqmst.setBizDtlCd("CCS");
		}else if ("BZA".equals(bizDcd)) {
    		reqmst.setRqstStepCd("N");
    		reqmst.setRqstStepCdNm("작성전");
    		reqmst.setBizDtlCd(bizDcd);
		}else if ("DQI".equals(bizDcd)) {
    		reqmst.setRqstStepCd("N");
    		reqmst.setRqstStepCdNm("작성전");
    		reqmst.setBizDtlCd(bizDcd);
		} else if ("CTQ".equals(bizDcd)) {
    		reqmst.setRqstStepCd("N");
    		reqmst.setRqstStepCdNm("작성전");
    		reqmst.setBizDtlCd(bizDcd);
		} else if ("BRA".equals(bizDcd)) {
    		reqmst.setRqstStepCd("N");
    		reqmst.setRqstStepCdNm("작성전");
    		reqmst.setBizDtlCd(bizDcd);
		}else if ("PRF".equals(bizDcd)) {
    		reqmst.setRqstStepCd("N");
    		reqmst.setRqstStepCdNm("작성전");
//    		reqmst.setBizDtlCd("PDM");
		}else if ("IMP".equals(bizDcd)) {
    		reqmst.setRqstStepCd("N");
    		reqmst.setRqstStepCdNm("작성전");
    		reqmst.setBizDtlCd(bizDcd);
		} else if ("IMR".equals(bizDcd)) {
    		reqmst.setRqstStepCd("N");
    		reqmst.setRqstStepCdNm("작성전");
    		reqmst.setBizDtlCd(bizDcd);
		}else if ("MSG".equals(bizDcd)) {
    		reqmst.setRqstStepCd("N");
    		reqmst.setRqstStepCdNm("작성전");
    		reqmst.setBizDtlCd(bizDcd);
		}else if ("CDT".equals(bizDcd)) {
    		reqmst.setRqstStepCd("N");
    		reqmst.setRqstStepCdNm("작성전");
    		reqmst.setBizDtlCd(bizDcd);
		}else if ("MST".equals(bizDcd)) {
    		reqmst.setRqstStepCd("N");
    		reqmst.setRqstStepCdNm("작성전");
    		reqmst.setBizDtlCd(bizDcd);
		} else if ("CDM".equals(bizDcd)) {
    		reqmst.setRqstStepCd("N");
    		reqmst.setRqstStepCdNm("작성전");
//    		reqmst.setBizDtlCd("DMN");
		} else if ("ADC".equals(bizDcd)) {
			reqmst.setRqstStepCd("N");
			reqmst.setRqstStepCdNm("작성전");
//			reqmst.setBizDtlCd(bizDcd);
		} else if ("APD".equals(bizDcd)) {
			reqmst.setRqstStepCd("N");
			reqmst.setRqstStepCdNm("작성전");
//			reqmst.setBizDtlCd(bizDcd);
		} else if ("API".equals(bizDcd)) {
			reqmst.setRqstStepCd("N");
			reqmst.setRqstStepCdNm("작성전");
//			reqmst.setBizDtlCd(bizDcd);
		}

		return reqmst;
	}
	/** 요청마스터 정보에 따라 업무별 관련 정보를 가져온다. (관련 요청 테이블 및 url)
	 * 	업무 추가시 반드시 해당 메소드에 업무별 정보를 추가한다.
	 *  @return insomnia */
	@Override
	public WaaBizInfo getBizInfo(WaqMstr reqmst) {
		String bizcd = reqmst.getBizDcd();
		String bizDtl = reqmst.getBizDtlCd();
		WaaBizInfo retbizinfo = new WaaBizInfo();

		retbizinfo.setBizDcd(bizcd);
		retbizinfo.setBizDtlCd(bizDtl);

		if ("STWD".equals(bizDtl)) {
			retbizinfo.setTblNm("WAQ_STWD");
			retbizinfo.setColNm("STWD_LNM");
//			retbizinfo.setUrl("/meta/stnd/stndword_rqst.do");
			retbizinfo.setUrl("/dq/stnd/stndtot_rqst.do");
		} else if ("DMN".equals(bizDtl)) {
			retbizinfo.setTblNm("WAQ_DMN");
			retbizinfo.setColNm("DMN_LNM");
//			retbizinfo.setUrl("/meta/stnd/stnddmn_rqst.do");
			retbizinfo.setUrl("/dq/stnd/stndtot_rqst.do");

		} else if ("SDITM".equals(bizDtl)) {
			retbizinfo.setTblNm("WAQ_SDITM");
			retbizinfo.setColNm("SDITM_LNM");
//			retbizinfo.setUrl("/meta/stnd/stnditem_rqst.do");
			retbizinfo.setUrl("/dq/stnd/stndtot_rqst.do");
			
		} else if ("SDENG".equals(bizcd)) { 
			retbizinfo.setTblNm("WAQ_SDITM");
			retbizinfo.setColNm("SDITM_LNM");
			retbizinfo.setUrl("/meta/stnd/stnditem_eng_rqst.do");
						
		} else if ("DIC".equals(bizcd)) {
			retbizinfo.setTblNm("WAQ_SDITM");
			retbizinfo.setColNm("SDITM_LNM");
			retbizinfo.setUrl("/dq/stnd/stndtot_rqst.do");

		} else if ("LDM".equals(bizcd)) {
			retbizinfo.setTblNm("WAQ_LDM_ENTY");
			retbizinfo.setColNm("LDM_ENTY_LNM");
			retbizinfo.setUrl("/meta/ldm/ldmenty_rqst.do");	 
			
		} else if ("PDM".equals(bizcd)) {
			retbizinfo.setTblNm("WAQ_PDM_TBL");
			retbizinfo.setColNm("PDM_TBL_PNM");
			retbizinfo.setUrl("/meta/model/pdmtbl_rqst.do");

		} else if ("R7P".equals(bizcd)) {
			//ERWIN R7 연계 모델등록
			retbizinfo.setTblNm("WAQ_PDM_TBL");
			retbizinfo.setColNm("PDM_TBL_PNM");
			retbizinfo.setUrl("/meta/model/r7_pdmtbl_rqst.do");

		} else if ("R9P".equals(bizcd)) {
			//ERWIN R9연계 모델등록
			retbizinfo.setTblNm("WAQ_PDM_TBL");
			retbizinfo.setColNm("PDM_TBL_PNM");
			retbizinfo.setUrl("/meta/model/r9_pdmtbl_rqst.do");
		
		} else if ("PRL".equals(bizcd)) {
			//물리모델 관계 
			retbizinfo.setTblNm("WAQ_PDM_REL_MST");
			retbizinfo.setColNm("PDM_REL_PNM");
			retbizinfo.setUrl("/meta/pdmrel/pdmrelation_rqst.do"); 	
			
		} else if ("DDX".equals(bizcd)) {
			retbizinfo.setTblNm("WAQ_DDL_IDX");
			retbizinfo.setColNm("DDL_IDX_PNM");
			retbizinfo.setUrl("/meta/ddl/ddlidx_rqst.do");

		} else if ("DDL".equals(bizcd)) {
			retbizinfo.setTblNm("WAQ_DDL_TBL");
			retbizinfo.setColNm("DDL_TBL_PNM");
			retbizinfo.setUrl("/meta/ddl/ddltbl_rqst.do");

		} else if ("DTT".equals(bizcd)) {
			retbizinfo.setTblNm("WAQ_DDL_TBL");
			retbizinfo.setColNm("DDL_TBL_PNM");
			retbizinfo.setUrl("/meta/ddltsf/ddltsftbl_rqst.do");	
			
		} else if ("DET".equals(bizcd)) {
			retbizinfo.setTblNm("WAQ_DDL_ETC");
			retbizinfo.setColNm("DDL_ETC_PNM");
			retbizinfo.setUrl("/meta/ddletc/ddletc_rqst.do");		
			
		} else if ("DML".equals(bizcd)) {
			retbizinfo.setTblNm("WAQ_DML_WRK");
			retbizinfo.setColNm("RQST_NM");
			retbizinfo.setUrl("/meta/ddl/dmlinic_rqst.do");
		

		} else if ("TMP".equals(bizcd)) {
			retbizinfo.setTblNm("WAQ_TBL_MAP");
			retbizinfo.setColNm("TGT_TBL_PNM");
			retbizinfo.setUrl("/meta/mapping/tblmap_rqst.do");

		} else if ("CMP".equals(bizcd)) {
			retbizinfo.setTblNm("WAQ_COL_MAP");
			retbizinfo.setColNm("TGT_COL_PNM");
			retbizinfo.setUrl("/meta/mapping/colmap_rqst.do");

		} else if ("DMP".equals(bizcd)) {
			retbizinfo.setTblNm("WAQ_COD_MAP");
			retbizinfo.setColNm("TGT_DMN_LNM");
			retbizinfo.setUrl("/meta/mapping/codmap_rqst.do");

		} else if ("CCS".equals(bizcd)) {
			retbizinfo.setTblNm("WAQ_CODE_CFC_SYS");
			retbizinfo.setColNm("CODE_CFC_SYS_LNM");
			retbizinfo.setUrl("/meta/codecfcsys/codecfcsys_rqst.do");

		}else if ("BZA".equals(bizcd)) {

			retbizinfo.setTblNm("WAQ_BIZ_AREA");
			retbizinfo.setColNm("BIZ_AREA_LNM");
			retbizinfo.setUrl("/dq/criinfo/bizarea/bizarea_rqst.do");

		} else if ("DQI".equals(bizcd)) {
			retbizinfo.setTblNm("WAQ_DQI");
			retbizinfo.setColNm("DQI_LNM");
			retbizinfo.setUrl("/dq/criinfo/dqi/dqi_rqst.do");

		} else if ("CTQ".equals(bizcd)) {
			retbizinfo.setTblNm("WAQ_CTQ");
			retbizinfo.setColNm("CTQ_LNM");
			retbizinfo.setUrl("/dq/criinfo/ctq/ctq_rqst.do");

		} else if ("IMP".equals(bizcd)) {
			retbizinfo.setTblNm("WAQ_CS_ANA_MSTR");
			retbizinfo.setColNm("ANA_JOB_NM");
			retbizinfo.setUrl("/dq/impv/impl_rqst.do");

		} else if ("IMR".equals(bizcd)) {
			retbizinfo.setTblNm("WAQ_IM_ACT_MSTR");
			retbizinfo.setColNm("ANA_JOB_NM");
			retbizinfo.setUrl("/dq/impv/imrsl_rqst.do");

		} else if ("BRA".equals(bizcd)) {
			retbizinfo.setTblNm("WAQ_BR_MSTR");
			retbizinfo.setColNm("BR_NM");
			retbizinfo.setUrl("/dq/bizrule/bizrule_rqst.do");

		}
		else if ("PRF".equals(bizcd)) {
			retbizinfo.setTblNm("WAQ_PRF_MSTR");
			retbizinfo.setColNm("OBJ_NM");
			retbizinfo.setUrl("/dq/profile/prfexl_rqst.do");
		}
		else if ("APD".equals(bizDtl)) {
			retbizinfo.setTblNm("WAQ_APP_STWD");
			retbizinfo.setColNm("APP_STWD_LNM");
			retbizinfo.setUrl("/meta/app/appstndtot_rqst.do");
		}
		else if ("API".equals(bizDtl)) {
			retbizinfo.setTblNm("WAQ_APP_SDITM");
			retbizinfo.setColNm("APP_SDITM_LNM");
			retbizinfo.setUrl("/meta/app/appstndtot_rqst.do");
		} else if ("ADC".equals(bizcd)) {
			retbizinfo.setTblNm("WAQ_APP_SDITM");
			retbizinfo.setColNm("APP_SDITM_LNM");
			retbizinfo.setUrl("/meta/app/appstndtot_rqst.do");
		}
		else if ("MSG".equals(bizcd)) {
			retbizinfo.setTblNm("WAQ_MSG");
			retbizinfo.setColNm("MSG_CD");
			retbizinfo.setUrl("/meta/stnd/message_rqst.do");
			
		}
		else if ("PDP".equals(bizcd)) {
			retbizinfo.setTblNm("WAQ_PDM_TBL");
			retbizinfo.setColNm("PDM_TBL_PNM");
			retbizinfo.setUrl("/meta/model/pd_pdmtbl_rqst.do");

		}
		else if ("CDT".equals(bizcd)) {
			retbizinfo.setTblNm("WAQ_CD_VAL_TSF");
			retbizinfo.setColNm("CD_VAL");
			retbizinfo.setUrl("/meta/stnd/codetsf_rqst.do");

		}
		else if ("MST".equals(bizcd)) {
			retbizinfo.setTblNm("WAQ_MSG_TSF");
			retbizinfo.setColNm("MSG_CD");
			retbizinfo.setUrl("/meta/stnd/messagetsf_rqst.do");

		}else if ("CDM".equals(bizcd)) {
			retbizinfo.setTblNm("WAQ_DMN");
			retbizinfo.setColNm("DMN_LNM");
//			retbizinfo.setUrl("/meta/stnd/stnddmn_rqst.do");
			retbizinfo.setUrl("/meta/stnd/codedmn_rqst.do");

		}




		return retbizinfo;
	}

	/** meta */
	@Override
	public List<WaqMstr> getRqstMyList(WaqMstr record) {
		// TODO Auto-generated method stub
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();

		List<WaqMstr> list = null;

		String userid = user.getUniqId();
		//유저ID의 그룹을 확인한다. 관리자권한은 전체조회 가능, 그외에는 본인 요청내역만 확인가능...
		WaaUser tmpUser = userService.getUserInfo(userid);
		if(tmpUser != null) {
			if(!"AD".equals(tmpUser.getUsergTypCd())) { // 관리자가 아닐 경우 본인것만 조회되도록 한다...
				// record.setRqstUserId(userid);
			}
			list = mapper.selectRqstMyList(record);

		}

		return list;
	}
	
	/** meta */
	@Override
	public List<WaqMstr> getRejMyList(WaqMstr record) {
		// TODO Auto-generated method stub
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();

		List<WaqMstr> list = null;

		String userid = user.getUniqId();
		//유저ID의 그룹을 확인한다. 관리자권한은 전체조회 가능, 그외에는 본인 요청내역만 확인가능...
		WaaUser tmpUser = userService.getUserInfo(userid);
		if(tmpUser != null) {
			if(!"AD".equals(tmpUser.getUsergTypCd())) { // 관리자가 아닐 경우 본인것만 조회되도록 한다...
				record.setRqstUserId(userid);
			}
			list = mapper.selectRejMyList(record);

		}

		return list;
	}

	/** meta */
	@Override
	public List<WaqMstr> getRqstToDoList(WaqMstr record) {
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		record.setRqstUserId(userid);
		return mapper.selectRqstToDoList(record);
	}
	
	
	@Override
	public List<WaqMstr> getRqstResultList(WaqMstr record) {
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		record.setRqstUserId(userid);
		return mapper.selectRqstResultList(record);
	}

	/** meta */
	/** 메인화면 표시용 내 요청목록 조회 (5개) */
	@Override
	public List<WaqMstr> getRqstMyListForMain(WaqMstr record) {
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		record.setRqstUserId(userid);
		return mapper.selectRqstMyListForMain(record);
	}

	/** meta */
	/** 메인화면 표시용 내 결재목록 조회 (5개) */
	@Override
	public List<WaqMstr> getRqstToDoListForMain(WaqMstr record) {
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		record.setRqstUserId(userid);
		return mapper.selectRqstToDoListForMain(record);
	}

	/** meta */
	/** 메인화면에서 등록요청건수 리턴 */
	@Override
	public int getRqstMyListCount(WaqMstr record) {
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		record.setRqstUserId(userid);
		return mapper.selectRqstMyListCount(record).getRqstMyCount();
	}

	/** meta */
	/** 메인화면에서 결재대상건수 리턴 */
	@Override
	public int getRqstToDoListCount(WaqMstr record) {
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		//유저ID의 그룹을 확인한다. 관리자권한은 전체조회 가능, 그외에는 본인 요청내역만 확인가능...
		if(user.getIsAdminYn().equals("Y")) {
			 //userid = null;
		}
		record.setRqstUserId(userid);
		return mapper.selectRqstToDoListCount(record).getRqstToDoCount();
	}

	/** meta */
	/** 메인화면에서 결재대상건수 리턴 */
	@Override
	public WaqMstr getRqstCount(WaqMstr record) {
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		//유저ID의 그룹을 확인한다. 관리자권한은 전체조회 가능, 그외에는 본인 요청내역만 확인가능...
		if(user.getIsAdminYn().equals("Y") ) {
			 // userid = null;
		}
		record.setRqstUserId(userid);
		return mapper.selectRqstCount(record);
	}


}
