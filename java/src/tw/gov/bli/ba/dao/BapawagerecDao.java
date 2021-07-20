package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 勞保最高最低投保薪資紀錄檔 (<code>BAPAWAGEREC</code>)
 * 
 * @author KIYOMI
 */

public interface BapawagerecDao {

    /**
     * 依傳入條件取得 投保薪資分級表第一級 (<code>BAPAWAGEREC</code>)
     * 
     * @param maxPayYm 受理編號
     * @return
     */
    public BigDecimal getMinWage(String maxPayYm);
}
