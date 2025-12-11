package project.freemates2.api.user.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.freemates2.api.user.domain.Gender;
import project.freemates2.api.user.domain.Role;
import project.freemates2.api.user.domain.User;
import project.freemates2.api.user.domain.UserRepository;
import project.freemates2.api.user.dto.OnboardingRequest;
import project.freemates2.external.oauth2.dto.OAuth2UserDto;
import project.freemates2.global.error.ErrorCode;

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
        .role(Role.ROLE_USER)
        .isProfileCompleted(false)  // 온보딩 전
        .build();

    return userRepository.save(user);
  }

  @Transactional
  public void completeOnboarding(UUID userId, OnboardingRequest req) {
    User user = getUserById(userId);

    Gender gender = req.gender() != null
        ? Gender.valueOf(req.gender().toUpperCase()) // "FEMALE", "MALE" 등
        : null; // 선택값이면 null 허용

    user.completeOnboarding(
        req.nickname(),
        req.birthYear(),
        req.universityId(),
        gender
    );
  }

  @Transactional(readOnly = true)
  public User getUserById(UUID userId) {
    return userRepository.findById(userId)
        .orElseThrow( () -> new UsernameNotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()+":"+ userId)
        );
  }


}
