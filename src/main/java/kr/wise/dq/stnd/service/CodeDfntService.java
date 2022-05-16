package kr.wise.dq.stnd.service;

import java.util.ArrayList;
import java.util.List;

import kr.wise.dq.model.service.WamNiaPdmCol;

public interface CodeDfntService {

	List<WamCdVal> getCodeList(WamCdVal data);

	int registerWam(ArrayList<WamCdVal> list) throws Exception;

	List<DbWamCdVal> getDbCodeList(DbWamCdVal data);

	int registerDbWam(ArrayList<DbWamCdVal> list) throws Exception;

//	List<WamNiaPdmCol> getCodeGapList(WamCdVal data) throws Exception;
////	
//	List<WamNiaPdmCol> getDbCodeGapList(DbWamCdVal data) throws Exception;

	List<WamNiaPdmCol> getCodeGapList(WamNiaPdmCol data) throws Exception;

	List<WamNiaPdmCol> getDbCodeGapList(WamNiaPdmCol data) throws Exception;

	List<WamNiaPdmCol> getDbCodeExistList(WamNiaPdmCol data);

	List<WamNiaPdmCol> getCodeExistList(WamNiaPdmCol data);

}
