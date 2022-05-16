/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : RequestMstAspect.java
 * 2. Package : kr.wise.commons.handler
 * 3. Comment : 요청서 업무 처리시 요청마스터에 bizInfo 셋팅 (AOP 방식)
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 4. 22. 오전 10:09:39
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 4. 22. :            : 신규 개발.
 */
package kr.wise.commons.handler;

import javax.inject.Inject;

import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaaBizInfo;
import kr.wise.commons.rqstmst.service.WaqMstr;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : RequestMstAspect.java
 * 3. Package  : kr.wise.commons.handler
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 4. 22. 오전 10:09:39
 * </PRE>
 */
@Aspect
public class RequestMstAspect {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private RequestMstService requestMstService;

	@Before("execution(public * kr.wise.commons.rqstmst.service.CommonRqstService.*(..)) && "+
//			"execution(public * kr.wise.commons.rqstmst.service.RequestMstService.getrequestMst(..)) && "+
			" args(mstVo,..)")
	public void before(WaqMstr mstVo) {
//		public String before(JoinPoint point) {
		logger.debug("@Aspect @Before:reqmst:{}", mstVo);

		WaaBizInfo bizInfo = requestMstService.getBizInfo(mstVo);

		mstVo.setBizInfo(bizInfo);

		logger.debug("@Before:reqmst.bizinfo:{}", mstVo.getBizInfo());

//		System.out.println("@Before:reqmst"+ mstVo.toString());

//		String method= point.getSignature().getName();
//		System.out.println("@Before:메소드-->" + method);

	}

}
