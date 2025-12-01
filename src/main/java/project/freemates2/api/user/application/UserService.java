package project.freemates2.api.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.freemates2.api.user.domain.Role;
import project.freemates2.api.user.domain.User;
import project.freemates2.api.user.domain.UserRepository;
import project.freemates2.global.oauth2.dto.OAuth2UserDto;

@RequiredArgsConstructor
@Service
public class UserService {
  private final UserRepository userRepository;

  @Transactional
  public User handleOAuth2Login(OAuth2UserDto dto) {
    return userRepository
        .findByProviderAndProviderUserId(dto.provider(), dto.providerUserId())
        .orElseGet(() -> createNewUser(dto));
  }

  private User createNewUser(OAuth2UserDto dto) {
    User user = User.builder()
        .provider(dto.provider())
        .providerUserId(dto.providerUserId())
        .email(dto.email())
        .role(Role.USER)
        .profileCompleted(false)   // 온보딩 전
        .build();

    return userRepository.save(user);
  }


}
