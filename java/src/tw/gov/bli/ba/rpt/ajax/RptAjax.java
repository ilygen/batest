package tw.gov.bli.ba.rpt.ajax;

import java.io.File;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ReportUtility;

public class RptAjax {
    public String isMonthlyRpt08FileExist(String payCode, String issuYm, String chkDate, String npWithLip) {
        String fileName = StringUtils.trimToEmpty(payCode) + "_" + StringUtils.trimToEmpty(DateUtility.changeChineseYearMonthType(issuYm)) + "_" + StringUtils.trimToEmpty(DateUtility.changeDateType(chkDate)) + "_" + npWithLip + "_MonthlyRpt08_";
        
        File[] fileList = ReportUtility.listPDFFile(fileName);
        
        if (fileList != null && fileList.length > 0)
            return "true";
        else
            return "false";
    }
}
