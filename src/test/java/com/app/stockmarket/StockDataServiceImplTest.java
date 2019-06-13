/**
 * 
 */
package com.app.stockmarket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.app.stockmarket.domain.CommonStock;
import com.app.stockmarket.domain.FixedDividendStock;
import com.app.stockmarket.domain.Stock;
import com.app.stockmarket.domain.TradeTransaction;
import com.app.stockmarket.exception.InvalidStockException;
import com.app.stockmarket.service.TradeService.BuySellIndicator;
import com.app.stockmarket.utils.Logger;
import com.app.stockmarket.service.impl.StockDataServiceImpl;
import com.app.stockmarket.types.Currency;

/**
 *
 */
public class StockDataServiceImplTest {


	private StockDataServiceImpl stockDataServiceImpl;

	@Before
	public void setUp() throws Exception {
		stockDataServiceImpl = new StockDataServiceImpl();
		
		try {
			Stock stock = new CommonStock();
			stock.setSymbol("TEA");
			stock.setParValue(100);
			stock.setCurrency(Currency.USD);
			stockDataServiceImpl.saveStockData(stock);
			
			stock = new FixedDividendStock();
			stock.setSymbol("GIN");
			stock.setParValue(100);
			stock.setCurrency(Currency.USD);
			stockDataServiceImpl.saveStockData(stock);
		} catch (InvalidStockException e) {
			e.printStackTrace(System.out);
			Logger.logDebugMessage("Message : " + e.getMessage());
			assertNull(e.getErrorCode().toString(), e);
		}
	}
	
	/**
	 * Test method for {@link StockDataServiceImpl#listStockSymbols()}.
	 */
	@Test
	public void testListStockSymbols() {
		List<String> stockSymbols = stockDataServiceImpl.listStockSymbols();
		
		assertNotNull("List of Stock symbols empty.", stockSymbols);
		
		assertEquals("Unexpted size returned", 2, stockSymbols.size());
	}

	/**
	 * Test method for {@link StockDataServiceImpl#saveStockData(com.app.stockmarket.domain.Stock)}.
	 */
	@Test
	public void testSaveStockData() {
		
		try {
			Stock stock = new CommonStock();
			stock.setSymbol("TEA");
			stock.setParValue(100);
			stock.setCurrency(Currency.USD);
			stockDataServiceImpl.saveStockData(stock);
			
			stock = new FixedDividendStock();
			stock.setSymbol("GIN");
			stock.setParValue(100);
			stock.setCurrency(Currency.USD);
			stockDataServiceImpl.saveStockData(stock);
		} catch (InvalidStockException e) {
			Logger.logDebugMessage("Message : " + e.getMessage());
			assertNull(e.getErrorCode().toString(), e);
		}
	}

	/**
	 * Test method for {@link StockDataServiceImpl#getStockData(java.lang.String)}.
	 */
	@Test
	public void testGetStockData() {
		Stock stock;
		try {
			stock = stockDataServiceImpl.getStockData("TEA");
			assertTrue("Unexpected Data for TEA", "TEA".equals(stock.getSymbol()));
			
			stock = stockDataServiceImpl.getStockData("GIN");
			assertTrue("Unexpected Data for GIN, Symbol was : " + stock.getSymbol(), "GIN".equals(stock.getSymbol()));
			assertTrue("Unexpected Data for GIN, Stock Type was : " + stock.getStockType().toString(), "PREFERRED".equals(stock.getStockType().toString()));
			
		} catch (InvalidStockException e) {
			e.printStackTrace(System.out);
			Logger.logDebugMessage("Message : " + e.getMessage());
			assertNull(e.getErrorCode().toString(), e);
		}
	}

	/**
	 * Test method for {@link StockDataServiceImpl#recordTradeTransation(com.app.stockmarket.domain.TradeTransaction)}.
	 */
	@Test
	public void testRecordTradeTransation() {
		try {
			
			TradeTransaction tradeTransaction = new TradeTransaction();
			tradeTransaction.setStockSymbol("TEA");
			tradeTransaction.setBuySellIndicator(BuySellIndicator.BUY);
			tradeTransaction.setQuantity(1);
			tradeTransaction.setTimestamp(new Date());
			tradeTransaction.setTradedPrice(10.00);
			stockDataServiceImpl.recordTradeTransation(tradeTransaction);
		} catch (InvalidStockException e) {
			e.printStackTrace(System.out);
			Logger.logDebugMessage("Message : " + e.getMessage());
			assertNull(e.getErrorCode().toString(), e);
		}
	}

}
