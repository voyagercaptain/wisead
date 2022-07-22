package kr.wise.commons.user.service;

import java.util.List;

import kr.wise.commons.user.WaaUserg;

public interface CmpRateService {
	public List<WaaUserg> getOrgCmpRateList(WaaUserg search);
	public List<WaaUserg> getDbCmpRateList(WaaUserg search);
}
