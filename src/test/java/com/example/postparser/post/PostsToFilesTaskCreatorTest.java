package com.example.postparser.post;

import com.example.postparser.post.configuration.Configuration;
import com.example.postparser.post.repository.PostRepository;
import com.example.postparser.post.result.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;

import static com.example.postparser.post.PostFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

class PostsToFilesTaskCreatorTest {

    @Test
    void shouldCreateSaveToFileTasks() {
        PostRepository repositoryMock = mock(PostRepository.class);
        PostFileCreator fileCreatorMock = mock(PostFileCreator.class);
        Configuration configMock = mock(Configuration.class);
        when(configMock.getAbsolutePath()).thenReturn("/test/directory");

        PostsToFilesTaskCreator tested  = new PostsToFilesTaskCreator(repositoryMock, fileCreatorMock, configMock);
        List<Post> given = List.of(POST1, POST2);
        List<Callable<Result>> result = tested.createSaveToFileTasks(given);

        assertEquals(result.size(), 2);
        verify(fileCreatorMock).getFile(POST1, "/test/directory");
        verify(fileCreatorMock).getFile(POST2, "/test/directory");
    }
}