package com.sqli.kafka.connect.sap.model;

import java.util.List;
import java.util.Map;

public class SapProductsResponse {

    private List<Map<String, Object>> sapProductList;

    public List<Map<String, Object>> getSapProductList() {
        return sapProductList;
    }

    public SapProductsResponse(List<Map<String, Object>> sapProducts) {
        sapProductList = sapProducts;
    }
}
