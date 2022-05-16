package kr.wise.dq.bizrule.service;

import java.util.Date;

import kr.wise.commons.cmm.CommonVo;

public class WaqBrMstr extends CommonVo {
//    private String rqstNo;
//
//    private Integer rqstSno;

    private String brId;

    private String brNm;

    private String dbConnTrgId;

    private String dbConnTrgPnm;

    private String dbSchId;

    private String dbSchPnm;

    private String dbcTblNm;

    private String dbcColNm;

    private String bizAreaId;

    private String bizAreaLnm;

    private String brCrgpUserId;

    private String brCrgpUserNm;

    private Short agrNv;

    private Short glNv;

    private String useYn;

    private String tgtDbConnTrgId;

    private String tgtDbConnTrgPnm;

    private String tgtDbSchId;

    private String tgtDbSchPnm;

    private String tgtDbcTblNm;

    private String tgtDbcColNm;

    private String tgtKeyDbcColNm;

    private String tgtKeyDbcColVal;
    
    private String tgtVrfJoinCd;

//    private String rqstDcd;
//
//    private String rvwStsCd;
//
//    private String rvwConts;
//
//    private String vrfCd;
//
//    private String vrfRmk;

//    private String objDescn;
//
//    private Integer objVers;
//
//    private String regTypCd;
//
//    private Date frsRqstDtm;
//
//    private String frsRqstUserId;
//
//    private Date rqstDtm;
//
//    private String rqstUserId;
//
//    private Date aprvDtm;
//
//    private String aprvUserId;

    private String cntSql;

    private String erCntSql;

    private String anaSql;

    private String tgtCntSql;

    private String tgtErCntSql;

    private String tgtAnaSql;
    
    private String dqiId;
    
    private String dqiLnm;
    
    private String ctqId;
    
    private String ctqLnm;
    
    

    /**
	 * @return the dqiId
	 */
	public String getDqiId() {
		return dqiId;
	}

	/**
	 * @param dqiId the dqiId to set
	 */
	public void setDqiId(String dqiId) {
		this.dqiId = dqiId;
	}

	

	/**
	 * @return the dqiLnm
	 */
	public String getDqiLnm() {
		return dqiLnm;
	}

	/**
	 * @param dqiLnm the dqiLnm to set
	 */
	public void setDqiLnm(String dqiLnm) {
		this.dqiLnm = dqiLnm;
	}

	/**
	 * @return the ctqId
	 */
	public String getCtqId() {
		return ctqId;
	}

	/**
	 * @param ctqId the ctqId to set
	 */
	public void setCtqId(String ctqId) {
		this.ctqId = ctqId;
	}

	/**
	 * @return the ctqLnm
	 */
	public String getCtqLnm() {
		return ctqLnm;
	}

	/**
	 * @param ctqLnm the ctqLnm to set
	 */
	public void setCtqLnm(String ctqLnm) {
		this.ctqLnm = ctqLnm;
	}

//	public String getRqstNo() {
//        return rqstNo;
//    }
//
//    public void setRqstNo(String rqstNo) {
//        this.rqstNo = rqstNo;
//    }
//
//    public Integer getRqstSno() {
//        return rqstSno;
//    }
//
//    public void setRqstSno(Integer rqstSno) {
//        this.rqstSno = rqstSno;
//    }

    public String getBrId() {
        return brId;
    }

    public void setBrId(String brId) {
        this.brId = brId;
    }

    public String getBrNm() {
        return brNm;
    }

    public void setBrNm(String brNm) {
        this.brNm = brNm;
    }

    public String getDbConnTrgId() {
        return dbConnTrgId;
    }

    public void setDbConnTrgId(String dbConnTrgId) {
        this.dbConnTrgId = dbConnTrgId;
    }

    public String getDbConnTrgPnm() {
        return dbConnTrgPnm;
    }

    public void setDbConnTrgPnm(String dbConnTrgPnm) {
        this.dbConnTrgPnm = dbConnTrgPnm;
    }

    public String getDbSchId() {
        return dbSchId;
    }

