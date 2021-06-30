package tw.gov.bli.ba.rpt.actions;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.rpt.cases.DataUpdateRpt04Case;
import tw.gov.bli.ba.rpt.forms.DataUpdateRpt04Form;
import tw.gov.bli.ba.rpt.report.DataUpdateRpt04Report;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 列印作業 - 更正作業相關報表 - 紓困貸款呆帳債務人照會單 - 查詢頁面 (balp0d140l.jsp)
 * 
 * @author swim
 */
public class DataUpdateRpt04Action extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(DataUpdateRpt04Action.class);
    
    private RptService rptService;

    /**
     * 列印作業 - 更正作業相關報表 - 紓困貸款呆帳債務人照會單 - 查詢頁面 (balp0d140l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 更正作業相關報表 - 紓困貸款呆帳債務人照會單 - 查詢頁面 DataUpdateRpt04Action.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DataUpdateRpt04Form queryForm = (DataUpdateRpt04Form) form;

        try {
            String apNo1 = queryForm.getApNo1();
            String apNo2 = queryForm.getApNo2();
            String apNo3 = queryForm.getApNo3();
            String apNo4 = queryForm.getApNo4();

            // 抓報表資料
            List<DataUpdateRpt04Case> caseData = rptService.getDataUpdateRpt04DataBy(apNo1+apNo2+apNo3+apNo4);

            if (caseData.size() == 0) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            else {
                ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                DataUpdateRpt04Report report = new DataUpdateRpt04Report();
                baoOutput = report.doReport(userData, caseData);

                String printDate = DateUtility.getNowChineseDate();

                // 設定回傳回 Client 端之檔案大小, 若不設定,
                // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                response.setHeader("Content-disposition", "attachment; filename=\"DataUpdateRpt04_" + printDate + ".pdf" + "\"");
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

                return null;
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("產製 紓困貸款呆帳債務人照會單 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}
