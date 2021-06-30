package tw.gov.bli.ba.update.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Baapplog;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 繼承人資料更正
 * 
 * @author jerry
 */
public class InheritorRegisterCase implements Serializable {
    private BigDecimal baappbaseId;// 給付主檔資料編號
    private String apNo;// 受理編號
    private String seqNo;//序號
    private String formatApNo;// 受理編號輸出格式
    private String kind;// 給付種類FOR頁面
    private String evtName;// 事故者姓名
    private String evtBrDate;// 事故者出生日期
    private String evtDieDate;// 事故者死亡日期
    private String evtIdnNo;// 事故者身分證號
    private String appDate;// 申請日期
    private String benNationTyp;// 繼承人國籍別
    private String benNationCode;// 繼承人 國籍碼
    private String benIdnNo;// 繼承人身分證號
    private String benName;// 繼承人姓名
    private String benBrDate;// 繼承人出生日期
    private String benSex;// 繼承人性別
    private String benEvtRel;// 繼承人關係
    private String tel1;// 電話1
    private String tel2;// 電話2
    private String commTyp;// 通訊地址別
    private String commZip;// 通訊郵遞區號
    private String commAddr;// 通訊地址
    private String grdIdnNo;// 法定代理人身分證號
    private String grdName;// 法定代理人姓名
    private String grdBrDate;// 法定代理人出生日期
    private List<Baappbase> detailList;
    private List<Baapplog> baapplogList;// 更正記錄檔

    public String getChineseAppDate() {
        if (appDate != null && appDate.length() > 7) {
            return DateUtility.changeDateType(appDate);
        }
        return appDate;
    }

    public String getChineseEvtBrDate() {
        if (evtBrDate != null && evtBrDate.length() > 7) {
            return DateUtility.changeDateType(evtBrDate);
        }
        return evtBrDate;
    }

    public String getChineseEvtDieDate() {
        if (StringUtils.isNotBlank(evtDieDate) && evtDieDate.length() > 7) {
            return DateUtility.changeDateType(evtDieDate);
        }
        return evtBrDate;
    }

    public BigDecimal getBaappbaseId() {
        return baappbaseId;
    }

    public void setBaappbaseId(BigDecimal baappbaseId) {
        this.baappbaseId = baappbaseId;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getFormatApNo() {
        return formatApNo;
    }

    public void setFormatApNo(String formatApNo) {
        this.formatApNo = formatApNo;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public String getEvtBrDate() {
        if (evtBrDate != null && evtBrDate.length() < 8) {
            return DateUtility.changeDateType(evtBrDate);
        }
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

    public List<Baappbase> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<Baappbase> detailList) {
        this.detailList = detailList;
    }

    public String getAppDate() {
        if (appDate != null && appDate.length() < 8) {
            return DateUtility.changeDateType(appDate);
        }
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getBenNationTyp() {
        return benNationTyp;
    }

    public void setBenNationTyp(String benNationTyp) {
        this.benNationTyp = benNationTyp;
    }

    public String getBenNationCode() {
        return benNationCode;
    }

    public void setBenNationCode(String benNationCode) {
        this.benNationCode = benNationCode;
    }

    public String getBenIdnNo() {
        return benIdnNo;
    }

    public void setBenIdnNo(String benIdnNo) {
        this.benIdnNo = benIdnNo;
    }

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }

    public String getBenBrDate() {
        if (benBrDate != null && benBrDate.length() < 8) {
            return DateUtility.changeDateType(benBrDate);
        }
        return benBrDate;
    }

    public String getChineseBenBrDate() {
        if (benBrDate.length() > 7) {
            return DateUtility.changeDateType(benBrDate);
        }
        return benBrDate;
    }

    public void setBenBrDate(String benBrDate) {
        this.benBrDate = benBrDate;
    }

    public String getBenSex() {
        return benSex;
    }

    public void setBenSex(String benSex) {
        this.benSex = benSex;
    }

    public String getBenEvtRel() {
        return benEvtRel;
    }

    public void setBenEvtRel(String benEvtRel) {
        this.benEvtRel = benEvtRel;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getCommTyp() {
        return commTyp;
    }

    public void setCommTyp(String commTyp) {
        this.commTyp = commTyp;
    }

    public String getCommZip() {
        return commZip;
    }

    public void setCommZip(String commZip) {
        this.commZip = commZip;
    }

    public String getCommAddr() {
        return commAddr;
    }

    public void setCommAddr(String commAddr) {
        this.commAddr = commAddr;
    }

    public String getGrdIdnNo() {
        return grdIdnNo;
    }

    public void setGrdIdnNo(String grdIdnNo) {
        this.grdIdnNo = grdIdnNo;
    }

    public String getGrdName() {
        return grdName;
    }

    public void setGrdName(String grdName) {
        this.grdName = grdName;
    }

    public String getGrdBrDate() {
        if (grdBrDate != null && grdBrDate.length() < 8) {
            return DateUtility.changeDateType(grdBrDate);
        }
        return grdBrDate;
    }

    public String getChineseGrdBrDate() {
        if (grdBrDate != null && grdBrDate.length() > 7) {
            return DateUtility.changeDateType(grdBrDate);
        }
        return grdBrDate;
    }

    public void setGrdBrDate(String grdBrDate) {
        this.grdBrDate = grdBrDate;
    }

    public List<Baapplog> getBaapplogList() {
        return baapplogList;
    }

    public void setBaapplogList(List<Baapplog> baapplogList) {
        this.baapplogList = baapplogList;
    }

    public String getEvtDieDate() {
        return evtDieDate;
    }

    public void setEvtDieDate(String evtDieDate) {
        this.evtDieDate = evtDieDate;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

}
