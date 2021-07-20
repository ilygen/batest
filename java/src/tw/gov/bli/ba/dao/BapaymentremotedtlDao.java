package tw.gov.bli.ba.dao;

import java.math.BigDecimal;

import tw.gov.bli.ba.domain.Bapaymentremotedtl;

public interface BapaymentremotedtlDao {
	 /**
	   * 寫入處理pf系統傳入狀態後的資料
	   * 
	   * @param Bapaymentremotedtl
	   * @return
	   */
	 public BigDecimal insertPaymentWebservice(Bapaymentremotedtl processData);

}
