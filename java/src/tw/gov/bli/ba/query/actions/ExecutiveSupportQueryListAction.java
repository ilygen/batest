package tw.gov.bli.ba.query.actions;

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
import org.apache.struts.actions.DispatchAction;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Badapr;
import tw.gov.bli.ba.domain.Maadmrec;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.query.cases.ExecutiveSupportDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryDetailDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryIssuPayDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryLetterTypeMkCase;
import tw.gov.bli.ba.query.forms.ExecutiveSupportQueryForm;
import tw.gov.bli.ba.query.forms.ExecutiveSupportQueryListForm;
import tw.gov.bli.ba.query.forms.PaymentQueryForm;
import tw.gov.bli.ba.query.helper.CaseSessionHelper;
import tw.gov.bli.ba.query.helper.FormSessionHelper;
import tw.gov.bli.ba.services.QueryService;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 查詢作業 - 行政支援查詢 - 清單頁面 (baiq0d051q.jsp)
 * 
 * @author jerry
 */
public class ExecutiveSupportQueryListAction extends DispatchAction {
    private static Log log = LogFactory.getLog(ExecutiveSupportQueryListAction.class);
    private static final String FORWARD_DETAIL = "detail"; // 行政支援查詢詳細頁面
    private static final String FORWARD_PAYMENT_QUERY_DETAIL_FOR_OLDAGE = "paymentQueryDetailForOldAge";
    private static final String FORWARD_PAYMENT_QUERY_DETAIL_SIMPLY_FOR_OLDAGE = "paymentQueryDetailSimplyForOldAge";
    private static final String FORWARD_PAYMENT_QUERY_DETAIL_FOR_DISABLED = "paymentQueryDetailForDisabled";
    private static final String FORWARD_PAYMENT_QUERY_DETAIL_SIMPLY_FOR_DISABLED = "paymentQueryDetailSimplyForDisabled";
    private static final String FORWARD_PAYMENT_QUERY_DETAIL_FOR_SURVIVOR = "paymentQueryDetailForSurvivor";
    private static final String FORWARD_PAYMENT_QUERY_DETAIL_SIMPLY_FOR_SURVIVOR = "paymentQueryDetailSimplyForSurvivor";
    private QueryService queryService;

