package kr.wise.dq.model.service;

import java.util.ArrayList;
import java.util.List;

public interface NiaModelPdmService {

	int regNiaPdmColList(ArrayList<WamNiaPdmCol> arrayList) throws Exception;

	List<WamNiaPdmCol> getNiaPdmColList(WamNiaPdmCol search) throws Exception;

	int delWamNiaPdmColList(ArrayList<WamNiaPdmCol> arrayList) throws Exception;

	int delWamNiaPdmColAll() throws Exception;

	List<WamNiaPdmCol> getAnaPdm() throws Exception;

	List<WamNiaPdmCol> getAnaCol() throws Exception;

	int regNiaPdmAna(ArrayList<WamNiaPdmCol> arrayList) throws Exception;

	int regNiaColAna(ArrayList<WamNiaPdmCol> arrayList) throws Exception;

	List<WamNiaPdmCol> getNiaPdmAnaList(WamNiaPdmCol search) throws Exception;

	List<WamNiaPdmCol> getNiaColAnaList(WamNiaPdmCol search) throws Exception;

	List<WamNiaPdmCol> delDupData() throws Exception;

}
