package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 退回現金業務單位使用紀錄檔
 * 
 * @author Kiyomi
 */
public class Pfpfm implements Serializable {
    private String insKd; // 保險別
    private String cprnDt; // 清單印表日期
    private BigDecimal cprnPage; // 清單印表頁次
    private String workType; // 業務別
    private String wdCd; // 收支區分
    private String accept_Issue_Kind; // 核發種類
    private String banTy; // 銀行別
    private String accountNo; // 銀行帳戶別 
    private String bookedBook; // 入帳方式（給付方式）
    private String js_Resource; // 來源檔案（勞保年金給付核定檔[BADAPR]）
    private BigDecimal issue_Amt; // 本頁核定金額
    private BigDecimal accept_Amt; // 本頁實收付金額    
    private BigDecimal pfd_Cnt; // 核定清單明細總行睥
    private String rechkDept; // 複核人員單位別
    private String rechkStaff; // 複核人員
    private String rckDate; // 複核日期
    private String confirm_Dt; // 確認日期
    private String confirm_Per; // 確認人員
    private String payDte; // 出納核付日期
    private BigDecimal putfile_Page; // 歸檔頁次
    private String branchCode; // 分局別

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
    
    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }
    
    public String getWdCd() {
        return wdCd;
    }

    public void setWdCd(String wdCd) {
        this.wdCd = wdCd;
    }

    public String getAccept_Issue_Kind() {
        return accept_Issue_Kind;
    }

    public void setAccept_Issue_Kind(String accept_Issue_Kind) {
        this.accept_Issue_Kind = accept_Issue_Kind;
    }
    
    public String getBanTy() {
        return banTy;
    }

    public void setBanTy(String banTy) {
        this.banTy = banTy;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
    
    public String getBookedBook() {
        return bookedBook;
    }

    public void setBookedBook(String bookedBook) {
        this.bookedBook = bookedBook;
    }
    
    public String getJs_Resource() {
        return js_Resource;
    }

    public void setJs_Resource(String js_Resource) {
        this.js_Resource = js_Resource;
    }

    public BigDecimal getIssue_Amt() {
        return issue_Amt;
    }

    public void setIssue_Amt(BigDecimal issue_Amt) {
        this.issue_Amt = issue_Amt;
    }

    public BigDecimal getAccept_Amt() {
        return accept_Amt;
    }

    public void setAccept_Amt(BigDecimal accept_Amt) {
        this.accept_Amt = accept_Amt;
    }
    
    public BigDecimal getPfd_Cnt() {
        return pfd_Cnt;
    }

    public void setPfd_Cnt(BigDecimal pfd_Cnt) {
        this.pfd_Cnt = pfd_Cnt;
    }
    
    public String getRechkDept() {
        return rechkDept;
    }

    public void setRechkDept(String rechkDept) {
        this.rechkDept = rechkDept;
    }
    
    public String getRechkStaff() {
        return rechkStaff;
    }

    public void setRechkStaff(String rechkStaff) {
        this.rechkStaff = rechkStaff;
    }
    
    public String getRckDate() {
        return rckDate;
    }

    public void setRckDate(String rckDate) {
        this.rckDate = rckDate;
    }
    
    public String getConfirm_Dt() {
        return confirm_Dt;
    }

    public void setConfirm_Dt(String confirm_Dt) {
        this.confirm_Dt = confirm_Dt;
    }
    
    public String getConfirm_Per() {
        return confirm_Per;
    }

    public void setConfirm_Per(String confirm_Per) {
        this.confirm_Per = confirm_Per;
    }
    
    public String getPayDte() {
        return payDte;
    }

    public void setPayDte(String payDte) {
        this.payDte = payDte;
    }
    
    public BigDecimal getPutfile_Page() {
        return putfile_Page;
    }

    public void setPutfile_Page(BigDecimal putfile_Page) {
        this.putfile_Page = putfile_Page;
    }
    
    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }
    
}
