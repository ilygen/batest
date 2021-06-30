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
import tw.gov.bli.ba.util.DateUtility;

/**
 * 更正作業 - 案件資料更正 - 失能年金案件資料更正 (bamo0d130c.jsp bamo0d131c.jsp bamo0d133c.jsp)
 * 
 * @author Goston
 */
public class DisabledApplicationDataUpdateForm extends BaseValidatorForm {
    private boolean initialed;

    private String method;
    private String comSeniority;    //
    private String comSeniorityCk;  //
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
    private String evtJobDate; // 事故者離職日期 - 診斷失能日期
    private String evtDieDate; // 事故者死亡日期
    private String appDate; // 申請日期
    private String apUbno; // 申請單位保險證號
    private String apubnock; // 申請單位保險證號檢查碼
    private String lsUbno; // 最後單位保險證號 - 事故發生單位保險證號
    private String lsUbnoCk; // 最後單位保險證號檢查碼
    private String notifyForm; // 核定通知書格式
    private BigDecimal cutAmt; // 扣減 / 補償總金額 - 年金應扣金額
    private String loanMk; // 不須抵銷紓困貸款註記
    private String closeCause; // 結案原因
    private String choiceYm; // 擇領起月
    private String interValMonth;// 發放方式
    private String mapNo; // 國保受理編號

    // BAAPPEXPAND
    private BigDecimal baappexpandId; // 資料編號
    private String evTyp; // 傷病分類
    private String evCode; // 傷病原因
    private String criInPart1; // 受傷部位1
    private String criInPart2; // 受傷部位2
    private String criInPart3; // 受傷部位3
    private String criMedium; // 媒介物
    private String criInIssul; // 核定等級
    private String criInJcl1; // 失能等級1
    private String criInJcl2; // 失能等級2
    private String criInJcl3; // 失能等級3
    private String criInJdp1; // 失能項目1
    private String criInJdp2; // 失能項目2
    private String criInJdp3; // 失能項目3
    private String criInJdp4; // 失能項目4
    private String criInJdp5; // 失能項目5
    private String criInJdp6; // 失能項目6
    private String criInJdp7; // 失能項目7
    private String criInJdp8; // 失能項目8
    private String criInJdp9; // 失能項目9
    private String criInJdp10; // 失能項目10
    private String hosId; // 醫療院所代碼
    private String doctorName1; // 醫師姓名1
    private String doctorName2; // 醫師姓名2
    private String ocAccHosId; // 職病醫療院所代碼
    private String ocAccDoctorName1; // 職病醫師姓名1
    private String ocAccDoctorName2; // 職病醫師姓名2    
    private String criInJnme1; // 國際疾病代碼1
    private String criInJnme2; // 國際疾病代碼2
    private String criInJnme3; // 國際疾病代碼3
    private String criInJnme4; // 國際疾病代碼4
    private String rehcMk; // 重新查核失能程度註記
    // private String rehcYm; // 重新查核失能程度年月
    private String ocaccIdentMk; // 符合第20條之1註記
    private String prType; // 先核普通
    private BigDecimal ocAccaddAmt; // 己領職災增給金額
    private BigDecimal deductDay; // 扣除天數
    private String evAppTyp;// 申請傷病分類
    private String disQualMk;// 年金請領資格
    private String monNotifyingMk;  // 寄發月通知表

    // CVLDTL
    private String name; // 戶籍姓名

    // Other
    private String hosName; // 醫療院所名稱
    private String ocAccHosName; // 醫療院所名稱
    // private String cbxRehcMk; // 重新查核失能程度註記 (input - Checkbox)
    private String rehcYear; // 重新查核失能程度註記 - 年 (input - Text)
    private String rehcMonth; // 重新查核失能程度註記 - 月 (input - Text)
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
    private String oldEvtJobDate; // 事故者離職日期 - 診斷失能日期
    private String oldEvtDieDate; // 事故者死亡日期
    private String oldAppDate; // 申請日期
    private String oldApUbno; // 申請單位保險證號
    private String oldApubnock; // 申請單位保險證號檢查碼
    private String oldLsUbno; // 最後單位保險證號 - 事故發生單位保險證號
    private String oldLsUbnoCk; // 最後單位保險證號檢查碼
    private String oldNotifyForm; // 核定通知書格式
    private BigDecimal oldCutAmt; // 扣減 / 補償總金額 - 年金應扣金額
    private String oldLoanMk; // 不須抵銷紓困貸款註記
    private String oldCloseCause; // 結案原因
    private String oldChoiceYm; // 擇領起月
    private String oldInterValMonth;// 發放方式
    private String oldMapNo; // 國保受理編號

