/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DbRoleService.java
 * 2. Package : kr.wise.commons.damgmt.db.service
 * 3. Comment : 
 * 4. 작성자  : meta
 * 5. 작성일  : 2014. 8. 13. 오전 10:19:55
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    meta : 2014. 8. 13. :            : 신규 개발.
 */
package kr.wise.commons.damgmt.db.service;

import java.util.ArrayList;
import java.util.List;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DbRoleService.java
 * 3. Package  : kr.wise.commons.damgmt.db.service
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 8. 13. 오전 10:19:55
 * </PRE>
 */
public interface DbRoleService {

	/** @param search
	/** @return meta */
	List<WaaDbRole> getDbRoleList(WaaDbRole search);

	/** @param list
	/** @return meta 
	 * @throws Exception */
	int regDbRoleList(ArrayList<WaaDbRole> list) throws Exception;

	/** @param list
	/** @return meta */
	int delDbRoleList(ArrayList<WaaDbRole> list);

}
