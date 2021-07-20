package tw.gov.bli.ba.dao.impl;

/**
 * DAO for 重新查核失能程度檔 (<code>BARECHECK</code>)
 * 
 * @author Noctis
 */
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BachkfileDao;
import tw.gov.bli.ba.dao.BarecheckDao;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Bachkfile;
import tw.gov.bli.ba.domain.Barecheck;
import tw.gov.bli.ba.update.cases.ChkFileCase;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

@DaoTable("BARECHECK")
public class BarecheckDaoImpl extends SqlMapClientDaoSupport implements BarecheckDao {

    /**
     * 依傳入條件取得 重新查核失能程度檔(<code>BARECHECK</code>) 資料 FOR 失能年金受款人資料更正 重新查核失能程度
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @return <code>List<Bachkfile></code> 物件
     */
    @DaoFieldList("APNO,SEQNO")
    public List<Barecheck> selectBareCheckDataBy(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        if (StringUtils.isNotBlank(seqNo)) {
            map.put("seqNo", seqNo);
        }
        return getSqlMapClientTemplate().queryForList("BARECHECK.selectBareCheckDataBy", map);
    }
    
    /**
     * 更新 重新查核失能程度檔(<code>BARECHECK</code>) 資料
     * 
     * @param barecheck 重新查核失能程度檔
     */
    public void updateBareCheckData(Barecheck barecheck) {
        getSqlMapClientTemplate().update("BARECHECK.updateBareCheckData", barecheck);
    }
    
    /**
     * 刪除 重新查核失能程度檔(<code>BARECHECK</code>) 資料
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param reChkYm 重新查核失能程度年月
     */
    public void deleteDataForBareCheck(String apNo, String seqNo, String reChkYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("reChkYm", reChkYm);
        getSqlMapClientTemplate().update("BARECHECK.deleteDataForBareCheck", map);
    }
    
    /**
     * 依傳入條件取得 重新查核失能程度檔(<code>BARECHECK</code>) 資料 FOR  受理審核清單 重新查核失能程度 資料
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @return <code>List<Bachkfile></code> 物件
     */
    @DaoFieldList("APNO,SEQNO")
    public List<Barecheck> selectBareCheckDataForRpt01By(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        if (StringUtils.isNotBlank(seqNo)) {
            map.put("seqNo", seqNo);
        }
        return getSqlMapClientTemplate().queryForList("BARECHECK.selectBareCheckDataForRpt01By", map);
    }
    
    /**
     * 依傳入條件取得 重新查核失能程度檔(<code>BARECHECK</code>) 資料 FOR  受理審核清單 重新查核失能程度 資料 資料庫是否已有同月份資料
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @param comReChkYm 完成重新查核年月
     * @return <code>Boolean</code>
     */
    public Boolean selectBareCheckDataCountBy(String apNo, String seqNo, String comReChkYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("comReChkYm", comReChkYm);
        Integer count = (Integer) getSqlMapClientTemplate().queryForObject("BARECHECK.selectBareCheckDataCountBy", map);
        // 檢查資料庫是否已有同月份資料,count > 0表示已有資料,因此回傳不可修改
        if (count != null && count > 0) {
            return new Boolean(true);
        }
        if (count != null && count == 0) {
            return new Boolean(false);
        }
        return new Boolean(false);
    }
    
    /**
     * 依傳入條件取得 重新查核失能程度檔(<code>BARECHECK</code>) 資料 FOR  受理審核清單 重新查核失能程度 資料
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @return <code>Boolean</code>
     */
    public String selectMaxReChkYmBy(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
       
        return (String) getSqlMapClientTemplate().queryForObject("BARECHECK.selectMaxReChkYmBy", map);
    }
}
