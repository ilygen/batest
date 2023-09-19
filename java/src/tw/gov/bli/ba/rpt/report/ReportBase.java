package tw.gov.bli.ba.rpt.report;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.Barcode39;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

import tw.gov.bli.ba.framework.helper.EnvHelper;
import tw.gov.bli.ba.util.ExceptionUtility;

public abstract class ReportBase {
    private static Log log = LogFactory.getLog(ReportBase.class);

    protected Document document;
    protected ByteArrayOutputStream bao;
    protected PdfWriter writer;

    private PdfContentByte contentByte = null;
    private PdfTemplate template = null;

    // 水平對齊方式常數
    public final int CENTER = Element.ALIGN_CENTER;
    public final int RIGHT = Element.ALIGN_RIGHT;
    public final int LEFT = Element.ALIGN_LEFT;
    public final int JUSTIFIED = Element.ALIGN_JUSTIFIED;
    public final int JUSTIFIEDALL = Element.ALIGN_JUSTIFIED_ALL;

    // 數值轉為中文大寫
    private double amount = 0.0D;
    private static String NUM = "零壹貳參肆伍陸柒捌玖";
    private static String UNIT = "仟佰拾個";
    private static String GRADEUNIT = "仟萬億兆";
    private static String DOTUNIT = "角分厘";
    private static int GRADE = 4;
    private static NumberFormat nf = new DecimalFormat("#0.###");

    // 垂直對齊方式常數
    public final int TOP = Element.ALIGN_TOP;
    public final int MIDDLE = Element.ALIGN_MIDDLE;
    public final int BOTTOM = Element.ALIGN_BOTTOM;
    public final int BASELINE = Element.ALIGN_BASELINE;

    // 字型路徑
    // static ResourceBundle resourceBondule = ResourceBundle.getBundle("properties.font");
    // static String sFontPath = resourceBondule.getString("path");

    // 一般字型 - 細明體
    public BaseFont bfChinese = null;
    public Font fontCh40 = null;
    public Font fontCh32 = null;
    public Font fontCh30 = null;
    public Font fontCh28 = null;
    public Font fontCh26 = null;
    public Font fontCh24 = null;
    public Font fontCh22 = null;
    public Font fontCh20 = null;
    public Font fontCh18 = null;
    public Font fontCh16 = null;
    public Font fontCh14 = null;
    public Font fontCh13 = null;
    public Font fontCh12 = null;
    public Font fontCh11 = null;
    public Font fontCh10 = null;
    public Font fontCh9 = null;
    public Font fontCh8 = null;
    public Font fontCh7 = null;
    public Font fontCh6 = null;
    public Font fontCh1 = null;

    // 粗體字型 - 細明體
    public Font fontCh40b = null;
    public Font fontCh32b = null;
    public Font fontCh30b = null;
    public Font fontCh28b = null;
    public Font fontCh26b = null;
    public Font fontCh24b = null;
    public Font fontCh22b = null;
    public Font fontCh20b = null;
    public Font fontCh18b = null;
    public Font fontCh16b = null;
    public Font fontCh14b = null;
    public Font fontCh13b = null;
    public Font fontCh12b = null;
    public Font fontCh11b = null;
    public Font fontCh10b = null;
    public Font fontCh9b = null;
    public Font fontCh8b = null;
    public Font fontCh7b = null;
    public Font fontCh6b = null;

    // 底線字型 - 細明體
    public Font fontCh12l = null;

    // 一般字型 - 標楷體
    public BaseFont bfKChinese = null;
    public Font fontKCh40 = null;
    public Font fontKCh32 = null;
    public Font fontKCh30 = null;
    public Font fontKCh28 = null;
    public Font fontKCh26 = null;
    public Font fontKCh24 = null;
    public Font fontKCh22 = null;
    public Font fontKCh20 = null;
    public Font fontKCh18 = null;
    public Font fontKCh16 = null;
    public Font fontKCh14 = null;
    public Font fontKCh13 = null;
    public Font fontKCh12 = null;
    public Font fontKCh11 = null;
    public Font fontKCh10 = null;
    public Font fontKCh9 = null;
    public Font fontKCh8 = null;
    public Font fontKCh7 = null;
    public Font fontKCh6 = null;
    public Font fontKCh1 = null;

    // 粗體字型 - 標楷體
    public Font fontKCh40b = null;
    public Font fontKCh32b = null;
    public Font fontKCh30b = null;
    public Font fontKCh28b = null;
    public Font fontKCh26b = null;
    public Font fontKCh24b = null;
    public Font fontKCh22b = null;
    public Font fontKCh20b = null;
    public Font fontKCh18b = null;
    public Font fontKCh16b = null;
    public Font fontKCh14b = null;
    public Font fontKCh13b = null;
    public Font fontKCh12b = null;
    public Font fontKCh11b = null;
    public Font fontKCh10b = null;
    public Font fontKCh9b = null;
    public Font fontKCh8b = null;
    public Font fontKCh7b = null;
    public Font fontKCh6b = null;

    // 底線字型 - 標楷體
    public Font fontKCh12l = null;

    public static final float PX_PER_MM = 2.8333333F;

    // 繼承的 Class 要寫
    abstract public Document createDocument();

    /**
     * 建立 Table
     * 
     * @param columnSize 欄位數目
     * @param vAlignment 垂直對齊方式
     * @return 所建立之 Table
     * @throws Exception
     */
    public Table createTable(int columnSize) throws Exception {
        Table table = new Table(columnSize);
        // table.setOffset(-12);
        // table.setSpacing(3f);
        table.setPadding(2.0f);
        table.setBorderWidth(0);
        // table.setDefaultVerticalAlignment(vAlignment);
        table.setWidth(100);
        return table;
    }

