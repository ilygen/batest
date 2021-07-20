package tw.gov.bli.ba.query.report;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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

public class AnnuityStatisticsExcelReport04 extends ExcelReportBase {

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

	public AnnuityStatisticsExcelReport04(String excelTyp) {
		super(excelTyp);
	}

	private void printHead(String payCodeStr, String qryType) {
		String str = StringUtils.equals(qryType, "G") ? "年齡級距" : "投保薪資級距";
		tempSheet = workbook.getSheetAt(0);
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFRow row0 = sheet.getRow(0);
		setCell(row0.getCell((short) 0), payCodeStr);
		HSSFRow row1 = sheet.getRow(1);
		setCell(row1.getCell((short) 0), "(4)" + str + "：");
		HSSFRow row3 = sheet.getRow(3);
		setCell(row3.getCell((short) 0), str);
	}
	public ByteArrayOutputStream doReport(List<AnnuityStatisticsCase> caseDataList, String payKind, AnnuityStatisticsForm queryForm) throws Exception {
		tempSheet = workbook.getSheetAt(0);
		HSSFSheet sheet = workbook.getSheetAt(0);
		printHead(getPayKindStr(payKind), queryForm.getQrytype());
		if(StringUtils.equals(queryForm.getAnalysisSelect(), "X")) {
			HSSFRow row = sheet.getRow(4);
			int cnt = writeGroupAlias(sheet, row, caseDataList, (short) 4, null);
			checkYMAndWriteDataForXGH(caseDataList, sheet, row, (short) 4, (short) 1);
			writeTotal(sheet, row, cnt, 2, (short) 4);
			row = sheet.getRow(4);
			writePercent(sheet, row, cnt + 1, 1);
		}
		workbook.getSheetAt(0).setSelected(true);
		workbook.write(bao);
		return bao;
	}
	public ByteArrayOutputStream doReport(List<AnnuityStatisticsCase> yearTotalCaseDataList, List<AnnuityStatisticsCase> caseDataList, String payKind, AnnuityStatisticsForm queryForm) throws Exception {

		tempSheet = workbook.getSheetAt(0);
		HSSFSheet sheet = workbook.getSheetAt(0);
		printHead(getPayKindStr(payKind), queryForm.getQrytype());
		if(StringUtils.equals(queryForm.getAnalysisSelect(), "S")) {
				HSSFRow row = sheet.getRow(5);
				int groupAliasCnt = writeGroupAlias(sheet, row, caseDataList, (short) 5, null);
				List<String> eq = Arrays.asList("1","2");
				checkGroupAliasAndWriteData(caseDataList, sheet, row, (short) 5, (short) 3, (short) 2, "sex", eq);
//				writeYearTotal(sheet, row, 2, 2, groupAliasCnt, (short) 5, (short) 1, (short) 3);
				row = sheet.getRow(5);
				checkYMAndWriteDataForXGH(yearTotalCaseDataList, sheet, row, (short) 5, (short) 1);
				writeTotal(sheet, row, groupAliasCnt, 6, (short) 5);
				row = sheet.getRow(5);
				writePercent(sheet, row, groupAliasCnt + 1, eq.size() + 1);
			}else if(StringUtils.equals(queryForm.getAnalysisSelect(), "E")) {
				HSSFRow row = sheet.getRow(5);
				int groupAliasCnt = writeGroupAlias(sheet, row, caseDataList, (short) 5, null);
				List<String> eq = Arrays.asList("1","3");
				checkGroupAliasAndWriteData(caseDataList, sheet, row, (short) 5, (short) 3, (short) 2, "evType", eq);
//				writeYearTotal(sheet, row, 2, 2, groupAliasCnt, (short) 5, (short) 1, (short) 3);
				row = sheet.getRow(5);
				checkYMAndWriteDataForXGH(yearTotalCaseDataList, sheet, row, (short) 5, (short) 1);
				writeTotal(sheet, row, groupAliasCnt, 6, (short) 5);
				row = sheet.getRow(5);
				writePercent(sheet, row, groupAliasCnt + 1, eq.size() + 1);
			}else if(StringUtils.equals(queryForm.getAnalysisSelect(), "N")) {
				HSSFRow row = sheet.getRow(5);
				int groupAliasCnt = writeGroupAlias(sheet, row, caseDataList, (short) 5, null);
				List<String> eq = Arrays.asList("1","2");
				checkGroupAliasAndWriteData(caseDataList, sheet, row, (short) 5, (short) 3, (short) 2, "EvtNationTpe", eq);
//				writeYearTotal(sheet, row, 2, 2, groupAliasCnt, (short) 5, (short) 1, (short) 3);
				row = sheet.getRow(5);
				checkYMAndWriteDataForXGH(yearTotalCaseDataList, sheet, row, (short) 5, (short) 1);
				writeTotal(sheet, row, groupAliasCnt, 6, (short) 5);
				row = sheet.getRow(5);
				writePercent(sheet, row, groupAliasCnt + 1, eq.size() + 1);
			}else if(StringUtils.equals(queryForm.getAnalysisSelect(), "C")) {
				HSSFRow row = sheet.getRow(5);
				int groupAliasCnt = writeGroupAlias(sheet, row, caseDataList, (short) 5, null);
				List<String> eq = Arrays.asList("","F","Y","N","1","2","3","4","5");
				checkGroupAliasAndWriteData(caseDataList, sheet, row, (short) 5, (short) 3, (short) 2, "cipbFMk", eq);
//				writeYearTotal(sheet, row, eq.size(), 2, groupAliasCnt, (short) 5, (short) 1, (short) 3);
				row = sheet.getRow(5);
				checkYMAndWriteDataForXGH(yearTotalCaseDataList, sheet, row, (short) 5, (short) 1);
				writeTotal(sheet, row, groupAliasCnt, eq.size() * 2 + 2, (short) 5);
				row = sheet.getRow(5);
				writePercent(sheet, row, groupAliasCnt + 1, eq.size() + 1);
			}
		workbook.getSheetAt(0).setSelected(true);
		workbook.write(bao);
		return bao;
	}

