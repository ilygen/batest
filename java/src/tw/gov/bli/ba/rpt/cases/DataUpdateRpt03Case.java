package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 * Case for 紓困貸款抵銷情形照會單
 * 
 * @author Goston
 */
public class DataUpdateRpt03Case implements Serializable {
    // 給付主檔 (BAAPPBASE) - 事故者資料
    // [
    private String apNo; // 受理編號
    private String evtIdnNo; // 事故者身分證號
    private String evtName; // 事故者姓名
    private String evtBrDate; // 事故者出生日期
    // ]

    // 現金給付參考檔 (PBBMSA)
    // [
    List<DataUpdateRpt03DetailCase> detailList;
    // ]

    /**
     * 依給付種類取得報表中所需的給付處 xx 給付科稱
     * 
     * @return
     */
    public String getDeptNameString() {
        String payKindCode = StringUtils.substring(apNo, 0, 1);

        if (StringUtils.equalsIgnoreCase(payKindCode, "L"))
            return "生老";
        else if (StringUtils.equalsIgnoreCase(payKindCode, "K"))
            return "傷殘";
        else if (StringUtils.equalsIgnoreCase(payKindCode, "S"))
            return "死亡";
        else
            return "";
    }

    public DataUpdateRpt03Case() {

    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getEvtIdnNo() {
        return evtIdnNo;
    }

    public void setEvtIdnNo(String evtIdnNo) {
        this.evtIdnNo = evtIdnNo;
    }

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public String getEvtBrDate() {
        return evtBrDate;
    }

    public void setEvtBrDate(String evtBrDate) {
        this.evtBrDate = evtBrDate;
    }

    public List<DataUpdateRpt03DetailCase> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<DataUpdateRpt03DetailCase> detailList) {
        this.detailList = detailList;
    }

}
