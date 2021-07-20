package tw.gov.bli.ba.services;

import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.ba.dao.BaappbaseDao;
import tw.gov.bli.ba.dao.BaappexpandDao;
import tw.gov.bli.ba.dao.Babcml7Dao;
import tw.gov.bli.ba.dao.CvldtlDao;
import tw.gov.bli.ba.dao.NpbanklistDao;
import tw.gov.bli.ba.dao.BcbpfDao;
import tw.gov.bli.ba.dao.NppostlistDao;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Baappexpand;
import tw.gov.bli.ba.domain.Babcml7;
import tw.gov.bli.ba.domain.Cvldtl;
import tw.gov.bli.ba.domain.Npbanklist;
import tw.gov.bli.ba.domain.Bcbpf;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.reviewfee.cases.ReviewFeeDecisionCase;
import tw.gov.bli.ba.reviewfee.cases.ReviewFeeDecisionDetailCase;
import tw.gov.bli.ba.reviewfee.cases.ReviewFeeReceiptCase;
import tw.gov.bli.ba.reviewfee.cases.ReviewFeeReceiptDetailCase;
import tw.gov.bli.ba.reviewfee.cases.ReviewFeeReviewCase;
import tw.gov.bli.ba.reviewfee.cases.ReviewFeeReviewDetailCase;
import tw.gov.bli.ba.reviewfee.forms.ReviewFeeReceiptForm;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.helper.EnvFacadeHelper;
import tw.gov.bli.common.helper.HttpHelper;
import tw.gov.bli.common.util.FrameworkLogUtil;

/**
 * Service for 複檢費用
 * 
 * @author Goston
 */
public class ReviewFeeService {
    private static Log log = LogFactory.getLog(ReviewFeeService.class);

    private BaappbaseDao baappbaseDao;
    private BaappexpandDao baappexpandDao;
    private Babcml7Dao babcml7Dao;
    private CvldtlDao cvldtlDao;
    private NpbanklistDao npbanklistDao;
    private BcbpfDao bcbpfDao;
    private NppostlistDao nppostlistDao;

    /**
     * 依傳入之 受理編號 取得 複檢費用受理 之資料 for 複檢費用受理作業
     * 
     * @param apNo 受理編號
     * @return
     */
    public ReviewFeeReceiptCase getReviewFeeReceiptDataForInsert(String apNo) {
        // 取得 BAAPPBASE 資料
        // [
        Baappbase masterData = baappbaseDao.getReviewFeeReceiptHeaderDataForInsertBy(apNo);

        if (masterData == null)
            return null;

        ReviewFeeReceiptCase caseData = new ReviewFeeReceiptCase();

        caseData.setBaappbaseId(masterData.getBaappbaseId()); // 資料列編號
        caseData.setApNo(masterData.getApNo()); // 受理編號
        caseData.setSeqNo(masterData.getSeqNo()); // 序號
        caseData.setAppDate(masterData.getAppDate()); // 申請日期
        caseData.setEvtName(masterData.getEvtName()); // 事故者姓名
        caseData.setEvtIdnNo(masterData.getEvtIdnNo()); // 事故者身分證號
        caseData.setEvtBrDate(masterData.getEvtBrDate()); // 事故者出生日期
        caseData.setEvtJobDate(masterData.getEvtJobDate()); // 診斷失能日期
        // ]

        // 取得 BAAPPEXPAND 資料
        // [
        Baappexpand expandData = baappexpandDao.getReviewFeeReceiptHeaderDataForInsertBy(caseData.getBaappbaseId());

        if (expandData != null) {
            caseData.setBaappexpandId(expandData.getBaappexpandId()); // 資料編號
            caseData.setCriInJdp1(expandData.getCriInJdp1()); // 失能項目 1
            caseData.setCriInJdp2(expandData.getCriInJdp2()); // 失能項目 2
            caseData.setCriInJdp3(expandData.getCriInJdp3()); // 失能項目 3
            caseData.setCriInJdp4(expandData.getCriInJdp4()); // 失能項目 4
            caseData.setCriInJdp5(expandData.getCriInJdp5()); // 失能項目 5
            caseData.setCriInJdp6(expandData.getCriInJdp6()); // 失能項目 6
            caseData.setCriInJdp7(expandData.getCriInJdp7()); // 失能項目 7
            caseData.setCriInJdp8(expandData.getCriInJdp8()); // 失能項目 8
            caseData.setCriInJdp9(expandData.getCriInJdp9()); // 失能項目 9
            caseData.setCriInJdp10(expandData.getCriInJdp10()); // 失能項目 10
            caseData.setCriInJcl1(expandData.getCriInJcl1()); // 失能等級 1
            caseData.setCriInJcl2(expandData.getCriInJcl2()); // 失能等級 2
            caseData.setCriInJcl3(expandData.getCriInJcl3()); // 失能等級 3
            caseData.setEvTyp(expandData.getEvTyp()); // 傷病分類
            caseData.setEvTypName(expandData.getEvTypName()); // 傷病分類 - 中文 (取自 BAPARAM)
        }
        // ]

        // BABCML7
        // [
        // 取得 複檢費用受理編號
        String reApNo = StringUtils.substring(caseData.getApNo(), 0, 1) + "3" + StringUtils.substring(caseData.getApNo(), 2, StringUtils.length(caseData.getApNo()));
        // 取得 複檢費用申請序
        Integer apSeq = babcml7Dao.getReviewFeeReceiptApSeqForInsertBy(reApNo);

        caseData.setReApNo(reApNo); // 複檢費用受理編號
        caseData.setApSeq(((apSeq != null) ? new BigDecimal(apSeq.intValue()) : new BigDecimal(1))); // 複檢費用申請序
        // ]

        return caseData;
    }

