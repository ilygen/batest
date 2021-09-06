package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import tw.gov.bli.common.annotation.LogField;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 給付主檔
 * 
 * @author Rickychi
 */
@Table("BAAPPBASE")
public class Baappbase implements Serializable {
    @PkeyField("BAAPPBASEID")
    private BigDecimal baappbaseId;// 資料列編號

    @LogField("APNO")
    private String apNo;// 受理編號

    @LogField("SEQNO")
    private String seqNo;// 序號

    @LogField("IMK")
    private String imk;// 保險別

    @LogField("APITEM")
    private String apItem;// 申請項目

    @LogField("PAYKIND")
    private String payKind;// 給付種類

    @LogField("APPDATE")
    private String appDate;// 申請日期

    @LogField("CASETYP")
    private String caseTyp;// 案件類別

    @LogField("MAPNO")
    private String mapNo;// 相關受理編號

    @LogField("MAPROOTMK")
    private String mapRootMk;// 相關受理編號註記

    @LogField("COMBAPMARK")
    private String combapMark;// 國勞合併申請註記

    @LogField("CASEMK")
    private String caseMk;// 案件註記

    @LogField("PROCSTAT")
    private String procStat;// 處理狀態
    
    @LogField("PROCSTATMK")
    private String procStatMk;// 判斷此案件的處理狀態 (PROCSTAT) 是否小於20或大於等於20

    @LogField("ACCEPTMK")
    private String acceptMk;// 合格註記

    @LogField("MANCHKMK")
    private String manchkMk;// 人工審核結果

    @LogField("CHGNOTE")
    private String chgNote;// 更正原因

    @LogField("EXESTAT")
    private String exeStat;// 決行狀況

    @LogField("APUBNO")
    private String apUbno;// 申請單位保險證號

    @LogField("APUBNOCK")
    private String apubnock;// 申請單位保險證號檢查碼

    @LogField("LSUBNO")
    private String lsUbno;// 最後單位保險證號

    @LogField("LSUBNOCK")
    private String lsUbnoCk;// 最後單位保險證號檢查碼

    @LogField("EVTIDS")
    private String evtIds;// 事故者社福識別碼

    @LogField("EVTIDNNO")
    private String evtIdnNo;// 事故者身分證號

    @LogField("EVTNAME")
    private String evtName;// 事故者姓名

    @LogField("EVTBRDATE")
    private String evtBrDate;// 事故者出生日期

    @LogField("EVTSEX")
    private String evtSex;// 事故者性別

    @LogField("EVTNATIONTPE")
    private String evtNationTpe;// 事故者國籍別

    @LogField("EVTNATIONCODE")
    private String evtNationCode;// 事故者國籍

    @LogField("EVTIDENT")
    private String evtIdent;// 事故者身分別

    @LogField("EVTJOBDATE")
    private String evtJobDate;// 事故者離職日期

    @LogField("EVTDATE")
    private String evtDate;// 事故日期

    @LogField("EVTDIEDATE")
    private String evtDieDate;// 事故者死亡日期

    @LogField("EVTAGE")
    private String evtAge;// 事故者申請時年齡

    @LogField("BENIDS")
    private String benIds;// 受益人社福識別碼

    @LogField("BENIDNNO")
    private String benIdnNo;// 受益人身分證號

    @LogField("BENNAME")
    private String benName;// 受益人姓名

    @LogField("BENBRDATE")
    private String benBrDate;// 受益人出生日期

    @LogField("BENSEX")
    private String benSex;// 受益人性別

    @LogField("BENNATIONTYP")
    private String benNationTyp;// 受益人國籍別

    @LogField("BENNATIONCODE")
    private String benNationCode;// 受益人國籍

    @LogField("BENEVTREL")
    private String benEvtRel;// 受益人與事故者關係

    @LogField("BENDIEDATE")
    private String benDieDate;// 受益人死亡日期

    @LogField("BENPAYMK")
    private String benPayMk;// 受益人領取狀態註記

    @LogField("IDNCHKYM")
    private String idnChkYm;// 身分查核年月

    @LogField("IDNCHKNOTE")
    private String idnChkNote;// 身分查核註記

    @LogField("TEL1")
    private String tel1;// 電話1

    @LogField("TEL2")
    private String tel2;// 電話2

    @LogField("COMMTYP")
    private String commTyp;// 通訊地址別

    @LogField("COMMZIP")
    private String commZip;// 通訊郵遞區號

    @LogField("COMMADDR")
    private String commAddr;// 通訊地址

    @LogField("PAYTYP")
    private String payTyp;// 給付方式

    @LogField("BANKNAME")
    private String bankName;// 金融機構名稱

    @LogField("PAYBANKID")
    private String payBankId;// 金融機構總代號

    @LogField("BRANCHID")
    private String branchId;// 分支代號

    @LogField("PAYEEACC")
    private String payEeacc;// 銀行帳號

    @LogField("CHKPAYBANKID")
    private String chkPayBankId;// 金融機構總代號 複驗

    @LogField("CHKBRANCHID")
    private String chkBranchId;// 分支代號 複驗

    @LogField("CHKPAYEEACC")
    private String chkPayEeacc;// 銀行帳號 複驗

    @LogField("ACCIDN")
    private String accIdn;// 戶名IDN

    @LogField("ACCNAME")
    private String accName;// 戶名

    @LogField("ACCREL")
    private String accRel;// 戶名與受益人關係

    @LogField("MITRATE")
    private BigDecimal mitRate;// 匯款匯費

    @LogField("GRDIDNNO")
    private String grdIdnNo;// 法定代理人身分證號

    @LogField("GRDNAME")
    private String grdName;// 法定代理人姓名

    @LogField("GREBRDATE")
    private String grdBrDate;// 法定代理人出生日期

    @LogField("ASSIGNIDNNO")
    private String assignIdnNo;// 受委託人身分證號

    @LogField("ASSIGNNAME")
    private String assignName;// 受委託人姓名

    @LogField("ASSIGNBRDATE")
    private String assignBrDate;// 受委託人出生日期

    @LogField("ISSUYM")
    private String issuYm;// 核定年月

    @LogField("PAYYM")
    private String payYm;// 給付年月

    @LogField("ISSUEAMT")
    private BigDecimal issueAmt;// 核定金額

    @LogField("ONCEPAYMK")
    private String oncePayMk;// 一次給付符合註記

    @LogField("OLDSENIAB")
    private String oldSeniab;// 一次請領之年資採計方式

    @LogField("MUSTISSUEAMT")
    private BigDecimal mustIssueAmt;// 可領金額

    @LogField("PAYDATE")
    private String payDate;// 首次核定日期

    @LogField("PAYYMS")
    private String payYms;// 首次發放起月

    @LogField("PAYYME")
    private String payYme;// 首次發放迄月

    @LogField("PAUSEYM")
    private String pauseYm;// 暫停續發年月

    @LogField("UNPAUSEYM")
    private String unPauseYm;// 恢復續發年月

    @LogField("NOTIFYFORM")
    private String notifyForm;// 核定通知書格式

    @LogField("LOANMK")
    private String loanMk;// 不須抵銷紓困貸款註記

    @LogField("MEXCLVL")
    private String mexcLvl;// 應決行層級

    @LogField("REALEXCLVL")
    private String realexcLvl;// 實際決行層級

    @LogField("CHKDATE")
    private String chkDate;// 審核日期

    @LogField("CHKMAN")
    private String chkMan;// 審核人員

    @LogField("RECHKDATE")
    private String rechkDate;// 複核日期

    @LogField("RECHKMAN")
    private String rechkMan;// 複核人員

    @LogField("EXEDATE")
    private String exeDate;// 決行日期

    @LogField("EXEMAN")
    private String exeMan;// 決行人員

    @LogField("ARCDATE")
    private String arcDate;// 歸檔日期

    @LogField("ARCPG")
    private String arcPg;// 歸檔頁次

    @LogField("CLOSEDATE")
    private String closeDate;// 結案日期

    @LogField("CLOSECAUSE")
    private String closeCause;// 結案原因

    @LogField("PROMOTEUSER")
    private String promoteUser;// 承辦者代號

    @LogField("CRTUSER")
    private String crtUser;// 新增者代號

    @LogField("CRTTIME")
    private String crtTime;// 新增日期時間

    @LogField("UPDUSER")
    private String updUser;// 異動者代號

    @LogField("UPDTIME")
    private String updTime;// 異動日期時間

    @LogField("DUPEIDNNOMK")
    private String dupeIdnNoMk;// 身分證重號註記

    @LogField("EVTEXPIREDATE")
    private String evtExpireDate; // 事故者屆齡日期

    @LogField("EVTELIGIBLEDATE")
    private String evtEligibleDate; // 事故者符合日期

    @LogField("EVTMISSINGDATE")
    private String evtMissingDate; // 事故者失蹤日期

    @LogField("LSINSMAMT")
    private BigDecimal lsInsmAmt; // 投保金額

    @LogField("LSCHKMK")
    private String lsChkMk;// 勞貸戶註記

    @LogField("LSCOUNT")
    private BigDecimal lsCount;// 勞貸戶筆數

