package com.stackroute.stockapp.model;

/*
 * create  the following fields
 * 
    "symbol": "AAPL",
    "name": "Apple Inc",
    "currency": "USD",
    "exchange": "NASDAQ",
    "mic_code": "XNGS",
    "country": "United States",
    "type": "Common Stock"

    Use Lombok to generate no-args constructor,All Argument constructor, getters, setters, and toString() method.
    Use @Document annotation to specify the collection name in mongoDB.
     
  
 */


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    @Id
    private String symbol;
    private String name;
    private String currency;
    private String exchange;
    private String mic_code;
    private String country;
    private String type;
}