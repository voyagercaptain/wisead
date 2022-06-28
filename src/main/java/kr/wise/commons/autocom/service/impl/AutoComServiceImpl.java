/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : AutoComServiceImpl.java
 * 2. Package : kr.wise.commons.autocom.service.impl
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 30. 오후 1:10:43
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 30. :            : 신규 개발.
 */
package kr.wise.commons.autocom.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.autocom.service.AutoComMapper;
import kr.wise.commons.autocom.service.AutoComService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : AutoComServiceImpl.java
 * 3. Package  : kr.wise.commons.autocom.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 30. 오후 1:10:43
 * </PRE>
 */
@Service("autoComService")
public class AutoComServiceImpl implements AutoComService{

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private AutoComMapper mapper;

	/** insomnia */
	@Override
	public List<String> getsearchTermList(Map<String, Object> searchmap) {
		//조회할 단어를 uppser처리한다....
		searchmap.put("searchTerm",  ((String)searchmap.get("searchTerm")).toUpperCase());

		String searchType = (String)searchmap.get("searchType");

		if("STWD".equals(searchType)) {
			return mapper.selectStndWordList(searchmap);
		} else if ("DMN".equals(searchType)) {
			return mapper.selectStndDmnList(searchmap);
		} else if ("SDITM".equals(searchType)) {
			return mapper.selectStndSditmList(searchmap);
		} else if ("SYMN".equals(searchType)) {
			return mapper.selectSymnList(searchmap);
		} else if ("SBSWD".equals(searchType)) {
			return mapper.selectSymnSbswdList(searchmap);
		} else if ("SUBJ".equals(searchType)) {
			return mapper.selectSubjList(searchmap);
		} else if ("PDMTBL".equals(searchType)) {
			return mapper.selectPdmTblList(searchmap);
		} else if ("PDMCOL".equals(searchType)) {
			return mapper.selectPdmColList(searchmap);
		} else if ("DDLTBL".equals(searchType)) {
			return mapper.selectDdlTblList(searchmap);
		} else if ("DBCTBL".equals(searchType)) {
			return mapper.selectDbcTblList(searchmap);
		} else if("BIZLNM".equals(searchType)){
			return mapper.selectBizLnmList(searchmap);
		} else if("DQILNM".equals(searchType)){
			return mapper.selectDqiLnmList(searchmap);
		} else if("CTQLNM".equals(searchType)){
			return mapper.selectCtqLnmList(searchmap);
		} else if("DBSCH".equals(searchType)){
			return mapper.selectDbSchList(searchmap);
		} else if("DBCCOL".equals(searchType)){
			return mapper.selectDbcColList(searchmap);
		} else if("BRNM".equals(searchType)){
			return mapper.selectBrNmList(searchmap);
		} else if("SHDLNM".equals(searchType)){
			return mapper.selectShdLnmList(searchmap);
		} else if("USRNM".equals(searchType)){
			return mapper.selectUserNmList(searchmap);
		} else if("OBJNM".equals(searchType)){
			return mapper.selectObjNmList(searchmap);
		} else if("DBMS".equals(searchType)){
			return mapper.selectDbmsList(searchmap);
		} else if("DEPT".equals(searchType)){
			return mapper.selectDeptList(searchmap);
		} else if("DMNG".equals(searchType)){
			return mapper.selectDmngList(searchmap);
		} else if("MENU".equals(searchType)){
			return mapper.selectMenuList(searchmap);
		}else if("DDLTSFTBL".equals(searchType)){
			return mapper.selectDdlTsfTblList(searchmap);
		}else if("SLCITEM".equals(searchType)) {
			return mapper.selectSlcItemList(searchmap);
		}else if("APD".equals(searchType)) {
			return mapper.selectAppStwdList(searchmap);
		}else if("API".equals(searchType)) {
			return mapper.selectAppSditmList(searchmap);
		}else if("ORGNM".equals(searchType)) {
			return mapper.selectOrgNmList(searchmap);
		}
		return null;
	}

}
