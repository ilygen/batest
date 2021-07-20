package tw.gov.bli.ba.review.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.PresentationUtility;

/**
 * Case for 失能年金給付審核作業 額外資料
 * 
 * @author Rickychi
 */
public class DisabledPaymentReviewExtCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
    private String benName;// 受款人姓名
    private String chkCode;// 編審註記代號
    private String chkResult;// 編審結果說明
    private String proDate; // 承辦/創收日期
    private String ndomMk1; // 處理註記一

    // 年資維護介接欄位
    // [
    private String apNo;// 受理編號
    private String seqNo;// 序號
    private String seniTyp;// 年資類別
    private String evtIdnNo;// 事故者身分證號
    private String evtBrDate;// 事故者出生日期
    private String evtName;// 事故者姓名
    private String ubno;// 保險證號
    private String ubnoCk;// 保險證號檢查碼
    private String owesBdate;// 扣除期間起日
    private String owesEdate;// 扣除期間迄日
    private String owesMk;// 欠費註記
    private String oldSeniab;// 一次請領之年資採計方式
    // ]

    // 被保險人異動資料檔欄位
    // [
    private String inTyp;// 保險別
    private String idn;// 身分證號
    private String uno;// 保險證號
    private String unoCk;// 保險證號檢查碼
    private String txcd;// 異動代號
    private String efDte;// 生效日期
    private String wage;// 投保薪資
    private String dept;// 工作部門註記
    private String bsmn;// 計費記號
    private String staff;// 處理人員代號
    private String sidMk;// 特殊身分註記
    private String tsMk;// 異動原因註記
    private String nrpMk;// 不退費註記
    private String fill;// 空白
    private String proDte;// 資料擷取日
    // ]

    // 給付核定資料檔欄位
    // [
    private String payYm; // 給付年月
    private BigDecimal issueAmt; // 核定金額
    private BigDecimal adjustAmt;// 調整金額
    private BigDecimal recAmt; // 收回金額
    private BigDecimal payBanance; // 給付沖抵
    private BigDecimal supAmt; // 補發金額
    private BigDecimal offsetAmt; // 紓困金額
    private BigDecimal otherAmt; // 另案扣減金額
    private BigDecimal payRate; // 匯款匯費
    private BigDecimal aplPayAmt;// 實付金額(BADAPR - aplpayAmt)
    // ]

    // 一次給付資料欄位
    // [
    private String closeCause;// 結案原因
    // private String oldSeniab;// 年資採計方式
    private String oncePayMk;// 一次給付符合註記
    private String dabApNo;// 已領失能年金受理編號
    private BigDecimal dabAnnuAmt;// 已領失能年金金額
    private BigDecimal annuAmt; // 累計已領老年年金金額
    private BigDecimal oldtY;// 一次給付實付年資(X年X月-年)
    private BigDecimal oldtD;// 一次給付實付年資(X年X月-月)
    private BigDecimal nitrmY;// 一次給付實付年資(X年X日-年)
    private BigDecimal nitrmM;// 一次給付實付年資(X年X日-日)
    private BigDecimal onceOldAmt;// 老年一次金金額
    private BigDecimal oncePayAmt;// 一次給付核定金額
    private BigDecimal onceAvgAmt;// 一次給付平均薪資
    private BigDecimal oncePayYm;// 一次給付月數
    private BigDecimal diffAmt;// 差額金金額
    private BigDecimal sumPayAmt;// 已領年金總額
    // ]

    // 受款人核定資料
    // [
    private BigDecimal badaprId;// 給付核定檔資料列編號
    private BigDecimal baappbaseId; // 資料列編號
    // private String apNo; // 受理編號
    // private String seqNo; // 受款人序
    private String benIdnNo; // 受款人身分證號
    // private String benName; // 受款人姓名
    private String benBrDate; // 受款人出生日期
    private String benSex;// 受款人性別
    private String benEvtRel; // 關係
    private String chgNote; // 更正原因
    private String isShowCond1; // 檢核條件1
    private String isShowCond2; // 檢核條件2
    private String isShowCond3; // 檢核條件3
    private String isShowCond4; // 檢核條件4
    private String issuYm; // 核定年月
    private String acceptMk; // 電腦編審結果
    private String manchkMk; // 人工審核結果
    private String manchkMkValue;// 核定資料 人工審核結果Radio值

    // private String issueAmt; // 核定金額
    // private BigDecimal adjustAmt; // 調整金額
    // private BigDecimal recAmt; // 應收金額
    // private BigDecimal supAmt; // 補發金額
    // private BigDecimal otherAmt; // 事故者扣減總金額
    // private String payRate; // 匯款匯費
    // private BigDecimal aplPayAmt; // 實付金額zzz
    // ]

    // 受款人編審註記
    // [
    // private String seqNo; // 序號
    // private String payYm; //給付年月
    private String chkCodePost; // 嚴重程度
    // private String chkResult; // 編審註記名稱
    private String issuPayYm;// 編審核定年月
    private List<ChkFileCase> chkFileList;
    private Integer chkFileDataSize;// 資料筆數

    // 核定通知書(稿)資料
    private String notifyForm;// 核定通知書格式
    private List<String> notifyBenName;// 核定通知書受文者
    private String subject; // 主旨
    private List<String> content; // 說明

    // ]

    // 請領同類／他類／另案扣減
    // [
    // 一次給付資料
    private String bmApNo; // 受理編號
    private String bmApDte; // 受理日期
    private String bmEvName; // 事故者姓名
    private String bmEvtDte; // 事故日期
    private BigDecimal bmChkAmt; // 核定金額
    private String bmExmDte; // 核定日期(複核日期)
    private String bmPayDte; // 核付日期
    private String bmNrepDate; // 補件日期
    private String bmNdocMk; // 補件註記
    private String bmNopDate; // 不給付日期
    private BigDecimal bmAdjAmts; // 補收金額
    private String bmUbNo; // 保險證號

    // 年金給付資料
    // private BigDecimal baappbaseId;
    // private String apNo; // 受理編號
    // private String evtName; // 事故者姓名
    private String appDate; // 申請日期
    // private String issuYm; // 核定年月
    // private String payYm; // 給付年月
    private String evtDate; // 事故日期
    private String lsUbno; // 保險證號
    // private BigDecimal issueAmt; // 核定金額
    // private String evtIdnNo;
    // private String evtBrDate;
    private String exeDate; // 不給付日期
    // private String proDate; // 補件日期
    private String ndomk1; // 處理註記
    private String chkDate; // 核定日期
    private String aplPayDate; // 核付日期
    // private BigDecimal recAmt; // 補收金額
    // private BigDecimal supAmt; // 已收金額

    // 申請失能給付紀錄
    // private String bmApNo; // 受理編號
    // private String bmApDte; // 受理日期
    // private String bmEvName; // 事故者姓名
    // private String bmEvtDte; // 診斷失能日期
    // private BigDecimal bmChkAmt; // 核定金額
    // private String bmExmDte; // 核定日期
    // private String bmPayDte; // 核付日期
    // private String bmNrepDate; // 補件日期
    // private String bmNdocMk; // 補件註記
    // private String bmNopDate; // 不給付日期
    private String bmCriInjCl; // 失能項目
    private String bmCriInjDp; // 失能等級

    // 申請死亡給付紀錄
    // private String bmApNo; // 受理編號
    // private String bmApDte; // 申請日期
    // private String bmEvName; // 事故者姓名
    // private String bmEvtDte; // 死亡日期
    // private BigDecimal bmChkAmt; // 核定金額
    // private String bmExmDte; // 核定日期
    // private String bmPayDte; // 核付日期
    // private String bmNrepDate; // 補件日期
    // private String bmNdocMk;// 補件註記
    // private String bmNopDate; // 不給付日期

    // 申請傷病給付紀錄
    // private String bmApNo; // 受理編號
    // private String bmApDte; // 申請日期
    // private String bmEvName; // 事故者姓名
    // private String bmEvtDte; // 事故日期
    // private BigDecimal bmChkAmt; // 核定金額
    // private String bmExmDte; // 核定日期
    // private String bmPayDte; // 核付日期
    // private String bmNrepDate; // 補件日期
    // private String bmNdocMk; // 補件註記
    // private String bmNopDate; // 不給付日期
    private String bmLosFmDte; // 核定起日
    private String bmLosToDte; // 核定迄日

    // 申請失業給付紀錄
    // private String apNo; //受理編號
    private String symDte; // 給付起日
    private String tymDte; // 給付迄日
    private String apDte; // 申請日期
    private String name; // 被保險人姓名
    private String tdte; // 事故日期
    private BigDecimal chkAmt; // 核定金額
    private String chkDte; // 核定日期
    private String payDte; // 核付日期
    private String npyDte; // 不給付日期
    private String aprDte;// 求職日期
    private String ndcMrk;// 補件註記
    private String savDt1;// 補件日期

    // 申請國保給付記錄
    // private String appDate; // 申請日期(收件日期)
    // private String apNo; // 受理編號
    // private String issuYm; // 核定年月
    // private String payYm; // 給付年月
    private String evteeName; // 事故者姓名
    private String evtDt; // 事故日期
    // private BigDecimal issueAmt; // 核定金額
    private String manChkFlg; // 人工審核結果
    private String acceptMark; // 合格註記
    private String chkDt;// 核定日期

    // private String aplPayDate;// 核付日期
    // ]

    // 頁面顯示轉換
    // [
    public String getChkCodeStr() {
        if (StringUtils.isNotBlank(getChkCode())) {
            return getChkCode().replace("\\", "\\\\").replace("\"", "\\\"").replace("\'", "\\\'");
        }
        else {
            return "";
        }
    }

    public String getChkResultStr() {
        if (StringUtils.isNotBlank(getChkResult())) {
            return getChkResult().replace("\\", "\\\\").replace("\"", "\\\"").replace("\'", "\\\'");
        }
        else {
            return "";
        }

    }

    public String getApNoStr() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH) {
            return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12);
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

    public String getBenBrDateStr() {
        if (StringUtils.isNotBlank(getBenBrDate())) {
            return PresentationUtility.changeDateTypeForDisplay(getBenBrDate());
        }
        else {
            return "";
        }
    }

    public String getOwesBdateStr() {
        if (StringUtils.isNotBlank(getOwesBdate())) {
            return DateUtility.changeDateType(getOwesBdate());
        }
        else {
            return "";
        }
    }

    public String getOwesEdateStr() {
        if (StringUtils.isNotBlank(getOwesEdate())) {
            return DateUtility.changeDateType(getOwesEdate());
        }
        else {
            return "";
        }
    }

    public String getEfDteStr() {
        if (StringUtils.isNotBlank(getEfDte())) {
            return DateUtility.changeDateType(getEfDte());
        }
        else {
            return "";
        }
    }

    public String getProDteStr() {
        if (StringUtils.isNotBlank(getProDte())) {
            return DateUtility.changeDateType(getProDte());
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

    public String getIssuYmStr() {
        if (StringUtils.isNotBlank(getIssuYm()) && getIssuYm().length() == 6) {
            return DateUtility.changeDateType(getIssuYm() + "01").substring(0, 5);
        }
        else {
            return "";
        }
    }

    public String getDabApNoStr() {
        if (StringUtils.isNotBlank(getDabApNo()) && getDabApNo().length() == ConstantKey.APNO_LENGTH) {
            return getDabApNo().substring(0, 1) + "-" + getDabApNo().substring(1, 2) + "-" + getDabApNo().substring(2, 7) + "-" + getDabApNo().substring(7, 12);
        }
        else {
            return "";
        }
    }

    public String getProDateStr() {
        if (StringUtils.isNotBlank(getProDate())) {
            return DateUtility.changeDateType(getProDate());
        }
        else {
            return "";
        }
    }

    public String getBmApNoStr() {
        if (StringUtils.isNotBlank(getBmApNo()) && getBmApNo().length() == ConstantKey.APNO_LENGTH) {
            return getBmApNo().substring(0, 1) + "-" + getBmApNo().substring(1, 2) + "-" + getBmApNo().substring(2, 7) + "-" + getBmApNo().substring(7, 12);
        }
        else {
            return "";
        }
    }

    public String getBmApDteStr() {
        if (StringUtils.isNotBlank(getBmApDte())) {
            return DateUtility.changeDateType(getBmApDte());
        }
        else {
            return "";
        }
    }

    public String getBmEvtDteStr() {
        if (StringUtils.isNotBlank(getBmEvtDte())) {
            return DateUtility.changeDateType(getBmEvtDte());
        }
        else {
            return "";
        }
    }

    public String getBmExmDteStr() {
        if (StringUtils.isNotBlank(getBmExmDte())) {
            return DateUtility.changeDateType(getBmExmDte());
        }
        else {
            return "";
        }
    }

    public String getBmPayDteStr() {
        if (StringUtils.isNotBlank(getBmPayDte())) {
            return DateUtility.changeDateType(getBmPayDte());
        }
        else {
            return "";
        }
    }

    public String getBmNrepDateStr() {
        if (StringUtils.isNotBlank(getBmNrepDate())) {
            return DateUtility.changeDateType(getBmNrepDate());
        }
        else {
            return "";
        }
    }

    public String getBmNopDateStr() {
        if (StringUtils.isNotBlank(getBmNopDate())) {
            return DateUtility.changeDateType(getBmNopDate());
        }
        else {
            return "";
        }
    }

    public String getBmLosFmDteStr() {
        if (StringUtils.isNotBlank(getBmLosFmDte())) {
            return DateUtility.changeDateType(getBmLosFmDte());
        }
        else {
            return "";
        }
    }

    public String getBmLosToDteStr() {
        if (StringUtils.isNotBlank(getBmLosToDte())) {
            return DateUtility.changeDateType(getBmLosToDte());
        }
        else {
            return "";
        }
    }

    public String getSymDteStr() {
        if (StringUtils.isNotBlank(getSymDte())) {
            return DateUtility.changeDateType(getSymDte());
        }
        else {
            return "";
        }
    }

    public String getTymDteStr() {
        if (StringUtils.isNotBlank(getTymDte())) {
            return DateUtility.changeDateType(getTymDte());
        }
        else {
            return "";
        }
    }

    public String getApDteStr() {
        if (StringUtils.isNotBlank(getApDte())) {
            return DateUtility.changeDateType(getApDte());
        }
        else {
            return "";
        }
    }

    public String getTdteStr() {
        if (StringUtils.isNotBlank(getTdte())) {
            return DateUtility.changeDateType(getTdte());
        }
        else {
            return "";
        }
    }

    public String getChkDteStr() {
        if (StringUtils.isNotBlank(getChkDte())) {
            return DateUtility.changeDateType(getChkDte());
        }
        else {
            return "";
        }
    }

    public String getPayDteStr() {
        if (StringUtils.isNotBlank(getPayDte())) {
            return DateUtility.changeDateType(getPayDte());
        }
        else {
            return "";
        }
    }

    public String getNpyDteStr() {
        if (StringUtils.isNotBlank(getNpyDte())) {
            return DateUtility.changeDateType(getNpyDte());
        }
        else {
            return "";
        }
    }

    public String getAprDteStr() {
        if (StringUtils.isNotBlank(getProDate())) {
            return DateUtility.changeDateType(getProDate());
        }
        else {
            return "";
        }
    }

    public String getSavDt1Str() {
        if (StringUtils.isNotBlank(getSavDt1())) {
            return DateUtility.changeDateType(getSavDt1());
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

    public String getEvtDateStr() {
        if (StringUtils.isNotBlank(getEvtDate())) {
            return DateUtility.changeDateType(getEvtDate());
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

    public String getChkDateStr() {
        if (StringUtils.isNotBlank(getChkDate())) {
            return DateUtility.changeDateType(getChkDate());
        }
        else {
            return "";
        }
    }

    public String getAplPayDateStr() {
        if (StringUtils.isNotBlank(getAplPayDate())) {
            return DateUtility.changeDateType(getAplPayDate());
        }
        else {
            return "";
        }
    }

    public String getEvtDtStr() {
        if (StringUtils.isNotBlank(getEvtDt())) {
            return DateUtility.changeDateType(getEvtDt());
        }
        else {
            return "";
        }
    }

    public String getChkDtStr() {
        if (StringUtils.isNotBlank(getChkDt())) {
            return DateUtility.changeDateType(getChkDt());
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

    public String getChgNote() {
        return chgNote;
    }

    public void setChgNote(String chgNote) {
        this.chgNote = chgNote;
    }

    public String getIsShowCond1() {
        return isShowCond1;
    }

    public void setIsShowCond1(String isShowCond1) {
        this.isShowCond1 = isShowCond1;
    }

    public String getIsShowCond2() {
        return isShowCond2;
    }

    public void setIsShowCond2(String isShowCond2) {
        this.isShowCond2 = isShowCond2;
    }

    public String getIsShowCond3() {
        return isShowCond3;
    }

    public void setIsShowCond3(String isShowCond3) {
        this.isShowCond3 = isShowCond3;
    }

    public String getIsShowCond4() {
        return isShowCond4;
    }

    public void setIsShowCond4(String isShowCond4) {
        this.isShowCond4 = isShowCond4;
    }

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
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

    public String getInTyp() {
        return inTyp;
    }

    public void setInTyp(String inTyp) {
        this.inTyp = inTyp;
    }

    public String getIdn() {
        return idn;
    }

    public void setIdn(String idn) {
        this.idn = idn;
    }

    public String getUno() {
        return uno;
    }

    public void setUno(String uno) {
        this.uno = uno;
    }

    public String getUnoCk() {
        return unoCk;
    }

    public void setUnoCk(String unoCk) {
        this.unoCk = unoCk;
    }

    public String getTxcd() {
        return txcd;
    }

    public void setTxcd(String txcd) {
        this.txcd = txcd;
    }

    public String getEfDte() {
        return efDte;
    }

    public void setEfDte(String efDte) {
        this.efDte = efDte;
    }

    public String getWage() {
        return wage;
    }

    public void setWage(String wage) {
        this.wage = wage;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getBsmn() {
        return bsmn;
    }

    public void setBsmn(String bsmn) {
        this.bsmn = bsmn;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getSidMk() {
        return sidMk;
    }

    public void setSidMk(String sidMk) {
        this.sidMk = sidMk;
    }

    public String getTsMk() {
        return tsMk;
    }

    public void setTsMk(String tsMk) {
        this.tsMk = tsMk;
    }

    public String getNrpMk() {
        return nrpMk;
    }

    public void setNrpMk(String nrpMk) {
        this.nrpMk = nrpMk;
    }

    public String getFill() {
        return fill;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }

    public String getProDte() {
        return proDte;
    }

    public void setProDte(String proDte) {
        this.proDte = proDte;
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

    public String getSeniTyp() {
        return seniTyp;
    }

    public void setSeniTyp(String seniTyp) {
        this.seniTyp = seniTyp;
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

    public String getUbno() {
        return ubno;
    }

    public void setUbno(String ubno) {
        this.ubno = ubno;
    }

    public String getUbnoCk() {
        return ubnoCk;
    }

    public void setUbnoCk(String ubnoCk) {
        this.ubnoCk = ubnoCk;
    }

    public String getOwesBdate() {
        return owesBdate;
    }

    public void setOwesBdate(String owesBdate) {
        this.owesBdate = owesBdate;
    }

    public String getOwesEdate() {
        return owesEdate;
    }

    public void setOwesEdate(String owesEdate) {
        this.owesEdate = owesEdate;
    }

    public String getOwesMk() {
        return owesMk;
    }

    public void setOwesMk(String owesMk) {
        this.owesMk = owesMk;
    }

    public String getOldSeniab() {
        return oldSeniab;
    }

    public void setOldSeniab(String oldSeniab) {
        this.oldSeniab = oldSeniab;
    }

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }

    public String getChkCode() {
        return chkCode;
    }

    public void setChkCode(String chkCode) {
        this.chkCode = chkCode;
    }

    public String getChkResult() {
        return chkResult;
    }

    public void setChkResult(String chkResult) {
        this.chkResult = chkResult;
    }

    public String getProDate() {
        return proDate;
    }

    public void setProDate(String proDate) {
        this.proDate = proDate;
    }

    public String getNdomMk1() {
        return ndomMk1;
    }

    public void setNdomMk1(String ndomMk1) {
        this.ndomMk1 = ndomMk1;
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

    public BigDecimal getAdjustAmt() {
        return adjustAmt;
    }

    public void setAdjustAmt(BigDecimal adjustAmt) {
        this.adjustAmt = adjustAmt;
    }

    public BigDecimal getRecAmt() {
        return recAmt;
    }

    public void setRecAmt(BigDecimal recAmt) {
        this.recAmt = recAmt;
    }

    public BigDecimal getSupAmt() {
        return supAmt;
    }

    public void setSupAmt(BigDecimal supAmt) {
        this.supAmt = supAmt;
    }

    public BigDecimal getOtherAmt() {
        return otherAmt;
    }

    public void setOtherAmt(BigDecimal otherAmt) {
        this.otherAmt = otherAmt;
    }

    public BigDecimal getPayRate() {
        return payRate;
    }

    public void setPayRate(BigDecimal payRate) {
        this.payRate = payRate;
    }

    public BigDecimal getAplPayAmt() {
        return aplPayAmt;
    }

    public void setAplPayAmt(BigDecimal aplPayAmt) {
        this.aplPayAmt = aplPayAmt;
    }

    public String getCloseCause() {
        return closeCause;
    }

    public void setCloseCause(String closeCause) {
        this.closeCause = closeCause;
    }

    public String getOncePayMk() {
        return oncePayMk;
    }

    public void setOncePayMk(String oncePayMk) {
        this.oncePayMk = oncePayMk;
    }

    public BigDecimal getOnceAvgAmt() {
        return onceAvgAmt;
    }

    public void setOnceAvgAmt(BigDecimal onceAvgAmt) {
        this.onceAvgAmt = onceAvgAmt;
    }

    public BigDecimal getOnceOldAmt() {
        return onceOldAmt;
    }

    public void setOnceOldAmt(BigDecimal onceOldAmt) {
        this.onceOldAmt = onceOldAmt;
    }

    public BigDecimal getAnnuAmt() {
        return annuAmt;
    }

    public void setAnnuAmt(BigDecimal annuAmt) {
        this.annuAmt = annuAmt;
    }

    public String getChkCodePost() {
        return chkCodePost;
    }

    public void setChkCodePost(String chkCodePost) {
        this.chkCodePost = chkCodePost;
    }

    public BigDecimal getBadaprId() {
        return badaprId;
    }

    public void setBadaprId(BigDecimal badaprId) {
        this.badaprId = badaprId;
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

    public BigDecimal getOldtY() {
        return oldtY;
    }

    public void setOldtY(BigDecimal oldtY) {
        this.oldtY = oldtY;
    }

    public BigDecimal getOldtD() {
        return oldtD;
    }

    public void setOldtD(BigDecimal oldtD) {
        this.oldtD = oldtD;
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

    public BigDecimal getOncePayAmt() {
        return oncePayAmt;
    }

    public void setOncePayAmt(BigDecimal oncePayAmt) {
        this.oncePayAmt = oncePayAmt;
    }

    public BigDecimal getOncePayYm() {
        return oncePayYm;
    }

    public void setOncePayYm(BigDecimal oncePayYm) {
        this.oncePayYm = oncePayYm;
    }

    public BigDecimal getDiffAmt() {
        return diffAmt;
    }

    public void setDiffAmt(BigDecimal diffAmt) {
        this.diffAmt = diffAmt;
    }

    public BigDecimal getSumPayAmt() {
        return sumPayAmt;
    }

    public void setSumPayAmt(BigDecimal sumPayAmt) {
        this.sumPayAmt = sumPayAmt;
    }

    public String getBmApNo() {
        return bmApNo;
    }

    public void setBmApNo(String bmApNo) {
        this.bmApNo = bmApNo;
    }

    public String getBmApDte() {
        return bmApDte;
    }

    public void setBmApDte(String bmApDte) {
        this.bmApDte = bmApDte;
    }

    public String getBmEvName() {
        return bmEvName;
    }

    public void setBmEvName(String bmEvName) {
        this.bmEvName = bmEvName;
    }

    public String getBmEvtDte() {
        return bmEvtDte;
    }

    public void setBmEvtDte(String bmEvtDte) {
        this.bmEvtDte = bmEvtDte;
    }

    public BigDecimal getBmChkAmt() {
        return bmChkAmt;
    }

    public void setBmChkAmt(BigDecimal bmChkAmt) {
        this.bmChkAmt = bmChkAmt;
    }

    public String getBmExmDte() {
        return bmExmDte;
    }

    public void setBmExmDte(String bmExmDte) {
        this.bmExmDte = bmExmDte;
    }

    public String getBmPayDte() {
        return bmPayDte;
    }

    public void setBmPayDte(String bmPayDte) {
        this.bmPayDte = bmPayDte;
    }

    public String getBmNrepDate() {
        return bmNrepDate;
    }

    public void setBmNrepDate(String bmNrepDate) {
        this.bmNrepDate = bmNrepDate;
    }

    public String getBmNdocMk() {
        return bmNdocMk;
    }

    public void setBmNdocMk(String bmNdocMk) {
        this.bmNdocMk = bmNdocMk;
    }

    public String getBmNopDate() {
        return bmNopDate;
    }

    public void setBmNopDate(String bmNopDate) {
        this.bmNopDate = bmNopDate;
    }

    public BigDecimal getBmAdjAmts() {
        return bmAdjAmts;
    }

    public void setBmAdjAmts(BigDecimal bmAdjAmts) {
        this.bmAdjAmts = bmAdjAmts;
    }

    public String getBmUbNo() {
        return bmUbNo;
    }

    public void setBmUbNo(String bmUbNo) {
        this.bmUbNo = bmUbNo;
    }

    public String getBmCriInjCl() {
        return bmCriInjCl;
    }

    public void setBmCriInjCl(String bmCriInjCl) {
        this.bmCriInjCl = bmCriInjCl;
    }

    public String getBmCriInjDp() {
        return bmCriInjDp;
    }

    public void setBmCriInjDp(String bmCriInjDp) {
        this.bmCriInjDp = bmCriInjDp;
    }

    public String getBmLosFmDte() {
        return bmLosFmDte;
    }

    public void setBmLosFmDte(String bmLosFmDte) {
        this.bmLosFmDte = bmLosFmDte;
    }

    public String getBmLosToDte() {
        return bmLosToDte;
    }

    public void setBmLosToDte(String bmLosToDte) {
        this.bmLosToDte = bmLosToDte;
    }

    public String getAprDte() {
        return aprDte;
    }

    public void setAprDte(String aprDte) {
        this.aprDte = aprDte;
    }

    public String getNdcMrk() {
        return ndcMrk;
    }

    public void setNdcMrk(String ndcMrk) {
        this.ndcMrk = ndcMrk;
    }

    public String getSavDt1() {
        return savDt1;
    }

    public void setSavDt1(String savDt1) {
        this.savDt1 = savDt1;
    }

    public String getSymDte() {
        return symDte;
    }

    public void setSymDte(String symDte) {
        this.symDte = symDte;
    }

    public String getTymDte() {
        return tymDte;
    }

    public void setTymDte(String tymDte) {
        this.tymDte = tymDte;
    }

    public String getApDte() {
        return apDte;
    }

    public void setApDte(String apDte) {
        this.apDte = apDte;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTdte() {
        return tdte;
    }

    public void setTdte(String tdte) {
        this.tdte = tdte;
    }

    public BigDecimal getChkAmt() {
        return chkAmt;
    }

    public void setChkAmt(BigDecimal chkAmt) {
        this.chkAmt = chkAmt;
    }

    public String getChkDte() {
        return chkDte;
    }

    public void setChkDte(String chkDte) {
        this.chkDte = chkDte;
    }

    public String getPayDte() {
        return payDte;
    }

    public void setPayDte(String payDte) {
        this.payDte = payDte;
    }

    public String getNpyDte() {
        return npyDte;
    }

    public void setNpyDte(String npyDte) {
        this.npyDte = npyDte;
    }

    public String getBenSex() {
        return benSex;
    }

    public void setBenSex(String benSex) {
        this.benSex = benSex;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getEvtDate() {
        return evtDate;
    }

    public void setEvtDate(String evtDate) {
        this.evtDate = evtDate;
    }

    public String getLsUbno() {
        return lsUbno;
    }

    public void setLsUbno(String lsUbno) {
        this.lsUbno = lsUbno;
    }

    public String getExeDate() {
        return exeDate;
    }

    public void setExeDate(String exeDate) {
        this.exeDate = exeDate;
    }

    public String getNdomk1() {
        return ndomk1;
    }

    public void setNdomk1(String ndomk1) {
        this.ndomk1 = ndomk1;
    }

    public String getChkDate() {
        return chkDate;
    }

    public void setChkDate(String chkDate) {
        this.chkDate = chkDate;
    }

    public String getAplPayDate() {
        return aplPayDate;
    }

    public void setAplPayDate(String aplPayDate) {
        this.aplPayDate = aplPayDate;
    }

    public String getEvteeName() {
        return evteeName;
    }

    public void setEvteeName(String evteeName) {
        this.evteeName = evteeName;
    }

    public String getEvtDt() {
        return evtDt;
    }

    public void setEvtDt(String evtDt) {
        this.evtDt = evtDt;
    }

    public String getManChkFlg() {
        return manChkFlg;
    }

    public void setManChkFlg(String manChkFlg) {
        this.manChkFlg = manChkFlg;
    }

    public String getAcceptMark() {
        return acceptMark;
    }

    public void setAcceptMark(String acceptMark) {
        this.acceptMark = acceptMark;
    }

    public String getChkDt() {
        return chkDt;
    }

    public void setChkDt(String chkDt) {
        this.chkDt = chkDt;
    }

    public List<String> getNotifyBenName() {
        return notifyBenName;
    }

    public void setNotifyBenName(List<String> notifyBenName) {
        this.notifyBenName = notifyBenName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public String getNotifyForm() {
        return notifyForm;
    }

    public void setNotifyForm(String notifyForm) {
        this.notifyForm = notifyForm;
    }

    public String getIssuPayYm() {
        return issuPayYm;
    }

    public void setIssuPayYm(String issuPayYm) {
        this.issuPayYm = issuPayYm;
    }

    public List<ChkFileCase> getChkFileList() {
        return chkFileList;
    }

    public void setChkFileList(List<ChkFileCase> chkFileList) {
        this.chkFileList = chkFileList;
    }

    public Integer getChkFileDataSize() {
        return chkFileDataSize;
    }

    public void setChkFileDataSize(Integer chkFileDataSize) {
        this.chkFileDataSize = chkFileDataSize;
    }

    public String getManchkMkValue() {
        return manchkMkValue;
    }

    public void setManchkMkValue(String manchkMkValue) {
        this.manchkMkValue = manchkMkValue;
    }

    public BigDecimal getPayBanance() {
        return payBanance;
    }

    public void setPayBanance(BigDecimal payBanance) {
        this.payBanance = payBanance;
    }

    public BigDecimal getOffsetAmt() {
        return offsetAmt;
    }

    public void setOffsetAmt(BigDecimal offsetAmt) {
        this.offsetAmt = offsetAmt;
    }
}
