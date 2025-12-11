package project.freemates2.api.user.application;

import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import project.freemates2.global.error.BusinessException;
import project.freemates2.global.error.ErrorCode;
import project.freemates2.global.security.JwtProperties;
import project.freemates2.global.security.JwtTokenProvider;

@RequiredArgsConstructor
@Service
public class AuthService {

  private final UserService userService;
  private final JwtTokenProvider jwtTokenProvider;
  private final JwtProperties jwtProperties;

  // OAuth2 로그인 성공시 토큰 발급 및 응답 처리
  public void handleOAuth2Success(HttpServletResponse response, UUID userId) {

    String refreshToken = jwtTokenProvider.createRefreshToken(userId);

    ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
        .httpOnly(true)
        .secure(jwtProperties.getCookieSecure())
        .path("/")
        .maxAge(jwtProperties.getRefreshExpTime().getSeconds())
        .sameSite("Lax")
        .build();

    response.addHeader("Set-Cookie", cookie.toString());
  }

  public String issueAccessToken(String refreshToken) {
    if(refreshToken == null || refreshToken.isEmpty()) {
      throw new BusinessException(ErrorCode.TOKEN_MISSING);
    }
    if(!jwtTokenProvider.isValid(refreshToken)) {
      throw new BusinessException(ErrorCode.INVALID_TOKEN);
    }
    UUID userId = jwtTokenProvider.getUserId(refreshToken);
    var user = userService.getUserById(userId);

    return jwtTokenProvider.createAccessToken(userId, user.getRole().name());

  }


}