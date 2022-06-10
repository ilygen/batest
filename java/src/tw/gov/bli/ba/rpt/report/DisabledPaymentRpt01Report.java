package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Badapr;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.query.cases.PaymentQueryBenDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryChkFileDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryDetailDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryDisabledDataCase;
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
public class DisabledPaymentRpt01Report extends ReportBase {

    private int nPage; // 每筆資料的頁數

    private String printDate = ""; // 印表日期

    private String line = "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";

    public DisabledPaymentRpt01Report() throws Exception {
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

    public ByteArrayOutputStream doReport(UserBean userData, PaymentQueryDetailDataCase caseData ,List<PaymentQueryIssuPayDataCase>  issuPayDataList,List<Badapr> origIssuPayDataLis,List<PaymentQueryDetailDataCase> chkFileDataList,List<PaymentQueryLetterTypeMkCase> detail1,List<PaymentQueryLetterTypeMkCase> detail2,List<PaymentQueryLetterTypeMkCase> detail3,List<PaymentQueryLetterTypeMkCase> detail4,List<PaymentQueryLetterTypeMkCase> detail5,PaymentQueryLetterTypeMkCase detail6,PaymentQueryForm qryForm,List<PaymentQueryDetailDataCase> benChkList,List<PaymentQueryDetailDataCase> matchChkList,PaymentQueryOccAccDataCase occAccData,PaymentQueryDisabledDataCase disabledData,List<PaymentQueryBenDataCase> benDataList) throws Exception {
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
                addColumn(table, 20, 1, "事故者姓名："+caseData.getEvtName() + "(羅馬拼音：" + caseData.getRmp_Name() + ")" , fontCh12, 0, LEFT);
            } else {
                addColumn(table, 20, 1, "事故者姓名："+caseData.getEvtName() , fontCh12, 0, LEFT);
            }

            addColumn(table, 20, 1, "", fontCh12, 0, LEFT);
            addColumn(table, 20, 1, "年金請領資格："+disabledData.getDisQualMkStr() , fontCh12, 0, LEFT);
            
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
            	addColumn(table, 15, 1, "申請項目："+caseData.getApItemStr() , fontCh12, 0, LEFT);
            }else{
            	addColumn(table, 15, 1, "申請項目："+caseData.getApItem()+"-"+caseData.getApItemStr() , fontCh12, 0, LEFT);
            }
            
            addColumn(table, 15, 1, "核定格式："+caseData.getNotifyForm(), fontCh12, 0, LEFT);
            addColumn(table, 15, 1, "承辦人員："+caseData.getPromoteUser(), fontCh12, 0, LEFT);
            addColumn(table, 15, 1, " ", fontCh12, 0, LEFT);
            
            addColumn(table, 15, 1, "申請單位："+caseData.getApUbno() , fontCh12, 0, LEFT);
            addColumn(table, 15, 1, "事故發生單位： "+caseData.getLsUbno() , fontCh12, 0, LEFT);
            addColumn(table, 15, 1, "國際疾病代碼： "+disabledData.getCriInJnmeStr() , fontCh12, 0, LEFT);
            addColumn(table, 15, 1, " ", fontCh12, 0, LEFT);
            
            addColumn(table, 15, 1, "失能等級："+disabledData.getCriInJclStr() , fontCh12, 0, LEFT);
            addColumn(table, 15, 1, "核定等級："+disabledData.getCriInIssul() , fontCh12, 0, LEFT);
            addColumn(table, 15, 1, "失能項目："+disabledData.getCriInJdpStr() , fontCh12, 0, LEFT);
            addColumn(table, 15, 1, " ", fontCh12, 0, LEFT);
           
            if(!StringUtils.isBlank(disabledData.getEvTyp())){
                 addColumn(table, 60, 1, "傷病分類："+disabledData.getEvTyp()+"-"+disabledData.getEvTypStr() , fontCh12, 0, LEFT);
            }else{
            	addColumn(table, 60, 1, "傷病分類："+disabledData.getEvTypStr() , fontCh12, 0, LEFT);
            }
//            addColumn(table, 15, 1, "傷病原因："+disabledData.getEvCode() , fontCh12, 0, LEFT);
//            addColumn(table, 15, 1, "受傷部位："+disabledData.getCriInPartStr() , fontCh12, 0, LEFT);
//            addColumn(table, 15, 1, "媒介物："+disabledData.getCriMedium() , fontCh12, 0, LEFT);
            
