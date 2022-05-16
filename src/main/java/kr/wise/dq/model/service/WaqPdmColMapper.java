package kr.wise.dq.model.service;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.dq.model.service.WaqPdmCol;

@Mapper
public interface WaqPdmColMapper {
    int insert(WaqPdmCol record);

    int insertSelective(WaqPdmCol record);
}