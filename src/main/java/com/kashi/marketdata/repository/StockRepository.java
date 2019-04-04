package com.kashi.marketdata.repository;

import com.kashi.marketdata.model.Stock;
import org.springframework.data.repository.CrudRepository;

public interface StockRepository extends CrudRepository<Stock, Long> {
}