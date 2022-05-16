package kr.wise.dq.dbstnd.service;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WapDbDvCanDicMapper {

    int insert(WapDbDvCanDic record);

    int insertSelective(WapDbDvCanDic record);

	/** @param record insomnia */
	int insertStwd(WapDbDvCanDic record);

	/** @param dvRqstNo insomnia */
	int deleteDvCanDicByDvRqstNo(String dvRqstNo);

	/** @param record insomnia */
	int insertDmn(WapDbDvCanDic record);

	/** @param record
	/** @return insomnia */
	int insertFirst(WapDbDvCanDic record);

	/** @param record insomnia */
	int insertStwdAll(WapDbDvCanDic record);

	/** @param record insomnia */
	int insertDmnAll(WapDbDvCanDic record);
	
	//항목분할 도메인명 미존재
	int checkDmnInfo(String dvRqstNo);
	
	//항목분할 단어 미존재
	int checkSdwd(String dvRqstNo);
	
	//항목 존재
	int checkDupSditm(String dvRqstNo);
	
	//도메인분할 도메인그룹ID UPDATE
	int updtDmngId(String dvRqstNo);
	//도메인분할 인포타입ID UPDATE
	int updtInfotpId(String dvRqstNo);
	
	//도메인분할 도메인그룹논리명 미존재
	int checkDmngId(String dvRqstNo);
	
	//도메인분할 인포타입논리명 미존재
	int checkInfotpId(String dvRqstNo);
	
	//도메인분할 도메인 기존재
	int checkDmnDup(String dvRqstNo);
	
	//도메인그룹 인포타입 매핑 상이
	int checkDmngInfotyp(String dvRqstNo);
	
	//구성정보 오류
	int checkAsmDs(String dvRqstNo);
	
	//물리명길이(30)
	int checkPnmMaxLen(String dvRqstNo);
	
	//끝자리 숫자 체크
	int checkEndNum(String dvRqstNo);
	
	
//	//항목분할 단어 미존재
//	int checkSdwd(String dvRqstNo);
//	
//	//항목 존재
//	int checkDupSditm(String dvRqstNo);
	
	/** @param record mse */
	int insertAppStwd(WapDbDvCanDic record);

	int checkInpotpLnm(String dvrqstno); 
	
	//191015 분류어입력
	int insertClsWd(WapDbDvCanDic record);
		
    //191015 분류어와 매핑된 도메인 정보 입력
	int insertDmnFromClsWdMap(WapDbDvCanDic record);
	int insertDmnFromClsWdMapAll(WapDbDvCanDic record);
	
	int checkDmnYn(String dvrqstno);
	
	int checkExistsDmn(String dvrqstno);
	
}