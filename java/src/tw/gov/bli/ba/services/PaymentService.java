package tw.gov.bli.ba.services;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tw.gov.bli.ba.dao.BaappbaseDao;
import tw.gov.bli.ba.dao.BapaymentDao;
import tw.gov.bli.ba.dao.BapaymentassigndtlDao;
import tw.gov.bli.ba.dao.BapaymentdtlDao;
import tw.gov.bli.ba.dao.BapaymentremoteDao;
import tw.gov.bli.ba.dao.BapaymentremotedtlDao;
import tw.gov.bli.ba.dao.BapaymentstagedtlDao;
import tw.gov.bli.ba.dao.BaunacpdtlDao;
import tw.gov.bli.ba.dao.Bccmf45Dao;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Bacpirec;
import tw.gov.bli.ba.domain.Bapayment;
import tw.gov.bli.ba.domain.Bapaymentassigndtl;
import tw.gov.bli.ba.domain.Bapaymentdtl;
import tw.gov.bli.ba.domain.Bapaymentremote;
import tw.gov.bli.ba.domain.Bapaymentremotedtl;
import tw.gov.bli.ba.domain.Bapaymentstagedtl;
import tw.gov.bli.ba.domain.Baunacpdtl;
import tw.gov.bli.ba.domain.Bccmf45;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.maint.cases.AdviceMaintCase;
import tw.gov.bli.ba.maint.cases.CpiRecMaintCase;
import tw.gov.bli.ba.payment.cases.PaymentProcessCase;
import tw.gov.bli.ba.payment.cases.PaymentProcessQueryCase;
import tw.gov.bli.ba.payment.cases.PaymentProgressQueryCase;
import tw.gov.bli.ba.payment.cases.PaymentReprintCase;
import tw.gov.bli.ba.payment.cases.PaymentReprintdtlCase;
import tw.gov.bli.ba.payment.cases.PaymentStageDtlCase;
import tw.gov.bli.ba.payment.cases.PaymentUpdateCase;
import tw.gov.bli.ba.payment.cases.PaymentWebserviceDtlCase;
import tw.gov.bli.ba.payment.cases.PaymentDetailCase;
import tw.gov.bli.ba.payment.cases.PaymentWebserviceProcessCase;
import tw.gov.bli.ba.payment.forms.PaymentProcessQueryForm;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.StringUtility;

public class PaymentService {
	private static Log log = LogFactory.getLog(PaymentService.class);
	BapaymentDao bapaymentDao;
	BaappbaseDao baappbaseDao;
    BaunacpdtlDao baunacpdtlDao;
    BapaymentdtlDao bapaymentdtlDao;
    BapaymentremoteDao bapaymentremoteDao;
    BapaymentremotedtlDao bapaymentremotedtlDao;
    BapaymentstagedtlDao bapaymentstagedtlDao;
    BapaymentassigndtlDao bapaymentassigndtlDao;
    Bccmf45Dao bccmf45Dao;
	/**
     * 查詢IDN繳款單資料
     * 
     * @param 
     * @return <code>List<PaymentProcessQueryCase></code>
     */
    public List<PaymentProcessQueryCase> getPaymentQueryCaseBy(String idn) {
    	 List<Bapayment> paymentData = bapaymentDao.selectPaymentQueryData(idn);//查詢IDN繳款單資料
    	 List<PaymentProcessQueryCase> returnList = new ArrayList<PaymentProcessQueryCase>();;

         if (paymentData.size()>0 || paymentData != null) {
             for(int i=0; i<paymentData.size(); i++){
            	 Bapayment caseData = paymentData.get(i);
            	 PaymentProcessQueryCase returnCase = new PaymentProcessQueryCase();
            	 returnCase.setApno(caseData.getApno());//受理編號
            	 returnCase.setCaseNo(caseData.getCaseNo());//移送案號
            	 returnCase.setEvtIdnno(caseData.getIdn());//事故者IDN(後來改成受益者)
            	 returnCase.setEvtName(caseData.getPaymentName());//事故者姓名(後來改成受益者)
            	 returnCase.setPaymentNo(caseData.getPaymentNo());//繳款單號
            	 returnCase.setPayTotAmt(caseData.getPayTotAmt());//應繳總額
            	 returnCase.setPrtDate(DateUtility.changeDateType(caseData.getPrtDate()));//列印日期
            	 returnList.add(returnCase);
             }
         }
         return returnList;
    }
    /**
     * 查詢受益人IDN對應姓名
     * 
     * @param idn
     * @return benName
     */
    public String getPaymentBenName(String idn) {
		return baappbaseDao.selectPaymentBenName(idn);
    	
    }
    public List<PaymentStageDtlCase> getTryStageDataForPage(List<PaymentStageDtlCase> cpData){
    	List<PaymentStageDtlCase> listData = new ArrayList<PaymentStageDtlCase>();
    	boolean flag = true;
    	int x=0;
    	for(int i=0; i< cpData.size(); i++){    	
 		    PaymentStageDtlCase cd = cpData.get(i);
 		    PaymentStageDtlCase ld = new PaymentStageDtlCase();
 		    BeanUtility.copyProperties(ld, cd);
 		    if(i==0){
 		    	if(cpData.size()>1){
 		    		if((cd.getNowStage()==cpData.get(i+1).getNowStage()) && 
	 		    			(cd.getiRate().compareTo(cpData.get(i+1).getiRate())!=0)){//期數一樣且利率不一樣
 		    			if(cd.getiRate().compareTo(BigDecimal.ZERO) > 0 && cpData.get(i+1).getiRate().compareTo(BigDecimal.ZERO)>0  ){
 		    				ld.setiRate(BigDecimal.ZERO);
 		 		    		ld.setAdjInterest(cd.getAdjInterest().add(cpData.get(i+1).getAdjInterest()));
 		 		    		ld.setTryInterest(cd.getTryInterest().add(cpData.get(i+1).getTryInterest()));
 		 		    		ld.setDateBetween(cd.getDateBetween()+cpData.get(i+1).getDateBetween());
 		 		    		ld.setInterestEndDate(cpData.get(i+1).getInterestEndDate());
 		 		    		listData.add(ld);
 		 		    		flag = false;
 		    			}else{
 		    				if(cd.getiRate().compareTo(BigDecimal.ZERO)>0){
 		    					ld.setiRate(BigDecimal.ZERO);
 	 		 		    		ld.setAdjInterest(cd.getAdjInterest());
 	 		 		    		ld.setTryInterest(cd.getTryInterest());
 	 		 		    		ld.setDateBetween(cpData.get(i+1).getDateBetween()+cd.getDateBetween());
 	 		 		    		ld.setInterestEndDate(cpData.get(i+1).getInterestEndDate());
 	 		 		    		listData.add(ld);
 	 		 		    		flag = false;
 		    				}else if(cpData.get(i+1).getiRate().compareTo(BigDecimal.ZERO)>0){
 		    					ld.setiRate(BigDecimal.ZERO);
 	 		 		    		ld.setAdjInterest(cpData.get(i+1).getAdjInterest());
 	 		 		    		ld.setTryInterest(cpData.get(i+1).getTryInterest());
 	 		 		    		ld.setDateBetween(cpData.get(i+1).getDateBetween()+cd.getDateBetween());
 	 		 		    		ld.setInterestEndDate(cpData.get(i+1).getInterestEndDate());
 	 		 		    		listData.add(ld);
 	 		 		    		flag = false;
 		    				}else{
 		    					ld.setiRate(BigDecimal.ZERO);
 	 		 		    		ld.setAdjInterest(BigDecimal.ZERO);
 	 		 		    		ld.setTryInterest(BigDecimal.ZERO);
 	 		 		    		ld.setDateBetween(cpData.get(i+1).getDateBetween()+cd.getDateBetween());
 	 		 		    		ld.setInterestEndDate(cpData.get(i+1).getInterestEndDate());
 	 		 		    		listData.add(ld);
 	 		 		    		flag = false;
 		    				}
 		    				
 		    			}
	 		    		
		 		    }else{
		 		    	if(cd.getNowStage()==cpData.get(i+1).getNowStage()){
		 		    			ld.setiRate(cd.getiRate());
	 		 		    		ld.setAdjInterest(cd.getAdjInterest().add(cpData.get(i+1).getAdjInterest()));
	 		 		    		ld.setTryInterest(cd.getAdjInterest().add(cpData.get(i+1).getTryInterest()));
	 		 		    		ld.setDateBetween(cpData.get(i+1).getDateBetween()+cd.getDateBetween());
	 		 		    		ld.setInterestEndDate(cpData.get(i+1).getInterestEndDate());
	 		 		    		listData.add(ld);
	 		 		    		flag = false;
		 		    	}else{
		 		    		listData.add(ld);
		 		    	}
		 		    	
		 		    }
 		    	}else{
 		    		listData.add(ld);
 		    	}
 		    	
 		    }else{
 		    	if((i+1) <= cpData.size()){
 		    		if(flag==true && (i+1)==cpData.size()){
 	 	 		    	listData.add(ld);
 	 	 		    }else if(flag==false && (i+1)==cpData.size()){
 	 	 		    	listData.get(x).setiRate(BigDecimal.ZERO);
 	 	 		    }else if(flag==false && cd.getNowStage()==cpData.get(i+1).getNowStage()){
 		    			if(listData.get(x).getiRate().compareTo(cd.getiRate())==0){
 		    				//listData.get(0).setiRate(listData.get(0).getiRate());
 		    				listData.get(x).setAdjInterest(listData.get(x).getAdjInterest().add(cpData.get(i+1).getAdjInterest()));
 		    				listData.get(x).setTryInterest(listData.get(x).getTryInterest().add(cpData.get(i+1).getTryInterest()));
 		    				listData.get(x).setDateBetween(listData.get(x).getDateBetween()+cpData.get(i+1).getDateBetween());
 		    				listData.get(x).setInterestEndDate(cpData.get(i+1).getInterestEndDate());
 		 		    		//listData.add(ld);
 		 		    		flag = false;
 		    			}else{
 		    				listData.get(x).setiRate(BigDecimal.ZERO);
 		    				listData.get(x).setAdjInterest(listData.get(x).getAdjInterest().add(cpData.get(i+1).getAdjInterest()));
 		    				listData.get(x).setTryInterest(listData.get(x).getTryInterest().add(cpData.get(i+1).getTryInterest()));
 		    				listData.get(x).setDateBetween(listData.get(x).getDateBetween()+cpData.get(i+1).getDateBetween());
 		    				listData.get(x).setInterestEndDate(cpData.get(i+1).getInterestEndDate());
 		 		    		flag = false;
 		    			}
 		    			
 	 	 		    }else if(flag==false && cd.getNowStage()!=cpData.get(i+1).getNowStage()){
 	 	 		    	flag = true;
 	 	 		    }else if((cd.getNowStage()==cpData.get(i+1).getNowStage()) && 
 	 		    			(cd.getiRate().compareTo(cpData.get(i+1).getiRate())!=0)){//期數一樣且利率不一樣
 	 	 		    	if(cd.getiRate().compareTo(BigDecimal.ZERO) > 0 && cpData.get(i+1).getiRate().compareTo(BigDecimal.ZERO) > 0){
 		    				if(cd.getiRate().compareTo(cpData.get(i+1).getiRate())==0){
 		    					ld.setiRate(cd.getiRate());
 		    				}else{
 		    					ld.setiRate(BigDecimal.ZERO);
 		    				}
 	 	 		    		ld.setAdjInterest(cd.getAdjInterest().add(cpData.get(i+1).getAdjInterest()));
 		 		    		ld.setTryInterest(cd.getTryInterest().add(cpData.get(i+1).getTryInterest()));
 		 		    		ld.setDateBetween(cd.getDateBetween()+cpData.get(i+1).getDateBetween());
 		 		    		ld.setInterestEndDate(cpData.get(i+1).getInterestEndDate());
 		 		    		listData.add(ld);
 		 		    		x = x + 1;
 		 		    		flag = false;
 		    			}else{
 		    				if(cd.getiRate().compareTo(BigDecimal.ZERO)>0){
 		    					ld.setiRate(BigDecimal.ZERO);
 	 		 		    		ld.setAdjInterest(cd.getAdjInterest());
 	 		 		    		ld.setTryInterest(cd.getTryInterest());
 	 		 		    		ld.setDateBetween(cpData.get(i+1).getDateBetween()+cd.getDateBetween());
 	 		 		    		ld.setInterestEndDate(cpData.get(i+1).getInterestEndDate());
 	 		 		    		listData.add(ld);
 	 		 		    		x = x + 1;
 	 		 		    		flag = false;
 		    				}else if(cpData.get(i+1).getiRate().compareTo(BigDecimal.ZERO)>0){
 		    					ld.setiRate(BigDecimal.ZERO);
 	 		 		    		ld.setAdjInterest(cpData.get(i+1).getAdjInterest());
 	 		 		    		ld.setTryInterest(cpData.get(i+1).getTryInterest());
 	 		 		    		ld.setDateBetween(cpData.get(i+1).getDateBetween()+cd.getDateBetween());
 	 		 		    		ld.setInterestEndDate(cpData.get(i+1).getInterestEndDate());
 	 		 		    		listData.add(ld);
 	 		 		    		x = x + 1;
 	 		 		    		flag = false;
 		    				}else{
 		    					ld.setiRate(BigDecimal.ZERO);
 	 		 		    		ld.setAdjInterest(BigDecimal.ZERO);
 	 		 		    		ld.setTryInterest(BigDecimal.ZERO);
 	 		 		    		ld.setDateBetween(cpData.get(i+1).getDateBetween()+cd.getDateBetween());
 	 		 		    		ld.setInterestEndDate(cpData.get(i+1).getInterestEndDate());
 	 		 		    		listData.add(ld);
 	 		 		    		x = x + 1;
 	 		 		    		flag = false;
 		    				}
 		    				
 		    			}
 	 		    	}else{
 	 		    		if(cd.getNowStage()==cpData.get(i+1).getNowStage()){
	 		    			ld.setiRate(cd.getiRate());
 		 		    		ld.setAdjInterest(cd.getAdjInterest().add(cpData.get(i+1).getAdjInterest()));
 		 		    		ld.setTryInterest(cd.getAdjInterest().add(cpData.get(i+1).getTryInterest()));
 		 		    		ld.setDateBetween(cpData.get(i+1).getDateBetween()+cd.getDateBetween());
 		 		    		ld.setInterestEndDate(cpData.get(i+1).getInterestEndDate());
 		 		    		listData.add(ld);
 		 		    		x = x + 1;
 		 		    		flag = false;
		 		    	}else{
		 		    		x = x + 1;
	 	 		    		listData.add(ld);
		 		    	}
 	 		    		
 	 		    	}
 		    	}
 		    }
 	   }
 	   return listData;
    }
    /**
     * 依傳入條件取得受理編號
     * 
     * @param idn 身分證號
     * @return
     */
    public List<String> getApnoDataFromMultiSource(String idn){
    	return baappbaseDao.selectApnoDataFromMultiSource(idn);
    }
    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料是否存在
     * 
     * @param idn 身分證號
     * @return
     */
    public int getEvtPersonDataCount(String idn){
    	int count = baappbaseDao.selectEvtPersonDataCount(idn);
    	return count;
    }
    
