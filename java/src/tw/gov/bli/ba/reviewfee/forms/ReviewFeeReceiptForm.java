package tw.gov.bli.ba.reviewfee.forms;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.domain.Bacountry;
import tw.gov.bli.ba.domain.Baparam;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 複檢費用 - 複檢費用受理作業 (bare0d010a.jsp bare0d011a.jsp bare0d012c.jsp bare0d013c.jsp)
 * 
 * @author Goston
 */
public class ReviewFeeReceiptForm extends BaseValidatorForm {
    private boolean initialed;

    private String method;

    // Page Query
    private String apNo1; // 受理編號 - 第一欄 ( 1 碼 )
    private String apNo2; // 受理編號 - 第二欄 ( 1 碼 )
    private String apNo3; // 受理編號 - 第三欄 ( 5 碼 )
    private String apNo4; // 受理編號 - 第四欄 ( 5 碼 )

    private String qryIdnNo; // 身分證號

    // Page List
    private BigDecimal listApSeq; // 複檢費用申請序

    // Page Detail
    private String reApNo; // 複檢費用受理編號
    private BigDecimal apSeq; // 複檢費用申請序
    private String apNo; // 受理編號
    // private String procStat; // 處理狀態
    private String inreDate; // 通知複檢日期
    private String reasCode; // 複檢原因
    private String hosId; // 醫療院所代碼
    private String recosDate; // 複檢費用申請日期
    private BigDecimal reNum; // 複檢門診次數
    private BigDecimal rehpStay; // 複檢住院天數
    private BigDecimal reFees; // 複檢費用
    private BigDecimal nonreFees; // 非複檢必須費用
    // private BigDecimal reAmtPay; // 複檢實付金額
    private String notifyForm; // 核定通知書格式
    private String benIdnNo; // 受益人身分證號
    private String benName; // 受益人姓名
    private String benBrDate; // 受益人出生日期
    private String benSex; // 受益人性別
    private String benNationTyp; // 受益人國籍別
    private String benNationCode; // 受益人國籍
    private String benEvtRel; // 受益人與事故者關係
    private String tel1; // 電話1
    private String tel2; // 電話2
    private String commTyp; // 通訊地址別
    private String commZip; // 通訊郵遞區號
    private String commAddr; // 通訊地址
    private String payTyp; // 給付方式
    private String accountNo1; // 帳號 (前)
    private String accountNo2; // 帳號 (後)
    private String bankName; // 金融機構名稱
    private String accName; // 戶名

    // Other
    private String hosName; // 醫療院所名稱
    private String tmpBenNationCode; // 受益人國籍代碼 (input - Text)

    // for 改前值
    // [
    private String oldInreDate; // 通知複檢日期
    private String oldReasCode; // 複檢原因
    private String oldHosId; // 醫療院所代碼
    private String oldRecosDate; // 複檢費用申請日期
    private BigDecimal oldReNum; // 複檢門診次數
    private BigDecimal oldRehpStay; // 複檢住院天數
    private BigDecimal oldReFees; // 複檢費用
    private BigDecimal oldNonreFees; // 非複檢必須費用
    // private BigDecimal reAmtPay; // 複檢實付金額
    private String oldNotifyForm; // 核定通知書格式
    private String oldBenIdnNo; // 受益人身分證號
    private String oldBenName; // 受益人姓名
    private String oldBenBrDate; // 受益人出生日期
    private String oldBenSex; // 受益人性別
    private String oldBenNationTyp; // 受益人國籍別
    private String oldBenNationCode; // 受益人國籍
    private String oldBenEvtRel; // 受益人與事故者關係
    private String oldTel1; // 電話1
    private String oldTel2; // 電話2
    private String oldCommTyp; // 通訊地址別
    private String oldCommZip; // 通訊郵遞區號
    private String oldCommAddr; // 通訊地址
    private String oldPayTyp; // 給付方式
    private String oldAccountNo1; // 帳號 (前)
    private String oldAccountNo2; // 帳號 (後)
    private String oldBankName; // 金融機構名稱
    private String oldAccName; // 戶名

    // Other
    private String oldTmpBenNationCode; // 受益人國籍代碼 (input - Text)
    // ]
    // for 改前值

    private List<Baparam> reasCodeOptionList; // 複檢原因下拉選單
    private List<Baparam> benEvtRelOptionList; // 受益人與事故者關係下拉選單
    private List<Bacountry> countryOptionList; // 國籍下拉選單
    private List<Baparam> payTypOptionList; // 給付方式下拉選單

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        if (page == 1 || StringUtils.equals(method, "doInsert") || StringUtils.equals(method, "doUpdate")) {
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
        if (!initialed) {
            clearFields();
            initialed = true;
        }
    }

