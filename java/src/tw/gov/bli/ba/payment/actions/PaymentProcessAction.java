package tw.gov.bli.ba.payment.actions;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.owasp.encoder.Encode;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.bj.helper.CaseSessionHelper;
import tw.gov.bli.ba.bj.helper.FormSessionHelper;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.payment.cases.PaymentProcessCase;
import tw.gov.bli.ba.payment.cases.PaymentProcessQueryCase;
import tw.gov.bli.ba.payment.cases.PaymentStageDtlCase;
import tw.gov.bli.ba.payment.cases.PaymentUpdateCase;
import tw.gov.bli.ba.payment.forms.PaymentProcessQueryForm;
import tw.gov.bli.ba.payment.rpt.PaymentInterestReport;
import tw.gov.bli.ba.payment.rpt.PaymentReport;
import tw.gov.bli.ba.services.PaymentService;
import tw.gov.bli.ba.services.QueryService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.util.StringUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

public class PaymentProcessAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(PaymentProcessAction.class);

	private PaymentService paymentService;
	private QueryService queryService;
	private String insertUpdate = "";// 這個變數用在後面儲存時修改或是新增

	/**
	 * 繳款單作業 - 開單維護作業 (bapm0d012l.jsp) <br>
	 * 按下「刪除」的動作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		PaymentProcessQueryForm queryForm = (PaymentProcessQueryForm) form;
		try {
			if (StringUtils.isNotBlank(queryForm.getPaymentNo())) {
				// 刪除繳款單
				paymentService.delelteFromBapayment(queryForm.getPaymentNo());
			}
			saveMessages(session, DatabaseMessageHelper.getDeleteSuccessMessage());
			CaseSessionHelper.removeAllPaymentCaseList(request);// 刪除繳款單後remove存在session的值
			return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);

		} catch (Exception e) {
			// 設定查詢失敗訊息
			log.error("PaymentProcessAction.doDelete() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getDeleteFailMessage());
			return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
		}
	}

	/**
	 * 繳款單作業 - 開單維護作業 (bapm0d010l.jsp) <br>
	 * 按下「確認」的動作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("繳款單作業 - 開單維護作業 - 查詢頁面  PaymentProcessAction.doQuery() 開始 ... ");
		CaseSessionHelper.removeAllPaymentCaseList(request);
		insertUpdate = "";// 這個變數用在後面修改或是新增，進入query，清空變數
		HttpSession session = request.getSession();
		PaymentProcessQueryForm queryForm = (PaymentProcessQueryForm) form;
		try {
			List<PaymentProcessQueryCase> queryList = paymentService.getPaymentQueryCaseBy(queryForm.getIdn());// 查詢繳款單資料
			String benName = paymentService.getPaymentBenName(queryForm.getIdn());
			// 查無資料
			if (queryList.size() == 0) {
				int queryEvtIdn = paymentService.getEvtPersonDataCount(queryForm.getIdn()); // 給付主檔資料是否存在
				if (queryEvtIdn > 0) {
					// 將 查詢結果清單 存到 Request Scope
					queryForm.setEvtName(benName);// 改為受益人
					FormSessionHelper.setPaymentProcessQueryForm(queryForm, request);
					log.debug("繳款單作業 - 開單維護作業 - 查詢頁面  PaymentProcessAction.doQuery() 完成 (尚未有繳款單資料，可新增)... ");
					return mapping.findForward(ConstantKey.FORWARD_QUERY_LIST);
				} else {
					log.debug("繳款單作業 - 開單維護作業 - 查詢頁面  PaymentProcessAction.doQuery() 完成 ..查無資料. ");
					saveMessages(session, DatabaseMessageHelper.getNoDataMessage());
					return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
				}
			}
			queryForm.setEvtName(benName);// 本查詢事故者後改為受益者姓名
			// 將查詢結果清單 存到 Request Scope
			CaseSessionHelper.setPaymentProcessQueryList(queryList, request);
			FormSessionHelper.setPaymentProcessQueryForm(queryForm, request);
			log.debug("繳款單作業 - 開單維護作業 - 查詢頁面  PaymentProcessAction.doQuery() 完成(有繳款單資料，可新增) ... ");
			return mapping.findForward(ConstantKey.FORWARD_QUERY_LIST);

		} catch (Exception e) {
			// 設定查詢失敗訊息
			log.error("PaymentProcessAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
			return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
		}
	}

	/**
	 * 繳款單作業 - 開單維護作業 (bapm0d010l.jsp) <br>
	 * 按下「新增」的動作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("繳款單作業 - 開單維護作業 - 查詢頁面  PaymentProcessAction.doInsert() 開始 ... ");
		HttpSession session = request.getSession();
		CaseSessionHelper.removeAllPaymentCaseList(request);
		PaymentProcessQueryForm queryForm = (PaymentProcessQueryForm) form;
		try {
			insertUpdate = "I";// 新增註記
			List<String> apnoList = paymentService.getApnoDataFromMultiSource(queryForm.getIdn());// 查詢該IDN的APNOLIST
			// 查無資料
			if (apnoList.size() <= 0) {
				log.debug("繳款單作業 - 開單維護作業 - 新增頁面  PaymentProcessAction.doInsert() 查無保險證號資料 ... ");
				saveMessages(session, DatabaseMessageHelper.getNoDataMessage());
				return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
			} else {
				// 查詢應收未收檔
				List<PaymentProcessCase> caseList = paymentService.getUnacpdtlDataList(apnoList);
				if (caseList.size() > 0) {
					PaymentProcessCase paymentData = paymentService.getPaymentBaseData(caseList.get(0).getApno(),
							queryForm.getIdn());
					if (paymentData != null) {
						queryForm.setAddr(paymentData.getAddr());// 地址
						queryForm.setZipCode(paymentData.getZipCode());// 郵遞區號
					}
					// 查詢今天繳款單開單序號
					int countDate = paymentService.getNowDatePaymentCount(DateUtility.getNowWestDate()) + 1;
					String paymentNo = caseList.get(0).getPayKind()
							+ StringUtils.substring(DateUtility.getNowChineseDate(), 1,
									DateUtility.getNowChineseDate().length())
							+ StringUtility.chtLeftPad(String.valueOf(countDate), 5, "0") + "00";// 繳款單號(民國年月日去掉年第一位)+今天開單序號+00(主案)
					BigDecimal payTotAmt = BigDecimal.ZERO;// 應繳總本金
					for (int i = 0; i < caseList.size(); i++) {
						BigDecimal payAmt = caseList.get(i).getPayAmt();
						payTotAmt = payTotAmt.add(payAmt);// 應繳總本金
					}
					queryForm.setCaseType("一般案件");// 繳款單類別(一般案件、提繳案件)
					queryForm.setPaymentNo(paymentNo);// 繳款單號
					queryForm.setTotTrgAmt(BigDecimal.ZERO);// 利息標的金額
					queryForm.setInterestTryStage(BigDecimal.ZERO);// 利息試算期數
					queryForm.setMonthlyPayAmt(BigDecimal.ZERO);// 利息每期本金
					queryForm.setPayTotAmt(payTotAmt);// 應繳總本金
					queryForm.setTotAmtStage(BigDecimal.ONE);// 本金期數
					queryForm.setInterestStage(BigDecimal.ZERO);// 利息期數
					BigDecimal stageAmt = payTotAmt.divide(queryForm.getTotAmtStage());// 本金期數
					queryForm.setStageAmt(stageAmt);// 每期攤還本金
					// 將 查詢結果清單 存到 Request Scope
					CaseSessionHelper.setPaymentProcessDetailList(caseList, request);
					FormSessionHelper.setPaymentProcessQueryForm(queryForm, request);
					log.debug("繳款單作業 - 開單維護作業 -  新增畫面 PaymentProcessAction.doInsert() 完成 ... ");
					return mapping.findForward(ConstantKey.FORWARD_QUERY_UNACPDTL_LIST);
				} else {
					// 查無應收未收資料
					log.debug("繳款單作業 - 開單維護作業 - 查詢頁面  PaymentProcessAction.doQuery() 完成 ..查無資料. ");
					saveMessages(session, DatabaseMessageHelper.getNoUnacpdtlDataMessage());
					return mapping.findForward(ConstantKey.FORWARD_QUERY_UNACPDTL_EMPTY);
				}
			}
		} catch (Exception e) {
			// 設定查詢失敗訊息
			log.error("PaymentProcessAction.doInsert() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getSaveFailMessage());
			return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
		}
	}

	/**
	 * 繳款單作業 - 開單明細維護作業(BAPM0D012M.jsp) <br>
	 * 按下「利息計算」、「重算」的動作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doCompute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("繳款單作業 - 利息試算、重算 PaymentProcessAction.doCompute() 開始 ... ");
		HttpSession session = request.getSession();
		PaymentProcessQueryForm queryForm = (PaymentProcessQueryForm) form;
		try {
			List<PaymentProcessCase> resultList = CaseSessionHelper.getPaymentProcessDetailList(request);
			String[] idForConfirm = queryForm.getIdForConfirm().split(",");
			String[] payAmtConfirm = queryForm.getPayAmtForConfirm().split(",");
			String[] writeoffSeqNo = queryForm.getSeqNoForConfirm().split(",");
			String[] monthlyPayAmt = null;
			String[] interestEndDate = null;
			if (StringUtils.isNotBlank(queryForm.getMonthlyPayList())
					&& StringUtils.isNotBlank(queryForm.getModifyString())) {
				monthlyPayAmt = queryForm.getMonthlyPayList().split(",");
			}
			if (StringUtils.isNotBlank(queryForm.getInterestEndDateList())
					&& StringUtils.isNotBlank(queryForm.getModifyString())) {
				interestEndDate = queryForm.getInterestEndDateList().split(",");
			}
			for (int i = 0; i < resultList.size(); i++) {
				resultList.get(i).setWriteoffSeqNo("");
				for (int j = 0; j < idForConfirm.length; j++) {
					if (i == Integer.parseInt(idForConfirm[j])) {
						resultList.get(i).setPayAmt(BigDecimal.valueOf(Integer.parseInt(payAmtConfirm[j])));
						resultList.get(i).setWriteoffSeqNo(writeoffSeqNo[j]);
					}
					if (i == Integer.parseInt(queryForm.getMapnoMk()) - 1) {
						resultList.get(i).setMapnoMk("1");
					} else {
						resultList.get(i).setMapnoMk("0");
					}
				}
			}

			// 查詢利率IRATE
			Map<String, Double> rateMap = paymentService.getYearRateMapData();
			List<PaymentStageDtlCase> caseData = paymentService.getTryStageData(queryForm, rateMap, resultList,
					monthlyPayAmt, interestEndDate);
			List<PaymentStageDtlCase> cpData = new ArrayList<PaymentStageDtlCase>();

			List<PaymentStageDtlCase> compareList = CaseSessionHelper.getPaymentInterestDetailListForDb(request);
			String flag = "";
			String rtnMsg = "";
			StringBuilder msg = new StringBuilder();

			if (compareList != null && compareList.size() > 0) {
				if (StringUtils.equals(queryForm.getModifyString(), "M")) {

					int totTrgAmt = 0;
					for (int i = 0; i < compareList.size(); i++) {
						totTrgAmt = totTrgAmt + compareList.get(i).getRePayAmt().intValue();
					}
					if (StringUtils.isNotBlank(flag)) {
						BigDecimal interest = new BigDecimal(0);
						for (PaymentStageDtlCase cp : caseData) {
							if (cp.getAdjInterest() != null) {
								interest = interest.add(cp.getAdjInterest());
								cpData.add(cp);
								if (StringUtils.isNotBlank(cp.getRateMsg())) {
									msg.append(cp.getRateMsg());
								}
							} else {
								cpData.add(cp);
								if (StringUtils.isNotBlank(cp.getRateMsg())) {
									msg.append(cp.getRateMsg());
								}
							}

							if (StringUtils.isNotBlank(cp.getRateMsg())) {
								rtnMsg = cp.getRateMsg();
							}
						}
						List<PaymentStageDtlCase> listData = paymentService.getTryStageDataForPage(cpData);
						queryForm.setTotInterst(interest);
						queryForm.setTryInterestAmt(interest.add(queryForm.getTotTrgAmt()));// 應繳本利和
						FormSessionHelper.setPaymentProcessQueryForm(queryForm, request);
						CaseSessionHelper.setPaymentProcessDetailList(resultList, request);
						CaseSessionHelper.setPaymentInterestDetailList(listData, request);
						CaseSessionHelper.setPaymentInterestDetailListForDb(caseData, request);

					} else {
						BigDecimal interest = new BigDecimal(0);
						for (PaymentStageDtlCase cp : compareList) {
							if (cp.getAdjInterest() != null) {
								interest = interest.add(cp.getAdjInterest());
								cpData.add(cp);
								if (StringUtils.isNotBlank(cp.getRateMsg())
										&& StringUtils.equals(queryForm.getModifyString(), "R")) {
									msg.append(cp.getRateMsg());
								}
							} else {
								cpData.add(cp);
								if (StringUtils.isNotBlank(cp.getRateMsg())
										&& StringUtils.equals(queryForm.getModifyString(), "R")) {
									rtnMsg = cp.getRateMsg();
								}
							}

						}
						queryForm.setTotInterst(interest);
						queryForm.setInsertString("");
						queryForm.setTryInterestAmt(interest.add(queryForm.getTotTrgAmt()));// 應繳本利和
						List<PaymentStageDtlCase> listDataForOld = paymentService.getTryStageDataForPage(compareList);
						CaseSessionHelper.setPaymentInterestDetailList(listDataForOld, request);
					}
				} else {
					BigDecimal interest = new BigDecimal(0);
					for (PaymentStageDtlCase cp : caseData) {
						if (cp.getAdjInterest() != null) {
							interest = interest.add(cp.getAdjInterest());
							cpData.add(cp);
							if (StringUtils.isNotBlank(cp.getRateMsg())) {
								rtnMsg = cp.getRateMsg();
							}
						} else {
							cpData.add(cp);
							if (StringUtils.isNotBlank(cp.getRateMsg())) {
								rtnMsg = cp.getRateMsg();
							}
						}
					}
					List<PaymentStageDtlCase> listData = paymentService.getTryStageDataForPage(cpData);
					queryForm.setTotInterst(interest);
					if (StringUtils.equals(queryForm.getInsertString(), "I")) {
						queryForm.setTryInterestAmt(queryForm.getTotTrgAmt());// 應繳本利和
					} else {
						queryForm.setTryInterestAmt(interest.add(queryForm.getTotTrgAmt()));// 應繳本利和
					}
					FormSessionHelper.setPaymentProcessQueryForm(queryForm, request);
					CaseSessionHelper.setPaymentProcessDetailList(resultList, request);
					CaseSessionHelper.setPaymentInterestDetailList(listData, request);
					CaseSessionHelper.setPaymentInterestDetailListForDb(caseData, request);
				}
			} else {
				BigDecimal interest = new BigDecimal(0);
				for (PaymentStageDtlCase cp : caseData) {
					if (cp.getAdjInterest() != null) {
						interest = interest.add(cp.getAdjInterest());
						cpData.add(cp);
						if (StringUtils.isNotBlank(cp.getRateMsg())
								&& StringUtils.equals(queryForm.getModifyString(), "R")) {
							rtnMsg = cp.getRateMsg();
						}
					} else {
						cpData.add(cp);
						if (StringUtils.isNotBlank(cp.getRateMsg())
								&& StringUtils.equals(queryForm.getModifyString(), "R")) {
							rtnMsg = cp.getRateMsg();
						}
					}

				}
				List<PaymentStageDtlCase> listData = paymentService.getTryStageDataForPage(cpData);
				queryForm.setInsertString("I");

				queryForm.setTotInterst(interest);
				if (StringUtils.equals(queryForm.getInsertString(), "I")) {
					queryForm.setTryInterestAmt(queryForm.getTotTrgAmt());// 應繳本利和
				} else {
					queryForm.setTryInterestAmt(interest.add(queryForm.getTotTrgAmt()));// 應繳本利和
				}
				FormSessionHelper.setPaymentProcessQueryForm(queryForm, request);
				CaseSessionHelper.setPaymentProcessDetailList(resultList, request);
				CaseSessionHelper.setPaymentInterestDetailList(listData, request);
				CaseSessionHelper.setPaymentInterestDetailListForDb(caseData, request);
			}
			if (StringUtils.isNotBlank(rtnMsg)) {
				saveMessages(session, DatabaseMessageHelper.getNoRateMessage(rtnMsg));
			}
			log.debug("繳款單作業 - 利息試算、重算 PaymentProcessAction.doCompute() 完成 ... ");
			return mapping.findForward(ConstantKey.FORWARD_QUERY_INTEREST_LIST);
		} catch (Exception e) {
			// 設定查詢失敗訊息
			log.error("PaymentProcessAction.doCompute() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
			return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
		}

	}

	/**
	 * 繳款單作業 -利息計算作業 (BAPM0D013M.jsp) <br>
	 * 按下「刪除」的動作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doDeleteInterest(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("繳款單作業 - 利息計算作業  - 刪除 PaymentProcessAction.doDeleteInterest() 開始 ... ");
		HttpSession session = request.getSession();
		PaymentProcessQueryForm queryForm = (PaymentProcessQueryForm) form;
		try {
			CaseSessionHelper.removePaymentInterestDetailList(request);
			CaseSessionHelper.removePaymentInterestDetailListForDb(request);
			CaseSessionHelper.removePaymentAssignDetailList(request);
			CaseSessionHelper.removePaymentAssignLastList(request);
			queryForm.setTotTrgAmt(BigDecimal.ZERO);
			queryForm.setInterestTryStage(BigDecimal.ZERO);
			queryForm.setMonthlyPayAmt(BigDecimal.ZERO);
			FormSessionHelper.setPaymentProcessQueryForm(queryForm, request);
			log.debug("繳款單作業 - 利息計算作業  - 刪除 PaymentProcessAction.doDeleteInterest() 完成 ... ");
			return mapping.findForward(ConstantKey.FORWARD_QUERY_UNACPDTL_LIST);
		} catch (Exception e) {
			// 設定查詢失敗訊息
			log.error("PaymentProcessAction.doDeleteInterest() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
			return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
		}
	}

	/**
	 * 繳款單作業 - 開單明細維護作業(BAPM0D013M.jsp) <br>
	 * 按下「列印試算結果」的動作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doPrintInterest(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("繳款單作業 - 利息計算作業   - 列印試算結果 PaymentProcessAction.doPrintInterest() 開始 ... ");
		HttpSession session = request.getSession();
		PaymentProcessQueryForm queryForm = (PaymentProcessQueryForm) form;
		try {
			String[] adjInterestList = queryForm.getAdjInterestList().split(",");
			List<PaymentStageDtlCase> caseList = CaseSessionHelper.getPaymentInterestDetailList(request);
			List<PaymentStageDtlCase> caseListDb = CaseSessionHelper.getPaymentInterestDetailListForDb(request);

			Map<String, BigDecimal> subData = new HashMap<String, BigDecimal>();
			for (int i = 0; i < caseList.size(); i++) {
				if (BigDecimal.valueOf(Long.parseLong(adjInterestList[i]))
						.compareTo(caseList.get(i).getAdjInterest()) != 0) {
					subData.put(String.valueOf(caseList.get(i).getNowStage()),
							BigDecimal.valueOf(Long.parseLong(adjInterestList[i])));
					caseList.get(i).setAdjInterest(BigDecimal.valueOf(Long.parseLong(adjInterestList[i])));
				}
			}
			int flag = 0;
			int x = 0;
			BigDecimal adjInterest = BigDecimal.ZERO;
			for (int j = 0; j < caseListDb.size(); j++) {
				PaymentStageDtlCase caseDb = caseListDb.get(j);
				// 第i期的資料中調整利率有變動而且不為重複年度的第二年
				if (subData.get(String.valueOf(caseDb.getNowStage())) != null) {// 有修改adjInterst
					if ((j + 1) < caseListDb.size()) {
						if (caseDb.getNowStage() == caseListDb.get(j + 1).getNowStage()) {
							if (x == 0) {
								adjInterest = subData.get(String.valueOf(caseDb.getNowStage()))
										.subtract(caseListDb.get(j + 1).getTryInterest());
								flag = j;
							} else {
								adjInterest = adjInterest.subtract(caseDb.getAdjInterest());
							}
							x = x + 1;
							caseListDb.get(flag).setAdjInterest(adjInterest);
						} else {
							if (x < 1) {
								adjInterest = subData.get(String.valueOf(caseDb.getNowStage()));
								caseListDb.get(j).setAdjInterest(adjInterest);
								x = 0;
							} else {
								x = 0;
							}
						}
					} else {
						if (x == 0) {
							caseListDb.get(j).setAdjInterest(subData.get(String.valueOf(caseDb.getNowStage())));
						}
					}
				}
			}

			// RPT START====================================================================
			ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

			PaymentInterestReport report = new PaymentInterestReport();
			baoOutput = report.doReport(queryForm, caseListDb);

			String printDate = DateUtility.getNowChineseDate();

			// 設定回傳回 Client 端之檔案大小, 若不設定,
			// 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
            response.setHeader("Content-disposition", "attachment; filename=\"" + Encode.forJava(queryForm.getPaymentNo()) + "_tryPaymentInterest_" + Encode.forJava(printDate) + ".pdf" + "\"");
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
			} catch (Exception e) {
			} finally {
				if (baoOutput != null)
					baoOutput.close();
				if (sout != null)
					sout.close();
			}
			FormSessionHelper.setPaymentProcessQueryForm(queryForm, request);
			CaseSessionHelper.setPaymentInterestDetailList(caseList, request);
			CaseSessionHelper.setPaymentInterestDetailListForDb(caseListDb, request);
			log.debug("繳款單作業 - 利息計算作業   - 列印試算結果 PaymentProcessAction.doPrintInterest()完成 ... ");
			return null;
		} catch (Exception e) {
			// 設定查詢失敗訊息
			log.error("PaymentProcessAction.doPrintInterest() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
			return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
		}
	}

	/**
	 * 繳款單作業 - 利息計算作業(BAPM0D013M.jsp) <br>
	 * 按下「確認」的動作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("繳款單作業 - 利息計算作業- 確認  PaymentProcessAction.doConfirm() 開始 ... ");
		HttpSession session = request.getSession();
		PaymentProcessQueryForm queryForm = (PaymentProcessQueryForm) form;
		try {
			String[] adjInterestList = queryForm.getAdjInterestList().split(",");

			List<PaymentStageDtlCase> caseList = CaseSessionHelper.getPaymentInterestDetailList(request);
			List<PaymentStageDtlCase> caseListDb = CaseSessionHelper.getPaymentInterestDetailListForDb(request);

			Map<String, BigDecimal> subData = new HashMap<String, BigDecimal>();
			for (int i = 0; i < caseList.size(); i++) {
				if (BigDecimal.valueOf(Long.parseLong(adjInterestList[i]))
						.compareTo(caseList.get(i).getAdjInterest()) != 0) {
					subData.put(String.valueOf(caseList.get(i).getNowStage()),
							BigDecimal.valueOf(Long.parseLong(adjInterestList[i])));
					caseList.get(i).setAdjInterest(BigDecimal.valueOf(Long.parseLong(adjInterestList[i])));
				}
			}
			int flag = 0;
			for (int j = 0; j < caseListDb.size(); j++) {
				PaymentStageDtlCase caseDb = caseListDb.get(j);
				// 第i期的資料中調整利率有變動而且不為重複年度的第二年
				if (subData.get(String.valueOf(caseDb.getNowStage())) != null && (caseDb.getNowStage() != flag)) {
					// 最後一筆不會有跨年度的問題
					if (j < (caseListDb.size() - 1)) {
						// 若與下一期期別相同
						if (caseListDb.get(j).getNowStage() == caseListDb.get(j + 1).getNowStage()) {
							BigDecimal bd = subData.get(String.valueOf(caseDb.getNowStage()))
									.subtract(caseListDb.get(j + 1).getAdjInterest());
							caseDb.setAdjInterest(bd);
							flag = caseDb.getNowStage();
						} else {// subData.get(String.valueOf(caseList.get(j).getNowStage()))
							caseDb.setAdjInterest(subData.get(String.valueOf(caseDb.getNowStage())));
						}
					}

				}
			}
			queryForm.setAfterInterest("Y");
			FormSessionHelper.setPaymentProcessQueryForm(queryForm, request);
			CaseSessionHelper.setPaymentInterestDetailList(caseList, request);
			CaseSessionHelper.setPaymentInterestDetailListForDb(caseListDb, request);
			saveMessages(session, DatabaseMessageHelper.getSaveSuccessMessage());
			log.debug("繳款單作業 - 利息計算作業 - 確認  PaymentProcessAction.doConfirm() 完成 ... ");
			return mapping.findForward(ConstantKey.FORWARD_QUERY_INTEREST_LIST);
		} catch (Exception e) {
			// 設定查詢失敗訊息
			log.error("PaymentProcessAction.doConfirm() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
			return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
		}

	}

	/**
	 * 繳款單作業 - 開單明細維護作業(BAPM0D012M.jsp) <br>
	 * 按下「確認」的動作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doSaveConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("繳款單作業 - 開單明細維護作業 - PaymentProcessAction.doSaveConfirm() 開始 ... ");
		HttpSession session = request.getSession();
		PaymentProcessQueryForm queryForm = (PaymentProcessQueryForm) form;
		try {
			if (queryForm.getTotTrgAmt().intValue() == 0 && queryForm.getInterestTryStage().intValue() == 0
					&& queryForm.getMonthlyPayAmt().intValue() == 0) {
				queryForm.setTotInterst(BigDecimal.ZERO);
			}
			queryForm.setIsPrint("0");// 進來都控制為不能列印，除非儲存了後才能列印
			List<PaymentProcessCase> resultList = CaseSessionHelper.getPaymentProcessDetailList(request);
			String[] idForConfirm = queryForm.getIdForConfirm().split(",");
			String[] payAmtConfirm = queryForm.getPayAmtForConfirm().split(",");
			String[] writeoffSeqNo = queryForm.getSeqNoForConfirm().split(",");
			for (int i = 0; i < resultList.size(); i++) {
				resultList.get(i).setWriteoffSeqNo("");
				for (int j = 0; j < idForConfirm.length; j++) {
					if (i == Integer.parseInt(idForConfirm[j])) {
						resultList.get(i).setPayAmt(BigDecimal.valueOf(Integer.parseInt(payAmtConfirm[j])));
						resultList.get(i).setWriteoffSeqNo(writeoffSeqNo[j]);
					}

					if (i == Integer.parseInt(queryForm.getMapnoMk()) - 1) {
						resultList.get(i).setMapnoMk("1");
					} else {
						resultList.get(i).setMapnoMk("0");
					}
				}
			}
			CaseSessionHelper.setPaymentProcessDetailList(resultList, request);
			List<PaymentStageDtlCase> paymentAssignList = paymentService.getPaymentStageDtl(queryForm);
			// 修改
			if (StringUtils.isNotBlank(queryForm.getModifyString())) {
				List<PaymentStageDtlCase> oldList = CaseSessionHelper.getPaymentAssignDetailList(request);
				if (oldList == null || oldList.size() <= 0 || oldList.size() != paymentAssignList.size()
						|| paymentAssignList.get(0).getRePayAmt().intValue() != oldList.get(0).getRePayAmt()
								.intValue()) {
					// 原來就有利息資料(有修改過)
					if (oldList != null && oldList.size() > 0) {
						// 利息資料改變
						if (oldList.size() != paymentAssignList.size()) {
							FormSessionHelper.setPaymentProcessQueryForm(queryForm, request);
							BigDecimal totInterest = new BigDecimal(0);
							for (int i = 0; i < oldList.size(); i++) {
								totInterest = totInterest.add(oldList.get(i).getPayInterest());
							}
							paymentAssignList.get(paymentAssignList.size() - 1).setPayInterest(totInterest);
							CaseSessionHelper.setPaymentAssignDetailList(paymentAssignList, request);
							saveMessages(request, DatabaseMessageHelper.getInterestTryMessage());
							log.debug("繳款單作業 - 開單明細維護作業 - PaymentProcessAction.doSaveConfirm() 完成 (有變更利息資料) ... ");
							return mapping.findForward(ConstantKey.FORWARD_PAYMENT_ASSIGN_LIST);
						}
					}

					// 本來沒有利息資料
					if (paymentAssignList.size() > 0 && paymentAssignList != null) {
						if (queryForm.getTotInterst() != null) {
							queryForm.setPayTotIntAmt(queryForm.getPayTotAmt().add(queryForm.getTotInterst()));
						} else {
							queryForm.setPayTotIntAmt(queryForm.getPayTotAmt());
						}
						queryForm.setTotStage(queryForm.getTotAmtStage().add(queryForm.getInterestStage()));// 總共期數(利息期數+本金期數)

						FormSessionHelper.setPaymentProcessQueryForm(queryForm, request);
						CaseSessionHelper.setPaymentAssignDetailList(paymentAssignList, request);
						log.debug("繳款單作業 - 開單明細維護作業 - PaymentProcessAction.doSaveConfirm() 完成 (新增利息資料) ... ");
						return mapping.findForward(ConstantKey.FORWARD_PAYMENT_ASSIGN_LIST);
					} else {
						log.debug("繳款單作業 - 開單確認作業 - PaymentProcessAction.doSaveConfirm() 完成 (無利息資料)... ");
						saveMessages(session, DatabaseMessageHelper.getNoDataMessage());
						return mapping.findForward(ConstantKey.FORWARD_QUERY_UNACPDTL_LIST);
					}
				} else {
					// 期數不變，比對利息的值
					// 比對interest
					BigDecimal preTotInterest = new BigDecimal(0);
					BigDecimal payTotInterest = new BigDecimal(0);
					for (int i = 0; i < oldList.size(); i++) {
						PaymentStageDtlCase oldCase = oldList.get(i);
						PaymentStageDtlCase newCase = paymentAssignList.get(i);
						preTotInterest = preTotInterest.add(oldCase.getPayInterest());
						payTotInterest = payTotInterest.add(newCase.getPayInterest());
					}
					// 值不一樣(有修改)
					if (payTotInterest.compareTo(preTotInterest) != 0
							&& payTotInterest.compareTo(BigDecimal.ZERO) != 0) {
						if (paymentAssignList.size() > 0 && paymentAssignList != null) {
							if (queryForm.getTotInterst() != null) {
								queryForm.setPayTotIntAmt(queryForm.getPayTotAmt().add(queryForm.getTotInterst()));
							} else {
								queryForm.setPayTotIntAmt(queryForm.getPayTotAmt());
							}
							queryForm.setTotStage(queryForm.getTotAmtStage().add(queryForm.getInterestStage()));// 總共期數(利息期數+本金期數)

							FormSessionHelper.setPaymentProcessQueryForm(queryForm, request);
							CaseSessionHelper.setPaymentAssignDetailList(paymentAssignList, request);
							log.debug("繳款單作業 - 開單確認作業 - PaymentProcessAction.doSaveConfirm() 完成(有修改利息的值) ()... ");
							return mapping.findForward(ConstantKey.FORWARD_PAYMENT_ASSIGN_LIST);
						} else {
							log.debug("繳款單作業 - 開單確認作業 - PaymentProcessAction.doSaveConfirm() 完成 (無利息的值)... ");
							saveMessages(session, DatabaseMessageHelper.getNoDataMessage());
							return mapping.findForward(ConstantKey.FORWARD_QUERY_UNACPDTL_LIST);
						}
					} else {
						FormSessionHelper.setPaymentProcessQueryForm(queryForm, request);
						// 沒有修改過
						if (!StringUtils.equals(queryForm.getPaymentDateLine(), oldList.get(0).getPaymentDateLine())) {
							oldList.get(0).setPaymentDateLine(queryForm.getPaymentDateLine());
						}
						CaseSessionHelper.setPaymentAssignDetailList(oldList, request);
						log.debug("繳款單作業 - 開單確認作業 - PaymentProcessAction.doSaveConfirm() 完成... ");
						return mapping.findForward(ConstantKey.FORWARD_PAYMENT_ASSIGN_LIST);
					}
				}
			} else {
				if (paymentAssignList.size() > 0 && paymentAssignList != null) {
					if (queryForm.getTotInterst() != null) {
						queryForm.setPayTotIntAmt(queryForm.getPayTotAmt().add(queryForm.getTotInterst()));
					} else {
						queryForm.setPayTotIntAmt(queryForm.getPayTotAmt());
					}
					queryForm.setTotStage(queryForm.getTotAmtStage().add(queryForm.getInterestStage()));// 總共期數(利息期數+本金期數)

					FormSessionHelper.setPaymentProcessQueryForm(queryForm, request);
					CaseSessionHelper.setPaymentAssignDetailList(paymentAssignList, request);
					log.debug("繳款單作業 - 開單確認作業 - PaymentProcessAction.doSaveConfirm() 新增資料 完成 ... ");
					return mapping.findForward(ConstantKey.FORWARD_PAYMENT_ASSIGN_LIST);
				} else {
					log.debug("繳款單作業 - 開單確認作業 - PaymentProcessAction.doSaveConfirm() 新增資料 完成 (無利息資料)... ");
					saveMessages(session, DatabaseMessageHelper.getNoDataMessage());
					return mapping.findForward(ConstantKey.FORWARD_QUERY_UNACPDTL_LIST);
				}
			}
		} catch (Exception e) {
			// 設定查詢失敗訊息
			log.error("PaymentProcessAction.doSaveConfirm() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
			return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
		}
	}

	/**
	 * 繳款單作業 - 開單明細維護作業(BAPM0D014M.jsp) <br>
	 * 按下「分配明細」的動作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doAssign(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("繳款單作業 - 開單確認作業 - 分配明細頁面  PaymentProcessAction.doAssign() 開始... ");
		HttpSession session = request.getSession();
		PaymentProcessQueryForm queryForm = (PaymentProcessQueryForm) form;
		try {
			List<PaymentStageDtlCase> paymentAssignList = CaseSessionHelper.getPaymentAssignDetailList(request);
			String[] dateList = queryForm.getPaymentDateLineList().split(",");
			String[] execList = queryForm.getExecList().split(",");
			String[] interestList = queryForm.getInterstList().split(",");
			int execAmt = 0;
			for (int i = 0; i < paymentAssignList.size(); i++) {
				paymentAssignList.get(i).setPaymentDateLine(dateList[i]);
				paymentAssignList.get(i).setPayInterest(BigDecimal.valueOf(Long.parseLong(interestList[i])));
				paymentAssignList.get(i).setExecAmt(BigDecimal.valueOf(Long.parseLong(execList[i])));
				execAmt = execAmt + Integer.parseInt(execList[i]);
			}
			queryForm.setTotAmt(
					BigDecimal.valueOf(execAmt).add(queryForm.getPayTotAmt().add(queryForm.getTotInterst())));
			List<PaymentProcessCase> resultList = CaseSessionHelper.getPaymentProcessDetailList(request);
			List<PaymentStageDtlCase> listData = paymentService.getAssignPaymentData(resultList, paymentAssignList);
			for (int i = 0; i < listData.size(); i++) {
				if (i + 1 < listData.size()) {
					if (listData.get(i).getNowStage() == listData.get(i + 1).getNowStage()) {
						listData.get(i + 1).setExecAmt(BigDecimal.ZERO);
						listData.get(i + 1).setPayInterest(BigDecimal.ZERO);
					}
				}

			}
			if (listData.size() == 0 || listData == null) {
				log.debug("繳款單作業 - 開單確認作業 - 分配明細 PaymentProcessAction.doAssign() 完成 ..查無資料. ");
				saveMessages(session, DatabaseMessageHelper.getNoProcDataMessage());
				return mapping.findForward(ConstantKey.FORWARD_PAYMENT_ASSIGN_LIST);
			} else {
				log.debug("繳款單作業 - 開單維護作業 - 分配明細  PaymentProcessAction.doAssign() 完成 ... ");
				CaseSessionHelper.setPaymentAssignLastList(listData, request);
				return mapping.findForward(ConstantKey.FORWARD_PAYMENT_ASSIGN_LAST_LIST);
			}
		} catch (Exception e) {
			// 設定查詢失敗訊息
			log.error("PaymentProcessAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
			return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
		}
	}

	/**
	 * 繳款單作業 - 開單明細維護作業(BAPM0D013M.jsp) <br>
	 * 按下「列印」的動作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doPrint(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("繳款單作業 - 開單維護作業 -開單確認- 點選列印 PaymentProcessAction.doPrint() 開始 ... ");
		HttpSession session = request.getSession();
		PaymentProcessQueryForm queryForm = (PaymentProcessQueryForm) form;
		try {
			int count = paymentService.getPaymentCount(queryForm.getPaymentNo());
			if (count <= 0) {
				log.debug("繳款單作業 - 開單維護作業 - 查詢頁面  PaymentProcessAction.doPrint() 完成 ..查無資料. ");
				saveMessages(session, DatabaseMessageHelper.getSaveDataMessage());
				return mapping.findForward(ConstantKey.FORWARD_PAYMENT_ASSIGN_LIST);
			}

			List<PaymentStageDtlCase> paymentList = CaseSessionHelper.getPaymentAssignDetailList(request);
			List<PaymentProcessCase> apnoList = CaseSessionHelper.getPaymentProcessDetailList(request);
			String printDate = DateUtility.getNowChineseDate();
			ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();
			String mApno = "";
			for (int i = 0; i < apnoList.size(); i++) {
				if (StringUtils.equals(apnoList.get(i).getMapnoMk(), "1")) {
					mApno = apnoList.get(i).getApno();
				}
			}
			String appName = "";
			String mPayKind = "";
			String paymentSex = "";
			PaymentProcessCase paymentData = paymentService.getPaymentBaseData(mApno, queryForm.getIdn());
			if (paymentData != null) {
				appName = paymentData.getBenName();
				mPayKind = paymentData.getPayKind();
				paymentSex = paymentData.getPaymentSex();
			}
			String evtName = paymentService.getEvtNameFromBaappbase(queryForm.getIdn());
			String empExt = queryService.getEmpExtData(mApno);// 分機
			String[] dateList = queryForm.getPaymentDateLineList().split(",");
			String[] execList = queryForm.getExecList().split(",");
			String[] interestList = queryForm.getInterstList().split(",");
			int execAmt = 0;
			int interestAmt = 0;
			for (int i = 0; i < paymentList.size(); i++) {
				paymentList.get(i).setPaymentDateLine(dateList[i]);
				paymentList.get(i).setPayInterest(BigDecimal.valueOf(Long.parseLong(interestList[i])));
				paymentList.get(i).setExecAmt(BigDecimal.valueOf(Long.parseLong(execList[i])));
				execAmt = execAmt + Integer.parseInt(execList[i]);
				interestAmt = interestAmt + Integer.parseInt(interestList[i]);

			}
			queryForm.setTotExec(BigDecimal.valueOf(execAmt));
			queryForm.setTotInterst(BigDecimal.valueOf(interestAmt));
			queryForm.setTotAmt(
					BigDecimal.valueOf(execAmt).add(queryForm.getPayTotAmt().add(queryForm.getTotInterst())));
			queryForm.setPrtDate(DateUtility.getNowChineseDate());
			FormSessionHelper.setPaymentProcessQueryForm(queryForm, request);
			PaymentReport report = new PaymentReport();
			baoOutput = report.doReport(null, mApno, evtName, appName, mPayKind, empExt, paymentList, apnoList,
					queryForm, paymentSex);
			// 設定回傳回 Client 端之檔案大小, 若不設定,
			// 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
			// response.setHeader("Content-disposition", "attachment;
			// filename=\"MonthlyRpt23_" + printDate + ".pdf" + "\"");
            response.setHeader("Content-disposition", "attachment; filename=\"" + "PaymentReport_" + Encode.forJava(printDate) + ".pdf" + "\"");
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
			} catch (Exception e) {
			} finally {
				if (baoOutput != null)
					baoOutput.close();
				if (sout != null)
					sout.close();
				paymentService.updatePrintDate(DateUtility.getNowWestDate(), queryForm.getPaymentNo(), paymentList);

			}
			log.debug("繳款單作業 - 開單維護作業 -開單確認- 點選列印 PaymentProcessAction.doPrint() 完成 ... ");
			return null;
		} catch (Exception e) {
			// 設定查詢失敗訊息
			log.error("PaymentProcessAction.doPrint() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
			return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
		}
	}

	/**
	 * 繳款單作業 - 開單明細維護作業(BAPM0D014M.jsp) <br>
	 * 按下「確認」的動作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doSavePayment(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("繳款單作業 - 開單確認作業  - 點選確認 PaymentProcessAction.doSavePayment() 開始 ... ");
		HttpSession session = request.getSession();
		UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
		PaymentProcessQueryForm queryForm = (PaymentProcessQueryForm) form;
		try {
			List<PaymentProcessQueryCase> qryData = CaseSessionHelper.getPaymentProcessQueryList(request);// BAPM0D11A
			List<PaymentProcessCase> qryDataUnacpdtl = CaseSessionHelper.getPaymentProcessDetailList(request);// BAPM0D12M
			List<PaymentStageDtlCase> prsInterest = CaseSessionHelper.getPaymentInterestDetailListForDb(request);// BAPM0D013M
			List<PaymentStageDtlCase> dtlPayment = CaseSessionHelper.getPaymentAssignDetailList(request);// BAPM0D014M
			List<PaymentStageDtlCase> assPaymentAuto = new ArrayList<PaymentStageDtlCase>();
			List<PaymentStageDtlCase> paymentAssignList = CaseSessionHelper.getPaymentAssignDetailList(request);
			String[] dateList = queryForm.getPaymentDateLineList().split(",");
			String[] execList = queryForm.getExecList().split(",");
			String[] interestList = queryForm.getInterstList().split(",");
			int execAmt = 0;
			for (int i = 0; i < paymentAssignList.size(); i++) {
				paymentAssignList.get(i).setPayInterest(BigDecimal.valueOf(Long.parseLong(interestList[i])));
				paymentAssignList.get(i).setPaymentDateLine(dateList[i]);
				paymentAssignList.get(i).setExecAmt(BigDecimal.valueOf(Long.parseLong(execList[i])));
				execAmt = execAmt + Integer.parseInt(execList[i]);
			}
			queryForm.setTotAmt(
					BigDecimal.valueOf(execAmt).add(queryForm.getPayTotAmt().add(queryForm.getTotInterst())));
			List<PaymentProcessCase> resultList = CaseSessionHelper.getPaymentProcessDetailList(request);
			List<PaymentStageDtlCase> listData = paymentService.getAssignPaymentData(resultList, paymentAssignList);
			for (int i = 0; i < listData.size(); i++) {
				if (i + 1 < listData.size()) {
					if (listData.get(i).getNowStage() == listData.get(i + 1).getNowStage()) {
						listData.get(i + 1).setExecAmt(BigDecimal.ZERO);
						listData.get(i + 1).setPayInterest(BigDecimal.ZERO);
					}
				}

			}
			CaseSessionHelper.setPaymentAssignLastList(listData, request);// BAPM0D015M
			assPaymentAuto = CaseSessionHelper.getPaymentAssignLastList(request);
			if (StringUtils.isBlank(insertUpdate)) {
				paymentService.updateEveryPaymentData(userData, queryForm, qryData, qryDataUnacpdtl, prsInterest,
						dtlPayment, assPaymentAuto);
			} else {
				paymentService.insertEveryPaymentData(userData, queryForm, qryData, qryDataUnacpdtl, prsInterest,
						dtlPayment, assPaymentAuto);
			}

			if (StringUtils.isBlank(insertUpdate)) {
				queryForm.setIsPrint("1");// 儲存後才可以列印
				FormSessionHelper.setPaymentProcessQueryForm(queryForm, request);
				saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());
			} else {
				queryForm.setIsPrint("1");// 儲存後才可以列印
				FormSessionHelper.setPaymentProcessQueryForm(queryForm, request);
				saveMessages(session, DatabaseMessageHelper.getSaveSuccessMessage());
			}
			log.debug("繳款單作業 - 開單確認作業  - 點選確認 PaymentProcessAction.doSavePayment() 完成 ... ");
			return mapping.findForward(ConstantKey.FORWARD_PAYMENT_ASSIGN_LIST);
		} catch (Exception e) {
			// 設定查詢失敗訊息
			log.error("PaymentProcessAction.doSavePayment() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getSaveFailMessage());
			return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
		}

	}

	/**
	 * 繳款單作業 - 修改作業(BAPM0D011M.jsp) <br>
	 * 按下「確認」的動作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doModify(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("繳款單作業 - 開單維護作業 - 修改作業- PaymentProcessAction.doModify() 開始... ");
		HttpSession session = request.getSession();
		PaymentProcessQueryForm queryForm = (PaymentProcessQueryForm) form;
		try {
			PaymentUpdateCase qryData = paymentService.getPaymentProcessQueryList(queryForm.getPaymentNoforModify());// Form會用到的
			if (qryData != null) {
				BeanUtility.copyProperties(queryForm, qryData);
				if (StringUtils.isNotBlank(qryData.getPrtDate())) {
					queryForm.setPrtDate(qryData.getPrtDate());
				} else {
					queryForm.setPrtDate("");
				}
				FormSessionHelper.setPaymentProcessQueryForm(queryForm, request);
			} else {
				// 設定查詢失敗訊息
				saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
				return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
			}
			List<PaymentProcessCase> qryDataUnacpdtl = paymentService
					.getPaymentProcessDetailList(queryForm.getPaymentNoforModify());// BAPM0D012M
			if (qryDataUnacpdtl == null && qryDataUnacpdtl.size() <= 0) {
				// 設定查詢失敗訊息
				saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
				return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
			} else {
				CaseSessionHelper.setPaymentProcessDetailList(qryDataUnacpdtl, request);
			}
			List<PaymentStageDtlCase> prsInterest = paymentService
					.getPaymentInterestDetailListForDb(queryForm.getPaymentNo());// BAPM0D013M
			if (prsInterest != null && prsInterest.size() > 0) {
				CaseSessionHelper.setPaymentInterestDetailListForDb(prsInterest, request);
			}
			List<PaymentStageDtlCase> assPayment = paymentService
					.getPaymentAssignDetailListForDb(queryForm.getPaymentNo());// BAPM0D014,BAPM0D015
			if (assPayment != null && assPayment.size() > 0) {
				CaseSessionHelper.setPaymentAssignLastList(assPayment, request);
			}

			List<PaymentStageDtlCase> confirmPayment = paymentService.getPaymentAssignDetailList(
					queryForm.getPaymentNo(), assPayment.get(assPayment.size() - 1).getNowStage());// BAPM0D014,BAPM0D015
			if (confirmPayment != null && confirmPayment.size() > 0) {
				CaseSessionHelper.setPaymentAssignDetailList(confirmPayment, request);
			}
			BigDecimal tmpInterestExec = BigDecimal.ZERO;
			for (int i = 0; i < confirmPayment.size(); i++) {
				tmpInterestExec = tmpInterestExec.add(confirmPayment.get(i).getExecAmt())
						.add(confirmPayment.get(i).getPayInterest());
			}
			queryForm.setPayTotAmt(queryForm.getPayTotAmt().subtract(tmpInterestExec));
			FormSessionHelper.setPaymentProcessQueryForm(queryForm, request);

			log.debug("繳款單作業 - 開單維護作業 - 修改作業- PaymentProcessAction.doModify() 完成... ");
			if (StringUtils.isBlank(qryData.getPrtDate())) {
				return mapping.findForward(ConstantKey.FORWARD_QUERY_UNACPDTL_LIST);
			} else {
				return mapping.findForward(ConstantKey.FORWARD_QUERY_UNACPDTL_PRTDATE_LIST);
			}
		} catch (Exception e) {
			// 設定查詢失敗訊息
			log.error("PaymentProcessAction.doModify() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
			return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
		}

	}

	public ActionForward doBackSession(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		try {
			return mapping.findForward(ConstantKey.FORWARD_QUERY_UNACPDTL_LIST);
		} catch (Exception e) {
			// 設定查詢失敗訊息
			log.error("PaymentProcessAction.doBackSession() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
			return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
		}
	}

	/**
	 * 繳款單作業 - 修改作業(BAPM0D016M.jsp) <br>
	 * 按下「確認」的動作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doUpdateBasic(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("繳款單作業 - 開單明細維護作業 - 按下確認- PaymentProcessAction.doUpdateBasic() 開始 ... ");
		HttpSession session = request.getSession();
		UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
		PaymentProcessQueryForm queryForm = (PaymentProcessQueryForm) form;
		try {
			List<PaymentProcessCase> resultList = CaseSessionHelper.getPaymentProcessDetailList(request);
			String[] idForConfirm = queryForm.getIdForConfirm().split(",");
			String[] payAmtConfirm = queryForm.getPayAmtForConfirm().split(",");
			String[] writeoffSeqNo = queryForm.getSeqNoForConfirm().split(",");
			for (int i = 0; i < resultList.size(); i++) {
				resultList.get(i).setWriteoffSeqNo("");
				for (int j = 0; j < idForConfirm.length; j++) {
					if (i == Integer.parseInt(idForConfirm[j])) {
						resultList.get(i).setPayAmt(BigDecimal.valueOf(Integer.parseInt(payAmtConfirm[j])));
						resultList.get(i).setWriteoffSeqNo(writeoffSeqNo[j]);
					}
					if (i == Integer.parseInt(queryForm.getMapnoMk()) - 1) {
						resultList.get(i).setMapnoMk("1");
					} else {
						resultList.get(i).setMapnoMk("0");
					}
				}
			}
			CaseSessionHelper.setPaymentProcessDetailList(resultList, request);
			paymentService.updateBasicData(queryForm, resultList, userData);
			saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());
			log.debug("繳款單作業 - 開單明細維護作業 - 按下確認- PaymentProcessAction.doUpdateBasic() 完成... ");
			return mapping.findForward(ConstantKey.FORWARD_QUERY_UNACPDTL_PRTDATE_LIST);
		} catch (Exception e) {
			// 設定查詢失敗訊息
			log.error("PaymentProcessAction.doUpdateBasic() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
			return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
		}
	}

	public static void setLog(Log log) {
		PaymentProcessAction.log = log;
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	public void setQueryService(QueryService queryService) {
		this.queryService = queryService;
	}
}
