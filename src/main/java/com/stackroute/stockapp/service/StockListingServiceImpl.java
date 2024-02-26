package com.stackroute.stockapp.service;

 /*
  * class implements StockListingServiceInterface
  * throws StockNotFoundException if stock not found
 * throws StockAlreadyExistsException if stock already exists
 * Use RestTemplate  bean to call  third party api
 * Use RestTemplate to get stocks from third party API (TewleveData)
* get the value of API_URL from application.properties
  */
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.stackroute.stockapp.exceptions.StockAlreadyExistException;
import com.stackroute.stockapp.exceptions.StockNotFoundException;
import com.stackroute.stockapp.model.Stock;
import com.stackroute.stockapp.model.StockList;
import com.stackroute.stockapp.repository.StockRepository;

import  org.springframework.beans.factory.annotation.Value;

import org.springframework.web.util.UriComponentsBuilder;


@Service
public class StockListingServiceImpl implements StockListingServiceInterface {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StockRepository stockRepository;
    // get the value of API_URL from application.properties
     @Value("${api.url}")
     private  String API_URL;

     public void setAPI_URL(String aPI_URL) {
         API_URL = aPI_URL;
     }

    @Override
    public Stock saveStock(Stock stock) throws StockAlreadyExistException {
        Optional<Stock> stock1 = stockRepository.findById(stock.getSymbol());
        if (stock1.isPresent()) {
            throw new StockAlreadyExistException("Stock already exists");
        }
        return stockRepository.save(stock);
    }

    @Override
    public boolean deleteStock(String symbol) throws StockNotFoundException {
        Optional<Stock> stock = stockRepository.findById(symbol);
        if (!stock.isPresent()) {
            throw new StockNotFoundException("Stock not found");
        }
        stockRepository.deleteById(symbol);
        return true;
    }

    @Override
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    @Override
    public Stock getStockBySymbol(String symbol) throws StockNotFoundException {
        Optional<Stock> stock = stockRepository.findById(symbol);
        if (!stock.isPresent()) {
            throw new StockNotFoundException("Stock not found");
        }
        return stock.get();
    }

   

    @Override
    public List<Stock> getStockByExchangeAndCountry(String exchange, String country) {
        return stockRepository.findByExchangeAndCountry(exchange, country);
    }

    @Override
    public List<Stock> getStockByCountry(String country) {

// use UriComponentsBuilder to build the URL
// use RestTemplate to get stocks from third party API (TewleveData)
// use RestTemplate  bean to call  third party api
// get the value of API_URL from application.properties
      
String url =UriComponentsBuilder.fromUriString(API_URL).queryParam("country", country).toUriString();
StockList stockList = restTemplate.getForObject(url, StockList.class);
return stockList.getData();
     
    }
   
}

