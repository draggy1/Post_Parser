package com.example.postparser.post;

import com.example.postparser.api.Api;
import com.example.postparser.post.configuration.PlaceholderConfiguration;
import com.example.postparser.post.result.InternalErrorResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.postparser.post.result.PostSaveStatus.LACKING_API_ADDRESS;

public class PostParserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostParserService.class);
    private static final String NOT_PROVIDED_API_ADDRESS = "Not provided api address";

    private final Api api;
    private final PostsToFilesTaskCreator creator;
    private final PostsSaveInvoker invoker;
    private final PostResultHandler handler;
    private final PlaceholderConfiguration config;

    @Inject
    public PostParserService(Api api, PostsToFilesTaskCreator creator,
                             PostsSaveInvoker invoker, PostResultHandler handler,
                             PlaceholderConfiguration config) {
        this.api = api;
        this.creator = creator;
        this.invoker = invoker;
        this.handler = handler;
        this.config = config;
    }

    public void parse(){
        final PlaceholderConfiguration config = new PlaceholderConfiguration();
        final ExecutorService postsToFilesExecutor = Executors.newCachedThreadPool();
        Optional.of(api.getAllPosts(config.getPlaceholderUrl()))
                .map(creator::prepareSaveToFileTasks)
                .map(callables -> invoker.invokeSave(callables, postsToFilesExecutor))
                .map(handler::handleResults)
                .orElse(List.of(InternalErrorResult.of(NOT_PROVIDED_API_ADDRESS, LACKING_API_ADDRESS)))
                .forEach(result -> LOGGER.info(result.getResultMessage()));

        postsToFilesExecutor.shutdown();
    }

}
