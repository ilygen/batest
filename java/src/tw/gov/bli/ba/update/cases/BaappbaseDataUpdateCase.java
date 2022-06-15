package tw.gov.bli.ba.update.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Baapplog;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.util.DateUtil;

/**
 * Case for 資料更正
 * 
 * @author Rickychi
 */
public class BaappbaseDataUpdateCase implements Serializable {
    private BigDecimal baappbaseId;// 資料列編號
    private String apNo;// 受理編號
    private String seqNo;// 序號
    private String imk;// 保險別
    private String apItem;// 申請項目
    private String payKind;// 給付種類
    private String appDate;// 申請日期
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
    private String apUbno;// 申請單位保險證號
    private String apubnock;// 申請單位保險證號檢查碼
    private String lsUbno;// 最後單位保險證號
    private String lsUbnoCk;// 最後單位保險證號檢查碼
    private String evtIds;// 事故者社福識別碼
    private String evtIdnNo;// 事故者身分證號
    private String evtName;// 事故者姓名
    private String evtBrDate;// 事故者出生日期
    private String evtSex;// 事故者性別
    private String evtNationTpe;// 事故者國籍別
    private String evtNationCode;// 事故者國籍
    private String evtIdent;// 事故者身分別
    private String evtJobDate;// 事故者離職日期
    private String evtDate;// 事故日期
    private String evtDieDate;// 事故者死亡日期
    private String evtAge;// 事故者申請時年齡
    private String benIds;// 受益人社福識別碼
    private String benIdnNo;// 受益人身分證號
    private String benName;// 受益人姓名
    private String benBrDate;// 受益人出生日期
    private String benSex;// 受益人性別
    private String benNationTyp;// 受益人國籍別
    private String benNationCode;// 受益人國籍
    private String benEvtRel;// 受益人與事故者關係
    private String benDieDate;// 受益人死亡日期
    private String benPayMk;// 受益人領取狀態註記
    private String idnChkYm;// 身分查核年月
    private String idnChkNote;// 身分查核註記
    private String tel1;// 電話1
    private String tel2;// 電話2
    private String commTyp;// 通訊地址別
    private String commZip;// 通訊郵遞區號
    private String commAddr;// 通訊地址
    private String payTyp;// 給付方式
    private String bankName;// 金融機構名稱
    private String payBankId;// 金融機構總代號
    private String branchId;// 分支代號
    private String payEeacc;// 銀行帳號 (帳號(後))
    private String accIdn;// 戶名IDN
    private String accName;// 戶名
    private String accRel;// 戶名與受益人關係
    private BigDecimal mitRate;// 匯款匯費
    private String grdIdnNo;// 法定代理人身分證號
    private String grdName;// 法定代理人姓名
    private String grdBrDate;// 法定代理人出生日期
    private String assignIdnNo;// 受委託人身分證號
    private String assignName;// 受委託人姓名
    private String assignBrDate;// 受委託人出生日期
    private String issuYm;// 核定年月
    private String payYm;// 給付年月
    private BigDecimal issueAmt;// 核定金額
    private BigDecimal onceAmt;// 一次給付核定金額
    private BigDecimal onceAvgAmt;// 一次給付平均薪資
    private BigDecimal onceAplPayAmt;// 一次給付實發年資
    private String oncePayMk;// 一次給付符合註記
    private BigDecimal onceOldAmt;// 老年一次金金額
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
    private BigDecimal nitrmY;// 投保年資(年-年金制)
    private BigDecimal nitrmM;// 投保年資(月-年金制)
    private BigDecimal aplPaySeniY; // 實付年資(年)
    private BigDecimal aplPaySeniM; // 實付年資(月)
    private BigDecimal noldtY; // 老年年資(年)
    private BigDecimal noldtM; // 老年年資(月)
    private BigDecimal valseniY; // 國保已繳年資(年)
    private BigDecimal valseniM; // 國保已繳年資(月)
    private BigDecimal insAvgAmt; // 平均薪資
    private String isShowCvldtlName;// 是否顯示戶籍姓名; Y:顯示; N:不顯示
    private String isShowLoanMk;// 是否顯示不須抵銷紓困貸款; Y:顯示; N:不顯示
    private BigDecimal cutAmt;// 扣減/補償總金額
    private String benMarrMk;// 婚姻狀況
    private String chgMk;// 更正註記

