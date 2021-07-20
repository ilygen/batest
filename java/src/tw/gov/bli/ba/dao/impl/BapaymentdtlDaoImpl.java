package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BapaymentdtlDao;
import tw.gov.bli.ba.domain.Bapayment;
import tw.gov.bli.ba.domain.Bapaymentassigndtl;
import tw.gov.bli.ba.domain.Bapaymentdtl;
import tw.gov.bli.ba.payment.cases.PaymentProcessCase;
import tw.gov.bli.ba.payment.cases.PaymentDetailCase;
import tw.gov.bli.common.annotation.DaoTable;

/**
* DAO for 繳款單明細檔 (<code>BAPAYMENTDTL</code>)
* 
*/
@DaoTable("BAPAYMENTDTL")
public class BapaymentdtlDaoImpl extends SqlMapClientDaoSupport implements BapaymentdtlDao{

	public BigDecimal insertPaymentData(Bapaymentdtl paymentData) {
		 return (BigDecimal) getSqlMapClientTemplate().insert("BAPAYMENTDTL.insertPaymentData", paymentData);
	}

	/**
	   * 依傳入條件取得 應收未收資料(繳款單明細檔)
	   * 
	   * @param 繳款單號
	   * @return List
	  */
	public List<Bapaymentdtl> getPaymentProcessDetailList(String paymentNo) {
		HashMap<String, String> map = new HashMap<String, String>();
	     if (StringUtils.isNotBlank(paymentNo)) {
	    	 map.put("paymentNo", paymentNo);
	     }
	     return (List<Bapaymentdtl>) getSqlMapClientTemplate().queryForList("BAPAYMENTDTL.seletPaymentProcessDetailList", map);
	}
	/**
	   * 依傳入條件取得 繳款單列印清單
	   * 
	   * @param 繳款單號
	   * @return List
	 */
	public List<Bapaymentdtl> getPaymentReprintList(String idn, String paymentNo, String caseNo, String prtDt, String sequenceNo, String paymentDate) {
		HashMap<String, String> map = new HashMap<String, String>();
	     if (StringUtils.isNotBlank(paymentNo)) {
	    	 map.put("paymentNo", paymentNo);
	     }
	     if(StringUtils.isNotBlank(idn)){
	    	 map.put("idn", idn);
	     }
	     if(StringUtils.isNotBlank(caseNo)){
	    	 map.put("caseNo", caseNo);
	     }
	     
	     if(StringUtils.isNotBlank(sequenceNo)){
	    	 map.put("sequenceNo", sequenceNo);
	     }
	     
	     if(StringUtils.isNotBlank(paymentDate)){
	    	 map.put("paymentDate", paymentDate);
	     }
	     
	     map.put("prtDt", prtDt);
	     
	     return (List<Bapaymentdtl>) getSqlMapClientTemplate().queryForList("BAPAYMENTDTL.seletPaymentReprintList", map);
	}
	public int deletePaymentData(String paymentNo) {
		 HashMap<String, String> map = new HashMap<String, String>();
	     if (StringUtils.isNotBlank(paymentNo)) {
	    	 map.put("paymentNo", paymentNo);
	     }
	     return (int) getSqlMapClientTemplate().delete("BAPAYMENTDTL.deletePaymentData", map);
	}

	/**
	   * 依傳入條件取得 應收未收檔 資料清單
	   * 
	   * @param 繳款單號
	   * @return List
	  */
	public List<Bapaymentdtl> getUnacpdtlData(String paymentNo) {
		HashMap<String, String> map = new HashMap<String, String>();
	    if (StringUtils.isNotBlank(paymentNo)) {
	    	map.put("paymentNo", paymentNo);
	    }
	    return (List<Bapaymentdtl>) getSqlMapClientTemplate().queryForList("BAPAYMENTDTL.selectUnacpdtlData", map);
	}

	/**
	   * 依傳入條件取得 應收未收資料(繳款單明細檔)
	   * 
	   * @param 繳款單號
	   * @return List
	  */
	public List<Bapaymentdtl> getPaymentQueryDetailList(String paymentNo) {
		HashMap<String, String> map = new HashMap<String, String>();
	     if (StringUtils.isNotBlank(paymentNo)) {
	    	 map.put("paymentNo", paymentNo);
	     }
	     return (List<Bapaymentdtl>) getSqlMapClientTemplate().queryForList("BAPAYMENTDTL.selectPaymentQueryDetailList", map);
	}


}