    // BAAPPEXPAND
    private String oldEvTyp; // 傷病分類
    private String oldEvCode; // 傷病原因
    private String oldCriInPart1; // 受傷部位1
    private String oldCriInPart2; // 受傷部位2
    private String oldCriInPart3; // 受傷部位3
    private String oldCriMedium; // 媒介物
    private String oldCriInIssul; // 核定等級
    private String oldCriInJcl1; // 失能等級1
    private String oldCriInJcl2; // 失能等級2
    private String oldCriInJcl3; // 失能等級3
    private String oldCriInJdp1; // 失能項目1
    private String oldCriInJdp2; // 失能項目2
    private String oldCriInJdp3; // 失能項目3
    private String oldCriInJdp4; // 失能項目4
    private String oldCriInJdp5; // 失能項目5
    private String oldCriInJdp6; // 失能項目6
    private String oldCriInJdp7; // 失能項目7
    private String oldCriInJdp8; // 失能項目8
    private String oldCriInJdp9; // 失能項目9
    private String oldCriInJdp10; // 失能項目10
    private String oldHosId; // 醫療院所代碼
    private String oldDoctorName1; // 醫師姓名1
    private String oldDoctorName2; // 醫師姓名2
    private String oldOcAccHosId; // 職病醫療院所代碼
    private String oldOcAccDoctorName1; // 職病醫師姓名1
    private String oldOcAccDoctorName2; // 職病醫師姓名2    
    private String oldCriInJnme1; // 國際疾病代碼1
    private String oldCriInJnme2; // 國際疾病代碼2
    private String oldCriInJnme3; // 國際疾病代碼3
    private String oldCriInJnme4; // 國際疾病代碼4
    private String oldRehcMk; // 重新查核失能程度註記
    // private String oldRehcYm; // 重新查核失能程度年月
    private String oldOcaccIdentMk; // 符合第20條之1註記
    private String oldPrType; // 先核普通
    private BigDecimal oldOcAccaddAmt; // 己領職災增給金額
    private BigDecimal oldDeductDay; // 扣除天數
    private String oldEvAppTyp;// 申請傷病分類
    private String oldDisQualMk;// 年金請領資格
    private String oldMonNotifyingMk;  // 寄發月通知表

    // Other
    // private String oldCbxRehcMk; // 重新查核失能程度註記 (input - Checkbox)
    private String oldRehcYear; // 重新查核失能程度註記 - 年 (input - Text)
    private String oldRehcMonth; // 重新查核失能程度註記 - 月 (input - Text)
    private String oldTmpEvtNationCode; // 事故者國籍代碼 (input - 下拉選單)
    // ]
    // for 改前值
    
    private String oldProcStat; // 處理狀態
    private String oldOrType; // 先核普通
    private String oldRvAppTyp;// 申請傷病分類

    // CVLDTL
    private String oldName; // 戶籍姓名

    // Other
    private String oldHosName; // 醫療院所名稱
    private String oldOcAccHosName; // 職病醫療院所名稱

    private List<Bacountry> countryOptionList; // 國籍下拉選單
    private List<Baparam> evTypOptionList; // 傷病分類下拉選單
    private List<Baparam> kcloseCauseOptionList; // 結案原因下拉選單
    private List<Baparam> disQualMkOptionList; // 年金請領資格下拉選單
    private List<Baparam> reChkStatusOptionList; // 重新查核狀態下拉選單
    private List<Baparam> reChkResult1OptionList; // 重新查核結果下拉選單
    private List<Baparam> reChkResult2OptionList; // 重新查核結果下拉選單

    private String validNotifyFormValue; // 可輸入的 核定通知書格式 值, 用於頁面檢核
    private String rehcYm; // 重新查核失能程度註記 (XXX年XX月) 的部份
    private String oldRehcYm; // 重新查核失能程度註記 (XXX年XX月) 的部份

    private String cbxSameAsApply; // 同申請單位 Checkbox
    private String oldCbxSameAsApply; // 同申請單位 Checkbox

    // Page 3 - 身分證重號選擇事故者資料頁面
    private int radioDupeIndex;
    
