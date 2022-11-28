package kr.wise.commons.user.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.user.WaaUserg;

@Mapper
public interface RegistStatMapper {
	 List<WaaUserg> getRegistStatList(WaaUserg param);
	 List<WaaUserg> getDbRegistStatList(WaaUserg param);
	 List<WaaUserg> getTotalStatList(WaaUserg param);
	 List<WaaUserg> getTotalStatSubList(WaaUserg param);
	 List<WaaUserg> RegTotalOrgSelectlist(WaaUserg param);
	 List<WaaUserg> getApplyTotalStatSubList(WaaUserg param);

	 List<Map> getTotalDbCountList1(Map map);
	 Map getTotalDbCount2(Map map);
	 Map getTotalOrgCount(Map map);
	 List<WaaUserg> selectDbList();

}
