package tw.gov.bli.ba.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.dao.BaappbaseDao;
import tw.gov.bli.ba.dao.BaappexpandDao;
import tw.gov.bli.ba.dao.BabasicamtDao;
import tw.gov.bli.ba.dao.BachkfileDao;
import tw.gov.bli.ba.dao.BacountryDao;
import tw.gov.bli.ba.dao.BacpirecDao;
import tw.gov.bli.ba.dao.BadaprDao;
import tw.gov.bli.ba.dao.BafamilyDao;
import tw.gov.bli.ba.dao.BamarginamtnotifyDao;
import tw.gov.bli.ba.dao.BapawagerecDao;
import tw.gov.bli.ba.dao.BadupeidnDao;
import tw.gov.bli.ba.dao.CaubDao;
import tw.gov.bli.ba.dao.CipbDao;
import tw.gov.bli.ba.dao.CipgDao;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Baappexpand;
import tw.gov.bli.ba.domain.Babasicamt;
import tw.gov.bli.ba.domain.Babcml7;
import tw.gov.bli.ba.domain.Badapr;
import tw.gov.bli.ba.domain.Bafamily;
import tw.gov.bli.ba.domain.Cipg;
import tw.gov.bli.ba.rpt.cases.CheckAmtCase;
import tw.gov.bli.ba.rpt.cases.SurvivorBasicAmtCase;
import tw.gov.bli.common.helper.SpringHelper;

public class BaReportReplaceUtility {
	private Baappbase baappbase;
	private Baappbase benData;
	private Baappexpand baappexpand;
	private List<Baappbase> baappbaseList;
	private List<Baappbase> baappbasePrintList;
	private List<Baappbase> baappbasePrintListA150;
	private Badapr badapr;
	private Badapr badaprA133; // 受理案件的核定總額資料 For A133
	private List<Bafamily> BafamilyDataList;
	private Babcml7 babcml7;
	private CipbDao cipbDao;
	private CipgDao cipgDao;
	private BacountryDao bacountryDao;
	private BapawagerecDao bapawagerecDao;
	private BadupeidnDao badupeidnDao;
	private BamarginamtnotifyDao bamarginamtnotifyDao;
	private HashMap<String, String> replaceValue = new HashMap<String, String>();
	private HashMap<String, Object> badaprTotal; // 受理案件的核定總額資料
	private HashMap<String, Object> badaprBefAmt; // 受理案件的原核定金額資料
	// private HashMap<String, Object> badaprPersonal; // 受理案件的個受款人核定總額資料
	private List<Badapr> badaprPersonal;
	private HashMap<String, Object> badaprSup; // 受理案件的補發資料
	private HashMap<String, Object> badaprA118; // 受理案件的核定總額資料 For A118
	private HashMap<String, Object> badaprA122; // 受理案件的核定總額資料 For A122
	private HashMap<String, Object> badaprA139; // 受理案件的核定總額資料 For139
	private CheckAmtCase checkAmtCaseData; // For A019 A020 計算年金金額
	private String chkResult; // 勞工退休金提繳中註記
	private String issuYm; // 頁面所輸入之核定年月
	private String benPayType; // 遺屬使用 當筆列印之遺屬給付方式
	private BigDecimal avgWageA014; // 失能、遺屬年金6個月平均薪資 For A014, A026, A127
	private BigDecimal payeeCountA080; // 受款人數 For A080
	private BigDecimal badaprSupAmtA141; // 補發金額
	private String chkResultA137; // 請領勞工退休金說明
	private Baappbase baappbaseA077; // 第一個受款人之基本資料 For A077, A078, A079
	private static DecimalFormat DF = new DecimalFormat("##,###,###,###,##0"); // 千分位格式化
	private List<Badapr> badaprA045S;
	private String benNationName; // 受益人國籍 For A157
	private BigDecimal minWageA149; // 投保薪資分級表第一級 For A149
	private BigDecimal peopleNumberA143; // 本月核定合格及不合格人數
	private List<Baappbase> baappbaseListA089;
	private String maxPayYmL021;
	private HashMap<String, Object> badaprPayYmL012;
	private HashMap<String, Object> badaprPayYmA164;
	private BigDecimal avgWgL018;
	private HashMap<String, Object> issueDateL023; // 老年差額金通知 - 第一、二次發文日期
	private static final int DATE_20230101 = 20230101;

	/*
	 * for 受益人資料替換 須傳入依照事故人之SEQNO ISSUYM所查詢出來的Badapr chkCode資料
	 */
	public BaReportReplaceUtility(Baappbase baappbase, Badapr badapr, String chkResult, boolean isBalp010) {
		this.baappbase = baappbase;
		this.badapr = badapr;
		this.chkResult = chkResult;
		initialReplaceCode(isBalp010);
	}

	/*
	 * for 受益人資料替換 須傳入依照事故人之SEQNO(非0000) ISSUYM所查詢出來的Badapr chkCode資料 for 核定通知書 &
	 * 遺屬年金受理審核清單裡的核定通知書
	 */
	public BaReportReplaceUtility(Baappbase baappbase, Baappexpand baappexpand, String benPayType, Badapr badapr,
			String chkResult, List<Baappbase> baappbaseList, CheckAmtCase checkAmtCaseData, String issuYm,
			boolean isBalp010, Baappbase benData, List<Baappbase> baappbasePrintList) {
		this.baappbase = baappbase;
		this.baappexpand = baappexpand;
		this.benPayType = benPayType;
		this.badapr = badapr;
		this.chkResult = chkResult;
		this.issuYm = DateUtility.changeChineseYearMonthType(issuYm);
		this.baappbaseList = baappbaseList;
		this.checkAmtCaseData = checkAmtCaseData;
		this.benData = benData;
		this.baappbasePrintList = baappbasePrintList;
		//如果是L案，才給A137初始化設定
		if(baappbase.getApNo().substring(0, 1).equals("L")) {
			BachkfileDao bachkfileDao = (BachkfileDao) SpringHelper.getBeanById("bachkfileDao");
			String chkResultA137 = bachkfileDao.selectForRptReplaceA137(baappbase.getApNo(), baappbase.getSeqNo(),
					baappbase.getIssuYm());
			this.chkResultA137 = chkResultA137;
		}
		initialReplaceCode(isBalp010);
	}

	/*
	 * for 事故人資料替換
	 */
	public BaReportReplaceUtility(Baappbase baappbase, boolean isBalp010, String sA114RptMk, boolean isBalp3t0,
			Baappbase benData) {
		CipbDao cipbDao = (CipbDao) SpringHelper.getBeanById("cipbDao");
		BadupeidnDao badupeidnDao = (BadupeidnDao) SpringHelper.getBeanById("badupeidnDao");
		BamarginamtnotifyDao bamarginamtnotifyDao = (BamarginamtnotifyDao) SpringHelper
				.getBeanById("bamarginamtnotifyDao");
		this.baappbase = baappbase;
		this.benData = benData;

		// for A076 共同具領人員，其它 method 透過參數傳入，但若此 method 也用參數傳入，會與「for 事故人資料替換 for
		// 遺屬年金受理審核清單」method 衝突
		BaappbaseDao baappbaseDao = (BaappbaseDao) SpringHelper.getBeanById("baappbaseDao");
		List<Baappbase> baappbaseList = baappbaseDao.selectMonthlyRpt05ForSurvivorReport(baappbase.getApNo());
		if (baappbaseList.size() != 0)
			this.baappbaseList = baappbaseList;

		BadaprDao badaprDao = (BadaprDao) SpringHelper.getBeanById("badaprDao");
		if (isBalp010) {
			List<Badapr> badaprList = badaprDao.selectDataForReportReplaceA140(baappbase.getApNo(),
					baappbase.getIssuYm());
			if (badaprList.size() > 0) {
				this.badapr = badaprList.get(0);
			}
		} else {
			List<Badapr> badaprList = badaprDao.getMonthlyRpt05ListBy(baappbase.getApNo(), baappbase.getIssuYm());
			if (badaprList.size() > 0) {
				this.badapr = badaprList.get(0);
			}
		}
		BachkfileDao bachkfileDao = (BachkfileDao) SpringHelper.getBeanById("bachkfileDao");
		String chkResult = bachkfileDao.selectForRptReplace(baappbase.getApNo(), baappbase.getSeqNo(),
				baappbase.getIssuYm());
		String chkResultA137 = bachkfileDao.selectForRptReplaceA137(baappbase.getApNo(), baappbase.getSeqNo(),
				baappbase.getIssuYm());
		this.chkResult = chkResult;
		this.chkResultA137 = chkResultA137;

		BaappexpandDao baappexpandDao = (BaappexpandDao) SpringHelper.getBeanById("baappexpandDao");
		this.baappexpand = baappexpandDao.getDisabledReviewRpt01AnnuityPayList(baappbase.getApNo());

		// 補送在學證明通知函 A114_失能眷屬姓名
		BafamilyDao bafamilyDao = (BafamilyDao) SpringHelper.getBeanById("bafamilyDao");
		List<Bafamily> BafamilyDataList = bafamilyDao.selectFamNameForMonthlyRpt29By(baappbase.getApNo(), sA114RptMk);
		this.BafamilyDataList = BafamilyDataList;

		if (isBalp3t0) {

			// String lastIssuYm = DateUtility.calMonth(baappbase.getIssuYm() + "01", -1);
			// String maxPayYm =
			// badaprDao.selectRptReplaceForMaxPayYmL021(baappbase.getApNo(),
			// lastIssuYm.substring(0, 6));
			String maxPayYm = badaprDao.selectRptReplaceForMaxPayYmL021(baappbase.getApNo(), baappbase.getIssuYm());
			this.maxPayYmL021 = maxPayYm;

			badaprPayYmL012 = badaprDao.selectRptReplaceForPayYmL012(baappbase.getApNo(), baappbase.getIssuYm());

			if (StringUtils.isNotBlank(baappbase.getDupeIdnNoMk())) {
				if (baappbase.getDupeIdnNoMk().equals("1")) {
					String newIdnNo = badupeidnDao.selectRptReplaceForNewIdnNoL018(baappbase.getApNo(),
							baappbase.getSeqNo(), baappbase.getEvtIdnNo());
					avgWgL018 = cipbDao.getAvgWg(baappbase.getApNo(), baappbase.getSeqNo(), newIdnNo, "1");
				}
			}else {
				avgWgL018 = cipbDao.getAvgWg(baappbase.getApNo(), baappbase.getSeqNo(), baappbase.getEvtIdnNo(), "2");
			}

			if(StringUtils.equals(sA114RptMk, "001P") || StringUtils.equals(sA114RptMk, "002P") || StringUtils.equals(sA114RptMk, "003P")) {
				issueDateL023 = bamarginamtnotifyDao.selectRptReplaceForIssueDateL023(baappbase.getApNo());
			}

		} else {
			this.maxPayYmL021 = null;
			badaprPayYmL012 = null;
			avgWgL018 = null;
			issueDateL023 = null;
		}

		initialReplaceCode(isBalp010);
	}

	/*
	 * for 事故人資料替換 for 遺屬年金受理審核清單
	 */
	public BaReportReplaceUtility(Baappbase baappbase, List<Baappbase> benDataList, boolean isBalp010) {
		this.baappbase = baappbase;
		this.baappbaseList = benDataList;
		BadaprDao badaprDao = (BadaprDao) SpringHelper.getBeanById("badaprDao");
		if (isBalp010) {
			List<Badapr> badaprList = badaprDao.selectDataForReportReplace(baappbase.getApNo(), baappbase.getIssuYm());
			if (badaprList.size() > 0) {
				this.badapr = badaprList.get(0);
			}
		} else {
			List<Badapr> badaprList = badaprDao.getMonthlyRpt05ListBy(baappbase.getApNo(), baappbase.getIssuYm());
			if (badaprList.size() > 0) {
				this.badapr = badaprList.get(0);
			}
		}
		BachkfileDao bachkfileDao = (BachkfileDao) SpringHelper.getBeanById("bachkfileDao");
		String chkResult = bachkfileDao.selectForRptReplace(baappbase.getApNo(), baappbase.getSeqNo(),
				baappbase.getIssuYm());
		String chkResultA137 = bachkfileDao.selectForRptReplaceA137(baappbase.getApNo(), baappbase.getSeqNo(),
				baappbase.getIssuYm());
		this.chkResult = chkResult;
		this.chkResultA137 = chkResultA137;
		initialReplaceCode(isBalp010);
	}

	/*
	 * for 複檢費用資料替換
	 */
	public BaReportReplaceUtility(Babcml7 babcml7, boolean isBalp010) {

		this.babcml7 = babcml7;
		initialReplaceCode(isBalp010);
	}

	public void initialReplaceCode(boolean isBalp010) {
		BadaprDao badaprDao = (BadaprDao) SpringHelper.getBeanById("badaprDao");
		CipgDao cipgDao = (CipgDao) SpringHelper.getBeanById("cipgDao");
		BaappbaseDao baappbaseDao = (BaappbaseDao) SpringHelper.getBeanById("baappbaseDao");
		BafamilyDao bafamilyDao = (BafamilyDao) SpringHelper.getBeanById("bafamilyDao");
		BacountryDao bacountryDao = (BacountryDao) SpringHelper.getBeanById("bacountryDao");
		BapawagerecDao bapawagerecDao = (BapawagerecDao) SpringHelper.getBeanById("bapawagerecDao");

		String eqType = "";
		if (baappbase.getApNo().substring(0, 1).equals("S"))
			eqType = "!="; // 不等於
		else
			eqType = "=="; // 等於

		if (isBalp010) {
			badaprTotal = badaprDao.selectReportReplaceDataTotalForBalp010(baappbase.getApNo(), baappbase.getIssuYm());
			badaprPersonal = badaprDao.selectReportReplaceDataPersonalForBalp010(baappbase.getApNo(),
					baappbase.getIssuYm(), baappbase.getSeqNo());
			badaprA139 = badaprDao.selectReportReplaceDataA139ForBalp010(baappbase.getApNo(), baappbase.getIssuYm());
			if (StringUtils.isBlank(issuYm)) {
				badaprA118 = badaprDao.selectReportReplaceDataTotalForA118(baappbase.getApNo(), baappbase.getIssuYm(),
						eqType);
				badaprA122 = badaprDao.selectReportReplaceDataTotalForA122(baappbase.getApNo(), baappbase.getIssuYm(),
						eqType);
			} else {
				badaprA118 = badaprDao.selectReportReplaceDataTotalForA118(baappbase.getApNo(), issuYm, eqType);
				badaprA122 = badaprDao.selectReportReplaceDataTotalForA122(baappbase.getApNo(), issuYm, eqType);
			}
		} else {
			badaprTotal = badaprDao.selectReportReplaceDataTotal(baappbase.getApNo(), null);
			badaprPersonal = badaprDao.selectReportReplaceDataPersonal(baappbase.getApNo(), null, baappbase.getSeqNo());
			badaprA139 = badaprDao.selectReportReplaceDataForA139(baappbase.getApNo(), null);
			if (StringUtils.isBlank(issuYm)) {
				badaprA118 = badaprDao.selectReportReplaceDataTotalForA118(baappbase.getApNo(), baappbase.getIssuYm(),
						eqType);
				badaprA122 = badaprDao.selectReportReplaceDataTotalForA122(baappbase.getApNo(), baappbase.getIssuYm(),
						eqType);
			} else {
				badaprA118 = badaprDao.selectReportReplaceDataTotalForA118(baappbase.getApNo(), issuYm, eqType);
				badaprA122 = badaprDao.selectReportReplaceDataTotalForA122(baappbase.getApNo(), issuYm, eqType);
			}
		}

		List<Badapr> badaprList = badaprDao.selectReportReplaceDataForA133(baappbase.getApNo(), baappbase.getIssuYm());
		if (badaprList.size() > 0) {
			this.badaprA133 = badaprList.get(0);
		}

		List<Baappbase> baappbaseA077List = baappbaseDao.selectReportReplaceDataForA077(baappbase.getApNo());
		if (baappbaseA077List.size() > 0) {
			this.baappbaseA077 = baappbaseA077List.get(0);
		}

		if (baappbase.getApNo().substring(0, 1).equals("S")) {
			this.badaprA045S = badaprDao.selectReportReplaceDataForA045S(baappbase.getApNo(), baappbase.getIssuYm());

			// 受益人國籍 (A157)
			if (benData != null) {
				this.benNationName = bacountryDao.selectCNameData(benData.getBenNationCode());
			}

			// 本月核定合格及不合格人數 (A143)
			peopleNumberA143 = badaprDao.selectReportReplaceDataForPeopleNumberA143(baappbase.getApNo(),
					baappbase.getIssuYm(), baappbase.getPayYm());

			baappbaseListA089 = baappbaseDao.selectReportReplaceDataForA089(baappbase.getApNo());

			// 實付金額不為 0 之最小給付年月 (A164)
			badaprPayYmA164 = badaprDao.selectRptReplaceForPayYmA164(baappbase.getApNo(), baappbase.getIssuYm());

			// 不合格說明 (A150)
			baappbasePrintListA150 = new ArrayList<Baappbase>();
			baappbasePrintListA150 = baappbaseDao.selectMonthlyRpt05PrintCase2DataA150(baappbase.getApNo(),
					baappbase.getIssuYm());

		}

		badaprBefAmt = badaprDao.selectReportReplaceDataForBadaprBefAmt(baappbase.getApNo(), baappbase.getIssuYm());
		badaprSup = badaprDao.selectReportReplaceDataForSupData(baappbase.getApNo(), baappbase.getIssuYm());
		avgWageA014 = cipgDao.getAvgWageForKS(baappbase.getApNo(), "0000", baappbase.getEvtIdnNo());
		badaprSupAmtA141 = badaprDao.selectReportReplaceDataForBadaprSupAmt(baappbase.getApNo(), baappbase.getIssuYm());
		if (badaprTotal != null) {
			minWageA149 = bapawagerecDao.getMinWage(badaprTotal.get("MAXPAYYM").toString());
		} else {
			minWageA149 = null;
		}

		if (baappbase.getApNo().substring(0, 1).equals("K"))
			payeeCountA080 = bafamilyDao.getPayeeCount(baappbase.getApNo());
		else
			payeeCountA080 = baappbaseDao.getPayeeCount(baappbase.getApNo());

		A001();
		A002();
		A003();
		A004();
		A005();
		A006();
		A007();
		A008();
		A009();
		A010();
		A011();
		A012();
		A013();
		A014();
		A015();
		A016();
		A017();
		A019();
		A020();
		A026();
		A028();
		A029();
		A030();
		A031();
		A035();
		A036();
		A040();
		A041();
		A042();
		A043();
		A044();
		A045();
		A046();
		A047();
		A048();
		A049();
		A052();
		A053();
		A054();
		A055();
		A059();
		A060();
		A061();
		A062();
		A063();
		A064();
		A065();
		A066();
		A067();
		A068();
		A069();
		A070();
		A071();
		A072();
		A073();
		A074();
		A075();
		A076();
		A077();
		A078();
		A079();
		A080();
		A088();
		A089();
		A090();
		A092();
		A093();
		A094();
		A095();
		A096();
		A097();
		A098();
		A099();
		A100();
		A101();
		A102();
		A103();
		A104();
		A105();
		A106();
		A107();
		A108();
		A110();
		A111();
		A112();
		A113();
		A114();
		A116();
		A117();
		A118();
		A119();
		A120();
		A121();
		A122();
		A123();
		A124();
		A125();
		A126();
		A127();
		A128();
		A129();
		A130();
		A131();
		A132();
		A133();
		A134();
		A135();
		A136();
		A137();
		A138();
		A139();
		A140();
		A141();
		A142();
		A143();
		A144();
		A145();
		A146();
		A147();
		A148();
		A149();
		A151();
		A152();
		A153();
		A154();
		A155();
		A156();
		A157();
		A158();
		A159();
		A160();
		A161();
		A162();
		A163();
		A164();
		A165();
		A167();
		A168();
		A150();//A150會用到A003、A164、A165、A167、A168、A172、A173、A184
		A185();

		if (baappbase.getApNo().substring(0, 1).equals("K")) {
			K001();
		}

		if (baappbase.getApNo().substring(0, 1).equals("L")) {
			L011();
			L012();
			L013();
			L014();
			L015();
			L016();
			L017();
			L018();
			L019();
			L020();
			L021();
			L022();
			L023();
			L024();
		}
	}

	public void initialReplaceCode(Babcml7 babcml7, boolean isBalp010) {

		A004();
		A005();
		A006();
		A007();
		A060();
		A100();
		A101();
		A102();
	}

	/**
	 * 將傳入的字串替換
	 *
	 * @param str 字串
	 * @return 替換後字串
	 */
	public String replace(String str) {
		Set<String> keySet = replaceValue.keySet();
		for (String key : keySet) {
			String replaceStr = StringUtils.defaultString(replaceValue.get(key));
			str = str.replaceAll("＜" + key + "＞", replaceStr);
			str = str.replaceAll("<" + key + ">", replaceStr);
		}
		return str;
	}

	/**
	 * 將傳入的List字串替換
	 *
	 * @param List字串
	 * @return 替換後List字串
	 */
	public List<String> replace(List<String> strList) {
		List<String> replaceList = new ArrayList<String>();
		for (String str : strList) {
			replaceList.add(replace(str));
		}
		return replaceList;
	}

	public String test() {
		String str = "三、台端所請老年年金給付案（最後投保單位：00）<A094>，經本局審查符合規定，按台端保險年資00年又00個月<A010>，擇優發給加保期間最高60個月之平均月投保薪資000<A012>元之0.775％計算，加計3,000元＜A015＞，計000＜A016＞元，另台端自000年00月00日符合老年年金給付條件，延後000年00月至000年00月00日申請，應依前述金額增給00.00%，核計0000元。<A017>本局將自000年00月＜A040＞起按月發給，本次一併發給000年00月起至000年00月<A041>之老年年金給付金額，合計000<A046>元，並送交金融機構轉帳匯入申請書所載指定之帳號（如未入帳，請向02-23961266轉2212保險收支科洽詢。如有帳號錯誤，本局處理方式一律以文書通知）<A060>。又台端業已離職，惟尚未申報退保，本局逕自離職日起予以退保。<A096>另如有提繳勞工退休金者，一併停止提繳。<A055>台端未於國內設有戶籍，每年須重新檢送身分及相關證明文件至局查核，屆時未檢送應暫時停止發給，併予敘明。<A097>";
		return replace(str);
	}

	// 「受理編號」欄 BAAPPBASE. APNO
	public void A001() {
		if (baappbase != null) {
			replaceValue.put(ConstantKey.A001, baappbase.getApNo());
		} else {
			replaceValue.put(ConstantKey.A001, "");
		}

	}

	// 「申請日期」欄BAAPPBASE. APPDATE
	public void A002() {
		if (baappbase != null) {
			replaceValue.put(ConstantKey.A002, DateUtility.formatChineseDateTimeString(baappbase.getAppDate(), true));
		} else {
			replaceValue.put(ConstantKey.A002, "");
		}
	}

	// 「事故者姓名」欄 BAAPPBASE. EVTNAME
	public void A003() {
		if (baappbase != null) {
			replaceValue.put(ConstantKey.A003, baappbase.getEvtName());
		} else {
			replaceValue.put(ConstantKey.A003, "");
		}
	}

