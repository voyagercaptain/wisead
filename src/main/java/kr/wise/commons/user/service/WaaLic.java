package kr.wise.commons.user.service;

import java.util.Date;

import kr.wise.commons.cmm.CommonVo;


public class WaaLic extends CommonVo {
	private String macAddr;
	private String licKey;
	private String orgNm;
	private String chrgUserNm;
	private String chrgEmail;
	private String chrgTelNo;
	private Date writDtm;
	
	public String getLicKey() {
		return licKey;
	}
	public void setLicKey(String licKey) {
		this.licKey = licKey;
	}
	public String getOrgNm() {
		return orgNm;
	}
	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}
	public String getChrgUserNm() {
		return chrgUserNm;
	}
	public void setChrgUserNm(String chrgUserNm) {
		this.chrgUserNm = chrgUserNm;
	}
	public String getChrgEmail() {
		return chrgEmail;
	}
	public void setChrgEmail(String chrgEmail) {
		this.chrgEmail = chrgEmail;
	}
	public String getChrgTelNo() {
		return chrgTelNo;
	}
	public void setChrgTelNo(String chrgTelNo) {
		this.chrgTelNo = chrgTelNo;
	}
	public Date getWritDtm() {
		return writDtm;
	}
	public void setWritDtm(Date writDtm) {
		this.writDtm = writDtm;
	}
	public String getMacAddr() {
		return macAddr;
	}
	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}
}