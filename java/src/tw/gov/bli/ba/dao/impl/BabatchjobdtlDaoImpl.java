package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BabatchjobdtlDao;
import tw.gov.bli.ba.domain.Babatchjobdtl;

public class BabatchjobdtlDaoImpl extends SqlMapClientDaoSupport implements BabatchjobdtlDao {
    /**
     * 查詢年金線上產製媒體排程-目前進度查詢(明細)
     * 
     * @param baJobId 資料列編號(JOBID)
     */

    public List<Babatchjobdtl> selectNowProgressStepsDtl(String baJobId) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("baJobId", baJobId);

        return (List<Babatchjobdtl>) getSqlMapClientTemplate().queryForList("BABATCHJOBDTL.selectNowProgressStepsDtl", map);

    }

    /**
     * 依傳入條件取得 勞保年金批次作業明細檔(<code>BABATCHJOBDTL</code>) 資料清單
     * 
     * @param baJobId 資料列編號(JOBID)
     * @return 內含 <code>Babatchjobdtl</code> 物件的 List
     */
    public List<Babatchjobdtl> selectBabatchjobdtlDataForBjMonthBatchBy(String baJobId) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(baJobId)) {
            map.put("baJobId", baJobId);
        }

        return getSqlMapClientTemplate().queryForList("BABATCHJOBDTL.selectBabatchjobdtlDataForBjMonthBatchBy", map);
    }

    /**
     * 批次處理 - 批次程式執行作業 - 新增排程作業狀態
     * 
     * @param 內含 <code>Babatchjobdtl</code> 物件的 List
     */
    public void insertBaBatchJobDtl(Babatchjobdtl babatchjobdtl) {

        if (babatchjobdtl != null) {
            getSqlMapClientTemplate().update("BABATCHJOBDTL.insertBaBatchJobDtl", babatchjobdtl);
        }
    }
    
    public void inserData(Babatchjobdtl babatchjobdtl) {
        getSqlMapClientTemplate().insert("BABATCHJOBDTL.insertData", babatchjobdtl);
    }

}
