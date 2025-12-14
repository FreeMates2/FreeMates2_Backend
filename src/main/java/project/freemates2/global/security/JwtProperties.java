package project.freemates2.global.security;


import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

// JWT 설정값
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
@Validated
@Configuration
public class JwtProperties {
  @NotBlank
  private String secretKey;
  private Duration accessExpTime;
  private Duration refreshExpTime;
  private String tokenPrefix;
  private Boolean cookieSecure;

  @PostConstruct
  public void validateSecretKey(){
    if (secretKey == null || secretKey.isBlank()){
      throw new IllegalStateException("jwt.secretKey must be configured");
    }

    if (secretKey.getBytes(StandardCharsets.UTF_8).length < 32){
      throw new IllegalStateException("jwt.secretKey must be at least 32 bytes long (>= 256-bit for HS256)");
    }


  }
}
