package kr.wise.commons.test;

import kr.wise.commons.test.service.toTestService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
//@RequestMapping("/commons/test")
public class testCtrl {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private toTestService totestService;

	@RequestMapping("/commons/test/testTrans.do")
	void transactionTest() throws Exception {

		try{

		totestService.testmethod1() ;
		} catch (Exception e) {
			throw e;
		}

	}
}
