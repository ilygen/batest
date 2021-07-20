package tw.gov.bli.ba.rpt.forms;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 列印作業 - 審核作業相關報表 - 勞保老年年金給付受理編審清單 - 查詢頁面 (balp0d020l.jsp)
 * 
 * @author swim
 */
public class OldAgeReviewRpt02Form extends BaseValidatorForm {
    private String method;

    private String payCode; // 給付別
    private String chkMan; // 審核人員
    private String chkDate; // 審核日期
    private String prtTyp;// 列印種類 1:打包列印 2:重新列印
    private String packDate;// 打包日期
    private BigDecimal pageNo;// 頁次
    
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        ActionErrors errors = super.validate(mapping, request);

        // Validation.xml 驗證若有錯誤則先讓使用者改正所輸入之資料
        if (errors != null && errors.size() > 0)
            return errors;

        return errors;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getChkMan() {
        return chkMan;
    }

    public void setChkMan(String chkMan) {
        this.chkMan = chkMan;
    }

    public String getChkDate() {
        return chkDate;
    }

    public void setChkDate(String chkDate) {
        this.chkDate = chkDate;
    }

    public String getPrtTyp() {
        return prtTyp;
    }

    public void setPrtTyp(String prtTyp) {
        this.prtTyp = prtTyp;
    }
    
    public String getPackDate() {
        return packDate;
    }

    public void setPackDate(String packDate) {
        this.packDate = packDate;
    }

    public BigDecimal getPageNo() {
        return pageNo;
    }

    public void setPageNo(BigDecimal pageNo) {
        this.pageNo = pageNo;
    }
}
