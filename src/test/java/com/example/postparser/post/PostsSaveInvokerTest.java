package com.example.postparser.post;

import com.example.postparser.post.repository.PostRepository;
import com.example.postparser.post.result.InternalErrorResult;
import com.example.postparser.post.result.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

import static com.example.postparser.post.PostFixtures.*;
import static com.example.postparser.post.result.PostSaveStatus.FAILURE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PostsSaveInvokerTest {

    @Test
    void shouldInvokeSave() throws IOException {
        ObjectMapper mapperMock = Mockito.mock(ObjectMapper.class);
        doNothing().when(mapperMock).writeValue(FILE_0, POST0);
        doNothing().when(mapperMock).writeValue(FILE_1, POST1);
        List<Callable<Result>> givenTasks = prepareTasks(mapperMock);

        PostsSaveInvoker tested = new PostsSaveInvoker(Executors.newSingleThreadExecutor());
        List<Future<Result>> result = tested.invokeSave(givenTasks);
        assertEquals(result.size(), 2);

        List<Result> consumedFutures = result.stream().map(this::getFuture).toList();
        List<Result> expected = prepareExpected();
        assertEquals(expected, consumedFutures);
        verify(mapperMock, times(1)).writeValue(FILE_0, POST0);
        verify(mapperMock, times(1)).writeValue(FILE_1, POST1);
    }

    private Result getFuture(Future<Result> future) {
        try {
            return future.get(1, TimeUnit.SECONDS);

        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            e.printStackTrace();
            return new InternalErrorResult(e.getMessage(), FAILURE);
        }
    }

    private List<Callable<Result>> prepareTasks(ObjectMapper mapperMock) {
        PostRepository postRepository = new PostRepository(mapperMock);
        Callable<Result> task1 = () -> postRepository.save(POST0, FILE_0);
        Callable<Result> task2 = () -> postRepository.save(POST1, FILE_1);

        return List.of(task1, task2);
    }

    private List<Result> prepareExpected() {
        return List.of(SUCCESS_RESULT0, SUCCESS_RESULT1);
    }

}