package project.freemates2.api.user.presentation;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.freemates2.api.user.application.UserService;
import project.freemates2.api.user.dto.OnboardingRequest;
import project.freemates2.global.oauth2.domain.OAuth2UserPrincipal;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  @PostMapping("/me/onboarding")
  public ResponseEntity<Void> completeOnboarding(
      @AuthenticationPrincipal OAuth2UserPrincipal principal,
      @RequestBody OnboardingRequest req
  ) {
    userService.completeOnboarding(principal.getUserId(), req);
    return ResponseEntity.noContent().build();
  }


}
