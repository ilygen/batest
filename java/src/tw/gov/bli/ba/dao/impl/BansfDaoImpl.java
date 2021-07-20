package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BansfDao;
import tw.gov.bli.ba.domain.Bansf;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 勞保年金統計檔 (<code>BANSF</code>)
 * 
 * @author Eddie
 */
@DaoTable("BANSF")
public class BansfDaoImpl extends SqlMapClientDaoSupport implements BansfDao {
	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 件數及金額 的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType1X(String payKind, String paymentYMStart, String paymentYMEnd) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(payKind))
			map.put("payKind", payKind);
		if (StringUtils.isNotBlank(paymentYMStart))
			map.put("paymentYMStart", paymentYMStart);
		if (StringUtils.isNotBlank(paymentYMEnd))
			map.put("paymentYMEnd", paymentYMEnd);

		return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.qryRptType1X", map);
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 件數及金額 的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType1X1(String payKind, String paymentYMStart, String paymentYMEnd) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(payKind))
			map.put("payKind", payKind);
		if (StringUtils.isNotBlank(paymentYMStart))
			map.put("paymentYMStart", paymentYMStart);
		if (StringUtils.isNotBlank(paymentYMEnd))
			map.put("paymentYMEnd", paymentYMEnd);

		return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.qryRptType1X1", map);
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 件數及金額 的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType1X2(String payKind, String paymentYMStart, String paymentYMEnd) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(payKind))
			map.put("payKind", payKind);
		if (StringUtils.isNotBlank(paymentYMStart))
			map.put("paymentYMStart", paymentYMStart);
		if (StringUtils.isNotBlank(paymentYMEnd))
			map.put("paymentYMEnd", paymentYMEnd);

		return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.qryRptType1X2", map);
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 性別 的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType1S(String payKind, String paymentYMStart, String paymentYMEnd) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(payKind))
			map.put("payKind", payKind);
		if (StringUtils.isNotBlank(paymentYMStart))
			map.put("paymentYMStart", paymentYMStart);
		if (StringUtils.isNotBlank(paymentYMEnd))
			map.put("paymentYMEnd", paymentYMEnd);

		return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.qryRptType1S", map);
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 單位類別 的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType1U(String payKind, String paymentYMStart, String paymentYMEnd) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(payKind))
			map.put("payKind", payKind);
		if (StringUtils.isNotBlank(paymentYMStart))
			map.put("paymentYMStart", paymentYMStart);
		if (StringUtils.isNotBlank(paymentYMEnd))
			map.put("paymentYMEnd", paymentYMEnd);

		return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.qryRptType1U", map);
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 國籍別 的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType1N(String payKind, String paymentYMStart, String paymentYMEnd) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(payKind))
			map.put("payKind", payKind);
		if (StringUtils.isNotBlank(paymentYMStart))
			map.put("paymentYMStart", paymentYMStart);
		if (StringUtils.isNotBlank(paymentYMEnd))
			map.put("paymentYMEnd", paymentYMEnd);

		return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.qryRptType1N", map);
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 國籍別 的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType1N1(String payKind, String paymentYMStart, String paymentYMEnd) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(payKind))
			map.put("payKind", payKind);
		if (StringUtils.isNotBlank(paymentYMStart))
			map.put("paymentYMStart", paymentYMStart);
		if (StringUtils.isNotBlank(paymentYMEnd))
			map.put("paymentYMEnd", paymentYMEnd);

		return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.qryRptType1N1", map);
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 外籍別 的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @param cipbFMk        外籍別(CIPB)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType1C(String payKind, String paymentYMStart, String paymentYMEnd) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(payKind))
			map.put("payKind", payKind);
		if (StringUtils.isNotBlank(paymentYMStart))
			map.put("paymentYMStart", paymentYMStart);
		if (StringUtils.isNotBlank(paymentYMEnd))
			map.put("paymentYMEnd", paymentYMEnd);

		return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.qryRptType1C", map);
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 外籍別 的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @param cipbFMk        外籍別(CIPB)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType1C1(String payKind, String paymentYMStart, String paymentYMEnd) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(payKind))
			map.put("payKind", payKind);
		if (StringUtils.isNotBlank(paymentYMStart))
			map.put("paymentYMStart", paymentYMStart);
		if (StringUtils.isNotBlank(paymentYMEnd))
			map.put("paymentYMEnd", paymentYMEnd);

		return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.qryRptType1C1", map);
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 傷病分類 的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType1E(String payKind, String paymentYMStart, String paymentYMEnd) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(payKind))
			map.put("payKind", payKind);
		if (StringUtils.isNotBlank(paymentYMStart))
			map.put("paymentYMStart", paymentYMStart);
		if (StringUtils.isNotBlank(paymentYMEnd))
			map.put("paymentYMEnd", paymentYMEnd);

		return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.qryRptType1E", map);
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 傷病分類 的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType1E1(String payKind, String paymentYMStart, String paymentYMEnd) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(payKind))
			map.put("payKind", payKind);
		if (StringUtils.isNotBlank(paymentYMStart))
			map.put("paymentYMStart", paymentYMStart);
		if (StringUtils.isNotBlank(paymentYMEnd))
			map.put("paymentYMEnd", paymentYMEnd);

		return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.qryRptType1E1", map);
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年度平均 的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType2X(String payKind, String paymentYMStart, String paymentYMEnd) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(payKind))
			map.put("payKind", payKind);
		if (StringUtils.isNotBlank(paymentYMStart))
			map.put("paymentYMStart", paymentYMStart);
		if (StringUtils.isNotBlank(paymentYMEnd))
			map.put("paymentYMEnd", paymentYMEnd);

		return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.qryRptType2X", map);
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年度平均 性別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType2S(String payKind, String paymentYMStart, String paymentYMEnd) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(payKind))
			map.put("payKind", payKind);
		if (StringUtils.isNotBlank(paymentYMStart))
			map.put("paymentYMStart", paymentYMStart);
		if (StringUtils.isNotBlank(paymentYMEnd))
			map.put("paymentYMEnd", paymentYMEnd);

		return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.qryRptType2S", map);
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年度平均 單位類別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType2U(String payKind, String paymentYMStart, String paymentYMEnd) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(payKind))
			map.put("payKind", payKind);
		if (StringUtils.isNotBlank(paymentYMStart))
			map.put("paymentYMStart", paymentYMStart);
		if (StringUtils.isNotBlank(paymentYMEnd))
			map.put("paymentYMEnd", paymentYMEnd);

		return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.qryRptType2U", map);
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年度平均 國籍別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType2N(String payKind, String paymentYMStart, String paymentYMEnd) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(payKind))
			map.put("payKind", payKind);
		if (StringUtils.isNotBlank(paymentYMStart))
			map.put("paymentYMStart", paymentYMStart);
		if (StringUtils.isNotBlank(paymentYMEnd))
			map.put("paymentYMEnd", paymentYMEnd);

		return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.qryRptType2N", map);
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年度平均 外籍別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType2C(String payKind, String paymentYMStart, String paymentYMEnd) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(payKind))
			map.put("payKind", payKind);
		if (StringUtils.isNotBlank(paymentYMStart))
			map.put("paymentYMStart", paymentYMStart);
		if (StringUtils.isNotBlank(paymentYMEnd))
			map.put("paymentYMEnd", paymentYMEnd);

		return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.qryRptType2C", map);
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年度平均 傷病分類的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType2E(String payKind, String paymentYMStart, String paymentYMEnd) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(payKind))
			map.put("payKind", payKind);
		if (StringUtils.isNotBlank(paymentYMStart))
			map.put("paymentYMStart", paymentYMStart);
		if (StringUtils.isNotBlank(paymentYMEnd))
			map.put("paymentYMEnd", paymentYMEnd);

		return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.qryRptType2E", map);
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年齡級距 分析選項 無的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType4XA(String payKind, String paymentYMStart, String paymentYMEnd) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(payKind))
			map.put("payKind", payKind);
		if (StringUtils.isNotBlank(paymentYMStart))
			map.put("paymentYMStart", paymentYMStart);
		if (StringUtils.isNotBlank(paymentYMEnd))
			map.put("paymentYMEnd", paymentYMEnd);

		return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.qryRptType4XA", map);
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 投保薪資級距 分析選項 無的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType4XW(String payKind, String paymentYMStart, String paymentYMEnd) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(payKind))
			map.put("payKind", payKind);
		if (StringUtils.isNotBlank(paymentYMStart))
			map.put("paymentYMStart", paymentYMStart);
		if (StringUtils.isNotBlank(paymentYMEnd))
			map.put("paymentYMEnd", paymentYMEnd);

		return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.qryRptType4XW", map);
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年齡級距 分析選項 性別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType4SA(String payKind, String paymentYMStart, String paymentYMEnd) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(payKind))
			map.put("payKind", payKind);
		if (StringUtils.isNotBlank(paymentYMStart))
			map.put("paymentYMStart", paymentYMStart);
		if (StringUtils.isNotBlank(paymentYMEnd))
			map.put("paymentYMEnd", paymentYMEnd);

		return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.qryRptType4SA", map);
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 投保薪資級距 分析選項 性別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType4SW(String payKind, String paymentYMStart, String paymentYMEnd) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(payKind))
			map.put("payKind", payKind);
		if (StringUtils.isNotBlank(paymentYMStart))
			map.put("paymentYMStart", paymentYMStart);
		if (StringUtils.isNotBlank(paymentYMEnd))
			map.put("paymentYMEnd", paymentYMEnd);

		return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.qryRptType4SW", map);
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年齡級距 分析選項 單位類別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType4UA(String payKind, String paymentYMStart, String paymentYMEnd) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(payKind))
			map.put("payKind", payKind);
		if (StringUtils.isNotBlank(paymentYMStart))
			map.put("paymentYMStart", paymentYMStart);
		if (StringUtils.isNotBlank(paymentYMEnd))
			map.put("paymentYMEnd", paymentYMEnd);

		return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.qryRptType4UA", map);
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 投保薪資級距 分析選項 單位類別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType4UW(String payKind, String paymentYMStart, String paymentYMEnd) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(payKind))
			map.put("payKind", payKind);
		if (StringUtils.isNotBlank(paymentYMStart))
			map.put("paymentYMStart", paymentYMStart);
		if (StringUtils.isNotBlank(paymentYMEnd))
			map.put("paymentYMEnd", paymentYMEnd);

		return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.qryRptType4UW", map);
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年齡級距 分析選項 國籍別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType4NA(String payKind, String paymentYMStart, String paymentYMEnd) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(payKind))
			map.put("payKind", payKind);
		if (StringUtils.isNotBlank(paymentYMStart))
			map.put("paymentYMStart", paymentYMStart);
		if (StringUtils.isNotBlank(paymentYMEnd))
			map.put("paymentYMEnd", paymentYMEnd);

		return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.qryRptType4NA", map);
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 投保薪資級距 分析選項 國籍別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType4NW(String payKind, String paymentYMStart, String paymentYMEnd) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(payKind))
			map.put("payKind", payKind);
		if (StringUtils.isNotBlank(paymentYMStart))
			map.put("paymentYMStart", paymentYMStart);
		if (StringUtils.isNotBlank(paymentYMEnd))
			map.put("paymentYMEnd", paymentYMEnd);

		return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.qryRptType4NW", map);
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年齡級距 分析選項 外籍別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType4CA(String payKind, String paymentYMStart, String paymentYMEnd) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(payKind))
			map.put("payKind", payKind);
		if (StringUtils.isNotBlank(paymentYMStart))
			map.put("paymentYMStart", paymentYMStart);
		if (StringUtils.isNotBlank(paymentYMEnd))
			map.put("paymentYMEnd", paymentYMEnd);

		return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.qryRptType4CA", map);
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 投保薪資級距 分析選項 外籍別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType4CW(String payKind, String paymentYMStart, String paymentYMEnd) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(payKind))
			map.put("payKind", payKind);
		if (StringUtils.isNotBlank(paymentYMStart))
			map.put("paymentYMStart", paymentYMStart);
		if (StringUtils.isNotBlank(paymentYMEnd))
			map.put("paymentYMEnd", paymentYMEnd);

		return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.qryRptType4CW", map);
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年齡級距 分析選項 傷病分類的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType4EA(String payKind, String paymentYMStart, String paymentYMEnd) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(payKind))
			map.put("payKind", payKind);
		if (StringUtils.isNotBlank(paymentYMStart))
			map.put("paymentYMStart", paymentYMStart);
		if (StringUtils.isNotBlank(paymentYMEnd))
			map.put("paymentYMEnd", paymentYMEnd);

		return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.qryRptType4EA", map);
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 投保薪資級距 分析選項 傷病分類的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType4EW(String payKind, String paymentYMStart, String paymentYMEnd) {
		HashMap<String, String> map = new HashMap<String, String>();

		if (StringUtils.isNotBlank(payKind))
			map.put("payKind", payKind);
		if (StringUtils.isNotBlank(paymentYMStart))
			map.put("paymentYMStart", paymentYMStart);
		if (StringUtils.isNotBlank(paymentYMEnd))
			map.put("paymentYMEnd", paymentYMEnd);

		return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.qryRptType4EW", map);
	}

    /**
     * 金額級距、展減級距、平均薪資級距及年資級距
     * @param payCode 給付別
     * @param paymentYmStart 西元 核付日期(起)
     * @param paymentYmEnd   西元 核付日期(迄)
     * @param qrytype 統計項目 C,D,E,F
     * @param spcStart	級距區間起
     * @param spcEnd	級距區間迄
     * @param analysisSelect 分析選項
     * @return
     */
    public Bansf queryReport3DataXData(String payCode, String paymentYmStart, String paymentYmEnd, String qrytype, String spcStart, String spcEnd
    		,String totSpcStart,String  totSpcEnd ) {
    	 HashMap<String, String> map = new HashMap<String, String>();
         map.put("payKind", payCode);
         map.put("paymentYmStart", paymentYmStart);
         map.put("paymentYmEnd", paymentYmEnd);
         map.put("qrytype", qrytype);
         map.put("paramStart", spcStart);
         map.put("paramEnd", spcEnd);
         map.put("totParamStart", totSpcStart);
         map.put("totParamEnd", totSpcEnd);
         return (Bansf) getSqlMapClientTemplate().queryForObject("BANSF.queryReport3DataXData", map);
    }

    /**
     * 金額級距、展減級距、平均薪資級距及年資級距
     * @param payCode 給付別
     * @param paymentYmStart 西元 核付日期(起)
     * @param paymentYmEnd   西元 核付日期(迄)
     * @param qrytype 統計項目 C,D,E,F
     * @param spcStart	級距區間起
     * @param spcEnd	級距區間迄
     * @param analysisSelect 分析選項
     * @return
     */
	public List<Bansf> queryReport3DataSData(String payCode, String paymentYmStart, String paymentYmEnd, String qrytype,
			String spcStart, String spcEnd, String totSpcStart, String totSpcEnd) {
		HashMap<String, String> map = new HashMap<String, String>();
        map.put("payKind", payCode);
        map.put("paymentYmStart", paymentYmStart);
        map.put("paymentYmEnd", paymentYmEnd);
        map.put("qrytype", qrytype);
        map.put("paramStart", spcStart);
        map.put("paramEnd", spcEnd);
        map.put("totParamStart", totSpcStart);
        map.put("totParamEnd", totSpcEnd);
        return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.queryReport3DataSData", map);
	}
	
	/**
     * 金額級距、展減級距、平均薪資級距及年資級距
     * @param payCode 給付別
     * @param paymentYmStart 西元 核付日期(起)
     * @param paymentYmEnd   西元 核付日期(迄)
     * @param qrytype 統計項目 C,D,E,F
     * @param spcStart	級距區間起
     * @param spcEnd	級距區間迄
     * @param analysisSelect 分析選項
     * @param ubType 單位類別
     * @return
     */
    public List<Bansf> queryReport3DataUData(String payCode, String paymentYmStart, String paymentYmEnd, String qrytype, String spcStart, String spcEnd, String[] ubType
    		, String totSpcStart, String totSpcEnd){
    	HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("payKind", payCode);
        map.put("paymentYmStart", paymentYmStart);
        map.put("paymentYmEnd", paymentYmEnd);
        map.put("qrytype", qrytype);
        map.put("paramStart", spcStart);
        map.put("paramEnd", spcEnd);
        map.put("ubType", ubType);
        map.put("totParamStart", totSpcStart);
        map.put("totParamEnd", totSpcEnd);
        return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.queryReport3DataUData", map);
    }
    
    /**
     * 金額級距、展減級距、平均薪資級距及年資級距
     * @param payCode 給付別
     * @param paymentYmStart 西元 核付日期(起)
     * @param paymentYmEnd   西元 核付日期(迄)
     * @param qrytype 統計項目 C,D,E,F
     * @param spcStart	級距區間起
     * @param spcEnd	級距區間迄
     * @param analysisSelect 分析選項
     * @return
     */
	public List<Bansf> queryReport3DataNData(String payCode, String paymentYmStart, String paymentYmEnd, String qrytype,
			String spcStart, String spcEnd, String totSpcStart, String totSpcEnd) {
		HashMap<String, String> map = new HashMap<String, String>();
        map.put("payKind", payCode);
        map.put("paymentYmStart", paymentYmStart);
        map.put("paymentYmEnd", paymentYmEnd);
        map.put("qrytype", qrytype);
        map.put("paramStart", spcStart);
        map.put("paramEnd", spcEnd);
        map.put("totParamStart", totSpcStart);
        map.put("totParamEnd", totSpcEnd);
        return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.queryReport3DataNData", map);
	}
	
	/**
     * 金額級距、展減級距、平均薪資級距及年資級距
     * @param payCode 給付別
     * @param paymentYmStart 西元 核付日期(起)
     * @param paymentYmEnd   西元 核付日期(迄)
     * @param qrytype 統計項目 C,D,E,F
     * @param spcStart	級距區間起
     * @param spcEnd	級距區間迄
     * @param analysisSelect 分析選項
     * @param cipbFmk 外籍別
     * @return
     */
    public List<Bansf> queryReport3DataCData(String payCode, String paymentYmStart, String paymentYmEnd, String qrytype, String spcStart, String spcEnd, String[] cipbFmk
    		, String totSpcStart, String totSpcEnd){
    	HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("payKind", payCode);
        map.put("paymentYmStart", paymentYmStart);
        map.put("paymentYmEnd", paymentYmEnd);
        map.put("qrytype", qrytype);
        map.put("paramStart", spcStart);
        map.put("paramEnd", spcEnd);
        map.put("cipbFmk", cipbFmk);
        map.put("totParamStart", totSpcStart);
        map.put("totParamEnd", totSpcEnd);
        return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.queryReport3DataCData", map);
    }
    /**
     * 金額級距、展減級距、平均薪資級距及年資級距
     * @param payCode 給付別
     * @param paymentYmStart 西元 核付日期(起)
     * @param paymentYmEnd   西元 核付日期(迄)
     * @param qrytype 統計項目 C,D,E,F
     * @param spcStart	級距區間起
     * @param spcEnd	級距區間迄
     * @param analysisSelect 分析選項
     * @return
     */
    public List<Bansf> queryReport3DataEData(String payCode, String paymentYmStart, String paymentYmEnd, String qrytype, String spcStart, String spcEnd
    		, String totSpcStart, String totSpcEnd){
    	HashMap<String, String> map = new HashMap<String, String>();
        map.put("payKind", payCode);
        map.put("paymentYmStart", paymentYmStart);
        map.put("paymentYmEnd", paymentYmEnd);
        map.put("qrytype", qrytype);
        map.put("paramStart", spcStart);
        map.put("paramEnd", spcEnd);
        map.put("totParamStart", totSpcStart);
        map.put("totParamEnd", totSpcEnd);
        
        return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.queryReport3DataEData", map);
    }
	/**
     * 依傳入條件取得(<code>BANSF</code>) 勞保年金統計檔詳細資料
     * 
     * @param issuYm
     * @return <code>Bansf</code> 物件
     */
	public List<Bansf> selectStatisticsReportData(String issuYm) {
		HashMap<String, String> map = new HashMap<String, String>();
        map.put("issuYm", issuYm);
        return (List<Bansf>) getSqlMapClientTemplate().queryForList("BANSF.selectStatisticsReportData", map);
		
	}
}
