package kr.wise.dq.dbstnd.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.dq.stnd.service.WamDmn;
import kr.wise.dq.stnd.service.WamSditm;
import kr.wise.dq.stnd.service.WamStwd;

public interface StndService {

	
	List<WamDbStcd> getStndCodelist(WamDbStcd data);
	
	int registerStcdWam(List<WamDbStcd> reglist) throws Exception;

	int decideStndCode(List<WamDbStcd> reglist, WaqMstr reqmst ) throws Exception;
	int initStndCode(List<WamDbStcd> reglist, WaqMstr reqmst ) throws Exception;

	int selectDupSdCodeCount(WamDbStcd data) throws Exception;


	//배치관련
	List<WamSditm> selectBatchSditmList(); //표준용어
	List<WamDmn>   selectBatchDmnList();   //표준도메인
	List<WamStwd>  selectBatchStwdList();  //표준단어
	List<WamDbStcd>  selectBatchStcdList();  //표준코드

	void updateStndTotInspect(List<WamSditm> sditmList,List<WamDmn> dmnList,List<WamStwd> stwdList,List<WamDbStcd> stcdList) throws Exception;

}
