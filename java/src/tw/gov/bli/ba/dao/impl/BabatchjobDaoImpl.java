package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BabatchjobDao;
import tw.gov.bli.ba.domain.Babatchjob;
import tw.gov.bli.common.annotation.DaoFieldList;

public class BabatchjobDaoImpl extends SqlMapClientDaoSupport implements BabatchjobDao {
   /**
	* 查詢勞保年金線上產製媒體排程目前佇列中相同條件的數量
	* 
	* @param issuYm   核定年月
	* @param chkDate  核定日期 
	* @param payCode  給付別
	*/
	@DaoFieldList("ISSUYM,CHKDATE,PAYCODE,PROCTYPE")
	public Integer selectBatchJobStatus(String issuYm, String chkDate, String payCode, String procType) {
		HashMap<String, String> map = new HashMap<String, String>();

		map.put("issuYm", issuYm);
		map.put("chkDate", chkDate);
		map.put("payCode", payCode);
		map.put("procType", procType);
		return (Integer) getSqlMapClientTemplate().queryForObject("BABATCHJOB.selectBatchJobStatus", map);

	}
	
	@DaoFieldList("ISSUYM,CHKDATE,PAYCODE,PROCTYPE")
	public Integer selectBatchJobStatusForDisabled(String issuYm, String chkDate, String payCode,String paySeqNo, String procType){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("paySeqNo", paySeqNo);
		map.put("issuYm", issuYm);
		map.put("chkDate", chkDate);
		map.put("payCode", payCode);
		map.put("procType", procType);
		return (Integer) getSqlMapClientTemplate().queryForObject("BABATCHJOB.selectBatchJobDisabledStatus", map);
	}
	/**
	 * 勞保年金線上產製媒體排程將作業計入排程
	 * 
	 * @param issuYm 核定年月
	 * @param chkDate 核定日期
	 * @param payCode 給付別
	 * @param procEmpNo 執行作業人員員工編號
	 * @param procDeptId 執行作業人員單位代碼
	 * @param procIp 執行作業人員IP
	 * @param procBegTime 處裡起始日期時間
	 * @param procType 處理類型
	 * @param status 處理狀態
	 * @param paySeqNo 欄位值
	 */
	public void insertBatchJobM(Babatchjob babatchjob) {
		if (babatchjob != null)
			getSqlMapClientTemplate().insert("BABATCHJOB.insertBaBatchRec", babatchjob);
	}
	/**
	 * 勞保年金線上產製媒體排程將作業-更新排程作業狀態
	 * 
	 * @param baJobId  資料列編號(jobid)
	 * @param nowWestDateTime 處理時間
	 * @param status 處理狀態
	 */
	public void updateBaBatchJobStatus(Babatchjob babatchjob) {
		if (babatchjob != null) {
			if (StringUtils.equals(babatchjob.getStatus(), "R")) {
				getSqlMapClientTemplate().update("BABATCHJOB.updateBaBatchJobStatusBegTime", babatchjob);
			} else {
				getSqlMapClientTemplate().update("BABATCHJOB.updateBaBatchJobStatusEndTime", babatchjob);
			}
		}
	}
	
