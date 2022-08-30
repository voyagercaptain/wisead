
package kr.wise.commons.user.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.user.WaaUserg;

@Mapper
public interface CmpRateMapper {
	 List<WaaUserg> getOrgCmpRateList(WaaUserg param);
	 List<WaaUserg> getDbCmpRateList(WaaUserg param);
	 List<WaaUserg> getDbCmpRateOrgItemList(WaaUserg param);
	 List<WaaUserg> getDbCmpRateOrgCodeList(WaaUserg param);
}
