package tw.gov.bli.ba.query.report;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.Region;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Caub;
import tw.gov.bli.ba.query.cases.AnnuityStatisticsCase;
import tw.gov.bli.ba.query.forms.AnnuityStatisticsForm;
import tw.gov.bli.ba.rpt.report.ExcelReportBase;
import tw.gov.bli.ba.util.DateUtility;

public class AnnuityStatisticsExcelReport02 extends ExcelReportBase {

	private HSSFSheet tempSheet;
	private HSSFCell tempCell;

	public String getPayKindStr(String payKind) {
		if (payKind.equals("L"))
			return ConstantKey.PAYKIND_L;
		else if (payKind.equals("K"))
			return ConstantKey.PAYKIND_K;
		else if (payKind.equals("S"))
			return ConstantKey.PAYKIND_S;
		else
			return "";
	}

	public AnnuityStatisticsExcelReport02(String excelTyp) {
		super(excelTyp);
	}

	private void printHead(String payCodeStr) {
		tempSheet = workbook.getSheetAt(0);
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFRow row0 = sheet.getRow(0);
		setCell(row0.getCell((short) 0), payCodeStr);
	}

	public ByteArrayOutputStream doReport(List<AnnuityStatisticsCase> caseDataList, String payKind, AnnuityStatisticsForm queryForm) throws Exception {
		tempSheet = workbook.getSheetAt(0);
		HSSFSheet sheet = workbook.getSheetAt(0);
		printHead(getPayKindStr(payKind));
	
		if (StringUtils.equals(queryForm.getAnalysisSelect(), "X")) {
			HSSFRow row = sheet.getRow(4);
			int paymentYM = writeYM(queryForm, sheet, row, (short) 4, null);
			checkYMAndWriteDataForXB(caseDataList, sheet, row, (short) 4, (short) 1);
			writeTotal(sheet, row, paymentYM, 1, (short) 4);
		}
		
		workbook.getSheetAt(0).setSelected(true);
		workbook.write(bao);
		return bao;
	}
	public ByteArrayOutputStream doReport(List<AnnuityStatisticsCase> yearTotalCaseDataList, List<AnnuityStatisticsCase> caseDataList, String payKind, AnnuityStatisticsForm queryForm) throws Exception {
		tempSheet = workbook.getSheetAt(0);
		HSSFSheet sheet = workbook.getSheetAt(0);
		printHead(getPayKindStr(payKind));
		
		if(StringUtils.equals(queryForm.getAnalysisSelect(), "S")) {
			HSSFRow row = sheet.getRow(5);
			int paymentYM = writeYM(queryForm, sheet, row, (short) 5, null);
			List<String> eq = Arrays.asList("1","2");
			checkYMAndWriteData(caseDataList, sheet, row, (short) 5, (short) 5, (short) 4, "sex", eq);
//			writeYearTotal(sheet, row, eq.size(), 4, paymentYM, (short) 5, (short) 1, (short) 5);
			row = sheet.getRow(5);
			checkYMAndWriteDataForXB(yearTotalCaseDataList, sheet, row, (short) 5, (short) 1);
			writeTotal(sheet, row, paymentYM, eq.size() + 1, (short) 5);
		}else if(StringUtils.equals(queryForm.getAnalysisSelect(), "E")) {
			HSSFRow row = sheet.getRow(5);
			int paymentYM = writeYM(queryForm, sheet, row, (short) 5, null);
			List<String> eq = Arrays.asList("1","3");
			checkYMAndWriteData(caseDataList, sheet, row, (short) 5, (short) 5, (short) 4, "evType", eq);
//			writeYearTotal(sheet, row, eq.size(), 4, paymentYM, (short) 5, (short) 1, (short) 5);
			row = sheet.getRow(5);
			checkYMAndWriteDataForXB(yearTotalCaseDataList, sheet, row, (short) 5, (short) 1);
			writeTotal(sheet, row, paymentYM, eq.size() + 1, (short) 5);
		}else if(StringUtils.equals(queryForm.getAnalysisSelect(), "N")) {
			HSSFRow row = sheet.getRow(5);
			int paymentYM = writeYM(queryForm, sheet, row, (short) 5, null);
			List<String> eq = Arrays.asList("1","2");
			checkYMAndWriteData(caseDataList, sheet, row, (short) 5, (short) 5, (short) 4, "EvtNationTpe", eq);
//			writeYearTotal(sheet, row, eq.size(), 4, paymentYM, (short) 5, (short) 1, (short) 5);
			row = sheet.getRow(5);
			checkYMAndWriteDataForXB(yearTotalCaseDataList, sheet, row, (short) 5, (short) 1);
			writeTotal(sheet, row, paymentYM, eq.size() + 1, (short) 5);
		}else if(StringUtils.equals(queryForm.getAnalysisSelect(), "C")) {
			HSSFRow row = sheet.getRow(5);
			int paymentYM = writeYM(queryForm, sheet, row, (short) 5, null);
			List<String> eq = Arrays.asList("","F","Y","N","1","2","3","4","5");
			checkYMAndWriteData(caseDataList, sheet, row, (short) 5, (short) 5, (short) 4, "cipbFMk", eq);
//			writeYearTotal(sheet, row, eq.size(), 4, paymentYM, (short) 5, (short) 1, (short) 5);
			row = sheet.getRow(5);
			checkYMAndWriteDataForXB(yearTotalCaseDataList, sheet, row, (short) 5, (short) 1);
			writeTotal(sheet, row, paymentYM, eq.size() + 1, (short) 5);
		}
		
		workbook.getSheetAt(0).setSelected(true);
		workbook.write(bao);
		return bao;
	}

