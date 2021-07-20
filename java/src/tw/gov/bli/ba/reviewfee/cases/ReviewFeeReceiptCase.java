package tw.gov.bli.ba.reviewfee.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 複檢費用受理作業
 * 
 * @author Goston
 */
public class ReviewFeeReceiptCase implements Serializable {
    // Header
    // [
    // BABCML7
    private String reApNo; // 複檢費用受理編號
    private BigDecimal apSeq; // 複檢費用申請序

    // BAAPPBASE
    private BigDecimal baappbaseId; // 資料列編號
    private String apNo; // 受理編號
    private String seqNo; // 序號
    private String appDate; // 申請日期
    private String evtName; // 事故者姓名
    private String evtIdnNo; // 事故者身分證號
    private String evtBrDate; // 事故者出生日期
    private String evtJobDate; // 診斷失能日期

    // BAAPPEXPAND
    private BigDecimal baappexpandId; // 資料編號
    private String criInJdp1; // 失能項目 1
    private String criInJdp2; // 失能項目 2
    private String criInJdp3; // 失能項目 3
    private String criInJdp4; // 失能項目 4
    private String criInJdp5; // 失能項目 5
    private String criInJdp6; // 失能項目 6
    private String criInJdp7; // 失能項目 7
    private String criInJdp8; // 失能項目 8
    private String criInJdp9; // 失能項目 9
    private String criInJdp10; // 失能項目 10
    private String criInJcl1; // 失能等級 1
    private String criInJcl2; // 失能等級 2
    private String criInJcl3; // 失能等級 3
    private String evTyp; // 傷病分類
    private String evTypName; // 傷病分類 - 中文 (取自 BAPARAM)
    // ]

    // BABCML7 - 複檢費用主檔明細資料
    private List<ReviewFeeReceiptDetailCase> detailList;

    private ReviewFeeReceiptDetailCase addCaseData; // 用來存放新增用的 Case
    private ReviewFeeReceiptDetailCase modifyCaseData; // 用來存放修改用的 Case

    /**
     * 複檢費用受理編號
     */
    public String getReApNoString() {
        if (StringUtils.length(reApNo) == ConstantKey.APNO_LENGTH)
            return reApNo.substring(0, 1) + "-" + reApNo.substring(1, 2) + "-" + reApNo.substring(2, 7) + "-" + reApNo.substring(7, 12);
        else
            return StringUtils.defaultString(reApNo);
    }

    /**
     * 受理編號
     * 
     * @return
     */
    public String getApNoString() {
        if (StringUtils.length(apNo) == ConstantKey.APNO_LENGTH)
            return apNo.substring(0, 1) + "-" + apNo.substring(1, 2) + "-" + apNo.substring(2, 7) + "-" + apNo.substring(7, 12);
        else
            return StringUtils.defaultString(apNo);
    }

    /**
     * 申請日期
     * 
     * @return
     */
    public String getAppDateString() {
        return DateUtility.formatChineseDateString(appDate, false);
    }

    /**
     * 事故者出生日期
     * 
     * @return
     */
    public String getEvtBrDateString() {
        return DateUtility.formatChineseDateString(evtBrDate, false);
    }

    /**
     * 診斷失能日期
     * 
     * @return
     */
    public String getEvtJobDateString() {
        return DateUtility.formatChineseDateString(evtJobDate, false);
    }

    /**
     * 失能項目 表示字串
     * 
     * @return
     */
    public String getCriInJdpString() {
        StringBuffer str = new StringBuffer("");

        if (StringUtils.isNotBlank(criInJdp1))
            str.append("," + criInJdp1);
        if (StringUtils.isNotBlank(criInJdp2))
            str.append("," + criInJdp2);
        if (StringUtils.isNotBlank(criInJdp3))
            str.append("," + criInJdp3);
        if (StringUtils.isNotBlank(criInJdp4))
            str.append("," + criInJdp4);
        if (StringUtils.isNotBlank(criInJdp5))
            str.append("," + criInJdp5);
        if (StringUtils.isNotBlank(criInJdp6))
            str.append("," + criInJdp6);
        if (StringUtils.isNotBlank(criInJdp7))
            str.append("," + criInJdp7);
        if (StringUtils.isNotBlank(criInJdp8))
            str.append("," + criInJdp8);
        if (StringUtils.isNotBlank(criInJdp9))
            str.append("," + criInJdp9);
        if (StringUtils.isNotBlank(criInJdp10))
            str.append("," + criInJdp10);

        if (StringUtils.isNotBlank(str.toString()))
            return StringUtils.substring(str.toString(), 1, str.toString().length()); // 去掉開頭的逗號
        else
            return str.toString();
    }

    /**
     * 失能等級 表示字串
     * 
     * @return
     */
    public String getCriInJclString() {
        StringBuffer str = new StringBuffer("");

        if (StringUtils.isNotBlank(criInJcl1))
            str.append("," + criInJcl1);
        if (StringUtils.isNotBlank(criInJcl2))
            str.append("," + criInJcl2);
        if (StringUtils.isNotBlank(criInJcl3))
            str.append("," + criInJcl3);

        if (StringUtils.isNotBlank(str.toString()))
            return StringUtils.substring(str.toString(), 1, str.toString().length()); // 去掉開頭的逗號
        else
            return str.toString();
    }

    /**
     * 傷病分類 表示字串
     * 
     * @return
     */
    public String getEvTypString() {
        StringBuffer str = new StringBuffer("");

        if (StringUtils.isNotBlank(evTyp))
            str.append("-" + evTyp);
        if (StringUtils.isNotBlank(evTypName))
            str.append("-" + evTypName);

        if (StringUtils.isNotBlank(str.toString()))
            return StringUtils.substring(str.toString(), 1, str.toString().length()); // 去掉開頭的 Dash 符號
        else
            return str.toString();
    }

