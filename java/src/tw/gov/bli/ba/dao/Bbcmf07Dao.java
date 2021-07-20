package tw.gov.bli.ba.dao;

import java.util.List;
import tw.gov.bli.ba.domain.Bbcmf07;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 醫療院所參數檔 (<code>BBCMF07</code>)
 * 
 * @author Evelyn Hsu
 */
public interface Bbcmf07Dao {

    /**
     * 依傳入的條件取得 醫療院所參數檔(<code>BBCMF07</code>) for 複檢費用審核給付清單
     * 
     * @param hpCode 醫院代碼
     * @return
     */
    public List<Bbcmf07> selecReviewFeeReceiptHosListBy(String hpCode);

    /**
     * 依傳入的條件取得 醫療院所參數檔(<code>BBCMF07</code>) for 失能年金案件資料更正
     * 
     * @param hpCode 醫院代碼
     * @return
     */
    public Bbcmf07 getDisabledApplicationDataUpdateHosDataBy(String hpCode);

    /**
     * 依傳入的條件取得 醫療院所參數檔(<code>BBCMF07</code>) for 複檢費用受理作業
     * 
     * @param hpCode 醫院代碼
     * @return
     */
    public Bbcmf07 getReviewFeeReceiptHosDataBy(String hpCode);

    /**
     * 依傳入的條件取得 醫療院所參數檔(<code>BBCMF07</code>) 醫院簡稱
     * 
     * @param hpCode 醫院代碼
     * @return
     */
    public String selectHpSnamBy(String hpCode);
    
    /**
     * 依傳入的條件取得 醫療院所參數檔(<code>BBCMF07</code>) for 查調病歷
     * 
     * @param hpCode 醫院代碼
     * @return
     */
    public Bbcmf07 getExecutiveSupportHosData(String hpCode);   

}
