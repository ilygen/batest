package tw.gov.bli.ba.rpt.actions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
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
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01Case;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01Case;
import tw.gov.bli.ba.rpt.cases.SurvivorReviewRpt01Case;
import tw.gov.bli.ba.rpt.forms.OldAgeReviewRpt01Form;
import tw.gov.bli.ba.rpt.report.DisableReviewRpt01Kind36Report;
import tw.gov.bli.ba.rpt.report.DisableReviewRpt01Report;
import tw.gov.bli.ba.rpt.report.OldAgeReviewRpt01Report;
import tw.gov.bli.ba.rpt.report.SurvivorReviewRpt01Report;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 列印作業 - 審核作業相關報表 - 勞保老年年金給付受理編審清單 - 查詢頁面 (balp0d010l.jsp)
 * 
 * @author Goston
 */
public class OldAgeReviewRpt01Action extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(OldAgeReviewRpt01Action.class);

	private RptService rptService;

	// private UpdateService updateService;
	/**
	 * 列印作業 - 審核作業相關報表 - 勞保老年年金給付受理編審清單 - 查詢頁面 (balp0d010l.jsp) <br>
	 * 按下「列印」的動作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		OldAgeReviewRpt01Form queryForm = (OldAgeReviewRpt01Form) form;
		String payKindMsg = queryForm.getApNo1Begin();

		if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payKindMsg)) {
			log.debug("執行 列印作業 - 審核作業相關報表 - 勞保老年年金給付受理編審清單 - 查詢頁面 OldAgeReviewRpt01Action.doReport() 開始 ... ");
		} else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payKindMsg)) {
			log.debug("執行 列印作業 - 審核作業相關報表 - 勞保失能年金給付受理編審清單 - 查詢頁面 OldAgeReviewRpt01Action.doReport() 開始 ... ");
		} else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payKindMsg)) {
			log.debug("執行 列印作業 - 審核作業相關報表 - 勞保遺屬年金給付受理編審清單 - 查詢頁面 OldAgeReviewRpt01Action.doReport() 開始 ... ");
		} else {
			log.debug("執行 列印作業 - 審核作業相關報表 - 勞保年金給付受理編審清單 - 查詢頁面 OldAgeReviewRpt01Action.doReport() 開始 ... ");
		}

		HttpSession session = request.getSession();
		UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

		String action = request.getParameter("action");
		// 勾選輸入受理編號起訖
		if ("single".equals(action)) {
			try {
				String apNoBegin = StringUtils.upperCase(StringUtils.defaultString(queryForm.getApNo1Begin()))
						+ StringUtils.defaultString(queryForm.getApNo2Begin())
						+ StringUtils.defaultString(queryForm.getApNo3Begin())
						+ StringUtils.defaultString(queryForm.getApNo4Begin());

				String apNoEnd = StringUtils.upperCase(StringUtils.defaultString(queryForm.getApNo1End()))
						+ StringUtils.defaultString(queryForm.getApNo2End())
						+ StringUtils.defaultString(queryForm.getApNo3End())
						+ StringUtils.defaultString(queryForm.getApNo4End());

				// 受理編號長度不滿 12 碼則不做查詢
				if (StringUtils.length(apNoBegin) != ConstantKey.APNO_LENGTH
						|| StringUtils.length(apNoEnd) != ConstantKey.APNO_LENGTH) {
					// 設定 無查詢資料 訊息
					saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
					return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
				}
				// 給付種類
				String payKind = apNoBegin.substring(0, 1);

				// 抓報表資料
				// [
				List caseList = null;
				if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payKind)) {// 老年
					caseList = new ArrayList<OldAgeReviewRpt01Case>();
					caseList = rptService.getOldAgeReviewRpt01DataBy(apNoBegin, apNoEnd);
				} else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payKind)) {// 失能
					caseList = new ArrayList<DisableReviewRpt01Case>();
					caseList = rptService.getDisableReviewRpt01DataBy(apNoBegin, apNoEnd);
					// 放入得請領起始年月
					if (caseList != null) {
						for (int i = 0; i < caseList.size(); i++) {
							// 放入得請領起始年月formatChineseYearMonthString
							DisableReviewRpt01Case caseData = (DisableReviewRpt01Case) caseList.get(i);
							// 處理日期前
							String ableapsYmBefor = rptService.getAbleapsYmForDisabledCheckMarkLevelAdjust(
									caseData.getApNo(), caseData.getBaappbaseId());

							if (StringUtils.isNotBlank(ableapsYmBefor)) {
								// 處理日期後 放入list內各筆資料之AbleapsYm 得請領起始年月
								String ableapsYmAfter = DateUtility.formatChineseYearMonthString(
										DateUtility.changeWestYearMonthType(ableapsYmBefor), false);
								caseData.setAbleapsYm(ableapsYmAfter);
							}
						}
					}
				} else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payKind)) {// 遺屬
					caseList = new ArrayList<SurvivorReviewRpt01Case>();
					caseList = rptService.getSurvivorReviewRpt01DataBy(apNoBegin, apNoEnd);
				}
				// ]

				if (caseList == null || caseList.size() == 0) {
					// 設定 無查詢資料 訊息
					saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
					return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
				} else {
					ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

					if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payKind)) {// 老年
						OldAgeReviewRpt01Report report = new OldAgeReviewRpt01Report();
						baoOutput = report.doReport(userData, caseList);
					} else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payKind)) {// 失能

						DisableReviewRpt01Case caseObj = new DisableReviewRpt01Case();
						caseObj = (DisableReviewRpt01Case) caseList.get(0);
						if ("36".equals(caseObj.getPayKind())) {
							DisableReviewRpt01Kind36Report report = new DisableReviewRpt01Kind36Report();
							baoOutput = report.doReport(userData, caseList);
						} else {
							DisableReviewRpt01Report report = new DisableReviewRpt01Report();
							baoOutput = report.doReport(userData, caseList);
						}
					} else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payKind)) {// 遺屬
						SurvivorReviewRpt01Report report = new SurvivorReviewRpt01Report();
						baoOutput = report.doReport(userData, caseList);
					}

					String printDate = DateUtility.getNowChineseDate();

					// 設定回傳回 Client 端之檔案大小, 若不設定,
					// 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
					if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payKind)) {// 老年
						response.setHeader("Content-disposition",
								"attachment; filename=\"OldAgeReviewRpt01_" + printDate + ".pdf" + "\"");
					} else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payKind)) {// 失能
						response.setHeader("Content-disposition",
								"attachment; filename=\"DisableReviewRpt01_" + printDate + ".pdf" + "\"");
					} else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payKind)) {// 遺屬
						response.setHeader("Content-disposition",
								"attachment; filename=\"SurvivorReviewRpt01_" + printDate + ".pdf" + "\"");
					}
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

					return null;
				}
			} catch (Exception e) {
				// 設定查詢失敗訊息
				if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payKindMsg)) {
					log.error("產製 勞保老年年金給付受理編審清單 發生錯誤:" + ExceptionUtility.getStackTrace(e));
				} else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payKindMsg)) {
					log.error("產製 勞保失能年金給付受理編審清單 發生錯誤:" + ExceptionUtility.getStackTrace(e));
				} else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payKindMsg)) {
					log.error("產製 勞保遺屬年金給付受理編審清單 發生錯誤:" + ExceptionUtility.getStackTrace(e));
				} else {
					log.error("產製 勞保年金給付受理編審清單 發生錯誤:" + ExceptionUtility.getStackTrace(e));
				}
				saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
				return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
			}
		}
		// 勾選輸入多筆受理編號
		if ("multiple".equals(action)) {
			try {
				String query1 = StringUtils.upperCase(StringUtils.defaultString(queryForm.getApNo1()))
						+ StringUtils.defaultString(queryForm.getApNo2())
						+ StringUtils.defaultString(queryForm.getApNo3())
						+ StringUtils.defaultString(queryForm.getApNo4());

				String query2 = StringUtils.upperCase(StringUtils.defaultString(queryForm.getApNo5()))
						+ StringUtils.defaultString(queryForm.getApNo6())
						+ StringUtils.defaultString(queryForm.getApNo7())
						+ StringUtils.defaultString(queryForm.getApNo8());

				String query3 = StringUtils.upperCase(StringUtils.defaultString(queryForm.getApNo9()))
						+ StringUtils.defaultString(queryForm.getApNo10())
						+ StringUtils.defaultString(queryForm.getApNo11())
						+ StringUtils.defaultString(queryForm.getApNo12());

				String query4 = StringUtils.upperCase(StringUtils.defaultString(queryForm.getApNo13()))
						+ StringUtils.defaultString(queryForm.getApNo14())
						+ StringUtils.defaultString(queryForm.getApNo15())
						+ StringUtils.defaultString(queryForm.getApNo16());

				String query5 = StringUtils.upperCase(StringUtils.defaultString(queryForm.getApNo17()))
						+ StringUtils.defaultString(queryForm.getApNo18())
						+ StringUtils.defaultString(queryForm.getApNo19())
						+ StringUtils.defaultString(queryForm.getApNo20());

				String query6 = StringUtils.upperCase(StringUtils.defaultString(queryForm.getApNo21()))
						+ StringUtils.defaultString(queryForm.getApNo22())
						+ StringUtils.defaultString(queryForm.getApNo23())
						+ StringUtils.defaultString(queryForm.getApNo24());

				String query7 = StringUtils.upperCase(StringUtils.defaultString(queryForm.getApNo25()))
						+ StringUtils.defaultString(queryForm.getApNo26())
						+ StringUtils.defaultString(queryForm.getApNo27())
						+ StringUtils.defaultString(queryForm.getApNo28());

				String query8 = StringUtils.upperCase(StringUtils.defaultString(queryForm.getApNo29()))
						+ StringUtils.defaultString(queryForm.getApNo30())
						+ StringUtils.defaultString(queryForm.getApNo31())
						+ StringUtils.defaultString(queryForm.getApNo32());

				String query9 = StringUtils.upperCase(StringUtils.defaultString(queryForm.getApNo33()))
						+ StringUtils.defaultString(queryForm.getApNo34())
						+ StringUtils.defaultString(queryForm.getApNo35())
						+ StringUtils.defaultString(queryForm.getApNo36());

				String query10 = StringUtils.upperCase(StringUtils.defaultString(queryForm.getApNo37()))
						+ StringUtils.defaultString(queryForm.getApNo38())
						+ StringUtils.defaultString(queryForm.getApNo39())
						+ StringUtils.defaultString(queryForm.getApNo40());

				String query11 = StringUtils.upperCase(StringUtils.defaultString(queryForm.getApNo41()))
						+ StringUtils.defaultString(queryForm.getApNo42())
						+ StringUtils.defaultString(queryForm.getApNo43())
						+ StringUtils.defaultString(queryForm.getApNo44());

				String query12 = StringUtils.upperCase(StringUtils.defaultString(queryForm.getApNo45()))
						+ StringUtils.defaultString(queryForm.getApNo46())
						+ StringUtils.defaultString(queryForm.getApNo47())
						+ StringUtils.defaultString(queryForm.getApNo48());

				String query13 = StringUtils.upperCase(StringUtils.defaultString(queryForm.getApNo49()))
						+ StringUtils.defaultString(queryForm.getApNo50())
						+ StringUtils.defaultString(queryForm.getApNo51())
						+ StringUtils.defaultString(queryForm.getApNo52());

				String query14 = StringUtils.upperCase(StringUtils.defaultString(queryForm.getApNo53()))
						+ StringUtils.defaultString(queryForm.getApNo54())
						+ StringUtils.defaultString(queryForm.getApNo55())
						+ StringUtils.defaultString(queryForm.getApNo56());

				String query15 = StringUtils.upperCase(StringUtils.defaultString(queryForm.getApNo57()))
						+ StringUtils.defaultString(queryForm.getApNo58())
						+ StringUtils.defaultString(queryForm.getApNo59())
						+ StringUtils.defaultString(queryForm.getApNo60());

				String query16 = StringUtils.upperCase(StringUtils.defaultString(queryForm.getApNo61()))
						+ StringUtils.defaultString(queryForm.getApNo62())
						+ StringUtils.defaultString(queryForm.getApNo63())
						+ StringUtils.defaultString(queryForm.getApNo64());

				String query17 = StringUtils.upperCase(StringUtils.defaultString(queryForm.getApNo65()))
						+ StringUtils.defaultString(queryForm.getApNo66())
						+ StringUtils.defaultString(queryForm.getApNo67())
						+ StringUtils.defaultString(queryForm.getApNo68());

				String query18 = StringUtils.upperCase(StringUtils.defaultString(queryForm.getApNo69()))
						+ StringUtils.defaultString(queryForm.getApNo70())
						+ StringUtils.defaultString(queryForm.getApNo71())
						+ StringUtils.defaultString(queryForm.getApNo72());

				String query19 = StringUtils.upperCase(StringUtils.defaultString(queryForm.getApNo73()))
						+ StringUtils.defaultString(queryForm.getApNo74())
						+ StringUtils.defaultString(queryForm.getApNo75())
						+ StringUtils.defaultString(queryForm.getApNo76());

				String query20 = StringUtils.upperCase(StringUtils.defaultString(queryForm.getApNo77()))
						+ StringUtils.defaultString(queryForm.getApNo78())
						+ StringUtils.defaultString(queryForm.getApNo79())
						+ StringUtils.defaultString(queryForm.getApNo80());
				List<String> queryList = new ArrayList<String>();
				queryList.add(query1);
				queryList.add(query2);
				queryList.add(query3);
				queryList.add(query4);
				queryList.add(query5);
				queryList.add(query6);
				queryList.add(query7);
				queryList.add(query8);
				queryList.add(query9);
				queryList.add(query10);
				queryList.add(query11);
				queryList.add(query12);
				queryList.add(query13);
				queryList.add(query14);
				queryList.add(query15);
				queryList.add(query16);
				queryList.add(query17);
				queryList.add(query18);
				queryList.add(query19);
				queryList.add(query20);

				// 把有輸入apNo的值放到List裡面
				List<String> haveValueList = new LinkedList<String>();
				for (String s : queryList) {
					if (StringUtils.length(s) == ConstantKey.APNO_LENGTH) {
						haveValueList.add(s);
					}
				}
//				String tmp;
//				boolean flag = false; // 是否發生交換
//				// 把List排序
//				for (int i = haveValueList.size() - 1; i >= 0; i--) {
//					for (int j = 0; j < i; j++) {
//						if (Long.parseLong(haveValueList.get(j).substring(1)) > Long
//								.parseLong(haveValueList.get(j + 1).substring(1))) {
//							tmp = haveValueList.get(j);
//							haveValueList.set(j, haveValueList.get(j + 1));
//							haveValueList.set(j + 1, tmp);
//							flag = true;
//						}
//					}
//					if (!flag)
//						break;
//				}

				// 受理編號長度不滿 12 碼則不做查詢
				if (StringUtils.length(query1) != ConstantKey.APNO_LENGTH
						&& StringUtils.length(query2) != ConstantKey.APNO_LENGTH
						&& StringUtils.length(query3) != ConstantKey.APNO_LENGTH
						&& StringUtils.length(query4) != ConstantKey.APNO_LENGTH
						&& StringUtils.length(query5) != ConstantKey.APNO_LENGTH
						&& StringUtils.length(query6) != ConstantKey.APNO_LENGTH
						&& StringUtils.length(query7) != ConstantKey.APNO_LENGTH
						&& StringUtils.length(query8) != ConstantKey.APNO_LENGTH
						&& StringUtils.length(query9) != ConstantKey.APNO_LENGTH
						&& StringUtils.length(query10) != ConstantKey.APNO_LENGTH
						&& StringUtils.length(query11) != ConstantKey.APNO_LENGTH
						&& StringUtils.length(query12) != ConstantKey.APNO_LENGTH
						&& StringUtils.length(query13) != ConstantKey.APNO_LENGTH
						&& StringUtils.length(query14) != ConstantKey.APNO_LENGTH
						&& StringUtils.length(query15) != ConstantKey.APNO_LENGTH
						&& StringUtils.length(query16) != ConstantKey.APNO_LENGTH
						&& StringUtils.length(query17) != ConstantKey.APNO_LENGTH
						&& StringUtils.length(query18) != ConstantKey.APNO_LENGTH
						&& StringUtils.length(query19) != ConstantKey.APNO_LENGTH
						&& StringUtils.length(query20) != ConstantKey.APNO_LENGTH) {
					// 設定 無查詢資料 訊息
					saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
					return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
				}

				List<OldAgeReviewRpt01Case> OldAgeReviewRpt01CaseList = new ArrayList<OldAgeReviewRpt01Case>();
				List<DisableReviewRpt01Case> DisableReviewRpt01CaseList = new ArrayList<DisableReviewRpt01Case>();
				List<SurvivorReviewRpt01Case> SurvivorReviewRpt01CaseList = new ArrayList<SurvivorReviewRpt01Case>();

				String payKind = null;
				for (String s : haveValueList) {

					// 給付種類
					payKind = s.substring(0, 1);

					// 抓報表資料
					// [

					if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payKind)) {// 老年
						List<OldAgeReviewRpt01Case> list = rptService.getOldAgeReviewRpt01DataBy(s, s);
						if(!list.isEmpty()) {
							OldAgeReviewRpt01CaseList.add(rptService.getOldAgeReviewRpt01DataBy(s, s).get(0));
						}
					} else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payKind)) {// 失能
						List<DisableReviewRpt01Case> list = rptService.getDisableReviewRpt01DataBy(s, s);
						if(!list.isEmpty()) {
							DisableReviewRpt01CaseList.add(rptService.getDisableReviewRpt01DataBy(s, s).get(0));
						}
						// 放入得請領起始年月
						if (DisableReviewRpt01CaseList != null) {
							for (int j = 0; j < DisableReviewRpt01CaseList.size(); j++) {
								// 放入得請領起始年月formatChineseYearMonthString
								DisableReviewRpt01Case caseData = (DisableReviewRpt01Case) DisableReviewRpt01CaseList
										.get(j);
								// 處理日期前
								String ableapsYmBefor = rptService.getAbleapsYmForDisabledCheckMarkLevelAdjust(
										caseData.getApNo(), caseData.getBaappbaseId());

								if (StringUtils.isNotBlank(ableapsYmBefor)) {
									// 處理日期後 放入list內各筆資料之AbleapsYm 得請領起始年月
									String ableapsYmAfter = DateUtility.formatChineseYearMonthString(
											DateUtility.changeWestYearMonthType(ableapsYmBefor), false);
									caseData.setAbleapsYm(ableapsYmAfter);
								}
							}
						}
					} else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payKind)) {// 遺屬
						List<SurvivorReviewRpt01Case> list = rptService.getSurvivorReviewRpt01DataBy(s, s);
						if(!list.isEmpty()) {
							SurvivorReviewRpt01CaseList.add(rptService.getSurvivorReviewRpt01DataBy(s, s).get(0));
						}
					}
				}
				// ]

				if ((OldAgeReviewRpt01CaseList == null || OldAgeReviewRpt01CaseList.size() == 0) 
						&& (DisableReviewRpt01CaseList == null || DisableReviewRpt01CaseList.size() == 0) 
						&& (SurvivorReviewRpt01CaseList == null || SurvivorReviewRpt01CaseList.size() == 0)) {
					// 設定 無查詢資料 訊息
					saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
					return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
				} else {
					ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

					if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payKind)) {// 老年
						OldAgeReviewRpt01Report report = new OldAgeReviewRpt01Report();
						baoOutput = report.doReport(userData, OldAgeReviewRpt01CaseList);
					} else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payKind)) {// 失能

						DisableReviewRpt01Case caseObj = new DisableReviewRpt01Case();
						caseObj = (DisableReviewRpt01Case) DisableReviewRpt01CaseList.get(0);
						if ("36".equals(caseObj.getPayKind())) {
							DisableReviewRpt01Kind36Report report = new DisableReviewRpt01Kind36Report();
							baoOutput = report.doReport(userData, DisableReviewRpt01CaseList);
						} else {
							DisableReviewRpt01Report report = new DisableReviewRpt01Report();
							baoOutput = report.doReport(userData, DisableReviewRpt01CaseList);
						}
					} else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payKind)) {// 遺屬
						SurvivorReviewRpt01Report report = new SurvivorReviewRpt01Report();
						baoOutput = report.doReport(userData, SurvivorReviewRpt01CaseList);
					}

					String printDate = DateUtility.getNowChineseDate();

					// 設定回傳回 Client 端之檔案大小, 若不設定,
					// 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
					if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payKind)) {// 老年
						response.setHeader("Content-disposition",
								"attachment; filename=\"OldAgeReviewRpt01_" + printDate + ".pdf" + "\"");
					} else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payKind)) {// 失能
						response.setHeader("Content-disposition",
								"attachment; filename=\"DisableReviewRpt01_" + printDate + ".pdf" + "\"");
					} else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payKind)) {// 遺屬
						response.setHeader("Content-disposition",
								"attachment; filename=\"SurvivorReviewRpt01_" + printDate + ".pdf" + "\"");
					}
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

					return null;
				}

			} catch (Exception e) {
				// 設定查詢失敗訊息
				if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payKindMsg)) {
					log.error("產製 勞保老年年金給付受理編審清單 發生錯誤:" + ExceptionUtility.getStackTrace(e));
				} else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payKindMsg)) {
					log.error("產製 勞保失能年金給付受理編審清單 發生錯誤:" + ExceptionUtility.getStackTrace(e));
				} else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payKindMsg)) {
					log.error("產製 勞保遺屬年金給付受理編審清單 發生錯誤:" + ExceptionUtility.getStackTrace(e));
				} else {
					log.error("產製 勞保年金給付受理編審清單 發生錯誤:" + ExceptionUtility.getStackTrace(e));
				}
				saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
				return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
			}
		}
		return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
	}

	public void setRptService(RptService rptService) {
		this.rptService = rptService;
	}
	// public void setUpdateService(UpdateService updateService) {
	// this.updateService = updateService;
	// }
}
