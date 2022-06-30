package com.example.postparser.post;

import com.example.postparser.post.configuration.Configuration;
import com.google.inject.Inject;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class represent for File creation
 * @param config
 *      common config class {@link Configuration}
 */
public record PostFileCreator(Configuration config) {
    @Inject
    public PostFileCreator {
    }

    /**
     * Method returns file {@link File} created from {@link Post} post absolute path
     * and file localization from config
     * @param post
     *      post from which will be created file
     * @param absolutePath
     *      absolute Path to project
     * @return
     *      created File object
     */
    File getFile(Post post, String absolutePath) {
        final String fileName = String.format("%s.json", post.id());
        Path firstPart = Path.of(absolutePath);
        Path secondPart = Paths.get(config.getFileLocalization()).resolve(fileName);
        Path full = Path.of(firstPart.toString(), secondPart.toString());

        return new File(full.toUri());
    }
}
