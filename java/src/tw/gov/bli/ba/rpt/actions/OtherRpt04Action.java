package tw.gov.bli.ba.rpt.actions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
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
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.rpt.cases.DecisionRpt03Case;
import tw.gov.bli.ba.rpt.cases.OtherRpt04Case;
import tw.gov.bli.ba.rpt.forms.DecisionRpt03Form;
import tw.gov.bli.ba.rpt.forms.OtherRpt04Form;
import tw.gov.bli.ba.rpt.report.DecisionRpt02Report;
import tw.gov.bli.ba.rpt.report.DecisionRpt03Report;
import tw.gov.bli.ba.rpt.report.OtherRpt04Report;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 列印作業 - 其他類報表  - 受理案件統計表 - 查詢頁面 (balp0d540l.jsp)
 * 
 * @author Noctis
 */
public class OtherRpt04Action extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(OtherRpt04Action.class);

    private RptService rptService;

    /**
     * 列印作業 - 其他類報表  - 受理案件統計表 - 查詢頁面 (balp0d540l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 其他類報表  - 受理案件統計表 - 查詢頁面 OtherRpt04Action.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        OtherRpt04Form queryForm = (OtherRpt04Form) form;

        try {
        	
            // 抓報表資料 page1
        	List<OtherRpt04Case> page1List = rptService.selectOtherRpt04Page1DataBy(queryForm.getCrtDate(),queryForm.getPayCode());
        	
        	// 抓報表資料 page2
        	List<OtherRpt04Case> page2List = rptService.selectOtherRpt04Page2DataBy(queryForm.getCrtDate(),queryForm.getPayCode());

            if (page1List.size() == 0) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            else {
                ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                OtherRpt04Report report = new OtherRpt04Report();
                baoOutput = report.doReport(userData, page1List, page2List, queryForm.getCrtDate());

                String printDate = DateUtility.getNowChineseDate();

                // 設定回傳回 Client 端之檔案大小, 若不設定,
                // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                response.setHeader("Content-disposition", "attachment; filename=\"OtherRpt04_" + printDate + ".pdf" + "\"");
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
            log.error("產製 受理案件統計表 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}