    /**
     * 建立多行的 Column
     * 
     * @param table : 欲填入欄位的 table 元件
     * @param icolspan : column span 若無填1
     * @param irowspan : row span 若無填1
     * @param str : 欄位內容字串
     * @param font : 字型
     * @param phraseSize : phrase 的大小
     * @param borderWidth : 欄位 border 的寬度
     * @param hAlignment : 水平對齊方式
     * @throws BadElementException
     */
    public void addMultiRowColumn(Table table, int icolspan, int irowspan, String str, Font font, int phraseSize, int borderWidth, int hAlignment) throws BadElementException {
        Phrase phrase = new Phrase(phraseSize);
        phrase.add(new Chunk(StringUtils.defaultString(str), font));

        Cell cell = new Cell(phrase);
        cell.setBorderWidth(borderWidth);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(hAlignment);

        if (icolspan > 1)
            cell.setColspan(icolspan);

        if (irowspan > 1)
            cell.setRowspan(irowspan);

        table.addCell(cell);
    }

    /**
     * 建立 Column - 基本型 最常用的
     * 
     * @param table : 欲填入欄位的table元件
     * @param icolspan : column span 若無填1
     * @param irowspan : row span 若無填1
     * @param str : 欄位內容字串
     * @param font : 字型
     * @param borderWidth : 欄位 border 的寬度
     * @param hAlignment : 水平對齊方式
     * @throws BadElementException
     */
    public void addColumn(Table table, int icolspan, int irowspan, String str, Font font, int borderWidth, int hAlignment) throws BadElementException {
        Cell cell = new Cell(new Phrase(font.getSize() - (table.getSpacing() / 5), StringUtils.defaultString(str), font));
        cell.setBorderWidth(borderWidth);
        cell.setHorizontalAlignment(hAlignment);

        if (icolspan > 1)
            cell.setColspan(icolspan);

        if (irowspan > 1)
            cell.setRowspan(irowspan);

        table.addCell(cell);
    }

    /**
     * 建立 Column - 基本型 最常用的
     * 
     * @param table : 欲填入欄位的table元件
     * @param icolspan : column span 若無填1
     * @param irowspan : row span 若無填1
     * @param str : 欄位內容字串
     * @param font : 字型
     * @param lineSpace : 行距
     * @param borderWidth : 欄位 border 的寬度
     * @param hAlignment : 水平對齊方式
     * @throws BadElementException
     */
    public void addColumnAssignLineSpace(Table table, int icolspan, int irowspan, String str, Font font, float lineSpace, int borderWidth, int hAlignment) throws BadElementException {
        Cell cell = new Cell(new Phrase(font.getSize() + lineSpace, StringUtils.defaultString(str), font));
        cell.setBorderWidth(borderWidth);
        cell.setHorizontalAlignment(hAlignment);

        if (icolspan > 1)
            cell.setColspan(icolspan);

        if (irowspan > 1)
            cell.setRowspan(irowspan);

        table.addCell(cell);
    }

    /**
     * 建立 Column - 基本型 最常用的
     * 
     * @param table : 欲填入欄位的table元件
     * @param icolspan : column span 若無填1
     * @param irowspan : row span 若無填1
     * @param str : 欄位內容字串
     * @param font : 字型
     * @param borderWidth : 欄位 border 的寬度
     * @param hAlignment : 水平對齊方式
     * @param vAlignment : 垂直對齊方式
     * @throws BadElementException
     */
    public void addColumnAssignVAlignment(Table table, int icolspan, int irowspan, String str, Font font, int borderWidth, int hAlignment, int vAlignment) throws BadElementException {
        Cell cell = new Cell(new Phrase(font.getSize() - (table.getSpacing() / 5), StringUtils.defaultString(str), font));
        cell.setBorderWidth(borderWidth);
        cell.setHorizontalAlignment(hAlignment);
        cell.setVerticalAlignment(vAlignment);

        if (icolspan > 1)
            cell.setColspan(icolspan);

        if (irowspan > 1)
            cell.setRowspan(irowspan);

        table.addCell(cell);
    }

    /**
     * 建立 Column - 基本型 最常用的
     * 
     * @param table : 欲填入欄位的table元件
     * @param icolspan : column span 若無填1
     * @param irowspan : row span 若無填1
     * @param str : 欄位內容字串
     * @param font : 字型
     * @param lineSpace : 行距
     * @param borderWidth : 欄位 border 的寬度
     * @param hAlignment : 水平對齊方式
     * @param vAlignment : 垂直對齊方式
     * @throws BadElementException
     */
    public void addColumnAssignVAlignmentAndLineSpace(Table table, int icolspan, int irowspan, String str, Font font, float lineSpace, int borderWidth, int hAlignment, int vAlignment) throws BadElementException {
        Cell cell = new Cell(new Phrase(font.getSize() + lineSpace, StringUtils.defaultString(str), font));
        cell.setBorderWidth(borderWidth);
        cell.setHorizontalAlignment(hAlignment);
        cell.setVerticalAlignment(vAlignment);

        if (icolspan > 1)
            cell.setColspan(icolspan);

        if (irowspan > 1)
            cell.setRowspan(irowspan);

        table.addCell(cell);
    }

