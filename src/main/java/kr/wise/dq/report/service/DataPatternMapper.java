/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ShdDptrnMapper.java
 * 2. Package : kr.wise.commons.damgmt.schedule.service
 * 3. Comment :
 * 4. 작성자  : kchoi
 * 5. 작성일  : 2014. 5. 2. 오후 1:24:18
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    kchoi : 2014. 5. 2. :            : 신규 개발.
 */
package kr.wise.dq.report.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : WamShdDptrnMapper.java
 * 3. Package  : kr.wise.commons.damgmt.schedule.service
 * 4. Comment  :
 * 5. 작성자   : kchoi
 * 6. 작성일   : 2014. 5. 2. 오후 1:24:18
 * </PRE>
 */
@Mapper
public interface DataPatternMapper {

	List<DataPatternVO> dptrnList(DataPatternVO search);

	DataPatternVO dptrnHeader(DataPatternVO search);

	DataPatternVO DptrnHeaderText(DataPatternVO search);

	DataPatternVO selectPrfAnaResDtl(DataPatternVO search);

	DataPatternVO selectBrAnaResDtl(DataPatternVO search);

	List<DataPatternVO> selectPkDptrnList(DataPatternVO search);

	DataPatternVO PkDptrnHeaderText(DataPatternVO search);

	/** @param search
	/** @return insomnia */
	List<DataPatternVO> dptrnListNoDate(DataPatternVO search);

}
