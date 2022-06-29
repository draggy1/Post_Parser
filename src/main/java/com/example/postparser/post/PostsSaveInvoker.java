package com.example.postparser.post;

import com.example.postparser.post.result.InternalErrorResult;
import com.example.postparser.post.result.Result;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.*;

import static com.example.postparser.post.result.PostSaveStatus.INTERRUPTED;

public record PostsSaveInvoker(ExecutorService postsToFilesExecutor) {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostsSaveInvoker.class);

    @Inject
    public PostsSaveInvoker {
    }

    List<Future<Result>> invokeSave(List<Callable<Result>> postsToFilesTasks) {
        try {
            return postsToFilesExecutor.invokeAll(postsToFilesTasks);
        } catch (InterruptedException e) {
            LOGGER.error("Failed task execution - interrupted thread");
            return List.of(CompletableFuture.completedFuture(new InternalErrorResult(e.getMessage(), INTERRUPTED)));
        } finally {
            postsToFilesExecutor.shutdown();
        }
    }
}
