package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BafailurelistDao;
import tw.gov.bli.ba.domain.Bafailurelist;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 整批收回失敗清單檔 (<code>BAFAILURELIST</code>)
 * 
 * @author KIYOMI
 */
@DaoTable("BAFAILURELIST")
public class BafailurelistDaoImpl extends SqlMapClientDaoSupport implements BafailurelistDao {
    /**
     * 依傳入條件取得 被保險人異動資料檔(<code>BAFAILURELIST</code>) 資料清單
     * 
     * @param payCode 給付別
     * @param chkDate 核收日期
     * @return 內含 <code>Bafailurelist</code> 物件的 List
     */
    @DaoFieldList("PAYCODE,CHKDATE")
    public List<Bafailurelist> selectBafailureListDataBy(String payCode,String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }
        if (StringUtils.isNotBlank(chkDate)) {
            map.put("chkDate", chkDate);
        }

        return getSqlMapClientTemplate().queryForList("BAFAILURELIST.selectBafailureListDataBy", map);
    }

    /**
     * 新增(<code>BAFAILURELIST</code>) 資料 for 整批收回核定清單
     * 
     * @param bafailurelist
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForFailureListData(Bafailurelist bafailurelist) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAFAILURELIST.insertDataForFailureListData", bafailurelist);
    }
    
}
