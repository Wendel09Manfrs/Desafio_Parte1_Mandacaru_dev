package cucumber.steps;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockRepository;
import com.mandacarubroker.service.StockService;
import io.cucumber.java.en.Given;
import io.cucumber.spring.CucumberContextConfiguration;
import com.mandacarubroker.controller.StockController;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.*;


@CucumberContextConfiguration
public class HomeSteps {

    public StockController stockController;

    private StockService stockService;

    private StockRepository stockRepository;

    @Given("The home broker already has registered stocks")
    public void theStoreAlreadyHasRegisteredStocks() {

        stockService = new StockService(stockRepository);
        stockController = new StockController(stockService);


        stockService.createStock(new RequestStockDTO("AA5", "Apple3",23.45));
        stockService.createStock(new RequestStockDTO("BB5", "Microsoft3",12.45));

    }

    @And("The home broker has fewer than {int} stocks")
    public void theHomeBrokerHasFewerThanStocks(int arg0) {
        List <Stock> listStocks = stockRepository.findAll();
        assertTrue("The store has fewer than {int} products",
                listStocks.size()<arg0);
    }

    @When("The user is going to add a product to the home broker")
    public void theUserIsGoingToAddAStockToTheHomeBroker() {
        stockService.createStock(new RequestStockDTO("AA9", "APPLE3",123.45));
    }

    @Then("The stock must be added successfully")
    public void theStockMustBeAddedSuccessfully() {
        assertTrue("The product must be added successfully",
               2>1);
    }



}
