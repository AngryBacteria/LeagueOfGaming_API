package com;

import com.model.Helper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class LogAPI {


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }

    public static void main(String[] args) {

        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                Helper helper = new Helper();
                helper.addGamesToAllPlayers();
                helper.updateAllPlayers();
                helper.updateMetadata();
                System.out.println("SCHEDULED TASK EXECUTED");
            }
        };
        Timer timer = new Timer("Timer");

        long delay = 1000L;
        //24 hours
        long day = 1000L * 60L * 60L * 24L;
        //long threeminute = 1000L * 60L * 3;
        timer.scheduleAtFixedRate(repeatedTask, delay, day);
        SpringApplication.run(LogAPI.class, args);
    }
}
