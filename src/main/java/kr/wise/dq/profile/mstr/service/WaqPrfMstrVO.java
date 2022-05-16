package kr.wise.dq.profile.mstr.service;

import kr.wise.commons.cmm.CommonVo;

public class WaqPrfMstrVO extends CommonVo {

	private String prfId;

    private String prfKndCd;

    private String prfNm;

    private String dbConnTrgId;
    
    private String dbConnTrgPnm;

    private String dbSchId;
    
    private String dbSchPnm;

    private String dbcTblNm;

    private String objNm;

    private String adtCndSql;

    private String hntSql;

    private String useYn;
    
    private String dqiLnm;
    private String dqiId;
    

    public String getDqiLnm() {
		return dqiLnm;
	}

	public void setDqiLnm(String dqiLnm) {
		this.dqiLnm = dqiLnm;
	}

	public String getDqiId() {
		return dqiId;
	}

	public void setDqiId(String dqiId) {
		this.dqiId = dqiId;
	}

	public String getPrfId() {
        return prfId;
    }

    public void setPrfId(String prfId) {
        this.prfId = prfId;
    }

    public String getPrfKndCd() {
        return prfKndCd;
    }

    public void setPrfKndCd(String prfKndCd) {
        this.prfKndCd = prfKndCd;
    }

    public String getPrfNm() {
        return prfNm;
    }

    public void setPrfNm(String prfNm) {
        this.prfNm = prfNm;
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

	public String getDbcTblNm() {
        return dbcTblNm;
    }

    public void setDbcTblNm(String dbcTblNm) {
        this.dbcTblNm = dbcTblNm;
    }

    public String getObjNm() {
		return objNm;
	}

	public void setObjNm(String objNm) {
		this.objNm = objNm;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaqPrfMstrVO [prfId=").append(prfId)
				.append(", prfKndCd=").append(prfKndCd).append(", prfNm=")
				.append(prfNm).append(", dbConnTrgId=").append(dbConnTrgId)
				.append(", dbConnTrgPnm=").append(dbConnTrgPnm)
				.append(", dbSchId=").append(dbSchId).append(", dbSchPnm=")
				.append(dbSchPnm).append(", dbcTblNm=").append(dbcTblNm)
				.append(", objNm=").append(objNm).append(", adtCndSql=")
				.append(adtCndSql).append(", hntSql=").append(hntSql)
				.append(", useYn=").append(useYn).append("]");
		return builder.toString() + super.toString();
	}

    
}