            addColumn(table, 30, 1, "醫療院所代碼："+disabledData.getHosIdSname() , fontCh12, 0, LEFT);
            addColumn(table, 15, 1, "醫師姓名："+disabledData.getDoctorNameStr() , fontCh12, 0, LEFT);
            if(caseData.getInterValMonth().equals("0") || caseData.getInterValMonth().equals("1") || caseData.getInterValMonth().equals(""))
                addColumn(table, 15, 1, "發放方式：按月發放", fontCh12, 0, LEFT); //發放方式
            else if (!caseData.getInterValMonth().equals("0") || !caseData.getInterValMonth().equals("1") || !caseData.getInterValMonth().equals(""))
            	addColumn(table, 15, 1, "發放方式：按"+caseData.getInterValMonth()+"個月發放", fontCh12, 0, LEFT); //發放方式
            
//            addColumn(table, 30, 1, "職病醫療院所代碼："+disabledData.getOcAccHosIdSname() , fontCh12, 0, LEFT);
//            addColumn(table, 15, 1, "職病醫師姓名："+disabledData.getOcAccDoctorNameStr() , fontCh12, 0, LEFT);
//            addColumn(table, 15, 1, " ", fontCh12, 0, LEFT);
            
            addColumn(table, 15, 1, "重新查核失能程度年月："+disabledData.getRehcYmStr() , fontCh12, 0, LEFT);
//            addColumn(table, 15, 1, "符合第20條之1："+disabledData.getOcaccIdentMk() , fontCh12, 0, LEFT);
//            addColumn(table, 15, 1, "先核普通："+disabledData.getPrType() , fontCh12, 0, LEFT);
            if(disabledData.getDeductDay() == null){
            	disabledData.setDeductDay(new BigDecimal(0));
            }
            addColumn(table, 45, 1, "扣除日數： "+disabledData.getDeductDay().toString()+" 天" , fontCh12, 0, LEFT);
            
            /*
            if(occAccData.getBefIssueAmt() == null){
            	occAccData.setBefIssueAmt(new BigDecimal(0));
            }
            if(occAccData.getInsAvgAmt() == null){
            	occAccData.setInsAvgAmt(new BigDecimal(0));
            }
            if(occAccData.getInsAvgAmt().compareTo(new BigDecimal(0)) == 0 && occAccData.getBefIssueAmt().compareTo(new BigDecimal(0)) == 0){
            	addColumn(table, 30, 1, "職災補償一次金／6個月平均薪資：" , fontCh12, 0, LEFT);
            }else{
            	addColumn(table, 30, 1, "職災補償一次金／6個月平均薪資："+DecimalFormat.getNumberInstance().format(occAccData.getBefIssueAmt()).toString()+" ／ "+DecimalFormat.getNumberInstance().format(occAccData.getInsAvgAmt()).toString() , fontCh12, 0, LEFT);
            }
            if(occAccData.getOcAccaddAmt() == null){
            	occAccData.setOcAccaddAmt(new BigDecimal(0));
            }
            if(occAccData.getOcAccaddAmt().compareTo(new BigDecimal(0)) == 0){
            	addColumn(table, 15, 1, "已領職災增給金額： " , fontCh12, 0, LEFT);
            }else{
            	addColumn(table, 15, 1, "已領職災增給金額： "+DecimalFormat.getNumberInstance().format(occAccData.getOcAccaddAmt()).toString() , fontCh12, 0, LEFT);
            }
            if(occAccData.getAplpayAmt() == null){
            	occAccData.setAplpayAmt(new BigDecimal(0));
            }
            if(occAccData.getAplpayAmt().compareTo(new BigDecimal(0)) == 0){
            	addColumn(table, 15, 1, "實發職災一次金： " , fontCh12, 0, LEFT);
            }else{
            	addColumn(table, 15, 1, "實發職災一次金： "+DecimalFormat.getNumberInstance().format(occAccData.getAplpayAmt()).toString() , fontCh12, 0, LEFT);
            }
            */
            
            addColumn(table, 60, 1, "來源受理編號： " + caseData.getApnoFm(), fontCh12, 0, LEFT);
            