    // 重新審核失能程度
    private String isreChk;     // 是否複檢
    private String reChkStatus; // 重新查核狀態
    //頁面select使用
    private String reChkResult1; // 重新查核結果1
    private String reChkResult2; // 重新查核結果2
    private String reChkYm;     // 重新查核失能程度年月
    private String comReChkYm;  // 完成重新查核年月
    
    private String oldIsreChk;     // 是否複檢
    private String oldReChkStatus; // 重新查核狀態
    private String oldReChkResult1; // 重新查核結果
    private String oldReChkResult2; // 重新查核結果
    private String oldComReChkYm; // 完成重新查核年月
  

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
     * 重新查核失能程度註記 (XXX年XX月) 的部份
     * 
     * @return
     */
    public String getRehcYmString() {
        if (StringUtils.isNotBlank(rehcYm)) {
            String returnYearMonth = rehcYm;
            if (StringUtils.length(rehcYm) == 6) {
                returnYearMonth = DateUtility.changeWestYearMonthType(rehcYm);
            }

            return StringUtils.substring(returnYearMonth, 0, 3) + "年" + StringUtils.substring(returnYearMonth, 3, 5) + "月";
        }
        else {
            return "　 年　月";
        }
    }

    /**
     * 重新查核失能程度註記 (XXX年XX月) 的部份
     * 
     * @return
     */
    public String getRehcYmStr() {
        if (StringUtils.isNotBlank(rehcYm)) {
            String returnYearMonth = rehcYm;
            if (StringUtils.length(rehcYm) == 6) {
                returnYearMonth = DateUtility.changeWestYearMonthType(rehcYm);
            }
            return returnYearMonth;
        }
        return rehcYm;
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
        ocaccIdentMk = ""; // 符合第20條之1註記
        prType = ""; // 先核普通
        loanMk = ""; // 不須抵銷紓困貸款註記
        cbxSameAsApply = ""; // 同申請單位 Checkbox
        monNotifyingMk = "";  // 寄發月通知表

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
        evtJobDate = ""; // 事故者離職日期 - 診斷失能日期
        evtDieDate = ""; // 事故者死亡日期
        appDate = ""; // 申請日期
        apUbno = ""; // 申請單位保險證號
        apubnock = ""; // 申請單位保險證號檢查碼
        lsUbno = ""; // 最後單位保險證號 - 事故發生單位保險證號
        lsUbnoCk = ""; // 最後單位保險證號檢查碼
        notifyForm = ""; // 核定通知書格式
        cutAmt = null; // 扣減 / 補償總金額 - 年金應扣金額
        loanMk = ""; // 不須抵銷紓困貸款註記
        closeCause = ""; // 結案原因
        choiceYm = ""; // 擇領起月

        evTyp = ""; // 傷病分類
        disQualMk = ""; // 年金請領資格
        evCode = ""; // 傷病原因
        criInPart1 = ""; // 受傷部位1
        criInPart2 = ""; // 受傷部位2
        criInPart3 = ""; // 受傷部位3
        criMedium = ""; // 媒介物
        criInIssul = ""; // 核定等級
        criInJcl1 = ""; // 失能等級1
        criInJcl2 = ""; // 失能等級2
        criInJcl3 = ""; // 失能等級3
        criInJdp1 = ""; // 失能項目1
        criInJdp2 = ""; // 失能項目2
        criInJdp3 = ""; // 失能項目3
        criInJdp4 = ""; // 失能項目4
        criInJdp5 = ""; // 失能項目5
        criInJdp6 = ""; // 失能項目6
        criInJdp7 = ""; // 失能項目7
        criInJdp8 = ""; // 失能項目8
        criInJdp9 = ""; // 失能項目9
        criInJdp10 = ""; // 失能項目10
        hosId = ""; // 醫療院所代碼
        doctorName1 = ""; // 醫師姓名1
        doctorName2 = ""; // 醫師姓名2
        ocAccHosId = ""; // 職病醫療院所代碼
        ocAccDoctorName1 = ""; // 職病醫師姓名1
        ocAccDoctorName2 = ""; // 職病醫師姓名2        
        criInJnme1 = ""; // 國際疾病代碼1
        criInJnme2 = ""; // 國際疾病代碼2
        criInJnme3 = ""; // 國際疾病代碼3
        criInJnme4 = ""; // 國際疾病代碼4
        rehcMk = ""; // 重新查核失能程度註記
        ocaccIdentMk = ""; // 符合第20條之1註記
        prType = ""; // 先核普通
        ocAccaddAmt = null; // 己領職災增給金額
        deductDay = null; // 扣除天數
        monNotifyingMk = "";  // 寄發月通知表

        name = ""; // 戶籍姓名

        hosName = ""; // 醫療院所名稱
        ocAccHosName = ""; // 職病醫療院所名稱
        // cbxRehcMk = ""; // 重新查核失能程度註記 (input - Checkbox)
        rehcYear = ""; // 重新查核失能程度註記 - 年 (input - Text)
        rehcMonth = ""; // 重新查核失能程度註記 - 月 (input - Text)
        tmpEvtNationCode = ""; // 事故者國籍代碼 (input - 下拉選單)

        oldEvtNationTpe = ""; // 事故者國籍別
        oldEvtNationCode = ""; // 事故者國籍
        oldEvtSex = ""; // 事故者性別
        oldEvtName = ""; // 事故者姓名
        oldEvtIdnNo = ""; // 事故者身分證號
        oldEvtBrDate = ""; // 事故者出生日期
        oldEvtJobDate = ""; // 事故者離職日期 - 診斷失能日期
        oldEvtDieDate = ""; // 事故者死亡日期
        oldAppDate = ""; // 申請日期
        oldApUbno = ""; // 申請單位保險證號
        oldApubnock = ""; // 申請單位保險證號檢查碼
        oldLsUbno = ""; // 最後單位保險證號 - 事故發生單位保險證號
        oldLsUbnoCk = ""; // 最後單位保險證號檢查碼
        oldNotifyForm = ""; // 核定通知書格式
        oldCutAmt = null; // 扣減 / 補償總金額 - 年金應扣金額
        oldLoanMk = ""; // 不須抵銷紓困貸款註記
        oldCloseCause = ""; // 結案原因
        oldChoiceYm = ""; // 擇領起月

        oldEvTyp = ""; // 傷病分類
        oldDisQualMk = ""; // 年金請領資格
        oldEvCode = ""; // 傷病原因
        oldCriInPart1 = ""; // 受傷部位1
        oldCriInPart2 = ""; // 受傷部位2
        oldCriInPart3 = ""; // 受傷部位3
        oldCriMedium = ""; // 媒介物
        oldCriInIssul = ""; // 核定等級
        oldCriInJcl1 = ""; // 失能等級1
        oldCriInJcl2 = ""; // 失能等級2
        oldCriInJcl3 = ""; // 失能等級3
        oldCriInJdp1 = ""; // 失能項目1
        oldCriInJdp2 = ""; // 失能項目2
        oldCriInJdp3 = ""; // 失能項目3
        oldCriInJdp4 = ""; // 失能項目4
        oldCriInJdp5 = ""; // 失能項目5
        oldCriInJdp6 = ""; // 失能項目6
        oldCriInJdp7 = ""; // 失能項目7
        oldCriInJdp8 = ""; // 失能項目8
        oldCriInJdp9 = ""; // 失能項目9
        oldCriInJdp10 = ""; // 失能項目10
        oldHosId = ""; // 醫療院所代碼
        oldDoctorName1 = ""; // 醫師姓名1
        oldDoctorName2 = ""; // 醫師姓名2
        oldOcAccHosId = ""; // 職病醫療院所代碼
        oldOcAccDoctorName1 = ""; // 職病醫師姓名1
        oldOcAccDoctorName2 = ""; // 職病醫師姓名2        
        oldCriInJnme1 = ""; // 國際疾病代碼1
        oldCriInJnme2 = ""; // 國際疾病代碼2
        oldCriInJnme3 = ""; // 國際疾病代碼3
        oldCriInJnme4 = ""; // 國際疾病代碼4
        oldRehcMk = ""; // 重新查核失能程度註記
        oldOcaccIdentMk = ""; // 符合第20條之1註記
        oldPrType = ""; // 先核普通
        oldOcAccaddAmt = null; // 己領職災增給金額
        oldDeductDay = null; // 扣除天數
        oldMonNotifyingMk = "";  // 寄發月通知表

        // oldCbxRehcMk = ""; // 重新查核失能程度註記 (input - Checkbox)
        oldRehcYear = ""; // 重新查核失能程度註記 - 年 (input - Text)
        oldRehcMonth = ""; // 重新查核失能程度註記 - 月 (input - Text)
        oldTmpEvtNationCode = ""; // 事故者國籍代碼 (input - 下拉選單)

        rehcYm = ""; // 重新查核失能程度註記 (XXX年XX月) 的部份

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

    public String getEvtJobDate() {
        return evtJobDate;
    }

    public void setEvtJobDate(String evtJobDate) {
        this.evtJobDate = evtJobDate;
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

    public BigDecimal getCutAmt() {
        return cutAmt;
    }

    public void setCutAmt(BigDecimal cutAmt) {
        this.cutAmt = cutAmt;
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

    public BigDecimal getBaappexpandId() {
        return baappexpandId;
    }

    public void setBaappexpandId(BigDecimal baappexpandId) {
        this.baappexpandId = baappexpandId;
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

    public String getCriInIssul() {
        return criInIssul;
    }

    public void setCriInIssul(String criInIssul) {
        this.criInIssul = criInIssul;
    }

    public String getCriInJcl1() {
        return criInJcl1;
    }

    public void setCriInJcl1(String criInJcl1) {
        this.criInJcl1 = criInJcl1;
    }

    public String getCriInJcl2() {
        return criInJcl2;
    }

    public void setCriInJcl2(String criInJcl2) {
        this.criInJcl2 = criInJcl2;
    }

    public String getCriInJcl3() {
        return criInJcl3;
    }

    public void setCriInJcl3(String criInJcl3) {
        this.criInJcl3 = criInJcl3;
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
    
    public String getOcAccHosId() {
        return ocAccHosId;
    }

    public void setOcAccHosId(String ocAccHosId) {
        this.ocAccHosId = ocAccHosId;
    }

    public String getOcAccDoctorName1() {
        return ocAccDoctorName1;
    }

    public void setOcAccDoctorName1(String ocAccDoctorName1) {
        this.ocAccDoctorName1 = ocAccDoctorName1;
    }

    public String getOcAccDoctorName2() {
        return ocAccDoctorName2;
    }

    public void setOcAccDoctorName2(String ocAccDoctorName2) {
        this.ocAccDoctorName2 = ocAccDoctorName2;
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

    public String getRehcMk() {
        return rehcMk;
    }

    public void setRehcMk(String rehcMk) {
        this.rehcMk = rehcMk;
    }

    public String getOcaccIdentMk() {
        return ocaccIdentMk;
    }

    public void setOcaccIdentMk(String ocaccIdentMk) {
        this.ocaccIdentMk = ocaccIdentMk;
    }

    public String getPrType() {
        return prType;
    }

    public void setPrType(String prType) {
        this.prType = prType;
    }

    public BigDecimal getOcAccaddAmt() {
        return ocAccaddAmt;
    }

    public void setOcAccaddAmt(BigDecimal ocAccaddAmt) {
        this.ocAccaddAmt = ocAccaddAmt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*
     * public String getCbxRehcMk() { return cbxRehcMk; } public void setCbxRehcMk(String cbxRehcMk) { this.cbxRehcMk = cbxRehcMk; }
     */

    public String getRehcYear() {
        return rehcYear;
    }

    public void setRehcYear(String rehcYear) {
        this.rehcYear = rehcYear;
    }

    public String getRehcMonth() {
        return rehcMonth;
    }

    public void setRehcMonth(String rehcMonth) {
        this.rehcMonth = rehcMonth;
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

    public String getOldEvtJobDate() {
        return oldEvtJobDate;
    }

    public void setOldEvtJobDate(String oldEvtJobDate) {
        this.oldEvtJobDate = oldEvtJobDate;
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

    public BigDecimal getOldCutAmt() {
        return oldCutAmt;
    }

    public void setOldCutAmt(BigDecimal oldCutAmt) {
        this.oldCutAmt = oldCutAmt;
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

    public String getOldCriInIssul() {
        return oldCriInIssul;
    }

    public void setOldCriInIssul(String oldCriInIssul) {
        this.oldCriInIssul = oldCriInIssul;
    }

    public String getOldCriInJcl1() {
        return oldCriInJcl1;
    }

    public void setOldCriInJcl1(String oldCriInJcl1) {
        this.oldCriInJcl1 = oldCriInJcl1;
    }

    public String getOldCriInJcl2() {
        return oldCriInJcl2;
    }

    public void setOldCriInJcl2(String oldCriInJcl2) {
        this.oldCriInJcl2 = oldCriInJcl2;
    }

    public String getOldCriInJcl3() {
        return oldCriInJcl3;
    }

    public void setOldCriInJcl3(String oldCriInJcl3) {
        this.oldCriInJcl3 = oldCriInJcl3;
    }

    public String getOldCriInJdp1() {
        return oldCriInJdp1;
    }

    public void setOldCriInJdp1(String oldCriInJdp1) {
        this.oldCriInJdp1 = oldCriInJdp1;
    }

    public String getOldCriInJdp2() {
        return oldCriInJdp2;
    }

    public void setOldCriInJdp2(String oldCriInJdp2) {
        this.oldCriInJdp2 = oldCriInJdp2;
    }

    public String getOldCriInJdp3() {
        return oldCriInJdp3;
    }

    public void setOldCriInJdp3(String oldCriInJdp3) {
        this.oldCriInJdp3 = oldCriInJdp3;
    }

    public String getOldCriInJdp4() {
        return oldCriInJdp4;
    }

    public void setOldCriInJdp4(String oldCriInJdp4) {
        this.oldCriInJdp4 = oldCriInJdp4;
    }

    public String getOldCriInJdp5() {
        return oldCriInJdp5;
    }

    public void setOldCriInJdp5(String oldCriInJdp5) {
        this.oldCriInJdp5 = oldCriInJdp5;
    }

    public String getOldCriInJdp6() {
        return oldCriInJdp6;
    }

    public void setOldCriInJdp6(String oldCriInJdp6) {
        this.oldCriInJdp6 = oldCriInJdp6;
    }

    public String getOldCriInJdp7() {
        return oldCriInJdp7;
    }

    public void setOldCriInJdp7(String oldCriInJdp7) {
        this.oldCriInJdp7 = oldCriInJdp7;
    }

    public String getOldCriInJdp8() {
        return oldCriInJdp8;
    }

    public void setOldCriInJdp8(String oldCriInJdp8) {
        this.oldCriInJdp8 = oldCriInJdp8;
    }

    public String getOldCriInJdp9() {
        return oldCriInJdp9;
    }

    public void setOldCriInJdp9(String oldCriInJdp9) {
        this.oldCriInJdp9 = oldCriInJdp9;
    }

    public String getOldCriInJdp10() {
        return oldCriInJdp10;
    }

    public void setOldCriInJdp10(String oldCriInJdp10) {
        this.oldCriInJdp10 = oldCriInJdp10;
    }

    public String getOldHosId() {
        return oldHosId;
    }

    public void setOldHosId(String oldHosId) {
        this.oldHosId = oldHosId;
    }

    public String getOldDoctorName1() {
        return oldDoctorName1;
    }

    public void setOldDoctorName1(String oldDoctorName1) {
        this.oldDoctorName1 = oldDoctorName1;
    }

    public String getOldDoctorName2() {
        return oldDoctorName2;
    }

    public void setOldDoctorName2(String oldDoctorName2) {
        this.oldDoctorName2 = oldDoctorName2;
    }

    public String getOldOcAccHosId() {
        return oldOcAccHosId;
    }

    public void setOldOcAccHosId(String oldOcAccHosId) {
        this.oldOcAccHosId = oldOcAccHosId;
    }

    public String getOldOcAccDoctorName1() {
        return oldOcAccDoctorName1;
    }

    public void setOldOcAccDoctorName1(String oldOcAccDoctorName1) {
        this.oldOcAccDoctorName1 = oldOcAccDoctorName1;
    }

    public String getOldOcAccDoctorName2() {
        return oldOcAccDoctorName2;
    }

    public void setOldOcAccDoctorName2(String oldOcAccDoctorName2) {
        this.oldOcAccDoctorName2 = oldOcAccDoctorName2;
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

    public String getOldRehcMk() {
        return oldRehcMk;
    }

    public void setOldRehcMk(String oldRehcMk) {
        this.oldRehcMk = oldRehcMk;
    }

    public String getOldOcaccIdentMk() {
        return oldOcaccIdentMk;
    }

    public void setOldOcaccIdentMk(String oldOcaccIdentMk) {
        this.oldOcaccIdentMk = oldOcaccIdentMk;
    }

    public String getOldPrType() {
        return oldPrType;
    }

    public void setOldPrType(String oldPrType) {
        this.oldPrType = oldPrType;
    }

    public BigDecimal getOldOcAccaddAmt() {
        return oldOcAccaddAmt;
    }

    public void setOldOcAccaddAmt(BigDecimal oldOcAccaddAmt) {
        this.oldOcAccaddAmt = oldOcAccaddAmt;
    }

    /*
     * public String getOldCbxRehcMk() { return oldCbxRehcMk; } public void setOldCbxRehcMk(String oldCbxRehcMk) { this.oldCbxRehcMk = oldCbxRehcMk; }
     */

    public String getOldRehcYear() {
        return oldRehcYear;
    }

    public void setOldRehcYear(String oldRehcYear) {
        this.oldRehcYear = oldRehcYear;
    }

    public String getOldRehcMonth() {
        return oldRehcMonth;
    }

    public void setOldRehcMonth(String oldRehcMonth) {
        this.oldRehcMonth = oldRehcMonth;
    }

    public String getOldTmpEvtNationCode() {
        return oldTmpEvtNationCode;
    }

    public void setOldTmpEvtNationCode(String oldTmpEvtNationCode) {
        this.oldTmpEvtNationCode = oldTmpEvtNationCode;
    }

    public String getHosName() {
        return hosName;
    }

    public void setHosName(String hosName) {
        this.hosName = hosName;
    }
    
    public String getOcAccHosName() {
        return ocAccHosName;
    }

    public void setOcAccHosName(String ocAccHosName) {
        this.ocAccHosName = ocAccHosName;
    }    

    public List<Bacountry> getCountryOptionList() {
        return countryOptionList;
    }

    public void setCountryOptionList(List<Bacountry> countryOptionList) {
        this.countryOptionList = countryOptionList;
    }

    public List<Baparam> getEvTypOptionList() {
        return evTypOptionList;
    }

    public void setEvTypOptionList(List<Baparam> evTypOptionList) {
        this.evTypOptionList = evTypOptionList;
    }

    public List<Baparam> getReChkStatusOptionList() {
		return reChkStatusOptionList;
	}

	public void setReChkStatusOptionList(List<Baparam> reChkStatusOptionList) {
		this.reChkStatusOptionList = reChkStatusOptionList;
	}

	public List<Baparam> getReChkResult1OptionList() {
		return reChkResult1OptionList;
	}

	public void setReChkResult1OptionList(List<Baparam> reChkResult1OptionList) {
		this.reChkResult1OptionList = reChkResult1OptionList;
	}

	public List<Baparam> getReChkResult2OptionList() {
		return reChkResult2OptionList;
	}

	public void setReChkResult2OptionList(List<Baparam> reChkResult2OptionList) {
		this.reChkResult2OptionList = reChkResult2OptionList;
	}

	public List<Baparam> getKcloseCauseOptionList() {
        return kcloseCauseOptionList;
    }

    public void setKcloseCauseOptionList(List<Baparam> kcloseCauseOptionList) {
        this.kcloseCauseOptionList = kcloseCauseOptionList;
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

    public String getRehcYm() {
        return rehcYm;
    }

    public void setRehcYm(String rehcYm) {
        this.rehcYm = rehcYm;
    }

    public String getCbxSameAsApply() {
        return cbxSameAsApply;
    }

    public void setCbxSameAsApply(String cbxSameAsApply) {
        this.cbxSameAsApply = cbxSameAsApply;
    }

    public BigDecimal getDeductDay() {
        return deductDay;
    }

    public void setDeductDay(BigDecimal deductDay) {
        this.deductDay = deductDay;
    }

    public BigDecimal getOldDeductDay() {
        return oldDeductDay;
    }

    public void setOldDeductDay(BigDecimal oldDeductDay) {
        this.oldDeductDay = oldDeductDay;
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

    public String getComSeniority() {
        return comSeniority;
    }

    public void setComSeniority(String comSeniority) {
        this.comSeniority = comSeniority;
    }

    public boolean isInitialed() {
        return initialed;
    }

    public void setInitialed(boolean initialed) {
        this.initialed = initialed;
    }

    public String getComSeniorityCk() {
        return comSeniorityCk;
    }

    public void setComSeniorityCk(String comSeniorityCk) {
        this.comSeniorityCk = comSeniorityCk;
    }

	public String getDisQualMk() {
		return disQualMk;
	}

	public void setDisQualMk(String disQualMk) {
		this.disQualMk = disQualMk;
	}

	public String getOldDisQualMk() {
		return oldDisQualMk;
	}

	public void setOldDisQualMk(String oldDisQualMk) {
		this.oldDisQualMk = oldDisQualMk;
	}

	public List<Baparam> getDisQualMkOptionList() {
		return disQualMkOptionList;
	}

	public void setDisQualMkOptionList(List<Baparam> disQualMkOptionList) {
		this.disQualMkOptionList = disQualMkOptionList;
	}

	public String getReChkYm() {
		return reChkYm;
	}

	public void setReChkYm(String reChkYm) {
		this.reChkYm = reChkYm;
	}

	public String getIsreChk() {
		return isreChk;
	}

	public void setIsreChk(String isreChk) {
		this.isreChk = isreChk;
	}

	public String getReChkStatus() {
		return reChkStatus;
	}

	public void setReChkStatus(String reChkStatus) {
		this.reChkStatus = reChkStatus;
	}

	public String getComReChkYm() {
		return comReChkYm;
	}

	public void setComReChkYm(String comReChkYm) {
		this.comReChkYm = comReChkYm;
	}

	public String getOldIsreChk() {
		return oldIsreChk;
	}

	public void setOldIsreChk(String oldIsreChk) {
		this.oldIsreChk = oldIsreChk;
	}

	public String getOldReChkStatus() {
		return oldReChkStatus;
	}

	public void setOldReChkStatus(String oldReChkStatus) {
		this.oldReChkStatus = oldReChkStatus;
	}

	public String getOldReChkResult1() {
		return oldReChkResult1;
	}

	public void setOldReChkResult1(String oldReChkResult1) {
		this.oldReChkResult1 = oldReChkResult1;
	}

	public String getOldReChkResult2() {
		return oldReChkResult2;
	}

	public void setOldReChkResult2(String oldReChkResult2) {
		this.oldReChkResult2 = oldReChkResult2;
	}

	public String getReChkResult1() {
		return reChkResult1;
	}

	public void setReChkResult1(String reChkResult1) {
		this.reChkResult1 = reChkResult1;
	}

	public String getReChkResult2() {
		return reChkResult2;
	}

	public void setReChkResult2(String reChkResult2) {
		this.reChkResult2 = reChkResult2;
	}

	public String getMapNo() {
		return mapNo;
	}

	public void setMapNo(String mapNo) {
		this.mapNo = mapNo;
	}

	public String getOldMapNo() {
		return oldMapNo;
	}

	public void setOldMapNo(String oldMapNo) {
		this.oldMapNo = oldMapNo;
	}

	public String getOldComReChkYm() {
		return oldComReChkYm;
	}

	public void setOldComReChkYm(String oldComReChkYm) {
		this.oldComReChkYm = oldComReChkYm;
	}

	public String getOldProcStat() {
		return oldProcStat;
	}

	public void setOldProcStat(String oldProcStat) {
		this.oldProcStat = oldProcStat;
	}

	public String getOldOrType() {
		return oldOrType;
	}

	public void setOldOrType(String oldOrType) {
		this.oldOrType = oldOrType;
	}

	public String getOldRvAppTyp() {
		return oldRvAppTyp;
	}

	public void setOldRvAppTyp(String oldRvAppTyp) {
		this.oldRvAppTyp = oldRvAppTyp;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public String getOldHosName() {
		return oldHosName;
	}

	public void setOldHosName(String oldHosName) {
		this.oldHosName = oldHosName;
	}
	
    public String getOldOcAccHosName() {
        return oldOcAccHosName;
    }

    public void setOldOcAccHosName(String oldOcAccHosName) {
        this.oldOcAccHosName = oldOcAccHosName;
    }	

	public String getOldRehcYm() {
		return oldRehcYm;
	}

	public void setOldRehcYm(String oldRehcYm) {
		this.oldRehcYm = oldRehcYm;
	}

	public String getOldCbxSameAsApply() {
		return oldCbxSameAsApply;
	}

	public void setOldCbxSameAsApply(String oldCbxSameAsApply) {
		this.oldCbxSameAsApply = oldCbxSameAsApply;
	}
	
    public String getMonNotifyingMk() {
        return monNotifyingMk;
    }

    public void setMonNotifyingMk(String monNotifyingMk) {
        this.monNotifyingMk = monNotifyingMk;
    }   	
	
    public String getOldMonNotifyingMk() {
        return oldMonNotifyingMk;
    }

    public void setOldMonNotifyingMk(String oldMonNotifyingMk) {
        this.oldMonNotifyingMk = oldMonNotifyingMk;
    }	

}