    public int getPaymentCount(String paymentNo){
    	int count = bapaymentDao.selectPaymentCount(paymentNo);
    	return count;
    }
    /**
     * 依傳入條件取得 受理編號的事故者姓名
     * 
     * @param idn 身分證號
     * @return
     */
    public String getEvtNameFromBaappbase(String idn){//受理編號的事故者姓名
    	return baappbaseDao.selectEvtName(idn);
    }
    
    /**
     * @param apno 受理編號
     * @param idnNo 身分證號
     * @return
     */
    public String getEvtNameFromBaappbaseByIdnAndApno(String idn, String apno){//受理編號的事故者姓名
    	return baappbaseDao.selectEvtNameByIdnAndApno(idn, apno);
    }
    /**
     * 依傳入條件取得 主檔受益人資料
     * 
     * @param apno 受理編號
     * @param idnNo 身分證號
     * @return
     */
    public PaymentProcessCase getPaymentBaseData(String apno, String idnNo) {
    	Baappbase baseData = baappbaseDao.selectPaymentBaseData(apno, idnNo);
    	if(baseData != null) {
    		PaymentProcessCase returnCase = new PaymentProcessCase();
    		returnCase.setBenName(baseData.getBenName());
    		returnCase.setSeqNo(baseData.getSeqNo());
        	returnCase.setApno(baseData.getApNo());
        	returnCase.setPayKind(baseData.getPayKind());
        	returnCase.setPaymentSex(baseData.getBenSex());
        	returnCase.setAddr(baseData.getCommAddr());
        	returnCase.setZipCode(baseData.getCommZip());
        	return returnCase;
    	}else {
    		return null;
    	}
    	
    }
    /**
     * 依傳入條件取得 應收未收資料
     * 
     * @param apno 受理編號
     * @return
     */
    public List<PaymentProcessCase> getUnacpdtlDataList(List<String> apnoList){
    	List<PaymentProcessCase> returnList = new ArrayList<PaymentProcessCase>();
    	for(int i=0; i< apnoList.size(); i++){
    		String apno = apnoList.get(i);
    		List<Baunacpdtl> queryList = baunacpdtlDao.selectUnacpdtlDataList(apno);
    		if(queryList.size()>0){
    			for(int j=0; j<queryList.size(); j++){
    				Baunacpdtl queryCase = queryList.get(j);
    				PaymentProcessCase returnCase = new PaymentProcessCase();
    				//1:主辦案 0:其他
    				if(j==0){
    					returnCase.setMapnoMk("1");
    				}else{
    					returnCase.setMapnoMk("0");
    				}
    				returnCase.setApno(queryCase.getApNo());//受理編號
    				returnCase.setBenName(queryCase.getBenName());//受益者姓名
    				returnCase.setIssuYm(DateUtility.changeWestYearMonthType(queryCase.getIssuYm()));//核定年月
    				returnCase.setPayAmt(queryCase.getPayAmt());//應繳本金
    				returnCase.setPayYm(DateUtility.changeWestYearMonthType(queryCase.getPayYm()));//給付年月
    				returnCase.setRecAmt(queryCase.getRecAmt());//應收(應扣減)金額
    				returnCase.setRecRem(queryCase.getRecRem());// 未收餘額
    				returnCase.setSeqNo(queryCase.getSeqNo());//受款人序
    				returnCase.setPayKind(queryCase.getPayKind());//給付方式
    				returnCase.setWriteoffSeqNo(String.valueOf(j+1));//銷帳序
    				returnCase.setPaymentSex(queryCase.getBenSex());//受益人性別
    				returnList.add(returnCase);
    			}
    		}
    	}
    	return returnList;
    }
    /**
     * 依傳入條件取得 地址資料
     * 
     * @param apno 受理編號
     * @return
     */
    public PaymentProcessCase getAddrData(String apno,String seqNo){
    	Baappbase caseData = baappbaseDao.selectAddrData(apno,seqNo);
    	PaymentProcessCase returnCase = new PaymentProcessCase();
    	returnCase.setAddr(caseData.getCommAddr());//地址
    	returnCase.setZipCode(caseData.getCommZip());//郵遞區號
    	return returnCase;
    }
    
