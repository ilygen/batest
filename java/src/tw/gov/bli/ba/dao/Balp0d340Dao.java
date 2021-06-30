package tw.gov.bli.ba.dao;

import java.util.List;
import tw.gov.bli.ba.domain.Balp0d340;

public interface Balp0d340Dao {
    /**
     * 依傳入條件取得 月核定撥付檔 (<code>BALP0D340</code>) for 月核定撥付總表
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Balp0d340> selectMonthlyRpt04DataBy(String payCode,String issuYm);
}
