package tw.gov.bli.ba;

import java.math.BigDecimal;

public class ConstantKey {
    // ---------------------------------------------------------
    // 常數
    // ---------------------------------------------------------
    // 受理編號長度
    public static final int APNO_LENGTH = 12;
    // 貸方科目長度(不含檢查碼)(ex:123456__67890_)
    public static final int ACCOUNT_LENGTH = 14;

    // DisplayTag 分頁每頁資料筆數
    // [
    public static final int LIST_PAGE_SIZE = 10;
    public static final int DECISION_LIST_PAGE_SIZE = 20;
    public static final int DETAIL_LIST_PAGE_SIZE = 0; // 不分頁
    // ]
    // For Log
    // [
    // 用來存放欄位修改前值的 Property 名稱 Prefix
    public static final String OLD_FIELD_PREFIX = "old";
    // ResourceBundle 中 oldage receipt for Baapplog 欄位的 Key Prefix
    public static final String BAAPPLOG_RECEIPT_OLDAGE_FIELD_RESOURCE_PREFIX = "label.receipt.oldAgeAnnuityReceipt.";
    // ResourceBundle 中 communication update for Baapplog 欄位的 Key Prefix
    public static final String BAAPPLOG_UPDATE_COMM_FIELD_RESOURCE_PREFIX = "label.update.communicationDataUpdate.";
    // ResourceBundle 中 payment update for Baapplog 欄位的 Key Prefix
    public static final String BAAPPLOG_UPDATE_PAYMENT_FIELD_RESOURCE_PREFIX = "label.update.paymentDataUpdate.";
    // ResourceBundle 中 payee data update for Baapplog 欄位的 Key Prefix
    public static final String BAAPPLOG_UPDATE_PAYEE_FIELD_RESOURCE_PREFIX = "label.update.payeeDataUpdate.";
    // ResourceBundle 中 Inherritor Register update for Baapplog 欄位的 Key Prefix
    public static final String BAAPPLOG_UPDATE_INHERITOR_FIELD_RESOURCE_PREFIX = "label.update.inheritorRegr.";
    // ResourceBundle 中 application update for Baapplog 欄位的 Key Prefix
    public static final String BAAPPLOG_UPDATE_APPLICATION_FIELD_RESOURCE_PREFIX = "label.update.applicationDataUpdate.";
    // ResourceBundle 中 disabled receipt for Baapplog 欄位的 Key Prefix
    public static final String BAAPPLOG_RECEIPT_DISABLED_FIELD_RESOURCE_PREFIX = "label.receipt.disabledAnnuityReceipt.";
    // ResourceBundle 中 survivor receipt for Baapplog 欄位的 Key Prefix
    public static final String BAAPPLOG_RECEIPT_SURVIVOR_FIELD_RESOURCE_PREFIX = "label.receipt.survivorAnnuityReceipt.";
    // ResourceBundle 中 Disabled Application Data Update for Baapplog 欄位的 Key Prefix
    public static final String BAAPPLOG_DISABLED_APPLICATION_DATA_UPDATE_FIELD_RESOURCE_PREFIX = "label.update.disabled.";
    // ResourceBundle 中 Survivor Application Data Update for Baapplog 欄位的 Key Prefix
    public static final String BAAPPLOG_SURVIVOR_APPLICATION_DATA_UPDATE_FIELD_RESOURCE_PREFIX = "label.update.survivor.";
    // ResourceBundle 中 dependant data update for Baapplog 欄位的 Key Prefix
    public static final String BAAPPLOG_UPDATE_DEPENDANT_FIELD_RESOURCE_PREFIX = "label.update.dependantDataUpdate.";
    // ResourceBundle 中 oldage Review for Baapplog 欄位的 Key Prefix
    public static final String BAAPPLOG_REVIEW_OLDAGE_FIELD_RESOURCE_PREFIX = "label.review.PaymentReview.";

    // 即時編審回傳值
    // [
    public static final String DO_CHECK_MARK_SUCCESS = "0";// 成功
    public static final String DO_CHECK_MARK_FAIL = "1";// 失敗
    // ]

    // ]
    // ---------------------------------------------------------
    // Database Field Value Constant
    // ---------------------------------------------------------
    // BAPARAM - 系統參數檔
    // [
    // 參數種類 PARAMGROUP : PAYCODE
    public static final String BAPARAM_PARAMGROUP_PAYCODE = "PAYCODE";
    public static final String BAPARAM_PARAMGROUP_KRECHKSTATUS = "KRECHKSTATUS";
    public static final String BAPARAM_PARAMGROUP_KRECHKRESULT1 = "KRECHKRESULT1";
    public static final String BAPARAM_PARAMGROUP_KRECHKRESULT2 = "KRECHKRESULT2";
    public static final String BAPARAM_PARAMGROUP_CHKGROUP = "CHKGROUP";
    public static final String BAPARAM_PARAMGROUP_CHKLEVEL = "CHKLEVEL";
    public static final String BAPARAM_PARAMGROUP_CHKOBJ = "CHKOBJ";
    public static final String BAPARAM_PARAMGROUP_LETTERTYPE = "LETTERTYPE";
    public static final String BAPARAM_PARAMGROUP_RECHKLVL = "RECHKLVL";
    public static final String BAPARAM_PARAMGROUP_PAYTYP = "PAYTYPE";
    public static final String BAPARAM_PARAMGROUP_MITRATE = "MITRATE";
    public static final String BAPARAM_PARAMGROUP_APITEM = "APITEM";
    public static final String BAPARAM_PARAMGROUP_CLOSECAUSE = "CLOSECAUSE";
    public static final String BAPARAM_PARAMGROUP_STEXPNDREASON = "STEXPNDREASON";
    public static final String BAPARAM_PARAMGROUP_CHGNOTE = "CHGNOTE";
    public static final String BAPARAM_PARAMGROUP_RELIEFTYP = "RELIEFTYP";
    public static final String BAPARAM_PARAMGROUP_RELIEFKIND = "RELIEFKIND";
    public static final String BAPARAM_PARAMGROUP_RELIEFSTAT = "RELIEFSTAT";
    public static final String BAPARAM_PARAMGROUP_EVTYP = "EVTYP";
    public static final String BAPARAM_PARAMGROUP_DISQUALMK = "DISQUALMK";
    public static final String BAPARAM_PARAMGROUP_KCLOSECAUSE = "KCLOSECAUSE";
    public static final String BAPARAM_PARAMGROUP_SCLOSECAUSE = "SCLOSECAUSE";
    public static final String BAPARAM_PARAMGROUP_SURVIVORCLOSECAUSE = "SURVIVORCLOSECAUSE";
    public static final String BAPARAM_PARAMGROUP_REASCODE = "REASCODE";
    public static final String BAPARAM_PARAMGROUP_SURVIVORUNQUALIFIEDCAUSE = "UNQUALIFIEDCAUSE";

    // 參數類別 PARAMTYP : 內部參數
    public static final String BAPARAM_PARAMTYP_PRIVATE = "0";
    // 參數類別 PARAMTYP : 共用參數
    public static final String BAPARAM_PARAMTYP_SHARE = "1";
    // ]

    // ---------------------------------------------------------
    // ActionMapping Key, 注意: 必須對應 struts-config.xml
    // ---------------------------------------------------------
    public static final String FORWARD_BACK = "back";
    public static final String FORWARD_SUCCESS = "success";
    public static final String FORWARD_FAIL = "fail";
    public static final String CSRF_FAIL = "csrffail";
    public static final String FORWARD_QUERY_SUCCESS = "querySuccess";
    public static final String FORWARD_QUERY_DETAIL = "queryDetail";
    public static final String FORWARD_QUERY_DETAIL_02 = "queryDetail02";
    public static final String FORWARD_QUERY_DETAIL_03 = "queryDetail03";
    public static final String FORWARD_QUERY_DETAIL_04 = "queryDetail04";
    public static final String FORWARD_QUERY_FAIL = "queryFail";
    public static final String FORWARD_QUERY_EMPTY = "queryEmpty";
    public static final String FORWARD_QUERY_UNACPDTL_EMPTY = "queryUnacpdtlEmpty";
    public static final String FORWARD_SAVE_SUCCESS = "saveSuccess";
    public static final String FORWARD_SAVE_FAIL = "saveFail";
    public static final String FORWARD_UPDATE_SUCCESS = "updateSuccess";
    public static final String FORWARD_UPDATE_FAIL = "updateFail";
    public static final String FORWARD_DELETE_SUCCESS = "deleteSuccess";
    public static final String FORWARD_DELETE_FAIL = "deleteFail";
    public static final String FORWARD_QUERY_LIST = "queryList";
    public static final String FORWARD_QUERY_UNACPDTL_LIST = "queryUnacpdtlList";
    public static final String FORWARD_QUERY_UNACPDTL_PRTDATE_LIST = "queryUnacpdtlPrtdateList";
    public static final String FORWARD_QUERY_INTEREST_LIST = "queryInterestList";
    public static final String FORWARD_PAYMENT_ASSIGN_LIST = "paymentAssignList";
    public static final String FORWARD_PAYMENT_ASSIGN_LAST_LIST = "paymentAssignLast";
    public static final String PAYMENT_ASSIGN_LAST_CASE_LIST = "paymentAssignLastList";
    public static final String FORWARD_PAYMENT_COMP_PRINT = "compPrint";
    public static final String PAYMENT_SAVE_SUCCESS = "insertSuccess";
    public static final String FORWARD_QUERY_DTL_EMPTY = "queryDtlEmpty";
    public static final String FORWARD_QUERY_DETAIL_SUCCESS = "queryDtlSuccess";
    public static final String FORWARD_QUERY_DETAIL_FAIL = "queryDtlFail";
    // ---------------------------------------------------------
    // Form Request / Session Key
    // ---------------------------------------------------------
    // 維護作業 - 先抽對象維護作業 - 查詢頁面 (bapa0x040f.jsp)
    public static final String PREVIEW_CONDITION_MAINT_QUERY_FORM = "PreviewConditionMaintQueryForm";
    // 維護作業 - 先抽對象維護作業 - 老年年金維護作業 (bapa0x042c.jsp)
    public static final String PREVIEW_CONDITION_MAINT_DETAIL_FOR_OLD_AGE_ANNUITY_FORM = "PreviewConditionMaintDetailForOldAgeAnnuityForm";
    // 受理作業 - 老年年金給付受理作業
    public static final String OLDAGE_ANNUITY_RECEIPT_FORM = "OldAgeAnnuityReceiptForm";
    // 受理作業 - 老年年金給付受理作業 - 查詢條件FORM
    public static final String OLDAGE_ANNUITY_RECEIPT_QRYCOND_FORM = "OldAgeAnnuityReceiptQryCondForm";
    // 受理作業 - 老年年金給付受理作業 - 查詢條件FORM
    public static final String OLDAGE_ANNUITY_RECEIPT_QUERY_FORM = "OldAgeAnnuityReceiptQueryForm";
    // 受理作業 - 失能年金臨櫃受理作業
    public static final String DISABLED_ANNUITY_WALK_IN_RECEIPT_QUERY_FORM = "DisabledAnnuityWalkInReceiptQueryForm";
    // 受理作業 - 失能年金給付受理作業
    public static final String DISABLED_ANNUITY_RECEIPT_FORM = "DisabledAnnuityReceiptForm";
    // 受理作業 - 失能年金給付受理作業
    public static final String DISABLED_ANNUITY_RECEIPT_FAM_FORM = "DisabledAnnuityReceiptFamForm";
    // 受理作業 - 失能年金給付受理作業 - 查詢條件FORM
    public static final String DISABLED_ANNUITY_RECEIPT_QRYCOND_FORM = "DisabledAnnuityReceiptQryCondForm";
    // 受理作業 - 失能年金給付受理作業 - 查詢條件FORM
    public static final String DISABLED_ANNUITY_RECEIPT_QUERY_FORM = "DisabledAnnuityReceiptQueryForm";
    // 維護作業 - 編審註記維護作業 (bapa0x010f.jsp)
    public static final String CHECK_MARK_MAINT_QUERY_FORM = "CheckMarkMaintQueryForm";
    // 維護作業 - 編審註記維護作業 (bapa0x011a.jsp)
    public static final String CHECK_MARK_MAINT_DETAIL_FORM = "CheckMarkMaintDetailForm";
    // 維護作業 - 核定通知書維護作業 (bapa0x020f.jsp)
    public static final String ADVICE_MAINT_QUERY_FORM = "AdviceMaintQueryForm";
    // 維護作業 - 核定通知書維護作業 (bapa0x021a.jsp)
    public static final String ADVICE_MAINT_DETAIL_FORM = "AdviceMaintDetailForm";
    // 維護作業 - 決行層級維護作業 (bapa0x030f.jsp)
    public static final String DECISION_LEVEL_MAINT_QUERY_FORM = "DecisionLevelMaintQueryForm";
    // 維護作業 - 決行層級維護作業 (bapa0x031a.jsp)
    public static final String DECISION_LEVEL_MAINT_DETAIL_FORM = "DecisionLevelMaintDetailForm";
    // 維護作業 - 物價指數調整明細維護作業 (bapa0x060f.jsp)
    public static final String CPI_DTL_MAINT_QUERY_FORM = "CpiDtlMaintQueryForm";
    // 維護作業 - 物價指數調整明細維護作業 (bapa0x061c.jsp)
    public static final String CPI_DTL_MAINT_DETAIL_FORM = "CpiDtlMaintDetailForm";
    // 維護作業 - 物價指數調整紀錄維護作業 (bapa0x070f.jsp)
    public static final String CPI_REC_MAINT_QUERY_FORM = "CpiRecMaintQueryForm";
    // 維護作業 - 物價指數調整紀錄維護作業 (bapa0x071c.jsp)
    public static final String CPI_REC_MAINT_DETAIL_FORM = "CpiRecMaintDetailForm";

    // 維護作業 - 老年年金加計金額調整作業(bapa0x080q.jsp)
    public static final String BASIC_AMT_MAINT_QUERYL_FORM = "BasicAmtMaintQueryForm";
    // 維護作業 - 老年年金加計金額調整作業 (bapa0x081f.jsp)
    public static final String BASIC_AMT_MAINT_DETAIL_FORM = "BasicAmtMaintDetailForm";

    // 維護作業 - 勞保年金平均薪資月數參數維護修改作業 (bapa0x110q.jsp)
    public static final String AVG_AMT_MON_MAINT_QUERY_FORM = "AvgAmtMonMaintQueryForm";
    // 維護作業 - 勞保年金平均薪資月數參數維護修改作業 (bapa0x111f.jsp)
    public static final String AVG_AMT_MON_MAINT_LIST_FORM = "AvgAmtMonMaintListForm";
    // 維護作業 - 勞保年金平均薪資月數參數維護修改作業 (bapa0x111f.jsp)
    public static final String AVG_AMT_MON_MAINT_DETAIL_FORM = "AvgAmtMonMaintDetailForm";

    // 維護作業 - 老年年金所得替代率參數維護修改作業 (bapa0x120q.jsp)
    public static final String REPLACEMENT_RATIO_MAINT_QUERY_FORM = "ReplacementRatioMaintQueryForm";
    // 維護作業 - 老年年金所得替代率參數維護修改作業 (bapa0x121f.jsp)
    public static final String REPLACEMENT_RATIO_MAINT_LIST_FORM = "ReplacementRatioMaintListForm";
    // 維護作業 - 老年年金所得替代率參數維護修改作業 (bapa0x122a.jsp,bapa0x123c.jsp)
    public static final String REPLACEMENT_RATIO_MAINT_DETAIL_FORM = "ReplacementRatioMaintDetailForm";
    // 維護作業 - 失能年金所得替代率參數維護修改作業 (bapa0x130q.jsp)
    public static final String DISABLED_REPLACEMENT_RATIO_MAINT_QUERY_FORM = "DisabledReplacementRatioMaintQueryForm";
    // 維護作業 - 失能年金所得替代率參數維護修改作業 (bapa0x131f.jsp)
    public static final String DISABLED_REPLACEMENT_RATIO_MAINT_LIST_FORM = "DisabledReplacementRatioMaintListForm";
    // 維護作業 - 失能年金所得替代率參數維護修改作業 (bapa0x132a.jsp,bapa0x133c.jsp)
    public static final String DISABLED_REPLACEMENT_RATIO_MAINT_DETAIL_FORM = "DisabledReplacementRatioMaintDetailForm";
    // 維護作業 - 遺屬年金所得替代率參數維護修改作業 (bapa0x140q.jsp)
    public static final String SURVIVOR_REPLACEMENT_RATIO_MAINT_QUERY_FORM = "SurvivorReplacementRatioMaintQueryForm";
    // 維護作業 - 遺屬年金所得替代率參數維護修改作業 (bapa0x141f.jsp)
    public static final String SURVIVOR_REPLACEMENT_RATIO_MAINT_LIST_FORM = "SurvivorReplacementRatioMaintListForm";
    // 維護作業 - 遺屬年金所得替代率參數維護修改作業 (bapa0x142a.jsp,bapa0x143c.jsp)
    public static final String SURVIVOR_REPLACEMENT_RATIO_MAINT_DETAIL_FORM = "SurvivorReplacementRatioMaintDetailForm";
    // 維護作業 - 重設案件給付參數資料 (bapa0x150q.jsp)
    public static final String RESET_PAYMENT_PARAMETER_FORM = "ResetPaymentParameterForm";

    // 查詢作業 - 編審註記查詢作業 - 查詢頁面 (baiq0d040q.jsp)
    public static final String CHECK_MARK_LEVEL_QUERY_FORM = "CheckMarkLevelQueryForm";
    // 維護作業 - 扣繳維護作業 (bapa0x051a.jsp)
    public static final String DEDUCT_ITEM_MAINT_DETAIL_FORM = "DeductItemMaintDetailForm";
    // 查詢作業 - 更正日誌查詢 - 查詢頁面 (baiq0d030q.jsp)
    public static final String UPDATE_LOG_QUERY_FORM = "UpdateLogQueryForm";
    // 查詢作業 - 行政支援查詢 - 查詢頁面 (biq0d050q.jsp)
    public static final String EXECUTIVE_SUPPORT_QUERY_FORM = "ExecutiveSupportQueryForm";
    // 查詢作業 - 行政支援查詢 - 查詢頁面 (biq0d050q.jsp)
    public static final String EXECUTIVE_SUPPORT_QUERY_COND_FORM = "ExecutiveSupportQueryCondForm";
    // 查詢作業 - 行政支援查詢 - 清單頁面 (biq0d051q.jsp)
    public static final String EXECUTIVE_SUPPORT_QUERY_LIST_FORM = "ExecutiveSupportQueryListForm";
    // 查詢作業 - 行政支援查詢 - 查詢頁面 (biq0d050q.jsp)
    public static final String CHECK_MARK_LEVEL_QUERY_QUERY_FORM = "CheckMarkLevelQueryForm";
    // 更正作業 - 通訊資料更正 (bamo0d010c.jsp)
    public static final String COMMUNICATION_DATA_UPDATE_QUERY_FORM = "CommunicationDataUpdateQueryForm";
    // 更正作業 - 通訊資料更正 (bamo0d010c.jsp)
    public static final String COMMUNICATION_DATA_UPDATE_FORM = "CommunicationDataUpdateForm";
    // 更正作業 - 給付資料更正 (bamo0d020c.jsp)
    public static final String PAYMENT_DATA_UPDATE_QUERY_FORM = "PaymentDataUpdateQueryForm";
    // 更正作業 - 給付資料更正 (bamo0d020c.jsp)
    public static final String PAYMENT_DATA_UPDATE_FORM = "PaymentDataUpdateForm";
    // 更正作業 - 務繼承人資料更正 (bamo0d050a.jsp)
    public static final String INHERITOR_REGISTER_FORM = "InheritorRegisterForm";
    // 更正作業 - 結案狀態變更作業 (bamo0d310c.jsp)
    public static final String CLOSE_STATUS_ALTERATION_FORM = "CloseStatusAlterationForm";
    // 更正作業 - 編審註記程度調整 (bamo0d060c.jsp)
    public static final String CHECK_MARK_LEVEL_ADJUST_FORM = "CheckMarkLevelAdjustForm";
    // 更正作業 - 編審註記程度調整 - 失能年金編審註記程度調整 (bamo0d160c.jsp bamo0d161c.jsp)
    public static final String DISABLED_CHECK_MARK_LEVEL_ADJUST_FORM = "DisabledCheckMarkLevelAdjustForm";
    // 更正作業 - 編審註記程度調整 - 遺屬年金編審註記程度調整 (bamo0d260c.jsp bamo0d261c.jsp)
    public static final String SURVIVOR_CHECK_MARK_LEVEL_ADJUST_FORM = "SurvivorCheckMarkLevelAdjustForm";
    // 更正作業 - 案件資料更正 - 失能年金案件資料更正 (bamo0d130c.jsp bamo0d131c.jsp bamo0d133c.jsp)
    public static final String DISABLED_APPLICATION_DATA_UPDATE_FORM = "DisabledApplicationDataUpdateForm";
    // 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 (bamo0d230c.jsp bamo0d231c.jsp bamo0d233c.jsp)
    public static final String SURVIVOR_APPLICATION_DATA_UPDATE_FORM = "SurvivorApplicationDataUpdateForm";
    // 更正作業 - 案件資料更正 (bamo0d030c.jsp)
    public static final String APPLICATION_DATA_UPDATE_FORM = "ApplicationDataUpdateForm";
    // 更正作業 - 案件資料更正 (bamo0d030c.jsp)
    public static final String APPLICATION_DATA_UPDATE_QUERY_FORM = "ApplicationDataUpdateQueryForm";
    // 更正作業 - 案件資料更正 - 欠費期間維護(bamo0d022n.jsp)
    public static final String OWES_DATA_UPDATE_FORM = "OwesDataUpdateForm";
    // 更正作業 - 案件資料更正 - 一次給付資料更正(bamo0d032c.jsp)
    public static final String ONCEAMT_DATA_UPDATE_FORM = "OnceAmtDataUpdateForm";
    // 更正作業 - 受款人資料更正 (bamo0d080c.jsp)
    public static final String PAYEE_DATA_UPDATE_QUERY_FORM = "PayeeDataUpdateQueryForm";
    // 更正作業 - 受款人資料更正 (bamo0d080c.jsp)
    public static final String PAYEE_DATA_UPDATE_DETAIL_FORM = "PayeeDataUpdateDetailForm";
    // 更正作業 - 失能年金受款人資料更正 (bamo0d180c.jsp)
    public static final String DISABLED_PAYEE_DATA_UPDATE_QUERY_FORM = "DisabledPayeeDataUpdateQueryForm";
    // 更正作業 - 失能年金受款人資料更正 (bamo0d180c.jsp)
    public static final String DISABLED_PAYEE_DATA_UPDATE_DETAIL_FORM = "DisabledPayeeDataUpdateDetailForm";
    // 更正作業 - 遺屬年金受款人資料更正 (bamo0d280c.jsp)
    public static final String SURVIVOR_PAYEE_DATA_UPDATE_QUERY_FORM = "SurvivorPayeeDataUpdateQueryForm";
    // 更正作業 - 遺屬年金受款人資料更正 (bamo0d280c.jsp)
    public static final String SURVIVOR_PAYEE_DATA_UPDATE_DETAIL_FORM = "SurvivorPayeeDataUpdateDetailForm";
    // 更正作業 - 遺屬年金受款人資料更正 (bamo0d286c.jsp)
    public static final String SURVIVOR_PAYEE_DATA_UPDATE_STUDTERM_FORM = "SurvivorPayeeDataUpdateStudTermForm";
    // 更正作業 - 遺屬年金受款人資料更正 (bamo0d289c01.jsp)
    public static final String SURVIVOR_PAYEE_DATA_UPDATE_HANDICAPTERM_FORM = "SurvivorPayeeDataUpdateHandicapTermForm";
    // 更正作業 - 遺屬年金受款人資料更正 (bamo0d289c.jsp)
    public static final String SURVIVOR_PAYEE_DATA_UPDATE_COMPEL_DATA_FORM = "SurvivorPayeeDataUpdateCompelDataForm";
    // 更正作業 - 眷屬資料更正 (bamo0d070c.jsp)
    public static final String DEPENDANT_DATA_UPDATE_QUERY_FORM = "DependantDataUpdateQueryForm";
    // 更正作業 - 眷屬資料更正 (bamo0d070c.jsp)
    public static final String DEPENDANT_DATA_UPDATE_DETAIL_FORM = "DependantDataUpdateDetailForm";
    // 更正作業 - 眷屬資料更正 (bamo0d074c.jsp)
    public static final String DEPENDANT_DATA_UPDATE_STUDTERM_FORM = "DependantDataUpdateStudTermForm";
    // 更正作業 - 眷屬資料更正 (bamo0d075c.jsp)
    public static final String DEPENDANT_DATA_UPDATE_COMPEL_DATA_FORM = "DependantDataUpdateCompelDataForm";
    // 更正作業 - 止付處理 (bamo0d040n.jsp)
    public static final String STOP_PAYMENT_PROCESS_QUERY_FORM = "StopPaymentProcessQueryForm";
    // 更正作業 - 止付處理 (bamo0d041n.jsp)
    public static final String STOP_PAYMENT_PROCESS_DETAIL_FORM = "StopPaymentProcessDetailForm";
    // 更正作業 - 死亡改匯處理作業 (bamo0d090c.jsp)
    public static final String OLDAGE_DEATH_REPAY_FORM = "OldAgeDeathRepayForm";
    // 更正作業 - 應收收回處理作業 - 老年年金應收收回處理作業 (bamo0d100c.jsp)
    public static final String OLDAGE_PAYMENT_RECEIVE_FORM = "OldAgePaymentReceiveForm";
    // 更正作業 - 應收收回處理作業 - 失能年金應收收回處理作業 (bamo0d200c.jsp)
    public static final String DISABLED_PAYMENT_RECEIVE_FORM = "DisabledPaymentReceiveForm";
    // 更正作業 - 應收收回處理作業 - 遺屬年金應收收回處理作業 (bamo0d200c.jsp)
    public static final String SURVIVOR_PAYMENT_RECEIVE_FORM = "SurvivorPaymentReceiveForm";

