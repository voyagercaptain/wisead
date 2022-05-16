/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : DataSetService.java
 * 2. Package : kr.wise.advisor.prepare.dataset.service
 * 3. Comment : 
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2017. 9. 18. 오후 5:55:43
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2017. 9. 18. :            : 신규 개발.
 */
package kr.wise.advisor.prepare.dataset.service;

import java.util.List;

import kr.wise.advisor.prepare.outlier.service.WadOtlDtc;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgTblVO;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DataSetService.java
 * 3. Package  : kr.wise.advisor.prepare.dataset.service
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 9. 18. 오후 5:55:43
 * </PRE>
 */
public interface DataSetService {

	/** @param list
	/** @return insomnia 
	 * @throws Exception */
	List<WadDataSet> regDataSet(List<AnaTrgTblVO> list) throws Exception;

	/** @param varlist
	/** @return insomnia 
	 * @throws Exception */
	int regAnaVar(List<WadAnaVar> varlist) throws Exception;

	/** @param dslist
	/** @return insomnia */
	List<WadAnaVar> getAnaVar(List<WadDataSet> dslist);

	/** @param search
	/** @return insomnia */
	List<WadDataSet> getDataSetList(WadDataSet search);

	/** @param search
	/** @return insomnia */
	List<WadAnaVar> getAnaVarbyDs(WadDataSet search);

	/** @param search
	/** @return insomnia */
	WadAnaVar getAnlVarDtl(WadAnaVar search);

	/** @param rqstNo
	/** @return insomnia */
	List<WaaDbConnTrgVO> getDataSetListbyrqstNo(String rqstNo);

	/** @param search
	/** @return insomnia */
	WaaDbConnTrgVO getTgtDbmsbyds(WadDataSet search);

	/** @param dataset
	/** @return insomnia */
	List<WadAnaVar> getAnaVarListbyDsId(WadDataSet dataset);

	List<WadAnaVar> getDaseColList(WadDataSet search); 

}
