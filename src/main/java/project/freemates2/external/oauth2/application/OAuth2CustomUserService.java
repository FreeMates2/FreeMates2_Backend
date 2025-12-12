package project.freemates2.external.oauth2.application;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import project.freemates2.api.user.application.UserService;
import project.freemates2.api.user.domain.User;
import project.freemates2.external.oauth2.domain.OAuth2UserInfo;
import project.freemates2.external.oauth2.domain.OAuth2UserPrincipal;
import project.freemates2.external.oauth2.dto.OAuth2UserDto;
import project.freemates2.external.oauth2.infrastructure.OAuth2UserInfoFactory;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuth2CustomUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  private final UserService userService;
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) {
    OAuth2User delegate = new DefaultOAuth2UserService().loadUser(userRequest);
    Map<String, Object> attributes = delegate.getAttributes();

    // provider 판별
    String registrationId = userRequest.getClientRegistration().getRegistrationId();

    // provider별 UserInfo 생성
    OAuth2UserInfo userInfo = OAuth2UserInfoFactory.of(registrationId, attributes);

    // UserInfo -> DTO
    OAuth2UserDto dto = OAuth2UserDto.from(userInfo);

    // 우리 도메인 User 조회/생성
    User user = userService.handleOAuth2Login(dto);

    // SecurityContext에 올라갈 Principal 반환
    return new OAuth2UserPrincipal(user, attributes);
  }

}