    // 審核作業 - 給付審核作業 (baco0d010a.jsp)
    public static final String PAYMENT_REVIEW_FORM = "PaymentReviewForm";
    // 審核作業 - 給付審核作業 - 查詢條件 (baco0d010a.jsp)
    public static final String PAYMENT_REVIEW_QUERY_FORM = "PaymentReviewQueryForm";
    // 審核作業 - 失能年金給付審核作業 (baco0d110a.jsp)
    public static final String DISABLED_PAYMENT_REVIEW_FORM = "DisabledPaymentReviewForm";
    // 審核作業 - 失能年金給付審核作業 - 查詢條件 (baco0d110a.jsp)
    public static final String DISABLED_PAYMENT_REVIEW_QUERY_FORM = "DisabledPaymentReviewQueryForm";
    // 審核作業 - 遺屬年金給付審核作業 (baco0d210a.jsp)
    public static final String SURVIVOR_PAYMENT_REVIEW_FORM = "SurvivorPaymentReviewForm";
    // 審核作業 - 遺屬年金給付審核作業 - 查詢條件 (baco0d210a.jsp)
    public static final String SURVIVOR_PAYMENT_REVIEW_QUERY_FORM = "SurvivorPaymentReviewQueryForm";

    // 決行作業 - 老年年金給付決行作業 (barc0d010a.jsp)
    public static final String PAYMENT_DECISION_FORM = "PaymentDecisionForm";
    // 決行作業 - 老年年金給付決行作業 - 查詢條件 (barc0d010a.jsp)
    public static final String PAYMENT_DECISION_QUERY_FORM = "PaymentDecisionQueryForm";
    // 決行作業 - 失能年金給付決行作業 (barc0d110a.jsp)
    public static final String DISABLED_PAYMENT_DECISION_FORM = "DisabledPaymentDecisionForm";
    // 決行作業 - 失能年金給付決行作業 - 查詢條件 (barc0d110a.jsp)
    public static final String DISABLED_PAYMENT_DECISION_QUERY_FORM = "DisabledPaymentDecisionQueryForm";
    // 決行作業 - 遺屬年金給付決行作業 (barc0d210a.jsp)
    public static final String SURVIVOR_PAYMENT_DECISION_FORM = "SurvivorPaymentDecisionForm";
    // 決行作業 - 遺屬年金給付決行作業 - 查詢條件 (barc0d210a.jsp)
    public static final String SURVIVOR_PAYMENT_DECISION_QUERY_FORM = "SurvivorPaymentDecisionQueryForm";
    // 查詢作業 - 給付查詢作業 (baiq0d010q.jsp)
    public static final String PAYMENT_QUERY_FORM = "PaymentQueryForm";
    // 查詢作業 - 給付查詢作業 - 查詢條件 (baiq0d010q.jsp)
    public static final String PAYMENT_QUERY_COND_FORM = "PaymentQueryCondForm";
    // 行政支援 - 行政支援記錄維護
    public static final String EXECUTIVE_SUPPORT_MAINT_FORM = "ExecutiveSupportMaintForm";
    // 行政支援 - 行政支援記錄維護
    public static final String EXECUTIVE_SUPPORT_MAINT_ADD_FORM = "ExecutiveSupportMaintAddForm";
    // 行政支援 - 行政支援記錄維護
    public static final String EXECUTIVE_SUPPORT_MAINT_LIST_FORM = "ExecutiveSupportMaintListForm";
    // 行政支援 - 行政支援記錄維護
    public static final String EXECUTIVE_SUPPORT_MAINT_MODIFY_FORM = "ExecutiveSupportMaintModifyForm";
    // 行政支援 - 行政支援記錄銷案
    public static final String EXECUTIVE_SUPPORT_CLOSE_QUERY_FORM = "ExecutiveSupportCloseQueryForm";
    // 行政支援 - 行政支援記錄銷案
    public static final String EXECUTIVE_SUPPORT_CLOSE_FORM = "ExecutiveSupportCloseForm";
    // 查詢作業 - 應收查詢作業 (baiq0d020q.jsp)
    public static final String RECEIVABLE_QUERY_FORM = "ReceivableQueryForm";
    // 查詢作業 - 應收查詢作業 - 查詢條件 (baiq0d020q.jsp)
    public static final String RECEIVABLE_QUERY_COND_FORM = "ReceivableQueryCondForm";

    // 複檢費用 - 複檢費用受理作業 (bare0d010a.jsp bare0d011a.jsp bare0d012c.jsp bare0d013c.jsp)
    public static final String REVIEW_FEE_RECEIPT_FORM = "ReviewFeeReceiptForm";
    // 複檢費用 - 複檢費用審核作業 (bare0d020a.jsp bare0d021a.jsp bare0d022a.jsp)
    public static final String REVIEW_FEE_REVIEW_FORM = "ReviewFeeReviewForm";
    // 複檢費用 - 複檢費用決行作業 (bare0d030a.jsp bare0d031a.jsp bare0d032a.jsp)
    public static final String REVIEW_FEE_DECISION_FORM = "ReviewFeeDecisionForm";

    // 批次處理 - 給付媒體回押註記作業 (baba0m010x.jsp)
    public static final String UPDATE_PAID_MARK_BJ_FORM = "UpdatePaidMarkBJForm";
    // 批次處理 - 給付媒體回押註記作業 (baba0m012x.jsp)
    public static final String UPDATE_PAID_MARK_BJ_DETAIL_FORM = "UpdatePaidMarkBJDetailForm";
    // 批次處理 - 收回作業 (baba0m020x.jsp)
    public static final String RETURN_WRITE_OFF_BJ_FORM = "ReturnWriteOffBJForm";
    // 批次處理 - 轉催收作業 (baba0m030x.jsp)
    public static final String TRANS_2_OVERDUE_RECEIVABLE_BJ_FORM = "Trans2OverdueReceivableBJForm";
    // 批次處理 - 轉催收作業 - 查詢條件 (baba0m030x.jsp)
    public static final String TRANS_2_OVERDUE_RECEIVABLE_BJ_COND_FORM = "Trans2OverdueReceivableBJCondForm";
    // 批次處理 - 檢核確認作業 - 查詢條件 (baba0m210x.jsp)
    public static final String CHECK_JOB_FORM = "CheckJobForm";
    // 批次處理 - 產生檢核案件作業 - 查詢條件 (baba0m200x.jsp)
    public static final String CHECK_JOB_RPT_FORM = "CheckJobRptForm";
    // 批次處理 - 已收調整作業 (baba0m040x.jsp)
    public static final String RECEIVABLE_ADJUST_BJ_FORM = "ReceivableAdjustBJForm";
    // 批次處理 - 已收調整作業 - 查詢條件 (baba0m040x.jsp)
    public static final String RECEIVABLE_ADJUST_BJ_COND_FORM = "ReceivableAdjustBJCondForm";
    // 批次處理 - 已收調整作業 - 查詢條件 (baba0m050x.jsp)
    public static final String MONTH_BATCH_L_FORM = "MonthBatchLForm";
    // 批次處理 - 已收調整作業 - 查詢條件 (baba0m060x.jsp)
    public static final String MONTH_BATCH_K_FORM = "MonthBatchKForm";
    // 批次處理 - 已收調整作業 - 查詢條件 (baba0m070x.jsp)
    public static final String MONTH_BATCH_S_FORM = "MonthBatchSForm";
    // 批次處理 - 已收調整作業 - 查詢條件 (baba0m080x.jsp)
    public static final String MONTH_CHECK_L_FORM = "MonthCheckLForm";
    // 批次處理 - 已收調整作業 - 查詢條件 (baba0m090x.jsp)
    public static final String MONTH_CHECK_K_FORM = "MonthCheckKForm";
    // 批次處理 - 已收調整作業 - 查詢條件 (baba0m100x.jsp)
    public static final String MONTH_CHECK_S_FORM = "MonthCheckSForm";
    // 批次處理 - 已收調整作業 - 查詢條件 (baba0m110x.jsp)
    public static final String MONTH_L_FORM = "MonthLForm";
    // 批次處理 - 已收調整作業 - 查詢條件 (baba0m120x.jsp)
    public static final String MONTH_K_FORM = "MonthKForm";
    // 批次處理 - 已收調整作業 - 查詢條件 (baba0m130x.jsp)
    public static final String MONTH_S_FORM = "MonthSForm";
    // 批次處理 - 批次排程作業 - 批次程式設定作業 (baba0m220x.jsp)
    public static final String SET_PROCEDURE_FORM = "SetProcedureForm";
    // 批次處理 - 批次排程作業 - 批次程式執行(baba0m230x.jsp)
    public static final String RUN_PROCEDURE_FORM = "RunProcedureForm";
    // 批次處理 - 批次排程作業 - 批次程式查詢作業 (baba0m240x.jsp)
    public static final String QRY_PROCEDURE_FORM = "QryProcedureForm";
    // 受理作業 - 遺屬年金給付受理作業 - FORM
    public static final String SURVIVOR_ANNUITY_RECEIPT_FORM = "SurvivorAnnuityReceiptForm";
    // 受理作業 - 遺屬年金給付受理作業 - FORM
    public static final String SURVIVOR_ANNUITY_RECEIPT_BEN_FORM = "SurvivorAnnuityReceiptBenForm";
    // 受理作業 - 遺屬年金給付受理作業 - 查詢FORM
    public static final String SURVIVOR_ANNUITY_RECEIPT_QUERY_FORM = "SurvivorAnnuityReceiptQueryForm";
    // 受理作業 - 老年年金給付受理作業 - 查詢條件
    public static final String SURVIVOR_ANNUITY_RECEIPT_QRYCOND_FORM = "SurvivorAnnuityReceiptQryCondForm";

    // 列印作業 - 其他類報表 - 補送在學通知函
    public static final String MONTHLY_RPT_29_FORM = "MonthlyRpt29Form";
    // 列印作業 - 其他類報表 - 查核失能程度通知函
    public static final String MONTHLY_RPT_30_FORM = "MonthlyRpt30Form";
    // 列印作業 - 其他類報表 - 老年差額金通知
    public static final String MONTHLY_RPT_31_FORM = "MonthlyRpt31Form";
    // 列印作業 - 應收收回相關報表 - 整批收回核定清單 (balp0d690l.jsp)
    public static final String BATCH_PAYMENT_RECEIVE_FORM = "BatchPaymentReceiveForm";
    // 批次作業 - 年金統計執行作業 
    public static final String EXEC_STATISTICS_FORM = "ExecStatisticsForm";

    // ---------------------------------------------------------
    // Case Request / Session Key
    // ---------------------------------------------------------
    // LIST
    // [
    // 受理作業 - 老年年金給付受理作業 - 查詢結果清單
    public static final String OLDAGE_ANNUITY_RECEIPT_LIST = "OldAgeAnnuityReceiptList";
    // 受理作業 - 失能年金給付受理作業 - 查詢結果清單
    public static final String DISABLED_ANNUITY_RECEIPT_LIST = "DisabledAnnuityReceiptList";
    // 受理作業 - 失能年金給付受理作業 - 遺屬眷屬暫存檔資料列編號
    public static final String DISABLED_ANNUITY_RECEIPT_BAFAMILYTEMPID = "DisabledAnnuityReceiptBafamilytempId";
    // 受理作業 - 失能年金給付受理作業 - 眷屬資料清單
    public static final String DISABLED_ANNUITY_RECEIPT_FAM_DATA_LIST = "DisabledAnnuityReceiptFamDataList";
    // 受理作業 - 失能年金給付受理作業 - 登錄國保受理新增作業
    public static final String DISABLED_ANNUITY_RECEIPT_EVT_DATA_CASE = "DisabledAnnuityReceiptEvtDataCase";
    // 受理作業 - 遺屬年金給付受理作業 - 遺屬清單
    public static final String SURVIVOR_ANNUITY_RECEIPT_EVT_DATA = "SurvivorAnnuityReceiptEvtDataList";
    // 受理作業 - 遺屬年金給付受理作業 - 遺屬清單
    public static final String SURVIVOR_ANNUITY_RECEIPT_BEN_DATA_LIST = "SurvivorAnnuityReceiptBenDataList";
    // 受理作業 - 遺屬年金給付受理作業 - 查詢結果清單
    public static final String SURVIVOR_ANNUITY_RECEIPT_LIST = "SurvivorAnnuityReceiptList";
    // 受理作業 - 遺屬年金給付受理作業 - 遺屬眷屬暫存檔資料列編號
    public static final String SURVIVOR_ANNUITY_RECEIPT_BAFAMILYTEMPID = "SurvivorAnnuityReceiptBafamilytempId";
    // 更正作業 - 通訊資料更正 - 查詢結果清單
    public static final String COMMUNICATION_DATA_UPDATE_LIST = "CommunicationDataUpdateList";
    // 更正作業 - 給付資料更正 - 查詢結果清單
    public static final String PAYMENT_DATA_UPDATE_LIST = "PaymentDataUpdateList";
    // 更正作業 - 案件資料更正 - 查詢結果清單
    public static final String APPLICATION_DATA_UPDATE_LIST = "ApplicationDataUpdateList";
    // 更正作業 - 受款人資料更正 - 查詢結果清單
    public static final String PAYEE_DATA_UPDATE_LIST = "PayeeDataUpdateList";
    // 更正作業 - 失能年金受款人資料更正 - 查詢結果清單
    public static final String DISABLED_PAYEE_DATA_UPDATE_LIST = "DisabledPayeeDataUpdateList";
    // 更正作業 - 遺屬年金受款人資料更正 - 查詢結果清單
    public static final String SURVIVOR_PAYEE_DATA_UPDATE_LIST = "SurvivorPayeeDataUpdateList";
    // 更正作業 - 眷屬資料更正 - 查詢結果清單
    public static final String DEPENDANT_DATA_UPDATE_LIST = "DependantDataUpdateList";
    // 更正作業 - 案件資料更正 - 欠費更正資料 - 查詢結果清單
    public static final String BASENIMAINTCASE_LIST = "BasenimaintCaseList";
    // 更正作業 - 案件資料更正 - 身分證號重號資料 - 查詢結果清單
    public static final String APPLICATION_UPDATE_DUPEIDNNO_CASE_LIST = "ApplicationUpdateDupeIdnNoCaseList";
    // 審核作業 - 老年年金給付審核作業 - 查詢結果清單
    public static final String PAYMENT_REVIEW_LIST = "PaymentReviewList";
    // 審核作業 - 老年年金給付審核作業 - 編審註記 查詢結果清單
    public static final String PAYMENT_REVIEW_CHK_LIST = "PaymentReviewChkList";
    // 審核作業 - 老年年金給付審核作業 - 受款人編審註記 查詢結果清單
    public static final String PAYMENT_REVIEW_BEN_CHK_LIST = "PaymentReviewBenChkList";
    // 審核作業 - 老年年金給付審核作業 - 受款人 查詢結果清單
    public static final String PAYMENT_REVIEW_BENNAME_LIST = "PaymentReviewBenNameList";
    // 審核作業 - 老年年金給付審核作業 - 欠費期間資料 查詢結果清單
    public static final String PAYMENT_REVIEW_SENIMAINT_DATA_LIST = "PaymentReviewSenimaintDataList";
    // 審核作業 - 老年年金給付審核作業 - 承保異動資料 查詢結果清單
    public static final String PAYMENT_REVIEW_CIPT_DATA_LIST = "PaymentReviewCiptDataList";
    // 審核作業 - 老年年金給付審核作業 - 編審給付資料 查詢結果清單
    public static final String PAYMENT_REVIEW_PAY_DATA_LIST = "PaymentReviewPayDataList";
    // 審核作業 - 老年年金給付審核作業 - 一次給付更正資料 查詢結果清單
    public static final String PAYMENT_REVIEW_ONCEPAYMODIFY_DATA_LIST = "PaymentReviewOncePayModifyDataList";
    // 審核作業 - 老年年金給付審核作業 - 受款人核定資料 查詢結果清單
    public static final String PAYMENT_REVIEW_BENISSUE_DATA_LIST = "PaymentReviewBenIssueDataList";
    // 審核作業 - 老年年金給付審核作業 - 受款人資料 查詢結果清單
    public static final String PAYMENT_REVIEW_BENEFICIARY_DATA_LIST = "PaymentReviewBeneficiaryDataList";
    // 審核作業 - 老年年金給付審核作業 - 請領同類／他類／另案扣減 - 申請失能給付紀錄 查詢結果清單
    public static final String PAYMENT_REVIEW_CRIPAY_APPLY_DATA_CASE_LIST = "PaymentReviewCriPayApplyDataCaseList";
    // 審核作業 - 老年年金給付審核作業 - 請領同類／他類／另案扣減 - 申請傷病給付紀錄 查詢結果清單
    public static final String PAYMENT_REVIEW_INJPAY_APPLY_DATA_CASE_LIST = "PaymentReviewInjPayApplyDataCaseList";
    // 審核作業 - 老年年金給付審核作業 - 請領同類／他類／另案扣減 - 申請失業給付紀錄 查詢結果清單
    public static final String PAYMENT_REVIEW_UNEMPPAY_APPLY_DATA_CASE_LIST = "PaymentReviewUnEmpPayApplyDataCaseList";
    // 審核作業 - 老年年金給付審核作業 - 請領同類／他類／另案扣減 - 年金給付資料 查詢結果清單
    public static final String PAYMENT_REVIEW_ANNUITYPAY_DATA_CASE_LIST = "PaymentReviewAnnuityPayDataCaseList";
    // 審核作業 - 失能年金給付審核作業 - 查詢結果清單
    public static final String DISABLED_PAYMENT_REVIEW_LIST = "DisabledPaymentReviewList";
    // 審核作業 - 失能年金給付審核作業 - 編審給付資料 查詢結果清單
    public static final String DISABLED_PAYMENT_REVIEW_PAY_DATA_LIST = "DisabledPaymentReviewPayDataList";
    // 審核作業 - 失能年金給付審核作業 - 事故者編審註記 查詢結果清單
    public static final String DISABLED_PAYMENT_REVIEW_EVT_CHK_LIST = "DisabledPaymentReviewEvtChkList";
    // 審核作業 - 失能年金給付審核作業 - 眷屬編審註記 查詢結果清單
    public static final String DISABLED_PAYMENT_REVIEW_BEN_CHK_LIST = "DisabledPaymentReviewBenChkList";
    // 審核作業 - 失能年金給付審核作業 - 符合註記 查詢結果清單
    public static final String DISABLED_PAYMENT_REVIEW_MATCH_CHK_LIST = "DisabledPaymentReviewMatchChkList";
    // 審核作業 - 失能年金給付審核作業 - 受款人資料 查詢結果清單
    public static final String DISABLED_PAYMENT_REVIEW_BENEFICIARY_DATA_LIST = "DisabledPaymentReviewBeneficiaryDataList";
    // 審核作業 - 失能年金給付審核作業 - 受款人 查詢結果清單
    public static final String DISABLED_PAYMENT_REVIEW_BENNAME_LIST = "DisabledPaymentReviewBenNameList";
    // 審核作業 - 遺屬年金給付審核作業 - 查詢結果清單
    public static final String SURVIVOR_PAYMENT_REVIEW_LIST = "SurvivorPaymentReviewList";
    // 審核作業 - 遺屬年金給付審核作業 - 編審給付資料 查詢結果清單
    public static final String SURVIVOR_PAYMENT_REVIEW_PAY_DATA_LIST = "SurvivorPaymentReviewPayDataList";
    // 審核作業 - 遺屬年金給付審核作業 - 事故者編審註記 查詢結果清單
    public static final String SURVIVOR_PAYMENT_REVIEW_EVT_CHK_LIST = "SurvivorPaymentReviewEvtChkList";
    // 審核作業 - 遺屬年金給付審核作業 - 眷屬編審註記 查詢結果清單
    public static final String SURVIVOR_PAYMENT_REVIEW_BEN_CHK_LIST = "SurvivorPaymentReviewBenChkList";
    // 審核作業 - 遺屬年金給付審核作業 - 符合註記 查詢結果清單
    public static final String SURVIVOR_PAYMENT_REVIEW_MATCH_CHK_LIST = "SurvivorPaymentReviewMatchChkList";
    // 審核作業 - 遺屬年金給付審核作業 - 受款人資料 查詢結果清單
    public static final String SURVIVOR_PAYMENT_REVIEW_BENEFICIARY_DATA_LIST = "SurvivorPaymentReviewBeneficiaryDataList";
    // 審核作業 - 遺屬年金給付審核作業 - 受款人核定資料 原始查詢結果清單List
    public static final String SURVIVOR_PAYMENT_REVIEW_ORIG_BEN_ISSU_DATA_LIST = "SurvivorPaymentReviewOrigBenIssuDataList";
    // 審核作業 - 遺屬年金給付審核作業 - 受款人核定資料 查詢結果清單List
    public static final String SURVIVOR_PAYMENT_REVIEW_BEN_ISSU_DATA_LIST = "SurvivorPaymentReviewBenIssuDataList";
    // 審核作業 - 遺屬年金給付審核作業 - 受款人核定資料 查詢結果清單List
    public static final String SURVIVOR_PAYMENT_REVIEW_BEN_ISSU_DATA_MAP = "SurvivorPaymentReviewBenIssuDataMap";
    // 審核作業 - 遺屬年金給付審核作業 - 受款人 查詢結果清單
    public static final String SURVIVOR_PAYMENT_REVIEW_BENNAME_LIST = "SurvivorPaymentReviewBenNameList";

