package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BapaymentassigndtlDao;
import tw.gov.bli.ba.domain.Bapaymentassigndtl;
import tw.gov.bli.ba.domain.Bapaymentdtl;
import tw.gov.bli.ba.domain.Bapaymentstagedtl;
import tw.gov.bli.common.annotation.DaoTable;

/**
* DAO for 繳款單分配明細檔 (<code>BAPAYMENTASSIGNDTL</code>)
* 
*/
@DaoTable("BAPAYMENTASSIGNDTL")
public class BapaymentassigndtlDaoImpl extends SqlMapClientDaoSupport implements BapaymentassigndtlDao{

	public BigDecimal insertPaymentData(Bapaymentassigndtl paymentData) {
		 return (BigDecimal) getSqlMapClientTemplate().insert("BAPAYMENTASSIGNDTL.insertPaymentData", paymentData);
	}
	/**
	   * 依傳入條件取得繳款單本金分配明細資料清單
	   * 
	   * @param paymentNo 繳款單號
	   * @return 物件的 List
	 */
	public List<Bapaymentassigndtl> getPaymentAssignDetailList(String paymentNo) {
		HashMap<String, String> map = new HashMap<String, String>();
	    if (StringUtils.isNotBlank(paymentNo)) {
	    	map.put("paymentNo", paymentNo);
	    }
	    return (List<Bapaymentassigndtl>) getSqlMapClientTemplate().queryForList("BAPAYMENTASSIGNDTL.seletPaymentStageDetailList", map);
	}
	 /**
	   * 依傳入條件 取得利息及執行費的分配明細資料清單
	   * 
	   * @param paymentNo 繳款單號
	   * @return 物件的 List
	 */	
	public List<Bapaymentassigndtl> getPaymentConfirmDetailList(String paymentNo) {
		HashMap<String, String> map = new HashMap<String, String>();
	    if (StringUtils.isNotBlank(paymentNo)) {
	    	map.put("paymentNo", paymentNo);
	    }
	    return (List<Bapaymentassigndtl>) getSqlMapClientTemplate().queryForList("BAPAYMENTASSIGNDTL.seletPaymentDetailList", map);
	}
	/**
     * 依傳入條件取得 繳款單分配明細 資料清單
     * 
     * @param paymentNo 繳款單號
     * @return 物件的 List
     */
	public List<Bapaymentassigndtl> getPaymentConfirmDetail(String paymentNo) {
		HashMap<String, String> map = new HashMap<String, String>();
	    if (StringUtils.isNotBlank(paymentNo)) {
	    	map.put("paymentNo", paymentNo);
	    }
	    return (List<Bapaymentassigndtl>) getSqlMapClientTemplate().queryForList("BAPAYMENTASSIGNDTL.seletPaymentDetail", map);
	}
	
	public int deletePaymentData(String paymentNo) {
		HashMap<String, String> map = new HashMap<String, String>();
	    if (StringUtils.isNotBlank(paymentNo)) {
	    	map.put("paymentNo", paymentNo);
	    }
		return (int) getSqlMapClientTemplate().delete("BAPAYMENTASSIGNDTL.deletePaymentData", map);
	}
	
	/**
     * 依傳入條件取得 繳款單補印明細清單
     * 
     * @param paymentNo 繳款單號
     * @return 物件的 List
     */
	public List<Bapaymentassigndtl> getPaymentReprintList(String paymentNo) {
		HashMap<String, String> map = new HashMap<String, String>();
	    if (StringUtils.isNotBlank(paymentNo)) {
	    	map.put("paymentNo", paymentNo);
	    }
		return (List<Bapaymentassigndtl>) getSqlMapClientTemplate().queryForList("BAPAYMENTASSIGNDTL.selectPaymentReprintList", map);

	}
	/**
	   * 依傳入條件更新繳款單條碼資料清單
	   * 
	   * @param Bapaymentassigndtl
	   * @return int
	 */
	public int updatePaymentBarcode(Bapaymentassigndtl paymentData) {
		return (int) getSqlMapClientTemplate().update("BAPAYMENTASSIGNDTL.updatePaymentBarcode", paymentData);
	}
	/**
	   * 依傳入條件取得繳款單分期最大
	   * 
	   * @param paymentNo 繳款單號
	   * @return 物件的 List
	 */
	public int getMaxInterest(String paymentNo) {
		HashMap<String, String> map = new HashMap<String, String>();
	    if (StringUtils.isNotBlank(paymentNo)) {
	    	map.put("paymentNo", paymentNo);
	    }
		return (Integer) getSqlMapClientTemplate().queryForObject("BAPAYMENTASSIGNDTL.selectMaxInterest", paymentNo);
	}

	
}


