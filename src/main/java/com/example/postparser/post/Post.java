package com.example.postparser.post;

/**
 * Model class representing Post from Api
 * @param userId
 *      userId from Api
 * @param id
 *      post id
 * @param title
 *      post title
 * @param body
 *      post body
 */
public record Post(int userId, int id, String title, String body) {
}
