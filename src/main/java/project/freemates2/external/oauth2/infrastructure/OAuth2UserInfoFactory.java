package project.freemates2.external.oauth2.infrastructure;

import java.util.Map;
import project.freemates2.external.oauth2.domain.OAuth2UserInfo;

public class OAuth2UserInfoFactory {

  public static OAuth2UserInfo of(String registrationId, Map<String, Object> attributes) {
    return switch (registrationId) {
      case "kakao" -> new OAuth2Attributes.Kakao(attributes);
      case "naver" -> new OAuth2Attributes.Naver(attributes);
      default -> throw new IllegalArgumentException("지원하지 않는 provider: " + registrationId);
    };
  }

}
