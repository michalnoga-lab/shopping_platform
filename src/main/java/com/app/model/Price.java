package com.app.model;

public enum Price {

    NET("NET_PRICE"),
    GROSS("GROSS_PRICE");

    private String description;

    Price(String description) {
        this.description = description;
    }
}