	/**
	 * 勞保年金線上產製媒體排程將作業-更新排程作業狀態
	 * 
	 * @param baJobId  資料列編號(jobid)
	 * @param nowWestDateTime 處理時間
	 * @param status 處理狀態
	 */
	public void updateBaBatchJobStatusRpt10(Babatchjob babatchjob) {
		if (babatchjob != null) {
			getSqlMapClientTemplate().update("BABATCHJOB.updateBaBatchJobStatusRpt10", babatchjob);

		}
	}
	/**
	 * 取出勞保年金媒體作業目前要處理的工作
	 * 
	 * @param baJobId 資料列編號(JOBID)
	 * 
	 */
	public Babatchjob getBaBatchJobData(String bajobId) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("bajobId", bajobId);
		return (Babatchjob) getSqlMapClientTemplate().queryForObject("BABATCHJOB.selectBaBatchJobData", map);
	}
	/**
	 * 產生媒體檔案(call storeprocedure)
	 * 
	 * @param batchJobId 資料列編號(JOBID)
	 * @param issuTyp	   核付處理類別
	 * @param issuYm     核定年月
	 * @param chkDate    核定日期
	 * @param payCode	   給付別
	 * @param mtestMk	   處理註記
	 * @param procEmpNo  執行作業人員員工編號
	 * @param procDeptId 執行作業人員單位代碼
	 * @param procIp	   執行作業人員IP
	 * @param paySeqNo   欄位值：1為第一次出媒體
	 * @return
	 */
	
	public String genBaPayFile(String batchJobId, String issuTyp, String issuYm, String chkDate, String payCode, String mtestMk, String procEmpNo, String procDeptId, String procIp, String paySeqNo, String procType) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String outFlag = null;
		map.put("v_i_issutyp", issuTyp);
		map.put("v_i_proctype", procType);
		map.put("v_i_issuym", issuYm);
		map.put("v_i_chkdate", chkDate);
		map.put("v_i_paycode", payCode);
		map.put("v_i_mtestmk", mtestMk);
		map.put("v_i_procempno", procEmpNo);
		map.put("v_i_procdeptid", procDeptId);
		map.put("v_i_procip", procIp);
		map.put("v_i_payseqno", paySeqNo);
		map.put("v_i_bajobid", batchJobId);
		//map.put("v_o_flag", outFlag); 20170522 因應PROCEDURE修改不取回傳值
	
		getSqlMapClientTemplate().queryForObject("BABATCHJOB.genBaPayFile", map);
		outFlag = "0";
		return outFlag;

	}
	/**
	 * 查詢年金線上產製媒體排程目前進度
	 * 
	 * @param procType 處理類型
	 * @param issuYm   核定年月  
	 * @param chkDate  核定日期
	 * @param payCode  給付別
	 * @param paySeqNo 欄位值預設為1
	 */
	public List<Babatchjob> selectNowProgressSteps(String procType, String issuYm, String chkDate, String payCode, String paySeqNo) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("procType", procType);
		map.put("issuYm", issuYm);
		map.put("chkDate", chkDate);
		map.put("payCode", payCode);
		map.put("paySeqNo", paySeqNo);
		return (List<Babatchjob>) getSqlMapClientTemplate().queryForList("BABATCHJOB.selectNowProgressSteps", map);

	}
	
	/**
     * 依傳入條件取得 勞保年金批次作業檔(<code>BABATCHJOB</code>) 查詢該給付別於當次核定年月，是否已經執行過。 for 老年 遺屬 批次月處理作業
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param procType 月處理類型(2:批次月試核 3:第一次批次月核定 4:第二次批次月核定)
     * @return <code>BigDecimal</code>
     */
    public BigDecimal selectCountCheckForBjMonthBatchBy(String payCode, String issuYm, String procType) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("procType", procType);
        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BABATCHJOB.selectCountCheckForBjMonthBatchBy", map);
    }
    
    /**
     * 依傳入條件取得 勞保年金批次作業檔(<code>BABATCHJOB</code>) 查詢該給付別於當次核定年月，是否已經執行過。 for 失能 批次月處理作業
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param procType 月處理類型(2:批次月試核 3:第一次批次月核定 4:第二次批次月核定)
     * @param paySeqNo 失能年金(35、38案)欄位值為1、失能年金(36案)欄位值為2(其他給付別不用)
     * @return <code>BigDecimal</code>
     */
    public BigDecimal selectCountCheckForBjMonthBatchKBy(String payCode, String issuYm, String procType, String paySeqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("procType", procType);
        map.put("paySeqNo", paySeqNo);
        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BABATCHJOB.selectCountCheckForBjMonthBatchKBy", map);
    }
    
    /**
     * 依傳入條件取得 勞保年金批次作業檔(<code>BABATCHJOB</code>)  資料清單 老年遺屬
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param procType 月處理類型(2:批次月試核 3:第一次批次月核定 4:第二次批次月核定)
     * @return 內含 <code>Babatchjob</code> 物件的 List
     */
    public List<Babatchjob> selectMonthBatchQueryDataListBy(String payCode, String issuYm, String baJobIdDate, String procType) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }
        if (StringUtils.isNotBlank(issuYm)) {
            map.put("issuYm", issuYm);
        }
        if (StringUtils.isNotBlank(baJobIdDate)) {
            map.put("baJobIdDate", baJobIdDate);
        }
        if (StringUtils.isNotBlank(procType)) {
            map.put("procType", procType);
        }
      
        return getSqlMapClientTemplate().queryForList("BABATCHJOB.selectMonthBatchQueryDataListBy", map);
    }
    
    /**
     * 依傳入條件取得 勞保年金批次作業檔(<code>BABATCHJOB</code>) 資料清單 失能
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param procType 月處理類型(2:批次月試核 3:第一次批次月核定 4:第二次批次月核定)
     * @param paySeqNo 失能年金(35、38案)欄位值為1、失能年金(36案)欄位值為2(其他給付別不用)
     * @return 內含 <code>Babatchjob</code> 物件的 List
     */
    public List<Babatchjob> selectMonthBatchKQueryDataListBy(String payCode, String issuYm, String baJobIdDate, String procType, String paySeqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }
        if (StringUtils.isNotBlank(issuYm)) {
            map.put("issuYm", issuYm);
        }
        if (StringUtils.isNotBlank(baJobIdDate)) {
            map.put("baJobIdDate", baJobIdDate);
        }
        if (StringUtils.isNotBlank(procType)) {
            map.put("procType", procType);
        }
        if (StringUtils.isNotBlank(paySeqNo)) {
            map.put("paySeqNo", paySeqNo);
        }
      
        return getSqlMapClientTemplate().queryForList("BABATCHJOB.selectMonthBatchKQueryDataListBy", map);
    }
    
    /**
     * 新增 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 資料
     * 
     * @param baonlinejob 給付主檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertBabatchjobDataForMonthBatch(Babatchjob babatchjob) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BABATCHJOB.insertBabatchjobDataForMonthBatch", babatchjob);
    }
    
    /**
     * 新增 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 資料
     * 
     * @param baonlinejob 給付主檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertBabatchjobDataForMonthBatchK(Babatchjob babatchjob) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BABATCHJOB.insertBabatchjobDataForMonthBatchK", babatchjob);
    }

    /**
   	* 查詢勞核付明細表排程目前佇列中相同條件的狀態
   	* 
   	* @param issuYm   核定年月
   	* @param chkDate  核定日期 
   	* @param payCode  給付別
   	* @param procType 處理類別
   	* @param paySeqNo 傳入值(35,38:1;36:2)
   	* 
   	* @return Babatchjob
   	*/
	public Babatchjob doScheduleBatchJobStatus(String issuYm, String chkDate, String payCode, String procType, String paySeqNo) {
		HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(chkDate)) {
            map.put("issuYm", issuYm);
        }
        if (StringUtils.isNotBlank(chkDate)) {
            map.put("chkDate", chkDate);
        }
        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }
        if (StringUtils.isNotBlank(procType)) {
            map.put("procType", procType);
        }
        if (StringUtils.isNotBlank(paySeqNo)) {
            map.put("paySeqNo", paySeqNo);
        }
      
        return (Babatchjob) getSqlMapClientTemplate().queryForObject("BABATCHJOB.selectScheduleBatchJobStatus", map);
	}
	
	 /**
   	* 查詢勞核付明細表排程目前佇列中相同條件的狀態
   	* 
   	* @param issuYm   核定年月
   	* @param chkDate  核定日期 
   	* @param payCode  給付別
   	* @param procType 處理類別
   	* @param paySeqNo 傳入值(35,38:1;36:2)
   	* 
   	* @return Babatchjob
   	*/
	public Babatchjob doScheduleBatchJobStatusRpt10(String issuYm, String chkDate, String payCode, String procType, String paySeqNo) {
		HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(chkDate)) {
            map.put("issuYm", issuYm);
        }
        if (StringUtils.isNotBlank(chkDate)) {
            map.put("chkDate", chkDate);
        }
        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }
        if (StringUtils.isNotBlank(procType)) {
            map.put("procType", procType);
        }
        if (StringUtils.isNotBlank(paySeqNo)) {
            map.put("paySeqNo", paySeqNo);
        }
      
        return (Babatchjob) getSqlMapClientTemplate().queryForObject("BABATCHJOB.selectScheduleBatchJobStatusRpt10", map);
	}
	
    /**
     * 查詢 Procedure 排程目前進度
     * 
     * @param procType  處理類型
     * @param startDate 處理起始日期  
     * @param endDate   處理結束日期
     */
    public List<Babatchjob> selectProcedureProgressSteps(String procType, String startDate, String endDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("procType", procType);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        
        return (List<Babatchjob>) getSqlMapClientTemplate().queryForList("BABATCHJOB.selectProcedureProgressSteps", map);

    }      
    /**
     * 查詢 處理年月是否已經存在BABATCHJOB
     * 
     * @param issuYm(西元)
     * @return
     */
    public List<Babatchjob> selectBatchJob12Status(String issuYm) {
    	HashMap<String, String> map = new HashMap<String, String>();
        map.put("issuYm", issuYm);
        return (List<Babatchjob>) getSqlMapClientTemplate().queryForList("BABATCHJOB.selectBatchJob12Status", map);

    }
    
    /**
     * 呼叫PKG_BANSF_01.SP_BANSF
     * 
     * @param jobId
     * @param issuYm (西元)
     * @return
     */
	public String callSpBansf(String jobId, String issuYm) {
		HashMap<String, String> map = new HashMap<String, String>();
		String outFlag = null;
		String outMsg = null;
		map.put("v_i_jobid", jobId);
		map.put("v_i_issuym", issuYm);
		map.put("v_o_rtncode", outMsg); 
		map.put("v_o_rtnflag", outFlag); 
		getSqlMapClientTemplate().queryForObject("BABATCHJOB.callSpBansf", map);
		return map.get("v_o_rtnflag");
	}

	/**
	 * 查詢批次核定函排程目前佇列中相同條件的數量
	 * 
	 * @param issuYm  核定年月
	 * @param payCode  給付別
	 * @param fileName  檔案名稱
	 */
	public Babatchjob doQueryBatchJobStatusforOtherRpt05Action(String issuYm, String payCode, String fileName) {
		HashMap<String, String> map = new HashMap<String, String>();

		map.put("issuYm", issuYm);
		map.put("payCode", payCode);
		map.put("fileName", fileName);
		
		return (Babatchjob) getSqlMapClientTemplate().queryForObject("BABATCHJOB.doQueryBatchJobStatusforOtherRpt05Action", map);
	}

	
	public void doScheduleBatchJobforOtherRpt05Action(Babatchjob babatchjob) {
		if (babatchjob != null)
			getSqlMapClientTemplate().insert("BABATCHJOB.insertBaBatchRec", babatchjob);
	}
}
