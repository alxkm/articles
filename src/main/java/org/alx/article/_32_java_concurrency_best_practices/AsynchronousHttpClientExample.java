package org.alx.article._32_java_concurrency_best_practices;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class AsynchronousHttpClientExample {

    public static void main(String[] args) {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/posts/1"))
                .build();

        CompletableFuture<HttpResponse<String>> responseFuture = httpClient.sendAsync(
                httpRequest, HttpResponse.BodyHandlers.ofString());

        responseFuture.thenAccept(response -> {
            int statusCode = response.statusCode();
            String responseBody = response.body();
            System.out.println("Status Code: " + statusCode);
            System.out.println("Response Body: " + responseBody);
        }).exceptionally(throwable -> {
            System.err.println("Error occurred: " + throwable);
            return null;
        });

        // Perform other tasks while waiting for the HTTP response
        System.out.println("Performing other tasks...");
    }
}