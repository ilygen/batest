package tw.gov.bli.ba.update.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 失能年金案件資料更正
 * 
 * @author Goston
 */
public class DisabledApplicationDataUpdateCase implements Serializable {
    // BAAPPBASE
    private BigDecimal baappbaseId; // 資料列編號
    private String apNo; // 受理編號
    private String seqNo; // 序號
    private String payKind;// 給付種類
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
    private String dupeIdnNoMk; // 身分證重號註記
    private String interValMonth;// 發放方式
    private String mapNo;// 相關受理編號
    private String sysCode;// 系統類別
    private String apnoFm;// 來源受理編號

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
    private String criInJdpStr;// 所有失能項目字串
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
    private String rehcYm; // 重新查核失能程度年月
    private String ocaccIdentMk; // 符合第20條之1註記
    private String prType; // 先核普通
    private BigDecimal ocAccaddAmt; // 己領職災增給金額
    private BigDecimal deductDay; // 扣除天數
    private String evAppTyp;// 申請傷病分類
    private String comnpMk; // 併計國保年資
    private String disQualMk; //年金請領資格
    private String rehcOpen; //是否開啟 重新查核失能程度註記 按鈕
    private String monNotifyingMk;  // 寄發月通知表
    
    // CVLDTL
    private String name; // 戶籍姓名

    // OTHER
    private String hosName; // 醫療院所名稱
    private String ocAccHosName; // 職病醫療院所名稱
    private boolean showCancelButton; // 是否顯示 註銷 按鍵
    private boolean canModifyEvtName; // 事故者姓名 可否修改
    private String isShowInterValMonth;// 是否顯示發放方式

    // BADUPEIDN - 身分證重號資料
    private List<DisabledApplicationDataUpdateDupeIdnCase> dupeIdnList;

    // BACHKFILE - 編審註記資料
    private List<DisabledApplicationDataUpdateCheckFileCase> checkFileList;

    // BADAPR
    private Integer benCount; // 非本人的受款人筆數
    private Integer benCountAfterCheck; // 月核後受款人筆數
    
