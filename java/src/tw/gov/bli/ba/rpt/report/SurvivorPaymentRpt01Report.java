package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.util.StringUtil;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Badapr;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.query.cases.PaymentQueryBenDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryChkFileDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryDetailDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryDisabledDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryFamilyDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryIssuPayDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryIssuPayExtDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryLetterTypeMkCase;
import tw.gov.bli.ba.query.cases.PaymentQueryOccAccDataCase;
import tw.gov.bli.ba.query.forms.PaymentQueryForm;
import tw.gov.bli.ba.rpt.cases.CiptUtilityCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01AnnuityPayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01BenDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01BenPayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01Case;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01ChangeDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01ChkfileDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01ChkfileDescCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01DecideDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01DeductAnnuityDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01DeductOnceDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01DieOncePayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01DiePayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01DisablePayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01InjuryPayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01IssueAmtDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01JoblessPayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01LoanDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01MonthAvgAmtDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01NotifyDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01NpPayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01OncePayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01PayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01UnitCase;
import tw.gov.bli.ba.util.DateUtility;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import tw.gov.bli.ba.util.ValidateUtility;
import tw.gov.bli.common.helper.UserSessionHelper;
import tw.gov.bli.common.util.DateUtil;

/**
 * 勞保老年年金給付查詢 案件資料
 * 
 * @author frank
 */
public class SurvivorPaymentRpt01Report extends ReportBase {

    private int nPage; // 每筆資料的頁數

    private String printDate = ""; // 印表日期

    private String line = "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";

    public SurvivorPaymentRpt01Report() throws Exception {
        super();
        nPage = 0;
        printDate = DateUtility.formatNowChineseDateString(false);
    }

    public Document createDocument() {
        // 參數說明: Document(頁面大小, 左邊空白, 右邊空白, 上面空白, 下面空白)
        Document document = new Document(PageSize.A4.rotate(), 10, 4, 20, 20);
        return document;
    }

    /**
     * 加入分隔線
     * 
     * @param table
     * @param icolspan Column Span
     * @throws Exception
     */
    public void addLine(Table table) throws Exception {
        addColumn(table, 60, 1, line, fontCh8, 0, LEFT);
    }

    /**
     * 加入空白行
     * 
     * @param table
     * @param rows 行數
     * @throws Exception
     */
    public void addEmptyRow(Table table, int rows) throws Exception {
        for (int i = 0; i < rows; i++) {
            addColumn(table, 60, 1, "　", fontCh12, 0, LEFT); // 注意: 內容是全形空白
        }
    }

    /**
     * 刪除行
     * 
     * @param table
     * @param rows 行數
     * @throws Exception
     */
    public void deleteRow(Table table, int rows) throws Exception {
        for (int i = 0; i < rows; i++) {
            table.deleteLastRow();
        }
    }

    /**
     * 建立表頭
     * 
     * @param payListSize 給付查詢 案件資料
     * @param attached 是否為附表
     * @return
     * @throws Exception
     */
    public Table addHeader(UserBean userData ,PaymentQueryDetailDataCase caseData, boolean attached) throws Exception {
        document.newPage();
        
        // 建立表格
        Table table = createTable(60);
        table.setAutoFillEmptyCells(true);
        table.setAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        createPageText(735, 540,writer.getCurrentPageNumber(), "頁次 ：", " (共  ",8, 150, 50);
        //460, 795, writer.getCurrentPageNumber(), "第 ", " 頁，共 ", 11, 150, 50,bfKChinese
        addColumn(table, 60, 1, "給付查詢", fontCh18, 0, CENTER);
        
        addColumn(table, 10, 1, "受理編號："+caseData.getApNoStrDisplay() , fontCh8, 0, LEFT);
        addColumn(table, 20, 1, "事故者姓名："+caseData.getEvtName(), fontCh8, 0, LEFT);
        addColumn(table, 15, 1, "印表人員："+userData.getEmpNo()  , fontCh8, 0, RIGHT);
        addColumn(table, 15, 1, "   印表日期：" + printDate, fontCh8, 0, LEFT);
        //addColumn(table, 6, 1, "頁次：" + StringUtils.leftPad(String.valueOf(writer.getCurrentPageNumber() - nPage), 3, "0"), fontCh8, 0, RIGHT);
        //addColumn(table, 6, 1, "( 共 " + StringUtils.leftPad(String.valueOf(writer.getCurrentPageNumber() - nPage)+" 頁 )", 3, "0"), fontCh8, 0, RIGHT);
        addLine(table);
        
        return table;
    }

