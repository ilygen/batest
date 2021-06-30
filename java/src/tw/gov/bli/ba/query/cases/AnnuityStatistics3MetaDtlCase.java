package tw.gov.bli.ba.query.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.StringUtility;
/**
 * case for 年金統計查詢附件3(明細資料檔)
 * @author TseHua
 *
 */
public class AnnuityStatistics3MetaDtlCase implements Serializable {
	private static final long serialVersionUID = 3676535167703250203L;
	private String cntRatio;//比例
	private String payCnt;//件數
	private String sex;//性別
	private String ubType;//單位類別
	private String evtNationTpe;//國籍別
	private String cipbFmk;//外籍別
	private String evType;//傷病分類

	public String getCntRatio() {
		return cntRatio;
	}

	public void setCntRatio(String cntRatio) {
		this.cntRatio = cntRatio;
	}

	public String getPayCnt() {
		return payCnt;
	}

	public void setPayCnt(String payCnt) {
		this.payCnt = payCnt;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getUbType() {
		return ubType;
	}

	public void setUbType(String ubType) {
		this.ubType = ubType;
	}

	public String getEvtNationTpe() {
		return evtNationTpe;
	}

	public void setEvtNationTpe(String evtNationTpe) {
		this.evtNationTpe = evtNationTpe;
	}
	
	public String getCipbFmk() {
		return cipbFmk;
	}

	public void setCipbFmk(String cipbFmk) {
		this.cipbFmk = cipbFmk;
	}
	

	public String getEvType() {
		return evType;
	}

	public void setEvType(String evType) {
		this.evType = evType;
	}
	/**
	 * 國籍別字串對應轉換
	 * @return
	 */
	public String getEvtNationTpeStr() {
		if(StringUtils.equals(evtNationTpe, "1")) {
			return ConstantKey.EVTNATIONTPE_NATIONAL_STR;
		} else if(StringUtils.equals(evtNationTpe, "2")) {
			return ConstantKey.EVTNATIONTPE_FOREIGN_STR;
		} else {
			return "";
		}
	}
	/**
	 * 外籍別字串對應轉換
	 * @return
	 */
	public String getCipbFmkStr() {
		// NULL/空白/空格-本國籍(系統認定)、F-外國籍(人工認定或單位自行申報)、Y-外國籍(系統認定)、N-本國籍(人工認定)、1-外籍配偶、2-大陸配偶、3-單一國籍、4-取得國籍、5-雙重國籍。
		if (StringUtils.isBlank(cipbFmk)) {
			return ConstantKey.CIPBFMK_NON;//"本國籍(系統認定)";
		} else if (StringUtils.equals(cipbFmk, "F")) {
			return ConstantKey.CIPBFMK_F;//"外國籍(人工認定或單位自行申報)";
		} else if (StringUtils.equals(cipbFmk, "Y")) {
			return ConstantKey.CIPBFMK_Y;//"國籍(系統認定)";
		} else if (StringUtils.equals(cipbFmk, "N")) {
			return ConstantKey.CIPBFMK_N;//"本國籍(人工認定)";
		} else if (StringUtils.equals(cipbFmk, "1")) {
			return ConstantKey.CIPBFMK_1; //"外籍配偶";
		} else if (StringUtils.equals(cipbFmk, "2")) {
			return ConstantKey.CIPBFMK_2; //"大陸配偶";
		} else if (StringUtils.equals(cipbFmk, "3")) {
			return ConstantKey.CIPBFMK_3; //"單一國籍";
		} else if (StringUtils.equals(cipbFmk, "4")) {
			return ConstantKey.CIPBFMK_4; //"取得國籍";
		} else if (StringUtils.equals(cipbFmk, "5")) {
			return ConstantKey.CIPBFMK_5; //"雙重國籍";
		} else {
			return "";
		}

	}
	/**
	 * 傷病分類字串對應轉換
	 * @return
	 */
	public String getEvTypeStr() {
		// 1-普通傷病、3-職業傷病
		if (StringUtils.equals(evType, "1")) {
			return ConstantKey.EVTYPE_NORMAL_STR;
		} else if (StringUtils.equals(evType, "3")) {
			return ConstantKey.EVTYPE_PROFESSIONAL_STR;
		} else {
			return "";
		}
	}
	/**
	 * 性別字串轉換
	 * @return
	 */
	public String getSexStr() {
		if (StringUtils.equals(sex, "1")) {
			return ConstantKey.BAAPPBASE_SEX_STR_1;//"男性";
		} else if (StringUtils.equals(sex, "2")) {
			return ConstantKey.BAAPPBASE_SEX_STR_2;//"女性";
		} else {
			return "";
		}
	}
	
	public String getCntRatioStr() {
		return NumberFormat.getInstance().format(Float.parseFloat(cntRatio));
	}

}
