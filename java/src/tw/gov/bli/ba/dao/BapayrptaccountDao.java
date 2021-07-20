package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;

import tw.gov.bli.ba.domain.Bapayrptaccount;

/**
 * DAO for 核付報表總表會計科目紀錄檔 (<code>BAPAYRPTACCOUNT</code>)
 * 
 * @author Kiyomi
 */
public interface BapayrptaccountDao {

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 月核定撥付總表（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param benEvtRel 關係
     * @param eqType SQL EqualType
     * @return
     */
    public List<Bapayrptaccount> selectMonthlyRpt04Report(String payCode, String issuYm, String[] benEvtRel, String eqType, String chkDate);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 月核定撥付總表（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param benEvtRel 關係
     * @param eqType SQL EqualType
     * @return
     */
    public List<Bapayrptaccount> selectMonthlyRptK04Report(String payCode, String issuYm, String[] benEvtRel, String eqType, String chkDate, String paySeqNo, String isNullOrNot, String eqOrNot);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 月核定撥付總表（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param benEvtRel 關係
     * @param eqType SQL EqualType
     * @return
     */
    public List<Bapayrptaccount> selectMonthlyRptS04Report(String payCode, String issuYm, String[] benEvtRel, String eqType, String chkDate, String isNullOrNot, String eqOrNot);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 退匯核定清單
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptaccount> selectMonthlyRpt11Report(String payCode, String issuYm, String chkDate);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 改匯核定清單
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptaccount> selectMonthlyRpt13Report(String payCode, String issuYm, String chkDate);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 退匯應收已收核定清單
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptaccount> selectMonthlyRpt19Report(String payCode, String issuYm, String chkDate, String paySeqNo, String isNullOrNot, String eqOrNot);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 應收款立帳核定清單
     * 
     * @param payTyp 給付別
     * @param issuYm 核定年月
     * @param chkDate 核定日期
     * @return
     */
    public List<Bapayrptaccount> selectMonthlyRpt20Report(String payCode, String issuYm, String chkDate, String paySeqNo, String isNull, String isEqual);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 退匯/現應收已收核定清單
     * 
     * @param rptTyp
     * @param apNo 受理編號
     * @param chkDate 已收日期/註銷已收日期
     * @param apSeqNo 受款人序
     * @return
     */
    public List<Bapayrptaccount> selectMonthlyRpt25Report(String rptTyp, String apNo, String chkDate, String seqNo, String paySeqNo);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 退匯/現應收已收註銷核定清單
     * 
     * @param rptTyp
     * @param apNo 受理編號
     * @param chkDate 已收日期/註銷已收日期
     * @param apSeqNo 受款人序
     * @return
     */
    public List<Bapayrptaccount> selectMonthlyRpt27Report(String rptTyp, String apNo, String chkDate, String seqNo, String paySeqNo);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 保留遺屬年金計息存儲核定清單
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptaccount> selectMonthlyRpt32Report(String payCode, String issuYm, String chkDate, String paySeqNo, String isNullOrNot, String eqOrNot);

    public List<Bapayrptaccount> selectOtherRpt06DataBy(String payCode, String deadYy, String paySeqNo, String isNull, String isEqual, String nowDate);

    public List<Bapayrptaccount> selectOtherRpt08DataBy(String payCode, String apNo, String deadYy, String paySeqNo, String isNull, String isEqual, String nowDate);

    public List<Bapayrptaccount> selectOtherRpt09DataBy(String payCode, String apNo, String deadYy, String paySeqNo, String isNull, String isEqual);

    public String execAccountRpt16Type(String payCode, String procYm, String paySeqNo, String cprnDt);

    /**
     * 依傳入的條件取得轉催收核定清單 的資料- 補印
     * 
     * @param payCode 給付別
     * @param demDate 轉催收日期
     * @return
     */
    public List<Bapayrptaccount> selectOtherRpt06CompRptBy(String payCode, String demDate, String isNull, String isEqual);

    /**
     * 依傳入的條件取得轉呆帳核定清單 的資料- 補印
     * 
     * @param payCode 給付別
     * @param bDebtDate 轉催收日期
     * @return
     */
    public List<Bapayrptaccount> selectOtherRpt08CompRptBy(String payCode, String bDebtDate, String isNull, String isEqual);

    /**
     * 新增(<code>BAPAYRPTACCOUNT</code>) 資料 for 整批收回核定清單
     * 
     * @param bapayrptaccount
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForBatchPaymentReceiveData(Bapayrptaccount bapayrptaccount);

    /**
     * 新增(<code>BAPAYRPTACCOUNT</code>) 資料 for 整批收回核定清單
     * 
     * @param bapayrptaccount
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForPaymentReceiveData(Bapayrptaccount bapayrptaccount);

    /**
     * 新增(<code>BAPAYRPTACCOUNT</code>) 資料 for （失能）整批收回核定清單
     * 
     * @param bapayrptaccount
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertKDataForBatchPaymentReceiveData(Bapayrptaccount bapayrptaccount);

    /**
     * 新增(<code>BAPAYRPTACCOUNT</code>) 資料 for （遺屬）整批收回核定清單
     * 
     * @param bapayrptaccount
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertSDataForBatchPaymentReceiveData(Bapayrptaccount bapayrptaccount);

    /**
     * 依傳入條件取得 核付報表總表會計科目紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 整批收回應收已收清冊
     * 
     * @param rptTyp
     * @param payCode 受理編號
     * @param chkDate 已收日期/註銷已收日期
     * @param cPrnDate
     * @return
     */
    public List<Bapayrptaccount> getBatchPaymentReceiveDataBy(String rptTyp, String payCode, String chkDate, String cPrnDate, String isNullOrNot, String eqOrNot);

