package kr.wise.dq.dbstnd.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.dq.stnd.service.WamSditm;

public interface DbStndService {

	List<WamDbSditm> getStndItemList(WamDbSditm data);
	List<WamDbDmn> getDomainList(WamDbDmn data);
	
	List<WamDbStwd> getStndWordList(WamDbStwd data);
	
	List<WamDbStcd> getStndCodelist(WamDbStcd data);
	
	int registerItemWam(List<WamDbSditm> reglist, WaqMstr reqmst) throws Exception;
	
	int registerDmnWam(List<WamDbDmn> reglist) throws Exception;
	
	int registerStwdWam(List<WamDbStwd> reglist) throws Exception;
	
	int registerStcdWam(List<WamDbStcd> reglist) throws Exception;

	List<WapDbDvCanAsm> getItemDvRqstList(WapDbDvCanDic data);
	
	Map<String, String>  regItemAutoDiv( List<WapDbDvCanAsm> list,WapDbDvCanDic record2) throws Exception;
	
	Map<String, String>  delItemAutoDiv( List<WapDbDvCanAsm> list) throws Exception;
	
	List<WamDbSditm> selectUserDbList(String userId);
	List<WamDbSditm> selectUserOrgList(String userId);
	WamDbSditm selectUserOrg(String userId);
	
	List getDbList(WamDbSditm data);
	
	Map<String, String> selectDomainDataType(Map<String, String> param);
	
	Map<String, String> selectDbDomainDataType(Map<String, String> param);
	
	int dupliCheckDbStndItem(Map<String, String> param);
	
	int dupliCheckDbStndDmn(Map<String, String> param);
	
	int dupliCheckDbStndStwd(Map<String, String> param);
	
	int dupliCheckDbStndStcd(Map<String, String> param);
	
	int initDbStndItem(List<WamDbSditm> reglist, WaqMstr reqmst) throws Exception;
	
	int initDbStndDmn(List<WamDbDmn> reglist, WaqMstr reqmst) throws Exception;
	
	int initDbStndStwd(List<WamDbStwd> reglist, WaqMstr reqmst) throws Exception;
	
	int initDbStndStcd(List<WamDbStcd> reglist, WaqMstr reqmst) throws Exception;

	int bulkUpdateConfirm2(WamDbSditm data);

	int bulkUpdateConfirm2(WamDbDmn data);

	int bulkUpdateConfirm2(WamDbStwd data);

	int bulkUpdateConfirm2(WamDbStcd data);

	
	List<WamDbSditm> selectDbSditmList();
	List<WamDbDmn>   selectDbDmnList();
	List<WamDbStwd>  selectDbStwdList();
	List<WamDbStcd>  selectDbStcdList();
	Integer getStndItemTotalCnt(WamDbSditm data);
	Integer getDomainTotalCnt(WamDbDmn data);
	Integer getStndWordTotalCnt(WamDbStwd data);
	Integer getStndCodeTotalCnt(WamDbStcd data);
	void updateDbStndTotInspect(List<WamDbSditm> sditmList,List<WamDbDmn> dmnList,List<WamDbStwd> stwdList,List<WamDbStcd> stcdList) throws Exception;
	List<WamDbSditm> getDbStndItemList(WamDbSditm data);
	List<WamDbDmn> getDbStndDmnList(WamDbDmn data);
	List<WamDbStwd> getDbStndStwdList(WamDbStwd data);
	List<WamDbStcd> getDbStndStcdList(WamDbStcd data);
	
}
