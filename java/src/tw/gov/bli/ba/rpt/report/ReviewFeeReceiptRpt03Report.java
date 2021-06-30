package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt10Type1PayAmtCase;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt10Type1RptCase;
import tw.gov.bli.ba.rpt.cases.ReviewFeeReceiptRpt03DataCase;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;
import tw.gov.bli.ba.util.StringUtility;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

public class ReviewFeeReceiptRpt03Report extends ReportBase {
    
    private String printDate = ""; // 印表日期
    
    public ReviewFeeReceiptRpt03Report() throws Exception {
        super();
        printDate = DateUtility.formatNowChineseDateString(true);
    }
    
    public Document createDocument() {
        // 參數說明: Document(頁面大小, 左邊空白, 右邊空白, 上面空白, 下面空白)
        Document document = new Document(PageSize.A4, 20, 20, 30, 30);
        return document;
    }
    
    /**
     * 加入空白行
     * 
     * @param table
     * @param rows 行數
     * @throws Exception
     */
    public void addEmptyRow(Table table, int rows) throws Exception {
        for (int i = 0; i < rows; i++) {
            addColumn(table, 60, 1, "　", fontCh10, 0, LEFT); // 注意: 內容是全形空白
        }
    }

    /**
     * 刪除行
     * 
     * @param table
     * @param rows 行數
     * @throws Exception
     */
    public void deleteRow(Table table, int rows) throws Exception {
        for (int i = 0; i < rows; i++) {
            table.deleteLastRow();
        }
    }
    
       
    /**
     * 建立表頭
     * 
     * @return
     * @throws Exception
     */
    public Table addHeader(ReviewFeeReceiptRpt03DataCase caseData) throws Exception {
        document.newPage();

        // 建立表格
        Table table = createTable(60);
        table.setAutoFillEmptyCells(true);
        table.setAlignment(Element.ALIGN_CENTER);
        table.setPadding(1.57f);

        addColumn(table, 15, 1, "報表編號：BARE0D042L", fontCh10, 0, LEFT);
        addColumn(table, 30, 1, RptTitleUtility.getOrgTitle(DateUtility.getNowWestDate())+"　"+RptTitleUtility.getGroupsTitle(caseData.getReApNo().substring(0, 1), DateUtility.getNowWestDate()), fontCh16, 0, CENTER);
        //addColumn(table, 30, 1, "勞工保險局  給付處", fontCh16, 0, CENTER);
        addColumn(table, 15, 1, "　", fontCh10, 0, LEFT);
        // ---

        addColumn(table, 18, 1, " ", fontCh10, 0, LEFT); 
        addColumn(table, 24, 1, "複檢費用核定清單", fontCh16, 0, CENTER);
        addColumn(table, 18, 1, " ", fontCh10, 0, LEFT);
        
        // ---
        addColumn(table, 39, 1, "核定日期：" + StringUtils.defaultString(caseData.getExeDate()), fontCh10, 0, LEFT);
        addColumn(table, 21, 1, "(印表日期： " + printDate+"第  "+writer.getCurrentPageNumber()+ "頁)", fontCh10, 0, LEFT);

        // 表格標題
        // [
        addColumn(table, 4, 2, "行次", fontCh10, 1, CENTER);
        addColumn(table, 13, 2, "複檢費用受理編號", fontCh10, 1, CENTER);
        addColumn(table, 11, 2, "金融機構轉帳帳號", fontCh10, 1, CENTER);
        addColumn(table, 10, 2, "受款人姓名", fontCh10, 1, CENTER);
        addColumn(table, 6, 2, "複檢費用   實付金額", fontCh10, 1, CENTER);
        addColumn(table, 3, 2, "申請序", fontCh10, 1, CENTER);
        addColumn(table, 4, 2, "初核  人員", fontCh10, 1, CENTER);
        addColumn(table, 9, 2, "備　　註", fontCh10, 1, CENTER);
        // ---
//        addColumn(table, 6, 1, "實付金額", fontCh10, 0, CENTER);
//        addColumn(table, 4, 1, "人員", fontCh10, 0, CENTER);

        // ]

        return table;
    }
    
       
    
