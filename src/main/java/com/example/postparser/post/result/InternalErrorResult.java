package com.example.postparser.post.result;

/**
 * Failure result record representation
 *
 * @param stackMessage
 *      message from exception
 *   @param status
 *      Status what went wrong {@link PostSaveStatus}
 */
public record InternalErrorResult(String stackMessage, PostSaveStatus status) implements Result {
    public static InternalErrorResult of(String message, PostSaveStatus status){
        return new InternalErrorResult(message, status);
    }

    @Override
    public String getResultMessage() {
        return String.format(status.message, stackMessage);
    }
}
