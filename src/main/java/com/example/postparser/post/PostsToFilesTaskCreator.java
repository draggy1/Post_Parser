package com.example.postparser.post;

import com.example.postparser.post.result.FailureResult;
import com.example.postparser.post.result.Result;
import com.example.postparser.post.result.SuccessResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import static com.example.postparser.post.result.PostSaveStatus.FAILURE;
import static com.example.postparser.post.result.PostSaveStatus.SUCCESS;

public final class PostsToFilesTaskCreator {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostsToFilesTaskCreator.class);

    public static List<Callable<Result>> prepareSaveToFileTasks(List<Post> posts, SavePostsContext context) {
        return posts
                .stream()
                .map(post -> prepareSaveToFileTask(post, context))
                .toList();
    }

    private static Callable<Result> prepareSaveToFileTask(Post post, SavePostsContext context){
        final File file = PostFileCreator.getFile(post, context.absolutePath(), context.fileLocalization());
        return () -> saveTask(post, file, context.mapper());
    }

    private static Result saveTask(Post post, File file, ObjectMapper mapper) {
        try {
            mapper.writeValue(file, post);
            return new SuccessResult(post, SUCCESS);
        } catch (IOException e) {
            LOGGER.error("Problem with saving post to file");
            return new FailureResult(post, FAILURE);
        }
    }
}
