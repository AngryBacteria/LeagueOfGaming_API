package angryb;

import angryb.service.InitService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class LogAPI {


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }

    public static void main(String[] args) throws InterruptedException {
        //SpringApplication.run(LogAPI.class, args);
        ApplicationContext applicationContext = SpringApplication.run(LogAPI.class, args);
        InitService initService = applicationContext.getBean(InitService.class);
//        initService.addGamesToSummoners();
//        initService.updateMetadata();
//        Thread.sleep(60_000);
//        initService.updateMetadata();
        System.out.println("---------------------------DONE---------------------------");
    }
}
