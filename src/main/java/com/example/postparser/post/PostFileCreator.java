package com.example.postparser.post;

import com.example.postparser.post.configuration.PlaceholderConfiguration;
import com.google.inject.Inject;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public record PostFileCreator(PlaceholderConfiguration config) {
    @Inject
    public PostFileCreator {
    }

    File getFile(Post post, String absolutePath) {
        final String fileName = String.format("%s.json", post.id());
        Path firstPart = Path.of(absolutePath);
        Path secondPart = Paths.get(config.getFileLocalization()).resolve(fileName);
        Path full = Path.of(firstPart.toString(), secondPart.toString());

        return new File(full.toUri());
    }
}
