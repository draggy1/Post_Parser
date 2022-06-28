package com.example.postparser.post;

import org.junit.jupiter.api.Test;

import java.io.File;

import static com.example.postparser.post.PostFixtures.FILE_0;
import static com.example.postparser.post.PostFixtures.POST0;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PostFileCreatorTest {

    @Test
    void shouldGetFile() {
        File result = PostFileCreator.getFile(POST0, "/home/test", "/tmp/directory/");
        assertEquals(FILE_0, result);
    }
}