package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import com.lowagie.text.Element;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.Image;

import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt29Case;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;

public class MonthlyRpt29Report extends ReportBase {

    public MonthlyRpt29Report() throws Exception {
        super();
    }

    public Document createDocument() {
        Rectangle rectangle = new Rectangle(576, 862);
        Document document = new Document(rectangle, 10, 10, 0, 0);
        return document;
    }

    public Table printPage(MonthlyRpt29Case caseData, HashMap<String, Object> map, String rptTyp) throws Exception {
    	String rptKind = (String) map.get("rptKind");

        document.newPage();
        // 建立表格
        Table table = createTable(60);
        
        //修改時打開 方便看 B01或B03
        //addColumn(table, 60, 1, rptTyp, fontCh8, 0, CENTER);
        addColumn(table, 60, 1, "　", fontCh8, 0, CENTER);

        addColumn(table, 15, 1, " ", fontCh20, 0, RIGHT);
        addColumnAssignLineSpace(table, 30, 1, RptTitleUtility.getRpt05Title(DateUtility.getNowWestDate())+"  函", fontCh22b, 0, 0, LEFT);
        // addColumnAssignVAlignment(table, 17, 1, "(給 付 函)", fontCh14, 0, LEFT, BOTTOM);
        addColumnAssignVAlignment(table, 15, 1, "", fontCh14, 0, RIGHT, BOTTOM);

        addColumnAssignLineSpace(table, 60, 1, "　", fontCh1, 0, 0, RIGHT);
        addColumnAssignLineSpace(table, 35, 1, "　", fontCh1, 0, 0, RIGHT);
        addColumnAssignLineSpace(table, 25, 1, "機關地址：10013台北市羅斯福路1段4號", fontCh11, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 35, 1, "　", fontCh1, 0, 0, RIGHT);
        addColumnAssignLineSpace(table, 25, 1, "承辦單位："+ caseData.getUnit(), fontCh11, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 35, 1, "　", fontCh1, 0, 0, RIGHT);
        addColumnAssignLineSpace(table, 25, 1, "聯絡方式：" + caseData.getComTel(), fontCh11, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 35, 1, "　", fontCh1, 0, 0, RIGHT);
        addColumnAssignLineSpace(table, 25, 1, "受理編號：" + caseData.getWordNo(), fontCh11, 0, 0, LEFT);

        addColumnAssignLineSpace(table, 11, 1, "", fontCh12, 0, 0, RIGHT);
        addColumnAssignLineSpace(table, 49, 1, "掛　號", fontCh12, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 11, 1, "地  址：", fontCh12, 0, 0, RIGHT);
        addColumnAssignLineSpace(table, 49, 1, caseData.getAddrZip(), fontCh12, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 11, 1, " ", fontCh12, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 23, 1, caseData.getAddr(), fontCh12, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 26, 1, " ", fontCh12, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 11, 1, "受文者：", fontCh16, 0, 0, RIGHT);
        addColumnAssignLineSpace(table, 49, 1, caseData.getName(), fontCh16, 0, 0, LEFT);
        addColumn(table, 60, 1, " ", fontCh10, 0, CENTER);
        String test = caseData.getWordDate();
        addColumnAssignLineSpace(table, 60, 1, "發文日期：" + "中華民國 "+StringUtils.rightPad(caseData.getWordDate(), (StringUtils.isBlank(caseData.getWordDate())) ? 28 : 26, " "), fontCh11, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 60, 1, "發文字號：" + "保職簡字第" + caseData.getWordNo() + "號", fontCh11, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 60, 1, "速別：普通件", fontCh11, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 60, 1, "密等及解密條件或保密期限：普通", fontCh11, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 60, 1, "附件：", fontCh11, 0, 0, LEFT);
        addColumn(table, 60, 1, " ", fontCh8, 0, CENTER);

        List<String> message = caseData.getMessage();
        addColumnAssignLineSpace(table, 5, 1, "主旨：", fontCh12, 5, 0, LEFT);
        addColumnAssignLineSpace(table, 55, 1, message.get(0), fontCh12, 5, 0, JUSTIFIED);

        addColumn(table, 60, 1, " ", fontCh8, 0, CENTER);
        addColumn(table, 60, 1, " ", fontCh8, 0, CENTER);

        addColumn(table, 6, 1, "說明：", fontCh12, 0, LEFT);
        addColumn(table, 54, 1, " ", fontCh12, 0, LEFT);

        for (int i = 1; i < message.size(); i++) {
            String msg = StringUtils.trim(message.get(i));
            int index = msg.indexOf("、");
            addColumnAssignLineSpace(table, 6, 1, msg.substring(0, index + 1), fontCh12, 5, 0, RIGHT);
            addColumnAssignLineSpace(table, 54, 1, msg.substring(index + 1, msg.length()), fontCh12, 5, 0, JUSTIFIED);
        }

        /*
         * 20090417 修改 不要印關係 Z,F if ((caseData.getCopy1() == null || caseData.getCopy1().isEmpty()) && (caseData.getCopy2() == null || caseData.getCopy2().isEmpty())) { addColumn(table, 60, 1, " ", fontCh12, 0, CENTER); addColumn(table, 60, 1, " ", fontCh12, 0, CENTER); } else if (caseData.getCopy2() == null || caseData.getCopy2().isEmpty()) { addColumnAssignLineSpace(table, 5, 1, "副本：", fontCh12, 8, 0, LEFT); addColumnAssignLineSpace(table, 55, 1, getNameMsg(caseData.getCopy1()), fontCh12, 8,
         * 0, LEFT); addColumn(table, 60, 1, " ", fontCh12, 0, CENTER); } else if (caseData.getCopy1() == null || caseData.getCopy1().isEmpty()) { addColumnAssignLineSpace(table, 5, 1, "副本：", fontCh12, 8, 0, LEFT); addColumnAssignLineSpace(table, 55, 1, getNameMsg(caseData.getCopy2()), fontCh12, 8, 0, LEFT); addColumn(table, 60, 1, " ", fontCh12, 0, CENTER); } else { addColumnAssignLineSpace(table, 5, 1, "副本：", fontCh12, 8, 0, LEFT); addColumnAssignLineSpace(table, 55, 1,
         * getNameMsg(caseData.getCopy1()), fontCh12, 8, 0, LEFT); addColumnAssignLineSpace(table, 5, 1, " ", fontCh12, 8, 0, LEFT); addColumnAssignLineSpace(table, 55, 1, getNameMsg(caseData.getCopy2()), fontCh12, 8, 0, LEFT); }
         */

        while (writer.fitsPage(table)) {
            addColumnAssignLineSpace(table, 60, 1, " ", fontCh1, 0, 0, RIGHT);
        }

        for (int i = 0; i < 19; i++)
            table.deleteLastRow();

        // 20091210 增加列印總經理
        if (StringUtils.isNotBlank(caseData.getManager())) {
            //addColumn(table, 60, 1, "     " + RptTitleUtility.getManegerTitle(caseData.getAplpayDate(), caseData.getManagerStr()), fontCh20, 0, LEFT);
        	addColumn(table, 60, 1, "     " + caseData.getManager(), fontCh20, 0, LEFT);
        }
        else {
            addColumn(table, 60, 1, "     ", fontCh20, 0, LEFT);
        }
        for (int i = 0; i < 10; i++)
            addColumnAssignLineSpace(table, 35, 1, "", fontCh12, 28, 0, RIGHT);
            //addColumnAssignLineSpace(table, 22, 1, "(留　用)", fontCh12, 28, 0, LEFT);
            //addColumnAssignLineSpace(table, 25, 1, RptTitleUtility.getRpt05FooterStr(DateUtility.getNowWestDate()) , fontCh12, 33, 0, LEFT);
            addColumnAssignLineSpace(table, 25, 1, "" , fontCh12, 33, 0, LEFT);
        /*
         * // [ 先增加總經理 計算可以塞幾個空白行 // 20091210 增加列印總經理 if (StringUtils.isNotBlank(caseData.getManager())) { addColumn(table, 60, 1, " " + caseData.getManagerStr(), fontCh20, 0, LEFT); } else { addColumn(table, 60, 1, " ", fontCh20, 0, LEFT); } addColumnAssignLineSpace(table, 60, 1, caseData.getPbm0001(), fontCh12, 0, 0, RIGHT); int spaceNumber = -1; while (writer.fitsPage(table)) { addColumnAssignLineSpace(table, 60, 1, " ", fontCh1, 0, 0, RIGHT); spaceNumber++; } for (int i = 0; i < spaceNumber
         * + 3; i++) table.deleteLastRow(); // ] final int managerSpace = 5;// 總經理跟頁尾要差距的空白行 若內容太大則要調整空白行數 // 先塞計算出來的空白行 在印總經理 for (int i = 0; i < spaceNumber - managerSpace -5; i++) addColumnAssignLineSpace(table, 60, 1, " ", fontCh1, 0, 0, RIGHT); // 20091210 增加列印總經理 if (StringUtils.isNotBlank(caseData.getManager())) { addColumn(table, 60, 1, " " + caseData.getManagerStr(), fontCh20, 0, LEFT); } else { addColumn(table, 60, 1, " ", fontCh20, 0, LEFT); } if (spaceNumber - managerSpace > 0) { for
         * (int i = 0; i < managerSpace; i++) addColumnAssignLineSpace(table, 60, 1, " ", fontCh1, 0, 0, RIGHT); } else { for (int i = 0; i < spaceNumber; i++) addColumnAssignLineSpace(table, 60, 1, " ", fontCh1, 0, 0, RIGHT); } addColumnAssignLineSpace(table, 60, 1, caseData.getPbm0001(), fontCh12, 0, 0, RIGHT);
         */
        return table;
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<MonthlyRpt29Case> caseList, List<MonthlyRpt29Case> caseList06, HashMap<String, Object> map) throws Exception {

        try {

            document.open();
            Table table = null;
            
        	String payCode = (String) map.get("payCode");
        	//報表格式
        	String rptTyp = "";
            
        	 //列印 B01格式
            if(payCode.equals("K")){
        		rptTyp = "失能 B-01";
        	}else if(payCode.equals("S")){
        		rptTyp = "遺屬 B-01";
        	}
            if(caseList != null){
            for (MonthlyRpt29Case caseData : caseList) {
            	
            	//每張報表印兩份 20141117 改成印一份
            	for(int i = 0 ; i < 1 ; i++){
            	
                table = printPage(caseData, map, rptTyp);
                document.add(table);
                
            	}
            }
            }

            //列印六月份 B03格式
            if(payCode.equals("K")){
        		rptTyp = "失能 B-03";
        	}else if(payCode.equals("S")){
        		rptTyp = "遺屬 B-03";
        	}
            if(caseList06 != null){
            	 for (MonthlyRpt29Case caseData : caseList06) {
                 	
                 	for(int i = 0 ; i < 1 ; i++){
                 	
                     table = printPage(caseData, map, rptTyp);
                     document.add(table);
                     
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
