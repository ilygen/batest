package tw.gov.bli.ba.update.forms;

import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;
import tw.gov.bli.ba.util.StringUtility;

/**
 * 更正作業 - 案件資料更正 (BAMO0D010C)
 * 
 * @author Rickychi
 */
public class ApplicationDataUpdateForm extends BaseValidatorForm {
    private String method;

    private BigDecimal baappbaseId;// 資料列編號
    private String apNo;// 受理編號
    private String apNo1;// 受理編號-1
    private String apNo2;// 受理編號-2
    private String apNo3;// 受理編號-3
    private String apNo4;// 受理編號-4
    private String evtNationTpe;// 事故者國籍別
    private String evtNationCode;// 事故者國籍
    private String evtNationCodeOption;// 國籍下拉選單
    private String evtSex;// 事故者性別
    private String evtName;// 事故者姓名
    private String evtBrDate;// 事故者出生日期
    private String evtIdnNo;// 事故者身分證號
    private String evtJobDate;// 離職日期
    private String evtDieDate;// 死亡日期
    private String combapMark;// 國勞合併申請註記
    private String appDate;// 申請日期
    private String apUbno;// 申請單位保險證號
    private String lsUbno;// 最後單位保險證號
    private String apItem;// 申請項目
    private String notifyForm;// 核定格式
    private String loanMk;// 結案日期
    private String closeDate;// 結案原因
    private String closeCause;// 不須抵銷紓困貸款註記

    private String benNationCode;// 受益人國籍
    private String benSex;// 受益人性別
    private String benName;// 受益人姓名
    private String benBrDate;// 受益人出生日期
    private String benIdnNo;// 受益人身分證號
    private String benDieDate;// 受益人死亡日期
    private String caseMk;// 案件註記
    private String procStat;// 處理狀態
    private String accRel;// 戶名與受益人關係

    private String dabApNo;// 已領失能年金受理編號
    private String dabApNo1;// 已領失能年金受理編號-1
    private String dabApNo2;// 已領失能年金受理編號-2
    private String dabApNo3;// 已領失能年金受理編號-3
    private String dabApNo4;// 已領失能年金受理編號-4

    private String evtData;// 身分證號重號資料(姓名;身分證號;出生日期)
    private String interValMonth;//發放方式

    private String oldEvtNationCode;// 事故者國籍
    // private String oldEvtName;// 事故者姓名
    private String oldEvtBrDate;// 事故者出生日期
    private String oldEvtIdnNo;// 事故者身分證號
    private String oldEvtJobDate;// 事故者離職日期
    private String oldEvtDieDate;// 事故者死亡日期
    private String oldBenNationCode;// 受益人國籍
    private String oldBenName;// 受益人姓名
    private String oldBenBrDate;// 受益人出生日期
    private String oldBenIdnNo;// 受益人身分證號
    private String oldBenDieDate;// 受益人死亡日期
    private String oldCombapMark;// 國勞合併申請註記
    private String oldAppDate;// 申請日期
    private String oldApUbno;// 申請單位保險證號
    private String oldLsUbno;// 最後單位保險證號
    private String oldApItem;// 申請項目
    private String oldNotifyForm;// 核定格式
    private String oldLoanMk;// 結案日期
    private String oldCloseDate;// 結案原因
    private String oldCloseCause;// 不須抵銷紓困貸款註記
    private String oldCaseMk;// 案件註記
    private String oldProcStat;// 處理狀態
    private String oldDabApNo;// 已領失能年金受理編號
    private String oldInterValMonth;//發放方式
    private String oldEvtNationTpe;// 事故者國籍別
    private String oldEvtNationCodeOption;// 國籍下拉選單
    private String oldEvtSex;// 事故者性別
    private String oldEvtName;// 事故者姓名
    private String oldBenSex;// 受益人性別
    private String oldAccRel;// 戶名與受益人關係

    private String oldevtData;// 身分證號重號資料(姓名;身分證號;出生日期)
    private String oldinterValMonth;//發放方式
    
    private String idnoExist;//檢查錯誤檔

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

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public BigDecimal getBaappbaseId() {
        return baappbaseId;
    }

