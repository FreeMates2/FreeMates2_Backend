package project.freemates2.external.kakao.application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import project.freemates2.api.place.domain.Place;
import project.freemates2.api.place.domain.PlaceCategoryType;
import project.freemates2.external.kakao.dto.KakaoPlaceResponse;

class KakaoPlaceMapperTest {

  KakaoPlaceMapper mapper = Mappers.getMapper(KakaoPlaceMapper.class);

  @Test
  void toPlace() {
    // given
    KakaoPlaceResponse.Document doc = new KakaoPlaceResponse.Document(
        "1",
        "마트",
        "대형마트",
        "MT1",         // 🔥 여기 포인트
        "대형마트",
        "010-0000-0000",
        "주소",
        "도로명",
        "127.0",
        "37.5",
        "url",
        "100"
    );

    // when
    Place place = mapper.toPlace(doc);

    // then
    assertEquals(PlaceCategoryType.SHOPPING, place.getCategory());
  }
  }