    private String evtAppName;// 事故者申請時姓名
    private String evtAppIdnNo;// 事故者申請時身分證號
    private String evtAppBrDate;// 事故者申請時出生日期
    private String evtExpireDate; // 事故者屆齡日期
    private String evtEligibleDate; // 事故者符合日期
    private String evtMissingDate; // 事故者失蹤日期

    private String apNo1;// 受理編號-1
    private String apNo2;// 受理編號-2
    private String apNo3;// 受理編號-3
    private String apNo4;// 受理編號-4
    private String payBankIdBranchId;// 帳號(前)(bankId+branchId)
    private String pagePayKind;// 給付別(受理編號第一碼)
    private String benSelectResult;// 具名領取選單筆數(0:結果=0筆; 1:結果>=1筆)
    private String accSeqNoAmt;// 被具名領取筆數(0:結果=0筆; 1:結果>=1筆)
    private String payAccount;// 海外帳戶
    private String payCategory;// 給付方式(本人領取 or 具名領取)
    private String pageChkStr;// 頁面編審註記組合字串
    private String appUser;// 繼承自受款人

    private List<Baapplog> baapplogList;// 更正記錄檔

    private String dabApNo;// 已領失能年金受理編號
    private String dabApNo1;// 已領失能年金受理編號-1
    private String dabApNo2;// 已領失能年金受理編號-2
    private String dabApNo3;// 已領失能年金受理編號-3
    private String dabApNo4;// 已領失能年金受理編號-4
    private String reSeniMk;// 轉公保註記
    private String offInsurDate;// 轉公保日期
    private String lawRetireDate;// 依法退休日期

    private String cvldtlName;// 戶籍姓名
    private String accSeqNo;// 被共同具領之受款人員序號
    private BigDecimal accBaappbaseId;// 被共同具領之受款人員資料列編號
    private String cvlDtlCommZip;// 通訊郵遞區號(戶籍)
    private String cvlDtlCommAddr;// 通訊地址(戶籍)

    private String delButtonQ4;// 受款人資料更正 刪除鈕控制
    private String delBtnStatus;// 案件資料更正"註銷"按鈕狀態 Y:enable N:disable
    private String evtNameStatus;// 案件資料更正"事故者姓名"可否修改狀態 Y:enable N:disable
    private String oldAplDpt; // 申請代算單位
    private String isHeir; // 受款人或繼承人

    private String interValMonth;// 發放方式
    private String isShowInterValMonth;// 是否顯示發放方式

    private String oriIssuYm;// 核定年月
    private String brChkDate; // 退匯初核日期
    private BigDecimal remitAmt; // 退匯金額
    private String issuKind; // 核發種類
    private String brNote; // 退匯原因
    private String heirSeqNo;// 繼承人序
    private BigDecimal avgNum;// 繼承人分配後所得金額
    private String procUser; // 作業人員代號
    private String procDeptId; // 作業人員的部室別
    private String procIp; // 作業人員的 IP
    private String paramName;// 退匯原因
    private String indexCount;// 每月分配金額人數(產生報表時需使用)

    private String regetCipbMk; // 重讀CIPB

    // 【改匯註銷】按鈕的狀態及使用條件(是否開啟的條件)
    private String dateCount1; // 條件1
    private String dateCount2; // 條件2
    private String dateCount3; // 條件3

    private String indexString; // 帶入預設check勾選項目

    private String specialAcc;// 專戶

    private String speAccDate;// 設定專戶日期
    private String sysCode;// 系統類別
    private String apnoFm;// 來源受理編號

    public String getInterValMonth() {
        return interValMonth;
    }

    public void setInterValMonth(String interValMonth) {
        this.interValMonth = interValMonth;
    }

    public String getIsShowInterValMonth() {
        return isShowInterValMonth;
    }

    public void setIsShowInterValMonth(String isShowInterValMonth) {
        this.isShowInterValMonth = isShowInterValMonth;
    }

