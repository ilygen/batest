package tw.gov.bli.ba.services;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.dao.BaappbaseDao;
import tw.gov.bli.ba.dao.BafamilyDao;
import tw.gov.bli.ba.dao.BaparamDao;
import tw.gov.bli.ba.dao.Bbcmf07Dao;
import tw.gov.bli.ba.dao.CvldtlDao;
import tw.gov.bli.ba.dao.NpbanklistDao;
import tw.gov.bli.ba.dao.NpcodeDao;
import tw.gov.bli.ba.dao.BcbpfDao;
import tw.gov.bli.ba.dao.PbbmsaDao;
import tw.gov.bli.ba.domain.Baparam;
import tw.gov.bli.ba.domain.Bbcmf07;
import tw.gov.bli.ba.domain.Cvldtl;
import tw.gov.bli.ba.domain.Npbanklist;
import tw.gov.bli.ba.domain.Bcbpf;
import tw.gov.bli.ba.domain.Npcode;
import tw.gov.bli.ba.domain.Pbbmsa;
import tw.gov.bli.ba.update.actions.SurvivorCheckMarkLevelAdjustAction;
import tw.gov.bli.ba.update.cases.BaappbaseDataUpdateCase;
import tw.gov.bli.ba.update.cases.SurvivorCheckMarkLevelAdjustBenDataCase;
import tw.gov.bli.ba.update.cases.SurvivorCheckMarkLevelAdjustBenDetailDataCase;
import tw.gov.bli.ba.update.cases.SurvivorCheckMarkLevelAdjustCase;
import tw.gov.bli.ba.update.cases.DisabledCheckMarkLevelAdjustCase;
import tw.gov.bli.ba.update.cases.CheckMarkLevelAdjustCase;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Ajax Service for 更正作業
 * 
 * @author Rickychi
 */
public class UpdateAjaxService {
    private static Log log = LogFactory.getLog(ReceiptAjaxService.class);
    private BaappbaseDao baappbaseDao;
    private BaparamDao baparamDao;
    private PbbmsaDao pbbmsaDao;
    private Bbcmf07Dao bbcmf07Dao;
    private CvldtlDao cvldtlDao;
    private BafamilyDao bafamilyDao;
    private NpbanklistDao npbanklistDao;
    private NpcodeDao npcodeDao;    
    private BcbpfDao bcbpfDao;

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 處理狀態
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return <code>String</code> 物件
     */
    public String getBaappbaseProcStat(String apNo) {
        String procStat = baappbaseDao.selectProcStatBy(apNo, "0000");
        if (StringUtils.isNotBlank(procStat)) {
            int procStatNum = Integer.valueOf(procStat).intValue();

            if (procStatNum >= 0 && procStatNum <= 9) {
                procStat = "0";
            }
            else if (procStatNum >= 10 && procStatNum <= 19) {
                procStat = "1";
            }
            else if (procStatNum >= 20 && procStatNum <= 29) {
                procStat = "2";
            }
            else if (procStatNum >= 30 && procStatNum <= 39) {
                procStat = "3";
            }
            else if (procStatNum >= 40 && procStatNum <= 49) {
                procStat = "4";
            }
            else if (procStatNum >= 50 && procStatNum <= 59) {
                procStat = "5";
            }
            else if (procStatNum >= 60 && procStatNum <= 69) {
                procStat = "6";
            }
            else if (procStatNum >= 70 && procStatNum <= 79) {
                procStat = "7";
            }
            else if (procStatNum >= 80 && procStatNum <= 89) {
                procStat = "8";
            }
            else if (procStatNum >= 90 && procStatNum <= 99) {
                procStat = "9";
            }
            return procStat;
        }
        else {
            return "";
        }
    }

    /**
     * 依傳入關係條件取得 給付方式(<code>BAAPPBASE</code>) 下拉選單資料
     * 
     * @param relation 關係
     * @return
     */
    public List<Baparam> getPattyOptionList(String relation) {
        if ("Z".equals(relation)) {
            return baparamDao.selectOptionListByUpdate(null, ConstantKey.BAPARAM_PARAMGROUP_PAYTYP, null);
        }
        else {
            return baparamDao.selectOptionListBy(null, ConstantKey.BAPARAM_PARAMGROUP_PAYTYP, null);
        }
    }

