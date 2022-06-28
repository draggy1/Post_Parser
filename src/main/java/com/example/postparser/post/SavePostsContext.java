package com.example.postparser.post;

import com.fasterxml.jackson.databind.ObjectMapper;

public record SavePostsContext(String baseUrl, String absolutePath, String fileLocalization, ObjectMapper mapper) {
}
