package tw.gov.bli.ba.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import tw.gov.bli.ba.bj.cases.BatchRecTemp;
import tw.gov.bli.ba.dao.BagivedtlDao;
import tw.gov.bli.ba.domain.Babatchrec;
import tw.gov.bli.ba.domain.Bagivedtl;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;
import tw.gov.bli.common.domain.Mmaplog;
import tw.gov.bli.common.util.StrUtil;

/**
 * DAO for 給付入帳媒體明細錄檔 (<code>BAGIVEDTL</code>)
 * 
 * @author swim
 */
@DaoTable("BAGIVEDTL")
public class BagivedtlDaoImpl extends SqlMapClientDaoSupport implements BagivedtlDao {
    private static Log log = LogFactory.getLog(BagivedtlDaoImpl.class);

    /**
     * 新增資料到 給付入帳媒體明細錄檔(<code>BAGIVEDTL</code>)
     * 
     * @param data <code>BatchRecTemp</code> 物件
     */
    public void insertData(final List<BatchRecTemp> caseList) {
        for (BatchRecTemp caseData : caseList) {
            caseData.setUpdTime(DateUtility.getNowChineseDateTime(true));

            getSqlMapClientTemplate().insert("BAGIVEDTL.insertData", caseData);
        }
        /*
         * getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
         * public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
         * executor.startBatch();
         * for (BatchRecTemp caseData : caseList) {
         * caseData.setUpdTime(DateUtility.getNowChineseDateTime(true));
         * executor.insert("BAGIVEDTL.insertData", caseData);
         * }
         * int rowsaffected = executor.executeBatch();
         * return new Integer(rowsaffected);
         * }
         * });
         */
    }

    public void insertStringData(final String batchRecId, final String mfileName, final String mfileDate, final String[] stringList) {
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                int rowsaffected = 0;

                HashMap<String, String> map = new HashMap<String, String>();

                for (int i = 1; i < stringList.length - 1; i++) {
                    if (i == 1)
                        executor.startBatch();

                    map.clear();
                    map.put("batchRecId", batchRecId);
                    map.put("mfileName", mfileName);
                    map.put("mfileDate", mfileDate);
                    map.put("seqNo", String.valueOf(i));
                    map.put("data", stringList[i]);

                    executor.insert("BAGIVEDTL.insertStringData", map);

                    if (i % 5000 == 0 || i == stringList.length - 2) {
                        rowsaffected += executor.executeBatch();
                        log.info("處理第 " + i + " 筆");
                        if (i != stringList.length - 2)
                        executor.startBatch();
                    }
                }

                // int rowsaffected = executor.executeBatch();

                return new Integer(rowsaffected);
            }
        });
    }

    /**
     * 依傳入條件取得 給付入帳媒體明細錄檔(<code>BAGIVEDTL</code>) 資料 FOR 給付媒體回押註記
     * 
     * @param baBatchRecId
     * @return <code>List<Bagivedtl></code> 物件
     */
    @DaoFieldList("BABATCHRECID")
    public List<Bagivedtl> selectUpdatePaidMarkList3By(String baBatchRecId) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(baBatchRecId))
            map.put("baBatchRecId", baBatchRecId);

        return getSqlMapClientTemplate().queryForList("BAGIVEDTL.selectUpdatePaidMarkData3", map);
    }

    /**
     * 依傳入條件取得 給付入帳媒體明細錄檔(<code>BAGIVEDTL</code>) 資料 FOR 給付媒體回押註記
     * 
     * @param baBatchRecId
     * @return <code>List<Bagivedtl></code> 物件
     */
    @DaoFieldList("BABATCHRECID,PAYDATE,TATYP")
    public Bagivedtl selectUpdatePaidMark2By(String baBatchRecId, String payCode, String payDate, String taTyp) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(baBatchRecId))
            map.put("baBatchRecId", baBatchRecId);
        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(payDate))
            map.put("payDate", payDate);
        if (StringUtils.isNotBlank(taTyp))
            map.put("taTyp", taTyp);

        return (Bagivedtl) getSqlMapClientTemplate().queryForObject("BAGIVEDTL.selectUpdatePaidMarkData2", map);
    }

    /**
     * Call StoreProcedure
     * 
     * @param procTyp 回押註記
     * @param baBatchRecId
     * @return
     */
    @DaoFieldList("PROCTYP,PAYCODE,BABATCHRECID")
    public String updatePaidMarkStatus(String procTyp, String payCode, BigDecimal baBatchRecId, UserBean userData) {
        String procMsgCode = null;
        String procMsg = null;
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("v_i_babatchrecid", baBatchRecId.toString());
        map.put("v_i_payCode", payCode);
        map.put("v_i_procempno", userData.getEmpNo());
        map.put("v_i_procdeptid", userData.getDeptId());
        map.put("v_i_procip", userData.getLoginIP());
        map.put("v_o_procMsgCode", procMsgCode);
        map.put("v_o_procMsg", procMsg);

        getSqlMapClientTemplate().queryForObject("BAGIVEDTL.updatePaidMarkStatus", map);

        procMsgCode = (String) map.get("v_o_procMsgCode");
        procMsg = (String) map.get("v_o_procMsg");

        return procMsg;

    }

    /**
     * Call StoreProcedure
     * 
     * @param procTyp 回押註記
     * @param baBatchRecId
     * @return
     */
    @DaoFieldList("PROCTYP,PAYCODE,BABATCHRECID")
    public String chkPaidMarkStatus(String procTyp, String payCode, BigDecimal baBatchRecId, UserBean userData) {
        String procMsgCode = null;
        String procMsg = null;
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("v_i_babatchrecid", baBatchRecId.toString());
        map.put("v_i_payCode", payCode);
        map.put("v_i_procempno", userData.getEmpNo());
        map.put("v_o_procMsgCode", "");
        map.put("v_o_procMsg", "");
        getSqlMapClientTemplate().queryForObject("BAGIVEDTL.chkPaidMarkStatus", map);
        procMsgCode = (String) map.get("v_o_procMsgCode");
        procMsg = (String) map.get("v_o_procMsg");

        return procMsg;
    }

    /**
     * 依傳入條件取得 給付別(<code>BAGIVEDTL</code>) 資料 FOR 給付媒體回押註記
     * 
     * @param payDate
     * @param baBatchRecId
     * @param taTyp
     * @return <code>List<Bagivedtl></code> 物件
     */
    public String selectUpdatePaidMarkBJPayCodeBy(String payDate, BigDecimal baBatchRecId, String taTyp) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("payDate", payDate);
        map.put("baBatchRecId", baBatchRecId);
        map.put("taTyp", taTyp);
        return (String) getSqlMapClientTemplate().queryForObject("BAGIVEDTL.selectUpdatePaidMarkBJPayCodeBy", map);
    }
}