    /**
     * 自 戶政全戶檔 (<code>CVLDTL</code>) 取得通訊地址資料 for 複檢費用受理作業
     * 
     * @param idnNo
     * @param brDate
     * @return
     */
    public List<Cvldtl> getReviewFeeReceiptCommAddrData(String idnNo, String brDate) {
        return cvldtlDao.selectDataBy(idnNo, ((StringUtils.length(brDate) == 7) ? DateUtility.changeDateType(brDate) : brDate));
    }

    /**
     * 自 銀行資料檔 (<code>BCBPF</code>) 取得銀行資料
     * 
     * @param bankBranchId 銀行 主機構代碼 + 分支機構代碼
     * @return
     */
    public Bcbpf getBankData(String bankBranchId) {
        if (StringUtils.isBlank(bankBranchId))
            return null;

        List<Bcbpf> list = bcbpfDao.selectBankDataBy(bankBranchId);

        if (list != null && list.size() == 1)
            return list.get(0);

        return null;
    }

    /**
     * 自 郵局代碼檔 (<code>NPPOSTLIST</code>) 取得郵局名稱
     * 
     * @param postId 郵局電腦代號
     * @return
     */
    public String getPostNameBy(String postId) {
        if (StringUtils.isBlank(postId))
            return null;

        return nppostlistDao.selectPostNameBy(postId);
    }

