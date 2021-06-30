package tw.gov.bli.ba.services;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.owasp.encoder.Encode;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.bj.cases.CompPaymentCase;
import tw.gov.bli.ba.dao.*;
import tw.gov.bli.ba.domain.*;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.query.cases.AnnuityStatistics3Case;
import tw.gov.bli.ba.query.cases.AnnuityStatistics3DtlCase;
import tw.gov.bli.ba.query.cases.AnnuityStatistics3MetaDtlCase;
import tw.gov.bli.ba.query.cases.AnnuityStatisticsCase;
import tw.gov.bli.ba.query.cases.CheckMarkLevelQueryCase;
import tw.gov.bli.ba.query.cases.ExecutiveSupportDataCase;
import tw.gov.bli.ba.query.cases.ExecutiveSupportQueryCase;
import tw.gov.bli.ba.query.cases.PaymentQueryAlreadyReceiveCase;
import tw.gov.bli.ba.query.cases.PaymentQueryApplyDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryArclistDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryArclistDetailDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryAvgAmtDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryBenDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryBenExtDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryChkFileDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryCpiDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryDetailDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryDisabledDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryFamilyDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryIssuPayDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryIssuPayExtDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryLetterTypeMkCase;
import tw.gov.bli.ba.query.cases.PaymentQueryLoanDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryMasterCase;
import tw.gov.bli.ba.query.cases.PaymentQueryNpDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryNpIssuDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryOccAccDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryOncePayDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryReFeesDetailDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryReFeesMasterDataCase;
import tw.gov.bli.ba.query.cases.PaymentQuerySeniDataCase;
import tw.gov.bli.ba.query.cases.PaymentQuerySeniExtDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryUnqualifiedNoticeDataCase;
import tw.gov.bli.ba.query.cases.ReceivableQueryDetailCase;
import tw.gov.bli.ba.query.cases.ReceivableQueryMasterCase;
import tw.gov.bli.ba.query.cases.UpdateLogQueryCase;
import tw.gov.bli.ba.query.cases.UpdateLogQueryDetailCase;
import tw.gov.bli.ba.update.cases.DisabledApplicationDataUpdateBareCheckCase;
import tw.gov.bli.ba.update.cases.DisabledApplicationDataUpdateCase;
import tw.gov.bli.ba.update.cases.DisabledApplicationDataUpdateCheckFileCase;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Service for 查詢作業
 * 
 * @author
 */
public class QueryService {
	private static Log log = LogFactory.getLog(QueryService.class);

	private BaappbaseDao baappbaseDao;
	private BapadchkDao bapadchkDao;
	private BaapplogDao baapplogDao;
	private BadaprDao badaprDao;
	private BachkfileDao bachkfileDao;
	private BasenimaintDao basenimaintDao;
	private CiptDao ciptDao;
	private BaoncepayDao baoncepayDao;
	private BaunacprecDao baunacprecDao;
	private BaunacpdtlDao baunacpdtlDao;
	// private BapaallocateDao bapaallocateDao;
	private Bbcmf09Dao bbcmf09Dao;
	private PbbmsaDao pbbmsaDao;
	private BirefDao birefDao;
	private NbappbaseDao nbappbaseDao;
	private BaparamDao baparamDao;
	private MaadmrecDao maadmrecDao;
	private BaappexpandDao baappexpandDao;
	private Bbcmf07Dao bbcmf07Dao;
	private BafamilyDao bafamilyDao;
	private Babcml7Dao babcml7Dao;
	private BastudtermDao bastudtermDao;
	private BahandicaptermDao bahandicaptermDao;
	private CipgDao cipgDao;
	private BacpirecDao bacpirecDao;
	private BacompelnopayDao bacompelnopayDao;
	private BaarclistDao baarclistDao;
	private NbdaprDao nbdaprDao;
	private BapaavgmonDao bapaavgmonDao;
	private BadupeidnDao badupeidnDao;
	private BarecheckDao barecheckDao;
	private BabasicamtDao babasicamtDao;
	private NpcodeDao npcodeDao;
	private BaunqualifiednoticeDao baunqualifiednoticeDao;
	private BansfDao bansfDao;
	private CipbDao cipbDao;
	private CaubDao caubDao;
	private CvldtlDao cvldtlDao;

	/**
	 * 依傳入的 受理編號 自 給付主檔 (<code>BAAPPBASE</code>) 及 給付資料更正記錄檔 (<code>BAAPPLOG</code>)
	 * 取得 <code>UpdateLogQueryCase</code><br>
	 * for : 查詢作業 - 更正日誌查詢<br>
	 * 
	 * @param payCode 給付別
	 * @prarm updateDateBegin 處理時間 (起)
	 * @param updateDateEnd 處理時間 (迄)
	 * @param apNo          受理編號
	 * @param benIdnNo      身分證號
	 * @param updUser       更正人員
	 * @return
	 */
	public UpdateLogQueryCase getUpdateLogQueryCaseBy(String payCode, String updateDateBegin, String updateDateEnd,
			String apNo, String benIdnNo, String updUser) {
		log.debug("執行 QueryService.getUpdateLogQueryCaseBy() 開始 ...");

		UpdateLogQueryCase caseData = new UpdateLogQueryCase();

		String sUpdateDateBegin = updateDateBegin;
		String sUpdateDateEnd = updateDateEnd;

		// 處理時間 民國轉西元
		if (StringUtils.length(sUpdateDateBegin) == 7)
			sUpdateDateBegin = DateUtility.changeDateType(sUpdateDateBegin);
		if (StringUtils.length(sUpdateDateEnd) == 7)
			sUpdateDateEnd = DateUtility.changeDateType(sUpdateDateEnd);

		// 塞查詢條件
		caseData.setPayCode(payCode); // 給付別
		caseData.setUpdateDateBegin(sUpdateDateBegin); // 處理時間 (起)
		caseData.setUpdateDateEnd(sUpdateDateEnd); // 處理時間 (迄)
		caseData.setApNo(apNo); // 受理編號
		caseData.setBenIdnNo(benIdnNo); // 身分證號
		caseData.setUpdUser(updUser); // 更正人員

		// 取得欲查詢之更正日誌資料
		List<UpdateLogQueryDetailCase> detailCaseList = new ArrayList<UpdateLogQueryDetailCase>();
		List<Baapplog> logList = baapplogDao.selectLogListBy(payCode, sUpdateDateBegin, sUpdateDateEnd, apNo, benIdnNo,
				updUser);

		for (Baapplog logData : logList) {
			UpdateLogQueryDetailCase detailCaseData = new UpdateLogQueryDetailCase();

			detailCaseData.setBaappbaseId(logData.getBaappbaseId()); // 資料列編號
			detailCaseData.setStatus(logData.getStatus()); // 狀態
			detailCaseData.setUpdTime(logData.getUpdTime()); // 異動時間
			detailCaseData.setUpdUser(logData.getUpdUser()); // 異動人員
			detailCaseData.setUpCol(logData.getUpCol()); // 異動項目
			detailCaseData.setBvalue(logData.getBvalue()); // 改前內容
			detailCaseData.setAvalue(logData.getAvalue()); // 改後內容
			detailCaseData.setApNo(logData.getApNo()); // 受理編號
			detailCaseData.setSeqNo(logData.getSeqNo()); // 序號
			detailCaseData.setBenIdnNo(logData.getBenIdnNo()); // 受益人身分證號

			detailCaseList.add(detailCaseData);
		}

		caseData.setUpdateLogList(detailCaseList);

		return caseData;
	}

	/**
	 * 依傳入的 受理編號 自 給付主檔 (<code>Baappbase</code>) 取得
	 * <code>ExecutiveSupportQueryCase資料</code> <br>
	 * for : 查詢作業 - 行政支援查詢
	 * 
	 * @param apno 受理編號
	 * @return <code>Baappbase</code>; 若無資料回傳 <code>null</code>
	 */
	public ExecutiveSupportQueryCase getExecutiveSupportQueryBy(String apno) {
		log.debug("執行 QueryService.getExecutiveSupportQueryBy() 開始 ...");

		Baappbase bappbase = baappbaseDao.selectExecutiveSupportQueryBy(apno);
		ExecutiveSupportQueryCase queryCase = new ExecutiveSupportQueryCase();
		if (bappbase != null) {
			bappbase.setEvtBrDate(
					DateUtility.changeDateType((String) ObjectUtils.defaultIfNull(bappbase.getEvtBrDate(), "")));
			BeanUtility.copyProperties(queryCase, bappbase);
		}
		log.debug("執行 QueryService.getExecutiveSupportQueryBy() 完成 ...");

		return queryCase;
	}

	/**
	 * 依傳入的 受理編號 自 行政支援記錄檔 (<code>MAADMREC</code>) 取得
	 * <code>List<ExecutiveSupportDataCase>資料</code> <br>
	 * for : 查詢作業 - 行政支援查詢
	 * 
	 * @param apNo   受理編號
	 * @param issuYm 核定年月
	 * @return <code>List<ExecutiveSupportDataCase></code>; 若無資料回傳 <code>null</code>
	 */
	public List<ExecutiveSupportDataCase> getExecutiveSupportQueryListBy(String apNo, String idNo, String issuYm) {
		log.debug("執行 QueryService.getExecutiveSupportQueryListBy() 開始 ...");

		List<Maadmrec> list = maadmrecDao.selectExecutiveSupportQueryListBy(apNo, idNo, issuYm);
		List<ExecutiveSupportDataCase> caseList = new ArrayList<ExecutiveSupportDataCase>();

		for (int i = 0; i < list.size(); i++) {
			Maadmrec maadmrec = list.get(i);
			ExecutiveSupportDataCase caseData = new ExecutiveSupportDataCase();
			BeanUtility.copyProperties(caseData, maadmrec);

			caseData.setIssuYm(DateUtility.changeWestYearMonthType(maadmrec.getIssuYm()));
			caseData.setProDate(DateUtility.changeDateType(maadmrec.getProDate()));
			caseData.setClosDate(DateUtility.changeDateType(maadmrec.getClosDate()));
			caseData.setSlDate(DateUtility.changeDateType(maadmrec.getSlDate()));
			caseData.setFinishDate(DateUtility.changeDateType(maadmrec.getFinishDate()));
			caseData.setClosDate(DateUtility.changeDateType(maadmrec.getClosDate()));
			if (StringUtils.isNotEmpty(maadmrec.getReliefStat()))
				caseData.setReliefStat(baparamDao.selectParamNameBy(ConstantKey.BAPARAM_PARAMTYP_SHARE,
						ConstantKey.BAPARAM_PARAMGROUP_RELIEFKIND + maadmrec.getReliefKind(),
						maadmrec.getReliefStat()));
			if (StringUtils.isNotEmpty(maadmrec.getReliefKind()))
				caseData.setReliefKind(baparamDao.selectParamNameBy(ConstantKey.BAPARAM_PARAMTYP_SHARE,
						ConstantKey.BAPARAM_PARAMGROUP_RELIEFKIND, maadmrec.getReliefKind()));
			if (StringUtils.isNotEmpty(maadmrec.getReliefTyp()))
				caseData.setReliefTyp(baparamDao.selectParamNameBy(ConstantKey.BAPARAM_PARAMTYP_SHARE,
						ConstantKey.BAPARAM_PARAMGROUP_RELIEFTYP, maadmrec.getReliefTyp()));

			caseList.add(caseData);
		}

		log.debug("執行 QueryService.getExecutiveSupportQueryListBy() 完成 ...");

		return caseList;
	}

	/**
	 * 依傳入的 給付種類 自 編審註記參數檔 (<code>BAPADCHK</code>) 取得
	 * <code>CheckMarkMaintCase</code><br>
	 * for : 維護作業 - 先抽對象維護作業<br>
	 * 
	 * @param payKind 給付種類
	 * @return <code>CheckMarkMaintCase</code>; 若無資料回傳 <code>null</code>
	 */
	public List<CheckMarkLevelQueryCase> getCheckMarkLevelQueryCaseBy(String chkObj, String chkGroup, String chkCode) {
		log.debug("執行 QueryService.getCheckMarkLevelQueryCaseBy() 開始 ...");

		List<Bapadchk> bapadchkList = bapadchkDao.selectData(chkObj, chkGroup, chkCode);
		List<CheckMarkLevelQueryCase> caseList = new ArrayList<CheckMarkLevelQueryCase>();
		for (int i = 0; i < bapadchkList.size(); i++) {
			Bapadchk obj = bapadchkList.get(i);
			CheckMarkLevelQueryCase caseObj = new CheckMarkLevelQueryCase();
			BeanUtility.copyProperties(caseObj, obj);
			caseList.add(caseObj);
		}

		log.debug("執行 QueryService.getCheckMarkLevelQueryCaseBy() 完成 ...");

		return caseList;
	}

