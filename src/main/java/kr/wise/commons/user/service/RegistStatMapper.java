package kr.wise.commons.user.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.user.WaaUserg;

@Mapper
public interface RegistStatMapper {
	 List<WaaUserg> getRegistStatList(WaaUserg param);
	 List<WaaUserg> getDbRegistStatList(WaaUserg param);
}
