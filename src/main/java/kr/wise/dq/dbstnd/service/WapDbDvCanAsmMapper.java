package kr.wise.dq.dbstnd.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;


@Mapper
public interface WapDbDvCanAsmMapper {

    int insertFirst(WapDbDvCanDic record);
    
    int insertDvListFirst(WapDbDvCanAsm record);

    int insertAsmDmn(String dvRqstNo);

    int insertAsmDic(String dvRqstNo);

    int insertAsmNotExistDic(String dvRqstNo);

    int deleteDmnAsm(String dvRqstNo);
    
    int deleteExistDmnAsm(String dvRqstNo);

    int deleteNotEndPrcAsmDic(String dvRqstNo);

    int updateNotEndPrcAsmDic(String dvRqstNo);

    int deleteDvCanAsmByDvRqstNo(String dvRqstNo);

    int deleteDvCanAsmByDup(String dvRqstNo);
    
    int deleteDvCanAsmByDvOrderBy(WapDbDvCanDic record);

    List<WapDbDvCanAsm> selectList(String dvRqstNo);
    
    List<WapDbDvCanAsm> selectItemDvRqstList(WapDbDvCanDic record);
    
    List<WapDbDvCanAsm> selectDmnDvRqstList(WapDbDvCanDic record);

	/** @param rqstno
	/** @param dvrqstno
	/** @param sditmLnm insomnia */
	int insertFirstList(@Param("rqstNo") String rqstno, @Param("rqstNo") String dvrqstno, String sditmLnm);

	/** @param dvrqstno
	/** @return insomnia */
	List<WapDbDvCanAsm> selectItemDivList(String dvrqstno);
	
	int delItemAutoDiv(WapDbDvCanAsm record);
	
	int delDmnAutoDiv(WapDbDvCanAsm record);
	
	int insertFirstApp(WapDbDvCanDic record);
	
    int insertAsmAppDic(String dvRqstNo);
    
    int insertAsmNotExistAppDic(String dvRqstNo);
    
    int deleteNotEndPrcAsmAppDic(String dvRqstNo);
    
    int updateNotEndPrcAsmAppDic(String dvRqstNo);
    
    int updateUnderbar(String dvRqstNo);
    
    int insertAsmDmnFromClsWdMap(WapDbDvCanDic record);
    
    
}