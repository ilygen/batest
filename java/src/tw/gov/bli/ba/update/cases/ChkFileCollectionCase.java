package tw.gov.bli.ba.update.cases;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.util.DateUtility;

/**
 * @author Rickychi
 */
public class ChkFileCollectionCase implements Serializable {
    private static final long serialVersionUID = 4482110121235865214L;
    private String payYm; // 給付年月
    private List<ChkFileCase> chkFileCollection; // 編審註記List

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

    // ]

    public String getPayYm() {
        return payYm;
    }

    public void setPayYm(String payYm) {
        this.payYm = payYm;
    }

    public List<ChkFileCase> getChkFileCollection() {
        return chkFileCollection;
    }

    public void setChkFileCollection(List<ChkFileCase> chkFileCollection) {
        this.chkFileCollection = chkFileCollection;
    }

}