	public ByteArrayOutputStream doReport(List<AnnuityStatisticsCase> yearTotalCaseDataList, List<AnnuityStatisticsCase> caseDataList, String payKind,
			AnnuityStatisticsForm queryForm, List<Caub> caubList) throws Exception {
		tempSheet = workbook.getSheetAt(0);
		HSSFSheet sheet = workbook.getSheetAt(0);
		printHead(getPayKindStr(payKind));
		
		short ubTypeCol = 5;
		HSSFCell cell;
		// 寫ubType
		for (Caub c : caubList) {
			HSSFRow row = sheet.getRow(3);
			// 第一個儲存格寫ubType
			tempCell = tempSheet.getRow(3).getCell((short) 5);
			cell = row.createCell(ubTypeCol);
			setCell(cell, tempCell.getCellStyle(), c.getUbType());
			sheet.setColumnWidth(ubTypeCol, sheet.getColumnWidth((short) 3));
			// 第二個儲存格複製格式
			tempCell = tempSheet.getRow(3).getCell((short) 6);
			cell = row.createCell((short) (ubTypeCol + 1));
			cell.setCellStyle(tempCell.getCellStyle());
			sheet.setColumnWidth((short) (ubTypeCol + 1), sheet.getColumnWidth((short) 3));
			// 第三個儲存格複製格式
			tempCell = tempSheet.getRow(3).getCell((short) 7);
			cell = row.createCell((short) (ubTypeCol + 2));
			cell.setCellStyle(tempCell.getCellStyle());
			sheet.setColumnWidth((short) (ubTypeCol + 2), sheet.getColumnWidth((short) 3));
			// 第四個儲存格複製格式
			tempCell = tempSheet.getRow(3).getCell((short) 8);
			cell = row.createCell((short) (ubTypeCol + 3));
			cell.setCellStyle(tempCell.getCellStyle());
			sheet.setColumnWidth((short) (ubTypeCol + 3), sheet.getColumnWidth((short) 3));
			// 合併上面四個儲存格
			Region region = new Region();
			region.setRowFrom(3);
			region.setRowTo(3);
			region.setColumnFrom(ubTypeCol);
			region.setColumnTo((short) (ubTypeCol + 3));
			sheet.addMergedRegion(region);
			// 寫平均單價格子
			row = incrementRow(sheet, row);
			tempCell = tempSheet.getRow(4).getCell((short) 5);
			cell = row.createCell(ubTypeCol);
			setCell(cell, tempCell.getCellStyle(), "平均單價");
			// 寫平均薪資格子
			tempCell = tempSheet.getRow(4).getCell((short) 6);
			cell = row.createCell((short) (ubTypeCol + 1));
			setCell(cell, tempCell.getCellStyle(), "平均薪資");
			// 寫平均年資格子
			tempCell = tempSheet.getRow(4).getCell((short) 7);
			cell = row.createCell((short) (ubTypeCol + 2));
			setCell(cell, tempCell.getCellStyle(), "平均年資");
			// 寫平均年齡格子
			tempCell = tempSheet.getRow(4).getCell((short) 8);
			cell = row.createCell((short) (ubTypeCol + 3));
			setCell(cell, tempCell.getCellStyle(), "平均年齡");
			// 複製平均單價下面格子
			row = incrementRow(sheet, row);
			tempCell = tempSheet.getRow(5).getCell((short) 5);
			cell = row.createCell(ubTypeCol);
			cell.setCellStyle(tempCell.getCellStyle());
			// 複製平均薪資下面格子
			tempCell = tempSheet.getRow(5).getCell((short) 6);
			cell = row.createCell((short) (ubTypeCol + 1));
			cell.setCellStyle(tempCell.getCellStyle());
			// 複製平均年資下面格子
			tempCell = tempSheet.getRow(5).getCell((short) 7);
			cell = row.createCell((short) (ubTypeCol + 2));
			cell.setCellStyle(tempCell.getCellStyle());
			// 複製平均年齡下面格子
			tempCell = tempSheet.getRow(5).getCell((short) 8);
			cell = row.createCell((short) (ubTypeCol + 3));
			cell.setCellStyle(tempCell.getCellStyle());
			// 複製平均單價合計欄
			row = incrementRow(sheet, row);
			tempCell = tempSheet.getRow(6).getCell((short) 5);
			cell = row.createCell(ubTypeCol);
			cell.setCellStyle(tempCell.getCellStyle());
			// 複製平均薪資合計欄
			tempCell = tempSheet.getRow(6).getCell((short) 6);
			cell = row.createCell((short) (ubTypeCol + 1));
			cell.setCellStyle(tempCell.getCellStyle());
			// 複製平均年資合計欄
			tempCell = tempSheet.getRow(6).getCell((short) 7);
			cell = row.createCell((short) (ubTypeCol + 2));
			cell.setCellStyle(tempCell.getCellStyle());
			// 複製平均年齡合計欄
			tempCell = tempSheet.getRow(6).getCell((short) 8);
			cell = row.createCell((short) (ubTypeCol + 3));
			cell.setCellStyle(tempCell.getCellStyle());
			// 往右移四格
			ubTypeCol += 4;
		}
		HSSFRow row = sheet.getRow(5);
		int paymentYM = writeYM(queryForm, sheet, row, (short) 5, "ubType");
		List<String> eq = new ArrayList<String>();
		for(Caub c : caubList)
			eq.add(c.getUbType());
		checkYMAndWriteData(caseDataList, sheet, row, (short) 5, (short) 5, (short) 4, "ubType", eq);
//		writeYearTotal(sheet, row, eq.size(), 4, paymentYM, (short) 5, (short) 1, (short) 5);
		row = sheet.getRow(5);
		checkYMAndWriteDataForXB(yearTotalCaseDataList, sheet, row, (short) 5, (short) 1);
		writeTotal(sheet, row, paymentYM, eq.size() + 1, (short) 5);
		
		workbook.getSheetAt(0).setSelected(true);
		workbook.write(bao);
		return bao;
	}
	/**
	 * 寫年月，並把所有儲存格都預設為0
	 */
	private int writeYM(AnnuityStatisticsForm queryForm, HSSFSheet sheet, HSSFRow row, short rowNum, String eq) {
		HSSFCell cell;
		// 計算年月
		int paymentYMStart = Integer.parseInt(queryForm.getPaymentYMStart());
		int paymentYM = DateUtility.monthsBetween(queryForm.getPaymentYMEnd() + "01", queryForm.getPaymentYMStart() + "01");
		paymentYM += 1;

		for (int i = 0; i < paymentYM; i++) {
			// 寫年月
			tempCell = tempSheet.getRow(rowNum).getCell((short) 0);
			cell = row.createCell((short) 0); 
			setCell(cell, tempCell.getCellStyle(), paymentYMStart);
			// 月份+1，如果月份大於12月，+1年
			if ((Integer.parseInt(StringUtils.substring(String.valueOf(paymentYMStart), 3, 5)) + 1) < 13) {
				paymentYMStart += 1;
			} else {
				paymentYMStart -= 12;
				paymentYMStart += 101;
			}
			int lastCellNum = StringUtils.equals(eq, "ubType") ? sheet.getRow(5).getLastCellNum() : sheet.getRow(5).getLastCellNum() - 1;
			// 把件數跟金額都預設為 0
			for (int j = 0; j < lastCellNum; j++) {
				int cellNum = j + 1;
				tempCell = tempSheet.getRow(rowNum).getCell((short) 1);
				cell = row.createCell((short) (cellNum));
				setCell(cell, tempCell.getCellStyle(), 0);
			}
			// 如果還有下一欄，把合計欄往下移動一欄，並把游標往下一欄移動
			if (i + 1 < paymentYM) {
				sheet.shiftRows(sheet.getLastRowNum(), sheet.getLastRowNum(), 1, true, false);
				row = incrementRow(sheet, row);
				// 如果沒有下一欄，把合計欄的件數和金額都先預設為 0
			} else {
				row = incrementRow(sheet, row);
				for (int j = 0; j < lastCellNum; j++) {
					int cellNum = j + 1;
					tempCell = tempSheet.getRow(rowNum).getCell((short) 1);
					cell = row.createCell((short) (cellNum));
					setCell(cell, tempCell.getCellStyle(), 0);
				}
			}
		}
		return paymentYM;
	}

