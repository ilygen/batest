package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BaappexpandDao;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Baappexpand;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * DAO for 給付延伸主檔 (<code>BAAPPEXPAND</code>)
 * 
 * @author Evelyn Hsu
 */

@DaoTable("BAAPPEXPAND")
public class BaappexpandDaoImpl extends SqlMapClientDaoSupport implements BaappexpandDao {

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 詳細資料
     * 
     * @param baappbaseId 給付主檔資料列編號
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return <code>Baappexpand</code> 物件
     */
    @DaoFieldList("BAAPPBASEID,APNO,SEQNO")
    public List<Baappexpand> getDisableBaappexpandListBy(BigDecimal baappbaseId, String apNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("baappbaseId", baappbaseId);
        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        map.put("seqNo", "0000");
        return getSqlMapClientTemplate().queryForList("BAAPPEXPAND.selectDisableBaappexpandListBy", map);
    }

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 詳細資料
     * 
     * @param baappbaseId 給付主檔資料列編號
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return <code>Baappexpand</code> 物件
     */
    @DaoFieldList("BAAPPBASEID,APNO,SEQNO")
    public List<Baappexpand> getSurvivorBaappexpandListBy(BigDecimal baappbaseId, String apNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("baappbaseId", baappbaseId);
        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        map.put("seqNo", "0000");
        return getSqlMapClientTemplate().queryForList("BAAPPEXPAND.selectSurvivorBaappexpandListBy", map);
    }

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 詳細資料
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return <code>Baappexpand</code> 物件
     */
    @DaoFieldList("BAAPPBASEID,APNO,SEQNO")
    public List<Baappexpand> getSurvivorBaappexpandListByEvt(String apNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        map.put("seqNo", "0000");
        return getSqlMapClientTemplate().queryForList("BAAPPEXPAND.selectSurvivorBaappexpandListByEvt", map);
    }

    /**
     * 新增 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 失能年金受理作業
     * 
     * @param baappexpand 給付延伸主檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForDisabledAnnuityReceipt(Baappexpand baappexpand) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAAPPEXPAND.insertDataForDisabledAnnuityReceipt", baappexpand);
    }

    /**
     * 修改 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 失能年金受理作業
     * 
     * @param baappexpand 給付延伸主檔
     */
    public void updateDataForDisabledAnnuityReceipt(Baappexpand baappexpand) {
        getSqlMapClientTemplate().update("BAAPPEXPAND.updateDataForDisabledAnnuityReceipt", baappexpand);
    }

    /**
     * 新增 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 失能年金受款人資料更正作業
     * 
     * @param baappexpand 給付延伸主檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForDisabledPayeeDataUpdate(Baappexpand baappexpand) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAAPPEXPAND.insertDataForDisabledPayeeDataUpdate", baappexpand);
    }

    /**
     * 新增 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 遺屬年金受款人資料更正作業
     * 
     * @param baappexpand 給付延伸主檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForSurvivorPayeeDataUpdate(Baappexpand baappexpand) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAAPPEXPAND.insertDataForSurvivorPayeeDataUpdate", baappexpand);
    }

    /**
     * 修改 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 遺屬年金受款人資料更正作業
     * 
     * @param baappexpand 給付延伸主檔
     * @return <code>BigDecimal</code>
     */
    public void updateDataForSurvivorPayeeDataUpdate(Baappexpand baappexpand) {
        getSqlMapClientTemplate().update("BAAPPEXPAND.updateDataForSurvivorPayeeDataUpdate", baappexpand);
    }

    /**
     * 新增 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 遺屬年金受理作業
     * 
     * @param baappexpand 給付延伸主檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForSurvivorAnnuityReceipt(Baappexpand baappexpand) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAAPPEXPAND.insertDataForSurvivorAnnuityReceipt", baappexpand);
    }

    /**
     * 修改 給付延伸主檔(<code>BAAPPEXPAND</code>) 事故者資料 for 遺屬年金受理作業
     * 
     * @param baappexpand 給付延伸主檔
     */
    public void updateEvtDataForSurvivorAnnuityReceipt(Baappexpand baappexpand) {
        getSqlMapClientTemplate().update("BAAPPEXPAND.updateEvtDataForSurvivorAnnuityReceipt", baappexpand);
    }

