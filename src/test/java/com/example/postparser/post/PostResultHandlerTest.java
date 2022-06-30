package com.example.postparser.post;

import com.example.postparser.post.result.Result;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import static com.example.postparser.post.PostFixtures.FAILURE_RESULT2;
import static com.example.postparser.post.PostFixtures.SUCCESS_RESULT1;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PostResultHandlerTest {

    @Test
    void shouldHandleResults() {
        Future<Result> result1 = CompletableFuture.completedFuture(SUCCESS_RESULT1);
        Future<Result> result2 = CompletableFuture.failedFuture(
                new ExecutionException( "Test Execution Exception", new TimeoutException()));
        List<Future<Result>> givenResults = List.of(result1, result2);;

        PostResultHandler tested = new PostResultHandler();
        List<Result> result = tested.handleResults(givenResults);

        List<Result> expected =  List.of(SUCCESS_RESULT1, FAILURE_RESULT2);
        assertEquals(expected, result);
    }
}