    // 決行作業 - 給付決行作業 - 查詢結果清單
    public static final String PAYMENT_DECISION_LIST = "PaymentDecisionList";
    // 決行作業 - 給付決行作業 - 事故者編審住記 查詢結果清單
    public static final String PAYMENT_DECISION_CHK_LIST = "PaymentDecisionChkList";
    // 決行作業 - 給付決行作業 - 受款人編審住記 查詢結果清單
    public static final String PAYMENT_DECISION_BEN_CHK_LIST = "PaymentDecisionBenChkList";
    // 決行作業 - 給付決行作業 - 受款人 查詢結果清單
    public static final String PAYMENT_DECISION_BENNAME_LIST = "PaymentDecisionBenNameList";
    // 決行作業 - 給付決行作業 - 欠費期間資料 查詢結果清單
    public static final String PAYMENT_DECISION_SENIMAINT_DATA_LIST = "PaymentDecisionSenimaintDataList";
    // 決行作業 - 給付決行作業 - 承保異動資料 查詢結果清單
    public static final String PAYMENT_DECISION_CIPT_DATA_LIST = "PaymentDecisionCiptDataList";
    // 決行作業 - 給付決行作業 - 編審給付資料 查詢結果清單
    public static final String PAYMENT_DECISION_PAY_DATA_LIST = "PaymentDecisionPayDataList";
    // 決行作業 - 給付決行作業 - 一次給付更正資料 查詢結果清單
    public static final String PAYMENT_DECISION_ONCEPAYMODIFY_DATA_LIST = "PaymentDecisionOncePayModifyDataList";
    // 決行作業 - 給付決行作業 - 受款人核定資料 查詢結果清單
    public static final String PAYMENT_DECISION_BENISSUE_DATA_LIST = "PaymentDecisionBenIssueDataList";
    // 決行作業 - 給付決行作業 - 受款人資料 查詢結果清單
    public static final String PAYMENT_DECISION_BENEFICIARY_DATA_LIST = "PaymentDecisionBeneficiaryDataList";
    // 決行作業 - 給付決行作業 - 請領同類／他類／另案扣減 - 申請失能給付紀錄 查詢結果清單
    public static final String PAYMENT_DECISION_CRIPAY_APPLY_DATA_CASE_LIST = "PaymentDecisionCriPayApplyDataCaseList";
    // 決行作業 - 給付決行作業 - 請領同類／他類／另案扣減 - 申請傷病給付紀錄 查詢結果清單
    public static final String PAYMENT_DECISION_INJPAY_APPLY_DATA_CASE_LIST = "PaymentDecisionInjPayApplyDataCaseList";
    // 決行作業 - 給付決行作業 - 請領同類／他類／另案扣減 - 申請失業給付紀錄 查詢結果清單
    public static final String PAYMENT_DECISION_UNEMPPAY_APPLY_DATA_CASE_LIST = "PaymentDecisionUnEmpPayApplyDataCaseList";
    // 決行作業 - 給付決行作業 - 請領同類／他類／另案扣減 - 年金給付資料 查詢結果清單
    public static final String PAYMENT_DECISION_ANNUITYPAY_DATA_CASE_LIST = "PaymentDecisionAnnuityPayDataCaseList";
    // 決行作業 - 失能年金給付決行作業 - 查詢結果清單
    public static final String DISABLED_PAYMENT_DECISION_LIST = "DisabledPaymentDecisionList";
    // 決行作業 - 失能年金給付決行作業 - 編審給付資料 查詢結果清單
    public static final String DISABLED_PAYMENT_DECISION_PAY_DATA_LIST = "DisabledPaymentDecisionPayDataList";
    // 決行作業 - 失能年金給付決行作業 - 事故者編審註記 查詢結果清單
    public static final String DISABLED_PAYMENT_DECISION_EVT_CHK_LIST = "DisabledPaymentDecisionEvtChkList";
    // 決行作業 - 失能年金給付決行作業 - 眷屬編審註記 查詢結果清單
    public static final String DISABLED_PAYMENT_DECISION_BEN_CHK_LIST = "DisabledPaymentDecisionBenChkList";
    // 決行作業 - 失能年金給付決行作業 - 符合註記 查詢結果清單
    public static final String DISABLED_PAYMENT_DECISION_MATCH_CHK_LIST = "DisabledPaymentDecisionMatchChkList";
    // 決行作業 - 失能年金給付決行作業 - 受款人資料 查詢結果清單
    public static final String DISABLED_PAYMENT_DECISION_BENEFICIARY_DATA_LIST = "DisabledPaymentDecisionBeneficiaryDataList";
    // 決行作業 - 失能年金給付決行作業 - 受款人 查詢結果清單
    public static final String DISABLED_PAYMENT_DECISION_BENNAME_LIST = "DisabledPaymentDecisionBenNameList";
    // 決行作業 - 遺屬年金給付決行作業 - 查詢結果清單
    public static final String SURVIVOR_PAYMENT_DECISION_LIST = "SurvivorPaymentDecisionList";
    // 決行作業 - 遺屬年金給付決行作業 - 編審給付資料 查詢結果清單
    public static final String SURVIVOR_PAYMENT_DECISION_PAY_DATA_LIST = "SurvivorPaymentDecisionPayDataList";
    // 決行作業 - 遺屬年金給付決行作業 - 事故者編審註記 查詢結果清單
    public static final String SURVIVOR_PAYMENT_DECISION_EVT_CHK_LIST = "SurvivorPaymentDecisionEvtChkList";
    // 決行作業 - 遺屬年金給付決行作業 - 眷屬編審註記 查詢結果清單
    public static final String SURVIVOR_PAYMENT_DECISION_BEN_CHK_LIST = "SurvivorPaymentDecisionBenChkList";
    // 決行作業 - 遺屬年金給付決行作業 - 符合註記 查詢結果清單
    public static final String SURVIVOR_PAYMENT_DECISION_MATCH_CHK_LIST = "SurvivorPaymentDecisionMatchChkList";
    // 決行作業 - 遺屬年金給付決行作業 - 受款人資料 查詢結果清單
    public static final String SURVIVOR_PAYMENT_DECISION_BENEFICIARY_DATA_LIST = "SurvivorPaymentDecisionBeneficiaryDataList";
    // 決行作業 - 遺屬年金給付決行作業 - 受款人 查詢結果清單
    public static final String SURVIVOR_PAYMENT_DECISION_BENNAME_LIST = "SurvivorPaymentDecisionBenNameList";
    // 決行作業 - 遺屬年金給付審核作業 - 受款人核定資料 原始查詢結果清單List
    public static final String SURVIVOR_PAYMENT_DECISION_ORIG_BEN_ISSU_DATA_LIST = "SurvivorPaymentDecisionOrigBenIssuDataList";
    // 決行作業 - 遺屬年金給付審核作業 - 受款人核定資料 查詢結果清單List
    public static final String SURVIVOR_PAYMENT_DECISION_BEN_ISSU_DATA_LIST = "SurvivorPaymentDecisionBenIssuDataList";
    // 決行作業 - 遺屬年金給付審核作業 - 受款人核定資料 查詢結果清單List
    public static final String SURVIVOR_PAYMENT_DECISION_BEN_ISSU_DATA_MAP = "SurvivorPaymentDecisionBenIssuDataMap";

    // 查詢作業 - 給付查詢作業 - 查詢結果清單
    public static final String PAYMENT_QUERY_MASTER_CASE_LIST = "PaymentQueryMasterCaseList";
    // 查詢作業 - 應收查詢作業 - Master結果清單
    public static final String RECEIVABLE_QUERY_MASTER_CASE_LIST = "ReceivableQueryMasterCaseList";
    // 查詢作業 - 應收查詢作業 - Detail結果清單
    public static final String RECEIVABLE_QUERY_DETAIL_CASE_LIST = "ReceivableQueryDetailCaseList";
    // 查詢作業 - 行政支援查詢 - 查詢結果清單
    public static final String EXECUTIVE_SUPPORT_QUERY_CASE_LIST = "ExecutiveSupportQueryCaseList";
    // 批次處理 - 已收調整作業 - Master結果清單
    public static final String RECEIVABLE_ADJUST_BJ_MASTER_CASE_LIST = "ReceivableQueryMasterCaseList";
    // 列印作業 - 月核定處理相關報表 - 月核定合格清冊
    public static final String MONTHLY_RPT_08_FILE_LIST = "MonthlyRpt08FileList";
    // 列印作業 - 月核定處理相關報表 - 月核定合格清冊
    public static final String MONTHLY_RPT_10_FILE_LIST = "MonthlyRpt10FileList";
    // 列印作業 - 其他類報表 - 批次核定函
    public static final String OTHER_RPT_05_FILE_LIST = "OtherRpt05FileList";
    // 列印作業 - 其他類報表 - 補送在學通知函
    public static final String MONTHLY_RPT_29_FILE_LIST = "MonthlyRpt29FileList";
    // 列印作業 - 其他類報表 - 查核失能程度通知函
    public static final String MONTHLY_RPT_30_FILE_LIST = "MonthlyRpt30FileList";
    // 列印作業 - 其他類報表 - 老年差額金通知
    public static final String MONTHLY_RPT_31_FILE_LIST = "MonthlyRpt31FileList";
    // 列印作業 - 其他類報表 - 老年差額金通知補印作業
    public static final String OTHER_RPT_11_FILE_LIST = "OtherRpt11FileList";
    // 列印作業 - 其他類報表 - 批次老年差額金通知列印作業
    public static final String OTHER_RPT_12_FILE_LIST = "OtherRpt12FileList";
    // ]

    // Case
    // [
    // 維護作業 - 先抽對象維護作業
    public static final String PREVIEW_CONDITION_MAINT_CASE = "PreviewConditionMaintCase";
    // 查詢作業 - 更正日誌查詢
    public static final String UPDATE_LOG_QUERY_CASE = "UpdateLogQueryCase";
    // 查詢作業 - 更正日誌查詢 (外單位用)
    public static final String SIMPLIFY_UPDATE_LOG_QUERY_CASE = "SimplifyUpdateLogQueryCase";
    // 查詢作業 - 行政支援查詢
    public static final String EXECUTIVE_SUPPORT_QUERY_CASE = "ExecutiveSupportQueryCase";
    // 查詢作業 - 行政支援查詢
    public static final String EXECUTIVE_SUPPORT_QUERY_DATA_CASE = "ExecutiveSupportQueryDataCase";
    // 更正作業 - 受款人資料更正
    public static final String PAYEE_DATA_UPDATE_QUERY_CASE = "PayeeDataUpdateQueryCase";
    // 更正作業 - 受款人資料更正
    public static final String PAYEE_DATA_UPDATE_QUERY_CASE_LIST = "PayeeDataUpdateQueryCaseList";
    // 更正作業 - 受款人資料更正
    public static final String PAYEE_DATA_UPDATE_QUERY_CASE_Q1 = "PayeeDataUpdateQueryCaseQ1";
    // 更正作業 - 受款人資料更正
    public static final String PAYEE_DATA_UPDATE_QUERY_CASE_Q2 = "PayeeDataUpdateQueryCaseQ2";
    // 更正作業 - 受款人資料更正
    public static final String PAYEE_DATA_UPDATE_QUERY_CASE_ACCSEQNO_CONTROL = "PayeeDataUpdateQueryCaseAccSeqNoControl";
    // 更正作業 - 受款人資料更正
    public static final String PAYEE_DATA_UPDATE_QUERY_CASE_RELATIONQ2 = "PayeeDataUpdateQueryCaseRelationQ2";
    // 更正作業 - 眷屬資料更正
    public static final String DEPENDANT_DATA_UPDATE_QUERY_CASE = "DependantDataUpdateQueryCase";
    // 更正作業 - 眷屬資料更正 - 事故者資料
    public static final String DEPENDANT_EVT_DATA_UPDATE_QUERY_CASE = "DependantEvtDataUpdateQueryCase";

    // 更正作業 - 失能年金受款人資料更正
    public static final String DISABLED_PAYEE_DATA_INHERIT_FROM = "DisabledPayeeDataInheritFrom";
    // 更正作業 - 失能年金受款人資料更正
    public static final String DISABLED_PAYEE_DATA_UPDATE_QUERY_CASE = "DisabledPayeeDataUpdateQueryCase";
    // 更正作業 - 失能年金受款人資料更正
    public static final String DISABLED_PAYEE_DATA_UPDATE_QUERY_CASE_Q1 = "DisabledPayeeDataUpdateQueryCaseQ1";
    // 更正作業 - 失能年金受款人資料更正
    public static final String DISABLED_PAYEE_DATA_UPDATE_QUERY_CASE_Q2 = "DisabledPayeeDataUpdateQueryCaseQ2";
    // 更正作業 - 失能年金受款人資料更正
    public static final String DISABLED_PAYEE_DATA_UPDATE_QUERY_CASE_ACCSEQNO_CONTROL = "DisabledPayeeDataUpdateQueryCaseAccSeqNoControl";
    // 更正作業 - 失能年金受款人資料更正
    public static final String DISABLED_PAYEE_DATA_UPDATE_QUERY_CASE_RELATIONQ2 = "DisabledPayeeDataUpdateQueryCaseRelationQ2";
    // 更正作業 - 失能年金受款人資料更正
    public static final String DISABLED_DEPENDANT_DATA_UPDATE_QUERY_CASE = "DisabledDependantDataUpdateQueryCase";

    // 更正作業 - 遺屬年金受款人資料更正
    public static final String SURVIVOR_PAYEE_DATA_INHERIT_FROM = "SurvivorPayeeDataInheritFrom";
    // 更正作業 - 遺屬年金受款人資料更正
    public static final String SURVIVOR_PAYEE_DATA_UPDATE_QUERY_CASE = "SurvivorPayeeDataUpdateQueryCase";
    // 更正作業 - 遺屬年金受款人資料更正
    public static final String SURVIVOR_PAYEE_EVT_DATA_UPDATE_QUERY_CASE = "SurvivorPayeeEvtDataUpdateQueryCase";
    // 更正作業 - 遺屬年金受款人資料更正
    public static final String SURVIVOR_PAYEE_DATA_UPDATE_QUERY_CASE_Q1 = "SurvivorPayeeDataUpdateQueryCaseQ1";
    // 更正作業 - 遺屬年金受款人資料更正
    public static final String SURVIVOR_PAYEE_DATA_UPDATE_QUERY_CASE_Q2 = "SurvivorPayeeDataUpdateQueryCaseQ2";
    // 更正作業 - 遺屬年金受款人資料更正
    public static final String SURVIVOR_PAYEE_DATA_UPDATE_QUERY_CASE_ACCSEQNO_CONTROL = "SurvivorPayeeDataUpdateQueryCaseAccSeqNoControl";
    // 更正作業 - 遺屬年金受款人資料更正
    public static final String SURVIVOR_PAYEE_DATA_UPDATE_QUERY_CASE_RELATIONQ2 = "SurvivorPayeeDataUpdateQueryCaseRelationQ2";
    // 更正作業 - 遺屬年金受款人資料更正 - 得請領起月
    public static final String SURVIVOR_PAYEE_DATA_UPDATE_ABLEAPSYM_OPTION_LIST = "ABLEAPSYM_OPTION_LIST";

    // 行政支援作業 - 行政支援記錄維護
    public static final String EXECUTIVE_SUPPORT_MAINT_CASE = "ExecutiveSupportMaintCase";
    // 行政支援作業 - 行政支援記錄維護
    public static final String EXECUTIVE_SUPPORT_MAINT_LIST_CASE = "ExecutiveSupportMaintListCase";
    // 行政支援作業 - 行政支援記錄維護
    public static final String EXECUTIVE_SUPPORT_MAINT_DETAIL_CASE = "ExecutiveSupportMaintDetailCase";
    // 行政支援作業 - 行政支援記錄銷案
    public static final String EXECUTIVE_SUPPORT_CLOSE_CASE = "ExecutiveSupportCloseCase";
    // 行政支援作業 - 行政支援記錄銷案
    public static final String EXECUTIVE_SUPPORT_CLOSE_LIST_CASE = "ExecutiveSupportCloseListCase";
    // 維護作業 - 編審註記維護作業
    public static final String CHECK_MARK_MAINT_QUERY_CASE = "CheckMarkMaintQueryCase";
    // 維護作業 - 核定通知書維護作業
    public static final String ADVICE_MAINT_QUERY_CASE = "AdviceMaintQueryCase";
    // 維護作業 - 核定通知書維護作業
    public static final String ADVICE_MAINT_DATA_CONT_CASE = "AdviceMaintDataContCase";
    // 維護作業 - 核定通知書維護作業
    public static final String BALETTER_CODE_CASE_LIST = "BaletterCodeCaseList";
    // 維護作業 - 決行層級維護作業
    public static final String DECISION_LEVEL_MAINT_QUERY_CASE = "DecisionLevelMaintQueryCase";
    // 維護作業 - 物價指數調整明細維護作業
    public static final String CPI_DTL_MAINT_QUERY_CASE = "CpiDtlMaintQueryCase";
    // 維護作業 - 物價指數調整明細維護作業
    public static final String CPI_DTL_ADJYEAR_AMOUNT_CASE_LIST = "CpiDtlAdjYearAmountCaseList";
    // 維護作業 - 物價指數調整明細維護作業
    public static final String CPI_DTL_ADJYEAR_CASE_LIST = "CpiDtlAdjYearCaseList";
    // 維護作業 - 物價指數調整明細維護作業
    public static final String CPI_DTL_OLD_ADJYEAR_CASE = "CpiDtlOldAdjYearCase";
    // 維護作業 - 物價指數調整明細維護作業
    public static final String CPI_DTL_USER_DETAIL_CASE_LIST = "CpiDtlUserDetailCaseList";
    // 維護作業 - 物價指數調整紀錄維護作業
    public static final String CPI_REC_MAINT_QUERY_CASE = "CpiRecMaintQueryCase";
    // 維護作業 - 物價指數調整紀錄維護作業
    public static final String CPI_REC_MAINT_DETAIL_CASE_LIST = "CpiRecMaintDetailCaseList";
    // 維護作業 - 老年年金加計金額調整作業
    public static final String BASIC_AMT_MAINT_QUERY_CASE = "BasicAmtMaintQueryCase";
    // 維護作業 - 勞保年金平均薪資月數參數維護修改作業
    public static final String AVG_AMT_MON_MAINT_QUERY_CASE_LIST = "AvgAmtMonMaintQueryCaseList";
    // 維護作業 - 勞保年金平均薪資月數參數維護修改作業
    public static final String AVG_AMT_MON_MAINT_QUERY_CASE = "AvgAmtMonMaintQueryCase";
    // 維護作業 - 老年年金所得替代率參數維護作業
    public static final String REPLACEMENT_RATIO_MAINT_QUERY_CASE_LIST = "ReplacementRatioMaintQueryCaseList";
    // 維護作業 - 老年年金所得替代率參數維護作業
    public static final String REPLACEMENT_RATIO_MAINT_QUERY_CASE = "ReplacementRatioMaintQueryCase";
    // 維護作業 - 失能年金所得替代率參數維護作業
    public static final String DISABLED_REPLACEMENT_RATIO_MAINT_QUERY_CASE_LIST = "DisabledReplacementRatioMaintQueryCaseList";
    // 維護作業 - 失能年金所得替代率參數維護作業
    public static final String DISABLED_REPLACEMENT_RATIO_MAINT_QUERY_CASE = "DisabledReplacementRatioMaintQueryCase";
    // 維護作業 - 遺屬年金所得替代率參數維護作業
    public static final String SURVIVOR_REPLACEMENT_RATIO_MAINT_QUERY_CASE_LIST = "SurvivorReplacementRatioMaintQueryCaseList";
    // 維護作業 - 遺屬年金所得替代率參數維護作業
    public static final String SURVIVOR_REPLACEMENT_RATIO_MAINT_QUERY_CASE = "SurvivorReplacementRatioMaintQueryCase";
    // 維護作業 - 重設案件給付參數資料
    public static final String RESET_PAYMENT_PARAMETER_CASE = "ResetPaymentParameterCase";

    // 查詢作業 - 編審註記查詢作業
    public static final String CHECK_MARK_LEVEL_QUERY_CASE = "CheckMarkLevelQueryCase";
    // 更正作業 - 給付主檔更正
    public static final String BAAPPBASE_DATA_UPDATE_CASE = "BaappbaseDataUpdateCase";
    // 更正作業 - 止付處理作業
    public static final String STOP_PAYMENT_PROCESS_QUERY_CASE = "StopPaymentProcessQueryCase";
    // 更正作業 - 繼承人資料更正
    public static final String INHERITOR_REGISTER_CASE = "InheritorRegisterCase";
    // 更正作業 - 編審註記程度調整
    public static final String CHECK_MARK_LEVEL_ADJUST_CASE = "CheckMarkLevelAdjustCase";
    // 更正作業 - 編審註記程度調整 - 失能年金編審註記程度調整 (bamo0d160c.jsp bamo0d161c.jsp)
    public static final String DISABLED_CHECK_MARK_LEVEL_ADJUST_CASE = "DisabledCheckMarkLevelAdjustCase";
    // 更正作業 - 編審註記程度調整 - 遺屬年金編審註記程度調整 (bamo0d260c.jsp bamo0d261c.jsp)
    public static final String SURVIVOR_CHECK_MARK_LEVEL_ADJUST_CASE = "SurvivorCheckMarkLevelAdjustCase";
    // 更正作業 - 案件資料更正 - 失能年金案件資料更正 (bamo0d130c.jsp bamo0d131c.jsp bamo0d133c.jsp)
    public static final String DISABLED_APPLICATION_DATA_UPDATE_CASE = "DisabledApplicationDataUpdateCase";
    // 更正作業 - 案件資料更正 - 失能年金案件資料更正 (bamo0d131c.jsp bamo0d136c.jsp)
    public static final String DISABLED_APPLICATION_DATA_UPDATE_BARE_CHECK_CASE_LIST = "DisabledApplicationDataUpdateBareCheckCaseList";
    // 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 (bamo0d230c.jsp bamo0d231c.jsp bamo0d233c.jsp)
    public static final String SURVIVOR_APPLICATION_DATA_UPDATE_CASE = "SurvivorApplicationDataUpdateCase";
    // 更正作業 - 受款人資料更正 - 查詢結果清單
    public static final String PAYEE_DATA_UPDATE_DETAIL_CASE = "PayeeDataUpdateDetailCase";
    // 更正作業 - 受款人資料更正 - 查詢結果清單
    public static final String PAYEE_DATA_UPDATE_BADAPR_CASE = "PayeeDataUpdateBadaprCase";
    // 更正作業 - 失能年金受款人資料更正 - 查詢結果清單
    public static final String DISABLED_PAYEE_DATA_UPDATE_DETAIL_CASE = "DisabledPayeeDataUpdateDetailCase";
    // 更正作業 - 失能年金受款人資料更正 - 查詢結果清單
    public static final String DISABLED_PAYEE_DATA_UPDATE_BADAPR_CASE = "DisabledPayeeDataUpdateBadaprCase";
    // 更正作業 - 遺屬年金受款人資料更正 - 查詢結果清單
    public static final String SURVIVOR_PAYEE_DATA_UPDATE_DETAIL_CASE = "SurvivorPayeeDataUpdateDetailCase";
    // 更正作業 - 遺屬年金受款人資料更正 - 查詢結果清單
    public static final String SURVIVOR_PAYEE_DATA_UPDATE_DETAIL_BAAPPEXPAND_CASE = "SurvivorPayeeDataUpdateDetailBaappexpandCase";
    // 更正作業 - 遺屬年金受款人資料更正 - 查詢結果清單
    public static final String SURVIVOR_PAYEE_DATA_UPDATE_BADAPR_CASE = "SurvivorPayeeDataUpdateBadaprCase";
    // 更正作業 - 遺屬年金受款人資料更正 - 查詢結果清單
    public static final String SURVIVOR_PAYEE_DATA_UPDATE_BADAPR_LIST_CASE = "SurvivorPayeeDataUpdateBadaprListCase";
    // 更正作業 - 遺屬年金受款人資料更正 - 在學期間查詢結果清單
    public static final String SURVIVOR_PAYEE_DATA_UPDATE_STUDTERM_LIST_CASE = "SurvivorPayeeDataUpdateStudtermListCase";
    // 更正作業 - 遺屬年金受款人資料更正 - 重殘起訖年月查詢結果清單
    public static final String SURVIVOR_PAYEE_DATA_UPDATE_HANDICAPTERM_LIST_CASE = "SurvivorPayeeDataUpdateHandicaptermListCase";
    // 更正作業 - 遺屬年金受款人資料更正 - 不合格年月查詢結果清單
    public static final String SURVIVOR_PAYEE_DATA_UPDATE_COMPELDATA_LIST_CASE = "SurvivorPayeeDataUpdateCompelDataListCase";
    // 更正作業 - 眷屬資料更正 - 查詢結果清單
    public static final String DEPENDANT_DATA_UPDATE_DETAIL_CASE = "DependantDataUpdateDetailCase";
    // 更正作業 - 眷屬資料更正 - 眷屬編審註記
    public static final String DEPENDANT_QUERY_BEN_CHKFILE_DATA_CASE = "DependantQueryBenChkFileDataCase";
    // 更正作業 - 眷屬資料更正 - 符合註記
    public static final String DEPENDANT_QUERY_MATCH_CHKFILE_DATA_CASE = "DependantQueryMatchChkFileDataCase";
    // 更正作業 - 眷屬資料更正 - 在學期間查詢結果清單
    public static final String DEPENDANT_DATA_UPDATE_STUDTERM_LIST_CASE = "DependantDataUpdateStudtermListCase";
    // 更正作業 - 眷屬資料更正 - 不合格年月查詢結果清單
    public static final String DEPENDANT_DATA_UPDATE_COMPELDATA_LIST_CASE = "DependantDataUpdateCompelDataListCase";
    // 更正作業 - 死亡改匯處理作業 - 檢核受款人資料是否符合可改匯處理的資格 (QryData_1)
    public static final String CHECK_OLDAGE_DEATH_REPAY_DATA_CASE = "CheckOldAgeDeathRepayDataCase";
    // 更正作業 - 死亡改匯處理作業 - 檢核是否符合資料，是否有退匯資料 (QryData_2)
    public static final String OLDAGE_DEATH_REPAY_DATA_CASE_LIST = "OldAgeDeathRepayDataCaseList";
    // 更正作業 - 死亡改匯處理作業 - 檢核受款人的繼承人資料是否存在 (QryData_3)
    public static final String HEIR_SEQNO_EXIST_DATA_CASE_LIST = "HeirSeqNoExistDataCaseList";
    // 更正作業 - 死亡改匯處理作業 - 檢核受款人的繼承人資料是否存在 (QryData_3)
    public static final String REPAY_ISSUEAMT_DATA_CASE_LIST = "RepayIssueAmtDataCaseList";
    // 更正作業 - 應收收回處理作業 - 老年年金應收收回處理作業 - 收回狀況資料
    public static final String OLDAGE_PAYMENT_RECEIVE_DATA_CASE = "OldAgePaymentReceiveDataCase";
    // 更正作業 - 應收收回處理作業 - 老年年金應收收回處理作業 - 應收資料List
    public static final String ACCOUNTS_RECEIVABLE_DATA_CASE_LIST = "AccountsReceivableDataCaseList";
    // 更正作業 - 應收收回處理作業 - 老年年金應收收回處理作業 - 退現資料
    public static final String CASH_RECEIVE_DATA_CASE_LIST = "CashReceiveDataCaseList";
    // 更正作業 - 應收收回處理作業 - 老年年金應收收回處理作業 - 退匯資料
    public static final String REMITTANCE_RECEIVE_DATA_CASE_LIST = "RemittanceReceiveDataCaseList";
    // 更正作業 - 應收收回處理作業 - 失能年金應收收回處理作業 - 收回狀況資料
    public static final String DISABLED_PAYMENT_RECEIVE_DATA_CASE = "DisabledPaymentReceiveDataCase";
    // 更正作業 - 應收收回處理作業 - 失能年金應收收回處理作業 - 應收資料List
    public static final String DISABLED_ACCOUNTS_RECEIVABLE_DATA_CASE_LIST = "DisabledAccountsReceivableDataCaseList";
    // 更正作業 - 應收收回處理作業 - 失能年金應收收回處理作業 - 退現資料
    public static final String DISABLED_CASH_RECEIVE_DATA_CASE_LIST = "DisabledCashReceiveDataCaseList";
    // 更正作業 - 應收收回處理作業 - 失能年金應收收回處理作業 - 退匯資料
    public static final String DISABLED_REMITTANCE_RECEIVE_DATA_CASE_LIST = "DisabledRemittanceReceiveDataCaseList";
    // 更正作業 - 應收收回處理作業 - 遺屬年金應收收回處理作業 - 收回狀況資料
    public static final String SURVIVOR_PAYMENT_RECEIVE_DATA_CASE = "SurvivorPaymentReceiveDataCase";
    // 更正作業 - 應收收回處理作業 - 遺屬年金應收收回處理作業 - 應收資料List
    public static final String SURVIVOR_ACCOUNTS_RECEIVABLE_DATA_CASE_LIST = "SurvivorAccountsReceivableDataCaseList";
    // 更正作業 - 應收收回處理作業 - 遺屬年金應收收回處理作業 - 退現資料
    public static final String SURVIVOR_CASH_RECEIVE_DATA_CASE_LIST = "SurvivorCashReceiveDataCaseList";
    // 更正作業 - 應收收回處理作業 - 遺屬年金應收收回處理作業 - 退匯資料
    public static final String SURVIVOR_REMITTANCE_RECEIVE_DATA_CASE_LIST = "SurvivorRemittanceReceiveDataCaseList";
    // 更正作業 - 結案狀態變更作業
    public static final String CLOSE_STATUS_ALTERATION_CASE = "CloseStatusAlterationCase";
    // 更正作業 - 結案狀態變更作業
    public static final String CLOSE_STATUS_ALTERATION_CASE_PAYEE_LIST = "CloseStatusAlterationCasePayeeList";

