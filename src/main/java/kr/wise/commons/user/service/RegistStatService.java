package kr.wise.commons.user.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.user.WaaUserg;
import kr.wise.dq.dbstnd.service.WamDbSditm;

public interface RegistStatService {
	
	public List<WaaUserg> getRegistStatList(WaaUserg search);
	public List<WaaUserg> getDbRegistStatList(WaaUserg search);
	public List<WaaUserg> getTotalStatList(WaaUserg search);
	public List<WaaUserg> getTotalStatSubList(WaaUserg search);
	public List<WaaUserg> RegTotalOrgSelectlist(WaaUserg search);

	List<Map> getTotalDbCountList1(Map map);
	Map getTotalDbCount2(Map map);
	Map getTotalOrgCount(Map map);
	List<WaaUserg> selectDbList();
}
