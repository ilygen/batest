package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;

import tw.gov.bli.ba.domain.Bapayrptrecord;

/**
 * DAO for 核付後報表清單紀錄檔 (<code>BAPAYRPTRECORD</code>)
 * 
 * @author Rickychi
 */
public interface BapayrptrecordDao {
    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 給付抵銷紓困貸款明細（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRpt09DataBy(String payCode, String issuYm, String chkDate);

    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 給付抵銷紓困貸款明細（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRptK09DataBy(String payCode, String issuYm, String chkDate, String paySeqNo);

    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 給付抵銷紓困貸款明細（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRptS09DataBy(String payCode, String issuYm, String chkDate);

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 核付清單（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRpt10RptType1DataBy(String payCode, String issuYm, String chkDate, String[] benEvtRel, String eqType, String[] orderBy);

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 核付清單（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRptK10RptType1DataBy(String payCode, String issuYm, String chkDate, String paySeqNo);

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 核付清單（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRptS10RptType1DataBy(String payCode, String issuYm, String chkDate);

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 核付清單(郵政匯票)（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRpt10RptType3DataBy(String payCode, String issuYm, String chkDate, String[] benEvtRel, String eqType, String[] orderBy);

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 核付清單(郵政匯票)（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRptK10RptType3DataBy(String payCode, String issuYm, String chkDate, String paySeqNo);

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 核付清單(郵政匯票)（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRptS10RptType3DataBy(String payCode, String issuYm, String chkDate);

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 核付清單
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRpt10RptType1PayAmtDataBy(String payCode, String issuYm, String chkDate);

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 核付明細表（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param chkDate
     * @param benEvtRel
     * @param eqType
     * @param orderBy 排序方式
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRpt10RptType2DataBy(String payCode, String issuYm, String chkDate, String[] benEvtRel, String eqType, String[] orderBy);

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 核付明細表（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRptK10RptType2DataBy(String payCode, String issuYm, String chkDate, String paySeqNo);

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 核付明細表（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRptS10RptType2DataBy(String payCode, String issuYm, String chkDate);

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 核付清單
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRpt10RptType2PayAmtDataBy(String payCode, String issuYm, String chkDate);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 月核定撥付總表（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param benEvtRel 關係
     * @param eqType SQL EqualType
     * @return
     */
    public List<Bapayrptrecord> selectMonthlyRpt04Report(String payCode, String issuYm, String[] benEvtRel, String eqType, String chkDate);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 月核定撥付總表（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param benEvtRel 關係
     * @param eqType SQL EqualType
     * @return
     */
    public List<Bapayrptrecord> selectMonthlyRptK04Report(String payCode, String issuYm, String[] benEvtRel, String eqType, String chkDate);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 月核定撥付總表（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param benEvtRel 關係
     * @param eqType SQL EqualType
     * @return
     */
    public List<Bapayrptrecord> selectMonthlyRptS04Report(String payCode, String issuYm, String[] benEvtRel, String eqType, String chkDate);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 月核定撥付總表 代扣補償金資料
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param payTyp 給付方式
     * @param benEvtRel 關係
     * @return
     */
    public List<Bapayrptrecord> selectMonthlyRpt04ReportCompenAmtDetail(String payCode, String issuYm, String payTyp, String benEvtRel);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 退匯核定清單
     * 
     * @return
     */
    public List<Bapayrptrecord> selectMonthlyRpt11Report(String payCode, String issuYm, String chkDate);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 改匯核定清單
     * 
     * @return
     */
    public List<Bapayrptrecord> selectMonthlyRpt13Report(String payCode, String issuYm, String chkDate);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 月核定合格清冊（老年）
     * 
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRpt08DataBy(String apNo, String issuYm, String chkDate);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 月核定合格清冊（失能）
     * 
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRptK08DataBy(String apNo, String issuYm, String chkDate, String paySeqNo, String isNullOrNot, String eqOrNot);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 月核定合格清冊（遺屬）
     * 
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRptS08DataBy(String apNo, String issuYm, String chkDate, String isNullOrNot, String eqOrNot);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 退匯清冊
     * 
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRpt12DataBy(String apNo, String issuYm, String chkDate);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 改匯清冊
     * 
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRpt14DataBy(String apNo, String issuYm, String chkDate);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 退匯應收已收清冊
     * 
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRpt15DataBy(String apNo, String issuYm, String chkDate, String paySeqNo, String isNullOrNot, String eqOrNot);

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 退回現金清冊（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRpt17DataBy(String payCode, String issuYm, String chkDate);

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 退回現金清冊（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRptK17DataBy(String payCode, String issuYm, String chkDate, String paySeqNo);

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 退回現金清冊（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRptS17DataBy(String payCode, String issuYm, String chkDate);

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 退回現金轉暫收及待結轉清單（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRpt18DataBy(String payCode, String issuYm, String chkDate);

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 退回現金轉暫收及待結轉清單（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRptK18DataBy(String payCode, String issuYm, String chkDate, String paySeqNo);

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 退回現金轉暫收及待結轉清單（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRptS18DataBy(String payCode, String issuYm, String chkDate);

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 退匯應收已收核定清單
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptrecord> selectMonthlyRpt19DataBy(String payCode, String issuYm, String chkDate);

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 應收款立帳核定清單（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptrecord> selectMonthlyRpt20DataBy(String payCode, String issuYm, String chkDate);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 應收款立帳清冊
     * 
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRpt21DataBy(String apNo, String issuYm, String chkDate, String paySeqNo, String isNull, String isEqual);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 月處理核定報表彙整
     * 
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRpt22DataBy(String apNo, String issuYm, String chkDate);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 月處理核定報表彙整
     * 
     * @return
     */
    public Integer getMonthlyRpt22DataByPaytyp(String apNo, String issuYm, String chkDate, String eqType);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 郵政匯票通知／入戶匯款證明（老年）
     * 
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRpt23DataBy(String apNo, String issuYm, String chkDate);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 郵政匯票通知／入戶匯款證明（失能）
     * 
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRptK23DataBy(String apNo, String issuYm, String chkDate, String paySeqNo);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 郵政匯票通知／入戶匯款證明（遺屬）
     * 
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRptS23DataBy(String apNo, String issuYm, String chkDate);

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 核付明細表
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRpt24DataBy(String payCode, String issuYm, String chkDate);

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 核付清單
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRpt24PayAmtDataBy(String payCode, String issuYm, String chkDate);

