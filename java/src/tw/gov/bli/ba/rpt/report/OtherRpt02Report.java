/**
 * 
 */
package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.util.List;
import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.rpt.cases.OtherRpt02Case;
import tw.gov.bli.ba.rpt.report.ReportBase;
import tw.gov.bli.ba.util.DateUtility;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;

/**
 * 列印作業 - 其他類報表 - 已退匯止付或退回現金尚未沖轉收回案件清單
 * 
 * @author Noctis
 */
public class OtherRpt02Report extends ReportBase {
    private String printDate = ""; // 印表日期
    private static final int COUNT = 30; // 報表每頁筆數

    public OtherRpt02Report() throws Exception {
        super();
        printDate = DateUtility.getNowChineseDate();
    }

    public Document createDocument() {
        // 參數說明: Document(頁面大小, 左邊空白, 右邊空白, 上面空白, 下面空白)
        Document document = new Document(PageSize.A4, 20, 20, 20, 20);
        return document;
    }

    /**
     * 加入空白行
     * 
     * @param table
     * @param rows 行數
     * @throws Exception
     */
    private void addEmptyRow(Table table, int rows) throws Exception {
        for (int i = 0; i < rows; i++) {
            addColumn(table, 200, 1, "　", fontCh12, 0, CENTER);
        }
    }

    /**
     * 建立 Column - 需指定 border 的樣式時使用
     * 
     * @param table : 欲填入欄位的 table 元件
     * @param icolspan : column span 若無填1
     * @param str : 欄位內容字串
     * @param font : 字型
     * @param borderWidth : 欄位 border 的寬度
     * @param borderType : 欄位 border 的樣式 (顯示格線 上 下 左 右...)
     * @param hAlignment : 水平對齊方式
     * @throws BadElementException
     */
    public void addPdfPCell(PdfPTable table, int icolspan, String str, Font font, int borderWidth, int borderType, int hAlignment) throws BadElementException {
        PdfPCell cell = new PdfPCell(new Phrase(font.getSize(), StringUtils.defaultString(str), font));
        cell.setBorderWidth(borderWidth);
        cell.setBorder(borderType);
        cell.setHorizontalAlignment(hAlignment);
        cell.setPaddingTop(3.0f);
        cell.setPaddingBottom(5.0f);

        if (icolspan > 1)
            cell.setColspan(icolspan);

        table.addCell(cell);
    }

    /**
     * 建立 Column - 基本型 最常用的
     * 
     * @param table : 欲填入欄位的table元件
     * @param icolspan : column span 若無填1
     * @param str : 欄位內容字串
     * @param font : 字型
     * @param borderWidth : 欄位 border 的寬度
     * @param hAlignment : 水平對齊方式
     * @throws BadElementException
     */
    public void addPdfPCell(PdfPTable table, int icolspan, String str, Font font, int borderWidth, int hAlignment) throws BadElementException {
        PdfPCell cell = new PdfPCell(new Phrase(font.getSize(), StringUtils.defaultString(str), font));
        cell.setBorderWidth(borderWidth);
        cell.setHorizontalAlignment(hAlignment);
        cell.setVerticalAlignment(MIDDLE);
        cell.setPaddingTop(8.0f);
        cell.setPaddingBottom(10.0f);

        if (icolspan > 1)
            cell.setColspan(icolspan);

        table.addCell(cell);
    }

    /**
     * 建立 PdfPCell
     * 
     * @param table : 欲填入欄位的table元件
     * @param icolspan : column span 若無填 1
     * @param str : 欄位內容字串
     * @param font : 字型
     * @param hAlignment : 水平對齊方式
     * @throws BadElementException
     */
    public void addPdfPCellWithEvent(PdfPTable table, int icolspan, String str, Font font, int hAlignment, PdfPCellEvent event) throws BadElementException {
        PdfPCell cell = new PdfPCell(new Phrase(font.getSize(), StringUtils.defaultString(str), font));
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(hAlignment);
        cell.setCellEvent(event);
        cell.setPaddingBottom(4.0f);

        if (icolspan > 1)
            cell.setColspan(icolspan);

        table.addCell(cell);
    }

    /**
     * 建立 PdfPCell
     * 
     * @param table : 欲填入欄位的table元件
     * @param icolspan : column span 若無填 1
     * @param str : 欄位內容字串
     * @param font : 字型
     * @param hAlignment : 水平對齊方式
     * @param vAlignment : 垂直對齊方式
     * @throws BadElementException
     */
    public void addPdfPCellAssignVAlignmentWithEvent(PdfPTable table, int icolspan, String str, Font font, int hAlignment, int vAlignment, PdfPCellEvent event) throws BadElementException {
        PdfPCell cell = new PdfPCell(new Phrase(font.getSize(), StringUtils.defaultString(str), font));
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(hAlignment);
        cell.setVerticalAlignment(vAlignment);
        cell.setCellEvent(event);
        cell.setPaddingBottom(4.0f);

        if (icolspan > 1)
            cell.setColspan(icolspan);

        table.addCell(cell);
    }

