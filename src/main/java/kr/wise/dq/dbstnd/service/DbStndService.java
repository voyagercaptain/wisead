package kr.wise.dq.dbstnd.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.WaqMstr;

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
}
