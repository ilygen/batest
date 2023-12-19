package tw.gov.bli.ba.dao;

import java.util.List;

import tw.gov.bli.ba.domain.Baap0d060;
import tw.gov.bli.ba.receipt.cases.SurvivorAnnuityReceiptBenCase;

public interface Baap0d060Dao {

	/**
	 * 依 apno 取得 BE 系統死亡一次金轉入的案件資料
	 * 
	 * @param apno
	 * @return
	 */
	List<Baap0d060> selectSurvivorTurnInDataFromBe(String apno);

	/**
	 * 依 apno 取得 BE 系統死亡一次金轉入的遺屬資料
	 * 
	 * @param apno
	 * @return
	 */
	List<SurvivorAnnuityReceiptBenCase> selectSurvivorTurnInBenFromBe(String apno);

	/**
	 * 依 apno 取得 BC 系統死亡一次金轉入的案件資料
	 * 
	 * @param apno
	 * @return
	 */
	List<Baap0d060> selectSurvivorTurnInDataFromBc(String apno);

	/**
	 * 依 apno 取得 BC 系統死亡一次金轉入的遺屬資料
	 * 
	 * @param apno
	 * @return
	 */
	List<SurvivorAnnuityReceiptBenCase> selectSurvivorTurnInBenFromBc(String apno);

	/**
	 * 依 apno 取得 BB 系統遺屬年金轉入的案件資料
	 * 
	 * @param apno
	 * @return
	 */
	List<Baap0d060> selectSurvivorTurnInDataFromBb(String apno);

	/**
	 * 依 apno 取得 BB 系統遺屬年金轉入的遺屬資料
	 * 
	 * @param apno
	 * @return
	 */
	List<SurvivorAnnuityReceiptBenCase> selectSurvivorTurnInBenFromBb(String apno);

    /**
     * 依傳入條件取得 現金給付主檔(<code>BCPMA</code>) for 受理作業
     * 
     * @param idn 身分證號
     * @param apNo BA受理編號
     * @return <code>List<String></code>
     */
    public List<String> selectSurvivorBcApno(String idn, String apNo);

}
