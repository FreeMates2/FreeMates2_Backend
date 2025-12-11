package project.freemates2.global.security;


import java.time.Duration;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

// Redis 설정값
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
@Configuration
public class JwtProperties {
  private String secretKey;
  private Duration accessExpTime;
  private Duration refreshExpTime;
  private String tokenPrefix;
}
