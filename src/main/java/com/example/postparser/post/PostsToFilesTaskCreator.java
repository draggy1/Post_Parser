package com.example.postparser.post;

import com.example.postparser.post.repository.Repository;
import com.example.postparser.post.result.Result;
import com.google.inject.Inject;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;

public final class PostsToFilesTaskCreator {
    @Inject
    private Repository postRepository;
    @Inject
    private PostFileCreator creator;

    public List<Callable<Result>> prepareSaveToFileTasks(List<Post> posts) {
        return posts
                .stream()
                .map(this::prepareSaveToFileTask)
                .toList();
    }

    private Callable<Result> prepareSaveToFileTask(Post post) {
        final File file = creator.getFile(post);
        return () -> postRepository.save(post, file);
    }
}
