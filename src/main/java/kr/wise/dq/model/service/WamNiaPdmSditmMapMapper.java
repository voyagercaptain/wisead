package kr.wise.dq.model.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamNiaPdmSditmMapMapper {

	int insertPdmSditmMap(WamNiaPdmSditmMap record);

	int insertPdmSditmMapAna(WamNiaPdmSditmMapAna record);

	String getSditmId(NiaPdmSditmMapVo record);

	String getSditmIdByNm(NiaPdmSditmMapVo record);

	List<WamNiaPdmSditmMapAna> selectPdmSditmMapAna();

	int updateByMapId(WamNiaPdmSditmMap record);

	List<NiaPdmSditmMapVo> selectPdmSditmMapRqst(NiaPdmSditmMapVo search);

	List<NiaPdmSditmMapVo> selectPdmSditmMapList(NiaPdmSditmMapVo search);

	List<WamNiaPdmSditmMapAna> getPdmSditmMapAna();

	int deletePdmSditmMapList(NiaPdmSditmMapVo record);

	String getMapIdByColSditm(NiaPdmSditmMapVo record);

	String getDbSditmIdByNm(NiaPdmSditmMapVo record);

	String getStndNm();

	List<NiaPdmSditmMapVo> selectPdmAllMapList(NiaPdmSditmMapVo search);

}
