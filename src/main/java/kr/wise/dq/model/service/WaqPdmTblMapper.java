package kr.wise.dq.model.service;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.dq.model.service.WaqPdmTbl;

@Mapper
public interface WaqPdmTblMapper {
    int insert(WaqPdmTbl record);

    int insertSelective(WaqPdmTbl record);
}