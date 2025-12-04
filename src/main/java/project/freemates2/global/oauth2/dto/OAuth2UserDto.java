package project.freemates2.global.oauth2.dto;

import project.freemates2.api.user.domain.AuthProvider;
import project.freemates2.global.oauth2.domain.OAuth2UserInfo;

public record OAuth2UserDto(
    AuthProvider provider,
    String providerUserId,
    String email
) {

  public static OAuth2UserDto from(OAuth2UserInfo info) {
    AuthProvider provider = AuthProvider.valueOf(info.provider().toUpperCase());
    return new OAuth2UserDto(provider, info.providerId(), info.email());
  }
}