    @LogField("LNCHKMK")
    private String lnChkMk;// 紓困貸款呆帳債務人註記

    @LogField("DABAPNO")
    private String dabApNo;// 已領失能年金受理編號

    @LogField("DABANNUAMT")
    private BigDecimal dabAnnuAmt;// 已領失能年金金額

    @LogField("RESENIMK")
    private String reSeniMk;// 保留年資

    @LogField("OFFINSURDATE")
    private String offInsurDate;// 轉公保日期

    @LogField("LAWRETIREDATE")
    private String lawRetireDate;// 依法退休日期

    @LogField("EVTAPPNAME")
    private String evtAppName;// 事故者申請時姓名

    @LogField("CUTAMT")
    private BigDecimal cutAmt;// 扣減/補償總金額

    @LogField("ACCSEQNO")
    private String accSeqNo;// 被共同具領之受款人員序號

    @LogField("BENMARRMK")
    private String benMarrMk;// 婚姻狀況

    @LogField("CHGMK")
    private String chgMk;// 更正註記

    @LogField("PAYAMTS")
    private BigDecimal payAmts;// 首次核定總金額

    @LogField("EVTAPPIDNNO")
    private String evtAppIdnNo;// 事故者申請時身分證號

    @LogField("EVTAPPBRDATE")
    private String evtAppBrDate;// 事故者申請時出生日期

    @LogField("OLDAPLDPT")
    private String oldAplDpt;// 申請代算單位

    @LogField("CHOICEYM")
    private String choiceYm; // 擇領起月

    @LogField("MOBILEPHONE")
    private String mobilePhone; // 手機複驗

    @LogField("INTERVALMONTH")
    private String interValMonth;// 間隔月份

    @LogField("CLOSECAUSECODE")
    private String closeCauseCode;// 結案原因(代碼)

    @LogField("SPECIALACC")
    private String specialAcc;// 專戶

    @LogField("SPEACCDATE")
    private String speAccDate;// 設定專戶日期

    @LogField("CHECKIN")
    private String checkin; // 來源別(1:個人申辦  2:單位申辦)

    @LogField("MARGINAMT")
    private BigDecimal marginAmt;// 老年差額金
    private String evtJobAge; // 事故年齡
    private BigDecimal samTY; // 同單位年資(年)
    private BigDecimal samTD; // 同單位年資(日)
    private BigDecimal onceTY; // 一次給付年資
    private BigDecimal oncePayM; // 一次給付月數
    private BigDecimal onceIssueAmt; // 一次給付金額

    private String payBankIdBranchId;

    // 報表29使用 Excel
    private String payType; // 給付別
    private String prpNo; // 初核人員

    private String unqualifiedCause; // 不合格原因
    private String issuNotifyingMk; // 寄發核定通知書

    // 失能/遺屬年金專用
    // [
    // private String prfmlyMk;// 有無前一順位親屬
    // private String monIncomeMk;// 每月工作收入註記
    // private BigDecimal monIncome;// 每月工作收入
    // private String studMk;// 在學
    // private String marryDate;// 結婚日期
    // private String digamyDate;// 再婚日期
    // private String divorceDate;// 離婚日期
    // private String abanApply;// 放棄請領
    // private String abanapDate;// 放棄請領起始日期
    // private String evTyp;// 傷病分類
    // private String evCode;// 傷病原因
    // private String criInPart1;// 受傷部位1
    // private String criInPart2;// 受傷部位2
    // private String criInPart3;// 受傷部位3
    // private String criMedium;// 媒介物
    // private String criInjCl1;// 失能等級1
    // private String criInjCl2;// 失能等級2
    // private String criInjCl3;// 失能等級3
    // private String criInJdp1;// 失能項目1
    // private String criInJdp2;// 失能項目2
    // private String criInJdp3;// 失能項目3
    // private String criInJdp4;// 失能項目4
    // private String criInJdp5;// 失能項目5
    // private String criInJdp6;// 失能項目6
    // private String criInJdp7;// 失能項目7
    // private String criInJdp8;// 失能項目8
    // private String criInJdp9;// 失能項目9
    // private String criInJdp10;// 失能項目10
    // private String criInIssul;// 核定等級
    // private String criInjNme1;// 國際疾病代碼1
    // private String criInjNme2;// 國際疾病代碼2
    // private String criInjNme3;// 國際疾病代碼3
    // private String criInjNme4;// 國際疾病代碼4
    // private String raiseChild;// 扶養子女
    // private String rehcMk;// 重新查核失能程度註記
    // private String rehcYm;// 重新查核失能程度年月
    // private String hosId;// 醫療院所代碼
    // private String doctorName1;// 醫師姓名1
    // private String doctorName2;// 醫師姓名2
    // private String handIcap;// 領有重度以上身心障礙手冊或證明
    // private String cancelMk;// 撤銷註記
    // private String cancelYm;// 撤銷請領年月
    // private String interDictMk;// 受禁治產宣告註記
    // private String interDictDate;// 受禁治產宣告日期
    // private String relatChgDate;// 親屬關係變動日期
    // private String adoPtnMk;// 收養未滿註記
    // private String judgeDate;// 判決日期
    // private String benMissingSdate;// 受益人失蹤期間(起)
    // private String benMissingEdate;// 受益人失蹤期間(迄)
    // private String prisonSdate;// 監管期間(起)
    // private String prisonEdate;// 監管期間(迄)
    // ]

    // Field not in BAAPPBASE
    // [
    private String payKindName; // 給付種類 - 中文說明
    private String maxPayYm; // 給付年月起
    private String minPayYm; // 給付年月迄
    private BigDecimal aplPayAmt; // 實付總額
    private BigDecimal adjustAmt; // 調整金額
    private BigDecimal otherAmt; // 扣減
    private String veriSeq; // 版次
    private BigDecimal nitrmY; // 投保年資(X年X月-年)
    private BigDecimal nitrmM; // 投保年資(X年X月-月)
    private BigDecimal itrmY; // 投保年資(X年X日-年)
    private BigDecimal itrmD; // 投保年資(X年X日-日)
    private BigDecimal aplPaySeniY; // 實付年資(年)
    private BigDecimal aplPaySeniM; // 實付年資(月)
    private BigDecimal noldtY; // 老年年資(年)
    private BigDecimal noldtM; // 老年年資(月)
    private BigDecimal valSeniY; // 國保年資(年)
    private BigDecimal valSeniM; // 國保年資(月)
    private BigDecimal insAvgAmt; // 平均薪資
    private String oldAb; // 計算式
    private BigDecimal oldAmt; // 金額
    private BigDecimal payRate;// 匯款匯費
    private BigDecimal recAmt; // 收回金額
    private BigDecimal supAmt; // 補發金額
    private BigDecimal diffAmt;// 差額金金額
    private BigDecimal annuAmt;// 累計已領年金金額
    private String closeCauseName;// 結案原因(擇領) - 中文說明
    private BigDecimal oldRate; // 展延/減額比率
    private String oldRateSdate; // 展延/減額期間(起)
    private String oldRateEdate; // 展延/減額期間(迄)
    private String uname; // 投保單位名稱
    private BigDecimal oldExtraRate; // 遺屬展延/減額比率

    private String isShowCond1;// 審核管制條件1 N:不顯示; Y:顯示「最後單位不得為空」
    private String isShowCond2;// 審核管制條件2 N:不顯示; Y:顯示「實發年資有誤」
    private String isShowCond3;// 審核管制條件3 N:不顯示; Y:顯示「單位墊付金額不得為零」
    private String isShowCond4;// 審核管制條件4 N:不顯示; Y:顯示「實際補償金額不得為零」
    private String isShowCond5;// 審核管制條件5 N:不顯示; Y:顯示「」
    private String isShowCond6;// 審核管制條件6 N:不顯示; Y:顯示「受款人可領金額有誤」
    private String isShowCond7;// 審核管制條件7 N:不顯示; Y:顯示「通訊地址欄位不得為空白」
    private String isShowCond8;// 審核管制條件8 N:不顯示; Y:顯示「編審註記待處理」
    private String isShowCond9;// 審核管制條件9 N:不顯示; Y:顯示「行政支援記錄未銷案」

    private BigDecimal badaprId;// 給付核定檔資料列編號
    private String benEvtAppDate;// 繼承人申請日期
    private String benEvtName;// 繼承自受款人姓名
    private String evtNationName;// 事故者國籍名稱
    private String benNationName;// 受款人國籍名稱
    private String reChk;// 複核是否顯示條件
    private String exeMk; // 決行是否顯示條件
    private String empId;// 員工編號
    private String pagePayKind;// 給付別(受理編號第一碼)
    private BigDecimal compenAmt; // 補償金筆數
    private String cpiAdjYm; // 物價調整年度
    private BigDecimal cpi; // 物價調整指數
    private BigDecimal benAmt; // 受款人數
    private BigDecimal issuYmAmt; // 累計已領年金期數
    private String proDate; // 補件日期
    private String ndomk1; // 處理註記
    private String aplPayDate; // 核付日期
    private BigDecimal befIssueAmt;// 核定總額
    private BigDecimal offsetAmt; // 本次抵銷金額
    // private BigDecimal supAmt; // 補發金額
    private BigDecimal payBanance; // 給付沖抵
    private BigDecimal badDebtAmt; // 呆帳金額
    private String mtestMk; // 處理註記
    
