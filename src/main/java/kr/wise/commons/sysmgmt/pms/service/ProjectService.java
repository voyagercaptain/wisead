package kr.wise.commons.sysmgmt.pms.service;

import java.util.ArrayList;
import java.util.List;


public interface ProjectService {

	List<ProjectVO> selectProjectList(ProjectVO searchVO);

	ProjectVO selectProjectDetail(ProjectVO searchVO);

	int saveProject(ProjectVO record) throws Exception;

	

	/** @param list
	/** @return meta 
	 * @throws Exception */
	int regProject(ArrayList<ProjectVO> list) throws Exception;

	/** @param list
	/** @return meta */
	int deleteProject(ArrayList<ProjectVO> list);
	

}
