package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.dao.BaappbaseDao;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Babatchjobdtl;
import tw.gov.bli.ba.domain.Baunqualifiednotice;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt05Case;
import tw.gov.bli.ba.services.BjService;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.util.RptTitleUtility;
import tw.gov.bli.common.helper.SpringHelper;

public class MonthlyRpt05Report extends ReportBase {

    private RptService rptService;
    public int PageNo = 1;
    private int flag = 0;
    private static Log log = LogFactory.getLog(MonthlyRpt05Report.class);

    public MonthlyRpt05Report() throws Exception {
        super();
    }

    public Document createDocument() {
        Rectangle rectangle = new Rectangle(576, 862);
        Document document = new Document(rectangle, 10, 10, 0, 0);
        return document;
    }

    public Table printPage(MonthlyRpt05Case caseData) throws Exception {
        int SigWidth = NumberUtils.toInt(PropertyHelper.getProperty("signature.width"));
        int SigHeight = NumberUtils.toInt(PropertyHelper.getProperty("signature.height"));

    	if(flag == 0){  //寫入成功 寫檔一開始PDF new Page有使用所以下一筆要再 new page
    		document.newPage();
    		flag = 1;   //先設初始
    	}
        
        // 建立表格
        Table table = createTable(60);
        // table.setAutoFillEmptyCells(true);

        // addColumn(table, 60, 1, " ", fontCh8, 0, CENTER);
        addColumn(table, 15, 1, " ", fontCh20, 0, RIGHT);
        // 20230711 BABAWEB-81 遺屬核定函邊界調整
        addColumnAssignLineSpace(table, 30, 1, RptTitleUtility.getRpt05Title(caseData.getAplpayDate())+"  函", fontCh22b, 10, 0, LEFT);
        addColumnAssignVAlignment(table, 15, 1, StringUtils.leftPad(String.valueOf(PageNo), 5, "0"), fontCh10, 0, RIGHT, TOP);
        // addColumn(table, 60, 1, " ", fontCh8, 0, CENTER);

//        addColumn(table, 15, 1, " ", fontCh20, 0, RIGHT);
        
        // addColumnAssignVAlignment(table, 17, 1, "(給 付 函)", fontCh14, 0, LEFT, BOTTOM);
        addColumnAssignVAlignment(table, 60, 1, "", fontCh14, 0, RIGHT, BOTTOM);

        // 20090429 先壓0980430 本批次印出後 在改為核付日期
        //caseData.setWordDate(DateUtility.formatChineseDateString("0980430", true));
        // addColumn(table, 37, 1, " ", fontCh11, 0, CENTER);
        // addColumn(table, 23, 1, "機關地址：10013台北市羅斯福路1段4號 " + "發文日期：" + StringUtils.rightPad(prtDate, (StringUtils.isBlank(prtDate)) ? 28 : 26, " ") + "發文字號：保給字第" + caseData.getWordNo() + "號" + " " + "承辦單位："
        // + StringUtils.rightPad(caseData.getUnit(), 18, " ") + "聯絡電話：" + caseData.getComTel(), fontCh11, 0, LEFT);
        addColumnAssignLineSpace(table, 60, 1, "　", fontCh1, 0, 0, RIGHT);
        addColumnAssignLineSpace(table, 35, 1, "　", fontCh1, 0, 0, RIGHT);
        addColumnAssignLineSpace(table, 25, 1, "機關地址：100232台北市羅斯福路1段4號", fontCh11, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 35, 1, "　", fontCh1, 0, 0, RIGHT);
        addColumnAssignLineSpace(table, 25, 1, "承辦單位："+ caseData.getUnit(), fontCh11, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 35, 1, "　", fontCh1, 0, 0, RIGHT);
        addColumnAssignLineSpace(table, 25, 1, "聯絡方式：" + caseData.getComTel(), fontCh11, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 35, 1, "　", fontCh1, 0, 0, RIGHT);
        addColumnAssignLineSpace(table, 25, 1, "受理編號：" + caseData.getWordNo(), fontCh11, 0, 0, LEFT);

        addColumnAssignLineSpace(table, 11, 1, "地  址：", fontCh12, 0, 0, RIGHT);
        addColumnAssignLineSpace(table, 49, 1, caseData.getAddrZip(), fontCh12, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 11, 1, " ", fontCh12, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 23, 1, caseData.getAddr(), fontCh12, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 26, 1, " ", fontCh12, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 11, 1, "受文者：", fontCh16, 0, 0, RIGHT);
        if(StringUtils.isNotBlank(caseData.getReceiveName())){
        	addColumnAssignLineSpace(table, 49, 1, caseData.getReceiveName(), fontCh16, 0, 0, LEFT);
        }else{
        	addColumnAssignLineSpace(table, 49, 1, caseData.getName(), fontCh16, 0, 0, LEFT);
        }

        if(StringUtils.isNotBlank(caseData.getRmp_Name())) {
            addColumnAssignLineSpace(table, 11, 1, "", fontCh16, 0, 0, RIGHT);
            addColumnAssignLineSpace(table, 49, 1, caseData.getRmp_Name(), fontCh12, 0, 0, LEFT);
        }

        addColumn(table, 60, 1, " ", fontCh10, 0, CENTER);
        String test = caseData.getWordDate();
        addColumnAssignLineSpace(table, 60, 1, "發文日期：" + StringUtils.rightPad(caseData.getWordDate(), (StringUtils.isBlank(caseData.getWordDate())) ? 28 : 26, " "), fontCh11, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 60, 1, "發文字號：" + RptTitleUtility.getGroupsWordTitleForAplpayDate(caseData.getWordNo().substring(0, 1), caseData.getAplpayDate()) + "第" + caseData.getWordNo() + "號", fontCh11, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 60, 1, "速別：普通件", fontCh11, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 60, 1, "密等及解密條件或保密期限：普通", fontCh11, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 60, 1, "附件：", fontCh11, 0, 0, LEFT);
        addColumn(table, 60, 1, " ", fontCh8, 0, CENTER);

        List<String> message = caseData.getMessage();
        addColumnAssignLineSpace(table, 5, 1, "主旨：", fontCh12, 2, 0, LEFT);
        addColumnAssignLineSpace(table, 55, 1, message.get(0), fontCh12, 2, 0, JUSTIFIED);

//        addColumn(table, 60, 1, " ", fontCh8, 0, CENTER);
//        addColumn(table, 60, 1, " ", fontCh8, 0, CENTER);

        addColumn(table, 6, 1, "說明：", fontCh12, 0, LEFT);
        addColumn(table, 54, 1, " ", fontCh12, 0, LEFT);

        for (int i = 1; i < message.size(); i++) {
            String msg = StringUtils.trim(message.get(i));
            int index = msg.indexOf("、");
            addColumnAssignLineSpace(table, 6, 1, msg.substring(0, index + 1), fontCh12, 2, 0, RIGHT);
            addColumnAssignLineSpace(table, 54, 1, msg.substring(index + 1, msg.length()), fontCh12, 2, 0, JUSTIFIED);
        }

        if (caseData.getPayCode().equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_S)) {
            addColumnAssignLineSpace(table, 5, 1, "正本：", fontCh12, 5, 0, LEFT);
            addColumnAssignLineSpace(table, 55, 1, caseData.getRealName(), fontCh12, 5, 0, LEFT);
        }
        //else if (caseData.getPayCode().equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_L) && (caseData.getNotifyForm().substring(0, 1).equals("D") || caseData.getNotifyForm().substring(0, 1).equals("L"))) {
        else if (caseData.getPayCode().equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_L) ) {	
            // 老年年金  NotifyForm = Dxx 或 Lxx 不用印「正本」
        	// 2021/3/22 mail 所有老年年金不用印「正本」
//            addColumnAssignLineSpace(table, 5, 1, "　", fontCh12, 5, 0, LEFT);
//            addColumnAssignLineSpace(table, 55, 1, "　", fontCh12, 5, 0, LEFT);
        }
        else { // 失能年金
            addColumnAssignLineSpace(table, 5, 1, "正本：", fontCh12, 5, 0, LEFT);
            addColumnAssignLineSpace(table, 55, 1, caseData.getProgenitor(), fontCh12, 5, 0, LEFT);
        }
        /*
         * 20090417 修改 不要印關係 Z,F if ((caseData.getCopy1() == null || caseData.getCopy1().isEmpty()) && (caseData.getCopy2() == null || caseData.getCopy2().isEmpty())) { addColumn(table, 60, 1, " ", fontCh12, 0, CENTER); addColumn(table, 60, 1, " ", fontCh12, 0, CENTER); } else if (caseData.getCopy2() == null || caseData.getCopy2().isEmpty()) { addColumnAssignLineSpace(table, 5, 1, "副本：", fontCh12, 8, 0, LEFT); addColumnAssignLineSpace(table, 55, 1, getNameMsg(caseData.getCopy1()), fontCh12, 8,
         * 0, LEFT); addColumn(table, 60, 1, " ", fontCh12, 0, CENTER); } else if (caseData.getCopy1() == null || caseData.getCopy1().isEmpty()) { addColumnAssignLineSpace(table, 5, 1, "副本：", fontCh12, 8, 0, LEFT); addColumnAssignLineSpace(table, 55, 1, getNameMsg(caseData.getCopy2()), fontCh12, 8, 0, LEFT); addColumn(table, 60, 1, " ", fontCh12, 0, CENTER); } else { addColumnAssignLineSpace(table, 5, 1, "副本：", fontCh12, 8, 0, LEFT); addColumnAssignLineSpace(table, 55, 1,
         * getNameMsg(caseData.getCopy1()), fontCh12, 8, 0, LEFT); addColumnAssignLineSpace(table, 5, 1, " ", fontCh12, 8, 0, LEFT); addColumnAssignLineSpace(table, 55, 1, getNameMsg(caseData.getCopy2()), fontCh12, 8, 0, LEFT); }
         */
        //20141112移除副本資料
        //if (caseData.getCopy1() != null && caseData.getCopy1().size() > 0) {
        //    addColumnAssignLineSpace(table, 5, 1, "副本：", fontCh12, 8, 0, LEFT);
        //    addColumnAssignLineSpace(table, 55, 1, getNameMsg(caseData.getCopy1()), fontCh12, 8, 0, LEFT);
        //}

        while (writer.fitsPage(table)) {
            addColumnAssignLineSpace(table, 60, 1, " ", fontCh1, 0, 0, RIGHT);
        }

        // if (caseData.getPayCode().equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_L) && (caseData.getNotifyForm().substring(0, 1).equals("D") || caseData.getNotifyForm().substring(0, 1).equals("L"))) {
        if (caseData.getPayCode().equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_L)) {
            for (int i = 0; i < 25; i++)
                table.deleteLastRow();
        }
        else {
            for (int i = 0; i < 19; i++)
                table.deleteLastRow();
        }

        // 20091210 增加列印總經理
        if (caseData.getManagerImg() != null) {
            //addColumn(table, 60, 1, "     " + RptTitleUtility.getManegerTitle(caseData.getAplpayDate(), caseData.getManagerStr()), fontCh20, 0, LEFT);
//        	addColumn(table, 60, 1, "     " + caseData.getManager(), fontCh20, 0, LEFT);
            //drawString(document, "     " + caseData.getManager(), 10, 130, 0, 20, "left");
            drawImage(caseData.getManagerImg(), 18, 268, SigWidth, SigHeight); // 單位：mm
        }
        else {
//            addColumn(table, 60, 1, "     ", fontCh20, 0, LEFT);
            drawString(document, "     ", 10, 130, 0, 20, "left");

        }
        String Pbm0001 = StringUtils.replace(caseData.getPbm0001(), " ", "");
        
        //直接用畫的上去不會造成跳頁問題
        drawString(document, Pbm0001, 456, 16, 0, 12, "right");
        
        return table;
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<MonthlyRpt05Case> caseList, String payCode, String jobId, BjService bjService, String issuYm, int Pageno) throws Exception {

        try {
            document.open();
            Table table = null;
            String strinfo = "";
            ArrayList<String> ApNoList = new  ArrayList<String>();
            PageNo = Pageno;

            for (MonthlyRpt05Case caseData : caseList) {
                if (!(caseData.getApNo().substring(0, 1).equals("L") && caseData.getNotifyForm().substring(0, 1).equals("D") && caseData.getSeqNo().equals("0000"))) {
                    caseData.setPayCode(payCode);
                    try{
                    	table = printPage(caseData);
                        document.add(table);
                        PageNo++;
                        flag = 0; //寫入成功 寫檔一開始PDF new Page有使用所以下一筆要再 new page
                        ApNoList.add(caseData.getApNo());
                    }catch (Exception e){
                    	if(jobId != null && bjService !=null){
                    		//log 產檔失敗資訊與筆數
                    		strinfo = "受理編號:" + caseData.getApNo() + "，SEQNO=" + caseData.getSeqNo() +  "，寫入核定函資料檔失敗-" + e;
                    		log.error(strinfo);
                    		log.error(ExceptionUtility.getStackTrace(e));
                    		
                    		if (strinfo.length() > 150)
                    			strinfo = strinfo.substring(0, 150);
                			savebabatchjobdtl(jobId, "1", strinfo, bjService);
                    	}
                    	flag = 1; //寫入失敗  寫檔一開始PDF new Page沒有使用所以下一筆無須再 new page
                    }
                }
            }
            
            if("S".equals(payCode) &&  ApNoList.size() > 0){
            	BaappbaseDao baappbaseDao = (BaappbaseDao) SpringHelper.getBeanById("baappbaseDao");
                UpdateService updateService = (UpdateService) SpringHelper.getBeanById("updateService");
                
                List<Baappbase> baappbasePrintList2 = new ArrayList<Baappbase>();
                // 20180810 不合格原因為死亡，也需寫入核定通知紀錄，倘未寫入將來無法追蹤其是否曾於核定函內被提及死亡 (TRIM(A.BENDIEDATE) = '' OR TRIM(A.BENDIEDATE) IS NULL) 死亡日期 
                for(String apNo : ApNoList){
                	
                	List<Baappbase> baappbaseList = baappbaseDao.selectMonthlyRpt05ForSurvivorReport(apNo);
                    if (baappbaseList.size() == 0)
                        return null;// 查無主檔資料

                    // 以序號0000那比去查給付檔
                    Baappbase evtData = baappbaseList.get(0);
                	
                	baappbasePrintList2 = baappbaseDao.selectMonthlyRpt05PrintCase2DataForSurvivorReport2(apNo, ("".equals(issuYm)) ? evtData.getIssuYm() : issuYm);
                    
                    for (Baappbase baappbase : baappbasePrintList2) {
                        if (!baappbase.getUnqualifiedCause().equals("") || (baappbase.getCloseCause().equals("") && baappbase.getUnqualifiedCause().equals(""))) {

                            List<Baunqualifiednotice> baunqualifiednoticeList = new ArrayList<Baunqualifiednotice>();

                            // 新增不合格核定通知紀錄檔 (BAUNQUALIFIEDNOTICE)
                            Baunqualifiednotice baunqualifiednotice = new Baunqualifiednotice();
                            baunqualifiednotice.setBaappbaseId(baappbase.getBaappbaseId());
                            baunqualifiednotice.setApNo(baappbase.getApNo());
                            baunqualifiednotice.setCaseTyp(baappbase.getCaseTyp());
                            baunqualifiednotice.setIssuYm(baappbase.getIssuYm());
                            baunqualifiednotice.setSeqNo(baappbase.getSeqNo());
                            baunqualifiednotice.setBenIdnNo(baappbase.getBenIdnNo());
                            baunqualifiednotice.setBenName(baappbase.getBenName());
                            baunqualifiednotice.setUnqualifiedCause(baappbase.getUnqualifiedCause());
                            baunqualifiednotice.setNotifyForm(baappbase.getNotifyForm());
                            if (userData != null)
                            baunqualifiednotice.setCrtUser(userData.getEmpNo());
                            baunqualifiednotice.setCrtTime(DateUtility.getNowWestDateTime(true));

                            baunqualifiednoticeList.add(baunqualifiednotice);

                            // 新增不合格核定通知紀錄檔 (BAUNQUALIFIEDNOTICE) 及更新主檔欄位
                            updateService.insertUnqualifiedNotice(baunqualifiednoticeList, baappbase.getApNo(), baappbase.getSeqNo());

                            /**
                             * // 新增不合格核定通知紀錄檔 (BAUNQUALIFIEDNOTICE) baunqualifiednoticeDao.insertData(baunqualifiednoticeList); // 更新主檔欄位 baappbaseDao.updateIssuNotifyingMkByApNo(baappbase.getApNo());
                             * baappbaseDao.updateUnqualifiedCauseByApNo(baappbase.getApNo(), baappbase.getSeqNo());
                             */
                        }
                    }
                }
            }
            document.close();
        }
        finally {
            document.close();
        }
        return bao;
    }

	private String getNameMsg(List<String> nameList) {
        String msg = "";
        if (nameList != null) {
            for (String name : nameList) {
                if (StringUtils.isNotBlank(msg)) {
                    msg = msg + "、" + name;
                }
                else {
                    msg = name;
                }
            }
        }
        return msg;
    }
    
    //將執行結果訊息存入[線上批次啟動作業紀錄明細檔 BABATCHJOBDTL]
    public void savebabatchjobdtl(String jobDataid, String flag, String strinfo, BjService bjService){
    		try {
    			Babatchjobdtl babatchjobdtl = new Babatchjobdtl();
    			babatchjobdtl.setBaJobId(jobDataid);
    			babatchjobdtl.setFlag(flag);
    			babatchjobdtl.setFlagMsg(strinfo);
    			babatchjobdtl.setFlagTime(DateUtility.getNowWestDateTime(true));
    			bjService.inserBabatchjobdtlData(babatchjobdtl);
    		}
    		catch(Exception exp) {
    			log.error("新增babatchjobdtl失敗-" + exp);
    		}
    	}

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}

// 加入浮水印
// Image bliImg = Image.getInstance(this.getResourcePath("BLI_IMG.JPG"));
// bliImg.setAbsolutePosition(97, 740);
// bliImg.scalePercent(10, 10);
// document.add(bliImg);
// Image bliLabel = Image.getInstance(this.getResourcePath("BLI_LABEL.JPG"));
// bliLabel.setAbsolutePosition(50, 40);
// bliLabel.scalePercent(50, 50);
// document.add(bliLabel);
// Image bliLabel2 = Image.getInstance(this.getResourcePath("BLI_LABEL2.JPG"));
// bliLabel2.setAbsolutePosition(50, 10);
// bliLabel2.scalePercent(77, 77);
// document.add(bliLabel2);
// Image bliWaterMark = Image.getInstance(this.getResourcePath("BLI_WATERMARK.JPG"));
// bliWaterMark.setAbsolutePosition(130, 230);
// bliWaterMark.scalePercent(80,80);
// document.add(bliWaterMark);
