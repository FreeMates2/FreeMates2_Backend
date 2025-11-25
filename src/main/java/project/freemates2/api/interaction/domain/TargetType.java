package project.freemates2.api.interaction.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TargetType {
  PLACE("장소"),
  COURSE("코스"),
  REVIEW("리뷰");
  private final String description;

}
