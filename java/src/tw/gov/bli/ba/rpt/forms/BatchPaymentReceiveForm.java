package tw.gov.bli.ba.rpt.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 列印作業 - 應收收回相關報表 - 整批收回核定清單 - 查詢頁面 (balp0d690l.jsp)
 * 
 * @author Kiyomi
 */

public class BatchPaymentReceiveForm extends BaseValidatorForm {

    private String method;
    private String payCode; // 給付別
    private String payCodeStr;
    private String chkDate; // 核收日期
    private String chkDateStr;
    private String reportType; // 功能選項
    private int[] index; // 資料列序號
    private String apNo;

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        ActionErrors errors = super.validate(mapping, request);

        // Validation.xml 驗證若有錯誤則先讓使用者改正所輸入之資料
        if (errors != null && errors.size() > 0)
            return errors;

        return errors;
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
    
    public String getPayCodeStr() {
        if (payCodeStr.equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_L))
            payCodeStr = ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L;
        else if (payCodeStr.equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_K))
            payCodeStr = ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K;
        else if (payCodeStr.equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_S))
            payCodeStr = ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S;
        
        return payCodeStr;
    }

    public void setPayCodeStr(String payCodeStr) {                
        this.payCodeStr = payCodeStr;
    }    

    public String getChkDate() {
        return chkDate;
    }

    public void setChkDate(String chkDate) {
        this.chkDate = chkDate;
    }
    
    public String getChkDateStr() {
        return chkDateStr;
    }

    public void setChkDateStr(String chkDateStr) {
        this.chkDateStr = chkDateStr;
    }    

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
    
    public int[] getIndex() {
        return index;
    }

    public void setIndex(int[] index) {
        this.index = index;
    }
    
    public String getApNo() {
        return apNo;
    }
    public void setApNo(String apNo) {
        this.apNo = apNo;
    }    

}
