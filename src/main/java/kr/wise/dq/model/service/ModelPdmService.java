/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ModelPdmService.java
 * 2. Package : kr.wise.meta.model.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 3. 31. 오후 3:36:30
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 3. 31. :            : 신규 개발.
 */
package kr.wise.dq.model.service;

import java.util.List;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : ModelPdmService.java
 * 3. Package  : kr.wise.meta.model.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 3. 31. 오후 3:36:30
 * </PRE>
 */
public interface ModelPdmService {
	public List<WamPdmTbl> getPdmTblList(WamPdmTbl search) ;

	public List<WamPdmTbl> getPdmTblHist(WamPdmTbl search);

	public List<WamPdmCol> getPdmColList(WamPdmCol search) ;
	
	public List<WamPdmTbl> getPdmTblHistList(WamPdmTbl search);

	public List<WamPdmCol> getPdmColHistList(WamPdmTbl search);
}
