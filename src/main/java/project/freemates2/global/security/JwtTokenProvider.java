package project.freemates2.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import project.freemates2.api.user.domain.Role;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

  private final JwtProperties jwtProperties;

  private SecretKey getSigningKey() {
    // 시크릿은 최소 32바이트 이상 권장
    return Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
  }
  // access토큰 생성 메서드
  public String createAccessToken(UUID userId, String role) {
    // 토큰 생성 로직 구현
    Instant now = Instant.now();
    Instant expiry = now.plusSeconds(jwtProperties.getAccessExpTime().toSeconds());
    return Jwts.builder()
        .subject(userId.toString())
        .claim("role", role)
        .issuedAt(Date.from(now))
        .expiration(Date.from(expiry))
        .signWith(getSigningKey(), Jwts.SIG.HS256)
        .compact();
  }

  public String createRefreshToken(UUID userId) {
    // 토큰 생성 로직 구현
    Instant now = Instant.now();
    Instant expiry = now.plusSeconds(jwtProperties.getRefreshExpTime().toSeconds());
    return Jwts.builder()
        .subject(userId.toString())
        .issuedAt(Date.from(now))
        .expiration(Date.from(expiry))
        .signWith(getSigningKey(), Jwts.SIG.HS256)
        .compact();
  }

  // 토큰 검증 메서드
  private Jws<Claims> parse(String token) {
    return Jwts.parser()
        .verifyWith(getSigningKey())
        .build()
        .parseSignedClaims(token);
  }

  public boolean isValid(String token) {
    try {
      parse(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      // 토큰 검증 실패 처리
      return false;
    }
  }

  // 토큰에서 사용자 ID 추출 메서드
  public UUID getUserId(String token) {
    Claims claims = parse(token).getPayload();
    return UUID.fromString(claims.getSubject());
  }

  // 토큰에서 역할 추출 메서드
  public Role getRole(String token) {
    Claims claims = parse(token).getPayload();
    String roleName = claims.get("role", String.class);
    return Role.valueOf(roleName);
  }



}