    /**
     * 依傳入關係條件取得 申請代算單位(<code>PBBMSA</code>) 下拉式選單資料
     * 
     * @param apNo 受理編號
     * @param brDate 事故者出生日期
     * @param idnNo 事故者身份證字號
     * @return
     */
    public List<Pbbmsa> getOldAplDpt(String apNo, String brDate, String idnNo, String oldAplDpt) {

        List<BaappbaseDataUpdateCase> applyList = baappbaseDao.selectPayeeDataForList(apNo);
        BaappbaseDataUpdateCase baappbaseDataUpdateCase = null;
        String tempString = null;

        for (int i = 0; i < applyList.size(); i++) {
            baappbaseDataUpdateCase = applyList.get(i);
            if (!(oldAplDpt).equals(applyList.get(i).getOldAplDpt())) {
                if (i == 0 && !("").equals(applyList.get(i).getOldAplDpt())) {
                    tempString = baappbaseDataUpdateCase.getOldAplDpt();
                }
                else if (!("").equals(applyList.get(i).getOldAplDpt()) && tempString == null) {
                    tempString = baappbaseDataUpdateCase.getOldAplDpt();
                }
                else if (!("").equals(applyList.get(i).getOldAplDpt()) && tempString != null && i + 1 != applyList.size()) {
                    tempString += "','" + baappbaseDataUpdateCase.getOldAplDpt() + "'";
                }
                else if (!("").equals(applyList.get(i).getOldAplDpt()) && tempString != null && i + 1 == applyList.size()) {
                    tempString += "','" + baappbaseDataUpdateCase.getOldAplDpt();
                }
            }
        }

        return pbbmsaDao.getOldAplDptOptionList(DateUtility.changeDateType(brDate), idnNo, tempString);
    }

    /**
     * 依傳入的 醫療院所代碼 自 醫療院所參數檔 (<code>BBCMF07<code>) 取得 醫療院所名稱
     * 
     * @param hosId 醫院代碼
     * @return
     */
    public Bbcmf07 getHosDataBy(String hosId) {
        return bbcmf07Dao.getDisabledApplicationDataUpdateHosDataBy(hosId);
    }

    /**
     * 依傳入的條件 自 (<code>CVLDTL<code>) 取得 戶籍姓名
     * 
     * @param idNo 身分證號
     * @param brDate 出身日期
     * @return
     */
    public String getCvldtlName(String idNo, String brDate) {
        if (StringUtils.isBlank(idNo) || StringUtils.isBlank(brDate))
            return "";

        if (StringUtils.length(brDate) == 7)
            brDate = DateUtility.changeDateType(brDate);
        else if (StringUtils.length(brDate) == 8 && StringUtils.equals(StringUtils.substring(brDate, 0, 1), "-"))
            brDate = DateUtility.changeDateType(brDate);

        List<Cvldtl> cvlList = cvldtlDao.selectDataBy(idNo, brDate);
        if (cvlList.size() > 0) {
            Cvldtl cvlData = cvlList.get(0);
            return StringUtils.defaultString(cvlData.getName()).trim(); // 戶籍姓名
        }

        return "";
    }

    /**
     * 依傳入條件取得 眷屬檔 for 眷屬資料更正作業
     * 
     * @param apNo 受理編號
     * @param famIdnNo 眷屬身份證字號
     * @param famBrDate 眷屬出生日期
     * @return
     */
    public Integer getDependantDataCount(String apNo, String famIdnNo, String bafamilyId) {
        return bafamilyDao.getDependantDataCount(apNo, famIdnNo, StringUtils.isNotBlank(bafamilyId)?bafamilyId:null);
    }

    /**
     * 依傳入條件取得 眷屬檔 for 眷屬資料更正作業
     * 
     * @param apNo 受理編號
     * @param famIdnNo 眷屬身份證字號
     * @param famBrDate 眷屬出生日期
     * @return
     */
    public Integer getEvtDataCount(String apNo, String famIdnNo, String bafamilyId) {
        return bafamilyDao.getEvtDataCount(apNo, famIdnNo, StringUtils.isNotBlank(bafamilyId)?bafamilyId:null);
    }

