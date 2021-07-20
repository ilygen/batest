package tw.gov.bli.ba.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.bj.cases.CompPaymentCase;
import tw.gov.bli.ba.dao.BaapplogDao;
import tw.gov.bli.ba.domain.Baapplog;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * DAO for 給付資料更正記錄檔 (<code>BAAPPLOG</code>)
 * 
 * @author Rickychi
 */
@DaoTable("BAAPPLOG")
public class BaapplogDaoImpl extends SqlMapClientDaoSupport implements BaapplogDao {

    /**
     * 依傳入的條件取得 給付資料更正記錄檔(<code>BAAPPLOG</code>) 的資料
     * 
     * @param payCode 給付別
     * @prarm updateDateBegin 處理時間 (起)
     * @param updateDateEnd 處理時間 (迄)
     * @param apNo 受理編號
     * @param benIdnNo 身分證號
     * @param updUser 更正人員
     * @return
     */
    @DaoFieldList("PAYCODE,UPDTIME_BEG,UPDTIME_END,APNO,BENIDNNO,UPDUSER")
    public List<Baapplog> selectLogListBy(String payCode, String updateDateBegin, String updateDateEnd, String apNo, String benIdnNo, String updUser) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("payCode", payCode);
        map.put("updateDateBegin", updateDateBegin);
        map.put("updateDateEnd", updateDateEnd);

        if (StringUtils.isNotBlank(StringUtils.trimToEmpty(apNo)))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(StringUtils.trimToEmpty(benIdnNo)))
            map.put("benIdnNo", benIdnNo);
        if (StringUtils.isNotBlank(StringUtils.trimToEmpty(updUser)))
            map.put("updUser", updUser);

        return getSqlMapClientTemplate().queryForList("BAAPPLOG.selectLogListBy", map);
    }

    /**
     * 新增 給付資料更正記錄檔(<code>BAAPPLOG</code>) 資料
     * 
     * @param baapplog 給付資料更正記錄檔
     */
    public void insertData(Baapplog baapplog) {
        getSqlMapClientTemplate().insert("BAAPPLOG.insertData", baapplog);
    }

    /**
     * 新增 給付資料更正記錄檔(<code>BAAPPLOG</code>) 資料
     * 
     * @param baapplogList
     */
    public void insertData(final List<Baapplog> baapplogList) {
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();
                for (int i = 0; i < baapplogList.size(); i++) {
                    Baapplog data = (Baapplog) baapplogList.get(i);
                    executor.insert("BAAPPLOG.insertData", data);
                }
                int rowsaffected = executor.executeBatch();

                return new Integer(rowsaffected);
            }
        });
    }
    
    /**
     * 新增 老年、失能年金補正核付作業更正記錄檔(<code>BAAPPLOG</code>) 資料
     * 
     * @param oldAgePaymentCase
     */
    public void insertLogForCompPayment(CompPaymentCase compPaymentCase) {
    	Baapplog baapplog = new Baapplog();
    	baapplog.setBaappbaseId(compPaymentCase.getBaappbaseId());
    	baapplog.setStatus(compPaymentCase.getStatus());
    	baapplog.setUpdTime(compPaymentCase.getProcTime());
    	baapplog.setUpdUser(compPaymentCase.getEmpNo());
    	baapplog.setUpCol(compPaymentCase.getUpCol());
    	baapplog.setBvalue(compPaymentCase.getbValue());
    	baapplog.setAvalue(compPaymentCase.getaValue());
    	baapplog.setApNo(compPaymentCase.getApNo());
    	baapplog.setSeqNo(compPaymentCase.getSeqNo());
    	baapplog.setPayYm(compPaymentCase.getPayYm());
    	baapplog.setIssuYm(compPaymentCase.getIssuYm());
    	
        getSqlMapClientTemplate().insert("BAAPPLOG.insertLogForCompPayment", baapplog);
    }
}
