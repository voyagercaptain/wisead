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
		List<WaaUserg> list = mapper.selectList(search);
		return list;
	}

}