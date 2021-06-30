package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;

import tw.gov.bli.ba.domain.Bapaymentassigndtl;
import tw.gov.bli.ba.domain.Bapaymentdtl;
import tw.gov.bli.ba.payment.cases.PaymentProcessCase;
import tw.gov.bli.ba.payment.cases.PaymentDetailCase;
/**
 * DAO for 繳款單明細檔(<code>BAPAYMENTDTLDAO</code>)
 * 
 * @author Zehua
 */
public interface BapaymentdtlDao {
	 
	 public BigDecimal insertPaymentData(Bapaymentdtl paymentData);
	 /**
	   * 依傳入條件取得 應收未收資料(繳款單明細檔)
	   * 
	   * @param 繳款單號
	   * @return List
	  */
	 public List<Bapaymentdtl> getPaymentProcessDetailList(String paymentNo);
	 
	 public int deletePaymentData(String paymentNo);
	 /**
	   * 依傳入條件取得 繳款單列印清單
	   * 
	   * @param 繳款單號
	   * @return List
	  */
	 public List<Bapaymentdtl> getPaymentReprintList(String idn, String paymentNo, String caseNo, String prtDt, String sequenceNo, String paymentDate);
	 /**
	   * 依傳入條件取得 應收未收檔 資料清單
	   * 
	   * @param 繳款單號
	   * @return List
	  */
	 public List<Bapaymentdtl> getUnacpdtlData(String paymentNo);
	 public List<Bapaymentdtl> getPaymentQueryDetailList(String paymentNo);
}