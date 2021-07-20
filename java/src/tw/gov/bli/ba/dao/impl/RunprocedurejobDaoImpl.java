package tw.gov.bli.ba.dao.impl;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.owasp.encoder.Encode;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapSession;

import tw.gov.bli.ba.bj.cases.RunProcedureCase;
import tw.gov.bli.ba.dao.RunprocedurejobDao;
import tw.gov.bli.ba.util.ExceptionUtility;

/**
 * DAO for RUNPROCEDUREJOB (<code>RUNPROCEDUREJOB</code>)
 * 
 * @author KIYOMI
 */

public class RunprocedurejobDaoImpl extends SqlMapClientDaoSupport implements RunprocedurejobDao {

    private static Log log = LogFactory.getLog(RunprocedurejobDaoImpl.class);

    /**
     * 呼叫 procedure
     * 
     * @param caseData
     * @return
     */
    public Map<String, String> callProcedure(List<RunProcedureCase> caseData) {
        CallableStatement cst = null;
        SqlMapClient sqlMapClient = getSqlMapClientTemplate().getSqlMapClient();
        SqlMapSession seesion = sqlMapClient.openSession();

        String sProcedureName = "";
        String sParams = "";
        String sProcedure = "";
        try {

            for (int i = 0, len = caseData.size(); i < len; i++) {
                RunProcedureCase runProcedureCase = (RunProcedureCase) caseData.get(i);

                if (i == 0) {
                    sProcedureName = runProcedureCase.getprocedureName();
                }

                if (i == caseData.size() - 1) {
                    sParams += "?";
                }
                else {
                    sParams += "?,";
                }
            }

            seesion.startTransaction();

            cst = seesion.getCurrentConnection().prepareCall("{ call " + Encode.forJava(sProcedureName) + " (" + Encode.forJava(sParams) + ")}");

            for (int i = 0, len = caseData.size(); i < len; i++) {
                RunProcedureCase runProcedureCase = (RunProcedureCase) caseData.get(i);

                if (runProcedureCase.getInOut().equals("IN")) {
                    cst.setString((i + 1), runProcedureCase.getParamValue());
                }
                else {
                    int types = 0;

                    if (runProcedureCase.getdataType().equals("VARCHAR2") || runProcedureCase.getdataType().equals("VARCHAR"))
                        types = Types.VARCHAR;
                    else if (runProcedureCase.getdataType().equals("NUMBER"))
                        types = Types.NUMERIC;

                    cst.registerOutParameter((i + 1), types);
                }
            }

            cst.execute();

            HashMap<String, String> map = new HashMap<String, String>(); // 20171206
            for (int i = 0, len = caseData.size(); i < len; i++) {
                RunProcedureCase runProcedureCase = (RunProcedureCase) caseData.get(i);
                if (runProcedureCase.getInOut().equals("OUT")) {
                    map.put(runProcedureCase.getParameterName(), cst.getString((i + 1))); // 20171206
                }
            }

            seesion.endTransaction();
            return map;
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            log.error(ExceptionUtility.getStackTrace(e));

        }
        finally {
            seesion.close();
        }
        return null;
    }

}
