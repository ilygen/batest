package tw.gov.bli.ba.maint.actions;

import java.util.List;
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
import tw.gov.bli.ba.framework.helper.SystemMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.domain.Bapadchk;
import tw.gov.bli.ba.domain.Bapadcslvl;
import tw.gov.bli.ba.maint.cases.CheckMarkMaintCase;
import tw.gov.bli.ba.maint.forms.CheckMarkMaintDetailForm;
import tw.gov.bli.ba.maint.forms.CheckMarkMaintQueryForm;
import tw.gov.bli.ba.maint.forms.DecisionLevelMaintDetailForm;
import tw.gov.bli.ba.maint.forms.DecisionLevelMaintQueryForm;
import tw.gov.bli.ba.maint.helper.CaseSessionHelper;
import tw.gov.bli.ba.maint.helper.FormSessionHelper;
import tw.gov.bli.ba.services.MaintService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 維護作業 - 決行層級維護作業 - 查詢頁面 (bapa0x031a.jsp)
 * 
 * @author Swim
 */
public class DecisionLevelMaintDetailAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(DecisionLevelMaintDetailAction.class);

    private MaintService maintService;

    /**
     * 維護作業 - 決行層級維護作業 - 查詢頁面 (bapa0x031a.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 決行層級維護作業 - 新增頁面 DecisionLevelMaintDetailAction.doSave() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DecisionLevelMaintDetailForm detailForm = (DecisionLevelMaintDetailForm) form;

        try {
            // 檢查是否有重複資料
            List<Bapadcslvl> checkList = maintService.getDecisionLevelMaintQueryCaseBy(detailForm.getPayCode(), detailForm.getPayKind(), detailForm.getRechklvl());

            if (checkList.size() > 0) {
                // 設定該筆資料已存在訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultToExistMessage());

                return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
            }

            if (detailForm != null) {
                Bapadcslvl detailData = new Bapadcslvl();
                BeanUtility.copyProperties(detailData, detailForm);

                // 存檔
                maintService.saveDecisionLevelMaintData(detailData, userData);

                // 設定存檔成功訊息
                saveMessages(session, DatabaseMessageHelper.getSaveSuccessMessage());
            }

            log.debug("執行 維護作業 - 決行層級維護作業 - 新增頁面  DecisionLevelMaintDetailAction.doSave() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DecisionLevelMaintDetailAction.doSave() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 維護作業 - 決行層級維護作業 - 修改頁面 (bapa0x033c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 決行層級維護作業 - 修改頁面 DecisionLevelMaintDetailAction.doConfirm() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DecisionLevelMaintDetailForm detailForm = (DecisionLevelMaintDetailForm) form;

        try {
            if (detailForm != null) {
                Bapadcslvl detailData = new Bapadcslvl();
                BeanUtility.copyProperties(detailData, detailForm);

                if (StringUtils.defaultString(detailForm.getEchk()).equals("true")) {
                    detailData.setEchkLevel("1");
                }
                else {
                    detailData.setEchkLevel("");
                }

                if (StringUtils.defaultString(detailForm.getOchk()).equals("true")) {
                    detailData.setOchkLevel("1");
                }
                else {
                    detailData.setOchkLevel("");
                }

                if (StringUtils.defaultString(detailForm.getWchk()).equals("true")) {
                    detailData.setWchkLevel("1");
                }
                else {
                    detailData.setWchkLevel("");
                }

                // 存檔
                maintService.updateDecisionLevelMaintData(detailData, userData);

                // 更新清單資料
                DecisionLevelMaintQueryForm queryForm = (DecisionLevelMaintQueryForm) FormSessionHelper.getDecisionLevelMaintQueryForm(request);
                List<Bapadcslvl> applyList = maintService.getDecisionLevelMaintQueryCaseBy(queryForm.getPayCode(), null, null);
                CaseSessionHelper.setDecisionLevelMaintQueryCase(applyList, request);

                // 設定存檔成功訊息
                saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());
            }

            log.debug("執行 維護作業 - 決行層級維護作業 - 修改頁面  DecisionLevelMaintDetailAction.doConfirm() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DecisionLevelMaintDetailAction.doConfirm() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 維護作業 - 決行層級維護作業 - 修改頁面 (bapa0x033c.jsp) <br>
     * 按下「刪除」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 決行層級維護作業 - 修改頁面 DecisionLevelMaintDetailAction.doDelete() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DecisionLevelMaintDetailForm detailForm = (DecisionLevelMaintDetailForm) form;

        try {

            if (detailForm != null) {
                Bapadcslvl detailData = new Bapadcslvl();
                BeanUtility.copyProperties(detailData, detailForm);

                // 刪檔
                maintService.deleteDecisionLevelMaintCaseBy(detailData);

                // 更新清單資料
                DecisionLevelMaintQueryForm queryForm = (DecisionLevelMaintQueryForm) FormSessionHelper.getDecisionLevelMaintQueryForm(request);
                List<Bapadcslvl> applyList = maintService.getDecisionLevelMaintQueryCaseBy(queryForm.getPayCode(), null, null);
                CaseSessionHelper.setDecisionLevelMaintQueryCase(applyList, request);

                // 設定刪檔成功訊息
                saveMessages(session, DatabaseMessageHelper.getDeleteSuccessMessage());
            }

            log.debug("執行 維護作業 - 決行層級維護作業 - 修改頁面  DecisionLevelMaintDetailAction.doDelete() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_DELETE_SUCCESS);
        }
        catch (Exception e) {
            // 設定失敗訊息
            log.error("DecisionLevelMaintDetailAction.doDelete() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_DELETE_FAIL);
        }
    }

    public void setMaintService(MaintService maintService) {
        this.maintService = maintService;
    }
}
