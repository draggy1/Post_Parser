package com.example.postparser.post.repository;

import com.example.postparser.post.Post;
import com.example.postparser.post.result.FailureResult;
import com.example.postparser.post.result.Result;
import com.example.postparser.post.result.SuccessResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static com.example.postparser.post.result.PostSaveStatus.FAILURE;
import static com.example.postparser.post.result.PostSaveStatus.SUCCESS;

public class PostRepository implements Repository{
    private static final Logger LOGGER = LoggerFactory.getLogger(PostRepository.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Result save(Post post, File file) {
        try {
            mapper.writeValue(file, post);
            return new SuccessResult(post, SUCCESS);
        } catch (IOException e) {
            LOGGER.error("Problem with saving post to file");
            return new FailureResult(post, FAILURE);
        }
    }
}
