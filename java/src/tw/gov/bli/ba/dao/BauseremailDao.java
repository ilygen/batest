package tw.gov.bli.ba.dao;

import java.util.List;

import tw.gov.bli.ba.domain.Bauseremail;

/**
 * DAO for 承辦人電子郵件檔 (<code>BAUSEREMAIL</code>)
 * 
 * @author Noctis
 */
public interface BauseremailDao {

    /**
     * 依傳入條件取得 承辦人電子郵件檔(<code>BAUSEREMAIL</code>) 資料清單
     */
    public List<Bauseremail> selectBauseremailDataListBy();
    public List<Bauseremail> selectBaMediaUserEmailDataListBy(String payCode);

}
