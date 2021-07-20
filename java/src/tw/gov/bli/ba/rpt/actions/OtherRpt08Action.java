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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.owasp.encoder.Encode;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.bj.helper.CaseSessionHelper;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.query.helper.FormSessionHelper;
import tw.gov.bli.ba.rpt.cases.OtherRpt08Case;
import tw.gov.bli.ba.rpt.forms.OtherRpt08Form;
import tw.gov.bli.ba.rpt.report.OtherRpt08Report;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 列印作業 - 其他報表 - 轉呆帳核定清單 - 查詢轉單清單頁面 (balp0d670l.jsp)
 * 
 * @author ZeHua
 */
public class OtherRpt08Action extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(OtherRpt08Action.class);

    private RptService rptService;

    public ActionForward doDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        OtherRpt08Form queryForm = (OtherRpt08Form) form;
        try {
            String payCode = queryForm.getPayCode();
            String apNo = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();
            String isNull = null;
            String isEqual = null;
            String notNull = null;
            String notEqual = null;
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
            List<OtherRpt08Case> caseData = rptService.getBadDebtDtl(payCode, queryForm.getDeadYy(), apNo, isNull, isEqual);

            if (caseData == null || caseData.size() <= 0) {
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                log.debug(" 列印作業 - 其他報表 - 轉呆帳核定清單維護  - OtherRpt08Action.doDetail() 完成 ...(查無資料) ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            else {
                CaseSessionHelper.setOtherRpt08DetailList(caseData, request);
                FormSessionHelper.setOtherRpt08Form(queryForm, request);
                log.debug(" 列印作業 - 其他報表 - 轉呆帳核定清單維護 - OtherRpt08Action.doDetail() 完成 ...");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_DETAIL);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("轉呆帳核定清單 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 列印作業 - 其他報表 - 轉呆帳核定清單 - 查詢轉單清單頁面 (balp0d671l.jsp) 確認
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSaveDataForAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("列印作業 - 其他報表 - 轉呆帳核定清單 - 查詢轉單清單頁面  OtherRpt08Action.doSaveBeneficiaryDataForAll() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        OtherRpt08Form detailform = (OtherRpt08Form) form;
        // FormSessionHelper.setOtherRpt08DetailForm(detailform, request);
        OtherRpt08Form queryform = FormSessionHelper.getOtherRpt08Form(request);
        try {
            if (!("").equals(detailform.getApNoOfConfirm())) {
                rptService.updateAllBadDebtData(detailform.getApNoOfConfirm(), CaseSessionHelper.getOtherRpt08DetailList(request));

                // 設定更新成功訊息
                saveMessages(session, CustomMessageHelper.getReviewSuccessMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);

            }
            else {// 無資料可更新
                // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                FormSessionHelper.removeOtherRpt08Form(request);
                FormSessionHelper.removeOtherRpt08DetailForm(request);
                CaseSessionHelper.removeOtherRpt08DetailList(request);

                ActionMessages msgs = new ActionMessages();
                msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.review.noDataToUpdate"));
                saveMessages(session, msgs);

                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);

            }
        }
        catch (Exception e) {
            // 設定更新失敗訊息
            log.error("PaymentReviewAction.doSaveDataForAll() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getReviewFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 列印作業 - 其他類報表 - 轉呆帳核定清單 - 查詢頁面 (balp0d670l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 其他類報表  - 轉呆帳核定清單 - 查詢頁面 OtherRpt08Action.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        OtherRpt08Form queryForm = (OtherRpt08Form) form;

        try {

            String payCode = queryForm.getPayCode();
            String deadYy = queryForm.getDeadYy();
            String apNo = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("payCode", payCode);
            map.put("deadYy", deadYy);
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
            List caseData = null;
            List caseDataFooter = null; // 20120406
            caseData = new ArrayList<OtherRpt08Case>();
            caseDataFooter = new ArrayList<OtherRpt08Case>(); // 20120406
            List caseData1 = null;
            List caseDataFooter1 = null; // 20120406
            caseData1 = new ArrayList<OtherRpt08Case>();
            caseDataFooter1 = new ArrayList<OtherRpt08Case>(); // 20120406

            List caseTest = null;
            List caseTest1 = null;
            caseTest = new ArrayList<OtherRpt08Case>();
            caseTest1 = new ArrayList<OtherRpt08Case>(); // 20150820

            caseTest = rptService.getOtherRpt08CompRptBy(payCode, DateUtility.getNowWestDate(), isNull, isEqual);
            if (!(payCode.equals("L"))) {
                caseTest1 = rptService.getOtherRpt08CompRptBy(payCode, DateUtility.getNowChineseDate(), notNull, notEqual);
            }
            if (caseTest.size() > 0 || caseTest1.size() > 0) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getProcDulpicate());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }

            String flag = rptService.execOverDueRptType(payCode, "", paySeqNo, DateUtility.getNowWestDate(), "17", apNo, DateUtility.changeChineseYearType(deadYy));
            if (StringUtils.isBlank(flag)) {
                // 處理完成 失敗訊息
                saveMessages(session, DatabaseMessageHelper.getProcDataErrorMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
                // 處理完成 成功訊息
            }
            else {
                // 抓報表資料
                // 【NACHGMK is null】
                caseData = rptService.getOtherRpt08DataBy(payCode, apNo, DateUtility.changeChineseYearType(deadYy), paySeqNo, isNull, isEqual, DateUtility.getNowWestDate());
                caseDataFooter = rptService.getOtherRpt08DataFooterBy(payCode, apNo, DateUtility.changeChineseYearType(deadYy), paySeqNo, isNull, isEqual, DateUtility.getNowWestDate());
                // 【NACHGMK is not null】

                if (!(payCode.equals("L"))) {
                    caseData1 = rptService.getOtherRpt08DataBy(payCode, apNo, DateUtility.changeChineseYearType(deadYy), paySeqNo, notNull, notEqual, DateUtility.getNowWestDate());
                    caseDataFooter1 = rptService.getOtherRpt08DataFooterBy(payCode, apNo, DateUtility.changeChineseYearType(deadYy), paySeqNo, notNull, notEqual, DateUtility.getNowWestDate());
                }

                if (caseData.size() == 0 && caseData1.size() == 0) {
                    // 設定 無查詢資料 訊息
                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
                }
                else {
                    ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                    OtherRpt08Report report = new OtherRpt08Report();
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

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("產製 轉呆帳核定清單 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 列印作業 - 其他類報表 - 轉呆帳核定清單 - 補印功能 (balp0d670l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doCompReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 其他類報表  - 轉呆帳核定清單 -　補印功能　 OtherRpt08Action.doCompReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        OtherRpt08Form queryForm = (OtherRpt08Form) form;

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
            caseData = new ArrayList<OtherRpt08Case>();
            caseDataFooter = new ArrayList<OtherRpt08Case>(); // 20120406
            List caseData1 = null;
            List caseDataFooter1 = null; // 20120406
            caseData1 = new ArrayList<OtherRpt08Case>();
            caseDataFooter1 = new ArrayList<OtherRpt08Case>(); // 20120406

            // 抓報表資料
            // 【NACHGMK is null】
            caseData = rptService.getOtherRpt08CompRptBy(payCode, bDebtDate, isNull, isEqual);
            caseDataFooter = rptService.getOtherRpt08CompRptFooterBy(payCode, bDebtDate, isNull, isEqual);
            // 【NACHGMK is not null】

            if (!(payCode.equals("L"))) {
                caseData1 = rptService.getOtherRpt08CompRptBy(payCode, bDebtDate, notNull, notEqual);
                caseDataFooter1 = rptService.getOtherRpt08CompRptFooterBy(payCode, bDebtDate, notNull, notEqual);
            }

            if (caseData.size() == 0 && caseData1.size() == 0) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            else {
                ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                OtherRpt08Report report = new OtherRpt08Report();
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
