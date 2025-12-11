package project.freemates2.external.oauth2.infrastructure;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import project.freemates2.api.user.application.AuthService;
import project.freemates2.api.user.domain.Role;
import project.freemates2.api.user.domain.User;
import project.freemates2.api.user.domain.UserRepository;
import project.freemates2.external.oauth2.domain.OAuth2UserPrincipal;
import project.freemates2.global.security.JwtTokenProvider;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final UserRepository userRepository;
  private final AuthService authService;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication)
      throws IOException, ServletException {

    OAuth2UserPrincipal principal = (OAuth2UserPrincipal) authentication.getPrincipal();
    UUID userId = principal.getUserId();
    authService.handleOAuth2Success(response, userId);

    User user = userRepository.findById(userId)
        .orElseThrow();

    String targetUrl = user.isProfileCompleted() ? "/" : "/onboarding";

    getRedirectStrategy().sendRedirect(request, response, targetUrl);
  }
}

