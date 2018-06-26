package com.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.filter.MarkerFilter;
import org.apache.logging.log4j.spi.AbstractLogger;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.logging.LoggingApplicationListener;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;

public class InitLoggerListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent>, Ordered {

    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        context.getConfiguration().addFilter(MarkerFilter.createFilter(
                AbstractLogger.FLOW_MARKER.getName(), Filter.Result.ACCEPT, Filter.Result.NEUTRAL));
        context.updateLoggers();
    }

    public int getOrder() {
        return LoggingApplicationListener.DEFAULT_ORDER + 1;
    }
}
