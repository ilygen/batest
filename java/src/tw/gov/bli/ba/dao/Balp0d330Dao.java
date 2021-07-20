package tw.gov.bli.ba.dao;

import java.util.List;
import tw.gov.bli.ba.domain.Balp0d330;

/**
 * DAO for 月核定差異統計檔 (<code>BALP0D330</code>)
 * 
 * @author Goston
 */
public interface Balp0d330Dao {

    /**
     * 依傳入條件取得 月核定差異統計檔 (<code>BALP0D330</code>) for 月核定差異統計表
     * 
     * @param payCode 給付別
     * @param issuYmBegin 核定年月 (起)
     * @param issuYmEnd 核定年月 (迄)
     * @return
     */
    public List<Balp0d330> getMonthlyRpt03ListBy(String payCode, String issuYmBegin, String issuYmEnd);
}
