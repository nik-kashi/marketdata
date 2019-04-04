package integration;

import com.kashi.marketdata.MarketDataApp;
import com.kashi.marketdata.model.Stock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import unit.repository.StockRepositoryTest;
import util.JsonUtil;

import javax.persistence.EntityManager;
import java.util.HashMap;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MarketDataApp.class)
@AutoConfigureMockMvc

public class StockIntegrationTest {

    public static final String GOOG = "GOOG";
    public static final String AAPL = "AAPL";
    public static final Long ID_GOOG = 1L;
    public static final Long ID_AAPL = 2L;

    public static final String URL_STOCKS = "/api/stocks";
    public static final String URL_STOCK = "/api/stocks/{1}";
    public static final String URL_STOCK_LAST_PRICE = "/api/stocks/{1}/currentPrice/{2}";
    @Autowired
    private MockMvc mvc;

    @Autowired
    private EntityManager entityManager;


    @Test
    public void findAll_regularRestCall_listOfAllStocks() throws Exception {
        mvc.perform(get(URL_STOCKS)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is(GOOG)))
                .andExpect(jsonPath("$.length()", is(2)))
        ;
    }

    @Test
    public void findById_2_appleStock() throws Exception {
        mvc.perform(get(URL_STOCK, ID_AAPL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(AAPL)))
        ;
    }

    @Test
    public void save_regularStock_stockWithItsId() throws Exception {
        mvc.perform(post(URL_STOCKS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.asJsonString(new Stock(StockRepositoryTest.TEST_STOCK))))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(StockRepositoryTest.TEST_STOCK)));
    }

    @Test
    public void save_duplicatedStockName_clientError() throws Exception {
        mvc.perform(post(URL_STOCKS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.asJsonString(new Stock(GOOG))))
                .andExpect(status().isConflict())
        ;
    }


    @Test
    public void updateCurrentPrice_partialRequest_successful() throws Exception {
        final Double NEW_PRICE = 11.23;
        Assert.assertNotEquals(entityManager.find(Stock.class, ID_GOOG).getCurrentPrice(), NEW_PRICE);
        mvc.perform(patch(URL_STOCK,ID_GOOG)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.asJsonString(new HashMap<String, Object>() {{
                    put("currentPrice", NEW_PRICE);
                }})))
                .andExpect(status().isOk());
        Assert.assertEquals(entityManager.find(Stock.class, ID_GOOG).getCurrentPrice(), NEW_PRICE);
    }

    @Test
    public void updateCurrentPrice_putRequest_successful() throws Exception {
        final Double NEW_PRICE = 12.23;
        Assert.assertNotEquals(entityManager.find(Stock.class, ID_GOOG).getCurrentPrice(), NEW_PRICE);
        mvc.perform(put(URL_STOCK_LAST_PRICE, ID_GOOG, NEW_PRICE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPrice", is(NEW_PRICE)));
        Assert.assertEquals(entityManager.find(Stock.class, ID_GOOG).getCurrentPrice(), NEW_PRICE);
    }


}