            if(caseData.getAnnuAmt() == null){
            	caseData.setAnnuAmt(new BigDecimal(0));
            }
            addColumn(table, 15, 1, "已領失能年金金額： "+DecimalFormat.getNumberInstance().format(caseData.getAnnuAmt()).toString() , fontCh12, 0, LEFT);
            addColumn(table, 30, 1, "已領老年年金受理編號： "+caseData.getDabApNoStrDisplay() , fontCh12, 0, LEFT);
            if(caseData.getDabAnnuAmt() == null){
            	caseData.setDabAnnuAmt(new BigDecimal(0));
            }
            addColumn(table, 15, 1, "已領老年年金金額： "+DecimalFormat.getNumberInstance().format(caseData.getDabAnnuAmt()).toString() , fontCh12, 0, LEFT);
           
            
            addColumn(table, 30, 1, "首次給付年月／金額： " + caseData.getPayYmsStr() + " ~ " + caseData.getPayYmeStr() + "／" + DecimalFormat.getNumberInstance().format(caseData.getPayAmts()).toString() , fontCh12, 0, LEFT);
            
            String calAnnuAmtSrt = "";
            if(caseData.getCalAnnuAmt() == null){
            	calAnnuAmtSrt = "";
            }else{
            	calAnnuAmtSrt = DecimalFormat.getNumberInstance().format(caseData.getCalAnnuAmt()).toString();
            }
            addColumn(table, 30, 1, "累計已領年金金額： "+calAnnuAmtSrt , fontCh12, 0, LEFT);
            
            addColumn(table, 15, 1, "更正註記： "+caseData.getChgMk() , fontCh12, 0, LEFT);
            addColumn(table, 45, 1, "受理鍵入資料及修改紀錄： (鍵入／更正人員代號："+caseData.getCrtUser()+"／"+caseData.getUpdUser()+")" , fontCh12, 0, LEFT);
            
            addLine(table);  //【最新核定資料】
            
            addColumn(table, 60, 1, "【最新核定資料】", fontCh12, 0, LEFT);
            
            addColumn(table, 30, 1, "核定年月："+caseData.getIssuYmStr(), fontCh12, 0, LEFT);
            addColumn(table, 30, 1, "給付年月起迄："+caseData.getMinPayYmStr()+"~"+caseData.getMaxPayYmStr(), fontCh12, 0, LEFT);
            
            if(caseData.getBefIssueAmt() == null){
            	caseData.setBefIssueAmt(new BigDecimal(0));
            }
            addColumn(table, 30, 1, "核定總額："+DecimalFormat.getNumberInstance().format(caseData.getBefIssueAmt()).toString(), fontCh12, 0, LEFT);
            if(caseData.getAplPayAmt() == null){
            	caseData.setAplPayAmt(new BigDecimal(0));
            }
            addColumn(table, 30, 1, "實付總額："+DecimalFormat.getNumberInstance().format(caseData.getAplPayAmt()).toString(), fontCh12, 0, LEFT);
            
            if(caseData.getInsAvgAmt() == null){
            	caseData.setInsAvgAmt(new BigDecimal(0));
            }
            addColumn(table, 30, 1, "平均薪資："+DecimalFormat.getNumberInstance().format(caseData.getInsAvgAmt()).toString(), fontCh12, 0, LEFT);
            if(caseData.getOldRate() == null){
            	caseData.setOldRate(new BigDecimal(0));
            }
            String oldRateAmtSrt = "";
            if(caseData.getOldRateAmt() == null){
            	oldRateAmtSrt = "";
            }else{
            	oldRateAmtSrt = caseData.getOldRateAmt().toString();
            }
            addColumn(table, 30, 1, "加發眷屬補助："+caseData.getOldRate()+"%／"+oldRateAmtSrt, fontCh12, 0, LEFT);
           

            addColumn(table, 15, 1, "投保年資："+caseData.getNitrmY()+"年"+caseData.getNitrmM()+"月 ("+caseData.getItrmY()+"年"+caseData.getItrmD()+"日 )" , fontCh12, 0, LEFT);
            addColumn(table, 15, 1, "實付年資："+caseData.getAplPaySeniY()+"年"+caseData.getAplPaySeniM()+"月", fontCh12, 0, LEFT);
            if(caseData.getOldaAmt() == null){
            	caseData.setOldaAmt(new BigDecimal(0));
            }
            if(caseData.getOldbAmt() == null){
            	caseData.setOldbAmt(new BigDecimal(0));
            }
            addColumn(table, 30, 1, "勞保計算／給付金額："+DecimalFormat.getNumberInstance().format(caseData.getOldaAmt()).toString()+"／"+DecimalFormat.getNumberInstance().format(caseData.getOldbAmt()).toString(), fontCh12, 0, LEFT);
            
