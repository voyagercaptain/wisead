package kr.wise.commons.error;

public enum ErrorCode {

    // 1000~ 용어 오류
    ERROR_ITEM_NOTNULL("1000", "표준용어명 누락"),
    ERROR_ITEM_REGEX_NOT_MATCH("1001", "표준용어명 형식 오류(공백,특수문자입력불가)"),
    ERROR_ITEM_ENG_NOTNULL("1002", "영문명누락"),
    ERROR_ITEM_UPPER("1003", "영문명 소문자 불가"),
    ERROR_ITEM_ENG_INIT_NOTNULL("1004", "영문약어명 누락"),
    ERROR_ITEM_ENG_REGEX_NOT_MATCH("1005", "영문약어명 형식 오류(대문자,특수문자미포함)"),
    ERROR_ITEM_DESC_EQUAL("1006", "용어설명과 표준용어명 동일"),
    ERROR_ITEM_DATE_FORMAT("1007", "제정일자 포맷오류"),
    ERROR_ITEM_DATE_NOTNULL("1008", "제정일자 누락"),

    ERROR_ITEM_DUP("1009", "데이터 중복 오류"),

    ERROR_ITEM_ORG_NM_NOTNULL("1010", "기관명 누락"),

    ERROR_ITEM_DMN_NOTNULL("1011", "표준도메인명 누락"),


    // 2000~ 도메인 오류
    ERROR_DMN_NOTNULL("2001", "도메인명 누락"),
    ERROR_DMN_TYPE_NOTNULL("2002", "데이터타입 누락"),
    ERROR_DMN_LENGTH_NOTNULL("2003", "데이터길이 누락"),


    ERROR_DMN_DATE_FORMAT("2004", "제정일자 포맷오류"),
    ERROR_DMN_DATE_NOTNULL("2005", "제정일자 누락"),
    ERROR_DMN_TYPE_LENGTH_ERROR("2006", "표준도메인 오류"),

    // 3000~ 단어 오류
    ERROR_WORD_NOTNULL("3001", "단어명 누락"),
    ERROR_WORD_ENG_NOTNULL("3002", "영문약어명 누락"),
    ERROR_WORD_FORMAT("3003", "형식단어 오류"),
    ERROR_WORD_FORMAT_NOTNULL("3004", "형식단어 누락"),
    ERROR_WORD_DATE_FORMAT("3005", "제정일자 포맷오류"),
    ERROR_WORD_DATE_NOTNULL("3006", "제정일자 누락"),

    // 4000~ 코드 오류
    ERROR_CODE_NOTNULL("4001", "코드명 누락"),
    ERROR_CODE_VALUE_NOTNULL("4002", "코드값 누락"),
    ERROR_CODE_VALUE_MEAN_NOTNULL("4004", "코드값의미 누락"),
    ERROR_CODE_DATE_FORMAT("4005", "제정일자 포맷오류"),
    ERROR_CODE_DATE_NOTNULL("4006", "제정일자 누락"),
    ;

    private String code;
    private String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

}
