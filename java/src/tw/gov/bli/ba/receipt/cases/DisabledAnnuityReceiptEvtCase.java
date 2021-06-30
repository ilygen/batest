package tw.gov.bli.ba.receipt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.domain.Baapplog;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 失能年金受理作業 事故者資料
 * 
 * @author Rickychi
 */
public class DisabledAnnuityReceiptEvtCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
    private BigDecimal baappbaseId;// 給付主檔資料列編號
    private BigDecimal baappexpandId; // 給付延伸主檔資料列編號

    private String apNo;// 受理編號
    private String seqNo;// 序號
    private String apNo1;// 受理編號-1
    private String apNo2;// 受理編號-2
    private String apNo3;// 受理編號-3
    private String apNo4;// 受理編號-4
    private String appDate;// 申請日期
    private String apUbno;// 申請單位保險證號

    // 事故者個人資料
    private String evtNationTpe;// 事故者國籍別
    private String evtSex;// 性別
    private String evtNationCode;// 事故者國籍
    private String evtNationCodeOption;// 事故者國籍下拉選單
    private String evtName;// 事故者姓名
    private String evtDieDate;// 事故者死亡日期
    private String cvldtlName;// 戶籍姓名
    private String evtIdnNo;// 事故者身分證號
    private String evtBrDate;// 事故者出生日期
    private String tel1;// 電話1
    private String tel2;// 電話2
    private String commTyp;// 通訊地址別
    private String commZip;// 通訊郵遞區號
    private String commAddr;// 通訊地址
    private String grdIdnNo;// 法定代理人身分證號
    private String grdName;// 法定代理人姓名
    private String grdBrDate;// 法定代理人出生日期
    private String mobilePhone; // 手機複驗
    private String evtHandIcapMk;// 有無診斷書
    private String mapNo;// 相關受理編號
    private String handIcApDt; // 手冊鑑定日期
    private String diaDabDt;

    // 受益人資料
    private String benIdnNo;// 受益人身分證號
    private String benName;// 受益人姓名
    private String benBrDate;// 受益人出生日期
    private String benSex;// 受益人性別
    private String benNationTyp;// 受益人國籍別
    private String benEvtRel; // 受益人與事故者關係

    // 保險事故
    private String evTyp;// 傷病分類
    private String evtJobDate;// 診斷失能日期
    private String evCode;// 傷病原因
    private String criInPart1;// 受傷部位
    private String criInPart2;// 受傷部位
    private String criInPart3;// 受傷部位
    private String criMedium;// 媒 介 物
    private String criInJdp1;// 失能項目
    private String criInJdp2;// 失能項目
    private String criInJdp3;// 失能項目
    private String criInJdp4;// 失能項目
    private String criInJdp5;// 失能項目
    private String criInJdp6;// 失能項目
    private String criInJdp7;// 失能項目
    private String criInJdp8;// 失能項目
    private String criInJdp9;// 失能項目
    private String criInJdp10;// 失能項目
    private String hosId;// 醫療院所代碼
    private String doctorName1;// 醫師姓名
    private String doctorName2;// 醫師姓名
    private String criInJnme1;// 國際疾病代碼
    private String criInJnme2;// 國際疾病代碼
    private String criInJnme3;// 國際疾病代碼
    private String criInJnme4;// 國際疾病代碼

    // 給付資料
    private String payTyp;// 給付方式
    private String payBankIdBranchId;// 帳號(前)
    private String chkPayBankIdChkBranchId;// 帳號(前) 複驗
    private String payEeacc;// 帳號(後)
    private String chkPayEeacc;// 銀行帳號 (帳號(後)) 複驗
    // Baappbase欄位
    private String evtIds;// 事故者社福識別碼
    private String evtAge;// 事故者申請時年齡
    private String benIds;// 受益人社福識別碼
    private String bankName;// 金融機構名稱
    private String payBankId;// 金融機構總代號
    private String branchId;// 分支代號
    private String chkPayBankId;// 金融機構總代號 複驗
    private String chkBranchId;// 分支代號 複驗
    private String evtIdent;// 事故者身分別
    private String accIdn; // 戶名IDN
    private String accName; // 戶名
    private String accRel; // 戶名與受益人關係
    private String grdBirth; // 法定代理人出生日期

    private String sFlag36;// 國併勞

    // 頁面顯示轉換
    // [
    public String getApNoStrDisplay() {
        return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12);
    }

    public String getAppDateStr() {
        if (StringUtils.isNotBlank(getAppDate()) && getAppDate().length() == 8) {
            return DateUtility.changeDateType(getAppDate());
        }
        else {
            return "";
        }
    }

    public String getEvtBrDateStr() {
        if (StringUtils.isNotBlank(getEvtBrDate()) && getEvtBrDate().length() == 8) {
            return DateUtility.changeDateType(getEvtBrDate());
        }
        else {
            return "";
        }
    }

    public String getGrdBrDateStr() {
        if (StringUtils.isNotBlank(getGrdBrDate()) && getGrdBrDate().length() == 8) {
            return DateUtility.changeDateType(getGrdBrDate());
        }
        else {
            return "";
        }
    }

    public String getEvtJobDateStr() {
        if (StringUtils.isNotBlank(getEvtJobDate()) && getEvtJobDate().length() == 8) {
            return DateUtility.changeDateType(getEvtJobDate());
        }
        else {
            return "";
        }
    }

    // ]

    private List<Baapplog> baapplogList;// 更正記錄檔

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

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getApNo1() {
        return apNo1;
    }

    public void setApNo1(String apNo1) {
        this.apNo1 = apNo1;
    }

    public String getApNo2() {
        return apNo2;
    }

    public void setApNo2(String apNo2) {
        this.apNo2 = apNo2;
    }

    public String getApNo3() {
        return apNo3;
    }

    public void setApNo3(String apNo3) {
        this.apNo3 = apNo3;
    }

    public String getApNo4() {
        return apNo4;
    }

    public void setApNo4(String apNo4) {
        this.apNo4 = apNo4;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getApUbno() {
        return apUbno;
    }

    public void setApUbno(String apUbno) {
        this.apUbno = apUbno;
    }

    public String getEvtNationTpe() {
        return evtNationTpe;
    }

    public void setEvtNationTpe(String evtNationTpe) {
        this.evtNationTpe = evtNationTpe;
    }

    public String getEvtSex() {
        return evtSex;
    }

    public void setEvtSex(String evtSex) {
        this.evtSex = evtSex;
    }

    public String getEvtNationCode() {
        return evtNationCode;
    }

    public void setEvtNationCode(String evtNationCode) {
        this.evtNationCode = evtNationCode;
    }

    public String getEvtNationCodeOption() {
        return evtNationCodeOption;
    }

    public void setEvtNationCodeOption(String evtNationCodeOption) {
        this.evtNationCodeOption = evtNationCodeOption;
    }

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public String getCvldtlName() {
        return cvldtlName;
    }

    public void setCvldtlName(String cvldtlName) {
        this.cvldtlName = cvldtlName;
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
        return grdBrDate;
    }

    public void setGrdBrDate(String grdBrDate) {
        this.grdBrDate = grdBrDate;
    }

    public String getEvTyp() {
        return evTyp;
    }

    public void setEvTyp(String evTyp) {
        this.evTyp = evTyp;
    }

    public String getEvtJobDate() {
        return evtJobDate;
    }

    public void setEvtJobDate(String evtJobDate) {
        this.evtJobDate = evtJobDate;
    }

    public String getEvCode() {
        return evCode;
    }

    public void setEvCode(String evCode) {
        this.evCode = evCode;
    }

    public String getCriInPart1() {
        return criInPart1;
    }

    public void setCriInPart1(String criInPart1) {
        this.criInPart1 = criInPart1;
    }

    public String getCriInPart2() {
        return criInPart2;
    }

    public void setCriInPart2(String criInPart2) {
        this.criInPart2 = criInPart2;
    }

    public String getCriInPart3() {
        return criInPart3;
    }

    public void setCriInPart3(String criInPart3) {
        this.criInPart3 = criInPart3;
    }

    public String getCriMedium() {
        return criMedium;
    }

    public void setCriMedium(String criMedium) {
        this.criMedium = criMedium;
    }

    public String getCriInJdp1() {
        return criInJdp1;
    }

    public void setCriInJdp1(String criInJdp1) {
        this.criInJdp1 = criInJdp1;
    }

    public String getCriInJdp2() {
        return criInJdp2;
    }

    public void setCriInJdp2(String criInJdp2) {
        this.criInJdp2 = criInJdp2;
    }

    public String getCriInJdp3() {
        return criInJdp3;
    }

    public void setCriInJdp3(String criInJdp3) {
        this.criInJdp3 = criInJdp3;
    }

    public String getCriInJdp4() {
        return criInJdp4;
    }

    public void setCriInJdp4(String criInJdp4) {
        this.criInJdp4 = criInJdp4;
    }

    public String getCriInJdp5() {
        return criInJdp5;
    }

    public void setCriInJdp5(String criInJdp5) {
        this.criInJdp5 = criInJdp5;
    }

    public String getCriInJdp6() {
        return criInJdp6;
    }

    public void setCriInJdp6(String criInJdp6) {
        this.criInJdp6 = criInJdp6;
    }

    public String getCriInJdp7() {
        return criInJdp7;
    }

    public void setCriInJdp7(String criInJdp7) {
        this.criInJdp7 = criInJdp7;
    }

    public String getCriInJdp8() {
        return criInJdp8;
    }

    public void setCriInJdp8(String criInJdp8) {
        this.criInJdp8 = criInJdp8;
    }

    public String getCriInJdp9() {
        return criInJdp9;
    }

    public void setCriInJdp9(String criInJdp9) {
        this.criInJdp9 = criInJdp9;
    }

    public String getCriInJdp10() {
        return criInJdp10;
    }

    public void setCriInJdp10(String criInJdp10) {
        this.criInJdp10 = criInJdp10;
    }

    public String getHosId() {
        return hosId;
    }

    public void setHosId(String hosId) {
        this.hosId = hosId;
    }

    public String getDoctorName1() {
        return doctorName1;
    }

    public void setDoctorName1(String doctorName1) {
        this.doctorName1 = doctorName1;
    }

    public String getDoctorName2() {
        return doctorName2;
    }

    public void setDoctorName2(String doctorName2) {
        this.doctorName2 = doctorName2;
    }

    public String getCriInJnme1() {
        return criInJnme1;
    }

    public void setCriInJnme1(String criInJnme1) {
        this.criInJnme1 = criInJnme1;
    }

    public String getCriInJnme2() {
        return criInJnme2;
    }

    public void setCriInJnme2(String criInJnme2) {
        this.criInJnme2 = criInJnme2;
    }

    public String getCriInJnme3() {
        return criInJnme3;
    }

    public void setCriInJnme3(String criInJnme3) {
        this.criInJnme3 = criInJnme3;
    }

    public String getCriInJnme4() {
        return criInJnme4;
    }

    public void setCriInJnme4(String criInJnme4) {
        this.criInJnme4 = criInJnme4;
    }

    public String getPayTyp() {
        return payTyp;
    }

    public void setPayTyp(String payTyp) {
        this.payTyp = payTyp;
    }

    public String getPayBankIdBranchId() {
        return payBankIdBranchId;
    }

    public void setPayBankIdBranchId(String payBankIdBranchId) {
        this.payBankIdBranchId = payBankIdBranchId;
    }

    public String getPayEeacc() {
        return payEeacc;
    }

    public void setPayEeacc(String payEeacc) {
        this.payEeacc = payEeacc;
    }

    public List<Baapplog> getBaapplogList() {
        return baapplogList;
    }

    public void setBaapplogList(List<Baapplog> baapplogList) {
        this.baapplogList = baapplogList;
    }

    public String getEvtIds() {
        return evtIds;
    }

    public void setEvtIds(String evtIds) {
        this.evtIds = evtIds;
    }

    public String getEvtAge() {
        return evtAge;
    }

    public void setEvtAge(String evtAge) {
        this.evtAge = evtAge;
    }

    public String getBenIds() {
        return benIds;
    }

    public void setBenIds(String benIds) {
        this.benIds = benIds;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getPayBankId() {
        return payBankId;
    }

    public void setPayBankId(String payBankId) {
        this.payBankId = payBankId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public BigDecimal getBaappexpandId() {
        return baappexpandId;
    }

    public void setBaappexpandId(BigDecimal baappexpandId) {
        this.baappexpandId = baappexpandId;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEvtHandIcapMk() {
        return evtHandIcapMk;
    }

    public void setEvtHandIcapMk(String evtHandIcapMk) {
        this.evtHandIcapMk = evtHandIcapMk;
    }

    public String getChkPayBankIdChkBranchId() {
        return chkPayBankIdChkBranchId;
    }

    public void setChkPayBankIdChkBranchId(String chkPayBankIdChkBranchId) {
        this.chkPayBankIdChkBranchId = chkPayBankIdChkBranchId;
    }

    public String getChkPayEeacc() {
        return chkPayEeacc;
    }

    public void setChkPayEeacc(String chkPayEeacc) {
        this.chkPayEeacc = chkPayEeacc;
    }

    public String getChkPayBankId() {
        return chkPayBankId;
    }

    public void setChkPayBankId(String chkPayBankId) {
        this.chkPayBankId = chkPayBankId;
    }

    public String getChkBranchId() {
        return chkBranchId;
    }

    public void setChkBranchId(String chkBranchId) {
        this.chkBranchId = chkBranchId;
    }

    public String getEvtDieDate() {
        return evtDieDate;
    }

    public void setEvtDieDate(String evtDieDate) {
        this.evtDieDate = evtDieDate;
    }

    public String getMapNo() {
        return mapNo;
    }

    public void setMapNo(String mapNo) {
        this.mapNo = mapNo;
    }

    public String getHandIcApDt() {
        return handIcApDt;
    }

    public void setHandIcApDt(String handIcApDt) {
        this.handIcApDt = handIcApDt;
    }

    public String getDiaDabDt() {
        return diaDabDt;
    }

    public void setDiaDabDt(String diaDabDt) {
        this.diaDabDt = diaDabDt;
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

    public String getBenNationTyp() {
        return benNationTyp;
    }

    public void setBenNationTyp(String benNationTyp) {
        this.benNationTyp = benNationTyp;
    }

    public String getBenEvtRel() {
        return benEvtRel;
    }

    public void setBenEvtRel(String benEvtRel) {
        this.benEvtRel = benEvtRel;
    }

    public String getsFlag36() {
        return sFlag36;
    }

    public void setsFlag36(String sFlag36) {
        this.sFlag36 = sFlag36;
    }

    public String getEvtIdent() {
        return evtIdent;
    }

    public void setEvtIdent(String evtIdent) {
        this.evtIdent = evtIdent;
    }

    public String getAccIdn() {
        return accIdn;
    }

    public void setAccIdn(String accIdn) {
        this.accIdn = accIdn;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getAccRel() {
        return accRel;
    }

    public void setAccRel(String accRel) {
        this.accRel = accRel;
    }

    public String getGrdBirth() {
        return grdBirth;
    }

    public void setGrdBirth(String grdBirth) {
        this.grdBirth = grdBirth;
    }

}
