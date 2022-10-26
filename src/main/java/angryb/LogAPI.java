package angryb;

import angryb.model.Helper;
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

        Helper helper = new Helper();
        TimerTask repeatedTask = new TimerTask() {
            public void run() {

                helper.addGamesToAllPlayers();
                helper.updateAllPlayers();
                helper.updateMetadata();
                System.out.println("SCHEDULED TASK EXECUTED");
            }
        };
        Timer timer = new Timer("Timer");

        long delay = 1000L;
        long day = 1000L * 60L * 60L * 24L;

        timer.scheduleAtFixedRate(repeatedTask, delay, day);
        SpringApplication.run(LogAPI.class, args);
    }
}
