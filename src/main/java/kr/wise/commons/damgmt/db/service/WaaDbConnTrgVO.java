package kr.wise.commons.damgmt.db.service;

import kr.wise.commons.cmm.CommonVo;

public class WaaDbConnTrgVO extends CommonVo {

    private String dbConnTrgId;

//    private Date expDtm;
//
//    private Date strDtm;

    private String dbConnTrgPnm;

    private String dbConnTrgLnm;

    private String dbmsTypCd;

    private String dbmsVersCd;

    private String connTrgDbLnkChrw;

    private String connTrgLnkUrl;

    private String connTrgDrvrNm;

    private String crgpNm;

    private String crgpCntel;

//    private String objDescn;
//
//    private Integer objVers;
//
//    private String regTypCd;
//
//    private Date writDtm;
//
//    private String writUserId;
//    private String writUserNm;

    private String dbConnAcId;

    private String dbConnAcPwd;

    private String dbLnkSts;


    private String dbSchId;
    private String dbSchPnm;
    private String dbSchLnm;
    private String ddlTrgYn;
    private String cltXclYn;
    private String ddlTrgDcd;
    private String cdSndTrgYn;
    private String cdAutoSndYn;

    private String metaMngYn;


	public String getMetaMngYn() {
		return metaMngYn;
	}

	public void setMetaMngYn(String metaMngYn) {
		this.metaMngYn = metaMngYn;
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

	public String getDbSchLnm() {
		return dbSchLnm;
	}

	public void setDbSchLnm(String dbSchLnm) {
		this.dbSchLnm = dbSchLnm;
	}

	public String getDdlTrgYn() {
		return ddlTrgYn;
	}

	public void setDdlTrgYn(String ddlTrgYn) {
		this.ddlTrgYn = ddlTrgYn;
	}

	public String getCltXclYn() {
		return cltXclYn;
	}

	public void setCltXclYn(String cltXclYn) {
		this.cltXclYn = cltXclYn;
	}

	public String getDdlTrgDcd() {
		return ddlTrgDcd;
	}

	public void setDdlTrgDcd(String ddlTrgDcd) {
		this.ddlTrgDcd = ddlTrgDcd;
	}

	public String getCdSndTrgYn() {
		return cdSndTrgYn;
	}

	public void setCdSndTrgYn(String cdSndTrgYn) {
		this.cdSndTrgYn = cdSndTrgYn;
	}

	public String getCdAutoSndYn() {
		return cdAutoSndYn;
	}

	public void setCdAutoSndYn(String cdAutoSndYn) {
		this.cdAutoSndYn = cdAutoSndYn;
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

    public String getDbConnTrgLnm() {
        return dbConnTrgLnm;
    }

    public void setDbConnTrgLnm(String dbConnTrgLnm) {
        this.dbConnTrgLnm = dbConnTrgLnm;
    }

    public String getDbmsTypCd() {
        return dbmsTypCd;
    }

    public void setDbmsTypCd(String dbmsTypCd) {
        this.dbmsTypCd = dbmsTypCd;
    }

    public String getDbmsVersCd() {
        return dbmsVersCd;
    }

    public void setDbmsVersCd(String dbmsVersCd) {
        this.dbmsVersCd = dbmsVersCd;
    }

    public String getConnTrgDbLnkChrw() {
        return connTrgDbLnkChrw;
    }

    public void setConnTrgDbLnkChrw(String connTrgDbLnkChrw) {
        this.connTrgDbLnkChrw = connTrgDbLnkChrw;
    }

    public String getConnTrgLnkUrl() {
        return connTrgLnkUrl;
    }

    public void setConnTrgLnkUrl(String connTrgLnkUrl) {
        this.connTrgLnkUrl = connTrgLnkUrl;
    }

    public String getConnTrgDrvrNm() {
        return connTrgDrvrNm;
    }

    public void setConnTrgDrvrNm(String connTrgDrvrNm) {
        this.connTrgDrvrNm = connTrgDrvrNm;
    }

    public String getCrgpNm() {
        return crgpNm;
    }

    public void setCrgpNm(String crgpNm) {
        this.crgpNm = crgpNm;
    }

    public String getCrgpCntel() {
        return crgpCntel;
    }

    public void setCrgpCntel(String crgpCntel) {
        this.crgpCntel = crgpCntel;
    }


	/**
	 * @return the dbConnAcId
	 */
	public String getDbConnAcId() {
		return dbConnAcId;
	}

	/**
	 * @param dbConnAcId the dbConnAcId to set
	 */
	public void setDbConnAcId(String dbConnAcId) {
		this.dbConnAcId = dbConnAcId;
	}

	/**
	 * @return the dbConnAcPwd
	 */
	public String getDbConnAcPwd() {
		return dbConnAcPwd;
	}

	/**
	 * @param dbConnAcPwd the dbConnAcPwd to set
	 */
	public void setDbConnAcPwd(String dbConnAcPwd) {
		this.dbConnAcPwd = dbConnAcPwd;
	}

	/**
	 * @return the dbLnkSts
	 */
	public String getDbLnkSts() {
		return dbLnkSts;
	}

	/**
	 * @param dbLnkSts the dbLnkSts to set
	 */
	public void setDbLnkSts(String dbLnkSts) {
		this.dbLnkSts = dbLnkSts;
	}

	/** insomnia */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaDbConnTrgVO [dbConnTrgId=").append(dbConnTrgId)
				.append(", dbConnTrgPnm=").append(dbConnTrgPnm)
				.append(", dbConnTrgLnm=").append(dbConnTrgLnm)
				.append(", dbmsTypCd=").append(dbmsTypCd)
				.append(", dbmsVersCd=").append(dbmsVersCd)
				.append(", connTrgDbLnkChrw=").append(connTrgDbLnkChrw)
				.append(", connTrgLnkUrl=").append(connTrgLnkUrl)
				.append(", connTrgDrvrNm=").append(connTrgDrvrNm)
				.append(", crgpNm=").append(crgpNm).append(", crgpCntel=")
				.append(crgpCntel).append(", dbConnAcId=").append(dbConnAcId)
				.append(", dbConnAcPwd=").append(dbConnAcPwd)
				.append(", dbLnkSts=").append(dbLnkSts).append(", dbSchId=")
				.append(dbSchId).append(", dbSchPnm=").append(dbSchPnm)
				.append(", dbSchLnm=").append(dbSchLnm).append(", ddlTrgYn=")
				.append(ddlTrgYn).append(", cltXclYn=").append(cltXclYn)
				.append(", ddlTrgDcd=").append(ddlTrgDcd)
				.append(", cdSndTrgYn=").append(cdSndTrgYn)
				.append(", cdAutoSndYn=").append(cdAutoSndYn).append("]");
		return builder.toString();
	}




}