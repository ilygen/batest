package tw.gov.bli.ba.util;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang.StringUtils;

import com.lowagie.text.Image;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.Barcode39;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfSmartCopy;
import com.lowagie.text.pdf.PdfStamper;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.helper.PropertyHelper;

/**
 * Report Utility
 * 
 * @author Goston
 */
public class ReportUtility {
    
    public static File[] listPDFFile(String fileName) {       
        return listFile(fileName, "pdf");
    }
    
    public static File[] listEXCELFile(String fileName) {       
        return listFile(fileName, "xls");
    }

    public static File[] listFile(String fileName, String fileExt) {
        File dir = new File(PropertyHelper.getProperty("rpt.path"));
        FileFilter fileFilter = new WildcardFileFilter(fileName + "*." + fileExt);
        File[] fileList = dir.listFiles(fileFilter);
        
        return fileList;
    }
    
    public static File[] listFileAll(String fileName) {
        File dir = new File(PropertyHelper.getProperty("rpt.path"));
        FileFilter fileFilter = new WildcardFileFilter(fileName + "*");
        File[] fileList = dir.listFiles(fileFilter);
        
        return fileList;
    }
    /**
     * PDF 套表
     * 
     * @param templateFile Template 檔
     * @param map <code>HashMap</code> 其 Key 為欄位名稱，Value 為欄位值
     * @param copy <code>PdfSmartCopy</code> Object
     * @return 套表完成之新檔案
     * @throws ReportException
     */
    public static void templateReportForPayment(String barCode1, String barCode2, String barCode3, byte[] templateFile, HashMap<String, String> map, PdfSmartCopy copy) throws Exception {
        if (map == null)
            throw new Exception("套表之欄位名稱及欄位值不得為 null");

        ByteArrayOutputStream outFile = new ByteArrayOutputStream();

        PdfReader template = new PdfReader(templateFile);

        PdfStamper stamp = new PdfStamper(template, outFile);
        AcroFields form = stamp.getAcroFields();

        for (String key : map.keySet()) {
            if (key != null)
                form.setField(key, StringUtils.defaultString(map.get(key)));
        }

        PdfContentByte content = stamp.getOverContent(template.getNumberOfPages());
        Image barCodeImg1 = getBarCodeImage(content ,barCode1, 10, 10, 10, 10);
        barCodeImg1.setAbsolutePosition(50,95);
        barCodeImg1.scaleAbsolute(180,30);
        content.addImage(barCodeImg1);
        
        Image barCodeImg2 = getBarCodeImage(content ,barCode2, 70, 120, 480, 775);
        barCodeImg2.setAbsolutePosition(50,30);
        barCodeImg2.scaleAbsolute(180,30);
        content.addImage(barCodeImg2);
       
        Image barCodeImg3 = getBarCodeImage(content ,barCode3, 70, 120, 480, 775);
        barCodeImg3.setAbsolutePosition(270,95);
        barCodeImg3.scaleAbsolute(180,30);
        content.addImage(barCodeImg3);
//        
        stamp.setFormFlattening(true);
        stamp.close();
        outFile.close();

        template = new PdfReader(outFile.toByteArray());
        copy.addPage(copy.getImportedPage(template, 1));
        
        
       
    }
    
    /**
     * PDF 套表
     * 
     * @param templateFile Template 檔
     * @param map <code>HashMap</code> 其 Key 為欄位名稱，Value 為欄位值
     * @param copy <code>PdfSmartCopy</code> Object
     * @return 套表完成之新檔案
     * @throws ReportException
     */
    public static void templateReportForPaymentDtl(byte[] templateFile, HashMap<String, String> map, PdfSmartCopy copy) throws Exception {
        if (map == null)
            throw new Exception("套表之欄位名稱及欄位值不得為 null");

        ByteArrayOutputStream outFile = new ByteArrayOutputStream();

        PdfReader template = new PdfReader(templateFile);

        PdfStamper stamp = new PdfStamper(template, outFile);
        AcroFields form = stamp.getAcroFields();

        for (String key : map.keySet()) {
            if (key != null)
                form.setField(key, StringUtils.defaultString(map.get(key)));
        }

        
        stamp.setFormFlattening(true);
        stamp.close();
        outFile.close();

        template = new PdfReader(outFile.toByteArray());
        copy.addPage(copy.getImportedPage(template, 1));
       
    }
    
    /**
     * 建立 Barcode 格式為 Barcode39 (不顯示 Barcode 下的文字) for 絕對定位用
     * 
     * @param code Barcode 內容
     * @param codeWidth Barcode 寬度縮圖百分比
     * @param codeHeight Barcode 寬度縮圖百分比
     * @param absoluteX Barcode x軸位置
     * @param absoluteY Barcode y軸位置
     * @return image
     */
    public static Image getBarCodeImage(PdfContentByte cb,String code, float codeWidth, float codeHeight, float absoluteX, float absoluteY) {
        	PdfContentByte codeCb = cb;
            Barcode39 barCode = new Barcode39();
            barCode.setCode(StringUtils.defaultString(code));
            barCode.setStartStopText(false);
            Image image = barCode.createImageWithBarcode(codeCb, null, Color.WHITE);
            image.scalePercent(codeWidth, codeHeight);
            image.setAbsolutePosition(absoluteX, absoluteY);
            return image;
        
    	
    }
    /**
     * PDF 套表
     * 
     * @param templateFile Template 檔
     * @param map <code>HashMap</code> 其 Key 為欄位名稱，Value 為欄位值
     * @param copy <code>PdfSmartCopy</code> Object
     * @return 套表完成之新檔案
     * @throws ReportException
     */
    public static void templateReport(byte[] templateFile, HashMap<String, String> map, PdfSmartCopy copy) throws Exception {
        if (map == null)
            throw new Exception("套表之欄位名稱及欄位值不得為 null");

        ByteArrayOutputStream outFile = new ByteArrayOutputStream();

        PdfReader template = new PdfReader(templateFile);

        PdfStamper stamp = new PdfStamper(template, outFile);
        AcroFields form = stamp.getAcroFields();

        for (String key : map.keySet()) {
            if (key != null)
                form.setField(key, StringUtils.defaultString(map.get(key)));
        }

        stamp.setFormFlattening(true);
        stamp.close();
        outFile.close();

        template = new PdfReader(outFile.toByteArray());
        copy.addPage(copy.getImportedPage(template, 1));
    }

}
