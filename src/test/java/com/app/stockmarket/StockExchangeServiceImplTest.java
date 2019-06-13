/**
 * 
 */
package com.app.stockmarket;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.app.stockmarket.service.TradeService;
import com.app.stockmarket.service.impl.StockExchangeServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import com.app.stockmarket.domain.CommonStock;
import com.app.stockmarket.domain.FixedDividendStock;
import com.app.stockmarket.domain.Stock;
import com.app.stockmarket.exception.InvalidStockException;
import com.app.stockmarket.service.StockDataService;
import com.app.stockmarket.utils.Logger;
import com.app.stockmarket.service.TradeService.BuySellIndicator;
import com.app.stockmarket.service.impl.StockDataServiceImpl;
import com.app.stockmarket.service.impl.TradeServiceImpl;
import com.app.stockmarket.types.Currency;

/**
 *
 *
 */
public class StockExchangeServiceImplTest {

	static StockExchangeServiceImpl stockExchangeServiceImpl = null;
	
	static String[] setOfSymbols = new String[] { "TEA", "POP", "ALE", "GIN", "JOE" };
	
	static int[] percentageDiff = new int[] { -10, -5, -3, -2, -1, 0, 1, 2, 3, 5, 10 };
	
	SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
	
	static StockDataService stockDS;
	static TradeService tradeService;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUp() throws Exception {
		
		stockDS = new StockDataServiceImpl();

		stockExchangeServiceImpl = new StockExchangeServiceImpl();
		stockExchangeServiceImpl.setCountry("UK");
		stockExchangeServiceImpl.setName("GBCE");
		stockExchangeServiceImpl.registerStockDataService(stockDS);
		tradeService = new TradeServiceImpl();
		tradeService.setStockDataService(stockDS);
		stockExchangeServiceImpl.registerTradeService(tradeService);

		try {
			FixedDividendStock stock = new FixedDividendStock();
			stock.setSymbol("GIN");
			stock.setParValue(100);
			stock.setLastDividend(8);
			stock.setFixedDividendPercentage(2);
			stock.setCurrency(Currency.USD);
			stockExchangeServiceImpl.createStockInMarket(stock);
		} catch (InvalidStockException e) {
			e.printStackTrace(System.out);
			Logger.logDebugMessage("Message : " + e.getMessage());
			assertNull(e.getErrorCode().toString(), e);
		}
		
	}

	/**
	 * Test method for {@link StockExchangeServiceImpl#createStockInMarket(com.app.stockmarket.domain.Stock)}.
	 */
	@Test
	public void testCreateStockInMarket() {
		try {
			Stock stock = new CommonStock();
			stock.setSymbol("TEA");
			stock.setLastDividend(0);
			stock.setParValue(100);
			stock.setLastDividend(0);
			stock.setCurrency(Currency.USD);
			stockExchangeServiceImpl.createStockInMarket(stock);
			
			stock = new CommonStock();
			stock.setSymbol("POP");
			stock.setParValue(100);
			stock.setLastDividend(8);
			stock.setCurrency(Currency.USD);
			stockExchangeServiceImpl.createStockInMarket(stock);
			
			stock = new CommonStock();
			stock.setSymbol("ALE");
			stock.setLastDividend(0);
			stock.setParValue(100);
			stock.setLastDividend(23);
			stock.setCurrency(Currency.USD);
			stockExchangeServiceImpl.createStockInMarket(stock);
			
			
			stock = new CommonStock();
			stock.setSymbol("JOE");
			stock.setLastDividend(0);
			stock.setParValue(100);
			stock.setLastDividend(13);
			stock.setCurrency(Currency.USD);
			stockExchangeServiceImpl.createStockInMarket(stock);
			
			FixedDividendStock stock1 = new FixedDividendStock();
			stock1.setSymbol("GIN");
			stock1.setParValue(100);
			stock1.setCurrency(Currency.USD);
			stock1.setLastDividend(8);
			stock1.setFixedDividendPercentage(2);
			stockExchangeServiceImpl.createStockInMarket(stock1);
		} catch (InvalidStockException e) {
			e.printStackTrace(System.out);
			Logger.logDebugMessage("Message : " + e.getMessage());
			assertNull(e.getErrorCode().toString(), e);
		}
	}

	/**
	 * Test method for {@link StockExchangeServiceImpl#buyStock(java.lang.String, int, double)}.
	 */
	@Test
	public void testBuyStock() {
		try {
				
			for(int i = 0; i < 6; i++) {
				Date  currTime = new Date();
				stockExchangeServiceImpl.buyStock("GIN", 1, 10.00);
				Logger.logDebugMessage("Bought GIN Stock - Step " + (i + 1) + " Time : "+ dt1.format(currTime));
			}
			
			
		} catch (InvalidStockException e) {
			e.printStackTrace(System.out);
			Logger.logDebugMessage("Message : " + e.getMessage());
			assertNull(e.getErrorCode().toString(), e);
		}
	}

