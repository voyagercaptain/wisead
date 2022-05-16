package kr.wise.dq.result.service;

import java.math.BigDecimal;
import java.util.List;

import kr.wise.commons.cmm.CommonVo;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : ProgChartVO.java
 * 3. Package  : kr.wise.dq.report.profile.service
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 6. 18. 오후 3:27:30
 * </PRE>
 */ 
public class ResultVO extends CommonVo{
	 
	private String uppDqiLnm; 
	
	private String dqiLnm;	
	
	private String cntName;
	
	private BigDecimal totCnt;
	
	private BigDecimal errCnt;
	
	private Float errRate;
	
	private String errSql;
	
	private BigDecimal noExe;
	
	private BigDecimal colCnt;
	
	private BigDecimal tblCnt;
	
	private Float sumRate;
	
	private String dbConnTrgId;
	
	private String dbConnTrgPnm;
	
	private String dbConnTrgLnm;
	
	private String dbSchPnm;
	
	private String dbSchLnm;
	
	private String dbcTblNm; 
	
	private String dbcTblKorNm;
	
	private String dbcColNm;
	
	private String dbcColKorNm;
	
	private String dbSchId;
	
	private String anaStrDtm;
	
	private String anaEndDtm;
	
	private String prfKndCd;
	
	private String prfTyp;
	
	private String prfNm;
	
	private String prfId;
	
	private String prfYn;
	
	private String dataType;
	
	private String expYn;
	
	private String expRsnCntn;
	
	private String stndRate;
	
	private String goal;
	
	private String errLst;
	
	private BigDecimal errColCnt;
	
	private List<ResultDataVO> errList;
	
	private List<ResultDataVO> errData;
	
	private String pcolnm;
	private String bcolnm;
	private String bcolnm2;
	
	
	public String getBcolnm2() {
		return bcolnm2;
	}

	public void setBcolnm2(String bcolnm2) {
		this.bcolnm2 = bcolnm2;
	}

	private int anaDgr;

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public String getStndRate() {
		return stndRate;
	}

	public void setStndRate(String stndRate) {
		this.stndRate = stndRate;
	}

	public String getCntName() {
		return cntName;
	}

	public void setCntName(String cntName) {
		this.cntName = cntName;
	}

	public BigDecimal getTotCnt() {
		return totCnt;
	}

	public void setTotCnt(BigDecimal totCnt) {
		this.totCnt = totCnt;
	}

	public BigDecimal getErrCnt() {
		return errCnt;
	}

	public void setErrCnt(BigDecimal errCnt) {
		this.errCnt = errCnt;
	}

	public Float getErrRate() {
		return errRate;
	}

	public void setErrRate(Float errRate) {
		this.errRate = errRate;
	}

	public BigDecimal getNoExe() {
		return noExe;
	}

	public void setNoExe(BigDecimal noExe) {
		this.noExe = noExe;
	}

	public BigDecimal getColCnt() {
		return colCnt;
	}

	public void setColCnt(BigDecimal colCnt) {
		this.colCnt = colCnt;
	}

	public BigDecimal getTblCnt() {
		return tblCnt;
	}

	public void setTblCnt(BigDecimal tblCnt) {
		this.tblCnt = tblCnt;
	}

	public Float getSumRate() {
		return sumRate;
	}

	public void setSumRate(Float sumRate) {
		this.sumRate = sumRate;
	}

	public String getDqiLnm() {
		return dqiLnm;
	}

	public void setDqiLnm(String dqiLnm) {
		this.dqiLnm = dqiLnm;
	}

	public String getDbConnTrgId() {
		return dbConnTrgId;
	}

	public void setDbConnTrgId(String dbConnTrgId) {
		this.dbConnTrgId = dbConnTrgId;
	}

	public String getDbSchPnm() {
		return dbSchPnm;
	}

	public void setDbSchPnm(String dbSchPnm) {
		this.dbSchPnm = dbSchPnm;
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

	public String getUppDqiLnm() {
		return uppDqiLnm;
	}

	public void setUppDqiLnm(String uppDqiLnm) {
		this.uppDqiLnm = uppDqiLnm;
	}

	public String getDbSchId() {
		return dbSchId;
	}

	public void setDbSchId(String dbSchId) {
		this.dbSchId = dbSchId;
	}

	public String getDbConnTrgPnm() {
		return dbConnTrgPnm;
	}

	public void setDbConnTrgPnm(String dbConnTrgPnm) {
		this.dbConnTrgPnm = dbConnTrgPnm;
	}

	public String getDbConnTrgLnm() {
		return dbConnTrgLnm;
	}

	public void setDbConnTrgLnm(String dbConnTrgLnm) {
		this.dbConnTrgLnm = dbConnTrgLnm;
	}

	public String getDbSchLnm() {
		return dbSchLnm;
	}

	public void setDbSchLnm(String dbSchLnm) {
		this.dbSchLnm = dbSchLnm;
	}

	public String getAnaStrDtm() {
		return anaStrDtm;
	}

	public void setAnaStrDtm(String anaStrDtm) {
		this.anaStrDtm = anaStrDtm;
	}

	public String getAnaEndDtm() {
		return anaEndDtm;
	}

	public void setAnaEndDtm(String anaEndDtm) {
		this.anaEndDtm = anaEndDtm;
	}

	public String getErrSql() {
		return errSql;
	}

	public void setErrSql(String errSql) {
		this.errSql = errSql;
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

	public String getPrfKndCd() {
		return prfKndCd;
	}

	public void setPrfKndCd(String prfKndCd) {
		this.prfKndCd = prfKndCd;
	}

	public String getPrfTyp() {
		return prfTyp;
	}

	public void setPrfTyp(String prfTyp) {
		this.prfTyp = prfTyp;
	}

	public String getPrfNm() {
		return prfNm;
	}

	public void setPrfNm(String prfNm) {
		this.prfNm = prfNm;
	}

	public String getPrfId() {
		return prfId;
	}

	public void setPrfId(String prfId) {
		this.prfId = prfId;
	}

	public String getPrfYn() {
		return prfYn;
	}

	public void setPrfYn(String prfYn) {
		this.prfYn = prfYn;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getExpYn() {
		return expYn;
	}

	public void setExpYn(String expYn) {
		this.expYn = expYn;
	}

	public String getExpRsnCntn() {
		return expRsnCntn;
	}

	public void setExpRsnCntn(String expRsnCntn) {
		this.expRsnCntn = expRsnCntn;
	}

	public List<ResultDataVO> getErrData() {
		return errData;
	}

	public void setErrData(List<ResultDataVO> errData) {
		this.errData = errData;
	}

	public List<ResultDataVO> getErrList() {
		return errList;
	}

	public void setErrList(List<ResultDataVO> errList) {
		this.errList = errList;
	}

	public String getPcolnm() {
		return pcolnm;
	}

	public void setPcolnm(String pcolnm) {
		this.pcolnm = pcolnm;
	}
	
	public String getBcolnm() {
		return bcolnm;
	}

	public void setBcolnm(String bcolnm) {
		this.bcolnm = bcolnm;
	}

	public int getAnaDgr() {
		return anaDgr;
	}

	public void setAnaDgr(int anaDgr) {
		this.anaDgr = anaDgr;
	}

	public String getErrLst() {
		return errLst;
	}

	public void setErrLst(String errLst) {
		this.errLst = errLst;
	}

	public BigDecimal getErrColCnt() {
		return errColCnt;
	}

	public void setErrColCnt(BigDecimal errColCnt) {
		this.errColCnt = errColCnt;
	}
	

}