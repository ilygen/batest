package tw.gov.bli.ba.update.cases;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 失能年金應收收回處理
 * 
 * @author Noctis
 */
public class DisabledPaymentReceiveDataCase implements Serializable {
    private static final long serialVersionUID = -5056817223316820001L;

    private String apNo; // 受理編號
    private String seqNo; // 受款人序
    private String evtName; // 事故者姓名
    private String evtIdnNo; // 事故者身分證號
    private String evtBrDate; // 事故者出生日期
    private String evtJobDate;// 事故日期
    private String payKind;// 給付種類

    public String getApNoStrDisplay() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH)
            return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12);
        else
            return "";
    }

    // 給付別中文
    public String getApNoKindStrDisplay() {
        String apNoKind = "";
        String apNoKindStr = "";
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH)
            apNoKind = getApNo().substring(0, 1);
        if (apNoKind.equals("L")) {
            apNoKindStr = "老年";
            return apNoKindStr;
        }
        else if (apNoKind.equals("K")) {
            apNoKindStr = "失能";
            return apNoKindStr;
        }
        else if (apNoKind.equals("S")) {
            apNoKindStr = "遺屬";
            return apNoKindStr;
        }
        else
            return "";
    }

    public String getEvtBrDateStr() {
        if (StringUtils.isNotBlank(getEvtBrDate())) {
            return DateUtility.changeDateType(getEvtBrDate());
        }
        else {
            return "";
        }
    }

    public String getEvtJobDateStr() {
        if (StringUtils.isNotBlank(getEvtJobDate())) {
            return DateUtility.changeDateType(getEvtJobDate());
        }
        else {
            return "";
        }
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getEvtIdnNo() {
        return evtIdnNo;
    }

    public void setEvtIdnNo(String evtIdnNo) {
        this.evtIdnNo = evtIdnNo;
    }

    public String getEvtBrDate() {
        return evtBrDate;
    }

    public void setEvtBrDate(String evtBrDate) {
        this.evtBrDate = evtBrDate;
    }

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public String getEvtJobDate() {
        return evtJobDate;
    }

    public void setEvtJobDate(String evtJobDate) {
        this.evtJobDate = evtJobDate;
    }

    public String getPayKind() {
        return payKind;
    }

    public void setPayKind(String payKind) {
        this.payKind = payKind;
    }

}
