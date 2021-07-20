package tw.gov.bli.ba.query.cases;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Maadmrec;
/**
 * 行政支援查詢作業 
 * 
 * @author jerry
 */
public class ExecutiveSupportQueryCase implements Serializable {
    private String apNo;// 受理編號
    private String payKind;// 給付種類
    private String chkMan;// 審核人員
    private String evtName;// 事故者姓名
    private String evtBrDate;// 事故者出生日期  
    private String evtIdnNo;// 事故者身分證號
    private List<Maadmrec> detailList;
    private Maadmrec detail;
    public String getApNo() {
        return apNo;
    }
    public void setApNo(String apNo) {
        this.apNo = apNo;
    }
    public String getPayKind() {
        return payKind;
    }
    public void setPayKind(String payKind) {
        this.payKind = payKind;
    }
    public String getChkMan() {
        return chkMan;
    }
    public void setChkMan(String chkMan) {
        this.chkMan = chkMan;
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
    public String getEvtIdnNo() {
        return evtIdnNo;
    }
    public void setEvtIdnNo(String evtIdnNo) {
        this.evtIdnNo = evtIdnNo;
    }
    public List<Maadmrec> getDetailList() {
        return detailList;
    }
    public void setDetailList(List<Maadmrec> detailList) {
        this.detailList = detailList;
    }
    public Maadmrec getDetail() {
        return detail;
    }
    public void setDetail(Maadmrec detail) {
        this.detail = detail;
    }
    public String getPayKindStr() {
        if(apNo.equals("") || apNo.equals(null)){
            return "";
        } else {
            if(apNo.substring(0, 1).equals("L") )
                return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L;
            else if(apNo.substring(0, 1).equals("K"))
                return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K;
            else if(apNo.substring(0, 1).equals("S"))
                return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S;
            else
                return "";          
        }
    }
    public String getApNoStr() {
        if (StringUtils.length(apNo) == ConstantKey.APNO_LENGTH)
            return apNo.substring(0, 1) + "-" + apNo.substring(1, 2) + "-" + apNo.substring(2, 7) + "-" + apNo.substring(7, 12);
        else
            return StringUtils.defaultString(apNo);
    }
}
