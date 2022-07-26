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

    // 2000~ 도메인 오류

    // 3000~ 단어 오류

    // 4000~ 코드 오류
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
