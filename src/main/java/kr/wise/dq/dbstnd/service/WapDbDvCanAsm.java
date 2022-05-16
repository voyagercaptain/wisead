package kr.wise.dq.dbstnd.service;

import kr.wise.commons.damgmt.dmnginfo.service.WaaInfoType;

public class WapDbDvCanAsm extends WaaInfoType {
    private String dvRqstNo;

    private String dicAsmLnm;

    private String dicAsmPnm;

    private String dmnLnm;

    private String dmnPnm;

    private String dmngLnm;
    private String dmngPnm;
    private String dmngId;
    
    private String infotpLnm;
    private String infotpId;
    
    
    private String dicAsmDsLnm;

    private String dicAsmDsPnm;

    private String dvTrgLnm;
    
    private String dvOrgLnm;
    
    private String dvOrgDbNm;
    
    private String dvEncYn;
    
    private String dvObjDescn;
    
    private String dvRqstUserId;

    private String prcCd;
    
    private String regPosYn;

    private String trgLnm;
    
    private String dvRqstRes;
    
    private String dvSeCd;
    
    private String sditmAutoCrtYn;
	
    private String cdValTypCd;   	    	 
	
    private String cdValIvwCd;
    
    private String stndAsrt; //표준분류
    
    private String persInfoCnvYn;
    
    private String persInfoGrd;

    private String dmnYn;
    
    
    
	public String getDvOrgDbNm() {
		return dvOrgDbNm;
	}

	public void setDvOrgDbNm(String dvOrgDbNm) {
		this.dvOrgDbNm = dvOrgDbNm;
	}

	public String getDmnYn() {
		return dmnYn;
	}

	public void setDmnYn(String dmnYn) {
		this.dmnYn = dmnYn;
	}

	public String getStndAsrt() {
		return stndAsrt;
	}