    public void updateBasicData( PaymentProcessQueryForm queryForm,  List<PaymentProcessCase> qryDataUnacpdtl, UserBean userData ){
    	Bapayment paymentData = new Bapayment();
    	paymentData.setPaymentNo(queryForm.getPaymentNo());
    	paymentData.setChkObj(queryForm.getChkObj());
    	paymentData.setCaseNo(queryForm.getClassNo());
    	paymentData.setUpdTime(DateUtility.getNowWestDateTime(true));
    	paymentData.setUpdUser(userData.getEmpNo());
    	if(StringUtils.isBlank(queryForm.getClassNo())){
    		paymentData.setCaseType("1");
    	}else{
    		paymentData.setCaseType("2");
    	}
    	paymentData.setChkObj(queryForm.getChkObj());
    	paymentData.setPaymentDateLine(DateUtility.changeDateType(queryForm.getPaymentDateLine()));
    	paymentData.setPaymentZip(queryForm.getZipCode());
    	paymentData.setPaymentAddr(queryForm.getAddr());
    	paymentData.setTotAmtStage(queryForm.getTotAmtStage());
    	paymentData.setInterestStage(queryForm.getInterestStage());
    	paymentData.setStageAmt(queryForm.getStageAmt());
    	paymentData.setTotTrgAmt(queryForm.getTotTrgAmt());
    	paymentData.setInterestTryStage(queryForm.getInterestTryStage());
    	paymentData.setMonthlyPayAmt(queryForm.getMonthlyPayAmt());
    	paymentData.setPrtDate(DateUtility.changeDateType(queryForm.getPrtDate()));
    	paymentData.setIdn(queryForm.getIdn());
    	paymentData.setUseMk("Y");
    	paymentData.setPayTotAmt(queryForm.getPayTotAmt());
    	bapaymentDao.updatePaymentData(paymentData);
    	bapaymentdtlDao.deletePaymentData(paymentData.getPaymentNo());
    	if(qryDataUnacpdtl.size()>0 && qryDataUnacpdtl != null){
    		// 說明
            for (int i = 0; i < qryDataUnacpdtl.size(); i++) {
            	PaymentProcessCase qduData =qryDataUnacpdtl.get(i);
            	Bapaymentdtl pd = new Bapaymentdtl();
                pd.setApno(qduData.getApno());
                pd.setIssuYm(DateUtility.changeChineseYearMonthType(qduData.getIssuYm()));
                pd.setmApnoMk(qduData.getMapnoMk());
                pd.setPayAmt(qduData.getPayAmt());
                pd.setPaymentName(qduData.getBenName());
                pd.setPaymentNo(queryForm.getPaymentNo());
                pd.setPayYm(DateUtility.changeChineseYearMonthType(qduData.getPayYm()));
                
                pd.setRecAmt(qduData.getRecAmt());
                pd.setRecRem(qduData.getRecRem());
                pd.setSeqNo(qduData.getSeqNo());
                pd.setWriteoffSeqno(qduData.getWriteoffSeqNo());
                bapaymentdtlDao.insertPaymentData(pd);
            }
    	}
    }
    /**
     * 繳款單開單序號
     * 
     * @param nowDate 今天日期
     * @return
     */
    public int getNowDatePaymentCount(String nowDate){
    	return bapaymentDao.selectCountForPaymentToday(nowDate);
    }
    /**
     * 試算利息資料
     * 
     * @param PaymentProcessQueryForm queryForm
     * @return
     * @throws UnsupportedEncodingException 
     */
    public List<PaymentStageDtlCase> getTryStageData(PaymentProcessQueryForm queryForm, Map<String,Double> rateMap, List<PaymentProcessCase> resultList, String[] monthlyPayAmt, String[] interestEndDate) throws UnsupportedEncodingException{
    	//利息試算期數
    	int interestTryStage = queryForm.getInterestTryStage().intValue();
    	//利息標的金額
    	BigDecimal totTrgAmt = queryForm.getTotTrgAmt();
    	BigDecimal accAmt = BigDecimal.ZERO;
    	List<PaymentStageDtlCase> listData = new ArrayList<PaymentStageDtlCase>();
    	
 	    if(queryForm.getInterestTryStage().intValue() > 0){
 	   		for(int i=0; i<interestTryStage; i++){
 	   			int tryInterest = 0;
 	   			double iRate;
 	   			PaymentStageDtlCase caseData = new PaymentStageDtlCase();
 	   			caseData.setNowStage(i+1);//期別
 	   			caseData.setAccAmt(totTrgAmt);//剩餘本金
 	   			if(monthlyPayAmt == null){
	 	   			if((interestTryStage>1 && interestTryStage>(i+1))||interestTryStage==1){//非最末期
	 	   				totTrgAmt = totTrgAmt.subtract(queryForm.getMonthlyPayAmt());
		   				caseData.setRePayAmt(queryForm.getMonthlyPayAmt());//還款金額
		   			}else{//最末期以前一期的還款金額作為剩餘金額
		   				totTrgAmt = totTrgAmt.subtract(caseData.getAccAmt());
		   				caseData.setRePayAmt(caseData.getAccAmt());//還款金額
		   			}
 	   			}else{
	 	   			if((interestTryStage>1 && interestTryStage>(i+1))||interestTryStage==1){//非最末期
	 	   				totTrgAmt = totTrgAmt.subtract(BigDecimal.valueOf(Long.parseLong(monthlyPayAmt[i])));
		   				caseData.setRePayAmt(BigDecimal.valueOf(Long.parseLong(monthlyPayAmt[i])));//還款金額
		   			}else{//最末期以前一期的還款金額作為剩餘金額
		   				totTrgAmt = totTrgAmt.subtract(accAmt);
		   				caseData.setRePayAmt(caseData.getAccAmt());//還款金額
		   			}
 	   			}
 	   			caseData.setAccAmtEnd(caseData.getAccAmt().subtract(caseData.getRePayAmt()));
	   			
	 	   		accAmt = caseData.getAccAmt();
 	   			caseData.setPaymentNoDetail(queryForm.getPaymentNo().substring(0,13)+StringUtility.chtLeftPad(String.valueOf(i), 2, "0"));//繳款單號
 	   			if(StringUtils.isNotBlank(queryForm.getInterestBegDates()) && (i==0)){
 	   				caseData.setInterestBegDate(queryForm.getInterestBegDates());
 	   			}else{
 	   				//利息起算日
 		 	   		if(StringUtils.isNotBlank(queryForm.getPaymentDateLine()) && (i==0)){
 		 	   			caseData.setInterestBegDate(DateUtility.calDay(queryForm.getPaymentDateLine(), 1));
 		 	   		}else if(StringUtils.isBlank(queryForm.getPaymentDateLine()) && (i==0)){
 		 	   			caseData.setInterestBegDate(DateUtility.getNowChineseDate().substring(0,3)+"0101");	 
 	 	   			}else if(listData.size()!=i){//有跨年度
 	 	   				caseData.setInterestBegDate(DateUtility.calDay(listData.get(listData.size()-1).getInterestEndDate(),1));
	 	   			}else{
 	 	   				caseData.setInterestBegDate(DateUtility.calDay(listData.get(i-1).getInterestEndDate(),1));
 	 	   			}
 	   			}
 	   			
 	   			if(interestTryStage >= 1 && interestEndDate!=null){
 	   				//利息結算日
 		 	   		caseData.setInterestEndDate(interestEndDate[i]);
 		 	   	}else{
 		 	   		caseData.setInterestEndDate(DateUtility.calDay(DateUtility.calMonth(caseData.getInterestBegDate(), 1),-1));
 		 	   	}
 	   			if(interestTryStage == 1 && interestEndDate==null){
 	   				caseData.setInterestEndDate("");
 	   			}
	 	   		caseData.setDateBetween(DateUtility.daysBetween(caseData.getInterestBegDate(),caseData.getInterestEndDate())+1);
	 	   		if(StringUtils.isNotBlank(caseData.getInterestBegDate()) && StringUtils.isNotBlank(caseData.getInterestEndDate())){
	 	   			//利息起算日-利息結算日 跨年度
		 	   		if(!caseData.getInterestBegDate().substring(0,3).equals(caseData.getInterestEndDate().substring(0,3))){
		 	   			if(Integer.parseInt(caseData.getInterestEndDate().substring(0,3))-Integer.parseInt(caseData.getInterestBegDate().substring(0,3))>1){
		 	   				int begYear = Integer.parseInt(caseData.getInterestBegDate().substring(0,3));
		 	   				int endYear = Integer.parseInt(caseData.getInterestEndDate().substring(0,3));
			 	   			String endDate = caseData.getInterestEndDate();
			 	   			String begDate = caseData.getInterestBegDate();
		 	   				for(int x=begYear;x<=endYear;x++){
		 	   					if(x==begYear){
					 	   			//第一段
						 	   		caseData.setInterestEndDate(begDate.substring(0,3)+"1231");
					 	   			caseData.setDateBetween(DateUtility.daysBetween(caseData.getInterestBegDate(),caseData.getInterestEndDate())+1);//天數
					 	   			if(!rateMap.isEmpty()){
					 	   				if(rateMap.get(begDate.substring(0,3)) != null){
						 	   				iRate = (double)rateMap.get(begDate.substring(0,3));
						 	   				if(DateUtility.isLeapYear(begDate)){
						 	   					tryInterest = (int) Math.round(caseData.getAccAmt().intValue() * caseData.getDateBetween()* iRate / 366);//利息
							 	   			}else{
							 	   				tryInterest = (int) Math.round(caseData.getAccAmt().intValue() * caseData.getDateBetween()* iRate / 365);//利息
							 	   			}
							 	   			caseData.setiRate(BigDecimal.valueOf(iRate));
								 	   		caseData.setTryInterest(BigDecimal.valueOf((long)tryInterest));//試算利息
							 	   			caseData.setAdjInterest(BigDecimal.valueOf((long)tryInterest));//調整利息
					 	   				}else{
					 	   					caseData.setiRate(BigDecimal.valueOf(0));
					 	   					caseData.setRateMsg(caseData.getInterestBegDate().substring(0,3));
						 	   			}
						 	   		}
				 	   				listData.add(caseData);
		 	   					}else if(x==endYear){
		 	   						//第二段
					 	   			PaymentStageDtlCase secData = new PaymentStageDtlCase();
					 	   			BeanUtility.copyProperties(secData, caseData);
						 	   		secData.setInterestBegDate(endDate.substring(0,3)+"0101");
						 	   		secData.setInterestEndDate(endDate);
						 	   		secData.setDateBetween(DateUtility.daysBetween(secData.getInterestBegDate(),secData.getInterestEndDate())+1);//天數
					 	   			if(!rateMap.isEmpty()){
					 	   				if(rateMap.get(endDate.substring(0,3)) != null){
						 	   				iRate = (double)rateMap.get(endDate.substring(0,3));
							 	   			secData.setiRate(BigDecimal.valueOf(iRate));
								 	   		secData.setRePayAmt(BigDecimal.ZERO);//還款金額
								 	   		secData.setAccAmtEnd(secData.getAccAmt().subtract(secData.getRePayAmt()));
									 	   	if(DateUtility.isLeapYear(endDate)){
						 	   					tryInterest = (int) Math.round(secData.getAccAmt().intValue() * secData.getDateBetween()* iRate / 366);//利息
							 	   			}else{
							 	   				tryInterest = (int) Math.round(secData.getAccAmt().intValue() * secData.getDateBetween()* iRate / 365);//利息
							 	   			}
									 	    secData.setAccAmt(BigDecimal.ZERO);//剩餘本金
						 	   				secData.setTryInterest(BigDecimal.valueOf((long)tryInterest));//試算利息
							 	   			secData.setAdjInterest(BigDecimal.valueOf((long)tryInterest));//調整利息
								 	   		
					 	   				}else{
						 	   				secData.setRePayAmt(BigDecimal.ZERO);//還款金額
								 	   		secData.setAccAmt(BigDecimal.ZERO);//剩餘本金
								 	   		secData.setAccAmtEnd(secData.getAccAmt().subtract(secData.getRePayAmt()));
								 	   		secData.setiRate(BigDecimal.valueOf(0));
								 	   		secData.setRateMsg(endDate.substring(0,3));
						 	   			}
						 	   		}
						 	   		listData.add(secData);
		 	   					}else{
		 	   						//其他
					 	   			PaymentStageDtlCase otherData = new PaymentStageDtlCase();
					 	   			BeanUtility.copyProperties(otherData, caseData);
					 	   			otherData.setInterestBegDate(String.valueOf(x)+"0101");
					 	   			otherData.setInterestEndDate(String.valueOf(x)+"1231");
					 	   			otherData.setDateBetween(DateUtility.daysBetween(otherData.getInterestBegDate(),otherData.getInterestEndDate())+1);//天數
					 	   			if(!rateMap.isEmpty()){
					 	   				if(rateMap.get(String.valueOf(x)) != null){
						 	   				iRate = (double)rateMap.get(String.valueOf(x));
							 	   			otherData.setiRate(BigDecimal.valueOf(iRate));
							 	   			otherData.setRePayAmt(BigDecimal.ZERO);//還款金額
							 	   			otherData.setAccAmtEnd(otherData.getAccAmt().subtract(otherData.getRePayAmt()));
								 	   		if(DateUtility.isLeapYear(String.valueOf(x)+"0101")){
						 	   					tryInterest = (int) Math.round(otherData.getAccAmt().intValue() * otherData.getDateBetween()* iRate / 366);//利息
							 	   			}else{
							 	   				tryInterest = (int) Math.round(otherData.getAccAmt().intValue() * otherData.getDateBetween()* iRate / 365);//利息
							 	   			}
								 	   		otherData.setAccAmt(BigDecimal.ZERO);//剩餘本金
								 	   		otherData.setTryInterest(BigDecimal.valueOf((long)tryInterest));//試算利息
							 	   			otherData.setAdjInterest(BigDecimal.valueOf((long)tryInterest));//調整利息
					 	   				}else{
					 	   					otherData.setRePayAmt(BigDecimal.ZERO);//還款金額
					 	   					otherData.setAccAmt(BigDecimal.ZERO);//剩餘本金
					 	   					otherData.setAccAmtEnd(otherData.getAccAmt().subtract(otherData.getRePayAmt()));
					 	   					otherData.setiRate(BigDecimal.valueOf(0));
					 	   					otherData.setRateMsg(endDate.substring(0,3));
						 	   			}
						 	   		}
						 	   		listData.add(otherData);
		 	   					}
		 	   				}
		 	   			}else{
			 	   			String endDate = caseData.getInterestEndDate();
			 	   			String begDate = caseData.getInterestBegDate();
			 	   			//第一段
				 	   		caseData.setInterestEndDate(begDate.substring(0,3)+"1231");
			 	   			caseData.setDateBetween(DateUtility.daysBetween(caseData.getInterestBegDate(),caseData.getInterestEndDate())+1);//天數
			 	   			if(!rateMap.isEmpty()){
			 	   				if(rateMap.get(begDate.substring(0,3)) != null){
				 	   				iRate = (double)rateMap.get(begDate.substring(0,3));
				 	   				if(DateUtility.isLeapYear(begDate)){
				 	   					tryInterest = (int) Math.round(caseData.getAccAmt().intValue() * caseData.getDateBetween()* iRate / 366);//利息
					 	   			}else{
					 	   				tryInterest = (int) Math.round(caseData.getAccAmt().intValue() * caseData.getDateBetween()* iRate / 365);//利息
					 	   			}
					 	   			caseData.setiRate(BigDecimal.valueOf(iRate));
						 	   		caseData.setTryInterest(BigDecimal.valueOf((long)tryInterest));//試算利息
					 	   			caseData.setAdjInterest(BigDecimal.valueOf((long)tryInterest));//調整利息
			 	   				}else{
			 	   					caseData.setiRate(BigDecimal.valueOf(0));
			 	   					caseData.setRateMsg(caseData.getInterestBegDate().substring(0,3));
				 	   			}
				 	   		}
			 	   			listData.add(caseData);
			 	   			//第二段
			 	   			PaymentStageDtlCase secData = new PaymentStageDtlCase();
			 	   			BeanUtility.copyProperties(secData, caseData);
				 	   		secData.setInterestBegDate(endDate.substring(0,3)+"0101");
				 	   		secData.setInterestEndDate(endDate);
				 	   		secData.setDateBetween(DateUtility.daysBetween(secData.getInterestBegDate(),secData.getInterestEndDate())+1);//天數
			 	   			if(!rateMap.isEmpty()){
			 	   				if(rateMap.get(endDate.substring(0,3)) != null){
				 	   				iRate = (double)rateMap.get(endDate.substring(0,3));
					 	   			secData.setiRate(BigDecimal.valueOf(iRate));
						 	   		secData.setRePayAmt(BigDecimal.ZERO);//還款金額
						 	   		secData.setAccAmtEnd(secData.getAccAmt().subtract(secData.getRePayAmt()));
							 	   	if(DateUtility.isLeapYear(endDate)){
				 	   					tryInterest = (int) Math.round(secData.getAccAmt().intValue() * secData.getDateBetween()* iRate / 366);//利息
					 	   			}else{
					 	   				tryInterest = (int) Math.round(secData.getAccAmt().intValue() * secData.getDateBetween()* iRate / 365);//利息
					 	   			}

						 	   		secData.setAccAmt(BigDecimal.ZERO);//剩餘本金
				 	   				secData.setTryInterest(BigDecimal.valueOf((long)tryInterest));//試算利息
					 	   			secData.setAdjInterest(BigDecimal.valueOf((long)tryInterest));//調整利息
			 	   				}else{
				 	   				secData.setRePayAmt(BigDecimal.ZERO);//還款金額
						 	   		secData.setAccAmt(BigDecimal.ZERO);//剩餘本金
						 	   		secData.setAccAmtEnd(secData.getAccAmt().subtract(secData.getRePayAmt()));
						 	   		secData.setiRate(BigDecimal.valueOf(0));
						 	   		secData.setRateMsg(endDate.substring(0,3));
				 	   			}
				 	   		}
				 	   		listData.add(secData);
		 	   			}
		 	   			
		 	   		}else{
			 	   		if(!rateMap.isEmpty()){
			 	   			if(rateMap.get(caseData.getInterestBegDate().substring(0,3)) != null){
				 	   			iRate = (double)rateMap.get(caseData.getInterestBegDate().substring(0,3));
					 	   		if(DateUtility.isLeapYear(caseData.getInterestBegDate())){
			 	   					tryInterest = (int) Math.round(caseData.getAccAmt().intValue() * caseData.getDateBetween()* iRate / 366);//利息
				 	   			}else{
				 	   				tryInterest = (int) Math.round(caseData.getAccAmt().intValue() * caseData.getDateBetween()* iRate / 365);//利息
				 	   			}
				 	   			caseData.setiRate(BigDecimal.valueOf(iRate));
					 	   		caseData.setTryInterest(BigDecimal.valueOf((long)tryInterest));//試算利息
				 	   			caseData.setAdjInterest(BigDecimal.valueOf((long)tryInterest));//調整利息
			 	   			}else{
			 	   				caseData.setiRate(BigDecimal.valueOf(0));
			 	   				caseData.setRateMsg(caseData.getInterestBegDate().substring(0,3));
			 	   			}
			 	   		}
			 	   		listData.add(caseData);
		 	   		}	
	 	   		}else{
		 	   		if(!rateMap.isEmpty()){
		 	   			if(rateMap.get(caseData.getInterestBegDate().substring(0,3)) != null){
		 	   				iRate = (double)rateMap.get(caseData.getInterestBegDate().substring(0,3));
			 	   			if(DateUtility.isLeapYear(caseData.getInterestBegDate())){
		 	   					tryInterest = (int) Math.round(caseData.getAccAmt().intValue() * caseData.getDateBetween()* iRate / 366);//利息
			 	   			}else{
			 	   				tryInterest = (int) Math.round(caseData.getAccAmt().intValue() * caseData.getDateBetween()* iRate / 365);//利息
			 	   			}
		 	   				caseData.setiRate(BigDecimal.valueOf(iRate));
		 	   				caseData.setTryInterest(BigDecimal.valueOf((long)tryInterest));//試算利息
		 	   				caseData.setAdjInterest(BigDecimal.valueOf((long)tryInterest));//調整利息
		 	   			}else{
		 	   				caseData.setiRate(BigDecimal.valueOf(0));
		 	   				caseData.setRateMsg("查無"+caseData.getInterestBegDate().substring(0,3)+"該年度利率檔相關資料");
		 	   			}
		 	   			
		 	   		}
	 	   			listData.add(caseData);
	 	   		}
	 	   		
 	   		}
 	    }
    	return listData;
    }
    /**
     * 開單確認作業
     * 
     * @param List<PaymentProcessCase> resultList
     * @param List<PaymentStageDtlCase> listData
     * @return
     */
    public List<PaymentStageDtlCase> getPaymentStageDtl(PaymentProcessQueryForm queryForm){
    	int nowStage = queryForm.getTotAmtStage().add(queryForm.getInterestStage()).intValue();//總共期數(利息期數+本金期數)
    	BigDecimal stageAmt = queryForm.getStageAmt();//每期本金
    	BigDecimal payTotAmt = queryForm.getPayTotAmt();//應繳總本金
    	String lsPaymentDateLine = "";
    	List<PaymentStageDtlCase> returnList = new ArrayList<PaymentStageDtlCase>();
    	boolean flag = false;
    	if(nowStage>0){
    		for(int i=1; i <= nowStage; i++){
    			//條件:本金分期數大於0，目前的期數大於等於總本金分期數(若沒有利息分期問題，則最後一筆本金分期數=最終期數)
    			if(queryForm.getTotAmtStage().intValue()>0 && (i==queryForm.getTotAmtStage().intValue() || i==nowStage)){
    				flag = true;
    			}
    			PaymentStageDtlCase caseData = new PaymentStageDtlCase();
    			caseData.setNowStage(i); 
    			caseData.setPaymentNoDetail(queryForm.getPaymentNo().substring(0,13)+StringUtility.chtLeftPad(String.valueOf(i), 2, "0"));//繳款單號
    			//繳款期限
		 	   	if(StringUtils.isNotBlank(queryForm.getPaymentDateLine()) && (i==1)){
		 	   		caseData.setPaymentDateLine(queryForm.getPaymentDateLine());
		 	   		lsPaymentDateLine = queryForm.getPaymentDateLine();
		 	   	}else if(StringUtils.isBlank(queryForm.getPaymentDateLine())){
		 	   		caseData.setPaymentDateLine("");	 
		 	   		//lsPaymentDateLine = DateUtility.calDay(DateUtility.getNowChineseDate(), 30);
		 	   	}else{
	 	   			caseData.setPaymentDateLine(DateUtility.calMonth(queryForm.getPaymentDateLine(), i-1));
	 	   			lsPaymentDateLine = DateUtility.calMonth(queryForm.getPaymentDateLine(), i-1);
	 	   		}
		 	   	
		 	   	if(i >= queryForm.getTotAmtStage().intValue()){
		 	   		caseData.setMflag("1");
		 	   	}else{
		 	   		caseData.setMflag("0");
		 	   	}
		 	   	
		 	   	caseData.setExecAmt(BigDecimal.ZERO);
    			if(flag==false){
    		 	   	if(payTotAmt.intValue()>=stageAmt.intValue()){
    		 	   		caseData.setRePayAmt(stageAmt);
    		 	   		payTotAmt = payTotAmt.subtract(stageAmt);
    		 	   	}else{
    		 	   		caseData.setRePayAmt(payTotAmt);
    		 	   		payTotAmt=BigDecimal.ZERO;
    		 	   	}
    		 	   	caseData.setPayInterest(BigDecimal.ZERO);
    		 	   	
    			}else{
    				caseData.setRePayAmt(payTotAmt);
    		 	   	payTotAmt=BigDecimal.ZERO;	
        		 	   	
    				//最後一期
    				if(i==nowStage){    	
    					if(queryForm.getTotInterst()!=null){
    						caseData.setPayInterest(queryForm.getTotInterst());
    					}else{
    						caseData.setPayInterest(BigDecimal.ZERO);
    					}    					    					
    				}else{
    					caseData.setPayInterest(BigDecimal.ZERO);
    				}
    			}
    			
    			returnList.add(caseData);
    		}
    		return returnList;
    	}else{
    		return null;
    	}
    	
    }
    
