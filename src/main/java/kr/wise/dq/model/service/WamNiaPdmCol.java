package kr.wise.dq.model.service;

import kr.wise.commons.cmm.CommonVo;


public class WamNiaPdmCol extends CommonVo {
	private String colId;
	private String orgNm;
	private String dbNm;
	private String tblPnm;
	private String tblLnm;
	private String colPnm;
	private String colLnm;
	private String stndCdYn;
	private String stndCdNm;
	private String colDescn;
	private String dataType;
	private String dataLen;
	private String dataScal;
	private String dataFrm;
	private String notNullYn;
	private String pkYn;
	private String fkYn;
	private String constrnt;
	private String prsnYn;
	private String encYn;
	private String openYn;
	
	private String searchFlag;
	
	private String anaCtns    ;
	private String anaDtm     ;
	private String tblCnt     ;
	private String tblErrCnt  ;
	private String tblErrRt   ;
	private String colCnt     ;
	private String colErrCnt  ;
	private String colErrRt   ;
							  
	private String colNcharCnt;
	private String colNcharRt ;
	private String colSpaceCnt;
	private String colSpaceRt ;


	public String getColId() {
		return colId;
	}
	public void setColId(String colId) {
		this.colId = colId;
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
	public String getTblPnm() {
		return tblPnm;
	}
	public void setTblPnm(String tblPnm) {
		this.tblPnm = tblPnm;
	}
	public String getTblLnm() {
		return tblLnm;
	}
	public void setTblLnm(String tblLnm) {
		this.tblLnm = tblLnm;
	}
	public String getColPnm() {
		return colPnm;
	}
	public void setColPnm(String colPnm) {
		this.colPnm = colPnm;
	}
	public String getColLnm() {
		return colLnm;
	}
	public void setColLnm(String colLnm) {
		this.colLnm = colLnm;
	}
	public String getStndCdYn() {
		return stndCdYn;
	}
	public void setStndCdYn(String stndCdYn) {
		this.stndCdYn = stndCdYn;
	}
	public String getStndCdNm() {
		return stndCdNm;
	}
	public void setStndCdNm(String stndCdNm) {
		this.stndCdNm = stndCdNm;
	}
	public String getColDescn() {
		return colDescn;
	}
	public void setColDescn(String colDescn) {
		this.colDescn = colDescn;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getDataLen() {
		return dataLen;
	}
	public void setDataLen(String dataLen) {
		this.dataLen = dataLen;
	}
	public String getDataScal() {
		return dataScal;
	}
	public void setDataScal(String dataScal) {
		this.dataScal = dataScal;
	}
	public String getDataFrm() {
		return dataFrm;
	}
	public void setDataFrm(String dataFrm) {
		this.dataFrm = dataFrm;
	}
	public String getNotNullYn() {
		return notNullYn;
	}
	public void setNotNullYn(String notNullYn) {
		this.notNullYn = notNullYn;
	}
	public String getPkYn() {
		return pkYn;
	}
	public void setPkYn(String pkYn) {
		this.pkYn = pkYn;
	}
	public String getFkYn() {
		return fkYn;
	}
	public void setFkYn(String fkYn) {
		this.fkYn = fkYn;
	}
	public String getConstrnt() {
		return constrnt;
	}
	public void setConstrnt(String constrnt) {
		this.constrnt = constrnt;
	}
	public String getPrsnYn() {
		return prsnYn;
	}
	public void setPrsnYn(String prsnYn) {
		this.prsnYn = prsnYn;
	}
	public String getEncYn() {
		return encYn;
	}
	public void setEncYn(String encYn) {
		this.encYn = encYn;
	}
	public String getOpenYn() {
		return openYn;
	}
	public void setOpenYn(String openYn) {
		this.openYn = openYn;
	}
	public String getSearchFlag() {
		return searchFlag;
	}
	public void setSearchFlag(String searchFlag) {
		this.searchFlag = searchFlag;
	}
	public String getAnaCtns() {
		return anaCtns;
	}
	public void setAnaCtns(String anaCtns) {
		this.anaCtns = anaCtns;
	}
	public String getAnaDtm() {
		return anaDtm;
	}
	public void setAnaDtm(String anaDtm) {
		this.anaDtm = anaDtm;
	}
	public String getTblCnt() {
		return tblCnt;
	}
	public void setTblCnt(String tblCnt) {
		this.tblCnt = tblCnt;
	}
	public String getTblErrCnt() {
		return tblErrCnt;
	}
	public void setTblErrCnt(String tblErrCnt) {
		this.tblErrCnt = tblErrCnt;
	}
	public String getTblErrRt() {
		return tblErrRt;
	}
	public void setTblErrRt(String tblErrRt) {
		this.tblErrRt = tblErrRt;
	}
	public String getColCnt() {
		return colCnt;
	}
	public void setColCnt(String colCnt) {
		this.colCnt = colCnt;
	}
	public String getColErrCnt() {
		return colErrCnt;
	}
	public void setColErrCnt(String colErrCnt) {
		this.colErrCnt = colErrCnt;
	}
	public String getColErrRt() {
		return colErrRt;
	}
	public void setColErrRt(String colErrRt) {
		this.colErrRt = colErrRt;
	}
	public String getColNcharCnt() {
		return colNcharCnt;
	}
	public void setColNcharCnt(String colNcharCnt) {
		this.colNcharCnt = colNcharCnt;
	}
	public String getColNcharRt() {
		return colNcharRt;
	}
	public void setColNcharRt(String colNcharRt) {
		this.colNcharRt = colNcharRt;
	}
	public String getColSpaceCnt() {
		return colSpaceCnt;
	}
	public void setColSpaceCnt(String colSpaceCnt) {
		this.colSpaceCnt = colSpaceCnt;
	}
	public String getColSpaceRt() {
		return colSpaceRt;
	}
	public void setColSpaceRt(String colSpaceRt) {
		this.colSpaceRt = colSpaceRt;
	}
	
		
}