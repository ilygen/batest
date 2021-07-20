package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BapaymentstagedtlDao;
import tw.gov.bli.ba.domain.Bapaymentdtl;
import tw.gov.bli.ba.domain.Bapaymentstagedtl;
import tw.gov.bli.common.annotation.DaoTable;

/**
* DAO for 繳款單利息明細檔 (<code>BAPAYMENTSTAGEDTL</code>)
* 
*/
@DaoTable("BAPAYMENTSTAGEDTL")
public class BapaymentstagedtlDaoImpl extends SqlMapClientDaoSupport implements BapaymentstagedtlDao{

	public BigDecimal insertPaymentData(Bapaymentstagedtl paymentData) {
		return (BigDecimal) getSqlMapClientTemplate().insert("BAPAYMENTSTAGEDTL.insertPaymentData", paymentData);
	}
	/**
	   * 取得繳款單利息資料
	   * 
	   * @param paymentNo 繳款單號
	   * @return
	   */
	public List<Bapaymentstagedtl> getPaymentStageDetailList(String paymentNo){
		HashMap<String, String> map = new HashMap<String, String>();
	    if (StringUtils.isNotBlank(paymentNo)) {
	    	map.put("paymentNo", paymentNo);
	    }
	    return (List<Bapaymentstagedtl>) getSqlMapClientTemplate().queryForList("BAPAYMENTSTAGEDTL.seletPaymentStageDetailList", map);
	}


	public int deletePaymentData(String paymentNo) {
		HashMap<String, String> map = new HashMap<String, String>();
	    if (StringUtils.isNotBlank(paymentNo)) {
	    	map.put("paymentNo", paymentNo);
	    }
	    return (int) getSqlMapClientTemplate().delete("BAPAYMENTSTAGEDTL.deletePaymentData", map);
	}
}
