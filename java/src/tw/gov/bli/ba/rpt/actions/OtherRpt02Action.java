package tw.gov.bli.ba.rpt.actions;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
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
import org.owasp.encoder.Encode;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.rpt.cases.OtherRpt02Case;
import tw.gov.bli.ba.rpt.forms.OtherRpt02Form;
import tw.gov.bli.ba.rpt.report.OtherRpt02Report;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 列印作業 - 其他類報表 - 已退匯或退回現金尚未沖轉收回案件清單 - 查詢頁面 (balp0d520l.jsp)
 * 
 * @author Noctis
 */
public class OtherRpt02Action extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(OtherRpt02Action.class);

    private RptService rptService;

    /**
     * 列印作業 - 其他類報表 - 已退匯或退回現金尚未沖轉收回案件清單 - 查詢頁面 (balp0d520l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 其他類報表 - 已退匯或退回現金尚未沖轉收回案件清單 - 查詢頁面 OtherRpt01Action.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        OtherRpt02Form queryForm = (OtherRpt02Form) form;

        try {
            // 給付別
            String payCode = queryForm.getPayCode();
            BigDecimal recRem = null;

            // reportRList 報表所使用之List
            List<OtherRpt02Case> reportRList = new ArrayList<OtherRpt02Case>();

            // -----退匯、退匯止付-----

            // 抓報表資料 APNO,SEQNO list
            List<OtherRpt02Case> caseList = rptService.selectOtherRpt02DataListBy(payCode);

            for (OtherRpt02Case caseData : caseList) {
                // 抓取 應收金額
                recRem = rptService.selectOtherRpt02RecRemDataBy(payCode, caseData.getApNo(), caseData.getSeqNo());

                // 抓取該APNO + SEQNO 的退匯（退現）日期、退匯（退回）金額、來源
                List<OtherRpt02Case> sourceDataList = rptService.selectOtherRpt02SourceDataListBy(caseData.getApNo());

                for (int i = 0; i < sourceDataList.size(); i++) {
                    OtherRpt02Case reportData = new OtherRpt02Case();
                    if (i == 0) {
                        reportData.setApNo(caseData.getApNo());
                        reportData.setSeqNo(caseData.getSeqNo());
                        reportData.setRecRem(recRem);
                        reportData.setBrChkDate(sourceDataList.get(i).getBrChkDate());
                        reportData.setRemitAmt(sourceDataList.get(i).getRemitAmt());
                        reportData.setSource(sourceDataList.get(i).getSource());
                    }
                    else {
                        reportData.setApNo("");
                        reportData.setSeqNo("");
                        reportData.setRecRem(null);
                        reportData.setBrChkDate(sourceDataList.get(i).getBrChkDate());
                        reportData.setRemitAmt(sourceDataList.get(i).getRemitAmt());
                        reportData.setSource(sourceDataList.get(i).getSource());
                    }
                    reportRList.add(reportData);
                }
            }
            // -----退匯、退匯止付-----

            // -----退回現金-----

            // 抓報表資料 APNO,SEQNO list
            List<OtherRpt02Case> cashDataList = rptService.selectOtherRpt02CashDataListBy(payCode);

            for (OtherRpt02Case caseData : cashDataList) {

                // 抓取該APNO + SEQNO 的退匯（退現）日期、退匯（退回）金額、來源
                List<OtherRpt02Case> cashSourceDataList = rptService.selectOtherRpt02CashSourceDataListBy(caseData.getApNo());

                for (int i = 0; i < cashSourceDataList.size(); i++) {
                    OtherRpt02Case reportData = new OtherRpt02Case();
                    if (i == 0) {
                        reportData.setApNo(caseData.getApNo());
                        reportData.setSeqNo(caseData.getSeqNo());
                        reportData.setRecRem(caseData.getRecRem());
                        reportData.setBrChkDate(cashSourceDataList.get(i).getBrChkDate());
                        reportData.setRemitAmt(cashSourceDataList.get(i).getRemitAmt());
                        reportData.setSource(cashSourceDataList.get(i).getSource());
                    }
                    else {
                        reportData.setApNo("");
                        reportData.setSeqNo("");
                        reportData.setRecRem(null);
                        reportData.setBrChkDate(cashSourceDataList.get(i).getBrChkDate());
                        reportData.setRemitAmt(cashSourceDataList.get(i).getRemitAmt());
                        reportData.setSource(cashSourceDataList.get(i).getSource());
                    }
                    reportRList.add(reportData);
                }
            }

            // -----退回現金-----

            if (caseList.size() <= 0 && cashDataList.size() <= 0) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            else {
                ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                OtherRpt02Report report = new OtherRpt02Report();
                baoOutput = report.doReport(payCode, reportRList);

                String printDate = DateUtility.getNowChineseDate();

                // 設定回傳回 Client 端之檔案大小, 若不設定,
                // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                response.setHeader("Content-disposition", "attachment; filename=\"OtherRpt02_" + Encode.forJava(payCode) + ".pdf" + "\"");
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
            log.error("產製 已退匯或退回現金尚未沖轉收回案件清單:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}
