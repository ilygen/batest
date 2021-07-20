package tw.gov.bli.ba.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.ba.dao.BaappbaseDao;
import tw.gov.bli.ba.dao.BabanDao;
import tw.gov.bli.ba.dao.Bbcmf07Dao;
import tw.gov.bli.ba.dao.MaadmrecDao;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Baban;
import tw.gov.bli.ba.domain.Bbcmf07;
import tw.gov.bli.ba.domain.Maadmrec;
import tw.gov.bli.ba.executive.cases.ExecutiveSupportCloseCase;
import tw.gov.bli.ba.executive.cases.ExecutiveSupportDataCase;
import tw.gov.bli.ba.executive.cases.ExecutiveSupportMaintCase;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.helper.EnvFacadeHelper;
import tw.gov.bli.common.helper.HttpHelper;
import tw.gov.bli.common.util.FrameworkLogUtil;

public class ExecutiveService {
    private static Log log = LogFactory.getLog(ExecutiveService.class);

    private BaappbaseDao baappbaseDao;
    private MaadmrecDao maadmrecDao;
    private BabanDao babanDao;
    private Bbcmf07Dao bbcmf07Dao;
    
    /**
     * 依傳入的 受理編號 自 給付主檔 (<code>Baappbase</code>) 取得 <code>ExecutiveSupportMaintCase資料</code>
     * <br>
     * for : 查詢作業 - 行政支援查詢
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return <code>Baappbase</code>; 若無資料回傳 <code>null</code>
     */
    public ExecutiveSupportMaintCase getExecutiveSupportMaintBy(String apNo,String issuYm) {
        log.debug("執行 ExecutiveService.getExecutiveSupportMaintBy() 開始 ...");

        Baappbase bappbase= baappbaseDao.selectExecutiveSupportMaintBy(apNo, issuYm);
        ExecutiveSupportMaintCase caseData = null;
        
        if(bappbase != null){
            caseData = new ExecutiveSupportMaintCase();
            bappbase.setEvtBrDate(DateUtility.changeDateType((String)ObjectUtils.defaultIfNull(bappbase.getEvtBrDate(),"")));
            bappbase.setIssuYm(DateUtility.changeWestYearMonthType((String)ObjectUtils.defaultIfNull(bappbase.getIssuYm(),"")));
            BeanUtility.copyProperties(caseData, bappbase);
        }
        
        log.debug("執行 ExecutiveService.getExecutiveSupportMaintBy() 完成 ...");

        return caseData;
    }
    
    /**
     * 依傳入的 受理編號 自 給付主檔 (<code>Baappbase</code>) 取得 <code>ExecutiveSupportCloseCase資料</code>
     * <br>
     * for : 查詢作業 - 行政支援查詢
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return <code>Baappbase</code>; 若無資料回傳 <code>null</code>
     */
    public ExecutiveSupportCloseCase getExecutiveSupportCloseBy(String apNo,String issuYm) {
        log.debug("執行 ExecutiveService.getExecutiveSupportCloseBy() 開始 ...");

        // Baappbase bappbase= baappbaseDao.selectExecutiveSupportMaintBy(apNo, issuYm);
        Baappbase bappbase= baappbaseDao.selectExecutiveSupportMaintForCloseBy(apNo, issuYm);
        ExecutiveSupportCloseCase caseData = null;
        
        if(bappbase != null){
            caseData = new ExecutiveSupportCloseCase();
            bappbase.setEvtBrDate(DateUtility.changeDateType((String)ObjectUtils.defaultIfNull(bappbase.getEvtBrDate(),"")));
            bappbase.setIssuYm(DateUtility.changeWestYearMonthType((String)ObjectUtils.defaultIfNull(bappbase.getIssuYm(),"")));
            BeanUtility.copyProperties(caseData, bappbase);
        }

        log.debug("執行 ExecutiveService.getExecutiveSupportCloseBy() 完成 ...");

        return caseData;
    }
    
