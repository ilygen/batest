package tw.gov.bli.ba.receipt.forms;

import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.StringUtility;

/**
 * 受理作業 - 老年年金給付受理作業 (BAAP0D010A)
 *
 * @author Rickychi
 */
public class OldAgeAnnuityReceiptForm extends BaseValidatorForm {
    private String method;

    private BigDecimal baappbaseId;// 資料列編號
    // private String qryIdn;// 查詢條件身分證號
    // private String qryApNo;// 查詢條件受理編號
    private String apNo;// 受理編號
    private String apNo1;// 受理編號-1
    private String apNo2;// 受理編號-2
    private String apNo3;// 受理編號-3
    private String apNo4;// 受理編號-4
    private String evtNationCodeOption;// 國籍下拉選單
    private String apItem;// 申請項目
    private String appDate;// 申請日期
    private String combapMark;// 國勞合併申請註記
    private String apUbno;// 申請單位保險證號
    private String evtIdnNo;// 事故者身分證號
    private String evtName;// 事故者姓名
    private String evtBrDate;// 事故者出生日期
    private String evtSex;// 性別
    private String evtNationTpe;// 國籍別
    private String evtNationCode;// 國籍
    private String evtJobDate;// 離職日期
    private String tel1;// 電話1
    private String tel2;// 電話2
    private String commTyp;// 通訊地址別
    private String commZip;// 通訊郵遞區號
    private String commAddr;// 通訊地址
    private String grdIdnNo;// 法定代理人身分證號
    private String grdName;// 法定代理人姓名
    private String grdBrDate;// 法定代理人出生日期
    private String payTyp;// 給付方式
    private String payBankId;// 金融機構總代號
    private String branchId;// 分支代號
    private String payBankIdBranchId;// 帳號(前)
    private String payEeacc;// 帳號(後)
    private String chkPayBankId;// 帳號複驗 金融機構總代號
    private String chkBranchId;// 帳號複驗 分支代號
    private String chkPayBankIdChkBranchId;// 帳號複驗(前)
    private String chkPayEeacc;// 帳號複驗(後)
    private String cvldtlName;// 戶籍姓名
    private String mobilePhone; // 手機複驗
    private String specialAcc;	// 專戶(扣押戶用)	EthanChen; 20210512; for ba_1100427619
    private String chkSpecialAcc;// 專戶chkbox用
    
    private String oldApItem;// 申請項目
    private String oldAppDate;// 申請日期
    private String oldCombapMark;// 國勞合併申請註記
    private String oldApUbno;// 申請單位保險證號
    private String oldEvtIdnNo;// 事故者身分證號
    private String oldEvtName;// 事故者姓名
    private String oldEvtBrDate;// 事故者出生日期
    private String oldEvtSex;// 性別
    private String oldEvtNationTpe;// 國籍別
    private String oldEvtNationCode;// 國籍
    private String oldEvtJobDate;// 離職日期
    private String oldTel1;// 電話1
    private String oldTel2;// 電話2
    private String oldCommTyp;// 通訊地址別
    private String oldCommZip;// 通訊郵遞區號
    private String oldCommAddr;// 通訊地址
    private String oldGrdIdnNo;// 法定代理人身分證號
    private String oldGrdName;// 法定代理人姓名
    private String oldGrdBrDate;// 法定代理人出生日期
    private String oldPayTyp;// 給付方式
    private String oldPayBankIdBranchId;// 帳號(前)
    private String oldPayEeacc;// 帳號(後)
    private String oldChkPayBankIdChkBranchId;// 帳號復驗 (前)
    private String oldChkPayEeacc;// 帳號復驗 (後)
    private String oldCvldtlName;// 戶籍姓名
    private String oldMobilePhone; // 手機複驗
    
    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getOldMobilePhone() {
        return oldMobilePhone;
    }

    public void setOldMobilePhone(String oldMobilePhone) {
        this.oldMobilePhone = oldMobilePhone;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        ActionErrors errors = super.validate(mapping, request);

        // Validation.xml 驗證若有錯誤則先讓使用者改正所輸入之資料
        if (errors != null && errors.size() > 0)
            return errors;

        return errors;
    }

    public String getAppDateStr() {
        if (StringUtils.isNotBlank(getAppDate())) {
            return DateUtility.changeDateType(getAppDate());
        }
        else {
            return "";
        }
    }

    public String getEvtBrDateStr() {
        if (StringUtils.isNotBlank(getEvtBrDate())) {
            return DateUtility.parseChineseBirtydayString(getEvtBrDate());
        }
        else {
            return "";
        }
    }

    public String getEvtJobDateStr() {
        if (StringUtils.isNotBlank(getEvtJobDate())) {
            return DateUtility.changeDateType(getEvtJobDate());
        }
        else {
            return "";
        }
    }

    public String getGrdBrDateStr() {
        if (StringUtils.isNotBlank(getGrdBrDate())) {
            return DateUtility.parseChineseBirtydayString(getGrdBrDate());
        }
        else {
            return "";
        }
    }

