package kr.wise.commons.util;

import kr.wise.dq.dbstnd.service.WamDbDmn;
import kr.wise.dq.dbstnd.service.WamDbSditm;
import kr.wise.dq.dbstnd.service.WamDbStcd;
import kr.wise.dq.dbstnd.service.WamDbStwd;
import kr.wise.dq.stnd.service.WamCdVal;
import kr.wise.dq.stnd.service.WamDmn;
import kr.wise.dq.stnd.service.WamSditm;
import kr.wise.dq.stnd.service.WamStwd;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class ExcelDownUtil {

    private static ExcelDownUtil instance = null;

    public static  ExcelDownUtil getInstance() {
        if (instance != null)
            return instance;

        return new ExcelDownUtil();
    }

    public SXSSFWorkbook makeExcelFile(List<String> headers, List<String> fields, List<Object> list) throws IllegalAccessException {

        // 엑셀생성
        SXSSFWorkbook wb = new SXSSFWorkbook();

        // Sheet 생성
        Sheet sh = wb.createSheet();

        // header row 입력
        Row row = sh.createRow(0);
        int cellnum = 0;

        // headers 에서 Sheet Name 데이터 삭제
        Cell cell = row.createCell(cellnum);

        for (String header : headers) {
            cell.setCellValue(header);
            cell = row.createCell(++cellnum);
        }

        // 엑셀 데이터 저장
        // 타이틀을 제외하고 1번 row부터 시트 저장
        int i = 1;

        for(Object vo : (List<Object>)list.get(0)) {
            Field[] f = null;

            if (vo instanceof WamDbSditm)
                f = WamDbSditm.class.getDeclaredFields();
            else if (vo instanceof WamDbDmn)
                f = WamDbDmn.class.getDeclaredFields();
            else if (vo instanceof WamDbStwd)
                f = WamDbStwd.class.getDeclaredFields();
            else if (vo instanceof WamDbStcd)
                f = WamDbStcd.class.getDeclaredFields();
            else if (vo instanceof WamSditm)
                f = WamSditm.class.getDeclaredFields();
            else if (vo instanceof WamDmn)
                f = WamDmn.class.getDeclaredFields();
            else if (vo instanceof WamStwd)
                f = WamStwd.class.getDeclaredFields();
            else if (vo instanceof WamCdVal)
                f = WamCdVal.class.getDeclaredFields();

            row = sh.createRow(i++);

            for (int j = 0; j < headers.size(); j++) {
                cell = row.createCell(j);
                if (j == 0) cell.setCellValue(i-1);
                if (j == 1) cell.setCellValue("");

                if (j > 1) {
                    String value = getFieldValue(vo, fields.get(j));
                    cell.setCellValue(value);
                    System.out.println(fields.get(j) + " = " + value);
                }
            }
        }

        return wb;
    }

    public static <T> T getFieldValue(Object obj, String fieldName){
        Objects.requireNonNull(obj);

        try {
            Field field = getFieldByName(obj, fieldName); // 4. 해당 필드 조회 후
            if (field.getType().getName().equals("java.util.Date")) {
                String dateToStr = DateFormatUtils.format((Date) field.get(obj), "yyyyMMdd");
                return (T)dateToStr;
            }
            return (T) field.get(obj);	// 5. get 을 이용하여 field value 획득
        } catch (IllegalAccessException e){
            return null;
        }
    }

    public static <T> Field getFieldByName(T t, String fieldName){
        Objects.requireNonNull(t);

        Field field = null;
        for(Field f : getAllFields(t)){
            if (f.getName().equals(fieldName)){
                field = f;	// 2. 모든 필드들로부터 fieldName이 일치하는 필드 추출
                break;
            }
        }
        if (field != null){
            field.setAccessible(true);	// 3. 접근 제어자가 private 일 경우
        }
        return field;
    }

    public static <T> List<Field> getAllFields(T t){
        Objects.requireNonNull(t);

        Class<?> clazz = t.getClass();
        List<Field> fields = new ArrayList<>();
        while(clazz != null){	// 1. 상위 클래스가 null 이 아닐때까지 모든 필드를 list 에 담는다.
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}
