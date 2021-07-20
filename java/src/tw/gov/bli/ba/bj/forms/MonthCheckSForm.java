package tw.gov.bli.ba.bj.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 批次處理 - 線上月試核作業 - 老年年金線上月試核作業 - 查詢頁面  (baba0m080x.jsp)
 * 
 * @author Noctis
 *
 */
public class MonthCheckSForm extends BaseValidatorForm {
    
    private String method;

    private String apNo;// 受理編號
    private String apNo1;// 受理編號-1
    private String apNo2;// 受理編號-2
    private String apNo3;// 受理編號-3
    private String apNo4;// 受理編號-4
    
    private String apNoA;// 受理編號
    private String apNoA1;// 受理編號-1
    private String apNoA2;// 受理編號-2
    private String apNoA3;// 受理編號-3
    private String apNoA4;// 受理編號-4
    
    private String apNoB;// 受理編號
    private String apNoB1;// 受理編號-1
    private String apNoB2;// 受理編號-2
    private String apNoB3;// 受理編號-3
    private String apNoB4;// 受理編號-4
    
    private String apNoC;// 受理編號
    private String apNoC1;// 受理編號-1
    private String apNoC2;// 受理編號-2
    private String apNoC3;// 受理編號-3
    private String apNoC4;// 受理編號-4
    
    private String apNoD;// 受理編號
    private String apNoD1;// 受理編號-1
    private String apNoD2;// 受理編號-2
    private String apNoD3;// 受理編號-3
    private String apNoD4;// 受理編號-4
    
    private String apNoE;// 受理編號
    private String apNoE1;// 受理編號-1
    private String apNoE2;// 受理編號-2
    private String apNoE3;// 受理編號-3
    private String apNoE4;// 受理編號-4
    
    private String apNoF;// 受理編號
    private String apNoF1;// 受理編號-1
    private String apNoF2;// 受理編號-2
    private String apNoF3;// 受理編號-3
    private String apNoF4;// 受理編號-4
    
    private String apNoG;// 受理編號
    private String apNoG1;// 受理編號-1
    private String apNoG2;// 受理編號-2
    private String apNoG3;// 受理編號-3
    private String apNoG4;// 受理編號-4
    
    private String apNoH;// 受理編號
    private String apNoH1;// 受理編號-1
    private String apNoH2;// 受理編號-2
    private String apNoH3;// 受理編號-3
    private String apNoH4;// 受理編號-4
    
    private String apNoI;// 受理編號
    private String apNoI1;// 受理編號-1
    private String apNoI2;// 受理編號-2
    private String apNoI3;// 受理編號-3
    private String apNoI4;// 受理編號-4
    
    private String issuYm;// 核定年月
    
    private String apNoOfConfirm; // 受理編號
    private String bfMonthOfConfirm; //調整前一核定年月
    
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        ActionErrors errors = super.validate(mapping, request);

        // Validation.xml 驗證若有錯誤則先讓使用者改正所輸入之資料
        if (errors != null && errors.size() > 0)
            return errors;