    /**
     * 建立 Column - 需指定 border 的樣式時使用
     * 
     * @param table : 欲填入欄位的 table 元件
     * @param icolspan : column span 若無填1
     * @param irowspan : row span 若無填1
     * @param str : 欄位內容字串
     * @param font : 字型
     * @param borderWidth : 欄位 border 的寬度
     * @param borderType : 欄位 border 的樣式 (顯示格線 上 下 左 右...)
     * @param hAlignment : 水平對齊方式
     * @throws BadElementException
     */
    public void addColumn(Table table, int icolspan, int irowspan, String str, Font font, int borderWidth, int borderType, int hAlignment) throws BadElementException {
        Cell cell = new Cell(new Phrase(font.getSize() - (table.getSpacing() / 5), StringUtils.defaultString(str), font));
        cell.setBorderWidth(borderWidth);
        cell.setBorder(borderType);
        cell.setHorizontalAlignment(hAlignment);

        if (icolspan > 1)
            cell.setColspan(icolspan);

        if (irowspan > 1)
            cell.setRowspan(irowspan);

        table.addCell(cell);
    }

    /**
     * 建立 Column - 需指定 border 的樣式時使用
     * 
     * @param table : 欲填入欄位的 table 元件
     * @param icolspan : column span 若無填1
     * @param irowspan : row span 若無填1
     * @param str : 欄位內容字串
     * @param font : 字型
     * @param lineSpace : 行距
     * @param borderWidth : 欄位 border 的寬度
     * @param borderType : 欄位 border 的樣式 (顯示格線 上 下 左 右...)
     * @param hAlignment : 水平對齊方式
     * @throws BadElementException
     */
    public void addColumnAssignLineSpace(Table table, int icolspan, int irowspan, String str, Font font, float lineSpace, int borderWidth, int borderType, int hAlignment) throws BadElementException {
        Cell cell = new Cell(new Phrase(font.getSize() + lineSpace, StringUtils.defaultString(str), font));
        cell.setBorderWidth(borderWidth);
        cell.setBorder(borderType);
        cell.setHorizontalAlignment(hAlignment);

        if (icolspan > 1)
            cell.setColspan(icolspan);

        if (irowspan > 1)
            cell.setRowspan(irowspan);

        table.addCell(cell);
    }

    /**
     * 建立 Column - 需指定 border 的樣式時使用
     * 
     * @param table : 欲填入欄位的 table 元件
     * @param icolspan : column span 若無填1
     * @param irowspan : row span 若無填1
     * @param str : 欄位內容字串
     * @param font : 字型
     * @param borderWidth : 欄位 border 的寬度
     * @param borderType : 欄位 border 的樣式 (顯示格線 上 下 左 右...)
     * @param hAlignment : 水平對齊方式
     * @param vAlignment : 垂直對齊方式
     * @throws BadElementException
     */
    public void addColumnAssignVAlignment(Table table, int icolspan, int irowspan, String str, Font font, int borderWidth, int borderType, int hAlignment, int vAlignment) throws BadElementException {
        Cell cell = new Cell(new Phrase(font.getSize() - (table.getSpacing() / 5), StringUtils.defaultString(str), font));
        cell.setBorderWidth(borderWidth);
        cell.setBorder(borderType);
        cell.setHorizontalAlignment(hAlignment);
        cell.setVerticalAlignment(vAlignment);

        if (icolspan > 1)
            cell.setColspan(icolspan);

        if (irowspan > 1)
            cell.setRowspan(irowspan);

        table.addCell(cell);
    }

    /**
     * 建立 Column - 需指定 border 的樣式時使用
     * 
     * @param table : 欲填入欄位的 table 元件
     * @param icolspan : column span 若無填1
     * @param irowspan : row span 若無填1
     * @param str : 欄位內容字串
     * @param font : 字型
     * @param lineSpace : 行距
     * @param borderWidth : 欄位 border 的寬度
     * @param borderType : 欄位 border 的樣式 (顯示格線 上 下 左 右...)
     * @param hAlignment : 水平對齊方式
     * @param vAlignment : 垂直對齊方式
     * @throws BadElementException
     */
    public void addColumnAssignVAlignmentAndLineSpace(Table table, int icolspan, int irowspan, String str, Font font, float lineSpace, int borderWidth, int borderType, int hAlignment, int vAlignment) throws BadElementException {
        Cell cell = new Cell(new Phrase(font.getSize() + lineSpace, StringUtils.defaultString(str), font));
        cell.setBorderWidth(borderWidth);
        cell.setBorder(borderType);
        cell.setHorizontalAlignment(hAlignment);
        cell.setVerticalAlignment(vAlignment);

        if (icolspan > 1)
            cell.setColspan(icolspan);

        if (irowspan > 1)
            cell.setRowspan(irowspan);

        table.addCell(cell);
    }

    /**
     * 建立 Column - 利用 Phrase 建立欄位
     * 
     * @param table : 欲填入欄位的table元件
     * @param icolspan : column span 若無填1
     * @param irowspan : row span 若無填1
     * @param pharse : 欄位內容
     * @param borderWidth : 欄位 border 的寬度
     * @param hAlignment : 水平對齊方式
     * @throws BadElementException
     */
    public void addColumn(Table table, int icolspan, int irowspan, Phrase pharse, int borderWidth, int hAlignment) throws BadElementException {
        Cell cell = new Cell(pharse);
        cell.setBorderWidth(borderWidth);
        cell.setHorizontalAlignment(hAlignment);

        if (icolspan > 1)
            cell.setColspan(icolspan);

        if (irowspan > 1)
            cell.setRowspan(irowspan);

        table.addCell(cell);
    }