	// 事故者身分證號」欄 BAAPPBASE. EVTIDNNO 第三至七碼以*代替
	public void A004() {
		if (baappbase != null && baappbase.getEvtIdnNo() != null) {
			String idn = baappbase.getEvtIdnNo();
			if (idn.length() >= 10) {
				// replaceValue.put(ConstantKey.A004, idn.substring(0, 2) + "*****" +
				// idn.substring(7, idn.length()));
				replaceValue.put(ConstantKey.A004, idn.substring(0, 6) + "****");
			} else {
				replaceValue.put(ConstantKey.A004, idn.substring(0, 6) + "****");
			}
		} else if (babcml7 != null) {
			if (babcml7.getEvtIdnNo().length() >= 10) {
				// replaceValue.put(ConstantKey.A004, babcml7.getEvtIdnNo().substring(0, 2) +
				// "*****" + babcml7.getEvtIdnNo().substring(7,
				// babcml7.getEvtIdnNo().length()));
				replaceValue.put(ConstantKey.A004, babcml7.getEvtIdnNo().substring(0, 6) + "****");
			} else {
				replaceValue.put(ConstantKey.A004, babcml7.getEvtIdnNo().substring(0, 6) + "****");
			}
		} else {
			replaceValue.put(ConstantKey.A004, "");
		}
	}

	// 「事故者出生日期」欄 BAAPPBASE. EVTBRDATE 格式：XXX年XX月XX日
	public void A005() {
		if (baappbase != null) {
			replaceValue.put(ConstantKey.A005, DateUtility.formatChineseDateTimeString(baappbase.getEvtBrDate(), true));
		} else if (babcml7 != null) {
			replaceValue.put(ConstantKey.A005, DateUtility.formatChineseDateTimeString(babcml7.getEvtBrDate(), true));
		} else {
			replaceValue.put(ConstantKey.A005, "");
		}
	}

	// 老年、失能年金事故者之「死亡日期」欄 BAAPPBASE. EVTDIEDATE
	public void A006() {
		if (baappbase != null) {
			replaceValue.put(ConstantKey.A006,
					DateUtility.formatChineseDateTimeString(baappbase.getEvtDieDate(), true));
		} else {
			replaceValue.put(ConstantKey.A006, "");
		}
	}

	// 「核付日期」欄 IF BADAPR. APLPAYMK=3 Then BADAPR. APLPAYDATE 格式：XXX年XX月XX日
	public void A007() {
		if (badapr != null && "3".equalsIgnoreCase(badapr.getAplpayMk())) {
			replaceValue.put(ConstantKey.A007, DateUtility.formatChineseDateTimeString(badapr.getAplpayDate(), true));
		} else if (babcml7 != null) {
			replaceValue.put(ConstantKey.A007, "000年00月00日");
		} else {
			replaceValue.put(ConstantKey.A007, "000年00月00日");
		}
	}

	// 逕予退保日 失能年金之「診斷失能日期」欄 BAAPPBASE.EVTJOBDATE
	public void A008() {
		if (baappbase != null) {
			replaceValue.put(ConstantKey.A008,
					DateUtility.formatChineseDateTimeString(baappbase.getEvtJobDate(), true));
		} else {
			replaceValue.put(ConstantKey.A008, "");
		}
	}

	// 失能、遺屬年金之「普通/職業」 「傷病分類」欄：「1」或「2」顯示「職業災害」；「3」或「4」顯示「普通事故」
	public void A009() {
		if (baappexpand != null
				&& ("1".equalsIgnoreCase(baappexpand.getEvTyp()) || "2".equalsIgnoreCase(baappexpand.getEvTyp()))) {
			replaceValue.put(ConstantKey.A009, "職業災害");
		} else if (baappexpand != null
				&& ("3".equalsIgnoreCase(baappexpand.getEvTyp()) || "4".equalsIgnoreCase(baappexpand.getEvTyp()))) {
			replaceValue.put(ConstantKey.A009, "普通事故");
		} else {
			replaceValue.put(ConstantKey.A009, "");
		}
	}

	// 各項年金之「投保年資」欄 BADAPR. NITRMY(年) BADAPR. NITRMY(月) 格式：XXX年又XX個月
	public void A010() {
		if (badapr != null) {
			int year = 0, month = 0;
			if (badapr.getNitrmY() != null)
				year = badapr.getNitrmY().intValue();
			if (badapr.getNitrmM() != null)
				month = badapr.getNitrmM().intValue();
			replaceValue.put(ConstantKey.A010, year + "年又" + month + "個月");
		} else {
			replaceValue.put(ConstantKey.A010, "000年又00個月");
		}
	}

	// 各項年金之「實發年資」欄 BADAPR. APLPAYSENIY (年) BADAPR. APLPAYSENIM (月)
	public void A011() {
		if (badapr != null) {
			int year = 0, month = 0;
			if (badapr.getAplPaySeniY() != null)
				year = badapr.getAplPaySeniY().intValue();
			if (badapr.getAplPaySeniM() != null)
				month = badapr.getAplPaySeniM().intValue();
			replaceValue.put(ConstantKey.A011, year + "年又" + month + "個月");
		} else {
			replaceValue.put(ConstantKey.A011, "000年00個月");
		}
	}

	// 老年年金：「平均薪資」欄 BADAPR. INSAVGAMT
	public void A012() {
		if (badapr != null) {
			String insAvgAmt = "0";
			if (badapr.getInsAvgAmt() != null)
				insAvgAmt = DF.format(badapr.getInsAvgAmt().intValue());
			replaceValue.put(ConstantKey.A012, insAvgAmt);
		} else {
			replaceValue.put(ConstantKey.A012, "0");
		}
	}

	public void A013() {
		// TODO
		replaceValue.put(ConstantKey.A013, "");
	}

	// 失能、遺屬年金：6個月平均薪資（修改時需同時確認A026、A127）
	public void A014() {
		if (baappexpand != null
				&& ("1".equalsIgnoreCase(baappexpand.getEvTyp()) || "2".equalsIgnoreCase(baappexpand.getEvTyp()))) {
			String insAvgAmt = "0";

			// BigDecimal avgWage = cipgDao.getAvgWageForKS(baappbase.getApNo(), "0000");

			if (avgWageA014 != null && avgWageA014.intValue() > 0) {
				insAvgAmt = DF.format(avgWageA014.intValue());
				replaceValue.put(ConstantKey.A014, insAvgAmt);
			} else {
				replaceValue.put(ConstantKey.A014, "0");
			}
		} else {
			replaceValue.put(ConstantKey.A014, "0");
		}
	}

	// 老年年金之「計算式」欄： 1.「1」：顯示”之0.775％計算，加計3,000元”。 2.「2」：顯示”之1.55％”計算。
	public void A015() {
		if (badapr != null && "1".equalsIgnoreCase(badapr.getOldab())) {
			replaceValue.put(ConstantKey.A015, "0.775％計算，加計3,000元");
		} else if (badapr != null && "2".equalsIgnoreCase(badapr.getOldab())) {
			replaceValue.put(ConstantKey.A015, "1.55％計算");
		} else {
			replaceValue.put(ConstantKey.A015, "0");
		}

	}

	// If BADAPR. OLDAB =1 tehn BADAPR. OLDAAMT Else BADAPR. OLDBAMT
	public void A016() {
		if (badapr != null && "1".equalsIgnoreCase(badapr.getOldab())) {
			String oldaAmt = "0";
			if (badapr.getOldaAmt() != null)
				oldaAmt = DF.format(badapr.getOldaAmt().intValue());
			replaceValue.put(ConstantKey.A016, oldaAmt);
		} else if (badapr != null && "2".equalsIgnoreCase(badapr.getOldab())) {
			String oldbAmt = "0";
			if (badapr.getOldbAmt() != null) {
				oldbAmt = DF.format(badapr.getOldbAmt().intValue());
			}
			replaceValue.put(ConstantKey.A016, oldbAmt);
		} else {
			replaceValue.put(ConstantKey.A016, "0");
		}
	}

	// 判斷「展延比率」或「減額比率」欄是否有值：
	// 1.均無值：顯示”。”。
	// 2.「展延比率」有值：另增給00年00個月（帶入展延期間）計00.00%（帶入展延比率）之展延老年年金給付，每月實際核給老年年金給付金額為0000元（帶入核定金額）
	// 3.「減額比率」有值：又台端未符合老年年金給付請領年齡，提前至00年00月00日（帶入申請日期）申請，應減給00年00個月（帶入減額期間）計00.00%（帶入減額比率）之老年年金給付，每月實際核給老年年金給付金額為0000元（帶入核定金額）
	// 符合日期= BAAPPBASE. EVTELIGIBLEDATE 展延/減額期間(迄)=BADAPR.OLDRATEEDATE 申請日期=
	// BAAPPBASE.APPDATE 展延/減額比率=BADAPR.OLDRATE 核定金額=BADAPR.ISSUEAMT
	public void A017() {
		int issueAmt = 0;
		if (badapr == null || badapr.getOldRate().doubleValue() == 0) {
			replaceValue.put(ConstantKey.A017, "。");
		} else if (baappbase != null && "1".equalsIgnoreCase(baappbase.getApItem())) {
			int totalMonth = DateUtility.wholeMonthsBetween(badapr.getOldRateSdate() + "01",
					badapr.getOldRateEdate() + "01");
			String year = String.valueOf(totalMonth / 12);
			String month = String.valueOf(totalMonth % 12);
			if (badapr.getBefIssueAmt() != null) {
				issueAmt = badapr.getBefIssueAmt().intValue();
			}
			String str = "";
			if (NumberUtils.toInt(year) >= 5) {
				str = "，另增給展延期間5 年 0 個月計" + badapr.getOldRate() + "%之展延老年年金給付，每月核給老年年金給付" + DF.format(issueAmt) + "元。";
			} else {
				str = "，另增給展延期間" + year + " 年 " + month + "個月計" + badapr.getOldRate() + "%之展延老年年金給付，每月核給老年年金給付"
						+ DF.format(issueAmt) + "元。";
			}
			replaceValue.put(ConstantKey.A017, str);
		} else if (baappbase != null && "2".equalsIgnoreCase(baappbase.getApItem())) {
			int totalMonth = DateUtility.wholeMonthsBetween(badapr.getOldRateSdate() + "01",
					badapr.getOldRateEdate() + "01");
			String year = String.valueOf(totalMonth / 12);
			String month = String.valueOf(totalMonth % 12);
			/*
			 * if (badapr.getIssueAmt() != null) { issueAmt =
			 * badapr.getIssueAmt().intValue(); }
			 */
			if (badapr.getBefIssueAmt() != null) {
				issueAmt = badapr.getBefIssueAmt().intValue();
			}
			String str = "";
			if (NumberUtils.toInt(baappbase.getEvtDate()) <= NumberUtils.toInt(baappbase.getAppDate())) {
				str = "，又因尚未符合老年年金給付法定請領年齡，提前" + year + " 年 " + month + "個月於"
						+ DateUtility.formatChineseDateTimeString(baappbase.getAppDate(), true) + "申請，應減給"
						+ StringUtils.removeStart(badapr.getOldRate().toString(), "-") + "%之老年年金給付，每月核給老年年金給付"
						+ DF.format(issueAmt) + "元。";
			} else {
				str = "，又因尚未符合老年年金給付法定請領年齡，於" + DateUtility.formatChineseDateTimeString(baappbase.getEvtDate(), true)
						+ "離職退保，應減給" + year + " 年 " + month + "個月，計"
						+ StringUtils.removeStart(badapr.getOldRate().toString(), "-") + "%之老年年金給付，每月核給老年年金給付"
						+ DF.format(issueAmt) + "元。";
			}
			replaceValue.put(ConstantKey.A017, str);
		} else {
			replaceValue.put(ConstantKey.A017, "");
		}
	}

	// 老年年金金額：參考遺屬編審程式(BaCmExtend AccountIssueAmt line
	// 328)申請項目8之遺屬年金核定金額計算方式，以A110所抓取之核定檔，作為計算依據計算老年年金金額帶入。
	public void A019() {
		BabasicamtDao babasicamtDao = (BabasicamtDao) SpringHelper.getBeanById("babasicamtDao");
		BadaprDao badaprDao = (BadaprDao) SpringHelper.getBeanById("badaprDao");
		BacpirecDao bacpirecDao = (BacpirecDao) SpringHelper.getBeanById("bacpirecDao");

		BigDecimal issueAmt = BigDecimal.ZERO;
		if (StringUtils.isNotBlank(baappbase.getDabApNo()) && StringUtils.startsWith(baappbase.getDabApNo(), "L")
				&& checkAmtCaseData != null) {
			List<Badapr> badaprApItem8List = badaprDao.selectDabAnnuDataBy(baappbase.getDabApNo(),
					StringUtils.substring(baappbase.getEvtJobDate(), 0, 6));

			if (badaprApItem8List.size() > 0) {
				Badapr badaprApItem8 = badaprApItem8List.get(0);
				if (badaprApItem8 != null) {
					// 給付年月 < 最小施行日期年月 (表示核定金額沒有因替代率參數更改直接用老年的核定金額即可)
					if (NumberUtils.toInt(baappbase.getPayYm()) < NumberUtils.toInt(checkAmtCaseData.getMinEffYm())) {
						issueAmt = badaprApItem8.getBefIssueAmt();
						// issueAmt = badaprApItem8.getBefIssueAmt().divide(BigDecimal.valueOf(2), 0,
						// BigDecimal.ROUND_HALF_UP);
					}
					// 給付年月 >= 最小施行日期年月 (表示核定金額有因替代率參數更改 要用重算老年的核定金額)
					else {
						BigDecimal nitrMy = trimToZero(badaprApItem8.getNitrmY());// 投保年資-年數
						BigDecimal nitrMm = trimToZero(badaprApItem8.getNitrmM());// 投保年資-月數
						BigDecimal seni = nitrMy.add(
								nitrMm.divide(BigDecimal.valueOf(12), ConstantKey.SCALE_SENI, BigDecimal.ROUND_HALF_UP))
								.setScale(2, BigDecimal.ROUND_HALF_UP);// 勞保年資
						List<Babasicamt> babasicamtList_old = babasicamtDao.selectBasicAmtBy("L");
						SurvivorBasicAmtCase oldAgeBasicAmtCase = new SurvivorBasicAmtCase(babasicamtList_old);
						BigDecimal type1Amt = checkAmtCaseData
								.getType1Amt(baappbase.getPayYm(), badaprApItem8.getInsAvgAmt(), seni)
								.add(oldAgeBasicAmtCase.getBasicAmt(baappbase.getPayYm()));
						BigDecimal type2Amt = checkAmtCaseData.getType2Amt(baappbase.getPayYm(),
								badaprApItem8.getInsAvgAmt(), seni);
						if (type1Amt.compareTo(type2Amt) >= 0) {
							issueAmt = type1Amt;
						} else {
							issueAmt = type2Amt;
						}
						// 展延年金計算
						BigDecimal OldRate = BigDecimal.ONE;
						if (StringUtils.equals("1", badaprApItem8.getApItem())
								|| StringUtils.equals("2", badaprApItem8.getApItem())
								|| StringUtils.equals("6", badaprApItem8.getApItem())) {
							if (badaprApItem8.getOldRate() != null) {
								// 展延比率 = (1+ OldRate/100)
								OldRate = OldRate.add(badaprApItem8.getOldRate()
										.divide(ConstantKey.BIGDECIMAL_CONSTANT_100, 4, BigDecimal.ROUND_HALF_UP));
							}
						}
						// 老年年金金額
						issueAmt = issueAmt.multiply(OldRate).setScale(0, BigDecimal.ROUND_HALF_UP);
						// 遺屬年金只能領一半
						// issueAmt = issueAmt.divide(BigDecimal.valueOf(2), 0,
						// BigDecimal.ROUND_HALF_UP);

						// 加計物價指數 (20140808)
						/*
						if (badaprApItem8.getCpiRate() != null) {
							BigDecimal cpi = BigDecimal.ONE
									.add(badaprApItem8.getCpiRate().divide(ConstantKey.BIGDECIMAL_CONSTANT_100));
							issueAmt = issueAmt.multiply(cpi).setScale(0, BigDecimal.ROUND_HALF_UP);
						}
						*/
						if (StringUtils.isNotBlank(badaprApItem8.getAppDate()) && StringUtils.isNotBlank(badaprApItem8.getEvtDieDate())) {
							List<BigDecimal> cpiRateList =
									bacpirecDao.selectCpiRateByAppDateAndEvtDieDate(badaprApItem8.getAppDate(), badaprApItem8.getEvtDieDate(),badaprApItem8.getPayYmBegin());
							for (BigDecimal cpiRate : cpiRateList) {
								issueAmt = issueAmt.multiply(cpiRate).setScale(0, BigDecimal.ROUND_HALF_UP);
							}
						}
					}
				} else { // if (badaprApItem8 != null)
					issueAmt = BigDecimal.ZERO;
				} // if (badaprApItem8 != null)

			} else { // if (badaprApItem8List.size() > 0)
				issueAmt = BigDecimal.ZERO;
			} // if (badaprApItem8List.size() > 0)

		}
		replaceValue.put(ConstantKey.A019, DF.format(issueAmt));

	}

	// 失能年金金額：請參考遺屬編審程式(BaCmExtend AccountIssueAmt line
	// 289)申請項目7之遺屬年金核定金額計算方式，以A110所抓取之核定檔，作為計算依據計算失能年金金額帶入。
	public void A020() {
		BadaprDao badaprDao = (BadaprDao) SpringHelper.getBeanById("badaprDao");
		BigDecimal issueAmt = BigDecimal.ZERO;
		if (StringUtils.isNotBlank(baappbase.getDabApNo()) && StringUtils.startsWith(baappbase.getDabApNo(), "K")
				&& checkAmtCaseData != null) {
			List<Badapr> badaprApItem7List = badaprDao.selectDabAnnuDataBy(baappbase.getDabApNo(),
					StringUtils.substring(baappbase.getEvtJobDate(), 0, 6));
			if (badaprApItem7List.size() > 0) {
				Badapr badaprApItem7 = badaprApItem7List.get(0);
				BigDecimal nitrMy = trimToZero(badaprApItem7.getNitrmY());// 投保年資-年數
				BigDecimal nitrMm = trimToZero(badaprApItem7.getNitrmM());// 投保年資-月數
				BigDecimal seni = nitrMy
						.add(nitrMm.divide(BigDecimal.valueOf(12), ConstantKey.SCALE_SENI, BigDecimal.ROUND_HALF_UP))
						.setScale(2, BigDecimal.ROUND_HALF_UP);// 勞保年資
				issueAmt = checkAmtCaseData.getType2Amt(baappbase.getPayYm(), badaprApItem7.getInsAvgAmt(), seni)
						.divide(BigDecimal.valueOf(2), 0, BigDecimal.ROUND_HALF_UP);
			} else {
				issueAmt = BigDecimal.ZERO;
			}
			replaceValue.put(ConstantKey.A020, DF.format(issueAmt));
		} else {
			replaceValue.put(ConstantKey.A020, DF.format(BigDecimal.ZERO));
		}
	}

	// 喪葬津貼金額 <A014>*5（修改時需同時確認A014、A127）
	public void A026() {
		String insAvgAmt = "0";

		if (baappexpand != null && ("1".equalsIgnoreCase(baappexpand.getEvAppTyp())
				|| "2".equalsIgnoreCase(baappexpand.getEvAppTyp()))) {
			if (avgWageA014 != null && avgWageA014.intValue() > 0) {
				insAvgAmt = DF.format(avgWageA014.intValue() * 5);
				replaceValue.put(ConstantKey.A026, insAvgAmt);
			} else {
				replaceValue.put(ConstantKey.A026, "0");
			}
		} else {
			replaceValue.put(ConstantKey.A026, "0");
		}
	}

	// 「受委託人姓名」欄 BAAPPBASE. ASSIGNNAME
	public void A028() {
		if (baappbase != null) {
			replaceValue.put(ConstantKey.A028, StringUtils.trimToEmpty(baappbase.getAssignName()));
		} else {
			replaceValue.put(ConstantKey.A028, "");
		}
	}

	// 「受委託人身分證號」欄 BAAPPBASE. ASSIGNIDNNO
	public void A029() {
		if (baappbase != null) {
			replaceValue.put(ConstantKey.A029, StringUtils.trimToEmpty(baappbase.getAssignIdnNo()));
		} else {
			replaceValue.put(ConstantKey.A029, "");
		}
	}

	// 「受委託人出生日期」欄 BAAPPBASE. ASSIGNBRDATE
	public void A030() {
		if (baappbase != null) {
			replaceValue.put(ConstantKey.A030, StringUtils.trimToEmpty(baappbase.getAssignBrDate()));
		} else {
			replaceValue.put(ConstantKey.A030, "");
		}
	}

	// 老年、失能年金之「國保繳費總年資」 BADAPR.VALSENIY (年) BADAPR.VALSENIM (月)
	public void A031() {
		if (badapr != null) {
			int year = 0, month = 0;
			if (badapr.getValseniY() != null) {
				year = badapr.getValseniY().intValue();
			}
			if (badapr.getValseniM() != null) {
				month = badapr.getValseniM().intValue();
			}
			replaceValue.put(ConstantKey.A031, year + " 年 " + month + " 月");
		} else {
			replaceValue.put(ConstantKey.A031, "");
		}
	}

	// 失能項目 失能年金之「失能項目」欄 BAAPPEXPAND.criInJdp1 ～ criInJdp10
	public void A035() {
		if (baappexpand != null) {
			replaceValue.put(ConstantKey.A035, baappexpand.getCriInJdpStr());
		} else {
			replaceValue.put(ConstantKey.A035, "");
		}
	}

	// 失能年金之「失能等級」欄 BAAPPEXPAND.CRIINJCL1 ～ CRIINJCL3
	public void A036() {
		if (baappexpand != null) {
			replaceValue.put(ConstantKey.A036, baappexpand.getCriInJclStr());
		} else {
			replaceValue.put(ConstantKey.A036, "");
		}
	}

	// 帶入核定年月之最小給付年月 BADAPR. PAYYM 格式：XXX年XX月 (第四段SQL)
	public void A040() {
		if (badaprTotal != null) {
			String minPayYm = DateUtility.changeDateType(badaprTotal.get("MINPAYYM") + "01");
			replaceValue.put(ConstantKey.A040, minPayYm.substring(0, 3) + " 年 " + minPayYm.substring(3, 5) + " 月");
		} else {
			replaceValue.put(ConstantKey.A040, "000年00月");
		}
	}

	// 同一核定年月之給付年月起迄，顯示”000年00月至000年00月期間”（ 不含補發部分金額之給付年月） BADAPR. PAYYM~ BADAPR.
	// PAYYM (第四段SQL)
	public void A041() {
		if (badaprTotal != null) {
			String minPayYm = DateUtility.changeDateType(badaprTotal.get("MINPAYYM") + "01");
			String maxPayYm = DateUtility.changeDateType(badaprTotal.get("MANPAYYM") + "01");

			if (maxPayYm.equals(minPayYm) || maxPayYm == null || maxPayYm.equals("")) {
				replaceValue.put(ConstantKey.A041, minPayYm.substring(0, 3) + " 年 " + minPayYm.substring(3, 5) + " 月");
			} else {
				replaceValue.put(ConstantKey.A041, minPayYm.substring(0, 3) + " 年 " + minPayYm.substring(3, 5) + " 月至"
						+ maxPayYm.substring(0, 3) + " 年 " + maxPayYm.substring(3, 5) + " 月");
			}
		} else {
			replaceValue.put(ConstantKey.A041, "000年00月至000年00月");
		}
	}

	public void A042() {
		if (badaprSup != null) {
			String minSupPayYm = DateUtility.changeDateType((String) badaprSup.get("MINSUPPAYYM") + "01");
			String maxSupPayYm = DateUtility.changeDateType((String) badaprSup.get("MANSUPPAYYM") + "01");
			replaceValue.put(ConstantKey.A042, minSupPayYm.substring(0, 3) + " 年 " + minSupPayYm.substring(3, 5) + " 月至"
					+ maxSupPayYm.substring(0, 3) + " 年 " + maxSupPayYm.substring(3, 5) + " 月止");
		} else {
			replaceValue.put(ConstantKey.A042, "");
		}
	}

	public void A043() {
		// TODO
		replaceValue.put(ConstantKey.A043, "");
	}