    /**
     * 依傳入銀行帳號取得 檢查檢查銀行帳號前7碼(<code>BCBPF</code>)
     * 
     * @param payBankIdBranchId 檢查銀行帳號
     * @return
     */
    public boolean checkBankAccount(String payBankIdBranchId) {
        boolean checkResult = false;
        List<Bcbpf> list = bcbpfDao.selectBankDataBy(payBankIdBranchId);
        if (list.size() == 1) {
            checkResult = true;
        }
        return checkResult;
    }

    /**
     * 依傳入的條件 取得SurvivorCheckMarkLevelAdjustCase中的資料
     * 
     * @param payYm 給付年月
     * @param seqNo 序號
     * @return
     */
    public SurvivorCheckMarkLevelAdjustBenDataCase[] getBenFilterdataList(String payYm, String seqNo) {
        WebContext ctx = WebContextFactory.get();
        HttpServletRequest request = ctx.getHttpServletRequest();
        SurvivorCheckMarkLevelAdjustCase caseData = CaseSessionHelper.getSurvivorCheckMarkLevelAdjustCase(request);

        List<SurvivorCheckMarkLevelAdjustBenDataCase> filterBenDataList = new ArrayList<SurvivorCheckMarkLevelAdjustBenDataCase>();
        if (StringUtils.isBlank(payYm) && StringUtils.isBlank(seqNo))
            return (SurvivorCheckMarkLevelAdjustBenDataCase[]) caseData.getBenDataInBenList().toArray(new SurvivorCheckMarkLevelAdjustBenDataCase[filterBenDataList.size()]);
        if (caseData != null) {
            List<SurvivorCheckMarkLevelAdjustBenDataCase> benDataList = caseData.getBenDataInBenList();
            for (SurvivorCheckMarkLevelAdjustBenDataCase benData : benDataList) {
                if (StringUtils.isBlank(seqNo) || (StringUtils.isNotBlank(seqNo) && StringUtils.equals(seqNo, benData.getSeqNo()))) {
                    SurvivorCheckMarkLevelAdjustBenDataCase filterBenData = new SurvivorCheckMarkLevelAdjustBenDataCase();
                    BeanUtility.copyProperties(filterBenData, benData);
                    if (benData.getDetailListSize() > 0) {
                        if (StringUtils.isNotBlank(payYm)) {
                            List<SurvivorCheckMarkLevelAdjustBenDetailDataCase> filterDetailList = new ArrayList<SurvivorCheckMarkLevelAdjustBenDetailDataCase>();
                            for (SurvivorCheckMarkLevelAdjustBenDetailDataCase detailCase : benData.getDetailList()) {
                                if (StringUtils.equals(payYm, detailCase.getPayYm()))
                                    filterDetailList.add(detailCase);
                            }
                            filterBenData.setDetailList(filterDetailList);
                        }
                        else {
                            filterBenData.setDetailList(benData.getDetailList());
                        }
                    }
                    filterBenDataList.add(filterBenData);
                }
            }
        }
        return (SurvivorCheckMarkLevelAdjustBenDataCase[]) filterBenDataList.toArray(new SurvivorCheckMarkLevelAdjustBenDataCase[filterBenDataList.size()]);
    }

    /**
     * 依傳入的條件 更新SurvivorCheckMarkLevelAdjustCase中的資料
     * 
     * @param baChkFileId 資料列編號
     * @param adjLevel 程度調整
     * @param valisYm 有效年月 - 起
     * @param valieYm 有效年月 - 迄
     * @return
     */
    public void setCheckMarkLevel(String baChkFileId, String adjLevel, String valisYm, String valieYm) {
        WebContext ctx = WebContextFactory.get();
        HttpServletRequest request = ctx.getHttpServletRequest();
        SurvivorCheckMarkLevelAdjustCase caseData = CaseSessionHelper.getSurvivorCheckMarkLevelAdjustCase(request);

        // 民國年月 轉 西元年月
        if (StringUtils.length(valisYm) == 5)
            valisYm = DateUtility.changeChineseYearMonthType(valisYm);
        if (StringUtils.length(valieYm) == 5)
            valieYm = DateUtility.changeChineseYearMonthType(valieYm);

        // 將輸入欄位的值更新到 CaseData 中
        caseData.setDetailListInputData(baChkFileId, adjLevel, valisYm, valieYm);
        CaseSessionHelper.setSurvivorCheckMarkLevelAdjustCase(caseData, request);
    }
    
