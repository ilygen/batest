package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class Bccmf45 implements Serializable{
	private BigDecimal rate;
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	private String year;
}
