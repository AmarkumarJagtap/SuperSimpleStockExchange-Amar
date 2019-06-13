/**
 * 
 */
package com.app.stockmarket.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.app.stockmarket.domain.Stock;
import com.app.stockmarket.domain.TradeTransaction;
import com.app.stockmarket.exception.InvalidStockException;
import com.app.stockmarket.service.StockDataService;
import com.app.stockmarket.service.TradeService;
import com.app.stockmarket.utils.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 *
 */
@Service
public class TradeServiceImpl implements TradeService {


	@Autowired
	private StockDataService stockDS;

	public TradeServiceImpl() {
		super();
	}

	@Override
	public void setStockDataService(StockDataService stockDS){
		this.stockDS = stockDS;
	}

	/* (non-Javadoc)
	 * @see com.app.stockmarket.service.TradeService#createStockInMarket(com.app.stockmarket.domain.Stock)
	 */
	@Override
	public boolean createStockInMarket(Stock stock) throws InvalidStockException {
		stockDS.saveStockData(stock);
		return true;
	}

	/* (non-Javadoc)
	 * @see com.app.stockmarket.service.TradeService#tradeStockInMarket(java.lang.String, int, com.app.stockmarket.service.TradeService.BuySellIndicator, double, java.util.Date)
	 */
	@Override
	public boolean tradeStockInMarket(String stockSymbol, int quantity, BuySellIndicator buySellIndicator,
			double tradedPrice, Date timestamp) throws InvalidStockException {
		TradeTransaction tradeTransaction = new TradeTransaction();
		tradeTransaction.setStockSymbol(stockSymbol);
		tradeTransaction.setBuySellIndicator(buySellIndicator);
		tradeTransaction.setQuantity(quantity);
		tradeTransaction.setTimestamp(timestamp);
		tradeTransaction.setTradedPrice(tradedPrice);
		stockDS.recordTradeTransation(tradeTransaction);
		SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		
		if (buySellIndicator == BuySellIndicator.BUY) {
			Logger.logDebugMessage("Transaction completed for BUY " + stockSymbol + " Stock for $ " +  tradedPrice + " at  "+ dt1.format(timestamp));
		} else {
			Logger.logDebugMessage("Transaction completed for SELL " + stockSymbol + " Stock for $ " +  tradedPrice + " at "+ dt1.format(timestamp));
		}
		
		return true;
	}

	@Override
	public Stock getStockData(String stockSymbol) throws InvalidStockException {
		return stockDS.getStockData(stockSymbol);
	}

}