    /**
     * 依傳入的條件 更新SurvivorCheckMarkLevelAdjustCase中的資料
     * 
     * @param regetCipbMk 重讀CIPB
     * @return
     */
    public void setRegetCipbMk(String apNo, String regetCipbMk) {
        String payCode = apNo.substring(0, 1);
        WebContext ctx = WebContextFactory.get();
        HttpServletRequest request = ctx.getHttpServletRequest();
        
        if (payCode.equals("L")){
            CheckMarkLevelAdjustCase caseData = CaseSessionHelper.getCheckMarkLevelAdjustCase(request);
            // 將輸入欄位的值更新到 CaseData 中
            caseData.setRegetCipbMk(regetCipbMk);    
            CaseSessionHelper.setCheckMarkLevelAdjustCase(caseData, request);
            
        }else if (payCode.equals("K")){
            DisabledCheckMarkLevelAdjustCase caseData = CaseSessionHelper.getDisabledCheckMarkLevelAdjustCase(request);
            // 將輸入欄位的值更新到 CaseData 中
            caseData.setRegetCipbMk(regetCipbMk);    
            CaseSessionHelper.setDisabledCheckMarkLevelAdjustCase(caseData, request); 
            
        }else if (payCode.equals("S")){
            SurvivorCheckMarkLevelAdjustCase caseData = CaseSessionHelper.getSurvivorCheckMarkLevelAdjustCase(request);
            // 將輸入欄位的值更新到 CaseData 中
            caseData.setRegetCipbMk(regetCipbMk);    
            CaseSessionHelper.setSurvivorCheckMarkLevelAdjustCase(caseData, request);            
        }
        

    }
    

    /**
     * 依傳入的條件 取得SurvivorCheckMarkLevelAdjustCase中事故者編審資料
     * 
     * @return
     */
    public SurvivorCheckMarkLevelAdjustBenDetailDataCase[] getEvtDetailDataCase() {
        WebContext ctx = WebContextFactory.get();
        HttpServletRequest request = ctx.getHttpServletRequest();
        SurvivorCheckMarkLevelAdjustCase caseData = CaseSessionHelper.getSurvivorCheckMarkLevelAdjustCase(request);
        return caseData.getEvtDataInBenList().getDetailListArray();
    }
    
    /**
     * 依傳入條件取得 學校 資料
     * 
     * @param schoolName
     * @return
     */
    public List<Npcode> getSchoolListBy(String schoolName) {
        return npcodeDao.selectSchoolListBy(schoolName);
    }    

    public void setBaappbaseDao(BaappbaseDao baappbaseDao) {
        this.baappbaseDao = baappbaseDao;
    }

    public void setBaparamDao(BaparamDao baparamDao) {
        this.baparamDao = baparamDao;
    }

    public void setPbbmsaDao(PbbmsaDao pbbmsaDao) {
        this.pbbmsaDao = pbbmsaDao;
    }

    public void setBbcmf07Dao(Bbcmf07Dao bbcmf07Dao) {
        this.bbcmf07Dao = bbcmf07Dao;
    }

    public void setCvldtlDao(CvldtlDao cvldtlDao) {
        this.cvldtlDao = cvldtlDao;
    }

    public void setBafamilyDao(BafamilyDao bafamilyDao) {
        this.bafamilyDao = bafamilyDao;
    }

    public void setNpbanklistDao(NpbanklistDao npbanklistDao) {
        this.npbanklistDao = npbanklistDao;
    }

    public void setNpcodeDao(NpcodeDao npcodeDao) {
        this.npcodeDao = npcodeDao;
    }    
    
    public void setBcbpfDao(BcbpfDao bcbpfDao) {
        this.bcbpfDao = bcbpfDao;
    }

}
