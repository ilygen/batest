package tw.gov.bli.ba.rpt.actions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
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
import tw.gov.bli.ba.rpt.cases.AuditRpt01Case;
import tw.gov.bli.ba.rpt.cases.AuditRpt01ClassCase;
import tw.gov.bli.ba.rpt.cases.DecisionRpt03Case;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt29Case;
import tw.gov.bli.ba.rpt.forms.AuditRpt01Form;
import tw.gov.bli.ba.rpt.report.AuditRpt01ExcelReport;
import tw.gov.bli.ba.rpt.report.AuditRpt01Report;
import tw.gov.bli.ba.rpt.report.DecisionRpt03Report;
import tw.gov.bli.ba.rpt.report.MonthlyRpt29ExcelReport;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 列印作業 - 稽催管制相關報表 - 逾期未決行案件管制表 - 查詢頁面 (balp0d420l.jsp)
 * 
 * @author Noctis
 */
public class AuditRpt01Action extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(AuditRpt01Action.class);

    private RptService rptService;

    /**
     * 列印作業 - 稽催管制相關報表 - 逾期未決行案件管制表 - 查詢頁面 (balp0d420l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 決行作業相關報表 - 歸檔清單點交清冊 - 查詢頁面 AuditRpt01Action.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        AuditRpt01Form queryForm = (AuditRpt01Form) form;

        try {

            if (queryForm.getPrtTyp().equals("1")) {
                // 抓報表資料
                List<AuditRpt01Case> caseList = rptService.selectAuditRpt01DataBy(queryForm.getPayCode(), queryForm.getEndYm());
                // 測試
                // for(int i = 0 ; i < 20 ;i++){
                // caseList.add(caseList.get(1));
                // }

                // 抓取初核人員種類
                LinkedHashSet<String> chkManSet = new LinkedHashSet<String>();
                for (AuditRpt01Case caseData : caseList) {
                    chkManSet.add(caseData.getChkMan());
                }
                final List<String> chkManList = new ArrayList<String>();
                for (String value : chkManSet) {
                    chkManList.add(value);
                }
                // 初核人員分類後List
                List<AuditRpt01ClassCase> printList = new ArrayList<AuditRpt01ClassCase>();

                for (String value : chkManList) {
                    // 分類
                    List<AuditRpt01Case> classList = new ArrayList<AuditRpt01Case>();
                    AuditRpt01ClassCase printData = new AuditRpt01ClassCase();

                    for (AuditRpt01Case caseData : caseList) {
                        if (StringUtils.equalsIgnoreCase(value, caseData.getChkMan())) {
                            classList.add(caseData);
                        }
                    }

                    printData.setChkMan(value);
                    printData.setChkManAuditRptDataList(classList);

                    printList.add(printData);
                }

                // 非類初核人員List

                if (caseList.size() == 0) {
                    // 設定 無查詢資料 訊息
                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
                }
                else {

                    ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                    AuditRpt01Report report = new AuditRpt01Report();
                    baoOutput = report.doReport(userData, printList, queryForm.getEndYm(), queryForm.getPayCode());

                    String printDate = DateUtility.getNowChineseDate();

                    // 設定回傳回 Client 端之檔案大小, 若不設定,
                    // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                    response.setHeader("Content-disposition", "attachment; filename=\"AuditRpt01_" + printDate + ".pdf" + "\"");
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
            else {

                // 抓報表資料
                List<AuditRpt01Case> caseList = rptService.selectAuditRpt01ExcelDataBy(queryForm.getPayCode(), queryForm.getEndYm());

                if (caseList == null) {
                    // 設定 無查詢資料 訊息
                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                    queryForm.reset(mapping, request);
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);

                }
                else {
                    // 產生EXCEL報表
                    ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();
                    AuditRpt01ExcelReport report = new AuditRpt01ExcelReport();
                    baoOutput = report.doReport(caseList, queryForm.getEndYm(), queryForm.getPayCode());

                    // 設定回傳回 Client 端之檔案大小, 若不設定,
                    // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                    response.setHeader("Content-disposition", "attachment; filename=\"" + new String("AuditRpt01_Excel_".getBytes("Big5"), "ISO8859_1") + DateUtility.getNowWestDate() + ".xls" + "\"");
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
                        log.debug("列印作業 - 稽催管制相關報表 - 逾期未決行案件管制表(非當月) - 產製 AuditRpt01Action.doReportExcel() 完成 ... ");
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
            log.error("產製 逾期未決行案件管制表 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}
