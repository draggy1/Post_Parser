package com.example.postparser;

import com.example.postparser.common.WebClientCreator;
import com.example.postparser.post.PostResultHandler;
import com.example.postparser.post.PostsSaveInvoker;
import com.example.postparser.post.PostsToFilesTaskCreator;
import com.example.postparser.post.SavePostsContext;
import com.example.postparser.post.result.InternalErrorResult;
import com.example.postparser.post.result.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.postparser.post.result.PostSaveStatus.LACKING_API_ADDRESS;

@SpringBootApplication
public class PostParserApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostParserApplication.class);

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(PostParserApplication.class, args);
        SavePostsContext savePostsContext = prepareSavePostsContext(context);


        final ExecutorService postsToFilesExecutor = Executors.newCachedThreadPool();
        WebClientCreator.get(savePostsContext.baseUrl(), "posts")
                .map(client -> PostsToFilesTaskCreator.prepareTaskToPerform(client, savePostsContext))
                .map(callables -> PostsSaveInvoker.invokeSave(callables, postsToFilesExecutor))
                .map(PostResultHandler::handleResults)
                .orElse(prepareInternalErrorResult())
                .forEach(result -> LOGGER.info(result.getResultMessage()));

        postsToFilesExecutor.shutdown();
    }

    private static SavePostsContext prepareSavePostsContext(ConfigurableApplicationContext context){
        final ConfigurableEnvironment environment = context.getEnvironment();
        final String baseUrl = environment.getProperty("placeholder.url");
        final String fileLocalization = environment.getProperty("posts.path");
        final String projectPath = new File("").getAbsolutePath();
        return new SavePostsContext(baseUrl, projectPath,fileLocalization,new ObjectMapper());
    }

    private static List<Result> prepareInternalErrorResult() {
        return List.of(new InternalErrorResult("Not provided api address", LACKING_API_ADDRESS));
    }


}
