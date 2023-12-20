package tw.gov.bli.ba.dao;

import java.util.List;
import java.util.Map;

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
	 * call BC.PKG_TRANCASE_BATOBC.sp_acp_516 提供BA執行自動受理516案
	 * 
	 * @param baapno  年金受理編號
	 * @param casetyp 0:死亡給付喪葬津貼 9:職災補償一次金 (9為預留，避免日後需求變更)
	 * @param kind    1:臨櫃鍵入   2:整批鍵入
	 * @return
	 */
	Map<String, Object> callSpAcp516(String baapno, String casetyp, String kind);

}
