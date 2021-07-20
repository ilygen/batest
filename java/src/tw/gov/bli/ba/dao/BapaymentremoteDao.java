package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;

import tw.gov.bli.ba.domain.Bapaymentremote;
import tw.gov.bli.ba.payment.cases.PaymentWebserviceProcessCase;

public interface BapaymentremoteDao {
	/**
	   * 寫入PF系統傳入之狀態
	   * 
	   * @param Bapaymentremote
	   * @return
	   */
	 public BigDecimal insertPaymentWebservice(Bapaymentremote processData);
	 /**
	   * 更新PF系統回寫的繳款單狀態
	   * 
	   * @param Bapaymentremote
	   * @return
	   */
	 public BigDecimal updatePaymentWebservice(Bapaymentremote remoteData);
}
