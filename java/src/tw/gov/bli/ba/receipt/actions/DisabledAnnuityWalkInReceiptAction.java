package tw.gov.bli.ba.receipt.actions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.iisi.SecureToken;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Baap0d040;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.receipt.cases.DisabledAnnuityReceiptEvtCase;
import tw.gov.bli.ba.receipt.cases.DisabledAnnuityReceiptFamCase;
import tw.gov.bli.ba.receipt.forms.DisabledAnnuityWalkInReceiptQueryForm;
import tw.gov.bli.ba.receipt.helper.CaseSessionHelper;
import tw.gov.bli.ba.receipt.helper.FormSessionHelper;
import tw.gov.bli.ba.services.ReceiptService;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.webservices.SingleCheckMarkServiceHttpBindingStub;
import tw.gov.bli.ba.webservices.SingleCheckMarkServiceLocator;
import tw.gov.bli.common.helper.UserSessionHelper;

public class DisabledAnnuityWalkInReceiptAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(DisabledAnnuityWalkInReceiptAction.class);

	// 受理作業 - 失能年金臨櫃受理作業 - 整案新增作業確認成功
	private static final String FORWARD_SAVE_ALL_SUCCESS = "saveAllSuccess";
	// 受理作業 - 失能年金臨櫃受理作業 - 整案新增作業確認失敗
	private static final String FORWARD_SAVE_ALL_FAIL = "saveAllFail";
	// 案件操作模式 - 新增
	private static final String APP_ACTION_TYP_INSERT = "insertMode";
	// 案件操作模式 - 修改
	private static final String APP_ACTION_TYP_UPDATE = "updateMode";

	private ReceiptService receiptService;
	private SelectOptionService selectOptionService;

	/**
	 * 受理作業 - 失能年金臨櫃受理作業 - 登錄新增作業
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward prepareInsertBa(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("執行 受理作業 - 失能年金臨櫃受理作業 - 登錄新增作業 DisabledAnnuityWalkInReceiptAction.prepareInsertBa() 開始 ... ");

		HttpSession session = request.getSession();

		try {
			DisabledAnnuityWalkInReceiptQueryForm queryForm = (DisabledAnnuityWalkInReceiptQueryForm) form;
			String procType = queryForm.getProcType();

			// 轉入作業
			if (!StringUtils.equals(procType, "1")) {
				List<Baap0d040> turnInData = receiptService.getTurnInData(queryForm);
				// 查無轉入資料
				if (CollectionUtils.isEmpty(turnInData)) {
					saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
					log.debug(
							"執行 受理作業 - 失能年金臨櫃受理作業 - 登錄新增作業 DisabledAnnuityWalkInReceiptAction.prepareInsertBa() 完成 ... ");
					return mapping.findForward(ConstantKey.FORWARD_FAIL);
				} else {
					// 將它系統轉入資料設定至頁面使用的 form
					queryForm = receiptService.convertTurnInData(queryForm, turnInData.get(0));
				}
			}

			// 取得 遺屬眷屬暫存檔(BAFAMILYTEMP) 暫存檔資料列編號(Sequence.BAFAMILYTEMPID)
			BigDecimal bafamilytempId = receiptService.getNewbafamilytempId();

			// 取得國籍清單
			request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());

			// 眷屬資料清單
			List<DisabledAnnuityReceiptFamCase> famDataList = new ArrayList<DisabledAnnuityReceiptFamCase>();

			// 移除頁面輸入資料
//			FormSessionHelper.removeDisabledAnnuityReceiptForm(request);

			// 將資料存入Session
			CaseSessionHelper.setDisabledAnnuityReceiptBafamilytempId(bafamilytempId, request);
			CaseSessionHelper.setDisabledAnnuityReceiptFamDataList(famDataList, request);

			log.debug("執行 受理作業 - 失能年金臨櫃受理作業 - 登錄新增作業 DisabledAnnuityWalkInReceiptAction.prepareInsertBa() 完成 ... ");

			return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
		} catch (Exception e) {
			// 設定查詢失敗訊息
			log.error("DisabledAnnuityWalkInReceiptAction.prepareInsertBa() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
			return mapping.findForward(ConstantKey.FORWARD_FAIL);
		}
	}

	/**
	 * 受理作業 - 失能年金臨櫃受理作業 - 新增作業確認存檔
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doInsertBa(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("執行 受理作業 - 失能年金臨櫃受理作業 - 新增作業確認存檔 DisabledAnnuityWalkInReceiptAction.doInsertBa() 開始 ... ");

		HttpSession session = request.getSession();
		UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
		String forward = FORWARD_SAVE_ALL_FAIL;
		try {
			DisabledAnnuityWalkInReceiptQueryForm iform = (DisabledAnnuityWalkInReceiptQueryForm) form;
			String procType = iform.getProcType();
			String apNo = iform.getApNoStr();

			// 手動輸入之受理編號不可為 K00000000000
			if (StringUtils.equalsIgnoreCase(apNo, "K00000000000")) {
				// 設定訊息: 受理編號編碼規則錯誤
				saveMessages(session, DatabaseMessageHelper.getApNoErrorMessage());
			} else {
				// 檢查受理編號是否存在
				if (receiptService.checkApNoExist(apNo)) {
					// 設定訊息:新增失敗，此受理號碼已存在！
					saveMessages(session, DatabaseMessageHelper.getApNoExistMessage());
				} else {
					DisabledAnnuityReceiptEvtCase evtCase = new DisabledAnnuityReceiptEvtCase();
					BeanUtility.copyProperties(evtCase, iform);
					// 依畫面輸入欄位條件轉換 事故者資料欄位
					evtCase.setApNo(apNo);
					evtCase = receiptService.transDisabledEvtInputData(evtCase);
					// 取得眷屬暫存檔資料序號
					BigDecimal bafamilytempId = CaseSessionHelper.getDisabledAnnuityReceiptBafamilytempId(request);

					// 新增給付主檔、給付延伸主檔、眷屬檔資料
					receiptService.insertDataForDisabled(userData, evtCase, bafamilytempId);

					// 呼叫即時編審 WebService
					String webServiceUrl = PropertyHelper.getProperty("checkMarkWebServicesUrlForOldage");
					log.info("webServiceUrl: " + webServiceUrl);
					String returnCode = ConstantKey.DO_CHECK_MARK_FAIL;
					try {
						SingleCheckMarkServiceHttpBindingStub binding;
						binding = (SingleCheckMarkServiceHttpBindingStub) new SingleCheckMarkServiceLocator()
								.getSingleCheckMarkServiceHttpPort();
						returnCode = binding.doCheckMark(apNo, SecureToken.getInstance().getToken());
					} catch (Exception e) {
						log.error("DisabledAnnuityWalkInReceiptAction.doInsertBa() 即時編審發生錯誤:"
								+ ExceptionUtility.getStackTrace(e));
					}

					// 設定新增成功訊息
					if (StringUtils.equals(ConstantKey.DO_CHECK_MARK_FAIL, returnCode)) {
						saveMessages(session, CustomMessageHelper.getCheckMarkFailMessage()); // 設定即時編審失敗訊息
					} else {
						saveMessages(session,
								DatabaseMessageHelper.getReceiptSaveSuccessMessage(evtCase.getApNoStrDisplay()));
					}

					forward = FORWARD_SAVE_ALL_SUCCESS;

					// 重新取得頁面資料
					// [
					// 清除所有session資料
					FormSessionHelper.removeDisabledAnnuityWalkInReceiptFamForm(request);
					FormSessionHelper.removeDisabledAnnuityReceiptQryCondForm(request);
					CaseSessionHelper.removeAllDisabledAnnuityReceiptCase(request);
					FormSessionHelper.removeDisabledAnnuityWalkInReceiptQueryForm(request);
					// 設定 form 的 procType 供 baap0d041a.jsp 判斷
					DisabledAnnuityWalkInReceiptQueryForm initDataForm = new DisabledAnnuityWalkInReceiptQueryForm();
					initDataForm.setProcType(procType);
					FormSessionHelper.setDisabledAnnuityWalkInReceiptQueryForm(initDataForm, request);
					
					// 取得 遺屬眷屬暫存檔(BAFAMILYTEMP) 暫存檔資料列編號(Sequence.BAFAMILYTEMPID)
					bafamilytempId = receiptService.getNewbafamilytempId();
					// 取得國籍清單
					request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST,
							selectOptionService.getCountry());
					// 眷屬資料清單
					List<DisabledAnnuityReceiptFamCase> famDataList = new ArrayList<DisabledAnnuityReceiptFamCase>();
					// 將資料存入Session
					CaseSessionHelper.setDisabledAnnuityReceiptBafamilytempId(bafamilytempId, request);
					CaseSessionHelper.setDisabledAnnuityReceiptFamDataList(famDataList, request);
					// ]
				}
			}

			log.debug("執行 受理作業 - 失能年金臨櫃受理作業 - 新增作業確認存檔 DisabledAnnuityWalkInReceiptAction.doInsertBa() 完成 ... ");
			return mapping.findForward(forward);
		} catch (Exception e) {
			// 設定新增失敗訊息
			log.error("DisabledAnnuityWalkInReceiptAction.doInsertBa() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getSaveFailMessage());
			return mapping.findForward(FORWARD_SAVE_ALL_FAIL);
		}
	}

	/**
	 * 受理作業 - 失能年金臨櫃受理作業 - 返回
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("執行 受理作業 - 失能年金臨櫃受理作業 - 返回 DisabledAnnuityWalkInReceiptAction.doBack() 開始 ... ");

		// 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
		FormSessionHelper.removeDisabledAnnuityWalkInReceiptQueryForm(request);
		FormSessionHelper.removeDisabledAnnuityReceiptQryCondForm(request);
		CaseSessionHelper.removeAllDisabledAnnuityReceiptCase(request);

		log.debug("執行 受理作業 - 失能年金臨櫃受理作業 - 返回 DisabledAnnuityWalkInReceiptAction.doBack() 完成 ... ");
		return mapping.findForward(ConstantKey.FORWARD_BACK);
	}

	/**
	 * 受理作業 - 失能年金臨櫃受理作業 - 新增眷屬作業存檔
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doInsertFamData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("執行 受理作業 - 失能年金臨櫃受理作業 - 新增眷屬作業存檔 DisabledAnnuityWalkInReceiptAction.doInsertFamData() 開始 ... ");

		HttpSession session = request.getSession();
		UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
		String forward = ConstantKey.FORWARD_SAVE_FAIL;
		try {
			DisabledAnnuityWalkInReceiptQueryForm iform = (DisabledAnnuityWalkInReceiptQueryForm) form;
			DisabledAnnuityReceiptFamCase caseObj = new DisabledAnnuityReceiptFamCase();
			BeanUtility.copyProperties(caseObj, iform);

			// 保存畫面上已輸入之事故者資料
			DisabledAnnuityWalkInReceiptQueryForm evtForm = FormSessionHelper
					.getDisabledAnnuityWalkInReceiptQueryForm(request);
			evtForm = receiptService.keepInputEvtFormDataForWalkInDisabled(evtForm, iform);
			FormSessionHelper.setDisabledAnnuityWalkInReceiptQueryForm(evtForm, request);

			// 先判斷該案是新增或修改
			if ((APP_ACTION_TYP_INSERT).equals(iform.getActionTyp())) {
				// 檢查輸入之眷屬資料是否已存在(眷屬暫存檔)
				if (receiptService.checkExistFamTempData(caseObj.getBafamilytempId(), caseObj.getFamIdnNo(),
						DateUtility.changeDateType(caseObj.getFamBrDate()), null, null)) {
					// 設定 眷屬資料重複訊息
					saveMessages(session, DatabaseMessageHelper.getSaveFailCauseFamDataExistMessage());
				} else {
					// 依畫面輸入欄位條件轉換 給付主檔部分欄位
					caseObj = receiptService.transDisabledFamInputData(caseObj);
					receiptService.insertDisabledBafamilytempData(caseObj, userData);
					// 設定新增成功訊息
					saveMessages(session, DatabaseMessageHelper.getSaveSuccessMessage());

					forward = ConstantKey.FORWARD_SAVE_SUCCESS;

					// 重新查詢資料
					// [
					// 取得國籍清單
					request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST,
							selectOptionService.getCountry());
					// 眷屬資料清單
					List<DisabledAnnuityReceiptFamCase> famDataList = receiptService
							.getDisabledBafamilytempData(caseObj.getBafamilytempId());

					// 重置頁面輸入資料
					DisabledAnnuityWalkInReceiptQueryForm insertForm = new DisabledAnnuityWalkInReceiptQueryForm();
					BeanUtility.copyProperties(insertForm, iform);
					insertForm.cleanFamData();
					insertForm.setFocusLocation("famNationTyp");

					// 將資料存入Session
					FormSessionHelper.removeDisabledAnnuityWalkInReceiptFamForm(request);
					FormSessionHelper.setDisabledAnnuityWalkInReceiptFamForm(insertForm, request);
					CaseSessionHelper.setDisabledAnnuityReceiptFamDataList(famDataList, request);
					// ]
				}
			} else if ((APP_ACTION_TYP_UPDATE).equals(iform.getActionTyp())) {
				// 檢查輸入之眷屬資料是否已存在(眷屬檔)
				if (receiptService.checkExistFamData(caseObj.getBaappbaseId(), caseObj.getFamIdnNo(),
						DateUtility.changeDateType(caseObj.getFamBrDate()), null, null)) {
					// 設定 眷屬資料重複訊息
					saveMessages(session, DatabaseMessageHelper.getSaveFailCauseFamDataExistMessage());
				} else {
					// 依畫面輸入欄位條件轉換 給付主檔部分欄位
					caseObj = receiptService.transDisabledFamInputData(caseObj);
					receiptService.insertDisabledBafamilyData(caseObj, userData);
					// 設定新增成功訊息
					saveMessages(session, DatabaseMessageHelper.getSaveSuccessMessage());

					forward = ConstantKey.FORWARD_SAVE_SUCCESS;

					// 重新查詢資料
					// [
					// 取得國籍清單
					request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST,
							selectOptionService.getCountry());
					// 取得眷屬資料
					List<DisabledAnnuityReceiptFamCase> famDataList = receiptService
							.getBafamilyDataForDiasbled(caseObj.getBaappbaseId(), caseObj.getApNo());

					// 重置頁面輸入資料
					DisabledAnnuityWalkInReceiptQueryForm insertForm = new DisabledAnnuityWalkInReceiptQueryForm();
					BeanUtility.copyProperties(insertForm, iform);
					insertForm.cleanFamData();
					insertForm.setFocusLocation("famNationTyp");

					// 將資料存入Session
					FormSessionHelper.removeDisabledAnnuityWalkInReceiptFamForm(request);
					FormSessionHelper.setDisabledAnnuityWalkInReceiptFamForm(insertForm, request);
					CaseSessionHelper.setDisabledAnnuityReceiptFamDataList(famDataList, request);
					// ]
				}
			}
			log.debug("執行 受理作業 - 失能年金臨櫃受理作業 - 新增眷屬作業存檔 DisabledAnnuityWalkInReceiptAction.doInsertFamData() 完成 ... ");
			return mapping.findForward(forward);
		} catch (Exception e) {
			// 設定新增失敗訊息
			log.error("DisabledAnnuityWalkInReceiptAction.doInsertFamData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getSaveFailMessage());
			return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
		}
	}

	/**
	 * 受理作業 - 失能年金臨櫃受理作業 - 修改眷屬作業存檔
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doUpdateFamData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("執行 受理作業 - 失能年金臨櫃受理作業 - 修改眷屬作業存檔 DisabledAnnuityWalkInReceiptAction.doUpdateFamData() 開始 ... ");

		HttpSession session = request.getSession();
		UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
		String forward = ConstantKey.FORWARD_UPDATE_FAIL;

		try {
			DisabledAnnuityWalkInReceiptQueryForm iform = (DisabledAnnuityWalkInReceiptQueryForm) form;
			DisabledAnnuityReceiptFamCase caseObj = new DisabledAnnuityReceiptFamCase();
			BeanUtility.copyProperties(caseObj, iform);

			// 保存畫面上已輸入之事故者資料
			DisabledAnnuityWalkInReceiptQueryForm evtForm = FormSessionHelper
					.getDisabledAnnuityWalkInReceiptQueryForm(request);
			evtForm = receiptService.keepInputEvtFormDataForWalkInDisabled(evtForm, iform);
			FormSessionHelper.setDisabledAnnuityWalkInReceiptQueryForm(evtForm, request);

			// 先判斷該案是新增或修改
			if ((APP_ACTION_TYP_INSERT).equals(iform.getActionTyp())) {
				// 檢查輸入之眷屬資料是否已存在
				if (receiptService.checkExistFamTempData(caseObj.getBafamilytempId(), caseObj.getFamIdnNo(),
						DateUtility.changeDateType(caseObj.getFamBrDate()), caseObj.getSeqNo(), "<>")) {
					// 設定 眷屬資料重複訊息
					saveMessages(session, DatabaseMessageHelper.getSaveFailCauseFamDataExistMessage());
				} else {
					// 依畫面輸入欄位條件轉換 給付主檔部分欄位
					caseObj = receiptService.transDisabledFamInputData(caseObj);
					receiptService.updateDisabledBafamilytempData(caseObj, userData);
					// 設定修改成功訊息
					saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());

					forward = ConstantKey.FORWARD_UPDATE_SUCCESS;

					// 重新查詢資料
					// [
					// 取得國籍清單
					request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST,
							selectOptionService.getCountry());
					// 眷屬資料清單
					List<DisabledAnnuityReceiptFamCase> famDataList = receiptService
							.getDisabledBafamilytempData(caseObj.getBafamilytempId());

					// 重置頁面輸入資料
					DisabledAnnuityWalkInReceiptQueryForm insertForm = new DisabledAnnuityWalkInReceiptQueryForm();
					BeanUtility.copyProperties(insertForm, iform);
					insertForm.cleanFamData();
					insertForm.setFocusLocation("famNationTyp");

					// 將資料存入Session
					FormSessionHelper.removeDisabledAnnuityWalkInReceiptFamForm(request);
					FormSessionHelper.setDisabledAnnuityWalkInReceiptFamForm(insertForm, request);
					CaseSessionHelper.setDisabledAnnuityReceiptFamDataList(famDataList, request);
					// ]
				}
			} else if ((APP_ACTION_TYP_UPDATE).equals(iform.getActionTyp())) {
				// 檢查輸入之眷屬資料是否已存在(眷屬檔)
				if (receiptService.checkExistFamData(caseObj.getBaappbaseId(), caseObj.getFamIdnNo(),
						DateUtility.changeDateType(caseObj.getFamBrDate()), caseObj.getSeqNo(), "<>")) {
					// 設定 眷屬資料重複訊息
					saveMessages(session, DatabaseMessageHelper.getSaveFailCauseFamDataExistMessage());
				} else {
					// 依畫面輸入欄位條件轉換 給付主檔部分欄位
					caseObj = receiptService.transDisabledFamInputData(caseObj);
					receiptService.updateDisabledBafamilyData(caseObj, userData);
					// 設定修改成功訊息
					saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());

					forward = ConstantKey.FORWARD_UPDATE_SUCCESS;

					// 重新查詢資料
					// [
					// 取得國籍清單
					request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST,
							selectOptionService.getCountry());
					// 取得眷屬資料
					List<DisabledAnnuityReceiptFamCase> famDataList = receiptService
							.getBafamilyDataForDiasbled(caseObj.getBaappbaseId(), caseObj.getApNo());

					// 重置頁面輸入資料
					DisabledAnnuityWalkInReceiptQueryForm insertForm = new DisabledAnnuityWalkInReceiptQueryForm();
					BeanUtility.copyProperties(insertForm, iform);
					insertForm.cleanFamData();
					insertForm.setFocusLocation("famNationTyp");

					// 將資料存入Session
					FormSessionHelper.removeDisabledAnnuityWalkInReceiptFamForm(request);
					FormSessionHelper.setDisabledAnnuityWalkInReceiptFamForm(insertForm, request);
					CaseSessionHelper.setDisabledAnnuityReceiptFamDataList(famDataList, request);
					// ]
				}

			}
			log.debug("執行 受理作業 - 失能年金臨櫃受理作業 - 修改眷屬作業存檔 DisabledAnnuityWalkInReceiptAction.doUpdateFamData() 完成 ... ");
			return mapping.findForward(forward);
		} catch (Exception e) {
			// 設定修改失敗訊息
			log.error("DisabledAnnuityReceiptAction.doUpdateFamData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
			return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
		}
	}

	/**
	 * 受理作業 - 失能年金臨櫃受理作業 - 刪除眷屬作業存檔
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doDeleteFamData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("執行 受理作業 - 失能年金臨櫃受理作業 - 刪除眷屬作業存檔 DisabledAnnuityWalkInReceiptAction.doDeleteFamData() 開始 ... ");

		HttpSession session = request.getSession();
		UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
		String forward = ConstantKey.FORWARD_DELETE_FAIL;

		try {
			DisabledAnnuityWalkInReceiptQueryForm iform = (DisabledAnnuityWalkInReceiptQueryForm) form;

			// 先判斷該案是新增或修改
			if ((APP_ACTION_TYP_INSERT).equals(iform.getActionTyp())) {
				// 刪除該筆眷屬資料
				receiptService.deleteBafamilytempData(iform.getBafamilytempId(), iform.getSeqNo());

				// 設定刪除成功訊息
				saveMessages(session, DatabaseMessageHelper.getDeleteSuccessMessage());

				// 設定 forward
				forward = ConstantKey.FORWARD_DELETE_SUCCESS;

				// 重新查詢資料
				// [
				// 取得國籍清單
				request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
				// 取得眷屬資料
				List<DisabledAnnuityReceiptFamCase> famDataList = receiptService
						.getDisabledBafamilytempData(iform.getBafamilytempId());

				// 重置頁面輸入資料
				DisabledAnnuityWalkInReceiptQueryForm insertForm = new DisabledAnnuityWalkInReceiptQueryForm();
				BeanUtility.copyProperties(insertForm, iform);
				insertForm.cleanFamData();
				insertForm.setFocusLocation("famNationTyp");

				// 將資料存入Session
				FormSessionHelper.removeDisabledAnnuityWalkInReceiptFamForm(request);
				FormSessionHelper.setDisabledAnnuityWalkInReceiptFamForm(insertForm, request);
				CaseSessionHelper.setDisabledAnnuityReceiptFamDataList(famDataList, request);
				// ]
			} else if ((APP_ACTION_TYP_UPDATE).equals(iform.getActionTyp())) {
				// 刪除該筆眷屬資料
				receiptService.deleteBafamilyDataByBafamilyId(iform.getBafamilyId(), userData);

				// 設定刪除成功訊息
				saveMessages(session, DatabaseMessageHelper.getDeleteSuccessMessage());
				// 設定 forward
				forward = ConstantKey.FORWARD_DELETE_SUCCESS;

				// 重新查詢資料
				// [
				// 取得國籍清單
				request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
				// 眷屬資料清單
				List<DisabledAnnuityReceiptFamCase> famDataList = receiptService
						.getBafamilyDataForDiasbled(iform.getBaappbaseId(), iform.getApNo());

				// 重置頁面輸入資料
				DisabledAnnuityWalkInReceiptQueryForm insertForm = new DisabledAnnuityWalkInReceiptQueryForm();
				BeanUtility.copyProperties(insertForm, iform);
				insertForm.cleanFamData();
				insertForm.setFocusLocation("famNationTyp");

				// 將資料存入Session
				FormSessionHelper.removeDisabledAnnuityWalkInReceiptFamForm(request);
				FormSessionHelper.setDisabledAnnuityWalkInReceiptFamForm(insertForm, request);
				CaseSessionHelper.setDisabledAnnuityReceiptFamDataList(famDataList, request);
				// ]
			}

			log.debug("執行 受理作業 - 失能年金臨櫃受理作業 - 刪除眷屬作業存檔 DisabledAnnuityWalkInReceiptAction.doDeleteBenData() 完成 ... ");
			return mapping.findForward(forward);
		} catch (Exception e) {
			// 設定刪除失敗訊息
			log.error("DisabledAnnuityWalkInReceiptAction.doDeleteBenData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getDeleteFailMessage());
			return mapping.findForward(ConstantKey.FORWARD_DELETE_FAIL);
		}
	}

	public void setReceiptService(ReceiptService receiptService) {
		this.receiptService = receiptService;
	}

	public void setSelectOptionService(SelectOptionService selectOptionService) {
		this.selectOptionService = selectOptionService;
	}

}
