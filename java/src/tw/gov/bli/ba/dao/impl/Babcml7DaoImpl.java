package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.Babcml7Dao;
import tw.gov.bli.ba.domain.Babcml7;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 勞保複檢費用主檔 (<code>BABCML7</code>)
 * 
 * @author Evelyn Hsu
 */
public class Babcml7DaoImpl extends SqlMapClientDaoSupport implements Babcml7Dao {

    /**
     * 依傳入的條件取得 勞保複檢費用主檔(<code>BABCML7</code>) for 複檢費用審核給付清單
     * 
     * @param apNo 受理編號
     * @param apSeq 申請序號
     * @return
     */
    @DaoFieldList("APNO,APSEQ")
    public List<Babcml7> selecReviewFeeReceiptRpt01ListBy(String apNo, String apSeq) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(apSeq))
            map.put("apSeq", apSeq);

        return getSqlMapClientTemplate().queryForList("BABCML7.getReviewFeeReceiptRpt01DataBy", map);
    }

    /**
     * 依傳入的條件取得 勞保複檢費用主檔(<code>BABCML7</code>) for 複檢費用核定通知書
     * 
     * @param apNo 受理編號
     * @param apSeq 申請序號
     * @return
     */
    @DaoFieldList("APNO,APSEQ")
    public List<Babcml7> selecReviewFeeReceiptRpt02ListBy(String apNo, String apSeq) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(apSeq))
            map.put("apSeq", apSeq);
        

        return getSqlMapClientTemplate().queryForList("BABCML7.getReviewFeeReceiptRpt02DataBy", map);
    }

    /**
     * 依傳入的條件取得 勞保複檢費用主檔(<code>BABCML7</code>) for 複檢費用核定清單
     * 
     * @param apNo 受理編號
     * @param apSeq 申請序號
     * @return
     */
    @DaoFieldList("APNO,APSEQ")
    public List<Babcml7> selecReviewFeeReceiptRpt03ListBy(String apNo, String apSeq) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(apSeq))
            map.put("apSeq", apSeq);

        return getSqlMapClientTemplate().queryForList("BABCML7.getReviewFeeReceiptRpt03DataBy", map);
    }

    /**
     * 依傳入的條件取得 勞保複檢費用主檔(<code>BABCML7</code>) 該筆受理案件的下一筆複檢費用申請序 for 複檢費用受理作業
     * 
     * @param reApNo 複檢費用受理編號
     * @return
     */
    @DaoFieldList("REAPNO")
    public Integer getReviewFeeReceiptApSeqForInsertBy(String reApNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("reApNo", reApNo);

        return (Integer) getSqlMapClientTemplate().queryForObject("BABCML7.getReviewFeeReceiptApSeqForInsertBy", map);
    }

    /**
     * 依傳入的條件取得 勞保複檢費用主檔(<code>BABCML7</code>) 的資料 for 複檢費用受理作業
     * 
     * @param reApNo 複檢費用受理編號
     * @param evtIdnNo 事故者身分證號
     * @return
     */
    @DaoFieldList("REAPNO,EVTIDNNO")
    public List<Babcml7> getReviewFeeReceiptListForUpdateBy(String reApNo, String evtIdnNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("reApNo", reApNo);

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);

        return getSqlMapClientTemplate().queryForList("BABCML7.getReviewFeeReceiptListForUpdateBy", map);
    }

    /**
     * 依傳入的條件取得 勞保複檢費用主檔(<code>BABCML7</code>) 的資料 for 複檢費用受理作業 (MMAPLOG 用)
     * 
     * @param reApNo 複檢費用受理編號
     * @param apSeq 複檢費用申請序
     * @return
     */
    @DaoFieldList("REAPNO,APSEQ")
    public Babcml7 getReviewFeeReceiptDataForUpdateBy(String reApNo, BigDecimal apSeq) {
        if (StringUtils.isNotBlank(reApNo) && apSeq != null) {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("reApNo", reApNo);
            map.put("apSeq", apSeq);

            return (Babcml7) getSqlMapClientTemplate().queryForObject("BABCML7.getReviewFeeReceiptDataForUpdateBy", map);
        }
        else {
            return null;
        }
    }

    /**
     * 新增資料至 勞保複檢費用主檔(<code>BABCML7</code>) for 複檢費用受理作業
     * 
     * @param data
     */
    public void insertReviewFeeReceiptData(Babcml7 data) {
        if (data != null)
            getSqlMapClientTemplate().insert("BABCML7.insertReviewFeeReceiptData", data);
    }

    /**
     * 更新 勞保複檢費用主檔(<code>BABCML7</code>) 的資料 for 複檢費用受理作業
     * 
     * @param data
     */
    public void updateReviewFeeReceiptData(Babcml7 data) {
        if (data != null)
            getSqlMapClientTemplate().update("BABCML7.updateReviewFeeReceiptData", data);
    }

    /**
     * 刪除 勞保複檢費用主檔(<code>BABCML7</code>) 的資料 for 複檢費用受理作業
     * 
     * @param reApNo 複檢費用受理編號
     * @param apSeq 複檢費用申請序
     */
    public void deleteReviewFeeReceiptData(String reApNo, BigDecimal apSeq) {
        if (StringUtils.isNotBlank(reApNo) && apSeq != null) {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("reApNo", reApNo);
            map.put("apSeq", apSeq);

            getSqlMapClientTemplate().update("BABCML7.deleteReviewFeeReceiptData", map);
        }
    }

    /**
     * 依傳入的條件取得 勞保複檢費用主檔(<code>BABCML7</code>) 的資料 for 複檢費用審核作業
     * 
     * @param reApNo 複檢費用受理編號
     * @return
     */
    @DaoFieldList("REAPNO")
    public List<Babcml7> getReviewFeeReviewListBy(String reApNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("reApNo", reApNo);

        return getSqlMapClientTemplate().queryForList("BABCML7.getReviewFeeReviewListBy", map);
    }

    /**
     * 依傳入的條件取得 勞保複檢費用主檔(<code>BABCML7</code>) 的資料 for 複檢費用審核作業
     * 
     * @param reApNo 複檢費用受理編號
     * @param apSeq 複檢費用申請序
     * @return
     */
    @DaoFieldList("REAPNO,APSEQ")
    public Babcml7 getReviewFeeReviewDataForUpdateBy(String reApNo, BigDecimal apSeq) {
        if (StringUtils.isNotBlank(reApNo) && apSeq != null) {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("reApNo", reApNo);
            map.put("apSeq", apSeq);

            return (Babcml7) getSqlMapClientTemplate().queryForObject("BABCML7.getReviewFeeReviewDataForUpdateBy", map);
        }
        else {
            return null;
        }
    }

    /**
     * 更新 勞保複檢費用主檔(<code>BABCML7</code>) 的資料 for 複檢費用審核作業
     * 
     * @param data
     */
    public void updateReviewFeeReviewData(Babcml7 data) {
        if (data != null)
            getSqlMapClientTemplate().update("BABCML7.updateReviewFeeReviewData", data);
    }

    /**
     * 依傳入的條件取得 勞保複檢費用主檔(<code>BABCML7</code>) 的資料 for 複檢費用決行作業
     * 
     * @param reApNo 複檢費用受理編號
     * @return
     */
    @DaoFieldList("REAPNO")
    public List<Babcml7> getReviewFeeDecisionListBy(String reApNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("reApNo", reApNo);

        return getSqlMapClientTemplate().queryForList("BABCML7.getReviewFeeDecisionListBy", map);
    }

    /**
     * 依傳入的條件取得 勞保複檢費用主檔(<code>BABCML7</code>) 的資料 for 複檢費用決行作業
     * 
     * @param reApNo 複檢費用受理編號
     * @param apSeq 複檢費用申請序
     * @return
     */
    @DaoFieldList("REAPNO,APSEQ")
    public Babcml7 getReviewFeeDecisionDataForUpdateBy(String reApNo, BigDecimal apSeq) {
        if (StringUtils.isNotBlank(reApNo) && apSeq != null) {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("reApNo", reApNo);
            map.put("apSeq", apSeq);

            return (Babcml7) getSqlMapClientTemplate().queryForObject("BABCML7.getReviewFeeDecisionDataForUpdateBy", map);
        }
        else {
            return null;
        }
    }

    /**
     * 更新 勞保複檢費用主檔(<code>BABCML7</code>) 的資料 for 複檢費用決行作業
     * 
     * @param data
     */
    public void updateReviewFeeDecisionData(Babcml7 data) {
        if (data != null)
            getSqlMapClientTemplate().update("BABCML7.updateReviewFeeDecisionData", data);
    }

    /**
     * 依傳入的條件取得 勞保複檢費用主檔(<code>BABCML7</code>) 複檢費用資料 for 失能年金給付查詢
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Babcml7</code> 物件的 List
     */
    @DaoFieldList("APNO")
    public List<Babcml7> selectReFeesMasterDataForDisabledPaymentQuery(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);

        return getSqlMapClientTemplate().queryForList("BABCML7.selectReFeesMasterDataForDisabledPaymentQuery", map);
    }

    /**
     * 依傳入的條件取得 勞保複檢費用主檔(<code>BABCML7</code>) 複檢費用明細資料 for 失能年金給付查詢
     * 
     * @param reApNo 複檢費用受理編號
     * @param apSeq 申請序號
     * @param apNo 受理編號
     * @return 內含 <code>Babcml7</code> 物件的 List
     */
    @DaoFieldList("REAPNO,APSEQ,APNO")
    public List<Babcml7> selectReFeesDetailDataForDisabledPaymentQuery(String reApNo, BigDecimal apSeq, String apNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("reApNo", reApNo);
        map.put("apSeq", apSeq);
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BABCML7.selectReFeesDetailDataForDisabledPaymentQuery", map);
    }
}
