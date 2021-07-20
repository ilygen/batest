package tw.gov.bli.ba.dao;

import java.util.List;

import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Barvcase;
import tw.gov.bli.ba.domain.Barvconfirm;


/**
 * DAO for 月核案件檢核檔 (<code>BARVCASE</code>)
 * 
 * @author Noctis
 */
public interface BarvcaseDao {

    /**
     * 依傳入條件取得 月核案件檢核檔 (<code>BARVCASE</code>) 資料清單
     */
    public List<Barvcase> selectBarvcaseDataBy(String payCode, String issuYm, String rvType);

}