    public String getApNoStr() {
        return getApNo1() + getApNo2() + getApNo3() + getApNo4();
    }

    public String getApNoStrDisplay() {
        return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12);
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getOldApItem() {
        return oldApItem;
    }

    public void setOldApItem(String oldApItem) {
        this.oldApItem = oldApItem;
    }

    public String getOldAppDate() {
        return oldAppDate;
    }

    public void setOldAppDate(String oldAppDate) {
        this.oldAppDate = oldAppDate;
    }

    public String getOldCombapMark() {
        return oldCombapMark;
    }

    public void setOldCombapMark(String oldCombapMark) {
        this.oldCombapMark = oldCombapMark;
    }

    public String getOldApUbno() {
        return oldApUbno;
    }

    public void setOldApUbno(String oldApUbno) {
        this.oldApUbno = oldApUbno;
    }

    public String getOldEvtIdnNo() {
        return oldEvtIdnNo;
    }

    public void setOldEvtIdnNo(String oldEvtIdnNo) {
        this.oldEvtIdnNo = oldEvtIdnNo;
    }

    public String getOldEvtName() {
        return oldEvtName;
    }

    public void setOldEvtName(String oldEvtName) {
        this.oldEvtName = oldEvtName;
    }

    public String getOldEvtBrDate() {
        return oldEvtBrDate;
    }

    public void setOldEvtBrDate(String oldEvtBrDate) {
        this.oldEvtBrDate = oldEvtBrDate;
    }

    public String getOldEvtJobDate() {
        return oldEvtJobDate;
    }

    public void setOldEvtJobDate(String oldEvtJobDate) {
        this.oldEvtJobDate = oldEvtJobDate;
    }

    public String getOldTel1() {
        return oldTel1;
    }

    public void setOldTel1(String oldTel1) {
        this.oldTel1 = oldTel1;
    }

    public String getOldTel2() {
        return oldTel2;
    }

    public void setOldTel2(String oldTel2) {
        this.oldTel2 = oldTel2;
    }

    public String getOldCommTyp() {
        return oldCommTyp;
    }

    public void setOldCommTyp(String oldCommTyp) {
        this.oldCommTyp = oldCommTyp;
    }

    public String getOldCommZip() {
        return oldCommZip;
    }

    public void setOldCommZip(String oldCommZip) {
        this.oldCommZip = oldCommZip;
    }

    public String getOldCommAddr() {
        return oldCommAddr;
    }

    public void setOldCommAddr(String oldCommAddr) {
        this.oldCommAddr = oldCommAddr;
    }

    public String getOldGrdIdnNo() {
        return oldGrdIdnNo;
    }

    public void setOldGrdIdnNo(String oldGrdIdnNo) {
        this.oldGrdIdnNo = oldGrdIdnNo;
    }

    public String getOldGrdName() {
        return oldGrdName;
    }

    public void setOldGrdName(String oldGrdName) {
        this.oldGrdName = oldGrdName;
    }

    public String getOldGrdBrDate() {
        return oldGrdBrDate;
    }

    public void setOldGrdBrDate(String oldGrdBrDate) {
        this.oldGrdBrDate = oldGrdBrDate;
    }

    public String getOldPayTyp() {
        return oldPayTyp;
    }

    public void setOldPayTyp(String oldPayTyp) {
        this.oldPayTyp = oldPayTyp;
    }

    public String getOldPayBankIdBranchId() {
        return oldPayBankIdBranchId;
    }

    public void setOldPayBankIdBranchId(String oldPayBankIdBranchId) {
        this.oldPayBankIdBranchId = oldPayBankIdBranchId;
    }

    public String getOldPayEeacc() {
        return oldPayEeacc;
    }

    public void setOldPayEeacc(String oldPayEeacc) {
        this.oldPayEeacc = oldPayEeacc;
    }



	public String getOldChkPayEeacc() {
		return oldChkPayEeacc;
	}

	public void setOldChkPayEeacc(String oldChkPayEeacc) {
		this.oldChkPayEeacc = oldChkPayEeacc;
	}

	public String getCvldtlName() {
        return cvldtlName;
    }

