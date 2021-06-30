package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 退回現金業務單位使用紀錄檔
 * 
 * @author Kiyomi
 */
public class Pfpfd implements Serializable {
    private String insKd; // 保險別
    private String cprnDt; // 清單印表日期
    private BigDecimal cprnPage; // 清單印表頁次
    private BigDecimal lcnt; // 行次
    private String cdetail_Keyfield; // 清單明細關鍵欄
    private String per_Unit_Cd; // 單位或個人
    private String id_No; // 身分證統一號碼
    private String per_Unit_Name; // 單位或個人名稱
    private String accept_Pay_Type; // 給付方式
    private String account_No; // 全帳號
    private BigDecimal decide_Amt; // 核定金額
    private BigDecimal cash_Amt; // 現金金額(應付金額)
    private String cmemo; // 特殊會計科目(XXX)中的XXX
    private String chkStaff; // 初核人員
    private String chkSdate; // 初核日期
    private String opcFm; // 作業確認
    private BigDecimal cnt; // 件數
    private String bli_Account_Code; // 局帳戶代號
    private String acttitle_Ap_Code; // 會計科目案類代碼
    private String actDb; // 會計科目借方
    private String actCr; // 會計科目貸方
    private String data_Cd; // 資料區分
    private BigDecimal putfile_Page; // 歸檔頁次
    private String insuranced_Name; // 被保險人姓名
    private String accept_Issue_Cd; // 核發種類
    private String apNo; // 受理編號
    private String gvSeq; // 受款人序
    private String payYm; // 給付年月
    private String issuYm; // 核定年月

    public String getInsKd() {
        return insKd;
    }

    public void setInsKd(String insKd) {
        this.insKd = insKd;
    }

    public String getCprnDt() {
        return cprnDt;
    }

    public void setCprnDt(String cprnDt) {
        this.cprnDt = cprnDt;
    }

    public BigDecimal getCprnPage() {
        return cprnPage;
    }

    public void setCprnPage(BigDecimal cprnPage) {
        this.cprnPage = cprnPage;
    }

    public BigDecimal getLcnt() {
        return lcnt;
    }

    public void setLcnt(BigDecimal lcnt) {
        this.lcnt = lcnt;
    }
    
    public String getCdetail_Keyfield() {
        return cdetail_Keyfield;
    }

    public void setCdetail_Keyfield(String cdetail_Keyfield) {
        this.cdetail_Keyfield = cdetail_Keyfield;
    }
    
    public String getPer_Unit_Cd() {
        return per_Unit_Cd;
    }

    public void setPer_Unit_Cd(String per_Unit_Cd) {
        this.per_Unit_Cd = per_Unit_Cd;
    }
    
    public String getId_No() {
        return id_No;
    }

    public void setId_No(String id_No) {
        this.id_No = id_No;
    }
    
    public String getPer_Unit_Name() {
        return per_Unit_Name;
    }

    public void setPer_Unit_Name(String per_Unit_Name) {
        this.per_Unit_Name = per_Unit_Name;
    }
    
    public String getAccept_Pay_Type() {
        return accept_Pay_Type;
    }

    public void setAccept_Pay_Type(String accept_Pay_Type) {
        this.accept_Pay_Type = accept_Pay_Type;
    }
    
    public String getAccount_No() {
        return account_No;
    }

    public void setAccount_No(String account_No) {
        this.account_No = account_No;
    }

    public BigDecimal getDecide_Amt() {
        return decide_Amt;
    }

    public void setDecide_Amt(BigDecimal decide_Amt) {
        this.decide_Amt = decide_Amt;
    }

    public BigDecimal getCash_Amt() {
        return cash_Amt;
    }

    public void setCash_Amt(BigDecimal cash_Amt) {
        this.cash_Amt = cash_Amt;
    }
    
    public String getCmemo() {
        return cmemo;
    }

    public void setCmemo(String cmemo) {
        this.cmemo = cmemo;
    }
    
    public String getChkStaff() {
        return chkStaff;
    }

    public void setChkStaff(String chkStaff) {
        this.chkStaff = chkStaff;
    }
    
    public String getChkSdate() {
        return chkSdate;
    }

    public void setChkSdate(String chkSdate) {
        this.chkSdate = chkSdate;
    }
    
    public String getOpcFm() {
        return opcFm;
    }

    public void setOpcFm(String opcFm) {
        this.opcFm = opcFm;
    }

    public BigDecimal getCnt() {
        return cnt;
    }

    public void setCnt(BigDecimal cnt) {
        this.cnt = cnt;
    }

    public String getBli_Account_Code() {
        return bli_Account_Code;
    }

    public void setBli_Account_Code(String bli_Account_Code) {
        this.bli_Account_Code = bli_Account_Code;
    }
    
    public String getActtitle_Ap_Code() {
        return acttitle_Ap_Code;
    }

    public void setActtitle_Ap_Code(String acttitle_Ap_Code) {
        this.acttitle_Ap_Code = acttitle_Ap_Code;
    }

    public String getActDb() {
        return actDb;
    }

    public void setActDb(String actDb) {
        this.actDb = actDb;
    }

    public String getActCr() {
        return actCr;
    }

    public void setActCr(String actCr) {
        this.actCr = actCr;
    }

    public String getData_Cd() {
        return data_Cd;
    }

    public void setData_Cd(String data_Cd) {
        this.data_Cd = data_Cd;
    }
    
    public BigDecimal getPutfile_Page() {
        return putfile_Page;
    }

    public void setPutfile_Page(BigDecimal putfile_Page) {
        this.putfile_Page = putfile_Page;
    }
    
    public String getInsuranced_Name() {
        return insuranced_Name;
    }

    public void setInsuranced_Name(String insuranced_Name) {
        this.insuranced_Name = insuranced_Name;
    }
    
    public String getAccept_Issue_Cd() {
        return accept_Issue_Cd;
    }

    public void setAccept_Issue_Cd(String accept_Issue_Cd) {
        this.accept_Issue_Cd = accept_Issue_Cd;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }
    
    public String getGvSeq() {
        return gvSeq;
    }

    public void setGvSeq(String gvSeq) {
        this.gvSeq = gvSeq;
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

}
