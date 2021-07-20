package tw.gov.bli.ba.util;

import java.io.StringReader;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;


public class RptTitleUtility {
	//改前 局字樣
	private static final String OLD_TITLE ="勞工保險局";
	//對外民眾報表 局字樣
	private static final String PEOPLE_TITLE = "勞動部勞工保險局";
	//對外民眾報表 局字樣
	private static final String PEOPLE_ORG_GROUP_TITLE = "勞工保險局給付處";
	//對內報表 局字樣
    private static final String ORG_TITLE = "勞保局";
    
	//對應組室別 給付處 之對應
    //改前
    private static final String OLD_GROUP = "給付處";
    //改後
    private static final String OLDAGE_GROUP = "普通事故給付組";
    private static final String DISABLED_SURVIVOR_GROUP = "職業災害給付組";
    //改前
    private static final String OLD_GROUP_WORD = "保給核字";
    //改後
    private static final String OLDAGE_GROUP_WORD = "保普核字";
    private static final String DISABLED_SURVIVOR_GROUP_WORD = "保職核字";
    
    //對應科別 OO給付科 之對應 (生老給付科=>老年給付科、傷殘給付科=>失能給付科、死亡給付科=>死亡給付科)
    private static final String OO_PAYMENT_DIVISION = "OO給付科";
    //改前
    private static final String OLD_OLDAGE_PAYMENT_DIVISION = "生老給付科";
    private static final String OLD_DISABLED_PAYMENT_DIVISION = "傷殘給付科";
    private static final String OLD_SURVIVOR_PAYMENT_DIVISION = "死亡給付科";
    //改後
    private static final String OLDAGE_PAYMENT_DIVISION = "老年給付科";
    private static final String DISABLED_PAYMENT_DIVISION = "失能給付科";
    private static final String SURVIVOR_PAYMENT_DIVISION = "死亡給付科";

    //對應科別 給付出納科、出納科 之對應
    //改前
    private static final String OLD_SAFETY_PAYMENT_DIVISION_1 = "給付出納科";
    private static final String OLD_SAFETY_PAYMENT_DIVISION_2 = "出納科";
    private static final String OLD_SAFETY_PAYMENT_DIVISION_3 = "出納";
    //改後
    private static final String SAFETY_PAYMENT_DIVISION = "保險收支科";

    //給付別
    // 給付別 - L - 老年年金
    public static final String BAAPPBASE_RPT_PAYKIND_L = "L";
    // 給付別 - K - 失能年金
    public static final String BAAPPBASE_RPT_PAYKIND_K = "K";
    // 給付別 - S - 遺屬年金
    public static final String BAAPPBASE_RPT_PAYKIND_S = "S";
    
    //改前
    public static final String OLD_CLEAN_DIVISION = "財務處欠費清理科";
    //改後
    public static final String NEW_CLEAN_DIVISION = "保費組欠費清理科";
    
    //改後
    public static final String RPT05_FOOTER_STR = "(留　用)";
    
    // 組改日期
    public static final int RPT_DATE_CONTROL = Integer.parseInt(ConstantKey.ORG_CHANGE_DATE);
    // 核付日期 控制日期
    public static final int RPT_DATE = 20140301;
    
