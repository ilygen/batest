package tw.gov.bli.ba.rpt.actions;

import java.io.ByteArrayOutputStream;

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
import org.owasp.encoder.Encode;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.rpt.cases.DataUpdateRpt01Case;
import tw.gov.bli.ba.rpt.forms.DataUpdateRpt01Form;
import tw.gov.bli.ba.rpt.report.DataUpdateRpt01Report;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 列印作業 - 更正作業相關報表 - 紓困繳納查詢單 - 查詢頁面 (balp0d110l.jsp)
 * 
 * @author Goston
 */
public class DataUpdateRpt01Action extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(DataUpdateRpt01Action.class);

    private RptService rptService;

    /**
     * 列印作業 - 更正作業相關報表 - 紓困繳納查詢單 - 查詢頁面 (balp0d110l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 更正作業相關報表 - 紓困繳納查詢單 - 查詢頁面 DataUpdateRpt01Action.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DataUpdateRpt01Form queryForm = (DataUpdateRpt01Form) form;

        try {
            String apNo = StringUtils.upperCase(StringUtils.defaultString(queryForm.getApNo1())) + StringUtils.defaultString(queryForm.getApNo2()) + StringUtils.defaultString(queryForm.getApNo3()) + StringUtils.defaultString(queryForm.getApNo4());

            // 受理編號長度不滿 12 碼則不做查詢
            if (StringUtils.length(apNo) != ConstantKey.APNO_LENGTH) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }

            // 抓報表資料
            DataUpdateRpt01Case caseData = rptService.getDataUpdateRpt01DataBy(apNo);

            if (caseData == null) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            else {
                ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                DataUpdateRpt01Report report = new DataUpdateRpt01Report();
                baoOutput = report.doReport(userData, caseData);

                String printDate = DateUtility.getNowChineseDate();

                // 設定回傳回 Client 端之檔案大小, 若不設定,
                // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                response.setHeader("Content-disposition", "attachment; filename=\"DataUpdateRpt01_" + Encode.forJava(apNo) + ".pdf" + "\"");
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
            log.error("產製 紓困繳納查詢單 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}
