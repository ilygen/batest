package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 * Case for 月核定給付撥款總額表 (一個出帳類別一個 Case)
 * 
 * @author Goston
 */
public class MonthlyRpt07Case implements Serializable {
    private String payCode; // 給付別

    // HEADER
    private String taTyp2; // 出帳類別
    private String mfileName; // 媒體檔案名稱 - 檔案名稱
    private String payDate2; // 出帳日期 - 撥款日期

    // 新案相關資料 (issuTyp = 1)
    // [
    private String dataCountIssuTyp1; // 筆數 - 各 核付分類 的筆數
    private String amt2IssuTyp1; // 交易金額 - 各 核付分類 的金額
    // ]

    // 續發相關資料 (issuTyp = 2)
    // [
    private String dataCountIssuTyp2; // 筆數 - 各 核付分類 的筆數
    private String amt2IssuTyp2; // 交易金額 - 各 核付分類 的金額
    // ]
    
    // 結案相關資料 (issuTyp = 4)
    // [
    private String dataCountIssuTyp4; // 筆數 - 各 核付分類 的筆數
    private String amt2IssuTyp4; // 交易金額 - 各 核付分類 的金額
    // ]    

    // 補發相關資料 (issuTyp = 5)
    // [
    private String dataCountIssuTyp5; // 筆數 - 各 核付分類 的筆數
    private String amt2IssuTyp5; // 交易金額 - 各 核付分類 的金額
    // ]

    // 改匯相關資料 (issuTyp = A)
    // [
    private String dataCountIssuTypA; // 筆數 - 各 核付分類 的筆數
    private String amt2IssuTypA; // 交易金額 - 各 核付分類 的金額
    // ]

    // MIDDLE
    private List<MonthlyRpt07MiddleCase> middleList;

    // FOOTER
    private List<MonthlyRpt07FooterCase> footerList;

    /**
     * 給付別中文名稱
     * 
     * @return
     */
    public String getPayCodeString() {
        if (StringUtils.equalsIgnoreCase(payCode, "L"))
            return "老年年金";
        else if (StringUtils.equalsIgnoreCase(payCode, "K"))
            return "失能年金";
        else if (StringUtils.equalsIgnoreCase(payCode, "S"))
            return "遺屬年金";
        else
            return "　　年金";
    }

    /**
     * 取得 總筆數 及 輸入資料筆數 (總筆數 = 輸入資料筆數)
     */
    public BigDecimal getSumDataCount() {
        BigDecimal sumDataCount = new BigDecimal(0);
        if (StringUtils.isNotBlank(StringUtils.trimToEmpty(dataCountIssuTyp1)))
            sumDataCount = sumDataCount.add(new BigDecimal(StringUtils.trimToEmpty(dataCountIssuTyp1)));
        if (StringUtils.isNotBlank(StringUtils.trimToEmpty(dataCountIssuTyp2)))
            sumDataCount = sumDataCount.add(new BigDecimal(StringUtils.trimToEmpty(dataCountIssuTyp2)));
        if (StringUtils.isNotBlank(StringUtils.trimToEmpty(dataCountIssuTyp4)))
            sumDataCount = sumDataCount.add(new BigDecimal(StringUtils.trimToEmpty(dataCountIssuTyp4)));        
        if (StringUtils.isNotBlank(StringUtils.trimToEmpty(dataCountIssuTyp5)))
            sumDataCount = sumDataCount.add(new BigDecimal(StringUtils.trimToEmpty(dataCountIssuTyp5)));
        if (StringUtils.isNotBlank(StringUtils.trimToEmpty(dataCountIssuTypA)))
            sumDataCount = sumDataCount.add(new BigDecimal(StringUtils.trimToEmpty(dataCountIssuTypA)));
        return sumDataCount;
    }

