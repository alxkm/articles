package org.alx.article._48_java_rest_clients.okhttp;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class SimpleGetRequest {
    public static void main(String[] args) {
        // Create an OkHttpClient instance
        OkHttpClient client = new OkHttpClient();

        // Create a request
        Request request = new Request.Builder()
                .url("https://api.github.com")
                .build();

        // Execute the request and get the response
        try (Response response = client.newCall(request).execute()) {
            // Print the response status code and body
            System.out.println("Status code: " + response.code());
            System.out.println("Response body: " + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}