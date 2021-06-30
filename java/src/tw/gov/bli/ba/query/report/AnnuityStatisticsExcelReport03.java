package tw.gov.bli.ba.query.report;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.Region;
import org.eclipse.jetty.util.log.Log;

import com.lowagie.rups.view.Console;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.query.cases.AnnuityStatistics3Case;
import tw.gov.bli.ba.query.cases.AnnuityStatistics3DtlCase;
import tw.gov.bli.ba.query.cases.AnnuityStatistics3MetaDtlCase;
import tw.gov.bli.ba.rpt.report.ExcelReportBase;
import tw.gov.bli.ba.rpt.report.ReportBase;

public class AnnuityStatisticsExcelReport03 extends ExcelReportBase {
	private HSSFSheet tempSheet; 
    private HSSFCell tempCell; 
 
    public AnnuityStatisticsExcelReport03(String analysisSelect) throws Exception { 
    	super(analysisSelect); 
        sheetName = "AnnuityStatisticsExcel3";  
    }
    /**
     * 分析選項(無)
     * @param rptData
     */
    public void doDtlReport1(AnnuityStatistics3Case rptData) {
    	
    	tempSheet = workbook.getSheetAt(0); 
    	workbook.createSheet();
        HSSFSheet sheet = workbook.getSheetAt(1); 
         
        copyRows(workbook.getSheetName(0), workbook.getSheetName(1), 0, 3, 0, 100);//coprow0~100
        HSSFRow row = sheet.getRow(0); 
        
        HSSFCell cell = sheet.getRow(0).getCell((short) 0); 
        setCell(cell, cell.getCellStyle(), rptData.getQryPayCodeStr()); 
        
        cell = sheet.getRow(1).getCell((short) 0); 
        setCell(cell, cell.getCellStyle(), rptData.getQryTypStr()); 
        
        cell = sheet.getRow(3).getCell((short) 0); 
        setCell(cell, cell.getCellStyle(), rptData.getQryTypStr());
        
        row = incrementRow(sheet, row);row = incrementRow(sheet, row);row = incrementRow(sheet, row);
        
        BigDecimal sumPayCnt = new BigDecimal(0);
        BigDecimal sumCntRatio = new BigDecimal(0);
        
        
        for(int i=0; i<rptData.getDtlCase().size(); i++) {
        	AnnuityStatistics3DtlCase dtlCase = rptData.getDtlCase().get(i);
        	row = incrementRow(sheet, row);
        	
        	tempCell = tempSheet.getRow(4).getCell((short) 0); 
        	cell = row.createCell((short) 0); 
            setCell(cell, tempCell.getCellStyle(), dtlCase.getSpcStart()+"~"+dtlCase.getSpcEnd());
            
        	for(int j=0; j<dtlCase.getDtlList().size();j++) {
        		AnnuityStatistics3MetaDtlCase metaData = dtlCase.getDtlList().get(j);
                tempCell = tempSheet.getRow(4).getCell((short) 1); 
                cell = row.createCell((short) 1); 
                setCell(cell, tempCell.getCellStyle(), ReportBase.formatBigDecimalToInteger(new BigDecimal(metaData.getPayCnt()))); 
                sumPayCnt = sumPayCnt.add(new BigDecimal(metaData.getPayCnt()));
                
                tempCell = tempSheet.getRow(4).getCell((short) 2); 
                cell = row.createCell((short) 2); 
                setCell(cell, tempCell.getCellStyle(), metaData.getCntRatio()+"%"); 
                sumCntRatio = sumCntRatio.add((new BigDecimal(metaData.getCntRatio())));
        	}
        }
        row = incrementRow(sheet, row);
        
        tempCell = tempSheet.getRow(4).getCell((short) 0); 
        cell = row.createCell((short) 0); 
        setCell(cell, tempCell.getCellStyle(), "合計");
        
        tempCell = tempSheet.getRow(4).getCell((short) 1); 
        cell = row.createCell((short) 1); 
        setCell(cell, tempCell.getCellStyle(),  ReportBase.formatBigDecimalToInteger(sumPayCnt)); 
        
        tempCell = tempSheet.getRow(4).getCell((short) 2); 
        cell = row.createCell((short) 2); 
        setCell(cell, tempCell.getCellStyle(), sumCntRatio.setScale(0,BigDecimal.ROUND_HALF_UP)+"%"); 
         
    }
    /**
     * 分析選項:除了無以外其他
     * @param rptData
     */
    public void doDtlReport2(AnnuityStatistics3Case rptData) {    	
    	tempSheet = workbook.getSheetAt(0); 
    	workbook.createSheet();
        HSSFSheet sheet = workbook.getSheetAt(1); 
        copyRows(workbook.getSheetName(0), workbook.getSheetName(1), 0, 4, 0, 100);//coprow0~100
        HSSFRow row = sheet.getRow(0); 
        
        HSSFCell cell = sheet.getRow(0).getCell((short) 0); 
        setCell(cell, cell.getCellStyle(), rptData.getQryPayCodeStr()); //更改複製來的(轉換年金給付)
        
        cell = sheet.getRow(1).getCell((short) 0); 
        setCell(cell, cell.getCellStyle(), rptData.getQryTypStr());
        
        cell = sheet.getRow(3).getCell((short) 0); 
        setCell(cell, cell.getCellStyle(), rptData.getQryTypStr());
        
        row = incrementRow(sheet, row);//1
        row = incrementRow(sheet, row);//2
        row = incrementRow(sheet, row);//3
        row = incrementRow(sheet, row);//4
        
        int tmpRow = 0;
        Map<String, BigDecimal> columnSumData = new HashMap<String, BigDecimal>();//column計算合計
        BigDecimal numRangePaycnt = new BigDecimal(0);//件數合計
        BigDecimal numRangeCntratio = new BigDecimal(0);//比例合計
        
		for(int i=0; i<rptData.getDtlCase().size(); i++) {//級距(新增幾個就有幾個size)			
        	AnnuityStatistics3DtlCase dtlCase = rptData.getDtlCase().get(i);
        	row = incrementRow(sheet, row);//開始時先跳行(5)
        	//因為要回來畫表頭，所以先將目前的row存成tmp
        	tmpRow = row.getRowNum();
        	   
            int startC = 3;//header start(第三列)
            BigDecimal sumPayCnt = new BigDecimal(0);//左邊全體件數
            BigDecimal sumCntRatio = new BigDecimal(0);//左邊全體比例
            for(int j=0; j<dtlCase.getDtlList().size();j++) {//類別(column)
            	AnnuityStatistics3MetaDtlCase metaData = dtlCase.getDtlList().get(j);            	
            	if(i==0) {
            		tempCell = tempSheet.getRow(3).getCell((short) 1);//繪表頭的格式
                	row = sheet.getRow(3);//切到第三列畫表頭(跳過"全體")
                	cell = row.createCell((short) startC);//建第三列第startC的column 
            		
	            	//設定第三列第startC的column表頭名稱
	            	if(StringUtils.equals(rptData.getAnalysisSelect(),"S")) {
	            		setCell(cell, tempCell.getCellStyle(), metaData.getSexStr());
	                } else if(StringUtils.equals(rptData.getAnalysisSelect(),"U")) {
	                	setCell(cell, tempCell.getCellStyle(), metaData.getUbType());
	                } else if(StringUtils.equals(rptData.getAnalysisSelect(),"N")) {
	                	setCell(cell, tempCell.getCellStyle(), metaData.getEvtNationTpeStr());
	                } else if(StringUtils.equals(rptData.getAnalysisSelect(),"C")) {
	                	setCell(cell, tempCell.getCellStyle(), metaData.getCipbFmkStr());
	                } else if(StringUtils.equals(rptData.getAnalysisSelect(),"E")) {
	                	setCell(cell, tempCell.getCellStyle(), metaData.getEvTypeStr());
	                }
            	}
            	
            	
            	//取下一個column並合併儲存格
            	cell = row.createCell((short) (startC+1)); 
            	setCell(cell, tempCell.getCellStyle(), "");
            	Region region = new Region();
            	region.setColumnFrom((short) startC);
            	region.setColumnTo((short) (startC+1));
            	region.setRowFrom((short) 3);
            	region.setRowTo((short) 3);
            	sheet.addMergedRegion(region);
            	//設定第四列件數與比例
            	row = sheet.getRow(4);
            	cell = row.createCell((short) startC);
            	tempCell = tempSheet.getRow(4).getCell((short) 1);
            	setCell(cell, tempCell.getCellStyle(), "件數");
            	
            	cell = row.createCell((short) (startC+1)); 
            	tempCell = tempSheet.getRow(4).getCell((short) 2);
            	setCell(cell, tempCell.getCellStyle(), "比例");
            	
            	//計算合計資料(每一行最底下，如果不是null就新增到map，若已存在等下一個級距進來繼續累加)
            	if(columnSumData.get(String.valueOf(j+"paycnt"))==null) {
            		columnSumData.put(String.valueOf(j)+"paycnt", new BigDecimal(metaData.getPayCnt()));
            	}else {
            		columnSumData.put(String.valueOf(j)+"paycnt",columnSumData.get(String.valueOf(j)+"paycnt").add(new BigDecimal(metaData.getPayCnt())));
            	}
            	if(columnSumData.get(String.valueOf(j+"cntratio"))==null) {
            		columnSumData.put(String.valueOf(j)+"cntratio", new BigDecimal(metaData.getCntRatio()));
            	}else {
            		columnSumData.put(String.valueOf(j)+"cntratio",columnSumData.get(String.valueOf(j)+"cntratio").add(new BigDecimal(metaData.getCntRatio())));
            	}
            	
            	//開始寫資料
            	row = sheet.getRow(tmpRow);
            	
            	tempCell = tempSheet.getRow(5).getCell((short) 1); 
                cell = row.createCell((short) startC); 
                setCell(cell, tempCell.getCellStyle(), ReportBase.formatBigDecimalToInteger(new BigDecimal(metaData.getPayCnt()))); 
                sumPayCnt = sumPayCnt.add(new BigDecimal(metaData.getPayCnt()));//件數
                
                tempCell = tempSheet.getRow(5).getCell((short) 2); 
                cell = row.createCell((short) (startC+1)); 
                setCell(cell, tempCell.getCellStyle(), metaData.getCntRatio()+"%"); 
                //一次寫件數與比例，因此下一次直接跳兩格
                startC = startC + 2;
        	}
        	
            tempCell = tempSheet.getRow(5).getCell((short) 0); 
        	cell = row.createCell((short) 0); 
            setCell(cell, tempCell.getCellStyle(), dtlCase.getSpcStart()+"~"+dtlCase.getSpcEnd());//最左列級距
            
            //左邊全體件數
        	tempCell = tempSheet.getRow(5).getCell((short) 1); 
            cell = row.createCell((short) 1); 
            setCell(cell, tempCell.getCellStyle(),  ReportBase.formatBigDecimalToInteger(sumPayCnt));
            numRangePaycnt = numRangePaycnt.add(sumPayCnt);
            
            BigDecimal totalSum = BigDecimal.ZERO;
            if(rptData.getTotalSum() != null && rptData.getTotalSum().intValue()>0) {
            	totalSum = sumPayCnt.divide(rptData.getTotalSum(),4 ,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2);
            }
            //左邊全體比例
            tempCell = tempSheet.getRow(5).getCell((short) 2); 
            cell = row.createCell((short) 2); 
            setCell(cell, tempCell.getCellStyle(), totalSum.toString()+"%");
            numRangeCntratio = numRangeCntratio.add(totalSum);
            
        }
		
        row = incrementRow(sheet, row);
        //合計
        tempCell = tempSheet.getRow(5).getCell((short) 0); 
        cell = row.createCell((short) 0); 
        setCell(cell, tempCell.getCellStyle(), "合計");
        
        int startColumn = 3;//寫底下合計資料
        //一種類別有兩個(ex:男、女各有paycnt與cntratio)，一定會有資料
        for(int i=0;i<columnSumData.size()/2;i++) {
        	//合計資料從map取出每個類別的件數與比例
        	BigDecimal iPaycnt = columnSumData.get(i+"paycnt");
        	BigDecimal iCntratio = columnSumData.get(i+"cntratio");
        	
        	tempCell = tempSheet.getRow(5).getCell((short) 3); 
            cell = row.createCell((short) (startColumn)); 
            setCell(cell, tempCell.getCellStyle(), iPaycnt);
            
            tempCell = tempSheet.getRow(5).getCell((short) 4); 
            cell = row.createCell((short) (startColumn+1)); 
            setCell(cell, tempCell.getCellStyle(), iCntratio.setScale(0 ,BigDecimal.ROUND_HALF_UP) +"%");
            
            
            startColumn = startColumn + 2;
        }
        //合計資料	
        tempCell = tempSheet.getRow(5).getCell((short) 1); 
        cell = row.createCell((short) (1)); 
        setCell(cell, tempCell.getCellStyle(), numRangePaycnt);
        
        tempCell = tempSheet.getRow(5).getCell((short) 2); 
        cell = row.createCell((short) (2)); 
        setCell(cell, tempCell.getCellStyle(), numRangeCntratio.setScale(0 ,BigDecimal.ROUND_HALF_UP)+"%");
        
    }
    
    public ByteArrayOutputStream doReport(AnnuityStatistics3Case rptData) throws Exception {
    	if(StringUtils.equals(rptData.getAnalysisSelect(), "X")) {
    		doDtlReport1(rptData);//不分類
    	} else {
    		doDtlReport2(rptData);//需分類(S、U、N、C、E)
    	}
    	
    	workbook.removeSheetAt(0);
        workbook.getSheetAt(0).setSelected(true); 
        workbook.write(bao); 
        return bao;
    	
    }
}
