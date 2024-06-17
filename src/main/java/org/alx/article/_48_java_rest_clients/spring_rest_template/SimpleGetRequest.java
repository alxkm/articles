package org.alx.article._48_java_rest_clients.spring_rest_template;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

public class SimpleGetRequest {
    public static void main(String[] args) {
        // Create a RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Define the URL
        String url = "https://api.github.com";

        // Make the GET request and get the response
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Print the response status code and body
        System.out.println("Status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody());
    }
}