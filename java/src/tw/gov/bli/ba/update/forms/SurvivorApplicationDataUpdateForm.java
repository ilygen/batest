package tw.gov.bli.ba.update.forms;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Bacountry;
import tw.gov.bli.ba.domain.Baparam;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 (bamo0d230c.jsp bamo0d231c.jsp bamo0d233c.jsp)
 * 
 * @author Goston
 */
public class SurvivorApplicationDataUpdateForm extends BaseValidatorForm {
    private boolean initialed;

    private String method;

    // Page 1
    private String apNo1; // 受理編號 - 第一欄 ( 1 碼 )
    private String apNo2; // 受理編號 - 第二欄 ( 1 碼 )
    private String apNo3; // 受理編號 - 第三欄 ( 5 碼 )
    private String apNo4; // 受理編號 - 第四欄 ( 5 碼 )

    // Page 2
    // BAAPPBASE
    private BigDecimal baappbaseId; // 資料列編號
    private String apNo; // 受理編號
    private String seqNo; // 序號
    private String procStat; // 處理狀態
    private String evtNationTpe; // 事故者國籍別
    private String evtNationCode; // 事故者國籍
    private String evtSex; // 事故者性別
    private String evtName; // 事故者姓名
    private String evtIdnNo; // 事故者身分證號
    private String evtBrDate; // 事故者出生日期
    private String evtDieDate; // 事故者死亡日期
    private String appDate; // 申請日期
    private String apUbno; // 申請單位保險證號
    private String apubnock; // 申請單位保險證號檢查碼
    private String lsUbno; // 最後單位保險證號 - 事故發生單位保險證號
    private String lsUbnoCk; // 最後單位保險證號檢查碼
    private String notifyForm; // 核定通知書格式
    private String loanMk; // 不須抵銷紓困貸款註記
    private String closeCause; // 結案原因
    private String choiceYm; // 擇領起月
    private String dupeIdnNoMk; // 身分證重號註記
    private String apItem; // 申請項目
    private BigDecimal cutAmt; // 應扣失能金額
    private String interValMonth;//發放方式
    private String issuNotifyingMk; // 寄發核定通知書
    private String caseTyp; // 案件類別

    // BAAPPEXPAND
    private BigDecimal baappexpandId; // 資料編號
    private String judgeDate; // 判決日期
    private String evTyp; // 傷病分類
    private String evCode; // 傷病原因
    private String criInPart1; // 受傷部位1
    private String criInPart2; // 受傷部位2
    private String criInPart3; // 受傷部位3
    private String criMedium; // 媒介物
    private String criInJnme1; // 國際疾病代碼1
    private String criInJnme2; // 國際疾病代碼2
    private String criInJnme3; // 國際疾病代碼3
    private String criInJnme4; // 國際疾病代碼4
    private String prType; // 先核普通
    private String famAppMk;// 符合第 63 條之 1 第 3 項註記
    private String evAppTyp;// 申請傷病分類
    private String monNotifyingMk; //寄發月通知表

    // CVLDTL
    private String name; // 戶籍姓名

    // Other
    private String tmpEvtNationCode; // 事故者國籍代碼 (input - 下拉選單)

    // for 改前值
    // [
    // BAAPPBASE
    private String oldEvtNationTpe; // 事故者國籍別
    private String oldEvtNationCode; // 事故者國籍
    private String oldEvtSex; // 事故者性別
    private String oldEvtName; // 事故者姓名
    private String oldEvtIdnNo; // 事故者身分證號
    private String oldEvtBrDate; // 事故者出生日期
    private String oldEvtDieDate; // 事故者死亡日期
    private String oldAppDate; // 申請日期
    private String oldApUbno; // 申請單位保險證號
    private String oldApubnock; // 申請單位保險證號檢查碼
    private String oldLsUbno; // 最後單位保險證號 - 事故發生單位保險證號
    private String oldLsUbnoCk; // 最後單位保險證號檢查碼
    private String oldNotifyForm; // 核定通知書格式
    private String oldLoanMk; // 不須抵銷紓困貸款註記
    private String oldCloseCause; // 結案原因
    private String oldChoiceYm; // 擇領起月
    private String oldDupeIdnNoMk; // 身分證重號註記
    private String oldApItem; // 申請項目
    private BigDecimal oldCutAmt; // 應扣失能金額
    private String oldInterValMonth;//發放方式
    private String oldIssuNotifyingMk; // 寄發核定通知書

