package project.freemates2.global.config;


public class SecurityConstants {
  // 인스턴스 생성 방지
  private SecurityConstants() {}


  public static final String[] AUTH_WHITELIST = {

      "/v3/api-docs/**",
      "/swagger-ui/**",
      "/swagger-resources/**",
      "/swagger-ui.html",
      "/h2-console/**",
      "/api/users/oauth2/**",
      "/api/users/check/**",
      "/api/users/signup",
      "/api/users/login",
      "/docs/**"
  };



}
