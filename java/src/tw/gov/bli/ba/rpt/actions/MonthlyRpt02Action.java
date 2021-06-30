package tw.gov.bli.ba.rpt.actions;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.owasp.encoder.Encode;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt02Case;
import tw.gov.bli.ba.rpt.forms.MonthlyRpt02Form;
import tw.gov.bli.ba.rpt.report.MonthlyRpt02ExcelReport;
import tw.gov.bli.ba.rpt.report.MonthlyRpt02Report;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 列印作業 - 月核定處理相關報表 - 月編審異動清單 - 查詢頁面 (balp0d320l.jsp)
 * 
 * @author swim
 */
public class MonthlyRpt02Action extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(MonthlyRpt02Action.class);

    private RptService rptService;

    /**
     * 列印作業 - 月核定處理相關報表 - 月編審異動清單 - 查詢頁面 (balp0d320l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 月核定處理相關報表 - 月編審異動清單 - 查詢頁面 MonthlyRpt02Action.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        MonthlyRpt02Form queryForm = (MonthlyRpt02Form) form;

        try {
            String payCode = queryForm.getPayCode();
            String issuYm = queryForm.getIssuYm();
            String mtestMk = queryForm.getMtestMk();

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("payCode", payCode);
            map.put("issuYm", DateUtility.changeChineseYearMonthType(issuYm));
            map.put("mtestMk", mtestMk);

            // 抓報表資料
            List<MonthlyRpt02Case> caseData = rptService.getMonthlyRpt02DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), mtestMk);

            if (caseData.size() == 0) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            else {

                if (payCode.equals("L")) {
                    // 產生EXCEL報表
                    ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();
                    MonthlyRpt02ExcelReport report = new MonthlyRpt02ExcelReport();
                    baoOutput = report.doReport(userData, caseData, map);

                    String printDate = DateUtility.getNowChineseDate();

                    // 設定回傳回 Client 端之檔案大小, 若不設定,
                    // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                    response.setHeader("Content-disposition", "attachment; filename=\"" + Encode.forJava(payCode) + new String("_MonthlyRpt02_".getBytes("Big5"), "ISO8859_1") + Encode.forJava(printDate) + ".xls" + "\"");
                    response.setContentType("application/vnd.ms-excel"); // 設定MIME
                    response.setHeader("Pargma", "no-cache");
                    response.setHeader("Cache-Control", "max-age=1");
                    response.setHeader("Expires", "0");
                    response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                    response.setHeader("Pragma", "public");
                    response.setContentLength(baoOutput.size());

                    // 傳回 Client 端
                    ServletOutputStream sout = null;
                    try {
                        sout = response.getOutputStream();
                        baoOutput.writeTo(sout);
                        sout.flush();
                        log.debug("列印作業 - 月核定處理相關報表 - 月編審異動清單 - 產製 MonthlyRpt02Action.doReport() 完成 ... ");
                    }
                    catch (Exception e) {
                    }
                    finally {
                        if (baoOutput != null)
                            baoOutput.close();
                        if (sout != null)
                            sout.close();
                    }
                }
                else {

                    ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                    MonthlyRpt02Report report = new MonthlyRpt02Report();
                    baoOutput = report.doReport(userData, caseData, map);

                    String printDate = DateUtility.getNowChineseDate();

                    // 設定回傳回 Client 端之檔案大小, 若不設定,
                    // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                    response.setHeader("Content-disposition", "attachment; filename=\"" + Encode.forJava(payCode) + "_MonthlyRpt02_" + Encode.forJava(printDate) + ".pdf" + "\"");
                    response.setHeader("Content-Encoding", "pdf");
                    response.setContentType("application/pdf"); // 設定MIME
                    response.setHeader("Expires", "0");
                    response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                    response.setHeader("Pragma", "public");
                    response.setContentLength(baoOutput.size());

                    // 傳回 Client 端
                    ServletOutputStream sout = null;
                    try {
                        sout = response.getOutputStream();
                        baoOutput.writeTo(sout);
                        sout.flush();
                    }
                    catch (Exception e) {
                    }
                    finally {
                        if (baoOutput != null)
                            baoOutput.close();
                        if (sout != null)
                            sout.close();
                    }
                }
                return null;
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("產製 月編審異動清單 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}