    /**
     * 建立 Column - 利用 Phrase 建立欄位
     * 
     * @param table : 欲填入欄位的table元件
     * @param icolspan : column span 若無填1
     * @param irowspan : row span 若無填1
     * @param pharse : 欄位內容
     * @param borderWidth : 欄位 border 的寬度
     * @param hAlignment : 水平對齊方式
     * @param vAlignment : 垂直對齊方式
     * @throws BadElementException
     */
    public void addColumnAssignVAlignment(Table table, int icolspan, int irowspan, Phrase pharse, int borderWidth, int hAlignment, int vAlignment) throws BadElementException {
        Cell cell = new Cell(pharse);
        cell.setBorderWidth(borderWidth);
        cell.setHorizontalAlignment(hAlignment);
        cell.setVerticalAlignment(vAlignment);

        if (icolspan > 1)
            cell.setColspan(icolspan);

        if (irowspan > 1)
            cell.setRowspan(irowspan);

        table.addCell(cell);
    }

    /**
     * 建立 Barcode 格式為 Barcode39
     * 
     * @param table 欲填入欄位的table元件
     * @param code Barcode 內容
     * @param iBarScaleWidth Barcode 寬度縮圖百分比
     * @param iBarScaleHeight Barcode 寬度縮圖百分比
     * @param icolspan column span 若無填 1
     * @param irowspan row span 若無填 1
     * @param borderWidth 欄位 border 的寬度
     * @param hAlignment 水平對齊方式
     * @param vAlignment 垂直對齊方式
     * @throws BadElementException
     */
    public void addBarcode39(Table table, String code, int iBarScaleWidth, int iBarScaleHeight, int icolspan, int irowspan, int borderWidth, int hAlignment, int vAlignment) throws BadElementException {
        PdfContentByte cb = writer.getDirectContent();
        Barcode39 barCode = new Barcode39();
        barCode.setCode(StringUtils.defaultString(code));
        barCode.setStartStopText(false);
        Image image = barCode.createImageWithBarcode(cb, null, null);
        image.scalePercent(iBarScaleWidth, iBarScaleHeight);

        Cell cell = new Cell(new Phrase(new Chunk(image, 0, 0)));
        cell.setBorderWidth(borderWidth);
        cell.setHorizontalAlignment(hAlignment);

        if (icolspan > 1)
            cell.setColspan(icolspan);

        if (irowspan > 1)
            cell.setRowspan(irowspan);

        table.addCell(cell);
    }

