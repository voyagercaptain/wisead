package kr.wise.commons.user;

import kr.wise.commons.cmm.CommonVo;


public class WaaUserg extends CommonVo {
    private String usergId;

//    private Date expDtm;
//
//    private Date strDtm;

    private String usergLnm;

    private String usergPnm;

    private String usergTypCd;
    
    private String title;
    
	private int orgCount;
	
	private int totalOrg;
	
	private int regOrg;
	
	private int item;
	
	private int word;
	
	private int dmn;
	
	private int code;
	
	private int orgItemCount;
	
	private int orgWordCount;
	
	private int orgDomainCount;
	
	private int orgCodeCount;
	
	private int dbItemCount;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getOrgCount() {
		return orgCount;
	}

	public void setOrgCount(int orgCount) {
		this.orgCount = orgCount;
	}

	public int getTotalOrg() {
		return totalOrg;
	}

	public void setTotalOrg(int totalOrg) {
		this.totalOrg = totalOrg;
	}

	public int getRegOrg() {
		return regOrg;
	}

	public void setRegOrg(int regOrg) {
		this.regOrg = regOrg;
	}

	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
	}

	public int getWord() {
		return word;
	}

	public void setWord(int word) {
		this.word = word;
	}

	public int getDmn() {
		return dmn;
	}

	public void setDmn(int dmn) {
		this.dmn = dmn;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getOrgItemCount() {
		return orgItemCount;
	}

	public void setOrgItemCount(int orgItemCount) {
		this.orgItemCount = orgItemCount;
	}

	public int getOrgWordCount() {
		return orgWordCount;
	}

	public void setOrgWordCount(int orgWordCount) {
		this.orgWordCount = orgWordCount;
	}

	public int getOrgDomainCount() {
		return orgDomainCount;
	}

	public void setOrgDomainCount(int orgDomainCount) {
		this.orgDomainCount = orgDomainCount;
	}

	public int getOrgCodeCount() {
		return orgCodeCount;
	}

	public void setOrgCodeCount(int orgCodeCount) {
		this.orgCodeCount = orgCodeCount;
	}

	public int getDbItemCount() {
		return dbItemCount;
	}

	public void setDbItemCount(int dbItemCount) {
		this.dbItemCount = dbItemCount;
	}

	public int getDbWordCount() {
		return dbWordCount;
	}

	public void setDbWordCount(int dbWordCount) {
		this.dbWordCount = dbWordCount;
	}

	public int getDbDomainCount() {
		return dbDomainCount;
	}

	public void setDbDomainCount(int dbDomainCount) {
		this.dbDomainCount = dbDomainCount;
	}

	public int getDbCodeCount() {
		return dbCodeCount;
	}

	public void setDbCodeCount(int dbCodeCount) {
		this.dbCodeCount = dbCodeCount;
	}

	public int getCmItemCount() {
		return cmItemCount;
	}

	public void setCmItemCount(int cmItemCount) {
		this.cmItemCount = cmItemCount;
	}

	public int getCmWordCount() {
		return cmWordCount;
	}

	public void setCmWordCount(int cmWordCount) {
		this.cmWordCount = cmWordCount;
	}

	public int getCmDomainCount() {
		return cmDomainCount;
	}

	public void setCmDomainCount(int cmDomainCount) {
		this.cmDomainCount = cmDomainCount;
	}

	public int getOrgDbCount() {
		return orgDbCount;
	}

	public void setOrgDbCount(int orgDbCount) {
		this.orgDbCount = orgDbCount;
	}

	private int dbWordCount;
	
	private int dbDomainCount;
	
	private int dbCodeCount;
	
	private int cmItemCount;
	
	private int cmWordCount;
	
	private int cmDomainCount;
	
	private int orgDbCount;
	
	
	private String orgItemYn;
		
	private String orgWordYn;
		
	private String orgDomainYn;
		
	private String orgCodeYn;
		
	private String dbItemYn;
		
	private String dbWordYn;
		
	private String dbDomainYn;
		
	private String dbCodeYn;
		
	private String orgType;
		
	private Float orgItemRate;
		
	private Float orgWordRate;
		
	private Float orgDomainRate;
		
	private Float orgCodeRate;
		
	private Float dbItemRate;
		
	private Float dbWordRate;
		
	private Float dbDomainRate;
		
	private Float dbCodeRate;
		
		public String getOrgItemYn() {
			return orgItemYn;
		}

		public void setOrgItemYn(String orgItemYn) {
			this.orgItemYn = orgItemYn;
		}

		public String getOrgWordYn() {
			return orgWordYn;
		}

		public void setOrgWordYn(String orgWordYn) {
			this.orgWordYn = orgWordYn;
		}

		public String getOrgDomainYn() {
			return orgDomainYn;
		}

		public void setOrgDomainYn(String orgDomainYn) {
			this.orgDomainYn = orgDomainYn;
		}

		public String getOrgCodeYn() {
			return orgCodeYn;
		}

		public void setOrgCodeYn(String orgCodeYn) {
			this.orgCodeYn = orgCodeYn;
		}

		public String getDbItemYn() {
			return dbItemYn;
		}

		public void setDbItemYn(String dbItemYn) {
			this.dbItemYn = dbItemYn;
		}

		public String getDbWordYn() {
			return dbWordYn;
		}

		public void setDbWordYn(String dbWordYn) {
			this.dbWordYn = dbWordYn;
		}

		public String getDbDomainYn() {
			return dbDomainYn;
		}

		public void setDbDomainYn(String dbDomainYn) {
			this.dbDomainYn = dbDomainYn;
		}

		public String getDbCodeYn() {
			return dbCodeYn;
		}

		public void setDbCodeYn(String dbCodeYn) {
			this.dbCodeYn = dbCodeYn;
		}

		public String getOrgType() {
			return orgType;
		}

		public void setOrgType(String orgType) {
			this.orgType = orgType;
		}

		public Float getOrgItemRate() {
			return orgItemRate;
		}

		public void setOrgItemRate(Float orgItemRate) {
			this.orgItemRate = orgItemRate;
		}

		public Float getOrgWordRate() {
			return orgWordRate;
		}

		public void setOrgWordRate(Float orgWordRate) {
			this.orgWordRate = orgWordRate;
		}

		public Float getOrgDomainRate() {
			return orgDomainRate;
		}

		public void setOrgDomainRate(Float orgDomainRate) {
			this.orgDomainRate = orgDomainRate;
		}

		public Float getOrgCodeRate() {
			return orgCodeRate;
		}

		public void setOrgCodeRate(Float orgCodeRate) {
			this.orgCodeRate = orgCodeRate;
		}

		public Float getDbItemRate() {
			return dbItemRate;
		}

		public void setDbItemRate(Float dbItemRate) {
			this.dbItemRate = dbItemRate;
		}

		public Float getDbWordRate() {
			return dbWordRate;
		}

		public void setDbWordRate(Float dbWordRate) {
			this.dbWordRate = dbWordRate;
		}

		public Float getDbDomainRate() {
			return dbDomainRate;
		}

		public void setDbDomainRate(Float dbDomainRate) {
			this.dbDomainRate = dbDomainRate;
		}

		public Float getDbCodeRate() {
			return dbCodeRate;
		}

		public void setDbCodeRate(Float dbCodeRate) {
			this.dbCodeRate = dbCodeRate;
		}

//    private String objDescn;
//
//    private Integer objVers;
//
//    private String regTypCd;
//
//    private Date writDtm;
//
//    private String writUserId;

    public String getUsergId() {
        return usergId;
    }

    public void setUsergId(String usergId) {
        this.usergId = usergId;
    }

//    public Date getExpDtm() {
//        return expDtm;
//    }
//
//    public void setExpDtm(Date expDtm) {
//        this.expDtm = expDtm;
//    }
//
//    public Date getStrDtm() {
//        return strDtm;
//    }
//
//    public void setStrDtm(Date strDtm) {
//        this.strDtm = strDtm;
//    }

    public String getUsergLnm() {
        return usergLnm;
    }

    public void setUsergLnm(String usergLnm) {
        this.usergLnm = usergLnm;
    }

    public String getUsergPnm() {
        return usergPnm;
    }

    public void setUsergPnm(String usergPnm) {
        this.usergPnm = usergPnm;
    }

    public String getUsergTypCd() {
        return usergTypCd;
    }

    public void setUsergTypCd(String usergTypCd) {
        this.usergTypCd = usergTypCd;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaUserg [usergId=").append(usergId)
				.append(", usergLnm=").append(usergLnm).append(", usergPnm=")
				.append(usergPnm).append(", usergTypCd=").append(usergTypCd)
				.append("]");
		return builder.toString() + super.toString();
	}

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
//    public Date getWritDtm() {
//        return writDtm;
//    }
//
//    public void setWritDtm(Date writDtm) {
//        this.writDtm = writDtm;
//    }
//
//    public String getWritUserId() {
//        return writUserId;
//    }
//
//    public void setWritUserId(String writUserId) {
//        this.writUserId = writUserId;
//    }
    
    
}