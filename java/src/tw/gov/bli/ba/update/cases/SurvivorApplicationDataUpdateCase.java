package tw.gov.bli.ba.update.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;

/**
 * Case for 遺屬年金案件資料更正
 * 
 * @author Goston
 */
public class SurvivorApplicationDataUpdateCase implements Serializable {
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
    private String sysCode;// 系統類別
    private String apnoFm;// 來源受理編號

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
    private String evAppTyp; // 傷病分類
    private String monNotifyingMk; //寄發月通知表

    // CVLDTL
    private String name; // 戶籍姓名

    // OTHER
    private boolean showCancelButton; // 是否顯示 註銷 按鍵
    private boolean canModifyEvtName; // 事故者姓名 可否修改
    private String isShowInterValMonth;//是否顯示發放方式

    // BADUPEIDN - 身分證重號資料
    private List<SurvivorApplicationDataUpdateDupeIdnCase> dupeIdnList;

    // BACHKFILE - 編審註記資料
    private List<SurvivorApplicationDataUpdateCheckFileCase> checkFileList;

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
     * 加入一筆 身分證重號資料 到 dupeIdnList
     * 
     * @param dupeIdnData
     */
    public void addDupeIdnData(SurvivorApplicationDataUpdateDupeIdnCase dupeIdnData) {
        if (dupeIdnList == null)
            dupeIdnList = new ArrayList<SurvivorApplicationDataUpdateDupeIdnCase>();

        dupeIdnList.add(dupeIdnData);
    }

    /**
     * 加入一筆 編審註記資料 到 checkFileList
     * 
     * @param checkFileData
     */
    public void addCheckFileData(SurvivorApplicationDataUpdateCheckFileCase checkFileData) {
        if (checkFileList == null)
            checkFileList = new ArrayList<SurvivorApplicationDataUpdateCheckFileCase>();

        checkFileList.add(checkFileData);
    }

    /**
     * 取得 編審註記資料 表示字串
     * 
     * @return
     */
    public List<List<SurvivorApplicationDataUpdateCheckFileCase>> getCheckFileData() {
        List<List<SurvivorApplicationDataUpdateCheckFileCase>> returnList = new ArrayList<List<SurvivorApplicationDataUpdateCheckFileCase>>();

        if (checkFileList != null && checkFileList.size() > 0) { // begin ... [
            List<SurvivorApplicationDataUpdateCheckFileCase> caseList = new ArrayList<SurvivorApplicationDataUpdateCheckFileCase>();

            String previousPayYm = "";

            for (int i = 0; i < checkFileList.size(); i++) { // begin ... [
                SurvivorApplicationDataUpdateCheckFileCase checkFileData = checkFileList.get(i);

                if (i == 0)
                    previousPayYm = StringUtils.defaultString(checkFileData.getPayYm());

                if (!StringUtils.equals(checkFileData.getPayYm(), previousPayYm)) { // 給付年月和前一筆不同
                    // 將資料加入 returnList
                    returnList.add(caseList);

                    // 初始化一個新的 List
                    caseList = new ArrayList<SurvivorApplicationDataUpdateCheckFileCase>();

                    previousPayYm = StringUtils.defaultString(checkFileData.getPayYm());
                }

                caseList.add(checkFileData);

                if (i == checkFileList.size() - 1) {
                    // 將資料加入 returnList
                    returnList.add(caseList);
                }
            } // ] ... end for (int i = 0; i < checkFileList.size(); i++)
        } // ] ... end if (checkFileList != null && checkFileList.size() > 0)

        return returnList;
    }

    /**
     * 編審註記資料 是否有 BD 註記
     * 
     * @return
     */
    public boolean isContainCheckMarkBD() {
        if (checkFileList != null && checkFileList.size() > 0) {
            for (SurvivorApplicationDataUpdateCheckFileCase checkFileData : checkFileList) {
                if (StringUtils.equalsIgnoreCase(checkFileData.getChkFileCode(), "BD"))
                    return true;
            }
        }

        return false;
    }

    /**
     * 編審註記資料 是否有 LS 註記
     * 
     * @return
     */
    public boolean isContainCheckMarkLS() {
        if (checkFileList != null && checkFileList.size() > 0) {
            for (SurvivorApplicationDataUpdateCheckFileCase checkFileData : checkFileList) {
                if (StringUtils.equalsIgnoreCase(checkFileData.getChkFileCode(), "LS"))
                    return true;
            }
        }

        return false;
    }

    public SurvivorApplicationDataUpdateCase() {
        dupeIdnList = new ArrayList<SurvivorApplicationDataUpdateDupeIdnCase>();
        checkFileList = new ArrayList<SurvivorApplicationDataUpdateCheckFileCase>();
        showCancelButton = false;
        canModifyEvtName = false;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isShowCancelButton() {
        return showCancelButton;
    }

    public void setShowCancelButton(boolean showCancelButton) {
        this.showCancelButton = showCancelButton;
    }

    public boolean isCanModifyEvtName() {
        return canModifyEvtName;
    }

    public void setCanModifyEvtName(boolean canModifyEvtName) {
        this.canModifyEvtName = canModifyEvtName;
    }

    public List<SurvivorApplicationDataUpdateDupeIdnCase> getDupeIdnList() {
        return dupeIdnList;
    }

    public void setDupeIdnList(List<SurvivorApplicationDataUpdateDupeIdnCase> dupeIdnList) {
        this.dupeIdnList = dupeIdnList;
    }

    public List<SurvivorApplicationDataUpdateCheckFileCase> getCheckFileList() {
        return checkFileList;
    }

    public void setCheckFileList(List<SurvivorApplicationDataUpdateCheckFileCase> checkFileList) {
        this.checkFileList = checkFileList;
    }

    public BigDecimal getCutAmt() {
        return cutAmt;
    }

    public void setCutAmt(BigDecimal cutAmt) {
        this.cutAmt = cutAmt;
    }

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

    public String getEvAppTyp() {
        return evAppTyp;
    }

    public void setEvAppTyp(String evAppTyp) {
        this.evAppTyp = evAppTyp;
    }
    
    public String getMonNotifyingMk() {
        return monNotifyingMk;
    }

    public void setMonNotifyingMk(String monNotifyingMk) {
        this.monNotifyingMk = monNotifyingMk;
    }
    
    public String getIssuNotifyingMk() {
        return issuNotifyingMk;
    }

    public void setIssuNotifyingMk(String issuNotifyingMk) {
        this.issuNotifyingMk = issuNotifyingMk;
    }
    
    public String getCaseTyp() {
        return caseTyp;
    }

    public void setCaseTyp(String caseTyp) {
        this.caseTyp = caseTyp;
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
