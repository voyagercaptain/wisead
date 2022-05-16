package kr.wise.dq.stnd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;


@Mapper
public interface WaqStwdMapper extends CommonRqstMapper {
    int deleteByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int insert(WaqStwd record);

    int insertSelective(WaqStwd record);

    WaqStwd selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int updateByPrimaryKeySelective(WaqStwd record);

    int updateByPrimaryKey(WaqStwd record);

	/** @return insomnia */
	WaqStwd selectRqstStndWord(WaqStwd searchVO);

	/** @return insomnia */
	List<WaqStwd> selectrqstStndWordListbyMst(WaqMstr search);
	
	//신청된 단어가 있는지 체크
	List<WaqStwd> selectExistsWordCheck(WaqMstr search);

	/** @param saveVo
	/** @return insomnia */
	int deleteByrqstSno(WaqStwd saveVo);

	//검증 쿼리 리스트

	/** @param checkmap insomnia */
	int updateCheckInit(Map<String, Object> checkmap); 

	/** @param rqstNo insomnia */
	int checkDupStwd(Map<String, Object> checkmap);

	/** @param rqstNo insomnia */
	int checkNotExistStwd(Map<String, Object> checkmap);

	int checkDupStwdLnm(Map<String, Object> checkmap);
	
	int checkNtChek(Map<String, Object> checkmap);
	
	int checkSwChek(Map<String, Object> checkmap);

	int checkDupStwdPnm(Map<String, Object> checkmap);

	/** @param rqstNo insomnia */
	int checkUseStwd(Map<String, Object> checkmap);

	/** @param rqstNo insomnia */
	int checkExistSymn(Map<String, Object> checkmap);

	/** @param rqstNo insomnia */
	int checkLnmExistSpac(Map<String, Object> checkmap);

	/** @param rqstNo insomnia */
	int checkPnmExistSpac(Map<String, Object> checkmap);

	/** @param rqstNo insomnia */
	int checkLnmMaxLen(Map<String, Object> checkmap);

	/** @param rqstNo insomnia */
	int checkPnmMaxLen(Map<String, Object> checkmap);

	/** @param rqstNo insomnia */
	int checkNoChg(Map<String, Object> checkmap);

	/** @param rqstNo insomnia */
	int updateVrfCd(String rqstNo);

	/** @param ids
	/** @return insomnia */
	int deletebyRqstSno(@Param("ids") Integer[] ids, @Param("rqstNo") String rqstNo);

	/** @param savevo insomnia */
	int updatervwStsCd(WaqStwd savevo);

	/** @param rqstno
	/** @return insomnia */
	List<WaqStwd> selectWaqC(@Param("rqstNo") String rqstNo);

	/** @param savevo
	/** @return insomnia */
	int updateidByKey(WaqStwd savevo);

	/** @param reqmst
	/** @param list insomnia */
	int insertwam2waq(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WaqStwd> list);

	/** @param rqstNo
	/** @param string
	/** @return insomnia */
	List<WaqStwd> selectListByStwdLnm(@Param("rqstNo") String rqstNo, @Param("stwdLnm") String stwdLnm, @Param("stndAsrt") String stndAsrt);

	/** @param checkmap insomnia */
	int checkRequestStwd(Map<String, Object> checkmap);
	
	//표준단어 동음이의어 존재
	int checkHomonym(Map<String, Object> checkmap);
	
	//표준단어 물리모델명 존재여부 //20150701 이상익 추가
	int checkPnmExists(Map<String, Object> checkmap);
	
	//표준단어 설명 존재여부 체크
	int checkObjDesn(Map<String, Object> checkmap);
	
	int insertWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );

	int updateStwdDelInfo(String rqstNo);
}