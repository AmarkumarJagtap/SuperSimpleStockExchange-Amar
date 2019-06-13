package com.app.stockmarket;


import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class StockMarketControllerTest {


    @Test
    public void testCreateStocksSuccess(){
        Response res = get("http://localhost:8080/stockMarket/createStocksInMarket");

        assertEquals(200, res.getStatusCode());
        System.out.println(" res : "+res.asString());

    }

    @Test
    public void testCalcuateDividentYeild(){

        Response res = get("http://localhost:8080/stockMarket/calculateDividendYield/GIN/10");
        assertEquals("0.2", res.asString());

    }

    @Test
    public void testCalcuateDividentYeildFailure(){

        Response res = get("http://localhost:8080/stockMarket/calculateDividendYield/GJN/10");
        assertEquals(500, res.getStatusCode());

    }

    @Test
    public void testBuyStock(){
        baseURI="http://localhost:8080/stockMarket";

       given()
                .param("stockSymbol","GIN")
                .param("quantity", 10)
                .param("price", 2000)
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .post("/buy")
                .then().statusCode(200);


    }

    @Test
    public void testSellStock(){
        baseURI="http://localhost:8080/stockMarket";

        given()
                .param("stockSymbol","GIN")
                .param("quantity", 10)
                .param("price", 2000)
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .post("/sell")
                .then().statusCode(200);


    }

    @Test
    public void testCalculatePERatio(){
        Response res = get("http://localhost:8080/stockMarket/calculatePERatio/GIN/10");
        assertEquals("1.25", res.asString());
    }


}