    /**
     * 取得 輸出筆數 (輸出筆數 = 總筆數 + 2)
     * 
     * @return
     */
    public BigDecimal getOutputDataCount() {
        BigDecimal outputDataCount = new BigDecimal(0);
        if (StringUtils.isNotBlank(StringUtils.trimToEmpty(dataCountIssuTyp1)))
            outputDataCount = outputDataCount.add(new BigDecimal(StringUtils.trimToEmpty(dataCountIssuTyp1)));
        if (StringUtils.isNotBlank(StringUtils.trimToEmpty(dataCountIssuTyp2)))
            outputDataCount = outputDataCount.add(new BigDecimal(StringUtils.trimToEmpty(dataCountIssuTyp2)));
        if (StringUtils.isNotBlank(StringUtils.trimToEmpty(dataCountIssuTyp4)))
            outputDataCount = outputDataCount.add(new BigDecimal(StringUtils.trimToEmpty(dataCountIssuTyp4)));        
        if (StringUtils.isNotBlank(StringUtils.trimToEmpty(dataCountIssuTyp5)))
            outputDataCount = outputDataCount.add(new BigDecimal(StringUtils.trimToEmpty(dataCountIssuTyp5)));
        if (StringUtils.isNotBlank(StringUtils.trimToEmpty(dataCountIssuTypA)))
            outputDataCount = outputDataCount.add(new BigDecimal(StringUtils.trimToEmpty(dataCountIssuTypA)));
        return outputDataCount.add(new BigDecimal(2));
    }

    /**
     * 取得 總金額 及 實付金額 (總金額 = 實付金額)
     * 
     * @return
     */
    public BigDecimal getAmountAmt() {
        BigDecimal amountAmt = new BigDecimal(0);
        if (StringUtils.isNotBlank(StringUtils.trimToEmpty(amt2IssuTyp1)))
            amountAmt = amountAmt.add(new BigDecimal(StringUtils.trimToEmpty(amt2IssuTyp1)));
        if (StringUtils.isNotBlank(StringUtils.trimToEmpty(amt2IssuTyp2)))
            amountAmt = amountAmt.add(new BigDecimal(StringUtils.trimToEmpty(amt2IssuTyp2)));
        if (StringUtils.isNotBlank(StringUtils.trimToEmpty(amt2IssuTyp4)))
            amountAmt = amountAmt.add(new BigDecimal(StringUtils.trimToEmpty(amt2IssuTyp4)));        
        if (StringUtils.isNotBlank(StringUtils.trimToEmpty(amt2IssuTyp5)))
            amountAmt = amountAmt.add(new BigDecimal(StringUtils.trimToEmpty(amt2IssuTyp5)));
        if (StringUtils.isNotBlank(StringUtils.trimToEmpty(amt2IssuTypA)))
            amountAmt = amountAmt.add(new BigDecimal(StringUtils.trimToEmpty(amt2IssuTypA)));
        return amountAmt;
    }

