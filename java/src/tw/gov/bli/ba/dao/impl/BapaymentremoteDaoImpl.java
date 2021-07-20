package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BapaymentremoteDao;
import tw.gov.bli.ba.domain.Bapaymentremote;
import tw.gov.bli.ba.payment.cases.PaymentWebserviceProcessCase;

public class BapaymentremoteDaoImpl extends SqlMapClientDaoSupport implements BapaymentremoteDao{
	/**
	   * 寫入PF系統傳入之狀態
	   * 
	   * @param Bapaymentremote
	   * @return
	   */
	public BigDecimal insertPaymentWebservice(Bapaymentremote processData) {
		 return (BigDecimal) getSqlMapClientTemplate().insert("BAPAYMENTREMOTE.insertPaymentWebservice", processData);
	}
	/**
	   * 更新PF系統回寫的繳款單狀態
	   * 
	   * @param Bapaymentremote
	   * @return
	   */
	public BigDecimal updatePaymentWebservice(Bapaymentremote remoteData) {
		 return (BigDecimal) getSqlMapClientTemplate().insert("BAPAYMENTREMOTE.updatePaymentWebservice", remoteData);
	}

}
