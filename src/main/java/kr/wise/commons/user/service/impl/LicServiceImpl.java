/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : UserService.java
 * 2. Package : kr.wise.cmvw.user.service
 * 3. Comment :
 * 4. 작성자  : (ycyoo)유연철
 * 5. 작성일  : 2013. 4. 24.
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    유연철 	: 2013. 4. 24. 		: 신규 개발.
 */
package kr.wise.commons.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.security.License;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.sysmgmt.dept.service.WaaDept;
import kr.wise.commons.user.service.LicService;
import kr.wise.commons.user.service.WaaLic;
import kr.wise.commons.user.service.WaaLicMapper;
import kr.wise.commons.user.service.WaaUser;
import kr.wise.commons.util.UtilObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
//import seed.EncryptUtils;

/**
 * <PRE>
 * 1. ClassName : UserService
 * 2. Package  : kr.wise.cmvw.user.service
 * 3. Comment  :
 * 4. 작성자   : (ycyoo)유연철
 * 5. 작성일   : 2013. 4. 24.
 * </PRE>
 */
@Service("licService")
public class LicServiceImpl implements LicService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WaaLicMapper mapper;

	@Override
	public List<WaaLic> getList(WaaLic search) {

		List<WaaLic> list = mapper.selectList(search);

		return list;
	}

	public int regLic(WaaLic record) {
		String tmpStatus = record.getIbsStatus();
		int result = 0;
		
		//사용자ID가 없을경우 신규로, 그렇지 않을경우 수정으로 처리
		if("I".equals(tmpStatus) && !StringUtils.hasText(record.getLicKey())) {  // 신규...
			record.setRegTypCd("C");
			record.setLicKey(License.getLicense(record.getMacAddr()));
		} else if("U".equals(tmpStatus) || StringUtils.hasText(record.getLicKey())) {
			//사용자ID를 기반으로 데이터가 있는지 확인하여 없을경우 신규처리..
			WaaLic tmpVO = mapper.selectByPrimaryKey(record.getLicKey());
			if (tmpVO == null || !tmpVO.getLicKey().equals(record.getLicKey())) { 
				record.setObjVers(1);
				record.setRegTypCd("C");
				record.setLicKey(License.getLicense(record.getMacAddr())); // 암호화된 "1"이 기본 비밀번호
			} else {
				if (UtilObject.isNull(record.getObjVers())) {
					record.setObjVers(1);
				}
				else { 
					record.setObjVers(record.getObjVers()+1);
				}

				record.setLicKey(tmpVO.getLicKey());
				record.setRegTypCd("U");
				mapper.updateExpDtm(record);
			}
		} 
		
		result = mapper.insertSelective(record);
		
		return result;
	}

	@Override
	public int regList(ArrayList<WaaLic> list) {

		int result = 0;

		for (WaaLic waaLic : list) {
			result += regLic(waaLic);
		}

		return result;

	}


	@Override
	public int delList(ArrayList<WaaLic> list) {

		int result = 0;

		for (WaaLic waaLic : list) {
			String licKey = waaLic.getLicKey();
			if (licKey != null && !"".equals(licKey)) {
				waaLic.setIbsStatus("D");
				waaLic.setRegTypCd("D");
				result += mapper.updateExpDtm(waaLic);
			}
		}

		return result;

	}
}
