package kr.wise.dq.gap.service;

import java.util.List;

public interface GapMainService {

	List<ModelGapVO> getModelGapAnalyze(ModelGapVO search);

	/** @return yeonho */
	List<ModelGapVO> getGapChart();

	List<ModelGapVO> getGapChartDateJSON();
	
	List<ModelGapVO> getModelDdlColGapList(ModelGapVO search);
	
	List<ModelGapVO> getPdmDdlGapList(ModelGapVO search);

	List<ModelGapVO> getDdlDbcGapList(ModelGapVO search);
	
	List<ModelGapVO> getDdlDbcColGapList(ModelGapVO search);
	
	List<ModelGapVO> getMartPdmGapList(ModelGapVO search);
	
	List<ModelGapVO> getMartPdmColGapList(ModelGapVO search);
	
	List<ModelGapVO> getDdlTsfGapList(ModelGapVO search);
	
	List<ModelGapVO> getDdlTsfColGapList(ModelGapVO search);
	
	List<ModelGapVO> getDdlTsfDbcGapList(ModelGapVO search);
	
	List<ModelGapVO> getDdlTsfDbcColGapList(ModelGapVO search);
	
	List<ModelGapVO> getDbcGapList(ModelGapVO search);
	
    List<ModelGapVO> getDbcColGapList(ModelGapVO search);

	List<ModelGapVO> getMdlDevDbTblGapList(ModelGapVO search);

	List<ModelGapVO> getMdlDevDbColGapList(ModelGapVO search);

	List<ModelGapVO> getNonStndPdmColGapList(ModelGapVO search);

	List<ModelGapVO> getErwinPdmTblGapList(ModelGapVO search); 

	List<ModelGapVO> getErwinPdmColGapList(ModelGapVO search);

    List<ModelGapVO> getSrcDbSchList(ModelGapVO search);

    List<ModelGapVO> getTgtDbSchList(ModelGapVO search);

    List<ModelGapVO> getDbSrcTgtTblGapList(ModelGapVO search);

    List<ModelGapVO> getDbSrcTgtColGapList(ModelGapVO search);

    List<ModelGapVO> getDdlSrcTgtTblGapList(ModelGapVO search);

    List<ModelGapVO> getDdlSrcTgtColGapList(ModelGapVO search);

    List<ModelGapVO> getMdlDevDdlTblGapList(ModelGapVO search);

    List<ModelGapVO> getMdlDevDdlColGapList(ModelGapVO search);

    List<ModelGapVO> getDdlIdxSrcTgtTblGapList(ModelGapVO search);

    List<ModelGapVO> getDdlIdxSrcTgtColGapList(ModelGapVO search);
}
