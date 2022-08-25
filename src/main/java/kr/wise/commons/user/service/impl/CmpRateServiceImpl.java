package kr.wise.commons.user.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.wise.commons.user.WaaUserg;
import kr.wise.commons.user.service.CmpRateMapper;
import kr.wise.commons.user.service.CmpRateService;

@Service("cmpRateService")
public class CmpRateServiceImpl implements CmpRateService{
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private CmpRateMapper mapper;
	
	@Override
	public List<WaaUserg> getOrgCmpRateList(WaaUserg search) {
		logger.debug("getOrgCmpRateList Start.");
		List<WaaUserg> list = mapper.getOrgCmpRateList(search);
		return list;
	}

	@Override
	public List<WaaUserg> getDbCmpRateList(WaaUserg search) {
		logger.debug("getDbCmpRateList Start.");
		List<WaaUserg> list = mapper.getDbCmpRateList(search);
		List<WaaUserg> list2 = mapper.getDbCmpRateOrgItemList(search);

		Boolean isContains = false;

		for (WaaUserg orgItem : list2) {
			for (WaaUserg full : list) {
				if (full.getOrgNm().equals(orgItem.getOrgNm())) {
					isContains = true;
					full.setOrgItemCount(orgItem.getOrgItemCount());
				}
			}
			if (!isContains)
				list.add(orgItem);

			isContains = false;
		}
		return list;
	}

}
