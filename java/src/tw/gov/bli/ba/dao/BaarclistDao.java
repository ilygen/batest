package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;

import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Baarclist;

/**
 * DAO for 案件歸檔紀錄檔 (<code>BAARCLIST</code>)
 * 
 * @author Jerry
 */
public interface BaarclistDao {

    /**
     * 更新 案件歸檔紀錄檔(<code>BAARCLIST</code>) for 給付決行
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForDecision(Baappbase baappbase);

    /**
     * 查詢 案件歸檔紀錄檔(<code>BAARCLIST</code>) 取得當日已歸檔的最大頁次及序號 for 列印歸檔清單
     * 
     * @param payCode 給付別
     * @param arcDate 歸檔日期
     */
    public Baarclist selectMaxBaarclistNumAndArcPg(String arcDate, String payCode);
    
    /**
     * 查詢 案件歸檔紀錄檔(<code>BAARCLIST</code>) 取得當日已歸檔的最大頁次及序號 for 列印歸檔清單 (payCode = S and CaseTyp = 3)
     * 
     * @param payCode 給付別
     * @param arcDate 歸檔日期
     */
    public Baarclist selectMaxBaarclistNumAndArcPgForS(String arcDate, String payCode);    

    /**
     * 新增 案件歸檔紀錄檔(<code>BAARCLIST</code>) 資料
     * 
     * @param BAARCLIST 案件歸檔紀錄檔
     * @return
     */
    public void insertData(final List<Baarclist> baarclistList);

    /**
     * 查詢 案件歸檔紀錄檔(<code>BAARCLIST</code>) for 列印歸檔清單補印
     * 
     * @param payCode 給付別
     * @param arcDate 歸檔日期
     * @param arcPgBegin 歸檔頁次起
     * @param arcPgEnd 歸檔頁次迄
     * @return
     */
    public List<Baarclist> selectDataForDecisionRpt02(String payCode, String arcDate, BigDecimal arcPgBegin, BigDecimal arcPgEnd, String exeMan);
    
    /**
     * 查詢 案件歸檔紀錄檔(<code>BAARCLIST</code>) for 歸檔清單點交清冊
     * 
     * @param payCode 給付別
     * @param arcDate 歸檔日期
     * @return
     */
    public List<Baarclist> selectDataForDecisionRpt03(String payCode, String arcDate);

    /**
     * 查詢 案件歸檔紀錄檔(<code>BAARCLIST</code>) 核定年月起迄 for 給付查詢-歸檔記錄
     * 
     * @param apNo 受理編號
     * @return
     */
    public Baarclist selectPaymentQueryIssuYm(String apNo);

    /**
     * 查詢 案件歸檔紀錄檔(<code>BAARCLIST</code>) 歸檔記錄明細資料 for 給付查詢-歸檔記錄
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<Baarclist> selectPaymentQueryData(String apNo);
}
