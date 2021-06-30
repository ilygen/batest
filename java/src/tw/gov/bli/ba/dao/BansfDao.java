package tw.gov.bli.ba.dao;

import java.util.List;

import tw.gov.bli.ba.domain.Bansf;

/**
 * DAO for 勞保年金統計檔 (<code>BANSF</code>)
 * 
 * @author Eddie
 */
public interface BansfDao {
    /**
     * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 件數及金額 的資料<br>
     * 
     * @param payKind 給付別
     * @param paymentYMStart 核付年月(起)
     * @param paymentYMEnd 核付年月(迄)
     * @return <code>List<Bansf></code> 物件
     */
    public List<Bansf> qryRptType1X(String payKind, String paymentYMStart, String paymentYMEnd);
    
    /**
     * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 件數及金額 的資料<br>
     * 
     * @param payKind 給付別
     * @param paymentYMStart 核付年月(起)
     * @param paymentYMEnd 核付年月(迄)
     * @return <code>List<Bansf></code> 物件
     */
    public List<Bansf> qryRptType1X1(String payKind, String paymentYMStart, String paymentYMEnd);
    
    /**
     * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 件數及金額 的資料<br>
     * 
     * @param payKind 給付別
     * @param paymentYMStart 核付年月(起)
     * @param paymentYMEnd 核付年月(迄)
     * @return <code>List<Bansf></code> 物件
     */
    public List<Bansf> qryRptType1X2(String payKind, String paymentYMStart, String paymentYMEnd);
    
    /**
     * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 性別 的資料<br>
     * 
     * @param payKind 給付別
     * @param paymentYMStart 核付年月(起)
     * @param paymentYMEnd 核付年月(迄)
     * @return <code>List<Bansf></code> 物件
     */
    public List<Bansf> qryRptType1S(String payKind, String paymentYMStart, String paymentYMEnd);
    
    /**
     * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 單位類別 的資料<br>
     * 
     * @param payKind 給付別
     * @param paymentYMStart 核付年月(起)
     * @param paymentYMEnd 核付年月(迄)
     * @return <code>List<Bansf></code> 物件
     */
    public List<Bansf> qryRptType1U(String payKind, String paymentYMStart, String paymentYMEnd);
    
    /**
     * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 國籍別 的資料<br>
     * 
     * @param payKind 給付別
     * @param paymentYMStart 核付年月(起)
     * @param paymentYMEnd 核付年月(迄)
     * @return <code>List<Bansf></code> 物件
     */
    public List<Bansf> qryRptType1N(String payKind, String paymentYMStart, String paymentYMEnd);
    
    /**
     * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 國籍別 的資料<br>
     * 
     * @param payKind 給付別
     * @param paymentYMStart 核付年月(起)
     * @param paymentYMEnd 核付年月(迄)
     * @return <code>List<Bansf></code> 物件
     */
    public List<Bansf> qryRptType1N1(String payKind, String paymentYMStart, String paymentYMEnd);
    
    /**
     * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 外籍別 的資料<br>
     * 
     * @param payKind 給付別
     * @param paymentYMStart 核付年月(起)
     * @param paymentYMEnd 核付年月(迄)
     * @param cipbFMk 外籍別(CIPB)
     * @return <code>List<Bansf></code> 物件
     */
    public List<Bansf> qryRptType1C(String payKind, String paymentYMStart, String paymentYMEnd);
    
    /**
     * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 外籍別 的資料<br>
     * 
     * @param payKind 給付別
     * @param paymentYMStart 核付年月(起)
     * @param paymentYMEnd 核付年月(迄)
     * @param cipbFMk 外籍別(CIPB)
     * @return <code>List<Bansf></code> 物件
     */
    public List<Bansf> qryRptType1C1(String payKind, String paymentYMStart, String paymentYMEnd);
    
    /**
     * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 傷病分類 的資料<br>
     * 
     * @param payKind 給付別
     * @param paymentYMStart 核付年月(起)
     * @param paymentYMEnd 核付年月(迄)
     * @return <code>List<Bansf></code> 物件
     */
    public List<Bansf> qryRptType1E(String payKind, String paymentYMStart, String paymentYMEnd);
    
