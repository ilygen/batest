package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BacutrecDao;
import tw.gov.bli.ba.domain.Bacutrec;
import tw.gov.bli.ba.util.StringUtility;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 另案扣減紀錄檔 (<code>BACUTREC</code>)
 * 
 * @author Rickychi
 */
@DaoTable("BACUTREC")
public class BacutrecDaoImpl extends SqlMapClientDaoSupport implements BacutrecDao {

    /**
     * 依傳入條件取得 另案扣減紀錄檔(<code>BAAPPBASE</code>) 資料
     * 
     * @param bacutrecId 資料列編號
     * @param baappbaseId給付主檔資料編號
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param procStat 處理狀態
     * @param eqType SQL EqualType
     * @return 內含 <code>Bacutrec</code> 物件的 List
     */
    @DaoFieldList("BACUTRECID,BAAPPBASEID,APNO,SEQNO,PROCSTAT,EQTYPE")
    public List<Bacutrec> selectDataBy(BigDecimal bacutrecId, BigDecimal baappbaseId, String apNo, String seqNo, String[] procStat, String eqType) {
        // 修改FORTIFY
        eqType = StringUtility.changOperator(eqType);
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(bacutrecId.toString()))
            map.put("bacutrecId", bacutrecId);
        if (StringUtils.isNotBlank(baappbaseId.toString()))
            map.put("baappbaseId", baappbaseId);
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        if (procStat != null && procStat.length > 0)
            map.put("procStat", procStat);
        if (StringUtils.isNotBlank(eqType))
            map.put("eqType", eqType);
        return getSqlMapClientTemplate().queryForList("BACUTREC.selectDataBy", map);
    }

}