    /**
     * 建立 Barcode 格式為 Barcode39 (不顯示 Barcode 下的文字)
     * 
     * @param table 欲填入欄位的table元件
     * @param code Barcode 內容
     * @param iBarScaleWidth Barcode 寬度縮圖百分比
     * @param iBarScaleHeight Barcode 寬度縮圖百分比
     * @param iBaseline 文字 Baseline
     * @param icolspan column span 若無填 1
     * @param irowspan row span 若無填 1
     * @param borderWidth 欄位 border 的寬度
     * @param hAlignment 水平對齊方式
     * @param vAlignment 垂直對齊方式
     * @throws BadElementException
     */
    public void addBarcode39NoLabel(Table table, String code, int iBarScaleWidth, int iBarScaleHeight, int iBaseline, int icolspan, int irowspan, int borderWidth, int hAlignment, int vAlignment) throws BadElementException {
        PdfContentByte cb = writer.getDirectContent();
        Barcode39 barCode = new Barcode39();
        barCode.setCode(StringUtils.defaultString(code));
        barCode.setStartStopText(false);
        barCode.setSize(1);
        barCode.setBaseline(iBaseline);
        Image image = barCode.createImageWithBarcode(cb, null, Color.WHITE);
        image.scalePercent(iBarScaleWidth, iBarScaleHeight);

        Cell cell = new Cell(new Phrase(new Chunk(image, 0, 0)));
        cell.setBorderWidth(borderWidth);
        cell.setHorizontalAlignment(hAlignment);

        if (icolspan > 1)
            cell.setColspan(icolspan);

        if (irowspan > 1)
            cell.setRowspan(irowspan);

        table.addCell(cell);
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

    /**
     * 格式化傳入的數值, 將其加入三位一撇傳回
     * 
     * @param nNumber 欲格式化的數值
     * @return
     */
    public static String formatBigDecimalToInteger(BigDecimal nNumber) {
        if (nNumber != null) {
            DecimalFormat df = new DecimalFormat("##,###,###,###,###");
            return df.format(nNumber.longValue());
        }
        else {
            return "";
        }
    }

    /**
     * 格式化傳入的數值, 將其加入三位一撇傳回
     * 
     * @param nNumber 欲格式化的數值
     * @return
     */
    public static String formatBigDecimalToFloat(BigDecimal nNumber) {
        if (nNumber != null) {
            DecimalFormat df = new DecimalFormat("##,###,###,###,##0.00");
            return df.format(nNumber.doubleValue());
        }
        else {
            return "";
        }
    }

    /**
     * 格式化傳入的數值, 將其加入三位一撇傳回 例: 傳入 12345 將會傳回 12,345
     * 
     * @param nNumber 欲格式化的數值
     * @return
     */
    public static String formatNumber(long nNumber) {
        return formatNumber(new Long(nNumber));
    }

    /**
     * 格式化傳入的數值, 將其加入三位一撇傳回 例: 傳入 12345 將會傳回 12,345
     * 
     * @param nNumber 欲格式化的數值
     * @return
     */
    public static String formatNumber(Long nNumber) {
        if (nNumber != null) {
            DecimalFormat df = new DecimalFormat("##,###,###,###,###");
            return df.format(nNumber.longValue());
        }
        else {
            return "";
        }
    }

    /**
     * 格式化傳入的數值, 將其加入三位一撇傳回 例: 傳入 12345 將會傳回 12,345
     * 
     * @param nNumber 欲格式化的數值
     * @return
     */
    public static String formatNumber(Integer nNumber) {
        if (nNumber != null) {
            DecimalFormat df = new DecimalFormat("##,###,###,###,###");
            return df.format(nNumber.longValue());
        }
        else {
            return "";
        }
    }

    /**
     * 格式化傳入的 浮點 數值, 將其加入三位一撇傳回 - 小數位數為兩位 例: 傳入 12345 將會傳回 12,345.00
     * 
     * @param nNumber 欲格式化的數值
     * @return
     */
    public static String formatDoubleNumber(Double nNumber) {
        return formatDoubleNumber(nNumber, 2);
    }

    /**
     * 格式化傳入的 浮點 數值, 將其加入三位一撇傳回 - 可指定小數點位數 例: 傳入 12345 , 4 將會傳回 12,345.0000 若傳入小數點位數為 0 則會自動傳回兩位小數
     * 
     * @param nNumber 欲格式化的數值
     * @param nFraction 小數點位數
     * @return
     */
    public static String formatDoubleNumber(Double nNumber, int nFraction) {
        if (nFraction == 0)
            nFraction = 2;

        StringBuffer decimalTemplate = new StringBuffer("##,###,###,###,##0.");
        decimalTemplate.append(StringUtils.repeat("0", nFraction));

        if (nNumber != null) {
            DecimalFormat df = new DecimalFormat(decimalTemplate.toString());
            return df.format(nNumber.doubleValue());
        }
        else {
            return "0." + StringUtils.repeat("0", nFraction);
        }
    }

    /**
     * 合併 郵區成為住址
     * 
     * @param zip 郵區
     * @param addr 住址
     * @return 完整住址字串
     */
    public static String toAddressString(String zip, String addr) {
        String zipStr = "";
        if (StringUtils.isNotEmpty(zip))
            zipStr += " (" + zip.trim() + ")";
        if (StringUtils.isNotEmpty(addr))
            zipStr += " " + addr.trim();
        if (StringUtils.isNotEmpty(zipStr))
            return "　";
        return zipStr;
    }

    /**
     * 合併 區碼，分機 成為 完整電話號碼
     * 
     * @param zip 區碼
     * @param telNo 電話
     * @param ext 分機
     * @return 完整含有區碼，分機的電話號碼字串
     */
    public static String toPhoneString(String zip, String telNo) {
        return toPhoneString(zip, telNo, "");
    }

    public static String toPhoneString(String zip, String telNo, String ext) {
        String zipStr = "";
        if (StringUtils.isNotEmpty(zip))
            zipStr += " (" + zip.trim() + ")";
        if (StringUtils.isNotEmpty(telNo))
            zipStr += " " + telNo.trim();
        if (StringUtils.isNotEmpty(ext))
            zipStr += " 轉 " + ext.trim();
        if (StringUtils.isNotEmpty(zipStr))
            return "　";
        return zipStr;
    }

    /**
     * 回傳由 PdfWriter 產生的 ContentByte
     * 
     * @return
     */
    public PdfContentByte getPdfContetByte() {
        return writer.getDirectContent();
    }

    /**
     * 取得 PdfTemplate
     * 
     * @param width : 欲建立之template 的寬
     * @param height : 欲建立之template 的高
     * @return
     */
    public PdfTemplate getPdfTemplate(float width, float height) {
        return contentByte.createTemplate(width, height);
    }

    public void createPageText(float pageX, float pageY, String pageText1, String pageText2, int fontsize, float twidth, float theight) {
        if (contentByte == null)
            contentByte = getPdfContetByte();
        if (template == null)
            template = getPdfTemplate(twidth, theight);

        String text = pageText1 + writer.getPageNumber() + pageText2;

        float len = bfChinese.getWidthPoint(text, fontsize);
        contentByte.beginText();
        contentByte.setFontAndSize(bfChinese, fontsize);
        contentByte.setTextMatrix(pageX, pageY);
        contentByte.showText(pageText1);
        contentByte.showText(String.valueOf(writer.getPageNumber()));
        contentByte.showText(pageText2);
        contentByte.endText();
        contentByte.addTemplate(template, pageX + len, pageY - 10f);
    }

    public void createPageText(float pageX, float pageY, int pageNum, String pageText1, String pageText2, int fontsize, float twidth, float theight) {
        if (contentByte == null)
            contentByte = getPdfContetByte();
        if (template == null)
            template = getPdfTemplate(twidth, theight);

        String text = pageText1 + pageNum + pageText2;

        float len = bfChinese.getWidthPoint(text, fontsize);
        contentByte.beginText();
        contentByte.setFontAndSize(bfChinese, fontsize);
        contentByte.setTextMatrix(pageX, pageY);
        contentByte.showText(pageText1);
        contentByte.showText(String.valueOf(writer.getPageNumber()));
        contentByte.showText(pageText2);
        contentByte.endText();
        contentByte.addTemplate(template, pageX + len, pageY - 10f);
    }

    /**
     * 產生 (第幾頁共幾頁的東西) 要配合createPageText 最後會將總頁數填回 *****注意**** 若無 document.newpage會有無法取得總頁數的情況
     */
    public void closePageText(String pageText, float fontsize) {
        template.beginText();
        /* init Font set Font Type */

        template.setFontAndSize(bfChinese, fontsize);
        template.setTextMatrix(0f, 10f);
        template.showText(pageText);
        template.endText();
    }

    /**
     * 放置字串到報表指定位置
     */
    public void createPageStringText(float pageX, float pageY, String pageText, int fontsize, float twidth, float theight) {
        if (contentByte == null)
            contentByte = getPdfContetByte();
        if (template == null)
            template = getPdfTemplate(twidth, theight);

        String text = pageText;

        float len = bfChinese.getWidthPoint(text, fontsize);
        contentByte.beginText();
        contentByte.setFontAndSize(bfChinese, fontsize);
        contentByte.setTextMatrix(pageX, pageY);
        contentByte.showText(pageText);
        contentByte.endText();
        contentByte.addTemplate(template, pageX + len, pageY - 10f);
    }

    /**
     * 取得 Resource 目錄中指定檔名之實際路徑
     * 
     * @param fileName
     * @return
     */
    public String getResourcePath(String fileName) {
        String resourcePath = EnvHelper.resourcePath;
        if (!StringUtils.endsWithIgnoreCase(resourcePath, System.getProperty("file.separator")))
            resourcePath = resourcePath + System.getProperty("file.separator");
        System.out.println(resourcePath + fileName);
        return resourcePath + fileName;
    }

    public void initFont() throws IOException {
        try {
            /* init Font set Font Type */

            bfChinese = BaseFont.createFont(EnvHelper.sungPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            bfKChinese = BaseFont.createFont(EnvHelper.kaiPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            // bfChinese = BaseFont.createFont(getResourcePath("mingliu.ttc,0"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            // bfKChinese = BaseFont.createFont(getResourcePath("kaiu.ttf"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            // bfChinese = BaseFont.createFont("MSung-Light", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);

            fontCh40 = new Font(bfChinese, 40, Font.NORMAL);
            fontCh32 = new Font(bfChinese, 32, Font.NORMAL);
            fontCh30 = new Font(bfChinese, 30, Font.NORMAL);
            fontCh28 = new Font(bfChinese, 28, Font.NORMAL);
            fontCh26 = new Font(bfChinese, 26, Font.NORMAL);
            fontCh24 = new Font(bfChinese, 24, Font.NORMAL);
            fontCh22 = new Font(bfChinese, 22, Font.NORMAL);
            fontCh20 = new Font(bfChinese, 20, Font.NORMAL);
            fontCh18 = new Font(bfChinese, 18, Font.NORMAL);
            fontCh16 = new Font(bfChinese, 16, Font.NORMAL);
            fontCh14 = new Font(bfChinese, 14, Font.NORMAL);
            fontCh13 = new Font(bfChinese, 13, Font.NORMAL);
            fontCh12 = new Font(bfChinese, 12, Font.NORMAL);
            fontCh11 = new Font(bfChinese, 11, Font.NORMAL);
            fontCh10 = new Font(bfChinese, 10, Font.NORMAL);
            fontCh9 = new Font(bfChinese, 9, Font.NORMAL);
            fontCh8 = new Font(bfChinese, 8, Font.NORMAL);
            fontCh7 = new Font(bfChinese, 7, Font.NORMAL);
            fontCh6 = new Font(bfChinese, 6, Font.NORMAL);
            fontCh1 = new Font(bfChinese, 1, Font.NORMAL);

            fontKCh40 = new Font(bfKChinese, 40, Font.NORMAL);
            fontKCh32 = new Font(bfKChinese, 32, Font.NORMAL);
            fontKCh30 = new Font(bfKChinese, 30, Font.NORMAL);
            fontKCh28 = new Font(bfKChinese, 28, Font.NORMAL);
            fontKCh26 = new Font(bfKChinese, 26, Font.NORMAL);
            fontKCh24 = new Font(bfKChinese, 24, Font.NORMAL);
            fontKCh22 = new Font(bfKChinese, 22, Font.NORMAL);
            fontKCh20 = new Font(bfKChinese, 20, Font.NORMAL);
            fontKCh18 = new Font(bfKChinese, 18, Font.NORMAL);
            fontKCh16 = new Font(bfKChinese, 16, Font.NORMAL);
            fontKCh14 = new Font(bfKChinese, 14, Font.NORMAL);
            fontKCh13 = new Font(bfKChinese, 13, Font.NORMAL);
            fontKCh12 = new Font(bfKChinese, 12, Font.NORMAL);
            fontKCh11 = new Font(bfKChinese, 11, Font.NORMAL);
            fontKCh10 = new Font(bfKChinese, 10, Font.NORMAL);
            fontKCh9 = new Font(bfKChinese, 9, Font.NORMAL);
            fontKCh8 = new Font(bfKChinese, 8, Font.NORMAL);
            fontKCh7 = new Font(bfKChinese, 7, Font.NORMAL);
            fontKCh6 = new Font(bfKChinese, 6, Font.NORMAL);
            fontKCh1 = new Font(bfKChinese, 1, Font.NORMAL);
            // 粗體
            fontCh40b = new Font(bfChinese, 40, Font.BOLD);
            fontCh32b = new Font(bfChinese, 32, Font.BOLD);
            fontCh30b = new Font(bfChinese, 30, Font.BOLD);
            fontCh28b = new Font(bfChinese, 28, Font.BOLD);
            fontCh26b = new Font(bfChinese, 26, Font.BOLD);
            fontCh24b = new Font(bfChinese, 24, Font.BOLD);
            fontCh22b = new Font(bfChinese, 22, Font.BOLD);
            fontCh20b = new Font(bfChinese, 20, Font.BOLD);
            fontCh18b = new Font(bfChinese, 18, Font.BOLD);
            fontCh16b = new Font(bfChinese, 16, Font.BOLD);
            fontCh14b = new Font(bfChinese, 14, Font.BOLD);
            fontCh13b = new Font(bfChinese, 13, Font.BOLD);
            fontCh12b = new Font(bfChinese, 12, Font.BOLD);
            fontCh11b = new Font(bfChinese, 11, Font.BOLD);
            fontCh10b = new Font(bfChinese, 10, Font.BOLD);
            fontCh9b = new Font(bfChinese, 9, Font.BOLD);
            fontCh8b = new Font(bfChinese, 8, Font.BOLD);
            fontCh7b = new Font(bfChinese, 7, Font.BOLD);
            fontCh6b = new Font(bfChinese, 6, Font.BOLD);

            fontKCh40b = new Font(bfKChinese, 40, Font.BOLD);
            fontKCh32b = new Font(bfKChinese, 32, Font.BOLD);
            fontKCh30b = new Font(bfKChinese, 30, Font.BOLD);
            fontKCh28b = new Font(bfKChinese, 28, Font.BOLD);
            fontKCh26b = new Font(bfKChinese, 26, Font.BOLD);
            fontKCh24b = new Font(bfKChinese, 24, Font.BOLD);
            fontKCh22b = new Font(bfKChinese, 22, Font.BOLD);
            fontKCh20b = new Font(bfKChinese, 20, Font.BOLD);
            fontKCh18b = new Font(bfKChinese, 18, Font.BOLD);
            fontKCh16b = new Font(bfKChinese, 16, Font.BOLD);
            fontKCh14b = new Font(bfKChinese, 14, Font.BOLD);
            fontKCh13b = new Font(bfKChinese, 13, Font.BOLD);
            fontKCh12b = new Font(bfKChinese, 12, Font.BOLD);
            fontKCh11b = new Font(bfKChinese, 11, Font.BOLD);
            fontKCh10b = new Font(bfKChinese, 10, Font.BOLD);
            fontKCh9b = new Font(bfKChinese, 9, Font.BOLD);
            fontKCh8b = new Font(bfKChinese, 8, Font.BOLD);
            fontKCh7b = new Font(bfKChinese, 7, Font.BOLD);
            fontKCh6b = new Font(bfKChinese, 6, Font.BOLD);
            // 底線
            fontCh12l = new Font(bfChinese, 12, Font.UNDERLINE);

            fontKCh12l = new Font(bfKChinese, 12, Font.UNDERLINE);
        }
        catch (Exception e) {
            log.error("ReportBase initial Font Error:" + ExceptionUtility.getStackTrace(e));
        }
    }

    /**
     * 取得 金額轉為中文大寫
     * 
     * @param payAmt
     * @return
     */

    public static String toBigAmt(BigDecimal payAmt) {

        String amt = nf.format(payAmt.longValue());
        Double d = new Double(payAmt.doubleValue());

        String dotPart = ""; // 取小數位數
        String intPart = ""; // 取整數位數
        int dotPos;

        if ((dotPos = amt.indexOf('.')) != -1) {
            intPart = amt.substring(0, dotPos);
            dotPart = amt.substring(dotPos + 1);
        }
        else {
            intPart = amt;
        }
        if (intPart.length() > 16)
            throw new java.lang.InternalError("The amount is too big.");
        String intBig = intToBig(intPart);
        String dotBig = dotToBig(dotPart);
        if (dotBig.length() == 0)
            return intBig + "元整";
        else
            return intBig + "元" + dotBig;
    }

    private static String dotToBig(String dotPart) {
        // 得到轉換後的大寫金額（小數位數）
        String strRet = "";
        for (int i = 0; i < dotPart.length() && i < 3; i++) {
            int num;
            if ((num = Integer.parseInt(dotPart.substring(i, i + 1))) != 0)
                strRet += NUM.substring(num, num + 1) + DOTUNIT.substring(i, i + 1);
        }
        return strRet;
    }

    private static String intToBig(String intPart) {
        // 得到轉換後的大寫金額（整數位數）
        int grade; // 位數
        String result = "";
        String strTmp = "";

        // 得到位數
        grade = intPart.length() / GRADE;
        // 調整位數长度
        if (intPart.length() % GRADE != 0)
            grade += 1;

        // 對每位數處理
        for (int i = grade; i >= 1; i--) {
            strTmp = getNowGradeVal(intPart, i);// 取得目前位數
            result += getSubUnit(strTmp);// 轉成大寫
            result = dropZero(result);// 除零
            // 加位數單位
            if (i > 1) // 末位不加單位

                result += GRADEUNIT.substring(i - 1, i);
        }
        return result;
    }

    private static String getNowGradeVal(String strVal, int grade) {
        // 得到目前位數字串
        String rst;
        if (strVal.length() <= grade * GRADE)
            rst = strVal.substring(0, strVal.length() - (grade - 1) * GRADE);
        else
            rst = strVal.substring(strVal.length() - grade * GRADE, strVal.length() - (grade - 1) * GRADE);
        return rst;
    }

    private static String getSubUnit(String strVal) {
        // 數值轉換
        String rst = "";

        for (int i = 0; i < strVal.length(); i++) {
            String s = strVal.substring(i, i + 1);
            int num = Integer.parseInt(s);
            if (num == 0) {
                // 零
                if (i != strVal.length()) // 轉換後末位數不為零
                    rst += "零";
            }
            else {
                // If IntKey = 1 And i = 2 Then
                // "壹拾"
                // Else
                rst += NUM.substring(num, num + 1);
                // End If

                if (i != strVal.length() - 1)// 個位數不加單位
                    rst += UNIT.substring(i + 4 - strVal.length(), i + 4 - strVal.length() + 1);
            }
        }
        return rst;
    }

    private static String dropZero(String strVal) {
        // 去除連續的"零"
        String strRst;
        String strBefore; // 前一位置
        String strNow; // 目前位置

        strBefore = strVal.substring(0, 1);
        strRst = strBefore;

        for (int i = 1; i < strVal.length(); i++) {
            strNow = strVal.substring(i, i + 1);
            if (strNow.equals("零") && strBefore.equals("零"))
                ;// 同時為零
            else
                strRst += strNow;
            strBefore = strNow;
        }

        // 末位去零
        if (strRst.substring(strRst.length() - 1, strRst.length()).equals("零"))
            strRst = strRst.substring(0, strRst.length() - 1);
        return strRst;
    }

    public ReportBase() throws Exception {
        initFont();
        document = createDocument();
        bao = new ByteArrayOutputStream();
        writer = PdfWriter.getInstance(document, bao);
    }
    /**
	 * 用X&Y座標寫文字
	 * 
	 * @param document
	 * @param text
	 * @param X 起始點的X座標
	 * @param Y 起始點的Y座標(由bottom開始算)
	 * @param rotation 角度
	 * @param font 字型大小
	 * @param align 對齊方式
	 */	
	public void  drawString(Document document, String text, float x, float y ,float rotation, float font, String align) throws Exception {
		 
		//Font helvetica = fontKCh9;
		Font helvetica = fontCh9;
	    BaseFont bf_helv = helvetica.getCalculatedBaseFont(false);

		PdfContentByte canvas = writer.getDirectContentUnder();
		canvas.beginText();
        canvas.setFontAndSize(bf_helv, font);

        if(align.equals("right"))
        {
        	canvas.showTextAligned(Element.ALIGN_RIGHT, text, x, y, rotation);
        }
        else if(align.equals("left"))
        {
        	canvas.showTextAligned(Element.ALIGN_LEFT, text, x, y, rotation);
        }
        else
        {
        	canvas.showTextAligned(Element.ALIGN_JUSTIFIED, text, x, y, rotation);
        }
        /*canvas.showTextAligned(Element.ALIGN_CENTER, foobar, 400, 716, 0);
        canvas.showTextAligned(Element.ALIGN_CENTER, foobar, 400, 680, 30);
        canvas.showTextAlignedKerned(Element.ALIGN_LEFT, foobar, 400, 644, 0);*/
        canvas.endText();

	}
    public void printToDisk(String path, ByteArrayOutputStream dao) throws Exception {
        FileOutputStream fso = null;
        try {
            fso = new FileOutputStream(path);
            dao.writeTo(fso);
        }
        finally {
            if (fso != null) {
                try {
                    fso.close();
                }
                catch (IOException e) {
                }
            }
        }
    }

    //將以 mm 為單位的值換算為單位 pixel
    float pt(float mm) {
        return mm * PX_PER_MM;
    }

    //將以當前頁左上角為坐標原點的 y 坐標值轉為 PDF 之以左下角為原點之值(單位: pt)
    float transY(float y) {
        return writer.getPageSize().getHeight() - y;
    }

    public void drawImage(String imgPath, float x, float y, float width, float height) throws DocumentException, IOException {
    	PdfContentByte cb = writer.getDirectContent();
    	Image image = Image.getInstance(imgPath);
		image.scaleAbsolute((width > 0) ? pt(width) : image.getPlainWidth(), (height > 0) ? pt(height) : image.getPlainHeight());
		image.setAbsolutePosition(pt(x), transY(pt(y)) - image.getScaledHeight());

    	cb.addImage(image);
    }

    public void drawImage(byte[] imgByte, float x, float y, float width, float height) throws DocumentException, IOException {
    	PdfContentByte cb = writer.getDirectContent();
		Image image = Image.getInstance(imgByte);
		image.scaleAbsolute((width > 0) ? pt(width) : image.getPlainWidth(), (height > 0) ? pt(height) : image.getPlainHeight());
		image.setAbsolutePosition(pt(x), transY(pt(y)) - image.getScaledHeight());

    	cb.addImage(image);
    }

    public void addImage(Table table, byte[] imgByte, int iImageWidth, int iImageHeight, int icolspan, int irowspan, int borderWidth, int iImageOffsetX, int iImageOffsetY) throws BadElementException, MalformedURLException, IOException {
    	Image image = Image.getInstance(imgByte);
    	image.scaleAbsolute(pt(iImageWidth), pt(iImageHeight));
    	Cell cell = new Cell(new Phrase(new Chunk(image, pt(iImageOffsetX), pt(iImageOffsetY))));
    	cell.setBorder(borderWidth);
    	cell.setColspan(icolspan);
    	cell.setRowspan(irowspan);

    	table.addCell(cell);
	}
}