    // 審核作業 - 老年年金給付審核作業
    public static final String PAYMENT_REVIEW_CASE = "PaymentReviewCase";
    // 審核作業 - 老年年金給付審核作業 - 表頭
    public static final String PAYMENT_REVIEW_CASE_FOR_TITLE = "PaymentReviewCaseForTitle";
    // 審核作業 - 老年年金給付審核作業 - 一次給付資料 查詢結果
    public static final String PAYMENT_REVIEW_ONCEPAY_DATA_CASE = "PaymentReviewOncePayDataCase";
    // 審核作業 - 老年年金給付審核作業 - 核定通知書 查詢結果
    public static final String PAYMENT_REVIEW_NOTIFY_DATA_CASE = "PaymentReviewNotifyDataCase";
    // 審核作業 - 老年年金給付審核作業 - 請領同類／他類／另案扣減 - 一次給付資料 查詢結果
    public static final String PAYMENT_REVIEW_ONCEPAY_DETAIL_DATA_CASE = "PaymentReviewOncePayDetailDataCase";
    // 審核作業 - 老年年金給付審核作業 - 請領同類／他類／另案扣減 - 申請死亡給付紀錄 查詢結果
    public static final String PAYMENT_REVIEW_DIEPAY_APPLY_DATA_CASE = "PaymentReviewDiePayApplyDataCase";
    // 審核作業 - 老年年金給付審核作業 - 請領同類／他類／另案扣減 - 申請國保給付紀錄 查詢結果
    public static final String PAYMENT_REVIEW_NPPAY_APPLY_DATA_CASE = "PaymentReviewNpPayApplyDataCase";
    // 審核作業 - 老年年金給付審核作業 - 交查函資料
    public static final String PAYMENT_REVIEW_LETTER_TYPE_1_LIST = "PaymentReviewLetterTypeMk1List";
    // 審核作業 - 老年年金給付審核作業 - 不給付函資料
    public static final String PAYMENT_REVIEW_LETTER_TYPE_2_LIST = "PaymentReviewLetterTypeMk2List";
    // 審核作業 - 老年年金給付審核作業 - 補件通知函資料
    public static final String PAYMENT_REVIEW_LETTER_TYPE_3_LIST = "PaymentReviewLetterTypeMk3List";
    // 審核作業 - 老年年金給付審核作業 - 照會函資料
    public static final String PAYMENT_REVIEW_LETTER_TYPE_4_LIST = "PaymentReviewLetterTypeMk4List";
    // 審核作業 - 老年年金給付審核作業 - 其他簽函資料
    public static final String PAYMENT_REVIEW_LETTER_TYPE_5_LIST = "PaymentReviewLetterTypeMk5List";
    // 審核作業 - 老年年金給付審核作業 - 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料
    public static final String PAYMENT_REVIEW_LETTER_TYPE_6_LIST = "PaymentReviewLetterTypeMk6List";
    // 審核作業 - 失能年金給付審核作業
    public static final String DISABLED_PAYMENT_REVIEW_CASE = "DisabledPaymentReviewCase";
    // 審核作業 - 失能年金給付審核作業 - 表頭
    public static final String DISABLED_PAYMENT_REVIEW_CASE_FOR_TITLE = "DisabledPaymentReviewCaseForTitle";
    // 審核作業 - 失能年金給付審核作業 - 交查函資料
    public static final String DISABLED_PAYMENT_REVIEW_LETTER_TYPE_1_LIST = "DisabledPaymentReviewLetterTypeMk1List";
    // 審核作業 - 失能年金給付審核作業 - 不給付函資料
    public static final String DISABLED_PAYMENT_REVIEW_LETTER_TYPE_2_LIST = "DisabledPaymentReviewLetterTypeMk2List";
    // 審核作業 - 失能年金給付審核作業 - 補件通知函資料
    public static final String DISABLED_PAYMENT_REVIEW_LETTER_TYPE_3_LIST = "DisabledPaymentReviewLetterTypeMk3List";
    // 審核作業 - 失能年金給付審核作業 - 照會函資料
    public static final String DISABLED_PAYMENT_REVIEW_LETTER_TYPE_4_LIST = "DisabledPaymentReviewLetterTypeMk4List";
    // 審核作業 - 失能年金給付審核作業 - 其他簽函資料
    public static final String DISABLED_PAYMENT_REVIEW_LETTER_TYPE_5_LIST = "DisabledPaymentReviewLetterTypeMk5List";
    // 審核作業 - 失能年金給付審核作業 - 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料
    public static final String DISABLED_PAYMENT_REVIEW_LETTER_TYPE_6_LIST = "DisabledPaymentReviewLetterTypeMk6List";
    // 審核作業 - 失能年金給付審核作業 - 職災相關資料
    public static final String DISABLED_PAYMENT_REVIEW_OCCACC_DATA = "DisabledPaymentReviewOccAccData";
    // 審核作業 - 失能年金給付審核作業 - 失能相關資料
    public static final String DISABLED_PAYMENT_REVIEW_DISABLED_DATA = "DisabledPaymentReviewDisabledData";
    // 審核作業 - 遺屬年金給付審核作業
    public static final String SURVIVOR_PAYMENT_REVIEW_CASE = "SurvivorPaymentReviewCase";
    // 審核作業 - 遺屬年金給付審核作業 - 表頭
    public static final String SURVIVOR_PAYMENT_REVIEW_CASE_FOR_TITLE = "SurvivorPaymentReviewCaseForTitle";
    // 審核作業 - 遺屬年金給付審核作業 - 交查函資料
    public static final String SURVIVOR_PAYMENT_REVIEW_LETTER_TYPE_1_LIST = "SurvivorPaymentReviewLetterTypeMk1List";
    // 審核作業 - 遺屬年金給付審核作業 - 不給付函資料
    public static final String SURVIVOR_PAYMENT_REVIEW_LETTER_TYPE_2_LIST = "SurvivorPaymentReviewLetterTypeMk2List";
    // 審核作業 - 遺屬年金給付審核作業 - 補件通知函資料
    public static final String SURVIVOR_PAYMENT_REVIEW_LETTER_TYPE_3_LIST = "SurvivorPaymentReviewLetterTypeMk3List";
    // 審核作業 - 遺屬年金給付審核作業 - 照會函資料
    public static final String SURVIVOR_PAYMENT_REVIEW_LETTER_TYPE_4_LIST = "SurvivorPaymentReviewLetterTypeMk4List";
    // 審核作業 - 遺屬年金給付審核作業 - 其他簽函資料
    public static final String SURVIVOR_PAYMENT_REVIEW_LETTER_TYPE_5_LIST = "SurvivorPaymentReviewLetterTypeMk5List";
    // 審核作業 - 遺屬年金給付審核作業 - 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料
    public static final String SURVIVOR_PAYMENT_REVIEW_LETTER_TYPE_6_LIST = "SurvivorPaymentReviewLetterTypeMk6List";
    // 審核作業 - 遺屬年金給付審核作業 - 職災相關資料
    public static final String SURVIVOR_PAYMENT_REVIEW_OCCACC_DATA = "SurvivorPaymentReviewOccAccData";
    // 審核作業 - 遺屬年金給付審核作業 - 遺屬相關資料
    public static final String SURVIVOR_PAYMENT_REVIEW_DISABLED_DATA = "SurvivorPaymentReviewDisabledData";

    // 決行作業 - 給付決行作業
    public static final String PAYMENT_DECISION_CASE = "PaymentDecisionCase";
    // 決行作業 - 給付決行作業 - 表頭
    public static final String PAYMENT_DECISION_CASE_FOR_TITLE = "PaymentDecisionCaseForTitle";
    // 決行作業 - 給付決行作業 - 一次給付資料 查詢結果
    public static final String PAYMENT_DECISION_ONCEPAY_DATA_CASE = "PaymentDecisionOncePayDataCase";
    // 決行作業 - 給付決行作業 - 核定通知書 查詢結果
    public static final String PAYMENT_DECISION_NOTIFY_DATA_CASE = "PaymentDecisionNotifyDataCase";
    // 決行作業 - 給付決行作業 - 請領同類／他類／另案扣減 - 一次給付資料 查詢結果
    public static final String PAYMENT_DECISION_ONCEPAY_DETAIL_DATA_CASE = "PaymentDecisionOncePayDetailDataCase";
    // 決行作業 - 給付決行作業 - 請領同類／他類／另案扣減 - 申請死亡給付紀錄 查詢結果
    public static final String PAYMENT_DECISION_DIEPAY_APPLY_DATA_CASE = "PaymentDecisionDiePayApplyDataCase";
    // 決行作業 - 給付決行作業 - 請領同類／他類／另案扣減 - 申請國保給付紀錄 查詢結果
    public static final String PAYMENT_DECISION_NPPAY_APPLY_DATA_CASE = "PaymentDecisionNpPayApplyDataCase";
    // 決行作業 - 給付決行作業作業 - 交查函資料
    public static final String PAYMENT_DECISION_LETTER_TYPE_1_LIST = "PaymentDecisionLetterTypeMk1List";
    // 決行作業 - 給付決行作業作業 - 不給付函資料
    public static final String PAYMENT_DECISION_LETTER_TYPE_2_LIST = "PaymentDecisionLetterTypeMk2List";
    // 決行作業 - 給付決行作業作業 - 補件通知函資料
    public static final String PAYMENT_DECISION_LETTER_TYPE_3_LIST = "PaymentDecisionLetterTypeMk3List";
    // 決行作業 - 給付決行作業作業 - 照會函資料
    public static final String PAYMENT_DECISION_LETTER_TYPE_4_LIST = "PaymentDecisionLetterTypeMk4List";
    // 決行作業 - 給付決行作業作業 - 其他簽函資料
    public static final String PAYMENT_DECISION_LETTER_TYPE_5_LIST = "PaymentDecisionLetterTypeMk5List";
    // 決行作業 - 給付決行作業作業 - 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料
    public static final String PAYMENT_DECISION_LETTER_TYPE_6_LIST = "PaymentDecisionLetterTypeMk6List";
    // 決行作業 - 失能年金給付決行作業
    public static final String DISABLED_PAYMENT_DECISION_CASE = "DisabledPaymentDecisionCase";
    // 決行作業 - 失能年金給付決行作業 - 表頭
    public static final String DISABLED_PAYMENT_DECISION_CASE_FOR_TITLE = "DisabledPaymentDecisionCaseForTitle";
    // 決行作業 - 失能年金給付審核作業 - 職災相關資料
    public static final String DISABLED_PAYMENT_DECISION_OCCACC_DATA = "DisabledPaymentDecisionOccAccData";
    // 決行作業 - 失能年金給付審核作業 - 失能相關資料
    public static final String DISABLED_PAYMENT_DECISION_DISABLED_DATA = "DisabledPaymentDecisionDisabledData";
    // 決行作業 - 失能年金給付決行作業作業 - 交查函資料
    public static final String DISABLED_PAYMENT_DECISION_LETTER_TYPE_1_LIST = "DisabledPaymentDecisionLetterTypeMk1List";
    // 決行作業 - 失能年金給付決行作業作業 - 不給付函資料
    public static final String DISABLED_PAYMENT_DECISION_LETTER_TYPE_2_LIST = "DisabledPaymentDecisionLetterTypeMk2List";
    // 決行作業 - 失能年金給付決行作業作業 - 補件通知函資料
    public static final String DISABLED_PAYMENT_DECISION_LETTER_TYPE_3_LIST = "DisabledPaymentDecisionLetterTypeMk3List";
    // 決行作業 - 失能年金給付決行作業作業 - 照會函資料
    public static final String DISABLED_PAYMENT_DECISION_LETTER_TYPE_4_LIST = "DisabledPaymentDecisionLetterTypeMk4List";
    // 決行作業 - 失能年金給付決行作業作業 - 其他簽函資料
    public static final String DISABLED_PAYMENT_DECISION_LETTER_TYPE_5_LIST = "DisabledPaymentDecisionLetterTypeMk5List";
    // 決行作業 - 失能年金給付決行作業作業 - 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料
    public static final String DISABLED_PAYMENT_DECISION_LETTER_TYPE_6_LIST = "DisabledPaymentDecisionLetterTypeMk6List";
    // 決行作業 - 遺屬年金給付決行作業
    public static final String SURVIVOR_PAYMENT_DECISION_CASE = "SurvivorPaymentDecisionCase";
    // 決行作業 - 遺屬年金給付決行作業 - 表頭
    public static final String SURVIVOR_PAYMENT_DECISION_CASE_FOR_TITLE = "SurvivorPaymentDecisionCaseForTitle";
    // 決行作業 - 遺屬年金給付審核作業 - 職災相關資料
    public static final String SURVIVOR_PAYMENT_DECISION_OCCACC_DATA = "SurvivorPaymentDecisionOccAccData";
    // 決行作業 - 遺屬年金給付審核作業 - 失能相關資料
    public static final String SURVIVOR_PAYMENT_DECISION_DISABLED_DATA = "SurvivorPaymentDecisionDisabledData";
    // 決行作業 - 遺屬年金給付決行作業作業 - 交查函資料
    public static final String SURVIVOR_PAYMENT_DECISION_LETTER_TYPE_1_LIST = "SurvivorPaymentDecisionLetterTypeMk1List";
    // 決行作業 - 遺屬年金給付決行作業作業 - 不給付函資料
    public static final String SURVIVOR_PAYMENT_DECISION_LETTER_TYPE_2_LIST = "SurvivorPaymentDecisionLetterTypeMk2List";
    // 決行作業 - 遺屬年金給付決行作業作業 - 補件通知函資料
    public static final String SURVIVOR_PAYMENT_DECISION_LETTER_TYPE_3_LIST = "SurvivorPaymentDecisionLetterTypeMk3List";
    // 決行作業 - 遺屬年金給付決行作業作業 - 照會函資料
    public static final String SURVIVOR_PAYMENT_DECISION_LETTER_TYPE_4_LIST = "SurvivorPaymentDecisionLetterTypeMk4List";
    // 決行作業 - 遺屬年金給付決行作業作業 - 其他簽函資料
    public static final String SURVIVOR_PAYMENT_DECISION_LETTER_TYPE_5_LIST = "SurvivorPaymentDecisionLetterTypeMk5List";
    // 決行作業 - 遺屬年金給付決行作業作業 - 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料
    public static final String SURVIVOR_PAYMENT_DECISION_LETTER_TYPE_6_LIST = "SurvivorPaymentDecisionLetterTypeMk6List";

    // 查詢作業 - 給付查詢作業 - 明細資料
    public static final String PAYMENT_QUERY_DETAIL_DATA_CASE = "PaymentQueryDetailDataCase";
    // 查詢作業 - 給付查詢作業 - 核定年月起迄資料
    public static final String PAYMENT_QUERY_ISSU_PAY_DATA_CASE = "PaymentQueryIssuPayDataCase";
    // 查詢作業 - 給付查詢作業 - 核定年月起迄資料 (原始查詢結果)
    public static final String PAYMENT_QUERY_ORIG_ISSU_PAY_DATA_CASE = "PaymentQueryOrigIssuPayDataCase";
    // 查詢作業 - 給付查詢作業 - 核定年月起迄資料明細
    public static final String PAYMENT_QUERY_ISSU_PAY_DETAIL_CASE = "PaymentQueryIssuPayDetailCase";
    // 查詢作業 - 給付查詢作業 - 應收已收資料
    public static final String PAYMENT_QUERY_ALREADY_RECEIVE_DATA_CASE = "PaymentQueryAlreadyReceiveDataCase";
    // 查詢作業 - 給付查詢作業 - 受款人資料(含核定/編審資料)
    public static final String PAYMENT_QUERY_BEN_DATA_CASE_LIST = "PaymentQueryBenDataCaseList";
    // 查詢作業 - 給付查詢作業 - 受款人在學資料
    public static final String PAYMENT_QUERY_STUD_DETAIL_DATA_CASE_LIST = "PaymentQueryStudDetailDataCaseList";
    // 查詢作業 - 給付查詢作業 - 受款人在學資料 for 失能年金
    public static final String PAYMENT_QUERY_DISABLED_STUD_DETAIL_DATA_CASE_LIST = "PaymentQueryDisabledStudDetailDataCaseList";
    // 查詢作業 - 給付查詢作業 - 受款人重殘資料
    public static final String PAYMENT_QUERY_HANDICAP_DETAIL_DATA_CASE_LIST = "PaymentQueryHandicapDetailDataCaseList";
    // 查詢作業 - 給付查詢作業 - 年資資料(含欠費期間/承保異動資料)
    public static final String PAYMENT_QUERY_SENI_DATA_CASE = "PaymentQuerySeniDataCase";
    // 查詢作業 - 給付查詢作業 - 一次給付資料
    public static final String PAYMENT_QUERY_ONCEPAY_DATA_CASE = "PaymentQueryOncePayDataCase";
    // 查詢作業 - 給付查詢作業 - 一次給付更正資料
    public static final String PAYMENT_QUERY_ONCEPAY_MODIFY_DATA_CASE = "PaymentQueryBenDataCase";
    // 查詢作業 - 給付查詢作業 - 請領同類／他類／另案扣減 - 申請失能給付紀錄 查詢結果清單
    public static final String PAYMENT_QUERY_CRIPAY_APPLY_DATA_CASE_LIST = "PaymentReviewCriPayApplyDataCaseList";
    // 查詢作業 - 給付查詢作業 - 請領同類／他類／另案扣減 - 申請傷病給付紀錄 查詢結果清單
    public static final String PAYMENT_QUERY_INJPAY_APPLY_DATA_CASE_LIST = "PaymentReviewInjPayApplyDataCaseList";
    // 查詢作業 - 給付查詢作業 - 請領同類／他類／另案扣減 - 申請失業給付紀錄 查詢結果清單
    public static final String PAYMENT_QUERY_UNEMPPAY_APPLY_DATA_CASE_LIST = "PaymentReviewUnEmpPayApplyDataCaseList";
    // 查詢作業 - 給付查詢作業 - 請領同類／他類／另案扣減 - 年金給付資料 查詢結果清單
    public static final String PAYMENT_QUERY_ANNUITYPAY_DATA_CASE_LIST = "PaymentReviewAnnuityPayDataCaseList";
    // 查詢作業 - 給付查詢作業 - 請領同類／他類／另案扣減 - 一次給付資料 查詢結果
    public static final String PAYMENT_QUERY_ONCEPAY_DETAIL_DATA_CASE = "PaymentReviewOncePayDetailDataCase";
    // 查詢作業 - 給付查詢作業 - 請領同類／他類／另案扣減 - 申請死亡給付紀錄 查詢結果
    public static final String PAYMENT_QUERY_DIEPAY_APPLY_DATA_CASE = "PaymentReviewDiePayApplyDataCase";
    // 查詢作業 - 給付查詢作業 - 請領同類／他類／另案扣減 - 申請國保給付紀錄 查詢結果
    public static final String PAYMENT_QUERY_NPPAY_APPLY_DATA_CASE = "PaymentQueryNpPayApplyDataCase";
    // 查詢作業 - 給付查詢作業 - 交查函資料
    public static final String PAYMENT_QUERY_LETTER_TYPE_1_LIST = "PaymentQueryLetterTypeMk1List";
    // 查詢作業 - 給付查詢作業 - 不給付函資料
    public static final String PAYMENT_QUERY_LETTER_TYPE_2_LIST = "PaymentQueryLetterTypeMk2List";
    // 查詢作業 - 給付查詢作業 - 補件通知函資料
    public static final String PAYMENT_QUERY_LETTER_TYPE_3_LIST = "PaymentQueryLetterTypeMk3List";
    // 查詢作業 - 給付查詢作業 - 照會函資料
    public static final String PAYMENT_QUERY_LETTER_TYPE_4_LIST = "PaymentQueryLetterTypeMk4List";
    // 查詢作業 - 給付查詢作業 - 其他簽函資料
    public static final String PAYMENT_QUERY_LETTER_TYPE_5_LIST = "PaymentQueryLetterTypeMk5List";
    // 查詢作業 - 給付查詢作業 - 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料
    public static final String PAYMENT_QUERY_LETTER_TYPE_6_LIST = "PaymentQueryLetterTypeMk6List";
    // 查詢作業 - 給付查詢作業 - 職災相關資料
    public static final String PAYMENT_QUERY_OCCACC_DATA_CASE = "PaymentQueryOccAccDataCase";
    // 查詢作業 - 給付查詢作業 - 失能相關資料
    public static final String PAYMENT_QUERY_DISABLED_DATA_CASE = "PaymentQueryDisabledDataCase";
    // 查詢作業 - 給付查詢作業 - 眷屬資料
    public static final String PAYMENT_QUERY_FAMILY_DATA_LIST = "PaymentQueryFamilyDataList";
    // 查詢作業 - 給付查詢作業 - 複檢費用資料
    public static final String PAYMENT_QUERY_REFEES_MASTER_DATA_LIST = "PaymentQueryReFeesMasterDataList";
    // 查詢作業 - 給付查詢作業 - 複檢費用明細資料
    public static final String PAYMENT_QUERY_REFEES_DETAIL_DATA_CASE = "PaymentQueryReFeesDetailDataCase";
    // 查詢作業 - 給付查詢作業 - 最新紓困貸款資料
    public static final String PAYMENT_QUERY_LOAN_MASTER_DATA_CASE = "PaymentQueryLoanMasterDataCase";
    // 查詢作業 - 給付查詢作業 - 紓困貸款 核定資料
    public static final String PAYMENT_QUERY_LOAN_DETAIL_DATA_CASE_LIST = "PaymentQueryLoanDetailDataCaseList";
    // 查詢作業 - 給付查詢作業 - 平均薪資 - 年資資料
    public static final String PAYMENT_QUERY_AVGAMT_SENI_DATA_CASE = "PaymentQueryAvgAmtSeniDataCase";
    // 查詢作業 - 給付查詢作業 - 平均薪資 - 60個月平均薪資資料
    public static final String PAYMENT_QUERY_SIXTY_MON_AVGAMT_DATA_CASE_LIST = "PaymentQuerySixtyMonAvgAmtDataCaseList";
    // 查詢作業 - 給付查詢作業 - 物價指數調整
    public static final String PAYMENT_QUERY_CPI_DATA_CASE_LIST = "PaymentQueryCpiDataCaseList";
    // 查詢作業 - 給付查詢作業 - 物價指數調整記錄資料
    public static final String PAYMENT_QUERY_CPIREC_DATA_CASE_LIST = "PaymentQueryCpiRecDataCaseList";
    // 查詢作業 - 給付查詢作業 - 受款人強迫不合格資料
    public static final String PAYMENT_QUERY_COMPEL_NOPAY_DETAIL_DATA_CASE_LIST = "PaymentQueryCompelNopayDetailDataCaseList";
    // 查詢作業 - 給付查詢作業 - 歸檔記錄資料
    public static final String PAYMENT_QUERY_ARCLIST_CASE = "PaymentQueryArclistCase";
    // 查詢作業 - 給付查詢作業 - 核定通知記錄資料
    public static final String PAYMENT_QUERY_UNQUALIFIED_NOTICE_CASE = "PaymentQueryUnqualifiedNoticeCase";
    // 查詢作業 - 給付查詢作業 - 國保資料
    public static final String PAYMENT_QUERY_NP_DATA_CASE = "PaymentQueryNpDataCase";
    // 查詢作業 - 給付查詢作業 - 國保核定資料
    public static final String PAYMENT_QUERY_NP_ISSU_DATA_LIST = "PaymentQueryNpIssuDataCase";
    // 查詢作業 - 給付查詢作業 - 國保核定資料(原始)
    public static final String PAYMENT_QUERY_ORIG_NP_ISSU_DATA_LIST = "PaymentQueryOrigNpIssuDataCase";

