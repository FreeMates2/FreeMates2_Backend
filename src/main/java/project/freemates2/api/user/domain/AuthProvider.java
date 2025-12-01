package project.freemates2.api.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthProvider {
  LOCAL("로컬"),
  GOOGLE("구글"),
  KAKAO("카카오"),
  NAVER("네이버");

  private final String description;

}
