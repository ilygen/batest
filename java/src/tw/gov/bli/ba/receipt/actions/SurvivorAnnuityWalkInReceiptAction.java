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
import tw.gov.bli.ba.domain.Baap0d060;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Bafamilytemp;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.receipt.cases.SurvivorAnnuityReceiptBenCase;
import tw.gov.bli.ba.receipt.cases.SurvivorAnnuityReceiptEvtCase;
import tw.gov.bli.ba.receipt.forms.SurvivorAnnuityWalkInReceiptForm;
import tw.gov.bli.ba.receipt.helper.CaseSessionHelper;
import tw.gov.bli.ba.receipt.helper.FormSessionHelper;
import tw.gov.bli.ba.services.ReceiptService;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.util.StringUtility;
import tw.gov.bli.ba.webservices.SingleCheckMarkServiceHttpBindingStub;
import tw.gov.bli.ba.webservices.SingleCheckMarkServiceLocator;
import tw.gov.bli.common.helper.UserSessionHelper;

public class SurvivorAnnuityWalkInReceiptAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(SurvivorAnnuityWalkInReceiptAction.class);

	// 受理作業 - 遺屬年金臨櫃受理作業 - 整案新增作業確認成功
	private static final String FORWARD_SAVE_ALL_SUCCESS = "saveAllSuccess";
	// 受理作業 - 遺屬年金臨櫃受理作業 - 整案新增作業確認失敗
	private static final String FORWARD_SAVE_ALL_FAIL = "saveAllFail";
	// 受理作業 - 遺屬年金臨櫃受理作業 - 整案刪除作業確認成功
	private static final String FORWARD_DELETE_ALL_SUCCESS = "deleteAllSuccess";

	// 案件操作模式 - 新增
	private static final String APP_ACTION_TYP_INSERT = "insertMode";
	// 案件操作模式 - 修改
	private static final String APP_ACTION_TYP_UPDATE = "updateMode";

	private ReceiptService receiptService;
	private SelectOptionService selectOptionService;

	/**
	 * 受理作業 - 遺屬年金臨櫃受理作業 - 登錄新增作業
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward prepareInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("執行 受理作業 - 遺屬年金臨櫃受理作業 - 登錄新增作業 SurvivorAnnuityWalkInReceiptAction.prepareInsert() 開始 ... ");

		HttpSession session = request.getSession();
		UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

		SurvivorAnnuityWalkInReceiptForm iform = (SurvivorAnnuityWalkInReceiptForm) form;
		try {
			String procType = iform.getProcType();

			// 取得 遺屬眷屬暫存檔(BAFAMILYTEMP) 暫存檔資料列編號(Sequence.BAFAMILYTEMPID)
			BigDecimal bafamilytempId = receiptService.getNewbafamilytempId();

			List<SurvivorAnnuityReceiptBenCase> benDataList = new ArrayList<>();
			// 轉入作業
			if (!StringUtils.equals(procType, "1")) {
				List<Baap0d060> turnInData = receiptService.getSurvivorTurnInData(iform);
				// 查無轉入資料
				if (CollectionUtils.isEmpty(turnInData)) {
					saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
					log.debug(
							"執行 受理作業 - 遺屬年金臨櫃受理作業 - 登錄新增作業 SurvivorAnnuityWalkInReceiptAction.prepareInsert() 完成 ... ");
					return mapping.findForward(ConstantKey.FORWARD_FAIL);
				} else {
					// 將它系統轉入個人資料設定至頁面使用的 form
					iform = receiptService.convertSurvivorTurnInData(iform, turnInData.get(0));
					// 取得轉入遺屬資料後 insert bafamilytemp
					benDataList = receiptService.getSurvivorTurnInBen(iform, userData, bafamilytempId);
					// 清除 form 的 受理編號
					if (StringUtils.contains("2,3", procType)) {
						iform.cleanApnoForBc();
					} else if (StringUtils.equals(procType, "4")) {
						iform.cleanApnoForBb();
					}
				}
			}

			CaseSessionHelper.setSurvivorAnnuityReceiptBenDataList(benDataList, request);
			CaseSessionHelper.setSurvivorAnnuityReceiptBafamilytempId(bafamilytempId, request);

			// 取得國籍清單
			request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
			// 取得具名領取清單
			request.getSession().setAttribute(ConstantKey.BEN_OPTION_LIST, new ArrayList<Baappbase>());

			log.debug("執行 受理作業 - 遺屬年金臨櫃受理作業 - 登錄新增作業 SurvivorAnnuityWalkInReceiptAction.prepareInsert() 完成 ... ");
			return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
		} catch (Exception e) {
			// 設定查詢失敗訊息
			log.error("SurvivorAnnuityWalkInReceiptAction.prepareInsert() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
			return mapping.findForward(ConstantKey.FORWARD_FAIL);
		}
	}

	/**
	 * 受理作業 - 遺屬年金臨櫃受理作業 - 新增作業
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("執行 受理作業 - 遺屬年金臨櫃受理作業 - 新增作業 SurvivorAnnuityWalkInReceiptAction.doInsert() 開始 ... ");

		HttpSession session = request.getSession();
		UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
		String forward = FORWARD_SAVE_ALL_FAIL;
		try {
			SurvivorAnnuityWalkInReceiptForm iform = (SurvivorAnnuityWalkInReceiptForm) form;
			String apNo = iform.getApNoStr();
			String procType = iform.getProcType();

			// 檢查輸入之事故者資料是否與眷屬資料重複
			if (receiptService.checkExistFamTempData(iform.getBafamilytempId(), iform.getEvtIdnNo(),
					DateUtility.changeDateType(iform.getEvtBrDate()), null, null)) {
				// 設定 事故者資料與眷屬資料重複訊息
				saveMessages(session, DatabaseMessageHelper.getSaveFailCauseDupEvtFamDataMessage());
			} else {
				// 手動輸入之受理編號不可為 K00000000000
				if (StringUtils.equalsIgnoreCase(apNo, "S00000000000")) {
					// 設定訊息: 受理編號編碼規則錯誤
					saveMessages(session, DatabaseMessageHelper.getApNoErrorMessage());
				} else {
					// 檢查受理編號是否存在
					if (StringUtils.isNotBlank(apNo) && receiptService.checkApNoExist(apNo)) {
						// 設定訊息:新增失敗，此受理號碼已存在！
						saveMessages(session, DatabaseMessageHelper.getApNoExistMessage());
					} else {
						if (StringUtils.isBlank(apNo)) {
							// 自動取得受理編號
							apNo = "S2" + StringUtility.chtLeftPad(receiptService.getSequenceSApNo(), 10, "0");
						}

						SurvivorAnnuityReceiptEvtCase evtCase = new SurvivorAnnuityReceiptEvtCase();
						BeanUtility.copyProperties(evtCase, iform);
						evtCase.setApNo(apNo);

						// 依畫面輸入欄位條件轉換 給付主檔部分欄位
						evtCase = receiptService.transSurvivorEvtInputData(evtCase);

						// 新增遺屬年金受理資料
						receiptService.insertDataForSurvivor(userData, evtCase, iform.getBafamilytempId());

						// 呼叫即時編審 WebService
						String webServiceUrl = PropertyHelper.getProperty("checkMarkWebServicesUrlForOldage");
						log.info("webServiceUrl: " + webServiceUrl);
						String returnCode = ConstantKey.DO_CHECK_MARK_FAIL;
						try {
							SingleCheckMarkServiceHttpBindingStub binding = (SingleCheckMarkServiceHttpBindingStub) new SingleCheckMarkServiceLocator()
									.getSingleCheckMarkServiceHttpPort();
							returnCode = binding.doCheckMark(apNo, SecureToken.getInstance().getToken());
						} catch (Exception e) {
							log.error("SurvivorAnnuityWalkInReceiptAction.doInsert() 即時編審發生錯誤:"
									+ ExceptionUtility.getStackTrace(e));
						}

						// 設定新增成功訊息
						if (StringUtils.equals(ConstantKey.DO_CHECK_MARK_FAIL, returnCode)) {
							saveMessages(session, CustomMessageHelper.getCheckMarkFailMessage()); // 設定即時編審失敗訊息
						} else {
							saveMessages(session,
									DatabaseMessageHelper.getReceiptSaveSuccessMessage(evtCase.getApNoStrDisplay()));
						}

						// 取得 遺屬眷屬暫存檔(BAFAMILYTEMP) 暫存檔資料列編號(Sequence.BAFAMILYTEMPID)
						BigDecimal bafamilytempId = receiptService.getNewbafamilytempId();
						// 取得國籍清單
						request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST,
								selectOptionService.getCountry());
						// 取得具名領取清單
						request.getSession().setAttribute(ConstantKey.BEN_OPTION_LIST, new ArrayList<Baappbase>());
						// 眷屬資料清單
						List<SurvivorAnnuityReceiptBenCase> famDataList = new ArrayList<SurvivorAnnuityReceiptBenCase>();

						// 移除所有Session中的資料
						FormSessionHelper.removeSurvivorAnnuityWalkInReceiptForm(request);
						FormSessionHelper.removeSurvivorAnnuityReceiptQryCondForm(request);
						FormSessionHelper.removeSurvivorAnnuityReceiptQryForm(request);
						CaseSessionHelper.removeAllSurvivorAnnuityReceiptCase(request);
						// 將資料存入Session
						CaseSessionHelper.setSurvivorAnnuityReceiptBenDataList(
								new ArrayList<SurvivorAnnuityReceiptBenCase>(), request);
						CaseSessionHelper.setSurvivorAnnuityReceiptBafamilytempId(bafamilytempId, request);
						
						// 設定 form 的 procType 供 baap0d061a.jsp 判斷
						SurvivorAnnuityWalkInReceiptForm initDataForm = new SurvivorAnnuityWalkInReceiptForm();
						initDataForm.setProcType(procType);
						FormSessionHelper.setSurvivorAnnuityWalkInReceiptForm(initDataForm, request);

						forward = FORWARD_SAVE_ALL_SUCCESS;
					}
				}
			}
			log.debug("執行 受理作業 - 遺屬年金臨櫃受理作業 - 新增作業 SurvivorAnnuityWalkInReceiptAction.doInsert() 完成 ... ");
			return mapping.findForward(forward);
		} catch (Exception e) {
			// 設定新增失敗訊息
			log.error("SurvivorAnnuityWalkInReceiptAction.doInsert() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getSaveFailMessage());
			return mapping.findForward(FORWARD_SAVE_ALL_FAIL);
		}
	}

	/**
	 * 受理作業 - 遺屬年金臨櫃受理作業 - 遺屬新增作業
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doInsertBenData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("執行 受理作業 - 遺屬年金臨櫃受理作業 - 遺屬新增作業 SurvivorAnnuityWalkInReceiptAction.doInsertBenData() 開始 ... ");

		HttpSession session = request.getSession();
		UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
		String forward = ConstantKey.FORWARD_SAVE_FAIL;
		try {
			SurvivorAnnuityWalkInReceiptForm iform = (SurvivorAnnuityWalkInReceiptForm) form;
			SurvivorAnnuityReceiptBenCase caseObj = new SurvivorAnnuityReceiptBenCase();
			BeanUtility.copyProperties(caseObj, iform);

			// 保存畫面上已輸入之事故者資料
			SurvivorAnnuityWalkInReceiptForm evtForm = FormSessionHelper.getSurvivorAnnuityWalkInReceiptForm(request);
			evtForm = receiptService.keepInputEvtFormDataForWalkInSurvivor(evtForm, iform);
			FormSessionHelper.setSurvivorAnnuityWalkInReceiptForm(evtForm, request);

			// 先判斷該案是新增或修改
			if ((APP_ACTION_TYP_INSERT).equals(iform.getActionTyp())) {
				// 檢查輸入之眷屬資料是否已存在(眷屬暫存檔)
				if (receiptService.checkExistFamTempData(caseObj.getBafamilytempId(), caseObj.getBenIdnNo(),
						DateUtility.changeDateType(caseObj.getBenBrDate()), null, null)) {
					// 設定 眷屬資料重複訊息
					saveMessages(session, DatabaseMessageHelper.getSaveFailCauseFamDataExistMessage());
				} else {
					// 依畫面輸入欄位條件轉換 給付主檔部分欄位
					caseObj = receiptService.transSurvivorBenInputData(caseObj, APP_ACTION_TYP_INSERT);
					caseObj.setCheckMk("Y");// 判斷受理轉入遺屬是否完成檢核
					// 新增遺屬暫存檔資料
					receiptService.insertSurvivorBafamilytempData(caseObj, userData);
					// 設定新增成功訊息
					saveMessages(session, DatabaseMessageHelper.getSaveSuccessMessage());
					forward = ConstantKey.FORWARD_SAVE_SUCCESS;

					// 重新查詢資料
					// [
					// 取得國籍清單
					request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST,
							selectOptionService.getCountry());
					// 取得具名領取清單
					request.getSession().setAttribute(ConstantKey.BEN_OPTION_LIST,
							selectOptionService.getBenOptionListForSurvivorTemp(caseObj.getBafamilytempId(), null));
					// 眷屬資料清單
					List<SurvivorAnnuityReceiptBenCase> benDataList = receiptService
							.getSurvivorBafamilytempDataList(caseObj.getBafamilytempId());

					// 將資料存入Session
					FormSessionHelper.removeSurvivorAnnuityReceiptBenForm(request);
					CaseSessionHelper.setSurvivorAnnuityReceiptBenDataList(benDataList, request);

					SurvivorAnnuityWalkInReceiptForm benForm = new SurvivorAnnuityWalkInReceiptForm();
					benForm.setFocusLocation("benNationTyp");
					FormSessionHelper.setSurvivorAnnuityWalkInReceiptBenForm(benForm, request);
					// ]
				}
			} else if ((APP_ACTION_TYP_UPDATE).equals(iform.getActionTyp())) {
				// 檢查輸入之眷屬資料是否已存在(眷屬暫存檔)
				if (receiptService.checkExistBenData(caseObj.getApNo(), caseObj.getBenIdnNo(),
						DateUtility.changeDateType(caseObj.getBenBrDate()), null, null)) {
					// 設定 眷屬資料重複訊息
					saveMessages(session, DatabaseMessageHelper.getSaveFailCauseFamDataExistMessage());
				} else {
					// 取得事故者資料
					SurvivorAnnuityReceiptEvtCase evtCase = CaseSessionHelper.getSurvivorAnnuityReceiptEvtData(request);

					// 依畫面輸入欄位條件轉換 給付主檔部分欄位
					caseObj = receiptService.transSurvivorBenInputData(caseObj, APP_ACTION_TYP_UPDATE);
					// 新增遺屬暫存檔資料
					receiptService.insertSurvivorBaappbaseBenData(evtCase, caseObj, userData);
					// 設定新增成功訊息
					saveMessages(session, DatabaseMessageHelper.getSaveSuccessMessage());
					forward = ConstantKey.FORWARD_SAVE_SUCCESS;

					// 重新查詢資料
					// [
					// 取得國籍清單
					request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST,
							selectOptionService.getCountry());
					// 取得具名領取清單
					request.getSession().setAttribute(ConstantKey.BEN_OPTION_LIST,
							selectOptionService.getBenOptionListForSurvivor(null, caseObj.getApNo()));
					// 眷屬資料清單
					List<SurvivorAnnuityReceiptBenCase> benDataList = receiptService
							.getSurvivorBenDataList(caseObj.getApNo(), new String[] { "00" }, "in");

					// 將資料存入Session
					FormSessionHelper.removeSurvivorAnnuityReceiptBenForm(request);
					CaseSessionHelper.setSurvivorAnnuityReceiptBenDataList(benDataList, request);

					SurvivorAnnuityWalkInReceiptForm benForm = new SurvivorAnnuityWalkInReceiptForm();
					benForm.setFocusLocation("benNationTyp");
					FormSessionHelper.setSurvivorAnnuityWalkInReceiptBenForm(benForm, request);
					// ]
				}
			}

			log.debug("執行 受理作業 - 遺屬年金臨櫃受理作業 - 遺屬新增作業 SurvivorAnnuityWalkInReceiptAction.doInsertBenData() 完成 ... ");
			return mapping.findForward(forward);
		} catch (Exception e) {
			// 設定新增失敗訊息
			log.error("SurvivorAnnuityWalkInReceiptAction.doInsertBenData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getSaveFailMessage());
			return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
		}
	}

	/**
	 * 受理作業 - 遺屬年金給付受理作業 - 遺屬修改作業
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doUpdateBenData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("執行 受理作業 - 遺屬年金臨櫃受理作業 - 遺屬修改作業 SurvivorAnnuityWalkInReceiptAction.doUpdateBenData() 開始 ... ");

		HttpSession session = request.getSession();
		UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
		String forward = ConstantKey.FORWARD_UPDATE_SUCCESS;
		try {
			SurvivorAnnuityWalkInReceiptForm iform = (SurvivorAnnuityWalkInReceiptForm) form;
			SurvivorAnnuityReceiptBenCase benCase = new SurvivorAnnuityReceiptBenCase();
			BeanUtility.copyProperties(benCase, iform);

			// 保存畫面上已輸入之事故者資料
			SurvivorAnnuityWalkInReceiptForm evtForm = FormSessionHelper.getSurvivorAnnuityWalkInReceiptForm(request);
			evtForm = receiptService.keepInputEvtFormDataForWalkInSurvivor(evtForm, iform);
			FormSessionHelper.setSurvivorAnnuityWalkInReceiptForm(evtForm, request);

			// 先判斷該案是新增或修改
			if ((APP_ACTION_TYP_INSERT).equals(iform.getActionTyp())) {
				// 取得具名領取清單
				request.getSession().setAttribute(ConstantKey.BEN_OPTION_LIST, selectOptionService
						.getBenOptionListForSurvivorTemp(benCase.getBafamilytempId(), benCase.getSeqNo()));

				// 檢查輸入之眷屬資料是否已存在(眷屬暫存檔)
				if (receiptService.checkExistFamTempData(benCase.getBafamilytempId(), benCase.getBenIdnNo(),
						DateUtility.changeDateType(benCase.getBenBrDate()), benCase.getSeqNo(), "<>")) {
					// 設定 眷屬資料重複訊息
					saveMessages(session, DatabaseMessageHelper.getSaveFailCauseFamDataExistMessage());
				} else {
					// 依畫面輸入欄位條件轉換 給付主檔部分欄位
					benCase = receiptService.transSurvivorBenInputData(benCase, APP_ACTION_TYP_INSERT);
					benCase.setCheckMk("Y");// 判斷受理轉入遺屬是否完成檢核
					// 修改遺屬暫存檔資料
					receiptService.updateSurvivorBafamilytempData(benCase);
					// 設定修改成功訊息
					saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());
					forward = ConstantKey.FORWARD_UPDATE_SUCCESS;

					// 重新查詢資料
					// [
					// 取得國籍清單
					request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST,
							selectOptionService.getCountry());
					// 取得具名領取清單
					request.getSession().setAttribute(ConstantKey.BEN_OPTION_LIST,
							selectOptionService.getBenOptionListForSurvivorTemp(benCase.getBafamilytempId(), null));
					// 眷屬資料清單
					List<SurvivorAnnuityReceiptBenCase> benDataList = receiptService
							.getSurvivorBafamilytempDataList(benCase.getBafamilytempId());

					// 將資料存入Session
					FormSessionHelper.removeSurvivorAnnuityReceiptBenForm(request);
					CaseSessionHelper.setSurvivorAnnuityReceiptBenDataList(benDataList, request);

					SurvivorAnnuityWalkInReceiptForm benForm = new SurvivorAnnuityWalkInReceiptForm();
					benForm.setFocusLocation("benNationTyp");
					FormSessionHelper.setSurvivorAnnuityWalkInReceiptBenForm(benForm, request);
					// ]
				}
			} else if ((APP_ACTION_TYP_UPDATE).equals(iform.getActionTyp())) {
				// 取得具名領取清單
				request.getSession().setAttribute(ConstantKey.BEN_OPTION_LIST,
						selectOptionService.getBenOptionListForSurvivor(benCase.getBaappbaseId(), benCase.getApNo()));

				// 檢查輸入之眷屬資料是否已存在(眷屬暫存檔)
				if (receiptService.checkExistBenData(benCase.getApNo(), benCase.getBenIdnNo(),
						DateUtility.changeDateType(benCase.getBenBrDate()), benCase.getBaappbaseId(), "<>")) {
					// 設定 眷屬資料重複訊息
					saveMessages(session, DatabaseMessageHelper.getSaveFailCauseFamDataExistMessage());
				} else {
					if (StringUtils.isNotBlank(benCase.getBenAppDate())) {
						benCase.setBenAppDate(DateUtility.changeDateType(benCase.getBenAppDate()));
					}
					// 依畫面輸入欄位條件轉換 給付主檔部分欄位
					benCase = receiptService.transSurvivorBenInputData(benCase, APP_ACTION_TYP_UPDATE);

					// 取得需記錄異動LOG的欄位資料
					// 為不影響前端頁面顯示, 故須複製一份 Form
					// SurvivorAnnuityReceiptForm tempForm = new SurvivorAnnuityReceiptForm();
					// BeanUtility.copyProperties(tempForm, form);
					// benCase.setBaapplogList(LoggingHelper.getBaapplogList(tempForm,
					// ConstantKey.OLD_FIELD_PREFIX,
					// ConstantKey.BAAPPLOG_RECEIPT_SURVIVOR_FIELD_RESOURCE_PREFIX));

					// 修改遺屬暫存檔資料
					receiptService.updateSurvivorBaappbaseBenData(benCase, userData);
					// 設定修改成功訊息
					saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());
					forward = ConstantKey.FORWARD_UPDATE_SUCCESS;

					// 重新查詢資料
					// [
					// 取得國籍清單
					request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST,
							selectOptionService.getCountry());
					// 取得具名領取清單
					request.getSession().setAttribute(ConstantKey.BEN_OPTION_LIST,
							selectOptionService.getBenOptionListForSurvivor(null, benCase.getApNo()));
					// 眷屬資料清單
					List<SurvivorAnnuityReceiptBenCase> benDataList = receiptService
							.getSurvivorBenDataList(benCase.getApNo(), new String[] { "00" }, "in");

					// 將資料存入Session
					FormSessionHelper.removeSurvivorAnnuityReceiptBenForm(request);
					CaseSessionHelper.setSurvivorAnnuityReceiptBenDataList(benDataList, request);

					SurvivorAnnuityWalkInReceiptForm benForm = new SurvivorAnnuityWalkInReceiptForm();
					benForm.setFocusLocation("benNationTyp");
					FormSessionHelper.setSurvivorAnnuityWalkInReceiptBenForm(benForm, request);
					// ]
				}
			}
			log.debug("執行 受理作業 - 遺屬年金臨櫃受理作業 - 遺屬修改作業 SurvivorAnnuityWalkInReceiptAction.doUpdateBenData() 完成 ... ");
			return mapping.findForward(forward);
		} catch (Exception e) {
			// 設定修改失敗訊息
			log.error("SurvivorAnnuityWalkInReceiptAction.doUpdateBenData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
			return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
		}
	}

	/**
	 * 受理作業 - 遺屬年金給付受理作業 - 遺屬刪除作業
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doDeleteBenData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("執行 受理作業 - 遺屬年金臨櫃受理作業 - 遺屬刪除作業 SurvivorAnnuityWalkInReceiptAction.doDeleteBenData() 開始 ... ");

		HttpSession session = request.getSession();
		UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
		String forward = ConstantKey.FORWARD_DELETE_SUCCESS;
		try {
			SurvivorAnnuityWalkInReceiptForm iform = (SurvivorAnnuityWalkInReceiptForm) form;

			// 先判斷該案是新增或修改
			if ((APP_ACTION_TYP_INSERT).equals(iform.getActionTyp())) {
				// 檢查是否為 被具名領取對象
				if (receiptService.getAccSeqNoAmtFromBafamilytemp(iform.getBafamilytempId(), iform.getSeqNo()) > 0) {
					// 該筆資料為其他資料之具名領取者，不可刪除。

					saveMessages(session, CustomMessageHelper.getAccDataCantDeleteMessage());
				} else {
					// 刪除該筆眷屬資料
					String seqNo = iform.getSeqNo();
					BigDecimal bafamilytempId = iform.getBafamilytempId();
					List<Bafamilytemp> bafamilyTempList = receiptService
							.selectDeleteAfterDataBy(iform.getBafamilytempId(), iform.getSeqNo());
					receiptService.deleteBafamilytempData(iform.getBafamilytempId(), iform.getSeqNo());

					Bafamilytemp bafamilyTemp = new Bafamilytemp();
					Integer beforeSeqNo = new Integer(seqNo);
					if (bafamilyTemp != null && bafamilyTempList.size() > 0) {
						for (int i = 0; i < bafamilyTempList.size(); i++) {
							String afterSeqNo = beforeSeqNo + i < 10 ? "0" + String.valueOf(beforeSeqNo + i)
									: String.valueOf(beforeSeqNo + i);
							bafamilyTemp = bafamilyTempList.get(i);
							receiptService.updateBafamilytempSeqNoData(bafamilytempId, bafamilyTemp.getSeqNo(),
									afterSeqNo);

						}
					}

					// 設定刪除成功訊息
					saveMessages(session, DatabaseMessageHelper.getDeleteSuccessMessage());

					// 設定 forward
					forward = ConstantKey.FORWARD_DELETE_SUCCESS;

					// 重新查詢資料
					// [
					// 取得國籍清單
					request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST,
							selectOptionService.getCountry());
					// 取得具名領取清單
					request.getSession().setAttribute(ConstantKey.BEN_OPTION_LIST,
							selectOptionService.getBenOptionListForSurvivorTemp(iform.getBafamilytempId(), null));
					// 眷屬資料清單
					List<SurvivorAnnuityReceiptBenCase> benDataList = receiptService
							.getSurvivorBafamilytempDataList(iform.getBafamilytempId());

					// 將資料存入Session
					FormSessionHelper.removeSurvivorAnnuityReceiptBenForm(request);
					CaseSessionHelper.setSurvivorAnnuityReceiptBenDataList(benDataList, request);

					SurvivorAnnuityWalkInReceiptForm benForm = new SurvivorAnnuityWalkInReceiptForm();
					benForm.setFocusLocation("benNationTyp");
					FormSessionHelper.setSurvivorAnnuityWalkInReceiptBenForm(benForm, request);
					// ]
				}
			} else if ((APP_ACTION_TYP_UPDATE).equals(iform.getActionTyp())) {
				// 檢查是否為 被具名領取對象
				if (receiptService.getAccSeqNoAmtFromBaappbase(iform.getApNo(), iform.getSeqNo()) > 0) {
					// 該筆資料為其他資料之具名領取者，不可刪除。
					saveMessages(session, CustomMessageHelper.getAccDataCantDeleteMessage());
				} else {
					// 刪除該筆眷屬資料
					boolean isDeleteEvt = receiptService.deleteSurvivorBaappbaseBenData(iform.getBaappbaseId(),
							iform.getApNo(), userData);

					// 設定刪除成功訊息
					saveMessages(session, DatabaseMessageHelper.getDeleteSuccessMessage());

					if (isDeleteEvt) {// 事故者資料已刪除
						FormSessionHelper.removeSurvivorAnnuityReceiptForm(request);
						FormSessionHelper.removeSurvivorAnnuityReceiptQryForm(request);
						FormSessionHelper.removeSurvivorAnnuityReceiptQryCondForm(request);
						FormSessionHelper.removeSurvivorAnnuityReceiptBenForm(request);
						CaseSessionHelper.removeAllSurvivorAnnuityReceiptCase(request);

						// 設定 forward
						forward = FORWARD_DELETE_ALL_SUCCESS;
					} else {
						// 設定 forward
						forward = ConstantKey.FORWARD_DELETE_SUCCESS;
						// 重新查詢資料
						// [
						// 取得國籍清單
						request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST,
								selectOptionService.getCountry());
						// 取得具名領取清單
						request.getSession().setAttribute(ConstantKey.BEN_OPTION_LIST,
								selectOptionService.getBenOptionListForSurvivor(null, iform.getApNo()));
						// 眷屬資料清單
						List<SurvivorAnnuityReceiptBenCase> benDataList = receiptService
								.getSurvivorBenDataList(iform.getApNo(), new String[] { "00" }, "in");
						// 將資料存入Session
						FormSessionHelper.removeSurvivorAnnuityReceiptBenForm(request);
						CaseSessionHelper.setSurvivorAnnuityReceiptBenDataList(benDataList, request);

						SurvivorAnnuityWalkInReceiptForm benForm = new SurvivorAnnuityWalkInReceiptForm();
						benForm.setFocusLocation("benNationTyp");
						FormSessionHelper.setSurvivorAnnuityWalkInReceiptBenForm(benForm, request);
						// ]
					}
				}
			}
			log.debug("執行 受理作業 - 遺屬年金臨櫃受理作業 - 遺屬刪除作業 SurvivorAnnuityWalkInReceiptAction.doDeleteBenData() 完成 ... ");
			return mapping.findForward(forward);
		} catch (Exception e) {
			// 設定刪除失敗訊息
			log.error("SurvivorAnnuityWalkInReceiptAction.doDeleteBenData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getDeleteFailMessage());
			return mapping.findForward(ConstantKey.FORWARD_DELETE_FAIL);
		}
	}

	/**
	 * 受理作業 - 遺屬年金臨櫃受理作業 - 返回
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("執行 受理作業 - 遺屬年金臨櫃受理作業 - 返回 SurvivorAnnuityWalkInReceiptAction.doBack() 開始 ... ");

		String forward = ConstantKey.FORWARD_BACK;
		try {
			// 移除所有Session中的資料
			FormSessionHelper.removeSurvivorAnnuityReceiptQryCondForm(request);
			FormSessionHelper.removeSurvivorAnnuityWalkInReceiptForm(request);
			FormSessionHelper.removeSurvivorAnnuityReceiptBenForm(request);
			CaseSessionHelper.removeAllSurvivorAnnuityReceiptCase(request);

			log.debug("執行 受理作業 - 遺屬年金臨櫃受理作業 - 返回 SurvivorAnnuityWalkInReceiptAction.doBack() 完成 ... ");
			return mapping.findForward(forward);
		} catch (Exception e) {
			log.error("SurvivorAnnuityWalkInReceiptAction.doBack() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			return mapping.findForward(forward);
		}
	}

	public void setReceiptService(ReceiptService receiptService) {
		this.receiptService = receiptService;
	}

	public void setSelectOptionService(SelectOptionService selectOptionService) {
		this.selectOptionService = selectOptionService;
	}

}
