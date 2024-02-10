package com.mandacarubroker.service;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;

import java.util.List;

public interface StockService {

    List<Stock> getAllStocks();

    Stock getStockById(String id);

    Stock createStock(RequestStockDTO data);
//     Stock updateStock(String id, Stock updatedStock);

    void deleteStock(String id);


    Stock validateAndCreateStock(RequestStockDTO data);

    Stock validateAndUpdateStock(String id, RequestStockDTO data );



}
