package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import tw.gov.bli.ba.bj.cases.RunProcedureCase;


/**
 * DAO for RUNPROCEDUREJOB (<code>RUNPROCEDUREJOB</code>)
 * 
 * @author KIYOMI
 */
public interface RunprocedurejobDao {
    
    /**
     * 呼叫 procedure
     * 
     * @param caseData 
     * @return
     */
    public Map<String,String> callProcedure(List<RunProcedureCase> caseData);

   
}
