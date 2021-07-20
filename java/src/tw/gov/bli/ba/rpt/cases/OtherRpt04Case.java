package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 受理案件統計表
 * 
 * @author Noctis
 */
public class OtherRpt04Case implements Serializable {
	//page1
	private String payCode;// 給付別
    private String dm;// 本日 本月
    private String minApNo;// 最小apNo
    private String maxApNo;// 最大apNo
    private BigDecimal recCordCount; // 受理件數
    private String orderNo; //sql排序使用no
    
    //page2
    private String crtUser; //員工編號
    private BigDecimal payL1; // L (一般)
    private BigDecimal payL2; // L (網路)
    private BigDecimal payK1; // K (一般)
    private BigDecimal payK2; // K (國併勞)
    private BigDecimal payS1; // S
    private BigDecimal singleDTotal; //員工 每日 合計
    private BigDecimal singleMTotal; //員工 每月 合計
    
    //轉換payCode為中文 For page1
    public String getPayCodeStr() {
    	
    	String payCodeStr = "";
    	
    	if(payCode.equals("L1")){
    		payCodeStr = "L-老年年金(一般)";
    	}else if(payCode.equals("L2")){
    		payCodeStr = "L-老年年金(網路)";
    	}else if(payCode.equals("K1")){
    		payCodeStr = "K-失能年金(一般)";
    	}else if(payCode.equals("K2")){
    		payCodeStr = "K-失能年金(國併勞)";
    	}else if(payCode.equals("S1")){
    		payCodeStr = "S-遺屬年金";
    	}
    	
		return payCodeStr;
	}
    
  //轉換payCode為中文 page1使用 For page2
    public String getPayCodeStr2() {
    	
    	String payCodeStr = "";
    	
    	if(payCode.equals("L1")){
    		payCodeStr = "L (一般)";
    	}else if(payCode.equals("L2")){
    		payCodeStr = "L (網路)";
    	}else if(payCode.equals("K1")){
    		payCodeStr = "K (一般)";
    	}else if(payCode.equals("K2")){
    		payCodeStr = "K (國併勞)";
    	}else if(payCode.equals("S1")){
    		payCodeStr = "S";
    	}
    	
		return payCodeStr;
	}
    
    //轉換dm為中文
    public String getDmStr() {
    	
    	String dmStr = "";
    	
    	if(dm.equals("D")){
    		dmStr = "本日";
    	}else if(dm.equals("M")){
    		dmStr = "本月";
    	}
    	
		return dmStr;
	}
    
    public BigDecimal getPayValue(BigDecimal payValue) {
    	BigDecimal chkValue = null;
    	
    	chkValue = (payValue != null) ? payValue : BigDecimal.ZERO;
    	
    	return chkValue;
    }

	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	public String getDm() {
		return dm;
	}
	public void setDm(String dm) {
		this.dm = dm;
	}
	public String getMinApNo() {
		return minApNo;
	}
	public void setMinApNo(String minApNo) {
		this.minApNo = minApNo;
	}
	public String getMaxApNo() {
		return maxApNo;
	}
	public void setMaxApNo(String maxApNo) {
		this.maxApNo = maxApNo;
	}
	public BigDecimal getRecCordCount() {
		return recCordCount;
	}
	public void setRecCordCount(BigDecimal recCordCount) {
		this.recCordCount = recCordCount;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCrtUser() {
		return crtUser;
	}

	public void setCrtUser(String crtUser) {
		this.crtUser = crtUser;
	}

	public BigDecimal getPayL1() {
		return getPayValue(payL1);
	}

	public void setPayL1(BigDecimal payL1) {
		this.payL1 = payL1;
	}

	public BigDecimal getPayL2() {
		return getPayValue(payL2);
	}

	public void setPayL2(BigDecimal payL2) {
		this.payL2 = payL2;
	}

	public BigDecimal getPayK1() {
		return getPayValue(payK1);
	}

	public void setPayK1(BigDecimal payK1) {
		this.payK1 = payK1;
	}

	public BigDecimal getPayK2() {
		return getPayValue(payK2);
	}

	public void setPayK2(BigDecimal payK2) {
		this.payK2 = payK2;
	}

	public BigDecimal getPayS1() {
		return getPayValue(payS1);
	}

	public void setPayS1(BigDecimal payS1) {
		this.payS1 = payS1;
	}

	public BigDecimal getSingleDTotal() {
		return singleDTotal;
	}

	public void setSingleDTotal(BigDecimal singleDTotal) {
		this.singleDTotal = singleDTotal;
	}

	public BigDecimal getSingleMTotal() {
		return singleMTotal;
	}

	public void setSingleMTotal(BigDecimal singleMTotal) {
		this.singleMTotal = singleMTotal;
	}

}
