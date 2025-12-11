package project.freemates2.global.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import project.freemates2.api.user.domain.Role;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtTokenProvider jwtTokenProvider;
  private final JwtProperties jwtProperties;
  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain)
      throws java.io.IOException, ServletException {

    String token = resolveToken(request);

    if (token != null && jwtTokenProvider.isValid(token)) {
      UUID userId = jwtTokenProvider.getUserId(token);
      Role role = jwtTokenProvider.getRole(token);

      var auth = new UsernamePasswordAuthenticationToken(
          userId,
          null,
          List.of(new SimpleGrantedAuthority(role.name()))
      );
      SecurityContextHolder.getContext().setAuthentication(auth);
    }

    filterChain.doFilter(request, response);
  }

  private String resolveToken(HttpServletRequest request) {
    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authHeader != null && authHeader.startsWith(jwtProperties.getTokenPrefix())) {
      return authHeader.substring(jwtProperties.getTokenPrefix().length());
    }
    return null;
  }





}
