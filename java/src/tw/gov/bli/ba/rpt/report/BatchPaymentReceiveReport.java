package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.io.FileOutputStream;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.common.helper.SpringHelper;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.rpt.cases.BatchPaymentReceiveDataCase;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt15Case;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.BaBusinessUtility;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;
import tw.gov.bli.ba.util.StringUtility;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

/**
 * 整批收回應收已收清冊
 * 
 * @author Kiyomi
 */
public class BatchPaymentReceiveReport extends ReportBase {

    private RptService rptService;
    
    private Integer pageNum = 1; // 頁數
    private Integer pageNum1 = 1; // 頁數 (another report)
    private Integer pageNum2 = 1; // 頁數 (failure report)

    public BatchPaymentReceiveReport() throws Exception {
        super();
    }

    public Document createDocument() {
        Document document = new Document(PageSize.A4.rotate(), 30, 30, 30, 30);
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
            addColumn(table, 3, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 7, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 3, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 11, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 5, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 5, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 4, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 4, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 4, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 4, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 4, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 4, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 4, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 4, 1, "　", fontCh10, 1, CENTER);
        }
    }
    
    /**
     * 加入空白行 (Failure Report)
     * 
     * @param table
     * @param rows 行數
     * @throws Exception
     */
    public void addEmptyRowForFailureReport(Table table, int rows) throws Exception {
        for (int i = 0; i < rows; i++) {
            addColumn(table, 66, 1, "　", fontCh12, 0, LEFT); // 注意: 內容是全形空白
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

    public Table getHeader(boolean bPrintMainHeader, String payCode, String mchkTypStr, String payTypStr, String payYm, String issuYm, String payTyp, HashMap<String, Object> map, String cPrnDate, String naChgMk, String nlWkMk, String adWkMk, String isNaChgMk, String receiveType, BigDecimal rptPage, Integer pageNum) throws Exception {
        document.newPage();
        // 建立表格
        Table table = createTable(66);
        table.setAutoFillEmptyCells(true);

        addColumn(table, 66, 1, " ", fontCh8, 0, CENTER);

        // 列印大標題
        if (bPrintMainHeader) {
            addColumn(table, 52, 1, "報表編號：BALP0D690L", fontCh10, 0, LEFT);
            addColumn(table, 14, 1, "", fontCh10, 0, LEFT);

            addColumn(table, 14, 1, "", fontCh10, 0, CENTER);
            addColumn(table, 38, 1, RptTitleUtility.getOrgTitle(DateUtility.getNowWestDate()) + "　" + RptTitleUtility.getGroupsTitle(payCode, DateUtility.getNowWestDate()), fontCh18, 0, CENTER);
            // addColumn(table, 38, 1, "勞工保險局　給付處", fontCh18, 0, CENTER);
            addColumn(table, 14, 1, "", fontCh10, 0, LEFT);

            addColumn(table, 14, 1, "", fontCh10, 0, CENTER);
            addColumn(table, 38, 1, "整批收回應收已收清冊", fontCh18, 0, CENTER);
            addColumn(table, 14, 1, "", fontCh10, 0, CENTER);

            String payCodeStr = "";
            String isNaChgMkStr = "";

            if ("Y".equals(isNaChgMk)) {
                if ("26".equals(naChgMk)) {
                    isNaChgMkStr = " - 普改職";
                }
                else if ("13".equals(naChgMk)) {
                    isNaChgMkStr = " - 普改加職";
                }
                else if ("21".equals(naChgMk)) {
                    isNaChgMkStr = " - 職改普";
                }
                else if ("23".equals(naChgMk)) {
                    isNaChgMkStr = " - 職改加職";
                }
                else if ("31".equals(naChgMk)) {
                    isNaChgMkStr = " - 加職改普";
                }
                else if ("32".equals(naChgMk)) {
                    isNaChgMkStr = " - 加職改職";
                }
            }
            else if ("N".equals(isNaChgMk)) {
                isNaChgMkStr = "";
            }

            if (("L").equals(payCode)) {
                payCodeStr = "普通-老年給付(年金)";
            }
            else if (("K").equals(payCode)) {
                if (("1").equals(nlWkMk)) {
                    payCodeStr = "普通-失能給付(年金)" + isNaChgMkStr;
                }
                else if (("2").equals(nlWkMk) && ("1").equals(adWkMk)) {
                    payCodeStr = "職災-失能給付(年金)" + isNaChgMkStr;
                }
                else if (("2").equals(nlWkMk) && ("2").equals(adWkMk)) {
                    payCodeStr = "加職-失能給付(年金)" + isNaChgMkStr;
                }
                else {
                    payCodeStr = "普通-失能給付(年金)";
                }

            }
            else if (("S").equals(payCode)) {
                if (("1").equals(nlWkMk)) {
                    payCodeStr = "普通-遺屬給付(年金)";
                }
                else if (("2").equals(nlWkMk) && ("1").equals(adWkMk)) {
                    payCodeStr = "職災-遺屬給付(年金)";
                }
                else if (("2").equals(nlWkMk) && ("2").equals(adWkMk)) {
                    payCodeStr = "加職-遺屬給付(年金)";
                }
                else {
                    payCodeStr = "普通-遺屬給付(年金)";
                }
            }

            addColumn(table, 58, 1, "給 付 別：" + payCodeStr, fontCh10, 0, LEFT);
            addColumn(table, 8, 1, "給付方式：" + payTypStr, fontCh10, 0, LEFT);

            addColumn(table, 30, 1, "核定年月：" + DateUtility.changeWestYearMonthType(issuYm), fontCh10, 0, LEFT);
            addColumn(table, 28, 1, "印表日期：" + DateUtility.formatChineseDateTimeString(cPrnDate, false), fontCh10, 0, LEFT);
            addColumn(table, 8, 1, "頁　　次：" + rptPage, fontCh10, 0, LEFT);
//            addColumn(table, 8, 1, "頁　　次：" + pageNum.toString(), fontCh10, 0, LEFT);

        }

        addColumn(table, 3, 1, "序號", fontCh9, 1, CENTER);
        addColumn(table, 7, 1, "受理編號", fontCh9, 1, CENTER);
        addColumn(table, 3, 1, "給付\n年月", fontCh9, 1, CENTER);
        if (payTyp.equals(ConstantKey.BAAPPBASE_PAYTYP_A))
            addColumn(table, 11, 1, "受款人身份證字號", fontCh9, 1, CENTER);
        else
            addColumn(table, 11, 1, "金融機構轉帳帳號", fontCh9, 1, CENTER);
        addColumn(table, 5, 1, "受款人\n姓　名", fontCh9, 1, CENTER);
        addColumn(table, 5, 1, "核定金額(A)", fontCh9, 1, CENTER);
        addColumn(table, 4, 1, "另案扣減同保險(B)", fontCh9, 1, CENTER);
        addColumn(table, 4, 1, "另案扣減他保險(C)", fontCh9, 1, CENTER);
        addColumn(table, 4, 1, "匯費(D)", fontCh9, 1, CENTER);
        addColumn(table, 4, 1, "抵銷紓困(E)", fontCh9, 1, CENTER);
        addColumn(table, 4, 1, "代扣補償金(F)", fontCh9, 1, CENTER);
        addColumn(table, 4, 1, "保留遺屬津貼(G)", fontCh9, 1, CENTER);
        addColumn(table, 4, 1, "代扣利息所得稅(H)", fontCh9, 1, CENTER);
        addColumn(table, 4, 1, "其他(I)", fontCh9, 1, CENTER);

        return table;
    }

    public void addFooter(Table table, String DeptName, List<BatchPaymentReceiveDataCase> amtList, Integer rowCount, List<BatchPaymentReceiveDataCase> caseList, String issuTyp, String payTyp, Integer rowBeg, Integer rowEnd) throws Exception {
        Integer sumIssueAmt = 0;
        Integer sumOtheraAmt = 0;
        Integer sumOtherbAmt = 0;
        Integer sumMitRate = 0;
        Integer sumOffsetAmt = 0;
        Integer sumCompenAmt = 0;
        Integer sumInherItorAmt = 0;
        Integer sumItrtTax = 0;
        Integer sumOtherAmt = 0;

        Integer allIssueAmt = 0;
        Integer allOtheraAmt = 0;
        Integer allOtherbAmt = 0;
        Integer allMitRate = 0;
        Integer allOffsetAmt = 0;
        Integer allCompenAmt = 0;
        Integer allInherItorAmt = 0;
        Integer allItrtTax = 0;
        Integer allOtherAmt = 0;

        // 本頁小計
        for (int i = 0; i < rowCount; i++) {
            BatchPaymentReceiveDataCase amtData = amtList.get(i);
            sumIssueAmt += amtData.getIssueAmt().intValue();
            sumOtheraAmt += amtData.getOtheraAmt().intValue();
            sumOtherbAmt += amtData.getOtherbAmt().intValue();
            sumMitRate += amtData.getMitRate().intValue();
            sumOffsetAmt += amtData.getOffsetAmt().intValue();
            sumCompenAmt += amtData.getCompenAmt().intValue();
            sumInherItorAmt += amtData.getInherItorAmt().intValue();
            sumItrtTax += amtData.getItrtTax().intValue();
            sumOtherAmt += amtData.getOtherAmt().intValue();
        }
        // 總累計
        for (int j = rowBeg; j < rowEnd; j++) {
            BatchPaymentReceiveDataCase caseData = caseList.get(j);
            if (caseData.getIssuTyp().equals(issuTyp) && caseData.getPayTyp().equals(payTyp)) {
                allIssueAmt += caseData.getIssueAmt().intValue();
                allOtheraAmt += caseData.getOtheraAmt().intValue();
                allOtherbAmt += caseData.getOtherbAmt().intValue();
                allMitRate += caseData.getMitRate().intValue();
                allOffsetAmt += caseData.getOffsetAmt().intValue();
                allCompenAmt += caseData.getCompenAmt().intValue();
                allInherItorAmt += caseData.getInherItorAmt().intValue();
                allItrtTax += caseData.getItrtTax().intValue();
                allOtherAmt += caseData.getOtherAmt().intValue();
            }
        }

        // 清空暫存
        amtList.clear();
        rowCount = 0;

        addColumn(table, 24, 0, "", fontCh9, 0, CENTER);
        addColumn(table, 5, 1, "本頁小計", fontCh9, 1, CENTER);
        addColumn(table, 5, 1, ReportBase.formatNumber(sumIssueAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(sumOtheraAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(sumOtherbAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(sumMitRate), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(sumOffsetAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(sumCompenAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(sumInherItorAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(sumItrtTax), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(sumOtherAmt), fontCh8, 1, RIGHT);

        addColumn(table, 24, 0, RptTitleUtility.getSafetyPaymentTitle1(DateUtility.getNowWestDate()), fontCh9, 0, LEFT);
        // addColumn(table, 24, 0, "給付出納科", fontCh9, 0, LEFT);
        addColumn(table, 5, 1, "總(累)計", fontCh9, 1, CENTER);
        addColumn(table, 5, 1, ReportBase.formatNumber(allIssueAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(allOtheraAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(allOtherbAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(allMitRate), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(allOffsetAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(allCompenAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(allInherItorAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(allItrtTax), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(allOtherAmt), fontCh8, 1, RIGHT);
    }

    public Table doReport(UserBean userData, List<BatchPaymentReceiveDataCase> caseData, HashMap<String, Object> map) throws Exception {
        try {

            document.newPage();

            String payCode = (String) map.get("payCode");
            String receiveType = (String) map.get("receiveType");

            Table table = null;

            String issuTyp = null; // 案件類別
            String payTyp = null; // 給付方式
            String deptName = null; // 部門名稱
            String apNoLastDigit = null; // 受理編號的最後一碼

            List<BatchPaymentReceiveDataCase> amtList = new ArrayList<BatchPaymentReceiveDataCase>();

            // 序號
            Integer seqNo = 1;
            // 計算每頁筆數
            Integer rowCount = 0;
            // 累計金額起始
            Integer rowBeg = 0;

            for (int i = 0; i < caseData.size(); i++) { // 受理案件資料
                BatchPaymentReceiveDataCase batchPaymentReceiveCase = caseData.get(i);
                BigDecimal rptPage = batchPaymentReceiveCase.getRptPage();
                if (i == 0) {
                    // 取得表頭
                    table = getHeader(true, payCode, batchPaymentReceiveCase.getIssuTypStr(), batchPaymentReceiveCase.getPayTypStr(), batchPaymentReceiveCase.getPayYm(), batchPaymentReceiveCase.getIssuYm(), batchPaymentReceiveCase.getPayTyp(), map, batchPaymentReceiveCase.getcPrnDateStr(), batchPaymentReceiveCase.getNaChgMk(), batchPaymentReceiveCase.getNlWkMk(), batchPaymentReceiveCase.getAdWkMk(), batchPaymentReceiveCase.getIsNaChgMk(), receiveType, rptPage, pageNum);
                }
                else {
                    // if(((rowCount) % 20 == 0) || !(batchPaymentReceiveCase.getIssuTyp()).equals(issuTyp) || !(batchPaymentReceiveCase.getPayTyp()).equals(payTyp)){
                    if (((rowCount) % 20 == 0) || /**!(batchPaymentReceiveCase.getApNoLastDigit()).equals(apNoLastDigit) ||*/ !(batchPaymentReceiveCase.getPayTyp()).equals(payTyp)) {
                        // 不滿20筆補空白行
                        if (rowCount % 20 > 0)
                            addEmptyRow(table, 20 - (rowCount % 20));

                        // 加入表尾
                        addFooter(table, batchPaymentReceiveCase.getDeptNameString(), amtList, rowCount, caseData, issuTyp, payTyp, rowBeg, i);
                        document.add(table);

                        // 取得表頭
                        pageNum++;
                        table = getHeader(true, payCode, batchPaymentReceiveCase.getIssuTypStr(), batchPaymentReceiveCase.getPayTypStr(), batchPaymentReceiveCase.getPayYm(), batchPaymentReceiveCase.getIssuYm(), batchPaymentReceiveCase.getPayTyp(), map, batchPaymentReceiveCase.getcPrnDateStr(), batchPaymentReceiveCase.getNaChgMk(), batchPaymentReceiveCase.getNlWkMk(), batchPaymentReceiveCase.getAdWkMk(), batchPaymentReceiveCase.getIsNaChgMk(), receiveType, rptPage, pageNum);

                        // if (!(batchPaymentReceiveCase.getIssuTyp()).equals(issuTyp) || !(batchPaymentReceiveCase.getPayTyp()).equals(payTyp)) {
                        if (/**!(batchPaymentReceiveCase.getApNoLastDigit()).equals(apNoLastDigit) ||*/ !(batchPaymentReceiveCase.getPayTyp()).equals(payTyp)) {
                            // 序號歸零
                            seqNo = 1;
                            rowBeg = i;
                        }

                        rowCount = 0;
                    }
                }
                // 案件類別
                issuTyp = batchPaymentReceiveCase.getIssuTyp();
                // 給付方式
                payTyp = batchPaymentReceiveCase.getPayTyp();
                // 部門名稱
                deptName = batchPaymentReceiveCase.getDeptNameString();
                // 受理編號的最後一碼
                apNoLastDigit = batchPaymentReceiveCase.getApNoLastDigit();

                // 序號
                addColumn(table, 3, 1, Integer.toString(seqNo), fontCh9, 1, CENTER);
                // 受理編號
                addColumn(table, 7, 1, batchPaymentReceiveCase.getApNoStr(), fontCh9, 1, CENTER);
                // 給付年月
                addColumn(table, 3, 1, DateUtility.changeWestYearMonthType(batchPaymentReceiveCase.getPayYm()), fontCh9, 1, CENTER);
                // 金融機構轉帳帳號
                if (payTyp.equals(ConstantKey.BAAPPBASE_PAYTYP_STR_A))
                    addColumn(table, 11, 1, batchPaymentReceiveCase.getBenIdn(), fontCh9, 1, CENTER);
                else
                    addColumn(table, 11, 1, batchPaymentReceiveCase.getPayBankIdStr(), fontCh9, 1, CENTER);
                // 受款人姓名
                addColumn(table, 5, 1, batchPaymentReceiveCase.getBenName(), fontCh9, 1, CENTER);
                // 核定金額(A)
                addColumn(table, 5, 1, ReportBase.formatNumber(batchPaymentReceiveCase.getIssueAmt().longValue()), fontCh9, 1, RIGHT);
                // 另案扣減同保險(B)
                addColumn(table, 4, 1, ReportBase.formatNumber(batchPaymentReceiveCase.getOtheraAmt().longValue()), fontCh9, 1, RIGHT);
                // 另案扣減他保險(C)
                addColumn(table, 4, 1, ReportBase.formatNumber(batchPaymentReceiveCase.getOtherbAmt().longValue()), fontCh9, 1, RIGHT);
                // 匯費(D)
                addColumn(table, 4, 1, ReportBase.formatNumber(batchPaymentReceiveCase.getMitRate().longValue()), fontCh9, 1, RIGHT);
                // 抵銷紓困(E)
                addColumn(table, 4, 1, ReportBase.formatNumber(batchPaymentReceiveCase.getOffsetAmt().longValue()), fontCh9, 1, RIGHT);
                // 代扣補償金(F)
                addColumn(table, 4, 1, ReportBase.formatNumber(batchPaymentReceiveCase.getCompenAmt().longValue()), fontCh9, 1, RIGHT);
                // 保留遺屬津貼(G)
                addColumn(table, 4, 1, ReportBase.formatNumber(batchPaymentReceiveCase.getInherItorAmt().longValue()), fontCh9, 1, RIGHT);
                // 代扣利息所得稅(H)
                addColumn(table, 4, 1, ReportBase.formatNumber(batchPaymentReceiveCase.getItrtTax().longValue()), fontCh9, 1, RIGHT);
                // 其他(I)
                addColumn(table, 4, 1, ReportBase.formatNumber(batchPaymentReceiveCase.getOtherAmt().longValue()), fontCh9, 1, RIGHT);

                // 計算本頁小計用
                amtList.add(batchPaymentReceiveCase);

                seqNo++;
                rowCount++;

            }// for(int i=0;i<caseData.size();i++) end

            // 不滿20筆補空白行
            if (rowCount % 20 > 0)
                addEmptyRow(table, 20 - (rowCount % 20));

            // 加入表尾
            addFooter(table, deptName, amtList, rowCount, caseData, issuTyp, payTyp, rowBeg, caseData.size());

            

            return table;
        }
        finally {
            // document.close();
        }

    }

    public Table addAnotherReport(UserBean userData, List<BatchPaymentReceiveDataCase> caseList, List<BatchPaymentReceiveDataCase> caseList1, HashMap<String, Object> map, String pageNum) throws Exception {
        try {
            document.newPage();

            // Integer pageNum = 1;// 頁數

            Table table = null;

            String payCode = (String) map.get("payCode");
            String receiveType = (String) map.get("receiveType");
            String issuYm = (String) map.get("issuYm");

            RptService rptService = (RptService) SpringHelper.getBeanById("rptService");

            String printDate = DateUtility.getNowChineseDate();

            Integer seqNo = 1;// 序號
            BigDecimal tapNoAmt = new BigDecimal(0);// 件數
            BigDecimal tdataAmt = new BigDecimal(0);// 筆數
            BigDecimal tissueAmt = new BigDecimal(0);// 核定金額
            BigDecimal totheraAmt = new BigDecimal(0);// 另案扣減(同類保險)金額
            BigDecimal totherbAmt = new BigDecimal(0);// 另案扣減(他類保險)金額
            BigDecimal tmitRate = new BigDecimal(0);// 匯款匯費
            BigDecimal toffsetAmt = new BigDecimal(0);// 抵銷紓困
            BigDecimal tcompenAmt = new BigDecimal(0);// 代扣補償金
            BigDecimal tinherItorAmt = new BigDecimal(0);// 保留遺屬津貼
            BigDecimal titrtTax = new BigDecimal(0);// 代扣利息所得稅
            BigDecimal totherAmt = new BigDecimal(0);// 其他
            BigDecimal tpayAmt = new BigDecimal(0);// 應付金額

            BatchPaymentReceiveDataCase tmpCaseObj = caseList.get(0);

            table = getHeader1(true, payCode, tmpCaseObj.getPayTypStr(), tmpCaseObj.getChkDateStr(), pageNum, tmpCaseObj.getcPrnDateStr(), tmpCaseObj.getNaChgMk(), tmpCaseObj.getNlWkMk(), tmpCaseObj.getAdWkMk(), tmpCaseObj.getIsNaChgMk(), receiveType, pageNum1);

            for (BatchPaymentReceiveDataCase caseObj : caseList) {
                BigDecimal payAmt = new BigDecimal(0);// 應付金額歸0
                // 計算應付金額
                payAmt = caseObj.getIssueAmt();// 應付金額 = 核定金額
                payAmt = payAmt.subtract(caseObj.getOtheraAmt());
                payAmt = payAmt.subtract(caseObj.getOtherbAmt());
                payAmt = payAmt.subtract(caseObj.getMitRate());
                payAmt = payAmt.subtract(caseObj.getOffsetAmt());
                payAmt = payAmt.subtract(caseObj.getCompenAmt());
                payAmt = payAmt.subtract(caseObj.getInherItorAmt());
                payAmt = payAmt.subtract(caseObj.getItrtTax());
                payAmt = payAmt.subtract(caseObj.getOtherAmt());

                // 計算合計資料
                tapNoAmt = tapNoAmt.add(caseObj.getApNoAmt());// 件數
                tdataAmt = tdataAmt.add(caseObj.getDataAmt());// 筆數
                tissueAmt = tissueAmt.add(caseObj.getIssueAmt());// 核定金額
                totheraAmt = totheraAmt.add(caseObj.getOtheraAmt());// 另案扣減(同類保險)金額
                totherbAmt = totherbAmt.add(caseObj.getOtherbAmt());// 另案扣減(他類保險)金額
                tmitRate = tmitRate.add(caseObj.getMitRate());// 匯款匯費
                toffsetAmt = toffsetAmt.add(caseObj.getOffsetAmt());// 抵銷紓困
                tcompenAmt = tcompenAmt.add(caseObj.getCompenAmt());// 代扣補償金
                tinherItorAmt = tinherItorAmt.add(caseObj.getInherItorAmt());// 保留遺屬津貼
                titrtTax = titrtTax.add(caseObj.getItrtTax());// 代扣利息所得稅
                totherAmt = totherAmt.add(caseObj.getOtherAmt());// 其他
                tpayAmt = tpayAmt.add(payAmt);// 應付金額

                addColumn(table, 4, 1, Integer.toString(seqNo), fontCh10, 1, CENTER);
                addColumn(table, 5, 1, formatBigDecimalToInteger(caseObj.getApNoAmt()), fontCh10, 1, RIGHT);
                addColumn(table, 5, 1, formatBigDecimalToInteger(caseObj.getDataAmt()), fontCh10, 1, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(caseObj.getIssueAmt()), fontCh10, 1, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(caseObj.getOtheraAmt()), fontCh10, 1, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(caseObj.getOtherbAmt()), fontCh10, 1, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(caseObj.getMitRate()), fontCh10, 1, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(caseObj.getOffsetAmt()), fontCh10, 1, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(caseObj.getCompenAmt()), fontCh10, 1, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(caseObj.getInherItorAmt()), fontCh10, 1, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(caseObj.getItrtTax()), fontCh10, 1, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(caseObj.getOtherAmt()), fontCh10, 1, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(payAmt), fontCh10, 1, RIGHT);
                addColumn(table, 5, 1, caseObj.getMchkTypStr(), fontCh8, 1, CENTER);

                seqNo++;
            }
            int dataSize = caseList.size();
            if (dataSize < 7) {
                for (int i = 0; i < (7 - dataSize); i++) {
                    addColumn(table, 4, 1, "　", fontCh10, 1, CENTER);
                    addColumn(table, 5, 1, "", fontCh10, 1, RIGHT);
                    addColumn(table, 5, 1, "", fontCh10, 1, RIGHT);
                    addColumn(table, 8, 1, "", fontCh10, 1, RIGHT);
                    addColumn(table, 8, 1, "", fontCh10, 1, RIGHT);
                    addColumn(table, 8, 1, "", fontCh10, 1, RIGHT);
                    addColumn(table, 8, 1, "", fontCh10, 1, RIGHT);
                    addColumn(table, 8, 1, "", fontCh10, 1, RIGHT);
                    addColumn(table, 8, 1, "", fontCh10, 1, RIGHT);
                    addColumn(table, 8, 1, "", fontCh10, 1, RIGHT);
                    addColumn(table, 8, 1, "", fontCh10, 1, RIGHT);
                    addColumn(table, 8, 1, "", fontCh10, 1, RIGHT);
                    addColumn(table, 8, 1, "", fontCh10, 1, RIGHT);
                    addColumn(table, 5, 1, "", fontCh10, 1, CENTER);
                }
            }

            addColumn(table, 4, 1, "合計", fontCh10, 1, CENTER);
            addColumn(table, 5, 1, formatBigDecimalToInteger(tapNoAmt), fontCh10, 1, RIGHT);
            addColumn(table, 5, 1, formatBigDecimalToInteger(tdataAmt), fontCh10, 1, RIGHT);
            addColumn(table, 8, 1, formatBigDecimalToInteger(tissueAmt), fontCh10, 1, RIGHT);
            addColumn(table, 8, 1, formatBigDecimalToInteger(totheraAmt), fontCh10, 1, RIGHT);
            addColumn(table, 8, 1, formatBigDecimalToInteger(totherbAmt), fontCh10, 1, RIGHT);
            addColumn(table, 8, 1, formatBigDecimalToInteger(tmitRate), fontCh10, 1, RIGHT);
            addColumn(table, 8, 1, formatBigDecimalToInteger(toffsetAmt), fontCh10, 1, RIGHT);
            addColumn(table, 8, 1, formatBigDecimalToInteger(tcompenAmt), fontCh10, 1, RIGHT);
            addColumn(table, 8, 1, formatBigDecimalToInteger(tinherItorAmt), fontCh10, 1, RIGHT);
            addColumn(table, 8, 1, formatBigDecimalToInteger(titrtTax), fontCh10, 1, RIGHT);
            addColumn(table, 8, 1, formatBigDecimalToInteger(totherAmt), fontCh10, 1, RIGHT);
            addColumn(table, 8, 1, formatBigDecimalToInteger(tpayAmt), fontCh10, 1, RIGHT);
            addColumn(table, 5, 1, "", fontCh10, 1, CENTER);

            // table = addFooter(table, payCode, key, tissueAmt);
            // 20120406 begin
            table.setBorderWidth(1);
            int rowCount = 0;

            for (BatchPaymentReceiveDataCase caseObj1 : caseList1) {

                if ((caseObj1.getAccountSeq().toString()).equals("0")) {
                    addColumn(table, 4, 1, "借：", fontCh10, 0, LEFT);
                    addColumn(table, 12, 1, caseObj1.getAccountNo(), fontCh10, 0, LEFT);
                    addColumn(table, 70, 1, caseObj1.getAccountName(), fontCh10, 0, LEFT);
                    addColumn(table, 8, 1, formatBigDecimalToInteger(caseObj1.getPayAmt()), fontCh10, 0, RIGHT);
                    addColumn(table, 5, 1, "", fontCh10, 0, CENTER);
                    rowCount += 1;
                }
                else if ((caseObj1.getAccountSeq().toString()).equals("1")) {

                    String chkMk = "";
                    if (caseObj1.getAccountNo().substring(0, 10).equals("212312  12")) {
                        chkMk = BaBusinessUtility.getAccountChkMk(caseObj1.getAccountNo() + ".");
                    }

                    addColumn(table, 4, 1, "　　", fontCh10, 0, LEFT);
                    addColumn(table, 4, 1, "貸：", fontCh10, 0, LEFT);
                    addColumn(table, 12, 1, caseObj1.getAccountNo() + " " + chkMk, fontCh10, 0, LEFT);
                    addColumn(table, 66, 1, caseObj1.getAccountName(), fontCh10, 0, LEFT);
                    addColumn(table, 8, 1, formatBigDecimalToInteger(caseObj1.getPayAmt()), fontCh10, 0, RIGHT);
                    addColumn(table, 5, 1, "", fontCh10, 0, CENTER);
                    rowCount += 1;

                }

                seqNo++;
            }

            // 補空白行

            for (int i = 0; i < 13 - rowCount; i++) {
                addColumn(table, 99, 1, "　", fontCh10, 0, CENTER);
            }

            addColumn(table, 30, 1, "經辦人員", fontCh10, 0, LEFT);
            addColumn(table, 69, 1, RptTitleUtility.getSafetyPaymentTitle3(DateUtility.getNowWestDate()) + "科長：", fontCh10, 0, LEFT);
            // addColumn(table, 69, 1, "出納科長：", fontCh10, 0, LEFT);
            addColumn(table, 99, 1, "　", fontCh10, 0, CENTER);
            // 20120406 end

            pageNum1++;

            return table;
        }
        finally {
            // document.close();
        }

    }    
    
    public Table failureReport(UserBean userData, List<BatchPaymentReceiveDataCase> caseList, HashMap<String, Object> map) throws Exception {
        try {
            document.newPage();

            Table table = null;

//            String payCode = (String) map.get("payCode");
//            String chkDate = (String) map.get("chkDate");
//            String errorMessage = (String) map.get("errorMessage");

            BigDecimal pageRec = new BigDecimal(0); // 本頁小計 - 筆數
            BigDecimal allRec = new BigDecimal(0); // 總筆數

            int recNo = 0; // 用來暫存 編號

            for (int i = 0; i < caseList.size(); i++) { // ... [
                BatchPaymentReceiveDataCase caseData = caseList.get(i);

                // 第一筆加入表頭
                if (i == 0) {
                    table = addHeader(caseData, map, pageNum2);
                }

                // 本頁小計 - 筆數
                pageRec = pageRec.add(new BigDecimal(1));

                // 總筆數
                allRec = allRec.add(new BigDecimal(1));

                addColumn(table, 6, 1, String.valueOf(recNo + 1), fontCh14, 0, CENTER); // 序號
                addColumn(table, 8, 1, caseData.getTempHandleNo(), fontCh14, 0, LEFT); // 受理編號
                addColumn(table, 8, 1, caseData.getDivSeq(), fontCh14, 0, CENTER); // 分割序號
                addColumn(table, 8, 1, formatBigDecimalToFloat(((caseData.getAvailable_Money() == null) ? new BigDecimal(0) : caseData.getAvailable_Money())), fontCh14, 0, RIGHT); // 交易金額
                addColumn(table, 36, 1, caseData.getRemark(), fontCh14, 0, LEFT); // 備註

                // 判段是否為最後一筆, 或達成換頁條件 (受益人與事故者關係 + 相關單位名稱1)
                if (i == caseList.size() - 1) {
                    // 塞滿空白行, 以讓表格美觀
                    // [
                    while (writer.fitsPage(table)) {
                        addEmptyRowForFailureReport(table, 1);
                    }

                    deleteRow(table, 4); // 表尾有 3 行 + 前面回圈會多塞 1 行, 故要刪 4 行

                    addEmptyRowForFailureReport(table, 1);
                    // ---
                    if (i == caseList.size() - 1) {

                        addColumn(table, 66, 1, "　　　總筆數：" + formatBigDecimalToInteger(allRec) + "／" + "本頁小計筆數：" + formatBigDecimalToInteger(pageRec), fontCh16, 0, LEFT);

                    }
                    else {
                        // ---
                        addColumn(table, 33, 1, "　", fontCh12, 0, LEFT);
                        addColumn(table, 33, 1, "本頁小計筆數：" + formatBigDecimalToInteger(pageRec), fontCh16, 0, LEFT);

                    }
//                    document.add(table);

                    // 歸零
                    recNo = 0; // 編號
                    pageRec = new BigDecimal(0); // 本頁小計 - 筆數

                    if (i != caseList.size() - 1) {
                        pageNum2++;
                        table = addHeader(caseList.get(i + 1), map, pageNum2); // 下一筆
                    }

                }
                else {
                    // 測試塞下一筆 + 表尾時是否需要換頁
                    addColumn(table, 6, 1, String.valueOf(recNo + 2), fontCh14, 0, CENTER); // 序號
                    addColumn(table, 8, 1, caseList.get(i + 1).getTempHandleNo(), fontCh14, 0, LEFT); // 受理編號
                    addColumn(table, 8, 1, caseData.getDivSeq(), fontCh14, 0, CENTER); // 分割序號
                    addColumn(table, 8, 1, formatBigDecimalToFloat(((caseList.get(i + 1).getCash_Amt() == null) ? new BigDecimal(0) : caseList.get(i + 1).getCash_Amt())), fontCh14, 0, RIGHT); // 交易金額
                    addColumn(table, 36, 1, caseData.getRemark(), fontCh14, 0, LEFT); // 備註
                    // ---
                    addEmptyRowForFailureReport(table, 3); // 表尾 3 行

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        deleteRow(table, 4);

                        // 表尾之本頁小計
                        addEmptyRowForFailureReport(table, 1);
                        // ---
                        addColumn(table, 33, 1, "　", fontCh12, 0, LEFT);
                        addColumn(table, 33, 1, "本頁小計筆數：" + formatBigDecimalToInteger(pageRec), fontCh16, 0, LEFT);

                        document.add(table);

                        // 編號 + 1
                        recNo++;

                        // 本頁小計歸零
                        pageRec = new BigDecimal(0); // 本頁小計 - 筆數

                        pageNum2++;
                        table = addHeader(caseList.get(i + 1), map, pageNum2); // 下一筆

                    }
                    else {
                        // 編號 + 1
                        recNo++;
                        deleteRow(table, 4);
                    }
                }
            } // ] ... end for (int i = 0; i < caseList.size(); i++)

            return table;
        }
        finally {
            // document.close();
        }
    }

    public Table getHeader1(boolean bPrintMainHeader, String payCode, String payTypStr, String chkDate, String pageNum, String cPrnDate, String naChgMk, String nlWkMk, String adWkMk, String isNaChgMk, String receiveType, Integer pageNum1) throws Exception {
        // document.newPage();
        // 建立表格
        Table table = createTable(99);
        table.setAutoFillEmptyCells(true);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        addColumn(table, 99, 1, " ", fontCh8, 0, CENTER);

        // 列印大標題
        if (bPrintMainHeader) {
            addColumn(table, 99, 1, RptTitleUtility.getOrgTitle(DateUtility.getNowWestDate()), fontCh12b, 0, CENTER);
            addColumn(table, 99, 1, RptTitleUtility.getGroupsTitle(payCode, DateUtility.getNowWestDate()), fontCh10, 0, CENTER);
            // addColumn(table, 99, 1, "勞工保險局", fontCh12b, 0, CENTER);
            // addColumn(table, 99, 1, "給付處", fontCh10, 0, CENTER);

            addColumn(table, 99, 1, "整批收回核定清單", fontCh10, 0, CENTER);

            if (!("").equals(payTypStr)) {
                addColumn(table, 99, 1, "[" + payTypStr + "]", fontCh10, 0, CENTER);
            }

            String payCodeStr = "";
            String isNaChgMkStr = "";

            if ("Y".equals(isNaChgMk)) {
                if ("12".equals(naChgMk)) {
                    isNaChgMkStr = " - 普改職";
                }
                else if ("13".equals(naChgMk)) {
                    isNaChgMkStr = " - 普改加職";
                }
                else if ("21".equals(naChgMk)) {
                    isNaChgMkStr = " - 職改普";
                }
                else if ("23".equals(naChgMk)) {
                    isNaChgMkStr = " - 職改加職";
                }
                else if ("31".equals(naChgMk)) {
                    isNaChgMkStr = " - 加職改普";
                }
                else if ("32".equals(naChgMk)) {
                    isNaChgMkStr = " - 加職改職";
                }
            }
            else if ("N".equals(isNaChgMk)) {
                isNaChgMkStr = "";
            }

            if (("L").equals(payCode)) {
                payCodeStr = "普通-老年給付(年金)";
            }
            else if (("K").equals(payCode)) {
                if (("1").equals(nlWkMk)) {
                    payCodeStr = "普通-失能給付(年金)" + isNaChgMkStr;
                }
                else if (("2").equals(nlWkMk) && ("1").equals(adWkMk)) {
                    payCodeStr = "職災-失能給付(年金)" + isNaChgMkStr;
                }
                else if (("2").equals(nlWkMk) && ("2").equals(adWkMk)) {
                    payCodeStr = "加職-失能給付(年金)" + isNaChgMkStr;
                }
                else {
                    payCodeStr = "普通-失能給付(年金)";
                }

            }
            else if (("S").equals(payCode)) {
                if (("1").equals(nlWkMk)) {
                    payCodeStr = "普通-遺屬給付(年金)";
                }
                else if (("2").equals(nlWkMk) && ("1").equals(adWkMk)) {
                    payCodeStr = "職災-遺屬給付(年金)";
                }
                else if (("2").equals(nlWkMk) && ("2").equals(adWkMk)) {
                    payCodeStr = "加職-遺屬給付(年金)";
                }
                else {
                    payCodeStr = "普通-遺屬給付(年金)";
                }
            }

            addColumn(table, 99, 1, "給付別：" + payCodeStr, fontCh10, 0, LEFT);

            addColumn(table, 63, 1, "核定日期：" + DateUtility.formatChineseDateTimeString(chkDate, true), fontCh10, 0, LEFT);
            addColumn(table, 24, 1, "印表日期：" + DateUtility.formatChineseDateTimeString(cPrnDate, true), fontCh10, 0, LEFT);
            // addColumn(table, 7, 1, "第" + StringUtility.chtLeftPad(String.valueOf(pageNum + 500000), 6, "0") + "頁", fontCh10, 0, LEFT);
            if (StringUtils.isBlank(pageNum)) {
                addColumn(table, 12, 1, "", fontCh10, 0, LEFT);// 20130731
            }
            else {
                // addColumn(table, 12, 1, "第" + pageNum + "頁", fontCh10, 0, LEFT);// 20130731
                addColumn(table, 12, 1, "第" + pageNum1.toString() + "頁", fontCh10, 0, LEFT);// 20130731
            }

        }

        addColumn(table, 4, 1, "序號", fontCh10, 1, CENTER);
        addColumn(table, 5, 1, "件數", fontCh10, 1, CENTER);
        addColumn(table, 5, 1, "筆數", fontCh10, 1, CENTER);
        addColumn(table, 8, 1, "核定金額(A)", fontCh10, 1, CENTER);
        addColumn(table, 8, 1, "另案扣減同保險(B)", fontCh10, 1, CENTER);
        addColumn(table, 8, 1, "另案扣減他保險(C)", fontCh10, 1, CENTER);
        addColumn(table, 8, 1, "匯費(D)", fontCh10, 1, CENTER);
        addColumn(table, 8, 1, "抵銷紓困(E)", fontCh10, 1, CENTER);
        addColumn(table, 8, 1, "代扣補償金(F)", fontCh10, 1, CENTER);
        addColumn(table, 8, 1, "保留遺屬津貼(G)", fontCh10, 1, CENTER);
        addColumn(table, 8, 1, "代扣利息所得稅(H)", fontCh10, 1, CENTER);
        addColumn(table, 8, 1, "其他(I)", fontCh10, 1, CENTER);
        addColumn(table, 8, 1, "應付金額=(A)-(B)-(C)-(D)-(E)-(F)-(G)-(H)-(I)", fontCh10, 1, CENTER);
        addColumn(table, 5, 1, "備註", fontCh10, 1, CENTER);

        return table;
    }

    public ByteArrayOutputStream printReport(UserBean userData, List<BatchPaymentReceiveDataCase> caseData, HashMap<String, Object> map, List<BatchPaymentReceiveDataCase> caseData1, List<BatchPaymentReceiveDataCase> caseDataFooter, List<BatchPaymentReceiveDataCase> caseData3, List<BatchPaymentReceiveDataCase> caseDataFooter3, List<BatchPaymentReceiveDataCase> failureList) throws Exception {
        try {
            RptService rptService = (RptService) SpringHelper.getBeanById("rptService");

            document.open();

            Table pageOne = null;
            Table pageTwo = null;
            Table pageThree = null;

            // 建立資料Map
            // Map的Key 為 issuYm+payYm
            // Value則是同為該 payTyp + nlWkMk + adWkMk 下的所有資料
            Map<String, List<BatchPaymentReceiveDataCase>> dataMap = new TreeMap<String, List<BatchPaymentReceiveDataCase>>();

            for (int i = 0; i < caseData.size(); i++) {
                BatchPaymentReceiveDataCase obj = caseData.get(i);
                dataMap.put(obj.getRptPage() + obj.getIssuYm() + obj.getNaChgMk() + obj.getNlWkMk() + obj.getAdWkMk() + obj.getPayTyp(), new ArrayList<BatchPaymentReceiveDataCase>());
            }

            for (int i = 0; i < caseData.size(); i++) {
                BatchPaymentReceiveDataCase obj = caseData.get(i);
                (dataMap.get(obj.getRptPage() + obj.getIssuYm() + obj.getNaChgMk() + obj.getNlWkMk() + obj.getAdWkMk() + obj.getPayTyp())).add(obj);
            }

            for (Iterator it = dataMap.keySet().iterator(); it.hasNext();) {
                String key = (String) it.next();
                List<BatchPaymentReceiveDataCase> caseList = dataMap.get(key);

                // 應收已收清冊
                pageOne = doReport(userData, caseList, map);
                document.add(pageOne);
            }

            if (!(caseData1 == null)) {
                // 印主要報表 START - 【NACHGMK is null】
                // ================================================================================================================

                // 建立資料Map
                // Map的Key 為 issuYm+payYm
                // Value則是同為該 payTyp 下的所有資料
                Map<String, List<BatchPaymentReceiveDataCase>> dataMap1 = new TreeMap<String, List<BatchPaymentReceiveDataCase>>();
                Map<String, List<BatchPaymentReceiveDataCase>> dataMapFooter = new TreeMap<String, List<BatchPaymentReceiveDataCase>>(); // 20120406
                for (int i = 0; i < caseData1.size(); i++) {
                    BatchPaymentReceiveDataCase obj = caseData1.get(i);
                    dataMap1.put(obj.getPayTyp(), new ArrayList<BatchPaymentReceiveDataCase>());
                }

                for (int i = 0; i < caseData1.size(); i++) {
                    BatchPaymentReceiveDataCase obj = caseData1.get(i);
                    (dataMap1.get(obj.getPayTyp())).add(obj);
                }

                for (int i = 0; i < caseData1.size(); i++) { // 20120416
                    BatchPaymentReceiveDataCase obj = caseData1.get(i);
                    dataMapFooter.put(obj.getPayTyp(), new ArrayList<BatchPaymentReceiveDataCase>());
                }

                for (int i = 0; i < caseDataFooter.size(); i++) { // 20120416
                    BatchPaymentReceiveDataCase obj = caseDataFooter.get(i);
                    (dataMapFooter.get(obj.getPayTyp())).add(obj);
                }

                for (Iterator it = dataMap1.keySet().iterator(); it.hasNext();) {

                    String key = (String) it.next();
                    List<BatchPaymentReceiveDataCase> caseList = dataMap1.get(key);
                    List<BatchPaymentReceiveDataCase> caseList1 = dataMapFooter.get(key);

                    /**
                     * // 拿取頁次 20130731 List<BatchPaymentReceiveDataCase> caseListPageNum = dataMapFooter.get(key); String rptPage = caseListPageNum.get(0).getRptPage().toString(); String eRptPage = caseListPageNum.get(0).geteRptPage().toString(); String pageNum = ""; // 判斷頁次 if (rptPage.equals("0") && eRptPage.equals("0")) { pageNum = ""; } else { if (eRptPage.equals(rptPage)) { pageNum = rptPage; } else { pageNum = rptPage + "~" + eRptPage; } }
                     */

                    // 應收已收核定清單
                    pageTwo = addAnotherReport(userData, caseList, caseList1, map, "0");
                    document.add(pageTwo);
                }

                // ================================================================================================================
                // 印主要報表 END - 【NACHGMK is null】
            }

            if (!(caseData3 == null)) {
                // 印主要報表 START - 【NACHGMK is null】
                // ================================================================================================================

                // 建立資料Map
                // Map的Key 為 issuYm+payYm
                // Value則是同為該 payTyp 下的所有資料
                Map<String, List<BatchPaymentReceiveDataCase>> dataMap3 = new TreeMap<String, List<BatchPaymentReceiveDataCase>>();
                Map<String, List<BatchPaymentReceiveDataCase>> dataMapFooter3 = new TreeMap<String, List<BatchPaymentReceiveDataCase>>(); // 20120406
                for (int i = 0; i < caseData3.size(); i++) {
                    BatchPaymentReceiveDataCase obj = caseData3.get(i);
                    dataMap3.put(obj.getPayTyp(), new ArrayList<BatchPaymentReceiveDataCase>());
                }

                for (int i = 0; i < caseData3.size(); i++) {
                    BatchPaymentReceiveDataCase obj = caseData3.get(i);
                    (dataMap3.get(obj.getPayTyp())).add(obj);
                }

                for (int i = 0; i < caseData3.size(); i++) { // 20120416
                    BatchPaymentReceiveDataCase obj = caseData3.get(i);
                    dataMapFooter3.put(obj.getPayTyp(), new ArrayList<BatchPaymentReceiveDataCase>());
                }

                for (int i = 0; i < caseDataFooter3.size(); i++) { // 20120416
                    BatchPaymentReceiveDataCase obj = caseDataFooter3.get(i);
                    (dataMapFooter3.get(obj.getPayTyp())).add(obj);
                }

                for (Iterator it = dataMap3.keySet().iterator(); it.hasNext();) {

                    String key = (String) it.next();
                    List<BatchPaymentReceiveDataCase> caseList = dataMap3.get(key);
                    List<BatchPaymentReceiveDataCase> caseList1 = dataMapFooter3.get(key);

                    /**
                     * // 拿取頁次 20130731 List<BatchPaymentReceiveDataCase> caseListPageNum = dataMapFooter.get(key); String rptPage = caseListPageNum.get(0).getRptPage().toString(); String eRptPage = caseListPageNum.get(0).geteRptPage().toString(); String pageNum = ""; // 判斷頁次 if (rptPage.equals("0") && eRptPage.equals("0")) { pageNum = ""; } else { if (eRptPage.equals(rptPage)) { pageNum = rptPage; } else { pageNum = rptPage + "~" + eRptPage; } }
                     */

                    // 應收已收核定清單
                    pageTwo = addAnotherReport(userData, caseList, caseList1, map, "0");
                    document.add(pageTwo);
                }

                // ================================================================================================================
                // 印主要報表 END - 【NACHGMK is null】
            }
            
            if ((!(failureList == null)) && failureList.size() > 0) {
                // 失敗清單
                pageThree = failureReport(userData, failureList, map);
                document.add(pageThree);
            }

            document.close();
        }
        finally {
            document.close();
        }
        return bao;
    }
    
    
    /**
     * 建立表頭
     * 
     * @return
     * @throws Exception
     */
    public Table addHeader(BatchPaymentReceiveDataCase caseData, HashMap<String, Object> map, Integer pageNum2) throws Exception {
        document.newPage();
        // 建立表格
        Table table = createTable(66);
        table.setAutoFillEmptyCells(true);

        addColumn(table, 66, 1, " ", fontCh8, 0, CENTER);

        addColumn(table, 16, 1, "報表編號：BALP0D690L", fontCh16, 0, LEFT);
        addColumn(table, 38, 1, RptTitleUtility.getOrgTitle(DateUtility.getNowWestDate()) + "　" + RptTitleUtility.getGroupsTitle((String) map.get("payCode"), DateUtility.getNowWestDate()), fontCh22, 0, CENTER);
        // addColumn(table, 25, 1, "勞工保險局", fontCh18, 0, CENTER);
        addColumn(table, 12, 1, "　", fontCh12, 0, LEFT);
        // ---

        addColumn(table, 16, 1, "印表日期：" + DateUtility.formatChineseDateString(DateUtility.getNowChineseDate(), false), fontCh16, 0, LEFT);
        addColumn(table, 38, 1, "整批收回失敗清單", fontCh22, 0, CENTER);
        addColumn(table, 12, 1, "印表頁次：第 " + pageNum2.toString() + " 頁", fontCh16, 0, RIGHT);
        // ---

        addColumn(table, 66, 1, "核收日期：" + DateUtility.formatChineseDateString(StringUtils.defaultString((String)map.get("chkDate1")), false), fontCh16, 0, LEFT);

        addEmptyRowForFailureReport(table, 1);

        // 表格標題
        addColumn(table, 6, 1, "序號", fontCh16, 0, CENTER);
        addColumn(table, 8, 1, "受理編號", fontCh16, 0, LEFT);
        addColumn(table, 8, 1, "分割序號", fontCh16, 0, CENTER);
        addColumn(table, 8, 1, "給付金額", fontCh16, 0, RIGHT);
        addColumn(table, 36, 1, "備註", fontCh16, 0, LEFT);

        return table;
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }

}
