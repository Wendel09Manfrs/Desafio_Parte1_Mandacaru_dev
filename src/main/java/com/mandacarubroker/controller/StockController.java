package com.mandacarubroker.controller;


import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.dto.RequestStockDTO;
import com.mandacarubroker.service.StockService;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockController {
    public static final String ID = "/{id}";


    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {

        return ResponseEntity.ok(stockService.getAllStocks());

    }

    @GetMapping(ID)
    public ResponseEntity<Stock> getStockById(@PathVariable String id) {
        Stock stock = stockService.getStockById(id);
        return ResponseEntity.ok(stock);
    }

    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody RequestStockDTO data) {
        Stock createdStock = stockService.validateAndCreateStock(data);
        return ResponseEntity.ok(createdStock);


    }


    @PutMapping(ID)
    public ResponseEntity<Stock> updateStock(@PathVariable String id, @RequestBody RequestStockDTO data) {
        Stock stockUp = stockService.validateAndUpdateStock(id, data);
        return ResponseEntity.ok(stockUp);
    }

    @DeleteMapping(ID)
    public ResponseEntity<String> delete(@PathVariable String id) {
        stockService.deleteStock(id);
        return ResponseEntity.ok("Deleted with success");

    }

}
