package com.example.postparser.post.result;

import com.example.postparser.post.Post;

/**
 * Failure result record representation
 * @param failedPost
 *      Post {@link Post} instance which failed
 * @param status
 *      Status what went wrong {@link PostSaveStatus}
 *
 */
public record FailureResult(Post failedPost, PostSaveStatus status) implements Result {

    @Override
    public String getResultMessage() {
        return String.format(status.message, failedPost.id());
    }
}
