package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.IntRange;
import org.apache.commons.lang.math.NumberUtils;

import tw.gov.bli.ba.domain.Babasicamt;

public class SurvivorBasicAmtCase implements Serializable {
    private static final long serialVersionUID = 4482110121235865214L;
    public static final BigDecimal DEFAULT_BASE_AMT = new BigDecimal(3000);// 預設基本金額
    List<BasicAmtCase> basicAmtList = new ArrayList<BasicAmtCase>();

    public SurvivorBasicAmtCase(List<Babasicamt> amtList) {
        if (amtList != null && amtList.size() > 0) {
            for (Babasicamt babasicamt : amtList) {
                BasicAmtCase payYmRange = new BasicAmtCase();
                IntRange ymRange = new IntRange(NumberUtils.toInt(babasicamt.getPayYmB()), NumberUtils.toInt(babasicamt.getPayYmE()));
                payYmRange.setYmRange(ymRange);
                payYmRange.setBaseAmt(babasicamt.getBasicAmt());
                basicAmtList.add(payYmRange);
            }
        }
    }

    /**
     * 取的該年月的基本給付金額
     * 
     * @param payYm
     * @return
     */
    public BigDecimal getBasicAmt(String payYm) {
        int ym = NumberUtils.toInt(payYm);
        for (BasicAmtCase payYmRange : basicAmtList) {
            if (payYmRange.getYmRange().containsInteger(ym)) {
                return payYmRange.getBaseAmt();
            }
        }
        return DEFAULT_BASE_AMT;
    }
}
