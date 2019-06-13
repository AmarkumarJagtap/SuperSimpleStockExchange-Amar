/**
 * 
 */
package com.app.stockmarket.api.impl;

import com.app.stockmarket.domain.FixedDividendStock;
import com.app.stockmarket.exception.InvalidStockException;
import com.app.stockmarket.service.StockDataService;
import com.app.stockmarket.utils.Logger;
import org.springframework.stereotype.Service;

/**
 *
 *
 */
@Service
public class PreferredStockAPI extends AbstractStockAPI {

	/**
	 * @param stockDataService
	 */
	public PreferredStockAPI(StockDataService stockDataService) {
		super(stockDataService);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.app.stockmarket.api.impl.AbstractStockAPI#calculateDividendYield(java
	 * .lang.String, double)
	 */
	@Override
	public double calculateDividendYield(String stockSymbol, double price) throws InvalidStockException {

		FixedDividendStock stock = (FixedDividendStock) stockDataService.getStockData(stockSymbol);;

		double dividendYield = 0.0;
		
		Logger.logDebugMessage("Fixed DividendYield  = Fixed Dividend  * Par Value / Price");
		Logger.logDebugMessage("                     = " + stock.getFixedDividendPercentage() + " %  * " + stock.getParValue() + " / " + price);
		Logger.logDebugMessage("                     = " + stock.getFixedDividendPercentage() / 100.0 +  " * " + stock.getParValue() + "/" + price);
		double fixedDividendParValue 				 = (stock.getFixedDividendPercentage() / 100.0) * stock.getParValue();
		Logger.logDebugMessage("                     = " + fixedDividendParValue + " / " + price);

		if (price != 0.0) {
			dividendYield = fixedDividendParValue / price;
		}

		Logger.logDebugMessage("                     = " + dividendYield + "\n");
		return dividendYield;
	}

}
