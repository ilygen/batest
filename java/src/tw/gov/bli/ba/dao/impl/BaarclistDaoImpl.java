package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapExecutor;

import tw.gov.bli.ba.dao.BaarclistDao;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.common.annotation.DaoTable;
import tw.gov.bli.ba.domain.Baarclist;

/**
 * DAOIMPL for 案件歸檔紀錄檔 (<code>BAARCLIST</code>)
 * 
 * @author Jerry
 */
@DaoTable("BAARCLIST")
public class BaarclistDaoImpl extends SqlMapClientDaoSupport implements BaarclistDao {
    /**
     * 更新 案件歸檔紀錄檔(<code>BAARCLIST</code>) for 給付決行
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForDecision(Baappbase baappbase) {
        getSqlMapClientTemplate().update("BAARCLIST.updateDataForDecision", baappbase);
    }

    /**
     * 查詢 案件歸檔紀錄檔(<code>BAARCLIST</code>) 取得當日已歸檔的最大頁次及序號 for 列印歸檔清單
     * 
     * @param payCode 給付別
     * @param arcDate 歸檔日期
     */
    public Baarclist selectMaxBaarclistNumAndArcPg(String arcDate, String payCode) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("arcDate", arcDate);
        map.put("payCode", payCode);
        return (Baarclist) getSqlMapClientTemplate().queryForObject("BAARCLIST.selectMaxBaarclistNumAndArcPg", map);
    }
    
    /**
     * 查詢 案件歸檔紀錄檔(<code>BAARCLIST</code>) 取得當日已歸檔的最大頁次及序號 for 列印歸檔清單 (payCode = S and CaseTyp = 3)
     * 
     * @param payCode 給付別
     * @param arcDate 歸檔日期
     */
    public Baarclist selectMaxBaarclistNumAndArcPgForS(String arcDate, String payCode) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("arcDate", arcDate);
        map.put("payCode", payCode);
        return (Baarclist) getSqlMapClientTemplate().queryForObject("BAARCLIST.selectMaxBaarclistNumAndArcPgForS", map);
    }    

    /**
     * 新增 案件歸檔紀錄檔(<code>BAARCLIST</code>) 資料
     * 
     * @param BAARCLIST 案件歸檔紀錄檔
     * @return
     */
    public void insertData(final List<Baarclist> baarclistList) {
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();

                for (Baarclist baarclist : baarclistList) {

                    executor.insert("BAARCLIST.insertData", baarclist);
                }

                int rowsaffected = executor.executeBatch();

                return new Integer(rowsaffected);
            }
        });
    }

    /**
     * 查詢 案件歸檔紀錄檔(<code>BAARCLIST</code>) for 列印歸檔清單補印
     * 
     * @param payCode 給付別
     * @param arcDate 歸檔日期
     * @param arcPgBegin 歸檔頁次起
     * @param arcPgEnd 歸檔頁次迄
     * @return
     */
    public List<Baarclist> selectDataForDecisionRpt02(String payCode, String arcDate, BigDecimal arcPgBegin, BigDecimal arcPgEnd, String exeMan) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("payCode", payCode);
        map.put("arcDate", arcDate);
        if (arcPgBegin != null)
            map.put("arcPgBegin", arcPgBegin);
        if (arcPgEnd != null)
            map.put("arcPgEnd", arcPgEnd);
        if (exeMan != null)
            map.put("exeMan", exeMan);
        return (List<Baarclist>) getSqlMapClientTemplate().queryForList("BAARCLIST.selectDataForDecisionRpt02", map);
    }
    
    /**
     * 查詢 案件歸檔紀錄檔(<code>BAARCLIST</code>) for 歸檔清單點交清冊
     * 
     * @param payCode 給付別
     * @param arcDate 歸檔日期
     * @return
     */
    public List<Baarclist> selectDataForDecisionRpt03(String payCode, String arcDate){
    	  HashMap<String, Object> map = new HashMap<String, Object>();
          map.put("arcDate", arcDate);
          if (payCode != null)
              map.put("payCode", payCode);

          return (List<Baarclist>) getSqlMapClientTemplate().queryForList("BAARCLIST.selectDataForDecisionRpt03", map);
      }

    // Payment Query [
    /**
     * 查詢 案件歸檔紀錄檔(<code>BAARCLIST</code>) 核定年月起迄 for 給付查詢-歸檔記錄
     * 
     * @param apNo 受理編號
     * @return
     */
    public Baarclist selectPaymentQueryIssuYm(String apNo) {
        return (Baarclist) getSqlMapClientTemplate().queryForObject("BAARCLIST.selectPaymentQueryIssuYm", apNo);
    }
    
    /**
     * 查詢 案件歸檔紀錄檔(<code>BAARCLIST</code>) 歸檔記錄明細資料 for 給付查詢-歸檔記錄
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<Baarclist> selectPaymentQueryData(String apNo){
        return (List<Baarclist>)getSqlMapClientTemplate().queryForList("BAARCLIST.selectPaymentQueryData",apNo);
    }
    // Payment Query ]
}
