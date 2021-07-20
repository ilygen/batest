package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BapaymentDao;
import tw.gov.bli.ba.dao.BapaymentremotedtlDao;
import tw.gov.bli.ba.domain.Bapayment;
import tw.gov.bli.ba.domain.Bapaymentremotedtl;

public class BapaymentremotedtlDaoImpl extends SqlMapClientDaoSupport implements BapaymentremotedtlDao{
	/**
	   * 寫入處理pf系統傳入狀態後的資料
	   * 
	   * @param Bapaymentremotedtl
	   * @return
	   */ 
	public BigDecimal insertPaymentWebservice(Bapaymentremotedtl dtlCase){
		 return (BigDecimal) getSqlMapClientTemplate().insert("BAPAYMENTREMOTEDTL.insertPaymentData", dtlCase);
	 }
}
