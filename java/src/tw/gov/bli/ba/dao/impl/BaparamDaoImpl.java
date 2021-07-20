package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BaparamDao;
import tw.gov.bli.ba.domain.Baparam;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 系統參數檔 (<code>BAPARAM</code>)
 * 
 * @author Goston
 */
@DaoTable("BAPARAM")
public class BaparamDaoImpl extends SqlMapClientDaoSupport implements BaparamDao {
    /**
     * 依傳入的條件取得 系統參數檔 (<code>BAPARAM</code>) 的資料<br>
     * 
     * @param paramTyp 參數類別
     * @param paramGroup 參數種類
     * @param paramCode 參數代號
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    @DaoFieldList("PARAMTYP,PARAMGROUP,PARAMCODE")
    public List<Baparam> selectListBy(String paramTyp, String paramGroup, String paramCode) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(paramTyp))
            map.put("paramTyp", paramTyp);
        if (StringUtils.isNotBlank(paramGroup))
            map.put("paramGroup", paramGroup);
        if (StringUtils.isNotBlank(paramCode))
            map.put("paramCode", paramCode);

        return getSqlMapClientTemplate().queryForList("BAPARAM.selectDataBy", map);
    }

    /**
     * 依傳入的條件取得 系統參數檔 (<code>BAPARAM</code>) 的資料<br>
     * 
     * @param paramTyp 參數類別
     * @param paramGroup 參數種類
     * @param paramCode 參數代號
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    @DaoFieldList("PARAMTYP,PARAMGROUP,PARAMCODE")
    public List<Baparam> selectListByLetter(String paramTyp, String paramGroup, String paramCode) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(paramTyp))
            map.put("paramTyp", paramTyp);
        if (StringUtils.isNotBlank(paramGroup))
            map.put("paramGroup", paramGroup);
        if (StringUtils.isNotBlank(paramCode))
            map.put("paramCode", paramCode);

        return getSqlMapClientTemplate().queryForList("BAPARAM.selectDataByLetter", map);
    }

    /**
     * 依傳入的條件取得 系統參數檔 (<code>BAPARAM</code>) 的資料<br>
     * 
     * @param paramTyp 參數類別
     * @param paramGroup 參數種類
     * @param paramCode 參數代號
     * @return String 物件
     */
    @DaoFieldList("PARAMTYP,PARAMGROUP,PARAMCODE")
    public String selectParamNameBy(String paramTyp, String paramGroup, String paramCode) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(paramTyp))
            map.put("paramTyp", paramTyp);
        if (StringUtils.isNotBlank(paramGroup))
            map.put("paramGroup", paramGroup);
        if (StringUtils.isNotBlank(paramCode))
            map.put("paramCode", paramCode);

        return (String) getSqlMapClientTemplate().queryForObject("BAPARAM.selectParamNameBy", map);
    }

    /**
     * 依傳入的條件取得 系統參數檔 (<code>BAPARAM</code>) 資料的下拉選單<br>
     * PARAMCODE as 下拉選單的Value <br>
     * (PARAMNAME||'-'||PARAMNAME) as 下拉選單的Option<br>
     * 
     * @param paramTyp 參數類別
     * @param paramGroup 參數種類
     * @param paramCode 參數代號
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    @DaoFieldList("PARAMTYP,PARAMGROUP,PARAMCODE")
    public List<Baparam> selectOptionListBy(String paramTyp, String paramGroup, String paramCode) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(paramTyp))
            map.put("paramTyp", paramTyp);
        if (StringUtils.isNotBlank(paramGroup))
            map.put("paramGroup", paramGroup);
        if (StringUtils.isNotBlank(paramCode))
            map.put("paramCode", paramCode);

        return getSqlMapClientTemplate().queryForList("BAPARAM.selectOptionListBy", map);
    }
    
    /**
     * 依傳入的條件取得 系統參數檔 (<code>BAPARAM</code>) 資料的下拉選單<br>
     * PARAMCODE as 下拉選單的Value <br>
     * (PARAMNAME||'-'||PARAMNAME) as 下拉選單的Option<br>
     * 
     * @param paramTyp 參數類別
     * @param paramGroup 參數種類
     * @param paramCode 參數代號
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    @DaoFieldList("PARAMTYP,PARAMGROUP,PARAMCODE")
    public List<Baparam> selectOldOptionListBy(String paramTyp, String paramGroup, String paramCode) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(paramTyp))
            map.put("paramTyp", paramTyp);
        if (StringUtils.isNotBlank(paramGroup))
            map.put("paramGroup", paramGroup);
        if (StringUtils.isNotBlank(paramCode))
            map.put("paramCode", paramCode);

        return getSqlMapClientTemplate().queryForList("BAPARAM.selectOldOptionListBy", map);
    }

    /**
     * 依傳入關係的條件取得 系統參數檔 (<code>BAPARAM</code>) 資料的下拉選單<br>
     * PARAMCODE as 下拉選單的Value <br>
     * (PARAMNAME||'-'||PARAMNAME) as 下拉選單的Option<br>
     * 
     * @param paramTyp 參數類別
     * @param paramGroup 參數種類
     * @param paramCode 參數代號
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    @DaoFieldList("PARAMTYP,PARAMGROUP,PARAMCODE")
    public List<Baparam> selectOptionListByUpdate(String paramTyp, String paramGroup, String paramCode) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(paramTyp))
            map.put("paramTyp", paramTyp);
        if (StringUtils.isNotBlank(paramGroup))
            map.put("paramGroup", paramGroup);
        if (StringUtils.isNotBlank(paramCode))
            map.put("paramCode", paramCode);

        return getSqlMapClientTemplate().queryForList("BAPARAM.selectOptionListByUpdate", map);
    }

    /**
     * 取得 系統參數檔 (<code>BAPARAM</code>) 關係資料的下拉選單<br>
     * PARAMCODE as 下拉選單的Value <br>
     * (PARAMNAME||'-'||PARAMNAME) as 下拉選單的Option<br>
     * 
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    @DaoFieldList(" ")
    public List<Baparam> selectRelationOptionList() {
        return getSqlMapClientTemplate().queryForList("BAPARAM.selectRelationOptionList");
    }

    /**
     * 取得 系統參數檔 (<code>BAPARAM</code>) 關係資料的下拉選單<br>
     * PARAMCODE as 下拉選單的Value <br>
     * (PARAMNAME||'-'||PARAMNAME) as 下拉選單的Option<br>
     * 取得 1 ~ 7 關係的受益人
     * 
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    @DaoFieldList(" ")
    public List<Baparam> selectDisabledPayeeRelationOptionList() {
        return getSqlMapClientTemplate().queryForList("BAPARAM.selectDisabledPayeeRelationOptionList");
    }

    /**
     * 取得 系統參數檔 (<code>BAPARAM</code>) 關係資料的下拉選單<br>
     * PARAMCODE as 下拉選單的Value <br>
     * (PARAMNAME||'-'||PARAMNAME) as 下拉選單的Option<br>
     * 
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    @DaoFieldList(" ")
    public List<Baparam> selectPayeeRelationOptionList() {
        return getSqlMapClientTemplate().queryForList("BAPARAM.selectPayeeRelationOptionList");
    }

    /**
     * 取得 系統參數檔 (<code>BAPARAM</code>) 關係資料的下拉選單<br>
     * PARAMCODE as 下拉選單的Value <br>
     * (PARAMNAME||'-'||PARAMNAME) as 下拉選單的Option<br>
     * 
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    @DaoFieldList(" ")
    public List<Baparam> selectPayeeRelationOptionList2(String typeOne, String typeTwo, String typeThree, String typeFour,String typeFive, Boolean relationStatus) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(typeOne))
            map.put("typeOne", typeOne);
        if (StringUtils.isNotBlank(typeTwo))
            map.put("typeTwo", typeTwo);
        if (StringUtils.isNotBlank(typeThree))
            map.put("typeThree", typeThree);
        if (StringUtils.isNotBlank(typeFour))
            map.put("typeFour", typeFour);
        if (StringUtils.isNotBlank(typeFive))
            map.put("typeFive", typeFive);
        if (relationStatus)
            map.put("relationStatus", "relationStatus");

        return getSqlMapClientTemplate().queryForList("BAPARAM.selectPayeeRelationOptionList2", map);
    }

    /**
     * 取得 系統參數檔 (<code>BAPARAM</code>) 關係資料的下拉選單<br>
     * PARAMCODE as 下拉選單的Value <br>
     * (PARAMNAME||'-'||PARAMNAME) as 下拉選單的Option<br>
     * 
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    @DaoFieldList("APNO,BAAPPBASEID")
    public List<Baparam> selectBenNameOptionList(String apNo, String baappbaseId) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(baappbaseId))
            map.put("baappbaseId", baappbaseId);

        return getSqlMapClientTemplate().queryForList("BAPARAM.selectBenNameOptionList", map);
    }

    /**
     * 依傳入的條件取得 系統參數檔 (<code>BAPARAM</code>) 資料的下拉選單<br>
     * PARAMCODE as 下拉選單的Value <br>
     * (PARAMNAME||'-'||PARAMNAME) as 下拉選單的Option<br>
     * 
     * @param paramTyp 參數類別
     * @param paramGroup 參數種類
     * @param paramCode 參數代號
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    @DaoFieldList("PARAMGROUP")
    public List<Baparam> selectReliefOptionListBy(String reliefKind) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(reliefKind))
            map.put("reliefKind", reliefKind);

        return getSqlMapClientTemplate().queryForList("BAPARAM.selectReliefOptionListBy", map);
    }

    /**
     * 取得 系統參數檔 (<code>BAPARAM</code>) 關係資料的下拉選單<br>
     * PARAMCODE as 下拉選單的Value <br>
     * (PARAMNAME||'-'||PARAMNAME) as 下拉選單的Option<br>
     * 
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    @DaoFieldList(" ")
    public List<Baparam> selectDependantRelationOptionList() {
        return getSqlMapClientTemplate().queryForList("BAPARAM.selectDependantRelationOptionList");
    }

    /**
     * 取得 系統參數檔 (<code>BAPARAM</code>) 結案原因的下拉選單<br>
     * PARAMCODE as 下拉選單的Value <br>
     * PARAMNAME as 下拉選單的Option<br>
     * 
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    @DaoFieldList(" ")
    public List<Baparam> selectDependantCloseOptionList() {
        return getSqlMapClientTemplate().queryForList("BAPARAM.selectDepandantCloseOptionList");
    }

    /**
     * 取得 系統參數檔 (<code>BAPARAM</code>) 申請項目下拉選單 for 遺屬年金案件資料更正<br>
     * 
     * @return
     */
    @DaoFieldList("")
    public List<Baparam> selectSurvivorApplicationDataUpdateApItemList() {
        return getSqlMapClientTemplate().queryForList("BAPARAM.selectSurvivorApplicationDataUpdateApItemList");
    }

    /**
     * 取得 系統參數檔 (<code>BAPARAM</code>) 關係下拉選單 for 複檢費用受理作業<br>
     * 
     * @return
     */
    @DaoFieldList("")
    public List<Baparam> selectReviewFeeReceiptRelationList() {
        return getSqlMapClientTemplate().queryForList("BAPARAM.selectReviewFeeReceiptRelationList");
    }

    /**
     * 取得 系統參數檔 (<code>BAPARAM</code>) 給付方式 for 複檢費用受理作業<br>
     * 
     * @return
     */
    @DaoFieldList("")
    public List<Baparam> selectReviewFeeReceiptPayTypeList() {
        return getSqlMapClientTemplate().queryForList("BAPARAM.selectReviewFeeReceiptPayTypeList");
    }
    
    /**
     * 依傳入的條件取得 系統參數檔 (<code>BAPARAM</code>) 的資料<br>
     * 
     * @param paramTyp 參數類別
     * @param paramGroup 參數種類
     * @param paramCode 參數代號
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    @DaoFieldList("PARAMTYP,PARAMGROUP,PARAMCODE")
    public List<Baparam> selectListByBaban(String paramTyp, String paramGroup, String paramCode) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(paramTyp))
            map.put("paramTyp", paramTyp);
        if (StringUtils.isNotBlank(paramGroup))
            map.put("paramGroup", paramGroup);
        if (StringUtils.isNotBlank(paramCode))
            map.put("paramCode", paramCode);

        return getSqlMapClientTemplate().queryForList("BAPARAM.selectDataByBaban", map);
    }
    
    /**
     * 依傳入的條件取得 系統參數檔 (<code>BAPARAM</code>) 的資料<br>
     * @param paramCode 參數代號
     */
    @DaoFieldList("PARAMTYP")
    public BigDecimal getParaNameData(String paramCode) {
    	HashMap<String, String> map = new HashMap<String,String>();
    	if(StringUtils.isNotBlank(paramCode))
    		map.put("paramCode",paramCode);
        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BAPARAM.getParaNameData", map);
    }
    
    /**
     * For DeathRepayData
     * 依傳入的條件取得 系統參數檔 (<code>BAPARAM</code>) 的資料<br>
     * @param paramCode 參數代號
     */
    @DaoFieldList("PARAMCODE")
    public String getParamNameForDeathRepayData(String paramCode) {
    	HashMap<String, String> map = new HashMap<String,String>();
    	if(StringUtils.isNotBlank(paramCode))
    		map.put("paramCode",paramCode);
        return (String) getSqlMapClientTemplate().queryForObject("BAPARAM.getParamNameForDeathRepayData", map);
    }
    
    /**
     * For DeathRepayData
     * 依傳入的條件取得 系統參數檔 (<code>BAPARAM</code>) 的資料<br>
     * @param paramCode 參數代號
     */
    @DaoFieldList("PARAMCODE")
    public String getParamNameForKRECHKRESULT(String paramCode) {
    	HashMap<String, String> map = new HashMap<String,String>();
    	if(StringUtils.isNotBlank(paramCode))
    		map.put("paramCode",paramCode);
        return (String) getSqlMapClientTemplate().queryForObject("BAPARAM.getParamNameForKRECHKRESULT", map);
    }
    
    /**
     * For 整批收回核定清單 - PFM&PFD
     * 依傳入的條件取得 系統參數檔 (<code>BAPARAM</code>) 的資料<br>
     * @param paramCode 參數代號
     */
    @DaoFieldList("PARAMCODE")
    public String getParamNameForPfmPfd(String paramCode) {
        HashMap<String, String> map = new HashMap<String,String>();
        if(StringUtils.isNotBlank(paramCode))
            map.put("paramCode",paramCode);
        return (String) getSqlMapClientTemplate().queryForObject("BAPARAM.selectParamNameForPfmPfdBy", map);
    }
}
