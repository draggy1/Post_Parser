package com.example.postparser.post;

import com.example.postparser.api.Api;
import com.example.postparser.post.result.InternalErrorResult;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import static com.example.postparser.post.result.PostSaveStatus.LACKING_API_ADDRESS;

public record PostParserService(Api api,
                                PostsToFilesTaskCreator creator,
                                PostsSaveInvoker invoker,
                                PostResultHandler handler) {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostParserService.class);
    private static final String NOT_PROVIDED_API_ADDRESS = "Not provided api address";

    @Inject
    public PostParserService {
    }

    public void parse() {
        Optional.of(api.getAllPosts())
                .map(creator::createSaveToFileTasks)
                .map(invoker::invokeSave)
                .map(handler::handleResults)
                .orElse(List.of(InternalErrorResult.of(NOT_PROVIDED_API_ADDRESS, LACKING_API_ADDRESS)))
                .forEach(result -> LOGGER.info(result.getResultMessage()));

    }
}