    // BAAPPEXPAND
    private String oldJudgeDate; // 判決日期
    private String oldEvTyp; // 傷病分類
    private String oldEvCode; // 傷病原因
    private String oldCriInPart1; // 受傷部位1
    private String oldCriInPart2; // 受傷部位2
    private String oldCriInPart3; // 受傷部位3
    private String oldCriMedium; // 媒介物
    private String oldCriInJnme1; // 國際疾病代碼1
    private String oldCriInJnme2; // 國際疾病代碼2
    private String oldCriInJnme3; // 國際疾病代碼3
    private String oldCriInJnme4; // 國際疾病代碼4
    private String oldPrType; // 先核普通
    private String oldFamAppMk; // 符合第 63 條之 1 第 3 項註記
    private String oldEvAppTyp;// 申請傷病分類
    private String oldMonNotifyingMk; //寄發月通知表

    // Other
    private String oldTmpEvtNationCode; // 事故者國籍代碼 (input - 下拉選單)
    // ]
    // for 改前值

    private List<Bacountry> countryOptionList; // 國籍下拉選單
    private List<Baparam> apItemOptionList; // 申請項目下拉選單
    private List<Baparam> evTypOptionList; // 傷病分類下拉選單
    private List<Baparam> scloseCauseOptionList; // 結案原因下拉選單

    private String validNotifyFormValue; // 可輸入的 核定通知書格式 值, 用於頁面檢核

    private String cbxSameAsApply; // 同申請單位 Checkbox

    // Page 3 - 身分證重號選擇事故者資料頁面
    private int radioDupeIndex;
    
    private String idnoExist;//檢查錯誤檔

    /**
     * 受理編號
     * 
     * @return
     */
    public String getApNoString() {
        if (StringUtils.length(apNo) == ConstantKey.APNO_LENGTH)
            return apNo.substring(0, 1) + "-" + apNo.substring(1, 2) + "-" + apNo.substring(2, 7) + "-" + apNo.substring(7, 12);
        else
            return StringUtils.defaultString(apNo);
    }

