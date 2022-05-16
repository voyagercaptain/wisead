/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : PdmTblRqstServiceImpl.java
 * 2. Package : kr.wise.meta.model.service.impl
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 2. 오후 4:42:57
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 2. :            : 신규 개발.
 */
package kr.wise.dq.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilObject;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.model.service.PdmTblRqstService;
import kr.wise.dq.model.service.WahPdmColMapper;
import kr.wise.dq.model.service.WahPdmTblMapper;
import kr.wise.dq.model.service.WamPdmCol;
import kr.wise.dq.model.service.WamPdmColMapper;
import kr.wise.dq.model.service.WamPdmTbl;
import kr.wise.dq.model.service.WamPdmTblMapper;


/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : PdmTblRqstServiceImpl.java
 * 3. Package  : kr.wise.meta.model.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 2. 오후 4:42:57
 * </PRE>
 */
@Service("pdmTblRqstService")
public class PdmTblRqstServiceImpl implements PdmTblRqstService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WamPdmTblMapper wammapper;
	
	@Inject
	private WahPdmTblMapper wahmapper;

	@Inject
	private WamPdmColMapper wamcolmapper;

	@Inject
	private WahPdmColMapper wahcolmapper;

    @Inject
    private EgovIdGnrService objectIdGnrService;

	@Override
	public int register(WaqMstr mstVo, List<?> reglist) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int check(WaqMstr mstVo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int submit(WaqMstr mstVo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<WamPdmTbl> getPdmTblList(WamPdmTbl search) {
		// TODO Auto-generated method stub
		return wammapper.selectPdmTblList(search);
	}

	@Override
	public int regList(ArrayList<WamPdmTbl> list) throws Exception {

		int result = 0;

		for (WamPdmTbl pdmTbl : list) {
			result += regPdmTbl(pdmTbl);
		}

		return result;

	}


	@Override
	public int delList(ArrayList<WamPdmTbl> list) {

		int result = 0;

		for (WamPdmTbl pdmTbl : list) {
			String id = pdmTbl.getPdmTblId();
			if (id != null && !"".equals(id)) {
				pdmTbl.setIbsStatus("D");

				result += wahmapper.wam2wah(pdmTbl);
				result += wammapper.deleteByPrimaryKey(pdmTbl);
			}
		}

		return result;

	}
	
	@Override
	public int regPdmTbl(WamPdmTbl pdmTbl) throws Exception {
		String tmpStatus = pdmTbl.getIbsStatus();
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;
		
		if("I".equals(tmpStatus) && !StringUtils.hasText(pdmTbl.getPdmTblId())) {  // 신규...
			String id = objectIdGnrService.getNextStringId();
			pdmTbl.setPdmTblId(id);
			pdmTbl.setObjVers(1);
			pdmTbl.setRegTypCd("C");
			
		} else if("U".equals(tmpStatus) || StringUtils.hasText(pdmTbl.getPdmTblId())) {
			//사용자ID를 기반으로 데이터가 있는지 확인하여 없을경우 신규처리..
			WamPdmTbl tmpVO = wammapper.selectByPrimaryKey(pdmTbl.getPdmTblId());
			if (tmpVO == null || !tmpVO.getPdmTblId().equals(pdmTbl.getPdmTblId())) { 
				String id = objectIdGnrService.getNextStringId();
				pdmTbl.setPdmTblId(id);
				pdmTbl.setObjVers(1);
				pdmTbl.setRegTypCd("C");
			} else {
				if (UtilObject.isNull(pdmTbl.getObjVers())) {
					pdmTbl.setObjVers(1);
				}
				else { 
					pdmTbl.setObjVers(pdmTbl.getObjVers()+1);
				}

				pdmTbl.setRegTypCd("U");
				wahmapper.wam2wah(pdmTbl);
			}
		} 
		
		if(!pdmTbl.getRegTypCd().equals("C"))
			wammapper.deleteByPrimaryKey(pdmTbl);
		
		pdmTbl.setRqstUserId(user.getUniqId());
		result = wammapper.insertSelective(pdmTbl);
		logger.debug("pdmTblId >>> " + pdmTbl.getPdmTblId());
		
		return result;
	}
    
	@Override
	public List<WamPdmCol> getPdmColList(WamPdmCol search) throws Exception{
		return wamcolmapper.selectPdmColList(search);
	}
	

	@Override
	public int regColList(ArrayList<WamPdmCol> list) throws Exception {

		int result = 0;

		for (WamPdmCol pdmCol : list) {
			result += regPdmCol(pdmCol);
		}

		return result;

	}
	
	
	public int regPdmCol(WamPdmCol pdmCol) throws Exception {
		String tmpStatus = pdmCol.getIbsStatus();
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;
		try {
		if("I".equals(tmpStatus) && !StringUtils.hasText(pdmCol.getPdmColId())) {  // 신규...
			String id = objectIdGnrService.getNextStringId();
			pdmCol.setPdmColId(id);
			pdmCol.setObjVers(1);
			pdmCol.setRegTypCd("C");
			
		} else if("U".equals(tmpStatus) || StringUtils.hasText(pdmCol.getPdmColId())) {
			//사용자ID를 기반으로 데이터가 있는지 확인하여 없을경우 신규처리..
			WamPdmCol tmpVO = wamcolmapper.selectByPrimaryKey(pdmCol.getPdmColId());
			if (tmpVO == null || !tmpVO.getPdmColId().equals(pdmCol.getPdmColId())) { 
				String id = objectIdGnrService.getNextStringId();
				pdmCol.setPdmColId(id);
				pdmCol.setObjVers(1);
				pdmCol.setRegTypCd("C");
			} else {
				if (UtilString.null2Blank(pdmCol.getObjVers()).equals("")) {
					pdmCol.setObjVers(1);
				}
				else { 
					pdmCol.setObjVers(pdmCol.getObjVers()+1);
				}

				pdmCol.setRegTypCd("U");
				wahcolmapper.wam2wah(pdmCol);
			}
		} 

		if(!pdmCol.getRegTypCd().equals("C"))
			wamcolmapper.deleteByPrimaryKey(pdmCol);

		pdmCol.setRqstUserId(user.getUniqId());
		result = wamcolmapper.insertSelective(pdmCol);
		logger.debug("\n\n{}\n\n",pdmCol);
		wamcolmapper.updateTblId(pdmCol);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	public int delColList(ArrayList<WamPdmCol> list) throws Exception {

		int result = 0;

		for (WamPdmCol pdmCol : list) {
			result += delPdmCol(pdmCol);
		}

		return result;

	}
	
	
	public int delPdmCol(WamPdmCol pdmCol) throws Exception {
//		String tmpStatus = pdmCol.getIbsStatus();
//		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;
		try {
			if (UtilString.null2Blank(pdmCol.getObjVers()).equals("")) {
				pdmCol.setObjVers(1);
			}
			else { 
				pdmCol.setObjVers(pdmCol.getObjVers()+1);
			}
			
			pdmCol.setRegTypCd("D");
			result = wahcolmapper.wam2wah(pdmCol);

			result = wamcolmapper.deleteByPrimaryKey(pdmCol);
		
		} catch(Exception e) {
			result = 0;
			e.printStackTrace();
		}
		
		return result;
	}
    
}
