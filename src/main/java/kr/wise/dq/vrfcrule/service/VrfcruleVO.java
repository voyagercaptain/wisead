package kr.wise.dq.vrfcrule.service;

import java.util.ArrayList;
import kr.wise.commons.cmm.CommonVo;
import kr.wise.dq.profile.colana.service.WamPrfColAnaVO;
import kr.wise.dq.profile.coldtfrmana.service.WamPrfDtfrmAnaVO;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaAnaVO;
import kr.wise.dq.profile.colptrana.service.WamPrfPtrAnaVO;
import kr.wise.dq.profile.colrngana.service.WamPrfRngAnaVO;
import kr.wise.dq.profile.tblrel.service.WamPrfRelColVO;
import kr.wise.dq.profile.tblrel.service.WamPrfRelTblVO;
import kr.wise.dq.profile.tblunq.service.WamPrfUnqColVO;

public class VrfcruleVO extends CommonVo {

	/** 검증룰ID */
    private String vrfcId;

    /** 검증룰명 */
    private String vrfcNm;

    /** 검증유형 */
    private String vrfcTyp;

    /** 검증룰 */
    private String vrfcRule;

    /** 검증룰설명 */
    private String vrfcDescn;

    /** 품질지표ID */
    private String dqiId;
    
    /** 품질지표명 */
    private String dqiLnm;
    

    private String ruleRelId;

    private String prfKndCd;
    
    private String prfKndCdNm;

    private String dbConnTrgId;

    private String dbSchId;

    private String dbcTblNm;
    
    private String dbcTblKorNm;
    
    private String dbcColNm;
    
    private String dbcColKorNm;

    private String adtCndSql;
    
    private String addCnd;

    private String hntSql;

    private String useYn;
    
    //테이블, 컬럼 프로파일 구분
    private String tblColGb;
    
    //sql 생성
    private String dbmsTypCd;
    
    private String dbConnTrgPnm;
    
    private String dbSchPnm;
    
    private String cdSql;
    
    private String cdClsColNm;                                                                             
    private String cdClsNmColNm;                                   
    private String cdIdColNm;                                  
    private String cdNmColNm;
    
