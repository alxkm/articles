package org.alx.article._48_java_rest_clients.okhttp;

import okhttp3.*;

import java.io.IOException;

public class AsyncPostRequest {
    public static void main(String[] args) {
        // Create an OkHttpClient instance
        OkHttpClient client = new OkHttpClient();

        // Define the request payload
        String json = "{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}";

        // Create a request body
        RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));

        // Create a request
        Request request = new Request.Builder()
                .url("https://jsonplaceholder.typicode.com/posts")
                .post(body)
                .build();

        // Execute the request asynchronously
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Print the response status code and body
                System.out.println("Status code: " + response.code());
                System.out.println("Response body: " + response.body().string());
            }
        });
    }
}