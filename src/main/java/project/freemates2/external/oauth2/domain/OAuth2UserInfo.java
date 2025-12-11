package project.freemates2.external.oauth2.domain;

public interface OAuth2UserInfo {
  String providerId();   // 소셜 고유 ID
  String provider();     // "kakao", "naver"
  String email();

}