	/**
	 * Test method for {@link StockExchangeServiceImpl#sellStock(java.lang.String, int, double)}.
	 */
	@Test
	public void testSellStock() {
		for(int i = 0; i < 6; i++) {
			Date  currTime = new Date();
			
			try {
				stockExchangeServiceImpl.sellStock("GIN", 1, 10.00);
				Logger.logDebugMessage("Sold GIN Stock - Step " + (i + 1) + " Time : "+ dt1.format(currTime));
			} catch (InvalidStockException e) {
				Logger.logDebugMessage("Message : " + e.getMessage());
				assertNull(e.getErrorCode().toString(), e);
			}
		}
	}

	/**
	 * Test method for {@link StockExchangeServiceImpl#calculateDividendYield(java.lang.String, double)}.
	 */
	@Test
	public void testCalculateDividendYield() {
		try {
			final String stockSymbol = "POP";
			double dividend = stockDS.getStockData(stockSymbol).getLastDividend();
			double price = stockDS.getStockData(stockSymbol).getParValue() + (stockDS.getStockData(stockSymbol).getParValue() * 0.05);
			
			double dividendYield = dividend / price ;
			final double calculateDividendYield = stockExchangeServiceImpl.calculateDividendYield(stockSymbol, price);
			assertNotNull(calculateDividendYield);
			System.out.println(dividendYield + "," + calculateDividendYield);
			assertEquals(dividendYield, calculateDividendYield, 0.0);
			
		} catch (InvalidStockException e) {
			e.printStackTrace(System.out);
			Logger.logDebugMessage("Message : " + e.getMessage());
			assertNull(e.getMessage(), e);
		}
	}

	/**
	 * Test method for {@link StockExchangeServiceImpl#calculateDividendYield(java.lang.String, double)}.
	 */
	@Test
	public void testCalculateDividendYieldForPrefferredStocks() {
		try {
			final String stockSymbol = "GIN";
			
			FixedDividendStock fixedDividendStock = (FixedDividendStock) stockDS.getStockData(stockSymbol);
			
			double dividend = (fixedDividendStock.getParValue() * fixedDividendStock.getFixedDividendPercentage() / 100.0);
			double price = stockDS.getStockData(stockSymbol).getParValue() + (stockDS.getStockData(stockSymbol).getParValue() * 0.05);
			
			double dividendYield = dividend / price ;
			final double calculateDividendYield = stockExchangeServiceImpl.calculateDividendYield(stockSymbol, price);
			assertNotNull(calculateDividendYield);
			assertEquals(dividendYield, calculateDividendYield, 0.0);
			
		} catch (InvalidStockException e) {
			e.printStackTrace(System.out);
			Logger.logDebugMessage("Message : " + e.getMessage());
			assertNull(e.getMessage(), e);
		}
	}


	/**
	 * Test method for {@link StockExchangeServiceImpl#calculateAllShareIndex()}.
	 */
	@Test
	public void testCalculateAllShareIndex() {
		try {
			assertNotNull(stockExchangeServiceImpl.calculateAllShareIndex());
		} catch (InvalidStockException e) {
			e.printStackTrace(System.out);
			Logger.logDebugMessage("Message : " + e.getMessage());
			assertNull(e.getMessage(), e);
		}
	}

	/**
	 * Test method for {@link StockExchangeServiceImpl#priceOverDividendRatio(java.lang.String, double)}.
	 */
	@Test
	public void testPriceOverDividendRatio() {
		try {
			final String stockSymbol = "POP";
			double dividend = stockDS.getStockData(stockSymbol).getLastDividend();
			double price = stockDS.getStockData(stockSymbol).getParValue() + (stockDS.getStockData(stockSymbol).getParValue() * 0.05);
			
			double priceOverDividend = price / dividend;
			final double calculatedPriceOverDividend = stockExchangeServiceImpl.priceOverDividendRatio(stockSymbol, price);
			
			assertNotNull(calculatedPriceOverDividend);
			assertEquals(priceOverDividend, calculatedPriceOverDividend, 0.0);
		} catch (InvalidStockException e) {
			e.printStackTrace(System.out);
			Logger.logDebugMessage("Message : " + e.getMessage());
			assertNull(e.getMessage(), e);
		}
	}

