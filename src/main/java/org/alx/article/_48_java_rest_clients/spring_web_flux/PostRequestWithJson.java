package org.alx.article._48_java_rest_clients.spring_web_flux;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

public class PostRequestWithJson {
    public static void main(String[] args) {
        // Create a WebClient instance
        WebClient webClient = WebClient.create("https://jsonplaceholder.typicode.com");

        // Define the request payload
        String requestBody = "{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}";

        // Make the POST request and get the response
        Mono<String> response = webClient.post()
                .uri("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class);

        // Subscribe to the response and print the body
        response.subscribe(System.out::println);
    }
}