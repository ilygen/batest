package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.Baap0d060Dao;
import tw.gov.bli.ba.domain.Baap0d060;
import tw.gov.bli.ba.receipt.cases.SurvivorAnnuityReceiptBenCase;
import tw.gov.bli.common.annotation.DaoTable;

@DaoTable("BAAP0D060")
public class Baap0d060DaoImpl extends SqlMapClientDaoSupport implements Baap0d060Dao {

	@Override
	public List<Baap0d060> selectSurvivorTurnInDataFromBe(String apno) {
		Map<String, Object> map = new HashMap<>();
		map.put("apno", apno);

		return getSqlMapClientTemplate().queryForList("BAAP0D060.selectSurvivorTurnInDataFromBe", map);
	}

	@Override
	public List<SurvivorAnnuityReceiptBenCase> selectSurvivorTurnInBenFromBe(String apno) {
		Map<String, Object> map = new HashMap<>();
		map.put("apno", apno);

		return getSqlMapClientTemplate().queryForList("BAAP0D060.selectSurvivorTurnInBenFromBe", map);
	}

	@Override
	public List<Baap0d060> selectSurvivorTurnInDataFromBc(String apno) {
		Map<String, Object> map = new HashMap<>();
		map.put("apno", apno);

		return getSqlMapClientTemplate().queryForList("BAAP0D060.selectSurvivorTurnInDataFromBc", map);
	}

	@Override
	public List<SurvivorAnnuityReceiptBenCase> selectSurvivorTurnInBenFromBc(String apno) {
		Map<String, Object> map = new HashMap<>();
		map.put("apno", apno);

		return getSqlMapClientTemplate().queryForList("BAAP0D060.selectSurvivorTurnInBenFromBc", map);
	}

	@Override
	public List<Baap0d060> selectSurvivorTurnInDataFromBb(String apno) {
		Map<String, Object> map = new HashMap<>();
		map.put("apno", apno);

		return getSqlMapClientTemplate().queryForList("BAAP0D060.selectSurvivorTurnInDataFromBb", map);
	}

	@Override
	public List<SurvivorAnnuityReceiptBenCase> selectSurvivorTurnInBenFromBb(String apno) {
		Map<String, Object> map = new HashMap<>();
		map.put("apno", apno);

		return getSqlMapClientTemplate().queryForList("BAAP0D060.selectSurvivorTurnInBenFromBb", map);
	}

	/**
	 * 提供BA執行自動受理516案
	 */
	@Override
	public Map<String, Object> callSpAcp516(String baapno, String casetyp, String kind) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("v_in_baapno", baapno);
		map.put("v_in_casetyp", casetyp);
		map.put("v_in_kind", kind);

		Integer count = (Integer) getSqlMapClientTemplate().queryForObject("BAAP0D060.sp_acp_516", map);

		return map;
	}

}