    /**
     * 財務處欠費清理科 轉換 保費組欠費清理科<br>
     * @param dateControl 字串
     * @return
     */
    public static String getCleanDivisionTitle(String dateControl) {
    	if(StringUtils.isBlank(dateControl)){
    		dateControl = DateUtility.getNowWestDate();
    	}
        if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        		return OLD_CLEAN_DIVISION;
        }else{
        		return NEW_CLEAN_DIVISION;
        }
    }
    
    /**
     * 勞工保險局給付處 轉換 對外<br>
     * @param dateControl 字串
     * @return
     */
    public static String getOrgGroupTitle(String dateControl) {
    	if(StringUtils.isBlank(dateControl)){
    		dateControl = DateUtility.getNowWestDate();
    	}
        if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        		return PEOPLE_ORG_GROUP_TITLE;
        }else{
        		return PEOPLE_TITLE;
        }
    }
    
    /**
     * 勞工保險局 轉換 對外<br>
     * @param dateControl 字串
     * @return
     */
    public static String getPeopleTitle(String dateControl) {
    	if(StringUtils.isBlank(dateControl)){
    		dateControl = DateUtility.getNowWestDate();
    	}
        if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        		return OLD_TITLE;
        }else{
        		return PEOPLE_TITLE;
        }
    }
    
    /**
     * 勞工保險局 轉換 對內<br>
     * @param dateControl 字串
     * @return
     */
    public static String getOrgTitle(String dateControl) {
    	if(StringUtils.isBlank(dateControl)){
    		dateControl = DateUtility.getNowWestDate();
    	}
        if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        		return OLD_TITLE;
        }else{
        		return ORG_TITLE;
        }
    }
    
    /**
     * 勞工保險局 轉換 For MonthlyRpt05<br>
     * @param rptDate 字串
     * @return
     */
    public static String getRpt05Title(String dateControl) {
    	if(StringUtils.isBlank(dateControl)){
    		dateControl = DateUtility.getNowWestDate();
    	}
        if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        		return OLD_TITLE;
        }else{
        		return PEOPLE_TITLE;
        }
    }
    
    /**
     * 勞工保險局 轉換 For MonthlyRpt05 頁腳 留用 字串<br>
     * @param rptDate 字串
     * @return
     */
    public static String getRpt05FooterStr(String dateControl) {
    	if(StringUtils.isBlank(dateControl)){
    		dateControl = DateUtility.getNowWestDate();
    	}
        if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        		return "";
        }else{
        		return RPT05_FOOTER_STR;
        }
    }
    
    /**
     * 給付出納科 轉換<br>
     * @param dateControl 字串
     * @return
     */
    public static String getSafetyPaymentTitle1(String dateControl) {
    	if(StringUtils.isBlank(dateControl)){
    		dateControl = DateUtility.getNowWestDate();
    	}
        if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        		return OLD_SAFETY_PAYMENT_DIVISION_1;
        }else{
        		return SAFETY_PAYMENT_DIVISION;
        }
    }
    
    /**
     * 出納科 轉換<br>
     * @param dateControl 字串
     * @return
     */
    public static String getSafetyPaymentTitle2(String dateControl) {
    	if(StringUtils.isBlank(dateControl)){
    		dateControl = DateUtility.getNowWestDate();
    	}
        if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        		return OLD_SAFETY_PAYMENT_DIVISION_2;
        }else{
        		return SAFETY_PAYMENT_DIVISION;
        }
    }
    
    /**
     * 出納 轉換<br>
     * @param dateControl 字串
     * @return
     */
    public static String getSafetyPaymentTitle3(String dateControl) {
    	if(StringUtils.isBlank(dateControl)){
    		dateControl = DateUtility.getNowWestDate();
    	}
        if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        		return OLD_SAFETY_PAYMENT_DIVISION_3;
        }else{
        		return SAFETY_PAYMENT_DIVISION;
        }
    }

    /**
     * 組改後對應組別室 轉換<br>
     * @param payCode 給付別
     * @param dateControl 字串
     * @return
     */
    public static String getGroupsTitle(String payCode, String dateControl) {
    	if(StringUtils.isBlank(dateControl)){
    		dateControl = DateUtility.getNowWestDate();
    	}
    	
        if(payCode.equals(BAAPPBASE_RPT_PAYKIND_L)){
        	if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        		return OLD_GROUP;
        	}else{
        		return OLDAGE_GROUP;
        	}
        }else if(payCode.equals(BAAPPBASE_RPT_PAYKIND_K) || payCode.equals(BAAPPBASE_RPT_PAYKIND_S)){
        	if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        		return OLD_GROUP;
        	}else{
        		return DISABLED_SURVIVOR_GROUP;
        	}
        }else{
        	return "";
        }
    }
    
    /**
     * 組改後對應組別室 轉換<br>
     * @param payCode 給付別
     * @param dateControl 字串
     * @return
     */
    public static String getGroupsTitleForRpt05(String payCode, String dateControl) {
    	if(StringUtils.isBlank(dateControl)){
    		dateControl = DateUtility.getNowWestDate();
    	}
    	
        if(payCode.equals(BAAPPBASE_RPT_PAYKIND_L)){
        	if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        		return OLD_GROUP;
        	}else{
        		return OLDAGE_GROUP;
        	}
        }else if(payCode.equals(BAAPPBASE_RPT_PAYKIND_K) || payCode.equals(BAAPPBASE_RPT_PAYKIND_S)){
        	if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        		return OLD_GROUP;
        	}else{
        		return DISABLED_SURVIVOR_GROUP;
        	}
        }else{
        	return "";
        }
    }
    
    /**
     * 組改後對應oo核字 所帶字別  轉換<br>
     * @param payCode 給付別
     * @param dateControl 字串
     * @return
     */
    public static String getGroupsWordTitle(String payCode, String dateControl) {
    	if(StringUtils.isBlank(dateControl)){
    		dateControl = DateUtility.getNowWestDate();
    	}
    	
        if(payCode.equals(BAAPPBASE_RPT_PAYKIND_L)){
        	if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        		return OLD_GROUP_WORD;
        	}else{
        		return OLDAGE_GROUP_WORD;
        	}
        }else if(payCode.equals(BAAPPBASE_RPT_PAYKIND_K) || payCode.equals(BAAPPBASE_RPT_PAYKIND_S)){
        	if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        		return OLD_GROUP_WORD;
        	}else{
        		return DISABLED_SURVIVOR_GROUP_WORD;
        	}
        }else{
        	return "";
        }
    }
    
    /**
     * 組改後對應oo核字 所帶字別  轉換<br>
     * @param payCode 給付別
     * @param dateControl 字串
     * @return
     */
    public static String getGroupsWordTitleForAplpayDate(String payCode, String dateControl) {
    	if(StringUtils.isBlank(dateControl)){
    		dateControl = DateUtility.getNowWestDate();
    	}
    	
        if(payCode.equals(BAAPPBASE_RPT_PAYKIND_L)){
        	if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        		return OLD_GROUP_WORD;
        	}else{
        		return OLDAGE_GROUP_WORD;
        	}
        }else if(payCode.equals(BAAPPBASE_RPT_PAYKIND_K) || payCode.equals(BAAPPBASE_RPT_PAYKIND_S)){
        	if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        		return OLD_GROUP_WORD;
        	}else{
        		return DISABLED_SURVIVOR_GROUP_WORD;
        	}
        }else{
        	return "";
        }
    }
    
    /**
     * OO給付科 轉換<br>
     * @param payCode 給付別
     * @param dateControl 字串
     * @return
     */
    public static String getOODivisionTitle(String payCode, String dateControl) {
    	if(StringUtils.isBlank(dateControl)){
    		dateControl = DateUtility.getNowWestDate();
    	}
    	
        if(payCode.equals(BAAPPBASE_RPT_PAYKIND_L)){
        	if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        		return OO_PAYMENT_DIVISION;
        	}else{
        		return OLDAGE_PAYMENT_DIVISION;
        	}
        }else if(payCode.equals(BAAPPBASE_RPT_PAYKIND_K)){
        	if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        		return OO_PAYMENT_DIVISION;
        	}else{
        		return DISABLED_PAYMENT_DIVISION;
        	}
        }else if(payCode.equals(BAAPPBASE_RPT_PAYKIND_S)){
        	if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        		return OO_PAYMENT_DIVISION;
        	}else{
        		return SURVIVOR_PAYMENT_DIVISION;
        	}
        }else{
        	return "";
        }
    }
    
    /**
     * 各給付科 轉換<br>
     * @param payCode 給付別
     * @param dateControl 字串
     * @return
     */
    public static String getDivisionTitle(String payCode, String dateControl) {
    	if(StringUtils.isBlank(dateControl)){
    		dateControl = DateUtility.getNowWestDate();
    	}
    	
        if(payCode.equals(BAAPPBASE_RPT_PAYKIND_L)){
        	if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        		return OLD_OLDAGE_PAYMENT_DIVISION;
        	}else{
        		return OLDAGE_PAYMENT_DIVISION;
        	}
        }else if(payCode.equals(BAAPPBASE_RPT_PAYKIND_K)){
        	if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        		return OLD_DISABLED_PAYMENT_DIVISION;
        	}else{
        		return DISABLED_PAYMENT_DIVISION;
        	}
        }else if(payCode.equals(BAAPPBASE_RPT_PAYKIND_S)){
        	if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        		return OLD_SURVIVOR_PAYMENT_DIVISION;
        	}else{
        		return SURVIVOR_PAYMENT_DIVISION;
        	}
        }else{
        	return "";
        }
    }
    
    /**
     * 各給付科 轉換<br>
     * @param payCode 給付別
     * @param payCodeNum 給付項目
     * @param dateControl 字串
     * @return
     */
    public static String getDivisionTitleForPayCode(String payCode, String payCodeNum, String dateControl) {
    	if(StringUtils.isBlank(dateControl)){
    		dateControl = DateUtility.getNowWestDate();
    	}
    	
        if(payCodeNum.equals("11") || payCodeNum.equals("78")){
        	
        	if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        		if(payCode.equals(BAAPPBASE_RPT_PAYKIND_L)){
        			return OLD_OLDAGE_PAYMENT_DIVISION;
        		}else if(payCode.equals(BAAPPBASE_RPT_PAYKIND_K)){
        			return OLD_DISABLED_PAYMENT_DIVISION;
        		}else if(payCode.equals(BAAPPBASE_RPT_PAYKIND_S)){
        			return OLD_SURVIVOR_PAYMENT_DIVISION;
        		}else{
        			return "";
        		}
        	}else{
        		return "生育給付科";
        	}
        	
        }else if(payCodeNum.equals("41") || payCodeNum.equals("42") || payCodeNum.equals("45") || payCodeNum.equals("46") || payCodeNum.equals("48")){
        	
        	if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        		if(payCode.equals(BAAPPBASE_RPT_PAYKIND_L)){
        			return OLD_OLDAGE_PAYMENT_DIVISION;
        		}else if(payCode.equals(BAAPPBASE_RPT_PAYKIND_K)){
        			return OLD_DISABLED_PAYMENT_DIVISION;
        		}else if(payCode.equals(BAAPPBASE_RPT_PAYKIND_S)){
        			return OLD_SURVIVOR_PAYMENT_DIVISION;
        		}else{
        			return "";
        		}
        	}else{
        		return "老年給付科";
        	}
        	
        }else if(payCodeNum.equals("71") || payCodeNum.equals("73") || payCodeNum.equals("75")){
        	    if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        			if(payCode.equals(BAAPPBASE_RPT_PAYKIND_L)){
        				return OLD_OLDAGE_PAYMENT_DIVISION;
        			}else if(payCode.equals(BAAPPBASE_RPT_PAYKIND_K)){
        				return OLD_DISABLED_PAYMENT_DIVISION;
        			}else if(payCode.equals(BAAPPBASE_RPT_PAYKIND_S)){
        				return OLD_SURVIVOR_PAYMENT_DIVISION;
        			}else{
        				return "";
        			}
                }else{
        		    return "就業保險給付科";
                }
        }else if(payCodeNum.equals("21") || payCodeNum.equals("22")){
        	
    	    if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
    			if(payCode.equals(BAAPPBASE_RPT_PAYKIND_L)){
    				return OLD_OLDAGE_PAYMENT_DIVISION;
    			}else if(payCode.equals(BAAPPBASE_RPT_PAYKIND_K)){
    				return OLD_DISABLED_PAYMENT_DIVISION;
    			}else if(payCode.equals(BAAPPBASE_RPT_PAYKIND_S)){
    				return OLD_SURVIVOR_PAYMENT_DIVISION;
    			}else{
    			return "";
    			}
            }else{
    		    return "傷病給付科";
            }
    	    
        }else if(payCodeNum.equals("31") || payCodeNum.equals("32") || payCodeNum.equals("35") || payCodeNum.equals("36") || payCodeNum.equals("37") || payCodeNum.equals("38") || payCodeNum.equals("39")){
        	
    	    if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
    			if(payCode.equals(BAAPPBASE_RPT_PAYKIND_L)){
    				return OLD_OLDAGE_PAYMENT_DIVISION;
    			}else if(payCode.equals(BAAPPBASE_RPT_PAYKIND_K)){
    				return OLD_DISABLED_PAYMENT_DIVISION;
    			}else if(payCode.equals(BAAPPBASE_RPT_PAYKIND_S)){
    				return OLD_SURVIVOR_PAYMENT_DIVISION;
    			}else{
    				return "";
    			}
            }else{
    		    return "失能給付科";
            }
    	    
        }else if(payCodeNum.equals("51") || payCodeNum.equals("52") || payCodeNum.equals("55") || payCodeNum.equals("56") || payCodeNum.equals("57") || payCodeNum.equals("59") || payCodeNum.equals("61")){
        	
    	    if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
    			if(payCode.equals(BAAPPBASE_RPT_PAYKIND_L)){
    				return OLD_OLDAGE_PAYMENT_DIVISION;
    			}else if(payCode.equals(BAAPPBASE_RPT_PAYKIND_K)){
    				return OLD_DISABLED_PAYMENT_DIVISION;
    			}else if(payCode.equals(BAAPPBASE_RPT_PAYKIND_S)){
    				return OLD_SURVIVOR_PAYMENT_DIVISION;
    			}else{
    				return "";
    			}
            }else{
    		    return "死亡給付科";
            }
    	    
        }else if(payCodeNum.equals("82") || payCodeNum.equals("84") || payCodeNum.equals("85") || payCodeNum.equals("91") || payCodeNum.equals("92")){
        	
    	    if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
    			if(payCode.equals(BAAPPBASE_RPT_PAYKIND_L)){
    				return OLD_OLDAGE_PAYMENT_DIVISION;
    			}else if(payCode.equals(BAAPPBASE_RPT_PAYKIND_K)){
    				return OLD_DISABLED_PAYMENT_DIVISION;
    			}else if(payCode.equals(BAAPPBASE_RPT_PAYKIND_S)){
    				return OLD_SURVIVOR_PAYMENT_DIVISION;
    			}else{
    				return "";
    			}
            }else{
    		    return "醫療給付科";
            }
    	    
        }else{
        	return "";
        }
    }
    
    /**
     * 生老給付科 轉換<br>
     * @param dateControl 字串
     * @return
     */
    public static String getOldAgePaymentTitle(String dateControl) {
    	if(StringUtils.isBlank(dateControl)){
    		dateControl = DateUtility.getNowWestDate();
    	}
    	
        if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        		return OLD_OLDAGE_PAYMENT_DIVISION;
        }else{
        		return OLDAGE_PAYMENT_DIVISION;
        }
    }
    
    /**
     * 生老給付科 轉換<br>
     * @param dateControl 字串
     * @return
     */
    public static String getOldAgePaymentTitleForRpt05(String dateControl) {
    	if(StringUtils.isBlank(dateControl)){
    		dateControl = DateUtility.getNowWestDate();
    	}
    	
        if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        		return OLD_OLDAGE_PAYMENT_DIVISION;
        }else{
        		return OLDAGE_PAYMENT_DIVISION;
        }
    }
    
    /**
     * 傷殘給付科 轉換<br>
     * @param dateControl 字串
     * @return
     */
    public static String getDisabledPaymentTitle(String dateControl) {
    	if(StringUtils.isBlank(dateControl)){
    		dateControl = DateUtility.getNowWestDate();
    	}
    	
        if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        		return OLD_DISABLED_PAYMENT_DIVISION;
        }else{
        		return DISABLED_PAYMENT_DIVISION;
        }
    }
    
    /**
     * 傷殘給付科 轉換<br>
     * @param dateControl 字串
     * @return
     */
    public static String getDisabledPaymentTitleForRpt05(String dateControl) {
    	if(StringUtils.isBlank(dateControl)){
    		dateControl = DateUtility.getNowWestDate();
    	}
    	
        if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        		return OLD_DISABLED_PAYMENT_DIVISION;
        }else{
        		return DISABLED_PAYMENT_DIVISION;
        }
    }
    
    /**
     * 死亡給付科 轉換<br>
     * @param dateControl 字串
     * @return
     */
    public static String getSurvivorPaymentTitle(String dateControl) {
    	if(StringUtils.isBlank(dateControl)){
    		dateControl = DateUtility.getNowWestDate();
    	}
    	
        if(Integer.parseInt(dateControl) < RPT_DATE_CONTROL){
        		return OLD_SURVIVOR_PAYMENT_DIVISION;
        }else{
        		return SURVIVOR_PAYMENT_DIVISION;
        }
    }

    /**
     * 是否顯示改組後名稱
     * 
     * @return
     */
    public static boolean isOrgChg(String date) {
        if (StringUtils.isBlank(date)) {
            date = DateUtility.getNowWestDate();
        }
        if (NumberUtils.toInt(date) >= RPT_DATE_CONTROL) {
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * 局長
     * 
     * @return
     */
    public static String getManegerTitle(String date, String maneger) {
        if (isOrgChg(date)) {
            return StringUtils.replaceOnce(maneger, "總經理", "局長");
        }
        else {
            return maneger;
        }
    }
    
    public static String getPaymentTile(String payCodeNum){
    	return "勞保";
    	
    }
    public static String getPaymentTileForPayment(String payCodeNum){
    	return "勞工";
    	
    }
    
}
