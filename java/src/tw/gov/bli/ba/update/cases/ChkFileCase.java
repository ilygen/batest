package tw.gov.bli.ba.update.cases;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 案件資料更正-編審註記
 * 
 * @author swim
 */
public class ChkFileCase implements Serializable {
    private static final long serialVersionUID = 4482110121235865214L;
    private String payYm;// 給付年月
    private String issuYm; // 核定年月
    private String chkFileCode;// 編審註記代碼
    private String chkFileName; // 編審註記
    private String chkFileDesc; // 編審註記說明

    // 頁面顯示資料轉換
    // [
    public String getPayYmStr() {
        if (StringUtils.isNotBlank(getPayYm()) && getPayYm().length() == 6) {
            return DateUtility.changeDateType(getPayYm() + "01").substring(0, 5);
        }
        else {
            return "";
        }
    }

    public String getIssuYmStr() {
        if (StringUtils.isNotBlank(getIssuYm())) {
            return DateUtility.changeWestYearMonthType(getIssuYm());
        }
        else {
            return getIssuYm();
        }
    }

    public String getChkFileNameStr() {
        if (StringUtils.isNotBlank(getChkFileName())) {
            return getChkFileName().replace("\\", "\\\\").replace("\"", "\\\"").replace("\'", "\\\'");
        }
        else {
            return "";
        }
    }

    public String getChkFileDescStr() {
        if (StringUtils.isNotBlank(getChkFileDesc())) {
            return getChkFileDesc().replace("\\", "\\\\").replace("\"", "\\\"").replace("\'", "\\\'");
        }
        else {
            return "";
        }
    }

    // ]

    public String getChkFileName() {
        return chkFileName;
    }

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public void setChkFileName(String chkFileName) {
        this.chkFileName = chkFileName;
    }

    public String getChkFileDesc() {
        return chkFileDesc;
    }

    public void setChkFileDesc(String chkFileDesc) {
        this.chkFileDesc = chkFileDesc;
    }

    public String getChkFileCode() {
        return chkFileCode;
    }

    public void setChkFileCode(String chkFileCode) {
        this.chkFileCode = chkFileCode;
    }

    public String getPayYm() {
        return payYm;
    }

    public void setPayYm(String payYm) {
        this.payYm = payYm;
    }

}
