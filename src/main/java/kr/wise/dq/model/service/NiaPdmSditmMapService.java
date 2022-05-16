package kr.wise.dq.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface NiaPdmSditmMapService {

	List<NiaPdmSditmMapVo> getPdmSditmMapList(NiaPdmSditmMapVo search) throws Exception;

	int regPdmSditmMapAna(ArrayList<WamNiaPdmSditmMapAna> arrayList) throws Exception;

	List<WamNiaPdmSditmMapAna> getPdmSditmMapAna() throws Exception;

	List<NiaPdmSditmMapVo> getPdmSditmList(NiaPdmSditmMapVo search) throws Exception;

	int regPdmSditmMapRqst(ArrayList<NiaPdmSditmMapVo> arrayList) throws Exception;

	List<WamNiaPdmSditmMapAna> selectPdmSditmMapAna() throws Exception;

	int delPdmSditmMapList(ArrayList<NiaPdmSditmMapVo> list) throws Exception;

	List<NiaPdmSditmMapVo> getPdmAllMap(NiaPdmSditmMapVo search)throws Exception;



}
