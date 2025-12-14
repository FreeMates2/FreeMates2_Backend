package project.freemates2.external.kakao.application;

import project.freemates2.external.kakao.dto.KakaoPlaceResponse;
import reactor.core.publisher.Mono;

public interface KakaoPlaceClient {
  Mono<KakaoPlaceResponse> searchByCategory(
      String categoryCode,
      int page,
      double x,
      double y,
      int radius,
      int size
  );

}
