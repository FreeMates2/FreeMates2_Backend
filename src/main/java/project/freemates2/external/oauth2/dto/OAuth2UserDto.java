package project.freemates2.external.oauth2.dto;

import project.freemates2.api.user.domain.AuthProvider;
import project.freemates2.external.oauth2.domain.OAuth2UserInfo;

public record OAuth2UserDto(
    AuthProvider provider,
    String providerUserId,
    String email
) {

  public static OAuth2UserDto from(OAuth2UserInfo info) {
    AuthProvider provider = AuthProvider.fromString(info.provider());
    return new OAuth2UserDto(provider, info.providerId(), info.email());
  }
}