	/**
	 * 確認資料的年月與表格上的年月，並寫資料 For XB
	 */
	private void checkYMAndWriteDataForXB(List<AnnuityStatisticsCase> caseDataList, HSSFSheet sheet, HSSFRow row, short rowNum, short cellNum) {
		HSSFCell cell;
		for (int i = 0; i < caseDataList.size(); i++) {
			// caseData年月
			String nowIssuYm = DateUtility.changeChineseYearMonthType(caseDataList.get(i).getIssuYm());
			// 現在表格Row的年月
			String colIssuYm = "0";
			if (String.valueOf((int) row.getCell((short) 0).getNumericCellValue()).length() == 4) {
				colIssuYm += String.valueOf((int) row.getCell((short) 0).getNumericCellValue());
				colIssuYm = DateUtility.changeChineseYearMonthType(colIssuYm);
			} else {
				colIssuYm = DateUtility.changeChineseYearMonthType(String.valueOf((int) row.getCell((short) 0).getNumericCellValue()));
			}
			// 檢查此筆年月跟表格Row的年月一樣
			if (StringUtils.equals(nowIssuYm, colIssuYm)) {
				// 寫平均單價
				tempCell = tempSheet.getRow(rowNum).getCell(cellNum);
				cell = row.createCell(cellNum);
				setCell(cell, tempCell.getCellStyle(),
						NumberFormat.getInstance().format(Long.parseLong(caseDataList.get(i).getAvgPayAmt())));
				// 寫平均薪資
				tempCell = tempSheet.getRow(rowNum).getCell((short) (cellNum + 1));
				cell = row.createCell((short) (cellNum + 1));
				setCell(cell, tempCell.getCellStyle(),
						NumberFormat.getInstance().format(Float.parseFloat(caseDataList.get(i).getAvgPWage())));
				// 寫平均年資
				tempCell = tempSheet.getRow(rowNum).getCell((short) (cellNum + 2));
				cell = row.createCell((short) (cellNum + 2));
				setCell(cell, tempCell.getCellStyle(),
						NumberFormat.getInstance().format(Float.parseFloat(caseDataList.get(i).getAvgNitrm())));
				// 寫平均年齡
				tempCell = tempSheet.getRow(rowNum).getCell((short) (cellNum + 3));
				cell = row.createCell((short) (cellNum + 3));
				setCell(cell, tempCell.getCellStyle(),
						NumberFormat.getInstance().format(Float.parseFloat(caseDataList.get(i).getAvgAge())));
				// 如果此筆年月跟表格表格Row的年月不一樣，往下尋找
			} else {
				while (!StringUtils.equals(nowIssuYm, colIssuYm)) {
					row = incrementRow(sheet, row);
					colIssuYm = "0";
					if (String.valueOf((int) row.getCell((short) 0).getNumericCellValue()).length() == 4) {
						colIssuYm += String.valueOf((int) row.getCell((short) 0).getNumericCellValue());
						colIssuYm = DateUtility.changeChineseYearMonthType(colIssuYm);
					} else {
						colIssuYm = DateUtility.changeChineseYearMonthType(String.valueOf((int) row.getCell((short) 0).getNumericCellValue()));
					}
				}
				// 寫平均單價
				tempCell = tempSheet.getRow(rowNum).getCell(cellNum);
				cell = row.createCell(cellNum);
				setCell(cell, tempCell.getCellStyle(),
						NumberFormat.getInstance().format(Long.parseLong(caseDataList.get(i).getAvgPayAmt())));
				// 寫平均薪資
				tempCell = tempSheet.getRow(rowNum).getCell((short) (cellNum + 1));
				cell = row.createCell((short) (cellNum + 1));
				setCell(cell, tempCell.getCellStyle(),
						NumberFormat.getInstance().format(Float.parseFloat(caseDataList.get(i).getAvgPWage())));
				// 寫平均年資
				tempCell = tempSheet.getRow(rowNum).getCell((short) (cellNum + 2));
				cell = row.createCell((short) (cellNum + 2));
				setCell(cell, tempCell.getCellStyle(),
						NumberFormat.getInstance().format(Float.parseFloat(caseDataList.get(i).getAvgNitrm())));
				// 寫平均年齡
				tempCell = tempSheet.getRow(rowNum).getCell((short) (cellNum + 3));
				cell = row.createCell((short) (cellNum + 3));
				setCell(cell, tempCell.getCellStyle(),
						NumberFormat.getInstance().format(Float.parseFloat(caseDataList.get(i).getAvgAge())));
			}
		}
	}

