package project.freemates2.admin.place.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.freemates2.api.place.domain.PlaceRepository;
import project.freemates2.api.university.domain.University;
import project.freemates2.external.kakao.application.KakaoPlaceFetchService;
import project.freemates2.external.kakao.application.KakaoPlaceMapper;

@Service
@RequiredArgsConstructor
public class PlaceSyncService {
  private final KakaoPlaceFetchService kakaoPlaceFetchService;
  private final PlaceRepository placeRepository;
  private final KakaoPlaceMapper kakaoPlaceMapper;

  @Transactional
  public void syncPlacesFor(University university) {
    kakaoPlaceFetchService.fetchPlacesFor(university)
        .map(docs -> docs.stream()
            .map(kakaoPlaceMapper::toPlace)
            .toList()
        )
        .doOnNext(placeRepository::saveAll)
        .block(); // 배치/관리자용이니 block()도 가능 (비동기 유지하고 싶으면 다른 방식)
  }

}
