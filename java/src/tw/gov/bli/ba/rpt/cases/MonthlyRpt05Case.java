package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class MonthlyRpt05Case implements Serializable {
    private static final long serialVersionUID = -6541879602291890822L;
    private String apNo;
    private String wordNo;// 發文字號
    private String wordDate;// 發文日期
    private String unit;// 承辦單位
    private String comTel;// 連絡電話
    private String addrZip;// 郵遞區號
    private String addr;// 地址
    private String name;// 受文者姓名
    private String progenitor;// 正本
    private List<String> copy1;// 副本一
    private List<String> copy2;// 副本二
    private List<String> message;// 主旨說明
    private String issuYm;
    private String pbm0001;// 核定通知書檢核碼
    private String manager;// 總經理名稱
    private String notifyForm;
    private String seqNo;

    // 判斷列印標題 核付日期
    private String aplpayDate;
    private String receiveName;// 核定通知書受文者
    private String realName;// 核定通知書 正本使用姓名
    private String payCode; // 給付別

    private String rmp_Name; // 原住民羅馬拼音

    public String getManagerStr() {
        int title = StringUtils.lastIndexOf(manager, "總經理") + 3;
        String name = StringUtils.substringAfterLast(manager, "總經理");
        String str = "";
        for (int i = 0; i < StringUtils.length(name); i++) {
            str = str + StringUtils.substring(name, i, i + 1) + "  ";
        }
        String managerName = StringUtils.substring(manager, 0, title) + "  " + str;
        return managerName;
    }

    public String getWordNo() {
        return wordNo;
    }

    public void setWordNo(String wordNo) {
        this.wordNo = wordNo;
    }

    public String getWordDate() {
        return wordDate;
    }

    public void setWordDate(String wordDate) {
        this.wordDate = wordDate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getComTel() {
        return comTel;
    }

    public void setComTel(String comTel) {
        this.comTel = comTel;
    }

    public String getAddrZip() {
        return addrZip;
    }

    public void setAddrZip(String addrZip) {
        this.addrZip = addrZip;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public List<String> getCopy1() {
        return copy1;
    }

    public void setCopy1(List<String> copy1) {
        this.copy1 = copy1;
    }

    public List<String> getCopy2() {
        return copy2;
    }

    public void setCopy2(List<String> copy2) {
        this.copy2 = copy2;
    }

    public String getProgenitor() {
        return progenitor;
    }

    public void setProgenitor(String progenitor) {
        this.progenitor = progenitor;
    }

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String getPbm0001() {
        return pbm0001;
    }

    public void setPbm0001(String pbm0001) {
        this.pbm0001 = pbm0001;
    }

    public String getAplpayDate() {
        return aplpayDate;
    }

    public void setAplpayDate(String aplpayDate) {
        this.aplpayDate = aplpayDate;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getNotifyForm() {
        return notifyForm;
    }

    public void setNotifyForm(String notifyForm) {
        this.notifyForm = notifyForm;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getRmp_Name() {
        return rmp_Name;
    }

    public void setRmp_Name(String rmp_Name) {
        this.rmp_Name = rmp_Name;
    }
}
