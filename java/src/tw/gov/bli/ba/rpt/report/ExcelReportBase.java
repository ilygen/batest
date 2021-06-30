package tw.gov.bli.ba.rpt.report;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import tw.gov.bli.ba.framework.helper.EnvHelper;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.helper.HttpHelper;

public class ExcelReportBase {
    protected String resourcePath = "";
    protected String sheetName = "";
    protected BufferedInputStream bim;
    private String fileName = "";// 報表名稱
    private int[] columnType;// 欄位型態
    private int columnCount = 12;// 表格欄數
    private int headSize = 0; // 報表表頭row數
    private int bodySize = 30;// 每頁excel的內容row筆數
    private int footSize = 0; // 報表表底row數
    private int pageSize = 36; // 每頁excel row筆數

    private int currentPage = 0; // 目前頁數
    private int currentRow = 0; // 目前筆數(從0開始)
    private boolean isEject = false;// 是否分頁
    private boolean isPrintHead = false;// 是否每頁印表體表頭(true:每頁印;false:第一頁印)
    private boolean isPrintFoot = false;// 是否每頁印表體表底(true:每頁印:false:最後一頁印)

    protected HttpServletRequest request;
    protected HSSFWorkbook workbook;
    protected HSSFCellStyle cellStyle;
    protected HSSFFont font;
    protected ByteArrayOutputStream bao;

    public ExcelReportBase() {
        bao = new ByteArrayOutputStream();
    }

