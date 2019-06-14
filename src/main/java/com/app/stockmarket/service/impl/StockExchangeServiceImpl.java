/**
 * 
 */
package com.app.stockmarket.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.app.stockmarket.api.IStockAPI;
import com.app.stockmarket.api.StockAPIFactory;
import com.app.stockmarket.domain.Stock;
import com.app.stockmarket.exception.InvalidStockException;
import com.app.stockmarket.service.StockDataService;
import com.app.stockmarket.service.TradeService;
import com.app.stockmarket.service.TradeService.BuySellIndicator;
import com.app.stockmarket.service.StockExchangeService;
import com.app.stockmarket.types.StockType;
import com.app.stockmarket.utils.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class StockExchangeServiceImpl implements StockExchangeService {


	private static final int FIFTEEN_MINUTES = 15;

	@Autowired
	protected TradeService tradeService;

	@Autowired
	protected StockDataService stockDataService;

	private String name;

	private String country;

	/**
	 * Create Stock in Market
	 * 
	 * @param stock Details of the new stock
	 * @return
	 * @throws InvalidStockException 
	 */
	public boolean createStockInMarket(Stock stock) throws InvalidStockException {
		if(tradeService == null)
			throw new UnsupportedOperationException(ErrorMessages.TRADE_SERVICE_NOT_CONFIGURED);
		
		return tradeService.createStockInMarket(stock);
	}

	/**
	 * @param name the name to set
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param country the country to set
	 */
	@Override
	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public int getConfiguredMinuntes() {
		return FIFTEEN_MINUTES;
	}

	@Override
	public Locale getClientLocale() {
		return Locale.US;
	}
	/**
	 * Buy a stock denoted by a stock symbol
	 * 
	 * @param stockSymbol Symbol 
	 * @param quantity
	 * @param price
	 * @return
	 */
	public boolean buyStock(String stockSymbol, int quantity, double price) throws InvalidStockException {
		
		if(tradeService == null)
			throw new UnsupportedOperationException(ErrorMessages.TRADE_SERVICE_NOT_CONFIGURED);
		
		Date date = Calendar.getInstance().getTime();
		
		return tradeService.tradeStockInMarket(stockSymbol, quantity, BuySellIndicator.BUY, price, date);
	}

	/**
	 * Buy a stock denoted by a stock symbol
	 * 
	 * @param stockSymbol
	 * @param quantity
	 * @param price
	 * @return
	 */
	public boolean sellStock(String stockSymbol, int quantity, double price) throws InvalidStockException  {
		if(tradeService == null)
			throw new UnsupportedOperationException(ErrorMessages.TRADE_SERVICE_NOT_CONFIGURED);
		
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		
		return tradeService.tradeStockInMarket(stockSymbol, quantity, BuySellIndicator.SELL, price, date);
	}
	
	/**
	 * Calculate Dividend for a Stock denoted by a symbol
	 * 
	 * @param stockSymbol
	 * @return
	 */
	public double calculateDividendYield(String stockSymbol, double price) throws InvalidStockException  {
		
		if(tradeService == null)
			throw new UnsupportedOperationException(ErrorMessages.TRADE_SERVICE_NOT_CONFIGURED);
		
		
		if(stockDataService == null)
			throw new UnsupportedOperationException(ErrorMessages.TRADE_SERVICE_NOT_CONFIGURED);
		
		final StockType stockType = tradeService.getStockData(stockSymbol).getStockType();
		
		IStockAPI stockAPI = StockAPIFactory.generateStockAPI(stockType, stockDataService);
		
		return stockAPI.calculateDividendYield(stockSymbol, price);
	}
	
	/**
	 * Calculate Volume Weighted Stock Price
	 * 
	 * @param stockSymbol
	 * @return
	 */
	@Override
	public double calculateVolumeWeightedStockPrice(String stockSymbol) throws InvalidStockException  {
		
		if(tradeService == null)
			throw new UnsupportedOperationException(ErrorMessages.TRADE_SERVICE_NOT_CONFIGURED);
		
		if(stockDataService == null)
			throw new UnsupportedOperationException(ErrorMessages.TRADE_SERVICE_NOT_CONFIGURED);
		
		IStockAPI stockAPI = StockAPIFactory.generateStockAPI(tradeService.getStockData(stockSymbol).getStockType(), stockDataService);
		
		return stockAPI.volumeWeightedStockPriceByTime(stockSymbol, getConfiguredMinuntes());
	}

	
	@Override
	public double priceOverDividendRatio(String stockSymbol, double price) throws InvalidStockException {
		
		if(tradeService == null)
			throw new UnsupportedOperationException(ErrorMessages.TRADE_SERVICE_NOT_CONFIGURED);
		
		if(stockDataService == null)
			throw new UnsupportedOperationException(ErrorMessages.TRADE_SERVICE_NOT_CONFIGURED);
		
		IStockAPI stockAPI = StockAPIFactory.generateStockAPI(tradeService.getStockData(stockSymbol).getStockType(), stockDataService);
		
		
		return stockAPI.priceOverDividendRatio(stockSymbol, price);
	}


	public double calculateVolumeWeightedStockPrice(String stockSymbol, int minutes) throws InvalidStockException {
		
		if(tradeService == null)
			throw new UnsupportedOperationException(ErrorMessages.TRADE_SERVICE_NOT_CONFIGURED);
		
		if(stockDataService == null)
			throw new UnsupportedOperationException(ErrorMessages.TRADE_SERVICE_NOT_CONFIGURED);
		
		IStockAPI stockAPI = StockAPIFactory.generateStockAPI(tradeService.getStockData(stockSymbol).getStockType(), stockDataService);
		
		
		return stockAPI.volumeWeightedStockPriceByTime(stockSymbol, minutes);
	}
	
	/**
	 * Calculate the GBCE All Share Index using the geometric mean of prices for all stocks
	 * 
	 * @return
	 */
	public double calculateAllShareIndex() throws InvalidStockException  {


		if(stockDataService == null)
			throw new UnsupportedOperationException(ErrorMessages.TRADE_SERVICE_NOT_CONFIGURED);
		
		IStockAPI stockAPI = StockAPIFactory.generateStockAPI(StockType.COMMON, stockDataService);
		
		return stockAPI.calculateAllShareIndex();
	}
	
	@Override
	public List<String> listAllStockSymbols() {
		if(stockDataService == null)
			throw new UnsupportedOperationException(ErrorMessages.TRADE_SERVICE_NOT_CONFIGURED);
		
		return stockDataService.listStockSymbols();
	}

	@Override
	public List<Stock> listAllStocksInMarket() {
		if(stockDataService == null)
			throw new UnsupportedOperationException(ErrorMessages.TRADE_SERVICE_NOT_CONFIGURED);
		
		return stockDataService.listAllStocks();
	}
	/**
	 * Register Trade Service Implementation
	 * 
	 * @param tradeService implementation of TradeService interface
	 */
	@Override
	public StockExchangeServiceImpl registerTradeService(TradeService tradeService) {
		this.tradeService = tradeService;
		return this;
	}
	
	/**
	 * @param stockDataService the stockDataService to set
	 */
	@Override
	public StockExchangeServiceImpl registerStockDataService(StockDataService stockDataService) {
		this.stockDataService = stockDataService;
		return this;
	}


}
