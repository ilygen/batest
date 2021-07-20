package tw.gov.bli.ba.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.dao.*;
import tw.gov.bli.ba.domain.*;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.rpt.cases.*;
import tw.gov.bli.ba.update.cases.DisabledApplicationDataUpdateBareCheckCase;
import tw.gov.bli.ba.update.cases.DisabledApplicationDataUpdateCheckFileCase;
import tw.gov.bli.ba.util.BaCiptUtility;
import tw.gov.bli.ba.util.BaReportReplaceUtility;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.Pbm0001;
import tw.gov.bli.ba.util.RptTitleUtility;
import tw.gov.bli.common.domain.UserInfo;

/**
 * Service for 列印作業
 * 
 * @author Goston
 */
public class RptService {
    private static Log log = LogFactory.getLog(RptService.class);

    private BaappbaseDao baappbaseDao;
    private BabatchjobDao babatchjobDao;
    private BadaprDao badaprDao;
    private BachkfileDao bachkfileDao;
    private BaexalistDao baexalistDao;
    private LnmDao lnmDao;
    private LnmdDao lnmdDao;
    private BaparamDao baparamDao;
    private Balp0d330Dao balp0d330Dao;
    private Balp0d340Dao balp0d340Dao;
    private PbbmsaDao pbbmsaDao;
    private BanotifyDao banotifyDao;
    private BapaissudateDao bapaissudateDao;
    private BaoncepayDao baoncepayDao;
    private BirefDao birefDao;
    private NbappbaseDao nbappbaseDao;
    private NbdaprDao nbdaprDao;
    private CiptDao ciptDao;
    private CaubDao caubDao;
    private BadupeidnDao badupeidnDao;
    private BagivetmpdtlDao bagivetmpdtlDao;
    private BapayrptrecordDao bapayrptrecordDao;
    private BapflbacDao bapflbacDao;
    private BarxfDao barxfDao;
    private BbarfDao bbarfDao;
    private BapapaykindDao bapapaykindDao;
    private KcafDao kcafDao;
    private MaadmrecDao maadmrecDao;
    private Balp0d020Dao balp0d020Dao;
    private BaappexpandDao baappexpandDao;
    private BafamilyDao bafamilyDao;
    private Babcml7Dao babcml7Dao;
    private Bbcmf07Dao bbcmf07Dao;
    private CipbDao cipbDao;
    private CipgDao cipgDao;
    private BastudtermDao bastudtermDao;
    private BaunacpdtlDao baunacpdtlDao;
    private BacountryDao bacountryDao;
    private BaarclistDao baarclistDao;
    private BapayrptsumDao bapayrptsumDao;
    private BapayrptaccountDao bapayrptaccountDao;
    private LocalPfpcckyDao localPfpcckyDao;
    private BapaavgmonDao bapaavgmonDao;
    private BarptlogDao barptlogDao;
    private BapasignDao bapasignDao;
    private Bbcmf09Dao bbcmf09Dao;
    private BarecheckDao barecheckDao;
    private BapairrDao bapairrDao;
    private BabasicamtDao babasicamtDao;
    private BastudnotifyDao bastudnotifyDao;
    private BapandomkDao bapandomkDao;
    private NpcodeDao npcodeDao;
    private BafailurelistDao bafailurelistDao;
    private BaunqualifiednoticeDao baunqualifiednoticeDao;
    private SdholidayDao sdholidayDao;
    private BahandicaptermDao bahandicaptermDao;
    private UpdateService updateService;
    private CvldtlDao cvldtlDao;
    private BaappattfileDao baappattfileDao;
    /**
     * 依傳入的條件取得 勞保老年年金給付受理編審清單 的資料
     * 
     * @param apNoBegin 受理編號 (起)
     * @param apNoEnd 受理編號 (迄)
     * @return
     */
    public List<OldAgeReviewRpt01Case> getOldAgeReviewRpt01DataBy(String apNoBegin, String apNoEnd) {

        List<OldAgeReviewRpt01Case> caseList = new ArrayList<OldAgeReviewRpt01Case>();

        // 依傳入條件取得 給付主檔 (BAAPPBASE) 符合條件之 受理編號 (APNO)
        List<String> apNoList = baappbaseDao.getOldAgeReviewRpt01ApNoListBy(apNoBegin, apNoEnd);

        for (String apNo : apNoList) {
            OldAgeReviewRpt01Case caseData = new OldAgeReviewRpt01Case();

            // 取得 給付主檔 (BAAPPBASE) 事故者資料

            Baappbase evtData = baappbaseDao.selectOldAgeReviewRpt01EvtDataBy(apNo);
            BeanUtility.copyProperties(caseData, evtData);

            // 取得 給付案件附件檔 (BAAPPATTFILE) 附件資料
            Baappattfile baappattfile = baappattfileDao.selectFile(evtData.getApNo());
            if (baappattfile != null) {
                caseData.setConsent(baappattfile.getAttFile());

            }
            // 讀取事故者國籍資料

            caseData.setBenNationName(bacountryDao.selectCNameData(caseData.getBenNationCode()));

            // 20090421 新增 讀取關鍵欄位變更檔資料
            List<Kcaf> evtKcafList = getKcafListBy(evtData.getEvtIdnNo(), evtData.getEvtBrDate());

            String evtIdnNo = evtData.getEvtIdnNo();
            String evtBrDate = evtData.getEvtBrDate();
            String evtJobDate = evtData.getEvtJobDate();
            String evtIdnNoForCIPTPG = evtData.getEvtIdnNo(); // for CIPT&CIPG
            // 檢核是否有身分證重號的情況

            if (StringUtils.equals(evtData.getDupeIdnNoMk(), "2")) {
                Badupeidn dupeIdnData = badupeidnDao.getOldAgeReviewRpt01DupeIdnDataBy(apNo);
                if (dupeIdnData != null) {
                    if (StringUtils.isNotBlank(dupeIdnData.getIdnNo())) {
                        evtIdnNo = StringUtils.substring(dupeIdnData.getIdnNo().trim(), 0, 10);
                        evtIdnNoForCIPTPG = dupeIdnData.getIdnNo();
                    }

                    if (StringUtils.isNotBlank(dupeIdnData.getBrDate()))
                        evtBrDate = dupeIdnData.getBrDate().trim();
                }
            }

            // 取得 給付主檔 (BAAPPBASE) 受款人資料
            Baappbase benDataForA060 = null;
            List<OldAgeReviewRpt01BenDataCase> benList = new ArrayList<OldAgeReviewRpt01BenDataCase>();
            List<Baappbase> benDataList = baappbaseDao.selectOldAgeReviewRpt01BenListBy(apNo);
            for (Baappbase benData : benDataList) {

                if (benDataForA060 == null && (benData.getBenEvtRel().equals("2") || benData.getBenEvtRel().equals("3") || benData.getBenEvtRel().equals("4") || benData.getBenEvtRel().equals("5") || benData.getBenEvtRel().equals("6")
                                || benData.getBenEvtRel().equals("7"))) {

                    benDataForA060 = new Baappbase();
                    BeanUtility.copyProperties(benDataForA060, benData);
                }

                OldAgeReviewRpt01BenDataCase benDataCase = new OldAgeReviewRpt01BenDataCase();
                BeanUtility.copyProperties(benDataCase, benData);

                // 讀取受益人國籍資料
                benDataCase.setBenNationName(bacountryDao.selectCNameData(benDataCase.getBenNationCode()));

                // 取得 每位受款人 給付核定檔 (BADAPR) 受款人給付資料

                List<OldAgeReviewRpt01BenPayDataCase> benPayCaseList = new ArrayList<OldAgeReviewRpt01BenPayDataCase>();
                List<Badapr> benPayDataList = badaprDao.getOldAgeReviewRpt01BenPayListBy(apNo, benData.getSeqNo(), benData.getIssuYm());
                for (Badapr benPayData : benPayDataList) {
                    OldAgeReviewRpt01BenPayDataCase benPayCase = new OldAgeReviewRpt01BenPayDataCase();
                    BeanUtility.copyProperties(benPayCase, benPayData);
                    benPayCaseList.add(benPayCase);
                }
                benDataCase.setBenPayList(benPayCaseList);

                // 取得 每位受款人 給付編審檔 (BACHKFILE) 受款人編審註記

                List<OldAgeReviewRpt01ChkfileDataCase> benChkfileDataCaseList = new ArrayList<OldAgeReviewRpt01ChkfileDataCase>();
                List<Bachkfile> benChkfileDataList = bachkfileDao.getOldAgeReviewRpt01BenChkfileDataBy(apNo, benData.getSeqNo(), benData.getIssuYm());
                for (Bachkfile benChkfileData : benChkfileDataList) {
                    OldAgeReviewRpt01ChkfileDataCase benChkfileDataCase = new OldAgeReviewRpt01ChkfileDataCase();
                    BeanUtility.copyProperties(benChkfileDataCase, benChkfileData);
                    benChkfileDataCaseList.add(benChkfileDataCase);
                }
                benDataCase.setChkfileDataList(benChkfileDataCaseList);

                benList.add(benDataCase);
            }
            caseData.setBenList(benList);

            // 取得 給付核定檔 (BADAPR) 前次核定金額
            Badapr onceIssuCalcAmt = badaprDao.selectOnceIssuCalcAmtBy(apNo);
            if (onceIssuCalcAmt != null) {
                caseData.setIssuCalcAmt(onceIssuCalcAmt.getIssuCalcAmt());
            }

            // 取得 給付核定檔 (BADAPR) 核定總額資料
            OldAgeReviewRpt01IssueAmtDataCase issueAmtCase = new OldAgeReviewRpt01IssueAmtDataCase();
            Badapr issueAmtData = badaprDao.getOldAgeReviewRpt01IssueAmtDataBy(apNo, evtData.getIssuYm());
            if (issueAmtData != null) {
                BeanUtility.copyProperties(issueAmtCase, issueAmtData);
            }
            caseData.setIssueAmtData(issueAmtCase);

            // 取得 給付核定檔 (BADAPR) 給付資料
            List<OldAgeReviewRpt01PayDataCase> payList = new ArrayList<OldAgeReviewRpt01PayDataCase>();
            List<Badapr> payDataList = badaprDao.getOldAgeReviewRpt01PayListBy(apNo, evtData.getIssuYm());
            for (Badapr payData : payDataList) {
                OldAgeReviewRpt01PayDataCase payDataCase = new OldAgeReviewRpt01PayDataCase();
                BeanUtility.copyProperties(payDataCase, payData);
                payList.add(payDataCase);
            }
            caseData.setPayList(payList);

            // 取得 給付核定檔 (BADAPR) 核定資料
            OldAgeReviewRpt01DecideDataCase decideCase = new OldAgeReviewRpt01DecideDataCase();
            Badapr decideData = badaprDao.getOldAgeReviewRpt01DecideDataBy(apNo, evtData.getIssuYm());
            if (decideData != null) {
                BeanUtility.copyProperties(decideCase, decideData);
            }
            caseData.setDecideData(decideCase);

            // 取得 一次請領給付資料檔 (BAONCEPAY) 事故者死亡一次給付相關資料

            OldAgeReviewRpt01DieOncePayDataCase dieOncePayCase = new OldAgeReviewRpt01DieOncePayDataCase();
            Baoncepay dieOncePayData = baoncepayDao.getOldAgeReviewRpt01DieOncePayDataBy(apNo);
            if (dieOncePayData != null) {
                BeanUtility.copyProperties(dieOncePayCase, dieOncePayData);
            }
            caseData.setDieOncePayData(dieOncePayCase);

            // 取得 給付編審檔 (BACHKFILE) 編審資料 - 事故者編審註記

            List<OldAgeReviewRpt01ChkfileDataCase> chkfileCaseList = new ArrayList<OldAgeReviewRpt01ChkfileDataCase>();
            List<Bachkfile> chkfileDataList = bachkfileDao.getOldAgeReviewRpt01ChkfileDataBy(apNo, evtData.getIssuYm());
            for (Bachkfile chkfileData : chkfileDataList) {
                OldAgeReviewRpt01ChkfileDataCase chkfileCase = new OldAgeReviewRpt01ChkfileDataCase();
                BeanUtility.copyProperties(chkfileCase, chkfileData);
                chkfileCaseList.add(chkfileCase);
            }
            caseData.setChkfileDataList(chkfileCaseList);

            // 取得 給付編審檔 (BACHKFILE) 編審資料 - 編審註記說明
            List<OldAgeReviewRpt01ChkfileDescCase> chkfileDescCaseList = new ArrayList<OldAgeReviewRpt01ChkfileDescCase>();
            List<Bachkfile> chkfileDescDataList = bachkfileDao.getOldAgeReviewRpt01ChkfileDescBy(apNo, evtData.getIssuYm());
            for (Bachkfile chkfileDescData : chkfileDescDataList) {
                OldAgeReviewRpt01ChkfileDescCase chkfileDescCase = new OldAgeReviewRpt01ChkfileDescCase();
                BeanUtility.copyProperties(chkfileDescCase, chkfileDescData);
                chkfileDescCaseList.add(chkfileDescCase);
            }
            caseData.setChkfileDescList(chkfileDescCaseList);

            // 取得 核定通知書格式檔 (BANOTIFY 核定通知書資料

            // [
            // a. 若核定通知書格式為 999 或為 空白 及 NULL, 則整段核定通知書不顯示
            // b. 若查無受理案件核定通知書格式, 則整段核定通知書不顯示
            if (StringUtils.isNotBlank(evtData.getNotifyForm()) && !StringUtils.equals(evtData.getNotifyForm(), "999")) {
                BaReportReplaceUtility strReplace = new BaReportReplaceUtility(evtData, true, null, false, benDataForA060);

                // 取得 核定通知書 - 主旨
                List<Banotify> subjectList = banotifyDao.getOldAgeReviewRpt01NotifyListBy(StringUtils.substring(apNo, 0, 1), evtData.getNotifyForm(), "0");
                if (subjectList.size() > 0) {
                    OldAgeReviewRpt01NotifyDataCase notifyCase = new OldAgeReviewRpt01NotifyDataCase();
                    notifyCase.setSubject(strReplace.replace(StringUtils.defaultString(subjectList.get(0).getDataCont())));

                    // 取得 核定通知書 - 說明
                    List<Banotify> contentList = banotifyDao.getOldAgeReviewRpt01NotifyListBy(StringUtils.substring(apNo, 0, 1), evtData.getNotifyForm(), "1");
                    List<String> contents = new ArrayList<String>();
                    for (Banotify contentData : contentList) {
                        if (StringUtils.isNotBlank(contentData.getDataCont()))
                            contents.add(strReplace.replace(contentData.getDataCont()));
                    }
                    notifyCase.setContent(contents);

                    caseData.setNotifyData(notifyCase);
                }
            }
            // ]

            // 取得 請領同類給付資料
            // [
            // 取得 現金給付參考檔 (PBBMSA) 一次給付資料

            List<OldAgeReviewRpt01OncePayDataCase> oncePayCaseList = new ArrayList<OldAgeReviewRpt01OncePayDataCase>();
            List<Pbbmsa> oncePayDataList = pbbmsaDao.getOldAgeReviewRpt01OncePayListBy(evtIdnNo, evtBrDate);
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                oncePayDataList.addAll(pbbmsaDao.getOldAgeReviewRpt01OncePayListBy(StringUtils.substring(kcaf.getBIdn(), 0, 10), kcaf.getBBrDte()));
            }
            for (Pbbmsa oncePayData : oncePayDataList) {
                OldAgeReviewRpt01OncePayDataCase oncePayDataCase = new OldAgeReviewRpt01OncePayDataCase();
                BeanUtility.copyProperties(oncePayDataCase, oncePayData);
                oncePayCaseList.add(oncePayDataCase);
            }

            caseData.setOncePayList(oncePayCaseList);

            // 取得 年金給付資料
            List<OldAgeReviewRpt01AnnuityPayDataCase> annuityPayCaseList = new ArrayList<OldAgeReviewRpt01AnnuityPayDataCase>();
            List<Baappbase> annuityPayDataList = baappbaseDao.getOldAgeReviewRpt01AnnuityPayListBy(apNo, evtData.getEvtIdnNo(), evtData.getEvtBrDate());
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                annuityPayDataList.addAll(baappbaseDao.getOldAgeReviewRpt01AnnuityPayListBy(apNo, StringUtils.substring(kcaf.getBIdn(), 0, 10), kcaf.getBBrDte()));
            }
            for (Baappbase annuityPayData : annuityPayDataList) {
                OldAgeReviewRpt01AnnuityPayDataCase annuityPayDataCase = new OldAgeReviewRpt01AnnuityPayDataCase();

                BeanUtility.copyProperties(annuityPayDataCase, annuityPayData);

                Badapr annuityPayBadaprData = badaprDao.getOldAgeReviewRpt01AnnuityPayDataBy(annuityPayData.getApNo(), annuityPayData.getIssuYm(), annuityPayData.getPayYm());
                if (annuityPayBadaprData != null) {
                    annuityPayDataCase.setChkDate(annuityPayBadaprData.getChkDate()); // 核定日期
                    annuityPayDataCase.setAplpayDate(annuityPayBadaprData.getAplpayDate()); // 帳務日期
                    annuityPayDataCase.setRecAmt(annuityPayBadaprData.getRecAmt()); // 收回金額
                    annuityPayDataCase.setSupAmt(annuityPayBadaprData.getSupAmt()); // 補發金額
                }

                Maadmrec annuityPayMaadmrecData = maadmrecDao.getOldAgeReviewRpt01AnnuityPayDataBy(annuityPayData.getApNo(), annuityPayData.getIssuYm());
                if (annuityPayMaadmrecData != null) {
                    annuityPayDataCase.setProdate(annuityPayMaadmrecData.getProDate()); // 承辦 / 創收日期
                    annuityPayDataCase.setNdomk1(annuityPayMaadmrecData.getNdomk1()); // 處理註記一
                }

                annuityPayCaseList.add(annuityPayDataCase);
            }
            caseData.setAnnuityPayList(annuityPayCaseList);
            // ]

            // 取得 請領他類給付資料
            // [
            // 取得 現金給付參考檔 (PBBMSA) 申請失能給付記錄資料
            List<OldAgeReviewRpt01DisablePayDataCase> disablePayCaseList = new ArrayList<OldAgeReviewRpt01DisablePayDataCase>();
            List<Pbbmsa> disablePayDataList = pbbmsaDao.getOldAgeReviewRpt01DisablePayListBy(evtIdnNo, evtBrDate);
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                disablePayDataList.addAll(pbbmsaDao.getOldAgeReviewRpt01DisablePayListBy(StringUtils.substring(kcaf.getBIdn(), 0, 10), kcaf.getBBrDte()));
            }
            for (Pbbmsa disablePayData : disablePayDataList) {
                OldAgeReviewRpt01DisablePayDataCase disablePayDataCase = new OldAgeReviewRpt01DisablePayDataCase();
                BeanUtility.copyProperties(disablePayDataCase, disablePayData);
                disablePayCaseList.add(disablePayDataCase);
            }
            caseData.setDisablePayList(disablePayCaseList);

            // 取得 年金給付主檔 (BAAPPBASE A, BAAPPEXPAND C) 申請失能年金記錄資料 disableAnnuityPayList
            List<OldAgeReviewRpt01DisableAnnuityPayDataCase> disableAnnuityPayCaseList = new ArrayList<OldAgeReviewRpt01DisableAnnuityPayDataCase>();
            List<Baappbase> disableAnnuityPayDataList = baappbaseDao.getOldAgeReviewRpt01DisableAnnuityPayListBy(apNo, evtIdnNo);
            for (Baappbase disableAnnuityPayData : disableAnnuityPayDataList) {
                OldAgeReviewRpt01DisableAnnuityPayDataCase disableAnnuityPayCase = new OldAgeReviewRpt01DisableAnnuityPayDataCase();
                BeanUtility.copyProperties(disableAnnuityPayCase, disableAnnuityPayData);
                disableAnnuityPayCaseList.add(disableAnnuityPayCase);
            }

            caseData.setDisableAnnuityPayList(disableAnnuityPayCaseList);

            // 取得 現金給付參考檔 (PBBMSA) 申請死亡給付記錄資料
            List<OldAgeReviewRpt01DiePayDataCase> diePayCaseList = new ArrayList<OldAgeReviewRpt01DiePayDataCase>();
            List<Pbbmsa> diePayDataList = pbbmsaDao.getOldAgeReviewRpt01DiePayListBy(evtIdnNo, evtBrDate);
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                diePayDataList.addAll(pbbmsaDao.getOldAgeReviewRpt01DiePayListBy(StringUtils.substring(kcaf.getBIdn(), 0, 10), kcaf.getBBrDte()));
            }
            for (Pbbmsa diePayData : diePayDataList) {
                OldAgeReviewRpt01DiePayDataCase diePayDataCase = new OldAgeReviewRpt01DiePayDataCase();
                BeanUtility.copyProperties(diePayDataCase, diePayData);
                diePayCaseList.add(diePayDataCase);
            }
            caseData.setDiePayList(diePayCaseList);

            // 取得 年金給付主檔 (BAAPPBASE) 申請遺屬年金記錄資料 survivorAnnuityPayList
            List<OldAgeReviewRpt01SurvivorAnnuityPayDataCase> survivorAnnuityPayCaseList = new ArrayList<OldAgeReviewRpt01SurvivorAnnuityPayDataCase>();
            List<Baappbase> survivorAnnuityPayDataList = baappbaseDao.getOldAgeReviewRpt01SurvivorAnnuityPayListBy(evtIdnNo);
            for (Baappbase survivorAnnuityPayData : survivorAnnuityPayDataList) {
                OldAgeReviewRpt01SurvivorAnnuityPayDataCase survivorAnnuityPayCase = new OldAgeReviewRpt01SurvivorAnnuityPayDataCase();
                BeanUtility.copyProperties(survivorAnnuityPayCase, survivorAnnuityPayData);
                survivorAnnuityPayCaseList.add(survivorAnnuityPayCase);
            }

            caseData.setSurvivorAnnuityPayList(survivorAnnuityPayCaseList);

            // 取得 現金給付參考檔 (PBBMSA) 申請傷病給付記錄資料
            List<OldAgeReviewRpt01InjuryPayDataCase> injuryPayCaseList = new ArrayList<OldAgeReviewRpt01InjuryPayDataCase>();
            List<Pbbmsa> injuryPayDataList = pbbmsaDao.getOldAgeReviewRpt01InjuryPayListBy(evtIdnNo, evtBrDate, evtJobDate);
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                injuryPayDataList.addAll(pbbmsaDao.getOldAgeReviewRpt01InjuryPayListBy(StringUtils.substring(kcaf.getBIdn(), 0, 10), kcaf.getBBrDte(), evtJobDate));
            }
            for (Pbbmsa injuryPayData : injuryPayDataList) {
                OldAgeReviewRpt01InjuryPayDataCase injuryPayDataCase = new OldAgeReviewRpt01InjuryPayDataCase();
                BeanUtility.copyProperties(injuryPayDataCase, injuryPayData);
                injuryPayCaseList.add(injuryPayDataCase);
            }
            caseData.setInjuryPayList(injuryPayCaseList);

            // 取得 就保給付檔 (BIREF) 申請失業給付記錄資料
            List<OldAgeReviewRpt01JoblessPayDataCase> joblessPayCaseList = new ArrayList<OldAgeReviewRpt01JoblessPayDataCase>();
            List<Biref> joblessPayDataList = birefDao.getOldAgeReviewRpt01JoblessPayListBy(evtIdnNo, evtBrDate);
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                joblessPayDataList.addAll(birefDao.getOldAgeReviewRpt01JoblessPayListBy(StringUtils.substring(kcaf.getBIdn(), 0, 10), kcaf.getBBrDte()));
            }
            for (Biref joblessPayData : joblessPayDataList) {
                OldAgeReviewRpt01JoblessPayDataCase joblessPayDataCase = new OldAgeReviewRpt01JoblessPayDataCase();
                BeanUtility.copyProperties(joblessPayDataCase, joblessPayData);
                joblessPayCaseList.add(joblessPayDataCase);
            }
            caseData.setJoblessPayList(joblessPayCaseList);

            // 取得 國保給付主檔 (NBAPPBASE) 申請國保給付記錄資料
            List<OldAgeReviewRpt01NpPayDataCase> npPayCaseList = new ArrayList<OldAgeReviewRpt01NpPayDataCase>();
            List<Nbappbase> npPayDataList = nbappbaseDao.getOldAgeReviewRpt01NpPayListBy(evtData.getEvtIds());
            for (Nbappbase npPayData : npPayDataList) {
                OldAgeReviewRpt01NpPayDataCase npPayDataCase = new OldAgeReviewRpt01NpPayDataCase();
                BeanUtility.copyProperties(npPayDataCase, npPayData);

                // 取得 國保給付核定檔 (NBDAPR) 申請國保給付記錄資料
                Nbdapr npPayDetailData = nbdaprDao.getOldAgeReviewRpt01NpPayDetailDataBy(npPayData.getApNo(), npPayData.getIssuYm(), npPayData.getPayYm());
                if (npPayDetailData != null) {
                    npPayDataCase.setChkDt(npPayDetailData.getChkDt()); // 核定日期
                    npPayDataCase.setAplPayDate(npPayDetailData.getAplPayDate()); // 帳務日期
                }

                npPayCaseList.add(npPayDataCase);
            }
            caseData.setNpPayList(npPayCaseList);
            // ]

            // 取得 給付核定檔 (BADAPR) 事故者於受款人給付資料的資料
            List<OldAgeReviewRpt01BenPayDataCase> evtBenPayCaseList = new ArrayList<OldAgeReviewRpt01BenPayDataCase>();
            List<Badapr> evtBenPayDataList = badaprDao.getOldAgeReviewRpt01BenPayListBy(apNo, evtData.getSeqNo(), evtData.getIssuYm());
            for (Badapr evtBenPayData : evtBenPayDataList) {
                OldAgeReviewRpt01BenPayDataCase evtBenPayCase = new OldAgeReviewRpt01BenPayDataCase();
                BeanUtility.copyProperties(evtBenPayCase, evtBenPayData);
                evtBenPayCaseList.add(evtBenPayCase);
            }
            caseData.setEvtBenPayList(evtBenPayCaseList);

            // 取得 被保險人異動資料檔 (CIPT) 承保異動資料
            // List<OldAgeReviewRpt01ChangeDataCase> changeDataCaseList = new ArrayList<OldAgeReviewRpt01ChangeDataCase>();
            // List<Cipt> changeDataList = ciptDao.getOldAgeReviewRpt01ChangeListBy(evtIdnNo);
            // for (Cipt changeData : changeDataList) {
            // OldAgeReviewRpt01ChangeDataCase changeDataCase = new OldAgeReviewRpt01ChangeDataCase();
            // BeanUtility.copyProperties(changeDataCase, changeData);
            // changeDataCaseList.add(changeDataCase);
            // }

            // caseData.setChangeList(BaCiptUtility.getCiptUtilityCase(evtIdnNo));
            caseData.setChangeList(BaCiptUtility.getCiptUtilityCase(apNo, evtIdnNoForCIPTPG));

            // 取得 投保單位檔 (CAUB) 投保單位資料
            // [
            // 申請單位
            if (StringUtils.isNotBlank(evtData.getApUbno())) {
                Caub applyUnitData = caubDao.getOldAgeReviewRpt01UnitDataBy(evtData.getApUbno());
                if (applyUnitData != null) {
                    OldAgeReviewRpt01UnitCase applyUnitDataCase = new OldAgeReviewRpt01UnitCase();
                    BeanUtility.copyProperties(applyUnitDataCase, applyUnitData);
                    caseData.setApplyUnitData(applyUnitDataCase);
                }
            }

            // 最後單位

            if (StringUtils.isNotBlank(evtData.getLsUbno())) {
                Caub lastUnitData = caubDao.getOldAgeReviewRpt01UnitDataBy(evtData.getLsUbno());
                if (lastUnitData != null) {
                    OldAgeReviewRpt01UnitCase lastUnitDataCase = new OldAgeReviewRpt01UnitCase();
                    BeanUtility.copyProperties(lastUnitDataCase, lastUnitData);
                    caseData.setLastUnitData(lastUnitDataCase);
                }
            }
            // ]

            // 取得 本次紓困貸款資料
            // [
            OldAgeReviewRpt01LoanDataCase loanCase = new OldAgeReviewRpt01LoanDataCase();
            Badapr loanData = badaprDao.getOldAgeReviewRpt01LoanDataBy(apNo, evtData.getIssuYm());
            if (loanData != null) {
                BeanUtility.copyProperties(loanCase, loanData);
            }
            caseData.setLoanData(loanCase);
            // ]

            // 取得 另案扣減資料
            // [
            // 一次給付

            List<OldAgeReviewRpt01DeductOnceDataCase> deductOnceList = new ArrayList<OldAgeReviewRpt01DeductOnceDataCase>();
            List<Barxf> barxfList = barxfDao.selectOldAgeReviewRpt01DeductOnceListBy(evtData.getEvtIdnNo(), evtData.getEvtBrDate());
            for (Barxf deductOnceData : barxfList) {
                OldAgeReviewRpt01DeductOnceDataCase deductOnceCase = new OldAgeReviewRpt01DeductOnceDataCase();
                BeanUtility.copyProperties(deductOnceCase, deductOnceData);
                deductOnceList.add(deductOnceCase);
            }
            caseData.setDeductOnceList(deductOnceList);

            // 年金給付
            List<OldAgeReviewRpt01DeductAnnuityDataCase> deductAnnuityList = new ArrayList<OldAgeReviewRpt01DeductAnnuityDataCase>();
            List<Baunacpdtl> baunacpdtlList = baunacpdtlDao.selectOldAgeReviewRpt01DeductAnnuityListBy(evtData.getEvtIdnNo());
            for (Baunacpdtl deductAnnuityData : baunacpdtlList) {
                OldAgeReviewRpt01DeductAnnuityDataCase deductAnnuityCase = new OldAgeReviewRpt01DeductAnnuityDataCase();
                BeanUtility.copyProperties(deductAnnuityCase, deductAnnuityData);
                if (StringUtils.isNotBlank(deductAnnuityData.getApNo()) && StringUtils.isNotBlank(deductAnnuityData.getSeqNo())) {
                    deductAnnuityCase.setBenName(baappbaseDao.getOldAgeReviewRpt01DeductAnnuityNameBy(deductAnnuityData.getApNo(), deductAnnuityData.getSeqNo()));
                }
                deductAnnuityList.add(deductAnnuityCase);
            }
            caseData.setDeductAnnuityList(deductAnnuityList);
            // ]

            // 平均薪資
            // [

            Integer appMonth = null;
            String appMonthStr = "";
            appMonth = bapaavgmonDao.getBapaavgmonCount();
            String realAvgMon = cipgDao.getRealAvgMonForOldAge(apNo, "0000", evtIdnNoForCIPTPG);

            if (appMonth == 0) {
                appMonthStr = "60";
            }
            else {
                appMonthStr = bapaavgmonDao.getBapaavgmonAppMonth(caseData.getAppDate());
                if (StringUtils.isBlank(appMonthStr)) {
                    appMonthStr = "60";
                }
            }

            List<OldAgeReviewRpt01MonthAvgAmtDataCase> monthAvgAmtList = new ArrayList<OldAgeReviewRpt01MonthAvgAmtDataCase>();
            List<Cipg> CipgList = cipgDao.selectOldAgeReviewRpt01SixtyMonthAvgAmtList(apNo, "0000", evtIdnNoForCIPTPG, appMonthStr);
            for (Cipg monthAvgAmtData : CipgList) {
                OldAgeReviewRpt01MonthAvgAmtDataCase monthAvgAmtCase = new OldAgeReviewRpt01MonthAvgAmtDataCase();
                BeanUtility.copyProperties(monthAvgAmtCase, monthAvgAmtData);
                monthAvgAmtList.add(monthAvgAmtCase);
            }
            caseData.setMonthAvgAmtList(monthAvgAmtList);
            caseData.setAppMonth(appMonthStr); // 平均薪資月數
            caseData.setRealAvgMon(realAvgMon); // 實際均薪月數
            // ]

            // 一次平均薪資
            // [
            List<OldAgeReviewRpt01OnceAvgAmtDataCase> onceAvgAmtList = new ArrayList<OldAgeReviewRpt01OnceAvgAmtDataCase>();
            List<Cipb> CipbList = cipbDao.selectOldAgeReviewRpt01OnceAvgAmt(apNo, "0000", evtIdnNoForCIPTPG);
            for (Cipb onceAvgAmtData : CipbList) {
                OldAgeReviewRpt01OnceAvgAmtDataCase onceAvgAmtCase = new OldAgeReviewRpt01OnceAvgAmtDataCase();
                BeanUtility.copyProperties(onceAvgAmtCase, onceAvgAmtData);
                onceAvgAmtList.add(onceAvgAmtCase);
            }
            caseData.setOnceAvgAmtList(onceAvgAmtList);

            // ]

            caseList.add(caseData);
        }

        return caseList;

    }

    /**
     * 依傳入的條件取得 勞保失能年金給付受理編審清單 的資料
     * 
     * @param apNoBegin 受理編號 (起)
     * @param apNoEnd 受理編號 (迄)
     * @return
     */
    public List<DisableReviewRpt01Case> getDisableReviewRpt01DataBy(String apNoBegin, String apNoEnd) {

        List<DisableReviewRpt01Case> caseList = new ArrayList<DisableReviewRpt01Case>();

        // 依傳入條件取得 給付主檔 (BAAPPBASE) 符合條件之 受理編號 (APNO)
        List<String> apNoList = baappbaseDao.getDisableReviewRpt01ApNoListBy(apNoBegin, apNoEnd);

        for (String apNo : apNoList) {
            DisableReviewRpt01Case caseData = new DisableReviewRpt01Case();

            // 取得 給付主檔 (BAAPPBASE) 事故者資料

            Baappbase evtData = baappbaseDao.selectDisableReviewRpt01EvtDataBy(apNo);
            BeanUtility.copyProperties(caseData, evtData);

            // 20090421 新增 讀取關鍵欄位變更檔資料
            List<Kcaf> evtKcafList = getKcafListBy(evtData.getEvtIdnNo(), evtData.getEvtBrDate());

            String evtIdnNo = evtData.getEvtIdnNo();
            String evtBrDate = evtData.getEvtBrDate();
            // String evtJobDate = evtData.getEvtJobDate();
            // String evtIssuYm = evtData.getIssuYm();

            // 取得 給付主檔 (BAAPPBASE) 受款人資料

            List<DisableReviewRpt01BenDataCase> benList = new ArrayList<DisableReviewRpt01BenDataCase>();
            List<Baappbase> benDataList = baappbaseDao.selectDisableReviewRpt01BenListBy(apNo);
            
            for (Baappbase benData : benDataList) {

                DisableReviewRpt01BenDataCase benDataCase = new DisableReviewRpt01BenDataCase();
                BeanUtility.copyProperties(benDataCase, benData);

                // 取得 每位受款人 給付核定檔 (BADAPR) 受款人給付資料

                List<DisableReviewRpt01BenPayDataCase> benPayCaseList = new ArrayList<DisableReviewRpt01BenPayDataCase>();
                List<Badapr> benPayDataList = badaprDao.getDisableReviewRpt01BenPayListBy(apNo, benData.getSeqNo(), benData.getIssuYm());
                for (Badapr benPayData : benPayDataList) {
                    DisableReviewRpt01BenPayDataCase benPayCase = new DisableReviewRpt01BenPayDataCase();
                    BeanUtility.copyProperties(benPayCase, benPayData);
                    benPayCaseList.add(benPayCase);
                }
                benDataCase.setBenPayList(benPayCaseList);

                // 取得 每位受款人 給付編審檔 (BACHKFILE) 受款人編審註記

                List<DisableReviewRpt01ChkfileDataCase> benChkfileDataCaseList = new ArrayList<DisableReviewRpt01ChkfileDataCase>();
                List<Bachkfile> benChkfileDataList = bachkfileDao.getDisableReviewRpt01BenChkfileDataBy(apNo, benData.getSeqNo(), benData.getIssuYm());
                for (Bachkfile benChkfileData : benChkfileDataList) {
                    DisableReviewRpt01ChkfileDataCase benChkfileDataCase = new DisableReviewRpt01ChkfileDataCase();
                    BeanUtility.copyProperties(benChkfileDataCase, benChkfileData);
                    benChkfileDataCaseList.add(benChkfileDataCase);
                }
                benDataCase.setChkfileDataList(benChkfileDataCaseList);

                benList.add(benDataCase);
                // ]
            }
 
            caseData.setBenList(benList);

            // 取得 BACHKFILE 資料 - 編審註記資料 3M註記
            List<Bachkfile> checkFileList = bachkfileDao.getDisabledApplicationDataUpdateCheckFileListBy(evtData.getBaappbaseId());
            for (Bachkfile checkFileData : checkFileList) {
                DisabledApplicationDataUpdateCheckFileCase checkFileCase = new DisabledApplicationDataUpdateCheckFileCase();
                checkFileCase.setPayYm(checkFileData.getPayYm()); // 給付年月
                checkFileCase.setChkFileName(checkFileData.getChkFileName()); // 編審註記代號
                checkFileCase.setChkFileDesc(checkFileData.getChkFileDesc()); // 編審註記代號
                checkFileCase.setChkFileCode(checkFileData.getChkFileCode()); // 編審註記代號
                caseData.addCheckFileData(checkFileCase);
            }

            // 取得 給付核定檔 (BADAPR) 前次核定金額
            Badapr onceIssuCalcAmt = badaprDao.selectOnceIssuCalcAmtBy(apNo);
            if (onceIssuCalcAmt != null) {
                caseData.setIssuCalcAmt(onceIssuCalcAmt.getIssuCalcAmt());
            }

            // 取得 重新查核失能程度檔 資料
            List<Barecheck> bareCheckDataList = barecheckDao.selectBareCheckDataForRpt01By(apNo, evtData.getSeqNo());

            List<DisabledApplicationDataUpdateBareCheckCase> bareCheckDataCaseList = new ArrayList<DisabledApplicationDataUpdateBareCheckCase>();

            for (int i = 0; i < bareCheckDataList.size(); i++) {
                Barecheck obj = bareCheckDataList.get(i);
                DisabledApplicationDataUpdateBareCheckCase caseObj = new DisabledApplicationDataUpdateBareCheckCase();
                // 轉換日期格式
                obj.setReChkYm(DateUtility.changeWestYearMonthType(obj.getReChkYm()));
                obj.setComReChkYm(DateUtility.changeWestYearMonthType(obj.getComReChkYm()));

                BeanUtility.copyProperties(caseObj, obj);

                // 重新查核狀態 中文
                if (StringUtils.isNotBlank(obj.getReChkStatus())) {
                    caseObj.setReChkStatusStr(baparamDao.selectParamNameBy(null, "KRECHKSTATUS", obj.getReChkStatus()));
                }
                else {
                    caseObj.setReChkStatusStr("");
                }
                // 重新查核結果 中文
                if (StringUtils.isNotBlank(obj.getReChkResult())) {
                    String reChkResultStr = baparamDao.getParamNameForKRECHKRESULT(obj.getReChkResult());
                    if (reChkResultStr.length() > 12) {
                        caseObj.setReChkResultStr(reChkResultStr.substring(0, 12));
                    }
                    else {
                        caseObj.setReChkResultStr(reChkResultStr);
                    }

                }
                else {
                    caseObj.setReChkResultStr("");
                }

                bareCheckDataCaseList.add(caseObj);
            }

            // 加入最大 重新查核失能程度年月
            DisabledApplicationDataUpdateBareCheckCase maxChkYmCaseObj = new DisabledApplicationDataUpdateBareCheckCase();
            // 20140407修改
            // String maxReChkYm = barecheckDao.selectMaxReChkYmBy(apNo, evtData.getSeqNo());
            Baappexpand baappexpandDataForBareCheck = baappexpandDao.getDisabledReviewRpt01AnnuityPayList(apNo);

            if (baappexpandDataForBareCheck != null) {
                // if(StringUtils.isNotBlank(maxReChkYm)){
                if (StringUtils.isNotBlank(baappexpandDataForBareCheck.getRehcYm())) {

                    maxChkYmCaseObj.setReChkYm(DateUtility.changeWestYearMonthType(baappexpandDataForBareCheck.getRehcYm()));
                    bareCheckDataCaseList.add(maxChkYmCaseObj);
                }
            }

            caseData.setBareCheckList(bareCheckDataCaseList);

            // 取得 與失能相關之欄位資料
            List<DisableRevewRpt01ExpDataCase> expDataCaseList = new ArrayList<DisableRevewRpt01ExpDataCase>();

            List<Baappexpand> expDataList = baappexpandDao.getDisableBaappexpandListBy(evtData.getBaappbaseId(), apNo);
            for (Baappexpand expData : expDataList) {
                DisableRevewRpt01ExpDataCase expDataCase = new DisableRevewRpt01ExpDataCase();
                BeanUtility.copyProperties(expDataCase, expData);
                expDataCase.setHpName(bbcmf07Dao.selectHpSnamBy(expDataCase.getHosId()));
                expDataCase.setOcAccHpName(bbcmf07Dao.selectHpSnamBy(expDataCase.getOcAccHosId()));
                expDataCaseList.add(expDataCase);
            }
            caseData.setDisableList(expDataCaseList);

            // 取得 眷屬資料
            // [
            List<DisableReviewRpt01FamilyDataCase> famDataCaseList = new ArrayList<DisableReviewRpt01FamilyDataCase>();
            List<Bafamily> famDataList = bafamilyDao.getDisableBafamilyListBy(evtData.getBaappbaseId(), apNo);
            if (famDataList.size() > 0) {
                for (Bafamily famData : famDataList) {
                    DisableReviewRpt01FamilyDataCase famDataCase = new DisableReviewRpt01FamilyDataCase();

                    BeanUtility.copyProperties(famDataCase, famData);

                    // 取得 每位受款人 學校代碼的中文
                    if (famDataCase != null && !famDataCase.getSchoolCode().equals("")) {
                        Npcode npcodeData = npcodeDao.selectNpCodeNameBy(famDataCase.getSchoolCode());
                        if (npcodeData != null) {
                            famDataCase.setSchoolCodeStr(npcodeData.getCodeName());
                        }
                    }

                    Bastudterm bastudterm = bastudtermDao.selectStudMasterDataForSurvivorPaymentQuery(apNo, famData.getSeqNo());

                    famDataCase.setStudSdate(bastudterm.getStudSdate());
                    famDataCase.setStudEdate(bastudterm.getStudEdate());
                    famDataCase.setStudDataCount(bastudterm.getStudDataCount());

                    // 取得 每位眷屬 給付編審檔 (BACHKFILE) 眷屬編審註記
                    List<DisableReviewRpt01FamChkfileDataCase> famChkFileCaseList = new ArrayList<DisableReviewRpt01FamChkfileDataCase>();
                    List<Bachkfile> famChkfileDataList = bachkfileDao.getDisableReviewRpt01FamChkfileDataBy(apNo, famData.getSeqNo(), evtData.getIssuYm());
                    for (Bachkfile famChkfileData : famChkfileDataList) {
                        DisableReviewRpt01FamChkfileDataCase famChkfileDataCase = new DisableReviewRpt01FamChkfileDataCase();
                        BeanUtility.copyProperties(famChkfileDataCase, famChkfileData);
                        famChkFileCaseList.add(famChkfileDataCase);
                    }

                    famDataCase.setFamChkfileDataList(famChkFileCaseList);

                    // 取得 每位眷屬 給付編審檔 (BACHKFILE) 眷屬符合註記
                    List<DisableReviewRpt01FamChkfileDataCase> famChkFileDescCaseList = new ArrayList<DisableReviewRpt01FamChkfileDataCase>();
                    List<Bachkfile> famChkfileDescList = bachkfileDao.getDisableReviewRpt01FamChkfileDescBy(apNo, famData.getSeqNo(), evtData.getIssuYm());
                    for (Bachkfile famChkfileDesc : famChkfileDescList) {
                        DisableReviewRpt01FamChkfileDataCase famChkfileDescCase = new DisableReviewRpt01FamChkfileDataCase();
                        BeanUtility.copyProperties(famChkfileDescCase, famChkfileDesc);
                        famChkFileDescCaseList.add(famChkfileDescCase);
                    }

                    famDataCase.setFamChkfileDescList(famChkFileDescCaseList);

                    famDataCaseList.add(famDataCase);
                }
                caseData.setFamDataCaseList(famDataCaseList);
            }

            // 取得 每位眷屬 給付編審檔 (BACHKFILE) 眷屬編審註記
            List<DisableReviewRpt01FamChkfileDataCase> famChkDescCaseList = new ArrayList<DisableReviewRpt01FamChkfileDataCase>();
            List<Bachkfile> famChkfileDescDataList = bachkfileDao.getDisableReviewRpt01FamChkfileDescDataBy(apNo, evtData.getIssuYm());
            for (Bachkfile famChkfileDescData : famChkfileDescDataList) {
                DisableReviewRpt01FamChkfileDataCase famChkfileDescDataCase = new DisableReviewRpt01FamChkfileDataCase();
                BeanUtility.copyProperties(famChkfileDescDataCase, famChkfileDescData);
                famChkDescCaseList.add(famChkfileDescDataCase);
            }

            caseData.setFamChkDescDataList(famChkDescCaseList);

            // 取得 每位眷屬 給付編審檔 (BACHKFILE) 眷屬符合註記
            List<DisableReviewRpt01FamChkfileDataCase> famChkDescDataCaseList = new ArrayList<DisableReviewRpt01FamChkfileDataCase>();
            List<Bachkfile> famChkDescDataList = bachkfileDao.getDisableReviewRpt01FamChkfileDescData(apNo, evtData.getIssuYm());
            for (Bachkfile famChkDesc : famChkDescDataList) {
                DisableReviewRpt01FamChkfileDataCase famChkDescDataCase = new DisableReviewRpt01FamChkfileDataCase();
                BeanUtility.copyProperties(famChkDescDataCase, famChkDesc);
                famChkDescDataCaseList.add(famChkDescDataCase);
            }

            caseData.setFamChkDescList(famChkDescDataCaseList);

            // 取得 給付核定檔 (BADAPR)職災相關資料

            DisableQueryOccAccDataCase occAccCase = new DisableQueryOccAccDataCase();
            Badapr badapr = badaprDao.selectOccAccDataForDisabledReviewRpt01(apNo, evtData.getIssuYm());
            if (badapr != null) {
                BeanUtility.copyProperties(occAccCase, badapr);
            }
            caseData.setOccAcc(occAccCase);

            // 取得 給付核定檔 (BADAPR) 核定總額資料
            DisableReviewRpt01IssueAmtDataCase issueAmtCase = new DisableReviewRpt01IssueAmtDataCase();
            Badapr issueAmtData = badaprDao.getDisableReviewRpt01IssueAmtDataBy(apNo, evtData.getIssuYm());
            if (issueAmtData != null) {
                BeanUtility.copyProperties(issueAmtCase, issueAmtData);
            }
            caseData.setIssueAmtData(issueAmtCase);

            // 取得 給付核定檔 (BADAPR) 給付資料
            List<DisableReviewRpt01PayDataCase> payList = new ArrayList<DisableReviewRpt01PayDataCase>();
            List<Badapr> payDataList = badaprDao.getDisableReviewRpt01PayListBy(apNo, evtData.getIssuYm());
            for (Badapr payData : payDataList) {
                DisableReviewRpt01PayDataCase payDataCase = new DisableReviewRpt01PayDataCase();
                BeanUtility.copyProperties(payDataCase, payData);
                payList.add(payDataCase);
            }
            caseData.setPayList(payList);

            // 取得 給付核定檔 (BADAPR) 核定資料
            DisableReviewRpt01DecideDataCase decideCase = new DisableReviewRpt01DecideDataCase();
            Badapr decideData = badaprDao.getDisableReviewRpt01DecideDataBy(apNo, evtData.getIssuYm());
            if (decideData != null) {
                BeanUtility.copyProperties(decideCase, decideData);
            }
            caseData.setDecideData(decideCase);

            // 取得 本次紓困貸款(BADAPR) 給付資料
            List<DisableReviewRpt01LoanAmtCase> loanAmtList = new ArrayList<DisableReviewRpt01LoanAmtCase>();
            List<Badapr> loanAmtDataList = badaprDao.getDisableReviewRpt01LoanAmt(apNo, evtData.getIssuYm());
            for (Badapr loanAmtData : loanAmtDataList) {
                DisableReviewRpt01LoanAmtCase loanAmtDataCase = new DisableReviewRpt01LoanAmtCase();
                BeanUtility.copyProperties(loanAmtDataCase, loanAmtData);
                loanAmtList.add(loanAmtDataCase);
            }
            caseData.setLoanAmtData(loanAmtList);

            // 取得 給付編審檔 (BACHKFILE) 編審資料 - 事故者編審註記

            List<DisableReviewRpt01ChkfileDataCase> chkfileCaseList = new ArrayList<DisableReviewRpt01ChkfileDataCase>();
            List<Bachkfile> chkfileDataList = bachkfileDao.getDisableReviewRpt01ChkfileDataBy(apNo, evtData.getIssuYm());
            for (Bachkfile chkfileData : chkfileDataList) {
                DisableReviewRpt01ChkfileDataCase chkfileCase = new DisableReviewRpt01ChkfileDataCase();
                BeanUtility.copyProperties(chkfileCase, chkfileData);
                chkfileCaseList.add(chkfileCase);
            }
            caseData.setChkfileDataList(chkfileCaseList);

            // 取得 給付編審檔 (BACHKFILE) 編審資料 - 編審註記說明
            List<DisableReviewRpt01ChkfileDescCase> chkfileDescCaseList = new ArrayList<DisableReviewRpt01ChkfileDescCase>();
            List<Bachkfile> chkfileDescDataList = bachkfileDao.getDisableReviewRpt01ChkfileDescBy(apNo, evtData.getIssuYm());
            for (Bachkfile chkfileDescData : chkfileDescDataList) {
                DisableReviewRpt01ChkfileDescCase chkfileDescCase = new DisableReviewRpt01ChkfileDescCase();
                BeanUtility.copyProperties(chkfileDescCase, chkfileDescData);
                chkfileDescCaseList.add(chkfileDescCase);
            }
            caseData.setChkfileDescList(chkfileDescCaseList);

            // 取得 核定通知書格式檔 (BANOTIFY 核定通知書資料

            // [
            // a. 若核定通知書格式為 999 或為 空白 及 NULL, 則整段核定通知書不顯示
            // b. 若查無受理案件核定通知書格式, 則整段核定通知書不顯示
            if (StringUtils.isNotBlank(evtData.getNotifyForm()) && !StringUtils.equals(evtData.getNotifyForm(), "999")) {
                BaReportReplaceUtility strReplace = new BaReportReplaceUtility(evtData, true, null, false, null);

                // 取得 核定通知書 - 主旨
                List<Banotify> subjectList = banotifyDao.getDisableReviewRpt01NotifyListBy(StringUtils.substring(apNo, 0, 1), evtData.getNotifyForm(), "0");
                if (subjectList.size() > 0) {
                    DisableReviewRpt01NotifyDataCase notifyCase = new DisableReviewRpt01NotifyDataCase();
                    notifyCase.setSubject(strReplace.replace(StringUtils.defaultString(subjectList.get(0).getDataCont())));

                    // 取得 核定通知書 - 說明
                    List<Banotify> contentList = banotifyDao.getDisableReviewRpt01NotifyListBy(StringUtils.substring(apNo, 0, 1), evtData.getNotifyForm(), "1");
                    List<String> contents = new ArrayList<String>();
                    for (Banotify contentData : contentList) {
                        if (StringUtils.isNotBlank(contentData.getDataCont()))
                            contents.add(strReplace.replace(contentData.getDataCont()));
                    }
                    notifyCase.setContent(contents);

                    caseData.setNotifyData(notifyCase);
                }
            }
            // ]

            // 取得 請領同類給付資料
            // [
            // 取得 現金給付參考檔 (PBBMSA) 一次給付資料

            List<DisableReviewRpt01OncePayDataCase> oncePayCaseList = new ArrayList<DisableReviewRpt01OncePayDataCase>();
            List<Pbbmsa> oncePayDataList = pbbmsaDao.getDisableReviewRpt01OncePayListBy(evtIdnNo, evtBrDate);
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                oncePayDataList.addAll(pbbmsaDao.getDisableReviewRpt01OncePayListBy(StringUtils.substring(kcaf.getBIdn(), 0, 10), kcaf.getBBrDte()));
            }
            for (Pbbmsa oncePayData : oncePayDataList) {
                DisableReviewRpt01OncePayDataCase oncePayDataCase = new DisableReviewRpt01OncePayDataCase();
                BeanUtility.copyProperties(oncePayDataCase, oncePayData);
                oncePayCaseList.add(oncePayDataCase);
            }

            caseData.setOncePayList(oncePayCaseList);

            // 取得 現金給付參考檔 (PBBMSA) 申請農保殘廢給付記錄
            List<DisableReviewRpt01OncePayDataCase> farmPayCaseList = new ArrayList<DisableReviewRpt01OncePayDataCase>();
            List<Pbbmsa> farmPayDataList = pbbmsaDao.getDisableReviewRpt01FarmPayListBy(evtIdnNo, evtBrDate);
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                farmPayDataList.addAll(pbbmsaDao.getDisableReviewRpt01FarmPayListBy(StringUtils.substring(kcaf.getBIdn(), 0, 10), kcaf.getBBrDte()));
            }
            for (Pbbmsa farmPayData : farmPayDataList) {
                DisableReviewRpt01OncePayDataCase farmPayDataCase = new DisableReviewRpt01OncePayDataCase();
                BeanUtility.copyProperties(farmPayDataCase, farmPayData);
                farmPayCaseList.add(farmPayDataCase);
            }

            caseData.setFarmPayList(farmPayCaseList);

            // 取得 年金給付資料
            List<DisableReviewRpt01AnnuityPayDataCase> annuityPayCaseList = new ArrayList<DisableReviewRpt01AnnuityPayDataCase>();
            List<Baappbase> annuityPayDataList = baappbaseDao.getDisableReviewRpt01AnnuityPayListBy(apNo, evtData.getEvtIdnNo(), evtData.getEvtBrDate());
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                annuityPayDataList.addAll(baappbaseDao.getDisableReviewRpt01AnnuityPayListBy(apNo, StringUtils.substring(kcaf.getBIdn(), 0, 10), kcaf.getBBrDte()));
            }
            for (Baappbase annuityPayData : annuityPayDataList) {
                DisableReviewRpt01AnnuityPayDataCase annuityPayDataCase = new DisableReviewRpt01AnnuityPayDataCase();

                BeanUtility.copyProperties(annuityPayDataCase, annuityPayData);

                Baappexpand baappexpandData = baappexpandDao.getDisabledReviewRpt01AnnuityPayList(annuityPayData.getApNo());
                if (baappexpandData != null) {
                    annuityPayDataCase.setEvTyp(baappexpandData.getEvTyp()); // 傷病分類
                    annuityPayDataCase.setCriInJcl1(baappexpandData.getCriInJcl1()); // 失能等級 1
                    annuityPayDataCase.setCriInJcl2(baappexpandData.getCriInJcl2()); // 失能等級 2
                    annuityPayDataCase.setCriInJcl3(baappexpandData.getCriInJcl3()); // 失能等級 3
                    annuityPayDataCase.setCriInJdp1(baappexpandData.getCriInJdp1()); // 失能項目 1
                    annuityPayDataCase.setCriInJdp2(baappexpandData.getCriInJdp2()); // 失能項目 2
                    annuityPayDataCase.setCriInJdp3(baappexpandData.getCriInJdp3()); // 失能項目 3
                    annuityPayDataCase.setCriInJdp4(baappexpandData.getCriInJdp4()); // 失能項目 4
                    annuityPayDataCase.setCriInJdp5(baappexpandData.getCriInJdp5()); // 失能項目 5
                    annuityPayDataCase.setCriInJdp6(baappexpandData.getCriInJdp6()); // 失能項目 6
                    annuityPayDataCase.setCriInJdp7(baappexpandData.getCriInJdp7()); // 失能項目 7
                    annuityPayDataCase.setCriInJdp8(baappexpandData.getCriInJdp8()); // 失能項目 8
                    annuityPayDataCase.setCriInJdp9(baappexpandData.getCriInJdp9()); // 失能項目 9
                    annuityPayDataCase.setCriInJdp10(baappexpandData.getCriInJdp10()); // 失能項目 10
                }

                Badapr annuityPayBadaprData = badaprDao.getDisableReviewRpt01AnnuityPayDataBy(annuityPayData.getApNo(), annuityPayData.getIssuYm(), annuityPayData.getPayYm());
                if (annuityPayBadaprData != null) {
                    annuityPayDataCase.setChkDate(annuityPayBadaprData.getChkDate()); // 核定日期
                    annuityPayDataCase.setAplpayDate(annuityPayBadaprData.getAplpayDate()); // 核付日期
                    annuityPayDataCase.setRecAmt(annuityPayBadaprData.getRecAmt()); // 收回金額
                    annuityPayDataCase.setSupAmt(annuityPayBadaprData.getSupAmt()); // 補發金額
                }

                Maadmrec annuityPayMaadmrecData = maadmrecDao.getDisableReviewRpt01AnnuityPayDataBy(annuityPayData.getApNo(), annuityPayData.getIssuYm());
                if (annuityPayMaadmrecData != null) {
                    annuityPayDataCase.setProdate(annuityPayMaadmrecData.getProDate()); // 補件日期
                    annuityPayDataCase.setNdomk1(annuityPayMaadmrecData.getNdomk1()); // 處理註記
                }

                annuityPayCaseList.add(annuityPayDataCase);
            }
            caseData.setAnnuityPayList(annuityPayCaseList);

            // 取得 國保給付主檔 (NBAPPBASE) 申請國保身障給付記錄資料
            List<DisableReviewRpt01NpPayDataCase> npDisPayCaseList = new ArrayList<DisableReviewRpt01NpPayDataCase>();
            List<Nbappbase> npDisPayDataList = nbappbaseDao.getDisableReviewRpt01NpDisPayListBy(evtData.getEvtIds());
            for (Nbappbase npDisPayData : npDisPayDataList) {
                DisableReviewRpt01NpPayDataCase npDisPayDataCase = new DisableReviewRpt01NpPayDataCase();
                BeanUtility.copyProperties(npDisPayDataCase, npDisPayData);

                // 取得 國保給付核定檔 (NBDAPR) 申請國保給付記錄資料
                Nbdapr npDisPayDetailData = nbdaprDao.getDisableReviewRpt01NpPayDetailDataBy(npDisPayData.getApNo(), npDisPayData.getIssuYm(), npDisPayData.getPayYm());
                if (npDisPayDetailData != null) {
                    npDisPayDataCase.setChkDt(npDisPayDetailData.getChkDt()); // 核定日期
                    npDisPayDataCase.setAplPayDate(npDisPayDetailData.getAplPayDate()); // 帳務日期
                }

                npDisPayCaseList.add(npDisPayDataCase);
            }
            caseData.setNbDisPayList(npDisPayCaseList);

            // 取得 申請國保給付記錄資料
            List<DisableReviewRpt01AnnuityPayDataCase> nbPayCaseList = new ArrayList<DisableReviewRpt01AnnuityPayDataCase>();
            List<Baappbase> nbPayDataList = baappbaseDao.getDisableReviewRpt01NbPayListBy(evtData.getEvtIds());
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                nbPayDataList.addAll(baappbaseDao.getDisableReviewRpt01NbPayListBy(kcaf.getBIdn()));
            }
            for (Baappbase nbPayData : nbPayDataList) {
                DisableReviewRpt01AnnuityPayDataCase nbPayDataCase = new DisableReviewRpt01AnnuityPayDataCase();

                BeanUtility.copyProperties(nbPayDataCase, nbPayDataList);

                Badapr nbPayBadaprData = badaprDao.getDisableReviewRpt01AnnuityPayDataBy(nbPayData.getApNo(), nbPayData.getIssuYm(), nbPayData.getPayYm());
                if (nbPayBadaprData != null) {
                    nbPayDataCase.setChkDate(nbPayBadaprData.getChkDate()); // 核定日期
                    nbPayDataCase.setAplpayDate(nbPayBadaprData.getAplpayDate()); // 核付日期
                    nbPayDataCase.setRecAmt(nbPayBadaprData.getRecAmt()); // 收回金額
                    nbPayDataCase.setSupAmt(nbPayBadaprData.getSupAmt()); // 補發金額
                }

                nbPayCaseList.add(nbPayDataCase);
            }
            caseData.setNbPayList(nbPayCaseList);
            // ]

            // 取得 請領他類給付資料
            // [
            // 取得 老年年金給付記錄

            List<DisableReviewRpt01OldAgePayDataCase> oldAgePayCaseList = new ArrayList<DisableReviewRpt01OldAgePayDataCase>();
            List<Baappbase> oldAgePayDataList = baappbaseDao.getDisableReviewRpt01OldPayListBy(apNo, evtData.getEvtIdnNo(), evtData.getEvtBrDate());
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                oldAgePayDataList.addAll(baappbaseDao.getDisableReviewRpt01OldPayListBy(apNo, StringUtils.substring(kcaf.getBIdn(), 0, 10), kcaf.getBBrDte()));
            }
            for (Baappbase oldPayData : oldAgePayDataList) {
                DisableReviewRpt01OldAgePayDataCase oldPayDataCase = new DisableReviewRpt01OldAgePayDataCase();

                BeanUtility.copyProperties(oldPayDataCase, oldPayData);

                Badapr oldPayBadaprData = badaprDao.getDisableReviewRpt01AnnuityPayDataBy(oldPayData.getApNo(), oldPayData.getIssuYm(), oldPayData.getPayYm());
                if (oldPayBadaprData != null) {
                    oldPayDataCase.setChkDate(oldPayBadaprData.getChkDate()); // 核定日期
                    oldPayDataCase.setAplpayDate(oldPayBadaprData.getAplpayDate()); // 核付日期
                    oldPayDataCase.setRecAmt(oldPayBadaprData.getRecAmt()); // 收回金額
                    oldPayDataCase.setSupAmt(oldPayBadaprData.getSupAmt()); // 補發金額
                }

                Maadmrec oldPayMaadmrecData = maadmrecDao.getDisableReviewRpt01AnnuityPayDataBy(oldPayData.getApNo(), oldPayData.getIssuYm());
                if (oldPayMaadmrecData != null) {
                    oldPayDataCase.setProdate(oldPayMaadmrecData.getProDate()); // 補件日期
                    oldPayDataCase.setNdomk1(oldPayMaadmrecData.getNdomk1()); // 處理註記
                }

                oldAgePayCaseList.add(oldPayDataCase);
            }

            caseData.setOldAgePayList(oldAgePayCaseList);

            // 取得遺屬年金給付記錄

            List<DisableReviewRpt01SurvivorPayDataCase> survivorPayCaseList = new ArrayList<DisableReviewRpt01SurvivorPayDataCase>();
            List<Baappbase> survivorPayDataList = baappbaseDao.getDisableReviewRpt01SurvivorPayListBy(apNo, evtData.getEvtIdnNo(), evtData.getEvtBrDate());
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                survivorPayDataList.addAll(baappbaseDao.getDisableReviewRpt01SurvivorPayListBy(apNo, StringUtils.substring(kcaf.getBIdn(), 0, 10), kcaf.getBBrDte()));
            }
            for (Baappbase survivorPayData : survivorPayDataList) {
                DisableReviewRpt01SurvivorPayDataCase survivorPayDataCase = new DisableReviewRpt01SurvivorPayDataCase();

                BeanUtility.copyProperties(survivorPayDataCase, survivorPayData);

                Baappexpand baappexpandData = baappexpandDao.getDisabledReviewRpt01SurvivorPayList(survivorPayData.getApNo());
                if (baappexpandData != null) {
                    survivorPayDataCase.setEvTyp(baappexpandData.getEvTyp()); // 傷病分類
                    survivorPayDataCase.setCriInJcl1(baappexpandData.getCriInJcl1()); // 失能等級 1
                    survivorPayDataCase.setCriInJcl2(baappexpandData.getCriInJcl2()); // 失能等級 2
                    survivorPayDataCase.setCriInJcl3(baappexpandData.getCriInJcl3()); // 失能等級 3
                    survivorPayDataCase.setCriInJdp1(baappexpandData.getCriInJdp1()); // 失能項目 1
                    survivorPayDataCase.setCriInJdp2(baappexpandData.getCriInJdp2()); // 失能項目 2
                    survivorPayDataCase.setCriInJdp3(baappexpandData.getCriInJdp3()); // 失能項目 3
                    survivorPayDataCase.setCriInJdp4(baappexpandData.getCriInJdp4()); // 失能項目 4
                    survivorPayDataCase.setCriInJdp5(baappexpandData.getCriInJdp5()); // 失能項目 5
                    survivorPayDataCase.setCriInJdp6(baappexpandData.getCriInJdp6()); // 失能項目 6
                    survivorPayDataCase.setCriInJdp7(baappexpandData.getCriInJdp7()); // 失能項目 7
                    survivorPayDataCase.setCriInJdp8(baappexpandData.getCriInJdp8()); // 失能項目 8
                    survivorPayDataCase.setCriInJdp9(baappexpandData.getCriInJdp9()); // 失能項目 9
                    survivorPayDataCase.setCriInJdp10(baappexpandData.getCriInJdp10()); // 失能項目 10
                }

                Badapr survivorPayBadaprData = badaprDao.getDisableReviewRpt01AnnuityPayDataBy(survivorPayData.getApNo(), survivorPayData.getIssuYm(), survivorPayData.getPayYm());
                if (survivorPayBadaprData != null) {
                    survivorPayDataCase.setChkDate(survivorPayBadaprData.getChkDate()); // 核定日期
                    survivorPayDataCase.setAplpayDate(survivorPayBadaprData.getAplpayDate()); // 核付日期
                    survivorPayDataCase.setRecAmt(survivorPayBadaprData.getRecAmt()); // 收回金額
                    survivorPayDataCase.setSupAmt(survivorPayBadaprData.getSupAmt()); // 補發金額
                }

                Maadmrec survivorPayMaadmrecData = maadmrecDao.getDisableReviewRpt01AnnuityPayDataBy(survivorPayData.getApNo(), survivorPayData.getIssuYm());
                if (survivorPayMaadmrecData != null) {
                    survivorPayDataCase.setProdate(survivorPayMaadmrecData.getProDate()); // 補件日期
                    survivorPayDataCase.setNdomk1(survivorPayMaadmrecData.getNdomk1()); // 處理註記
                }

                survivorPayCaseList.add(survivorPayDataCase);
            }
            caseData.setSurvivorPayList(survivorPayCaseList);

            // // 取得 現金給付參考檔 (PBBMSA) 申請失能給付記錄資料
            // List<DisableReviewRpt01DisablePayDataCase> disablePayCaseList = new ArrayList<DisableReviewRpt01DisablePayDataCase>();
            // List<Pbbmsa> disablePayDataList = pbbmsaDao.getDisableReviewRpt01DisablePayListBy(evtIdnNo, evtBrDate);
            // // 用關鍵欄位變更檔 去找資料
            // for (Kcaf kcaf : evtKcafList) {
            // disablePayDataList.addAll(pbbmsaDao.getDisableReviewRpt01DisablePayListBy(StringUtils.substring(kcaf.getBIdn(),0, 10), kcaf.getBBrDte()));
            // }
            // for (Pbbmsa disablePayData : disablePayDataList) {
            // DisableReviewRpt01DisablePayDataCase disablePayDataCase = new DisableReviewRpt01DisablePayDataCase();
            // BeanUtility.copyProperties(disablePayDataCase, disablePayData);
            // disablePayCaseList.add(disablePayDataCase);
            // }
            // caseData.setDisablePayList(disablePayCaseList);
            //
            // 取得 現金給付參考檔 (PBBMSA) 申請死亡給付記錄資料
            List<DisableReviewRpt01DiePayDataCase> diePayCaseList = new ArrayList<DisableReviewRpt01DiePayDataCase>();
            List<Pbbmsa> diePayDataList = pbbmsaDao.getDisableReviewRpt01DiePayListBy(evtIdnNo, evtBrDate);
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                diePayDataList.addAll(pbbmsaDao.getDisableReviewRpt01DiePayListBy(StringUtils.substring(kcaf.getBIdn(), 0, 10), kcaf.getBBrDte()));
            }
            for (Pbbmsa diePayData : diePayDataList) {
                DisableReviewRpt01DiePayDataCase diePayDataCase = new DisableReviewRpt01DiePayDataCase();
                BeanUtility.copyProperties(diePayDataCase, diePayData);
                diePayCaseList.add(diePayDataCase);
            }
            caseData.setDiePayList(diePayCaseList);

            // 取得 現金給付參考檔 (PBBMSA) 申請失蹤給付記錄資料
            List<DisableReviewRpt01DiePayDataCase> disPayCaseList = new ArrayList<DisableReviewRpt01DiePayDataCase>();
            List<Pbbmsa> disPayDataList = pbbmsaDao.getDisableReviewRpt01DisPayListBy(evtIdnNo, evtBrDate);
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                disPayDataList.addAll(pbbmsaDao.getDisableReviewRpt01DisPayListBy(StringUtils.substring(kcaf.getBIdn(), 0, 10), kcaf.getBBrDte()));
            }
            for (Pbbmsa disPayData : disPayDataList) {
                DisableReviewRpt01DiePayDataCase disPayDataCase = new DisableReviewRpt01DiePayDataCase();
                BeanUtility.copyProperties(disPayDataCase, disPayData);
                disPayCaseList.add(disPayDataCase);
            }
            caseData.setDisPayList(disPayCaseList);

            // 取得 現金給付參考檔 (PBBMSA) 申請農保死亡給付記錄資料
            List<DisableReviewRpt01DiePayDataCase> famDiePayCaseList = new ArrayList<DisableReviewRpt01DiePayDataCase>();
            List<Pbbmsa> famDiePayDataList = pbbmsaDao.getDisableReviewRpt01FamDiePayListBy(evtIdnNo, evtBrDate);
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                disPayDataList.addAll(pbbmsaDao.getDisableReviewRpt01FamDiePayListBy(StringUtils.substring(kcaf.getBIdn(), 0, 10), kcaf.getBBrDte()));
            }
            for (Pbbmsa famDiePayData : famDiePayDataList) {
                DisableReviewRpt01DiePayDataCase famDiePayDataCase = new DisableReviewRpt01DiePayDataCase();
                BeanUtility.copyProperties(famDiePayDataCase, famDiePayData);
                famDiePayCaseList.add(famDiePayDataCase);
            }
            caseData.setFamDiePayList(famDiePayCaseList);

            // 取得 現金給付參考檔 (PBBMSA) 申請傷病給付記錄資料
            List<DisableReviewRpt01InjuryPayDataCase> injuryPayCaseList = new ArrayList<DisableReviewRpt01InjuryPayDataCase>();
            List<Pbbmsa> injuryPayDataList = pbbmsaDao.getDisableReviewRpt01InjuryPayListBy(evtIdnNo, evtBrDate);
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                injuryPayDataList.addAll(pbbmsaDao.getDisableReviewRpt01InjuryPayListBy(StringUtils.substring(kcaf.getBIdn(), 0, 10), kcaf.getBBrDte()));
            }
            for (Pbbmsa injuryPayData : injuryPayDataList) {
                DisableReviewRpt01InjuryPayDataCase injuryPayDataCase = new DisableReviewRpt01InjuryPayDataCase();
                BeanUtility.copyProperties(injuryPayDataCase, injuryPayData);
                injuryPayCaseList.add(injuryPayDataCase);
            }
            caseData.setInjuryPayList(injuryPayCaseList);

            // 取得 現金給付參考檔 (PBBMSA) 申請職災住院醫療給付記錄
            List<DisableReviewRpt01OncePayDataCase> hosPayCaseList = new ArrayList<DisableReviewRpt01OncePayDataCase>();
            List<Pbbmsa> hosPayDataList = pbbmsaDao.selectDisableReviewRpt01HosPayListBy(evtIdnNo, evtBrDate);
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                hosPayDataList.addAll(pbbmsaDao.selectDisableReviewRpt01HosPayListBy(StringUtils.substring(kcaf.getBIdn(), 0, 10), kcaf.getBBrDte()));
            }
            for (Pbbmsa hosPayData : hosPayDataList) {
                DisableReviewRpt01OncePayDataCase hosPayDataCase = new DisableReviewRpt01OncePayDataCase();
                BeanUtility.copyProperties(hosPayDataCase, hosPayData);
                hosPayCaseList.add(hosPayDataCase);
            }
            caseData.setHosPayList(hosPayCaseList);

            // 取得 現金給付參考檔(PBMSA) 申請 老年給付記綠資料

            List<DisableReviewRpt01OldPayDataCase> oldPayCaseList = new ArrayList<DisableReviewRpt01OldPayDataCase>();
            List<Pbbmsa> oldPayDataList = pbbmsaDao.getDisableReviewRpt01OldPayListBy(evtIdnNo, evtBrDate);
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                oldPayDataList.addAll(pbbmsaDao.getDisableReviewRpt01OldPayListBy(StringUtils.substring(kcaf.getBIdn(), 0, 10), kcaf.getBBrDte()));
            }
            for (Pbbmsa oldPayData : oldPayDataList) {
                DisableReviewRpt01OldPayDataCase oldPayDataCase = new DisableReviewRpt01OldPayDataCase();
                BeanUtility.copyProperties(oldPayDataCase, oldPayData);
                oldPayCaseList.add(oldPayDataCase);
            }
            caseData.setOldPayList(oldPayCaseList);

            // 取得 就保給付檔 (BIREF) 申請失業給付記錄資料
            List<DisableReviewRpt01JoblessPayDataCase> joblessPayCaseList = new ArrayList<DisableReviewRpt01JoblessPayDataCase>();
            List<Biref> joblessPayDataList = birefDao.getDisableReviewRpt01JoblessPayListBy(evtIdnNo, evtBrDate);
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                joblessPayDataList.addAll(birefDao.getDisableReviewRpt01JoblessPayListBy(StringUtils.substring(kcaf.getBIdn(), 0, 10), kcaf.getBBrDte()));
            }
            for (Biref joblessPayData : joblessPayDataList) {
                DisableReviewRpt01JoblessPayDataCase joblessPayDataCase = new DisableReviewRpt01JoblessPayDataCase();
                BeanUtility.copyProperties(joblessPayDataCase, joblessPayData);
                joblessPayCaseList.add(joblessPayDataCase);
            }
            caseData.setJoblessPayList(joblessPayCaseList);

            // 取得 就保給付檔 (BIREF) 申請職業訓練生活津貼記錄資料
            List<DisableReviewRpt01JoblessPayDataCase> vocationalTrainingLivingAllowanceList = new ArrayList<DisableReviewRpt01JoblessPayDataCase>();
            List<Biref> vocationalTrainingLivingAllowanceDataList = birefDao.getDisableReviewRpt01VocationalTrainingLivingAllowanceListBy(evtIdnNo, evtBrDate);
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                vocationalTrainingLivingAllowanceDataList.addAll(birefDao.getDisableReviewRpt01VocationalTrainingLivingAllowanceListBy(StringUtils.substring(kcaf.getBIdn(), 0, 10), kcaf.getBBrDte()));
            }
            for (Biref vocationalTrainingLivingAllowanceData : vocationalTrainingLivingAllowanceDataList) {
                DisableReviewRpt01JoblessPayDataCase vocationalTrainingLivingAllowanceDataCase = new DisableReviewRpt01JoblessPayDataCase();
                BeanUtility.copyProperties(vocationalTrainingLivingAllowanceDataCase, vocationalTrainingLivingAllowanceData);
                vocationalTrainingLivingAllowanceList.add(vocationalTrainingLivingAllowanceDataCase);
            }
            caseData.setVocationalTrainingLivingAllowanceList(vocationalTrainingLivingAllowanceList);

            // 取得 國保給付主檔 (NBAPPBASE) 申請國保給付記錄資料
            List<DisableReviewRpt01NpPayDataCase> npPayCaseList = new ArrayList<DisableReviewRpt01NpPayDataCase>();
            List<Nbappbase> npPayDataList = nbappbaseDao.getDisableReviewRpt01NpPayListBy(evtData.getEvtIds());
            for (Nbappbase npPayData : npPayDataList) {
                DisableReviewRpt01NpPayDataCase npPayDataCase = new DisableReviewRpt01NpPayDataCase();
                BeanUtility.copyProperties(npPayDataCase, npPayData);

                // 取得 國保給付核定檔 (NBDAPR) 申請國保給付記錄資料
                Nbdapr npPayDetailData = nbdaprDao.getDisableReviewRpt01NpPayDetailDataBy(npPayData.getApNo(), npPayData.getIssuYm(), npPayData.getPayYm());
                if (npPayDetailData != null) {
                    npPayDataCase.setChkDt(npPayDetailData.getChkDt()); // 核定日期
                    npPayDataCase.setAplPayDate(npPayDetailData.getAplPayDate()); // 帳務日期
                }

                npPayCaseList.add(npPayDataCase);
            }
            caseData.setNpPayList(npPayCaseList);
            // ]

            // 取得 給付核定檔 (BADAPR) 事故者於受款人給付資料的資料
            List<DisableReviewRpt01BenPayDataCase> evtBenPayCaseList = new ArrayList<DisableReviewRpt01BenPayDataCase>();
            List<Badapr> evtBenPayDataList = badaprDao.getDisableReviewRpt01BenPayListBy(apNo, evtData.getSeqNo(), evtData.getIssuYm());
            BigDecimal lecomAmt = badaprDao.getDisableReviewRpt01BenPayDataByLecomAmt(apNo);
            for (Badapr evtBenPayData : evtBenPayDataList) {
                DisableReviewRpt01BenPayDataCase evtBenPayCase = new DisableReviewRpt01BenPayDataCase();
                BeanUtility.copyProperties(evtBenPayCase, evtBenPayData);
                evtBenPayCase.setLecomAmt(lecomAmt);
                evtBenPayCaseList.add(evtBenPayCase);
            }
            caseData.setEvtBenPayList(evtBenPayCaseList);

            // 取得 給付核定檔 (BADAPR) 事故者於受款人給付資料的資料
            List<DisableReviewRpt01BenPayDataCase> benByPayCaseList = new ArrayList<DisableReviewRpt01BenPayDataCase>();
            List<Badapr> benByPayDataList = badaprDao.getDisableReviewRpt01BenByPayListBy(apNo, evtData.getSeqNo(), evtData.getIssuYm());
            BigDecimal lecomAmtBen = badaprDao.getDisableReviewRpt01BenPayDataByLecomAmt(apNo);
            for (Badapr benByPayData : benByPayDataList) {
                DisableReviewRpt01BenPayDataCase benByPayCase = new DisableReviewRpt01BenPayDataCase();
                BeanUtility.copyProperties(benByPayCase, benByPayData);
                benByPayCase.setLecomAmt(lecomAmtBen);
                benByPayCaseList.add(benByPayCase);
            }
            caseData.setBenByPayList(benByPayCaseList);

            // 取得 給付核定檔(BADAPR) 事故者於受款人給付資料是否需顯示
            Boolean evtBenPayDataStatus = badaprDao.getDisableReviewRpt01BenListBySum(apNo, evtData.getSeqNo(), evtData.getIssuYm());
            caseData.setEvtBenPayDataStatus(evtBenPayDataStatus);
            Boolean evtBafamilyStatus = bafamilyDao.getDisableReviewRpt01BafamilyByCount(apNo);
            caseData.setEvtBafamilyStatus(evtBafamilyStatus);

            // 取得 給付核定檔 (BADAPR) 事故者於受款人給付資料的資料
            List<DisableReviewRpt01BenPayDataCase> evtBenCaseList = new ArrayList<DisableReviewRpt01BenPayDataCase>();
            List<Badapr> evtBenDataList = new ArrayList<Badapr>();
            if (StringUtils.equals(evtData.getPayKind(), "38")) {
                evtBenDataList = badaprDao.getDisableReviewRpt01BenListFor38By(apNo, evtData.getSeqNo(), evtData.getIssuYm());
            }
            else {
                evtBenDataList = badaprDao.getDisableReviewRpt01BenListBy(apNo, evtData.getSeqNo(), evtData.getIssuYm());
            }
            for (Badapr evtBenData : evtBenDataList) {
                DisableReviewRpt01BenPayDataCase evtBenCase = new DisableReviewRpt01BenPayDataCase();
                BeanUtility.copyProperties(evtBenCase, evtBenData);

                // 取得基本金額 計算38案之加發眷屬補助
                BigDecimal basicAmt = babasicamtDao.selectBasicAmtForPaymentQuery(evtData.getApNo().substring(0, 1), evtBenData.getPayYm());
                if (basicAmt == null) {
                    basicAmt = new BigDecimal("4000");
                }
                evtBenCase.setBasicAmt(basicAmt);

                evtBenCaseList.add(evtBenCase);
            }
            caseData.setEvtBenPayDataList(evtBenCaseList);

            // 取得 被保險人異動資料檔 (CIPT) 承保異動資料
            // List<OldAgeReviewRpt01ChangeDataCase> changeDataCaseList = new ArrayList<OldAgeReviewRpt01ChangeDataCase>();
            // List<Cipt> changeDataList = ciptDao.getOldAgeReviewRpt01ChangeListBy(evtIdnNo);
            // for (Cipt changeData : changeDataList) {
            // OldAgeReviewRpt01ChangeDataCase changeDataCase = new OldAgeReviewRpt01ChangeDataCase();
            // BeanUtility.copyProperties(changeDataCase, changeData);
            // changeDataCaseList.add(changeDataCase);
            // }

            caseData.setChangeList(BaCiptUtility.getCiptUtilityCase(apNo, evtIdnNo));

            // 取得 被保險人投保薪資檔 (CIPG)
            // String adWkMk = expDataCaseList != null && expDataCaseList.get(0).getAdWkMk() != null ? expDataCaseList.get(0).getAdWkMk() : "";
            // String ocaccIdentMk = expDataCaseList != null && expDataCaseList.get(0).getOcaccIdentMk() != null ? expDataCaseList.get(0).getOcaccIdentMk() : "";
            String inTyp = "L";
            if (expDataCaseList != null && expDataCaseList.get(0).getAdWkMk() != null)
                inTyp = (expDataCaseList.get(0).getAdWkMk()).equals("2") && (expDataCaseList.get(0).getOcaccIdentMk() == null || !(expDataCaseList.get(0).getOcaccIdentMk()).equals("Y")) ? "V" : "L";

            List<DisableReviewRpt01CipgDataCase> avgWgDataCaseList = new ArrayList<DisableReviewRpt01CipgDataCase>();

            Integer appMonth = null;
            String appMonthStr = "";
            appMonth = bapaavgmonDao.getBapaavgmonCount();
            String realAvgMon = "";
            // 身分證重號處理
            String evtIdnNoAfter = caseData.getEvtIdnNo();
            if (StringUtils.equals(caseData.getDupeIdnNoMk(), "2")) {
                Badupeidn dupeIdnData = badupeidnDao.getOldAgeReviewRpt01DupeIdnDataBy(caseData.getApNo());
                if (dupeIdnData != null) {
                    if (StringUtils.isNotBlank(dupeIdnData.getIdnNo())) {
                        evtIdnNoAfter = dupeIdnData.getIdnNo();
                    }

                }
            }

            if (appMonth == 0) {
                appMonthStr = "60";
            }
            else {
                appMonthStr = bapaavgmonDao.getBapaavgmonAppMonth(caseData.getAppDate());
                if (StringUtils.isBlank(appMonthStr)) {
                    appMonthStr = "60";
                }
            }

            List<Cipg> avgWgDataList = cipgDao.selectDisableReviewRpt01CipgData(apNo, "0000", evtIdnNoAfter, expDataList.get(0).getEvTyp(), inTyp, expDataCaseList.get(0).getPrType(), appMonthStr);
            for (Cipg avgWgData : avgWgDataList) {
                DisableReviewRpt01CipgDataCase avgWgDataCase = new DisableReviewRpt01CipgDataCase();
                BeanUtility.copyProperties(avgWgDataCase, avgWgData);
                avgWgDataCaseList.add(avgWgDataCase);
            }

            if (StringUtils.isNotBlank(expDataList.get(0).getEvTyp()) && ("3".equals(expDataList.get(0).getEvTyp()) || "4".equals(expDataList.get(0).getEvTyp()))
                            && (expDataCaseList.get(0).getPrType() == null || !"Y".equals(expDataCaseList.get(0).getPrType()))) {
                realAvgMon = cipgDao.getRealAvgMonForDisableReviewRpt01By(apNo, "0000", evtIdnNoAfter, inTyp, "1");
            }
            else {
                realAvgMon = cipgDao.getRealAvgMonForDisableReviewRpt01By(apNo, "0000", evtIdnNoAfter, inTyp, "6");
            }
            caseData.setCipgData(avgWgDataCaseList);
            caseData.setAppMonth(appMonthStr); // 平均薪資月數
            caseData.setRealAvgMon(realAvgMon); // 實際均薪月數

            // 取得 投保單位檔 (CAUB) 投保單位資料
            // [
            // 申請單位
            if (StringUtils.isNotBlank(evtData.getApUbno())) {
                Caub applyUnitData = caubDao.getDisableReviewRpt01UnitDataBy(evtData.getApUbno());
                if (applyUnitData != null) {
                    DisableReviewRpt01UnitCase applyUnitDataCase = new DisableReviewRpt01UnitCase();
                    BeanUtility.copyProperties(applyUnitDataCase, applyUnitData);
                    caseData.setApplyUnitData(applyUnitDataCase);
                }
            }

            // 最後單位

            if (StringUtils.isNotBlank(evtData.getLsUbno())) {
                Caub lastUnitData = caubDao.getDisableReviewRpt01UnitDataBy(evtData.getLsUbno());
                if (lastUnitData != null) {
                    DisableReviewRpt01UnitCase lastUnitDataCase = new DisableReviewRpt01UnitCase();
                    BeanUtility.copyProperties(lastUnitDataCase, lastUnitData);
                    caseData.setLastUnitData(lastUnitDataCase);
                }
            }
            // ]

            // 取得 另案扣減資料
            // [
            // 一次給付

            List<DisableReviewRpt01DeductDataCase> deductList = new ArrayList<DisableReviewRpt01DeductDataCase>();
            List<Barxf> barxfList = barxfDao.selectDisableReviewRpt01DeductListBy(evtData.getEvtIdnNo(), evtData.getEvtBrDate());
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                barxfList.addAll(barxfDao.selectDisableReviewRpt01DeductListBy(kcaf.getBIdn(), kcaf.getBBrDte()));
            }
            for (Barxf barxf : barxfList) {
                DisableReviewRpt01DeductDataCase dataCase = new DisableReviewRpt01DeductDataCase();
                BeanUtility.copyProperties(dataCase, barxf);
                deductList.add(dataCase);
            }
            caseData.setDeductList(deductList);

            // 年金給付
            List<DisableReviewRpt01PayDeductDataCase> deductPayList = new ArrayList<DisableReviewRpt01PayDeductDataCase>();
            List<Baunacpdtl> baunacpdtlPayList = baunacpdtlDao.selectDisableReviewRpt01PayDeductListBy(evtData.getBenIdnNo());
            // 用關鍵欄位變更檔 去找資料baunacpdtlDao
            for (Kcaf kcaf : evtKcafList) {
                baunacpdtlPayList.addAll(baunacpdtlDao.selectDisableReviewRpt01PayDeductListBy(kcaf.getBIdn()));
            }
            for (Baunacpdtl baunacpdtl : baunacpdtlPayList) {
                DisableReviewRpt01PayDeductDataCase dataCase = new DisableReviewRpt01PayDeductDataCase();
                String benName = baappbaseDao.selectDisablePayDeductBenname(baunacpdtl.getApNo(), baunacpdtl.getSeqNo());
                BeanUtility.copyProperties(dataCase, baunacpdtl);
                dataCase.setBenName(benName);
                deductPayList.add(dataCase);
            }
            caseData.setDeductPayList(deductPayList);
            // ]

            // 國保資料payKind=38
            // [
            Nbappbase nbappbase38 = nbappbaseDao.selectDisabledReviewRpt01NpData38By(evtData.getMapNo());
            DisableReviewRpt01NpData38Case npData38 = new DisableReviewRpt01NpData38Case();
            List<DisableReviewRpt01NpData38Case> npData38List = new ArrayList<DisableReviewRpt01NpData38Case>();

            // 取得是否併計國保年資
            String comnpMk = baappexpandDao.selectComnpmkBy(evtData.getBaappbaseId(), evtData.getApNo(), evtData.getSeqNo());
            npData38.setComnpMk(comnpMk);

            if (nbappbase38 != null) {
                BeanUtility.copyProperties(npData38, nbappbase38);

                List<Nbdapr> nbdaprList = nbdaprDao.selectDisabledReviewRpt01NpDataList38By(nbappbase38.getApNo(), apNo, evtData.getIssuYm());
                if (nbdaprList.size() > 0) {
                    for (Nbdapr NbdaprData : nbdaprList) {
                        DisableReviewRpt01NpData38Case npData38ListData = new DisableReviewRpt01NpData38Case();
                        BeanUtility.copyProperties(npData38ListData, NbdaprData);
                        npData38List.add(npData38ListData);
                    }
                }
            }
            caseData.setNpData38(npData38);
            caseData.setNpData38List(npData38List);
            // ]

            // 國保資料payKind=36
            // [
            Nbappbase nbappbase36 = nbappbaseDao.selectDisabledReviewRpt01NpData36By(evtData.getMapNo());
            DisableReviewRpt01NpData36Case npData36 = new DisableReviewRpt01NpData36Case();
            List<DisableReviewRpt01NpData36Case> npData36List = new ArrayList<DisableReviewRpt01NpData36Case>();

            if (nbappbase36 != null) {
                BeanUtility.copyProperties(npData36, nbappbase36);

                List<Nbdapr> nbdaprList = nbdaprDao.selectDisabledReviewRpt01NpDataList36By(nbappbase36.getApNo(), nbappbase36.getIssuYm());
                if (nbdaprList.size() > 0) {
                    for (Nbdapr NbdaprData : nbdaprList) {
                        DisableReviewRpt01NpData36Case npData36ListData = new DisableReviewRpt01NpData36Case();
                        BeanUtility.copyProperties(npData36ListData, NbdaprData);
                        npData36List.add(npData36ListData);
                    }
                }
            }

            // 取得是否併計勞保年資
            String labMerge = nbappbaseDao.selectLabmergeByApNo(evtData.getMapNo());

            caseData.setLabMerge(labMerge);
            caseData.setNpData36(npData36);
            caseData.setNpData36List(npData36List);

            // ]

            // 併計勞保年資
            caseData.setLabMerge(nbappbaseDao.selectLabmergeByApNo(evtData.getMapNo()));

            caseList.add(caseData);

        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 勞保遺屬年金給付受理編審清單 的資料
     * 
     * @param apNoBegin 受理編號 (起)
     * @param apNoEnd 受理編號 (迄)
     * @return
     */
    public List<SurvivorReviewRpt01Case> getSurvivorReviewRpt01DataBy(String apNoBegin, String apNoEnd) {
        List<SurvivorReviewRpt01Case> caseList = new ArrayList<SurvivorReviewRpt01Case>();

        // 依傳入條件取得 給付主檔 (BAAPPBASE) 符合條件之 受理編號 (APNO)
        List<String> apNoList = baappbaseDao.selectSurvivorReviewRpt01ApNoListBy(apNoBegin, apNoEnd);

        for (String apNo : apNoList) {
            SurvivorReviewRpt01Case caseData = new SurvivorReviewRpt01Case();

            // 取得 給付主檔 (BAAPPBASE) 事故者資料

            Baappbase evtData = baappbaseDao.selectSurvivorReviewRpt01EvtDataBy(apNo);
            BeanUtility.copyProperties(caseData, evtData);

            // 20090421 新增 讀取關鍵欄位變更檔資料
            List<Kcaf> evtKcafList = getKcafListBy(evtData.getEvtIdnNo(), evtData.getEvtBrDate());

            String evtIdnNo = evtData.getEvtIdnNo();
            String evtBrDate = evtData.getEvtBrDate();
            // String evtJobDate = evtData.getEvtJobDate();

            caseData.setEvtNationCodeName(bacountryDao.selectCNameData(evtData.getEvtNationCode()));

            // 檢核是否有身分證重號的情況

            if (StringUtils.equals(evtData.getDupeIdnNoMk(), "2")) {
                Badupeidn dupeIdnData = badupeidnDao.selectSurvivorReviewRpt01DupeIdnDataBy(apNo);
                if (dupeIdnData != null) {
                    if (StringUtils.isNotBlank(dupeIdnData.getIdnNo()))
                        evtIdnNo = dupeIdnData.getIdnNo().trim();
                    if (StringUtils.isNotBlank(dupeIdnData.getBrDate()))
                        evtBrDate = dupeIdnData.getBrDate().trim();
                }
            }

            // 取得 另案扣減資料
            // [
            // 一次給付

            List<SurvivorReviewRpt01DeductDataCase> deductList = new ArrayList<SurvivorReviewRpt01DeductDataCase>();
            List<Barxf> barxfList = barxfDao.selectSurvivorReviewRpt01DeductListBy(evtData.getEvtIdnNo(), evtData.getEvtBrDate());
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                barxfList.addAll(barxfDao.selectSurvivorReviewRpt01DeductListBy(kcaf.getBIdn(), kcaf.getBBrDte()));
            }
            for (Barxf barxf : barxfList) {
                SurvivorReviewRpt01DeductDataCase dataCase = new SurvivorReviewRpt01DeductDataCase();
                BeanUtility.copyProperties(dataCase, barxf);
                deductList.add(dataCase);
            }

            // 取得 年金月給付金額
            // BigDecimal oldbAmt = badaprDao.getSurvivorReviewRpt01BenPayDataByOldbAmt(evtData.getDabApNo());
            // caseData.setDabAnnuAmt(oldbAmt);

            // 取得已扣失能、未扣失能

            BigDecimal lecomAmt = badaprDao.getSurvivorReviewRpt01BenPayDataByLecomAmt(apNo); // 已扣失能
            caseData.setLecomAmt(lecomAmt);
            BigDecimal recomAmt = badaprDao.getSurvivorReviewRpt01BenPayDataByRecomAmt(apNo); // 未扣失能
            caseData.setRecomAmt(recomAmt);

            // 取得 給付主檔 (BAAPPBASE) 遺屬資料

            List<Baappbase> benDataList = baappbaseDao.selectSurvivorReviewRpt01BenListBy(apNo);

            List<SurvivorReviewRpt01BenDataCase> benList = new ArrayList<SurvivorReviewRpt01BenDataCase>();
            for (Baappbase benData : benDataList) {

                // 取得每位遺屬資料
                List<Baappbase> survivorBenDataList = baappbaseDao.selectSurvivorRpt01BenDataList(apNo, benData.getSeqNo());

                for (Baappbase survivorBenData : survivorBenDataList) {
                    SurvivorReviewRpt01BenDataCase benDataCase = new SurvivorReviewRpt01BenDataCase();
                    BeanUtility.copyProperties(benDataCase, survivorBenData);

                    // 取得 每位受款人 學校代碼的中文
                    if (benDataCase != null && !benDataCase.getSchoolCode().equals("")) {
                        Npcode npcodeData = npcodeDao.selectNpCodeNameBy(benDataCase.getSchoolCode());
                        if (npcodeData != null) {
                            benDataCase.setSchoolCodeStr(npcodeData.getCodeName());
                        }
                    }

                    // 取得 每位受款人 給付核定檔 (BADAPR) 遺屬給付資料
                    List<SurvivorReviewRpt01BenPayDataCase> benPayCaseList = new ArrayList<SurvivorReviewRpt01BenPayDataCase>();
                    List<Badapr> benPayDataList = badaprDao.selectSurvivorReviewRpt01BenPayDataBy(apNo, benData.getSeqNo(), benData.getIssuYm());
                    for (Badapr benPayData : benPayDataList) {
                        SurvivorReviewRpt01BenPayDataCase benPayCase = new SurvivorReviewRpt01BenPayDataCase();
                        BeanUtility.copyProperties(benPayCase, benPayData);
                        benPayCaseList.add(benPayCase);

                    }
                    benDataCase.setBenPayDataList(benPayCaseList);

                    // 取得每位繼承人國籍名稱

                    benDataCase.setBenNationCodeName(bacountryDao.selectCNameData(benData.getBenNationCode()));

                    // 取得 每位遺屬 給付編審檔 (BACHKFILE) 遺屬編審註記
                    List<SurvivorReviewRpt01ChkfileDataCase> chkFileCaseList = new ArrayList<SurvivorReviewRpt01ChkfileDataCase>();
                    List<Bachkfile> chkfileDataList = bachkfileDao.getSurvivorReviewRpt01ChkfileDataBy(apNo, benData.getSeqNo(), benData.getIssuYm());
                    for (Bachkfile chkfileData : chkfileDataList) {
                        SurvivorReviewRpt01ChkfileDataCase chkfileDataCase = new SurvivorReviewRpt01ChkfileDataCase();
                        BeanUtility.copyProperties(chkfileDataCase, chkfileData);
                        chkFileCaseList.add(chkfileDataCase);
                    }

                    benDataCase.setChkfileDataList(chkFileCaseList);

                    // 取得 每位眷屬 給付編審檔 (BACHKFILE) 眷屬符合註記
                    List<SurvivorReviewRpt01ChkfileDataCase> chkCaseList = new ArrayList<SurvivorReviewRpt01ChkfileDataCase>();
                    List<Bachkfile> chkList = bachkfileDao.getSurvivorReviewRpt01BenChkfileBy(apNo, benData.getSeqNo(), benData.getIssuYm());
                    for (Bachkfile chkData : chkList) {
                        SurvivorReviewRpt01ChkfileDataCase chkCase = new SurvivorReviewRpt01ChkfileDataCase();
                        BeanUtility.copyProperties(chkCase, chkData);
                        chkCaseList.add(chkCase);
                    }

                    benDataCase.setChkDataList(chkCaseList);

                    // 取得 每位眷屬 重殘期間檔 (BAHANDICAP)
                    List<SurvivorReviewRpt01BenDataCase> handicapCaseList = new ArrayList<SurvivorReviewRpt01BenDataCase>();
                    Bahandicapterm bahandicapData = bahandicaptermDao.selectHandicapMasterDataForSurvivorPaymentQuery(apNo, benData.getSeqNo());
                    
                    SurvivorReviewRpt01BenDataCase handicapCase = new SurvivorReviewRpt01BenDataCase();
                    BeanUtility.copyProperties(handicapCase, bahandicapData);
                    handicapCaseList.add(handicapCase);
                    benDataCase.setHandicapSdate(bahandicapData.getHandicapSdate());
                    benDataCase.setHandicapEdate(bahandicapData.getHandicapEdate());
                    benDataCase.setHandicaptermCount(bahandicapData.getHandicapDataCount());
                    
                    benList.add(benDataCase);
                }
                caseData.setBenList(benList);

                // 取得 另案扣減資料
                // [
                // 一次給付

                List<Kcaf> benKcafList = getKcafListBy(benData.getBenIdnNo(), benData.getBenBrDate());
                barxfList = barxfDao.selectSurvivorReviewRpt01DeductListBy(benData.getBenIdnNo(), benData.getBenBrDate());
                // 用關鍵欄位變更檔 去找資料
                for (Kcaf kcaf : benKcafList) {
                    barxfList.addAll(barxfDao.selectSurvivorReviewRpt01DeductListBy(kcaf.getBIdn(), kcaf.getBBrDte()));
                }
                for (Barxf barxf : barxfList) {
                    SurvivorReviewRpt01DeductDataCase dataCase = new SurvivorReviewRpt01DeductDataCase();
                    BeanUtility.copyProperties(dataCase, barxf);
                    deductList.add(dataCase);
                }
            }
            caseData.setDeductList(deductList);

            // 取得 每位眷屬 給付編審檔 (BACHKFILE) 遺屬編審註記
            List<SurvivorReviewRpt01ChkfileDataCase> chkSurivorDescCaseList = new ArrayList<SurvivorReviewRpt01ChkfileDataCase>();
            List<Bachkfile> chkfileSurivorDescDataList = bachkfileDao.getSurvivorReviewRpt01ChkfileDescDataBy(apNo, evtData.getIssuYm());
            for (Bachkfile chkfileSurivorDescData : chkfileSurivorDescDataList) {
                SurvivorReviewRpt01ChkfileDataCase chkfileSurivorDescDataCase = new SurvivorReviewRpt01ChkfileDataCase();
                BeanUtility.copyProperties(chkfileSurivorDescDataCase, chkfileSurivorDescData);
                chkSurivorDescCaseList.add(chkfileSurivorDescDataCase);
            }

            caseData.setChkSurvivorDescDataList(chkSurivorDescCaseList);

            // 取得 每位眷屬 給付編審檔 (BACHKFILE) 遺屬符合註記
            List<SurvivorReviewRpt01ChkfileDataCase> benChkDescDataList = new ArrayList<SurvivorReviewRpt01ChkfileDataCase>();
            List<Bachkfile> chkDescDataList = bachkfileDao.getSurvivorReviewRpt01BenChkfileDescData(apNo, evtData.getIssuYm());
            for (Bachkfile chkDesc : chkDescDataList) {
                SurvivorReviewRpt01ChkfileDataCase chkDescDataCase = new SurvivorReviewRpt01ChkfileDataCase();
                BeanUtility.copyProperties(chkDescDataCase, chkDesc);
                benChkDescDataList.add(chkDescDataCase);
            }

            caseData.setBenChkDescList(benChkDescDataList);

            // 取得 給付主檔 (BAAPPBASE) 繼承人資料

            List<SurvivorReviewRpt01BenDataCase> extList = new ArrayList<SurvivorReviewRpt01BenDataCase>();
            List<Baappbase> extDataList = baappbaseDao.selectSurvivorRpt01ExtDataList(apNo);
            for (Baappbase extData : extDataList) {
                SurvivorReviewRpt01BenDataCase extDataCase = new SurvivorReviewRpt01BenDataCase();
                BeanUtility.copyProperties(extDataCase, extData);
                // 取得 每位受款人 給付核定檔 (BADAPR) 受款人給付資料

                List<SurvivorReviewRpt01BenPayDataCase> benPayCaseList = new ArrayList<SurvivorReviewRpt01BenPayDataCase>();
                List<Badapr> benPayDataList = badaprDao.selectSurvivorReviewRpt01BenPayDataBy(extData.getApNo(), extData.getSeqNo(), extData.getIssuYm());
                for (Badapr benPayData : benPayDataList) {
                    SurvivorReviewRpt01BenPayDataCase benPayCase = new SurvivorReviewRpt01BenPayDataCase();
                    BeanUtility.copyProperties(benPayCase, benPayData);
                    benPayCaseList.add(benPayCase);
                }
                extDataCase.setBenPayList(benPayCaseList);

                // 取得每位繼承人國籍名稱

                extDataCase.setBenNationCodeName(bacountryDao.selectCNameData(extData.getBenNationCode()));

                // 取得每位繼承人繼承自
                List<Baappbase> refBenList = baappbaseDao.selectSurvivorRpt01ExtRefDataList(extData.getApNo(), extData.getSeqNo());
                for (Baappbase refBenData : refBenList) {
                    extDataCase.setRefBenName(refBenData.getBenName());
                }

                extList.add(extDataCase);
            }
            caseData.setExtList(extList);

            // 取得 與失能相關之欄位資料
            List<SurvivorRevewRpt01ExpDataCase> expDataCaseList = new ArrayList<SurvivorRevewRpt01ExpDataCase>();
            List<Baappexpand> expDataList = baappexpandDao.getSurvivorBaappexpandListByEvt(apNo);
            for (Baappexpand expData : expDataList) {
                SurvivorRevewRpt01ExpDataCase expDataCase = new SurvivorRevewRpt01ExpDataCase();
                BeanUtility.copyProperties(expDataCase, expData);
                expDataCaseList.add(expDataCase);

            }
            caseData.setDisableList(expDataCaseList);

            // 取得 本次紓困貸款(BADAPR) 給付資料
            List<SurvivorReviewRpt01LoanAmtCase> loanAmtList = new ArrayList<SurvivorReviewRpt01LoanAmtCase>();
            List<Badapr> loanAmtDataList = badaprDao.getSurvivorReviewRpt01LoanAmt(apNo, evtData.getIssuYm());
            for (Badapr loanAmtData : loanAmtDataList) {
                SurvivorReviewRpt01LoanAmtCase loanAmtDataCase = new SurvivorReviewRpt01LoanAmtCase();
                BeanUtility.copyProperties(loanAmtDataCase, loanAmtData);
                loanAmtList.add(loanAmtDataCase);
            }
            caseData.setLoanAmtData(loanAmtList);

            // 取得 給付核定檔 (BADAPR) 前次核定金額
            Badapr onceIssuCalcAmt = badaprDao.selectOnceIssuCalcAmtBy(apNo);
            if (onceIssuCalcAmt != null) {
                caseData.setIssuCalcAmt(onceIssuCalcAmt.getIssuCalcAmt());
            }
            // 取得 被保險人投保薪資檔 (CIPG)

            // String adWkMk = expDataCaseList != null && expDataCaseList.get(0).getAdWkMk() != null ? expDataCaseList.get(0).getAdWkMk() : "";
            // String ocaccIdentMk = expDataCaseList != null && expDataCaseList.get(0).getOcaccIdentMk() != null ? expDataCaseList.get(0).getOcaccIdentMk() : "";
            String inTyp = "L";
            if(expDataCaseList != null && expDataCaseList.size() > 0) {
                if (expDataCaseList.get(0).getAdWkMk() != null) {
                    inTyp = (expDataCaseList.get(0).getAdWkMk()).equals("2") ? "V" : "L";
                }
            } else {
            	return null;
            }

            List<SurvivorReviewRpt01CipgDataCase> avgWgDataCaseList = new ArrayList<SurvivorReviewRpt01CipgDataCase>();

            Integer appMonth = null;
            String appMonthStr = "";
            appMonth = bapaavgmonDao.getBapaavgmonCount();
            String realAvgMon = "";
            // 他案受理編號 身分證號
            String dabapNo = "";
            // 原案件身分證字號
            String evtIdnNoOrg = caseData.getEvtIdnNo();
            if (StringUtils.equals(caseData.getDupeIdnNoMk(), "2")) {
                Badupeidn dupeIdnData = badupeidnDao.getOldAgeReviewRpt01DupeIdnDataBy(caseData.getApNo());
                if (dupeIdnData != null) {
                    if (StringUtils.isNotBlank(dupeIdnData.getIdnNo())) {
                        evtIdnNoOrg = dupeIdnData.getIdnNo();
                    }

                }
            }
            // ApItem 7或8 他案身份證字號
            String evtIdnNoAfter = "";

            if (appMonth == 0) {
                appMonthStr = "60";
            }
            else {
                if (caseData.getApItem().equals("7") || caseData.getApItem().equals("8")) {
                    Baappbase baappbaseData = baappbaseDao.selectDataForSurvivorAvgAmtDetail(caseData.getDabApNo());
                    if (baappbaseData != null) {
                        appMonthStr = bapaavgmonDao.getBapaavgmonAppMonth(baappbaseData.getAppDate());
                        dabapNo = baappbaseData.getApNo();
                        evtIdnNoAfter = baappbaseData.getEvtIdnNo();
                        // 身分證重號處理
                        if (StringUtils.equals(baappbaseData.getDupeIdnNoMk(), "2")) {
                            Badupeidn dupeIdnData = badupeidnDao.getOldAgeReviewRpt01DupeIdnDataBy(baappbaseData.getApNo());
                            if (dupeIdnData != null) {
                                if (StringUtils.isNotBlank(dupeIdnData.getIdnNo())) {
                                    evtIdnNoAfter = dupeIdnData.getIdnNo();
                                }

                            }
                        }

                    }
                    if (StringUtils.isBlank(appMonthStr)) {
                        appMonthStr = "60";
                    }
                }
                else {
                    appMonthStr = bapaavgmonDao.getBapaavgmonAppMonth(caseData.getAppDate());
                    if (StringUtils.isBlank(appMonthStr)) {
                        appMonthStr = "60";
                    }
                }
            }

            // 60個月平均薪資資料
            List<Cipg> avgWgDataList = new ArrayList<Cipg>();

            if (caseData.getApItem().equals("7")) {
                // 他案 失能
                Baappbase disableEvtData = baappbaseDao.selectDisableReviewRpt01EvtDataBy(dabapNo);
                List<Baappexpand> disableExpDataList = baappexpandDao.getDisableBaappexpandListBy(disableEvtData.getBaappbaseId(), dabapNo);
                if (StringUtils.isNotBlank(dabapNo) && StringUtils.isNotBlank(evtIdnNoAfter)) {
                    avgWgDataList = cipgDao.selectDisableReviewRpt01CipgData(dabapNo, "0000", evtIdnNoAfter, disableExpDataList.get(0).getEvTyp(), inTyp, disableExpDataList.get(0).getPrType(), appMonthStr);
                }
            }
            else if (caseData.getApItem().equals("8")) {
                // 他案 老年
                if (StringUtils.isNotBlank(dabapNo) && StringUtils.isNotBlank(evtIdnNoAfter)) {
                    avgWgDataList = cipgDao.selectOldAgeReviewRpt01SixtyMonthAvgAmtList(dabapNo, "0000", evtIdnNoAfter, appMonthStr);
                }
            }
            else {
                avgWgDataList = new ArrayList<Cipg>();
                avgWgDataList = cipgDao.selectSurvivorReviewRpt01CipgData(apNo, "0000", evtIdnNoOrg, expDataList.get(0).getEvTyp(), inTyp, expDataCaseList.get(0).getPrType(), appMonthStr);
            }

            for (Cipg avgWgData : avgWgDataList) {
                SurvivorReviewRpt01CipgDataCase avgWgDataCase = new SurvivorReviewRpt01CipgDataCase();
                BeanUtility.copyProperties(avgWgDataCase, avgWgData);
                avgWgDataCaseList.add(avgWgDataCase);
            }

            // 取得 實際均薪月數
            if (StringUtils.isNotBlank(expDataList.get(0).getEvTyp()) && ("3".equals(expDataList.get(0).getEvTyp()) || "4".equals(expDataList.get(0).getEvTyp()))
                            && (expDataCaseList.get(0).getPrType() == null || "Y".equals(expDataCaseList.get(0).getPrType()))) {
                if (caseData.getApItem().equals("7")) {
                    // 失能
                    Baappbase disableEvtData = baappbaseDao.selectDisableReviewRpt01EvtDataBy(dabapNo);
                    List<Baappexpand> disableExpDataList = baappexpandDao.getDisableBaappexpandListBy(disableEvtData.getBaappbaseId(), dabapNo);

                    if (StringUtils.isNotBlank(disableExpDataList.get(0).getEvTyp()) && ("3".equals(disableExpDataList.get(0).getEvTyp()) || "4".equals(disableExpDataList.get(0).getEvTyp()))
                                    && (disableExpDataList.get(0).getPrType() == null || !"Y".equals(disableExpDataList.get(0).getPrType()))) {
                        if (StringUtils.isNotBlank(dabapNo) && StringUtils.isNotBlank(evtIdnNoAfter)) {
                            realAvgMon = cipgDao.getRealAvgMonForDisableReviewRpt01By(dabapNo, "0000", evtIdnNoAfter, inTyp, "1");
                        }
                    }
                    else {
                        if (StringUtils.isNotBlank(dabapNo) && StringUtils.isNotBlank(evtIdnNoAfter)) {
                            realAvgMon = cipgDao.getRealAvgMonForDisableReviewRpt01By(dabapNo, "0000", evtIdnNoAfter, inTyp, "6");
                        }
                    }
                }
                else if (caseData.getApItem().equals("8")) {
                    // 老年
                    if (StringUtils.isNotBlank(dabapNo) && StringUtils.isNotBlank(evtIdnNoAfter)) {
                        realAvgMon = cipgDao.getRealAvgMonForOldAge(dabapNo, "0000", evtIdnNoAfter);
                    }
                }
                else {
                    realAvgMon = cipgDao.getRealAvgMonForSurvivorReviewRpt01By(apNo, "0000", evtIdnNoOrg, inTyp, "1");
                }
            }
            else {
                if (caseData.getApItem().equals("7")) {
                    // 失能
                    Baappbase disableEvtData = baappbaseDao.selectDisableReviewRpt01EvtDataBy(dabapNo);
                    List<Baappexpand> disableExpDataList = baappexpandDao.getDisableBaappexpandListBy(disableEvtData.getBaappbaseId(), dabapNo);

                    if (StringUtils.isNotBlank(disableExpDataList.get(0).getEvTyp()) && ("3".equals(disableExpDataList.get(0).getEvTyp()) || "4".equals(disableExpDataList.get(0).getEvTyp()))
                                    && (disableExpDataList.get(0).getPrType() == null || !"Y".equals(disableExpDataList.get(0).getPrType()))) {
                        if (StringUtils.isNotBlank(dabapNo) && StringUtils.isNotBlank(evtIdnNoAfter)) {
                            realAvgMon = cipgDao.getRealAvgMonForDisableReviewRpt01By(dabapNo, "0000", evtIdnNoAfter, inTyp, "1");
                        }
                    }
                    else {
                        if (StringUtils.isNotBlank(dabapNo) && StringUtils.isNotBlank(evtIdnNoAfter)) {
                            realAvgMon = cipgDao.getRealAvgMonForDisableReviewRpt01By(dabapNo, "0000", evtIdnNoAfter, inTyp, "6");
                        }
                    }
                }
                else if (caseData.getApItem().equals("8")) {
                    // 老年
                    if (StringUtils.isNotBlank(dabapNo) && StringUtils.isNotBlank(evtIdnNoAfter)) {
                        realAvgMon = cipgDao.getRealAvgMonForOldAge(dabapNo, "0000", evtIdnNoAfter);
                    }
                }
                else {
                    realAvgMon = cipgDao.getRealAvgMonForSurvivorReviewRpt01By(apNo, "0000", evtIdnNoOrg, inTyp, "6");
                }
            }

            caseData.setCipgData(avgWgDataCaseList);
            caseData.setAppMonth(appMonthStr); // 平均薪資月數
            caseData.setRealAvgMon(realAvgMon); // 實際均薪月數

            // 取得 投保單位檔 (CAUB) 投保單位資料
            // [
            // 申請單位
            if (StringUtils.isNotBlank(evtData.getApUbno())) {
                Caub applyUnitData = caubDao.getSurvivorReviewRpt01UnitDataBy(evtData.getApUbno());
                if (applyUnitData != null) {
                    SurvivorReviewRpt01UnitCase applyUnitDataCase = new SurvivorReviewRpt01UnitCase();
                    BeanUtility.copyProperties(applyUnitDataCase, applyUnitData);
                    caseData.setApplyUnitData(applyUnitDataCase);
                }
            }

            // 取得 給付核定檔

            List<SurvivorReviewRpt01PayOldDataCase> payOldDataList = new ArrayList<SurvivorReviewRpt01PayOldDataCase>();

            List<Badapr> payOldList = badaprDao.getSurvivorReviewRpt01OldPayListBy(apNo, caseData.getIssuYm());
            for (Badapr payOldData : payOldList) {
                SurvivorReviewRpt01PayOldDataCase payOldCase = new SurvivorReviewRpt01PayOldDataCase();
                BeanUtility.copyProperties(payOldCase, payOldData);
                payOldDataList.add(payOldCase);
            }
            caseData.setEvtPayOldList(payOldDataList);

            // 取得 給付核定檔 (BADAPR)職災相關資料

            // SurvivorQueryOccAccDataCase occAccCase = new SurvivorQueryOccAccDataCase();
            // Badapr badapr = badaprDao.selectOccAccDataForDisabledPaymentReview(apNo);
            // if (badapr != null) {
            // BeanUtility.copyProperties(occAccCase, badapr);
            // }
            // caseData.setOccAcc(occAccCase);

            // 取得 給付核定檔 (BADAPR) 核定資料
            SurvivorReviewRpt01DecideDataCase decideCase = new SurvivorReviewRpt01DecideDataCase();
            Badapr decideData = badaprDao.getSurvivorReviewRpt01DecideDataBy(apNo, evtData.getIssuYm());
            if (decideData != null) {
                BeanUtility.copyProperties(decideCase, decideData);
            }
            caseData.setDecideData(decideCase);

            // 取得 給付核定檔 (BADAPR) 核定總額資料
            SurvivorReviewRpt01IssueAmtDataCase issueAmtCase = new SurvivorReviewRpt01IssueAmtDataCase();
            Badapr issueAmtData = badaprDao.selectSurvivorReviewRpt01IssueAmtDataBy(apNo, evtData.getIssuYm());
            if (issueAmtData != null) {
                BeanUtility.copyProperties(issueAmtCase, issueAmtData);
            }
            caseData.setIssueAmtData(issueAmtCase);

            // 取得 給付核定檔 (BADAPR) 給付資料
            List<SurvivorReviewRpt01PayDataCase> payList = new ArrayList<SurvivorReviewRpt01PayDataCase>();
            List<Badapr> payDataList = badaprDao.selectSurvivorReviewRpt01PayDataBy(apNo, evtData.getIssuYm());
            for (Badapr payData : payDataList) {
                SurvivorReviewRpt01PayDataCase payDataCase = new SurvivorReviewRpt01PayDataCase();
                BeanUtility.copyProperties(payDataCase, payData);
                payList.add(payDataCase);
            }
            caseData.setPayList(payList);

            // 取得 給付編審檔 (BACHKFILE) 編審資料 - 事故者編審註記

            List<SurvivorReviewRpt01ChkfileDataCase> chkfileCaseList = new ArrayList<SurvivorReviewRpt01ChkfileDataCase>();
            List<Bachkfile> chkfileDataList = bachkfileDao.selectSurvivorReviewRpt01ChkfileDataBy(apNo, "0000", evtData.getIssuYm());
            for (Bachkfile chkfileData : chkfileDataList) {
                SurvivorReviewRpt01ChkfileDataCase chkfileCase = new SurvivorReviewRpt01ChkfileDataCase();
                BeanUtility.copyProperties(chkfileCase, chkfileData);
                chkfileCaseList.add(chkfileCase);
            }
            caseData.setChkfileDataList(chkfileCaseList);

            // 取得 給付編審檔 (BACHKFILE) 編審資料 - 編審註記說明
            List<SurvivorReviewRpt01ChkfileDataCase> evtChkfileDescList = new ArrayList<SurvivorReviewRpt01ChkfileDataCase>();
            List<Bachkfile> evtChkfileDescCaseList = bachkfileDao.getSurvivorReviewRpt01ChkfileDescBy(apNo, "0000", evtData.getIssuYm());
            for (Bachkfile evtChkfileDescData : evtChkfileDescCaseList) {
                SurvivorReviewRpt01ChkfileDataCase evtChkfileDescCase = new SurvivorReviewRpt01ChkfileDataCase();
                BeanUtility.copyProperties(evtChkfileDescCase, evtChkfileDescData);
                evtChkfileDescList.add(evtChkfileDescCase);
            }
            caseData.setChkfileDescList(evtChkfileDescList);

            // 取得 核定通知書格式檔 (BANOTIFY 核定通知書資料
            List<Baappbase> baappbaseList = baappbaseDao.selectMonthlyRpt05Report(apNo);
            List<Badapr> badaprList = badaprDao.selectDataForReportReplace(evtData.getApNo(), evtData.getIssuYm());
            // List<Badapr> badaprList = badaprDao.getMonthlyRpt05ListBy(evtData.getApNo(), evtData.getIssuYm());
            String chkResult = bachkfileDao.selectForRptReplace(evtData.getApNo(), evtData.getSeqNo(), evtData.getIssuYm());

            // 取得須列印資料 SeqNo
            // 2017增修案：新案讀取該受理編號主檔序號非0000之各受款人姓名當受文者，續發案以<A146>之對象為受文者。
            // 20171017需求：不論何種案件皆以<A146>之對象為受文者。
            List<Baappbase> baappbasePrintList = new ArrayList<Baappbase>();
            List<Baappbase> receiveName = new ArrayList<Baappbase>();
            String sReceiveName = "";
            String sCommZip = "";
            String sCommAddr = "";
            List<Baappbase> receiveBenNameList = new ArrayList<Baappbase>();

//            if (evtData.getCaseTyp().equals("2")) {
            baappbasePrintList = baappbaseDao.selectMonthlyRpt05PrintCase2Data(apNo, evtData.getIssuYm());

            // 受文者
            receiveName = baappbaseDao.getReceiveNameCase2By(apNo);

            if (!(receiveName == null) && receiveName.size() > 0) {
                receiveBenNameList = baappbaseDao.getReceiveBenNameCase2By(apNo, receiveName.get(0).getReceiveName());
            }
//            }
//            else {
//                baappbasePrintList = baappbaseDao.selectMonthlyRpt05PrintData(apNo);

                // 受文者
//                receiveName = baappbaseDao.getReceiveNameBy(apNo);
//                receiveBenNameList = baappbaseDao.getReceiveBenNameBy(apNo, receiveName);
//            }
         
            if (receiveBenNameList.size() > 0) {

                String receiveBenName = "";
                for (int i = 0; i < receiveBenNameList.size(); i++) {
                    if (i + 1 == receiveBenNameList.size()) {
                        receiveBenName = receiveBenName + receiveBenNameList.get(i).getBenName();
                    }
                    else {
                        receiveBenName = receiveBenName + receiveBenNameList.get(i).getBenName() + "、";
                    }
                }
                // 受文者字串組合
                if (receiveBenNameList.get(0).getReceiveMk().equals("Y")) {
                    sReceiveName = receiveName.get(0).getReceiveName() + "(兼" + receiveBenName + "之法定代理人)";
                }
                else if (receiveBenNameList.get(0).getReceiveMk().equals("N")) {
                    sReceiveName = receiveName.get(0).getReceiveName() + "(" + receiveBenName + "之法定代理人)";
                }
                else if(receiveBenNameList.get(0).getReceiveMk().equals("A")) {
                	sReceiveName = receiveName.get(0).getReceiveName();
                }

                sCommZip = receiveName.get(0).getCommZip();
                sCommAddr = receiveName.get(0).getCommAddr();
//                sCommZip = receiveBenNameList.get(receiveBenNameList.size() - 1).getCommZip();
//                sCommAddr = receiveBenNameList.get(receiveBenNameList.size() - 1).getCommAddr();
            }
            else {
                if (!(receiveName == null) && receiveName.size() > 0) {
                    sReceiveName = receiveName.get(0).getReceiveName();
                    sCommZip = receiveName.get(0).getCommZip();
                    sCommAddr = receiveName.get(0).getCommAddr();
                }
                else {
                    sReceiveName = "";
                    sCommZip = "";
                    sCommAddr = "";
                }
            }
            
            caseData.setReceiveName(sReceiveName);

            // 給付別
            String receivePayTyp = baappbaseDao.getReceivePayTypBy(apNo);
            // [
            // a. 若核定通知書格式為 999 或為 空白 及 NULL, 則整段核定通知書不顯示
            // b. 若查無受理案件核定通知書格式, 則整段核定通知書不顯示
            if (StringUtils.isNotBlank(evtData.getNotifyForm()) && !StringUtils.equals(evtData.getNotifyForm(), "999")) {
                // BaReportReplaceUtility strReplace = new BaReportReplaceUtility(evtData,benDataList, false);
                List<BapairrCase> pairrList = new ArrayList<BapairrCase>();
                CheckAmtCase checkAmtCaseData = new CheckAmtCase(pairrList);
                if (evtData.getApItem().equals("7") || evtData.getApItem().equals("8")) {
                    if (evtData.getApItem().equals("7")) {
                        checkAmtCaseData = getCheckAmtCaseBy(null, "K");
                    }
                    else {
                        checkAmtCaseData = getCheckAmtCaseBy(null, "L");
                    }
                }
                else {
                    checkAmtCaseData = getCheckAmtCaseBy(null, evtData.getApNo().substring(0, 1));
                }

                Baappexpand expDataCase = new Baappexpand();
                for (Baappexpand expData : expDataList) {
                    BeanUtility.copyProperties(expDataCase, expData);
                }

                // BaReportReplaceUtility strReplace = new BaReportReplaceUtility(evtData,null , receivePayTyp, badaprList.get(0), StringUtils.trimToEmpty(chkResult), baappbaseList, checkAmtCaseData, evtData.getIssuYm(), true);
                BaReportReplaceUtility strReplace = new BaReportReplaceUtility(evtData, expDataCase, receivePayTyp, badaprList.get(0), StringUtils.trimToEmpty(chkResult), benDataList, checkAmtCaseData, evtData.getIssuYm(), true, null,
                                baappbasePrintList);

                // 取得 核定通知書 - 主旨
                List<Banotify> subjectList = banotifyDao.getSurvivorReviewRpt01NotifyListBy(StringUtils.substring(apNo, 0, 1), evtData.getNotifyForm(), "0");
                if (subjectList.size() > 0) {
                    SurvivorReviewRpt01NotifyDataCase notifyCase = new SurvivorReviewRpt01NotifyDataCase();
                    notifyCase.setSubject(strReplace.replace(StringUtils.defaultString(subjectList.get(0).getDataCont())));

                    // 取得 核定通知書 - 說明
                    List<Banotify> contentList = banotifyDao.getSurvivorReviewRpt01NotifyListBy(StringUtils.substring(apNo, 0, 1), evtData.getNotifyForm(), "1");
                    List<String> contents = new ArrayList<String>();
                    for (Banotify contentData : contentList) {
                        if (StringUtils.isNotBlank(contentData.getDataCont()))
                            contents.add(strReplace.replace(contentData.getDataCont()));
                    }
                    notifyCase.setContent(contents);
                    // notifyCase.setCommZip(benDataList.get(0).getCommZip());
                    // notifyCase.setCommAddr(benDataList.get(0).getCommAddr());
                    notifyCase.setCommZip(sCommZip);
                    notifyCase.setCommAddr(sCommAddr);
                    
                    caseData.setNotifyData(notifyCase);
                }
            }

            // 取得 請領同類給付資料

            // [
            // 取得 現金給付參考檔 (PBBMSA) 一次給付資料

            List<SurvivorReviewRpt01OncePayDataCase> oncePayCaseList = new ArrayList<SurvivorReviewRpt01OncePayDataCase>();
            List<Pbbmsa> oncePayDataList = pbbmsaDao.selectSurvivorReviewRpt01OncePayListBy(caseData.getEvtIdnNo(), caseData.getEvtBrDate());
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                oncePayDataList.addAll(pbbmsaDao.selectSurvivorReviewRpt01OncePayListBy(StringUtils.substring(kcaf.getBIdn(), 0, 10), kcaf.getBBrDte()));
            }
            for (Pbbmsa oncePayData : oncePayDataList) {
                SurvivorReviewRpt01OncePayDataCase oncePayDataCase = new SurvivorReviewRpt01OncePayDataCase();
                BeanUtility.copyProperties(oncePayDataCase, oncePayData);
                oncePayCaseList.add(oncePayDataCase);
            }
            caseData.setOncePayList(oncePayCaseList);

            // 取得 年金給付資料
            List<SurvivorReviewRpt01AnnuityPayDataCase> annuityPayCaseList = new ArrayList<SurvivorReviewRpt01AnnuityPayDataCase>();
            List<Baappbase> annuityPayDataList = baappbaseDao.selectSurvivorReviewRpt01AnnuityPayListBy(apNo, caseData.getEvtIdnNo(), caseData.getEvtBrDate());
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                annuityPayDataList.addAll(baappbaseDao.selectSurvivorReviewRpt01AnnuityPayListBy(apNo, StringUtils.substring(kcaf.getBIdn(), 0, 10), kcaf.getBBrDte()));
            }
            for (Baappbase annuityPayData : annuityPayDataList) {
                SurvivorReviewRpt01AnnuityPayDataCase annuityPayDataCase = new SurvivorReviewRpt01AnnuityPayDataCase();
                BeanUtility.copyProperties(annuityPayDataCase, annuityPayData);

                Badapr dateData = badaprDao.selectSurvivorReviewRpt01DateDataBy(annuityPayData.getApNo());
                if (dateData != null) {
                    annuityPayDataCase.setChkDate(dateData.getChkDate()); // 核定日期
                    annuityPayDataCase.setAplpayDate(dateData.getAplpayDate()); // 核付日期
                    annuityPayDataCase.setRecAmt(dateData.getRecAmt()); // 收回金額
                    annuityPayDataCase.setSupAmt(dateData.getSupAmt()); // 補發金額
                }
                Maadmrec annuityPayMaadmrecData = maadmrecDao.selectSurvivorReviewRpt01AnnuityPayDataBy(annuityPayData.getApNo(), annuityPayData.getIssuYm());
                if (annuityPayMaadmrecData != null) {
                    annuityPayDataCase.setProdate(annuityPayMaadmrecData.getProDate()); // 補件日期
                    annuityPayDataCase.setNdomk1(annuityPayMaadmrecData.getNdomk1()); // 處理註記
                }

                Baappexpand annuityPayBaappexpand = baappexpandDao.getSurvivorReviewRpt01AnnuityPayList(annuityPayData.getApNo());
                if (annuityPayBaappexpand != null) {
                    annuityPayDataCase.setEvTyp(annuityPayBaappexpand.getEvTyp());// 傷病分類
                }

                annuityPayCaseList.add(annuityPayDataCase);
            }
            caseData.setAnnuityPayList(annuityPayCaseList);

            // ]

            // 取得 請領他類給付資料

            // [
            // 取得 老年年金給付記錄資料
            List<SurvivorReviewRpt01AnnuityPayDataCase> oldPayCaseList = new ArrayList<SurvivorReviewRpt01AnnuityPayDataCase>();
            List<Baappbase> oldPayDataList = baappbaseDao.selectSurvivorReviewRpt01OldPayListBy(apNo, caseData.getEvtIdnNo(), caseData.getEvtBrDate());
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                oldPayDataList.addAll(baappbaseDao.selectSurvivorReviewRpt01OldPayListBy(apNo, StringUtils.substring(kcaf.getBIdn(), 0, 10), kcaf.getBBrDte()));
            }
            for (Baappbase oldPayData : oldPayDataList) {
                SurvivorReviewRpt01AnnuityPayDataCase oldPayDataCase = new SurvivorReviewRpt01AnnuityPayDataCase();
                BeanUtility.copyProperties(oldPayDataCase, oldPayData);

                Badapr dateData = badaprDao.selectSurvivorReviewRpt01DateDataBy(oldPayData.getApNo());
                if (dateData != null) {
                    oldPayDataCase.setChkDate(dateData.getChkDate()); // 核定日期
                    oldPayDataCase.setAplpayDate(dateData.getAplpayDate()); // 核付日期
                    oldPayDataCase.setRecAmt(dateData.getRecAmt()); // 收回金額
                    oldPayDataCase.setSupAmt(dateData.getSupAmt()); // 補發金額
                }
                Maadmrec oldPayMaadmrecData = maadmrecDao.selectSurvivorReviewRpt01AnnuityPayDataBy(oldPayData.getApNo(), oldPayData.getIssuYm());
                if (oldPayMaadmrecData != null) {
                    oldPayDataCase.setProdate(oldPayMaadmrecData.getProDate()); // 補件日期
                    oldPayDataCase.setNdomk1(oldPayMaadmrecData.getNdomk1()); // 處理註記
                }

                oldPayCaseList.add(oldPayDataCase);
            }
            caseData.setOldPayList(oldPayCaseList);

            // 取得 申請失能年金給付記錄
            List<SurvivorReviewRpt01DisablePayDataCase> disablePayCaseList = new ArrayList<SurvivorReviewRpt01DisablePayDataCase>();
            List<Baappbase> disablePayDataList = baappbaseDao.selectSurvivorReviewRpt01DisablePayListBy(apNo, caseData.getEvtIdnNo(), caseData.getEvtBrDate());
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                disablePayDataList.addAll(baappbaseDao.selectSurvivorReviewRpt01DisablePayListBy(apNo, StringUtils.substring(kcaf.getBIdn(), 0, 10), kcaf.getBBrDte()));
            }
            for (Baappbase disablePayData : disablePayDataList) {
                SurvivorReviewRpt01DisablePayDataCase disablePayDataCase = new SurvivorReviewRpt01DisablePayDataCase();
                BeanUtility.copyProperties(disablePayDataCase, disablePayData);
                disablePayDataCase.setApNo(disablePayData.getApNo());

                Badapr dateData = badaprDao.selectSurvivorReviewRpt01DateDataBy(disablePayData.getApNo());
                if (dateData != null) {
                    disablePayDataCase.setChkDate(dateData.getChkDate()); // 核定日期
                    disablePayDataCase.setAplpayDate(dateData.getAplpayDate()); // 核付日期
                    disablePayDataCase.setRecAmt(dateData.getRecAmt()); // 收回金額
                    disablePayDataCase.setSupAmt(dateData.getSupAmt()); // 補發金額
                }
                Maadmrec disablePayMaadmrecData = maadmrecDao.selectSurvivorReviewRpt01AnnuityPayDataBy(disablePayData.getApNo(), disablePayData.getIssuYm());
                if (disablePayMaadmrecData != null) {
                    disablePayDataCase.setProdate(disablePayMaadmrecData.getProDate()); // 補件日期
                    disablePayDataCase.setNdomk1(disablePayMaadmrecData.getNdomk1()); // 處理註記
                }

                Baappexpand disablePayBaappexpand = baappexpandDao.getSurvivorReviewRpt01DisablePayList(disablePayData.getApNo());
                if (disablePayBaappexpand != null) {
                    disablePayDataCase.setEvTyp(disablePayBaappexpand.getEvTyp());// 傷病分類
                    disablePayDataCase.setCriInJdp1(disablePayBaappexpand.getCriInJdp1()); // 失能項目1
                    disablePayDataCase.setCriInJdp2(disablePayBaappexpand.getCriInJdp2()); // 失能項目2
                    disablePayDataCase.setCriInJdp3(disablePayBaappexpand.getCriInJdp3()); // 失能項目3
                    disablePayDataCase.setCriInJdp4(disablePayBaappexpand.getCriInJdp4()); // 失能項目4
                    disablePayDataCase.setCriInJdp5(disablePayBaappexpand.getCriInJdp5()); // 失能項目5
                    disablePayDataCase.setCriInJdp6(disablePayBaappexpand.getCriInJdp6()); // 失能項目6
                    disablePayDataCase.setCriInJdp7(disablePayBaappexpand.getCriInJdp7()); // 失能項目7
                    disablePayDataCase.setCriInJdp8(disablePayBaappexpand.getCriInJdp8()); // 失能項目8
                    disablePayDataCase.setCriInJdp9(disablePayBaappexpand.getCriInJdp9()); // 失能項目9
                    disablePayDataCase.setCriInJdp10(disablePayBaappexpand.getCriInJdp10()); // 失能項目10
                    disablePayDataCase.setCriInJcl1(disablePayBaappexpand.getCriInJcl1()); // 失能等級1
                    disablePayDataCase.setCriInJcl2(disablePayBaappexpand.getCriInJcl2()); // 失能等級2
                    disablePayDataCase.setCriInJcl3(disablePayBaappexpand.getCriInJcl3()); // 失能等級3

                }

                disablePayCaseList.add(disablePayDataCase);
            }

            caseData.setDisablePayList(disablePayCaseList);

            // 取得 現金給付參考檔 (PBBMSA) 申請老年給付記錄
            List<SurvivorReviewRpt01OncePayDataCase> oldAgePayCaseList = new ArrayList<SurvivorReviewRpt01OncePayDataCase>();
            List<Pbbmsa> oldAgePayDataList = pbbmsaDao.selectSurvivorReviewRpt01OldAgePayListBy(evtIdnNo, evtBrDate);
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                oldAgePayDataList.addAll(pbbmsaDao.selectSurvivorReviewRpt01OldAgePayListBy(StringUtils.substring(kcaf.getBIdn(), 0, 10), kcaf.getBBrDte()));
            }
            for (Pbbmsa oldAgePayData : oldAgePayDataList) {
                SurvivorReviewRpt01OncePayDataCase oldAgePayDataCase = new SurvivorReviewRpt01OncePayDataCase();
                BeanUtility.copyProperties(oldAgePayDataCase, oldAgePayData);
                oldAgePayCaseList.add(oldAgePayDataCase);
            }
            caseData.setOldAgePayList(oldAgePayCaseList);

            // 取得 現金給付參考檔 (PBBMSA) 申請失能給付記錄
            List<SurvivorReviewRpt01OncePayDataCase> disPayCaseList = new ArrayList<SurvivorReviewRpt01OncePayDataCase>();
            List<Pbbmsa> disPayDataList = pbbmsaDao.selectSurvivorReviewRpt01DisPayListBy(evtIdnNo, evtBrDate);
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                disPayDataList.addAll(pbbmsaDao.selectSurvivorReviewRpt01DisPayListBy(StringUtils.substring(kcaf.getBIdn(), 0, 10), kcaf.getBBrDte()));
            }
            for (Pbbmsa disPayData : disPayDataList) {
                SurvivorReviewRpt01OncePayDataCase disPayDataCase = new SurvivorReviewRpt01OncePayDataCase();
                BeanUtility.copyProperties(disPayDataCase, disPayData);
                disPayCaseList.add(disPayDataCase);
            }
            caseData.setDisPayList(disPayCaseList);

            // 取得 現金給付參考檔 (PBBMSA) 申請職災住院醫療給付記錄
            List<SurvivorReviewRpt01OncePayDataCase> hosPayCaseList = new ArrayList<SurvivorReviewRpt01OncePayDataCase>();
            List<Pbbmsa> hosPayDataList = pbbmsaDao.selectSurvivorReviewRpt01HosPayListBy(evtIdnNo, evtBrDate);
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                hosPayDataList.addAll(pbbmsaDao.selectSurvivorReviewRpt01HosPayListBy(StringUtils.substring(kcaf.getBIdn(), 0, 10), kcaf.getBBrDte()));
            }
            for (Pbbmsa hosPayData : hosPayDataList) {
                SurvivorReviewRpt01OncePayDataCase hosPayDataCase = new SurvivorReviewRpt01OncePayDataCase();
                BeanUtility.copyProperties(hosPayDataCase, hosPayData);
                hosPayCaseList.add(hosPayDataCase);
            }
            caseData.setHosPayList(hosPayCaseList);

            // 取得 現金給付參考檔 (PBBMSA) 申請傷病給付記錄資料
            List<SurvivorReviewRpt01InjuryPayDataCase> injurySurvivorPayCaseList = new ArrayList<SurvivorReviewRpt01InjuryPayDataCase>();
            List<Pbbmsa> injurySurvivorPayDataList = pbbmsaDao.selectSurvivorReviewRpt01InjuryPayListBy(caseData.getEvtIdnNo(), caseData.getEvtBrDate(), apNo);

            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                injurySurvivorPayDataList.addAll(pbbmsaDao.selectSurvivorReviewRpt01InjuryPayListBy(kcaf.getBIdn(), kcaf.getBBrDte(), apNo));
            }
            for (Pbbmsa injurySurvivorPayData : injurySurvivorPayDataList) {
                SurvivorReviewRpt01InjuryPayDataCase injurySurvivorPayDataCase = new SurvivorReviewRpt01InjuryPayDataCase();
                BeanUtility.copyProperties(injurySurvivorPayDataCase, injurySurvivorPayData);
                injurySurvivorPayCaseList.add(injurySurvivorPayDataCase);
            }
            caseData.setInjurySurvivorPayList(injurySurvivorPayCaseList);

            // 取得 就保給付檔 (BIREF) 申請失業給付記錄資料
            List<SurvivorReviewRpt01JoblessPayDataCase> joblessPayCaseList = new ArrayList<SurvivorReviewRpt01JoblessPayDataCase>();
            List<Biref> joblessPayDataList = birefDao.getDisableReviewRpt01JoblessPayListBy(caseData.getEvtIdnNo(), caseData.getEvtBrDate());
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                joblessPayDataList.addAll(birefDao.getDisableReviewRpt01JoblessPayListBy(StringUtils.substring(kcaf.getBIdn(), 0, 10), kcaf.getBBrDte()));
            }
            for (Biref joblessPayData : joblessPayDataList) {
                SurvivorReviewRpt01JoblessPayDataCase joblessPayDataCase = new SurvivorReviewRpt01JoblessPayDataCase();
                BeanUtility.copyProperties(joblessPayDataCase, joblessPayData);
                joblessPayCaseList.add(joblessPayDataCase);
            }
            caseData.setJoblessPayList(joblessPayCaseList);

            // 取得 現金給付參考檔 (PBBMSA) 申請失蹤給付記錄資料
            List<SurvivorReviewRpt01DiePayDataCase> disappearPayCaseList = new ArrayList<SurvivorReviewRpt01DiePayDataCase>();
            List<Pbbmsa> disappearPayDataList = pbbmsaDao.getSurvivorReviewRpt01DisappearPayListBy(caseData.getEvtIdnNo(), caseData.getEvtBrDate());
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                disappearPayDataList.addAll(pbbmsaDao.getSurvivorReviewRpt01DisappearPayListBy(StringUtils.substring(kcaf.getBIdn(), 0, 10), kcaf.getBBrDte()));
            }
            for (Pbbmsa disappearPayData : disappearPayDataList) {
                SurvivorReviewRpt01DiePayDataCase disappearPayDataCase = new SurvivorReviewRpt01DiePayDataCase();
                BeanUtility.copyProperties(disappearPayDataCase, disappearPayData);
                disappearPayCaseList.add(disappearPayDataCase);
            }
            caseData.setDisappearPayList(disappearPayCaseList);

            // 取得 現金給付參考檔 (PBBMSA) 申請農保死亡給付記錄資料
            List<SurvivorReviewRpt01OncePayDataCase> famDiePayCaseList = new ArrayList<SurvivorReviewRpt01OncePayDataCase>();
            List<Pbbmsa> famDiePayDataList = pbbmsaDao.getSurvivorReviewRpt01FamDiePayListBy(caseData.getEvtIdnNo(), caseData.getEvtBrDate());
            // 用關鍵欄位變更檔 去找資料
            for (Kcaf kcaf : evtKcafList) {
                disPayDataList.addAll(pbbmsaDao.getSurvivorReviewRpt01FamDiePayListBy(StringUtils.substring(kcaf.getBIdn(), 0, 10), kcaf.getBBrDte()));
            }
            for (Pbbmsa famDiePayData : famDiePayDataList) {
                SurvivorReviewRpt01OncePayDataCase famDiePayDataCase = new SurvivorReviewRpt01OncePayDataCase();
                BeanUtility.copyProperties(famDiePayDataCase, famDiePayData);
                famDiePayCaseList.add(famDiePayDataCase);
            }
            caseData.setFamDiePayList(famDiePayCaseList);

            // 取得 國保給付主檔 (NBAPPBASE) 申請國保給付記錄資料
            List<SurvivorReviewRpt01NpPayDataCase> npPayCaseList = new ArrayList<SurvivorReviewRpt01NpPayDataCase>();
            List<Nbappbase> npPayDataList = nbappbaseDao.selectSurvivorReviewRpt01NpPayListBy(caseData.getEvtIds());
            for (Nbappbase npPayData : npPayDataList) {
                SurvivorReviewRpt01NpPayDataCase npPayDataCase = new SurvivorReviewRpt01NpPayDataCase();
                BeanUtility.copyProperties(npPayDataCase, npPayData);

                // 取得 國保給付核定檔 (NBDAPR) 申請國保給付記錄資料
                Nbdapr npPayDetailData = nbdaprDao.selectSurvivorReviewRpt01NpPayDetailDataBy(npPayData.getApNo(), npPayData.getIssuYm(), npPayData.getPayYm());
                if (npPayDetailData != null) {
                    npPayDataCase.setChkDt(npPayDetailData.getChkDt()); // 核定日期
                    npPayDataCase.setAplPayDate(npPayDetailData.getAplPayDate()); // 帳務日期
                }

                npPayCaseList.add(npPayDataCase);
            }
            caseData.setNpPayList(npPayCaseList);

            // 取得 國保給付主檔 (NBAPPBASE) 申請國保遺屬給付記錄資料
            List<SurvivorReviewRpt01NpPayDataCase> npSurivorDidePayCaseList = new ArrayList<SurvivorReviewRpt01NpPayDataCase>();
            List<Nbappbase> npSurivorDidePayDataList = nbappbaseDao.selectSurvivorReviewRpt01NpDidePayListBy(caseData.getEvtIds(), "C");
            for (Nbappbase npSurivorDidePayData : npSurivorDidePayDataList) {
                SurvivorReviewRpt01NpPayDataCase npSurivorDidePayDataCase = new SurvivorReviewRpt01NpPayDataCase();
                BeanUtility.copyProperties(npSurivorDidePayDataCase, npSurivorDidePayData);

                // 取得 國保給付核定檔 (NBDAPR) 申請國保給付記錄資料
                Nbdapr npSurivorDidePayDetailData = nbdaprDao.selectSurvivorReviewRpt01NpPayDetailDataBy(npSurivorDidePayData.getApNo(), npSurivorDidePayData.getIssuYm(), npSurivorDidePayData.getPayYm());
                if (npSurivorDidePayDetailData != null) {
                    npSurivorDidePayDataCase.setChkDt(npSurivorDidePayDetailData.getChkDt()); // 核定日期
                    npSurivorDidePayDataCase.setAplPayDate(npSurivorDidePayDetailData.getAplPayDate()); // 帳務日期
                }

                npSurivorDidePayCaseList.add(npSurivorDidePayDataCase);
            }
            caseData.setNpSurivorDidePayList(npSurivorDidePayCaseList);

            // 取得 國保給付主檔 (NBAPPBASE) 申請國保遺屬給付記錄資料
            List<SurvivorReviewRpt01NpPayDataCase> npSurivorPayCaseList = new ArrayList<SurvivorReviewRpt01NpPayDataCase>();
            List<Nbappbase> npSurivorPayDataList = nbappbaseDao.selectSurvivorReviewRpt01NpDidePayListBy(caseData.getEvtIds(), "D");
            for (Nbappbase npSurivorPayData : npSurivorPayDataList) {
                SurvivorReviewRpt01NpPayDataCase npSurivorPayDataCase = new SurvivorReviewRpt01NpPayDataCase();
                BeanUtility.copyProperties(npSurivorPayDataCase, npSurivorPayData);

                // 取得 國保給付核定檔 (NBDAPR) 申請國保給付記錄資料
                Nbdapr npSurivorPayDetailData = nbdaprDao.selectSurvivorReviewRpt01NpPayDetailDataBy(npSurivorPayData.getApNo(), npSurivorPayData.getIssuYm(), npSurivorPayData.getPayYm());
                if (npSurivorPayDetailData != null) {
                    npSurivorPayDataCase.setChkDt(npSurivorPayDetailData.getChkDt()); // 核定日期
                    npSurivorPayDataCase.setAplPayDate(npSurivorPayDetailData.getAplPayDate()); // 帳務日期
                }

                npSurivorPayCaseList.add(npSurivorPayDataCase);
            }
            caseData.setNpSurivorPayList(npSurivorPayCaseList);

            // ]

            // 年金給付
            List<SurvivorReviewRpt01PayDeductDataCase> deductPayList = new ArrayList<SurvivorReviewRpt01PayDeductDataCase>();
            List<Baunacpdtl> baunacpdtlPayList = baunacpdtlDao.selectSurvivorReviewRpt01PayDeductListBy(evtData.getEvtIdnNo());
            // 用關鍵欄位變更檔 去找資料baunacpdtlDao
            for (Kcaf kcaf : evtKcafList) {
                baunacpdtlPayList.addAll(baunacpdtlDao.selectSurvivorReviewRpt01PayDeductListBy(kcaf.getBIdn()));
            }
            for (Baunacpdtl baunacpdtl : baunacpdtlPayList) {
                SurvivorReviewRpt01PayDeductDataCase dataCase = new SurvivorReviewRpt01PayDeductDataCase();
                String benName = baappbaseDao.selectDisablePayDeductBenname(baunacpdtl.getApNo(), baunacpdtl.getSeqNo());
                BeanUtility.copyProperties(dataCase, baunacpdtl);
                dataCase.setBenName(benName);
                deductPayList.add(dataCase);
            }
            caseData.setDeductPayList(deductPayList);
            // ]

            // 取得 給付核定檔 (BADAPR) 事故者於受款人給付資料的資料
            List<SurvivorReviewRpt01BenPayDataCase> evtBenPayCaseList = new ArrayList<SurvivorReviewRpt01BenPayDataCase>();
            List<Badapr> evtBenPayDataList = badaprDao.getSurvivorReviewRpt01BenPayListBy(apNo, evtData.getSeqNo(), evtData.getIssuYm());
            for (Badapr evtBenPayData : evtBenPayDataList) {
                SurvivorReviewRpt01BenPayDataCase evtBenPayCase = new SurvivorReviewRpt01BenPayDataCase();
                BeanUtility.copyProperties(evtBenPayCase, evtBenPayData);
                evtBenPayCaseList.add(evtBenPayCase);
            }
            caseData.setEvtBenPayList(evtBenPayCaseList);

            // 取得 被保險人異動資料檔 (CIPT) 承保異動資料
            // [
            // List<OldAgeReviewRpt01ChangeDataCase> changeDataCaseList = new ArrayList<OldAgeReviewRpt01ChangeDataCase>();
            // List<Cipt> changeDataList = ciptDao.getOldAgeReviewRpt01ChangeListBy(evtIdnNo);
            // for (Cipt changeData : changeDataList) {
            // OldAgeReviewRpt01ChangeDataCase changeDataCase = new OldAgeReviewRpt01ChangeDataCase();
            // BeanUtility.copyProperties(changeDataCase, changeData);
            // changeDataCaseList.add(changeDataCase);
            // }
            caseData.setChangeList(BaCiptUtility.getCiptUtilityCase(apNo, evtIdnNo));
            // ]

            // 取得 眷屬檔 (<code>BAFAMILY</code>) 眷屬資料
            // [
            // 取得事故者眷屬資料

            List<SurvivorReviewRpt01FamilyDataCase> familyList = new ArrayList<SurvivorReviewRpt01FamilyDataCase>();
            List<Bafamily> bafamilyList = bafamilyDao.selectReviewRpt01FamilyListBy(apNo, evtData.getBaappbaseId());

            for (Bafamily familyData : bafamilyList) {
                SurvivorReviewRpt01FamilyDataCase familyDataCase = new SurvivorReviewRpt01FamilyDataCase();
                BeanUtility.copyProperties(familyDataCase, familyData);

                // 取得 眷屬編審註記資料
                List<SurvivorReviewRpt01ChkfileDataCase> familyChkfileDataCaseList = new ArrayList<SurvivorReviewRpt01ChkfileDataCase>();
                List<Bachkfile> bachkfileDataList = bachkfileDao.getOldAgeReviewRpt01BenChkfileDataBy(apNo, familyData.getSeqNo(), evtData.getIssuYm());
                for (Bachkfile benChkfileData : bachkfileDataList) {
                    SurvivorReviewRpt01ChkfileDataCase familyChkfileDataCase = new SurvivorReviewRpt01ChkfileDataCase();
                    BeanUtility.copyProperties(familyChkfileDataCase, benChkfileData);
                    familyChkfileDataCaseList.add(familyChkfileDataCase);
                }
                familyDataCase.setChkfileDataList(familyChkfileDataCaseList);
                familyList.add(familyDataCase);
            }
            caseData.setFamilyList(familyList);
            // ]

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 勞工紓困貸款繳納本息情形查詢單 的資料
     * 
     * @param apNo 受理編號
     * @return
     */
    public DataUpdateRpt01Case getDataUpdateRpt01DataBy(String apNo) {
        DataUpdateRpt01Case caseData = null;

        // 取得 給付主檔 (BAAPPBASE) - 事故者資料

        Baappbase baappbase = baappbaseDao.selectDataUpdateRpt01DataBy(apNo);

        if (baappbase != null) {
            caseData = new DataUpdateRpt01Case();
            BeanUtility.copyProperties(caseData, baappbase);

            if (StringUtils.isNotBlank(baappbase.getEvtIdnNo()) && StringUtils.isNotBlank(baappbase.getEvtBrDate())) {
                // 取得 貸款主檔 (LNM) 的資料 (此檔的日期格式皆為民國日期)
                // 在 DAO 中會將傳入的西元日期轉為民國日期
                Lnm lnm = lnmDao.getDataUpdateRpt01DataBy(baappbase.getEvtIdnNo(), baappbase.getEvtBrDate());

                if (lnm != null) {
                    BeanUtility.copyProperties(caseData, lnm);

                    // 取得 月核定日期參數檔 (BAPAISSUDATE) 的資料

                    Bapaissudate bapaissudate = bapaissudateDao.getDataUpdateRpt01DataBy(baappbase.getIssuYm());
                    caseData.setIssuDate(bapaissudate.getIssuDate()); // 核定日期

                    return caseData;
                }
                else {
                    return null;
                }
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }

    /**
     * 依傳入的條件取得 紓困貸款撥款情形查詢清單 的資料
     * 
     * @param apNo 受理編號
     * @return
     */
    public DataUpdateRpt02Case getDataUpdateRpt02DataBy(String apNo) {
        DataUpdateRpt02Case caseData = null;

        // 取得 給付主檔 (BAAPPBASE) - 事故者資料

        Baappbase baappbase = baappbaseDao.selectDataUpdateRpt02DataBy(apNo);

        if (baappbase != null) {
            caseData = new DataUpdateRpt02Case();
            BeanUtility.copyProperties(caseData, baappbase);

            if (StringUtils.isNotBlank(baappbase.getEvtIdnNo()) && StringUtils.isNotBlank(baappbase.getEvtBrDate())) {
                // 取得 貸款主檔 (LNM) 的資料 (此檔的日期格式皆為民國日期)
                // 在 DAO 中會將傳入的西元日期轉為民國日期
                Lnm lnm = lnmDao.getDataUpdateRpt02DataBy(baappbase.getEvtIdnNo(), baappbase.getEvtBrDate());

                if (lnm != null) {
                    BeanUtility.copyProperties(caseData, lnm);
                    return caseData;
                }
                else {
                    return null;
                }
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }

    /**
     * 依傳入的條件取得 紓困貸款抵銷情形照會單 的資料
     * 
     * @param apNo 受理編號
     * @return
     */
    public DataUpdateRpt03Case getDataUpdateRpt03DataBy(String apNo) {
        DataUpdateRpt03Case caseData = null;

        // 取得 給付主檔 (BAAPPBASE) - 事故者資料

        Baappbase baappbase = baappbaseDao.selectDataUpdateRpt03DataBy(apNo);

        if (baappbase != null) {
            caseData = new DataUpdateRpt03Case();
            BeanUtility.copyProperties(caseData, baappbase);

            if (StringUtils.isNotBlank(baappbase.getEvtIdnNo()) && StringUtils.isNotBlank(baappbase.getEvtBrDate())) {
                // 取得 現金給付參考檔 (PBBMSA)
                List<Pbbmsa> pbbmsaList = pbbmsaDao.getDataUpdateRpt03DataBy(baappbase.getEvtIdnNo(), baappbase.getEvtBrDate());

                List<DataUpdateRpt03DetailCase> detailList = new ArrayList<DataUpdateRpt03DetailCase>();

                for (Pbbmsa pbbmsaData : pbbmsaList) {
                    DataUpdateRpt03DetailCase detailCase = new DataUpdateRpt03DetailCase();
                    BeanUtility.copyProperties(detailCase, pbbmsaData);

                    detailList.add(detailCase);
                }

                caseData.setDetailList(detailList);

                return caseData;
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }

    /**
     * 依傳入的條件取得 歸檔清單 的資料
     * 
     * @param payCode 給付別
     * @param decisionSdate 決行日期起日
     * @param decisionEdate 決行日期迄日
     * @return
     */
    public List<DecisionRpt01Case> getDecisionRpt01DataBy(String payCode, String decisionSdate, String decisionEdate, String exeMan) {
        List<DecisionRpt01Case> caseList = new ArrayList<DecisionRpt01Case>();

        // 決行日期轉西元

        if (StringUtils.length(decisionSdate) == 7)
            decisionSdate = DateUtility.changeDateType(decisionSdate);
        if (StringUtils.length(decisionEdate) == 7)
            decisionEdate = DateUtility.changeDateType(decisionEdate);

        List<Baappbase> dataList = null;
        if (payCode.equals("L") || payCode.equals("K")) {
            dataList = baappbaseDao.selectDecisionRpt01ListBy(payCode, decisionSdate, decisionEdate, exeMan);
        }
        else {
            dataList = baappbaseDao.selectDecisionRpt01ListForSBy(payCode, decisionSdate, decisionEdate, exeMan);
        }

        for (Baappbase baappbase : dataList) {
            DecisionRpt01Case caseData = new DecisionRpt01Case();
            caseData.setCaseTyp(baappbase.getCaseTyp());
            caseData.setApNo(baappbase.getApNo());
            caseData.setEvtName(baappbase.getEvtName());
            caseData.setIssuYm(baappbase.getIssuYm());
            caseData.setChkMan(baappbase.getChkMan());
            caseData.setExeMan(baappbase.getExeMan());
            caseData.setNote(baappbase.getNote());
            caseData.setExeDate(baappbase.getExeDate());
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 取得當日已歸檔的最大頁次及序號 for 列印歸檔清單
     * 
     * @param payCode
     * @return
     */
    public Baarclist selectMaxBaarclistNumAndArcPg(String payCode) {
        Baarclist baarclist = baarclistDao.selectMaxBaarclistNumAndArcPg(DateUtility.getNowWestDate(), payCode);
        if (baarclist == null) {
            baarclist = new Baarclist();
        }
        if (baarclist.getBaarclistNum() == null) {
            baarclist.setBaarclistNum(BigDecimal.ZERO);
        }
        if (baarclist.getArcPg() == null) {
            baarclist.setArcPg(BigDecimal.ZERO);
        }
        return baarclist;
    }

    /**
     * 取得當日已歸檔的最大頁次及序號 for 列印歸檔清單 (payCode = S and CaseTyp = 3)
     * 
     * @param payCode
     * @return
     */
    public Baarclist selectMaxBaarclistNumAndArcPgForS(String payCode) {
        Baarclist baarclist = baarclistDao.selectMaxBaarclistNumAndArcPgForS(DateUtility.getNowWestDate(), payCode);
        if (baarclist == null) {
            baarclist = new Baarclist();
        }
        if (baarclist.getBaarclistNum() == null) {
            baarclist.setBaarclistNum(BigDecimal.ZERO);
        }
        if (baarclist.getArcPg() == null) {
            baarclist.setArcPg(BigDecimal.ZERO);
        }
        return baarclist;
    }

    /**
     * 更新BAARCLIST,BAAPPBASE資料
     * 
     * @param caseList
     */
    public void upDateDateForDecisionRpt01(String payCode, String decisionSdate, String decisionEdate, List<DecisionRpt01Case> caseList, BigDecimal baarclistNum) {

        // 決行日期轉西元

        if (StringUtils.length(decisionSdate) == 7)
            decisionSdate = DateUtility.changeDateType(decisionSdate);
        if (StringUtils.length(decisionEdate) == 7)
            decisionEdate = DateUtility.changeDateType(decisionEdate);

        String nowDate = DateUtility.getNowWestDate();

        List<Baarclist> baarclistList = new ArrayList<Baarclist>();
        List<Baappbase> baappbaseList = new ArrayList<Baappbase>();

        for (DecisionRpt01Case dataCase : caseList) {
            // 新增案件歸檔紀錄檔_ BAARCLIST
            Baarclist baarclist = new Baarclist();
            baarclistNum = baarclistNum.add(BigDecimal.ONE);
            baarclist.setBaarclistNum(baarclistNum);
            baarclist.setPayCode(payCode);
            baarclist.setCaseTyp(dataCase.getCaseTyp());
            baarclist.setIssuYm(dataCase.getIssuYm());
            baarclist.setExeBdate(decisionSdate);
            baarclist.setExeEdate(decisionEdate);
            baarclist.setArcPg(dataCase.getArcPg());
            baarclist.setArcDate(nowDate);
            baarclist.setApNo(dataCase.getApNo());
            baarclist.setEvtName(dataCase.getEvtName());
            baarclist.setChkMan(StringUtils.defaultString(dataCase.getChkMan()));
            baarclist.setExeMan(StringUtils.defaultString(dataCase.getExeMan()));
            baarclist.setDelMl("");
            baarclist.setNote(dataCase.getNote());
            baarclist.setExeDate(dataCase.getExeDate());
            baarclistList.add(baarclist);

            // 更新主檔ARCDATE ARCPG欄位
            Baappbase baappbase = new Baappbase();
            baappbase.setApNo(dataCase.getApNo());
            baappbase.setArcDate(nowDate);
            baappbase.setArcPg(dataCase.getArcPg().toString());
            baappbaseList.add(baappbase);
        }

        // 新增案件歸檔紀錄檔_ BAARCLIST
        baarclistDao.insertData(baarclistList);

        // 更新主檔欄位
        baappbaseDao.updateDataForDecisionRpt01(baappbaseList);
    }

    /**
     * 依傳入的條件取得 歸檔清單點交清冊 的資料
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<DecisionRpt02Case> getDecisionRpt02DataBy(String payCode, String arcDate, BigDecimal arcPgBegin, BigDecimal arcPgEnd, String exeMan) {
        List<DecisionRpt02Case> caseList = new ArrayList<DecisionRpt02Case>();

        // 核定年月民國轉西元

        if (StringUtils.length(arcDate) == 7)
            arcDate = DateUtility.changeDateType(arcDate);

        List<Baarclist> dataList = baarclistDao.selectDataForDecisionRpt02(payCode, arcDate, arcPgBegin, arcPgEnd, exeMan);

        for (Baarclist baarclist : dataList) {
            DecisionRpt02Case caseData = new DecisionRpt02Case();
            BeanUtility.copyProperties(caseData, baarclist);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 歸檔清單點交清冊 的資料
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<DecisionRpt03Case> selectDataForDecisionRpt03(String payCode, String arcDate) {
        List<DecisionRpt03Case> caseList = new ArrayList<DecisionRpt03Case>();

        // 核定年月民國轉西元
        String arcDateQuery = "";

        if (StringUtils.length(arcDate) == 7)
            arcDateQuery = DateUtility.changeDateType(arcDate);

        List<Baarclist> dataList = baarclistDao.selectDataForDecisionRpt03(payCode, arcDateQuery);

        for (Baarclist baarclist : dataList) {
            DecisionRpt03Case caseData = new DecisionRpt03Case();
            BeanUtility.copyProperties(caseData, baarclist);
            caseData.setArcDate(arcDate);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 逾期未決行案件管制表 的資料
     * 
     * @param payCode 給付別
     * @param endYm 截止年月
     * @return
     */
    public List<AuditRpt01Case> selectAuditRpt01DataBy(String payCode, String endYm) {
        List<AuditRpt01Case> caseList = new ArrayList<AuditRpt01Case>();

        // 核定年月民國轉西元
        String endYmQuery = "";

        if (StringUtils.length(endYm) == 5)
            endYmQuery = DateUtility.changeChineseYearMonthType(endYm);

        List<Baappbase> dataList = baappbaseDao.selectAuditRpt01DataBy(payCode, endYmQuery);

        if (dataList.size() > 0) {

            for (Baappbase baappbase : dataList) {
                AuditRpt01Case caseData = new AuditRpt01Case();
                BeanUtility.copyProperties(caseData, baappbase);
                // 轉換 行政支援註記為中文
                String lpaymk = "";
                String kpaymk = "";
                String spaymk = "";
                lpaymk = (StringUtils.equalsIgnoreCase(baappbase.getApNo().substring(0, 1), "L") ? "1" : "");
                kpaymk = (StringUtils.equalsIgnoreCase(baappbase.getApNo().substring(0, 1), "K") ? "1" : "");
                spaymk = (StringUtils.equalsIgnoreCase(baappbase.getApNo().substring(0, 1), "S") ? "1" : "");

                String ndomk1Str = "";

                if (StringUtils.isNotBlank(baappbase.getLeterType())) {
                    ndomk1Str = bapandomkDao.selectDataForAuditRpt01By(baappbase.getLeterType(), baappbase.getNdomk1(), lpaymk, kpaymk, spaymk);
                }

                caseData.setNdomk1Str(ndomk1Str);

                // 轉換 行政支援函別為中文
                String paramName = "";

                if (StringUtils.isNotBlank(baappbase.getLeterType())) {
                    paramName = baparamDao.selectParamNameBy(ConstantKey.BAPARAM_PARAMTYP_SHARE, ConstantKey.BAPARAM_PARAMGROUP_LETTERTYPE, baappbase.getLeterType());
                }

                caseData.setLeterTypeStr(paramName);

                caseList.add(caseData);
            }

            return caseList;

        }
        else {
            return caseList;
        }
    }

    /**
     * 依傳入的條件取得 逾期未決行案件管制表 的資料 For Excel
     * 
     * @param payCode 給付別
     * @param endYm 截止年月
     * @return
     */
    public List<AuditRpt01Case> selectAuditRpt01ExcelDataBy(String payCode, String endYm) {
        List<AuditRpt01Case> caseList = new ArrayList<AuditRpt01Case>();

        // 核定年月民國轉西元
        String endYmQuery = "";

        if (StringUtils.length(endYm) == 5)
            endYmQuery = DateUtility.changeChineseYearMonthType(endYm);

        List<Baappbase> dataList = baappbaseDao.selectAuditRpt01ExcelDataBy(payCode, endYmQuery);

        if (dataList.size() > 0) {

            for (Baappbase baappbase : dataList) {
                AuditRpt01Case caseData = new AuditRpt01Case();
                BeanUtility.copyProperties(caseData, baappbase);

                // 轉換 行政支援註記為中文
                String lpaymk = "";
                String kpaymk = "";
                String spaymk = "";
                lpaymk = (StringUtils.equalsIgnoreCase(baappbase.getApNo().substring(0, 1), "L") ? "1" : "");
                kpaymk = (StringUtils.equalsIgnoreCase(baappbase.getApNo().substring(0, 1), "K") ? "1" : "");
                spaymk = (StringUtils.equalsIgnoreCase(baappbase.getApNo().substring(0, 1), "S") ? "1" : "");

                String ndomk1Str = "";

                if (StringUtils.isNotBlank(baappbase.getLeterType())) {
                    ndomk1Str = bapandomkDao.selectDataForAuditRpt01By(baappbase.getLeterType(), baappbase.getNdomk1(), lpaymk, kpaymk, spaymk);
                }

                caseData.setNdomk1Str(ndomk1Str);

                // 轉換 行政支援函別為中文
                String paramName = "";

                if (StringUtils.isNotBlank(baappbase.getLeterType())) {
                    paramName = baparamDao.selectParamNameBy(ConstantKey.BAPARAM_PARAMTYP_SHARE, ConstantKey.BAPARAM_PARAMGROUP_LETTERTYPE, baappbase.getLeterType());
                }

                caseData.setLeterTypeStr(paramName);

                // 本次編審註記列表
                String chkList = "";
                chkList = badaprDao.selectChkListForAuditRpt01(baappbase.getApNo(), baappbase.getIssuYm());
                caseData.setChkList(chkList);

                // 前次編審註記列表
                String preChkList = "";
                preChkList = badaprDao.selectPreChkListForAuditRpt01(baappbase.getApNo(), baappbase.getIssuYm());
                caseData.setPreChkList(preChkList);

                caseList.add(caseData);
            }

            return caseList;

        }
        else {
            return caseList;
        }
    }

    /**
     * 依傳入的條件取得 月核定差異統計表 的資料
     * 
     * @param payCode 給付別
     * @param issuYmBegin 核定年月 (起)
     * @param issuYmEnd 核定年月 (迄)
     * @return
     */
    public MonthlyRpt03Case getMonthlyRpt03DataBy(String payCode, String issuYm) {
        MonthlyRpt03Case caseData = new MonthlyRpt03Case();

        // 取得 給付別中文名稱

        String payCodeName = baparamDao.selectParamNameBy(ConstantKey.BAPARAM_PARAMTYP_PRIVATE, ConstantKey.BAPARAM_PARAMGROUP_PAYCODE, payCode);

        // 設定 給付別中文名稱

        caseData.setPayCodeName(payCodeName);

        String sIssuYm = issuYm;

        // 民國年月轉成西元年月
        if (StringUtils.length(sIssuYm) == 5)
            sIssuYm = DateUtility.changeChineseYearMonthType(sIssuYm);

        // 取得前一年 12 月的年月
        String sLastIssuYm = StringUtils.leftPad(String.valueOf((Integer.parseInt(StringUtils.substring(sIssuYm, 0, 4)) - 1)), 4, "0") + "12";

        caseData.setIssuYmBegin(sLastIssuYm);
        caseData.setIssuYmEnd(sIssuYm);

        // 取得 月核定差異統計檔 (BALP0D330) 的資料

        List<Balp0d330> balp0d330List = balp0d330Dao.getMonthlyRpt03ListBy(payCode, sLastIssuYm, sIssuYm);

        List<MonthlyRpt03DetailCase> detailCaseList = new ArrayList<MonthlyRpt03DetailCase>();
        for (Balp0d330 balp0d330 : balp0d330List) {
            MonthlyRpt03DetailCase detailCaseData = new MonthlyRpt03DetailCase();
            BeanUtility.copyProperties(detailCaseData, balp0d330);

            detailCaseList.add(detailCaseData);
        }

        if (detailCaseList.size() == 0)
            return null;
        else
            caseData.setDetailList(detailCaseList);

        return caseData;
    }

    /**
     * 依傳入的條件取得 調卷/還卷單 的資料
     * 
     * @param apNo 受理編號
     * @return
     */
    public OtherRpt01Case getOtherRpt01DataBy(String apNo) {
        OtherRpt01Case caseData = null;

        // 取得 給付主檔 (BAAPPBASE) - 事故者資料

        Baappbase baappbase = baappbaseDao.selectOtherRpt01DataBy(apNo);

        if (baappbase != null) {
            caseData = new OtherRpt01Case();
            BeanUtility.copyProperties(caseData, baappbase);
        }

        return caseData;
    }

    /**
     * 依傳入的條件取得 勞保老年年金給付審核給付清單 的資料
     * 
     * @param payCode 給付別
     * @param chkMan 審核人員
     * @param chkDate 審核日期
     * @return
     */
    public List<OldAgeReviewRpt02Case> getOldAgeReviewRpt02DataBy(String prtTyp, String payCode, String chkMan, String rptDate, BigDecimal pageNo, String chkDate) {
        List<OldAgeReviewRpt02Case> caseList = new ArrayList<OldAgeReviewRpt02Case>();
        // 打包列印
        if (("1").equals(prtTyp)) {
            // update PROCMK
            balp0d020Dao.updateProcmkForOldAgeReviewRpt02();

            String nowDate = DateUtility.getNowWestDate();// 系統日期
            BigDecimal spageNo = null;// 起始頁次
            BigDecimal epageNo = null;// 結束頁次
            BigDecimal existPageNo = balp0d020Dao.selectMaxPageNoBy(payCode, chkMan, nowDate);
            List<Balp0d020> nonPackDataList = balp0d020Dao.selectNonPackDataBy(payCode, chkMan, chkDate);

            // 建立資料Map
            // Map的Key 為 mexcLvl
            // Value則是同為該 mexcLvl 下的所有編審註記資料

            Map<String, List<Balp0d020>> map = new TreeMap<String, List<Balp0d020>>();
            for (int i = 0; i < nonPackDataList.size(); i++) {
                Balp0d020 obj = (Balp0d020) nonPackDataList.get(i);
                if (map.get(obj.getMexcLvl()) == null) {
                    List<Balp0d020> list = new ArrayList<Balp0d020>();
                    list.add(obj);
                    map.put(obj.getMexcLvl(), list);
                }
                else {
                    (map.get(obj.getMexcLvl())).add(obj);
                }
            }
            // 處理 map裡分類好的資料(計算頁次) 轉成update用的List
            List<Balp0d020> updateList = new ArrayList<Balp0d020>();
            for (Iterator it = map.keySet().iterator(); it.hasNext();) {
                String key = (String) it.next();
                List<Balp0d020> tempList = map.get(key);
                BigDecimal pageSize = new BigDecimal(20);// 一頁20筆

                for (int i = 0; i < tempList.size(); i++) {
                    Balp0d020 obj = tempList.get(i);
                    BigDecimal newPageNo = existPageNo.add(new BigDecimal((i / 20) + 1));

                    // 設定起始頁次 & 結束頁次;
                    // 起始頁次僅還是null的時候要設

                    // 結束頁次則是每一次都要設, 確保到最後一筆的時候頁次正確

                    // [
                    if (spageNo == null) {
                        spageNo = newPageNo;
                    }
                    epageNo = newPageNo;
                    // ]

                    obj.setPageNo(newPageNo);
                    obj.setRptDate(nowDate);
                    updateList.add(obj);
                }
                existPageNo = existPageNo.add(new BigDecimal((tempList.size() / 20) + 1));
            }

            // 將資料更新回BALP0D020
            balp0d020Dao.updateDataForOldAgeReviewRpt02(updateList);

            // 取得列印資料
            List<Balp0d020> rptDataList = balp0d020Dao.selectOldAgeReviewRpt02DataBy(payCode, chkMan, nowDate, prtTyp, spageNo, epageNo, null);
            for (int i = 0; i < rptDataList.size(); i++) {
                Balp0d020 balp0d020 = rptDataList.get(i);
                OldAgeReviewRpt02Case caseData = new OldAgeReviewRpt02Case();
                BeanUtility.copyProperties(caseData, balp0d020);
                caseList.add(caseData);
            }
        }
        // 重新列印
        else if (("2").equals(prtTyp)) {
            List<Balp0d020> rptDataList = balp0d020Dao.selectOldAgeReviewRpt02DataBy(payCode, chkMan, rptDate, prtTyp, null, null, pageNo);
            for (int i = 0; i < rptDataList.size(); i++) {
                Balp0d020 balp0d020 = rptDataList.get(i);
                OldAgeReviewRpt02Case caseData = new OldAgeReviewRpt02Case();
                BeanUtility.copyProperties(caseData, balp0d020);
                caseList.add(caseData);
            }
        }
        return caseList;
    }

    /**
     * 依傳入的條件取得 另案扣減照會單 的資料
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<OldAgeReviewRpt04Case> getOldAgeReviewRpt04DataBy(String apNo) {
        List<Barxf> barxfList = barxfDao.selectRxfApNoByApNo(apNo);
        if (barxfList != null && barxfList.size() > 0) {
            List<OldAgeReviewRpt04Case> caseList = new ArrayList<OldAgeReviewRpt04Case>();

            for (Barxf barxf : barxfList) {
                Bbarf bbarf = bbarfDao.selectDataBy(barxf.getRxfApNo());
                Baappbase data = baappbaseDao.selectOldAgeReviewRpt04DataBy(barxf.getApNo(), barxf.getSeqNo());
                OldAgeReviewRpt04Case caseData = new OldAgeReviewRpt04Case();
                // 待收回[
                List<Bapapaykind> bapapaykindList = null;
                if (bbarf != null) {
                    caseData.setApNo(bbarf.getApNo());
                    caseData.setIssueAmt(bbarf.getChkAmt());
                    caseData.setRecAmt(new BigDecimal(bbarf.getAmtTot().intValue() - bbarf.getAmtInc().intValue()));
                    caseData.setSeqNo(String.valueOf(bbarf.getGvSum().intValue()));
                    caseData.setEvtName(bbarf.getGvName());
                    caseData.setEvtIdnNo(bbarf.getGvIdno());
                    bapapaykindList = bapapaykindDao.selectDataBy(StringUtils.substring(bbarf.getApNo(), 4, 6));
                }

                if (bapapaykindList != null && bapapaykindList.size() > 0) {
                    caseData.setPayItem(bapapaykindList.get(0).getPayItem());
                    caseData.setDeptName(bapapaykindList.get(0).getDepartment());
                }
                // ]
                // 待撥付[
                if (data != null) {
                    caseData.setGiveApNo(data.getApNo());
                    caseData.setGiveIssuYm(data.getIssuYm());
                    caseData.setGiveAppDate(data.getAppDate());
                    caseData.setGiveSeqNo(data.getSeqNo());
                    caseData.setGiveEvtIdnNo(data.getBenIdnNo());
                    caseData.setGiveEvtName(data.getBenName());
                    caseData.setGiveEvtBrDate(data.getBenBrDate());
                }
                // ]
                caseList.add(caseData);
                
            }

            if (caseList != null && caseList.size() > 0) {
                return caseList;
            }
            else {
                return null;
            }
        }
        return null;
    }

    /**
     * 依傳入的條件取得 紓困貸款呆帳債務人照會單 的資料
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<DataUpdateRpt04Case> getDataUpdateRpt04DataBy(String apNo) {
        List<Baappbase> baappbaseList = baappbaseDao.selectDataUpdateRpt04DataBy(apNo);
        List<DataUpdateRpt04Case> caseList = new ArrayList<DataUpdateRpt04Case>();

        for (Baappbase baappbase : baappbaseList) {
            Lnmd lnmddata = lnmdDao.getDataUpdateRpt04DataBy(baappbase.getBenIdnNo(), baappbase.getBenBrDate());

            if (lnmddata != null) {
                DataUpdateRpt04Case caseData = new DataUpdateRpt04Case();
                BeanUtility.copyProperties(caseData, baappbase);
                BeanUtility.copyProperties(caseData, lnmddata);
                caseList.add(caseData);
            }
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 止付單 的資料
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<DataUpdateRpt05Case> getDataUpdateRpt05DataBy(String apNo) {
        // 查詢主檔資料
        List<Baappbase> baappbaseList = baappbaseDao.selectDataUpdateRpt05ForBaappbase(apNo, apNo.substring(0, 1));

        List<DataUpdateRpt05Case> dataUpdateRpt05CaseList = new ArrayList<DataUpdateRpt05Case>();

        for (int i = 0; i < baappbaseList.size(); i++) {
            Baappbase baappbase = baappbaseList.get(i);

            // 查詢給付檔資料

            List<Badapr> badaprList = badaprDao.selectDataUpdateRpt05ForBadapr(baappbase.getApNo(), baappbase.getSeqNo(), baappbase.getIssuYm());

            // 只選給付檔有資料的

            if (badaprList.size() != 0) {

                for (Badapr badaprData : badaprList) {

                    // 主檔資料為計算人數
                    DataUpdateRpt05Case caseData = new DataUpdateRpt05Case();
                    BeanUtility.copyProperties(caseData, baappbase);
                    caseData.setPayYm(badaprData.getPayYm());
                    // 人數
                    int peopleCount = 0;
                    if (apNo.substring(0, 1).equals("S")) {
                        peopleCount = badaprDao.getRpt05PeopleCountForSBy(baappbase.getApNo(), baappbase.getIssuYm());
                    }
                    else {
                        peopleCount = badaprDao.getRpt05PeopleCountBy(baappbase.getApNo(), baappbase.getIssuYm());
                    }

                    if ((baappbase.getApNo().substring(0, 1).equals("K") || baappbase.getApNo().substring(0, 1).equals("S")) && baappbase.getSeqNo().equals("0000") && badaprData.getOffsetAmt().compareTo(new BigDecimal(0)) == 1) {
                        caseData.setPeopleCount(Integer.toString(peopleCount + 1));
                    }
                    else {
                        caseData.setPeopleCount(Integer.toString(peopleCount));
                    }
                    // 給付檔資料實付金額需加總
                    String sumAplpayAmt = badaprDao.getDataUpdateRpt05ForAplpayAmt(baappbase.getApNo(), baappbase.getIssuYm());

                    // 若是受益人且關係為'F'時，讀取紓困主檔的受款人資料

                    if (baappbase.getBenEvtRel().equals("F")) {
                        Lnm lnm = lnmDao.getDataUpdateRpt01DataBy(baappbase.getEvtIdnNo(), baappbase.getEvtBrDate());

                        if (lnm != null) {
                            caseData.setIdn_Aply(lnm.getIdnAply());
                            caseData.setName_Aply(lnm.getNameAply());
                            caseData.setAccount_Pay(lnm.getAccountPay());
                        }
                    }

                    // K,S案件是否比照老年年金受款人關係F的格式產生止付單
                    caseData.setfStyleForKS("");

                    // 以下資料應該都一樣所以只抓第一筆，只有實付金額需加總
                    // caseData.setStexpndReason(badaprList.get(0).getStexpndReason());
                    // caseData.setStexpndDate(badaprList.get(0).getStexpndDate());
                    caseData.setStexpndReason(badaprData.getStexpndReason());
                    caseData.setStexpndDate(badaprData.getStexpndDate());
                    if (sumAplpayAmt != null)
                        caseData.setSumAplpayAmt(new BigDecimal(sumAplpayAmt));
                    else
                        caseData.setSumAplpayAmt(new BigDecimal(0));

                    if (badaprData.getAplpayAmt() != null)
                        caseData.setAplpayAmt(badaprData.getAplpayAmt());
                    else
                        caseData.setAplpayAmt(new BigDecimal(0));

                    if (apNo.substring(0, 1).equals("S") && !badaprData.getSeqNo().equals("0000")) {
                        // 找出有共同具領的 SEQNO (ACCREL = '3')
                        List<Baappbase> seqNoList = baappbaseDao.selectSeqNoListForDataUpdateRpt05(apNo, badaprData.getSeqNo());

                        if (seqNoList != null && seqNoList.size() > 0) {
                            ArrayList<String> list = new ArrayList<String>();

                            for (int j = 0; j < seqNoList.size(); j++) {
                                Baappbase listForSeqNo = seqNoList.get(j);
                                list.add(listForSeqNo.getSeqNo());
                            }

                            BigDecimal sumAplpayAmtForAccRel3 = badaprDao.getSumAplpayAmtForAccRel3(baappbase.getApNo(), list, badaprData.getIssuYm(), badaprData.getPayYm());
                            Integer iAplpayAmt = caseData.getAplpayAmt().intValue() + sumAplpayAmtForAccRel3.intValue();
                            caseData.setAplpayAmt(new BigDecimal(iAplpayAmt));
                        }
                    }

                    caseData.setAplpayDate(badaprData.getAplpayDate());

                    // AplpayAmt 大於0 才印
                    if (caseData.getAplpayAmt().compareTo(new BigDecimal(0)) == 1 && caseData.getAplpayAmt() != null) {
                        dataUpdateRpt05CaseList.add(caseData);
                    }
                }

                for (Badapr badaprData : badaprList) {

                    // 主檔資料為計算人數
                    DataUpdateRpt05Case caseData = new DataUpdateRpt05Case();
                    BeanUtility.copyProperties(caseData, baappbase);
                    // 若案件為失能或遺屬，受理編號第一碼為K or S，且SEQNO 欄位值為0000，selectDataUpdateRpt05ForBadapr中，欄位OFFSETAMT(本次抵銷金額)欄位值大於0，比照老年年金受款人關係F的格式產生止付單
                    if ((baappbase.getApNo().substring(0, 1).equals("K") || baappbase.getApNo().substring(0, 1).equals("S")) && baappbase.getSeqNo().equals("0000") && badaprData.getOffsetAmt().compareTo(new BigDecimal(0)) == 1) {

                        caseData.setPayYm(badaprData.getPayYm());
                        // 人數
                        int peopleCount = badaprDao.getRpt05PeopleCountBy(baappbase.getApNo(), baappbase.getIssuYm());
                        if ((baappbase.getApNo().substring(0, 1).equals("K") || baappbase.getApNo().substring(0, 1).equals("S")) && baappbase.getSeqNo().equals("0000") && badaprData.getOffsetAmt().compareTo(new BigDecimal(0)) == 1) {
                            caseData.setPeopleCount(Integer.toString(peopleCount + 1));
                        }
                        else {
                            caseData.setPeopleCount(Integer.toString(peopleCount));
                        }
                        // 給付檔資料實付金額需加總
                        String sumAplpayAmt = badaprDao.getDataUpdateRpt05ForAplpayAmt(baappbase.getApNo(), baappbase.getIssuYm());

                        // 若案件為失能或遺屬，受理編號第一碼為K or S，且SEQNO 欄位值為0000，selectDataUpdateRpt05ForBadapr中，欄位OFFSETAMT(本次抵銷金額)欄位值大於0，比照老年年金受款人關係F的格式產生止付單

                        Lnm lnm = lnmDao.getDataUpdateRpt01DataBy(baappbase.getEvtIdnNo(), baappbase.getEvtBrDate());

                        if (lnm != null) {
                            caseData.setIdn_Aply(lnm.getIdnAply());
                            caseData.setName_Aply(lnm.getNameAply());
                            caseData.setAccount_Pay(lnm.getAccountPay());
                        }
                        // K,S案件是否比照老年年金受款人關係F的格式產生止付單
                        caseData.setfStyleForKS("Y");

                        // 以下資料應該都一樣所以只抓第一筆，只有實付金額需加總
                        // caseData.setStexpndReason(badaprList.get(0).getStexpndReason());
                        // caseData.setStexpndDate(badaprList.get(0).getStexpndDate());
                        caseData.setStexpndReason(badaprData.getStexpndReason());
                        caseData.setStexpndDate(badaprData.getStexpndDate());
                        if (sumAplpayAmt != null)
                            caseData.setSumAplpayAmt(new BigDecimal(sumAplpayAmt));
                        else
                            caseData.setSumAplpayAmt(new BigDecimal(0));

                        if (badaprData.getOffsetAmt() != null)
                            caseData.setAplpayAmt(badaprData.getOffsetAmt());
                        else
                            caseData.setAplpayAmt(new BigDecimal(0));

                        caseData.setAplpayDate(badaprData.getAplpayDate());
                        dataUpdateRpt05CaseList.add(caseData);
                    }
                }

            }
        }

        return dataUpdateRpt05CaseList;
    }

    /**
     * 依傳入的條件取得 月編審異動清單 的資料
     * 
     * @param payTyp 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt02Case> getMonthlyRpt02DataBy(String payTyp, String issuYm, String mtestMk) {
        List<Baexalist> baexalistList = baexalistDao.selectMonthlyRpt02DataBy(payTyp, issuYm, mtestMk);
        List<MonthlyRpt02Case> caseList = new ArrayList<MonthlyRpt02Case>();

        for (Baexalist baexalist : baexalistList) {
            MonthlyRpt02Case caseData = new MonthlyRpt02Case();
            BeanUtility.copyProperties(caseData, baexalist);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 月處理異動清單 的資料
     * 
     * @param payTyp 給付別
     * @param issuYm 核定年月
     * @param mtestMk 月核定別
     * @param rptDate 核定日期
     * @return
     */
    public List<MonthlyBatchRptCase> getMonthlyBatchRptDataBy(String payTyp, String issuYm, String mtestMk, String rptDate) {
        List<Baexalist> baexalistList = baexalistDao.selectMonthlyBatchRptDataBy(payTyp, issuYm, mtestMk, rptDate);
        List<MonthlyBatchRptCase> caseList = new ArrayList<MonthlyBatchRptCase>();

        for (Baexalist baexalist : baexalistList) {
            MonthlyBatchRptCase caseData = new MonthlyBatchRptCase();
            BeanUtility.copyProperties(caseData, baexalist);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 月處理核定合格清冊 的資料
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt01Case> getMonthlyRpt01DataBy(String payCode, String issuYm) {
        // 查詢主檔資料
        List<Baappbase> BaappbaseList = baappbaseDao.selectMonthlyRpt01Report(payCode, issuYm);
        List<MonthlyRpt01Case> caseList = new ArrayList<MonthlyRpt01Case>();

        for (int i = 0; i < BaappbaseList.size(); i++) {
            Baappbase baappbase = BaappbaseList.get(i);

            // 查詢給付檔資料

            List<MonthlyRpt01Case> detailList = badaprDao.getMonthlyRpt01ListBy(baappbase.getApNo(), baappbase.getIssuYm());

            for (int j = 0; j < detailList.size(); j++) {
                MonthlyRpt01Case detailData = detailList.get(j);

                MonthlyRpt01Case reportData = new MonthlyRpt01Case();

                BeanUtility.copyProperties(reportData, detailData);

                reportData.setAppDate(baappbase.getAppDate());
                reportData.setEvtName(baappbase.getEvtName());
                reportData.setCaseTyp(baappbase.getCaseTyp());
                reportData.setPayBankId(baappbase.getPayBankId());
                reportData.setBranchId(baappbase.getBranchId());
                reportData.setPayEeacc(baappbase.getPayEeacc());
                reportData.setPayTyp(baappbase.getPayTyp());

                caseList.add(reportData);
            }
        }
        return caseList;
    }

    /**
     * 依傳入的條件取得 月核定撥付總表 的資料（老年）
     * 
     * @param payTyp 給付別
     * @param issuYm 核定年月
     * @param benEvtRel 關係
     * @param eqType SQL EqualType
     * @return
     */
    public List<MonthlyRpt04Case> getMonthlyRpt04DataBy(String payCode, String issuYm, String[] benEvtRel, String eqType, String chkDate) {
        // 20120406 資料改由原本抓Bapayrptrecord，變成抓Bapayrptsum
        // List<Bapayrptrecord> bapayrptrecordList = bapayrptrecordDao.selectMonthlyRpt04Report(payCode, issuYm, benEvtRel, eqType, chkDate);
        List<Bapayrptsum> bapayrptrecordList = bapayrptsumDao.selectMonthlyRpt04Report(payCode, issuYm, benEvtRel, eqType, chkDate);
        List<MonthlyRpt04Case> caseList = new ArrayList<MonthlyRpt04Case>();

        for (Bapayrptsum badapr : bapayrptrecordList) {
            MonthlyRpt04Case caseData = new MonthlyRpt04Case();
            BeanUtility.copyProperties(caseData, badapr);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 月核定撥付總表 Footer的資料（老年）
     * 
     * @param payTyp 給付別
     * @param issuYm 核定年月
     * @param benEvtRel 關係
     * @param eqType SQL EqualType
     * @return
     */
    public List<MonthlyRpt04Case> getMonthlyRpt04DataFooterBy(String payCode, String issuYm, String[] benEvtRel, String eqType, String chkDate) {
        // 20120406 會計科目資料抓Bapayrptaccount
        // List<Bapayrptrecord> bapayrptrecordList = bapayrptrecordDao.selectMonthlyRpt04Report(payCode, issuYm, benEvtRel, eqType, chkDate);
        List<Bapayrptaccount> bapayrptrecordList = bapayrptaccountDao.selectMonthlyRpt04Report(payCode, issuYm, benEvtRel, eqType, chkDate);
        List<MonthlyRpt04Case> caseList = new ArrayList<MonthlyRpt04Case>();

        for (Bapayrptaccount badapr : bapayrptrecordList) {
            MonthlyRpt04Case caseData = new MonthlyRpt04Case();
            BeanUtility.copyProperties(caseData, badapr);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 月核定撥付總表 的資料（失能）
     * 
     * @param payTyp 給付別
     * @param issuYm 核定年月
     * @param benEvtRel 關係
     * @param eqType SQL EqualType
     * @return
     */
    public List<MonthlyRpt04Case> getMonthlyRptK04DataBy(String payCode, String issuYm, String[] benEvtRel, String eqType, String chkDate, String paySeqNo, String isNullOrNot, String eqOrNot) {
        // 20120406 資料改由原本抓Bapayrptrecord，變成抓Bapayrptsum
        List<Bapayrptsum> bapayrptrecordList = bapayrptsumDao.selectMonthlyRptK04Report(payCode, issuYm, benEvtRel, eqType, chkDate, paySeqNo, isNullOrNot, eqOrNot);
        List<MonthlyRpt04Case> caseList = new ArrayList<MonthlyRpt04Case>();

        for (Bapayrptsum badapr : bapayrptrecordList) {
            MonthlyRpt04Case caseData = new MonthlyRpt04Case();
            BeanUtility.copyProperties(caseData, badapr);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 月核定撥付總表 Footer的資料（失能）
     * 
     * @param payTyp 給付別
     * @param issuYm 核定年月
     * @param benEvtRel 關係
     * @param eqType SQL EqualType
     * @return
     */
    public List<MonthlyRpt04Case> getMonthlyRptK04DataFooterBy(String payCode, String issuYm, String[] benEvtRel, String eqType, String chkDate, String paySeqNo, String isNullOrNot, String eqOrNot) {
        // 20120406 會計科目資料抓Bapayrptaccount
        // List<Bapayrptrecord> bapayrptrecordList = bapayrptrecordDao.selectMonthlyRpt04Report(payCode, issuYm, benEvtRel, eqType, chkDate);
        List<Bapayrptaccount> bapayrptrecordList = bapayrptaccountDao.selectMonthlyRptK04Report(payCode, issuYm, benEvtRel, eqType, chkDate, paySeqNo, isNullOrNot, eqOrNot);
        List<MonthlyRpt04Case> caseList = new ArrayList<MonthlyRpt04Case>();

        for (Bapayrptaccount badapr : bapayrptrecordList) {
            MonthlyRpt04Case caseData = new MonthlyRpt04Case();
            BeanUtility.copyProperties(caseData, badapr);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 月核定撥付總表 的資料（遺屬）
     * 
     * @param payTyp 給付別
     * @param issuYm 核定年月
     * @param benEvtRel 關係
     * @param eqType SQL EqualType
     * @return
     */
    public List<MonthlyRpt04Case> getMonthlyRptS04DataBy(String payCode, String issuYm, String[] benEvtRel, String eqType, String chkDate, String isNullOrNot, String eqOrNot) {
        // 20120406 資料改由原本抓Bapayrptrecord，變成抓Bapayrptsum
        List<Bapayrptsum> bapayrptrecordList = bapayrptsumDao.selectMonthlyRptS04Report(payCode, issuYm, benEvtRel, eqType, chkDate, isNullOrNot, eqOrNot);
        List<MonthlyRpt04Case> caseList = new ArrayList<MonthlyRpt04Case>();

        for (Bapayrptsum badapr : bapayrptrecordList) {
            MonthlyRpt04Case caseData = new MonthlyRpt04Case();
            BeanUtility.copyProperties(caseData, badapr);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 月核定撥付總表 Footer的資料（遺屬）
     * 
     * @param payTyp 給付別
     * @param issuYm 核定年月
     * @param benEvtRel 關係
     * @param eqType SQL EqualType
     * @return
     */
    public List<MonthlyRpt04Case> getMonthlyRptS04DataFooterBy(String payCode, String issuYm, String[] benEvtRel, String eqType, String chkDate, String isNullOrNot, String eqOrNot) {
        // 20120406 會計科目資料抓Bapayrptaccount
        // List<Bapayrptrecord> bapayrptrecordList = bapayrptrecordDao.selectMonthlyRpt04Report(payCode, issuYm, benEvtRel, eqType, chkDate);
        List<Bapayrptaccount> bapayrptrecordList = bapayrptaccountDao.selectMonthlyRptS04Report(payCode, issuYm, benEvtRel, eqType, chkDate, isNullOrNot, eqOrNot);
        List<MonthlyRpt04Case> caseList = new ArrayList<MonthlyRpt04Case>();

        for (Bapayrptaccount badapr : bapayrptrecordList) {
            MonthlyRpt04Case caseData = new MonthlyRpt04Case();
            BeanUtility.copyProperties(caseData, badapr);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 月核定撥付總表 代扣補償金資料
     * 
     * @param payTyp 給付別
     * @param issuYm 核定年月
     * @param payTyp 給付方式
     * @param benEvtRel 關係
     * @return
     */
    public List<MonthlyRpt04Case> getMonthlyRpt04CompenAmtDetail(String payCode, String issuYm, String payTyp, String benEvtRel) {
        List<Bapayrptrecord> compenAmtDataList = bapayrptrecordDao.selectMonthlyRpt04ReportCompenAmtDetail(payCode, issuYm, payTyp, benEvtRel);
        List<MonthlyRpt04Case> caseList = new ArrayList<MonthlyRpt04Case>();

        for (Bapayrptrecord badapr : compenAmtDataList) {
            MonthlyRpt04Case caseData = new MonthlyRpt04Case();
            BeanUtility.copyProperties(caseData, badapr);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 退匯核定清單 的資料
     * 
     * @param payTyp 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt11Case> getMonthlyRpt11DataBy(String payCode, String issuYm, String chkDate) {
        // List<Bapayrptrecord> bapayrptrecordList = bapayrptrecordDao.selectMonthlyRpt11Report(payCode, issuYm, chkDate);
        List<Bapayrptsum> bapayrptrecordList = bapayrptsumDao.selectMonthlyRpt11Report(payCode, issuYm, chkDate);
        List<MonthlyRpt11Case> caseList = new ArrayList<MonthlyRpt11Case>();

        for (Bapayrptsum badapr : bapayrptrecordList) {
            MonthlyRpt11Case caseData = new MonthlyRpt11Case();
            BeanUtility.copyProperties(caseData, badapr);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 退匯核定清單 的資料
     * 
     * @param rptTyp
     * @param apNo 受理編號
     * @param chkDate 已收日期/註銷已收日期
     * @param apSeqNo 受款人序
     * @return
     */
    public List<MonthlyRpt25Case> selectMonthlyRpt25DataBy(String rptTyp, String apNo, String chkDate, String seqNo, String paySeqNo) {
        // List<Bapayrptrecord> bapayrptrecordList = bapayrptrecordDao.selectMonthlyRpt11Report(payCode, issuYm, chkDate);
        List<Bapayrptsum> bapayrptrecordList = bapayrptsumDao.selectMonthlyRpt25DataBy(rptTyp, apNo, chkDate, seqNo, paySeqNo);
        List<MonthlyRpt25Case> caseList = new ArrayList<MonthlyRpt25Case>();

        for (Bapayrptsum badapr : bapayrptrecordList) {
            MonthlyRpt25Case caseData = new MonthlyRpt25Case();
            BeanUtility.copyProperties(caseData, badapr);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 退匯核定清單 的資料
     * 
     * @param rptTyp
     * @param apNo 受理編號
     * @param chkDate 已收日期/註銷已收日期
     * @param apSeqNo 受款人序
     * @return
     */
    public List<MonthlyRpt27Case> selectMonthlyRpt27DataBy(String rptTyp, String apNo, String chkDate, String seqNo, String paySeqNo) {
        // List<Bapayrptrecord> bapayrptrecordList = bapayrptrecordDao.selectMonthlyRpt11Report(payCode, issuYm, chkDate);
        List<Bapayrptsum> bapayrptrecordList = bapayrptsumDao.selectMonthlyRpt27DataBy(rptTyp, apNo, chkDate, seqNo, paySeqNo);
        List<MonthlyRpt27Case> caseList = new ArrayList<MonthlyRpt27Case>();

        for (Bapayrptsum badapr : bapayrptrecordList) {
            MonthlyRpt27Case caseData = new MonthlyRpt27Case();
            BeanUtility.copyProperties(caseData, badapr);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 退匯核定清單 的資料
     * 
     * @param payTyp 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt11Case> getMonthlyRpt11DataFooterBy(String payCode, String issuYm, String chkDate) {
        // 20120406 會計科目資料抓Bapayrptaccount
        // List<Bapayrptrecord> bapayrptrecordList = bapayrptrecordDao.selectMonthlyRpt04Report(payCode, issuYm, benEvtRel, eqType, chkDate);
        List<Bapayrptaccount> bapayrptrecordList = bapayrptaccountDao.selectMonthlyRpt11Report(payCode, issuYm, chkDate);
        List<MonthlyRpt11Case> caseList = new ArrayList<MonthlyRpt11Case>();

        for (Bapayrptaccount badapr : bapayrptrecordList) {
            MonthlyRpt11Case caseData = new MonthlyRpt11Case();
            BeanUtility.copyProperties(caseData, badapr);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 退匯/現應收已收核定清單 的資料
     * 
     * @param rptTyp
     * @param apNo 受理編號
     * @param chkDate 已收日期/註銷已收日期
     * @param apSeqNo 受款人序
     * @return
     */
    public List<MonthlyRpt25Case> selectMonthlyRpt25Report(String rptTyp, String apNo, String chkDate, String seqNo, String paySeqNo) {
        // 20120406 會計科目資料抓Bapayrptaccount
        // List<Bapayrptrecord> bapayrptrecordList = bapayrptrecordDao.selectMonthlyRpt04Report(payCode, issuYm, benEvtRel, eqType, chkDate);
        List<Bapayrptaccount> bapayrptrecordList = bapayrptaccountDao.selectMonthlyRpt25Report(rptTyp, apNo, chkDate, seqNo, paySeqNo);
        List<MonthlyRpt25Case> caseList = new ArrayList<MonthlyRpt25Case>();

        for (Bapayrptaccount badapr : bapayrptrecordList) {
            MonthlyRpt25Case caseData = new MonthlyRpt25Case();
            BeanUtility.copyProperties(caseData, badapr);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 退匯/現應收已收註銷核定清單 的資料
     * 
     * @param rptTyp
     * @param apNo 受理編號
     * @param chkDate 已收日期/註銷已收日期
     * @param apSeqNo 受款人序
     * @return
     */
    public List<MonthlyRpt27Case> selectMonthlyRpt27Report(String rptTyp, String apNo, String chkDate, String seqNo, String paySeqNo) {
        // 20120406 會計科目資料抓Bapayrptaccount
        // List<Bapayrptrecord> bapayrptrecordList = bapayrptrecordDao.selectMonthlyRpt04Report(payCode, issuYm, benEvtRel, eqType, chkDate);
        List<Bapayrptaccount> bapayrptrecordList = bapayrptaccountDao.selectMonthlyRpt25Report(rptTyp, apNo, chkDate, seqNo, paySeqNo);
        List<MonthlyRpt27Case> caseList = new ArrayList<MonthlyRpt27Case>();

        for (Bapayrptaccount badapr : bapayrptrecordList) {
            MonthlyRpt27Case caseData = new MonthlyRpt27Case();
            BeanUtility.copyProperties(caseData, badapr);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 改匯核定清單 的資料
     * 
     * @param payTyp 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt13Case> getMonthlyRpt13DataBy(String payCode, String issuYm, String chkDate) {
        // List<Bapayrptrecord> bapayrptrecordList = bapayrptrecordDao.selectMonthlyRpt13Report(payCode, issuYm, chkDate);
        List<Bapayrptsum> bapayrptrecordList = bapayrptsumDao.selectMonthlyRpt13Report(payCode, issuYm, chkDate);
        List<MonthlyRpt13Case> caseList = new ArrayList<MonthlyRpt13Case>();

        for (Bapayrptsum badapr : bapayrptrecordList) {
            MonthlyRpt13Case caseData = new MonthlyRpt13Case();
            BeanUtility.copyProperties(caseData, badapr);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 退匯核定清單 的資料
     * 
     * @param payTyp 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt13Case> getMonthlyRpt13DataFooterBy(String payCode, String issuYm, String chkDate) {
        // 20120406 會計科目資料抓Bapayrptaccount
        // List<Bapayrptrecord> bapayrptrecordList = bapayrptrecordDao.selectMonthlyRpt04Report(payCode, issuYm, benEvtRel, eqType, chkDate);
        List<Bapayrptaccount> bapayrptrecordList = bapayrptaccountDao.selectMonthlyRpt13Report(payCode, issuYm, chkDate);
        List<MonthlyRpt13Case> caseList = new ArrayList<MonthlyRpt13Case>();

        for (Bapayrptaccount badapr : bapayrptrecordList) {
            MonthlyRpt13Case caseData = new MonthlyRpt13Case();
            BeanUtility.copyProperties(caseData, badapr);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 給付函核定通知書 的資料
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<MonthlyRpt05Case> getMonthlyRpt05DataBy(String apNo, String issuYm) {

        List<Baappbase> baappbaseList = new ArrayList<Baappbase>();
        Baappbase evtData = new Baappbase();
        baappbaseList = baappbaseDao.selectMonthlyRpt05Report(apNo);

        String sNotifyForm = "";

        if (baappbaseList.size() == 0) {
            return null;// 查無主檔資料
        }
        else {
            evtData = baappbaseList.get(0);

            // SEQNO=0000 的人不一定會有 Notifyform，故先暫存 SEQNO=0000 的 Notifyform，稍後塞給 SEQNO<>0000
            sNotifyForm = baappbaseList.get(0).getNotifyForm();

            if (baappbaseList.get(0).getNotifyForm().substring(0, 1).equals("D")) {

                // 20171208 核定通知書格式為 Dxx，受文者排除事故者 (SEQNO=0000)，只印關係 (BENEVTREL) 為 2~7 的受益人
                baappbaseList = baappbaseDao.selectMonthlyRpt05Report2(apNo);

                if (baappbaseList.size() == 0) {
                    return null;// 查無主檔資料
                }
            }
            
            if (baappbaseList.get(0).getNotifyForm().substring(0, 1).equals("L")) {

                // 201902 核定通知書格式為 Lxx，受文者排除只印關係 (BENEVTREL) 為 1~7 的受益人
                baappbaseList = baappbaseDao.selectMonthlyRpt05Report3(apNo);

                if (baappbaseList.size() == 0) {
                    return null;// 查無主檔資料
                }
            }
        }

        // 以序號0000那比去查給付檔

        List<Badapr> badaprList = badaprDao.getMonthlyRpt05ListBy(evtData.getApNo(), issuYm);
        if (badaprList.size() == 0)
            return null;// 查無給付核定檔資料

        String chkResult = bachkfileDao.selectForRptReplace(evtData.getApNo(), evtData.getSeqNo(), evtData.getIssuYm());
        List<MonthlyRpt05Case> caseList = new ArrayList<MonthlyRpt05Case>();
        // 承辦單位 連絡電話
        Bbcmf09 bbcmf09 = bbcmf09Dao.selectComTelForMonthlyRpt05By(evtData.getApNo());
        String unit = "";
        String comTel = "";
        String title = "";
        if (bbcmf09 != null) {
            if (bbcmf09.getSex().equals("1")) {
                title = "先生";
            }
            else if (bbcmf09.getSex().equals("2")) {
                title = "小姐";
            }
        }
        if ("L".equalsIgnoreCase(apNo.substring(0, 1))) {
            unit = RptTitleUtility.getGroupsTitleForRpt05(apNo.substring(0, 1), badaprList.get(0).getAplpayDate()) + RptTitleUtility.getOldAgePaymentTitleForRpt05(badaprList.get(0).getAplpayDate());
            if (StringUtils.isBlank(title)) {
                comTel = "02-23961266轉2262";
            }
            else {
                comTel = bbcmf09.getfName() + title + "02-23961266轉" + bbcmf09.getData2();
            }
        }
        else if ("K".equalsIgnoreCase(apNo.substring(0, 1))) {
            unit = RptTitleUtility.getGroupsTitleForRpt05(apNo.substring(0, 1), badaprList.get(0).getAplpayDate()) + RptTitleUtility.getDisabledPaymentTitleForRpt05(badaprList.get(0).getAplpayDate());
            if (StringUtils.isBlank(title)) {
                comTel = "02-23961266轉2250";
            }
            else {
                comTel = bbcmf09.getfName() + title + "02-23961266轉" + bbcmf09.getData2();
            }
        }
        else if ("S".equalsIgnoreCase(apNo.substring(0, 1))) {
            unit = RptTitleUtility.getGroupsTitleForRpt05(apNo.substring(0, 1), badaprList.get(0).getAplpayDate()) + RptTitleUtility.getSurvivorPaymentTitle(DateUtility.getNowWestDate());
            if (StringUtils.isBlank(title)) {
                comTel = "02-23961266轉2263";
            }
            else {
                comTel = bbcmf09.getfName() + title + "02-23961266轉" + bbcmf09.getData2();
            }
        }

        // 正本
        String progenitor = "";
        if (StringUtils.equals("1", StringUtils.substring(baappbaseList.get(0).getEvtIdnNo(), 1, 2))) {
//            progenitor = baappbaseList.get(0).getEvtName() + " 先生";
        	progenitor = baappbaseList.get(0).getEvtName() + " 君";
        }
        else {
//            progenitor = baappbaseList.get(0).getEvtName() + " 女士";
        	progenitor = baappbaseList.get(0).getEvtName() + " 君";
        }
        // 副本
        List<String> copy1 = baappbaseDao.selectMonthlyRpt05ReportCopy1(apNo);
        // 20090417 修改 不要印關係 Z,F
        // List<String> copy2 = baappbaseDao.selectMonthlyRpt05ReportCopy2(apNo);
        // 取代資料庫中主旨跟說明

        List<String> message = new ArrayList<String>();

        if (!("L".equalsIgnoreCase(apNo.substring(0, 1)) && evtData.getNotifyForm().substring(0, 1).equals("D"))) {
            List<Banotify> banotifyList = banotifyDao.selectMonthlyRpt05Report(evtData.getApNo().substring(0, 1), evtData.getNotifyForm());
            CheckAmtCase checkAmtCaseData = getCheckAmtCaseBy(null, apNo.substring(0, 1));
            BaReportReplaceUtility strReplace = new BaReportReplaceUtility(evtData, null, null, badaprList.get(0), StringUtils.trimToEmpty(chkResult), baappbaseList, checkAmtCaseData, issuYm, false, evtData, null);

            for (Banotify banotify : banotifyList) {
                message.add(strReplace.replace(banotify.getDataCont()));
            }
        }

        // 總經理名稱
        String manager = bapasignDao.selectManager(badaprList.get(0).getAplpayDate());

        // 核定通知書檢核碼
        Pbm0001 pbm0001 = new Pbm0001();

        //Modified By LouisChange 20200311 begin
//        Cvldtl cvldtl = cvldtlDao.selectHaddrBy(baappbaseList.get(0).getEvtIds());
        //ba_1090122544 需求  : 以事故者身分證及生日去搜尋當戶政全戶檔
        //List<Cvldtl> cvldtls = cvldtlDao.selectRmpNameBy(baappbaseList.get(0).getEvtIdnNo(), baappbaseList.get(0).getEvtBrDate());
        ////Modified By LouisChange 20200311 end
        
        for (Baappbase baappbase : baappbaseList) {

            // 受文者
            String receiveName = baappbaseDao.getSingleReceiveNameBy(baappbase.getApNo(), baappbase.getSeqNo());

            List<Baappbase> receiveBenNameList = baappbaseDao.getReceiveBenNameBy(baappbase.getApNo(), receiveName);

            if (receiveBenNameList.size() > 0) {

                String receiveBenName = "";
                for (int i = 0; i < receiveBenNameList.size(); i++) {
                    if (i + 1 == receiveBenNameList.size()) {
                        receiveBenName = receiveBenName + receiveBenNameList.get(i).getBenName();
                    }
                    else {
                        receiveBenName = receiveBenName + receiveBenNameList.get(i).getBenName() + "、";
                    }
                }
                // 受文者字串組合
                if (receiveBenNameList.get(0).getReceiveMk().equals("Y")) {
                    receiveName = receiveName + "(兼" + receiveBenName + "之法定代理人)";
                }
                else {
                    receiveName = receiveName + "(" + receiveBenName + "之法定代理人)";
                }
            }

            if ("L".equalsIgnoreCase(apNo.substring(0, 1)) && evtData.getNotifyForm().substring(0, 1).equals("D")) {
                List<Banotify> banotifyList = banotifyDao.selectMonthlyRpt05Report(evtData.getApNo().substring(0, 1), evtData.getNotifyForm());
                CheckAmtCase checkAmtCaseData = getCheckAmtCaseBy(null, apNo.substring(0, 1));
                BaReportReplaceUtility strReplace = new BaReportReplaceUtility(evtData, null, null, badaprList.get(0), StringUtils.trimToEmpty(chkResult), baappbaseList, checkAmtCaseData, issuYm, false, baappbase, null);

                message = new ArrayList<String>();
                for (Banotify banotify : banotifyList) {
                    message.add(strReplace.replace(banotify.getDataCont()));
                }
            }

            MonthlyRpt05Case monthlyRpt05Case = new MonthlyRpt05Case();
            // 報表發文字號
            monthlyRpt05Case.setWordNo(apNo.substring(0, 12));
            // 報表發文日期
            if ("L".equalsIgnoreCase(apNo.substring(0, 1))) {
                // 20190102 鄭小姐提出改印系統日期
                monthlyRpt05Case.setWordDate(DateUtility.formatChineseDateString(DateUtility.getNowChineseDate(), true));
            }
            else {
                monthlyRpt05Case.setWordDate(DateUtility.formatChineseDateString(DateUtility.changeDateType(badaprList.get(0).getAplpayDate()), true));
            }
            // 報表承辦單位
            monthlyRpt05Case.setUnit(unit);
            // 報表連絡電話
            monthlyRpt05Case.setComTel(comTel);
            // 報表判斷標題使用之核付日期
            monthlyRpt05Case.setAplpayDate(badaprList.get(0).getAplpayDate());
            // 報表受文者 受款人為自然人時要印先生 女士
            String benEvtRel = StringUtils.trimToEmpty(baappbase.getBenEvtRel());
            if (benEvtRel.equals("1") || benEvtRel.equals("2") || benEvtRel.equals("3") || benEvtRel.equals("4") || benEvtRel.equals("5") || benEvtRel.equals("6") || benEvtRel.equals("7") || benEvtRel.equalsIgnoreCase("D")
                            || benEvtRel.equalsIgnoreCase("E")) {
                String benIdnNo = StringUtils.trimToEmpty(baappbase.getBenIdnNo());
                if (!benIdnNo.equals("") && benIdnNo.substring(1, 2).equals("1")) {
//                    monthlyRpt05Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()) + " 先生");
                	monthlyRpt05Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()) + " 君");
                }
                else if (!benIdnNo.equals("") && benIdnNo.substring(1, 2).equals("2")) {
//                    monthlyRpt05Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()) + " 女士");
                	monthlyRpt05Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()) + " 君");
                }
                else if (!benIdnNo.equals("")) {
                    monthlyRpt05Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()) + " 君  ");
                }
            }
            else {
                monthlyRpt05Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()));
            }

          //Modified By LouisChange 20200311 begin
//            if (cvldtl != null)
//                monthlyRpt05Case.setRmp_Name(cvldtl.getRmp_Name());
            //ba_1090122544 需求  : 以事故者身分證及生日去搜尋當戶政全戶檔, 若只有一筆資料且該筆資料原住名身分代碼(OlivCode)為1或2時, 原住民羅馬拼音(Rmp_Name)有值則顯示
            List<Cvldtl> cvldtls = cvldtlDao.selectRmpNameBy(baappbase.getBenIdnNo(), baappbase.getBenBrDate());
            if (CollectionUtils.isNotEmpty(cvldtls) && cvldtls.size() == 1) {
            	Cvldtl cvldtl = cvldtls.get(0);
            	if (cvldtl != null && ("1".equals(cvldtl.getOlivCode()) || "2".equals(cvldtl.getOlivCode()))) {
            		String rmpName = cvldtl.getRmp_Name();
            		if (StringUtils.isNotBlank(rmpName)) {
            			monthlyRpt05Case.setRmp_Name(rmpName);
            		}
            	}
            }
            //Modified By LouisChange 20200311 end

            // 地址
            monthlyRpt05Case.setAddrZip(StringUtils.trimToEmpty(baappbase.getCommZip()));
            monthlyRpt05Case.setAddr(StringUtils.trimToEmpty(baappbase.getCommAddr()));

            monthlyRpt05Case.setMessage(message);
            monthlyRpt05Case.setProgenitor(progenitor);
            monthlyRpt05Case.setCopy1(copy1);
            // monthlyRpt05Case.setCopy2(copy2);
            monthlyRpt05Case.setIssuYm(badaprList.get(0).getIssuYm());
            if (badaprList.get(0).getIssueAmt() != null) {
                monthlyRpt05Case.setPbm0001(pbm0001.getPrintString(baappbase.getApNo(), baappbase.getBenIdnNo(), badaprList.get(0).getAplpayDate(), badaprList.get(0).getIssueAmt().intValue()));
            }
            else {
                monthlyRpt05Case.setPbm0001(pbm0001.getPrintString(baappbase.getApNo(), baappbase.getBenIdnNo(), badaprList.get(0).getAplpayDate(), 0));
            }

            monthlyRpt05Case.setManager(manager);
            monthlyRpt05Case.setReceiveName(receiveName);
            monthlyRpt05Case.setRealName(baappbaseDao.getSingleReceiveNameBy(baappbase.getApNo(), baappbase.getSeqNo()));
            monthlyRpt05Case.setApNo(apNo);
            monthlyRpt05Case.setSeqNo(baappbase.getSeqNo());
            monthlyRpt05Case.setNotifyForm(sNotifyForm);
            caseList.add(monthlyRpt05Case);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 給付函核定通知書 的資料
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<MonthlyRpt05Case> getMonthlyRpt05DisabledDataBy(String apNo, String issuYm) {
        List<Baappbase> baappbaseList = baappbaseDao.selectMonthlyRpt05Report(apNo);
        Baappexpand baappexpand = baappexpandDao.selectDisabledDataForPaymentReview(apNo);
        if (baappbaseList.size() == 0)
            return null;// 查無主檔資料

        // 以序號0000那比去查給付檔

        Baappbase evtData = baappbaseList.get(0);
        List<Badapr> badaprList = badaprDao.getMonthlyRpt05ListBy(evtData.getApNo(), issuYm);
        if (badaprList.size() == 0)
            return null;// 查無給付核定檔資料

        String chkResult = bachkfileDao.selectForRptReplace(evtData.getApNo(), evtData.getSeqNo(), evtData.getIssuYm());
        List<MonthlyRpt05Case> caseList = new ArrayList<MonthlyRpt05Case>();
        // 承辦單位 連絡電話
        Bbcmf09 bbcmf09 = bbcmf09Dao.selectComTelForMonthlyRpt05By(evtData.getApNo());
        String unit = "";
        String comTel = "";
        String title = "";
        if (bbcmf09 != null) {
            if (bbcmf09.getSex().equals("1")) {
                title = "先生";
            }
            else if (bbcmf09.getSex().equals("2")) {
                title = "小姐";
            }
        }
        if ("L".equalsIgnoreCase(apNo.substring(0, 1))) {
            unit = RptTitleUtility.getGroupsTitleForRpt05(apNo.substring(0, 1), badaprList.get(0).getAplpayDate()) + RptTitleUtility.getOldAgePaymentTitleForRpt05(badaprList.get(0).getAplpayDate());
            if (StringUtils.isBlank(title)) {
                comTel = "02-23961266轉2262";
            }
            else {
                comTel = bbcmf09.getfName() + title + "02-23961266轉" + bbcmf09.getData2();
            }
        }
        else if ("K".equalsIgnoreCase(apNo.substring(0, 1))) {
            unit = RptTitleUtility.getGroupsTitleForRpt05(apNo.substring(0, 1), badaprList.get(0).getAplpayDate()) + RptTitleUtility.getDisabledPaymentTitleForRpt05(badaprList.get(0).getAplpayDate());
            if (StringUtils.isBlank(title)) {
                comTel = "02-23961266轉2250";
            }
            else {
                comTel = bbcmf09.getfName() + title + "02-23961266轉" + bbcmf09.getData2();
            }
        }
        else if ("S".equalsIgnoreCase(apNo.substring(0, 1))) {
            unit = RptTitleUtility.getGroupsTitleForRpt05(apNo.substring(0, 1), badaprList.get(0).getAplpayDate()) + RptTitleUtility.getSurvivorPaymentTitle(DateUtility.getNowWestDate());
            if (StringUtils.isBlank(title)) {
                comTel = "02-23961266轉2263";
            }
            else {
                comTel = bbcmf09.getfName() + title + "02-23961266轉" + bbcmf09.getData2();
            }
        }

        // 正本
        String progenitor = "";
        if (StringUtils.equals("1", StringUtils.substring(baappbaseList.get(0).getEvtIdnNo(), 1, 2))) {
//            progenitor = baappbaseList.get(0).getEvtName() + " 先生";
        	progenitor = baappbaseList.get(0).getEvtName() + " 君";
        }
        else {
//            progenitor = baappbaseList.get(0).getEvtName() + " 女士";
        	progenitor = baappbaseList.get(0).getEvtName() + " 君";
        }
        // 副本
        List<String> copy1 = baappbaseDao.selectMonthlyRpt05ReportCopy1(apNo);
        // 20090417 修改 不要印關係 Z,F
        // List<String> copy2 = baappbaseDao.selectMonthlyRpt05ReportCopy2(apNo);
        // 取代資料庫中主旨跟說明

        List<Banotify> banotifyList = banotifyDao.selectMonthlyRpt05Report(evtData.getApNo().substring(0, 1), evtData.getNotifyForm());
        CheckAmtCase checkAmtCaseData = getCheckAmtCaseBy(null, apNo.substring(0, 1));
        BaReportReplaceUtility strReplace = new BaReportReplaceUtility(evtData, baappexpand, null, badaprList.get(0), StringUtils.trimToEmpty(chkResult), baappbaseList, checkAmtCaseData, issuYm, false, null, null);
        List<String> message = new ArrayList<String>();
        for (Banotify banotify : banotifyList) {
            message.add(strReplace.replace(banotify.getDataCont()));
        }

        // 總經理名稱
        String manager = bapasignDao.selectManager(badaprList.get(0).getAplpayDate());

        // 核定通知書檢核碼
        Pbm0001 pbm0001 = new Pbm0001();

        //Modified By LouisChange 20200311 begin
//        Cvldtl cvldtl = cvldtlDao.selectHaddrBy(baappbaseList.get(0).getEvtIds());
        //ba_1090122544 需求  : 以事故者身分證及生日去搜尋當戶政全戶檔
        //List<Cvldtl> cvldtls = cvldtlDao.selectRmpNameBy(baappbaseList.get(0).getEvtIdnNo(), baappbaseList.get(0).getEvtBrDate());
        //Modified By LouisChange 20200311 end

        for (Baappbase baappbase : baappbaseList) {
            MonthlyRpt05Case monthlyRpt05Case = new MonthlyRpt05Case();
            // 報表發文字號
            monthlyRpt05Case.setWordNo(apNo.substring(0, 12));
            // 報表發文日期
            monthlyRpt05Case.setWordDate(DateUtility.formatChineseDateString(DateUtility.changeDateType(badaprList.get(0).getAplpayDate()), true));
            // 報表承辦單位
            monthlyRpt05Case.setUnit(unit);
            // 報表連絡電話
            monthlyRpt05Case.setComTel(comTel);
            // 報表判斷標題使用之核付日期
            monthlyRpt05Case.setAplpayDate(badaprList.get(0).getAplpayDate());
            // 報表受文者 受款人為自然人時要印先生 女士
            String benEvtRel = StringUtils.trimToEmpty(baappbase.getBenEvtRel());
            if (benEvtRel.equals("1") || benEvtRel.equals("2") || benEvtRel.equals("3") || benEvtRel.equals("4") || benEvtRel.equals("5") || benEvtRel.equals("6") || benEvtRel.equals("7") || benEvtRel.equalsIgnoreCase("D")
                            || benEvtRel.equalsIgnoreCase("E")) {
                String benIdnNo = StringUtils.trimToEmpty(baappbase.getBenIdnNo());
                if (!benIdnNo.equals("") && benIdnNo.substring(1, 2).equals("1")) {
//                    monthlyRpt05Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()) + " 先生");
                    monthlyRpt05Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()) + " 君");
                }
                else if (!benIdnNo.equals("") && benIdnNo.substring(1, 2).equals("2")) {
//                    monthlyRpt05Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()) + " 女士");
                	monthlyRpt05Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()) + " 君");
                }
                else if (!benIdnNo.equals("")) {
                    monthlyRpt05Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()) + " 君  ");
                }
            }
            else {
                monthlyRpt05Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()));
            }

            //Modified By LouisChange 20200311 begin
//            if (cvldtl != null)
//                monthlyRpt05Case.setRmp_Name(cvldtl.getRmp_Name());
            //ba_1090122544 需求  : 以事故者身分證及生日去搜尋當戶政全戶檔, 若只有一筆資料且該筆資料原住名身分代碼(OlivCode)為1或2時, 原住民羅馬拼音(Rmp_Name)有值則顯示
            List<Cvldtl> cvldtls = cvldtlDao.selectRmpNameBy(baappbase.getBenIdnNo(), baappbase.getBenBrDate());
            if (CollectionUtils.isNotEmpty(cvldtls) && cvldtls.size() == 1) {
            	Cvldtl cvldtl = cvldtls.get(0);
            	if (cvldtl != null && ("1".equals(cvldtl.getOlivCode()) || "2".equals(cvldtl.getOlivCode()))) {
            		String rmpName = cvldtl.getRmp_Name();
            		if (StringUtils.isNotBlank(rmpName)) {
            			monthlyRpt05Case.setRmp_Name(rmpName);
            		}
            	}
            }
            //Modified By LouisChange 20200311 end

            // 地址
            monthlyRpt05Case.setAddrZip(StringUtils.trimToEmpty(baappbase.getCommZip()));
            monthlyRpt05Case.setAddr(StringUtils.trimToEmpty(baappbase.getCommAddr()));

            monthlyRpt05Case.setMessage(message);
            monthlyRpt05Case.setProgenitor(progenitor);
            monthlyRpt05Case.setCopy1(copy1);
            // monthlyRpt05Case.setCopy2(copy2);
            monthlyRpt05Case.setIssuYm(badaprList.get(0).getIssuYm());
            if (badaprList.get(0).getIssueAmt() != null) {
                monthlyRpt05Case.setPbm0001(pbm0001.getPrintString(baappbase.getApNo(), baappbase.getBenIdnNo(), badaprList.get(0).getAplpayDate(), badaprList.get(0).getIssueAmt().intValue()));
            }
            else {
                monthlyRpt05Case.setPbm0001(pbm0001.getPrintString(baappbase.getApNo(), baappbase.getBenIdnNo(), badaprList.get(0).getAplpayDate(), 0));
            }

            monthlyRpt05Case.setManager(manager);
            monthlyRpt05Case.setApNo(apNo);
            monthlyRpt05Case.setSeqNo(baappbase.getSeqNo());
            monthlyRpt05Case.setNotifyForm(baappbase.getNotifyForm());
            caseList.add(monthlyRpt05Case);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 給付函核定通知書 的資料 遺屬年金
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<MonthlyRpt05Case> getMonthlyRpt05SurvivorDataBy(UserBean userData, String apNo, String issuYm) {
        List<Baappbase> baappbaseList = baappbaseDao.selectMonthlyRpt05ForSurvivorReport(apNo);
        if (baappbaseList.size() == 0)
            return null;// 查無主檔資料

        // 以序號0000那比去查給付檔
        Baappbase evtData = baappbaseList.get(0);
        List<Badapr> badaprList = badaprDao.getMonthlyRpt05ListBy(evtData.getApNo(), issuYm);
        // List<Badapr> badaprList = badaprDao.selectDataForReportReplace(evtData.getApNo(), issuYm);
        if (badaprList.size() == 0)
            return null;// 查無給付核定檔資料

        String chkResult = bachkfileDao.selectForRptReplace(evtData.getApNo(), evtData.getSeqNo(), evtData.getIssuYm());
        List<MonthlyRpt05Case> caseList = new ArrayList<MonthlyRpt05Case>();
        // 承辦單位 連絡電話
        Bbcmf09 bbcmf09 = bbcmf09Dao.selectComTelForMonthlyRpt05By(evtData.getApNo());
        String unit = "";
        String comTel = "";
        String title = "";
        if (bbcmf09 != null) {
            if (bbcmf09.getSex().equals("1")) {
                title = "先生";
            }
            else if (bbcmf09.getSex().equals("2")) {
                title = "小姐";
            }
        }
        if ("L".equalsIgnoreCase(apNo.substring(0, 1))) {
            unit = RptTitleUtility.getGroupsTitleForRpt05(apNo.substring(0, 1), badaprList.get(0).getAplpayDate()) + RptTitleUtility.getOldAgePaymentTitleForRpt05(badaprList.get(0).getAplpayDate());
            if (StringUtils.isBlank(title)) {
                comTel = "02-23961266轉2262";
            }
            else {
                comTel = bbcmf09.getfName() + title + "02-23961266轉" + bbcmf09.getData2();
            }
        }
        else if ("K".equalsIgnoreCase(apNo.substring(0, 1))) {
            unit = RptTitleUtility.getGroupsTitleForRpt05(apNo.substring(0, 1), badaprList.get(0).getAplpayDate()) + RptTitleUtility.getDisabledPaymentTitleForRpt05(badaprList.get(0).getAplpayDate());
            if (StringUtils.isBlank(title)) {
                comTel = "02-23961266轉2250";
            }
            else {
                comTel = bbcmf09.getfName() + title + "02-23961266轉" + bbcmf09.getData2();
            }
        }
        else if ("S".equalsIgnoreCase(apNo.substring(0, 1))) {
            unit = RptTitleUtility.getGroupsTitleForRpt05(apNo.substring(0, 1), badaprList.get(0).getAplpayDate()) + RptTitleUtility.getSurvivorPaymentTitle(DateUtility.getNowWestDate());
            if (StringUtils.isBlank(title)) {
                comTel = "02-23961266轉2263";
            }
            else {
                comTel = bbcmf09.getfName() + title + "02-23961266轉" + bbcmf09.getData2();
            }
        }

        // 20090417 修改 不要印關係 Z,F
        // List<String> copy2 = baappbaseDao.selectMonthlyRpt05ReportCopy2(apNo);
        // 取代資料庫中主旨跟說明

        // 總經理名稱
        String manager = bapasignDao.selectManager(badaprList.get(0).getAplpayDate());

        // 核定通知書檢核碼
        Pbm0001 pbm0001 = new Pbm0001();

        // 取得須列印資料 SeqNo
        // 2017增修案：新案讀取該受理編號主檔序號非0000之各受款人姓名當受文者，續發案以<A146>之對象為受文者。
        // 20171017需求：不論何種案件皆以<A146>之對象為受文者。
        List<Baappbase> baappbasePrintList = new ArrayList<Baappbase>();
        List<Baappbase> baappbasePrintList1 = new ArrayList<Baappbase>();

//        if (evtData.getCaseTyp().equals("2")) {
        // 資料中有代理人會整合，取得該APNO，非本人(<>0000) seqno 最小的那筆
        baappbasePrintList = baappbaseDao.selectMonthlyRpt05PrintCase2DataForSurvivorReport(apNo, ("".equals(issuYm)) ? evtData.getIssuYm() : issuYm);

        // 資料中有代理人不會整合，取得該APNO，非本人(<>0000)所有資料
        baappbasePrintList1 = baappbaseDao.selectMonthlyRpt05PrintCase2DataForSurvivorReport1(apNo, ("".equals(issuYm)) ? evtData.getIssuYm() : issuYm);

//        }
//        else {
//            baappbasePrintList = baappbaseDao.selectMonthlyRpt05PrintDataForSurvivorReport(apNo); // 資料中有代理人會整合
//            baappbasePrintList1 = baappbaseDao.selectMonthlyRpt05PrintDataForSurvivorReport1(apNo); // 資料中有代理人不會整合
//        }

        // 正本
        String progenitor = "";
        if (StringUtils.equals("1", StringUtils.substring(baappbasePrintList.get(0).getBenIdnNo(), 1, 2))) {
//            progenitor = baappbasePrintList.get(0).getBenName() + " 先生";
            progenitor = baappbasePrintList.get(0).getBenName() + " 君";
        }
        else {
//            progenitor = baappbasePrintList.get(0).getBenName() + " 女士";
            progenitor = baappbasePrintList.get(0).getBenName() + " 君";
        }
        // 副本
        List<String> copy1 = new ArrayList<String>();
        if (baappbasePrintList.size() > 1) {
            for (Baappbase baappbase : baappbasePrintList) {
                if (!baappbase.getSeqNo().equals(baappbasePrintList.get(0).getSeqNo())) {
                    copy1.add(baappbase.getBenName());
                }
            }
        }

        

        for (Baappbase baappbase : baappbasePrintList) {
            // 遺屬不印0000受款人
            if (!baappbase.getSeqNo().equals("0000")) {

                Baappexpand baappexpand = new Baappexpand();
                BeanUtility.copyProperties(baappexpand, baappbase);

                // 投保薪資比例金額
                String sInsuranceSalary = bachkfileDao.selectInsuranceSalaryForRptReplace(baappbase.getApNo(), baappbase.getSeqNo(), baappbase.getIssuYm());
                if (sInsuranceSalary == null) {
                    sInsuranceSalary = "0";
                }
                baappbase.setSInsuranceSalary(sInsuranceSalary);

                String receiveName = "";
                List<Baappbase> receiveBenNameList = new ArrayList<Baappbase>();
//                if (evtData.getCaseTyp().equals("2")) {
                    // 受文者
                    receiveName = baappbaseDao.getSingleReceiveNameCase2By(baappbase.getApNo(), baappbase.getSeqNo());
                    receiveBenNameList = baappbaseDao.getReceiveBenNameCase2By(baappbase.getApNo(), receiveName);
//                }
//                else {
                    // 受文者
//                    receiveName = baappbaseDao.getSingleReceiveNameBy(baappbase.getApNo(), baappbase.getSeqNo());
//                    receiveBenNameList = baappbaseDao.getReceiveBenNameBy(baappbase.getApNo(), receiveName);
//                }
                    
                    
                String sReceiveIdnNo = baappbaseDao.getSingleReceiveIdnNoBy(baappbase.getApNo(), receiveName);
                String sReceiveGender = "";

                if (sReceiveIdnNo != null) {
                    if (!sReceiveIdnNo.equals("") && sReceiveIdnNo.substring(1, 2).equals("1")) {
//                        sReceiveGender = " 先生";
                    	sReceiveGender = " 君";
                    }
                    else if (!sReceiveIdnNo.equals("") && sReceiveIdnNo.substring(1, 2).equals("2")) {
//                        sReceiveGender = " 女士";
                    	sReceiveGender = " 君";
                    }
                    else if (!sReceiveIdnNo.equals("")) {
                        sReceiveGender = " 君";
                    }
                }

                if (receiveBenNameList.size() > 0) {

                    String receiveBenName = "";
                    for (int i = 0; i < receiveBenNameList.size(); i++) {
                        if (i + 1 == receiveBenNameList.size()) {
                            receiveBenName = receiveBenName + receiveBenNameList.get(i).getBenName();
                        }
                        else {
                            receiveBenName = receiveBenName + receiveBenNameList.get(i).getBenName() + "、";
                        }
                    }
                    // 受文者字串組合
                    if (receiveBenNameList.get(0).getReceiveMk().equals("Y")) {
                        receiveName = receiveName + "(兼" + receiveBenName + "之法定代理人)" + sReceiveGender;
                    }
                    else if(receiveBenNameList.get(0).getReceiveMk().equals("N")) {
                        receiveName = receiveName + "(" + receiveBenName + "之法定代理人)" + sReceiveGender;
                    }
                }
                else {
                    receiveName = receiveName + sReceiveGender;
                }

                // 文字代碼替換
                List<Banotify> banotifyList = banotifyDao.selectMonthlyRpt05Report(evtData.getApNo().substring(0, 1), evtData.getNotifyForm());
                List<BapairrCase> pairrList = new ArrayList<BapairrCase>();
                CheckAmtCase checkAmtCaseData = new CheckAmtCase(pairrList);
                if (evtData.getApItem().equals("7") || evtData.getApItem().equals("8")) {
                    if (evtData.getApItem().equals("7")) {
                        checkAmtCaseData = getCheckAmtCaseBy(null, "K");
                    }
                    else {
                        checkAmtCaseData = getCheckAmtCaseBy(null, "L");
                    }
                }
                else {
                    checkAmtCaseData = getCheckAmtCaseBy(null, evtData.getApNo().substring(0, 1));
                }
                BaReportReplaceUtility strReplace = new BaReportReplaceUtility(evtData, baappexpand, baappbase.getPayTyp(), badaprList.get(0), StringUtils.trimToEmpty(chkResult), baappbaseList, checkAmtCaseData, issuYm, true, baappbase,
                                baappbasePrintList1);
                List<String> message = new ArrayList<String>();
                for (Banotify banotify : banotifyList) {
                    message.add(strReplace.replace(banotify.getDataCont()));
                }

                MonthlyRpt05Case monthlyRpt05Case = new MonthlyRpt05Case();
                // 報表發文字號
                monthlyRpt05Case.setWordNo(apNo.substring(0, 12));
                // 報表發文日期
                monthlyRpt05Case.setWordDate(DateUtility.formatChineseDateString(DateUtility.changeDateType(badaprList.get(0).getAplpayDate()), true));
                // 報表承辦單位
                monthlyRpt05Case.setUnit(unit);
                // 報表連絡電話
                monthlyRpt05Case.setComTel(comTel);
                // 報表判斷標題使用之核付日期
                monthlyRpt05Case.setAplpayDate(badaprList.get(0).getAplpayDate());
                // 報表受文者 受款人為自然人時要印先生 女士
                String benEvtRel = StringUtils.trimToEmpty(baappbase.getBenEvtRel());
                if (benEvtRel.equals("1") || benEvtRel.equals("2") || benEvtRel.equals("3") || benEvtRel.equals("4") || benEvtRel.equals("5") || benEvtRel.equals("6") || benEvtRel.equals("7") || benEvtRel.equalsIgnoreCase("D") || benEvtRel.equalsIgnoreCase("E")) {
                    // String benIdnNo = StringUtils.trimToEmpty(baappbase.getBenIdnNo());
                    String benIdnNo = baappbaseDao.getSingleReceiveIdnNoBy(baappbase.getApNo(), baappbase.getBenName());
                    if (benIdnNo != null) {
                        if (!benIdnNo.equals("") && benIdnNo.substring(1, 2).equals("1")) {
//                            monthlyRpt05Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()) + " 先生");
                            monthlyRpt05Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()) + " 君");
                        }
                        else if (!benIdnNo.equals("") && benIdnNo.substring(1, 2).equals("2")) {
//                            monthlyRpt05Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()) + " 女士");
                            monthlyRpt05Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()) + " 君");
                        }
                        else if (!benIdnNo.equals("")) {
                            monthlyRpt05Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()) + " 君");
                        }
                    }
                    else {
                        monthlyRpt05Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()));
                    }
                }
                else {
                    monthlyRpt05Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()));
                }

                //Modified By LouisChange 20200311 begin
//                if (cvldtl != null)
//                    monthlyRpt05Case.setRmp_Name(cvldtl.getRmp_Name());
//              Cvldtl cvldtl = cvldtlDao.selectHaddrBy(baappbaseList.get(0).getEvtIds());
                //ba_1090122544 需求  : 身分證及生日去搜尋當戶政全戶檔, 若只有一筆資料且該筆資料原住名身分代碼(OlivCode)為1或2時, 原住民羅馬拼音(Rmp_Name)有值則顯示
       
              List<Cvldtl> cvldtls = cvldtlDao.selectRmpNameBy(baappbase.getBenIdnNo(), baappbase.getBenBrDate());

                if (CollectionUtils.isNotEmpty(cvldtls) && cvldtls.size() == 1) {
                	Cvldtl cvldtl = cvldtls.get(0);
                	if (cvldtl != null && ("1".equals(cvldtl.getOlivCode()) || "2".equals(cvldtl.getOlivCode()))) {
                		String rmpName = cvldtl.getRmp_Name();
                		if (StringUtils.isNotBlank(rmpName)) {
                			monthlyRpt05Case.setRmp_Name(rmpName);
                		}
                	}
                }
                //Modified By LouisChange 20200311 begin

                // 地址
                monthlyRpt05Case.setAddrZip(StringUtils.trimToEmpty(baappbase.getCommZip()));
                monthlyRpt05Case.setAddr(StringUtils.trimToEmpty(baappbase.getCommAddr()));

                monthlyRpt05Case.setMessage(message);
                monthlyRpt05Case.setProgenitor(progenitor);
                monthlyRpt05Case.setCopy1(copy1);
                // monthlyRpt05Case.setCopy2(copy2);
                monthlyRpt05Case.setIssuYm(badaprList.get(0).getIssuYm());
                if (badaprList.get(0).getIssueAmt() != null) {
                    monthlyRpt05Case.setPbm0001(pbm0001.getPrintString(baappbase.getApNo(), baappbase.getBenIdnNo(), badaprList.get(0).getAplpayDate(), badaprList.get(0).getIssueAmt().intValue()));
                }
                else {
                    monthlyRpt05Case.setPbm0001(pbm0001.getPrintString(baappbase.getApNo(), baappbase.getBenIdnNo(), badaprList.get(0).getAplpayDate(), 0));
                }
                
                // 正本 姓名 + 先生/女士
                String sRealName = baappbaseDao.getSingleReceiveNameBy(baappbase.getApNo(), baappbase.getSeqNo());
                String sRealIdnNo = baappbaseDao.getSingleReceiveIdnNoBy(baappbase.getApNo(), sRealName);
                String sGender = "";

                if (sRealIdnNo != null) {
                    if (!sRealIdnNo.equals("") && sRealIdnNo.substring(1, 2).equals("1")) {
//                        sGender = " 先生";
                    	sGender = " 君";
                    }
                    else if (!sRealIdnNo.equals("") && sRealIdnNo.substring(1, 2).equals("2")) {
//                        sGender = " 女士";
                    	sGender = " 君";
                    }
                    else if (!sRealIdnNo.equals("")) {
                        sGender = " 君";
                    }
                }

                monthlyRpt05Case.setManager(manager);
                monthlyRpt05Case.setReceiveName(receiveName);
                monthlyRpt05Case.setRealName(sRealName + sGender);
                monthlyRpt05Case.setApNo(apNo);
                monthlyRpt05Case.setSeqNo(baappbase.getSeqNo());
                monthlyRpt05Case.setNotifyForm(baappbase.getNotifyForm());
                caseList.add(monthlyRpt05Case);

            }
        }
        return caseList;
    }
    
    /**
     * 依傳入的條件取得 勞保新制計算核定金額用CASE
     * 
     * @param payCode
     * @return
     */
    public CheckAmtCase getCheckAmtCaseBy(String effYm, String payCode) {
        List<Bapairr> bapairrList = bapairrDao.selectBapairrDataForReplacementRatioMaintBy(effYm, payCode);
        List<BapairrCase> bapairrCaseList = new ArrayList<BapairrCase>();
        for (Bapairr bapairr : bapairrList) {
            bapairrCaseList.add(new BapairrCase(bapairr));
        }

        return new CheckAmtCase(bapairrCaseList);
    }

    /**
     * 依傳入的條件取得 核定清單 的資料
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt06Case> getMonthlyRpt06DataBy(String payCode, String issuYm) {
        // 查詢主檔資料
        List<Baappbase> BaappbaseList = baappbaseDao.selectMonthlyRpt06Report(payCode, issuYm);
        List<MonthlyRpt06Case> caseList = new ArrayList<MonthlyRpt06Case>();

        for (int i = 0; i < BaappbaseList.size(); i++) {
            Baappbase baappbase = BaappbaseList.get(i);

            // 查詢給付檔資料

            MonthlyRpt06DetailCase detailData = badaprDao.getMonthlyRpt06DataBy(baappbase.getApNo(), baappbase.getSeqNo(), baappbase.getIssuYm());

            MonthlyRpt06Case reportData = new MonthlyRpt06Case();
            BeanUtility.copyProperties(reportData, baappbase);

            if (detailData != null) {
                reportData.setVeriSeq(detailData.getVeriSeq());
                reportData.setPayYmBeg(detailData.getPayYmBeg());
                reportData.setPayYmEnd(detailData.getPayYmEnd());
                reportData.setIssueAmt(detailData.getIssueAmt());
                reportData.setAplpayAmt(detailData.getAplpayAmt());
                reportData.setPayTyp(baappbase.getPayTyp());
            }

            caseList.add(reportData);
        }
        return caseList;
    }

    /**
     * 依傳入的條件取得 月核定給付撥款總額表 的資料（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt07Case> getMonthlyRpt07DataBy(String payCode, String issuYm, String chkDate) {
        List<MonthlyRpt07Case> caseList = new ArrayList<MonthlyRpt07Case>();

        // 依 給付別 及 核定年月 取得 出帳類別 清單
        List<String> taTyp2List = bagivetmpdtlDao.getMonthlyRpt07TaTyp2List(payCode, DateUtility.changeChineseYearMonthType(issuYm), chkDate);

        // 依各 出帳類別 取得 MonthlyRpt07Case 的資料

        for (String taTyp2 : taTyp2List) { // begin ... [

            MonthlyRpt07Case caseData = new MonthlyRpt07Case();

            // 設定 給付別

            caseData.setPayCode(payCode);

            // 取得mFileName 資料
            List<Bagivetmpdtl> mFileNameList = bagivetmpdtlDao.getMonthlyRpt07mFileNameBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), taTyp2, chkDate);
            StringBuffer mFileName = new StringBuffer();
            for (int i = 0; i < mFileNameList.size(); i++) {
                if (i == mFileNameList.size() - 1) {
                    mFileName.append(mFileNameList.get(i).getMfileName());
                }
                else {
                    mFileName.append(mFileNameList.get(i).getMfileName() + "、");
                }
            }

            // 取得 Header 資料
            List<Bagivetmpdtl> headerList = bagivetmpdtlDao.getMonthlyRpt07HeaderDataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), taTyp2, chkDate);
            for (Bagivetmpdtl headerData : headerList) {
                caseData.setTaTyp2(taTyp2); // 出帳類別
                // caseData.setMfileName(headerData.getMfileName()); // 媒體檔案名稱 - 檔案名稱
                caseData.setMfileName(mFileName.toString()); // 媒體檔案名稱 - 檔案名稱
                caseData.setPayDate2(headerData.getPayDate2()); // 實際核付日期 - 撥款日期

                // 新案相關資料 (issuTyp = 1)
                // [
                if (StringUtils.equals(headerData.getIssuTyp(), ConstantKey.BAAPPBASE_CASETYP_1)) {
                    caseData.setDataCountIssuTyp1(headerData.getDataCount()); // 筆數 - 各 核付分類 的筆數

                    caseData.setAmt2IssuTyp1(headerData.getAmt2()); // 交易金額 - 各 核付分類 的金額

                }
                // ]

                // 續發相關資料 (issuTyp = 2)
                // [
                if (StringUtils.equals(headerData.getIssuTyp(), ConstantKey.BAAPPBASE_CASETYP_2)) {
                    caseData.setDataCountIssuTyp2(headerData.getDataCount()); // 筆數 - 各 核付分類 的筆數

                    caseData.setAmt2IssuTyp2(headerData.getAmt2()); // 交易金額 - 各 核付分類 的金額

                }
                // ]

                // 結案相關資料 (issuTyp = 4)
                // [
                if (StringUtils.equals(headerData.getIssuTyp(), ConstantKey.BAAPPBASE_CASETYP_4)) {
                    caseData.setDataCountIssuTyp4(headerData.getDataCount()); // 筆數 - 各 核付分類 的筆數

                    caseData.setAmt2IssuTyp4(headerData.getAmt2()); // 交易金額 - 各 核付分類 的金額

                }
                // ]

                // 補發相關資料 (issuTyp = 5)
                // [
                if (StringUtils.equals(headerData.getIssuTyp(), ConstantKey.BAAPPBASE_CASETYP_5)) {
                    caseData.setDataCountIssuTyp5(headerData.getDataCount()); // 筆數 - 各 核付分類 的筆數

                    caseData.setAmt2IssuTyp5(headerData.getAmt2()); // 交易金額 - 各 核付分類 的金額

                }
                // ]

                // 改匯相關資料 (issuTyp = A)
                // [
                if (StringUtils.equals(headerData.getIssuTyp(), ConstantKey.BAAPPBASE_CASETYP_A)) {
                    caseData.setDataCountIssuTypA(headerData.getDataCount()); // 筆數 - 各 核付分類 的筆數

                    caseData.setAmt2IssuTypA(headerData.getAmt2()); // 交易金額 - 各 核付分類 的金額

                }
                // ]
            }

            // 取得 Middle 資料
            List<Bagivetmpdtl> middleList = bagivetmpdtlDao.getMonthlyRpt07MiddleDataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), taTyp2, chkDate);
            List<MonthlyRpt07MiddleCase> middleCaseList = new ArrayList<MonthlyRpt07MiddleCase>();
            for (Bagivetmpdtl middleData : middleList) {
                MonthlyRpt07MiddleCase middleCase = new MonthlyRpt07MiddleCase();

                middleCase.setDataCount(middleData.getDataCount()); // 筆數 - 各 行庫局 的筆數

                middleCase.setAmt2(middleData.getAmt2()); // 交易金額 - 各 行庫局 的金額

                middleCase.setTaTyp2(middleData.getTaTyp2()); // 出帳類別
                middleCase.setHbank2(middleData.getHbank2()); // 總行代號

                middleCaseList.add(middleCase);
            }
            caseData.setMiddleList(middleCaseList);

            // 取得 Footer 資料
            List<Bagivetmpdtl> footerList = bagivetmpdtlDao.getMonthlyRpt07FooterDataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), taTyp2, chkDate);
            List<MonthlyRpt07FooterCase> footerCaseList = new ArrayList<MonthlyRpt07FooterCase>();
            for (Bagivetmpdtl footerData : footerList) {
                MonthlyRpt07FooterCase footerCase = new MonthlyRpt07FooterCase();

                footerCase.setDataCount(footerData.getDataCount()); // 筆數 - 各 給付種類 的筆數

                footerCase.setAmt2(footerData.getAmt2()); // 交易金額 - 各 給付種類 的金額

                footerCase.setTaTyp2(footerData.getTaTyp2()); // 出帳類別
                footerCase.setPayTyp2(footerData.getPayTyp2()); // 給付種類

                footerCaseList.add(footerCase);
            }
            caseData.setFooterList(footerCaseList);

            caseList.add(caseData);
        } // ] ... end for (String taTyp2 : taTyp2List)

        return caseList;
    }

    /**
     * 依傳入的條件取得 月核定給付撥款總額表 的資料（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt07Case> getMonthlyRptK07DataBy(String payCode, String issuYm, String chkDate, String paySeqNo) {
        List<MonthlyRpt07Case> caseList = new ArrayList<MonthlyRpt07Case>();

        // 依 給付別 及 核定年月 取得 出帳類別 清單
        List<String> taTyp2List = bagivetmpdtlDao.getMonthlyRptK07TaTyp2List(payCode, DateUtility.changeChineseYearMonthType(issuYm), chkDate, paySeqNo);

        // 依各 出帳類別 取得 MonthlyRpt07Case 的資料

        for (String taTyp2 : taTyp2List) { // begin ... [
            MonthlyRpt07Case caseData = new MonthlyRpt07Case();

            // 設定 給付別

            caseData.setPayCode(payCode);

            // 取得mFileName 資料
            List<Bagivetmpdtl> mFileNameList = bagivetmpdtlDao.getMonthlyRpt07mFileNameBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), taTyp2, chkDate);
            StringBuffer mFileName = new StringBuffer();
            for (int i = 0; i < mFileNameList.size(); i++) {
                if (i == mFileNameList.size() - 1) {
                    mFileName.append(mFileNameList.get(i).getMfileName());
                }
                else {
                    mFileName.append(mFileNameList.get(i).getMfileName() + "、");
                }
            }

            // 取得 Header 資料
            List<Bagivetmpdtl> headerList = bagivetmpdtlDao.getMonthlyRptK07HeaderDataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), taTyp2, chkDate, paySeqNo);
            for (Bagivetmpdtl headerData : headerList) {
                caseData.setTaTyp2(taTyp2); // 出帳類別
                // caseData.setMfileName(headerData.getMfileName()); // 媒體檔案名稱 - 檔案名稱
                caseData.setMfileName(mFileName.toString()); // 媒體檔案名稱 - 檔案名稱
                caseData.setPayDate2(headerData.getPayDate2()); // 實際核付日期 - 撥款日期

                // 新案相關資料 (issuTyp = 1)
                // [
                if (StringUtils.equals(headerData.getIssuTyp(), ConstantKey.BAAPPBASE_CASETYP_1)) {
                    caseData.setDataCountIssuTyp1(headerData.getDataCount()); // 筆數 - 各 核付分類 的筆數

                    caseData.setAmt2IssuTyp1(headerData.getAmt2()); // 交易金額 - 各 核付分類 的金額

                }
                // ]

                // 續發相關資料 (issuTyp = 2)
                // [
                if (StringUtils.equals(headerData.getIssuTyp(), ConstantKey.BAAPPBASE_CASETYP_2)) {
                    caseData.setDataCountIssuTyp2(headerData.getDataCount()); // 筆數 - 各 核付分類 的筆數

                    caseData.setAmt2IssuTyp2(headerData.getAmt2()); // 交易金額 - 各 核付分類 的金額

                }
                // ]

                // 結案相關資料 (issuTyp = 4)
                // [
                if (StringUtils.equals(headerData.getIssuTyp(), ConstantKey.BAAPPBASE_CASETYP_4)) {
                    caseData.setDataCountIssuTyp4(headerData.getDataCount()); // 筆數 - 各 核付分類 的筆數

                    caseData.setAmt2IssuTyp4(headerData.getAmt2()); // 交易金額 - 各 核付分類 的金額

                }
                // ]

                // 補發相關資料 (issuTyp = 5)
                // [
                if (StringUtils.equals(headerData.getIssuTyp(), ConstantKey.BAAPPBASE_CASETYP_5)) {
                    caseData.setDataCountIssuTyp5(headerData.getDataCount()); // 筆數 - 各 核付分類 的筆數

                    caseData.setAmt2IssuTyp5(headerData.getAmt2()); // 交易金額 - 各 核付分類 的金額

                }
                // ]

                // 改匯相關資料 (issuTyp = A)
                // [
                if (StringUtils.equals(headerData.getIssuTyp(), ConstantKey.BAAPPBASE_CASETYP_A)) {
                    caseData.setDataCountIssuTypA(headerData.getDataCount()); // 筆數 - 各 核付分類 的筆數

                    caseData.setAmt2IssuTypA(headerData.getAmt2()); // 交易金額 - 各 核付分類 的金額

                }
                // ]
            }

            // 取得 Middle 資料
            List<Bagivetmpdtl> middleList = bagivetmpdtlDao.getMonthlyRptK07MiddleDataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), taTyp2, chkDate, paySeqNo);
            List<MonthlyRpt07MiddleCase> middleCaseList = new ArrayList<MonthlyRpt07MiddleCase>();
            for (Bagivetmpdtl middleData : middleList) {
                MonthlyRpt07MiddleCase middleCase = new MonthlyRpt07MiddleCase();

                middleCase.setDataCount(middleData.getDataCount()); // 筆數 - 各 行庫局 的筆數

                middleCase.setAmt2(middleData.getAmt2()); // 交易金額 - 各 行庫局 的金額

                middleCase.setTaTyp2(middleData.getTaTyp2()); // 出帳類別
                middleCase.setHbank2(middleData.getHbank2()); // 總行代號

                middleCaseList.add(middleCase);
            }
            caseData.setMiddleList(middleCaseList);

            // 取得 Footer 資料
            List<Bagivetmpdtl> footerList = bagivetmpdtlDao.getMonthlyRptK07FooterDataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), taTyp2, chkDate, paySeqNo);
            List<MonthlyRpt07FooterCase> footerCaseList = new ArrayList<MonthlyRpt07FooterCase>();
            for (Bagivetmpdtl footerData : footerList) {
                MonthlyRpt07FooterCase footerCase = new MonthlyRpt07FooterCase();

                footerCase.setDataCount(footerData.getDataCount()); // 筆數 - 各 給付種類 的筆數

                footerCase.setAmt2(footerData.getAmt2()); // 交易金額 - 各 給付種類 的金額

                footerCase.setTaTyp2(footerData.getTaTyp2()); // 出帳類別
                footerCase.setPayTyp2(footerData.getPayTyp2()); // 給付種類

                footerCaseList.add(footerCase);
            }
            caseData.setFooterList(footerCaseList);

            caseList.add(caseData);
        } // ] ... end for (String taTyp2 : taTyp2List)

        return caseList;
    }

    /**
     * 依傳入的條件取得 月核定給付撥款總額表 的資料（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt07Case> getMonthlyRptS07DataBy(String payCode, String issuYm, String chkDate) {
        List<MonthlyRpt07Case> caseList = new ArrayList<MonthlyRpt07Case>();

        // 依 給付別 及 核定年月 取得 出帳類別 清單
        List<String> taTyp2List = bagivetmpdtlDao.getMonthlyRptS07TaTyp2List(payCode, DateUtility.changeChineseYearMonthType(issuYm), chkDate);

        // 依各 出帳類別 取得 MonthlyRpt07Case 的資料

        for (String taTyp2 : taTyp2List) { // begin ... [
            MonthlyRpt07Case caseData = new MonthlyRpt07Case();

            // 設定 給付別

            caseData.setPayCode(payCode);

            // 取得mFileName 資料
            List<Bagivetmpdtl> mFileNameList = bagivetmpdtlDao.getMonthlyRpt07mFileNameBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), taTyp2, chkDate);
            StringBuffer mFileName = new StringBuffer();
            for (int i = 0; i < mFileNameList.size(); i++) {
                if (i == mFileNameList.size() - 1) {
                    mFileName.append(mFileNameList.get(i).getMfileName());
                }
                else {
                    mFileName.append(mFileNameList.get(i).getMfileName() + "、");
                }
            }

            // 取得 Header 資料
            List<Bagivetmpdtl> headerList = bagivetmpdtlDao.getMonthlyRptS07HeaderDataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), taTyp2, chkDate);
            for (Bagivetmpdtl headerData : headerList) {
                caseData.setTaTyp2(taTyp2); // 出帳類別
                // caseData.setMfileName(headerData.getMfileName()); // 媒體檔案名稱 - 檔案名稱
                caseData.setMfileName(mFileName.toString()); // 媒體檔案名稱 - 檔案名稱
                caseData.setPayDate2(headerData.getPayDate2()); // 實際核付日期 - 撥款日期

                // 新案相關資料 (issuTyp = 1)
                // [
                if (StringUtils.equals(headerData.getIssuTyp(), ConstantKey.BAAPPBASE_CASETYP_1)) {
                    caseData.setDataCountIssuTyp1(headerData.getDataCount()); // 筆數 - 各 核付分類 的筆數

                    caseData.setAmt2IssuTyp1(headerData.getAmt2()); // 交易金額 - 各 核付分類 的金額

                }
                // ]

                // 續發相關資料 (issuTyp = 2)
                // [
                if (StringUtils.equals(headerData.getIssuTyp(), ConstantKey.BAAPPBASE_CASETYP_2)) {
                    caseData.setDataCountIssuTyp2(headerData.getDataCount()); // 筆數 - 各 核付分類 的筆數

                    caseData.setAmt2IssuTyp2(headerData.getAmt2()); // 交易金額 - 各 核付分類 的金額

                }
                // ]

                // 結案相關資料 (issuTyp = 4)
                // [
                if (StringUtils.equals(headerData.getIssuTyp(), ConstantKey.BAAPPBASE_CASETYP_4)) {
                    caseData.setDataCountIssuTyp4(headerData.getDataCount()); // 筆數 - 各 核付分類 的筆數

                    caseData.setAmt2IssuTyp4(headerData.getAmt2()); // 交易金額 - 各 核付分類 的金額

                }
                // ]

                // 補發相關資料 (issuTyp = 5)
                // [
                if (StringUtils.equals(headerData.getIssuTyp(), ConstantKey.BAAPPBASE_CASETYP_5)) {
                    caseData.setDataCountIssuTyp5(headerData.getDataCount()); // 筆數 - 各 核付分類 的筆數

                    caseData.setAmt2IssuTyp5(headerData.getAmt2()); // 交易金額 - 各 核付分類 的金額

                }
                // ]

                // 改匯相關資料 (issuTyp = A)
                // [
                if (StringUtils.equals(headerData.getIssuTyp(), ConstantKey.BAAPPBASE_CASETYP_A)) {
                    caseData.setDataCountIssuTypA(headerData.getDataCount()); // 筆數 - 各 核付分類 的筆數

                    caseData.setAmt2IssuTypA(headerData.getAmt2()); // 交易金額 - 各 核付分類 的金額

                }
                // ]
            }

            // 取得 Middle 資料
            List<Bagivetmpdtl> middleList = bagivetmpdtlDao.getMonthlyRptS07MiddleDataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), taTyp2, chkDate);
            List<MonthlyRpt07MiddleCase> middleCaseList = new ArrayList<MonthlyRpt07MiddleCase>();
            for (Bagivetmpdtl middleData : middleList) {
                MonthlyRpt07MiddleCase middleCase = new MonthlyRpt07MiddleCase();

                middleCase.setDataCount(middleData.getDataCount()); // 筆數 - 各 行庫局 的筆數

                middleCase.setAmt2(middleData.getAmt2()); // 交易金額 - 各 行庫局 的金額

                middleCase.setTaTyp2(middleData.getTaTyp2()); // 出帳類別
                middleCase.setHbank2(middleData.getHbank2()); // 總行代號

                middleCaseList.add(middleCase);
            }
            caseData.setMiddleList(middleCaseList);

            // 取得 Footer 資料
            List<Bagivetmpdtl> footerList = bagivetmpdtlDao.getMonthlyRptS07FooterDataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), taTyp2, chkDate);
            List<MonthlyRpt07FooterCase> footerCaseList = new ArrayList<MonthlyRpt07FooterCase>();
            for (Bagivetmpdtl footerData : footerList) {
                MonthlyRpt07FooterCase footerCase = new MonthlyRpt07FooterCase();

                footerCase.setDataCount(footerData.getDataCount()); // 筆數 - 各 給付種類 的筆數

                footerCase.setAmt2(footerData.getAmt2()); // 交易金額 - 各 給付種類 的金額

                footerCase.setTaTyp2(footerData.getTaTyp2()); // 出帳類別
                footerCase.setPayTyp2(footerData.getPayTyp2()); // 給付種類

                footerCaseList.add(footerCase);
            }
            caseData.setFooterList(footerCaseList);

            caseList.add(caseData);
        } // ] ... end for (String taTyp2 : taTyp2List)

        return caseList;
    }

    /*
     * public void insertBarptlogData(List<MonthlyRpt08Case> list) { barptlogDao.insertData(list); }
     */

    /**
     * 取得 月核定合格清冊 幾頁分一個檔
     * 
     * @return
     */
    public int getMonthlyRpt08Pages() {
        String pages = baparamDao.selectParamNameBy("1", "REPORT", "PAGING");

        if (pages != null) {
            return Integer.parseInt(pages);
        }

        return 0;
    }

    /**
     * 取得 批次核定函 幾筆apNo分一個檔
     * 
     * @return
     */
    public int getOtherRpt05Size() {
        String size = baparamDao.selectParamNameBy("1", "REPORT", "PAGING02");

        if (size != null) {
            return Integer.parseInt(size);
        }

        return 0;
    }

    /**
     * 依傳入的條件取得 月核定合格清冊 的資料（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt08Case> getMonthlyRpt08DataBy(String payCode, String issuYm, String chkDate) {
        List<MonthlyRpt08Case> caseList = new ArrayList<MonthlyRpt08Case>();
        // 取得報表資料
        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRpt08DataBy(payCode, issuYm, chkDate);

        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt08Case caseData = new MonthlyRpt08Case();
            BeanUtility.copyProperties(caseData, rptData);

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 月核定合格清冊 的資料（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt08Case> getMonthlyRptK08DataBy(String payCode, String issuYm, String chkDate, String paySeqNo, String isNullOrNot, String eqOrNot) {
        List<MonthlyRpt08Case> caseList = new ArrayList<MonthlyRpt08Case>();
        // 取得報表資料
        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRptK08DataBy(payCode, issuYm, chkDate, paySeqNo, isNullOrNot, eqOrNot);

        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt08Case caseData = new MonthlyRpt08Case();
            BeanUtility.copyProperties(caseData, rptData);

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 月核定合格清冊 的資料（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt08Case> getMonthlyRptS08DataBy(String payCode, String issuYm, String chkDate, String isNullOrNot, String eqOrNot) {
        List<MonthlyRpt08Case> caseList = new ArrayList<MonthlyRpt08Case>();
        // 取得報表資料
        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRptS08DataBy(payCode, issuYm, chkDate, isNullOrNot, eqOrNot);

        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt08Case caseData = new MonthlyRpt08Case();
            BeanUtility.copyProperties(caseData, rptData);

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 給付抵銷紓困貸款明細 的資料（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt09Case> getMonthlyRpt09DataBy(String payCode, String issuYm, String chkDate) {
        List<MonthlyRpt09Case> caseList = new ArrayList<MonthlyRpt09Case>();

        // 核定年月由民國轉成西元

        String sIssuYm = DateUtility.changeChineseYearMonthType(issuYm);
        String sChkDate = DateUtility.changeDateType(chkDate);

        // 取得報表資料
        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRpt09DataBy(payCode, sIssuYm, sChkDate);

        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt09Case caseData = new MonthlyRpt09Case();
            caseData.setPayCode(payCode); // 給付別

            caseData.setBranchId(rptData.getBranchId()); // 分行代號
            caseData.setPayEeacc(rptData.getPayEeacc()); // 銀行帳號 - 放款帳號
            caseData.setBenName(rptData.getBenName()); // 受益人姓名 - 姓名
            caseData.setBenIdn(rptData.getBenIdn()); // 受益人身分證號 - 身分證號
            caseData.setPayAmt(rptData.getPayAmt()); // 實付金額 - 抵銷金額
            caseData.setDlineDate(rptData.getDlineDate()); // 勞貸本息截止日 - 本息截止日
            caseData.setcPrnDate(rptData.getcPrnDate()); // 印表日期

            caseData.setPayDate(rptData.getPayDate()); // 核付日期

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 給付抵銷紓困貸款明細 的資料（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt09Case> getMonthlyRptK09DataBy(String payCode, String issuYm, String chkDate, String paySeqNo) {
        List<MonthlyRpt09Case> caseList = new ArrayList<MonthlyRpt09Case>();

        // 核定年月由民國轉成西元

        String sIssuYm = DateUtility.changeChineseYearMonthType(issuYm);
        String sChkDate = DateUtility.changeDateType(chkDate);

        // 取得報表資料
        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRptK09DataBy(payCode, sIssuYm, sChkDate, paySeqNo);

        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt09Case caseData = new MonthlyRpt09Case();
            caseData.setPayCode(payCode); // 給付別

            caseData.setBranchId(rptData.getBranchId()); // 分行代號
            caseData.setPayEeacc(rptData.getPayEeacc()); // 銀行帳號 - 放款帳號
            caseData.setBenName(rptData.getBenName()); // 受益人姓名 - 姓名
            caseData.setBenIdn(rptData.getBenIdn()); // 受益人身分證號 - 身分證號
            caseData.setPayAmt(rptData.getPayAmt()); // 實付金額 - 抵銷金額
            caseData.setDlineDate(rptData.getDlineDate()); // 勞貸本息截止日 - 本息截止日
            caseData.setcPrnDate(rptData.getcPrnDate()); // 印表日期

            caseData.setPayDate(rptData.getPayDate()); // 核付日期

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 給付抵銷紓困貸款明細 的資料（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt09Case> getMonthlyRptS09DataBy(String payCode, String issuYm, String chkDate) {
        List<MonthlyRpt09Case> caseList = new ArrayList<MonthlyRpt09Case>();

        // 核定年月由民國轉成西元

        String sIssuYm = DateUtility.changeChineseYearMonthType(issuYm);
        String sChkDate = DateUtility.changeDateType(chkDate);

        // 取得報表資料
        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRptS09DataBy(payCode, sIssuYm, sChkDate);

        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt09Case caseData = new MonthlyRpt09Case();
            caseData.setPayCode(payCode); // 給付別

            caseData.setBranchId(rptData.getBranchId()); // 分行代號
            caseData.setPayEeacc(rptData.getPayEeacc()); // 銀行帳號 - 放款帳號
            caseData.setBenName(rptData.getBenName()); // 受益人姓名 - 姓名
            caseData.setBenIdn(rptData.getBenIdn()); // 受益人身分證號 - 身分證號
            caseData.setPayAmt(rptData.getPayAmt()); // 實付金額 - 抵銷金額
            caseData.setDlineDate(rptData.getDlineDate()); // 勞貸本息截止日 - 本息截止日
            caseData.setcPrnDate(rptData.getcPrnDate()); // 印表日期

            caseData.setPayDate(rptData.getPayDate()); // 核付日期

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入條件取得 核付清單（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt10Type1RptCase> getMonthlyRpt10RptType1DataBy(String payCode, String issuYm, String chkDate, String[] benEvtRel, String eqType, String[] orderBy) {
        List<MonthlyRpt10Type1RptCase> caseList = new ArrayList<MonthlyRpt10Type1RptCase>();

        // 核定年月由民國轉成西元

        String sIssuYm = DateUtility.changeChineseYearMonthType(issuYm);
        String sChkDate = DateUtility.changeDateType(chkDate);

        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRpt10RptType1DataBy(payCode, sIssuYm, sChkDate, benEvtRel, eqType, orderBy);
        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt10Type1RptCase caseData = new MonthlyRpt10Type1RptCase();

            caseData.setPayCode(payCode); // 給付別

            caseData.setApNo(rptData.getApNo()); // 受理編號
            caseData.setIssuYm(rptData.getIssuYm()); // 核定年月
            caseData.setPayYm(rptData.getPayYm()); // 給付年月
            caseData.setBenIdn(rptData.getBenIdn()); // 受款人身份證號

            caseData.setBenName(rptData.getBenName()); // 受款人姓名

            caseData.setPayAmt(rptData.getPayAmt()); // 金額
            caseData.setChkDate(rptData.getChkDate()); // 核定日期
            caseData.setPayDate(rptData.getPayDate()); // 開票日期
            caseData.setPayTyp(rptData.getPayTyp()); // 給付方式
            caseData.setRptPage(rptData.getRptPage()); // 頁次
            caseData.setBenEvtRel(rptData.getBenEvtRel()); // 受益人與事故者關係

            caseData.setCompName1(rptData.getCompName1()); // 相關單位名稱1
            caseData.setAccountNo(rptData.getAccountNo()); // 帳號
            caseData.setPayTypName(rptData.getPayTypName()); // 給付方式中文名稱
            caseData.setCompName2(rptData.getCompName2()); // 相關單位名稱2
            caseData.setcPrnDate(rptData.getcPrnDate()); // 印表日期

            caseData.setPayBankId(rptData.getPayBankId()); // 入帳銀行 (總行)
            caseData.setBranchId(rptData.getBranchId()); // 入帳銀行 (分行)
            caseData.setPayEeacc(rptData.getPayEeacc()); // 入帳帳號

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入條件取得 核付清單（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt10Type1RptCase> getMonthlyRptK10RptType1DataBy(String payCode, String issuYm, String chkDate, String paySeqNo) {
        List<MonthlyRpt10Type1RptCase> caseList = new ArrayList<MonthlyRpt10Type1RptCase>();

        // 核定年月由民國轉成西元

        String sIssuYm = DateUtility.changeChineseYearMonthType(issuYm);
        String sChkDate = DateUtility.changeDateType(chkDate);

        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRptK10RptType1DataBy(payCode, sIssuYm, sChkDate, paySeqNo);
        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt10Type1RptCase caseData = new MonthlyRpt10Type1RptCase();

            caseData.setPayCode(payCode); // 給付別

            caseData.setApNo(rptData.getApNo()); // 受理編號
            caseData.setIssuYm(rptData.getIssuYm()); // 核定年月
            caseData.setPayYm(rptData.getPayYm()); // 給付年月
            caseData.setBenIdn(rptData.getBenIdn()); // 受款人身份證號

            caseData.setBenName(rptData.getBenName()); // 受款人姓名

            caseData.setPayAmt(rptData.getPayAmt()); // 金額
            caseData.setChkDate(rptData.getChkDate()); // 核定日期
            caseData.setPayDate(rptData.getPayDate()); // 開票日期
            caseData.setPayTyp(rptData.getPayTyp()); // 給付方式
            caseData.setRptPage(rptData.getRptPage()); // 頁次
            caseData.setBenEvtRel(rptData.getBenEvtRel()); // 受益人與事故者關係

            caseData.setCompName1(rptData.getCompName1()); // 相關單位名稱1
            caseData.setAccountNo(rptData.getAccountNo()); // 帳號
            caseData.setPayTypName(rptData.getPayTypName()); // 給付方式中文名稱
            caseData.setCompName2(rptData.getCompName2()); // 相關單位名稱2
            caseData.setcPrnDate(rptData.getcPrnDate()); // 印表日期

            caseData.setPayBankId(rptData.getPayBankId()); // 入帳銀行 (總行)
            caseData.setBranchId(rptData.getBranchId()); // 入帳銀行 (分行)
            caseData.setPayEeacc(rptData.getPayEeacc()); // 入帳帳號

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入條件取得 核付清單（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt10Type1RptCase> getMonthlyRptS10RptType1DataBy(String payCode, String issuYm, String chkDate) {
        List<MonthlyRpt10Type1RptCase> caseList = new ArrayList<MonthlyRpt10Type1RptCase>();

        // 核定年月由民國轉成西元

        String sIssuYm = DateUtility.changeChineseYearMonthType(issuYm);
        String sChkDate = DateUtility.changeDateType(chkDate);

        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRptS10RptType1DataBy(payCode, sIssuYm, sChkDate);
        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt10Type1RptCase caseData = new MonthlyRpt10Type1RptCase();

            caseData.setPayCode(payCode); // 給付別

            caseData.setApNo(rptData.getApNo()); // 受理編號
            caseData.setIssuYm(rptData.getIssuYm()); // 核定年月
            caseData.setPayYm(rptData.getPayYm()); // 給付年月
            caseData.setBenIdn(rptData.getBenIdn()); // 受款人身份證號

            caseData.setBenName(rptData.getBenName()); // 受款人姓名

            caseData.setPayAmt(rptData.getPayAmt()); // 金額
            caseData.setChkDate(rptData.getChkDate()); // 核定日期
            caseData.setPayDate(rptData.getPayDate()); // 開票日期
            caseData.setPayTyp(rptData.getPayTyp()); // 給付方式
            caseData.setRptPage(rptData.getRptPage()); // 頁次
            caseData.setBenEvtRel(rptData.getBenEvtRel()); // 受益人與事故者關係

            caseData.setCompName1(rptData.getCompName1()); // 相關單位名稱1
            caseData.setAccountNo(rptData.getAccountNo()); // 帳號
            caseData.setPayTypName(rptData.getPayTypName()); // 給付方式中文名稱
            caseData.setCompName2(rptData.getCompName2()); // 相關單位名稱2
            caseData.setcPrnDate(rptData.getcPrnDate()); // 印表日期

            caseData.setPayBankId(rptData.getPayBankId()); // 入帳銀行 (總行)
            caseData.setBranchId(rptData.getBranchId()); // 入帳銀行 (分行)
            caseData.setPayEeacc(rptData.getPayEeacc()); // 入帳帳號

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入條件取得 核付清單（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt10Type3RptCase> getMonthlyRpt10RptType3DataBy(String payCode, String issuYm, String chkDate, String[] benEvtRel, String eqType, String[] orderBy) {
        List<MonthlyRpt10Type3RptCase> caseList = new ArrayList<MonthlyRpt10Type3RptCase>();

        // 核定年月由民國轉成西元

        String sIssuYm = DateUtility.changeChineseYearMonthType(issuYm);
        String sChkDate = DateUtility.changeDateType(chkDate);

        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRpt10RptType3DataBy(payCode, sIssuYm, sChkDate, benEvtRel, eqType, orderBy);
        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt10Type3RptCase caseData = new MonthlyRpt10Type3RptCase();

            caseData.setPayCode(payCode); // 給付別

            caseData.setApNo(rptData.getApNo()); // 受理編號
            caseData.setIssuYm(rptData.getIssuYm()); // 核定年月
            caseData.setPayYm(rptData.getPayYm()); // 給付年月
            caseData.setBenIdn(rptData.getBenIdn()); // 受款人身份證號

            caseData.setBenName(rptData.getBenName()); // 受款人姓名

            caseData.setPayAmt(rptData.getPayAmt()); // 金額
            caseData.setChkDate(rptData.getChkDate()); // 核定日期
            caseData.setPayDate(rptData.getPayDate()); // 開票日期
            caseData.setPayTyp(rptData.getPayTyp()); // 給付方式
            caseData.setRptPage(rptData.getRptPage()); // 頁次
            caseData.setBenEvtRel(rptData.getBenEvtRel()); // 受益人與事故者關係

            caseData.setCompName1(rptData.getCompName1()); // 相關單位名稱1
            caseData.setAccountNo(rptData.getAccountNo()); // 帳號
            caseData.setPayTypName(rptData.getPayTypName()); // 給付方式中文名稱
            caseData.setCompName2(rptData.getCompName2()); // 相關單位名稱2
            caseData.setCommZip(rptData.getCommZip()); // 受款人地址郵遞區號

            caseData.setCommAddr(rptData.getCommAddr()); // 受款人地址
            caseData.setcPrnDate(rptData.getcPrnDate()); // 印表日期

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入條件取得 核付清單（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt10Type3RptCase> getMonthlyRptK10RptType3DataBy(String payCode, String issuYm, String chkDate, String paySeqNo) {
        List<MonthlyRpt10Type3RptCase> caseList = new ArrayList<MonthlyRpt10Type3RptCase>();

        // 核定年月由民國轉成西元

        String sIssuYm = DateUtility.changeChineseYearMonthType(issuYm);
        String sChkDate = DateUtility.changeDateType(chkDate);

        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRptK10RptType3DataBy(payCode, sIssuYm, sChkDate, paySeqNo);
        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt10Type3RptCase caseData = new MonthlyRpt10Type3RptCase();

            caseData.setPayCode(payCode); // 給付別

            caseData.setApNo(rptData.getApNo()); // 受理編號
            caseData.setIssuYm(rptData.getIssuYm()); // 核定年月
            caseData.setPayYm(rptData.getPayYm()); // 給付年月
            caseData.setBenIdn(rptData.getBenIdn()); // 受款人身份證號

            caseData.setBenName(rptData.getBenName()); // 受款人姓名

            caseData.setPayAmt(rptData.getPayAmt()); // 金額
            caseData.setChkDate(rptData.getChkDate()); // 核定日期
            caseData.setPayDate(rptData.getPayDate()); // 開票日期
            caseData.setPayTyp(rptData.getPayTyp()); // 給付方式
            caseData.setRptPage(rptData.getRptPage()); // 頁次
            caseData.setBenEvtRel(rptData.getBenEvtRel()); // 受益人與事故者關係

            caseData.setCompName1(rptData.getCompName1()); // 相關單位名稱1
            caseData.setAccountNo(rptData.getAccountNo()); // 帳號
            caseData.setPayTypName(rptData.getPayTypName()); // 給付方式中文名稱
            caseData.setCompName2(rptData.getCompName2()); // 相關單位名稱2
            caseData.setCommZip(rptData.getCommZip()); // 受款人地址郵遞區號

            caseData.setCommAddr(rptData.getCommAddr()); // 受款人地址
            caseData.setcPrnDate(rptData.getcPrnDate()); // 印表日期

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入條件取得 核付清單（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt10Type3RptCase> getMonthlyRptS10RptType3DataBy(String payCode, String issuYm, String chkDate) {
        List<MonthlyRpt10Type3RptCase> caseList = new ArrayList<MonthlyRpt10Type3RptCase>();

        // 核定年月由民國轉成西元

        String sIssuYm = DateUtility.changeChineseYearMonthType(issuYm);
        String sChkDate = DateUtility.changeDateType(chkDate);

        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRptS10RptType3DataBy(payCode, sIssuYm, sChkDate);
        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt10Type3RptCase caseData = new MonthlyRpt10Type3RptCase();

            caseData.setPayCode(payCode); // 給付別

            caseData.setApNo(rptData.getApNo()); // 受理編號
            caseData.setIssuYm(rptData.getIssuYm()); // 核定年月
            caseData.setPayYm(rptData.getPayYm()); // 給付年月
            caseData.setBenIdn(rptData.getBenIdn()); // 受款人身份證號

            caseData.setBenName(rptData.getBenName()); // 受款人姓名

            caseData.setPayAmt(rptData.getPayAmt()); // 金額
            caseData.setChkDate(rptData.getChkDate()); // 核定日期
            caseData.setPayDate(rptData.getPayDate()); // 開票日期
            caseData.setPayTyp(rptData.getPayTyp()); // 給付方式
            caseData.setRptPage(rptData.getRptPage()); // 頁次
            caseData.setBenEvtRel(rptData.getBenEvtRel()); // 受益人與事故者關係

            caseData.setCompName1(rptData.getCompName1()); // 相關單位名稱1
            caseData.setAccountNo(rptData.getAccountNo()); // 帳號
            caseData.setPayTypName(rptData.getPayTypName()); // 給付方式中文名稱
            caseData.setCompName2(rptData.getCompName2()); // 相關單位名稱2
            caseData.setCommZip(rptData.getCommZip()); // 受款人地址郵遞區號

            caseData.setCommAddr(rptData.getCommAddr()); // 受款人地址
            caseData.setcPrnDate(rptData.getcPrnDate()); // 印表日期

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入條件取得 核付清單 (代扣補償金)
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public HashMap<String, MonthlyRpt10Type1PayAmtCase> getMonthlyRpt10RptType1PayAmtDataBy(String payCode, String issuYm, String chkDate) {
        HashMap<String, MonthlyRpt10Type1PayAmtCase> payAmtMap = new HashMap<String, MonthlyRpt10Type1PayAmtCase>();

        // 核定年月由民國轉成西元

        String sIssuYm = DateUtility.changeChineseYearMonthType(issuYm);
        String sChkDate = DateUtility.changeDateType(chkDate);

        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRpt10RptType1PayAmtDataBy(payCode, sIssuYm, sChkDate);
        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt10Type1PayAmtCase payAmtData = new MonthlyRpt10Type1PayAmtCase();

            payAmtData.setCompName1(rptData.getCompName1()); // 相關單位名稱1
            payAmtData.setPayAmt(rptData.getPayAmt()); // 金額

            payAmtMap.put(StringUtils.trimToEmpty(payAmtData.getCompName1()), payAmtData);
        }

        return payAmtMap;
    }

    /**
     * 依傳入條件取得 核付明細表（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt10Type2RptCase> getMonthlyRpt10RptType2DataBy(String payCode, String issuYm, String chkDate, String[] benEvtRel, String eqType, String[] orderBy) {
        List<MonthlyRpt10Type2RptCase> caseList = new ArrayList<MonthlyRpt10Type2RptCase>();

        // 核定年月由民國轉成西元

        String sIssuYm = DateUtility.changeChineseYearMonthType(issuYm);
        String sChkDate = DateUtility.changeDateType(chkDate);

        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRpt10RptType2DataBy(payCode, sIssuYm, sChkDate, benEvtRel, eqType, orderBy);
        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt10Type2RptCase caseData = new MonthlyRpt10Type2RptCase();

            caseData.setPayCode(payCode); // 給付別

            caseData.setPayBankId(rptData.getPayBankId()); // 入帳銀行 (總行)
            caseData.setBranchId(rptData.getBranchId()); // 入帳銀行 (分行)
            caseData.setPayEeacc(rptData.getPayEeacc()); // 入帳帳號
            caseData.setPayAmt(rptData.getPayAmt()); // 交易金額
            caseData.setApNo(rptData.getApNo()); // 受理編號
            caseData.setIssuYm(rptData.getIssuYm()); // 核定年月
            caseData.setPayYm(rptData.getPayYm()); // 給付年月
            caseData.setBenIdn(rptData.getBenIdn()); // 身份證號碼

            caseData.setBenEvtRel(rptData.getBenEvtRel()); // 受益人與事故者關係

            caseData.setCompName1(rptData.getCompName1()); // 相關單位名稱1
            caseData.setCompName2(rptData.getCompName2()); // 申請代算單位

            caseData.setBenName(rptData.getBenName()); // 姓名
            caseData.setPayDate(rptData.getPayDate()); // 核付日期
            caseData.setcPrnDate(rptData.getcPrnDate()); // 印表日期
            caseData.setPayTyp(rptData.getPayTyp()); // 給付方式

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入條件取得 核付明細表（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt10Type2RptCase> getMonthlyRptK10RptType2DataBy(String payCode, String issuYm, String chkDate, String paySeqNo) {
        List<MonthlyRpt10Type2RptCase> caseList = new ArrayList<MonthlyRpt10Type2RptCase>();

        // 核定年月由民國轉成西元

        String sIssuYm = DateUtility.changeChineseYearMonthType(issuYm);
        String sChkDate = DateUtility.changeDateType(chkDate);

        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRptK10RptType2DataBy(payCode, sIssuYm, sChkDate, paySeqNo);
        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt10Type2RptCase caseData = new MonthlyRpt10Type2RptCase();

            caseData.setPayCode(payCode); // 給付別

            caseData.setPayBankId(rptData.getPayBankId()); // 入帳銀行 (總行)
            caseData.setBranchId(rptData.getBranchId()); // 入帳銀行 (分行)
            caseData.setPayEeacc(rptData.getPayEeacc()); // 入帳帳號
            caseData.setPayAmt(rptData.getPayAmt()); // 交易金額
            caseData.setApNo(rptData.getApNo()); // 受理編號
            caseData.setIssuYm(rptData.getIssuYm()); // 核定年月
            caseData.setPayYm(rptData.getPayYm()); // 給付年月
            caseData.setBenIdn(rptData.getBenIdn()); // 身份證號碼

            caseData.setBenEvtRel(rptData.getBenEvtRel()); // 受益人與事故者關係

            caseData.setCompName1(rptData.getCompName1()); // 相關單位名稱1
            caseData.setCompName2(rptData.getCompName2()); // 申請代算單位

            caseData.setBenName(rptData.getBenName()); // 姓名
            caseData.setPayDate(rptData.getPayDate()); // 核付日期
            caseData.setcPrnDate(rptData.getcPrnDate()); // 印表日期
            caseData.setPayTyp(rptData.getPayTyp()); // 給付方式

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入條件取得 核付明細表（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt10Type2RptCase> getMonthlyRptS10RptType2DataBy(String payCode, String issuYm, String chkDate) {
        List<MonthlyRpt10Type2RptCase> caseList = new ArrayList<MonthlyRpt10Type2RptCase>();

        // 核定年月由民國轉成西元

        String sIssuYm = DateUtility.changeChineseYearMonthType(issuYm);
        String sChkDate = DateUtility.changeDateType(chkDate);

        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRptS10RptType2DataBy(payCode, sIssuYm, sChkDate);
        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt10Type2RptCase caseData = new MonthlyRpt10Type2RptCase();

            caseData.setPayCode(payCode); // 給付別

            caseData.setPayBankId(rptData.getPayBankId()); // 入帳銀行 (總行)
            caseData.setBranchId(rptData.getBranchId()); // 入帳銀行 (分行)
            caseData.setPayEeacc(rptData.getPayEeacc()); // 入帳帳號
            caseData.setPayAmt(rptData.getPayAmt()); // 交易金額
            caseData.setApNo(rptData.getApNo()); // 受理編號
            caseData.setIssuYm(rptData.getIssuYm()); // 核定年月
            caseData.setPayYm(rptData.getPayYm()); // 給付年月
            caseData.setBenIdn(rptData.getBenIdn()); // 身份證號碼

            caseData.setBenEvtRel(rptData.getBenEvtRel()); // 受益人與事故者關係

            caseData.setCompName1(rptData.getCompName1()); // 相關單位名稱1
            caseData.setCompName2(rptData.getCompName2()); // 申請代算單位

            caseData.setBenName(rptData.getBenName()); // 姓名
            caseData.setPayDate(rptData.getPayDate()); // 核付日期
            caseData.setcPrnDate(rptData.getcPrnDate()); // 印表日期
            caseData.setPayTyp(rptData.getPayTyp()); // 給付方式

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入條件取得 核付明細表 (代扣補償金)
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public HashMap<String, MonthlyRpt10Type2PayAmtCase> getMonthlyRpt10RptType2PayAmtDataBy(String payCode, String issuYm, String chkDate) {
        HashMap<String, MonthlyRpt10Type2PayAmtCase> payAmtMap = new HashMap<String, MonthlyRpt10Type2PayAmtCase>();

        // 核定年月由民國轉成西元

        String sIssuYm = DateUtility.changeChineseYearMonthType(issuYm);
        String sChkDate = DateUtility.changeDateType(chkDate);

        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRpt10RptType2PayAmtDataBy(payCode, sIssuYm, sChkDate);
        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt10Type2PayAmtCase payAmtData = new MonthlyRpt10Type2PayAmtCase();

            payAmtData.setCompName1(rptData.getCompName1()); // 相關單位名稱1
            payAmtData.setPayAmt(rptData.getPayAmt()); // 金額

            payAmtMap.put(StringUtils.trimToEmpty(payAmtData.getCompName1()), payAmtData);
        }

        return payAmtMap;
    }

    /**
     * 依傳入的條件取得 退匯清冊 的資料
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt12Case> getMonthlyRpt12DataBy(String payCode, String issuYm, String chkDate) {
        List<MonthlyRpt12Case> caseList = new ArrayList<MonthlyRpt12Case>();
        // 取得報表資料
        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRpt12DataBy(payCode, issuYm, chkDate);

        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt12Case caseData = new MonthlyRpt12Case();
            BeanUtility.copyProperties(caseData, rptData);

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 退匯現應收已收清冊 的資料
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt26Case> getMonthlyRpt26DataBy(String rptTyp, String apNo, String chkDate, String seqNo, String paySeqNo) {
        List<MonthlyRpt26Case> caseList = new ArrayList<MonthlyRpt26Case>();
        // 取得報表資料
        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRpt26DataBy(rptTyp, apNo, chkDate, seqNo, paySeqNo);

        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt26Case caseData = new MonthlyRpt26Case();
            BeanUtility.copyProperties(caseData, rptData);

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 退匯現應收已收清冊 的資料
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt28Case> getMonthlyRpt28DataBy(String rptTyp, String apNo, String chkDate, String seqNo, String paySeqNo) {
        List<MonthlyRpt28Case> caseList = new ArrayList<MonthlyRpt28Case>();
        // 取得報表資料
        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRpt28DataBy(rptTyp, apNo, chkDate, seqNo, paySeqNo);

        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt28Case caseData = new MonthlyRpt28Case();
            BeanUtility.copyProperties(caseData, rptData);

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 補送在學證明通知函 08 09 B01 的資料
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<MonthlyRpt29Case> getMonthlyRpt29DataBy(String rptKind, String payCode, String studeChkMonth, String studeDate, String studeDateFormChk) {

        List<Baappbase> baappbaseList = new ArrayList<Baappbase>();
        if (StringUtils.equals(rptKind, "1")) {
            if (payCode.equals("K")) {
                baappbaseList = baappbaseDao.getMonthlyRpt29DisabledMDataBy();
            }
            else if (payCode.equals("S")) {
                baappbaseList = baappbaseDao.getMonthlyRpt29SurvivorMDataBy();
            }
        }
        else if (StringUtils.equals(rptKind, "2")) {
            if (payCode.equals("K")) {
                baappbaseList = baappbaseDao.getMonthlyRpt29DisabledYDataBy(studeChkMonth, studeDate, studeDateFormChk);
            }
            else if (payCode.equals("S")) {
                baappbaseList = baappbaseDao.getMonthlyRpt29SurvivorYDataBy(studeChkMonth, studeDate, studeDateFormChk);
            }
        }

        if (baappbaseList.size() == 0)
            return null;// 查無主檔資料

        List<MonthlyRpt29Case> caseList = new ArrayList<MonthlyRpt29Case>();

        for (Baappbase baappbase : baappbaseList) {

            // 承辦單位 連絡電話
            // Bbcmf09 bbcmf09 = bbcmf09Dao.selectComTelForMonthlyRpt05By(evtData.getApNo());
            // String title = "";
            // if(bbcmf09 != null){
            // if(bbcmf09.getSex().equals("1")) {
            // title = "先生";
            // }else if(bbcmf09.getSex().equals("2")){
            // title = "小姐";
            // }
            // }
            String unit = "";
            String comTel = "";
            // 承辦單位 連絡電話
            Bbcmf09 bbcmf09 = bbcmf09Dao.selectComTelForMonthlyRpt05By(baappbase.getApNo());

            if ("K".equalsIgnoreCase(baappbase.getPayType())) {
                unit = "職業災害給付組失能給付科";
                if (bbcmf09 != null) {
                    comTel = "(02)23961266轉" + bbcmf09.getData2();
                }

            }
            else if ("S".equalsIgnoreCase(baappbase.getPayType())) {
                unit = "職業災害給付組死亡給付科";
                if (bbcmf09 != null) {
                    comTel = "(02)23961266轉" + bbcmf09.getData2();
                }
            }

            // 總經理名稱
            String manager = bapasignDao.selectManager(DateUtility.getNowWestDate());

            MonthlyRpt29Case MonthlyRpt29Case = new MonthlyRpt29Case();

            // B01 細項
            Banotify banotify31 = new Banotify();
            banotify31.setDataCont(MonthlyRpt29Case.getDataCont1());
            Banotify banotify32 = new Banotify();
            if (StringUtils.equalsIgnoreCase(baappbase.getPayType(), "K")) {
                banotify32.setDataCont(MonthlyRpt29Case.getDataCont2ForK());
            }
            else {
                banotify32.setDataCont(MonthlyRpt29Case.getDataCont2());
            }
            Banotify banotify33 = new Banotify();
            banotify33.setDataCont(MonthlyRpt29Case.getDataCont3());
            Banotify banotify34 = new Banotify();
            banotify34.setDataCont(MonthlyRpt29Case.getDataCont4());
            Banotify banotify35 = new Banotify();
            banotify35.setDataCont(MonthlyRpt29Case.getDataCont5());

            // 傳入固定主旨&說明 目前寫死 之後如改成抓資料庫這樣較方便
            List<Banotify> banotifyList = new ArrayList<Banotify>();
            if (StringUtils.equalsIgnoreCase(rptKind, "1") && StringUtils.equalsIgnoreCase(baappbase.getPayType(), "K")) {
                Banotify banotify1 = new Banotify();
                banotify1.setDataCont(MonthlyRpt29Case.getDataContMK1());
                Banotify banotify2 = new Banotify();
                banotify2.setDataCont(MonthlyRpt29Case.getDataContMK2());
                Banotify banotify3 = new Banotify();
                banotify3.setDataCont(MonthlyRpt29Case.getDataContMK3());
                Banotify banotify4 = new Banotify();
                banotify4.setDataCont(MonthlyRpt29Case.getDataContMK4());
                banotifyList.add(banotify1);
                banotifyList.add(banotify2);
                banotifyList.add(banotify3);
                banotifyList.add(banotify31);
                banotifyList.add(banotify32);
                banotifyList.add(banotify33);
                banotifyList.add(banotify34);
                banotifyList.add(banotify4);
            }
            else if (StringUtils.equalsIgnoreCase(rptKind, "1") && StringUtils.equalsIgnoreCase(baappbase.getPayType(), "S")) {
                Banotify banotify1 = new Banotify();
                banotify1.setDataCont(MonthlyRpt29Case.getDataContMS1());
                Banotify banotify2 = new Banotify();
                banotify2.setDataCont(MonthlyRpt29Case.getDataContMS2());
                Banotify banotify3 = new Banotify();
                banotify3.setDataCont(MonthlyRpt29Case.getDataContMS3());
                Banotify banotify4 = new Banotify();
                banotify4.setDataCont(MonthlyRpt29Case.getDataContMS4());
                banotifyList.add(banotify1);
                banotifyList.add(banotify2);
                banotifyList.add(banotify3);
                banotifyList.add(banotify31);
                banotifyList.add(banotify32);
                banotifyList.add(banotify33);
                banotifyList.add(banotify34);
                banotifyList.add(banotify4);
            }
            else if (StringUtils.equalsIgnoreCase(rptKind, "2") && StringUtils.equalsIgnoreCase(baappbase.getPayType(), "K")) {
                Banotify banotify1 = new Banotify();
                banotify1.setDataCont(MonthlyRpt29Case.getDataContYK1());
                Banotify banotify2 = new Banotify();
                banotify2.setDataCont(MonthlyRpt29Case.getDataContYK2());
                Banotify banotify3 = new Banotify();
                banotify3.setDataCont(MonthlyRpt29Case.getDataContYK3());
                Banotify banotify4 = new Banotify();
                banotify4.setDataCont(MonthlyRpt29Case.getDataContYK4());
                banotifyList.add(banotify1);
                banotifyList.add(banotify2);
                banotifyList.add(banotify3);
                banotifyList.add(banotify31);
                banotifyList.add(banotify32);
                banotifyList.add(banotify33);
                banotifyList.add(banotify34);
                banotifyList.add(banotify4);
            }
            else if (StringUtils.equalsIgnoreCase(rptKind, "2") && StringUtils.equalsIgnoreCase(baappbase.getPayType(), "S")) {
                Banotify banotify1 = new Banotify();
                banotify1.setDataCont(MonthlyRpt29Case.getDataContYS1());
                Banotify banotify2 = new Banotify();
                banotify2.setDataCont(MonthlyRpt29Case.getDataContYS2());
                Banotify banotify3 = new Banotify();
                banotify3.setDataCont(MonthlyRpt29Case.getDataContYS3());
                Banotify banotify4 = new Banotify();
                banotify4.setDataCont(MonthlyRpt29Case.getDataContYS4());
                banotifyList.add(banotify1);
                banotifyList.add(banotify2);
                banotifyList.add(banotify3);
                banotifyList.add(banotify31);
                banotifyList.add(banotify32);
                banotifyList.add(banotify33);
                banotifyList.add(banotify34);
                banotifyList.add(banotify35); // 年度 細項第5點
                banotifyList.add(banotify4);
            }

            BaReportReplaceUtility strReplace = new BaReportReplaceUtility(baappbase, false, rptKind, false, null);
            List<String> message = new ArrayList<String>();
            for (Banotify banotify : banotifyList) {
                message.add(strReplace.replace(banotify.getDataCont()));
            }

            // 報表發文字號
            MonthlyRpt29Case.setWordNo(baappbase.getApNo());
            // 報表發文日期 發文日期：以系統日期加5個工作日列印。
            MonthlyRpt29Case.setWordDate(DateUtility.formatChineseDateString(DateUtility.calDay(DateUtility.getNowChineseDate(), 5), true));
            // 報表承辦單位
            MonthlyRpt29Case.setUnit(unit);
            // 報表連絡電話
            MonthlyRpt29Case.setComTel(comTel);
            // 報表受文者 受款人為自然人時要印先生 女士
            if (StringUtils.equalsIgnoreCase(baappbase.getBenSex(), "1")) {
                MonthlyRpt29Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()) + " 先生");
            }
            else if (StringUtils.equalsIgnoreCase(baappbase.getBenSex(), "2")) {
                MonthlyRpt29Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()) + " 女士");
            }
            else {
                MonthlyRpt29Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()) + " 君");
            }

            // 地址
            MonthlyRpt29Case.setAddrZip(StringUtils.trimToEmpty(baappbase.getCommZip()));
            MonthlyRpt29Case.setAddr(StringUtils.trimToEmpty(baappbase.getCommAddr()));

            MonthlyRpt29Case.setMessage(message);

            MonthlyRpt29Case.setManager(manager);

            caseList.add(MonthlyRpt29Case);

            // 寫入在學證明通知函記錄檔（BASTUDNOTIFY）
            Bastudnotify bastudnotify = new Bastudnotify();
            bastudnotify.setNotifyType(rptKind);
            bastudnotify.setApNo(baappbase.getApNo());
            bastudnotify.setSeqNo(baappbase.getSeqNo());
            bastudnotify.setEvtName(baappbase.getEvtName());
            bastudnotify.setBenName(baappbase.getBenName());
            bastudnotify.setStudeDate(studeDateFormChk);
            bastudnotifyDao.insertBastudnotify(bastudnotify);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 查核失能程度通知函 的資料
     * 
     * @param rptKind 報表類別
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt30Case> getMonthlyRpt30DataBy(String rptKind, String payCode, String issuYm) {

        List<Baappbase> baappbaseList = new ArrayList<Baappbase>();
        if (StringUtils.equals(rptKind, "1")) {
            baappbaseList = baappbaseDao.getMonthlyRpt30K01DataBy(payCode, issuYm);

        }
        else if (StringUtils.equals(rptKind, "2")) {
            baappbaseList = baappbaseDao.getMonthlyRpt30K02DataBy(payCode, issuYm);

        }

        if (baappbaseList.size() == 0)
            return null;// 查無主檔資料

        List<MonthlyRpt30Case> caseList = new ArrayList<MonthlyRpt30Case>();

        for (Baappbase baappbase : baappbaseList) {

            String unit = "";
            String comTel = "";
            // 承辦單位 連絡電話
            Bbcmf09 bbcmf09 = bbcmf09Dao.selectComTelForMonthlyRpt05By(baappbase.getApNo());

            if ("K".equalsIgnoreCase(baappbase.getPayType())) {
                unit = "職業災害給付組失能給付科";
                if (bbcmf09 != null) {
                    comTel = "(02)23961266轉" + bbcmf09.getData2();
                }
            }

            // 總經理名稱
            String manager = bapasignDao.selectManager(DateUtility.getNowWestDate());

            MonthlyRpt30Case MonthlyRpt30Case = new MonthlyRpt30Case();

            // 細項
            Banotify banotify31 = new Banotify();
            Banotify banotify32 = new Banotify();

            if (StringUtils.equalsIgnoreCase(rptKind, "1")) {
                banotify31.setDataCont(MonthlyRpt30Case.getDataContK0101());

                if (baappbase.getNotifyForm().equals("C01") || baappbase.getNotifyForm().equals("C03")) {
                    banotify32.setDataCont(MonthlyRpt30Case.getDataContK0102());
                }
                else {
                    banotify32.setDataCont(MonthlyRpt30Case.getDataContK0103());
                }
            }

            // 傳入固定主旨&說明 目前寫死 之後如改成抓資料庫這樣較方便
            List<Banotify> banotifyList = new ArrayList<Banotify>();
            if (StringUtils.equalsIgnoreCase(rptKind, "1") && baappbase.getNotifyForm().equals("C01")) {
                Banotify banotify1 = new Banotify();
                banotify1.setDataCont(MonthlyRpt30Case.getDataContK01C0101());
                Banotify banotify2 = new Banotify();
                banotify2.setDataCont(MonthlyRpt30Case.getDataContK01C0102());
                Banotify banotify3 = new Banotify();
                banotify3.setDataCont(MonthlyRpt30Case.getDataContK01C0103());
                Banotify banotify4 = new Banotify();
                banotify4.setDataCont(MonthlyRpt30Case.getDataContK01C0104());
                banotifyList.add(banotify1);
                banotifyList.add(banotify2);
                banotifyList.add(banotify3);
                banotifyList.add(banotify31);
                banotifyList.add(banotify32);
                banotifyList.add(banotify4);
            }
            else if (StringUtils.equalsIgnoreCase(rptKind, "1") && baappbase.getNotifyForm().equals("C02")) {
                Banotify banotify1 = new Banotify();
                banotify1.setDataCont(MonthlyRpt30Case.getDataContK01C0201());
                Banotify banotify2 = new Banotify();
                banotify2.setDataCont(MonthlyRpt30Case.getDataContK01C0202());
                Banotify banotify3 = new Banotify();
                banotify3.setDataCont(MonthlyRpt30Case.getDataContK01C0203());
                Banotify banotify4 = new Banotify();
                banotify4.setDataCont(MonthlyRpt30Case.getDataContK01C0204());
                banotifyList.add(banotify1);
                banotifyList.add(banotify2);
                banotifyList.add(banotify3);
                banotifyList.add(banotify31);
                banotifyList.add(banotify32);
                banotifyList.add(banotify4);
            }
            else if (StringUtils.equalsIgnoreCase(rptKind, "1") && baappbase.getNotifyForm().equals("C03")) {
                Banotify banotify1 = new Banotify();
                banotify1.setDataCont(MonthlyRpt30Case.getDataContK01C0301());
                Banotify banotify2 = new Banotify();
                banotify2.setDataCont(MonthlyRpt30Case.getDataContK01C0302());
                Banotify banotify3 = new Banotify();
                banotify3.setDataCont(MonthlyRpt30Case.getDataContK01C0303());
                Banotify banotify4 = new Banotify();
                banotify4.setDataCont(MonthlyRpt30Case.getDataContK01C0304());
                banotifyList.add(banotify1);
                banotifyList.add(banotify2);
                banotifyList.add(banotify3);
                banotifyList.add(banotify31);
                banotifyList.add(banotify32);
                banotifyList.add(banotify4);
            }
            else if (StringUtils.equalsIgnoreCase(rptKind, "1") && baappbase.getNotifyForm().equals("C04")) {
                Banotify banotify1 = new Banotify();
                banotify1.setDataCont(MonthlyRpt30Case.getDataContK01C0401());
                Banotify banotify2 = new Banotify();
                banotify2.setDataCont(MonthlyRpt30Case.getDataContK01C0402());
                Banotify banotify3 = new Banotify();
                banotify3.setDataCont(MonthlyRpt30Case.getDataContK01C0403());
                Banotify banotify4 = new Banotify();
                banotify4.setDataCont(MonthlyRpt30Case.getDataContK01C0404());
                banotifyList.add(banotify1);
                banotifyList.add(banotify2);
                banotifyList.add(banotify3);
                banotifyList.add(banotify31);
                banotifyList.add(banotify32);
                banotifyList.add(banotify4);
            }
            else if (StringUtils.equalsIgnoreCase(rptKind, "2") && baappbase.getNotifyForm().equals("C05")) {
                Banotify banotify1 = new Banotify();
                banotify1.setDataCont(MonthlyRpt30Case.getDataContK02C0501());
                Banotify banotify2 = new Banotify();
                banotify2.setDataCont(MonthlyRpt30Case.getDataContK02C0502());
                Banotify banotify3 = new Banotify();
                banotify3.setDataCont(MonthlyRpt30Case.getDataContK02C0503());
                banotifyList.add(banotify1);
                banotifyList.add(banotify2);
                banotifyList.add(banotify3);
            }
            else if (StringUtils.equalsIgnoreCase(rptKind, "2") && baappbase.getNotifyForm().equals("C06")) {
                Banotify banotify1 = new Banotify();
                banotify1.setDataCont(MonthlyRpt30Case.getDataContK02C0601());
                Banotify banotify2 = new Banotify();
                banotify2.setDataCont(MonthlyRpt30Case.getDataContK02C0602());
                Banotify banotify3 = new Banotify();
                banotify3.setDataCont(MonthlyRpt30Case.getDataContK02C0603());
                banotifyList.add(banotify1);
                banotifyList.add(banotify2);
                banotifyList.add(banotify3);
            }

            BaReportReplaceUtility strReplace = new BaReportReplaceUtility(baappbase, false, rptKind, false, null);
            List<String> message = new ArrayList<String>();
            for (Banotify banotify : banotifyList) {
                message.add(strReplace.replace(banotify.getDataCont()));
            }

            // 報表發文字號
            MonthlyRpt30Case.setWordNo(baappbase.getApNo());

            // 報表發文日期
            if (baappbase.getNotifyForm().equals("C05") || baappbase.getNotifyForm().equals("C06")) {
                // 報表發文日期 發文日期：以系統日期加5個工作日列印。
                // MonthlyRpt30Case.setWordDate(DateUtility.formatChineseDateString(DateUtility.calDay(DateUtility.getNowChineseDate(), 5), true));
                String wordDate = DateUtility.getNowWestDate();
                int workDay = 0;
                while (workDay < 5) {
                    wordDate = DateUtility.calDay(wordDate, 1);
                    if (!sdholidayDao.checkHoliday(wordDate)) {
                        workDay++;
                    }
                }
                MonthlyRpt30Case.setWordDate(DateUtility.formatChineseDateString(wordDate, true));
            }
            else {
                // 報表發文日期 發文日期：以系統日期加7個工作日列印。
                // MonthlyRpt30Case.setWordDate(DateUtility.formatChineseDateString(DateUtility.calDay(DateUtility.getNowChineseDate(), 7), true));
                String wordDate = DateUtility.getNowWestDate();
                int workDay = 0;
                while (workDay < 7) {
                    wordDate = DateUtility.calDay(wordDate, 1);
                    if (!sdholidayDao.checkHoliday(wordDate)) {
                        workDay++;
                    }
                }
                MonthlyRpt30Case.setWordDate(DateUtility.formatChineseDateString(wordDate, true));
            }
            // 報表承辦單位
            MonthlyRpt30Case.setUnit(unit);
            // 報表連絡電話
            MonthlyRpt30Case.setComTel(comTel);

            // 報表受文者 受款人為自然人時要印先生 女士
            // (1)當該案「法定代理人姓名」為空白時，以事故者之姓名帶入。
            // (2)當該案「法定代理人姓名」不為空白時，以法定代理人姓名+(事故者姓名之法定代理人)帶入。

            if (StringUtils.isBlank(baappbase.getGrdName())) {
                if (StringUtils.equalsIgnoreCase(baappbase.getBenSex(), "1")) {
                    MonthlyRpt30Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()) + " 先生");
                }
                else if (StringUtils.equalsIgnoreCase(baappbase.getBenSex(), "2")) {
                    MonthlyRpt30Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()) + " 女士");
                }
                else {
                    MonthlyRpt30Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()) + " 君");
                }
            }
            else {
                String sBenName = "";
                if (StringUtils.equalsIgnoreCase(baappbase.getBenSex(), "1")) {
                    sBenName = "（" + StringUtils.trimToEmpty(baappbase.getBenName()) + "之法定代理人）";
                }
                else if (StringUtils.equalsIgnoreCase(baappbase.getBenSex(), "2")) {
                    sBenName = "（" + StringUtils.trimToEmpty(baappbase.getBenName()) + "之法定代理人）";
                }
                else {
                    sBenName = "（" + StringUtils.trimToEmpty(baappbase.getBenName()) + "之法定代理人）";
                }

                if (StringUtils.equalsIgnoreCase(StringUtils.substring(baappbase.getGrdIdnNo().trim(), 1, 2), "1")) {
                    MonthlyRpt30Case.setName(StringUtils.trimToEmpty(baappbase.getGrdName()) + " 先生" + sBenName);
                }
                else if (StringUtils.equalsIgnoreCase(StringUtils.substring(baappbase.getGrdIdnNo().trim(), 1, 2), "2")) {
                    MonthlyRpt30Case.setName(StringUtils.trimToEmpty(baappbase.getGrdName()) + " 女士" + sBenName);
                }
                else {
                    MonthlyRpt30Case.setName(StringUtils.trimToEmpty(baappbase.getGrdName()) + " 君" + sBenName);
                }
            }

            // 地址
            MonthlyRpt30Case.setAddrZip(StringUtils.trimToEmpty(baappbase.getCommZip()));
            MonthlyRpt30Case.setAddr(StringUtils.trimToEmpty(baappbase.getCommAddr()));

            MonthlyRpt30Case.setMessage(message);

            MonthlyRpt30Case.setManager(manager);

            caseList.add(MonthlyRpt30Case);

        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 老年差額金通知 的資料
     * 
     * @param rptKind 報表類別
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return
     */
    public List<MonthlyRpt31Case> getMonthlyRpt31DataBy(String rptKind, String payCode, String apNo, String issuYm, UserBean userData, HashMap<String, Object> map) {

        String sProgId = (String) map.get("progId");
        if (sProgId.equals("BALP0D570L")) {
            // 【批次通知函列印】原本 map 中寫入的 rptKind 值為 Form 上面的，但程式執行必需使用該筆資料真正的通知函種類
            map.put("rptKind", rptKind);
        }

        List<Baappbase> baappbaseList = new ArrayList<Baappbase>();
        if (StringUtils.equals(rptKind, "001")) { // 第一次通知函
            baappbaseList = baappbaseDao.getMonthlyRpt31001FDataBy(payCode, apNo);
        }else if (StringUtils.equals(rptKind, "002") || StringUtils.equals(rptKind, "003")) {
            baappbaseList = baappbaseDao.getMonthlyRpt31002And31003FDataBy(payCode, apNo);
        }else if (StringUtils.equals(rptKind, "001U")) { // 第二次通知函（催辦）
            baappbaseList = baappbaseDao.getMonthlyRpt31001UDataBy(payCode, apNo, issuYm);
        }else if (StringUtils.equals(rptKind, "002U")) {
            baappbaseList = baappbaseDao.getMonthlyRpt31002UDataBy(payCode, apNo, issuYm);
        }else if (StringUtils.equals(rptKind, "003U")) {
            baappbaseList = baappbaseDao.getMonthlyRpt31003UDataBy(payCode, apNo, issuYm);
        }else if (StringUtils.equals(rptKind, "001P")) { // 第三次通知函（延不補正）
            baappbaseList = baappbaseDao.getMonthlyRpt31001PDataBy(payCode, apNo, issuYm);
        }else if (StringUtils.equals(rptKind, "002P")) {
            baappbaseList = baappbaseDao.getMonthlyRpt31002PDataBy(payCode, apNo, issuYm);
        }else if (StringUtils.equals(rptKind, "003P")) {
            baappbaseList = baappbaseDao.getMonthlyRpt31003PDataBy(payCode, apNo, issuYm);
        }

        if (baappbaseList.size() == 0)
            return null;// 查無主檔資料

        List<MonthlyRpt31Case> caseList = new ArrayList<MonthlyRpt31Case>();

        for (Baappbase baappbase : baappbaseList) {

            String unit = "";
            String comTel = "";
            String title = "";
            // 承辦單位 連絡電話
            Bbcmf09 bbcmf09 = bbcmf09Dao.selectComTelForMonthlyRpt05By(baappbase.getApNo());

            if (bbcmf09 != null) {
                if (bbcmf09.getSex().equals("1")) {
                    title = "先生";
                }
                else if (bbcmf09.getSex().equals("2")) {
                    title = "小姐";
                }
            }

            if ("L".equalsIgnoreCase(payCode)) {
                unit = "普通事故給付組老年給付科";
                if (bbcmf09 != null) {
                    comTel = bbcmf09.getfName() + title + "(02)23961266轉" + bbcmf09.getData2();
                }
            }

            // 總經理名稱
            String manager = bapasignDao.selectManager(DateUtility.getNowWestDate());

            MonthlyRpt31Case MonthlyRpt31Case = new MonthlyRpt31Case();

            // 細項
            Banotify banotify41 = new Banotify();
            Banotify banotify42 = new Banotify();
            Banotify banotify51 = new Banotify();
            Banotify banotify52 = new Banotify();
            Banotify banotify61 = new Banotify();
            Banotify banotify62 = new Banotify();

            if (StringUtils.equalsIgnoreCase(rptKind, "002") || StringUtils.equalsIgnoreCase(rptKind, "002U") || StringUtils.equalsIgnoreCase(rptKind, "002P")) {
                banotify51.setDataCont(MonthlyRpt31Case.getDataContSubitemL00201());
                banotify52.setDataCont(MonthlyRpt31Case.getDataContSubitemL00202());
                banotify61.setDataCont(MonthlyRpt31Case.getDataContSubitemL00203());
                banotify62.setDataCont(MonthlyRpt31Case.getDataContSubitemL00204());
            }
            else if (StringUtils.equalsIgnoreCase(rptKind, "003") || StringUtils.equalsIgnoreCase(rptKind, "003U") || StringUtils.equalsIgnoreCase(rptKind, "003P")) {
                banotify41.setDataCont(MonthlyRpt31Case.getDataContSubitemL00301());
                banotify42.setDataCont(MonthlyRpt31Case.getDataContSubitemL00302());
                banotify51.setDataCont(MonthlyRpt31Case.getDataContSubitemL00303());
                banotify52.setDataCont(MonthlyRpt31Case.getDataContSubitemL00304());
            }

            // 傳入固定主旨&說明 目前寫死 之後如改成抓資料庫這樣較方便
            List<Banotify> banotifyList = new ArrayList<Banotify>();
            if (StringUtils.equalsIgnoreCase(rptKind, "001")) { // 第一次通知函
                Banotify banotify1 = new Banotify();
                banotify1.setDataCont(MonthlyRpt31Case.getDataContL00101()); // 主旨
                Banotify banotify2 = new Banotify();
                banotify2.setDataCont(MonthlyRpt31Case.getDataContL00102());
                Banotify banotify3 = new Banotify();
                banotify3.setDataCont(MonthlyRpt31Case.getDataContL00103());
                Banotify banotify4 = new Banotify();
                banotify4.setDataCont(MonthlyRpt31Case.getDataContL00104());
                Banotify banotify5 = new Banotify();
                banotify5.setDataCont(MonthlyRpt31Case.getDataContL00105());
                Banotify banotify6 = new Banotify();
                banotify6.setDataCont(MonthlyRpt31Case.getDataContL00106());
                banotifyList.add(banotify1);
                banotifyList.add(banotify2);
                banotifyList.add(banotify3);
                banotifyList.add(banotify4);
                banotifyList.add(banotify5);
                banotifyList.add(banotify6);
            }
            else if (StringUtils.equalsIgnoreCase(rptKind, "002")) {
                Banotify banotify1 = new Banotify();
                banotify1.setDataCont(MonthlyRpt31Case.getDataContL00201()); // 主旨
                Banotify banotify2 = new Banotify();
                banotify2.setDataCont(MonthlyRpt31Case.getDataContL00202());
                Banotify banotify3 = new Banotify();
                banotify3.setDataCont(MonthlyRpt31Case.getDataContL00203());
                Banotify banotify4 = new Banotify();
                banotify4.setDataCont(MonthlyRpt31Case.getDataContL00204());
                Banotify banotify5 = new Banotify();
                banotify5.setDataCont(MonthlyRpt31Case.getDataContL00205());
                Banotify banotify6 = new Banotify();
                banotify6.setDataCont(MonthlyRpt31Case.getDataContL00206());
                Banotify banotify7 = new Banotify();
                banotify7.setDataCont(MonthlyRpt31Case.getDataContL00207());
                banotifyList.add(banotify1);
                banotifyList.add(banotify2);
                banotifyList.add(banotify3);
                banotifyList.add(banotify4);
                banotifyList.add(banotify5);
                banotifyList.add(banotify51);
                banotifyList.add(banotify52);
                banotifyList.add(banotify6);
                banotifyList.add(banotify61);
                banotifyList.add(banotify62);
                banotifyList.add(banotify7);
            }
            else if (StringUtils.equalsIgnoreCase(rptKind, "003")) {
                Banotify banotify1 = new Banotify();
                banotify1.setDataCont(MonthlyRpt31Case.getDataContL00301()); // 主旨
                Banotify banotify2 = new Banotify();
                banotify2.setDataCont(MonthlyRpt31Case.getDataContL00302());
                Banotify banotify3 = new Banotify();
                banotify3.setDataCont(MonthlyRpt31Case.getDataContL00303());
                Banotify banotify4 = new Banotify();
                banotify4.setDataCont(MonthlyRpt31Case.getDataContL00304());
                Banotify banotify5 = new Banotify();
                banotify5.setDataCont(MonthlyRpt31Case.getDataContL00305());
                Banotify banotify6 = new Banotify();
                banotify6.setDataCont(MonthlyRpt31Case.getDataContL00306());
                banotifyList.add(banotify1);
                banotifyList.add(banotify2);
                banotifyList.add(banotify3);
                banotifyList.add(banotify4);
                banotifyList.add(banotify41);
                banotifyList.add(banotify42);
                banotifyList.add(banotify5);
                banotifyList.add(banotify51);
                banotifyList.add(banotify52);
                banotifyList.add(banotify6);
            }
            else if (StringUtils.equalsIgnoreCase(rptKind, "001U")) { // 第二次通知函（催辦）
                Banotify banotify1 = new Banotify();
                banotify1.setDataCont(MonthlyRpt31Case.getDataContL001U01()); // 主旨
                Banotify banotify2 = new Banotify();
                banotify2.setDataCont(MonthlyRpt31Case.getDataContL00102());
                Banotify banotify3 = new Banotify();
                banotify3.setDataCont(MonthlyRpt31Case.getDataContL00103());
                Banotify banotify4 = new Banotify();
                banotify4.setDataCont(MonthlyRpt31Case.getDataContL00104());
                Banotify banotify5 = new Banotify();
                banotify5.setDataCont(MonthlyRpt31Case.getDataContL00105());
                Banotify banotify6 = new Banotify();
                banotify6.setDataCont(MonthlyRpt31Case.getDataContL00106());
                banotifyList.add(banotify1);
                banotifyList.add(banotify2);
                banotifyList.add(banotify3);
                banotifyList.add(banotify4);
                banotifyList.add(banotify5);
                banotifyList.add(banotify6);
            }
            else if (StringUtils.equalsIgnoreCase(rptKind, "002U")) {
                Banotify banotify1 = new Banotify();
                banotify1.setDataCont(MonthlyRpt31Case.getDataContL002U01()); // 主旨
                Banotify banotify2 = new Banotify();
                banotify2.setDataCont(MonthlyRpt31Case.getDataContL00202());
                Banotify banotify3 = new Banotify();
                banotify3.setDataCont(MonthlyRpt31Case.getDataContL00203());
                Banotify banotify4 = new Banotify();
                banotify4.setDataCont(MonthlyRpt31Case.getDataContL00204());
                Banotify banotify5 = new Banotify();
                banotify5.setDataCont(MonthlyRpt31Case.getDataContL00205());
                Banotify banotify6 = new Banotify();
                banotify6.setDataCont(MonthlyRpt31Case.getDataContL00206());
                Banotify banotify7 = new Banotify();
                banotify7.setDataCont(MonthlyRpt31Case.getDataContL00207());
                banotifyList.add(banotify1);
                banotifyList.add(banotify2);
                banotifyList.add(banotify3);
                banotifyList.add(banotify4);
                banotifyList.add(banotify5);
                banotifyList.add(banotify51);
                banotifyList.add(banotify52);
                banotifyList.add(banotify6);
                banotifyList.add(banotify61);
                banotifyList.add(banotify62);
                banotifyList.add(banotify7);
            }
            else if (StringUtils.equalsIgnoreCase(rptKind, "003U")) {
                Banotify banotify1 = new Banotify();
                banotify1.setDataCont(MonthlyRpt31Case.getDataContL003U01()); // 主旨
                Banotify banotify2 = new Banotify();
                banotify2.setDataCont(MonthlyRpt31Case.getDataContL00302());
                Banotify banotify3 = new Banotify();
                banotify3.setDataCont(MonthlyRpt31Case.getDataContL00303());
                Banotify banotify4 = new Banotify();
                banotify4.setDataCont(MonthlyRpt31Case.getDataContL00304());
                Banotify banotify5 = new Banotify();
                banotify5.setDataCont(MonthlyRpt31Case.getDataContL00305());
                Banotify banotify6 = new Banotify();
                banotify6.setDataCont(MonthlyRpt31Case.getDataContL00306());
                banotifyList.add(banotify1);
                banotifyList.add(banotify2);
                banotifyList.add(banotify3);
                banotifyList.add(banotify4);
                banotifyList.add(banotify41);
                banotifyList.add(banotify42);
                banotifyList.add(banotify5);
                banotifyList.add(banotify51);
                banotifyList.add(banotify52);
                banotifyList.add(banotify6);
            }
            else if (StringUtils.equalsIgnoreCase(rptKind, "001P")) { // 第三次通知函（延不補正）
                Banotify banotify1 = new Banotify();
                banotify1.setDataCont(MonthlyRpt31Case.getDataContL001P01()); // 主旨
                Banotify banotify2 = new Banotify();
                banotify2.setDataCont(MonthlyRpt31Case.getDataContL00102());
                Banotify banotify3 = new Banotify();
                banotify3.setDataCont(MonthlyRpt31Case.getDataContL00103());
                Banotify banotify4 = new Banotify();
                banotify4.setDataCont(MonthlyRpt31Case.getDataContL00104());
                Banotify banotify5 = new Banotify();
                banotify5.setDataCont(MonthlyRpt31Case.getDataContL00105());
                Banotify banotify6 = new Banotify();
                banotify6.setDataCont(MonthlyRpt31Case.getDataContL00106());
                banotifyList.add(banotify1);
                banotifyList.add(banotify2);
                banotifyList.add(banotify3);
                banotifyList.add(banotify4);
                banotifyList.add(banotify5);
                banotifyList.add(banotify6);
            }
            else if (StringUtils.equalsIgnoreCase(rptKind, "002P")) {
                Banotify banotify1 = new Banotify();
                banotify1.setDataCont(MonthlyRpt31Case.getDataContL002P01()); // 主旨
                Banotify banotify2 = new Banotify();
                banotify2.setDataCont(MonthlyRpt31Case.getDataContL00202());
                Banotify banotify3 = new Banotify();
                banotify3.setDataCont(MonthlyRpt31Case.getDataContL00203());
                Banotify banotify4 = new Banotify();
                banotify4.setDataCont(MonthlyRpt31Case.getDataContL00204());
                Banotify banotify5 = new Banotify();
                banotify5.setDataCont(MonthlyRpt31Case.getDataContL00205());
                Banotify banotify6 = new Banotify();
                banotify6.setDataCont(MonthlyRpt31Case.getDataContL00206());
                Banotify banotify7 = new Banotify();
                banotify7.setDataCont(MonthlyRpt31Case.getDataContL00207());
                banotifyList.add(banotify1);
                banotifyList.add(banotify2);
                banotifyList.add(banotify3);
                banotifyList.add(banotify4);
                banotifyList.add(banotify5);
                banotifyList.add(banotify51);
                banotifyList.add(banotify52);
                banotifyList.add(banotify6);
                banotifyList.add(banotify61);
                banotifyList.add(banotify62);
                banotifyList.add(banotify7);
            }
            else if (StringUtils.equalsIgnoreCase(rptKind, "003P")) {
                Banotify banotify1 = new Banotify();
                banotify1.setDataCont(MonthlyRpt31Case.getDataContL003P01()); // 主旨
                Banotify banotify2 = new Banotify();
                banotify2.setDataCont(MonthlyRpt31Case.getDataContL00302());
                Banotify banotify3 = new Banotify();
                banotify3.setDataCont(MonthlyRpt31Case.getDataContL00303());
                Banotify banotify4 = new Banotify();
                banotify4.setDataCont(MonthlyRpt31Case.getDataContL00304());
                Banotify banotify5 = new Banotify();
                banotify5.setDataCont(MonthlyRpt31Case.getDataContL00305());
                Banotify banotify6 = new Banotify();
                banotify6.setDataCont(MonthlyRpt31Case.getDataContL00306());
                banotifyList.add(banotify1);
                banotifyList.add(banotify2);
                banotifyList.add(banotify3);
                banotifyList.add(banotify4);
                banotifyList.add(banotify41);
                banotifyList.add(banotify42);
                banotifyList.add(banotify5);
                banotifyList.add(banotify51);
                banotifyList.add(banotify52);
                banotifyList.add(banotify6);
            }

            BaReportReplaceUtility strReplace = new BaReportReplaceUtility(baappbase, false, rptKind, true, null);
            List<String> message = new ArrayList<String>();
            for (Banotify banotify : banotifyList) {
                message.add(strReplace.replace(banotify.getDataCont()));
            }

            // 報表發文字號
            MonthlyRpt31Case.setWordNo(baappbase.getApNo());
            // 報表發文日期 發文日期：以系統日期列印。
            MonthlyRpt31Case.setWordDate(DateUtility.formatChineseDateString(DateUtility.getNowChineseDate(), true));
            // 報表承辦單位
            MonthlyRpt31Case.setUnit(unit);
            // 報表連絡電話
            MonthlyRpt31Case.setComTel(comTel);
            // 報表受文者 受款人為自然人時要印先生 女士
            // if (StringUtils.equalsIgnoreCase(baappbase.getBenSex(), "1")) {
            // MonthlyRpt31Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()) + " 先生");
            // }
            // else if (StringUtils.equalsIgnoreCase(baappbase.getBenSex(), "2")) {
            // MonthlyRpt31Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()) + " 女士");
            // }
            // else {
            MonthlyRpt31Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()) + " 君之家屬");
            // }

            // 地址
            MonthlyRpt31Case.setAddrZip(StringUtils.trimToEmpty(baappbase.getCommZip()));
            MonthlyRpt31Case.setAddr(StringUtils.trimToEmpty(baappbase.getCommAddr()));

            MonthlyRpt31Case.setMessage(message);

            MonthlyRpt31Case.setManager(manager);

            MonthlyRpt31Case.setApNo(StringUtils.trimToEmpty(baappbase.getApNo()));
            MonthlyRpt31Case.setSeqNo(StringUtils.trimToEmpty(baappbase.getSeqNo()));
            MonthlyRpt31Case.setIssuYm(StringUtils.trimToEmpty(baappbase.getIssuYm()));
            MonthlyRpt31Case.setEvtIdnNo(StringUtils.trimToEmpty(baappbase.getEvtIdnNo()));
            MonthlyRpt31Case.setEvtName(StringUtils.trimToEmpty(baappbase.getEvtName()));
            MonthlyRpt31Case.setEvtBrDate(StringUtils.trimToEmpty(baappbase.getEvtBrDate()));

            caseList.add(MonthlyRpt31Case);

        }
        
        // 寫入老年差額金通知發文紀錄檔 (BAMARGINAMTNOTIFY) 及 行政支援紀錄檔 (MAADMREC)
        updateService.insertDataToBamarginamtnotifyForMonthlyRpt31(userData, caseList, map);

        return caseList;
    }

    /**
     * 查詢 給付主檔 (<code>BAAPPBASE</code>) 案件資料 FOR 批次老年差額金通知（1-第1次發函）
     */
    public List<Baappbase> selectApNoListForOtherRpt12Notify1(String payCode, String issuYm) {
        return baappbaseDao.selectApNoListForOtherRpt12Notify1(payCode, issuYm);
    }

    /**
     * 查詢 給付主檔 (<code>BAAPPBASE</code>) 案件資料 FOR 批次老年差額金通知（2-催辦）
     */
    public List<Baappbase> selectApNoListForOtherRpt12Notify2(String payCode, String issuYm) {
        return baappbaseDao.selectApNoListForOtherRpt12Notify2(payCode, issuYm);
    }

    /**
     * 查詢 給付主檔 (<code>BAAPPBASE</code>) 案件資料 FOR 批次老年差額金通知（3-延不補正）
     */
    public List<Baappbase> selectApNoListForOtherRpt12Notify3(String payCode, String issuYm) {
        return baappbaseDao.selectApNoListForOtherRpt12Notify3(payCode, issuYm);
    }

    /**
     * 查詢 給付主檔 (<code>BAAPPBASE</code>) 案件資料 FOR 批次老年差額金通知（A-全部）
     */
    public List<Baappbase> selectApNoListForOtherRpt12NotifyA(String payCode, String issuYm) {
        return baappbaseDao.selectApNoListForOtherRpt12NotifyA(payCode, issuYm);
    }

    /**
     * 依傳入的條件 取得 查核失能程度通知函 的資料
     * 
     * @param rptKind 報表類別
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt30Case> getMonthlyRpt30ExcelDataBy(String rptKind, String payCode, String issuYm) {

        List<Baappbase> baappbaseList = new ArrayList<Baappbase>();

        if (StringUtils.equals(rptKind, "1")) {
            baappbaseList = baappbaseDao.getMonthlyRpt30ExeclK01DataBy(payCode, issuYm);
        }
        else if (StringUtils.equals(rptKind, "2")) {
            baappbaseList = baappbaseDao.getMonthlyRpt30ExeclK02DataBy(payCode, issuYm);
        }

        if (baappbaseList.size() == 0)
            return null;// 查無主檔資料

        List<MonthlyRpt30Case> caseList = new ArrayList<MonthlyRpt30Case>();

        for (Baappbase baappbase : baappbaseList) {

            MonthlyRpt30Case caseData = new MonthlyRpt30Case();
            BeanUtility.copyProperties(caseData, baappbase);

            caseList.add(caseData);

        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 補送在學證明通知函 06 B01 的資料
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<MonthlyRpt29Case> getMonthlyRpt29For06B01DataBy(String rptKind, String payCode, String studeChkMonth, String studeDate, String apNo, String studeDateFormChk) {

        List<Baappbase> baappbaseList = new ArrayList<Baappbase>();

        if (payCode.equals("K")) {
            baappbaseList = baappbaseDao.getMonthlyRpt29DisabledY06B01DataBy(studeChkMonth, studeDate, apNo);
        }
        else if (payCode.equals("S")) {
            baappbaseList = baappbaseDao.getMonthlyRpt29SurvivorY06B01DataBy(studeChkMonth, studeDate, apNo);
        }

        if (baappbaseList.size() == 0)
            return null;// 查無主檔資料

        List<MonthlyRpt29Case> caseList = new ArrayList<MonthlyRpt29Case>();

        for (Baappbase baappbase : baappbaseList) {

            // 承辦單位 連絡電話
            // Bbcmf09 bbcmf09 = bbcmf09Dao.selectComTelForMonthlyRpt05By(evtData.getApNo());
            // String title = "";
            // if(bbcmf09 != null){
            // if(bbcmf09.getSex().equals("1")) {
            // title = "先生";
            // }else if(bbcmf09.getSex().equals("2")){
            // title = "小姐";
            // }
            // }
            String unit = "";
            String comTel = "";
            // 承辦單位 連絡電話
            Bbcmf09 bbcmf09 = bbcmf09Dao.selectComTelForMonthlyRpt05By(baappbase.getApNo());

            if ("K".equalsIgnoreCase(baappbase.getPayType())) {
                unit = "職業災害給付組失能給付科";
                if (bbcmf09 != null) {
                    comTel = "(02)23961266轉" + bbcmf09.getData2();
                }

            }
            else if ("S".equalsIgnoreCase(baappbase.getPayType())) {
                unit = "職業災害給付組死亡給付科";
                if (bbcmf09 != null) {
                    comTel = "(02)23961266轉" + bbcmf09.getData2();
                }
            }

            // 總經理名稱
            String manager = bapasignDao.selectManager(DateUtility.getNowWestDate());

            MonthlyRpt29Case MonthlyRpt29Case = new MonthlyRpt29Case();

            // B01 細項
            Banotify banotify31 = new Banotify();
            banotify31.setDataCont(MonthlyRpt29Case.getDataCont1());
            Banotify banotify32 = new Banotify();
            banotify32.setDataCont(MonthlyRpt29Case.getDataCont2());
            Banotify banotify33 = new Banotify();
            banotify33.setDataCont(MonthlyRpt29Case.getDataCont3());
            Banotify banotify34 = new Banotify();
            banotify34.setDataCont(MonthlyRpt29Case.getDataCont4());
            Banotify banotify35 = new Banotify();
            banotify35.setDataCont(MonthlyRpt29Case.getDataCont5());

            // 傳入固定主旨&說明
            List<Banotify> banotifyList = new ArrayList<Banotify>();
            if (StringUtils.equalsIgnoreCase(rptKind, "1") && StringUtils.equalsIgnoreCase(baappbase.getPayType(), "K")) {
                Banotify banotify1 = new Banotify();
                banotify1.setDataCont(MonthlyRpt29Case.getDataContMK1());
                Banotify banotify2 = new Banotify();
                banotify2.setDataCont(MonthlyRpt29Case.getDataContMK2());
                Banotify banotify3 = new Banotify();
                banotify3.setDataCont(MonthlyRpt29Case.getDataContMK3());
                Banotify banotify4 = new Banotify();
                banotify4.setDataCont(MonthlyRpt29Case.getDataContMK4());
                banotifyList.add(banotify1);
                banotifyList.add(banotify2);
                banotifyList.add(banotify3);
                banotifyList.add(banotify31);
                banotifyList.add(banotify32);
                banotifyList.add(banotify33);
                banotifyList.add(banotify34);
                banotifyList.add(banotify4);
            }
            else if (StringUtils.equalsIgnoreCase(rptKind, "1") && StringUtils.equalsIgnoreCase(baappbase.getPayType(), "S")) {
                Banotify banotify1 = new Banotify();
                banotify1.setDataCont(MonthlyRpt29Case.getDataContMS1());
                Banotify banotify2 = new Banotify();
                banotify2.setDataCont(MonthlyRpt29Case.getDataContMS2());
                Banotify banotify3 = new Banotify();
                banotify3.setDataCont(MonthlyRpt29Case.getDataContMS3());
                Banotify banotify4 = new Banotify();
                banotify4.setDataCont(MonthlyRpt29Case.getDataContMS4());
                banotifyList.add(banotify1);
                banotifyList.add(banotify2);
                banotifyList.add(banotify3);
                banotifyList.add(banotify31);
                banotifyList.add(banotify32);
                banotifyList.add(banotify33);
                banotifyList.add(banotify34);
                banotifyList.add(banotify4);
            }
            else if (StringUtils.equalsIgnoreCase(rptKind, "2") && StringUtils.equalsIgnoreCase(baappbase.getPayType(), "K")) {
                Banotify banotify1 = new Banotify();
                banotify1.setDataCont(MonthlyRpt29Case.getDataContYK1());
                Banotify banotify2 = new Banotify();
                banotify2.setDataCont(MonthlyRpt29Case.getDataContYK2());
                Banotify banotify3 = new Banotify();
                banotify3.setDataCont(MonthlyRpt29Case.getDataContYK3());
                Banotify banotify4 = new Banotify();
                banotify4.setDataCont(MonthlyRpt29Case.getDataContYK4());
                banotifyList.add(banotify1);
                banotifyList.add(banotify2);
                banotifyList.add(banotify3);
                banotifyList.add(banotify31);
                banotifyList.add(banotify32);
                banotifyList.add(banotify33);
                banotifyList.add(banotify34);
                banotifyList.add(banotify4);
            }
            else if (StringUtils.equalsIgnoreCase(rptKind, "2") && StringUtils.equalsIgnoreCase(baappbase.getPayType(), "S")) {
                Banotify banotify1 = new Banotify();
                banotify1.setDataCont(MonthlyRpt29Case.getDataContYS1());
                Banotify banotify2 = new Banotify();
                banotify2.setDataCont(MonthlyRpt29Case.getDataContYS2());
                Banotify banotify3 = new Banotify();
                banotify3.setDataCont(MonthlyRpt29Case.getDataContYS3());
                Banotify banotify4 = new Banotify();
                banotify4.setDataCont(MonthlyRpt29Case.getDataContYS4());
                banotifyList.add(banotify1);
                banotifyList.add(banotify2);
                banotifyList.add(banotify3);
                banotifyList.add(banotify31);
                banotifyList.add(banotify32);
                banotifyList.add(banotify33);
                banotifyList.add(banotify34);
                banotifyList.add(banotify35); // 年度 細項第5點
                banotifyList.add(banotify4);
            }

            BaReportReplaceUtility strReplace = new BaReportReplaceUtility(baappbase, false, rptKind, false, null);
            List<String> message = new ArrayList<String>();
            for (Banotify banotify : banotifyList) {
                message.add(strReplace.replace(banotify.getDataCont()));
            }

            // 報表發文字號
            MonthlyRpt29Case.setWordNo(baappbase.getApNo());
            // 報表發文日期 發文日期：以系統日期加5個工作日列印。
            MonthlyRpt29Case.setWordDate(DateUtility.formatChineseDateString(DateUtility.calDay(DateUtility.getNowChineseDate(), 5), true));
            // 報表承辦單位
            MonthlyRpt29Case.setUnit(unit);
            // 報表連絡電話
            MonthlyRpt29Case.setComTel(comTel);
            // 報表受文者 受款人為自然人時要印先生 女士
            if (StringUtils.equalsIgnoreCase(baappbase.getBenSex(), "1")) {
                MonthlyRpt29Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()) + " 先生");
            }
            else if (StringUtils.equalsIgnoreCase(baappbase.getBenSex(), "2")) {
                MonthlyRpt29Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()) + " 女士");
            }
            else {
                MonthlyRpt29Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()) + " 君");
            }

            // 地址
            MonthlyRpt29Case.setAddrZip(StringUtils.trimToEmpty(baappbase.getCommZip()));
            MonthlyRpt29Case.setAddr(StringUtils.trimToEmpty(baappbase.getCommAddr()));

            MonthlyRpt29Case.setMessage(message);

            MonthlyRpt29Case.setManager(manager);

            caseList.add(MonthlyRpt29Case);

            // 寫入在學證明通知函記錄檔（BASTUDNOTIFY）
            Bastudnotify bastudnotify = new Bastudnotify();
            bastudnotify.setNotifyType(rptKind);
            bastudnotify.setApNo(baappbase.getApNo());
            bastudnotify.setSeqNo(baappbase.getSeqNo());
            bastudnotify.setEvtName(baappbase.getEvtName());
            bastudnotify.setBenName(baappbase.getBenName());
            bastudnotify.setStudeDate(studeDateFormChk);
            bastudnotifyDao.insertBastudnotify(bastudnotify);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 補送在學證明通知函 的資料 For 在學結束月份 6月
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<MonthlyRpt29Case> getMonthlyRpt29DataForMonth06By(String rptKind, String payCode, String studeChkMonth, String studeDate, String studeDateFormChk) {

        List<Baappbase> baappbaseList = new ArrayList<Baappbase>();

        if (payCode.equals("K")) {
            baappbaseList = baappbaseDao.getMonthlyRpt29DisabledYDataBy(studeChkMonth, studeDate, studeDateFormChk);
        }
        else if (payCode.equals("S")) {
            baappbaseList = baappbaseDao.getMonthlyRpt29SurvivorYDataBy(studeChkMonth, studeDate, studeDateFormChk);
        }

        if (baappbaseList.size() == 0)
            return null;// 查無主檔資料

        List<MonthlyRpt29Case> caseList = new ArrayList<MonthlyRpt29Case>();

        for (Baappbase baappbase : baappbaseList) {

            String unit = "";
            String comTel = "";
            // 承辦單位 連絡電話
            Bbcmf09 bbcmf09 = bbcmf09Dao.selectComTelForMonthlyRpt05By(baappbase.getApNo());

            if ("K".equalsIgnoreCase(baappbase.getPayType())) {
                unit = "職業災害給付組失能給付科";
                if (bbcmf09 != null) {
                    comTel = "(02)23961266轉" + bbcmf09.getData2();
                }

            }
            else if ("S".equalsIgnoreCase(baappbase.getPayType())) {
                unit = "職業災害給付組死亡給付科";
                if (bbcmf09 != null) {
                    comTel = "(02)23961266轉" + bbcmf09.getData2();
                }
            }

            // 總經理名稱
            String manager = bapasignDao.selectManager(DateUtility.getNowWestDate());

            MonthlyRpt29Case MonthlyRpt29Case = new MonthlyRpt29Case();

            // 傳入固定主旨&說明
            List<Banotify> banotifyList = new ArrayList<Banotify>();
            if (StringUtils.equalsIgnoreCase(rptKind, "2") && StringUtils.equalsIgnoreCase(baappbase.getPayType(), "K")) {
                Banotify banotify1 = new Banotify();
                banotify1.setDataCont(MonthlyRpt29Case.getDataContMonth06YK1());
                Banotify banotify2 = new Banotify();
                banotify2.setDataCont(MonthlyRpt29Case.getDataContMonth06YK2());
                Banotify banotify3 = new Banotify();
                banotify3.setDataCont(MonthlyRpt29Case.getDataContMonth06YK3());
                banotifyList.add(banotify1);
                banotifyList.add(banotify2);
                banotifyList.add(banotify3);
            }
            else if (StringUtils.equalsIgnoreCase(rptKind, "2") && StringUtils.equalsIgnoreCase(baappbase.getPayType(), "S")) {
                Banotify banotify1 = new Banotify();
                banotify1.setDataCont(MonthlyRpt29Case.getDataContMonth06YS1());
                Banotify banotify2 = new Banotify();
                banotify2.setDataCont(MonthlyRpt29Case.getDataContMonth06YS2());
                Banotify banotify3 = new Banotify();
                banotify3.setDataCont(MonthlyRpt29Case.getDataContMonth06YS3());
                banotifyList.add(banotify1);
                banotifyList.add(banotify2);
                banotifyList.add(banotify3);
            }

            BaReportReplaceUtility strReplace = new BaReportReplaceUtility(baappbase, false, rptKind, false, null);
            List<String> message = new ArrayList<String>();
            for (Banotify banotify : banotifyList) {
                message.add(strReplace.replace(banotify.getDataCont()));
            }

            // 報表發文字號
            MonthlyRpt29Case.setWordNo(baappbase.getApNo());
            // 報表發文日期 發文日期：以系統日期加5個工作日列印。
            MonthlyRpt29Case.setWordDate(DateUtility.formatChineseDateString(DateUtility.calDay(DateUtility.getNowChineseDate(), 5), true));
            // 報表承辦單位
            MonthlyRpt29Case.setUnit(unit);
            // 報表連絡電話
            MonthlyRpt29Case.setComTel(comTel);
            // 報表受文者 受款人為自然人時要印先生 女士
            if (StringUtils.equalsIgnoreCase(baappbase.getBenSex(), "1")) {
                MonthlyRpt29Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()) + " 先生");
            }
            else if (StringUtils.equalsIgnoreCase(baappbase.getBenSex(), "2")) {
                MonthlyRpt29Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()) + " 女士");
            }
            else {
                MonthlyRpt29Case.setName(StringUtils.trimToEmpty(baappbase.getBenName()) + " 君");
            }

            // 地址
            MonthlyRpt29Case.setAddrZip(StringUtils.trimToEmpty(baappbase.getCommZip()));
            MonthlyRpt29Case.setAddr(StringUtils.trimToEmpty(baappbase.getCommAddr()));

            MonthlyRpt29Case.setMessage(message);

            MonthlyRpt29Case.setManager(manager);

            caseList.add(MonthlyRpt29Case);

            // 寫入在學證明通知函記錄檔（BASTUDNOTIFY）
            Bastudnotify bastudnotify = new Bastudnotify();
            bastudnotify.setNotifyType(rptKind);
            bastudnotify.setApNo(baappbase.getApNo());
            bastudnotify.setSeqNo(baappbase.getSeqNo());
            bastudnotify.setEvtName(baappbase.getEvtName());
            bastudnotify.setBenName(baappbase.getBenName());
            bastudnotify.setStudeDate(studeDateFormChk);
            bastudnotifyDao.insertBastudnotify(bastudnotify);
        }

        return caseList;
    }

    /**
     * 依傳入的條件 取得 補在學證明通知函案件 的資料
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<MonthlyRpt29Case> getMonthlyRpt29ExcelDataBy(String rptKind, String payCode, String studeChkMonth, String studeDate, String studeDateFormChk) {

        List<Baappbase> baappbaseList = new ArrayList<Baappbase>();

        if (StringUtils.equals(rptKind, "1")) {
            if (payCode.equals("K")) {
                baappbaseList = baappbaseDao.getMonthlyRpt29ExeclDisabledMDataBy();
            }
            else if (payCode.equals("S")) {
                baappbaseList = baappbaseDao.getMonthlyRpt29ExeclSurvivorMDataBy();
            }
        }
        else if (StringUtils.equals(rptKind, "2")) {
            if (payCode.equals("K")) {
                baappbaseList = baappbaseDao.getMonthlyRpt29ExeclDisabledYDataBy(studeChkMonth, studeDate, studeDateFormChk);
            }
            else if (payCode.equals("S")) {
                baappbaseList = baappbaseDao.getMonthlyRpt29ExeclSurvivorYDataBy(studeChkMonth, studeDate, studeDateFormChk);
            }
        }

        if (baappbaseList.size() == 0)
            return null;// 查無主檔資料

        List<MonthlyRpt29Case> caseList = new ArrayList<MonthlyRpt29Case>();

        for (Baappbase baappbase : baappbaseList) {

            MonthlyRpt29Case caseData = new MonthlyRpt29Case();
            BeanUtility.copyProperties(caseData, baappbase);

            List<Bachkfile> chkCodeList = bachkfileDao.selectChkCodeForRpt29By(caseData.getApNo(), caseData.getSeqNo(), caseData.getIssuYm(), caseData.getPayYm());

            StringBuffer chkCodeString = new StringBuffer();

            if (chkCodeList.size() > 0) {
                for (int i = 0; i < chkCodeList.size(); i++) {
                    if (i == chkCodeList.size() - 1) {
                        chkCodeString.append(chkCodeList.get(i).getChkCode());
                    }
                    else {
                        chkCodeString.append(chkCodeList.get(i).getChkCode() + "、");
                    }
                }
            }

            caseData.setChkCode(chkCodeString.toString());

            caseList.add(caseData);

            // 寫入在學證明通知函記錄檔（BASTUDNOTIFY）
            String insertRptKind = ""; // NotifyType
            if (StringUtils.equals(rptKind, "1")) {
                insertRptKind = "3";
            }
            else if (StringUtils.equals(rptKind, "2")) {
                insertRptKind = "4";
            }
            Bastudnotify bastudnotify = new Bastudnotify();
            bastudnotify.setNotifyType(insertRptKind);
            bastudnotify.setApNo(caseData.getApNo());
            bastudnotify.setSeqNo(caseData.getSeqNo());
            bastudnotify.setEvtName(caseData.getEvtName());
            bastudnotify.setBenName(caseData.getBenName());
            bastudnotify.setStudeDate(studeDateFormChk);
            bastudnotifyDao.insertBastudnotify(bastudnotify);
        }

        return caseList;
    }

    /**
     * 依傳入的條件 取得 補在學證明通知函案件 的資料
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<MonthlyRpt29Case> getMonthlyRpt29Excel06DataBy(String rptKind, String payCode, String studeChkMonth, String studeDate, String apNo, String studeDateFormChk) {

        List<Baappbase> baappbaseList = new ArrayList<Baappbase>();

        if (payCode.equals("K")) {
            baappbaseList = baappbaseDao.getMonthlyRpt29ExeclDisabledY06DataBy(studeChkMonth, studeDate, apNo);
        }
        else if (payCode.equals("S")) {
            baappbaseList = baappbaseDao.getMonthlyRpt29ExeclSurvivorY06DataBy(studeChkMonth, studeDate, apNo);
        }

        if (baappbaseList.size() == 0)
            return null;// 查無主檔資料

        List<MonthlyRpt29Case> caseList = new ArrayList<MonthlyRpt29Case>();

        for (Baappbase baappbase : baappbaseList) {

            MonthlyRpt29Case caseData = new MonthlyRpt29Case();
            BeanUtility.copyProperties(caseData, baappbase);

            List<Bachkfile> chkCodeList = bachkfileDao.selectChkCodeForRpt29By(caseData.getApNo(), caseData.getSeqNo(), caseData.getIssuYm(), caseData.getPayYm());

            StringBuffer chkCodeString = new StringBuffer();

            if (chkCodeList.size() > 0) {
                for (int i = 0; i < chkCodeList.size(); i++) {
                    if (i == chkCodeList.size() - 1) {
                        chkCodeString.append(chkCodeList.get(i).getChkCode());
                    }
                    else {
                        chkCodeString.append(chkCodeList.get(i).getChkCode() + "、");
                    }
                }
            }

            caseData.setChkCode(chkCodeString.toString());

            caseList.add(caseData);

            // 寫入在學證明通知函記錄檔（BASTUDNOTIFY）
            String insertRptKind = ""; // NotifyType
            if (StringUtils.equals(rptKind, "1")) {
                insertRptKind = "3";
            }
            else if (StringUtils.equals(rptKind, "2")) {
                insertRptKind = "4";
            }
            Bastudnotify bastudnotify = new Bastudnotify();
            bastudnotify.setNotifyType(insertRptKind);
            bastudnotify.setApNo(caseData.getApNo());
            bastudnotify.setSeqNo(caseData.getSeqNo());
            bastudnotify.setEvtName(caseData.getEvtName());
            bastudnotify.setBenName(caseData.getBenName());
            bastudnotify.setStudeDate(studeDateFormChk);
            bastudnotifyDao.insertBastudnotify(bastudnotify);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 改匯清冊 的資料
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt14Case> getMonthlyRpt14DataBy(String payCode, String issuYm, String chkDate) {
        List<MonthlyRpt14Case> caseList = new ArrayList<MonthlyRpt14Case>();
        // 取得報表資料
        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRpt14DataBy(payCode, issuYm, chkDate);

        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt14Case caseData = new MonthlyRpt14Case();
            BeanUtility.copyProperties(caseData, rptData);

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 退匯應收已收清冊 的資料
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt15Case> getMonthlyRpt15DataBy(String payCode, String issuYm, String chkDate, String paySeqNo, String isNullOrNot, String eqOrNot) {
        List<MonthlyRpt15Case> caseList = new ArrayList<MonthlyRpt15Case>();
        // 取得報表資料
        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRpt15DataBy(payCode, issuYm, chkDate, paySeqNo, isNullOrNot, eqOrNot);

        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt15Case caseData = new MonthlyRpt15Case();
            BeanUtility.copyProperties(caseData, rptData);

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入條件取得 退匯通知書 的資料
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param apNo 受理編號
     * @param benIdn 受款人身分證號
     * @return
     */
    public List<MonthlyRpt16Case> getMonthlyRpt16DataBy(String payCode, String issuYm, String apNo, String benIdn, String chkDate) {
        List<MonthlyRpt16Case> caseList = new ArrayList<MonthlyRpt16Case>();

        // 核定年月由民國轉成西元

        String sIssuYm = DateUtility.changeChineseYearMonthType(issuYm);
        String sChkDate = DateUtility.changeDateType(chkDate);

        List<Bapflbac> dataList = bapflbacDao.getMonthlyRpt16DataBy(payCode, sIssuYm, apNo, benIdn, sChkDate);
        // 總經理名稱
        String manager = bapasignDao.selectManager(DateUtility.getNowWestDate());

        for (Bapflbac rptData : dataList) {
            MonthlyRpt16Case caseData = new MonthlyRpt16Case();

            caseData.setPayCode(payCode); // 給付別

            caseData.setApNo(rptData.getApNo()); // 受理編號
            caseData.setPayDate(rptData.getPayDate()); // 核付日期
            caseData.setRemitAmt(rptData.getRemitAmt()); // 退匯金額

            caseData.setBrNote(rptData.getBrNote()); // 退匯原因

            caseData.setBrChkDate(rptData.getBrChkDate()); // 退匯初核日期

            caseData.setBenIdn(rptData.getBenIdn()); // 受款人身分證號

            caseData.setBenName(rptData.getBenName()); // 受款人姓名

            caseData.setCommZip(rptData.getCommZip()); // 郵遞區號

            caseData.setCommAddr(rptData.getCommAddr()); // 地址
            caseData.setCommTel(rptData.getCommTel()); // 電話

            caseData.setManager(manager); // 總經理名稱

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入條件取得 退回現金清冊 的資料（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt17Case> getMonthlyRpt17DataBy(String payCode, String issuYm, String chkDate) {
        List<MonthlyRpt17Case> caseList = new ArrayList<MonthlyRpt17Case>();

        // 核定年月由民國轉成西元

        String sIssuYm = DateUtility.changeChineseYearMonthType(issuYm);
        String sChkDate = DateUtility.changeDateType(chkDate);

        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRpt17DataBy(payCode, sIssuYm, sChkDate);

        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt17Case caseData = new MonthlyRpt17Case();

            caseData.setPayCode(payCode); // 給付別

            caseData.setApNo(rptData.getApNo()); // 年金受理編號
            caseData.setPayYm(rptData.getPayYm()); // 給付年月
            caseData.setPayDate(rptData.getPayDate()); // 核付日期 - 憑證日期
            caseData.setBenName(rptData.getBenName()); // 受益人姓名

            caseData.setBenIdn(rptData.getBenIdn()); // 受益人身分證號

            caseData.setIssueAmt(rptData.getIssueAmt()); // 核定金額
            caseData.setCommTel(rptData.getCommTel()); // 受益人聯絡電話

            caseData.setOtherApNo1(rptData.getOtherApNo1()); // 他類案件受理編號
            caseData.setOtherSeqNo1(rptData.getOtherSeqNo1()); // 他類案件受款人序號

            caseData.setHjMk(rptData.getHjMk()); // 移至註記
            caseData.setIssuTyp(rptData.getIssuTyp()); // 核付分類 - 案件類別
            caseData.setPayTyp(rptData.getPayTyp()); // 給付方式
            caseData.setDataKd(rptData.getDataKd()); // 資料種類
            caseData.setDataTyp(rptData.getDataTyp()); // 資料種類
            caseData.setBliPayeeAcc(rptData.getBliPayeeAcc()); // 本局帳戶
            caseData.setcPrnDate(rptData.getcPrnDate()); // 印表日期

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入條件取得 退回現金清冊 的資料（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt17Case> getMonthlyRptK17DataBy(String payCode, String issuYm, String chkDate, String paySeqNo) {
        List<MonthlyRpt17Case> caseList = new ArrayList<MonthlyRpt17Case>();

        // 核定年月由民國轉成西元

        String sIssuYm = DateUtility.changeChineseYearMonthType(issuYm);
        String sChkDate = DateUtility.changeDateType(chkDate);

        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRptK17DataBy(payCode, sIssuYm, sChkDate, paySeqNo);

        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt17Case caseData = new MonthlyRpt17Case();

            caseData.setPayCode(payCode); // 給付別

            caseData.setApNo(rptData.getApNo()); // 年金受理編號
            caseData.setPayYm(rptData.getPayYm()); // 給付年月
            caseData.setPayDate(rptData.getPayDate()); // 核付日期 - 憑證日期
            caseData.setBenName(rptData.getBenName()); // 受益人姓名

            caseData.setBenIdn(rptData.getBenIdn()); // 受益人身分證號

            caseData.setIssueAmt(rptData.getIssueAmt()); // 核定金額
            caseData.setCommTel(rptData.getCommTel()); // 受益人聯絡電話

            caseData.setOtherApNo1(rptData.getOtherApNo1()); // 他類案件受理編號
            caseData.setOtherSeqNo1(rptData.getOtherSeqNo1()); // 他類案件受款人序號

            caseData.setHjMk(rptData.getHjMk()); // 移至註記
            caseData.setIssuTyp(rptData.getIssuTyp()); // 核付分類 - 案件類別
            caseData.setPayTyp(rptData.getPayTyp()); // 給付方式
            caseData.setDataKd(rptData.getDataKd()); // 資料種類
            caseData.setDataTyp(rptData.getDataTyp()); // 資料種類
            caseData.setBliPayeeAcc(rptData.getBliPayeeAcc()); // 本局帳戶
            caseData.setcPrnDate(rptData.getcPrnDate()); // 印表日期

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入條件取得 退回現金清冊 的資料（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt17Case> getMonthlyRptS17DataBy(String payCode, String issuYm, String chkDate) {
        List<MonthlyRpt17Case> caseList = new ArrayList<MonthlyRpt17Case>();

        // 核定年月由民國轉成西元

        String sIssuYm = DateUtility.changeChineseYearMonthType(issuYm);
        String sChkDate = DateUtility.changeDateType(chkDate);

        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRptS17DataBy(payCode, sIssuYm, sChkDate);

        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt17Case caseData = new MonthlyRpt17Case();

            caseData.setPayCode(payCode); // 給付別

            caseData.setApNo(rptData.getApNo()); // 年金受理編號
            caseData.setPayYm(rptData.getPayYm()); // 給付年月
            caseData.setPayDate(rptData.getPayDate()); // 核付日期 - 憑證日期
            caseData.setBenName(rptData.getBenName()); // 受益人姓名

            caseData.setBenIdn(rptData.getBenIdn()); // 受益人身分證號

            caseData.setIssueAmt(rptData.getIssueAmt()); // 核定金額
            caseData.setCommTel(rptData.getCommTel()); // 受益人聯絡電話

            caseData.setOtherApNo1(rptData.getOtherApNo1()); // 他類案件受理編號
            caseData.setOtherSeqNo1(rptData.getOtherSeqNo1()); // 他類案件受款人序號

            caseData.setHjMk(rptData.getHjMk()); // 移至註記
            caseData.setIssuTyp(rptData.getIssuTyp()); // 核付分類 - 案件類別
            caseData.setPayTyp(rptData.getPayTyp()); // 給付方式
            caseData.setDataKd(rptData.getDataKd()); // 資料種類
            caseData.setDataTyp(rptData.getDataTyp()); // 資料種類
            caseData.setBliPayeeAcc(rptData.getBliPayeeAcc()); // 本局帳戶
            caseData.setcPrnDate(rptData.getcPrnDate()); // 印表日期

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入條件取得 退回現金轉暫收及待結轉清單 的資料（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt18Case> getMonthlyRpt18DataBy(String payCode, String issuYm, String chkDate) {
        List<MonthlyRpt18Case> caseList = new ArrayList<MonthlyRpt18Case>();

        // 核定年月由民國轉成西元

        String sIssuYm = DateUtility.changeChineseYearMonthType(issuYm);
        // 核定日期由民國轉成西元

        String sChkDate = DateUtility.changeDateType(chkDate);

        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRpt18DataBy(payCode, sIssuYm, sChkDate);

        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt18Case caseData = new MonthlyRpt18Case();

            caseData.setPayCode(payCode); // 給付別

            caseData.setOtherApNo1(rptData.getOtherApNo1()); // 他類案件受理編號 - 受理編號
            caseData.setPayDate(rptData.getPayDate()); // 核付日期 - 退回日期

            caseData.setBenName(rptData.getBenName()); // 受益人姓名 - 受款人姓名

            caseData.setIssueAmt(rptData.getIssueAmt()); // 核定金額 - 金額
            caseData.setIssuTyp(rptData.getIssuTyp()); // 核付分類 - 類別
            caseData.setPayTyp(rptData.getPayTyp()); // 給付方式
            caseData.setcPrnDate(rptData.getcPrnDate()); // 印表日期

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入條件取得 退回現金轉暫收及待結轉清單 的資料（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt18Case> getMonthlyRptK18DataBy(String payCode, String issuYm, String chkDate, String paySeqNo) {
        List<MonthlyRpt18Case> caseList = new ArrayList<MonthlyRpt18Case>();

        // 核定年月由民國轉成西元

        String sIssuYm = DateUtility.changeChineseYearMonthType(issuYm);
        // 核定日期由民國轉成西元

        String sChkDate = DateUtility.changeDateType(chkDate);

        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRptK18DataBy(payCode, sIssuYm, sChkDate, paySeqNo);

        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt18Case caseData = new MonthlyRpt18Case();

            caseData.setPayCode(payCode); // 給付別

            caseData.setOtherApNo1(rptData.getOtherApNo1()); // 他類案件受理編號 - 受理編號
            caseData.setPayDate(rptData.getPayDate()); // 核付日期 - 退回日期

            caseData.setBenName(rptData.getBenName()); // 受益人姓名 - 受款人姓名

            caseData.setIssueAmt(rptData.getIssueAmt()); // 核定金額 - 金額
            caseData.setIssuTyp(rptData.getIssuTyp()); // 核付分類 - 類別
            caseData.setPayTyp(rptData.getPayTyp()); // 給付方式
            caseData.setcPrnDate(rptData.getcPrnDate()); // 印表日期

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入條件取得 退回現金轉暫收及待結轉清單 的資料（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt18Case> getMonthlyRptS18DataBy(String payCode, String issuYm, String chkDate) {
        List<MonthlyRpt18Case> caseList = new ArrayList<MonthlyRpt18Case>();

        // 核定年月由民國轉成西元

        String sIssuYm = DateUtility.changeChineseYearMonthType(issuYm);
        // 核定日期由民國轉成西元

        String sChkDate = DateUtility.changeDateType(chkDate);

        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRptS18DataBy(payCode, sIssuYm, sChkDate);

        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt18Case caseData = new MonthlyRpt18Case();

            caseData.setPayCode(payCode); // 給付別

            caseData.setOtherApNo1(rptData.getOtherApNo1()); // 他類案件受理編號 - 受理編號
            caseData.setPayDate(rptData.getPayDate()); // 核付日期 - 退回日期

            caseData.setBenName(rptData.getBenName()); // 受益人姓名 - 受款人姓名

            caseData.setIssueAmt(rptData.getIssueAmt()); // 核定金額 - 金額
            caseData.setIssuTyp(rptData.getIssuTyp()); // 核付分類 - 類別
            caseData.setPayTyp(rptData.getPayTyp()); // 給付方式
            caseData.setcPrnDate(rptData.getcPrnDate()); // 印表日期

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 應收款立帳清冊 的資料
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt21Case> getMonthlyRpt21DataBy(String payCode, String issuYm, String chkDate, String paySeqNo, String isNull, String isEqual) {
        List<MonthlyRpt21Case> caseList = new ArrayList<MonthlyRpt21Case>();
        // 取得報表資料
        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRpt21DataBy(payCode, issuYm, chkDate, paySeqNo, isNull, isEqual);

        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt21Case caseData = new MonthlyRpt21Case();
            BeanUtility.copyProperties(caseData, rptData);

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 月處理核定報表彙整 的資料
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRpt22DataBy(String payCode, String issuYm, String chkDate) {

        // 取得報表資料
        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRpt22DataBy(payCode, issuYm, chkDate);

        return dataList;
    }

    /**
     * 依傳入的條件取得 月處理核定報表彙整 的資料
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public Integer getMonthlyRpt22DataByPaytyp(String payCode, String issuYm, String chkDate, String eqType) {

        // 取得報表資料

        return bapayrptrecordDao.getMonthlyRpt22DataByPaytyp(payCode, issuYm, chkDate, eqType);
    }

    /**
     * 依傳入的條件取得 郵政匯票通知／入戶匯款證明 的資料（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt23Case> getMonthlyRpt23DataBy(String payCode, String issuYm, String chkDate) {
        List<MonthlyRpt23Case> caseList = new ArrayList<MonthlyRpt23Case>();
        // 取得報表資料
        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRpt23DataBy(payCode, issuYm, chkDate);

        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt23Case caseData = new MonthlyRpt23Case();
            BeanUtility.copyProperties(caseData, rptData);

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 郵政匯票通知／入戶匯款證明 的資料（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt23Case> getMonthlyRptK23DataBy(String payCode, String issuYm, String chkDate, String paySeqNo) {
        List<MonthlyRpt23Case> caseList = new ArrayList<MonthlyRpt23Case>();
        // 取得報表資料
        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRptK23DataBy(payCode, issuYm, chkDate, paySeqNo);

        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt23Case caseData = new MonthlyRpt23Case();
            BeanUtility.copyProperties(caseData, rptData);

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 郵政匯票通知／入戶匯款證明 的資料（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt23Case> getMonthlyRptS23DataBy(String payCode, String issuYm, String chkDate) {
        List<MonthlyRpt23Case> caseList = new ArrayList<MonthlyRpt23Case>();
        // 取得報表資料
        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRptS23DataBy(payCode, issuYm, chkDate);

        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt23Case caseData = new MonthlyRpt23Case();
            BeanUtility.copyProperties(caseData, rptData);

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 退匯應收已收核定清單 的資料
     * 
     * @param payTyp 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt19Case> getMonthlyRpt19DataBy(String payCode, String issuYm, String chkDate, String paySeqNo, String isNullOrNot, String eqOrNot) {
        // 20120406 資料改由原本抓Bapayrptrecord，變成抓Bapayrptsum
        // List<Bapayrptrecord> bapayrptrecordList = bapayrptrecordDao.selectMonthlyRpt19DataBy(payCode, issuYm, chkDate);
        List<Bapayrptsum> bapayrptrecordList = bapayrptsumDao.selectMonthlyRpt19DataBy(payCode, issuYm, chkDate, paySeqNo, isNullOrNot, eqOrNot);
        List<MonthlyRpt19Case> caseList = new ArrayList<MonthlyRpt19Case>();

        for (Bapayrptsum badapr : bapayrptrecordList) {
            MonthlyRpt19Case caseData = new MonthlyRpt19Case();
            BeanUtility.copyProperties(caseData, badapr);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 退匯應收已收核定清單 的資料
     * 
     * @param payTyp 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt19Case> getMonthlyRpt19DataFooterBy(String payCode, String issuYm, String chkDate, String paySeqNo, String isNullOrNot, String eqOrNot) {
        // 20120406 會計科目資料抓Bapayrptaccount
        // List<Bapayrptrecord> bapayrptrecordList = bapayrptrecordDao.selectMonthlyRpt04Report(payCode, issuYm, benEvtRel, eqType, chkDate);
        List<Bapayrptaccount> bapayrptrecordList = bapayrptaccountDao.selectMonthlyRpt19Report(payCode, issuYm, chkDate, paySeqNo, isNullOrNot, eqOrNot);
        List<MonthlyRpt19Case> caseList = new ArrayList<MonthlyRpt19Case>();

        for (Bapayrptaccount badapr : bapayrptrecordList) {
            MonthlyRpt19Case caseData = new MonthlyRpt19Case();
            BeanUtility.copyProperties(caseData, badapr);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 應收款立帳核定清單 的資料
     * 
     * @param payTyp 給付別
     * @param issuYm 核定年月
     * @param chkDate 核定日期
     * @return
     */
    public List<MonthlyRpt20Case> getMonthlyRpt20DataBy(String payCode, String issuYm, String chkDate, String paySeqNo, String isNull, String isEqual) {
        // 20120406 資料改由原本抓Bapayrptrecord，變成抓Bapayrptsum
        // List<Bapayrptrecord> bapayrptrecordList = bapayrptrecordDao.selectMonthlyRpt20DataBy(payCode, issuYm, chkDate);
        List<Bapayrptsum> bapayrptrecordList = bapayrptsumDao.selectMonthlyRpt20DataBy(payCode, issuYm, chkDate, paySeqNo, isNull, isEqual);
        List<MonthlyRpt20Case> caseList = new ArrayList<MonthlyRpt20Case>();

        for (Bapayrptsum badapr : bapayrptrecordList) {
            MonthlyRpt20Case caseData = new MonthlyRpt20Case();
            BeanUtility.copyProperties(caseData, badapr);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 應收款立帳核定清單 的資料
     * 
     * @param payTyp 給付別
     * @param issuYm 核定年月
     * @param chkDate 核定日期
     * @return
     */
    public List<MonthlyRpt20Case> getMonthlyRpt20DataFooterBy(String payCode, String issuYm, String chkDate, String paySeqNo, String isNull, String isEqual) {
        // 20120406 會計科目資料抓Bapayrptaccount
        // List<Bapayrptrecord> bapayrptrecordList = bapayrptrecordDao.selectMonthlyRpt04Report(payCode, issuYm, benEvtRel, eqType, chkDate);
        List<Bapayrptaccount> bapayrptrecordList = bapayrptaccountDao.selectMonthlyRpt20Report(payCode, issuYm, chkDate, paySeqNo, isNull, isEqual);
        List<MonthlyRpt20Case> caseList = new ArrayList<MonthlyRpt20Case>();

        for (Bapayrptaccount badapr : bapayrptrecordList) {
            MonthlyRpt20Case caseData = new MonthlyRpt20Case();
            BeanUtility.copyProperties(caseData, badapr);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 保留遺屬年金計息存儲核定清單 的資料
     * 
     * @param payTyp 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt32Case> getMonthlyRpt32DataBy(String payCode, String issuYm, String chkDate, String paySeqNo, String isNullOrNot, String eqOrNot) {
        // 20120406 資料改由原本抓Bapayrptrecord，變成抓Bapayrptsum
        // List<Bapayrptrecord> bapayrptrecordList = bapayrptrecordDao.selectMonthlyRpt19DataBy(payCode, issuYm, chkDate);
        List<Bapayrptsum> bapayrptrecordList = bapayrptsumDao.selectMonthlyRpt32DataBy(payCode, issuYm, chkDate, paySeqNo, isNullOrNot, eqOrNot);
        List<MonthlyRpt32Case> caseList = new ArrayList<MonthlyRpt32Case>();

        for (Bapayrptsum badapr : bapayrptrecordList) {
            MonthlyRpt32Case caseData = new MonthlyRpt32Case();
            BeanUtility.copyProperties(caseData, badapr);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 保留遺屬年金計息存儲核定清單 的資料
     * 
     * @param payTyp 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt32Case> getMonthlyRpt32DataFooterBy(String payCode, String issuYm, String chkDate, String paySeqNo, String isNullOrNot, String eqOrNot) {
        // 20120406 會計科目資料抓Bapayrptaccount
        // List<Bapayrptrecord> bapayrptrecordList = bapayrptrecordDao.selectMonthlyRpt04Report(payCode, issuYm, benEvtRel, eqType, chkDate);
        List<Bapayrptaccount> bapayrptrecordList = bapayrptaccountDao.selectMonthlyRpt32Report(payCode, issuYm, chkDate, paySeqNo, isNullOrNot, eqOrNot);
        List<MonthlyRpt32Case> caseList = new ArrayList<MonthlyRpt32Case>();

        for (Bapayrptaccount badapr : bapayrptrecordList) {
            MonthlyRpt32Case caseData = new MonthlyRpt32Case();
            BeanUtility.copyProperties(caseData, badapr);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 保留遺屬年金計息存儲清冊 的資料
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt33Case> getMonthlyRpt33DataBy(String payCode, String issuYm, String chkDate, String paySeqNo, String isNullOrNot, String eqOrNot) {
        List<MonthlyRpt33Case> caseList = new ArrayList<MonthlyRpt33Case>();
        // 取得報表資料
        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRpt33DataBy(payCode, issuYm, chkDate, paySeqNo, isNullOrNot, eqOrNot);

        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt33Case caseData = new MonthlyRpt33Case();
            BeanUtility.copyProperties(caseData, rptData);

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 複檢費用審核給付清單 的資料
     * 
     * @param apNo 受理編號
     * @param apSeq 申請序號
     * @return
     */

    public List<ReviewFeeReceiptRpt01DataCase> getReviewFeeReceiptRpt01ListDataBy(String apNo, String apSeq) {
        List<ReviewFeeReceiptRpt01DataCase> caseList = new ArrayList<ReviewFeeReceiptRpt01DataCase>();
        List<Babcml7> babcml7List = babcml7Dao.selecReviewFeeReceiptRpt01ListBy(apNo, apSeq);

        for (Babcml7 babcml7Data : babcml7List) {
            List<Bbcmf07> bbcmf07List = bbcmf07Dao.selecReviewFeeReceiptHosListBy(babcml7Data.getHosId());
            ReviewFeeReceiptRpt01DataCase bbcmf07DataCase = new ReviewFeeReceiptRpt01DataCase();
            BeanUtility.copyProperties(bbcmf07DataCase, babcml7Data);
            for (Bbcmf07 Bbcmf07Data : bbcmf07List) {
                bbcmf07DataCase.setHosName(Bbcmf07Data.getHpName());
            }
            // 取得 核定通知書格式檔 (BANOTIFY 核定通知書資料

            // [
            // a. 若核定通知書格式為 999 或為 空白 及 NULL, 則整段核定通知書不顯示
            // b. 若查無受理案件核定通知書格式, 則整段核定通知書不顯示
            if (StringUtils.isNotBlank(babcml7Data.getNotifyForm()) && !StringUtils.equals(babcml7Data.getNotifyForm(), "999")) {
                BaReportReplaceUtility strReplace = new BaReportReplaceUtility(babcml7Data, true);

                // 取得 核定通知書 - 主旨
                List<Banotify> subjectList = banotifyDao.getReviewFeeReceiptwRpt01NotifyListBy(StringUtils.substring(apNo, 0, 1), babcml7Data.getNotifyForm(), "0");
                if (subjectList.size() > 0) {
                    ReviewFeeReceiptNotifyDataCase notifyCase = new ReviewFeeReceiptNotifyDataCase();
                    notifyCase.setSubject(strReplace.replace(StringUtils.defaultString(subjectList.get(0).getDataCont())));

                    // 取得 核定通知書 - 說明
                    List<Banotify> contentList = banotifyDao.getReviewFeeReceiptwRpt01NotifyListBy(StringUtils.substring(apNo, 0, 1), babcml7Data.getNotifyForm(), "1");
                    List<String> contents = new ArrayList<String>();
                    for (Banotify contentData : contentList) {
                        if (StringUtils.isNotBlank(contentData.getDataCont()))
                            contents.add(strReplace.replace(contentData.getDataCont()));
                    }
                    notifyCase.setContent(contents);

                    bbcmf07DataCase.setNotifyData(notifyCase);
                }
            }
            // ]

            caseList.add(bbcmf07DataCase);

        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 複檢費用核定通知書 的資料
     * 
     * @param apNo 受理編號
     * @param apSeq 申請序號
     * @return
     */

    public List<ReviewFeeReceiptRpt02DataCase> getReviewFeeReceiptRpt02ListDataBy(String apNo, String apSeq) {
        List<ReviewFeeReceiptRpt02DataCase> caseList = new ArrayList<ReviewFeeReceiptRpt02DataCase>();
        List<Babcml7> babcml7List = babcml7Dao.selecReviewFeeReceiptRpt02ListBy(apNo, apSeq);

        if (babcml7List.size() == 0)
            return null;// 查無主檔資料

        for (Babcml7 babcml7Data : babcml7List) {

            ReviewFeeReceiptRpt02DataCase bbcmf07DataCase = new ReviewFeeReceiptRpt02DataCase();
            BeanUtility.copyProperties(bbcmf07DataCase, babcml7Data);

            // 正本

            if (StringUtils.equals("1", StringUtils.substring(babcml7Data.getEvtIdnNo(), 1, 2))) {
                bbcmf07DataCase.setProgenitor(babcml7Data.getEvtName() + " 先生");
            }
            else {
                bbcmf07DataCase.setProgenitor(babcml7Data.getEvtName() + " 女士");
            }

            // 報表發文字號
            bbcmf07DataCase.setWordNo(apNo.substring(0, 12));
            // 報表發文日期
            bbcmf07DataCase.setWordDate(DateUtility.formatChineseDateString(DateUtility.changeDateType(babcml7Data.getPayDate()), true));
            // 報表承辦單位
            bbcmf07DataCase.setUnit("本局給付處傷殘給付科");
            // 報表連絡電話
            bbcmf07DataCase.setComTel("02-23961266");

            // 報表受文者 受款人為自然人時要印先生 女士
            String benEvtRel = StringUtils.trimToEmpty(babcml7Data.getBenEvtRel());
            if (benEvtRel.equals("1") || benEvtRel.equals("2") || benEvtRel.equals("3") || benEvtRel.equals("4") || benEvtRel.equals("5") || benEvtRel.equals("6") || benEvtRel.equals("7") || benEvtRel.equalsIgnoreCase("D")
                            || benEvtRel.equalsIgnoreCase("E")) {
                String benIdnNo = StringUtils.trimToEmpty(babcml7Data.getBenIdnNo());
                if (!benIdnNo.equals("") && benIdnNo.substring(1, 2).equals("1")) {
                    bbcmf07DataCase.setName(StringUtils.trimToEmpty(babcml7Data.getBenName()) + " 先生");
                }
                else {
                    bbcmf07DataCase.setName(StringUtils.trimToEmpty(babcml7Data.getBenName()) + " 女士");
                }
            }
            else {
                bbcmf07DataCase.setName(StringUtils.trimToEmpty(babcml7Data.getBenName()));
            }

            // 取得 核定通知書格式檔 (BANOTIFY 核定通知書資料

            // [
            // a. 若核定通知書格式為 999 或為 空白 及 NULL, 則整段核定通知書不顯示
            // b. 若查無受理案件核定通知書格式, 則整段核定通知書不顯示
            if (StringUtils.isNotBlank(bbcmf07DataCase.getNotifyForm()) && !StringUtils.equals(babcml7Data.getNotifyForm(), "999")) {
                BaReportReplaceUtility strReplace = new BaReportReplaceUtility(babcml7Data, true);

                // 取得 核定通知書 - 主旨
                List<Banotify> subjectList = banotifyDao.getReviewFeeReceiptwRpt01NotifyListBy(StringUtils.substring(apNo, 0, 1), babcml7Data.getNotifyForm(), "0");
                if (subjectList.size() > 0) {
                    ReviewFeeReceiptNotifyDataCase notifyCase = new ReviewFeeReceiptNotifyDataCase();
                    notifyCase.setSubject(strReplace.replace(StringUtils.defaultString(subjectList.get(0).getDataCont())));

                    // 取得 核定通知書 - 說明
                    List<Banotify> contentList = banotifyDao.getReviewFeeReceiptwRpt01NotifyListBy(StringUtils.substring(apNo, 0, 1), babcml7Data.getNotifyForm(), "1");
                    List<String> contents = new ArrayList<String>();
                    for (Banotify contentData : contentList) {
                        if (StringUtils.isNotBlank(contentData.getDataCont()))
                            contents.add(strReplace.replace(contentData.getDataCont()));
                    }
                    notifyCase.setContent(contents);

                    bbcmf07DataCase.setNotifyData(notifyCase);
                }
            }
            // ]

            caseList.add(bbcmf07DataCase);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 複檢費用核定清單 的資料
     * 
     * @param apNo 受理編號
     * @param apSeq 申請序號
     * @return
     */

    public List<ReviewFeeReceiptRpt03DataCase> getReviewFeeReceiptRpt03ListDataBy(String apNo, String apSeq) {
        List<ReviewFeeReceiptRpt03DataCase> caseList = new ArrayList<ReviewFeeReceiptRpt03DataCase>();
        List<Babcml7> babcml7List = babcml7Dao.selecReviewFeeReceiptRpt03ListBy(apNo, apSeq);

        for (Babcml7 babcml7Data : babcml7List) {
            List<Bbcmf07> bbcmf07List = bbcmf07Dao.selecReviewFeeReceiptHosListBy(babcml7Data.getHosId());
            ReviewFeeReceiptRpt03DataCase bbcmf07DataCase = new ReviewFeeReceiptRpt03DataCase();
            BeanUtility.copyProperties(bbcmf07DataCase, babcml7Data);
            for (Bbcmf07 Bbcmf07Data : bbcmf07List) {
                bbcmf07DataCase.setHosName(Bbcmf07Data.getHpName());
            }

            caseList.add(bbcmf07DataCase);
        }

        return caseList;
    }

    /**
     * 依傳入條件取得 代扣補償金清冊
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt24Case> getMonthlyRpt24DataBy(String payCode, String issuYm, String chkDate) {
        List<MonthlyRpt24Case> caseList = new ArrayList<MonthlyRpt24Case>();

        // 核定年月由民國轉成西元

        String sIssuYm = DateUtility.changeChineseYearMonthType(issuYm);
        String sChkDate = DateUtility.changeDateType(chkDate);

        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRpt24DataBy(payCode, sIssuYm, sChkDate);
        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt24Case caseData = new MonthlyRpt24Case();

            caseData.setPayCode(payCode); // 給付別

            caseData.setPayBankId(rptData.getPayBankId()); // 入帳銀行 (總行)
            caseData.setBranchId(rptData.getBranchId()); // 入帳銀行 (分行)
            caseData.setPayEeacc(rptData.getPayEeacc()); // 入帳帳號
            caseData.setPayAmt(rptData.getPayAmt()); // 交易金額
            caseData.setApNo(rptData.getApNo()); // 受理編號
            caseData.setIssuYm(rptData.getIssuYm()); // 核定年月
            caseData.setPayYm(rptData.getPayYm()); // 給付年月
            caseData.setBenIdn(rptData.getBenIdn()); // 身份證號碼

            caseData.setBenEvtRel(rptData.getBenEvtRel()); // 受益人與事故者關係

            caseData.setCompName1(rptData.getCompName1()); // 相關單位名稱1
            caseData.setCompName2(rptData.getCompName2()); // 申請代算單位

            caseData.setBenName(rptData.getBenName()); // 姓名
            caseData.setPayDate(rptData.getPayDate()); // 核付日期

            caseData.setPayTyp(rptData.getPayTyp()); // 給付方式
            caseData.setcPrnDate(rptData.getcPrnDate()); // 印表日期

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入條件取得 代扣補償金清冊
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    public HashMap<String, MonthlyRpt24PayAmtCase> getMonthlyRpt24PayAmtDataBy(String payCode, String issuYm, String chkDate) {
        HashMap<String, MonthlyRpt24PayAmtCase> payAmtMap = new HashMap<String, MonthlyRpt24PayAmtCase>();

        // 核定年月由民國轉成西元

        String sIssuYm = DateUtility.changeChineseYearMonthType(issuYm);
        String sChkDate = DateUtility.changeDateType(chkDate);

        List<Bapayrptrecord> dataList = bapayrptrecordDao.getMonthlyRpt24PayAmtDataBy(payCode, sIssuYm, sChkDate);
        for (Bapayrptrecord rptData : dataList) {
            MonthlyRpt24PayAmtCase payAmtData = new MonthlyRpt24PayAmtCase();

            payAmtData.setCompName1(rptData.getCompName1()); // 相關單位名稱1
            payAmtData.setPayAmt(rptData.getPayAmt()); // 金額

            payAmtData.setPayTyp(rptData.getPayTyp()); // 給付方式
            payAmtData.setPayBankId(rptData.getPayBankId()); // 入帳銀行 (總行)
            payAmtData.setBranchId(rptData.getBranchId()); // 入帳銀行 (分行)
            payAmtData.setPayEeacc(rptData.getPayEeacc()); // 入帳帳號

            // payAmtMap.put(StringUtils.trimToEmpty(payAmtData.getCompName1()), payAmtData);
            payAmtMap.put(StringUtils.trimToEmpty(payAmtData.getPayTyp()) + StringUtils.trimToEmpty(payAmtData.getCompName1()) + StringUtils.trimToEmpty(payAmtData.getPayBankId()) + StringUtils.trimToEmpty(payAmtData.getBranchId())
                            + StringUtils.trimToEmpty(payAmtData.getPayEeacc()), payAmtData);
        }

        return payAmtMap;
    }

    // =========================================================================================
    /*
     * 查詢關鍵欄位變更資料
     */
    private List<Kcaf> getKcafListBy(String evtIdno, String evtBrDte) {
        List<Kcaf> evtKcafList = new ArrayList<Kcaf>();
        getKcafListBy(evtIdno, evtBrDte, evtKcafList);
        return evtKcafList;
    }

    /*
     * 用事故者IDN BRDTE KCAF找出有變更的資料 再用出來到的變更資料去 KCAF 找變更資料 直到所有變更資料都找完為止
     */
    private void getKcafListBy(String evtIdno, String evtBrDte, List<Kcaf> evtKcafList) {
        List<Kcaf> kcafList = kcafDao.selectByAIdnAndABrDte(evtIdno, evtBrDte);
        for (Kcaf kcaf : kcafList) {
            // 先把KCAF 身分證TRIM成十碼

            kcaf.setBIdn(StringUtils.substring(kcaf.getBIdn(), 0, 10));
            // 身分證生日 跟主檔不同才須要加入 名子不同不用
            if (!(StringUtils.equals(kcaf.getBIdn(), evtIdno) && StringUtils.equals(kcaf.getBBrDte(), evtBrDte))) {
                if (addData(kcaf, evtKcafList)) {
                    getKcafListBy(kcaf.getBIdn(), kcaf.getBBrDte(), evtKcafList);
                }
            }
        }
    }

    // 判斷查出來的資料是否有重複

    private boolean addData(Kcaf kcaf, List<Kcaf> evtKcafList) {
        boolean add = true;
        for (Kcaf evtKcaf : evtKcafList) {
            // 身分證生日 不同才須要加入 名子不同不用
            if (StringUtils.equals(kcaf.getBIdn(), evtKcaf.getBIdn()) && StringUtils.equals(kcaf.getBBrDte(), evtKcaf.getBBrDte())) {
                add = false;
                break;
            }
        }
        if (add) {
            evtKcafList.add(kcaf);
        }
        return add;
    }

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 得請領起始年月 for 失能年金編審註記程度調整
     * 
     * @param apNo 受理編號
     * @param baappbaseId
     */
    public String getAbleapsYmForDisabledCheckMarkLevelAdjust(String apNo, BigDecimal baappbaseId) {
        return baappexpandDao.getAbleapsYmForDisabledCheckMarkLevelAdjust(apNo, baappbaseId);
    }

    /**
     * 依傳入條件取得 應收帳務明細檔 的資料 for 已退匯或退回現金尚未沖轉收回案件清單
     * 
     * @param payCode 給付別
     * @return
     */
    public List<OtherRpt02Case> selectOtherRpt02DataListBy(String payCode) {
        // 查詢主檔資料
        List<Bapflbac> bapflbacList = bapflbacDao.selectOtherRpt02DataListBy(payCode);

        List<OtherRpt02Case> caseList = new ArrayList<OtherRpt02Case>();

        for (int i = 0; i < bapflbacList.size(); i++) {
            Bapflbac bapflbac = bapflbacList.get(i);

            OtherRpt02Case reportData = new OtherRpt02Case();
            BeanUtility.copyProperties(reportData, bapflbac);
            caseList.add(reportData);
        }
        return caseList;
    }

    /**
     * 依傳入條件取得 應收帳務明細檔 應收金額(<code>BAUNACPDTL</code>) 資料 for 已退匯或退回現金尚未沖轉收回案件清單
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @return BigDecimal
     */
    public BigDecimal selectOtherRpt02RecRemDataBy(String payCode, String apNo, String seqNo) {
        // 查詢 應收金額
        BigDecimal recRem = baunacpdtlDao.selectOtherRpt02RecRemDataBy(payCode, apNo, seqNo);

        if (recRem == null) {
            recRem = new BigDecimal("0");
        }

        return recRem;
    }

    /**
     * 依傳入條件取得 應收資料(<code>BAUNACPDTL</code>) 資料 for 應收收回處理作業
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<OtherRpt02Case> selectOtherRpt02SourceDataListBy(String apNo) {
        // 查詢主檔資料
        List<Baunacpdtl> baunacpdtlList = baunacpdtlDao.selectOtherRpt02SourceDataListBy(apNo);

        List<OtherRpt02Case> caseList = new ArrayList<OtherRpt02Case>();

        for (int i = 0; i < baunacpdtlList.size(); i++) {
            Baunacpdtl baunacpdtl = baunacpdtlList.get(i);

            if (baunacpdtl.getSource().equals("R")) {
                baunacpdtl.setSource("退匯");
            }
            else if (baunacpdtl.getSource().equals("S")) {
                baunacpdtl.setSource("退匯止付");
            }
            else {
                baunacpdtl.setSource("");
            }

            OtherRpt02Case reportData = new OtherRpt02Case();
            BeanUtility.copyProperties(reportData, baunacpdtl);
            caseList.add(reportData);
        }
        return caseList;
    }

    /**
     * 依傳入條件取得 退現資料檔(<code>PFPCCKY</code>) 資料 for 已退匯或退回現金尚未沖轉收回案件清單 - 退現
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<OtherRpt02Case> selectOtherRpt02CashSourceDataListBy(String apNo) {
        // 查詢主檔資料
        List<Pfpccky> localPfpcckyList = localPfpcckyDao.selectOtherRpt02CashSourceDataListBy(apNo);

        List<OtherRpt02Case> caseList = new ArrayList<OtherRpt02Case>();

        for (int i = 0; i < localPfpcckyList.size(); i++) {
            Pfpccky pfpccky = localPfpcckyList.get(i);

            if (pfpccky.getSource().equals("C")) {
                pfpccky.setSource("退回現金(" + pfpccky.getPer_Unit_Name() + ")");
            }
            else {
                pfpccky.setSource("");
            }

            OtherRpt02Case reportData = new OtherRpt02Case();
            BeanUtility.copyProperties(reportData, pfpccky);
            caseList.add(reportData);
        }
        return caseList;
    }

    /**
     * 依傳入條件取得 應收帳務明細檔(<code>BAUNACPDTL</code>) 資料 for 應收收回處理作業 - 退現
     * 
     * @param payCode 給付別
     * @return
     */
    public List<OtherRpt02Case> selectOtherRpt02CashDataListBy(String payCode) {
        // 查詢主檔資料
        List<Baunacpdtl> baunacpdtlList = baunacpdtlDao.selectOtherRpt02CashDataListBy(payCode);

        List<OtherRpt02Case> caseList = new ArrayList<OtherRpt02Case>();

        for (int i = 0; i < baunacpdtlList.size(); i++) {
            Baunacpdtl baunacpdtl = baunacpdtlList.get(i);

            OtherRpt02Case reportData = new OtherRpt02Case();
            BeanUtility.copyProperties(reportData, baunacpdtl);
            caseList.add(reportData);
        }
        return caseList;
    }

    /**
     * 依傳入條件取得 退匯檔 的資料 for 已退匯止付尚未應收款立帳案件清單
     * 
     * @param payCode 給付別
     * @return
     */
    public List<OtherRpt03Case> selectOtherRpt03DataListBy(String payCode) {
        // 查詢主檔資料
        List<Bapflbac> bapflbacList = bapflbacDao.selectOtherRpt03DataListBy(payCode);
        List<OtherRpt03Case> caseList = new ArrayList<OtherRpt03Case>();

        for (int i = 0; i < bapflbacList.size(); i++) {
            Bapflbac bapflbac = bapflbacList.get(i);

            OtherRpt03Case reportData = new OtherRpt03Case();
            BeanUtility.copyProperties(reportData, bapflbac);
            caseList.add(reportData);
        }
        return caseList;
    }

    /**
     * 依傳入的條件取得 受理案件統計表 的資料 page1
     * 
     * @param crtDate 受理日期
     * @return
     */
    public List<OtherRpt04Case> selectOtherRpt04Page1DataBy(String crtDate, String payCode) {
        List<OtherRpt04Case> caseList = new ArrayList<OtherRpt04Case>();

        // 受理日期民國轉西元
        String crtDateQuery = "";
        String crtYmQuery = "";

        if (StringUtils.length(crtDate) == 7)
            crtDateQuery = DateUtility.changeDateType(crtDate);
        crtYmQuery = crtDateQuery.substring(0, 6);

        List<Baappbase> dataList = new ArrayList<Baappbase>();

        if (payCode.equals("L")) {
            dataList = baappbaseDao.selectOtherRpt04Page1LDataBy(crtDateQuery, crtYmQuery);
        }
        else if (payCode.equals("K")) {
            dataList = baappbaseDao.selectOtherRpt04Page1KDataBy(crtDateQuery, crtYmQuery);
        }
        else if (payCode.equals("S")) {
            dataList = baappbaseDao.selectOtherRpt04Page1SDataBy(crtDateQuery, crtYmQuery);
        }
        else {
            dataList = baappbaseDao.selectOtherRpt04Page1DataBy(crtDateQuery, crtYmQuery);
        }

        for (Baappbase baappbase : dataList) {
            OtherRpt04Case caseData = new OtherRpt04Case();
            BeanUtility.copyProperties(caseData, baappbase);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 受理案件統計表 的資料 page2
     * 
     * @param crtDate 受理日期
     * @return
     */
    public List<OtherRpt04Case> selectOtherRpt04Page2DataBy(String crtDate, String payCode) {
        List<OtherRpt04Case> caseList = new ArrayList<OtherRpt04Case>();

        // 受理日期民國轉西元
        String crtDateQuery = "";
        String crtYmQuery = "";

        if (StringUtils.length(crtDate) == 7)
            crtDateQuery = DateUtility.changeDateType(crtDate);
        crtYmQuery = crtDateQuery.substring(0, 6);

        List<Baappbase> dataList = new ArrayList<Baappbase>();

        if (payCode.equals("L")) {
            dataList = baappbaseDao.selectOtherRpt04Page2LDataBy(crtDateQuery, crtYmQuery);
        }
        else if (payCode.equals("K")) {
            dataList = baappbaseDao.selectOtherRpt04Page2KDataBy(crtDateQuery, crtYmQuery);
        }
        else if (payCode.equals("S")) {
            dataList = baappbaseDao.selectOtherRpt04Page2SDataBy(crtDateQuery, crtYmQuery);
        }
        else {
            dataList = baappbaseDao.selectOtherRpt04Page2DataBy(crtDateQuery, crtYmQuery);
        }

        // 取出有幾個初核人員
        LinkedHashSet<String> crtUserSet = new LinkedHashSet<String>();
        for (Baappbase baappbase : dataList) {
            crtUserSet.add(baappbase.getCrtUser());
        }

        for (String crtUser : crtUserSet) {

            OtherRpt04Case caseDataDay = new OtherRpt04Case();
            caseDataDay.setCrtUser(crtUser);
            caseDataDay.setDm("D");

            OtherRpt04Case caseDataMonth = new OtherRpt04Case();
            caseDataMonth.setCrtUser(crtUser);
            caseDataMonth.setDm("M");

            for (Baappbase baappbase : dataList) {

                if (baappbase.getCrtUser().equals(crtUser) && baappbase.getDm().equals("D")) {
                    if (baappbase.getPayCode().equals("K1")) {
                        caseDataDay.setPayK1(baappbase.getRecCordCount());
                    }
                    else if (baappbase.getPayCode().equals("K2")) {
                        caseDataDay.setPayK2(baappbase.getRecCordCount());
                    }
                    else if (baappbase.getPayCode().equals("L1")) {
                        caseDataDay.setPayL1(baappbase.getRecCordCount());
                    }
                    else if (baappbase.getPayCode().equals("L2")) {
                        caseDataDay.setPayL2(baappbase.getRecCordCount());
                    }
                    else if (baappbase.getPayCode().equals("S1")) {
                        caseDataDay.setPayS1(baappbase.getRecCordCount());
                    }
                }
                else if (baappbase.getCrtUser().equals(crtUser) && baappbase.getDm().equals("M")) {
                    if (baappbase.getPayCode().equals("K1")) {
                        caseDataMonth.setPayK1(baappbase.getRecCordCount());
                    }
                    else if (baappbase.getPayCode().equals("K2")) {
                        caseDataMonth.setPayK2(baappbase.getRecCordCount());
                    }
                    else if (baappbase.getPayCode().equals("L1")) {
                        caseDataMonth.setPayL1(baappbase.getRecCordCount());
                    }
                    else if (baappbase.getPayCode().equals("L2")) {
                        caseDataMonth.setPayL2(baappbase.getRecCordCount());
                    }
                    else if (baappbase.getPayCode().equals("S1")) {
                        caseDataMonth.setPayS1(baappbase.getRecCordCount());
                    }
                }
            }

            caseList.add(caseDataDay);
            caseList.add(caseDataMonth);
        }

        // 計算每日 每月 合計
        for (OtherRpt04Case caseData : caseList) {
            if (caseData.getDm().equals("D")) {
                caseData.setSingleDTotal(caseData.getPayK1().add(caseData.getPayK2()).add(caseData.getPayL1()).add(caseData.getPayL2()).add(caseData.getPayS1()));
            }
            else if (caseData.getDm().equals("M")) {
                caseData.setSingleMTotal(caseData.getPayK1().add(caseData.getPayK2()).add(caseData.getPayL1()).add(caseData.getPayL2()).add(caseData.getPayS1()));
            }
        }

        return caseList;
    }

    /**
     * 勞保年金線上排程-更新排程作業狀態
     * 
     * @param baJobId 資料列編號(jobid)
     * @param nowWestDateTime 處理時間
     * @param status 處理狀態
     */
    public void updateBaBatchJobStatus(String baJobId, String nowWestDateTime, String status, String procType) {
        Babatchjob babatchjob = new Babatchjob();
        babatchjob.setBaJobId(baJobId);
        if (StringUtils.equals(status, "N") || StringUtils.equals(status, "E")) {
            babatchjob.setProcEndTime(nowWestDateTime);
            babatchjob.setProcType(procType);
        }
        else {
            babatchjob.setProcBegTime(nowWestDateTime);
        }
        babatchjob.setStatus(status);
        babatchjobDao.updateBaBatchJobStatus(babatchjob);

    }

    /**
     * 取出勞保年金作業目前要處理的工作
     * 
     * @param baJobId 資料列編號(JOBID)
     */
    public Babatchjob getBaBatchJobData(String baJobId) {
        Babatchjob baBatchJobData = babatchjobDao.getBaBatchJobData(baJobId);
        return baBatchJobData;
    }

    /**
     * 查詢 給付主檔(<code>BAAPPBASE</code>) 案件資料 FOR 核定通知書 批次
     */
    public List<String> selectForPrintNotify(String payCode, String issuYm, String rptKind) {
        return baappbaseDao.selectForPrintNotify(payCode, issuYm, rptKind);
    }

    /**
     * 查詢 給付主檔(<code>BAAPPBASE</code>) 案件資料 FOR 核定通知書 批次 遺屬
     */
    public List<String> selectSurvivorForPrintNotify(String payCode, String issuYm) {
        return baappbaseDao.selectSurvivorForPrintNotify(payCode, issuYm);
    }

    /**
     * 查詢 給付主檔(<code>BAAPPBASE</code>) 案件資料 FOR 核定通知書 批次 失能
     */
    public List<String> selectDisabledForPrintNotify(String payCode, String issuYm) {
        return baappbaseDao.selectSurvivorForPrintNotify(payCode, issuYm);
    }

    /**
     * 查詢勞核付明細表排程目前佇列中相同條件的狀態
     * 
     * @param issuYm 核定年月
     * @param chkDate 核定日期
     * @param payCode 給付別
     * @param procType 處理類別
     * @param paySeqNo 傳入值(35,38:1;36:2)
     * @return Babatchjob
     */
    public Babatchjob doScheduleBatchJobStatus(String issuYm, String chkDate, String payCode, String procType, String paySeqNo) {
        return babatchjobDao.doScheduleBatchJobStatus(issuYm, chkDate, payCode, procType, paySeqNo);
    }

    /**
     * 勞保年金線上產製核付明細表將作業計入排程
     * 
     * @param issuYm 核定年月
     * @param chkDate 核定日期
     * @param payCode 給付別
     * @param procEmpNo 執行作業人員員工編號
     * @param procDeptId 執行作業人員單位代碼
     * @param procIp 執行作業人員IP
     * @param procBegTime 處裡起始日期時間
     * @param procType 處理類型
     * @param status 處理狀態
     * @param paySeqNo 欄位值
     */
    public void doScheduleBatchJob(String baJobId, String issuYm, String chkDate, String payCode, String procEmpNo, String procDeptId, String procIp, String procBegTime, String procType, String status, String paySeqNo) {
        Babatchjob babatchjob = new Babatchjob();
        babatchjob.setBaJobId(baJobId);
        babatchjob.setIssuYm(issuYm);
        babatchjob.setChkDate(chkDate);
        babatchjob.setPayCode(payCode);
        babatchjob.setProcEmpNo(procEmpNo);
        babatchjob.setProcDeptId(procDeptId);
        babatchjob.setProcIp(procIp);
        babatchjob.setProcType(procType);
        babatchjob.setStatus(status);
        babatchjob.setPaySeqNo(paySeqNo);
        babatchjobDao.insertBatchJobM(babatchjob);
    }

    /**
     * 批次處理 - 合格清冊產製報表進度查詢
     * 
     * @param procType 處理類型
     * @param issuYm 核定年月
     * @param chkDate 核定日期
     * @param payCode 給付別
     * @param paySeqNo 欄位值預設為1
     * @return
     */
    public List<MonthlyRpt08ProgressCase> getRpt08ProgressStep(String procType, String issuYm, String chkDate, String payCode, String paySeqNo) {
        List<Babatchjob> dataList = babatchjobDao.selectNowProgressSteps(procType, issuYm, chkDate, payCode, paySeqNo);
        List<MonthlyRpt08ProgressCase> caseList = new ArrayList<MonthlyRpt08ProgressCase>();
        for (int i = 0; i < dataList.size(); i++) {
            Babatchjob babatchjob = dataList.get(i);
            MonthlyRpt08ProgressCase returnCase = new MonthlyRpt08ProgressCase();
            returnCase.setBaJobId(babatchjob.getBaJobId());
            returnCase.setChkDate(DateUtility.changeDateType(babatchjob.getChkDate()));
            returnCase.setIssuYm(DateUtility.changeWestYearMonthType(babatchjob.getIssuYm()));
            if (StringUtils.equals(babatchjob.getPayCode(), "L")) {
                returnCase.setPayCode("老年");
            }
            else if (StringUtils.equals(babatchjob.getPayCode(), "K")) {
                if (StringUtils.equals(paySeqNo, "2")) {
                    returnCase.setPayCode("失能-36");
                }
                else {
                    returnCase.setPayCode("失能");
                }
            }
            else if (StringUtils.equals(babatchjob.getPayCode(), "S")) {
                returnCase.setPayCode("遺屬");
            }
            else {
                returnCase.setPayCode("");
            }
            if (StringUtils.equals(babatchjob.getStatus(), "E")) {
                returnCase.setStatus("執行結束");
            }
            else if (StringUtils.equals(babatchjob.getStatus(), "N")) {
                returnCase.setStatus("產製錯誤");
            }
            else if (StringUtils.equals(babatchjob.getStatus(), "W")) {
                returnCase.setStatus("等待中");
            }
            else {
                returnCase.setStatus("執行中");
            }

            returnCase.setRowNum(i + 1);
            if (StringUtils.equals(procType, "7")) {
                returnCase.setProcType("產製月核定合格清冊");
            }
            caseList.add(returnCase);
        }
        if (caseList == null || caseList.size() <= 0) {
            return null;
        }
        else {
            return caseList;
        }
    }

    /**
     * 批次處理 - 核付明細表產製報表進度查詢
     * 
     * @param procType 處理類型
     * @param issuYm 核定年月
     * @param chkDate 核定日期
     * @param payCode 給付別
     * @param paySeqNo 欄位值預設為1
     * @return
     */
    public List<MonthlyRpt10ProgressCase> getRpt10ProgressStep(String procType, String issuYm, String chkDate, String payCode, String paySeqNo) {
        List<Babatchjob> dataList = babatchjobDao.selectNowProgressSteps(procType, issuYm, chkDate, payCode, paySeqNo);
        List<MonthlyRpt10ProgressCase> caseList = new ArrayList<MonthlyRpt10ProgressCase>();
        for (int i = 0; i < dataList.size(); i++) {
            Babatchjob babatchjob = dataList.get(i);
            MonthlyRpt10ProgressCase returnCase = new MonthlyRpt10ProgressCase();
            returnCase.setBaJobId(babatchjob.getBaJobId());
            returnCase.setChkDate(DateUtility.changeDateType(babatchjob.getChkDate()));
            returnCase.setIssuYm(DateUtility.changeWestYearMonthType(babatchjob.getIssuYm()));
            if (StringUtils.equals(babatchjob.getPayCode(), "L")) {
                returnCase.setPayCode("老年");
            }
            else if (StringUtils.equals(babatchjob.getPayCode(), "K")) {
                if (StringUtils.equals(paySeqNo, "2")) {
                    returnCase.setPayCode("失能-36");
                }
                else {
                    returnCase.setPayCode("失能");
                }

            }
            else if (StringUtils.equals(babatchjob.getPayCode(), "S")) {
                returnCase.setPayCode("遺屬");
            }
            else {
                returnCase.setPayCode("");
            }
            if (StringUtils.equals(babatchjob.getStatus(), "E")) {
                returnCase.setStatus("執行結束");
            }
            else if (StringUtils.equals(babatchjob.getStatus(), "N")) {
                returnCase.setStatus("產製錯誤");
            }
            else if (StringUtils.equals(babatchjob.getStatus(), "W")) {
                returnCase.setStatus("等待中");
            }
            else {
                returnCase.setStatus("執行中");
            }

            returnCase.setRowNum(i + 1);
            if (StringUtils.equals(procType, "6")) {
                returnCase.setProcType("產製核付明細表");
            }
            caseList.add(returnCase);
        }
        if (caseList == null || caseList.size() <= 0) {
            return null;
        }
        else {
            return caseList;
        }
    }

    /**
     * 依傳入的條件取得轉催收核定清單 的資料
     * 
     * @param payCode 給付別
     * @param procYm 處理年月
     * @param paySeqNo 欄位值
     * @return
     */
    public List<OtherRpt06Case> getOtherRpt06DataBy(String payCode, String procYm, String paySeqNo, String isNull, String isEqual, String nowDate) {
        // 20120406 資料改由原本抓Bapayrptrecord，變成抓Bapayrptsum
        List<Bapayrptsum> bapayrptsumList = bapayrptsumDao.selectOtherRpt06DataBy(payCode, procYm, paySeqNo, isNull, isEqual, nowDate);
        List<OtherRpt06Case> caseList = new ArrayList<OtherRpt06Case>();

        for (Bapayrptsum badapr : bapayrptsumList) {
            OtherRpt06Case caseData = new OtherRpt06Case();
            BeanUtility.copyProperties(caseData, badapr);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 轉催收核定清單 的資料
     * 
     * @param payCode 給付別
     * @param procYm 處理年月
     * @param paySeqNo 欄位值
     * @return
     */
    public List<OtherRpt06Case> getOtherRpt06DataFooterBy(String payCode, String procYm, String paySeqNo, String isNull, String isEqual, String nowDate) {
        List<Bapayrptaccount> bapayrptrecordList = bapayrptaccountDao.selectOtherRpt06DataBy(payCode, procYm, paySeqNo, isNull, isEqual, nowDate);
        List<OtherRpt06Case> caseList = new ArrayList<OtherRpt06Case>();

        for (Bapayrptaccount badapr : bapayrptrecordList) {
            OtherRpt06Case caseData = new OtherRpt06Case();
            BeanUtility.copyProperties(caseData, badapr);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得轉催收核定清單 的資料
     * 
     * @param payCode 給付別
     * @param procYm 處理年月
     * @param paySeqNo 欄位值
     * @return
     */
    public List<OtherRpt07Case> getOtherRpt07DataBy(String payCode, String procYm, String paySeqNo, String isNull, String isEqual) {
        List<OtherRpt07Case> caseList = new ArrayList<OtherRpt07Case>();
        // 取得報表資料
        List<Bapayrptrecord> dataList = bapayrptrecordDao.getOtherRpt07DataBy(payCode, procYm, paySeqNo, isNull, isEqual);
        bapayrptrecordDao.updateOtherRpt07DataBy(payCode, procYm, paySeqNo, isNull, isEqual);
        for (Bapayrptrecord rptData : dataList) {
            OtherRpt07Case caseData = new OtherRpt07Case();
            BeanUtility.copyProperties(caseData, rptData);

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得轉催收核定清冊 的資料 -補印
     * 
     * @param payCode 給付別
     * @param procYm 處理年月
     * @param paySeqNo 欄位值
     * @return
     */
    public List<OtherRpt07Case> getOtherRpt07CompDataBy(String payCode, String demDate, String isNull, String isEqual) {
        List<OtherRpt07Case> caseList = new ArrayList<OtherRpt07Case>();
        // 取得報表資料
        List<Bapayrptrecord> dataList = bapayrptrecordDao.getOtherRptComp07DataBy(payCode, demDate, isNull, isEqual);

        for (Bapayrptrecord rptData : dataList) {
            OtherRpt07Case caseData = new OtherRpt07Case();
            BeanUtility.copyProperties(caseData, rptData);

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 轉呆帳核定清冊 的資料
     * 
     * @param payCode 給付別
     * @param procYm 處理年月
     * @param paySeqNo 欄位值
     * @return
     */
    public List<OtherRpt08Case> getOtherRpt08DataBy(String payCode, String apNo, String deadYy, String paySeqNo, String isNull, String isEqual, String nowDate) {
        // 20120406 資料改由原本抓Bapayrptrecord，變成抓Bapayrptsum
        // List<Bapayrptrecord> bapayrptrecordList = bapayrptrecordDao.selectMonthlyRpt20DataBy(payCode, issuYm, chkDate);
        List<Bapayrptsum> bapayrptsumList = bapayrptsumDao.selectOtherRpt08DataBy(payCode, apNo, deadYy, paySeqNo, isNull, isEqual, nowDate);
        List<OtherRpt08Case> caseList = new ArrayList<OtherRpt08Case>();

        for (Bapayrptsum badapr : bapayrptsumList) {
            OtherRpt08Case caseData = new OtherRpt08Case();
            BeanUtility.copyProperties(caseData, badapr);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 轉呆帳核定清單 的資料
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @param deadYy 呆帳年度
     * @param paySeqNo 欄位值
     * @return
     */
    public List<OtherRpt08Case> getOtherRpt08DataFooterBy(String payCode, String apNo, String deadYy, String paySeqNo, String isNull, String isEqual, String nowDate) {
        List<Bapayrptaccount> bapayrptrecordList = bapayrptaccountDao.selectOtherRpt08DataBy(payCode, apNo, deadYy, paySeqNo, isNull, isEqual, nowDate);
        List<OtherRpt08Case> caseList = new ArrayList<OtherRpt08Case>();

        for (Bapayrptaccount badapr : bapayrptrecordList) {
            OtherRpt08Case caseData = new OtherRpt08Case();
            BeanUtility.copyProperties(caseData, badapr);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 轉呆帳核定清冊 的資料
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @param deadYy 呆帳年度
     * @param paySeqNo 欄位值
     * @return
     */
    public List<OtherRpt09Case> getOtherRpt09DataBy(String payCode, String apNo, String deadYy, String paySeqNo, String isNull, String isEqual, String nowDate) {
        List<OtherRpt09Case> caseList = new ArrayList<OtherRpt09Case>();
        // 取得報表資料
        List<Bapayrptrecord> dataList = bapayrptrecordDao.getOtherRpt09DataBy(payCode, apNo, deadYy, paySeqNo, isNull, isEqual);
        bapayrptrecordDao.updateOtherRpt09DataBy(payCode, apNo, deadYy, paySeqNo, isNull, isEqual);

        for (Bapayrptrecord rptData : dataList) {
            OtherRpt09Case caseData = new OtherRpt09Case();
            BeanUtility.copyProperties(caseData, rptData);

            caseList.add(caseData);
        }

        return caseList;

    }

    /**
     * 依傳入的條件取得 轉呆帳核定清冊 的資料
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @param deadYy 呆帳年度
     * @param paySeqNo 欄位值
     * @return
     */
    public List<OtherRpt09Case> getOtherRptComp09DataBy(String payCode, String bDebtDate, String isNull, String isEqual) {
        List<OtherRpt09Case> caseList = new ArrayList<OtherRpt09Case>();
        // 取得報表資料
        List<Bapayrptrecord> dataList = bapayrptrecordDao.getOtherRptComp09DataBy(payCode, bDebtDate, isNull, isEqual);

        for (Bapayrptrecord rptData : dataList) {
            OtherRpt09Case caseData = new OtherRpt09Case();
            BeanUtility.copyProperties(caseData, rptData);

            caseList.add(caseData);
        }

        return caseList;

    }

    /**
     * procedure 產生 轉催/轉呆 資料
     * 
     * @return
     */
    public String execOverDueRptType(String payCode, String procYm, String paySeqNo, String cprnDt, String rptTyp, String apNo, String deadYy) {
        String flag = baunacpdtlDao.execRptOverdue(payCode, procYm, paySeqNo, cprnDt, rptTyp, apNo, deadYy);
        log.error(flag);
        return flag;
    }

    /**
     * 依傳入的條件取得 轉呆帳核定清單 明細資料
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @param deadYy 呆帳年度
     * @param paySeqNo 欄位值
     * @return
     */
    public List<OtherRpt08Case> getBadDebtDtl(String payCode, String deadYy, String apNo, String isNull, String isEqual) {
        List<OtherRpt08Case> caseList = new ArrayList<OtherRpt08Case>();
        // 取得報表資料
        List<Baunacpdtl> dataList = baunacpdtlDao.getBadDebtDtlDataBy(payCode, apNo, isNull, isEqual);

        for (Baunacpdtl rptData : dataList) {
            OtherRpt08Case caseData = new OtherRpt08Case();
            BeanUtility.copyProperties(caseData, rptData);
            caseData.setbDebtYear(DateUtility.changeChineseYearType(deadYy));
            caseData.setIssuYm(DateUtility.changeWestYearMonthType(rptData.getIssuYm()));
            caseData.setPayYm(DateUtility.changeWestYearMonthType(rptData.getPayYm()));
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 轉呆帳確認儲存 - 修改應收未收檔
     * 
     * @param apNo 受理編號
     * @param userData 使用者資料
     */
    public void updateAllBadDebtData(String indexList, List<OtherRpt08Case> dataList) {
        // 更新資料到 應收未收檔
        // [
        String[] tmpIndexList = indexList.split(",");
        for (int i = 0; i < tmpIndexList.length; i++) {
            String dataIndex = tmpIndexList[i];
            if (StringUtils.isNotBlank(dataIndex)) {

                OtherRpt08Case caseObj = (OtherRpt08Case) dataList.get(Integer.parseInt(dataIndex));
                Baunacpdtl baunacpdtl = new Baunacpdtl(); // 給付主檔改前值

                BeanUtility.copyProperties(baunacpdtl, caseObj);
                baunacpdtlDao.updateDataForBadDebt(baunacpdtl);

            }
        }
    }

    /**
     * 依傳入的條件取得轉催收核定清單 的資料- 補印
     * 
     * @param payCode 給付別
     * @param demDate 轉催收日期
     * @return
     */
    public List<OtherRpt06Case> getOtherRpt06CompRptBy(String payCode, String demDate, String isNull, String isEqual) {
        List<Bapayrptsum> bapayrptsumList = bapayrptsumDao.selectOtherRpt06CompRptBy(payCode, demDate, isNull, isEqual);
        List<OtherRpt06Case> caseList = new ArrayList<OtherRpt06Case>();

        for (Bapayrptsum badapr : bapayrptsumList) {
            OtherRpt06Case caseData = new OtherRpt06Case();
            BeanUtility.copyProperties(caseData, badapr);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得轉催收核定清單 的資料- 補印
     * 
     * @param payCode 給付別
     * @param demDate 轉催收日期
     * @return
     */
    public List<OtherRpt06Case> getOtherRpt06CompRptFooterBy(String payCode, String demDate, String isNull, String isEqual) {
        List<Bapayrptaccount> bapayrptrecordList = bapayrptaccountDao.selectOtherRpt06CompRptBy(payCode, demDate, isNull, isEqual);
        List<OtherRpt06Case> caseList = new ArrayList<OtherRpt06Case>();

        for (Bapayrptaccount badapr : bapayrptrecordList) {
            OtherRpt06Case caseData = new OtherRpt06Case();
            BeanUtility.copyProperties(caseData, badapr);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得轉催收核定清單 的資料- 補印
     * 
     * @param payCode 給付別
     * @param demDate 轉催收日期
     * @return
     */
    public List<OtherRpt08Case> getOtherRpt08CompRptBy(String payCode, String bDebtDate, String isNull, String isEqual) {
        List<Bapayrptsum> bapayrptsumList = bapayrptsumDao.selectOtherRpt08CompRptBy(payCode, bDebtDate, isNull, isEqual);
        List<OtherRpt08Case> caseList = new ArrayList<OtherRpt08Case>();

        for (Bapayrptsum badapr : bapayrptsumList) {
            OtherRpt08Case caseData = new OtherRpt08Case();
            BeanUtility.copyProperties(caseData, badapr);
            caseList.add(caseData);
        }

        return caseList;

    }

    /**
     * 依傳入的條件取得轉催收核定清單 的資料- 補印
     * 
     * @param payCode 給付別
     * @param demDate 轉催收日期
     * @return
     */
    public List<OtherRpt08Case> getOtherRpt08CompRptFooterBy(String payCode, String bDebtDate, String isNull, String isEqual) {
        List<Bapayrptaccount> bapayrptrecordList = bapayrptaccountDao.selectOtherRpt08CompRptBy(payCode, bDebtDate, isNull, isEqual);
        List<OtherRpt08Case> caseList = new ArrayList<OtherRpt08Case>();

        for (Bapayrptaccount badapr : bapayrptrecordList) {
            OtherRpt08Case caseData = new OtherRpt08Case();
            BeanUtility.copyProperties(caseData, badapr);
            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 穿透案件統計表 的資料
     * 
     * @param issuYm 核定年月
     * @return
     */
    public List<OtherRpt10Case> selectOtherRpt10DataBy(String issuYm, String issuDate, String preIssuDate) {
        List<OtherRpt10Case> caseList = new ArrayList<OtherRpt10Case>();

        // 核定年月民國轉西元
        String issuYmQuery = "";

        if (StringUtils.length(issuYm) == 5)
            issuYmQuery = DateUtility.changeChineseYearMonthType(issuYm);

        List<Badapr> dataList = badaprDao.selectOtherRpt10DataBy(issuYmQuery, issuDate, preIssuDate);

        if (dataList.size() > 0) {

            // 抓取初核人員種類
            LinkedHashSet<String> chkManSet = new LinkedHashSet<String>();
            for (Badapr caseData : dataList) {
                chkManSet.add(caseData.getChkMan());
            }
            final List<String> chkManList = new ArrayList<String>();
            for (String value : chkManSet) {
                chkManList.add(value);
            }

            // 每二筆放入一個case
            int index = 0;

            // 計算需做幾個case
            int listSize = dataList.size();
            int times = 0;
            if (listSize % 2 != 0) {
                times = listSize / 2 + 1;
            }
            else {
                times = listSize / 2;
            }

            for (String value : chkManList) {

                for (int x = 0; x < times; x++) {
                    OtherRpt10Case caseObj = new OtherRpt10Case();

                    for (int i = 0; i < 2; i++) {

                        if (i == 0) {
                            if (index < listSize) {
                                Badapr obj = dataList.get(index);

                                if (StringUtils.equalsIgnoreCase(value, obj.getChkMan())) {
                                    caseObj.setApNo(obj.getApNo());
                                    caseObj.setCaseTyp(obj.getCaseTyp());
                                    caseObj.setSeqNo(obj.getSeqNo());
                                    caseObj.setChkMan(obj.getChkMan());
                                    index++;
                                }
                            }
                        }
                        if (i == 1) {
                            if (index < listSize) {
                                Badapr obj = dataList.get(index);
                                if (StringUtils.equalsIgnoreCase(value, obj.getChkMan())) {
                                    caseObj.setApNo1(obj.getApNo());
                                    caseObj.setCaseTyp1(obj.getCaseTyp());
                                    caseObj.setSeqNo1(obj.getSeqNo());
                                    index++;
                                }
                            }
                        }
                    }

                    if (caseObj.getApNo() != null)
                        caseList.add(caseObj);

                }

            }
            /**
             * for (Badapr badapr : dataList) { OtherRpt10Case caseData = new OtherRpt10Case(); BeanUtility.copyProperties(caseData, badapr); caseList.add(caseData); }
             **/

            return caseList;

        }
        else {
            return caseList;
        }
    }

    /**
     * 依傳入的條件取得 穿透案件統計表 的資料
     * 
     * @param issuYm 核定年月
     * @return
     */
    public int selectTotalCountFromOtherRpt10DataBy(String issuYm, String issuDate, String preIssuDate) {

        // 核定年月民國轉西元
        String issuYmQuery = "";

        if (StringUtils.length(issuYm) == 5)
            issuYmQuery = DateUtility.changeChineseYearMonthType(issuYm);

        List<Badapr> dataList = badaprDao.selectOtherRpt10DataBy(issuYmQuery, issuDate, preIssuDate);

        if (dataList.size() > 0) {

            int totalCount = dataList.size();

            return totalCount;

        }
        else {
            return 0;
        }
    }

    /**
     * 依傳入條件取得 退現資料(<code>localPfpccky</code>) 資料 for 整批收回核定清單
     * 
     * @param payCode 給付別
     * @param chkDate 核收日期
     * @return
     */
    public List<BatchPaymentReceiveDataCase> selectCashReceiveDataListBy(String payCode, String chkDate) {
        List<Pfpccky> list = localPfpcckyDao.selectBatchPaymentReceiveDataListBy(payCode, chkDate);
        List<BatchPaymentReceiveDataCase> returnList = new ArrayList<BatchPaymentReceiveDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Pfpccky obj = list.get(i);
            BatchPaymentReceiveDataCase caseObj = new BatchPaymentReceiveDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            if (StringUtils.isNotBlank(caseObj.getBkAccountDt())) {
                caseObj.setBkAccountDt(DateUtility.changeWestYearType(caseObj.getBkAccountDt().substring(0, 4)) + caseObj.getBkAccountDt().substring(4, caseObj.getBkAccountDt().length()));
            }
            returnList.add(caseObj);
        }

        return returnList;
    }

    /**
     * 依傳入條件取得 退現資料 (<code>localPfpccky</code>) 資料 for 整批收回核定清單
     * 
     * @param payCode 給付別
     * @param chkDate 核收日期
     * @return
     */
    public List<BatchPaymentReceiveDataCase> selectCashReceiveDataMasterListBy(String payCode, String chkDate) {
        List<Pfpccky> list = localPfpcckyDao.selectBatchPaymentReceiveDataMasterListBy(payCode, chkDate);
        List<BatchPaymentReceiveDataCase> returnList = new ArrayList<BatchPaymentReceiveDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Pfpccky obj = list.get(i);
            BatchPaymentReceiveDataCase caseObj = new BatchPaymentReceiveDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            if (StringUtils.isNotBlank(caseObj.getBkAccountDt())) {
                caseObj.setBkAccountDt(DateUtility.changeWestYearType(caseObj.getBkAccountDt().substring(0, 4)) + caseObj.getBkAccountDt().substring(4, caseObj.getBkAccountDt().length()));
            }
            returnList.add(caseObj);
        }

        return returnList;
    }

    /**
     * 依傳入的條件取得 整批收回應收已收清冊 的資料
     * 
     * @param rptTyp 報表類別
     * @param payCode 給付別
     * @param chkDate 核定日期
     * @param cPrnDate 列印日期
     * @return
     */
    public List<BatchPaymentReceiveDataCase> getBatchPaymentReceiveDataBy(String rptTyp, String payCode, String chkDate, String cPrnDate, String isNullOrNot, String eqOrNot) {
        List<BatchPaymentReceiveDataCase> caseList = new ArrayList<BatchPaymentReceiveDataCase>();
        // 取得報表資料
        List<Bapayrptrecord> dataList = bapayrptrecordDao.getBatchPaymentReceiveDataBy(rptTyp, payCode, chkDate, cPrnDate, isNullOrNot, eqOrNot);

        for (Bapayrptrecord rptData : dataList) {
            BatchPaymentReceiveDataCase caseData = new BatchPaymentReceiveDataCase();
            BeanUtility.copyProperties(caseData, rptData);

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 整批收回應收已收清冊 的資料
     * 
     * @param rptTyp 報表類別
     * @param payCode 給付別
     * @param chkDate 核定日期
     * @param cPrnDate 列印日期
     * @return
     */
    public List<BatchPaymentReceiveDataCase> selectBatchPaymentReceiveSumDataBy(String rptTyp, String payCode, String chkDate, String cPrnDate, String isNullOrNot, String eqOrNot) {
        List<BatchPaymentReceiveDataCase> caseList = new ArrayList<BatchPaymentReceiveDataCase>();
        // 取得報表資料
        List<Bapayrptsum> dataList = bapayrptsumDao.getBatchPaymentReceiveDataBy(rptTyp, payCode, chkDate, cPrnDate, isNullOrNot, eqOrNot);

        for (Bapayrptsum rptData : dataList) {
            BatchPaymentReceiveDataCase caseData = new BatchPaymentReceiveDataCase();
            BeanUtility.copyProperties(caseData, rptData);

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 整批收回應收已收清冊 的資料
     * 
     * @param rptTyp 報表類別
     * @param payCode 給付別
     * @param chkDate 核定日期
     * @param cPrnDate 列印日期
     * @return
     */
    public List<BatchPaymentReceiveDataCase> selectBatchPaymentReceiveAccountDataBy(String rptTyp, String payCode, String chkDate, String cPrnDate, String isNullOrNot, String eqOrNot) {
        List<BatchPaymentReceiveDataCase> caseList = new ArrayList<BatchPaymentReceiveDataCase>();
        // 取得報表資料
        List<Bapayrptaccount> dataList = bapayrptaccountDao.getBatchPaymentReceiveDataBy(rptTyp, payCode, chkDate, cPrnDate, isNullOrNot, eqOrNot);

        for (Bapayrptaccount rptData : dataList) {
            BatchPaymentReceiveDataCase caseData = new BatchPaymentReceiveDataCase();
            BeanUtility.copyProperties(caseData, rptData);

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入的條件取得 整批收回應收已收清冊 的資料
     * 
     * @param rptTyp 報表類別
     * @param payCode 給付別
     * @param chkDate 核定日期
     * @param cPrnDate 列印日期
     * @return
     */
    public List<BatchPaymentReceiveDataCase> getFailureListDataBy(String payCode, String chkDate) {
        List<BatchPaymentReceiveDataCase> caseList = new ArrayList<BatchPaymentReceiveDataCase>();
        // 取得報表資料
        List<Bafailurelist> dataList = bafailurelistDao.selectBafailureListDataBy(payCode, chkDate);

        for (Bafailurelist rptData : dataList) {
            BatchPaymentReceiveDataCase caseData = new BatchPaymentReceiveDataCase();
            BeanUtility.copyProperties(caseData, rptData);

            caseList.add(caseData);
        }

        return caseList;
    }

    /**
     * 依傳入條件取得 月核定日期參數檔 (<code>BAPAISSUDATE</code>) 資料 for 穿透案件統計表
     * 
     * @param payCode 給付別
     * @param issuYm
     * @return
     */
    public String selectIssuDateForOtherRpt10(String payCode, String issuYm) {

        String issuDate = bapaissudateDao.selectIssuDateForOtherRpt10(payCode, issuYm);
        return issuDate;
    }
    
    //Added by LouisChange 20200311 begin
    /**
     * 依傳入條件取得羅馬拼音, 若沒有羅馬拼音則返回相對應中文姓名長度的空白格字串
     * 
     * @param idnno 身分證號
     * @param brDate 生日
     * @param benName 中文名稱
     * @return
     */
    private String getRmpName(String idnno, String brDate, String benName) {
    	String result = "";
    	//取得原住民羅馬拼音
    	if (StringUtils.isNotBlank(idnno) && StringUtils.isNotBlank(brDate)) {
        	List<Cvldtl> cvldtls = cvldtlDao.selectRmpNameBy(idnno, brDate);
        	if (CollectionUtils.isNotEmpty(cvldtls) && cvldtls.size() == 1) {
        		Cvldtl cvldtl = cvldtls.get(0);
        		if (cvldtl != null && ("1".equals(cvldtl.getOlivCode()) || "2".equals(cvldtl.getOlivCode()))) {
        			if (StringUtils.isNotBlank(cvldtl.getRmp_Name())) {
        				result = cvldtl.getRmp_Name();
        			}
        		}
        	}
        }
    	//若沒有原住民羅馬拼音時, 依中文姓名的內容添加半形空白格
    	if (StringUtils.isBlank(result) && StringUtils.isNotBlank(benName)) {
        	for (int i = 0; i < benName.length(); i++) {
        		char character = benName.charAt(i);
        		result += " ";  //任何字元至少有一個半形空白格
        		if (Character.UnicodeBlock.of(character) != Character.UnicodeBlock.BASIC_LATIN || !isHalfWidth(character)) {
        			result += " ";  //該字元若不為英文字或半形時, 再添加一個半形空白格
        		}
        	}
        }
    	return result;
    }
    
    /**
     * 判斷該字元是否為半形
     * 
     * @param c
     * @return
     */
    private boolean isHalfWidth(char c) {
    	return '\u0000' <= c && c <= '\u00FF'
    		|| '\uFF61' <= c && c <= '\uFFDC'
    		|| '\uFFE8' <= c && c <= '\uFFEE';
    }
    //Added by LouisChange 20200311 begin

    // =========================================================================================

    public Babatchjob doQueryBatchJobStatusforOtherRpt05Action(String changeChineseYearMonthType, String payCode, String fileName) {
		return babatchjobDao.doQueryBatchJobStatusforOtherRpt05Action(changeChineseYearMonthType, payCode, fileName);
	}
    
    public void doScheduleBatchJobforOtherRpt05Action(String jobId, String issuYm, String fileName ,String status, String payCode,UserInfo userData){		
		Babatchjob babatchjob = new Babatchjob();
		babatchjob.setBaJobId(jobId);
		babatchjob.setIssuYm(issuYm);
		babatchjob.setPayCode(payCode);
		babatchjob.setProcEmpNo(userData.getUserId());
		babatchjob.setPaySeqNo(" ");
		babatchjob.setFileName(fileName);
		babatchjob.setProcBegTime(DateUtility.getNowWestDateTime(true));
		babatchjob.setProcType("13");
		babatchjob.setStatus(status);
		
		babatchjobDao.doScheduleBatchJobforOtherRpt05Action(babatchjob);
	}
    
    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }

    public void setBaappbaseDao(BaappbaseDao baappbaseDao) {
        this.baappbaseDao = baappbaseDao;
    }

    public void setBadaprDao(BadaprDao badaprDao) {
        this.badaprDao = badaprDao;
    }

    public void setBachkfileDao(BachkfileDao bachkfileDao) {
        this.bachkfileDao = bachkfileDao;
    }

    public void setBaexalistDao(BaexalistDao baexalistDao) {
        this.baexalistDao = baexalistDao;
    }

    public void setLnmDao(LnmDao lnmDao) {
        this.lnmDao = lnmDao;
    }

    public void setBalp0d340Dao(Balp0d340Dao balp0d340Dao) {
        this.balp0d340Dao = balp0d340Dao;
    }

    public void setBalp0d330Dao(Balp0d330Dao balp0d330Dao) {
        this.balp0d330Dao = balp0d330Dao;
    }

    public void setBaparamDao(BaparamDao baparamDao) {
        this.baparamDao = baparamDao;
    }

    public void setPbbmsaDao(PbbmsaDao pbbmsaDao) {
        this.pbbmsaDao = pbbmsaDao;
    }

    public void setLnmdDao(LnmdDao lnmdDao) {
        this.lnmdDao = lnmdDao;
    }

    public void setBanotifyDao(BanotifyDao banotifyDao) {
        this.banotifyDao = banotifyDao;
    }

    public void setBapaissudateDao(BapaissudateDao bapaissudateDao) {
        this.bapaissudateDao = bapaissudateDao;
    }

    public void setBaoncepayDao(BaoncepayDao baoncepayDao) {
        this.baoncepayDao = baoncepayDao;
    }

    public void setBirefDao(BirefDao birefDao) {
        this.birefDao = birefDao;
    }

    public void setNbappbaseDao(NbappbaseDao nbappbaseDao) {
        this.nbappbaseDao = nbappbaseDao;
    }

    public void setNbdaprDao(NbdaprDao nbdaprDao) {
        this.nbdaprDao = nbdaprDao;
    }

    public void setCiptDao(CiptDao ciptDao) {
        this.ciptDao = ciptDao;
    }

    public void setCaubDao(CaubDao caubDao) {
        this.caubDao = caubDao;
    }

    public void setBadupeidnDao(BadupeidnDao badupeidnDao) {
        this.badupeidnDao = badupeidnDao;
    }

    public void setBagivetmpdtlDao(BagivetmpdtlDao bagivetmpdtlDao) {
        this.bagivetmpdtlDao = bagivetmpdtlDao;
    }

    public void setBapayrptrecordDao(BapayrptrecordDao bapayrptrecordDao) {
        this.bapayrptrecordDao = bapayrptrecordDao;
    }

    public void setBapflbacDao(BapflbacDao bapflbacDao) {
        this.bapflbacDao = bapflbacDao;
    }

    public void setBarxfDao(BarxfDao barxfDao) {
        this.barxfDao = barxfDao;
    }

    public void setBbarfDao(BbarfDao bbarfDao) {
        this.bbarfDao = bbarfDao;
    }

    public void setBapapaykindDao(BapapaykindDao bapapaykindDao) {
        this.bapapaykindDao = bapapaykindDao;
    }

    public void setKcafDao(KcafDao kcafDao) {
        this.kcafDao = kcafDao;
    }

    public void setMaadmrecDao(MaadmrecDao maadmrecDao) {
        this.maadmrecDao = maadmrecDao;
    }

    public void setBalp0d020Dao(Balp0d020Dao balp0d020Dao) {
        this.balp0d020Dao = balp0d020Dao;
    }

    public void setBaappexpandDao(BaappexpandDao baappexpandDao) {
        this.baappexpandDao = baappexpandDao;
    }

    public void setBafamilyDao(BafamilyDao bafamilyDao) {
        this.bafamilyDao = bafamilyDao;
    }

    public void setBabcml7Dao(Babcml7Dao babcml7Dao) {
        this.babcml7Dao = babcml7Dao;
    }

    public void setBbcmf07Dao(Bbcmf07Dao bbcmf07Dao) {
        this.bbcmf07Dao = bbcmf07Dao;
    }

    public void setCipbDao(CipbDao cipbDao) {
        this.cipbDao = cipbDao;
    }

    public void setCipgDao(CipgDao cipgDao) {
        this.cipgDao = cipgDao;
    }

    public void setBastudtermDao(BastudtermDao bastudtermDao) {
        this.bastudtermDao = bastudtermDao;
    }

    public void setBaunacpdtlDao(BaunacpdtlDao baunacpdtlDao) {
        this.baunacpdtlDao = baunacpdtlDao;
    }

    public void setBacountryDao(BacountryDao bacountryDao) {
        this.bacountryDao = bacountryDao;
    }

    public void setBaarclistDao(BaarclistDao baarclistDao) {
        this.baarclistDao = baarclistDao;
    }

    public void setBapayrptsumDao(BapayrptsumDao bapayrptsumDao) {
        this.bapayrptsumDao = bapayrptsumDao;
    }

    public void setBapayrptaccountDao(BapayrptaccountDao bapayrptaccountDao) {
        this.bapayrptaccountDao = bapayrptaccountDao;
    }

    public void setLocalPfpcckyDao(LocalPfpcckyDao localPfpcckyDao) {
        this.localPfpcckyDao = localPfpcckyDao;
    }

    public void setBapaavgmonDao(BapaavgmonDao bapaavgmonDao) {
        this.bapaavgmonDao = bapaavgmonDao;
    }

    public void setBarptlogDao(BarptlogDao barptlogDao) {
        this.barptlogDao = barptlogDao;
    }

    public void setBapasignDao(BapasignDao bapasignDao) {
        this.bapasignDao = bapasignDao;
    }

    public void setBbcmf09Dao(Bbcmf09Dao bbcmf09Dao) {
        this.bbcmf09Dao = bbcmf09Dao;
    }

    public void setBarecheckDao(BarecheckDao barecheckDao) {
        this.barecheckDao = barecheckDao;
    }

    public void setBapairrDao(BapairrDao bapairrDao) {
        this.bapairrDao = bapairrDao;
    }

    public void setBabasicamtDao(BabasicamtDao babasicamtDao) {
        this.babasicamtDao = babasicamtDao;
    }

    public void setBastudnotifyDao(BastudnotifyDao bastudnotifyDao) {
        this.bastudnotifyDao = bastudnotifyDao;
    }

    public void setBapandomkDao(BapandomkDao bapandomkDao) {
        this.bapandomkDao = bapandomkDao;
    }

    public void setBabatchjobDao(BabatchjobDao babatchjobDao) {
        this.babatchjobDao = babatchjobDao;
    }

    public void setNpcodeDao(NpcodeDao npcodeDao) {
        this.npcodeDao = npcodeDao;
    }

    public void setBafailurelistDao(BafailurelistDao bafailurelistDao) {
        this.bafailurelistDao = bafailurelistDao;
    }

    public void setBaunqualifiednoticeDao(BaunqualifiednoticeDao baunqualifiednoticeDao) {
        this.baunqualifiednoticeDao = baunqualifiednoticeDao;
    }

    public void setSdholidayDao(SdholidayDao sdholidayDao) {
        this.sdholidayDao = sdholidayDao;
    }

    public void setBahandicaptermDao(BahandicaptermDao bahandicaptermDao) {
        this.bahandicaptermDao = bahandicaptermDao;
    }

    public void setCvldtlDao(CvldtlDao cvldtlDao) {
        this.cvldtlDao = cvldtlDao;
    }

    public void setBaappattfileDao(BaappattfileDao baappattfileDao) {
        this.baappattfileDao = baappattfileDao;
    }
}
