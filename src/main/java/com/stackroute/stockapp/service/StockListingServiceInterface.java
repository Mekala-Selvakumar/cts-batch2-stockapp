package com.stackroute.stockapp.service;

 /*
  * create method to  save stock  ,to delete the stock based on symbol,
    * to get all stocks, to get stock by symbol, to get stock by country, 
    to get stock by exchange and country
    *  
    * 

  */
import java.util.List;

import com.stackroute.stockapp.exceptions.StockAlreadyExistException;
import com.stackroute.stockapp.exceptions.StockNotFoundException;
import com.stackroute.stockapp.model.Stock;

public interface StockListingServiceInterface {
    public Stock saveStock(Stock stock) throws StockAlreadyExistException;
    public boolean deleteStock(String symbol) throws StockNotFoundException;
    public List<Stock> getAllStocks();
    public Stock getStockBySymbol(String symbol) throws StockNotFoundException;
    public List<Stock> getStockByCountry(String country);
    public List<Stock> getStockByExchangeAndCountry(String exchange, String country);
}

