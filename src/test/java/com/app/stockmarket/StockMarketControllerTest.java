package com.app.stockmarket;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;

public class StockMarketControllerTest {


    @Test
    public void testCreateStocksSuccess(){
        Response res = get("http://localhost:8080/stockMarket/createStocksInMarket");

        assertEquals(200, res.getStatusCode());

    }

    @Test
    public void testCalcuateDividentYeild(){

        Response res = get("http://localhost:8080/stockMarket/calculateDividendYield/GIN/10");
        assertEquals("0.2", res.asString());

    }

    @Test
    public void testGetAllStocks(){
        Response res = get("http://localhost:8080/stockMarket/getAllStocks");
        assertEquals(200, res.getStatusCode());
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
    public void testCreateCommonStock() {


        baseURI="http://localhost:8080/stockMarket";

        RequestSpecification request = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        requestParams.put("symbol", "TEA");
        requestParams.put("parValue", 100);
        requestParams.put("currency", "USD");
        requestParams.put("lastDividend", 0);

        request.body(requestParams.toString());
        request.header("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        Response response = request.post("/createCommonStock");
        assertEquals(200, response.getStatusCode());

    }

    @Test
    public void testCreateCommonStocks(){

        baseURI="http://localhost:8080/stockMarket";

        RequestSpecification request = RestAssured.given();

        JSONArray jsonArray = new JSONArray();
        JSONObject requestParams = new JSONObject();
        requestParams.put("symbol", "TEA");
        requestParams.put("parValue", 100);
        requestParams.put("currency", "USD");
        requestParams.put("lastDividend", 0);

        JSONObject requestParams1 = new JSONObject();
        requestParams1.put("symbol", "JOE");
        requestParams1.put("parValue", 80);
        requestParams1.put("currency", "USD");
        requestParams1.put("lastDividend", 250);

        jsonArray.put(requestParams);
        jsonArray.put(requestParams1);

        request.body(jsonArray.toString());
        request.header("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        Response response = request.post("/createCommonStocks");
        assertEquals(200, response.getStatusCode());

    }

    @Test
    public void testCreatePreferredStock() {


        baseURI="http://localhost:8080/stockMarket";

        RequestSpecification request = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        requestParams.put("symbol", "GIN");
        requestParams.put("parValue", 100);
        requestParams.put("currency", "USD");
        requestParams.put("lastDividend", 0);
        requestParams.put("fixedDividendPercentage", 2);

        request.body(requestParams.toString());
        request.header("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        Response response = request.post("/createPreferredStock");
        assertEquals(200, response.getStatusCode());

    }

    @Test
    public void testCreatePreferredStocks() {


        baseURI="http://localhost:8080/stockMarket";

        RequestSpecification request = RestAssured.given();

        JSONArray jsonArray = new JSONArray();

        JSONObject requestParams = new JSONObject();
        requestParams.put("symbol", "GIN");
        requestParams.put("parValue", 100);
        requestParams.put("currency", "USD");
        requestParams.put("lastDividend", 0);
        requestParams.put("fixedDividendPercentage", 2);

        JSONObject requestParams1 = new JSONObject();
        requestParams1.put("symbol", "TOE");
        requestParams1.put("parValue", 80);
        requestParams1.put("currency", "USD");
        requestParams1.put("lastDividend", 40);
        requestParams1.put("fixedDividendPercentage", 23);

        jsonArray.put(requestParams);
        jsonArray.put(requestParams1);

        request.body(jsonArray.toString());
        request.header("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        Response response = request.post("/createPreferredStocks");
        assertEquals(200, response.getStatusCode());

    }

    @Ignore
    @Test
    public void testCalculatePERatio(){
        Response res = get("http://localhost:8080/stockMarket/calculatePERatio/GIN/10");
        assertEquals("1.25", res.asString());
    }


}
