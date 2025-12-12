package project.freemates2.external.oauth2.infrastructure;

import java.util.Map;
import project.freemates2.external.oauth2.domain.OAuth2UserInfo;

public class OAuth2Attributes {

  /**
   * 카카오 userInfo 응답 래퍼
   */
  public record Kakao(Map<String, Object> attributes) implements OAuth2UserInfo {

    private Map<String, Object> kakaoAccount() {
      return (Map<String, Object>) attributes.get("kakao_account");
    }

    @Override
    public String providerId() {
      return String.valueOf(attributes.get("id"));
    }

    @Override
    public String provider() {
      return "kakao";
    }

    @Override
    public String email() {
      var account = kakaoAccount();
      return account != null ? (String) account.get("email") : null;
    }
  }

  /**
   * 네이버 userInfo 응답 래퍼
   */
  public record Naver(Map<String, Object> attributes) implements OAuth2UserInfo {

    private Map<String, Object> response() {
      return (Map<String, Object>) attributes.get("response");
    }

    @Override
    public String providerId() {
      var res = response();
      return res != null ? (String) res.get("id") : null;
    }

    @Override
    public String provider() {
      return "naver";
    }

    @Override
    public String email() {
      var res = response();
      return res != null ? (String) res.get("email") : null;
    }
  }

}