    public ExcelReportBase(String filePath) {
        InputStream in = null;
        try {
            // WAR 檔不解開，直接取得 Resource
            in = HttpHelper.getHttpRequest().getSession(true).getServletContext().getResourceAsStream("/xls/" + filePath);

            /*
             * File templet = new File(this.getXlsTempletPath(filePath)); FileInputStream fis = new FileInputStream(templet);
             */

            POIFSFileSystem pfs = new POIFSFileSystem(in);
            workbook = new HSSFWorkbook(pfs);
            bao = new ByteArrayOutputStream();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (IOException e) {
                }
            }
        }
    }

    public void init(HttpServletRequest request, HSSFWorkbook workbook) throws IOException {
        this.request = request;
        this.workbook = workbook;
        columnType = new int[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columnType[i] = HSSFCell.CELL_TYPE_STRING;
        }
    }

    /**
     * 設定Cell Style
     * 
     * @param workbook HSSFWorkbook
     */
    public void initCellStyle(HSSFWorkbook workbook) {
        cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        if (font != null) {
            cellStyle.setFont(font);
        }
    }

    /**
     * 設定字型
     * 
     * @param workbook HSSFWorkbook
     */
    public void initFont(HSSFWorkbook workbook) {
        font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        font.setFontHeightInPoints((short) 10);
        font.setFontName("新細明體");
    }

    /**
     * 報表表頭
     * 
     * @param request HttpServletRequest
     * @param sheet HSSFSheet
     */
    protected void printReportStart(HSSFSheet sheet) {
    }

    /**
     * 表體表頭
     * 
     * @param request HttpServletRequest
     * @param sheet HSSFSheet
     */
    protected void printHead(HSSFSheet sheet) {
        addRowCount(getHeadSize());
    }

    /**
     * 報表表體
     * 
     * @param request HttpServletRequest
     * @param sheet HSSFSheet
     */
    protected void printReportBody(HSSFSheet sheet) {
    };

    /**
     * 表體表底
     * 
     * @param request HttpServletRequest
     * @param sheet HSSFSheet
     */
    protected void printFoot(HSSFSheet sheet) {
        addRowCount(getFootSize());
    }

    /**
     * 報表表底
     * 
     * @param request HttpServletRequest
     * @param sheet HSSFSheet
     */
    protected void printReportEnd(HSSFSheet sheet) {
    }

    /**
     * 分頁
     * 
     * @param sheet HSSFSheet
     * @param currentRow int
     * @return int
     */
    public int eject(HSSFSheet sheet) {
        // 第一頁以後要加上foot size再判斷是否跳頁
        if ((currentRow + ((isPrintFoot && currentPage > 0) ? footSize : 0)) % (pageSize) == 0) {
            // 先印表底
            if (currentRow > 0 && isPrintFoot) {
                printFoot(sheet);
            }
            // 設定換頁
            if (currentPage > 0) {
                sheet.setRowBreak(currentRow - 1);
            }
            // 印表頭
            if (isPrintHead) {
                printHead(sheet);
            }
        }
        return currentRow;
    }

    /**
     * 判斷換頁
     * 
     * @param currentRow int
     * @return boolean
     */
    public boolean isEject(int currentRow) {
        if (currentRow % (pageSize) == 0) {
            return true;
        }
        return false;
    }

    public void setCell(HSSFCell cell, short encoding, int cellType, HSSFCellStyle style, String value) {
        // cell.setEncoding(encoding);
        cell.setCellType(cellType);
        cell.setCellStyle(style);
        cell.setCellValue(new HSSFRichTextString(StringUtils.defaultString(value)));
    }

    public void setCell(HSSFCell cell, short encoding, int cellType, HSSFCellStyle style, double value) {
        // cell.setEncoding(encoding);
        cell.setCellType(cellType);
        cell.setCellStyle(style);
        cell.setCellValue(value);
    }

    public void setCell(HSSFCell cell, HSSFCellStyle style, String value) {
        // cell.setEncoding(HSSFCell.ENCODING_UTF_16);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellStyle(style);
        cell.setCellValue(formatStrToHrtStr(value));
    }

    public void setCell(HSSFCell cell, HSSFCellStyle style, BigDecimal value) {
        // cell.setEncoding(HSSFCell.ENCODING_UTF_16);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellStyle(style);
        cell.setCellValue(formatBigDecimalToDouble(value));
    }

    public void setCell(HSSFCell cell, HSSFCellStyle style, double value) {
        // cell.setEncoding(HSSFCell.ENCODING_UTF_16);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellStyle(style);
        cell.setCellValue(value);
    }

    public void setCell(HSSFCell cell, short align, String value) {
        // cell.setEncoding(HSSFCell.ENCODING_UTF_16);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cellStyle.setAlignment(align);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(formatStrToHrtStr(StringUtils.defaultString(value)));
    }

    public void setCell(HSSFCell cell, short align, double value) {
        // cell.setEncoding(HSSFCell.ENCODING_UTF_16);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cellStyle.setAlignment(align);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);
    }

    public void setCell(HSSFCell cell, String value) {
        // cell.setEncoding(HSSFCell.ENCODING_UTF_16);
        cell.setCellValue(formatStrToHrtStr(StringUtils.defaultString(value)));
    }

    public void setCell(HSSFCell cell, double value) {
        // cell.setEncoding(HSSFCell.ENCODING_UTF_16);
        cell.setCellValue(value);
    }

    /**
     * 報表path
     * 
     * @param resourcePath String
     */
    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    /**
     * sheet名稱
     * 
     * @param sheetName String
     */
    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setCellStyle(HSSFCellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }

    public HSSFCellStyle getCellStyle() {
        return cellStyle;
    }

    public void setFont(HSSFFont font) {
        this.font = font;
    }

    public HSSFFont getFont() {
        return font;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setHeadSize(int headSize) {
        this.headSize = headSize;
    }

    public int getHeadSize() {
        return headSize;
    }

    public void setFootSize(int footSize) {
        this.footSize = footSize;
    }

    public int getFootSize() {
        return footSize;
    }

    public void setBodySize(int bodySize) {
        this.bodySize = bodySize;
    }

    public int getBodySize() {
        return bodySize;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void addRowCount(int count) {
        this.currentRow += count;
    }

    public void setIsEject(boolean isEject) {
        this.isEject = isEject;
    }

    public boolean getIsEject() {
        return isEject;
    }

    public void setIsPrintHead(boolean isPrintHead) {
        this.isPrintHead = isPrintHead;
    }

    public boolean getIsPrintHead() {
        return isPrintHead;
    }

    public void setIsPrintFoot(boolean isPrintFoot) {
        this.isPrintFoot = isPrintFoot;
    }

    public boolean getIsPrintFoot() {
        return isPrintFoot;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setColumnType(int[] columnType) {
        this.columnType = columnType;
    }

    public int[] getColumnType() {
        return columnType;
    }

    public HSSFWorkbook getWorkbook() {
        return workbook;
    }

    /**
     * 複製行
     * 
     * @param sourceSheetIndex
     * @param targetSheetIndex
     * @param sourceStartRow
     * @param sourceEndRow
     * @param targetPosition
     */

    public void copyRows(int sourceSheetIndex, int targetSheetIndex, int sourceStartRow, int sourceEndRow, int targetPosition) {
        copyRows(workbook.getSheetName(sourceSheetIndex), workbook.getSheetName(targetSheetIndex), sourceStartRow, sourceEndRow, targetPosition);
    }

    /**
     * 複製行
     * 
     * @param sourceSheetName
     * @param targetSheetName
     * @param sourceStartRow
     * @param sourceEndRow
     * @param targetPosition
     */
    public void copyRows(String sourceSheetName, String targetSheetName, int sourceStartRow, int sourceEndRow, int targetPosition) {
        HSSFRow sourceRow = null;
        HSSFRow targetRow = null;
        HSSFCell sourceCell = null;
        HSSFCell targetCell = null;
        HSSFSheet sourceSheet = null;
        HSSFSheet targetSheet = null;
        Region region = null;
        int cType;
        int i;
        short j;
        int targetRowFrom;
        int targetRowTo;

        if ((sourceStartRow == -1) || (sourceEndRow == -1)) {
            return;
        }
        sourceSheet = workbook.getSheet(sourceSheetName);
        targetSheet = workbook.getSheet(targetSheetName);
        // 拷貝合並的單元格
        for (i = 0; i < sourceSheet.getNumMergedRegions(); i++) {
            region = sourceSheet.getMergedRegionAt(i);
            if ((region.getRowFrom() >= sourceStartRow) && (region.getRowTo() <= sourceEndRow)) {
                targetRowFrom = region.getRowFrom() - sourceStartRow + targetPosition;
                targetRowTo = region.getRowTo() - sourceStartRow + targetPosition;
                region.setRowFrom(targetRowFrom);
                region.setRowTo(targetRowTo);
                targetSheet.addMergedRegion(region);
            }
        }
        // 設置列寬
        for (i = sourceStartRow; i <= sourceEndRow; i++) {
            sourceRow = sourceSheet.getRow(i);
            if (sourceRow != null) {
                for (j = 0; j < columnCount; j++) {
                    if (sourceSheet.getColumnWidth(j) <= 8) {
                        targetSheet.setColumnWidth(j, (short) 2500);
                    }
                    else {
                        targetSheet.setColumnWidth(j, sourceSheet.getColumnWidth(j));
                    }
                }
                break;
            }
        }
        // 拷貝行並填充數据
        for (; i <= sourceEndRow; i++) {
            sourceRow = sourceSheet.getRow(i);
            if (sourceRow == null) {
                continue;
            }
            targetRow = targetSheet.createRow(i - sourceStartRow + targetPosition);
            targetRow.setHeight(sourceRow.getHeight());
            for (j = 0; j < columnCount; j++) {
                sourceCell = sourceRow.getCell(j);
                if (sourceCell == null) {
                    continue;
                }
                targetCell = targetRow.createCell(j);
                // targetCell.setEncoding(sourceCell.getEncoding());
                targetCell.setCellStyle(sourceCell.getCellStyle());
                cType = sourceCell.getCellType();
                targetCell.setCellType(cType);
                switch (cType) {
                    case HSSFCell.CELL_TYPE_BOOLEAN:
                        targetCell.setCellValue(sourceCell.getBooleanCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_ERROR:
                        targetCell.setCellErrorValue(sourceCell.getErrorCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_FORMULA:
                        targetCell.setCellFormula(parseFormula(sourceCell.getCellFormula()));
                        break;
                    case HSSFCell.CELL_TYPE_NUMERIC:
                        targetCell.setCellValue(sourceCell.getNumericCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_STRING:
                        targetCell.setCellValue(sourceCell.getRichStringCellValue());
                        break;
                }
            }
        }
    }

    /**
     * 複製行
     * 
     * @param sourceSheetName
     * @param targetSheetName
     * @param sourceStartRow
     * @param sourceEndRow
     * @param targetPosition
     * @param columnCount
     */
    public void copyRows(String sourceSheetName, String targetSheetName, int sourceStartRow, int sourceEndRow, int targetPosition, int columnCount) {
        HSSFRow sourceRow = null;
        HSSFRow targetRow = null;
        HSSFCell sourceCell = null;
        HSSFCell targetCell = null;
        HSSFSheet sourceSheet = null;
        HSSFSheet targetSheet = null;
        Region region = null;
        int cType;
        int i;
        short j;
        int targetRowFrom;
        int targetRowTo;

        if ((sourceStartRow == -1) || (sourceEndRow == -1)) {
            return;
        }
        sourceSheet = workbook.getSheet(sourceSheetName);
        targetSheet = workbook.getSheet(targetSheetName);
        // 拷貝合並的單元格
        for (i = 0; i < sourceSheet.getNumMergedRegions(); i++) {
            region = sourceSheet.getMergedRegionAt(i);
            if ((region.getRowFrom() >= sourceStartRow) && (region.getRowTo() <= sourceEndRow)) {
                targetRowFrom = region.getRowFrom() - sourceStartRow + targetPosition;
                targetRowTo = region.getRowTo() - sourceStartRow + targetPosition;
                region.setRowFrom(targetRowFrom);
                region.setRowTo(targetRowTo);
                targetSheet.addMergedRegion(region);
            }
        }
        // 設置列寬
        for (i = sourceStartRow; i <= sourceEndRow; i++) {
            sourceRow = sourceSheet.getRow(i);
            if (sourceRow != null) {
                for (j = 0; j < columnCount; j++) {
                    if (sourceSheet.getColumnWidth(j) <= 8) {
                        targetSheet.setColumnWidth(j, (short) 2500);
                    }
                    else {
                        targetSheet.setColumnWidth(j, sourceSheet.getColumnWidth(j));
                    }
                }
                break;
            }
        }
        // 拷貝行並填充數据
        for (; i <= sourceEndRow; i++) {
            sourceRow = sourceSheet.getRow(i);
            if (sourceRow == null) {
                continue;
            }
            targetRow = targetSheet.createRow(i - sourceStartRow + targetPosition);
            targetRow.setHeight(sourceRow.getHeight());
            for (j = 0; j < columnCount; j++) {
                sourceCell = sourceRow.getCell(j);
                if (sourceCell == null) {
                    continue;
                }
                targetCell = targetRow.createCell(j);
                // targetCell.setEncoding(sourceCell.getEncoding());
                targetCell.setCellStyle(sourceCell.getCellStyle());
                cType = sourceCell.getCellType();
                targetCell.setCellType(cType);
                switch (cType) {
                    case HSSFCell.CELL_TYPE_BOOLEAN:
                        targetCell.setCellValue(sourceCell.getBooleanCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_ERROR:
                        targetCell.setCellErrorValue(sourceCell.getErrorCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_FORMULA:
                        // parseFormula這個函數的用途在后面說明
                        targetCell.setCellFormula(parseFormula(sourceCell.getCellFormula()));
                        break;
                    case HSSFCell.CELL_TYPE_NUMERIC:
                        targetCell.setCellValue(sourceCell.getNumericCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_STRING:
                        targetCell.setCellValue(sourceCell.getRichStringCellValue());
                        break;
                }
            }
        }
    }

    /**
     * 公式的問題。POI對Excel公式的支持是相當好的，<br>
     * 但是我發現一個問題，如果公式里面的函數不帶參數， <br>
     * 比如now()或today()，那麼你通過getCellFormula()取出來的值就是now(ATTR(semiVolatile)) <br>
     * 和today(ATTR(semiVolatile))，這樣的值寫入Excel是會出錯的， <br>
     * 這也是上面copyRow的函數在寫入公式前要調用parseFormula的原因， <br>
     * parseFormula這個函數的功能很簡單，就是把ATTR(semiVolatile)刪掉
     * 
     * @param pPOIFormula String
     * @return String
     */
    private String parseFormula(String pPOIFormula) {
        final String cstReplaceString = "ATTR(semiVolatile)"; //$NON-NLS-1$
        StringBuffer result = null;
        int index;

        result = new StringBuffer();
        index = pPOIFormula.indexOf(cstReplaceString);
        if (index >= 0) {
            result.append(pPOIFormula.substring(0, index));
            result.append(pPOIFormula.substring(index + cstReplaceString.length()));
        }
        else {
            result.append(pPOIFormula);
        }

        return result.toString();
    }

    /**
     * 取得 xls 目錄中指定檔名之實際路徑
     * 
     * @param fileName
     * @return
     */
    public String getXlsTempletPath(String fileName) {
        String resourcePath = EnvHelper.xlsTemplatePath;
        if (!StringUtils.endsWithIgnoreCase(resourcePath, System.getProperty("file.separator")))
            resourcePath = resourcePath + System.getProperty("file.separator");
        resourcePath = resourcePath + fileName;
        return resourcePath;
    }

    /**
     * 將String 轉成 HSSFRichTextString
     * 
     * @param str
     * @return
     */
    public HSSFRichTextString formatStrToHrtStr(String str) {
        HSSFRichTextString returnStr = new HSSFRichTextString("");

        if (StringUtils.isNotBlank(str)) {
            returnStr = new HSSFRichTextString(str);
        }

        return returnStr;
    }

    /**
     * 將int 轉成 HSSFRichTextString
     * 
     * @param str
     * @return
     */
    public HSSFRichTextString formatIntToHrtStr(int num) {
        HSSFRichTextString returnStr = new HSSFRichTextString(String.valueOf(num));

        return returnStr;
    }

    /**
     * 將BigDecimal 轉成 HSSFRichTextString
     * 
     * @param num
     * @return
     */
    public HSSFRichTextString formatBigDecimalToHrtStr(BigDecimal num) {
        HSSFRichTextString returnStr = new HSSFRichTextString("");

        if (num != null) {
            returnStr = new HSSFRichTextString(num.toPlainString());
        }

        return returnStr;
    }

    /**
     * 將BigDecimal 轉成 double
     * 
     * @param num
     * @return
     */
    public double formatBigDecimalToDouble(BigDecimal num) {
        double returnValue = 0;

        if (num != null) {
            returnValue = num.doubleValue();
        }

        return returnValue;
    }

    /**
     * 新增 row
     * 
     * @param sheet
     * @param r
     * @return
     */
    public HSSFRow incrementRow(HSSFSheet sheet, HSSFRow r) {
        int rowNum = r.getRowNum();
        rowNum++;
        r = sheet.getRow(rowNum);
        if (r == null) {
            r = sheet.createRow(rowNum);
        }
        return r;
    }

    /**
     * 去掉日期(年月日)年份前的0
     * 
     * @param sheet
     * @param r
     * @return
     */
    public String delFrontZeroForDateValue(String date) {
        String dateValue = "";
        if (StringUtils.isNotBlank(date)) {
            if (date.length() == 7) {
                dateValue = (Integer.valueOf(date.substring(0, 3))).toString() + date.substring(3, 7);
            }
            if (date.length() == 8) {
                date = DateUtility.changeDateType(date);
                dateValue = (Integer.valueOf(date.substring(0, 3))).toString() + date.substring(3, 7);
            }
        }
        return dateValue;
    }

    /**
     * 去掉日期(年月)年份前的0
     * 
     * @param sheet
     * @param r
     * @return
     */
    public String delFrontZeroForDateYmValue(String dateYm) {
        String dateValue = "";
        if (StringUtils.isNotBlank(dateYm)) {
            if (dateYm.length() == 5) {
                dateValue = (Integer.valueOf(dateYm.substring(0, 3))).toString() + dateYm.substring(3, 5);
            }
            if (dateYm.length() == 6) {
                dateYm = DateUtility.changeWestYearMonthType(dateYm);
                dateValue = (Integer.valueOf(dateYm.substring(0, 3))).toString() + dateYm.substring(3, 5);
            }
        }
        return dateValue;
    }
    
    /**
     * 複製行
     * 
     * @param sourceSheetName
     * @param targetSheetName
     * @param sourceStartRow
     * @param sourceEndRow
     * @param targetPosition
     * @param columnCount
     */
    public void copyRowsWithC(String sourceSheetName, String targetSheetName, int sourceStartRow, int sourceEndRow, int targetPosition, int sourceStartColumn, int targetStartColumn, int columnCount) {
        HSSFRow sourceRow = null;
        HSSFRow targetRow = null;
        HSSFCell sourceCell = null;
        HSSFCell targetCell = null;
        HSSFSheet sourceSheet = null;
        HSSFSheet targetSheet = null;
        Region region = null;
        int cType;
        int i;
        short j;
        int targetRowFrom;
        int targetRowTo;

        if ((sourceStartRow == -1) || (sourceEndRow == -1)) {
            return;
        }
        sourceSheet = workbook.getSheet(sourceSheetName);
        targetSheet = workbook.getSheet(targetSheetName);
        // 拷貝合並的單元格
        for (i = 0; i < sourceSheet.getNumMergedRegions(); i++) {
            region = sourceSheet.getMergedRegionAt(i);
            if ((region.getRowFrom() >= sourceStartRow) && (region.getRowTo() <= sourceEndRow)) {
                targetRowFrom = region.getRowFrom() - sourceStartRow + targetPosition;
                targetRowTo = region.getRowTo() - sourceStartRow + targetPosition;
                region.setRowFrom(targetRowFrom);
                region.setRowTo(targetRowTo);
                targetSheet.addMergedRegion(region);
            }
        }
        int sourceStartColumnTmp = sourceStartColumn;
        // 設置列寬
        for (i = sourceStartRow; i <= sourceEndRow; i++) {
        	
            sourceRow = sourceSheet.getRow(i);
            if (sourceRow != null) {
                for (j = (short) targetStartColumn; j < columnCount; j++) {
                    if (sourceSheet.getColumnWidth((short)sourceStartColumnTmp) <= 8) {
                        targetSheet.setColumnWidth(j, (short) 2500);
                    }
                    else {
                        targetSheet.setColumnWidth(j, sourceSheet.getColumnWidth((short)sourceStartColumnTmp));
                    }
                    sourceStartColumnTmp = sourceStartColumnTmp + 1;
                }
                break;
            }
            
        }
        // 拷貝行並填充數据
        for (; i <= sourceEndRow; i++) {
            sourceRow = sourceSheet.getRow(i);
            if (sourceRow == null) {
                continue;
            }
            targetRow = targetSheet.createRow(i - sourceStartRow + targetPosition);
            targetRow.setHeight(sourceRow.getHeight());
            int sourceStartColumnTmp1 = sourceStartColumn;
            for (j = (short) targetStartColumn; j < columnCount; j++) {
                sourceCell = sourceRow.getCell((short)sourceStartColumnTmp1);
                if (sourceCell == null) {
                    continue;
                }
                targetCell = targetRow.createCell(j);
                // targetCell.setEncoding(sourceCell.getEncoding());
                targetCell.setCellStyle(sourceCell.getCellStyle());
                cType = sourceCell.getCellType();
                targetCell.setCellType(cType);
                switch (cType) {
                    case HSSFCell.CELL_TYPE_BOOLEAN:
                        targetCell.setCellValue(sourceCell.getBooleanCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_ERROR:
                        targetCell.setCellErrorValue(sourceCell.getErrorCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_FORMULA:
                        // parseFormula這個函數的用途在后面說明
                        targetCell.setCellFormula(parseFormula(sourceCell.getCellFormula()));
                        break;
                    case HSSFCell.CELL_TYPE_NUMERIC:
                        targetCell.setCellValue(sourceCell.getNumericCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_STRING:
                        targetCell.setCellValue(sourceCell.getRichStringCellValue());
                        break;
                }
                sourceStartColumnTmp1 = sourceStartColumnTmp1 + 1;
            }
        }
    }

    /**
     * 將 BigDecimal 的值轉成字串
     * 
     * @param nNumber
     * @return
     */
    public static String getBigDecimalValue(BigDecimal nNumber) {
        if (nNumber != null)
            return nNumber.toPlainString();
        else
            return "";
    }
}
