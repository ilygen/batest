package tw.gov.bli.ba.bj.actions;

import java.util.ArrayList;
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
import tw.gov.bli.ba.bj.cases.CompPaymentCase;
import tw.gov.bli.ba.bj.forms.CompPaymentForm;
import tw.gov.bli.ba.bj.helper.CaseSessionHelper;
import tw.gov.bli.ba.bj.helper.FormSessionHelper;
import tw.gov.bli.ba.domain.Badapr;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.services.BjService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 批次作業 - 補正核付資料作業 - 失能年金補正核付作業
 * 
 * @author Eddie
 *
 */
public class SurvivorPaymentAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(SurvivorPaymentAction.class);
	private BjService bjService;

	/**
	 * 批次作業 - 補正核付資料作業 - 遺屬年金補正核付作業 - 查詢頁面 (baba0m260f.jsp) <br>
	 * 按下「新增」的動作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		log.debug("執行 批次作業 - 補正核付資料作業 - 遺屬年金補正核付作業 - 查詢頁面 SurvivorPaymentAction.doInsert() 開始 ... ");

		HttpSession session = request.getSession();
		UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
		CompPaymentForm queryForm = (CompPaymentForm) form;

		String apNo = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();
		String issuYm = bjService.qryChkIssuym(queryForm.getApNo1());
		
		if (StringUtils.isBlank(issuYm)) {
			saveMessages(session, DatabaseMessageHelper.getnoDataWithPayCodeForSurvivorPaymentMessage());
			return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
		}

		List<Badapr> badaprList = bjService.qryChkManPay(apNo, issuYm);
		
		if(badaprList.size() > 0 && badaprList != null) {
			saveMessages(session, DatabaseMessageHelper.noExecuteSurvivorPayment());
			return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
		}else {
			List<CompPaymentCase> qryAddManPayList = bjService.qryAddManPayListForSurvivor(apNo, issuYm);
			if (qryAddManPayList.size() > 0 && qryAddManPayList != null) {
				CaseSessionHelper.setCompPaymentCase(qryAddManPayList, request);
			}else {
				saveMessages(session, DatabaseMessageHelper.getnoDataWithApNoForSurvivorPaymentMessage());
				return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
			}
		}
		return mapping.findForward(ConstantKey.FORWARD_QUERY_DETAIL);
	}

	/**
	 * 批次作業 - 補正核付資料作業 - 遺屬年金補正核付作業 - 查詢頁面 (baba0m260f.jsp) <br>
	 * 按下「修改」的動作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doModify(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		log.debug("執行 批次作業 - 補正核付資料作業 - 遺屬年金補正核付作業 - 查詢頁面 SurvivorPaymentAction.doModify() 開始 ... ");

		HttpSession session = request.getSession();
		UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
		CompPaymentForm queryForm = (CompPaymentForm) form;

		String apNo = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();
		String issuYm = bjService.qryChkIssuym(queryForm.getApNo1());
		
		if (StringUtils.isBlank(issuYm)) {
			saveMessages(session, DatabaseMessageHelper.getnoDataWithPayCodeForSurvivorPaymentMessage());
			return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
		}
		
		List<CompPaymentCase> qryUpdManPayList = bjService.qryUpdManPayList(apNo, issuYm);

		if (qryUpdManPayList.size() > 0 && qryUpdManPayList != null) {
			CaseSessionHelper.setCompPaymentCase(qryUpdManPayList, request);
		} else {
			saveMessages(session, DatabaseMessageHelper.getnoDataWithApNoForSurvivorPaymentMessage());
			return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
		}
		return mapping.findForward(ConstantKey.FORWARD_QUERY_DETAIL_02);
	}

	/**
	 * 批次作業 - 補正核付資料作業 - 遺屬年金補正核付新增作業頁面 (baba0m261a.jsp) <br>
	 * 按下「確認」的動作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward insertPageDoConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		log.debug("執行 批次作業 - 補正核付資料作業 - 遺屬年金補正核付新增作業頁面 SurvivorPaymentAction.insertPageDoConfirm() 開始 ... ");

		HttpSession session = request.getSession();
		UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
		CompPaymentForm queryForm = (CompPaymentForm) form;
		List<CompPaymentCase> compPaymentCaseList = CaseSessionHelper.getCompPaymentCase(request);
		
		CompPaymentCase compPaymentCase = setCompPaymentCase(userData, queryForm, "A", bjService.qryChkIssuym(queryForm.getApNo1()), queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4());
		
		try {

			if (StringUtils.equals(compPaymentCaseList.get(0).getMtestMk(), "F")) {
				bjService.updManPayDataList(userData, compPaymentCase);
			} else {
				bjService.addManPayDataList(userData, compPaymentCase);
			}

			List<String> upCol = getDifCol(compPaymentCaseList, queryForm);
			
			bjService.insertLogForCompPayment(upCol, compPaymentCaseList, compPaymentCase);

		} catch (Exception e) {
			// 失敗訊息
			log.error("SurvivorPaymentAction.insertPageDoConfirm() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
			return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
		}

		saveMessages(session, DatabaseMessageHelper.successInsertForSurvivorPayment());
		log.debug("執行 批次作業 - 補正核付資料作業 - 遺屬年金補正核付新增作業頁面 SurvivorPaymentAction.insertPageDoConfirm() 完成 ... ");
		return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);
	}

	/**
	 * 批次作業 - 補正核付資料作業 - 遺屬年金補正核付修改作業頁面 (baba0m262c.jsp) <br>
	 * 按下「確認」的動作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward modifyPageDoConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		log.debug("執行 批次作業 - 補正核付資料作業 - 遺屬年金補正核付修改作業頁面 SurvivorPaymentAction.modifyPageDoConfirm() 開始 ... ");

		HttpSession session = request.getSession();
		UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
		CompPaymentForm queryForm = (CompPaymentForm) form;
		List<CompPaymentCase> compPaymentCaseList = CaseSessionHelper.getCompPaymentCase(request);
		CompPaymentCase compPaymentCase = setCompPaymentCase(userData, queryForm, "U", bjService.qryChkIssuym(queryForm.getApNo1()), queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4());

		try {
			
			bjService.updManPayDataListForSurvivor2(userData, compPaymentCase);
			
			List<String> upCol = getDifCol(compPaymentCaseList, queryForm);
			
			bjService.insertLogForCompPayment(upCol, compPaymentCaseList, compPaymentCase);
			
		} catch(Exception e) {
			// 失敗訊息
			log.error("SurvivorPaymentAction.modifyPageDoConfirm() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
			return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
		}
		
		saveMessages(session, DatabaseMessageHelper.successModifyForSurvivorPayment());
		log.debug("執行 批次作業 - 補正核付資料作業 - 遺屬年金補正核付修改作業頁面 SurvivorPaymentAction.modifyPageDoConfirm() 完成 ... ");
		return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);
	}
	/**
	 * 批次作業 - 補正核付資料作業 - 遺屬年金補正核付作業 -新增頁面和修改頁面 (baba0m261a.jsp, baba0m262c.jsp) <br>
	 * 按下「返回」的動作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		log.debug(" 批次作業 - 補正核付資料作業 - 遺屬年金補正核付作業 SurvivorPaymentAction.doBack() 開始 ... ");
		HttpSession session = request.getSession();
		try {
			CaseSessionHelper.removeCompPaymentCase(request);
			FormSessionHelper.removeCompPaymentForm(request);
			log.debug("執行 批次作業 - 補正核付資料作業 - 遺屬年金補正核付作業 -新增頁面和修改頁面  SurvivorPaymentAction.doBack() 完成 ... ");
			return mapping.findForward(ConstantKey.FORWARD_BACK);
		} catch (Exception e) {
			// 設定查詢失敗訊息
			log.error("SurvivorPaymentAction.doBack() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
			return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
		}
	}

	public CompPaymentCase setCompPaymentCase(UserBean userData, CompPaymentForm queryForm, String status, String issuYm, String apNo) {
		
		CompPaymentCase compPaymentCase = new CompPaymentCase();
		
		compPaymentCase.setChkDate(DateUtility.changeDateToAD(queryForm.getChkDate()));
		compPaymentCase.setAplPayDate(DateUtility.changeDateToAD(queryForm.getAplPayDate()));
		compPaymentCase.setRemitDate(DateUtility.changeDateToAD(queryForm.getRemitDate()));
		compPaymentCase.setApNo(apNo);
		compPaymentCase.setIssuYm(issuYm);
		compPaymentCase.setProcTime(DateUtility.getNowWestDateTime(true));
		compPaymentCase.setEmpNo(userData.getEmpNo());
		compPaymentCase.setStatus(status);
		
		return compPaymentCase;
	}
	
	public List<String> getDifCol(List<CompPaymentCase> compPaymentCaseList, CompPaymentForm queryForm) {
		List<String> upCol = new ArrayList<String>();
		
		String bChkDate = DateUtility.changeDateToAD(compPaymentCaseList.get(0).getChkDate());
		String bAplPayDate = DateUtility.changeDateToAD(compPaymentCaseList.get(0).getAplPayDate());
		String bRemitDate = DateUtility.changeDateToAD(compPaymentCaseList.get(0).getRemitDate());
		String aChkDate = DateUtility.changeDateToAD(queryForm.getChkDate());
		String aAplPayDate = DateUtility.changeDateToAD(queryForm.getAplPayDate());
		String aRemitDate = DateUtility.changeDateToAD(queryForm.getRemitDate());
		
		if (!StringUtils.equals(bChkDate, aChkDate)) {
			upCol.add("核定日期");
		}

		if (!StringUtils.equals(bAplPayDate, aAplPayDate)) {
			upCol.add("核付日期");
		}

		if (!StringUtils.equals(bRemitDate, aRemitDate)) {
			upCol.add("入帳日期");
		}
		
		return upCol;
	}
	
	public void setBjService(BjService bjService) {
		this.bjService = bjService;
	}

}
