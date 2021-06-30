package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import com.lowagie.text.Element;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.Image;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt30Case;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;

public class MonthlyRpt30Report extends ReportBase {

    public MonthlyRpt30Report() throws Exception {
        super();
    }

    public Document createDocument() {
        Rectangle rectangle = new Rectangle(576, 862);
        Document document = new Document(rectangle, 10, 10, 0, 0);
        return document;
    }

    public Table printPage(MonthlyRpt30Case caseData, HashMap<String, Object> map) throws Exception {
        String rptKind = (String) map.get("rptKind");

        document.newPage();
        // 建立表格
        Table table = createTable(60);

        // 修改時打開 方便看 報表種類
        // addColumn(table, 60, 1, rptKind + " " + caseData.getNotifyForm(), fontCh8, 0, CENTER);
        addColumn(table, 60, 1, "　", fontCh8, 0, CENTER);

        addColumn(table, 15, 1, " ", fontCh20, 0, RIGHT);
        addColumnAssignLineSpace(table, 30, 1, RptTitleUtility.getRpt05Title(DateUtility.getNowWestDate()) + "  函", fontCh22b, 0, 0, LEFT);
        // addColumnAssignVAlignment(table, 17, 1, "(給 付 函)", fontCh14, 0, LEFT, BOTTOM);
        addColumnAssignVAlignment(table, 15, 1, "", fontCh14, 0, RIGHT, BOTTOM);

        addColumnAssignLineSpace(table, 60, 1, "　", fontCh1, 0, 0, RIGHT);
        addColumnAssignLineSpace(table, 35, 1, "　", fontCh1, 0, 0, RIGHT);
        addColumnAssignLineSpace(table, 25, 1, "機關地址：10013台北市羅斯福路1段4號", fontCh11, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 35, 1, "　", fontCh1, 0, 0, RIGHT);
        addColumnAssignLineSpace(table, 25, 1, "承辦單位：" + caseData.getUnit(), fontCh11, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 35, 1, "　", fontCh1, 0, 0, RIGHT);
        addColumnAssignLineSpace(table, 25, 1, "聯絡方式：" + caseData.getComTel(), fontCh11, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 35, 1, "　", fontCh1, 0, 0, RIGHT);
        addColumnAssignLineSpace(table, 25, 1, "受理編號：" + caseData.getWordNo(), fontCh11, 0, 0, LEFT);

        addColumnAssignLineSpace(table, 11, 1, "", fontCh12, 0, 0, RIGHT);
        addColumnAssignLineSpace(table, 49, 1, "掛　號", fontCh12, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 11, 1, "地  址：", fontCh12, 0, 0, RIGHT);
        addColumnAssignLineSpace(table, 49, 1, caseData.getAddrZip(), fontCh12, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 11, 1, " ", fontCh12, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 23, 1, caseData.getAddr(), fontCh12, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 26, 1, " ", fontCh12, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 11, 1, "受文者：", fontCh16, 0, 0, RIGHT);
        addColumnAssignLineSpace(table, 49, 1, caseData.getName(), fontCh16, 0, 0, LEFT);
        addColumn(table, 60, 1, " ", fontCh10, 0, CENTER);
        String test = caseData.getWordDate();
        addColumnAssignLineSpace(table, 60, 1, "發文日期：" + "中華民國 " + StringUtils.rightPad(caseData.getWordDate(), (StringUtils.isBlank(caseData.getWordDate())) ? 28 : 26, " "), fontCh11, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 60, 1, "發文字號：" + "保職簡字第" + caseData.getWordNo() + "號", fontCh11, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 60, 1, "速別：普通件", fontCh11, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 60, 1, "密等及解密條件或保密期限：普通", fontCh11, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 60, 1, "附件：", fontCh11, 0, 0, LEFT);
        addColumn(table, 60, 1, " ", fontCh8, 0, CENTER);

        List<String> message = caseData.getMessage();
        addColumnAssignLineSpace(table, 5, 1, "主旨：", fontCh12, 5, 0, LEFT);
        addColumnAssignLineSpace(table, 55, 1, message.get(0), fontCh12, 5, 0, JUSTIFIED);

        addColumn(table, 60, 1, " ", fontCh8, 0, CENTER);
        addColumn(table, 60, 1, " ", fontCh8, 0, CENTER);

        addColumn(table, 6, 1, "說明：", fontCh12, 0, LEFT);
        addColumn(table, 54, 1, " ", fontCh12, 0, LEFT);

        for (int i = 1; i < message.size(); i++) {
            String msg = StringUtils.trim(message.get(i));

            if (msg.substring(0, 1).equals("(")) {
                // addColumnAssignLineSpace(table, 6, 1, " ", fontCh12, 5, 0, RIGHT);
                // addColumnAssignLineSpace(table, 54, 1, msg.substring(0, msg.length()), fontCh12, 5, 0, JUSTIFIED);
                int index = msg.indexOf(")");
                addColumnAssignLineSpace(table, 9, 1, msg.substring(0, index + 1), fontCh12, 5, 0, RIGHT);
                addColumnAssignLineSpace(table, 51, 1, msg.substring(index + 1, msg.length()), fontCh12, 5, 0, JUSTIFIED);
            }
            else {
                int index = msg.indexOf("、");
                addColumnAssignLineSpace(table, 6, 1, msg.substring(0, index + 1), fontCh12, 5, 0, RIGHT);
                addColumnAssignLineSpace(table, 54, 1, msg.substring(index + 1, msg.length()), fontCh12, 5, 0, JUSTIFIED);
            }

        }

        while (writer.fitsPage(table)) {
            addColumnAssignLineSpace(table, 60, 1, " ", fontCh1, 0, 0, RIGHT);
        }

        for (int i = 0; i < 19; i++)
            table.deleteLastRow();

        // 20091210 增加列印總經理
        if (StringUtils.isNotBlank(caseData.getManager())) {
            // addColumn(table, 60, 1, " " + RptTitleUtility.getManegerTitle(caseData.getAplpayDate(), caseData.getManagerStr()), fontCh20, 0, LEFT);
            addColumn(table, 60, 1, "     " + caseData.getManager(), fontCh20, 0, LEFT);
        }
        else {
            addColumn(table, 60, 1, "     ", fontCh20, 0, LEFT);
        }
        for (int i = 0; i < 10; i++)
            addColumnAssignLineSpace(table, 35, 1, "", fontCh12, 28, 0, RIGHT);

        addColumnAssignLineSpace(table, 25, 1, "", fontCh12, 33, 0, LEFT);

        return table;
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<MonthlyRpt30Case> caseList, HashMap<String, Object> map) throws Exception {

        try {

            document.open();
            Table table = null;

            String payCode = (String) map.get("payCode");

            if (caseList != null) {
                for (MonthlyRpt30Case caseData : caseList) {

                    // 每張報表印兩份 20141117 改成印一份
                    for (int i = 0; i < 1; i++) {

                        table = printPage(caseData, map);
                        document.add(table);

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