    private String comSeniority;//

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
    public void addDupeIdnData(DisabledApplicationDataUpdateDupeIdnCase dupeIdnData) {
        if (dupeIdnList == null)
            dupeIdnList = new ArrayList<DisabledApplicationDataUpdateDupeIdnCase>();

        dupeIdnList.add(dupeIdnData);
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

    /**
     * 取得 編審註記資料 表示字串
     * 
     * @return
     */
    public List<List<DisabledApplicationDataUpdateCheckFileCase>> getCheckFileData() {
        List<List<DisabledApplicationDataUpdateCheckFileCase>> returnList = new ArrayList<List<DisabledApplicationDataUpdateCheckFileCase>>();

        if (checkFileList != null && checkFileList.size() > 0) { // begin ... [
            List<DisabledApplicationDataUpdateCheckFileCase> caseList = new ArrayList<DisabledApplicationDataUpdateCheckFileCase>();

            String previousPayYm = "";

            for (int i = 0; i < checkFileList.size(); i++) { // begin ... [
                DisabledApplicationDataUpdateCheckFileCase checkFileData = checkFileList.get(i);

                if (i == 0)
                    previousPayYm = StringUtils.defaultString(checkFileData.getPayYm());

                if (!StringUtils.equals(checkFileData.getPayYm(), previousPayYm)) { // 給付年月和前一筆不同
                    // 將資料加入 returnList
                    returnList.add(caseList);

                    // 初始化一個新的 List
                    caseList = new ArrayList<DisabledApplicationDataUpdateCheckFileCase>();

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
            for (DisabledApplicationDataUpdateCheckFileCase checkFileData : checkFileList) {
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
            for (DisabledApplicationDataUpdateCheckFileCase checkFileData : checkFileList) {
                if (StringUtils.equalsIgnoreCase(checkFileData.getChkFileCode(), "LS"))
                    return true;
            }
        }

        return false;
    }

    /**
     * 編審註記資料 是否有 3R 註記
     * 
     * @return
     */
    public boolean isContainCheckMark3R() {
        if (checkFileList != null && checkFileList.size() > 0) {
            for (DisabledApplicationDataUpdateCheckFileCase checkFileData : checkFileList) {
                if (StringUtils.equalsIgnoreCase(checkFileData.getChkFileCode(), "3R"))
                    return true;
            }
        }

        return false;
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

    public DisabledApplicationDataUpdateCase() {
        dupeIdnList = new ArrayList<DisabledApplicationDataUpdateDupeIdnCase>();
        checkFileList = new ArrayList<DisabledApplicationDataUpdateCheckFileCase>();
        showCancelButton = false;
        canModifyEvtName = false;
    }

    public String getCriInJdpStr() {
    	criInJdpStr = criInJdp1+" "+criInJdp2+" "+criInJdp3+" "+criInJdp4+" "+criInJdp5+" "+criInJdp6+" "+criInJdp7+" "+criInJdp8+" "+criInJdp9+" "+criInJdp10;
		return criInJdpStr;
	}

	public void setCriInJdpStr(String criInJdpStr) {
		this.criInJdpStr = criInJdpStr;
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

    public String getRehcYm() {
        return rehcYm;
    }

    public void setRehcYm(String rehcYm) {
        this.rehcYm = rehcYm;
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

    public List<DisabledApplicationDataUpdateDupeIdnCase> getDupeIdnList() {
        return dupeIdnList;
    }

    public void setDupeIdnList(List<DisabledApplicationDataUpdateDupeIdnCase> dupeIdnList) {
        this.dupeIdnList = dupeIdnList;
    }

    public List<DisabledApplicationDataUpdateCheckFileCase> getCheckFileList() {
        return checkFileList;
    }

    public void setCheckFileList(List<DisabledApplicationDataUpdateCheckFileCase> checkFileList) {
        this.checkFileList = checkFileList;
    }

    public String getDupeIdnNoMk() {
        return dupeIdnNoMk;
    }

    public void setDupeIdnNoMk(String dupeIdnNoMk) {
        this.dupeIdnNoMk = dupeIdnNoMk;
    }

    public BigDecimal getDeductDay() {
        return deductDay;
    }

    public void setDeductDay(BigDecimal deductDay) {
        this.deductDay = deductDay;
    }

    public Integer getBenCount() {
        return benCount;
    }

    public void setBenCount(Integer benCount) {
        this.benCount = benCount;
    }

    public Integer getBenCountAfterCheck() {
        return benCountAfterCheck;
    }

    public void setBenCountAfterCheck(Integer benCountAfterCheck) {
        this.benCountAfterCheck = benCountAfterCheck;
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

    public String getMapNo() {
        return mapNo;
    }

    public void setMapNo(String mapNo) {
        this.mapNo = mapNo;
    }

    public String getComnpMk() {
        return comnpMk;
    }

    public void setComnpMk(String comnpMk) {
        this.comnpMk = comnpMk;
    }

    public String getComSeniority() {
        return comSeniority;
    }

    public void setComSeniority(String comSeniority) {
        this.comSeniority = comSeniority;
    }

    public String getPayKind() {
        return payKind;
    }

    public void setPayKind(String payKind) {
        this.payKind = payKind;
    }

	public String getDisQualMk() {
		return disQualMk;
	}

	public void setDisQualMk(String disQualMk) {
		this.disQualMk = disQualMk;
	}

	public String getRehcOpen() {
		return rehcOpen;
	}

	public void setRehcOpen(String rehcOpen) {
		this.rehcOpen = rehcOpen;
	}
	
    public String getMonNotifyingMk() {
        return monNotifyingMk;
    }

    public void setMonNotifyingMk(String monNotifyingMk) {
        this.monNotifyingMk = monNotifyingMk;
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
