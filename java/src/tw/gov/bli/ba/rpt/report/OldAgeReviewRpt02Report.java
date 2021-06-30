package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt02Case;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;
import tw.gov.bli.ba.util.StringUtility;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Table;

public class OldAgeReviewRpt02Report extends ReportBase {

    private RptService rptService;

    public OldAgeReviewRpt02Report() throws Exception {
        super();
    }

    public Document createDocument() {
        Document document = new Document(PageSize.A4, 30, 30, 30, 30);
        return document;
    }

    public Table getHeader(boolean bPrintMainHeader, String payCode, String payCodeStr, String issuYmStr, String chkMan, String mexcLvlStr, String rptDateStr, BigDecimal pageNo) throws Exception {
        document.newPage();
        // 建立表格
        Table table = createTable(100);
        table.setAutoFillEmptyCells(true);
        table.setPadding(2.0f);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        addColumn(table, 100, 1, " ", fontCh8, 0, CENTER);

        // 列印大標題
        if (bPrintMainHeader) {
            addColumnAssignVAlignment(table, 30, 1, "報表編號：BALP0D020", fontCh10, 0, LEFT, BOTTOM);
            addColumnAssignVAlignment(table, 45, 1, RptTitleUtility.getOrgTitle(DateUtility.getNowWestDate())+"　"+RptTitleUtility.getGroupsTitle(payCode, DateUtility.getNowWestDate()), fontCh18, 0, CENTER, BOTTOM);
            //addColumnAssignVAlignment(table, 40, 1, "勞工保險局　給付處", fontCh18, 0, CENTER, BOTTOM);
            addColumnAssignVAlignment(table, 10, 1, "", fontCh8, 0, LEFT, BOTTOM);
            addColumnAssignVAlignment(table, 15, 1, "頁　　次：" + getBigDecimalValue(pageNo), fontCh10, 0, LEFT, BOTTOM);

            addColumnAssignVAlignment(table, 30, 1, "", fontCh8, 0, LEFT, TOP);
            addColumnAssignVAlignment(table, 40, 1, "審核給付清單", fontCh18, 0, CENTER, TOP);
            addColumnAssignVAlignment(table, 10, 1, "", fontCh8, 0, LEFT, BOTTOM);
            addColumnAssignVAlignment(table, 20, 1, "日　　期：" + DateUtility.formatChineseDateTimeString(DateUtility.getNowChineseDate(), false), fontCh10, 0, LEFT, TOP);

            addColumn(table, 100, 1, " ", fontCh10, 0, CENTER);

            String barCode1 = chkMan;// 條碼一: [審核人員]
            // 20111115 by Kiyomi 條碼(barCode2)不要包含「給付別」
            //String barCode2 = payCode + rptDateStr + StringUtils.leftPad(getBigDecimalValue(pageNo), 3, "0");// 條碼二: [給付別]+[打包日期(民國年)]+[頁次(不足3碼的以0補滿3碼再轉成條碼)]
            String barCode2 = rptDateStr + StringUtils.leftPad(getBigDecimalValue(pageNo), 3, "0");// 條碼二: [打包日期(民國年)]+[頁次(不足3碼的以0補滿3碼再轉成條碼)]
            addBarcode39(table, barCode1.toUpperCase(), 100, 80, 60, 0, 0, CENTER, MIDDLE);
            addBarcode39(table, barCode2.toUpperCase(), 100, 80, 40, 0, 0, RIGHT, MIDDLE);

            addColumn(table, 23, 1, "給付別：" + payCodeStr, fontCh10, 0, LEFT);
            addColumn(table, 23, 1, "核定年月：" + issuYmStr, fontCh10, 0, LEFT);
            addColumn(table, 24, 1, "審核人員：" + chkMan, fontCh10, 0, LEFT);
            addColumn(table, 30, 1, "應決行層級：" + mexcLvlStr, fontCh10, 0, LEFT);
        }

        addColumn(table, 3, 1, "序號", fontCh10, 1, CENTER);
        addColumn(table, 15, 1, "受理編號", fontCh10, 1, CENTER);
        addColumn(table, 9, 1, "給付年月\r\n起迄", fontCh10, 1, CENTER);
        addColumn(table, 3, 1, "版次", fontCh10, 1, CENTER);
        addColumn(table, 25, 1, "事故者姓名", fontCh10, 1, CENTER);
        addColumn(table, 12, 1, "核定金額", fontCh10, 1, CENTER);
        addColumn(table, 12, 1, "實付金額", fontCh10, 1, CENTER);
        addColumn(table, 10, 1, "資料別", fontCh10, 1, CENTER);
        addColumn(table, 11, 1, "審核日期", fontCh10, 1, CENTER);

        return table;
    }

