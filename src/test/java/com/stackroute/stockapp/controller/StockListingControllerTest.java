package com.stackroute.stockapp.controller;

 import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
 import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
 import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.stackroute.stockapp.exceptions.GlobalExceptionHandler;
import com.stackroute.stockapp.exceptions.StockAlreadyExistException;
import com.stackroute.stockapp.exceptions.StockNotFoundException;
import com.stackroute.stockapp.model.Stock;
import com.stackroute.stockapp.service.StockListingServiceInterface;
  
 
@WebMvcTest(StockListingController.class)
public class StockListingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockListingServiceInterface stockService;

    @InjectMocks
    private StockListingController stockController;

    private Stock stock1;
    private Stock stock2;

    @BeforeEach
    public void setup() {   
        MockitoAnnotations.openMocks(this);
        //MockMvcBuilders.webAppContextSetup(context).build();
      //  mockMvc = MockMvcBuilders.standaloneSetup(stockController).build();
       mockMvc = MockMvcBuilders.standaloneSetup(stockController).setControllerAdvice(new GlobalExceptionHandler()).build();
      stock1 = new Stock("AAPL", "Apple Inc", "USD", "NASDAQ", "XNGS", "United States", "Common Stock");
        stock2 = new Stock("GOOGL", "Alphabet Inc", "USD", "NASDAQ", "XNGS", "United States", "Common Stock");

    }

    @Test
    public void testSaveStock() throws Exception {
        when(stockService.saveStock(any(Stock.class))).thenReturn(stock1);
        mockMvc.perform(post("/api/v1/stock")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"symbol\":\"AAPL\",\"name\":\"Apple Inc.\",\"exchange\":\"NASDAQ\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.symbol").value("AAPL"))
                .andExpect(jsonPath("$.name").value("Apple Inc"));
                
                 // .content(new ObjectMapper().writeValueAsString(stock)))
	// 			.andExpect(status().isCreated()).andDo(print());
    }

    @Test
    public void testDeleteStock() throws Exception {
        when(stockService.deleteStock("AAPL")).thenReturn(true);

        mockMvc.perform(delete("/api/v1/stock/AAPL"))
                .andExpect(status().isOk())
                .andExpect(content().string("Stock deleted"));
    }

    @Test
    public void testGetAllStocks() throws Exception {
      
        List<Stock> stocks = Arrays.asList(stock1, stock2);
        when(stockService.getAllStocks()).thenReturn(stocks);

        mockMvc.perform(get("/api/v1/stock"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].symbol").value("AAPL"))
                .andExpect(jsonPath("$[0].name").value("Apple Inc"))
                .andExpect(jsonPath("$[0].exchange").value("NASDAQ"))
                .andExpect(jsonPath("$[1].symbol").value("GOOGL"))
                .andExpect(jsonPath("$[1].name").value("Alphabet Inc"))
                .andExpect(jsonPath("$[1].exchange").value("NASDAQ"));
    }

    @Test
    public void testGetStockBySymbol() throws Exception {
      
        when(stockService.getStockBySymbol("AAPL")).thenReturn(stock1);

        mockMvc.perform(get("/api/v1/stock/AAPL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol").value("AAPL"))
                .andExpect(jsonPath("$.name").value("Apple Inc"))
                .andExpect(jsonPath("$.exchange").value("NASDAQ"));
    }

    @Test
    public void testGetStockByCountry() throws Exception {
       
        List<Stock> stocks = Arrays.asList(stock1, stock2);
        when(stockService.getStockByCountry("United States")).thenReturn(stocks);

        mockMvc.perform(get("/api/v1/stock/country/United States"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].symbol").value("AAPL"))
                .andExpect(jsonPath("$[0].name").value("Apple Inc"))
                .andExpect(jsonPath("$[0].exchange").value("NASDAQ"))
                .andExpect(jsonPath("$[1].symbol").value("GOOGL"))
                .andExpect(jsonPath("$[1].name").value("Alphabet Inc"))
                .andExpect(jsonPath("$[1].exchange").value("NASDAQ"));
    }

    @Test
    public void testGetStockByExchangeAndCountry() throws Exception {
        
        List<Stock> stocks = Arrays.asList(stock1, stock2);
        when(stockService.getStockByExchangeAndCountry("NASDAQ", "United States")).thenReturn(stocks);

        mockMvc.perform(get("/api/v1/stock/exchange/NASDAQ/country/United States"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].symbol").value("AAPL"))
                .andExpect(jsonPath("$[0].name").value("Apple Inc"))
                .andExpect(jsonPath("$[0].exchange").value("NASDAQ"))
                .andExpect(jsonPath("$[1].symbol").value("GOOGL"))
                .andExpect(jsonPath("$[1].name").value("Alphabet Inc"))
                .andExpect(jsonPath("$[1].exchange").value("NASDAQ"));
    }


    //create negative case for  deleteStock

    @Test 
    public void testDeleteStockNegative() throws Exception {
      //  when(stockService.deleteStock("AAPL")).thenThrow(new StockNotFoundException("Stock not found"));
      doThrow(new StockNotFoundException("Stock not found")).when(stockService).deleteStock("AAPL");
        mockMvc.perform(delete("/api/v1/stock/AAPL"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Stock not found"));
    }   

//create negative test case for saveStock
    @Test
    public void testSaveStockNegative() throws Exception {
        when(stockService.saveStock(any(Stock.class))).thenThrow(new StockAlreadyExistException("Stock already exist"));
        mockMvc.perform(post("/api/v1/stock")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"symbol\":\"AAPL\",\"name\":\"Apple Inc.\",\"exchange\":\"NASDAQ\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Stock already exist"));
    }
     

  
}
