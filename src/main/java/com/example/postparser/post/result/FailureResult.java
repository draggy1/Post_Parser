package com.example.postparser.post.result;

import com.example.postparser.post.Post;

public record FailureResult(Post failedPost, PostSaveStatus status) implements Result {

    @Override
    public String getResultMessage() {
        return String.format(status.message, failedPost.id());
    }
}
