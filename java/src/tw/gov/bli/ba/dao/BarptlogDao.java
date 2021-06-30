package tw.gov.bli.ba.dao;

import java.util.List;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt08Case;

public interface BarptlogDao {
    public void insertData(final List<MonthlyRpt08Case> list);
}
