package tw.gov.bli.ba.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BarxfDao;
import tw.gov.bli.ba.domain.Barxf;
import com.ibatis.sqlmap.client.SqlMapExecutor;

public class BarxfDaoImpl extends SqlMapClientDaoSupport implements BarxfDao {
    /**
     * 依傳入條件取得 另案扣減工作檔(<code>BARXF</code>) 資料
     * 
     * @param APNO 受理編號-撥付
     * @return RXFAPNO 受理編號-應收
     */
    public List<Barxf> selectRxfApNoByApNo(String apNo) {
        return (List<Barxf>) getSqlMapClientTemplate().queryForList("BARXF.selectRxfApNoByApNo", apNo);
    }

    /**
     * 依傳入條件取得 另案扣減工作檔(<code>BARXF</code>) 資料
     * 
     * @param RXFAPNO 受理編號-應收
     * @param APNO 受理編號-撥付
     * @param SEQNO 序號-撥付
     * @return
     */
    public Barxf selectDataBy(String rxfApNo, String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("rxfApNo", rxfApNo);
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return (Barxf) getSqlMapClientTemplate().queryForObject("BARXF.selectDataBy", map);
    }

    /**
     * 新增 給付主檔(<code>BARXF</code>) 資料
     * 
     * @param Barxf 另案扣減工作檔
     * @return
     */
    public void insertData(final List<Barxf> barxfList) {
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();
                for (Barxf barxf : barxfList) {
                    executor.insert("BARXF.insertData", barxf);
                }
                int rowsaffected = executor.executeBatch();
                return new Integer(rowsaffected);
            }
        });
    }

    /**
     * 依傳入條件取得 另案扣減工作檔(<code>BARXF</code>) 另案扣減資料 - 一次給付 for 勞保老年年金給付受理編審清單
     * 
     * @param benIdnNo 受款人身分證號 - 撥付
     * @param benBrDate 受款人出生日期 - 撥付
     * @return
     */
    public List<Barxf> selectOldAgeReviewRpt01DeductOnceListBy(String benIdnNo, String benBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(benIdnNo))
            map.put("benIdnNo", benIdnNo);
        if (StringUtils.isNotBlank(benBrDate))
            map.put("benBrDate", benBrDate);

        return getSqlMapClientTemplate().queryForList("BARXF.selectOldAgeReviewRpt01DeductOnceListBy", map);
    }

    /**
     * 依傳入條件取得 另案扣減工作檔(<code>BARXF</code>) 另案扣減資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param APNO 受理編號-撥付
     * @param ISSUYM 核定年月
     * @param BENIDNNO 受益人身分證號
     * @return
     */
    public List<Barxf> selectSurvivorReviewRpt01DeductListBy(String benIdnNo, String benBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("benIdnNo", benIdnNo);
        map.put("benBrDate", benBrDate);
        return (List<Barxf>) getSqlMapClientTemplate().queryForList("BARXF.selectSurvivorReviewRpt01DeductListBy", map);
    }

    /**
     * 依傳入條件取得 另案扣減工作檔(<code>BARXF</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param APNO 受理編號-撥付
     * @param ISSUYM 核定年月
     * @param BENIDNNO 受益人身分證號
     * @return
     */
    public List<Barxf> selectDisableReviewRpt01DeductListBy(String benIdnNo, String benBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("benIdnNo", benIdnNo);
        map.put("benBrDate", benBrDate);
        return (List<Barxf>) getSqlMapClientTemplate().queryForList("BARXF.selectDisableReviewRpt01DeductListBy", map);
    }
}
