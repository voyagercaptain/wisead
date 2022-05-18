package kr.wise.commons.handler;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import kr.wise.dq.profile.mstr.service.WamPrfMstrCommonVO;
import kr.wise.dq.result.service.ResultDataVO;
import kr.wise.dq.result.service.ResultVO;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PoiHandler {
	private final  Logger logger = LoggerFactory.getLogger(getClass());

	private String type;				//.xls, .xlsx
	private Workbook workbook=null;		//excel

	private CellStyle titleStyle;
	private CellStyle headerStyle;
	private CellStyle listStyle;
	private CellStyle normalStyle;
	private CellStyle dataStyle;
	private CellStyle dataCenterStyle;
	private CellStyle resultStyle;


	//생성자
	public PoiHandler(String type) {
		logger.debug("Poi Constructer");
		this.type=type;

		if(type.equals("1")) {				//excel2003이하
//			type = ".xls";
			workbook = new HSSFWorkbook();	//.xls
		} else if(type.equals("2")) {		//excel2007이상
//			type = ".xlsx";
			workbook = new XSSFWorkbook();	//.xlsx
		} else if(type.equals("3")) {
//			type = ".xlsx";
			workbook = new SXSSFWorkbook(-1);	//.xlsx
		} else{
			logger.debug(type + " 올바르지 않은 형식");
		}

		///////////////////////////////////////////////////////////////////////////////////
		//스타일 변수
		//title style
		titleStyle=workbook.createCellStyle();	//title 스타일 생성

		titleStyle.setAlignment((short)2);					//가로 중앙 정렬
		titleStyle.setVerticalAlignment((short)1);			//세로 중앙 정렬

		Font titleFont = workbook.createFont();				//title 폰트 생성
		titleFont.setFontHeightInPoints((short)17);			//크기 17
//		titleFont.setBold(true);							//굵은체
		titleFont.setFontName("맑은 고딕");
		titleStyle.setFont(titleFont);						//스타일에 폰트 적용


		//header style
		headerStyle=workbook.createCellStyle();			//header 스타일 생성
		headerStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);		//bottom 윤곽석
		headerStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);			//left 윤관석
		headerStyle.setBorderRight(CellStyle.BORDER_MEDIUM);		//right 윤관석
		headerStyle.setBorderTop(CellStyle.BORDER_MEDIUM);			//top 윤곽선
		headerStyle.setAlignment((short)2);							//가로 중앙 정렬
		headerStyle.setVerticalAlignment((short)1);					//세로 중앙 정렬
		headerStyle.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.index);	//배경색 지정
		headerStyle.setFillPattern(headerStyle.SOLID_FOREGROUND);	//배경 적용

		Font headerFont=workbook.createFont();						//header 폰트 생성
		headerFont.setFontHeightInPoints((short)13);				//크기 15
//		headerFont.setBold(true);									//굵은체
		headerFont.setColor(IndexedColors.WHITE.index); 			//흰색 글씨
		headerFont.setFontName("맑은 고딕");
		headerStyle.setFont(headerFont);							//스타일에 폰트 적용


		//listStyle
		listStyle=workbook.createCellStyle();				//list 스타일 생성
		listStyle.setBorderBottom(CellStyle.BORDER_THIN);			//bottom 윤곽석
		listStyle.setBorderLeft(CellStyle.BORDER_THIN);				//left 윤관석
		listStyle.setBorderRight(CellStyle.BORDER_THIN);			//right 윤관석
		listStyle.setBorderTop(CellStyle.BORDER_THIN);				//top 윤곽선
		listStyle.setAlignment((short)2);							//가로 중앙 정렬
		listStyle.setVerticalAlignment((short)1);					//세로 중앙 정렬
		listStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);		//배경색 지정
		listStyle.setFillPattern(listStyle.SOLID_FOREGROUND); 		//배경 적용

		Font listFont=workbook.createFont();						//list 폰트 생성
		listFont.setFontHeightInPoints((short)11); 					//크기 13
//		listFont.setBold(true); 									//굵은체
		listFont.setFontName("맑은 고딕");
		listStyle.setFont(listFont);								//스타일에 폰트 적용


		//normal style
		normalStyle=workbook.createCellStyle();			//normal 스타일 생성
		normalStyle.setAlignment((short)1);							//가로 왼쪽 정렬
		normalStyle.setVerticalAlignment((short)1);					//세로 중앙 정렬

		Font normalFont=workbook.createFont();						//normal 폰트 생성
		normalFont.setFontHeightInPoints((short)11); 				//크기 11
		normalFont.setFontName("맑은 고딕"); 							
		normalStyle.setFont(normalFont); 							//스타일에 폰트 적용


		//data style
		dataStyle=workbook.createCellStyle();				//data 스타일 생성
		dataStyle.setAlignment((short)3);							//가로 오른쪽 정렬
		dataStyle.setVerticalAlignment((short)1);					//세로 중앙 정렬

		Font dataFont=workbook.createFont();						//data 폰트 생성
		dataFont.setFontHeightInPoints((short)11); 					//크기 11
		dataFont.setFontName("맑은 고딕"); 							
		dataStyle.setFont(dataFont); 								//스타일에 폰트 적용

		//dataCenter style
		dataCenterStyle=workbook.createCellStyle();				//data 스타일 생성
		dataCenterStyle.setAlignment((short)2);							//가로 오른쪽 정렬
		dataCenterStyle.setVerticalAlignment((short)1);					//세로 중앙 정렬

		Font dataCenterFont=workbook.createFont();						//data 폰트 생성
		dataCenterFont.setFontHeightInPoints((short)11); 					//크기 11
		dataCenterFont.setFontName("맑은 고딕"); 							
		dataCenterStyle.setFont(dataCenterFont); 								//스타일에 폰트 적용


		//result style
		resultStyle=workbook.createCellStyle();			//result 스타일 생성
		resultStyle.setBorderBottom(CellStyle.BORDER_THIN);		//bottom 윤곽석
		resultStyle.setBorderLeft(CellStyle.BORDER_THIN);			//left 윤관석
		resultStyle.setBorderRight(CellStyle.BORDER_THIN);		//right 윤관석
		resultStyle.setBorderTop(CellStyle.BORDER_THIN);			//top 윤곽선
		resultStyle.setAlignment((short)3);							//가로 오른쪽 정렬
		resultStyle.setVerticalAlignment((short)1);					//세로 중앙 정렬
		resultStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);		//배경색 지정
		resultStyle.setFillPattern(listStyle.SOLID_FOREGROUND); 		//배경 적용

		Font resultFont=workbook.createFont();						//result 폰트 생성
		resultFont.setFontHeightInPoints((short)12); 				//크기 12
