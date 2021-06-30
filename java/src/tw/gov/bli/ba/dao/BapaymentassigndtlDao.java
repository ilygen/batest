package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;

import tw.gov.bli.ba.domain.Bapaymentassigndtl;
import tw.gov.bli.ba.domain.Bapaymentdtl;
/**
 * DAO for 繳款單分配明細檔 (<code>BAPAYMENTASSIGNDTL</code>)
 * 
 * @author Zehua
 */
public interface BapaymentassigndtlDao {
	 public BigDecimal insertPaymentData(Bapaymentassigndtl paymentData);
	 /**
	   * 依傳入條件更新繳款單條碼資料清單
	   * 
	   * @param Bapaymentassigndtl
	   * @return int
	 */
	 public int updatePaymentBarcode(Bapaymentassigndtl paymentData);
	 
	 /**
	   * 依傳入條件取得繳款單本金分配明細資料清單
	   * 
	   * @param paymentNo 繳款單號
	   * @return 物件的 List
	 */
	 public List<Bapaymentassigndtl> getPaymentAssignDetailList(String paymentNo);
	 /**
	   * 依傳入條件 取得利息及執行費的分配明細資料清單
	   * 
	   * @param paymentNo 繳款單號
	   * @return 物件的 List
	 */
	 public List<Bapaymentassigndtl> getPaymentConfirmDetailList(String paymentNo);
	 /**
	   * 依傳入條件取得 繳款單分配明細 資料清單
	   * 
	   * @param paymentNo 繳款單號
	   * @return 物件的 List
	 */
	 public List<Bapaymentassigndtl> getPaymentConfirmDetail(String paymentNo);
	 public int deletePaymentData(String paymentNo);
	 /**
	   * 依傳入條件取得 繳款單補印明細清單
	   * 
	   * @param paymentNo 繳款單號
	   * @return 物件的 List
	  */	 
	 public List<Bapaymentassigndtl> getPaymentReprintList(String paymentNo);
	 /**
	   * 依傳入條件取得 繳款單最大期數
	   * 
	   * @param paymentNo 繳款單號
	   * @return 物件的 List
	  */	 
	 public int getMaxInterest(String paymentNo);
}
