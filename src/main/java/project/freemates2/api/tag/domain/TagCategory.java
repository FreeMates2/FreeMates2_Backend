package project.freemates2.api.tag.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TagCategory {
  PLACE("장소"),
  COURSE("코스"),
  POST("게시물");

  private final String description;
}