    // 查詢作業 - 年金統計查詢 - 本國籍(系統認定)
    public static final String CIPBFMK_NON = "本國籍(系統認定)";
    // 查詢作業 - 年金統計查詢 - 外國籍(人工認定或單位自行申報)
    public static final String CIPBFMK_F = "外國籍(人工認定或單位自行申報)";
    // 查詢作業 - 年金統計查詢 - 外國籍(系統認定)
    public static final String CIPBFMK_Y = "外國籍(系統認定)";
    // 查詢作業 - 年金統計查詢 - 本國籍(人工認定)
    public static final String CIPBFMK_N = "本國籍(人工認定)";
    // 查詢作業 - 年金統計查詢 - 外籍配偶
    public static final String CIPBFMK_1 = "外籍配偶";
    // 查詢作業 - 年金統計查詢 - 大陸配偶
    public static final String CIPBFMK_2 = "大陸配偶";
    // 查詢作業 - 年金統計查詢 - 單一國籍
    public static final String CIPBFMK_3 = "單一國籍";
    // 查詢作業 - 年金統計查詢 - 取得國籍
    public static final String CIPBFMK_4 = "取得國籍";
    // 查詢作業 - 年金統計查詢 - 雙重國籍
    public static final String CIPBFMK_5 = "雙重國籍";
    // 查詢作業 - 年金統計查詢 - 老年年金給付
    public static final String PAYKIND_L = "老年年金給付";
    // 查詢作業 - 年金統計查詢 - 失能年金給付
    public static final String PAYKIND_K = "失能年金給付";
    // 查詢作業 - 年金統計查詢 - 遺屬年金給付
    public static final String PAYKIND_S = "遺屬年金給付";
    //查詢作業 - 年金統計查詢
    //國籍別-本籍
    public static final String EVTNATIONTPE_NATIONAL_STR = "本籍";
    //國籍別-外籍
    public static final String EVTNATIONTPE_FOREIGN_STR = "外籍";
    //傷病分類-普通傷病
    public static final String EVTYPE_NORMAL_STR = "普通傷病";
    //傷病分類-職業傷病
    public static final String EVTYPE_PROFESSIONAL_STR = "職業傷病";
    //級距分類
    public static final String SPACING_C = "C";
    public static final String SPACING_C_STR = "金額級距";
    public static final String SPACING_D = "D";
    public static final String SPACING_D_STR = "展減級距";
    public static final String SPACING_E = "E";
    public static final String SPACING_E_STR = "平均薪資級距";
    public static final String SPACING_F = "F";
    public static final String SPACING_F_STR = "年資級距";
    
    // 複檢費用 - 複檢費用受理作業 (bare0d010a.jsp bare0d011a.jsp bare0d012c.jsp bare0d013c.jsp)
    public static final String REVIEW_FEE_RECEIPT_CASE = "ReviewFeeReceiptCase";
    // 複檢費用 - 複檢費用審核作業 (bare0d020a.jsp bare0d021a.jsp bare0d022a.jsp)
    public static final String REVIEW_FEE_REVIEW_CASE = "ReviewFeeReviewCase";
    // 複檢費用 - 複檢費用決行作業 (bare0d030a.jsp bare0d031a.jsp bare0d032a.jsp)
    public static final String REVIEW_FEE_DECISION_CASE = "ReviewFeeDecisionCase";

    // 查詢作業 - 應收查詢作業 - Master資料
    public static final String RECEIVABLE_QUERY_MASTER_CASE = "ReceivableQueryMasterCase";
    // 查詢作業 - 給付查詢作業 - 事故者編審註記
    public static final String PAYMENT_QUERY_EVT_CHKFILE_DATA_CASE = "PaymentQueryEvtChkFileDataCase";
    // 查詢作業 - 給付查詢作業 - 受款人編審註記
    public static final String PAYMENT_QUERY_BEN_CHKFILE_DATA_CASE = "PaymentQueryBenChkFileDataCase";
    // 查詢作業 - 給付查詢作業 - 符合註記
    public static final String PAYMENT_QUERY_MATCH_CHKFILE_DATA_CASE = "PaymentQueryMatchChkFileDataCase";
    // 批次處理 -給付媒體回押註記作業- 資料
    public static final String UPDATE_PAID_MARK_BJ_CASE = "UpdatePaidMarkBJCase";
    // 批次處理 -給付媒體回押註記作業- 媒體檔案資訊資料
    public static final String UPDATE_PAID_MARK_BJ_DETAIL_CASE = "UpdatePaidMarkBJDetailCase";
    // 批次處理 -給付媒體回押註記作業- 給付媒體檔案資料比對結果資料
    public static final String UPDATE_PAID_MARK_BJ_DETAIL_CASE_2 = "UpdatePaidMarkBJDetailCase2";
    // 批次處理 -給付媒體回押註記作業- 給付媒體檔案轉入資訊資料
    public static final String UPDATE_PAID_MARK_BJ_DETAIL_CASE_3 = "UpdatePaidMarkBJDetailCase3";
    // 批次處理 -收回作業- 資料
    public static final String RETURN_WRITE_OFF_BJ_CASE = "ReturnWriteOffBJCase";
    // 批次處理 - 轉催收作業 - Master資料
    public static final String TRANS_2_OVERDUE_RECEIVABLE_BJ_MASTER_CASE = "Trans2OverdueReceivableBJMasterCase";
    // 批次處理 - 轉催收作業 - Detail資料
    public static final String TRANS_2_OVERDUE_RECEIVABLE_BJ_DETAIL_CASE = "Trans2OverdueReceivableBJDetailCase";
    // 批次處理 - 已收調整作業 - Master結果
    public static final String RECEIVABLE_ADJUST_BJ_MASTER_CASE = "ReceivableQueryMasterCase";
    // 批次處理 - 已收調整作業 - Detail結果
    public static final String RECEIVABLE_ADJUST_BJ_DETAIL_CASE = "ReceivableQueryDetailCase";
    // 批次處理 - 線上月核定作業 - 表頭
    public static final String MONTH_QUERY_CASE_FOR_TITLE = "MonthQueryCaseForTitle";
    // 批次處理 - 線上月核定作業 - Detail資料
    public static final String MONTH_L_CASE_LIST = "MonthLCaseList";
    // 批次處理 - 線上月核定作業 - Detail資料
    public static final String MONTH_K_CASE_LIST = "MonthKCaseList";
    // 批次處理 - 線上月核定作業 - Detail資料
    public static final String MONTH_S_CASE_LIST = "MonthSCaseList";
    // 批次處理 - 線上月核定作業 - key
    public static final String MONTH_Y = "Y";
    // 批次處理 - 線上月核定作業 - key
    public static final String MONTH_QUERY_ISSUYM = "MonthQueryIssuYm";
    // 批次處理 - 線上月試核作業 - Detail資料
    public static final String MONTH_CHECK_L_CASE_LIST = "MonthCheckLCaseList";
    // 批次處理 - 線上月試核作業 - Detail資料
    public static final String MONTH_CHECK_K_CASE_LIST = "MonthCheckKCaseList";
    // 批次處理 - 線上月試核作業 - Detail資料
    public static final String MONTH_CHECK_S_CASE_LIST = "MonthCheckSCaseList";
    // 批次處理 - 批次月處理作業 - Detail資料
    public static final String MONTH_BATCH_L_CASE_LIST = "MonthBatchLCaseList";
    // 批次處理 - 批次月處理作業 - Detail資料
    public static final String MONTH_BATCH_K_CASE_LIST = "MonthBatchKCaseList";
    // 批次處理 - 批次月處理作業 - Detail資料
    public static final String MONTH_BATCH_S_CASE_LIST = "MonthBatchSCaseList";
    // 批次處理 - 批次月處理作業 - 批次作業明細檔資料
    public static final String MONTH_BATCH_DTL_CASE_LIST = "MonthBatchDtlCaseList";
    // 批次處理 - 檢核確認作業 - 檢核確認作業明細檔資料
    public static final String CHECK_JOB_CASE_LIST = "CheckJobCaseList";
    // 批次作業 - 補正核付資料作業 - 老年年金補正核付作業 
    public static final String COMP_PAYMENT_CASE = "CompPaymentCase";
    // 批次作業 - 補正核付資料作業 - 老年年金補正核付作業 
    public static final String COMP_PAYMENT_FORM = "CompPaymentForm";
    // 月核定處理相關報表 - 合格清冊產製報表進度查詢
    public static final String MONTHLY_RPT08_PROGRESS_CASE_LIST = "MonthlyRpt08ProgressCaseList";
    // 月核定處理相關報表 - 合格清冊產製報表進度查詢
    public static final String MONTHLY_RPT10_PROGRESS_CASE_LIST = "MonthlyRpt10ProgressCaseList";
    // 其他報表 - 列印轉呆帳核定清單維護作業
    public static final String OTHER_RPT08_DETAIL_CASE_LIST = "OtherRpt08DetailCaseList";
    // 繳款單作業 - 繳款單維護作業
    public static final String PAYMENT_PROCESS_QUERY_CASE_LIST = "PaymentProcessQueryCase";
    // 繳款單作業 - 繳款單維護作業
    public static final String PAYMENT_PROCESS_DETAIL_CASE_LIST = "PaymentProcessDetailCase";
    // 繳款單作業 - 繳款單維護作業
    public static final String PAYMENT_INTEREST_DETAIL_CASE_LIST = "PaymentInterestDetailCaseList";
    // 繳款單作業 - 繳款單維護作業
    public static final String PAYMENT_INTEREST_DETAIL_CASE_LIST_DB = "PaymentInterestDetailCaseListDb";
    // 繳款單作業 - 繳款單維護作業
    public static final String PAYMENT_ASSIGN_DETAIL_CASE_LIST = "PaymentAssignDetailCaseList";
    // 繳款單作業 - 繳款單維護作業
    public static final String PAYMENT_QUERY_LIST = "PaymentQueryList";
    // 繳款單作業 - 繳款列印作業
    public static final String PAYMENT_REPRINT_LIST = "PaymentReprintList";

    // 繳款單作業 - 繳款單維護作業
    public static final String PAYMENT_PROCESS_QUERY_FORM = "PaymentProcessQueryForm";
    // 繳款單作業 - 繳款單列印作業
    public static final String PAYMENT_REPRINT_FORM = "PaymentReprintForm";
    // 繳款單作業 - 繳款單列印作業
    public static final String PAYMENT_PROGRESS_QUERY_FORM = "PaymentProgressQueryForm";
    // 列印作業 - 應收收回相關報表 - 整批收回核定清單 - 退現資料
    public static final String BATCH_PAYMENT_RECEIVE_DATA_CASE_LIST = "BatchPaymentReceiveDataCaseList";
    // ]

    // ---------------------------------------------------------
    // Option List Request / Session Key
    // ---------------------------------------------------------
    // BAPARAM - 系統參數檔
    // [
    // 給付種類 下拉式選單 (BAPARAM.PARAMGROUP = 'PAYCODE')
    public static final String PAYKIND_OPTION_LIST = "_payKind_option_list";
    // 給付類別 下拉式選單 (BAPARAM.PARAMGROUP = 'CHKGROUP')
    public static final String PAYCATEGORY_OPTION_LIST = "_payCategory_option_list";
    // 嚴重程度 下拉式選單 (BAPARAM.PARAMGROUP = 'CHKLEVEL')
    public static final String CHKLEVEL_OPTION_LIST = "_chkLevel_option_list";
    // 決行層級 下拉式選單 (BAPARAM.PARAMGROUP = 'RECHKLVL')
    public static final String RECHKLVL_OPTION_LIST = "_rechkLvl_option_list";
    // 扣繳順位下拉式選單
    public static final String[] CUTSEQ_OPTION_LIST = { "順位一", "順位二", "順位三", "順位四", "順位五", "順位六" };
    // 給付方式 下拉式選單 (BAPARAM.PARAMGROUP = 'PAYTYP')
    public static final String PAYTYP_OPTION_LIST = "_payTyp_option_list";
    // 止付原因 下拉式選單 (BAPARAM.PARAMGROUP = 'STEXPNDREASON')
    public static final String STEXPNDREASON_OPTION_LIST = "_stexpndReason_option_list";
    // 更正原因 下拉式選單
    public static final String CHGNOTE_OPTION_LIST = "_chgNote_option_list";
    // 程式名稱 下拉式選單 (BAPROCEDURE)
    public static final String PROCEDURE_NAME_LIST = "_procedure_name_list";

    // 參數種類 (BAPARAM.PARAMGROUP = 'APITEM')
    // 參數代號 PARAMCODE - 1 - 老年年金給付
    public static final String APITEM_PARAMCODE_1 = "老年年金給付";
    // 參數代號 PARAMCODE - 2 - 減額老年年金給付
    public static final String APITEM_PARAMCODE_2 = "減額老年年金給付";
    // 參數代號 PARAMCODE - 3 - 危險堅強體力
    public static final String APITEM_PARAMCODE_3 = "危險堅強體力";
    // ]

    // 國籍別 下拉式選單
    public static final String COUNTRYID_OPTION_LIST = "_countryid_option_list";
    // 處理函別 下拉式選單
    public static final String LETTERTYPE_OPTION_LIST = "_letterType_option_list";
    // 處理函別 下拉選單條件
    public static final String BUTTON_STATUS_Q2 = "_button_status_q2";
    // 處理註記 下拉式選單
    public static final String NDOMK_OPTION_LIST = "_ndomk_option_list";
    // 關係 下拉式選單
    public static final String RELATIION_OPTION_LIST = "_relation_option_list";
    // 關係 受款人專用下拉式選單
    public static final String PAYEE_RELATIION_OPTION_LIST = "_payee_relation_option_list";
    // 繼承自受款人 受款人專用下拉式選單
    public static final String BENNAME_OPTION_LIST = "_benname_option_list";
    // 具名領取 下拉式選單
    public static final String BEN_OPTION_LIST = "_ben_option_list";
    // 申請項目 下拉式選單
    public static final String APITEM_OPTION_LIST = "_apItem_option_list";
    // 結案原因 下拉式選單
    public static final String CLOSECAUSE_OPTION_LIST = "_closeCasue_option_list";
    // 給付編審檔列表
    public static final String CHKFILE_OPTION_LIST = "_chkFile_option_list";
    // 給付性質下拉式選單
    public static final String RELIEFTYP_OPTION_LIST = "_reliefTyp_option_list";
    // 救濟種類下拉式選單
    public static final String RELIEFKIND_OPTION_LIST = "_reliefKind_option_list";
    // 行政救濟辦理情形下拉式選單
    public static final String RELIEFSTAT_OPTION_LIST = "_reliefStat_option_list";
    // 申請代算單位下拉式選單
    public static final String OLDAPLDPT_OPTION_LIST = "_oldAplDpt_option_list";
    // 結案原因下拉式選單
    public static final String CLOSE_OPTION_LIST = "_close_option_list";
    // (遺屬年金受款人資料更正)結案原因下拉式選單
    public static final String SURVIVOR_CLOSE_OPTION_LIST = "_survivor_close_option_list";
    // (遺屬年金受款人資料更正)不合格原因下拉式選單
    public static final String SURVIVOR_UNQUALIFIEDCAUSE_OPTION_LIST = "_survivor_unqualifiedcause_option_list";
    // 遺屬年金給付審核 給付年月下拉選單
    public static final String SURVIVOR_PAYMENT_REVIEW_PAYYM_OPTION_LIST = "_survivor_payment_review_payYm_option_list";
    // 遺屬年金給付審核 受款人下拉選單
    public static final String SURVIVOR_PAYMENT_REVIEW_SEQNO_OPTION_LIST = "_survivor_payment_review_seqNo_option_list";
    // 遺屬年金給付決行 給付年月下拉選單
    public static final String SURVIVOR_PAYMENT_DECISION_PAYYM_OPTION_LIST = "_survivor_payment_decision_payYm_option_list";
    // 遺屬年金給付決行 受款人下拉選單
    public static final String SURVIVOR_PAYMENT_DECISION_SEQNO_OPTION_LIST = "_survivor_payment_decision_seqNo_option_list";
    // 遺屬年金編審註記程度調整 年月下拉選單
    public static final String SURVIVOR_CHECK_MARK_LEVEL_ADJUST_PAYYM_OPTION_LIST = "_survivor_check_mark_level_adjust_payYm_option_list";
    // 遺屬年金編審註記程度調整 受款人下拉選單
    public static final String SURVIVOR_CHECK_MARK_LEVEL_ADJUST_SEQNO_OPTION_LIST = "_survivor_check_mark_level_adjust_seqNo_option_list";
    // 給付查詢 核定年月下拉選單
    public static final String PAYMENT_QUERY_ISSUYM_OPTION_LIST = "_payment_query_issuYm_option_list";
    // 給付查詢 給付年月下拉選單
    public static final String PAYMENT_QUERY_PAYYM_OPTION_LIST = "_payment_query_payYm_option_list";
    // 給付查詢 國保核定年月下拉選單
    public static final String PAYMENT_QUERY_NPDATA_ISSUYM_OPTION_LIST = "_npData_payment_query_issuYm_option_list";
    // 給付查詢 國保給付年月下拉選單
    public static final String PAYMENT_QUERY_NPDATA_PAYYM_OPTION_LIST = "_npData_payment_query_payYm_option_list";
    // 給付查詢 受款人下拉選單
    public static final String PAYMENT_QUERY_SEQNO_OPTION_LIST = "_payment_query_seqNo_option_list";
    // 學校代碼 下拉式選單
    public static final String SCHOOLCODE_OPTION_LIST = "_schoolcode_option_list";

    // BAAPPBASE - 給付主檔
    // [
    // 國籍別 - 1 - 本國
    public static final String BAAPPBASE_NATIONCODE_1 = "1";
    // 國籍別 - 2 - 外籍
    public static final String BAAPPBASE_NATIONCODE_2 = "2";
    // 國籍別 - 1 - 本國 (中文)
    public static final String BAAPPBASE_NATIONCODE_STR_1 = "本國";
    // 國籍別 - 2 - 外籍 (中文)
    public static final String BAAPPBASE_NATIONCODE_STR_2 = "外籍";

    // 性別 - 1 - 男
    public static final String BAAPPBASE_SEX_1 = "1";
    // 性別 - 2 - 女
    public static final String BAAPPBASE_SEX_2 = "2";

    // 性別 - 1 - 男
    public static final String BAAPPBASE_SEX_STR_1 = "男";
    // 性別 - 2 - 女
    public static final String BAAPPBASE_SEX_STR_2 = "女";

    // 地址 - 1 - 同戶籍地
    public static final String BAAPPBASE_COMMTYP_1 = "1";
    // 地址 - 2 - 現住址
    public static final String BAAPPBASE_COMMTYP_2 = "2";

    // 申請項目 - 1 - 老年年金給付
    public static final String BAAPPBASE_APITEM_1 = "1";
    // 申請項目 - 2 - 減額老年年金給付
    public static final String BAAPPBASE_APITEM_2 = "2";
    // 申請項目 - 3 - 危險堅強體力
    public static final String BAAPPBASE_APITEM_3 = "3";
    // 申請項目 - 4 - 遺屬年金加喪葬津貼
    public static final String BAAPPBASE_APITEM_4 = "4";
    // 申請項目 - 5 - 遺屬年金
    public static final String BAAPPBASE_APITEM_5 = "5";
    // 申請項目 - 7 - 領取失能年金期間死亡之遺屬年金
    public static final String BAAPPBASE_APITEM_7 = "7";
    // 申請項目 - 8 - 領取老年年金期間死亡之遺屬年金
    public static final String BAAPPBASE_APITEM_8 = "8";
    // 申請項目 - 9 - 年資15年且符合老年一次給付之遺屬年金
    public static final String BAAPPBASE_APITEM_9 = "9";
    // 申請項目 - 0 - 失能年金
    public static final String BAAPPBASE_APITEM_0 = "0";

    // 申請項目-1-老年年金給付 (中文)
    public static final String BAAPPBASE_APITEM_STR_1 = "老年年金給付";
    // 申請項目-2-減額老年年金給付 (中文)
    public static final String BAAPPBASE_APITEM_STR_2 = "減額老年年金給付";
    // 申請項目-3-危險堅強體力 (中文)
    public static final String BAAPPBASE_APITEM_STR_3 = "危險堅強體力";
    // 申請項目-4-遺屬年金加喪葬津貼 (中文)
    public static final String BAAPPBASE_APITEM_STR_4 = "遺屬年金加喪葬津貼";
    // 申請項目-5-遺屬年金 (中文)
    public static final String BAAPPBASE_APITEM_STR_5 = "遺屬年金";
    // 申請項目-7-領取失能年金期間死亡之遺屬年金 (中文)
    public static final String BAAPPBASE_APITEM_STR_7 = "領取失能年金期間死亡之遺屬年金";
    // 申請項目-8-領取老年年金期間死亡之遺屬年金 (中文)
    public static final String BAAPPBASE_APITEM_STR_8 = "領取老年年金期間死亡之遺屬年金";
    // 申請項目-9-年資15年且符合老年一次給付之遺屬年金 (中文)
    public static final String BAAPPBASE_APITEM_STR_9 = "年資15年且符合老年一次給付之遺屬年金";
    // 申請項目-0-失能年金 (中文)
    public static final String BAAPPBASE_APITEM_STR_0 = "失能年金給付";

    // 申請項目-1-老年年金給付 (代碼+中文名稱)
    public static final String BAAPPBASE_APITEM_CODESTR_1 = "1-老年年金給付";
    // 申請項目-2-減額老年年金給付 (代碼+中文名稱)
    public static final String BAAPPBASE_APITEM_CODESTR_2 = "2-減額老年年金給付";
    // 申請項目-3-危險堅強體力 (代碼+中文名稱)
    public static final String BAAPPBASE_APITEM_CODESTR_3 = "3-危險堅強體力";
    // 申請項目-4-遺屬年金加喪葬津貼 (代碼+中文名稱)
    public static final String BAAPPBASE_APITEM_CODESTR_4 = "4-遺屬年金加喪葬津貼";
    // 申請項目-5-遺屬年金 (代碼+中文名稱)
    public static final String BAAPPBASE_APITEM_CODESTR_5 = "5-遺屬年金";
    // 申請項目-7-領取失能年金期間死亡之遺屬年金 (代碼+中文名稱)
    public static final String BAAPPBASE_APITEM_CODESTR_7 = "7-領取失能年金期間死亡之遺屬年金";
    // 申請項目-8-領取老年年金期間死亡之遺屬年金 (代碼+中文名稱)
    public static final String BAAPPBASE_APITEM_CODESTR_8 = "8-領取老年年金期間死亡之遺屬年金";
    // 申請項目-9-年資15年且符合老年一次給付之遺屬年金 (代碼+中文名稱)
    public static final String BAAPPBASE_APITEM_CODESTR_9 = "9-年資15年且符合老年一次給付之遺屬年金";
    // 申請項目-0-失能年金 (代碼+中文名稱)
    public static final String BAAPPBASE_APITEM_CODESTR_0 = "0-失能年金給付";

    // 改匯方式 - 1 - 本人領取
    public static final String BAAPPBASE_ACCREL_1 = "1";
    // 改匯方式 - 2 - 監護人
    public static final String BAAPPBASE_ACCREL_2 = "2";
    // 改匯方式 - 3 - 共同具領
    public static final String BAAPPBASE_ACCREL_3 = "3";
    // 改匯方式 - 4 - 代領轉發
    public static final String BAAPPBASE_ACCREL_4 = "4";
    // 改匯方式 - 1 - 本人領取(中文)
    public static final String BAAPPBASE_ACCREL_STR_1 = "1";
    // 改匯方式 - 2 - 監護人(中文)
    public static final String BAAPPBASE_ACCREL_STR_2 = "2";
    // 改匯方式 - 3 - 共同具領(中文)
    public static final String BAAPPBASE_ACCREL_STR_3 = "3";
    // 改匯方式 - 4 - 代領轉發(中文)
    public static final String BAAPPBASE_ACCREL_STR_4 = "4";

    // 給付方式 - 1 - 匯入銀行帳號
    public static final String BAAPPBASE_PAYTYP_1 = "1";
    // 給付方式 - 2 - 匯入郵局帳號
    public static final String BAAPPBASE_PAYTYP_2 = "2";
    // 給付方式 - 3 - 來局領存(土銀駐局)
    public static final String BAAPPBASE_PAYTYP_3 = "3";
    // 給付方式 - 4 - 匯票郵寄申請人
    public static final String BAAPPBASE_PAYTYP_4 = "4";
    // 給付方式 - 5 - 國外匯款
    public static final String BAAPPBASE_PAYTYP_5 = "5";
    // 給付方式 - 6 - 大陸地區匯款
    public static final String BAAPPBASE_PAYTYP_6 = "6";
    // 給付方式 - 7 - 緊急電匯
    public static final String BAAPPBASE_PAYTYP_7 = "7";
    // 給付方式 - 8 - 人工電匯
    public static final String BAAPPBASE_PAYTYP_8 = "8";
    // 給付方式 - 9 - 臨櫃(人工開票)
    public static final String BAAPPBASE_PAYTYP_9 = "9";
    // 給付方式 - A - 扣押戶
    public static final String BAAPPBASE_PAYTYP_A = "A";

    // 給付方式 = 2 時的 金融機構總代號
    public static final String BAAPPBASE_PAYTYP_2_PAYBANKID = "700";
    // 給付方式 = 2 時的 分支代號
    public static final String BAAPPBASE_PAYTYP_2_BRANCHID = "0021";

