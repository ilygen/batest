package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt02Case;
import tw.gov.bli.ba.rpt.report.ExcelReportBase;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;

public class MonthlyRpt02ExcelReport extends ExcelReportBase {

    private HSSFSheet tempSheet;
    private HSSFCell tempCell;

    public MonthlyRpt02ExcelReport() throws Exception {
        super("MonthlyRpt02Excel.xls");
    }

    // 取得給付別中文說明
    private String getPayCodeStr(String payCode) {
        if (StringUtils.equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_L, payCode)) {
            return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L;
        }
        else if (StringUtils.equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_K, payCode)) {
            return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K;
        }
        else if (StringUtils.equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_S, payCode)) {
            return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S;
        }
        else {
            return "";
        }
    }

    public void printHead(String payCodeStr, String reportNo, String payCode, String reportTitle, String issuYm) {

        HSSFSheet sheet = workbook.getSheetAt(1);
        HSSFRow row = sheet.getRow(0);
        setCell(row.getCell((short) 0), "給付別：" + payCodeStr);
        setCell(row.getCell((short) 9), "報表編號：" + reportNo);

        row = sheet.getRow(1);
        setCell(row.getCell((short) 2), RptTitleUtility.getOrgTitle(DateUtility.getNowWestDate()) + "　" + RptTitleUtility.getGroupsTitle(payCode, DateUtility.getNowWestDate()));
        setCell(row.getCell((short) 9), "日期：" + DateUtility.formatChineseDateTimeString(DateUtility.getNowChineseDate(), false));

        row = sheet.getRow(2);
        setCell(row.getCell((short) 2), reportTitle);

        row = sheet.getRow(3);
        setCell(row.getCell((short) 0), "核定年月：" + issuYm);
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<MonthlyRpt02Case> caseData, HashMap<String, Object> map) throws Exception {

        // 序號
        Integer seqNo = 1;
        String payCode = caseData.get(0).getPayCodeStr();
        String rptPayCode = caseData.get(0).getApNo().substring(0, 1);
        String issuYm = caseData.get(0).getIssuYmStr();
        String chkMan = caseData.get(0).getChkMan();
        String mtestMk = (String) map.get("mtestMk");
        String reportTitle = null;
        String reportNo = null;

        if (mtestMk.equals("M")) {
            reportTitle = "月試核異動清單";
            reportNo = "BALP0D320";
        }
        else {
            reportTitle = "月核定異動清單";
            reportNo = "BALP0D321";
        }

        // head
        printHead(payCode, reportNo, rptPayCode, reportTitle, issuYm);

        HSSFSheet sheet = workbook.getSheetAt(1);
        HSSFRow row = null;
        int index = 5;

        // 列印資料內容
        for (MonthlyRpt02Case data : caseData) {
            copyRows(0, 1, 5, 5, index);
            row = sheet.getRow(index);
            setCell(row.getCell((short) 0), Integer.toString(index - 4));
            setCell(row.getCell((short) 1), data.getApNoStr());
            setCell(row.getCell((short) 2), data.getEvtIdnNo());
            setCell(row.getCell((short) 3), data.getEvtName());
            setCell(row.getCell((short) 4), data.getUpCauseCodeStr());
            setCell(row.getCell((short) 5), data.getCprnDateStr());
            setCell(row.getCell((short) 6), data.getCaseTypStr());
            setCell(row.getCell((short) 7), data.getChkMan());
            setCell(row.getCell((short) 8), data.getChkList());
            setCell(row.getCell((short) 9), data.getPreChkList());
            index++;
        }
        workbook.removeSheetAt(0);
        workbook.getSheetAt(0).setSelected(true);
        workbook.write(bao);
        return bao;

    }

}
