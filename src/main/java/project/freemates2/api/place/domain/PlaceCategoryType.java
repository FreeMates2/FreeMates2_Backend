package project.freemates2.api.place.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PlaceCategoryType {
  CAFE("카페", Set.of("CE7")),
  FOOD("먹거리", Set.of("FD6")),
  SHOPPING("쇼핑", Set.of("MT1", "CS2")),
  WALK("산책", Set.of("AT4")),
  PLAY("놀거리", Set.of("CT1")),
  HOSPITAL("병원", Set.of("HP8", "PM9"));

  private final String label;
  private final Set<String> kakaoCodes;


  public boolean matches(String kakaoCode) {
    return kakaoCodes.contains(kakaoCode);
  }
  @JsonCreator
  public static PlaceCategoryType of(String kakaoCode) {
    return Arrays.stream(values())
        .filter(cat -> cat.matches(kakaoCode))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Invalid kakaoCode: " + kakaoCode));
  }
}
