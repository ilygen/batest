package tw.gov.bli.ba.query.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.update.cases.DisabledApplicationDataUpdateCheckFileCase;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.PresentationUtility;

/**
 * Case for 給付查詢作業 明細資料
 * 
 * @author Rickychi
 */
public class PaymentQueryDetailDataCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
    private BigDecimal baappbaseId; // 資料列編號
    private String apNo; // 受理編號
    private String seqNo; // 受款人序
    private String imk; // 保險別
    private String apItem; // 申請項目
    private String payKind; // 給付種類
    private String appDate; // 申請日期
    private String caseTyp; // 案件類別
    private String mapNo; // 相關受理編號
    private String mapRootMk; // 相關受理編號註記
    private String combapMark; // 國勞合併申請註記
    private String caseMk; // 案件註記
    private String procStat; // 處理狀態
    private String acceptMk; // 電腦編審結果
    private String manchkMk; // 人工審核結果
    private String chgNote; // 更正原因
    private String exeStat; // 決行狀況
    private String apUbno; // 申請單位保險證號
    private String apubnock; // 申請單位保險證號檢查碼
    private String lsUbno; // 老年-最後單位保險證號;失能-事故發生單位保險證號
    private String lsUbnoCk; // 老年-最後單位保險證號檢查碼;失能-事故發生單位保險證號檢查碼
    private String evtIds; // 事故者社福識別碼
    private String evtIdnNo; // 事故者身分證號
    private String evtName; // 事故者姓名
    private String evtBrDate; // 事故者出生日期
    private String evtSex; // 事故者性別
    private String evtNationTpe; // 事故者國籍別
    private String evtNationCode; // 事故者國籍
    private String evtNationName;// 事故者國籍名稱
    private String evtIdent; // 事故者身分別
    private String evtJobDate; // 老年-事故日期;失能-診斷失能日期
    private String evtDate; // 退保日期
    private String evtDieDate; // 事故者死亡日期
    private String evtAge; // 事故者申請時年齡
    private String benIds; // 受益人社福識別碼
    private String benIdnNo; // 受益人身分證號
    private String benName; // 受益人姓名
    private String benBrDate; // 受益人出生日期
    private String benSex; // 受益人性別
    private String benNationTyp; // 受益人國籍別
    private String benNationCode; // 受益人國籍
    private String benNationName;// 受款人國籍名稱
    private String benEvtRel; // 關係
    private String benDieDate; // 受益人死亡日期
    private String benPayMk; // 受益人領取狀態註記
    private String idnChkYm; // 身分查核年月
    private String idnChkNote; // 身分查核註記
    private String tel1; // 電話1
    private String tel2; // 電話2
    private String commTyp; // 通訊地址別
    private String commZip; // 通訊郵遞區號
    private String commAddr; // 通訊地址
    private String payTyp; // 給付方式
    private String bankName; // 金融機構名稱
    private String payBankId; // 金融機構總代號
    private String branchId; // 分支代號
    private String payEeacc; // 銀行帳號
    private String accIdn; // 戶名IDN
    private String accName; // 戶名
    private String accRel; // 戶名與受益人關係
    private BigDecimal mitRate; // 匯款匯費
    private String grdIdnNo; // 法定代理人身分證號
    private String grdName; // 法定代理人姓名
    private String grdBrDate; // 法定代理人出生日期
    private String assignIdnNo; // 受委託人身分證號
    private String assignName; // 受委託人姓名
    private String assignBrDate; // 受委託人出生日期
    private String issuYm; // 核定年月
    private String payYm; // 給付年月
    private BigDecimal issueAmt; // 核定金額
    private String oncePayMk; // 一次給付符合註記
    private String oldSeniab; // 一次請領之年資採計方式
    private BigDecimal mustIssueAmt; // 可領金額
    private String payDate; // 首次核定日期
    private String payYms; // 首次發放起月
    private String payYme; // 首次發放迄月
    private String pauseYm; // 暫停續發年月
    private String unPauseYm; // 恢復續發年月
    private String notifyForm; // 核定通知書格式
    private String loanMk; // 不須抵銷紓困貸款註記
    private String mexcLvl; // 應決行層級
    private String realexcLvl; // 實際決行層級
    private String chkDate; // 審核日期
    private String chkMan; // 審核人員
    private String rechkDate; // 複核日期
    private String rechkMan; // 複核人員
    private String exeDate; // 決行日期
    private String exeMan; // 決行人員
    private String arcDate; // 歸檔日期
    private String arcPg; // 歸檔頁次
    private String closeDate; // 結案日期
    private String closeCause; // 結案原因(中文)
    private String closeCauseCode; // 結案原因(代碼)
    private String promoteUser; // 承辦者代號
    private String crtUser; // 身分證重號註記
    private String crtTime; // 新增者代號
    private String updUser; // 新增日期時間
    private String updTime; // 異動者代號
    private String dupeIdnNoMk; // 異動日期時間
    private String evtExpireDate; // 事故者屆齡日期
    private String evtEligibleDate; // 事故者符合日期
    private String evtMissingDate; // 事故者失蹤日期
    private BigDecimal lsInsmAmt; // 投保金額
    private String lsChkMk; // 勞貸戶註記
    private BigDecimal lsCount; // 勞貸戶筆數
    private String lnChkMk; // 紓困貸款呆帳債務人註記
    private String dabApNo; // 已領其他年金受理編號
    private BigDecimal dabAnnuAmt; // 老年:失能年金已領金額;失能:老年年金已領金額
    private String reSeniMk; // 保留年資
    private String offInsurDate; // 轉公保日期
    private String lawRetireDate; // 依法退休日期
    private String evtAppName; // 事故者申請時姓名
    private BigDecimal cutAmt; // 老年:扣減/補償總金額;失能:應扣失能金額
    private String accSeqNo; // 被共同具領之受款人員序號
    private String benMarrMk; // 受益人婚姻狀況
    private String chgMk; // 更正註記
    private BigDecimal payAmts; // 首次核定總金額
    private String evtAppIdnNo; // 事故者申請時身分證號
    private String evtAppBrDate; // 事故者申請時出生日期
    private String oldAplDpt; // 申請代算單位
    private String choiceYm; // 擇領起月
    private String pagePayKind; // 給付別
    private BigDecimal annuAmt; // 老年:老年年金已領金額;失能:失能年金已領金額
    private String maxPayYm; // 給付年月(迄)
    private String minPayYm; // 給付年月(迄)
    private BigDecimal befIssueAmt; // 核定總額
    private BigDecimal aplPayAmt; // 實付總額
    private String veriSeq; // 版次
    private String mchkTyp; // 月核案件類別
    private BigDecimal nitrmY; // 老年-投保年資(年-年金制);失能-勞保投保(年-年金制)
    private BigDecimal nitrmM; // 老年-投保年資(月-年金制);失能-勞保投保(月-年金制)
    private BigDecimal itrmY; // 老年-投保年資(年);失能-勞保投保(年)
    private BigDecimal itrmD; // 老年-投保年資(日);失能-勞保投保(日)
    private BigDecimal aplPaySeniY; // 老年-實付年資(年);失能-實發年資(日)
    private BigDecimal aplPaySeniM; // 老年-實付年資(月);失能-實發年資(日)
    private BigDecimal noldtY; // 老年年資(年)
    private BigDecimal noldtM; // 老年年資(月)
    private BigDecimal valSeniY; // 國保已繳年資(年)
    private BigDecimal valSeniM; // 國保已繳年資(月)
    private BigDecimal insAvgAmt; // 平均薪資
    private String oldAb; // 第一式/第二式
    private BigDecimal oldAmt; // 老年-第一式式金額/第二式式金額(擇領)
    private BigDecimal oldRate; // 老年-展延/減額比率;失能-加計比率(加發眷屬補助比率)
    private String oldRateSdate; // 展延/減額期間(起)
    private String oldRateEdate; // 展延/減額期間(迄)
    private BigDecimal compenAmt; // 老年-本次代扣補償金額;失能-當月扣除失能
    private BigDecimal lecomAmt; // 老年-已代扣補償金額;失能-已扣失能金額
    private BigDecimal recomAmt; // 老年-未扣補償金金額;失能-未扣失能金額
    private BigDecimal oldaAmt; // 老年-第一式式金額;失能-勞保計算;遺屬-基本月給付金額
    private BigDecimal oldbAmt; // 老年-第二式式金額;失能-給付金額;遺屬-勞保給付金額
    private BigDecimal oldExtraRate; // 遺屬展延/減額比率
    private String interValMonth;// 間隔月份
    private BigDecimal inheritorAmt; // 計息存儲金額

    private String cpiAdjYm; // 物價調整年度
    private BigDecimal cpi; // 物價調整指數
    private BigDecimal benAmt; // 受款人數
    private BigDecimal issuYmAmt; // 累計已領年金期數
    private BigDecimal adjustAmt; // 調整金額
    private BigDecimal otherAmt; // 扣減
    private String empExt;// 承辦人分機號碼

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

    private String issuPayYm;// 編審核定年月
    private Integer chkFileDataSize;// 資料筆數
    private List<PaymentQueryChkFileDataCase> chkFileDataList;// 編審註記資料
    private List<PaymentQueryDetailDataCase> otherChkDataList;// 編審註記資料

    private String oldApNo;// 遺屬年金 - 老年年金受理編號
    private BigDecimal oldAnnuAmt; // 遺屬年金 - 老年年金已領金額
    private String disApNo;// 遺屬年金 - 失能年金受理編號
    private BigDecimal disAnnuAmt; // 遺屬年金 - 失能年金已領金額

    private String bmApNo; // 受理編號(差額金受理編號)
    private BigDecimal bmChkAmt; // 核定金額(差額金金額)
    private String bmPayDte; // 核付日期(差額金核付日期)
    private String comnpMk; // 併計國保年資
    private String monNotifyingMk; // 寄發月通知表

    private String specialAcc;// 專戶

    private BigDecimal basicAmt;// 38案 基本金額
    private String secrecy; // 是否保密
    private BigDecimal marginAmt;// 老年差額金

    private String adWkMk; // 給付延伸主檔 (BAAPPEXPAND) - 加職註記
    private String nlWkMk; // 給付延伸主檔 (BAAPPEXPAND) - 普職註記

    private String rmp_Name; // 原住民羅馬拼音
    private String sysCode;// 系統類別
    private String apnoFm;// 來源受理編號

    // BACHKFILE - 編審註記資料
    private List<DisabledApplicationDataUpdateCheckFileCase> checkFileList;

    public String getComnpMkStr() {
        if (StringUtils.equals(comnpMk, "Y")) {
            return "Y-同意併計";
        }
        else if (StringUtils.equals(comnpMk, "N")) {
            return "N-逕不併計";
        }
        else if (StringUtils.equals(comnpMk, "X")) {
            return "X-同意不併計";
        }
        else {
            return "";
        }
    }

    public String getMonNotifyingMkStr() {
        if (StringUtils.equals(monNotifyingMk, "Y")) {
            return "Y-寄發";
        }
        else if (StringUtils.equals(monNotifyingMk, "N")) {
            return "N-不寄發";
        }
        else if (StringUtils.equals(monNotifyingMk, null)) {
            return "N-不寄發";
        }
        else {
            return "";
        }
    }

    public String getNlWkMkStr() {
        if (StringUtils.equals(nlWkMk, "1")) {
            return "1-普通";
        }
        else if (StringUtils.equals(nlWkMk, "2")) {
            return "2-職災";
        }
        else {
            return "";
        }
    }

    public String getAdWkMkStr() {
        if (StringUtils.equals(adWkMk, "1")) {
            return "1-一般職災";
        }
        else if (StringUtils.equals(adWkMk, "2")) {
            return "2-加職職災";
        }
        else {
            return "";
        }
    }

    // 頁面顯示轉換
    // [
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

    public BigDecimal getOldRateAmt() {
        if (StringUtils.equals(payKind, "38")) {

            oldbAmt = (oldbAmt == null) ? new BigDecimal(0) : oldbAmt;
            oldRate = (oldRate == null) ? new BigDecimal(0) : oldRate;
            basicAmt = (basicAmt == null) ? new BigDecimal(0) : basicAmt;

            if (oldbAmt.intValue() >= basicAmt.intValue()) {
                return oldbAmt.multiply(oldRate.divide(new BigDecimal(100))).setScale(0, BigDecimal.ROUND_HALF_UP);
            }
            else {
                return basicAmt.multiply(oldRate.divide(new BigDecimal(100))).setScale(0, BigDecimal.ROUND_HALF_UP);
            }
        }
        else {
            BigDecimal oldbAmt = new BigDecimal(0);
            BigDecimal oldRate = new BigDecimal(0);
            if (getOldbAmt() != null) {
                oldbAmt = getOldbAmt();
            }
            if (getOldRate() != null) {
                oldRate = getOldRate();
            }
            return oldbAmt.multiply(oldRate.divide(new BigDecimal(100))).setScale(0, BigDecimal.ROUND_HALF_UP);
        }
    }

    public String getChoiceYmStr() {
        if (StringUtils.isNotBlank(getChoiceYm())) {
            return DateUtility.changeWestYearMonthType(getChoiceYm());
        }
        else {
            return getChoiceYm();
        }
    }

    // 計算 累計已領年金金額 失能年金給付查詢
    public BigDecimal getCalAnnuAmt() {
        BigDecimal calAnnuAmt = new BigDecimal(0);
        if (getAnnuAmt() != null) {
            calAnnuAmt = calAnnuAmt.add(getAnnuAmt());
        }
        if (getDabAnnuAmt() != null) {
            calAnnuAmt = calAnnuAmt.add(getDabAnnuAmt());
        }
        return calAnnuAmt;
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

    public String getProcStatStr() {
        String tmpCaseTypStr = "";
        String tmpProcStatStr = "";

        if (StringUtils.isNotBlank(getProcStat())) {
            String tmpCaseTyp = getCaseTyp();
            String tmpProcStat = getProcStat();
            // 轉換 資料別
            if ((ConstantKey.BAAPPBASE_CASETYP_1).equals(tmpCaseTyp)) {
                tmpCaseTypStr = ConstantKey.BAAPPBASE_CASETYP_STR_1;
            }
            else if ((ConstantKey.BAAPPBASE_CASETYP_2).equals(tmpCaseTyp)) {
                tmpCaseTypStr = ConstantKey.BAAPPBASE_CASETYP_STR_2;
            }
            else if ((ConstantKey.BAAPPBASE_CASETYP_3).equals(tmpCaseTyp)) {
                tmpCaseTypStr = ConstantKey.BAAPPBASE_CASETYP_STR_3;
            }
            else if ((ConstantKey.BAAPPBASE_CASETYP_4).equals(tmpCaseTyp)) {
                tmpCaseTypStr = ConstantKey.BAAPPBASE_CASETYP_STR_4;
            }
            else if ((ConstantKey.BAAPPBASE_CASETYP_5).equals(tmpCaseTyp)) {
                tmpCaseTypStr = ConstantKey.BAAPPBASE_CASETYP_STR_5;
            }
            else if ((ConstantKey.BAAPPBASE_CASETYP_6).equals(tmpCaseTyp)) {
                tmpCaseTypStr = ConstantKey.BAAPPBASE_CASETYP_STR_6;
            }
            // 轉換 處理狀態
            if ((ConstantKey.BAAPPBASE_PROCSTAT_00).equals(tmpProcStat)) {
                tmpProcStatStr = ConstantKey.BAAPPBASE_PROCSTAT_STR_00;
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_01).equals(tmpProcStat)) {
                tmpProcStatStr = ConstantKey.BAAPPBASE_PROCSTAT_STR_01;
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_10).equals(tmpProcStat)) {
                tmpProcStatStr = ConstantKey.BAAPPBASE_PROCSTAT_STR_10;
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_11).equals(tmpProcStat)) {
                tmpProcStatStr = ConstantKey.BAAPPBASE_PROCSTAT_STR_11;
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_12).equals(tmpProcStat)) {
                tmpProcStatStr = ConstantKey.BAAPPBASE_PROCSTAT_STR_12;
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_13).equals(tmpProcStat)) {
                tmpProcStatStr = ConstantKey.BAAPPBASE_PROCSTAT_STR_13;
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_20).equals(tmpProcStat)) {
                tmpProcStatStr = ConstantKey.BAAPPBASE_PROCSTAT_STR_20;
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_30).equals(tmpProcStat)) {
                tmpProcStatStr = ConstantKey.BAAPPBASE_PROCSTAT_STR_30;
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_40).equals(tmpProcStat)) {
                tmpProcStatStr = ConstantKey.BAAPPBASE_PROCSTAT_STR_40;
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_50).equals(tmpProcStat)) {
                tmpProcStatStr = ConstantKey.BAAPPBASE_PROCSTAT_STR_50;
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_90).equals(tmpProcStat)) {
                tmpProcStatStr = ConstantKey.BAAPPBASE_PROCSTAT_STR_90;
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_99).equals(tmpProcStat)) {
                tmpProcStatStr = ConstantKey.BAAPPBASE_PROCSTAT_STR_99;
            }
        }
        return tmpCaseTypStr + "-" + tmpProcStatStr;
    }

    public String getPayKindStr() {
        String payKindStr = "";
        if (StringUtils.isNotBlank(getPayKind())) {
            // 轉換 給付種類
            if ((ConstantKey.BAAPPBASE_PAYKIND_35).equals(getPayKind())) {
                payKindStr = ConstantKey.BAAPPBASE_PAYKIND_STR_35;
            }
            else if ((ConstantKey.BAAPPBASE_PAYKIND_36).equals(getPayKind())) {
                payKindStr = ConstantKey.BAAPPBASE_PAYKIND_STR_36;
            }
            else if ((ConstantKey.BAAPPBASE_PAYKIND_37).equals(getPayKind())) {
                payKindStr = ConstantKey.BAAPPBASE_PAYKIND_STR_37;
            }
            else if ((ConstantKey.BAAPPBASE_PAYKIND_38).equals(getPayKind())) {
                payKindStr = ConstantKey.BAAPPBASE_PAYKIND_STR_38;
            }
            else if ((ConstantKey.BAAPPBASE_PAYKIND_39).equals(getPayKind())) {
                payKindStr = ConstantKey.BAAPPBASE_PAYKIND_STR_39;
            }
            else if ((ConstantKey.BAAPPBASE_PAYKIND_45).equals(getPayKind())) {
                payKindStr = ConstantKey.BAAPPBASE_PAYKIND_STR_45;
            }
            else if ((ConstantKey.BAAPPBASE_PAYKIND_48).equals(getPayKind())) {
                payKindStr = ConstantKey.BAAPPBASE_PAYKIND_STR_48;
            }
            else if ((ConstantKey.BAAPPBASE_PAYKIND_49).equals(getPayKind())) {
                payKindStr = ConstantKey.BAAPPBASE_PAYKIND_STR_49;
            }
            else if ((ConstantKey.BAAPPBASE_PAYKIND_55).equals(getPayKind())) {
                payKindStr = ConstantKey.BAAPPBASE_PAYKIND_STR_55;
            }
            else if ((ConstantKey.BAAPPBASE_PAYKIND_56).equals(getPayKind())) {
                payKindStr = ConstantKey.BAAPPBASE_PAYKIND_STR_56;
            }
            else if ((ConstantKey.BAAPPBASE_PAYKIND_57).equals(getPayKind())) {
                payKindStr = ConstantKey.BAAPPBASE_PAYKIND_STR_57;
            }
            else if ((ConstantKey.BAAPPBASE_PAYKIND_58).equals(getPayKind())) {
                payKindStr = ConstantKey.BAAPPBASE_PAYKIND_STR_58;
            }
            else if ((ConstantKey.BAAPPBASE_PAYKIND_59).equals(getPayKind())) {
                payKindStr = ConstantKey.BAAPPBASE_PAYKIND_STR_59;
            }
        }
        return payKindStr;
    }

    public String getPagePayKindStr() {
        String pagePayKindStr = "";
        if (StringUtils.isNotBlank(getPayKind())) {
            // 轉換 給付別
            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(getPagePayKind())) {
                pagePayKindStr = ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L;
            }
            else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(getPagePayKind())) {
                pagePayKindStr = ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K;
            }
            else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(getPagePayKind())) {
                pagePayKindStr = ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S;
            }
        }
        return pagePayKindStr;
    }

    public String getEvtSexStr() {
        String evtSexStr = "";
        if (StringUtils.isNotBlank(getEvtSex())) {
            // 轉換 事故者性別
            if ((ConstantKey.BAAPPBASE_SEX_1).equals(getEvtSex())) {
                evtSexStr = ConstantKey.BAAPPBASE_SEX_STR_1;
            }
            else if ((ConstantKey.BAAPPBASE_SEX_2).equals(getEvtSex())) {
                evtSexStr = ConstantKey.BAAPPBASE_SEX_STR_2;
            }
        }
        return evtSexStr;
    }

    public String getAcceptMkStr() {
        String acceptMkStr = ConstantKey.PAYMENT_REVIEW_ACCEPTMK_CODESTR_OTHER;
        if (StringUtils.isNotBlank(getAcceptMk())) {
            // 轉換 電腦審核結果
            if ((ConstantKey.PAYMENT_REVIEW_ACCEPTMK_Y).equals(getAcceptMk())) {
                acceptMkStr = ConstantKey.PAYMENT_REVIEW_ACCEPTMK_CODESTR_Y;
            }
            else if ((ConstantKey.PAYMENT_REVIEW_ACCEPTMK_N).equals(getAcceptMk())) {
                acceptMkStr = ConstantKey.PAYMENT_REVIEW_ACCEPTMK_CODESTR_N;
            }
        }
        return acceptMkStr;
    }

    public String getManchkMkStr() {
        String manchkMkStr = ConstantKey.PAYMENT_REVIEW_ACCEPTMK_CODESTR_OTHER;
        if (StringUtils.isNotBlank(getManchkMk())) {
            // 轉換 人工審核結果
            if ((ConstantKey.PAYMENT_REVIEW_MANCHKMK_Y).equals(getManchkMk())) {
                manchkMkStr = ConstantKey.PAYMENT_REVIEW_MANCHKMK_CODESTR_Y;
            }
            else if ((ConstantKey.PAYMENT_REVIEW_MANCHKMK_N).equals(getManchkMk())) {
                manchkMkStr = ConstantKey.PAYMENT_REVIEW_MANCHKMK_CODESTR_N;
            }
        }
        return manchkMkStr;
    }

    public String getApItemStr() {
        String apItemStr = "";
        if (StringUtils.isNotBlank(getApItem())) {
            // 轉換 申請項目
            if ((ConstantKey.BAAPPBASE_APITEM_1).equals(getApItem())) {
                apItemStr = ConstantKey.BAAPPBASE_APITEM_STR_1;
            }
            else if ((ConstantKey.BAAPPBASE_APITEM_2).equals(getApItem())) {
                apItemStr = ConstantKey.BAAPPBASE_APITEM_STR_2;
            }
            else if ((ConstantKey.BAAPPBASE_APITEM_3).equals(getApItem())) {
                apItemStr = ConstantKey.BAAPPBASE_APITEM_STR_3;
            }
            else if ((ConstantKey.BAAPPBASE_APITEM_4).equals(getApItem())) {
                apItemStr = ConstantKey.BAAPPBASE_APITEM_STR_4;
            }
            else if ((ConstantKey.BAAPPBASE_APITEM_5).equals(getApItem())) {
                apItemStr = ConstantKey.BAAPPBASE_APITEM_STR_5;
            }
            else if ((ConstantKey.BAAPPBASE_APITEM_7).equals(getApItem())) {
                apItemStr = ConstantKey.BAAPPBASE_APITEM_STR_7;
            }
            else if ((ConstantKey.BAAPPBASE_APITEM_8).equals(getApItem())) {
                apItemStr = ConstantKey.BAAPPBASE_APITEM_STR_8;
            }
            else if ((ConstantKey.BAAPPBASE_APITEM_9).equals(getApItem())) {
                apItemStr = ConstantKey.BAAPPBASE_APITEM_STR_9;
            }
            else if ((ConstantKey.BAAPPBASE_APITEM_0).equals(getApItem())) {
                apItemStr = ConstantKey.BAAPPBASE_APITEM_STR_0;
            }
        }
        return apItemStr;
    }

    public String getOldAbStr() {
        String oldAbStr = "";
        if (StringUtils.isNotBlank(getOldAb())) {
            // 轉換 計算式
            if ((ConstantKey.BADAPR_OLDAB_1).equals(getOldAb())) {
                oldAbStr = ConstantKey.BADAPR_OLDAB_STR_1;
            }
            else if ((ConstantKey.BADAPR_OLDAB_2).equals(getOldAb())) {
                oldAbStr = ConstantKey.BADAPR_OLDAB_STR_2;
            }
        }
        return oldAbStr;
    }

    /**
     * 編審註記資料 是否有 3M 註記
     * 
     * @return
     */
    public boolean isContainCheckMark3M() {
        if (checkFileList != null && checkFileList.size() > 0) {
            for (DisabledApplicationDataUpdateCheckFileCase checkFileData : checkFileList) {
                if (StringUtils.equalsIgnoreCase(checkFileData.getChkFileCode(), "3M"))
                    return true;
            }
        }

        return false;
    }

    /**
     * 加入一筆 編審註記資料 到 checkFileList
     * 
     * @param checkFileData
     */
    public void addCheckFileData(DisabledApplicationDataUpdateCheckFileCase checkFileData) {
        if (checkFileList == null)
            checkFileList = new ArrayList<DisabledApplicationDataUpdateCheckFileCase>();

        checkFileList.add(checkFileData);
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

    public String getApNoStrDisplay() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH)
            return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12);
        else
            return "";
    }

    public String getMapNoStrDisplay() {
        if (StringUtils.isNotBlank(getMapNo()) && getMapNo().length() == ConstantKey.APNO_LENGTH)
            return getMapNo().substring(0, 1) + "-" + getMapNo().substring(1, 2) + "-" + getMapNo().substring(2, 7) + "-" + getMapNo().substring(7, 12);
        else
            return "";
    }

    public String getDabApNoStrDisplay() {
        if (StringUtils.isNotBlank(getDabApNo()) && getDabApNo().length() == ConstantKey.APNO_LENGTH)
            return getDabApNo().substring(0, 1) + "-" + getDabApNo().substring(1, 2) + "-" + getDabApNo().substring(2, 7) + "-" + getDabApNo().substring(7, 12);
        else
            return "";
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
            return DateUtility.changeDateType(getBenBrDate());
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
            return DateUtility.changeDateType(getGrdBrDate());
        }
        else {
            return "";
        }
    }

    public String getAssignBrDateStr() {
        if (StringUtils.isNotBlank(getAssignBrDate())) {
            return DateUtility.changeDateType(getAssignBrDate());
        }
        else {
            return "";
        }
    }

    public String getIssuYmStr() {
        if (StringUtils.isNotBlank(getIssuYm()) && getIssuYm().length() == 6) {
            return DateUtility.changeWestYearMonthType(getIssuYm());
        }
        else {
            return getIssuYm();
        }
    }

    public String getPayYmStr() {
        if (StringUtils.isNotBlank(getPayYm()) && getPayYm().length() == 6) {
            return DateUtility.changeWestYearMonthType(getPayYm());
        }
        else {
            return getPayYm();
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
            return DateUtility.changeWestYearMonthType(getPayYms());
        }
        else {
            return "";
        }
    }

    public String getPayYmeStr() {
        if (StringUtils.isNotBlank(getPayYme()) && getPayYme().length() == 6) {
            return DateUtility.changeWestYearMonthType(getPayYme());
        }
        else {
            return "";
        }
    }

    public String getPauseYmStr() {
        if (StringUtils.isNotBlank(getPauseYm()) && getPauseYm().length() == 6) {
            return DateUtility.changeWestYearMonthType(getPauseYm());
        }
        else {
            return "";
        }
    }

    public String getUnPauseYmStr() {
        if (StringUtils.isNotBlank(getUnPauseYm()) && getUnPauseYm().length() == 6) {
            return DateUtility.changeWestYearMonthType(getUnPauseYm());
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

    public String getMaxPayYmStr() {
        if (StringUtils.isNotBlank(getMaxPayYm()) && getMaxPayYm().length() == 6) {
            return DateUtility.changeWestYearMonthType(getMaxPayYm());
        }
        else {
            return "";
        }
    }

    public String getMinPayYmStr() {
        if (StringUtils.isNotBlank(getMinPayYm()) && getMinPayYm().length() == 6) {
            return DateUtility.changeWestYearMonthType(getMinPayYm());
        }
        else {
            return "";
        }
    }

    public String getCpiAdjYmStr() {
        if (StringUtils.isNotBlank(getCpiAdjYm()) && getCpiAdjYm().length() == 4) {
            return DateUtility.changeDateType(getCpiAdjYm() + "0101").substring(0, 3);
        }
        else {
            return "";
        }
    }

    public String getOldRateSdateStr() {
        if (StringUtils.isNotBlank(getOldRateSdate()) && getOldRateSdate().length() == 6) {
            return DateUtility.changeWestYearMonthType(getOldRateSdate());
        }
        else {
            return "";
        }
    }

    public String getOldRateEdateStr() {
        if (StringUtils.isNotBlank(getOldRateEdate()) && getOldRateEdate().length() == 6) {
            return DateUtility.changeWestYearMonthType(getOldRateEdate());
        }
        else {
            return "";
        }
    }

    // ]

    public BigDecimal getBaappbaseId() {
        return baappbaseId;
    }

    public void setBaappbaseId(BigDecimal baappbaseId) {
        this.baappbaseId = baappbaseId;
    }

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getPagePayKind() {
        return pagePayKind;
    }

    public void setPagePayKind(String pagePayKind) {
        this.pagePayKind = pagePayKind;
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

    public String getNotifyForm() {
        return notifyForm;
    }

    public void setNotifyForm(String notifyForm) {
        this.notifyForm = notifyForm;
    }

    public String getApItem() {
        return apItem;
    }

    public void setApItem(String apItem) {
        this.apItem = apItem;
    }

    public String getCombapMark() {
        return combapMark;
    }

    public void setCombapMark(String combapMark) {
        this.combapMark = combapMark;
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

    public String getEvtDieDate() {
        return evtDieDate;
    }

    public void setEvtDieDate(String evtDieDate) {
        this.evtDieDate = evtDieDate;
    }

    public String getApUbno() {
        return apUbno;
    }

    public void setApUbno(String apUbno) {
        this.apUbno = apUbno;
    }

    public String getLsUbno() {
        return lsUbno;
    }

    public void setLsUbno(String lsUbno) {
        this.lsUbno = lsUbno;
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

    public String getCloseCauseCode() {
        return closeCauseCode;
    }

    public void setCloseCauseCode(String closeCauseCode) {
        this.closeCauseCode = closeCauseCode;
    }

    public String getPromoteUser() {
        return promoteUser;
    }

    public void setPromoteUser(String promoteUser) {
        this.promoteUser = promoteUser;
    }

    public String getPayYms() {
        return payYms;
    }

    public void setPayYms(String payYms) {
        this.payYms = payYms;
    }

    public BigDecimal getCompenAmt() {
        return compenAmt;
    }

    public void setCompenAmt(BigDecimal compenAmt) {
        this.compenAmt = compenAmt;
    }

    public String getCpiAdjYm() {
        return cpiAdjYm;
    }

    public void setCpiAdjYm(String cpiAdjYm) {
        this.cpiAdjYm = cpiAdjYm;
    }

    public BigDecimal getCpi() {
        return cpi;
    }

    public void setCpi(BigDecimal cpi) {
        this.cpi = cpi;
    }

    public BigDecimal getBenAmt() {
        return benAmt;
    }

    public void setBenAmt(BigDecimal benAmt) {
        this.benAmt = benAmt;
    }

    public BigDecimal getIssuYmAmt() {
        return issuYmAmt;
    }

    public void setIssuYmAmt(BigDecimal issuYmAmt) {
        this.issuYmAmt = issuYmAmt;
    }

    public String getOldAb() {
        return oldAb;
    }

    public void setOldAb(String oldAb) {
        this.oldAb = oldAb;
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

    public BigDecimal getAnnuAmt() {
        return annuAmt;
    }

    public void setAnnuAmt(BigDecimal annuAmt) {
        this.annuAmt = annuAmt;
    }

    public String getChgNote() {
        return chgNote;
    }

    public void setChgNote(String chgNote) {
        this.chgNote = chgNote;
    }

    public String getEmpExt() {
        return empExt;
    }

    public void setEmpExt(String empExt) {
        this.empExt = empExt;
    }

    public String getPayKind() {
        return payKind;
    }

    public void setPayKind(String payKind) {
        this.payKind = payKind;
    }

    public String getEvtIds() {
        return evtIds;
    }

    public void setEvtIds(String evtIds) {
        this.evtIds = evtIds;
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

    public String getLsUbnoCk() {
        return lsUbnoCk;
    }

    public void setLsUbnoCk(String lsUbnoCk) {
        this.lsUbnoCk = lsUbnoCk;
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

    public String getAccSeqNo() {
        return accSeqNo;
    }

    public void setAccSeqNo(String accSeqNo) {
        this.accSeqNo = accSeqNo;
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

    public BigDecimal getOldAmt() {
        return oldAmt;
    }

    public void setOldAmt(BigDecimal oldAmt) {
        this.oldAmt = oldAmt;
    }

    public BigDecimal getPayAmts() {
        return payAmts;
    }

    public void setPayAmts(BigDecimal payAmts) {
        this.payAmts = payAmts;
    }

    public BigDecimal getBefIssueAmt() {
        return befIssueAmt;
    }

    public void setBefIssueAmt(BigDecimal befIssueAmt) {
        this.befIssueAmt = befIssueAmt;
    }

    public String getIssuPayYm() {
        return issuPayYm;
    }

    public void setIssuPayYm(String issuPayYm) {
        this.issuPayYm = issuPayYm;
    }

    public Integer getChkFileDataSize() {
        return chkFileDataSize;
    }

    public void setChkFileDataSize(Integer chkFileDataSize) {
        this.chkFileDataSize = chkFileDataSize;
    }

    public List<PaymentQueryChkFileDataCase> getChkFileDataList() {
        return chkFileDataList;
    }

    public void setChkFileDataList(List<PaymentQueryChkFileDataCase> chkFileDataList) {
        this.chkFileDataList = chkFileDataList;
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

    public String getOldAplDpt() {
        return oldAplDpt;
    }

    public void setOldAplDpt(String oldAplDpt) {
        this.oldAplDpt = oldAplDpt;
    }

    public String getChoiceYm() {
        return choiceYm;
    }

    public void setChoiceYm(String choiceYm) {
        this.choiceYm = choiceYm;
    }

    public String getMchkTyp() {
        return mchkTyp;
    }

    public void setMchkTyp(String mchkTyp) {
        this.mchkTyp = mchkTyp;
    }

    public BigDecimal getLecomAmt() {
        return lecomAmt;
    }

    public void setLecomAmt(BigDecimal lecomAmt) {
        this.lecomAmt = lecomAmt;
    }

    public BigDecimal getRecomAmt() {
        return recomAmt;
    }

    public void setRecomAmt(BigDecimal recomAmt) {
        this.recomAmt = recomAmt;
    }

    public BigDecimal getOldaAmt() {
        return oldaAmt;
    }

    public void setOldaAmt(BigDecimal oldaAmt) {
        this.oldaAmt = oldaAmt;
    }

    public BigDecimal getOldbAmt() {
        return oldbAmt;
    }

    public void setOldbAmt(BigDecimal oldbAmt) {
        this.oldbAmt = oldbAmt;
    }

    public List<PaymentQueryDetailDataCase> getOtherChkDataList() {
        return otherChkDataList;
    }

    public void setOtherChkDataList(List<PaymentQueryDetailDataCase> otherChkDataList) {
        this.otherChkDataList = otherChkDataList;
    }

    public String getEvtNationName() {
        return evtNationName;
    }

    public void setEvtNationName(String evtNationName) {
        this.evtNationName = evtNationName;
    }

    public String getBenNationName() {
        return benNationName;
    }

    public void setBenNationName(String benNationName) {
        this.benNationName = benNationName;
    }

    public String getOldApNo() {
        return oldApNo;
    }

    public void setOldApNo(String oldApNo) {
        this.oldApNo = oldApNo;
    }

    public BigDecimal getOldAnnuAmt() {
        return oldAnnuAmt;
    }

    public void setOldAnnuAmt(BigDecimal oldAnnuAmt) {
        this.oldAnnuAmt = oldAnnuAmt;
    }

    public String getDisApNo() {
        return disApNo;
    }

    public void setDisApNo(String disApNo) {
        this.disApNo = disApNo;
    }

    public BigDecimal getDisAnnuAmt() {
        return disAnnuAmt;
    }

    public void setDisAnnuAmt(BigDecimal disAnnuAmt) {
        this.disAnnuAmt = disAnnuAmt;
    }

    public BigDecimal getOldExtraRate() {
        return oldExtraRate;
    }

    public void setOldExtraRate(BigDecimal oldExtraRate) {
        this.oldExtraRate = oldExtraRate;
    }

    public String getInterValMonth() {
        return interValMonth;
    }

    public void setInterValMonth(String interValMonth) {
        this.interValMonth = interValMonth;
    }

    public String getBmApNo() {
        return bmApNo;
    }

    public void setBmApNo(String bmApNo) {
        this.bmApNo = bmApNo;
    }

    public BigDecimal getBmChkAmt() {
        return bmChkAmt;
    }

    public void setBmChkAmt(BigDecimal bmChkAmt) {
        this.bmChkAmt = bmChkAmt;
    }

    public String getBmPayDte() {
        return bmPayDte;
    }

    public void setBmPayDte(String bmPayDte) {
        this.bmPayDte = bmPayDte;
    }

    public String getBmPayDteStr() {
        if (StringUtils.isNotBlank(getBmPayDte())) {
            return DateUtility.changeDateType(getBmPayDte());
        }
        else {
            return "";
        }
    }

    public String getComnpMk() {
        return comnpMk;
    }

    public void setComnpMk(String comnpMk) {
        this.comnpMk = comnpMk;
    }

    public String getMonNotifyingMk() {
        return monNotifyingMk;
    }

    public void setMonNotifyingMk(String monNotifyingMk) {
        this.monNotifyingMk = monNotifyingMk;
    }

    public List<DisabledApplicationDataUpdateCheckFileCase> getCheckFileList() {
        return checkFileList;
    }

    public void setCheckFileList(List<DisabledApplicationDataUpdateCheckFileCase> checkFileList) {
        this.checkFileList = checkFileList;
    }

    public String getSpecialAcc() {
        return specialAcc;
    }

    public void setSpecialAcc(String specialAcc) {
        this.specialAcc = specialAcc;
    }

    public BigDecimal getBasicAmt() {
        return basicAmt;
    }

    public void setBasicAmt(BigDecimal basicAmt) {
        this.basicAmt = basicAmt;
    }

    public String getSecrecy() {
        return secrecy;
    }

    public void setSecrecy(String secrecy) {
        this.secrecy = secrecy;
    }

    public BigDecimal getMarginAmt() {
        return marginAmt;
    }

    public void setMarginAmt(BigDecimal marginAmt) {
        this.marginAmt = marginAmt;
    }

    public String getAdWkMk() {
        return adWkMk;
    }

    public void setAdWkMk(String adWkMk) {
        this.adWkMk = adWkMk;
    }

    public String getNlWkMk() {
        return nlWkMk;
    }

    public void setNlWkMk(String nlWkMk) {
        this.nlWkMk = nlWkMk;
    }

    public BigDecimal getInheritorAmt() {
        return inheritorAmt;
    }

    public void setInheritorAmt(BigDecimal inheritorAmt) {
        this.inheritorAmt = inheritorAmt;
    }

    public String getRmp_Name() {
        return rmp_Name;
    }

    public void setRmp_Name(String rmp_Name) {
        this.rmp_Name = rmp_Name;
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
