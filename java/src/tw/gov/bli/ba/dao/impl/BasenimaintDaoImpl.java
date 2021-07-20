package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BasenimaintDao;
import tw.gov.bli.ba.domain.Basenimaint;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 年資維護介接檔 (<code>BASENIMAINT</code>)
 * 
 * @author Rickychi
 */
@DaoTable("BASENIMAINT")
public class BasenimaintDaoImpl extends SqlMapClientDaoSupport implements BasenimaintDao {
    /**
     * 依傳入條件取得 年資維護介接檔(<code>BASENIMAINT</code>) 資料List
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param seniTyp 處理狀態
     * @return 內含 <code>Basenimaint</code> 物件的 List
     */
    @DaoFieldList("APNO,SEQNO,SENITYP") 
    public List<Basenimaint> selectDataBy(String apNo, String seqNo, String seniTyp) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        if (StringUtils.isNotBlank(seqNo)) {
            map.put("seqNo", seqNo);
        }
        if (StringUtils.isNotBlank(seniTyp)) {
            map.put("seniTyp", seniTyp);
        }
        return getSqlMapClientTemplate().queryForList("BASENIMAINT.selectDataBy", map);
    }

    /**
     * 依傳入條件取得 年資維護介接檔(<code>BASENIMAINT</code>) 序號
     * 
     * @param apNo 受理編號
     * @param seniTyp 處理狀態
     * @return
     */
    @DaoFieldList("APNO,SENITYP") 
    public String selectSeqNoBy(String apNo, String seniTyp) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seniTyp", seniTyp);
        return (String) getSqlMapClientTemplate().queryForObject("BASENIMAINT.selectSeqNoBy", map);
    }

    /**
     * 新增 年資維護介接檔(<code>BASENIMAINT</code>) 資料
     * 
     * @param basenimaint 年資維護介接檔
     * @return
     */
    public void insertData(Basenimaint basenimaint) {
        getSqlMapClientTemplate().update("BASENIMAINT.insertData", basenimaint);
    }

    /**
     * 修改 年資維護介接檔(<code>BASENIMAINT</code>) 資料
     * 
     * @param basenimaint 給付主檔
     * @return
     */
    public void updateData(Basenimaint basenimaint) {
        getSqlMapClientTemplate().update("BASENIMAINT.updateData", basenimaint);
    }

    /**
     * 修改 年資維護介接檔(<code>BASENIMAINT</code>) 資料 for 一次請領之年資採計方式
     * 
     * @param basenimaint 給付主檔
     * @return
     */
    public void updateDataForOldSeniab(Basenimaint basenimaint) {
        getSqlMapClientTemplate().update("BASENIMAINT.updateDataForOldSeniab", basenimaint);
    }

    /**
     * 刪除 年資維護介接檔(<code>BASENIMAINT</code>) 資料
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param seniTyp 年資類別
     * @return
     */
    public void deleteData(String apNo, String seqNo, String seniTyp) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("seniTyp", seniTyp);
        getSqlMapClientTemplate().update("BASENIMAINT.deleteData", map);
    }
}
