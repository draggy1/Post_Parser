package com.example.postparser.post.configuration;


import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;

/**
 * Common configuration for project
 */
public class Configuration {
    private final Config defaultConfig = ConfigFactory.parseResources("defaults.conf");

    public String getPlaceholderUrl(){
        return defaultConfig.getString("conf.placeholder.url");
    }

    public String getFileLocalization() {
        return defaultConfig.getString("conf.posts.path");
    }

    public String getAbsolutePath() {
        return new File("").getAbsolutePath();
    }
}