    /**
     * 修改 給付延伸主檔(<code>BAAPPEXPAND</code>) 事故者資料 for 遺屬年金受理作業 (多筆)
     * 
     * @param baappexpandList 給付延伸主檔List
     */
    public void updateEvtDataForSurvivorAnnuityReceipt(final List<Baappexpand> baappexpandList) {
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();
                for (int i = 0; i < baappexpandList.size(); i++) {
                    Baappexpand baappexpand = baappexpandList.get(i);
                    executor.update("BAAPPEXPAND.updateEvtDataForSurvivorAnnuityReceipt", baappexpand);
                }
                int rowsaffected = executor.executeBatch();

                return new Integer(rowsaffected);
            }
        });
    }

    /**
     * 修改 給付延伸主檔(<code>BAAPPEXPAND</code>) 遺屬資料 for 遺屬年金受理作業
     * 
     * @param baappexpand 給付延伸主檔
     */
    public void updateBenDataForSurvivorAnnuityReceipt(Baappexpand baappexpand) {
        getSqlMapClientTemplate().update("BAAPPEXPAND.updateBenDataForSurvivorAnnuityReceipt", baappexpand);
    }

    /**
     * 刪除 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 By baappbaseId
     * 
     * @param baappbaseId 給付主檔資料列編號
     */
    public void deleteDataByBaappbaseId(BigDecimal baappbaseId) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("baappbaseId", baappbaseId);
        getSqlMapClientTemplate().delete("BAAPPEXPAND.deleteDataByBaappbaseId", map);
    }

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 失能年金案件資料更正
     * 
     * @param baappbaseId 給付主檔資料列編號
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return
     */
    @DaoFieldList("BAAPPBASEID,APNO,SEQNO")
    public Baappexpand getDisabledApplicationDataUpdateExpandDataBy(BigDecimal baappbaseId, String apNo, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("baappbaseId", baappbaseId);
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);

        return (Baappexpand) getSqlMapClientTemplate().queryForObject("BAAPPEXPAND.getDisabledApplicationDataUpdateExpandDataBy", map);
    }

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 失能年金案件資料更正 (須更新的資料列編號)
     * 
     * @param apNo 受理編號
     * @param baappbaseId 給付主檔資料列編號
     * @return <code>BAAPPEXPANDID</code> 資料編號
     */
    @DaoFieldList("APNO,BAAPPBASEID")
    public List<BigDecimal> getDisabledApplicationDataUpdateListForUpdateBy(String apNo, BigDecimal baappbaseId) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("apNo", apNo);
        map.put("baappbaseId", baappbaseId);

        return getSqlMapClientTemplate().queryForList("BAAPPEXPAND.getDisabledApplicationDataUpdateListForUpdateBy", map);
    }

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 失能年金案件資料更正 (依指定的資料列編號取得須更新的給付延伸主檔資料)
     * 
     * @param baappexpandId 資料編號
     * @return
     */
    @DaoFieldList("BAAPPBASEID")
    public Baappexpand getDisabledApplicationDataUpdateExpandDataByBaappexpandId(BigDecimal baappexpandId) {
        HashMap<String, BigDecimal> map = new HashMap<String, BigDecimal>();

        map.put("baappexpandId", baappexpandId);

        return (Baappexpand) getSqlMapClientTemplate().queryForObject("BAAPPEXPAND.getDisabledApplicationDataUpdateExpandDataByBaappexpandId", map);
    }

    /**
     * 更新 給付延伸主檔(<code>BAAPPEXPAND</code>) 的資料 for 失能年金案件資料更正
     * 
     * @param dataList
     */
    public void updateDisabledApplicationDataUpdateData(final List<Baappexpand> dataList) {
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();

                for (int i = 0; i < dataList.size(); i++) {
                    Baappexpand data = (Baappexpand) dataList.get(i);
                    executor.update("BAAPPEXPAND.updateDisabledApplicationDataUpdateData", data);
                }

                int rowsaffected = executor.executeBatch();

                return new Integer(rowsaffected);
            }
        });
    }

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 失能年金給付
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Baappexpand getDisabledReviewRpt01AnnuityPayList(String apNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("apNo", apNo);

        return (Baappexpand) getSqlMapClientTemplate().queryForObject("BAAPPEXPAND.selectDisabledReviewRpt01AnnuityPayList", map);
    }

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 失能年金給付
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Baappexpand getDisabledReviewRpt01SurvivorPayList(String apNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("apNo", apNo);

        return (Baappexpand) getSqlMapClientTemplate().queryForObject("BAAPPEXPAND.selectDisabledReviewRpt01SurvivorPayList", map);
    }

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 遺屬年金案件資料更正
     * 
     * @param baappbaseId 給付主檔資料列編號
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return
     */
    @DaoFieldList("BAAPPBASEID,APNO,SEQNO")
    public Baappexpand getSurvivorApplicationDataUpdateExpandDataBy(BigDecimal baappbaseId, String apNo, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("baappbaseId", baappbaseId);
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);

        return (Baappexpand) getSqlMapClientTemplate().queryForObject("BAAPPEXPAND.getSurvivorApplicationDataUpdateExpandDataBy", map);
    }

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 遺屬年金案件資料更正 (須更新的資料列編號)
     * 
     * @param apNo 受理編號
     * @param baappbaseId 給付主檔資料列編號
     * @return <code>BAAPPEXPANDID</code> 資料編號
     */
    @DaoFieldList("APNO,BAAPPBASEID")
    public List<BigDecimal> getSurvivorApplicationDataUpdateListForUpdateBy(String apNo, BigDecimal baappbaseId) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("apNo", apNo);
        map.put("baappbaseId", baappbaseId);

        return getSqlMapClientTemplate().queryForList("BAAPPEXPAND.getSurvivorApplicationDataUpdateListForUpdateBy", map);
    }

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 遺屬年金案件資料更正 (依指定的資料列編號取得須更新的給付延伸主檔資料)
     * 
     * @param baappexpandId 資料編號
     * @return
     */
    @DaoFieldList("BAAPPBASEID")
    public Baappexpand getSurvivorApplicationDataUpdateExpandDataByBaappexpandId(BigDecimal baappexpandId) {
        HashMap<String, BigDecimal> map = new HashMap<String, BigDecimal>();

        map.put("baappexpandId", baappexpandId);

        return (Baappexpand) getSqlMapClientTemplate().queryForObject("BAAPPEXPAND.getSurvivorApplicationDataUpdateExpandDataByBaappexpandId", map);
    }

    /**
     * 更新 給付延伸主檔(<code>BAAPPEXPAND</code>) 的資料 for 遺屬年金案件資料更正
     * 
     * @param dataList
     */
    public void updateSurvivorApplicationDataUpdateData(final List<Baappexpand> dataList) {
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();

                for (int i = 0; i < dataList.size(); i++) {
                    Baappexpand data = (Baappexpand) dataList.get(i);
                    executor.update("BAAPPEXPAND.updateSurvivorApplicationDataUpdateData", data);
                }

                int rowsaffected = executor.executeBatch();

                return new Integer(rowsaffected);
            }
        });
    }

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 複檢費用受理作業 (Header for Add)
     * 
     * @param baappbaseId 給付主檔資料列編號
     */
    @DaoFieldList("BAAPPBASEID")
    public Baappexpand getReviewFeeReceiptHeaderDataForInsertBy(BigDecimal baappbaseId) {
        HashMap<String, BigDecimal> map = new HashMap<String, BigDecimal>();

        map.put("baappbaseId", baappbaseId);

        return (Baappexpand) getSqlMapClientTemplate().queryForObject("BAAPPEXPAND.getReviewFeeReceiptHeaderDataForInsertBy", map);
    }

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 複檢費用受理作業 (Header for Update)
     * 
     * @param baappbaseId 給付主檔資料列編號
     * @return
     */
    @DaoFieldList("BAAPPBASEID")
    public Baappexpand getReviewFeeReceiptHeaderDataForUpdateBy(BigDecimal baappbaseId) {
        HashMap<String, BigDecimal> map = new HashMap<String, BigDecimal>();

        map.put("baappbaseId", baappbaseId);

        return (Baappexpand) getSqlMapClientTemplate().queryForObject("BAAPPEXPAND.getReviewFeeReceiptHeaderDataForUpdateBy", map);
    }

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 複檢費用審核作業
     * 
     * @param baappbaseId 給付主檔資料列編號
     * @return
     */
    @DaoFieldList("BAAPPBASEID")
    public Baappexpand getReviewFeeReviewHeaderDataBy(BigDecimal baappbaseId) {
        HashMap<String, BigDecimal> map = new HashMap<String, BigDecimal>();

        map.put("baappbaseId", baappbaseId);

        return (Baappexpand) getSqlMapClientTemplate().queryForObject("BAAPPEXPAND.getReviewFeeReviewHeaderDataBy", map);
    }

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 複檢費用決行作業
     * 
     * @param baappbaseId 給付主檔資料列編號
     * @return
     */
    @DaoFieldList("BAAPPBASEID")
    public Baappexpand getReviewFeeDecisionHeaderDataBy(BigDecimal baappbaseId) {
        HashMap<String, BigDecimal> map = new HashMap<String, BigDecimal>();

        map.put("baappbaseId", baappbaseId);

        return (Baappexpand) getSqlMapClientTemplate().queryForObject("BAAPPEXPAND.getReviewFeeDecisionHeaderDataBy", map);
    }

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 失能相關資料 for 失能 / 遺屬 年金給付審核作業
     * 
     * @param apNo 受理編號
     * @return <code>Baappexpand</code> 物件
     */
    @DaoFieldList("APNO")
    public Baappexpand selectDisabledDataForPaymentReview(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return (Baappexpand) getSqlMapClientTemplate().queryForObject("BAAPPEXPAND.selectDisabledDataForPaymentReview", map);
    }

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 遺屬年金受款人資料更正作業
     * 
     * @param baappbaseId 給付主檔資料列編號
     */
    @DaoFieldList("BAAPPBASEID")
    public Baappexpand selectDataForSurvivorPayeeDataUpdate(BigDecimal baappbaseId) {
        HashMap<String, BigDecimal> map = new HashMap<String, BigDecimal>();
        map.put("baappbaseId", baappbaseId);
        return (Baappexpand) getSqlMapClientTemplate().queryForObject("BAAPPEXPAND.selectDataForSurvivorPayeeDataUpdate", map);
    }

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 年金給付
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Baappexpand getSurvivorReviewRpt01AnnuityPayList(String apNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("apNo", apNo);

        return (Baappexpand) getSqlMapClientTemplate().queryForObject("BAAPPEXPAND.selectSurvivorReviewRpt01AnnuityPayList", map);
    }

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 失能年金給付
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Baappexpand getSurvivorReviewRpt01DisablePayList(String apNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("apNo", apNo);

        return (Baappexpand) getSqlMapClientTemplate().queryForObject("BAAPPEXPAND.selectSurvivorReviewRpt01DisablePayList", map);
    }

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 遺屬欄位資料 for 遺屬年金給付查詢
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return <code>Baappexpand</code> 物件
     */
    @DaoFieldList("APNO,SEQNO")
    public Baappexpand selectBenDataForSurvivorPaymentQuery(String apNo, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return (Baappexpand) getSqlMapClientTemplate().queryForObject("BAAPPEXPAND.selectBenDataForSurvivorPaymentQuery", map);
    }

    /**
     * 修改 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 失能年金編審註記程度調整
     * 
     * @param baappexpand 給付延伸主檔
     */
    public void updateAbleapsYmForDisabledCheckMarkLevelAdjust(Baappexpand baappexpand) {
        getSqlMapClientTemplate().update("BAAPPEXPAND.updateAbleapsYmForDisabledCheckMarkLevelAdjust", baappexpand);
    }

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 得請領起始年月 for 失能年金編審註記程度調整
     * 
     * @param apNo 受理編號
     * @param baappbaseId
     */
    public String getAbleapsYmForDisabledCheckMarkLevelAdjust(String apNo, BigDecimal baappbaseId) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("baappbaseId", baappbaseId);
        return (String) getSqlMapClientTemplate().queryForObject("BAAPPEXPAND.getAbleapsYmForDisabledCheckMarkLevelAdjust", map);
    }

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) COMNPMK
     * 
     * @param baappbaseId
     * @param apNo
     * @param seqNo
     */
    public String selectComnpmkBy(BigDecimal baappbaseId, String apNo, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("baappbaseId", baappbaseId);
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return (String) getSqlMapClientTemplate().queryForObject("BAAPPEXPAND.selectComnpmkBy", map);
    }
    
    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) MONNOTIFYING
     * 
     * @param baappbaseId
     * @param apNo
     * @param seqNo
     */
    public String selectMonnotifyingmkBy(BigDecimal baappbaseId, String apNo, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("baappbaseId", baappbaseId);
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return (String) getSqlMapClientTemplate().queryForObject("BAAPPEXPAND.selectMonnotifyingmkBy", map);
    }

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) NLWKMK and ADWKMK for 給付查詢
     * 
     * @param baappbaseId
     * @param apNo
     * @return
     */
    @DaoFieldList("BAAPPBASEID,APNO")
    public Baappexpand getBaappexpandDataForWkMkBy(BigDecimal baappbaseId, String apNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("baappbaseId", baappbaseId);
        map.put("apNo", apNo);

        return (Baappexpand) getSqlMapClientTemplate().queryForObject("BAAPPEXPAND.getBaappexpandDataForWkMkBy", map);

    }

    /**
     * 更新 CPI 基準年月 for 給付延伸主檔(<code>BAAPPEXPAND</code>)
     * 
     * @param apNo
     * @param updUser
     * @param updTime
     */
    public void updateCpiBaseYMForResetPaymentParameter(String apNo, String updUser, String updTime) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("updUser", updUser);
        map.put("updTime", updTime);

        getSqlMapClientTemplate().update("BAAPPEXPAND.updateCpiBaseYMForResetPaymentParameter", map);
    }

    /**
     * 更新 計算平均薪資申請年度 for 給付延伸主檔(<code>BAAPPEXPAND</code>)
     * 
     * @param apNo
     * @param updUser
     * @param updTime
     */
    public void updateInsAvgAmtAppYearForResetPaymentParameter(String apNo, String updUser, String updTime) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("updUser", updUser);
        map.put("updTime", updTime);

        getSqlMapClientTemplate().update("BAAPPEXPAND.updateInsAvgAmtAppYearForResetPaymentParameter", map);
    }
}
