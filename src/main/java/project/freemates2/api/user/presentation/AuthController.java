package project.freemates2.api.user.presentation;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import project.freemates2.api.user.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.freemates2.api.user.application.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/token")
  public ResponseEntity<TokenResponse> issueAccessToken(HttpServletRequest request){
    String refreshToken = extractRefreshTokenFromCookie(request);
    String accessToken = authService.issueAccessToken(refreshToken);
    return ResponseEntity.ok(new TokenResponse(accessToken));

  }


  private String extractRefreshTokenFromCookie(HttpServletRequest request) {
    if (request.getCookies() == null) return null;
    for (var cookie : request.getCookies()) {
      if ("refreshToken".equals(cookie.getName())) {
        return cookie.getValue();
      }
    }
    return null;
  }

}