    /**
     * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 傷病分類 的資料<br>
     * 
     * @param payKind 給付別
     * @param paymentYMStart 核付年月(起)
     * @param paymentYMEnd 核付年月(迄)
     * @return <code>List<Bansf></code> 物件
     */
    public List<Bansf> qryRptType1E1(String payKind, String paymentYMStart, String paymentYMEnd);
    
    /**
     * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年度平均 的資料<br>
     * 
     * @param payKind 給付別
     * @param paymentYMStart 核付年月(起)
     * @param paymentYMEnd 核付年月(迄)
     * @return <code>List<Bansf></code> 物件
     */
    public List<Bansf> qryRptType2X(String payKind, String paymentYMStart, String paymentYMEnd);
    
    /**
     * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年度平均 性別的資料<br>
     * 
     * @param payKind 給付別
     * @param paymentYMStart 核付年月(起)
     * @param paymentYMEnd 核付年月(迄)
     * @return <code>List<Bansf></code> 物件
     */
    public List<Bansf> qryRptType2S(String payKind, String paymentYMStart, String paymentYMEnd);
    
    /**
     * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年度平均 單位類別的資料<br>
     * 
     * @param payKind 給付別
     * @param paymentYMStart 核付年月(起)
     * @param paymentYMEnd 核付年月(迄)
     * @return <code>List<Bansf></code> 物件
     */
    public List<Bansf> qryRptType2U(String payKind, String paymentYMStart, String paymentYMEnd);
    
    /**
     * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年度平均 國籍別的資料<br>
     * 
     * @param payKind 給付別
     * @param paymentYMStart 核付年月(起)
     * @param paymentYMEnd 核付年月(迄)
     * @return <code>List<Bansf></code> 物件
     */
    public List<Bansf> qryRptType2N(String payKind, String paymentYMStart, String paymentYMEnd);
    
    /**
     * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年度平均 外籍別的資料<br>
     * 
     * @param payKind 給付別
     * @param paymentYMStart 核付年月(起)
     * @param paymentYMEnd 核付年月(迄)
     * @return <code>List<Bansf></code> 物件
     */
    public List<Bansf> qryRptType2C(String payKind, String paymentYMStart, String paymentYMEnd);
    
    /**
     * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年度平均 傷病分類的資料<br>
     * 
     * @param payKind 給付別
     * @param paymentYMStart 核付年月(起)
     * @param paymentYMEnd 核付年月(迄)
     * @return <code>List<Bansf></code> 物件
     */
    public List<Bansf> qryRptType2E(String payKind, String paymentYMStart, String paymentYMEnd);
    /**
     * 金額級距、展減級距、平均薪資級距及年資級距
     * @param payCode 給付別
     * @param paymentYmStart 西元 核付日期(起)
     * @param paymentYmEnd   西元 核付日期(迄)
     * @param qrytype 統計項目 C,D,E,F
     * @param spcStart	級距區間起
     * @param spcEnd	級距區間迄
     * @param analysisSelect 分析選項
     * @return
     */
    public Bansf queryReport3DataXData(String payCode, String paymentYmStart, String paymentYmEnd, String qrytype, String spcStart, String spcEnd, String totSpcStart, String totSpcEnd);
    /**
     * 金額級距、展減級距、平均薪資級距及年資級距
     * @param payCode 給付別
     * @param paymentYmStart 西元 核付日期(起)
     * @param paymentYmEnd   西元 核付日期(迄)
     * @param qrytype 統計項目 C,D,E,F
     * @param spcStart	級距區間起
     * @param spcEnd	級距區間迄
     * @param analysisSelect 分析選項
     * @return
     */
    public List<Bansf> queryReport3DataSData(String payCode, String paymentYmStart, String paymentYmEnd, String qrytype, String spcStart, String spcEnd, String totSpcStart, String totSpcEnd);
    /**
     * 金額級距、展減級距、平均薪資級距及年資級距
     * @param payCode 給付別
     * @param paymentYmStart 西元 核付日期(起)
     * @param paymentYmEnd   西元 核付日期(迄)
     * @param qrytype 統計項目 C,D,E,F
     * @param spcStart	級距區間起
     * @param spcEnd	級距區間迄
     * @param analysisSelect 分析選項
     * @param ubType 單位類別
     * @return
     */
    public List<Bansf> queryReport3DataUData(String payCode, String paymentYmStart, String paymentYmEnd, String qrytype, String spcStart, String spcEnd, String[] ubType, String totSpcStart, String totSpcEnd);

