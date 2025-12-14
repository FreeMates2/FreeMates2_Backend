package project.freemates2.external.kakao.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoPlaceResponse(
    Meta meta,
    List<Document> documents
) {
  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  @JsonIgnoreProperties(ignoreUnknown = true)
  public record Meta(
      Integer totalCount,
      Integer pageableCount,
      Boolean isEnd,
      SameName sameName
  ) {}

  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  @JsonIgnoreProperties(ignoreUnknown = true)
  public record SameName(
      String[] region,
      String keyword,
      String selectedRegion
  ) {}

  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  @JsonIgnoreProperties(ignoreUnknown = true)
  public record Document(
      String id,
      String placeName,
      String categoryName,
      String categoryGroupCode,
      String categoryGroupName,
      String phone,
      String addressName,
      String roadAddressName,
      String x,
      String y,
      String placeUrl,
      String distance
  ) {}

}
