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
import tw.gov.bli.common.helper.SpringHelper;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt08Case;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt02Case;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;
import tw.gov.bli.ba.util.StringUtility;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

/**
 * 月核定合格清冊
 * 
 * @author swim
 */
public class MonthlyRptK08Report extends ReportBase {

    // private int pageNow = 0; // for Log
	private long allIssueAmt = 0l;
	private long allOtheraAmt = 0l;
	private long allOtherbAmt = 0l;
	private long allMitRate = 0l;
	private long allOffsetAmt = 0l;
	private long allCompenAmt = 0l;
	private long allInherItorAmt = 0l;
	private long allItrtTax = 0l;
	private long allOtherAmt = 0l;
    public MonthlyRptK08Report() throws Exception {
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

    public Table getHeader(boolean bPrintMainHeader, String payCodeStr, String mchkTypStr, String payTypStr, String payYm, String payTyp, HashMap<String, Object> map,String payCode, String cPrnDate) throws Exception {

        document.newPage();

        // for Log
        // [
        /*
        pageNow = writer.getPageNumber();
        */
        // ]

        // 建立表格
        Table table = createTable(66);
        table.setAutoFillEmptyCells(true);

        addColumn(table, 66, 1, " ", fontCh8, 0, CENTER);

        // 列印大標題
        if (bPrintMainHeader) {
            addColumn(table, 52, 1, "報表編號：BALP0D380", fontCh10, 0, LEFT);
            addColumn(table, 14, 1, "", fontCh10, 0, LEFT);

            addColumn(table, 14, 1, "", fontCh10, 0, CENTER);
            addColumn(table, 38, 1, RptTitleUtility.getOrgTitle(DateUtility.getNowWestDate())+"　"+RptTitleUtility.getGroupsTitle(payCode, DateUtility.getNowWestDate()), fontCh18, 0, CENTER);
            //addColumn(table, 38, 1, "勞工保險局　給付處", fontCh18, 0, CENTER);
            addColumn(table, 14, 1, "", fontCh10, 0, LEFT);

            addColumn(table, 14, 1, "", fontCh10, 0, CENTER);
            addColumn(table, 38, 1, "月核定合格清冊", fontCh18, 0, CENTER);
            addColumn(table, 14, 1, "", fontCh10, 0, CENTER);

            addColumn(table, 14, 1, "", fontCh10, 0, LEFT);
            addColumn(table, 38, 1, "", fontCh10, 0, CENTER);
            addColumn(table, 14, 1, "頁　　次：" + StringUtility.chtLeftPad(String.valueOf(writer.getPageNumber()), 3, "0"), fontCh10, 0, LEFT);

            addColumn(table, 14, 1, "核定年月：" + map.get("issuYm"), fontCh10, 0, LEFT);
            addColumn(table, 38, 1, "", fontCh10, 0, CENTER);
            addColumn(table, 14, 1, "印表日期：" + DateUtility.formatChineseDateTimeString(cPrnDate, false), fontCh10, 0, LEFT);

            addColumn(table, 24, 1, "給 付 別：" + payCodeStr, fontCh10, 0, LEFT);
            addColumn(table, 28, 1, "類別：" + mchkTypStr, fontCh10, 0, LEFT);
            addColumn(table, 14, 1, "給付方式：" + payTypStr, fontCh10, 0, LEFT);
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

    public void addFooter(Table table, String DeptName, List<MonthlyRpt08Case> amtList, Integer rowCount, String issuTyp, String payTyp, Integer rowBeg, Integer rowEnd,String payCode, boolean reCompute) throws Exception {
    	long sumIssueAmt = 0l;
        long sumOtheraAmt = 0l;
        long sumOtherbAmt = 0l;
        long sumMitRate = 0l;
        long sumOffsetAmt = 0l;
        long sumCompenAmt = 0l;
        long sumInherItorAmt = 0l;
        long sumItrtTax = 0l;
        long sumOtherAmt = 0l;

        // 本頁小計
        for (int i = 0; i < rowCount; i++) {
            MonthlyRpt08Case amtData = amtList.get(i);
            sumIssueAmt += amtData.getIssueAmt().longValue();
            sumOtheraAmt += amtData.getOtheraAmt().longValue();
            sumOtherbAmt += amtData.getOtherbAmt().longValue();
            sumMitRate += amtData.getMitRate().longValue();
            sumOffsetAmt += amtData.getOffsetAmt().longValue();
            sumCompenAmt += amtData.getCompenAmt().longValue();
            sumInherItorAmt += amtData.getInherItorAmt().longValue();
            sumItrtTax += amtData.getItrtTax().longValue();
            sumOtherAmt += amtData.getOtherAmt().longValue();
        }
        if(reCompute==true){
        	allIssueAmt = 0l;
        	allOtheraAmt = 0l;
        	allOtherbAmt = 0l;
        	allMitRate = 0l;
        	allOffsetAmt = 0l;
        	allCompenAmt = 0l;
        	allInherItorAmt = 0l;
        	allItrtTax = 0l;
        	allOtherAmt = 0l;
        }
        
        // 總累計
        allIssueAmt += sumIssueAmt;
        allOtheraAmt += sumOtheraAmt;
        allOtherbAmt += sumOtherbAmt;
        allMitRate += sumMitRate;
        allOffsetAmt += sumOffsetAmt;
        allCompenAmt += sumCompenAmt;
        allInherItorAmt += sumInherItorAmt;
        allItrtTax +=  sumItrtTax;
        allOtherAmt += sumOtherAmt;
//        for (int j = rowBeg; j < rowEnd; j++) {
//            MonthlyRpt08Case caseData = caseList.get(j);
//            if (caseData.getIssuTyp().equals(issuTyp) && caseData.getPayTyp().equals(payTyp)) {
//                allIssueAmt += caseData.getIssueAmt().longValue();
//                allOtheraAmt += caseData.getOtheraAmt().longValue();
//                allOtherbAmt += caseData.getOtherbAmt().longValue();
//                allMitRate += caseData.getMitRate().longValue();
//                allOffsetAmt += caseData.getOffsetAmt().longValue();
//                allCompenAmt += caseData.getCompenAmt().longValue();
//                allInherItorAmt += caseData.getInherItorAmt().longValue();
//                allItrtTax += caseData.getItrtTax().longValue();
//                allOtherAmt += caseData.getOtherAmt().longValue();
//            }
//        }

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

        addColumn(table, 24, 0, RptTitleUtility.getDivisionTitle(payCode, DateUtility.getNowWestDate()), fontCh9, 0, LEFT);
        //addColumn(table, 24, 0, DeptName + "給付科", fontCh9, 0, LEFT);
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

    public ByteArrayOutputStream doReport(UserBean userData, List<MonthlyRpt08Case> caseData, List<MonthlyRpt08Case> caseData1, HashMap<String, Object> map) throws Exception {
        try {
            Table table = null;

            String issuTyp = null; // 案件類別
            String payTyp = null; // 給付方式
            String deptName = null; // 部門名稱
            String payCode = (String) map.get("payCode");
            String naChgMk = null;
            String nlWkMk = null;
            String adWkMk = null;

            // 2009.2.20 swim add 輸出報表檔至主機上
            String printDate = DateUtility.getNowChineseDate();
            // FileOutputStream bao = new FileOutputStream(ConstantKey.RPT_PATH + payCode + "_MonthlyRpt08_" + printDate + ".pdf");
            // FileOutputStream bao = new FileOutputStream("F:\\"+"MonthlyRpt08_" + printDate + ".pdf");
            writer = PdfWriter.getInstance(document, bao);

            document.open();

            // 印主要報表 START - 【NACHGMK is null】
            // ================================================================================================================
            // 201205 start

            
            // for Log
            // [
            /*
            pageNow = 0;
            String baseFileName = (String) map.get("baseFileName");
            int pagePerFile = Integer.parseInt((String) map.get("pagePerFile"));
            String logRptName = (String) map.get("logRptName");
            String logQryCondition = (String) map.get("logQryCondition");
            String logIssuYm = (String) map.get("logIssuYm");
            String logChkdate = (String) map.get("logChkdate");
            String logProcuser = (String) map.get("logProcuser");
            String logProctime = (String) map.get("logProctime");
            int seq = -1;
            String fileName = "";
            */ 
            // ]
                        
            // 建立資料Map
            // Map的Key 為 issuYm+payYm
            // Value則是同為該 nlWkMk + adWkMk 下的所有資料
            Map<String, List<MonthlyRpt08Case>> dataMap = new TreeMap<String, List<MonthlyRpt08Case>>();

            // Map<String, List<MonthlyRpt04Case>> dataMapFooter = new TreeMap<String, List<MonthlyRpt04Case>>(); //20120406
            for (int i = 0; i < caseData.size(); i++) {
                MonthlyRpt08Case obj = caseData.get(i);
                // dataMap.put(obj.getPayTyp() + obj.getNlWkMk() + obj.getAdWkMk(), new ArrayList<MonthlyRpt20Case>());
                dataMap.put(obj.getNlWkMk() + obj.getAdWkMk() + obj.getPayTyp() + obj.getIssuTyp(), new ArrayList<MonthlyRpt08Case>());
            }

            for (int i = 0; i < caseData.size(); i++) {
                MonthlyRpt08Case obj = caseData.get(i);
                // (dataMap.get(obj.getPayTyp() + obj.getNlWkMk() + obj.getAdWkMk())).add(obj);
                (dataMap.get(obj.getNlWkMk() + obj.getAdWkMk() + obj.getPayTyp() + obj.getIssuTyp())).add(obj);
            }
           
            for (Iterator it = dataMap.keySet().iterator(); it.hasNext();) {
            	allIssueAmt = 0l;
                allOtheraAmt = 0l;
                allOtherbAmt = 0l;
                allMitRate = 0l;
                allOffsetAmt = 0l;
                allCompenAmt = 0l;
                allInherItorAmt = 0l;
                allItrtTax = 0l;
                allOtherAmt = 0l;
                // 201205 end

                List<MonthlyRpt08Case> amtList = new ArrayList<MonthlyRpt08Case>();

                // 序號
                Integer seqNo = 1;
                // 計算每頁筆數
                Integer rowCount = 0;
                // 累計金額起始
                Integer rowBeg = 0;

                String key = (String) it.next();
                List<MonthlyRpt08Case> caseList = dataMap.get(key);

                // for (int i = 0; i < caseData.size(); i++) { // 受理案件資料
                // MonthlyRpt08Case monthlyRpt08Case = caseData.get(i);
                
                //List<MonthlyRpt08Case> tmpList =  new ArrayList<MonthlyRpt08Case>(caseData);
                int tmpListSize = caseList.size();
                int recCount = 0;
                for (int i = 0, len = caseList.size(); i < len; i++) { // 受理案件資料
                    MonthlyRpt08Case monthlyRpt08Case = caseList.get(i);
                    
                    if (recCount == 0) {
                        // 取得表頭
                        table = getHeader(true, monthlyRpt08Case.getPayCodeStr(monthlyRpt08Case.getAdWkMk(), monthlyRpt08Case.getNlWkMk(), "N"), monthlyRpt08Case.getIssuTypStr(), monthlyRpt08Case.getPayTypStr(), monthlyRpt08Case.getPayYm(), monthlyRpt08Case.getPayTyp(), map,payCode, monthlyRpt08Case.getcPrnDateStr());
                    }
                    else {
                        if (((rowCount) % 20 == 0) || !(monthlyRpt08Case.getNaChgMk()).equals(naChgMk) || !(monthlyRpt08Case.getNlWkMk()).equals(nlWkMk) || !(monthlyRpt08Case.getAdWkMk()).equals(adWkMk) || !(monthlyRpt08Case.getIssuTyp()).equals(issuTyp) || !(monthlyRpt08Case.getPayTyp()).equals(payTyp)) {
                            // 不滿20筆補空白行
                            if (rowCount % 20 > 0)
                                addEmptyRow(table, 20 - (rowCount % 20));

                            // 加入表尾
                            addFooter(table, monthlyRpt08Case.getDeptNameString(), amtList, rowCount, issuTyp, payTyp, rowBeg, recCount, payCode, false);
                            document.add(table);

                            // 取得表頭
                            table = getHeader(true, monthlyRpt08Case.getPayCodeStr(monthlyRpt08Case.getAdWkMk(), monthlyRpt08Case.getNlWkMk(), "N"), monthlyRpt08Case.getIssuTypStr(), monthlyRpt08Case.getPayTypStr(), monthlyRpt08Case.getPayYm(), monthlyRpt08Case.getPayTyp(), map,payCode, monthlyRpt08Case.getcPrnDateStr());

                            if (!(monthlyRpt08Case.getNaChgMk()).equals(naChgMk) || !(monthlyRpt08Case.getNlWkMk()).equals(nlWkMk) || !(monthlyRpt08Case.getAdWkMk()).equals(adWkMk) || !(monthlyRpt08Case.getIssuTyp()).equals(issuTyp) || !(monthlyRpt08Case.getPayTyp()).equals(payTyp)) {
                                // 序號歸零
                                seqNo = 1;
                                rowBeg = recCount;
                                allIssueAmt = 0l;
                                allOtheraAmt = 0l;
                                allOtherbAmt = 0l;
                                allMitRate = 0l;
                                allOffsetAmt = 0l;
                                allCompenAmt = 0l;
                                allInherItorAmt = 0l;
                                allItrtTax = 0l;
                                allOtherAmt = 0l;
                                
                                
                            }

                            rowCount = 0;
                        }
                    }
                    // 案件類別
                    issuTyp = monthlyRpt08Case.getIssuTyp();
                    // 給付方式
                    payTyp = monthlyRpt08Case.getPayTyp();
                    // 部門名稱
                    deptName = monthlyRpt08Case.getDeptNameString();

                    naChgMk = monthlyRpt08Case.getNaChgMk();
                    nlWkMk = monthlyRpt08Case.getNlWkMk();
                    adWkMk = monthlyRpt08Case.getAdWkMk();

                    // 序號
                    addColumn(table, 3, 1, Integer.toString(seqNo), fontCh9, 1, CENTER);
                    // 受理編號
                    addColumn(table, 7, 1, monthlyRpt08Case.getApNoStr(), fontCh9, 1, CENTER);
                    // 給付年月
                    addColumn(table, 3, 1, DateUtility.changeWestYearMonthType(monthlyRpt08Case.getPayYm()), fontCh9, 1, CENTER);
                    // 金融機構轉帳帳號
                    if (payTyp.equals(ConstantKey.BAAPPBASE_PAYTYP_STR_A))
                        addColumn(table, 11, 1, monthlyRpt08Case.getBenIdn(), fontCh9, 1, CENTER);
                    else
                        addColumn(table, 11, 1, monthlyRpt08Case.getPayBankIdStr(), fontCh9, 1, CENTER);
                    // 受款人姓名
                    addColumn(table, 5, 1, monthlyRpt08Case.getBenName(), fontCh9, 1, CENTER);
                    // 核定金額(A)
                    addColumn(table, 5, 1, ReportBase.formatNumber(monthlyRpt08Case.getIssueAmt().longValue()), fontCh9, 1, RIGHT);
                    // 另案扣減同保險(B)
                    addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt08Case.getOtheraAmt().longValue()), fontCh9, 1, RIGHT);
                    // 另案扣減他保險(C)
                    addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt08Case.getOtherbAmt().longValue()), fontCh9, 1, RIGHT);
                    // 匯費(D)
                    addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt08Case.getMitRate().longValue()), fontCh9, 1, RIGHT);
                    // 抵銷紓困(E)
                    addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt08Case.getOffsetAmt().longValue()), fontCh9, 1, RIGHT);
                    // 代扣補償金(F)
                    addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt08Case.getCompenAmt().longValue()), fontCh9, 1, RIGHT);
                    // 保留遺屬津貼(G)
                    addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt08Case.getInherItorAmt().longValue()), fontCh9, 1, RIGHT);
                    // 代扣利息所得稅(H)
                    addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt08Case.getItrtTax().longValue()), fontCh9, 1, RIGHT);
                    // 其他(I)
                    addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt08Case.getOtherAmt().longValue()), fontCh9, 1, RIGHT);

                    // for Log
                    // [
                    /*
                    int pageOffset = 1;
                    if (pageNow % pagePerFile == 0)
                        pageOffset = 0;
                    monthlyRpt08Case.setRptName(logRptName);
                    monthlyRpt08Case.setQryCondition(logQryCondition);
                    // private String apNo;
                    monthlyRpt08Case.setIssuYm(logIssuYm);
                    // private String payYm;
                    monthlyRpt08Case.setChkdate(logChkdate);
                    monthlyRpt08Case.setPage(new BigDecimal(pageNow));
                    monthlyRpt08Case.setFilename(baseFileName + String.format("%03d", ((pageNow / pagePerFile) + pageOffset)) + ".pdf");
                    monthlyRpt08Case.setProcuser(logProcuser);
                    monthlyRpt08Case.setProctime(logProctime);
                    */
                    // ]
                    
                    // 計算本頁小計用
                    amtList.add(monthlyRpt08Case);

                    seqNo++;
                    rowCount++;
                    
                    recCount = recCount + 1; 
                    caseList.remove(i);
                    len = len - 1;
                    i = i - 1;
                    /*
                     * addColumn(table, 66, 1, " " , fontCh10, 1, CENTER); addColumn(table, 66, 1, " " , fontCh10, 1, CENTER); addColumn(table, 66, 1, " " , fontCh10, 1, CENTER); addColumn(table, 66, 1, " " , fontCh10, 1, CENTER); addColumn(table, 66, 1, " " , fontCh10, 1, CENTER); addColumn(table, 66, 1, " " , fontCh10, 1, CENTER); if (!writer.fitsPage(table)) // 超過一頁所能顯示的行數 { // 刪除造成換頁的該筆資料 (一筆有一行) table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow();
                     * table.deleteLastRow(); table.deleteLastRow(); // 不滿20筆補空白行 if(rowCount % 20 > 0) addEmptyRow(table, 20 - (rowCount % 20)); // 加入表尾 addFooter(table, deptName, amtList, rowCount, caseData, issuTyp, payTyp, rowBeg, i); document.add(table); // 加入表頭 table = getHeader(true, monthlyRpt08Case.getPayCodeStr(), monthlyRpt08Case.getIssuTypStr(), monthlyRpt08Case.getPayTypStr(), monthlyRpt08Case.getPayYm(), monthlyRpt08Case.getPayTyp(), map); rowCount = 0; } else { // 刪除造成換頁的該筆資料
                     * (一筆有一行) table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); }
                     */
                }// for(int i=0;i<caseData.size();i++) end

                /*
                 * if (!writer.fitsPage(table)) // 超過一頁所能顯示的行數 { // 刪除造成換頁的該筆資料 (一筆有一行) table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); // 不滿20筆補空白行 if(rowCount % 20 > 0) addEmptyRow(table, 20 - (rowCount % 20)); // 加入表尾 addFooter(table, deptName, amtList, rowCount, caseData, issuTyp, payTyp, rowBeg, caseData.size()); document.add(table); } else { }
                 */

                // 不滿20筆補空白行
                if (rowCount % 20 > 0)
                    addEmptyRow(table, 20 - (rowCount % 20));
                
                // 加入表尾
                addFooter(table, deptName, amtList, rowCount, issuTyp, payTyp, rowBeg, tmpListSize, payCode, false);
                document.add(table);
            } // 201205 分頁
              // ================================================================================================================
              // 印主要報表 END - 【NACHGMK is null】

            // 印主要報表 START - 【NACHGMK is not null】
            // ================================================================================================================
            // 201205 start

            // 建立資料Map
            // Map的Key 為 issuYm+payYm
            // Value則是同為該 nlWkMk + adWkMk 下的所有資料
            Map<String, List<MonthlyRpt08Case>> dataMap1 = new TreeMap<String, List<MonthlyRpt08Case>>();

            // Map<String, List<MonthlyRpt04Case>> dataMapFooter = new TreeMap<String, List<MonthlyRpt04Case>>(); //20120406
            for (int i = 0; i < caseData1.size(); i++) {
                MonthlyRpt08Case obj = caseData1.get(i);
                // dataMap.put(obj.getPayTyp() + obj.getNlWkMk() + obj.getAdWkMk(), new ArrayList<MonthlyRpt20Case>());
                dataMap1.put(obj.getNaChgMk() + obj.getNlWkMk() + obj.getAdWkMk() + obj.getPayTyp() + obj.getIssuTyp(), new ArrayList<MonthlyRpt08Case>());
            }

            for (int i = 0; i < caseData1.size(); i++) {
                MonthlyRpt08Case obj = caseData1.get(i);
                // (dataMap.get(obj.getPayTyp() + obj.getNlWkMk() + obj.getAdWkMk())).add(obj);
                (dataMap1.get(obj.getNaChgMk() + obj.getNlWkMk() + obj.getAdWkMk() + obj.getPayTyp() + obj.getIssuTyp())).add(obj);
            }
            for (Iterator it = dataMap1.keySet().iterator(); it.hasNext();) {
            	//重新計算總計
            	allIssueAmt = 0l;
                allOtheraAmt = 0l;
                allOtherbAmt = 0l;
                allMitRate = 0l;
                allOffsetAmt = 0l;
                allCompenAmt = 0l;
                allInherItorAmt = 0l;
                allItrtTax = 0l;
                allOtherAmt = 0l;
                // 201205 end

                List<MonthlyRpt08Case> amtList = new ArrayList<MonthlyRpt08Case>();

                // 序號
                Integer seqNo = 1;
                // 計算每頁筆數
                Integer rowCount = 0;
                // 累計金額起始
                Integer rowBeg = 0;

                String key = (String) it.next();
                List<MonthlyRpt08Case> caseList = dataMap1.get(key);

                // for (int i = 0; i < caseData.size(); i++) { // 受理案件資料
                // MonthlyRpt08Case monthlyRpt08Case = caseData.get(i);
                
                //List<MonthlyRpt08Case> tmpList =  new ArrayList<MonthlyRpt08Case>(caseData);
                int tmpListSize = caseList.size();
                int recCount = 0;
                //執行完一次就把list最上層一筆刪除
                for (int i = 0, len = caseList.size(); i < len; i++) { // 受理案件資料
                    MonthlyRpt08Case monthlyRpt08Case = caseList.get(i);
                    
                    if (recCount == 0) {
                        // 取得表頭
                        table = getHeader(true, monthlyRpt08Case.getPayCodeStr(monthlyRpt08Case.getAdWkMk(), monthlyRpt08Case.getNlWkMk(), "Y"), monthlyRpt08Case.getIssuTypStr(), monthlyRpt08Case.getPayTypStr(), monthlyRpt08Case.getPayYm(), monthlyRpt08Case.getPayTyp(), map,payCode, monthlyRpt08Case.getcPrnDateStr());
                    }
                    else {
                        if (((rowCount) % 20 == 0) || !(monthlyRpt08Case.getNaChgMk()).equals(naChgMk) || !(monthlyRpt08Case.getNlWkMk()).equals(nlWkMk) || !(monthlyRpt08Case.getAdWkMk()).equals(adWkMk) || !(monthlyRpt08Case.getIssuTyp()).equals(issuTyp) || !(monthlyRpt08Case.getPayTyp()).equals(payTyp)) {
                            // 不滿20筆補空白行
                            if (rowCount % 20 > 0)
                                addEmptyRow(table, 20 - (rowCount % 20));

                            // 加入表尾
                            addFooter(table, monthlyRpt08Case.getDeptNameString(), amtList, rowCount, issuTyp, payTyp, rowBeg, recCount, payCode, false);
                            document.add(table);

                            // 取得表頭
                            table = getHeader(true, monthlyRpt08Case.getPayCodeStr(monthlyRpt08Case.getAdWkMk(), monthlyRpt08Case.getNlWkMk(), "Y"), monthlyRpt08Case.getIssuTypStr(), monthlyRpt08Case.getPayTypStr(), monthlyRpt08Case.getPayYm(), monthlyRpt08Case.getPayTyp(), map,payCode, monthlyRpt08Case.getcPrnDateStr());

                            if (!(monthlyRpt08Case.getNaChgMk()).equals(naChgMk) || !(monthlyRpt08Case.getNlWkMk()).equals(nlWkMk) || !(monthlyRpt08Case.getAdWkMk()).equals(adWkMk) || !(monthlyRpt08Case.getIssuTyp()).equals(issuTyp) || !(monthlyRpt08Case.getPayTyp()).equals(payTyp)) {
                                // 序號歸零
                                seqNo = 1;
                                rowBeg = recCount;
                                allIssueAmt = 0l;
                                allOtheraAmt = 0l;
                                allOtherbAmt = 0l;
                                allMitRate = 0l;
                                allOffsetAmt = 0l;
                                allCompenAmt = 0l;
                                allInherItorAmt = 0l;
                                allItrtTax = 0l;
                                allOtherAmt = 0l;
                            }

                            rowCount = 0;
                        }
                    }
                    // 案件類別
                    issuTyp = monthlyRpt08Case.getIssuTyp();
                    // 給付方式
                    payTyp = monthlyRpt08Case.getPayTyp();
                    // 部門名稱
                    deptName = monthlyRpt08Case.getDeptNameString();

                    naChgMk = monthlyRpt08Case.getNaChgMk();
                    nlWkMk = monthlyRpt08Case.getNlWkMk();
                    adWkMk = monthlyRpt08Case.getAdWkMk();

                    // 序號
                    addColumn(table, 3, 1, Integer.toString(seqNo), fontCh9, 1, CENTER);
                    // 受理編號
                    addColumn(table, 7, 1, monthlyRpt08Case.getApNoStr(), fontCh9, 1, CENTER);
                    // 給付年月
                    addColumn(table, 3, 1, DateUtility.changeWestYearMonthType(monthlyRpt08Case.getPayYm()), fontCh9, 1, CENTER);
                    // 金融機構轉帳帳號
                    if (payTyp.equals(ConstantKey.BAAPPBASE_PAYTYP_STR_A))
                        addColumn(table, 11, 1, monthlyRpt08Case.getBenIdn(), fontCh9, 1, CENTER);
                    else
                        addColumn(table, 11, 1, monthlyRpt08Case.getPayBankIdStr(), fontCh9, 1, CENTER);
                    // 受款人姓名
                    addColumn(table, 5, 1, monthlyRpt08Case.getBenName(), fontCh9, 1, CENTER);
                    // 核定金額(A)
                    addColumn(table, 5, 1, ReportBase.formatNumber(monthlyRpt08Case.getIssueAmt().longValue()), fontCh9, 1, RIGHT);
                    // 另案扣減同保險(B)
                    addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt08Case.getOtheraAmt().longValue()), fontCh9, 1, RIGHT);
                    // 另案扣減他保險(C)
                    addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt08Case.getOtherbAmt().longValue()), fontCh9, 1, RIGHT);
                    // 匯費(D)
                    addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt08Case.getMitRate().longValue()), fontCh9, 1, RIGHT);
                    // 抵銷紓困(E)
                    addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt08Case.getOffsetAmt().longValue()), fontCh9, 1, RIGHT);
                    // 代扣補償金(F)
                    addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt08Case.getCompenAmt().longValue()), fontCh9, 1, RIGHT);
                    // 保留遺屬津貼(G)
                    addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt08Case.getInherItorAmt().longValue()), fontCh9, 1, RIGHT);
                    // 代扣利息所得稅(H)
                    addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt08Case.getItrtTax().longValue()), fontCh9, 1, RIGHT);
                    // 其他(I)
                    addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt08Case.getOtherAmt().longValue()), fontCh9, 1, RIGHT);

                    // for Log
                    // [
                    /*
                    int pageOffset = 1;
                    if (pageNow % pagePerFile == 0)
                        pageOffset = 0;
                    monthlyRpt08Case.setRptName(logRptName);
                    monthlyRpt08Case.setQryCondition(logQryCondition);
                    // private String apNo;
                    monthlyRpt08Case.setIssuYm(logIssuYm);
                    // private String payYm;
                    monthlyRpt08Case.setChkdate(logChkdate);
                    monthlyRpt08Case.setPage(new BigDecimal(pageNow));
                    
                    if (seq != ((pageNow / pagePerFile) + pageOffset)) {
                        seq = ((pageNow / pagePerFile) + pageOffset);
                        fileName = baseFileName + String.format("%03d", seq) + ".pdf";
                    }
                    
                    monthlyRpt08Case.setFilename(fileName);
                    monthlyRpt08Case.setProcuser(logProcuser);
                    monthlyRpt08Case.setProctime(logProctime);
                    */
                    // ]
                    
                    // 計算本頁小計用
                    amtList.add(monthlyRpt08Case);

                    seqNo++;
                    rowCount++;
                    //執行完一次就把list最上層一筆刪除
                    recCount = recCount + 1; 
                    caseList.remove(i);
                    len = len - 1;
                    i = i - 1;
                    /*
                     * addColumn(table, 66, 1, " " , fontCh10, 1, CENTER); addColumn(table, 66, 1, " " , fontCh10, 1, CENTER); addColumn(table, 66, 1, " " , fontCh10, 1, CENTER); addColumn(table, 66, 1, " " , fontCh10, 1, CENTER); addColumn(table, 66, 1, " " , fontCh10, 1, CENTER); addColumn(table, 66, 1, " " , fontCh10, 1, CENTER); if (!writer.fitsPage(table)) // 超過一頁所能顯示的行數 { // 刪除造成換頁的該筆資料 (一筆有一行) table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow();
                     * table.deleteLastRow(); table.deleteLastRow(); // 不滿20筆補空白行 if(rowCount % 20 > 0) addEmptyRow(table, 20 - (rowCount % 20)); // 加入表尾 addFooter(table, deptName, amtList, rowCount, caseData, issuTyp, payTyp, rowBeg, i); document.add(table); // 加入表頭 table = getHeader(true, monthlyRpt08Case.getPayCodeStr(), monthlyRpt08Case.getIssuTypStr(), monthlyRpt08Case.getPayTypStr(), monthlyRpt08Case.getPayYm(), monthlyRpt08Case.getPayTyp(), map); rowCount = 0; } else { // 刪除造成換頁的該筆資料
                     * (一筆有一行) table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); }
                     */
                }// for(int i=0;i<caseData.size();i++) end

                /*
                 * if (!writer.fitsPage(table)) // 超過一頁所能顯示的行數 { // 刪除造成換頁的該筆資料 (一筆有一行) table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); // 不滿20筆補空白行 if(rowCount % 20 > 0) addEmptyRow(table, 20 - (rowCount % 20)); // 加入表尾 addFooter(table, deptName, amtList, rowCount, caseData, issuTyp, payTyp, rowBeg, caseData.size()); document.add(table); } else { }
                 */

                // 不滿20筆補空白行
                if (rowCount % 20 > 0)
                    addEmptyRow(table, 20 - (rowCount % 20));

                // 加入表尾
                addFooter(table, deptName, amtList, rowCount, issuTyp, payTyp, rowBeg, tmpListSize, payCode, false);
                document.add(table);
            } // 201205 分頁
              // ================================================================================================================
              // 印主要報表 END - 【NACHGMK is not null】
            document.close();
        }
        finally {
            document.close();
        }
        return bao;
    }
}
