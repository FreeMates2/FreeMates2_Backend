package project.freemates2.global.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOriginPatterns("http://localhost:3000","https://your.app")
        .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
        .allowCredentials(true)
        .maxAge(3600);
  }
}

