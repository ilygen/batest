package tw.gov.bli.ba.rpt.actions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.owasp.encoder.Encode;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.rpt.forms.OtherRpt11Form;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.util.ReportUtility;
import tw.gov.bli.common.helper.UserSessionHelper;
import tw.gov.bli.common.util.ExceptionUtil;

/**
 * 列印作業 - 其他類報表 - 老年差額金通知補印作業 - 查詢頁面 (balp0d560l.jsp)
 * 
 * @author KIYOMI
 */
public class OtherRpt11Action extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(OtherRpt11Action.class);

    private RptService rptService;

    String LIST_PAGE = "listPage";

    /**
     * 列印作業 - 其他類報表 - 老年差額金通知補印作業 - 查詢頁面 (balp0d560l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 其他類報表 - 老年差額金通知補印作業 - 查詢頁面 OtherRpt11Action.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        OtherRpt11Form queryForm = (OtherRpt11Form) form;

        try {
            String apNo = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();
            String issuYm = DateUtility.changeChineseYearMonthType(queryForm.getIssuYm());

            String fileName = "";

            if (!apNo.equals("")) {
                fileName = "MonthlyRpt31_" + apNo;
            }
            else if (!issuYm.equals("")) {
                fileName = "MonthlyRpt31_" + issuYm;
            }
            else {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                queryForm.reset(mapping, request);
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }

            // 抓取已產過的報表
            File[] fileList = ReportUtility.listFile(fileName, "pdf");
            if (fileList != null && fileList.length > 0) {

                List<String> files = new ArrayList<String>();
                for (File existFile : fileList) {
                    files.add(existFile.getName());
                }
                session.setAttribute(ConstantKey.OTHER_RPT_11_FILE_LIST, files);
                return mapping.findForward(LIST_PAGE);

            }
            else {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                queryForm.reset(mapping, request);
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("老年差額金通知補印作業 發生錯誤：" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public ActionForward downloadFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.info("執行 列印作業 - 其他類報表  - 老年差額金通知補印作業 - 下載檔案 OtherRpt11Action.downloadFile() ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        OtherRpt11Form queryForm = (OtherRpt11Form) form;
        FileInputStream fs = null;
        try {
            String fileName = queryForm.getDownloadFileName();

            if (StringUtils.isNotBlank(fileName)) {
                fs = new FileInputStream(PropertyHelper.getProperty("rpt.path") + Encode.forJava(fileName));
                byte[] content = IOUtils.toByteArray(fs);

                // 設定回傳回 Client 端之檔案大小, 若不設定,
                // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                response.setHeader("Content-disposition", "attachment; filename=\"" + Encode.forJava(fileName) + "\"");
                response.setHeader("Content-Encoding", "pdf");
                response.setContentType("application/pdf"); // 設定MIME
                response.setHeader("Expires", "0");
                response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                response.setHeader("Pragma", "public");
                response.setContentLength(content.length);

                ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();
                baoOutput.write(content);
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
            else {
                saveMessages(session, CustomMessageHelper.getDownloadErrorMessage());
                return mapping.findForward(LIST_PAGE);
            }

        }
        catch (Exception e) {
            log.error("老年差額金通知補印作業 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getDownloadErrorMessage());
            return mapping.findForward(LIST_PAGE);
        }
        finally {
            ExceptionUtil.safeColse(fs);
        }
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}
