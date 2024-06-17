package org.alx.article._48_java_rest_clients.appache_http_client;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class SimpleGetRequest {
    public static void main(String[] args) {
        // Create a CloseableHttpClient instance
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Create an HttpGet request
            HttpGet request = new HttpGet("https://api.github.com");

            // Execute the request and get the response
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                // Get the response entity
                HttpEntity entity = response.getEntity();

                // Print the response status and body
                System.out.println("Status code: " + response.getStatusLine().getStatusCode());
                if (entity != null) {
                    System.out.println("Response body: " + EntityUtils.toString(entity));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}