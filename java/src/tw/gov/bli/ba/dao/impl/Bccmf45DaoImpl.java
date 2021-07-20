package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.Bccmf42Dao;
import tw.gov.bli.ba.dao.Bccmf45Dao;
import tw.gov.bli.ba.domain.Bccmf45;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 利率檔 (<code>BCCMF45</code>)
 * 
 * @author Zehua
 */
@DaoTable("BCCMF45")
public class Bccmf45DaoImpl extends SqlMapClientDaoSupport implements Bccmf45Dao{

	public List<Bccmf45> getYearRateMapData() {
		 return (List<Bccmf45>) getSqlMapClientTemplate().queryForList("BCCMF45.selectYearRateMapData");
	}

}
