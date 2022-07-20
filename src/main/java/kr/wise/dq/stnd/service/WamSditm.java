package kr.wise.dq.stnd.service;

import kr.wise.commons.cmm.CommonVo;

public class WamSditm extends CommonVo {
    private String sditmId;

    private String sditmLnm;

    private String sditmPnm;
    
    private String notsditmLnm;
    
    private String notsditmPnm;

    private String lnmCriDs;

    private String pnmCriDs;

	private String dmnId;

    private String dmngId;
    
    private String dmngLnm;

    private String infotpId;
    
    private String infotpLnm;

    private String infotpChgYn;

    private String encYn;

    private String stndInfoYn;
    
    private String bzwkFld;
    private String ownrOrg;
    private String admnStndCd;
    
    private String saveFrm;
    private String exprsnFrm;
    private String unit;
    
    private String alwVal;
    
    
//    private String rqstNo;
//
//    private Integer rqstSno;
//
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
    
//    private String dataLen;
//    
//    private String dataScal;
    
    private Integer dataLen;

    private Integer dataScal;
    
    private String stndCd;
    private String spclNt;
    private String bsnssFld;
    private String pnm;
    
    private String userId;
    private String usergId;
    
    private Integer seq;

	private String errChk;

	public String getErrChk() {
		return errChk;
	}

