package project.freemates2.external.kakao.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.freemates2.external.kakao.config.KakaoPlaceFetchProperties;
import project.freemates2.api.place.domain.PlaceCategoryType;
import project.freemates2.api.university.domain.University;
import project.freemates2.external.kakao.dto.KakaoPlaceResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoPlaceFetchService {

  private final KakaoPlaceClient kakaoPlaceClient;
  private final KakaoPlaceFetchProperties kakaoPlaceFetchProperties;

  private static final List<PlaceCategoryType> CATEGORIES = List.of(
      PlaceCategoryType.CAFE,
      PlaceCategoryType.FOOD,
      PlaceCategoryType.SHOPPING,
      PlaceCategoryType.WALK,
      PlaceCategoryType.PLAY,
      PlaceCategoryType.HOSPITAL
  );

  // 대학별 모든 카테고리 조회 시 사용
  public Mono<List<KakaoPlaceResponse.Document>> fetchPlacesFor(University university) {
    double x = university.getLng();
    double y = university.getLat();

    return Flux.fromIterable(CATEGORIES)
        .concatMap(placeCategoryType ->
            Flux.fromIterable(placeCategoryType.getKakaoCodes())
                .concatMap(code-> fetchAllPagesFor(code,x,y)) // 아래 메서드에서 client 사용
                .flatMapIterable(response -> response.documents())
        )
        .filter(doc ->
            CATEGORIES.stream()
                .anyMatch(cat -> cat.getKakaoCodes().contains(doc.categoryGroupCode()))
        )
        .collectList()
        .doOnError(e -> log.error("카카오 장소 전체 조회 실패: {}", e.getMessage(), e));
  }

  // 카테고리 별 모든 페이지 조회
  private Flux<KakaoPlaceResponse> fetchAllPagesFor(String categoryCode, double x, double y) {
    return Mono.just(1)
        .expand(page -> page < kakaoPlaceFetchProperties.getMaxPage() ? Mono.just(page + 1) : Mono.empty())
        .concatMap(page ->
            kakaoPlaceClient.searchByCategory(
                    categoryCode, page, x, y, kakaoPlaceFetchProperties.getRadius(), kakaoPlaceFetchProperties.getPageSize()
                )
                .onErrorResume(e -> {
                  log.warn("카테고리 {} page {} 호출 실패: {}", categoryCode, page, e.getMessage());
                  return Mono.just(new KakaoPlaceResponse(null, List.of()));
                })
        )
        .takeUntil(resp -> resp.meta() == null || resp.meta().isEnd());
  }

}
