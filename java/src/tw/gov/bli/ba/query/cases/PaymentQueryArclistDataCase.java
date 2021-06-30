package tw.gov.bli.ba.query.cases;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 給付查詢作業 歸檔記錄-核定年月
 * 
 * @author jerry
 */
public class PaymentQueryArclistDataCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;

    private String minIssuYm;// 核定年月起
    private String maxIssuYm;// 核定年月迄
    private List<PaymentQueryArclistDetailDataCase> detailList;// 明細資料

    public String getMinIssuYmChinese() {
        return StringUtils.trimToEmpty(DateUtility.changeWestYearMonthType(minIssuYm));
    }

    public String getMaxIssuYmChinese() {
        return StringUtils.trimToEmpty(DateUtility.changeWestYearMonthType(maxIssuYm));
    }

    public String getMinIssuYm() {
        return minIssuYm;
    }

    public void setMinIssuYm(String minIssuYm) {
        this.minIssuYm = minIssuYm;
    }

    public String getMaxIssuYm() {
        return maxIssuYm;
    }

    public void setMaxIssuYm(String maxIssuYm) {
        this.maxIssuYm = maxIssuYm;
    }

    public List<PaymentQueryArclistDetailDataCase> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<PaymentQueryArclistDetailDataCase> detailList) {
        this.detailList = detailList;
    }

}
