/**
 * 
 */
package com.app.stockmarket.api;

import com.app.stockmarket.api.impl.CommonStockAPI;
import com.app.stockmarket.api.impl.PreferredStockAPI;
import com.app.stockmarket.service.StockDataService;
import com.app.stockmarket.types.StockType;

/**
 *
 */
public class StockAPIFactory {

	/**
	 * 
	 */
	private StockAPIFactory() {
		
	}

	public static IStockAPI generateStockAPI(StockType stockType, StockDataService stockDataService) {

		switch (stockType) {
			case COMMON:
				return new CommonStockAPI(stockDataService);
	
			case PREFERRED:
				return new PreferredStockAPI(stockDataService);
	
			default:
				break;
		}

		return null;
	}

}
