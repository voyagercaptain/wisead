package kr.wise.commons.damgmt.db.service;

import java.util.Date;

public class WaaDataTypeMapru extends WaaDbmsDataType {
    private String dataTypeMapruId;

//    private Date expDtm;
//
//    private Date strDtm;

    private String mapruNm;

//    private String dbmsDataTypeId;

    private String lgcDataType;

    private String minVal;

    private String cndCd;

    private String maxVal;

//    private String objDescn;
//
//    private Integer objVers;
//
//    private String regTypCd;
//
//    private Date writDtm;
//
//    private String writUserId;

    public String getDataTypeMapruId() {
        return dataTypeMapruId;
    }

    public void setDataTypeMapruId(String dataTypeMapruId) {
        this.dataTypeMapruId = dataTypeMapruId;
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

    public String getMapruNm() {
        return mapruNm;
    }

    public void setMapruNm(String mapruNm) {
        this.mapruNm = mapruNm;
    }

//    public String getDbmsDataTypeId() {
//        return dbmsDataTypeId;
//    }
//
//    public void setDbmsDataTypeId(String dbmsDataTypeId) {
//        this.dbmsDataTypeId = dbmsDataTypeId;
//    }

    public String getLgcDataType() {
        return lgcDataType;
    }

    public void setLgcDataType(String lgcDataType) {
        this.lgcDataType = lgcDataType;
    }

    public String getMinVal() {
        return minVal;
    }

    public void setMinVal(String minVal) {
        this.minVal = minVal;
    }

    public String getCndCd() {
        return cndCd;
    }

    public void setCndCd(String cndCd) {
        this.cndCd = cndCd;
    }

    public String getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(String maxVal) {
        this.maxVal = maxVal;
    }

	/** meta */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaDataTypeMapru [dataTypeMapruId=")
				.append(dataTypeMapruId).append(", mapruNm=").append(mapruNm)
				.append(", lgcDataType=").append(lgcDataType)
				.append(", minVal=").append(minVal).append(", cndCd=")
				.append(cndCd).append(", maxVal=").append(maxVal).append("]");
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