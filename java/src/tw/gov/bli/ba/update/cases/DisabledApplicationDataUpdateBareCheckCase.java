package tw.gov.bli.ba.update.cases;

import java.io.Serializable;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 失能年金案件資料更正 - 重新審核失能程度資料
 * 
 * @author Noctis
 */
public class DisabledApplicationDataUpdateBareCheckCase implements Serializable {
    // BARECHECK
	private String apNo; //受理編號
	private String seqNo; //受款人序
	private String reChkYm;     // 重新查核失能程度年月
    private String isreChk;     // 是否複檢
    private String reChkStatus; // 重新查核狀態
    private String reChkResult; // 重新查核結果
    private String comReChkYm;  // 完成重新查核年月
    private String isreChkStr;     // 是否複檢 中文
    private String reChkStatusStr;     // 重新查核狀態 中文
    private String reChkResultStr; // 重新查核結果 中文
    private String updUser;
    private String updTime;
    
    //轉換 重新查核結果
//    public String getReChkResultStr() {
//    	if(reChkResult.equals("P1")){
//    		reChkResultStr = "P1-植物人無需重新查核";
//    	}else if(reChkResult.equals("P2")){
//    		reChkResultStr = "P2-已達老年年金請領資格無需重新查核";
//    	}else if(reChkResult.equals("F1")){
//    		reChkResultStr = "F1-終身無工作能力20大項";
//    	}else if(reChkResult.equals("F2")){
//    		reChkResultStr = "F2-個別化專業評估工作能力減損達70%以上";
//    	}else if(reChkResult.equals("F3")){
//    		reChkResultStr = "F3-符合1-6等級改領一次金";
//    	}else if(reChkResult.equals("F4")){
//    		reChkResultStr = "F4-不符合年金請領資格";
//    	}
//		return reChkResultStr;
//	}
    
    //轉換 重新查核狀態
//    public String getReChkStatusStr() {
//    	if(reChkStatus.equals("1")){
//    		reChkStatusStr = "1-無需重新查核";
//    	}else if(reChkStatus.equals("2")){
//    		reChkStatusStr = "2-完成重新查核";
//    	}
//		return reChkStatusStr;
//	}
    
    //轉換 是否複檢
    public String getIsreChkStr() {
    	if(isreChk.equals("1")){
    		isreChkStr = "是";
    	}else if(isreChk.equals("2")){
    		isreChkStr = "否";
    	}
		return isreChkStr;
	}
    
	public String getReChkYm() {
		return reChkYm;
	}
	public void setReChkYm(String reChkYm) {
		this.reChkYm = reChkYm;
	}
	public String getIsreChk() {
		return isreChk;
	}
	public void setIsreChk(String isreChk) {
		this.isreChk = isreChk;
	}
	public String getReChkStatus() {
		return reChkStatus;
	}
	public void setReChkStatus(String reChkStatus) {
		this.reChkStatus = reChkStatus;
	}
	public String getReChkResult() {
		return reChkResult;
	}
	public void setReChkResult(String reChkResult) {
		this.reChkResult = reChkResult;
	}
	public String getComReChkYm() {
		return comReChkYm;
	}
	public void setComReChkYm(String comReChkYm) {
		this.comReChkYm = comReChkYm;
	}

	public String getApNo() {
		return apNo;
	}

	public void setApNo(String apNo) {
		this.apNo = apNo;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getUpdUser() {
		return updUser;
	}

	public void setUpdUser(String updUser) {
		this.updUser = updUser;
	}

	public String getUpdTime() {
		return updTime;
	}

	public void setUpdTime(String updTime) {
		this.updTime = updTime;
	}

	public String getReChkResultStr() {
		return reChkResultStr;
	}

	public void setReChkResultStr(String reChkResultStr) {
		this.reChkResultStr = reChkResultStr;
	}

	public String getReChkStatusStr() {
		return reChkStatusStr;
	}

	public void setReChkStatusStr(String reChkStatusStr) {
		this.reChkStatusStr = reChkStatusStr;
	}
    
   
}