    public void setBaappbaseId(BigDecimal baappbaseId) {
        this.baappbaseId = baappbaseId;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
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

    public String getEvtNationTpe() {
        return evtNationTpe;
    }

    public void setEvtNationTpe(String evtNationTpe) {
        this.evtNationTpe = evtNationTpe;
    }

    public String getEvtSex() {
        return evtSex;
    }

    public void setEvtSex(String evtSex) {
        this.evtSex = evtSex;
    }

    public String getEvtNationCode() {
        return evtNationCode;
    }

    public void setEvtNationCode(String evtNationCode) {
        this.evtNationCode = evtNationCode;
    }

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public String getEvtBrDate() {
        return evtBrDate;
    }

    public void setEvtBrDate(String evtBrDate) {
        this.evtBrDate = evtBrDate;
    }

    public String getEvtIdnNo() {
        return evtIdnNo;
    }

    public void setEvtIdnNo(String evtIdnNo) {
        this.evtIdnNo = evtIdnNo;
    }

    public String getEvtJobDate() {
        return evtJobDate;
    }

    public void setEvtJobDate(String evtJobDate) {
        this.evtJobDate = evtJobDate;
    }

    public String getEvtDieDate() {
        return evtDieDate;
    }

    public void setEvtDieDate(String evtDieDate) {
        this.evtDieDate = evtDieDate;
    }

    public String getCombapMark() {
        return combapMark;
    }

    public void setCombapMark(String combapMark) {
        this.combapMark = combapMark;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getApUbno() {
        return apUbno;
    }

    public void setApUbno(String apUbno) {
        this.apUbno = apUbno;
    }

    public String getLsUbno() {
        return lsUbno;
    }

    public void setLsUbno(String lsUbno) {
        this.lsUbno = lsUbno;
    }

    public String getApItem() {
        return apItem;
    }

    public void setApItem(String apItem) {
        this.apItem = apItem;
    }

    public String getNotifyForm() {
        return notifyForm;
    }

    public void setNotifyForm(String notifyForm) {
        this.notifyForm = notifyForm;
    }

    public String getLoanMk() {
        return loanMk;
    }

    public void setLoanMk(String loanMk) {
        this.loanMk = loanMk;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public String getCloseCause() {
        return closeCause;
    }

    public void setCloseCause(String closeCause) {
        this.closeCause = closeCause;
    }

    public String getEvtNationCodeOption() {
        if (StringUtils.isNotBlank(getEvtNationCode())) {
            return StringUtility.chtLeftPad(getEvtNationCode(), 3, "0");
        }
        else {
            return evtNationCodeOption;
        }
    }

    public void setEvtNationCodeOption(String evtNationCodeOption) {
        this.evtNationCodeOption = evtNationCodeOption;
    }

    public String getBenNationCode() {
        return benNationCode;
    }

    public void setBenNationCode(String benNationCode) {
        this.benNationCode = benNationCode;
    }

    public String getBenSex() {
        return benSex;
    }

    public void setBenSex(String benSex) {
        this.benSex = benSex;
    }

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }

    public String getBenBrDate() {
        return benBrDate;
    }

    public void setBenBrDate(String benBrDate) {
        this.benBrDate = benBrDate;
    }

    public String getBenIdnNo() {
        return benIdnNo;
    }

    public void setBenIdnNo(String benIdnNo) {
        this.benIdnNo = benIdnNo;
    }

    public String getBenDieDate() {
        return benDieDate;
    }

    public void setBenDieDate(String benDieDate) {
        this.benDieDate = benDieDate;
    }

    public String getOldEvtNationCode() {
        return oldEvtNationCode;
    }

    public void setOldEvtNationCode(String oldEvtNationCode) {
        this.oldEvtNationCode = oldEvtNationCode;
    }

    public String getOldEvtBrDate() {
        return oldEvtBrDate;
    }

    public void setOldEvtBrDate(String oldEvtBrDate) {
        this.oldEvtBrDate = oldEvtBrDate;
    }

    public String getOldEvtIdnNo() {
        return oldEvtIdnNo;
    }

    public void setOldEvtIdnNo(String oldEvtIdnNo) {
        this.oldEvtIdnNo = oldEvtIdnNo;
    }

    public String getOldEvtJobDate() {
        return oldEvtJobDate;
    }

    public void setOldEvtJobDate(String oldEvtJobDate) {
        this.oldEvtJobDate = oldEvtJobDate;
    }

    public String getOldEvtDieDate() {
        return oldEvtDieDate;
    }

    public void setOldEvtDieDate(String oldEvtDieDate) {
        this.oldEvtDieDate = oldEvtDieDate;
    }

    public String getOldBenNationCode() {
        return oldBenNationCode;
    }

    public void setOldBenNationCode(String oldBenNationCode) {
        this.oldBenNationCode = oldBenNationCode;
    }

    public String getOldBenName() {
        return oldBenName;
    }

    public void setOldBenName(String oldBenName) {
        this.oldBenName = oldBenName;
    }

    public String getOldBenBrDate() {
        return oldBenBrDate;
    }

    public void setOldBenBrDate(String oldBenBrDate) {
        this.oldBenBrDate = oldBenBrDate;
    }

    public String getOldBenIdnNo() {
        return oldBenIdnNo;
    }

    public void setOldBenIdnNo(String oldBenIdnNo) {
        this.oldBenIdnNo = oldBenIdnNo;
    }

    public String getOldBenDieDate() {
        return oldBenDieDate;
    }

    public void setOldBenDieDate(String oldBenDieDate) {
        this.oldBenDieDate = oldBenDieDate;
    }

    public String getOldCombapMark() {
        return oldCombapMark;
    }

    public void setOldCombapMark(String oldCombapMark) {
        this.oldCombapMark = oldCombapMark;
    }

    public String getOldAppDate() {
        return oldAppDate;
    }

    public void setOldAppDate(String oldAppDate) {
        this.oldAppDate = oldAppDate;
    }

    public String getOldApUbno() {
        return oldApUbno;
    }

    public void setOldApUbno(String oldApUbno) {
        this.oldApUbno = oldApUbno;
    }

    public String getOldLsUbno() {
        return oldLsUbno;
    }

    public void setOldLsUbno(String oldLsUbno) {
        this.oldLsUbno = oldLsUbno;
    }

    public String getOldApItem() {
        return oldApItem;
    }

    public void setOldApItem(String oldApItem) {
        this.oldApItem = oldApItem;
    }

    public String getOldNotifyForm() {
        return oldNotifyForm;
    }

    public void setOldNotifyForm(String oldNotifyForm) {
        this.oldNotifyForm = oldNotifyForm;
    }

    public String getOldLoanMk() {
        return oldLoanMk;
    }

    public void setOldLoanMk(String oldLoanMk) {
        this.oldLoanMk = oldLoanMk;
    }

    public String getOldCloseDate() {
        return oldCloseDate;
    }

    public void setOldCloseDate(String oldCloseDate) {
        this.oldCloseDate = oldCloseDate;
    }

    public String getOldCloseCause() {
        return oldCloseCause;
    }

    public void setOldCloseCause(String oldCloseCause) {
        this.oldCloseCause = oldCloseCause;
    }

    public String getCaseMk() {
        return caseMk;
    }

    public void setCaseMk(String caseMk) {
        this.caseMk = caseMk;
    }

    public String getProcStat() {
        return procStat;
    }

    public void setProcStat(String procStat) {
        this.procStat = procStat;
    }

    public String getOldCaseMk() {
        return oldCaseMk;
    }

    public void setOldCaseMk(String oldCaseMk) {
        this.oldCaseMk = oldCaseMk;
    }

    public String getOldProcStat() {
        return oldProcStat;
    }

    public void setOldProcStat(String oldProcStat) {
        this.oldProcStat = oldProcStat;
    }

    public String getAccRel() {
        return accRel;
    }

    public void setAccRel(String accRel) {
        this.accRel = accRel;
    }

    public String getDabApNo() {
        return dabApNo;
    }

    public void setDabApNo(String dabApNo) {
        this.dabApNo = dabApNo;
    }

    public String getDabApNo1() {
        return dabApNo1;
    }

    public void setDabApNo1(String dabApNo1) {
        this.dabApNo1 = dabApNo1;
    }

    public String getDabApNo2() {
        return dabApNo2;
    }

    public void setDabApNo2(String dabApNo2) {
        this.dabApNo2 = dabApNo2;
    }

    public String getDabApNo3() {
        return dabApNo3;
    }

    public void setDabApNo3(String dabApNo3) {
        this.dabApNo3 = dabApNo3;
    }

    public String getDabApNo4() {
        return dabApNo4;
    }

    public void setDabApNo4(String dabApNo4) {
        this.dabApNo4 = dabApNo4;
    }

    public String getOldDabApNo() {
        return oldDabApNo;
    }

    public void setOldDabApNo(String oldDabApNo) {
        this.oldDabApNo = oldDabApNo;
    }

    public String getEvtData() {
        return evtData;
    }

    public void setEvtData(String evtData) {
        this.evtData = evtData;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        loanMk = "0";
    }

    public String getInterValMonth() {
        return interValMonth;
    }

    public void setInterValMonth(String interValMonth) {
        this.interValMonth = interValMonth;
    }

    public String getOldInterValMonth() {
        return oldInterValMonth;
    }

    public void setOldInterValMonth(String oldInterValMonth) {
        this.oldInterValMonth = oldInterValMonth;
    }

	public String getIdnoExist() {
		return idnoExist;
	}

	public void setIdnoExist(String idnoExist) {
		this.idnoExist = idnoExist;
	}

	public String getOldEvtNationTpe() {
		return oldEvtNationTpe;
	}

	public void setOldEvtNationTpe(String oldEvtNationTpe) {
		this.oldEvtNationTpe = oldEvtNationTpe;
	}

	public String getOldEvtNationCodeOption() {
		return oldEvtNationCodeOption;
	}

	public void setOldEvtNationCodeOption(String oldEvtNationCodeOption) {
		this.oldEvtNationCodeOption = oldEvtNationCodeOption;
	}

	public String getOldEvtSex() {
		return oldEvtSex;
	}

	public void setOldEvtSex(String oldEvtSex) {
		this.oldEvtSex = oldEvtSex;
	}

	public String getOldEvtName() {
		return oldEvtName;
	}

	public void setOldEvtName(String oldEvtName) {
		this.oldEvtName = oldEvtName;
	}

	public String getOldBenSex() {
		return oldBenSex;
	}

	public void setOldBenSex(String oldBenSex) {
		this.oldBenSex = oldBenSex;
	}

	public String getOldAccRel() {
		return oldAccRel;
	}

	public void setOldAccRel(String oldAccRel) {
		this.oldAccRel = oldAccRel;
	}

	public String getOldevtData() {
		return oldevtData;
	}

	public void setOldevtData(String oldevtData) {
		this.oldevtData = oldevtData;
	}

	public String getOldinterValMonth() {
		return oldinterValMonth;
	}

	public void setOldinterValMonth(String oldinterValMonth) {
		this.oldinterValMonth = oldinterValMonth;
	}
    
    
}
