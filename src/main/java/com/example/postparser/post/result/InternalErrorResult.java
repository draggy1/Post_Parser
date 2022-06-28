package com.example.postparser.post.result;

public record InternalErrorResult(String stackMessage, PostSaveStatus status) implements Result {

    @Override
    public String getResultMessage() {
        return String.format(status.message, stackMessage);
    }
}