	// 核定年月最小給付年月之核定金額 BADAPR. ISSUEAMT
	public void A044() {
		if (badapr != null && badapr.getIssueAmt() != null) {

			replaceValue.put(ConstantKey.A044, DF.format(badapr.getIssueAmt()));
		} else if (badapr != null) {
			replaceValue.put(ConstantKey.A044, "0");
		} else {
			replaceValue.put(ConstantKey.A044, "0");
		}
	}

	// 核定年月最小給付年月之實付金額 BADAPR. APLPAYAMT
	public void A045() {

		if (badaprA045S != null && badaprA045S.size() > 0 && badaprA045S.get(0).getAplpayAmt() != null) {
			replaceValue.put(ConstantKey.A045, DF.format(badaprA045S.get(0).getAplpayAmt()));
		} else {
			if (badapr != null && badapr.getAplpayAmt() != null) {
				replaceValue.put(ConstantKey.A045, DF.format(badapr.getAplpayAmt()));
			} else if (badapr != null) {
				replaceValue.put(ConstantKey.A045, "0");
			} else {
				replaceValue.put(ConstantKey.A045, "0");
			}
		}
	}

	// 同一核定年月之各給付年月總核定金額加總 SUM(BADAPR. BEFISSUEAMT) (第四段SQL)
	public void A046() {
		if (badaprTotal != null) {
			int sumIssueAmt = 0;
			if (badaprTotal.get("SUMBEFISSUEAMT") != null) {
				sumIssueAmt = ((BigDecimal) badaprTotal.get("SUMBEFISSUEAMT")).intValue();
			}
			replaceValue.put(ConstantKey.A046, DF.format(sumIssueAmt));
		} else {
			replaceValue.put(ConstantKey.A046, "0");
		}
	}

	// 同一核定年月之各給付年月實付金額加總 SUM(BADAPR. APLPAYAMT) (第四段SQL)
	public void A047() {
		if (badaprTotal != null) {
			int aumAplpayAmt = 0;
			if (badaprTotal.get("SUMAPLPAYAMT") != null) {
				aumAplpayAmt = ((BigDecimal) badaprTotal.get("SUMAPLPAYAMT")).intValue();
			}
			replaceValue.put(ConstantKey.A047, DF.format(aumAplpayAmt));
		} else {
			replaceValue.put(ConstantKey.A047, "");
		}
	}

	// 同一核定年月補發部分金額之給付年月補發金額加總 SUM(BADAPR. SUPAMT)
	public void A048() {
		if (badaprSup != null) {
			if (badaprSup.containsKey("SUMSUPAMT")) {
				replaceValue.put(ConstantKey.A048, DF.format(badaprSup.get("SUMSUPAMT")));
			} else {
				replaceValue.put(ConstantKey.A048, "");
			}
		} else {
			replaceValue.put(ConstantKey.A048, "");
		}
	}

	// 顯示”00給付（另案扣減資料）0000元（帶入同一核定年月之各給付年月事故者扣減總額加總）” SUM(BADAPR. OTHERAMT) (第四段SQL)
	public void A049() {
		if (badaprTotal != null) {
			int sumOtherAmt = 0;
			if (badaprTotal.get("SUMOTHERAMT") != null) {
				sumOtherAmt = ((BigDecimal) badaprTotal.get("SUMOTHERAMT")).intValue();
			}
			replaceValue.put(ConstantKey.A049, "00給付" + DF.format(sumOtherAmt) + "元");
		} else {
			replaceValue.put(ConstantKey.A049, "");
		}
	}

	// 顯示”受款人乙0000元，...” BADAPR. ISSUEAMT (第五段SQL)
	public void A052() {
		if (badaprPersonal != null) {
			int issueAmt = 0;
			String A052Sting = "";

			/*
			 * if (badaprPersonal.get("SUMISSUEAMT") != null) { issueAmt = ((BigDecimal)
			 * badaprPersonal.get("SUMISSUEAMT")).intValue(); }
			 * replaceValue.put(ConstantKey.A052, "受款人 " + baappbase.getBenName() + " " +
			 * DF.format(issueAmt) + "元");
			 */

			if (badaprPersonal.size() > 0) {

				for (int i = 0; i < badaprPersonal.size(); i++) {
					if (i != badaprPersonal.size() - 1) {
						A052Sting = A052Sting + badaprPersonal.get(i).getBenName() + " "
								+ DF.format(badaprPersonal.get(i).getSumIssueAmt()) + "元" + "，";
					} else if (i == badaprPersonal.size() - 1) {
						A052Sting = A052Sting + badaprPersonal.get(i).getBenName() + " "
								+ DF.format(badaprPersonal.get(i).getSumIssueAmt()) + "元";
					}
				}
			}
			replaceValue.put(ConstantKey.A052, A052Sting);

		} else {
			replaceValue.put(ConstantKey.A052, "");
		}
	}

	// （（帶入該核定年月受款人另案扣減金額之總額），受款人乙0000元，...” (第五段SQL)
	public void A053() {
		if (badaprPersonal != null) {
			int sumOtherAmt = 0;
			String A053Sting = "";
			/*
			 * if (badaprPersonal.get("SUMOTHERAMT") != null) { sumOtherAmt = ((BigDecimal)
			 * badaprPersonal.get("SUMOTHERAMT")).intValue(); }
			 * replaceValue.put(ConstantKey.A053, "受款人 " + baappbase.getBenName() + " " +
			 * DF.format(sumOtherAmt) + "元");
			 */

			if (badaprPersonal.size() > 0) {

				for (int i = 0; i < badaprPersonal.size(); i++) {
					if (i != badaprPersonal.size() - 1) {
						A053Sting = A053Sting + badaprPersonal.get(i).getBenName() + " "
								+ DF.format(badaprPersonal.get(i).getSumOtherAmt()) + "元" + "，";
					} else if (i == badaprPersonal.size() - 1) {
						A053Sting = A053Sting + badaprPersonal.get(i).getBenName() + " "
								+ DF.format(badaprPersonal.get(i).getSumOtherAmt()) + "元";
					}
				}
			}
			replaceValue.put(ConstantKey.A053, A053Sting);
		} else {
			replaceValue.put(ConstantKey.A053, "");
		}
	}

	// 帶入該核定年月受款人實付金額之總額），受款人乙0000元，...” (第五段SQL)
	public void A054() {
		if (badaprPersonal != null) {
			int sumAplpayAmt = 0;
			String A054Sting = "";
			/*
			 * if (badaprPersonal.get("SUMAPLPAYAMT") != null) { sumAplpayAmt =
			 * ((BigDecimal) badaprPersonal.get("SUMAPLPAYAMT")).intValue(); }
			 * replaceValue.put(ConstantKey.A054, "受款人 " + baappbase.getBenName() + " " +
			 * DF.format(sumAplpayAmt) + "元");
			 */

			if (badaprPersonal.size() > 0) {

				for (int i = 0; i < badaprPersonal.size(); i++) {
					if (i != badaprPersonal.size() - 1) {
						A054Sting = A054Sting + badaprPersonal.get(i).getBenName() + " "
								+ DF.format(badaprPersonal.get(i).getSumAplpayAmt()) + "元" + "，";
					} else if (i == badaprPersonal.size() - 1) {
						A054Sting = A054Sting + badaprPersonal.get(i).getBenName() + " "
								+ DF.format(badaprPersonal.get(i).getSumAplpayAmt()) + "元";
					}
				}
			}
			replaceValue.put(ConstantKey.A054, A054Sting);

		} else {
			replaceValue.put(ConstantKey.A054, "");
		}
	}

	public void A055() {
		if (StringUtils.isNotBlank(chkResult)) {
			replaceValue.put(ConstantKey.A055, "另如有提繳勞工退休金者，一併停止提繳。");
		} else {
			replaceValue.put(ConstantKey.A055, "");
		}
	}

	// 款人資料之「受款人匯費」欄 BADAPR. PAYRATE
	public void A059() {
		if (badapr != null && badapr.getPayRate() != null) {
			replaceValue.put(ConstantKey.A059, badapr.getPayRate().toString());
		} else if (badapr != null) {
			replaceValue.put(ConstantKey.A059, "0");
		} else {
			replaceValue.put(ConstantKey.A059, "");
		}
	}

	// If BAAPPBASE. PAYTYP =1
	// Print(並送交金融機構轉帳匯入申請書所載指定之帳號（如未入帳，請向02-23961266轉2212保險收支科洽詢。如有帳號錯誤，本局處理方式一律以文書通知）)
	// If BAAPPBASE. PAYTYP =2
	// Print(並送交金融機構轉帳匯入申請書所載指定之郵局帳號（如未入帳，請向02-23961266轉2212保險收支科洽詢。如有帳號錯誤，本局處理方式一律以文書通知）)
	// If BAAPPBASE. PAYTYP =3
	// Print(請於每月最後1日（遇假日順延）本人親自攜帶本核定通知書、原申請書所用之印章及國民身分證至本局一樓土地銀行（上班時間上午9時30分至下午3時30分）領取。)
	// If BAAPPBASE. PAYTYP =4 Print(檢附郵政匯票乙紙，請 查收。)
	// If BAAPPBASE. PAYTYP =5
	// Print(並送交金融機構於近日內轉帳匯入申請書所載指定之帳號（如未入帳，請向02-23961266轉2212保險收支科洽詢。如有帳號錯誤，本局處理方式一律以文書通知）)
	// If BAAPPBASE. PAYTYP =6
	// Print(並送交金融機構轉帳匯入申請書所載指定之帳號（如未入帳，請向02-23961266轉2212保險收支科洽詢。如有帳號錯誤，本局處理方式一律以文書通知）)
	// If BAAPPBASE. PAYTYP =7 Print(並送交金融機構轉帳匯入 台端指定之帳號。)
	// If BAAPPBASE. PAYTYP =8 Print(並送交金融機構轉帳匯入 台端指定之帳號。)
	// If BAAPPBASE. PAYTYP =9 Print(檢附土地銀行支票乙紙，請 查收。)
	// If BAAPPBASE. PAYTYP =A
	// Print(並由土地銀行台北銀行開立支票寄送台端收領，台端可於收受支票後親至土地銀行台北分行兌領現金或以委任取款方式至受委任人開立帳戶之銀行辦理兌領手續。)

//    public void A060() {
//
//        // 如不是傳入核付日期 則使用系統年月判斷是否組改
//
//        String aplpayDate = (badapr != null ? badapr.getAplpayDate() : null);
//        if (baappbase == null && babcml7 == null) {
//            replaceValue.put(ConstantKey.A060, "");
//            return;
//        }
//        else if (baappbase != null) {
//            if ("1".equalsIgnoreCase(baappbase.getPayTyp())) {
//                replaceValue.put(ConstantKey.A060, "，並送交金融機構轉帳匯入申請書所載指定之帳號（如未入帳，請向02-23961266轉2212" + RptTitleUtility.getSafetyPaymentTitle1(aplpayDate) + "洽詢。）。");
//            }
//            else if ("2".equalsIgnoreCase(baappbase.getPayTyp())) {
//                replaceValue.put(ConstantKey.A060, "，並送交金融機構轉帳匯入申請書所載指定之郵局帳號（如未入帳，請向02-23961266轉2212" + RptTitleUtility.getSafetyPaymentTitle1(aplpayDate) + "洽詢。）。");
//            }
//            else if ("3".equalsIgnoreCase(baappbase.getPayTyp())) {
//                replaceValue.put(ConstantKey.A060, "，請於每月最後1日（遇假日順延）本人親自攜帶本核定通知書、原申請書所用之印章及國民身分證至本局一樓土地銀行（上班時間上午9時30分至下午3時30分）領取。");
//            }
//            else if ("4".equalsIgnoreCase(baappbase.getPayTyp())) {
//                replaceValue.put(ConstantKey.A060, "，檢附郵政匯票乙紙，請  查收。");
//            }
//            else if ("5".equalsIgnoreCase(baappbase.getPayTyp())) {
//                replaceValue.put(ConstantKey.A060, "，並送交金融機構於近日內轉帳匯入申請書所載指定之帳號（如未入帳，請向02-23961266轉2212" + RptTitleUtility.getSafetyPaymentTitle1(aplpayDate) + "洽詢。）。");
//            }
//            else if ("6".equalsIgnoreCase(baappbase.getPayTyp())) {
//                replaceValue.put(ConstantKey.A060, "，並送交金融機構轉帳匯入申請書所載指定之帳號（如未入帳，請向02-23961266轉2212" + RptTitleUtility.getSafetyPaymentTitle1(aplpayDate) + "洽詢。）。");
//            }
//            else if ("7".equalsIgnoreCase(baappbase.getPayTyp())) {
//                replaceValue.put(ConstantKey.A060, "，並送交金融機構轉帳匯入  台端指定之帳號。");
//            }
//            else if ("8".equalsIgnoreCase(baappbase.getPayTyp())) {
//                replaceValue.put(ConstantKey.A060, "，並送交金融機構轉帳匯入  台端指定之帳號。");
//            }
//            else if ("9".equalsIgnoreCase(baappbase.getPayTyp())) {
//                replaceValue.put(ConstantKey.A060, "檢附土地銀行支票乙紙，請  查收。");
//            }
//            else if ("A".equalsIgnoreCase(baappbase.getPayTyp())) {
//                replaceValue.put(ConstantKey.A060, "，並由土地銀行台北分行開立支票寄送台端收領，台端可於收受支票後親至土地銀行全省各分行兌領現金或以委任取款方式至受委任人開立帳戶之銀行辦理兌領手續。");
//            }
//            else {
//                replaceValue.put(ConstantKey.A060, "");
//            }
//        }
//        else if (babcml7 != null) {
//            if ("1".equalsIgnoreCase(babcml7.getPayTyp())) {
//                replaceValue.put(ConstantKey.A060, "，並送交金融機構轉帳匯入申請書所載指定之帳號（如未入帳，請向02-23961266轉2212" + RptTitleUtility.getSafetyPaymentTitle1(aplpayDate) + "洽詢。）。");
//            }
//            else if ("2".equalsIgnoreCase(babcml7.getPayTyp())) {
//                replaceValue.put(ConstantKey.A060, "，並送交金融機構轉帳匯入申請書所載指定之郵局帳號（如未入帳，請向02-23961266轉2212" + RptTitleUtility.getSafetyPaymentTitle1(aplpayDate) + "洽詢。）。");
//            }
//            else if ("3".equalsIgnoreCase(babcml7.getPayTyp())) {
//                replaceValue.put(ConstantKey.A060, "，請於每月最後1日（遇假日順延）本人親自攜帶本核定通知書、原申請書所用之印章及國民身分證至本局一樓土地銀行（上班時間上午9時30分至下午3時30分）領取。");
//            }
//            else if ("4".equalsIgnoreCase(babcml7.getPayTyp())) {
//                replaceValue.put(ConstantKey.A060, "，檢附郵政匯票乙紙，請  查收。");
//            }
//            else if ("5".equalsIgnoreCase(babcml7.getPayTyp())) {
//                replaceValue.put(ConstantKey.A060, "，並送交金融機構於近日內轉帳匯入申請書所載指定之帳號（如未入帳，請向02-23961266轉2212" + RptTitleUtility.getSafetyPaymentTitle1(aplpayDate) + "洽詢。）。");
//            }
//            else if ("6".equalsIgnoreCase(babcml7.getPayTyp())) {
//                replaceValue.put(ConstantKey.A060, "，並送交金融機構轉帳匯入申請書所載指定之帳號（如未入帳，請向02-23961266轉2212" + RptTitleUtility.getSafetyPaymentTitle1(aplpayDate) + "洽詢。）。");
//            }
//            else if ("7".equalsIgnoreCase(babcml7.getPayTyp())) {
//                replaceValue.put(ConstantKey.A060, "，並送交金融機構轉帳匯入  台端指定之帳號。");
//            }
//            else if ("8".equalsIgnoreCase(babcml7.getPayTyp())) {
//                replaceValue.put(ConstantKey.A060, "，並送交金融機構轉帳匯入  台端指定之帳號。");
//            }
//            else if ("9".equalsIgnoreCase(babcml7.getPayTyp())) {
//                replaceValue.put(ConstantKey.A060, "檢附土地銀行支票乙紙，請  查收。");
//            }
//            else if ("A".equalsIgnoreCase(babcml7.getPayTyp())) {
//                replaceValue.put(ConstantKey.A060, "，並由土地銀行台北分行開立支票寄送台端收領，台端可於收受支票後親至土地銀行全省各分行兌領現金或以委任取款方式至受委任人開立帳戶之銀行辦理兌領手續。");
//            }
//            else {
//                replaceValue.put(ConstantKey.A060, "");
//            }
//        }
//
//    }

	public void A060() {

		if (baappbase == null || baappbase.getNotifyForm() == null || "".equals(baappbase.getNotifyForm().trim())) {
			replaceValue.put(ConstantKey.A060, "");
			return;
		} else {
			if (baappbase.getNotifyForm().substring(0, 1).equals("D")) {

				// 如不是傳入核付日期 則使用系統年月判斷是否組改

				if (benData == null) {
					replaceValue.put(ConstantKey.A060, "");
					return;
				}

				String aplpayDate = (badaprA133 != null ? badaprA133.getAplpayDate() : null);
				if ("1".equalsIgnoreCase(benData.getPayTyp())) {
					if (RptTitleUtility.isOrgChg(badaprA133.getIssuYm())) {
						replaceValue.put(ConstantKey.A060, "並送交金融機構轉帳匯入指定之帳號（如未入帳，請向02-23961266轉2212"
								+ RptTitleUtility.getSafetyPaymentTitle1(aplpayDate) + "洽詢。）");
					} else {
						replaceValue.put(ConstantKey.A060, "並送交金融機構轉帳匯入指定之帳號（如未入帳，請向02-23961266轉2212"
								+ RptTitleUtility.getSafetyPaymentTitle1(aplpayDate) + "洽詢。）");
					}
				} else if ("2".equalsIgnoreCase(benData.getPayTyp())) {
					if (RptTitleUtility.isOrgChg(badaprA133.getIssuYm())) {
						replaceValue.put(ConstantKey.A060, "並送交金融機構轉帳匯入指定之郵局帳號（如未入帳，請向02-23961266轉2212"
								+ RptTitleUtility.getSafetyPaymentTitle1(aplpayDate) + "洽詢。）");
					} else {
						replaceValue.put(ConstantKey.A060, "並送交金融機構轉帳匯入指定之郵局帳號（如未入帳，請向02-23961266轉2212"
								+ RptTitleUtility.getSafetyPaymentTitle1(aplpayDate) + "洽詢。）");
					}
				} else if ("3".equalsIgnoreCase(benData.getPayTyp())) {
					replaceValue.put(ConstantKey.A060,
							"請於每月最後1日（遇假日順延）本人親自攜帶本核定通知書、原申請書所用之印章及國民身分證至本局一樓土地銀行（上班時間上午9時30分至下午3時30分）領取。");
				} else if ("4".equalsIgnoreCase(benData.getPayTyp())) {
					replaceValue.put(ConstantKey.A060, "檢附郵政匯票乙紙，請  查收");
				} else if ("5".equalsIgnoreCase(benData.getPayTyp())) {
					replaceValue.put(ConstantKey.A060, "並送交金融機構於近日內轉帳匯入指定之帳號（如未入帳，請向02-23961266轉2212"
							+ RptTitleUtility.getSafetyPaymentTitle1(aplpayDate) + "洽詢。）");
				} else if ("6".equalsIgnoreCase(benData.getPayTyp())) {
					replaceValue.put(ConstantKey.A060, "並送交金融機構轉帳匯入指定之帳號（如未入帳，請向02-23961266轉2212"
							+ RptTitleUtility.getSafetyPaymentTitle1(aplpayDate) + "洽詢。）");
				} else if ("7".equalsIgnoreCase(benData.getPayTyp())) {
					replaceValue.put(ConstantKey.A060, "並送交金融機構轉帳匯入  台端指定之帳號");
				} else if ("8".equalsIgnoreCase(benData.getPayTyp())) {
					replaceValue.put(ConstantKey.A060, "並送交金融機構轉帳匯入  台端指定之帳號");
				} else if ("9".equalsIgnoreCase(benData.getPayTyp())) {
					replaceValue.put(ConstantKey.A060, "檢附土地銀行支票乙紙，請  查收");
				} else if ("A".equalsIgnoreCase(benData.getPayTyp())) {
					replaceValue.put(ConstantKey.A060,
							"並由土地銀行台北分行開立支票寄送台端收領，台端可於收受支票後親至土地銀行全省各分行兌領現金或以委任取款方式至受委任人開立帳戶之銀行辦理兌領手續");
				} else {
					replaceValue.put(ConstantKey.A060, "");
				}

			} else {
				// 如不是傳入核付日期 則使用系統年月判斷是否組改

				String aplpayDate = (badaprA133 != null ? badaprA133.getAplpayDate() : null);
				if ("1".equalsIgnoreCase(baappbase.getPayTyp())) {
					if (RptTitleUtility.isOrgChg(badaprA133.getIssuYm())) {
						replaceValue.put(ConstantKey.A060, "並送交金融機構轉帳匯入指定之帳號（如未入帳，請向02-23961266轉2212"
								+ RptTitleUtility.getSafetyPaymentTitle1(aplpayDate) + "洽詢。）");
					} else {
						replaceValue.put(ConstantKey.A060, "並送交金融機構轉帳匯入指定之帳號（如未入帳，請向02-23961266轉2212"
								+ RptTitleUtility.getSafetyPaymentTitle1(aplpayDate) + "洽詢。）");
					}
				} else if ("2".equalsIgnoreCase(baappbase.getPayTyp())) {
					if (RptTitleUtility.isOrgChg(badaprA133.getIssuYm())) {
						replaceValue.put(ConstantKey.A060, "並送交金融機構轉帳匯入指定之郵局帳號（如未入帳，請向02-23961266轉2212"
								+ RptTitleUtility.getSafetyPaymentTitle1(aplpayDate) + "洽詢。）");
					} else {
						replaceValue.put(ConstantKey.A060, "並送交金融機構轉帳匯入指定之郵局帳號（如未入帳，請向02-23961266轉2212"
								+ RptTitleUtility.getSafetyPaymentTitle1(aplpayDate) + "洽詢。）");
					}
				} else if ("3".equalsIgnoreCase(baappbase.getPayTyp())) {
					replaceValue.put(ConstantKey.A060,
							"請於每月最後1日（遇假日順延）本人親自攜帶本核定通知書、原申請書所用之印章及國民身分證至本局一樓土地銀行（上班時間上午9時30分至下午3時30分）領取。");
				} else if ("4".equalsIgnoreCase(baappbase.getPayTyp())) {
					replaceValue.put(ConstantKey.A060, "檢附郵政匯票乙紙，請  查收");
				} else if ("5".equalsIgnoreCase(baappbase.getPayTyp())) {
					replaceValue.put(ConstantKey.A060, "並送交金融機構於近日內轉帳匯入指定之帳號（如未入帳，請向02-23961266轉2212"
							+ RptTitleUtility.getSafetyPaymentTitle1(aplpayDate) + "洽詢。）");
				} else if ("6".equalsIgnoreCase(baappbase.getPayTyp())) {
					replaceValue.put(ConstantKey.A060, "並送交金融機構轉帳匯入指定之帳號（如未入帳，請向02-23961266轉2212"
							+ RptTitleUtility.getSafetyPaymentTitle1(aplpayDate) + "洽詢。）");
				} else if ("7".equalsIgnoreCase(baappbase.getPayTyp())) {
					replaceValue.put(ConstantKey.A060, "並送交金融機構轉帳匯入  台端指定之帳號");
				} else if ("8".equalsIgnoreCase(baappbase.getPayTyp())) {
					replaceValue.put(ConstantKey.A060, "並送交金融機構轉帳匯入  台端指定之帳號");
				} else if ("9".equalsIgnoreCase(baappbase.getPayTyp())) {
					replaceValue.put(ConstantKey.A060, "檢附土地銀行支票乙紙，請  查收");
				} else if ("A".equalsIgnoreCase(baappbase.getPayTyp())) {
					replaceValue.put(ConstantKey.A060,
							"並由土地銀行台北分行開立支票寄送台端收領，台端可於收受支票後親至土地銀行全省各分行兌領現金或以委任取款方式至受委任人開立帳戶之銀行辦理兌領手續");
				} else {
					replaceValue.put(ConstantKey.A060, "");
				}
			}
		}
	}

