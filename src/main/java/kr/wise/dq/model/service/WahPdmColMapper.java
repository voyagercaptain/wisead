package kr.wise.dq.model.service;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WahPdmColMapper {
    int insert(WahPdmCol record);

    int insertSelective(WahPdmCol record);

	int wam2wah(WamPdmCol pdmCol);
}