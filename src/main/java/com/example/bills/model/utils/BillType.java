package com.example.bills.model.utils;

public enum BillType {
    WATER("water"),
    ELECTRICITY("electricity"),
    GAS("gas"),
    TELCO("telco"),
    OTHER("other");
    private final String stringValue;

    BillType(String stringValue) {
        this.stringValue = stringValue;
    }
}
