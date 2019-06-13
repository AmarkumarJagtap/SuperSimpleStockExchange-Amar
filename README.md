# SuperSimpleStockMarket Project

## Overview
This Project contains source code and classes for Super Simple Stock Market which does the below:

a. For a given stock,
	i. Given any price as input, calculate the dividend yield
	ii. Given any price as input, calculate the P/E Ratio
	iii. Record a trade, with timestamp, quantity of shares, buy or sell indicator and traded price
	iv. Calculate Volume Weighted Stock Price based on trades in past 15 minutes

b. Calculate the GBCE All Share Index using the geometric mean of prices for all stocks

## Important Classes
1. com.app.stockmarket.StockMarketController : Class containing rest api's to  call and execute the StockMarket API

2. com.app.stockmarket.service.StockExchangeService : Interface to define operations on stock market to buy/sell stock, create new stock, calculate stock data

3. com.app.stockmarket.service.impl.StockExchangeServiceImpl : An abstract implemnentation of StockExchangeAPI

4. com.app.stockmarket.api.IStockAPI : Interface for calculative operations on stock

5. com.app.stockmarket.api.CommonStockAPI: Implementation of IStockAPI on Common type of stocks

6. com.app.stockmarket.api.PreferredStockAPI: Implementation of IStockAPI on Preferred type of stocks

7. com.app.stockmarket.domain.TradeTransaction: Class to encapsulate Trade Transaction Record


## How to run:

### Using Maven

	1. Download the zip to a local directory 
	2. With all maven settings run mvn spring-boot:run
	3. With postman or browser hit the rest APIs and get the result
	4. Refer Test Classes to know how it will work	
	
	Below are the endpoints - 
	1. http://localhost:8080/stockMarket/createStocksInMarket
	2. http://localhost:8080/stockMarket/getAllStocks
	3. http://localhost:8080/stockMarket/buy?stockSymbol=GIN&quantity=10&price=20000  -- POST request
	4. http://localhost:8080/stockMarket/sell?stockSymbol=GIN&quantity=10&price=20000 -- POST request
	5. http://localhost:8080/stockMarket/calculateDividendYield/{stockSymbol}/{price}
	6. http://localhost:8080/stockMarket/calculatePERatio/{stockSymbol}/{price}
	7. http://localhost:8080/stockMarket/volWeightedStockPrice/{stockSymbol}/{minutes}
	8. http://localhost:8080/stockMarket/calculateAllShareIndex
	
	


