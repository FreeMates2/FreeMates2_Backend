package project.freemates2.external.kakao.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "place.fetch")
@Getter
@Setter
@Validated
@Configuration
public class KakaoPlaceFetchProperties {

  private Integer imageCnt;

  private Integer radius;

  private Integer pageSize;

  private Integer maxPage;

}
