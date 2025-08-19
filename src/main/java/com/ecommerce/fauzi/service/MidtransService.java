package com.ecommerce.fauzi.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ecommerce.fauzi.dto.request.MidtransRequest;

@Service
public class MidtransService {

    private final RestTemplate restTemplate = new RestTemplate();
    
    @Value("${midtrans.server.key}")
    private String serverKey;

    public Map<String, Object> createTransaction(MidtransRequest request) {
        String url = "https://app.sandbox.midtrans.com/snap/v1/transactions";

        Map<String, Object> transactionDetails = Map.of(
                "order_id", "order-" + System.currentTimeMillis(),
                "gross_amount", request.getProduct().price()
        );

        Map<String, Object> productDetails = Map.of(
                "product_name", request.getProduct().productName(),
                "price", request.getProduct().price(),
                "desc", request.getProduct().desc()
        );

        Map<String, Object> customerDetails = Map.of(
                "name", request.getUser().name(),
                "email", request.getUser().email()
        );

        Map<String, Object> body = Map.of(
                "transaction_details", transactionDetails,
                "product_details", productDetails,
                "customer_details", customerDetails
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(serverKey, "");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );
        
        return response.getBody();
    }
}
