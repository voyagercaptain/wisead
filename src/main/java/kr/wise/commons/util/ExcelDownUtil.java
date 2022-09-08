package kr.wise.commons.util;

import kr.wise.dq.dbstnd.service.WamDbDmn;
import kr.wise.dq.dbstnd.service.WamDbSditm;
import kr.wise.dq.dbstnd.service.WamDbStcd;
import kr.wise.dq.dbstnd.service.WamDbStwd;
import kr.wise.dq.stnd.service.WamCdVal;
import kr.wise.dq.stnd.service.WamDmn;
import kr.wise.dq.stnd.service.WamSditm;
import kr.wise.dq.stnd.service.WamStwd;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class ExcelDownUtil {

    private static ExcelDownUtil instance = null;

    public static  ExcelDownUtil getInstance() {
        if (instance != null)
            return instance;

        return new ExcelDownUtil();
    }

    public SXSSFWorkbook makeExcelFile(List<String> titleList, List<Object> list) throws IllegalAccessException {

        // 엑셀생성
        SXSSFWorkbook wb = new SXSSFWorkbook();
        // 리스트의 0번째 데이터는 Sheet Name
        Sheet sh = wb.createSheet(titleList.get(0));

        // 테이블 타이틀 입력
        Row row = sh.createRow(0);
        int cellnum = 0;
        // titleList에서 Sheet Name 데이터 삭제
        titleList.remove(0);
        Cell cell = row.createCell(cellnum);

        for (String title : titleList) {
            cell.setCellValue(title);
            cell = row.createCell(++cellnum);
        }

        // 엑셀 데이터 저장
        // 타이틀을 제외하고 1번 row부터 시트 저장
        int i = 1;

        for(Object vo : list) {
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
            cellnum = 0;

            for (int j = 0; j < f.length; j++) {
                f[j].setAccessible(true);
                cell = row.createCell(cellnum++);
                cell.setCellValue(f[j].get(vo) != null ? f[j].get(vo).toString() : "");
            }
        }

        return wb;
    }

}
