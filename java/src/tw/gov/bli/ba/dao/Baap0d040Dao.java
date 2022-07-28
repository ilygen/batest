package tw.gov.bli.ba.dao;

import java.util.List;

import tw.gov.bli.ba.domain.Baap0d040;

public interface Baap0d040Dao {

	/**
	 * 依 apno 取得 BE 系統的轉入資料
	 * 
	 * @param apno
	 * @return
	 */
	List<Baap0d040> selectTurnInDataFromBe(String apno);

	/**
	 * 依 apno 取得 BC 系統的轉入資料
	 * 
	 * @param apno
	 * @return
	 */
	List<Baap0d040> selectTurnInDataFromBc(String apno);

	/**
	 * 依 apno 取得 BB 系統的轉入資料
	 * 
	 * @param apno
	 * @return
	 */
	List<Baap0d040> selectTurnInDataFromBb(String apno);
	
	/**
	 * 從 BAS 的 SEQUENCE(BAAPNOK3) 取得受理編號
	 * @return
	 */
	String selectBaapnok3();

}
