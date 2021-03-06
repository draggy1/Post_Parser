package com.example.postparser.post.configuration;

import com.example.postparser.api.Api;
import com.example.postparser.api.PlaceholderApiImpl;
import com.example.postparser.post.repository.PostRepository;
import com.example.postparser.post.repository.Repository;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import java.util.concurrent.ExecutorService;

/**
 * Guice module in which are configured binders for interfaces
 */
public class PostParserModule extends AbstractModule {
    protected void configure() {
        bind(Api.class).to(PlaceholderApiImpl.class).in(Scopes.NO_SCOPE);
        bind(Repository.class).to(PostRepository.class).in(Scopes.NO_SCOPE);
        bind(ExecutorService.class).toProvider(ExecutorServiceProvider.class).in(Scopes.NO_SCOPE);
    }
}
