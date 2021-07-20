package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BapaymentDao;
import tw.gov.bli.ba.domain.Bapayment;
import tw.gov.bli.ba.domain.Bapaymentdtl;
import tw.gov.bli.common.annotation.DaoTable;

/**
* DAO for 繳款單檔 (<code>BAPAYMENT</code>)
* 
*/
@DaoTable("BAPAYMENT")
public class BapaymentDaoImpl extends SqlMapClientDaoSupport implements BapaymentDao{
	 /**
	   * 查詢IDN繳款單資料
	   * 
	   * @param paymentNo 繳款單號
	   * @return
	   */
	public List<Bapayment> selectPaymentQueryData(String idn) {
		 HashMap<String, String> map = new HashMap<String, String>();
	     if (StringUtils.isNotBlank(idn)) {
	    	 map.put("idn", idn);
	     }
	     return getSqlMapClientTemplate().queryForList("BAPAYMENT.selectPaymentQueryData", map);
	}
	 /**
	   * 繳款單開單序號
	   * 
	   * @param nowDate 今天日期
	   * @return
	   */
	public int selectCountForPaymentToday(String nowDate){
		 HashMap<String, String> map = new HashMap<String, String>();
	     if (StringUtils.isNotBlank(nowDate)) {
	    	 map.put("nowDate", nowDate);
	     }
	     return (Integer) getSqlMapClientTemplate().queryForObject("BAPAYMENT.selectCountForPaymentToday", map);
	}
	/**
	   * 繳款單資料
	   * 
	   * @param Bapayment paymentData
	   * @return
	   */
	public BigDecimal insertPaymentData(Bapayment paymentData) {
		 return (BigDecimal) getSqlMapClientTemplate().insert("BAPAYMENT.insertPaymentData", paymentData);
		
	}
	/**
	 * 取得繳款單主檔資料
	 * 
	 * @param paymentNo 繳款單號
	 * @return
	 */
	public Bapayment getPaymentData(String paymentNo) {
		 HashMap<String, String> map = new HashMap<String, String>();
	     if (StringUtils.isNotBlank(paymentNo)) {
	    	 map.put("paymentNo", paymentNo);
	     }
	     return (Bapayment) getSqlMapClientTemplate().queryForObject("BAPAYMENT.selectPaymentData", map);
	}
	
	public int updatePaymentData(Bapayment paymentData) {
		 return (int) getSqlMapClientTemplate().update("BAPAYMENT.updatePaymentData", paymentData);
	}
	/**
	   * 刪除繳款單
	   * 
	   * @param paymentNo 繳款單號
	   * @return
	   */
	public int updateUsemkForBapayment(String paymentNo){
		 HashMap<String, String> map = new HashMap<String, String>();
	     if (StringUtils.isNotBlank(paymentNo)) {
	    	 map.put("paymentNo", paymentNo);
	     }
		 return (int) getSqlMapClientTemplate().update("BAPAYMENT.updateUsemkForBapayment", map);
	}
	
	public List<Bapayment> getPaymentQueryData(String idn, String paymentNo, String caseNo, String sequenceNo, String paymentDate){
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
		return (List<Bapayment>) getSqlMapClientTemplate().queryForList("BAPAYMENT.selectPaymentProgressQueryData", map);
		
	}
	/**
	   * 更新繳款單列印日期
	   * 
	   * @param westDate 系統時間
	   * @param paymentNo 繳款單號
	   * @return
	   */
	public int updatePrintDate(String westDate, String paymentNo) {
		HashMap<String, String> map = new HashMap<String, String>();
		if (StringUtils.isNotBlank(paymentNo)) {
		 	 map.put("paymentNo", paymentNo);
		}
		if(StringUtils.isNotBlank(westDate)){
		   	 map.put("westDate", westDate);
		}
		 return (int) getSqlMapClientTemplate().update("BAPAYMENT.updatePrintDate", map);
	}
	
	public int selectPaymentCount(String paymentNo) {
		 HashMap<String, String> map = new HashMap<String, String>();
	     if (StringUtils.isNotBlank(paymentNo)) {
	    	 map.put("paymentNo", paymentNo);
	     }
	     return (Integer) getSqlMapClientTemplate().queryForObject("BAPAYMENT.selectPaymentCount", map);
	}
}
