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
import tw.gov.bli.ba.rpt.cases.OtherRpt10Case;
import tw.gov.bli.ba.rpt.cases.OtherRpt10ClassCase;
import tw.gov.bli.ba.rpt.forms.OtherRpt10Form;
import tw.gov.bli.ba.rpt.report.OtherRpt10Report;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 列印作業 - 其他類報表 - 穿透案件統計表 - 查詢頁面 (balp0d550l.jsp)
 * 
 * @author Kiyomi
 */
public class OtherRpt10Action extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(OtherRpt10Action.class);

    private RptService rptService;

    /**
     * 列印作業 - 其他類報表 - 穿透案件統計表 - 查詢頁面 (balp0d550l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 其他類報表 - 穿透案件統計表 - 查詢頁面 OtherRpt10Action.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        OtherRpt10Form queryForm = (OtherRpt10Form) form;
        int totalCount = 0;

        try {

            // 取得輸入核定年月之前一個核定年月
            String preIssuYm = DateUtility.calMonth(queryForm.getIssuYm() + "01", -1);
            // 以輸入核定年月取得第二次月核定產生媒體日 (ISSUDATE)
            String issuDate = rptService.selectIssuDateForOtherRpt10("S", DateUtility.changeChineseYearMonthType(queryForm.getIssuYm()));
            // 以輸入核定年月之前一個核定年月取得第二次月核定產生媒體日 (ISSUDATE)
            String preIssuDate = rptService.selectIssuDateForOtherRpt10("S", DateUtility.changeChineseYearMonthType(StringUtils.substring(preIssuYm, 0, 5)));
            
            
            // 抓報表資料
            List<OtherRpt10Case> caseList = rptService.selectOtherRpt10DataBy(queryForm.getIssuYm(), issuDate, preIssuDate);
            
            // 測試
            // for(int i = 0 ; i < 20 ;i++){
            // caseList.add(caseList.get(1));
            // }
            
            totalCount = rptService.selectTotalCountFromOtherRpt10DataBy(queryForm.getIssuYm(), issuDate, preIssuDate);

            // 抓取初核人員種類
            LinkedHashSet<String> chkManSet = new LinkedHashSet<String>();
            for (OtherRpt10Case caseData : caseList) {
                chkManSet.add(caseData.getChkMan());
            }
            final List<String> chkManList = new ArrayList<String>();
            for (String value : chkManSet) {
                chkManList.add(value);
            }
            // 初核人員分類後List
            List<OtherRpt10ClassCase> printList = new ArrayList<OtherRpt10ClassCase>();

            for (String value : chkManList) {
                // 分類
                List<OtherRpt10Case> classList = new ArrayList<OtherRpt10Case>();
                OtherRpt10ClassCase printData = new OtherRpt10ClassCase();

                for (OtherRpt10Case caseData : caseList) {
                    if (StringUtils.equalsIgnoreCase(value, caseData.getChkMan())) {                                              
                        classList.add(caseData);
                    }
                }

                printData.setChkMan(value);
                printData.setChkManOtherRptDataList(classList);

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

                OtherRpt10Report report = new OtherRpt10Report();
                baoOutput = report.doReport(userData, printList, queryForm.getIssuYm(), "S", totalCount);

                String printDate = DateUtility.getNowChineseDate();

                // 設定回傳回 Client 端之檔案大小, 若不設定,
                // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                response.setHeader("Content-disposition", "attachment; filename=\"OtherRpt10_" + printDate + ".pdf" + "\"");
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
            log.error("產製 穿透案件統計表 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}