    private BigDecimal lecomAmt; // 已代扣補償金額
    private BigDecimal recomAmt; // 未扣補償金金額
    private BigDecimal oldaAmt; // 第一式式金額
    private BigDecimal oldbAmt; // 第二式式金額
    private BigDecimal inheritorAmt; // 計息存儲金額

    // DecisionRpt 01
    private String note;// 備註

    // DecisionRpt 02 begin... [
    private String arcPgBegin; // 頁次起
    private String arcPgEnd; // 頁次迄
    private BigDecimal dataCount; // 件數
    // ] ... end DecisionRpt 02

    private String mchkTyp; // 月核類別
    private BigDecimal balp0d020Id;// 審核清單編號
    private BigDecimal tissueAmt;// 核定金額
    private BigDecimal taplPayAmt;// 實付金額
    private String paysYm;// 給付年月起
    private String payeYm;// 給付年月迄
    private String rptDate;// 列印日期
    private BigDecimal pageNo;// 列印頁次
    private String reBox;// 複核預設值
    private String exeBox;// 決行預設值

    // AuditRpt 01
    private String endYm;// 截止年月
    private String lastDate;// 最後狀態日期
    private String lastUser;// 最後狀態人員
    private String leterType;// 行政支援函別

    // OtherRpt04
    private String payCode;// 給付別
    private String dm;// 本日 本月
    private String minApNo;// 最小apNo
    private String maxApNo;// 最大apNo
    private BigDecimal recCordCount; // 受理件數
    private String orderNo; //

    // 編審清單 核定通知書
    private String receiveMk; // 受款人註記
    private String receiveName;

    // Field from Baappexpand
    // [
    private BigDecimal baappexpandId; // 資料編號
    // private BigDecimal baappbaseId; // 給付主檔資料編號
    // private String apNo; // 受理編號
    // private String seqNo; // 序號
    private String cancelMk; // 撤銷註記
    private String cancelYm; // 撤銷請領年月
    private String ocaccIdentMk; // 符合第20條之1註記
    private String prType; // 先核普通
    private String evTyp; // 傷病分類
    private String evCode; // 傷病原因
    private String criInPart1; // 受傷部位1
    private String criInPart2; // 受傷部位2
    private String criInPart3; // 受傷部位3
    private String criMedium; // 媒介物
    private String criInJnme1; // 國際疾病代碼1
    private String criInJnme2; // 國際疾病代碼2
    private String criInJnme3; // 國際疾病代碼3
    private String criInJnme4; // 國際疾病代碼4
    private String criInJcl; // 失能等級
    private String criInJcl1; // 失能等級1
    private String criInJcl2; // 失能等級2
    private String criInJcl3; // 失能等級3
    private String criInJdp; // 失能項目
    private String criInJdp1; // 失能項目1
    private String criInJdp2; // 失能項目2
    private String criInJdp3; // 失能項目3
    private String criInJdp4; // 失能項目4
    private String criInJdp5; // 失能項目5
    private String criInJdp6; // 失能項目6
    private String criInJdp7; // 失能項目7
    private String criInJdp8; // 失能項目8
    private String criInJdp9; // 失能項目9
    private String criInJdp10; // 失能項目10
    private String criInIssul; // 核定等級
    private String rehcMk; // 重新查核失能程度註記
    private String rehcYm; // 重新查核失能程度年月
    private String hosId; // 醫療院所代碼
    private String doctorName1; // 醫師姓名1
    private String doctorName2; // 醫師姓名2
    private String prFmlyMk; // 有無前一順位親屬註記
    private String monIncomeMk; // 每月工作收入註記
    private BigDecimal monIncome; // 每月工作收入
    private String famAppMk; // 符合第63條之1第3項註記
    private String studMk; // 在學註記
    private String marryDate; // 結婚日期
    private String digamyDate; // 再婚日期
    private String abanApplyMk; // 放棄請領
    private String abanApsYm; // 放棄請領起始年月
    private String raiseChildMk; // 配偶扶養
    private String handIcapMk; // 領有重度以上身心障礙手冊或證明
    private String interDictMk; // 受禁治產(監護)宣告
    private String interDictDate; // 受禁治產(監護)宣告日期
    private String repealInterdictDate;// 受禁治產(監護)宣告 - 撤銷日期
    private String relatChgDate; // 親屬關係變動日期
    private String judgeDate; // 判決日期
    private String benMissingSdate; // 受益人失蹤期間(起)
    private String benMissingEdate; // 受益人失蹤期間(迄)
    private String prisonSdate; // 監管期間(起)
    private String prisonEdate; // 監管期間(迄)
    private String adoPtDate; // 收養日期
    // private String assignIdnNo; // 代辦人身分證號
    // private String assignName; // 代辦人姓名
    // private String assignBrDate; // 代辦人出生日期
    private String raiseEvtMk; // 被保險人扶養

    // private String crtUser; // 新增者代號
    // private String crtTime; // 新增日期時間
    // private String updUser; // 異動者代號
    // private String updTime; // 異動日期時間
    private String insAvgAmtAppYear; // 計算平均薪資申請年度
    private String cpiBaseYM; // CPI基準年月
    // ]

    private String ableApsYm; // 得請領起月
    private String savingMk; // 計息存儲
    private String studSdate; // 在學起月
    private String studEdate; // 在學迄月
    private BigDecimal termCount; // 在學段數
    private String schoolCode; // 學校代碼

    private String regetCipbMk; // 重讀CIPB

    private String oriIssuYm;// 核定年月
    private String brChkDate; // 退匯初核日期
    private BigDecimal remitAmt; // 退匯金額
    private String issuKind; // 核發種類
    private String brNote; // 退匯原因
    private String closeCauseStr; // 結案原因
    // ]

    private String bkAccountDt; // 銀行入帳日期

    // Field not in BAAPPBASE

    // 給付查詢 檢查保密資料 idn
    private String idnNo; // 檢查身分證

    private String sinsuranceSalary; // 投保薪資比例金額 For A154
    private String comRechkYm;

    // Field from NBSCHOOL
    private String key;// 查詢鍵值：  ID:    10碼身份證號          MISC:  任意鍵值、無標準長度/格式
    private String status_E; // 在學狀態（迄）
    private String statusDate_E; // 狀態日期（迄）
    private String status_S; // 在學狀態（起）
    private String statusDate_S; // 狀態日期（起）

    // 結案狀態變更作業
    private String idForConfirm;// 是否出現 checkBox

    private String remitDate; //入帳日期
    
    public BigDecimal getPayBanance() {
        return payBanance;
    }

    public void setPayBanance(BigDecimal payBanance) {
        this.payBanance = payBanance;
    }

    public String getMchkTyp() {
        return mchkTyp;
    }

    public void setMchkTyp(String mchkTyp) {
        this.mchkTyp = mchkTyp;
    }

    public String getProDate() {
        return proDate;
    }

    public void setProDate(String proDate) {
        this.proDate = proDate;
    }

    public String getNdomk1() {
        return ndomk1;
    }

    public void setNdomk1(String ndomk1) {
        this.ndomk1 = ndomk1;
    }

    public String getAplPayDate() {
        return aplPayDate;
    }

    public void setAplPayDate(String aplPayDate) {
        this.aplPayDate = aplPayDate;
    }

    public String getPagePayKind() {
        return pagePayKind;
    }

    public void setPagePayKind(String pagePayKind) {
        this.pagePayKind = pagePayKind;
    }

    public BigDecimal getCompenAmt() {
        return compenAmt;
    }

    public void setCompenAmt(BigDecimal compenAmt) {
        this.compenAmt = compenAmt;
    }

    public BigDecimal getCpi() {
        return cpi;
    }

    public void setCpi(BigDecimal cpi) {
        this.cpi = cpi;
    }

    public BigDecimal getBenAmt() {
        return benAmt;
    }

    public void setBenAmt(BigDecimal benAmt) {
        this.benAmt = benAmt;
    }

    public BigDecimal getIssuYmAmt() {
        return issuYmAmt;
    }

