package tw.gov.bli.ba.rpt.actions;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
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
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt02Case;
import tw.gov.bli.ba.rpt.forms.OldAgeReviewRpt02Form;
import tw.gov.bli.ba.rpt.report.OldAgeReviewRpt02Report;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 列印作業 - 審核作業相關報表 - 審核給付清單 - 查詢頁面 (balp0d020l.jsp)
 * 
 * @author swim
 */
public class OldAgeReviewRpt02Action extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(OldAgeReviewRpt02Action.class);

    private RptService rptService;

    /**
     * 列印作業 - 審核作業相關報表 - 審核給付清單 - 查詢頁面 (balp0d020l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 審核作業相關報表 - 審核給付清單 - 查詢頁面 OldAgeReviewRpt02Action.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        OldAgeReviewRpt02Form queryForm = (OldAgeReviewRpt02Form) form;

        try {
            String prtTyp = queryForm.getPrtTyp();// 列印項目
            String payCode = queryForm.getPayCode();// 給付種類
            String chkMan = queryForm.getChkMan();// 審核人員
            String chkDate = DateUtility.changeDateType(queryForm.getChkDate());// 審核日期
            String packDate = DateUtility.changeDateType(queryForm.getPackDate());// 打包日期
            BigDecimal pageNo = queryForm.getPageNo();// 頁次
            
            // 抓報表資料
            List<OldAgeReviewRpt02Case> caseData = rptService.getOldAgeReviewRpt02DataBy(prtTyp, payCode, chkMan, packDate, pageNo, chkDate);

            if (caseData.size() == 0) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            else {
                ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                OldAgeReviewRpt02Report report = new OldAgeReviewRpt02Report();
                baoOutput = report.doReport(userData, caseData);

                String printDate = DateUtility.getNowChineseDate();

                // 設定回傳回 Client 端之檔案大小, 若不設定,
                // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                response.setHeader("Content-disposition", "attachment; filename=\"OldAgeReviewRpt02_" + printDate + ".pdf" + "\"");
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
            log.error("產製 審核給付清單 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}
