package com.example.postparser.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

import static com.example.postparser.post.PostFixtures.POST0;
import static com.example.postparser.post.PostFixtures.POST1;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PostsFromApiFetcherTest {
    private WebClient webClientMock;

    @BeforeEach
    void setUp() {
        webClientMock = PostFixtures.prepareWebClientMock();
    }

    @Test
    void shouldFetch() {
        Optional<List<Post>> expected = prepareExpected();
        Optional<List<Post>> result = PostsFromApiFetcher.fetch(webClientMock);
        assertEquals(result, expected);
    }

    private Optional<List<Post>> prepareExpected() {
        return Optional.of(List.of(POST0, POST1));
    }
}