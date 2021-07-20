package tw.gov.bli.ba.query.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 給付查詢作業 複檢費用資料明細
 * 
 * @author Rickychi
 */
public class PaymentQueryReFeesDetailDataCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
    private String reApNo; // 複檢費用受理編號
    private BigDecimal apSeq; // 複檢費用申請序
    private String apNo; // 受理編號
    private String procStat; // 處理狀態
    private String inreDate; // 通知複檢日期
    private String reasCode; // 複檢原因
    private String hosId; // 醫療院所代碼
    private String recosDate; // 複檢費用申請日期
    private BigDecimal reNum; // 複檢門診次數
    private BigDecimal rehpStay; // 複檢住院天數
    private BigDecimal reFees; // 複檢費用
    private BigDecimal nonreFees; // 非複檢必須費用
    private BigDecimal reAmtPay; // 複檢實付金額
    private String notifyForm; // 核定通知書格式
    private String benIdnNo; // 受益人身分證號
    private String benName; // 受益人姓名
    private String benBrDate; // 受益人出生日期
    private String benSex; // 受益人性別
    private String benNationTyp; // 受益人國籍別
    private String benNationCode; // 受益人國籍
    private String benNationName; // 受益人國籍名稱
    private String benEvtRel; // 受益人與事故者關係
    private String tel1; // 電話1
    private String tel2; // 電話2
    private String commTyp; // 通訊地址別
    private String commZip; // 通訊郵遞區號
    private String commAddr; // 通訊地址
    private String payTyp; // 給付方式
    private String bankName; // 金融機構名稱
    private String payBankId; // 金融機構總代號
    private String branchId; // 分支代號
    private String payeeAcc; // 銀行帳號
    private String accName; // 戶名
    private String accRel; // 戶名與受益人關係
    private String mexcLvl; // 應決行層級
    private String chkDate; // 審核日期
    private String chkMan; // 審核人員
    private String rechkDate; // 複核日期
    private String rechkMan; // 複核人員
    private String exeDate; // 決行日期
    private String exeMan; // 決行人員
    private String chkStat; // 初核狀況
    private String rechkStat; // 複核狀況
    private String payDate; // 核付日期
    private String rpayDate; // 入帳日期
    private String rtDate; // 退匯確認日期
    private String repDate; // 改匯日期
    private String stexpndReason; // 止付原因
    private String stexpndDate; // 止付日期
    private String hpSnam;// 醫院簡稱

    // 頁面顯示轉換
    // [
    public String getApNoStrDisplay() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH)
            return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12);
        else
            return getApNo();
    }

    public String getReApNoStrDisplay() {
        if (StringUtils.isNotBlank(getReApNo()) && getReApNo().length() == ConstantKey.APNO_LENGTH)
            return getReApNo().substring(0, 1) + "-" + getReApNo().substring(1, 2) + "-" + getReApNo().substring(2, 7) + "-" + getReApNo().substring(7, 12);
        else
            return getReApNo();
    }

    public String getProcStatStr() {
        String procStatStr = getProcStat();
        if (StringUtils.isNotBlank(getProcStat())) {
            if ((ConstantKey.BABCML7_PROCSTAT_00).equals(getProcStat())) {
                procStatStr = ConstantKey.BABCML7_PROCSTAT_STR_00;
            }
            else if ((ConstantKey.BABCML7_PROCSTAT_01).equals(getProcStat())) {
                procStatStr = ConstantKey.BABCML7_PROCSTAT_STR_01;
            }
            else if ((ConstantKey.BABCML7_PROCSTAT_10).equals(getProcStat())) {
                procStatStr = ConstantKey.BABCML7_PROCSTAT_STR_10;
            }
            else if ((ConstantKey.BABCML7_PROCSTAT_11).equals(getProcStat())) {
                procStatStr = ConstantKey.BABCML7_PROCSTAT_STR_11;
            }
            else if ((ConstantKey.BABCML7_PROCSTAT_12).equals(getProcStat())) {
                procStatStr = ConstantKey.BABCML7_PROCSTAT_STR_12;
            }
            else if ((ConstantKey.BABCML7_PROCSTAT_20).equals(getProcStat())) {
                procStatStr = ConstantKey.BABCML7_PROCSTAT_STR_20;
            }
            else if ((ConstantKey.BABCML7_PROCSTAT_30).equals(getProcStat())) {
                procStatStr = ConstantKey.BABCML7_PROCSTAT_STR_30;
            }
            else if ((ConstantKey.BABCML7_PROCSTAT_40).equals(getProcStat())) {
                procStatStr = ConstantKey.BABCML7_PROCSTAT_STR_40;
            }
            else if ((ConstantKey.BABCML7_PROCSTAT_50).equals(getProcStat())) {
                procStatStr = ConstantKey.BABCML7_PROCSTAT_STR_50;
            }
            else if ((ConstantKey.BABCML7_PROCSTAT_90).equals(getProcStat())) {
                procStatStr = ConstantKey.BABCML7_PROCSTAT_STR_90;
            }
            else if ((ConstantKey.BABCML7_PROCSTAT_99).equals(getProcStat())) {
                procStatStr = ConstantKey.BABCML7_PROCSTAT_STR_99;
            }
        }
        return procStatStr;
    }

    public String getInreDateStr() {
        if (StringUtils.isNotBlank(getInreDate())) {
            return DateUtility.changeDateType(getInreDate());
        }
        else {
            return getInreDate();
        }
    }

    public String getReasCodeStr() {
        String reasCodeStr = getReasCode();
        if (StringUtils.isNotBlank(getReasCode())) {
            if ((ConstantKey.BABCML7_REASCODE_1).equals(getReasCode())) {
                reasCodeStr = ConstantKey.BABCML7_REASCODE_CODESTR_1;
            }
            else if ((ConstantKey.BABCML7_REASCODE_2).equals(getReasCode())) {
                reasCodeStr = ConstantKey.BABCML7_REASCODE_CODESTR_2;
            }
            else if ((ConstantKey.BABCML7_REASCODE_3).equals(getReasCode())) {
                reasCodeStr = ConstantKey.BABCML7_REASCODE_CODESTR_3;
            }
            else if ((ConstantKey.BABCML7_REASCODE_4).equals(getReasCode())) {
                reasCodeStr = ConstantKey.BABCML7_REASCODE_CODESTR_4;
            }
            else if ((ConstantKey.BABCML7_REASCODE_5).equals(getReasCode())) {
                reasCodeStr = ConstantKey.BABCML7_REASCODE_CODESTR_5;
            }
        }
        return reasCodeStr;
    }

    public String getRecosDateStr() {
        if (StringUtils.isNotBlank(getRecosDate())) {
            return DateUtility.changeDateType(getRecosDate());
        }
        else {
            return getRecosDate();
        }
    }

    public String getBenBrDateStr() {
        if (StringUtils.isNotBlank(getBenBrDate())) {
            return DateUtility.changeDateType(getBenBrDate());
        }
        else {
            return getBenBrDate();
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
        else if ((ConstantKey.BAAPPBASE_PAYTYP_7).equals(getPayTyp())) {
            payTypStr = ConstantKey.BAAPPBASE_PAYTYP_STR_7;
        }
        else if ((ConstantKey.BAAPPBASE_PAYTYP_8).equals(getPayTyp())) {
            payTypStr = ConstantKey.BAAPPBASE_PAYTYP_STR_8;
        }
        else if ((ConstantKey.BAAPPBASE_PAYTYP_9).equals(getPayTyp())) {
            payTypStr = ConstantKey.BAAPPBASE_PAYTYP_STR_9;
        }
        else if ((ConstantKey.BAAPPBASE_PAYTYP_A).equals(getPayTyp())) {
            payTypStr = ConstantKey.BAAPPBASE_PAYTYP_STR_A;
        }
        return payTypStr;
    }

    public String getChkDateStr() {
        if (StringUtils.isNotBlank(getChkDate())) {
            return DateUtility.changeDateType(getChkDate());
        }
        else {
            return getChkDate();
        }
    }

    public String getRechkDateStr() {
        if (StringUtils.isNotBlank(getRechkDate())) {
            return DateUtility.changeDateType(getRechkDate());
        }
        else {
            return getRechkDate();
        }
    }

    public String getExeDateStr() {
        if (StringUtils.isNotBlank(getExeDate())) {
            return DateUtility.changeDateType(getExeDate());
        }
        else {
            return getExeDate();
        }
    }

    public String getChkStatStr() {
        String chkStatStr = getChkStat();
        if ((ConstantKey.BABCML7_CHKSTAT_1).equals(getExeDate())) {
            chkStatStr = ConstantKey.BABCML7_CHKSTAT_CODESTR_1;
        }
        else if ((ConstantKey.BABCML7_CHKSTAT_2).equals(getExeDate())) {
            chkStatStr = ConstantKey.BABCML7_CHKSTAT_CODESTR_2;
        }
        return chkStatStr;
    }

    public String getRechkStatStr() {
        String rechkStatStr = getRechkStat();
        if ((ConstantKey.BABCML7_RECHKSTAT_1).equals(getRechkStat())) {
            rechkStatStr = ConstantKey.BABCML7_RECHKSTAT_CODESTR_1;
        }
        else if ((ConstantKey.BABCML7_RECHKSTAT_2).equals(getRechkStat())) {
            rechkStatStr = ConstantKey.BABCML7_RECHKSTAT_CODESTR_2;
        }
        else if ((ConstantKey.BABCML7_RECHKSTAT_3).equals(getRechkStat())) {
            rechkStatStr = ConstantKey.BABCML7_RECHKSTAT_CODESTR_3;
        }
        else if ((ConstantKey.BABCML7_RECHKSTAT_N).equals(getRechkStat())) {
            rechkStatStr = ConstantKey.BABCML7_RECHKSTAT_CODESTR_N;
        }
        return rechkStatStr;
    }

    public String getPayDateStr() {
        if (StringUtils.isNotBlank(getPayDate())) {
            return DateUtility.changeDateType(getPayDate());
        }
        else {
            return getPayDate();
        }
    }

    public String getRpayDateStr() {
        if (StringUtils.isNotBlank(getRpayDate())) {
            return DateUtility.changeDateType(getRpayDate());
        }
        else {
            return getRpayDate();
        }
    }

    public String getRtDateStr() {
        if (StringUtils.isNotBlank(getRtDate())) {
            return DateUtility.changeDateType(getRtDate());
        }
        else {
            return getRtDate();
        }
    }

    public String getRepDateStr() {
        if (StringUtils.isNotBlank(getRepDate())) {
            return DateUtility.changeDateType(getRepDate());
        }
        else {
            return getRepDate();
        }
    }

    public String getStexpndDateStr() {
        if (StringUtils.isNotBlank(getStexpndDate())) {
            return DateUtility.changeDateType(getStexpndDate());
        }
        else {
            return getStexpndDate();
        }
    }

    // ]

    public String getReApNo() {
        return reApNo;
    }

    public void setReApNo(String reApNo) {
        this.reApNo = reApNo;
    }

    public BigDecimal getApSeq() {
        return apSeq;
    }

    public void setApSeq(BigDecimal apSeq) {
        this.apSeq = apSeq;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getProcStat() {
        return procStat;
    }

    public void setProcStat(String procStat) {
        this.procStat = procStat;
    }

    public String getInreDate() {
        return inreDate;
    }

    public void setInreDate(String inreDate) {
        this.inreDate = inreDate;
    }

    public String getReasCode() {
        return reasCode;
    }

    public void setReasCode(String reasCode) {
        this.reasCode = reasCode;
    }

    public String getHosId() {
        return hosId;
    }

    public void setHosId(String hosId) {
        this.hosId = hosId;
    }

    public String getRecosDate() {
        return recosDate;
    }

    public void setRecosDate(String recosDate) {
        this.recosDate = recosDate;
    }

    public BigDecimal getReNum() {
        return reNum;
    }

    public void setReNum(BigDecimal reNum) {
        this.reNum = reNum;
    }

    public BigDecimal getRehpStay() {
        return rehpStay;
    }

    public void setRehpStay(BigDecimal rehpStay) {
        this.rehpStay = rehpStay;
    }

    public BigDecimal getReFees() {
        return reFees;
    }

    public void setReFees(BigDecimal reFees) {
        this.reFees = reFees;
    }

    public BigDecimal getNonreFees() {
        return nonreFees;
    }

    public void setNonreFees(BigDecimal nonreFees) {
        this.nonreFees = nonreFees;
    }

    public BigDecimal getReAmtPay() {
        return reAmtPay;
    }

    public void setReAmtPay(BigDecimal reAmtPay) {
        this.reAmtPay = reAmtPay;
    }

    public String getNotifyForm() {
        return notifyForm;
    }

    public void setNotifyForm(String notifyForm) {
        this.notifyForm = notifyForm;
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

    public String getPayeeAcc() {
        return payeeAcc;
    }

    public void setPayeeAcc(String payeeAcc) {
        this.payeeAcc = payeeAcc;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getMexcLvl() {
        return mexcLvl;
    }

    public void setMexcLvl(String mexcLvl) {
        this.mexcLvl = mexcLvl;
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

    public String getChkStat() {
        return chkStat;
    }

    public void setChkStat(String chkStat) {
        this.chkStat = chkStat;
    }

    public String getRechkStat() {
        return rechkStat;
    }

    public void setRechkStat(String rechkStat) {
        this.rechkStat = rechkStat;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getRpayDate() {
        return rpayDate;
    }

    public void setRpayDate(String rpayDate) {
        this.rpayDate = rpayDate;
    }

    public String getRtDate() {
        return rtDate;
    }

    public void setRtDate(String rtDate) {
        this.rtDate = rtDate;
    }

    public String getRepDate() {
        return repDate;
    }

    public void setRepDate(String repDate) {
        this.repDate = repDate;
    }

    public String getStexpndReason() {
        return stexpndReason;
    }

    public void setStexpndReason(String stexpndReason) {
        this.stexpndReason = stexpndReason;
    }

    public String getStexpndDate() {
        return stexpndDate;
    }

    public void setStexpndDate(String stexpndDate) {
        this.stexpndDate = stexpndDate;
    }

    public String getBenNationName() {
        return benNationName;
    }

    public void setBenNationName(String benNationName) {
        this.benNationName = benNationName;
    }

    public String getHpSnam() {
        return hpSnam;
    }

    public void setHpSnam(String hpSnam) {
        this.hpSnam = hpSnam;
    }

    public String getAccRel() {
        return accRel;
    }

    public void setAccRel(String accRel) {
        this.accRel = accRel;
    }

    public String getBenEvtRel() {
        return benEvtRel;
    }

    public void setBenEvtRel(String benEvtRel) {
        this.benEvtRel = benEvtRel;
    }
}
