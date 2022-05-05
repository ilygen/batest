package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;
import tw.gov.bli.ba.domain.Bxadmins;
import tw.gov.bli.ba.executive.cases.ExecutiveSupportDataCase;
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
    @DaoFieldList("APNO,ISSUYM")
    public Bxadmins selectSurvivorReviewRpt01AnnuityPayDataBy(String apNo, String issuYm);

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) 年金給付資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public Bxadmins getDisableReviewRpt01AnnuityPayDataBy(String apNo, String issuYm);
}
