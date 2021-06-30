package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.PbbmsaDao;
import tw.gov.bli.ba.dao.LocalPfpcckyDao;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Pbbmsa;
import tw.gov.bli.ba.domain.Pfpccky;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for (<code>PFPCCKY</code>) <br>
 * 
 * @author Noctis
 */
@DaoTable("PFPCCKY")
public class LocalPfpcckyDaoImpl extends SqlMapClientDaoSupport implements LocalPfpcckyDao {

    /**
     * 依傳入條件取得 退現資料檔 (<code>PFPCCKY</code>)  for 老年年金應收收回處理
     * 
     * @param apNo 受理編號
     * @param payCode 給付別
     * @return
     */
    public List<Pfpccky> selectCashReceiveDataListBy(String apNo,String payCode) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(apNo))
            map.put("payCode", payCode);

        return getSqlMapClientTemplate().queryForList("PFPCCKY.selectCashReceiveDataListBy", map);
    }
    
    /**
     * 依傳入條件取得 退現資料檔(<code>PFPCCKY</code>) 資料 for 已退匯或退回現金尚未沖轉收回案件清單 - 退現
     * 
     * @param apNo  受理編號
     * @return
     */
    public List<Pfpccky> selectOtherRpt02CashSourceDataListBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return getSqlMapClientTemplate().queryForList("PFPCCKY.selectOtherRpt02CashSourceDataListBy", map);
    }
    
    /**
     * 依傳入條件取得 退現資料檔 (<code>PFPCCKY</code>)  for 整批收回核定清單
     * 
     * @param payCode 給付別
     * @param chkDate 核收日期
     * @return
     */
    public List<Pfpccky> selectBatchPaymentReceiveDataListBy(String payCode, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(chkDate))
            map.put("chkDate", chkDate);
        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);

        return getSqlMapClientTemplate().queryForList("PFPCCKY.selectBatchPaymentReceiveDataDetailListBy", map);
    }
    
    /**
     * 依傳入條件取得 退現資料檔 (<code>PFPCCKY</code>)  for 整批收回核定清單
     * 
     * @param payCode 給付別
     * @param chkDate 核收日期
     * @return
     */
    public List<Pfpccky> selectBatchPaymentReceiveDataDetailListBy(String payCode, String chkDate, String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(chkDate))
            map.put("chkDate", chkDate);
        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return getSqlMapClientTemplate().queryForList("PFPCCKY.selectBatchPaymentReceiveDataDetailListBy", map);
    }    
    
    /**
     * 依傳入條件取得 退現資料檔 (<code>PFPCCKY</code>)  for 整批收回核定清單
     * 
     * @param payCode 給付別
     * @param chkDate 核收日期
     * @return
     */
    public List<Pfpccky> selectBatchPaymentReceiveDataMasterListBy(String payCode, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(chkDate))
            map.put("chkDate", chkDate);
        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);

        return getSqlMapClientTemplate().queryForList("PFPCCKY.selectBatchPaymentReceiveDataMasterListBy", map);
    }    
  
}
