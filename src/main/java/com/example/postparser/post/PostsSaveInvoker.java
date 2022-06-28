package com.example.postparser.post;

import com.example.postparser.post.result.InternalErrorResult;
import com.example.postparser.post.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static com.example.postparser.post.result.PostSaveStatus.INTERRUPTED;

public class PostsSaveInvoker {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostsSaveInvoker.class);

    public static List<Future<Result>> invokeSave(List<Callable<Result>> postsToFilesTasks, ExecutorService postsToFilesExecutor) {
        try {
            return postsToFilesExecutor.invokeAll(postsToFilesTasks);
        } catch (InterruptedException e) {
            LOGGER.error("Failed task execution - interrupted thread");
            return List.of(CompletableFuture.completedFuture(new InternalErrorResult(e.getMessage(), INTERRUPTED)));
        }
    }
}
