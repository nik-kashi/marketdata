package unit.repository;

import com.kashi.marketdata.MarketDataApp;
import com.kashi.marketdata.model.Stock;
import com.kashi.marketdata.repository.StockRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceException;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = MarketDataApp.class)
public class StockRepositoryTest {

    public static final String TEST_STOCK = "TestStock";

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StockRepository stockRepository;

    @Test
    public void save_regularStock_successful() {
        Stock stock = createStock(TEST_STOCK);

        Assert.assertEquals(stock.getName(), TEST_STOCK);
        Assert.assertNotNull(stock.getId());
    }

    @Test(expected = PersistenceException.class)
    public void save_twoStockWithOneName_fail() {
        Stock stock = createStock(TEST_STOCK);
        Assert.assertEquals(stock.getName(), TEST_STOCK);
        Assert.assertNotNull(stock.getId());
        createStock(TEST_STOCK);
    }


    private Stock createStock(String stockName) {
        Stock stock = new Stock();
        stock.setName(stockName);
        stock = stockRepository.save(stock);
        entityManager.flush();
        return stock;
    }
}
