package com.example.postparser.post;

import com.example.postparser.post.result.InternalErrorResult;
import com.example.postparser.post.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.example.postparser.post.result.PostSaveStatus.*;

public class PostResultHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostResultHandler.class);

    public List<Result> handleResults(List<Future<Result>> results){
       return results
               .stream()
               .map(this::handleResult)
               .toList();
    }

    private Result handleResult(Future<Result> result) {
        try {
            return result.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            LOGGER.error(INTERRUPTED.message);
            return new InternalErrorResult(e.getMessage(), INTERRUPTED);
        } catch (ExecutionException e) {
            LOGGER.error(e.getMessage());
            return new InternalErrorResult(e.getMessage(), FAILED_SAVE_TASK);
        } catch (TimeoutException e) {
            LOGGER.error(TIMEOUT.message);
            return new InternalErrorResult(e.getMessage(), TIMEOUT);
        }
    }
}
