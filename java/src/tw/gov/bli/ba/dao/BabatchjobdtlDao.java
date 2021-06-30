package tw.gov.bli.ba.dao;

import java.util.List;
import tw.gov.bli.ba.domain.Babatchjobdtl;

/**
 * 查詢年金線上產製媒體排程-目前進度查詢(明細)
 * 
 * @param baJobId 資料列編號(JOBID)
 */
public interface BabatchjobdtlDao {
    public List<Babatchjobdtl> selectNowProgressStepsDtl(String baJobId);

    /**
     * 依傳入條件取得 勞保年金批次作業明細檔(<code>BABATCHJOBDTL</code>) 資料清單
     * 
     * @param baJobId 資料列編號(JOBID)
     * @return 內含 <code>Babatchjobdtl</code> 物件的 List
     */
    public List<Babatchjobdtl> selectBabatchjobdtlDataForBjMonthBatchBy(String baJobId);

    /**
     * 批次處理 - 批次程式執行作業 - 新增排程作業狀態
     * 
     * @param 內含 <code>Babatchjobdtl</code> 物件的 List
     */
    public void insertBaBatchJobDtl(Babatchjobdtl babatchjobdtl);

	public void inserData(Babatchjobdtl babatchjobdtl); 

}
