package tw.gov.bli.ba.rpt.actions;

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
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.rpt.cases.OtherRpt06Case;
import tw.gov.bli.ba.rpt.forms.OtherRpt06Form;
import tw.gov.bli.ba.rpt.report.OtherRpt06Report;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 列印作業 - 其他報表 - 轉催收核定清單 - 查詢頁面 (balp0d650l.jsp)
 * 
 * @author ZeHua
 */
public class OtherRpt06Action extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(OtherRpt05Action.class);

    private static final String FORWARD_LIST_PAGE = "listPage";

    private RptService rptService;

    /**
     * 列印作業 - 其他類報表 - 轉催收核定清單 - 查詢頁面 (balp0d650l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doProduct(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 其他類報表  - 轉催收核定清單 - 查詢頁面 OtherRpt06Action.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        OtherRpt06Form queryForm = (OtherRpt06Form) form;

        try {

            String payCode = queryForm.getPayCode();
            String procYm = queryForm.getProcYm();
            // String npWithLip = queryForm.getNpWithLip(); // 國併勞

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("payCode", payCode);
            map.put("procYm", procYm);

            String paySeqNo = null;

            String flag = rptService.execOverDueRptType(payCode, DateUtility.changeChineseYearMonthType(procYm), paySeqNo, DateUtility.getNowWestDate(), "16", "", "");
            if (StringUtils.isBlank(flag)) {
                // 處理完成 失敗訊息
                saveMessages(session, DatabaseMessageHelper.getProcDataErrorMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
            else {
                if (Integer.parseInt(flag) <= 0) {
                    // 設定 無查詢資料 訊息
                    saveMessages(session, DatabaseMessageHelper.getNoProcDataMessage());
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
                }
                else {
                    // 處理完成 成功訊息
                    saveMessages(session, DatabaseMessageHelper.getProcDataMessage());
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
                }
            }

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("轉催收核定清單 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 列印作業 - 其他類報表 - 轉催收核定清單 - 查詢頁面 (balp0d650l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 其他類報表  - 轉催收核定清單 - 查詢頁面 OtherRpt06Action.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        OtherRpt06Form queryForm = (OtherRpt06Form) form;

        try {

            String payCode = queryForm.getPayCode();
            String procYm = queryForm.getProcYm();
            // String npWithLip = queryForm.getNpWithLip(); // 國併勞

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("payCode", payCode);
            map.put("procYm", procYm);

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

            List caseData = null;
            List caseDataFooter = null; // 20120406
            caseData = new ArrayList<OtherRpt06Case>();
            caseDataFooter = new ArrayList<OtherRpt06Case>(); // 20120406
            List caseData1 = null;
            List caseDataFooter1 = null; // 20120406
            caseData1 = new ArrayList<OtherRpt06Case>();
            caseDataFooter1 = new ArrayList<OtherRpt06Case>(); // 20120406
            List<OtherRpt06Case> caseTest = new ArrayList<OtherRpt06Case>();
            List<OtherRpt06Case> caseTest1 = new ArrayList<OtherRpt06Case>();
            caseTest = rptService.getOtherRpt06CompRptBy(payCode, DateUtility.getNowWestDate(), isNull, isEqual);
            if (!(payCode.equals("L"))) {
                caseTest1 = rptService.getOtherRpt06CompRptBy(payCode, DateUtility.getNowWestDate(), notNull, notEqual);
            }
            if (caseTest.size() > 0 || caseTest1.size() > 0) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getProcDulpicate());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            String flag = rptService.execOverDueRptType(payCode, DateUtility.changeChineseYearMonthType(procYm), paySeqNo, DateUtility.getNowWestDate(), "16", "", "");

            if (StringUtils.isBlank(flag)) {
                // 處理完成 失敗訊息
                saveMessages(session, DatabaseMessageHelper.getProcDataErrorMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
            else {
                if (Integer.parseInt(flag) <= 0) {
                    // 設定 無查詢資料 訊息
                    saveMessages(session, DatabaseMessageHelper.getNoProcDataMessage());
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
                }
                else {

                    // 抓報表資料
                    // 【NACHGMK is null】
                    caseData = rptService.getOtherRpt06DataBy(payCode, DateUtility.changeChineseYearMonthType(procYm), paySeqNo, isNull, isEqual, DateUtility.getNowWestDate());
                    caseDataFooter = rptService.getOtherRpt06DataFooterBy(payCode, DateUtility.changeChineseYearMonthType(procYm), paySeqNo, isNull, isEqual, DateUtility.getNowWestDate());

                    if (!(payCode.equals("L"))) {
                        caseData1 = rptService.getOtherRpt06DataBy(payCode, DateUtility.changeChineseYearMonthType(procYm), paySeqNo, notNull, notEqual, DateUtility.getNowWestDate());
                        caseDataFooter1 = rptService.getOtherRpt06DataFooterBy(payCode, DateUtility.changeChineseYearMonthType(procYm), paySeqNo, notNull, notEqual, DateUtility.getNowWestDate());
                    }

                    if (caseData.size() == 0 && caseData1.size() == 0) {
                        // 設定 無查詢資料 訊息
                        saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                        return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
                    }
                    else {
                        ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                        OtherRpt06Report report = new OtherRpt06Report();
                        baoOutput = report.doReport(userData, caseData, caseData1, caseDataFooter, caseDataFooter1, map);

                        String printDate = DateUtility.getNowChineseDate();

                        // 設定回傳回 Client 端之檔案大小, 若不設定,
                        // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                        response.setHeader("Content-disposition", "attachment; filename=\"" + Encode.forJava(payCode) + "_OtherRpt06_" + Encode.forJava(printDate) + ".pdf" + "\"");
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
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("產製 轉催收核定清單 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 列印作業 - 其他類報表 - 轉催收核定清單 - 補印功能 (balp0d650l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doCompReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 其他類報表  -  轉催收核定清單 - 補印功能　 OtherRpt06Action.doCompReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        OtherRpt06Form queryForm = (OtherRpt06Form) form;

        try {

            String payCode = queryForm.getPayCode();
            String demDate = queryForm.getDemDate();

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("payCode", payCode);

            String paySeqNo = null;
            String isNull = null;
            String isEqual = null;
            String notNull = null;
            String notEqual = null;

            // String flag = rptService.execOverDueRptType(payCode, "" ,paySeqNo ,DateUtility.getNowWestDate() ,"17" ,apNo, DateUtility.changeChineseYearType(deadYy));
            // 處理完成 成功訊息
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
            List caseData = null;
            List caseDataFooter = null; // 20120406
            caseData = new ArrayList<OtherRpt06Case>();
            caseDataFooter = new ArrayList<OtherRpt06Case>(); // 20120406
            List caseData1 = null;
            List caseDataFooter1 = null; // 20120406
            caseData1 = new ArrayList<OtherRpt06Case>();
            caseDataFooter1 = new ArrayList<OtherRpt06Case>(); // 20120406

            // 抓報表資料
            // 【NACHGMK is null】
            caseData = rptService.getOtherRpt06CompRptBy(payCode, DateUtility.changeDateType(demDate), isNull, isEqual);
            caseDataFooter = rptService.getOtherRpt06CompRptFooterBy(payCode, DateUtility.changeDateType(demDate), isNull, isEqual);
            // 【NACHGMK is not null】

            if (!(payCode.equals("L"))) {
                caseData1 = rptService.getOtherRpt06CompRptBy(payCode, DateUtility.changeDateType(demDate), notNull, notEqual);
                caseDataFooter1 = rptService.getOtherRpt06CompRptFooterBy(payCode, DateUtility.changeDateType(demDate), notNull, notEqual);
            }

            if (caseData.size() == 0 && caseData1.size() == 0) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            else {
                ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                OtherRpt06Report report = new OtherRpt06Report();
                baoOutput = report.doReport(userData, caseData, caseData1, caseDataFooter, caseDataFooter1, map);

                String printDate = DateUtility.getNowChineseDate();

                // 設定回傳回 Client 端之檔案大小, 若不設定,
                // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                response.setHeader("Content-disposition", "attachment; filename=\"" + Encode.forJava(payCode) + "_OtherRpt08_" + Encode.forJava(printDate) + ".pdf" + "\"");
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
            log.error("產製 轉呆帳核定清單 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}
