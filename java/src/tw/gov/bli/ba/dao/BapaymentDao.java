package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;

import tw.gov.bli.ba.domain.Bapayment;
import tw.gov.bli.ba.domain.Bapaymentassigndtl;

public interface BapaymentDao {
	 /**
	   * 查詢IDN繳款單資料
	   * 
	   * @param paymentNo 繳款單號
	   * @return
	   */
	 public List<Bapayment> selectPaymentQueryData(String idn);
	 /**
	   * 繳款單開單序號
	   * 
	   * @param nowDate 今天日期
	   * @return
	   */
	 public int selectCountForPaymentToday(String nowDate);
	 public int selectPaymentCount(String paymentNo);
	 public BigDecimal insertPaymentData(Bapayment paymentData);
	 /**
	   * 取得繳款單主檔資料
	   * 
	   * @param paymentNo 繳款單號
	   * @return
	   */
	 public Bapayment getPaymentData(String paymentNo);
	 
	 public int updatePaymentData(Bapayment paymentData);
	 /**
	   * 更新繳款單列印日期
	   * 
	   * @param westDate 系統時間
	   * @param paymentNo 繳款單號
	   * @return
	   */
	 public int updatePrintDate(String westDate, String paymentNo);
	 
	 public int updateUsemkForBapayment(String paymentNo);
	 
	 public List<Bapayment> getPaymentQueryData(String idn, String paymentNo, String caseNo, String sequenceNo, String paymentDate);

}
