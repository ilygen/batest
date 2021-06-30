package tw.gov.bli.ba.bj.actions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.owasp.encoder.Encode;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.bj.cases.CheckJobRptCase;
import tw.gov.bli.ba.bj.forms.CheckJobRptForm;
import tw.gov.bli.ba.bj.report.CheckJobRptKReport;
import tw.gov.bli.ba.bj.report.CheckJobRptLReport;
import tw.gov.bli.ba.bj.report.CheckJobRptSReport;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.services.BjService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 批次處理 - 檢核作業 - 產生檢核案件作業 - 查詢頁面 (baba0m200x.jsp)
 * 
 * @author Noctis
 */
public class CheckJobRptAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(CheckJobRptAction.class);

    private BjService bjService;

    /**
     * 批次處理 - 檢核作業 - 產生檢核案件作業 - 查詢頁面 (baba0m200x.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReportExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 檢核作業 - 產生檢核案件作業 - 產製 CheckJobRptAction.doReportExcel() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        CheckJobRptForm queryForm = (CheckJobRptForm) form;

        try {

            String payCode = queryForm.getPayCode();
            String issuYm = queryForm.getIssuYm();

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("payCode", payCode);
            map.put("issuYm", issuYm);

            String title = "CheckJobRpt" + payCode;

            // 取得 rvType = 1 資料
            List<CheckJobRptCase> dataListForRv1 = new ArrayList<CheckJobRptCase>();

            dataListForRv1 = bjService.selectBarvcaseDataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), "1");

            // 取得 rvType = 2 資料
            List<CheckJobRptCase> dataListForRv2 = new ArrayList<CheckJobRptCase>();

            dataListForRv2 = bjService.selectBarvcaseDataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), "2");

            if (dataListForRv1.size() == 0 && dataListForRv2.size() == 0) {

                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                queryForm.reset(mapping, request);
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);

            }
            else {
                // 產生EXCEL報表
                ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();
                if (StringUtils.equals(payCode, "L")) {

                    CheckJobRptLReport report = new CheckJobRptLReport();
                    baoOutput = report.doReport(dataListForRv1, dataListForRv2, map);

                }
                else if (StringUtils.equals(payCode, "K")) {

                    CheckJobRptKReport report = new CheckJobRptKReport();
                    baoOutput = report.doReport(dataListForRv1, dataListForRv2, map);

                }
                else if (StringUtils.equals(payCode, "S")) {

                    CheckJobRptSReport report = new CheckJobRptSReport();
                    baoOutput = report.doReport(dataListForRv1, dataListForRv2, map);

                }

                String printDate = DateUtility.getNowChineseDate();

                // 設定回傳回 Client 端之檔案大小, 若不設定,
                // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                response.setHeader("Content-disposition", "attachment; filename=\"" + Encode.forJava(new String(title.getBytes("Big5"), "ISO8859_1")) + Encode.forJava(printDate) + ".xls" + "\"");
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
                    log.debug("列印作業 - 月核定處理相關報表 - 勞保年金補送在學證明案件明細資料 Excel - 產製 MonthlyRpt29Action.doReportExcel() 完成 ... ");
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
            log.error("產製 檢核案件作業 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setBjService(BjService bjService) {
        this.bjService = bjService;
    }

}
