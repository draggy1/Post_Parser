package com.example.postparser.post.repository;

import com.example.postparser.post.Post;
import com.example.postparser.post.result.Result;

import java.io.File;

public interface Repository {
    Result save(Post post, File file);
}
