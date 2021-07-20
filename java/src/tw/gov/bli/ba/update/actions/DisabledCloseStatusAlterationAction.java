package tw.gov.bli.ba.update.actions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.helper.LoggingHelper;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.update.cases.CloseStatusAlterationCase;
import tw.gov.bli.ba.update.cases.CloseStatusAlterationCasePayeeList;
import tw.gov.bli.ba.update.forms.CloseStatusAlterationForm;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;
import tw.gov.bli.common.util.DateUtil;

/**
 * 更正作業 - 結案狀態變更作業 - 失能年金結案狀態變更作業 - 查詢頁面 (bamo0d210c.jsp)
 * 
 * @author EDDIE
 */
public class DisabledCloseStatusAlterationAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(DisabledCloseStatusAlterationAction.class);
	private static final String FORWARD_QUERY_SUCCESS = "querySuccess";
	private static final String FORWARD_QUERY_FAIL = "queryFail";
	private static final String FORWARD_UPDATE_SUCCESS = "updateSuccess";
	private static final String FORWARD_UPDATE_FAIL = "updateFail";
	private static final String FORWARD_BACK = "back";
	private static final String FORWARD_FAIL = "fail";
	private UpdateService updateService;

	/**
	 * 更正作業 - 結案狀態變更作業 - 失能年金結案狀態變更作業 - 查詢頁面 (bamo0d210c.jsp) <br>
	 * 按下「確認」的動作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doQueryList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug(" 更正作業 - 結案狀態變更作業 - 失能年金結案狀態變更作業 - 查詢頁面 DisabledCloseStatusAlterationAction.doQueryList() 開始 ... ");
		HttpSession session = request.getSession();
		UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

		CloseStatusAlterationForm queryForm = (CloseStatusAlterationForm) form;
		try {
			String sApNo = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();
			CloseStatusAlterationCase querycase = new CloseStatusAlterationCase();

			// 查詢資料
			List<CloseStatusAlterationCase> dataList = new ArrayList<CloseStatusAlterationCase>();

			String payeeDataCount = updateService.getPayeeDataCountForOldAgeAndDisabledCloseStatusAlteration(sApNo);
			if (Integer.parseInt(payeeDataCount) == 0) {
				String sApNoFormat = sApNo.substring(0, 1) + "-" + sApNo.substring(1, 2) + "-" + sApNo.substring(2, 7)
						+ "-" + sApNo.substring(7, 12);
				saveMessages(session,
						DatabaseMessageHelper.getNoDataWithApNoForOldAgeAndDisabledCloseStatusAlterationMessage(sApNoFormat));
				return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
			} else {
				dataList = updateService.selectPayeeListForOldAgeAndDisabledCloseStatusAlteration(sApNo);
			}

			if (dataList == null || dataList.isEmpty()) {
				saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
				return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
			}

			CaseSessionHelper.setCloseStatusAlterationCase(dataList, request);

			log.debug(
					"執行 更正作業 - 結案狀態變更作業 - 失能年金結案狀態變更作業 - 查詢頁面 DisabledCloseStatusAlterationAction.doQueryList() 完成 ... ");
			return mapping.findForward(FORWARD_QUERY_SUCCESS);
		} catch (Exception e) {
			// 設定查詢失敗訊息
			log.error("InheritorRegisterAction.doQueryList() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
			return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
		}
	}

	/**
	 * 更正作業 - 結案狀態變更作業 - 失能年金結案狀態變更作業 - 修改頁面 (bamo0d211c.jsp) <br>
	 * 按下「確認」的動作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug(" 更正作業 - 結案狀態變更作業 - 失能年金結案狀態變更作業 - 修改頁面 DisabledCloseStatusAlterationAction.doUpdate() 開始 ... ");
		HttpSession session = request.getSession();
		UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

		CloseStatusAlterationForm queryForm = (CloseStatusAlterationForm) form;
		try {

			String apNo = "";
			String caseTyp = "";

			String[] sChkList = queryForm.getIdForConfirm().split(",");

			for (int i = 0; i < 1; i++) {
				apNo = new String(sChkList[i].split(";")[0]);
				caseTyp = new String(sChkList[i].split(";")[2]);
			}

			for (String recId : sChkList) {
				String sApNo = new String(recId.split(";")[0]);
				String sSeqNo = new String(recId.split(";")[1]);

				updateService.updateDataForOldAgeAndDsiabledCloseStatusAlteration(sApNo, userData.getEmpNo(), caseTyp);
			}

			saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());
			log.debug(
					"執行 更正作業 - 結案狀態變更作業 - 失能年金結案狀態變更作業 - 修改頁面 DisabledCloseStatusAlterationAction.doUpdate() 完成 ... ");
			return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
		} catch (Exception e) {
			// 設定查詢失敗訊息
			log.error("DisabledCloseStatusAlterationAction.doUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
			return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
		}
	}

	/**
	 * 更正作業 - 結案狀態變更作業 - 失能年金結案狀態變更作業 - 修改頁面 (bamo0d211c.jsp) <br>
	 * 按下「返回」的動作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug(" 更正作業 - 結案狀態變更作業 - 失能年金結案狀態變更作業 DisabledCloseStatusAlterationAction.doBack() 開始 ... ");
		HttpSession session = request.getSession();

		try {
			CaseSessionHelper.removeCloseStatusAlterationCase(request);
			FormSessionHelper.removeCloseStatusAlterationForm(request);
			log.debug("執行 更正作業 - 結案狀態變更作業 - 失能年金結案狀態變更作業 - 修改頁面 DisabledCloseStatusAlterationAction.doBack() 完成 ... ");
			return mapping.findForward(FORWARD_BACK);
		} catch (Exception e) {
			// 設定查詢失敗訊息
			log.error("DisabledCloseStatusAlterationAction.doBack() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
			return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
		}
	}

	public void setUpdateService(UpdateService updateService) {
		this.updateService = updateService;
	}

}