    /**
     * 建立 PdfPCell
     * 
     * @param table : 欲填入欄位的table元件
     * @param icolspan : column span 若無填 1
     * @param font : 字型
     * @param hAlignment : 水平對齊方式
     * @param padding
     * @throws BadElementException
     */
    public void addEmptyPdfPCellWithEvent(PdfPTable table, int icolspan, Font font, int hAlignment, float padding, PdfPCellEvent event) throws BadElementException {
        PdfPCell cell = new PdfPCell(new Phrase(font.getSize(), StringUtils.defaultString(""), font));
        cell.setBorderWidth(0);
        cell.setHorizontalAlignment(hAlignment);
        cell.setCellEvent(event);
        cell.setPaddingBottom(padding);

        if (icolspan > 1)
            cell.setColspan(icolspan);

        table.addCell(cell);
    }

    /**
     * 取得報表 Header PdfTable
     * 
     * @param qryAppCycleYY
     * @param qryAppNo
     * @param appNo
     * @return
     */
    public PdfPTable doReportHeader(String payCode) throws Exception {
        PdfPTable table = new PdfPTable(100);
        table.setWidthPercentage(100f);

        addPdfPCell(table, 100, "已退匯或退回現金尚未沖轉收回案件清單", fontCh14b, 0, 1, CENTER);
        addPdfPCell(table, 100, "　", fontCh14b, 0, 1, CENTER);

        if(payCode.equals("L")){
        	addPdfPCell(table, 30, "給付別：老年年金", fontCh12, 0, 1, LEFT);
        }else if(payCode.equals("K")){
        	addPdfPCell(table, 30, "給付別：失能年金" + StringUtils.defaultString(payCode), fontCh12, 0, 1, LEFT);
        }else if(payCode.equals("S")){
        	addPdfPCell(table, 30, "給付別：遺屬年金" + StringUtils.defaultString(payCode), fontCh12, 0, 1, LEFT);
        }else{
        	addPdfPCell(table, 30, "給付別：", fontCh12, 0, 1, LEFT);
        }
        addPdfPCell(table, 40, "", fontCh12, 0, 1, LEFT);
        addPdfPCell(table, 30, "列印日期：" + StringUtils.defaultString(DateUtility.formatChineseDateString(printDate,false)), fontCh12, 0, 1, RIGHT);
        
        addPdfPCell(table, 8, "序號", fontCh12, 1, 15, CENTER);
        addPdfPCell(table, 17, "受理編號", fontCh12, 1, 15, CENTER);
        //addPdfPCell(table, 12, "核定年月 ", fontCh12, 1, 15, CENTER);
        //addPdfPCell(table, 12, "給付年月", fontCh12, 1, 15, CENTER);
        addPdfPCell(table, 13, "受款人序", fontCh12, 1, 15, CENTER);
        addPdfPCell(table, 13, "應收金額", fontCh12, 1, 15, CENTER);
        addPdfPCell(table, 19, "退匯(退現)日期", fontCh12, 1, 15, CENTER);
        addPdfPCell(table, 19, "退匯(退現)金額", fontCh12, 1, 15, CENTER);
        addPdfPCell(table, 11, "來源", fontCh12, 1, 15, CENTER);
        
        return table;
    }
    
    public ByteArrayOutputStream doReport(String payCode, List<OtherRpt02Case> caseList) throws Exception {
        try {
            document.open();
            document.newPage();

            PdfPTable table = doReportHeader(payCode);
            
            for ( int i=0; i < caseList.size(); i++){
                addPdfPCell(table, 8, String.valueOf(i+1), fontCh12, 1,15, CENTER);
                addPdfPCell(table, 17, caseList.get(i).getApNo(), fontCh12, 1,15, CENTER);
                //String issuYm = DateUtility.changeWestYearMonthType(caseList.get(i).getIssuYm()); //核定年月
                //addPdfPCell(table, 12, DateUtility.formatChineseYearMonthString(issuYm,false), fontCh12, 1,15, CENTER);
                //String payYm = DateUtility.changeWestYearMonthType(caseList.get(i).getPayYm()); //給付年月
                //addPdfPCell(table, 12, DateUtility.formatChineseYearMonthString(payYm,false), fontCh12, 1,15, CENTER);
                addPdfPCell(table, 13, caseList.get(i).getSeqNo(), fontCh12, 1,15, CENTER);
                addPdfPCell(table, 13, formatBigDecimalToInteger(caseList.get(i).getRecRem()), fontCh12, 1,15, RIGHT);
                String brChkDate =  DateUtility.changeDateType(caseList.get(i).getBrChkDate()); //退匯（退現）日期
                addPdfPCell(table, 19,DateUtility.formatChineseDateString(brChkDate,false), fontCh12, 1,15, CENTER);
                addPdfPCell(table, 19, formatBigDecimalToInteger(caseList.get(i).getRemitAmt()), fontCh12, 1,15, RIGHT);
                addPdfPCell(table, 11, caseList.get(i).getSource(), fontCh12, 1,15, CENTER);
                
                if ((i+1)%COUNT == 0) { // 每COUNT筆換頁一次
                	document.add(table);
                	document.newPage();
                	table = doReportHeader(payCode);
                }
            }
            
            document.add(table);
            document.close();
        }
        finally {
            document.close();
        }

        return bao;
    }
}
