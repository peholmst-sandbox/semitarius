package net.pkhapps.semitarius.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

/**
 * Main class for the Semitarius Server application.
 */
@SpringBootApplication
public class SemitariusServerApp {

    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }

    public static void main(String[] args) {
        SpringApplication.run(SemitariusServerApp.class, args);
    }
}