//		resultFont.setBold(true);									//굵은체
		resultFont.setFontName("맑은 고딕"); 							
		resultStyle.setFont(resultFont); 							//스타일에 폰트 적용

	}

	//실적조회
	public void createExcelResult(List<ResultVO> dataList, BigDecimal totTrgTblCnt, BigDecimal expTrgTblCnt,  ResultVO term, BigDecimal exeSchColSucessCnt, BigDecimal exeSchColTotCnt, 
			List<ResultVO> tblList, List<ResultVO> dmnRule, List<ResultVO> execList, List<ResultVO> errList, List<ResultVO> dqiErrList, String dataSelect) throws Exception{	
		logger.debug("Poi createExcel started");

		if(dataList.size()==0)	//없는경우
			return;
		
		
		if(dataSelect.equals("result")) {
		
		createExcelResult_sheet1(dataList, totTrgTblCnt, expTrgTblCnt, term, exeSchColSucessCnt,exeSchColTotCnt, dqiErrList);
		createExcelResult_sheet2(tblList);
//		createExcelResult_sheet3(dmnRule);
		createExcelResult_sheet4(execList);
		createExcelResult_sheet5(errList);
		}else if(dataSelect.equals("errResult")) {
			for(int i=0; i<dqiErrList.size(); i++) {
				createExcelResult_sheet6(dqiErrList.get(i));
				createExcelResult_sheet7(dqiErrList.get(i));
			}
		}
	}
	
	//실적조회
	public void createExcelResult_sheet1(List<ResultVO> dataList, BigDecimal totTrgTblCnt, BigDecimal expTrgTblCnt, ResultVO term
			, BigDecimal exeSchColSucessCnt, BigDecimal exeSchColTotCnt, List<ResultVO> dqiErrList) throws Exception{
		logger.debug("Poi createExcel1 started");

		if(dataList.size()==0)	//없는경우
			return;

		//Sheet, Row, Cell 생성
		Sheet sheet = workbook.createSheet("진단결과종합현황");
		Row row= null;
		Cell cell=null;

		int index;
		//행열 설정
		int rowNum= dataList.size()+21;	//데이터 +정보를 나타내주는 행
		int colNum=7;

		//각 행열 대한 작업셀 생성
		for(int i=0;i<rowNum;i++){
			row=sheet.createRow(i);
			for(int j=0;j<colNum;j++){
				cell=row.createCell(j);
			}
		}

		//컬럼 width설정
		sheet.setColumnWidth(0, 4500);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 9000);
		sheet.setColumnWidth(4, 4000);
		sheet.setColumnWidth(5, 4000);
		sheet.setColumnWidth(6, 4000);
		sheet.setColumnWidth(7, 3500);
		sheet.setColumnWidth(8, 4000);
		sheet.setColumnWidth(9, 4000);
		sheet.setColumnWidth(10, 3000);
		sheet.setColumnWidth(11, 3000);
		sheet.setColumnWidth(12, 3000);
		sheet.setColumnWidth(13, 3000);
		sheet.setColumnWidth(14, 3000);
		sheet.setColumnWidth(15, 3000);


		////////////////////////////////////////////////////////////////
		//값 입력
		//WiseDQ진단 종합 현황 
		row=sheet.getRow(0);
		cell=row.getCell(0);
		cell.setCellValue("WISE DQ 진단결과종합현황");

		//출력일
		row=sheet.getRow(1);
		cell=row.getCell(0);
		cell.setCellValue("출력일 : " + getTodayTime());


		//진단 데이터베이스 기본 정보
		row=sheet.getRow(2);
		cell=row.getCell(0);
		cell.setCellValue("진단 데이터베이스 기본 정보");


		String[] tempList={"진단기간", "DB종류", "버전","DB명","스키마명"};
		String[] tempValue={term.getAnaStrDtm() + " ~ " + term.getAnaEndDtm(), "DBMS 종류를 입력하세요.", "버전을 입력하세요.",term.getDbConnTrgLnm(),term.getDbSchLnm()};
		index=0;
		for(int i=3;i<=7;i++){
			row=sheet.getRow(i);
			cell=row.getCell(0);
			cell.setCellValue(tempList[index]);

			//값 설정
			cell=row.getCell(3);
			cell.setCellValue(tempValue[index++]);
		}
		
		//진단 데이터베이스 테이블 현황
		BigDecimal expData = expTrgTblCnt;
		BigDecimal noneExpData = totTrgTblCnt;
		row=sheet.getRow(9);
		cell=row.getCell(0);
		cell.setCellValue("진단 대상 테이블 현황");	
		
		String[] tempList1={"대상", "대상율", "제외","제외율"};
		String[] tempValue1={expData+" / "+noneExpData+"  (검증대상 테이블 수 / 전체 테이블 수)"
			,((expData.divide(noneExpData, 2, BigDecimal.ROUND_CEILING)).multiply(new BigDecimal(100))).setScale(2)+"%"
			,noneExpData.subtract(expData)+" / "+noneExpData+"  (제외 테이블 수 / 전체 테이블 수)",
			((noneExpData.subtract(expData)).divide(noneExpData, 2, BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100))).setScale(2)+"%"
		};			
		index=0;
		for(int i=10;i<=13;i++){
			row=sheet.getRow(i);
			cell=row.getCell(0);
			cell.setCellValue(tempList1[index]);

			//값 설정
			cell=row.getCell(3);
			cell.setCellValue(tempValue1[index++]);
		}
		
//		//진단실행상태
		row=sheet.getRow(15);
		cell=row.getCell(0);
		cell.setCellValue("진단 실행 상태");	
		String[] tempCnt={"완료", "완료율"};
		String[] tempCntValue={exeSchColSucessCnt+" / "+exeSchColTotCnt+"  (검증 성공 컬럼 수 / 검증 가능 컬럼 수)"
			,((exeSchColSucessCnt.divide(exeSchColTotCnt, 2, BigDecimal.ROUND_CEILING)).multiply(new BigDecimal(100))).setScale(2)+"%"
			};
		index=0;
		for(int i=16;i<=17;i++){
			row=sheet.getRow(i);
			cell=row.getCell(0);
			cell.setCellValue(tempCnt[index]);

			//값 설정
			cell=row.getCell(3);
			cell.setCellValue(tempCntValue[index++]);
		}
		
		
//		String[] tempList2={"", "대상 테이블", "대상 컬럼", "진단규칙 설정", "", "진단규칙 실행"};
//		String[] tempValue2={"진단규칙 설정 현황"
//							, makeComma(dataList.get(dataList.size()-1).getTblCnt()+"")
//							, makeComma(dataList.get(dataList.size()-1).getColCnt()+"")
//							, makeComma(term.getTotCnt()+"")
//							, ""
//							, makeComma(term.getErrCnt()+"")};
////			index=0;
//		for(int i=0;i<tempList2.length;i++){
//			row=sheet.getRow(19);
//			cell=row.getCell(i);
//			cell.setCellValue(tempList2[i]);
//
//			//값 설정
//			row=sheet.getRow(20);
//			cell=row.getCell(i);
//			cell.setCellValue(tempValue2[i]);
//		}


		//하단부 table header
//		String[] tempHeader = {"분석영역", "검증유형", "테이블", "컬럼", "전체데이터", "오류데이터", "오류율(%)"};
		String[] tempHeader = {"검증유형","진단대상 컬럼", " 오류발견 컬럼", "최근진단일자", "전체데이터", "오류데이터", "오류율(%)"};
		row=sheet.getRow(19);
		for(int i=0;i<colNum;i++){
			cell=row.getCell(i);
			cell.setCellValue(tempHeader[i]);
		}
		
		
		Map<String, BigDecimal> errColCntMap = new LinkedHashMap<String , BigDecimal>(); 
		BigDecimal errColTotCnt = BigDecimal.ZERO;
		
		for(int i = 0 ; i < 12 ; i++) {	// 품질지표별 에러 컬럼 수를 세기 위해 Dictionary(Map) 으로 매핑했다.
			for(int j = 0 ; j < dqiErrList.size(); j++) {
				if (dataList.get(i).getDqiLnm().equals(dqiErrList.get(j).getDqiLnm())){
					if(dqiErrList.get(j).getErrColCnt() != BigDecimal.ZERO) {
						errColCntMap.put(dataList.get(i).getDqiLnm(), dqiErrList.get(j).getErrColCnt());
						errColTotCnt = errColTotCnt.add(dqiErrList.get(j).getErrColCnt());
					}
					break;
				}
			}
		}
		
		
		//하단부 table data
		index=0;
		for(int i=0;i<dataList.size();i++){
			row=sheet.getRow(i+20);
			
			ResultVO tempVO= dataList.get(i);
			
			if(errColCntMap.containsKey(tempVO.getDqiLnm())) {
				tempVO.setErrColCnt(errColCntMap.get(tempVO.getDqiLnm()));	
			}
			
			if (i == dataList.size()-1) {
				tempVO.setErrColCnt(errColTotCnt);
			}

			//tempHeader에 해당하는 값들 tempKey에 저장
//			String[] tempKey={tempVO.getUppDqiLnm(), tempVO.getDqiLnm(), tempVO.getTblCnt()+"", tempVO.getColCnt()+"",
//					tempVO.getTotCnt()+"", tempVO.getErrCnt()+"", tempVO.getErrRate()+""};
			
			String[] tempKey={tempVO.getDqiLnm(), tempVO.getColCnt()+"", tempVO.getErrColCnt()+"",
					tempVO.getAnaStrDtm(), tempVO.getTotCnt()+"", tempVO.getErrCnt()+"", tempVO.getErrRate()+""};

//				if(i==9){
//					cell=row.getCell(0);
//					cell.setCellValue(tempKey[0]);				//분석유형
//				}

			for(int j=0;j<colNum;j++){		//데이터
				cell=row.getCell(j);
				String tempCellValue=tempKey[j];
				if(tempCellValue==null || tempCellValue.equals("null") || tempCellValue.length()==0 )	//null값을 갖는 값이 있다면
					tempCellValue="0";
				
				if(j == 1 && tempCellValue.equals("0") || (j == 2 && tempCellValue.equals("0")) || (j == 3 && tempCellValue.equals("0")) ||
						(j == 4 && tempCellValue.equals("0")) || (j == 5 && tempCellValue.equals("0")) || (j == 6 && tempCellValue.equals("0")) )
					tempCellValue = "";

				cell.setCellValue(makeComma(tempCellValue));
				
				if(j==0) {
					cell.setCellStyle(normalStyle);
				} else {
					cell.setCellStyle(dataStyle);
				}
			}
		}

		////////////////////////////////////////////////////////////////
		//style 적용
		//WiseDQ 프로파일 조회 - titleStyle
		row=sheet.getRow(0);
		for(int i=0;i<colNum;i++){
			cell=row.getCell(i);
			cell.setCellStyle(titleStyle);
		}

		//출력일 = dataStyle
		row=sheet.getRow(1);
		cell=row.getCell(0);
		cell.setCellStyle(dataStyle);

		//진단데이터베이스정보 - headerStyle
		row=sheet.getRow(2);
		for(int i=0;i<colNum;i++){
			cell=row.getCell(i);
			cell.setCellStyle(headerStyle);
		}

		//진단기간,dbms,스키마 - listStyle, 값 - normalStyle
		for(int i=3;i<=7;i++){
			row=sheet.getRow(i);
			for(int j=0;j<colNum;j++){
				cell=row.getCell(j);
				if(j<=2)
					cell.setCellStyle(listStyle);
				else
					cell.setCellStyle(normalStyle);
			}
		}
		
		//진단 데이터베이스 테이블 현황		
		row=sheet.getRow(9);
		for(int i=0;i<colNum;i++){
			cell=row.getCell(i);
			cell.setCellStyle(headerStyle);
		}		
		
		for(int i=10;i<=13;i++){
			row=sheet.getRow(i);
			for(int j=0;j<colNum;j++){
				cell=row.getCell(j);
				if(j<=2)
					cell.setCellStyle(listStyle);
				else
					cell.setCellStyle(normalStyle);
			}
		}
		
		row=sheet.getRow(15);
		for(int i=0;i<colNum;i++){
			cell=row.getCell(i);
			cell.setCellStyle(headerStyle);
		}		
		
		for(int i=16;i<=17;i++){
			row=sheet.getRow(i);
			for(int j=0;j<colNum;j++){
				cell=row.getCell(j);
				if(j<=2)
					cell.setCellStyle(listStyle);
				else
					cell.setCellStyle(normalStyle);
			}
		}		
		
