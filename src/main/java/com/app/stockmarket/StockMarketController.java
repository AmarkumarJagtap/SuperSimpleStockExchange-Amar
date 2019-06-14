package com.app.stockmarket;


import com.app.stockmarket.domain.CommonStock;
import com.app.stockmarket.domain.FixedDividendStock;
import com.app.stockmarket.domain.Stock;
import com.app.stockmarket.exception.InvalidStockException;
import com.app.stockmarket.service.StockDataService;
import com.app.stockmarket.service.TradeService;
import com.app.stockmarket.service.StockExchangeService;
import com.app.stockmarket.types.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Rest service to call stock market APIs
 */
@RestController
@RequestMapping("/stockMarket")
public class StockMarketController {


    /**
     * Stock Data Service used to record stocks information
     */
    @Autowired
    StockDataService stockDS;

    /**
     * Trade service
     */
    @Autowired
    TradeService tradeService;

    /**
     * Stock exchange to perform all the operations
     */
    @Autowired
    StockExchangeService stockExchange;

    /**
     *
     * @param stockSymbol
     * @param price
     * @return
     * @throws InvalidStockException
     */
    @GetMapping("/calculateDividendYield/{stockSymbol}/{price}")
    public ResponseEntity<Double> calculateDividendYield(@PathVariable String stockSymbol, @PathVariable double price) throws InvalidStockException
    {
        return new ResponseEntity<>(stockExchange.calculateDividendYield(stockSymbol, price), HttpStatus.OK);

    }

    /**
     *
     * @param stockSymbol
     * @param price
     * @return
     * @throws InvalidStockException
     */
    @GetMapping("/calculatePERatio/{stockSymbol}/{price}")
    public ResponseEntity<Double>  calculatePERatio(@PathVariable String stockSymbol, @PathVariable double price) throws InvalidStockException
    {
        return new ResponseEntity<>(stockExchange.priceOverDividendRatio(stockSymbol, price), HttpStatus.OK);

    }

    /**
     *
     * @return
     */
    @GetMapping("/getAllStocks")
    public ResponseEntity<List<Stock>> getAllStocks(){
        return new ResponseEntity<> (stockExchange.listAllStocksInMarket(), HttpStatus.OK);
    }

    /**
     *
     * @return
     */
    @GetMapping("/getAllStockSymbols")
    public ResponseEntity<List<String>> getAllStockSymbols(){
        return new ResponseEntity<> (stockExchange.listAllStockSymbols(), HttpStatus.OK);
    }

    /**
     *
     * @param stockSymbol
     * @param quantity
     * @param price
     * @return
     * @throws InvalidStockException
     */
    @PostMapping(value = "/sell")
    public ResponseEntity<String> sell(@Valid String stockSymbol, @Valid int quantity, @Valid double price) throws InvalidStockException{
        return new ResponseEntity<> (stockExchange.sellStock(stockSymbol,quantity,price) ? "Stock sold successfully": "Something went wrong, please try again",  HttpStatus.OK);
    }

    /**
     *
     * @param stockSymbol
     * @param quantity
     * @param price
     * @return
     * @throws InvalidStockException
     */
    @PostMapping(value = "/buy")
    public ResponseEntity<String>  buy(@Valid String stockSymbol, @Valid int quantity, @Valid double price) throws InvalidStockException{
        return new ResponseEntity<> (stockExchange.buyStock(stockSymbol,quantity,price) ? "Stock bought successfully" : "Something went wrong, please try again", HttpStatus.OK);
    }

    /**
     *
     * @param stockSymbol
     * @return
     * @throws InvalidStockException
     */
    @GetMapping("/volWeightedStockPrice/{stockSymbol}")
    public ResponseEntity<Double>  calculateVolumeWeightedStockPrice(@PathVariable String stockSymbol) throws InvalidStockException
    {
        return new ResponseEntity<> (stockExchange.calculateVolumeWeightedStockPrice(stockSymbol), HttpStatus.OK);
    }


    /**
     *
     * @return
     * @throws InvalidStockException
     */
    @GetMapping("/calculateAllShareIndex")
    public ResponseEntity<Double> calculateAllShareIndex() throws InvalidStockException{
        return new ResponseEntity<> (stockExchange.calculateAllShareIndex(), HttpStatus.OK);
    }

    /**
     *
     * @param commonStock
     * @return
     */

