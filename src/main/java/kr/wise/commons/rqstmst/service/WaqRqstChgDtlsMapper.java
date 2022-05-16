package kr.wise.commons.rqstmst.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqRqstChgDtlsMapper {

    List<WaqRqstChgDtls> selectStwdChgList(WaqMstr search);

	/** @param search
	/** @return meta */
	List<WaqRqstChgDtls> selectDmnChgList(WaqMstr search);

	/** @param search
	/** @return meta */
	List<WaqRqstChgDtls> selectItemChgList(WaqMstr search);

	/** @param search
	/** @return meta */
	List<WaqRqstChgDtls> selectPdmTblChgList(WaqMstr search);

	/** @param search
	/** @return meta */
	List<WaqRqstChgDtls> selectPdmColChgList(WaqMstr search);

	/** @param search
	/** @return meta */
	List<WaqRqstChgDtls> selectDdlTblChgList(WaqMstr search);

	/** @param search
	/** @return meta */
	List<WaqRqstChgDtls> selectDdlColChgList(WaqMstr search);

	/** @param search
	/** @return meta */
	List<WaqRqstChgDtls> selectTblMapChgList(WaqMstr search);
	
	/** @param search
	/** @return meta */
	List<WaqRqstChgDtls> selectCodMapChgList(WaqMstr search);

	/** @param search
	/** @return meta */
	List<WaqRqstChgDtls> selectBizAreaChgList(WaqMstr search);

	/** @param search
	/** @return meta */
	List<WaqRqstChgDtls> selectCtqChgList(WaqMstr search);

	/** @param search
	/** @return meta */
	List<WaqRqstChgDtls> selectDqiChgList(WaqMstr search);

	/** @param search
	/** @return meta */
	List<WaqRqstChgDtls> selectBizruleChgList(WaqMstr search);

	/** @param search
	/** @return meta */
	List<WaqRqstChgDtls> selectImPlChgList(WaqMstr search);

	/** @param search
	/** @return meta */
	List<WaqRqstChgDtls> selectImRslChgList(WaqMstr search);

	/** @param search
	/** @return meta */
	List<WaqRqstChgDtls> selectCodeCfcSysChgList(WaqMstr search);

	/** @param search
	/** @return meta */
	List<WaqRqstChgDtls> selectPdmRelChgList(WaqMstr search);

	/** @param search
	/** @return meta */
	List<WaqRqstChgDtls> selectDdlIdxColChgList(WaqMstr search);

	/** @param search
	/** @return meta */
	List<WaqRqstChgDtls> selectDdlIdxChgList(WaqMstr search);

	/** @param search
	/** @return meta */
	List<WaqRqstChgDtls> selectDdlRelChgList(WaqMstr search);

	/** @param search
	/** @return meta */
	List<WaqRqstChgDtls> selectDdlTsfIdxChgList(WaqMstr search);

	/** @param search
	/** @return meta */
	List<WaqRqstChgDtls> selectDdlTsfTblChgList(WaqMstr search);
	
	//메시지코드 변경사항 조회
	List<WaqRqstChgDtls> selectMsgChgList(WaqMstr search);

	//유효값 변경사항 조회
	List<WaqRqstChgDtls> selectCdValChgList(WaqMstr search);
	
	//코드이관 변경사항 조회
	List<WaqRqstChgDtls> selectCodeTsfChgList(WaqMstr search);
	
	//메시지이관 변경사항 조회
	List<WaqRqstChgDtls> selectMsgTsfChgList(WaqMstr search);
	
	//이관 대상 컬럼 변경사항 조회
	List<WaqRqstChgDtls> selectDdlColTsfChgList(WaqMstr search);
	
	//이관대상 인덱스 컬럼 변경사항 조회
	List<WaqRqstChgDtls> selectDdlIdxColTsfChgList(WaqMstr search);

	List<WaqRqstChgDtls> selectLdmAttrChgList(WaqMstr search);

	List<WaqRqstChgDtls> selectLdmEntyChgList(WaqMstr search);  
	
	
	
}