    /**
     * 取得 行庫局 名稱 筆數 及 金額 (MIDDLE)
     * 
     * @return
     */
    public List<MonthlyRpt07MiddleRptCase> getMiddleRptData() {
        List<MonthlyRpt07MiddleRptCase> middleRptList = new ArrayList<MonthlyRpt07MiddleRptCase>();

        if (middleList != null) {
            if (StringUtils.equalsIgnoreCase(taTyp2, "BLA")) {
                // 當 出帳類別 = BLA 時, 則 行庫別 只要顯示 "支票", 名稱 顯示 空白
                BigDecimal dataCount = new BigDecimal(0);
                BigDecimal amountAmt = new BigDecimal(0);

                for (MonthlyRpt07MiddleCase middleCase : middleList) {
                    if (StringUtils.isNotBlank(StringUtils.trimToEmpty(middleCase.getDataCount())))
                        dataCount = dataCount.add(new BigDecimal(StringUtils.trimToEmpty(middleCase.getDataCount())));
                    if (StringUtils.isNotBlank(StringUtils.trimToEmpty(middleCase.getAmt2())))
                        amountAmt = amountAmt.add(new BigDecimal(StringUtils.trimToEmpty(middleCase.getAmt2())));
                }

                MonthlyRpt07MiddleRptCase middleRptCase = new MonthlyRpt07MiddleRptCase();
                middleRptCase.setBankNo("支票"); // 行庫局
                middleRptCase.setBankName(""); // 名稱
                middleRptCase.setDataCount(dataCount); // 筆數
                middleRptCase.setAmountAmt(amountAmt); // 金額
                middleRptList.add(middleRptCase);
            }
            else if (StringUtils.equalsIgnoreCase(taTyp2, "LAB")) {
                // 當 出帳類別 = LAB 時, 則 行庫別 只要顯示 "005", 名稱 顯示 "勞保紓困貸款本息抵銷繳款"
                BigDecimal dataCount = new BigDecimal(0);
                BigDecimal amountAmt = new BigDecimal(0);

                for (MonthlyRpt07MiddleCase middleCase : middleList) {
                    if (StringUtils.isNotBlank(StringUtils.trimToEmpty(middleCase.getDataCount())))
                        dataCount = dataCount.add(new BigDecimal(StringUtils.trimToEmpty(middleCase.getDataCount())));
                    if (StringUtils.isNotBlank(StringUtils.trimToEmpty(middleCase.getAmt2())))
                        amountAmt = amountAmt.add(new BigDecimal(StringUtils.trimToEmpty(middleCase.getAmt2())));
                }

                MonthlyRpt07MiddleRptCase middleRptCase = new MonthlyRpt07MiddleRptCase();
                middleRptCase.setBankNo("005"); // 行庫局
                middleRptCase.setBankName("勞保紓困貸款本息抵銷繳款"); // 名稱
                middleRptCase.setDataCount(dataCount); // 筆數
                middleRptCase.setAmountAmt(amountAmt); // 金額
                middleRptList.add(middleRptCase);
            }
            else {
                // 當 出帳類別 <> BLA 跟 <> LAB 時
                // 顯示時, 只要顯示三個行庫局的資料, 即: 005 / 700 / 999 (扣掉 005 跟 700 的, 全都算在這個裡面)
                // 005 - 台灣土地銀行 / 700 - 台灣郵政股份有限公司 / 999 - 其他
                BigDecimal dataCount005 = new BigDecimal(0);
                BigDecimal amountAmt005 = new BigDecimal(0);
                BigDecimal dataCount700 = new BigDecimal(0);
                BigDecimal amountAmt700 = new BigDecimal(0);
                BigDecimal dataCount999 = new BigDecimal(0);
                BigDecimal amountAmt999 = new BigDecimal(0);

                for (MonthlyRpt07MiddleCase middleCase : middleList) {
                    if (StringUtils.endsWithIgnoreCase(middleCase.getHbank2(), "005")) {
                        if (StringUtils.isNotBlank(StringUtils.trimToEmpty(middleCase.getDataCount())))
                            dataCount005 = dataCount005.add(new BigDecimal(StringUtils.trimToEmpty(middleCase.getDataCount())));
                        if (StringUtils.isNotBlank(StringUtils.trimToEmpty(middleCase.getAmt2())))
                            amountAmt005 = amountAmt005.add(new BigDecimal(StringUtils.trimToEmpty(middleCase.getAmt2())));
                    }
                    else if (StringUtils.endsWithIgnoreCase(middleCase.getHbank2(), "700")) {
                        if (StringUtils.isNotBlank(StringUtils.trimToEmpty(middleCase.getDataCount())))
                            dataCount700 = dataCount700.add(new BigDecimal(StringUtils.trimToEmpty(middleCase.getDataCount())));
                        if (StringUtils.isNotBlank(StringUtils.trimToEmpty(middleCase.getAmt2())))
                            amountAmt700 = amountAmt700.add(new BigDecimal(StringUtils.trimToEmpty(middleCase.getAmt2())));
                    }
                    else {
                        if (StringUtils.isNotBlank(StringUtils.trimToEmpty(middleCase.getDataCount())))
                            dataCount999 = dataCount999.add(new BigDecimal(StringUtils.trimToEmpty(middleCase.getDataCount())));
                        if (StringUtils.isNotBlank(StringUtils.trimToEmpty(middleCase.getAmt2())))
                            amountAmt999 = amountAmt999.add(new BigDecimal(StringUtils.trimToEmpty(middleCase.getAmt2())));
                    }
                }

                // 005 - 台灣土地銀行
                MonthlyRpt07MiddleRptCase middleRpt005Case = new MonthlyRpt07MiddleRptCase();
                middleRpt005Case.setBankNo("005"); // 行庫局
                middleRpt005Case.setBankName("台灣土地銀行"); // 名稱
                middleRpt005Case.setDataCount(dataCount005); // 筆數
                middleRpt005Case.setAmountAmt(amountAmt005); // 金額
                middleRptList.add(middleRpt005Case);

                // 700 - 台灣郵政股份有限公司
                MonthlyRpt07MiddleRptCase middleRpt700Case = new MonthlyRpt07MiddleRptCase();
                middleRpt700Case.setBankNo("700"); // 行庫局
                middleRpt700Case.setBankName("台灣郵政股份有限公司"); // 名稱
                middleRpt700Case.setDataCount(dataCount700); // 筆數
                middleRpt700Case.setAmountAmt(amountAmt700); // 金額
                middleRptList.add(middleRpt700Case);

                // 999 - 其他
                MonthlyRpt07MiddleRptCase middleRpt999Case = new MonthlyRpt07MiddleRptCase();
                middleRpt999Case.setBankNo("999"); // 行庫局
                middleRpt999Case.setBankName("其他"); // 名稱
                middleRpt999Case.setDataCount(dataCount999); // 筆數
                middleRpt999Case.setAmountAmt(amountAmt999); // 金額
                middleRptList.add(middleRpt999Case);
            }
        }

        return middleRptList;
    }

