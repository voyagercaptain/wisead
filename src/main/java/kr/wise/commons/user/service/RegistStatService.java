package kr.wise.commons.user.service;

import java.util.List;

import kr.wise.commons.user.WaaUserg;

public interface RegistStatService {
	
	public List<WaaUserg> getRegistStatList(WaaUserg search);
	public List<WaaUserg> getDbRegistStatList(WaaUserg search);
	public List<WaaUserg> getTotalStatList(WaaUserg search);
	public List<WaaUserg> getTotalStatSubList(WaaUserg search);
	public List<WaaUserg> RegTotalOrgSelectlist(WaaUserg search);
}
