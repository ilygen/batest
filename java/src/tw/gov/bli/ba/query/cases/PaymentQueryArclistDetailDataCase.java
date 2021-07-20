package tw.gov.bli.ba.query.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 給付查詢作業 歸檔記錄-明細資料
 * 
 * @author jerry
 */
public class PaymentQueryArclistDetailDataCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
    private String caseTyp; // 案件類別
    private String issuYm; // 核定年月
    private String chkMan; // 審核人員
    private String exeMan; // 決行人員
    private String exeDate;// 決行日期
    private BigDecimal arcPg;// 歸檔頁次
    private String arcDate;// 歸檔日期(印表日期)
    private String delMl;// 刪除註記

    public String getCaseTypStr() {
        // 轉換 案件類別
        if ((ConstantKey.BAAPPBASE_CASETYP_1).equals(caseTyp)) {
            return ConstantKey.BAAPPBASE_CASETYP_STR_1;
        }
        else if ((ConstantKey.BAAPPBASE_CASETYP_2).equals(caseTyp)) {
            return ConstantKey.BAAPPBASE_CASETYP_STR_2;
        }
        else if ((ConstantKey.BAAPPBASE_CASETYP_3).equals(caseTyp)) {
            return ConstantKey.BAAPPBASE_CASETYP_STR_3;
        }
        else if ((ConstantKey.BAAPPBASE_CASETYP_4).equals(caseTyp)) {
            return ConstantKey.BAAPPBASE_CASETYP_STR_4;
        }
        else if ((ConstantKey.BAAPPBASE_CASETYP_5).equals(caseTyp)) {
            return ConstantKey.BAAPPBASE_CASETYP_STR_5;
        }
        else if ((ConstantKey.BAAPPBASE_CASETYP_6).equals(caseTyp)) {
            return ConstantKey.BAAPPBASE_CASETYP_STR_6;
        }
        else {
            return "";
        }
    }

    public String getIssuYmChinese() {
        return StringUtils.trimToEmpty(DateUtility.changeWestYearMonthType(issuYm));
    }

    public String getExeDateChinese() {
        if (StringUtils.length(exeDate) == 8)
            return StringUtils.trimToEmpty(DateUtility.changeDateType(exeDate));
        else
            return StringUtils.trimToEmpty(exeDate);
    }

    public String getArcDateChinese() {
        if (StringUtils.length(arcDate) == 8)
            return StringUtils.trimToEmpty(DateUtility.changeDateType(arcDate));
        else
            return StringUtils.trimToEmpty(arcDate);
    }

    public String getCaseTyp() {
        return caseTyp;
    }

    public void setCaseTyp(String caseTyp) {
        this.caseTyp = caseTyp;
    }

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String getChkMan() {
        return chkMan;
    }

    public void setChkMan(String chkMan) {
        this.chkMan = chkMan;
    }

    public String getExeMan() {
        return exeMan;
    }

    public void setExeMan(String exeMan) {
        this.exeMan = exeMan;
    }

    public String getExeDate() {
        return exeDate;
    }

    public void setExeDate(String exeDate) {
        this.exeDate = exeDate;
    }

    public BigDecimal getArcPg() {
        return arcPg;
    }

    public void setArcPg(BigDecimal arcPg) {
        this.arcPg = arcPg;
    }

    public String getArcDate() {
        return arcDate;
    }

    public void setArcDate(String arcDate) {
        this.arcDate = arcDate;
    }

    public String getDelMl() {
        return delMl;
    }

    public void setDelMl(String delMl) {
        this.delMl = delMl;
    }

}
