package tw.gov.bli.ba.bj.ajax;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.ba.domain.Baproceduredtl;
import tw.gov.bli.ba.services.BjAjaxService;
import tw.gov.bli.ba.util.DateUtility;


/**
 * Ajax Service for 線上產製媒體作業
 * 
 * @author Zehua
 */
public class BjCommonAjax {
   private static Log log = LogFactory.getLog(BjCommonAjax.class);

   private BjAjaxService bjAjaxService;
   /**
    * 依傳入條件判斷是不是於月核核定日期 for 老年、失能、遺屬年金
    * 
    * @param issuYm 核定年月
    * @param chkDate 核定日期
    * @return
    */
   public boolean getCheckChkdate(String payCode, String issuYm, String chkDate) {
       log.debug("執行 BjAjaxService.getCheckChkdate(" + issuYm + "," + chkDate + ") ...");
       return bjAjaxService.getCheckChkdate(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate));
   }
   
   /**
    * 依傳入條件取得 procedure 的參數
    * 
    * @param procName 程式名稱
    * @return
    */
   public List<Baproceduredtl> getParameters(String procName) {
       log.debug("執行 BjAjaxService.getParameters(" + procName + ") ...");
       return bjAjaxService.getParameters(procName);
   }   
   
	public void setBjAjaxService(BjAjaxService bjAjaxService) {
		this.bjAjaxService = bjAjaxService;
	}
	public String getPaymentDates(String j, String paymentBegDate){
		int i = Integer.parseInt(j) + 1;
		return i+DateUtility.calMonth(paymentBegDate, i);
	}
   
}