//		row=sheet.getRow(19);
//		for(int i=0;i<7;i++){
//			cell=row.getCell(i);
//			cell.setCellStyle(headerStyle);
//		}
//		
//		row=sheet.getRow(20);
//		cell=row.getCell(0);
//		cell.setCellStyle(listStyle);
//		for(int i=1;i<7;i++){
//			cell=row.getCell(i);
//			cell.setCellStyle(dataStyle);
//		}

		//분석유형, ... , SIGMA - headerStyle
		row=sheet.getRow(19);
		for(int i=0;i<colNum;i++){
			cell=row.getCell(i);
			cell.setCellStyle(headerStyle);
		}

		////////////////////////////////////////////////////////////////
		//셀병합 cellRangeAddress(행시작,행끝,열시작,열끝)
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,colNum-1));	//WiseDQ 프로파일 조회
		sheet.addMergedRegion(new CellRangeAddress(1,1,0,colNum-1));	//출력일	
		sheet.addMergedRegion(new CellRangeAddress(2,2,0,colNum-1));	//진단 데이터베이스 기본 정보

		for(int i=3;i<=7;i++){
			sheet.addMergedRegion(new CellRangeAddress(i,i,0,2));		//진단기간 , 기관명, 시스템명, DB종류, 버전, DB명, 스키마명
			sheet.addMergedRegion(new CellRangeAddress(i,i,3,colNum-1));//값
		}
		
		
		sheet.addMergedRegion(new CellRangeAddress(9,9,0,colNum-1));	//진단대상 테이블 현황
		for(int i=10;i<=13;i++){
			sheet.addMergedRegion(new CellRangeAddress(i,i,0,2));		//대상, 대상율, 제외 ,제외율
			sheet.addMergedRegion(new CellRangeAddress(i,i,3,colNum-1));//값
		}
		
		sheet.addMergedRegion(new CellRangeAddress(15,15,0,colNum-1));	//진단실행상태
		for(int i=16;i<=17;i++){
			sheet.addMergedRegion(new CellRangeAddress(i,i,0,2));		//완료, 완료율
			sheet.addMergedRegion(new CellRangeAddress(i,i,3,colNum-1));//값
		}			
		
//		sheet.addMergedRegion(new CellRangeAddress(19,19,3,4));
//		sheet.addMergedRegion(new CellRangeAddress(19,19,5,6));
//		sheet.addMergedRegion(new CellRangeAddress(20,20,3,4));
//		sheet.addMergedRegion(new CellRangeAddress(20,20,5,6));
		
//		sheet.addMergedRegion(new CellRangeAddress(24, 29, 0, 0));
//		sheet.addMergedRegion(new CellRangeAddress(30, 34, 0, 0));

//			if(rowNum-2!=9)
//				sheet.addMergedRegion(new CellRangeAddress(9,rowNum-2,0,0));	//관계분석
//			sheet.addMergedRegion(new CellRangeAddress(rowNum-1,rowNum-1,0,6));	//값 진단 종합

		////////////////////////////////////////////////////////////////
		//헤더설정 및 프린트옵션 설정
		Header header = sheet.getHeader();
		header.setLeft("WISE DQ 종합 진단 현황");


		sheet.setFitToPage(true);
		PrintSetup ps = sheet.getPrintSetup();
		ps.setFitWidth((short)1);
		ps.setFitHeight((short)0);
	}
	
	//테이블목록
	public void createExcelResult_sheet2(List<ResultVO> dataList) throws Exception{	
		logger.debug("Poi createExcel2 started");

		if(dataList.size()==0)	//없는경우
			return;

		//Sheet, Row, Cell 생성
		Sheet sheet = workbook.createSheet("테이블목록");
		Row row= null;
		Cell cell=null;

		//행열 설정
		int rowNum= 0;	//데이터 +정보를 나타내주는 행
//		if(dataList.size()+1 > 65535)
//			rowNum = 65535;	//데이터 +정보를 나타내주는 행
//		else
			rowNum = dataList.size()+1;
		int colNum=7;

		//각 행열 대한 작업셀 생성
		for(int i=0;i<rowNum;i++){
			row=sheet.createRow(i);
			for(int j=0;j<colNum;j++){
				cell=row.createCell(j);
			}
		}

		//컬럼 width설정
		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 4000);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 6000);
		sheet.setColumnWidth(5, 4000);
		sheet.setColumnWidth(6, 4000);


		////////////////////////////////////////////////////////////////
		//하단부 table header
		String[] tempHeader = {"번호", "SCHEMA", "테이블", "테이블(한글)", "테이블 수집일자", "제외여부", "제외사유"};
		row=sheet.getRow(0);
		for(int i=0;i<colNum;i++){
			cell=row.getCell(i);
			cell.setCellValue(tempHeader[i]);
		}
		
		//하단부 table data
		for(int i=0;i<rowNum-1;i++){
			row=sheet.getRow(i+1);
			
			ResultVO tempVO= dataList.get(i);

			//tempHeader에 해당하는 값들 tempKey에 저장
			String[] tempKey={(i+1)+"", tempVO.getDbSchPnm(), tempVO.getDbcTblNm(), tempVO.getDbcTblKorNm(), tempVO.getAnaStrDtm(),
					tempVO.getExpYn(), tempVO.getExpRsnCntn()};

			for(int j=0;j<colNum;j++){		//데이터
				cell=row.getCell(j);
				String tempCellValue=tempKey[j];
				if(tempCellValue==null || tempCellValue.equals("null") || tempCellValue.length()==0)	//null값을 갖는 값이 있다면
					tempCellValue="";

				cell.setCellValue(tempCellValue);
			}
		}

		////////////////////////////////////////////////////////////////
		
		row=sheet.getRow(0);
		for(int i=0;i<7;i++){
			cell=row.getCell(i);
			cell.setCellStyle(listStyle);
		}
		
