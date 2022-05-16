package kr.wise.dq.model.service;

import kr.wise.commons.cmm.CommonVo;

public class WamNiaPdmSditmMap extends CommonVo{
	private String mapId;
	private String colId;
	private String sditmId;
	private String stndNm;

	public String getMapId() {
		return mapId;
	}
	public void setMapId(String mapId) {
		this.mapId = mapId;
	}
	public String getColId() {
		return colId;
	}
	public void setColId(String colId) {
		this.colId = colId;
	}
	public String getSditmId() {
		return sditmId;
	}
	public void setSditmId(String sditmId) {
		this.sditmId = sditmId;
	}
	public String getStndNm() {
		return stndNm;
	}
	public void setStndNm(String stndNm) {
		this.stndNm = stndNm;
	}
}
