package project.freemates2.api.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

  ROLE_USER("일반 회원"),
  ROLE_ADMIN("관리자");

  private final String description;
}

