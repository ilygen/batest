package tw.gov.bli.ba.rpt.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 列印作業 - 其他類報表 - 批次老年差額金通知列印作業 - 查詢頁面 (balp0d570l.jsp)
 * 
 * @author KIYOMI
 */

public class OtherRpt12Form extends BaseValidatorForm {

    private String method;

    private String rptKind; // A-全部，1-第1次發函，2-催辦，3-延不補正

    private String downloadFileName;

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