        return errors;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
    	apNo1="";
    	apNo2="";    
    	apNo3="";  
    	apNo4="";  
    	apNoA1="";
    	apNoA2="";    
    	apNoA3="";  
    	apNoA4="";  
    	apNoB1="";
    	apNoB2="";    
    	apNoB3="";  
    	apNoB4="";  
    	apNoC1="";
    	apNoC2="";    
    	apNoC3="";  
    	apNoC4="";  
    	apNoD1="";
    	apNoD2="";    
    	apNoD3="";  
    	apNoD4="";  
    	apNoE1="";
    	apNoE2="";    
    	apNoE3="";  
    	apNoE4="";  
    	apNoF1="";
    	apNoF2="";    
    	apNoF3="";  
    	apNoF4="";  
    	apNoG1="";
    	apNoG2="";    
    	apNoG3="";  
    	apNoG4="";  
    	apNoH1="";
    	apNoH2="";    
    	apNoH3="";  
    	apNoH4="";  
    	apNoI1="";
    	apNoI2="";    
    	apNoI3="";  
    	apNoI4="";  
    }
    
    public String getApNoStr() {
        return getApNo1() + getApNo2() + getApNo3() + getApNo4();
    }

    public String getApNoStrDisplay() {
        return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12);
    }
    
    public String getApNoAStr() {
        return getApNoA1() + getApNoA2() + getApNoA3() + getApNoA4();
    }

    public String getApNoAStrDisplay() {
        return getApNoA().substring(0, 1) + "-" + getApNoA().substring(1, 2) + "-" + getApNoA().substring(2, 7) + "-" + getApNoA().substring(7, 12);
    }
    
    public String getApNoBStr() {
        return getApNoB1() + getApNoB2() + getApNoB3() + getApNoB4();
    }

    public String getApNoBStrDisplay() {
        return getApNoB().substring(0, 1) + "-" + getApNoB().substring(1, 2) + "-" + getApNoB().substring(2, 7) + "-" + getApNoB().substring(7, 12);
    }
    
    public String getApNoCStr() {
        return getApNoC1() + getApNoC2() + getApNoC3() + getApNoC4();
    }

    public String getApNoCStrDisplay() {
        return getApNoC().substring(0, 1) + "-" + getApNoC().substring(1, 2) + "-" + getApNoC().substring(2, 7) + "-" + getApNoC().substring(7, 12);
    }
    
    public String getApNoDStr() {
        return getApNoD1() + getApNoD2() + getApNoD3() + getApNoD4();
    }

    public String getApNoDStrDisplay() {
        return getApNoD().substring(0, 1) + "-" + getApNoD().substring(1, 2) + "-" + getApNoD().substring(2, 7) + "-" + getApNoD().substring(7, 12);
    }
    
    public String getApNoEStr() {
        return getApNoE1() + getApNoE2() + getApNoE3() + getApNoE4();
    }

    public String getApNoEStrDisplay() {
        return getApNoE().substring(0, 1) + "-" + getApNoE().substring(1, 2) + "-" + getApNoE().substring(2, 7) + "-" + getApNoE().substring(7, 12);
    }
    
    public String getApNoFStr() {
        return getApNoF1() + getApNoF2() + getApNoF3() + getApNoF4();
    }

    public String getApNoFStrDisplay() {
        return getApNoF().substring(0, 1) + "-" + getApNoF().substring(1, 2) + "-" + getApNoF().substring(2, 7) + "-" + getApNoF().substring(7, 12);
    }
    
    public String getApNoGStr() {
        return getApNoG1() + getApNoG2() + getApNoG3() + getApNoG4();
    }

    public String getApNoGStrDisplay() {
        return getApNoG().substring(0, 1) + "-" + getApNoG().substring(1, 2) + "-" + getApNoG().substring(2, 7) + "-" + getApNoG().substring(7, 12);
    }
    
    public String getApNoHStr() {
        return getApNoH1() + getApNoH2() + getApNoH3() + getApNoH4();
    }

    public String getApNoHStrDisplay() {
        return getApNoH().substring(0, 1) + "-" + getApNoH().substring(1, 2) + "-" + getApNoH().substring(2, 7) + "-" + getApNoH().substring(7, 12);
    }
    
    public String getApNoIStr() {
        return getApNoI1() + getApNoI2() + getApNoI3() + getApNoI4();
    }

    public String getApNoIStrDisplay() {
        return getApNoI().substring(0, 1) + "-" + getApNoI().substring(1, 2) + "-" + getApNoI().substring(2, 7) + "-" + getApNoI().substring(7, 12);
    }
    
    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }

	public String getApNoOfConfirm() {
		return apNoOfConfirm;
	}

	public void setApNoOfConfirm(String apNoOfConfirm) {
		this.apNoOfConfirm = apNoOfConfirm;
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

	public String getApNoA() {
		return apNoA;
	}

	public void setApNoA(String apNoA) {
		this.apNoA = apNoA;
	}

	public String getApNoA1() {
		return apNoA1;
	}

	public void setApNoA1(String apNoA1) {
		this.apNoA1 = apNoA1;
	}

	public String getApNoA2() {
		return apNoA2;
	}

	public void setApNoA2(String apNoA2) {
		this.apNoA2 = apNoA2;
	}

	public String getApNoA3() {
		return apNoA3;
	}

	public void setApNoA3(String apNoA3) {
		this.apNoA3 = apNoA3;
	}

	public String getApNoA4() {
		return apNoA4;
	}

	public void setApNoA4(String apNoA4) {
		this.apNoA4 = apNoA4;
	}

	public String getApNoB() {
		return apNoB;
	}

	public void setApNoB(String apNoB) {
		this.apNoB = apNoB;
	}

	public String getApNoB1() {
		return apNoB1;
	}

	public void setApNoB1(String apNoB1) {
		this.apNoB1 = apNoB1;
	}

	public String getApNoB2() {
		return apNoB2;
	}

	public void setApNoB2(String apNoB2) {
		this.apNoB2 = apNoB2;
	}

	public String getApNoB3() {
		return apNoB3;
	}

	public void setApNoB3(String apNoB3) {
		this.apNoB3 = apNoB3;
	}

	public String getApNoB4() {
		return apNoB4;
	}

	public void setApNoB4(String apNoB4) {
		this.apNoB4 = apNoB4;
	}

	public String getApNoC() {
		return apNoC;
	}

	public void setApNoC(String apNoC) {
		this.apNoC = apNoC;
	}

	public String getApNoC1() {
		return apNoC1;
	}

	public void setApNoC1(String apNoC1) {
		this.apNoC1 = apNoC1;
	}

	public String getApNoC2() {
		return apNoC2;
	}

	public void setApNoC2(String apNoC2) {
		this.apNoC2 = apNoC2;
	}

	public String getApNoC3() {
		return apNoC3;
	}

	public void setApNoC3(String apNoC3) {
		this.apNoC3 = apNoC3;
	}

	public String getApNoC4() {
		return apNoC4;
	}

	public void setApNoC4(String apNoC4) {
		this.apNoC4 = apNoC4;
	}

	public String getApNoD() {
		return apNoD;
	}

	public void setApNoD(String apNoD) {
		this.apNoD = apNoD;
	}

	public String getApNoD1() {
		return apNoD1;
	}

	public void setApNoD1(String apNoD1) {
		this.apNoD1 = apNoD1;
	}

	public String getApNoD2() {
		return apNoD2;
	}

	public void setApNoD2(String apNoD2) {
		this.apNoD2 = apNoD2;
	}

	public String getApNoD3() {
		return apNoD3;
	}

	public void setApNoD3(String apNoD3) {
		this.apNoD3 = apNoD3;
	}

	public String getApNoD4() {
		return apNoD4;
	}

	public void setApNoD4(String apNoD4) {
		this.apNoD4 = apNoD4;
	}

	public String getApNoE() {
		return apNoE;
	}

	public void setApNoE(String apNoE) {
		this.apNoE = apNoE;
	}

	public String getApNoE1() {
		return apNoE1;
	}

	public void setApNoE1(String apNoE1) {
		this.apNoE1 = apNoE1;
	}

	public String getApNoE2() {
		return apNoE2;
	}

	public void setApNoE2(String apNoE2) {
		this.apNoE2 = apNoE2;
	}

	public String getApNoE3() {
		return apNoE3;
	}

	public void setApNoE3(String apNoE3) {
		this.apNoE3 = apNoE3;
	}

	public String getApNoE4() {
		return apNoE4;
	}

	public void setApNoE4(String apNoE4) {
		this.apNoE4 = apNoE4;
	}

	public String getApNoF() {
		return apNoF;
	}

	public void setApNoF(String apNoF) {
		this.apNoF = apNoF;
	}

	public String getApNoF1() {
		return apNoF1;
	}

	public void setApNoF1(String apNoF1) {
		this.apNoF1 = apNoF1;
	}

	public String getApNoF2() {
		return apNoF2;
	}

	public void setApNoF2(String apNoF2) {
		this.apNoF2 = apNoF2;
	}

	public String getApNoF3() {
		return apNoF3;
	}

	public void setApNoF3(String apNoF3) {
		this.apNoF3 = apNoF3;
	}

	public String getApNoF4() {
		return apNoF4;
	}

	public void setApNoF4(String apNoF4) {
		this.apNoF4 = apNoF4;
	}

	public String getApNoG() {
		return apNoG;
	}

	public void setApNoG(String apNoG) {
		this.apNoG = apNoG;
	}

	public String getApNoG1() {
		return apNoG1;
	}

	public void setApNoG1(String apNoG1) {
		this.apNoG1 = apNoG1;
	}

	public String getApNoG2() {
		return apNoG2;
	}

	public void setApNoG2(String apNoG2) {
		this.apNoG2 = apNoG2;
	}

	public String getApNoG3() {
		return apNoG3;
	}

	public void setApNoG3(String apNoG3) {
		this.apNoG3 = apNoG3;
	}

	public String getApNoG4() {
		return apNoG4;
	}

	public void setApNoG4(String apNoG4) {
		this.apNoG4 = apNoG4;
	}

	public String getApNoH() {
		return apNoH;
	}

	public void setApNoH(String apNoH) {
		this.apNoH = apNoH;
	}

	public String getApNoH1() {
		return apNoH1;
	}

	public void setApNoH1(String apNoH1) {
		this.apNoH1 = apNoH1;
	}

	public String getApNoH2() {
		return apNoH2;
	}

	public void setApNoH2(String apNoH2) {
		this.apNoH2 = apNoH2;
	}

	public String getApNoH3() {
		return apNoH3;
	}

	public void setApNoH3(String apNoH3) {
		this.apNoH3 = apNoH3;
	}

	public String getApNoH4() {
		return apNoH4;
	}

	public void setApNoH4(String apNoH4) {
		this.apNoH4 = apNoH4;
	}

	public String getApNoI() {
		return apNoI;
	}

	public void setApNoI(String apNoI) {
		this.apNoI = apNoI;
	}

	public String getApNoI1() {
		return apNoI1;
	}

	public void setApNoI1(String apNoI1) {
		this.apNoI1 = apNoI1;
	}

	public String getApNoI2() {
		return apNoI2;
	}

	public void setApNoI2(String apNoI2) {
		this.apNoI2 = apNoI2;
	}

	public String getApNoI3() {
		return apNoI3;
	}

	public void setApNoI3(String apNoI3) {
		this.apNoI3 = apNoI3;
	}

	public String getApNoI4() {
		return apNoI4;
	}

	public void setApNoI4(String apNoI4) {
		this.apNoI4 = apNoI4;
	}

	public String getBfMonthOfConfirm() {
		return bfMonthOfConfirm;
	}

	public void setBfMonthOfConfirm(String bfMonthOfConfirm) {
		this.bfMonthOfConfirm = bfMonthOfConfirm;
	}

	public String getIssuYm() {
		return issuYm;
	}

	public void setIssuYm(String issuYm) {
		this.issuYm = issuYm;
	}
	
}
