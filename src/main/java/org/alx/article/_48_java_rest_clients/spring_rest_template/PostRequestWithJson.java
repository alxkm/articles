package org.alx.article._48_java_rest_clients.spring_rest_template;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class PostRequestWithJson {
    public static void main(String[] args) {
        // Create a RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Define the URL
        String url = "https://jsonplaceholder.typicode.com/posts";

        // Create the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the request payload
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("title", "foo");
        requestBody.put("body", "bar");
        requestBody.put("userId", 1);

        // Wrap the payload in an HttpEntity
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Make the POST request and get the response
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        // Print the response status code and body
        System.out.println("Status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody());
    }
}