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
import tw.gov.bli.ba.rpt.cases.MonthlyRpt31Case;
import tw.gov.bli.ba.rpt.forms.MonthlyRpt31Form;
import tw.gov.bli.ba.rpt.report.MonthlyRpt31Report;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.util.ReportUtility;
import tw.gov.bli.common.helper.UserSessionHelper;
import tw.gov.bli.common.util.ExceptionUtil;

/**
 * 列印作業 - 其他類報表 - 老年差額金通知 - 查詢頁面 (balp0d3t0l.jsp)
 * 
 * @author KIYOMI
 */
public class MonthlyRpt31Action extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(MonthlyRpt31Action.class);

    private RptService rptService;
    private UpdateService updateService;

    String LIST_PAGE = "listPage";

    /**
     * 列印作業 - 其他類報表 - 老年差額金通知 PDF - 查詢頁面 (balp0d3t0l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 其他類報表 - 老年差額金通知 - 查詢頁面 MonthlyRpt31Action.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        MonthlyRpt31Form queryForm = (MonthlyRpt31Form) form;

        try {
            String rptKind = queryForm.getRptKind();
            String payCode = queryForm.getApNo1();
            String apNo = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();
            // String sysYm = DateUtility.getNowWestDate().substring(0, 6);
  String sysYm = "201712";
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("progId", "BALP0D3T0L");
            map.put("rptKind", rptKind);
            map.put("payCode", payCode);
            map.put("apNo", apNo);
            map.put("sysYm", sysYm);

            String fileName = "";

            // 抓取 PDF 報表資料
            List<MonthlyRpt31Case> caseDataList = new ArrayList<MonthlyRpt31Case>();

            fileName = "MonthlyRpt31_" + apNo + "_" + rptKind + "_";

            // 抓取 PDF 報表資料
            caseDataList = rptService.getMonthlyRpt31DataBy(rptKind, payCode, apNo, sysYm, userData, map);

            if ((caseDataList == null || caseDataList.size() == 0)) {

                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                queryForm.reset(mapping, request);
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);

            }
            else {

                ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                MonthlyRpt31Report report = new MonthlyRpt31Report();
                baoOutput = report.doReport(userData, caseDataList, map);

                String printDate = DateUtility.getNowChineseDate();

                // 寫入local檔案
                File file = new File(PropertyHelper.getProperty("rpt.path") + Encode.forJava(fileName) + Encode.forJava(printDate) + ".pdf");
                OutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(file);
                    baoOutput.writeTo(outputStream);
                }
                finally {
                    ExceptionUtil.safeColse(outputStream);
                    ExceptionUtil.safeColse(baoOutput);
                }

                
                // 寫入老年差額金通知發文紀錄檔 (BAMARGINAMTNOTIFY) 及 行政支援紀錄檔 (MAADMREC)
                // updateService.insertDataToBamarginamtnotifyForMonthlyRpt31(userData, caseDataList, map);
                
                // 抓取以產過的報表
                File[] fileList = ReportUtility.listPDFFile(fileName);
                if (fileList != null && fileList.length > 0) {

                    List<String> files = new ArrayList<String>();
                    for (File existFile : fileList) {
                        files.add(existFile.getName());
                    }
                    session.setAttribute(ConstantKey.MONTHLY_RPT_31_FILE_LIST, files);
                    return mapping.findForward(LIST_PAGE);

                }
                else {
                    // 設定 無查詢資料 訊息
                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                    queryForm.reset(mapping, request);
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
                }

//                // 設定回傳回 Client 端之檔案大小, 若不設定,
//                // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
//                response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + printDate + ".pdf" + "\"");
//                response.setHeader("Content-Encoding", "pdf");
//                response.setContentType("application/pdf"); // 設定MIME
//                response.setHeader("Expires", "0");
//                response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
//                response.setHeader("Pragma", "public");
//                response.setContentLength(baoOutput.size());

//                // 傳回 Client 端
//                ServletOutputStream sout = null;
//                try {
//                    sout = response.getOutputStream();
//                    baoOutput.writeTo(sout);
//                    sout.flush();
//                }
//                catch (Exception e) {
//                }
//                finally {
//                    if (baoOutput != null)
//                        baoOutput.close();
//                    if (sout != null)
//                        sout.close();
//                }

//                return null;

            }

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("產製 老年差額金通知 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public ActionForward downloadFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.info("執行 列印作業 - 其他類報表  - 老年差額金通知 - 下載檔案 MonthlyRpt31Action.downloadFile() ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        MonthlyRpt31Form queryForm = (MonthlyRpt31Form) form;
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
            log.error("老年差額金通知 發生錯誤:" + ExceptionUtility.getStackTrace(e));
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

    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }
}