            if(caseData.getCompenAmt() == null){
            	caseData.setCompenAmt(new BigDecimal(0));
            }
            addColumn(table, 15, 1, "當月扣除失能："+DecimalFormat.getNumberInstance().format(caseData.getCompenAmt()).toString()+"／"+DecimalFormat.getNumberInstance().format(caseData.getOldbAmt()).toString() , fontCh12, 0, LEFT);
            if(caseData.getCutAmt() == null){
            	caseData.setCutAmt(new BigDecimal(0));
            }
            if(caseData.getLecomAmt() == null){
            	caseData.setLecomAmt(new BigDecimal(0));
            }
            if(caseData.getRecomAmt() == null){
            	caseData.setRecomAmt(new BigDecimal(0));
            }
            addColumn(table, 45, 1, "應扣失能／已扣失能／未扣失能："+DecimalFormat.getNumberInstance().format(caseData.getCutAmt()).toString()+"／"+DecimalFormat.getNumberInstance().format(caseData.getLecomAmt()).toString()+"／"+DecimalFormat.getNumberInstance().format(caseData.getRecomAmt()).toString() , fontCh12, 0, LEFT);
            
            if(StringUtils.isBlank(caseData.getChkMan()) || StringUtils.isBlank(caseData.getChkDateStr())){
                addColumn(table, 15, 1, "審核人員／日期："+caseData.getChkMan() , fontCh12, 0, LEFT);}
            else{
            	addColumn(table, 15, 1, "審核人員／日期："+caseData.getChkMan()+"／"+caseData.getChkDateStr() , fontCh12, 0, LEFT);}
            if(StringUtils.isBlank(caseData.getRechkMan()) || StringUtils.isBlank(caseData.getRechkDateStr())){
                addColumn(table, 15, 1, "複核人員／日期："+caseData.getRechkMan(), fontCh12, 0, LEFT);}
            else{
            	addColumn(table, 15, 1, "複核人員／日期："+caseData.getRechkMan()+"／"+caseData.getRechkDateStr(), fontCh12, 0, LEFT);}
            if(StringUtils.isBlank(caseData.getExeMan()) || StringUtils.isBlank(caseData.getExeDateStr())){
                addColumn(table, 15, 1, "決行人員／日期："+caseData.getExeMan(), fontCh12, 0, LEFT);}
            else{
            	addColumn(table, 15, 1, "決行人員／日期："+caseData.getExeMan()+"／"+caseData.getExeDateStr(), fontCh12, 0, LEFT);}
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
                addEmptyRow(table, 5);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // 換了頁就不印分隔線了
                    deleteRow(table, 5);
                    document.add(table);
                    table = addHeader(userData ,caseData, false);
                }
                else {
                    deleteRow(table, 5);
                }
            }           
            }
            //---
            addLine(table);
            // 眷屬符合註記---
            addColumn(table, 15, 1, "眷屬符合註記：", fontCh12, 0, LEFT);
            addColumn(table, 45, 1, " ", fontCh12, 0, LEFT);
            
            if(matchChkList.size() > 0){
            	
            	for(PaymentQueryDetailDataCase masterChkFile : matchChkList ){
            		addColumn(table, 60, 1, "眷屬序："+masterChkFile.getSeqNo(), fontCh12, 0, LEFT);
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
                         addEmptyRow(table, 5);

                         if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            // 換了頁就不印分隔線了
                            deleteRow(table, 5);
                            document.add(table);
                            table = addHeader(userData ,caseData, false);
                         }
                        else {
                            deleteRow(table, 5);
                        }
            			 }
            		}
            	
            	}
            
            //---
            addLine(table);
            // 眷屬編審註記---
            addColumn(table, 15, 1, "眷屬編審註記：", fontCh12, 0, LEFT);
            addColumn(table, 45, 1, " ", fontCh12, 0, LEFT);
            
            if(benChkList.size() > 0){
            	
            	for(PaymentQueryDetailDataCase benChkFile : benChkList ){
            		addColumn(table, 60, 1, "眷屬序："+benChkFile.getSeqNo(), fontCh12, 0, LEFT);
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
                         addEmptyRow(table, 5);

                         if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            // 換了頁就不印分隔線了
                            deleteRow(table, 5);
                            document.add(table);
                            table = addHeader(userData ,caseData, false);
                         }
                        else {
                            deleteRow(table, 5);
                        }
            			 }
            		}
            	
            	}
            //---
            
            addLine(table);
            
            //【結案資料】
            
            addColumn(table, 60, 1, "【結案資料】", fontCh12, 0, LEFT);
            
            addColumn(table, 15, 1, "結案日期："+caseData.getCloseDateStr(), fontCh12, 0, LEFT);// 結案日期
            addColumn(table, 15, 1, "失蹤日期："+caseData.getEvtMissingDateStr(), fontCh12, 0, LEFT);// 失蹤日期
            addColumn(table, 15, 1, "死亡日期："+caseData.getEvtDieDateStr(), fontCh12, 0, LEFT);// 死亡日期
            addColumn(table, 15, 1, "擇領起月："+caseData.getChoiceYmStr(), fontCh12, 0, LEFT);
            
            addColumn(table, 60, 1, "結案原因："+caseData.getCloseCause(), fontCh12, 0, LEFT);//結案原因：
            
            if(StringUtils.isBlank(caseData.getBmApNo())){
            	caseData.setBmApNo("");
            }
            addColumn(table, 15, 1, "差額金受理編號："+caseData.getBmApNo(), fontCh12, 0, LEFT);// 差額金受理編號
            if(caseData.getBmChkAmt() == null){
            	caseData.setBmChkAmt(new BigDecimal(0));
            }
            addColumn(table, 15, 1, "差額金金額："+DecimalFormat.getNumberInstance().format(caseData.getBmChkAmt()).toString() , fontCh12, 0, LEFT);// 差額金金額
            addColumn(table, 15, 1, "差額金核付日期："+caseData.getBmPayDteStr(), fontCh12, 0, LEFT);// 差額金核付日期
            addColumn(table, 15, 1, " ", fontCh12, 0, LEFT);
            
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
            addColumn(table, 10, 1, "補件日期：", fontCh12, 0, LEFT);
            StringBuffer detail3ProDateString = new StringBuffer("");
            
            if(detail3.size() > 0){
            for(int i = 0 ; i < detail3.size() ; i++){
            	if(i == detail3.size() - 1){
            		detail3ProDateString.append(detail3.get(i).getProDate());
            	}else{
            		detail3ProDateString.append(detail3.get(i).getProDate()+" ,");
            	}
            }
            addColumn(table, 20, 1, detail3ProDateString.toString() , fontCh12, 0, LEFT); //補件日期：
            }else{
            addColumn(table, 20, 1, " ", fontCh12, 0, LEFT);	//補件日期：
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
            
            //其他簽函日期：---
            addColumn(table, 10, 1, "其他簽函日期：", fontCh12, 0, LEFT);
            StringBuffer detail5ProDateString = new StringBuffer("");
            
            if(detail5.size() > 0){
            for(int i = 0 ; i < detail5.size() ; i++){
            	if(i == detail5.size() - 1){
            		detail5ProDateString.append(detail5.get(i).getProDate());
            	}else{
            		detail5ProDateString.append(detail5.get(i).getProDate()+" ,");
            	}
            }
            addColumn(table, 50, 1, detail5ProDateString.toString() , fontCh12, 0, LEFT); //其他簽函日期：
            }else{
            addColumn(table, 50, 1, " ", fontCh12, 0, LEFT);	//其他簽函日期：
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
                    addColumn(table, 6, 1, "受款人序" , fontCh12, 0, CENTER);
                    addColumn(table, 6, 1, "受款人姓名" , fontCh12, 0, CENTER);
                    addColumn(table, 6, 1, "關係" , fontCh12, 0, CENTER);
                    addColumn(table, 6, 1, "核定金額" , fontCh12, 0, CENTER);
                    addColumn(table, 6, 1, "紓困金額" , fontCh12, 0, CENTER);
                    addColumn(table, 6, 1, "扣減總金額" , fontCh12, 0, CENTER);
                    addColumn(table, 8, 1, "補發/收回金額" , fontCh12, 0, CENTER);
                    addColumn(table, 5, 1, "實付金額" , fontCh12, 0, CENTER);
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
                    	addColumn(table, 6, 1, intValue , fontCh12, 0, CENTER); //受款人序
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
                        addColumn(table, 8, 1, DecimalFormat.getNumberInstance().format(issuExt.getRecAmt()).toString() , fontCh12, 0, RIGHT); //補發/收回金額
                        if(issuExt.getAplpayAmt() == null){
                        	issuExt.setAplpayAmt(new BigDecimal(0));
                        } 
                        addColumn(table, 5, 1, DecimalFormat.getNumberInstance().format(issuExt.getAplpayAmt()).toString() , fontCh12, 0, RIGHT); //實付金額
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
            if(benDataList != null && benDataList.size() > 0){
            for(PaymentQueryBenDataCase benData : benDataList){
            	
            	//---在塞分隔線前, 先隨便塞空白行測試是否需換頁
                addEmptyRow(table, 7);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // 換了頁就不印分隔線了
                    deleteRow(table, 7);
                    document.add(table);
                    table = addHeader(userData ,caseData, false);
                    }else {
                    deleteRow(table, 7);
                    }
                //---
                
            	if(benDataList.get(0).equals(benData)){
            		
            		addColumn(table, 60, 1, "【事故者資料】", fontCh12, 0, LEFT);
            		
            		addColumn(table, 60, 1, "事故者姓名："+benData.getBenName(), fontCh12, 0, LEFT);
            		addColumn(table, 15, 1, "事故者身分證號："+benData.getBenIdnNo(), fontCh12, 0, LEFT);      		
            		addColumn(table, 15, 1, "事故者出生日期："+benData.getBenBrDateStr(), fontCh12, 0, LEFT);
            		String BenSex = "";
            		if(benData.getBenSex().equals("1")){
            			BenSex = ConstantKey.BAAPPBASE_SEX_STR_1;
            		}else if(benData.getBenSex().equals("2")){
            			BenSex = ConstantKey.BAAPPBASE_SEX_STR_2;
            		}
            		addColumn(table, 15, 1, "性別："+BenSex, fontCh12, 0, LEFT);
            		addColumn(table, 15, 1, "關係："+benData.getBenEvtRelStr(), fontCh12, 0, LEFT);
            		
            		addColumn(table, 15, 1, "申請日期："+benData.getBenEvtAppDateStr(), fontCh12, 0, LEFT);
            		if(benData.getBenNationTyp().equals("1")){
            		    addColumn(table, 15, 1, "國籍別： 本國", fontCh12, 0, LEFT);
            		}else if(benData.getBenNationTyp().equals("2")){
            			addColumn(table, 15, 1, "國籍別： 外籍", fontCh12, 0, LEFT);
            		}
            		addColumn(table, 15, 1, "國籍："+benData.getBenNationCode()+" "+benData.getBenNationName(), fontCh12, 0, LEFT);
            		addColumn(table, 15, 1, "身分查核年月："+benData.getIdnChkYmStr(), fontCh12, 0, LEFT);
            	
            		if(benData.getBenEvtRel().equals("Z")){
            			addColumn(table, 30, 1, "身分查核年月："+benData.getOldAplDpt(), fontCh12, 0, LEFT);
            			addColumn(table, 30, 1, "身分查核年月："+benData.getUname(), fontCh12, 0, LEFT);
            		}
            		
            		addColumn(table, 15, 1, "電話1："+benData.getTel1(), fontCh12, 0, LEFT);
            		addColumn(table, 15, 1, "電話2："+benData.getTel2(), fontCh12, 0, LEFT);
            		if(benData.getCommTyp().equals("1")){
            		    addColumn(table, 30, 1, "地址： "+"同戶籍地 -"+benData.getCommZip()+" "+benData.getCommAddr(), fontCh12, 0, LEFT);
            		}else if(benData.getCommTyp().equals("2")){
            			addColumn(table, 30, 1, "地址："+"現住址 -"+benData.getCommZip()+" "+benData.getCommAddr(), fontCh12, 0, LEFT);	
            		}
            		
            		if(benData.getAccRel().equals("1") || benData.getAccRel().equals("2")){
            		    addColumn(table, 30, 1, "給付方式："+" 本人領取 - "+benData.getPayTypStr(), fontCh12, 0, LEFT);
            		    if(benData.getSpecialAcc().equals("Y")){
            		    	if(benData.getPayTyp().equals("1") && !benData.getPayBankId().equals("700")){
            		    	    addColumn(table, 30, 1, "帳號："+benData.getPayBankId()+"-0000-"+benData.getPayEeacc()+" "+"("+benData.getBankName()+")"+"專戶", fontCh12, 0, LEFT);
            		    	}else{
            		    		addColumn(table, 30, 1, "帳號："+benData.getPayBankId()+"-"+benData.getBranchId()+"-"+benData.getPayEeacc()+" "+"("+benData.getBankName()+")"+"專戶", fontCh12, 0, LEFT);
            		    	}
            		    }else{
            		    	if(benData.getPayTyp().equals("1") && !benData.getPayBankId().equals("700")){
            		    	    addColumn(table, 30, 1, "帳號："+benData.getPayBankId()+"-0000-"+benData.getPayEeacc()+" "+"("+benData.getBankName()+")", fontCh12, 0, LEFT);
            		    	}else{
            		    		addColumn(table, 30, 1, "帳號："+benData.getPayBankId()+"-"+benData.getBranchId()+"-"+benData.getPayEeacc()+" "+"("+benData.getBankName()+")", fontCh12, 0, LEFT);
            		    	}
            		    }
            		}else if (benData.getAccRel().equals("3")){
            			addColumn(table, 30, 1, "給付方式："+" 具名領取 - "+benData.getAccName(), fontCh12, 0, LEFT);
            			addColumn(table, 30, 1, "帳號：", fontCh8, 0, LEFT);
            		}
            		
            		if(benData.getAccRel().equals("1") || benData.getAccRel().equals("2")){
            		    addColumn(table, 60, 1, "戶名："+benData.getAccName(), fontCh12, 0, LEFT);
            		}else if (benData.getAccRel().equals("3")){
            			addColumn(table, 60, 1, "戶名：", fontCh12, 0, LEFT);
            		}

            	}else{
            		
            		addColumn(table, 60, 1, "【受款人資料】", fontCh12, 0, LEFT);
            		//受款人序
            		String indexValueStr = "";
            		for(int i = 1 ; i < benDataList.size() ; i++){
            			if(benDataList.get(i).equals(benData)){
            			indexValueStr = Integer.toString(i);
            			}
            		}
            		addColumn(table, 15, 1, "受款人序："+indexValueStr, fontCh12, 0, LEFT);
            		addColumn(table, 15, 1, "受款人姓名："+benData.getBenName(), fontCh12, 0, LEFT);
            		addColumn(table, 30, 1, " ", fontCh12, 0, LEFT);
            		
            		addColumn(table, 15, 1, "受款人身分證號："+benData.getBenIdnNo(), fontCh12, 0, LEFT);
            		addColumn(table, 15, 1, "受款人出生日期："+benData.getBenIdnNo(), fontCh12, 0, LEFT);
            		String BenSex = "";
            		if(benData.getBenSex().equals("1")){
            			BenSex = ConstantKey.BAAPPBASE_SEX_STR_1;
            		}else if(benData.getBenSex().equals("2")){
            			BenSex = ConstantKey.BAAPPBASE_SEX_STR_2;
            		}
            		addColumn(table, 15, 1, "性別："+BenSex, fontCh12, 0, LEFT);
            		addColumn(table, 15, 1, "關係："+benData.getBenEvtRelStr(), fontCh12, 0, LEFT);
                    
            		if(benData.getBenEvtRel().equals("Z")){
            			if(benData.getCutAmt() == null){
                			benData.setCutAmt(new BigDecimal(0));
                		}
            			addColumn(table, 60, 1, "實際補償金額："+benData.getCutAmt(), fontCh12, 0, LEFT);
            		}
            		
            		addColumn(table, 15, 1, "繼承人申請日期："+benData.getBenEvtAppDateStr(), fontCh12, 0, LEFT);
            		if(benData.getBenEvtRel().equals("1") || benData.getBenEvtRel().equals("2") || benData.getBenEvtRel().equals("3") || benData.getBenEvtRel().equals("4") || benData.getBenEvtRel().equals("5") || benData.getBenEvtRel().equals("6") || benData.getBenEvtRel().equals("7")){
            		addColumn(table, 15, 1, "繼承自受款人："+benData.getBenEvtName(), fontCh12, 0, LEFT);
            		}else if(!benData.getBenEvtRel().equals("1") || !benData.getBenEvtRel().equals("2") || !benData.getBenEvtRel().equals("3") || !benData.getBenEvtRel().equals("4") || !benData.getBenEvtRel().equals("5") || !benData.getBenEvtRel().equals("6") || !benData.getBenEvtRel().equals("7")){
            			addColumn(table, 15, 1, " ", fontCh8, 0, LEFT);	
            		}
            		addColumn(table, 30, 1, " ", fontCh12, 0, LEFT);
            		
            		if(benData.getBenNationTyp().equals("1")){
            		    addColumn(table, 15, 1, "國籍別： 本國", fontCh12, 0, LEFT);
            		}else if(benData.getBenNationTyp().equals("2")){
            			addColumn(table, 15, 1, "國籍別： 外籍", fontCh12, 0, LEFT);
            		}
            		addColumn(table, 15, 1, "國籍："+benData.getBenNationCode()+" "+benData.getBenNationName(), fontCh12, 0, LEFT);
            		addColumn(table, 15, 1, "身分查核年月："+benData.getIdnChkYmStr(), fontCh12, 0, LEFT);
            		addColumn(table, 15, 1, " ", fontCh12, 0, LEFT);
            		
            		
            		addColumn(table, 15, 1, "電話1："+benData.getBenEvtAppDateStr(), fontCh12, 0, LEFT);
            		addColumn(table, 15, 1, "電話2："+benData.getBenNationTyp(), fontCh12, 0, LEFT);
            		if(benData.getCommTyp().equals("1")){
            		    addColumn(table, 30, 1, "地址： "+"同戶籍地 -"+benData.getCommZip()+" "+benData.getCommAddr(), fontCh12, 0, LEFT);
            		}else if(benData.getCommTyp().equals("2")){
            			addColumn(table, 30, 1, "地址："+"現住址 -"+benData.getCommZip()+" "+benData.getCommAddr(), fontCh12, 0, LEFT);	
            		}
            	    
            		// 在塞分隔線前, 先隨便塞空白行測試是否需換頁
                    addEmptyRow(table, 5);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                       // 換了頁就不印分隔線了
                       deleteRow(table, 5);
                       document.add(table);
                       table = addHeader(userData ,caseData, false);
                    }
                   else {
                       deleteRow(table, 5);
                   }
            		
            		if(benData.getAccRel().equals("1") || benData.getAccRel().equals("2")){
            		    addColumn(table, 30, 1, "給付方式："+" 本人領取 - "+benData.getPayTypStr(), fontCh12, 0, LEFT);
            		    if(benData.getSpecialAcc().equals("Y")){
            		    	if(benData.getPayTyp().equals("1") && !benData.getPayBankId().equals("700")){
            		    	    addColumn(table, 30, 1, "帳號："+benData.getPayBankId()+"-0000-"+benData.getPayEeacc()+" "+"("+benData.getBankName()+")"+"專戶", fontCh12, 0, LEFT);
            		    	}else{
            		    		addColumn(table, 30, 1, "帳號："+benData.getPayBankId()+"-"+benData.getBranchId()+"-"+benData.getPayEeacc()+" "+"("+benData.getBankName()+")"+"專戶", fontCh12, 0, LEFT);
            		    	}
            		    }else{
            		    	if(benData.getPayTyp().equals("1") && !benData.getPayBankId().equals("700")){
            		    	    addColumn(table, 30, 1, "帳號："+benData.getPayBankId()+"-0000-"+benData.getPayEeacc()+" "+"("+benData.getBankName()+")", fontCh12, 0, LEFT);
            		    	}else{
            		    		addColumn(table, 30, 1, "帳號："+benData.getPayBankId()+"-"+benData.getBranchId()+"-"+benData.getPayEeacc()+" "+"("+benData.getBankName()+")", fontCh12, 0, LEFT);
            		    	}
            		    }
            		}else if (benData.getAccRel().equals("3")){
            			addColumn(table, 30, 1, "給付方式："+" 具名領取 - "+benData.getAccName(), fontCh12, 0, LEFT);
            			addColumn(table, 30, 1, "帳號：", fontCh12, 0, LEFT);
            		}
            		
            		if(benData.getAccRel().equals("1") || benData.getAccRel().equals("2")){
            		    addColumn(table, 60, 1, "戶名："+benData.getAccName(), fontCh12, 0, LEFT);
            		}else if (benData.getAccRel().equals("3")){
            			addColumn(table, 60, 1, "戶名：", fontCh12, 0, LEFT);
            		}
            		
            		if(!benData.getBenEvtRel().equals("Z") && !benData.getBenEvtRel().equals("F") && !benData.getBenEvtRel().equals("N")){
            			addColumn(table, 15, 1, "婚姻狀況："+benData.getBenMarrMkStr(), fontCh12, 0, LEFT);
            			addColumn(table, 45, 1, "法定代理人姓名："+benData.getGrdName(), fontCh12, 0, LEFT);
            			addColumn(table, 15, 1, "法定代理人身分證號："+benData.getGrdIdnNo(), fontCh12, 0, LEFT);
            			addColumn(table, 45, 1, "法定代理人出生日期："+benData.getGrdBrDateStr(), fontCh12, 0, LEFT);
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
