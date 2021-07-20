package tw.gov.bli.ba.dao;

import java.util.List;
import tw.gov.bli.ba.domain.Pfpcckyuserrec;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 退回現金業務單位使用紀錄檔 (<code>PFPCCKYUSERREC</code>)
 * 
 * @author Kiyomi
 */
public interface LocalPfpcckyuserrecDao {
    /**
     * 依傳入條件取得 退回現金業務單位使用紀錄檔(<code>PFPCCKYUSERREC</code>) EXPID
     * 
     * @param idn 身分證號
     * @return
     */
    public Integer selectExpid(String sInskd, String sBliAccountCode, String sBookedbook, String sBkaccountdt, Long lBatchno, Long lSerialno, Long lCashAmt, Long lDivseq, Long lIndexNo);
}
