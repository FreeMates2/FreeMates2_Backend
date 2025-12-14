package project.freemates2.external.kakao.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.freemates2.api.university.domain.University;
import project.freemates2.external.kakao.config.KakaoPlaceFetchProperties;
import project.freemates2.external.kakao.dto.KakaoPlaceResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class KakaoPlaceFetchServiceTest {

  @Mock
  KakaoPlaceClient kakaoPlaceClient;

  @Mock
  KakaoPlaceFetchProperties kakaoPlaceFetchProperties;


  KakaoPlaceFetchService kakaoPlaceFetchService;

  @BeforeEach
  void setUp() {
    kakaoPlaceFetchService = new KakaoPlaceFetchService(kakaoPlaceClient, kakaoPlaceFetchProperties);

    // 테스트용 기본값
    when(kakaoPlaceFetchProperties.getRadius()).thenReturn(1000);
    when(kakaoPlaceFetchProperties.getPageSize()).thenReturn(15);
    when(kakaoPlaceFetchProperties.getMaxPage()).thenReturn(3);


  }

  @Test
  void fetchPlacesFor() {

    /**
     * given
     * */
    University uni = University.builder()
        .lat(37.55)
        .lng(127.07)
        .name("세종대")
        .slug("sejong")
        .build();

    // Meta: 1페이지는 isEnd=false, 2페이지는 isEnd=true
    KakaoPlaceResponse.Meta metaPage1 = new KakaoPlaceResponse.Meta(30, 30, false, null);
    KakaoPlaceResponse.Meta metaPage2 = new KakaoPlaceResponse.Meta(30, 30, true, null);

    KakaoPlaceResponse.Document doc1 =
        new KakaoPlaceResponse.Document("1", "카페A", "카페 > 디저트", "CE7",
            "카페", "010-1111-1111", "주소1", "도로명1", "127.0", "37.5", "url1", "100");

    KakaoPlaceResponse.Document doc2 =
        new KakaoPlaceResponse.Document("2", "카페B", "카페 > 디저트", "CE7",
            "카페", "010-2222-2222", "주소2", "도로명2", "127.0", "37.5", "url2", "200");

    KakaoPlaceResponse resp1 = new KakaoPlaceResponse(metaPage1, List.of(doc1));
    KakaoPlaceResponse resp2 = new KakaoPlaceResponse(metaPage2, List.of(doc2));

    // PlaceCategoryType.CAFE 안에 "CE7"이 들어있다고 가정
    // page=1,2 에 대해 mock
    when(kakaoPlaceClient.searchByCategory(eq("CE7"), eq(1), anyDouble(), anyDouble(), anyInt(), anyInt()))
        .thenReturn(Mono.just(resp1));
    when(kakaoPlaceClient.searchByCategory(eq("CE7"), eq(2), anyDouble(), anyDouble(), anyInt(), anyInt()))
        .thenReturn(Mono.just(resp2));

    // 다른 카테고리/코드들은 빈 응답으로
    when(kakaoPlaceClient.searchByCategory(argThat(code -> !"CE7".equals(code)), anyInt(), anyDouble(), anyDouble(), anyInt(), anyInt()))
        .thenReturn(Mono.just(new KakaoPlaceResponse(null, List.of())));

    /**
     * when
     * */

    Mono<List<KakaoPlaceResponse.Document>> mono = kakaoPlaceFetchService.fetchPlacesFor(uni);

    /**
     * then
     * */

    StepVerifier.create(mono)
        .assertNext(docs -> {
          // 1페이지 + 2페이지 → 총 2개
          assertEquals(2, docs.size());
          assertEquals("카페A", docs.get(0).placeName());
          assertEquals("카페B", docs.get(1).placeName());
        })
        .verifyComplete();
  }


  }
