package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;

import tw.gov.bli.ba.domain.Bapaymentstagedtl;

/**
 * DAO for 繳款單分期明細檔(<code>BAPAYMENTSTAGEDTL</code>)
 * 
 * @author Zehua
 */
public interface BapaymentstagedtlDao {
	 
	 public BigDecimal insertPaymentData(Bapaymentstagedtl pd);
	 /**
	   * 取得繳款單利息資料
	   * 
	   * @param paymentNo 繳款單號
	   * @return
	   */
	 public List<Bapaymentstagedtl> getPaymentStageDetailList(String paymentNo);
	  
	 public int deletePaymentData(String paymentNo);

}