	/**
	 * 寫合計
	 */
	private void writeTotal(HSSFSheet sheet, HSSFRow row, int rowCnt, int cnt, short rowStart) {
		HSSFCell cell;
		short position = 1;
		for (int i = 0; i < cnt; i++) {
			long avgPayAmt = 0;
			float avgPWage = 0f;
			float avgNitrm = 0f;
			float avgAge = 0f;
			row = sheet.getRow(rowStart);
			for (int j = 0; j < rowCnt; j++) {
				avgPayAmt += (long) checkCellType(row.getCell(position));
				avgPWage += checkCellType(row.getCell((short) (position + 1)));
				avgNitrm += checkCellType(row.getCell((short) (position + 2)));
				avgAge += checkCellType(row.getCell((short) (position + 3)));
				// 游標往下計算下個年月的值
				row = incrementRow(sheet, row);
			}

			// 計算完之後寫合計
			// 平均單價
			tempCell = tempSheet.getRow(sheet.getLastRowNum()).getCell(position);
			cell = row.createCell(position);
			setCell(cell, tempCell.getCellStyle(), NumberFormat.getInstance().format(avgPayAmt));
			// 平均薪資
			tempCell = tempSheet.getRow(sheet.getLastRowNum()).getCell(position);
			cell = row.createCell((short) (position + 1));
			setCell(cell, tempCell.getCellStyle(), NumberFormat.getInstance().format(avgPWage));
			// 平均年資
			tempCell = tempSheet.getRow(sheet.getLastRowNum()).getCell(position);
			cell = row.createCell((short) (position + 2));
			setCell(cell, tempCell.getCellStyle(), NumberFormat.getInstance().format(avgNitrm));
			// 平均年齡
			tempCell = tempSheet.getRow(sheet.getLastRowNum()).getCell(position);
			cell = row.createCell((short) (position + 3));
			setCell(cell, tempCell.getCellStyle(), NumberFormat.getInstance().format(avgAge));
			position += 4;
		}
	}
	/**
	 * 確認資料的年月與表格上的年月，並按照分類寫入資料
	 */
	private void checkYMAndWriteData(List<AnnuityStatisticsCase> caseDataList, HSSFSheet sheet, HSSFRow row, short rowNum, short cellNum, short nextPosition, String eq, List<String> eq1) {
		String str = "";
		short paramCellNum = cellNum;
		for (int i = 0; i < caseDataList.size(); i++) {
			cellNum = paramCellNum;
			if(StringUtils.equals(eq, "sex"))
				str = caseDataList.get(i).getSex();
			if(StringUtils.equals(eq, "evType"))
				str = caseDataList.get(i).getEvType();
			if(StringUtils.equals(eq, "EvtNationTpe"))
				str = caseDataList.get(i).getEvtNationTpe();
			if(StringUtils.equals(eq, "cipbFMk"))
				str = StringUtils.equals(caseDataList.get(i).getCipbFMk(), null) || StringUtils.equals(caseDataList.get(i).getCipbFMk().trim(), "")? "" : caseDataList.get(i).getCipbFMk();
			if(StringUtils.equals(eq, "ubType"))
				str = caseDataList.get(i).getUbType();
			// caseData年月
			String nowIssuYm = DateUtility.changeChineseYearMonthType(caseDataList.get(i).getIssuYm());
			// 現在表格Row的年月
			String colIssuYm = "0";
			if (String.valueOf((int) row.getCell((short) 0).getNumericCellValue()).length() == 4) {
				colIssuYm += String.valueOf((int) row.getCell((short) 0).getNumericCellValue());
				colIssuYm = DateUtility.changeChineseYearMonthType(colIssuYm);
			} else {
				colIssuYm = DateUtility.changeChineseYearMonthType(String.valueOf((int) row.getCell((short) 0).getNumericCellValue()));
			}
			// 檢查此筆年月跟表格Row的年月一樣
			if (StringUtils.equals(nowIssuYm, colIssuYm)) {
				for(String s : eq1) {
					if (StringUtils.equals(str, s)) {
						writeData(row, caseDataList.get(i), rowNum, cellNum);
					} else {
						cellNum += nextPosition;
					}
				}
				// 如果此筆年月跟表格表格Row的年月不一樣，往下尋找
			} else {
				while (!StringUtils.equals(nowIssuYm, colIssuYm)) {
					row = incrementRow(sheet, row);
					colIssuYm = "0";
					if (String.valueOf((int) row.getCell((short) 0).getNumericCellValue()).length() == 4) {
						colIssuYm += String.valueOf((int) row.getCell((short) 0).getNumericCellValue());
						colIssuYm = DateUtility.changeChineseYearMonthType(colIssuYm);
					} else {
						colIssuYm = DateUtility.changeChineseYearMonthType(String.valueOf((int) row.getCell((short) 0).getNumericCellValue()));
					}
				}
				for(String s : eq1) {
					if (StringUtils.equals(str, s)) {
						writeData(row, caseDataList.get(i), rowNum, cellNum);
					} else {
						cellNum += nextPosition;
					}
				}
			}
		}
	}
	/**
	 * 寫平均單價、平均薪資、平均年資、平均年齡
	 */
	private void writeData(HSSFRow row, AnnuityStatisticsCase caseData, short rowNum, short cellNum) {
		HSSFCell cell;
		// 平均單價
		tempCell = tempSheet.getRow(rowNum).getCell(cellNum);
		cell = row.createCell(cellNum);
		setCell(cell, tempCell.getCellStyle(), NumberFormat.getInstance().format(Long.parseLong(caseData.getAvgPayAmt())));
		// 平均薪資
		tempCell = tempSheet.getRow(rowNum).getCell(cellNum);
		cell = row.createCell((short) (cellNum + 1));
		setCell(cell, tempCell.getCellStyle(), NumberFormat.getInstance().format(Float.parseFloat(caseData.getAvgPWage())));
		// 平均年資
		tempCell = tempSheet.getRow(rowNum).getCell(cellNum);
		cell = row.createCell((short) (cellNum + 2));
		setCell(cell, tempCell.getCellStyle(), NumberFormat.getInstance().format(Float.parseFloat(caseData.getAvgNitrm())));
		// 平均年齡
		tempCell = tempSheet.getRow(rowNum).getCell(cellNum);
		cell = row.createCell((short) (cellNum + 3));
		setCell(cell, tempCell.getCellStyle(), NumberFormat.getInstance().format(Float.parseFloat(caseData.getAvgAge())));
	}
	