    public void setDbSchId(String dbSchId) {
        this.dbSchId = dbSchId;
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

    public String getBizAreaId() {
        return bizAreaId;
    }

    public void setBizAreaId(String bizAreaId) {
        this.bizAreaId = bizAreaId;
    }

    public String getBizAreaLnm() {
        return bizAreaLnm;
    }

    public void setBizAreaLnm(String bizAreaLnm) {
        this.bizAreaLnm = bizAreaLnm;
    }

    public String getBrCrgpUserId() {
        return brCrgpUserId;
    }

    public void setBrCrgpUserId(String brCrgpUserId) {
        this.brCrgpUserId = brCrgpUserId;
    }

    public String getBrCrgpUserNm() {
        return brCrgpUserNm;
    }

    public void setBrCrgpUserNm(String brCrgpUserNm) {
        this.brCrgpUserNm = brCrgpUserNm;
    }

    public Short getAgrNv() {
        return agrNv;
    }

    public void setAgrNv(Short agrNv) {
        this.agrNv = agrNv;
    }

    public Short getGlNv() {
        return glNv;
    }

    public void setGlNv(Short glNv) {
        this.glNv = glNv;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getTgtDbConnTrgId() {
        return tgtDbConnTrgId;
    }

    public void setTgtDbConnTrgId(String tgtDbConnTrgId) {
        this.tgtDbConnTrgId = tgtDbConnTrgId;
    }

    public String getTgtDbConnTrgPnm() {
        return tgtDbConnTrgPnm;
    }

    public void setTgtDbConnTrgPnm(String tgtDbConnTrgPnm) {
        this.tgtDbConnTrgPnm = tgtDbConnTrgPnm;
    }

    public String getTgtDbSchId() {
        return tgtDbSchId;
    }

    public void setTgtDbSchId(String tgtDbSchId) {
        this.tgtDbSchId = tgtDbSchId;
    }

    public String getTgtDbSchPnm() {
        return tgtDbSchPnm;
    }

    public void setTgtDbSchPnm(String tgtDbSchPnm) {
        this.tgtDbSchPnm = tgtDbSchPnm;
    }

    public String getTgtDbcTblNm() {
        return tgtDbcTblNm;
    }

    public void setTgtDbcTblNm(String tgtDbcTblNm) {
        this.tgtDbcTblNm = tgtDbcTblNm;
    }

    public String getTgtDbcColNm() {
        return tgtDbcColNm;
    }

    public void setTgtDbcColNm(String tgtDbcColNm) {
        this.tgtDbcColNm = tgtDbcColNm;
    }

    public String getTgtKeyDbcColNm() {
        return tgtKeyDbcColNm;
    }

    public void setTgtKeyDbcColNm(String tgtKeyDbcColNm) {
        this.tgtKeyDbcColNm = tgtKeyDbcColNm;
    }

    public String getTgtKeyDbcColVal() {
        return tgtKeyDbcColVal;
    }

    public void setTgtKeyDbcColVal(String tgtKeyDbcColVal) {
        this.tgtKeyDbcColVal = tgtKeyDbcColVal;
    }

//    public String getRqstDcd() {
//        return rqstDcd;
//    }
//
//    public void setRqstDcd(String rqstDcd) {
//        this.rqstDcd = rqstDcd;
//    }
//
//    public String getRvwStsCd() {
//        return rvwStsCd;
//    }
//
//    public void setRvwStsCd(String rvwStsCd) {
//        this.rvwStsCd = rvwStsCd;
//    }
//
//    public String getRvwConts() {
//        return rvwConts;
//    }
//
//    public void setRvwConts(String rvwConts) {
//        this.rvwConts = rvwConts;
//    }
//
//    public String getVrfCd() {
//        return vrfCd;
//    }
//
//    public void setVrfCd(String vrfCd) {
//        this.vrfCd = vrfCd;
//    }
//
//    public String getVrfRmk() {
//        return vrfRmk;
//    }
//
//    public void setVrfRmk(String vrfRmk) {
//        this.vrfRmk = vrfRmk;
//    }
//
//    public String getObjDescn() {
//        return objDescn;
//    }
//
//    public void setObjDescn(String objDescn) {
//        this.objDescn = objDescn;
//    }
//
//    public Integer getObjVers() {
//        return objVers;
//    }
//
//    public void setObjVers(Integer objVers) {
//        this.objVers = objVers;
//    }
//
//    public String getRegTypCd() {
//        return regTypCd;
//    }
//
//    public void setRegTypCd(String regTypCd) {
//        this.regTypCd = regTypCd;
//    }
//
//    public Date getFrsRqstDtm() {
//        return frsRqstDtm;
//    }
//
//    public void setFrsRqstDtm(Date frsRqstDtm) {
//        this.frsRqstDtm = frsRqstDtm;
//    }
//
//    public String getFrsRqstUserId() {
//        return frsRqstUserId;
//    }
//
//    public void setFrsRqstUserId(String frsRqstUserId) {
//        this.frsRqstUserId = frsRqstUserId;
//    }
//
//    public Date getRqstDtm() {
//        return rqstDtm;
//    }
//
//    public void setRqstDtm(Date rqstDtm) {
//        this.rqstDtm = rqstDtm;
//    }
//
//    public String getRqstUserId() {
//        return rqstUserId;
//    }
//
//    public void setRqstUserId(String rqstUserId) {
//        this.rqstUserId = rqstUserId;
//    }
//
//    public Date getAprvDtm() {
//        return aprvDtm;
//    }
//
//    public void setAprvDtm(Date aprvDtm) {
//        this.aprvDtm = aprvDtm;
//    }
//
//    public String getAprvUserId() {
//        return aprvUserId;
//    }
//
//    public void setAprvUserId(String aprvUserId) {
//        this.aprvUserId = aprvUserId;
//    }

    public String getCntSql() {
        return cntSql;
    }

    public void setCntSql(String cntSql) {
        this.cntSql = cntSql;
    }

    public String getErCntSql() {
        return erCntSql;
    }

    public void setErCntSql(String erCntSql) {
        this.erCntSql = erCntSql;
    }

    public String getAnaSql() {
        return anaSql;
    }

    public void setAnaSql(String anaSql) {
        this.anaSql = anaSql;
    }

    public String getTgtCntSql() {
        return tgtCntSql;
    }

    public void setTgtCntSql(String tgtCntSql) {
        this.tgtCntSql = tgtCntSql;
    }

    public String getTgtErCntSql() {
        return tgtErCntSql;
    }

    public void setTgtErCntSql(String tgtErCntSql) {
        this.tgtErCntSql = tgtErCntSql;
    }

    public String getTgtAnaSql() {
        return tgtAnaSql;
    }

    public void setTgtAnaSql(String tgtAnaSql) {
        this.tgtAnaSql = tgtAnaSql;
    }

    public String getTgtVrfJoinCd() {
		return tgtVrfJoinCd;
	}

	public void setTgtVrfJoinCd(String tgtVrfJoinCd) {
		this.tgtVrfJoinCd = tgtVrfJoinCd;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaqBrMstr [brId=").append(brId).append(", brNm=")
				.append(brNm).append(", dbConnTrgId=").append(dbConnTrgId)
				.append(", dbConnTrgPnm=").append(dbConnTrgPnm)
				.append(", dbSchId=").append(dbSchId).append(", dbSchPnm=")
				.append(dbSchPnm).append(", dbcTblNm=").append(dbcTblNm)
				.append(", dbcColNm=").append(dbcColNm).append(", bizAreaId=")
				.append(bizAreaId).append(", bizAreaLnm=").append(bizAreaLnm)
				.append(", brCrgpUserId=").append(brCrgpUserId)
				.append(", brCrgpUserNm=").append(brCrgpUserNm)
				.append(", agrNv=").append(agrNv).append(", glNv=")
				.append(glNv).append(", useYn=").append(useYn)
				.append(", tgtDbConnTrgId=").append(tgtDbConnTrgId)
				.append(", tgtDbConnTrgPnm=").append(tgtDbConnTrgPnm)
				.append(", tgtDbSchId=").append(tgtDbSchId)
				.append(", tgtDbSchPnm=").append(tgtDbSchPnm)
				.append(", tgtDbcTblNm=").append(tgtDbcTblNm)
				.append(", tgtDbcColNm=").append(tgtDbcColNm)
				.append(", tgtKeyDbcColNm=").append(tgtKeyDbcColNm)
				.append(", tgtKeyDbcColVal=").append(tgtKeyDbcColVal)
				.append(", cntSql=").append(cntSql).append(", erCntSql=")
				.append(erCntSql).append(", anaSql=").append(anaSql)
				.append(", tgtCntSql=").append(tgtCntSql)
				.append(", tgtErCntSql=").append(tgtErCntSql)
				.append(", tgtAnaSql=").append(tgtAnaSql).append(", dqiId=")
				.append(dqiId).append(", dqiLnm=").append(dqiLnm)
				.append(", ctqId=").append(ctqId).append(", ctqLnm=")
				.append(ctqLnm).append("]");
		return builder.toString() + super.toString();
	}
    
    
}