    // 給付方式 - 1 - 匯入銀行帳號 (中文)
    public static final String BAAPPBASE_PAYTYP_STR_1 = "匯入銀行帳號";
    // 給付方式 - 2 - 匯入郵局帳號 (中文)
    public static final String BAAPPBASE_PAYTYP_STR_2 = "匯入郵局帳號";
    // 給付方式 - 3 - 來局領存(土銀駐局) (中文)
    public static final String BAAPPBASE_PAYTYP_STR_3 = "來局領取";
    // 給付方式 - 4 - 匯票郵寄申請人 (中文)
    public static final String BAAPPBASE_PAYTYP_STR_4 = "匯票郵寄申請人";
    // 給付方式 - 5 - 國外匯款 (中文)
    public static final String BAAPPBASE_PAYTYP_STR_5 = "國外匯款";
    // 給付方式 - 6 - 大陸地區匯款 (中文)
    public static final String BAAPPBASE_PAYTYP_STR_6 = "大陸地區匯款";
    // 給付方式 - 7 - 緊急電匯 (中文)
    public static final String BAAPPBASE_PAYTYP_STR_7 = "緊急電匯";
    // 給付方式 - 8 - 人工電匯 (中文)
    public static final String BAAPPBASE_PAYTYP_STR_8 = "人工電匯";
    // 給付方式 - 9 - 臨櫃(人工開票) (中文)
    public static final String BAAPPBASE_PAYTYP_STR_9 = "臨櫃(人工開票)";
    // 給付方式 - A - 扣押戶 (中文)
    public static final String BAAPPBASE_PAYTYP_STR_A = "扣押戶";

    // 給付方式 - 1 - 匯入銀行帳號 (代碼+中文名稱)
    public static final String BAAPPBASE_PAYTYP_CODESTR_1 = "1 - 匯入銀行帳號";
    // 給付方式 - 2 - 匯入郵局帳號 (代碼+中文名稱)
    public static final String BAAPPBASE_PAYTYP_CODESTR_2 = "2 - 匯入郵局帳號";
    // 給付方式 - 3 - 來局領取 (代碼+中文名稱)
    public static final String BAAPPBASE_PAYTYP_CODESTR_3 = "3 - 來局領取";
    // 給付方式 - 4 - 匯票郵寄申請人 (代碼+中文名稱)
    public static final String BAAPPBASE_PAYTYP_CODESTR_4 = "4 - 匯票郵寄申請人";
    // 給付方式 - 5 - 國外匯款 (代碼+中文名稱)
    public static final String BAAPPBASE_PAYTYP_CODESTR_5 = "5 - 國外匯款";
    // 給付方式 - 6 - 大陸地區匯款 (代碼+中文名稱)
    public static final String BAAPPBASE_PAYTYP_CODESTR_6 = "6 - 大陸地區匯款";
    // 給付方式 - 7 - 緊急電匯 (代碼+中文名稱)
    public static final String BAAPPBASE_PAYTYP_CODESTR_7 = "7 - 緊急電匯";
    // 給付方式 - 8 - 人工電匯 (代碼+中文名稱)
    public static final String BAAPPBASE_PAYTYP_CODESTR_8 = "8 - 人工電匯";
    // 給付方式 - 9 - 臨櫃(人工開票) (代碼+中文名稱)
    public static final String BAAPPBASE_PAYTYP_CODESTR_9 = "9 - 臨櫃(人工開票)";
    // 給付方式 - A - 扣押戶 (代碼+中文名稱)
    public static final String BAAPPBASE_PAYTYP_CODESTR_A = "A - 扣押戶";

    // 給付種類 - 35 - 失能年金
    public static final String BAAPPBASE_PAYKIND_35 = "35";
    // 給付種類 - 36 - 失能年金
    public static final String BAAPPBASE_PAYKIND_36 = "36";
    // 給付種類 - 37 - 職災失能補償一次金
    public static final String BAAPPBASE_PAYKIND_37 = "37";
    // 給付種類 - 38 - 失能年金併計國保年資
    public static final String BAAPPBASE_PAYKIND_38 = "38";
    // 給付種類 - 39 - 請領一次失能給付差額
    public static final String BAAPPBASE_PAYKIND_39 = "39";
    // 給付種類 - 45 - 老年年金
    public static final String BAAPPBASE_PAYKIND_45 = "45";
    // 給付種類 - 48 - 併計國保年資
    public static final String BAAPPBASE_PAYKIND_48 = "48";
    // 給付種類 - 49 - 請領一次老年給付差額
    public static final String BAAPPBASE_PAYKIND_49 = "49";
    // 給付種類 - 55 - 遺屬年金
    public static final String BAAPPBASE_PAYKIND_55 = "55";
    // 給付種類 - 56 - 遺屬年金(老年年金後續)
    public static final String BAAPPBASE_PAYKIND_56 = "56";
    // 給付種類 - 57 - 職災死亡補償一次金
    public static final String BAAPPBASE_PAYKIND_57 = "57";
    // 給付種類 - 58 - 喪葬津貼
    public static final String BAAPPBASE_PAYKIND_58 = "58";
    // 給付種類 - 59 - 遺屬年金(失能年金後續)
    public static final String BAAPPBASE_PAYKIND_59 = "59";

    // 給付種類 - 35 - 失能年金 (中文)
    public static final String BAAPPBASE_PAYKIND_STR_35 = "失能年金";
    // 給付種類 - 36 - 國保年金併計失能(主辦為國保年金) (中文)
    public static final String BAAPPBASE_PAYKIND_STR_36 = "國保年金併計失能(主辦為國保年金)";
    // 給付種類 - 37 - 職災失能補償一次金 (中文)
    public static final String BAAPPBASE_PAYKIND_STR_37 = "職災失能補償一次金";
    // 給付種類 - 38 - 失能年金併計國保年資 (中文)
    public static final String BAAPPBASE_PAYKIND_STR_38 = "失能年金併計國保年資";
    // 給付種類 - 39 - 請領一次失能給付差額 (中文)
    public static final String BAAPPBASE_PAYKIND_STR_39 = "請領一次失能給付差額";
    // 給付種類 - 45 - 老年年金 (中文)
    public static final String BAAPPBASE_PAYKIND_STR_45 = "老年年金";
    // 給付種類 - 48 - 併計國保年資 (中文)
    public static final String BAAPPBASE_PAYKIND_STR_48 = "併計國保年資";
    // 給付種類 - 49 - 請領一次老年給付差額 (中文)
    public static final String BAAPPBASE_PAYKIND_STR_49 = "請領一次老年給付差額";
    // 給付種類 - 55 - 遺屬年金 (中文)
    public static final String BAAPPBASE_PAYKIND_STR_55 = "遺屬年金";
    // 給付種類 - 56 - 遺屬年金(老年年金後續) (中文)
    public static final String BAAPPBASE_PAYKIND_STR_56 = "遺屬年金(老年年金後續)";
    // 給付種類 - 57 - 職災死亡補償一次金 (中文)
    public static final String BAAPPBASE_PAYKIND_STR_57 = "職災死亡補償一次金";
    // 給付種類 - 58 - 喪葬津貼 (中文)
    public static final String BAAPPBASE_PAYKIND_STR_58 = "喪葬津貼";
    // 給付種類 - 59 - 遺屬年金(失能年金後續) (中文)
    public static final String BAAPPBASE_PAYKIND_STR_59 = "遺屬年金(失能年金後續)";

    // 止付原因 - 01 - 撤銷
    public static final String BAAPPBASE_STEXPNDREASON_STR_01 = "撤銷";
    // 止付原因 - 02 - 基本資料錯誤
    public static final String BAAPPBASE_STEXPNDREASON_STR_02 = "基本資料錯誤";
    // 止付原因 - 03 - 帳號錯誤
    public static final String BAAPPBASE_STEXPNDREASON_STR_03 = "帳號錯誤";
    // 止付原因 - 04 - 改核不給付
    public static final String BAAPPBASE_STEXPNDREASON_STR_04 = "改核不給付";
    // 止付原因 - 05 - 核定金額異動
    public static final String BAAPPBASE_STEXPNDREASON_STR_05 = "核定金額異動";
    // 止付原因 - 06 - 已死亡
    public static final String BAAPPBASE_STEXPNDREASON_STR_06 = "已死亡";
    // 止付原因 - 07 - 已死亡
    public static final String BAAPPBASE_STEXPNDREASON_STR_07 = "更改給付方式";
    // 止付原因 - 07 - 其他
    public static final String BAAPPBASE_STEXPNDREASON_STR_99 = "其他";

    // 關係 - 1 - 本人
    public static final String BAAPPBASE_BENEVTREL_1 = "1";
    // 關係 - 2 - 配偶
    public static final String BAAPPBASE_BENEVTREL_2 = "2";
    // 關係 - 3 - 父母
    public static final String BAAPPBASE_BENEVTREL_3 = "3";
    // 關係 - 4 - 子女
    public static final String BAAPPBASE_BENEVTREL_4 = "4";
    // 關係 - 5 - 祖父母
    public static final String BAAPPBASE_BENEVTREL_5 = "5";
    // 關係 - 6 - 兄弟姊妹
    public static final String BAAPPBASE_BENEVTREL_6 = "6";
    // 關係 - 7 - 孫子女
    public static final String BAAPPBASE_BENEVTREL_7 = "7";
    // 關係 - A - 投保單位
    public static final String BAAPPBASE_BENEVTREL_A = "A";
    // 關係 - C - 單位代領轉發
    public static final String BAAPPBASE_BENEVTREL_C = "C";
    // 關係 - E - 受委託人(個人)
    public static final String BAAPPBASE_BENEVTREL_E = "E";
    // 關係 - F - 紓困貸款分行
    public static final String BAAPPBASE_BENEVTREL_F = "F";
    // 關係 - N - 抵銷呆帳債務人紓困貸款金額
    public static final String BAAPPBASE_BENEVTREL_N = "N";
    // 關係 - Z - 補償繳還單位
    public static final String BAAPPBASE_BENEVTREL_Z = "Z";
    // 關係 - O - 其他
    public static final String BAAPPBASE_BENEVTREL_O = "O";

    // 關係 - 1 - 本人 (中文)
    public static final String BAAPPBASE_BENEVTREL_STR_1 = "本人";
    // 關係 - 2 - 配偶 (中文)
    public static final String BAAPPBASE_BENEVTREL_STR_2 = "配偶";
    // 關係 - 3 - 父母 (中文)
    public static final String BAAPPBASE_BENEVTREL_STR_3 = "父母";
    // 關係 - 4 - 子女 (中文)
    public static final String BAAPPBASE_BENEVTREL_STR_4 = "子女";
    // 關係 - 5 - 祖父母 (中文)
    public static final String BAAPPBASE_BENEVTREL_STR_5 = "祖父母";
    // 關係 - 6 - 兄弟姊妹 (中文)
    public static final String BAAPPBASE_BENEVTREL_STR_6 = "兄弟姊妹";
    // 關係 - 7 - 孫子女 (中文)
    public static final String BAAPPBASE_BENEVTREL_STR_7 = "孫子女";
    // 關係 - A - 投保單位 (中文)
    public static final String BAAPPBASE_BENEVTREL_STR_A = "投保單位";
    // 關係 - C - 單位代領轉發 (中文)
    public static final String BAAPPBASE_BENEVTREL_STR_C = "單位代領轉發";
    // 關係 - E - 受委託人(個人) (中文)
    public static final String BAAPPBASE_BENEVTREL_STR_E = "受委託人(個人)";
    // 關係 - F - 紓困貸款分行 (中文)
    public static final String BAAPPBASE_BENEVTREL_STR_F = "紓困貸款分行";
    // 關係 - N - 抵銷呆帳債務人紓困貸款金額 (中文)
    public static final String BAAPPBASE_BENEVTREL_STR_N = "抵銷呆帳債務人紓困貸款金額";
    // 關係 - Z - 補償繳還單位 (中文)
    public static final String BAAPPBASE_BENEVTREL_STR_Z = "補償繳還單位";
    // 關係 - O - 其他 (中文)
    public static final String BAAPPBASE_BENEVTREL_STR_O = "其他";

    // 關係 - 1 - 本人 (代碼+中文名稱)
    public static final String BAAPPBASE_BENEVTREL_CODESTR_1 = "1 - 本人";
    // 關係 - 2 - 配偶 (代碼+中文名稱)
    public static final String BAAPPBASE_BENEVTREL_CODESTR_2 = "2 - 配偶";
    // 關係 - 3 - 父母 (代碼+中文名稱)
    public static final String BAAPPBASE_BENEVTREL_CODESTR_3 = "3 - 父母";
    // 關係 - 4 - 子女 (代碼+中文名稱)
    public static final String BAAPPBASE_BENEVTREL_CODESTR_4 = "4 - 子女";
    // 關係 - 5 - 祖父母 (代碼+中文名稱)
    public static final String BAAPPBASE_BENEVTREL_CODESTR_5 = "5 - 祖父母";
    // 關係 - 6 - 兄弟姊妹 (代碼+中文名稱)
    public static final String BAAPPBASE_BENEVTREL_CODESTR_6 = "6 - 兄弟姊妹";
    // 關係 - 7 - 孫子女 (代碼+中文名稱)
    public static final String BAAPPBASE_BENEVTREL_CODESTR_7 = "7 - 孫子女";
    // 關係 - A - 投保單位 (代碼+中文名稱)
    public static final String BAAPPBASE_BENEVTREL_CODESTR_A = "A - 投保單位";
    // 關係 - C - 單位代領轉發 (代碼+中文名稱)
    public static final String BAAPPBASE_BENEVTREL_CODESTR_C = "C - 單位代領轉發";
    // 關係 - E - 受委託人(個人) (代碼+中文名稱)
    public static final String BAAPPBASE_BENEVTREL_CODESTR_E = "E - 受委託人(個人)";
    // 關係 - F - 紓困貸款分行 (代碼+中文名稱)
    public static final String BAAPPBASE_BENEVTREL_CODESTR_F = "F - 紓困貸款分行";
    // 關係 - N - 抵銷呆帳債務人紓困貸款金額 (代碼+中文名稱)
    public static final String BAAPPBASE_BENEVTREL_CODESTR_N = "N - 抵銷呆帳債務人紓困貸款金額";
    // 關係 - Z - 補償繳還單位 (代碼+中文名稱)
    public static final String BAAPPBASE_BENEVTREL_CODESTR_Z = "Z - 補償繳還單位";
    // 關係 - O - 其他 (代碼+中文名稱)
    public static final String BAAPPBASE_BENEVTREL_CODESTR_O = "O - 其他";

    // 給付別 - L - 老年年金
    public static final String BAAPPBASE_PAGE_PAYKIND_L = "L";
    // 給付別 - K - 失能年金
    public static final String BAAPPBASE_PAGE_PAYKIND_K = "K";
    // 給付別 - S - 遺屬年金
    public static final String BAAPPBASE_PAGE_PAYKIND_S = "S";
    // 給付別 - L - 老年年金 (中文)
    public static final String BAAPPBASE_PAGE_PAYKIND_STR_L = "老年年金";
    // 給付別 - K - 失能年金 (中文)
    public static final String BAAPPBASE_PAGE_PAYKIND_STR_K = "失能年金";
    // 給付別 - S - 遺屬年金 (中文)
    public static final String BAAPPBASE_PAGE_PAYKIND_STR_S = "遺屬年金";

    // 資料別 - 1 - 新案
    public static final String BAAPPBASE_CASETYP_1 = "1";
    // 資料別 - 2 - 續發案
    public static final String BAAPPBASE_CASETYP_2 = "2";
    // 資料別 - 3 - 不給付案
    public static final String BAAPPBASE_CASETYP_3 = "3";
    // 資料別 - 4 - 結案
    public static final String BAAPPBASE_CASETYP_4 = "4";
    // 資料別 - 5 - 暫緩給付案
    public static final String BAAPPBASE_CASETYP_5 = "5";
    // 資料別 - 6 - 異動案 (中文)
    public static final String BAAPPBASE_CASETYP_6 = "6";
    // 資料別 - A - 改匯
    public static final String BAAPPBASE_CASETYP_A = "A";
    // 資料別 - B - 退匯
    public static final String BAAPPBASE_CASETYP_B = "B";
    // 資料別 - C - 止付
    public static final String BAAPPBASE_CASETYP_C = "C";
    // 資料別 - D - 退匯收回
    public static final String BAAPPBASE_CASETYP_D = "D";
    // 資料別 - E - 退回現金收回
    public static final String BAAPPBASE_CASETYP_E = "E";
    // 資料別 - F - 給付收回
    public static final String BAAPPBASE_CASETYP_F = "F";
    // 資料別 - 1 - 新案 (中文)
    public static final String BAAPPBASE_CASETYP_STR_1 = "新案";
    // 資料別 - 2 - 續發案 (中文)
    public static final String BAAPPBASE_CASETYP_STR_2 = "續發案";
    // 資料別 - 3 - 不給付案 (中文)
    public static final String BAAPPBASE_CASETYP_STR_3 = "不給付案";
    // 資料別 - 4 - 結案 (中文)
    public static final String BAAPPBASE_CASETYP_STR_4 = "結案";
    // 資料別 - 5 - 暫緩給付案 (中文)
    public static final String BAAPPBASE_CASETYP_STR_5 = "補發案";
    // 資料別 - 6 - 異動案 (中文)
    public static final String BAAPPBASE_CASETYP_STR_6 = "暫緩給付案";
    // 資料別 - A - 改匯 (中文)
    public static final String BAAPPBASE_CASETYP_STR_A = "改匯";
    // 資料別 - B - 退匯 (中文)
    public static final String BAAPPBASE_CASETYP_STR_B = "退匯";
    // 資料別 - C - 止付 (中文)
    public static final String BAAPPBASE_CASETYP_STR_C = "止付";
    // 資料別 - D - 退匯收回 (中文)
    public static final String BAAPPBASE_CASETYP_STR_D = "退匯收回";
    // 資料別 - E - 退回現金收回 (中文)
    public static final String BAAPPBASE_CASETYP_STR_E = "退回現金收回";
    // 資料別 - F - 給付收回 (中文)
    public static final String BAAPPBASE_CASETYP_STR_F = "給付收回";

    // 異動狀況 - 1 - 投保年資異動
    public static final String BAAPPBASE_UPCAUSECODE_STR_1 = "投保年資異動";
    // 異動狀況 - 2 - 實發年資異動
    public static final String BAAPPBASE_UPCAUSECODE_STR_2 = "實發年資異動";
    // 異動狀況 - 3 - 國保年資異動
    public static final String BAAPPBASE_UPCAUSECODE_STR_3 = "國保年資異動";
    // 異動狀況 - 4 - 平均薪資異動
    public static final String BAAPPBASE_UPCAUSECODE_STR_4 = "平均薪資異動";
    // 異動狀況 - 5 - 墊付金額異動
    public static final String BAAPPBASE_UPCAUSECODE_STR_5 = "墊付金額異動";
    // 異動狀況 - 6 - 抵銷金額異動
    public static final String BAAPPBASE_UPCAUSECODE_STR_6 = "抵銷金額異動";
    // 異動狀況 - 7 - 代扣補償金額異動
    public static final String BAAPPBASE_UPCAUSECODE_STR_7 = "代扣補償金額異動";
    // 異動狀況 - 8 - 紓困呆帳債務人抵銷金額異動
    public static final String BAAPPBASE_UPCAUSECODE_STR_8 = "紓困呆帳債務人抵銷金額異動";
    // 異動狀況 - 9 - 新增另案扣減
    public static final String BAAPPBASE_UPCAUSECODE_STR_9 = "新增另案扣減";
    // 異動狀況 - 10 - 另案扣減金額異動
    public static final String BAAPPBASE_UPCAUSECODE_STR_10 = "另案扣減金額異動";
    // 異動狀況 - 11 - 匯款手續費異動
    public static final String BAAPPBASE_UPCAUSECODE_STR_11 = "匯款手續費異動";
    // 異動狀況 - 12 - 新增事故者編審註記
    public static final String BAAPPBASE_UPCAUSECODE_STR_12 = "新增事故者編審註記";
    // 異動狀況 - 13 - 事故者編審註記狀況異動
    public static final String BAAPPBASE_UPCAUSECODE_STR_13 = "事故者編審註記狀況異動";
    // 異動狀況 - 14 - 新增眷屬或遺屬編審註記
    public static final String BAAPPBASE_UPCAUSECODE_STR_14 = "新增眷屬或遺屬編審註記";
    // 異動狀況 - 15 - 眷屬或遺屬編審註記狀況異動
    public static final String BAAPPBASE_UPCAUSECODE_STR_15 = "眷屬或遺屬編審註記狀況異動";
    // 異動狀況 - 16 - 眷屬或遺屬符合註記異動
    public static final String BAAPPBASE_UPCAUSECODE_STR_16 = "眷屬或遺屬符合註記異動";
    // 異動狀況 - 17 - 眷屬或遺屬符合人數異動
    public static final String BAAPPBASE_UPCAUSECODE_STR_17 = "眷屬或遺屬符合人數異動";
    // 異動狀況 - 18 - 另案扣減未回應
    public static final String BAAPPBASE_UPCAUSECODE_STR_18 = "另案扣減未回應";
    // 異動代碼_19 每月扣除失能金額異動
    public static final String BAAPPBASE_UPCAUSECODE_STR_19 = "每月扣除失能金額異動";
    // 異動代碼 - 20 - 計息存儲金額異動
    public static final String BAAPPBASE_UPCAUSECODE_STR_20 = "計息存儲金額異動";
    // 異動代碼 - 21 - 展減比率異動
    public static final String BAAPPBASE_UPCAUSECODE_STR_21 = "展減比率異動";
    // 異動代碼 - 22 - 給付種類異動
    public static final String BAAPPBASE_UPCAUSECODE_STR_22 = "給付種類異動";
    // 異動代碼 - 23 - 國保月核定金額變動
    public static final String BAAPPBASE_UPCAUSECODE_STR_23 = "國保月核定金額變動";
    // 異動代碼 - 24 - 線上月試核
    public static final String BAAPPBASE_UPCAUSECODE_STR_24 = "線上月試核";
    // 異動代碼 - 25 - 新增死亡異動
    public static final String BAAPPBASE_UPCAUSECODE_STR_25 = "新增死亡異動";
    // 異動狀況 - 99 - 其他
    public static final String BAAPPBASE_UPCAUSECODE_STR_99 = "其他";

    // 應決行層級 - 1
    public static final String BAAPPBASE_MEXCLVL_1 = "1";
    // 應決行層級 - 2
    public static final String BAAPPBASE_MEXCLVL_2 = "2";
    // 應決行層級 - 3
    public static final String BAAPPBASE_MEXCLVL_3 = "3";
    // 應決行層級 - 4
    public static final String BAAPPBASE_MEXCLVL_4 = "4";
    // 應決行層級 - 5
    public static final String BAAPPBASE_MEXCLVL_5 = "5";
    // 應決行層級 - 6
    public static final String BAAPPBASE_MEXCLVL_6 = "6";
    // 應決行層級 - 1 - 承辦人員
    public static final String BAAPPBASE_MEXCLVL_STR_1 = "承辦人員";
    // 應決行層級 - 2 - 複核
    public static final String BAAPPBASE_MEXCLVL_STR_2 = "複核";
    // 應決行層級 - 3 - 科長
    public static final String BAAPPBASE_MEXCLVL_STR_3 = "科長";
    // 應決行層級 - 4 - 一等專員
    public static final String BAAPPBASE_MEXCLVL_STR_4 = "一等專員";
    // 應決行層級 - 5 - 副經理
    public static final String BAAPPBASE_MEXCLVL_STR_5 = "副經理";
    // 應決行層級 - 6 - 經理
    public static final String BAAPPBASE_MEXCLVL_STR_6 = "經理";

    // 處理狀態 - 00 - 受理
    public static final String BAAPPBASE_PROCSTAT_00 = "00";
    // 處理狀態 - 01 - 更正
    public static final String BAAPPBASE_PROCSTAT_01 = "01";
    // 處理狀態 - 10 - 已編審待審核
    public static final String BAAPPBASE_PROCSTAT_10 = "10";
    // 處理狀態 - 11 - 複核決行退件
    public static final String BAAPPBASE_PROCSTAT_11 = "11";
    // 處理狀態 - 12 - 改核
    public static final String BAAPPBASE_PROCSTAT_12 = "12";
    // 處理狀態 - 13 - 停發
    public static final String BAAPPBASE_PROCSTAT_13 = "13";
    // 處理狀態 - 20 - 審核
    public static final String BAAPPBASE_PROCSTAT_20 = "20";
    // 處理狀態 - 30 - 複核
    public static final String BAAPPBASE_PROCSTAT_30 = "30";
    // 處理狀態 - 40 - 決行
    public static final String BAAPPBASE_PROCSTAT_40 = "40";
    // 處理狀態 - 50 - 核定
    public static final String BAAPPBASE_PROCSTAT_50 = "50";
    // 處理狀態 - 80 - 暫緩給付
    public static final String BAAPPBASE_PROCSTAT_80 = "80";
    // 處理狀態 - 90 - 結案
    public static final String BAAPPBASE_PROCSTAT_90 = "90";
    // 處理狀態 - 98 - 改匯註銷
    public static final String BAAPPBASE_PROCSTAT_98 = "98";
    // 處理狀態 - 99 - 註銷
    public static final String BAAPPBASE_PROCSTAT_99 = "99";

