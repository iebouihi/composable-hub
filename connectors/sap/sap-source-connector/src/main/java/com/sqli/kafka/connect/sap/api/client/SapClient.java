package com.sqli.kafka.connect.sap.api.client;

import com.google.gson.GsonBuilder;
import com.sqli.kafka.connect.sap.api.SapProductApi;
import feign.Feign;
import feign.Retryer;
import feign.gson.GsonDecoder;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class SapClient {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SapClient.class);
    public static List<Map> getProducts(String fromTime, String username, String password, String baseUrl, String apiVersion) throws IOException, InterruptedException {
        try {
            String token = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
            return Feign.builder()
                    .decoder(sapProductDecoder())
                    .retryer(Retryer.NEVER_RETRY)
                    .target(SapProductApi.class, baseUrl)
                    .getProducts(fromTime, token, apiVersion);
        } catch (Exception e) {
            log.error("Caught following exception, ignoring to ensure connector doesn't fail", e);
            return new ArrayList<>();
        }
    }

    private static GsonDecoder sapProductDecoder() {
        return new GsonDecoder(
                new GsonBuilder().create());
    }
}