    /**
     * 取得 給付代號 給付名稱 筆數 及 金額 (FOOTER)
     * 
     * @return
     */
    public List<MonthlyRpt07FooterRptCase> getFooterRptData() {
        List<MonthlyRpt07FooterRptCase> footerRptList = new ArrayList<MonthlyRpt07FooterRptCase>();

        if (footerList != null) {
            for (MonthlyRpt07FooterCase footerCase : footerList) {
                MonthlyRpt07FooterRptCase footerRptCase = new MonthlyRpt07FooterRptCase();

                // 給付代號 及 給付名稱
                if (StringUtils.equalsIgnoreCase(footerCase.getPayTyp2(), "34")) {
                    footerRptCase.setPayNo("34");
                    footerRptCase.setPayName("失能年金(物價指數)");
                }
                else if (StringUtils.equalsIgnoreCase(footerCase.getPayTyp2(), "35")) {
                    footerRptCase.setPayNo("35");
                    footerRptCase.setPayName("失能年金");
                }
                else if (StringUtils.equalsIgnoreCase(footerCase.getPayTyp2(), "36")) {
                    footerRptCase.setPayNo("36");
                    footerRptCase.setPayName("國保年金併計失能(主辦為國保年金)");
                }
                else if (StringUtils.equalsIgnoreCase(footerCase.getPayTyp2(), "37")) {
                    footerRptCase.setPayNo("37");
                    footerRptCase.setPayName("職災失能補償一次金");
                }
                else if (StringUtils.equalsIgnoreCase(footerCase.getPayTyp2(), "38")) {
                    footerRptCase.setPayNo("38");
                    footerRptCase.setPayName("失能年金併計國保年金");
                }
                else if (StringUtils.equalsIgnoreCase(footerCase.getPayTyp2(), "39")) {
                    footerRptCase.setPayNo("39");
                    footerRptCase.setPayName("請領一次失能給付差額");
                }
                else if (StringUtils.equalsIgnoreCase(footerCase.getPayTyp2(), "44")) {
                    footerRptCase.setPayNo("44");
                    footerRptCase.setPayName("老年年金(物價指數)");
                }
                else if (StringUtils.equalsIgnoreCase(footerCase.getPayTyp2(), "45")) {
                    footerRptCase.setPayNo("45");
                    footerRptCase.setPayName("老年年金");
                }
                else if (StringUtils.equalsIgnoreCase(footerCase.getPayTyp2(), "48")) {
                    footerRptCase.setPayNo("48");
                    footerRptCase.setPayName("老年年金併計國保年資");
                }
                else if (StringUtils.equalsIgnoreCase(footerCase.getPayTyp2(), "49")) {
                    footerRptCase.setPayNo("49");
                    footerRptCase.setPayName("請領一次老年給付差額");
                }
                else if (StringUtils.equalsIgnoreCase(footerCase.getPayTyp2(), "54")) {
                    footerRptCase.setPayNo("54");
                    footerRptCase.setPayName("遺屬年金(物價指數)");
                }
                else if (StringUtils.equalsIgnoreCase(footerCase.getPayTyp2(), "55")) {
                    footerRptCase.setPayNo("55");
                    footerRptCase.setPayName("遺屬年金");
                }
                else if (StringUtils.equalsIgnoreCase(footerCase.getPayTyp2(), "56")) {
                    footerRptCase.setPayNo("56");
                    footerRptCase.setPayName("遺屬年金(老年年金後續)");
                }
                else if (StringUtils.equalsIgnoreCase(footerCase.getPayTyp2(), "57")) {
                    footerRptCase.setPayNo("57");
                    footerRptCase.setPayName("職災死亡補償一次金");
                }
                else if (StringUtils.equalsIgnoreCase(footerCase.getPayTyp2(), "58")) {
                    footerRptCase.setPayNo("58");
                    footerRptCase.setPayName("喪葬津貼");
                }
                else if (StringUtils.equalsIgnoreCase(footerCase.getPayTyp2(), "59")) {
                    footerRptCase.setPayNo("59");
                    footerRptCase.setPayName("遺屬年金(失能年金後續)");
                }

                // 筆數
                if (StringUtils.isNotBlank(StringUtils.trimToEmpty(footerCase.getDataCount())))
                    footerRptCase.setDataCount(new BigDecimal(StringUtils.trimToEmpty(footerCase.getDataCount())));
                // 金額
                if (StringUtils.isNotBlank(StringUtils.trimToEmpty(footerCase.getAmt2())))
                    footerRptCase.setAmountAmt(new BigDecimal(StringUtils.trimToEmpty(footerCase.getAmt2())));

                footerRptList.add(footerRptCase);
            }
        }

        return footerRptList;
    }

