/**
 * 
 */
package tw.gov.bli.ba.query.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 給付查詢作業 國保資料
 * 
 * @author Rickychi
 */
public class PaymentQueryNpDataCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
    private String apNo; // 受理編號
    private String payYms;// 首次發放起月
    private String labMerge;// 併計勞保年資
    private String dabLevel;// 障礙等級 0:無 4:輕度 3:中度 2:重度1:極重度
    private String dabType; // 障礙類別
    private String dabPart; // 障礙鑑定部位
    private String manChkFlg; // 人工審核結果
    private BigDecimal valSeni; // 國保年資 月
    private BigDecimal valSenid; // 國保年資 日
    private String issuYm; // 核定年月
    private String valSeniSrt; // 國保年資字串
    private String mapNoStr; // 頁面片段是否有 關聯國保資料的受理編號使用

    // private List<PaymentQueryNpIssuDataCase> issuDataList;// 核定資料

    // 頁面顯示轉換
    // [
    public String getApNoStrDisplay() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH)
            return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12);
        else
            return "";
    }

    public String getPayYmsStr() {
        if (StringUtils.isNotBlank(payYms) && payYms.length() == 6) {
            return DateUtility.changeWestYearMonthType(payYms);
        }
        else {
            return payYms;
        }
    }

    public String getPayYmsString() {
        if (StringUtils.isNotBlank(payYms) && payYms.length() == 6) {
            String payYmsStr = DateUtility.changeWestYearMonthType(payYms);
            return StringUtils.substring(payYmsStr, 0, 3) + "年" + StringUtils.substring(payYmsStr, 3, 5) + "月";

        }
        else {
            return payYms;
        }
    }

    public String getIssuYmStr() {
        if (StringUtils.isNotBlank(issuYm) && issuYm.length() == 6) {
            return DateUtility.changeWestYearMonthType(issuYm);
        }
        else {
            return issuYm;
        }
    }

    public String getManChkFlgStr() {
        String manChkFlgStr = ConstantKey.PAYMENT_REVIEW_ACCEPTMK_CODESTR_OTHER;
        if (StringUtils.isNotBlank(manChkFlg)) {
            // 轉換 人工審核結果
            if ((ConstantKey.PAYMENT_REVIEW_MANCHKMK_Y).equals(manChkFlg)) {
                manChkFlgStr = ConstantKey.PAYMENT_REVIEW_MANCHKMK_CODESTR_Y;
            }
            else if ((ConstantKey.PAYMENT_REVIEW_MANCHKMK_N).equals(manChkFlg)) {
                manChkFlgStr = ConstantKey.PAYMENT_REVIEW_MANCHKMK_CODESTR_N;
            }
        }
        return manChkFlgStr;
    }

    // ]
    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getPayYms() {
        return payYms;
    }

    public void setPayYms(String payYms) {
        this.payYms = payYms;
    }

    public String getLabMerge() {
        return labMerge;
    }

    public void setLabMerge(String labMerge) {
        this.labMerge = labMerge;
    }

    public String getDabLevel() {
        return dabLevel;
    }

    public void setDabLevel(String dabLevel) {
        this.dabLevel = dabLevel;
    }

    public String getDabType() {
        return dabType;
    }

    public void setDabType(String dabType) {
        this.dabType = dabType;
    }

    public String getDabPart() {
        return dabPart;
    }

    public void setDabPart(String dabPart) {
        this.dabPart = dabPart;
    }

    public String getManChkFlg() {
        return manChkFlg;
    }

    public void setManChkFlg(String manChkFlg) {
        this.manChkFlg = manChkFlg;
    }

    public BigDecimal getValSeni() {
        return valSeni;
    }

    public void setValSeni(BigDecimal valSeni) {
        this.valSeni = valSeni;
    }

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String getValSeniSrt() {
        return valSeniSrt;
    }

    public void setValSeniSrt(String valSeniSrt) {
        this.valSeniSrt = valSeniSrt;
    }

    public String getMapNoStr() {
        return mapNoStr;
    }

    public void setMapNoStr(String mapNoStr) {
        this.mapNoStr = mapNoStr;
    }

    public BigDecimal getValSenid() {
        return valSenid;
    }

    public void setValSenid(BigDecimal valSenid) {
        this.valSenid = valSenid;
    }

}