    public ByteArrayOutputStream doReport(UserBean userData, PaymentQueryDetailDataCase caseData ,List<PaymentQueryIssuPayDataCase>  issuPayDataList,List<Badapr> origIssuPayDataLis,List<PaymentQueryDetailDataCase> chkFileDataList,List<PaymentQueryLetterTypeMkCase> detail1,List<PaymentQueryLetterTypeMkCase> detail2,List<PaymentQueryLetterTypeMkCase> detail3,List<PaymentQueryLetterTypeMkCase> detail4,List<PaymentQueryLetterTypeMkCase> detail5,PaymentQueryLetterTypeMkCase detail6,PaymentQueryForm qryForm,List<PaymentQueryDetailDataCase> benChkList,List<PaymentQueryDetailDataCase> matchChkList,PaymentQueryDisabledDataCase disabledData,List<PaymentQueryBenDataCase> benDataList) throws Exception {
        try {
            document.open();
            
//            for (int index = 0; index < 5; index++) {
//                // 頁次處理
//                if (index > 0) {
//                    nPage = writer.getPageNumber();
//                }
            
            // 表頭
            Table table = addHeader(userData, caseData, false);
            // 受理編號 + 系統類別
            String apnoAndSysCode = caseData.getApNoStrDisplay().concat(" ").concat(caseData.getSysCode());
            
            addColumn(table, 20, 1, "受理編號：" + apnoAndSysCode, fontCh12, 0, LEFT);
            addColumn(table, 20, 1, "事故日期/申請日期："+DateUtil.changeDateType(caseData.getEvtJobDate())+"/"+DateUtil.changeDateType(caseData.getAppDate()) , fontCh12, 0, LEFT);
            addColumn(table, 20, 1, "給付別："+caseData.getPagePayKindStr() , fontCh12, 0, LEFT);

            if (StringUtils.isNotBlank(caseData.getRmp_Name())) {
                addColumn(table, 60, 1, "事故者姓名："+caseData.getEvtName() + "(羅馬拼音：" + caseData.getRmp_Name() + ")" , fontCh12, 0, LEFT);
            } else {
                addColumn(table, 60, 1, "事故者姓名："+caseData.getEvtName() , fontCh12, 0, LEFT);
            }

            addColumn(table, 20, 1, "事故者身分證號："+caseData.getEvtIdnNo() , fontCh12, 0, LEFT);
            addColumn(table, 20, 1, "事故者出生日期："+caseData.getEvtBrDateStr() , fontCh12, 0, LEFT);
            addColumn(table, 20, 1, "事故者年齡/性別："+caseData.getEvtAge()+"歲/"+caseData.getEvtSexStr() , fontCh12, 0, LEFT);

            
            addColumn(table, 20, 1, "電腦/人工審核結果："+caseData.getAcceptMkStr()+"/"+caseData.getManchkMkStr() , fontCh12, 0, LEFT);
            addColumn(table, 20, 1, "處理狀態："+caseData.getProcStatStr() , fontCh12, 0, LEFT);
            addColumn(table, 20, 1, "給付種類："+caseData.getPayKindStr() , fontCh12, 0, LEFT);
            
            addLine(table);  //【案件資料】
            
            addColumn(table, 30, 1, "【案件資料】", fontCh12, 0, LEFT);
            if(StringUtils.isBlank(caseData.getEmpExt())){
            	caseData.setEmpExt("");
            }
            addColumn(table, 22, 1, " " , fontCh9, 0, RIGHT);
            addColumn(table, 8, 1, "承辦人分機號碼："+caseData.getEmpExt() , fontCh9, 0, LEFT);
            
            if(StringUtils.isBlank(caseData.getApItem())){
                addColumn(table, 30, 1, caseData.getApItem()+"- 申請項目："+caseData.getApItemStr() , fontCh12, 0, LEFT);
            }else{
            	addColumn(table, 30, 1, "申請項目："+caseData.getApItemStr() , fontCh12, 0, LEFT);
            }
            
            addColumn(table, 15, 1, "核定格式："+caseData.getNotifyForm(), fontCh12, 0, LEFT);
            addColumn(table, 15, 1, "承辦人員："+caseData.getPromoteUser(), fontCh12, 0, LEFT);
            
            
            addColumn(table, 15, 1, "申請單位："+caseData.getApUbno() , fontCh12, 0, LEFT);
            addColumn(table, 15, 1, "事故發生單位： "+caseData.getLsUbno() , fontCh12, 0, LEFT);
            addColumn(table, 15, 1, "判決日期： "+disabledData.getJudgeDateStr() , fontCh12, 0, LEFT);
            addColumn(table, 15, 1, "符合日期："+caseData.getEvtEligibleDateStr(), fontCh12, 0, LEFT);
            
            addColumn(table, 15, 1, "傷病分類："+disabledData.getEvTypStr() , fontCh12, 0, LEFT);
//            addColumn(table, 15, 1, "傷病原因："+disabledData.getEvCode() , fontCh12, 0, LEFT);
//            addColumn(table, 15, 1, "受傷部位："+disabledData.getCriInPartStr() , fontCh12, 0, LEFT);
            addColumn(table, 45, 1, "屆齡日期："+caseData.getEvtExpireDateStr() , fontCh12, 0, LEFT);
            
//            addColumn(table, 30, 1, "國際疾病代碼："+disabledData.getCriInJnmeStr() , fontCh12, 0, LEFT);
//            addColumn(table, 15, 1, "媒介物："+disabledData.getCriMedium() , fontCh12, 0, LEFT);
            addColumn(table, 60, 1, "先核普通："+disabledData.getPrType() , fontCh12, 0, LEFT);
            
            addColumn(table, 60, 1, "來源受理編號：" + caseData.getApnoFm(), fontCh12, 0, LEFT);
            
            if(StringUtils.isBlank(caseData.getOldApNo())){
            	caseData.setOldApNo("");
            }
            addColumn(table, 30, 1, "老年年金受理編號："+caseData.getOldApNo() , fontCh12, 0, LEFT);
            if(caseData.getDabAnnuAmt() == null){
            	caseData.setDabAnnuAmt(new BigDecimal(0));
            }
            addColumn(table, 15, 1, "老年年金核定金額："+DecimalFormat.getNumberInstance().format(caseData.getDabAnnuAmt()).toString() , fontCh12, 0, LEFT);
            addColumn(table, 15, 1, "符合第63條之1第3項： "+disabledData.getFamAppMkStr() , fontCh12, 0, LEFT);
            
            if(StringUtils.isBlank(caseData.getDisApNo())){
            	caseData.setDisApNo("");
            }
            addColumn(table, 30, 1, "失能年金受理編號："+caseData.getDisApNo() , fontCh12, 0, LEFT);
            if(caseData.getDisAnnuAmt() == null){
            	caseData.setDisAnnuAmt(new BigDecimal(0));
            }
            addColumn(table, 15, 1, "失能年金核定金額："+DecimalFormat.getNumberInstance().format(caseData.getDisAnnuAmt()).toString() , fontCh12, 0, LEFT);
            if(caseData.getInterValMonth().equals("0") || caseData.getInterValMonth().equals("1") || caseData.getInterValMonth().equals(""))
                addColumn(table, 15, 1, "發放方式：按月發放", fontCh12, 0, LEFT); //發放方式
            else if (!caseData.getInterValMonth().equals("0") || !caseData.getInterValMonth().equals("1") || !caseData.getInterValMonth().equals(""))
            	addColumn(table, 15, 1, "發放方式：按"+caseData.getInterValMonth()+"個月發放", fontCh12, 0, LEFT); //發放方式
            
            if(caseData.getPayAmts() == null){
            	caseData.setPayAmts(new BigDecimal(0));
            }
            addColumn(table, 10, 1, "首次給付年月／金額： "+caseData.getPayYmsStr(), fontCh12, 0, LEFT);
            if(!StringUtils.isBlank(caseData.getPayYmsStr()) || !StringUtils.isBlank(caseData.getPayYmeStr()) ){
            	  addColumn(table, 20, 1, "~"+caseData.getPayYmeStr()+"／"+DecimalFormat.getNumberInstance().format(caseData.getPayAmts()).toString(), fontCh12, 0, LEFT);
             }else{
            	 addColumn(table, 20, 1,caseData.getPayYmeStr()+"／"+DecimalFormat.getNumberInstance().format(caseData.getPayAmts()).toString(), fontCh12, 0, LEFT);
             }
            if(caseData.getAnnuAmt() == null){
            	caseData.setAnnuAmt(new BigDecimal(0));
            }
            addColumn(table, 30, 1, "累計已領年金金額： "+DecimalFormat.getNumberInstance().format(caseData.getAnnuAmt()).toString() , fontCh12, 0, LEFT);
            
            addColumn(table, 15, 1, "更正註記： "+caseData.getChgMk() , fontCh12, 0, LEFT);
            addColumn(table, 45, 1, "受理鍵入資料及修改紀錄： (鍵入／更正人員代號："+caseData.getCrtUser()+"／"+caseData.getUpdUser()+")" , fontCh12, 0, LEFT);
            
            addLine(table);  //【最新核定資料】
            
            addColumn(table, 60, 1, "【最新核定資料】", fontCh12, 0, LEFT);
            
            addColumn(table, 30, 1, "核定年月："+caseData.getIssuYmStr(), fontCh12, 0, LEFT);
            
            //給付年月起迄
            addColumn(table, 10, 1, "給付年月起迄："+caseData.getMinPayYmStr(), fontCh12, 0, LEFT);
            if(StringUtils.isBlank(caseData.getMinPayYmStr()) || StringUtils.isBlank(caseData.getMaxPayYmStr())){
            	addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
            }else{
            	addColumn(table, 2, 1, "~", fontCh12, 0, LEFT);
            }
            addColumn(table, 18, 1, caseData.getMaxPayYmStr(), fontCh12, 0, LEFT);
            //---
            
            if(caseData.getBefIssueAmt() == null){
            	caseData.setBefIssueAmt(new BigDecimal(0));
            }
            addColumn(table, 30, 1, "核定總額："+
            		DecimalFormat.getNumberInstance().format(caseData.getBefIssueAmt()).toString(), fontCh12, 0, LEFT);
            if(caseData.getAplPayAmt() == null){
            	caseData.setAplPayAmt(new BigDecimal(0));
            }
            addColumn(table, 30, 1, "實付總額："+DecimalFormat.getNumberInstance().format(caseData.getAplPayAmt().add(caseData.getInheritorAmt())).toString(), fontCh12, 0, LEFT);
            
            if(caseData.getLsInsmAmt() == null){
            	caseData.setLsInsmAmt(new BigDecimal(0));
            }
            addColumn(table, 30, 1, "投保薪資："+DecimalFormat.getNumberInstance().format(caseData.getLsInsmAmt()).toString(), fontCh12, 0, LEFT);
            if(caseData.getInsAvgAmt() == null){
            	caseData.setInsAvgAmt(new BigDecimal(0));
            }
            addColumn(table, 30, 1, "平均薪資："+DecimalFormat.getNumberInstance().format(caseData.getInsAvgAmt()).toString(), fontCh12, 0, LEFT);
            
            
            addColumn(table, 30, 1, "投保年資："+caseData.getNitrmY()+"年"+caseData.getNitrmM()+"月"+"("+caseData.getItrmY()+"年"+caseData.getItrmD()+"日)" , fontCh12, 0, LEFT);
            addColumn(table, 30, 1, "實付年資："+caseData.getAplPaySeniY()+"年"+caseData.getAplPaySeniM()+"月", fontCh12, 0, LEFT);
            
            if(caseData.getOldaAmt() == null){
            	caseData.setOldaAmt(new BigDecimal(0));
            }
            addColumn(table, 30, 1, "基本月給付金額／計算式："+DecimalFormat.getNumberInstance().format(caseData.getOldaAmt()).toString()+"／"+caseData.getOldAb(), fontCh12, 0, LEFT);
            //避免資料帶入顯示為null 以""替換
            String oldRateDateYMStr = "";
            if(StringUtils.isBlank(caseData.getOldRateDateYMStr())){
            	oldRateDateYMStr = "";
            }else{
            	oldRateDateYMStr = caseData.getOldRateDateYMStr();
            }
            if(caseData.getOldRate() == null){
            	caseData.setOldRate(new BigDecimal(0));
            }
            if(!StringUtils.isBlank(caseData.getOldRateDateYMStr()) || !StringUtils.isBlank(caseData.getOldRate().toString())){
            	
                if(caseData.getApItem().equals("2")){
                    addColumn(table, 30, 1, "減額期間／比率："+oldRateDateYMStr+"／"+caseData.getOldRate()+"%", fontCh12, 0, LEFT);
                }else{
                    addColumn(table, 30, 1, "展延期間／比率："+oldRateDateYMStr+"／"+caseData.getOldRate()+"%", fontCh12, 0, LEFT);
                }
            }else{
            	
            	if(caseData.getApItem().equals("2")){
                    addColumn(table, 30, 1, "減額期間／比率："+oldRateDateYMStr, fontCh12, 0, LEFT);
            	}else{
                    addColumn(table, 30, 1, "展延期間／比率："+oldRateDateYMStr, fontCh12, 0, LEFT);	
            	}
            }
            
            if(caseData.getOldbAmt() == null){
            	caseData.setOldbAmt(new BigDecimal(0));
            }
            addColumn(table, 30, 1, "勞保給付金額："+DecimalFormat.getNumberInstance().format(caseData.getOldbAmt()).toString() , fontCh12, 0, LEFT);
            if(caseData.getOldRate() == null){
            	caseData.setOldRate(new BigDecimal(0));
            }
            if(caseData.getOldRate() != null){
            	if(caseData.getOldRateAmt() != null){
            		addColumn(table, 30, 1, "遺屬加計比例／金額："+caseData.getOldRate()+"%／"+DecimalFormat.getNumberInstance().format(caseData.getOldRateAmt()).toString() , fontCh12, 0, LEFT);
            	}else{
            		addColumn(table, 30, 1, "遺屬加計比例／金額："+caseData.getOldRate()+"%" , fontCh12, 0, LEFT);
            	}
            	
            }else{
            	addColumn(table, 30, 1, "遺屬加計比例／金額：" , fontCh12, 0, LEFT);
            }
            
            addColumn(table, 8, 1, "審核人員／日期："+caseData.getChkMan() , fontCh12, 0, LEFT);
            if(!StringUtils.isBlank(caseData.getChkMan()) || !StringUtils.isBlank(caseData.getChkDateStr())){
            	addColumn(table, 2, 1, "／" , fontCh12, 0, LEFT);
            }else{
            	addColumn(table, 2, 1, " " , fontCh12, 0, LEFT);
            }
            addColumn(table, 5, 1, caseData.getChkDateStr() , fontCh12, 0, LEFT);
            
            addColumn(table, 8, 1, "複核人員／日期："+caseData.getRechkMan(), fontCh12, 0, LEFT);
            if(!StringUtils.isBlank(caseData.getRechkMan()) || !StringUtils.isBlank(caseData.getRechkDateStr())){
            	addColumn(table, 2, 1, "／" , fontCh12, 0, LEFT);
            }else{
            	addColumn(table, 2, 1, " " , fontCh12, 0, LEFT);
            }
            addColumn(table, 5, 1, caseData.getRechkDateStr() , fontCh12, 0, LEFT);
            
            addColumn(table, 8, 1, "決行人員／日期："+caseData.getExeMan(), fontCh12, 0, LEFT);
            if(!StringUtils.isBlank(caseData.getExeMan()) || !StringUtils.isBlank(caseData.getExeDateStr())){
            	addColumn(table, 2, 1, "／" , fontCh12, 0, LEFT);
            }else{
            	addColumn(table, 2, 1, " " , fontCh12, 0, LEFT);
            }
            addColumn(table, 5, 1, caseData.getExeDateStr() , fontCh12, 0, LEFT);
            
            addColumn(table, 15, 1, " ", fontCh12, 0, LEFT);
            
            addColumn(table, 60, 1, "更正原因："+caseData.getChgNote(), fontCh12, 0, LEFT);
            
            // 事故者編審註記---
            addColumn(table, 15, 1, "事故者編審註記：", fontCh12, 0, LEFT);
            addColumn(table, 45, 1, " ", fontCh12, 0, LEFT);
            
            if(chkFileDataList.size() > 0){
            for(PaymentQueryDetailDataCase chkFileData: chkFileDataList){
            	
            	addColumn(table, 10, 1, chkFileData.getIssuPayYm()+"：" , fontCh12, 0, LEFT);
            	
            	List<PaymentQueryChkFileDataCase> chkFile = chkFileData.getChkFileDataList();
            	StringBuffer chkfileString = new StringBuffer("");
            	for(int i = 0 ; i < chkFile.size() ; i++){
            	
            	if(i == chkFile.size() - 1){
                      chkfileString.append(chkFile.get(i).getChkCodePost());
            	}else{
            		  chkfileString.append(chkFile.get(i).getChkCodePost()+" ,");
            	}
            	}
            	addColumn(table, 50, 1, chkfileString.toString() , fontCh12, 0, LEFT); //事故者編審註記
            	
            	// 在塞分隔線前, 先隨便塞空白行測試是否需換頁
                addEmptyRow(table, 3);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // 換了頁就不印分隔線了
                    deleteRow(table, 3);
                    document.add(table);
                    table = addHeader(userData ,caseData, false);
                }
                else {
                    deleteRow(table, 3);
                }
            }           
            }
            //---
            addLine(table);
            // 眷屬符合註記---
            addColumn(table, 15, 1, "遺屬符合註記：", fontCh12, 0, LEFT);
            addColumn(table, 45, 1, " ", fontCh12, 0, LEFT);
            
            if(matchChkList.size() > 0){
            	
            	for(PaymentQueryDetailDataCase masterChkFile : matchChkList ){
            		addColumn(table, 60, 1, "遺屬序："+masterChkFile.getSeqNo(), fontCh12, 0, LEFT);
            		List<PaymentQueryDetailDataCase> otherChkDataList = masterChkFile.getOtherChkDataList();
            		for(PaymentQueryDetailDataCase chkFileData : otherChkDataList){
            			addColumn(table, 10, 1, chkFileData.getIssuPayYm() , fontCh12, 0, LEFT);
            			 List<PaymentQueryChkFileDataCase> chkFile = chkFileData.getChkFileDataList();
            			 StringBuffer chkfileString = new StringBuffer("");
                     	 for(int x = 0 ; x < chkFile.size() ; x++){
                     	    if(x == chkFile.size() - 1){
                                chkfileString.append(chkFile.get(x).getChkCodePost());
                     	    }else{
                     		    chkfileString.append(chkFile.get(x).getChkCodePost()+" ,");
                     	 }
                     	 }
                     	 addColumn(table, 50, 1, chkfileString.toString() , fontCh12, 0, LEFT); //眷屬符合註記
                         // 在塞分隔線前, 先隨便塞空白行測試是否需換頁
                         addEmptyRow(table, 3);

                         if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            // 換了頁就不印分隔線了
                            deleteRow(table, 3);
                            document.add(table);
                            table = addHeader(userData ,caseData, false);
                         }
                        else {
                            deleteRow(table, 3);
                        }
            			 }
            		}
            	
            	}
            