	// 抵銷紓困貸款資料之「勞貸貸款金額」欄 BADAPR .LOANAMT
	public void A061() {
		if (badapr != null && badapr.getLoanAmt() != null) {
			replaceValue.put(ConstantKey.A061, DF.format(badapr.getLoanAmt().intValue()));
		} else if (badapr != null) {
			replaceValue.put(ConstantKey.A061, "0");
		} else {
			replaceValue.put(ConstantKey.A061, "0");
		}
	}

	// 抵銷紓困貸款資料之「本次勞貸本息截止日」欄 BADAPR .DLINEDATE
	public void A062() {
		if (badapr != null) {
			// replaceValue.put(ConstantKey.A062,
			// DateUtility.changeDateType(badapr.getDlineDate()));
			replaceValue.put(ConstantKey.A062, DateUtility.formatChineseDateTimeString(badapr.getDlineDate(), true));
		} else {
			replaceValue.put(ConstantKey.A062, "");
		}
	}

	// 抵銷紓困貸款資料之「本次勞貸本金餘額」欄 BADAPR .RECAPAMT
	public void A063() {
		if (badapr != null && badapr.getRecapAmt() != null) {
			replaceValue.put(ConstantKey.A063, DF.format(badapr.getRecapAmt().intValue()));
		} else if (badapr != null) {
			replaceValue.put(ConstantKey.A063, "0");
		} else {
			replaceValue.put(ConstantKey.A063, "0");
		}
	}

	// 抵銷紓困貸款資料之「本次勞貸利息」欄 BADAPR .LOANITRT
	public void A064() {
		if (badapr != null && badapr.getLoaniTrt() != null) {
			replaceValue.put(ConstantKey.A064, DF.format(badapr.getLoaniTrt().intValue()));
		} else if (badapr != null) {
			replaceValue.put(ConstantKey.A064, "0");
		} else {
			replaceValue.put(ConstantKey.A064, "0");
		}
	}

	// 抵銷紓困貸款資料之「本次抵銷勞貸其他費用」欄 BADAPR .OFFSETEXP
	public void A065() {
		if (badapr != null && badapr.getOffsetExp() != null) {
			replaceValue.put(ConstantKey.A065, DF.format(badapr.getOffsetExp().intValue()));
		} else if (badapr != null) {
			replaceValue.put(ConstantKey.A065, "0");
		} else {
			replaceValue.put(ConstantKey.A065, "0");
		}
	}

	// 該核定年月中，各給付年月抵銷紓困貸款資料之「本次抵銷金額」之總合 BADAPR .OFFSETAMT (第四段SQL)
	public void A066() {
		if (badaprTotal != null) {
			int sumOffsetAmt = 0;
			if (badaprTotal.get("SUMOFFSETAMT") != null) {
				sumOffsetAmt = ((BigDecimal) badaprTotal.get("SUMOFFSETAMT")).intValue();
			}
			replaceValue.put(ConstantKey.A066, DF.format(sumOffsetAmt));
		} else {
			replaceValue.put(ConstantKey.A066, "");
		}
	}

	// 該核定年月中，各給付年月實付金額之總額－「本次抵銷金額」之總合 SUM(BADAPR.APLPAYAMT) (第四段SQL)
	public void A067() {
		if (badaprTotal != null) {
			int sumAplpayAmt = 0;
			if (badaprTotal.get("SUMAPLPAYAMT") != null) {
				sumAplpayAmt = ((BigDecimal) badaprTotal.get("SUMAPLPAYAMT")).intValue();
			}
			replaceValue.put(ConstantKey.A067, DF.format(sumAplpayAmt));
		} else {
			replaceValue.put(ConstantKey.A067, "");
		}
	}

	public void A068() {
		int iOffsetExp = 0;
		int iRecapAmt = 0;
		int iLoaniTrt = 0;

		if (badapr != null) {

			if (badapr.getOffsetExp() != null) {
				iOffsetExp = badapr.getOffsetExp().intValue();
			}

			if (badapr.getRecapAmt() != null) {
				iRecapAmt = badapr.getRecapAmt().intValue();
			}

			if (badapr.getLoaniTrt() != null) {
				iLoaniTrt = badapr.getLoaniTrt().intValue();
			}

			replaceValue.put(ConstantKey.A068, DF.format(iOffsetExp + iRecapAmt + iLoaniTrt));
		} else {
			replaceValue.put(ConstantKey.A068, "0");
		}

	}

	public void A069() {
		// TODO
		replaceValue.put(ConstantKey.A069, "");
	}

	public void A070() {
		// TODO
		replaceValue.put(ConstantKey.A070, "");
	}

	public void A071() {
		// TODO
		replaceValue.put(ConstantKey.A071, "");
	}

	public void A072() {
		// TODO
		replaceValue.put(ConstantKey.A072, "");
	}

	public void A073() {
		// TODO
		replaceValue.put(ConstantKey.A073, "");
	}

	public void A074() {
		// TODO
		replaceValue.put(ConstantKey.A074, "");
	}

	// If BADAPR. OLDRATE > 0 then Print(第58條之2第1項、)
	// If BADAPR. OLDRATE < 0 then Print(第58條之2第2項、)
	public void A075() {
		if (badapr != null && (badapr.getOldRate() != null && badapr.getOldRate().doubleValue() > 0)) {
			replaceValue.put(ConstantKey.A075, "第58條之2第1項、");
		} else if (badapr != null && (badapr.getOldRate() != null && badapr.getOldRate().doubleValue() < 0)) {
			replaceValue.put(ConstantKey.A075, "第58條之2第2項、");
		} else {
			replaceValue.put(ConstantKey.A075, "");
		}
	}

	public void A076() {

		if (baappbaseList != null) {

			if (baappbaseList.size() > 0) {
				String A076Sting = "";
				for (int i = 0; i < baappbaseList.size(); i++) {
					if (!baappbaseList.get(i).getSeqNo().equals("0000") && i != baappbaseList.size() - 1) {
						if (StringUtils.isNotBlank(baappbaseList.get(i).getGrdName())) {
							// A076Sting = A076Sting +"姓名："+ baappbaseList.get(i).getBenName() +"，身分證統一編號："
							// + baappbaseList.get(i).getBenIdnNo().substring(0, 2) + "*****" +
							// baappbaseList.get(i).getBenIdnNo().substring(7,
							// baappbaseList.get(i).getBenIdnNo().length()) +"，出生日期："+
							// DateUtility.formatChineseDateTimeString(baappbaseList.get(i).getBenBrDate(),
							// true) +"，法定代理人："+ baappbaseList.get(i).getGrdName()+"；";
							A076Sting = A076Sting + baappbaseList.get(i).getBenName() + "，身分證統一編號："
									+ (baappbaseList.get(i).getBenIdnNo().length() >= 10
											? baappbaseList.get(i).getBenIdnNo().substring(0, 6)
											: "")
									+ "****" + "，出生日期："
									+ DateUtility.formatChineseDateTimeString(baappbaseList.get(i).getBenBrDate(), true)
									+ "，法定代理人：" + baappbaseList.get(i).getGrdName() + "；";
						} else {
							// A076Sting = A076Sting +"姓名："+ baappbaseList.get(i).getBenName() +"，身分證統一編號："
							// + baappbaseList.get(i).getBenIdnNo().substring(0, 2) + "*****" +
							// baappbaseList.get(i).getBenIdnNo().substring(7,
							// baappbaseList.get(i).getBenIdnNo().length()) +"，出生日期："+
							// DateUtility.formatChineseDateTimeString(baappbaseList.get(i).getBenBrDate(),
							// true) + "；";
							A076Sting = A076Sting + baappbaseList.get(i).getBenName() + "，身分證統一編號："
									+ (baappbaseList.get(i).getBenIdnNo().length() >= 10
											? baappbaseList.get(i).getBenIdnNo().substring(0, 6)
											: "")
									+ "****" + "，出生日期："
									+ DateUtility.formatChineseDateTimeString(baappbaseList.get(i).getBenBrDate(), true)
									+ "；";
						}
					} else if (!baappbaseList.get(i).getSeqNo().equals("0000") && i == baappbaseList.size() - 1) {
						if (StringUtils.isNotBlank(baappbaseList.get(i).getGrdName())) {
							// A076Sting = A076Sting +"姓名："+ baappbaseList.get(i).getBenName() +"，身分證統一編號："
							// + baappbaseList.get(i).getBenIdnNo().substring(0, 2) + "*****" +
							// baappbaseList.get(i).getBenIdnNo().substring(7,
							// baappbaseList.get(i).getBenIdnNo().length()) +"，出生日期："+
							// DateUtility.formatChineseDateTimeString(baappbaseList.get(i).getBenBrDate(),
							// true) +"，法定代理人："+ baappbaseList.get(i).getGrdName();
							A076Sting = A076Sting + baappbaseList.get(i).getBenName() + "，身分證統一編號："
									+ (baappbaseList.get(i).getBenIdnNo().length() >= 10
											? baappbaseList.get(i).getBenIdnNo().substring(0, 6)
											: "")
									+ "****" + "，出生日期："
									+ DateUtility.formatChineseDateTimeString(baappbaseList.get(i).getBenBrDate(), true)
									+ "，法定代理人：" + baappbaseList.get(i).getGrdName();
						} else {
							// A076Sting = A076Sting +"姓名："+ baappbaseList.get(i).getBenName() +"，身分證統一編號："
							// + baappbaseList.get(i).getBenIdnNo().substring(0, 2) + "*****" +
							// baappbaseList.get(i).getBenIdnNo().substring(7,
							// baappbaseList.get(i).getBenIdnNo().length()) +"，出生日期："+
							// DateUtility.formatChineseDateTimeString(baappbaseList.get(i).getBenBrDate(),
							// true);
							A076Sting = A076Sting + baappbaseList.get(i).getBenName() + "，身分證統一編號："
									+ (baappbaseList.get(i).getBenIdnNo().length() >= 10
											? baappbaseList.get(i).getBenIdnNo().substring(0, 6)
											: "")
									+ "****" + "，出生日期：" + DateUtility
											.formatChineseDateTimeString(baappbaseList.get(i).getBenBrDate(), true);
						}
					}
				}

				replaceValue.put(ConstantKey.A076, A076Sting);

			} else {
				replaceValue.put(ConstantKey.A076, "");
			}

		} else {
			replaceValue.put(ConstantKey.A076, "");
		}

	}

	// 「受款人姓名」欄 BAAPPBASE.BENNAME
	public void A077() {
		if (baappbaseA077 != null) {
			replaceValue.put(ConstantKey.A077, StringUtils.trimToEmpty(baappbaseA077.getBenName()));
		} else {
			replaceValue.put(ConstantKey.A077, "");
		}
	}

	// 「受款人身分證號」欄 BAAPPBASE.BENIDNNO
	public void A078() {
		if (baappbaseA077 != null && baappbaseA077.getBenIdnNo() != null) {
			String idn = baappbaseA077.getBenIdnNo();
			if (idn.length() >= 10) {
				// replaceValue.put(ConstantKey.A078, idn.substring(0, 2) + "*****" +
				// idn.substring(7, idn.length()));
				replaceValue.put(ConstantKey.A078, idn.substring(0, 6) + "****");
			} else {
				replaceValue.put(ConstantKey.A078, idn);
			}
		} else {
			replaceValue.put(ConstantKey.A078, "");
		}
	}

	// 「受款人出生日期」欄 BAAPPBASE. BENBRDATE
	public void A079() {
		if (baappbaseA077 != null) {
			replaceValue.put(ConstantKey.A079, DateUtility
					.formatChineseDateTimeString(StringUtils.trimToEmpty(baappbaseA077.getBenBrDate()), true));
		} else {
			replaceValue.put(ConstantKey.A079, "");
		}
	}

	// 「受款人數」欄 Count(BAAPPBASE.SEQNO)
	public void A080() {
		if (payeeCountA080 != null && payeeCountA080.intValue() > 0) {
			replaceValue.put(ConstantKey.A080, payeeCountA080.toString());
		} else {
			replaceValue.put(ConstantKey.A080, "0");
		}
	}

	// 眷屬或遺屬符合人數」：BADAPR.QUALCOUNT(參考A010抓的核定檔)。
	public void A088() {
		if (badapr != null) {
			replaceValue.put(ConstantKey.A088, DF.format(badapr.getQualCount()));
		} else {
			replaceValue.put(ConstantKey.A088, "");
		}
	}

	// 符合之遺屬姓名：帶入結案原因空白且不合格原因空白，且最大給付年月合格之遺屬姓名，多人時帶入「000、…、000及000」
	public void A089() {

		if (baappbaseListA089 != null) {

			if (baappbaseListA089.size() > 0) {
				String A089String = "";
				ArrayList<String> sNameList = new ArrayList<String>();

				for (int i = 0; i < baappbaseListA089.size(); i++) {
					// if (!baappbasePrintList.get(i).getSeqNo().equals("0000") &&
					// baappbasePrintList.get(i).getCloseCause().equals("") &&
					// baappbasePrintList.get(i).getUnqualifiedCause().equals("") &&
					// baappbasePrintList.get(i).getBenDieDate().equals("")) {
					if (baappbaseListA089.get(i).getSeqNo().substring(2, 4).equals("00")) {
						sNameList.add(baappbaseListA089.get(i).getBenName());
					}
				}

				for (int i = 0; i < sNameList.size(); i++) {
					if (i != sNameList.size() - 1 && i != sNameList.size() - 2) {
						A089String = A089String + sNameList.get(i) + "、";
					} else if (i == sNameList.size() - 2 && i != sNameList.size() - 1) {
						A089String = A089String + sNameList.get(i) + "及";
					} else if (i == sNameList.size() - 1) {
						A089String = A089String + sNameList.get(i);
					}
				}

				replaceValue.put(ConstantKey.A089, A089String);
			} else {
				replaceValue.put(ConstantKey.A089, "");
			}
		} else {
			replaceValue.put(ConstantKey.A089, "");
		}
	}

	public void A090() {
		// TODO
		replaceValue.put(ConstantKey.A090, "");
	}

	public void A092() {
		// TODO
		replaceValue.put(ConstantKey.A092, "");
	}

	// 老年、失能年金之「累計已領年金金額」欄 Sum(BADAPR.APLPAYAMT)
	public void A093() {
		// TODO
		replaceValue.put(ConstantKey.A093, "");
	}

	// 當申請單位保險證號非空白時，顯示”（最後投保單位：○○）”，○○係以最後單位保險證號比對投保單位檔，帶入投保單位中文名稱。
	// 20190430：當申請單位保險證號非空白時，顯示”（最後投保單位：○○）”；當申請單位保險證號為空白時，不顯示”（最後投保單位：○○）”
	public void A094() {
		if (baappbase != null) {
			String lsUbNo = StringUtils.trimToEmpty(baappbase.getLsUbno());
			String apUbNo = StringUtils.trimToEmpty(baappbase.getApUbno());

			if ((!apUbNo.equals("")) && (!lsUbNo.equals(""))) {
				CaubDao caubDao = (CaubDao) SpringHelper.getBeanById("caubDao");
				replaceValue.put(ConstantKey.A094,
						"（最後投保單位:" + StringUtils.defaultString(caubDao.selectCaubName(baappbase.getLsUbno())) + "）");
			} else {
				replaceValue.put(ConstantKey.A094, "");
			}

		} else {
			replaceValue.put(ConstantKey.A094, "");
		}

	}

	// 1.當「國勞合併」欄之值為空白時，顯示”勞工保險老年給付申請書及給付收據書件”
	// 2.當「國勞合併」欄之值為Y時，顯示”同時請領勞工保險國民年金保險老年年金給付申請書及給付收據書件”
	public void A095() {

		if (baappbase != null && StringUtils.trimToEmpty(baappbase.getCombapMark()).equalsIgnoreCase("")) {
			replaceValue.put(ConstantKey.A095, "勞工保險老年給付申請書及給付收據書件");
		} else if (baappbase != null && "Y".equalsIgnoreCase(baappbase.getCombapMark())) {
			replaceValue.put(ConstantKey.A095, "同時請領勞工保險國民年金保險老年年金給付申請書及給付收據書件");
		} else {
			replaceValue.put(ConstantKey.A095, "勞工保險老年給付申請書及給付收據書件");
		}
	}

	// 新案申請單位保險證號非空白，且無退保日期或事故日期<退保日期時，顯示”又台端業已離職，惟尚未申報退保，本局逕自離職日起予以退保。
	public void A096() {
		if (baappbase != null) {
			String apubNo = StringUtils.trimToEmpty(baappbase.getApUbno());
			String evtJobDate = StringUtils.trimToEmpty(baappbase.getEvtJobDate()); // 事故日期
			String evtDate = StringUtils.trimToEmpty(baappbase.getEvtDate()); // 退保日期
			if (!apubNo.equals("") && (StringUtils.isBlank(evtDate)
					|| (StringUtils.isNotBlank(evtDate) && Integer.parseInt(evtJobDate) < Integer.parseInt(evtDate)))) {
				replaceValue.put(ConstantKey.A096, "又台端業已離職，惟尚未申報退保，本局逕自離職日起予以退保。");
			} else {
				replaceValue.put(ConstantKey.A096, "");
			}
		} else {
			replaceValue.put(ConstantKey.A096, "");
		}
	}

	// 當為外籍人士，且身分查核年月欄位非空白時，顯示”台端未於國內設有戶籍，每年須重新檢送身分及相關證明文件至局查核，屆時未檢送應暫時停止發給，併予敘明。
	public void A097() {
		if (baappbase != null) {
			String benNationTyp = StringUtils.trimToEmpty(baappbase.getBenNationTyp());
			String idnChkYm = StringUtils.trimToEmpty(baappbase.getIdnChkYm());
			if (benNationTyp.equals("2") && !idnChkYm.equals("")) {
				replaceValue.put(ConstantKey.A097, "台端未於國內設有戶籍，每年須重新檢送身分及相關證明文件至局查核，屆時未檢送應暫時停止發給，併予敘明。");
			} else {
				replaceValue.put(ConstantKey.A097, "");
			}
		} else {
			replaceValue.put(ConstantKey.A097, "");
		}
	}

	// 當申請單位保險證號非空白，且等於最後投保單位保險證號時，顯示”或投保單位”
	public void A098() {
		if (baappbase != null) {
			String apUbNo = StringUtils.trimToEmpty(baappbase.getApUbno());
			String lsUbNo = StringUtils.trimToEmpty(baappbase.getLsUbno());
			if (!apUbNo.equals("") && apUbNo.equalsIgnoreCase(lsUbNo)) {
				replaceValue.put(ConstantKey.A098, "或投保單位");
			} else {
				replaceValue.put(ConstantKey.A098, "");
			}
		} else {
			replaceValue.put(ConstantKey.A098, "");
		}
	}

	// 「事故日期」欄BAAPPBASE. EVTJOBDATE
	public void A099() {
		if (baappbase != null) {
			replaceValue.put(ConstantKey.A099,
					DateUtility.formatChineseDateTimeString(baappbase.getEvtJobDate(), true));
		} else {
			replaceValue.put(ConstantKey.A099, "");
		}
	}

//    public void A100() {
//        if (babcml7 != null && babcml7.getReFees() != null) {
//            replaceValue.put(ConstantKey.A100, DF.format(babcml7.getReFees()));
//        }
//        else {
//            replaceValue.put(ConstantKey.A100, "");
//        }
//    }

