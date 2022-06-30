package com.example.postparser.post;

import com.example.postparser.post.configuration.Configuration;
import com.example.postparser.post.repository.Repository;
import com.example.postparser.post.result.Result;
import com.google.inject.Inject;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;

public record PostsToFilesTaskCreator(Repository postRepository, PostFileCreator creator, Configuration config) {
    @Inject
    public PostsToFilesTaskCreator {
    }

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
