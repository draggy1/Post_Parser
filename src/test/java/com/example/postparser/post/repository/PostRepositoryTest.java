package com.example.postparser.post.repository;

import com.example.postparser.post.result.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static com.example.postparser.post.PostFixtures.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PostRepositoryTest {
    @Test
    void shouldSave() throws IOException {
        ObjectMapper mock = Mockito.mock(ObjectMapper.class);
        doNothing().when(mock).writeValue(FILE_0, POST0);

        PostRepository tested = new PostRepository(mock);
        Result result = tested.save(POST0, FILE_0);
        assertEquals(SUCCESS_RESULT0, result);
        verify(mock, times(1)).writeValue(FILE_0, POST0);
    }

    @Test
    void shouldSaveThrowException() throws IOException {
        ObjectMapper mock = Mockito.mock(ObjectMapper.class);
        doThrow(IOException.class).when(mock).writeValue(FILE_1, POST1);

        PostRepository tested = new PostRepository(mock);
        Result result = tested.save(POST1, FILE_1);
        assertEquals(THROWN_BY_EXCEPTION_RESULT, result);
        verify(mock, times(1)).writeValue(FILE_1, POST1);
    }
}