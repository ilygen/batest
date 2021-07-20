package tw.gov.bli.ba.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import tw.gov.bli.ba.dao.BarptlogDao;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt08Case;

public class BarptlogDaoImpl extends SqlMapClientDaoSupport implements BarptlogDao {
    private static Log log = LogFactory.getLog(BarptlogDaoImpl.class);

    public void insertData(final List<MonthlyRpt08Case> list) {
        /*
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                int rowsaffected = 0;

                HashMap<String, String> map = new HashMap<String, String>();

                for (int i = 0; i < list.size(); i++) {
                    if (i == 1)
                        executor.startBatch();

                    MonthlyRpt08Case caseData = list.get(i);

                    map.clear();
                    map.put("rptName", caseData.getRptName());
                    map.put("qryCondition", caseData.getQryCondition());
                    map.put("apNo", caseData.getApNo());
                    map.put("issuYm", caseData.getIssuYm());
                    map.put("payYm", caseData.getPayYm());
                    map.put("chkdate", caseData.getChkdate());
                    map.put("page", caseData.getPage().toPlainString());
                    map.put("filename", caseData.getFilename());
                    map.put("procuser", caseData.getProcuser());
                    map.put("proctime", caseData.getProctime());

                    executor.insert("BARPTLOG.insertData", map);

                    if (i % 3000 == 0 || i == list.size() - 1) {
                        rowsaffected += executor.executeBatch();
                        log.info("處理 Barptlog 第 " + i + " 筆");
                        if (i != list.size() - 1)
                            executor.startBatch();
                    }
                }
                
                log.info("處理 Barptlog 完成");

                return new Integer(rowsaffected);
            }
        });
        */
    }

}
