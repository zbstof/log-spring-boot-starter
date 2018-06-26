package com.example;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;

public class EventsTracingListener implements ApplicationListener<ApplicationEvent>, Ordered {

    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println(">>> Event: " + event);
    }

    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
