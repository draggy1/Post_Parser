package com.example.postparser.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

public class WebClientCreator {

    public static Optional<WebClient> get(String baseUrl, String endPoint){
        return prepareUrl(endPoint, baseUrl)
                .map(WebClientCreator::create);
    }

    private static Optional<String> prepareUrl(String endPoint, String baseUrl) {
        return Optional.ofNullable(baseUrl)
                .map(url -> UriComponentsBuilder
                        .fromUriString(baseUrl)
                        .path(endPoint)
                        .build()
                        .toUriString());
    }

    private static WebClient create(String url) {
        return WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
