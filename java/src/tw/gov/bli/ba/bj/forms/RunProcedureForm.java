package tw.gov.bli.ba.bj.forms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 批次處理 - 批次排程作業 - 批次程式查詢作業 - 查詢頁面  (baba0m240x.jsp)
 * 
 * @author KIYOMI
 */
public class RunProcedureForm extends BaseValidatorForm {
    private String method; 
    
    private String procedureName;
    private String runDateBegin;
    private String paramsList;



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

    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
    
    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }        
    
    public String getRunDateBegin() {
        return runDateBegin;
    }

    public void setRunDateBegin(String runDateBegin) {
        this.runDateBegin = runDateBegin;
    }

    public String getParamsList() {
        return paramsList;
    }

    public void setParamsList(String paramsList) {
        this.paramsList = paramsList;
    }            
    
}
