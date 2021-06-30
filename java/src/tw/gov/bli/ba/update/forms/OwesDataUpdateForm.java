package tw.gov.bli.ba.update.forms;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 更正作業 - 案件資料更正 - 欠費期間維護(BAMO0D022N)
 * 
 * @author Rickychi
 */
public class OwesDataUpdateForm extends BaseValidatorForm {
    private String method;

    private String apNo;// 受理編號
    private String seqNo;// 序號
    private String owesMk;// 欠費註記
    private String owesBdate;// 欠費期間起
    private String owesEdate;// 欠費期間迄
    private String lsUbno;// 最後單位保險證號
    private String lsUbnoCk;// 最後單位保險證號檢查碼
    
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        ActionErrors errors = super.validate(mapping, request);

        // Validation.xml 驗證若有錯誤則先讓使用者改正所輸入之資料
        if (errors != null && errors.size() > 0)
            return errors;

        return errors;
    }

    public String getLsUbnoCk() {
        return lsUbnoCk;
    }

    public void setLsUbnoCk(String lsUbnoCk) {
        this.lsUbnoCk = lsUbnoCk;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getOwesMk() {
        return owesMk;
    }

    public void setOwesMk(String owesMk) {
        this.owesMk = owesMk;
    }

    public String getOwesBdate() {
        return owesBdate;
    }

    public void setOwesBdate(String owesBdate) {
        this.owesBdate = owesBdate;
    }

    public String getOwesEdate() {
        return owesEdate;
    }

    public void setOwesEdate(String owesEdate) {
        this.owesEdate = owesEdate;
    }

    public String getLsUbno() {
        return lsUbno;
    }

    public void setLsUbno(String lsUbno) {
        this.lsUbno = lsUbno;
    }
}