    /**
     * 試算利息總額
     * 
     * @param List<PaymentProcessCase> resultList
     * @param List<PaymentStageDtlCase> listData
     * @return
     */
    public BigDecimal getTotInterest(List<PaymentStageDtlCase> listData){
    	BigDecimal interest = new BigDecimal(0);
		for(PaymentStageDtlCase cp:listData){
		   interest = interest.add(cp.getAdjInterest());
	   	}
	   	return interest;
    }
    
    /**
     * 試算分配明細
     * 
     * @param List<PaymentProcessCase> resultList
     * @param List<PaymentStageDtlCase> listData
     * @return
     */
    public List<PaymentStageDtlCase> getAssignPaymentData(List<PaymentProcessCase> resultList,List<PaymentStageDtlCase> listData){
    	List<PaymentProcessCase> rList = new ArrayList<PaymentProcessCase>();
    	for(int i=1;i<=resultList.size();i++){
    		for(int j=0; j<resultList.size(); j++){
    			if(StringUtils.isNotBlank(resultList.get(j).getWriteoffSeqNo())){
    				if(i==Integer.parseInt(resultList.get(j).getWriteoffSeqNo())){
        				rList.add(resultList.get(j));
        			}
    			}
    			
    		}
    	}
    	
    	int flagRePayAmt = 0;
	   	int flagPayAmt = 0;
	   	BigDecimal payAmt = rList.get(flagPayAmt).getPayAmt();
		BigDecimal rePayAmt = listData.get(flagRePayAmt).getRePayAmt();
		//用這個list來存最終結果
		List<PaymentStageDtlCase> returnList = new ArrayList<PaymentStageDtlCase>();
	   		do{
	   			PaymentStageDtlCase listCase = new PaymentStageDtlCase();
	   			//剛好一筆
	   			if(payAmt.compareTo(rePayAmt)==0){
	   				BeanUtility.copyProperties(listCase, listData.get(flagRePayAmt));
					listCase.setApno(rList.get(flagPayAmt).getApno());
					listCase.setPayYm(rList.get(flagPayAmt).getPayYm());
					listCase.setIssuYm(rList.get(flagPayAmt).getIssuYm());
					listCase.setPayAmt(payAmt);
					//寫入apno,issuym,payym
					returnList.add(listCase);
					rePayAmt = rePayAmt.subtract(payAmt);
					
					if(flagPayAmt+1<rList.size()){
						payAmt = rList.get(flagPayAmt+1).getPayAmt();
					}
					if(flagRePayAmt+1<listData.size()){
						rePayAmt = listData.get(flagRePayAmt+1).getRePayAmt();	
					}
					flagPayAmt = flagPayAmt + 1;
					flagRePayAmt = flagRePayAmt + 1;
				}else if(payAmt.compareTo(rePayAmt)>0){
					//一筆還有餘，下一筆可以繼續扣
					BeanUtility.copyProperties(listCase, listData.get(flagRePayAmt));
					//寫入apno,issuym,payym
	   				listCase.setApno(rList.get(flagPayAmt).getApno());
	   				listCase.setPayYm(rList.get(flagPayAmt).getPayYm());
	   				listCase.setIssuYm(rList.get(flagPayAmt).getIssuYm());
	   				listCase.setPayAmt(rePayAmt);
	   				returnList.add(listCase);
	   				payAmt = payAmt.subtract(rePayAmt);
	   				if(flagRePayAmt+1<listData.size()){
	   					rePayAmt = listData.get(flagRePayAmt+1).getRePayAmt();
	   				}
	   				flagRePayAmt = flagRePayAmt + 1;	   				
				}else{
					//拆兩筆	
					BeanUtility.copyProperties(listCase, listData.get(flagRePayAmt));
					listCase.setApno(rList.get(flagPayAmt).getApno());
	   				listCase.setPayYm(rList.get(flagPayAmt).getPayYm());
	   				listCase.setIssuYm(rList.get(flagPayAmt).getIssuYm());
	   				listCase.setPayAmt(payAmt);
	   				returnList.add(listCase);
					
	   				rePayAmt = rePayAmt.subtract(payAmt);
	   				if(flagPayAmt+1<rList.size()){
						payAmt = rList.get(flagPayAmt+1).getPayAmt();	
					}
					flagPayAmt = flagPayAmt + 1; 
				}
	   		}while(rePayAmt.compareTo(BigDecimal.ZERO)!=0);
	   		for(int i=0; i<returnList.size();i++){
	   			if(i==0){
	   				returnList.get(i).setNowStagForShow(String.valueOf(returnList.get(i).getNowStage()));
	   			}else{
	   				if(returnList.get(i).getNowStage() != returnList.get(i-1).getNowStage()){
	   					returnList.get(i).setNowStagForShow(String.valueOf(returnList.get(i).getNowStage()));
	   				}
	   			}
	   		}
	   		return returnList;
    }
    
    
    public void insertEveryPaymentData(UserBean userData, PaymentProcessQueryForm queryForm, List<PaymentProcessQueryCase> qryData, List<PaymentProcessCase> qryDataUnacpdtl,
    		List<PaymentStageDtlCase> prsInterest, List<PaymentStageDtlCase> dtlPayment, List<PaymentStageDtlCase> assPayment){
    	Bapayment paymentData = new Bapayment();
    	paymentData.setPaymentNo(queryForm.getPaymentNo());
    	paymentData.setCaseNo(queryForm.getClassNo());
    	paymentData.setCrtTime(DateUtility.getNowWestDateTime(true));
    	paymentData.setCrtUser(userData.getEmpNo());
    	if(StringUtils.equals(queryForm.getCaseType(),"一般案件")){
    		paymentData.setCaseType("1");
    	}else{
    		paymentData.setCaseType("2");
    	}
    	paymentData.setChkObj(queryForm.getChkObj());
    	paymentData.setPaymentDateLine(DateUtility.changeDateType(queryForm.getPaymentDateLine()));
    	paymentData.setPaymentZip(queryForm.getZipCode());
    	paymentData.setPaymentAddr(queryForm.getAddr());
    	paymentData.setTotAmtStage(queryForm.getTotAmtStage());
    	paymentData.setInterestStage(queryForm.getInterestStage());
    	paymentData.setStageAmt(queryForm.getStageAmt());
    	paymentData.setTotTrgAmt(queryForm.getTotTrgAmt());
    	paymentData.setInterestTryStage(queryForm.getInterestTryStage());
    	paymentData.setMonthlyPayAmt(queryForm.getMonthlyPayAmt());
    	paymentData.setPrtDate(DateUtility.changeDateType(queryForm.getPrtDate()));
    	paymentData.setIdn(queryForm.getIdn());
    	paymentData.setUseMk("Y");
    	if(queryForm.getTotInterst()!=null && queryForm.getTotExec()!=null){
    		paymentData.setPayTotAmt(queryForm.getPayTotAmt().add(queryForm.getTotInterst()).add(queryForm.getTotExec()));
    	}
    	
    	bapaymentDao.insertPaymentData(paymentData);
    	
    	if(qryDataUnacpdtl.size()>0 && qryDataUnacpdtl != null){
    		// 說明
            for (int i = 0; i < qryDataUnacpdtl.size(); i++) {
            	PaymentProcessCase qduData =qryDataUnacpdtl.get(i);
            	Bapaymentdtl pd = new Bapaymentdtl();
                pd.setApno(qduData.getApno());
                pd.setIssuYm(DateUtility.changeChineseYearMonthType(qduData.getIssuYm()));
                pd.setmApnoMk(qduData.getMapnoMk());
                pd.setPayAmt(qduData.getPayAmt());
                pd.setPaymentName(qduData.getBenName());
                pd.setPaymentNo(queryForm.getPaymentNo());
                pd.setPayYm(DateUtility.changeChineseYearMonthType(qduData.getPayYm()));
                pd.setPaymentSex(qduData.getPaymentSex());
                pd.setRecAmt(qduData.getRecAmt());
                pd.setRecRem(qduData.getRecRem());
                pd.setSeqNo(qduData.getSeqNo());
                pd.setWriteoffSeqno(qduData.getWriteoffSeqNo());
                bapaymentdtlDao.insertPaymentData(pd);
            }
    	}
    	if(prsInterest != null && prsInterest.size()>0){
    		// 說明List<PaymentStageDtlCase> 
            for (int i = 0; i < prsInterest.size(); i++) {
            	PaymentStageDtlCase stageData = prsInterest.get(i);
            	Bapaymentstagedtl pd = new Bapaymentstagedtl();
                pd.setAccAmt(stageData.getAccAmt());
                pd.setAdjInterest(stageData.getAdjInterest());
                pd.setInterestBegDate(DateUtility.changeDateType(stageData.getInterestBegDate()));
                pd.setInterestEndDate(DateUtility.changeDateType(stageData.getInterestEndDate()));
                pd.setRePayAmt(stageData.getRePayAmt());
                pd.setTryInterest(stageData.getTryInterest());
                pd.setiRate(stageData.getiRate());
                pd.setNowStage(BigDecimal.valueOf(stageData.getNowStage()));
                pd.setPaymentNo(queryForm.getPaymentNo());
            	bapaymentstagedtlDao.insertPaymentData(pd);
            }
    	}
    	if(assPayment != null  && assPayment.size()>0){
    		int finalAmtStage = 0;//本金期數的最後一期
    		// 說明List<PaymentStageDtlCase> 
            for (int i = 0; i < assPayment.size(); i++) {
            	Bapaymentassigndtl pd = new Bapaymentassigndtl();
            	PaymentStageDtlCase assData = assPayment.get(i);
                pd.setPaymentNo(queryForm.getPaymentNo());
                pd.setNowStage(BigDecimal.valueOf(assData.getNowStage()));
                pd.setApno(assData.getApno());
                pd.setExecAmt(assData.getExecAmt());
                pd.setIssuYm(DateUtility.changeChineseYearMonthType(assData.getIssuYm()));
                pd.setPayAmt(assData.getPayAmt());
                pd.setPayInterest(assData.getPayInterest());
                pd.setPaymentDateLine(DateUtility.changeDateType(assData.getPaymentDateLine()));
                pd.setPaymentNoDetail(assData.getPaymentNoDetail());
                pd.setPayYm(DateUtility.changeChineseYearMonthType(assData.getPayYm()));
                pd.setBarCode1(assData.getBarCode1());
                pd.setBarCode2(assData.getBarCode2());
                pd.setBarCode3(assData.getBarCode3());
                bapaymentassigndtlDao.insertPaymentData(pd);
                finalAmtStage = pd.getNowStage().intValue();
            }
            if(dtlPayment!=null && dtlPayment.size()>0){
            	for(int j=0;j<dtlPayment.size();j++){
                	PaymentStageDtlCase stageData = dtlPayment.get(j);
                	if(stageData.getNowStage()>finalAmtStage){
                		Bapaymentassigndtl pd = new Bapaymentassigndtl();
                		pd.setPaymentNo(queryForm.getPaymentNo());
                		pd.setPaymentDateLine(DateUtility.changeDateType(stageData.getPaymentDateLine()));
                		pd.setPaymentNoDetail(stageData.getPaymentNoDetail());
                		pd.setExecAmt(stageData.getExecAmt());
                		pd.setPayInterest(stageData.getPayInterest());
                		pd.setApno("0");
                		pd.setNowStage(BigDecimal.valueOf(stageData.getNowStage()));
                		pd.setBarCode1(stageData.getBarCode1());
                		pd.setBarCode2(stageData.getBarCode2());
                		pd.setBarCode3(stageData.getBarCode3());   
                		bapaymentassigndtlDao.insertPaymentData(pd);
                	}
                }
            }
            
    	}
    }
    
    
    public void updateEveryPaymentData(UserBean userData, PaymentProcessQueryForm queryForm, List<PaymentProcessQueryCase> qryData, List<PaymentProcessCase> qryDataUnacpdtl,
    		List<PaymentStageDtlCase> prsInterest, List<PaymentStageDtlCase> dtlPayment, List<PaymentStageDtlCase> assPayment){
    	Bapayment paymentData = new Bapayment();
    	paymentData.setPaymentNo(queryForm.getPaymentNo());
    	paymentData.setChkObj(queryForm.getChkObj());
    	paymentData.setCaseNo(queryForm.getClassNo());
    	paymentData.setUpdTime(DateUtility.getNowWestDateTime(true));
    	paymentData.setUpdUser(userData.getEmpNo());
    	if(StringUtils.equals(queryForm.getCaseType(),"一般案件")){
    		paymentData.setCaseType("1");
    	}else{
    		paymentData.setCaseType("2");
    	}
    	paymentData.setChkObj(queryForm.getChkObj());
    	paymentData.setPaymentDateLine(DateUtility.changeDateType(queryForm.getPaymentDateLine()));
    	paymentData.setPaymentZip(queryForm.getZipCode());
    	paymentData.setPaymentAddr(queryForm.getAddr());
    	paymentData.setTotAmtStage(queryForm.getTotAmtStage());
    	paymentData.setInterestStage(queryForm.getInterestStage());
    	paymentData.setStageAmt(queryForm.getStageAmt());
    	paymentData.setTotTrgAmt(queryForm.getTotTrgAmt());
    	paymentData.setInterestTryStage(queryForm.getInterestTryStage());
    	paymentData.setMonthlyPayAmt(queryForm.getMonthlyPayAmt());
    	paymentData.setPrtDate(DateUtility.changeDateType(queryForm.getPrtDate()));
    	paymentData.setIdn(queryForm.getIdn());
    	paymentData.setUseMk("Y");
    	if(queryForm.getTotInterst()!=null && queryForm.getTotExec()!=null){
    		paymentData.setPayTotAmt(queryForm.getPayTotAmt().add(queryForm.getTotInterst()).add(queryForm.getTotExec()));
    	}
    	//paymentData.setPayTotAmt(queryForm.getPayTotAmt());
    	bapaymentDao.updatePaymentData(paymentData);
    	bapaymentdtlDao.deletePaymentData(paymentData.getPaymentNo());
    	bapaymentstagedtlDao.deletePaymentData(paymentData.getPaymentNo());
    	bapaymentassigndtlDao.deletePaymentData(paymentData.getPaymentNo());
    	if(qryDataUnacpdtl.size()>0 && qryDataUnacpdtl != null){
    		// 說明
            for (int i = 0; i < qryDataUnacpdtl.size(); i++) {
            	PaymentProcessCase qduData =qryDataUnacpdtl.get(i);
            	Bapaymentdtl pd = new Bapaymentdtl();
                pd.setApno(qduData.getApno());
                pd.setIssuYm(DateUtility.changeChineseYearMonthType(qduData.getIssuYm()));
                pd.setmApnoMk(qduData.getMapnoMk());
                pd.setPayAmt(qduData.getPayAmt());
                pd.setPaymentName(qduData.getBenName());
                pd.setPaymentNo(queryForm.getPaymentNo());
                pd.setPayYm(DateUtility.changeChineseYearMonthType(qduData.getPayYm()));
                pd.setRecAmt(qduData.getRecAmt());
                pd.setRecRem(qduData.getRecRem());
                pd.setSeqNo(qduData.getSeqNo());
                pd.setWriteoffSeqno(qduData.getWriteoffSeqNo());
                bapaymentdtlDao.insertPaymentData(pd);
            }
    	}
    	if(prsInterest != null && prsInterest.size()>0){
    		// 說明List<PaymentStageDtlCase> 
            for (int i = 0; i < prsInterest.size(); i++) {
            	PaymentStageDtlCase stageData = prsInterest.get(i);
            	Bapaymentstagedtl pd = new Bapaymentstagedtl();
                pd.setAccAmt(stageData.getAccAmt());
                pd.setAdjInterest(stageData.getAdjInterest());
                pd.setInterestBegDate(DateUtility.changeDateType(stageData.getInterestBegDate()));
                pd.setInterestEndDate(DateUtility.changeDateType(stageData.getInterestEndDate()));
                pd.setRePayAmt(stageData.getRePayAmt());
                pd.setTryInterest(stageData.getTryInterest());
                pd.setiRate(stageData.getiRate());
                pd.setNowStage(BigDecimal.valueOf(stageData.getNowStage()));
                pd.setPaymentNo(queryForm.getPaymentNo());
            	bapaymentstagedtlDao.insertPaymentData(pd);
            }
    	}
    	if(assPayment != null  && assPayment.size()>0){
    		int finalAmtStage = 0;//本金期數的最後一期
    		// 說明List<PaymentStageDtlCase> 
            for (int i = 0; i < assPayment.size(); i++) {
            	Bapaymentassigndtl pd = new Bapaymentassigndtl();
            	PaymentStageDtlCase assData = assPayment.get(i);
                pd.setPaymentNo(queryForm.getPaymentNo());
                pd.setNowStage(BigDecimal.valueOf(assData.getNowStage()));
                pd.setApno(assData.getApno());
                pd.setExecAmt(assData.getExecAmt());
                pd.setIssuYm(DateUtility.changeChineseYearMonthType(assData.getIssuYm()));
                pd.setPayAmt(assData.getPayAmt());
                pd.setPayInterest(assData.getPayInterest());
                pd.setPaymentDateLine(DateUtility.changeDateType(assData.getPaymentDateLine()));
                pd.setPaymentNoDetail(assData.getPaymentNoDetail());
                pd.setPayYm(DateUtility.changeChineseYearMonthType(assData.getPayYm()));
                pd.setBarCode1(assData.getBarCode1());
                pd.setBarCode2(assData.getBarCode2());
                pd.setBarCode3(assData.getBarCode3());
                bapaymentassigndtlDao.insertPaymentData(pd);
                finalAmtStage = pd.getNowStage().intValue();
            }
            //如果利率資料
            if(dtlPayment!=null && dtlPayment.size()>0){
            	for(int j=0;j<dtlPayment.size();j++){
                	PaymentStageDtlCase stageData = dtlPayment.get(j);
                	if(stageData.getNowStage()>finalAmtStage){
                		stageData.getPaymentNoDetail();
                		Bapaymentassigndtl pd = new Bapaymentassigndtl();
                		pd.setPaymentNo(queryForm.getPaymentNo());
                		pd.setPaymentNoDetail(stageData.getPaymentNoDetail());
                		pd.setExecAmt(stageData.getExecAmt());
                		pd.setPaymentDateLine(DateUtility.changeDateType(stageData.getPaymentDateLine()));
                		pd.setPayInterest(stageData.getPayInterest());
                		pd.setApno("0");
                		pd.setNowStage(BigDecimal.valueOf(stageData.getNowStage()));
                		pd.setBarCode1(stageData.getBarCode1());
                		pd.setBarCode2(stageData.getBarCode2());
                		pd.setBarCode3(stageData.getBarCode3());                		
                		bapaymentassigndtlDao.insertPaymentData(pd);
                	}
                }
            }
            
    	}
    }
    public PaymentUpdateCase getPaymentProcessQueryList(String paymentNo){//繳款單主檔資料
    	//繳款單主檔資料
    	Bapayment caseData = bapaymentDao.getPaymentData(paymentNo);
    	PaymentUpdateCase updateCase = new PaymentUpdateCase();
    	
    	if(caseData != null){
        	if(StringUtils.equals(caseData.getCaseType(),"1")){//繳款單類別
        		updateCase.setCaseType("一般案件");
        	}else{
        		updateCase.setCaseType("傳繳案件");
        	}
        	updateCase.setChkObj(caseData.getChkObj());//繳款期限類別1:文到30日內2:系統日+30日
        	updateCase.setPaymentNo(caseData.getPaymentNo());//繳款單號
        	updateCase.setPaymentDateLine(DateUtility.changeDateType(caseData.getPaymentDateLine()));//繳款期限
        	updateCase.setZipCode(caseData.getPaymentZip());//郵遞區號
        	updateCase.setAddr(caseData.getPaymentAddr());//地址
        	updateCase.setTotAmtStage(caseData.getTotAmtStage());//本金期數
        	updateCase.setInterestStage(caseData.getInterestStage());//利息期數
        	updateCase.setStageAmt(caseData.getStageAmt());//每期攤還本金
        	updateCase.setClassNo(caseData.getCaseNo());//移送案號
        	updateCase.setTotTrgAmt(caseData.getTotTrgAmt());//利息標的金額
        	updateCase.setInterestTryStage(caseData.getInterestTryStage());//利息試算期數
        	updateCase.setMonthlyPayAmt(caseData.getMonthlyPayAmt());//利息每期本金
        	updateCase.setPrtDate(DateUtility.changeDateType(caseData.getPrtDate()));//列印日期
        	updateCase.setIdn(caseData.getIdn());//身分證號
        	updateCase.setPayTotAmt(caseData.getPayTotAmt());//應繳總額
    	}
    	return updateCase;
    	
    }
	public List<PaymentProcessCase> getPaymentProcessDetailList(String paymentNo){//應收未收資料(繳款單明細檔)
		//BAPM0D012M
		List<Bapaymentdtl> caseList = bapaymentdtlDao.getPaymentProcessDetailList(paymentNo);
		List<PaymentProcessCase> returnList = new ArrayList<PaymentProcessCase>();
		if(caseList!=null && caseList.size()>0){
			for(int i=0; i<caseList.size(); i++){
				Bapaymentdtl caseData = caseList.get(i);
				PaymentProcessCase returnCase = new PaymentProcessCase();
				returnCase.setApno(caseData.getApno());//受理編號
				returnCase.setIssuYm(DateUtility.changeWestYearMonthType(caseData.getIssuYm()));//核定年月
				returnCase.setMapnoMk(caseData.getmApnoMk());//主辦案號
				returnCase.setPayYm(DateUtility.changeWestYearMonthType(caseData.getPayYm()));//給付年月
				returnCase.setPayAmt(caseData.getPayAmt());//應繳本金
				returnCase.setBenName(caseData.getPaymentName());//繳款單姓名
				returnCase.setRecAmt(caseData.getRecAmt());//應收金額 
				returnCase.setRecRem(caseData.getRecRem());//未收餘額
				returnCase.setSeqNo(caseData.getSeqNo());//受款人序
				returnCase.setWriteoffSeqNo(caseData.getWriteoffSeqno());//銷帳序
				returnList.add(returnCase);
			}
			return returnList;
		}else{
			return null;
		}
		
	}
	
