/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : ModelSubjService.java
 * 2. Package : kr.wise.meta.model.service
 * 3. Comment : 
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2013. 4. 15. 오전 10:57:55
 * 6. 변경이력 : 
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    insomnia 	: 2013. 4. 15. 		: 신규 개발.
 */
package kr.wise.dq.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.model.service.NiaModelPdmService;
import kr.wise.dq.model.service.WamNiaPdmCol;
import kr.wise.dq.model.service.WamNiaPdmMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("niaModelPdmService")

public class NiaModelPdmServiceImpl implements NiaModelPdmService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private WamNiaPdmMapper mapper;
	
	@Inject
	private EgovIdGnrService objectIdGnrService; 
	
	@Override
	public List<WamNiaPdmCol> getNiaPdmColList(WamNiaPdmCol search) throws Exception {
		
		List<WamNiaPdmCol> list = mapper.selectNiaPdmColList(search);
		
		return list;
	}
	
	public int regNiaPdmCol(WamNiaPdmCol saveVo) throws Exception {
		int result = 0;
		
		String tmpstatus = saveVo.getIbsStatus();

		if(UtilString.null2Blank(saveVo.getColId()).equals("")){
			tmpstatus = "I";
		}
		if ("I".equals(tmpstatus)) {
			if(saveVo.getColId().equals("")){
				//신규 등록 : 나중에 적재를 위해 미리 오브젝트 ID를 셋팅한다...
				String objid = objectIdGnrService.getNextStringId();
				saveVo.setColId(objid);
				saveVo.setRegTypCd("C");
				
				result = mapper.insertNiaPdmCol(saveVo);
			}else{//엑셀 등록 수정
				//업데이트
				saveVo.setRegTypCd("U");
				result = mapper.updateByPrimaryKey(saveVo);
			}
		}else if("U".equals(tmpstatus)){
			//업데이트
			saveVo.setRegTypCd("U");
			result = mapper.updateByPrimaryKey(saveVo);
		}else if("D".equals(tmpstatus)){
			result = mapper.deleteByPrimaryKey(saveVo.getColId());
		}
		
		
		return result;
	}
	
	@Override
	public int regNiaPdmColList(ArrayList<WamNiaPdmCol> list) throws Exception {
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
//		String tmpid = null;
		int result = 0;
		for (WamNiaPdmCol record : list) {
			//그리드 상태가 있을 경우만 DB에 처리한다...
				
				result += regNiaPdmCol(record);
//			}
			
			if(result < 0) return result;
		}
		
		return result;
	}

	@Override
	public int delWamNiaPdmColList(ArrayList<WamNiaPdmCol> list) throws Exception {
		
		int result = 0;
		
		for (WamNiaPdmCol record : list) {
			String id = record.getColId();
			result = mapper.deleteByPrimaryKey(id);
		}
		
		
		
		return result;
	}

	@Override
	public int delWamNiaPdmColAll() throws Exception {
		return mapper.deleteAll();
	}

	@Override
	public List<WamNiaPdmCol> getAnaPdm() throws Exception {
		List<WamNiaPdmCol> list = mapper.selectAnaPdm();
		
		return list;
	}

	@Override
	public List<WamNiaPdmCol> getAnaCol() throws Exception {
		logger.debug("getAnaCol >>> ");
		List<WamNiaPdmCol> list = mapper.selectAnaCol();
		logger.debug("getAnaCol list size >>> " + list.size());
		return list;
	}

	@Override
	public int regNiaPdmAna(ArrayList<WamNiaPdmCol> arrayList) throws Exception {
		// TODO Auto-generated method stub
		int result = 0;
		for (WamNiaPdmCol record : arrayList) {
			//그리드 상태가 있을 경우만 DB에 처리한다...
//			if(!UtilString.isBlank(record.getIbsStatus())) {
				
			result = mapper.insertNiaPdmAna(record);
//			}
			
			if(result < 0) return result;
		}
		
		return result;
	}

	@Override
	public int regNiaColAna(ArrayList<WamNiaPdmCol> arrayList) throws Exception {
		// TODO Auto-generated method stub
		int result = 0;
		for (WamNiaPdmCol record : arrayList) {
			//그리드 상태가 있을 경우만 DB에 처리한다...
			result = mapper.insertNiaColAna(record);
			
			if(result < 0) return result;
		}
		
		return result;
	}

	@Override
	public List<WamNiaPdmCol> getNiaPdmAnaList(WamNiaPdmCol search) throws Exception {
		List<WamNiaPdmCol> list = mapper.selectAnaPdmLst();
		
		return list;
	}

	@Override
	public List<WamNiaPdmCol> getNiaColAnaList(WamNiaPdmCol search) throws Exception {
		List<WamNiaPdmCol> list = mapper.selectAnaColLst();
		
		return list;
	}

	@Override
	public List<WamNiaPdmCol> delDupData() throws Exception{
		// TODO Auto-generated method stub
		
		
		return mapper.deletedupData();
	}
}