    /**
     * 複檢費用受理作業 新增 存檔處理
     * 
     * @param userData
     * @param caseData
     */
    public void saveReviewFeeReceiptData(UserBean userData, ReviewFeeReceiptCase caseData) {

        ReviewFeeReceiptDetailCase addCaseData = caseData.getAddCaseData();

        if (addCaseData != null) {
            Babcml7 saveData = new Babcml7();

            saveData.setReApNo(addCaseData.getReApNo()); // 複檢費用受理編號

            // 複檢費用申請序 於新增存檔時再取一次, 以免因前端閒置太久有其它 User 已新增該申請序之資料
            Integer apSeq = babcml7Dao.getReviewFeeReceiptApSeqForInsertBy(addCaseData.getReApNo());
            saveData.setApSeq(((apSeq != null) ? new BigDecimal(apSeq.intValue()) : new BigDecimal(1))); // 複檢費用申請序

            saveData.setApNo(addCaseData.getApNo()); // 受理編號
            saveData.setProcStat("00"); // 處理狀態
            saveData.setInreDate((StringUtils.length(addCaseData.getInreDate()) == 7) ? DateUtility.changeDateType(addCaseData.getInreDate()) : addCaseData.getInreDate()); // 通知複檢日期
            saveData.setReasCode(addCaseData.getReasCode()); // 複檢原因
            saveData.setHosId(addCaseData.getHosId()); // 醫療院所代碼
            saveData.setRecosDate((StringUtils.length(addCaseData.getRecosDate()) == 7) ? DateUtility.changeDateType(addCaseData.getRecosDate()) : addCaseData.getRecosDate()); // 複檢費用申請日期
            saveData.setReNum(addCaseData.getReNum()); // 複檢門診次數
            saveData.setRehpStay(addCaseData.getRehpStay()); // 複檢住院天數
            saveData.setReFees((addCaseData.getReFees() != null) ? addCaseData.getReFees() : new BigDecimal(0)); // 複檢費用
            saveData.setNonreFees((addCaseData.getNonreFees() != null) ? addCaseData.getNonreFees() : new BigDecimal(0)); // 非複檢必須費用
            saveData.setReAmtPay((addCaseData.getReAmtPay() != null) ? addCaseData.getReAmtPay() : new BigDecimal(0)); // 複檢實付金額
            saveData.setNotifyForm(addCaseData.getNotifyForm()); // 核定通知書格式
            saveData.setBenIdnNo(addCaseData.getBenIdnNo()); // 受益人身分證號
            saveData.setBenName(addCaseData.getBenName()); // 受益人姓名
            saveData.setBenBrDate((StringUtils.length(addCaseData.getBenBrDate()) == 7 || StringUtils.equals(StringUtils.substring(addCaseData.getBenBrDate(), 0, 1), "-")) ? DateUtility.changeDateType(addCaseData.getBenBrDate()) : addCaseData.getBenBrDate()); // 受益人出生日期
            saveData.setBenSex(addCaseData.getBenSex()); // 受益人性別
            saveData.setBenNationTyp(addCaseData.getBenNationTyp()); // 受益人國籍別
            saveData.setBenNationCode(addCaseData.getBenNationCode()); // 受益人國籍
            saveData.setBenEvtRel(addCaseData.getBenEvtRel()); // 受益人與事故者關係
            saveData.setTel1(addCaseData.getTel1()); // 電話1
            saveData.setTel2(addCaseData.getTel2()); // 電話2
            saveData.setCommTyp(addCaseData.getCommTyp()); // 通訊地址別
            saveData.setCommZip(addCaseData.getCommZip()); // 通訊郵遞區號
            saveData.setCommAddr(addCaseData.getCommAddr()); // 通訊地址
            saveData.setPayTyp(addCaseData.getPayTyp()); // 給付方式
            saveData.setBankName(addCaseData.getBankName()); // 金融機構名稱
            saveData.setPayBankId(addCaseData.getPayBankId()); // 金融機構總代號
            saveData.setBranchId(addCaseData.getBranchId()); // 分支代號
            saveData.setPayeeAcc(addCaseData.getPayeeAcc()); // 銀行帳號
            saveData.setAccName(addCaseData.getAccName()); // 戶名
            saveData.setCrtUser(userData.getEmpNo()); // 新增者代號
            saveData.setCrtTime(DateUtility.getNowWestDateTime(true)); // 新增日期時間

            babcml7Dao.insertReviewFeeReceiptData(saveData);

            // 紀錄 MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                FrameworkLogUtil.doInsertLog(saveData);
            }
        }
    }

    /**
     * 依傳入之查詢條件取得 複檢費用受理 之資料 for 複檢費用受理作業
     * 
     * @param apNo 受理編號
     * @param evtIdnNo 事故者身分證號
     * @return
     */
    public ReviewFeeReceiptCase getReviewFeeReceiptDataForUpdate(String apNo, String evtIdnNo) {
        // 取得 BAAPPBASE 資料
        // [
        Baappbase masterData = baappbaseDao.getReviewFeeReceiptHeaderDataForUpdateBy(apNo, evtIdnNo);

        if (masterData == null)
            return null;

        ReviewFeeReceiptCase caseData = new ReviewFeeReceiptCase();

        caseData.setBaappbaseId(masterData.getBaappbaseId()); // 資料列編號
        caseData.setApNo(masterData.getApNo()); // 受理編號
        caseData.setSeqNo(masterData.getSeqNo()); // 序號
        caseData.setAppDate(masterData.getAppDate()); // 申請日期
        caseData.setEvtName(masterData.getEvtName()); // 事故者姓名
        caseData.setEvtIdnNo(masterData.getEvtIdnNo()); // 事故者身分證號
        caseData.setEvtBrDate(masterData.getEvtBrDate()); // 事故者出生日期
        caseData.setEvtJobDate(masterData.getEvtJobDate()); // 診斷失能日期
        // ]

        // 取得 BAAPPEXPAND 資料
        // [
        Baappexpand expandData = baappexpandDao.getReviewFeeReceiptHeaderDataForUpdateBy(caseData.getBaappbaseId());

        if (expandData != null) {
            caseData.setBaappexpandId(expandData.getBaappexpandId()); // 資料編號
            caseData.setCriInJdp1(expandData.getCriInJdp1()); // 失能項目 1
            caseData.setCriInJdp2(expandData.getCriInJdp2()); // 失能項目 2
            caseData.setCriInJdp3(expandData.getCriInJdp3()); // 失能項目 3
            caseData.setCriInJdp4(expandData.getCriInJdp4()); // 失能項目 4
            caseData.setCriInJdp5(expandData.getCriInJdp5()); // 失能項目 5
            caseData.setCriInJdp6(expandData.getCriInJdp6()); // 失能項目 6
            caseData.setCriInJdp7(expandData.getCriInJdp7()); // 失能項目 7
            caseData.setCriInJdp8(expandData.getCriInJdp8()); // 失能項目 8
            caseData.setCriInJdp9(expandData.getCriInJdp9()); // 失能項目 9
            caseData.setCriInJdp10(expandData.getCriInJdp10()); // 失能項目 10
            caseData.setCriInJcl1(expandData.getCriInJcl1()); // 失能等級 1
            caseData.setCriInJcl2(expandData.getCriInJcl2()); // 失能等級 2
            caseData.setCriInJcl3(expandData.getCriInJcl3()); // 失能等級 3
            caseData.setEvTyp(expandData.getEvTyp()); // 傷病分類
            caseData.setEvTypName(expandData.getEvTypName()); // 傷病分類 - 中文 (取自 BAPARAM)
        }
        // ]

        // 取得 BABCML7 資料
        // [
        List<Babcml7> detailList = babcml7Dao.getReviewFeeReceiptListForUpdateBy(apNo, evtIdnNo);

        for (Babcml7 detailData : detailList) {
            ReviewFeeReceiptDetailCase detailCaseData = new ReviewFeeReceiptDetailCase();

            detailCaseData.setReApNo(detailData.getReApNo()); // 複檢費用受理編號
            detailCaseData.setApSeq(detailData.getApSeq()); // 複檢費用申請序
            detailCaseData.setApNo(detailData.getApNo()); // 受理編號
            detailCaseData.setProcStat(detailData.getProcStat()); // 處理狀態
            detailCaseData.setInreDate(detailData.getInreDate()); // 通知複檢日期
            detailCaseData.setReasCode(detailData.getReasCode()); // 複檢原因
            detailCaseData.setHosId(detailData.getHosId()); // 醫療院所代碼
            detailCaseData.setRecosDate(detailData.getRecosDate()); // 複檢費用申請日期
            detailCaseData.setReNum(detailData.getReNum()); // 複檢門診次數
            detailCaseData.setRehpStay(detailData.getRehpStay()); // 複檢住院天數
            detailCaseData.setReFees(detailData.getReFees()); // 複檢費用
            detailCaseData.setNonreFees(detailData.getNonreFees()); // 非複檢必須費用
            detailCaseData.setReAmtPay(detailData.getReAmtPay()); // 複檢實付金額
            detailCaseData.setNotifyForm(detailData.getNotifyForm()); // 核定通知書格式
            detailCaseData.setBenIdnNo(detailData.getBenIdnNo()); // 受益人身分證號
            detailCaseData.setBenName(detailData.getBenName()); // 受益人姓名
            detailCaseData.setBenBrDate(detailData.getBenBrDate()); // 受益人出生日期
            detailCaseData.setBenSex(detailData.getBenSex()); // 受益人性別
            detailCaseData.setBenNationTyp(detailData.getBenNationTyp()); // 受益人國籍別
            detailCaseData.setBenNationCode(detailData.getBenNationCode()); // 受益人國籍
            detailCaseData.setBenEvtRel(detailData.getBenEvtRel()); // 受益人與事故者關係
            detailCaseData.setTel1(detailData.getTel1()); // 電話1
            detailCaseData.setTel2(detailData.getTel2()); // 電話2
            detailCaseData.setCommTyp(detailData.getCommTyp()); // 通訊地址別
            detailCaseData.setCommZip(detailData.getCommZip()); // 通訊郵遞區號
            detailCaseData.setCommAddr(detailData.getCommAddr()); // 通訊地址
            detailCaseData.setPayTyp(detailData.getPayTyp()); // 給付方式
            detailCaseData.setBankName(detailData.getBankName()); // 金融機構名稱
            detailCaseData.setPayBankId(detailData.getPayBankId()); // 金融機構總代號
            detailCaseData.setBranchId(detailData.getBranchId()); // 分支代號
            detailCaseData.setPayeeAcc(detailData.getPayeeAcc()); // 銀行帳號
            detailCaseData.setAccName(detailData.getAccName()); // 戶名

            // caseData.reApNo 須設定, 否則 List 頁面之 Header 資籵將無法顯示複檢費用受理編號
            caseData.setReApNo(detailData.getReApNo()); // 複檢費用受理編號

            caseData.addDetailData(detailCaseData);
        }
        // ]

        return caseData;
    }

    /**
     * 複檢費用受理作業 修改 存檔處理
     * 
     * @param userData
     * @param caseData
     * @param updateForm
     */
    public void updateReviewFeeReceiptData(UserBean userData, ReviewFeeReceiptCase caseData, ReviewFeeReceiptForm updateForm) {
        String dateTime = DateUtility.getNowWestDateTime(true);

        ReviewFeeReceiptDetailCase updateCaseData = caseData.getModifyCaseData();

        if (updateCaseData != null) {
            Babcml7 updateData = babcml7Dao.getReviewFeeReceiptDataForUpdateBy(updateCaseData.getReApNo(), updateCaseData.getApSeq());

            if (updateData != null) {
                Babcml7 oldUpdateData = (Babcml7) BeanUtility.cloneBean(updateData);

                updateData.setInreDate((StringUtils.length(updateCaseData.getInreDate()) == 7) ? DateUtility.changeDateType(updateCaseData.getInreDate()) : updateCaseData.getInreDate()); // 通知複檢日期
                updateData.setReasCode(updateCaseData.getReasCode()); // 複檢原因
                updateData.setHosId(updateCaseData.getHosId()); // 醫療院所代碼
                updateData.setRecosDate((StringUtils.length(updateCaseData.getRecosDate()) == 7) ? DateUtility.changeDateType(updateCaseData.getRecosDate()) : updateCaseData.getRecosDate()); // 複檢費用申請日期
                updateData.setReNum(updateCaseData.getReNum()); // 複檢門診次數
                updateData.setRehpStay(updateCaseData.getRehpStay()); // 複檢住院天數
                updateData.setReFees((updateCaseData.getReFees() != null) ? updateCaseData.getReFees() : new BigDecimal(0)); // 複檢費用
                updateData.setNonreFees((updateCaseData.getNonreFees() != null) ? updateCaseData.getNonreFees() : new BigDecimal(0)); // 非複檢必須費用
                updateData.setReAmtPay((updateCaseData.getReAmtPay() != null) ? updateCaseData.getReAmtPay() : new BigDecimal(0)); // 複檢實付金額
                updateData.setNotifyForm(updateCaseData.getNotifyForm()); // 核定通知書格式
                updateData.setBenIdnNo(updateCaseData.getBenIdnNo()); // 受益人身分證號
                updateData.setBenName(updateCaseData.getBenName()); // 受益人姓名
                updateData.setBenBrDate((StringUtils.length(updateCaseData.getBenBrDate()) == 7 || StringUtils.equals(StringUtils.substring(updateCaseData.getBenBrDate(), 0, 1), "-")) ? DateUtility.changeDateType(updateCaseData.getBenBrDate()) : updateCaseData.getBenBrDate()); // 受益人出生日期
                updateData.setBenSex(updateCaseData.getBenSex()); // 受益人性別
                updateData.setBenNationTyp(updateCaseData.getBenNationTyp()); // 受益人國籍別
                updateData.setBenNationCode(updateCaseData.getBenNationCode()); // 受益人國籍
                updateData.setBenEvtRel(updateCaseData.getBenEvtRel()); // 受益人與事故者關係
                updateData.setTel1(updateCaseData.getTel1()); // 電話1
                updateData.setTel2(updateCaseData.getTel2()); // 電話2
                updateData.setCommTyp(updateCaseData.getCommTyp()); // 通訊地址別
                updateData.setCommZip(updateCaseData.getCommZip()); // 通訊郵遞區號
                updateData.setCommAddr(updateCaseData.getCommAddr()); // 通訊地址
                updateData.setPayTyp(updateCaseData.getPayTyp()); // 給付方式
                updateData.setBankName(updateCaseData.getBankName()); // 金融機構名稱
                updateData.setPayBankId(updateCaseData.getPayBankId()); // 金融機構總代號
                updateData.setBranchId(updateCaseData.getBranchId()); // 分支代號
                updateData.setPayeeAcc(updateCaseData.getPayeeAcc()); // 銀行帳號
                updateData.setAccName(updateCaseData.getAccName()); // 戶名
                updateData.setUpdUser(userData.getEmpNo()); // 新增者代號
                updateData.setUpdTime(dateTime); // 新增日期時間

                babcml7Dao.updateReviewFeeReceiptData(updateData);

                // 紀錄 MMAPLOG
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    FrameworkLogUtil.doUpdateLog(oldUpdateData, updateData);
                }
            }
        }
    }

    /**
     * 複檢費用受理作業 刪除 存檔處理
     * 
     * @param userData
     * @param reApNo 複檢費用受理編號
     * @param apSeq 複檢費用申請序
     */
    public void deleteReviewFeeReceiptData(UserBean userData, String reApNo, BigDecimal apSeq) {
        String dateTime = DateUtility.getNowWestDateTime(true);

        if (StringUtils.isNotBlank(reApNo) && apSeq != null) {
            Babcml7 deleteData = babcml7Dao.getReviewFeeReceiptDataForUpdateBy(reApNo, apSeq);

            if (deleteData != null) {
                babcml7Dao.deleteReviewFeeReceiptData(reApNo, apSeq);

                // 紀錄 MMAPLOG
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    FrameworkLogUtil.doDeleteLog(deleteData);
                }
            }
        }
    }

    /**
     * 依傳入之 受理編號 取得 複檢費用審核 之資料 for 複檢費用審核作業
     * 
     * @param apNo 受理編號
     * @return
     */
    public ReviewFeeReviewCase getReviewFeeReviewData(String apNo) {
        // 取得 BAAPPBASE 資料
        // [
        Baappbase masterData = baappbaseDao.getReviewFeeReviewHeaderDataBy(apNo);

        if (masterData == null)
            return null;

        ReviewFeeReviewCase caseData = new ReviewFeeReviewCase();

        caseData.setBaappbaseId(masterData.getBaappbaseId()); // 資料列編號
        caseData.setApNo(masterData.getApNo()); // 受理編號
        caseData.setSeqNo(masterData.getSeqNo()); // 序號
        caseData.setAppDate(masterData.getAppDate()); // 申請日期
        caseData.setEvtName(masterData.getEvtName()); // 事故者姓名
        caseData.setEvtIdnNo(masterData.getEvtIdnNo()); // 事故者身分證號
        caseData.setEvtBrDate(masterData.getEvtBrDate()); // 事故者出生日期
        caseData.setEvtJobDate(masterData.getEvtJobDate()); // 診斷失能日期
        // ]

        // 取得 BAAPPEXPAND 資料
        // [
        Baappexpand expandData = baappexpandDao.getReviewFeeReviewHeaderDataBy(caseData.getBaappbaseId());

        if (expandData != null) {
            caseData.setBaappexpandId(expandData.getBaappexpandId()); // 資料編號
            caseData.setCriInJdp1(expandData.getCriInJdp1()); // 失能項目 1
            caseData.setCriInJdp2(expandData.getCriInJdp2()); // 失能項目 2
            caseData.setCriInJdp3(expandData.getCriInJdp3()); // 失能項目 3
            caseData.setCriInJdp4(expandData.getCriInJdp4()); // 失能項目 4
            caseData.setCriInJdp5(expandData.getCriInJdp5()); // 失能項目 5
            caseData.setCriInJdp6(expandData.getCriInJdp6()); // 失能項目 6
            caseData.setCriInJdp7(expandData.getCriInJdp7()); // 失能項目 7
            caseData.setCriInJdp8(expandData.getCriInJdp8()); // 失能項目 8
            caseData.setCriInJdp9(expandData.getCriInJdp9()); // 失能項目 9
            caseData.setCriInJdp10(expandData.getCriInJdp10()); // 失能項目 10
            caseData.setCriInJcl1(expandData.getCriInJcl1()); // 失能等級 1
            caseData.setCriInJcl2(expandData.getCriInJcl2()); // 失能等級 2
            caseData.setCriInJcl3(expandData.getCriInJcl3()); // 失能等級 3
            caseData.setEvTyp(expandData.getEvTyp()); // 傷病分類
            caseData.setEvTypName(expandData.getEvTypName()); // 傷病分類 - 中文 (取自 BAPARAM)
        }
        // ]

        // 取得 BABCML7 資料
        // [
        List<Babcml7> detailList = babcml7Dao.getReviewFeeReviewListBy(apNo);

        for (Babcml7 detailData : detailList) {
            ReviewFeeReviewDetailCase detailCaseData = new ReviewFeeReviewDetailCase();

            detailCaseData.setReApNo(detailData.getReApNo()); // 複檢費用受理編號
            detailCaseData.setApSeq(detailData.getApSeq()); // 複檢費用申請序
            detailCaseData.setApNo(detailData.getApNo()); // 受理編號
            detailCaseData.setProcStat(detailData.getProcStat()); // 處理狀態
            detailCaseData.setInreDate(detailData.getInreDate()); // 通知複檢日期
            detailCaseData.setReasCode(detailData.getReasCode()); // 複檢原因
            detailCaseData.setHosId(detailData.getHosId()); // 醫療院所代碼
            detailCaseData.setRecosDate(detailData.getRecosDate()); // 複檢費用申請日期
            detailCaseData.setReNum(detailData.getReNum()); // 複檢門診次數
            detailCaseData.setRehpStay(detailData.getRehpStay()); // 複檢住院天數
            detailCaseData.setReFees(detailData.getReFees()); // 複檢費用
            detailCaseData.setNonreFees(detailData.getNonreFees()); // 非複檢必須費用
            detailCaseData.setReAmtPay(detailData.getReAmtPay()); // 複檢實付金額
            detailCaseData.setNotifyForm(detailData.getNotifyForm()); // 核定通知書格式
            detailCaseData.setBenIdnNo(detailData.getBenIdnNo()); // 受益人身分證號
            detailCaseData.setBenName(detailData.getBenName()); // 受益人姓名
            detailCaseData.setBenBrDate(detailData.getBenBrDate()); // 受益人出生日期
            detailCaseData.setBenSex(detailData.getBenSex()); // 受益人性別
            detailCaseData.setBenNationTyp(detailData.getBenNationTyp()); // 受益人國籍別
            detailCaseData.setBenNationCode(detailData.getBenNationCode()); // 受益人國籍
            detailCaseData.setBenEvtRel(detailData.getBenEvtRel()); // 受益人與事故者關係
            detailCaseData.setTel1(detailData.getTel1()); // 電話1
            detailCaseData.setTel2(detailData.getTel2()); // 電話2
            detailCaseData.setCommTyp(detailData.getCommTyp()); // 通訊地址別
            detailCaseData.setCommZip(detailData.getCommZip()); // 通訊郵遞區號
            detailCaseData.setCommAddr(detailData.getCommAddr()); // 通訊地址
            detailCaseData.setPayTyp(detailData.getPayTyp()); // 給付方式
            detailCaseData.setBankName(detailData.getBankName()); // 金融機構名稱
            detailCaseData.setPayBankId(detailData.getPayBankId()); // 金融機構總代號
            detailCaseData.setBranchId(detailData.getBranchId()); // 分支代號
            detailCaseData.setPayeeAcc(detailData.getPayeeAcc()); // 銀行帳號
            detailCaseData.setAccName(detailData.getAccName()); // 戶名
            // Other
            detailCaseData.setReasName(detailData.getReasName()); // 複檢原因 - 中文
            detailCaseData.setHosName(detailData.getHosName()); // 醫療院所 - 中文
            detailCaseData.setBenNationName(detailData.getBenNationName()); // 受款人國籍名稱
            detailCaseData.setBenEvtRelName(detailData.getBenEvtRelName()); // 受益人與事故者關係 - 中文
            detailCaseData.setPayTypName(detailData.getPayTypName()); // 給付方式 - 中文

            // caseData.reApNo 須設定, 否則 List 頁面之 Header 資籵將無法顯示複檢費用受理編號
            caseData.setReApNo(detailData.getReApNo()); // 複檢費用受理編號

            caseData.addDetailData(detailCaseData);
        }
        // ]

        return caseData;
    }

    /**
     * 複檢費用審核作業 存檔處理
     * 
     * @param userData
     * @param caseData
     */
    public void updateReviewFeeReviewData(UserBean userData, ReviewFeeReviewCase caseData) {
        String dateTime = DateUtility.getNowWestDateTime(true);
        String today = StringUtils.substring(dateTime, 0, 8);

        ReviewFeeReviewDetailCase detailCaseData = caseData.getDetailData();

        if (detailCaseData != null) {
            Babcml7 updateData = babcml7Dao.getReviewFeeReviewDataForUpdateBy(detailCaseData.getReApNo(), detailCaseData.getApSeq());

            if (updateData != null) {
                Babcml7 oldUpdateData = (Babcml7) BeanUtility.cloneBean(updateData);

                updateData.setChkDate(today); // 審核日期
                updateData.setChkMan(userData.getEmpNo()); // 審核人員
                updateData.setUpdUser(userData.getEmpNo()); // 異動者代號
                updateData.setUpdTime(dateTime); // 異動日期時間

                babcml7Dao.updateReviewFeeReviewData(updateData);

                // 紀錄 MMAPLOG
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    FrameworkLogUtil.doUpdateLog(oldUpdateData, updateData);
                }
            }
        }
    }

    /**
     * 依傳入之 受理編號 取得 複檢費用決行 之資料 for 複檢費用決行作業
     * 
     * @param apNo 受理編號
     * @return
     */
    public ReviewFeeDecisionCase getReviewFeeDecisionData(String apNo, UserBean userData) {
        // 取得 BAAPPBASE 資料
        // [
        Baappbase masterData = baappbaseDao.getReviewFeeDecisionHeaderDataBy(apNo);

        if (masterData == null)
            return null;

        ReviewFeeDecisionCase caseData = new ReviewFeeDecisionCase();

        caseData.setBaappbaseId(masterData.getBaappbaseId()); // 資料列編號
        caseData.setApNo(masterData.getApNo()); // 受理編號
        caseData.setSeqNo(masterData.getSeqNo()); // 序號
        caseData.setAppDate(masterData.getAppDate()); // 申請日期
        caseData.setEvtName(masterData.getEvtName()); // 事故者姓名
        caseData.setEvtIdnNo(masterData.getEvtIdnNo()); // 事故者身分證號
        caseData.setEvtBrDate(masterData.getEvtBrDate()); // 事故者出生日期
        caseData.setEvtJobDate(masterData.getEvtJobDate()); // 診斷失能日期
        // ]

        // 取得 BAAPPEXPAND 資料
        // [
        Baappexpand expandData = baappexpandDao.getReviewFeeDecisionHeaderDataBy(caseData.getBaappbaseId());

        if (expandData != null) {
            caseData.setBaappexpandId(expandData.getBaappexpandId()); // 資料編號
            caseData.setCriInJdp1(expandData.getCriInJdp1()); // 失能項目 1
            caseData.setCriInJdp2(expandData.getCriInJdp2()); // 失能項目 2
            caseData.setCriInJdp3(expandData.getCriInJdp3()); // 失能項目 3
            caseData.setCriInJdp4(expandData.getCriInJdp4()); // 失能項目 4
            caseData.setCriInJdp5(expandData.getCriInJdp5()); // 失能項目 5
            caseData.setCriInJdp6(expandData.getCriInJdp6()); // 失能項目 6
            caseData.setCriInJdp7(expandData.getCriInJdp7()); // 失能項目 7
            caseData.setCriInJdp8(expandData.getCriInJdp8()); // 失能項目 8
            caseData.setCriInJdp9(expandData.getCriInJdp9()); // 失能項目 9
            caseData.setCriInJdp10(expandData.getCriInJdp10()); // 失能項目 10
            caseData.setCriInJcl1(expandData.getCriInJcl1()); // 失能等級 1
            caseData.setCriInJcl2(expandData.getCriInJcl2()); // 失能等級 2
            caseData.setCriInJcl3(expandData.getCriInJcl3()); // 失能等級 3
            caseData.setEvTyp(expandData.getEvTyp()); // 傷病分類
            caseData.setEvTypName(expandData.getEvTypName()); // 傷病分類 - 中文 (取自 BAPARAM)
        }
        // ]

        // 取得 BABCML7 資料
        // [
        List<Babcml7> detailList = babcml7Dao.getReviewFeeDecisionListBy(apNo);

        for (Babcml7 detailData : detailList) {
            ReviewFeeDecisionDetailCase detailCaseData = new ReviewFeeDecisionDetailCase();

            detailCaseData.setReApNo(detailData.getReApNo()); // 複檢費用受理編號
            detailCaseData.setApSeq(detailData.getApSeq()); // 複檢費用申請序
            detailCaseData.setApNo(detailData.getApNo()); // 受理編號
            detailCaseData.setProcStat(detailData.getProcStat()); // 處理狀態
            detailCaseData.setInreDate(detailData.getInreDate()); // 通知複檢日期
            detailCaseData.setReasCode(detailData.getReasCode()); // 複檢原因
            detailCaseData.setHosId(detailData.getHosId()); // 醫療院所代碼
            detailCaseData.setRecosDate(detailData.getRecosDate()); // 複檢費用申請日期
            detailCaseData.setReNum(detailData.getReNum()); // 複檢門診次數
            detailCaseData.setRehpStay(detailData.getRehpStay()); // 複檢住院天數
            detailCaseData.setReFees(detailData.getReFees()); // 複檢費用
            detailCaseData.setNonreFees(detailData.getNonreFees()); // 非複檢必須費用
            detailCaseData.setReAmtPay(detailData.getReAmtPay()); // 複檢實付金額
            detailCaseData.setNotifyForm(detailData.getNotifyForm()); // 核定通知書格式
            detailCaseData.setBenIdnNo(detailData.getBenIdnNo()); // 受益人身分證號
            detailCaseData.setBenName(detailData.getBenName()); // 受益人姓名
            detailCaseData.setBenBrDate(detailData.getBenBrDate()); // 受益人出生日期
            detailCaseData.setBenSex(detailData.getBenSex()); // 受益人性別
            detailCaseData.setBenNationTyp(detailData.getBenNationTyp()); // 受益人國籍別
            detailCaseData.setBenNationCode(detailData.getBenNationCode()); // 受益人國籍
            detailCaseData.setBenEvtRel(detailData.getBenEvtRel()); // 受益人與事故者關係
            detailCaseData.setTel1(detailData.getTel1()); // 電話1
            detailCaseData.setTel2(detailData.getTel2()); // 電話2
            detailCaseData.setCommTyp(detailData.getCommTyp()); // 通訊地址別
            detailCaseData.setCommZip(detailData.getCommZip()); // 通訊郵遞區號
            detailCaseData.setCommAddr(detailData.getCommAddr()); // 通訊地址
            detailCaseData.setPayTyp(detailData.getPayTyp()); // 給付方式
            detailCaseData.setBankName(detailData.getBankName()); // 金融機構名稱
            detailCaseData.setPayBankId(detailData.getPayBankId()); // 金融機構總代號
            detailCaseData.setBranchId(detailData.getBranchId()); // 分支代號
            detailCaseData.setPayeeAcc(detailData.getPayeeAcc()); // 銀行帳號
            detailCaseData.setAccName(detailData.getAccName()); // 戶名
            // Other
            detailCaseData.setReasName(detailData.getReasName()); // 複檢原因 - 中文
            detailCaseData.setHosName(detailData.getHosName()); // 醫療院所 - 中文
            detailCaseData.setBenNationName(detailData.getBenNationName()); // 受款人國籍名稱
            detailCaseData.setBenEvtRelName(detailData.getBenEvtRelName()); // 受益人與事故者關係 - 中文
            detailCaseData.setPayTypName(detailData.getPayTypName()); // 給付方式 - 中文

            // caseData.reApNo 須設定, 否則 List 頁面之 Header 資籵將無法顯示複檢費用受理編號
            caseData.setReApNo(detailData.getReApNo()); // 複檢費用受理編號

            caseData.addDetailData(detailCaseData);
        }
        // ]

        // 取得 複核 及 決行 相關資料
        Baappbase excLvlData = baappbaseDao.getReviewFeeDecisionExcLvlBy(apNo, userData.getEmpNo());
        if (excLvlData != null) {
            caseData.setMexcLvl(excLvlData.getMexcLvl()); // 應決行層級
            caseData.setReChk(excLvlData.getReChk()); // 複核是否顯示條件
            caseData.setExeMk(excLvlData.getExeMk()); // 決行是否顯示條件
        }

        return caseData;
    }

    /**
     * 複檢費用決行作業 存檔處理
     * 
     * @param userData
     * @param detailCaseList
     */
    public void updateReviewFeeDecisionData(UserBean userData, List<ReviewFeeDecisionDetailCase> detailCaseList) {
        String dateTime = DateUtility.getNowWestDateTime(true);
        String today = StringUtils.substring(dateTime, 0, 8);

        for (ReviewFeeDecisionDetailCase detailCaseData : detailCaseList) {
            Babcml7 updateData = babcml7Dao.getReviewFeeDecisionDataForUpdateBy(detailCaseData.getReApNo(), detailCaseData.getApSeq());

            if (updateData != null) {
                Babcml7 oldUpdateData = (Babcml7) BeanUtility.cloneBean(updateData);

                updateData.setProcStat(detailCaseData.getProcStat()); // 處理狀態 - 複核 = '30' 決行 = '40' 退件 = '11'
                if (StringUtils.equals(detailCaseData.getProcStat(), "40")) {
                    updateData.setExeDate(today); // 決行日期
                    updateData.setExeMan(userData.getEmpNo()); // 決行人員
                }
                updateData.setUpdUser(userData.getEmpNo()); // 異動者代號
                updateData.setUpdTime(dateTime); // 異動日期時間

                babcml7Dao.updateReviewFeeDecisionData(updateData);

                // 紀錄 MMAPLOG
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    FrameworkLogUtil.doUpdateLog(oldUpdateData, updateData);
                }
            }
        }
    }

    public void setBaappbaseDao(BaappbaseDao baappbaseDao) {
        this.baappbaseDao = baappbaseDao;
    }

    public void setBaappexpandDao(BaappexpandDao baappexpandDao) {
        this.baappexpandDao = baappexpandDao;
    }

    public void setBabcml7Dao(Babcml7Dao babcml7Dao) {
        this.babcml7Dao = babcml7Dao;
    }

    public void setCvldtlDao(CvldtlDao cvldtlDao) {
        this.cvldtlDao = cvldtlDao;
    }
    
    public void setNpbanklistDao(NpbanklistDao npbanklistDao) {
        this.npbanklistDao = npbanklistDao;
    }

    public void setBcbpfDao(BcbpfDao bcbpfDao) {
        this.bcbpfDao = bcbpfDao;
    }

    public void setNppostlistDao(NppostlistDao nppostlistDao) {
        this.nppostlistDao = nppostlistDao;
    }

}
