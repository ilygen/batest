package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;

import tw.gov.bli.ba.domain.Balp0d020;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 審核給付清單紀錄檔 (<code>BALP0D020</code>)
 * 
 * @author Rickychi
 */
public interface Balp0d020Dao {

    /**
     * 依傳入條件取得 審核給付清單紀錄檔 (<code>BALP0D020</code>) 已執行"打包"列印最大的頁次
     * 
     * @param payCode 給付別
     * @param chkMan 審核人員
     * @param rptDate 報表日期
     * @return BigDecimal
     */
    public BigDecimal selectMaxPageNoBy(String payCode, String chkMan, String rptDate);

    /**
     * 依傳入條件取得 審核給付清單紀錄檔 (<code>BALP0D020</code>) 已執行"打包"列印最大的頁次
     * 
     * @param payCode 給付別
     * @param chkMan 審核人員
     * @param chkDate 審核日期
     * @return
     */
    public List<Balp0d020> selectNonPackDataBy(String payCode, String chkMan, String chkDate);

    /**
     * 依傳入條件取得 審核給付清單紀錄檔 (<code>BALP0D020</code>) 報表資料 For 審核給付清單
     * 
     * @param payCode 給付別
     * @param chkMan 審核人員
     * @param rptDate 報表日期
     * @param prtTyp 列印項目
     * @param spageNo 起始列印頁次
     * @param epageNo 結束列印頁次
     * @param pageNo 列印頁次
     * @return
     */
    public List<Balp0d020> selectOldAgeReviewRpt02DataBy(String payCode, String chkMan, String rptDate, String prtTyp, BigDecimal spageNo, BigDecimal epageNo, BigDecimal pageNo);

    /**
     * 依傳入條件取得 審核給付清單紀錄檔 (<code>BALP0D020</code>) 已執行"打包"列印最大的頁次
     * 
     * @param balp0d020 審核給付清單紀錄檔
     * @return
     */
    public void updateDataForOldAgeReviewRpt02(Balp0d020 balp0d020);

    /**
     * 依傳入條件取得 審核給付清單紀錄檔 (<code>BALP0D020</code>) 已執行"打包"列印最大的頁次
     * 
     * @param balp0d020 審核給付清單紀錄檔
     * @return
     */
    public void updateDataForOldAgeReviewRpt02(final List<Balp0d020> list);

    /**
     * 更新 審核給付清單紀錄檔 (<code>BALP0D020</code>) For 給付審核
     * 
     * @param balp0d020 審核給付清單紀錄檔
     * @return
     */
    public void updateDataForReview(Balp0d020 balp0d020);

    /**
     * 新增 審核給付清單紀錄檔 (<code>BALP0D020</code>) For 給付審核
     * 
     * @param balp0d020 審核給付清單紀錄檔
     * @return
     */
    public void insertDataForReview(Balp0d020 balp0d020);

    /**
     * 新增 審核給付清單紀錄檔 (<code>BALP0D020</code>) For 遺屬年金給付審核
     * 
     * @param balp0d020 審核給付清單紀錄檔
     * @return
     */
    public void insertDataForSurvivorPaymentReview(Balp0d020 balp0d020);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) for 給付決行
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForDecision(Balp0d020 balp0d020);

    /**
     * 更新 審核給付清單紀錄檔 (<code>BALP0D020</code>) for 審核給付清單
     */
    public void updateProcmkForOldAgeReviewRpt02();

    /**
     * 更新 審核給付清單紀錄檔 (<code>BALP0D020</code>)
     */
    public void updateProcMkByApNo(String apNo);
}