    /**
     * 依傳入條件 新增行政支援記錄檔(<code>MAADMREC</code>)for 行政支援記錄維護
     * 
     * @param maadmrec 行政支援記錄檔
     * @return
     */
    public void insertMaadmrec(Maadmrec maadmrec, ExecutiveSupportMaintCase caseData, UserBean userData, String payMk){
        // 取得SEQNO
    	
        String seqNo = maadmrecDao.selectSeqNoBy(caseData.getApNo(), DateUtility.changeChineseYearMonthType(caseData.getIssuYm()), DateUtility.changeDateType(maadmrec.getProDate()), maadmrec.getLetterType());
        
        maadmrec.setSeqNo(seqNo);
        maadmrec.setApNo(caseData.getApNo());
        maadmrec.setIssuYm(DateUtility.changeChineseYearMonthType(caseData.getIssuYm()));
        maadmrec.setProDate(DateUtility.changeDateType(maadmrec.getProDate()));
        maadmrec.setClosDate(DateUtility.changeDateType(maadmrec.getClosDate()));
        maadmrec.setSlDate(DateUtility.changeDateType(maadmrec.getSlDate()));
        maadmrec.setFinishDate(DateUtility.changeDateType(maadmrec.getFinishDate()));
        maadmrec.setCrtUser(userData.getEmpNo());
        maadmrec.setCrtTime(DateUtility.getNowWestDateTime(true));
        //20090425 預設MAADMREC.IMK='1' ('1'-勞保年金， 空白-國民年金)，且MAADMREC.SOURCE='1' ('0'-從SDD批次轉入，'1'-行政支援自行新增)
        maadmrec.setImk("1");
        maadmrec.setSource("1");
        BigDecimal maAdmRecId = maadmrecDao.insertData(maadmrec);
        
        if("17".equals(maadmrec.getLetterType())){
            
            Bbcmf07 bbcmf07 = bbcmf07Dao.getExecutiveSupportHosData(maadmrec.getHpNo());
            
            Baban baban = new Baban();
            baban.setApNo(caseData.getApNo());
            baban.setProDte(maadmrec.getProDate());
            baban.setClinNo(maadmrec.getHpNo());
            baban.setIdNo(caseData.getEvtIdnNo());
            baban.setName(caseData.getEvtName());
            baban.setPayType(caseData.getPayKind());
            baban.setClinNm(bbcmf07!=null && bbcmf07.getHpName()!=null?bbcmf07.getHpName():"");
//            baban.setPayType(bbcmf07.getPayTyp());
            baban.setExpEns(bbcmf07!=null && bbcmf07.getPayAmt()!=null?bbcmf07.getPayAmt():new BigDecimal(0));
            baban.setInvRpn(maadmrec.getSlrpNo());
            baban.setInvDte(maadmrec.getProDate());
            baban.setInvPno(maadmrec.getPromoteUser());
            baban.setRepDte(maadmrec.getClosDate());
            baban.setPayMk(payMk);
            if("A".equals(payMk)){
                baban.setAdvDte(DateUtility.getNowWestDate());
                
            }else{
                baban.setAdvDte("");
            }
            
            
            babanDao.insertData(baban);
            // Insert MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log
                FrameworkLogUtil.doInsertLog(baban);
            }
           
        }
        
//        if("21".equals(maadmrec.getLetterType())){
//            List<BigDecimal> id = baappbaseDao.selectBaappbaseIdBy(caseData.getApNo());
//            for(int i=0;i<id.size();i++){
//                baappbaseDao.updateCaseTyp(id.get(i), "3", userData.getEmpNo(), DateUtility.getNowWestDateTime(true));
//            }
//        } 
        
