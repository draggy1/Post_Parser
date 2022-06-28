package com.example.postparser.post;

import com.example.postparser.post.result.Result;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static com.example.postparser.post.PostFixtures.*;
import static org.junit.jupiter.api.Assertions.*;

class PostResultHandlerTest {

    @Test
    void shouldHandleResults() {
        List<Future<Result>> given = prepareGiven();
        List<Result> expected = prepareExpected();
        List<Result> result = PostResultHandler.handleResults(given);
        assertEquals(result, expected);
    }

    private List<Future<Result>> prepareGiven() {
        CompletableFuture<Result> result1 = CompletableFuture.completedFuture(SUCCESS_RESULT2);
        CompletableFuture<Result> result2 = CompletableFuture.completedFuture(SUCCESS_RESULT3);
        CompletableFuture<Result> result3 = CompletableFuture.completedFuture(FAILURE_RESULT1);

        return List.of(result1, result2, result3);
    }

    private List<Result> prepareExpected() {
        return List.of(SUCCESS_RESULT2, SUCCESS_RESULT3, FAILURE_RESULT1);
    }
}