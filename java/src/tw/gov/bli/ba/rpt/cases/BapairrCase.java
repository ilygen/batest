package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Bapairr;

/**
 * 給付年月之所得替代率參數CASE
 * 
 * @author jerry
 */
public class BapairrCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;

    private int insAvgLevel;
    private String payCode;// 給付別
    private String effYm;// 施行年月
    private Map<Integer, BigDecimal> insAvgMap;// 平均薪資級距
    private Map<Integer, BigDecimal> irrType1Map;// 所得替代率第一式
    private Map<Integer, BigDecimal> irrType2Map;// 所得替代率第二式
    private Map<Integer, BigDecimal> insAvgIrrType1Map;// 所得替代率第一式 * 平均薪資級距 (先算好避免重複計算 加快執行速度)
    private Map<Integer, BigDecimal> insAvgIrrType2Map;// 所得替代率第二式 * 平均薪資級距 (先算好避免重複計算 加快執行速度)

    public BapairrCase(Bapairr bapairr) {
        insAvgMap = new HashMap<Integer, BigDecimal>();
        irrType1Map = new HashMap<Integer, BigDecimal>();
        irrType2Map = new HashMap<Integer, BigDecimal>();
        insAvgIrrType1Map = new HashMap<Integer, BigDecimal>();
        insAvgIrrType2Map = new HashMap<Integer, BigDecimal>();
        insAvgLevel = bapairr.getInsAvgLevel();
        payCode = bapairr.getPayCode();
        effYm = bapairr.getEffYm();

        switch (insAvgLevel) {
            case Bapairr.INS_AVG_Level_4: {
                insAvgMap.put(Bapairr.INS_AVG_Level_4, bapairr.getInsAvg4());
                irrType1Map.put(Bapairr.INS_AVG_Level_4, bapairr.getIrrTypeD1().divide(ConstantKey.BIGDECIMAL_CONSTANT_100));
                irrType2Map.put(Bapairr.INS_AVG_Level_4, bapairr.getIrrTypeD2().divide(ConstantKey.BIGDECIMAL_CONSTANT_100));
                insAvgIrrType1Map.put(Bapairr.INS_AVG_Level_4, bapairr.getInsAvg4().subtract(bapairr.getInsAvg3()).multiply(bapairr.getIrrTypeD1().divide(ConstantKey.BIGDECIMAL_CONSTANT_100)));
                insAvgIrrType2Map.put(Bapairr.INS_AVG_Level_4, bapairr.getInsAvg4().subtract(bapairr.getInsAvg3()).multiply(bapairr.getIrrTypeD2().divide(ConstantKey.BIGDECIMAL_CONSTANT_100)));
            }
            case Bapairr.INS_AVG_Level_3: {
                insAvgMap.put(Bapairr.INS_AVG_Level_3, bapairr.getInsAvg3());
                irrType1Map.put(Bapairr.INS_AVG_Level_3, bapairr.getIrrTypeC1().divide(ConstantKey.BIGDECIMAL_CONSTANT_100));
                irrType2Map.put(Bapairr.INS_AVG_Level_3, bapairr.getIrrTypeC2().divide(ConstantKey.BIGDECIMAL_CONSTANT_100));
                insAvgIrrType1Map.put(Bapairr.INS_AVG_Level_3, bapairr.getInsAvg3().subtract(bapairr.getInsAvg2()).multiply(bapairr.getIrrTypeC1().divide(ConstantKey.BIGDECIMAL_CONSTANT_100)));
                insAvgIrrType2Map.put(Bapairr.INS_AVG_Level_3, bapairr.getInsAvg3().subtract(bapairr.getInsAvg2()).multiply(bapairr.getIrrTypeC2().divide(ConstantKey.BIGDECIMAL_CONSTANT_100)));
            }
            case Bapairr.INS_AVG_Level_2: {
                insAvgMap.put(Bapairr.INS_AVG_Level_2, bapairr.getInsAvg2());
                irrType1Map.put(Bapairr.INS_AVG_Level_2, bapairr.getIrrTypeB1().divide(ConstantKey.BIGDECIMAL_CONSTANT_100));
                irrType2Map.put(Bapairr.INS_AVG_Level_2, bapairr.getIrrTypeB2().divide(ConstantKey.BIGDECIMAL_CONSTANT_100));
                insAvgIrrType1Map.put(Bapairr.INS_AVG_Level_2, bapairr.getInsAvg2().subtract(bapairr.getInsAvg1()).multiply(bapairr.getIrrTypeB1().divide(ConstantKey.BIGDECIMAL_CONSTANT_100)));
                insAvgIrrType2Map.put(Bapairr.INS_AVG_Level_2, bapairr.getInsAvg2().subtract(bapairr.getInsAvg1()).multiply(bapairr.getIrrTypeB2().divide(ConstantKey.BIGDECIMAL_CONSTANT_100)));
            }
            case Bapairr.INS_AVG_Level_1: {
                insAvgMap.put(Bapairr.INS_AVG_Level_1, bapairr.getInsAvg1());
                irrType1Map.put(Bapairr.INS_AVG_Level_1, bapairr.getIrrTypeA1().divide(ConstantKey.BIGDECIMAL_CONSTANT_100));
                irrType2Map.put(Bapairr.INS_AVG_Level_1, bapairr.getIrrTypeA2().divide(ConstantKey.BIGDECIMAL_CONSTANT_100));
                insAvgIrrType1Map.put(Bapairr.INS_AVG_Level_1, bapairr.getInsAvg1().multiply(bapairr.getIrrTypeA1().divide(ConstantKey.BIGDECIMAL_CONSTANT_100)));
                insAvgIrrType2Map.put(Bapairr.INS_AVG_Level_1, bapairr.getInsAvg1().multiply(bapairr.getIrrTypeA2().divide(ConstantKey.BIGDECIMAL_CONSTANT_100)));
            }
        }
    }

    /**
     * 判斷 平均薪資 在哪個級距
     * 
     * @param avgWg
     * @return
     */
    public int checkAvgWgLevel(BigDecimal avgWg) {
        for (int i = Bapairr.INS_AVG_Level_1; i <= insAvgLevel; i++) {
            if (avgWg.intValue() <= insAvgMap.get(i).intValue()) {
                return i;
            }
        }
        return insAvgLevel;
    }

    public int getInsAvgLevel() {
        return insAvgLevel;
    }

    public BigDecimal getInsAvg(int key) {
        return insAvgMap.get(key);
    }

    public BigDecimal getIrrType1(int key) {
        return irrType1Map.get(key);
    }

    public BigDecimal getIrrType2(int key) {
        return irrType2Map.get(key);
    }

    public String getPayCode() {
        return payCode;
    }

    public String getEffYm() {
        return effYm;
    }

    public BigDecimal getInsAvgIrrType1(int key) {
        return insAvgIrrType1Map.get(key);
    }

    public BigDecimal getInsAvgIrrType2(int key) {
        return insAvgIrrType2Map.get(key);
    }

}
