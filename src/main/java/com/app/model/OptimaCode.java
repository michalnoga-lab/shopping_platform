package com.app.model;

public enum OptimaCode {

    TEST("TEST_CODE"),
    ACE_WYBIELACZ_LAGODNY("ACE WYBIELACZ 1000 D"),
    AJAX_PROSZEK_450("AJAX PROSZEK 450");

    private String description;

    OptimaCode(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}