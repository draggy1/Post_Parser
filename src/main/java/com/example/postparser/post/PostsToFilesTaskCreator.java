package com.example.postparser.post;

import com.example.postparser.post.configuration.Configuration;
import com.example.postparser.post.repository.Repository;
import com.example.postparser.post.result.Result;
import com.google.inject.Inject;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Class is responsible for creating save task as {@link Callable}
 * @param postRepository
 *      class contains save method
 * @param creator
 *      class is responsible for file creation
 * @param config
 *      common configuration class
 */
public record PostsToFilesTaskCreator(Repository postRepository, PostFileCreator creator, Configuration config) {
    @Inject
    public PostsToFilesTaskCreator {
    }

    /**
     * Method is responsible for creating save task as {@link Callable}
     * @param posts
     *      list of posts to save
     */
    List<Callable<Result>> createSaveToFileTasks(List<Post> posts) {
        return posts
                .stream()
                .map(this::createSaveToFileTask)
                .toList();
    }

    private Callable<Result> createSaveToFileTask(Post post) {
        final String absolutePath = config.getAbsolutePath();
        final File file = creator.getFile(post, absolutePath);
        return () -> postRepository.save(post, file);
    }
}