    public ByteArrayOutputStream doReport(UserBean userData, List<ReviewFeeReceiptRpt03DataCase> caseList) throws Exception {
        try {

            document.open();
            // 定義 Border 樣式
            int nLine1BorderStyle = Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT;
            int nLine2BorderStyle = Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT;
            
            int recNo = 0; // 用來暫存 行次
            
            Table table = null;
            
            for (int i = 0; i < caseList.size(); i++) { // ... [
                ReviewFeeReceiptRpt03DataCase caseData = caseList.get(i);

                // 第一筆加入表頭
                if (i == 0) {
                    table = addHeader(caseData);
                }
                
                // 列印資料
                // [
                addColumnAssignVAlignment(table, 4, 2, String.valueOf(recNo + 1), fontCh10, 1, CENTER, MIDDLE); // 行次
                addColumn(table, 13, 2, caseData.getReApNoString(), fontCh10, 1, LEFT); // 複檢費用受理編號
                addColumn(table, 11, 2, ((caseData.getAccString().equals("-"))?" ":caseData.getAccString()), fontCh10, 1, LEFT); // 金融機構轉帳帳號
                addColumn(table, 10, 2, caseData.getBenName(), fontCh10, 1, LEFT); // 受款人姓名
                addColumn(table, 6, 1, formatBigDecimalToInteger(((caseData.getReFees() == null) ? new BigDecimal(0) : caseData.getReFees())), fontCh10, 1, nLine1BorderStyle, RIGHT); // 複檢費用
                addColumn(table, 3, 2, formatBigDecimalToInteger(caseData.getApSeq()), fontCh10, 1, CENTER); // 申請序
                addColumn(table, 4, 2, "　", fontCh10, 1, nLine1BorderStyle, LEFT); // 初核人員
                addColumn(table, 9, 2, " ",fontCh10, 1, LEFT); //備註
                // ---
                addColumn(table, 6, 1, formatBigDecimalToInteger(((caseData.getReAmtPay() == null) ? new BigDecimal(0) : caseData.getReAmtPay())), fontCh10, 1, nLine2BorderStyle, RIGHT); // 實付金額
              
                // ]
                
             // 判段是否為最後一筆, 或達成換頁條件 ()
                if (i == caseList.size() - 1 ) {
                    // 塞滿空白行
                    // [
                    while (writer.fitsPage(table)) {
                        // [
                        addColumn(table, 4, 2, "　", fontCh10, 1, CENTER);
                        addColumn(table, 13, 2, "　", fontCh10, 1, LEFT);
                        addColumn(table, 11, 2, "　", fontCh10, 1, LEFT);
                        addColumn(table, 10, 2, " ", fontCh10, 1, CENTER);
                        addColumn(table, 6, 1, "　", fontCh10, 1, nLine1BorderStyle, RIGHT);
                        addColumn(table, 3, 2, "　", fontCh10, 1, RIGHT);
                        addColumn(table, 4, 2, "　", fontCh10, 1, LEFT);
                        addColumn(table, 9, 2, "　", fontCh10, 1, LEFT);
                        // ---
                        
                        addColumn(table, 6, 1, "　", fontCh10, 1, nLine2BorderStyle, RIGHT);
                        
                        // ]
                    }
                    // ]
                    deleteRow(table, 8);
                    
                    // -- 
                    addEmptyRow(table, 1);
                    addColumn(table, 60, 1, "借：保險給付-普通（職災）複檢費用", fontCh10, 0, LEFT);
                    // -- 
                    addColumn(table, 3, 1, " ", fontCh10, 0, LEFT);
                    addColumn(table, 57, 1, "貸：銀行存款", fontCh10, 0, LEFT);
                    addEmptyRow(table, 2);
                    
                    // --
                    addColumn(table, 30, 1, RptTitleUtility.getDivisionTitle(caseData.getReApNo().substring(0, 1), DateUtility.getNowWestDate()), fontCh10, 0, LEFT);
                    //addColumn(table, 30, 1, "失能給付科", fontCh10, 0, LEFT);
                    addColumn(table, 30, 1, RptTitleUtility.getSafetyPaymentTitle3(DateUtility.getNowWestDate()), fontCh10, 0, LEFT);
                    //addColumn(table, 30, 1, "出納", fontCh10, 0, LEFT);
                    addEmptyRow(table, 1);
                    document.add(table);
                    
                    if (i != caseList.size() - 1)
                        table = addHeader(caseList.get(i + 1)); // 下一筆
                    
                }else {
                    // 測試塞下一筆 + 表尾時是否需要換頁
                    
                    // [
                    addColumnAssignVAlignment(table, 4, 2, String.valueOf(recNo + 1), fontCh10, 1, CENTER, MIDDLE); // 行次
                    addColumn(table, 13, 2, caseData.getReApNoString(), fontCh10, 1, LEFT); // 複檢費用受理編號
                    addColumn(table, 11, 2, ((caseData.getAccString().equals("-"))?" ":caseData.getAccString()), fontCh10, 1, LEFT); // 金融機構轉帳帳號
                    addColumn(table, 10, 2, caseData.getBenName(), fontCh10, 1, LEFT); // 受款人姓名
                    addColumn(table, 6, 1, formatBigDecimalToInteger(((caseData.getReFees() == null) ? new BigDecimal(0) : caseData.getReFees())), fontCh10, 1, nLine1BorderStyle, RIGHT); // 複檢費用
                    addColumn(table, 3, 2, formatBigDecimalToInteger(caseData.getApSeq()), fontCh10, 1, CENTER); // 申請序
                    addColumn(table, 4, 2, "　", fontCh10, 1, LEFT); // 初核人員
                    addColumn(table, 9, 2, " ",fontCh10, 1, LEFT); //備註
                    // ---
                    addColumn(table, 6, 1, formatBigDecimalToInteger(((caseData.getReAmtPay() == null) ? new BigDecimal(0) : caseData.getReAmtPay())), fontCh10, 1, nLine2BorderStyle, RIGHT); // 實付金額
                    
                    // 表尾
                    // -- 
                    addEmptyRow(table, 1);
                    addColumn(table, 60, 1, "借：保險給付-普通（職災）複檢費用", fontCh10, 0, LEFT);
                    // -- 
                    addColumn(table, 3, 1, " ", fontCh10, 0, LEFT);
                    addColumn(table, 57, 1, "貸：銀行存款", fontCh10, 0, LEFT);
                    addEmptyRow(table, 2);
                    
                    // --
                    addColumn(table, 30, 1, RptTitleUtility.getDivisionTitle(caseData.getReApNo().substring(0, 1), DateUtility.getNowWestDate()), fontCh10, 0, LEFT);
                    //addColumn(table, 30, 1, "失能給付科", fontCh10, 0, LEFT);
                    addColumn(table, 30, 1, RptTitleUtility.getSafetyPaymentTitle3(DateUtility.getNowWestDate()), fontCh10, 0, LEFT);
                    //addColumn(table, 30, 1, "出納", fontCh10, 0, LEFT);
                    addEmptyRow(table, 1);
                    
                    // ]
                    
                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        deleteRow(table, 9);
                        
                        // 表尾
                        // -- 
                        addEmptyRow(table, 1);
                        addColumn(table, 60, 1, "借：保險給付-普通（職災）複檢費用", fontCh10, 0, LEFT);
                        // -- 
                        addColumn(table, 3, 1, " ", fontCh10, 0, LEFT);
                        addColumn(table, 57, 1, "貸：銀行存款", fontCh10, 0, LEFT);
                        addEmptyRow(table, 2);
                        
                        // --
                        addColumn(table, 30, 1, RptTitleUtility.getDivisionTitle(caseData.getReApNo().substring(0, 1), DateUtility.getNowWestDate()), fontCh10, 0, LEFT);
                        //addColumn(table, 30, 1, "失能給付科", fontCh10, 0, LEFT);
                        addColumn(table, 30, 1, RptTitleUtility.getSafetyPaymentTitle3(DateUtility.getNowWestDate()), fontCh10, 0, LEFT);
                        //addColumn(table, 30, 1, "出納", fontCh10, 0, LEFT);
                        addEmptyRow(table, 1);
                        
                        document.add(table);

                        // 編號 + 1
                        recNo++;
                        
                    }else {
                        // 編號 + 1
                        recNo++;

                        deleteRow(table, 9);
                    }
                }
            }
            document.close();
        }
        finally {
            document.close();
        }

    return bao;
        
    }

}
