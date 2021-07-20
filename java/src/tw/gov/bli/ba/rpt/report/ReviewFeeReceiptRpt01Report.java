package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01BenDataCase;
import tw.gov.bli.ba.rpt.cases.ReviewFeeReceiptRpt01DataCase;
import tw.gov.bli.ba.rpt.cases.ReviewFeeReceiptNotifyDataCase;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ValidateUtility;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Table;

/**
 * 複檢費用受理審核清單
 * 
 * @author Evelyn Hsu
 */

public class ReviewFeeReceiptRpt01Report extends ReportBase {
    
    private int nPage; // 每筆資料的頁數

    private String printDate = ""; // 印表日期

    private String line = "---------------------------------------------------------------------------------------------------------------------------------------------";

    
    public ReviewFeeReceiptRpt01Report() throws Exception {
        super();
        nPage = 0;
        printDate = DateUtility.formatNowChineseDateString(false);
    }
    
    public Document createDocument() {
        // 參數說明: Document(頁面大小, 左邊空白, 右邊空白, 上面空白, 下面空白)
        Document document = new Document(PageSize.A4, 10, 4, 20, 20);
        return document;
    }
    
    /**
     * 加入分隔線
     * 
     * @param table
     * @param icolspan Column Span
     * @throws Exception
     */
    public void addLine(Table table) throws Exception {
        addColumn(table, 60, 1, line, fontCh8, 0, LEFT);
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
            addColumn(table, 60, 1, "　", fontCh12, 0, LEFT); // 注意: 內容是全形空白
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
     * @param payListSize 事故者 給付資料 筆數
     * @param attached 是否為附表
     * @return
     * @throws Exception
     */
    public Table addHeader(ReviewFeeReceiptRpt01DataCase caseData, boolean attached) throws Exception {
        document.newPage();
        // 建立表格
        Table table = createTable(60);
        table.setAutoFillEmptyCells(true);
        table.setAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        addColumn(table, 12, 1, " ", fontCh12, 0, LEFT);
        addColumn(table, 35, 1, "勞保失能給付複檢費用受理/審核清單", fontCh18, 0, CENTER);
        addColumn(table, 13, 1, "頁次：" + StringUtils.leftPad(String.valueOf(writer.getCurrentPageNumber() - nPage), 3, "0"), fontCh12, 0, LEFT);
        // 第 ? 頁 / 共 ? 頁 中的 第 ? 頁 部份
        // [
        // addColumn(table, 15, 1, " ", fontCh12, 0, LEFT);
        // createPageText(500, 800, writer.getCurrentPageNumber() - nPage,"頁次：", " / ", 12, 150, 50);
        // ]
        // ---
        addColumn(table, 15, 1, " ", fontCh12, 0, LEFT);
        addColumn(table, 30, 1, " ", fontCh12, 0, CENTER);
        addColumn(table, 15, 1, "印表日期：" + printDate, fontCh12, 0, LEFT);
        // ---
        addColumn(table, 20, 1, "申請序：" + caseData.getApSeq(), fontCh12, 0, LEFT);
        addColumn(table, 40, 1, " ", fontCh12, 0, LEFT);
        
        // ---
        addColumn(table, 20, 1, "複檢費用申請日期：" + DateUtility.changeDateType(caseData.getRecosDate()), fontCh12, 0, LEFT);
        addColumn(table, 20, 1, "複檢費用受理編號：" + caseData.getReApNo(), fontCh12, 0, LEFT);
        addBarcode39NoLabel(table, caseData.getReApNo(), 75, 75, -5, 20, 1, 0, CENTER, MIDDLE);
       
        // ---
        addLine(table);
        // ---
        addColumn(table, 9, 1, caseData.getEvtName(), fontCh16, 0, LEFT);
        addColumn(table, 4, 1, StringUtils.defaultString(caseData.getEvtAge()) + "歲", fontCh16, 8, 0, LEFT);
        addColumn(table, 3, 1, " ", fontCh16, 0, LEFT);
        addColumn(table, 21, 1, caseData.getEvtBrDateString(), fontCh16, 0, LEFT);
        addColumn(table, 9, 1, caseData.getEvtIdnNo(), fontCh16, 0, LEFT);
        addColumn(table, 2, 1, ValidateUtility.validatePersonIdNo(caseData.getEvtIdnNo()) ? "" : "W", fontCh16, 0, LEFT);
        addBarcode39NoLabel(table, StringUtils.defaultString(caseData.getEvtIdnNo()), 75, 75, -5, 12, 1, 0, LEFT, MIDDLE);
        // ---
        addLine(table);

        return table;
    }
    
    public ByteArrayOutputStream doReport(UserBean userData, List<ReviewFeeReceiptRpt01DataCase> caseList) throws Exception {
        try {
            document.open();
            for (int index = 0; index < caseList.size(); index++) {
             // 頁次處理
                if (index > 0) {
                    nPage = writer.getPageNumber();
                }

                ReviewFeeReceiptRpt01DataCase caseData = (ReviewFeeReceiptRpt01DataCase) caseList.get(index);

                // 表頭
                Table table = addHeader(caseData, false);

                // 複核費用資料
                // [
                addColumn(table, 27, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 11, 1, "審核", fontCh12, 0, CENTER);
                addColumn(table, 11, 1, "覆核", fontCh12, 0, CENTER);
                addColumn(table, 11, 1, "總核", fontCh12, 0, CENTER);
                
                // ---
                addColumn(table, 11, 1, "複檢費用：", fontCh12, 0, LEFT);
                addColumn(table, 9, 1, formatBigDecimalToInteger(caseData.getReFees()), fontCh12, 0, RIGHT); // 複檢費用
                addColumn(table, 40, 1, " ", fontCh12, 0, LEFT);
                
                // ---
                addColumn(table, 11, 1, "非複檢必要費用：", fontCh12, 0, LEFT);
                addColumn(table, 9, 1, formatBigDecimalToInteger(caseData.getNonreFees()), fontCh12, 0, RIGHT); // 非複檢必要費用
                addColumn(table, 40, 1, " ", fontCh12, 0, LEFT);
                
                // ---
                addColumn(table, 11, 1, "複檢實付金額：", fontCh12, 0, LEFT);
                addColumn(table, 9, 1, formatBigDecimalToInteger(caseData.getReAmtPay()), fontCh12, 0, RIGHT); // 複檢實付金額
                addColumn(table, 40, 1, " ", fontCh12, 0, LEFT);
                // 分隔線
                addLine(table);
                // ]
                
                // 現金給付醫院
                // [
                
                // ---
                addColumn(table, 12, 1, "複檢醫院代碼：", fontCh12, 0, LEFT);
                addColumn(table, 13, 1, caseData.getHosId(), fontCh12, 0, RIGHT); // 複檢醫院代碼
                addColumn(table, 35, 1, " ", fontCh12, 0, LEFT);
                
                // ---
                addColumn(table, 12, 1, "複檢醫院名稱：", fontCh12, 0, LEFT);
                addColumn(table, 23, 1, caseData.getHosName(), fontCh12, 0, RIGHT); // 複檢醫院名稱
                addColumn(table, 25, 1, " ", fontCh12, 0, LEFT);
                // 分隔線
                addLine(table);
                // ]
                
                // 受款人資料
                // [
                addColumn(table, 60, 1, "受理鍵入資料及修改紀錄：（鍵入／更正人員代號：" + ((StringUtils.isNotBlank(caseData.getUpdUser())) ? caseData.getUpdUser() : StringUtils.defaultString(caseData.getCrtUser())) + "）", fontCh12, 0, LEFT);
                
                // 塞入一行空白行
                addEmptyRow(table, 1);
                // ---
                addColumn(table, 60, 1, "受款人姓名：" + StringUtils.defaultString(caseData.getBenName()), fontCh12, 0, LEFT);
                // ---
                addColumn(table, 16, 1, "身分證號：" + StringUtils.defaultString(caseData.getBenIdnNo()), fontCh12, 0, LEFT);
                addColumn(table, 16, 1, "出生日期：" + StringUtils.defaultString(caseData.getBenBrDateString()), fontCh12, 0, LEFT);
                addColumn(table, 28, 1, "電話：" + StringUtils.defaultString(caseData.getTel1())
                                + ((StringUtils.isNotBlank(caseData.getTel2()) && StringUtils.isNotBlank(caseData.getTel1())) ? ("、" + StringUtils.defaultString(caseData.getTel2())) : StringUtils.defaultString(caseData.getTel2())), fontCh12, 0, LEFT);
                
                // ---
                // 帳號處理
                String strEvtAccountNo = StringUtils.defaultString(caseData.getPayBankId());
                if (StringUtils.isNotBlank(caseData.getBranchId()))
                    strEvtAccountNo = strEvtAccountNo + "-" + caseData.getBranchId();
                if (StringUtils.isNotBlank(caseData.getPayeeAcc()))
                    strEvtAccountNo = strEvtAccountNo + "-" + caseData.getPayeeAcc();

                addColumn(table, 7, 1, "關係：" + StringUtils.defaultString(caseData.getBenEvtRel()), fontCh12, 0, LEFT);
                addColumn(table, 9, 1, "給付方式：" + StringUtils.defaultString(caseData.getPayTyp()), fontCh12, 0, LEFT);
                addColumn(table, 25, 1, "帳號：" + strEvtAccountNo + " " + StringUtils.defaultString(caseData.getBankName()), fontCh12, 0, LEFT);
                addColumn(table, 19, 1, "戶名：" + StringUtils.defaultString(caseData.getAccName()), fontCh12, 0, LEFT);
                
                // ---
                addColumn(table, 10, 1, "複檢實付金額：", fontCh12, 0, LEFT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(caseData.getReAmtPay()), fontCh12, 0, RIGHT); // 複檢實付金額
                addColumn(table, 42, 1, " ", fontCh12, 0, LEFT);
                
                // ---
                addColumn(table, 60, 1, "地址：" + StringUtils.defaultString(caseData.getCommZip()) + " " + StringUtils.defaultString(caseData.getCommAddr()), fontCh12, 0, LEFT);
                
                // ---
                if (StringUtils.isNotBlank(caseData.getGrdName()) || StringUtils.isNotBlank(caseData.getGrdIdnNo()) || StringUtils.isNotBlank(caseData.getGrdBrDate())) {
                    addColumn(table, 16, 1, "法定代理人姓名：" + StringUtils.defaultString(caseData.getGrdName()), fontCh12, 0, LEFT);
                    addColumn(table, 16, 1, "身分證號：" + StringUtils.defaultString(caseData.getGrdIdnNo()), fontCh12, 0, LEFT);
                    addColumn(table, 28, 1, "出生日期：" + StringUtils.defaultString(caseData.getGrdBrDateString()), fontCh12, 0, LEFT);
                }
                // 在塞分隔線前, 先隨便塞空白行測試是否需換頁
                addEmptyRow(table, 1);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // 換了頁就不印分隔線了
                    deleteRow(table, 1);
                    document.add(table);
                    table = addHeader(caseData, false);
                }
                else {
                    deleteRow(table, 1);
                    addLine(table);
                }                
                
                // ]
                
              
                // 核定通知書
                // [
                if (caseData.getNotifyData() != null) {
                    ReviewFeeReceiptNotifyDataCase notifyData = caseData.getNotifyData();

                    // 受文者
                    StringBuffer receiveName = new StringBuffer("");
                    if (StringUtils.isNotBlank(caseData.getBenName()))
                        receiveName.append(caseData.getBenName());

                 // 先試印核定通知書的 標題 受文者 地址 主旨 及 說明的第一點, 測試是否需換頁
                    // [
                    addColumn(table, 30, 1, "核 定 通 知 書", fontCh12l, 0, RIGHT); // 有底線
                    addColumn(table, 14, 1, "（稿）", fontCh12, 0, LEFT);
                    addColumn(table, 16, 1, ((StringUtils.isNotBlank(caseData.getNotifyForm())) ? ("核定格式：" + caseData.getNotifyForm()) : ""), fontCh12, 0, LEFT); // 核定格式 - 有值才顯示
                    // ---
                    addEmptyRow(table, 1);
                    // ---
                    addColumnAssignVAlignmentAndLineSpace(table, 6, 1, "受文者：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                    addColumnAssignVAlignmentAndLineSpace(table, 54, 1, receiveName.toString(), fontCh12, 8, 0, JUSTIFIED, TOP); // 受文者
                    // ---
                    addColumnAssignVAlignmentAndLineSpace(table, 5, 1, "地址：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                    addColumnAssignVAlignmentAndLineSpace(table, 55, 1, (StringUtils.defaultString(caseData.getCommZip()) + " " + StringUtils.defaultString(caseData.getCommAddr())).trim(), fontCh12, 8, 0, JUSTIFIED, TOP); // 受文者
                    // ---
                    addColumnAssignVAlignmentAndLineSpace(table, 5, 1, "主旨：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                    addColumnAssignVAlignmentAndLineSpace(table, 54, 1, notifyData.getSubject(), fontCh12, 8, 0, JUSTIFIED, TOP); // 主旨
                    addColumnAssignVAlignmentAndLineSpace(table, 1, 1, " ", fontCh12, 8, 0, JUSTIFIED, TOP);
                    // ---
                    if (notifyData.getContent().size() > 0) {
                        addColumnAssignVAlignmentAndLineSpace(table, 5, 1, "說明：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                        addColumnAssignVAlignmentAndLineSpace(table, 54, 1, notifyData.getContent().get(0), fontCh12, 8, 0, JUSTIFIED, TOP); // 說明
                        addColumnAssignVAlignmentAndLineSpace(table, 1, 1, " ", fontCh12, 8, 0, JUSTIFIED, TOP);
                    }

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        if (notifyData.getContent().size() > 0)
                            deleteRow(table, 6);
                        else
                            deleteRow(table, 5);
                        document.add(table);
                        table = addHeader(caseData, false);
                    }
                    else {
                        if (notifyData.getContent().size() > 0)
                            deleteRow(table, 6);
                        else
                            deleteRow(table, 5);
                    }
                    // ]

                    // 正式印核定通知書
                    addColumn(table, 30, 1, "核 定 通 知 書", fontCh12l, 0, RIGHT); // 有底線
                    addColumn(table, 14, 1, "（稿）", fontCh12, 0, LEFT);
                    addColumn(table, 16, 1, ((StringUtils.isNotBlank(caseData.getNotifyForm())) ? ("核定格式：" + caseData.getNotifyForm()) : ""), fontCh12, 0, LEFT); // 核定格式 - 有值才顯示
                    // ---
                    addEmptyRow(table, 1);
                    // ---
                    addColumnAssignVAlignmentAndLineSpace(table, 6, 1, "受文者：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                    addColumnAssignVAlignmentAndLineSpace(table, 54, 1, receiveName.toString(), fontCh12, 8, 0, JUSTIFIED, TOP); // 受文者
                    // ---
                    addColumnAssignVAlignmentAndLineSpace(table, 5, 1, "地址：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                    addColumnAssignVAlignmentAndLineSpace(table, 55, 1, (StringUtils.defaultString(caseData.getCommZip()) + " " + StringUtils.defaultString(caseData.getCommAddr())).trim(), fontCh12, 8, 0, JUSTIFIED, TOP); // 受文者
                    // ---
                    addColumnAssignVAlignmentAndLineSpace(table, 5, 1, "主旨：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                    addColumnAssignVAlignmentAndLineSpace(table, 54, 1, notifyData.getSubject(), fontCh12, 8, 0, JUSTIFIED, TOP); // 主旨
                    addColumnAssignVAlignmentAndLineSpace(table, 1, 1, " ", fontCh12, 8, 0, JUSTIFIED, TOP);
                    // ---
                    // 說明 的處理
                    for (int nContentCount = 0; nContentCount < notifyData.getContent().size(); nContentCount++) {
                        String content = notifyData.getContent().get(nContentCount);

                        if (nContentCount == 0)
                            addColumnAssignVAlignmentAndLineSpace(table, 5, 1, "說明：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                        else
                            addColumnAssignVAlignmentAndLineSpace(table, 5, 1, " ", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                        addColumnAssignVAlignmentAndLineSpace(table, 3, 1, content.substring(0, content.indexOf("、") + 1), fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                        addColumnAssignVAlignmentAndLineSpace(table, 51, 1, content.substring(content.indexOf("、") + 1, content.length()), fontCh12, 8, 0, JUSTIFIED, TOP); // 說明
                        addColumnAssignVAlignmentAndLineSpace(table, 1, 1, " ", fontCh12, 8, 0, JUSTIFIED, TOP);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false);

                            // 換了頁要再印一次
                            if (nContentCount == 0)
                                addColumnAssignVAlignmentAndLineSpace(table, 5, 1, "說明：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                            else
                                addColumnAssignVAlignmentAndLineSpace(table, 5, 1, " ", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                            addColumnAssignVAlignmentAndLineSpace(table, 3, 1, content.substring(0, content.indexOf("、") + 1), fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                            addColumnAssignVAlignmentAndLineSpace(table, 51, 1, content.substring(content.indexOf("、") + 1, content.length()), fontCh12, 8, 0, JUSTIFIED, TOP); // 說明
                            addColumnAssignVAlignmentAndLineSpace(table, 1, 1, " ", fontCh12, 8, 0, JUSTIFIED, TOP);
                        }
                    }

                    
                }
                // 20080218 新增無核定資料要印標題
                else {
                     //先試印核定通知書的 標題測試是否需換頁
                     //[
                    addColumn(table, 30, 1, "核 定 通 知 書", fontCh12l, 0, RIGHT); // 有底線
                    addColumn(table, 14, 1, "（稿）", fontCh12, 0, LEFT);
                    addColumn(table, 16, 1, ((StringUtils.isNotBlank(caseData.getNotifyForm())) ? ("核定格式：" + caseData.getNotifyForm()) : ""), fontCh12, 0, LEFT); // 核定格式 - 有值才顯示
                    // ---
                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        deleteRow(table, 1);
                        document.add(table);
                        table = addHeader(caseData, false);
                    }
                    else {
                        deleteRow(table, 1);
                    }

                    // 正式印核定通知書
                    addColumn(table, 30, 1, "核 定 通 知 書", fontCh12l, 0, RIGHT); // 有底線
                    addColumn(table, 14, 1, "（稿）", fontCh12, 0, LEFT);
                    addColumn(table, 16, 1, ((StringUtils.isNotBlank(caseData.getNotifyForm())) ? ("核定格式：" + caseData.getNotifyForm()) : ""), fontCh12, 0, LEFT); // 核定格式 - 有值才顯示
                    
                }
                // ]
                // 核定通知書

                

                
                document.add(table);
                
                // ]
                
            }
            document.close();
            
        }
        finally {
            document.close();
        }
        
        return bao;
    }

}