    /**
     * 金額級距、展減級距、平均薪資級距及年資級距
     * @param payCode 給付別
     * @param paymentYmStart 西元 核付日期(起)
     * @param paymentYmEnd   西元 核付日期(迄)
     * @param qrytype 統計項目 C,D,E,F
     * @param spcStart	級距區間起
     * @param spcEnd	級距區間迄
     * @param analysisSelect 分析選項
     * @return
     */
    public List<Bansf> queryReport3DataNData(String payCode, String paymentYmStart, String paymentYmEnd, String qrytype, String spcStart, String spcEnd, String totSpcStart, String totSpcEnd);
    /**
     * 金額級距、展減級距、平均薪資級距及年資級距
     * @param payCode 給付別
     * @param paymentYmStart 西元 核付日期(起)
     * @param paymentYmEnd   西元 核付日期(迄)
     * @param qrytype 統計項目 C,D,E,F
     * @param spcStart	級距區間起
     * @param spcEnd	級距區間迄
     * @param analysisSelect 分析選項
     * @param cipbFmk 外籍別
     * @return
     */
    public List<Bansf> queryReport3DataCData(String payCode, String paymentYmStart, String paymentYmEnd, String qrytype, String spcStart, String spcEnd, String[] cipbFmk, String totSpcStart, String totSpcEnd);
    /**
     * 金額級距、展減級距、平均薪資級距及年資級距
     * @param payCode 給付別
     * @param paymentYmStart 西元 核付日期(起)
     * @param paymentYmEnd   西元 核付日期(迄)
     * @param qrytype 統計項目 C,D,E,F
     * @param spcStart	級距區間起
     * @param spcEnd	級距區間迄
     * @param analysisSelect 分析選項
     * @return
     */
    public List<Bansf> queryReport3DataEData(String payCode, String paymentYmStart, String paymentYmEnd, String qrytype, String spcStart, String spcEnd, String totSpcStart, String totSpcEnd);


	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年齡級距 分析選項 無的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType4XA(String payKind, String paymentYMStart, String paymentYMEnd);

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 投保薪資級距 分析選項 無的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType4XW(String payKind, String paymentYMStart, String paymentYMEnd);

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年齡級距 分析選項 性別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType4SA(String payKind, String paymentYMStart, String paymentYMEnd);

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 投保薪資級距 分析選項 性別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType4SW(String payKind, String paymentYMStart, String paymentYMEnd);

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年齡級距 分析選項 單位類別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType4UA(String payKind, String paymentYMStart, String paymentYMEnd);

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 投保薪資級距 分析選項 單位類別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType4UW(String payKind, String paymentYMStart, String paymentYMEnd);

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年齡級距 分析選項 國籍別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType4NA(String payKind, String paymentYMStart, String paymentYMEnd);

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 投保薪資級距 分析選項 國籍別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType4NW(String payKind, String paymentYMStart, String paymentYMEnd);

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年齡級距 分析選項 外籍別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType4CA(String payKind, String paymentYMStart, String paymentYMEnd);

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 投保薪資級距 分析選項 外籍別的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType4CW(String payKind, String paymentYMStart, String paymentYMEnd);

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 年齡級距 分析選項 傷病分類的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType4EA(String payKind, String paymentYMStart, String paymentYMEnd);

	/**
	 * 依傳入的條件取得 勞保年金統計檔 (<code>BANSF</code>) 投保薪資級距 分析選項 傷病分類的資料<br>
	 * 
	 * @param payKind        給付別
	 * @param paymentYMStart 核付年月(起)
	 * @param paymentYMEnd   核付年月(迄)
	 * @return <code>List<Bansf></code> 物件
	 */
	public List<Bansf> qryRptType4EW(String payKind, String paymentYMStart, String paymentYMEnd);
	/**
     * 依傳入條件取得(<code>BANSF</code>) 勞保年金統計檔詳細資料
     * 
     * @param issuYm
     * @return <code>Bansf</code> 物件
     */
	public List<Bansf> selectStatisticsReportData(String issuYm);
}
	