	/**
	 * Test method for {@link StockExchangeServiceImpl#(java.lang.String, int)}.
	 */
	@Test
	public void testVolumeWeightedStockPriceByTime() {
		try {
			assertNotNull(stockExchangeServiceImpl.calculateVolumeWeightedStockPrice("GIN", 2));
			Logger.logDebugMessage("Starting the simulator... " + " Done");

			Logger.logDebugMessage("\n\n");
			Logger.logDebugMessage("**************************** Current Stock Summary *****************************");
			List<Stock> stocks = stockExchangeServiceImpl.listAllStocksInMarket();
			for (Stock currStock : stocks) {

				if (currStock instanceof FixedDividendStock) {
					FixedDividendStock fixedStock = (FixedDividendStock) currStock;

					Logger.logDebugMessage(
							"\t" + fixedStock.getSymbol() + "\t" + String.format(" %-15s", fixedStock.getStockType()) + "\t"
									+ String.format(" %5d", (int) fixedStock.getLastDividend()) + "\t"
									+ fixedStock.getFixedDividendPercentage() + " %\t"
									+ String.format("%5d", (int) fixedStock.getParValue()));
				} else {
					Logger.logDebugMessage(
							"\t" + currStock.getSymbol() + "\t" + String.format(" %-15s", currStock.getStockType()) + "\t"
									+ String.format("%5d", (int) currStock.getLastDividend()) + "\t\t"
									+ String.format("%5d", (int) currStock.getParValue()));
				}
			}
			
			String stockSymbol = "POP";

			
			Logger.logDebugMessage("********************************************************************************");
			Logger.logDebugMessage("\n");
			Logger.logDebugMessage("Stock selected for monitoring is : " + stockSymbol);
			Logger.logDebugMessage("\n");
			Logger.logDebugMessage("**************************** Transactions **************************************");
			SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");


			double sumTradedPrice = 0.0;
			int sumQty = 0;
			
			int simulationTimeInMinutes = 2;
			int diffForCalculation = 1;

			for (int i = 0; i < simulationTimeInMinutes * 4; i++) {
				final double random = Math.random();

				long randomPercentage = Math.round(random * 100);

				int index = (int) randomPercentage / 10;
				int stockIndex = index % setOfSymbols.length;

				int buyIndicatorIndex = index % BuySellIndicator.values().length;
				
				int priceChangeIndex = index % percentageDiff.length;
				
				Stock stock = stockDS.getStockData(setOfSymbols[stockIndex]);

				double tradedPrice = stock.getParValue() + (stock.getParValue() * percentageDiff[priceChangeIndex] / 100);
				
				BuySellIndicator buySellIndicator = BuySellIndicator.values()[buyIndicatorIndex];

				int quantity = index;
				
				quantity = (quantity > 0)? quantity : 1; 
				
				if (i >= (diffForCalculation * 4) && stockSymbol.equals(setOfSymbols[stockIndex])) {
					sumTradedPrice += (tradedPrice * quantity);
					sumQty += quantity;
				}

				if (buySellIndicator == BuySellIndicator.BUY) {
					Logger.logDebugMessage("Purchase request raised for " + setOfSymbols[stockIndex] + " for $" + tradedPrice + "(" + percentageDiff[priceChangeIndex] + "% change)");
					stockExchangeServiceImpl.buyStock(setOfSymbols[stockIndex], quantity, tradedPrice);
				} else {
					Logger.logDebugMessage("Sell request raised for " + setOfSymbols[stockIndex] + " for $" + tradedPrice + "(" + percentageDiff[priceChangeIndex] + "% change)");
					stockExchangeServiceImpl.sellStock(setOfSymbols[stockIndex], quantity, tradedPrice);
				}
				Logger.logDebugMessage("----------------------------------------------------------------------------");
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			Logger.logDebugMessage(
					"**************************** REPORT for " + stockSymbol + "*****************************");
			Logger.logDebugMessage(
					String.format("Dividend Yield : %5.2f", stockExchangeServiceImpl.calculateDividendYield(stockSymbol, 20)));
			Logger.logDebugMessage(
					String.format("P/E Ratio : %5.2f", stockExchangeServiceImpl.priceOverDividendRatio(stockSymbol, 20)));
			final double calculateVolumeWeightedStockPrice = stockExchangeServiceImpl.calculateVolumeWeightedStockPrice(stockSymbol, diffForCalculation);
			Logger.logDebugMessage(String.format("Volume Weighted Stock Price based on trades in past 15 minutes : %5.2f", calculateVolumeWeightedStockPrice));
			final double expected = sumTradedPrice / sumQty;
			Logger.logDebugMessage(String.format("Expected: " + expected));
			Logger.logDebugMessage("GBCE All Share Index : " + stockExchangeServiceImpl.calculateAllShareIndex());
			Logger.logDebugMessage("*****************************************************************");
			
			assertEquals(expected, calculateVolumeWeightedStockPrice, 0.0);
			
		} catch (InvalidStockException e) {
			e.printStackTrace(System.out);
			Logger.logDebugMessage("Message : " + e.getMessage());
			assertNull(e.getMessage(), e);
		}
	}
	
	
	@Test
	public void testListOfAllStockSymbols() {
		
		List<String> listOfAllSymbols = stockExchangeServiceImpl.listAllStockSymbols();
		
		assertNotNull(listOfAllSymbols);
		
		Logger.logDebugMessage("List of all symbols :");
		for(String symbol : listOfAllSymbols) {
			Logger.logDebugMessage(symbol);
		}
	}
	
	@Test
	public void testListOfAllStock() {
		
		List<Stock> listOfAllStocks = stockExchangeServiceImpl.listAllStocksInMarket();
		
		assertNotNull(listOfAllStocks);
		
		Logger.logDebugMessage("List of all stocks :");
		for(Stock stock : listOfAllStocks) {
			Logger.logDebugMessage(stock.toString());
		}
	}

}
