package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;

import tw.gov.bli.ba.domain.Bccmf45;

/**
 * DAO for 利率檔 (<code>BCCMF45</code>)
 * 
 * @author Zehua
 */
public interface Bccmf45Dao {
	 public List<Bccmf45> getYearRateMapData();
}
