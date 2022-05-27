package kr.wise.dq.dbstnd.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.WaqMstr;

public interface StndService {

	
	List<WamDbStcd> getStndCodelist(WamDbStcd data);
	
	int registerStcdWam(List<WamDbStcd> reglist) throws Exception;

}
