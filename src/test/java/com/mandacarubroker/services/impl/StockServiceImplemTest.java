package com.mandacarubroker.services.impl;


import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.dto.RequestStockDTO;
import com.mandacarubroker.repositories.StockRepository;
import com.mandacarubroker.service.Implementation.StockServiceImplem;
import com.mandacarubroker.service.exceptions.DataIntegratyViolationException;
import com.mandacarubroker.service.exceptions.StockNotFoundException;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.mandacarubroker.service.Implementation.StockServiceImplem.validateRequestStockDTO;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class StockServiceImplemTest {

    private static final String ID  = "12345";
    private static final Integer INDEX   = 0;
    private static final String SYMBOL    = "AABB2";
    private static final String COMPANYNAME    = "Cuida";
    private static final Double PRICE = 111D;

    private static final String STOCK_NOT_FOUND = "Stock not found";

    private static final String STOCK_ALREADY_REGISTERED = "Stock already registered with this symbol";

    private static final List<String> CONSTRAINT = Arrays.asList("Validation failed. Details: [companyName: Company name cannot be blank]",
            "Validation failed. Details: [symbol: Symbol must be 4 letters followed by 1 number]");





    @Autowired
    private StockServiceImplem service;

    @MockBean
    private StockRepository repository;


    private Stock stock;
    private RequestStockDTO stockDTO;
    private Optional<Stock> optionalStock;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startStock();
    }

    @Test
    void whenFindByIdThenReturnAnStockInstance() {
        when(repository.findById(anyString())).thenReturn(optionalStock);

        Stock response = service.getStockById(ID);

        assertNotNull(response);

        assertEquals(Stock.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(SYMBOL, response.getSymbol());
        assertEquals(COMPANYNAME, response.getCompanyName());
        assertEquals(PRICE, response.getPrice());
    }

    @Test
    void whenFindByIdThenReturnAnStockNotFoundException() {

        when(repository.findById(anyString()))
                .thenThrow(new StockNotFoundException(STOCK_NOT_FOUND));

        try{
            service.getStockById(ID);
        } catch (Exception ex) {
            assertEquals(StockNotFoundException.class, ex.getClass());
            assertEquals(STOCK_NOT_FOUND, ex.getMessage());

        }
    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() {
        when(repository.findAll()).thenReturn(List.of(stock));

        List<Stock> response = service.getAllStocks();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(Stock.class, response.get(INDEX).getClass());

        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(SYMBOL, response.get(INDEX).getSymbol());
        assertEquals(COMPANYNAME, response.get(INDEX).getCompanyName());
        assertEquals(PRICE, response.get(INDEX).getPrice());
    }

    @Test
    void whenCreateThenReturnSuccess() {
        when(repository.save(any())).thenReturn(stock);

        Stock response = service.validateAndCreateStock(stockDTO);

        assertNotNull(response);
        assertEquals(Stock.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(SYMBOL, response.getSymbol());
        assertEquals(COMPANYNAME, response.getCompanyName());
        assertEquals(PRICE, response.getPrice());
    }

    @Test
    void whenCreateThenReturnAnConstraintViolationException() {
        when(repository.findById(anyString())).thenReturn(optionalStock);

        try{
            service.validateAndCreateStock(stockDTO);
        } catch (Exception ex) {
            assertEquals(ConstraintViolationException.class, ex.getClass());

            assertTrue(CONSTRAINT.contains(ex.getMessage()));

        }
    }


    @Test
    void whenCreateThenReturnAnDataIntegrityViolationException() {
        when(repository.findBySymbol(anyString())).thenReturn(optionalStock);

        try{
            service.validateAndCreateStock(stockDTO);
        } catch (Exception ex) {
            assertEquals(DataIntegratyViolationException.class, ex.getClass());
            assertEquals(STOCK_ALREADY_REGISTERED, ex.getMessage());
        }
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(repository.findById(eq(ID))).thenReturn(Optional.of(stock));
        when(repository.save(any())).thenReturn(stock);

        Stock response = service.validateAndUpdateStock(ID, stockDTO);

        assertNotNull(response);
        assertEquals(Stock.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(SYMBOL, response.getSymbol());
        assertEquals(COMPANYNAME, response.getCompanyName());
        assertEquals(PRICE, response.getPrice());


        System.out.println("Response: " + response);
        System.out.println("Response Class: " + response.getClass());
        System.out.println("ID: " + response.getId());
        System.out.println("Symbol: " + response.getSymbol());
        System.out.println("CompanyName: " + response.getCompanyName());
        System.out.println("Price: " + response.getPrice());
    }

    @Test
    void whenUpdateThenReturnAnConstraintViolationException() {
        when(repository.findById(anyString())).thenReturn(optionalStock);

        try{
            validateRequestStockDTO(stockDTO);
        } catch (Exception ex) {
            assertEquals(ConstraintViolationException.class, ex.getClass());
            assertTrue(CONSTRAINT.contains(ex.getMessage()));

        }
    }


    @Test
    void whenUpdateThenReturnAnDataIntegrityViolationException() {
        when(repository.findById(eq(ID))).thenReturn(Optional.of(stock));
        when(repository.findBySymbol(anyString())).thenReturn(optionalStock);

        try{
            service.validateAndUpdateStock(ID,stockDTO);
        } catch (Exception ex) {
            assertEquals(DataIntegratyViolationException.class, ex.getClass());
            assertEquals(STOCK_ALREADY_REGISTERED, ex.getMessage());
        }
    }

    @Test
    void whenUpdateThenReturnAnStockNotFoundException() {

        when(repository.findById(anyString()))
                .thenThrow(new StockNotFoundException(STOCK_NOT_FOUND));

        try{
            service.validateAndUpdateStock(ID,stockDTO);
        } catch (Exception ex) {
            assertEquals(StockNotFoundException.class, ex.getClass());
            assertEquals(STOCK_NOT_FOUND, ex.getMessage());

        }
    }

    @Test
    void deleteWithSuccess() {
        when(repository.findById(anyString())).thenReturn(optionalStock);
        doNothing().when(repository).deleteById(anyString());
        service.deleteStock(ID);
        verify(repository, times(1)).deleteById(anyString());
    }

    @Test
    void whenDeleteThenReturnObjectNotFoundException() {
        when(repository.findById(anyString()))
                .thenThrow(new StockNotFoundException(STOCK_NOT_FOUND));
        try {
            service.deleteStock(ID);
        } catch (Exception ex) {
            assertEquals(StockNotFoundException.class, ex.getClass());
            assertEquals(STOCK_NOT_FOUND, ex.getMessage());
        }
    }


    private void startStock() {
        stockDTO = new RequestStockDTO(SYMBOL, COMPANYNAME,  PRICE);
        stock = new Stock(stockDTO);
        stock.setId(ID);
        optionalStock = Optional.of(stock);
    }
}