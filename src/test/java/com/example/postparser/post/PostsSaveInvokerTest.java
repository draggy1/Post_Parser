package com.example.postparser.post;

import com.example.postparser.post.result.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;

import static com.example.postparser.post.PostFixtures.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PostsSaveInvokerTest {
    private ExecutorService executorService;

    @BeforeEach
    void setUp() {
        //executorService = Mockito.mock(ExecutorService.class);
        executorService= Executors.newSingleThreadExecutor();
    }

    @Test
    void shouldInvokeSave() throws InterruptedException {
        List<Callable<Result>> given = prepareGiven();
        List<Result> expected = prepareExpected();

        //when(executorService.invokeAll(given)).thenReturn(expected);
        List<Future<Result>> result = PostsSaveInvoker.invokeSave(given, executorService);
        List<Result> consumedFutureResult = consumeFuture(result);

        assertEquals(expected, consumedFutureResult);
        //verify(executorService, times(1)).invokeAll(given);
    }

    private List<Result> consumeFuture(List<Future<Result>> result) {
        return result.stream().map(future -> {
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }).toList();
    }

    private static List<Callable<Result>> prepareGiven() {
        Callable<Result> task1 = () -> SUCCESS_RESULT2;
        Callable<Result> task2 = () -> SUCCESS_RESULT3;
        Callable<Result> task3 = () -> FAILURE_RESULT1;

        return List.of(task1, task2, task3);
    }

    private List<Result> prepareExpected(){
        Future<Result> result1 = CompletableFuture.completedFuture(SUCCESS_RESULT2);
        Future<Result> result2 = CompletableFuture.completedFuture(SUCCESS_RESULT3);
        Future<Result> result3 = CompletableFuture.completedFuture(FAILURE_RESULT1);

        return List.of(SUCCESS_RESULT2, SUCCESS_RESULT3, FAILURE_RESULT1);
    }
}