    public void setCvldtlName(String cvldtlName) {
        this.cvldtlName = cvldtlName;
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
    
    public String getApItem() {
        return apItem;
    }

    public void setApItem(String apItem) {
        this.apItem = apItem;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getCombapMark() {
        return combapMark;
    }

    public void setCombapMark(String combapMark) {
        this.combapMark = combapMark;
    }

    public String getApUbno() {
        return apUbno;
    }

    public void setApUbno(String apUbno) {
        this.apUbno = apUbno;
    }

    public String getEvtIdnNo() {
        return evtIdnNo;
    }

    public void setEvtIdnNo(String evtIdnNo) {
        this.evtIdnNo = evtIdnNo;
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

    public String getEvtSex() {
        return evtSex;
    }

    public void setEvtSex(String evtSex) {
        this.evtSex = evtSex;
    }

    public String getEvtNationTpe() {
        return evtNationTpe;
    }

    public void setEvtNationTpe(String evtNationTpe) {
        this.evtNationTpe = evtNationTpe;
    }

    public String getEvtNationCode() {
        return evtNationCode;
    }

    public void setEvtNationCode(String evtNationCode) {
        this.evtNationCode = evtNationCode;
    }

    public String getEvtJobDate() {
        return evtJobDate;
    }

    public void setEvtJobDate(String evtJobDate) {
        this.evtJobDate = evtJobDate;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getCommTyp() {
        return commTyp;
    }

    public void setCommTyp(String commTyp) {
        this.commTyp = commTyp;
    }

    public String getCommZip() {
        return commZip;
    }

    public void setCommZip(String commZip) {
        this.commZip = commZip;
    }

    public String getCommAddr() {
        return commAddr;
    }

    public void setCommAddr(String commAddr) {
        this.commAddr = commAddr;
    }

    public String getGrdIdnNo() {
        return grdIdnNo;
    }

    public void setGrdIdnNo(String grdIdnNo) {
        this.grdIdnNo = grdIdnNo;
    }

    public String getGrdName() {
        return grdName;
    }

    public void setGrdName(String grdName) {
        this.grdName = grdName;
    }

    public String getGrdBrDate() {
        return grdBrDate;
    }

    public void setGrdBrDate(String grdBrDate) {
        this.grdBrDate = grdBrDate;
    }

    public String getPayTyp() {
        return payTyp;
    }

    public void setPayTyp(String payTyp) {
        this.payTyp = payTyp;
    }

    public String getPayBankIdBranchId() {
    	if(StringUtils.isBlank(payBankId)){
    		payBankId = "";
    	}
    	if(StringUtils.isBlank(branchId)){
    		branchId = "";
    	}
    	payBankIdBranchId = payBankId + branchId;
        return payBankIdBranchId;
    }

    public void setPayBankIdBranchId(String payBankIdBranchId) {
        this.payBankIdBranchId = payBankIdBranchId;
    }

    public String getPayEeacc() {
        return payEeacc;
    }

    public String getChkPayBankIdChkBranchId() {
    	if(StringUtils.isBlank(chkPayBankId)){
    		chkPayBankId = "";
    	}
    	if(StringUtils.isBlank(chkBranchId)){
    		chkBranchId = "";
    	}
    	chkPayBankIdChkBranchId = chkPayBankId + chkBranchId;
    	
		return chkPayBankIdChkBranchId;
	}

	public void setChkPayBankIdChkBranchId(String chkPayBankIdChkBranchId) {
		this.chkPayBankIdChkBranchId = chkPayBankIdChkBranchId;
	}

	public String getChkPayEeacc() {
		return chkPayEeacc;
	}

	public void setChkPayEeacc(String chkPayEeacc) {
		this.chkPayEeacc = chkPayEeacc;
	}

	public void setPayEeacc(String payEeacc) {
        this.payEeacc = payEeacc;
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

	public String getPayBankId() {
		return payBankId;
	}

	public void setPayBankId(String payBankId) {
		this.payBankId = payBankId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getChkPayBankId() {
		return chkPayBankId;
	}

	public void setChkPayBankId(String chkPayBankId) {
		this.chkPayBankId = chkPayBankId;
	}

	public String getChkBranchId() {
		return chkBranchId;
	}

	public void setChkBranchId(String chkBranchId) {
		this.chkBranchId = chkBranchId;
	}

	public String getOldChkPayBankIdChkBranchId() {
		return oldChkPayBankIdChkBranchId;
	}

	public void setOldChkPayBankIdChkBranchId(String oldChkPayBankIdChkBranchId) {
		this.oldChkPayBankIdChkBranchId = oldChkPayBankIdChkBranchId;
	}

	public String getOldEvtSex() {
		return oldEvtSex;
	}

	public void setOldEvtSex(String oldEvtSex) {
		this.oldEvtSex = oldEvtSex;
	}

	public String getOldEvtNationTpe() {
		return oldEvtNationTpe;
	}

	public void setOldEvtNationTpe(String oldEvtNationTpe) {
		this.oldEvtNationTpe = oldEvtNationTpe;
	}

	public String getOldEvtNationCode() {
		return oldEvtNationCode;
	}

	public void setOldEvtNationCode(String oldEvtNationCode) {
		this.oldEvtNationCode = oldEvtNationCode;
	}

	public String getOldCvldtlName() {
		return oldCvldtlName;
	}

	public void setOldCvldtlName(String oldCvldtlName) {
		this.oldCvldtlName = oldCvldtlName;
	}

	public String getSpecialAcc() {
		return specialAcc;
	}

	public void setSpecialAcc(String specialAcc) {
		this.specialAcc = specialAcc;
	}

	public String getChkSpecialAcc() {
		return chkSpecialAcc;
	}

	public void setChkSpecialAcc(String chkSpecialAcc) {
		this.chkSpecialAcc = chkSpecialAcc;
	}
}
