package tw.gov.bli.ba.update.cases;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 案件更正 - 身分證號重號資料
 * 
 * @author Rickychi
 */
public class ApplicationUpdateDupeIdnNoCase implements Serializable {
    private static final long serialVersionUID = 4482110121235865214L;
    private String apNo;// 受理編號
    private String seqNo;// 序號
    private String idnNo;// 身分證號
    private String name;// 姓名
    private String brDate;// 出生日期
    private String selMk;// 選擇註記
    private String updUser;// 異動者代號
    private String updTime;// 異動日期時間

    // 頁面顯示轉換
    // [
    public String getBrDateStr() {
        if (StringUtils.isNotBlank(getBrDate())) {
            return DateUtility.changeDateType(getBrDate());
        }
        else {
            return "";
        }
    }

    public String getApNoStrDisplay() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH)
            return apNo.substring(0, 1) + "-" + apNo.substring(1, 2) + "-" + apNo.substring(2, 7) + "-" + apNo.substring(7, 12);
        else
            return StringUtils.defaultString(apNo);
    }

    public String getIdnNoStr() {
        if (StringUtils.isNotBlank(getIdnNo()) && getIdnNo().length() >= 10)
            return getIdnNo().substring(0, 10);
        else
            return getIdnNo();
    }

    // ]

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

    public String getIdnNo() {
        return idnNo;
    }

    public void setIdnNo(String idnNo) {
        this.idnNo = idnNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrDate() {
        return brDate;
    }

    public void setBrDate(String brDate) {
        this.brDate = brDate;
    }

    public String getSelMk() {
        return selMk;
    }

    public void setSelMk(String selMk) {
        this.selMk = selMk;
    }

    public String getUpdUser() {
        return updUser;
    }

    public void setUpdUser(String updUser) {
        this.updUser = updUser;
    }

    public String getUpdTime() {
        return updTime;
    }

    public void setUpdTime(String updTime) {
        this.updTime = updTime;
    }
}
