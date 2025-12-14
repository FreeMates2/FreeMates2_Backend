package project.freemates2.admin.place.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.freemates2.api.place.domain.Place;
import project.freemates2.api.place.domain.PlaceRepository;
import project.freemates2.api.university.domain.University;
import project.freemates2.external.kakao.application.KakaoPlaceFetchService;
import project.freemates2.external.kakao.application.KakaoPlaceMapper;
import project.freemates2.external.kakao.dto.KakaoPlaceResponse;
import project.freemates2.external.kakao.dto.KakaoPlaceResponse.Document;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class PlaceSyncServiceTest {
  @Mock
  KakaoPlaceFetchService kakaoPlaceFetchService;

  @Mock
  PlaceRepository placeRepository;

  @Mock
  KakaoPlaceMapper kakaoPlaceMapper;

  PlaceSyncService placeSyncService;

  @BeforeEach
  void setUp() {
    placeSyncService = new PlaceSyncService(kakaoPlaceFetchService, placeRepository, kakaoPlaceMapper);
  }

  @Test
  void syncPlacesFor() {
    // given
    University uni = University.builder()
        .lat(37.55)
        .lng(127.07)
        .name("세종대")
        .slug("sejong")
        .build();

    KakaoPlaceResponse.Document doc1 = new KakaoPlaceResponse.Document(
        "1", "장소1", "카테고리", "CE7",
        "카페", "010-0000-0000", "주소1", "도로명1",
        "127.0", "37.5", "url1", "100"
    );

    KakaoPlaceResponse.Document doc2 = new KakaoPlaceResponse.Document(
        "2", "장소2", "카테고리", "FD6",
        "음식점", "010-1111-1111", "주소2", "도로명2",
        "127.0", "37.5", "url2", "200"
    );

    List<Document> docs = List.of(doc1, doc2);

    when(kakaoPlaceFetchService.fetchPlacesFor(uni))
        .thenReturn(Mono.just(docs));

    Place place1 = Place.builder().kakaoPlaceId("1").build();
    Place place2 = Place.builder().kakaoPlaceId("2").build();

    when(kakaoPlaceMapper.toPlace(doc1)).thenReturn(place1);
    when(kakaoPlaceMapper.toPlace(doc2)).thenReturn(place2);

    // when
    placeSyncService.syncPlacesFor(uni);

    // then
    ArgumentCaptor<List<Place>> captor = ArgumentCaptor.forClass(List.class);
    verify(placeRepository).saveAll(captor.capture());

    List<Place> saved = captor.getValue();
    assertEquals(2, saved.size());
    assertTrue(saved.stream().anyMatch(p -> "1".equals(p.getKakaoPlaceId())));
    assertTrue(saved.stream().anyMatch(p -> "2".equals(p.getKakaoPlaceId())));
  }
}