        // Insert MMAPLOG       
        maadmrec.setMaAdmRecId(maAdmRecId);
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(maadmrec);
        }
    }
    
    /**
     * 依傳入的 受理編號 自 行政支援記錄檔 (<code>Maadmrec</code>) 取得 <code>ExecutiveSupportMaintCase資料</code>
     * <br>
     * for : 行政支援作業 - 行政支援記錄維護
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return <code>ExecutiveSupportMaintCase</code>; 若無資料回傳 <code>null</code>
     */
    public List<ExecutiveSupportDataCase> getExecutiveSupportMaintListBy(String apNo,String issuYm) {
        log.debug("執行 ExecutiveService.getExecutiveSupportMaintListBy() 開始 ...");

        List<Maadmrec> list= maadmrecDao.selectExecutiveSupportQueryListBy(apNo, null, issuYm);
        List<ExecutiveSupportDataCase> caseList = new ArrayList<ExecutiveSupportDataCase>();
        
        for(int i=0;i<list.size();i++){
            Maadmrec maadmrec = list.get(i);
            ExecutiveSupportDataCase caseData = new ExecutiveSupportDataCase();
            BeanUtility.copyProperties(caseData, maadmrec);
            
            caseData.setIssuYm(DateUtility.changeWestYearMonthType(maadmrec.getIssuYm()));
            caseData.setProDate(DateUtility.changeDateType(maadmrec.getProDate()));
            caseData.setClosDate(DateUtility.changeDateType(maadmrec.getClosDate()));
            caseData.setSlDate(DateUtility.changeDateType(maadmrec.getSlDate()));
            caseData.setFinishDate(DateUtility.changeDateType(maadmrec.getFinishDate()));
            
            if("17".equals(caseData.getLetterType())){
                String payMk = babanDao.getBabanPayMk(caseData.getApNo(), DateUtility.changeChineseYearMonthType(caseData.getIssuYm()), DateUtility.changeDateType(caseData.getProDate()), caseData.getHpNo());
                 caseData.setPayMk(payMk);
             }
            
            caseList.add(caseData);
        }

        log.debug("執行 ExecutiveService.getExecutiveSupportMaintListBy() 完成 ...");
        return caseList;
    }
    
    /**
     * 依傳入的 受理編號 自 行政支援記錄檔 (<code>Maadmrec</code>) 取得 <code>ExecutiveSupportCloseCase資料</code>
     * <br>
     * for : 行政支援作業 - 行政支援銷案
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return <code>ExecutiveSupportCloseCase</code>; 若無資料回傳 <code>null</code>
     */
    public List<ExecutiveSupportDataCase> getExecutiveSupportCloseListBy(String apNo,String issuYm) {
        log.debug("執行 ExecutiveService.getExecutiveSupportCloseListBy() 開始 ...");

        List<Maadmrec> list= maadmrecDao.selectExecutiveSupportCloseListBy(apNo, issuYm,null,null,null);
        List<ExecutiveSupportDataCase> caseList = new ArrayList<ExecutiveSupportDataCase>();
        
        for(int i=0;i<list.size();i++){
            Maadmrec maadmrec = list.get(i);
            ExecutiveSupportDataCase caseData = new ExecutiveSupportDataCase();
            BeanUtility.copyProperties(caseData, maadmrec);
            
            caseData.setIssuYm(DateUtility.changeWestYearMonthType(maadmrec.getIssuYm()));
            caseData.setProDate(DateUtility.changeDateType(maadmrec.getProDate()));
            caseData.setClosDate(DateUtility.changeDateType(maadmrec.getClosDate()));
            caseData.setSlDate(DateUtility.changeDateType(maadmrec.getSlDate()));
            caseData.setFinishDate(DateUtility.changeDateType(maadmrec.getFinishDate()));
            
            if("17".equals(caseData.getLetterType())){
                String payMk = babanDao.getBabanPayMk(caseData.getApNo(), DateUtility.changeChineseYearMonthType(caseData.getIssuYm()), DateUtility.changeDateType(caseData.getProDate()), caseData.getHpNo());
                 caseData.setPayMk(payMk);
             }
            
            caseList.add(caseData);
        }

        log.debug("執行 ExecutiveService.getExecutiveSupportCloseListBy() 完成 ...");
        return caseList;
    }
    
    /**
     * 依傳入條件 更新行政支援記錄檔(<code>MAADMREC</code>)for 行政支援記錄維護
     * 
     * @param ExecutiveSupportMaintModifyForm 
     * @param ExecutiveSupportMaintCase 
     * @param UserBean 
     * @return
     */
    public void updateMaadmrec(Maadmrec maadmrec, ExecutiveSupportDataCase caseData, UserBean userData, ExecutiveSupportMaintCase exeCaseData, Baban baban){
        log.debug("執行 ExecutiveService.updateMaadmrec() 開始 ...");
        
        Maadmrec beforMaadmrec = new Maadmrec();
        BeanUtility.copyProperties(beforMaadmrec, caseData);
        
        // 取得SEQNO
        String seqNo = maadmrecDao.selectSeqNoBy(caseData.getApNo(), DateUtility.changeChineseYearMonthType(caseData.getIssuYm()), DateUtility.changeDateType(maadmrec.getProDate()), maadmrec.getLetterType());
        
        maadmrec.setMaAdmRecId(caseData.getMaAdmRecId());
        maadmrec.setSeqNo(seqNo);
        maadmrec.setProDate(DateUtility.changeDateType(maadmrec.getProDate()));
        maadmrec.setClosDate(DateUtility.changeDateType(maadmrec.getClosDate()));
        maadmrec.setSlDate(DateUtility.changeDateType(maadmrec.getSlDate()));
        maadmrec.setFinishDate(DateUtility.changeDateType(maadmrec.getFinishDate()));
        maadmrec.setUpdTime(DateUtility.getNowWestDateTime(true));
        maadmrec.setUpdUser(userData.getEmpNo());
        
        /*HashMap<String,String> map = new HashMap<String,String>();
        map.put("letterType", from.getLetterType());
        map.put("proDate", DateUtility.changeDateType(from.getProDate()));
        map.put("ndomk1", from.getNdomk1());
        map.put("ndomk2", from.getNdomk2());
        map.put("note", from.getNote());
        map.put("closDate", DateUtility.changeDateType(from.getClosDate()));
        map.put("delMk", from.getDelMk());
        map.put("updUser", userData.getUserName());
        map.put("updTime", DateUtility.getNowWestDateTime(true));
        map.put("apNo", Case.getApNo());
        map.put("oldProDate", DateUtility.changeDateType(Case.getDetail().getProdate()));
        map.put("issuYm", DateUtility.changeChineseYearMonthType(Case.getDetail().getIssuym()));
        map.put("oldSeqNo", Case.getDetail().getSeqno());*/
        
        
        maadmrecDao.updateData(maadmrec);
        
        if("17".equals(maadmrec.getLetterType()) && !"D".equals(maadmrec.getDelMk())){
            Bbcmf07 bbcmf07 = bbcmf07Dao.getExecutiveSupportHosData(maadmrec.getHpNo());
            Baban beforeBaban = new Baban();
            BeanUtility.copyProperties(beforeBaban, baban);
            
            baban.setApNo(caseData.getApNo());
            baban.setProDte(maadmrec.getProDate());
            baban.setClinNo(maadmrec.getHpNo());
            baban.setIdNo(maadmrec.getIdNo());
            baban.setName(maadmrec.getName());
            baban.setPayType(exeCaseData.getPayKind());
            baban.setClinNm(bbcmf07.getHpName());
//            baban.setPayType(bbcmf07.getPayTyp());
            baban.setExpEns(bbcmf07.getPayAmt());
            baban.setInvRpn(maadmrec.getSlrpNo());
            baban.setInvDte(maadmrec.getProDate());
            baban.setInvPno(maadmrec.getPromoteUser());
            baban.setRepDte(maadmrec.getClosDate());
            
            babanDao.updateData(baban);
            
         // Insert MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log
                FrameworkLogUtil.doUpdateLog(beforeBaban, baban);
            }
        }
        
        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doUpdateLog(beforMaadmrec, maadmrec);
        }
        
        log.debug("執行 ExecutiveService.updateMaadmrec() 完成 ..."); 
    }
    
    /**
     * 依傳入條件 更新行政支援記錄檔 (<code>MAADMREC</code>)closedate欄位 for 行政支援記錄銷案
     * 
     * @param checkbox 
     * @param ExecutiveSupportMaintCase 
     * @param UserBean 
     * @return
     */
    public void updateMaadmrecCloseDate(BigDecimal[] checkbox ,List<ExecutiveSupportDataCase> caseList ,UserBean userData){
       log.debug("執行 ExecutiveService.updateMaadmrecCloseDate() 開始 ...");

       for(int i=0;i<checkbox.length;i++){
           Maadmrec beforeMaadmrec= new Maadmrec();
           Maadmrec maadmrec= new Maadmrec();
           
           BeanUtility.copyProperties(beforeMaadmrec, caseList.get(checkbox[i].intValue()-1));
           BeanUtility.copyProperties(maadmrec, caseList.get(checkbox[i].intValue()-1));
           
           maadmrec.setClosDate(DateUtility.getNowWestDateTime(false).substring(0, 8));
           maadmrec.setUpdUser(userData.getEmpNo());
           maadmrec.setUpdTime(DateUtility.getNowWestDateTime(true));
           maadmrecDao.updateDataClosedate(maadmrec);
           
        // Insert MMAPLOG
           if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
               // 紀錄 Log
               FrameworkLogUtil.doUpdateLog(beforeMaadmrec, maadmrec);
           }
           
       }
       log.debug("執行 ExecutiveService.updateMaadmrecCloseDate() 完成 ..."); 
    }
    
    /**
     * 依傳入條件 (<code>BAAPPBASE</code>) 取得 處理函別 下拉式選單條件的資料
     * 
     * @return
     */
    
    public Integer getUpdateLetterTypeOptionList(String apNo, String issuYm) {
        return baappbaseDao.getProCstatUpdateLetterType(apNo, issuYm);
    }
    
    /**
     * 依傳入條件 (<code>BAAPPBASE</code>) 取得 按鈕狀態的資料
     * 
     * @return
     */
    
    public Integer getButtonStatus(String apNo) {
        return baappbaseDao.getButtonStatus(apNo);
    }
    
    /**
     * 依傳入條件 刪除行政支援記錄檔(<code>MAADMREC</code>)for 行政支援記錄維護
     * 
     * @param ExecutiveSupportMaintCase 
     * @return
     */
    public void deleteMaadmrec(ExecutiveSupportDataCase caseData){
        Maadmrec beforeMaadmrec = new Maadmrec();
        BeanUtility.copyProperties(beforeMaadmrec, caseData);
        maadmrecDao.deleteData(caseData);    

        // Insert MMAPLOG
         if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
             // 紀錄 Log
             FrameworkLogUtil.doDeleteLog(beforeMaadmrec);
         }
        
    }

    public void setBaappbaseDao(BaappbaseDao baappbaseDao) {
        this.baappbaseDao = baappbaseDao;
    }

    public void setMaadmrecDao(MaadmrecDao maadmrecDao) {
        this.maadmrecDao = maadmrecDao;
    }
    
    public void setBbcmf07Dao(Bbcmf07Dao bbcmf07Dao){
        this.bbcmf07Dao = bbcmf07Dao;
    }
    
    public void setBabanDao(BabanDao babanDao) {
        this.babanDao = babanDao;
    }
}