    public void clearFields() {
        apSeq = null; // 複檢費用申請序
        inreDate = ""; // 通知複檢日期
        reasCode = ""; // 複檢原因
        hosId = ""; // 醫療院所代碼
        recosDate = ""; // 複檢費用申請日期
        reNum = null; // 複檢門診次數
        rehpStay = null; // 複檢住院天數
        reFees = null; // 複檢費用
        nonreFees = null; // 非複檢必須費用
        notifyForm = ""; // 核定通知書格式
        benIdnNo = ""; // 受益人身分證號
        benName = ""; // 受益人姓名
        benBrDate = ""; // 受益人出生日期
        benSex = ""; // 受益人性別
        benNationTyp = "1"; // 受益人國籍別
        benNationCode = ""; // 受益人國籍
        benEvtRel = ""; // 受益人與事故者關係
        tel1 = ""; // 電話1
        tel2 = ""; // 電話2
        commTyp = "2"; // 通訊地址別
        commZip = ""; // 通訊郵遞區號
        commAddr = ""; // 通訊地址
        payTyp = ""; // 給付方式
        accountNo1 = ""; // 帳號 (前)
        accountNo2 = ""; // 帳號 (後)
        bankName = ""; // 金融機構名稱
        accName = ""; // 戶名
        tmpBenNationCode = "000"; // 受益人國籍代碼 (input - Text)
        
        // 改前值
        oldInreDate = ""; // 通知複檢日期
        oldReasCode = ""; // 複檢原因
        oldHosId = ""; // 醫療院所代碼
        oldRecosDate = ""; // 複檢費用申請日期
        oldReNum = null; // 複檢門診次數
        oldRehpStay = null; // 複檢住院天數
        oldReFees = null; // 複檢費用
        oldNonreFees = null; // 非複檢必須費用
        oldNotifyForm = ""; // 核定通知書格式
        oldBenIdnNo = ""; // 受益人身分證號
        oldBenName = ""; // 受益人姓名
        oldBenBrDate = ""; // 受益人出生日期
        oldBenSex = ""; // 受益人性別
        oldBenNationTyp = ""; // 受益人國籍別
        oldBenNationCode = ""; // 受益人國籍
        oldBenEvtRel = ""; // 受益人與事故者關係
        oldTel1 = ""; // 電話1
        oldTel2 = ""; // 電話2
        oldCommTyp = ""; // 通訊地址別
        oldCommZip = ""; // 通訊郵遞區號
        oldCommAddr = ""; // 通訊地址
        oldPayTyp = ""; // 給付方式
        oldAccountNo1 = ""; // 帳號 (前)
        oldAccountNo2 = ""; // 帳號 (後)
        oldBankName = ""; // 金融機構名稱
        oldAccName = ""; // 戶名
        tmpBenNationCode = ""; // 受益人國籍代碼 (input - Text)
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

    public String getQryIdnNo() {
        return qryIdnNo;
    }

    public void setQryIdnNo(String qryIdnNo) {
        this.qryIdnNo = qryIdnNo;
    }

    public BigDecimal getListApSeq() {
        return listApSeq;
    }

    public void setListApSeq(BigDecimal listApSeq) {
        this.listApSeq = listApSeq;
    }

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

    public String getPayTyp() {
        return payTyp;
    }

    public void setPayTyp(String payTyp) {
        this.payTyp = payTyp;
    }

    public String getAccountNo1() {
        return accountNo1;
    }

    public void setAccountNo1(String accountNo1) {
        this.accountNo1 = accountNo1;
    }

    public String getAccountNo2() {
        return accountNo2;
    }

    public void setAccountNo2(String accountNo2) {
        this.accountNo2 = accountNo2;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getHosName() {
        return hosName;
    }

    public void setHosName(String hosName) {
        this.hosName = hosName;
    }

    public String getOldInreDate() {
        return oldInreDate;
    }

    public void setOldInreDate(String oldInreDate) {
        this.oldInreDate = oldInreDate;
    }

    public String getOldReasCode() {
        return oldReasCode;
    }

    public void setOldReasCode(String oldReasCode) {
        this.oldReasCode = oldReasCode;
    }

    public String getOldHosId() {
        return oldHosId;
    }

    public void setOldHosId(String oldHosId) {
        this.oldHosId = oldHosId;
    }

    public String getOldRecosDate() {
        return oldRecosDate;
    }

    public void setOldRecosDate(String oldRecosDate) {
        this.oldRecosDate = oldRecosDate;
    }

    public BigDecimal getOldReNum() {
        return oldReNum;
    }

    public void setOldReNum(BigDecimal oldReNum) {
        this.oldReNum = oldReNum;
    }

    public BigDecimal getOldRehpStay() {
        return oldRehpStay;
    }

    public void setOldRehpStay(BigDecimal oldRehpStay) {
        this.oldRehpStay = oldRehpStay;
    }

    public BigDecimal getOldReFees() {
        return oldReFees;
    }

    public void setOldReFees(BigDecimal oldReFees) {
        this.oldReFees = oldReFees;
    }

    public BigDecimal getOldNonreFees() {
        return oldNonreFees;
    }

    public void setOldNonreFees(BigDecimal oldNonreFees) {
        this.oldNonreFees = oldNonreFees;
    }

    public String getOldNotifyForm() {
        return oldNotifyForm;
    }

    public void setOldNotifyForm(String oldNotifyForm) {
        this.oldNotifyForm = oldNotifyForm;
    }

    public String getOldBenIdnNo() {
        return oldBenIdnNo;
    }

    public void setOldBenIdnNo(String oldBenIdnNo) {
        this.oldBenIdnNo = oldBenIdnNo;
    }

    public String getOldBenName() {
        return oldBenName;
    }

    public void setOldBenName(String oldBenName) {
        this.oldBenName = oldBenName;
    }

    public String getOldBenBrDate() {
        return oldBenBrDate;
    }

    public void setOldBenBrDate(String oldBenBrDate) {
        this.oldBenBrDate = oldBenBrDate;
    }

    public String getOldBenSex() {
        return oldBenSex;
    }

    public void setOldBenSex(String oldBenSex) {
        this.oldBenSex = oldBenSex;
    }

    public String getOldBenNationTyp() {
        return oldBenNationTyp;
    }

    public void setOldBenNationTyp(String oldBenNationTyp) {
        this.oldBenNationTyp = oldBenNationTyp;
    }

    public String getOldBenNationCode() {
        return oldBenNationCode;
    }

    public void setOldBenNationCode(String oldBenNationCode) {
        this.oldBenNationCode = oldBenNationCode;
    }

    public String getOldBenEvtRel() {
        return oldBenEvtRel;
    }

    public void setOldBenEvtRel(String oldBenEvtRel) {
        this.oldBenEvtRel = oldBenEvtRel;
    }

    public String getOldTel1() {
        return oldTel1;
    }

    public void setOldTel1(String oldTel1) {
        this.oldTel1 = oldTel1;
    }

    public String getOldTel2() {
        return oldTel2;
    }

    public void setOldTel2(String oldTel2) {
        this.oldTel2 = oldTel2;
    }

    public String getOldCommTyp() {
        return oldCommTyp;
    }

    public void setOldCommTyp(String oldCommTyp) {
        this.oldCommTyp = oldCommTyp;
    }

    public String getOldCommZip() {
        return oldCommZip;
    }

    public void setOldCommZip(String oldCommZip) {
        this.oldCommZip = oldCommZip;
    }

    public String getOldCommAddr() {
        return oldCommAddr;
    }

    public void setOldCommAddr(String oldCommAddr) {
        this.oldCommAddr = oldCommAddr;
    }

    public String getOldPayTyp() {
        return oldPayTyp;
    }

    public void setOldPayTyp(String oldPayTyp) {
        this.oldPayTyp = oldPayTyp;
    }

    public String getOldAccountNo1() {
        return oldAccountNo1;
    }

    public void setOldAccountNo1(String oldAccountNo1) {
        this.oldAccountNo1 = oldAccountNo1;
    }

    public String getOldAccountNo2() {
        return oldAccountNo2;
    }

    public void setOldAccountNo2(String oldAccountNo2) {
        this.oldAccountNo2 = oldAccountNo2;
    }

    public String getOldAccName() {
        return oldAccName;
    }

    public void setOldAccName(String oldAccName) {
        this.oldAccName = oldAccName;
    }

    public List<Baparam> getReasCodeOptionList() {
        return reasCodeOptionList;
    }

    public void setReasCodeOptionList(List<Baparam> reasCodeOptionList) {
        this.reasCodeOptionList = reasCodeOptionList;
    }

    public List<Baparam> getBenEvtRelOptionList() {
        return benEvtRelOptionList;
    }

    public void setBenEvtRelOptionList(List<Baparam> benEvtRelOptionList) {
        this.benEvtRelOptionList = benEvtRelOptionList;
    }

    public List<Bacountry> getCountryOptionList() {
        return countryOptionList;
    }

    public void setCountryOptionList(List<Bacountry> countryOptionList) {
        this.countryOptionList = countryOptionList;
    }

    public List<Baparam> getPayTypOptionList() {
        return payTypOptionList;
    }

    public void setPayTypOptionList(List<Baparam> payTypOptionList) {
        this.payTypOptionList = payTypOptionList;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getOldBankName() {
        return oldBankName;
    }

    public void setOldBankName(String oldBankName) {
        this.oldBankName = oldBankName;
    }

    public String getTmpBenNationCode() {
        return tmpBenNationCode;
    }

    public void setTmpBenNationCode(String tmpBenNationCode) {
        this.tmpBenNationCode = tmpBenNationCode;
    }

    public String getOldTmpBenNationCode() {
        return oldTmpBenNationCode;
    }

    public void setOldTmpBenNationCode(String oldTmpBenNationCode) {
        this.oldTmpBenNationCode = oldTmpBenNationCode;
    }

}
