package kr.wise.dq.dbstnd.service;

import kr.wise.commons.cmm.CommonVo;

//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class WamDbStcd extends CommonVo  {
	
	
	private String commCdId;
	private String commCdNm;
	private String commCdDesc;
	private String comnCdDttpNm;
	private Integer comnCdDataLen;
	private String comnCdEnnm;
	private String mngDeptCd;
	private String pclrMtr;
	private String uppCommCdId;
	private String commDtlCdNm;
	private String commDtlCdMn;
	private String commDtlCdDesc;
	private String useYn;
	private String orgNm;
	private String dbNm;
	private String userId;
    private String spclNt;
    
    private String stndNm;
    
       
    
	public String getCommDtlCdMn() {
		return commDtlCdMn;
	}
	public void setCommDtlCdMn(String commDtlCdMn) {
		this.commDtlCdMn = commDtlCdMn;
	}
	public String getStndNm() {
		return stndNm;
	}
	public void setStndNm(String stndNm) {
		this.stndNm = stndNm;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSpclNt() {
		return spclNt;
	}
	public void setSpclNt(String spclNt) {
		this.spclNt = spclNt;
	}
	public String getCommCdId() {
		return commCdId;
	}
	public void setCommCdId(String commCdId) {
		this.commCdId = commCdId;
	}
	public String getCommCdNm() {
		return commCdNm;
	}
	public void setCommCdNm(String commCdNm) {
		this.commCdNm = commCdNm;
	}
	public String getCommCdDesc() {
		return commCdDesc;
	}
	public void setCommCdDesc(String commCdDesc) {
		this.commCdDesc = commCdDesc;
	}
	public String getComnCdDttpNm() {
		return comnCdDttpNm;
	}
	public void setComnCdDttpNm(String comnCdDttpNm) {
		this.comnCdDttpNm = comnCdDttpNm;
	}
	public Integer getComnCdDataLen() {
		return comnCdDataLen;
	}
	public void setComnCdDataLen(Integer comnCdDataLen) {
		this.comnCdDataLen = comnCdDataLen;
	}
	public String getComnCdEnnm() {
		return comnCdEnnm;
	}
	public void setComnCdEnnm(String comnCdEnnm) {
		this.comnCdEnnm = comnCdEnnm;
	}
	public String getMngDeptCd() {
		return mngDeptCd;
	}
	public void setMngDeptCd(String mngDeptCd) {
		this.mngDeptCd = mngDeptCd;
	}
	
	public String getPclrMtr() {
		return pclrMtr;
	}
	public void setPclrMtr(String pclrMtr) {
		this.pclrMtr = pclrMtr;
	}
	public String getUppCommCdId() {
		return uppCommCdId;
	}
	public void setUppCommCdId(String uppCommCdId) {
		this.uppCommCdId = uppCommCdId;
	}
	public String getCommDtlCdNm() {
		return commDtlCdNm;
	}
	public void setCommDtlCdNm(String commDtlCdNm) {
		this.commDtlCdNm = commDtlCdNm;
	}
	public String getCommDtlCdDesc() {
		return commDtlCdDesc;
	}
	public void setCommDtlCdDesc(String commDtlCdDesc) {
		this.commDtlCdDesc = commDtlCdDesc;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getOrgNm() {
		return orgNm;
	}
	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}
	public String getDbNm() {
		return dbNm;
	}
	public void setDbNm(String dbNm) {
		this.dbNm = dbNm;
	}
	
	
	
    
	
    

}