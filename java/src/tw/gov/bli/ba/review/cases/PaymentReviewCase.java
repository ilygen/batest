package tw.gov.bli.ba.review.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Baapplog;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.PresentationUtility;

/**
 * Case for 給付審核作業
 *
 * @author Rickychi
 */
public class PaymentReviewCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
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
    private String payEeacc;// 銀行帳號
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
    private String pagePayKind;// 給付別(受理編號第一碼)
    private String evtExpireDate; // 事故者屆齡日期
    private String evtEligibleDate; // 事故者符合日期
    private String evtMissingDate; // 事故者失蹤日期
    private BigDecimal oncPayYm; // 一次給付月數
    private BigDecimal lsInsmAmt; // 投保金額
    private BigDecimal oldRate; // 展延/減額比率
    private String oldRateSdate; // 展延/減額期間(起)
    private String oldRateEdate; // 展延/減額期間(迄)

    private String rptTyp;// 報表種類
    private String rptDate;// 列印日期
    private BigDecimal pageNo;// 頁次
    private BigDecimal tissueAmt;// 合計核定金額
    private BigDecimal taplPayAmt;// 合計實付金額
    private String changeYyp;// 異動狀況

    private String letterTypeMk1;// 交查函註記
    private String letterTypeMk2;// 不給付函註記
    private String letterTypeMk3;// 照會函註記
    private String letterTypeMk4;// 補件通知函註記
    private String letterTypeMk5;// 其他簽函註記

    private String letterTypeMk1Title;// 交查函註記(小黃標)
    private String letterTypeMk2Title;// 不給付函註記(小黃標)
    private String letterTypeMk3Title;// 照會函註記(小黃標)
    private String letterTypeMk4Title;// 補件通知函註記(小黃標)
    private String letterTypeMk5Title;// 其他簽函註記(小黃標)

    private String maxPayYm; // 給付年月起
    private String minPayYm; // 給付年月迄
    private BigDecimal aplPayAmt; // 實付總額
    private BigDecimal adjustAmt; // 調整金額
    private BigDecimal otherAmt; // 扣減
    private String veriSeq; // 版次
    private BigDecimal nitrmY; // 投保年資(X年X月-年)
    private BigDecimal nitrmM; // 投保年資(X年X月-月)
    private BigDecimal itrmY; // 投保年資(X年X日-年)
    private BigDecimal itrmD; // 投保年資(X年X日-日)
    private BigDecimal aplPaySeniY; // 實付年資(年)
    private BigDecimal aplPaySeniM; // 實付年資(月)
    private BigDecimal noldtY; // 老年年資(年)
    private BigDecimal noldtM; // 老年年資(月)
    private BigDecimal valSeniY; // 國保年資(年)
    private BigDecimal valSeniM; // 國保年資(月)
    private BigDecimal insAvgAmt; // 平均薪資
    private String oldAb; // 計算式
    private BigDecimal oldAmt; // 金額
    private BigDecimal payRate;// 匯款匯費

    private BigDecimal diffAmt;// 差額金金額
    private BigDecimal annuAmt;// 累計已領年金金額
    private String closeCauseName;// 結案原因(擇領) - 中文說明

    private String benEvtAppDate;// 繼承人申請日期
    private String benEvtName;// 繼承自受款人姓名
    private String benNationName;// 國籍名稱
    private BigDecimal befIssueAmt;// 核定總額
    private BigDecimal offsetAmt; // 紓困總額
    private BigDecimal supAmt; // 補發金額

    private List<PaymentReviewExtCase> benIssueDataList;// 受款人核定資料
    private List<PaymentReviewExtCase> benChkDataList;// 受款人的編審註記
    private List<PaymentReviewExtCase> otherChkDataList;// 受款人編審註記

    private String paysYm; // 給付年月(起)
    private String payeYm; // 給付年月(迄)
    private String showManchkMkMaster;// 核定資料 人工審核結果Radio(全選)
    private String updateBtnStatus;// 確認按鈕狀態

    private String accptMk; // 電腦審核結果
    private String mtestMk; // 處理註記
    private String mchkTyp; // 月核案件類別
    private String flag1; // 審核FLAG
    private Integer q1;// Q1 for 判斷「人工審核結果」radio button用
    private Integer q2;// Q2 for 判斷「人工審核結果」radio button用

    private String proDate;// 承辦/創收日期
    private String ndomk1;// 函別內容一
    private String ndomk2;// 函別內容二
    private String paramName; // BAPARAM.paramName - 處理函別名稱 for 行政支援查詢 功能
    private String ndomkName1; // BAPANDOMK.ndomkName - 處理註記一名稱 for 行政支援查詢 功能
    private String ndomkName2; // BAPARAM.paramName - 處理註記二名稱 for 行政支援查詢 功能
    private String reliefCause; // 救濟事由
    private String reliefKind; // 救濟種類
    private String reliefStat;// 行政救濟辦理情形

    private BigDecimal issueAmtTotal;// 核定/物價調整金額累加

    private List<Baapplog> baapplogList;// 更正記錄檔

    public List<Baapplog> getBaapplogList() {
        return baapplogList;
    }

    public void setBaapplogList(List<Baapplog> baapplogList) {
        this.baapplogList = baapplogList;
    }

    // 頁面顯示轉換
    // [
    public String getProDateStr() {
        if (StringUtils.isNotBlank(getProDate())) {
            return DateUtility.changeDateType(getProDate());
        }
        else {
            return "";
        }
    }

    public String getBenEvtRelStr() {
        String benEvtRelStr = "";
        if ((ConstantKey.BAAPPBASE_BENEVTREL_1).equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_1;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_2).equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_2;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_3).equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_3;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_4).equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_4;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_5).equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_5;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_6).equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_6;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_7).equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_7;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_A).equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_A;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_C).equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_C;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_E).equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_E;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_F).equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_F;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_N).equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_N;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_Z).equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_Z;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_O).equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_O;
        }
        return benEvtRelStr;
    }

    public String getOldRateDateYMStr() {
        String oldRateDateYMStr = "";
        if (StringUtils.isNotBlank(getOldRateSdate()) && StringUtils.isNotBlank(getOldRateEdate())) {
            int oldRateDateYM = DateUtility.wholeMonthsBetween(getOldRateSdate() + "01", getOldRateEdate() + "01");
            if (oldRateDateYM > 0) {
                oldRateDateYMStr = oldRateDateYM / 12 + "年 " + oldRateDateYM % 12 + "月";
            }
        }
        return oldRateDateYMStr;
    }

    public String getPaysYmStr() {
        if (StringUtils.isNotBlank(getPaysYm()) && getPaysYm().length() == 6) {
            return DateUtility.changeDateType(getPaysYm() + "01").substring(0, 5);
        }
        else {
            return "";
        }
    }

    public String getPayeYmStr() {
        if (StringUtils.isNotBlank(getPayeYm()) && getPayeYm().length() == 6) {
            return DateUtility.changeDateType(getPayeYm() + "01").substring(0, 5);
        }
        else {
            return "";
        }
    }

    public String getApNo1() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH) {
            return getApNo().substring(0, 1);
        }
        else {
            return "";
        }
    }

    public String getApNo2() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH) {
            return getApNo().substring(1, 2);
        }
        else {
            return "";
        }
    }

    public String getApNo3() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH) {
            return getApNo().substring(2, 7);
        }
        else {
            return "";
        }
    }

    public String getApNo4() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH) {
            return getApNo().substring(7, 12);
        }
        else {
            return "";
        }
    }

    public String getBenEvtAppDateStr() {
        if (StringUtils.isNotBlank(getBenEvtAppDate())) {
            return DateUtility.changeDateType(getBenEvtAppDate());
        }
        else {
            return "";
        }
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
            return PresentationUtility.changeDateTypeForDisplay(getEvtBrDate());
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

    public String getEvtDateStr() {
        if (StringUtils.isNotBlank(getEvtDate())) {
            return DateUtility.changeDateType(getEvtDate());
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
            return PresentationUtility.changeDateTypeForDisplay(getBenBrDate());
        }
        else {
            return "";
        }
    }

    public String getBenDieDateStr() {
        if (StringUtils.isNotBlank(getBenDieDate())) {
            return DateUtility.changeDateType(getBenDieDate());
        }
        else {
            return "";
        }
    }

    public String getGrdBrDateStr() {
        if (StringUtils.isNotBlank(getGrdBrDate())) {
            return PresentationUtility.changeDateTypeForDisplay(getGrdBrDate());
        }
        else {
            return "";
        }
    }

    public String getAssignBrDateStr() {
        if (StringUtils.isNotBlank(getAssignBrDate())) {
            return PresentationUtility.changeDateTypeForDisplay(getAssignBrDate());
        }
        else {
            return "";
        }
    }

    public String getIssuYmStr() {
        if (StringUtils.isNotBlank(getIssuYm()) && getIssuYm().length() == 6) {
            return DateUtility.changeDateType(getIssuYm() + "01").substring(0, 5);
        }
        else {
            return "";
        }
    }

    public String getPayYmStr() {
        if (StringUtils.isNotBlank(getPayYm()) && getPayYm().length() == 6) {
            return DateUtility.changeDateType(getPayYm() + "01").substring(0, 5);
        }
        else {
            return "";
        }
    }

    public String getPayDateStr() {
        if (StringUtils.isNotBlank(getPayDate())) {
            return DateUtility.changeDateType(getPayDate());
        }
        else {
            return "";
        }
    }

    public String getPayYmsStr() {
        if (StringUtils.isNotBlank(getPayYms()) && getPayYms().length() == 6) {
            return DateUtility.changeDateType(getPayYms() + "01").substring(0, 5);
        }
        else {
            return "";
        }
    }

    public String getPayYmeStr() {
        if (StringUtils.isNotBlank(getPayYme()) && getPayYme().length() == 6) {
            return DateUtility.changeDateType(getPayYme() + "01").substring(0, 5);
        }
        else {
            return "";
        }
    }

    public String getPauseYmStr() {
        if (StringUtils.isNotBlank(getPauseYm()) && getPauseYm().length() == 6) {
            return DateUtility.changeDateType(getPauseYm() + "01").substring(0, 5);
        }
        else {
            return "";
        }
    }

    public String getUnPauseYmStr() {
        if (StringUtils.isNotBlank(getUnPauseYm()) && getUnPauseYm().length() == 6) {
            return DateUtility.changeDateType(getUnPauseYm() + "01").substring(0, 5);
        }
        else {
            return "";
        }
    }

    public String getChkDateStr() {
        if (StringUtils.isNotBlank(getChkDate())) {
            return DateUtility.changeDateType(getChkDate());
        }
        else {
            return "";
        }
    }

    public String getRechkDateStr() {
        if (StringUtils.isNotBlank(getRechkDate())) {
            return DateUtility.changeDateType(getRechkDate());
        }
        else {
            return "";
        }
    }

    public String getExeDateStr() {
        if (StringUtils.isNotBlank(getExeDate())) {
            return DateUtility.changeDateType(getExeDate());
        }
        else {
            return "";
        }
    }

    public String getArcDateStr() {
        if (StringUtils.isNotBlank(getArcDate())) {
            return DateUtility.changeDateType(getArcDate());
        }
        else {
            return "";
        }
    }

    public String getCloseDateStr() {
        if (StringUtils.isNotBlank(getCloseDate())) {
            return DateUtility.changeDateType(getCloseDate());
        }
        else {
            return "";
        }
    }

    public String getRptDateStr() {
        if (StringUtils.isNotBlank(getRptDate())) {
            return DateUtility.changeDateType(getRptDate());
        }
        else {
            return "";
        }
    }

    public String getMaxPayYmStr() {
        if (StringUtils.isNotBlank(getMaxPayYm()) && getMaxPayYm().length() == 6) {
            return DateUtility.changeDateType(getMaxPayYm() + "01").substring(0, 5);
        }
        else {
            return "";
        }
    }

    public String getMinPayYmStr() {
        if (StringUtils.isNotBlank(getMinPayYm()) && getMinPayYm().length() == 6) {
            return DateUtility.changeDateType(getMinPayYm() + "01").substring(0, 5);
        }
        else {
            return "";
        }
    }

    public String getApNoStrDisplay() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH)
            return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12);
        else
            return "";
    }

    public String getEvtExpireDateStr() {
        if (StringUtils.isNotBlank(getEvtExpireDate())) {
            return DateUtility.changeDateType(getEvtExpireDate());
        }
        else {
            return "";
        }
    }

    public String getEvtEligibleDateStr() {
        if (StringUtils.isNotBlank(getEvtEligibleDate())) {
            return DateUtility.changeDateType(getEvtEligibleDate());
        }
        else {
            return "";
        }
    }

    public String getEvtMissingDateStr() {
        if (StringUtils.isNotBlank(getEvtMissingDate())) {
            return DateUtility.changeDateType(getEvtMissingDate());
        }
        else {
            return "";
        }
    }

    public String getOldRateSdateStr() {
        if (StringUtils.isNotBlank(getOldRateSdate())) {
            return DateUtility.changeWestYearMonthType(getOldRateSdate());
            // return DateUtility.changeDateType(getOldRateSdate());
        }
        else {
            return "";
        }
    }

    public String getOldRateEdateStr() {
        if (StringUtils.isNotBlank(getOldRateEdate())) {
            return DateUtility.changeWestYearMonthType(getOldRateEdate());
            // return DateUtility.changeDateType(getOldRateEdate());
        }
        else {
            return "";
        }
    }

    public String getIdnChkYmStr() {
        if (StringUtils.isNotBlank(getIdnChkYm()) && getIdnChkYm().length() == 6) {
            return DateUtility.changeDateType(getIdnChkYm() + "01").substring(0, 5);
        }
        else {
            return "";
        }
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

    public String getPagePayKind() {
        return pagePayKind;
    }

    public void setPagePayKind(String pagePayKind) {
        this.pagePayKind = pagePayKind;
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

    public BigDecimal getOncPayYm() {
        return oncPayYm;
    }

    public void setOncPayYm(BigDecimal oncPayYm) {
        this.oncPayYm = oncPayYm;
    }

    public BigDecimal getLsInsmAmt() {
        return lsInsmAmt;
    }

    public void setLsInsmAmt(BigDecimal lsInsmAmt) {
        this.lsInsmAmt = lsInsmAmt;
    }

    public BigDecimal getOldRate() {
        return oldRate;
    }

    public void setOldRate(BigDecimal oldRate) {
        this.oldRate = oldRate;
    }

    public String getOldRateSdate() {
        return oldRateSdate;
    }

    public void setOldRateSdate(String oldRateSdate) {
        this.oldRateSdate = oldRateSdate;
    }

    public String getOldRateEdate() {
        return oldRateEdate;
    }

    public void setOldRateEdate(String oldRateEdate) {
        this.oldRateEdate = oldRateEdate;
    }

    public String getRptTyp() {
        return rptTyp;
    }

    public void setRptTyp(String rptTyp) {
        this.rptTyp = rptTyp;
    }

    public String getRptDate() {
        return rptDate;
    }

    public void setRptDate(String rptDate) {
        this.rptDate = rptDate;
    }

    public BigDecimal getPageNo() {
        return pageNo;
    }

    public void setPageNo(BigDecimal pageNo) {
        this.pageNo = pageNo;
    }

    public BigDecimal getTissueAmt() {
        return tissueAmt;
    }

    public void setTissueAmt(BigDecimal tissueAmt) {
        this.tissueAmt = tissueAmt;
    }

    public BigDecimal getTaplPayAmt() {
        return taplPayAmt;
    }

    public void setTaplPayAmt(BigDecimal taplPayAmt) {
        this.taplPayAmt = taplPayAmt;
    }

    public String getChangeYyp() {
        return changeYyp;
    }

    public void setChangeYyp(String changeYyp) {
        this.changeYyp = changeYyp;
    }

    public String getLetterTypeMk1() {
        return letterTypeMk1;
    }

    public void setLetterTypeMk1(String letterTypeMk1) {
        this.letterTypeMk1 = letterTypeMk1;
    }

    public String getLetterTypeMk2() {
        return letterTypeMk2;
    }

    public void setLetterTypeMk2(String letterTypeMk2) {
        this.letterTypeMk2 = letterTypeMk2;
    }

    public String getLetterTypeMk3() {
        return letterTypeMk3;
    }

    public void setLetterTypeMk3(String letterTypeMk3) {
        this.letterTypeMk3 = letterTypeMk3;
    }

    public String getLetterTypeMk4() {
        return letterTypeMk4;
    }

    public void setLetterTypeMk4(String letterTypeMk4) {
        this.letterTypeMk4 = letterTypeMk4;
    }

    public String getLetterTypeMk5() {
        return letterTypeMk5;
    }

    public void setLetterTypeMk5(String letterTypeMk5) {
        this.letterTypeMk5 = letterTypeMk5;
    }

    public String getMaxPayYm() {
        return maxPayYm;
    }

    public void setMaxPayYm(String maxPayYm) {
        this.maxPayYm = maxPayYm;
    }

    public String getMinPayYm() {
        return minPayYm;
    }

    public void setMinPayYm(String minPayYm) {
        this.minPayYm = minPayYm;
    }

    public BigDecimal getAplPayAmt() {
        return aplPayAmt;
    }

    public void setAplPayAmt(BigDecimal aplPayAmt) {
        this.aplPayAmt = aplPayAmt;
    }

    public BigDecimal getAdjustAmt() {
        return adjustAmt;
    }

    public void setAdjustAmt(BigDecimal adjustAmt) {
        this.adjustAmt = adjustAmt;
    }

    public BigDecimal getOtherAmt() {
        return otherAmt;
    }

    public void setOtherAmt(BigDecimal otherAmt) {
        this.otherAmt = otherAmt;
    }

    public String getVeriSeq() {
        return veriSeq;
    }

    public void setVeriSeq(String veriSeq) {
        this.veriSeq = veriSeq;
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

    public BigDecimal getItrmY() {
        return itrmY;
    }

    public void setItrmY(BigDecimal itrmY) {
        this.itrmY = itrmY;
    }

    public BigDecimal getItrmD() {
        return itrmD;
    }

    public void setItrmD(BigDecimal itrmD) {
        this.itrmD = itrmD;
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

    public BigDecimal getValSeniY() {
        return valSeniY;
    }

    public void setValSeniY(BigDecimal valSeniY) {
        this.valSeniY = valSeniY;
    }

    public BigDecimal getValSeniM() {
        return valSeniM;
    }

    public void setValSeniM(BigDecimal valSeniM) {
        this.valSeniM = valSeniM;
    }

    public BigDecimal getInsAvgAmt() {
        return insAvgAmt;
    }

    public void setInsAvgAmt(BigDecimal insAvgAmt) {
        this.insAvgAmt = insAvgAmt;
    }

    public String getOldAb() {
        return oldAb;
    }

    public void setOldAb(String oldAb) {
        this.oldAb = oldAb;
    }

    public BigDecimal getOldAmt() {
        return oldAmt;
    }

    public void setOldAmt(BigDecimal oldAmt) {
        this.oldAmt = oldAmt;
    }

    public BigDecimal getDiffAmt() {
        return diffAmt;
    }

    public void setDiffAmt(BigDecimal diffAmt) {
        this.diffAmt = diffAmt;
    }

    public BigDecimal getAnnuAmt() {
        return annuAmt;
    }

    public void setAnnuAmt(BigDecimal annuAmt) {
        this.annuAmt = annuAmt;
    }

    public String getCloseCauseName() {
        return closeCauseName;
    }

    public void setCloseCauseName(String closeCauseName) {
        this.closeCauseName = closeCauseName;
    }

    public BigDecimal getPayRate() {
        return payRate;
    }

    public void setPayRate(BigDecimal payRate) {
        this.payRate = payRate;
    }

    public List<PaymentReviewExtCase> getBenIssueDataList() {
        return benIssueDataList;
    }

    public void setBenIssueDataList(List<PaymentReviewExtCase> benIssueDataList) {
        this.benIssueDataList = benIssueDataList;
    }

    public List<PaymentReviewExtCase> getBenChkDataList() {
        return benChkDataList;
    }

    public void setBenChkDataList(List<PaymentReviewExtCase> benChkDataList) {
        this.benChkDataList = benChkDataList;
    }

    public String getBenNationName() {
        return benNationName;
    }

    public void setBenNationName(String benNationName) {
        this.benNationName = benNationName;
    }

    public String getLetterTypeMk1Title() {
        return letterTypeMk1Title;
    }

    public void setLetterTypeMk1Title(String letterTypeMk1Title) {
        this.letterTypeMk1Title = letterTypeMk1Title;
    }

    public String getLetterTypeMk2Title() {
        return letterTypeMk2Title;
    }

    public void setLetterTypeMk2Title(String letterTypeMk2Title) {
        this.letterTypeMk2Title = letterTypeMk2Title;
    }

    public String getLetterTypeMk3Title() {
        return letterTypeMk3Title;
    }

    public void setLetterTypeMk3Title(String letterTypeMk3Title) {
        this.letterTypeMk3Title = letterTypeMk3Title;
    }

    public String getLetterTypeMk4Title() {
        return letterTypeMk4Title;
    }

    public void setLetterTypeMk4Title(String letterTypeMk4Title) {
        this.letterTypeMk4Title = letterTypeMk4Title;
    }

    public String getLetterTypeMk5Title() {
        return letterTypeMk5Title;
    }

    public void setLetterTypeMk5Title(String letterTypeMk5Title) {
        this.letterTypeMk5Title = letterTypeMk5Title;
    }

    public String getPaysYm() {
        return paysYm;
    }

    public void setPaysYm(String paysYm) {
        this.paysYm = paysYm;
    }

    public String getPayeYm() {
        return payeYm;
    }

    public void setPayeYm(String payeYm) {
        this.payeYm = payeYm;
    }

    public String getBenEvtAppDate() {
        return benEvtAppDate;
    }

    public void setBenEvtAppDate(String benEvtAppDate) {
        this.benEvtAppDate = benEvtAppDate;
    }

    public String getBenEvtName() {
        return benEvtName;
    }

    public void setBenEvtName(String benEvtName) {
        this.benEvtName = benEvtName;
    }

    public String getShowManchkMkMaster() {
        return showManchkMkMaster;
    }

    public void setShowManchkMkMaster(String showManchkMkMaster) {
        this.showManchkMkMaster = showManchkMkMaster;
    }

    public BigDecimal getBefIssueAmt() {
        return befIssueAmt;
    }

    public void setBefIssueAmt(BigDecimal befIssueAmt) {
        this.befIssueAmt = befIssueAmt;
    }

    public String getAccptMk() {
        return accptMk;
    }

    public void setAccptMk(String accptMk) {
        this.accptMk = accptMk;
    }

    public String getMtestMk() {
        return mtestMk;
    }

    public void setMtestMk(String mtestMk) {
        this.mtestMk = mtestMk;
    }

    public String getMchkTyp() {
        return mchkTyp;
    }

    public void setMchkTyp(String mchkTyp) {
        this.mchkTyp = mchkTyp;
    }

    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public String getUpdateBtnStatus() {
        return updateBtnStatus;
    }

    public void setUpdateBtnStatus(String updateBtnStatus) {
        this.updateBtnStatus = updateBtnStatus;
    }

    public Integer getQ1() {
        return q1;
    }

    public void setQ1(Integer q1) {
        this.q1 = q1;
    }

    public Integer getQ2() {
        return q2;
    }

    public void setQ2(Integer q2) {
        this.q2 = q2;
    }

    public String getProDate() {
        return proDate;
    }

    public void setProDate(String proDate) {
        this.proDate = proDate;
    }

    public String getNdomk1() {
        return ndomk1;
    }

    public void setNdomk1(String ndomk1) {
        this.ndomk1 = ndomk1;
    }

    public String getNdomk2() {
        return ndomk2;
    }

    public void setNdomk2(String ndomk2) {
        this.ndomk2 = ndomk2;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getNdomkName1() {
        return ndomkName1;
    }

    public void setNdomkName1(String ndomkName1) {
        this.ndomkName1 = ndomkName1;
    }

    public String getNdomkName2() {
        return ndomkName2;
    }

    public void setNdomkName2(String ndomkName2) {
        this.ndomkName2 = ndomkName2;
    }

    public String getReliefCause() {
        return reliefCause;
    }

    public void setReliefCause(String reliefCause) {
        this.reliefCause = reliefCause;
    }

    public String getReliefKind() {
        return reliefKind;
    }

    public void setReliefKind(String reliefKind) {
        this.reliefKind = reliefKind;
    }

    public String getReliefStat() {
        return reliefStat;
    }

    public void setReliefStat(String reliefStat) {
        this.reliefStat = reliefStat;
    }

    public BigDecimal getOffsetAmt() {
        return offsetAmt;
    }

    public void setOffsetAmt(BigDecimal offsetAmt) {
        this.offsetAmt = offsetAmt;
    }

    public BigDecimal getSupAmt() {
        return supAmt;
    }

    public void setSupAmt(BigDecimal supAmt) {
        this.supAmt = supAmt;
    }

    public List<PaymentReviewExtCase> getOtherChkDataList() {
        return otherChkDataList;
    }

    public void setOtherChkDataList(List<PaymentReviewExtCase> otherChkDataList) {
        this.otherChkDataList = otherChkDataList;
    }

    public BigDecimal getIssueAmtTotal() {
        return issueAmtTotal;
    }

    public void setIssueAmtTotal(BigDecimal issueAmtTotal) {
        this.issueAmtTotal = issueAmtTotal;
    }
}
