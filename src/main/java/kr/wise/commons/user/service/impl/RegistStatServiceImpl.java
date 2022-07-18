package kr.wise.commons.user.service.impl;

import java.util.List;

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
		return list;
	}

}
