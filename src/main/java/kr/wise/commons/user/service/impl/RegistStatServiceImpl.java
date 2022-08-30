package kr.wise.commons.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.wise.commons.user.WaaUserg;
import kr.wise.commons.user.service.RegistStatMapper;
import kr.wise.commons.user.service.RegistStatService;

@Service("registStatService")
public class RegistStatServiceImpl implements RegistStatService{
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private RegistStatMapper mapper;
	
	@Override
	public List<WaaUserg> getRegistStatList(WaaUserg search) {
		logger.debug("getRegistStatList Start.");
		List<WaaUserg> list = mapper.getRegistStatList(search);
		return list;
	}

	@Override
	public List<WaaUserg> getDbRegistStatList(WaaUserg search) {
		logger.debug("getDbRegistStatList Start.");
		List<WaaUserg> list = mapper.getDbRegistStatList(search);
		return list;
	}

	@Override
	public List<WaaUserg> getTotalStatList(WaaUserg search) {
		logger.debug("getTotalStatList Start.");
		List<WaaUserg> list = mapper.getTotalStatList(search);
		return list;
	}

	@Override
	public List<WaaUserg> getTotalStatSubList(WaaUserg search) {
		logger.debug("getTotalStatSubList Start.");
		List<WaaUserg> list = mapper.getTotalStatSubList(search);

		Map map = mapper.getTotalOrgCount(new HashMap());
		Integer TotalCount = (Integer)map.get("CNT");

		for(WaaUserg thiswaa : list) {
			thiswaa.setTotalOrg(TotalCount);
		}

		return list;
	}

	@Override
	public List<WaaUserg> RegTotalOrgSelectlist(WaaUserg search) {
		logger.debug("RegTotalOrgSelectlist Start.");
		List<WaaUserg> list = mapper.RegTotalOrgSelectlist(search);
		return list;
	}

	@Override
	public List<Map> getTotalDbCountList1(Map map) {
		List<Map> list = mapper.getTotalDbCountList1(map);
		return list;
	}

	@Override
	public Map getTotalDbCount2(Map _map) {
		Map map = mapper.getTotalDbCount2(_map);
		return map;
	}

	@Override
	public Map getTotalOrgCount(Map _map) {
		Map map = mapper.getTotalOrgCount(_map);
		return map;
	}

	@Override
	public List<WaaUserg> selectDbList() {
		return mapper.selectDbList();
	}

}
