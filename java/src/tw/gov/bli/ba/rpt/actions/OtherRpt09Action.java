package tw.gov.bli.ba.rpt.actions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
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
import tw.gov.bli.ba.rpt.cases.OtherRpt09Case;
import tw.gov.bli.ba.rpt.forms.OtherRpt09Form;
import tw.gov.bli.ba.rpt.report.OtherRpt09Report;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 列印作業 - 其他報表 - 轉呆帳核定清冊 - 查詢頁面 (balp0d680l.jsp)
 * 
 * @author ZeHua
 */
public class OtherRpt09Action extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(OtherRpt09Action.class);

    private RptService rptService;

    /**
     * 列印作業 - 其他報表 - 轉呆帳核定清冊 - 查詢頁面 (balp0d680l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("列印作業 - 其他報表 - 轉呆帳核定清冊  查詢頁面 Other07Action.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        OtherRpt09Form queryForm = (OtherRpt09Form) form;

        try {
            String payCode = queryForm.getPayCode();
            String deadYy = queryForm.getDeadYy();
            String apNo = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("payCode", payCode);
            map.put("issuYm", deadYy);
            map.put("apNo", apNo);

            String paySeqNo = null;
            String isNull = null;
            String isEqual = null;
            String notNull = null;
            String notEqual = null;

            if (payCode.equals("L")) {
                isNull = "";
                isEqual = "";
                notNull = "";
                notEqual = "";

            }
            else {
                isNull = "IS NULL";
                isEqual = "=";
                notNull = "IS NOT NULL";
                notEqual = "<>";
            }

            List caseData1 = null;
            caseData1 = new ArrayList<OtherRpt09Case>();
            List caseTest1 = null;
            caseTest1 = new ArrayList<OtherRpt09Case>();

            List<OtherRpt09Case> caseTest = rptService.getOtherRptComp09DataBy(payCode, DateUtility.getNowWestDate(), isNull, isEqual);
            // 【NACHGMK is not null】
            if (!(payCode.equals("L"))) {
                caseTest1 = rptService.getOtherRptComp09DataBy(payCode, DateUtility.getNowWestDate(), notNull, notEqual);
            }
            if (caseTest.size() > 0 || caseTest1.size() > 0) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            // 抓報表資料
            // 【NACHGMK is null】
            List<OtherRpt09Case> caseData = rptService.getOtherRpt09DataBy(payCode, apNo, DateUtility.changeChineseYearType(deadYy), paySeqNo, isNull, isEqual, DateUtility.getNowWestDate());
            // 【NACHGMK is not null】
            if (!(payCode.equals("L"))) {
                caseData1 = rptService.getOtherRpt09DataBy(payCode, apNo, DateUtility.changeChineseYearType(deadYy), paySeqNo, notNull, notEqual, DateUtility.getNowWestDate());
            }

            if (caseData.size() == 0 && caseData1.size() == 0) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                queryForm.reset(mapping, request);
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            else {
                ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                OtherRpt09Report report = new OtherRpt09Report();
                baoOutput = report.doReport(userData, caseData, caseData1, map);

                String printDate = DateUtility.getNowChineseDate();

                // 設定回傳回 Client 端之檔案大小, 若不設定,
                // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                response.setHeader("Content-disposition", "attachment; filename=\"" + Encode.forJava(payCode) + "_OtherRpt09_" + Encode.forJava(printDate) + ".pdf" + "\"");
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
            log.error("產製 轉呆帳核定清冊 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 列印作業 - 其他報表 - 轉呆帳核定清冊- 補印 - 查詢頁面 (balp0d680l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doCompReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("列印作業 - 其他報表 - 轉呆帳核定清冊-補印  查詢頁面 Other07Action.doCompReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        OtherRpt09Form queryForm = (OtherRpt09Form) form;

        try {
            String payCode = queryForm.getPayCode();
            String bDebtDate = DateUtility.changeDateType(queryForm.getbDebtDate());

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("payCode", payCode);
            map.put("bDebtDate", bDebtDate);

            String paySeqNo = null;
            String isNull = null;
            String isEqual = null;
            String notNull = null;
            String notEqual = null;

            if (payCode.equals("L")) {
                isNull = "";
                isEqual = "";
                notNull = "";
                notEqual = "";

            }
            else {
                isNull = "IS NULL";
                isEqual = "=";
                notNull = "IS NOT NULL";
                notEqual = "<>";
            }

            List caseData1 = null;
            caseData1 = new ArrayList<OtherRpt09Case>();

            // 抓報表資料
            // 【NACHGMK is null】
            List<OtherRpt09Case> caseData = rptService.getOtherRptComp09DataBy(payCode, bDebtDate, isNull, isEqual);
            // 【NACHGMK is not null】
            if (!(payCode.equals("L"))) {
                caseData1 = rptService.getOtherRptComp09DataBy(payCode, bDebtDate, notNull, notEqual);
            }

            if (caseData.size() == 0 && caseData1.size() == 0) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                queryForm.reset(mapping, request);
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            else {
                ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                OtherRpt09Report report = new OtherRpt09Report();
                baoOutput = report.doReport(userData, caseData, caseData1, map);

                String printDate = DateUtility.getNowChineseDate();

                // 設定回傳回 Client 端之檔案大小, 若不設定,
                // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                response.setHeader("Content-disposition", "attachment; filename=\"" + Encode.forJava(payCode) + "_OtherRpt09_" + Encode.forJava(printDate) + ".pdf" + "\"");
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
            log.error("產製 轉呆帳核定清冊 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}
