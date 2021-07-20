package tw.gov.bli.ba.receipt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Baapplog;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 遺屬年金受理作業
 * 
 * @author Rickychi
 */
public class SurvivorAnnuityReceiptCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
    private BigDecimal baappbaseId;// 資料列編號
    private String apNo;// 受理編號
    private String apNo1;// 受理編號-1
    private String apNo2;// 受理編號-2
    private String apNo3;// 受理編號-3
    private String apNo4;// 受理編號-4

    // 事故者資料
    // private String apNo ;//受理編號
    private String apUbno;// 申請單位保險證號
    private String appDate;// 申請日期
    private String evtNationTpe;// 國籍別
    private String evtDieDate;// 死亡日期
    private String evtJobDate;// 事故日期
    private String evtSex;// 性別
    private String evtNationCode;// 國籍
    private String evtName;// 事故者姓名
    private String evtIdnNo;// 事故者身分證號
    private String evtBrDate;// 事故者出生日期
    private String evTyp;// 傷病分類
    private String apItem;// 申請項目

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
    private String studMk;// 在學
    private String monIncomeMk;// 每月工作收入註記
    private BigDecimal monIncome;// 每月工作收入
    private String handIcap;// 領有重度以上身心障礙手冊或證明
    private String interDictMk;// 受禁治產(監護)宣告
    private String accData;// 具名領取資料
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
    private String chkPayBankId;// 金融機構總代號
    private String chkBranchId;// 分支代號

    private List<Baapplog> baapplogList;// 更正記錄檔

    // 其他主檔欄位(存檔時用)
    // [
    private String seqNo;// 序號
    private String imk;// 保險別
    private String payKind;// 給付種類
    private String caseTyp;// 案件類別
    private String mapNo;// 相關受理編號
    private String mapRootMk;// 相關受理編號註記
    private String combapMark;// 國勞合併申請註記
    private String caseMk;// 案件註記
    private String procStat;// 處理狀態
    private String acceptMk;// 合格註記
    private String manchkMk;// 人工審核結果
    private String chgNote;// 更正原因
    private String exeStat;// 決行狀況
    private String apubnock;// 申請單位保險證號檢查碼
    private String lsUbno;// 最後單位保險證號
    private String lsUbnoCk;// 最後單位保險證號檢查碼
    private String evtIds;// 事故者社福識別碼
    private String evtIdent;// 事故者身分別
    private String evtDate;// 事故日期
    private String evtAge;// 事故者申請時年齡
    private String benIds;// 受益人社福識別碼
    private String benDieDate;// 受益人死亡日期
    private String benPayMk;// 受益人領取狀態註記
    private String idnChkYm;// 身分查核年月
    private String idnChkNote;// 身分查核註記
    private String accIdn;// 戶名IDN
    private BigDecimal mitRate;// 匯款匯費
    private String assignIdnNo;// 受委託人身分證號
    private String assignName;// 受委託人姓名
    private String assignBrDate;// 受委託人出生日期
    private String issuYm;// 核定年月
    private String payYm;// 給付年月
    private BigDecimal issueAmt;// 核定金額
    private String oncePayMk;// 一次給付符合註記
    private String oldSeniab;// 一次請領之年資採計方式
    private BigDecimal mustIssueAmt;// 可領金額
    private String payDate;// 首次核定日期
    private String payYms;// 首次發放起月
    private String payYme;// 首次發放迄月
    private String pauseYm;// 暫停續發年月
    private String unPauseYm;// 恢復續發年月
    private String notifyForm;// 核定通知書格式
    private String loanMk;// 不須抵銷紓困貸款註記
    private String mexcLvl;// 應決行層級
    private String realexcLvl;// 實際決行層級
    private String chkDate;// 審核日期
    private String chkMan;// 審核人員
    private String rechkDate;// 複核日期
    private String rechkMan;// 複核人員
    private String exeDate;// 決行日期
    private String exeMan;// 決行人員
    private String arcDate;// 歸檔日期
    private String arcPg;// 歸檔頁次
    private String closeDate;// 結案日期
    private String closeCause;// 結案原因
    private String promoteUser;// 承辦者代號
    private String crtUser;// 新增者代號
    private String crtTime;// 新增日期時間
    private String updUser;// 異動者代號
    private String updTime;// 異動日期時間
    private String dupeIdnNoMk;// 身分證重號註記
    private String evtExpireDate; // 事故者屆齡日期
    private String evtEligibleDate; // 事故者符合日期
    private String evtMissingDate; // 事故者失蹤日期
    private BigDecimal lsInsmAmt; // 投保金額
    private String lsChkMk;// 勞貸戶註記
    private BigDecimal lsCount;// 勞貸戶筆數
    private String lnChkMk;// 紓困貸款呆帳債務人註記
    private String dabApNo;// 已領失能年金受理編號
    private BigDecimal dabAnnuAmt;// 已領失能年金金額
    private String reSeniMk;// 保留年資
    private String offInsurDate;// 轉公保日期
    private String lawRetireDate;// 依法退休日期
    private String evtAppName;// 事故者申請時姓名
    private BigDecimal cutAmt;// 扣減/補償總金額
    private String benMarrMk;// 婚姻狀況
    private String chgMk;// 更正註記
    private String prfmlyMk;// 有無前一順位親屬
    private String digamyDate;// 再婚日期
    private String divorceDate;// 離婚日期
    private String abanApply;// 放棄請領
    private String abanapDate;// 放棄請領起始日期
    private String evCode;// 傷病原因
    private String criInPart1;// 受傷部位1
    private String criInPart2;// 受傷部位2
    private String criInPart3;// 受傷部位3
    private String criMedium;// 媒介物
    private String criInjCl1;// 失能等級1
    private String criInjCl2;// 失能等級2
    private String criInjCl3;// 失能等級3
    private String criInJdp1;// 失能項目1
    private String criInJdp2;// 失能項目2
    private String criInJdp3;// 失能項目3
    private String criInJdp4;// 失能項目4
    private String criInJdp5;// 失能項目5
    private String criInJdp6;// 失能項目6
    private String criInJdp7;// 失能項目7
    private String criInJdp8;// 失能項目8
    private String criInJdp9;// 失能項目9
    private String criInJdp10;// 失能項目10
    private String criInIssul;// 核定等級
    private String criInJnme1;// 國際疾病代碼1
    private String criInJnme2;// 國際疾病代碼2
    private String criInJnme3;// 國際疾病代碼3
    private String criInJnme4;// 國際疾病代碼4
    private String raiseChild;// 扶養子女
    private String rehcMk;// 重新查核失能程度註記
    private String rehcYm;// 重新查核失能程度年月
    private String hosId;// 醫療院所代碼
    private String doctorName1;// 醫師姓名1
    private String doctorName2;// 醫師姓名2
    private String cancelMk;// 撤銷註記
    private String cancelYm;// 撤銷請領年月
    private String interDictDate;// 受禁治產(監護)宣告日期
    private String relatChgDate;// 親屬關係變動日期
    private String adoPtnMk;// 收養未滿註記
    private String judgeDate;// 判決日期
    private String benMissingSdate;// 受益人失蹤期間(起)
    private String benMissingEdate;// 受益人失蹤期間(迄)
    private String prisonSdate;// 監管期間(起)
    private String prisonEdate;// 監管期間(迄)

    // ]

    // 頁面顯示轉換
    // [
    public String getPayBankIdBranchIdStr() {
        return getPayBankId() + getBranchId();
    }

    public String getApNoStrDisplay() {
        return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12);
    }

    public String getAppDateStr() {
        if (StringUtils.isNotBlank(getAppDate()) && getAppDate().length() == 8) {
            return DateUtility.changeDateType(getAppDate());
        }
        else {
            return getAppDate();
        }
    }

    public String getEvtDieDateStr() {
        if (StringUtils.isNotBlank(getEvtDieDate()) && getEvtDieDate().length() == 8) {
            return DateUtility.changeDateType(getEvtDieDate());
        }
        else {
            return getEvtDieDate();
        }
    }

    public String getEvtJobDateStr() {
        if (StringUtils.isNotBlank(getEvtJobDate()) && getEvtJobDate().length() == 8) {
            return DateUtility.changeDateType(getEvtJobDate());
        }
        else {
            return getEvtJobDate();
        }
    }

    public String getEvtBrDateStr() {
        if (StringUtils.isNotBlank(getEvtBrDate()) && getEvtBrDate().length() == 8) {
            return DateUtility.changeDateType(getEvtBrDate());
        }
        else {
            return getEvtBrDate();
        }
    }

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
        if (("1").equals(getBenEvtRelStr())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_1;
        }
        else if (("2").equals(getBenEvtRelStr())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_2;
        }
        else if (("3").equals(getBenEvtRelStr())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_3;
        }
        else if (("4").equals(getBenEvtRelStr())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_4;
        }
        else if (("5").equals(getBenEvtRelStr())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_5;
        }
        else if (("6").equals(getBenEvtRelStr())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_6;
        }
        else if (("7").equals(getBenEvtRelStr())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_7;
        }
        else if (("A").equals(getBenEvtRelStr())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_A;
        }
        else if (("C").equals(getBenEvtRelStr())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_C;
        }
        else if (("E").equals(getBenEvtRelStr())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_E;
        }
        else if (("F").equals(getBenEvtRelStr())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_F;
        }
        else if (("N").equals(getBenEvtRelStr())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_N;
        }
        else if (("Z").equals(getBenEvtRelStr())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_Z;
        }
        else if (("O").equals(getBenEvtRelStr())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_O;
        }

        return benEvtRelStr;
    }

    public String getApItemStr() {
        String apItemStr = "";
        if (("1").equals(getApItem())) {
            apItemStr = ConstantKey.BAAPPBASE_APITEM_CODESTR_1;
        }
        else if (("2").equals(getApItem())) {
            apItemStr = ConstantKey.BAAPPBASE_APITEM_CODESTR_2;
        }
        else if (("3").equals(getApItem())) {
            apItemStr = ConstantKey.BAAPPBASE_APITEM_CODESTR_3;
        }
        else if (("4").equals(getApItem())) {
            apItemStr = ConstantKey.BAAPPBASE_APITEM_CODESTR_4;
        }
        else if (("5").equals(getApItem())) {
            apItemStr = ConstantKey.BAAPPBASE_APITEM_CODESTR_5;
        }
        else if (("7").equals(getApItem())) {
            apItemStr = ConstantKey.BAAPPBASE_APITEM_CODESTR_7;
        }
        else if (("8").equals(getApItem())) {
            apItemStr = ConstantKey.BAAPPBASE_APITEM_CODESTR_8;
        }
        else if (("9").equals(getApItem())) {
            apItemStr = ConstantKey.BAAPPBASE_APITEM_CODESTR_9;
        }
        else if (("0").equals(getApItem())) {
            apItemStr = ConstantKey.BAAPPBASE_APITEM_CODESTR_0;
        }

        return apItemStr;
    }

    public String getStudMkStr() {
        String studMkStr = "";
        if ((ConstantKey.BAAPPBASE_STUDMK_0).equals(getStudMk())) {
            studMkStr = ConstantKey.BAAPPBASE_STUDMK_STR_0;
        }
        else if ((ConstantKey.BAAPPBASE_STUDMK_1).equals(getStudMk())) {
            studMkStr = ConstantKey.BAAPPBASE_STUDMK_STR_1;
        }
        else if ((ConstantKey.BAAPPBASE_STUDMK_2).equals(getStudMk())) {
            studMkStr = ConstantKey.BAAPPBASE_STUDMK_STR_2;
        }
        return studMkStr;
    }

    public String getHandIcapStr() {
        String handIcapStr = "";
        if (("Y").equals(getHandIcap())) {
            handIcapStr = ConstantKey.BAAPPBASE_HANDICAP_STR_Y;
        }
        else {
            handIcapStr = ConstantKey.BAAPPBASE_HANDICAP_STR_N;
        }
        return handIcapStr;
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

    public String getApUbno() {
        return apUbno;
    }

    public void setApUbno(String apUbno) {
        this.apUbno = apUbno;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getEvtNationTpe() {
        return evtNationTpe;
    }

    public void setEvtNationTpe(String evtNationTpe) {
        this.evtNationTpe = evtNationTpe;
    }

    public String getEvtDieDate() {
        return evtDieDate;
    }

    public void setEvtDieDate(String evtDieDate) {
        this.evtDieDate = evtDieDate;
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

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
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

    public String getEvTyp() {
        return evTyp;
    }

    public void setEvTyp(String evTyp) {
        this.evTyp = evTyp;
    }

    public String getApItem() {
        return apItem;
    }

    public void setApItem(String apItem) {
        this.apItem = apItem;
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

    public String getHandIcap() {
        return handIcap;
    }

    public void setHandIcap(String handIcap) {
        this.handIcap = handIcap;
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

    public String getEvtJobDate() {
        return evtJobDate;
    }

    public void setEvtJobDate(String evtJobDate) {
        this.evtJobDate = evtJobDate;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getImk() {
        return imk;
    }

    public void setImk(String imk) {
        this.imk = imk;
    }

    public String getPayKind() {
        return payKind;
    }

    public void setPayKind(String payKind) {
        this.payKind = payKind;
    }

    public String getCaseTyp() {
        return caseTyp;
    }

    public void setCaseTyp(String caseTyp) {
        this.caseTyp = caseTyp;
    }

    public String getMapNo() {
        return mapNo;
    }

    public void setMapNo(String mapNo) {
        this.mapNo = mapNo;
    }

    public String getMapRootMk() {
        return mapRootMk;
    }

    public void setMapRootMk(String mapRootMk) {
        this.mapRootMk = mapRootMk;
    }

    public String getCombapMark() {
        return combapMark;
    }

    public void setCombapMark(String combapMark) {
        this.combapMark = combapMark;
    }

    public String getCaseMk() {
        return caseMk;
    }

    public void setCaseMk(String caseMk) {
        this.caseMk = caseMk;
    }

    public String getProcStat() {
        return procStat;
    }

    public void setProcStat(String procStat) {
        this.procStat = procStat;
    }

    public String getAcceptMk() {
        return acceptMk;
    }

    public void setAcceptMk(String acceptMk) {
        this.acceptMk = acceptMk;
    }

    public String getManchkMk() {
        return manchkMk;
    }

    public void setManchkMk(String manchkMk) {
        this.manchkMk = manchkMk;
    }

    public String getChgNote() {
        return chgNote;
    }

    public void setChgNote(String chgNote) {
        this.chgNote = chgNote;
    }

    public String getExeStat() {
        return exeStat;
    }

    public void setExeStat(String exeStat) {
        this.exeStat = exeStat;
    }

    public String getApubnock() {
        return apubnock;
    }

    public void setApubnock(String apubnock) {
        this.apubnock = apubnock;
    }

    public String getLsUbno() {
        return lsUbno;
    }

    public void setLsUbno(String lsUbno) {
        this.lsUbno = lsUbno;
    }

    public String getLsUbnoCk() {
        return lsUbnoCk;
    }

    public void setLsUbnoCk(String lsUbnoCk) {
        this.lsUbnoCk = lsUbnoCk;
    }

    public String getEvtIds() {
        return evtIds;
    }

    public void setEvtIds(String evtIds) {
        this.evtIds = evtIds;
    }

    public String getEvtIdent() {
        return evtIdent;
    }

    public void setEvtIdent(String evtIdent) {
        this.evtIdent = evtIdent;
    }

    public String getEvtDate() {
        return evtDate;
    }

    public void setEvtDate(String evtDate) {
        this.evtDate = evtDate;
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

    public String getBenDieDate() {
        return benDieDate;
    }

    public void setBenDieDate(String benDieDate) {
        this.benDieDate = benDieDate;
    }

    public String getBenPayMk() {
        return benPayMk;
    }

    public void setBenPayMk(String benPayMk) {
        this.benPayMk = benPayMk;
    }

    public String getIdnChkYm() {
        return idnChkYm;
    }

    public void setIdnChkYm(String idnChkYm) {
        this.idnChkYm = idnChkYm;
    }

    public String getIdnChkNote() {
        return idnChkNote;
    }

    public void setIdnChkNote(String idnChkNote) {
        this.idnChkNote = idnChkNote;
    }

    public String getAccIdn() {
        return accIdn;
    }

    public void setAccIdn(String accIdn) {
        this.accIdn = accIdn;
    }

    public BigDecimal getMitRate() {
        return mitRate;
    }

    public void setMitRate(BigDecimal mitRate) {
        this.mitRate = mitRate;
    }

    public String getAssignIdnNo() {
        return assignIdnNo;
    }

    public void setAssignIdnNo(String assignIdnNo) {
        this.assignIdnNo = assignIdnNo;
    }

    public String getAssignName() {
        return assignName;
    }

    public void setAssignName(String assignName) {
        this.assignName = assignName;
    }

    public String getAssignBrDate() {
        return assignBrDate;
    }

    public void setAssignBrDate(String assignBrDate) {
        this.assignBrDate = assignBrDate;
    }

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String getPayYm() {
        return payYm;
    }

    public void setPayYm(String payYm) {
        this.payYm = payYm;
    }

    public BigDecimal getIssueAmt() {
        return issueAmt;
    }

    public void setIssueAmt(BigDecimal issueAmt) {
        this.issueAmt = issueAmt;
    }

    public String getOncePayMk() {
        return oncePayMk;
    }

    public void setOncePayMk(String oncePayMk) {
        this.oncePayMk = oncePayMk;
    }

    public String getOldSeniab() {
        return oldSeniab;
    }

    public void setOldSeniab(String oldSeniab) {
        this.oldSeniab = oldSeniab;
    }

    public BigDecimal getMustIssueAmt() {
        return mustIssueAmt;
    }

    public void setMustIssueAmt(BigDecimal mustIssueAmt) {
        this.mustIssueAmt = mustIssueAmt;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPayYms() {
        return payYms;
    }

    public void setPayYms(String payYms) {
        this.payYms = payYms;
    }

    public String getPayYme() {
        return payYme;
    }

    public void setPayYme(String payYme) {
        this.payYme = payYme;
    }

    public String getPauseYm() {
        return pauseYm;
    }

    public void setPauseYm(String pauseYm) {
        this.pauseYm = pauseYm;
    }

    public String getUnPauseYm() {
        return unPauseYm;
    }

    public void setUnPauseYm(String unPauseYm) {
        this.unPauseYm = unPauseYm;
    }

    public String getNotifyForm() {
        return notifyForm;
    }

    public void setNotifyForm(String notifyForm) {
        this.notifyForm = notifyForm;
    }

    public String getLoanMk() {
        return loanMk;
    }

    public void setLoanMk(String loanMk) {
        this.loanMk = loanMk;
    }

    public String getMexcLvl() {
        return mexcLvl;
    }

    public void setMexcLvl(String mexcLvl) {
        this.mexcLvl = mexcLvl;
    }

    public String getRealexcLvl() {
        return realexcLvl;
    }

    public void setRealexcLvl(String realexcLvl) {
        this.realexcLvl = realexcLvl;
    }

    public String getChkDate() {
        return chkDate;
    }

    public void setChkDate(String chkDate) {
        this.chkDate = chkDate;
    }

    public String getChkMan() {
        return chkMan;
    }

    public void setChkMan(String chkMan) {
        this.chkMan = chkMan;
    }

    public String getRechkDate() {
        return rechkDate;
    }

    public void setRechkDate(String rechkDate) {
        this.rechkDate = rechkDate;
    }

    public String getRechkMan() {
        return rechkMan;
    }

    public void setRechkMan(String rechkMan) {
        this.rechkMan = rechkMan;
    }

    public String getExeDate() {
        return exeDate;
    }

    public void setExeDate(String exeDate) {
        this.exeDate = exeDate;
    }

    public String getExeMan() {
        return exeMan;
    }

    public void setExeMan(String exeMan) {
        this.exeMan = exeMan;
    }

    public String getArcDate() {
        return arcDate;
    }

    public void setArcDate(String arcDate) {
        this.arcDate = arcDate;
    }

    public String getArcPg() {
        return arcPg;
    }

    public void setArcPg(String arcPg) {
        this.arcPg = arcPg;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public String getCloseCause() {
        return closeCause;
    }

    public void setCloseCause(String closeCause) {
        this.closeCause = closeCause;
    }

    public String getPromoteUser() {
        return promoteUser;
    }

    public void setPromoteUser(String promoteUser) {
        this.promoteUser = promoteUser;
    }

    public String getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser;
    }

    public String getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(String crtTime) {
        this.crtTime = crtTime;
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

    public String getDupeIdnNoMk() {
        return dupeIdnNoMk;
    }

    public void setDupeIdnNoMk(String dupeIdnNoMk) {
        this.dupeIdnNoMk = dupeIdnNoMk;
    }

    public String getEvtExpireDate() {
        return evtExpireDate;
    }

    public void setEvtExpireDate(String evtExpireDate) {
        this.evtExpireDate = evtExpireDate;
    }

    public String getEvtEligibleDate() {
        return evtEligibleDate;
    }

    public void setEvtEligibleDate(String evtEligibleDate) {
        this.evtEligibleDate = evtEligibleDate;
    }

    public String getEvtMissingDate() {
        return evtMissingDate;
    }

    public void setEvtMissingDate(String evtMissingDate) {
        this.evtMissingDate = evtMissingDate;
    }

    public BigDecimal getLsInsmAmt() {
        return lsInsmAmt;
    }

    public void setLsInsmAmt(BigDecimal lsInsmAmt) {
        this.lsInsmAmt = lsInsmAmt;
    }

    public String getLsChkMk() {
        return lsChkMk;
    }

    public void setLsChkMk(String lsChkMk) {
        this.lsChkMk = lsChkMk;
    }

    public BigDecimal getLsCount() {
        return lsCount;
    }

    public void setLsCount(BigDecimal lsCount) {
        this.lsCount = lsCount;
    }

    public String getLnChkMk() {
        return lnChkMk;
    }

    public void setLnChkMk(String lnChkMk) {
        this.lnChkMk = lnChkMk;
    }

    public String getDabApNo() {
        return dabApNo;
    }

    public void setDabApNo(String dabApNo) {
        this.dabApNo = dabApNo;
    }

    public BigDecimal getDabAnnuAmt() {
        return dabAnnuAmt;
    }

    public void setDabAnnuAmt(BigDecimal dabAnnuAmt) {
        this.dabAnnuAmt = dabAnnuAmt;
    }

    public String getReSeniMk() {
        return reSeniMk;
    }

    public void setReSeniMk(String reSeniMk) {
        this.reSeniMk = reSeniMk;
    }

    public String getOffInsurDate() {
        return offInsurDate;
    }

    public void setOffInsurDate(String offInsurDate) {
        this.offInsurDate = offInsurDate;
    }

    public String getLawRetireDate() {
        return lawRetireDate;
    }

    public void setLawRetireDate(String lawRetireDate) {
        this.lawRetireDate = lawRetireDate;
    }

    public String getEvtAppName() {
        return evtAppName;
    }

    public void setEvtAppName(String evtAppName) {
        this.evtAppName = evtAppName;
    }

    public BigDecimal getCutAmt() {
        return cutAmt;
    }

    public void setCutAmt(BigDecimal cutAmt) {
        this.cutAmt = cutAmt;
    }

    public String getBenMarrMk() {
        return benMarrMk;
    }

    public void setBenMarrMk(String benMarrMk) {
        this.benMarrMk = benMarrMk;
    }

    public String getChgMk() {
        return chgMk;
    }

    public void setChgMk(String chgMk) {
        this.chgMk = chgMk;
    }

    public String getPrfmlyMk() {
        return prfmlyMk;
    }

    public void setPrfmlyMk(String prfmlyMk) {
        this.prfmlyMk = prfmlyMk;
    }

    public String getDigamyDate() {
        return digamyDate;
    }

    public void setDigamyDate(String digamyDate) {
        this.digamyDate = digamyDate;
    }

    public String getDivorceDate() {
        return divorceDate;
    }

    public void setDivorceDate(String divorceDate) {
        this.divorceDate = divorceDate;
    }

    public String getAbanApply() {
        return abanApply;
    }

    public void setAbanApply(String abanApply) {
        this.abanApply = abanApply;
    }

    public String getAbanapDate() {
        return abanapDate;
    }

    public void setAbanapDate(String abanapDate) {
        this.abanapDate = abanapDate;
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

    public String getCriInjCl1() {
        return criInjCl1;
    }

    public void setCriInjCl1(String criInjCl1) {
        this.criInjCl1 = criInjCl1;
    }

    public String getCriInjCl2() {
        return criInjCl2;
    }

    public void setCriInjCl2(String criInjCl2) {
        this.criInjCl2 = criInjCl2;
    }

    public String getCriInjCl3() {
        return criInjCl3;
    }

    public void setCriInjCl3(String criInjCl3) {
        this.criInjCl3 = criInjCl3;
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

    public String getCriInIssul() {
        return criInIssul;
    }

    public void setCriInIssul(String criInIssul) {
        this.criInIssul = criInIssul;
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

    public String getRaiseChild() {
        return raiseChild;
    }

    public void setRaiseChild(String raiseChild) {
        this.raiseChild = raiseChild;
    }

    public String getRehcMk() {
        return rehcMk;
    }

    public void setRehcMk(String rehcMk) {
        this.rehcMk = rehcMk;
    }

    public String getRehcYm() {
        return rehcYm;
    }

    public void setRehcYm(String rehcYm) {
        this.rehcYm = rehcYm;
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

    public String getCancelMk() {
        return cancelMk;
    }

    public void setCancelMk(String cancelMk) {
        this.cancelMk = cancelMk;
    }

    public String getCancelYm() {
        return cancelYm;
    }

    public void setCancelYm(String cancelYm) {
        this.cancelYm = cancelYm;
    }

    public String getInterDictDate() {
        return interDictDate;
    }

    public void setInterDictDate(String interDictDate) {
        this.interDictDate = interDictDate;
    }

    public String getRelatChgDate() {
        return relatChgDate;
    }

    public void setRelatChgDate(String relatChgDate) {
        this.relatChgDate = relatChgDate;
    }

    public String getAdoPtnMk() {
        return adoPtnMk;
    }

    public void setAdoPtnMk(String adoPtnMk) {
        this.adoPtnMk = adoPtnMk;
    }

    public String getJudgeDate() {
        return judgeDate;
    }

    public void setJudgeDate(String judgeDate) {
        this.judgeDate = judgeDate;
    }

    public String getBenMissingSdate() {
        return benMissingSdate;
    }

    public void setBenMissingSdate(String benMissingSdate) {
        this.benMissingSdate = benMissingSdate;
    }

    public String getBenMissingEdate() {
        return benMissingEdate;
    }

    public void setBenMissingEdate(String benMissingEdate) {
        this.benMissingEdate = benMissingEdate;
    }

    public String getPrisonSdate() {
        return prisonSdate;
    }

    public void setPrisonSdate(String prisonSdate) {
        this.prisonSdate = prisonSdate;
    }

    public String getPrisonEdate() {
        return prisonEdate;
    }

    public void setPrisonEdate(String prisonEdate) {
        this.prisonEdate = prisonEdate;
    }

    public List<Baapplog> getBaapplogList() {
        return baapplogList;
    }

    public void setBaapplogList(List<Baapplog> baapplogList) {
        this.baapplogList = baapplogList;
    }

    public String getAccData() {
        return accData;
    }

    public void setAccData(String accData) {
        this.accData = accData;
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
