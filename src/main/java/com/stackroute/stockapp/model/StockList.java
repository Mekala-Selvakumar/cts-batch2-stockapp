package com.stackroute.stockapp.model;

/*
 * data field should be type of List<Stock>
 * use Lombok to generate no-args constructor,All Argument constructor, getters, setters, and toString() method.
 */
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockList {
  
    private List<Stock> data;
}

 
