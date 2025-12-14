package project.freemates2.external.kakao.application;

import java.util.ArrayList;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.freemates2.api.place.domain.PlaceCategoryType;
import project.freemates2.api.place.domain.Place;
import project.freemates2.external.kakao.dto.KakaoPlaceResponse;

@Mapper(componentModel = "spring")
public interface KakaoPlaceMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "kakaoPlaceId", source = "id")
  @Mapping(target = "x", expression = "java(Double.parseDouble(doc.y()))")
  @Mapping(target = "y", expression = "java(Double.parseDouble(doc.x()))")
  @Mapping(target = "imageUrl", ignore = true)
  /**
   * 나중에 세팅
   * */
  @Mapping(target = "category", ignore = true)
  @Mapping(target = "likeCount", ignore = true)
  @Mapping(target = "reviewCount", ignore = true)
  @Mapping(target = "viewCount", ignore = true)
  Place toPlace(KakaoPlaceResponse.Document doc);

  @AfterMapping
  default void fillDefaults(KakaoPlaceResponse.Document doc,
      @MappingTarget Place.PlaceBuilder builder) {
    builder
        .category(PlaceCategoryType.of(doc.categoryGroupCode()))
        .likeCount(0L)
        .reviewCount(0L)
        .viewCount(0L);
  }

}
