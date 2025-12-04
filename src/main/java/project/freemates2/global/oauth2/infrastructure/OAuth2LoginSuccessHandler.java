package project.freemates2.global.oauth2.infrastructure;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import project.freemates2.api.user.domain.User;
import project.freemates2.api.user.domain.UserRepository;
import project.freemates2.global.oauth2.domain.OAuth2UserPrincipal;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final UserRepository userRepository;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication)
      throws IOException, ServletException {

    OAuth2UserPrincipal principal = (OAuth2UserPrincipal) authentication.getPrincipal();
    UUID userId = principal.getUserId();

    User user = userRepository.findById(userId)
        .orElseThrow(); // 필요하면 CustomException으로 변경

    if (!user.isProfileCompleted()) {
      // 프로필 완성 안 된 경우 온보딩 페이지로 리다이렉트
      setDefaultTargetUrl("/onboarding");
    } else {
      // 프로필 완성된 경우 메인 페이지로 리다이렉트
      setDefaultTargetUrl("/");
    }

    super.onAuthenticationSuccess(request, response, authentication);
  }
}

