package tw.gov.bli.ba.query.actions;

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
import org.owasp.encoder.Encode;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Caub;
import tw.gov.bli.ba.domain.Cipb;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.query.cases.AnnuityStatistics3Case;
import tw.gov.bli.ba.query.cases.AnnuityStatisticsCase;
import tw.gov.bli.ba.query.forms.AnnuityStatisticsForm;
import tw.gov.bli.ba.query.helper.CaseSessionHelper;
import tw.gov.bli.ba.query.helper.FormSessionHelper;
import tw.gov.bli.ba.query.report.AnnuityStatisticsExcelReport03;
import tw.gov.bli.ba.query.report.AnnuityStatisticsExcelReport;
import tw.gov.bli.ba.query.report.AnnuityStatisticsExcelReport02;
import tw.gov.bli.ba.query.report.AnnuityStatisticsExcelReport04;
import tw.gov.bli.ba.services.QueryService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 查詢作業 - 年金統計查詢- 查詢頁面 (baiq0d070q.jsp)
 * 
 * @author Eddie
 */
public class AnnuityStatisticsAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(AnnuityStatisticsAction.class);

	private QueryService queryService;

	/**
	 * 查詢作業 - 年金統計查詢- 查詢頁面 (baiq0d070q.jsp) <br>
	 * 按下「確認」的動作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("執行 查詢作業 - 年金統計查詢- 查詢頁面 AnnuityStatisticsAction.doReport() 開始 ... ");

		HttpSession session = request.getSession();
		UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

		AnnuityStatisticsForm queryForm = (AnnuityStatisticsForm) form;

		try {
			String payKind = queryForm.getPayCode();
			String paymentYMStart = DateUtility.changeChineseYearMonthType(queryForm.getPaymentYMStart());
			String paymentYMEnd = StringUtils.equals(queryForm.getPaymentYMEnd(), "") || queryForm.getPaymentYMEnd() == null ? DateUtility.changeChineseYearMonthType(queryForm.getPaymentYMStart()) : DateUtility.changeChineseYearMonthType(queryForm.getPaymentYMEnd());
			queryForm.setPaymentYMEnd(DateUtility.changeWestYearMonthType(paymentYMEnd));
			// 產生EXCEL報表
			ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

			AnnuityStatisticsExcelReport report = null;
			AnnuityStatisticsExcelReport02 report2 = null;
			AnnuityStatisticsExcelReport03 report3 = null;
			AnnuityStatisticsExcelReport04 report4 = null;
			
			if (StringUtils.equals(queryForm.getQrytype(), "A")) {
				if (queryForm.getAnalysisSelect().equals("X")) {
					// 抓件數及金額資料 (年月、件數、金額)
					List<AnnuityStatisticsCase> qryRptType1X = queryService.qryRptType1X(payKind, paymentYMStart,
							paymentYMEnd);

					// 抓件數及金額資料 (最高金額)
					List<AnnuityStatisticsCase> qryRptType1X1 = queryService.qryRptType1X1(payKind, paymentYMStart,
							paymentYMEnd);

					// 抓件數及金額資料 (最低金額)
					List<AnnuityStatisticsCase> qryRptType1X2 = queryService.qryRptType1X2(payKind, paymentYMStart,
							paymentYMEnd);
					for(AnnuityStatisticsCase c : qryRptType1X) {
						for(AnnuityStatisticsCase c2 : qryRptType1X1) {
							if(StringUtils.equals(c.getIssuYm(), c2.getIssuYm())) {
								c.setMaxPayAmt(c2.getMaxPayAmt());
							}
						}
					}
					
					for(AnnuityStatisticsCase c : qryRptType1X) {
						for(AnnuityStatisticsCase c2 : qryRptType1X2) {
							if(StringUtils.equals(c.getIssuYm(), c2.getIssuYm())) {
								c.setMinPayAmt(c2.getMinPayAmt());
							}
						}
					}

					report = new AnnuityStatisticsExcelReport("AnnuityStatisticsExcelXA.xls");
					baoOutput = report.doReport(qryRptType1X, payKind, queryForm);

				} else if (queryForm.getAnalysisSelect().equals("S")) {
					// 抓件數及金額資料 (性別)
					List<AnnuityStatisticsCase> qryRptType1S = queryService.qryRptType1S(payKind, paymentYMStart,
							paymentYMEnd);
					report = new AnnuityStatisticsExcelReport("AnnuityStatisticsExcelSA.xls");
					baoOutput = report.doReport(qryRptType1S, payKind, queryForm);
				} else if (queryForm.getAnalysisSelect().equals("U")) {
					// 抓件數及金額資料 (單位類別)
					List<Caub> caubList = queryService.qryUbTypeList();
					List<AnnuityStatisticsCase> qryRptType1U = queryService.qryRptType1U(payKind, paymentYMStart,
							paymentYMEnd);
					report = new AnnuityStatisticsExcelReport("AnnuityStatisticsExcelUA.xls");
					baoOutput = report.doReport(qryRptType1U, payKind, queryForm, caubList);
				} else if (queryForm.getAnalysisSelect().equals("N")) {
					// 抓件數及金額資料 (國籍別(年月、件數、金額))
					List<AnnuityStatisticsCase> qryRptType1N = queryService.qryRptType1N(payKind, paymentYMStart, paymentYMEnd);
					// 抓件數及金額資料 (國籍別(職災補償一次金))
					List<AnnuityStatisticsCase> qryRptType1N1 = queryService.qryRptType1N1(payKind, paymentYMStart, paymentYMEnd);
					report = new AnnuityStatisticsExcelReport("AnnuityStatisticsExcelNA.xls");
					baoOutput = report.doReport(qryRptType1N, qryRptType1N1, payKind, queryForm);
				} else if (queryForm.getAnalysisSelect().equals("C")) {
					// 抓件數及金額資料 (外籍別(年月、外籍別、件數、金額))
					List<Cipb> cipbList = queryService.qryCipbFmkList();
					List<AnnuityStatisticsCase> qryRptType1C = queryService.qryRptType1C(payKind, paymentYMStart,
							paymentYMEnd);

					// 抓件數及金額資料 (外籍別(職災補償一次金))
					List<AnnuityStatisticsCase> qryRptType1C1 = queryService.qryRptType1C1(payKind, paymentYMStart,
							paymentYMEnd);
					report = new AnnuityStatisticsExcelReport("AnnuityStatisticsExcelCA.xls");
					baoOutput = report.doReport(qryRptType1C, qryRptType1C1, payKind, queryForm);
				} else if (queryForm.getAnalysisSelect().equals("E")) {
					// 抓件數及金額資料 (傷病分類(年月、傷病分類、件數、金額))
					List<AnnuityStatisticsCase> qryRptType1E = queryService.qryRptType1E(payKind, paymentYMStart,
							paymentYMEnd);

					// 抓件數及金額資料 (傷病分類(職災補償一次金))
					List<AnnuityStatisticsCase> qryRptType1E1 = queryService.qryRptType1E1(payKind, paymentYMStart,
							paymentYMEnd);
					for(AnnuityStatisticsCase c : qryRptType1E) {
						if(!qryRptType1E1.isEmpty() && qryRptType1E1.size() > 0) {
							for(AnnuityStatisticsCase c2 : qryRptType1E1) {
								if(StringUtils.equals(c.getIssuYm(), c2.getIssuYm())) {
									c.setHinPays(c2.getHinPays());
								}
							}
						}else {
							c.setHinPays("0");
						}
					}
					report = new AnnuityStatisticsExcelReport("AnnuityStatisticsExcelEA.xls");
					baoOutput = report.doReport(qryRptType1E, payKind, queryForm);
				}
			} else if (StringUtils.equals(queryForm.getQrytype(), "B")) {
				if (queryForm.getAnalysisSelect().equals("X")) {
					// 年度平均，無
					List<AnnuityStatisticsCase> qryRptType2X = queryService.qryRptType2X(payKind, paymentYMStart,
							paymentYMEnd);
					report2 = new AnnuityStatisticsExcelReport02("AnnuityStatisticsExcelXB.xls");
					baoOutput = report2.doReport(qryRptType2X, payKind, queryForm);
				} else if (queryForm.getAnalysisSelect().equals("S")) {
					// 年度平均，性別
					List<AnnuityStatisticsCase> qryRptType2X = queryService.qryRptType2X(payKind, paymentYMStart,
							paymentYMEnd);
					List<AnnuityStatisticsCase> qryRptType2S = queryService.qryRptType2S(payKind, paymentYMStart,
							paymentYMEnd);

					report2 = new AnnuityStatisticsExcelReport02("AnnuityStatisticsExcelSB.xls");
					baoOutput = report2.doReport(qryRptType2X, qryRptType2S, payKind, queryForm);
				} else if (queryForm.getAnalysisSelect().equals("U")) {
					// 年度平均，單位類別
					List<Caub> caubList = queryService.qryUbTypeList();
					List<AnnuityStatisticsCase> qryRptType2X = queryService.qryRptType2X(payKind, paymentYMStart,
							paymentYMEnd);
					List<AnnuityStatisticsCase> qryRptType2U = queryService.qryRptType2U(payKind, paymentYMStart,
							paymentYMEnd);
					report2 = new AnnuityStatisticsExcelReport02("AnnuityStatisticsExcelUB.xls");
					baoOutput = report2.doReport(qryRptType2X, qryRptType2U, payKind, queryForm, caubList);
				} else if (queryForm.getAnalysisSelect().equals("N")) {
					// 年度平均，國籍別
					List<AnnuityStatisticsCase> qryRptType2X = queryService.qryRptType2X(payKind, paymentYMStart,
							paymentYMEnd);
					List<AnnuityStatisticsCase> qryRptType2N = queryService.qryRptType2N(payKind, paymentYMStart,
							paymentYMEnd);
					report2 = new AnnuityStatisticsExcelReport02("AnnuityStatisticsExcelNB.xls");
					baoOutput = report2.doReport(qryRptType2X, qryRptType2N, payKind, queryForm);
				} else if (queryForm.getAnalysisSelect().equals("C")) {
					// 年度平均，外籍別
					List<Cipb> cipbList = queryService.qryCipbFmkList();
					List<AnnuityStatisticsCase> qryRptType2X = queryService.qryRptType2X(payKind, paymentYMStart,
							paymentYMEnd);
					List<AnnuityStatisticsCase> qryRptType2C = queryService.qryRptType2C(payKind, paymentYMStart,
							paymentYMEnd);
					report2 = new AnnuityStatisticsExcelReport02("AnnuityStatisticsExcelCB.xls");
					baoOutput = report2.doReport(qryRptType2X, qryRptType2C, payKind, queryForm);
				} else if (queryForm.getAnalysisSelect().equals("E")) {
					// 年度平均，傷病分類
					List<AnnuityStatisticsCase> qryRptType2X = queryService.qryRptType2X(payKind, paymentYMStart,
							paymentYMEnd);
					List<AnnuityStatisticsCase> qryRptType2E = queryService.qryRptType2E(payKind, paymentYMStart,
							paymentYMEnd);

					report2 = new AnnuityStatisticsExcelReport02("AnnuityStatisticsExcelEB.xls");
					baoOutput = report2.doReport(qryRptType2X, qryRptType2E, payKind, queryForm);
				}
			} else if (StringUtils.equals(queryForm.getQrytype(), "C")||StringUtils.equals(queryForm.getQrytype(), "D")
					|| StringUtils.equals(queryForm.getQrytype(), "E")||StringUtils.equals(queryForm.getQrytype(), "F")) {
				String paymentYmStart = DateUtility.changeChineseYearMonthType(queryForm.getPaymentYMStart());
				String paymentYmEnd = "";
				if(StringUtils.isNotBlank(queryForm.getPaymentYMEnd())) {
					paymentYmEnd = DateUtility.changeChineseYearMonthType(queryForm.getPaymentYMEnd());
				} else {
					paymentYmEnd = paymentYmStart;
				}
				AnnuityStatistics3Case reportData3 = queryService.queryReportData3Data(queryForm.getQrytype(), queryForm.getPayCode(),paymentYmStart,paymentYmEnd,queryForm.getSpacing(), queryForm.getAnalysisSelect());
				
				if(StringUtils.equals(queryForm.getAnalysisSelect(), "E")) {
					report3 = new AnnuityStatisticsExcelReport03("AnnuityStatisticsExcel3E.xls");
				} else if(StringUtils.equals(queryForm.getAnalysisSelect(), "S")) {
		    		report3 = new AnnuityStatisticsExcelReport03("AnnuityStatisticsExcel3S.xls");
		    	} else if(StringUtils.equals(queryForm.getAnalysisSelect(), "U")) {
		    		report3 = new AnnuityStatisticsExcelReport03("AnnuityStatisticsExcel3U.xls");		    		
				} else if(StringUtils.equals(queryForm.getAnalysisSelect(), "N")) {
		    		report3 = new AnnuityStatisticsExcelReport03("AnnuityStatisticsExcel3N.xls");
				} else if(StringUtils.equals(queryForm.getAnalysisSelect(), "C")) {
		    		report3 = new AnnuityStatisticsExcelReport03("AnnuityStatisticsExcel3C.xls");
				} else if(StringUtils.equals(queryForm.getAnalysisSelect(), "X")){
		    		report3 = new AnnuityStatisticsExcelReport03("AnnuityStatisticsExcel3.xls");
				}
				baoOutput = report3.doReport(reportData3);
			} else if (StringUtils.equals(queryForm.getQrytype(), "G")) {
				if (queryForm.getAnalysisSelect().equals("X")) {
					//年齡級距，無
					List<AnnuityStatisticsCase> qryRptType4XA = queryService.qryRptType4XA(payKind, paymentYMStart,
							paymentYMEnd);
					report4 = new AnnuityStatisticsExcelReport04("AnnuityStatisticsExcelXGH.xls");
					baoOutput = report4.doReport(qryRptType4XA, payKind, queryForm);
				} else if (queryForm.getAnalysisSelect().equals("S")) {
					//年齡級距，性別
					List<AnnuityStatisticsCase> qryRptType4XA = queryService.qryRptType4XA(payKind, paymentYMStart,
							paymentYMEnd);
					List<AnnuityStatisticsCase> qryRptType4SA = queryService.qryRptType4SA(payKind, paymentYMStart,
							paymentYMEnd);
					report4 = new AnnuityStatisticsExcelReport04("AnnuityStatisticsExcelSGH.xls");
					baoOutput = report4.doReport(qryRptType4XA, qryRptType4SA, payKind, queryForm);
				} else if (queryForm.getAnalysisSelect().equals("U")) {
					//年齡級距，單位類別
					List<Caub> caubList = queryService.qryUbTypeList();
					List<AnnuityStatisticsCase> qryRptType4XA = queryService.qryRptType4XA(payKind, paymentYMStart,
							paymentYMEnd);
					List<AnnuityStatisticsCase> qryRptType4UA = queryService.qryRptType4UA(payKind, paymentYMStart,
							paymentYMEnd);
					report4 = new AnnuityStatisticsExcelReport04("AnnuityStatisticsExcelUGH.xls");
					baoOutput = report4.doReport(qryRptType4XA, qryRptType4UA, caubList, payKind, queryForm);
				} else if (queryForm.getAnalysisSelect().equals("N")) {
					//年齡級距，國籍別
					List<AnnuityStatisticsCase> qryRptType4XA = queryService.qryRptType4XA(payKind, paymentYMStart,
							paymentYMEnd);
					List<AnnuityStatisticsCase> qryRptType4NA = queryService.qryRptType4NA(payKind, paymentYMStart,
							paymentYMEnd);
					report4 = new AnnuityStatisticsExcelReport04("AnnuityStatisticsExcelNGH.xls");
					baoOutput = report4.doReport(qryRptType4XA, qryRptType4NA, payKind, queryForm);
				} else if (queryForm.getAnalysisSelect().equals("C")) {
					//年齡級距，外籍別
					List<AnnuityStatisticsCase> qryRptType4XA = queryService.qryRptType4XA(payKind, paymentYMStart,
							paymentYMEnd);
					List<AnnuityStatisticsCase> qryRptType4CA = queryService.qryRptType4CA(payKind, paymentYMStart,
							paymentYMEnd);
					report4 = new AnnuityStatisticsExcelReport04("AnnuityStatisticsExcelCGH.xls");
					baoOutput = report4.doReport(qryRptType4XA, qryRptType4CA, payKind, queryForm);
				} else if (queryForm.getAnalysisSelect().equals("E")) {
					//年齡級距，傷病分類
					List<AnnuityStatisticsCase> qryRptType4XA = queryService.qryRptType4XA(payKind, paymentYMStart,
							paymentYMEnd);
					List<AnnuityStatisticsCase> qryRptType4EA = queryService.qryRptType4EA(payKind, paymentYMStart,
							paymentYMEnd);
					report4 = new AnnuityStatisticsExcelReport04("AnnuityStatisticsExcelEGH.xls");
					baoOutput = report4.doReport(qryRptType4XA, qryRptType4EA, payKind, queryForm);
				}
			}else if (StringUtils.equals(queryForm.getQrytype(), "H")) {
				if (queryForm.getAnalysisSelect().equals("X")) {
					//投保薪資級距，無
					List<AnnuityStatisticsCase> qryRptType4XW = queryService.qryRptType4XW(payKind, paymentYMStart,
							paymentYMEnd);
					report4 = new AnnuityStatisticsExcelReport04("AnnuityStatisticsExcelXGH.xls");
					baoOutput = report4.doReport(qryRptType4XW, payKind, queryForm);
				} else if (queryForm.getAnalysisSelect().equals("S")) {
					//投保薪資級距，性別
					List<AnnuityStatisticsCase> qryRptType4XW = queryService.qryRptType4XW(payKind, paymentYMStart,
							paymentYMEnd);
					List<AnnuityStatisticsCase> qryRptType4SW = queryService.qryRptType4SW(payKind, paymentYMStart,
							paymentYMEnd);
					report4 = new AnnuityStatisticsExcelReport04("AnnuityStatisticsExcelSGH.xls");
					baoOutput = report4.doReport(qryRptType4XW, qryRptType4SW, payKind, queryForm);
				} else if (queryForm.getAnalysisSelect().equals("U")) {
					//投保薪資級距，單位類別
					List<Caub> caubList = queryService.qryUbTypeList();
					List<AnnuityStatisticsCase> qryRptType4XW = queryService.qryRptType4XW(payKind, paymentYMStart,
							paymentYMEnd);
					List<AnnuityStatisticsCase> qryRptType4UW = queryService.qryRptType4UW(payKind, paymentYMStart,
							paymentYMEnd);
					report4 = new AnnuityStatisticsExcelReport04("AnnuityStatisticsExcelUGH.xls");
					baoOutput = report4.doReport(qryRptType4XW, qryRptType4UW, caubList, payKind, queryForm);
				} else if (queryForm.getAnalysisSelect().equals("N")) {
					//投保薪資級距，國籍別
					List<AnnuityStatisticsCase> qryRptType4XW = queryService.qryRptType4XW(payKind, paymentYMStart,
							paymentYMEnd);
					List<AnnuityStatisticsCase> qryRptType4NW = queryService.qryRptType4NW(payKind, paymentYMStart,
							paymentYMEnd);
					report4 = new AnnuityStatisticsExcelReport04("AnnuityStatisticsExcelNGH.xls");
					baoOutput = report4.doReport(qryRptType4XW, qryRptType4NW, payKind, queryForm);
				} else if (queryForm.getAnalysisSelect().equals("C")) {
					//投保薪資級距，外籍別
					List<AnnuityStatisticsCase> qryRptType4XW = queryService.qryRptType4XW(payKind, paymentYMStart,
							paymentYMEnd);
					List<AnnuityStatisticsCase> qryRptType4CW = queryService.qryRptType4CW(payKind, paymentYMStart,
							paymentYMEnd);
					report4 = new AnnuityStatisticsExcelReport04("AnnuityStatisticsExcelCGH.xls");
					baoOutput = report4.doReport(qryRptType4XW, qryRptType4CW, payKind, queryForm);
				} else if (queryForm.getAnalysisSelect().equals("E")) {
					//投保薪資級距，傷病分類
					List<AnnuityStatisticsCase> qryRptType4XW = queryService.qryRptType4XW(payKind, paymentYMStart,
							paymentYMEnd);
					List<AnnuityStatisticsCase> qryRptType4EW = queryService.qryRptType4EW(payKind, paymentYMStart,
							paymentYMEnd);
					report4 = new AnnuityStatisticsExcelReport04("AnnuityStatisticsExcelEGH.xls");
					baoOutput = report4.doReport(qryRptType4XW, qryRptType4EW, payKind, queryForm);
				}
			}

			// 設定回傳回 Client 端之檔案大小, 若不設定,
			// 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
			response.setHeader("Content-disposition",
					"attachment; filename=\"" + Encode.forJava(queryForm.getPayCode())
							+ new String("_AnnuityStatisticsRpt_".getBytes("Big5"), "ISO8859_1")
							+ Encode.forJava(DateUtility.getNowChineseDate()) + ".xls" + "\"");
			response.setContentType("application/vnd.ms-excel"); // 設定MIME
			response.setHeader("Pargma", "no-cache");
			response.setHeader("Cache-Control", "max-age=1");
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
				log.debug("查詢作業 - 年金統計查詢 - 產製 AnnuityStatisticsAction.doReport() 完成 ... ");
			} catch (Exception e) {
			} finally {
				FormSessionHelper.removeAnnuityStatisticsForm(request);
				CaseSessionHelper.removeAnnuityStatisticsCase(request);
				if (baoOutput != null)
					baoOutput.close();
				if (sout != null)
					sout.close();
			}
			return null;
		} catch (Exception e) {
			// 設定查詢失敗訊息
			log.error("產製 年金統計查詢 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
			return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
		}
	}

	public void setQueryService(QueryService queryService) {
		this.queryService = queryService;
	}

}
