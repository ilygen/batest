package tw.gov.bli.ba.rpt.actions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
import tw.gov.bli.ba.rpt.cases.MonthlyRpt30Case;
import tw.gov.bli.ba.rpt.forms.MonthlyRpt30Form;
import tw.gov.bli.ba.rpt.report.MonthlyRpt30ExcelReport;
import tw.gov.bli.ba.rpt.report.MonthlyRpt30Report;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.util.ReportUtility;
import tw.gov.bli.common.helper.UserSessionHelper;
import tw.gov.bli.common.util.ExceptionUtil;

/**
 * 列印作業 - 其他類報表 - 查核失能程度通知函 - 查詢頁面 (balp0d3s0l.jsp)
 * 
 * @author KIYOMI
 */
public class MonthlyRpt30Action extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(MonthlyRpt30Action.class);

    private RptService rptService;

    String LIST_PAGE = "listPage";

    /**
     * 列印作業 - 其他類報表 - 查核失能程度通知函 PDF+EXCEL - 查詢頁面 (balp0d3s0l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 其他類報表 - 查核失能程度通知函 - 查詢頁面 MonthlyRpt30Action.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        MonthlyRpt30Form queryForm = (MonthlyRpt30Form) form;

        try {
            String rptKind = queryForm.getRptKind();
            String payCode = queryForm.getPayCode();
            String issuYm = DateUtility.changeChineseYearMonthType(queryForm.getIssuYm());

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("rptKind", rptKind);
            map.put("payCode", payCode);
            map.put("issuYm", issuYm);

            String fileName = "";

            // 抓取 PDF 報表資料
            List<MonthlyRpt30Case> caseDataList = new ArrayList<MonthlyRpt30Case>();

            // 抓取 EXCEL 報表資料
            List<MonthlyRpt30Case> dataList = new ArrayList<MonthlyRpt30Case>();

            if (rptKind.equals("1")) { // 1. 重新查核失能程度通知函
                fileName = "MonthlyRpt30_K01_";
            }
            else { // 2. 通過查核續發失能年金通知函
                fileName = "MonthlyRpt30_K02_";
            }

            // 抓取 PDF 報表資料
            caseDataList = rptService.getMonthlyRpt30DataBy(rptKind, payCode, issuYm);

            // 抓取 EXCEL 報表資料
            dataList = rptService.getMonthlyRpt30ExcelDataBy(rptKind, payCode, issuYm);

            if ((caseDataList == null || caseDataList.size() == 0) && (dataList == null || dataList.size() == 0)) {

                // 抓取已產過的報表
                File[] fileList = ReportUtility.listFileAll(fileName);
                if (fileList != null && fileList.length > 0) {

                    List<String> files = new ArrayList<String>();
                    for (File existFile : fileList) {
                        files.add(existFile.getName());
                    }
                    session.setAttribute(ConstantKey.MONTHLY_RPT_30_FILE_LIST, files);
                    return mapping.findForward(LIST_PAGE);

                }
                else {
                    // 設定 無查詢資料 訊息
                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                    queryForm.reset(mapping, request);
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
                }

            }
            else {

                // 產製PDF報表
                if (caseDataList != null || caseDataList.size() > 0) {

                    ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                    MonthlyRpt30Report report = new MonthlyRpt30Report();
                    baoOutput = report.doReport(userData, caseDataList, map);

                    String printDate = DateUtility.getNowChineseDateTime(false);

                    // 寫入local檔案
                    File file = new File(PropertyHelper.getProperty("rpt.path") + Encode.forJava(fileName) + Encode.forJava(issuYm) + "_" + Encode.forJava(printDate) + ".pdf");
                    OutputStream outputStream = null;
                    try {
                        outputStream = new FileOutputStream(file);
                        baoOutput.writeTo(outputStream);
                    }
                    finally {
                        ExceptionUtil.safeColse(outputStream);
                        ExceptionUtil.safeColse(baoOutput);
                    }

                }

                // 產生EXCEL報表
                if (dataList != null || dataList.size() > 0) {

                    String excelTyp = "";
                    if (StringUtils.equals("1", rptKind)) {
                        excelTyp = "MonthlyRpt30ExcelK01.xls";
                    }
                    else {
                        excelTyp = "MonthlyRpt30ExcelK02.xls";
                    }

                    ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();
                    MonthlyRpt30ExcelReport report = new MonthlyRpt30ExcelReport(excelTyp);
                    baoOutput = report.doReport(dataList, map);

                    String printDate = DateUtility.getNowChineseDateTime(false);

                    // 寫入local檔案
                    File file = new File(PropertyHelper.getProperty("rpt.path") + Encode.forJava(fileName) + Encode.forJava(issuYm) + "_" + Encode.forJava(printDate) + ".xls");
                    OutputStream outputStream = null;
                    try {
                        outputStream = new FileOutputStream(file);
                        baoOutput.writeTo(outputStream);
                    }
                    finally {
                        ExceptionUtil.safeColse(outputStream);
                        ExceptionUtil.safeColse(baoOutput);
                    }

                }

                // 抓取以產過的報表
                File[] fileList = ReportUtility.listFileAll(fileName);
                if (fileList != null && fileList.length > 0) {

                    List<String> files = new ArrayList<String>();
                    for (File existFile : fileList) {
                        files.add(existFile.getName());
                    }
                    session.setAttribute(ConstantKey.MONTHLY_RPT_30_FILE_LIST, files);
                    return mapping.findForward(LIST_PAGE);

                }
                else {
                    // 設定 無查詢資料 訊息
                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                    queryForm.reset(mapping, request);
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
                }
            }

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("產製 查核失能程度通知函 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public ActionForward downloadFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.info("執行 列印作業 - 其他類報表 - 查核失能程度通知函 - 下載檔案 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        MonthlyRpt30Form queryForm = (MonthlyRpt30Form) form;
        FileInputStream fs = null;
        try {
            String fileName = queryForm.getDownloadFileName();
            List<String> fileNames = (List<String>) session.getAttribute(ConstantKey.MONTHLY_RPT_30_FILE_LIST);
            boolean fileExist = false;
            for(String fn : fileNames) {
            	if(StringUtils.equals(fn, fileName)) {
            		fileExist = true;
            		break;
            	}
            }
            if (StringUtils.isNotBlank(fileName) && fileExist) {
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
            log.error("下載 查核失能程度通知函 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getDownloadErrorMessage());
            return mapping.findForward(LIST_PAGE);
        }
        finally {
            ExceptionUtil.safeColse(fs);
        }
    }

    public ActionForward deleteFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.info("執行 列印作業 - 其他類報表 - 查核失能程度通知函 - 刪除檔案 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        MonthlyRpt30Form queryForm = (MonthlyRpt30Form) form;

        try {

            // 取得勾選資料
            String sFileNameList[] = queryForm.getIndex();
            String sPartFileName = "";

            for (int i = 0; i < sFileNameList.length; i++) {
                String sFileName = sFileNameList[i];

                if (i == 0) {
                    sPartFileName = sFileName.substring(0, 16);
                }

                File file = new File(PropertyHelper.getProperty("rpt.path") + Encode.forJava(sFileName));
                file.delete();
            }

            // 抓取以產過的報表
            File[] fileList = ReportUtility.listFileAll(sPartFileName);
            if (fileList != null && fileList.length > 0) {

                List<String> files = new ArrayList<String>();
                for (File existFile : fileList) {
                    files.add(existFile.getName());
                }
                session.setAttribute(ConstantKey.MONTHLY_RPT_30_FILE_LIST, files);
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
            log.error("刪除 查核失能程度通知函 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getDownloadErrorMessage());
            return mapping.findForward(LIST_PAGE);
        }
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}
