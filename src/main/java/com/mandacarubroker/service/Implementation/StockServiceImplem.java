package com.mandacarubroker.service.Implementation;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockRepository;
import com.mandacarubroker.service.StockService;
import com.mandacarubroker.service.exceptions.StockNotFoundException;
import jakarta.validation.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public
class StockServiceImplem implements StockService {

    private final StockRepository stockRepository;
    public StockServiceImplem(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Stock getStockById(String id) {
        return stockRepository.findById(id).orElseThrow(
                ()-> new StockNotFoundException("Stock not found"));
    }

    public Stock createStock(RequestStockDTO data) {
        Stock novaAcao = new Stock(data);
        validateRequestStockDTO(data);
        return stockRepository.save(novaAcao);
    }

    public void deleteStock(String id) {
        Stock stock = stockRepository.findById(id).orElseThrow(
                () -> new StockNotFoundException("Stock not found"));

        stockRepository.deleteById(stock.getId());
    }

    public Stock validateAndUpdateStock(String id, RequestStockDTO data) {
            validateRequestStockDTO(data);

            Stock stockUp = new Stock(data);
            return stockRepository.findById(id)
                    .map(stock -> {
                        stock.setSymbol(stockUp.getSymbol());
                        stock.setCompanyName(stockUp.getCompanyName());
                        double newPrice = stock.changePrice(stockUp.getPrice(),true);
                        stock.setPrice(newPrice);

                        return stockRepository.save(stock);
                    }).orElseThrow(
                            ()-> new StockNotFoundException("Stock not found"));
    }



    public static void validateRequestStockDTO(RequestStockDTO data) {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<RequestStockDTO>> violations = validator.validate(data);

            if (!violations.isEmpty()) {
                StringBuilder errorMessage = new StringBuilder("Validation failed. Details: ");

                for (ConstraintViolation<RequestStockDTO> violation : violations) {
                    errorMessage.append(String.format("[%s: %s], ", violation.getPropertyPath(), violation.getMessage()));
                }

                errorMessage.delete(errorMessage.length() - 2, errorMessage.length());

                throw new ConstraintViolationException(errorMessage.toString(), violations);
            }
        }
    }


    public Stock validateAndCreateStock(RequestStockDTO data) {
        validateRequestStockDTO(data);

        Stock novaAcao = new Stock(data);
        return stockRepository.save(novaAcao);
    }


}
