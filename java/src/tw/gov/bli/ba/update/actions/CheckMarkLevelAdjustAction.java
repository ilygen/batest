package tw.gov.bli.ba.update.actions;

import java.rmi.RemoteException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.iisi.SecureToken;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.update.cases.BaappbaseDataUpdateCase;
import tw.gov.bli.ba.update.cases.CheckMarkLevelAdjustCase;
import tw.gov.bli.ba.update.forms.CheckMarkLevelAdjustForm;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.webservices.SingleCheckMarkServiceHttpBindingStub;
import tw.gov.bli.ba.webservices.SingleCheckMarkServiceLocator;
import tw.gov.bli.common.helper.UserSessionHelper;

public class CheckMarkLevelAdjustAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(CheckMarkLevelAdjustAction.class);
    private UpdateService updateService;

    /**
     * 更正作業 - 編審註記程度調整 - 查詢頁面 (bamo0d060c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 編審註記程度調整 - 查詢頁面 CheckMarkLevelAdjustAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        // UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        CheckMarkLevelAdjustForm queryForm = (CheckMarkLevelAdjustForm) form;

        try {
            String apno = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();
            CheckMarkLevelAdjustCase querycase = new CheckMarkLevelAdjustCase();

            // 查詢資料
            List<BaappbaseDataUpdateCase> dataList = updateService.selectBaappbaseDataList(null, apno, "0000", new String[] { "01", "10", "11", "12", "20" }, "in");
            if (dataList == null || dataList.size() == 0) {
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }

            querycase.setFormatApNo(queryForm.getApNo1() + "-" + queryForm.getApNo2() + "-" + queryForm.getApNo3() + "-" + queryForm.getApNo4());
            BeanUtility.copyProperties(querycase, dataList.get(0));
            querycase.setKind(queryForm.getApNo1());

            CaseSessionHelper.setCheckMarkLevelAdjustCase(updateService.getChkListForCheckMarkLevelAdjust(querycase, queryForm.getOrderBy()), request);
            log.debug("執行 更正作業 - 編審註記程度調整- 查詢頁面 CheckMarkLevelAdjustAction.doQuery() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("CheckMarkLevelAdjustAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 更正作業 - 編審註記程度調整 - 清單頁面 (bamo0d061c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 編審註記程度調整 - 確認儲存 CheckMarkLevelAdjustAction.doUpdate() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        CheckMarkLevelAdjustForm queryForm = (CheckMarkLevelAdjustForm) form;

        try {
            String sClearCloseDate = queryForm.getClearCloseDate(); // （是否）結案日期清為空白
            if (sClearCloseDate != null) {
                if (sClearCloseDate.equals("Y")) {
                    log.info("執行 更正作業 - 編審註記程度調整 - 確認儲存 CheckMarkLevelAdjustAction.doUpdate() ... （是否）結案日期清為空白：有勾選");
                }
                else {
                    log.info("執行 更正作業 - 編審註記程度調整 - 確認儲存 CheckMarkLevelAdjustAction.doUpdate() ... （是否）結案日期清為空白：未勾選");
                }
            }

            CheckMarkLevelAdjustCase Case = CaseSessionHelper.getCheckMarkLevelAdjustCase(request);
            updateService.updateBachkfileForCheckMarkLevelAdjust(Case.getApNo(), queryForm.getBaChkFileIdW(), queryForm.getBaChkFileIdO(), userData);
            updateService.updateForCheckMarkLevelAdjust(Case.getApNo(), Case.getRegetCipbMk());

            // 「清空結案日期」欄位，於勾選時，將給付主檔之結案日期欄清為空白。
            if (sClearCloseDate != null) {
                if (sClearCloseDate.equals("Y")) {
                    updateService.updateCloseDateForCheckMarkLevelAdjust(Case.getApNo());
                }
            }

            // 呼叫即時編審WebService
            // String webServiceUrl = (ResourceBundle.getBundle("webServiceUrl")).getString("checkMarkWebServicesUrlForOldage");
            String webServiceUrl = PropertyHelper.getProperty("checkMarkWebServicesUrlForOldage");
            log.info("webServiceUrl: " + webServiceUrl);
            SingleCheckMarkServiceHttpBindingStub binding;
            SingleCheckMarkServiceLocator singleCheckMarkServiceLocator = new SingleCheckMarkServiceLocator();
            singleCheckMarkServiceLocator.setSingleCheckMarkServiceHttpPortEndpointAddress(webServiceUrl);
            binding = (SingleCheckMarkServiceHttpBindingStub) singleCheckMarkServiceLocator.getSingleCheckMarkServiceHttpPort();
            String returnCode = binding.doCheckMark(Case.getApNo(), SecureToken.getInstance().getToken());
            log.debug("執行 更正作業 - 編審註記程度調整 - 確認儲存 - 呼叫即時編審結果... " + returnCode);

            // 設定更新訊息
            if ((ConstantKey.DO_CHECK_MARK_FAIL).equals(returnCode)) {
                // 設定即時編審失敗訊息
                saveMessages(session, CustomMessageHelper.getCheckMarkFailMessage());
            }
            else {
                // 設定更新成功訊息
                saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());
            }

            // 清除之前頁面資料
            CaseSessionHelper.removeCheckMarkLevelAdjustCase(request);
            FormSessionHelper.removeCheckMarkLevelAdjustFormm(request);
            log.debug("執行 更正作業 - 編審註記程度調整 - 確認儲存 CheckMarkLevelAdjustAction.doUpdate() 完成 ... ");
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);

        }
        catch (RemoteException ex) {
            // 清除之前頁面資料
            CaseSessionHelper.removeCheckMarkLevelAdjustCase(request);
            FormSessionHelper.removeCheckMarkLevelAdjustFormm(request);
            // 設定及時編審失敗訊息
            log.error("CheckMarkLevelAdjustAction.doUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(ex));
            saveMessages(session, CustomMessageHelper.getCheckMarkFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("CheckMarkLevelAdjustAction.doUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 更正作業 - 編審註記程度調整 - 清單頁面 (bamo0d061c.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 編審註記程度調整 - 清單頁面 CheckMarkLevelAdjustAction.doBack() 開始 ... ");

        // 清除之前頁面資料
        CaseSessionHelper.removeCheckMarkLevelAdjustCase(request);
        FormSessionHelper.removeCheckMarkLevelAdjustFormm(request);
        log.debug("執行 更正作業 - 編審註記程度調整- 清單頁面 CheckMarkLevelAdjustAction.doBack() 完成 ... ");
        return mapping.findForward(ConstantKey.FORWARD_BACK);
    }

    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }

}