	// A100 = ISSUYM 顯示為： ○○○年○○月
	public void A100() {
		if (baappbase != null) {
			replaceValue.put(ConstantKey.A100, DateUtility
					.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(baappbase.getIssuYm()), true));
		} else {
			replaceValue.put(ConstantKey.A100, "");
		}
	}

	// 原核定金額
	public void A101() {
		if (badaprBefAmt != null) {
			replaceValue.put(ConstantKey.A101, DF.format(badaprBefAmt.get("BEFISSUEAMT")));
		} else {
			replaceValue.put(ConstantKey.A101, "");
		}
	}

	// 原計算式
	public void A102() {
		if (badaprBefAmt != null) {
			String str = "第" + badaprBefAmt.get("OLDAB") + "式";
			replaceValue.put(ConstantKey.A102, str);
		} else {
			replaceValue.put(ConstantKey.A102, "");
		}
	}

	// 投保年資計算
	public void A103() {
		if (badapr != null) {
			BigDecimal aplpayseniy = BigDecimal.ZERO;
			if (badapr.getAplPaySeniY() != null)
				aplpayseniy = badapr.getAplPaySeniY();
			BigDecimal aplpaysenim = BigDecimal.ZERO;
			if (badapr.getAplPaySeniM() != null)
				aplpaysenim = badapr.getAplPaySeniM();

			BigDecimal seni = aplpayseniy.add(aplpaysenim.divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP));
			replaceValue.put(ConstantKey.A103, DF.format(seni));
		} else {
			replaceValue.put(ConstantKey.A103, "");
		}
	}

	// 原所得替代率
	public void A104() {
		if (badaprBefAmt != null) {
			if (StringUtils.equals("1", (String) badaprBefAmt.get("OLDAB"))) {
				replaceValue.put(ConstantKey.A104, "0.775%+3000");
			} else if (StringUtils.equals("2", (String) badaprBefAmt.get("OLDAB"))) {
				replaceValue.put(ConstantKey.A104, "1.55%");
			} else {
				replaceValue.put(ConstantKey.A104, "");
			}
		} else {
			replaceValue.put(ConstantKey.A104, "");
		}
	}

	// 展減比例
	public void A105() {
		if (badapr != null) {
			if (badapr.getOldRate() == null) {
				replaceValue.put(ConstantKey.A105, "*（1 + 00.00%）");
			} else if (badapr.getOldRate().intValue() >= 0) {
				replaceValue.put(ConstantKey.A105, "*（1 + " + badapr.getOldRate().intValue() + ".00%）");
			} else {
				replaceValue.put(ConstantKey.A105, "*（1 - " + badapr.getOldRate().abs().intValue() + ".00%）");
			}
		} else {
			replaceValue.put(ConstantKey.A105, "*（1 + 00.00%）");
		}
	}

	// 條例58條之2
	public void A106() {
		if (badapr != null) {
			if (badapr.getOldRate() == null || badapr.getOldRate().intValue() == 0) {
				replaceValue.put(ConstantKey.A106, "");
			} else if (badapr.getOldRate().intValue() > 0) {
				replaceValue.put(ConstantKey.A106,
						"另依照同條例第58條之2第1項規定，符合第58條第1項第1款及第5項所定請領老年年金給付條件而延後請領者，於請領時應發給展延老年年金給付。每延後1年，依前條規定計算之給付金額增給4%，最多增給20%。");
			} else {
				replaceValue.put(ConstantKey.A106,
						"另依照同條例第58條之2第2項規定，被保人年資滿15年，未符合第58條第1項及第5項所定請領年齡者，得提前5年請領老年年金給付，每提前1年，依前條規定計算之給付金額減給4%，最多減給20%。");
			}
		} else {
			replaceValue.put(ConstantKey.A106, "");
		}
	}

	// 核定年月總核付金額
	public void A107() {
		if (baappbase != null) {
			BadaprDao badaprDao = (BadaprDao) SpringHelper.getBeanById("badaprDao");
			HashMap<String, Object> map = badaprDao.selectReportReplaceDataForToalIssuAmt(baappbase.getApNo(),
					baappbase.getIssuYm());
			if (map != null && map.containsKey("TOTALISSUEAMT")) {
				replaceValue.put(ConstantKey.A107, DF.format(map.get("TOTALISSUEAMT")));
			} else {
				replaceValue.put(ConstantKey.A107, "");
			}
		} else {
			replaceValue.put(ConstantKey.A107, "");
		}
	}

	// 核定總金額
	public void A108() {
		if (badaprTotal != null) {
			int sumIssueAmt = 0;
			if (badaprTotal.get("SUMISSUEAMT") != null) {
				sumIssueAmt = ((BigDecimal) badaprTotal.get("SUMISSUEAMT")).intValue();
			}
			replaceValue.put(ConstantKey.A108, DF.format(sumIssueAmt));
		} else {
			replaceValue.put(ConstantKey.A108, "0");
		}
	}

	// 加計眷屬或遺屬比率：BADAPR. OLDRATE
	public void A110() {
		if (badapr != null) {
			replaceValue.put(ConstantKey.A110, DF.format(badapr.getOldRate()));
		} else {
			replaceValue.put(ConstantKey.A110, "");
		}
	}

	// 年度在學通知補正期限
	// 1.當次月核定之核定年月為8月者：帶入系統年度+9月+30日帶入，例如102年9月30日。
	// 2.當次月核定之核定年月為9月者：帶入系統年度+10月+15日帶入，例如102年10月15日。
	public void A111() {
		String A111String = "";

		if (StringUtils.isNotBlank(baappbase.getIssuYm())) {
			String MMonth = baappbase.getIssuYm().substring(4, 6);
			if (StringUtils.equals(MMonth, "08") || StringUtils.equals(MMonth, "06")) {
				A111String = DateUtility.getNowChineseDate().substring(0, 3) + "年9月30日";
			} else if (StringUtils.equals(MMonth, "09")) {
				A111String = DateUtility.getNowChineseDate().substring(0, 3) + "年10月15日";
			}
		}

		replaceValue.put(ConstantKey.A111, A111String);

	}

	// 年度在學通知補正期限
	// 1.當次月核定之核定年月為8月者：帶入系統年度+9月+15日帶入，例如102年9月15日。
	// 2.當次月核定之核定年月為9月者：帶入系統年度+10月+15日帶入，例如102年10月15日。
	public void A112() {
		String A112String = "";

		if (StringUtils.isNotBlank(baappbase.getIssuYm())) {
			String MMonth = baappbase.getIssuYm().substring(4, 6);
			if (StringUtils.equals(MMonth, "08")) {
				A112String = DateUtility.getNowChineseDate().substring(0, 3) + "年9月15日";
			} else if (StringUtils.equals(MMonth, "09")) {
				A112String = DateUtility.getNowChineseDate().substring(0, 3) + "年10月15日";
			}
		}

		replaceValue.put(ConstantKey.A112, A112String);

	}

	// 年度通知函補送在學證明年度 以系統年度帶入
	public void A113() {

		String A113String = DateUtility.getNowChineseDate().substring(0, 3);

		replaceValue.put(ConstantKey.A113, A113String);

	}

	// 遺屬年金勞保計算金額：讀取事故者給付核定檔「第一式金額」之值帶入 = Badapr(0000).OLDAAMT
	public void A114() {
		if (BafamilyDataList != null) {
			if (BafamilyDataList.size() > 0) {
				StringBuffer famName = new StringBuffer();
				for (int i = 0; i < BafamilyDataList.size(); i++) {
					if (i == BafamilyDataList.size() - 1) {
						famName.append(BafamilyDataList.get(i).getFamName());
					} else {
						famName.append(BafamilyDataList.get(i).getFamName() + "、");
					}
				}

				replaceValue.put(ConstantKey.A114, famName.toString());
			} else {
				replaceValue.put(ConstantKey.A114, "");
			}
		} else {
			replaceValue.put(ConstantKey.A114, "");
		}
	}

	// 遺屬年金勞保計算金額：讀取事故者給付核定檔「第一式金額」之值帶入 = Badapr(0000).OLDAAMT
	public void A116() {
		if (badapr != null) {
			replaceValue.put(ConstantKey.A116, DF.format(badapr.getOldaAmt()));
		} else {
			replaceValue.put(ConstantKey.A116, "0");
		}

	}

	// 遺屬年金勞保給付金額：讀取事故者給付核定檔「第二式金額」之值帶入 = Badapr(0000). OLDBAMT
	public void A117() {
		if (badapr != null) {
			replaceValue.put(ConstantKey.A117, DF.format(badapr.getOldbAmt()));
		} else {
			replaceValue.put(ConstantKey.A117, "0");
		}

	}

	// 同一核定年月，排除補發部分金額之給付年月後，有二個以上給付年月：顯示「。本次一併發給<A041>遺屬年金合計<A047>元」
	public void A118() {
		String A118String = "";
		if (badaprA118 != null) {
			int payYmCount = ((BigDecimal) badaprA118.get("PAYYMCOUNT")).intValue();

			if (payYmCount > 1) {

				String minPayYm = DateUtility.changeWestYearMonthType((String) badaprA118.get("MINPAYYM"));
				String maxPayYm = DateUtility.changeWestYearMonthType((String) badaprA118.get("MAXPAYYM"));
				String maxMinPayYm = minPayYm.substring(0, 3) + " 年 " + minPayYm.substring(3, 5) + " 月至"
						+ maxPayYm.substring(0, 3) + " 年 " + maxPayYm.substring(3, 5) + " 月";

				int sumBefIssueAmt = 0;
				// if (badaprA118.get("SUMBEFISSUEAMT") != null) {
				// sumBefIssueAmt = ((BigDecimal) badaprA118.get("SUMBEFISSUEAMT")).intValue();
				// }
				if (badaprA118.get("SUMAPLPAYAMT") != null) {
					sumBefIssueAmt = ((BigDecimal) badaprA118.get("SUMAPLPAYAMT")).intValue();
				}

				A118String = "。本次一併發給" + maxMinPayYm + "期間遺屬年金合計" + DF.format(sumBefIssueAmt) + "元";

				replaceValue.put(ConstantKey.A118, A118String);

			} else {
				replaceValue.put(ConstantKey.A118, A118String);
			}

		} else {
			replaceValue.put(ConstantKey.A118, A118String);
		}

	}

	// 如果遺屬年金的勞保計算金額小於3000，本欄位顯示「，依規定，不足3000元，以3000元計算」。
	public void A119() {
		if (badapr != null && badapr.getOldaAmt().compareTo(new BigDecimal("3000")) == -1) {
			replaceValue.put(ConstantKey.A119, "，依規定，不足3,000元，以3,000元計算");
		} else {
			replaceValue.put(ConstantKey.A119, "");
		}

	}

	// 如果遺屬年金的勞保計算金額小於3000，本欄位顯示「，因不足3000元，發給3000元」。
	public void A120() {
		if (badapr != null && badapr.getOldaAmt().compareTo(new BigDecimal("3000")) == -1) {
			replaceValue.put(ConstantKey.A120, "，因不足3,000元，發給3,000元");
		} else {
			replaceValue.put(ConstantKey.A120, "");
		}

	}

	// (1) 讀取事故者給付核定檔「第一式金額」Badapr(0000).OLDAAMT之值< 3000 ，顯示
	// “之半數為”+Badapr(0000).OLDAAMT+“元，因不足3000元，發給3000元”
	// (2) 讀取事故者給付核定檔「第一式金額」Badapr(0000).OLDAAMT之值> 3000 ，顯示
	// “，發給半數計”+Badapr(0000).OLDAAMT+“元”
	public void A121() {
		String A121String = "";

		if (badapr != null && badapr.getOldaAmt().compareTo(new BigDecimal("3000")) == -1) {
			A121String = "之半數為" + DF.format(badapr.getOldaAmt()) + "元，因不足3,000元，發給3,000元";
		} else if (badapr != null && badapr.getOldaAmt().compareTo(new BigDecimal("3000")) == 1) {
			A121String = "，發給半數計" + badapr.getOldaAmt() + "元";
		}

		replaceValue.put(ConstantKey.A121, A121String);

	}

	// 同一核定年月，排除補發部分金額之給付年月後，有二個以上給付年月：顯示「、第65條之1」。(1) A118該段SQL，PAYYMCOUNT > 1
	// ，則顯示：“、第65條之1”
	public void A122() {
		String A122String = "";
		if (badaprA122 != null) {
			int payYmCount = ((BigDecimal) badaprA122.get("PAYYMCOUNT")).intValue();

			if (payYmCount > 1) {
				A122String = "、第65條之1";
				replaceValue.put(ConstantKey.A122, A122String);
			} else {
				replaceValue.put(ConstantKey.A122, A122String);
			}
		} else {
			replaceValue.put(ConstantKey.A122, A122String);
		}
	}

	// 每月在學通知補正期限)顯示：核定年月+1個月之年月及該月最後一日：例如，核定年月為102年12月，則顯示103年1月31日
	public void A123() {
		String MDate = "";

		if (StringUtils.isNotBlank(baappbase.getIssuYm())) {
			MDate = DateUtility.calMonth(baappbase.getIssuYm() + "01", 1);
			String MDay = String.valueOf(DateUtility.lastDay(MDate));
			MDate = DateUtility.formatChineseDateString(MDate.substring(0, 6) + MDay, true);
		}

		replaceValue.put(ConstantKey.A123, MDate);

	}

	// 遺屬每月補在學證明通知函中的替換變數(A124_每月通知函補送在學證明年度)顯示
	// a.核定年月為8、9、10、11、12月者：顯示當次月核定之核定年月之年度。b.核定年月為1、2、3、4、5、6、7月者：顯示當次月核定之核定年月-1年之年度。
	public void A124() {
		String MYear = "";
		if (StringUtils.isNotBlank(baappbase.getIssuYm())) {
			String MMonth = baappbase.getIssuYm().substring(4, 6);
			if (StringUtils.equals(MMonth, "01") || StringUtils.equals(MMonth, "02") || StringUtils.equals(MMonth, "03")
					|| StringUtils.equals(MMonth, "04") || StringUtils.equals(MMonth, "05")
					|| StringUtils.equals(MMonth, "06") || StringUtils.equals(MMonth, "07")) {
				MYear = DateUtility.calYear(DateUtility.changeWestYearMonthType(baappbase.getIssuYm()) + "01", -1).substring(0, 3);
			} else {
				MYear = DateUtility.changeWestYearMonthType(baappbase.getIssuYm()).substring(0, 3);
			}
		}

		replaceValue.put(ConstantKey.A124, MYear);

	}

	// 遺屬每月補在學證明通知函中的替換變數(A125_每月通知函補送在學證明學期)顯示 a.核定年月為8、9、10、11、12月者：顯示1
	// b.核定年月為1、2、3、4、5、6、7月者：顯示2。
	public void A125() {
		String semester = "";

		if (StringUtils.isNotBlank(baappbase.getIssuYm())) {
			String MMonth = baappbase.getIssuYm().substring(4, 6);
			if (StringUtils.equals(MMonth, "01") || StringUtils.equals(MMonth, "02") || StringUtils.equals(MMonth, "03")
					|| StringUtils.equals(MMonth, "04") || StringUtils.equals(MMonth, "05")
					|| StringUtils.equals(MMonth, "06") || StringUtils.equals(MMonth, "07")) {
				semester = "2";
			} else {
				semester = "1";
			}
		}

		replaceValue.put(ConstantKey.A125, semester);

	}

	// 「A126_共同具領人員(讀取主檔序號非0000之各受款人給付方式,帶入給付方式所載對應之文字)」:
	public void A126() {

		if (StringUtils.isBlank(benPayType)) {
			replaceValue.put(ConstantKey.A126, "");
			return;
		}

		if ("1".equalsIgnoreCase(benPayType)) {

			replaceValue.put(ConstantKey.A126,
					"並送交金融機構轉帳匯入申請書所載指定之帳號(如未入帳，請向02-23961266轉2212保險收支科洽詢。如有帳號錯誤，本局處理方式一律以文書通知)");
		} else if ("2".equalsIgnoreCase(benPayType)) {

			replaceValue.put(ConstantKey.A126,
					"並送交金融機構轉帳匯入申請書所載指定之郵局帳號(如未入帳，請向02-23961266轉2212保險收支科洽詢。如有帳號錯誤，本局處理方式一律以文書通知)");
		} else if ("3".equalsIgnoreCase(benPayType)) {
			replaceValue.put(ConstantKey.A126,
					"請於每月最後1日(遇假日順延)本人親自攜帶本核定通知書、原申請書所用之印章及國民身分證至本局一樓土地銀行(上班時間上午9時30分至下午3時30分)領取。");
		} else if ("4".equalsIgnoreCase(benPayType)) {
			replaceValue.put(ConstantKey.A126, "檢附郵政匯票乙紙，請 查收。");
		} else if ("5".equalsIgnoreCase(benPayType)) {
			replaceValue.put(ConstantKey.A126,
					"並送交金融機構於近日內轉帳匯入申請書所載指定之帳號(如未入帳，請向02-23961266轉2212保險收支科洽詢。如有帳號錯誤，本局處理方式一律以文書通知)");
		} else if ("6".equalsIgnoreCase(benPayType)) {
			replaceValue.put(ConstantKey.A126,
					"並送交金融機構轉帳匯入申請書所載指定之帳號(如未入帳，請向02-23961266轉2212保險收支科洽詢。如有帳號錯誤，本局處理方式一律以文書通知)");
		} else if ("7".equalsIgnoreCase(benPayType)) {
			replaceValue.put(ConstantKey.A126, "並送交金融機構轉帳匯入 台端指定之帳號。");
		} else if ("8".equalsIgnoreCase(benPayType)) {
			replaceValue.put(ConstantKey.A126, "並送交金融機構轉帳匯入 台端指定之帳號。");
		} else if ("9".equalsIgnoreCase(benPayType)) {
			replaceValue.put(ConstantKey.A126, "檢附土地銀行支票乙紙，請 查收。");
		} else if ("A".equalsIgnoreCase(benPayType)) {
			replaceValue.put(ConstantKey.A126,
					"並由土地銀行台北銀行開立支票寄送台端收領，台端可於收受支票後親至土地銀行台北分行兌領現金或以委任取款方式至受委任人開立帳戶之銀行辦理兌領手續。");
		} else {
			replaceValue.put(ConstantKey.A126, "");
		}
	}

	// 職災補償一次金金額 <A014>*10（修改時需同時確認A014、A026）
	public void A127() {
		String insAvgAmt = "0";

		if (baappexpand != null && ("1".equalsIgnoreCase(baappexpand.getEvAppTyp())
				|| "2".equalsIgnoreCase(baappexpand.getEvAppTyp()))) {
			if (avgWageA014 != null && avgWageA014.intValue() > 0) {
				insAvgAmt = DF.format(avgWageA014.intValue() * 10);
				replaceValue.put(ConstantKey.A127, insAvgAmt);
			} else {
				replaceValue.put(ConstantKey.A127, "0");
			}
		} else {
			replaceValue.put(ConstantKey.A127, "0");
		}
	}

	// 失能、遺屬年金：職災補償一次金說明
	public void A128() {
		String A128String = "";
		String sIssuYm = "";
		String insAvgAmt = "0";
		String insAvgAmt10Months = "0";

		if (baappexpand != null
				&& ("1".equalsIgnoreCase(baappexpand.getEvTyp()) || "2".equalsIgnoreCase(baappexpand.getEvTyp()))) {

			if (baappbase != null) {
				sIssuYm = DateUtility
						.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(baappbase.getIssuYm()), true);
			}
			if (avgWageA014 != null) {
				insAvgAmt = DF.format(avgWageA014.intValue());
				insAvgAmt10Months = DF.format(avgWageA014.intValue() * 10);
			}

			A128String = "至職業災害死亡補償一次金，按被保險人死亡當月起前6個月之平均月投保薪資" + insAvgAmt + "元計算，發給10個月計" + insAvgAmt10Months + "元，亦將於"
					+ sIssuYm + "底匯入申請書所載指定之帳戶。";
			replaceValue.put(ConstantKey.A128, A128String);
		} else {
			replaceValue.put(ConstantKey.A128, A128String);
		}
	}

	// 傷病分類為1或2時顯示：「第64條、」
	public void A129() {
		if (baappexpand != null
				&& ("1".equalsIgnoreCase(baappexpand.getEvTyp()) || "2".equalsIgnoreCase(baappexpand.getEvTyp()))) {
			replaceValue.put(ConstantKey.A129, "、第64條");
		} else {
			replaceValue.put(ConstantKey.A129, "");
		}
	}

	// 遺屬年金之「符合規定說明」 「傷病分類」欄：「1」或「2」顯示「職業災害及請領遺屬年金規定」；「3」或「4」顯示「請領規定」
	public void A130() {
		if (baappexpand != null
				&& ("1".equalsIgnoreCase(baappexpand.getEvTyp()) || "2".equalsIgnoreCase(baappexpand.getEvTyp()))) {
			replaceValue.put(ConstantKey.A130, "職業災害及請領遺屬年金規定");
		} else if (baappexpand != null
				&& ("3".equalsIgnoreCase(baappexpand.getEvTyp()) || "4".equalsIgnoreCase(baappexpand.getEvTyp()))) {
			replaceValue.put(ConstantKey.A130, "請領規定");
		} else {
			replaceValue.put(ConstantKey.A130, "");
		}
	}

	// 死亡次月：死亡日期 (BAAPPBASE. EVTDIEDATE) 之年月+1個月，以民國年顯示○○○年○○月
	public void A131() {

		if (baappbase != null) {
			String nextMonth = "";

			if (StringUtils.isNotBlank(baappbase.getEvtDieDate())) {
				nextMonth = DateUtility.calMonth(baappbase.getEvtDieDate().substring(0, 6) + "01", 1);
				nextMonth = DateUtility.changeDateType(nextMonth);

				replaceValue.put(ConstantKey.A131,
						nextMonth.substring(0, 3) + " 年 " + nextMonth.substring(3, 5) + " 月");
			} else {
				replaceValue.put(ConstantKey.A131, "");
			}
		} else {
			replaceValue.put(ConstantKey.A131, "");
		}
	}

	// 核定年月總實付金額：核定年月核定總額<A046>+核定年月補發部分金額加總<A048>
	public void A132() {
		int sumIssueAmt = 0;
		int sumSupAmt = 0;

		if (badaprTotal != null) {
			if (badaprTotal.get("SUMISSUEAMT") != null) {
				sumIssueAmt = ((BigDecimal) badaprTotal.get("SUMISSUEAMT")).intValue();
			}
		}

		if (badaprSup != null) {
			if (badaprSup.containsKey("SUMSUPAMT")) {
				sumSupAmt = ((BigDecimal) badaprSup.get("SUMSUPAMT")).intValue();
			}
		}

		replaceValue.put(ConstantKey.A132, DF.format(sumIssueAmt + sumSupAmt));
	}

	// 核定年月最大給付年月之核定金額 BADAPR. ISSUEAMT
	public void A133() {
		if (badaprA133 != null && badaprA133.getIssueAmt() != null) {

			replaceValue.put(ConstantKey.A133, DF.format(badaprA133.getIssueAmt()));
		} else if (badaprA133 != null) {
			replaceValue.put(ConstantKey.A133, "0");
		} else {
			replaceValue.put(ConstantKey.A133, "0");
		}
	}

	// 本次紓困抵銷金額：核定檔「本次抵銷金額」欄 BADAPR.OFFSETAMT
	public void A134() {
		if (badapr != null && badapr.getOffsetAmt() != null) {
			replaceValue.put(ConstantKey.A134, DF.format(badapr.getOffsetAmt().intValue()));
		} else if (badapr != null) {
			replaceValue.put(ConstantKey.A134, "0");
		} else {
			replaceValue.put(ConstantKey.A134, "0");
		}
	}

	// 單月抵銷紓困後實發金額：核定年月最大給付年月之「核定金額」欄-「本次抵銷金額」欄
	public void A135() {
		if (badaprA133 != null && badaprA133.getBefIssueAmt() != null) {

			replaceValue.put(ConstantKey.A135,
					DF.format(badaprA133.getBefIssueAmt().intValue() - badaprA133.getOffsetAmt().intValue()));
		} else if (badaprA133 != null) {
			replaceValue.put(ConstantKey.A135, "0");
		} else {
			replaceValue.put(ConstantKey.A135, "0");
		}
	}

	// 「首次發放起月」欄 BAAPPBASE.PAYYMS
	public void A136() {
		if (baappbase != null) {
			String payYms = DateUtility.changeDateType(baappbase.getPayYms() + "01");
			if (!payYms.equals("")) {
				replaceValue.put(ConstantKey.A136, payYms.substring(0, 3) + " 年 " + payYms.substring(3, 5) + " 月");
			} else {
				replaceValue.put(ConstantKey.A136, "");
			}
		} else {
			replaceValue.put(ConstantKey.A136, "");
		}
	}

	public void A137() {
		if (StringUtils.isNotBlank(chkResultA137)) {
			if (baappbase != null) {
				if (StringUtils.isNotBlank(baappbase.getEvtDieDate())) {
					replaceValue.put(ConstantKey.A137,
							"查被保險人生前有雇主為其提繳新制勞工退休金（勞退個人專戶），請依勞工退休金條例規定，由當序順位遺屬（同勞保本人死亡給付請領順位）於其死亡之翌日起10年內向本局提出申請；如有拋棄繼承，應由其餘順位遺屬請領之。如有申請疑義請電02-23961266轉分機5099洽詢。");
				} else {
					replaceValue.put(ConstantKey.A137,
							"94年7月以後有雇主為被保險人提繳新制勞工退休金，現已符合請領條件，如被保險人欲請領勞工退休金，請另填寫勞工退休金申請書向本局提出申請。如有申請疑義請電02-23961266轉5099分機洽詢。");
				}
			} else {
				replaceValue.put(ConstantKey.A137, "");
			}
		} else {
			replaceValue.put(ConstantKey.A137, "");
		}
	}

	// 本次代扣補償金額：核定檔「本次代扣補償金額(老年)」欄 BADAPR.COMPENAMT
	public void A138() {
		if (badapr != null && badapr.getCompenAmt() != null) {
			replaceValue.put(ConstantKey.A138, DF.format(badapr.getCompenAmt().intValue()));
		} else if (badapr != null) {
			replaceValue.put(ConstantKey.A138, "0");
		} else {
			replaceValue.put(ConstantKey.A138, "0");
		}
	}

	// 代扣補償金後之實付金額：該核定年月中，各給付年月受款人序0000可領金額之總和
	public void A139() {
		if (badaprA139 != null) {
			int aumAplpayAmt = 0;
			if (badaprA139.get("SUMAPLPAYAMT") != null) {
				aumAplpayAmt = ((BigDecimal) badaprA139.get("SUMAPLPAYAMT")).intValue();
			}
			replaceValue.put(ConstantKey.A139, DF.format(aumAplpayAmt));
		} else {
			replaceValue.put(ConstantKey.A139, "");
		}
	}

	// 核定年月最小給付年月總核定金額 BADAPR.BEFISSUEAMT
	public void A140() {
		if (badapr != null && badapr.getBefIssueAmt() != null) {

			replaceValue.put(ConstantKey.A140, DF.format(badapr.getBefIssueAmt()));
		} else if (badapr != null) {
			replaceValue.put(ConstantKey.A140, "0");
		} else {
			replaceValue.put(ConstantKey.A140, "0");
		}
	}

	// 帶入該核定年月最小給付年月之補發金額
	public void A141() {

		String supAmt = "0";

		if (badaprSupAmtA141 != null && badaprSupAmtA141.intValue() > 0) {
			supAmt = DF.format(badaprSupAmtA141.intValue());
			replaceValue.put(ConstantKey.A141, supAmt);
		} else {
			replaceValue.put(ConstantKey.A141, "0");
		}

	}

	public static BigDecimal trimToZero(BigDecimal value) {
		if (value == null) {
			return BigDecimal.ZERO;
		} else {
			return value;
		}
	}

	// 身分證明文件補正年月
	public void A142() {

		if (baappbase != null) {
			// String correctionYm = DateUtility.calMonth(baappbase.getIssuYm() + "01", 1);
			String correctionMonth = baappbase.getIssuYm().substring(4, baappbase.getIssuYm().length());
			String correctionYm = String.valueOf((Integer.parseInt(baappbase.getIssuYm().substring(0, 4)) + 1))
					+ correctionMonth;
			replaceValue.put(ConstantKey.A142, DateUtility.formatChineseYearMonthString(
					DateUtility.changeWestYearMonthType(StringUtils.substring(correctionYm, 0, 6)), true));
		} else {
			replaceValue.put(ConstantKey.A142, "");
		}

	}

	// 本月核定合格及不合格人數
	public void A143() {

		if (peopleNumberA143 != null && peopleNumberA143.intValue() > 0) {
			replaceValue.put(ConstantKey.A143, peopleNumberA143.toString());
		} else {
			replaceValue.put(ConstantKey.A143, "0");
		}

		/**
		 * int iUnqualifiedPeople = 0; int iQualifiedPeople = 0; int
		 * iUnqualifiedDeadPeople = 0; int iTotalPeople = 0;
		 *
		 * if (baappbasePrintList != null) {
		 *
		 * if (baappbasePrintList.size() > 0) {
		 *
		 * for (int i = 0; i < baappbasePrintList.size(); i++) { if
		 * (baappbasePrintList.get(i).getCloseCause().equals("") &&
		 * baappbasePrintList.get(i).getUnqualifiedCause().equals("")) {
		 * iQualifiedPeople = iQualifiedPeople + 1; } else
		 * if(!baappbasePrintList.get(i).getUnqualifiedCause().equals("")){
		 * iUnqualifiedPeople = iUnqualifiedPeople + 1; } else if
		 * (!baappbasePrintList.get(i).getUnqualifiedCause().equals("") &&
		 * !baappbasePrintList.get(i).getBenDieDate().equals("")){
		 * iUnqualifiedDeadPeople = iUnqualifiedDeadPeople + 1; } }
		 *
		 * iTotalPeople = iQualifiedPeople + iUnqualifiedPeople -
		 * iUnqualifiedDeadPeople;
		 *
		 * replaceValue.put(ConstantKey.A143, String.valueOf(iTotalPeople)); } else {
		 * replaceValue.put(ConstantKey.A143, "0"); } } else {
		 * replaceValue.put(ConstantKey.A143, "0"); }
		 */

	}

	// 合格遺屬基本資料
	public void A144() {

		if (baappbasePrintList != null) {

			if (baappbasePrintList.size() > 0) {
				String A144Sting = "";
				for (int i = 0; i < baappbasePrintList.size(); i++) {
					if (baappbasePrintList.get(i).getCloseCause().equals("")
							&& baappbasePrintList.get(i).getUnqualifiedCause().equals("")
							&& baappbasePrintList.get(i).getBenDieDate().equals("")) {
						if (!baappbasePrintList.get(i).getSeqNo().equals("0000")
								&& baappbasePrintList.get(i).getSeqNo().substring(2, 4).equals("00")
								&& i != baappbasePrintList.size() - 1) {
							if (StringUtils.isNotBlank(baappbasePrintList.get(i).getGrdName())) {
								// A144Sting = A144Sting + "姓名：" + baappbasePrintList.get(i).getBenName() +
								// "，身分證統一編號：" + (baappbasePrintList.get(i).getBenIdnNo().length() >= 10 ?
								// baappbasePrintList.get(i).getBenIdnNo().substring(0, 6) : "") + "****" +
								// "，出生日期：" +
								// DateUtility.formatChineseDateTimeString(baappbasePrintList.get(i).getBenBrDate(),
								// true) + "，法定代理人：" + baappbasePrintList.get(i).getGrdName() + "；";
								A144Sting = A144Sting + baappbasePrintList.get(i).getBenName() + "，身分證統一編號："
										+ (baappbasePrintList.get(i).getBenIdnNo().length() >= 10
												? baappbasePrintList.get(i).getBenIdnNo().substring(0, 6)
												: "")
										+ "****" + "，出生日期："
										+ DateUtility.formatChineseDateTimeString(
												baappbasePrintList.get(i).getBenBrDate(), true)
										+ "，法定代理人：" + baappbasePrintList.get(i).getGrdName() + "；";
							} else {
								// A144Sting = A144Sting + "姓名：" + baappbasePrintList.get(i).getBenName() +
								// "，身分證統一編號：" + (baappbasePrintList.get(i).getBenIdnNo().length() >= 10 ?
								// baappbasePrintList.get(i).getBenIdnNo().substring(0, 6) : "") + "****" +
								// "，出生日期：" +
								// DateUtility.formatChineseDateTimeString(baappbasePrintList.get(i).getBenBrDate(),
								// true) + "；";
								A144Sting = A144Sting + baappbasePrintList.get(i).getBenName() + "，身分證統一編號："
										+ (baappbasePrintList.get(i).getBenIdnNo().length() >= 10
												? baappbasePrintList.get(i).getBenIdnNo().substring(0, 6)
												: "")
										+ "****" + "，出生日期：" + DateUtility.formatChineseDateTimeString(
												baappbasePrintList.get(i).getBenBrDate(), true)
										+ "；";
							}
						} else if (!baappbasePrintList.get(i).getSeqNo().equals("0000")
								&& baappbasePrintList.get(i).getSeqNo().substring(2, 4).equals("00")
								&& i == baappbasePrintList.size() - 1) {
							if (StringUtils.isNotBlank(baappbasePrintList.get(i).getGrdName())) {
								// A144Sting = A144Sting + "姓名：" + baappbasePrintList.get(i).getBenName() +
								// "，身分證統一編號：" + (baappbasePrintList.get(i).getBenIdnNo().length() >= 10 ?
								// baappbasePrintList.get(i).getBenIdnNo().substring(0, 6) : "") + "****" +
								// "，出生日期：" +
								// DateUtility.formatChineseDateTimeString(baappbasePrintList.get(i).getBenBrDate(),
								// true) + "，法定代理人：" + baappbasePrintList.get(i).getGrdName();
								A144Sting = A144Sting + baappbasePrintList.get(i).getBenName() + "，身分證統一編號："
										+ (baappbasePrintList.get(i).getBenIdnNo().length() >= 10
												? baappbasePrintList.get(i).getBenIdnNo().substring(0, 6)
												: "")
										+ "****" + "，出生日期："
										+ DateUtility.formatChineseDateTimeString(
												baappbasePrintList.get(i).getBenBrDate(), true)
										+ "，法定代理人：" + baappbasePrintList.get(i).getGrdName();
							} else {
								// A144Sting = A144Sting + "姓名：" + baappbasePrintList.get(i).getBenName() +
								// "，身分證統一編號：" + (baappbasePrintList.get(i).getBenIdnNo().length() >= 10 ?
								// baappbasePrintList.get(i).getBenIdnNo().substring(0, 6) : "") + "****" +
								// "，出生日期：" +
								// DateUtility.formatChineseDateTimeString(baappbasePrintList.get(i).getBenBrDate(),
								// true);
								A144Sting = A144Sting + baappbasePrintList.get(i).getBenName() + "，身分證統一編號："
										+ (baappbasePrintList.get(i).getBenIdnNo().length() >= 10
												? baappbasePrintList.get(i).getBenIdnNo().substring(0, 6)
												: "")
										+ "****" + "，出生日期：" + DateUtility.formatChineseDateTimeString(
												baappbasePrintList.get(i).getBenBrDate(), true);
							}
						}
					}
				}

				if (A144Sting.length() > 0) {
					int iLocation = A144Sting.lastIndexOf("；");
					if (iLocation > 0 && iLocation == A144Sting.length() - 1) {
						A144Sting = A144Sting.substring(0, iLocation);
					}
				}

				replaceValue.put(ConstantKey.A144, A144Sting);
			} else {
				replaceValue.put(ConstantKey.A144, "");
			}
		} else {
			replaceValue.put(ConstantKey.A144, "");
		}
	}

	// 不合格遺屬基本資料
	public void A145() {

		if (baappbasePrintList != null) {

			if (baappbasePrintList.size() > 0) {
				String A145Sting = "";
				for (int i = 0; i < baappbasePrintList.size(); i++) {
					if (!baappbasePrintList.get(i).getUnqualifiedCause().equals("")
							&& baappbasePrintList.get(i).getBenDieDate().equals("")) {
						if (!baappbasePrintList.get(i).getSeqNo().equals("0000")
								&& baappbasePrintList.get(i).getSeqNo().substring(2, 4).equals("00")
								&& i != baappbasePrintList.size() - 1) {
							if (StringUtils.isNotBlank(baappbasePrintList.get(i).getGrdName())) {
								// A145Sting = A145Sting + "姓名：" + baappbasePrintList.get(i).getBenName() +
								// "，身分證統一編號：" + (baappbasePrintList.get(i).getBenIdnNo().length() >= 10 ?
								// baappbasePrintList.get(i).getBenIdnNo().substring(0, 6) : "") + "****" +
								// "，出生日期：" +
								// DateUtility.formatChineseDateTimeString(baappbasePrintList.get(i).getBenBrDate(),
								// true) + "，法定代理人：" + baappbasePrintList.get(i).getGrdName() + "；";
								A145Sting = A145Sting + baappbasePrintList.get(i).getBenName() + "，身分證統一編號："
										+ (baappbasePrintList.get(i).getBenIdnNo().length() >= 10
												? baappbasePrintList.get(i).getBenIdnNo().substring(0, 6)
												: "")
										+ "****" + "，出生日期："
										+ DateUtility.formatChineseDateTimeString(
												baappbasePrintList.get(i).getBenBrDate(), true)
										+ "，法定代理人：" + baappbasePrintList.get(i).getGrdName() + "；";
							} else {
								// A145Sting = A145Sting + "姓名：" + baappbasePrintList.get(i).getBenName() +
								// "，身分證統一編號：" + (baappbasePrintList.get(i).getBenIdnNo().length() >= 10 ?
								// baappbasePrintList.get(i).getBenIdnNo().substring(0, 6) : "") + "****" +
								// "，出生日期：" +
								// DateUtility.formatChineseDateTimeString(baappbasePrintList.get(i).getBenBrDate(),
								// true) + "；";
								A145Sting = A145Sting + baappbasePrintList.get(i).getBenName() + "，身分證統一編號："
										+ (baappbasePrintList.get(i).getBenIdnNo().length() >= 10
												? baappbasePrintList.get(i).getBenIdnNo().substring(0, 6)
												: "")
										+ "****" + "，出生日期：" + DateUtility.formatChineseDateTimeString(
												baappbasePrintList.get(i).getBenBrDate(), true)
										+ "；";
							}
						} else if (!baappbasePrintList.get(i).getSeqNo().equals("0000")
								&& baappbasePrintList.get(i).getSeqNo().substring(2, 4).equals("00")
								&& i == baappbasePrintList.size() - 1) {
							if (StringUtils.isNotBlank(baappbasePrintList.get(i).getGrdName())) {
								// A145Sting = A145Sting + "姓名：" + baappbasePrintList.get(i).getBenName() +
								// "，身分證統一編號：" + (baappbasePrintList.get(i).getBenIdnNo().length() >= 10 ?
								// baappbasePrintList.get(i).getBenIdnNo().substring(0, 6) : "") + "****" +
								// "，出生日期：" +
								// DateUtility.formatChineseDateTimeString(baappbasePrintList.get(i).getBenBrDate(),
								// true) + "，法定代理人：" + baappbasePrintList.get(i).getGrdName();
								A145Sting = A145Sting + baappbasePrintList.get(i).getBenName() + "，身分證統一編號："
										+ (baappbasePrintList.get(i).getBenIdnNo().length() >= 10
												? baappbasePrintList.get(i).getBenIdnNo().substring(0, 6)
												: "")
										+ "****" + "，出生日期："
										+ DateUtility.formatChineseDateTimeString(
												baappbasePrintList.get(i).getBenBrDate(), true)
										+ "，法定代理人：" + baappbasePrintList.get(i).getGrdName();
							} else {
								// A145Sting = A145Sting + "姓名：" + baappbasePrintList.get(i).getBenName() +
								// "，身分證統一編號：" + (baappbasePrintList.get(i).getBenIdnNo().length() >= 10 ?
								// baappbasePrintList.get(i).getBenIdnNo().substring(0, 6) : "") + "****" +
								// "，出生日期：" +
								// DateUtility.formatChineseDateTimeString(baappbasePrintList.get(i).getBenBrDate(),
								// true);
								A145Sting = A145Sting + baappbasePrintList.get(i).getBenName() + "，身分證統一編號："
										+ (baappbasePrintList.get(i).getBenIdnNo().length() >= 10
												? baappbasePrintList.get(i).getBenIdnNo().substring(0, 6)
												: "")
										+ "****" + "，出生日期：" + DateUtility.formatChineseDateTimeString(
												baappbasePrintList.get(i).getBenBrDate(), true);
							}
						}
					}
				}

				if (A145Sting.length() > 0) {
					int iLocation = A145Sting.lastIndexOf("；");
					if (iLocation > 0 && iLocation == A145Sting.length() - 1) {
						A145Sting = A145Sting.substring(0, iLocation);
					}
				}

				replaceValue.put(ConstantKey.A145, A145Sting);
			} else {
				replaceValue.put(ConstantKey.A145, "");
			}
		} else {
			replaceValue.put(ConstantKey.A145, "");
		}
	}

	// 合格及不合格遺屬基本資料
	public void A146() {

		if (baappbasePrintList != null) {

			if (baappbasePrintList.size() > 0) {
				String A146Sting = "";
				for (int i = 0; i < baappbasePrintList.size(); i++) {
					if (!baappbasePrintList.get(i).getSeqNo().equals("0000")
							&& baappbasePrintList.get(i).getBenDieDate().equals("")
							&& baappbasePrintList.get(i).getSeqNo().substring(2, 4).equals("00")
							&& i != baappbasePrintList.size() - 1) {
						if (StringUtils.isNotBlank(baappbasePrintList.get(i).getGrdName())) {
							// A146Sting = A146Sting + "姓名：" + baappbasePrintList.get(i).getBenName() +
							// "，身分證統一編號：" + (baappbasePrintList.get(i).getBenIdnNo().length() >= 10 ?
							// baappbasePrintList.get(i).getBenIdnNo().substring(0, 6) : "") + "****" +
							// "，出生日期：" +
							// DateUtility.formatChineseDateTimeString(baappbasePrintList.get(i).getBenBrDate(),
							// true) + "，法定代理人：" + baappbasePrintList.get(i).getGrdName() + "；";
							A146Sting = A146Sting + baappbasePrintList.get(i).getBenName() + "，身分證統一編號："
									+ (baappbasePrintList.get(i).getBenIdnNo().length() >= 10
											? baappbasePrintList.get(i).getBenIdnNo().substring(0, 6)
											: "")
									+ "****" + "，出生日期：" + DateUtility
											.formatChineseDateTimeString(baappbasePrintList.get(i).getBenBrDate(), true)
									+ "，法定代理人：" + baappbasePrintList.get(i).getGrdName() + "；";
						} else {
							// A146Sting = A146Sting + "姓名：" + baappbasePrintList.get(i).getBenName() +
							// "，身分證統一編號：" + (baappbasePrintList.get(i).getBenIdnNo().length() >= 10 ?
							// baappbasePrintList.get(i).getBenIdnNo().substring(0, 6) : "") + "****" +
							// "，出生日期：" +
							// DateUtility.formatChineseDateTimeString(baappbasePrintList.get(i).getBenBrDate(),
							// true) + "；";
							A146Sting = A146Sting + baappbasePrintList.get(i).getBenName() + "，身分證統一編號："
									+ (baappbasePrintList.get(i).getBenIdnNo().length() >= 10
											? baappbasePrintList.get(i).getBenIdnNo().substring(0, 6)
											: "")
									+ "****" + "，出生日期：" + DateUtility.formatChineseDateTimeString(
											baappbasePrintList.get(i).getBenBrDate(), true)
									+ "；";
						}
					} else if (!baappbasePrintList.get(i).getSeqNo().equals("0000")
							&& baappbasePrintList.get(i).getBenDieDate().equals("")
							&& baappbasePrintList.get(i).getSeqNo().substring(2, 4).equals("00")
							&& i == baappbasePrintList.size() - 1) {
						if (StringUtils.isNotBlank(baappbasePrintList.get(i).getGrdName())) {
							// A146Sting = A146Sting + "姓名：" + baappbasePrintList.get(i).getBenName() +
							// "，身分證統一編號：" + (baappbasePrintList.get(i).getBenIdnNo().length() >= 10 ?
							// baappbasePrintList.get(i).getBenIdnNo().substring(0, 6) : "") + "****" +
							// "，出生日期：" +
							// DateUtility.formatChineseDateTimeString(baappbasePrintList.get(i).getBenBrDate(),
							// true) + "，法定代理人：" + baappbasePrintList.get(i).getGrdName();
							A146Sting = A146Sting + baappbasePrintList.get(i).getBenName() + "，身分證統一編號："
									+ (baappbasePrintList.get(i).getBenIdnNo().length() >= 10
											? baappbasePrintList.get(i).getBenIdnNo().substring(0, 6)
											: "")
									+ "****" + "，出生日期：" + DateUtility
											.formatChineseDateTimeString(baappbasePrintList.get(i).getBenBrDate(), true)
									+ "，法定代理人：" + baappbasePrintList.get(i).getGrdName();
						} else {
							// A146Sting = A146Sting + "姓名：" + baappbasePrintList.get(i).getBenName() +
							// "，身分證統一編號：" + (baappbasePrintList.get(i).getBenIdnNo().length() >= 10 ?
							// baappbasePrintList.get(i).getBenIdnNo().substring(0, 6) : "") + "****" +
							// "，出生日期：" +
							// DateUtility.formatChineseDateTimeString(baappbasePrintList.get(i).getBenBrDate(),
							// true);
							A146Sting = A146Sting + baappbasePrintList.get(i).getBenName() + "，身分證統一編號："
									+ (baappbasePrintList.get(i).getBenIdnNo().length() >= 10
											? baappbasePrintList.get(i).getBenIdnNo().substring(0, 6)
											: "")
									+ "****" + "，出生日期：" + DateUtility.formatChineseDateTimeString(
											baappbasePrintList.get(i).getBenBrDate(), true);
						}
					}
				}

				if (A146Sting.length() > 0) {
					int iLocation = A146Sting.lastIndexOf("；");
					if (iLocation > 0 && iLocation == A146Sting.length() - 1) {
						A146Sting = A146Sting.substring(0, iLocation);
					}
				}

				replaceValue.put(ConstantKey.A146, A146Sting);

			} else {
				replaceValue.put(ConstantKey.A146, "");
			}

		} else {
			replaceValue.put(ConstantKey.A146, "");
		}

	}

	// 最大給付年月
	public void A147() {
		if (badaprTotal != null) {
			String maxPayYm = DateUtility.changeDateType(badaprTotal.get("MAXPAYYM") + "01");
			replaceValue.put(ConstantKey.A147, maxPayYm.substring(0, 3) + " 年 " + maxPayYm.substring(3, 5) + " 月");
		} else {
			replaceValue.put(ConstantKey.A147, "000年00月");
		}
	}

	// 最大給付年月前一個月
	public void A148() {
		if (badaprTotal != null) {
			String minPayYm = DateUtility.changeDateType(badaprTotal.get("PREYM") + "01");
			replaceValue.put(ConstantKey.A148, minPayYm.substring(0, 3) + " 年 " + minPayYm.substring(3, 5) + " 月");
		} else {
			replaceValue.put(ConstantKey.A148, "000年00月");
		}
	}
	//成年之年月
	public void A172(String unqualifiedCause) {
		for (int i = 0; i < baappbasePrintListA150.size(); i++) {
			Baappbase baappbaseA150 = baappbasePrintListA150.get(i);
			if (baappbaseA150.getUnqualifiedCause().equals(unqualifiedCause) && baappbaseA150.getBenDieDate().equals("")) {
				if (!"".equals(baappbaseA150.getEvtJobDate()) && !"".equals(baappbaseA150.getBenBrDate())) {
					String A172Ym = "";
					if (NumberUtils.toInt(baappbaseA150.getEvtJobDate()) < DATE_20230101 && NumberUtils.toInt(baappbaseA150.getBenBrDate()) < DATE_20230101 ) {
						A172Ym = DateUtility.changeDateType(DateUtility.calYear(baappbaseA150.getBenBrDate(), 20));// 遺屬滿20歲日期
					}else {
						A172Ym = DateUtility.changeDateType(DateUtility.calYear(baappbaseA150.getBenBrDate(), 18));// 遺屬滿18歲日期
					}
					replaceValue.put(ConstantKey.A172, A172Ym.substring(0, 3) + " 年 " + A172Ym.substring(3, 5) + " 月");
				}
				break;
			}
		}
	}
	
	//成年之次月
	public void A173(String unqualifiedCause) {
		for (int i = 0; i < baappbasePrintListA150.size(); i++) {
			Baappbase baappbaseA150 = baappbasePrintListA150.get(i);
			if (baappbaseA150.getUnqualifiedCause().equals(unqualifiedCause) && baappbaseA150.getBenDieDate().equals("")) {
				if (!"".equals(baappbaseA150.getEvtJobDate()) && !"".equals(baappbaseA150.getBenBrDate())) {
					String A173Ym = "";
					if (NumberUtils.toInt(baappbaseA150.getEvtJobDate()) < DATE_20230101 && NumberUtils.toInt(baappbaseA150.getBenBrDate()) < DATE_20230101 ) {
						A173Ym = DateUtility.changeDateType(DateUtility.calMonth(DateUtility.calYear(baappbaseA150.getBenBrDate(), 20), 1));
					} else {
						A173Ym = DateUtility.changeDateType(DateUtility.calMonth(DateUtility.calYear(baappbaseA150.getBenBrDate(), 18), 1));
					}
					replaceValue.put(ConstantKey.A173, A173Ym.substring(0, 3) + " 年 " + A173Ym.substring(3, 5) + " 月");
				}
				break;
			}
		}
	}
	
	//成年年齡
	//一、事故日期 < 1120101且遺屬出生日期 < 1120101：顯示「20」。
	//二、符合事故日期 >= 1120101或符合事故日期 < 1120101且遺屬出生日期 >= 1120101：顯示「18」。
	public void A184(String unqualifiedCause) {
		String adultAge = "18";
		for (int i = 0; i < baappbasePrintListA150.size(); i++) {
			Baappbase baappbaseA150 = baappbasePrintListA150.get(i);
		    if (baappbaseA150.getUnqualifiedCause().equals(unqualifiedCause) && baappbaseA150.getBenDieDate().equals("")) {
		    	if (!"".equals(baappbaseA150.getEvtJobDate()) && !"".equals(baappbaseA150.getBenBrDate())) {
					if (NumberUtils.toInt(baappbaseA150.getEvtJobDate()) < DATE_20230101 && NumberUtils.toInt(baappbaseA150.getBenBrDate()) < DATE_20230101 ) {
						adultAge = "20";
					} else if (NumberUtils.toInt(baappbaseA150.getEvtJobDate()) >= DATE_20230101 ||
							(NumberUtils.toInt(baappbaseA150.getEvtJobDate()) < DATE_20230101 && NumberUtils.toInt(baappbaseA150.getBenBrDate()) >= DATE_20230101)) {
						adultAge = "18";
					}
		    	}
				break;
			}
		}
		replaceValue.put(ConstantKey.A184, adultAge);
	}

	//不合格者仍在學，無需審議文句
	//遺屬所選的不合格原因代碼有01、05、06、11、12、18、20其中之一時
	public void A185() {
		List<String> unqualifiedCauseList = Arrays.asList(new String[]{"01", "05", "06", "11", "12", "18", "20"});
		if (baappbasePrintList != null) {

			if (baappbasePrintList.size() > 0) {
				String A185String = "";
				ArrayList<String> sNameList = new ArrayList<String>();

				for (int i = 0; i < baappbasePrintList.size(); i++) {
					if (!baappbasePrintList.get(i).getSeqNo().equals("0000")
							&& unqualifiedCauseList.contains(baappbasePrintList.get(i).getUnqualifiedCause())
							&& baappbasePrintList.get(i).getBenDieDate().equals("")) {
						sNameList.add(baappbasePrintList.get(i).getBenName());
					}
				}

				for (int i = 0; i < sNameList.size(); i++) {
					if (i != sNameList.size() - 1 && i != sNameList.size() - 2) {
						A185String = A185String + sNameList.get(i) + "、";
					} else if (i == sNameList.size() - 2 && i != sNameList.size() - 1) {
						A185String = A185String + sNameList.get(i) + "及";
					} else if (i == sNameList.size() - 1) {
						A185String = A185String + sNameList.get(i);
					}
				}

				if (sNameList.size() > 0) {
					replaceValue.put(ConstantKey.A185, "【註：倘" + A185String + "仍在學，請檢具在學證明送局憑辦，註明「補件」及填寫受理編號，「無須」填具勞工保險爭議事項審議申請書】");
				} else {
					replaceValue.put(ConstantKey.A185, "");
				}
			} else {
				replaceValue.put(ConstantKey.A185, "");
			}
		} else {
			replaceValue.put(ConstantKey.A185, "");
		}

	}

	// 投保薪資分級表第一級
	public void A149() {

		String minWage = "0";

		if (minWageA149 != null && minWageA149.intValue() > 0) {
			minWage = DF.format(minWageA149.intValue());
			replaceValue.put(ConstantKey.A149, minWage);
		} else {
			replaceValue.put(ConstantKey.A149, "0");
		}

	}

	// 不合格說明
	public void A150() {
		String A150String = "";
		String A150String_1 = "";
		String A150String_2 = "";
		String A150String_3 = "";
		String A150String_4 = "";
		String A150String_5 = "";
		String A150String_6 = "";
		String A150String_7 = "";
		String A150String_8 = "";
		String A150String_9 = "";
		String A150String_10 = "";
		String A150String_11 = "";
		String A150String_12 = "";
		String A150String_13 = "";
		String A150String_14 = "";
		String A150String_15 = "";
		String A150String_16 = "";
		String A150String_17 = "";
		String A150String_18 = "";
		String A150String_23 = "";
		String A150String_24 = "";
		String maxPayYm = "";
		String minPayYm = "";
		String minWage = "0";
		int cnt = 0;
		String digamyDate = "";
		String benDieDate = "";
		String A003Value = StringUtils.isNotBlank(replaceValue.get(ConstantKey.A003)) ? replaceValue.get(ConstantKey.A003) : "";
		String A164Value = StringUtils.isNotBlank(replaceValue.get(ConstantKey.A164)) ? replaceValue.get(ConstantKey.A164) : "";
		String A165Value = StringUtils.isNotBlank(replaceValue.get(ConstantKey.A165)) ? replaceValue.get(ConstantKey.A165) : "";
		String A167Value = StringUtils.isNotBlank(replaceValue.get(ConstantKey.A167)) ? replaceValue.get(ConstantKey.A167) : "";
		String A168Value = StringUtils.isNotBlank(replaceValue.get(ConstantKey.A168)) ? replaceValue.get(ConstantKey.A168) : "";
		String A172Value = "";
		String A173Value = "";
		String A184Value = "";

//		BaappbaseDao baappbaseDao = (BaappbaseDao) SpringHelper.getBeanById("baappbaseDao");
		// 畢業年月、休學年月、退學年月 NBSCHOOL.STATUSDATE_E

		if (badaprTotal != null) {
			maxPayYm = DateUtility.changeDateType(badaprTotal.get("MAXPAYYM") + "01"); // A147
			maxPayYm = maxPayYm.substring(0, 3) + " 年 " + maxPayYm.substring(3, 5) + " 月";

			minPayYm = DateUtility.changeDateType(badaprTotal.get("PREYM") + "01"); // A148
			minPayYm = minPayYm.substring(0, 3) + " 年 " + minPayYm.substring(3, 5) + " 月";

		} else {
			maxPayYm = "000年00月";
			minPayYm = "000年00月";
		}

		if (minWageA149 != null && minWageA149.intValue() > 0) {
			minWage = DF.format(minWageA149.intValue());
		}

		if (baappbasePrintListA150 != null && baappbasePrintListA150.size() > 0) {

			int firstPersonDate = baappbasePrintListA150.size();

			A150String_1 = nameConcatForSchool2(baappbasePrintListA150, "01", "G", minPayYm);

//			HashMap<String, String> A150Map_1 = nameConcatForSchool(baappbasePrintListA150, "01", "G", minPayYm);
//			if (StringUtils.isNotBlank(A150Map_1.get("str"))) {
//				A150String_1 = A150Map_1.get("str") + "已於 " + A150Map_1.get("statusDate") + "畢業未在學且非無謀生能力；";
//			}

			A150String_2 = nameConcat(baappbasePrintListA150, "02");
			if (StringUtils.isNotBlank(A150String_2)) {
				A184("02");
				A184Value = StringUtils.isNotBlank(replaceValue.get(ConstantKey.A184)) ? replaceValue.get(ConstantKey.A184) : "";
				A150String_2 = A150String_2 + "年齡逾" + A184Value + "歲，工作收入超過投保薪資分級表第一級（" + minWage + "元）；";
			}

			A150String_3 = nameConcat(baappbasePrintListA150, "03");
			if (StringUtils.isNotBlank(A150String_3)) {
				A184("03");
				A184Value = StringUtils.isNotBlank(replaceValue.get(ConstantKey.A184)) ? replaceValue.get(ConstantKey.A184) : "";
				A150String_3 = A150String_3 + "年齡逾" + A184Value + "歲，" + A165Value + "及" + A164Value + "工作收入超過投保薪資分級表第一級（" + minWage + "元）；";
			}

            A150String_4 = nameConcat(baappbasePrintListA150, "04");
			if (StringUtils.isNotBlank(A150String_4)) {
				A150String_4 = A150String_4 + "年齡逾25歲且非無謀生能力；";
			}

			A150String_5 = nameConcat(baappbasePrintListA150, "05");
			if (StringUtils.isNotBlank(A150String_5)) {
				A184("05");
				A184Value = StringUtils.isNotBlank(replaceValue.get(ConstantKey.A184)) ? replaceValue.get(ConstantKey.A184) : "";
				A150String_5 = A150String_5 + "年齡逾" + A184Value + "歲，未在學且非無謀生能力；";
			}

			A150String_6 = nameConcat(baappbasePrintListA150, "06");
			if (StringUtils.isNotBlank(A150String_6)) {
				A184("06");
				A184Value = StringUtils.isNotBlank(replaceValue.get(ConstantKey.A184)) ? replaceValue.get(ConstantKey.A184) : "";
				A150String_6 = A150String_6 + "年齡逾" + A184Value + "歲，" + A165Value + "起未在學且非無謀生能力；";
			}

			A150String_7 = nameConcat(baappbasePrintListA150, "07");
			if (StringUtils.isNotBlank(A150String_7)) {
				A150String_7 = A150String_7 + "年齡未滿55歲，工作收入超過投保薪資分級表第一級（" + minWage + "元）；";
			}

			A150String_8 = nameConcat(baappbasePrintListA150, "08");
			if (StringUtils.isNotBlank(A150String_8)) {
				A150String_8 = A150String_8 + "年齡未滿55歲，" + minPayYm + "及" + maxPayYm + "工作收入超過投保薪資分級表第一級（" + minWage + "元）；";
		    }

			firstPersonDate = baappbasePrintListA150.size();
			for(int i = 0; i < baappbasePrintListA150.size(); i++) {
				if (baappbasePrintListA150.get(i).getUnqualifiedCause().equals("09") && baappbasePrintListA150.get(i).getBenDieDate().equals("")) {
					A150String_9 = A150String_9 + baappbasePrintListA150.get(i).getBenName() + "，";
					cnt++;
					if(firstPersonDate > i) {
						digamyDate = DateUtility.changeDateType(baappbasePrintListA150.get(i).getDigamyDate());
						firstPersonDate = i;
					}
				}
			}

			if (StringUtils.isNotBlank(A150String_9)) {
				A150String_9 = nameConcatFor09And10(A150String_9);
				if (digamyDate.length() == 7){
					A150String_9 = A150String_9 + "已於" + digamyDate.substring(0, 3) + " 年 " + digamyDate.substring(3, 5) + " 月" + digamyDate.substring(5, 7) + " 日" + "再婚；";
				}
				cnt = 0;
			}

			firstPersonDate = baappbasePrintListA150.size();
			for(int i = 0; i < baappbasePrintListA150.size(); i++) {
				if (baappbasePrintListA150.get(i).getUnqualifiedCause().equals("10") && !baappbasePrintListA150.get(i).getBenDieDate().equals("")) {
					A150String_10 = A150String_10 + baappbasePrintListA150.get(i).getBenName() + "，";
					cnt++;
					if(firstPersonDate > i) {
						benDieDate = DateUtility.changeDateType(baappbasePrintListA150.get(i).getBenDieDate());
						firstPersonDate = i;
					}
				}
			}

			if (StringUtils.isNotBlank(A150String_10)) {
				A150String_10 = nameConcatFor09And10(A150String_10);
				A150String_10 = A150String_10 + "已於" + benDieDate.substring(0, 3) + " 年 " + benDieDate.substring(3, 5) + " 月" + benDieDate.substring(5, 7) + " 日" + "死亡；";
			}

			A150String_11 = nameConcatForSchool2(baappbasePrintListA150, "11", "S", minPayYm);

//			HashMap<String, String> A150Map_11 = nameConcatForSchool(baappbasePrintListA150, "11", "S", minPayYm);
//			if (StringUtils.isNotBlank(A150Map_11.get("str"))) {
//				A150String_11 = A150Map_11.get("str") + "已於 " + A150Map_11.get("statusDate") + "休學且非無謀生能力；";
//			}

			A150String_12 = nameConcatForSchool2(baappbasePrintListA150, "12", "D", minPayYm);
//			HashMap<String, String> A150Map_12 = nameConcatForSchool(baappbasePrintListA150, "12", "D", minPayYm);
//			if (StringUtils.isNotBlank(A150Map_12.get("str"))) {
//				A150String_12 = A150Map_12.get("str") + "已於 " + A150Map_12.get("statusDate") + "退學且非無謀生能力；";
//			}

			A150String_13 = nameConcat(baappbasePrintListA150, "13");
			if (StringUtils.isNotBlank(A150String_13)) {
				A150String_13 = A150String_13 + "未滿55歲；";
			}

			A150String_14 = nameConcat(baappbasePrintListA150, "14");
			if (StringUtils.isNotBlank(A150String_14)) {
				A150String_14 = A150String_14 + "工作收入超過投保薪資分級表第一級（" + minWage + "元）；";
			}

			A150String_15 = nameConcat(baappbasePrintListA150, "15");
			if (StringUtils.isNotBlank(A150String_15)) {
		        A150String_15 = A150String_15 + minPayYm + "及" + maxPayYm + "工作收入超過投保薪資分級表第一級（" + minWage + "元）；";
			}

			A150String_16 = nameConcat(baappbasePrintListA150, "16");
			if (StringUtils.isNotBlank(A150String_16)) {
				A150String_16 = A150String_16 + "於" + minPayYm + "起入監服刑；";
			}

			A150String_17 = nameConcat(baappbasePrintListA150, "17");
			if (StringUtils.isNotBlank(A150String_17)) {
			    A150String_17 = A150String_17 + "於" + minPayYm + "失蹤；";
			}
			
			A150String_18 = nameConcat(baappbasePrintListA150, "18");
			if (StringUtils.isNotBlank(A150String_18)) {
				A172("18");
				A172Value = StringUtils.isNotBlank(replaceValue.get(ConstantKey.A172)) ? replaceValue.get(ConstantKey.A172) : "";
				A173("18");
				A173Value = StringUtils.isNotBlank(replaceValue.get(ConstantKey.A173)) ? replaceValue.get(ConstantKey.A173) : "";
				A184("18");
				A184Value = StringUtils.isNotBlank(replaceValue.get(ConstantKey.A184)) ? replaceValue.get(ConstantKey.A184) : "";

			    A150String_18 = A150String_18 + "於" + A172Value + "年滿" + A184Value + "歲，" + A173Value + "起未在學且非無謀生能力";
			}
			
			A150String_23 = nameConcat(baappbasePrintListA150, "23");
			if (StringUtils.isNotBlank(A150String_23)) {
				A150String_23 = A150String_23 + "與" + A003Value + "之婚姻關係存續未滿一年，且未扶養符合請領遺屬年金給付條件之子女";
			}
			
			A150String_24 = nameConcat(baappbasePrintListA150, "24");
			if (StringUtils.isNotBlank(A150String_24)) {
				A150String_24 = A150String_24 + "年齡未滿45歲，且未扶養符合請領遺屬年金給付條件之子女";
			}

			A150String = A150String_1 + A150String_2 + A150String_3 + A150String_4 + A150String_5 + A150String_6
					   + A150String_7 + A150String_8 + A150String_9 + A150String_10 + A150String_11 + A150String_12
					   + A150String_13 + A150String_14 + A150String_15 + A150String_16 + A150String_17
					   + A150String_18 + A150String_23 + A150String_24;

			if (A150String.length() > 0) {
				int iLocation = A150String.lastIndexOf("；");
				if (iLocation > 0){
					A150String = A150String.substring(0, iLocation);
				}

			}

			replaceValue.put(ConstantKey.A150, A150String);

		} else {
			replaceValue.put(ConstantKey.A150, "");
		}

	}

	// 再婚日期
	public void A151() {

		if (benData != null && benData.getDigamyDate() != null && benData.getDigamyDate().length() == 8) {

			if (benData.getBenEvtRel().equals("2") && !benData.getUnqualifiedCause().equals("")) {

				String digamyDate = DateUtility.changeDateType(benData.getDigamyDate());
				replaceValue.put(ConstantKey.A151,
						digamyDate.substring(0, 3) + " 年 " + digamyDate.substring(3, 5) + " 月");
			}
		} else {
			replaceValue.put(ConstantKey.A151, "000年00月");
		}

	}

	// 遺屬死亡日期
	public void A152() {
		if (benData != null) {
			if (!benData.getUnqualifiedCause().equals("") && !benData.getBenDieDate().equals("")) {

				String benDieDate = DateUtility.changeDateType(benData.getBenDieDate());
				replaceValue.put(ConstantKey.A152,
						benDieDate.substring(0, 3) + " 年 " + benDieDate.substring(3, 5) + " 月");
			}
		} else {
			replaceValue.put(ConstantKey.A152, "000年00月");
		}
	}

	// 無謀生能力解釋令
	public void A153() {

		// if (benData != null) {
		if (baappbasePrintList != null) {

			if (baappbasePrintList.size() > 0) {
				int iCount = 0;
				for (int i = 0; i < baappbasePrintList.size(); i++) {

					if (!baappbasePrintList.get(i).getSeqNo().equals("0000")
							&& baappbasePrintList.get(i).getBenDieDate().equals("")
							&& (baappbasePrintList.get(i).getUnqualifiedCause().equals("01")
									|| baappbasePrintList.get(i).getUnqualifiedCause().equals("04")
									|| baappbasePrintList.get(i).getUnqualifiedCause().equals("05")
									|| baappbasePrintList.get(i).getUnqualifiedCause().equals("06"))) {

						iCount = iCount + 1;
					}
				}

				if (iCount > 0) {
					replaceValue.put(ConstantKey.A153, "及改制前行政院勞工委員會97年12月25日勞保2字第0970140586號令");
				} else {
					replaceValue.put(ConstantKey.A153, "");
				}
			}
		} else {
			replaceValue.put(ConstantKey.A153, "");
		}
	}

	// 投保薪資比例金額
	public void A154() {
		if (!baappbase.getApNo().substring(0, 1).equals("L")) {
			if (benData != null && Integer.parseInt(StringUtils.defaultIfEmpty(benData.getSinsuranceSalary(), "0") ) > 0) {
				replaceValue.put(ConstantKey.A154, DF.format(Integer.parseInt(benData.getSinsuranceSalary())));
			} else {
				replaceValue.put(ConstantKey.A154, "0");
			}
		}
	}

	// 外籍受益人補證明期限
	public void A155() {
		/**
		 * // 顯示當次月核定之核定年月+1個月之年月及該月最後一日：例如，當次月核定為102年11月，則顯示103年1月31日。
		 *
		 * if (baappbase != null) {
		 *
		 * String afterIssuYm = DateUtility.calMonth(baappbase.getIssuYm() + "01", 1);
		 * int iLastDay = DateUtility.lastDay(afterIssuYm);
		 *
		 * replaceValue.put(ConstantKey.A155,
		 * DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(afterIssuYm.substring(0,
		 * 6)), true) + String.valueOf(iLastDay) + "日"); } else {
		 * replaceValue.put(ConstantKey.A155, ""); }
		 */

		// 20180315 顯示結案日期空白且最大給付年月合格的遺屬的身分查核年月前一個月及該月最後一日，若前述遺屬有多人，顯示受款人序號最大的那筆
		if (baappbasePrintList != null) {

			if (baappbasePrintList.size() > 0) {
				String s1 = "";
				String s2 = "";
				String sSeqNoTmp = "";
				int i1 = 0;

				for (int i = 0; i <= (baappbasePrintList.size() - 1); i++) {

					if (!baappbasePrintList.get(i).getAcceptMk().equals("N")
							&& baappbasePrintList.get(i).getCloseDate().trim().equals("")
							&& !baappbasePrintList.get(i).getIdnChkYm().equals("")) {

						s1 = baappbasePrintList.get(i).getSeqNo().toString();
						if (sSeqNoTmp.equals(""))
							sSeqNoTmp = s1;

						if (sSeqNoTmp.compareTo(s1) == 0 || sSeqNoTmp.compareTo(s1) < 0) {
							i1 = i;
							sSeqNoTmp = s1;
						}

					}

				}

				if (!baappbasePrintList.get(i1).getIdnChkYm().equals("")) {

					String afterIssuYm = DateUtility.calMonth(baappbasePrintList.get(i1).getIdnChkYm() + "01", -1);
					int iLastDay = DateUtility.lastDay(afterIssuYm);

					replaceValue.put(ConstantKey.A155,
							DateUtility.formatChineseYearMonthString(
									DateUtility.changeWestYearMonthType(afterIssuYm.substring(0, 6)), true)
									+ String.valueOf(iLastDay) + "日");
				} else {
					replaceValue.put(ConstantKey.A155, "000年00月00日");
				}
			}
		} else {
			replaceValue.put(ConstantKey.A155, "000年00月00日");
		}
	}

	// 外籍受益人應補證明文件
	public void A156() {

		String A156String = "";
		String A156TempString = "";
		int iBenevtrel2 = 0;
		int iBenevtrel3 = 0;
		int iBenevtrel4 = 0;
		String s45YearOld = "";
		String s55YearOld = "";
		String afterIssuYm = "";
		int iLastDay = 0;

		if (baappbasePrintList != null) {

			if (baappbasePrintList.size() > 0) {

				for (int i = 0; i < baappbasePrintList.size(); i++) {
					if (baappbasePrintList.get(i).getCloseCause().equals("")
							&& baappbasePrintList.get(i).getUnqualifiedCause().equals("")
							&& baappbasePrintList.get(i).getBenDieDate().equals("")) {
						if (baappbasePrintList.get(i).getBenEvtRel().equals("2")) {
							iBenevtrel2 = iBenevtrel2 + 1;

							// 屆滿45歲日期≦ 核定年月+2個月當月之最末日＜屆滿55歲日期且最大給付年月符合註記無25
							s45YearOld = DateUtility.calYear(baappbasePrintList.get(i).getBenBrDate(), 45);
							s55YearOld = DateUtility.calYear(baappbasePrintList.get(i).getBenBrDate(), 55);

							afterIssuYm = DateUtility.calMonth(baappbase.getIssuYm() + "01", 2);
							iLastDay = DateUtility.lastDay(afterIssuYm);

							if (A156TempString.equals("")
									&& (Integer.parseInt(s45YearOld) <= Integer
											.parseInt(afterIssuYm + String.valueOf(iLastDay))
											&& (Integer.parseInt(afterIssuYm + String.valueOf(iLastDay)) < Integer
													.parseInt(s55YearOld)))
									&& baappbasePrintList.get(i).getDataCount().intValueExact() == 0) {
								A156TempString = "未再婚證明、工作收入證明文件";
							} else {
								A156TempString = "未再婚證明文件";
							}

						} else if (baappbasePrintList.get(i).getBenEvtRel().equals("3")) {
							iBenevtrel3 = iBenevtrel3 + 1;
						} else if (baappbasePrintList.get(i).getBenEvtRel().equals("4")) {
							iBenevtrel4 = iBenevtrel4 + 1;
						}
					}
				}

				if (iBenevtrel3 > 0) {
					A156String = "工作收入證明文件";
				} else if (iBenevtrel2 == 0 && iBenevtrel3 == 0 && iBenevtrel4 > 0) {
					A156String = "未被收養證明文件";
				} else if (iBenevtrel2 > 0 && iBenevtrel3 == 0 && iBenevtrel4 == 0) {
					if (!A156TempString.equals("")) {
						A156String = A156TempString;
					}

				} else if (iBenevtrel2 > 0 && iBenevtrel3 == 0 && iBenevtrel4 > 0) {
					A156String = "配偶未再婚及子女未被收養證明文件";
				}

				replaceValue.put(ConstantKey.A156, A156String);
			} else {
				replaceValue.put(ConstantKey.A156, "");
			}
		} else {
			replaceValue.put(ConstantKey.A156, "");
		}

	}

	// 受益人國籍
	public void A157() {
		if (StringUtils.isNotBlank(benNationName)) {
			replaceValue.put(ConstantKey.A157, benNationName);
		} else {
			replaceValue.put(ConstantKey.A157, "");
		}

	}

	// 應補身分證明文件遺屬姓名
	public void A158() {

		if (baappbasePrintList != null) {

			if (baappbasePrintList.size() > 0) {
				String A158String = "";
				ArrayList<String> sNameList = new ArrayList<String>();

				for (int i = 0; i < baappbasePrintList.size(); i++) {
					if (!baappbasePrintList.get(i).getSeqNo().equals("0000")
							&& !StringUtils.isBlank(baappbasePrintList.get(i).getIdnChkYm())
							&& baappbasePrintList.get(i).getCloseCause().equals("")
							&& baappbasePrintList.get(i).getUnqualifiedCause().equals("")
							&& baappbasePrintList.get(i).getBenDieDate().equals("")) {
						sNameList.add(baappbasePrintList.get(i).getBenName() + "君");
					}
				}

				for (int i = 0; i < sNameList.size(); i++) {
					if (i != sNameList.size() - 1 && i != sNameList.size() - 2) {
						A158String = A158String + sNameList.get(i) + "、";
					} else if (i == sNameList.size() - 2 && i != sNameList.size() - 1) {
						A158String = A158String + sNameList.get(i) + "及";
					} else if (i == sNameList.size() - 1) {
						A158String = A158String + sNameList.get(i);
					}
				}

				replaceValue.put(ConstantKey.A158, A158String);
			} else {
				replaceValue.put(ConstantKey.A158, "");
			}
		} else {
			replaceValue.put(ConstantKey.A158, "");
		}

	}

	// 查核失能程度通知補正期限
	public void A159() {
		// 顯示當次月核定之核定年月+2個月之年月及該月最後一日：例如，當次月核定為106年2月，則顯示106年4月30日。

		if (baappbase != null) {

			String afterIssuYm = DateUtility.calMonth(baappbase.getIssuYm() + "01", 2);
			int iLastDay = DateUtility.lastDay(afterIssuYm);

			replaceValue.put(ConstantKey.A159,
					DateUtility.formatChineseYearMonthString(
							DateUtility.changeWestYearMonthType(afterIssuYm.substring(0, 6)), true)
							+ String.valueOf(iLastDay) + "日");
		} else {
			replaceValue.put(ConstantKey.A159, "");
		}
	}

	// 畢業年月
	public void A160() {
		String statusDate_S = "";
		Baappbase nbSchoolData = null;
		BaappbaseDao baappbaseDao = (BaappbaseDao) SpringHelper.getBeanById("baappbaseDao");

		if (benData != null) {
			statusDate_S = baappbaseDao.selectMaxDateFromNbschool(benData.getBenIdnNo());
			nbSchoolData = baappbaseDao.selectMaxDateFromNbschool2(benData.getBenIdnNo(), statusDate_S);

			if (nbSchoolData != null) {
				if (nbSchoolData.getStatus_S().equals("G") && StringUtils.isNotBlank(nbSchoolData.getStatusDate_S())) {
					String statusDate = DateUtility.changeWestYearMonthType(nbSchoolData.getStatusDate_S());
					replaceValue.put(ConstantKey.A160,
							statusDate.substring(0, 3) + " 年 " + statusDate.substring(3, 5) + " 月");
				} else {
					if (badaprTotal != null) {
						String minPayYm = DateUtility.changeDateType(badaprTotal.get("PREYM") + "01");
						replaceValue.put(ConstantKey.A160,
								minPayYm.substring(0, 3) + " 年 " + minPayYm.substring(3, 5) + " 月");
					} else {
						replaceValue.put(ConstantKey.A160, "000年00月");
					}
				}
			}
		}
	}

	// 休學年月
	public void A161() {

		String statusDate_S = "";
		Baappbase nbSchoolData = null;
		BaappbaseDao baappbaseDao = (BaappbaseDao) SpringHelper.getBeanById("baappbaseDao");

		if (benData != null) {
			statusDate_S = baappbaseDao.selectMaxDateFromNbschool(benData.getBenIdnNo());
			nbSchoolData = baappbaseDao.selectMaxDateFromNbschool2(benData.getBenIdnNo(), statusDate_S);

			if (nbSchoolData != null) {
				if (nbSchoolData.getStatus_S().equals("S") && StringUtils.isNotBlank(nbSchoolData.getStatusDate_S())) {
					String statusDate = DateUtility.changeWestYearMonthType(nbSchoolData.getStatusDate_S());
					replaceValue.put(ConstantKey.A161,
							statusDate.substring(0, 3) + " 年 " + statusDate.substring(3, 5) + " 月");
				} else {
					if (badaprTotal != null) {
						String minPayYm = DateUtility.changeDateType(badaprTotal.get("PREYM") + "01");
						replaceValue.put(ConstantKey.A161,
								minPayYm.substring(0, 3) + " 年 " + minPayYm.substring(3, 5) + " 月");
					} else {
						replaceValue.put(ConstantKey.A161, "000年00月");
					}
				}
			}
		}
	}

	// 退學年月
	public void A162() {

		String statusDate_S = "";
		Baappbase nbSchoolData = null;
		BaappbaseDao baappbaseDao = (BaappbaseDao) SpringHelper.getBeanById("baappbaseDao");

		if (benData != null) {
			statusDate_S = baappbaseDao.selectMaxDateFromNbschool(benData.getBenIdnNo());
			nbSchoolData = baappbaseDao.selectMaxDateFromNbschool2(benData.getBenIdnNo(), statusDate_S);

			if (nbSchoolData != null) {
				if (nbSchoolData.getStatus_S().equals("D") && StringUtils.isNotBlank(nbSchoolData.getStatusDate_S())) {
					String statusDate = DateUtility.changeWestYearMonthType(nbSchoolData.getStatusDate_S());
					replaceValue.put(ConstantKey.A162,
							statusDate.substring(0, 3) + " 年 " + statusDate.substring(3, 5) + " 月");
				} else {
					if (badaprTotal != null) {
						String minPayYm = DateUtility.changeDateType(badaprTotal.get("PREYM") + "01");
						replaceValue.put(ConstantKey.A162,
								minPayYm.substring(0, 3) + " 年 " + minPayYm.substring(3, 5) + " 月");
					} else {
						replaceValue.put(ConstantKey.A162, "000年00月");
					}
				}
			}
		}
	}

	// 入帳對象
	public void A163() {
		if (baappbasePrintList != null) {

			if (baappbasePrintList.size() > 0) {
				String A163String = "";
				ArrayList<String> sNameList = new ArrayList<String>();

				for (int i = 0; i < baappbasePrintList.size(); i++) {
					if (!baappbasePrintList.get(i).getSeqNo().equals("0000")
							&& baappbasePrintList.get(i).getCloseDate().equals("")
							&& !baappbasePrintList.get(i).getAcceptMk().equals("N")
							&& baappbasePrintList.get(i).getAccRel().equals("1")) {
						sNameList.add(baappbasePrintList.get(i).getBenName() + "君");
					}
				}

				for (int i = 0; i < sNameList.size(); i++) {
					if (i != sNameList.size() - 1 && i != sNameList.size() - 2) {
						A163String = A163String + sNameList.get(i) + "、";
					} else if (i == sNameList.size() - 2 && i != sNameList.size() - 1) {
						A163String = A163String + sNameList.get(i) + "及";
					} else if (i == sNameList.size() - 1) {
						A163String = A163String + sNameList.get(i);
					}
				}

				if (sNameList.size() == 1) {
					A163String = "匯入指定之" + A163String + "帳戶";
				} else if (sNameList.size() > 1) {
					A163String = "平均匯入" + A163String + "指定之帳戶";
				}

				replaceValue.put(ConstantKey.A163, A163String);
			} else {
				replaceValue.put(ConstantKey.A163, "");
			}
		} else {
			replaceValue.put(ConstantKey.A163, "");
		}
	}

	// 實付金額不為0之最小給付年月
	public void A164() {
		if (badaprPayYmA164 != null) {
			if (!(badaprPayYmA164.get("MINPAYYM") == null) && !badaprPayYmA164.get("MINPAYYM").equals("000000")
					&& !badaprPayYmA164.get("MINPAYYM").equals("")) {
				String minPayYm = DateUtility.changeDateType(badaprPayYmA164.get("MINPAYYM") + "01");
				replaceValue.put(ConstantKey.A164, minPayYm.substring(0, 3) + " 年 " + minPayYm.substring(3, 5) + " 月");
			} else {
				replaceValue.put(ConstantKey.A164, "000年00月");
			}
		} else {
			replaceValue.put(ConstantKey.A164, "000年00月");
		}
	}
	//實付金額不為0之最小給付年月的前一個月
	public void A165() {
		if (badaprPayYmA164 != null) {
			if (!(badaprPayYmA164.get("PREMINPAYYM") == null) && !badaprPayYmA164.get("PREMINPAYYM").equals("000000")
					&& !badaprPayYmA164.get("PREMINPAYYM").equals("")) {
				String minPayYm = DateUtility.changeDateType(badaprPayYmA164.get("PREMINPAYYM") + "01");
				replaceValue.put(ConstantKey.A165, minPayYm.substring(0, 3) + " 年 " + minPayYm.substring(3, 5) + " 月");
			} else {
				replaceValue.put(ConstantKey.A165, "000年00月");
			}
		} else {
			replaceValue.put(ConstantKey.A165, "000年00月");
		}
	}
	//實付金額不為0之最大給付年月
	public void A167() {
		if (badaprPayYmA164 != null) {
			if (!(badaprPayYmA164.get("MAXPAYYM") == null) && !badaprPayYmA164.get("MAXPAYYM").equals("000000")
					&& !badaprPayYmA164.get("MAXPAYYM").equals("")) {
				String minPayYm = DateUtility.changeDateType(badaprPayYmA164.get("MAXPAYYM") + "01");
				replaceValue.put(ConstantKey.A167, minPayYm.substring(0, 3) + " 年 " + minPayYm.substring(3, 5) + " 月");
			} else {
				replaceValue.put(ConstantKey.A167, "000年00月");
			}
		} else {
			replaceValue.put(ConstantKey.A167, "000年00月");
		}
	}
	//實付金額不為0之最大給付年月的次月
	public void A168() {
		if (badaprPayYmA164 != null) {
			if (!(badaprPayYmA164.get("NEXTMAXPAYYM") == null) && !badaprPayYmA164.get("NEXTMAXPAYYM").equals("000000")
					&& !badaprPayYmA164.get("NEXTMAXPAYYM").equals("")) {
				String minPayYm = DateUtility.changeDateType(badaprPayYmA164.get("NEXTMAXPAYYM") + "01");
				replaceValue.put(ConstantKey.A168, minPayYm.substring(0, 3) + " 年 " + minPayYm.substring(3, 5) + " 月");
			} else {
				replaceValue.put(ConstantKey.A168, "000年00月");
			}
		} else {
			replaceValue.put(ConstantKey.A168, "000年00月");
		}
	}

	// 「首次給付年月」欄 BAAPPBASE.PAYYMS
	public void L011() {
		if (baappbase != null && baappbase.getPayYms() != null) {
			if (!baappbase.getPayYms().equals("")) {

				String payYms = DateUtility.changeDateType(baappbase.getPayYms() + "01");
				replaceValue.put(ConstantKey.L011, payYms.substring(0, 3) + " 年 " + payYms.substring(3, 5) + " 月");
			}
		} else {
			replaceValue.put(ConstantKey.L011, "000年00月");
		}
	}

	// 「給付年月」欄 BADAPR.PAYYM
	public void L012() {
		if (badaprPayYmL012 != null) {
			if (!badaprPayYmL012.get("MINPAYYM").equals("000000")
					&& !badaprPayYmL012.get("MAXPAYYM").equals("000000")) {
				String minPayYm = DateUtility.changeDateType(badaprPayYmL012.get("MINPAYYM") + "01");
				String maxPayYm = DateUtility.changeDateType(badaprPayYmL012.get("MAXPAYYM") + "01");

				if (maxPayYm.equals(minPayYm) || maxPayYm == null || maxPayYm.equals("")) {
					replaceValue.put(ConstantKey.L012,
							minPayYm.substring(0, 3) + " 年 " + minPayYm.substring(3, 5) + " 月");
				} else {
					replaceValue.put(ConstantKey.L012, minPayYm.substring(0, 3) + " 年 " + minPayYm.substring(3, 5)
							+ " 月至" + maxPayYm.substring(0, 3) + " 年 " + maxPayYm.substring(3, 5) + " 月");
				}
			} else {
				replaceValue.put(ConstantKey.L012, "000年00月至000年00月");
			}
		} else {
			replaceValue.put(ConstantKey.L012, "000年00月至000年00月");
		}
	}

	// 「總核定金額」欄 BADAPR.BEFISSUEAMT
	public void L013() {
		if (baappbase != null && baappbase.getBefIssueAmt() != null) {
			if (baappbase.getBefIssueAmt().intValue() > 0) {
				replaceValue.put(ConstantKey.L013, DF.format(baappbase.getBefIssueAmt().intValue()));
			} else {
				replaceValue.put(ConstantKey.L013, "0");
			}

		} else {
			replaceValue.put(ConstantKey.L013, "0");
		}

	}

	// 「離職退保日期」欄 BAAPPBASE.EVTJOBDATE
	public void L014() {
		if (baappbase != null && baappbase.getEvtJobDate() != null) {
			if (!baappbase.getEvtJobDate().equals("")) {

				replaceValue.put(ConstantKey.L014,
						DateUtility.formatChineseDateTimeString(baappbase.getEvtJobDate(), true));
			}
		} else {
			replaceValue.put(ConstantKey.L014, "000年00月00日");
		}
	}

	// 「老年差額金」欄 BAAPPBASE.MARGINAMT
	public void L015() {
		if (baappbase != null && baappbase.getMarginAmt() != null) {
			if (baappbase.getMarginAmt().intValue() > 0) {
				replaceValue.put(ConstantKey.L015, DF.format(baappbase.getMarginAmt().intValue()));
			} else {
				replaceValue.put(ConstantKey.L015, "0");
			}

		} else {
			replaceValue.put(ConstantKey.L015, "0");
		}
	}

	// 「老年年資(年)」欄 BADAPR.NOLDTY
	public void L016() {
		if (baappbase != null && baappbase.getNoldtY() != null) {
			if (baappbase.getNoldtY().intValue() > 0) {
				replaceValue.put(ConstantKey.L016, DF.format(baappbase.getNoldtY().intValue()));
			} else {
				replaceValue.put(ConstantKey.L016, "0");
			}

		} else {
			replaceValue.put(ConstantKey.L016, "0");
		}
	}

	// 「老年年資(月)」欄 BADAPR.NOLDTM
	public void L017() {
		if (baappbase != null && baappbase.getNoldtM() != null) {

			if (baappbase.getNoldtM().intValue() > 0) {
				replaceValue.put(ConstantKey.L017, DF.format(baappbase.getNoldtM().intValue()));
			} else {
				replaceValue.put(ConstantKey.L017, "0");
			}

		} else {
			replaceValue.put(ConstantKey.L017, "0");
		}
	}

	// 「一次平均薪資」欄 CIPB.AVGWG
	public void L018() {
		if (avgWgL018 != null && avgWgL018.intValue() > 0) {

			replaceValue.put(ConstantKey.L018, DF.format(avgWgL018.intValue()));

		} else {
			replaceValue.put(ConstantKey.L018, "0");
		}
	}

	// 「給付月數」欄 BAAPPBASE.ONCEPAYM
	public void L019() {
		if (baappbase != null && baappbase.getOncePayM() != null) {
			if (baappbase.getOncePayM().doubleValue() > 0) {
				replaceValue.put(ConstantKey.L019, baappbase.getOncePayM().toPlainString());
			} else {
				replaceValue.put(ConstantKey.L019, "0");
			}

		} else {
			replaceValue.put(ConstantKey.L019, "0");
		}
	}

	// 「一次給付金額」欄 BAAPPBASE.ONCEISSUEAMT
	public void L020() {
		if (baappbase != null && baappbase.getOnceIssueAmt() != null) {
			if (baappbase.getOnceIssueAmt().intValue() > 0) {
				replaceValue.put(ConstantKey.L020, DF.format(baappbase.getOnceIssueAmt().intValue()));
			} else {
				replaceValue.put(ConstantKey.L020, "0");
			}

		} else {
			replaceValue.put(ConstantKey.L020, "0");
		}
	}

	// 「最後給付年月」欄 BADAPR.PAYYM：上一核定年月之最大給付年月
	public void L021() {
		if (maxPayYmL021 != null && StringUtils.isNotBlank(maxPayYmL021)) {

			String maxPayYm = DateUtility.changeDateType(maxPayYmL021 + "01");
			replaceValue.put(ConstantKey.L021, maxPayYm.substring(0, 3) + " 年 " + maxPayYm.substring(3, 5) + " 月");

		} else {
			replaceValue.put(ConstantKey.L021, "000年00月");
		}

	}

	// 「累計已領年金金額」欄 BAAPPBASE.ANNUAMT
	public void L022() {
		if (baappbase != null && baappbase.getAnnuAmt() != null) {
			if (baappbase.getAnnuAmt().intValue() > 0) {
				replaceValue.put(ConstantKey.L022, DF.format(baappbase.getAnnuAmt().intValue()));
			} else {
				replaceValue.put(ConstantKey.L022, "0");
			}

		} else {
			replaceValue.put(ConstantKey.L022, "0");
		}
	}

	// 「第1次發文日期」欄
	public void L023() {

		if (issueDateL023 != null && issueDateL023.get("ISSUEDATE1") != null) {
			if (!issueDateL023.get("ISSUEDATE1").equals("")) {

				replaceValue.put(ConstantKey.L023,
						DateUtility.formatChineseDateTimeString(issueDateL023.get("ISSUEDATE1").toString(), true));
			}
		} else {
			replaceValue.put(ConstantKey.L023, "000年00月00日");
		}
	}

	// 「第2次發文日期」欄
	public void L024() {

		if (issueDateL023 != null && issueDateL023.get("ISSUEDATE2") != null) {
			if (!issueDateL023.get("ISSUEDATE2").equals("")) {

				replaceValue.put(ConstantKey.L024,
						DateUtility.formatChineseDateTimeString(issueDateL023.get("ISSUEDATE2").toString(), true));
			}
		} else {
			replaceValue.put(ConstantKey.L024, "000年00月00日");
		}
	}

	// 「受益人性別」欄 BAAPPBASE.BENSEX
	public void K001() {
		if (baappbase != null) {
			String sTitle = "";
			if (baappbase.getBenSex().equals("1")) {
				sTitle = "先生";
			} else if (baappbase.getBenSex().equals("2")) {
				sTitle = "女士";
			} else {
				sTitle = "君";
			}

			replaceValue.put(ConstantKey.K001, sTitle);
		} else {
			replaceValue.put(ConstantKey.K001, "");
		}
	}

	public String nameConcat(List<Baappbase> baappbasePrintListA150, String unqualifiedCause) {
		int cnt = 0;
		String str = "";
		for (int i = 0; i < baappbasePrintListA150.size(); i++) {
		    if (baappbasePrintListA150.get(i).getUnqualifiedCause().equals(unqualifiedCause) && baappbasePrintListA150.get(i).getBenDieDate().equals("")) {
				str = str + baappbasePrintListA150.get(i).getBenName() + "，";
				cnt++;
			}
		}

		if (StringUtils.isNotBlank(str)) {
			String array[] = str.split("，");
			str = "";

			for(int i = 0; i < array.length; i++) {
				if(i+1 < array.length -1) {
					str = str + array[i] + "、";
				}else if(i+1 == array.length -1) {
					str = str + array[i] + "及";
				}else {
					str = str + array[i];
				}
			}
		}

		return str;
	}

	private String nameConcatForSchool2(List<Baappbase> baappbasePrintListA150, String unqualifiedCause, String status_S, String minPayYm) {
		BaappbaseDao baappbaseDao = (BaappbaseDao) SpringHelper.getBeanById("baappbaseDao");
		StringBuilder sb;
		String statusDate = "";
		String statusDate_S;
		Baappbase nbSchoolData;
		List<Baappbase> list = new ArrayList<Baappbase>();
		List<Baappbase> newList = new ArrayList<Baappbase>();
		for (Baappbase b : baappbasePrintListA150) {
			Baappbase baappbase = new Baappbase();
			statusDate_S = baappbaseDao.selectMaxDateFromNbschool(b.getBenIdnNo());
			nbSchoolData = baappbaseDao.selectMaxDateFromNbschool2(b.getBenIdnNo(), statusDate_S);
			if (StringUtils.equals(b.getUnqualifiedCause(), unqualifiedCause)) {
				if (nbSchoolData != null) {
					if (nbSchoolData.getStatus_S().equals(status_S) && StringUtils.isNotBlank(nbSchoolData.getStatusDate_S())) {
							statusDate = DateUtility.changeWestYearMonthType(nbSchoolData.getStatusDate_S());
							statusDate = statusDate.substring(0, 3) + " 年 " + statusDate.substring(3, 5) + " 月";
					} else {
						statusDate = minPayYm;
					}
				} else {
					statusDate = replaceValue.get(ConstantKey.A148);
				}

				baappbase.setBenName(b.getBenName());
				baappbase.setStatusDate_S(statusDate);
				list.add(baappbase);
			}
		}

		for (int i = 0; i < list.size(); i++) {
			Baappbase baappbase = new Baappbase();
			baappbase.setBenName(list.get(i).getBenName() + "，");
			baappbase.setStatusDate_S(list.get(i).getStatusDate_S());
			newList.add(baappbase);
			for (int j = i + 1; j < list.size() ; j++) {
				if (StringUtils.equals(list.get(i).getStatusDate_S(), list.get(j).getStatusDate_S())) {
					newList.get(i).setBenName(newList.get(i).getBenName() + list.get(j).getBenName() + "，");
					list.remove(j);
					j = i;
				}
			}
		}

		for (Baappbase b : newList) {
			String[] array = b.getBenName().split("，");
			sb = new StringBuilder();
			for(int i = 0; i < array.length; i++) {
				if(i + 1 < array.length - 1) {
					sb.append(array[i]).append("、");
				}else if(i + 1 == array.length - 1) {
					sb.append(array[i]).append("及");
				}else {
					sb.append(array[i]);
				}
			}
			b.setBenName(String.valueOf(sb));
		}

		sb = new StringBuilder();
		for (Baappbase b : newList) {
			if (StringUtils.equals("01", unqualifiedCause)) {
				sb.append(b.getBenName()).append("已於").append(b.getStatusDate_S()).append("畢業未在學且非無謀生能力；");
			} else if (StringUtils.equals("11", unqualifiedCause)) {
				sb.append(b.getBenName()).append("已於").append(b.getStatusDate_S()).append("休學且非無謀生能力；");
			} else if (StringUtils.equals("12", unqualifiedCause)) {
				sb.append(b.getBenName()).append("已於").append(b.getStatusDate_S()).append("退學且非無謀生能力；");
			}
		}
		return String.valueOf(sb);
	}

	public HashMap<String, String> nameConcatForSchool(List<Baappbase> baappbasePrintListA150, String unqualifiedCause, String status_S, String minPayYm) {
		int cnt = 0;
		int firstPersonDate = baappbasePrintListA150.size();
		String statusDate = "";
		String statusDate_S = "";
		Baappbase nbSchoolData = null;
		String str = "";
		HashMap<String, String> map = new HashMap<String, String>();
		BaappbaseDao baappbaseDao = (BaappbaseDao) SpringHelper.getBeanById("baappbaseDao");

		for (int i = 0; i < baappbasePrintListA150.size(); i++) {
			statusDate_S = baappbaseDao.selectMaxDateFromNbschool(baappbasePrintListA150.get(i).getBenIdnNo());
			nbSchoolData = baappbaseDao.selectMaxDateFromNbschool2(baappbasePrintListA150.get(i).getBenIdnNo(), statusDate_S);
			if (nbSchoolData != null) {
				if(baappbasePrintListA150.get(i).getUnqualifiedCause().equals(unqualifiedCause) && nbSchoolData.getStatus_S().equals(status_S) && StringUtils.isNotBlank(nbSchoolData.getStatusDate_S())) {
					str = str + baappbasePrintListA150.get(i).getBenName() + "，";
					cnt++;
			        if(firstPersonDate > i) {
					    if (StringUtils.isNotBlank(nbSchoolData.getStatusDate_S())) {
						    statusDate = DateUtility.changeWestYearMonthType(nbSchoolData.getStatusDate_S());
					    	statusDate = statusDate.substring(0, 3) + " 年 " + statusDate.substring(3, 5) + " 月";
					    }else {
				    		statusDate = minPayYm;
				    	}
					    firstPersonDate = i;
				    }
				}
			} else {
				if(StringUtils.equals(baappbasePrintListA150.get(i).getUnqualifiedCause(), unqualifiedCause)) {
					str = str + baappbasePrintListA150.get(i).getBenName() + "，";
				}
				statusDate = replaceValue.get(ConstantKey.A148);
			}
		}

		if (StringUtils.isNotBlank(str)) {
			String array[] = str.split("，");
			str = "";
			for(int i = 0; i < array.length; i++) {
				if(i+1 < array.length -1) {
					str = str + array[i] + "、";
				}else if(i+1 == array.length -1) {
					str = str + array[i] + "及";
				}else {
					str = str + array[i];
				}
			}
			map.put("str", str);
			map.put("statusDate", statusDate);
		}

		return map;
	}

	public String nameConcatFor09And10(String str) {
		String array[] = str.split("，");
		str = "";
		for(int i = 0; i < array.length; i++) {
			if(i+1 < array.length -1) {
				str = str + array[i] + "、";
			}else if(i+1 == array.length -1) {
				str = str + array[i] + "及";
			}else {
				str = str + array[i];
			}
		}
		return str;
	}

}
