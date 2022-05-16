package kr.wise.dq.criinfo.dqi.service;

import kr.wise.commons.cmm.CommonVo;

public class WaqDqiVO extends CommonVo {

    private String dqiId;

    private String dqiLnm;

    private String dqiPnm;

    private Short dqiLvl;

    private String uppDqiLnm;

    private String uppDqiPnm;

    private String uppDqiId;
    
    private String fullPath;

    
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

    public String getDqiPnm() {
        return dqiPnm;
    }

    public void setDqiPnm(String dqiPnm) {
        this.dqiPnm = dqiPnm;
    }
    public Short getDqiLvl() {
        return dqiLvl;
    }

    public void setDqiLvl(Short dqiLvl) {
        this.dqiLvl = dqiLvl;
    }

    public String getUppDqiLnm() {
        return uppDqiLnm;
    }

    public void setUppDqiLnm(String uppDqiLnm) {
        this.uppDqiLnm = uppDqiLnm;
    }

    public String getUppDqiPnm() {
        return uppDqiPnm;
    }

    public void setUppDqiPnm(String uppDqiPnm) {
        this.uppDqiPnm = uppDqiPnm;
    }

    public String getUppDqiId() {
        return uppDqiId;
    }

    public void setUppDqiId(String uppDqiId) {
        this.uppDqiId = uppDqiId;
    }

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaqDqiVO [dqiId=").append(dqiId).append(", dqiLnm=")
				.append(dqiLnm).append(", dqiPnm=").append(dqiPnm)
				.append(", dqiLvl=").append(dqiLvl).append(", uppDqiLnm=")
				.append(uppDqiLnm).append(", uppDqiPnm=").append(uppDqiPnm)
				.append(", uppDqiId=").append(uppDqiId).append("]");
		return builder.toString() + super.toString();
	}

    
}