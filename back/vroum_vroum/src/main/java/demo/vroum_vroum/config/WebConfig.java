package demo.vroum_vroum.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    // Configuration CORS
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("**")                                  // behavior defined for all routes of the app
                        .allowedOrigins("http://localhost:4200")           // address of the front app
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")     // http methods allowed
                        .allowedHeaders("*")                                // allow all headers
                        .exposedHeaders("Authorization")                    // headers visible to JS
                        .allowCredentials(true);                            // needed for session cookies
            }
        };
    }
}