            //---
            addLine(table);
            // 眷屬編審註記---
            addColumn(table, 15, 1, "遺屬編審註記：", fontCh12, 0, LEFT);
            addColumn(table, 45, 1, " ", fontCh12, 0, LEFT);
            
            if(benChkList.size() > 0){
            	
            	for(PaymentQueryDetailDataCase benChkFile : benChkList ){
            		addColumn(table, 60, 1, "遺屬序："+benChkFile.getSeqNo(), fontCh12, 0, LEFT);
            		List<PaymentQueryDetailDataCase> otherChkDataList = benChkFile.getOtherChkDataList();
            		for(PaymentQueryDetailDataCase chkFileData : otherChkDataList){
            			addColumn(table, 10, 1, chkFileData.getIssuPayYm() , fontCh12, 0, LEFT);
            			 List<PaymentQueryChkFileDataCase> chkFile = chkFileData.getChkFileDataList();
            			 StringBuffer chkfileString = new StringBuffer("");
                     	 for(int x = 0 ; x < chkFile.size() ; x++){
                     	    if(x == chkFile.size() - 1){
                                chkfileString.append(chkFile.get(x).getChkCodePost());
                     	    }else{
                     		    chkfileString.append(chkFile.get(x).getChkCodePost()+" ,");
                     	 }
                     	 }
                     	 addColumn(table, 50, 1, chkfileString.toString() , fontCh12, 0, LEFT); //眷屬符合註記
                         // 在塞分隔線前, 先隨便塞空白行測試是否需換頁
                         addEmptyRow(table, 3);

                         if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            // 換了頁就不印分隔線了
                            deleteRow(table, 3);
                            document.add(table);
                            table = addHeader(userData ,caseData, false);
                         }
                        else {
                            deleteRow(table, 3);
                        }
            			 }
            		}
            	
            	}
            //---
            
            addLine(table);
            
            //【結案資料】
            
            addColumn(table, 60, 1, "【結案資料】", fontCh12, 0, LEFT);
            
            addColumn(table, 15, 1, "結案日期："+caseData.getCloseDateStr(), fontCh12, 0, LEFT);// 結案日期
            addColumn(table, 15, 1, "結案原因："+caseData.getCloseCause(), fontCh12, 0, LEFT);//結案原因：
            addColumn(table, 30, 1, " ", fontCh12, 0, LEFT);
            if(StringUtils.isBlank(caseData.getBmApNo())){
            	caseData.setBmApNo("");
            }

            addLine(table);
            
            //---在塞分隔線前, 先隨便塞空白行測試是否需換頁
            addEmptyRow(table, 3);

            if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                // 換了頁就不印分隔線了
                deleteRow(table, 3);
                document.add(table);
                table = addHeader(userData ,caseData, false);
                }else {
                deleteRow(table, 3);
                }
            //---
            //---在塞分隔線前, 先隨便塞空白行測試是否需換頁
            addEmptyRow(table, 5);

            if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                // 換了頁就不印分隔線了
                deleteRow(table, 5);
                document.add(table);
                table = addHeader(userData ,caseData, false);
                }else {
                deleteRow(table, 5);
                }
            //【行政支援記錄】
            addColumn(table, 60, 1, "【行政支援記錄】", fontCh12, 0, LEFT);
            
            //交查日期：---
            addColumn(table, 15, 1, "交查日期：", fontCh12, 0, LEFT);
            StringBuffer detail1ProDateString = new StringBuffer("");
            
            if(detail1.size() > 0){
            for(int i = 0 ; i < detail1.size() ; i++){
            	if(i == detail1.size() - 1){
            		detail1ProDateString.append(detail1.get(i).getProDate());
            	}else{
            		detail1ProDateString.append(detail1.get(i).getProDate()+" ,");
            	}
            }
            addColumn(table, 15, 1, detail1ProDateString.toString() , fontCh12, 0, LEFT); //交查日期
            }else{
            addColumn(table, 15, 1, " ", fontCh12, 0, LEFT);	//交查日期
            }
            
            //---
            
            //不給付日期：---
            addColumn(table, 15, 1, "不給付日期：", fontCh12, 0, LEFT);
            
            StringBuffer detail2ProDateString = new StringBuffer("");
            if(detail2.size() > 0){
            	
            	for(int i = 0 ; i < detail2.size() ; i++){
            		
                	if(i == detail2.size()-1){
                		
                		if(StringUtils.isBlank(detail2.get(i).getNdomk2())){
                		   detail2ProDateString.append(detail2.get(i).getNdomk1()+"-"+detail2.get(i).getProDateStr());
                		   }else{
                		   detail2ProDateString.append(detail2.get(i).getNdomk1()+"、"+detail2.get(i).getNdomk2()+"-"+detail2.get(i).getProDateStr());
                		   }
                	}else{
                		if(StringUtils.isBlank(detail2.get(i).getNdomk2())){
                    		detail2ProDateString.append(detail2.get(i).getNdomk1()+"-"+detail2.get(i).getProDateStr()+",");
                    		}else{
                    		detail2ProDateString.append(detail2.get(i).getNdomk1()+"、"+detail2.get(i).getNdomk2()+"-"+detail2.get(i).getProDateStr()+",");
                    		}
                	}
            	 }
            	addColumn(table, 15, 1, detail2ProDateString.toString() , fontCh12, 0, LEFT); //不給付日期：
            }else{
            	addColumn(table, 15, 1, " ", fontCh12, 0, LEFT); //不給付日期：
            }
            //---
            
            //補件日期：---
            addColumn(table, 15, 1, "補件日期：", fontCh12, 0, LEFT);
            StringBuffer detail3ProDateString = new StringBuffer("");
            
            if(detail3.size() > 0){
            for(int i = 0 ; i < detail3.size() ; i++){
            	if(i == detail3.size() - 1){
            		detail3ProDateString.append(detail3.get(i).getProDate());
            	}else{
            		detail3ProDateString.append(detail3.get(i).getProDate()+" ,");
            	}
            }
            addColumn(table, 15, 1, detail3ProDateString.toString() , fontCh12, 0, LEFT); //補件日期：
            }else{
            addColumn(table, 15, 1, " ", fontCh12, 0, LEFT);	//補件日期：
            }
            
            //---

            //照會日期：---
            addColumn(table, 15, 1, "照會日期：", fontCh12, 0, LEFT);
            StringBuffer detail4ProDateString = new StringBuffer("");
            
            if(detail4.size() > 0){
            for(int i = 0 ; i < detail4.size() ; i++){
            	if(i == detail4.size() - 1){
            		detail4ProDateString.append(detail4.get(i).getProDate());
            	}else{
            		detail4ProDateString.append(detail4.get(i).getProDate()+" ,");
            	}
            }
            addColumn(table, 15, 1, detail4ProDateString.toString() , fontCh12, 0, LEFT); //照會日期：
            }else{
            addColumn(table, 15, 1, " ", fontCh12, 0, LEFT);	//照會日期：
            }
            
            //---
            
            //調病歷日期：
            addColumn(table, 60, 1, "調病歷日期：", fontCh12, 0, LEFT);
            
            //---在塞分隔線前, 先隨便塞空白行測試是否需換頁
            addEmptyRow(table, 3);

            if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                // 換了頁就不印分隔線了
                deleteRow(table, 3);
                document.add(table);
                table = addHeader(userData ,caseData, false);
                }else {
                deleteRow(table, 3);
                }
            
            //其他簽函日期：---
            addColumn(table, 30, 1, "其他簽函日期：", fontCh12, 0, LEFT);
            StringBuffer detail5ProDateString = new StringBuffer("");
            
            if(detail5.size() > 0){
            for(int i = 0 ; i < detail5.size() ; i++){
            	if(i == detail5.size() - 1){
            		detail5ProDateString.append(detail5.get(i).getProDate());
            	}else{
            		detail5ProDateString.append(detail5.get(i).getProDate()+" ,");
            	}
            }
            addColumn(table, 30, 1, detail5ProDateString.toString() , fontCh12, 0, LEFT); //其他簽函日期：
            }else{
            addColumn(table, 30, 1, " ", fontCh12, 0, LEFT);	//其他簽函日期：
            }
            
            
            //---
            
            //行政救濟日期：---
            addColumn(table, 15, 1, "行政救濟日期："+detail6.getProDateStr(), fontCh12, 0, LEFT);//行政救濟日期
            //救濟種類：---
            if(StringUtils.isBlank(detail6.getReliefKind())){
            	detail6.setReliefKind("");
            }
            addColumn(table, 15, 1, "救濟種類："+detail6.getReliefKind(), fontCh12, 0, LEFT);//救濟種類
            //行政救濟辦理情形：---
            if(StringUtils.isBlank(detail6.getReliefStat())){
            	detail6.setReliefStat("");
            }
            addColumn(table, 15, 1, "行政救濟辦理情形："+detail6.getReliefStat(), fontCh12, 0, LEFT);//行政救濟辦理情形
            addColumn(table, 15, 1, " ", fontCh12, 0, LEFT);
            
            //救濟事由：---
            if(StringUtils.isBlank(detail6.getReliefCause())){
            	detail6.setReliefCause("");
            }
            addColumn(table, 60, 1, "救濟事由："+detail6.getReliefCause(), fontCh12, 0, LEFT);//救濟事由
 
            addLine(table);
            
            //---在塞分隔線前, 先隨便塞空白行測試是否需換頁
            addEmptyRow(table, 3);

            if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                // 換了頁就不印分隔線了
                deleteRow(table, 3);
                document.add(table);
                table = addHeader(userData ,caseData, false);
                }else {
                deleteRow(table, 3);
                }
            //---
            // ]  
           
            
            if(!StringUtils.isBlank(qryForm.getStartYm()) && !StringUtils.isBlank(qryForm.getEndYm())){
            
            if(qryForm.getQryCond().equals("ISSUYM")){
            	addColumn(table, 10, 1, "核定年月起迄："+qryForm.getStartYm(), fontCh12, 0, LEFT);
            	if(!StringUtils.isBlank(qryForm.getStartYm()) || !StringUtils.isBlank(qryForm.getEndYm())){
            		addColumn(table, 2, 1, "~" , fontCh12, 0, LEFT);
            	}else{
            		addColumn(table, 2, 1, " " , fontCh12, 0, LEFT);
            	}
            	addColumn(table, 10, 1, qryForm.getEndYm() , fontCh12, 0, LEFT);
            	addColumn(table, 38, 1, " " , fontCh12, 0, LEFT);
            	
            }else if(qryForm.getQryCond().equals("PAYYM")){
            	addColumn(table, 15, 1, "給付年月起迄：", fontCh12, 0, LEFT);
            	addColumn(table, 10, 1, qryForm.getStartYm() , fontCh12, 0, LEFT);
            	if(!StringUtils.isBlank(qryForm.getStartYm()) || !StringUtils.isBlank(qryForm.getEndYm())){
            		addColumn(table, 5, 1, "~" , fontCh12, 0, LEFT);
            	}else{
            		addColumn(table, 5, 1, " " , fontCh12, 0, LEFT);
            	}
            	addColumn(table, 10, 1, qryForm.getEndYm() , fontCh12, 0, LEFT);
            	addColumn(table, 20, 1, " " , fontCh12, 0, LEFT);
            }
            }
            
            if(issuPayDataList.size() > 0){
            	for(PaymentQueryIssuPayDataCase Issu : issuPayDataList){ //第一層
            		
            		//---在塞分隔線前, 先隨便塞空白行測試是否需換頁
                    addEmptyRow(table, 3);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        // 換了頁就不印分隔線了
                        deleteRow(table, 3);
                        document.add(table);
                        table = addHeader(userData ,caseData, false);
                        }else {
                        deleteRow(table, 3);
                        }
                    //---
                    
            		for(int i = 0; i<Issu.getIssuPayDataList().size();i++){ //第二層
            			
            			//---在塞分隔線前, 先隨便塞空白行測試是否需換頁
                        addEmptyRow(table, 3);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            // 換了頁就不印分隔線了
                            deleteRow(table, 3);
                            document.add(table);
                            table = addHeader(userData ,caseData, false);
                            }else {
                            deleteRow(table, 3);
                            }
                        //---
                        
            		PaymentQueryIssuPayDataCase  subIssu  = Issu.getIssuPayDataList().get(i);
            		
            		if(Issu.getIssuPayDataList().get(0).equals(subIssu)){
            		//第一欄 給付年月or核定年月
            		if(qryForm.getQryCond().equals("ISSUYM")){
            			addColumn(table, 15, 1, "核定年月："+subIssu.getIssuYmStr() , fontCh12, 0, LEFT);
            		}else if(qryForm.getQryCond().equals("PAYYM")){
            			addColumn(table, 15, 1, "給付年月："+subIssu.getPayYmStr() , fontCh12, 0, LEFT);
            		}
            		}else{
            			addColumn(table, 15, 1, " " , fontCh12, 0, LEFT);
            		}
            		//第二欄 給付年月or核定年月
            		if(qryForm.getQryCond().equals("ISSUYM")){
            			addColumn(table, 15, 1, "給付年月："+subIssu.getPayYmStr() , fontCh12, 0, LEFT);
            		}else if(qryForm.getQryCond().equals("PAYYM")){
            			addColumn(table, 15, 1, "核定年月："+subIssu.getIssuYmStr() , fontCh12, 0, LEFT);
            		}
            		//核付日期
            	    if(Issu.getIssuPayDataList().get(0).equals(subIssu)){
            			addColumn(table, 15, 1, "核付日期："+subIssu.getAplpayDateStr() , fontCh12, 0, LEFT);
            		}else{
            			addColumn(table, 15, 1, " " , fontCh12, 0, LEFT);
            		}
            	    //止付日期
                    if(Issu.getIssuPayDataList().get(0).equals(subIssu)){
            			addColumn(table, 15, 1, "止付日期："+subIssu.getStexpndDateStr() , fontCh12, 0, LEFT);
            		}else{
            			addColumn(table, 15, 1, " " , fontCh12, 0, LEFT);
            		}
                    //資料項目
                    if(Issu.getIssuPayDataList().get(0).equals(subIssu)){
                    addColumn(table, 5, 1, "受款人序" , fontCh12, 0, CENTER);
                    addColumn(table, 6, 1, "受款人姓名" , fontCh12, 0, CENTER);
                    addColumn(table, 6, 1, "關係" , fontCh12, 0, CENTER);
                    addColumn(table, 6, 1, "核定金額" , fontCh12, 0, CENTER);
                    addColumn(table, 6, 1, "紓困金額" , fontCh12, 0, CENTER);
                    addColumn(table, 6, 1, "扣減總金額" , fontCh12, 0, CENTER);
                    addColumn(table, 7, 1, "補發/收回金額" , fontCh12, 0, CENTER);
                    addColumn(table, 7, 1, "存儲/實付金額" , fontCh12, 0, CENTER);
                    addColumn(table, 5, 1, "帳務日期" , fontCh12, 0, CENTER);
                    addColumn(table, 6, 1, "核定結果" , fontCh12, 0, CENTER);
                    }else{
                    	addColumn(table, 60, 1, " " , fontCh12, 0, LEFT); //資料項目 非第一筆不放標頭
                    }
                    //受款人資料
                    for(int x = 0; x < subIssu.getIssuPayExtDataList().size();x++){
                    	
                    	//---在塞分隔線前, 先隨便塞空白行測試是否需換頁
                        addEmptyRow(table, 3);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            // 換了頁就不印分隔線了
                            deleteRow(table, 3);
                            document.add(table);
                            table = addHeader(userData ,caseData, false);
                            }else {
                            deleteRow(table, 3);
                            }
                        //---
                        
                        PaymentQueryIssuPayExtDataCase  issuExt = subIssu.getIssuPayExtDataList().get(x);
                        String intValue = Integer.toString(x+1);
                    	addColumn(table, 5, 1, intValue , fontCh12, 0, CENTER); //受款人序
                        addColumn(table, 6, 1, issuExt.getBenName() , fontCh12, 0, LEFT); //受款人姓名
                        addColumn(table, 6, 1, issuExt.getBenEvtRelName() , fontCh12, 0, LEFT); //關係
                        if(issuExt.getIssueAmt() == null){
                        	issuExt.setIssueAmt(new BigDecimal(0));
                        }
                        addColumn(table, 6, 1, DecimalFormat.getNumberInstance().format(issuExt.getIssueAmt()).toString() , fontCh12, 0, RIGHT); //核定金額
                        if(issuExt.getOffsetAmt() == null){
                        	issuExt.setOffsetAmt(new BigDecimal(0));
                        } 
                        addColumn(table, 6, 1, DecimalFormat.getNumberInstance().format(issuExt.getOffsetAmt()).toString() , fontCh12, 0, RIGHT); //紓困金額
                        if(issuExt.getOtherAmt() == null){
                        	issuExt.setOtherAmt(new BigDecimal(0));
                        } 
                        addColumn(table, 6, 1, DecimalFormat.getNumberInstance().format(issuExt.getOtherAmt()).toString() , fontCh12, 0, RIGHT); //扣減總金額
                        if(issuExt.getRecAmt() == null){
                        	issuExt.setRecAmt(new BigDecimal(0));
                        } 
                        addColumn(table, 7, 1, DecimalFormat.getNumberInstance().format(issuExt.getRecAmt()).toString() , fontCh12, 0, RIGHT); //補發/收回金額
                        if(issuExt.getAplpayAmt() == null){
                        	issuExt.setAplpayAmt(new BigDecimal(0));
                        } 
                        addColumn(table, 7, 1, DecimalFormat.getNumberInstance().format(issuExt.getAplpayAmt().add(issuExt.getInheritorAmt())).toString() , fontCh12, 0, RIGHT); //實付金額
                        addColumn(table, 5, 1, issuExt.getRemitDateStr() , fontCh12, 0, RIGHT); //帳務日期
                        addColumn(table, 6, 1, issuExt.getManchkMkStr() , fontCh12, 0, CENTER); //核定結果
                        }
                        }
            	}
            }else{
            	addColumn(table, 60, 1, "無資料", fontCh12, 0, CENTER);
            }
            
            //此處印完要強制換頁
            //---在塞分隔線前, 先隨便塞空白行測試是否需換頁
            addEmptyRow(table,40);

            if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                // 換了頁就不印分隔線了
                deleteRow(table, 40);
                document.add(table);
                table = addHeader(userData ,caseData, false);
                }else {
                deleteRow(table, 40);
                }
            //---
            
            //事故者/受款人 資料
    		addColumn(table, 60, 1, "【事故者資料】", fontCh12, 0, LEFT);
    		
    		addColumn(table, 60, 1, "事故者姓名："+caseData.getEvtName(), fontCh12, 0, LEFT);
    		addColumn(table, 15, 1, "事故者身分證號："+caseData.getEvtIdnNo(), fontCh12, 0, LEFT);  
    		addColumn(table, 15, 1, "事故者出生日期："+caseData.getEvtBrDateStr(), fontCh12, 0, LEFT);
    		String evtSex = "";
    		if(caseData.getEvtSex().equals("1")){
    			evtSex = ConstantKey.BAAPPBASE_SEX_STR_1;
    		}else if(caseData.getEvtSex().equals("2")){
    			evtSex = ConstantKey.BAAPPBASE_SEX_STR_2;
    		}
    		addColumn(table, 15, 1, "性別："+evtSex, fontCh12, 0, LEFT);
    		addColumn(table, 15, 1, "關係："+caseData.getBenEvtRelStr(), fontCh12, 0, LEFT);
    		
    		
    		if(caseData.getEvtNationTpe().equals("1")){
    		    addColumn(table, 15, 1, "國籍別： 本國", fontCh12, 0, LEFT);
    		}else if(caseData.getEvtNationTpe().equals("2")){
    			addColumn(table, 15, 1, "國籍別： 外籍", fontCh12, 0, LEFT);
    		}
    		addColumn(table, 15, 1, "國籍："+caseData.getEvtNationCode()+" "+caseData.getEvtNationName(), fontCh12, 0, LEFT);
    		addColumn(table, 30, 1, " " , fontCh12, 0, LEFT);
            
    		
            if(benDataList.size() > 0){
            	for(PaymentQueryBenDataCase benDetailData : benDataList){
            		if(!benDetailData.getSeqNo().equals("0000") ){
            			addColumn(table, 60, 1, "【受款人資料】", fontCh12, 0, LEFT);
            			
            			// 在塞分隔線前, 先隨便塞空白行測試是否需換頁
                        addEmptyRow(table, 3);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            // 換了頁就不印分隔線了
                            deleteRow(table, 3);
                            document.add(table);
                            table = addHeader(userData ,caseData, false);
                        }
                        else {
                            deleteRow(table, 3);
                        }
                        
            			//受款人序
                		String indexValueStr = "";
                		for(int i = 0 ; i < benDataList.size() ; i++){
                			if(benDataList.get(i).equals(benDetailData)){
                			indexValueStr = Integer.toString(i+1);
                			}
                		}
                		addColumn(table, 15, 1, "受款人序： "+indexValueStr, fontCh12, 0, LEFT);
                		addColumn(table, 15, 1, "受款人姓名："+benDetailData.getBenName(), fontCh12, 0, LEFT);
                		addColumn(table, 30, 1, " ", fontCh12, 0, LEFT);
                		
                		addColumn(table, 15, 1, "受款人身分證號："+benDetailData.getBenIdnNo(), fontCh12, 0, LEFT);
                		if(!benDetailData.getBenEvtRel().equals("Z") && !benDetailData.getBenEvtRel().equals("F") && !benDetailData.getBenEvtRel().equals("N")){
                			addColumn(table, 15, 1, "受款人出生日期："+benDetailData.getBenBrDateStr(), fontCh12, 0, LEFT);
                		}else{
                			addColumn(table, 15, 1, " ", fontCh12, 0, LEFT);
                		}
                		if(!benDetailData.getBenEvtRel().equals("Z") && !benDetailData.getBenEvtRel().equals("F") && !benDetailData.getBenEvtRel().equals("N")){
                			String BenSex = "";
                    		if(benDetailData.getBenSex().equals("1")){
                    			BenSex = ConstantKey.BAAPPBASE_SEX_STR_1;
                    		}else if(benDetailData.getBenSex().equals("2")){
                    			BenSex = ConstantKey.BAAPPBASE_SEX_STR_2;
                    		}
                    		addColumn(table, 15, 1, "性別："+BenSex, fontCh12, 0, LEFT);
                		}else{
                			addColumn(table, 15, 1, " ", fontCh12, 0, LEFT);
                		}
                        addColumn(table, 15, 1, "關係："+benDetailData.getBenEvtRelStr(), fontCh12, 0, LEFT);
                        
                        if(benDetailData.getBenEvtRel().equals("Z")){
                        	if(benDetailData.getCutAmt() == null){
                        		benDetailData.setCutAmt(new BigDecimal(0));
                        	}
                        	addColumn(table, 15, 1, "實際補償金額："+benDetailData.getCutAmt(), fontCh12, 0, LEFT);
                        }
                        
                        addColumn(table, 15, 1, "申請日期："+benDetailData.getAppDateStr(), fontCh12, 0, LEFT);
                        if(benDetailData.getBenNationTyp().equals("1")){
                		    addColumn(table, 15, 1, "國籍別： 本國", fontCh12, 0, LEFT);
                		}else if(benDetailData.getBenNationTyp().equals("2")){
                			addColumn(table, 15, 1, "國籍別： 外籍", fontCh12, 0, LEFT);
                		}
                        addColumn(table, 15, 1, "國籍："+benDetailData.getBenNationCode()+" "+benDetailData.getBenNationName(), fontCh12, 0, LEFT);
                        addColumn(table, 15, 1, "身分查核年月："+benDetailData.getIdnChkYmStr(), fontCh12, 0, LEFT);
                        
                        // 在塞分隔線前, 先隨便塞空白行測試是否需換頁
                        addEmptyRow(table, 3);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            // 換了頁就不印分隔線了
                            deleteRow(table, 3);
                            document.add(table);
                            table = addHeader(userData ,caseData, false);
                        }
                        else {
                            deleteRow(table, 3);
                        }
                        
                        if(StringUtils.isBlank(benDetailData.getAbleApsYmStr())){
                        	String ableApsYmStr = "";
                        	addColumn(table, 15, 1, "得請領起月："+ableApsYmStr, fontCh12, 0, LEFT);
                        }else{
                            addColumn(table, 15, 1, "得請領起月："+benDetailData.getAbleApsYmStr(), fontCh12, 0, LEFT);
                        }
                        if(StringUtils.isBlank(benDetailData.getAbanApsYmStr())){
                        	String abanApsYmStr = "";
                        	addColumn(table, 15, 1, "放棄請領起始年月："+abanApsYmStr, fontCh12, 0, LEFT);
                        }else{
                        	addColumn(table, 15, 1, "放棄請領起始年月："+benDetailData.getAbanApsYmStr(), fontCh12, 0, LEFT);
                        }
                        addColumn(table, 30, 1, "死亡日期："+benDetailData.getBenDieDateStr(), fontCh12, 0, LEFT);
                        
                        if(StringUtils.isBlank(benDetailData.getMarryDateStr())){
                        	addColumn(table, 15, 1, "結婚日期：", fontCh12, 0, LEFT);
                        }else{
                        	addColumn(table, 15, 1, "結婚日期："+benDetailData.getMarryDateStr(), fontCh12, 0, LEFT);
                        }
                        if(StringUtils.isBlank(benDetailData.getDigamyDateStr())){
                        	String digamyDateStr = "";
                        	addColumn(table, 15, 1, "再婚日期："+digamyDateStr, fontCh12, 0, LEFT);
                        }else{
                        	addColumn(table, 15, 1, "再婚日期："+benDetailData.getDigamyDateStr(), fontCh12, 0, LEFT);
                        }
                        
                        
                        if(benDetailData.getDataCount() == null){
                        	benDetailData.setDataCount(new BigDecimal(0));
                        }
                        if(StringUtils.isBlank(benDetailData.getCompelSdate()) || StringUtils.isBlank(benDetailData.getCompelEdateStr())){
                        	addColumn(table, 30, 1, "強迫不合格年月：", fontCh12, 0, LEFT);
                        }else{
                        	addColumn(table, 30, 1, "強迫不合格年月："+benDetailData.getCompelSdate()+"~"+benDetailData.getCompelEdateStr()+" ("+benDetailData.getDataCount()+")", fontCh12, 0, LEFT);
                        }
                        
                        addColumn(table, 15, 1, "被保險人扶養："+benDetailData.getRaiseEvtMkStr(), fontCh12, 0, LEFT);
                        addColumn(table, 15, 1, "配偶扶養："+benDetailData.getRaiseChildMkStr(), fontCh12, 0, LEFT);
                        if(StringUtils.isBlank(benDetailData.getAdoPtDateStr())){
                        	String adoPtDateStr = "";
                        	addColumn(table, 15, 1, "收養日期："+adoPtDateStr, fontCh12, 0, LEFT);
                        }else{
                        	addColumn(table, 15, 1, "收養日期："+benDetailData.getAdoPtDateStr(), fontCh12, 0, LEFT);
                        }
                        if(StringUtils.isBlank(benDetailData.getRelatChgDateStr())){
                        	addColumn(table, 15, 1, "親屬關係變動日期：", fontCh12, 0, LEFT);
                        }else{
                        	addColumn(table, 15, 1, "親屬關係變動日期："+benDetailData.getRelatChgDateStr(), fontCh12, 0, LEFT);
                        }
                        
                        // 在塞分隔線前, 先隨便塞空白行測試是否需換頁
                        addEmptyRow(table, 3);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            // 換了頁就不印分隔線了
                            deleteRow(table, 3);
                            document.add(table);
                            table = addHeader(userData ,caseData, false);
                        }
                        else {
                            deleteRow(table, 3);
                        }
                        
                        
                        if(!StringUtils.isBlank(benDetailData.getStudSdate()) || !StringUtils.isBlank(benDetailData.getStudEdate())){
                        	addColumn(table, 30, 1, "在學起迄年月："+benDetailData.getStudSdateStr()+"~"+benDetailData.getStudEdateStr()+" ("+DecimalFormat.getNumberInstance().format(benDetailData.getStudDataCount())+")", fontCh12, 0, LEFT);
                        }else{
                        addColumn(table, 30, 1, "在學起迄年月：", fontCh12, 0, LEFT);
                        }
                        if(StringUtils.isBlank(benDetailData.getMonIncomeMk())){
                        	benDetailData.setMonIncomeMk("");
                        }
                        addColumn(table, 13, 1, "每月工作收入註記／收入："+benDetailData.getMonIncomeMkStr(), fontCh12, 0, LEFT);
                        if(benDetailData.getMonIncomeMk().equals("Y")){
                        	if(benDetailData.getMonIncome() == null){
                        		benDetailData.setMonIncome(new BigDecimal(0));
                            }
                        	addColumn(table, 17, 1, "／"+DecimalFormat.getNumberInstance().format(benDetailData.getMonIncome()), fontCh12, 0, LEFT);
                        }else{
                        	if(benDetailData.getMonIncome() == null){
                        		benDetailData.setMonIncome(new BigDecimal(0));
                            }
                        	addColumn(table, 17, 1, " ", fontCh12, 0, LEFT);
                        }
                        if(StringUtils.isBlank(benDetailData.getInterDictMk())){
                        	benDetailData.setInterDictMk("");
                        }
                        if(benDetailData.getInterDictMk().equals("Y")){
                        	addColumn(table, 20, 1, "受禁治產(監護)宣告："+benDetailData.getInterDictMk()+"(宣告起迄期間："+benDetailData.getInterDictDateStr(), fontCh12, 0, LEFT);
                        	if(!StringUtils.isBlank(benDetailData.getInterDictDateStr()) || !StringUtils.isBlank(benDetailData.getRepealInterdictDateStr())){
                        		addColumn(table, 3, 1, "~", fontCh12, 0, LEFT);
                        	}else{
                        	    addColumn(table, 3, 1, " ", fontCh12, 0, LEFT);
                        }
                        addColumn(table, 7, 1, benDetailData.getRepealInterdictDateStr()+")", fontCh12, 0, LEFT);
                        }else{
                        	addColumn(table, 30, 1, "受禁治產(監護)宣告：", fontCh12, 0, LEFT);
                        }
                        addColumn(table, 30, 1, "領有重度以上身心障礙手冊或證明："+benDetailData.getHandIcapMkStr(), fontCh12, 0, LEFT);
                        
                        // 在塞分隔線前, 先隨便塞空白行測試是否需換頁
                        addEmptyRow(table, 3);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            // 換了頁就不印分隔線了
                            deleteRow(table, 3);
                            document.add(table);
                            table = addHeader(userData ,caseData, false);
                        }
                        else {
                            deleteRow(table, 3);
                        }
                        
                        if(StringUtils.isBlank(benDetailData.getBenMissingSdateStr())){
                            addColumn(table, 10, 1, "遺屬失蹤起迄期間：", fontCh12, 0, LEFT);
                        }else{
                        	addColumn(table, 10, 1, "遺屬失蹤起迄期間："+benDetailData.getBenMissingSdateStr(), fontCh12, 0, LEFT);
                        }
                        if(StringUtils.isBlank(benDetailData.getBenMissingSdateStr()) || StringUtils.isBlank(benDetailData.getBenMissingEdateStr())){
                        	addColumn(table, 5, 1, " ", fontCh12, 0, LEFT);
                        }else{
                        	addColumn(table, 5, 1, "~", fontCh12, 0, LEFT);
                        }
                        addColumn(table, 15, 1, benDetailData.getBenMissingEdateStr(), fontCh12, 0, LEFT);
                        
                        
                        if(StringUtils.isBlank(benDetailData.getPrisonSdateStr())){
                            addColumn(table, 10, 1, "監管起迄期間：", fontCh12, 0, LEFT);
                        }else{
                        	addColumn(table, 10, 1, "監管起迄期間："+benDetailData.getPrisonSdateStr(), fontCh12, 0, LEFT);
                        }
                        if(StringUtils.isBlank(benDetailData.getPrisonSdateStr()) || StringUtils.isBlank(benDetailData.getPrisonEdateStr())){
                        	addColumn(table, 5, 1, " ", fontCh12, 0, LEFT);
                        }else{
                        	addColumn(table, 5, 1, "~", fontCh12, 0, LEFT);
                        }
                        addColumn(table, 15, 1, benDetailData.getPrisonEdateStr(), fontCh12, 0, LEFT);
                        
                        addColumn(table, 15, 1,"電話1："+benDetailData.getTel1(), fontCh12, 0, LEFT);
                        addColumn(table, 15, 1,"電話2："+benDetailData.getTel2(), fontCh12, 0, LEFT);
                        if(benDetailData.getCommTyp().equals("1")){
                            addColumn(table, 30, 1,"地址： 同戶籍地 -"+benDetailData.getCommZip()+" "+benDetailData.getCommAddr(), fontCh12, 0, LEFT);
                        }else if(benDetailData.getCommTyp().equals("2")){
                        	addColumn(table, 30, 1,"地址： 現住址 -"+benDetailData.getCommZip()+" "+benDetailData.getCommAddr(), fontCh12, 0, LEFT);
                        }
                        
                        // 在塞分隔線前, 先隨便塞空白行測試是否需換頁
                        addEmptyRow(table, 3);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                           // 換了頁就不印分隔線了
                           deleteRow(table, 3);
                           document.add(table);
                           table = addHeader(userData ,caseData, false);
                        }
                       else {
                           deleteRow(table, 3);
                       }
                        
                        if(benDetailData.getAccRel().equals("1") || benDetailData.getAccRel().equals("2")){
                		    addColumn(table, 30, 1, "給付方式："+" 本人領取 - "+benDetailData.getPayTypStr(), fontCh12, 0, LEFT);
                		    if(benDetailData.getSpecialAcc().equals("Y")){
                		    	if(benDetailData.getPayTyp().equals("1") && !benDetailData.getPayBankId().equals("700")){
                		    	    addColumn(table, 30, 1, "帳號："+benDetailData.getPayBankId()+"-0000-"+benDetailData.getPayEeacc()+" "+"("+benDetailData.getBankName()+")"+"專戶", fontCh12, 0, LEFT);
                		    	}else{
                		    		addColumn(table, 30, 1, "帳號："+benDetailData.getPayBankId()+"-"+benDetailData.getBranchId()+"-"+benDetailData.getPayEeacc()+" "+"("+benDetailData.getBankName()+")"+"專戶", fontCh12, 0, LEFT);
                		    	}
                		    }else{
                		    	if(benDetailData.getPayTyp().equals("1") && !benDetailData.getPayBankId().equals("700")){
                		    	    addColumn(table, 30, 1, "帳號："+benDetailData.getPayBankId()+"-0000-"+benDetailData.getPayEeacc()+" "+"("+benDetailData.getBankName()+")", fontCh12, 0, LEFT);
                		    	}else{
                		    		addColumn(table, 30, 1, "帳號："+benDetailData.getPayBankId()+"-"+benDetailData.getBranchId()+"-"+benDetailData.getPayEeacc()+" "+"("+benDetailData.getBankName()+")", fontCh12, 0, LEFT);
                		    	}
                		    }
                		}else if (benDetailData.getAccRel().equals("3")){
                			addColumn(table, 30, 1, "給付方式："+" 具名領取 - "+benDetailData.getAccName(), fontCh12, 0, LEFT);
                			addColumn(table, 30, 1, "帳號：", fontCh8, 0, LEFT);
                		}
                        
                        // 在塞分隔線前, 先隨便塞空白行測試是否需換頁
                        addEmptyRow(table, 3);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                           // 換了頁就不印分隔線了
                           deleteRow(table, 3);
                           document.add(table);
                           table = addHeader(userData ,caseData, false);
                        }
                       else {
                           deleteRow(table, 3);
                       }
                        
                        if(benDetailData.getAccRel().equals("1") || benDetailData.getAccRel().equals("2")){
                		    addColumn(table, 60, 1, "戶名："+benDetailData.getAccName(), fontCh12, 0, LEFT);
                		}else if (benDetailData.getAccRel().equals("3")){
                			addColumn(table, 60, 1, "戶名：", fontCh12, 0, LEFT);
                		}
                       
                        if(!benDetailData.getBenEvtRel().equals("Z") && !benDetailData.getBenEvtRel().equals("F") && !benDetailData.getBenEvtRel().equals("N")){
                        	addColumn(table, 15, 1, "婚姻狀況："+benDetailData.getBenMarrMkStr(), fontCh12, 0, LEFT);
                        	addColumn(table, 15, 1, "法定代理人姓名："+benDetailData.getGrdName(), fontCh12, 0, LEFT);
                        	addColumn(table, 30, 1, " ", fontCh12, 0, LEFT);
                        	
                        	if(StringUtils.isBlank(benDetailData.getSavingMk())){
                                addColumn(table, 15, 1, "計息存儲：", fontCh12, 0, LEFT);
                            }else{
                            	addColumn(table, 15, 1, "計息存儲："+benDetailData.getSavingMk(), fontCh12, 0, LEFT);
                            }
                        	addColumn(table, 15, 1, "法定代理人身分證號："+benDetailData.getGrdIdnNo(), fontCh12, 0, LEFT);
                        	addColumn(table, 30, 1, "法定代理人出生日期："+benDetailData.getGrdBrDateStr(), fontCh12, 0, LEFT);
                        }
                        
                        // 在塞分隔線前, 先隨便塞空白行測試是否需換頁
                        addEmptyRow(table, 3);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                           // 換了頁就不印分隔線了
                           deleteRow(table, 3);
                           document.add(table);
                           table = addHeader(userData ,caseData, false);
                        }
                        else {
                           deleteRow(table, 3);
                        }
                        
                        if(StringUtils.isBlank(benDetailData.getAssignName())){
                            addColumn(table, 30, 1, "代辦人姓名：", fontCh12, 0, LEFT);
                        }else{
                        	addColumn(table, 30, 1, "代辦人姓名："+benDetailData.getAssignName(), fontCh12, 0, LEFT);
                        }
                        if(StringUtils.isBlank(benDetailData.getAssignIdnNo())){
                            addColumn(table, 15, 1, "代辦人身分證號：", fontCh12, 0, LEFT);
                        }else{
                        	addColumn(table, 15, 1, "代辦人身分證號："+benDetailData.getAssignIdnNo(), fontCh12, 0, LEFT);
                        }
                        if(StringUtils.isBlank(benDetailData.getAssignBrDateStr())){
                            addColumn(table, 15, 1, "代辦人出生日期：", fontCh12, 0, LEFT);
                        }else{
                        	addColumn(table, 15, 1, "代辦人出生日期："+benDetailData.getAssignBrDateStr(), fontCh12, 0, LEFT);
                        }
                        
                        // 在塞分隔線前, 先隨便塞空白行測試是否需換頁
                        addEmptyRow(table, 3);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                           // 換了頁就不印分隔線了
                           deleteRow(table, 3);
                           document.add(table);
                           table = addHeader(userData ,caseData, false);
                        }
                       else {
                           deleteRow(table, 3);
                       }
                    	
                    	addColumn(table, 15, 1, "結案日期："+benDetailData.getCloseDateStr(), fontCh12, 0, LEFT);
                    	addColumn(table, 15, 1, "結案原因："+benDetailData.getCloseCause(), fontCh12, 0, LEFT);
                    	addColumn(table, 30, 1, " ", fontCh12, 0, LEFT);
                    
                    	//擇領起月
                    	String choiceYmStr = "";
                    	if(StringUtils.isBlank(benDetailData.getChoiceYmStr())){
                    		choiceYmStr = "";
                    	}else{
                    		choiceYmStr = benDetailData.getChoiceYmStr();
                    	}
                    	addColumn(table, 15, 1, "擇領起月："+choiceYmStr , fontCh12, 0, LEFT);
                    	addColumn(table, 15, 1, "擇領老年年金受理編號：", fontCh12, 0, LEFT);
                    	addColumn(table, 30, 1, " ", fontCh12, 0, LEFT);
                    	
                    	// 在塞分隔線前, 先隨便塞空白行測試是否需換頁
                        addEmptyRow(table, 3);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                           // 換了頁就不印分隔線了
                           deleteRow(table, 3);
                           document.add(table);
                           table = addHeader(userData ,caseData, false);
                        }
                        else {
                           deleteRow(table, 3);
                        }
                    	
                    	// 眷屬符合註記---
                        addColumn(table, 15, 1, "遺屬符合註記：", fontCh12, 0, LEFT);
                        addColumn(table, 45, 1, " ", fontCh12, 0, LEFT);

                        	    List<PaymentQueryFamilyDataCase> matchChkList2 = benDetailData.getMatchChkList();
                        		for(PaymentQueryFamilyDataCase chkFileData : matchChkList2){
                        			addColumn(table, 10, 1, chkFileData.getIssuPayYm() , fontCh12, 0, LEFT);
                        			 List<PaymentQueryChkFileDataCase> chkFile = chkFileData.getChkFileDataList();
                        			 StringBuffer chkfileString = new StringBuffer("");
                                 	 for(int x = 0 ; x < chkFile.size() ; x++){
                                 	    if(x == chkFile.size() - 1){
                                            chkfileString.append(chkFile.get(x).getChkCodePost());
                                 	    }else{
                                 		    chkfileString.append(chkFile.get(x).getChkCodePost()+" ,");
                                 	 }
                                 	 }
                                 	 addColumn(table, 50, 1, chkfileString.toString() , fontCh12, 0, LEFT); //眷屬符合註記
                                     // 在塞分隔線前, 先隨便塞空白行測試是否需換頁
                                     addEmptyRow(table, 3);

                                     if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                                        // 換了頁就不印分隔線了
                                        deleteRow(table, 3);
                                        document.add(table);
                                        table = addHeader(userData ,caseData, false);
                                     }
                                    else {
                                        deleteRow(table, 3);
                                    }
                        			 }

                        // 遺屬編審註記---
                        addColumn(table, 15, 1, "遺屬編審註記：", fontCh12, 0, LEFT);
                        addColumn(table, 45, 1, " ", fontCh12, 0, LEFT);
                        

                        List<PaymentQueryFamilyDataCase> benChkList2 = benDetailData.getBenChkList();
                        		for(PaymentQueryFamilyDataCase chkFileData : benChkList2){
                        			addColumn(table, 10, 1, chkFileData.getIssuPayYm() , fontCh12, 0, LEFT);
                        			 List<PaymentQueryChkFileDataCase> chkFile = chkFileData.getChkFileDataList();
                        			 StringBuffer chkfileString = new StringBuffer("");
                                 	 for(int x = 0 ; x < chkFile.size() ; x++){
                                 	    if(x == chkFile.size() - 1){
                                            chkfileString.append(chkFile.get(x).getChkCodePost());
                                 	    }else{
                                 		    chkfileString.append(chkFile.get(x).getChkCodePost()+" ,");
                                 	 }
                                 	 }
                                 	 addColumn(table, 50, 1, chkfileString.toString() , fontCh12, 0, LEFT); //眷屬符合註記
                                     // 在塞分隔線前, 先隨便塞空白行測試是否需換頁
                                     addEmptyRow(table, 3);

                                     if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                                        // 換了頁就不印分隔線了
                                        deleteRow(table, 3);
                                        document.add(table);
                                        table = addHeader(userData ,caseData, false);
                                     }
                                    else {
                                        deleteRow(table, 3);
                                    }
                        			}
                        		
                        	
                        
                    	
            		}
            	}
            }else{
            	addColumn(table, 60, 1, "無資料", fontCh12, 0, CENTER);
            }
            
            
            
            
            document.add(table);
            
            }
//            document.close();
//            }

        finally {
        	closePageText(String.valueOf(writer.getPageNumber()) + " 頁)", 8);
            document.close();
        }

        return bao;
    }
}
