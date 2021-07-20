package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.owasp.encoder.Encode;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfSmartCopy;

import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt23Case;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ReportUtility;
import tw.gov.bli.ba.util.RptTitleUtility;
import tw.gov.bli.common.helper.HttpHelper;
import tw.gov.bli.common.util.ExceptionUtil;

/**
 * 郵政匯票通知／入戶匯款證明
 * 
 * @author Evelyn Hsu
 */

public class MonthlyRptK23Report extends ReportBase {

    private int nPage; // 每筆資料的頁數

    private static final String TEMPLATE_FILE = "/pdf/MonthlyRpt23.pdf";

    public MonthlyRptK23Report() throws Exception {
        super();
        nPage = 0;
    }

    public Document createDocument() {
        // 參數說明: Document(頁面大小, 左邊空白, 右邊空白, 上面空白, 下面空白)
        Document document = new Document(PageSize.A4, 90, 90, 10, 10);
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
            addColumn(table, 17, 1, "　", fontCh10, 0, LEFT); // 注意: 內容是全形空白
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

    public Table addHeader() throws Exception {
        document.newPage();

        // 建立表格
        Table table = createTable(17);
        table.setAutoFillEmptyCells(true);

        addEmptyRow(table, 3);

        return table;
    }

    public ByteArrayOutputStream doReport(InputStream inFile, UserBean userData, List<MonthlyRpt23Case> caseList, HashMap<String, Object> map) throws Exception {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // Document document = new Document();
        // Rectangle rectangle = new Rectangle(576, 862);
        Document document = new Document(PageSize.A4);

        try {

            String payCode = (String) map.get("payCode");

            // 2009.2.20 EvelynHsu add 輸出報表檔至主機上
            String printDate = DateUtility.getNowChineseDate();
            // FileOutputStream bao = new FileOutputStream(PropertyHelper.getProperty("rpt.path") + Encode.forJava(payCode) + "_MonthlyRpt23_" + Encode.forJava(printDate) + ".pdf");
            // 讀入 Template 檔
            byte[] templateFile;
            if (inFile == null) {
                InputStream in = null;
                try {
                    in = HttpHelper.getHttpRequest().getSession(true).getServletContext().getResourceAsStream(TEMPLATE_FILE);
                    templateFile = IOUtils.toByteArray(in);
                }
                finally {
                    ExceptionUtil.safeColse(in);
                }
            }
            else {
                templateFile = IOUtils.toByteArray(inFile);
            }
            HashMap<String, String> mapSend = new HashMap<String, String>();

            byte[] tempResultFile = null;
            byte[] resultFile = null;

            PdfSmartCopy copy;
            if (inFile == null) {
                copy = new PdfSmartCopy(document, out);
            }
            else {
                copy = new PdfSmartCopy(document, bao);
            }

            document.open();

            for (int i = 0; i < caseList.size(); i = i + 2) {

                document.newPage();
                mapSend.clear();
                MonthlyRpt23Case detailData = caseList.get(i);
                // 50093111
                mapSend.put("account1_1", StringUtils.defaultString("5"));
                mapSend.put("account1_2", StringUtils.defaultString("0"));
                mapSend.put("account1_3", StringUtils.defaultString("0"));
                mapSend.put("account1_4", StringUtils.defaultString("9"));
                mapSend.put("account1_5", StringUtils.defaultString("3"));
                mapSend.put("account1_6", StringUtils.defaultString("1"));
                mapSend.put("account1_7", StringUtils.defaultString("1"));
                mapSend.put("account1_8", StringUtils.defaultString("1"));
                mapSend.put("accountName1", StringUtils.defaultString(RptTitleUtility.getOrgGroupTitle(DateUtility.getNowWestDate())));
                mapSend.put("code1", StringUtils.defaultString("0410"));
                mapSend.put("commZip1", StringUtils.defaultString(detailData.getCommZip()));
                if (detailData.getCommAddr().length() > 36) {
                    mapSend.put("caddr1_1", StringUtils.defaultString(detailData.getCommAddr().substring(0, 18)));
                    mapSend.put("caddr1_2", StringUtils.defaultString(detailData.getCommAddr().substring(18, 36)));
                    mapSend.put("caddr1_3", StringUtils.defaultString(detailData.getCommAddr().substring(36, detailData.getCommAddr().length())));

                }
                else {
                    if (detailData.getCommAddr().length() <= 20) {
                        mapSend.put("caddr1_1", StringUtils.defaultString(detailData.getCommAddr()));
                    }
                    else {
                        mapSend.put("caddr1_1", StringUtils.defaultString(detailData.getCommAddr().substring(0, 18)));
                        mapSend.put("caddr1_2", StringUtils.defaultString(detailData.getCommAddr().substring(18, detailData.getCommAddr().length())));
                    }
                }
                mapSend.put("accName1", StringUtils.defaultString(detailData.getAccName()));
                mapSend.put("appNo1", StringUtils.defaultString(detailData.getApNoString() + " " + detailData.getIssuYmString() + " " + detailData.getPayYmString()));
                mapSend.put("payAmt1", StringUtils.defaultString(toBigAmt(detailData.getPayAmt())));

                if (i + 1 < caseList.size()) {
                    MonthlyRpt23Case detailData2 = caseList.get(i + 1);
                    mapSend.put("account2_1", StringUtils.defaultString("5"));
                    mapSend.put("account2_2", StringUtils.defaultString("0"));
                    mapSend.put("account2_3", StringUtils.defaultString("0"));
                    mapSend.put("account2_4", StringUtils.defaultString("9"));
                    mapSend.put("account2_5", StringUtils.defaultString("3"));
                    mapSend.put("account2_6", StringUtils.defaultString("1"));
                    mapSend.put("account2_7", StringUtils.defaultString("1"));
                    mapSend.put("account2_8", StringUtils.defaultString("1"));
                    mapSend.put("accountName2", StringUtils.defaultString(RptTitleUtility.getOrgGroupTitle(DateUtility.getNowWestDate())));
                    mapSend.put("code2", StringUtils.defaultString("0410"));
                    mapSend.put("commZip2", StringUtils.defaultString(detailData2.getCommZip()));
                    if (detailData2.getCommAddr().length() > 36) {
                        mapSend.put("caddr2_1", StringUtils.defaultString(detailData2.getCommAddr().substring(0, 18)));
                        mapSend.put("caddr2_2", StringUtils.defaultString(detailData2.getCommAddr().substring(18, 36)));
                        mapSend.put("caddr2_3", StringUtils.defaultString(detailData2.getCommAddr().substring(36, detailData2.getCommAddr().length())));
                    }
                    else {
                        if (detailData2.getCommAddr().length() <= 20) {
                            mapSend.put("caddr2_1", StringUtils.defaultString(detailData2.getCommAddr()));
                        }
                        else {
                            mapSend.put("caddr2_1", StringUtils.defaultString(detailData2.getCommAddr().substring(0, 18)));
                            mapSend.put("caddr2_2", StringUtils.defaultString(detailData2.getCommAddr().substring(18, detailData2.getCommAddr().length())));
                        }
                    }

                    mapSend.put("accName2", StringUtils.defaultString(detailData2.getAccName()));
                    mapSend.put("appNo2", StringUtils.defaultString(detailData2.getApNoString() + " " + detailData2.getIssuYmString() + " " + detailData2.getPayYmString()));
                    mapSend.put("payAmt2", StringUtils.defaultString(toBigAmt(detailData2.getPayAmt())));

                }

                ReportUtility.templateReport(templateFile, mapSend, copy);

            }

            document.close();
            // 20180103 輸出報表檔至主機上 因為FORTIY 修改寫法
            if (inFile == null) {
                printToDisk((PropertyHelper.getProperty("rpt.path") + Encode.forJava(payCode) + "_MonthlyRptK23_" + Encode.forJava(printDate) + ".pdf"), out);
            }
            else {
                printToDisk((PropertyHelper.getProperty("rpt.path") + Encode.forJava(payCode) + "_MonthlyRptK23_" + Encode.forJava(printDate) + ".pdf"), bao);
            }

        }
        finally {
            document.close();
        }

        if (inFile == null) {
            return out;
        }
        else {
            return bao;
        }
    }

}