	public List<PaymentProcessCase> getPaymentQueryDetailList(String paymentNo){//應收未收資料(繳款單明細檔)
		//BAPM0D012M
		List<Bapaymentdtl> caseList = bapaymentdtlDao.getPaymentQueryDetailList(paymentNo);
		List<PaymentProcessCase> returnList = new ArrayList<PaymentProcessCase>();
		if(caseList!=null && caseList.size()>0){
			for(int i=0; i<caseList.size(); i++){
				Bapaymentdtl caseData = caseList.get(i);
				PaymentProcessCase returnCase = new PaymentProcessCase();
				returnCase.setApno(caseData.getApno());//受理編號
				returnCase.setIssuYm(DateUtility.changeWestYearMonthType(caseData.getIssuYm()));//核定年月
				returnCase.setMapnoMk(caseData.getmApnoMk());//主辦案號
				returnCase.setPayYm(DateUtility.changeWestYearMonthType(caseData.getPayYm()));//給付年月
				returnCase.setPayAmt(caseData.getPayAmt());//應繳本金
				returnCase.setBenName(caseData.getPaymentName());//繳款單姓名
				returnCase.setRecAmt(caseData.getRecAmt());//應收金額 
				returnCase.setRecRem(caseData.getRecRem());//未收餘額
				returnCase.setSeqNo(caseData.getSeqNo());//受款人序
				returnCase.setWriteoffSeqNo(caseData.getWriteoffSeqno());//銷帳序
				returnList.add(returnCase);
			}
			return returnList;
		}else{
			return null;
		}
		
	}
	public List<PaymentReprintCase> getPaymentReprintList(String idn, String paymentNo, String caseNo,String prtDt,String sequenceNo, String paymentDate){//繳款單列印作業
		//BAPM0D012M
		List<Bapaymentdtl> caseList = bapaymentdtlDao.getPaymentReprintList(idn, paymentNo, caseNo, prtDt,  sequenceNo,  paymentDate);
		List<PaymentReprintCase> returnList = new ArrayList<PaymentReprintCase>();
		if(caseList!=null && caseList.size()>0){
			for(int i=0; i<caseList.size(); i++){
				Bapaymentdtl caseData = caseList.get(i);
				PaymentReprintCase returnCase = new PaymentReprintCase();
				returnCase.setCaseNo(caseData.getCaseNo());//移送案號
				if(StringUtils.isNotBlank(caseData.getUpdTime())){
					returnCase.setMaintainDate(DateUtility.changeDateType(StringUtils.substring(caseData.getUpdTime(), 0, 8)));//維護日期
				}else{
					returnCase.setMaintainDate(DateUtility.changeDateType(StringUtils.substring(caseData.getCrtTime(), 0, 8)));//維護日期
				}
				returnCase.setmApno(caseData.getApno());//主辦案號
				returnCase.setPayTotAmt(caseData.getPayTotAmt());//應繳總額
				returnCase.setPaymentNo(caseData.getPaymentNo());//繳款單號
				returnCase.setPrtDate(DateUtility.changeDateType(caseData.getPrtDate()));//列印日期
				returnCase.setPaymentName(caseData.getPaymentName());//繳款單姓名
				returnCase.setIdn(caseData.getIdn());//身分證號
				int maxInterest = bapaymentassigndtlDao.getMaxInterest(returnCase.getPaymentNo());
				returnCase.setMaxInterest(maxInterest);
				
				returnList.add(returnCase);
			}
			return returnList;
		}else{
			return null;
		}
		
	}
	
