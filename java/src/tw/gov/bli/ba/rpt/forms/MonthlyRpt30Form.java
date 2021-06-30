package tw.gov.bli.ba.rpt.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 列印作業 - 其他類報表 - 查核失能程度通知函 - 查詢頁面 (balp0d3s0l.jsp)
 * 
 * @author KIYOMI
 */

public class MonthlyRpt30Form extends BaseValidatorForm {

    private String method;

    private String payCode; // 給付別
    private String rptKind; // 1：重新查核失能程度通知函，2：通過查核續發失能年金通知函
    private String issuYm;

    private String downloadFileName;
    private String[] index; // 資料列序號

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

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String[] getIndex() {
        return index;
    }

    public void setIndex(String[] index) {
        this.index = index;
    }

}
