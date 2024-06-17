package org.alx.article._48_java_rest_clients.appache_http_client;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class PostRequestWithJson {
    public static void main(String[] args) {
        // Create a CloseableHttpClient instance
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Create an HttpPost request
            HttpPost post = new HttpPost("https://jsonplaceholder.typicode.com/posts");

            // Set the request headers
            post.setHeader("Content-Type", "application/json");

            // Set the request payload
            String json = "{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}";
            post.setEntity(new StringEntity(json));

            // Execute the request and get the response
            try (CloseableHttpResponse response = httpClient.execute(post)) {
                // Print the response status and body
                System.out.println("Status code: " + response.getStatusLine().getStatusCode());
                System.out.println("Response body: " + EntityUtils.toString(response.getEntity()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}