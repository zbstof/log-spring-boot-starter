package com.example;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.filter.MarkerFilter;
import org.apache.logging.log4j.spi.AbstractLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LoggingInitializationContext;
import org.springframework.boot.logging.LoggingSystemProperties;
import org.springframework.boot.logging.log4j2.Log4J2LoggingSystem;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Configuration
public class AutoConfig {

    @Autowired
    private ConfigurableEnvironment environment;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private Log4J2LoggingSystem loggingSystem;

    @PropertySource("classpath:defaults/application.properties")
    @Configuration
    public static class DefaultLogPropertiesConfig {
    }

    @PostConstruct
    public void setup() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        reinitializeLoggingSystem(context);
        addMarker(context);
        context.updateLoggers();
    }

    private void reinitializeLoggingSystem(LoggerContext context) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        markUninitialized(context);
        new LoggingSystemProperties(environment).apply();
        loggingSystem.initialize(new LoggingInitializationContext(environment), null, null);
    }

    private void addMarker(LoggerContext context) {
        context.getConfiguration().addFilter(MarkerFilter.createFilter(
                AbstractLogger.FLOW_MARKER.getName(), Filter.Result.ACCEPT, Filter.Result.NEUTRAL));
    }

    private void markUninitialized(LoggerContext context)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        @SuppressWarnings("JavaReflectionMemberAccess")
        Method markAsUninitialized = Log4J2LoggingSystem.class.getDeclaredMethod("markAsUninitialized", LoggerContext.class);
        markAsUninitialized.setAccessible(true);
        markAsUninitialized.invoke(loggingSystem, context);
    }
}