    /**
     * 依傳入條件取得 給付核定檔(<code>BAPAYRPTRECORD</code>) 資料 for 退匯/現應收已收清冊
     * 
     * @param rptTyp
     * @param apNo 受理編號
     * @param chkDate 已收日期/註銷已收日期
     * @param apSeqNo 受款人序
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRpt26DataBy(String rptTyp, String apNo, String chkDate, String seqNo, String paySeqNo);

    /**
     * 依傳入條件取得 給付核定檔(<code>BAPAYRPTRECORD</code>) 資料 for 退匯/現應收已收註銷清冊
     * 
     * @param rptTyp
     * @param apNo 受理編號
     * @param chkDate 已收日期/註銷已收日期
     * @param apSeqNo 受款人序
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRpt28DataBy(String rptTyp, String apNo, String chkDate, String seqNo, String paySeqNo);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 保留遺屬年金計息存儲清冊
     * 
     * @param apNo 受理編號
     * @param issuYm
     * @param chkDate
     * @param paySeqNo
     * @param isNullOrNot
     * @param eqOrNot
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRpt33DataBy(String apNo, String issuYm, String chkDate, String paySeqNo, String isNullOrNot, String eqOrNot);

    /**
     * 依傳入的條件取得 轉催收核定清冊 的資料
     * 
     * @return
     */
    public List<Bapayrptrecord> getOtherRpt07DataBy(String payCode, String procYm, String paySeqNo, String isNull, String isEqual);

    /**
     * 依傳入的條件取得 轉催收核定清冊 的資料-補印
     * 
     * @return
     */
    public List<Bapayrptrecord> getOtherRptComp07DataBy(String payCode, String demDate, String isNull, String isEqual);

    /**
     * 依傳入的條件取得 轉呆帳核定清冊 的資料
     * 
     * @return
     */
    public List<Bapayrptrecord> getOtherRpt09DataBy(String payCode, String apNo, String procYm, String paySeqNo, String isNull, String isEqual);

    /**
     * 依傳入的條件取得 轉呆帳核定清冊 的資料-補印
     * 
     * @return
     */
    public List<Bapayrptrecord> getOtherRptComp09DataBy(String payCode, String bDebtDate, String isNull, String isEqual);

    public void updateOtherRpt09DataBy(String payCode, String apNo, String deadYy, String paySeqNo, String isNull, String isEqual);

