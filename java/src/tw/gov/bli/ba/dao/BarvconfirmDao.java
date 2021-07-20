package tw.gov.bli.ba.dao;

import java.util.List;

import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Barvconfirm;


/**
 * DAO for 月核案件檢核確認檔 (<code>BARVCONFIRM</code>)
 * 
 * @author Noctis
 */
public interface BarvconfirmDao {

    /**
     * 依傳入條件取得 月核案件檢核確認檔 (<code>BARVCONFIRM</code>) 資料清單
     */
    public List<Barvconfirm> selectBarvconfirmDataBy(String payCode, String issuYm);

    /**
     * 更新 月核案件檢核確認檔(<code>BARVCONFIRM</code>) 資料
     * 
     * @param barvconfirm 給付主檔
     */
    public void updateBarvconfirmData(Barvconfirm barvconfirm);
    
    /**
     * 依傳入條件取得 月核案件檢核確認檔(<code>BARVCONFIRM</code>) 資料清單
     */
    public String selectDataCountBy(String payCode, String issuYm);
    
}
