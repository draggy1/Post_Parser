package com.example.postparser.post.result;

import com.example.postparser.post.Post;

/**
 * Success result record representation
 * @param post
 *      Post {@link Post} instance which was saved
 * @param status
 *      Status what went wrong {@link PostSaveStatus}
 *
 */
public record SuccessResult(Post post, PostSaveStatus status) implements Result {

    @Override
    public String getResultMessage() {
        return String.format(status.message, post.id());
    }
}
