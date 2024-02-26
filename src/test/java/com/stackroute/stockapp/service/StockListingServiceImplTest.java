package com.stackroute.stockapp.service;

// FILEPATH: /D:/cts/cts-batch2/stockapp/src/test/java/com/stackroute/stockapp/service/StockListingServiceImplTest.java

import com.stackroute.stockapp.exceptions.StockAlreadyExistException;
import com.stackroute.stockapp.exceptions.StockNotFoundException;
import com.stackroute.stockapp.model.Stock;
import com.stackroute.stockapp.model.StockList;
import com.stackroute.stockapp.repository.StockRepository;
import com.stackroute.stockapp.service.StockListingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StockListingServiceImplTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private StockListingServiceImpl stockListingService;

    private Stock stock1, stock2;
    private List<Stock> stockList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        stock1 = new Stock("AAPL", "Apple Inc", "USD", "NASDAQ", "XNGS", "United States", "Common Stock");
        stock2 = new Stock("MSFT", "Microsoft Corporation", "USD", "NASDAQ", "XNGS", "United States", "Common Stock");
        stockList = Arrays.asList(stock1, stock2);
    }

    @Test
    public void saveStockTest() throws StockAlreadyExistException {
        when(stockRepository.findById(stock1.getSymbol())).thenReturn(Optional.empty());
        when(stockRepository.save(stock1)).thenReturn(stock1);

        Stock result = stockListingService.saveStock(stock1);
        assertEquals(stock1, result);

        verify(stockRepository, times(1)).findById(stock1.getSymbol());
        verify(stockRepository, times(1)).save(stock1);
    }

    @Test
    public void deleteStockTest() throws StockNotFoundException {
        when(stockRepository.findById(stock1.getSymbol())).thenReturn(Optional.of(stock1));
        boolean result = stockListingService.deleteStock(stock1.getSymbol());
        assertTrue(result);
        verify(stockRepository, times(1)).findById(stock1.getSymbol());
        verify(stockRepository, times(1)).deleteById(stock1.getSymbol());
    }

    //create Negative test case for deleteStock
@Test  
    public void deleteStockTestNegative() throws StockNotFoundException {
        when(stockRepository.findById(stock1.getSymbol())).thenReturn(Optional.empty());
        assertThrows(StockNotFoundException.class, () -> stockListingService.deleteStock(stock1.getSymbol()));
        verify(stockRepository, times(1)).findById(stock1.getSymbol());
        verify(stockRepository, times(0)).deleteById(stock1.getSymbol());
    }






    @Test
    public void getAllStocksTest() {
        when(stockRepository.findAll()).thenReturn(stockList);

        List<Stock> result = stockListingService.getAllStocks();
        assertEquals(2, result.size());

        verify(stockRepository, times(1)).findAll();
    }

    @Test
    public void getStockBySymbolTest() throws StockNotFoundException {
        when(stockRepository.findById(stock1.getSymbol())).thenReturn(Optional.of(stock1));

        Stock result = stockListingService.getStockBySymbol(stock1.getSymbol());
        assertEquals(stock1, result);

        verify(stockRepository, times(1)).findById(stock1.getSymbol());
    }

    @Test
    public void getStockByExchangeAndCountryTest() {
        when(stockRepository.findByExchangeAndCountry(stock1.getExchange(), stock1.getCountry())).thenReturn(stockList);

        List<Stock> result = stockListingService.getStockByExchangeAndCountry(stock1.getExchange(), stock1.getCountry());
        assertEquals(2, result.size());

        verify(stockRepository, times(1)).findByExchangeAndCountry(stock1.getExchange(), stock1.getCountry());
    }

    // Assuming StockList is a class with a getData method that returns a List<Stock>
    @Test
    public void getStockByCountryTest() {
        StockList mockStockList = mock(StockList.class);
        when(mockStockList.getData()).thenReturn(stockList);
        when(restTemplate.getForObject(anyString(), eq(StockList.class))).thenReturn(mockStockList);
         stockListingService.setAPI_URL("https://api.twelvedata.com/stocks");
        List<Stock> result = stockListingService.getStockByCountry(stock1.getCountry());
        assertEquals(2, result.size());

        verify(restTemplate, times(1)).getForObject(anyString(), eq(StockList.class));
    }
}

