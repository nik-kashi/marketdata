package com.kashi.marketdata.controller;

import com.kashi.marketdata.model.Stock;
import com.kashi.marketdata.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/stocks", produces = MediaType.APPLICATION_JSON_VALUE)
public class StockController {


    private final StockService stockService;

    public StockController(@Autowired StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    ResponseEntity findAll() {
        return ResponseEntity.ok(stockService.findAll());
    }

    @GetMapping(value = "/{id}")
    ResponseEntity findById(@PathVariable("id") Long id) {
        return ResponseEntity.of(stockService.findById(id));
    }

    @PostMapping
    ResponseEntity save(@RequestBody Stock stock) {
        return ResponseEntity.ok(stockService.save(stock));
    }

    @PutMapping(value = "/{id}/currentPrice/{price}")
    ResponseEntity updateCurrentPrice(@PathVariable("id") Long id, @PathVariable("price") Double price) {
        return ResponseEntity.of(stockService.updatePrice(id, price));
    }

    @PatchMapping(value = "/{id}")
    ResponseEntity updateCurrentPrice(@PathVariable("id") Long id, @RequestBody Map requestBody) {
        return ResponseEntity.of(stockService.updatePrice(id, (Double) requestBody.get("currentPrice")));
    }
}
