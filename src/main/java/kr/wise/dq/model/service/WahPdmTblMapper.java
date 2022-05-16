package kr.wise.dq.model.service;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WahPdmTblMapper {
    int insert(WahPdmTbl record);

    int insertSelective(WahPdmTbl record);

	int wam2wah(WamPdmTbl pdmTbl);
}