	public ByteArrayOutputStream doReport(List<AnnuityStatisticsCase> yearTotalCaseDataList, List<AnnuityStatisticsCase> caseDataList, List<Caub> caubList,String payKind, AnnuityStatisticsForm queryForm) throws Exception {
		tempSheet = workbook.getSheetAt(0);
		HSSFSheet sheet = workbook.getSheetAt(0);
		printHead(getPayKindStr(payKind), queryForm.getQrytype());
		
		short ubTypeCol = 3;
		HSSFCell cell;

		// 寫ubType
		for (Caub c : caubList) {
			HSSFRow row = sheet.getRow(3);
			// 第一個儲存格寫ubType
			tempCell = tempSheet.getRow(3).getCell((short) 3);
			cell = row.createCell(ubTypeCol);
			setCell(cell, tempCell.getCellStyle(), c.getUbType());
			sheet.setColumnWidth(ubTypeCol, sheet.getColumnWidth((short) 3));
			// 第二個儲存格複製格式
			tempCell = tempSheet.getRow(3).getCell((short) 4);
			cell = row.createCell((short) (ubTypeCol + 1));
			cell.setCellStyle(tempCell.getCellStyle());
			sheet.setColumnWidth((short) (ubTypeCol + 1), sheet.getColumnWidth((short) 3));
			// 合併上面兩個儲存格
			Region region = new Region();
			region.setRowFrom(3);
			region.setRowTo(3);
			region.setColumnFrom(ubTypeCol);
			region.setColumnTo((short) (ubTypeCol + 1));
			sheet.addMergedRegion(region);
			// 寫件數格子
			row = incrementRow(sheet, row);
			tempCell = tempSheet.getRow(4).getCell((short) 3);
			cell = row.createCell(ubTypeCol);
			setCell(cell, tempCell.getCellStyle(), "件數");
			// 寫比例格子
			tempCell = tempSheet.getRow(4).getCell((short) 4);
			cell = row.createCell((short) (ubTypeCol + 1));
			setCell(cell, tempCell.getCellStyle(), "比例");
			// 複製件數下面格子
			row = incrementRow(sheet, row);
			tempCell = tempSheet.getRow(5).getCell((short) 3);
			cell = row.createCell(ubTypeCol);
			cell.setCellStyle(tempCell.getCellStyle());
			// 複製比例下面格子
			tempCell = tempSheet.getRow(5).getCell((short) 4);
			cell = row.createCell((short) (ubTypeCol + 1));
			cell.setCellStyle(tempCell.getCellStyle());
			// 複製件數合計欄
			row = incrementRow(sheet, row);
			tempCell = tempSheet.getRow(6).getCell((short) 3);
			cell = row.createCell(ubTypeCol);
			cell.setCellStyle(tempCell.getCellStyle());
			// 複製比例合計欄
			tempCell = tempSheet.getRow(6).getCell((short) 4);
			cell = row.createCell((short) (ubTypeCol + 1));
			cell.setCellStyle(tempCell.getCellStyle());
			// 往右移兩格
			ubTypeCol += 2;
		}
		
		HSSFRow row = sheet.getRow(5);
		int groupAliasCnt = writeGroupAlias(sheet, row, caseDataList, (short) 5, "ubType");
		List<String> eq = new ArrayList<String>();
		for(Caub c : caubList)
			eq.add(c.getUbType());
		checkGroupAliasAndWriteData(caseDataList, sheet, row, (short) 5, (short) 3, (short) 2, "ubType", eq);
//		writeYearTotal(sheet, row, eq.size(), 2, groupAliasCnt, (short) 5, (short) 1, (short) 3);
		row = sheet.getRow(5);
		checkYMAndWriteDataForXGH(yearTotalCaseDataList, sheet, row, (short) 5, (short) 1);
		writeTotal(sheet, row, groupAliasCnt, eq.size() * 2 + 2, (short) 5);
		row = sheet.getRow(5);
		writePercent(sheet, row, groupAliasCnt + 1, eq.size() + 1);
		
		workbook.getSheetAt(0).setSelected(true);
		workbook.write(bao);
		return bao;
	}