    /**
     * 依傳入條件取得 核付報表總表會計科目紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 整批收回應收已收清冊
     * 
     * @param rptTyp
     * @param payCode 受理編號
     * @param chkDate 已收日期/註銷已收日期
     * @param cPrnDate
     * @return
     */
    public List<Bapayrptaccount> getRpttypeForPfmPfdResult(String payCode, String chkDate);

    /**
     * 依傳入條件取得 核付報表總表會計科目紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 整批收回應收已收清冊
     * 
     * @param rptTyp
     * @param payCode 受理編號
     * @param chkDate 已收日期/註銷已收日期
     * @param cPrnDate
     * @return
     */
    public List<Bapayrptaccount> getRpttypeForPaymentReceivePfmPfdResult(String payCode, String apNo);

    /**
     * 依傳入條件取得 核付報表總表會計科目紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 整批收回應收已收清冊 (K)
     * 
     * @param rptTyp
     * @param payCode 受理編號
     * @param chkDate 已收日期/註銷已收日期
     * @param cPrnDate
     * @return
     */
    public List<Bapayrptaccount> getRpttypeForPfmPfdKResult(String payCode, String chkDate);

    /**
     * 依傳入條件取得 核付報表總表會計科目紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 整批收回應收已收清冊 (S)
     * 
     * @param rptTyp
     * @param payCode 受理編號
     * @param chkDate 已收日期/註銷已收日期
     * @param cPrnDate
     * @return
     */
    public List<Bapayrptaccount> getRpttypeForPfmPfdSResult(String payCode, String chkDate);

    /**
     * 依傳入條件取得 核付報表總表會計科目紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 整批收回應收已收清冊
     * 
     * @param rptTyp
     * @param payCode 受理編號
     * @param chkDate 已收日期/註銷已收日期
     * @param cPrnDate
     * @return
     */
    public List<Bapayrptaccount> getDataForPfmPfdResult(String payCode, String chkDate, String issuTyp, String payTyp, String issuYm);

    /**
     * 依傳入條件取得 核付報表總表會計科目紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 整批收回應收已收清冊 (K)
     * 
     * @param rptTyp
     * @param payCode 受理編號
     * @param chkDate 已收日期/註銷已收日期
     * @param cPrnDate
     * @return
     */
    public List<Bapayrptaccount> getDataForPfmPfdKResult(String payCode, String chkDate, String issuTyp, String payTyp, String issuYm, String nlWkMk, String adWkMk);

    /**
     * 依傳入條件取得 核付報表總表會計科目紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 整批收回應收已收清冊 (S)
     * 
     * @param rptTyp
     * @param payCode 受理編號
     * @param chkDate 已收日期/註銷已收日期
     * @param cPrnDate
     * @return
     */
    public List<Bapayrptaccount> getDataForPfmPfdSResult(String payCode, String chkDate, String issuTyp, String payTyp, String issuYm, String nlWkMk, String adWkMk);

    /**
     * 更新 退現資料檔(<code>BAPAYRPTACCOUNT</code>) 資料
     * 
     * @param baacpdtl 退現資料檔
     */
    public void updateBapayrptaccountForPfmPfd(int v_cprnpage, int v_lcnt, String issuTyp, String issuYm, String chkDate, String payCode);

    /**
     * 更新 退現資料檔(<code>BAPAYRPTACCOUNT</code>) 資料
     * 
     * @param baacpdtl 退現資料檔
     */
    public void updateBapayrptaccountForPfmPfdK(int v_cprnpage, int v_lcnt, String issuTyp, String issuYm, String chkDate, String payCode, String nlWkMk, String adWkMk);

    /**
     * 更新 退現資料檔(<code>BAPAYRPTACCOUNT</code>) 資料
     * 
     * @param baacpdtl 退現資料檔
     */
    public void updateBapayrptaccountForPfmPfdS(int v_cprnpage, int v_lcnt, String issuTyp, String issuYm, String chkDate, String payCode, String nlWkMk, String adWkMk);

    /**
     * 更新 退現資料檔(<code>BAPAYRPTACCOUNT</code>) 資料
     * 
     * @param baacpdtl 退現資料檔
     */
    public void updateErptpageForPfmPfd(String v_i_cprndt, int v_cprnpage, int v_lcnt, String issuTyp, String chkDate, String payCode, String issuYm);

    /**
     * 更新 退現資料檔(<code>BAPAYRPTACCOUNT</code>) 資料
     * 
     * @param baacpdtl 退現資料檔
     */
    public void updateTempMk(String payCode, String apNo);

    /**
     * 更新 退現資料檔(<code>BAPAYRPTACCOUNT</code>) 資料
     * 
     * @param baacpdtl 退現資料檔
     */
    public void updateErptpageForPfmPfdK(String v_i_cprndt, int v_cprnpage, int v_lcnt, String issuTyp, String chkDate, String payCode, String issuYm, String nlWkMk, String adWkMk);

    /**
     * 更新 退現資料檔(<code>BAPAYRPTACCOUNT</code>) 資料
     * 
     * @param baacpdtl 退現資料檔
     */
    public void updateErptpageForPfmPfdS(String v_i_cprndt, int v_cprnpage, int v_lcnt, String issuTyp, String chkDate, String payCode, String issuYm, String nlWkMk, String adWkMk);

}
