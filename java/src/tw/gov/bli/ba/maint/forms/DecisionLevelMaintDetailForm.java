package tw.gov.bli.ba.maint.forms;

/**
 * 勞工保險年金給付系統 - 決行層級維護作業
 * 查詢條件輸入頁面
 * bapa0x031a.jsp
 */
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

public class DecisionLevelMaintDetailForm extends BaseValidatorForm {
    private String method;
    private String payCode; // 給付別
    private String payKind; // 給付種類
    private String rechklvl; // 決行層級
    private String hicLevel; // 失能等級
    private String nlwkmk; // 普職限制
    private BigDecimal stdAmt; // 決行金額
    private String echkLevel; // 錯誤程度(E)
    private String ochkLevel; // 穿透程度(O)
    private String wchkLevel; // 警告程度(W)
    private String echk;
    private String ochk;
    private String wchk;

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = super.validate(mapping, request);
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

    public String getRechklvl() {
        return rechklvl;
    }

    public void setRechklvl(String rechklvl) {
        this.rechklvl = rechklvl;
    }

    public BigDecimal getStdAmt() {
        return stdAmt;
    }

    public void setStdAmt(BigDecimal stdAmt) {
        this.stdAmt = stdAmt;
    }

    public String getEchkLevel() {
        return echkLevel;
    }

    public void setEchkLevel(String echkLevel) {
        this.echkLevel = echkLevel;
    }

    public String getOchkLevel() {
        return ochkLevel;
    }

    public void setOchkLevel(String ochkLevel) {
        this.ochkLevel = ochkLevel;
    }

    public String getWchkLevel() {
        return wchkLevel;
    }

    public void setWchkLevel(String wchkLevel) {
        this.wchkLevel = wchkLevel;
    }

    public String getHicLevel() {
        return hicLevel;
    }

    public void setHicLevel(String hicLevel) {
        this.hicLevel = hicLevel;
    }

    public String getNlwkmk() {
        return nlwkmk;
    }

    public void setNlwkmk(String nlwkmk) {
        this.nlwkmk = nlwkmk;
    }

    public String getPayKind() {
        return payKind;
    }

    public void setPayKind(String payKind) {
        this.payKind = payKind;
    }

    public String getEchk() {
        return echk;
    }

    public void setEchk(String echk) {
        this.echk = echk;
    }

    public String getOchk() {
        return ochk;
    }

    public void setOchk(String ochk) {
        this.ochk = ochk;
    }

    public String getWchk() {
        return wchk;
    }

    public void setWchk(String wchk) {
        this.wchk = wchk;
    }
}