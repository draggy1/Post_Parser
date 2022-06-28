package com.example.postparser.post.result;

import com.example.postparser.post.Post;

public record SuccessResult(Post post, PostSaveStatus status) implements Result {

    @Override
    public String getResultMessage() {
        return String.format(status.message, post.id());
    }
}