	public void updatePrintDate(String westDate,String paymentNo, List<PaymentStageDtlCase> paymentList){//更新列印日期及barcode
		int data = bapaymentDao.updatePrintDate(westDate,paymentNo);//更新列印日期
		for(int i=0;i<paymentList.size();i++){
			PaymentStageDtlCase pd = paymentList.get(i);
			Bapaymentassigndtl dd = new Bapaymentassigndtl();
			dd.setBarCode1(pd.getBarCode1());//條碼1
			dd.setBarCode2(pd.getBarCode2());//條碼2
			dd.setBarCode3(pd.getBarCode3());//條碼3
			dd.setPaymentNoDetail(pd.getPaymentNoDetail());//繳款單號明細
			int data1 = bapaymentassigndtlDao.updatePaymentBarcode(dd);
		}
		
	}
	public List<PaymentReprintCase> getPaymentReprintdtlList(PaymentReprintCase paymentCase){//補印明細清單
		//BAPM0D012M
		List<Bapaymentassigndtl> caseList = bapaymentassigndtlDao.getPaymentReprintList(paymentCase.getPaymentNo());
		List<PaymentReprintCase> returnList = new ArrayList<PaymentReprintCase>();
		if(caseList!=null && caseList.size()>0){
			for(int i=0; i<caseList.size(); i++){
				Bapaymentassigndtl caseData = caseList.get(i);
				PaymentReprintCase returnCase = new PaymentReprintCase();
				returnCase.setMaintainDate(paymentCase.getMaintainDate());//維護日期
				returnCase.setPaymentNo(caseData.getPaymentNoDetail());//繳款單號
				returnCase.setmApno(paymentCase.getmApno());//主辦案號
				returnCase.setPayTotAmt(caseData.getPayAmt());//應繳總額
				returnCase.setCaseNo(paymentCase.getCaseNo());//移送案號
				returnCase.setPrtDate(paymentCase.getPrtDate());//列印日期
				returnList.add(returnCase);
			}
			return returnList;
		}else{
			return null;
		}				
	}
	public List<PaymentStageDtlCase> getPaymentInterestDetailListForDb(String paymentNo){//試算利息資料
		//BAPM0D013M BAPM0D012M
		List<Bapaymentstagedtl> caseList = bapaymentstagedtlDao.getPaymentStageDetailList(paymentNo);//取得試算利息資料
		List<PaymentStageDtlCase> returnList = new ArrayList<PaymentStageDtlCase>();
		if(caseList!=null && caseList.size()>0){
			for(int i=0; i<caseList.size(); i++){
				Bapaymentstagedtl caseData = caseList.get(i);
				PaymentStageDtlCase returnCase = new PaymentStageDtlCase();
				returnCase.setAccAmt(caseData.getAccAmt());//剩餘本金
				returnCase.setAdjInterest(caseData.getAdjInterest());//調整利息
				returnCase.setInterestBegDate(DateUtility.changeDateType(caseData.getInterestBegDate()));//利息起算日
				returnCase.setInterestEndDate(DateUtility.changeDateType(caseData.getInterestEndDate()));//利息結算日
				returnCase.setDateBetween(DateUtility.daysBetween(returnCase.getInterestBegDate(),returnCase.getInterestEndDate())+1);//天數
				returnCase.setRePayAmt(caseData.getRePayAmt());//還款金額
                returnCase.setTryInterest(caseData.getTryInterest());//試算利息
                returnCase.setiRate(caseData.getiRate());//利率
                returnCase.setNowStage(caseData.getNowStage().intValue());//期別
                returnList.add(returnCase);
			}
			return returnList;
		}else{
			return null;
		}				
				
	}
	public List<PaymentStageDtlCase> getPaymentAssignDetailListForDb(String paymentNo){//本金分配明細
		//BAPM0D014,BAPM0D015
		List<Bapaymentassigndtl> caseList = bapaymentassigndtlDao.getPaymentAssignDetailList(paymentNo);//取得繳款單本金分配明細資料
		List<PaymentStageDtlCase> returnList = new ArrayList<PaymentStageDtlCase>();
		if(caseList!=null && caseList.size()>0){
			for(int i=0; i<caseList.size(); i++){
				Bapaymentassigndtl caseData = caseList.get(i);
				PaymentStageDtlCase returnCase = new PaymentStageDtlCase();
				returnCase.setNowStage(caseData.getNowStage().intValue());//期別
				returnCase.setApno(caseData.getApno());//受理編號
				returnCase.setExecAmt(caseData.getExecAmt());//執行費用
				returnCase.setIssuYm(DateUtility.changeWestYearMonthType(caseData.getIssuYm()));//核定年月
				returnCase.setPayAmt(caseData.getPayAmt());//應繳本金
				returnCase.setPayInterest(caseData.getPayInterest());//利息資料
				returnCase.setPaymentNoDetail(caseData.getPaymentNoDetail());
				returnCase.setPayYm(DateUtility.changeWestYearMonthType(caseData.getPayYm()));//給付年月
		        returnList.add(returnCase);
			}
			return returnList;
		}else{
			return null;
		}				
	}
	
