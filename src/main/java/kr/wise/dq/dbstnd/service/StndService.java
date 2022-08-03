package kr.wise.dq.dbstnd.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.dq.stnd.service.WamStwd;

public interface StndService {

	
	List<WamDbStcd> getStndCodelist(WamDbStcd data);
	
	int registerStcdWam(List<WamDbStcd> reglist) throws Exception;

	int decideStndCode(List<WamDbStcd> reglist, WaqMstr reqmst ) throws Exception;
	int initStndCode(List<WamDbStcd> reglist, WaqMstr reqmst ) throws Exception;

	int selectDupSdCodeCount(WamDbStcd data) throws Exception;
}
