package com.example.postparser.post;

import com.example.postparser.post.configuration.PlaceholderConfiguration;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;

import static com.example.postparser.post.PostFixtures.FILE_0;
import static com.example.postparser.post.PostFixtures.POST0;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PostFileCreatorTest {

    @Test
    void shouldGetFile() {
        String givenLocalizationPath = "/tmp/directory";
        PlaceholderConfiguration configMock = Mockito.spy(PlaceholderConfiguration.class);
        doReturn(givenLocalizationPath).when(configMock).getFileLocalization();

        PostFileCreator tested = new PostFileCreator(configMock);
        File result = tested.getFile(POST0, "/home/test");
        assertEquals(FILE_0, result);
        verify(configMock, times(1)).getFileLocalization();
    }
}