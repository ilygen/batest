package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.PfpfmDao;
import tw.gov.bli.ba.domain.Pfpfd;
import tw.gov.bli.ba.domain.Pfpfm;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 戶政全戶檔 (<code>PFPFM</code>)
 * 
 * @author KIYOMI
 */

@DaoTable("PFPFM")
public class PfpfmDaoImpl extends SqlMapClientDaoSupport implements PfpfmDao {

    /**
     * 依傳入條件取得 郵遞區號
     * 
     * @param cPrnDt
     * @return
     */
    public Integer selectCprnpage(String cPrnDt) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("cPrnDt", cPrnDt);

        return (Integer) getSqlMapClientTemplate().queryForObject("PFPFM.selectCprnpage", map);
    }

    /**
     * 依傳入條件取得 郵遞區號
     * 
     * @param cPrnDt
     * @param payKind
     * @return
     */
    public Integer selectPutfile_page(String cPrnDt, String payKind) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("cPrnDt", cPrnDt);
        map.put("payKind", payKind);

        return (Integer) getSqlMapClientTemplate().queryForObject("PFPFM.selectPutfile_page", map);
    }
    
    /**
     * 新增(<code>PFPFM</code>) 資料 for 整批收回核定清單
     * 
     * @param pfpfm
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForBatchPaymentReceiveData(Pfpfm pfpfm) {
        return (BigDecimal) getSqlMapClientTemplate().insert("PFPFM.insertDataForBatchPaymentReceiveData", pfpfm);
    }
}
