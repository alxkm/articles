package org.alx.article._48_java_rest_clients.spring_web_flux;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class SimpleGetRequest {
    public static void main(String[] args) {
        // Create a WebClient instance
        WebClient webClient = WebClient.create("https://api.github.com");

        // Make the GET request and get the response
        Mono<String> response = webClient.get()
                .retrieve()
                .bodyToMono(String.class);

        // Subscribe to the response and print the body
        response.subscribe(System.out::println);
    }
}