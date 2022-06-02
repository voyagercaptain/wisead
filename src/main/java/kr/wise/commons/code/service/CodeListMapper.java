/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : CodeListMapper.java
 * 2. Package : kr.wise.egmd.cmcd.mapper
 * 3. Comment :
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2013. 4. 15. 오후 4:30:23
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    insomnia 	: 2013. 4. 15. 		: 신규 개발.
 */
package kr.wise.commons.code.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;


/**
 * <PRE>
 * 1. ClassName : CodeListMapper
 * 2. Package  : kr.wise.egmd.cmcd.mapper
 * 3. Comment  : 목록성 코드를 가져오는 공통 매퍼...
 * 4. 작성자   : insomnia(장명수)
 * 5. 작성일   : 2013. 4. 15.
 * </PRE>
 */
@Mapper
public interface CodeListMapper {

	List<CodeListVo> metaComCodeList(String codenm);	//메타 공통코드 리스트
	List<CodeListVo> dqComCodeList(String codenm);	//DQ 공통코드 리스트
	List<CodeListVo> ptComCodeList(String codenm);  //포탈 공통코드 리스트

	List<CodeListVo> getProjectCodeList();			//프로젝트 리스트
	List<CodeListVo> getClCodeList();				//코드분류 리스트-전자정부용
	List<CodeListVo> getComCodeList();				//공통코드 리스트-전자정부용

	List<CodeListVo> getComDcdList();				//공통코드 리스트-제품용

	List<CodeListVo> getSysareaCodeList();	//시스템영역 목록 리스트
	List<CodeListVo> getUsergroupCodeList();
	List<CodeListVo> getUsergrouplnmCodeList();
	List<CodeListVo> getDbmsTypVersCodeList();
	List<CodeListVo> getDmngCodeList();	//도메인그룹 목록 리스트
	List<CodeListVo> getInfoTpCodeList();	//인포타입 목록 리스트
	List<CodeListVo> getDmngInfoTpCodeList();	//도메인그룹매핑 목록 리스트


	List<CodeListVo> getBbsAttrbCodeList(); //게시판 속성 코드 리스트
	List<CodeListVo> getBbsTyCodeList(); //게시판 유형 코드 리스트
	List<CodeListVo> gettmplatIdList(); //게시판 템플릿 유형 코드 리스트
	List<CodeListVo> getcateCodeList(); //게시판 업무 구분 코드 리스트
	/** @return insomnia */
	List<CodeListVo> getApproveGroup(); //결재그룹 코드 리스트
	/** @return meta */
	List<CodeListVo> getConnTrgDbms(); //진단대상 DBMS 코드 리스트
	List<CodeListVo> getAbrTempList(String userId); //표준단어 약어생성 임시저장테이블 코드 리스트
	List<CodeListVo> getDvRqstNoList(String userId); //표준항목 자동분할 ID
	List<CodeListVo> getDbDvRqstNoList(String userId); //표준항목 자동분할 ID
	
	
	List<CodeListVo> getDvDmnRqstNoList(String userId); //표준도메인 자동분할 ID
	List<CodeListVo> getAnaDgr(); //실행차수 리스트
	List<CodeListVo> getDbmsDataType(); //DBMS 데이터타입
	/** @return meta */
	List<CodeListVo> getDbmsDataTyp(); //DBMS 데이터타입(double_select)
	/** @return meta */
	List<CodeListVo> getSubjLnm(); //주제영역명 리스트
	/** @return meta */
	List<CodeListVo> getDbSchLnm(); //DB스키마명 리스트
	List<CodeListVo> getConnTrgSch(); // 진단대상, 스키마 리스트(PNM)(double_select)
	List<CodeListVo> getConnTrgSchId();// 진단대상, 스키마 리스트(ID)(double_select)
    List<CodeListVo> getConnTrgSchId2();// 진단대상, 스키마 리스트(ID)(double_select) 메타관리대상만
    List<CodeListVo> getConnTrgSchIdCodeTsf();// 진단대상, 스키마 리스트(ID)(double_select) 이관전용,코드전송대상만(개발제외)
	/**
	 * @param codenm
	 * @return
	 */
	List<CodeListVo> getPrfAnaDgr(); //실행차수 - 스케줄 관리용
	/**
	 * @param codenm
	 * @return
	 */
	List<CodeListVo> getBrAnaDgr(String codenm);
	/** @return insomnia */
	List<CodeListVo> getLangType();
	/** @return meta */
	List<CodeListVo> getDmngLowDgr(); //최하위차수 도메인그룹 정보 가져오는 리스트...
	/** @return meta */
	List<CodeListVo> getSubjPdmTbl();  //주제영역/물리테이블 리스트 조회...
	/** @return meta */
	List<CodeListVo> getDbSchForDoubleSelectIBS(); //IBSheet DoubleSelect용 스키마명 조회
	/** @return meta */
	List<CodeListVo> getDbConnTrgIdInDbRole();  //WAA_DB_ROLE에 등록된 DBMS명만 가져오는 쿼리
	/** @return meta */
	List<CodeListVo> getRoleNmForDoubleSelectIBS(); //ROLE명 리스트(IBSheet 더블SELECT용)
	/** @return meta */
	List<CodeListVo> getDbRoleNm(); //DB/ROLE명 더블SELECT
	/** @return meta */
	List<CodeListVo> getDbSchLnmOnly(); //스키마명만 select
	
	
	/** @return meta 
	 * 도메인에 정의된 도메인 유효값 조회
	 * */
	List<CodeListVo> getClSystmTyCode(); 
	List<CodeListVo> getClSystmTyIemCode();
	/** @return meta */
	List<CodeListVo> getTblSpacLnm();  //테이블스페이스명
	
	List<CodeListVo> getTblSpacId();  //테이블스페이스
	
	List<CodeListVo> getIdxSpacId();  //인덱스스페이스
	
	List<CodeListVo> getDbGapSrcSchId();  //소스스키마ID
	
	List<CodeListVo> getDbGapTgtSchId();  //타겟스키마ID

	List<CodeListVo> getInfoTpCodeListIBS();
	
	List<CodeListVo> getCodeDmngInfoTpCodeList();	//도메인그룹매핑 목록 리스트
	
	List<CodeListVo> getDevConnTrgSchId();
	
	List<CodeListVo> getDevConnTrgSchId2();
	
    List<CodeListVo> getSubjLvl1();
    
    List<CodeListVo> getDevDbSchLnm();
	/** @return insomnia */
	List<CodeListVo> getOtlAlgId();
	
	List<CodeListVo> getOtlAnaDgr(String codenm);
	List<CodeListVo> getUodAnaDgr(String codenm);
	List<CodeListVo> getOtlTypCd();
	List<CodeListVo> getOtlDqiMap();
	List<CodeListVo> getTmAnaDgr(String codenm);
	
	List<CodeListVo> getConnTrgSchIdMdl();
	
	List<CodeListVo> getVrfcIds();
	
	List<CodeListVo> getOrgCdList();
	
	List<CodeListVo> getOrgList(String userId);
	
}
