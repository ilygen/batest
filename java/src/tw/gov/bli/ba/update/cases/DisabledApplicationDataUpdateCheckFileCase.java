package tw.gov.bli.ba.update.cases;

import java.io.Serializable;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 失能年金案件資料更正 - 編審註記資料
 * 
 * @author Goston
 */
public class DisabledApplicationDataUpdateCheckFileCase implements Serializable {
    // BACHKFILE
    private String payYm; // 給付年月
    private String chkFileName; // 編審註記名稱
    private String chkFileDesc; // 編審註記代號 + 名稱
    private String chkFileCode; // 編審註記代號
    
    /**
     * 給付年月
     * 
     * @return
     */
    public String getPayYmString() {
        return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(payYm), false);
    }

    public DisabledApplicationDataUpdateCheckFileCase() {

    }

    public String getPayYm() {
        return payYm;
    }

    public void setPayYm(String payYm) {
        this.payYm = payYm;
    }

    public String getChkFileName() {
        return chkFileName;
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

}