	/**
	 * 寫年度全體
	 */
	private void writeYearTotal(HSSFSheet sheet, HSSFRow row, int cnt, int next, int paymentYM, short rowNum, short cellNum, short position) {
		HSSFCell cell;
		for (int i = 0; i < paymentYM; i++) {
			float avgPayAmt = 0;
			float avgPWage = 0f;
			float avgNitrm = 0f;
			float avgAge = 0f;
			short positionStart = position;
			for (int j = 0; j < cnt; j++) {
				avgPayAmt += checkCellType(row.getCell(positionStart));
				avgPWage += checkCellType(row.getCell((short) (positionStart + 1)));
				avgNitrm += checkCellType(row.getCell((short) (positionStart + 2)));
				avgAge += checkCellType(row.getCell((short) (positionStart + 3)));
				positionStart += next;
			}
			// 平均單價
			tempCell = tempSheet.getRow(rowNum).getCell(cellNum);
			cell = row.createCell(cellNum);
			setCell(cell, tempCell.getCellStyle(), NumberFormat.getInstance().format((long) (avgPayAmt)));
			// 平均薪資
			tempCell = tempSheet.getRow(rowNum).getCell(cellNum);
			cell = row.createCell((short) (cellNum + 1));
			setCell(cell, tempCell.getCellStyle(), NumberFormat.getInstance().format(avgPWage));
			// 平均年資
			tempCell = tempSheet.getRow(rowNum).getCell(cellNum);
			cell = row.createCell((short) (cellNum + 2));
			setCell(cell, tempCell.getCellStyle(), NumberFormat.getInstance().format(avgNitrm));
			// 平均年齡
			tempCell = tempSheet.getRow(rowNum).getCell(cellNum);
			cell = row.createCell((short) (cellNum + 3));
			setCell(cell, tempCell.getCellStyle(), NumberFormat.getInstance().format(avgAge));
			row = incrementRow(sheet, row);
		}
	}
	/**
	 * 寫年度全體
	 */
	private float checkCellType(HSSFCell cell) {
		float num = 0f;
		if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			num += (float) cell.getNumericCellValue();
		}else {
			String numTmp = cell.getRichStringCellValue().toString().replace(",", "");
			num += Float.parseFloat(numTmp);
		}
		return num;
	}

}
