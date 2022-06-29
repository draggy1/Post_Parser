package com.example.postparser.configuration;


import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class PlaceholderApiConfiguration {
    private final Config defaultConfig = ConfigFactory.parseResources("defaults.conf");

    public String getPlaceholderUrl(){
        return defaultConfig.getString("conf.placeholder.url");
    }

    public String getFileLocalization() {
        return defaultConfig.getString("conf.posts.path");
    }
}
