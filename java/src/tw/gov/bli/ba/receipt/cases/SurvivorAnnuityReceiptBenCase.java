package tw.gov.bli.ba.receipt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Baapplog;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 遺屬年金受理作業 遺屬資料
 * 
 * @author Rickychi
 */
public class SurvivorAnnuityReceiptBenCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
    private BigDecimal baappbaseId;// 給付主檔資料列編號
    private BigDecimal bafamilytempId;// 遺屬暫存擋資料列編號
    private BigDecimal baappexpandId;// 給付延伸檔資料列編號
    private String apNo;// 受理編號
    private String seqNo;// 序號

    // 遺屬資料
    private String benNationTyp;// 國籍別
    private String benSex;// 性別
    private String benNationCode;// 國籍
    private String benName;// 遺屬姓名
    private String benAppDate;// 遺屬申請日期
    private String benIdnNo;// 遺屬身分證號
    private String benBrDate;// 遺屬者出生日期
    private String benEvtRel;// 關係
    private String tel1;// 電話1
    private String tel2;// 電話2
    private String commTyp;// 通訊地址別
    private String commZip;// 通訊郵遞區號
    private String commAddr;// 通訊地址
    private String grdName;// 法定代理人姓名
    private String grdIdnNo;// 法定代理人身分證號
    private String grdBrDate;// 法定代理人出生日期
    private String marryDate;// 結婚日期
    private String studMk;// 在學註記
    private String monIncomeMk;// 每月工作收入註記
    private BigDecimal monIncome;// 每月工作收入金額
    private String handIcapMk;// 領有重度以上身心障礙手冊
    private String interDictMk;// 受禁治產(監護)宣告
    private String mobilePhone;// 手機複驗

    // 給付資料
    private String accData;// 具名領取資料
    private String accIdn;// 戶名IDN
    private String accName;// 戶名
    private String accRel;// 戶名與受益人關係
    private String accSeqNo;// 具名領取人
    private String payTyp;// 給付方式
    private String bankName;// 金融機構名稱
    private String payBankId;// 金融機構總代號
    private String branchId;// 分支代號
    private String payEeacc;// 銀行帳號
    private String payBankIdBranchId;// 帳號前7碼
    private String chkPayBankIdChkBranchId;// 帳號(前) 複驗
    private String chkPayEeacc;// 銀行帳號 (帳號(後)) 複驗
    private String chkPayBankId;// 金融機構總代號 複驗
    private String chkBranchId;// 分支代號 複驗

    private String payCategory;// 給付方式(本人領取 or 具名領取)
    private BigDecimal mitRate;

    // Field for Bappbasetemp
    private String famAppDate;// 遺屬/眷屬申請日期
    private String famIdnNo;// 遺屬/眷屬身分證號
    private String famName;// 遺屬/眷屬姓名
    private String famBrDate;// 遺屬/眷屬出生日期
    private String famSex;// 遺屬/眷屬性別
    private String famNationTyp;// 遺屬/眷屬國籍別
    private String famNationCode;// 遺屬/眷屬國籍
    private String famEvtRel;// 遺屬/眷屬與事故者關係
    private String benIds;// 遺屬社福識別碼

    private String accSeqNoAmt;// 被具名領取筆數

    private List<Baapplog> baapplogList;// 更正記錄檔

    // 頁面顯示轉換
    // [
    public String getPayBankIdBranchIdStr() {
        return getPayBankId() + getBranchId();
    }

    // public String getApNoStrDisplay() {
    // return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12);
    // }

    public String getBenAppDateStr() {
        if (StringUtils.isNotBlank(getBenAppDate()) && getBenAppDate().length() == 8) {
            return DateUtility.changeDateType(getBenAppDate());
        }
        else {
            return getBenAppDate();
        }
    }

    public String getBenBrDateStr() {
        if (StringUtils.isNotBlank(getBenBrDate()) && getBenBrDate().length() == 8) {
            return DateUtility.changeDateType(getBenBrDate());
        }
        else {
            return getBenBrDate();
        }
    }

    public String getGrdBrDateStr() {
        if (StringUtils.isNotBlank(getGrdBrDate()) && getGrdBrDate().length() == 8) {
            return DateUtility.changeDateType(getGrdBrDate());
        }
        else {
            return getGrdBrDate();
        }
    }

    public String getMarryDateStr() {
        if (StringUtils.isNotBlank(getMarryDate()) && getMarryDate().length() == 8) {
            return DateUtility.changeDateType(getMarryDate());
        }
        else {
            return getMarryDate();
        }
    }

    public String getBenEvtRelStr() {
        String benEvtRelStr = "";
        if (("1").equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_1;
        }
        else if (("2").equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_2;
        }
        else if (("3").equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_3;
        }
        else if (("4").equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_4;
        }
        else if (("5").equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_5;
        }
        else if (("6").equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_6;
        }
        else if (("7").equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_7;
        }
        else if (("A").equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_A;
        }
        else if (("C").equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_C;
        }
        else if (("E").equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_E;
        }
        else if (("F").equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_F;
        }
        else if (("N").equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_N;
        }
        else if (("Z").equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_Z;
        }
        else if (("O").equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_O;
        }
        return benEvtRelStr;
    }

    public String getStudMkStr() {
        String studMkStr = "";
        if (("Y").equals(getStudMk())) {
            studMkStr = "是";
        }
        return studMkStr;
    }

    public String getHandIcapMkStr() {
        String handIcapMkStr = "";
        if (("Y").equals(getHandIcapMk())) {
            handIcapMkStr = "有";
        }
        return handIcapMkStr;
    }

    // ]

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

    public String getBenNationTyp() {
        return benNationTyp;
    }

    public void setBenNationTyp(String benNationTyp) {
        this.benNationTyp = benNationTyp;
    }

    public String getBenSex() {
        return benSex;
    }

    public void setBenSex(String benSex) {
        this.benSex = benSex;
    }

    public String getBenNationCode() {
        return benNationCode;
    }

    public void setBenNationCode(String benNationCode) {
        this.benNationCode = benNationCode;
    }

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }

    public String getBenAppDate() {
        return benAppDate;
    }

    public void setBenAppDate(String benAppDate) {
        this.benAppDate = benAppDate;
    }

    public String getBenIdnNo() {
        return benIdnNo;
    }

    public void setBenIdnNo(String benIdnNo) {
        this.benIdnNo = benIdnNo;
    }

    public String getBenBrDate() {
        return benBrDate;
    }

    public void setBenBrDate(String benBrDate) {
        this.benBrDate = benBrDate;
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

    public String getGrdName() {
        return grdName;
    }

    public void setGrdName(String grdName) {
        this.grdName = grdName;
    }

    public String getGrdIdnNo() {
        return grdIdnNo;
    }

    public void setGrdIdnNo(String grdIdnNo) {
        this.grdIdnNo = grdIdnNo;
    }

    public String getGrdBrDate() {
        return grdBrDate;
    }

    public void setGrdBrDate(String grdBrDate) {
        this.grdBrDate = grdBrDate;
    }

    public String getMarryDate() {
        return marryDate;
    }

    public void setMarryDate(String marryDate) {
        this.marryDate = marryDate;
    }

    public String getStudMk() {
        return studMk;
    }

    public void setStudMk(String studMk) {
        this.studMk = studMk;
    }

    public String getMonIncomeMk() {
        return monIncomeMk;
    }

    public void setMonIncomeMk(String monIncomeMk) {
        this.monIncomeMk = monIncomeMk;
    }

    public BigDecimal getMonIncome() {
        return monIncome;
    }

    public void setMonIncome(BigDecimal monIncome) {
        this.monIncome = monIncome;
    }

    public String getHandIcapMk() {
        return handIcapMk;
    }

    public void setHandIcapMk(String handIcapMk) {
        this.handIcapMk = handIcapMk;
    }

    public String getInterDictMk() {
        return interDictMk;
    }

    public void setInterDictMk(String interDictMk) {
        this.interDictMk = interDictMk;
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

    public String getAccSeqNo() {
        return accSeqNo;
    }

    public void setAccSeqNo(String accSeqNo) {
        this.accSeqNo = accSeqNo;
    }

    public String getPayTyp() {
        return payTyp;
    }

    public void setPayTyp(String payTyp) {
        this.payTyp = payTyp;
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

    public String getPayEeacc() {
        return payEeacc;
    }

    public void setPayEeacc(String payEeacc) {
        this.payEeacc = payEeacc;
    }

    public String getPayBankIdBranchId() {
        return payBankIdBranchId;
    }

    public void setPayBankIdBranchId(String payBankIdBranchId) {
        this.payBankIdBranchId = payBankIdBranchId;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public BigDecimal getBafamilytempId() {
        return bafamilytempId;
    }

    public void setBafamilytempId(BigDecimal bafamilytempId) {
        this.bafamilytempId = bafamilytempId;
    }

    public String getAccData() {
        return accData;
    }

    public void setAccData(String accData) {
        this.accData = accData;
    }

    public String getPayCategory() {
        return payCategory;
    }

    public void setPayCategory(String payCategory) {
        this.payCategory = payCategory;
    }

    public String getAccIdn() {
        return accIdn;
    }

    public void setAccIdn(String accIdn) {
        this.accIdn = accIdn;
    }

    public String getFamAppDate() {
        return famAppDate;
    }

    public void setFamAppDate(String famAppDate) {
        this.famAppDate = famAppDate;
    }

    public String getFamIdnNo() {
        return famIdnNo;
    }

    public void setFamIdnNo(String famIdnNo) {
        this.famIdnNo = famIdnNo;
    }

    public String getFamName() {
        return famName;
    }

    public void setFamName(String famName) {
        this.famName = famName;
    }

    public String getFamBrDate() {
        return famBrDate;
    }

    public void setFamBrDate(String famBrDate) {
        this.famBrDate = famBrDate;
    }

    public String getFamSex() {
        return famSex;
    }

    public void setFamSex(String famSex) {
        this.famSex = famSex;
    }

    public String getFamNationTyp() {
        return famNationTyp;
    }

    public void setFamNationTyp(String famNationTyp) {
        this.famNationTyp = famNationTyp;
    }

    public String getFamNationCode() {
        return famNationCode;
    }

    public void setFamNationCode(String famNationCode) {
        this.famNationCode = famNationCode;
    }

    public String getFamEvtRel() {
        return famEvtRel;
    }

    public void setFamEvtRel(String famEvtRel) {
        this.famEvtRel = famEvtRel;
    }

    public List<Baapplog> getBaapplogList() {
        return baapplogList;
    }

    public void setBaapplogList(List<Baapplog> baapplogList) {
        this.baapplogList = baapplogList;
    }

    public BigDecimal getBaappexpandId() {
        return baappexpandId;
    }

    public void setBaappexpandId(BigDecimal baappexpandId) {
        this.baappexpandId = baappexpandId;
    }

    public String getBenIds() {
        return benIds;
    }

    public void setBenIds(String benIds) {
        this.benIds = benIds;
    }

    public String getAccSeqNoAmt() {
        return accSeqNoAmt;
    }

    public void setAccSeqNoAmt(String accSeqNoAmt) {
        this.accSeqNoAmt = accSeqNoAmt;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public BigDecimal getMitRate() {
        return mitRate;
    }

    public void setMitRate(BigDecimal mitRate) {
        this.mitRate = mitRate;
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

}
