package tw.gov.bli.ba.executive.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Maadmrec;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 行政支援註記維護作業
 * 
 * @author jerry
 */
public class ExecutiveSupportMaintCase implements Serializable {
    private BigDecimal baappbaseId;// 給付主檔資料編號
    private String apNo;// 受理編號
    // private String formatApNo;// 受理編號輸出格式
    private String kind;// 給付種類FOR頁面
    private String chkMan;// 審核人員
    private String evtName;// 事故者姓名
    private String evtBrDate;// 事故者出生日期
    private String evtIdnNo;// 事故者身分證號
    private String issuYm;// 核定年月
    private List<Maadmrec> detailList;
    private Maadmrec detail;
    private String payMk; // 付費註記
    private String payKind; // 給付種類
    private String payYms;// 首次發放起月
    private String procStat;// // 處理狀態
    private String procStatMk;//判斷此案件的處理狀態 (PROCSTAT) 是否小於20或大於等於20
    
    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
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

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public BigDecimal getBaappbaseId() {
        return baappbaseId;
    }

    public void setBaappbaseId(BigDecimal baappbaseId) {
        this.baappbaseId = baappbaseId;
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

    public String getPayMk() {
        return payMk;
    }

    public void setPayMk(String payMk) {
        this.payMk = payMk;
    }

    public String getPayKind() {
        return payKind;
    }

    public void setPayKind(String payKind) {
        this.payKind = payKind;
    }

    public String getPayYms() {
        return payYms;
    }

    public void setPayYms(String payYms) {
        this.payYms = payYms;
    }

    public String getPayKindStr() {
        if (apNo.equals("") || apNo.equals(null)) {
            return "";
        }
        else {
            if (apNo.substring(0, 1).equals("L"))
                return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L;
            else if (apNo.substring(0, 1).equals("K"))
                return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K;
            else if (apNo.substring(0, 1).equals("S"))
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

	public String getProcStat() {
		return procStat;
	}

	public void setProcStat(String procStat) {
		this.procStat = procStat;
	}

	public String getProcStatMk() {
		return procStatMk;
	}

	public void setProcStatMk(String procStatMk) {
		this.procStatMk = procStatMk;
	}

}