    // 處理狀態 - 00 - 受理 (中文)
    public static final String BAAPPBASE_PROCSTAT_STR_00 = "受理";
    // 處理狀態 - 01 - 更正 (中文)
    public static final String BAAPPBASE_PROCSTAT_STR_01 = "更正";
    // 處理狀態 - 10 - 已編審待審核 (中文)
    public static final String BAAPPBASE_PROCSTAT_STR_10 = "已編審待審核";
    // 處理狀態 - 11 - 複核決行退件 (中文)
    public static final String BAAPPBASE_PROCSTAT_STR_11 = "複核決行退件";
    // 處理狀態 - 12 - 改核 (中文)
    public static final String BAAPPBASE_PROCSTAT_STR_12 = "改核";
    // 處理狀態 - 13 - 停發 (中文)
    public static final String BAAPPBASE_PROCSTAT_STR_13 = "停發";
    // 處理狀態 - 20 - 審核 (中文)
    public static final String BAAPPBASE_PROCSTAT_STR_20 = "審核";
    // 處理狀態 - 30 - 複核 (中文)
    public static final String BAAPPBASE_PROCSTAT_STR_30 = "複核";
    // 處理狀態 - 40 - 決行 (中文)
    public static final String BAAPPBASE_PROCSTAT_STR_40 = "決行";
    // 處理狀態 - 50 - 核定 (中文)
    public static final String BAAPPBASE_PROCSTAT_STR_50 = "核定";
    // 處理狀態 - 80 - 暫緩給付 (中文)
    public static final String BAAPPBASE_PROCSTAT_STR_80 = "暫緩給付";
    // 處理狀態 - 90 - 結案 (中文)
    public static final String BAAPPBASE_PROCSTAT_STR_90 = "結案";
    // 處理狀態 - 99 - 註銷 (中文)
    public static final String BAAPPBASE_PROCSTAT_STR_99 = "註銷";

    // 在學 - 0 - 無
    public static final String BAAPPBASE_STUDMK_0 = "0";
    // 在學 - 1 - 有
    public static final String BAAPPBASE_STUDMK_1 = "1";
    // 在學 - 2 - 待補在學證明
    public static final String BAAPPBASE_STUDMK_2 = "2";
    // 在學 - 0 - 無
    public static final String BAAPPBASE_STUDMK_STR_0 = "否";
    // 在學 - 1 - 有
    public static final String BAAPPBASE_STUDMK_STR_1 = "是";
    // 在學 - 2 - 待補在學證明
    public static final String BAAPPBASE_STUDMK_STR_2 = "待補在學證明";

    // 不須抵銷紓困貸款註記 - 0 - 若有紓困貸款須抵銷紓困貸款
    public static final String BAAPPBASE_LOANMK_0 = "0";
    // 不須抵銷紓困貸款註記 - 1 - 不須抵銷紓困貸款
    public static final String BAAPPBASE_LOANMK_1 = "1";
    // 不須抵銷紓困貸款註記 - 0 - 若有紓困貸款須抵銷紓困貸款 (中文)
    public static final String BAAPPBASE_LOANMK_STR_0 = "若有紓困貸款須抵銷紓困貸款";
    // 不須抵銷紓困貸款註記 - 1 - 不須抵銷紓困貸款 (中文)
    public static final String BAAPPBASE_LOANMK_STR_1 = "不須抵銷紓困貸款";

    // 領有重度以上身心障礙手冊或證明 - Y - 有
    public static final String BAAPPBASE_HANDICAP_STR_Y = "有";
    // 領有重度以上身心障礙手冊或證明 - '' - 無
    public static final String BAAPPBASE_HANDICAP_STR_N = "無";

    // INTERVALMONTH
    public static final String BAAPPBASE_INTERVALMONTH_BY_MONTH = "0";// 按月發放
    public static final String BAAPPBASE_INTERVALMONTH_BY_SIXMONTH = "6";// 半年發放
    // ]

    // [
    // 普職限制 - 1: (1)普通
    public static final String NLWKMK_1 = "1";
    // 普職限制 - 2: (2)職災
    public static final String NLWKMK_2 = "2";

    // 欠費註記 - Y - 欠費
    public static final String OWESMK_Y = "欠費";
    // 欠費註記 - N - 未欠費
    public static final String OWESMK_N = "未欠費";

    // 審核管制條件 - 1
    public static final String PAYMENT_REVIEW_CONDITION_1 = "最後單位不得為空";
    // 審核管制條件 - 2
    public static final String PAYMENT_REVIEW_CONDITION_2 = "單位墊付金額不得為零";
    // 審核管制條件 - 3
    public static final String PAYMENT_REVIEW_CONDITION_3 = "實際補償金額不得為零";
    // 審核管制條件 - 4
    public static final String PAYMENT_REVIEW_CONDITION_4 = "實際補償金額不得為零";
    // 審核管制條件 - 5
    public static final String PAYMENT_REVIEW_CONDITION_5 = "實發年資有誤";
    // 審核管制條件 - 6
    public static final String PAYMENT_REVIEW_CONDITION_6 = "受款人可領金額有誤";
    // 審核管制條件 - 7
    public static final String PAYMENT_REVIEW_CONDITION_7 = "通訊地址欄位不得為空白";
    // 審核管制條件 - 8
    public static final String PAYMENT_REVIEW_CONDITION_8 = "編審註記待處理";
    // 審核管制條件 - 9
    public static final String PAYMENT_REVIEW_CONDITION_9 = "行政支援記錄未銷案";

    // 電腦編審結果 - Y
    public static final String PAYMENT_REVIEW_ACCEPTMK_Y = "Y";
    // 電腦編審結果 - N
    public static final String PAYMENT_REVIEW_ACCEPTMK_N = "N";
    // 電腦編審結果 - Y (中文)
    public static final String PAYMENT_REVIEW_ACCEPTMK_STR_Y = "合格";
    // 電腦編審結果 - N (中文)
    public static final String PAYMENT_REVIEW_ACCEPTMK_STR_N = "不合格";
    // 電腦編審結果 - '' (中文)
    public static final String PAYMENT_REVIEW_ACCEPTMK_STR_OTHER = "待處理";
    // 電腦編審結果 - Y (代碼+中文名稱)
    public static final String PAYMENT_REVIEW_ACCEPTMK_CODESTR_Y = "Y-合格";
    // 電腦編審結果 - N (代碼+中文名稱)
    public static final String PAYMENT_REVIEW_ACCEPTMK_CODESTR_N = "N-不合格";
    // 電腦編審結果 - '' (代碼+中文名稱)
    public static final String PAYMENT_REVIEW_ACCEPTMK_CODESTR_OTHER = "待處理";

    // 人工審核結果 - Y
    public static final String PAYMENT_REVIEW_MANCHKMK_Y = "Y";
    // 人工審核結果 - N
    public static final String PAYMENT_REVIEW_MANCHKMK_N = "N";
    // 人工審核結果 - Y (中文)
    public static final String PAYMENT_REVIEW_MANCHKMK_STR_Y = "合格";
    // 人工審核結果 - N (中文)
    public static final String PAYMENT_REVIEW_MANCHKMK_STR_N = "不合格";
    // 人工審核結果 - '' (中文)
    public static final String PAYMENT_REVIEW_MANCHKMK_STR_OTHER = "待處理";
    // 人工審核結果 - Y (代碼+中文名稱)
    public static final String PAYMENT_REVIEW_MANCHKMK_CODESTR_Y = "Y-合格";
    // 人工審核結果 - N (代碼+中文名稱)
    public static final String PAYMENT_REVIEW_MANCHKMK_CODESTR_N = "N-不合格";
    // 人工審核結果 - '' (代碼+中文名稱)
    public static final String PAYMENT_REVIEW_MANCHKMK_CODESTR_OTHER = "待處理";

    // 審核決行清單 - 報表種類 - 001
    public static final String BAEXALIST_RPTTYP_STR_001 = "月編審異動清單";
    // 審核決行清單 - 報表種類 - 002
    public static final String BAEXALIST_RPTTYP_STR_002 = "審核給付清單";
    // ]
    // 編審註記程度
    public static final String BACHKFILE_CHKCODE_O = "O-可穿透";
    public static final String BACHKFILE_CHKCODE_W = "W-警告";
    public static final String BACHKFILE_CHKCODE_E = "E-嚴重";
    public static final String BACHKFILE_CHKCODE_G = "G-提示註記";

    // 計息存儲註記 - T (中文)
    public static final String BAAPPEXPAND_SAVINGMK_STR_T = "結束計息存儲";
    // 計息存儲註記 - Y (中文)
    public static final String BAAPPEXPAND_SAVINGMK_STR_Y = "計息存儲存續期間";
    // 計息存儲註記 - '' (中文)
    public static final String BAAPPEXPAND_SAVINGMK_STR_OTHER = "無計息存儲";
    // ]

    // 報表REPLACE
    public static final String A001 = "A001";// 受理編號
    public static final String A002 = "A002";// 申請日期
    public static final String A003 = "A003";// 事故者姓名
    public static final String A004 = "A004";// 事故者身分證號
    public static final String A005 = "A005";// 事故者出生日期
    public static final String A006 = "A006";// 死亡日期
    public static final String A007 = "A007";// 核付日期
    public static final String A008 = "A008";// 逕予退保日
    public static final String A009 = "A009";// 普通/職業
    public static final String A010 = "A010";// 投保年資
    public static final String A011 = "A011";// 實付年資
    public static final String A012 = "A012";// 最高60個月平均薪資
    public static final String A013 = "A013";// 3年平均薪資
    public static final String A014 = "A014";// 6個月平均薪資（失能、遺屬）
    public static final String A015 = "A015";// 計算式
    public static final String A016 = "A016";// 計算金額
    public static final String A017 = "A017";// 展延或減額期間
    public static final String A019 = "A019";// 老年年金金額
    public static final String A020 = "A020";// 失能年金金額
    public static final String A026 = "A026";// 喪葬津貼金額
    public static final String A028 = "A028";// 受委託人姓名
    public static final String A029 = "A029";// 受委託人身分證號
    public static final String A030 = "A030";// 受委託人出生日期
    public static final String A031 = "A031";// 國保年資
    public static final String A035 = "A035";// 失能項目
    public static final String A036 = "A036";// 失能等級
    public static final String A040 = "A040";// 給付年月
    public static final String A041 = "A041";// 給付年月起迄
    public static final String A042 = "A042";// 補發部分金額之給付年月起迄
    public static final String A043 = "A043";// 補發部分金額之給付年月起月
    public static final String A044 = "A044";// 給付年月核定金額
    public static final String A045 = "A045";// 給付年月實付金額
    public static final String A046 = "A046";// 核定年月核定總額
    public static final String A047 = "A047";// 核定年月實付總額
    public static final String A048 = "A048";// 核定年月補發部分金額總額
    public static final String A049 = "A049";// 核定年月事故者扣減總額
    public static final String A052 = "A052";// 多受款人可領金額
    public static final String A053 = "A053";// 多受款人扣減金額
    public static final String A054 = "A054";// 受款人實付金額
    public static final String A055 = "A055";// 勞工退休金停繳(老年)
    public static final String A059 = "A059";// 匯款匯費
    public static final String A060 = "A060";// 給付方式
    public static final String A061 = "A061";// 紓困貸款撥款金額
    public static final String A062 = "A062";// 勞貸本息截止日
    public static final String A063 = "A063";// 勞貸本金
    public static final String A064 = "A064";// 勞貸利息
    public static final String A065 = "A065";// 勞貸其他費用
    public static final String A066 = "A066";// 勞貸抵銷金額
    public static final String A067 = "A067";// 抵銷紓困貸款後實付金額
    public static final String A068 = "A068";// 勞貸未償金額
    public static final String A069 = "A069";// 投保單位墊付金額
    public static final String A070 = "A070";// 已歸墊金額
    public static final String A071 = "A071";// 尚未歸墊金額
    public static final String A072 = "A072";// 本次歸墊金額
    public static final String A073 = "A073";// 歸墊方式
    public static final String A074 = "A074";// 歸墊單位
    public static final String A075 = "A075";// 勞保條例
    public static final String A076 = "A076";// 共同具領人員
    public static final String A077 = "A077";// 受款人姓名
    public static final String A078 = "A078";// 受款人身分證號
    public static final String A079 = "A079";// 受款人出生日期
    public static final String A080 = "A080";// 受款人數
    public static final String A088 = "A088";// 眷屬或遺屬符合人數
    public static final String A089 = "A089";// 符合之遺屬姓名
    public static final String A090 = "A090";// 一次給付月數
    public static final String A092 = "A092";// 一次給付之核定金額
    public static final String A093 = "A093";// 已領年金總額
    public static final String A094 = "A094";// 最後單位名稱
    public static final String A095 = "A095";// 國勞合併申請書
    public static final String A096 = "A096";// 老年逕退
    public static final String A097 = "A097";// 身分查核年月
    public static final String A098 = "A098";// 或投保單位
    public static final String A099 = "A099";// 事故日期
    public static final String A100 = "A100";// 複檢金額
    public static final String A101 = "A101";// 非複檢必須費用
    public static final String A102 = "A102";// 實付金額
    public static final String A103 = "A103";// 投保年資計算
    public static final String A104 = "A104";// 原所得替代率
    public static final String A105 = "A105";// 展減比例
    public static final String A106 = "A106";// 條例58條之2
    public static final String A107 = "A107";// 核定年月總核付金額
    public static final String A108 = "A108";// 核定總金額
    public static final String A110 = "A110";// 加計眷屬或遺屬比率
    public static final String A111 = "A111";// 年度在學通知補正期限
    public static final String A112 = "A112";// 年度在學通知補正期限
    public static final String A113 = "A113";// 年度在學通知補正期限
    public static final String A114 = "A114";// 失能眷屬姓名
    public static final String A116 = "A116";// 遺屬年金勞保計算金額
    public static final String A117 = "A117";// 遺屬年金勞保給付金額
    public static final String A118 = "A118";// 遺遺屬年金發多個月
    public static final String A119 = "A119";// 不足3000元以3000元計算
    public static final String A120 = "A120";// 因不足3000元，發給3000元
    public static final String A121 = "A121";// 發給半數文句
    public static final String A122 = "A122";// 第65條之1
    public static final String A123 = "A123";// 每月在學通知補正期限
    public static final String A124 = "A124";// 每月通知函補送在學證明年度
    public static final String A125 = "A125";// 每月通知函補送在學證明學期
    public static final String A126 = "A126";// 每月通知函補送在學證明學期
    public static final String A127 = "A127";// 職災補償一次金金額
    public static final String A128 = "A128";// 職災補償一次金說明
    public static final String A129 = "A129";// 第64條
    public static final String A130 = "A130";// 符合規定說明（遺屬）
    public static final String A131 = "A131";// 死亡次月
    public static final String A132 = "A132";// 核定年月總實付金額
    public static final String A133 = "A133";// 核定年月最大給付年月之核定金額
    public static final String A134 = "A134";// 本次紓困抵銷金額
    public static final String A135 = "A135";// 單月抵銷紓困後實發金額
    public static final String A136 = "A136";// 首次給付年月
    public static final String A137 = "A137";// 請領勞工退休金說明
    public static final String A138 = "A138";// 本次代扣補償金額
    public static final String A139 = "A139";// 代扣補償金後之實付金額
    public static final String A140 = "A140";// 總核定金額
    public static final String A141 = "A141";// 補發金額

    public static final String A142 = "A142";// 身分證明文件補正年月
    public static final String A143 = "A143";// 本月核定合格及不合格人數
    public static final String A144 = "A144";// 合格遺屬基本資料
    public static final String A145 = "A145";// 不合格遺屬基本資料
    public static final String A146 = "A146";// 合格及不合格遺屬基本資料
    public static final String A147 = "A147";// 最大給付年月
    public static final String A148 = "A148";// 最大給付年月前一個月
    public static final String A149 = "A149";// 投保薪資分級表第一級
    public static final String A150 = "A150";// 不合格說明
    public static final String A151 = "A151";// 再婚日期
    public static final String A152 = "A152";// 遺屬死亡日期
    public static final String A153 = "A153";// 無謀生能力解釋令
    public static final String A154 = "A154";// 投保薪資比例金額
    public static final String A155 = "A155";// 外籍受益人補證明期限
    public static final String A156 = "A156";// 外籍受益人應補證明文件
    public static final String A157 = "A157";// 受益人國籍
    public static final String A158 = "A158";// 外籍遺屬姓名
    public static final String A159 = "A159";// 查核失能程度通知補正期限
    public static final String A160 = "A160";// 畢業年月 (NBSCHOOL)
    public static final String A161 = "A161";// 休學年月 (NBSCHOOL)
    public static final String A162 = "A162";// 退學年月 (NBSCHOOL)
    public static final String A163 = "A163";// 入帳對象
    public static final String A164 = "A164";// 實付金額不為 0 之最小給付年月

    public static final String L011 = "L011";// 老年差額金通知 - 首次給付年月
    public static final String L012 = "L012";// 老年差額金通知 - 給付年月
    public static final String L013 = "L013";// 老年差額金通知 - 總核定金額
    public static final String L014 = "L014";// 老年差額金通知 - 離職退保日期
    public static final String L015 = "L015";// 老年差額金通知 - 老年差額金
    public static final String L016 = "L016";// 老年差額金通知 - 老年年資(年)
    public static final String L017 = "L017";// 老年差額金通知 - 老年年資(月)
    public static final String L018 = "L018";// 老年差額金通知 - 一次平均薪資
    public static final String L019 = "L019";// 老年差額金通知 - 給付月數
    public static final String L020 = "L020";// 老年差額金通知 - 一次給付金額
    public static final String L021 = "L021";// 老年差額金通知 - 最後給付年月
    public static final String L022 = "L022";// 老年差額金通知 - 累計已領年金金額
    public static final String L023 = "L023";// 老年差額金通知 - 第1次發文日期
    public static final String L024 = "L024";// 老年差額金通知 - 第2次發文日期
    public static final String K001 = "K001";// 受益人性別
    // ]

    // BAUNACPREC - 應收帳務記錄檔
    // [
    // 回收種類
    public static final String BAUNACPREC_RECKIND_01 = "01";
    public static final String BAUNACPREC_RECKIND_02 = "02";
    public static final String BAUNACPREC_RECKIND_03 = "03";
    public static final String BAUNACPREC_RECKIND_STR_01 = "應收款";
    public static final String BAUNACPREC_RECKIND_STR_02 = "催收款";
    public static final String BAUNACPREC_RECKIND_STR_03 = "另案扣減";

    // 處理狀態
    public static final String BAUNACPREC_PROCSTAT_00 = "00";
    public static final String BAUNACPREC_PROCSTAT_99 = "99";
    public static final String BAUNACPREC_PROCSTAT_STR_00 = "待處理";
    public static final String BAUNACPREC_PROCSTAT_STR_99 = "結案";
    // ]

    // BAUNACPDTL - 應收帳務明細檔
    // [
    // 收回方式
    public static final String BAUNACPDTL_RECTYP_01 = "01";
    public static final String BAUNACPDTL_RECTYP_02 = "02";
    public static final String BAUNACPDTL_RECTYP_03 = "03";
    public static final String BAUNACPDTL_RECTYP_04 = "04";
    public static final String BAUNACPDTL_RECTYP_STR_01 = "退匯沖抵";
    public static final String BAUNACPDTL_RECTYP_STR_02 = "退回現金沖抵";
    public static final String BAUNACPDTL_RECTYP_STR_03 = "退回現金沖抵";
    public static final String BAUNACPDTL_RECTYP_STR_04 = "另案扣減";
    // ]

    // BABATCHREC - 批次作業記錄檔
    // [
    // 批次 - 處理狀態 - 0 - 尚未處理
    public static final String BABATCHREC_PROCSTAT_0 = "0";
    // 批次 - 處理狀態 - 1 - 已入排程
    public static final String BABATCHREC_PROCSTAT_1 = "1";
    // 批次 - 處理狀態 - 2 - 排程處理中
    public static final String BABATCHREC_PROCSTAT_2 = "2";
    // 批次 - 處理狀態 - 3 - 媒體資料已轉入
    public static final String BABATCHREC_PROCSTAT_3 = "3";
    // 批次 - 處理狀態 - 4 - 媒體資料回押中
    public static final String BABATCHREC_PROCSTAT_4 = "4";
    // 批次 - 處理狀態 - 5 - 媒體資料已回押
    public static final String BABATCHREC_PROCSTAT_5 = "5";
    // 批次 - 處理狀態 - 6 - 媒體資料錯誤(不回押)
    public static final String BABATCHREC_PROCSTAT_6 = "6";
    // 批次 - 處理狀態 - 7 - 媒體資料回押作業失敗
    public static final String BABATCHREC_PROCSTAT_7 = "7";
    // 批次 - 處理狀態 - 8 - 媒體資料處理作業完成(不回押)
    public static final String BABATCHREC_PROCSTAT_8 = "8";

    // 批次 - 處理狀態 - 0 - 尚未處理 (代碼+中文名稱)
    public static final String BABATCHREC_PROCSTAT_STR_0 = "0-尚未處理";
    // 批次 - 處理狀態 - 1 - 已入排程 (代碼+中文名稱)
    public static final String BABATCHREC_PROCSTAT_STR_1 = "1-已入批次排程";
    // 批次 - 處理狀態 - 2 - 排程處理中 (代碼+中文名稱)
    public static final String BABATCHREC_PROCSTAT_STR_2 = "2-批次排程處理中";
    // 批次 - 處理狀態 - 3 - 媒體資料已轉入 (代碼+中文名稱)
    public static final String BABATCHREC_PROCSTAT_STR_3 = "3-媒體資料已轉入";
    // 批次 - 處理狀態 - 4 - 媒體資料回押中 (代碼+中文名稱)
    public static final String BABATCHREC_PROCSTAT_STR_4 = "4-媒體資料回押中";
    // 批次 - 處理狀態 - 5 - 媒體資料已回押 (代碼+中文名稱)
    public static final String BABATCHREC_PROCSTAT_STR_5 = "5-媒體資料回押作業完成(已回押)";
    // 批次 - 處理狀態 - 6 - 媒體資料錯誤(不回押) (代碼+中文名稱)
    public static final String BABATCHREC_PROCSTAT_STR_6 = "6-媒體資料錯誤(不回押)";
    // 批次 - 處理狀態 - 7 - 媒體資料回押作業失敗
    public static final String BABATCHREC_PROCSTAT_STR_7 = "7-媒體資料回押作業失敗";
    // 批次 - 處理狀態 - 8 - 媒體資料回押作業失敗
    public static final String BABATCHREC_PROCSTAT_STR_8 = "8-媒體資料處理作業完成(不回押)";

    // 批次 - 出帳類別 - BL1
    public static final String BABATCHREC_TATYP_BL1 = "BL1";
    // 批次 - 出帳類別 - BLA
    public static final String BABATCHREC_TATYP_BLA = "BLA";
    // 批次 - 出帳類別 - BL1 - 金融轉帳 (代碼+中文名稱)
    public static final String BABATCHREC_TATYP_STR_BL1 = "BL1-金融轉帳";
    // 批次 - 出帳類別 - BLA - 扣押戶 (代碼+中文名稱)
    public static final String BABATCHREC_TATYP_STR_BLA = "BLA-扣押戶";

    // 批次 - 回押註記說明 - 0 - 尚未回押
    public static final String BABATCHREC_PROCFLAG_0 = "0";
    // 批次 - 回押註記說明 - 1 - 已回押核定資料
    public static final String BABATCHREC_PROCFLAG_1 = "1";
    // 批次 - 回押註記說明 - 2 - 不回押核定資料
    public static final String BABATCHREC_PROCFLAG_2 = "2";
    // 批次 - 回押註記說明 - 3 - 媒體檔案錯誤
    public static final String BABATCHREC_PROCFLAG_3 = "3";
    // 批次 - 回押註記說明 - 4 - 回押作業失敗
    public static final String BABATCHREC_PROCFLAG_4 = "4";

    // 批次 - 回押註記說明 - 0 - 尚未回押 (代碼+中文名稱)
    public static final String BABATCHREC_PROCFLAG_STR_0 = "0-尚未回押";
    // 批次 - 回押註記說明 - 1 - 已回押核定資料 (代碼+中文名稱)
    public static final String BABATCHREC_PROCFLAG_STR_1 = "1-已回押核定檔";
    // 批次 - 回押註記說明 - 2 - 不回押核定資料 (代碼+中文名稱)
    public static final String BABATCHREC_PROCFLAG_STR_2 = "2-不回押核定檔";
    // 批次 - 回押註記說明 - 3 - 媒體檔案錯誤 (代碼+中文名稱)
    public static final String BABATCHREC_PROCFLAG_STR_3 = "3-媒體檔案錯誤";
    // 批次 - 回抽註記說明 - 4 - 回押作業失敗
    public static final String BABATCHREC_PROCFLAG_STR_4 = "4-回押作業失敗";
    // ]

    // 土銀地址
    public static final String LANDBAK_NAME = "土銀台北分行";
    public static final String LANDBAK_IDNNO = "10163-6";
    public static final String LANDBAK_ADDRESS = "台北市博愛路72號";
    public static final String LANDBAK_ZIP = "10043";

    // BADAPR - 給付核定檔
    // [
    // 給付核定檔 - 計算式 - 1
    public static final String BADAPR_OLDAB_1 = "1";
    // 給付核定檔 - 計算式 - 2
    public static final String BADAPR_OLDAB_2 = "2";
    // 給付核定檔 - 計算式 - 1 (中文)
    public static final String BADAPR_OLDAB_STR_1 = "第一式";
    // 給付核定檔 - 計算式 - 2 (中文)
    public static final String BADAPR_OLDAB_STR_2 = "第二式";

