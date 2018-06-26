package com.example;

import java.io.IOException;

import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.support.ResourcePropertySource;

public class InitDefaultsListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent>, Ordered {

    private static final String DEFAULTS_LOCATION = "defaults/application.properties";
    private static final String DEFAULTS_DEBUG_LOCATION = "defaults/application-debug.properties";

    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();
        MutablePropertySources propertySources = environment.getPropertySources();
        try {
            propertySources.addLast(new ResourcePropertySource("defaults", "classpath:" + DEFAULTS_LOCATION));
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot find " + DEFAULTS_LOCATION + " on classpath", e);
        }
        if (environment.acceptsProfiles("debug")) {
            try {
                propertySources.addLast(new ResourcePropertySource("debug", "classpath:" + DEFAULTS_DEBUG_LOCATION));
            } catch (IOException e) {
                throw new IllegalArgumentException("Cannot find " + DEFAULTS_DEBUG_LOCATION + " on classpath", e);
            }
        }
    }

    public int getOrder() {
        return ConfigFileApplicationListener.DEFAULT_ORDER + 1;
    }
}
