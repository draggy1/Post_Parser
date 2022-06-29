package com.example.postparser.post;

import com.example.postparser.post.configuration.PlaceholderConfiguration;
import com.google.inject.Inject;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PostFileCreator {
    private final PlaceholderConfiguration config;

    @Inject
    public PostFileCreator(PlaceholderConfiguration config) {
        this.config = config;
    }

    File getFile(Post post) {
        final String fileName = String.format("%s.json", post.id());
        Path firstPart = Path.of(new File("").getAbsolutePath());
        Path secondPart = Paths.get(config.getFileLocalization()).resolve(fileName);
        Path full = Path.of(firstPart.toString(), secondPart.toString());

        return new File(full.toUri());
    }
}
