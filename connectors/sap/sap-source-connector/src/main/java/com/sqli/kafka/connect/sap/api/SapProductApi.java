package com.sqli.kafka.connect.sap.api;

import com.sqli.kafka.connect.sap.model.SapProductsResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;
import java.util.Map;

public interface SapProductApi {
    @RequestLine("GET /{version}/InboudProduct/Products?filter = modifiedTime gt DateTime'{fromTime}'")
    @Headers({"Content-Type: application/json", "Authorization: Basic {token}"})
    List<Map> getProducts(@Param("fromTime") String fromTime, @Param("token") String token, @Param("version") String version);
}
