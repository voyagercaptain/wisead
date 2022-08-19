package kr.wise.dq.dbstnd.service;

import kr.wise.commons.cmm.CommonVo;

public class WamDbSditm extends CommonVo {
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
    
    private String vcWh;
    
    private String decideYn;
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
    
    private Integer startNum;
    private Integer endNum;
    private String userId;
    
    private String stndCd;
    private String spclNt;
    private String bsnssFld;
    private String pnm;
    
    private String orgCd;
    private String orgNm;
    
    private String usergId;
    
    private String errChk;
    
    private String validYn;
    
    private String confirmYn;
    
    private String confirmDtm ;
        
    public String getUsergId() {
		return usergId;
	}

	public void setUsergId(String usergId) {
		this.usergId = usergId;
	}

	public String getOrgCd() {
		return orgCd;
	}

	public void setOrgCd(String orgCd) {
		this.orgCd = orgCd;
	}

	public String getOrgNm() {
		return orgNm;
	}

	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
    
    private String sditmDtm;
    
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

	public String getErrChk() {
		return errChk;
	}

	public void setErrChk(String errChk) {
		this.errChk = errChk;
	}

	public String getValidYn() {
		return validYn;
	}

	public void setValidYn(String validYn) {
		this.validYn = validYn;
	}

	public String getConfirmYn() {
		return confirmYn;
	}

	public void setConfirmYn(String confirmYn) {
		this.confirmYn = confirmYn;
	}

	public String getConfirmDtm() {
		return confirmDtm;
	}

	public void setConfirmDtm(String confirmDtm) {
		this.confirmDtm = confirmDtm;
	}

	public String getVcWh() {
		return vcWh;
	}

	public void setVcWh(String vcWh) {
		this.vcWh = vcWh;
	}
	
	
	public String getDecideYn() {
		return decideYn;
	}

	public void setDecideYn(String decideYn) {
		this.decideYn = decideYn;
	}
	
	
	public String getSditmDtm() {
		return sditmDtm;
	}

	public void setSditmDtm(String sditmDtm) {
		this.sditmDtm = sditmDtm;
	}

	public Integer getStartNum() {
		return startNum;
	}

	public void setStartNum(Integer startNum) {
		this.startNum = startNum;
	}

	public Integer getEndNum() {
		return endNum;
	}

	public void setEndNum(Integer endNum) {
		this.endNum = endNum;
	}

	@Override
	public String toString() {
		return "WamDbSditm [sditmId=" + sditmId + ", sditmLnm=" + sditmLnm + ", sditmPnm=" + sditmPnm + ", notsditmLnm="
				+ notsditmLnm + ", notsditmPnm=" + notsditmPnm + ", lnmCriDs=" + lnmCriDs + ", pnmCriDs=" + pnmCriDs
				+ ", dmnId=" + dmnId + ", dmngId=" + dmngId + ", dmngLnm=" + dmngLnm + ", infotpId=" + infotpId
				+ ", infotpLnm=" + infotpLnm + ", infotpChgYn=" + infotpChgYn + ", encYn=" + encYn + ", stndInfoYn="
				+ stndInfoYn + ", bzwkFld=" + bzwkFld + ", ownrOrg=" + ownrOrg + ", admnStndCd=" + admnStndCd
				+ ", saveFrm=" + saveFrm + ", exprsnFrm=" + exprsnFrm + ", unit=" + unit + ", alwVal=" + alwVal
				+ ", vcWh=" + vcWh + ", decideYn=" + decideYn + ", dataLen=" + dataLen + ", dataScal=" + dataScal
				+ ", startNum=" + startNum + ", endNum=" + endNum + ", userId=" + userId + ", stndCd=" + stndCd
				+ ", spclNt=" + spclNt + ", bsnssFld=" + bsnssFld + ", pnm=" + pnm + ", orgCd=" + orgCd + ", orgNm="
				+ orgNm + ", usergId=" + usergId + ", errChk=" + errChk + ", validYn=" + validYn + ", confirmYn="
				+ confirmYn + ", confirmDtm=" + confirmDtm + ", dataType=" + dataType + ", dmnPnm=" + dmnPnm
				+ ", dmnLnm=" + dmnLnm + ", uppDmngId=" + uppDmngId + ", uppDmngLnm=" + uppDmngLnm + ", fullEngMean="
				+ fullEngMean + ", transYn=" + transYn + ", reqStr=" + reqStr + ", testDataCnvYn=" + testDataCnvYn
				+ ", persInfoCnvYn=" + persInfoCnvYn + ", persInfoGrd=" + persInfoGrd + ", oraDataType=" + oraDataType
				+ ", msDataType=" + msDataType + ", myDataType=" + myDataType + ", dupYn=" + dupYn + ", persInfoYn="
				+ persInfoYn + ", sditmDtm=" + sditmDtm + "]";
	}


	

    
}