    /**
     * 加入一筆 複檢費用主檔明細資料 到 detailList
     * 
     * @param detailData
     */
    public void addDetailData(ReviewFeeReceiptDetailCase detailData) {
        if (detailList == null)
            detailList = new ArrayList<ReviewFeeReceiptDetailCase>();

        if (detailData != null)
            detailList.add(detailData);
    }

    /**
     * 取得指定的 複檢費用申請序 的 複檢費用主檔明細資料
     * 
     * @param apSeq 複檢費用申請序
     * @return
     */
    public ReviewFeeReceiptDetailCase getDetailDataByApSeq(BigDecimal apSeq) {
        if (apSeq == null)
            return null;

        for (ReviewFeeReceiptDetailCase detailData : detailList) {
            if (detailData.getApSeq().compareTo(apSeq) == 0)
                return detailData;
        }

        return null;
    }

    public ReviewFeeReceiptCase() {
        detailList = new ArrayList<ReviewFeeReceiptDetailCase>();
    }

    public String getReApNo() {
        return reApNo;
    }

    public void setReApNo(String reApNo) {
        this.reApNo = reApNo;
    }

    public BigDecimal getApSeq() {
        return apSeq;
    }

    public void setApSeq(BigDecimal apSeq) {
        this.apSeq = apSeq;
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

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public String getEvtIdnNo() {
        return evtIdnNo;
    }

    public void setEvtIdnNo(String evtIdnNo) {
        this.evtIdnNo = evtIdnNo;
    }

    public String getEvtBrDate() {
        return evtBrDate;
    }

    public void setEvtBrDate(String evtBrDate) {
        this.evtBrDate = evtBrDate;
    }

    public String getEvtJobDate() {
        return evtJobDate;
    }

    public void setEvtJobDate(String evtJobDate) {
        this.evtJobDate = evtJobDate;
    }

    public String getCriInJdp1() {
        return criInJdp1;
    }

    public void setCriInJdp1(String criInJdp1) {
        this.criInJdp1 = criInJdp1;
    }

    public String getCriInJdp2() {
        return criInJdp2;
    }

    public void setCriInJdp2(String criInJdp2) {
        this.criInJdp2 = criInJdp2;
    }

    public String getCriInJdp3() {
        return criInJdp3;
    }

    public void setCriInJdp3(String criInJdp3) {
        this.criInJdp3 = criInJdp3;
    }

    public String getCriInJdp4() {
        return criInJdp4;
    }

    public void setCriInJdp4(String criInJdp4) {
        this.criInJdp4 = criInJdp4;
    }

    public String getCriInJdp5() {
        return criInJdp5;
    }

    public void setCriInJdp5(String criInJdp5) {
        this.criInJdp5 = criInJdp5;
    }

    public String getCriInJdp6() {
        return criInJdp6;
    }

    public void setCriInJdp6(String criInJdp6) {
        this.criInJdp6 = criInJdp6;
    }

    public String getCriInJdp7() {
        return criInJdp7;
    }

    public void setCriInJdp7(String criInJdp7) {
        this.criInJdp7 = criInJdp7;
    }

    public String getCriInJdp8() {
        return criInJdp8;
    }

    public void setCriInJdp8(String criInJdp8) {
        this.criInJdp8 = criInJdp8;
    }

    public String getCriInJdp9() {
        return criInJdp9;
    }

    public void setCriInJdp9(String criInJdp9) {
        this.criInJdp9 = criInJdp9;
    }

    public String getCriInJdp10() {
        return criInJdp10;
    }

    public void setCriInJdp10(String criInJdp10) {
        this.criInJdp10 = criInJdp10;
    }

    public String getCriInJcl1() {
        return criInJcl1;
    }

    public void setCriInJcl1(String criInJcl1) {
        this.criInJcl1 = criInJcl1;
    }

    public String getCriInJcl2() {
        return criInJcl2;
    }

    public void setCriInJcl2(String criInJcl2) {
        this.criInJcl2 = criInJcl2;
    }

    public String getCriInJcl3() {
        return criInJcl3;
    }

    public void setCriInJcl3(String criInJcl3) {
        this.criInJcl3 = criInJcl3;
    }

    public String getEvTyp() {
        return evTyp;
    }

    public void setEvTyp(String evTyp) {
        this.evTyp = evTyp;
    }

    public String getEvTypName() {
        return evTypName;
    }

    public void setEvTypName(String evTypName) {
        this.evTypName = evTypName;
    }

    public BigDecimal getBaappbaseId() {
        return baappbaseId;
    }

    public void setBaappbaseId(BigDecimal baappbaseId) {
        this.baappbaseId = baappbaseId;
    }

    public BigDecimal getBaappexpandId() {
        return baappexpandId;
    }

    public void setBaappexpandId(BigDecimal baappexpandId) {
        this.baappexpandId = baappexpandId;
    }

    public List<ReviewFeeReceiptDetailCase> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<ReviewFeeReceiptDetailCase> detailList) {
        this.detailList = detailList;
    }

    public ReviewFeeReceiptDetailCase getAddCaseData() {
        return addCaseData;
    }

    public void setAddCaseData(ReviewFeeReceiptDetailCase addCaseData) {
        this.addCaseData = addCaseData;
    }

    public ReviewFeeReceiptDetailCase getModifyCaseData() {
        return modifyCaseData;
    }

    public void setModifyCaseData(ReviewFeeReceiptDetailCase modifyCaseData) {
        this.modifyCaseData = modifyCaseData;
    }

}
