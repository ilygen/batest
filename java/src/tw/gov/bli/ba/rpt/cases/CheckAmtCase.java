package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;

import tw.gov.bli.ba.domain.Bapairr;

/**
 * 勞保新制計算核定金額用CASE (使用給付年月之所得替代率參數)
 * 
 * @author jerry
 */
public class CheckAmtCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;

    // public final static int TYPE_1 = 1;// 第一式
    // public final static int TYPE_2 = 2;// 第二式
    // public final static String TYPE_1_AMT = "type1Amt";// 第一式金額
    // public final static String TYPE_2_AMT = "type2Amt";// 第二式金額
    List<BapairrCase> pairrList;

    public CheckAmtCase(List<BapairrCase> pairrList) {
        if (pairrList != null) {
            this.pairrList = pairrList;
        }
        else {
            this.pairrList = new ArrayList<BapairrCase>();
        }
    }

    /**
     * 取得年金第一式金額
     * 
     * @param avgWg 平均薪資
     * @param oldSeni 老年年資
     * @param bapairrCase 所得替代率參數CASE
     * @return
     */
    private BigDecimal getType1Amt(BigDecimal avgWg, BigDecimal oldSeni, BapairrCase bapairrCase) {
        // 判斷平均薪資屬於哪個級距
        switch (bapairrCase.checkAvgWgLevel(avgWg)) {
            // 級距4
            case Bapairr.INS_AVG_Level_4: {
                // 【平均薪資級距1*所得替代率第一式a1*實付年資】（元以下四捨五入）
                BigDecimal level1 = bapairrCase.getInsAvgIrrType1(Bapairr.INS_AVG_Level_1).multiply(oldSeni).setScale(0, BigDecimal.ROUND_HALF_UP);
                // 【平均薪資級距2*所得替代率第一式b1*實付年資】（元以下四捨五入)
                BigDecimal level2 = bapairrCase.getInsAvgIrrType1(Bapairr.INS_AVG_Level_2).multiply(oldSeni).setScale(0, BigDecimal.ROUND_HALF_UP);
                // 【平均薪資級距3*所得替代率第一式b1*實付年資】（元以下四捨五入)
                BigDecimal level3 = bapairrCase.getInsAvgIrrType1(Bapairr.INS_AVG_Level_3).multiply(oldSeni).setScale(0, BigDecimal.ROUND_HALF_UP);
                // 【（平均薪資-平均薪資級距3）*所得替代率第一式d1*實付年資】（元以下四捨五入）
                BigDecimal level4 = avgWg.subtract(bapairrCase.getInsAvg(Bapairr.INS_AVG_Level_3)).multiply(bapairrCase.getIrrType1(Bapairr.INS_AVG_Level_4)).multiply(oldSeni).setScale(0, BigDecimal.ROUND_HALF_UP);

                return level1.add(level2).add(level3).add(level4);
                // break;
            }
            // 級距3
            case Bapairr.INS_AVG_Level_3: {
                // 【平均薪資級距1*所得替代率第一式a1*實付年資】（元以下四捨五入）
                BigDecimal level1 = bapairrCase.getInsAvgIrrType1(Bapairr.INS_AVG_Level_1).multiply(oldSeni).setScale(0, BigDecimal.ROUND_HALF_UP);
                // 【平均薪資級距2*所得替代率第一式b1*實付年資】（元以下四捨五入)
                BigDecimal level2 = bapairrCase.getInsAvgIrrType1(Bapairr.INS_AVG_Level_2).multiply(oldSeni).setScale(0, BigDecimal.ROUND_HALF_UP);
                // 【（平均薪資-平均薪資級距2）*所得替代率第一式c1*實付年資】（元以下四捨五入）
                BigDecimal level3 = avgWg.subtract(bapairrCase.getInsAvg(Bapairr.INS_AVG_Level_2)).multiply(bapairrCase.getIrrType1(Bapairr.INS_AVG_Level_3)).multiply(oldSeni).setScale(0, BigDecimal.ROUND_HALF_UP);

                return level1.add(level2).add(level3);
                // break;
            }
            // 級距2
            case Bapairr.INS_AVG_Level_2: {
                // 【平均薪資級距1*所得替代率第一式a1*實付年資】（元以下四捨五入）
                BigDecimal level1 = bapairrCase.getInsAvgIrrType1(Bapairr.INS_AVG_Level_1).multiply(oldSeni).setScale(0, BigDecimal.ROUND_HALF_UP);
                // 【（平均薪資-平均薪資級距1）*所得替代率第一式b1*實付年資】（元以下四捨五入）
                BigDecimal level2 = avgWg.subtract(bapairrCase.getInsAvg(Bapairr.INS_AVG_Level_1)).multiply(bapairrCase.getIrrType1(Bapairr.INS_AVG_Level_2)).multiply(oldSeni).setScale(0, BigDecimal.ROUND_HALF_UP);

                return level1.add(level2);
                // break;
            }
            // 級距1
            case Bapairr.INS_AVG_Level_1: {
                // 【平均薪資*所得替代率第一式a1*實付年資】（元以下四捨五入）
                BigDecimal level1 = avgWg.multiply(bapairrCase.getIrrType1(Bapairr.INS_AVG_Level_1)).multiply(oldSeni).setScale(0, BigDecimal.ROUND_HALF_UP);
                return level1;
                // break;
            }
        }

        return BigDecimal.ZERO;
    }

    /**
     * 取得年金第二式金額
     * 
     * @param avgWg 平均薪資
     * @param oldSeni 老年年資
     * @param bapairrCase 所得替代率參數CASE
     * @return
     */
    private BigDecimal getType2Amt(BigDecimal avgWg, BigDecimal oldSeni, BapairrCase bapairrCase) {
        // 判斷平均薪資屬於哪個級距
        switch (bapairrCase.checkAvgWgLevel(avgWg)) {
            // 級距4
            case Bapairr.INS_AVG_Level_4: {
                // 【平均薪資級距1*所得替代率第一式a1*實付年資】（元以下四捨五入）
                BigDecimal level1 = bapairrCase.getInsAvgIrrType2(Bapairr.INS_AVG_Level_1).multiply(oldSeni).setScale(0, BigDecimal.ROUND_HALF_UP);
                // 【平均薪資級距2*所得替代率第一式b1*實付年資】（元以下四捨五入)
                BigDecimal level2 = bapairrCase.getInsAvgIrrType2(Bapairr.INS_AVG_Level_2).multiply(oldSeni).setScale(0, BigDecimal.ROUND_HALF_UP);
                // 【平均薪資級距3*所得替代率第一式b1*實付年資】（元以下四捨五入)
                BigDecimal level3 = bapairrCase.getInsAvgIrrType2(Bapairr.INS_AVG_Level_3).multiply(oldSeni).setScale(0, BigDecimal.ROUND_HALF_UP);
                // 【（平均薪資-平均薪資級距3）*所得替代率第一式d1*實付年資】（元以下四捨五入）
                BigDecimal level4 = avgWg.subtract(bapairrCase.getInsAvg(Bapairr.INS_AVG_Level_3)).multiply(bapairrCase.getIrrType2(Bapairr.INS_AVG_Level_4)).multiply(oldSeni).setScale(0, BigDecimal.ROUND_HALF_UP);

                return level1.add(level2).add(level3).add(level4);
                // break;
            }
            // 級距3
            case Bapairr.INS_AVG_Level_3: {
                // 【平均薪資級距1*所得替代率第一式a1*實付年資】（元以下四捨五入）
                BigDecimal level1 = bapairrCase.getInsAvgIrrType2(Bapairr.INS_AVG_Level_1).multiply(oldSeni).setScale(0, BigDecimal.ROUND_HALF_UP);
                // 【平均薪資級距2*所得替代率第一式b1*實付年資】（元以下四捨五入)
                BigDecimal level2 = bapairrCase.getInsAvgIrrType2(Bapairr.INS_AVG_Level_2).multiply(oldSeni).setScale(0, BigDecimal.ROUND_HALF_UP);
                // 【（平均薪資-平均薪資級距2）*所得替代率第一式c1*實付年資】（元以下四捨五入）
                BigDecimal level3 = avgWg.subtract(bapairrCase.getInsAvg(Bapairr.INS_AVG_Level_2)).multiply(bapairrCase.getIrrType2(Bapairr.INS_AVG_Level_3)).multiply(oldSeni).setScale(0, BigDecimal.ROUND_HALF_UP);

                return level1.add(level2).add(level3);
                // break;
            }
            // 級距2
            case Bapairr.INS_AVG_Level_2: {
                // 【平均薪資級距1*所得替代率第一式a1*實付年資】（元以下四捨五入）
                BigDecimal level1 = bapairrCase.getInsAvgIrrType2(Bapairr.INS_AVG_Level_1).multiply(oldSeni).setScale(0, BigDecimal.ROUND_HALF_UP);
                // 【（平均薪資-平均薪資級距1）*所得替代率第一式b1*實付年資】（元以下四捨五入）
                BigDecimal level2 = avgWg.subtract(bapairrCase.getInsAvg(Bapairr.INS_AVG_Level_1)).multiply(bapairrCase.getIrrType2(Bapairr.INS_AVG_Level_2)).multiply(oldSeni).setScale(0, BigDecimal.ROUND_HALF_UP);

                return level1.add(level2);
                // break;
            }
            // 級距1
            case Bapairr.INS_AVG_Level_1: {
                // 【平均薪資*所得替代率第一式a1*實付年資】（元以下四捨五入）
                BigDecimal level1 = avgWg.multiply(bapairrCase.getIrrType2(Bapairr.INS_AVG_Level_1)).multiply(oldSeni).setScale(0, BigDecimal.ROUND_HALF_UP);
                return level1;
                // break;
            }
        }

        return BigDecimal.ZERO;
    }

    /**
     * 取得年金第一式金額
     * 
     * @param payYm
     * @param avgWg
     * @param oldSeni
     * @return
     */
    public BigDecimal getType1Amt(String payYm, BigDecimal avgWg, BigDecimal oldSeni) {
        int ym = NumberUtils.toInt(payYm);
        for (BapairrCase bapairrCase : pairrList) {
            if (ym >= NumberUtils.toInt(bapairrCase.getEffYm())) {
                return getType1Amt(avgWg, oldSeni, bapairrCase);
            }
        }
        return BigDecimal.ZERO;
    }

    /**
     * 取得年金第二式金額
     * 
     * @param payYm
     * @param avgWg
     * @param oldSeni
     * @return
     */
    public BigDecimal getType2Amt(String payYm, BigDecimal avgWg, BigDecimal oldSeni) {
        int ym = NumberUtils.toInt(payYm);
        for (BapairrCase bapairrCase : pairrList) {
            if (ym >= NumberUtils.toInt(bapairrCase.getEffYm())) {
                return getType2Amt(avgWg, oldSeni, bapairrCase);
            }
        }
        return BigDecimal.ZERO;
    }

    /**
     * 取得最小實行年月
     * 
     * @return
     */
    public String getMinEffYm() {
        return pairrList.get(pairrList.size() - 1).getEffYm();
    }
}