    private String shdJobId;     
    private String shdKndCd;
    private String etcJobNm;
    private String shdJobNm;
    
    
    @Override
	public String toString() {
		return "VrfcruleVO [" + (vrfcId != null ? "vrfcId=" + vrfcId + ", " : "")
				+ (vrfcNm != null ? "vrfcNm=" + vrfcNm + ", " : "")
				+ (vrfcTyp != null ? "vrfcTyp=" + vrfcTyp + ", " : "")
				+ (vrfcRule != null ? "vrfcRule=" + vrfcRule + ", " : "")
				+ (vrfcDescn != null ? "vrfcDescn=" + vrfcDescn + ", " : "")
				+ (dqiId != null ? "dqiId=" + dqiId + ", " : "") + (dqiLnm != null ? "dqiLnm=" + dqiLnm + ", " : "")
				+ (ruleRelId != null ? "ruleRelId=" + ruleRelId + ", " : "")
				+ (prfKndCd != null ? "prfKndCd=" + prfKndCd + ", " : "")
				+ (prfKndCdNm != null ? "prfKndCdNm=" + prfKndCdNm + ", " : "")
				+ (dbConnTrgId != null ? "dbConnTrgId=" + dbConnTrgId + ", " : "")
				+ (dbSchId != null ? "dbSchId=" + dbSchId + ", " : "")
				+ (dbcTblNm != null ? "dbcTblNm=" + dbcTblNm + ", " : "")
				+ (dbcTblKorNm != null ? "dbcTblKorNm=" + dbcTblKorNm + ", " : "")
				+ (dbcColNm != null ? "dbcColNm=" + dbcColNm + ", " : "")
				+ (dbcColKorNm != null ? "dbcColKorNm=" + dbcColKorNm + ", " : "")
				+ (adtCndSql != null ? "adtCndSql=" + adtCndSql + ", " : "")
				+ (addCnd != null ? "addCnd=" + addCnd + ", " : "") + (hntSql != null ? "hntSql=" + hntSql + ", " : "")
				+ (useYn != null ? "useYn=" + useYn + ", " : "")
				+ (tblColGb != null ? "tblColGb=" + tblColGb + ", " : "")
				+ (dbmsTypCd != null ? "dbmsTypCd=" + dbmsTypCd + ", " : "")
				+ (dbConnTrgPnm != null ? "dbConnTrgPnm=" + dbConnTrgPnm + ", " : "")
				+ (dbSchPnm != null ? "dbSchPnm=" + dbSchPnm + ", " : "")
				+ (cdSql != null ? "cdSql=" + cdSql + ", " : "")
				+ (cdClsColNm != null ? "cdClsColNm=" + cdClsColNm + ", " : "")
				+ (cdClsNmColNm != null ? "cdClsNmColNm=" + cdClsNmColNm + ", " : "")
				+ (cdIdColNm != null ? "cdIdColNm=" + cdIdColNm + ", " : "")
				+ (cdNmColNm != null ? "cdNmColNm=" + cdNmColNm + ", " : "")
				+ (shdJobId != null ? "shdJobId=" + shdJobId + ", " : "")
				+ (shdKndCd != null ? "shdKndCd=" + shdKndCd + ", " : "")
				+ (etcJobNm != null ? "etcJobNm=" + etcJobNm + ", " : "")
				+ (shdJobNm != null ? "shdJobNm=" + shdJobNm + ", " : "")
				+ (wamPrfColAnaVO != null ? "wamPrfColAnaVO=" + wamPrfColAnaVO + ", " : "")
				+ (wamPrfEfvaAnaVO != null ? "wamPrfEfvaAnaVO=" + wamPrfEfvaAnaVO + ", " : "")
				+ (wamPrfDtfrmAnaVO != null ? "wamPrfDtfrmAnaVO=" + wamPrfDtfrmAnaVO + ", " : "")
				+ (wamPrfRngAnaVO != null ? "wamPrfRngAnaVO=" + wamPrfRngAnaVO + ", " : "")
				+ (wamPrfPtrAnaVO != null ? "wamPrfPtrAnaVO=" + wamPrfPtrAnaVO + ", " : "")
				+ (wamPrfRelTblVO != null ? "wamPrfRelTblVO=" + wamPrfRelTblVO + ", " : "")
				+ (wamPrfRelColVO != null ? "wamPrfRelColVO=" + wamPrfRelColVO + ", " : "")
				+ (wamPrfUnqColVO != null ? "wamPrfUnqColVO=" + wamPrfUnqColVO : "") + "]";
	}

	public String getCdSql() {
		return cdSql;
	}

	public void setCdSql(String cdSql) {
		this.cdSql = cdSql;
	}

	public String getCdClsColNm() {
		return cdClsColNm;
	}

	public void setCdClsColNm(String cdClsColNm) {
		this.cdClsColNm = cdClsColNm;
	}

	public String getCdClsNmColNm() {
		return cdClsNmColNm;
	}

	public void setCdClsNmColNm(String cdClsNmColNm) {
		this.cdClsNmColNm = cdClsNmColNm;
	}

	public String getCdIdColNm() {
		return cdIdColNm;
	}

	public void setCdIdColNm(String cdIdColNm) {
		this.cdIdColNm = cdIdColNm;
	}

	public String getCdNmColNm() {
		return cdNmColNm;
	}

	public void setCdNmColNm(String cdNmColNm) {
		this.cdNmColNm = cdNmColNm;
	}

	//컬럼분석
    private WamPrfColAnaVO  wamPrfColAnaVO;
    
    //유효값분석
    private WamPrfEfvaAnaVO  wamPrfEfvaAnaVO;
    
    //날짜형식분석
    private WamPrfDtfrmAnaVO   wamPrfDtfrmAnaVO;
    
