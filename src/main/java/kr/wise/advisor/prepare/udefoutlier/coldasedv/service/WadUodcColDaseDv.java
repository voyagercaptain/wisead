package kr.wise.advisor.prepare.udefoutlier.coldasedv.service;

import java.util.Date;

import kr.wise.advisor.prepare.udefoutlier.service.WadUod;
import kr.wise.commons.cmm.CommonVo;

public class WadUodcColDaseDv extends WadUod{  
    private String udfOtlDtcId;

	private String creCompId;

	private String anaDaseId;
	
	private String anaDaseNm;

	private String srcAnaDaseId;
	
	private String srcAnaDaseNm;
	

	public String getUdfOtlDtcId() {
		return udfOtlDtcId;
	}

	public void setUdfOtlDtcId(String udfOtlDtcId) {
		this.udfOtlDtcId = udfOtlDtcId;
	}

	public String getCreCompId() {
		return creCompId;
	}

	public void setCreCompId(String creCompId) {
		this.creCompId = creCompId;
	}

	public String getAnaDaseId() {
		return anaDaseId;
	}

	public void setAnaDaseId(String anaDaseId) {
		this.anaDaseId = anaDaseId;
	}

	public String getSrcAnaDaseId() {
		return srcAnaDaseId;
	}

	public void setSrcAnaDaseId(String srcAnaDaseId) {
		this.srcAnaDaseId = srcAnaDaseId;
	} 

	public String getAnaDaseNm() {
		return anaDaseNm;
	}

	public void setAnaDaseNm(String anaDaseNm) {
		this.anaDaseNm = anaDaseNm;
	}
 
	public String getSrcAnaDaseNm() {
		return srcAnaDaseNm;
	}

	public void setSrcAnaDaseNm(String srcAnaDaseNm) {
		this.srcAnaDaseNm = srcAnaDaseNm;
	}

	
	
}