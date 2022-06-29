package com.example.postparser.post.result;

public record InternalErrorResult(String stackMessage, PostSaveStatus status) implements Result {
    public static InternalErrorResult of(String message, PostSaveStatus status){
        return new InternalErrorResult(message, status);
    }

    @Override
    public String getResultMessage() {
        return String.format(status.message, stackMessage);
    }
}
