package kr.wise.dq.bizrule.service;

import java.util.ArrayList;

import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqBrDqiMapMapper {
	
    int insertSelective(WaqBrMstr record);

    int updateByPrimaryKeySelective(WaqBrMstr record);

	/** @param record
	/** @return meta */
	int deleteByPrimaryKey(WaqBrMstr record);

	/** @param savevo meta */
	void updatervwStsCd(WaqBrMstr savevo);

	/** @param savevo meta */
	int updateidByKey(WaqBrMstr savevo);

	/** @param rqstno meta */
	int updateWaqCUD(String rqstno);

	/** @param rqstno meta */
	int deleteWAM(String rqstno);

	/** @param rqstno meta */
	int insertWAM(String rqstno);

	/** @param rqstno meta */
	int updateWAH(String rqstno);

	/** @param rqstno meta */
	int insertWAH(String rqstno);

	/** @param rqstNo
	/** @return meta */
	int updateDelId(String rqstNo);

	/** @param rqstNo
	/** @return meta */
	int updateObjInfo(String rqstNo);

	/** @param reqmst
	/** @param list
	/** @return meta */
	int insertwam2waq(@Param("reqmst") WaqMstr reqmst, @Param("brmstr") WaqBrMstr brmstr);
//	int insertwam2waq(@Param("reqmst") WaqMstr reqmst, @Param("list")ArrayList<WaqBrMstr> list);

	int insertWamDqi(WamBrMstr record);

	int updateWamDqi(WamBrMstr record);

	int deleteWamDqi(WamBrMstr record);
}