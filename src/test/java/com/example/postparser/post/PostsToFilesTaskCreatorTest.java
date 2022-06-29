package com.example.postparser.post;

import com.example.postparser.post.result.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.Callable;

import static com.example.postparser.post.PostFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostsToFilesTaskCreatorTest {
    private WebClient webClientMock;
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        webClientMock = PostFixtures.prepareWebClientMock();
    }

    /*@Test
    void shouldPrepareTaskToPerform() {
        final String givenFileLocalization = "/tmp/directory/";
        final String absolutePath = "/home/test";
        mapper = Mockito.mock(ObjectMapper.class);
        SavePostsContext savePostsContext = new SavePostsContext("", absolutePath, givenFileLocalization, mapper);
        MockedStatic<PostFileCreator> fileCreatorMock = mockStatic(PostFileCreator.class);

        fileCreatorMock.when(() -> PostFileCreator.getFile(POST0, absolutePath, givenFileLocalization)).thenReturn(FILE_0);
        fileCreatorMock.when(() -> PostFileCreator.getFile(POST1, absolutePath, givenFileLocalization)).thenReturn(FILE_1);

        final List<Callable<Result>> result =
                PostsToFilesTaskCreator.prepareTaskToPerform(webClientMock, savePostsContext);

        assertEquals(result.size(), 2);
        fileCreatorMock.verify(() -> PostFileCreator.getFile(POST0, absolutePath, givenFileLocalization), times(1));
        fileCreatorMock.verify(() -> PostFileCreator.getFile(POST1, absolutePath, givenFileLocalization), times(1));
    }*/
}