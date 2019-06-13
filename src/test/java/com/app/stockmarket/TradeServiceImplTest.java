/**
 * 
 */
package com.app.stockmarket;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import com.app.stockmarket.service.TradeService;
import org.junit.Before;
import org.junit.Test;

import com.app.stockmarket.domain.CommonStock;
import com.app.stockmarket.domain.FixedDividendStock;
import com.app.stockmarket.domain.Stock;
import com.app.stockmarket.exception.InvalidStockException;
import com.app.stockmarket.service.TradeService.BuySellIndicator;
import com.app.stockmarket.utils.Logger;
import com.app.stockmarket.service.impl.StockDataServiceImpl;
import com.app.stockmarket.service.impl.TradeServiceImpl;
import com.app.stockmarket.types.Currency;

/**
 *
 */
public class TradeServiceImplTest {

	TradeServiceImpl tradeServiceImpl;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		try {
			
			final StockDataServiceImpl stockDataServiceImpl = new StockDataServiceImpl();
			Stock stock = new CommonStock();
			stock.setSymbol("TEA");
			stock.setParValue(100);
			stock.setCurrency(Currency.USD);
		
			stockDataServiceImpl.saveStockData(stock);

			stock = new CommonStock();
			stock.setSymbol("POP");
			stock.setParValue(100);
			stock.setCurrency(Currency.USD);
			stockDataServiceImpl.saveStockData(stock);
			
			stock = new CommonStock();
			stock.setSymbol("ALE");
			stock.setParValue(60);
			stock.setCurrency(Currency.USD);
			stockDataServiceImpl.saveStockData(stock);
			
			stock = new FixedDividendStock();
			stock.setSymbol("GIN");
			stock.setParValue(100);
			stock.setCurrency(Currency.USD);

			stockDataServiceImpl.saveStockData(stock);
			stock = new CommonStock();
			stock.setSymbol("JOE");
			stock.setParValue(250);
			stock.setCurrency(Currency.USD);
			stockDataServiceImpl.saveStockData(stock);
			tradeServiceImpl = new TradeServiceImpl();
			tradeServiceImpl.setStockDataService(stockDataServiceImpl);
		
		} catch (InvalidStockException e) {
			Logger.logDebugMessage("Message : " + e.getMessage());
			assertNull(e.getErrorCode().toString(), e);
		}
	}

	/**
	 * Test method for {@link TradeServiceImpl#createStockInMarket(com.app.stockmarket.domain.Stock)}.
	 */
	@Test
	public void testCreateStockInMarket() {
		try {
			Stock stock = new CommonStock();
			stock.setSymbol("JOE");
			stock.setParValue(60);
			stock.setCurrency(Currency.USD);
			assertTrue(tradeServiceImpl.createStockInMarket(stock));
		} catch (InvalidStockException e) {
			Logger.logDebugMessage("Message : " + e.getMessage());
			assertNull(e.getErrorCode().toString(), e);
		}
	}

	/**
	 * Test method for {@link TradeServiceImpl#tradeStockInMarket(java.lang.String, int, TradeService.BuySellIndicator, double, java.util.Date)}.
	 */
	@Test
	public void testTradeStockInMarket() {
		try {
			assertTrue(tradeServiceImpl.tradeStockInMarket("TEA", 1, BuySellIndicator.BUY, 10.00, new Date()));
		} catch (InvalidStockException e) {
			Logger.logDebugMessage("Message : " + e.getMessage());
			assertNull(e.getErrorCode().toString(), e);
		}
	}
}