    public void addFooter(Table table, BigDecimal sumTissueAmt, BigDecimal sumTaplpayAmt) throws Exception {
        // 合計
        addColumn(table, 55, 1, "合  計", fontCh10, 1, CENTER);
        addColumn(table, 12, 1, ReportBase.formatBigDecimalToInteger(sumTissueAmt), fontCh10, 1, RIGHT);
        addColumn(table, 12, 1, ReportBase.formatBigDecimalToInteger(sumTaplpayAmt), fontCh10, 1, RIGHT);
        addColumn(table, 21, 1, "　\r\n　", fontCh10, 1, CENTER);
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<OldAgeReviewRpt02Case> caseData) throws Exception {
        try {
            document.open();
            Table table = null;
            String chkDate = null;
            String mexcLvl = null;

            // 建立資料Map
            // Map的Key 為 issuYm+payYm
            // Value則是同為該 payTyp+issuYm+mexcLvl 下的所有編審註記資料
            Map<String, List<OldAgeReviewRpt02Case>> map = new TreeMap<String, List<OldAgeReviewRpt02Case>>();
            for (int i = 0; i < caseData.size(); i++) {
                OldAgeReviewRpt02Case caseObj = (OldAgeReviewRpt02Case) caseData.get(i);
                if (map.get(caseObj.getRptDate() + caseObj.getPageNo()) == null) {
                    List<OldAgeReviewRpt02Case> list = new ArrayList<OldAgeReviewRpt02Case>();
                    list.add(caseObj);
                    map.put(caseObj.getRptDate() + caseObj.getPageNo(), list);
                }
                else {
                    (map.get(caseObj.getRptDate() + caseObj.getPageNo())).add(caseObj);
                }
            }

            for (Iterator it = map.keySet().iterator(); it.hasNext();) {
                BigDecimal sumTissueAmt = new BigDecimal(0);// 核定金額合計
                BigDecimal sumTaplpayAmt = new BigDecimal(0);// 實付金額合計
                Integer seqNo = 1;// 序號

                String key = (String) it.next();
                List<OldAgeReviewRpt02Case> tempList = map.get(key);
                for (int i = 0; i < tempList.size(); i++) {
                    OldAgeReviewRpt02Case tmpCase = tempList.get(i);
                    // 取第一筆列印表頭資料
                    if (i == 0) {
                        table = getHeader(true, tmpCase.getPayCode(), tmpCase.getPayCodeStr(), tmpCase.getIssuYmStr(), tmpCase.getChkMan(), tmpCase.getMexcLvlStr(), tmpCase.getRptDateStr(), tmpCase.getPageNo());
                    }
                    // 序號
                    addColumn(table, 3, 1, Integer.toString(seqNo), fontCh10, 1, CENTER);
                    // 受理編號
                    addColumn(table, 15, 1, tmpCase.getApNoStr(), fontCh10, 1, CENTER);
                    // 給付年月起迄
                    addColumn(table, 9, 1, tmpCase.getPaysYmStr() + "~\r\n" + tmpCase.getPayeYmStr(), fontCh10, 1, LEFT);
                    // 版次
                    addColumn(table, 3, 1, tmpCase.getVeriSeq(), fontCh10, 1, LEFT);
                    // 事故者姓名
                    addColumn(table, 25, 1, tmpCase.getEvtName(), fontCh10, 1, LEFT);
                    // 核定金額
                    addColumn(table, 12, 1, ReportBase.formatBigDecimalToInteger(tmpCase.getTissueAmt()), fontCh10, 1, RIGHT);
                    // 實付金額 (當[核定金額]=[實付金額]時,[實付金額]顯示空白,不要Show資料)
                    if ((tmpCase.getTissueAmt()).compareTo(tmpCase.getTaplPayAmt()) == 0) {
                        addColumn(table, 12, 1, "", fontCh10, 1, RIGHT);
                    }
                    else {
                        addColumn(table, 12, 1, ReportBase.formatBigDecimalToInteger(tmpCase.getTaplPayAmt()), fontCh10, 1, RIGHT);
                    }
                    // 資料別
                    addColumn(table, 10, 1, tmpCase.getCaseTypStr(), fontCh10, 1, LEFT);
                    // 審核日期
                    addColumn(table, 11, 1, DateUtility.formatChineseDateTimeString(tmpCase.getChkDateStr(), false), fontCh10, 1, CENTER);

                    sumTissueAmt = sumTissueAmt.add(tmpCase.getTissueAmt());// 核定金額合計
                    sumTaplpayAmt = sumTaplpayAmt.add(tmpCase.getTaplPayAmt());// 實付金額合計

                    seqNo++;
                }

                while (seqNo <= 20) {
                    addColumn(table, 3, 1, Integer.toString(seqNo), fontCh10, 1, CENTER);
                    addColumn(table, 15, 1, "", fontCh10, 1, CENTER);
                    addColumn(table, 9, 1, "　\r\n　", fontCh10, 1, LEFT);
                    addColumn(table, 3, 1, "", fontCh10, 1, LEFT);
                    addColumn(table, 25, 1, "", fontCh10, 1, LEFT);
                    addColumn(table, 12, 1, "", fontCh10, 1, RIGHT);
                    addColumn(table, 12, 1, "", fontCh10, 1, RIGHT);
                    addColumn(table, 10, 1, "", fontCh10, 1, LEFT);
                    addColumn(table, 11, 1, "", fontCh10, 1, CENTER);
                    seqNo++;
                }
                // 加入表尾
                addFooter(table, sumTissueAmt, sumTaplpayAmt);
                document.add(table);
            }

            document.close();
        }
        finally {
            document.close();
        }
        return bao;
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}