    /**
     * 處理狀態
     * 
     * @return
     */
    public String getProcStatString() {
        if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_00))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_00;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_01))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_01;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_10))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_10;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_11))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_11;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_12))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_12;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_13))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_13;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_20))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_20;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_30))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_30;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_40))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_40;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_50))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_50;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_80))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_80;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_90))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_90;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_99))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_99;
        else
            return "";
    }

    /**
     * 符合第 63 條之 1 第 3 項註記
     * 
     * @return
     */
    public String getFamAppMkString() {
        if (StringUtils.isBlank(StringUtils.trimToEmpty(famAppMk)))
            return "空白";
        else
            return famAppMk;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        if (page == 1 || StringUtils.equals(method, "doSave")) {
            ActionErrors errors = super.validate(mapping, request);

            // Validation.xml 驗證若有錯誤則先讓使用者改正所輸入之資料
            if (errors != null && errors.size() > 0)
                return errors;

            return errors;
        }
        else {
            return null;
        }
    }

    public void resetForm(HttpServletRequest request) {
        famAppMk = "";// 符合第 63 條之 1 第 3 項註記
        prType = ""; // 先核普通
        loanMk = ""; // 不須抵銷紓困貸款註記
        cbxSameAsApply = ""; // 同申請單位 Checkbox
        monNotifyingMk = "";  // 寄發月通知表
        issuNotifyingMk = ""; // 寄發核定通知書

        if (!initialed) {
            clearFields();
            initialed = true;
        }
    }

    public void clearFields() {
        evtNationTpe = ""; // 事故者國籍別
        evtNationCode = ""; // 事故者國籍
        evtSex = ""; // 事故者性別
        evtName = ""; // 事故者姓名
        evtIdnNo = ""; // 事故者身分證號
        evtBrDate = ""; // 事故者出生日期
        evtDieDate = ""; // 事故者死亡日期
        appDate = ""; // 申請日期
        apUbno = ""; // 申請單位保險證號
        apubnock = ""; // 申請單位保險證號檢查碼
        lsUbno = ""; // 最後單位保險證號 - 事故發生單位保險證號
        lsUbnoCk = ""; // 最後單位保險證號檢查碼
        notifyForm = ""; // 核定通知書格式
        loanMk = ""; // 不須抵銷紓困貸款註記
        closeCause = ""; // 結案原因
        choiceYm = ""; // 擇領起月
        dupeIdnNoMk = ""; // 身分證重號註記
        apItem = ""; // 申請項目

        judgeDate = ""; // 判決日期
        evTyp = ""; // 傷病分類
        evCode = ""; // 傷病原因
        criInPart1 = ""; // 受傷部位1
        criInPart2 = ""; // 受傷部位2
        criInPart3 = ""; // 受傷部位3
        criMedium = ""; // 媒介物
        criInJnme1 = ""; // 國際疾病代碼1
        criInJnme2 = ""; // 國際疾病代碼2
        criInJnme3 = ""; // 國際疾病代碼3
        criInJnme4 = ""; // 國際疾病代碼4
        prType = ""; // 先核普通
        famAppMk = "";// 符合第 63 條之 1 第 3 項註記
        monNotifyingMk = ""; //寄發月通知表
        issuNotifyingMk = ""; // 寄發核定通知書

        name = ""; // 戶籍姓名

        tmpEvtNationCode = ""; // 事故者國籍代碼 (input - 下拉選單)

        oldEvtNationTpe = ""; // 事故者國籍別
        oldEvtNationCode = ""; // 事故者國籍
        oldEvtSex = ""; // 事故者性別
        oldEvtName = ""; // 事故者姓名
        oldEvtIdnNo = ""; // 事故者身分證號
        oldEvtBrDate = ""; // 事故者出生日期
        oldEvtDieDate = ""; // 事故者死亡日期
        oldAppDate = ""; // 申請日期
        oldApUbno = ""; // 申請單位保險證號
        oldApubnock = ""; // 申請單位保險證號檢查碼
        oldLsUbno = ""; // 最後單位保險證號 - 事故發生單位保險證號
        oldLsUbnoCk = ""; // 最後單位保險證號檢查碼
        oldNotifyForm = ""; // 核定通知書格式
        oldLoanMk = ""; // 不須抵銷紓困貸款註記
        oldCloseCause = ""; // 結案原因
        oldChoiceYm = ""; // 擇領起月
        oldDupeIdnNoMk = ""; // 身分證重號註記
        oldApItem = ""; // 申請項目

        oldJudgeDate = ""; // 判決日期
        oldEvTyp = ""; // 傷病分類
        oldEvCode = ""; // 傷病原因
        oldCriInPart1 = ""; // 受傷部位1
        oldCriInPart2 = ""; // 受傷部位2
        oldCriInPart3 = ""; // 受傷部位3
        oldCriMedium = ""; // 媒介物
        oldCriInJnme1 = ""; // 國際疾病代碼1
        oldCriInJnme2 = ""; // 國際疾病代碼2
        oldCriInJnme3 = ""; // 國際疾病代碼3
        oldCriInJnme4 = ""; // 國際疾病代碼4
        oldPrType = ""; // 先核普通
        oldFamAppMk = "";// 符合第 63 條之 1 第 3 項註記
        oldMonNotifyingMk = ""; //寄發月通知表
        oldIssuNotifyingMk = ""; // 寄發核定通知書

        oldTmpEvtNationCode = ""; // 事故者國籍代碼 (input - 下拉選單)

        cbxSameAsApply = ""; // 同申請單位 Checkbox
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        resetForm(request);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
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

    public String getProcStat() {
        return procStat;
    }

    public void setProcStat(String procStat) {
        this.procStat = procStat;
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

    public String getEvtSex() {
        return evtSex;
    }

    public void setEvtSex(String evtSex) {
        this.evtSex = evtSex;
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

    public String getEvtDieDate() {
        return evtDieDate;
    }

    public void setEvtDieDate(String evtDieDate) {
        this.evtDieDate = evtDieDate;
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

    public String getCloseCause() {
        return closeCause;
    }

    public void setCloseCause(String closeCause) {
        this.closeCause = closeCause;
    }

    public String getChoiceYm() {
        return choiceYm;
    }

    public void setChoiceYm(String choiceYm) {
        this.choiceYm = choiceYm;
    }

    public String getDupeIdnNoMk() {
        return dupeIdnNoMk;
    }

    public void setDupeIdnNoMk(String dupeIdnNoMk) {
        this.dupeIdnNoMk = dupeIdnNoMk;
    }

    public String getApItem() {
        return apItem;
    }

    public void setApItem(String apItem) {
        this.apItem = apItem;
    }

    public BigDecimal getBaappexpandId() {
        return baappexpandId;
    }

    public void setBaappexpandId(BigDecimal baappexpandId) {
        this.baappexpandId = baappexpandId;
    }

    public String getJudgeDate() {
        return judgeDate;
    }

    public void setJudgeDate(String judgeDate) {
        this.judgeDate = judgeDate;
    }

    public String getEvTyp() {
        return evTyp;
    }

    public void setEvTyp(String evTyp) {
        this.evTyp = evTyp;
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

    public String getPrType() {
        return prType;
    }

    public void setPrType(String prType) {
        this.prType = prType;
    }

    public String getFamAppMk() {
        return famAppMk;
    }

    public void setFamAppMk(String famAppMk) {
        this.famAppMk = famAppMk;
    }
    
    public String getMonNotifyingMk() {
        return monNotifyingMk;
    }

    public void setMonNotifyingMk(String monNotifyingMk) {
        this.monNotifyingMk = monNotifyingMk;
    }    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTmpEvtNationCode() {
        return tmpEvtNationCode;
    }

    public void setTmpEvtNationCode(String tmpEvtNationCode) {
        this.tmpEvtNationCode = tmpEvtNationCode;
    }

    public String getOldEvtNationTpe() {
        return oldEvtNationTpe;
    }

    public void setOldEvtNationTpe(String oldEvtNationTpe) {
        this.oldEvtNationTpe = oldEvtNationTpe;
    }

    public String getOldEvtNationCode() {
        return oldEvtNationCode;
    }

    public void setOldEvtNationCode(String oldEvtNationCode) {
        this.oldEvtNationCode = oldEvtNationCode;
    }

    public String getOldEvtSex() {
        return oldEvtSex;
    }

    public void setOldEvtSex(String oldEvtSex) {
        this.oldEvtSex = oldEvtSex;
    }

    public String getOldEvtName() {
        return oldEvtName;
    }

    public void setOldEvtName(String oldEvtName) {
        this.oldEvtName = oldEvtName;
    }

    public String getOldEvtIdnNo() {
        return oldEvtIdnNo;
    }

    public void setOldEvtIdnNo(String oldEvtIdnNo) {
        this.oldEvtIdnNo = oldEvtIdnNo;
    }

    public String getOldEvtBrDate() {
        return oldEvtBrDate;
    }

    public void setOldEvtBrDate(String oldEvtBrDate) {
        this.oldEvtBrDate = oldEvtBrDate;
    }

    public String getOldEvtDieDate() {
        return oldEvtDieDate;
    }

    public void setOldEvtDieDate(String oldEvtDieDate) {
        this.oldEvtDieDate = oldEvtDieDate;
    }

    public String getOldAppDate() {
        return oldAppDate;
    }

    public void setOldAppDate(String oldAppDate) {
        this.oldAppDate = oldAppDate;
    }

    public String getOldApUbno() {
        return oldApUbno;
    }

    public void setOldApUbno(String oldApUbno) {
        this.oldApUbno = oldApUbno;
    }

    public String getOldApubnock() {
        return oldApubnock;
    }

    public void setOldApubnock(String oldApubnock) {
        this.oldApubnock = oldApubnock;
    }

    public String getOldLsUbno() {
        return oldLsUbno;
    }

    public void setOldLsUbno(String oldLsUbno) {
        this.oldLsUbno = oldLsUbno;
    }

    public String getOldLsUbnoCk() {
        return oldLsUbnoCk;
    }

    public void setOldLsUbnoCk(String oldLsUbnoCk) {
        this.oldLsUbnoCk = oldLsUbnoCk;
    }

    public String getOldNotifyForm() {
        return oldNotifyForm;
    }

    public void setOldNotifyForm(String oldNotifyForm) {
        this.oldNotifyForm = oldNotifyForm;
    }

    public String getOldLoanMk() {
        return oldLoanMk;
    }

    public void setOldLoanMk(String oldLoanMk) {
        this.oldLoanMk = oldLoanMk;
    }

    public String getOldCloseCause() {
        return oldCloseCause;
    }

    public void setOldCloseCause(String oldCloseCause) {
        this.oldCloseCause = oldCloseCause;
    }

    public String getOldChoiceYm() {
        return oldChoiceYm;
    }

    public void setOldChoiceYm(String oldChoiceYm) {
        this.oldChoiceYm = oldChoiceYm;
    }

    public String getOldDupeIdnNoMk() {
        return oldDupeIdnNoMk;
    }

    public void setOldDupeIdnNoMk(String oldDupeIdnNoMk) {
        this.oldDupeIdnNoMk = oldDupeIdnNoMk;
    }

    public String getOldApItem() {
        return oldApItem;
    }

    public void setOldApItem(String oldApItem) {
        this.oldApItem = oldApItem;
    }

    public String getOldJudgeDate() {
        return oldJudgeDate;
    }

    public void setOldJudgeDate(String oldJudgeDate) {
        this.oldJudgeDate = oldJudgeDate;
    }

    public String getOldEvTyp() {
        return oldEvTyp;
    }

    public void setOldEvTyp(String oldEvTyp) {
        this.oldEvTyp = oldEvTyp;
    }

    public String getOldEvCode() {
        return oldEvCode;
    }

    public void setOldEvCode(String oldEvCode) {
        this.oldEvCode = oldEvCode;
    }

    public String getOldCriInPart1() {
        return oldCriInPart1;
    }

    public void setOldCriInPart1(String oldCriInPart1) {
        this.oldCriInPart1 = oldCriInPart1;
    }

    public String getOldCriInPart2() {
        return oldCriInPart2;
    }

    public void setOldCriInPart2(String oldCriInPart2) {
        this.oldCriInPart2 = oldCriInPart2;
    }

    public String getOldCriInPart3() {
        return oldCriInPart3;
    }

    public void setOldCriInPart3(String oldCriInPart3) {
        this.oldCriInPart3 = oldCriInPart3;
    }

    public String getOldCriMedium() {
        return oldCriMedium;
    }

    public void setOldCriMedium(String oldCriMedium) {
        this.oldCriMedium = oldCriMedium;
    }

    public String getOldCriInJnme1() {
        return oldCriInJnme1;
    }

    public void setOldCriInJnme1(String oldCriInJnme1) {
        this.oldCriInJnme1 = oldCriInJnme1;
    }

    public String getOldCriInJnme2() {
        return oldCriInJnme2;
    }

    public void setOldCriInJnme2(String oldCriInJnme2) {
        this.oldCriInJnme2 = oldCriInJnme2;
    }

    public String getOldCriInJnme3() {
        return oldCriInJnme3;
    }

    public void setOldCriInJnme3(String oldCriInJnme3) {
        this.oldCriInJnme3 = oldCriInJnme3;
    }

    public String getOldCriInJnme4() {
        return oldCriInJnme4;
    }

    public void setOldCriInJnme4(String oldCriInJnme4) {
        this.oldCriInJnme4 = oldCriInJnme4;
    }

    public String getOldPrType() {
        return oldPrType;
    }

    public void setOldPrType(String oldPrType) {
        this.oldPrType = oldPrType;
    }

    public String getOldFamAppMk() {
        return oldFamAppMk;
    }

    public void setOldFamAppMk(String oldFamAppMk) {
        this.oldFamAppMk = oldFamAppMk;
    }
    
    public String getOldMonNotifyingMk() {
        return oldMonNotifyingMk;
    }

    public void setOldMonNotifyingMk(String oldMonNotifyingMk) {
        this.oldMonNotifyingMk = oldMonNotifyingMk;
    }    

    public String getOldTmpEvtNationCode() {
        return oldTmpEvtNationCode;
    }

    public void setOldTmpEvtNationCode(String oldTmpEvtNationCode) {
        this.oldTmpEvtNationCode = oldTmpEvtNationCode;
    }

    public List<Bacountry> getCountryOptionList() {
        return countryOptionList;
    }

    public void setCountryOptionList(List<Bacountry> countryOptionList) {
        this.countryOptionList = countryOptionList;
    }

    public List<Baparam> getApItemOptionList() {
        return apItemOptionList;
    }

    public void setApItemOptionList(List<Baparam> apItemOptionList) {
        this.apItemOptionList = apItemOptionList;
    }

    public List<Baparam> getEvTypOptionList() {
        return evTypOptionList;
    }

    public void setEvTypOptionList(List<Baparam> evTypOptionList) {
        this.evTypOptionList = evTypOptionList;
    }

    public List<Baparam> getScloseCauseOptionList() {
        return scloseCauseOptionList;
    }

    public void setScloseCauseOptionList(List<Baparam> scloseCauseOptionList) {
        this.scloseCauseOptionList = scloseCauseOptionList;
    }

    public int getRadioDupeIndex() {
        return radioDupeIndex;
    }

    public void setRadioDupeIndex(int radioDupeIndex) {
        this.radioDupeIndex = radioDupeIndex;
    }

    public String getValidNotifyFormValue() {
        return validNotifyFormValue;
    }

    public void setValidNotifyFormValue(String validNotifyFormValue) {
        this.validNotifyFormValue = validNotifyFormValue;
    }

    public String getCbxSameAsApply() {
        return cbxSameAsApply;
    }

    public void setCbxSameAsApply(String cbxSameAsApply) {
        this.cbxSameAsApply = cbxSameAsApply;
    }

    public BigDecimal getCutAmt() {
        return cutAmt;
    }

    public void setCutAmt(BigDecimal cutAmt) {
        this.cutAmt = cutAmt;
    }

    public BigDecimal getOldCutAmt() {
        return oldCutAmt;
    }

    public void setOldCutAmt(BigDecimal oldCutAmt) {
        this.oldCutAmt = oldCutAmt;
    }

    public String getInterValMonth() {
        return interValMonth;
    }

    public void setInterValMonth(String interValMonth) {
        this.interValMonth = interValMonth;
    }

    public String getOldInterValMonth() {
        return oldInterValMonth;
    }

    public void setOldInterValMonth(String oldInterValMonth) {
        this.oldInterValMonth = oldInterValMonth;
    }

    public String getEvAppTyp() {
        return evAppTyp;
    }

    public void setEvAppTyp(String evAppTyp) {
        this.evAppTyp = evAppTyp;
    }

    public String getOldEvAppTyp() {
        return oldEvAppTyp;
    }

    public void setOldEvAppTyp(String oldEvAppTyp) {
        this.oldEvAppTyp = oldEvAppTyp;
    }

	public String getIdnoExist() {
		return idnoExist;
	}

	public void setIdnoExist(String idnoExist) {
		this.idnoExist = idnoExist;
	}
	
    public String getIssuNotifyingMk() {
        return issuNotifyingMk;
    }

    public void setIssuNotifyingMk(String issuNotifyingMk) {
        this.issuNotifyingMk = issuNotifyingMk;
    }
    
    public String getOldIssuNotifyingMk() {
        return oldIssuNotifyingMk;
    }

    public void setOldIssuNotifyingMk(String oldIssuNotifyingMk) {
        this.oldIssuNotifyingMk = oldIssuNotifyingMk;
    }
    
    public String getCaseTyp() {
        return caseTyp;
    }

    public void setCaseTyp(String caseTyp) {
        this.caseTyp = caseTyp;
    }

}