	public void setErrChk(String errChk) {
		this.errChk = errChk;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getUsergId() {
		return usergId;
	}

	public void setUsergId(String usergId) {
		this.usergId = usergId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPnm() {
		return pnm;
	}

	public void setPnm(String pnm) {
		this.pnm = pnm;
	}

	public String getStndCd() {
		return stndCd;
	}

	public void setStndCd(String stndCd) {
		this.stndCd = stndCd;
	}

	public String getSpclNt() {
		return spclNt;
	}

	public void setSpclNt(String spclNt) {
		this.spclNt = spclNt;
	}

	public String getBsnssFld() {
		return bsnssFld;
	}

	public void setBsnssFld(String bsnssFld) {
		this.bsnssFld = bsnssFld;
	}
    
    public Integer getDataLen() {
		return dataLen;
	}

	public void setDataLen(Integer dataLen) {
		this.dataLen = dataLen;
	}

	public Integer getDataScal() {
		return dataScal;
	}

	public void setDataScal(Integer dataScal) {
		this.dataScal = dataScal;
	}

	private String dataType;
    
    private String dmnPnm;
    
    private String dmnLnm;
    
//    private String frsRqstUserNm;
//    
//    private String rqstUserNm;
//    
//    private String aprvUserNm;
    
    private String uppDmngId;
    
    private String uppDmngLnm;
    
    
    private String fullEngMean;
    
    private String transYn;
    
    private String reqStr;
    
    private String testDataCnvYn;

    private String persInfoCnvYn;
    
    private String persInfoGrd;
    
    private String oraDataType;

    private String msDataType;
    
    private String myDataType;
    
    private String dupYn;
    
    private String persInfoYn;
    
    
    
    public String getSaveFrm() {
		return saveFrm;
	}

	public void setSaveFrm(String saveFrm) {
		this.saveFrm = saveFrm;
	}

	public String getExprsnFrm() {
		return exprsnFrm;
	}

	public void setExprsnFrm(String exprsnFrm) {
		this.exprsnFrm = exprsnFrm;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPersInfoYn() {
		return persInfoYn;
	}

	public void setPersInfoYn(String persInfoYn) {
		this.persInfoYn = persInfoYn;
	}

	public String getDupYn() {
		return dupYn;
	}

	public void setDupYn(String dupYn) {
		this.dupYn = dupYn;
	}

	public String getOraDataType() {
		return oraDataType;
	}

	public void setOraDataType(String oraDataType) {
		this.oraDataType = oraDataType;
	}

	public String getMsDataType() {
		return msDataType;
	}

	public void setMsDataType(String msDataType) {
		this.msDataType = msDataType;
	}

	public String getMyDataType() {
		return myDataType;
	}

	public void setMyDataType(String myDataType) {
		this.myDataType = myDataType;
	}

	public String getPersInfoCnvYn() {
		return persInfoCnvYn;
	}

	public void setPersInfoCnvYn(String persInfoCnvYn) {
		this.persInfoCnvYn = persInfoCnvYn;
	}

	public String getPersInfoGrd() {
		return persInfoGrd;
	}

	public void setPersInfoGrd(String persInfoGrd) {
		this.persInfoGrd = persInfoGrd;
	}

	public String getTransYn() {
		return transYn;
	}

	public void setTransYn(String transYn) {
		this.transYn = transYn;
	}

	public String getPnmCriDs() {
		return pnmCriDs;
	}

	public void setPnmCriDs(String pnmCriDs) {
		this.pnmCriDs = pnmCriDs;
	}
  
    
//    public String getFrsRqstUserNm() {
//		return frsRqstUserNm;
//	}
//
//	public void setFrsRqstUserNm(String frsRqstUserNm) {
//		this.frsRqstUserNm = frsRqstUserNm;
//	}
//
//	public String getRqstUserNm() {
//		return rqstUserNm;
//	}
//
//	public void setRqstUserNm(String rqstUserNm) {
//		this.rqstUserNm = rqstUserNm;
//	}
//
//	public String getAprvUserNm() {
//		return aprvUserNm;
//	}

//	public void setAprvUserNm(String aprvUserNm) {
//		this.aprvUserNm = aprvUserNm;
//	}

//	public String getDataLen() {
//		return dataLen;
//	}

	public String getUppDmngId() {
		return uppDmngId;
	}

	public void setUppDmngId(String uppDmngId) {
		this.uppDmngId = uppDmngId;
	}

	public String getUppDmngLnm() {
		return uppDmngLnm;
	}

	public void setUppDmngLnm(String uppDmngLnm) {
		this.uppDmngLnm = uppDmngLnm;
	}

//	public void setDataLen(String dataLen) {
//		this.dataLen = dataLen;
//	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDmnPnm() {
		return dmnPnm;
	}

	public void setDmnPnm(String dmnPnm) {
		this.dmnPnm = dmnPnm;
	}

	public String getDmnLnm() {
		return dmnLnm;
	}

	public void setDmnLnm(String dmnLnm) {
		this.dmnLnm = dmnLnm;
	}

	public String getSditmId() {
        return sditmId;
    }

    public void setSditmId(String sditmId) {
        this.sditmId = sditmId;
    }

    public String getSditmLnm() {
        return sditmLnm;
    }

    public void setSditmLnm(String sditmLnm) {
        this.sditmLnm = sditmLnm;
    }

    public String getSditmPnm() {
        return sditmPnm;
    }

    public void setSditmPnm(String sditmPnm) {
        this.sditmPnm = sditmPnm;
    }

    public String getLnmCriDs() {
        return lnmCriDs;
    }

    public void setLnmCriDs(String lnmCriDs) {
        this.lnmCriDs = lnmCriDs;
    }

    public String getDmnId() {
        return dmnId;
    }

    public void setDmnId(String dmnId) {
        this.dmnId = dmnId;
    }

    public String getDmngId() {
        return dmngId;
    }

    public void setDmngId(String dmngId) {
        this.dmngId = dmngId;
    }

    public String getInfotpId() {
        return infotpId;
    }

    public void setInfotpId(String infotpId) {
        this.infotpId = infotpId;
    }

    public String getInfotpChgYn() {
        return infotpChgYn;
    }

    public void setInfotpChgYn(String infotpChgYn) {
        this.infotpChgYn = infotpChgYn;
    }

    public String getEncYn() {
        return encYn;
    }

    public void setEncYn(String encYn) {
        this.encYn = encYn;
    }

//	public String getDataScal() {
//		return dataScal;
//	}

//	public void setDataScal(String dataScal) {
//		this.dataScal = dataScal;
//	}

	public String getFullEngMean() {
		return fullEngMean;
	}

	public void setFullEngMean(String fullEngMean) {
		this.fullEngMean = fullEngMean;
	}

	public String getDmngLnm() {
		return dmngLnm;
	}

	public void setDmngLnm(String dmngLnm) {
		this.dmngLnm = dmngLnm;
	}

	public String getInfotpLnm() {
		return infotpLnm;
	}

	public void setInfotpLnm(String infotpLnm) {
		this.infotpLnm = infotpLnm;
	}
	
	
	public String getReqStr() {
		return reqStr;
	}

	public void setReqStr(String reqStr) {
		this.reqStr = reqStr;
	}

	public String getNotsditmLnm() {
		return notsditmLnm;
	}

	public void setNotsditmLnm(String notsditmLnm) {
		this.notsditmLnm = notsditmLnm;
	}

	public String getNotsditmPnm() {
		return notsditmPnm;
	}

	public void setNotsditmPnm(String notsditmPnm) {
		this.notsditmPnm = notsditmPnm;
	}

	public String getStndInfoYn() {
		return stndInfoYn;
	}

	public void setStndInfoYn(String stndInfoYn) {
		this.stndInfoYn = stndInfoYn;
	}

	public String getTestDataCnvYn() {
		return testDataCnvYn;
	}

	public void setTestDataCnvYn(String testDataCnvYn) {
		this.testDataCnvYn = testDataCnvYn;
	}

	@Override
	public String toString() {
		return "WamSditm [sditmId=" + sditmId + ", sditmLnm=" + sditmLnm
				+ ", sditmPnm=" + sditmPnm + ", notsditmLnm=" + notsditmLnm
				+ ", notsditmPnm=" + notsditmPnm + ", lnmCriDs=" + lnmCriDs
				+ ", pnmCriDs=" + pnmCriDs + ", dmnId=" + dmnId + ", dmngId="
				+ dmngId + ", dmngLnm=" + dmngLnm + ", infotpId=" + infotpId
				+ ", infotpLnm=" + infotpLnm + ", infotpChgYn=" + infotpChgYn
				+ ", encYn=" + encYn + ", stndInfoYn=" + stndInfoYn
				+ ", dataLen=" + dataLen + ", dataScal=" + dataScal
				+ ", dataType=" + dataType + ", dmnPnm=" + dmnPnm + ", dmnLnm="
				+ dmnLnm + ", uppDmngId=" + uppDmngId + ", uppDmngLnm="
				+ uppDmngLnm + ", fullEngMean=" + fullEngMean + ", transYn="
				+ transYn + ", reqStr=" + reqStr + ", testDataCnvYn="
				+ testDataCnvYn + "]";
	}

	public String getBzwkFld() {
		return bzwkFld;
	}

	public void setBzwkFld(String bzwkFld) {
		this.bzwkFld = bzwkFld;
	}

	public String getOwnrOrg() {
		return ownrOrg;
	}

	public void setOwnrOrg(String ownrOrg) {
		this.ownrOrg = ownrOrg;
	}

	public String getAdmnStndCd() {
		return admnStndCd;
	}

	public void setAdmnStndCd(String admnStndCd) {
		this.admnStndCd = admnStndCd;
	}

	public String getAlwVal() {
		return alwVal;
	}

	public void setAlwVal(String alwVal) {
		this.alwVal = alwVal;
	}

    
}