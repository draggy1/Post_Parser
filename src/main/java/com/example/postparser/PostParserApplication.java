package com.example.postparser;

import com.example.postparser.api.PlaceholderApiImpl;
import com.example.postparser.configuration.PlaceholderApiConfiguration;
import com.example.postparser.post.PostResultHandler;
import com.example.postparser.post.PostsSaveInvoker;
import com.example.postparser.post.PostsToFilesTaskCreator;
import com.example.postparser.post.SavePostsContext;
import com.example.postparser.post.result.InternalErrorResult;
import com.example.postparser.post.result.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.postparser.post.result.PostSaveStatus.LACKING_API_ADDRESS;

@SpringBootApplication
public class PostParserApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostParserApplication.class);

    public static void main(String[] args) {
        PlaceholderApiConfiguration config = new PlaceholderApiConfiguration();
        final SavePostsContext savePostsContext = prepareSavePostsContext(config);
        final PlaceholderApiImpl api = new PlaceholderApiImpl();
        final ExecutorService postsToFilesExecutor = Executors.newCachedThreadPool();

        Optional.of(api.getAllPosts(savePostsContext.baseUrl()))
                .map(posts -> PostsToFilesTaskCreator.prepareSaveToFileTasks(posts, savePostsContext))
                .map(callables -> PostsSaveInvoker.invokeSave(callables, postsToFilesExecutor))
                .map(PostResultHandler::handleResults)
                .orElse(prepareInternalErrorResult())
                .forEach(result -> LOGGER.info(result.getResultMessage()));

        postsToFilesExecutor.shutdown();
    }

    private static SavePostsContext prepareSavePostsContext(PlaceholderApiConfiguration config){
        final String baseUrl = config.getPlaceholderUrl();
        final String fileLocalization = config.getFileLocalization();
        final String projectPath = new File("").getAbsolutePath();
        return new SavePostsContext(baseUrl, projectPath, fileLocalization, new ObjectMapper());
    }

    private static List<Result> prepareInternalErrorResult() {
        return List.of(new InternalErrorResult("Not provided api address", LACKING_API_ADDRESS));
    }


}
