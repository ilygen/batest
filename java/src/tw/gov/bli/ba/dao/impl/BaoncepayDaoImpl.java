package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BaoncepayDao;
import tw.gov.bli.ba.domain.Baoncepay;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * Dao for 一次請領給付資料檔 (<code>BAONCEPAY</code>)
 * 
 * @author Rickychi
 */
@DaoTable("BAONCEPAY")
public class BaoncepayDaoImpl extends SqlMapClientDaoSupport implements BaoncepayDao {
    /**
     * 依傳入條件取得 一次請領給付資料檔(<code>BADAPR</code>) 一次給付資料
     * 
     * @param apNo 受理編號
     * @return 內含 <code>StopPaymentProcessCase</code> 物件的 List
     */
    @DaoFieldList("APNO")
    public List<Baoncepay> selectOncePayDataBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);

        return getSqlMapClientTemplate().queryForList("BADAPR.selectOncePayDataBy", map);
    }

    /**
     * 依傳入條件取得 一次請領給付資料檔(<code>BADAPR</code>) 一次給付資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @return <code>Baoncepay</code> 物件
     */
    @DaoFieldList("APNO")
    public Baoncepay getOldAgeReviewRpt01DieOncePayDataBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (Baoncepay) getSqlMapClientTemplate().queryForObject("BAONCEPAY.getOldAgeReviewRpt01DieOncePayDataBy", map);
    }
}
