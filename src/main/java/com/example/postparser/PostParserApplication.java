package com.example.postparser;

import com.example.postparser.post.PostParserService;
import com.example.postparser.post.configuration.PostParserModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class PostParserApplication {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new PostParserModule());
        PostParserService mySystemInstance = injector.getInstance(PostParserService.class);
        mySystemInstance.parse();
    }
}