	/**
	 * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 給付查詢MasterData
	 * 
	 * @param apNo      受理編號
	 * @param benIdnNo  受款人身分證號
	 * @param benName   受款人姓名
	 * @param benBrDate 受款人出生日期
	 * @param qryCond   查詢條件
	 * @param startYm   查詢年月(起)
	 * @param endYm     查詢年月(迄)
	 * @return 內含 <code>Baappbase</code> 物件的 List
	 */
	public List<PaymentQueryMasterCase> selectPaymentQueryMasterData(String apNo, String benIdnNo, String benName,
			String benBrDate, String qryCond, String startYm, String endYm) {
		List<Baappbase> dataList = baappbaseDao.selectPaymentQueryMasterDataBy(apNo, benIdnNo, benName, benBrDate,
				qryCond, startYm, endYm);
		List<PaymentQueryMasterCase> caseList = new ArrayList<PaymentQueryMasterCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Baappbase obj = dataList.get(i);
			PaymentQueryMasterCase caseObj = new PaymentQueryMasterCase();
			BeanUtility.copyProperties(caseObj, obj);
			caseList.add(caseObj);
		}
		return caseList;
	}

	/**
	 * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 給付查詢MasterDetail
	 * 
	 * @param apNo    受理編號
	 * @param payKind 給付種類
	 * @param payKind 給付別
	 * @return 內含 <code>PaymentQueryMasterDetailCase</code> 物件
	 */
	public PaymentQueryDetailDataCase getPaymentQueryDetail(String apNo, String payKind, String pagePayKind) {
		String sBMPAYKND = "";
		String sBMAPNO = "";
		List<Baappbase> dataList = baappbaseDao.selectPaymentQueryDetail(apNo, payKind, pagePayKind);
		PaymentQueryDetailDataCase caseObj = null;
		if (dataList.size() == 1) {
			Baappbase obj = dataList.get(0);
			caseObj = new PaymentQueryDetailDataCase();
			BeanUtility.copyProperties(caseObj, obj);

			// 查詢之資料為遺屬年金時，需額外處理已領年金資料
			if (StringUtils.equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_S, pagePayKind)) {
				if (StringUtils.equals(StringUtils.substring(caseObj.getDabApNo(), 0, 1),
						ConstantKey.BAAPPBASE_PAGE_PAYKIND_L)) {
					caseObj.setOldApNo(caseObj.getDabApNo());
					caseObj.setOldAnnuAmt(badaprDao.selectBefissueAmtForPaymentQuery(caseObj.getDabApNo()));
				} else if (StringUtils.equals(StringUtils.substring(caseObj.getDabApNo(), 0, 1),
						ConstantKey.BAAPPBASE_PAGE_PAYKIND_K)) {
					caseObj.setDisApNo(caseObj.getDabApNo());
					caseObj.setDisAnnuAmt(badaprDao.selectBefissueAmtForPaymentQuery(caseObj.getDabApNo()));
				}
			}

			// 20111019 失能、老年年金查詢之資料的結案原因為01時，需查詢差額金受理編號、差額金金額、差額金核付日期之值
			if (StringUtils.equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_L, pagePayKind)
					|| StringUtils.equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_K, pagePayKind)) {
				if (StringUtils.equals(caseObj.getCloseCauseCode(), "01")) {

					if (StringUtils.equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_K, pagePayKind)) {
						sBMPAYKND = "3";
						sBMAPNO = "39";
					} else {
						sBMPAYKND = "4";
						sBMAPNO = "49";
					}

					List<Pbbmsa> differenceDataList = pbbmsaDao.selectDifferenceDetail(caseObj.getEvtIdnNo(),
							caseObj.getEvtBrDate(), sBMPAYKND, sBMAPNO);
					if (differenceDataList.size() == 1) {
						Pbbmsa obj1 = differenceDataList.get(0);
						BeanUtility.copyProperties(caseObj, obj1);
						caseObj.setBmApNo(caseObj.getBmApNo());
						caseObj.setBmPayDte(caseObj.getBmPayDte());
						caseObj.setBmChkAmt(caseObj.getBmChkAmt());
					}
				}
			}

			// 20130724新增失能年金給付查詢 - 併計國保年資 註記欄位 begin
			// 取得 BAAPPBASE 資料
			// 取得 BaappbaseId
			Baappbase masterData = baappbaseDao.getDisabledApplicationDataUpdateMasterDataForRehcBy(apNo);

			if (StringUtils.equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_K, pagePayKind)) {

				if (masterData != null) {
					// 取得 BACHKFILE 資料 - 編審註記資料
					// [
					List<Bachkfile> checkFileList = bachkfileDao
							.getDisabledApplicationDataUpdateCheckFileListBy(masterData.getBaappbaseId());
					for (Bachkfile checkFileData : checkFileList) {
						DisabledApplicationDataUpdateCheckFileCase checkFileCase = new DisabledApplicationDataUpdateCheckFileCase();
						checkFileCase.setPayYm(checkFileData.getPayYm()); // 給付年月
						checkFileCase.setChkFileName(checkFileData.getChkFileName()); // 編審註記代號
						checkFileCase.setChkFileDesc(checkFileData.getChkFileDesc()); // 編審註記代號
						checkFileCase.setChkFileCode(checkFileData.getChkFileCode()); // 編審註記代號
						caseObj.addCheckFileData(checkFileCase);
					}
					// ]
					// 20130812修改
					if (caseObj.isContainCheckMark3M()) {
						String comnpMk = baappexpandDao.selectComnpmkBy(masterData.getBaappbaseId(), caseObj.getApNo(),
								caseObj.getSeqNo());
						caseObj.setComnpMk(comnpMk);
					}

				}
			}

			// 20151023 失能、遺屬年金增加顯示「寄發月通知表」
			// [
			String monNotifyingMk = baappexpandDao.selectMonnotifyingmkBy(masterData.getBaappbaseId(),
					caseObj.getApNo(), caseObj.getSeqNo());
			caseObj.setMonNotifyingMk(monNotifyingMk);
			// ]

			// 20180518 失能、遺屬年金增加顯示「普職註記」、「加職註記」
			// [
			Baappexpand baappexpandData = baappexpandDao.getBaappexpandDataForWkMkBy(masterData.getBaappbaseId(),
					caseObj.getApNo());
			if (baappexpandData != null) {
				caseObj.setAdWkMk(baappexpandData.getAdWkMk());
				caseObj.setNlWkMk(baappexpandData.getNlWkMk());
			}
			// ]

			BigDecimal lecomAmt = badaprDao.getDisableReviewRpt01BenPayDataByLecomAmt(apNo);
			Integer cutAmt = (caseObj.getCutAmt() == null) ? new Integer(0) : caseObj.getCutAmt().intValue();
			Integer lecomAmtint = (lecomAmt == null || "".equals(lecomAmt.toString())) ? new Integer(0) : lecomAmt.intValue();
			Integer recomAmt = (cutAmt - lecomAmtint);

			caseObj.setLecomAmt(new BigDecimal(lecomAmtint));
			caseObj.setRecomAmt(new BigDecimal(recomAmt));

			// if (StringUtils.equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_K, pagePayKind) &&
			// StringUtils.equals(caseObj.getPayKind(), "38")) {
			// String comnpMk = baappexpandDao.selectComnpmkBy(caseObj.getBaappbaseId(),
			// caseObj.getApNo(), caseObj.getSeqNo());
			// caseObj.setComnpMk((StringUtils.equals("Y", StringUtils.upperCase(comnpMk)) ?
			// "Y" : "N"));
			// }
			// 20130724新增失能年金給付查詢 - 併計國保年資 註記欄位 end

		}
		return caseObj;
	}

	/**
	 * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 給付查詢作業 明細資料表頭-核定/給付年月資料
	 * 
	 * @param apNo    受理編號
	 * @param startYm 查詢年月起
	 * @param endYm   查詢年月迄
	 * @param qryCond 查詢條件
	 * @return list.get(0) = 原始查詢結果List ; list.get(1) = 處理過後之List
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getPaymentQueryIssuPayDataList(String apNo, String startYm, String endYm, String qryCond) {
		List<Badapr> dataList = badaprDao.selectPaymentQueryIssuPayDataBy(apNo, startYm, endYm, qryCond);
		List<PaymentQueryIssuPayDataCase> groupList = new ArrayList<PaymentQueryIssuPayDataCase>();
		List<Object> returnList = new ArrayList<Object>();

		// 建立資料Map
		// qryCond = ISSUYM, Map Key = issuYm
		// qryCond = PAYYM, Map Key = payYm
		// Value則是同為該 issuYm／payYm 下的所有給付核定資料
		Map<String, List<PaymentQueryIssuPayExtDataCase>> map = new LinkedHashMap<String, List<PaymentQueryIssuPayExtDataCase>>();
		for (int i = 0; i < dataList.size(); i++) {
			Badapr obj = (Badapr) dataList.get(i);
			if (StringUtils.equals(qryCond, "ISSUYM")) {
				map.put(obj.getIssuYm(), new ArrayList<PaymentQueryIssuPayExtDataCase>());
			} else if (StringUtils.equals(qryCond, "PAYYM")) {
				map.put(obj.getPayYm(), new ArrayList<PaymentQueryIssuPayExtDataCase>());
			}
		}

		for (int i = 0; i < dataList.size(); i++) {
			Badapr obj = (Badapr) dataList.get(i);
			PaymentQueryIssuPayExtDataCase caseObj = new PaymentQueryIssuPayExtDataCase();
			BeanUtility.copyProperties(caseObj, obj);
			if (StringUtils.equals(qryCond, "ISSUYM")) {
				(map.get(obj.getIssuYm())).add(caseObj);
			} else if (StringUtils.equals(qryCond, "PAYYM")) {
				(map.get(obj.getPayYm())).add(caseObj);
			}
		}

		// 將 分類好的 map 轉為 list
		for (Iterator it = map.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			List<PaymentQueryIssuPayExtDataCase> subDataList = map.get(key);
			PaymentQueryIssuPayDataCase caseObj = new PaymentQueryIssuPayDataCase();
			List<PaymentQueryIssuPayExtDataCase> issuExtData = map.get(key);

			if (StringUtils.equals(qryCond, "ISSUYM")) {
				caseObj.setIssuYm(key);
			} else if (StringUtils.equals(qryCond, "PAYYM")) {
				caseObj.setPayYm(key);
			}

			// 建立子資料 SubMap
			// qryCond = ISSUYM, SubMap Key = payYm
			// qryCond = PAYYM, SubMap Key = issuYm
			// Value則是同為該 issuYm／payYm 下的所有給付核定資料
			// ------------------------------------------------------------------------
			Map<String, List<PaymentQueryIssuPayExtDataCase>> subMap = new LinkedHashMap<String, List<PaymentQueryIssuPayExtDataCase>>();
			for (int i = 0; i < subDataList.size(); i++) {
				PaymentQueryIssuPayExtDataCase obj = (PaymentQueryIssuPayExtDataCase) subDataList.get(i);
				if (StringUtils.equals(qryCond, "ISSUYM")) {
					subMap.put(obj.getPayYm(), new ArrayList<PaymentQueryIssuPayExtDataCase>());
				} else if (StringUtils.equals(qryCond, "PAYYM")) {
					subMap.put(obj.getIssuYm(), new ArrayList<PaymentQueryIssuPayExtDataCase>());
				}
			}

			for (int i = 0; i < subDataList.size(); i++) {
				PaymentQueryIssuPayExtDataCase subCaseObj = (PaymentQueryIssuPayExtDataCase) subDataList.get(i);
				if (StringUtils.equals(qryCond, "ISSUYM")) {
					(subMap.get(subCaseObj.getPayYm())).add(subCaseObj);
				} else if (StringUtils.equals(qryCond, "PAYYM")) {
					(subMap.get(subCaseObj.getIssuYm())).add(subCaseObj);
				}
			}

			// 將 分類好的 SubMap 轉為 list
			List<PaymentQueryIssuPayDataCase> subReturnList = new ArrayList<PaymentQueryIssuPayDataCase>();
			for (Iterator subIt = subMap.keySet().iterator(); subIt.hasNext();) {
				String subKey = (String) subIt.next();
				List<PaymentQueryIssuPayExtDataCase> tempList = subMap.get(subKey);
				PaymentQueryIssuPayDataCase subCaseObj = new PaymentQueryIssuPayDataCase();
				subCaseObj.setApNo(apNo);
				subCaseObj.setIssuYm(tempList.get(0).getIssuYm());
				subCaseObj.setPayYm(tempList.get(0).getPayYm());
				subCaseObj.setAplpayDate(tempList.get(0).getAplpayDate());
				subCaseObj.setStexpndDate(tempList.get(0).getStexpndDate());
				subCaseObj.setIssuPayExtDataList(tempList);

				// 檢查受理清單PDF是否存在
				// 2009.10.05 修改路徑/nps/ap/ba_rpt/final_rpt/→/nps/ba_rpt/final_rpt/
				// String fileUrl = "/nps/ap/ba_rpt/final_rpt/" + caseObj.getApNo() + "_" +
				// caseObj.getIssuYm() + "_F.pdf";
				String fileUrl = PropertyHelper.getProperty("rpt.path.final.rpt") + subCaseObj.getApNo() + "_"
						+ subCaseObj.getIssuYm() + "_F.pdf";
				File file = new File(Encode.forJava(fileUrl));
				if (file.exists()) {
					subCaseObj.setViewFlag("Y");
				} else {
					subCaseObj.setViewFlag("N");
				}

				subReturnList.add(subCaseObj);
			}
			// ------------------------------------------------------------------------

			caseObj.setIssuPayDataList(subReturnList);
			groupList.add(caseObj);
		}
		returnList.add(0, dataList);// 原始查詢結果List
		returnList.add(1, groupList);// 處理過後之List
		return returnList;

		// //
		// --------------------------------------------------------------------------------
		// // 建立資料Map
		// // Map的Key 為 issuYm+payYm
		// // Value則是同為該 issuYm+payYm 下的所有編審註記資料
		// Map<String, List<PaymentQueryIssuPayExtDataCase>> map = new
		// LinkedHashMap<String, List<PaymentQueryIssuPayExtDataCase>>();
		// for (int i = 0; i < dataList.size(); i++) {
		// Badapr obj = (Badapr) dataList.get(i);
		// map.put(obj.getIssuYm() + ";" + obj.getAplpayDate() + ";" + obj.getPayYm(),
		// new ArrayList<PaymentQueryIssuPayExtDataCase>());
		// }
		//
		// for (int i = 0; i < dataList.size(); i++) {
		// Badapr obj = (Badapr) dataList.get(i);
		// PaymentQueryIssuPayExtDataCase caseObj = new
		// PaymentQueryIssuPayExtDataCase();
		// BeanUtility.copyProperties(caseObj, obj);
		// (map.get(obj.getIssuYm() + ";" + obj.getAplpayDate() + ";" +
		// obj.getPayYm())).add(caseObj);
		// }
		//
		// // 將 分類好的 map 轉為 list
		// for (Iterator it = map.keySet().iterator(); it.hasNext();) {
		// String key = (String) it.next();
		// List<PaymentQueryIssuPayExtDataCase> tempList = map.get(key);
		// PaymentQueryIssuPayDataCase caseObj = new PaymentQueryIssuPayDataCase();
		// List<PaymentQueryIssuPayExtDataCase> issuExtData = map.get(key);
		//
		// String[] tmpStr = key.split(";");
		// String issuYm = tmpStr[0];
		// String aplpayDate = tmpStr[1];
		// String payYm = tmpStr[2];
		// caseObj.setApNo(apNo);
		// caseObj.setIssuYm(issuYm);
		// caseObj.setAplpayDate(aplpayDate);
		// caseObj.setPayYm(payYm);
		// caseObj.setIssuPayExtData(issuExtData);
		//
		// if (returnList.size() == 0) {
		// caseObj.setIsShowIssuYm("Y");
		// caseObj.setIsShowAplpayDate("Y");
		// caseObj.setIsShowPayYm("Y");
		// }
		// else {
		// int liteSize = returnList.size();
		// PaymentQueryIssuPayDataCase beforeCase = returnList.get(liteSize - 1);
		// if (("ISSUYM").equals(qryCond)) {
		// if (caseObj.getIssuYm().equals(beforeCase.getIssuYm()) &&
		// caseObj.getAplpayDate().equals(beforeCase.getAplpayDate())) {
		// caseObj.setIsShowIssuYm("N");
		// caseObj.setIsShowAplpayDate("N");
		// }
		// else if (caseObj.getIssuYm().equals(beforeCase.getIssuYm()) &&
		// !caseObj.getAplpayDate().equals(beforeCase.getAplpayDate())) {
		// caseObj.setIsShowIssuYm("N");
		// caseObj.setIsShowAplpayDate("Y");
		// }
		// else {
		// caseObj.setIsShowIssuYm("Y");
		// caseObj.setIsShowAplpayDate("Y");
		// }
		//
		// caseObj.setIsShowPayYm("Y");
		// }
		// else if (("PAYYM").equals(qryCond)) {
		// if (caseObj.getPayYm().equals(beforeCase.getPayYm()) &&
		// caseObj.getAplpayDate().equals(beforeCase.getAplpayDate())) {
		// caseObj.setIsShowPayYm("N");
		// caseObj.setIsShowAplpayDate("N");
		// }
		// else if (caseObj.getPayYm().equals(beforeCase.getPayYm()) &&
		// !caseObj.getAplpayDate().equals(beforeCase.getAplpayDate())) {
		// caseObj.setIsShowPayYm("N");
		// caseObj.setIsShowAplpayDate("Y");
		// }
		// else {
		// caseObj.setIsShowPayYm("Y");
		// caseObj.setIsShowAplpayDate("Y");
		// }
		// caseObj.setIsShowIssuYm("Y");
		// }
		// }
		//
		// // 檢查受理清單PDF是否存在
		// // 2009.10.05 修改路徑/nps/ap/ba_rpt/final_rpt/→/nps/ba_rpt/final_rpt/
		// // String fileUrl = "/nps/ap/ba_rpt/final_rpt/" + caseObj.getApNo() + "_" +
		// caseObj.getIssuYm() + "_F.pdf";
		// String fileUrl = "/nps/ap/ba_rpt/final_rpt/" + caseObj.getApNo() + "_" +
		// caseObj.getIssuYm() + "_F.pdf";
		// File file = new File(fileUrl);
		// if (file.exists()) {
		// caseObj.setViewFlag("Y");
		// }
		// else {
		// caseObj.setViewFlag("N");
		// }
		// returnList.add(caseObj);
		// }
		//
		// // // 檢查受理清單PDF是否存在
		// // String fileUrl = "/nps/ba_rpt/final_rpt/" + caseObj.getApNo() + "_" +
		// caseObj.getIssuYm() + "_F.pdf";
		// // File file = new File(fileUrl);
		// // if (file.exists()) {
		// // caseObj.setViewFlag("Y");
		// // }
		// // else {
		// // caseObj.setViewFlag("N");
		// // }
		// // caseList.add(caseObj);
		// // }
		//
		// return returnList;
	}

	/**
	 * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 給付查詢作業 受款人下拉選單
	 * 
	 * @param issuPayDataList 核定年月資料
	 * @return 內含 <code>Baappbase</code> 物件的 List
	 */
	@SuppressWarnings("unchecked")
	public List<Baappbase> getSeqNoOptionList(List<Badapr> issuPayDataList) {
		List<Baappbase> returnList = new ArrayList<Baappbase>();
		// 建立資料Map
		// Map Key = seqNo
		// Value 為該 baappbase obj
		Map<String, Baappbase> map = new TreeMap<String, Baappbase>();
		for (int i = 0; i < issuPayDataList.size(); i++) {
			Badapr caseObj = (Badapr) issuPayDataList.get(i);
			Baappbase obj = new Baappbase();
			obj.setSeqNo(caseObj.getSeqNo());
			obj.setBenName(caseObj.getBenName());
			map.put(caseObj.getSeqNo(), obj);
		}

		// 將 分類好的 map 轉為 list
		for (Iterator it = map.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			returnList.add(map.get(key));
		}

		return returnList;
	}

	/**
	 * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 給付查詢作業 核定年月下拉選單
	 * 
	 * @param issuPayDataList 核定年月資料
	 * @return 內含 <code>Badapr</code> 物件的 List
	 */
	@SuppressWarnings("unchecked")
	public List<Badapr> getIssuYmOptionList(List<Badapr> issuPayDataList) {
		List<Badapr> returnList = new ArrayList<Badapr>();
		// 建立資料Map
		// Map Key = IssuYm
		// Value 為該 Badapr obj
		Map<String, Badapr> map = new TreeMap<String, Badapr>();
		for (int i = 0; i < issuPayDataList.size(); i++) {
			Badapr caseObj = (Badapr) issuPayDataList.get(i);
			Badapr obj = new Badapr();
			obj.setIssuYm(caseObj.getIssuYm());
			obj.setIssuYmStr(DateUtility.changeWestYearMonthType(caseObj.getIssuYm()));
			map.put(caseObj.getIssuYm(), obj);
		}

		// 將 分類好的 map 轉為 list
		for (Iterator it = map.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			returnList.add(map.get(key));
		}

		return returnList;
	}

	/**
	 * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 給付查詢作業 給付年月下拉選單
	 * 
	 * @param issuPayDataList 核定年月資料
	 * @return 內含 <code>Badapr</code> 物件的 List
	 */
	@SuppressWarnings("unchecked")
	public List<Badapr> getPayYmOptionList(List<Badapr> issuPayDataList) {
		List<Badapr> returnList = new ArrayList<Badapr>();
		// 建立資料Map
		// Map Key = PayYm
		// Value 為該 Badapr obj
		Map<String, Badapr> map = new TreeMap<String, Badapr>();
		for (int i = 0; i < issuPayDataList.size(); i++) {
			Badapr caseObj = (Badapr) issuPayDataList.get(i);
			Badapr obj = new Badapr();
			obj.setPayYm(caseObj.getPayYm());
			obj.setPayYmStr(DateUtility.changeWestYearMonthType(caseObj.getPayYm()));
			map.put(caseObj.getPayYm(), obj);
		}

		// 將 分類好的 map 轉為 list
		for (Iterator it = map.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			returnList.add(map.get(key));
		}

		return returnList;
	}

	/**
	 * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 給付查詢作業 給付明細查詢 - 核定/給付資料明細
	 * 
	 * @param apNo   受理編號
	 * @param issuYm 核定年月
	 * @param payYm  給付年月
	 * @return <code>PaymentQueryIssuPayDataCase</code> 物件
	 */
	public PaymentQueryIssuPayDataCase getPaymentQueryIssuPayDetail(String apNo, String issuYm, String payYm) {
		List<Badapr> dataList = badaprDao.selectPaymentQueryIssuPayDetailBy(apNo, issuYm, payYm);
		PaymentQueryIssuPayDataCase caseObj = new PaymentQueryIssuPayDataCase();
		if (dataList.size() == 1) {
			Badapr obj = dataList.get(0);
			BeanUtility.copyProperties(caseObj, obj);
		}
		return caseObj;
	}

	/**
	 * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 事故者/受款人資料
	 * 
	 * @param apNo 受理編號
	 * @return 內含 <code>Baappbase</code> 物件的 List
	 */
	public List<PaymentQueryBenDataCase> getBeneficiaryData(String apNo, String qryCond, String startYm, String endYm) {
		List<Baappbase> benDataList = baappbaseDao.selectBeneficiaryDataBy(apNo);
		List<PaymentQueryBenDataCase> returnList = new ArrayList<PaymentQueryBenDataCase>();
		for (int i = 0; i < benDataList.size(); i++) {
			Baappbase obj = benDataList.get(i);
			PaymentQueryBenDataCase caseObj = new PaymentQueryBenDataCase();
			BeanUtility.copyProperties(caseObj, obj);
			// 取得 受款人核定資料
			// caseObj.setBenIssuDataList(getBenIssuDataList(caseObj.getApNo(),
			// caseObj.getSeqNo(), qryCond, startYm, endYm));
			// 取得 編審註記資料
			caseObj.setBenChkDataList(
					getBenChkDataList(caseObj.getApNo(), caseObj.getSeqNo(), qryCond, startYm, endYm));
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 核定資料
	 * 
	 * @param apNo 受理編號
	 * @return 內含 <code>Baappbase</code> 物件的 List
	 */
	public List<PaymentQueryBenDataCase> getIssuData(String apNo, String qryCond, String startYm, String endYm) {
		List<Baappbase> benDataList = baappbaseDao.selectBeneficiaryDataBy(apNo);
		List<PaymentQueryBenDataCase> returnList = new ArrayList<PaymentQueryBenDataCase>();
		for (int i = 0; i < benDataList.size(); i++) {
			Baappbase obj = benDataList.get(i);
			PaymentQueryBenDataCase caseObj = new PaymentQueryBenDataCase();
			BeanUtility.copyProperties(caseObj, obj);
			// 取得 受款人核定資料
			caseObj.setBenIssuDataList(
					getBenIssuDataList(caseObj.getApNo(), caseObj.getSeqNo(), qryCond, startYm, endYm));
			// 取得 編審註記資料
			// caseObj.setBenChkDataList(getBenChkDataList(caseObj.getApNo(),
			// caseObj.getSeqNo(), qryCond, startYm, endYm));
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人核定資料
	 * 
	 * @param apNo    受理編號
	 * @param seqNo   序號
	 * @param qryCond 查詢條件
	 * @param startYm 查詢年月起
	 * @param endYm   查詢年月迄
	 * @return 內含 <code>PaymentQueryBenExtDataCase</code> 物件的 List
	 */
	public List<PaymentQueryBenExtDataCase> getBenIssuDataList(String apNo, String seqNo, String qryCond,
			String startYm, String endYm) {
		List<Baappbase> benIssuDataList = baappbaseDao.selectBenIssuDataForPaymentQueryBy(apNo, seqNo, qryCond, startYm,
				endYm);
		List<PaymentQueryBenExtDataCase> returnList = new ArrayList<PaymentQueryBenExtDataCase>();
		for (int i = 0; i < benIssuDataList.size(); i++) {
			Baappbase obj = benIssuDataList.get(i);
			PaymentQueryBenExtDataCase caseObj = new PaymentQueryBenExtDataCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 受款人編審註記資料
	 * 
	 * @param apNo    受理編號
	 * @param seqNo   序號
	 * @param qryCond 查詢條件
	 * @param startYm 查詢年月起
	 * @param endYm   查詢年月迄
	 * @return <code>Bachkfile</code> 物件 List
	 */
	@SuppressWarnings("unchecked")
	public List<PaymentQueryBenExtDataCase> getBenChkDataList(String apNo, String seqNo, String qryCond, String startYm,
			String endYm) {
		List<Bachkfile> benChkDataList = bachkfileDao.selectBenChkDataForPaymentQueryBy(apNo, seqNo, qryCond, startYm,
				endYm);
		List<PaymentQueryBenExtDataCase> returnList = new ArrayList<PaymentQueryBenExtDataCase>();
		// List<Bachkfile> list = bachkfileDao.selectBenChkDataBy(apNo, seqNo);
		// List<PaymentReviewExtCase> returnList = new
		// ArrayList<PaymentReviewExtCase>();

		// 建立資料Map
		// Map的Key 為 issuYm+payYm
		// Value則是同為該 issuYm+payYm 下的所有編審註記資料
		Map<String, List<PaymentQueryChkFileDataCase>> map = new TreeMap<String, List<PaymentQueryChkFileDataCase>>();
		for (int i = 0; i < benChkDataList.size(); i++) {
			Bachkfile obj = (Bachkfile) benChkDataList.get(i);
			if (("ISSUYM").equals(qryCond)) {
				map.put(obj.getIssuYmStr() + "-" + obj.getPayYmStr(), new ArrayList<PaymentQueryChkFileDataCase>());
			} else {
				map.put(obj.getPayYmStr() + "-" + obj.getIssuYmStr(), new ArrayList<PaymentQueryChkFileDataCase>());
			}
		}

		// 當編審註記有出現BD時,需顯示'戶籍姓名'欄位(Disabled)
		for (int i = 0; i < benChkDataList.size(); i++) {
			Bachkfile obj = (Bachkfile) benChkDataList.get(i);
			PaymentQueryChkFileDataCase caseObj = new PaymentQueryChkFileDataCase();
			BeanUtility.copyProperties(caseObj, obj);
			if (("ISSUYM").equals(qryCond)) {
				(map.get(obj.getIssuYmStr() + "-" + obj.getPayYmStr())).add(caseObj);
			} else {
				(map.get(obj.getPayYmStr() + "-" + obj.getIssuYmStr())).add(caseObj);
			}
		}

		// 將 分類好的 map 轉為 list
		for (Iterator it = map.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			List<PaymentQueryChkFileDataCase> tempList = map.get(key);
			PaymentQueryBenExtDataCase caseObj = new PaymentQueryBenExtDataCase();
			caseObj.setIssuPayYm(key);
			caseObj.setBenChkDataList(tempList);
			caseObj.setChkFileDataSize(tempList.size());
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 一次給付資料 for 給付審核
	 * 
	 * @param apNo   受理編號
	 * @param issuYm 核定年月
	 * @return 內含 <code>PaymentReviewExtCase</code> 物件的 List
	 */
	public PaymentQueryOncePayDataCase getOncePayData(String apNo, String issuYm) {
		List<Baappbase> baappbaseList = baappbaseDao.selectOncePayDataBy(apNo);
		List<Badapr> badaprList = badaprDao.selectOncePayDataBy(apNo, issuYm);
		List<Baoncepay> baoncepayList = baoncepayDao.selectOncePayDataBy(apNo);

		PaymentQueryOncePayDataCase returnCase = new PaymentQueryOncePayDataCase();

		if (baappbaseList.size() == 1) {
			Baappbase obj = baappbaseList.get(0);
			BeanUtility.copyProperties(returnCase, obj);
		}
		if (badaprList.size() == 1) {
			Badapr obj = badaprList.get(0);
			BeanUtility.copyProperties(returnCase, obj);
		}
		if (baoncepayList.size() == 1) {
			Baoncepay obj = baoncepayList.get(0);
			BeanUtility.copyProperties(returnCase, obj);
		}
		BigDecimal dabAnnuAmt = new BigDecimal(0);
		BigDecimal annuAmt = new BigDecimal(0);
		BigDecimal oncePayAmt = new BigDecimal(0);
		BigDecimal sumPayAmt = new BigDecimal(0);
		if (returnCase.getDabAnnuAmt() != null) {
			dabAnnuAmt = returnCase.getDabAnnuAmt();
		}
		if (returnCase.getAnnuAmt() != null) {
			annuAmt = returnCase.getAnnuAmt();
		}
		if (returnCase.getOncePayAmt() != null) {
			oncePayAmt = returnCase.getOncePayAmt();
		}
		if (returnCase.getSumPayAmt() != null) {
			sumPayAmt = returnCase.getSumPayAmt();
		}

		returnCase.setSumPayAmt(dabAnnuAmt.add(annuAmt));
		returnCase.setDiffAmt(oncePayAmt.subtract(sumPayAmt));
		return returnCase;
	}

	/**
	 * 依傳入條件取得 年資維護介接檔(<code>BASENIMAINT</code>) 一次給付更正 for 給付審核
	 * 
	 * @param apNo 受理編號
	 * @return 內含 <code>PaymentDecisionExtCase</code> 物件的 List
	 */
	public List<PaymentQueryOncePayDataCase> getOncePayModifyData(String apNo) {
		List<Basenimaint> list = basenimaintDao.selectDataBy(apNo, null, "1");
		List<PaymentQueryOncePayDataCase> returnList = new ArrayList<PaymentQueryOncePayDataCase>();
		for (int i = 0; i < list.size(); i++) {
			Basenimaint obj = list.get(i);
			PaymentQueryOncePayDataCase caseObj = new PaymentQueryOncePayDataCase();
			caseObj.setOwesBdate(obj.getOwesBdate());
			caseObj.setOwesEdate(obj.getOwesEdate());
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 給付查詢作業 給付明細查詢 - 年資資料
	 * 
	 * @param apNo 受理編號
	 * @return 內含 <code>PaymentQuerySeniDataCase</code> 物件的 List
	 */
	public PaymentQuerySeniDataCase getSeniData(String apNo, String idn) {
		List<Badapr> seniDataList = badaprDao.selectSeniDataBy(apNo);
		PaymentQuerySeniDataCase caseObj = new PaymentQuerySeniDataCase();
		if (seniDataList.size() == 1) {
			Badapr obj = seniDataList.get(0);
			BeanUtility.copyProperties(caseObj, obj);
			// 取得 欠費期間資料
			caseObj.setSeniMaintDataList(getSeniMaintData(apNo));
			// 取得 承保異動資料
			caseObj.setTxcdDataList(getTxcdData(apNo, idn));
		}
		return caseObj;
	}

	/**
	 * 依傳入條件取得 年資維護介接檔(<code>BASENIMAINT</code>) 欠費期間資料
	 * 
	 * @param apNo 受理編號
	 * @return 內含 <code>PaymentQuerySeniExtDataCase</code> 物件的 List
	 */
	public List<PaymentQuerySeniExtDataCase> getSeniMaintData(String apNo) {
		List<Basenimaint> dataList = basenimaintDao.selectDataBy(apNo, null, "0");
		List<PaymentQuerySeniExtDataCase> returnList = new ArrayList<PaymentQuerySeniExtDataCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Basenimaint obj = dataList.get(i);
			PaymentQuerySeniExtDataCase caseObj = new PaymentQuerySeniExtDataCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 被保險人異動資料檔(<code>CIPT</code>) 承保異動資料
	 * 
	 * @param idn 身分證號
	 * @return 內含 <code>PaymentQuerySeniExtDataCase</code> 物件的 List
	 */
	public List<PaymentQuerySeniExtDataCase> getTxcdData(String apNo, String idn) {
		List<Cipt> dataList = ciptDao.selectTxcdDataBy(apNo, "0000", idn, "L");
		List<PaymentQuerySeniExtDataCase> returnList = new ArrayList<PaymentQuerySeniExtDataCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Cipt obj = dataList.get(i);
			PaymentQuerySeniExtDataCase caseObj = new PaymentQuerySeniExtDataCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;
	}

	// /**
	// * 取得 承辦人分案原則參數檔(<code>BAPAALLOCATE</code>) 承辦人分機號碼資料
	// *
	// * @param empNo 員工編號
	// * @param payCode 給付別
	// * @return <code>empExt</code> String物件
	// */
	// public String getEmpExtData(String empNo, String payCode) {
	// return bapaallocateDao.selectEmpExtDataBy(empNo, payCode);
	// }

	/**
	 * 取得 現金給付分案尾碼原則檔(<code>BBCMF09</code>) 承辦人分機號碼資料
	 * 
	 * @param payKind 給付種類
	 * @return <code>data2</code> String物件
	 */
	public String getEmpExtData(String apNo) {
		return bbcmf09Dao.selectData2By(apNo);
	}

	/**
	 * 依傳入條件取得 應收帳務記錄檔(<code>BAUNACPREC</code>) 資料 for 應收查詢
	 * 
	 * @param apNo       受理編號
	 * @param benIdnNo   事故者身分證號
	 * @param unacpSdate 立帳起日
	 * @param unacpEdate 立帳迄日
	 * @return 內含 <code>ReceivableQueryMasterCase</code> 物件的 List
	 */
	public List<ReceivableQueryMasterCase> getReceivableMasterList(String apNo, String benIdnNo, String unacpSdate,
			String unacpEdate) {
		List<Baunacprec> list = baunacprecDao.selectReceivableDataBy(apNo, benIdnNo, unacpSdate, unacpEdate);
		List<ReceivableQueryMasterCase> returnList = new ArrayList<ReceivableQueryMasterCase>();
		for (int i = 0; i < list.size(); i++) {
			Baunacprec obj = list.get(i);
			ReceivableQueryMasterCase caseObj = new ReceivableQueryMasterCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 應收帳務明細檔(<code>BAUNACPDTL</code>) 資料 for 應收查詢
	 * 
	 * @param apNo      受理編號
	 * @param evtIdnNo  事故者身分證號
	 * @param unacpDate 應收立帳日期
	 * @return 內含 <code>ReceivableQueryDetailCase</code> 物件的 List
	 */
	public List<ReceivableQueryDetailCase> getReceivableDetailList(String apNo, String evtIdnNo, String unacpDate) {
		List<Baunacpdtl> list = baunacpdtlDao.selectReceivableDetailBy(apNo, evtIdnNo, unacpDate, null);
		List<ReceivableQueryDetailCase> returnList = new ArrayList<ReceivableQueryDetailCase>();
		for (int i = 0; i < list.size(); i++) {
			Baunacpdtl obj = list.get(i);
			ReceivableQueryDetailCase caseObj = new ReceivableQueryDetailCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 應收帳務明細檔(<code>BAUNACPDTL</code>) 資料 for 給付查詢
	 * 
	 * @param apNo   受理編號
	 * @param issuYm 核定年月
	 * @return 內含 <code>PaymentQueryAlreadyReceiveCase</code> 物件的 List
	 */
	public List<PaymentQueryAlreadyReceiveCase> getPaymentQueryAlreadyReceiveData(String apNo, String issuYm) {
		List<Baunacpdtl> list = baunacpdtlDao.selectReceivableDetailBy(apNo, null, null, issuYm);
		List<PaymentQueryAlreadyReceiveCase> returnList = new ArrayList<PaymentQueryAlreadyReceiveCase>();
		for (int i = 0; i < list.size(); i++) {
			Baunacpdtl obj = list.get(i);
			PaymentQueryAlreadyReceiveCase caseObj = new PaymentQueryAlreadyReceiveCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 請領同類／他類／另案扣減 - 一次給付資料
	 * 
	 * @param evtIdnNo  事故者身分證號
	 * @param evtBrDate 事故者出生日期
	 * @return <code>PaymentQueryApplyDataCase</code> 物件
	 */
	public PaymentQueryApplyDataCase getOncePayDetailData(String evtIdnNo, String evtBrDate) {
		List<Pbbmsa> list = pbbmsaDao.selectOncePayDataForPaymentBy(evtIdnNo, evtBrDate);
		PaymentQueryApplyDataCase caseObj = null;
		if (list.size() == 1) {
			Pbbmsa obj = list.get(0);
			caseObj = new PaymentQueryApplyDataCase();
			BeanUtility.copyProperties(caseObj, obj);
		}
		return caseObj;
	}

	/**
	 * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 請領同類／他類／另案扣減 - 年金給付資料
	 * 
	 * @param apNo      受理編號
	 * @param evtIdnNo  事故者身分證號
	 * @param evtBrDate 事故者出生日期
	 * @return 內含 <code>PaymentQueryApplyDataCase</code> 物件的 List
	 */
	public List<PaymentQueryApplyDataCase> getAnnuityPayData(String apNo, String evtIdnNo, String evtBrDate) {
		List<Baappbase> list = baappbaseDao.selectAnnuityPayDataBy(apNo, evtIdnNo, evtBrDate);
		List<PaymentQueryApplyDataCase> returnList = new ArrayList<PaymentQueryApplyDataCase>();
		for (int i = 0; i < list.size(); i++) {
			Baappbase obj = list.get(i);
			PaymentQueryApplyDataCase caseObj = new PaymentQueryApplyDataCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 請領同類／他類／另案扣減 - 申請失能給付紀錄資料
	 * 
	 * @param evtIdnNo  事故者身分證號
	 * @param evtBrDate 事故者出生日期
	 * @return 內含 <code>PaymentReviewExtCase</code> 物件的 List
	 */
	public List<PaymentQueryApplyDataCase> getCriPayApplyData(String evtIdnNo, String evtBrDate) {
		List<Pbbmsa> list = pbbmsaDao.selectCriPayApplyDataForPaymentBy(evtIdnNo, evtBrDate);
		List<PaymentQueryApplyDataCase> returnList = new ArrayList<PaymentQueryApplyDataCase>();
		for (int i = 0; i < list.size(); i++) {
			Pbbmsa obj = list.get(i);
			PaymentQueryApplyDataCase caseObj = new PaymentQueryApplyDataCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 請領同類／他類／另案扣減 - 申請死亡給付紀錄資料
	 * 
	 * @param evtIdnNo  事故者身分證號
	 * @param evtBrDate 事故者出生日期
	 * @return <code>PaymentQueryApplyDataCase</code> 物件
	 */
	public PaymentQueryApplyDataCase getDiePayApplyData(String evtIdnNo, String evtBrDate) {
		List<Pbbmsa> list = pbbmsaDao.selectDiePayApplyDataForPaymentBy(evtIdnNo, evtBrDate);
		PaymentQueryApplyDataCase caseObj = null;
		if (list.size() == 1) {
			Pbbmsa obj = list.get(0);
			caseObj = new PaymentQueryApplyDataCase();
			BeanUtility.copyProperties(caseObj, obj);
		}
		return caseObj;
	}

	/**
	 * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 請領同類／他類／另案扣減 - 申請傷病給付紀錄資料
	 * 
	 * @param evtIdnNo  事故者身分證號
	 * @param evtBrDate 事故者出生日期
	 * @return 內含 <code>PaymentQueryApplyDataCase</code> 物件的 List
	 */
	public List<PaymentQueryApplyDataCase> getInjPayApplyData(String evtIdnNo, String evtBrDate) {
		List<Pbbmsa> list = pbbmsaDao.selectInjPayApplyDataForPaymentBy(evtIdnNo, evtBrDate);
		List<PaymentQueryApplyDataCase> returnList = new ArrayList<PaymentQueryApplyDataCase>();
		for (int i = 0; i < list.size(); i++) {
			Pbbmsa obj = list.get(i);
			PaymentQueryApplyDataCase caseObj = new PaymentQueryApplyDataCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 就保給付檔(<code>BIREF</code>) 請領同類／他類／另案扣減 - 申請失業給付紀錄資料
	 * 
	 * @param evtIdnNo  身分證號
	 * @param evtBrDate 出生日期
	 * @return 內含 <code>PaymentQueryApplyDataCase</code> 物件的 List
	 */
	public List<PaymentQueryApplyDataCase> getUnEmpPayApplyData(String evtIdnNo, String evtBrDate) {
		List<Biref> list = birefDao.selectUnEmpDataBy(evtIdnNo, evtBrDate);
		List<PaymentQueryApplyDataCase> returnList = new ArrayList<PaymentQueryApplyDataCase>();
		for (int i = 0; i < list.size(); i++) {
			Biref obj = list.get(i);
			PaymentQueryApplyDataCase caseObj = new PaymentQueryApplyDataCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 國保給付主檔(<code>NBAPPBASE</code>) 申請國保給付紀錄資料
	 * 
	 * @param evtIds 事故者社福識別碼
	 * @return <code>PaymentQueryApplyDataCase</code> 物件
	 */
	public PaymentQueryApplyDataCase getNpPayApplyData(String evtIds) {
		List<Nbappbase> list = nbappbaseDao.selectNpPayDataBy(evtIds);
		PaymentQueryApplyDataCase caseObj = null;
		if (list.size() == 1) {
			Nbappbase obj = list.get(0);
			caseObj = new PaymentQueryApplyDataCase();
			BeanUtility.copyProperties(caseObj, obj);
		}
		return caseObj;
	}

	// /**
	// * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) XX簽函資料
	// *
	// * @param apNo 受理編號
	// * @return
	// */
	// public PaymentQueryDetailDataCase getLetterTypeMk(PaymentQueryDetailDataCase
	// caseObj) {
	// String apNo = caseObj.getApNo();
	//
	// // 交查函註記
	// Maadmrec mk1 = getLetterTypeMk1(apNo);
	// caseObj.setLetterTypeMk1(mk1.getProDate());
	// caseObj.setLetterTypeMk1Title(mk1.getNdomk1());
	//
	// // 不給付函註記
	// Maadmrec mk2 = getLetterTypeMk2(apNo);
	// caseObj.setLetterTypeMk2(mk2.getProDate());
	// caseObj.setLetterTypeMk2Title(mk2.getNdomk1());
	//
	// // 補件通知函註記
	// Maadmrec mk3 = getLetterTypeMk3(apNo);
	// caseObj.setLetterTypeMk3(mk3.getProDate());
	// caseObj.setLetterTypeMk3Title(mk3.getNdomk1());
	//
	// // 照會函註記
	// Maadmrec mk4 = getLetterTypeMk4(apNo);
	// caseObj.setLetterTypeMk4(mk4.getProDate());
	// caseObj.setLetterTypeMk4Title(mk4.getNdomk1());
	//
	// // 其他簽函註記
	// Maadmrec mk5 = getLetterTypeMk5(apNo);
	// caseObj.setLetterTypeMk5(mk5.getProDate());
	// caseObj.setLetterTypeMk5Title(mk5.getNdomk1());
	//
	// return caseObj;
	// }
	/**
	 * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 交查函資料
	 * 
	 * @param apNo 受理編號
	 * @return 內含<code>PaymentQueryLetterTypeMkCase</code>物件的List
	 */
	public List<PaymentQueryLetterTypeMkCase> getLetterTypeMk1List(String apNo) {
		List<Maadmrec> list = maadmrecDao.selectLetterTypeMk1(apNo);
		List<PaymentQueryLetterTypeMkCase> returnList = new ArrayList<PaymentQueryLetterTypeMkCase>();
		for (int i = 0; i < list.size(); i++) {
			Maadmrec obj = list.get(i);
			PaymentQueryLetterTypeMkCase caseObj = new PaymentQueryLetterTypeMkCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 不給付函資料
	 * 
	 * @param apNo 受理編號
	 * @return 內含<code>PaymentQueryLetterTypeMkCase</code>物件的List
	 */
	public List<PaymentQueryLetterTypeMkCase> getLetterTypeMk2List(String apNo) {
		List<Maadmrec> list = maadmrecDao.selectLetterTypeMk2(apNo);
		List<PaymentQueryLetterTypeMkCase> returnList = new ArrayList<PaymentQueryLetterTypeMkCase>();
		for (int i = 0; i < list.size(); i++) {
			Maadmrec obj = list.get(i);
			PaymentQueryLetterTypeMkCase caseObj = new PaymentQueryLetterTypeMkCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;

	}

	/**
	 * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 補件通知函資料
	 * 
	 * @param apNo 受理編號
	 * @return 內含<code>PaymentQueryLetterTypeMkCase</code>物件的List
	 */
	public List<PaymentQueryLetterTypeMkCase> getLetterTypeMk3List(String apNo) {
		List<Maadmrec> list = maadmrecDao.selectLetterTypeMk3(apNo);
		List<PaymentQueryLetterTypeMkCase> returnList = new ArrayList<PaymentQueryLetterTypeMkCase>();
		for (int i = 0; i < list.size(); i++) {
			Maadmrec obj = list.get(i);
			PaymentQueryLetterTypeMkCase caseObj = new PaymentQueryLetterTypeMkCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 照會函資料
	 * 
	 * @param apNo 受理編號
	 * @return 內含<code>PaymentQueryLetterTypeMkCase</code>物件的List
	 */
	public List<PaymentQueryLetterTypeMkCase> getLetterTypeMk4List(String apNo) {
		List<Maadmrec> list = maadmrecDao.selectLetterTypeMk4(apNo);
		List<PaymentQueryLetterTypeMkCase> returnList = new ArrayList<PaymentQueryLetterTypeMkCase>();
		for (int i = 0; i < list.size(); i++) {
			Maadmrec obj = list.get(i);
			PaymentQueryLetterTypeMkCase caseObj = new PaymentQueryLetterTypeMkCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 其他簽函資料
	 * 
	 * @param apNo 受理編號
	 * @return 內含<code>PaymentQueryLetterTypeMkCase</code>物件的List
	 */
	public List<PaymentQueryLetterTypeMkCase> getLetterTypeMk5List(String apNo) {
		List<Maadmrec> list = maadmrecDao.selectLetterTypeMk5(apNo);
		List<PaymentQueryLetterTypeMkCase> returnList = new ArrayList<PaymentQueryLetterTypeMkCase>();
		for (int i = 0; i < list.size(); i++) {
			Maadmrec obj = list.get(i);
			PaymentQueryLetterTypeMkCase caseObj = new PaymentQueryLetterTypeMkCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料
	 * 
	 * @param apNo 受理編號
	 * @return <code>PaymentQueryLetterTypeMkCase</code> 物件
	 */
	public PaymentQueryLetterTypeMkCase getLetterTypeMk6List(String apNo) {
		List<Maadmrec> list = maadmrecDao.selectLetterTypeMk6(apNo);
		PaymentQueryLetterTypeMkCase caseObj = new PaymentQueryLetterTypeMkCase();
		if (list.size() >= 1) {
			Maadmrec obj = list.get(0);
			BeanUtility.copyProperties(caseObj, obj);
			String kind = getLetterTypeMk6Kind(obj.getReliefKind());
			String stat = getLetterTypeMk6Stat(obj.getReliefKind(), obj.getReliefStat());
			caseObj.setReliefKind(kind);
			caseObj.setReliefStat(stat);
		}

		return caseObj;
	}

	/**
	 * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 交查函資料
	 * 
	 * @param apNo 受理編號
	 * @return 內含<code>PaymentQueryLetterTypeMkCase</code>物件的List
	 */
	public List<PaymentQueryLetterTypeMkCase> getLetterTypeMk1ListK(String apNo) {
		List<Maadmrec> list = maadmrecDao.selectLetterTypeMk1K(apNo);
		List<PaymentQueryLetterTypeMkCase> returnList = new ArrayList<PaymentQueryLetterTypeMkCase>();
		for (int i = 0; i < list.size(); i++) {
			Maadmrec obj = list.get(i);
			PaymentQueryLetterTypeMkCase caseObj = new PaymentQueryLetterTypeMkCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 不給付函資料
	 * 
	 * @param apNo 受理編號
	 * @return 內含<code>PaymentQueryLetterTypeMkCase</code>物件的List
	 */
	public List<PaymentQueryLetterTypeMkCase> getLetterTypeMk2ListK(String apNo) {
		List<Maadmrec> list = maadmrecDao.selectLetterTypeMk2K(apNo);
		List<PaymentQueryLetterTypeMkCase> returnList = new ArrayList<PaymentQueryLetterTypeMkCase>();
		for (int i = 0; i < list.size(); i++) {
			Maadmrec obj = list.get(i);
			PaymentQueryLetterTypeMkCase caseObj = new PaymentQueryLetterTypeMkCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;

	}

	/**
	 * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 補件通知函資料
	 * 
	 * @param apNo 受理編號
	 * @return 內含<code>PaymentQueryLetterTypeMkCase</code>物件的List
	 */
	public List<PaymentQueryLetterTypeMkCase> getLetterTypeMk3ListK(String apNo) {
		List<Maadmrec> list = maadmrecDao.selectLetterTypeMk3K(apNo);
		List<PaymentQueryLetterTypeMkCase> returnList = new ArrayList<PaymentQueryLetterTypeMkCase>();
		for (int i = 0; i < list.size(); i++) {
			Maadmrec obj = list.get(i);
			PaymentQueryLetterTypeMkCase caseObj = new PaymentQueryLetterTypeMkCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 照會函資料
	 * 
	 * @param apNo 受理編號
	 * @return 內含<code>PaymentQueryLetterTypeMkCase</code>物件的List
	 */
	public List<PaymentQueryLetterTypeMkCase> getLetterTypeMk4ListK(String apNo) {
		List<Maadmrec> list = maadmrecDao.selectLetterTypeMk4K(apNo);
		List<PaymentQueryLetterTypeMkCase> returnList = new ArrayList<PaymentQueryLetterTypeMkCase>();
		for (int i = 0; i < list.size(); i++) {
			Maadmrec obj = list.get(i);
			PaymentQueryLetterTypeMkCase caseObj = new PaymentQueryLetterTypeMkCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 其他簽函資料
	 * 
	 * @param apNo 受理編號
	 * @return 內含<code>PaymentQueryLetterTypeMkCase</code>物件的List
	 */
	public List<PaymentQueryLetterTypeMkCase> getLetterTypeMk5ListK(String apNo) {
		List<Maadmrec> list = maadmrecDao.selectLetterTypeMk5K(apNo);
		List<PaymentQueryLetterTypeMkCase> returnList = new ArrayList<PaymentQueryLetterTypeMkCase>();
		for (int i = 0; i < list.size(); i++) {
			Maadmrec obj = list.get(i);
			PaymentQueryLetterTypeMkCase caseObj = new PaymentQueryLetterTypeMkCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料
	 * 
	 * @param apNo 受理編號
	 * @return <code>PaymentQueryLetterTypeMkCase</code> 物件
	 */
	public PaymentQueryLetterTypeMkCase getLetterTypeMk6ListK(String apNo) {
		List<Maadmrec> list = maadmrecDao.selectLetterTypeMk6K(apNo);
		PaymentQueryLetterTypeMkCase caseObj = new PaymentQueryLetterTypeMkCase();
		if (list.size() >= 1) {
			Maadmrec obj = list.get(0);
			BeanUtility.copyProperties(caseObj, obj);
			String kind = getLetterTypeMk6Kind(obj.getReliefKind());
			String stat = getLetterTypeMk6Stat(obj.getReliefKind(), obj.getReliefStat());
			caseObj.setReliefKind(kind);
			caseObj.setReliefStat(stat);
		}

		return caseObj;
	}

	/**
	 * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 交查函資料
	 * 
	 * @param apNo 受理編號
	 * @return 內含<code>PaymentQueryLetterTypeMkCase</code>物件的List
	 */
	public List<PaymentQueryLetterTypeMkCase> getLetterTypeMk1ListS(String apNo) {
		List<Maadmrec> list = maadmrecDao.selectLetterTypeMk1S(apNo);
		List<PaymentQueryLetterTypeMkCase> returnList = new ArrayList<PaymentQueryLetterTypeMkCase>();
		for (int i = 0; i < list.size(); i++) {
			Maadmrec obj = list.get(i);
			PaymentQueryLetterTypeMkCase caseObj = new PaymentQueryLetterTypeMkCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 不給付函資料
	 * 
	 * @param apNo 受理編號
	 * @return 內含<code>PaymentQueryLetterTypeMkCase</code>物件的List
	 */
	public List<PaymentQueryLetterTypeMkCase> getLetterTypeMk2ListS(String apNo) {
		List<Maadmrec> list = maadmrecDao.selectLetterTypeMk2S(apNo);
		List<PaymentQueryLetterTypeMkCase> returnList = new ArrayList<PaymentQueryLetterTypeMkCase>();
		for (int i = 0; i < list.size(); i++) {
			Maadmrec obj = list.get(i);
			PaymentQueryLetterTypeMkCase caseObj = new PaymentQueryLetterTypeMkCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;

	}

	/**
	 * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 補件通知函資料
	 * 
	 * @param apNo 受理編號
	 * @return 內含<code>PaymentQueryLetterTypeMkCase</code>物件的List
	 */
	public List<PaymentQueryLetterTypeMkCase> getLetterTypeMk3ListS(String apNo) {
		List<Maadmrec> list = maadmrecDao.selectLetterTypeMk3S(apNo);
		List<PaymentQueryLetterTypeMkCase> returnList = new ArrayList<PaymentQueryLetterTypeMkCase>();
		for (int i = 0; i < list.size(); i++) {
			Maadmrec obj = list.get(i);
			PaymentQueryLetterTypeMkCase caseObj = new PaymentQueryLetterTypeMkCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 照會函資料
	 * 
	 * @param apNo 受理編號
	 * @return 內含<code>PaymentQueryLetterTypeMkCase</code>物件的List
	 */
	public List<PaymentQueryLetterTypeMkCase> getLetterTypeMk4ListS(String apNo) {
		List<Maadmrec> list = maadmrecDao.selectLetterTypeMk4S(apNo);
		List<PaymentQueryLetterTypeMkCase> returnList = new ArrayList<PaymentQueryLetterTypeMkCase>();
		for (int i = 0; i < list.size(); i++) {
			Maadmrec obj = list.get(i);
			PaymentQueryLetterTypeMkCase caseObj = new PaymentQueryLetterTypeMkCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 其他簽函資料
	 * 
	 * @param apNo 受理編號
	 * @return 內含<code>PaymentQueryLetterTypeMkCase</code>物件的List
	 */
	public List<PaymentQueryLetterTypeMkCase> getLetterTypeMk5ListS(String apNo) {
		List<Maadmrec> list = maadmrecDao.selectLetterTypeMk5S(apNo);
		List<PaymentQueryLetterTypeMkCase> returnList = new ArrayList<PaymentQueryLetterTypeMkCase>();
		for (int i = 0; i < list.size(); i++) {
			Maadmrec obj = list.get(i);
			PaymentQueryLetterTypeMkCase caseObj = new PaymentQueryLetterTypeMkCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料
	 * 
	 * @param apNo 受理編號
	 * @return <code>PaymentQueryLetterTypeMkCase</code> 物件
	 */
	public PaymentQueryLetterTypeMkCase getLetterTypeMk6ListS(String apNo) {
		List<Maadmrec> list = maadmrecDao.selectLetterTypeMk6S(apNo);
		PaymentQueryLetterTypeMkCase caseObj = new PaymentQueryLetterTypeMkCase();
		if (list.size() >= 1) {
			Maadmrec obj = list.get(0);
			BeanUtility.copyProperties(caseObj, obj);
			String kind = getLetterTypeMk6Kind(obj.getReliefKind());
			String stat = getLetterTypeMk6Stat(obj.getReliefKind(), obj.getReliefStat());
			caseObj.setReliefKind(kind);
			caseObj.setReliefStat(stat);
		}

		return caseObj;
	}

	/**
	 * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 救濟種類資料
	 * 
	 * @param reliefKind 救濟種類
	 * @return <code>Maadmrec</code> 物件
	 */
	public String getLetterTypeMk6Kind(String reliefKind) {

		return maadmrecDao.selectLetterTypeMk6Kind(reliefKind);
	}

	/**
	 * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 行政救濟辦理情形資料
	 * 
	 * @param reliefKind 救濟種類
	 * @param reliefStat 行政救濟辦理情形
	 * @return <code>Maadmrec</code> 物件
	 */
	public String getLetterTypeMk6Stat(String reliefKind, String reliefStat) {

		return maadmrecDao.selectLetterTypeMk6Stat(reliefKind, reliefStat);
	}

	// /**
	// * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 交查函資料
	// *
	// * @param apNo 受理編號
	// * @return
	// */
	// public Maadmrec getLetterTypeMk1(String apNo) {
	// List<Maadmrec> list = maadmrecDao.selectLetterTypeMk1(apNo);
	// Maadmrec obj = new Maadmrec();
	// if (list.size() >= 1) {
	// obj = list.get(0);
	// }
	// return obj;
	// }
	//
	// /**
	// * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 不給付函資料
	// *
	// * @param apNo 受理編號
	// * @return
	// */
	// public Maadmrec getLetterTypeMk2(String apNo) {
	// List<Maadmrec> list = maadmrecDao.selectLetterTypeMk2(apNo);
	// Maadmrec obj = new Maadmrec();
	// if (list.size() >= 1) {
	// obj = list.get(0);
	// }
	// return obj;
	// }
	//
	// /**
	// * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 補件通知函資料
	// *
	// * @param apNo 受理編號
	// * @return
	// */
	// public Maadmrec getLetterTypeMk3(String apNo) {
	// List<Maadmrec> list = maadmrecDao.selectLetterTypeMk3(apNo);
	// Maadmrec obj = new Maadmrec();
	// if (list.size() >= 1) {
	// obj = list.get(0);
	// }
	// return obj;
	// }
	//
	// /**
	// * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 照會函資料
	// *
	// * @param apNo 受理編號
	// * @return
	// */
	// public Maadmrec getLetterTypeMk4(String apNo) {
	// List<Maadmrec> list = maadmrecDao.selectLetterTypeMk4(apNo);
	// Maadmrec obj = new Maadmrec();
	// if (list.size() >= 1) {
	// obj = list.get(0);
	// }
	// return obj;
	// }
	//
	// /**
	// * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 其他簽函資料
	// *
	// * @param apNo 受理編號
	// * @return
	// */
	// public Maadmrec getLetterTypeMk5(String apNo) {
	// List<Maadmrec> list = maadmrecDao.selectLetterTypeMk5(apNo);
	// Maadmrec obj = new Maadmrec();
	// if (list.size() >= 1) {
	// obj = list.get(0);
	// }
	// return obj;
	// }

	/**
	 * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 資料
	 * 
	 * @param apNo   受理編號
	 * @param seqNo  序號
	 * @param chkTyp 編審註記種類 (A:事故者編審註記, B:眷屬編審註記, C:符合註記)
	 * @return <code>PaymentReviewExtCase</code> 物件 List
	 */
	@SuppressWarnings("unchecked")
	public List<PaymentQueryDetailDataCase> getPaymentQueryEvtChkList(String apNo, String seqNo) {
		List<Bachkfile> list = bachkfileDao.selectReviewChkListBy(apNo, seqNo, "A", null);
		List<PaymentQueryDetailDataCase> returnList = new ArrayList<PaymentQueryDetailDataCase>();

		// 建立資料Map
		// Map的Key 為 issuYm+payYm
		// Value則是同為該 issuYm+payYm 下的所有編審註記資料
		Map<String, List<PaymentQueryChkFileDataCase>> map = new TreeMap<String, List<PaymentQueryChkFileDataCase>>();
		for (int i = 0; i < list.size(); i++) {
			Bachkfile obj = (Bachkfile) list.get(i);
			map.put(obj.getIssuYmStr() + "-" + obj.getPayYmStr(), new ArrayList<PaymentQueryChkFileDataCase>());
		}

		for (int i = 0; i < list.size(); i++) {
			Bachkfile obj = (Bachkfile) list.get(i);
			PaymentQueryChkFileDataCase caseObj = new PaymentQueryChkFileDataCase();
			BeanUtility.copyProperties(caseObj, obj);
			(map.get(obj.getIssuYmStr() + "-" + obj.getPayYmStr())).add(caseObj);
		}

		// 將 分類好的 map 轉為 list
		for (Iterator it = map.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			List<PaymentQueryChkFileDataCase> tempList = map.get(key);
			PaymentQueryDetailDataCase caseObj = new PaymentQueryDetailDataCase();
			caseObj.setIssuPayYm(key);
			caseObj.setChkFileDataList(tempList);
			caseObj.setChkFileDataSize(tempList.size());
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 資料 for 失能年金給付審核
	 * 
	 * @param apNo   受理編號
	 * @param seqNo  序號
	 * @param chkTyp 編審註記種類 (B:眷屬編審註記, C:符合註記)
	 * @return <code>DisabledPaymentReviewExtCase</code> 物件 List
	 */
	@SuppressWarnings("unchecked")
	public List<PaymentQueryDetailDataCase> getPaymentQueryOtherChkList(String apNo, String seqNo, String chkTyp) {
		List<Bachkfile> list = bachkfileDao.selectReviewChkListBy(apNo, seqNo, chkTyp, null);
		List<PaymentQueryDetailDataCase> returnList = new ArrayList<PaymentQueryDetailDataCase>();
		List<PaymentQueryDetailDataCase> subList = new ArrayList<PaymentQueryDetailDataCase>();

		// 建立資料Map
		// Map的Key 為 seqNo
		// Value則是同為該 seqNo 下的所有編審註記資料
		Map<String, List<PaymentQueryChkFileDataCase>> map = new TreeMap<String, List<PaymentQueryChkFileDataCase>>();
		for (int i = 0; i < list.size(); i++) {
			Bachkfile obj = (Bachkfile) list.get(i);
			map.put(obj.getSeqNo(), new ArrayList<PaymentQueryChkFileDataCase>());
		}
		for (int i = 0; i < list.size(); i++) {
			Bachkfile obj = (Bachkfile) list.get(i);
			PaymentQueryChkFileDataCase caseObj = new PaymentQueryChkFileDataCase();
			BeanUtility.copyProperties(caseObj, obj);
			(map.get(caseObj.getSeqNo())).add(caseObj);
		}

		// 將 分類好的 map 轉為 list
		for (Iterator it = map.keySet().iterator(); it.hasNext();) {
			subList = new ArrayList<PaymentQueryDetailDataCase>();
			String key = (String) it.next();
			List<PaymentQueryChkFileDataCase> tempList = map.get(key);

			// 建立資料SubMap
			// Map的Key 為 issuYm+payYm
			// Value則是同為該 issuYm+payYm 下的所有編審註記資料
			Map<String, List<PaymentQueryChkFileDataCase>> subMap = new TreeMap<String, List<PaymentQueryChkFileDataCase>>();
			for (int i = 0; i < tempList.size(); i++) {
				PaymentQueryChkFileDataCase obj = (PaymentQueryChkFileDataCase) tempList.get(i);
				subMap.put(obj.getIssuYmStr() + "-" + obj.getPayYmStr(), new ArrayList<PaymentQueryChkFileDataCase>());
			}

			for (int i = 0; i < tempList.size(); i++) {
				PaymentQueryChkFileDataCase obj = (PaymentQueryChkFileDataCase) tempList.get(i);
				(subMap.get(obj.getIssuYmStr() + "-" + obj.getPayYmStr())).add(obj);
			}

			// 將 分類好的 SubMap 轉為 list
			List<PaymentQueryChkFileDataCase> subTempList = new ArrayList<PaymentQueryChkFileDataCase>();
			for (Iterator subIt = subMap.keySet().iterator(); subIt.hasNext();) {
				String subKey = (String) subIt.next();
				subTempList = subMap.get(subKey);
				PaymentQueryDetailDataCase caseObj = new PaymentQueryDetailDataCase();
				caseObj.setIssuPayYm(subKey);
				caseObj.setChkFileDataList(subTempList);
				caseObj.setChkFileDataSize(subTempList.size());
				subList.add(caseObj);
			}
			if (subTempList.size() != 0) {
				PaymentQueryDetailDataCase masterCase = new PaymentQueryDetailDataCase();
				masterCase.setSeqNo(key);
				masterCase.setOtherChkDataList(subList);
				returnList.add(masterCase);
			}
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 資料 for 遺屬年金給付查詢
	 * 
	 * @param apNo   受理編號
	 * @param seqNo  序號
	 * @param chkTyp 編審註記種類 (A:事故者編審註記, B:眷屬編審註記, C:符合註記)
	 * @return <code>PaymentQueryDetailDataCase</code> 物件 List
	 */
	@SuppressWarnings("unchecked")
	public List<PaymentQueryDetailDataCase> getPaymentQueryEvtChkListForSurvivor(String apNo, String seqNo) {
		List<Bachkfile> list = bachkfileDao.selectReviewChkListBy(apNo, seqNo, "A", null);
		List<PaymentQueryDetailDataCase> returnList = new ArrayList<PaymentQueryDetailDataCase>();

		// 建立資料Map
		// Map的Key 為 issuYm+payYm
		// Value則是同為該 issuYm+payYm 下的所有編審註記資料
		Map<String, List<PaymentQueryChkFileDataCase>> map = new TreeMap<String, List<PaymentQueryChkFileDataCase>>();
		for (int i = 0; i < list.size(); i++) {
			Bachkfile obj = (Bachkfile) list.get(i);
			map.put(obj.getIssuYmStr() + "-" + obj.getPayYmStr(), new ArrayList<PaymentQueryChkFileDataCase>());
		}

		for (int i = 0; i < list.size(); i++) {
			Bachkfile obj = (Bachkfile) list.get(i);
			PaymentQueryChkFileDataCase caseObj = new PaymentQueryChkFileDataCase();
			BeanUtility.copyProperties(caseObj, obj);
			(map.get(obj.getIssuYmStr() + "-" + obj.getPayYmStr())).add(caseObj);
		}

		// 將 分類好的 map 轉為 list
		for (Iterator it = map.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			List<PaymentQueryChkFileDataCase> tempList = map.get(key);
			PaymentQueryDetailDataCase caseObj = new PaymentQueryDetailDataCase();
			caseObj.setIssuPayYm(key);
			caseObj.setChkFileDataList(tempList);
			caseObj.setChkFileDataSize(tempList.size());
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 資料 for 遺屬年金給付查詢
	 * 
	 * @param apNo   受理編號
	 * @param seqNo  序號
	 * @param chkTyp 編審註記種類 (B:眷屬編審註記, C:符合註記)
	 * @return <code>PaymentQueryDetailDataCase</code> 物件 List
	 */
	@SuppressWarnings("unchecked")
	public List<PaymentQueryDetailDataCase> getPaymentQueryOtherChkListForSurvivor(String apNo, String chkTyp) {
		List<Bachkfile> list = bachkfileDao.selectChkFileForSurvivorPaymentReview(apNo, chkTyp, null);
		List<PaymentQueryDetailDataCase> returnList = new ArrayList<PaymentQueryDetailDataCase>();
		List<PaymentQueryDetailDataCase> subList = new ArrayList<PaymentQueryDetailDataCase>();

		// 建立資料Map
		// Map的Key 為 seqNo
		// Value則是同為該 seqNo 下的所有編審註記資料
		Map<String, List<PaymentQueryChkFileDataCase>> map = new TreeMap<String, List<PaymentQueryChkFileDataCase>>();
		for (int i = 0; i < list.size(); i++) {
			Bachkfile obj = (Bachkfile) list.get(i);
			map.put(obj.getSeqNo(), new ArrayList<PaymentQueryChkFileDataCase>());
		}
		for (int i = 0; i < list.size(); i++) {
			Bachkfile obj = (Bachkfile) list.get(i);
			PaymentQueryChkFileDataCase caseObj = new PaymentQueryChkFileDataCase();
			BeanUtility.copyProperties(caseObj, obj);
			(map.get(caseObj.getSeqNo())).add(caseObj);
		}

		// 將 分類好的 map 轉為 list
		for (Iterator it = map.keySet().iterator(); it.hasNext();) {
			subList = new ArrayList<PaymentQueryDetailDataCase>();
			String key = (String) it.next();
			List<PaymentQueryChkFileDataCase> tempList = map.get(key);

			// 建立資料SubMap
			// Map的Key 為 issuYm+payYm
			// Value則是同為該 issuYm+payYm 下的所有編審註記資料
			Map<String, List<PaymentQueryChkFileDataCase>> subMap = new TreeMap<String, List<PaymentQueryChkFileDataCase>>();
			for (int i = 0; i < tempList.size(); i++) {
				PaymentQueryChkFileDataCase obj = (PaymentQueryChkFileDataCase) tempList.get(i);
				subMap.put(obj.getIssuYmStr() + "-" + obj.getPayYmStr(), new ArrayList<PaymentQueryChkFileDataCase>());
			}

			for (int i = 0; i < tempList.size(); i++) {
				PaymentQueryChkFileDataCase obj = (PaymentQueryChkFileDataCase) tempList.get(i);
				(subMap.get(obj.getIssuYmStr() + "-" + obj.getPayYmStr())).add(obj);
			}

			// 將 分類好的 SubMap 轉為 list
			List<PaymentQueryChkFileDataCase> subTempList = new ArrayList<PaymentQueryChkFileDataCase>();
			for (Iterator subIt = subMap.keySet().iterator(); subIt.hasNext();) {
				String subKey = (String) subIt.next();
				subTempList = subMap.get(subKey);
				PaymentQueryDetailDataCase caseObj = new PaymentQueryDetailDataCase();
				caseObj.setIssuPayYm(subKey);
				caseObj.setChkFileDataList(subTempList);
				caseObj.setChkFileDataSize(subTempList.size());
				subList.add(caseObj);
			}
			if (subTempList.size() != 0) {
				PaymentQueryDetailDataCase masterCase = new PaymentQueryDetailDataCase();
				masterCase.setSeqNo(key);
				masterCase.setOtherChkDataList(subList);
				returnList.add(masterCase);
			}
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 職災相關資料 for 給付查詢作業
	 * 
	 * @param apNo 受理編號
	 * @return <code>PaymentQueryOccAccDataCase</code>
	 */
	public PaymentQueryOccAccDataCase getOccAccDataForDisabled(String apNo) {
		Badapr badapr = badaprDao.selectOccAccDataForDisabledPaymentReview(apNo);
		PaymentQueryOccAccDataCase returnCase = new PaymentQueryOccAccDataCase();
		if (badapr != null) {
			BeanUtility.copyProperties(returnCase, badapr);
		}
		return returnCase;
	}

	/**
	 * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 失能相關資料 for 給付查詢作業
	 * 
	 * @param apNo 受理編號
	 * @return <code>PaymentQueryDisabledDataCase</code> 物件
	 */
	public PaymentQueryDisabledDataCase selectDisabledDataForDisabled(String apNo) {
		Baappexpand baappexpand = baappexpandDao.selectDisabledDataForPaymentReview(apNo);
		PaymentQueryDisabledDataCase returnCase = new PaymentQueryDisabledDataCase();
		if (baappexpand != null) {
			BeanUtility.copyProperties(returnCase, baappexpand);
			// 取得 醫院簡稱
			returnCase.setHpSnam(bbcmf07Dao.selectHpSnamBy(returnCase.getHosId()));
			// 取得 職病醫院簡稱
			returnCase.setOcAccHpSnam(bbcmf07Dao.selectHpSnamBy(returnCase.getOcAccHosId()));
		}
		return returnCase;
	}

	/**
	 * 依傳入條件取得 眷屬檔(<code>BAFAMILY</code>) 資料 For 失能年金給付查詢作業
	 * 
	 * @param apNo 受理編號
	 * @return 內含<code>Bafamily</code> 物件的List
	 */
	public List<PaymentQueryFamilyDataCase> getFamilyDataForDisabled(String apNo) {
		List<Bafamily> familyList = bafamilyDao.selectFamilyDataForDisabledPaymentQuery(apNo);
		List<PaymentQueryFamilyDataCase> returnList = new ArrayList<PaymentQueryFamilyDataCase>();

		for (int i = 0; i < familyList.size(); i++) {
			Bafamily obj = familyList.get(i);
			PaymentQueryFamilyDataCase caseObj = new PaymentQueryFamilyDataCase();
			BeanUtility.copyProperties(caseObj, obj);

			// 取得 在學起迄年月資料
			caseObj = getStudMasterDataForDisabled(caseObj);
			// 取得 眷屬編審註記資料
			List<PaymentQueryFamilyDataCase> benChkList = getPaymentQueryOtherChkListForFamilyData(apNo, "0000", "B",
					caseObj.getSeqNo());
			// 取得 符合註記資料
			List<PaymentQueryFamilyDataCase> matchChkList = getPaymentQueryOtherChkListForFamilyData(apNo, "0000", "C",
					caseObj.getSeqNo());
			// 取得學校代碼
			Npcode npcodeData = npcodeDao.selectNpCodeNameBy(obj.getSchoolCode());
			if (npcodeData != null) {
				caseObj.setSchoolCodeStr(npcodeData.getCodeString());
			}

			caseObj.setBenChkFileDataList(benChkList);
			caseObj.setMatchChkFileDataList(matchChkList);

			returnList.add(caseObj);
		}

		return returnList;
	}

	/**
	 * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 資料
	 * 
	 * @param apNo   受理編號
	 * @param seqNo  序號
	 * @param chkTyp 編審註記種類 (A:事故者編審註記, B:眷屬編審註記, C:符合註記)
	 * @return <code>PaymentReviewExtCase</code> 物件 List
	 */
	@SuppressWarnings("unchecked")
	public List<PaymentQueryFamilyDataCase> getPaymentQueryOtherChkListForFamilyData(String apNo, String seqNo,
			String chkTyp, String familySeqNo) {
		List<Bachkfile> list = bachkfileDao.selectReviewChkListBy(apNo, seqNo, chkTyp, familySeqNo);
		List<PaymentQueryFamilyDataCase> returnList = new ArrayList<PaymentQueryFamilyDataCase>();

		// 建立資料Map
		// Map的Key 為 issuYm+payYm
		// Value則是同為該 issuYm+payYm 下的所有編審註記資料
		Map<String, List<PaymentQueryChkFileDataCase>> map = new TreeMap<String, List<PaymentQueryChkFileDataCase>>();
		for (int i = 0; i < list.size(); i++) {
			Bachkfile obj = (Bachkfile) list.get(i);
			map.put(obj.getIssuYmStr() + "-" + obj.getPayYmStr(), new ArrayList<PaymentQueryChkFileDataCase>());
		}

		for (int i = 0; i < list.size(); i++) {
			Bachkfile obj = (Bachkfile) list.get(i);
			PaymentQueryChkFileDataCase caseObj = new PaymentQueryChkFileDataCase();
			BeanUtility.copyProperties(caseObj, obj);
			(map.get(obj.getIssuYmStr() + "-" + obj.getPayYmStr())).add(caseObj);
		}

		// 將 分類好的 map 轉為 list
		for (Iterator it = map.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			List<PaymentQueryChkFileDataCase> tempList = map.get(key);
			PaymentQueryFamilyDataCase caseObj = new PaymentQueryFamilyDataCase();
			caseObj.setIssuPayYm(key);
			caseObj.setChkFileDataList(tempList);
			caseObj.setChkFileDataSize(tempList.size());
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入的條件取得 勞保複檢費用主檔(<code>BABCML7</code>) 複檢費用資料 for 失能年金給付查詢
	 * 
	 * @param apNo 受理編號
	 * @return 內含 <code>PaymentQueryReFeesMasterDataCase</code> 物件的 List
	 */
	public List<PaymentQueryReFeesMasterDataCase> getReFeesMasterDataForDisabled(String apNo) {
		List<Babcml7> list = babcml7Dao.selectReFeesMasterDataForDisabledPaymentQuery(apNo);
		List<PaymentQueryReFeesMasterDataCase> retureList = new ArrayList<PaymentQueryReFeesMasterDataCase>();
		for (int i = 0; i < list.size(); i++) {
			Babcml7 obj = list.get(i);
			PaymentQueryReFeesMasterDataCase caseObj = new PaymentQueryReFeesMasterDataCase();
			BeanUtility.copyProperties(caseObj, obj);
			retureList.add(caseObj);
		}
		return retureList;
	}

	/**
	 * 依傳入的條件取得 勞保複檢費用主檔(<code>BABCML7</code>) 複檢費用明細資料 for 失能年金給付查詢
	 * 
	 * @param apNo 受理編號
	 * @return 內含 <code>PaymentQueryReFeesDetailDataCase</code> 物件的 List
	 */
	public PaymentQueryReFeesDetailDataCase getReFeesDetailDataForDisabled(String reApNo, BigDecimal apSeq,
			String apNo) {
		List<Babcml7> list = babcml7Dao.selectReFeesDetailDataForDisabledPaymentQuery(reApNo, apSeq, apNo);
		PaymentQueryReFeesDetailDataCase caseObj = null;
		if (list.size() != 0) {
			Babcml7 obj = list.get(0);
			caseObj = new PaymentQueryReFeesDetailDataCase();
			BeanUtility.copyProperties(caseObj, obj);

			// 取得 醫院簡稱
			caseObj.setHpSnam(bbcmf07Dao.selectHpSnamBy(caseObj.getHosId()));
		}
		return caseObj;
	}

	/**
	 * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 事故者/受款人資料 for 遺屬年金給付查詢
	 * 
	 * @param apNo 受理編號
	 * @return 內含 <code>Baappbase</code> 物件的 List
	 */
	public List<PaymentQueryBenDataCase> getBeneficiaryDataForSurvivor(String apNo, String qryCond, String startYm,
			String endYm) {
		List<Baappbase> benDataList = baappbaseDao.selectBeneficiaryDataBy(apNo);
		List<PaymentQueryBenDataCase> returnList = new ArrayList<PaymentQueryBenDataCase>();
		for (int i = 0; i < benDataList.size(); i++) {
			Baappbase obj = benDataList.get(i);
			PaymentQueryBenDataCase caseObj = new PaymentQueryBenDataCase();
			BeanUtility.copyProperties(caseObj, obj);

			// 資料為受款人時, 需取得額外資料
			if (!("0000").equals(caseObj.getSeqNo())) {
				// 取得 遺屬欄位資料
				caseObj = getFamDataForSurvivorPaymentQuery(caseObj);
				// 取得 強迫不合格年月資料
				caseObj = getCompelNopayDataForSurvivor(caseObj);
				// 取得 在學起迄年月資料
				caseObj = getStudMasterDataForSurvivor(caseObj);
				// 取得 重殘起迄年月資料
				caseObj = getHandicapMasterDataForSurvivor(caseObj);
				// 取得 遺屬編審註記
				caseObj.setBenChkList(
						getPaymentQueryOtherChkListForSurvivorFamilyData(caseObj.getApNo(), "B", caseObj.getSeqNo()));
				// 取得 符合註記
				caseObj.setMatchChkList(
						getPaymentQueryOtherChkListForSurvivorFamilyData(caseObj.getApNo(), "C", caseObj.getSeqNo()));
				// 取得 學校代碼
				// 重新抓取資料 - 還要再抓延伸主檔 並設至Form及Case中
				Baappexpand expand = baappexpandDao
						.selectDataForSurvivorPayeeDataUpdate(new BigDecimal(caseObj.getBaappbaseId()));
				if (expand != null) {
					Npcode npcodeData = npcodeDao.selectNpCodeNameBy(expand.getSchoolCode());
					if (npcodeData != null) {
						caseObj.setSchoolCodeStr(npcodeData.getCodeString());
					}
				}
			}
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 遺屬欄位資料 for 遺屬年金給付查詢
	 * 
	 * @param apNo  受理編號
	 * @param seqNo 序號
	 * @return <code>PaymentQueryBenDataCase</code> 物件
	 */
	public PaymentQueryBenDataCase getFamDataForSurvivorPaymentQuery(PaymentQueryBenDataCase caseObj) {
		Baappexpand baappexpand = baappexpandDao.selectBenDataForSurvivorPaymentQuery(caseObj.getApNo(),
				caseObj.getSeqNo());
		if (baappexpand != null) {
			caseObj.setRehcMk(baappexpand.getRehcMk()); // 重新查核失能程度註記
			caseObj.setRehcYm(baappexpand.getRehcYm()); // 重新查核失能程度年月
			caseObj.setMonIncomeMk(baappexpand.getMonIncomeMk()); // 每月工作收入註記
			caseObj.setMonIncome(baappexpand.getMonIncome()); // 每月工作收入
			caseObj.setStudMk(baappexpand.getStudMk()); // 在學註記
			caseObj.setHandIcapMk(baappexpand.getHandIcapMk()); // 重殘註記
			caseObj.setMarryDate(baappexpand.getMarryDate()); // 結婚日期
			caseObj.setDigamyDate(baappexpand.getDigamyDate()); // 再婚日期
			caseObj.setAbanApsYm(baappexpand.getAbanApsYm()); // 放棄請領起始年月
			caseObj.setRaiseChildMk(baappexpand.getRaiseChildMk()); // 配偶扶養
			caseObj.setHandIcapMk(baappexpand.getHandIcapMk()); // 領有重度以上身心障礙手冊或證明註記
			caseObj.setInterDictMk(baappexpand.getInterDictMk()); // 受禁治產(監護)宣告
			caseObj.setInterDictDate(baappexpand.getInterDictDate()); // 受禁治產(監護)宣告 - 宣告日期
			caseObj.setRepealInterdictDate(baappexpand.getRepealInterdictDate());// 受禁治產(監護)宣告 - 撤銷日期
			caseObj.setBenMissingSdate(baappexpand.getBenMissingSdate()); // 遺屬失蹤期間(起)
			caseObj.setBenMissingEdate(baappexpand.getBenMissingEdate()); // 遺屬失蹤期間(迄)
			caseObj.setPrisonSdate(baappexpand.getPrisonSdate()); // 監管期間(起)
			caseObj.setPrisonEdate(baappexpand.getPrisonEdate()); // 監管期間(迄)
			caseObj.setRelatChgDate(baappexpand.getRelatChgDate()); // 親屬關係變動日期
			caseObj.setAdoPtDate(baappexpand.getAdoPtDate()); // 收養日期
			caseObj.setJudgeDate(baappexpand.getJudgeDate()); // 判決日期
			caseObj.setRaiseEvtMk(baappexpand.getRaiseEvtMk()); // 被保險人扶養
			caseObj.setAssignIdnNo(baappexpand.getAssignIdnNo()); // 代辦人身分證號
			caseObj.setAssignName(baappexpand.getAssignName()); // 代辦人姓名
			caseObj.setAssignBrDate(baappexpand.getAssignBrDate()); // 代辦人出生日期
			caseObj.setAbleApsYm(baappexpand.getAbleApsYm());// 得請領年月
			caseObj.setSavingMk(baappexpand.getSavingMk());// 計息存儲
		}
		return caseObj;
	}

	/**
	 * 依傳入條件取得 遺屬在學期間檔 (<code>BASTUDTERM</code>) 在學資料 for 失能年金給付查詢
	 * 
	 * @param apNo  受理編號
	 * @param seqNo 序號
	 * @return <code>PaymentQueryFamilyDataCase</code> 物件
	 */
	public PaymentQueryFamilyDataCase getStudMasterDataForDisabled(PaymentQueryFamilyDataCase caseObj) {
		Bastudterm bastudterm = bastudtermDao.selectStudMasterDataForSurvivorPaymentQuery(caseObj.getApNo(),
				caseObj.getSeqNo());
		if (bastudterm != null) {
			caseObj.setStudSdate(bastudterm.getStudSdate()); // 在學起始年月
			caseObj.setStudEdate(bastudterm.getStudEdate()); // 在學結束年月
			caseObj.setStudDataCount(bastudterm.getStudDataCount()); // 在學記錄筆數
		}
		return caseObj;
	}

	/**
	 * 依傳入條件取得 遺屬在學期間檔 (<code>BASTUDTERM</code>) 在學資料明細 for 失能年金給付查詢
	 * 
	 * @param apNo  受理編號
	 * @param seqNo 序號
	 * @return 內含<code>PaymentQueryFamilyDataCase</code> 物件的List
	 */
	public List<PaymentQueryFamilyDataCase> getStudDetailDataForDisabled(String apNo, String seqNo) {
		List<Bastudterm> list = bastudtermDao.selectStudDetailDataForSurvivorPaymentQuery(apNo, seqNo);
		List<PaymentQueryFamilyDataCase> returnList = new ArrayList<PaymentQueryFamilyDataCase>();

		for (int i = 0; i < list.size(); i++) {
			Bastudterm obj = list.get(i);
			PaymentQueryFamilyDataCase caseObj = new PaymentQueryFamilyDataCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 遺屬在學期間檔 (<code>BASTUDTERM</code>) 在學資料 for 遺屬年金給付查詢
	 * 
	 * @param apNo  受理編號
	 * @param seqNo 序號
	 * @return <code>PaymentQueryBenDataCase</code> 物件
	 */
	public PaymentQueryBenDataCase getStudMasterDataForSurvivor(PaymentQueryBenDataCase caseObj) {
		Bastudterm bastudterm = bastudtermDao.selectStudMasterDataForSurvivorPaymentQuery(caseObj.getApNo(),
				caseObj.getSeqNo());
		if (bastudterm != null) {
			caseObj.setStudSdate(bastudterm.getStudSdate()); // 在學起始年月
			caseObj.setStudEdate(bastudterm.getStudEdate()); // 在學結束年月
			caseObj.setStudDataCount(bastudterm.getStudDataCount()); // 在學記錄筆數
		}
		return caseObj;
	}

	/**
	 * 依傳入條件取得 遺屬ＸＸ期間檔 (<code>BACOMPELNOPAY</code>) 在學資料 for 遺屬年金給付查詢
	 * 
	 * @param apNo  受理編號
	 * @param seqNo 序號
	 * @return <code>PaymentQueryBenDataCase</code> 物件
	 */
	public PaymentQueryBenDataCase getCompelNopayDataForSurvivor(PaymentQueryBenDataCase caseObj) {
		Bacompelnopay bacompelnopay = bacompelnopayDao.selectCompelNopayDataForSurvivorPaymentQuery(caseObj.getApNo(),
				caseObj.getSeqNo());
		if (bacompelnopay != null) {
			caseObj.setCompelSdate(bacompelnopay.getCompelSdate()); // 強迫不合格起始年月
			caseObj.setCompelEdate(bacompelnopay.getCompelEdate()); // 強迫不合格結束年月
			caseObj.setDataCount(bacompelnopay.getDataCount()); // 強迫不合格記錄筆數
		}
		return caseObj;
	}

	/**
	 * 依傳入條件取得 遺屬在學期間檔 (<code>BASTUDTERM</code>) 在學資料明細 for 遺屬年金給付查詢
	 * 
	 * @param apNo  受理編號
	 * @param seqNo 序號
	 * @return 內含<code>PaymentQueryBenDataCase</code> 物件的List
	 */
	public List<PaymentQueryBenDataCase> getStudDetailDataForSurvivor(String apNo, String seqNo) {
		List<Bastudterm> list = bastudtermDao.selectStudDetailDataForSurvivorPaymentQuery(apNo, seqNo);
		List<PaymentQueryBenDataCase> returnList = new ArrayList<PaymentQueryBenDataCase>();

		for (int i = 0; i < list.size(); i++) {
			Bastudterm obj = list.get(i);
			PaymentQueryBenDataCase caseObj = new PaymentQueryBenDataCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 遺屬重殘期間檔 (<code>BAHANDICAPTERM</code>) 重殘資料 for 遺屬年金給付查詢
	 * 
	 * @param apNo  受理編號
	 * @param seqNo 序號
	 * @return <code>PaymentQueryBenDataCase</code> 物件
	 */
	public PaymentQueryBenDataCase getHandicapMasterDataForSurvivor(PaymentQueryBenDataCase caseObj) {
		Bahandicapterm bahandicapterm = bahandicaptermDao
				.selectHandicapMasterDataForSurvivorPaymentQuery(caseObj.getApNo(), caseObj.getSeqNo());
		if (bahandicapterm != null) {
			caseObj.setHandicapSdate(bahandicapterm.getHandicapSdate()); // 重殘起始年月
			caseObj.setHandicapEdate(bahandicapterm.getHandicapEdate()); // 重殘結束年月
			caseObj.setHandicapDataCount(bahandicapterm.getHandicapDataCount()); // 重殘記錄筆數
		}
		return caseObj;
	}

	/**
	 * 依傳入條件取得 遺屬重殘期間檔 (<code>BAHANDICAPTERM</code>) 重殘資料明細 for 遺屬年金給付查詢
	 * 
	 * @param apNo  受理編號
	 * @param seqNo 序號
	 * @return 內含<code>PaymentQueryBenDataCase</code> 物件的List
	 */
	public List<PaymentQueryBenDataCase> getHandicapDetailDataForSurvivor(String apNo, String seqNo) {
		List<Bahandicapterm> list = bahandicaptermDao.selectHandicapDetailDataForSurvivorPaymentQuery(apNo, seqNo);
		List<PaymentQueryBenDataCase> returnList = new ArrayList<PaymentQueryBenDataCase>();

		for (int i = 0; i < list.size(); i++) {
			Bahandicapterm obj = list.get(i);
			PaymentQueryBenDataCase caseObj = new PaymentQueryBenDataCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 遺屬ＸＸ期間檔 (<code>BACOMPELNOPAY</code>) 強迫不合格資料明細 for 遺屬年金給付查詢
	 * 
	 * @param apNo  受理編號
	 * @param seqNo 序號
	 * @return 內含<code>PaymentQueryBenDataCase</code> 物件的List
	 */
	public List<PaymentQueryBenDataCase> getCompelNopayDetailDataForSurvivor(String apNo, String seqNo) {
		List<Bacompelnopay> list = bacompelnopayDao.selectCompelNopayDetailDataForSurvivorPaymentQuery(apNo, seqNo);
		List<PaymentQueryBenDataCase> returnList = new ArrayList<PaymentQueryBenDataCase>();

		for (int i = 0; i < list.size(); i++) {
			Bacompelnopay obj = list.get(i);
			PaymentQueryBenDataCase caseObj = new PaymentQueryBenDataCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 資料
	 * 
	 * @param apNo   受理編號
	 * @param seqNo  序號
	 * @param chkTyp 編審註記種類 (A:事故者編審註記, B:眷屬編審註記, C:符合註記)
	 * @return <code>PaymentReviewExtCase</code> 物件 List
	 */
	@SuppressWarnings("unchecked")
	public List<PaymentQueryFamilyDataCase> getPaymentQueryOtherChkListForSurvivorFamilyData(String apNo, String chkTyp,
			String familySeqNo) {
		List<Bachkfile> list = bachkfileDao.selectChkFileForSurvivorPaymentReview(apNo, chkTyp, familySeqNo);
		List<PaymentQueryFamilyDataCase> returnList = new ArrayList<PaymentQueryFamilyDataCase>();

		// 建立資料Map
		// Map的Key 為 issuYm+payYm
		// Value則是同為該 issuYm+payYm 下的所有編審註記資料
		Map<String, List<PaymentQueryChkFileDataCase>> map = new TreeMap<String, List<PaymentQueryChkFileDataCase>>();
		for (int i = 0; i < list.size(); i++) {
			Bachkfile obj = (Bachkfile) list.get(i);
			map.put(obj.getIssuYmStr() + "-" + obj.getPayYmStr(), new ArrayList<PaymentQueryChkFileDataCase>());
		}

		for (int i = 0; i < list.size(); i++) {
			Bachkfile obj = (Bachkfile) list.get(i);
			PaymentQueryChkFileDataCase caseObj = new PaymentQueryChkFileDataCase();
			BeanUtility.copyProperties(caseObj, obj);
			(map.get(obj.getIssuYmStr() + "-" + obj.getPayYmStr())).add(caseObj);
		}

		// 將 分類好的 map 轉為 list
		for (Iterator it = map.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			List<PaymentQueryChkFileDataCase> tempList = map.get(key);
			PaymentQueryFamilyDataCase caseObj = new PaymentQueryFamilyDataCase();
			caseObj.setIssuPayYm(key);
			caseObj.setChkFileDataList(tempList);
			caseObj.setChkFileDataSize(tempList.size());
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 給付查詢 最新紓困貸款資料
	 * 
	 * @param apNo 受理編號
	 * @return
	 */
	public PaymentQueryLoanDataCase getPaymentQueryLoanMasterData(String apNo, String payKind353638) {
		List<Badapr> list = badaprDao.selectPaymentQueryLoanMasterDataBy(apNo, payKind353638);
		PaymentQueryLoanDataCase caseObj = new PaymentQueryLoanDataCase();
		if (list.size() == 1) {
			Badapr obj = list.get(0);
			BeanUtility.copyProperties(caseObj, obj);
		}

		return caseObj;
	}

	/**
	 * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 給付查詢 最新紓困貸款資料 (BAAPPBASE.LSCHKMK =
	 * 4)
	 * 
	 * @param apNo 受理編號
	 * @return
	 */
	public PaymentQueryLoanDataCase getPaymentQueryLoanMasterDataForLsChkMk4(String apNo, String payKind353638) {
		List<Badapr> list = badaprDao.selectPaymentQueryLoanMasterDataForLsChkMk4By(apNo, payKind353638);
		PaymentQueryLoanDataCase caseObj = new PaymentQueryLoanDataCase();
		if (list.size() >= 1) {
			Badapr obj = list.get(0);
			BeanUtility.copyProperties(caseObj, obj);
		}

		return caseObj;
	}

	/**
	 * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 給付查詢 紓困貸款 核定資料
	 * 
	 * @param apNo    受理編號
	 * @param qryCond 查詢條件
	 * @param startYm 查詢年月起
	 * @param endYm   查詢年月迄
	 * @return
	 */
	public List<PaymentQueryLoanDataCase> getPaymentQueryLoanDetailData(String apNo, String qryCond, String startYm,
			String endYm, String payKind353638) {
		List<Badapr> list = badaprDao.selectPaymentQueryLoanDetailDataBy(apNo, qryCond, startYm, endYm, payKind353638);
		List<PaymentQueryLoanDataCase> returnList = new ArrayList<PaymentQueryLoanDataCase>();
		for (int i = 0; i < list.size(); i++) {
			Badapr obj = list.get(i);
			PaymentQueryLoanDataCase caseObj = new PaymentQueryLoanDataCase();
			BeanUtility.copyProperties(caseObj, obj);
			if (StringUtils.equals("ISSUYM", qryCond)) {
				caseObj.setIssuYm(obj.getIssuPayYm());
			}
			if (StringUtils.equals("PAYYM", qryCond)) {
				caseObj.setPayYm(obj.getIssuPayYm());
			}
			returnList.add(caseObj);
		}

		return returnList;
	}

	/**
	 * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 給付查詢 平均薪資 - 年資資料
	 * 
	 * @param apNo 受理編號
	 * @return
	 */
	public PaymentQueryAvgAmtDataCase getPaymentQueryAvgAmtSeniData(String apNo, String payKind) {
		List<Badapr> list = badaprDao.selectPaymentQuerySeniDataBy(apNo, payKind);
		PaymentQueryAvgAmtDataCase caseObj = new PaymentQueryAvgAmtDataCase();
		if (list.size() == 1) {
			Badapr obj = list.get(0);
			BeanUtility.copyProperties(caseObj, obj);
		}
		return caseObj;
	}

	/**
	 * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 給付查詢 平均薪資 - 年資資料 (老年年金)
	 * 
	 * @param apNo 受理編號
	 * @return
	 */
	public PaymentQueryAvgAmtDataCase getPaymentQueryAvgAmtSeniDataForOldAge(String apNo) {
		List<Badapr> list = badaprDao.selectPaymentQuerySeniDataForOldAge(apNo);
		PaymentQueryAvgAmtDataCase caseObj = new PaymentQueryAvgAmtDataCase();
		if (list.size() == 1) {
			Badapr obj = list.get(0);
			BeanUtility.copyProperties(caseObj, obj);
		}
		return caseObj;
	}

	/**
	 * 取得 平均薪資月數(<code>BAPAAVGMON</code>)
	 * 
	 * @return
	 */
	public String getBapaavgmonAppMonth(String appDate) {
		Integer appMonth = null;
		String appMonthStr = "";
		appMonth = bapaavgmonDao.getBapaavgmonCount();

		if (appMonth == 0) {
			appMonthStr = "60";
		} else {
			appMonthStr = bapaavgmonDao.getBapaavgmonAppMonth(appDate);
			if (StringUtils.isBlank(appMonthStr)) {
				appMonthStr = "60";
			}
		}

		return appMonthStr;
	}

	/**
	 * 取得 平均薪資月數(<code>BAPAAVGMON</code>) For 老年年金
	 * 
	 * @return
	 */
	public String getRealAvgMonForOldAge(String apNo, String seqNo, String idn) {
		String realAvgMon = cipgDao.getRealAvgMonForOldAge(apNo, seqNo, idn);
		return realAvgMon;
	}

	/**
	 * 取得 身分證重號處理
	 * 
	 * @return
	 */
	public String getDupeIdnNoMkIdn(String apNo, String dupeIdnNoMk, String idn) {
		String newIdn = idn;
		if (StringUtils.equals(dupeIdnNoMk, "2")) {
			Badupeidn dupeIdnData = badupeidnDao.getOldAgeReviewRpt01DupeIdnDataBy(apNo);
			if (dupeIdnData != null) {
				if (StringUtils.isNotBlank(dupeIdnData.getIdnNo())) {
					newIdn = dupeIdnData.getIdnNo();
				}
			}
		}

		return newIdn;
	}

	/**
	 * 取得 平均薪資月數(<code>BAPAAVGMON</code>) For 失能遺屬
	 * 
	 * @return
	 */
	public String getRealAvgMonBy(String apNo, String seqNo, String idn, String avgTyp) {
		String realAvgMon = cipgDao.getRealAvgMonBy(apNo, seqNo, idn, avgTyp);
		return realAvgMon;
	}

	/**
	 * 依傳入條件取得 被保險人投保薪資檔(<code>CIPG</code>)for 給付查詢 平均薪資 - 最高60個月平均薪資明細
	 * 
	 * @param idn    身分證號
	 * @param avgTyp 投保薪資類別
	 * @return
	 */
	public List<PaymentQueryAvgAmtDataCase> getPaymentQuerySixtyMonAvgAmtData(String apNo, String seqNo, String idn,
			String avgTyp, String appMonth) {
		List<Cipg> list = cipgDao.selectPaymentQuerySixtyMonAvgAmtDataBy(apNo, seqNo, idn, avgTyp, appMonth);
		List<PaymentQueryAvgAmtDataCase> returnList = new ArrayList<PaymentQueryAvgAmtDataCase>();
		for (int i = 0; i < list.size(); i++) {
			Cipg obj = list.get(i);
			PaymentQueryAvgAmtDataCase caseObj = new PaymentQueryAvgAmtDataCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}

		return returnList;
	}

	/**
	 * 依傳入條件取得 被保險人投保薪資檔(<code>CIPG</code>)for 給付查詢 平均薪資 - 最高60個月平均薪資明細 (老年年金)
	 * 
	 * @param idn 身分證號
	 * @return
	 */
	public List<PaymentQueryAvgAmtDataCase> getPaymentQuerySixtyMonAvgAmtDataForOldAge(String apNo, String seqNo,
			String idn, String appMonth) {
		List<Cipg> list = cipgDao.selectPaymentQuerySixtyMonAvgAmtDataForOldAgeBy(apNo, seqNo, idn, appMonth);
		List<PaymentQueryAvgAmtDataCase> returnList = new ArrayList<PaymentQueryAvgAmtDataCase>();
		for (int i = 0; i < list.size(); i++) {
			Cipg obj = list.get(i);
			PaymentQueryAvgAmtDataCase caseObj = new PaymentQueryAvgAmtDataCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}

		return returnList;
	}

	/**
	 * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 物價指數調整資料 for 給付查詢
	 * 
	 * @param apNo 受理編號
	 * @return
	 */
	public List<PaymentQueryCpiDataCase> getCpiDataForPaymentQuery(String apNo, String paycode) {
		List<Badapr> list = badaprDao.selectCpiDataForPaymentQuery(apNo, paycode);
		List<PaymentQueryCpiDataCase> returnList = new ArrayList<PaymentQueryCpiDataCase>();
		for (int i = 0; i < list.size(); i++) {
			Badapr obj = list.get(i);
			PaymentQueryCpiDataCase caseObj = new PaymentQueryCpiDataCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 物價指數調整紀錄檔 (<code>BACPIREC</code>) 物價指數調整記錄資料 for 給付查詢
	 * 
	 * @return
	 */
	public List<PaymentQueryCpiDataCase> getCpiRecForPaymentQuery() {
		List<Bacpirec> list = bacpirecDao.selectCpipRecForPaymentQuery();
		List<PaymentQueryCpiDataCase> returnList = new ArrayList<PaymentQueryCpiDataCase>();
		for (int i = 0; i < list.size(); i++) {
			Bacpirec obj = list.get(i);
			PaymentQueryCpiDataCase caseObj = new PaymentQueryCpiDataCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 查詢 案件歸檔紀錄檔(<code>BAARCLIST</code>) 歸檔記錄資料 for 給付查詢-歸檔記錄
	 * 
	 * @param apNo
	 * @return
	 */
	public PaymentQueryArclistDataCase getArclistDataForPaymentQuery(String apNo) {
		PaymentQueryArclistDataCase arclistcase = new PaymentQueryArclistDataCase();

		// 查詢核定年月起迄
		Baarclist baarclist = baarclistDao.selectPaymentQueryIssuYm(apNo);
		if (baarclist != null)
			BeanUtility.copyProperties(arclistcase, baarclist);

		// 查詢歸檔記錄資料
		List<Baarclist> dataList = baarclistDao.selectPaymentQueryData(apNo);
		List<PaymentQueryArclistDetailDataCase> detailList = new ArrayList<PaymentQueryArclistDetailDataCase>();
		if (dataList != null && dataList.size() > 0) {
			for (Baarclist data : dataList) {
				PaymentQueryArclistDetailDataCase detailCase = new PaymentQueryArclistDetailDataCase();
				BeanUtility.copyProperties(detailCase, data);
				detailList.add(detailCase);
			}
		}
		arclistcase.setDetailList(detailList);
		return arclistcase;
	}

	/**
	 * 查詢 不合格核定通知紀錄檔(<code>BAUNQUALIFIEDNOTICE</code>) 核定清單記錄資料 for 給付查詢-核定清單記錄
	 * 
	 * @param apNo
	 * @return
	 */
	public PaymentQueryUnqualifiedNoticeDataCase getUnqualifiedNoticeDataForPaymentQuery(String apNo) {
		PaymentQueryUnqualifiedNoticeDataCase unqualifiedNoticeCase = new PaymentQueryUnqualifiedNoticeDataCase();

		// 查詢歸檔記錄資料
		List<Baunqualifiednotice> dataList = baunqualifiednoticeDao.selectPaymentQueryData(apNo);
		List<PaymentQueryUnqualifiedNoticeDataCase> detailList = new ArrayList<PaymentQueryUnqualifiedNoticeDataCase>();
		if (dataList != null && dataList.size() > 0) {
			for (Baunqualifiednotice data : dataList) {
				PaymentQueryUnqualifiedNoticeDataCase detailCase = new PaymentQueryUnqualifiedNoticeDataCase();
				BeanUtility.copyProperties(detailCase, data);
				detailList.add(detailCase);
			}
		}
		unqualifiedNoticeCase.setDetailList(detailList);
		return unqualifiedNoticeCase;
	}

	/**
	 * 依傳入的條件 自 國保給付主檔 (<code>NBAPPBASE</code>) 取得 國保資料 for 失能年金給付查詢<br>
	 * 
	 * @param apNo
	 * @param qryCond
	 * @param startYm
	 * @param endYm
	 * @return
	 */
	public PaymentQueryNpDataCase getPaymentQueryDisabledNpData(String apNo) {
		log.debug("執行 QueryService.getUpdateLogQueryCaseBy() 開始 ...");

		// 先查詢國保資料
		List<Nbappbase> nbappbaseList = nbappbaseDao.selectPaymentQueryDisabledNpDataBy(apNo);
		PaymentQueryNpDataCase npDataCase = new PaymentQueryNpDataCase();
		if (nbappbaseList.size() == 1) {
			npDataCase = new PaymentQueryNpDataCase();
			BeanUtility.copyProperties(npDataCase, nbappbaseList.get(0));
		}

		/*
		 * //test npDataCase.setApNo("B01234567890"); npDataCase.setPayYms("201012");
		 * npDataCase.setLabMerge("Y"); npDataCase.setDabLevel("99");
		 * npDataCase.setDabType("G"); npDataCase.setDabPart("AAAA");
		 * npDataCase.setManChkFlg("Y"); npDataCase.setValSeni(new BigDecimal("15"));
		 * npDataCase.setIssuYm("201101");
		 */

		return npDataCase;
	}

	/**
	 * 依傳入的條件 自 國保給付主檔 (<code>NBAPPBASE</code>) 取得 國保資料 for 失能年金給付查詢<br>
	 * 
	 * @param apNo
	 * @param qryCond
	 * @param startYm
	 * @param endYm
	 * @return
	 */
	public List<Object> getPaymentQueryDisabledNpIssuData(String apNo, String qryCond, String startYm, String endYm) {
		log.debug("執行 QueryService.getUpdateLogQueryCaseBy() 開始 ...");

		List<Object> returnList = new ArrayList<Object>();

		// 查詢國保核定資料
		List<Nbdapr> nbdaprList = nbdaprDao.selectPaymentQueryDisabledNpIssuDataBy(apNo, qryCond, startYm, endYm);
		List<PaymentQueryNpIssuDataCase> issuDataList = new ArrayList<PaymentQueryNpIssuDataCase>();

		/*
		 * Nbdapr nbdaprObj = new Nbdapr(); nbdaprObj.setIssuYm("201301") ;// 核定年月
		 * nbdaprObj.setPayYm("201301") ;// 給付年月 nbdaprObj.setIssueAmt(new
		 * BigDecimal("25000")) ;// 核定金額 nbdaprObj.setSagtotAmt(new BigDecimal("500"))
		 * ;// 代扣保費金額 nbdaprObj.setItrtAmt(new BigDecimal("750")) ;//
		 * 利息(20091023新增)(代扣需加此欄方為代扣保費總和) nbdaprObj.setRecAmt(new BigDecimal("4500"))
		 * ;// 收回金額 nbdaprObj.setSupAmt(new BigDecimal("6870")) ;// 補發金額
		 * nbdaprObj.setCutAmt(new BigDecimal("12500")) ;// 減領金額
		 * nbdaprObj.setOtherAmt(new BigDecimal("1380")) ;// 另案扣減金額
		 * nbdaprObj.setAplPayAmt(new BigDecimal("14750")) ;// 實付金額
		 * nbdaprObj.setManChkFlg("Y") ;// 人工審核結果 nbdaprList.add(nbdaprObj); nbdaprObj =
		 * new Nbdapr(); nbdaprObj.setIssuYm("201301") ;// 核定年月
		 * nbdaprObj.setPayYm("201302") ;// 給付年月 nbdaprObj.setIssueAmt(new
		 * BigDecimal("25000")) ;// 核定金額 nbdaprObj.setSagtotAmt(new BigDecimal("500"))
		 * ;// 代扣保費金額 nbdaprObj.setItrtAmt(new BigDecimal("750")) ;//
		 * 利息(20091023新增)(代扣需加此欄方為代扣保費總和) nbdaprObj.setRecAmt(new BigDecimal("4500"))
		 * ;// 收回金額 nbdaprObj.setSupAmt(new BigDecimal("6870")) ;// 補發金額
		 * nbdaprObj.setCutAmt(new BigDecimal("12500")) ;// 減領金額
		 * nbdaprObj.setOtherAmt(new BigDecimal("1380")) ;// 另案扣減金額
		 * nbdaprObj.setAplPayAmt(new BigDecimal("14750")) ;// 實付金額
		 * nbdaprObj.setManChkFlg("Y") ;// 人工審核結果 nbdaprList.add(nbdaprObj); nbdaprObj =
		 * new Nbdapr(); nbdaprObj.setIssuYm("201301") ;// 核定年月
		 * nbdaprObj.setPayYm("201303") ;// 給付年月 nbdaprObj.setIssueAmt(new
		 * BigDecimal("25000")) ;// 核定金額 nbdaprObj.setSagtotAmt(new BigDecimal("500"))
		 * ;// 代扣保費金額 nbdaprObj.setItrtAmt(new BigDecimal("750")) ;//
		 * 利息(20091023新增)(代扣需加此欄方為代扣保費總和) nbdaprObj.setRecAmt(new BigDecimal("4500"))
		 * ;// 收回金額 nbdaprObj.setSupAmt(new BigDecimal("6870")) ;// 補發金額
		 * nbdaprObj.setCutAmt(new BigDecimal("12500")) ;// 減領金額
		 * nbdaprObj.setOtherAmt(new BigDecimal("1380")) ;// 另案扣減金額
		 * nbdaprObj.setAplPayAmt(new BigDecimal("14750")) ;// 實付金額
		 * nbdaprObj.setManChkFlg("Y") ;// 人工審核結果 nbdaprList.add(nbdaprObj); nbdaprObj =
		 * new Nbdapr(); nbdaprObj.setIssuYm("201302") ;// 核定年月
		 * nbdaprObj.setPayYm("201304") ;// 給付年月 nbdaprObj.setIssueAmt(new
		 * BigDecimal("25000")) ;// 核定金額 nbdaprObj.setSagtotAmt(new BigDecimal("500"))
		 * ;// 代扣保費金額 nbdaprObj.setItrtAmt(new BigDecimal("750")) ;//
		 * 利息(20091023新增)(代扣需加此欄方為代扣保費總和) nbdaprObj.setRecAmt(new BigDecimal("4500"))
		 * ;// 收回金額 nbdaprObj.setSupAmt(new BigDecimal("6870")) ;// 補發金額
		 * nbdaprObj.setCutAmt(new BigDecimal("12500")) ;// 減領金額
		 * nbdaprObj.setOtherAmt(new BigDecimal("1380")) ;// 另案扣減金額
		 * nbdaprObj.setAplPayAmt(new BigDecimal("14750")) ;// 實付金額
		 * nbdaprObj.setManChkFlg("Y") ;// 人工審核結果 nbdaprList.add(nbdaprObj); nbdaprObj =
		 * new Nbdapr(); nbdaprObj.setIssuYm("201302") ;// 核定年月
		 * nbdaprObj.setPayYm("201305") ;// 給付年月 nbdaprObj.setIssueAmt(new
		 * BigDecimal("25000")) ;// 核定金額 nbdaprObj.setSagtotAmt(new BigDecimal("500"))
		 * ;// 代扣保費金額 nbdaprObj.setItrtAmt(new BigDecimal("750")) ;//
		 * 利息(20091023新增)(代扣需加此欄方為代扣保費總和) nbdaprObj.setRecAmt(new BigDecimal("4500"))
		 * ;// 收回金額 nbdaprObj.setSupAmt(new BigDecimal("6870")) ;// 補發金額
		 * nbdaprObj.setCutAmt(new BigDecimal("12500")) ;// 減領金額
		 * nbdaprObj.setOtherAmt(new BigDecimal("1380")) ;// 另案扣減金額
		 * nbdaprObj.setAplPayAmt(new BigDecimal("14750")) ;// 實付金額
		 * nbdaprObj.setManChkFlg("Y") ;// 人工審核結果 nbdaprList.add(nbdaprObj); nbdaprObj =
		 * new Nbdapr(); nbdaprObj.setIssuYm("201302") ;// 核定年月
		 * nbdaprObj.setPayYm("201306") ;// 給付年月 nbdaprObj.setIssueAmt(new
		 * BigDecimal("25000")) ;// 核定金額 nbdaprObj.setSagtotAmt(new BigDecimal("500"))
		 * ;// 代扣保費金額 nbdaprObj.setItrtAmt(new BigDecimal("750")) ;//
		 * 利息(20091023新增)(代扣需加此欄方為代扣保費總和) nbdaprObj.setRecAmt(new BigDecimal("4500"))
		 * ;// 收回金額 nbdaprObj.setSupAmt(new BigDecimal("6870")) ;// 補發金額
		 * nbdaprObj.setCutAmt(new BigDecimal("12500")) ;// 減領金額
		 * nbdaprObj.setOtherAmt(new BigDecimal("1380")) ;// 另案扣減金額
		 * nbdaprObj.setAplPayAmt(new BigDecimal("14750")) ;// 實付金額
		 * nbdaprObj.setManChkFlg("Y") ;// 人工審核結果 nbdaprList.add(nbdaprObj); nbdaprObj =
		 * new Nbdapr(); nbdaprObj.setIssuYm("201303") ;// 核定年月
		 * nbdaprObj.setPayYm("201307") ;// 給付年月 nbdaprObj.setIssueAmt(new
		 * BigDecimal("25000")) ;// 核定金額 nbdaprObj.setSagtotAmt(new BigDecimal("500"))
		 * ;// 代扣保費金額 nbdaprObj.setItrtAmt(new BigDecimal("750")) ;//
		 * 利息(20091023新增)(代扣需加此欄方為代扣保費總和) nbdaprObj.setRecAmt(new BigDecimal("4500"))
		 * ;// 收回金額 nbdaprObj.setSupAmt(new BigDecimal("6870")) ;// 補發金額
		 * nbdaprObj.setCutAmt(new BigDecimal("12500")) ;// 減領金額
		 * nbdaprObj.setOtherAmt(new BigDecimal("1380")) ;// 另案扣減金額
		 * nbdaprObj.setAplPayAmt(new BigDecimal("14750")) ;// 實付金額
		 * nbdaprObj.setManChkFlg("Y") ;// 人工審核結果 nbdaprList.add(nbdaprObj); nbdaprObj =
		 * new Nbdapr(); nbdaprObj.setIssuYm("201303") ;// 核定年月
		 * nbdaprObj.setPayYm("201308") ;// 給付年月 nbdaprObj.setIssueAmt(new
		 * BigDecimal("25000")) ;// 核定金額 nbdaprObj.setSagtotAmt(new BigDecimal("500"))
		 * ;// 代扣保費金額 nbdaprObj.setItrtAmt(new BigDecimal("750")) ;//
		 * 利息(20091023新增)(代扣需加此欄方為代扣保費總和) nbdaprObj.setRecAmt(new BigDecimal("4500"))
		 * ;// 收回金額 nbdaprObj.setSupAmt(new BigDecimal("6870")) ;// 補發金額
		 * nbdaprObj.setCutAmt(new BigDecimal("12500")) ;// 減領金額
		 * nbdaprObj.setOtherAmt(new BigDecimal("1380")) ;// 另案扣減金額
		 * nbdaprObj.setAplPayAmt(new BigDecimal("14750")) ;// 實付金額
		 * nbdaprObj.setManChkFlg("Y") ;// 人工審核結果 nbdaprList.add(nbdaprObj); nbdaprObj =
		 * new Nbdapr(); nbdaprObj.setIssuYm("201303") ;// 核定年月
		 * nbdaprObj.setPayYm("201309") ;// 給付年月 nbdaprObj.setIssueAmt(new
		 * BigDecimal("25000")) ;// 核定金額 nbdaprObj.setSagtotAmt(new BigDecimal("500"))
		 * ;// 代扣保費金額 nbdaprObj.setItrtAmt(new BigDecimal("750")) ;//
		 * 利息(20091023新增)(代扣需加此欄方為代扣保費總和) nbdaprObj.setRecAmt(new BigDecimal("4500"))
		 * ;// 收回金額 nbdaprObj.setSupAmt(new BigDecimal("6870")) ;// 補發金額
		 * nbdaprObj.setCutAmt(new BigDecimal("12500")) ;// 減領金額
		 * nbdaprObj.setOtherAmt(new BigDecimal("1380")) ;// 另案扣減金額
		 * nbdaprObj.setAplPayAmt(new BigDecimal("14750")) ;// 實付金額
		 * nbdaprObj.setManChkFlg("Y") ;// 人工審核結果 nbdaprList.add(nbdaprObj);
		 */

		// 建立資料Map
		// qryCond = ISSUYM, Map Key = issuYm
		// qryCond = PAYYM, Map Key = payYm
		// Value則是同為該 issuYm／payYm 下的所有給付核定資料

		Map<String, List<PaymentQueryNpIssuDataCase>> map = new TreeMap<String, List<PaymentQueryNpIssuDataCase>>();
		for (int i = 0; i < nbdaprList.size(); i++) {
			Nbdapr obj = (Nbdapr) nbdaprList.get(i);
			if (StringUtils.equals(qryCond, "ISSUYM")) {
				map.put(obj.getIssuYm(), new ArrayList<PaymentQueryNpIssuDataCase>());
			} else if (StringUtils.equals(qryCond, "PAYYM")) {
				map.put(obj.getPayYm(), new ArrayList<PaymentQueryNpIssuDataCase>());
			}
		}

		for (int i = 0; i < nbdaprList.size(); i++) {
			Nbdapr obj = (Nbdapr) nbdaprList.get(i);
			PaymentQueryNpIssuDataCase caseObj = new PaymentQueryNpIssuDataCase();
			BeanUtility.copyProperties(caseObj, obj);

			if (StringUtils.equals(qryCond, "ISSUYM")) {
				(map.get(obj.getIssuYm())).add(caseObj);
			} else if (StringUtils.equals(qryCond, "PAYYM")) {
				(map.get(obj.getPayYm())).add(caseObj);
			}
		}

		// 將 分類好的 map 轉為 list
		for (Iterator it = map.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			List<PaymentQueryNpIssuDataCase> tempList = map.get(key);
			PaymentQueryNpIssuDataCase caseObj = new PaymentQueryNpIssuDataCase();

			if (StringUtils.equals(qryCond, "ISSUYM")) {
				caseObj.setIssuYm(key);
			} else if (StringUtils.equals(qryCond, "PAYYM")) {
				caseObj.setPayYm(key);
			}
			caseObj.setIssuDataList(tempList);
			issuDataList.add(caseObj);
		}
		returnList.add(0, nbdaprList);// 原始查詢結果List
		returnList.add(1, issuDataList);// 處理過後之List
		return returnList;
	}

	/**
	 * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 給付查詢作業 核定年月下拉選單
	 * 
	 * @param qryCond
	 * @param issuDataList
	 * @return 內含 <code>PaymentQueryNpIssuDataCase</code> 物件的 List
	 */
	@SuppressWarnings("unchecked")
	public List<PaymentQueryNpIssuDataCase> getNpDataIssuPayYmOptionList(String qryCond,
			List<PaymentQueryNpIssuDataCase> issuDataList) {
		List<PaymentQueryNpIssuDataCase> returnList = new ArrayList<PaymentQueryNpIssuDataCase>();

		for (int i = 0; i < issuDataList.size(); i++) {
			PaymentQueryNpIssuDataCase caseObj = (PaymentQueryNpIssuDataCase) issuDataList.get(i);
			PaymentQueryNpIssuDataCase newCase = new PaymentQueryNpIssuDataCase();

			if (StringUtils.equals(qryCond, "ISSUYM")) {
				newCase.setIssuYm(caseObj.getIssuYm());
				returnList.add(newCase);
			} else if (StringUtils.equals(qryCond, "PAYYM")) {
				newCase.setIssuYm(caseObj.getPayYm());
				returnList.add(newCase);
			}
		}

		return returnList;
	}

	/**
	 * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) for 遺屬年金平均薪資
	 * 
	 * @param apNo 受理編號
	 * @return caseObj
	 */
	public PaymentQueryDetailDataCase selectDataForSurvivorAvgAmtDetail(String apNo) {
		Baappbase baappbase = baappbaseDao.selectDataForSurvivorAvgAmtDetail(apNo);
		PaymentQueryDetailDataCase caseObj = null;
		if (baappbase != null) {
			caseObj = new PaymentQueryDetailDataCase();
			BeanUtility.copyProperties(caseObj, baappbase);
		}
		return caseObj;
	}

	/**
	 * 依傳入之 受理編號 取得 失能年金案件資料更正 之資料 for 失能年金案件資料更正
	 * 
	 * @param apNo 受理編號
	 * @return
	 */
	public DisabledApplicationDataUpdateCase getDisabledApplicationDataUpdateData(String apNo) {
		DisabledApplicationDataUpdateCase caseData = new DisabledApplicationDataUpdateCase();

		// 取得 BAAPPBASE 資料
		// [
		Baappbase masterData = baappbaseDao.getDisabledApplicationDataUpdateMasterDataBy(apNo);

		if (masterData == null)
			return null;

		caseData.setBaappbaseId(masterData.getBaappbaseId()); // 資料列編號
		caseData.setApNo(masterData.getApNo()); // 受理編號
		caseData.setSeqNo(masterData.getSeqNo()); // 序號
		caseData.setPayKind(masterData.getPayKind());// 給付種類
		caseData.setProcStat(masterData.getProcStat()); // 處理狀態
		caseData.setEvtNationTpe(masterData.getEvtNationTpe()); // 事故者國籍別
		caseData.setEvtNationCode(masterData.getEvtNationCode()); // 事故者國籍
		caseData.setEvtSex(masterData.getEvtSex()); // 事故者性別
		caseData.setEvtName(masterData.getEvtName()); // 事故者姓名
		caseData.setEvtIdnNo(masterData.getEvtIdnNo()); // 事故者身分證號
		caseData.setEvtBrDate(masterData.getEvtBrDate()); // 事故者出生日期
		caseData.setEvtJobDate(masterData.getEvtJobDate()); // 事故者離職日期 - 診斷失能日期
		caseData.setEvtDieDate(masterData.getEvtDieDate()); // 事故者死亡日期
		caseData.setAppDate(masterData.getAppDate()); // 申請日期
		caseData.setApUbno(masterData.getApUbno()); // 申請單位保險證號
		caseData.setApubnock(masterData.getApubnock()); // 申請單位保險證號檢查碼
		caseData.setLsUbno(masterData.getLsUbno()); // 最後單位保險證號 - 事故發生單位保險證號
		caseData.setLsUbnoCk(masterData.getLsUbnoCk()); // 最後單位保險證號檢查碼
		caseData.setNotifyForm(masterData.getNotifyForm()); // 核定通知書格式
		caseData.setCutAmt(masterData.getCutAmt()); // 扣減 / 補償總金額 - 年金應扣金額
		caseData.setLoanMk(masterData.getLoanMk()); // 不須抵銷紓困貸款註記
		caseData.setCloseCause(masterData.getCloseCause()); // 結案原因
		caseData.setChoiceYm(masterData.getChoiceYm()); // 擇領起月
		caseData.setDupeIdnNoMk(masterData.getDupeIdnNoMk()); // 身分證重號註記
		caseData.setInterValMonth(masterData.getInterValMonth());// 發放方式
		// ]

		// 取得 BAAPPEXPAND 資料
		// [
		Baappexpand expandData = baappexpandDao.getDisabledApplicationDataUpdateExpandDataBy(caseData.getBaappbaseId(),
				caseData.getApNo(), caseData.getSeqNo());

		if (expandData != null) {
			caseData.setBaappexpandId(expandData.getBaappexpandId()); // 資料編號
			caseData.setEvTyp(expandData.getEvTyp()); // 傷病分類
			caseData.setEvCode(expandData.getEvCode()); // 傷病原因
			caseData.setCriInPart1(expandData.getCriInPart1()); // 受傷部位1
			caseData.setCriInPart2(expandData.getCriInPart2()); // 受傷部位2
			caseData.setCriInPart3(expandData.getCriInPart3()); // 受傷部位3
			caseData.setCriMedium(expandData.getCriMedium()); // 媒介物
			caseData.setCriInIssul(expandData.getCriInIssul()); // 核定等級
			caseData.setCriInJcl1(expandData.getCriInJcl1()); // 失能等級1
			caseData.setCriInJcl2(expandData.getCriInJcl2()); // 失能等級2
			caseData.setCriInJcl3(expandData.getCriInJcl3()); // 失能等級3
			caseData.setCriInJdp1(expandData.getCriInJdp1()); // 失能項目1
			caseData.setCriInJdp2(expandData.getCriInJdp2()); // 失能項目2
			caseData.setCriInJdp3(expandData.getCriInJdp3()); // 失能項目3
			caseData.setCriInJdp4(expandData.getCriInJdp4()); // 失能項目4
			caseData.setCriInJdp5(expandData.getCriInJdp5()); // 失能項目5
			caseData.setCriInJdp6(expandData.getCriInJdp6()); // 失能項目6
			caseData.setCriInJdp7(expandData.getCriInJdp7()); // 失能項目7
			caseData.setCriInJdp8(expandData.getCriInJdp8()); // 失能項目8
			caseData.setCriInJdp9(expandData.getCriInJdp9()); // 失能項目9
			caseData.setCriInJdp10(expandData.getCriInJdp10()); // 失能項目10
			caseData.setHosId(expandData.getHosId()); // 醫療院所代碼
			caseData.setDoctorName1(expandData.getDoctorName1()); // 醫師姓名1
			caseData.setDoctorName2(expandData.getDoctorName2()); // 醫師姓名2
			caseData.setCriInJnme1(expandData.getCriInJnme1()); // 國際疾病代碼1
			caseData.setCriInJnme2(expandData.getCriInJnme2()); // 國際疾病代碼2
			caseData.setCriInJnme3(expandData.getCriInJnme3()); // 國際疾病代碼3
			caseData.setCriInJnme4(expandData.getCriInJnme4()); // 國際疾病代碼4
			caseData.setRehcMk(expandData.getRehcMk()); // 重新查核失能程度註記
			caseData.setRehcYm(expandData.getRehcYm()); // 重新查核失能程度年月
			caseData.setOcaccIdentMk(expandData.getOcaccIdentMk()); // 符合第20條之1註記
			caseData.setPrType(expandData.getPrType()); // 先核普通
			caseData.setOcAccaddAmt(expandData.getOcAccaddAmt()); // 己領職災增給金額
			caseData.setDeductDay(expandData.getDeductDay()); // 扣除天數
			caseData.setEvAppTyp(expandData.getEvAppTyp());// 申請傷病分類
			caseData.setDisQualMk(expandData.getDisQualMk());// 年金請領資格
		}
		// ]
		return caseData;
	}

	/**
	 * 依傳入之 受理編號 取得 失能年金案件資料更正 之資料 for 給付查詢重新查核失能程度
	 * 
	 * @param apNo 受理編號
	 * @return
	 */
	public DisabledApplicationDataUpdateCase getDisabledApplicationDataUpdateMasterDataForRehcBy(String apNo) {
		DisabledApplicationDataUpdateCase caseData = new DisabledApplicationDataUpdateCase();

		// 取得 BAAPPBASE 資料
		// [
		Baappbase masterData = baappbaseDao.getDisabledApplicationDataUpdateMasterDataForRehcBy(apNo);

		if (masterData == null)
			return null;

		caseData.setBaappbaseId(masterData.getBaappbaseId()); // 資料列編號
		caseData.setApNo(masterData.getApNo()); // 受理編號
		caseData.setSeqNo(masterData.getSeqNo()); // 序號
		caseData.setPayKind(masterData.getPayKind());// 給付種類
		caseData.setProcStat(masterData.getProcStat()); // 處理狀態
		caseData.setEvtNationTpe(masterData.getEvtNationTpe()); // 事故者國籍別
		caseData.setEvtNationCode(masterData.getEvtNationCode()); // 事故者國籍
		caseData.setEvtSex(masterData.getEvtSex()); // 事故者性別
		caseData.setEvtName(masterData.getEvtName()); // 事故者姓名
		caseData.setEvtIdnNo(masterData.getEvtIdnNo()); // 事故者身分證號
		caseData.setEvtBrDate(masterData.getEvtBrDate()); // 事故者出生日期
		caseData.setEvtJobDate(masterData.getEvtJobDate()); // 事故者離職日期 - 診斷失能日期
		caseData.setEvtDieDate(masterData.getEvtDieDate()); // 事故者死亡日期
		caseData.setAppDate(masterData.getAppDate()); // 申請日期
		caseData.setApUbno(masterData.getApUbno()); // 申請單位保險證號
		caseData.setApubnock(masterData.getApubnock()); // 申請單位保險證號檢查碼
		caseData.setLsUbno(masterData.getLsUbno()); // 最後單位保險證號 - 事故發生單位保險證號
		caseData.setLsUbnoCk(masterData.getLsUbnoCk()); // 最後單位保險證號檢查碼
		caseData.setNotifyForm(masterData.getNotifyForm()); // 核定通知書格式
		caseData.setCutAmt(masterData.getCutAmt()); // 扣減 / 補償總金額 - 年金應扣金額
		caseData.setLoanMk(masterData.getLoanMk()); // 不須抵銷紓困貸款註記
		caseData.setCloseCause(masterData.getCloseCause()); // 結案原因
		caseData.setChoiceYm(masterData.getChoiceYm()); // 擇領起月
		caseData.setDupeIdnNoMk(masterData.getDupeIdnNoMk()); // 身分證重號註記
		caseData.setInterValMonth(masterData.getInterValMonth());// 發放方式
		// ]

		// 取得 BAAPPEXPAND 資料
		// [
		Baappexpand expandData = baappexpandDao.getDisabledApplicationDataUpdateExpandDataBy(caseData.getBaappbaseId(),
				caseData.getApNo(), caseData.getSeqNo());

		if (expandData != null) {
			caseData.setBaappexpandId(expandData.getBaappexpandId()); // 資料編號
			caseData.setEvTyp(expandData.getEvTyp()); // 傷病分類
			caseData.setEvCode(expandData.getEvCode()); // 傷病原因
			caseData.setCriInPart1(expandData.getCriInPart1()); // 受傷部位1
			caseData.setCriInPart2(expandData.getCriInPart2()); // 受傷部位2
			caseData.setCriInPart3(expandData.getCriInPart3()); // 受傷部位3
			caseData.setCriMedium(expandData.getCriMedium()); // 媒介物
			caseData.setCriInIssul(expandData.getCriInIssul()); // 核定等級
			caseData.setCriInJcl1(expandData.getCriInJcl1()); // 失能等級1
			caseData.setCriInJcl2(expandData.getCriInJcl2()); // 失能等級2
			caseData.setCriInJcl3(expandData.getCriInJcl3()); // 失能等級3
			caseData.setCriInJdp1(expandData.getCriInJdp1()); // 失能項目1
			caseData.setCriInJdp2(expandData.getCriInJdp2()); // 失能項目2
			caseData.setCriInJdp3(expandData.getCriInJdp3()); // 失能項目3
			caseData.setCriInJdp4(expandData.getCriInJdp4()); // 失能項目4
			caseData.setCriInJdp5(expandData.getCriInJdp5()); // 失能項目5
			caseData.setCriInJdp6(expandData.getCriInJdp6()); // 失能項目6
			caseData.setCriInJdp7(expandData.getCriInJdp7()); // 失能項目7
			caseData.setCriInJdp8(expandData.getCriInJdp8()); // 失能項目8
			caseData.setCriInJdp9(expandData.getCriInJdp9()); // 失能項目9
			caseData.setCriInJdp10(expandData.getCriInJdp10()); // 失能項目10
			caseData.setHosId(expandData.getHosId()); // 醫療院所代碼
			caseData.setDoctorName1(expandData.getDoctorName1()); // 醫師姓名1
			caseData.setDoctorName2(expandData.getDoctorName2()); // 醫師姓名2
			caseData.setCriInJnme1(expandData.getCriInJnme1()); // 國際疾病代碼1
			caseData.setCriInJnme2(expandData.getCriInJnme2()); // 國際疾病代碼2
			caseData.setCriInJnme3(expandData.getCriInJnme3()); // 國際疾病代碼3
			caseData.setCriInJnme4(expandData.getCriInJnme4()); // 國際疾病代碼4
			caseData.setRehcMk(expandData.getRehcMk()); // 重新查核失能程度註記
			caseData.setRehcYm(expandData.getRehcYm()); // 重新查核失能程度年月
			caseData.setOcaccIdentMk(expandData.getOcaccIdentMk()); // 符合第20條之1註記
			caseData.setPrType(expandData.getPrType()); // 先核普通
			caseData.setOcAccaddAmt(expandData.getOcAccaddAmt()); // 己領職災增給金額
			caseData.setDeductDay(expandData.getDeductDay()); // 扣除天數
			caseData.setEvAppTyp(expandData.getEvAppTyp());// 申請傷病分類
			caseData.setDisQualMk(expandData.getDisQualMk());// 年金請領資格

			if (StringUtils.isBlank(caseData.getCriInJdpStr())) {
				caseData.setCriInJdpStr("");
			}
		}
		// ]
		return caseData;
	}

	/**
	 * 依傳入條件取得重新查核失能程度檔(<code>BARECHECK</code>) 資料 FOR 失能年金受款人資料更正 重新查核失能程度
	 * 
	 * @param apNo  受理編號
	 * @param seqNo 受款人序
	 * @return <code>List<Bachkfile></code> 物件
	 */
	public List<DisabledApplicationDataUpdateBareCheckCase> selectBareCheckDataBy(String apNo, String seqNo) {
		List<Barecheck> list = barecheckDao.selectBareCheckDataBy(apNo, seqNo);
		List<DisabledApplicationDataUpdateBareCheckCase> returnList = new ArrayList<DisabledApplicationDataUpdateBareCheckCase>();

		for (int i = 0; i < list.size(); i++) {
			Barecheck obj = list.get(i);
			DisabledApplicationDataUpdateBareCheckCase caseObj = new DisabledApplicationDataUpdateBareCheckCase();
			// 轉換日期格式
			obj.setReChkYm(DateUtility.changeWestYearMonthType(obj.getReChkYm()));
			obj.setComReChkYm(DateUtility.changeWestYearMonthType(obj.getComReChkYm()));

			BeanUtility.copyProperties(caseObj, obj);
			// 重新查核狀態 中文
			if (StringUtils.isNotBlank(caseObj.getReChkStatus())) {
				caseObj.setReChkStatusStr(caseObj.getReChkStatus() + "-"
						+ baparamDao.selectParamNameBy(null, "KRECHKSTATUS", caseObj.getReChkStatus()));
			}
			// 重新查核結果 中文
			if (StringUtils.isNotBlank(caseObj.getReChkResult())) {
				caseObj.setReChkResultStr(caseObj.getReChkResult() + "-"
						+ baparamDao.getParamNameForKRECHKRESULT(caseObj.getReChkResult()));
			}
			returnList.add(caseObj);
		}

		return returnList;
	}

	/**
	 * 依傳入的條件取得 老年年金加計金額調整紀錄檔 (<code>BABASICAMT</code>) 的資料<br>
	 * 
	 * @param payCode 給付別
	 * @param payYm   給付年月
	 * @return <code>BigDecimal</code> 物件
	 */
	public BigDecimal selectBasicAmtForPaymentQuery(String payCode, String payYm) {

		BigDecimal basicAmt = babasicamtDao.selectBasicAmtForPaymentQuery(payCode, payYm);
		if (basicAmt == null) {
			basicAmt = new BigDecimal("4000");
		}
		return basicAmt;
	}

	/**
	 * FOR 維護作業 - 年金受理資料轉出
	 * 
	 * @param apNo 受理編號
	 * @return <code>List<String></code> 物件
	 */
	public List<String> forAnnuityAcceptDataTransfer(String apNo) {
		return baappbaseDao.ForAnnuityAcceptDataTransfer(apNo);
	}

	/**
	 * 依傳入條件取得 被保險人基本資料檔(<code>CIPB</code>) for 年金統計查詢
	 * 
	 * @return
	 */
	public List<Cipb> qryCipbFmkList() {
		return cipbDao.qryCipbFmkList();
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 件數及金額 的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<AnnuityStatisticsCase></code> 物件
	 */
	public List<AnnuityStatisticsCase> qryRptType1X(String payKind, String paymentYMStart, String paymentYMEnd) {
		List<AnnuityStatisticsCase> annuityStatisticsCase = new ArrayList<AnnuityStatisticsCase>();
		List<Bansf> bansf = bansfDao.qryRptType1X(payKind, paymentYMStart, paymentYMEnd);
		for (Bansf bs : bansf) {
			AnnuityStatisticsCase returnCase = new AnnuityStatisticsCase();
			BeanUtility.copyProperties(returnCase, bs);
			returnCase.setIssuYm(DateUtility.changeWestYearMonthType(returnCase.getIssuYm()));
			returnCase.setPayCnt(returnCase.getPayCnt());
			returnCase.setpAmts(returnCase.getpAmts());
			annuityStatisticsCase.add(returnCase);
		}
		return annuityStatisticsCase;
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 件數及金額 的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<AnnuityStatisticsCase> qryRptType1X1(String payKind, String paymentYMStart, String paymentYMEnd) {
		List<AnnuityStatisticsCase> annuityStatisticsCase = new ArrayList<AnnuityStatisticsCase>();
		List<Bansf> bansf = bansfDao.qryRptType1X1(payKind, paymentYMStart, paymentYMEnd);
		for (Bansf bs : bansf) {
			AnnuityStatisticsCase returnCase = new AnnuityStatisticsCase();
			BeanUtility.copyProperties(returnCase, bs);
			returnCase.setIssuYm(DateUtility.changeWestYearMonthType(returnCase.getIssuYm()));
			returnCase.setMaxPayAmt(bs.getMaxPayAmt());
			annuityStatisticsCase.add(returnCase);
		}
		return annuityStatisticsCase;
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 件數及金額 的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<AnnuityStatisticsCase> qryRptType1X2(String payKind, String paymentYMStart, String paymentYMEnd) {
		List<AnnuityStatisticsCase> annuityStatisticsCase = new ArrayList<AnnuityStatisticsCase>();
		List<Bansf> bansf = bansfDao.qryRptType1X2(payKind, paymentYMStart, paymentYMEnd);
		for (Bansf bs : bansf) {
			AnnuityStatisticsCase returnCase = new AnnuityStatisticsCase();
			BeanUtility.copyProperties(returnCase, bs);
			returnCase.setIssuYm(DateUtility.changeWestYearMonthType(returnCase.getIssuYm()));
			returnCase.setMinPayAmt(bs.getMinPayAmt());
			annuityStatisticsCase.add(returnCase);
		}
		return annuityStatisticsCase;
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 性別 的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<AnnuityStatisticsCase> qryRptType1S(String payKind, String paymentYMStart, String paymentYMEnd) {
		List<AnnuityStatisticsCase> annuityStatisticsCase = new ArrayList<AnnuityStatisticsCase>();
		List<Bansf> bansf = bansfDao.qryRptType1S(payKind, paymentYMStart, paymentYMEnd);
		for (Bansf bs : bansf) {
			AnnuityStatisticsCase returnCase = new AnnuityStatisticsCase();
			BeanUtility.copyProperties(returnCase, bs);
			returnCase.setIssuYm(DateUtility.changeWestYearMonthType(returnCase.getIssuYm()));
			returnCase.setSex(bs.getSex());
			returnCase.setPayCnt(bs.getPayCnt());
			returnCase.setpAmts(bs.getpAmts());
			annuityStatisticsCase.add(returnCase);
		}
		return annuityStatisticsCase;
	}

	/**
	 * 依傳入條件取得 投保單位檔 (<code>CAUB</code>) 單位類別資料 for 年金統計查詢
	 * 
	 * @param ubType 單位類別
	 * @return
	 */
	public List<Caub> qryUbTypeList() {
		return caubDao.qryUbTypeList();
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 單位類別 的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @param ubType         單位類別
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<AnnuityStatisticsCase> qryRptType1U(String payKind, String paymentYMStart, String paymentYMEnd) {
		List<AnnuityStatisticsCase> annuityStatisticsCase = new ArrayList<AnnuityStatisticsCase>();
		List<Bansf> bansf = bansfDao.qryRptType1U(payKind, paymentYMStart, paymentYMEnd);
		for (Bansf bs : bansf) {
			AnnuityStatisticsCase returnCase = new AnnuityStatisticsCase();
			BeanUtility.copyProperties(returnCase, bs);
			returnCase.setIssuYm(DateUtility.changeWestYearMonthType(returnCase.getIssuYm()));
			returnCase.setUbType(bs.getUbType());
			returnCase.setPayCnt(bs.getPayCnt());
			returnCase.setpAmts(bs.getpAmts());
			annuityStatisticsCase.add(returnCase);
		}

		return annuityStatisticsCase;
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 國籍別 的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<AnnuityStatisticsCase> qryRptType1N(String payKind, String paymentYMStart, String paymentYMEnd) {
		List<AnnuityStatisticsCase> annuityStatisticsCase = new ArrayList<AnnuityStatisticsCase>();
		List<Bansf> bansf = bansfDao.qryRptType1N(payKind, paymentYMStart, paymentYMEnd);
		for (Bansf bs : bansf) {
			AnnuityStatisticsCase returnCase = new AnnuityStatisticsCase();
			BeanUtility.copyProperties(returnCase, bs);
			returnCase.setIssuYm(DateUtility.changeWestYearMonthType(returnCase.getIssuYm()));
			returnCase.setEvtNationTpe(bs.getEvtNationTpe());
			returnCase.setPayCnt(bs.getPayCnt());
			returnCase.setpAmts(bs.getpAmts());
			annuityStatisticsCase.add(returnCase);
		}
		return annuityStatisticsCase;
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 國籍別 的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<AnnuityStatisticsCase> qryRptType1N1(String payKind, String paymentYMStart, String paymentYMEnd) {
		List<AnnuityStatisticsCase> annuityStatisticsCase = new ArrayList<AnnuityStatisticsCase>();
		List<Bansf> bansf = bansfDao.qryRptType1N1(payKind, paymentYMStart, paymentYMEnd);
		for (Bansf bs : bansf) {
			AnnuityStatisticsCase returnCase = new AnnuityStatisticsCase();
			BeanUtility.copyProperties(returnCase, bs);
			returnCase.setHinPays(bs.getHinPays());
			annuityStatisticsCase.add(returnCase);
		}
		return annuityStatisticsCase;
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 外籍別 的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @param cipbFMk        外籍別(CIPB)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<AnnuityStatisticsCase> qryRptType1C(String payKind, String paymentYMStart, String paymentYMEnd) {
		List<AnnuityStatisticsCase> annuityStatisticsCase = new ArrayList<AnnuityStatisticsCase>();

		List<Bansf> bansf = bansfDao.qryRptType1C(payKind, paymentYMStart, paymentYMEnd);
		for (Bansf bs : bansf) {
			AnnuityStatisticsCase returnCase = new AnnuityStatisticsCase();
			BeanUtility.copyProperties(returnCase, bs);
			returnCase.setIssuYm(DateUtility.changeWestYearMonthType(returnCase.getIssuYm()));
			returnCase.setCipbFMk(bs.getCipbFMk());
			returnCase.setPayCnt(bs.getPayCnt());
			returnCase.setpAmts(bs.getpAmts());
			annuityStatisticsCase.add(returnCase);
		}

		return annuityStatisticsCase;
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 外籍別 的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @param cipbFMk        外籍別(CIPB)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<AnnuityStatisticsCase> qryRptType1C1(String payKind, String paymentYMStart, String paymentYMEnd) {
		List<AnnuityStatisticsCase> annuityStatisticsCase = new ArrayList<AnnuityStatisticsCase>();
		List<Bansf> bansf = bansfDao.qryRptType1C1(payKind, paymentYMStart, paymentYMEnd);
		for (Bansf bs : bansf) {
			AnnuityStatisticsCase returnCase = new AnnuityStatisticsCase();
			BeanUtility.copyProperties(returnCase, bs);
			returnCase.setHinPays(bs.getHinPays());
			annuityStatisticsCase.add(returnCase);
		}
		return annuityStatisticsCase;
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 傷病分類 的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<AnnuityStatisticsCase> qryRptType1E(String payKind, String paymentYMStart, String paymentYMEnd) {
		List<AnnuityStatisticsCase> annuityStatisticsCase = new ArrayList<AnnuityStatisticsCase>();
		List<Bansf> bansf = bansfDao.qryRptType1E(payKind, paymentYMStart, paymentYMEnd);
		for (Bansf bs : bansf) {
			AnnuityStatisticsCase returnCase = new AnnuityStatisticsCase();
			BeanUtility.copyProperties(returnCase, bs);
			returnCase.setIssuYm(DateUtility.changeWestYearMonthType(returnCase.getIssuYm()));
			returnCase.setEvType(bs.getEvType());
			returnCase.setPayCnt(bs.getPayCnt());
			returnCase.setpAmts(bs.getpAmts());
			annuityStatisticsCase.add(returnCase);
		}
		return annuityStatisticsCase;
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 傷病分類 的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<AnnuityStatisticsCase> qryRptType1E1(String payKind, String paymentYMStart, String paymentYMEnd) {
		List<AnnuityStatisticsCase> annuityStatisticsCase = new ArrayList<AnnuityStatisticsCase>();
		List<Bansf> bansf = bansfDao.qryRptType1E1(payKind, paymentYMStart, paymentYMEnd);
		for (Bansf bs : bansf) {
			AnnuityStatisticsCase returnCase = new AnnuityStatisticsCase();
			BeanUtility.copyProperties(returnCase, bs);
			returnCase.setIssuYm(DateUtility.changeWestYearMonthType(returnCase.getIssuYm()));
			returnCase.setHinPays(bs.getHinPays());
			annuityStatisticsCase.add(returnCase);
		}
		return annuityStatisticsCase;
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年度平均 的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<AnnuityStatisticsCase> qryRptType2X(String payKind, String paymentYMStart, String paymentYMEnd) {
		List<AnnuityStatisticsCase> annuityStatisticsCase = new ArrayList<AnnuityStatisticsCase>();
		List<Bansf> bansf = bansfDao.qryRptType2X(payKind, paymentYMStart, paymentYMEnd);
		for (Bansf bs : bansf) {
			AnnuityStatisticsCase returnCase = new AnnuityStatisticsCase();
			BeanUtility.copyProperties(returnCase, bs);
			returnCase.setIssuYm(DateUtility.changeWestYearMonthType(returnCase.getIssuYm()));
			annuityStatisticsCase.add(returnCase);
		}
		return annuityStatisticsCase;
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年度平均 性別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<AnnuityStatisticsCase> qryRptType2S(String payKind, String paymentYMStart, String paymentYMEnd) {
		List<AnnuityStatisticsCase> annuityStatisticsCase = new ArrayList<AnnuityStatisticsCase>();
		List<Bansf> bansf = bansfDao.qryRptType2S(payKind, paymentYMStart, paymentYMEnd);
		for (Bansf bs : bansf) {
			AnnuityStatisticsCase returnCase = new AnnuityStatisticsCase();
			BeanUtility.copyProperties(returnCase, bs);
			returnCase.setIssuYm(DateUtility.changeWestYearMonthType(returnCase.getIssuYm()));
			annuityStatisticsCase.add(returnCase);
		}
		return annuityStatisticsCase;
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年度平均 單位類別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<AnnuityStatisticsCase> qryRptType2U(String payKind, String paymentYMStart, String paymentYMEnd) {
		List<AnnuityStatisticsCase> annuityStatisticsCase = new ArrayList<AnnuityStatisticsCase>();
		List<Bansf> bansf = bansfDao.qryRptType2U(payKind, paymentYMStart, paymentYMEnd);
		for (Bansf bs : bansf) {
			AnnuityStatisticsCase returnCase = new AnnuityStatisticsCase();
			BeanUtility.copyProperties(returnCase, bs);
			returnCase.setIssuYm(DateUtility.changeWestYearMonthType(returnCase.getIssuYm()));
			annuityStatisticsCase.add(returnCase);
		}
		return annuityStatisticsCase;
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年度平均 國籍別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<AnnuityStatisticsCase> qryRptType2N(String payKind, String paymentYMStart, String paymentYMEnd) {
		List<AnnuityStatisticsCase> annuityStatisticsCase = new ArrayList<AnnuityStatisticsCase>();
		List<Bansf> bansf = bansfDao.qryRptType2N(payKind, paymentYMStart, paymentYMEnd);
		for (Bansf bs : bansf) {
			AnnuityStatisticsCase returnCase = new AnnuityStatisticsCase();
			BeanUtility.copyProperties(returnCase, bs);
			returnCase.setIssuYm(DateUtility.changeWestYearMonthType(returnCase.getIssuYm()));
			annuityStatisticsCase.add(returnCase);
		}
		return annuityStatisticsCase;
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年度平均 外籍別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<AnnuityStatisticsCase> qryRptType2C(String payKind, String paymentYMStart, String paymentYMEnd) {
		List<AnnuityStatisticsCase> annuityStatisticsCase = new ArrayList<AnnuityStatisticsCase>();
		List<Bansf> bansf = bansfDao.qryRptType2C(payKind, paymentYMStart, paymentYMEnd);
		for (Bansf bs : bansf) {
			AnnuityStatisticsCase returnCase = new AnnuityStatisticsCase();
			BeanUtility.copyProperties(returnCase, bs);
			returnCase.setIssuYm(DateUtility.changeWestYearMonthType(returnCase.getIssuYm()));
			annuityStatisticsCase.add(returnCase);
		}
		return annuityStatisticsCase;
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年度平均 傷病分類的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<AnnuityStatisticsCase> qryRptType2E(String payKind, String paymentYMStart, String paymentYMEnd) {
		List<AnnuityStatisticsCase> annuityStatisticsCase = new ArrayList<AnnuityStatisticsCase>();
		List<Bansf> bansf = bansfDao.qryRptType2E(payKind, paymentYMStart, paymentYMEnd);
		for (Bansf bs : bansf) {
			AnnuityStatisticsCase returnCase = new AnnuityStatisticsCase();
			BeanUtility.copyProperties(returnCase, bs);
			returnCase.setIssuYm(DateUtility.changeWestYearMonthType(returnCase.getIssuYm()));
			annuityStatisticsCase.add(returnCase);
		}
		return annuityStatisticsCase;
    }
	
    /**
     * 金額級距、展減級距、平均薪資級距及年資級距
     * @param qryType 統計項目 C,D,E,F
     * @param payCode 給付別
     * @param paymentYmStart 西元 核付日期(起)
     * @param paymentYmEnd   西元 核付日期(迄)
     * @param spacing	級距區間
     * @param analysisSelect 分析選項
     * @return
     */
    public AnnuityStatistics3Case queryReportData3Data(String qryType, String payCode, String paymentYmStart,
			String paymentYmEnd, String[] spacing, String analysisSelect) {
    	List<AnnuityStatistics3DtlCase> spacingList = formateSpacingData(spacing);//區間轉為list
    	AnnuityStatistics3Case returnCase = new AnnuityStatistics3Case();
    	BigDecimal totalSum = new BigDecimal(0);
		returnCase.setQryPayCode(payCode);
		returnCase.setQryType(qryType);
		List<AnnuityStatistics3DtlCase> returnList = new ArrayList<AnnuityStatistics3DtlCase>();
    	if (StringUtils.equals(analysisSelect, "X")) {//無
    		for(int i=0; i<spacingList.size(); i++) {//用區間查詢
    			Bansf reportData3Dtl = bansfDao.queryReport3DataXData(payCode, paymentYmStart, paymentYmEnd, qryType, spacingList.get(i).getSpcStart(), spacingList.get(i).getSpcEnd()
    					,spacingList.get(0).getSpcStart(),spacingList.get(spacingList.size()-1).getSpcEnd());
    			AnnuityStatistics3DtlCase returnDtlCase = new AnnuityStatistics3DtlCase();
    			returnDtlCase.setSpcStart(spacingList.get(i).getSpcStart());
				returnDtlCase.setSpcEnd(spacingList.get(i).getSpcEnd()); 
				List<AnnuityStatistics3MetaDtlCase> metaDtlList = new ArrayList<AnnuityStatistics3MetaDtlCase>();
				AnnuityStatistics3MetaDtlCase caseData = new AnnuityStatistics3MetaDtlCase();
    			if(reportData3Dtl!=null) {
    				caseData.setCntRatio(reportData3Dtl.getCntRatio());
    				caseData.setPayCnt(reportData3Dtl.getPayCnt());
    			}else {
    				caseData.setCntRatio("0");
    				caseData.setPayCnt("0");
    			}
    			metaDtlList.add(caseData);//X只會有一筆
    			returnDtlCase.setDtlList(metaDtlList);
    			returnList.add(returnDtlCase);
    		}
    		returnCase.setDtlCase(returnList);
		} else if (StringUtils.equals(analysisSelect, "S")) {//性別
			totalSum = BigDecimal.ZERO;
			for(int i=0; i<spacingList.size(); i++) {
    			List<Bansf> reportData3Dtl = bansfDao.queryReport3DataSData(payCode, paymentYmStart, paymentYmEnd, qryType, spacingList.get(i).getSpcStart(), spacingList.get(i).getSpcEnd()
    					,spacingList.get(0).getSpcStart(),spacingList.get(spacingList.size()-1).getSpcEnd());
    			List<AnnuityStatistics3MetaDtlCase> metaDtlList = new ArrayList<AnnuityStatistics3MetaDtlCase>();
    			if(reportData3Dtl==null || reportData3Dtl.size()== 0) {
    				for(int j=0; j<2; j++) {//男、女
    					AnnuityStatistics3MetaDtlCase metaDtlCase = new AnnuityStatistics3MetaDtlCase();
        				metaDtlCase.setCntRatio("0");
        				metaDtlCase.setPayCnt("0");
        				metaDtlCase.setSex(String.valueOf(j+1));
        				metaDtlList.add(metaDtlCase);
    				}
    			} else if(reportData3Dtl.size()==1) {
    				if(StringUtils.equals(reportData3Dtl.get(0).getSex(), "1")) {//男
    					AnnuityStatistics3MetaDtlCase metaDtlCase0 = new AnnuityStatistics3MetaDtlCase();
    					metaDtlCase0.setCntRatio(reportData3Dtl.get(0).getCntRatio());
    					metaDtlCase0.setPayCnt(reportData3Dtl.get(0).getPayCnt());
    					totalSum = totalSum.add(new BigDecimal(reportData3Dtl.get(0).getPayCnt()));    					
    					metaDtlCase0.setSex(reportData3Dtl.get(0).getSex());
        				metaDtlList.add(metaDtlCase0);
        				AnnuityStatistics3MetaDtlCase metaDtlCase1 = new AnnuityStatistics3MetaDtlCase();
        				metaDtlCase1.setCntRatio("0");
        				metaDtlCase1.setPayCnt("0");
        				metaDtlCase1.setSex(ConstantKey.BAAPPBASE_SEX_2);
        				metaDtlList.add(metaDtlCase1);
    				} 
    				if(StringUtils.equals(reportData3Dtl.get(0).getSex(), "2")) {//女
    					AnnuityStatistics3MetaDtlCase metaDtlCase0 = new AnnuityStatistics3MetaDtlCase();
    					metaDtlCase0.setCntRatio("0");
    					metaDtlCase0.setPayCnt("0");
    					metaDtlCase0.setSex(ConstantKey.BAAPPBASE_SEX_1);
        				metaDtlList.add(metaDtlCase0);
    					AnnuityStatistics3MetaDtlCase metaDtlCase = new AnnuityStatistics3MetaDtlCase();
        				metaDtlCase.setCntRatio(reportData3Dtl.get(0).getCntRatio());
        				metaDtlCase.setPayCnt(reportData3Dtl.get(0).getPayCnt());
        				totalSum = totalSum.add(new BigDecimal(reportData3Dtl.get(0).getPayCnt()));
        				metaDtlCase.setSex(reportData3Dtl.get(0).getSex());
        				metaDtlList.add(metaDtlCase);
    				}
    			}else {
    				for(Bansf bsf:reportData3Dtl) {
        				AnnuityStatistics3MetaDtlCase metaDtlCase = new AnnuityStatistics3MetaDtlCase();
        				metaDtlCase.setCntRatio(bsf.getCntRatio());
        				metaDtlCase.setPayCnt(bsf.getPayCnt());
        				totalSum = totalSum.add(new BigDecimal(bsf.getPayCnt()));
        				metaDtlCase.setSex(bsf.getSex());
        				metaDtlList.add(metaDtlCase);
        			}
    			}
    			AnnuityStatistics3DtlCase returnDtlCase = new AnnuityStatistics3DtlCase();
    			returnDtlCase.setSpcStart(spacingList.get(i).getSpcStart());
				returnDtlCase.setSpcEnd(spacingList.get(i).getSpcEnd()); 
				returnDtlCase.setDtlList(metaDtlList);
				returnList.add(returnDtlCase);
    		}
			returnCase.setDtlCase(returnList);
		} else if (StringUtils.equals(analysisSelect, "U")) {//單位類別
			totalSum = BigDecimal.ZERO;
			List<Caub> caub = caubDao.qryUbTypeList();//FOR UBTYPE
			String[] ubType = new String[caub.size()];
			for(int i=0; i<caub.size(); i++) {
				ubType[i] = caub.get(i).getUbType();
			}
			for(int i=0; i<spacingList.size(); i++) {
				List<Bansf> reportData3Dtl = bansfDao.queryReport3DataUData(payCode, paymentYmStart, paymentYmEnd, qryType, spacingList.get(i).getSpcStart(), spacingList.get(i).getSpcEnd(),ubType
						,spacingList.get(0).getSpcStart(),spacingList.get(spacingList.size()-1).getSpcEnd());
				List<AnnuityStatistics3MetaDtlCase> metaDtlList = new ArrayList<AnnuityStatistics3MetaDtlCase>();
				if(reportData3Dtl==null || reportData3Dtl.size()==0) {//查出來沒有資料
					for(int j=0; j<ubType.length;j++) {//用ubtype Array的數量去RUN，都塞為0
						AnnuityStatistics3MetaDtlCase metaDtlCase = new AnnuityStatistics3MetaDtlCase();
        				metaDtlCase.setCntRatio("0");
        				metaDtlCase.setPayCnt("0");
        				metaDtlCase.setUbType(ubType[j]);;
        				metaDtlList.add(metaDtlCase);
					}
				} else {
					for(int j=0; j<ubType.length;j++) {
						AnnuityStatistics3MetaDtlCase metaDtlCase = new AnnuityStatistics3MetaDtlCase();
						boolean flagUsed = false;//查出來有沒有UBTYPE對應的統計檔
						int flagNum = 0;//查出來第幾個UBTYPE對應的統計檔
						for(int z=0; z<reportData3Dtl.size(); z++) {
							Bansf reportData3DtlCase = reportData3Dtl.get(z);
							if(StringUtils.equals(ubType[j], reportData3DtlCase.getUbType())) {
								flagUsed = true;
								flagNum = z;
								break;
							}
						}
						if(flagUsed == true) {//查出來的有對應的ubtype
	        				metaDtlCase.setCntRatio(reportData3Dtl.get(flagNum).getCntRatio());
	        				metaDtlCase.setPayCnt(reportData3Dtl.get(flagNum).getPayCnt());
	        				totalSum = totalSum.add(new BigDecimal(reportData3Dtl.get(flagNum).getPayCnt())); 
	        				metaDtlCase.setUbType(reportData3Dtl.get(flagNum).getUbType());
	        				metaDtlList.add(metaDtlCase);
						} else {//查出來的沒對應的ubtype
							metaDtlCase.setCntRatio("0");
	        				metaDtlCase.setPayCnt("0");
	        				metaDtlCase.setUbType(ubType[j]);
	        				metaDtlList.add(metaDtlCase);
						}
					}
				}
				AnnuityStatistics3DtlCase returnDtlCase = new AnnuityStatistics3DtlCase();
    			returnDtlCase.setSpcStart(spacingList.get(i).getSpcStart());
				returnDtlCase.setSpcEnd(spacingList.get(i).getSpcEnd()); 
				returnDtlCase.setDtlList(metaDtlList);
				returnList.add(returnDtlCase);
			}
			returnCase.setDtlCase(returnList);
		} else if (StringUtils.equals(analysisSelect, "N")) {//國籍別
			totalSum = BigDecimal.ZERO;
			for(int i=0; i<spacingList.size(); i++) {
    			List<Bansf> reportData3Dtl = bansfDao.queryReport3DataNData(payCode, paymentYmStart, paymentYmEnd, qryType, spacingList.get(i).getSpcStart(), spacingList.get(i).getSpcEnd()
    					,spacingList.get(0).getSpcStart(),spacingList.get(spacingList.size()-1).getSpcEnd());
    			List<AnnuityStatistics3MetaDtlCase> metaDtlList = new ArrayList<AnnuityStatistics3MetaDtlCase>();
    			if(reportData3Dtl==null || reportData3Dtl.size()== 0) {
    				for(int j=0; j<2; j++) {//nationtpe(1,2)
    					AnnuityStatistics3MetaDtlCase metaDtlCase = new AnnuityStatistics3MetaDtlCase();
        				metaDtlCase.setCntRatio("0");
        				metaDtlCase.setPayCnt("0");
        				metaDtlCase.setEvtNationTpe(String.valueOf(j+1));//1-本國、2-外籍
        				metaDtlList.add(metaDtlCase);
    				}
    			} else if(reportData3Dtl.size()==1) {
    				if(StringUtils.equals(reportData3Dtl.get(0).getEvtNationTpe(), "1")) {//本籍
    					AnnuityStatistics3MetaDtlCase metaDtlCase = new AnnuityStatistics3MetaDtlCase();
        				metaDtlCase.setCntRatio(reportData3Dtl.get(0).getCntRatio());
        				metaDtlCase.setPayCnt(reportData3Dtl.get(0).getPayCnt());
        				totalSum = totalSum.add(new BigDecimal(reportData3Dtl.get(0).getPayCnt())); 
        				metaDtlCase.setEvtNationTpe(reportData3Dtl.get(0).getEvtNationTpe());
        				metaDtlList.add(metaDtlCase);
        				AnnuityStatistics3MetaDtlCase metaDtlCase2 = new AnnuityStatistics3MetaDtlCase();
        				metaDtlCase2.setCntRatio("0");
        				metaDtlCase2.setPayCnt("0");
        				metaDtlCase2.setEvtNationTpe("2");//外籍
        				metaDtlList.add(metaDtlCase2);
    				} 
    				if(StringUtils.equals(reportData3Dtl.get(0).getEvtNationTpe(), "2")) {//外籍
    					AnnuityStatistics3MetaDtlCase metaDtlCase0 = new AnnuityStatistics3MetaDtlCase();
    					metaDtlCase0.setCntRatio("0");
    					metaDtlCase0.setPayCnt("0");
    					metaDtlCase0.setEvtNationTpe("1");//本籍
        				metaDtlList.add(metaDtlCase0);
    					AnnuityStatistics3MetaDtlCase metaDtlCase = new AnnuityStatistics3MetaDtlCase();
        				metaDtlCase.setCntRatio(reportData3Dtl.get(0).getCntRatio());
        				totalSum = totalSum.add(new BigDecimal(reportData3Dtl.get(0).getPayCnt())); 
        				metaDtlCase.setPayCnt(reportData3Dtl.get(0).getPayCnt());
        				metaDtlCase.setEvtNationTpe(reportData3Dtl.get(0).getEvtNationTpe());
        				metaDtlList.add(metaDtlCase);
    				}
    			}else {
    				for(Bansf bsf:reportData3Dtl) {
        				AnnuityStatistics3MetaDtlCase metaDtlCase = new AnnuityStatistics3MetaDtlCase();
        				metaDtlCase.setCntRatio(bsf.getCntRatio());
        				metaDtlCase.setPayCnt(bsf.getPayCnt());
        				totalSum = totalSum.add(new BigDecimal(bsf.getPayCnt())); 
        				metaDtlCase.setEvtNationTpe(bsf.getEvtNationTpe());
        				metaDtlList.add(metaDtlCase);
        			}
    			}
    			AnnuityStatistics3DtlCase returnDtlCase = new AnnuityStatistics3DtlCase();
    			returnDtlCase.setSpcStart(spacingList.get(i).getSpcStart());
				returnDtlCase.setSpcEnd(spacingList.get(i).getSpcEnd()); 
				returnDtlCase.setDtlList(metaDtlList);
				returnList.add(returnDtlCase);
    		}
			returnCase.setDtlCase(returnList);
		} else if (StringUtils.equals(analysisSelect, "C")) {//外籍別
			totalSum = BigDecimal.ZERO;
			List<Cipb> cipb = cipbDao.qryCipbFmkList();//FOR CipbFmk
			String[] cipbFmk = new String[cipb.size()];
			for(int i=0; i<cipb.size(); i++) {
				cipbFmk[i] = cipb.get(i).getCipbFMk();
			}
			for(int i=0; i<spacingList.size(); i++) {
				List<Bansf> reportData3Dtl = bansfDao.queryReport3DataCData(payCode, paymentYmStart, paymentYmEnd, qryType, spacingList.get(i).getSpcStart(), spacingList.get(i).getSpcEnd(),cipbFmk
						,spacingList.get(0).getSpcStart(),spacingList.get(spacingList.size()-1).getSpcEnd());
				List<AnnuityStatistics3MetaDtlCase> metaDtlList = new ArrayList<AnnuityStatistics3MetaDtlCase>();
				if(reportData3Dtl==null || reportData3Dtl.size()==0) {//查出來沒有資料
					for(int j=0; j<cipbFmk.length;j++) {//用cipbFmk Array的數量去RUN，都塞為0
						AnnuityStatistics3MetaDtlCase metaDtlCase = new AnnuityStatistics3MetaDtlCase();
        				metaDtlCase.setCntRatio("0");
        				metaDtlCase.setPayCnt("0");
        				metaDtlCase.setCipbFmk(cipbFmk[j]);;
        				metaDtlList.add(metaDtlCase);
					}
				} else {
					for(int j=0; j<cipbFmk.length;j++) {
						AnnuityStatistics3MetaDtlCase metaDtlCase = new AnnuityStatistics3MetaDtlCase();
						boolean flagUsed = false;//查出來有沒有cipbFmk對應的統計檔
						int flagNum = 0;//查出來第幾個cipbFmk對應的統計檔
						for(int z=0; z<reportData3Dtl.size(); z++) {
							Bansf reportData3DtlCase = reportData3Dtl.get(z);
							if(StringUtils.equals(cipbFmk[j], reportData3DtlCase.getCipbFMk())) {
								flagUsed = true;
								flagNum = z;
								break;
							}
						}
						if(flagUsed == true) {//查出來的有對應的cipbFmk
	        				metaDtlCase.setCntRatio(reportData3Dtl.get(flagNum).getCntRatio());
	        				metaDtlCase.setPayCnt(reportData3Dtl.get(flagNum).getPayCnt());
	        				totalSum = totalSum.add(new BigDecimal(reportData3Dtl.get(flagNum).getPayCnt())); 
	        				metaDtlCase.setCipbFmk(reportData3Dtl.get(flagNum).getCipbFMk());
	        				metaDtlList.add(metaDtlCase);
						} else {//查出來的沒對應的cipbFmk
							metaDtlCase.setCntRatio("0");
	        				metaDtlCase.setPayCnt("0");
	        				metaDtlCase.setCipbFmk(cipbFmk[j]);
	        				metaDtlList.add(metaDtlCase);
						}
					}
				}
				AnnuityStatistics3DtlCase returnDtlCase = new AnnuityStatistics3DtlCase();
    			returnDtlCase.setSpcStart(spacingList.get(i).getSpcStart());
				returnDtlCase.setSpcEnd(spacingList.get(i).getSpcEnd()); 
				returnDtlCase.setDtlList(metaDtlList);
				returnList.add(returnDtlCase);
			}
			returnCase.setDtlCase(returnList);
		} else if (StringUtils.equals(analysisSelect, "E")) {//傷病分類
			totalSum = BigDecimal.ZERO;
			for(int i=0; i<spacingList.size(); i++) {
    			List<Bansf> reportData3Dtl = bansfDao.queryReport3DataEData(payCode, paymentYmStart, paymentYmEnd, qryType, spacingList.get(i).getSpcStart(), spacingList.get(i).getSpcEnd()
    					,spacingList.get(0).getSpcStart(),spacingList.get(spacingList.size()-1).getSpcEnd());
    			List<AnnuityStatistics3MetaDtlCase> metaDtlList = new ArrayList<AnnuityStatistics3MetaDtlCase>();
    			if(reportData3Dtl==null || reportData3Dtl.size()== 0) {
    				int evType = 1;//傷病分類(1,3)
    				for(int j=0; j<2; j++) {
    					AnnuityStatistics3MetaDtlCase metaDtlCase = new AnnuityStatistics3MetaDtlCase();
        				metaDtlCase.setCntRatio("0");
        				metaDtlCase.setPayCnt("0");
        				metaDtlCase.setEvType(String.valueOf(evType));
        				metaDtlList.add(metaDtlCase);
        				evType = evType + 2;
    				}
    			} else if(reportData3Dtl.size()==1) {
    				if(StringUtils.equals(reportData3Dtl.get(0).getEvType(), "1")) {//1-普通傷病
    					AnnuityStatistics3MetaDtlCase metaDtlCase = new AnnuityStatistics3MetaDtlCase();
        				metaDtlCase.setCntRatio(reportData3Dtl.get(0).getCntRatio());
        				metaDtlCase.setPayCnt(reportData3Dtl.get(0).getPayCnt());
        				totalSum = totalSum.add(new BigDecimal(reportData3Dtl.get(0).getPayCnt())); 
        				metaDtlCase.setEvType(reportData3Dtl.get(0).getEvType());
        				AnnuityStatistics3MetaDtlCase metaDtlCase2 = new AnnuityStatistics3MetaDtlCase();
        				metaDtlCase2.setCntRatio("0");
        				metaDtlCase2.setPayCnt("0");
        				metaDtlCase2.setEvType("3");//3-職業傷病
        				metaDtlList.add(metaDtlCase2);
    				} 
    				if(StringUtils.equals(reportData3Dtl.get(0).getEvType(), "3")) {//3-職業傷病
    					AnnuityStatistics3MetaDtlCase metaDtlCase0 = new AnnuityStatistics3MetaDtlCase();
    					metaDtlCase0.setCntRatio("0");
    					metaDtlCase0.setPayCnt("0");
    					metaDtlCase0.setEvType("1");//1-普通傷病
        				metaDtlList.add(metaDtlCase0);
    					AnnuityStatistics3MetaDtlCase metaDtlCase = new AnnuityStatistics3MetaDtlCase();
        				metaDtlCase.setCntRatio(reportData3Dtl.get(0).getCntRatio());
        				metaDtlCase.setPayCnt(reportData3Dtl.get(0).getPayCnt());
        				totalSum = totalSum.add(new BigDecimal(reportData3Dtl.get(0).getPayCnt())); 
        				metaDtlCase.setEvType(reportData3Dtl.get(0).getEvType());
        				metaDtlList.add(metaDtlCase);
    				}
    			}else {
    				for(Bansf bsf:reportData3Dtl) {
        				AnnuityStatistics3MetaDtlCase metaDtlCase = new AnnuityStatistics3MetaDtlCase();
        				metaDtlCase.setCntRatio(bsf.getCntRatio());
        				metaDtlCase.setPayCnt(bsf.getPayCnt());
        				totalSum = totalSum.add(new BigDecimal(bsf.getPayCnt())); 
        				metaDtlCase.setEvType(bsf.getEvType());
        				metaDtlList.add(metaDtlCase);
        			}
    			}
    			AnnuityStatistics3DtlCase returnDtlCase = new AnnuityStatistics3DtlCase();
    			returnDtlCase.setSpcStart(spacingList.get(i).getSpcStart());
				returnDtlCase.setSpcEnd(spacingList.get(i).getSpcEnd()); 
				returnDtlCase.setDtlList(metaDtlList);
				returnList.add(returnDtlCase);
    		}
			returnCase.setDtlCase(returnList);
		}
    	returnCase.setTotalSum(totalSum);
    	returnCase.setAnalysisSelect(analysisSelect);
    	return returnCase;
	}
    
    /**
     * 處裡spacing 轉換為起迄list
     * @param spacing
     * @return
     */
    public List<AnnuityStatistics3DtlCase> formateSpacingData(String[] spacing){
    	List<AnnuityStatistics3DtlCase> spacingList = new ArrayList<AnnuityStatistics3DtlCase>();
    	for(int i=0; i<spacing.length; i+=2) {
    		AnnuityStatistics3DtlCase spacingCse = new AnnuityStatistics3DtlCase();
    		if(i+1<spacing.length) {
    			spacingCse.setSpcStart(spacing[i]);
        		spacingCse.setSpcEnd(spacing[i+1]);
        		spacingList.add(spacingCse);
    		}
    	}
    	return spacingList;
    }

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年齡級距 分析選項 無的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<AnnuityStatisticsCase> qryRptType4XA(String payKind, String paymentYMStart, String paymentYMEnd) {
		List<AnnuityStatisticsCase> annuityStatisticsCase = new ArrayList<AnnuityStatisticsCase>();
		List<Bansf> bansf = bansfDao.qryRptType4XA(payKind, paymentYMStart, paymentYMEnd);
		for (Bansf bs : bansf) {
			AnnuityStatisticsCase returnCase = new AnnuityStatisticsCase();
			BeanUtility.copyProperties(returnCase, bs);
			annuityStatisticsCase.add(returnCase);
		}
		return annuityStatisticsCase;
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 投保薪資級距 分析選項 無的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<AnnuityStatisticsCase> qryRptType4XW(String payKind, String paymentYMStart, String paymentYMEnd) {
		List<AnnuityStatisticsCase> annuityStatisticsCase = new ArrayList<AnnuityStatisticsCase>();
		List<Bansf> bansf = bansfDao.qryRptType4XW(payKind, paymentYMStart, paymentYMEnd);
		for (Bansf bs : bansf) {
			AnnuityStatisticsCase returnCase = new AnnuityStatisticsCase();
			BeanUtility.copyProperties(returnCase, bs);
			annuityStatisticsCase.add(returnCase);
		}
		return annuityStatisticsCase;
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年齡級距 分析選項 性別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<AnnuityStatisticsCase> qryRptType4SA(String payKind, String paymentYMStart, String paymentYMEnd) {
		List<AnnuityStatisticsCase> annuityStatisticsCase = new ArrayList<AnnuityStatisticsCase>();
		List<Bansf> bansf = bansfDao.qryRptType4SA(payKind, paymentYMStart, paymentYMEnd);
		for (Bansf bs : bansf) {
			AnnuityStatisticsCase returnCase = new AnnuityStatisticsCase();
			BeanUtility.copyProperties(returnCase, bs);
			annuityStatisticsCase.add(returnCase);
		}
		return annuityStatisticsCase;
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 投保薪資級距 分析選項 性別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<AnnuityStatisticsCase> qryRptType4SW(String payKind, String paymentYMStart, String paymentYMEnd) {
		List<AnnuityStatisticsCase> annuityStatisticsCase = new ArrayList<AnnuityStatisticsCase>();
		List<Bansf> bansf = bansfDao.qryRptType4SW(payKind, paymentYMStart, paymentYMEnd);
		for (Bansf bs : bansf) {
			AnnuityStatisticsCase returnCase = new AnnuityStatisticsCase();
			BeanUtility.copyProperties(returnCase, bs);
			annuityStatisticsCase.add(returnCase);
		}
		return annuityStatisticsCase;
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年齡級距 分析選項 單位類別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<AnnuityStatisticsCase> qryRptType4UA(String payKind, String paymentYMStart, String paymentYMEnd) {
		List<AnnuityStatisticsCase> annuityStatisticsCase = new ArrayList<AnnuityStatisticsCase>();
		List<Bansf> bansf = bansfDao.qryRptType4UA(payKind, paymentYMStart, paymentYMEnd);
		for (Bansf bs : bansf) {
			AnnuityStatisticsCase returnCase = new AnnuityStatisticsCase();
			BeanUtility.copyProperties(returnCase, bs);
			annuityStatisticsCase.add(returnCase);
		}
		return annuityStatisticsCase;
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 投保薪資級距 分析選項 單位類別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<AnnuityStatisticsCase> qryRptType4UW(String payKind, String paymentYMStart, String paymentYMEnd) {
		List<AnnuityStatisticsCase> annuityStatisticsCase = new ArrayList<AnnuityStatisticsCase>();
		List<Bansf> bansf = bansfDao.qryRptType4UW(payKind, paymentYMStart, paymentYMEnd);
		for (Bansf bs : bansf) {
			AnnuityStatisticsCase returnCase = new AnnuityStatisticsCase();
			BeanUtility.copyProperties(returnCase, bs);
			annuityStatisticsCase.add(returnCase);
		}
		return annuityStatisticsCase;
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年齡級距 分析選項 國籍別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<AnnuityStatisticsCase> qryRptType4NA(String payKind, String paymentYMStart, String paymentYMEnd) {
		List<AnnuityStatisticsCase> annuityStatisticsCase = new ArrayList<AnnuityStatisticsCase>();
		List<Bansf> bansf = bansfDao.qryRptType4NA(payKind, paymentYMStart, paymentYMEnd);
		for (Bansf bs : bansf) {
			AnnuityStatisticsCase returnCase = new AnnuityStatisticsCase();
			BeanUtility.copyProperties(returnCase, bs);
			annuityStatisticsCase.add(returnCase);
		}
		return annuityStatisticsCase;
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 投保薪資級距 分析選項 國籍別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<AnnuityStatisticsCase> qryRptType4NW(String payKind, String paymentYMStart, String paymentYMEnd) {
		List<AnnuityStatisticsCase> annuityStatisticsCase = new ArrayList<AnnuityStatisticsCase>();
		List<Bansf> bansf = bansfDao.qryRptType4NW(payKind, paymentYMStart, paymentYMEnd);
		for (Bansf bs : bansf) {
			AnnuityStatisticsCase returnCase = new AnnuityStatisticsCase();
			BeanUtility.copyProperties(returnCase, bs);
			annuityStatisticsCase.add(returnCase);
		}
		return annuityStatisticsCase;
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年齡級距 分析選項 外籍別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<AnnuityStatisticsCase> qryRptType4CA(String payKind, String paymentYMStart, String paymentYMEnd) {
		List<AnnuityStatisticsCase> annuityStatisticsCase = new ArrayList<AnnuityStatisticsCase>();
		List<Bansf> bansf = bansfDao.qryRptType4CA(payKind, paymentYMStart, paymentYMEnd);
		for (Bansf bs : bansf) {
			AnnuityStatisticsCase returnCase = new AnnuityStatisticsCase();
			BeanUtility.copyProperties(returnCase, bs);
			annuityStatisticsCase.add(returnCase);
		}
		return annuityStatisticsCase;
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 投保薪資級距 分析選項 外籍別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<AnnuityStatisticsCase> qryRptType4CW(String payKind, String paymentYMStart, String paymentYMEnd) {
		List<AnnuityStatisticsCase> annuityStatisticsCase = new ArrayList<AnnuityStatisticsCase>();
		List<Bansf> bansf = bansfDao.qryRptType4CW(payKind, paymentYMStart, paymentYMEnd);
		for (Bansf bs : bansf) {
			AnnuityStatisticsCase returnCase = new AnnuityStatisticsCase();
			BeanUtility.copyProperties(returnCase, bs);
			annuityStatisticsCase.add(returnCase);
		}
		return annuityStatisticsCase;
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年齡級距 分析選項 傷病分類的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<AnnuityStatisticsCase> qryRptType4EA(String payKind, String paymentYMStart, String paymentYMEnd) {
		List<AnnuityStatisticsCase> annuityStatisticsCase = new ArrayList<AnnuityStatisticsCase>();
		List<Bansf> bansf = bansfDao.qryRptType4EA(payKind, paymentYMStart, paymentYMEnd);
		for (Bansf bs : bansf) {
			AnnuityStatisticsCase returnCase = new AnnuityStatisticsCase();
			BeanUtility.copyProperties(returnCase, bs);
			annuityStatisticsCase.add(returnCase);
		}
		return annuityStatisticsCase;
	}

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 投保薪資級距 分析選項 傷病分類的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<AnnuityStatisticsCase> qryRptType4EW(String payKind, String paymentYMStart, String paymentYMEnd) {
		List<AnnuityStatisticsCase> annuityStatisticsCase = new ArrayList<AnnuityStatisticsCase>();
		List<Bansf> bansf = bansfDao.qryRptType4EW(payKind, paymentYMStart, paymentYMEnd);
		for (Bansf bs : bansf) {
			AnnuityStatisticsCase returnCase = new AnnuityStatisticsCase();
			BeanUtility.copyProperties(returnCase, bs);
			annuityStatisticsCase.add(returnCase);
		}
		return annuityStatisticsCase;
	}

	/**
	 * 依傳入條件取得 戶政全戶檔(<code>CVLDTL</code>) 資料清單
	 *
	 * @param evtIds
	 * @return String
	 */
	public Cvldtl selectHaddrBy(String evtIds) {
		return cvldtlDao.selectHaddrBy(evtIds);
	}
	
	//Added By LouisChange 20200311 begin
	/**
     * 依傳入條件取得 戶政全戶檔(<code>CVLDTL</code>) 資料清單
     * 
     * @param idn 事故者身分證號
     * @param ebDate 事故者出生日期
     * @return 內含 <code>Cvldtl</code> 物件的 List
     */
    public List<Cvldtl> selectRmpNameBy(String idn, String ebDate) {
	    return cvldtlDao.selectRmpNameBy(idn, ebDate);
    }
    //Added By LouisChange 20200311 begin

	public void setBaappbaseDao(BaappbaseDao baappbaseDao) {
		this.baappbaseDao = baappbaseDao;
	}

	public void setBapadchkDao(BapadchkDao bapadchkDao) {
		this.bapadchkDao = bapadchkDao;
	}

	public void setBaapplogDao(BaapplogDao baapplogDao) {
		this.baapplogDao = baapplogDao;
	}

	public void setBadaprDao(BadaprDao badaprDao) {
		this.badaprDao = badaprDao;
	}

	public void setBachkfileDao(BachkfileDao bachkfileDao) {
		this.bachkfileDao = bachkfileDao;
	}

	public void setBasenimaintDao(BasenimaintDao basenimaintDao) {
		this.basenimaintDao = basenimaintDao;
	}

	public void setCiptDao(CiptDao ciptDao) {
		this.ciptDao = ciptDao;
	}

	public void setBaoncepayDao(BaoncepayDao baoncepayDao) {
		this.baoncepayDao = baoncepayDao;
	}

	public void setBaunacprecDao(BaunacprecDao baunacprecDao) {
		this.baunacprecDao = baunacprecDao;
	}

	public void setBaunacpdtlDao(BaunacpdtlDao baunacpdtlDao) {
		this.baunacpdtlDao = baunacpdtlDao;
	}

	// public void setBapaallocateDao(BapaallocateDao bapaallocateDao) {
	// this.bapaallocateDao = bapaallocateDao;
	// }

	public void setBbcmf09Dao(Bbcmf09Dao bbcmf09Dao) {
		this.bbcmf09Dao = bbcmf09Dao;
	}

	public void setPbbmsaDao(PbbmsaDao pbbmsaDao) {
		this.pbbmsaDao = pbbmsaDao;
	}

	public void setBirefDao(BirefDao birefDao) {
		this.birefDao = birefDao;
	}

	public void setNbappbaseDao(NbappbaseDao nbappbaseDao) {
		this.nbappbaseDao = nbappbaseDao;
	}

	public void setBaparamDao(BaparamDao baparamDao) {
		this.baparamDao = baparamDao;
	}

	public void setMaadmrecDao(MaadmrecDao maadmrecDao) {
		this.maadmrecDao = maadmrecDao;
	}

	public void setBaappexpandDao(BaappexpandDao baappexpandDao) {
		this.baappexpandDao = baappexpandDao;
	}

	public void setBbcmf07Dao(Bbcmf07Dao bbcmf07Dao) {
		this.bbcmf07Dao = bbcmf07Dao;
	}

	public void setBafamilyDao(BafamilyDao bafamilyDao) {
		this.bafamilyDao = bafamilyDao;
	}

	public void setBabcml7Dao(Babcml7Dao babcml7Dao) {
		this.babcml7Dao = babcml7Dao;
	}

	public void setBastudtermDao(BastudtermDao bastudtermDao) {
		this.bastudtermDao = bastudtermDao;
	}

	public void setCipgDao(CipgDao cipgDao) {
		this.cipgDao = cipgDao;
	}

	public void setBacpirecDao(BacpirecDao bacpirecDao) {
		this.bacpirecDao = bacpirecDao;
	}

	public void setBacompelnopayDao(BacompelnopayDao bacompelnopayDao) {
		this.bacompelnopayDao = bacompelnopayDao;
	}

	public void setBaarclistDao(BaarclistDao baarclistDao) {
		this.baarclistDao = baarclistDao;
	}

	public void setNbdaprDao(NbdaprDao nbdaprDao) {
		this.nbdaprDao = nbdaprDao;
	}

	public void setBapaavgmonDao(BapaavgmonDao bapaavgmonDao) {
		this.bapaavgmonDao = bapaavgmonDao;
	}

	public void setBadupeidnDao(BadupeidnDao badupeidnDao) {
		this.badupeidnDao = badupeidnDao;
	}

	public void setBarecheckDao(BarecheckDao barecheckDao) {
		this.barecheckDao = barecheckDao;
	}

	public void setBabasicamtDao(BabasicamtDao babasicamtDao) {
		this.babasicamtDao = babasicamtDao;
	}

	public void setNpcodeDao(NpcodeDao npcodeDao) {
		this.npcodeDao = npcodeDao;
	}

	public void setBaunqualifiednoticeDao(BaunqualifiednoticeDao baunqualifiednoticeDao) {
		this.baunqualifiednoticeDao = baunqualifiednoticeDao;
	}

	public void setBahandicaptermDao(BahandicaptermDao bahandicaptermDao) {
		this.bahandicaptermDao = bahandicaptermDao;
	}

	public void setBansfDao(BansfDao bansfDao) {
		this.bansfDao = bansfDao;
	}

	public void setCipbDao(CipbDao cipbDao) {
		this.cipbDao = cipbDao;
	}

	public void setCaubDao(CaubDao caubDao) {
		this.caubDao = caubDao;
	}

	public void setCvldtlDao(CvldtlDao cvldtlDao) {
		this.cvldtlDao = cvldtlDao;
	}
}
