package tw.gov.bli.ba.dao;

import tw.gov.bli.ba.domain.Bbpmak;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 給付延伸主檔 (<code>BBPMAK</code>)
 * 
 * @author 
 */

public interface BbpmakDao {

	/**
	 * 依傳入條件取得 給付延伸主檔(<code>BBPMAK</code>) 資料 for 失能年金給付
	 * 
	 * @param apNo 受理編號
	 * @return
	 */
	Bbpmak selectDisasterReviewRpt01DisablePayList(String apNo);

	/**
	 * 依傳入條件取得 給付延伸主檔(<code>BBPMAK</code>) 資料 for 失能年金給付
	 * 
	 * @param apNo 受理編號
	 * @return
	 */
	Bbpmak selectDisasterReviewRpt01AnnuityPayList(String apNo);

	/**
	 * 依傳入條件取得 給付延伸主檔(<code>BBPMAK</code>) 資料 for 失能年金給付
	 * 
	 * @param apNo 受理編號
	 * @return
	 */
	Bbpmak selectDisasterReviewRpt01SurvivorPayList(String apNo);

}
