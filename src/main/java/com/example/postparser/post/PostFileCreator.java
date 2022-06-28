package com.example.postparser.post;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PostFileCreator {
    static File getFile(Post post, String absolutePath, String fileLocalization) {
        final String fileName = String.format("%s.json", post.id());
        Path firstPart = Path.of(absolutePath);
        Path secondPart = Paths.get(fileLocalization).resolve(fileName);
        Path full = Path.of(firstPart.toString(), secondPart.toString());

        return new File(full.toUri());
    }
}
