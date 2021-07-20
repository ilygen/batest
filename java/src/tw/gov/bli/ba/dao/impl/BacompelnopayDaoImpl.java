package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BacompelnopayDao;
import tw.gov.bli.ba.domain.Bacompelnopay;
import tw.gov.bli.ba.domain.Bastudterm;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAOImpl for 遺屬強迫不合格記錄檔 (<code>BACOMPELNOPAY</code>)
 * 
 * @author Rickychi
 */
public class BacompelnopayDaoImpl extends SqlMapClientDaoSupport implements BacompelnopayDao {

    /**
     * 依傳入條件取得 遺屬強迫不合格記錄檔 (<code>BACOMPELNOPAY</code>)
     */
    public List<Bacompelnopay> selectDataBy(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return getSqlMapClientTemplate().queryForList("BACOMPELNOPAY.selectDataBy", map);
    }

    /**
     * 新增 遺屬強迫不合格記錄檔(<code>BACOMPELNOPAY</code>)
     */
    public void insertData(Bacompelnopay bacompelnopay) {
        getSqlMapClientTemplate().insert("BACOMPELNOPAY.insertData", bacompelnopay);
    }

    /**
     * 修改 遺屬強迫不合格記錄檔(<code>BACOMPELNOPAY</code>) 資料
     */
    public void updateData(Bacompelnopay bacompelnopay) {
        getSqlMapClientTemplate().update("BACOMPELNOPAY.updateData", bacompelnopay);
    }

    /**
     * 刪除 遺屬強迫不合格記錄檔(<code>BACOMPELNOPAY</code>) 資料
     */
    public void deleteData(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        getSqlMapClientTemplate().delete("BACOMPELNOPAY.deleteData", map);
    }
    
    /**
     * 依傳入條件取得 遺屬ＸＸ期間檔 (<code>BACOMPELNOPAY</code>) 強迫不合格資料 for 遺屬年金給付查詢
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return <code>Bacompelnopay</code> 物件
     */
    @DaoFieldList("APNO,SEQNO")
    public Bacompelnopay selectCompelNopayDataForSurvivorPaymentQuery(String apNo, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return (Bacompelnopay) getSqlMapClientTemplate().queryForObject("BACOMPELNOPAY.selectCompelNopayDataForSurvivorPaymentQuery", map);
    }    
    
    /**
     * 依傳入條件取得 遺屬ＸＸ期間檔 (<code>BACOMPELNOPAY</code>) 強迫不合格資料明細 for 遺屬年金給付查詢
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Bacompelnopay</code> 物件的List
     */
    @DaoFieldList("APNO,SEQNO")
    public List<Bacompelnopay> selectCompelNopayDetailDataForSurvivorPaymentQuery(String apNo, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return getSqlMapClientTemplate().queryForList("BACOMPELNOPAY.selectCompelNopayDetailDataForSurvivorPaymentQuery", map);
    }    
}
