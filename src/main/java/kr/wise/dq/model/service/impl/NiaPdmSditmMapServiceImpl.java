package kr.wise.dq.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.dq.model.service.NiaPdmSditmMapService;
import kr.wise.dq.model.service.NiaPdmSditmMapVo;
import kr.wise.dq.model.service.WamNiaPdmSditmMap;
import kr.wise.dq.model.service.WamNiaPdmSditmMapAna;
import kr.wise.dq.model.service.WamNiaPdmSditmMapMapper;

@Service("niaPdmSditmMapService")
public class NiaPdmSditmMapServiceImpl implements NiaPdmSditmMapService{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private WamNiaPdmSditmMapMapper mapper;
	
	@Inject
	private EgovIdGnrService objectIdGnrService;

	@Override
	public List<NiaPdmSditmMapVo> getPdmSditmList(NiaPdmSditmMapVo search) throws Exception {
		// TODO Auto-generated method stub
		String stndNm = null;
		
		stndNm = mapper.getStndNm();
		
		if(stndNm == null)
			stndNm = "ORG";
		
		search.setStndNm(stndNm);
		return	mapper.selectPdmSditmMapRqst(search);
	}
	
	@Override
	public int regPdmSditmMapRqst(ArrayList<NiaPdmSditmMapVo> list) throws Exception {
		// TODO Auto-generated method stub
		int result = 0;
		String sditmId = null;
		String stndNm = mapper.getStndNm();
		for(NiaPdmSditmMapVo record : list){
			WamNiaPdmSditmMap map = new WamNiaPdmSditmMap();
			//1. sditm id 없으면 용어 존재 확인 후 없으면 저장 안됨
			//2. sditm id 있으면 바로 저장
			if(stndNm.equals("ORG")){
				sditmId = mapper.getSditmIdByNm(record);
			}else if(stndNm.equals("DB")){
				sditmId = mapper.getDbSditmIdByNm(record);
			}else{
				return 0;
			}
			
				
			if(sditmId != null && record.getMapId().isEmpty()){//신규저장 시에는 용어 없으면 안들어가게
				//엑셀 등록시 중복 저장 안되게
				record.setStndNm(stndNm);
				record.setSditmId(sditmId);
				String mapId = mapper.getMapIdByColSditm(record);
				if(mapId == null){
					
					String objid = objectIdGnrService.getNextStringId();
					map.setMapId(objid);
					map.setColId(record.getColId());
					map.setSditmId(sditmId);
					map.setStndNm(stndNm);
					
					result += mapper.insertPdmSditmMap(map);
				}else{
					record.setMapId(mapId);
					record.setSditmId(sditmId);
				}
				
			}
				
			if(!record.getMapId().isEmpty()){//수정시에는 용어 없어도 업데이트하여 비매핑으로 나오게
				if(record.getSditmId() == null)
					map.setStndNm(null);
				
				map.setMapId(record.getMapId());
				map.setColId(record.getColId());
				map.setSditmId(sditmId);
				map.setStndNm(stndNm);

				result += mapper.updateByMapId(map);
			}
	
		}
			
		return result;
	}
	
	@Override
	public int regPdmSditmMapAna(ArrayList<WamNiaPdmSditmMapAna> list) throws Exception {
		// TODO Auto-generated method stub
		int result = 0;
		for(WamNiaPdmSditmMapAna record : list){
			result = mapper.insertPdmSditmMapAna(record);
		}
		return result;
	}

	@Override
	public List<WamNiaPdmSditmMapAna> selectPdmSditmMapAna() throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectPdmSditmMapAna();
	}

	@Override
	public List<NiaPdmSditmMapVo> getPdmSditmMapList(NiaPdmSditmMapVo search) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectPdmSditmMapList(search);
	}
	
	@Override
	public List<WamNiaPdmSditmMapAna> getPdmSditmMapAna() throws Exception {
		// TODO Auto-generated method stub
		return mapper.getPdmSditmMapAna();
	}

	@Override
	public int delPdmSditmMapList(ArrayList<NiaPdmSditmMapVo> list) throws Exception {
		// TODO Auto-generated method stub
		
		int result = 0;
		for(NiaPdmSditmMapVo record : list){
			result = mapper.deletePdmSditmMapList(record);
		}
		return result;
	}

	@Override
	public List<NiaPdmSditmMapVo> getPdmAllMap(NiaPdmSditmMapVo search) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectPdmAllMapList(search);
	}

}