    //범위분석
    private WamPrfRngAnaVO   wamPrfRngAnaVO;
    
    //패턴분석
    private ArrayList<WamPrfPtrAnaVO> wamPrfPtrAnaVO;
    
    //관계분석
    private WamPrfRelTblVO wamPrfRelTblVO;
    //관계분석 컬럼
    private ArrayList<WamPrfRelColVO> wamPrfRelColVO;
    
    //중복분석
    private ArrayList<WamPrfUnqColVO> wamPrfUnqColVO;

	public String getAddCnd() {
		return addCnd;
	}

	public void setAddCnd(String addCnd) {
		this.addCnd = addCnd;
	}

	public String getVrfcId() {
		return vrfcId;
	}

	public void setVrfcId(String vrfcId) {
		this.vrfcId = vrfcId;
	}

	public String getVrfcNm() {
		return vrfcNm;
	}

	public void setVrfcNm(String vrfcNm) {
		this.vrfcNm = vrfcNm;
	}

	public String getVrfcTyp() {
		return vrfcTyp;
	}

	public void setVrfcTyp(String vrfcTyp) {
		this.vrfcTyp = vrfcTyp;
	}

	public String getVrfcRule() {
		return vrfcRule;
	}

	public void setVrfcRule(String vrfcRule) {
		this.vrfcRule = vrfcRule;
	}

	public String getVrfcDescn() {
		return vrfcDescn;
	}

	public void setVrfcDescn(String vrfcDescn) {
		this.vrfcDescn = vrfcDescn;
	}

	public String getDqiId() {
		return dqiId;
	}

	public void setDqiId(String dqiId) {
		this.dqiId = dqiId;
	}

	public String getDqiLnm() {
		return dqiLnm;
	}

	public void setDqiLnm(String dqiLnm) {
		this.dqiLnm = dqiLnm;
	}

	public String getRuleRelId() {
		return ruleRelId;
	}

	public void setRuleRelId(String ruleRelId) {
		this.ruleRelId = ruleRelId;
	}

	public String getPrfKndCd() {
		return prfKndCd;
	}

	public void setPrfKndCd(String prfKndCd) {
		this.prfKndCd = prfKndCd;
	}

	public String getPrfKndCdNm() {
		return prfKndCdNm;
	}

	public void setPrfKndCdNm(String prfKndCdNm) {
		this.prfKndCdNm = prfKndCdNm;
	}

	public String getDbConnTrgId() {
		return dbConnTrgId;
	}

	public void setDbConnTrgId(String dbConnTrgId) {
		this.dbConnTrgId = dbConnTrgId;
	}

	public String getDbSchId() {
		return dbSchId;
	}

	public void setDbSchId(String dbSchId) {
		this.dbSchId = dbSchId;
	}

	public String getDbcTblNm() {
		return dbcTblNm;
	}

	public void setDbcTblNm(String dbcTblNm) {
		this.dbcTblNm = dbcTblNm;
	}

	public String getDbcColNm() {
		return dbcColNm;
	}

	public void setDbcColNm(String dbcColNm) {
		this.dbcColNm = dbcColNm;
	}

	public String getAdtCndSql() {
		return adtCndSql;
	}

	public void setAdtCndSql(String adtCndSql) {
		this.adtCndSql = adtCndSql;
	}

	public String getHntSql() {
		return hntSql;
	}

