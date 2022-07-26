package kr.wise.commons.util;

import kr.wise.commons.error.ErrorCode;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class ValidationCheck {

    /**
     * 표준 용어 검증
     * 필수값 누락 검증 : 데이터 누락 불가
     * 데이터 형식 검증 : 특수문자, 공백 문자 불가
     * 필수값 누락검증 메서드
     * @param data
     * @return
     */
    public static String checkSditmName(String data) {

        // 문자열 비어있는 경우
        if (StringUtils.isEmpty(data)) {
            return ErrorCode.ERROR_ITEM_NOTNULL.getMessage();
        }

        //문자열에 공백 혹은 특수문자가 입력된 경우
        String pattern = "^[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣]*$";
        if(!Pattern.matches(pattern, data)){
            return ErrorCode.ERROR_ITEM_REGEX_NOT_MATCH.getMessage();
        }

        return "";
    }

    /**
     * 표준 용어 영문명 검증
     * 영문 대문자로 구성되어야 함
     * @param data
     * @return
     */
    public static String checkSditmEng(String data) {

        // 문자열 비어있는 경우
        if (StringUtils.isEmpty(data)) {
            return ErrorCode.ERROR_ITEM_ENG_NOTNULL.getMessage();
        }

        //문자열 대문자가 아닌경우 입력된 경우
        String pattern = "^[A-Z]*$";
        if(!Pattern.matches(pattern, data)){
            return ErrorCode.ERROR_ITEM_UPPER.getMessage();
        }

        return "";
    }

    /**
     * 표준 용어 영문 약어명 검증
     * 필수값 누락 검증 : 데이터 누락 불가
     * 데이터 형식 검증 : 영문 대문자와 특수문자(‘_’)로 구성되어야 함
     * @param data
     * @return
     */
    public static String checkSditmInit(String data) {

        // 문자열 비어있는 경우
        if (StringUtils.isEmpty(data)) {
            return ErrorCode.ERROR_ITEM_ENG_INIT_NOTNULL.getMessage();
        }

        //문자열 대문자가 아닌경우 입력된 경우
        String pattern = "^[A-Z_]*$";
        if(!Pattern.matches(pattern, data)){
            return ErrorCode.ERROR_ITEM_ENG_REGEX_NOT_MATCH.getMessage();
        }

        return "";
    }

    /**
     * 표준 용어 설명 검증
     * 용어설명이 표준용어명과 동일한 경우 오류 처리
     * @param source
     * @param target
     * @return
     */
    public static String checkSditmDesc(String source, String target) {
        if (source.equals(target))
            return ErrorCode.ERROR_ITEM_DESC_EQUAL.getMessage();
        return "";
    }

    /**
     * 표준 용어 도메인명 검증
     * @param data
     * @return
     */
    public static String checkSditmDmnNm(String data) {
        return "";
    }

    /**
     * 표준 용어 코드명 검증
     * @param data
     * @return
     */
    public static String checkSditmCdNm(String data) {
        return "";
    }

    /**
     * 표준 용어 제정일자 검증
     * 필수값 검증,  데이터 형식 검증 (yyyyMMdd)
     * @param data
     * @return
     */
    public static String checkSditmDate(String data) {
        try {
            if (StringUtils.isEmpty(data)) {
                return ErrorCode.ERROR_ITEM_DATE_NOTNULL.getMessage();
            }

            SimpleDateFormat dateFormatParser = new SimpleDateFormat("yyyyMMdd"); //검증할 날짜 포맷 설정
            dateFormatParser.setLenient(false); //false일경우 처리시 입력한 값이 잘못된 형식일 시 오류가 발생
            dateFormatParser.parse(data); //대상 값 포맷에 적용되는지 확인
            return "";
        }
        catch (Exception e) {
            return ErrorCode.ERROR_ITEM_DATE_FORMAT.getMessage();
        }
    }
}
