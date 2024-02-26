package com.stackroute.stockapp.repository;

import org.junit.jupiter.api.Test;
 /*
  * create  DataMongoTest  for testing
  *  As a part  of setup delete all the records from the database
    *  write test case to save stock
    *  write test case to delete stock
    *  write test case to get all stocks
    *  write test case to get stock by symbol
    *  write test case to get stock by country
    *  write test case to get stock by exchange and country
    create  two stock objects and save them in the database
    autowire  StockRepository
  */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
 

import com.stackroute.stockapp.model.Stock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
 
 




@DataMongoTest
public class StockRepositoryTest {


    @Autowired
    private StockRepository stockRepository;

    private Stock stock1;
    private Stock stock2;

    @BeforeEach
    public void setUp() {
        stockRepository.deleteAll();
        stock1 =new Stock("AAPL", "Apple Inc", "USD", "NASDAQ", "XNGS", "United States", "Common Stock");   
        stock2 = new Stock("MSFT", "Microsoft Corporation", "USD", "NASDAQ", "XNGS", "United States", "Common Stock"); 
    }

    @Test
    @DisplayName("Test for saving stock")
    public void testSaveStock() {
        stockRepository.save(stock1);
        Stock fetchStock = stockRepository.findById(stock1.getSymbol()).get();
        assertNotNull(fetchStock);
        assertEquals(fetchStock.getSymbol(), stock1.getSymbol());
    }

    @Test
    @DisplayName("Test for deleting stock")
    public void testDeleteStock() {
        stockRepository.save(stock1);
        Stock fetchStock = stockRepository.findById(stock1.getSymbol()).get();
        assertNotNull(fetchStock);
        stockRepository.delete(fetchStock);
        Optional<Stock> optional = stockRepository.findById(stock1.getSymbol());
        assertEquals(Optional.empty(), optional);
    }

    @Test
    @DisplayName("Test for getting all stocks")
    public void testGetAllStocks() {
        stockRepository.save(stock1);
        stockRepository.save(stock2);
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).size().isEqualTo(2);
    }
    @Test
    @DisplayName("Test for getting stock by symbol")
    public void testGetStockBySymbol() {
        stockRepository.save(stock1);
        Stock fetchStock = stockRepository.findById(stock1.getSymbol()).get();
        assertNotNull(fetchStock);
        assertEquals(fetchStock.getSymbol(), stock1.getSymbol());
    }
    @Test
    @DisplayName("Test for getting stock by country")
    public void testGetStockByCountry() {
        stockRepository.save(stock1);
        stockRepository.save(stock2);
        List<Stock> stockList = stockRepository.findByCountry("United States");
        assertThat(stockList).size().isEqualTo(2);
    }
    @Test
    @DisplayName("Test for getting stock by exchange and country")
    public void testGetStockByExchangeAndCountry() {
        stockRepository.save(stock1);
        stockRepository.save(stock2);
        List<Stock> stockList = stockRepository.findByExchangeAndCountry("NASDAQ", "United States");
        assertThat(stockList).size().isEqualTo(2);
    }

    //creete negative test case for  getting stock by symbol
    @Test
    @DisplayName("Test for getting stock by symbol")
    public void testGetStockBySymbolNegative() {
        stockRepository.save(stock1);
        Stock fetchStock = stockRepository.findById(stock2.getSymbol()).orElse(null); 
        assertNull(fetchStock);
        
    }


}
 


 

 


 


 