    public MonthlyRpt07Case() {

    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getTaTyp2() {
        return taTyp2;
    }

    public void setTaTyp2(String taTyp2) {
        this.taTyp2 = taTyp2;
    }

    public String getMfileName() {
        return mfileName;
    }

    public void setMfileName(String mfileName) {
        this.mfileName = mfileName;
    }

    public String getPayDate2() {
        return payDate2;
    }

    public void setPayDate2(String payDate2) {
        this.payDate2 = payDate2;
    }

    public String getDataCountIssuTyp1() {
        return dataCountIssuTyp1;
    }

    public void setDataCountIssuTyp1(String dataCountIssuTyp1) {
        this.dataCountIssuTyp1 = dataCountIssuTyp1;
    }

    public String getAmt2IssuTyp1() {
        return amt2IssuTyp1;
    }

    public void setAmt2IssuTyp1(String amt2IssuTyp1) {
        this.amt2IssuTyp1 = amt2IssuTyp1;
    }

    public String getDataCountIssuTyp2() {
        return dataCountIssuTyp2;
    }

    public void setDataCountIssuTyp2(String dataCountIssuTyp2) {
        this.dataCountIssuTyp2 = dataCountIssuTyp2;
    }

    public String getAmt2IssuTyp2() {
        return amt2IssuTyp2;
    }

    public void setAmt2IssuTyp2(String amt2IssuTyp2) {
        this.amt2IssuTyp2 = amt2IssuTyp2;
    }
    
    public String getDataCountIssuTyp4() {
        return dataCountIssuTyp4;
    }

    public void setDataCountIssuTyp4(String dataCountIssuTyp4) {
        this.dataCountIssuTyp4 = dataCountIssuTyp4;
    }

    public String getAmt2IssuTyp4() {
        return amt2IssuTyp4;
    }

    public void setAmt2IssuTyp4(String amt2IssuTyp4) {
        this.amt2IssuTyp4 = amt2IssuTyp4;
    }    

    public String getDataCountIssuTyp5() {
        return dataCountIssuTyp5;
    }

    public void setDataCountIssuTyp5(String dataCountIssuTyp5) {
        this.dataCountIssuTyp5 = dataCountIssuTyp5;
    }

    public String getAmt2IssuTyp5() {
        return amt2IssuTyp5;
    }

    public void setAmt2IssuTyp5(String amt2IssuTyp5) {
        this.amt2IssuTyp5 = amt2IssuTyp5;
    }

    public String getDataCountIssuTypA() {
        return dataCountIssuTypA;
    }

    public void setDataCountIssuTypA(String dataCountIssuTypA) {
        this.dataCountIssuTypA = dataCountIssuTypA;
    }

    public String getAmt2IssuTypA() {
        return amt2IssuTypA;
    }

    public void setAmt2IssuTypA(String amt2IssuTypA) {
        this.amt2IssuTypA = amt2IssuTypA;
    }

    public List<MonthlyRpt07MiddleCase> getMiddleList() {
        return middleList;
    }

    public void setMiddleList(List<MonthlyRpt07MiddleCase> middleList) {
        this.middleList = middleList;
    }

    public List<MonthlyRpt07FooterCase> getFooterList() {
        return footerList;
    }

    public void setFooterList(List<MonthlyRpt07FooterCase> footerList) {
        this.footerList = footerList;
    }

}
