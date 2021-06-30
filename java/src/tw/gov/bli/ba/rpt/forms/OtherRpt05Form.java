package tw.gov.bli.ba.rpt.forms;

import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 列印作業 - 其他類報表 - 批次核定函 - 查詢頁面 (balp0d3q0l.jsp)
 * 
 * @author Noctis
 */
public class OtherRpt05Form extends BaseValidatorForm {
    private String method;

    private String payCode; // 給付別
    private String issuYm; // 核定年月
    private String rptKind; // O 一般，B 補償金，D 死亡，L 紓困

    private String downloadFileName;

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        if (page != 1)
            return null;

        ActionErrors errors = super.validate(mapping, request);

        // Validation.xml 驗證若有錯誤則先讓使用者改正所輸入之資料
        if (errors != null && errors.size() > 0)
            return errors;

        return errors;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        payCode = "";
        issuYm = "";
        rptKind = "";
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

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String getRptKind() {
        return rptKind;
    }

    public void setRptKind(String rptKind) {
        this.rptKind = rptKind;
    }

    public String getDownloadFileName() {
        return downloadFileName;
    }

    public void setDownloadFileName(String downloadFileName) {
        this.downloadFileName = downloadFileName;
    }

}