    public void updateOtherRpt07DataBy(String payCode, String procYm, String paySeqNo, String isNull, String isEqual);

    /**
     * 新增(<code>BAPAYRPTRECORD</code>) 資料 for 整批收回核定清單
     * 
     * @param bapayrptrecord
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForBatchPaymentReceiveData(Bapayrptrecord bapayrptrecord);

    /**
     * 新增(<code>BAPAYRPTRECORD</code>) 資料 for 更正作業 - 應收收回處理作業 - 老年年金應收收回處理
     * 
     * @param bapayrptrecord
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForPaymentReceiveData(Bapayrptrecord bapayrptrecord);

    /**
     * 新增(<code>BAPAYRPTRECORD</code>) 資料 for （失能）整批收回核定清單
     * 
     * @param bapayrptrecord
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertKDataForBatchPaymentReceiveData(Bapayrptrecord bapayrptrecord);

    /**
     * 新增(<code>BAPAYRPTRECORD</code>) 資料 for （遺屬）整批收回核定清單
     * 
     * @param bapayrptrecord
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertSDataForBatchPaymentReceiveData(Bapayrptrecord bapayrptrecord);

    /**
     * 依傳入的條件取得 整批收回應收已收清冊 的資料
     * 
     * @param payCode 給付別
     * @param chkDate
     * @return
     */
    public List<Bapayrptrecord> selectBapayrptrecordDataForRptsumBy(String payCode, String chkDate);

    /**
     * 依傳入的條件取得 整批收回應收已收清冊 的資料
     * 
     * @param payCode 給付別
     * @param chkDate
     * @return
     */
    public List<Bapayrptrecord> selectBapayrptrecordDataToRptsumForPaymentReceiveBy(String payCode, String sApNo);

    /**
     * 依傳入的條件取得 （失能）整批收回應收已收清冊 的資料
     * 
     * @param payCode 給付別
     * @param chkDate
     * @return
     */
    public List<Bapayrptrecord> selectKBapayrptrecordDataForRptsumBy(String payCode, String chkDate);

    /**
     * 依傳入的條件取得 （遺屬）整批收回應收已收清冊 的資料
     * 
     * @param payCode 給付別
     * @param chkDate
     * @return
     */
    public List<Bapayrptrecord> selectSBapayrptrecordDataForRptsumBy(String payCode, String chkDate);

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 整批收回應收已收清冊
     * 
     * @param rptTyp
     * @param payCode 受理編號
     * @param chkDate 已收日期/註銷已收日期
     * @param cPrnDate
     * @return
     */
    public List<Bapayrptrecord> getBatchPaymentReceiveDataBy(String rptTyp, String payCode, String chkDate, String cPrnDate, String isNullOrNot, String eqOrNot);

    /**
     * 依傳入的條件取得轉呆帳核定清單 的資料- 補印（失能）
     * 
     * @param payCode 給付別
     * @param chkDate
     * @return
     */
    public List<Bapayrptrecord> selectKBapayrptrecordDataForRptaccountBy(String payCode, String chkDate);

    /**
     * 更新 退現資料檔(<code>BAPAYRPTRECORD</code>) 資料
     * 
     * @param baacpdtl 退現資料檔
     */
    public void updateBapayrptrecordForPfmPfd(int v_cprnpage, int v_lcnt, String issuTyp, String apNo, String seqNo, String issuYm, String payYm, String chkDate);

    /**
     * 更新 退現資料檔(<code>BAPAYRPTRECORD</code>) 資料
     * 
     * @param baacpdtl 退現資料檔
     */
    public void updateBapayrptrecordForPfmPfdK(int v_cprnpage, int v_lcnt, String issuTyp, String apNo, String seqNo, String issuYm, String payYm, String chkDate, String nlWkMk, String adWkMk);

    /**
     * 更新 退現資料檔(<code>BAPAYRPTRECORD</code>) 資料
     * 
     * @param baacpdtl 退現資料檔
     */
    public void updateBapayrptrecordForPfmPfdS(int v_cprnpage, int v_lcnt, String issuTyp, String apNo, String seqNo, String issuYm, String payYm, String chkDate, String nlWkMk, String adWkMk);

    /**
     * 更新 退現資料檔(<code>BAPAYRPTRECORD</code>) 資料
     * 
     * @param baacpdtl 退現資料檔
     */
    public void updateTempMk(String payCode, String apNo);

}
