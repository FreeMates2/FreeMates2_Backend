package project.freemates2.external.kakao.infrastructure;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import project.freemates2.external.kakao.application.KakaoPlaceClient;
import project.freemates2.external.kakao.dto.KakaoPlaceResponse;
import project.freemates2.global.util.PayloadConverter;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class KakaoPlaceHttpClient implements KakaoPlaceClient {
  private final WebClient kakaoWebClient;
  private final String apiKey;
  private final PayloadConverter payloadConverter;

  @Value("${kakao.place}")
  private String place;

  @Override
  public Mono<KakaoPlaceResponse> searchByCategory(
      String categoryCode,
      int page,
      double x,
      double y,
      int radius,
      int size
  ) {
    return kakaoWebClient.get()
        .uri(b -> b.path(place)
            .queryParam("category_group_code", categoryCode)
            .queryParam("x", x)
            .queryParam("y", y)
            .queryParam("radius", radius)
            .queryParam("size", size)
            .queryParam("page", page)
            .build()
        )
        .retrieve()
        .bodyToMono(KakaoPlaceResponse.class)
        .doOnError(e ->
            log.warn("카카오 API 호출 실패: code={}, page={}, msg={}",
                categoryCode, page, e.getMessage())
        );
  }

}