	public void setStndAsrt(String stndAsrt) {
		this.stndAsrt = stndAsrt;
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

	public String getDvRqstNo() {
        return dvRqstNo;
    }

    public void setDvRqstNo(String dvRqstNo) {
        this.dvRqstNo = dvRqstNo;
    }

    public String getDicAsmLnm() {
        return dicAsmLnm;
    }

    public void setDicAsmLnm(String dicAsmLnm) {
        this.dicAsmLnm = dicAsmLnm;
    }

    public String getDicAsmPnm() {
        return dicAsmPnm;
    }

    public void setDicAsmPnm(String dicAsmPnm) {
        this.dicAsmPnm = dicAsmPnm;
    }

    public String getDmnLnm() {
        return dmnLnm;
    }

    public void setDmnLnm(String dmnLnm) {
        this.dmnLnm = dmnLnm;
    }

    public String getDmnPnm() {
        return dmnPnm;
    }

    public void setDmnPnm(String dmnPnm) {
        this.dmnPnm = dmnPnm;
    }

    public String getDmngLnm() {
		return dmngLnm;
	}

	public void setDmngLnm(String dmngLnm) {
		this.dmngLnm = dmngLnm;
	}

	public String getDicAsmDsLnm() {
        return dicAsmDsLnm;
    }

    public void setDicAsmDsLnm(String dicAsmDsLnm) {
        this.dicAsmDsLnm = dicAsmDsLnm;
    }

    public String getDicAsmDsPnm() {
        return dicAsmDsPnm;
    }

    public void setDicAsmDsPnm(String dicAsmDsPnm) {
        this.dicAsmDsPnm = dicAsmDsPnm;
    }

    public String getDvTrgLnm() {
        return dvTrgLnm;
    }

    public void setDvTrgLnm(String dvTrgLnm) {
        this.dvTrgLnm = dvTrgLnm;
    }

    public String getDvOrgLnm() {
		return dvOrgLnm;
	}

	public void setDvOrgLnm(String dvOrgLnm) {
		this.dvOrgLnm = dvOrgLnm;
	}

	public String getDvEncYn() {
		return dvEncYn;
	}

	public void setDvEncYn(String dvEncYn) {
		this.dvEncYn = dvEncYn;
	}

	public String getDvObjDescn() {
		return dvObjDescn;
	}

	public void setDvObjDescn(String dvObjDescn) {
		this.dvObjDescn = dvObjDescn;
	}

	public String getDvRqstUserId() {
		return dvRqstUserId;
	}

	public void setDvRqstUserId(String dvRqstUserId) {
		this.dvRqstUserId = dvRqstUserId;
	}

	public String getPrcCd() {
        return prcCd;
    }

    public void setPrcCd(String prcCd) {
        this.prcCd = prcCd;
    }

	public String getRegPosYn() {
		return regPosYn;
	}

	public void setRegPosYn(String regPosYn) {
		this.regPosYn = regPosYn;
	}
	public String getDvRqstRes() {
		return dvRqstRes;
	}

	public void setDvRqstRes(String dvRqstRes) {
		this.dvRqstRes = dvRqstRes;
	}

	/**
	 * @return the trgLnm
	 */
	public String getTrgLnm() {
		return trgLnm;
	}

	/**
	 * @param trgLnm the trgLnm to set
	 */
	public void setTrgLnm(String trgLnm) {
		this.trgLnm = trgLnm;
	}

	public String getDvSeCd() {
		return dvSeCd;
	}

	public void setDvSeCd(String dvSeCd) {
		this.dvSeCd = dvSeCd;
	}
	
	public String getDmngPnm() {
		return dmngPnm;
	}

	public void setDmngPnm(String dmngPnm) {
		this.dmngPnm = dmngPnm;
	}

	public String getDmngId() {
		return dmngId;
	}

	public void setDmngId(String dmngId) {
		this.dmngId = dmngId;
	}

	public String getInfotpLnm() {
		return infotpLnm;
	}

	public void setInfotpLnm(String infotpLnm) {
		this.infotpLnm = infotpLnm;
	}

	public String getInfotpId() {
		return infotpId;
	}

	public void setInfotpId(String infotpId) {
		this.infotpId = infotpId;
	}
	
	public String getSditmAutoCrtYn() {
		return sditmAutoCrtYn;
	}

	public void setSditmAutoCrtYn(String sditmAutoCrtYn) {
		this.sditmAutoCrtYn = sditmAutoCrtYn;
	}

	public String getCdValTypCd() {
		return cdValTypCd;
	}

	public void setCdValTypCd(String cdValTypCd) {
		this.cdValTypCd = cdValTypCd;
	}

	public String getCdValIvwCd() {
		return cdValIvwCd;
	}

	public void setCdValIvwCd(String cdValIvwCd) {
		this.cdValIvwCd = cdValIvwCd;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WapDvCanAsm [dvRqstNo=").append(dvRqstNo)
				.append(", dicAsmLnm=").append(dicAsmLnm)
				.append(", dicAsmPnm=").append(dicAsmPnm).append(", dmnLnm=")
				.append(dmnLnm).append(", dmnPnm=").append(dmnPnm)
				.append(", dmngLnm=").append(dmngLnm).append(", dicAsmDsLnm=")
				.append(dicAsmDsLnm).append(", dicAsmDsPnm=")
				.append(dicAsmDsPnm).append(", dvTrgLnm=").append(dvTrgLnm)
				.append(", dvOrgLnm=").append(dvOrgLnm).append(", dvEncYn=")
				.append(dvEncYn).append(", dvObjDescn=").append(dvObjDescn)
				.append(", dvRqstUserId=").append(dvRqstUserId)
				.append(", prcCd=").append(prcCd).append(", regPosYn=")
				.append(regPosYn).append(", trgLnm=").append(trgLnm)
				.append(", stndAsrt=").append(stndAsrt)
				.append(", persInfoCnvYn=").append(persInfoCnvYn)
				.append(", persInfoGrd=").append(persInfoGrd)
				.append("]");
		return builder.toString();
	}

	

}