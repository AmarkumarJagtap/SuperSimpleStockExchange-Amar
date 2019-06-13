package com.app.stockmarket.types;

public enum Currency {
	USD, GBP;
	
	/**
	 * 
	 * @param currency
	 * @return
	 */
	public Currency toCurrencyEnum(String currency) {
		
		for (Currency currEnum :  Currency.values()) {
			if(currEnum.equals(currency)) {
				return currEnum;
			}
		}
		
		return null;
	}
}
