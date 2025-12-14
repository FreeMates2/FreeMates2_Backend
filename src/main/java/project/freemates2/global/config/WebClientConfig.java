package project.freemates2.global.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import project.freemates2.external.kakao.application.KakaoPlaceClient;
import project.freemates2.external.kakao.infrastructure.KakaoPlaceHttpClient;
import project.freemates2.global.util.PayloadConverter;


@Configuration
public class WebClientConfig {
  // Kakao 검색 웹 클라이언트
  @Bean("kakaoPlaceClient")
  public KakaoPlaceClient kakaoPlaceClient(
      @Value("${kakao.api.key}" ) String apiKey,
      @Value("${kakao.host}" ) String endpoint,
      PayloadConverter payloadConverter
  ) {
    WebClient client = WebClient.builder()
        .baseUrl(endpoint)
        .build();
    return new KakaoPlaceHttpClient(client, apiKey, payloadConverter);

  }


}
