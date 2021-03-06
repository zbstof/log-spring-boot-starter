package com.example;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.filter.MarkerFilter;
import org.apache.logging.log4j.spi.AbstractLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.logging.LoggingApplicationListener;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;

public class InitAppDebugLoggerListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent>, Ordered {

    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        if (event.getEnvironment().acceptsProfiles("debug")) {
            String rootPackageName = ((SpringApplication) event.getSource()).getMainApplicationClass().getPackage().getName();
            context.getConfiguration().getLoggerConfig(rootPackageName).setLevel(Level.DEBUG);
        }
        context.updateLoggers();
    }

    public int getOrder() {
        return LoggingApplicationListener.DEFAULT_ORDER + 1;
    }
}