    /**
     * 查詢作業 - 行政支援查詢 - 清單頁面 (baiq0d051q.jsp) <br>
     * 按下「明細查詢」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 行政支援查詢 - 清單頁面 ExecutiveSupportQueryListAction.doDetail() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        ExecutiveSupportQueryListForm queryForm = (ExecutiveSupportQueryListForm) form;

        try {
            String index = queryForm.getIndex();

            List<ExecutiveSupportDataCase> caseList = CaseSessionHelper.getExecutiveSupportQueryCaseList(request);
            ExecutiveSupportDataCase caseData = caseList.get(Integer.parseInt(index) - 1);

            CaseSessionHelper.setExecutiveSupportQueryDataCase(caseData, request);

            log.debug("執行 查詢作業 - 行政支援查詢 - 清單頁面 ExecutiveSupportQueryListAction.doDetail() 完成 ... ");

            return mapping.findForward(FORWARD_DETAIL);

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ExecutiveSupportQueryListAction.doDetail() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 查詢作業 - 行政支援查詢 - 清單頁面 (baiq0d051q.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 行政支援查詢 - 清單頁面 ExecutiveSupportQueryListAction.doBack() 開始 ... ");

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        FormSessionHelper.removeExecutiveSupportQueryForm(request);
        FormSessionHelper.removeExecutiveSupportQueryCondForm(request);
        CaseSessionHelper.removeExecutiveSupportQueryCase(request);
        CaseSessionHelper.removeExecutiveSupportQueryCaseList(request);
        CaseSessionHelper.removeExecutiveSupportQueryDataCase(request);
        log.debug("執行  查詢作業 - 行政支援查詢 - 清單頁面 ExecutiveSupportQueryListAction.doBack() 完成 ... ");
        return mapping.findForward(ConstantKey.FORWARD_BACK);
    }

    /**
     * 查詢作業 - 行政支援查詢 - 返回給付查詢 for 老年年金給付查詢(baiq0d051q.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackToPaymentQueryForOldAge(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 行政支援查詢 - 返回給付查詢 ExecutiveSupportQueryListAction.doBackToPaymentQueryForOldAge() 開始 ... ");
        HttpSession session = request.getSession();
        ExecutiveSupportQueryForm iform = FormSessionHelper.getExecutiveSupportQueryCondForm(request);
        String forward = ConstantKey.FORWARD_QUERY_FAIL;

        if (("BAIQ0D012Q").equals(iform.getQryFromWhere())) {
            forward = FORWARD_PAYMENT_QUERY_DETAIL_FOR_OLDAGE;
        }
        else if (("BAIQ0D062Q").equals(iform.getQryFromWhere())) {
            forward = FORWARD_PAYMENT_QUERY_DETAIL_SIMPLY_FOR_OLDAGE;
        }

        try {
            // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
            FormSessionHelper.removeExecutiveSupportQueryForm(request);
            FormSessionHelper.removeExecutiveSupportQueryCondForm(request);
            CaseSessionHelper.removeExecutiveSupportQueryCase(request);
            CaseSessionHelper.removeExecutiveSupportQueryCaseList(request);
            CaseSessionHelper.removeExecutiveSupportQueryDataCase(request);

            // 起始查詢條件
            PaymentQueryDetailDataCase detail = tw.gov.bli.ba.query.helper.CaseSessionHelper.getPaymentQueryDetailDataCase(request);
            PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
            String apNo = qryForm.getApNoStr();
            String qryCond = qryForm.getQryCond();
            String startYm = qryForm.getStartYmStr();
            String endYm = qryForm.getEndYmStr();
            String evtName = qryForm.getName();
            String evtIdnNo = qryForm.getIdn();
            String evtBrDate = qryForm.getBrDateStr();

            // 取得 承辦人分機號碼
            detail.setEmpExt(queryService.getEmpExtData(detail.getApNo()));

            // 取得 XX函註記
            // detail = queryService.getLetterTypeMk(detail);
            List<PaymentQueryLetterTypeMkCase> detail1 = queryService.getLetterTypeMk1List(detail.getApNo());
            List<PaymentQueryLetterTypeMkCase> detail2 = queryService.getLetterTypeMk2List(detail.getApNo());
            List<PaymentQueryLetterTypeMkCase> detail3 = queryService.getLetterTypeMk3List(detail.getApNo());
            List<PaymentQueryLetterTypeMkCase> detail4 = queryService.getLetterTypeMk4List(detail.getApNo());
            List<PaymentQueryLetterTypeMkCase> detail5 = queryService.getLetterTypeMk5List(detail.getApNo());
            PaymentQueryLetterTypeMkCase detail6 = queryService.getLetterTypeMk6List(detail.getApNo());

            // 取得 核定年月資料
            List<Object> mixDataList = queryService.getPaymentQueryIssuPayDataList(apNo, startYm, endYm, qryCond);
            List<Badapr> origIssuPayDataList = (List<Badapr>)mixDataList.get(0);
            List<PaymentQueryIssuPayDataCase> issuPayDataList = (List<PaymentQueryIssuPayDataCase>)mixDataList.get(1);
            // 取得 事故者編審註記資料
            List<PaymentQueryDetailDataCase> chkFileDataList = queryService.getPaymentQueryEvtChkList(detail.getApNo(), "0000");

            // 取得 核定年月下拉選單
            if (StringUtils.equals("ISSUYM", qryCond)) {
                request.getSession().setAttribute(ConstantKey.PAYMENT_QUERY_ISSUYM_OPTION_LIST, queryService.getIssuYmOptionList(origIssuPayDataList));
            }
            // 取得 給付年月下拉選單
            if (StringUtils.equals("PAYYM", qryCond)) {
                request.getSession().setAttribute(ConstantKey.PAYMENT_QUERY_PAYYM_OPTION_LIST, queryService.getPayYmOptionList(origIssuPayDataList));
            }
            // 取得 受款人下拉選單
            request.getSession().setAttribute(ConstantKey.PAYMENT_QUERY_SEQNO_OPTION_LIST, queryService.getSeqNoOptionList(origIssuPayDataList));

            // 將 查詢條件/查詢結果清單 存到 Request Scope
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryDetailDataCase(detail, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryIssuPayDataCaseList(issuPayDataList, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryOrigIssuPayDataCaseList(origIssuPayDataList, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryEvtChkFileDataCase(chkFileDataList, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryLetterTypeMk1List(detail1, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryLetterTypeMk2List(detail2, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryLetterTypeMk3List(detail3, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryLetterTypeMk4List(detail4, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryLetterTypeMk5List(detail5, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryLetterTypeMk6List(detail6, request);

            log.debug("執行  查詢作業 - 行政支援查詢 - 返回給付查詢 ExecutiveSupportQueryListAction.doBackToPaymentQueryForOldAge() 完成 ... ");
            return mapping.findForward(forward);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ExecutiveSupportCloseListAction.doBackToPaymentQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(forward);
        }
    }

    /**
     * 查詢作業 - 行政支援查詢 - 返回給付查詢 for 失能年金給付查詢(baiq0d051q.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackToPaymentQueryForDisabled(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 行政支援查詢 - 返回給付查詢 ExecutiveSupportQueryListAction.doBackToPaymentQueryForDisabled() 開始 ... ");
        HttpSession session = request.getSession();
        ExecutiveSupportQueryForm iform = FormSessionHelper.getExecutiveSupportQueryCondForm(request);
        String forward = ConstantKey.FORWARD_QUERY_FAIL;

        if (("BAIQ0D112Q").equals(iform.getQryFromWhere())) {
            forward = FORWARD_PAYMENT_QUERY_DETAIL_FOR_DISABLED;
        }
        else if (("BAIQ0D162Q").equals(iform.getQryFromWhere())) {
            forward = FORWARD_PAYMENT_QUERY_DETAIL_SIMPLY_FOR_DISABLED;
        }

        try {
            // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
            FormSessionHelper.removeExecutiveSupportQueryForm(request);
            FormSessionHelper.removeExecutiveSupportQueryCondForm(request);
            CaseSessionHelper.removeExecutiveSupportQueryCase(request);
            CaseSessionHelper.removeExecutiveSupportQueryCaseList(request);
            CaseSessionHelper.removeExecutiveSupportQueryDataCase(request);

            // 起始查詢條件
            PaymentQueryDetailDataCase detail = tw.gov.bli.ba.query.helper.CaseSessionHelper.getPaymentQueryDetailDataCase(request);
            PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
            String apNo = qryForm.getApNoStr();
            String qryCond = qryForm.getQryCond();
            String startYm = qryForm.getStartYmStr();
            String endYm = qryForm.getEndYmStr();
            String evtName = qryForm.getName();
            String evtIdnNo = qryForm.getIdn();
            String evtBrDate = qryForm.getBrDateStr();

            // 取得 承辦人分機號碼
            detail.setEmpExt(queryService.getEmpExtData(detail.getApNo()));

            // 取得 XX函註記
            // detail = queryService.getLetterTypeMk(detail);
            List<PaymentQueryLetterTypeMkCase> detail1 = queryService.getLetterTypeMk1List(detail.getApNo());
            List<PaymentQueryLetterTypeMkCase> detail2 = queryService.getLetterTypeMk2List(detail.getApNo());
            List<PaymentQueryLetterTypeMkCase> detail3 = queryService.getLetterTypeMk3List(detail.getApNo());
            List<PaymentQueryLetterTypeMkCase> detail4 = queryService.getLetterTypeMk4List(detail.getApNo());
            List<PaymentQueryLetterTypeMkCase> detail5 = queryService.getLetterTypeMk5List(detail.getApNo());
            PaymentQueryLetterTypeMkCase detail6 = queryService.getLetterTypeMk6List(detail.getApNo());

            // 取得 核定年月資料
            List<Object> mixDataList = queryService.getPaymentQueryIssuPayDataList(apNo, startYm, endYm, qryCond);
            List<Badapr> origIssuPayDataList = (List<Badapr>)mixDataList.get(0);
            List<PaymentQueryIssuPayDataCase> issuPayDataList = (List<PaymentQueryIssuPayDataCase>)mixDataList.get(1);
            // 取得 事故者編審註記資料
            List<PaymentQueryDetailDataCase> chkFileDataList = queryService.getPaymentQueryEvtChkList(detail.getApNo(), "0000");
            // 取得 眷屬編審註記資料
            List<PaymentQueryDetailDataCase> benChkList = queryService.getPaymentQueryOtherChkList(detail.getApNo(), "0000", "B");
            // 取得 符合註記資料
            List<PaymentQueryDetailDataCase> matchChkList = queryService.getPaymentQueryOtherChkList(detail.getApNo(), "0000", "C");

            // 取得 核定年月下拉選單
            if (StringUtils.equals("ISSUYM", qryCond)) {
                request.getSession().setAttribute(ConstantKey.PAYMENT_QUERY_ISSUYM_OPTION_LIST, queryService.getIssuYmOptionList(origIssuPayDataList));
            }
            // 取得 給付年月下拉選單
            if (StringUtils.equals("PAYYM", qryCond)) {
                request.getSession().setAttribute(ConstantKey.PAYMENT_QUERY_PAYYM_OPTION_LIST, queryService.getPayYmOptionList(origIssuPayDataList));
            }
            // 取得 受款人下拉選單
            request.getSession().setAttribute(ConstantKey.PAYMENT_QUERY_SEQNO_OPTION_LIST, queryService.getSeqNoOptionList(origIssuPayDataList));

            // 將 查詢條件/查詢結果清單 存到 Request Scope
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryDetailDataCase(detail, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryIssuPayDataCaseList(issuPayDataList, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryOrigIssuPayDataCaseList(origIssuPayDataList, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryEvtChkFileDataCase(chkFileDataList, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryBenChkFileDataCase(benChkList, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryMatchChkFileDataCase(matchChkList, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryLetterTypeMk1List(detail1, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryLetterTypeMk2List(detail2, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryLetterTypeMk3List(detail3, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryLetterTypeMk4List(detail4, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryLetterTypeMk5List(detail5, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryLetterTypeMk6List(detail6, request);

            log.debug("執行  查詢作業 - 行政支援查詢 - 返回給付查詢 ExecutiveSupportQueryListAction.doBackToPaymentQueryForDisabled() 完成 ... ");
            return mapping.findForward(forward);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ExecutiveSupportCloseListAction.doBackToPaymentQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(forward);
        }
    }

    /**
     * 查詢作業 - 行政支援查詢 - 返回給付查詢 for 遺屬年金給付查詢(baiq0d051q.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackToPaymentQueryForSurvivor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 行政支援查詢 - 返回給付查詢 ExecutiveSupportQueryListAction.doBackToPaymentQueryForSurvivor() 開始 ... ");
        HttpSession session = request.getSession();
        ExecutiveSupportQueryForm iform = FormSessionHelper.getExecutiveSupportQueryCondForm(request);
        String forward = ConstantKey.FORWARD_QUERY_FAIL;

        if (("BAIQ0D212Q").equals(iform.getQryFromWhere())) {
            forward = FORWARD_PAYMENT_QUERY_DETAIL_FOR_SURVIVOR;
        }
        else if (("BAIQ0D262Q").equals(iform.getQryFromWhere())) {
            forward = FORWARD_PAYMENT_QUERY_DETAIL_SIMPLY_FOR_SURVIVOR;
        }

        try {
            // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
            FormSessionHelper.removeExecutiveSupportQueryForm(request);
            FormSessionHelper.removeExecutiveSupportQueryCondForm(request);
            CaseSessionHelper.removeExecutiveSupportQueryCase(request);
            CaseSessionHelper.removeExecutiveSupportQueryCaseList(request);
            CaseSessionHelper.removeExecutiveSupportQueryDataCase(request);

            // 起始查詢條件
            PaymentQueryDetailDataCase detail = tw.gov.bli.ba.query.helper.CaseSessionHelper.getPaymentQueryDetailDataCase(request);
            PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
            String apNo = qryForm.getApNoStr();
            String qryCond = qryForm.getQryCond();
            String startYm = qryForm.getStartYmStr();
            String endYm = qryForm.getEndYmStr();
            String evtName = qryForm.getName();
            String evtIdnNo = qryForm.getIdn();
            String evtBrDate = qryForm.getBrDateStr();

            // 取得 承辦人分機號碼
            detail.setEmpExt(queryService.getEmpExtData(detail.getApNo()));
            
            List<PaymentQueryLetterTypeMkCase> detail1 = null;
            List<PaymentQueryLetterTypeMkCase> detail2 = null;
            List<PaymentQueryLetterTypeMkCase> detail3 = null;
            List<PaymentQueryLetterTypeMkCase> detail4 = null;
            List<PaymentQueryLetterTypeMkCase> detail5 = null;
            PaymentQueryLetterTypeMkCase detail6 = null;
            
            if ("L".equalsIgnoreCase(detail.getApNo().substring(0, 1))) {
                // 取得 XX函註記
                // detail = queryService.getLetterTypeMk(detail);
                detail1 = queryService.getLetterTypeMk1List(detail.getApNo());
                detail2 = queryService.getLetterTypeMk2List(detail.getApNo());
                detail3 = queryService.getLetterTypeMk3List(detail.getApNo());
                detail4 = queryService.getLetterTypeMk4List(detail.getApNo());
                detail5 = queryService.getLetterTypeMk5List(detail.getApNo());
                detail6 = queryService.getLetterTypeMk6List(detail.getApNo());
            } else if ("K".equalsIgnoreCase(detail.getApNo().substring(0, 1))) {
                // 取得 XX函註記
                // detail = queryService.getLetterTypeMk(detail);
                detail1 = queryService.getLetterTypeMk1ListK(detail.getApNo());
                detail2 = queryService.getLetterTypeMk2ListK(detail.getApNo());
                detail3 = queryService.getLetterTypeMk3ListK(detail.getApNo());
                detail4 = queryService.getLetterTypeMk4ListK(detail.getApNo());
                detail5 = queryService.getLetterTypeMk5ListK(detail.getApNo());
                detail6 = queryService.getLetterTypeMk6ListK(detail.getApNo());                
            } else if ("S".equalsIgnoreCase(detail.getApNo().substring(0, 1))) {
                // 取得 XX函註記
                // detail = queryService.getLetterTypeMk(detail);
                detail1 = queryService.getLetterTypeMk1ListS(detail.getApNo());
                detail2 = queryService.getLetterTypeMk2ListS(detail.getApNo());
                detail3 = queryService.getLetterTypeMk3ListS(detail.getApNo());
                detail4 = queryService.getLetterTypeMk4ListS(detail.getApNo());
                detail5 = queryService.getLetterTypeMk5ListS(detail.getApNo());
                detail6 = queryService.getLetterTypeMk6ListS(detail.getApNo());                
            }

            // 取得 核定年月資料
            List<Object> mixDataList = queryService.getPaymentQueryIssuPayDataList(apNo, startYm, endYm, qryCond);
            List<Badapr> origIssuPayDataList = (List<Badapr>)mixDataList.get(0);
            List<PaymentQueryIssuPayDataCase> issuPayDataList = (List<PaymentQueryIssuPayDataCase>)mixDataList.get(1);
            // 取得 事故者編審註記資料
            List<PaymentQueryDetailDataCase> chkFileDataList = queryService.getPaymentQueryEvtChkList(detail.getApNo(), "0000");
            // 取得 眷屬編審註記資料
            List<PaymentQueryDetailDataCase> benChkList = queryService.getPaymentQueryOtherChkList(detail.getApNo(), "0000", "B");
            // 取得 符合註記資料
            List<PaymentQueryDetailDataCase> matchChkList = queryService.getPaymentQueryOtherChkList(detail.getApNo(), "0000", "C");

            // 取得 核定年月下拉選單
            if (StringUtils.equals("ISSUYM", qryCond)) {
                request.getSession().setAttribute(ConstantKey.PAYMENT_QUERY_ISSUYM_OPTION_LIST, queryService.getIssuYmOptionList(origIssuPayDataList));
            }
            // 取得 給付年月下拉選單
            if (StringUtils.equals("PAYYM", qryCond)) {
                request.getSession().setAttribute(ConstantKey.PAYMENT_QUERY_PAYYM_OPTION_LIST, queryService.getPayYmOptionList(origIssuPayDataList));
            }
            // 取得 受款人下拉選單
            request.getSession().setAttribute(ConstantKey.PAYMENT_QUERY_SEQNO_OPTION_LIST, queryService.getSeqNoOptionList(origIssuPayDataList));

            // 將 查詢條件/查詢結果清單 存到 Request Scope
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryDetailDataCase(detail, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryIssuPayDataCaseList(issuPayDataList, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryOrigIssuPayDataCaseList(origIssuPayDataList, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryEvtChkFileDataCase(chkFileDataList, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryBenChkFileDataCase(benChkList, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryMatchChkFileDataCase(matchChkList, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryLetterTypeMk1List(detail1, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryLetterTypeMk2List(detail2, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryLetterTypeMk3List(detail3, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryLetterTypeMk4List(detail4, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryLetterTypeMk5List(detail5, request);
            tw.gov.bli.ba.query.helper.CaseSessionHelper.setPaymentQueryLetterTypeMk6List(detail6, request);

            log.debug("執行  查詢作業 - 行政支援查詢 - 返回給付查詢 ExecutiveSupportQueryListAction.doBackToPaymentQueryForSurvivor() 完成 ... ");
            return mapping.findForward(forward);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ExecutiveSupportCloseListAction.doBackToPaymentQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(forward);
        }
    }

    public void setQueryService(QueryService queryService) {
        this.queryService = queryService;
    }
}
