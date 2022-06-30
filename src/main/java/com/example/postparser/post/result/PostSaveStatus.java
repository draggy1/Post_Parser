package com.example.postparser.post.result;

/**
 * Status of save
 */
public enum PostSaveStatus {
    SUCCESS("Post with id: %d successfully saved to file"),
    FAILURE("Save post with id: %d to file failed"),
    INTERRUPTED("Save post to file task interrupted: Exception message: %s"),
    LACKING_API_ADDRESS("%s"),
    FAILED_SAVE_TASK("Post save task failed. Exception message: %s"),
    TIMEOUT("Timout occurred during save post to file");

    PostSaveStatus(String message) {
        this.message = message;
    }

    public final String message;
}