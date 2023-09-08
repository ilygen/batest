package tw.gov.bli.ba.receipt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Baapplog;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 失能年金受理作業 遺屬資料
 * 
 * @author Rickychi
 */
public class DisabledAnnuityReceiptFamCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
    private BigDecimal baappbaseId;// 資料列編號
    private BigDecimal bafamilyId;// 資料列編號
    private BigDecimal bafamilytempId;// 資料列編號
    private String apNo;// 受理編號
    private String seqNo;// 序號
    private String famNationTyp;// 遺屬/眷屬國籍別
    private String famSex;// 遺屬/眷屬性別
    private String famNationCode;// 遺屬/眷屬國籍
    private String famNationCodeOption;// 遺屬/眷屬國籍下拉選單
    private String famName;// 遺屬/眷屬姓名
    private String famAppDate;// 遺屬/眷屬申請日期
    private String famIdnNo;// 遺屬/眷屬身分證號
    private String famBrDate;// 遺屬/眷屬出生日期
    private String famEvtRel;// 遺屬/眷屬與事故者關係
    private String marryDate;// 結婚日期
    private String raiseChildMk;// 配偶扶養註記
    private String studMk;// 在學註記
    private String monIncomeMk;// 每月工作收入註記
    private BigDecimal monIncome;// 每月工作收入金額
    private String handIcapMk;// 領有重度以上身心障礙手冊
    private String interDictMk;// 受禁治產(監護)宣告
    private String schoolCode; //學校代碼

    private List<Baapplog> baapplogList;// 更正記錄檔

    // 頁面顯示轉換
    // [
    public String getFamAppDateStr() {
        if (StringUtils.isNotBlank(getFamAppDate()) && getFamAppDate().length() == 8) {
            return DateUtility.changeDateType(getFamAppDate());
        }
        else {
            return "";
        }
    }

    public String getFamBrDateStr() {
        if (StringUtils.isNotBlank(getFamBrDate()) && getFamBrDate().length() == 8) {
            return DateUtility.changeDateType(getFamBrDate());
        }
        else {
            return "";
        }
    }

    public String getMarryDateStr() {
        if (StringUtils.isNotBlank(getMarryDate()) && getMarryDate().length() == 8) {
            return DateUtility.changeDateType(getMarryDate());
        }
        else {
            return "";
        }
    }

    public String getFamEvtRelStr() {
        String famEvtRelStr = "";
        if ((ConstantKey.BAAPPBASE_BENEVTREL_1).equals(getFamEvtRel())) {
            famEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_1;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_2).equals(getFamEvtRel())) {
            famEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_2;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_3).equals(getFamEvtRel())) {
            famEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_3;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_4).equals(getFamEvtRel())) {
            famEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_4;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_5).equals(getFamEvtRel())) {
            famEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_5;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_6).equals(getFamEvtRel())) {
            famEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_6;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_7).equals(getFamEvtRel())) {
            famEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_7;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_A).equals(getFamEvtRel())) {
            famEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_A;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_C).equals(getFamEvtRel())) {
            famEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_C;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_E).equals(getFamEvtRel())) {
            famEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_E;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_F).equals(getFamEvtRel())) {
            famEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_F;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_N).equals(getFamEvtRel())) {
            famEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_N;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_Z).equals(getFamEvtRel())) {
            famEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_Z;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_O).equals(getFamEvtRel())) {
            famEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_O;
        }
        return famEvtRelStr;
    }

    public String getStudMkStr() {
        String studMkStr = "";
        if (("Y").equals(getStudMk())) {
            studMkStr = "是";
        }
        return studMkStr;
    }

    public String getHandIcapMkStr() {
        String handIcapMkStr = "";
        if (("Y").equals(getHandIcapMk())) {
            handIcapMkStr = "有";
        }
        return handIcapMkStr;
    }
    // ]

    public BigDecimal getBaappbaseId() {
        return baappbaseId;
    }

    public void setBaappbaseId(BigDecimal baappbaseId) {
        this.baappbaseId = baappbaseId;
    }

    public BigDecimal getBafamilytempId() {
        return bafamilytempId;
    }

    public void setBafamilytempId(BigDecimal bafamilytempId) {
        this.bafamilytempId = bafamilytempId;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getFamNationTyp() {
        return famNationTyp;
    }

    public void setFamNationTyp(String famNationTyp) {
        this.famNationTyp = famNationTyp;
    }

    public String getFamSex() {
        return famSex;
    }

    public void setFamSex(String famSex) {
        this.famSex = famSex;
    }

    public String getFamNationCode() {
        return famNationCode;
    }

    public void setFamNationCode(String famNationCode) {
        this.famNationCode = famNationCode;
    }

    public String getFamNationCodeOption() {
        return famNationCodeOption;
    }

    public void setFamNationCodeOption(String famNationCodeOption) {
        this.famNationCodeOption = famNationCodeOption;
    }

    public String getFamName() {
        return famName;
    }

    public void setFamName(String famName) {
        this.famName = famName;
    }

    public String getFamAppDate() {
        return famAppDate;
    }

    public void setFamAppDate(String famAppDate) {
        this.famAppDate = famAppDate;
    }

    public String getFamIdnNo() {
        return famIdnNo;
    }

    public void setFamIdnNo(String famIdnNo) {
        this.famIdnNo = famIdnNo;
    }

    public String getFamBrDate() {
        return famBrDate;
    }

    public void setFamBrDate(String famBrDate) {
        this.famBrDate = famBrDate;
    }

    public String getFamEvtRel() {
        return famEvtRel;
    }

    public void setFamEvtRel(String famEvtRel) {
        this.famEvtRel = famEvtRel;
    }

    public String getMarryDate() {
        return marryDate;
    }

    public void setMarryDate(String marryDate) {
        this.marryDate = marryDate;
    }

    public String getRaiseChildMk() {
        return raiseChildMk;
    }

    public void setRaiseChildMk(String raiseChildMk) {
        this.raiseChildMk = raiseChildMk;
    }

    public String getStudMk() {
        return studMk;
    }

    public void setStudMk(String studMk) {
        this.studMk = studMk;
    }

    public String getMonIncomeMk() {
        return monIncomeMk;
    }

    public void setMonIncomeMk(String monIncomeMk) {
        this.monIncomeMk = monIncomeMk;
    }

    public BigDecimal getMonIncome() {
        return monIncome;
    }

    public void setMonIncome(BigDecimal monIncome) {
        this.monIncome = monIncome;
    }

    public String getHandIcapMk() {
        return handIcapMk;
    }

    public void setHandIcapMk(String handIcapMk) {
        this.handIcapMk = handIcapMk;
    }

    public String getInterDictMk() {
        return interDictMk;
    }

    public void setInterDictMk(String interDictMk) {
        this.interDictMk = interDictMk;
    }

    public List<Baapplog> getBaapplogList() {
        return baapplogList;
    }

    public void setBaapplogList(List<Baapplog> baapplogList) {
        this.baapplogList = baapplogList;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public BigDecimal getBafamilyId() {
        return bafamilyId;
    }

    public void setBafamilyId(BigDecimal bafamilyId) {
        this.bafamilyId = bafamilyId;
    }

	public String getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}

}
