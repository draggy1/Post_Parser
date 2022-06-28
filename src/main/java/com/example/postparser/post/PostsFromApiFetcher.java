package com.example.postparser.post;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public final class PostsFromApiFetcher {
    public static Optional<List<Post>> fetch(WebClient client) {
        return client
                .get()
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Post>>() {})
                .blockOptional(Duration.ofMinutes(1));
    }
}
