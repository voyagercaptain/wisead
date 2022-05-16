package kr.wise.dq.gap.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface ModelGapMapper {

	List<ModelGapVO> getModelGapAnalyze(ModelGapVO search);

	List<ModelGapVO> getGapChart();
	
	List<ModelGapVO> getModelDdlColGapList(ModelGapVO search);
	
	List<ModelGapVO> selectPdmDdlGapList(ModelGapVO search);
	
	List<ModelGapVO> selectDdlDbcColGapList(ModelGapVO search);
	
	List<ModelGapVO> selectMartPdmGapList(ModelGapVO search);
	
	List<ModelGapVO> selectMartPdmColGapList(ModelGapVO search);
		
	List<ModelGapVO> selectDdlDbcGapList(ModelGapVO search);
	
	List<ModelGapVO> selectDdlTsfGapList(ModelGapVO search);
	
	List<ModelGapVO> selectDdlTsfColGapList(ModelGapVO search);
	
	List<ModelGapVO> selectDdlTsfDbcGapList(ModelGapVO search);
	
	List<ModelGapVO> selectDdlTsfDbcColGapList(ModelGapVO search);
	
	List<ModelGapVO> selectDbcGapList(ModelGapVO search);
	
	List<ModelGapVO> selectDbcColGapList(ModelGapVO search);

	List<ModelGapVO> selectMdlDevDbTblGapList(ModelGapVO search); 

	List<ModelGapVO> selectMdlDevDbColGapList(ModelGapVO search);

	List<ModelGapVO> selectNonStndPdmColGapList(ModelGapVO search);

	List<ModelGapVO> selectErwinPdmTblGapList(ModelGapVO search);

	List<ModelGapVO> selectErwinPdmColGapList(ModelGapVO search);

    List<ModelGapVO> selectSrcDbSchList(ModelGapVO search);

    List<ModelGapVO> selectTgtDbSchList(ModelGapVO search);

    List<ModelGapVO> selectDbSrcTgtTblGapList(ModelGapVO search);

    List<ModelGapVO> selectDbSrcTgtColGapList(ModelGapVO search);

    List<ModelGapVO> selectDdlSrcTgtTblGapList(ModelGapVO search);

    List<ModelGapVO> selectDdlSrcTgtColGapList(ModelGapVO search);

    List<ModelGapVO> selectMdlDevDdlTblGapList(ModelGapVO search);

    List<ModelGapVO> selectMdlDevDdlColGapList(ModelGapVO search);

    List<ModelGapVO> selectDdlIdxSrcTgtTblGapList(ModelGapVO search);

    List<ModelGapVO> selectDdlIdxSrcTgtColGapList(ModelGapVO search);

	List<ModelGapVO> getGapChartDateJSON();           

}
