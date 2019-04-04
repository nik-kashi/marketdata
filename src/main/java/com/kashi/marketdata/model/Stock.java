package com.kashi.marketdata.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "STOCK", uniqueConstraints = @UniqueConstraint(columnNames = "STOCK_NAME"))

public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "STOCK_ID")
    private Long id;

    @Column(name = "STOCK_NAME", nullable = false)
    private String name;

    @Column(name = "CURRENT_PRICE")
    private Double currentPrice;

    @Column(name = "LAST_UPDATE")
    private Timestamp lastUpdate;

    @Column(name = "MIN_PRICE")
    private Double minPrice;

    @Column(name = "MAX_PRICE")
    private Double maxPrice;
    public Stock() {
    }

    public Stock(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }
}
