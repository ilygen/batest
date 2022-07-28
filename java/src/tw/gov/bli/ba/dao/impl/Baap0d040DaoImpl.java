package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.Baap0d040Dao;
import tw.gov.bli.ba.domain.Baap0d040;
import tw.gov.bli.common.annotation.DaoTable;

@DaoTable("BAAP0D040")
public class Baap0d040DaoImpl extends SqlMapClientDaoSupport implements Baap0d040Dao {

	@Override
	public List<Baap0d040> selectTurnInDataFromBe(String apno) {
		Map<String, Object> map = new HashMap<>();
		map.put("apno", apno);

		return getSqlMapClientTemplate().queryForList("BAAP0D040.selectTurnInDataFromBe", map);
	}

	@Override
	public List<Baap0d040> selectTurnInDataFromBc(String apno) {
		Map<String, Object> map = new HashMap<>();
		map.put("apno", apno);

		return getSqlMapClientTemplate().queryForList("BAAP0D040.selectTurnInDataFromBc", map);
	}

	@Override
	public List<Baap0d040> selectTurnInDataFromBb(String apno) {
		Map<String, Object> map = new HashMap<>();
		map.put("apno", apno);

		return getSqlMapClientTemplate().queryForList("BAAP0D040.selectTurnInDataFromBb", map);
	}

	@Override
	public String selectBaapnok3() {
		return (String) getSqlMapClientTemplate().queryForObject("BAAP0D040.selectBaapnok3", null);
	}

}