    public void setIssuYmAmt(BigDecimal issuYmAmt) {
        this.issuYmAmt = issuYmAmt;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getIsShowCond1() {
        return isShowCond1;
    }

    public void setIsShowCond1(String isShowCond1) {
        this.isShowCond1 = isShowCond1;
    }

    public String getIsShowCond2() {
        return isShowCond2;
    }

    public void setIsShowCond2(String isShowCond2) {
        this.isShowCond2 = isShowCond2;
    }

    public String getIsShowCond3() {
        return isShowCond3;
    }

    public void setIsShowCond3(String isShowCond3) {
        this.isShowCond3 = isShowCond3;
    }

    public String getIsShowCond4() {
        return isShowCond4;
    }

    public void setIsShowCond4(String isShowCond4) {
        this.isShowCond4 = isShowCond4;
    }

    public String getIsShowCond5() {
        return isShowCond5;
    }

    public void setIsShowCond5(String isShowCond5) {
        this.isShowCond5 = isShowCond5;
    }

    public String getIsShowCond6() {
        return isShowCond6;
    }

    public void setIsShowCond6(String isShowCond6) {
        this.isShowCond6 = isShowCond6;
    }

    public String getIsShowCond7() {
        return isShowCond7;
    }

    public void setIsShowCond7(String isShowCond7) {
        this.isShowCond7 = isShowCond7;
    }

    public BigDecimal getBaappbaseId() {
        return baappbaseId;
    }

    public void setBaappbaseId(BigDecimal baappbaseId) {
        this.baappbaseId = baappbaseId;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getImk() {
        return imk;
    }

    public void setImk(String imk) {
        this.imk = imk;
    }

    public String getApItem() {
        return apItem;
    }

    public void setApItem(String apItem) {
        this.apItem = apItem;
    }

    public String getPayKind() {
        return payKind;
    }

    public void setPayKind(String payKind) {
        this.payKind = payKind;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getCaseTyp() {
        return caseTyp;
    }

    public void setCaseTyp(String caseTyp) {
        this.caseTyp = caseTyp;
    }

    public String getMapNo() {
        return mapNo;
    }

    public void setMapNo(String mapNo) {
        this.mapNo = mapNo;
    }

    public String getMapRootMk() {
        return mapRootMk;
    }

    public void setMapRootMk(String mapRootMk) {
        this.mapRootMk = mapRootMk;
    }

    public String getCombapMark() {
        return combapMark;
    }

    public void setCombapMark(String combapMark) {
        this.combapMark = combapMark;
    }

    public String getCaseMk() {
        return caseMk;
    }

    public void setCaseMk(String caseMk) {
        this.caseMk = caseMk;
    }

    public String getProcStat() {
        return procStat;
    }

    public void setProcStat(String procStat) {
        this.procStat = procStat;
    }

    public String getAcceptMk() {
        return acceptMk;
    }

    public void setAcceptMk(String acceptMk) {
        this.acceptMk = acceptMk;
    }

    public String getManchkMk() {
        return manchkMk;
    }

    public void setManchkMk(String manchkMk) {
        this.manchkMk = manchkMk;
    }

    public String getChgNote() {
        return chgNote;
    }

    public void setChgNote(String chgNote) {
        this.chgNote = chgNote;
    }

    public String getExeStat() {
        return exeStat;
    }

    public void setExeStat(String exeStat) {
        this.exeStat = exeStat;
    }

    public String getApUbno() {
        return apUbno;
    }

    public void setApUbno(String apUbno) {
        this.apUbno = apUbno;
    }

    public String getApubnock() {
        return apubnock;
    }

    public void setApubnock(String apubnock) {
        this.apubnock = apubnock;
    }

    public String getLsUbno() {
        return lsUbno;
    }

    public void setLsUbno(String lsUbno) {
        this.lsUbno = lsUbno;
    }

    public String getLsUbnoCk() {
        return lsUbnoCk;
    }

    public void setLsUbnoCk(String lsUbnoCk) {
        this.lsUbnoCk = lsUbnoCk;
    }

    public String getEvtIds() {
        return evtIds;
    }

    public void setEvtIds(String evtIds) {
        this.evtIds = evtIds;
    }

    public String getEvtIdnNo() {
        return evtIdnNo;
    }

    public void setEvtIdnNo(String evtIdnNo) {
        this.evtIdnNo = evtIdnNo;
    }

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public String getEvtBrDate() {
        return evtBrDate;
    }

    public void setEvtBrDate(String evtBrDate) {
        this.evtBrDate = evtBrDate;
    }

    public String getEvtSex() {
        return evtSex;
    }

    public void setEvtSex(String evtSex) {
        this.evtSex = evtSex;
    }

    public String getEvtNationTpe() {
        return evtNationTpe;
    }

    public void setEvtNationTpe(String evtNationTpe) {
        this.evtNationTpe = evtNationTpe;
    }

    public String getEvtNationCode() {
        return evtNationCode;
    }

    public void setEvtNationCode(String evtNationCode) {
        this.evtNationCode = evtNationCode;
    }

    public String getEvtIdent() {
        return evtIdent;
    }

    public void setEvtIdent(String evtIdent) {
        this.evtIdent = evtIdent;
    }

    public String getEvtJobDate() {
        return evtJobDate;
    }

    public void setEvtJobDate(String evtJobDate) {
        this.evtJobDate = evtJobDate;
    }

    public String getEvtDate() {
        return evtDate;
    }

    public void setEvtDate(String evtDate) {
        this.evtDate = evtDate;
    }

    public String getEvtDieDate() {
        return evtDieDate;
    }

    public void setEvtDieDate(String evtDieDate) {
        this.evtDieDate = evtDieDate;
    }

    public String getEvtAge() {
        return evtAge;
    }

    public void setEvtAge(String evtAge) {
        this.evtAge = evtAge;
    }

    public String getBenIds() {
        return benIds;
    }

    public void setBenIds(String benIds) {
        this.benIds = benIds;
    }

    public String getBenIdnNo() {
        return benIdnNo;
    }

    public void setBenIdnNo(String benIdnNo) {
        this.benIdnNo = benIdnNo;
    }

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }

    public String getBenBrDate() {
        return benBrDate;
    }

    public void setBenBrDate(String benBrDate) {
        this.benBrDate = benBrDate;
    }

    public String getBenSex() {
        return benSex;
    }

    public void setBenSex(String benSex) {
        this.benSex = benSex;
    }

    public String getBenNationTyp() {
        return benNationTyp;
    }

    public void setBenNationTyp(String benNationTyp) {
        this.benNationTyp = benNationTyp;
    }

    public String getBenNationCode() {
        return benNationCode;
    }

    public void setBenNationCode(String benNationCode) {
        this.benNationCode = benNationCode;
    }

    public String getBenEvtRel() {
        return benEvtRel;
    }

    public void setBenEvtRel(String benEvtRel) {
        this.benEvtRel = benEvtRel;
    }

    public String getBenDieDate() {
        return benDieDate;
    }

    public void setBenDieDate(String benDieDate) {
        this.benDieDate = benDieDate;
    }

    public String getBenPayMk() {
        return benPayMk;
    }

    public void setBenPayMk(String benPayMk) {
        this.benPayMk = benPayMk;
    }

    public String getIdnChkYm() {
        return idnChkYm;
    }

    public void setIdnChkYm(String idnChkYm) {
        this.idnChkYm = idnChkYm;
    }

    public String getIdnChkNote() {
        return idnChkNote;
    }

    public void setIdnChkNote(String idnChkNote) {
        this.idnChkNote = idnChkNote;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getCommTyp() {
        return commTyp;
    }

    public void setCommTyp(String commTyp) {
        this.commTyp = commTyp;
    }

    public String getCommZip() {
        return commZip;
    }

    public void setCommZip(String commZip) {
        this.commZip = commZip;
    }

    public String getCommAddr() {
        return commAddr;
    }

    public void setCommAddr(String commAddr) {
        this.commAddr = commAddr;
    }

    public String getPayTyp() {
        return payTyp;
    }

    public void setPayTyp(String payTyp) {
        this.payTyp = payTyp;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getPayBankId() {
        return payBankId;
    }

    public void setPayBankId(String payBankId) {
        this.payBankId = payBankId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getPayEeacc() {
        return payEeacc;
    }

    public void setPayEeacc(String payEeacc) {
        this.payEeacc = payEeacc;
    }

    public String getChkPayBankId() {
        return chkPayBankId;
    }

    public void setChkPayBankId(String chkPayBankId) {
        this.chkPayBankId = chkPayBankId;
    }

    public String getChkBranchId() {
        return chkBranchId;
    }

    public void setChkBranchId(String chkBranchId) {
        this.chkBranchId = chkBranchId;
    }

    public String getChkPayEeacc() {
        return chkPayEeacc;
    }

    public void setChkPayEeacc(String chkPayEeacc) {
        this.chkPayEeacc = chkPayEeacc;
    }

    public String getAccIdn() {
        return accIdn;
    }

    public void setAccIdn(String accIdn) {
        this.accIdn = accIdn;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getAccRel() {
        return accRel;
    }

    public void setAccRel(String accRel) {
        this.accRel = accRel;
    }

    public BigDecimal getMitRate() {
        return mitRate;
    }

    public void setMitRate(BigDecimal mitRate) {
        this.mitRate = mitRate;
    }

    public String getGrdIdnNo() {
        return grdIdnNo;
    }

    public void setGrdIdnNo(String grdIdnNo) {
        this.grdIdnNo = grdIdnNo;
    }

    public String getGrdName() {
        return grdName;
    }

    public void setGrdName(String grdName) {
        this.grdName = grdName;
    }

    public String getGrdBrDate() {
        return grdBrDate;
    }

    public void setGrdBrDate(String grdBrDate) {
        this.grdBrDate = grdBrDate;
    }

    public String getAssignIdnNo() {
        return assignIdnNo;
    }

    public void setAssignIdnNo(String assignIdnNo) {
        this.assignIdnNo = assignIdnNo;
    }

    public String getAssignName() {
        return assignName;
    }

    public void setAssignName(String assignName) {
        this.assignName = assignName;
    }

    public String getAssignBrDate() {
        return assignBrDate;
    }

    public void setAssignBrDate(String assignBrDate) {
        this.assignBrDate = assignBrDate;
    }

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String getPayYm() {
        return payYm;
    }

    public void setPayYm(String payYm) {
        this.payYm = payYm;
    }

    public BigDecimal getIssueAmt() {
        return issueAmt;
    }

    public void setIssueAmt(BigDecimal issueAmt) {
        this.issueAmt = issueAmt;
    }

    public String getOncePayMk() {
        return oncePayMk;
    }

    public void setOncePayMk(String oncePayMk) {
        this.oncePayMk = oncePayMk;
    }

    public String getOldSeniab() {
        return oldSeniab;
    }

    public void setOldSeniab(String oldSeniab) {
        this.oldSeniab = oldSeniab;
    }

    public BigDecimal getMustIssueAmt() {
        return mustIssueAmt;
    }

    public void setMustIssueAmt(BigDecimal mustIssueAmt) {
        this.mustIssueAmt = mustIssueAmt;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPayYms() {
        return payYms;
    }

    public void setPayYms(String payYms) {
        this.payYms = payYms;
    }

    public String getPayYme() {
        return payYme;
    }

    public void setPayYme(String payYme) {
        this.payYme = payYme;
    }

    public String getPauseYm() {
        return pauseYm;
    }

    public void setPauseYm(String pauseYm) {
        this.pauseYm = pauseYm;
    }

    public String getUnPauseYm() {
        return unPauseYm;
    }

    public void setUnPauseYm(String unPauseYm) {
        this.unPauseYm = unPauseYm;
    }

    public String getNotifyForm() {
        return notifyForm;
    }

    public void setNotifyForm(String notifyForm) {
        this.notifyForm = notifyForm;
    }

    public String getLoanMk() {
        return loanMk;
    }

    public void setLoanMk(String loanMk) {
        this.loanMk = loanMk;
    }

    public String getMexcLvl() {
        return mexcLvl;
    }

    public void setMexcLvl(String mexcLvl) {
        this.mexcLvl = mexcLvl;
    }

    public String getRealexcLvl() {
        return realexcLvl;
    }

    public void setRealexcLvl(String realexcLvl) {
        this.realexcLvl = realexcLvl;
    }

    public String getChkDate() {
        return chkDate;
    }

    public void setChkDate(String chkDate) {
        this.chkDate = chkDate;
    }

    public String getChkMan() {
        return chkMan;
    }

    public void setChkMan(String chkMan) {
        this.chkMan = chkMan;
    }

    public String getRechkDate() {
        return rechkDate;
    }

    public void setRechkDate(String rechkDate) {
        this.rechkDate = rechkDate;
    }

    public String getRechkMan() {
        return rechkMan;
    }

    public void setRechkMan(String rechkMan) {
        this.rechkMan = rechkMan;
    }

    public String getExeDate() {
        return exeDate;
    }

    public void setExeDate(String exeDate) {
        this.exeDate = exeDate;
    }

    public String getExeMan() {
        return exeMan;
    }

    public void setExeMan(String exeMan) {
        this.exeMan = exeMan;
    }

    public String getArcDate() {
        return arcDate;
    }

    public void setArcDate(String arcDate) {
        this.arcDate = arcDate;
    }

    public String getArcPg() {
        return arcPg;
    }

    public void setArcPg(String arcPg) {
        this.arcPg = arcPg;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public String getCloseCause() {
        return closeCause;
    }

    public void setCloseCause(String closeCause) {
        this.closeCause = closeCause;
    }

    public String getCloseCauseCode() {
        return closeCauseCode;
    }

    public void setCloseCauseCode(String closeCauseCode) {
        this.closeCauseCode = closeCauseCode;
    }

    public String getPromoteUser() {
        return promoteUser;
    }

    public void setPromoteUser(String promoteUser) {
        this.promoteUser = promoteUser;
    }

    public String getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser;
    }

    public String getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(String crtTime) {
        this.crtTime = crtTime;
    }

    public String getUpdUser() {
        return updUser;
    }

    public void setUpdUser(String updUser) {
        this.updUser = updUser;
    }

    public String getUpdTime() {
        return updTime;
    }

    public void setUpdTime(String updTime) {
        this.updTime = updTime;
    }

    public String getDupeIdnNoMk() {
        return dupeIdnNoMk;
    }

    public void setDupeIdnNoMk(String dupeIdnNoMk) {
        this.dupeIdnNoMk = dupeIdnNoMk;
    }

    public String getEvtExpireDate() {
        return evtExpireDate;
    }

    public void setEvtExpireDate(String evtExpireDate) {
        this.evtExpireDate = evtExpireDate;
    }

    public String getEvtEligibleDate() {
        return evtEligibleDate;
    }

    public void setEvtEligibleDate(String evtEligibleDate) {
        this.evtEligibleDate = evtEligibleDate;
    }

    public String getEvtMissingDate() {
        return evtMissingDate;
    }

    public void setEvtMissingDate(String evtMissingDate) {
        this.evtMissingDate = evtMissingDate;
    }

    public BigDecimal getLsInsmAmt() {
        return lsInsmAmt;
    }

    public void setLsInsmAmt(BigDecimal lsInsmAmt) {
        this.lsInsmAmt = lsInsmAmt;
    }

    public String getPayKindName() {
        return payKindName;
    }

    public void setPayKindName(String payKindName) {
        this.payKindName = payKindName;
    }

    public String getMaxPayYm() {
        return maxPayYm;
    }

    public void setMaxPayYm(String maxPayYm) {
        this.maxPayYm = maxPayYm;
    }

    public String getMinPayYm() {
        return minPayYm;
    }

    public void setMinPayYm(String minPayYm) {
        this.minPayYm = minPayYm;
    }

    public BigDecimal getAplPayAmt() {
        return aplPayAmt;
    }

    public void setAplPayAmt(BigDecimal aplPayAmt) {
        this.aplPayAmt = aplPayAmt;
    }

    public BigDecimal getAdjustAmt() {
        return adjustAmt;
    }

    public void setAdjustAmt(BigDecimal adjustAmt) {
        this.adjustAmt = adjustAmt;
    }

    public BigDecimal getOtherAmt() {
        return otherAmt;
    }

    public void setOtherAmt(BigDecimal otherAmt) {
        this.otherAmt = otherAmt;
    }

    public String getVeriSeq() {
        return veriSeq;
    }

    public void setVeriSeq(String veriSeq) {
        this.veriSeq = veriSeq;
    }

    public BigDecimal getNitrmY() {
        return nitrmY;
    }

    public void setNitrmY(BigDecimal nitrmY) {
        this.nitrmY = nitrmY;
    }

    public BigDecimal getNitrmM() {
        return nitrmM;
    }

    public void setNitrmM(BigDecimal nitrmM) {
        this.nitrmM = nitrmM;
    }

    public BigDecimal getItrmY() {
        return itrmY;
    }

    public void setItrmY(BigDecimal itrmY) {
        this.itrmY = itrmY;
    }

    public BigDecimal getItrmD() {
        return itrmD;
    }

    public void setItrmD(BigDecimal itrmD) {
        this.itrmD = itrmD;
    }

    public BigDecimal getAplPaySeniY() {
        return aplPaySeniY;
    }

    public void setAplPaySeniY(BigDecimal aplPaySeniY) {
        this.aplPaySeniY = aplPaySeniY;
    }

    public BigDecimal getAplPaySeniM() {
        return aplPaySeniM;
    }

    public void setAplPaySeniM(BigDecimal aplPaySeniM) {
        this.aplPaySeniM = aplPaySeniM;
    }

    public BigDecimal getNoldtY() {
        return noldtY;
    }

    public void setNoldtY(BigDecimal noldtY) {
        this.noldtY = noldtY;
    }

    public BigDecimal getNoldtM() {
        return noldtM;
    }

    public void setNoldtM(BigDecimal noldtM) {
        this.noldtM = noldtM;
    }

    public BigDecimal getValSeniY() {
        return valSeniY;
    }

    public void setValSeniY(BigDecimal valSeniY) {
        this.valSeniY = valSeniY;
    }

    public BigDecimal getValSeniM() {
        return valSeniM;
    }

    public void setValSeniM(BigDecimal valSeniM) {
        this.valSeniM = valSeniM;
    }

    public BigDecimal getInsAvgAmt() {
        return insAvgAmt;
    }

    public void setInsAvgAmt(BigDecimal insAvgAmt) {
        this.insAvgAmt = insAvgAmt;
    }

    public String getOldAb() {
        return oldAb;
    }

    public void setOldAb(String oldAb) {
        this.oldAb = oldAb;
    }

    public BigDecimal getOldAmt() {
        return oldAmt;
    }

    public void setOldAmt(BigDecimal oldAmt) {
        this.oldAmt = oldAmt;
    }

    public BigDecimal getDiffAmt() {
        return diffAmt;
    }

    public void setDiffAmt(BigDecimal diffAmt) {
        this.diffAmt = diffAmt;
    }

    public BigDecimal getAnnuAmt() {
        return annuAmt;
    }

    public void setAnnuAmt(BigDecimal annuAmt) {
        this.annuAmt = annuAmt;
    }

    public String getCloseCauseName() {
        return closeCauseName;
    }

    public void setCloseCauseName(String closeCauseName) {
        this.closeCauseName = closeCauseName;
    }

    public BigDecimal getPayRate() {
        return payRate;
    }

    public void setPayRate(BigDecimal payRate) {
        this.payRate = payRate;
    }

    public String getBenEvtName() {
        return benEvtName;
    }

    public void setBenEvtName(String benEvtName) {
        this.benEvtName = benEvtName;
    }

    public String getBenNationName() {
        return benNationName;
    }

    public void setBenNationName(String benNationName) {
        this.benNationName = benNationName;
    }

    public String getBenEvtAppDate() {
        return benEvtAppDate;
    }

    public void setBenEvtAppDate(String benEvtAppDate) {
        this.benEvtAppDate = benEvtAppDate;
    }

    public BigDecimal getRecAmt() {
        return recAmt;
    }

    public void setRecAmt(BigDecimal recAmt) {
        this.recAmt = recAmt;
    }

    public BigDecimal getSupAmt() {
        return supAmt;
    }

    public void setSupAmt(BigDecimal supAmt) {
        this.supAmt = supAmt;
    }

    public BigDecimal getBadaprId() {
        return badaprId;
    }

    public void setBadaprId(BigDecimal badaprId) {
        this.badaprId = badaprId;
    }

    public String getReChk() {
        return reChk;
    }

    public void setReChk(String reChk) {
        this.reChk = reChk;
    }

    public String getExeMk() {
        return exeMk;
    }

    public void setExeMk(String exeMk) {
        this.exeMk = exeMk;
    }

    public String getCpiAdjYm() {
        return cpiAdjYm;
    }

    public void setCpiAdjYm(String cpiAdjYm) {
        this.cpiAdjYm = cpiAdjYm;
    }

    public String getLsChkMk() {
        return lsChkMk;
    }

    public void setLsChkMk(String lsChkMk) {
        this.lsChkMk = lsChkMk;
    }

    public BigDecimal getLsCount() {
        return lsCount;
    }

    public void setLsCount(BigDecimal lsCount) {
        this.lsCount = lsCount;
    }

    public String getLnChkMk() {
        return lnChkMk;
    }

    public void setLnChkMk(String lnChkMk) {
        this.lnChkMk = lnChkMk;
    }

    public String getDabApNo() {
        return dabApNo;
    }

    public void setDabApNo(String dabApNo) {
        this.dabApNo = dabApNo;
    }

    public BigDecimal getDabAnnuAmt() {
        return dabAnnuAmt;
    }

    public void setDabAnnuAmt(BigDecimal dabAnnuAmt) {
        this.dabAnnuAmt = dabAnnuAmt;
    }

    public String getIsShowCond8() {
        return isShowCond8;
    }

    public void setIsShowCond8(String isShowCond8) {
        this.isShowCond8 = isShowCond8;
    }

    public String getIsShowCond9() {
        return isShowCond9;
    }

    public void setIsShowCond9(String isShowCond9) {
        this.isShowCond9 = isShowCond9;
    }

    public String getReSeniMk() {
        return reSeniMk;
    }

    public void setReSeniMk(String reSeniMk) {
        this.reSeniMk = reSeniMk;
    }

    public String getOffInsurDate() {
        return offInsurDate;
    }

    public void setOffInsurDate(String offInsurDate) {
        this.offInsurDate = offInsurDate;
    }

    public String getLawRetireDate() {
        return lawRetireDate;
    }

    public void setLawRetireDate(String lawRetireDate) {
        this.lawRetireDate = lawRetireDate;
    }

    public String getArcPgBegin() {
        return arcPgBegin;
    }

    public void setArcPgBegin(String arcPgBegin) {
        this.arcPgBegin = arcPgBegin;
    }

    public String getArcPgEnd() {
        return arcPgEnd;
    }

    public void setArcPgEnd(String arcPgEnd) {
        this.arcPgEnd = arcPgEnd;
    }

    public BigDecimal getDataCount() {
        return dataCount;
    }

    public void setDataCount(BigDecimal dataCount) {
        this.dataCount = dataCount;
    }

    public String getEvtAppName() {
        return evtAppName;
    }

    public void setEvtAppName(String evtAppName) {
        this.evtAppName = evtAppName;
    }

    public BigDecimal getCutAmt() {
        return cutAmt;
    }

    public void setCutAmt(BigDecimal cutAmt) {
        this.cutAmt = cutAmt;
    }

    public String getAccSeqNo() {
        return accSeqNo;
    }

    public void setAccSeqNo(String accSeqNo) {
        this.accSeqNo = accSeqNo;
    }

    public String getBenMarrMk() {
        return benMarrMk;
    }

    public void setBenMarrMk(String benMarrMk) {
        this.benMarrMk = benMarrMk;
    }

    public BigDecimal getOldRate() {
        return oldRate;
    }

    public void setOldRate(BigDecimal oldRate) {
        this.oldRate = oldRate;
    }

    public String getOldRateSdate() {
        return oldRateSdate;
    }

    public void setOldRateSdate(String oldRateSdate) {
        this.oldRateSdate = oldRateSdate;
    }

    public String getOldRateEdate() {
        return oldRateEdate;
    }

    public void setOldRateEdate(String oldRateEdate) {
        this.oldRateEdate = oldRateEdate;
    }

    public String getChgMk() {
        return chgMk;
    }

    public void setChgMk(String chgMk) {
        this.chgMk = chgMk;
    }

    public BigDecimal getPayAmts() {
        return payAmts;
    }

    public void setPayAmts(BigDecimal payAmts) {
        this.payAmts = payAmts;
    }

    public String getEvtAppIdnNo() {
        return evtAppIdnNo;
    }

    public void setEvtAppIdnNo(String evtAppIdnNo) {
        this.evtAppIdnNo = evtAppIdnNo;
    }

    public String getEvtAppBrDate() {
        return evtAppBrDate;
    }

    public void setEvtAppBrDate(String evtAppBrDate) {
        this.evtAppBrDate = evtAppBrDate;
    }

    public String getOldAplDpt() {
        return oldAplDpt;
    }

    public void setOldAplDpt(String oldAplDpt) {
        this.oldAplDpt = oldAplDpt;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public BigDecimal getBefIssueAmt() {
        return befIssueAmt;
    }

    public void setBefIssueAmt(BigDecimal befIssueAmt) {
        this.befIssueAmt = befIssueAmt;
    }

    public BigDecimal getBalp0d020Id() {
        return balp0d020Id;
    }

    public void setBalp0d020Id(BigDecimal balp0d020Id) {
        this.balp0d020Id = balp0d020Id;
    }

    public BigDecimal getTissueAmt() {
        return tissueAmt;
    }

    public void setTissueAmt(BigDecimal tissueAmt) {
        this.tissueAmt = tissueAmt;
    }

    public BigDecimal getTaplPayAmt() {
        return taplPayAmt;
    }

    public void setTaplPayAmt(BigDecimal taplPayAmt) {
        this.taplPayAmt = taplPayAmt;
    }

    public String getPaysYm() {
        return paysYm;
    }

    public void setPaysYm(String paysYm) {
        this.paysYm = paysYm;
    }

    public String getPayeYm() {
        return payeYm;
    }

    public void setPayeYm(String payeYm) {
        this.payeYm = payeYm;
    }

    public String getRptDate() {
        return rptDate;
    }

    public void setRptDate(String rptDate) {
        this.rptDate = rptDate;
    }

    public BigDecimal getPageNo() {
        return pageNo;
    }

    public void setPageNo(BigDecimal pageNo) {
        this.pageNo = pageNo;
    }

    public String getReBox() {
        return reBox;
    }

    public void setReBox(String reBox) {
        this.reBox = reBox;
    }

    public String getExeBox() {
        return exeBox;
    }

    public void setExeBox(String exeBox) {
        this.exeBox = exeBox;
    }

    public BigDecimal getBaappexpandId() {
        return baappexpandId;
    }

    public void setBaappexpandId(BigDecimal baappexpandId) {
        this.baappexpandId = baappexpandId;
    }

    public String getCancelMk() {
        return cancelMk;
    }

    public void setCancelMk(String cancelMk) {
        this.cancelMk = cancelMk;
    }

    public String getCancelYm() {
        return cancelYm;
    }

    public void setCancelYm(String cancelYm) {
        this.cancelYm = cancelYm;
    }

    public String getOcaccIdentMk() {
        return ocaccIdentMk;
    }

    public void setOcaccIdentMk(String ocaccIdentMk) {
        this.ocaccIdentMk = ocaccIdentMk;
    }

    public String getPrType() {
        return prType;
    }

    public void setPrType(String prType) {
        this.prType = prType;
    }

    public String getEvTyp() {
        return evTyp;
    }

    public void setEvTyp(String evTyp) {
        this.evTyp = evTyp;
    }

    public String getEvCode() {
        return evCode;
    }

    public void setEvCode(String evCode) {
        this.evCode = evCode;
    }

    public String getCriInPart1() {
        return criInPart1;
    }

    public void setCriInPart1(String criInPart1) {
        this.criInPart1 = criInPart1;
    }

    public String getCriInPart2() {
        return criInPart2;
    }

    public void setCriInPart2(String criInPart2) {
        this.criInPart2 = criInPart2;
    }

    public String getCriInPart3() {
        return criInPart3;
    }

    public void setCriInPart3(String criInPart3) {
        this.criInPart3 = criInPart3;
    }

    public String getCriMedium() {
        return criMedium;
    }

    public void setCriMedium(String criMedium) {
        this.criMedium = criMedium;
    }

    public String getCriInJnme1() {
        return criInJnme1;
    }

    public void setCriInJnme1(String criInJnme1) {
        this.criInJnme1 = criInJnme1;
    }

    public String getCriInJnme2() {
        return criInJnme2;
    }

    public void setCriInJnme2(String criInJnme2) {
        this.criInJnme2 = criInJnme2;
    }

    public String getCriInJnme3() {
        return criInJnme3;
    }

    public void setCriInJnme3(String criInJnme3) {
        this.criInJnme3 = criInJnme3;
    }

    public String getCriInJnme4() {
        return criInJnme4;
    }

    public void setCriInJnme4(String criInJnme4) {
        this.criInJnme4 = criInJnme4;
    }

    public String getCriInJcl() {
        return criInJcl;
    }

    public void setCriInJcl(String criInJcl) {
        this.criInJcl = criInJcl;
    }

    public String getCriInJcl1() {
        return criInJcl1;
    }

    public void setCriInJcl1(String criInJcl1) {
        this.criInJcl1 = criInJcl1;
    }

    public String getCriInJcl2() {
        return criInJcl2;
    }

    public void setCriInJcl2(String criInJcl2) {
        this.criInJcl2 = criInJcl2;
    }

    public String getCriInJcl3() {
        return criInJcl3;
    }

    public void setCriInJcl3(String criInJcl3) {
        this.criInJcl3 = criInJcl3;
    }

    public String getCriInJdp() {
        return criInJdp;
    }

    public void setCriInJdp(String criInJdp) {
        this.criInJdp = criInJdp;
    }

    public String getCriInJdp1() {
        return criInJdp1;
    }

    public void setCriInJdp1(String criInJdp1) {
        this.criInJdp1 = criInJdp1;
    }

    public String getCriInJdp2() {
        return criInJdp2;
    }

    public void setCriInJdp2(String criInJdp2) {
        this.criInJdp2 = criInJdp2;
    }

    public String getCriInJdp3() {
        return criInJdp3;
    }

    public void setCriInJdp3(String criInJdp3) {
        this.criInJdp3 = criInJdp3;
    }

    public String getCriInJdp4() {
        return criInJdp4;
    }

    public void setCriInJdp4(String criInJdp4) {
        this.criInJdp4 = criInJdp4;
    }

    public String getCriInJdp5() {
        return criInJdp5;
    }

    public void setCriInJdp5(String criInJdp5) {
        this.criInJdp5 = criInJdp5;
    }

    public String getCriInJdp6() {
        return criInJdp6;
    }

    public void setCriInJdp6(String criInJdp6) {
        this.criInJdp6 = criInJdp6;
    }

    public String getCriInJdp7() {
        return criInJdp7;
    }

    public void setCriInJdp7(String criInJdp7) {
        this.criInJdp7 = criInJdp7;
    }

    public String getCriInJdp8() {
        return criInJdp8;
    }

    public void setCriInJdp8(String criInJdp8) {
        this.criInJdp8 = criInJdp8;
    }

    public String getCriInJdp9() {
        return criInJdp9;
    }

    public void setCriInJdp9(String criInJdp9) {
        this.criInJdp9 = criInJdp9;
    }

    public String getCriInJdp10() {
        return criInJdp10;
    }

    public void setCriInJdp10(String criInJdp10) {
        this.criInJdp10 = criInJdp10;
    }

    public String getCriInIssul() {
        return criInIssul;
    }

    public void setCriInIssul(String criInIssul) {
        this.criInIssul = criInIssul;
    }

    public String getRehcMk() {
        return rehcMk;
    }

    public void setRehcMk(String rehcMk) {
        this.rehcMk = rehcMk;
    }

    public String getRehcYm() {
        return rehcYm;
    }

    public void setRehcYm(String rehcYm) {
        this.rehcYm = rehcYm;
    }

    public String getHosId() {
        return hosId;
    }

    public void setHosId(String hosId) {
        this.hosId = hosId;
    }

    public String getDoctorName1() {
        return doctorName1;
    }

    public void setDoctorName1(String doctorName1) {
        this.doctorName1 = doctorName1;
    }

    public String getDoctorName2() {
        return doctorName2;
    }

    public void setDoctorName2(String doctorName2) {
        this.doctorName2 = doctorName2;
    }

    public String getPrFmlyMk() {
        return prFmlyMk;
    }

    public void setPrFmlyMk(String prFmlyMk) {
        this.prFmlyMk = prFmlyMk;
    }

    public String getMonIncomeMk() {
        return monIncomeMk;
    }

    public void setMonIncomeMk(String monIncomeMk) {
        this.monIncomeMk = monIncomeMk;
    }

    public BigDecimal getMonIncome() {
        return monIncome;
    }

    public void setMonIncome(BigDecimal monIncome) {
        this.monIncome = monIncome;
    }

    public String getFamAppMk() {
        return famAppMk;
    }

    public void setFamAppMk(String famAppMk) {
        this.famAppMk = famAppMk;
    }

    public String getStudMk() {
        return studMk;
    }

    public void setStudMk(String studMk) {
        this.studMk = studMk;
    }

    public String getMarryDate() {
        return marryDate;
    }

    public void setMarryDate(String marryDate) {
        this.marryDate = marryDate;
    }

    public String getDigamyDate() {
        return digamyDate;
    }

    public void setDigamyDate(String digamyDate) {
        this.digamyDate = digamyDate;
    }

    public String getAbanApplyMk() {
        return abanApplyMk;
    }

    public void setAbanApplyMk(String abanApplyMk) {
        this.abanApplyMk = abanApplyMk;
    }

    public String getAbanApsYm() {
        return abanApsYm;
    }

    public void setAbanApsYm(String abanApsYm) {
        this.abanApsYm = abanApsYm;
    }

    public String getRaiseChildMk() {
        return raiseChildMk;
    }

    public void setRaiseChildMk(String raiseChildMk) {
        this.raiseChildMk = raiseChildMk;
    }

    public String getHandIcapMk() {
        return handIcapMk;
    }

    public void setHandIcapMk(String handIcapMk) {
        this.handIcapMk = handIcapMk;
    }

    public String getInterDictMk() {
        return interDictMk;
    }

    public void setInterDictMk(String interDictMk) {
        this.interDictMk = interDictMk;
    }

    public String getInterDictDate() {
        return interDictDate;
    }

    public void setInterDictDate(String interDictDate) {
        this.interDictDate = interDictDate;
    }

    public String getRelatChgDate() {
        return relatChgDate;
    }

    public void setRelatChgDate(String relatChgDate) {
        this.relatChgDate = relatChgDate;
    }

    public String getJudgeDate() {
        return judgeDate;
    }

    public void setJudgeDate(String judgeDate) {
        this.judgeDate = judgeDate;
    }

    public String getBenMissingSdate() {
        return benMissingSdate;
    }

    public void setBenMissingSdate(String benMissingSdate) {
        this.benMissingSdate = benMissingSdate;
    }

    public String getBenMissingEdate() {
        return benMissingEdate;
    }

    public void setBenMissingEdate(String benMissingEdate) {
        this.benMissingEdate = benMissingEdate;
    }

    public String getPrisonSdate() {
        return prisonSdate;
    }

    public void setPrisonSdate(String prisonSdate) {
        this.prisonSdate = prisonSdate;
    }

    public String getPrisonEdate() {
        return prisonEdate;
    }

    public void setPrisonEdate(String prisonEdate) {
        this.prisonEdate = prisonEdate;
    }

    public String getAdoPtDate() {
        return adoPtDate;
    }

    public void setAdoPtDate(String adoPtDate) {
        this.adoPtDate = adoPtDate;
    }

    public String getRaiseEvtMk() {
        return raiseEvtMk;
    }

    public void setRaiseEvtMk(String raiseEvtMk) {
        this.raiseEvtMk = raiseEvtMk;
    }

    public String getChoiceYm() {
        return choiceYm;
    }

    public void setChoiceYm(String choiceYm) {
        this.choiceYm = choiceYm;
    }

    public BigDecimal getLecomAmt() {
        return lecomAmt;
    }

    public void setLecomAmt(BigDecimal lecomAmt) {
        this.lecomAmt = lecomAmt;
    }

    public BigDecimal getRecomAmt() {
        return recomAmt;
    }

    public void setRecomAmt(BigDecimal recomAmt) {
        this.recomAmt = recomAmt;
    }

    public BigDecimal getOldaAmt() {
        return oldaAmt;
    }

    public void setOldaAmt(BigDecimal oldaAmt) {
        this.oldaAmt = oldaAmt;
    }

    public BigDecimal getOldbAmt() {
        return oldbAmt;
    }

    public void setOldbAmt(BigDecimal oldbAmt) {
        this.oldbAmt = oldbAmt;
    }

    public String getEvtNationName() {
        return evtNationName;
    }

    public void setEvtNationName(String evtNationName) {
        this.evtNationName = evtNationName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getAbleApsYm() {
        return ableApsYm;
    }

    public void setAbleApsYm(String ableApsYm) {
        this.ableApsYm = ableApsYm;
    }

    public String getSavingMk() {
        return savingMk;
    }

    public void setSavingMk(String savingMk) {
        this.savingMk = savingMk;
    }

    public String getStudSdate() {
        return studSdate;
    }

    public void setStudSdate(String studSdate) {
        this.studSdate = studSdate;
    }

    public String getStudEdate() {
        return studEdate;
    }

    public void setStudEdate(String studEdate) {
        this.studEdate = studEdate;
    }

    public BigDecimal getTermCount() {
        return termCount;
    }

    public void setTermCount(BigDecimal termCount) {
        this.termCount = termCount;
    }

    public BigDecimal getOldExtraRate() {
        return oldExtraRate;
    }

    public void setOldExtraRate(BigDecimal oldExtraRate) {
        this.oldExtraRate = oldExtraRate;
    }

    public String getRepealInterdictDate() {
        return repealInterdictDate;
    }

    public void setRepealInterdictDate(String repealInterdictDate) {
        this.repealInterdictDate = repealInterdictDate;
    }

    public BigDecimal getOffsetAmt() {
        return offsetAmt;
    }

    public void setOffsetAmt(BigDecimal offsetAmt) {
        this.offsetAmt = offsetAmt;
    }

    public BigDecimal getBadDebtAmt() {
        return badDebtAmt;
    }

    public void setBadDebtAmt(BigDecimal badDebtAmt) {
        this.badDebtAmt = badDebtAmt;
    }

    public String getInterValMonth() {
        return interValMonth;
    }

    public void setInterValMonth(String interValMonth) {
        this.interValMonth = interValMonth;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getRegetCipbMk() {
        return regetCipbMk;
    }

    public void setRegetCipbMk(String regetCipbMk) {
        this.regetCipbMk = regetCipbMk;
    }

    public String getOriIssuYm() {
        return oriIssuYm;
    }

    public void setOriIssuYm(String oriIssuYm) {
        this.oriIssuYm = oriIssuYm;
    }

    public String getBrChkDate() {
        return brChkDate;
    }

    public void setBrChkDate(String brChkDate) {
        this.brChkDate = brChkDate;
    }

    public BigDecimal getRemitAmt() {
        return remitAmt;
    }

    public void setRemitAmt(BigDecimal remitAmt) {
        this.remitAmt = remitAmt;
    }

    public String getIssuKind() {
        return issuKind;
    }

    public void setIssuKind(String issuKind) {
        this.issuKind = issuKind;
    }

    public String getBrNote() {
        return brNote;
    }

    public void setBrNote(String brNote) {
        this.brNote = brNote;
    }

    public String getCloseCauseStr() {
        return closeCauseStr;
    }

    public void setCloseCauseStr(String closeCauseStr) {
        this.closeCauseStr = closeCauseStr;
    }

    public String getSpecialAcc() {
        return specialAcc;
    }

    public void setSpecialAcc(String specialAcc) {
        this.specialAcc = specialAcc;
    }

    public String getSpeAccDate() {
        return speAccDate;
    }

    public void setSpeAccDate(String speAccDate) {
        this.speAccDate = speAccDate;
    }

    public String getPayBankIdBranchId() {
        return payBankIdBranchId;
    }

    public void setPayBankIdBranchId(String payBankIdBranchId) {
        this.payBankIdBranchId = payBankIdBranchId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPrpNo() {
        return prpNo;
    }

    public void setPrpNo(String prpNo) {
        this.prpNo = prpNo;
    }

    public String getEndYm() {
        return endYm;
    }

    public void setEndYm(String endYm) {
        this.endYm = endYm;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getLastUser() {
        return lastUser;
    }

    public void setLastUser(String lastUser) {
        this.lastUser = lastUser;
    }

    public String getLeterType() {
        return leterType;
    }

    public void setLeterType(String leterType) {
        this.leterType = leterType;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm;
    }

    public String getMinApNo() {
        return minApNo;
    }

    public void setMinApNo(String minApNo) {
        this.minApNo = minApNo;
    }

    public String getMaxApNo() {
        return maxApNo;
    }

    public void setMaxApNo(String maxApNo) {
        this.maxApNo = maxApNo;
    }

    public BigDecimal getRecCordCount() {
        return recCordCount;
    }

    public void setRecCordCount(BigDecimal recCordCount) {
        this.recCordCount = recCordCount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getReceiveMk() {
        return receiveMk;
    }

    public void setReceiveMk(String receiveMk) {
        this.receiveMk = receiveMk;
    }
    
    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getIdnNo() {
        return idnNo;
    }

    public void setIdnNo(String idnNo) {
        this.idnNo = idnNo;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getBkAccountDt() {
        return bkAccountDt;
    }

    public void setBkAccountDt(String bkAccountDt) {
        this.bkAccountDt = bkAccountDt;
    }

    public String getUnqualifiedCause() {
        return unqualifiedCause;
    }

    public void setUnqualifiedCause(String unqualifiedCause) {
        this.unqualifiedCause = unqualifiedCause;
    }

    public String getIssuNotifyingMk() {
        return issuNotifyingMk;
    }

    public void setIssuNotifyingMk(String issuNotifyingMk) {
        this.issuNotifyingMk = issuNotifyingMk;
    }

    public String getSinsuranceSalary() {
        return sinsuranceSalary;
    }

    public void setSinsuranceSalary(String sinsuranceSalary) {
        this.sinsuranceSalary = sinsuranceSalary;
    }

    public BigDecimal getMarginAmt() {
        return marginAmt;
    }

    public void setMarginAmt(BigDecimal marginAmt) {
        this.marginAmt = marginAmt;
    }

    public String getEvtJobAge() {
        return evtJobAge;
    }

    public void setEvtJobAge(String evtJobAge) {
        this.evtJobAge = evtJobAge;
    }

    public BigDecimal getSamTY() {
        return samTY;
    }

    public void setSamTY(BigDecimal samTY) {
        this.samTY = samTY;
    }

    public BigDecimal getSamTD() {
        return samTD;
    }

    public void setSamTD(BigDecimal samTD) {
        this.samTD = samTD;
    }

    public BigDecimal getOnceTY() {
        return onceTY;
    }

    public void setOnceTY(BigDecimal onceTY) {
        this.onceTY = onceTY;
    }

    public BigDecimal getOncePayM() {
        return oncePayM;
    }

    public void setOncePayM(BigDecimal oncePayM) {
        this.oncePayM = oncePayM;
    }

    public BigDecimal getOnceIssueAmt() {
        return onceIssueAmt;
    }

    public void setOnceIssueAmt(BigDecimal onceIssueAmt) {
        this.onceIssueAmt = onceIssueAmt;
    }

    public String getComRechkYm() {
        return comRechkYm;
    }

    public void setComRechkYm(String comRechkYm) {
        this.comRechkYm = comRechkYm;
    }
    
    public String getStatus_E() {
        return status_E;
    }

    public void setStatus_E(String status_E) {
        this.status_E = status_E;
    }
    
    public String getStatusDate_E() {
        return statusDate_E;
    }

    public void setStatusDate_E(String statusDate_E) {
        this.statusDate_E = statusDate_E;
    }

    public String getStatus_S() {
        return status_S;
    }

    public void setStatus_S(String status_S) {
        this.status_S = status_S;
    }

    public String getStatusDate_S() {
        return statusDate_S;
    }

    public void setStatusDate_S(String statusDate_S) {
        this.statusDate_S = statusDate_S;
    }

    public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

    public String getIdForConfirm() {
        return idForConfirm;
    }

    public void setIdForConfirm(String idForConfirm) {
        this.idForConfirm = idForConfirm;
    }

    public BigDecimal getInheritorAmt() {
        return inheritorAmt;
    }

    public void setInheritorAmt(BigDecimal inheritorAmt) {
        this.inheritorAmt = inheritorAmt;
    }

    public String getInsAvgAmtAppYear() {
        return insAvgAmtAppYear;
    }

    public void setInsAvgAmtAppYear(String insAvgAmtAppYear) {
        this.insAvgAmtAppYear = insAvgAmtAppYear;
    }

    public String getCpiBaseYM() {
        return cpiBaseYM;
    }

    public void setCpiBaseYM(String cpiBaseYM) {
        this.cpiBaseYM = cpiBaseYM;
    }
    
	public String getProcStatMk() {
		return procStatMk;
	}

	public void setProcStatMk(String procStatMk) {
		this.procStatMk = procStatMk;
	}

	public String getMtestMk() {
		return mtestMk;
	}

	public void setMtestMk(String mtestMk) {
		this.mtestMk = mtestMk;
	}

	public String getRemitDate() {
		return remitDate;
	}

	public void setRemitDate(String remitDate) {
		this.remitDate = remitDate;
	}

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

}
