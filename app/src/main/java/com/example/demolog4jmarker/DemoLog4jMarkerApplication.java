package com.example.demolog4jmarker;

import lombok.extern.log4j.Log4j2;

import org.apache.logging.log4j.spi.AbstractLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log4j2
@SpringBootApplication
public class DemoLog4jMarkerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoLog4jMarkerApplication.class, args);
        log.trace(AbstractLogger.ENTRY_MARKER, ">>> Hello <<<");
    }
}
