package tw.gov.bli.ba.review.forms;

import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 審核作業 - 遺屬年金給付審核作業 (BACO0D210A)
 * 
 * @author Rickychi
 */
public class SurvivorPaymentReviewForm extends BaseValidatorForm {
    private String method;

    private String qryCond;// 查詢條件
    private String apNo;// 受理編號
    private String apNo1;// 受理編號-1
    private String apNo2;// 受理編號-2
    private String apNo3;// 受理編號-3
    private String apNo4;// 受理編號-4
    private String rptDate;// 列印日期
    private BigDecimal pageNo;// 頁次

    private String notifyForm;// 核定格式
    private String apNoOfConfirm; // 受理編號 for 批次審核
    private String chgNote;// 更正原因
    private String manchkMk;// 人工審核結果
    private BigDecimal resultSize;// 查詢結果筆數

    private String payYmOption; // 給付年月下拉選單值
    private String seqNoOption; // 受款人下拉選單值
    private String acceptMkOption; // 電腦審核下拉選單值
    private int dataIndex;// 資料清單序號
    private String procStat;// 處理狀態
    
    //for log
    private String oldNotifyForm;// 核定格式
    private String oldChgNote;// 更正原因
    private String oldManchkMk;// 人工審核結果
    private String oldProcStat;// 處理狀態

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        ActionErrors errors = super.validate(mapping, request);

        // Validation.xml 驗證若有錯誤則先讓使用者改正所輸入之資料
        if (errors != null && errors.size() > 0)
            return errors;

        return errors;
    }

    public String getApNoStr() {
        return getApNo1() + getApNo2() + getApNo3() + getApNo4();
    }

    public String getApNoStrDisplay() {
        return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12);
    }

    public BigDecimal getResultSize() {
        return resultSize;
    }

    public void setResultSize(BigDecimal resultSize) {
        this.resultSize = resultSize;
    }

    public String getApNoOfConfirm() {
        return apNoOfConfirm;
    }

    public void setApNoOfConfirm(String apNoOfConfirm) {
        this.apNoOfConfirm = apNoOfConfirm;
    }

    public String getManchkMk() {
        return manchkMk;
    }

    public void setManchkMk(String manchkMk) {
        this.manchkMk = manchkMk;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getApNo1() {
        return apNo1;
    }

    public void setApNo1(String apNo1) {
        this.apNo1 = apNo1;
    }

    public String getApNo2() {
        return apNo2;
    }

    public void setApNo2(String apNo2) {
        this.apNo2 = apNo2;
    }

    public String getApNo3() {
        return apNo3;
    }

    public void setApNo3(String apNo3) {
        this.apNo3 = apNo3;
    }

    public String getApNo4() {
        return apNo4;
    }

    public void setApNo4(String apNo4) {
        this.apNo4 = apNo4;
    }

    public String getRptDate() {
        return rptDate;
    }

    public void setRptDate(String rptDate) {
        this.rptDate = rptDate;
    }

    public BigDecimal getPageNo() {
        return pageNo;
    }

    public void setPageNo(BigDecimal pageNo) {
        this.pageNo = pageNo;
    }

    public String getQryCond() {
        return qryCond;
    }

    public void setQryCond(String qryCond) {
        this.qryCond = qryCond;
    }

    public String getNotifyForm() {
        return notifyForm;
    }

    public void setNotifyForm(String notifyForm) {
        this.notifyForm = notifyForm;
    }

    public String getChgNote() {
        return chgNote;
    }

    public void setChgNote(String chgNote) {
        this.chgNote = chgNote;
    }

    public String getPayYmOption() {
        return payYmOption;
    }

    public void setPayYmOption(String payYmOption) {
        this.payYmOption = payYmOption;
    }

    public String getSeqNoOption() {
        return seqNoOption;
    }

    public void setSeqNoOption(String seqNoOption) {
        this.seqNoOption = seqNoOption;
    }

    public String getAcceptMkOption() {
        return acceptMkOption;
    }

    public void setAcceptMkOption(String acceptMkOption) {
        this.acceptMkOption = acceptMkOption;
    }

    public int getDataIndex() {
        return dataIndex;
    }

    public void setDataIndex(int dataIndex) {
        this.dataIndex = dataIndex;
    }

	public String getProcStat() {
		return procStat;
	}

	public void setProcStat(String procStat) {
		this.procStat = procStat;
	}

	public String getOldNotifyForm() {
		return oldNotifyForm;
	}

	public void setOldNotifyForm(String oldNotifyForm) {
		this.oldNotifyForm = oldNotifyForm;
	}

	public String getOldChgNote() {
		return oldChgNote;
	}

	public void setOldChgNote(String oldChgNote) {
		this.oldChgNote = oldChgNote;
	}

	public String getOldManchkMk() {
		return oldManchkMk;
	}

	public void setOldManchkMk(String oldManchkMk) {
		this.oldManchkMk = oldManchkMk;
	}

	public String getOldProcStat() {
		return oldProcStat;
	}

	public void setOldProcStat(String oldProcStat) {
		this.oldProcStat = oldProcStat;
	}
    
}
