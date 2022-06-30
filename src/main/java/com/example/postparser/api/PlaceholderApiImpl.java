package com.example.postparser.api;

import com.example.postparser.post.Post;
import com.example.postparser.post.configuration.Configuration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

public record PlaceholderApiImpl(Configuration config) implements Api {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlaceholderApiImpl.class);
    public static final String POSTS = "posts";
    public static final String APPLICATION_JSON = "Application/JSON";

    @Inject
    public PlaceholderApiImpl {
    }

    @Override
    public List<Post> getAllPosts() {
        return prepareGetPostsUri(config.getPlaceholderUrl())
                .map(this::prepareGetPostsRequest)
                .flatMap(this::sendGetPostsRequest)
                .map(HttpResponse::body)
                .map(this::readPostsFromJson)
                .orElse(List.of());

    }

    private List<Post> readPostsFromJson(String json) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            LOGGER.error(String.format("Deserialization get posts request failed. Reason: %s", e.getMessage()));
            return List.of();
        }
    }

    private Optional<HttpResponse<String>> sendGetPostsRequest(HttpRequest request) {
        try {
            final HttpClient client = HttpClient.newHttpClient();
            return Optional.of(client.send(request, HttpResponse.BodyHandlers.ofString()));
        } catch (IOException | InterruptedException e) {
            LOGGER.error(String.format("Send get posts request failed. Reason: %s", e.getMessage()));
            return Optional.empty();
        }

    }

    private HttpRequest prepareGetPostsRequest(URI uri) {
        return HttpRequest.newBuilder()
                .uri(uri)
                .headers(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON)
                .timeout(Duration.ofSeconds(1))
                .GET()
                .build();
    }

    private Optional<URI> prepareGetPostsUri(String baseUrl) {
        try {
            return Optional.ofNullable(baseUrl)
                    .map(url -> URI.create(url).resolve(POSTS));
        } catch (IllegalArgumentException ex) {
            LOGGER.error(String.format("Creation of Uri failed: message: %s", ex.getMessage()));
            return Optional.empty();
        }
    }
}
