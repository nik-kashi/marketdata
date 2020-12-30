package unit.service;

import com.kashi.marketdata.model.Stock;
import com.kashi.marketdata.repository.StockRepository;
import com.kashi.marketdata.service.StockService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static integration.StockIntegrationTest.GOOG;
import static integration.StockIntegrationTest.ID_GOOG;

@RunWith(SpringRunner.class)
public class StockServiceTest {

    @MockBean
    private StockRepository stockRepository;

    @Before
    public void setUp() {
        Stock stock = new Stock(GOOG);
        stock.setId(ID_GOOG);
        stock.setCurrentPrice(220.0);
        stock.setMinPrice(220.0);
        stock.setMaxPrice(220.0);
        Mockito.when(stockRepository.findById(ID_GOOG))
                .thenReturn(Optional.of(stock));
    }

    @TestConfiguration
    static class StockServiceTestContextConfiguration {
        @Bean
        public StockService employeeService() {
            return new StockService();
        }
    }

    @Autowired
    private StockService stockService;

    @Test
    public void updatePrice_increasePrice_setPriceAsMax() {
        Double oldPrice = stockRepository.findById(ID_GOOG).get().getCurrentPrice();
        Double newPrice = 228.2;
        Assert.assertNotEquals(oldPrice,newPrice);
        stockService.updatePrice(ID_GOOG, newPrice);
        Assert.assertTrue(stockRepository.findById(ID_GOOG).isPresent());
        stockRepository.findById(ID_GOOG).ifPresent(stock -> {
            Assert.assertEquals(stock.getMinPrice(), oldPrice);
            Assert.assertEquals(stock.getMaxPrice(), newPrice);
            Assert.assertEquals(stock.getCurrentPrice(), newPrice);
        });
    }

    @Test
    public void updatePrice_decreasePrice_setPriceAsMin() {
        Double oldPrice = stockRepository.findById(ID_GOOG).get().getCurrentPrice();
        Double newPrice = 218.1;
        Assert.assertNotEquals(oldPrice,newPrice);
        stockService.updatePrice(ID_GOOG, newPrice);
        Assert.assertTrue(stockRepository.findById(ID_GOOG).isPresent());
        stockRepository.findById(ID_GOOG).ifPresent(stock -> {
            Assert.assertEquals(stock.getMinPrice(), newPrice);
            Assert.assertEquals(stock.getMaxPrice(), oldPrice);
            Assert.assertEquals(stock.getCurrentPrice(), newPrice);
        });
    }


}
