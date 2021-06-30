package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;

import tw.gov.bli.ba.domain.Bapayrptsum;

/**
 * DAO for 核付報表總表紀錄檔 (<code>BAPAYRPTSUM</code>)
 * 
 * @author Kiyomi
 */
public interface BapayrptsumDao {

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTSUM</code>) 資料 for 月核定撥付總表（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param benEvtRel 關係
     * @param eqType SQL EqualType
     * @return
     */
    public List<Bapayrptsum> selectMonthlyRpt04Report(String payCode, String issuYm, String[] benEvtRel, String eqType, String chkDate);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTSUM</code>) 資料 for 月核定撥付總表（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param benEvtRel 關係
     * @param eqType SQL EqualType
     * @return
     */
    public List<Bapayrptsum> selectMonthlyRptK04Report(String payCode, String issuYm, String[] benEvtRel, String eqType, String chkDate, String paySeqNo, String isNullOrNot, String eqOrNot);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTSUM</code>) 資料 for 月核定撥付總表（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param benEvtRel 關係
     * @param eqType SQL EqualType
     * @return
     */
    public List<Bapayrptsum> selectMonthlyRptS04Report(String payCode, String issuYm, String[] benEvtRel, String eqType, String chkDate, String isNullOrNot, String eqOrNot);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTSUM</code>) 資料 for 退匯核定清單
     * 
     * @return
     */
    public List<Bapayrptsum> selectMonthlyRpt11Report(String payCode, String issuYm, String chkDate);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTSUM</code>) 資料 for 改匯核定清單
     * 
     * @return
     */
    public List<Bapayrptsum> selectMonthlyRpt13Report(String payCode, String issuYm, String chkDate);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTSUM</code>) 資料 for 應收款立帳核定清單
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptsum> selectMonthlyRpt20DataBy(String payCode, String issuYm, String chkDate, String paySeqNo, String isNull, String isEqual);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTSUM</code>) 資料 for 退匯應收已收核定清單
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptsum> selectMonthlyRpt19DataBy(String payCode, String issuYm, String chkDate, String paySeqNo, String isNullOrNot, String eqOrNot);

    /**
     * 依傳入條件取得 給付核定檔(<code>Bapayrptsum</code>) 資料 for 退匯/現應收已收核定清單
     * 
     * @param rptTyp
     * @param apNo 受理編號
     * @param chkDate 已收日期/註銷已收日期
     * @param apSeqNo 受款人序
     * @return
     */
    public List<Bapayrptsum> selectMonthlyRpt25DataBy(String rptTyp, String apNo, String chkDate, String seqNo, String paySeqNo);

    /**
     * 依傳入條件取得 給付核定檔(<code>Bapayrptsum</code>) 資料 for 退匯/現應收已收註銷核定清單
     * 
     * @param rptTyp
     * @param apNo 受理編號
     * @param chkDate 已收日期/註銷已收日期
     * @param apSeqNo 受款人序
     * @return
     */
    public List<Bapayrptsum> selectMonthlyRpt27DataBy(String rptTyp, String apNo, String chkDate, String seqNo, String paySeqNo);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTSUM</code>) 資料 for 保留遺屬年金計息存儲核定清單
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptsum> selectMonthlyRpt32DataBy(String payCode, String issuYm, String chkDate, String paySeqNo, String isNullOrNot, String eqOrNot);

    /**
     * 依傳入條件取得 轉催收核定清單資料
     * 
     * @param rptTyp
     * @param apNo 受理編號
     * @param chkDate 已收日期/註銷已收日期
     * @param apSeqNo 受款人序
     * @return
     */
    public List<Bapayrptsum> selectOtherRpt06DataBy(String payCode, String procYm, String paySeqNo, String isNull, String isEqual, String nowDate);

    public List<Bapayrptsum> selectOtherRpt08DataBy(String payCode, String apNo, String deadYy, String paySeqNo, String isNull, String isEqual, String nowDate);

    public String execSumRpt16Type(String payCode, String procYm, String paySeqNo, String cprnDt);

    /**
     * 依傳入的條件取得轉催收核定清單 的資料- 補印
     * 
     * @param payCode 給付別
     * @param demDate 轉催收日期
     * @return
     */
    public List<Bapayrptsum> selectOtherRpt06CompRptBy(String payCode, String demDate, String isNull, String isEqual);

    /**
     * 依傳入的條件取得轉呆帳核定清單 的資料- 補印
     * 
     * @param payCode 給付別
     * @param demDate 轉催收日期
     * @return
     */
    public List<Bapayrptsum> selectOtherRpt08CompRptBy(String payCode, String bDebtDate, String isNull, String isEqual);

    /**
     * 新增(<code>BAPAYRPTSUM</code>) 資料 for 整批收回核定清單
     * 
     * @param bapayrptsum
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForBatchPaymentReceiveData(Bapayrptsum bapayrptsum);

    /**
     * 新增(<code>BAPAYRPTSUM</code>) 資料 for 整批收回核定清單
     * 
     * @param bapayrptsum
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForPaymentReceiveData(Bapayrptsum bapayrptsum);

    /**
     * 新增(<code>BAPAYRPTSUM</code>) 資料 for （失能）整批收回核定清單
     * 
     * @param bapayrptsum
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertKDataForBatchPaymentReceiveData(Bapayrptsum bapayrptsum);

    /**
     * 新增(<code>BAPAYRPTSUM</code>) 資料 for （遺屬）回核定清單
     * 
     * @param bapayrptsum
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertSDataForBatchPaymentReceiveData(Bapayrptsum bapayrptsum);

    /**
     * 依傳入的條件取得轉呆帳核定清單 的資料- 補印
     * 
     * @param payCode 給付別
     * @param chkDate
     * @return
     */
    public List<Bapayrptsum> selectBapayrptsumDataForRptaccountBy(String payCode, String chkDate);

    /**
     * 依傳入的條件取得轉呆帳核定清單 的資料- 補印
     * 
     * @param payCode 給付別
     * @param chkDate
     * @return
     */
    public List<Bapayrptsum> selectBapayrptsumDataToRptaccountForPaymentReceiveBy(String payCode, String apNo);

    /**
     * 依傳入條件取得 核付報表總表紀錄檔(<code>BAPAYRPTSUM</code>) 資料 for 整批收回應收已收清冊
     * 
     * @param rptTyp
     * @param payCode 受理編號
     * @param chkDate 已收日期/註銷已收日期
     * @param cPrnDate
     * @return
     */
    public List<Bapayrptsum> getBatchPaymentReceiveDataBy(String rptTyp, String payCode, String chkDate, String cPrnDate, String isNullOrNot, String eqOrNot);

    /**
     * 依傳入的條件取得轉呆帳核定清單 的資料- 補印（遺屬）
     * 
     * @param payCode 給付別
     * @param chkDate
     * @return
     */
    public List<Bapayrptsum> selectSBapayrptsumDataForRptaccountBy(String payCode, String chkDate);

    /**
     * 更新 退現資料檔(<code>BAPAYRPTSUM</code>) 資料
     * 
     * @param baacpdtl 退現資料檔
     */
    public void updateBapayrptsumForPfmPfd(int v_cprnpage, int v_lcnt, String issuTyp, String issuYm, String chkDate, String payCode);

    /**
     * 更新 退現資料檔(<code>BAPAYRPTSUM</code>) 資料
     * 
     * @param baacpdtl 退現資料檔
     */
    public void updateBapayrptsumForPfmPfdK(int v_cprnpage, int v_lcnt, String issuTyp, String issuYm, String chkDate, String payCode, String nlWkMk, String adWkMk);

    /**
     * 更新 退現資料檔(<code>BAPAYRPTSUM</code>) 資料
     * 
     * @param baacpdtl 退現資料檔
     */
    public void updateBapayrptsumForPfmPfdS(int v_cprnpage, int v_lcnt, String issuTyp, String issuYm, String chkDate, String payCode, String nlWkMk, String adWkMk);

    /**
     * 更新 退現資料檔(<code>BAPAYRPTSUM</code>) 資料
     * 
     * @param baacpdtl 退現資料檔
     */
    public void updateTempMk(String payCode, String apNo);

}