	public List<PaymentDetailCase> getUnacpdtlData(PaymentWebserviceProcessCase caseData,String paymentNo){
		//BAPM0D014,BAPM0D015
		List<Bapaymentdtl> caseList = bapaymentdtlDao.getUnacpdtlData(paymentNo);
		List<PaymentDetailCase> returnList = new ArrayList<PaymentDetailCase>();
		if(caseList!=null && caseList.size()>0){
			for(int i=0; i<caseList.size(); i++){
				Bapaymentdtl cd = caseList.get(i);
				PaymentDetailCase returnCase = new PaymentDetailCase();
				returnCase.setAdWkMk(cd.getAdwkMk());//一般加職註記
				returnCase.setApno(cd.getApno());//受理編號
				returnCase.setBaUnacpdtlId(cd.getBaunacpdtlId());//應收未收id
				returnCase.setBenIdnNo(cd.getBenIdnNo());//受益人idn
				returnCase.setBenIds(cd.getBenIds());//受益人社福識別碼
				returnCase.setIssuYm(cd.getIssuYm());//核定年月
				returnCase.setNlWkMk(cd.getNlwkMk());//普職註記
				returnCase.setPayAmt(cd.getPayAmt());//應繳金額
				returnCase.setRecAmt(cd.getRecAmt());//應收總金額
				returnCase.setRecRem(cd.getRecRem());//未收總金額
				returnCase.setSeqNo(cd.getSeqNo());//序號
				returnCase.setSts(cd.getSts());//欠費狀態
				returnCase.setUnacpDate(cd.getUnacpDate());//應收立帳日期
				returnCase.setWriteOffSeqno(cd.getWriteoffSeqno());//銷帳序
				returnCase.setPayKind(cd.getPayKind());//給付種類
				returnList.add(returnCase);
			}
			return returnList;
		}else{
			return null;
		}	
	}
	public PaymentDetailCase getBaappbaseData(PaymentWebserviceProcessCase caseData, String paymentNo){
		//BAPM0D014,BAPM0D015
		Baappbase caseDt = baappbaseDao.getBaappbaseData(paymentNo);//主檔資料
		PaymentDetailCase returnCase = new PaymentDetailCase();
		if(caseDt!=null){
			returnCase.setBenIdnNo(caseDt.getBenIdnNo());//受益人idn
			returnCase.setBenName(caseDt.getBenName());//受益人姓名
			return returnCase;
		}else{
			return null;
		}	
	}
	public List<PaymentStageDtlCase> getPaymentAssignDetailList(String paymentNo, int lastStageForAmt){//利息及執行費的分配明細
		//BAPM0D014,BAPM0D015
		List<Bapaymentassigndtl> caseList = bapaymentassigndtlDao.getPaymentConfirmDetailList(paymentNo);//取得利息及執行費的分配明細
		List<PaymentStageDtlCase> returnList = new ArrayList<PaymentStageDtlCase>();
		if(caseList!=null && caseList.size()>0){
			for(int i=0; i<caseList.size(); i++){
				Bapaymentassigndtl caseData = caseList.get(i);
				PaymentStageDtlCase returnCase = new PaymentStageDtlCase();
				returnCase.setPaymentNoDetail(caseData.getPaymentNoDetail());//繳款單號明細(有帶期別)
				returnCase.setNowStage(caseData.getNowStage().intValue());//期別
				if(caseData.getNowStage().intValue()>=lastStageForAmt){//假如期別為最後一期，則利息與執行費為可修改，用於繳款單修改作業1:可修改0:不可修改
					returnCase.setMflag("1");
				}else{
					returnCase.setMflag("0");
				}
				returnCase.setExecAmt(caseData.getExecAmt());//執行費用
				returnCase.setPayInterest(caseData.getPayInterest());//利息費用
				returnCase.setPayAmt(caseData.getPayAmt());//應繳本金
				returnCase.setRePayAmt(caseData.getPayAmt());//還款金額
				returnCase.setPaymentDateLine(DateUtility.changeDateType(caseData.getPaymentDateLine()));//繳款期限
		        returnList.add(returnCase);
			}
			return returnList;
		}else{
			return null;
		}				
	}
	public List<PaymentProgressQueryCase> getPaymentQueryData(String idn, String paymentNo, String caseNo, String sequenceNo, String paymentDate){
		//BAPM0D014,BAPM0D015
		List<Bapayment> caseList = bapaymentDao.getPaymentQueryData(idn, paymentNo, caseNo, sequenceNo, paymentDate);
		List<PaymentProgressQueryCase> returnList = new ArrayList<PaymentProgressQueryCase>();
		if(caseList!=null && caseList.size()>0){
			for(int i=0; i<caseList.size(); i++){
				Bapayment caseData = caseList.get(i);
				PaymentProgressQueryCase returnCase = new PaymentProgressQueryCase();
				returnCase.setCaseNo(caseData.getCaseNo());
				returnCase.setmApno(caseData.getApno());
				returnCase.setNowStage(caseData.getNowStage());
				returnCase.setPaymentNo(caseData.getPaymentNo());
				returnCase.setPayTotAmt(caseData.getPayTotAmt());
				returnCase.setPrtDate(DateUtility.changeDateType(caseData.getPrtDate()));
				returnCase.setPaymentName(caseData.getPaymentName());
				returnCase.setIdn(caseData.getIdn());
		        returnList.add(returnCase);
			}
			return returnList;
		}else{
			return null;
		}						
	}
	public List<PaymentStageDtlCase> getTryStageDataForQuery(List<PaymentStageDtlCase> prsInterest){
		String flag = "0";
		for(int i=0;i<prsInterest.size();i++){  
        	PaymentStageDtlCase stageData = prsInterest.get(i);
            
        	if(stageData.getRePayAmt().intValue()==0 && stageData.getAccAmt().intValue()==0 && stageData.getiRate().intValue() != 0){
            	// 還款金額
            	prsInterest.get(i).setRePayAmt(BigDecimal.ZERO);
                // 剩餘本金
            	prsInterest.get(i).setAccAmt(BigDecimal.ZERO);
               
            }else{
            	 // 還款金額
            	prsInterest.get(i).setRePayAmt(stageData.getRePayAmt());
                // 剩餘本金
            	prsInterest.get(i).setAccAmt(stageData.getAccAmt());
            	
            	prsInterest.get(i).setAccAmtEnd(stageData.getAccAmt().subtract(stageData.getRePayAmt()));
            }
            if(i>0){
            	if(stageData.getNowStage() == prsInterest.get(i-1).getNowStage()){
            		prsInterest.get(i).setNowStage(0);
            	}
            }
            
        }
		return prsInterest;
		
	}
	public List<PaymentStageDtlCase> getPaymenInterestExecAmt(String paymentNo){
		//BAPM0D014,BAPM0D015
		List<Bapaymentassigndtl> caseList = bapaymentassigndtlDao.getPaymentConfirmDetail(paymentNo);
		List<PaymentStageDtlCase> returnList = new ArrayList<PaymentStageDtlCase>();
		if(caseList!=null && caseList.size()>0){
			for(int i=0; i<caseList.size(); i++){
				Bapaymentassigndtl caseData = caseList.get(i);
				PaymentStageDtlCase returnCase = new PaymentStageDtlCase();
				returnCase.setPaymentNoDetail(caseData.getPaymentNoDetail());//繳款單號明細
				returnCase.setNowStage(caseData.getNowStage().intValue());//期別
				returnCase.setExecAmt(caseData.getExecAmt());//執行費用
				returnCase.setPayInterest(caseData.getPayInterest());//利息費用
				returnCase.setPayAmt(caseData.getPayAmt());//應繳金額
				returnCase.setRePayAmt(caseData.getPayAmt());//剩餘金額
				returnCase.setPaymentDateLine(DateUtility.changeDateType(caseData.getPaymentDateLine()));//繳款期限
		        returnList.add(returnCase);
			}
			return returnList;
		}else{
			return null;
		}						
		
	}
	public Map<String, Double> getYearRateMapData(){
		List<Bccmf45> bccmf45Data =  bccmf45Dao.getYearRateMapData();
		Map<String,Double> returnMap = new HashMap<String, Double>();
		if(bccmf45Data!=null && bccmf45Data.size()>0){
			for(int i=0; i<bccmf45Data.size(); i++){
				Double rate = 0.0d;
				if(bccmf45Data.get(i).getRate().compareTo(BigDecimal.ZERO)>0){
					rate = bccmf45Data.get(i).getRate().divide(new BigDecimal(100)).doubleValue();
				}
				if(StringUtils.isNotBlank(bccmf45Data.get(i).getYear()) && rate.compareTo(0.0d)>0)
					returnMap.put(DateUtility.changeWestYearType(bccmf45Data.get(i).getYear()), rate);
			}
		}
		return returnMap;
	}
	
