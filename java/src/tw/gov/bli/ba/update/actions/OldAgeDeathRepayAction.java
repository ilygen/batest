package tw.gov.bli.ba.update.actions;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.rpt.report.OldAgeDeathRepayReport;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.update.cases.BaappbaseDataUpdateCase;
import tw.gov.bli.ba.update.forms.OldAgeDeathRepayForm;
import tw.gov.bli.ba.update.forms.PayeeDataUpdateQueryForm;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;
import org.owasp.encoder.Encode; 

/**
 * 更正作業 - 死亡改匯處理作業 - 老年年金受款人死亡改匯處理(BAMO0D090C)
 * 
 * @author Noctis
 */

public class OldAgeDeathRepayAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(OldAgeDeathRepayAction.class);

    private UpdateService updateService;
    private SelectOptionService selectOptionService;

    // 更正作業 - 死亡改匯處理作業 - 老年年金受款人死亡改匯資料清單頁面
    private static final String FORWARD_MODIFY_OLDAGE_DEATH_REPAY_DATA_LIST = "modifyOldAgeDeathRepayDataList";
    // 更正作業 - 死亡改匯處理作業 - 繼承人維護清單頁面
    private static final String FORWARD_MODIFY_PAYEE_DATA_LIST = "modifyPayeeDataList";

    /**
     * 更正作業 - 死亡改匯處理作業 - 查詢頁面 按下 確認 動作 (bamo0d090c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("更正作業 - 死亡改匯處理作業 - 查詢頁面 OldAgeDeathRepayAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        OldAgeDeathRepayForm queryForm = (OldAgeDeathRepayForm) form;

        try {

            // 檢核受款人資料是否符合可改匯處理的資格 (QryData_1)
            List<BaappbaseDataUpdateCase> checkOldAgeDeathRepayDataCaseList = updateService.getCheckOldAgeDeathRepayDataBy(queryForm.getApNoStr(), queryForm.getSeqNo());
            BaappbaseDataUpdateCase checkOldAgeDeathRepayDataCase = new BaappbaseDataUpdateCase();
            checkOldAgeDeathRepayDataCase.getApNoStr();
            if (checkOldAgeDeathRepayDataCaseList.size() > 0) {
                checkOldAgeDeathRepayDataCase = checkOldAgeDeathRepayDataCaseList.get(0);
                // 轉換西元日期為民國
                checkOldAgeDeathRepayDataCase.setBenDieDate(DateUtility.changeDateType(checkOldAgeDeathRepayDataCase.getBenDieDate()));
                checkOldAgeDeathRepayDataCase.setBenBrDate(DateUtility.changeDateType(checkOldAgeDeathRepayDataCase.getBenBrDate()));
            }

            if (checkOldAgeDeathRepayDataCaseList.size() <= 0) {
                saveMessages(session, CustomMessageHelper.getCheckOldAgeDeathRepayDataFailMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }

            // [繼承人序] 條件：當「受款人序」=0000時，[$繼承人序]=0%,當「受款人序」=0X00時，[$繼承人序]=0X%,當「受款人序」=0XX0時，[$繼承人序]=0XX%
            String heirSeqNo = "";
            if (queryForm.getSeqNo().equals("0000")) {
                heirSeqNo = "";
            }
            else if (!queryForm.getSeqNo().substring(1, 2).equals("0") && queryForm.getSeqNo().substring(2, 3).equals("0")) {
                heirSeqNo = queryForm.getSeqNo().substring(0, 2);
            }
            else if (!queryForm.getSeqNo().substring(1, 2).equals("0") && !queryForm.getSeqNo().substring(2, 3).equals("0")) {
                heirSeqNo = queryForm.getSeqNo().substring(0, 3);
            }
            // 檢核是否符合資料，是否有退匯資料 (QryData_2)
            String apNoStr = queryForm.getApNoStr();
            List<BaappbaseDataUpdateCase> oldAgeDeathRepayDataCaseList = updateService.getOldAgeDeathRepayDataBy(apNoStr, queryForm.getSeqNo(), heirSeqNo);
            if (oldAgeDeathRepayDataCaseList.size() <= 0) {
                saveMessages(session, CustomMessageHelper.getOldAgeDeathRepayDataFailMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }

            // 檢核受款人的繼承人資料是否存在 (QryData_3) 20131202 取消此檢核
            List<BaappbaseDataUpdateCase> heirSeqNoExistDataCaseList = updateService.getHeirSeqNoExistDataBy(queryForm.getApNoStr(), heirSeqNo);
            // if(heirSeqNoExistDataCaseList.size() <= 0 ){
            // saveMessages(session, CustomMessageHelper.getHeirSeqNoExistDataFailMessage());
            // return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            // }
            // 退匯金額分配資料 (QryData_4)
            List<BaappbaseDataUpdateCase> repayIssueAmtDataCaseList = updateService.getRepayIssueAmtDataBy(queryForm.getApNoStr(), heirSeqNo);
            String indexCount = updateService.selectRepayIssueAmtDataCountBy(queryForm.getApNoStr(), heirSeqNo);

            // 帶入預設 退匯金額分配資料 進入checkbox ------begin
            // 先取得已分配資料之 seqno 不得重複
            HashSet<String> seqNoSet = new HashSet<String>();
            //// 退匯金額分配資料 (QryData_4) repayIssueAmtDataCaseList
            for (BaappbaseDataUpdateCase repayIssueAmtData : repayIssueAmtDataCaseList) {
                seqNoSet.add(repayIssueAmtData.getSeqNo());
            }
            final List<String> seqNoList = new ArrayList<String>();
            for (String value : seqNoSet) {
                seqNoList.add(value);
            }
            // seqNo重新排序
            Collections.sort(seqNoList);

            StringBuffer indexString = new StringBuffer();
            // 比對繼承人seqno (QryData_3) heirSeqNoExistDataCaseList
            for (int i = 0; i < heirSeqNoExistDataCaseList.size(); i++) {
                for (String seqNo : seqNoList) {
                    if (heirSeqNoExistDataCaseList.get(i).getSeqNo().equals(seqNo)) {
                        indexString.append(i);
                    }
                }
            }
            // 放入case中帶入session
            checkOldAgeDeathRepayDataCase.setIndexString(indexString.toString());
            // 帶入預設 退匯金額分配資料 進入checkbox ------end
            // 測試用資料
            // BaappbaseDataUpdateCase TEST = new BaappbaseDataUpdateCase();
            // TEST.setPayYm("1010801");
            // TEST.setIssuYm("1010909");
            // TEST.setPayTyp("5");
            // repayIssueAmtDataCaseList.add(TEST);
            // int sizeaa = repayIssueAmtDataCaseList.size();
            // ---
            // 檢核QryData4(退匯金額分配資料)是否有資料 無=0 有=1 帶入隱藏欄位 checkQryData4
            if (repayIssueAmtDataCaseList.size() <= 0) {
                queryForm.setCheckQryData4("0");
            }
            else {
                queryForm.setCheckQryData4("1");
            }
            // ---
            // 放入Request Scope
            // (queryForm)
            FormSessionHelper.setOldAgeDeathRepayForm(queryForm, request);
            // (QryData_1) 受款人資料
            CaseSessionHelper.setCheckOldAgeDeathRepayDataCase(checkOldAgeDeathRepayDataCase, request);
            // (QryData_2) 受款人退匯中，可改匯的核付資料
            CaseSessionHelper.setOldAgeDeathRepayDataCaseList(oldAgeDeathRepayDataCaseList, request);
            // (QryData_3) 受款人的繼承人資料
            CaseSessionHelper.setHeirSeqNoExistDataCaseList(heirSeqNoExistDataCaseList, request);
            // (QryData_4) 退匯金額分配資料
            // 轉換西元日期為民國日期 並set每月分配金額人數(產生報表時需使用)
            for (BaappbaseDataUpdateCase repayIssueAmtData : repayIssueAmtDataCaseList) {
                repayIssueAmtData.setIndexCount(indexCount);
                repayIssueAmtData.setIssuYm(DateUtility.changeWestYearMonthType(repayIssueAmtData.getIssuYm()));
                repayIssueAmtData.setPayYm(DateUtility.changeWestYearMonthType(repayIssueAmtData.getPayYm()));
            }
            CaseSessionHelper.setRepayIssueAmtDataCaseList(repayIssueAmtDataCaseList, request);

            return mapping.findForward(FORWARD_MODIFY_OLDAGE_DEATH_REPAY_DATA_LIST);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("OldAgeDeathRepayAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }

    }

    /**
     * 更正作業 - 死亡改匯處理作業 - 按下 確認 動作 (bamo0d091c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("更正作業 - 死亡改匯處理作業 - 查詢頁面 OldAgeDeathRepayAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        OldAgeDeathRepayForm queryForm = (OldAgeDeathRepayForm) form;

        // ----------------------------------------------------------------------------------------------------
        // (QryData_1) 受款人資料
        BaappbaseDataUpdateCase QryData_1 = CaseSessionHelper.getCheckOldAgeDeathRepayDataCase(request);
        // (QryData_2) 受款人退匯中，可改匯的核付資料
        List<BaappbaseDataUpdateCase> QryData_2 = CaseSessionHelper.getOldAgeDeathRepayDataCaseList(request);
        // (QryData_3) 受款人的繼承人資料
        List<BaappbaseDataUpdateCase> QryData_3 = CaseSessionHelper.getHeirSeqNoExistDataCaseList(request);
        // (QryData_4) 退匯金額分配資料
        List<BaappbaseDataUpdateCase> QryData_4 = CaseSessionHelper.getRepayIssueAmtDataCaseList(request);
        // ----------------------------------------------------------------------------------------------------

        try {
            // 取得勾選資料 index
            int checkIndex[] = queryForm.getIndex();

            // 退匯金額分配資料(QryData_4)
            List<BaappbaseDataUpdateCase> repayIssueAmtDataCaseListOld = CaseSessionHelper.getRepayIssueAmtDataCaseList(request);
            // 民國年月轉換為西元年月
            for (BaappbaseDataUpdateCase repayIssueAmtData : repayIssueAmtDataCaseListOld) {
                repayIssueAmtData.setIssuYm(DateUtility.changeChineseYearMonthType(repayIssueAmtData.getIssuYm()));
                repayIssueAmtData.setPayYm(DateUtility.changeChineseYearMonthType(repayIssueAmtData.getPayYm()));
            }

            // 退匯金額分配資料}(QryData_4)無資料(資料筆數<=0)，且{繼承人資料}無勾選任何資料時，則不允許執行作業
            if (repayIssueAmtDataCaseListOld.size() <= 0 && checkIndex == null) {
                saveMessages(session, CustomMessageHelper.getCheckQryData4CheckIndexFailMessage());
                return mapping.findForward(FORWARD_MODIFY_OLDAGE_DEATH_REPAY_DATA_LIST);
            }

            // 退匯金額分配資料}(QryData_4)有資料(資料筆數>0)，且{繼承人資料}無勾選任何資料時，進行死亡改匯存檔處理作業
            // if(repayIssueAmtDataCaseList.size() > 0 && checkIndex == null ){
            // 更新、刪除 退匯金額分配資料
            // (QryData_1) 受款人資料
            BaappbaseDataUpdateCase babasicupdateData = CaseSessionHelper.getCheckOldAgeDeathRepayDataCase(request);
            // (QryData_2) 受款人退匯中，可改匯的核付資料
            List<BaappbaseDataUpdateCase> deathRepayDataCaseList = CaseSessionHelper.getOldAgeDeathRepayDataCaseList(request);
            // (QryData_3) 受款人的繼承人資料 檢核 案件今日是否有改匯或取消改匯的記錄使用
            List<BaappbaseDataUpdateCase> heirSeqNoExistDataCaseCheckList = CaseSessionHelper.getHeirSeqNoExistDataCaseList(request);

            // 檢核 本案件今日是否有取消改匯的記錄
            if (checkIndex == null) {
                for (BaappbaseDataUpdateCase deathRepayData : deathRepayDataCaseList) {
                    String checkAfChkDateCount = updateService.selectCheckAfChkDateForCheckBox(babasicupdateData.getApNo(), babasicupdateData.getSeqNo(), DateUtility.changeChineseYearMonthType(deathRepayData.getOriIssuYm()),
                                    DateUtility.changeChineseYearMonthType(deathRepayData.getPayYm()));
                    if (Integer.parseInt(checkAfChkDateCount) >= 2) {
                        saveMessages(session, CustomMessageHelper.getCheckAfChkDateForCheckBoxMessage());
                        return mapping.findForward(FORWARD_MODIFY_OLDAGE_DEATH_REPAY_DATA_LIST);
                    }
                }
            }
            // 檢核 本案件今日是否有改匯的記錄 有勾選 無資料
            if (checkIndex != null && repayIssueAmtDataCaseListOld.size() <= 0) {
                for (BaappbaseDataUpdateCase deathRepayData : deathRepayDataCaseList) {
                    String checkAfChkDateCount = updateService.selectCheckAfChkDateForCheckBox(babasicupdateData.getApNo(), babasicupdateData.getSeqNo(), DateUtility.changeChineseYearMonthType(deathRepayData.getOriIssuYm()),
                                    DateUtility.changeChineseYearMonthType(deathRepayData.getPayYm()));
                    if (Integer.parseInt(checkAfChkDateCount) >= 2) {
                        saveMessages(session, CustomMessageHelper.getCheckAfChkDateForCheckBoxMessage());
                        return mapping.findForward(FORWARD_MODIFY_OLDAGE_DEATH_REPAY_DATA_LIST);
                    }
                }
                for (int i = 0; i < checkIndex.length; i++) {
                    int Index = checkIndex[i];
                    for (BaappbaseDataUpdateCase deathRepayData : deathRepayDataCaseList) {
                        String checkAfChkDateCount = updateService.selectCheckAfChkDateForCheckBox(babasicupdateData.getApNo(), heirSeqNoExistDataCaseCheckList.get(Index).getSeqNo(),
                                        DateUtility.changeChineseYearMonthType(deathRepayData.getOriIssuYm()), DateUtility.changeChineseYearMonthType(deathRepayData.getPayYm()));
                        if (Integer.parseInt(checkAfChkDateCount) >= 2) {
                            saveMessages(session, CustomMessageHelper.getCheckAfChkDateForCheckBoxMessage());
                            return mapping.findForward(FORWARD_MODIFY_OLDAGE_DEATH_REPAY_DATA_LIST);
                        }
                    }
                }
            }
            // 檢核 本案件今日是否有改匯的記錄 有勾選 有資料
            if (checkIndex != null && repayIssueAmtDataCaseListOld.size() > 0) {
                // 先檢核選取資料是否已做過分配1
                for (int i = 0; i < checkIndex.length; i++) {
                    int Index = checkIndex[i];
                    for (BaappbaseDataUpdateCase deathRepayData : deathRepayDataCaseList) {
                        String checkAfChkDateCount = updateService.selectCheckAfChkDateForCheckBox(babasicupdateData.getApNo(), heirSeqNoExistDataCaseCheckList.get(Index).getSeqNo(),
                                        DateUtility.changeChineseYearMonthType(deathRepayData.getOriIssuYm()), DateUtility.changeChineseYearMonthType(deathRepayData.getPayYm()));
                        if (Integer.parseInt(checkAfChkDateCount) >= 2) {
                            saveMessages(session, CustomMessageHelper.getCheckAfChkDateForCheckBoxMessage());
                            return mapping.findForward(FORWARD_MODIFY_OLDAGE_DEATH_REPAY_DATA_LIST);
                        }
                    }
                }

                // 還原預設值 不還原無法取得無勾選狀態之null
                queryForm.setIndex(null);

                // 抓取分配資料之繼承人序使用 不能重複
                HashSet<String> seqNoSet = new HashSet<String>();
                // 檢核勾選之繼承人是否已分配過2
                for (BaappbaseDataUpdateCase repayIssueAmtData : repayIssueAmtDataCaseListOld) {
                    seqNoSet.add(repayIssueAmtData.getSeqNo());
                }
                final List<String> seqNoList = new ArrayList<String>();
                for (String value : seqNoSet) {
                    seqNoList.add(value);
                }
                // seqNo重新排序
                Collections.sort(seqNoList);

                // 組合已分配資料seqNo字串
                StringBuffer repayIssueAmtDataSeqno = new StringBuffer();
                // 組合勾選資料seqNo字串
                StringBuffer heirSeqNoExistDataSeqno = new StringBuffer();

                for (int i = 0; i < seqNoList.size(); i++) {
                    repayIssueAmtDataSeqno.append(seqNoList.get(i));
                }

                // 抓取勾選之seqno跟已分配資料比對是否重複
                for (int i = 0; i < checkIndex.length; i++) {
                    int Index = checkIndex[i];
                    heirSeqNoExistDataSeqno.append(heirSeqNoExistDataCaseCheckList.get(Index).getSeqNo());
                }
                if (repayIssueAmtDataSeqno.toString().equals(heirSeqNoExistDataSeqno.toString())) {
                    saveMessages(session, CustomMessageHelper.getCheckRepeatHeirMessage());
                    return mapping.findForward(FORWARD_MODIFY_OLDAGE_DEATH_REPAY_DATA_LIST);
                }

            }

            // 依查詢出來的(QryData_6).REMITAMT(退匯金額)與計算出來的[$對應繼承人序所分配後改匯金額]比較其金額是否相同。
            if (checkIndex != null) {
                for (BaappbaseDataUpdateCase oldAgeDeathRepayData : QryData_2) {
                    // 分得人數
                    BigDecimal checkIndexSize = new BigDecimal(checkIndex.length);
                    // 給付金額
                    BigDecimal remitAmt = oldAgeDeathRepayData.getRemitAmt();
                    // 給付年月
                    String payYM = oldAgeDeathRepayData.getPayYm();
                    List<BigDecimal> avgNumList = getAvgNum(checkIndexSize, remitAmt);

                    // 分給勾選人數
                    for (int i = 0; i <= checkIndex.length - 1; i++) {
                        int Index = checkIndex[i];
                        String apNo = QryData_1.getApNo();
                        String heirSeqNo = QryData_3.get(Index).getSeqNo();
                        String oriIssuYm = DateUtility.changeChineseYearMonthType(oldAgeDeathRepayData.getOriIssuYm());
                        String payYm = DateUtility.changeChineseYearMonthType(oldAgeDeathRepayData.getPayYm());
                        BigDecimal avgNumAfter = avgNumList.get(i);
                        // 原來的退匯金額
                        BigDecimal avgNumBefore = updateService.getRemitAmtDataForCheckAvgNum(apNo, heirSeqNo, oriIssuYm, payYm);
                        if (avgNumBefore != null) {
                            if (avgNumBefore.equals(BigDecimal.ZERO) == false) {
                                // 依查詢出來的(QryData_6).REMITAMT(退匯金額)與計算出來的[$對應繼承人序所分配後改匯金額]比較其金額是否相同。若金額不同時，則不允許執行作業。
                                if (avgNumBefore.equals(avgNumAfter) == false) {
                                    String heirSeqNoMessage = "「受款人序" + heirSeqNo + "的退匯金額已改變，請至受款人資料更正作業變更受款人序(改匯註銷再新增)，才可重新分派」。";
                                    saveMessages(session, CustomMessageHelper.getRemitAmtDataForCheckAvgNumMessage(heirSeqNoMessage));
                                    return mapping.findForward(FORWARD_MODIFY_OLDAGE_DEATH_REPAY_DATA_LIST);
                                }
                            }
                        }
                        // 重新分配後的退匯金額
                    }
                }
            }

            // 更新核定檔(BADAPR)受款人的資料 將REMITMK改回5
            // for(BaappbaseDataUpdateCase deathRepayData : deathRepayDataCaseList){
            // updateService.updateRepayIssueAmtDataForDeathRepay(babasicupdateData.getApNo(),babasicupdateData.getSeqNo(), DateUtility.changeChineseYearMonthType(deathRepayData.getOriIssuYm()),
            // DateUtility.changeChineseYearMonthType(deathRepayData.getPayYm()));
            // }
            // 刪除核定檔(BADAPR)的資料：需依照(QryData_2)的資料筆數，逐筆刪除。即(QryData_2)有2筆資料時，需刪除2筆資料所對應的資料，以此類推。
            // for(BaappbaseDataUpdateCase deathRepayData : deathRepayDataCaseList){
            // babasicupdateData.setOriIssuYm(deathRepayData.getOriIssuYm());
            // babasicupdateData.setPayYm(deathRepayData.getPayYm());
            // //刪除BADAPR REMITMK為2的所有資料
            // updateService.deleteRepayIssueAmtDataForDeathRepay(userData,babasicupdateData);
            // }
            // {繼承人資料}無勾選任何資料時，僅需要修改核定檔(BADAPR)的資料，需依照(QryData_2)的資料筆數，逐筆更新。
            // if(checkIndex == null){
            // for(BaappbaseDataUpdateCase deathRepayData : deathRepayDataCaseList){
            // //新增BADAPR 因無勾選，取消分配，新增原受款人資料
            // updateService.updateOldAgeDeathRepayDataBy(deathRepayData.getRemitAmt(),DateUtility.changeDateType(deathRepayData.getBrChkDate()),babasicupdateData.getApNo(),babasicupdateData.getSeqNo(),DateUtility.changeChineseYearMonthType(deathRepayData.getOriIssuYm()),DateUtility.changeChineseYearMonthType(deathRepayData.getPayYm()));
            // }
            // }
            // return mapping.findForward(FORWARD_MODIFY_OLDAGE_DEATH_REPAY_DATA_LIST);
            // }

            // 以下為QryData_4無資料，有勾選，新增資料
            // {繼承人資料}有勾選資料時，則新增核定檔(BADAPR)的資料：需依照勾選的資料筆數，逐筆計算金額及新增資料
            // qry_data2
            List<BaappbaseDataUpdateCase> oldAgeDeathRepayDataCaseList = CaseSessionHelper.getOldAgeDeathRepayDataCaseList(request);
            // qry_data3
            List<BaappbaseDataUpdateCase> heirSeqNoExistDataCaseList = CaseSessionHelper.getHeirSeqNoExistDataCaseList(request);
            // 受款人資料 qry_data1
            BaappbaseDataUpdateCase checkOldAgeDeathRepayData = CaseSessionHelper.getCheckOldAgeDeathRepayDataCase(request);

            // 檢核完成，開始進行死亡改匯存檔處理作業(作業中若其中一個異動失敗時，或 CALL PROCEDURE傳出值(V_O_PROCMSGCODE)<>0時，皆視同處理作業失敗，需整批做ROLLBACK。)
            updateService.doOldAgeDeathRepayOperation(userData, checkIndex, deathRepayDataCaseList, babasicupdateData, oldAgeDeathRepayDataCaseList, checkOldAgeDeathRepayData, heirSeqNoExistDataCaseList, repayIssueAmtDataCaseListOld);

            // 以勾選的繼承人資料，逐筆新增對應的核定檔資料，(有勾選且Qry_data4無資料)
            // if(checkIndex != null){
            // for(BaappbaseDataUpdateCase oldAgeDeathRepayData : oldAgeDeathRepayDataCaseList){
            //
            // BigDecimal checkIndexSize = new BigDecimal(checkIndex.length);
            // BigDecimal remitAmt = oldAgeDeathRepayData.getRemitAmt();
            // List<BigDecimal> avgNumList = getAvgNum(checkIndexSize,remitAmt);
            // BaappbaseDataUpdateCase insertDataCase = new BaappbaseDataUpdateCase();
            // //加入insert資料 Insert BADAPR
            // insertDataCase.setApNo(checkOldAgeDeathRepayData.getApNo());
            // insertDataCase.setSeqNo(checkOldAgeDeathRepayData.getSeqNo());
            // insertDataCase.setOriIssuYm(DateUtility.changeChineseYearMonthType(oldAgeDeathRepayData.getOriIssuYm()));
            // insertDataCase.setPayYm(DateUtility.changeChineseYearMonthType(oldAgeDeathRepayData.getPayYm()));
            //
            // for(int i = 0 ; i <= checkIndex.length-1 ; i++){
            // int Index = checkIndex[i];
            // insertDataCase.setHeirSeqNo(heirSeqNoExistDataCaseList.get(Index).getSeqNo());
            // insertDataCase.setAvgNum(avgNumList.get(i));
            // //INSERT勾選分配後資料進入BADAPR
            // updateService.insertDataForDeathRepay(insertDataCase);
            // }
            // }
            // }

            // 當{退匯金額分配資料}(QryData_4)無任何資料(資料筆數<=0)時，更新死亡退匯的受款人資料，將其資料狀態更新為給付收回。需依照(QryData_2)的資料筆數，逐筆更新。
            // if(repayIssueAmtDataCaseListOld.size() <= 0 ){
            // for(BaappbaseDataUpdateCase oldAgeDeathRepayData : oldAgeDeathRepayDataCaseList){
            // //更新退匯及改匯檔
            // String apNo = checkOldAgeDeathRepayData.getApNo();
            // String seqNo = checkOldAgeDeathRepayData.getSeqNo();
            // String oriIssuYm = DateUtility.changeChineseYearMonthType(oldAgeDeathRepayData.getOriIssuYm());
            // String payYm = DateUtility.changeChineseYearMonthType(oldAgeDeathRepayData.getPayYm());
            // String procUser = userData.getEmpNo();
            // String procDeptId = userData.getDeptId();
            // String procIp = userData.getLoginIP();
            // //將BRMK、AFMK改為 2、4 原為 3、3
            // updateService.updateBapflbacDeathRepayForQryData_2By(apNo,seqNo,oriIssuYm,payYm);
            // //將MK、AFMK改為 4、4 原為 1、1 call SP後會回寫分配後資料 新資料為 1、1
            // updateService.updateBaregivedtlDeathRepayForQryData_2By(apNo,seqNo,oriIssuYm,payYm,procUser,procDeptId,procIp);
            // //呼叫PROCEDURE 將出納table PFLBAC 的資料 MK 改為 4 原資料為1，新增 出納table PFLBACEVENT 將出納受款人資料 MK改為4 原為1，因為已分配金額 故MK改為4
            // String procMsg =updateService.doExpDeathRepayRec(apNo,seqNo,oriIssuYm,payYm,DateUtility.getNowWestDate());
            // }
            // }
            // 當{退匯金額分配資料}(QryData_4)有資料(資料筆數 > 0)時
            // if(repayIssueAmtDataCaseListOld.size() > 0 ){
            // for(BaappbaseDataUpdateCase repayIssueAmtData : repayIssueAmtDataCaseListOld){
            // //更新退匯及改會檔
            // String apNo = checkOldAgeDeathRepayData.getApNo();
            // String seqNo = repayIssueAmtData.getSeqNo();
            // String issuYm = DateUtility.changeChineseYearMonthType(repayIssueAmtData.getIssuYm());
            // String payYm = DateUtility.changeChineseYearMonthType(repayIssueAmtData.getPayYm());
            // String procUser = userData.getEmpNo();
            // String procDeptId = userData.getDeptId();
            // String procIp = userData.getLoginIP();
            // //將BRMK、AFMK改為 2、4 原為 3、3
            // updateService.updateBapflbacDeathRepayForQryData_4By(apNo,seqNo,issuYm,payYm);
            // //呼叫PROCEDURE 將出納table PFLBAC 的資料 MK 改為 4 原資料為1，新增 出納table PFLBACEVENT 將出納受款人資料 MK改為4 原為1，因為已分配金額 故MK改為4
            // updateService.updateBaregivedtlDeathRepayForQryData_4By(apNo,seqNo,issuYm,payYm,procUser,procDeptId,procIp);
            // //呼叫PROCEDURE
            // String procMsg = updateService.doExpDeathRepayRec(checkOldAgeDeathRepayData.getApNo(),repayIssueAmtData.getSeqNo(),repayIssueAmtData.getIssuYm(),repayIssueAmtData.getPayYm(),DateUtility.getNowWestDate());
            // }
            // }

            // 當{退匯金額分配資料}(QryData_4)有資料(資料筆數 > 0) 無勾選時
            // if(repayIssueAmtDataCaseListOld.size() > 0 && checkIndex == null ){
            // BaappbaseDataUpdateCase insertBapflbacDataCase = new BaappbaseDataUpdateCase();
            // for(BaappbaseDataUpdateCase oldAgeDeathRepayData : oldAgeDeathRepayDataCaseList){
            // insertBapflbacDataCase.setApNo(checkOldAgeDeathRepayData.getApNo());
            // insertBapflbacDataCase.setSeqNo(checkOldAgeDeathRepayData.getSeqNo());
            // insertBapflbacDataCase.setOriIssuYm(DateUtility.changeChineseYearMonthType(oldAgeDeathRepayData.getOriIssuYm()));
            // insertBapflbacDataCase.setPayYm(DateUtility.changeChineseYearMonthType(oldAgeDeathRepayData.getPayYm()));
            // insertBapflbacDataCase.setRemitAmt(oldAgeDeathRepayData.getRemitAmt());
            // insertBapflbacDataCase.setProcUser(userData.getEmpNo());
            // insertBapflbacDataCase.setProcIp(userData.getLoginIP());
            // insertBapflbacDataCase.setProcDeptId(userData.getDeptId());
            // //新增原受款人資料回Bapflbac
            // updateService.insertBapflbacDataForDeathRepayBy(insertBapflbacDataCase);
            // }
            // }

            // 當{退匯金額分配資料}(QryData_4)無資料(資料筆數 > 0) 有勾選時
            // if(checkIndex != null){
            // //以勾選的繼承人資料，逐筆新增對應的核定檔資料，(有勾選且Qry_data4無資料)
            // for(BaappbaseDataUpdateCase oldAgeDeathRepayData : oldAgeDeathRepayDataCaseList){
            //
            // BigDecimal checkIndexSize = new BigDecimal(checkIndex.length);
            // BigDecimal remitAmt = oldAgeDeathRepayData.getRemitAmt();
            // List<BigDecimal> avgNumList = getAvgNum(checkIndexSize,remitAmt);
            // BaappbaseDataUpdateCase insertBapflbacDataCase = new BaappbaseDataUpdateCase();
            // //加入insert資料
            // insertBapflbacDataCase.setApNo(checkOldAgeDeathRepayData.getApNo());
            // insertBapflbacDataCase.setSeqNo(checkOldAgeDeathRepayData.getSeqNo());
            // insertBapflbacDataCase.setOriIssuYm(DateUtility.changeChineseYearMonthType(oldAgeDeathRepayData.getOriIssuYm()));
            // insertBapflbacDataCase.setPayYm(DateUtility.changeChineseYearMonthType(oldAgeDeathRepayData.getPayYm()));
            // insertBapflbacDataCase.setProcUser(userData.getEmpNo());
            // insertBapflbacDataCase.setProcIp(userData.getLoginIP());
            // insertBapflbacDataCase.setProcDeptId(userData.getDeptId());
            // for(int i = 0 ; i <= checkIndex.length-1 ; i++){
            // int Index = checkIndex[i];
            // String heirSeqNo = heirSeqNoExistDataCaseList.get(Index).getSeqNo();
            // insertBapflbacDataCase.setHeirSeqNo(heirSeqNo);
            // insertBapflbacDataCase.setAvgNum(avgNumList.get(i));
            // //新增分配金額資料進入Bapflbac CALL第二個SP回寫分配資料時，會抓取此資料
            // updateService.insertBapflbacDataForDeathRepay2By(insertBapflbacDataCase);
            // }
            // }
            // }

            // if(checkIndex == null ){
            // for(BaappbaseDataUpdateCase oldAgeDeathRepayData : oldAgeDeathRepayDataCaseList){
            // String apNo = checkOldAgeDeathRepayData.getApNo();
            // String seqNo = checkOldAgeDeathRepayData.getSeqNo();
            // String oriIssuYm = DateUtility.changeChineseYearMonthType(oldAgeDeathRepayData.getOriIssuYm());
            // String payYm = DateUtility.changeChineseYearMonthType(oldAgeDeathRepayData.getPayYm());
            // String procMsg = updateService.doDeathRepayRefundment(apNo,seqNo,oriIssuYm,payYm,DateUtility.getNowWestDate());
            // String procMsg11 = procMsg;
            // }
            // }
            //
            // if(checkIndex != null ){
            // for(BaappbaseDataUpdateCase oldAgeDeathRepayData : oldAgeDeathRepayDataCaseList){
            // for(int i = 0 ; i <= checkIndex.length-1 ; i++){
            // int Index = checkIndex[i];
            // String apNo = checkOldAgeDeathRepayData.getApNo();
            // String seqNo = heirSeqNoExistDataCaseList.get(Index).getSeqNo();
            // String oriIssuYm = DateUtility.changeChineseYearMonthType(oldAgeDeathRepayData.getOriIssuYm());
            // String payYm = DateUtility.changeChineseYearMonthType(oldAgeDeathRepayData.getPayYm());
            // //CALL SP回寫分配金額資料至BAREGIVEDTL 資料MK為1 原分配的資料已改為4 如沒回寫此TABLE 無法抓取新分配之資料
            // //此SP也會新增 分配金額後的資料至PFLBAC出納TABLE
            // String procMsg = updateService.doDeathRepayRefundment(apNo,seqNo,oriIssuYm,payYm,DateUtility.getNowWestDate());
            // String procMsg11 = procMsg;
            // }
            // }
            // }

            // 更新頁面資料
            // [繼承人序] 條件：當「受款人序」=0000時，[$繼承人序]=0%,當「受款人序」=0X00時，[$繼承人序]=0X%,當「受款人序」=0XX0時，[$繼承人序]=0XX%
            String heirSeqNo = "";
            if (queryForm.getSeqNo().equals("0000")) {
                heirSeqNo = "";
            }
            else if (!queryForm.getSeqNo().substring(1, 2).equals("0") && queryForm.getSeqNo().substring(2, 3).equals("0")) {
                heirSeqNo = queryForm.getSeqNo().substring(0, 2);
            }
            else if (!queryForm.getSeqNo().substring(1, 2).equals("0") && !queryForm.getSeqNo().substring(2, 3).equals("0")) {
                heirSeqNo = queryForm.getSeqNo().substring(0, 3);
            }
            // 退匯金額分配資料 (QryData_4)
            log.info(" KIYOMI 死亡改匯 OldAgeDeathRepayAction.doConfirm (00) : queryForm.getApNoStr() : " + Encode.forJava(queryForm.getApNoStr())); 
            log.info(" KIYOMI 死亡改匯 OldAgeDeathRepayAction.doConfirm (00-00) : heirSeqNo : " + Encode.forJava(heirSeqNo)); 
            List<BaappbaseDataUpdateCase> repayIssueAmtDataCaseList = updateService.getRepayIssueAmtDataBy(queryForm.getApNoStr(), heirSeqNo);
            log.info(" KIYOMI 死亡改匯 OldAgeDeathRepayAction.doConfirm (01) : repayIssueAmtDataCaseList.size : " + repayIssueAmtDataCaseList.size());
            String indexCount = updateService.selectRepayIssueAmtDataCountBy(queryForm.getApNoStr(), heirSeqNo);
            log.info(" KIYOMI 死亡改匯 OldAgeDeathRepayAction.doConfirm (02) : indexCount : " + Encode.forJava(indexCount)); 
            for (BaappbaseDataUpdateCase repayIssueAmtDataNew : repayIssueAmtDataCaseList) {
                repayIssueAmtDataNew.setIndexCount(indexCount);
                repayIssueAmtDataNew.setIssuYm(DateUtility.changeWestYearMonthType(repayIssueAmtDataNew.getIssuYm()));
                repayIssueAmtDataNew.setPayYm(DateUtility.changeWestYearMonthType(repayIssueAmtDataNew.getPayYm()));
            }
            log.info(" KIYOMI 死亡改匯 OldAgeDeathRepayAction.doConfirm (03) : repayIssueAmtDataCaseList.size : " + repayIssueAmtDataCaseList.size());
            // 檢核QryData4(退匯金額分配資料)是否有資料 無=0 有=1 帶入隱藏欄位 checkQryData4
            if (repayIssueAmtDataCaseList.size() <= 0) {
                queryForm.setCheckQryData4("0");
            }
            else {
                queryForm.setCheckQryData4("1");
            }
            log.info(" KIYOMI 死亡改匯 OldAgeDeathRepayAction.doConfirm (04) : repayIssueAmtDataCaseList.size : " + repayIssueAmtDataCaseList.size());
            CaseSessionHelper.removeRepayIssueAmtDataCaseList(request);
            log.info(" KIYOMI 死亡改匯 OldAgeDeathRepayAction.doConfirm (05) : repayIssueAmtDataCaseList.size : " + repayIssueAmtDataCaseList.size());
            // 更新頁面資料
            CaseSessionHelper.setRepayIssueAmtDataCaseList(repayIssueAmtDataCaseList, request);
            log.info(" KIYOMI 死亡改匯 OldAgeDeathRepayAction.doConfirm (06) : repayIssueAmtDataCaseList.size : " + repayIssueAmtDataCaseList.size());

            // 還原預設值 不還原無法取得無勾選狀態之null
            queryForm.setIndex(null);

            // 帶入預設 退匯金額分配資料 進入checkbox ------begin
            // 先取得已分配資料之 seqno 不得重複
            HashSet<String> seqNoSet = new HashSet<String>();
            //// 退匯金額分配資料 (QryData_4) repayIssueAmtDataCaseList
            for (BaappbaseDataUpdateCase repayIssueAmtData : repayIssueAmtDataCaseList) {
                seqNoSet.add(repayIssueAmtData.getSeqNo());
            }
            final List<String> seqNoList = new ArrayList<String>();
            for (String value : seqNoSet) {
                seqNoList.add(value);
            }
            // seqNo重新排序
            Collections.sort(seqNoList);

            StringBuffer indexString = new StringBuffer();
            // 比對繼承人seqno (QryData_3) heirSeqNoExistDataCaseList
            for (int i = 0; i < heirSeqNoExistDataCaseList.size(); i++) {
                for (String seqNo : seqNoList) {
                    if (heirSeqNoExistDataCaseList.get(i).getSeqNo().equals(seqNo)) {
                        indexString.append(i);
                    }
                }
            }
            // 放入case中帶入session
            // (QryData_1) 受款人資料
            BaappbaseDataUpdateCase checkOldAgeDeathRepayDataCase = CaseSessionHelper.getCheckOldAgeDeathRepayDataCase(request);
            checkOldAgeDeathRepayDataCase.setIndexString(indexString.toString());
            CaseSessionHelper.setCheckOldAgeDeathRepayDataCase(checkOldAgeDeathRepayDataCase, request);
            // 帶入預設 退匯金額分配資料 進入checkbox ------end
            log.info(" KIYOMI 死亡改匯 OldAgeDeathRepayAction.doConfirm (07) : repayIssueAmtDataCaseList.size : " + repayIssueAmtDataCaseList.size());
            saveMessages(session, CustomMessageHelper.getOldAgeDeathRepaySuccessMessage());

            return mapping.findForward(FORWARD_MODIFY_OLDAGE_DEATH_REPAY_DATA_LIST);
        }
        catch (Exception e) {
            // 設定死亡改匯作業訊息
            log.error("OldAgeDeathRepayAction.doConfirm() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getOldAgeDeathRepayFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }

    }

    /**
     * 更正作業 - 死亡改匯處理作業 - 案件資料 按下 [改匯通知書] bamo0d091c.jsp
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 死亡改匯處理作業 - 案件資料列印 OldAgeDeathRepayAction.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        OldAgeDeathRepayForm iform = (OldAgeDeathRepayForm) form;

        try {
            // 抓報表資料
            // (queryForm)
            OldAgeDeathRepayForm queryForm = FormSessionHelper.getOldAgeDeathRepayForm(request);
            // (QryData_1)
            BaappbaseDataUpdateCase checkOldAgeDeathRepayDataCase = CaseSessionHelper.getCheckOldAgeDeathRepayDataCase(request);
            // (QryData_2)
            List<BaappbaseDataUpdateCase> oldAgeDeathRepayDataCaseList = CaseSessionHelper.getOldAgeDeathRepayDataCaseList(request);
            // (QryData_3)
            List<BaappbaseDataUpdateCase> heirSeqNoExistDataCaseList = CaseSessionHelper.getHeirSeqNoExistDataCaseList(request);
            // (QryData_4)
            List<BaappbaseDataUpdateCase> repayIssueAmtDataCaseList = CaseSessionHelper.getRepayIssueAmtDataCaseList(request);
            log.info(" KIYOMI 死亡改匯 OldAgeDeathRepayAction.doReport (01) : repayIssueAmtDataCaseList.size : " + repayIssueAmtDataCaseList.size());
            if (oldAgeDeathRepayDataCaseList.size() > 0) {
                for (BaappbaseDataUpdateCase oldAgeDeathRepayData : oldAgeDeathRepayDataCaseList) {
                    String ParamName = updateService.getParamNameForDeathRepayData(oldAgeDeathRepayData.getBrNote());
                    oldAgeDeathRepayData.setParamName(ParamName);
                }
            }

            // 給付種類
            String payKind = queryForm.getApNo1();

            ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payKind)) {// 老年
                OldAgeDeathRepayReport report = new OldAgeDeathRepayReport();
                log.info(" KIYOMI 死亡改匯 OldAgeDeathRepayAction.doReport (02) : repayIssueAmtDataCaseList.size : " + repayIssueAmtDataCaseList.size());
                baoOutput = report.doReport(userData, queryForm, checkOldAgeDeathRepayDataCase, oldAgeDeathRepayDataCaseList, heirSeqNoExistDataCaseList, repayIssueAmtDataCaseList);
            }

            String printDate = DateUtility.getNowChineseDate();

            // 設定回傳回 Client 端之檔案大小, 若不設定,
            // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payKind)) {// 老年
                response.setHeader("Content-disposition", "attachment; filename=\"OldAgeDeathRepayReport_" + printDate + ".pdf" + "\"");
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
            }
            catch (Exception e) {
            }
            finally {
                if (baoOutput != null)
                    baoOutput.close();
                if (sout != null)
                    sout.close();
            }
            return null;
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("產製 更正作業 - 死亡改匯處理作業  OldAgeDeathRepayAction.doReport() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getReportGenerateFailMessage());
            return mapping.findForward(FORWARD_MODIFY_OLDAGE_DEATH_REPAY_DATA_LIST);
        }
    }

    /**
     * 更正作業 - 死亡改匯處理作業 - 按下 繼承人維護 動作 (bamo0d091c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdateQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 死亡改匯處理作業 - 繼承人維護 OldAgeDeathRepayAction.doUpdateQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        OldAgeDeathRepayForm queryForm = (OldAgeDeathRepayForm) form;
        PayeeDataUpdateQueryForm updateQueryForm = new PayeeDataUpdateQueryForm();

        updateQueryForm.setApNo1(queryForm.getApNo1());
        updateQueryForm.setApNo2(queryForm.getApNo2());
        updateQueryForm.setApNo3(queryForm.getApNo3());
        updateQueryForm.setApNo4(queryForm.getApNo4());
        updateQueryForm.setSeqNo(queryForm.getSeqNo());

        // 清除 Session
        CaseSessionHelper.removePayeeDataUpdateQueryCase(request);
        CaseSessionHelper.removePayeeDataUpdateDetailCase(request);
        CaseSessionHelper.removePayeeDataUpdateList(request);
        CaseSessionHelper.removePayeeDataForBadaprCase(request);
        // 清除明細資料畫面
        FormSessionHelper.removePayeeDataUpdateQueryForm(request);
        FormSessionHelper.removePayeeDataUpdateDetailForm(request);

        try {
            // [繼承人序] 條件：當「受款人序」=0000時，[$繼承人序]=0%,當「受款人序」=0X00時，[$繼承人序]=0X%,當「受款人序」=0XX0時，[$繼承人序]=0XX%
            String heirSeqNo = "";
            if (queryForm.getSeqNo().equals("0000")) {
                heirSeqNo = "";
            }
            else if (!queryForm.getSeqNo().substring(1, 2).equals("0") && queryForm.getSeqNo().substring(2, 3).equals("0")) {
                heirSeqNo = queryForm.getSeqNo().substring(0, 2);
            }
            else if (!queryForm.getSeqNo().substring(1, 2).equals("0") && !queryForm.getSeqNo().substring(2, 3).equals("0")) {
                heirSeqNo = queryForm.getSeqNo().substring(0, 3);
            }
            // List<BaappbaseDataUpdateCase> applyList = updateService.selectPayeeDataForList(queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4());
            // 頁面使用
            List<BaappbaseDataUpdateCase> applyList = updateService.selectPayeeDataListForOldAgeDeathRepayBy(queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4(), heirSeqNo, queryForm.getSeqNo());
            // 後端使用
            List<BaappbaseDataUpdateCase> applyDataList = updateService.selectPayeeDataListForOldAgeDeathRepayBy(queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4(), heirSeqNo, queryForm.getSeqNo());

            if (applyList.size() <= 0) {
                // MSG：W1003-無此受理號碼或尚未產生核定資料！
                saveMessages(session, DatabaseMessageHelper.getNoResultForApNoMessage());
                return mapping.findForward(FORWARD_MODIFY_OLDAGE_DEATH_REPAY_DATA_LIST);
            }

            if (applyList.size() > 0) {
                for (BaappbaseDataUpdateCase caseData : applyList) {
                    // 判斷是否開啟 改匯註銷按鈕使用
                    String dateCount1 = updateService.getDataCount1ForQuery(caseData.getApNo(), caseData.getSeqNo());
                    String dateCount2 = updateService.getDataCount2ForQuery(caseData.getApNo(), caseData.getSeqNo());
                    String dateCount3 = updateService.getDataCount3ForQuery(caseData.getApNo(), caseData.getSeqNo());
                    caseData.setDateCount1(dateCount1);
                    caseData.setDateCount2(dateCount2);
                    caseData.setDateCount3(dateCount3);
                }
            }

            FormSessionHelper.setPayeeDataUpdateQueryForm(updateQueryForm, request);
            // 後端新增修改資料 使用之List
            CaseSessionHelper.setPayeeDataUpdateQueryCaseList(applyDataList, request);
            // 把原受款人自List中移除 帶入頁面
            for (int i = 0; i < applyList.size(); i++) {
                if (applyList.get(i).getSeqNo().equals(queryForm.getSeqNo())) {
                    applyList.remove(i);
                }
            }
            // 頁面使用List
            CaseSessionHelper.setPayeeDataUpdateQueryCase(applyList, request);

            return mapping.findForward(FORWARD_MODIFY_PAYEE_DATA_LIST);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("繼承人維護 OldAgeDeathRepayAction.doUpdateQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 更正作業 - 死亡改匯處理作業 - 老年年金受款人死亡改匯處理頁面 按下 返回 動作(bamo0d091c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */

    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        // 清除 Session
        CaseSessionHelper.removeCheckOldAgeDeathRepayDataCase(request);
        CaseSessionHelper.removeOldAgeDeathRepayDataCaseList(request);
        CaseSessionHelper.removeHeirSeqNoExistDataCaseList(request);
        CaseSessionHelper.removeRepayIssueAmtDataCaseList(request);

        // 清除明細資料畫面
        FormSessionHelper.removeOldAgeDeathRepayForm(request);

        return mapping.findForward(ConstantKey.FORWARD_BACK);
    }

    public static List<BigDecimal> getAvgNum(BigDecimal persons, BigDecimal num) {
        List<BigDecimal> result = new ArrayList<BigDecimal>();
        for (int i = 1; i <= persons.longValue(); i++) {
            result.add(!(num.remainder(persons).compareTo(new BigDecimal(i)) == -1) ? num.divide(persons, BigDecimal.ROUND_DOWN).add(new BigDecimal(1)) : num.divide(persons, BigDecimal.ROUND_DOWN));
        }
        return result;
    }

    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }

    public void setSelectOptionService(SelectOptionService selectOptionService) {
        this.selectOptionService = selectOptionService;
    }

}
