package tw.gov.bli.ba.dao;

import tw.gov.bli.ba.domain.Bxadmins;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 行政支援記錄檔 (<code>MAADMERC</code>)
 * 
 * @author swim
 */
public interface BxadminsDao {

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) 年金給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO")
    public Bxadmins selectSurvivorReviewRpt01AnnuityPayDataBy(String apNo);
}
