package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;

import tw.gov.bli.ba.domain.Baparam;

/**
 * DAO for 系統參數檔 (<code>BAPARAM</code>)
 * 
 * @author Goston
 */
public interface BaparamDao {
    /**
     * 依傳入的條件取得 系統參數檔 (<code>BAPARAM</code>) 的資料<br>
     * 
     * @param paramTyp 參數類別
     * @param paramGroup 參數種類
     * @param paramCode 參數代號
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    public List<Baparam> selectListBy(String paramTyp, String paramGroup, String paramCode);

    /**
     * 依傳入的條件取得 系統參數檔 (<code>BAPARAM</code>) 的資料<br>
     * 
     * @param paramTyp 參數類別
     * @param paramGroup 參數種類
     * @param paramCode 參數代號
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    public List<Baparam> selectListByLetter(String paramTyp, String paramGroup, String paramCode);

    /**
     * 依傳入的條件取得 系統參數檔 (<code>BAPARAM</code>) 的資料<br>
     * 
     * @param paramTyp 參數類別
     * @param paramGroup 參數種類
     * @param paramCode 參數代號
     * @return String 物件
     */
    public String selectParamNameBy(String paramTyp, String paramGroup, String paramCode);

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
    public List<Baparam> selectOptionListBy(String paramTyp, String paramGroup, String paramCode);
    
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
    public List<Baparam> selectOldOptionListBy(String paramTyp, String paramGroup, String paramCode);

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
    public List<Baparam> selectOptionListByUpdate(String paramTyp, String paramGroup, String paramCode);

    /**
     * 取得 系統參數檔 (<code>BAPARAM</code>) 關係資料的下拉選單<br>
     * PARAMCODE as 下拉選單的Value <br>
     * (PARAMNAME||'-'||PARAMNAME) as 下拉選單的Option<br>
     * 
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    public List<Baparam> selectRelationOptionList();

    /**
     * 取得 系統參數檔 (<code>BAPARAM</code>) 關係資料的下拉選單<br>
     * PARAMCODE as 下拉選單的Value <br>
     * (PARAMNAME||'-'||PARAMNAME) as 下拉選單的Option<br>
     * 取得關係 1 ~ 7的受益人
     * 
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    public List<Baparam> selectDisabledPayeeRelationOptionList();

    /**
     * 取得 系統參數檔 (<code>BAPARAM</code>) 關係資料的下拉選單<br>
     * PARAMCODE as 下拉選單的Value <br>
     * (PARAMNAME||'-'||PARAMNAME) as 下拉選單的Option<br>
     * 
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    public List<Baparam> selectPayeeRelationOptionList();

    /**
     * 取得 系統參數檔 (<code>BAPARAM</code>) 關係資料的下拉選單<br>
     * PARAMCODE as 下拉選單的Value <br>
     * (PARAMNAME||'-'||PARAMNAME) as 下拉選單的Option<br>
     * 
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    public List<Baparam> selectPayeeRelationOptionList2(String typeOne, String typeTwo, String typeThree, String typeFour,String typeFive, Boolean relationStatus);

    /**
     * 取得 系統參數檔 (<code>BAPARAM</code>) 關係資料的下拉選單<br>
     * PARAMCODE as 下拉選單的Value <br>
     * (PARAMNAME||'-'||PARAMNAME) as 下拉選單的Option<br>
     * 
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    public List<Baparam> selectBenNameOptionList(String apNo, String baappbaseId);

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
    public List<Baparam> selectReliefOptionListBy(String reliefKind);

    /**
     * 取得 系統參數檔 (<code>BAPARAM</code>) 關係資料的下拉選單<br>
     * PARAMCODE as 下拉選單的Value <br>
     * (PARAMNAME||'-'||PARAMNAME) as 下拉選單的Option<br>
     * 
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    public List<Baparam> selectDependantRelationOptionList();

    /**
     * 取得 系統參數檔 (<code>BAPARAM</code>) 結案原因的下拉選單<br>
     * PARAMCODE as 下拉選單的Value <br>
     * PARAMNAME as 下拉選單的Option<br>
     * 
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    public List<Baparam> selectDependantCloseOptionList();

    /**
     * 取得 系統參數檔 (<code>BAPARAM</code>) 申請項目下拉選單 for 遺屬年金案件資料更正<br>
     * 
     * @return
     */
    public List<Baparam> selectSurvivorApplicationDataUpdateApItemList();

    /**
     * 取得 系統參數檔 (<code>BAPARAM</code>) 關係下拉選單 for 複檢費用受理作業<br>
     * 
     * @return
     */
    public List<Baparam> selectReviewFeeReceiptRelationList();

    /**
     * 取得 系統參數檔 (<code>BAPARAM</code>) 給付方式 for 複檢費用受理作業<br>
     * 
     * @return
     */
    public List<Baparam> selectReviewFeeReceiptPayTypeList();
    
    /**
     * 依傳入的條件取得 系統參數檔 (<code>BAPARAM</code>) 的資料<br>
     * 
     * @param paramTyp 參數類別
     * @param paramGroup 參數種類
     * @param paramCode 參數代號
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    public List<Baparam> selectListByBaban(String paramTyp, String paramGroup, String paramCode);
    
    /**
     * 依傳入的條件取得 系統參數檔 (<code>BAPARAM</code>) 的資料<br>
     * @param paramCode 參數代號
     */
    public BigDecimal getParaNameData(String paramCode);
    
    /**
     * For DeathRepayData
     * 依傳入的條件取得 系統參數檔 (<code>BAPARAM</code>) 的資料<br>
     * @param paramCode 參數代號
     */
    public String getParamNameForDeathRepayData(String paramCode);
    
    /**
     * For DeathRepayData
     * 依傳入的條件取得 系統參數檔 (<code>BAPARAM</code>) 的資料<br>
     * @param paramCode 參數代號
     */
    public String getParamNameForKRECHKRESULT(String paramCode);
    
    /**
     * For DeathRepayData
     * 依傳入的條件取得 系統參數檔 (<code>BAPARAM</code>) 的資料<br>
     * @param paramCode 參數代號
     */
    public String getParamNameForPfmPfd(String paramCode);
}
