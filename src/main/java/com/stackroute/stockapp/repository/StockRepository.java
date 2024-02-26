package com.stackroute.stockapp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.stockapp.model.Stock;

@Repository
public interface StockRepository  extends MongoRepository<Stock, String>    {
        
         
        // create  query to find stock by country retrun List<Stock>
        public List<Stock> findByCountry(String country);

        // create  query to find stock by exchange  and Country retrun List<Stock>  
        public List<Stock> findByExchangeAndCountry(String exchange, String country);




}