//		for(int i=0; i<dataList.size(); i++) {
//			row=sheet.getRow(i+1);
//			for(int j=0;j<7;i++){
//				cell=row.getCell(j);
//				cell.setCellStyle(normalStyle);
//			}
//		}
		////////////////////////////////////////////////////////////////
		//헤더설정 및 프린트옵션 설정
		Header header = sheet.getHeader();
		header.setLeft("WISE DQ 종합 진한 현황");


		sheet.setFitToPage(true);
		PrintSetup ps = sheet.getPrintSetup();
		ps.setFitWidth((short)1);
		ps.setFitHeight((short)0);
	}
	
	//도메인
	public void createExcelResult_sheet3(List<ResultVO> dataList) throws Exception{	
		logger.debug("Poi createExcel3 started");

		if(dataList.size()==0)	//없는경우
			return;

		//Sheet, Row, Cell 생성
		Sheet sheet = workbook.createSheet("도메인규칙");
		Row row= null;
		Cell cell=null;

		//행열 설정
		int rowNum= 0;	//데이터 +정보를 나타내주는 행
//		if(dataList.size()+1 > 65535)
//			rowNum = 65535;	//데이터 +정보를 나타내주는 행
//		else
			rowNum = dataList.size()+1;
		int colNum=11;

		//각 행열 대한 작업셀 생성
		for(int i=0;i<rowNum;i++){
			row=sheet.createRow(i);
			for(int j=0;j<12;j++){
				cell=row.createCell(j);
			}
		}

		//컬럼 width설정
		sheet.setColumnWidth(0,  3000);
		sheet.setColumnWidth(1,  4000);
		sheet.setColumnWidth(2,  4000);
		sheet.setColumnWidth(3,  4000);
		sheet.setColumnWidth(4,  4000);
		sheet.setColumnWidth(5,  4000);
		sheet.setColumnWidth(6,  4000);
		sheet.setColumnWidth(7,  4000);
		sheet.setColumnWidth(8,  4000);
		sheet.setColumnWidth(9,  6000);
		sheet.setColumnWidth(10, 6000);
		sheet.setColumnWidth(11, 6000);
		sheet.setColumnWidth(12, 3000);


		////////////////////////////////////////////////////////////////
		//하단부 table header
		String[] tempHeader = {"번호","테이블명","한글테이블명","컬럼명","한글컬럼명","데이터타입","검증유형","검증형식"
				,"도메인","검증기준ID","검증기준명"}; //,"분류코드","제외데이터","제외데이터 구분자","도메인 검토내역","관련 검토기준","제외사유"};
		row=sheet.getRow(0);
		for(int i=0;i<colNum;i++){
			cell=row.getCell(i);
			cell.setCellValue(tempHeader[i]);
		}
		
		//하단부 table data
		for(int i=0;i<rowNum-1;i++){
			row=sheet.getRow(i+1);
			
			ResultVO tempVO= dataList.get(i);

			//tempHeader에 해당하는 값들 tempKey에 저장
			String[] tempKey={(i+1)+"", tempVO.getDbcTblNm(), tempVO.getDbcTblKorNm(), tempVO.getDbcColNm()
					, tempVO.getDbcColKorNm(),tempVO.getDataType(), tempVO.getDqiLnm(), tempVO.getPrfKndCd()
					, tempVO.getPrfId(), tempVO.getPrfNm(), tempVO.getPrfTyp()};
			
			for(int j=0;j<colNum;j++){		//데이터
				cell=row.getCell(j);
				String tempCellValue=tempKey[j];
				if(tempCellValue==null || tempCellValue.equals("null") || tempCellValue.length()==0)	//null값을 갖는 값이 있다면
					tempCellValue="";

				cell.setCellValue(tempCellValue);
			}
		}

		////////////////////////////////////////////////////////////////
		//style 적용
		//WiseDQ 프로파일 조회 - titleStyle
		row=sheet.getRow(0);
		for(int i=0;i<=11;i++){
			cell=row.getCell(i);
			cell.setCellStyle(listStyle);
		}
		
		//진단기간,dbms,스키마 - listStyle, 값 - normalStyle
		for(int i=1;i<rowNum;i++){
			row=sheet.getRow(i);
			for(int j=0;j<=11;j++){
				cell.setCellStyle(normalStyle);
			}
		}
		
		////////////////////////////////////////////////////////////////
		//헤더설정 및 프린트옵션 설정
		Header header = sheet.getHeader();
		header.setLeft("WISE DQ 종합 진한 현황");


		sheet.setFitToPage(true);
		PrintSetup ps = sheet.getPrintSetup();
		ps.setFitWidth((short)1);
		ps.setFitHeight((short)0);
	}
	
	//실행목록
	public void createExcelResult_sheet4(List<ResultVO> dataList) throws Exception{	
		logger.debug("Poi createExcel4 started");

		if(dataList.size()==0)	//없는경우
			return;

		//Sheet, Row, Cell 생성
		Sheet sheet = workbook.createSheet("실행목록");
		Row row= null;
		Cell cell=null;

		//행열 설정
		int rowNum= 0;	//데이터 +정보를 나타내주는 행
//		if(dataList.size()+1 > 65535)
//			rowNum = 65535;	//데이터 +정보를 나타내주는 행
//		else
			rowNum = dataList.size()+1;
		int colNum=12;

		//각 행열 대한 작업셀 생성
		for(int i=0;i<rowNum;i++){
			row=sheet.createRow(i);
			for(int j=0;j<=12;j++){
				cell=row.createCell(j);
			}
		}

		//컬럼 width설정
		sheet.setColumnWidth(0,  2000);
		sheet.setColumnWidth(1,  5000);
		sheet.setColumnWidth(2,  4000);
		sheet.setColumnWidth(3,  5000);
		sheet.setColumnWidth(4,  4000);
		sheet.setColumnWidth(5,  4000);
		sheet.setColumnWidth(6,  4000);
		sheet.setColumnWidth(7,  4000);
		sheet.setColumnWidth(8,  4000);
		sheet.setColumnWidth(9,  4000);
		sheet.setColumnWidth(10,  4000);
		sheet.setColumnWidth(11,  4000);
		sheet.setColumnWidth(12,  4000);		


		////////////////////////////////////////////////////////////////
		//하단부 table header
		String[] tempHeader = {"번호","테이블","테이블(한글)","컬럼","컬럼(한글)","데이터 타입","검증유형"
							  ,"검증기준ID","검증기준명","검증형식","검증성공여부","비고"};
		
		row=sheet.getRow(0);
		for(int i=0;i<colNum;i++){
			cell=row.getCell(i);
			cell.setCellValue(tempHeader[i]);
		}
		
		//하단부 table data
		for(int i=0;i<rowNum-1;i++){
			row=sheet.getRow(i+1);
			
			ResultVO tempVO= dataList.get(i);

			//tempHeader에 해당하는 값들 tempKey에 저장
			String[] tempKey={	(i+1)+""
								, tempVO.getDbcTblNm()
								, tempVO.getDbcTblKorNm()
								, tempVO.getDbcColNm()
								, tempVO.getDbcColKorNm()
								, tempVO.getDataType()
								, tempVO.getPrfKndCd()
								, tempVO.getPrfId()
								, tempVO.getPrfNm()
								, tempVO.getPrfTyp()
								, tempVO.getPrfYn()
								, tempVO.getExpRsnCntn()};
			
			for(int j=0;j<colNum;j++){		//데이터
				cell=row.getCell(j);
				String tempCellValue=tempKey[j];
				if(tempCellValue==null || tempCellValue.equals("null") || tempCellValue.length()==0)	//null값을 갖는 값이 있다면
					tempCellValue="";

				cell.setCellValue(tempCellValue);
			}
		}

		////////////////////////////////////////////////////////////////
		//style 적용
		//WiseDQ 프로파일 조회 - titleStyle
		row=sheet.getRow(0);
		for(int i=0;i<=12;i++){
			cell=row.getCell(i);
			cell.setCellStyle(listStyle);
		}
		
		//진단기간,dbms,스키마 - listStyle, 값 - normalStyle
		for(int i=1;i<dataList.size()+1;i++){
			row=sheet.getRow(i);
			for(int j=0;j<=12;j++){
				cell.setCellStyle(normalStyle);
			}
		}
		
		////////////////////////////////////////////////////////////////
		//헤더설정 및 프린트옵션 설정
		Header header = sheet.getHeader();
		header.setLeft("WISE DQ 종합 진한 현황");


		sheet.setFitToPage(true);
		PrintSetup ps = sheet.getPrintSetup();
		ps.setFitWidth((short)1);
		ps.setFitHeight((short)0);
	}
	
	//오류목록
	public void createExcelResult_sheet5(List<ResultVO> dataList) throws Exception{	
		logger.debug("Poi createExcel5 started");

		if(dataList.size()==0)	//없는경우
			return;

		//Sheet, Row, Cell 생성
		Sheet sheet = workbook.createSheet("오류목록");
		Row row= null;
		Cell cell=null;

		//행열 설정
		int rowNum= 0;	//데이터 +정보를 나타내주는 행
//		if(dataList.size()+1 > 65535)
//			rowNum = 65535;	//데이터 +정보를 나타내주는 행
//		else
			rowNum = dataList.size()+1;
		int colNum=11;

		//각 행열 대한 작업셀 생성
		for(int i=0;i<rowNum;i++){
			row=sheet.createRow(i);
			for(int j=0;j<12;j++){
				cell=row.createCell(j);
			}
		}

		//컬럼 width설정
		sheet.setColumnWidth(0,  2000);
		sheet.setColumnWidth(1,  4000);
		sheet.setColumnWidth(2,  4000);
		sheet.setColumnWidth(3,  4000);
		sheet.setColumnWidth(4,  4000);
		sheet.setColumnWidth(5,  4000);
		sheet.setColumnWidth(6,  4000);
		sheet.setColumnWidth(7,  4000);
		sheet.setColumnWidth(8,  4000);
		sheet.setColumnWidth(9,  4000);
		sheet.setColumnWidth(10, 10000);

		////////////////////////////////////////////////////////////////
		//하단부 table header
		String[] tempHeader = {"번호","검증유형","검증형식","테이블명","테이블한글명","컬럼명","컬럼한글명"
							  ,"전체건수","오류건수","오류율(%)","오류데이터"};
		
		row=sheet.getRow(0);
		for(int i=0;i<colNum;i++){
			cell=row.getCell(i);
			cell.setCellValue(tempHeader[i]);
		}
		
		//하단부 table data
		for(int i=0;i<rowNum-1;i++){
			row=sheet.getRow(i+1);
			
			ResultVO tempVO= dataList.get(i);
			
			//tempHeader에 해당하는 값들 tempKey에 저장
			String[] tempKey={	(i+1)+""
								, tempVO.getDqiLnm()
								, tempVO.getPrfKndCd()
								, tempVO.getDbcTblNm()
								, tempVO.getDbcTblKorNm()
								, tempVO.getDbcColNm()
								, tempVO.getDbcColKorNm()
								, tempVO.getTotCnt()+""
								, tempVO.getErrCnt()+""
								, tempVO.getErrRate()+""
								, tempVO.getErrLst()};
			
			for(int j=0;j<colNum;j++){		//데이터
				cell=row.getCell(j);
				String tempCellValue=tempKey[j];
				if(tempCellValue==null || tempCellValue.equals("null") || tempCellValue.length()==0)	//null값을 갖는 값이 있다면
					tempCellValue="";

				cell.setCellValue(tempCellValue);
			}
		}

		////////////////////////////////////////////////////////////////
		//style 적용
		//WiseDQ 프로파일 조회 - titleStyle
		row=sheet.getRow(0);
		for(int i=0;i<12;i++){
			cell=row.getCell(i);
			cell.setCellStyle(listStyle);
		}
		
		//진단기간,dbms,스키마 - listStyle, 값 - normalStyle
		for(int i=1;i<dataList.size()+1;i++){
			row=sheet.getRow(i);
			for(int j=0;j<12;j++){
				cell.setCellStyle(normalStyle);
			}
		}
		
		////////////////////////////////////////////////////////////////
		//헤더설정 및 프린트옵션 설정
		Header header = sheet.getHeader();
		header.setLeft("WISE DQ 종합 진한 현황");


		sheet.setFitToPage(true);
		PrintSetup ps = sheet.getPrintSetup();
		ps.setFitWidth((short)1);
		ps.setFitHeight((short)0);
	}
	
	//품질지표별 오류목록
	public void createExcelResult_sheet6(ResultVO resultVO) throws Exception{	
		logger.debug("Poi createExcel " + resultVO.getDqiLnm() + " started");

		logger.debug("errList size >>> " + resultVO.getErrList().size());
		if(resultVO.getErrList().size()==0)	//없는경우
			return;

		//Sheet, Row, Cell 생성
		Sheet sheet = workbook.createSheet(resultVO.getDqiLnm());
		Row row= null;
		Cell cell=null;

		//행열 설정
		int rowNum= 0;	//데이터 +정보를 나타내주는 행
//		if(resultVO.getErrList().size()+1 > 65535)
//			rowNum = 65535;	//데이터 +정보를 나타내주는 행
//		else
			rowNum = resultVO.getErrList().size()+1;
		int colNum=10;

		//각 행열 대한 작업셀 생성
		for(int i=0;i<rowNum;i++){
			row=sheet.createRow(i);
			for(int j=0;j<11;j++){
				cell=row.createCell(j);
			}
		}

		//컬럼 width설정
		sheet.setColumnWidth(0,  2000);
		sheet.setColumnWidth(1,  4000);
		sheet.setColumnWidth(2,  4000);
		sheet.setColumnWidth(3,  4000);
		sheet.setColumnWidth(4,  4000);
		sheet.setColumnWidth(5,  4000);
		sheet.setColumnWidth(6,  4000);
		sheet.setColumnWidth(7,  4000);
		sheet.setColumnWidth(8,  4000);
		sheet.setColumnWidth(9,  4000);
		sheet.setColumnWidth(10,  4000);
		sheet.setColumnWidth(11,  4000);

		////////////////////////////////////////////////////////////////
		//하단부 table header
//		String[] tempHeader = {"번호","진단룰명","분석유형","진단테이블(영문)","진단테이블(한글)","진단컬럼(영문)","진단컬럼(한글)"
//							  ,"전체건수","오류건수","오류율(%)","오류추정건수 SQL"};
		
		String[] tempHeader = {"번호","검증유형","검증형식","진단테이블(영문)","진단테이블(한글)","진단컬럼(영문)","진단컬럼(한글)"
				  				,"전체건수","오류건수","오류율(%)"};
		
		row=sheet.getRow(0);
		for(int i=0;i<colNum;i++){
			cell=row.getCell(i);
			cell.setCellValue(tempHeader[i]);
		}
		
		//하단부 table data
		for(int i=0;i<rowNum-1;i++){
			row=sheet.getRow(i+1);
			
			ResultDataVO tempVO= resultVO.getErrList().get(i);
			
			//tempHeader에 해당하는 값들 tempKey에 저장
			String[] tempKey={	(i+1)+""
								, tempVO.getDqiLnm()
								, tempVO.getPrfKndCd()
								, tempVO.getDbcTblNm()
								, tempVO.getDbcTblKorNm()
								, tempVO.getDbcColNm()
								, tempVO.getDbcColKorNm()
								, tempVO.getAnaCnt()+""
								, tempVO.getErrCnt()+""
								, tempVO.getErrRate()+""
								, tempVO.getErrSql()};
			
			for(int j=0;j<colNum;j++){		//데이터
				cell=row.getCell(j);
				String tempCellValue=tempKey[j];
				if(tempCellValue==null || tempCellValue.equals("null") || tempCellValue.length()==0)	//null값을 갖는 값이 있다면
					tempCellValue="";

				cell.setCellValue(tempCellValue);
			}
		}

		////////////////////////////////////////////////////////////////
		//style 적용
		//WiseDQ 프로파일 조회 - titleStyle
		row=sheet.getRow(0);
		for(int i=0;i<=10;i++){
			cell=row.getCell(i);
			cell.setCellStyle(listStyle);
		}
		
		//진단기간,dbms,스키마 - listStyle, 값 - normalStyle
		for(int i=1;i<resultVO.getErrList().size()+1;i++){
			row=sheet.getRow(i);
			for(int j=0;j<=10;j++){
				cell.setCellStyle(normalStyle);
			}
		}
		
		////////////////////////////////////////////////////////////////
		//헤더설정 및 프린트옵션 설정
		Header header = sheet.getHeader();
		header.setLeft("WISE DQ 종합 진한 현황");


		sheet.setFitToPage(true);
		PrintSetup ps = sheet.getPrintSetup();
		ps.setFitWidth((short)1);
		ps.setFitHeight((short)0);
	}
	//오류데이터
	public void createExcelResult_sheet7(ResultVO resultVO) throws Exception{	
		logger.debug("Poi createExcel " + resultVO.getDqiLnm() + " err data list started");

		if(resultVO.getErrData().size()==0)	//없는경우
			return;
		
		int colCnt = resultVO.getColCnt().intValue();

		//Sheet, Row, Cell 생성
		Sheet sheet = workbook.createSheet(resultVO.getDqiLnm() + "_오류데이터");
		Row row= null;
		Cell cell=null;
		
		//행열 설정
		int rowNum= 0;
		
//		if(resultVO.getErrData().size()+1 > 65535)
//			rowNum = 65535;	//데이터 +정보를 나타내주는 행
//		else
			rowNum = resultVO.getErrData().size()+1;
		
		int colNum=9;

		//각 행열 대한 작업셀 생성
		for(int i=0;i<rowNum;i++){
			row=sheet.createRow(i);
			for(int j=0;j<10;j++){
				cell=row.createCell(j);
			}
		}

		//컬럼 width설정
		sheet.setColumnWidth(0,  2000);
		sheet.setColumnWidth(1,  4000);
		sheet.setColumnWidth(2,  4000);
		sheet.setColumnWidth(3,  4000);
		sheet.setColumnWidth(4,  4000);
		sheet.setColumnWidth(5,  4000);
		sheet.setColumnWidth(6,  4000);
		sheet.setColumnWidth(7,  4000);
		sheet.setColumnWidth(8,  4000);
		sheet.setColumnWidth(9,  4000);
		sheet.setColumnWidth(10,  4000);

		////////////////////////////////////////////////////////////////
		//하단부 table header
		String[] tempHeader = {"번호","진단유형","진단룰명","진단테이블(영문)","진단테이블(한글)","진단컬럼(영문)","진단컬럼(한글)"
								,"오류데이터", "오류건수"};
		
		row=sheet.getRow(0);
		for(int i=0;i<colNum;i++){
			cell=row.getCell(i);
			cell.setCellValue(tempHeader[i]);
		}
		
		//하단부 table data
		for(int i=0;i<rowNum-1;i++){
			row=sheet.getRow(i+1);
			
			ResultDataVO tempVO= resultVO.getErrData().get(i);

			//tempHeader에 해당하는 값들 tempKey에 저장
			String[] tempKey={	(i+1)+""
								, tempVO.getPrfNm()
								, tempVO.getDqiLnm()
								, tempVO.getDbcTblNm()
								, tempVO.getDbcTblKorNm()
								, tempVO.getDbcColNm()
								, tempVO.getDbcColKorNm()
								, tempVO.getColNm1()
								, tempVO.getErrCnt()+""};
			
			for(int j=0;j<colNum;j++){		//데이터
				cell=row.getCell(j);
				String tempCellValue=tempKey[j];
				if(tempCellValue==null || tempCellValue.equals("null") || tempCellValue.length()==0)	//null값을 갖는 값이 있다면
					tempCellValue="";

				cell.setCellValue(tempCellValue);
			}
		}

		////////////////////////////////////////////////////////////////
		//style 적용
		//WiseDQ 프로파일 조회 - titleStyle
		row=sheet.getRow(0);
		for(int i=0;i<=9;i++){
			cell=row.getCell(i);
			cell.setCellStyle(listStyle);
		}
		
		//진단기간,dbms,스키마 - listStyle, 값 - normalStyle
		for(int i=1;i<resultVO.getErrData().size()+1;i++){
			row=sheet.getRow(i);
			for(int j=0;j<=9;j++){
				cell.setCellStyle(normalStyle);
			}
		}
		
		////////////////////////////////////////////////////////////////
		//헤더설정 및 프린트옵션 설정
		Header header = sheet.getHeader();
		header.setLeft("WISE DQ 종합 진한 현황");


		sheet.setFitToPage(true);
		PrintSetup ps = sheet.getPrintSetup();
		ps.setFitWidth((short)1);
		ps.setFitHeight((short)0);
	}
	
	//표준준수
	public void createExcelStnd(ResultVO term) throws Exception{	
		logger.debug("Poi createExcel started");
		
		createExcelStnd_sheet1(term);
	}
	
	//표준준수
	public void createExcelStnd_sheet1(ResultVO term) throws Exception{	
		logger.debug("Poi createExcel1 started");

		//Sheet, Row, Cell 생성
		Sheet sheet = workbook.createSheet("표준품질현황");
		Row row= null;
		Cell cell=null;

		
		int index;
		//행열 설정
		int rowNum= 10;	//데이터 +정보를 나타내주는 행
		int colNum=6;

		//각 행열 대한 작업셀 생성
		for(int i=0;i<rowNum;i++){
			row=sheet.createRow(i);
			for(int j=0;j<colNum;j++){
				cell=row.createCell(j);
			}
		}
		
		//컬럼 width설정
		sheet.setColumnWidth(0, 4500);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 3500);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 3000);
		sheet.setColumnWidth(5, 4000);
		sheet.setColumnWidth(6, 4000);
		sheet.setColumnWidth(7, 3500);
		sheet.setColumnWidth(8, 4000);
		
		sheet.setColumnWidth(9, 3500);
		sheet.setColumnWidth(10, 4000);
		sheet.setColumnWidth(11, 3500);

		
		////////////////////////////////////////////////////////////////
		//값 입력
		//WiseDQ진단 종합 현황 
		row=sheet.getRow(0);
		cell=row.getCell(0);
		cell.setCellValue("WISE DQ 표준진단종합현황");
		
		//출력일
		row=sheet.getRow(1);
		cell=row.getCell(0);
		cell.setCellValue("출력일 : " + getTodayTime());
		
		//진단 데이터베이스 기본 정보
		row=sheet.getRow(2);
		cell=row.getCell(0);
		cell.setCellValue("진단 데이터베이스 기본 정보");
		

		String[] tempList={"진단기간", "DBMS명", "스키마명"};
		String[] tempValue={term.getAnaStrDtm() + " ~ " + term.getAnaEndDtm(), term.getDbConnTrgLnm(), term.getDbSchLnm()};
		index=0;
		for(int i=3;i<=5;i++){
			row=sheet.getRow(i);
			cell=row.getCell(0);
			cell.setCellValue(tempList[index]);

			//값 설정
			cell=row.getCell(3);
			cell.setCellValue(tempValue[index++]);
		}
		
		BigDecimal multi = new BigDecimal("100");
		
		String[] tempList2={"분석영역", "검증룰명", "컬럼수", "불일치수", "불일치율", "적용율"};
		String[] tempValue2={"표준 진단"
							, "표준용어준수(+도메인)"
							, makeComma(term.getTotCnt()+"")
							, makeComma(term.getErrCnt()+"")
							, term.getErrCnt().divide(term.getTotCnt(), 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%"
							, term.getTotCnt().subtract(term.getErrCnt()).divide(term.getTotCnt(), 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%"};
		
		for(int i=0;i<tempList2.length;i++){
			row=sheet.getRow(7);
			cell=row.getCell(i);
			cell.setCellValue(tempList2[i]);

			//값 설정
			row=sheet.getRow(8);
			cell=row.getCell(i);
			cell.setCellValue(tempValue2[i]);
		}

		////////////////////////////////////////////////////////////////
		//style 적용
		//WiseDQ 프로파일 조회 - titleStyle
		row=sheet.getRow(0);
		for(int i=0;i<colNum;i++){
			cell=row.getCell(i);
			cell.setCellStyle(titleStyle);
		}

		//출력일 = dataStyle
		row=sheet.getRow(1);
		cell=row.getCell(0);
		cell.setCellStyle(dataStyle);

		//진단데이터베이스정보 - headerStyle
		row=sheet.getRow(2);
		for(int i=0;i<colNum;i++){
			cell=row.getCell(i);
			cell.setCellStyle(headerStyle);
		}

		//진단기간,dbms,스키마 - listStyle, 값 - normalStyle
		for(int i=3;i<6;i++){
			row=sheet.getRow(i);
			for(int j=0;j<colNum;j++){
				cell=row.getCell(j);
				if(j<=2)
					cell.setCellStyle(listStyle);
				else
					cell.setCellStyle(normalStyle);
			}
		}
		
		row=sheet.getRow(7);
		for(int i=0;i<colNum;i++){
			cell=row.getCell(i);
			cell.setCellStyle(headerStyle);
		}
//		
		row=sheet.getRow(8);
		cell=row.getCell(0);
		cell.setCellStyle(listStyle);
		for(int i=1;i<colNum;i++){
			cell=row.getCell(i);
			cell.setCellStyle(dataStyle);
		}

		logger.debug("end style");
		////////////////////////////////////////////////////////////////
		//셀병합 cellRangeAddress(행시작,행끝,열시작,열끝)
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,colNum-1));	//WiseDQ 프로파일 조회
		sheet.addMergedRegion(new CellRangeAddress(1,1,0,colNum-1));	//출력일	
		sheet.addMergedRegion(new CellRangeAddress(2,2,0,colNum-1));	//진단 데이터베이스 기본 정보

		for(int i=3;i<6;i++){
			sheet.addMergedRegion(new CellRangeAddress(i,i,0,2));		//진단기간, DBMS명, 스키마명
			sheet.addMergedRegion(new CellRangeAddress(i,i,3,colNum-1));//값
		}

		////////////////////////////////////////////////////////////////
		//헤더설정 및 프린트옵션 설정
		Header header = sheet.getHeader();
		header.setLeft("WISE DQ 표준 진단 현황");


		sheet.setFitToPage(true);
		PrintSetup ps = sheet.getPrintSetup();
		ps.setFitWidth((short)1);
		ps.setFitHeight((short)0);
	}
	
	//구조품질
	public void createExcelModel(ResultVO term, String tblCnt, String tblNcnt, String colCnt, String colNcnt, String pdmColCnt, String pdmColNcnt, String pdmTblCnt, String pdmTblNcnt) throws Exception{	
		logger.debug("Poi createExcel started");
		
		createExcelModel_sheet1(term, tblCnt, tblNcnt, colCnt, colNcnt, pdmColCnt, pdmColNcnt, pdmTblCnt, pdmTblNcnt);
	}
	
	//구조품질
	public void createExcelModel_sheet1(ResultVO term, String tblCnt, String tblNcnt, String colCnt, String colNcnt, String pdmColCnt, String pdmColNcnt, String pdmTblCnt, String pdmTblNcnt) throws Exception{	
		logger.debug("Poi createExcel1 started");

		//Sheet, Row, Cell 생성
		Sheet sheet = workbook.createSheet("진단결과종합현황");
		Row row= null;
		Cell cell=null;

		int index;
		//행열 설정
		int rowNum= 16;	//데이터 +정보를 나타내주는 행
		int colNum=6;

		//각 행열 대한 작업셀 생성
		for(int i=0;i<rowNum;i++){
			row=sheet.createRow(i);
			for(int j=0;j<colNum;j++){
				cell=row.createCell(j);
			}
		}

		//컬럼 width설정
		sheet.setColumnWidth(0, 4500);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 3000);
		sheet.setColumnWidth(5, 4000);
		sheet.setColumnWidth(6, 4000);
		sheet.setColumnWidth(7, 3500);
		sheet.setColumnWidth(8, 4000);
		sheet.setColumnWidth(9, 4000);
		sheet.setColumnWidth(10, 3000);
		sheet.setColumnWidth(11, 3000);
		sheet.setColumnWidth(12, 3000);
		sheet.setColumnWidth(13, 3000);
		sheet.setColumnWidth(14, 3000);
		sheet.setColumnWidth(15, 3000);


		////////////////////////////////////////////////////////////////
		//값 입력
		//WiseDQ진단 종합 현황 
		row=sheet.getRow(0);
		cell=row.getCell(0);
		cell.setCellValue("WISE DQ 구조 진단 종합 현황");

		//출력일
		row=sheet.getRow(1);
		cell=row.getCell(0);
		cell.setCellValue("출력일 : " + getTodayTime());


		//진단 데이터베이스 기본 정보
		row=sheet.getRow(2);
		cell=row.getCell(0);
		cell.setCellValue("진단 데이터베이스 기본 정보");


		String[] tempList={"진단기간", "DBMS명", "스키마명"};
		String[] tempValue={term.getAnaStrDtm() + " ~ " + term.getAnaEndDtm(), term.getDbConnTrgLnm(), term.getDbSchLnm()};
		index=0;
		for(int i=3;i<=5;i++){
			row=sheet.getRow(i);
			cell=row.getCell(0);
			cell.setCellValue(tempList[index]);

			//값 설정
			cell=row.getCell(3);
			cell.setCellValue(tempValue[index++]);
		}
		
		BigDecimal multi = new BigDecimal("100");
		BigDecimal tbl = new BigDecimal(tblCnt.trim()==null?"0":tblCnt.trim());
		BigDecimal tblN = new BigDecimal(tblNcnt.trim()==null?"0":tblNcnt.trim());
		BigDecimal col = new BigDecimal(colCnt.trim()==null?"0":colCnt.trim());
		BigDecimal colN = new BigDecimal(colNcnt.trim()==null?"0":colNcnt.trim());
		BigDecimal pdmTbl = new BigDecimal(pdmTblCnt.trim()==null?"0":pdmTblCnt.trim());
		BigDecimal pdmTblN = new BigDecimal(pdmTblNcnt.trim()==null?"0":pdmTblNcnt.trim());
		BigDecimal pdmCol = new BigDecimal(pdmColCnt.trim()==null?"0":pdmColCnt.trim());
		BigDecimal pdmColN = new BigDecimal(pdmColNcnt.trim()==null?"0":pdmColNcnt.trim());
		
		String[] tempList2={"분석영역", "검증룰명", "테이블/컬럼수", "불일치수", "불일치율", "현행화율"};
		String[] tempValue2={"구조진단"
							, "물리DB 테이블 불일치"
							, makeComma(tbl+"")
							, makeComma(tblN+"")
							, tblN.divide(tbl, 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%"
							, tbl.subtract(tblN).divide(tbl, 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%"};
		String[] tempValue3={"구조진단"
							, "물리DB 컬럼 불일치"
							, makeComma(col+"")
							, makeComma(colN+"")
							, colN.divide(col, 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%"
							, col.subtract(colN).divide(col, 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%"};
		String[] tempValue4={"구조진단"
							, "모델정의서 테이블 불일치"
							, makeComma(pdmTbl+"")
							, makeComma(pdmTblN+"")
							, pdmTblN.divide(pdmTbl, 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%"
							, pdmTbl.subtract(pdmTblN).divide(pdmTbl, 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%"};
		String[] tempValue5={"구조진단"
							, "모델정의서 컬럼 불일치"
							, makeComma(pdmCol+"")
							, makeComma(pdmColN+"")
							, pdmColN.divide(pdmCol, 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%"
							, pdmCol.subtract(pdmColN).divide(pdmCol, 3, BigDecimal.ROUND_HALF_UP).multiply(multi) + "%"};

		for(int i=0;i<tempList2.length;i++){
			row=sheet.getRow(7);
			cell=row.getCell(i);
			cell.setCellValue(tempList2[i]);

			//값 설정
			row=sheet.getRow(8);
			cell=row.getCell(i);
			cell.setCellValue(tempValue2[i]);
			
			//값 설정
			row=sheet.getRow(9);
			cell=row.getCell(i);
			cell.setCellValue(tempValue3[i]);
			
			//값 설정
			row=sheet.getRow(10);
			cell=row.getCell(i);
			cell.setCellValue(tempValue4[i]);
			
			//값 설정
			row=sheet.getRow(11);
			cell=row.getCell(i);
			cell.setCellValue(tempValue5[i]);
		}


		////////////////////////////////////////////////////////////////
		//style 적용
		//WiseDQ 프로파일 조회 - titleStyle
		row=sheet.getRow(0);
		for(int i=0;i<colNum;i++){
			cell=row.getCell(i);
			cell.setCellStyle(titleStyle);
		}

		//출력일 = dataStyle
		row=sheet.getRow(1);
		cell=row.getCell(0);
		cell.setCellStyle(dataStyle);

		//진단데이터베이스정보 - headerStyle
		row=sheet.getRow(2);
		for(int i=0;i<colNum;i++){
			cell=row.getCell(i);
			cell.setCellStyle(headerStyle);
		}

		//진단기간,dbms,스키마 - listStyle, 값 - normalStyle
		for(int i=3;i<6;i++){
			row=sheet.getRow(i);
			for(int j=0;j<colNum;j++){
				cell=row.getCell(j);
				if(j<=2)
					cell.setCellStyle(listStyle);
				else
					cell.setCellStyle(normalStyle);
			}
		}
		
		row=sheet.getRow(7);
		for(int i=0;i<colNum;i++){
			cell=row.getCell(i);
			cell.setCellStyle(headerStyle);
		}
		
//		row=sheet.getRow(8);
//		cell=row.getCell(0);
//		cell.setCellStyle(listStyle);
		for(int j=8; j<12; j++) {
			row=sheet.getRow(j);
			cell=row.getCell(0);
			cell.setCellStyle(listStyle);
			for(int i=1;i<colNum;i++){
				cell=row.getCell(i);
				cell.setCellStyle(dataStyle);
			}
		}

		////////////////////////////////////////////////////////////////
		//셀병합 cellRangeAddress(행시작,행끝,열시작,열끝)
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,colNum-1));	//WiseDQ 프로파일 조회
		sheet.addMergedRegion(new CellRangeAddress(1,1,0,colNum-1));	//출력일	
		sheet.addMergedRegion(new CellRangeAddress(2,2,0,colNum-1));	//진단 데이터베이스 기본 정보

		for(int i=3;i<6;i++){
			sheet.addMergedRegion(new CellRangeAddress(i,i,0,2));		//진단기간, DBMS명, 스키마명
			sheet.addMergedRegion(new CellRangeAddress(i,i,3,colNum-1));//값
		}
		
		sheet.addMergedRegion(new CellRangeAddress(8,11,0,0));

		////////////////////////////////////////////////////////////////
		//헤더설정 및 프린트옵션 설정
		Header header = sheet.getHeader();
		header.setLeft("WISE DQ 구조 진단 종합 현황");


		sheet.setFitToPage(true);
		PrintSetup ps = sheet.getPrintSetup();
		ps.setFitWidth((short)1);
		ps.setFitHeight((short)0);
	}
	
	//출력함수
	public void downExcel (String fileName,HttpServletResponse response) throws Exception{
		logger.debug("Poi DownExcel started");

		if(this.type.equals("1")) {
			this.type = ".xls";
		} else {
			this.type = ".xlsx";
		}
		//출력
		response.setContentType("Application/Msexcel");
		response.setHeader("Content-Disposition", "attachment; Filename="+URLEncoder.encode(fileName,"UTF-8")+ this.type);
		OutputStream fileOut  = response.getOutputStream();
		workbook.write(fileOut);

		fileOut.close();
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}

	//기타 필요한 함수
	//기간출력함수
	public String findStartEndDate(List<WamPrfMstrCommonVO> dataList){
		int max=00000000;
		int min=99999999;

		for(int i=0;i<dataList.size();i++){
			String temp = dataList.get(i).getAnaStrDtm();	//날짜시간
			if(temp!=null&&!temp.equals("")){
				int tempDate = Integer.parseInt(temp.substring(0,10).replace("-", ""));	//YYYYMMDD
				if(tempDate >= max)
					max=tempDate;
				if(tempDate<=min)
					min=tempDate;
			}
		}
		String minStr=min+"";
		String maxStr=max+"";
		
		if(max == 0)	//아무것도 안거친 경우
			return "";
		return minStr.substring(0,4)+"/"+minStr.substring(4,6)+"/"+minStr.substring(6,8)
				+" ~ "+ maxStr.substring(0,4)+"/"+maxStr.substring(4,6)+"/"+maxStr.substring(6,8);
	}

	//오늘 날짜 출력함수
	public String getTodayTime(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date time = new Date();
		return format.format(time);
	}

	//날짜출력함수
	public String getDateFormat(String data){
		if(data.equals(""))
			return "";
		return data.substring(0,data.length()-2);
	}

	//숫자3자리마다 , 생성함수
	public String makeComma(String str){
		if(str ==null || str.equals("") || str.equals("null") || str.length()==0)
			return "";

		String type = str.getClass().getName();
		DecimalFormat format = null;

		if(type.indexOf("String") == -1) {
			format = new DecimalFormat("###,###");
			return format.format(Long.parseLong(str));
		} else {
			return str;
		}
	}

	//sum함수
	public String sumOf(List<WamPrfMstrCommonVO> dataList, String key){
		
		BigInteger sum =BigInteger.ZERO;
		int countNull=0;
		for(int i=0;i<dataList.size();i++){
			if(key.equals("anaCnt")) {	//분석총건수총합
				String tempValue = dataList.get(i).getAnaCnt();
				if(tempValue == null || tempValue.equals("") || tempValue.equals("null") || tempValue.length()==0)
					countNull++;
				else{
					long temp =Long.parseLong(tempValue);
					sum=sum.add(BigInteger.valueOf(temp));
				}

			}else if(key.equals("esnErCnt")){	//추정오류건수총합
				String tempValue = dataList.get(i).getEsnErCnt();
				if(tempValue == null || tempValue.equals("") || tempValue.equals("null") || tempValue.length()==0)
					countNull++;
				else{
					long temp =Long.parseLong(tempValue);
					sum=sum.add(BigInteger.valueOf(temp));
				}
			}else if(key.equals("nullCnt")){ //널건수 총합
				String tempValue = dataList.get(i).getNullCnt()+"";
				if(tempValue == null || tempValue.equals("") || tempValue.equals("null") || tempValue.length()==0)
					countNull++;
				else{
					long temp =Long.parseLong(tempValue);
					sum=sum.add(BigInteger.valueOf(temp));
				}
			}else if(key.equals("spaceCnt")) {	//공백 건수 총합
				String tempValue = dataList.get(i).getSpaceCnt()+"";
				if(tempValue == null || tempValue.equals("") || tempValue.equals("null") || tempValue.length()==0)
					countNull++;
				else{
					long temp =Long.parseLong(tempValue);
					sum=sum.add(BigInteger.valueOf(temp));
				}
			}
			
		}

		if(countNull==dataList.size())
			return "";

		return sum+"";
	}


	//avg함수
	public String avgOf(List<WamPrfMstrCommonVO> dataList, String key){
		double sum=0;
		int countNull=0;
		for(int i=0;i<dataList.size();i++){
			if(key.equals("colErrRate")) {	//추정오류율평균
				String tempValue = dataList.get(i).getColErrRate();
				if(tempValue == null || tempValue.equals("") || tempValue.equals("null") || tempValue.length()==0)
					countNull++;
				else
					sum += Double.parseDouble(checkPointNum(tempValue));
			}
			else if(key.equals("sigma")){	//시그마평균
				String tempValue = dataList.get(i).getSigma();
				if(tempValue == null || tempValue.equals("") || tempValue.equals("null") || tempValue.length()==0)
					countNull++;
				else
					sum += Double.parseDouble(checkPointNum(tempValue));
			}else if(key.equals("minLen")){	//최소길이평균
				String tempValue = dataList.get(i).getMinLen()+"";
				if(tempValue == null || tempValue.equals("") || tempValue.equals("null") || tempValue.length()==0)
					countNull++;
				else
					sum += Double.parseDouble(checkPointNum(tempValue));
			}else if(key.equals("maxLen")){	//최대길이평균
				String tempValue = dataList.get(i).getMaxLen()+"";
				if(tempValue == null || tempValue.equals("") || tempValue.equals("null") || tempValue.length()==0)
					countNull++;
				else
					sum += Double.parseDouble(checkPointNum(tempValue));
			}
		}
		if(countNull==dataList.size())
			return "";
		if(sum==0)
			return "0";

		return String.format("%.2f", sum/(dataList.size()-countNull));
	}

	//소수점 문자열 검사함수
	public String checkPointNum(String value){
		String temp=value;
		if(value == null || value.length()==0)
			return "";
		else if(value.charAt(0)=='.'&&value.length()!=0)
			temp="0"+value;
		return temp;
	}
}
