package tw.gov.bli.ba.rpt.actions;

import java.io.ByteArrayOutputStream;
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
import tw.gov.bli.ba.rpt.cases.ReviewFeeReceiptRpt01DataCase;
import tw.gov.bli.ba.rpt.forms.ReviewFeeReceiptRpt01Form;
import tw.gov.bli.ba.rpt.report.ReviewFeeReceiptRpt01Report;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 列印作業 - 複檢費用申請作業 - 勞保失能年金給付複檢費用審核給付清單 - 查詢頁面 (bare0d040l.jsp)
 * 
 * @author Evelyn Hsu
 */

public class ReviewFeeReceiptRpt01Action extends BaseDispatchAction {
    
    private static Log log = LogFactory.getLog(ReviewFeeReceiptRpt01Action.class);
    private RptService rptService;
    
    /**
     * 列印作業 - 複檢費用審核給付清單報表 - 查詢頁面 (bare0d040l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 複檢費用審核給付清單報表 - 查詢頁面 ReviewFeeReceiptRpt01Action.doReport() 開始 ... ");
        
        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        ReviewFeeReceiptRpt01Form queryForm = (ReviewFeeReceiptRpt01Form) form;
        
        
        try {
            String apNo = queryForm.getApNo1()+queryForm.getApNo2()+queryForm.getApNo3()+queryForm.getApNo4();
            
            
            // 受理編號長度不滿 12 碼則不做查詢
            if (StringUtils.length(apNo) != ConstantKey.APNO_LENGTH) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            
            List<ReviewFeeReceiptRpt01DataCase> caseList = rptService.getReviewFeeReceiptRpt01ListDataBy(apNo, queryForm.getApSeq());
            
            if (caseList.size() == 0) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }else{
                
                String printDate = DateUtility.getNowChineseDate();
                
                ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();
                
                ReviewFeeReceiptRpt01Report report = new ReviewFeeReceiptRpt01Report();
                baoOutput = report.doReport(userData, caseList);
                
                // 設定回傳回 Client 端之檔案大小, 若不設定,
                // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                response.setHeader("Content-disposition", "attachment; filename=\"BARE0D040L_" + printDate + ".pdf" + "\"");
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
        }catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("產製 複檢費用受理審核清單 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }
    
    
    
    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}