	public void insertPaymentWebservice(PaymentWebserviceProcessCase processData){
		if(processData!=null){
			Bapaymentremote remoteData = new Bapaymentremote();
			remoteData.setBarCode1(processData.getBarCode1());//條碼1
			remoteData.setBarCode2(processData.getBarCode2());//條碼2
			remoteData.setBarCode3(processData.getBarCode3());//條碼3
			remoteData.setCashAmt(processData.getAmt());//應繳總金額
			remoteData.seteDate(processData.getEdate());//入帳日期
			remoteData.setSitDte(processData.getSitDte());//繳納日期
			remoteData.setPno(processData.getPno());//員工編號
			remoteData.setIdNo(processData.getIdno());//身分證號
			remoteData.setWaitMk(processData.getWaitMk());//待確認
			remoteData.setPerUnitCd(processData.getPerUnitCd());//個人或單位
			remoteData.setPerUnitName(processData.getPerUnitName());//個人或單位名稱
			remoteData.setDivMrk(processData.getDivMrk());//分割註記
			bapaymentremoteDao.insertPaymentWebservice(remoteData);// 寫入PF系統傳入之狀態
			   
		}
		if(processData.getDtlCase()!=null && processData.getDtlCase().size()>0 ){
			for(int i=0;i<processData.getDtlCase().size();i++){
				 PaymentWebserviceDtlCase dtlCase = processData.getDtlCase().get(i);
				 Bapaymentremotedtl caseData = new Bapaymentremotedtl();
				 caseData.setAdwkMk(dtlCase.getAdwkMk());//一般加職註記
				 caseData.setBarCode1(processData.getBarCode1());//條碼1
				 caseData.setBarCode2(processData.getBarCode2());//條碼2
				 caseData.setBarCode3(processData.getBarCode3());//條碼3
				 caseData.setCmMk(dtlCase.getCmmk());//現醫註記
				 caseData.setDivAmount(dtlCase.getDivAmout());//分割金額
				 caseData.setDivSeq(dtlCase.getDivSeq());//分割序號
				 caseData.setGvSeq(dtlCase.getGvSeq());//受款人序
				 caseData.setNlwkMk(dtlCase.getNlwkMk());//普職註記
				 caseData.setTempChkMemo(dtlCase.getTempChkMemo());//暫收查明移至摘要
				 caseData.setTempHandleNo(dtlCase.getTempHandleNo());//提繳受理編號
				 bapaymentremotedtlDao.insertPaymentWebservice(caseData);//寫入處理pf系統傳入狀態後的資料
			}
						
		}
	}
	
	public void updatePaymentRemoteData(PaymentWebserviceProcessCase caseData){
		if(caseData!=null){
			Bapaymentremote remoteData = new Bapaymentremote();
			remoteData.setBarCode1(caseData.getBarCode1());//條碼1
			remoteData.setBarCode2(caseData.getBarCode2());//條碼2
			remoteData.setBarCode3(caseData.getBarCode3());//條碼3
			remoteData.setInsKd(caseData.getInsKd());//保險別
			remoteData.setBliAccountCode(caseData.getBliAccountCode());//局帳戶代號
			remoteData.setBookEdBook(caseData.getBookEdBook());//入帳方式
			remoteData.setBkAccountDt(caseData.getBkAccountDt());//入帳日期
			remoteData.setBatchNo(caseData.getBatchNo());//批號
			remoteData.setSerialNo(caseData.getSerialNo());//流水號
			remoteData.setIndexNo(caseData.getIndexNo());//序號
			remoteData.setMsg(caseData.getMsg());//訊息
			remoteData.setStatus(caseData.getStatus());//狀態
			
			bapaymentremoteDao.updatePaymentWebservice(remoteData);//更新PF系統回寫的繳款單狀態
		}
	}
	public void setBapaymentremoteDao(BapaymentremoteDao bapaymentremoteDao) {
		this.bapaymentremoteDao = bapaymentremoteDao;
	}
	public void setBapaymentremotedtlDao(BapaymentremotedtlDao bapaymentremotedtlDao) {
		this.bapaymentremotedtlDao = bapaymentremotedtlDao;
	}
	/**
	   * 刪除繳款單(只是update註記)
	   * 
	   * @param paymentNo 繳款單號
	   * @return
	   */
	public void delelteFromBapayment(String paymentNo){
		bapaymentDao.updateUsemkForBapayment(paymentNo);
	}
    public static void setLog(Log log) {
		PaymentService.log = log;
	}
	public void setBapaymentDao(BapaymentDao bapaymentDao) {
		this.bapaymentDao = bapaymentDao;
	}
	public void setBaunacpdtlDao(BaunacpdtlDao baunacpdtlDao) {
		this.baunacpdtlDao = baunacpdtlDao;
	}
	public void setBaappbaseDao(BaappbaseDao baappbaseDao) {
		this.baappbaseDao = baappbaseDao;
	}
	public void setBapaymentdtlDao(BapaymentdtlDao bapaymentdtlDao) {
		this.bapaymentdtlDao = bapaymentdtlDao;
	}
	public void setBapaymentstagedtlDao(BapaymentstagedtlDao bapaymentstagedtlDao) {
		this.bapaymentstagedtlDao = bapaymentstagedtlDao;
	}
	public void setBapaymentassigndtlDao(BapaymentassigndtlDao bapaymentassigndtlDao) {
		this.bapaymentassigndtlDao = bapaymentassigndtlDao;
	}
	public void setBccmf45Dao(Bccmf45Dao bccmf45Dao) {
		this.bccmf45Dao = bccmf45Dao;
	}
}