	/**
	 * 寫級距，並把所有儲存格都預設為0
	 */
	private int writeGroupAlias(HSSFSheet sheet, HSSFRow row, List<AnnuityStatisticsCase> caseDataList, short rowNum,String eq) {
		HSSFCell cell;
		int cnt = 0;
		String str = "";
		List<String> strList = new LinkedList<String>();
		// 計算有幾個不同的級距
		for(AnnuityStatisticsCase c : caseDataList) {
			if(!StringUtils.equals(c.getGroupAlias(), str)) {
				strList.add(c.getGroupAlias());
				cnt++;
			}
			str = c.getGroupAlias();
		}
		for(int i = 0;i < cnt ;i++) {
			// 寫級距
			tempCell = tempSheet.getRow(rowNum).getCell((short) 0);
			cell = row.createCell((short) 0); 
			setCell(cell, tempCell.getCellStyle(), strList.get(i));
			int lastCellNum = StringUtils.equals(eq, "ubType") ? sheet.getRow(5).getLastCellNum() : sheet.getRow(5).getLastCellNum() - 1;
			// 把件數跟比例都預設為 0
			for (int j = 0; j < lastCellNum; j++) {
				int cellNum = j + 1;
				tempCell = tempSheet.getRow(rowNum).getCell((short) 1);
				cell = row.createCell((short) (cellNum));
				setCell(cell, tempCell.getCellStyle(), 0);
			}
			// 如果還有下一欄，把合計欄往下移動一欄，並把游標往下一欄移動
			if (i + 1 < cnt) {
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
		return cnt;
	}

	/**
	 * 確認資料的年月與表格上的年月，並寫資料 For XA
	 */
	private void checkYMAndWriteDataForXGH(List<AnnuityStatisticsCase> caseDataList, HSSFSheet sheet, HSSFRow row, short rowNum, short cellNum) {
		HSSFCell cell;
		for (int i = 0; i < caseDataList.size(); i++) {
			// caseData 級距
			String nowGroupAlias = caseDataList.get(i).getGroupAlias();
			// 現在表格Row的級距
			String colGroupAlias = row.getCell((short) 0).getRichStringCellValue().toString();
			// 檢查此筆年月跟表格Row的年月一樣
			if (StringUtils.equals(nowGroupAlias, colGroupAlias)) {
				// 寫件數
				tempCell = tempSheet.getRow(rowNum).getCell(cellNum);
				cell = row.createCell(cellNum);
				setCell(cell, tempCell.getCellStyle(),
						NumberFormat.getInstance().format(Long.parseLong(caseDataList.get(i).getPayCnt())));
				// 寫比例
				tempCell = tempSheet.getRow(rowNum).getCell((short) (cellNum + 1));
				cell = row.createCell((short) (cellNum + 1));
				setCell(cell, tempCell.getCellStyle(),
						NumberFormat.getInstance().format(Float.parseFloat(caseDataList.get(i).getCntRatio())));
				// 如果此筆年月跟表格表格Row的年月不一樣，往下尋找
			} else {
				while (!StringUtils.equals(nowGroupAlias, colGroupAlias)) {
					row = incrementRow(sheet, row);
					colGroupAlias = row.getCell((short) 0).getRichStringCellValue().toString();
				}
				// 寫件數
				tempCell = tempSheet.getRow(rowNum).getCell(cellNum);
				cell = row.createCell(cellNum);
				setCell(cell, tempCell.getCellStyle(),
						NumberFormat.getInstance().format(Long.parseLong(caseDataList.get(i).getPayCnt())));
				// 寫比例
				tempCell = tempSheet.getRow(rowNum).getCell((short) (cellNum + 1));
				cell = row.createCell((short) (cellNum + 1));
				setCell(cell, tempCell.getCellStyle(),
						NumberFormat.getInstance().format(Float.parseFloat(caseDataList.get(i).getCntRatio())));
			}
		}
	}

	/**
	 * 寫合計
	 */
	private void writeTotal(HSSFSheet sheet, HSSFRow row, int rowCnt, int cellCnt, short rowStart) {
		HSSFCell cell;
		short position = 1;
		for (int i = 0; i < cellCnt; i++) {
			long sumInt = 0;
			float sumFloat = 0f;
			if(position % 2 == 0) {
				row = sheet.getRow(rowStart);
				for (int j = 0; j < rowCnt; j++) {
					if (row.getCell(position).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
						sumFloat += (float) row.getCell(position).getNumericCellValue();
					} else if (row.getCell(position).getCellType() == HSSFCell.CELL_TYPE_STRING) {
						String tmpSum = row.getCell(position).getRichStringCellValue().toString().replace(",", "");
						sumFloat += Float.parseFloat(tmpSum);
					}
					// 游標往下計算下個年月的值
					row = incrementRow(sheet, row);
				}
			}else {
				row = sheet.getRow(rowStart);
				for (int j = 0; j < rowCnt; j++) {
					if (row.getCell(position).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
						sumInt += (long) row.getCell(position).getNumericCellValue();
					} else if (row.getCell(position).getCellType() == HSSFCell.CELL_TYPE_STRING) {
						String tmpSum = row.getCell(position).getRichStringCellValue().toString().replace(",", "");
						sumInt += Long.parseLong(tmpSum);
					}
					// 游標往下計算下個年月的值
					row = incrementRow(sheet, row);
				}
			}
			// 計算完之後寫合計
			if(position % 2 == 0) {
				tempCell = tempSheet.getRow(sheet.getLastRowNum()).getCell(position);
				cell = row.createCell(position);
				setCell(cell, tempCell.getCellStyle(), NumberFormat.getInstance().format(sumFloat));
			}else {
				tempCell = tempSheet.getRow(sheet.getLastRowNum()).getCell(position);
				cell = row.createCell(position);
				setCell(cell, tempCell.getCellStyle(), NumberFormat.getInstance().format(sumInt));
			}
			position++;
		}
	}
	
	/**
	 * 確認資料的級距與表格上的級距，並按照分類寫入資料
	 */
	private void checkGroupAliasAndWriteData(List<AnnuityStatisticsCase> caseDataList, HSSFSheet sheet, HSSFRow row, short rowNum, short cellNum, short nextPosition, String eq, List<String> eq1) {
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
			// caseData 級距
			String nowGroupAlias = caseDataList.get(i).getGroupAlias();
			// 現在表格Row的級距
			String colGroupAlias = row.getCell((short) 0).getRichStringCellValue().toString();
			// 檢查此筆級距跟表格Row的級距一樣
			if (StringUtils.equals(nowGroupAlias, colGroupAlias)) {
				for(String s : eq1) {
					if (StringUtils.equals(str, s)) {
						writeData(row, caseDataList.get(i), rowNum, cellNum);
					} else {
						cellNum += nextPosition;
					}
				}
				// 如果此筆級距跟表格Row的級距不一樣，往下尋找
			} else {
				while (!StringUtils.equals(nowGroupAlias, colGroupAlias)) {
					row = incrementRow(sheet, row);
					colGroupAlias = row.getCell((short) 0).getRichStringCellValue().toString();
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
	 * 寫件數、比例
	 */
	private void writeData(HSSFRow row, AnnuityStatisticsCase caseData, short rowNum, short cellNum) {
		HSSFCell cell;
		// 寫件數
		tempCell = tempSheet.getRow(rowNum).getCell(cellNum);
		cell = row.createCell(cellNum);
		setCell(cell, tempCell.getCellStyle(),
				NumberFormat.getInstance().format(Long.parseLong(caseData.getPayCnt())));
		// 寫比例
		tempCell = tempSheet.getRow(rowNum).getCell((short) (cellNum + 1));
		cell = row.createCell((short) (cellNum + 1));
		setCell(cell, tempCell.getCellStyle(),
				NumberFormat.getInstance().format(Float.parseFloat(caseData.getCntRatio())));
	}
	
	/**
	 * 寫年度全體
	 */
	private void writeYearTotal(HSSFSheet sheet, HSSFRow row, int cnt, int next, int groupAliasCnt, short rowNum, short cellNum, short position) {
		HSSFCell cell;
		for (int i = 0; i < groupAliasCnt; i++) {
			long sumInt = 0;
			float sumFloat = 0f;
			short positionStart = position;
			for (int j = 0; j < cnt; j++) {
				sumInt += checkCellType(row.getCell(positionStart));
				sumFloat += checkCellType(row.getCell((short) (positionStart + 1)));
				positionStart += next;
			}
			// 計算完之後寫全體
			tempCell = tempSheet.getRow(rowNum).getCell(cellNum);
			cell = row.createCell(cellNum);
			setCell(cell, tempCell.getCellStyle(), NumberFormat.getInstance().format(sumInt));
			tempCell = tempSheet.getRow(rowNum).getCell((short) (cellNum + 1));
			cell = row.createCell((short) (cellNum + 1));
			setCell(cell, tempCell.getCellStyle(), NumberFormat.getInstance().format(sumFloat));
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
	
	/**
	 * 比例加上%數
	 */
	private void writePercent(HSSFSheet sheet, HSSFRow row, int rowCnt, int cellCnt) {
		HSSFCell cell;
		for(int i = 0; i < rowCnt;i++) {
			short startPosition = 2;
			for(int j = 0;j < cellCnt;j++) {
				if(row.getCell(startPosition).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					String str = (float) row.getCell(startPosition).getNumericCellValue() + "%";
					str = StringUtils.equals(str, "0%") ? "0.0%" : str;
					tempCell = tempSheet.getRow(row.getRowNum()).getCell(startPosition);
					cell = row.createCell(startPosition);
					setCell(cell, tempCell.getCellStyle(), str);
				}else {
					String str = row.getCell(startPosition).getRichStringCellValue() + "%";
					str = StringUtils.equals(str, "0%") ? "0.0%" : str;
					tempCell = tempSheet.getRow(row.getRowNum()).getCell(startPosition);
					cell = row.createCell(startPosition);
					setCell(cell, tempCell.getCellStyle(), str);
				}
				startPosition += 2;
			}
			row = incrementRow(sheet, row);
		}
	}
}