    // 給付查詢 - 核定年月資料 - SQL Str
    public static final String BADAPR_PAYMENT_QUERY_ISSUPAY_DATA_SQL_VAR_IN = "入-";
    public static final String BADAPR_PAYMENT_QUERY_ISSUPAY_DATA_SQL_VAR_OUT = "退-";
    public static final String BADAPR_PAYMENT_QUERY_ISSUPAY_DATA_SQL_VAR_MOD = "改-";
    // ]

    public static final String BBDAPR_PAYMENT_QUERY_ISSUPAY_DATA_SQL_VAR_IN = "入-";
    public static final String BBDAPR_PAYMENT_QUERY_ISSUPAY_DATA_SQL_VAR_OUT = "退-";
    public static final String BBDAPR_PAYMENT_QUERY_ISSUPAY_DATA_SQL_VAR_MOD = "改-";

    // MAADMREC - 行政支援記錄檔
    // [
    // 行政支援記錄-XX函-SQL Str
    public static final String MAADMREC_LETTER_TYPE_MK_SQL_A = "發函-";
    public static final String MAADMREC_LETTER_TYPE_MK_SQL_B = "回覆-";
    public static final String MAADMREC_LETTER_TYPE_MK_SQL_C = "通知-";
    public static final String MAADMREC_LETTER_TYPE_MK_SQL_D = "催辦-";

    // 行政支援記錄檔 - 來源別 - 0
    public static final String MAADMREC_SOURCE_0 = "0";
    // 行政支援記錄檔 - 來源別 - 1
    public static final String MAADMREC_SOURCE_1 = "1";
    // 行政支援記錄檔 - 來源別 - 0 (中文)
    public static final String MAADMREC_SOURCE_STR_0 = "從SDD批次轉入";
    // 行政支援記錄檔 - 來源別 - 1 (中文)
    public static final String MAADMREC_SOURCE_STR_1 = "行政支援自行新增";
    // ]

    public static final String BXADMINS_LETTER_TYPE_MK_SQL_A = "發函-";
    public static final String BXADMINS_LETTER_TYPE_MK_SQL_B = "回覆-";
    public static final String BXADMINS_LETTER_TYPE_MK_SQL_C = "通知-";
    public static final String BXADMINS_LETTER_TYPE_MK_SQL_D = "催辦-";
    // 行政支援記錄檔 - 來源別 - 0
    public static final String BXADMINS_SOURCE_0 = "0";
    // 行政支援記錄檔 - 來源別 - 1
    public static final String BXADMINS_SOURCE_1 = "1";
    // 行政支援記錄檔 - 來源別 - 0 (中文)
    public static final String BXADMINS_SOURCE_STR_0 = "從SDD批次轉入";
    // 行政支援記錄檔 - 來源別 - 1 (中文)
    public static final String BXADMINS_SOURCE_STR_1 = "行政支援自行新增";
    
    // BAGIVEDTL - 給付入帳媒體明細錄檔
    // [
    // 批次 - 處理狀況
    public static final String BAGIVEDTL_STAT2_00 = "00";
    public static final String BAGIVEDTL_STAT2_01 = "01";
    public static final String BAGIVEDTL_STAT2_02 = "02";
    public static final String BAGIVEDTL_STAT2_03 = "03";
    public static final String BAGIVEDTL_STAT2_04 = "04";
    public static final String BAGIVEDTL_STAT2_05 = "05";
    public static final String BAGIVEDTL_STAT2_06 = "06";
    public static final String BAGIVEDTL_STAT2_07 = "07";
    public static final String BAGIVEDTL_STAT2_08 = "08";
    public static final String BAGIVEDTL_STAT2_10 = "10";
    public static final String BAGIVEDTL_STAT2_30 = "30";
    public static final String BAGIVEDTL_STAT2_40 = "40";
    public static final String BAGIVEDTL_STAT2_55 = "55";
    public static final String BAGIVEDTL_STAT2_99 = "99";
    // 處理狀況 - 00-扣帳成功
    public static final String BAGIVEDTL_STAT2_STR_00 = "00-扣帳成功";
    // 處理狀況- 01-帳號錯誤
    public static final String BAGIVEDTL_STAT2_STR_01 = "01-帳號錯誤";
    // 處理狀況 - 02-帳號與請領人不符
    public static final String BAGIVEDTL_STAT2_STR_02 = "02-帳號與請領人不符";
    // 處理狀況 - 03-行庫代號與帳號不符
    public static final String BAGIVEDTL_STAT2_STR_03 = "03-行庫代號與帳號不符";
    // 處理狀況 - 04-止付
    public static final String BAGIVEDTL_STAT2_STR_04 = "04-止付";
    // 處理狀況 - 05-無此帳號
    public static final String BAGIVEDTL_STAT2_STR_05 = "05-無此帳號";
    // 處理狀況 - 06-終止及靜止戶或拒絕往來戶
    public static final String BAGIVEDTL_STAT2_STR_06 = "06-終止及靜止戶或拒絕往來戶";
    // 處理狀況 - 07-無法投遞
    public static final String BAGIVEDTL_STAT2_STR_07 = "07-無法投遞";
    // 處理狀況 - 08-招領逾期未領
    public static final String BAGIVEDTL_STAT2_STR_08 = "08-招領逾期未領";
    // 處理狀況 - 10-姓名不符
    public static final String BAGIVEDTL_STAT2_STR_10 = "10-姓名不符";
    // 處理狀況 - 30-郵局開戶時身分證有誤
    public static final String BAGIVEDTL_STAT2_STR_30 = "30-郵局開戶時身分證有誤";
    // 處理狀況 - 40-懸帳戶
    public static final String BAGIVEDTL_STAT2_STR_40 = "40-懸帳戶";
    // 處理狀況 - 55-颱風日放假
    public static final String BAGIVEDTL_STAT2_STR_55 = "55-颱風日放假";
    // 處理狀況 - 99-其他錯誤(自行退匯及撤銷)
    public static final String BAGIVEDTL_STAT2_STR_99 = "99-其他錯誤(自行退匯及撤銷)";
    // ]

    // 另案扣減照會單_ BALP0D040L 待收回給付種類 參數[
    public static final String BALP0D040L_GIVE_PAY_KIND_L = "老年年金給付";// 老年年金給付
    public static final String BALP0D040L_GIVE_PAY_KIND_K = "失能年金給付";// 失能年金給付
    public static final String BALP0D040L_GIVE_PAY_KIND_S = "遺屬年金給付";// 遺屬年金給付

    public static final String BALP0D040L_GIVE_DEPT_NAME_L = "生老給付科";// 生老給付科
    public static final String BALP0D040L_GIVE_DEPT_NAME_K = "傷殘給付科";// 傷殘給付科
    public static final String BALP0D040L_GIVE_DEPT_NAME_S = "死亡給付科";// 死亡給付科

    // BAAPPEXPAND - 給付延伸主檔
    // [
    // 傷病分類 - 1 - 職業傷害
    public static final String BAAPPEXPAND_EVTYP_1 = "1";
    // 傷病分類 - 2 - 職業病
    public static final String BAAPPEXPAND_EVTYP_2 = "2";
    // 傷病分類 - 3 - 普通傷害
    public static final String BAAPPEXPAND_EVTYP_3 = "3";
    // 傷病分類 - 4 - 普通疾病
    public static final String BAAPPEXPAND_EVTYP_4 = "4";
    // 傷病分類 - 1 - 職業傷害 (中文)
    public static final String BAAPPEXPAND_EVTYP_STR_1 = "職業傷害";
    // 傷病分類 - 2 - 職業病 (中文)
    public static final String BAAPPEXPAND_EVTYP_STR_2 = "職業病";
    // 傷病分類 - 3 - 普通傷害 (中文)
    public static final String BAAPPEXPAND_EVTYP_STR_3 = "普通傷害";
    // 傷病分類 - 4 - 普通疾病 (中文)
    public static final String BAAPPEXPAND_EVTYP_STR_4 = "普通疾病";
    // 傷病分類 - 1 - 職業傷害 (代碼+中文名稱)
    public static final String BAAPPEXPAND_EVTYP_CODESTR_1 = "1-職業傷害";
    // 傷病分類 - 2 - 職業病 (代碼+中文名稱)
    public static final String BAAPPEXPAND_EVTYP_CODESTR_2 = "2-職業病";
    // 傷病分類 - 3 - 普通傷害 (代碼+中文名稱)
    public static final String BAAPPEXPAND_EVTYP_CODESTR_3 = "3-普通傷害";
    // 傷病分類 - 4 - 普通疾病 (代碼+中文名稱)
    public static final String BAAPPEXPAND_EVTYP_CODESTR_4 = "4-普通疾病";
    // 年金請領資格 - 1 - 終身無工作能力項目
    public static final String BAAPPEXPAND_DISQUALMK_1 = "1";
    // 年金請領資格 - 2 - 經個別化專業評估
    public static final String BAAPPEXPAND_DISQUALMK_2 = "2";
    // 年金請領資格 - 1 - 終身無工作能力項目 (中文)
    public static final String BAAPPEXPAND_DISQUALMK_STR_1 = "終身無工作能力項目";
    // 年金請領資格 - 2 - 經個別化專業評估 (中文)
    public static final String BAAPPEXPAND_DISQUALMK_STR_2 = "經個別化專業評估";
    // 年金請領資格 - 1 - 終身無工作能力項目 (代碼+中文名稱)
    public static final String BAAPPEXPAND_DISQUALMK_CODESTR_1 = "1-終身無工作能力項目";
    // 年金請領資格 - 2 - 經個別化專業評估 (代碼+中文名稱)
    public static final String BAAPPEXPAND_DISQUALMK_CODESTR_2 = "2-經個別化專業評估";

    // 符合第63條之1第3項註記 - Y - 合格
    public static final String BAAPPEXPAND_FAMAPPMK_Y = "Y";
    // 符合第63條之1第3項註記 - N - 不合格
    public static final String BAAPPEXPAND_FAMAPPMK_N = "N";
    // 符合第63條之1第3項註記 - Y - 合格
    public static final String BAAPPEXPAND_FAMAPPMK_STR_Y = "合格";
    // 符合第63條之1第3項註記 - N - 不合格
    public static final String BAAPPEXPAND_FAMAPPMK_STR_N = "不合格";

    // ]

    // BABCML7 - 勞保複檢費用主檔
    // [
    // 處理狀態 - 00 - 受理
    public static final String BABCML7_PROCSTAT_00 = "00";
    // 處理狀態 - 01 - 更正
    public static final String BABCML7_PROCSTAT_01 = "01";
    // 處理狀態 - 10 - 已編審待審核
    public static final String BABCML7_PROCSTAT_10 = "10";
    // 處理狀態 - 11 - 複核決行退件
    public static final String BABCML7_PROCSTAT_11 = "11";
    // 處理狀態 - 12 - 改核
    public static final String BABCML7_PROCSTAT_12 = "12";
    // 處理狀態 - 20 - 審核
    public static final String BABCML7_PROCSTAT_20 = "20";
    // 處理狀態 - 30 - 複核
    public static final String BABCML7_PROCSTAT_30 = "30";
    // 處理狀態 - 40 - 決行
    public static final String BABCML7_PROCSTAT_40 = "40";
    // 處理狀態 - 50 - 核定
    public static final String BABCML7_PROCSTAT_50 = "50";
    // 處理狀態 - 90 - 結案
    public static final String BABCML7_PROCSTAT_90 = "90";
    // 處理狀態 - 99 - 註銷
    public static final String BABCML7_PROCSTAT_99 = "99";

    // 處理狀態 - 00 - 受理 (中文)
    public static final String BABCML7_PROCSTAT_STR_00 = "受理";
    // 處理狀態 - 01 - 更正 (中文)
    public static final String BABCML7_PROCSTAT_STR_01 = "更正";
    // 處理狀態 - 10 - 已編審待審核 (中文)
    public static final String BABCML7_PROCSTAT_STR_10 = "已編審待審核";
    // 處理狀態 - 11 - 複核決行退件 (中文)
    public static final String BABCML7_PROCSTAT_STR_11 = "複核決行退件";
    // 處理狀態 - 12 - 改核 (中文)
    public static final String BABCML7_PROCSTAT_STR_12 = "改核";
    // 處理狀態 - 20 - 審核 (中文)
    public static final String BABCML7_PROCSTAT_STR_20 = "審核";
    // 處理狀態 - 30 - 複核 (中文)
    public static final String BABCML7_PROCSTAT_STR_30 = "複核";
    // 處理狀態 - 40 - 決行 (中文)
    public static final String BABCML7_PROCSTAT_STR_40 = "決行";
    // 處理狀態 - 50 - 核定 (中文)
    public static final String BABCML7_PROCSTAT_STR_50 = "核定";
    // 處理狀態 - 90 - 結案 (中文)
    public static final String BABCML7_PROCSTAT_STR_90 = "結案";
    // 處理狀態 - 99 - 註銷 (中文)
    public static final String BABCML7_PROCSTAT_STR_99 = "註銷";

    // 複檢原因 - 1 - REASCODE
    public static final String BABCML7_REASCODE_1 = "1";
    // 複檢原因 - 2 - REASCODE
    public static final String BABCML7_REASCODE_2 = "2";
    // 複檢原因 - 3 - REASCODE
    public static final String BABCML7_REASCODE_3 = "3";
    // 複檢原因 - 4 - REASCODE
    public static final String BABCML7_REASCODE_4 = "4";
    // 複檢原因 - 5 - REASCODE
    public static final String BABCML7_REASCODE_5 = "5";
    // 複檢原因 - 1 - REASCODE (代碼 + 中文)
    public static final String BABCML7_REASCODE_CODESTR_1 = "1-依第56條第2項規定查核失能程度";
    // 複檢原因 - 2 - REASCODE (代碼 + 中文)
    public static final String BABCML7_REASCODE_CODESTR_2 = "2-經檢舉或詐領給付";
    // 複檢原因 - 3 - REASCODE (代碼 + 中文)
    public static final String BABCML7_REASCODE_CODESTR_3 = "3-經本局特約醫師審查批示";
    // 複檢原因 - 4 - REASCODE (代碼 + 中文)
    public static final String BABCML7_REASCODE_CODESTR_4 = "4-行政救濟需重新查核失能程度";
    // 複檢原因 - 5 - REASCODE (代碼 + 中文)
    public static final String BABCML7_REASCODE_CODESTR_5 = "5-其他";

    // 初核狀況 - 1 - CHKSTAT
    public static final String BABCML7_CHKSTAT_1 = "1";
    // 初核狀況 - 2 - CHKSTAT
    public static final String BABCML7_CHKSTAT_2 = "2";
    // 初核狀況 - 1 - CHKSTAT (代碼 + 中文)
    public static final String BABCML7_CHKSTAT_CODESTR_1 = "1-給付";
    // 初核狀況 - 2 - CHKSTAT (代碼 + 中文)
    public static final String BABCML7_CHKSTAT_CODESTR_2 = "2-不給付";

    // 複核狀況 - 1 - RECHKSTAT
    public static final String BABCML7_RECHKSTAT_1 = "1";
    // 複核狀況 - 2 - RECHKSTAT
    public static final String BABCML7_RECHKSTAT_2 = "2";
    // 複核狀況 - 1 - RECHKSTAT
    public static final String BABCML7_RECHKSTAT_3 = "3";
    // 複核狀況 - 2 - RECHKSTAT
    public static final String BABCML7_RECHKSTAT_N = "N";
    // 複核狀況 - 1 - RECHKSTAT (代碼 + 中文)
    public static final String BABCML7_RECHKSTAT_CODESTR_1 = "1-複核中";
    // 複核狀況 - 2 - RECHKSTAT (代碼 + 中文)
    public static final String BABCML7_RECHKSTAT_CODESTR_2 = "2-決行";
    // 複核狀況 - 1 - RECHKSTAT (代碼 + 中文)
    public static final String BABCML7_RECHKSTAT_CODESTR_3 = "3-代決行";
    // 複核狀況 - 2 - RECHKSTAT (代碼 + 中文)
    public static final String BABCML7_RECHKSTAT_CODESTR_N = "N-退件";

    // 遺屬計算金額相關常數
    public static final int SCALE_SENI = 100;// BIGDECIMAL 除法小數位數 (計算年資用)
    public static final int SCALE_SURVIVOR_PERCENT = 10;// BIGDECIMAL 除法小數位數 (遺屬分錢比率用)
    public static final BigDecimal BIGDECIMAL_CONSTANT_100 = new BigDecimal(100);
    public static final BigDecimal BIGDECIMAL_CONSTANT_1000 = new BigDecimal(1000);
    public static final BigDecimal BIGDECIMAL_CONSTANT_10000 = new BigDecimal(10000);
    public static final BigDecimal BIGDECIMAL_CONSTANT_100000 = new BigDecimal(100000);
    public static final BigDecimal BEN_SUBSIDY_0 = BigDecimal.ZERO;// 遺屬補助 0%
    public static final BigDecimal BEN_SUBSIDY_1 = (new BigDecimal(25)).divide(BIGDECIMAL_CONSTANT_100);// 遺屬補助 每1人加發25%
    public static final BigDecimal BEN_SUBSIDY_2 = (new BigDecimal(50)).divide(BIGDECIMAL_CONSTANT_100);// 遺屬補助 最多加50%
    // ]

    // public static final String RPT_PATH = "/nps/ba_rpt/";
    // public static final String RPT_PATH = "/aplog/ba/ba_rpt/";// weblogic
    // public static final String RPT_PATH = "C:\\nps/ba_rpt/"; //LocalTest
    // public static final String RPT_PATH = "/apdata/ba/ba_rpt/"; //hinet weblogic
    // public static final String RPT_PATH_RPT29 = "/apdata/ba/ba_rpt/rpt29"; //hinet weblogic

    // public static final String RPT_PATH_FINAL_RPT = "/nps/ba_rpt/final_rpt/";//
    // public static final String RPT_PATH_FINAL_RPT = "/aplog/ba/ba_rpt/final_rpt/";// weblogic
    // public static final String RPT_PATH_FINAL_RPT = "/apdata/ba/ba_rpt/final_rpt/"; //hinet weblogic

    public static final String ORG_CHANGE_DATE = "20140210";// 勞保局組改日期

    public static final String FORWARD_SCHEDULE_SUCCESS = "scheduleSuccess";
    public static final String FORWARD_SCHEDULE_FAIL = "scheduleFail";
    public static final String FOWARD_DOWN_FILE = "downFile";
    public static final String FOWARD_QRY_PROGRESS = "qryProgress";
    public static final String FOWARD_QRY_PROGRESS_DTL = "qryProgressDtl";
    public static final String FORWARD_SUCCESS_UPLOAD = "successUpload";
    public static final String FORWARD_FAIL_UPLOAD = "failUpload";

    // 其他報表 - 轉呆帳核定清單 - 查詢FORM
    public static final String OTHER_RPT08_FORM = "OtherRpt08Form";
    // 其他報表 - 轉呆帳核定清單 - 查詢FORM
    public static final String OTHER_RPT08_DETAIL_FORM = "OtherRpt08DetailForm";
    // 批次處理 - 線上產製媒體檔作業- 老年年金線上產製媒體檔作業- 執行頁FORM
    public static final String OLD_MEDIA_ONLINE_FORM = "OldMediaOnlineForm";
    // 批次處理 - 線上產製媒體檔作業- 遺屬年金線上產製媒體檔作業- 執行頁FORM
    public static final String SURVIVOR_MEDIA_ONLINE_FORM = "SurvivorMediaOnlineForm";
    // 批次處理 - 線上產製媒體檔作業- 失能年金線上產製媒體檔作業 - 執行頁FORM
    public static final String DISABLED_MEDIA_ONLINE_FORM = "DisabledMediaOnlineForm";
    // 批次處理 - 批次產製媒體檔作業- 老年年金批次產製媒體檔作業- 執行頁FORM
    public static final String OLD_MEDIA_BATCH_FORM = "OldMediaBatchForm";
    // 批次處理 - 批次產製媒體檔作業- 遺屬年金批次產製媒體檔作業- 執行頁FORM
    public static final String SURVIVOR_MEDIA_BATCH_FORM = "SurvivorMediaBatchForm";
    // 批次處理 - 批次產製媒體檔作業 - 失能年金批次產製媒體檔作業- 執行頁FORM
    public static final String DISABLED_MEDIA_BATCH_FORM = "DisabledMediaBatchForm";

    // 批次處理 - 線上媒體檔下載 - 查詢資料(老年)
    public static final String OLD_MEDIA_ONLINE_CASE = "OldMediaOnlineCase";
    // 批次處理 - 線上媒體檔下載 - 查詢結果(老年)
    public static final String OLD_MEDIA_ONLINE_CASE_LIST = "OldMediaOnlineCaseList";
    // 批次處理 - 線上媒體檔進度查詢- 查詢結果(老年)
    public static final String OLD_MEDIA_ONLINE_PROGRESS_CASE_LIST = "OldMediaOnlineProgressCaseList";
    // 批次處理 - 線上媒體檔進度查詢明細 - 查詢結果(老年)
    public static final String OLD_MEDIA_ONLINE_PROGRESS_DTL_CASE_LIST = "OldMediaOnlineProgressDtlCaseList";
    // 批次處理 - 線上媒體檔下載 - 查詢資料(遺屬)
    public static final String SURVIVOR_MEDIA_ONLINE_CASE = "SurvivorMediaOnlineCase";
    // 批次處理 - 線上媒體檔下載 - 查詢結果(遺屬)
    public static final String SURVIVOR_MEDIA_ONLINE_CASE_LIST = "SurvivorMediaOnlineCaseList";
    // 批次處理 - 線上媒體檔進度查詢- 查詢結果(老年)
    public static final String SURVIVOR_MEDIA_ONLINE_PROGRESS_CASE_LIST = "SurvivorMediaOnlineProgressCaseList";
    // 批次處理 - 線上媒體檔進度查詢明細 - 查詢結果(老年)
    public static final String SURVIVOR_MEDIA_ONLINE_PROGRESS_DTL_CASE_LIST = "SurvivorMediaOnlineProgressDtlCaseList";
    // 批次處理 - 線上媒體檔下載 - 查詢資料(失能)
    public static final String DISABLED_MEDIA_ONLINE_CASE = "DisabledMediaOnlineCase";
    // 批次處理 - 線上媒體檔下載 - 查詢結果(失能)
    public static final String DISABLED_MEDIA_ONLINE_CASE_LIST = "DisabledMediaOnlineCaseList";
    // 批次處理 - 線上媒體檔進度查詢- 查詢結果(失能)
    public static final String DISABLED_MEDIA_ONLINE_PROGRESS_CASE_LIST = "DisabledMediaOnlineProgressCaseList";
    // 批次處理 - 線上媒體檔進度查詢明細 - 查詢結果(失能)
    public static final String DISABLED_MEDIA_ONLINE_PROGRESS_DTL_CASE_LIST = "DisabledMediaOnlineProgressDtlCaseList";
    // 批次處理 - 批次程式查詢作業 - 查詢結果
    public static final String QRY_PROCEDURE_PROGRESS_CASE_LIST = "QryProcedureProgressCaseList";
    // 批次處理 - 年金統計執行作業  - 查詢結果
    public static final String QRY_EXEC_STATISTICS_DATA_LIST = "QryExecStatisticsDataList";
    // 批次處理 - 年金統計執行作業  - 資料明細結果
    public static final String QRY_EXEC_STATISTICS_DATA_DTL_LIST = "QryExecStatisticsDtlDataList";

    // 線上產製媒體檔相關常數
    // public static final String PROCTYPE_MEDIA = "1"; //出媒體的處理類型
    public static final String STATUS_WAITING = "W";// 出媒體的處理狀態-等待中
    public static final String STATUS_RUNNING = "R";// 出媒體的處理狀態-執行中
    public static final String STATUS_END = "E";// 出媒體的處理狀態-結束
    public static final String STATUS_ERROR = "N";// 出媒體的狀態-發生錯誤
    public static final String STATUS_WAITING_STR = "等待中";// 出媒體的處理狀態-等待中
    public static final String STATUS_RUNNING_STR = "執行中";// 出媒體的處理狀態-執行中
    public static final String STATUS_END_STR = "執行結束";// 出媒體的處理狀態-結束
    public static final String STATUS_ERROR_STR = "發生錯誤";// 出媒體的狀態-發生錯誤
    public static final String PAYSEQNO_1 = "1";// 除了失能年金36案以外的CASE PAYSEQNO皆為1
    public static final String PAYSEQNO_2 = "2";// FOR 失能年金36案使用

    // 修改FORTIFY SQL
    public static final String OPERATOR_LESS_THAN = "lt";
    public static final String OPERATOR_LESS_EQUAL = "le";
    public static final String OPERATOR_EQUAL = "eq";
    public static final String OPERATOR_GREATER_EQUAL = "ge";
    public static final String OPERATOR_GREATER_THAN = "gt";
    public static final String OPERATOR_NOT_EQUAL = "ne";
    public static final String OPERATOR_IN = "in";
    public static final String OPERATOR_NOT_IN = "notIn";
    public static final String OPERATOR_IS_NULL = "isNull";
    public static final String OPERATOR_IS_NOT_NULL = "isNotNull";
}
