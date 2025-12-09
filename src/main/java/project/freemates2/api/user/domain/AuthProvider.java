package project.freemates2.api.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.freemates2.api.user.exception.UnsupportedProviderException;
import project.freemates2.global.util.EnumUtils;

@Getter
@AllArgsConstructor
public enum AuthProvider {
  LOCAL("로컬"),
  GOOGLE("구글"),
  KAKAO("카카오"),
  NAVER("네이버");

  private final String description;


  public static AuthProvider fromString(String provider) {
    return EnumUtils.parseIgnoreCaseOrThrow(
        provider,
        AuthProvider.class,
        UnsupportedProviderException.INSTANCE
    );
  }
}
