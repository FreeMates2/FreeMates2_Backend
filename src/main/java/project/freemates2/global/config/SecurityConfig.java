package project.freemates2.global.config;

import static org.springframework.security.config.Customizer.withDefaults;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import project.freemates2.external.oauth2.application.OAuth2CustomUserService;
import project.freemates2.external.oauth2.infrastructure.OAuth2LoginSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final OAuth2CustomUserService oAuth2CustomUserService;
  private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // @formatter:off
    http
        .cors(withDefaults())
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authz -> authz
            .requestMatchers(SecurityConstants.AUTH_WHITELIST).permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN") // "/admin/" 경로는 관리자만 출입 가능
            .anyRequest().authenticated() // 나머지 모든 경로는 일단 로그인해야 출입 가능
        )
//        .formLogin(withDefaults()) // 기본 로그인 페이지 제공
        .oauth2Login(oauth2 -> oauth2
            .userInfoEndpoint(userInfo -> userInfo
                .userService(oAuth2CustomUserService)

            )
            .successHandler(oAuth2LoginSuccessHandler))
        .logout(Customizer.withDefaults());
    // @formatter:on
    return http.build();
  }

//  /**
//   * 인메모리 유저 디테일 서비스
//   * */
//  @Bean
//  public UserDetailsService userDetailsService() {
//    // @formatter:off
//    UserDetails userDetails = User.withDefaultPasswordEncoder()
//        .username("user")
//        .password("password")
//        .roles("USER")
//        .build();
//    // @formatter:on
//    return new InMemoryUserDetailsManager(userDetails);
//  }
}