    public String getApNo1Str() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() >= 1) {
            return getApNo().substring(0, 1);
        }
        else {
            return "";
        }
    }

    public String getApNo2Str() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() >= 2) {
            return getApNo().substring(1, 2);
        }
        else {
            return "";
        }
    }

    public String getPayBankIdStr() {
        String payBankIdStr = "";

        payBankId = (StringUtils.isNotBlank(payBankId) ? payBankId : "");
        branchId = (StringUtils.isNotBlank(branchId) ? branchId : "");
        payEeacc = (StringUtils.isNotBlank(payEeacc) ? payEeacc : "");

        if (payTyp.equals("1") && !payBankId.equals("700")) {
            if (StringUtils.isBlank(payBankId) && StringUtils.isBlank(payEeacc)) {
                payBankIdStr = "";
            }
            else {
                payBankIdStr = payBankId + "-0000-" + payEeacc;
            }
        }
        else {
            payBankIdStr = payBankId + "-" + branchId + "-" + payEeacc;
        }
        return payBankIdStr;
    }

    public String getApNo3Str() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() >= 7) {
            return getApNo().substring(2, 7);
        }
        else {
            return "";
        }
    }

    public String getApNo4Str() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() >= 12) {
            return getApNo().substring(7, 12);
        }
        else {
            return "";
        }
    }

    public String getDelBtnStatus() {
        return delBtnStatus;
    }

    public void setDelBtnStatus(String delBtnStatus) {
        this.delBtnStatus = delBtnStatus;
    }

    public String getCvlDtlCommZip() {
        return cvlDtlCommZip;
    }

    public void setCvlDtlCommZip(String cvlDtlCommZip) {
        this.cvlDtlCommZip = cvlDtlCommZip;
    }

    public String getCvlDtlCommAddr() {
        return cvlDtlCommAddr;
    }

    public void setCvlDtlCommAddr(String cvlDtlCommAddr) {
        this.cvlDtlCommAddr = cvlDtlCommAddr;
    }

    public BigDecimal getAccBaappbaseId() {
        return accBaappbaseId;
    }

    public void setAccBaappbaseId(BigDecimal accBaappbaseId) {
        this.accBaappbaseId = accBaappbaseId;
    }

    public String getAccSeqNo() {
        return accSeqNo;
    }

    public void setAccSeqNo(String accSeqNo) {
        this.accSeqNo = accSeqNo;
    }

    // 頁面顯示轉換
    // [
    public String getOffInsurDateStr() {
        if (StringUtils.isNotBlank(getOffInsurDate())) {
            return DateUtility.changeDateType(getOffInsurDate());
        }
        else {
            return "";
        }
    }

    public String getLawRetireDateStr() {
        if (StringUtils.isNotBlank(getLawRetireDate())) {
            return DateUtility.changeDateType(getLawRetireDate());
        }
        else {
            return "";
        }
    }

    public String getOnceAplPayAmtStr() {
        String onceAplPayAmtStr = "";
        if (getOnceAplPayAmt() != null) {
            onceAplPayAmtStr = getOnceAplPayAmt().divide(new BigDecimal(12), 0, BigDecimal.ROUND_DOWN) + "年 " + getOnceAplPayAmt().remainder(new BigDecimal(12)) + "月";
        }
        return onceAplPayAmtStr;
    }

    public String getAppDateStr() {
        if (StringUtils.isNotBlank(getAppDate())) {
            return DateUtility.changeDateType(getAppDate());
        }
        else {
            return "";
        }
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

    public String getEvtDieDateStr() {
        if (StringUtils.isNotBlank(getEvtDieDate())) {
            return DateUtility.changeDateType(getEvtDieDate());
        }
        else {
            return "";
        }
    }

    public String getBenBrDateStr() {
        if (StringUtils.isNotBlank(getBenBrDate())) {
            return DateUtility.changeDateType(getBenBrDate());
        }
        else {
            return "";
        }
    }

    public String getGrdBrDateStr() {
        if (StringUtils.isNotBlank(getGrdBrDate())) {
            return DateUtility.changeDateType(getGrdBrDate());
        }
        else {
            return "";
        }
    }

    public String getEvtDateStr() {
        if (StringUtils.isNotBlank(getEvtDate())) {
            return DateUtility.changeDateType(getEvtDate());
        }
        else {
            return "";
        }
    }

    public String getApNoStr() {
        return getApNo1() + getApNo2() + getApNo3() + getApNo4();
    }

    public String getApNoStrDisplay() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH)
            return apNo.substring(0, 1) + "-" + apNo.substring(1, 2) + "-" + apNo.substring(2, 7) + "-" + apNo.substring(7, 12);
        else
            return StringUtils.defaultString(apNo);
    }

    public String getPayTypStr() {
        String payTypStr = "";
        if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(getPayTyp())) {
            payTypStr = ConstantKey.BAAPPBASE_PAYTYP_STR_1;
        }
        else if ((ConstantKey.BAAPPBASE_PAYTYP_2).equals(getPayTyp())) {
            payTypStr = ConstantKey.BAAPPBASE_PAYTYP_STR_2;
        }
        else if ((ConstantKey.BAAPPBASE_PAYTYP_3).equals(getPayTyp())) {
            payTypStr = ConstantKey.BAAPPBASE_PAYTYP_STR_3;
        }
        else if ((ConstantKey.BAAPPBASE_PAYTYP_4).equals(getPayTyp())) {
            payTypStr = ConstantKey.BAAPPBASE_PAYTYP_STR_4;
        }
        else if ((ConstantKey.BAAPPBASE_PAYTYP_5).equals(getPayTyp())) {
            payTypStr = ConstantKey.BAAPPBASE_PAYTYP_STR_5;
        }
        else if ((ConstantKey.BAAPPBASE_PAYTYP_6).equals(getPayTyp())) {
            payTypStr = ConstantKey.BAAPPBASE_PAYTYP_STR_6;
        }
        else if ((ConstantKey.BAAPPBASE_PAYTYP_A).equals(getPayTyp())) {
            payTypStr = ConstantKey.BAAPPBASE_PAYTYP_STR_A;
        }
        return payTypStr;
    }

    // 改匯方式轉換
    public String getAccRelStr() {
        String accRelStr = "";
        if ((ConstantKey.BAAPPBASE_ACCREL_1).equals(getAccRel())) {
            accRelStr = ConstantKey.BAAPPBASE_ACCREL_STR_1;
        }
        else if ((ConstantKey.BAAPPBASE_ACCREL_2).equals(getAccRel())) {
            accRelStr = ConstantKey.BAAPPBASE_ACCREL_STR_2;
        }
        else if ((ConstantKey.BAAPPBASE_ACCREL_3).equals(getAccRel())) {
            accRelStr = ConstantKey.BAAPPBASE_ACCREL_STR_3;
        }
        else if ((ConstantKey.BAAPPBASE_ACCREL_4).equals(getAccRel())) {
            accRelStr = ConstantKey.BAAPPBASE_ACCREL_STR_4;
        }

        return accRelStr;
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

    public String getApItem() {
        return apItem;
    }

    public void setApItem(String apItem) {
        this.apItem = apItem;
    }

    public String getPayKind() {
        return payKind;
    }

    public void setPayKind(String payKind) {
        this.payKind = payKind;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
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

    public String getApUbno() {
        return apUbno;
    }

    public void setApUbno(String apUbno) {
        this.apUbno = apUbno;
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

    public String getEvtSex() {
        return evtSex;
    }

    public void setEvtSex(String evtSex) {
        this.evtSex = evtSex;
    }

    public String getEvtNationTpe() {
        return evtNationTpe;
    }

    public void setEvtNationTpe(String evtNationTpe) {
        this.evtNationTpe = evtNationTpe;
    }

    public String getEvtNationCode() {
        return evtNationCode;
    }

    public void setEvtNationCode(String evtNationCode) {
        this.evtNationCode = evtNationCode;
    }

    public String getEvtIdent() {
        return evtIdent;
    }

    public void setEvtIdent(String evtIdent) {
        this.evtIdent = evtIdent;
    }

    public String getEvtJobDate() {
        return evtJobDate;
    }

    public void setEvtJobDate(String evtJobDate) {
        this.evtJobDate = evtJobDate;
    }

    public String getEvtDate() {
        return evtDate;
    }

    public void setEvtDate(String evtDate) {
        this.evtDate = evtDate;
    }

    public String getEvtDieDate() {
        return evtDieDate;
    }

    public void setEvtDieDate(String evtDieDate) {
        this.evtDieDate = evtDieDate;
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

    public String getBenNationCode() {
        return benNationCode;
    }

    public void setBenNationCode(String benNationCode) {
        this.benNationCode = benNationCode;
    }

    public String getBenEvtRel() {
        return benEvtRel;
    }

    public void setBenEvtRel(String benEvtRel) {
        this.benEvtRel = benEvtRel;
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

    public BigDecimal getMitRate() {
        return mitRate;
    }

    public void setMitRate(BigDecimal mitRate) {
        this.mitRate = mitRate;
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

    public BigDecimal getOnceAmt() {
        return onceAmt;
    }

    public void setOnceAmt(BigDecimal onceAmt) {
        this.onceAmt = onceAmt;
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

    public String getPayBankIdBranchId() {
        return payBankIdBranchId;
    }

    public void setPayBankIdBranchId(String payBankIdBranchId) {
        this.payBankIdBranchId = payBankIdBranchId;
    }

    public String getPagePayKind() {
        return pagePayKind;
    }

    public void setPagePayKind(String pagePayKind) {
        this.pagePayKind = pagePayKind;
    }

    public List<Baapplog> getBaapplogList() {
        return baapplogList;
    }

    public void setBaapplogList(List<Baapplog> baapplogList) {
        this.baapplogList = baapplogList;
    }

    public String getBenSelectResult() {
        return benSelectResult;
    }

    public void setBenSelectResult(String benSelectResult) {
        this.benSelectResult = benSelectResult;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public String getPayCategory() {
        return payCategory;
    }

    public void setPayCategory(String payCategory) {
        this.payCategory = payCategory;
    }

    public String getPageChkStr() {
        return pageChkStr;
    }

    public void setPageChkStr(String pageChkStr) {
        this.pageChkStr = pageChkStr;
    }

    public BigDecimal getOnceAvgAmt() {
        return onceAvgAmt;
    }

    public void setOnceAvgAmt(BigDecimal onceAvgAmt) {
        this.onceAvgAmt = onceAvgAmt;
    }

    public BigDecimal getOnceAplPayAmt() {
        return onceAplPayAmt;
    }

    public void setOnceAplPayAmt(BigDecimal onceAplPayAmt) {
        this.onceAplPayAmt = onceAplPayAmt;
    }

    public String getOncePayMk() {
        return oncePayMk;
    }

    public void setOncePayMk(String oncePayMk) {
        this.oncePayMk = oncePayMk;
    }

    public BigDecimal getOnceOldAmt() {
        return onceOldAmt;
    }

    public void setOnceOldAmt(BigDecimal onceOldAmt) {
        this.onceOldAmt = onceOldAmt;
    }

    public BigDecimal getMustIssueAmt() {
        return mustIssueAmt;
    }

    public void setMustIssueAmt(BigDecimal mustIssueAmt) {
        this.mustIssueAmt = mustIssueAmt;
    }

    public String getOldSeniab() {
        return oldSeniab;
    }

    public void setOldSeniab(String oldSeniab) {
        this.oldSeniab = oldSeniab;
    }

    public String getDupeIdnNoMk() {
        return dupeIdnNoMk;
    }

    public void setDupeIdnNoMk(String dupeIdnNoMk) {
        this.dupeIdnNoMk = dupeIdnNoMk;
    }

    public String getPayCode() {
        if (apNo.substring(0, 1).equals("L"))
            return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L;
        else if (apNo.substring(0, 1).equals("K"))
            return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K;
        else if (apNo.substring(0, 1).equals("S"))
            return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S;
        else
            return "";
    }

    public String getAppDateChineseString() {
        return DateUtil.changeDateType(appDate);
    }

    public String getEvtBrDateChineseString() {
        return DateUtil.changeDateType(evtBrDate);
    }

    public String getBenDieDateChineseString() {
        return DateUtil.changeDateType(benDieDate);
    }

    public String getAppUser() {
        return appUser;
    }

    public void setAppUser(String appUser) {
        this.appUser = appUser;
    }

    public String getBenEvtRelName() {
        if (benEvtRel.equals("1"))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_1;
        else if (benEvtRel.equals("2"))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_2;
        else if (benEvtRel.equals("3"))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_3;
        else if (benEvtRel.equals("4"))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_4;
        else if (benEvtRel.equals("5"))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_5;
        else if (benEvtRel.equals("6"))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_6;
        else if (benEvtRel.equals("7"))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_7;
        else if (benEvtRel.equals("A"))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_A;
        else if (benEvtRel.equals("C"))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_C;
        else if (benEvtRel.equals("E"))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_E;
        else if (benEvtRel.equals("F"))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_F;
        else if (benEvtRel.equals("N"))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_N;
        else if (benEvtRel.equals("Z"))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_Z;
        else if (benEvtRel.equals("O"))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_O;
        else
            return "";
    }

    public BigDecimal getNitrmY() {
        return nitrmY;
    }

    public void setNitrmY(BigDecimal nitrmY) {
        this.nitrmY = nitrmY;
    }

    public BigDecimal getNitrmM() {
        return nitrmM;
    }

    public void setNitrmM(BigDecimal nitrmM) {
        this.nitrmM = nitrmM;
    }

    public BigDecimal getAplPaySeniY() {
        return aplPaySeniY;
    }

    public void setAplPaySeniY(BigDecimal aplPaySeniY) {
        this.aplPaySeniY = aplPaySeniY;
    }

    public BigDecimal getAplPaySeniM() {
        return aplPaySeniM;
    }

    public void setAplPaySeniM(BigDecimal aplPaySeniM) {
        this.aplPaySeniM = aplPaySeniM;
    }

    public BigDecimal getNoldtY() {
        return noldtY;
    }

    public void setNoldtY(BigDecimal noldtY) {
        this.noldtY = noldtY;
    }

    public BigDecimal getNoldtM() {
        return noldtM;
    }

    public void setNoldtM(BigDecimal noldtM) {
        this.noldtM = noldtM;
    }

    public BigDecimal getValseniY() {
        return valseniY;
    }

    public void setValseniY(BigDecimal valseniY) {
        this.valseniY = valseniY;
    }

    public BigDecimal getValseniM() {
        return valseniM;
    }

    public void setValseniM(BigDecimal valseniM) {
        this.valseniM = valseniM;
    }

    public BigDecimal getInsAvgAmt() {
        return insAvgAmt;
    }

    public void setInsAvgAmt(BigDecimal insAvgAmt) {
        this.insAvgAmt = insAvgAmt;
    }

    public String getIssuYmStr() {
        return DateUtility.changeWestYearMonthType(issuYm);
    }

    public String getDabApNo() {
        return dabApNo;
    }

    public void setDabApNo(String dabApNo) {
        this.dabApNo = dabApNo;
    }

    public String getDabApNo1() {
        return dabApNo1;
    }

    public void setDabApNo1(String dabApNo1) {
        this.dabApNo1 = dabApNo1;
    }

    public String getDabApNo2() {
        return dabApNo2;
    }

    public void setDabApNo2(String dabApNo2) {
        this.dabApNo2 = dabApNo2;
    }

    public String getDabApNo3() {
        return dabApNo3;
    }

    public void setDabApNo3(String dabApNo3) {
        this.dabApNo3 = dabApNo3;
    }

    public String getDabApNo4() {
        return dabApNo4;
    }

    public void setDabApNo4(String dabApNo4) {
        this.dabApNo4 = dabApNo4;
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

    public String getCvldtlName() {
        return cvldtlName;
    }

    public void setCvldtlName(String cvldtlName) {
        this.cvldtlName = cvldtlName;
    }

    public String getIsShowCvldtlName() {
        return isShowCvldtlName;
    }

    public void setIsShowCvldtlName(String isShowCvldtlName) {
        this.isShowCvldtlName = isShowCvldtlName;
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

    public String getDelButtonQ4() {
        return delButtonQ4;
    }

    public void setDelButtonQ4(String delButtonQ4) {
        this.delButtonQ4 = delButtonQ4;
    }

    public String getIsShowLoanMk() {
        return isShowLoanMk;
    }

    public void setIsShowLoanMk(String isShowLoanMk) {
        this.isShowLoanMk = isShowLoanMk;
    }

    public String getEvtNameStatus() {
        return evtNameStatus;
    }

    public void setEvtNameStatus(String evtNameStatus) {
        this.evtNameStatus = evtNameStatus;
    }

    public String getEvtAppName() {
        return evtAppName;
    }

    public void setEvtAppName(String evtAppName) {
        this.evtAppName = evtAppName;
    }

    public String getEvtAppIdnNo() {
        return evtAppIdnNo;
    }

    public void setEvtAppIdnNo(String evtAppIdnNo) {
        this.evtAppIdnNo = evtAppIdnNo;
    }

    public String getEvtAppBrDate() {
        return evtAppBrDate;
    }

    public void setEvtAppBrDate(String evtAppBrDate) {
        this.evtAppBrDate = evtAppBrDate;
    }

    public String getAccSeqNoAmt() {
        return accSeqNoAmt;
    }

    public void setAccSeqNoAmt(String accSeqNoAmt) {
        this.accSeqNoAmt = accSeqNoAmt;
    }

    public String getOldAplDpt() {
        return oldAplDpt;
    }

    public void setOldAplDpt(String oldAplDpt) {
        this.oldAplDpt = oldAplDpt;
    }

    public String getIsHeir() {
        return isHeir;
    }

    public void setIsHeir(String isHeir) {
        this.isHeir = isHeir;
    }

    public String getRegetCipbMk() {
        return regetCipbMk;
    }

    public void setRegetCipbMk(String regetCipbMk) {
        this.regetCipbMk = regetCipbMk;
    }

    public String getOriIssuYm() {
        return oriIssuYm;
    }

    public void setOriIssuYm(String oriIssuYm) {
        this.oriIssuYm = oriIssuYm;
    }

    public String getBrChkDate() {
        return brChkDate;
    }

    public void setBrChkDate(String brChkDate) {
        this.brChkDate = brChkDate;
    }

    public BigDecimal getRemitAmt() {
        return remitAmt;
    }

    public void setRemitAmt(BigDecimal remitAmt) {
        this.remitAmt = remitAmt;
    }

    public String getIssuKind() {
        return issuKind;
    }

    public void setIssuKind(String issuKind) {
        this.issuKind = issuKind;
    }

    public String getBrNote() {
        return brNote;
    }

    public void setBrNote(String brNote) {
        this.brNote = brNote;
    }

    public String getHeirSeqNo() {
        return heirSeqNo;
    }

    public void setHeirSeqNo(String heirSeqNo) {
        this.heirSeqNo = heirSeqNo;
    }

    public BigDecimal getAvgNum() {
        return avgNum;
    }

    public void setAvgNum(BigDecimal avgNum) {
        this.avgNum = avgNum;
    }

    public String getProcUser() {
        return procUser;
    }

    public void setProcUser(String procUser) {
        this.procUser = procUser;
    }

    public String getProcDeptId() {
        return procDeptId;
    }

    public void setProcDeptId(String procDeptId) {
        this.procDeptId = procDeptId;
    }

    public String getProcIp() {
        return procIp;
    }

    public void setProcIp(String procIp) {
        this.procIp = procIp;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getIndexCount() {
        return indexCount;
    }

    public void setIndexCount(String indexCount) {
        this.indexCount = indexCount;
    }

    public String getDateCount1() {
        return dateCount1;
    }

    public void setDateCount1(String dateCount1) {
        this.dateCount1 = dateCount1;
    }

    public String getDateCount2() {
        return dateCount2;
    }

    public void setDateCount2(String dateCount2) {
        this.dateCount2 = dateCount2;
    }

    public String getDateCount3() {
        return dateCount3;
    }

    public void setDateCount3(String dateCount3) {
        this.dateCount3 = dateCount3;
    }

    public String getIndexString() {
        return indexString;
    }

    public void setIndexString(String indexString) {
        this.indexString = indexString;
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

    public String getSpecialAcc() {
        return specialAcc;
    }

    public void setSpecialAcc(String specialAcc) {
        this.specialAcc = specialAcc;
    }

    public String getSpeAccDate() {
        return speAccDate;
    }

    public void setSpeAccDate(String speAccDate) {
        this.speAccDate = speAccDate;
    }

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getApnoFm() {
		return apnoFm;
	}

	public void setApnoFm(String apnoFm) {
		this.apnoFm = apnoFm;
	}

}