    @PostMapping(value = "/createCommonStock", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createCommonStock(@RequestBody CommonStock commonStock) throws  InvalidStockException{
        tradeService.setStockDataService(stockDS);
        stockExchange.setName("GBCE");
        stockExchange.setCountry("UK");
        stockExchange.registerStockDataService(stockDS);
        stockExchange.registerTradeService(tradeService);

        return new ResponseEntity<> (stockExchange.createStockInMarket(commonStock) ? "Common Stock added successfully" : "Something went wrong, please try again", HttpStatus.OK);

    }

    /**
     *
     * @param commonStocks
     * @return
     * @throws InvalidStockException
     */
    @PostMapping(value = "/createCommonStocks", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createCommonStocks(@RequestBody List<CommonStock> commonStocks) throws  InvalidStockException{
        tradeService.setStockDataService(stockDS);
        stockExchange.setName("GBCE");
        stockExchange.setCountry("UK");
        stockExchange.registerStockDataService(stockDS);
        stockExchange.registerTradeService(tradeService);
        for(CommonStock commonStock:commonStocks){
            stockExchange.createStockInMarket(commonStock);
        }

        return new ResponseEntity<> ( "Common Stock added successfully", HttpStatus.OK);

    }

    /**
     *
     * @param stocks
     * @return
     * @throws InvalidStockException
     */
    @PostMapping(value = "/createPreferredStock", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createPreferredStock(@RequestBody List<FixedDividendStock> stocks) throws  InvalidStockException{
        tradeService.setStockDataService(stockDS);
        stockExchange.setName("GBCE");
        stockExchange.setCountry("UK");
        stockExchange.registerStockDataService(stockDS);
        stockExchange.registerTradeService(tradeService);
        for(FixedDividendStock stock:stocks){
            stockExchange.createStockInMarket(stock);
        }

        return new ResponseEntity<> ( "Preferred Stocks added successfully", HttpStatus.OK);

    }

    /**
     *
     * @param stock
     * @return
     */
    @PostMapping(value = "/createPreferredStock", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createPreferredStock(@RequestBody FixedDividendStock stock) throws  InvalidStockException{
        tradeService.setStockDataService(stockDS);
        stockExchange.setName("GBCE");
        stockExchange.setCountry("UK");
        stockExchange.registerStockDataService(stockDS);
        stockExchange.registerTradeService(tradeService);

        return new ResponseEntity<> (stockExchange.createStockInMarket(stock) ? "Preferred Stock added successfully" : "Something went wrong, please try again", HttpStatus.OK);

    }



    /**
     *
     * @return
     * @throws InvalidStockException
     */
    @GetMapping("/createStocksInMarket")
    public ResponseEntity<List<Stock>> createStocksInMarket() throws InvalidStockException{

        tradeService.setStockDataService(stockDS);
        stockExchange.setName("GBCE");
        stockExchange.setCountry("UK");
        stockExchange.registerStockDataService(stockDS);
        stockExchange.registerTradeService(tradeService);

        Stock stock = new CommonStock();
        stock.setSymbol("TEA");
        stock.setLastDividend(0);
        stock.setParValue(100);
        stock.setCurrency(Currency.USD);
        stockExchange.createStockInMarket(stock);

        stock = new CommonStock();
        stock.setSymbol("POP");
        stock.setParValue(100);
        stock.setLastDividend(8);
        stock.setCurrency(Currency.USD);
        stockExchange.createStockInMarket(stock);

        stock = new CommonStock();
        stock.setSymbol("ALE");
        stock.setLastDividend(23);
        stock.setParValue(60);
        stock.setCurrency(Currency.USD);
        stockExchange.createStockInMarket(stock);

        stock = new CommonStock();
        stock.setSymbol("JOE");
        stock.setLastDividend(13);
        stock.setParValue(250);
        stock.setCurrency(Currency.USD);
        stockExchange.createStockInMarket(stock);

        FixedDividendStock stock1 = new FixedDividendStock();
        stock1.setSymbol("GIN");
        stock1.setParValue(100);
        stock1.setCurrency(Currency.USD);
        stock1.setLastDividend(8);
        stock1.setFixedDividendPercentage(2);
        stockExchange.createStockInMarket(stock1);

       return new ResponseEntity<>(stockExchange.listAllStocksInMarket(), HttpStatus.OK);
    }

}
