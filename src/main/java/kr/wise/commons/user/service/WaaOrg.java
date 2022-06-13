package kr.wise.commons.user.service;

public class WaaOrg {
	
	private String orgCd;
	private String orgNm;
	private String dbNm;
	private String rqstDtm;
	private String rqstUserId;
	
	public String getOrgCd() {
		return orgCd;
	}
	public String getOrgNm() {
		return orgNm;
	}
	public String getRqstDtm() {
		return rqstDtm;
	}
	public String getRqstUserId() {
		return rqstUserId;
	}
	public void setOrgCd(String orgCd) {
		this.orgCd = orgCd;
	}
	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}
	public void setRqstDtm(String rqstDtm) {
		this.rqstDtm = rqstDtm;
	}
	public void setRqstUserId(String rqstUserId) {
		this.rqstUserId = rqstUserId;
	}
	public String getDbNm() {
		return dbNm;
	}
	public void setDbNm(String dbNm) {
		this.dbNm = dbNm;
	}
	
	
}
