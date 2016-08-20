package com.javahonk.pricecacheserializers;

import java.util.Objects;

public class Prices {

	private String symbol;
	
	private String cusip;
	
	private String compositeRicCode;
	
	private double closingPrice;
	
	private String closingDate;
	
	public Prices(
			String symbol,
			String cusip,
			String compositeRicCode,
			double closingPrice,
			String closingDate){
		
		this.symbol = symbol;
		this.cusip = cusip;
		this.compositeRicCode = compositeRicCode;
		this.closingPrice = closingPrice;
		this.closingDate = closingDate;
		
	}
	
	public String getSymbol(){
		return this.symbol;
	}
	
	public void setSymbol(String val){
		this.symbol = val;
	}
	
	public String getCusip(){
		return this.cusip;
	}
	
	public void setCusip(String val){
		this.cusip = val;
	}
	
	public String getCompositeRicCode(){
		return this.compositeRicCode;
	}
	
	public void setCompositeRicCode(String val){
		this.compositeRicCode = val;
	}
	
	public String getClosingDate(){
		return this.closingDate;
	}
	
	public void setClosingDate(String val){
		this.closingDate = val;
	}
	
	public double getClosingPrice(){
		return this.closingPrice;
	}
	
	public void setClosingPrice(double val){
		this.closingPrice = val;
	}
	
	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		Prices prices = (Prices) obj;

		return Objects.equals(prices.symbol, this.symbol)
				&& Objects.equals(prices.cusip, this.cusip)
				&& Objects.equals(prices.compositeRicCode, this.compositeRicCode)
				&& Objects.equals(prices.closingDate, this.closingDate)
				&& Objects.equals(prices.closingPrice, this.closingPrice);
	}

	@Override
	public int hashCode() {
		return Objects.hash(symbol,cusip,compositeRicCode,closingPrice,closingDate);
	};
}
