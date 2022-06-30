package com.example.postparser.api;

import com.example.postparser.post.Post;

import java.util.List;

/**
 * Api interface class
 */
public interface Api {
    List<Post> getAllPosts();
}
