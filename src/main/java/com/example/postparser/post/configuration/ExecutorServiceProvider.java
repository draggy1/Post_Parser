package com.example.postparser.post.configuration;

import com.google.inject.Provider;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceProvider implements Provider<ExecutorService> {
    @Override
    public ExecutorService get() {
        return Executors.newCachedThreadPool();
    }
}
