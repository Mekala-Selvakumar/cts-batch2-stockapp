package com.stackroute.stockapp.controller;
/*
 * Create a RestController class with a methods available
 * in the  StockService class.
 * Autowire the StorkService class in the StockListingController class.
 * use throws  class to handle the exceptions
 *   
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stackroute.stockapp.exceptions.StockAlreadyExistException;
import com.stackroute.stockapp.exceptions.StockNotFoundException;
import com.stackroute.stockapp.model.Stock;
import com.stackroute.stockapp.service.StockListingServiceInterface;

@RestController
@RequestMapping("/api/v1/stock")
public class StockListingController {
    @Autowired
    private StockListingServiceInterface stockService;

    @PostMapping 
    public ResponseEntity<Stock> saveStock(@RequestBody Stock stock) throws  StockAlreadyExistException {
        Stock savedStock = stockService.saveStock(stock);
        return new ResponseEntity<Stock>(savedStock, HttpStatus.CREATED);
    }

    @DeleteMapping("/{symbol}")
    public ResponseEntity<String> deleteStock(@PathVariable String symbol) throws StockNotFoundException {
     
        boolean flag = stockService.deleteStock(symbol);
        return new ResponseEntity<String>("Stock deleted", HttpStatus.OK);
    }


    //from the database
    @GetMapping      
    public ResponseEntity<List<Stock>> getAllStocks() {
        return new ResponseEntity<List<Stock>>(stockService.getAllStocks(), HttpStatus.OK);
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<Stock> getStockBySymbol(@PathVariable String symbol) throws Exception {
        Stock stock = stockService.getStockBySymbol(symbol);
        return new ResponseEntity<Stock>(stock, HttpStatus.OK);
    }

     //twelevedata api
    @GetMapping("/country/{country}")
    public ResponseEntity<List<Stock>> getStockByCountry(@PathVariable String country) {
        return new ResponseEntity<List<Stock>>(stockService.getStockByCountry(country), HttpStatus.OK);
    }

    @GetMapping("/exchange/{exchange}/country/{country}")
    public ResponseEntity<List<Stock>> getStockByExchangeAndCountry(@PathVariable String exchange, @PathVariable String country) {
        return new ResponseEntity<List<Stock>>(stockService.getStockByExchangeAndCountry(exchange, country), HttpStatus.OK);
    }
}

 



 



