package kr.wise.dq.model.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamNiaPdmMapper {

	List<WamNiaPdmCol> selectNiaPdmColList(WamNiaPdmCol search);

	int insertNiaPdmCol(WamNiaPdmCol record);

	int deleteAll();
	
	List<WamNiaPdmCol> selectAnaPdm();

	List<WamNiaPdmCol> selectAnaCol();

	int insertNiaPdmAna(WamNiaPdmCol record);

	int insertNiaColAna(WamNiaPdmCol record);

	List<WamNiaPdmCol> selectCodeGapList(WamNiaPdmCol data);

	List<WamNiaPdmCol> selectDbCodeGapList(WamNiaPdmCol data);

	List<WamNiaPdmCol> selectAnaPdmLst();

	List<WamNiaPdmCol> selectAnaColLst();
	
	List<WamNiaPdmCol> selectDbCodeExistList(WamNiaPdmCol data);

	List<WamNiaPdmCol> selectCodeExistList(WamNiaPdmCol data);

	List<WamNiaPdmCol> deletedupData();

	int deleteByPrimaryKey(String id);

	int updateByPrimaryKey(WamNiaPdmCol saveVo);

	void selectByPk(WamNiaPdmCol saveVo);
	
}