package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import tw.gov.bli.ba.domain.Pfpfd;

/**
 * DAO for 戶政全戶檔 (<code>PFPFD</code>)
 * 
 * @author Kiyomi
 */
public interface PfpfdDao {

    /**
     * 新增(<code>PFPFD</code>) 資料 for 整批收回核定清單
     * 
     * @param pfpfd
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForBatchPaymentReceiveData(Pfpfd pfpfd);
}