	public void setHntSql(String hntSql) {
		this.hntSql = hntSql;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getTblColGb() {
		return tblColGb;
	}

	public void setTblColGb(String tblColGb) {
		this.tblColGb = tblColGb;
	}

	public String getDbmsTypCd() {
		return dbmsTypCd;
	}

	public void setDbmsTypCd(String dbmsTypCd) {
		this.dbmsTypCd = dbmsTypCd;
	}

	public String getDbConnTrgPnm() {
		return dbConnTrgPnm;
	}

	public void setDbConnTrgPnm(String dbConnTrgPnm) {
		this.dbConnTrgPnm = dbConnTrgPnm;
	}

	public String getDbSchPnm() {
		return dbSchPnm;
	}

	public void setDbSchPnm(String dbSchPnm) {
		this.dbSchPnm = dbSchPnm;
	}

	public WamPrfColAnaVO getWamPrfColAnaVO() {
		return wamPrfColAnaVO;
	}

	public void setWamPrfColAnaVO(WamPrfColAnaVO wamPrfColAnaVO) {
		this.wamPrfColAnaVO = wamPrfColAnaVO;
	}

	public WamPrfEfvaAnaVO getWamPrfEfvaAnaVO() {
		return wamPrfEfvaAnaVO;
	}

	public void setWamPrfEfvaAnaVO(WamPrfEfvaAnaVO wamPrfEfvaAnaVO) {
		this.wamPrfEfvaAnaVO = wamPrfEfvaAnaVO;
	}

	public WamPrfDtfrmAnaVO getWamPrfDtfrmAnaVO() {
		return wamPrfDtfrmAnaVO;
	}

	public void setWamPrfDtfrmAnaVO(WamPrfDtfrmAnaVO wamPrfDtfrmAnaVO) {
		this.wamPrfDtfrmAnaVO = wamPrfDtfrmAnaVO;
	}

	public WamPrfRngAnaVO getWamPrfRngAnaVO() {
		return wamPrfRngAnaVO;
	}

	public void setWamPrfRngAnaVO(WamPrfRngAnaVO wamPrfRngAnaVO) {
		this.wamPrfRngAnaVO = wamPrfRngAnaVO;
	}

	public ArrayList<WamPrfPtrAnaVO> getWamPrfPtrAnaVO() {
		return wamPrfPtrAnaVO;
	}

	public void setWamPrfPtrAnaVO(ArrayList<WamPrfPtrAnaVO> wamPrfPtrAnaVO) {
		this.wamPrfPtrAnaVO = wamPrfPtrAnaVO;
	}

	public WamPrfRelTblVO getWamPrfRelTblVO() {
		return wamPrfRelTblVO;
	}

	public void setWamPrfRelTblVO(WamPrfRelTblVO wamPrfRelTblVO) {
		this.wamPrfRelTblVO = wamPrfRelTblVO;
	}

	public ArrayList<WamPrfRelColVO> getWamPrfRelColVO() {
		return wamPrfRelColVO;
	}

	public void setWamPrfRelColVO(ArrayList<WamPrfRelColVO> wamPrfRelColVO) {
		this.wamPrfRelColVO = wamPrfRelColVO;
	}

	public ArrayList<WamPrfUnqColVO> getWamPrfUnqColVO() {
		return wamPrfUnqColVO;
	}

	public void setWamPrfUnqColVO(ArrayList<WamPrfUnqColVO> wamPrfUnqColVO) {
		this.wamPrfUnqColVO = wamPrfUnqColVO;
	}

	public String getDbcTblKorNm() {
		return dbcTblKorNm;
	}

	public void setDbcTblKorNm(String dbcTblKorNm) {
		this.dbcTblKorNm = dbcTblKorNm;
	}

	public String getDbcColKorNm() {
		return dbcColKorNm;
	}

	public void setDbcColKorNm(String dbcColKorNm) {
		this.dbcColKorNm = dbcColKorNm;
	}

	public String getShdJobId() {
		return shdJobId;
	}

	public void setShdJobId(String shdJobId) {
		this.shdJobId = shdJobId;
	}

	public String getShdKndCd() {
		return shdKndCd;
	}

	public void setShdKndCd(String shdKndCd) {
		this.shdKndCd = shdKndCd;
	}
    
	public String getEtcJobNm() {
		return etcJobNm;
	}

	public void setEtcJobNm(String etcJobNm) {
		this.etcJobNm = etcJobNm;
	}

	public String getShdJobNm() {
		return shdJobNm;
	}

	public void setShdJobNm(String shdJobNm) {
		this.shdJobNm = shdJobNm;
	}
	
	
    
}





