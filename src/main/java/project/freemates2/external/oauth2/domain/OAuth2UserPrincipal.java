package project.freemates2.external.oauth2.domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import project.freemates2.api.user.domain.Role;
import project.freemates2.api.user.domain.User;

@Getter
public class OAuth2UserPrincipal implements OAuth2User {
  private final UUID userId;
  private final Role role;
  private final Map<String, Object> attributes;

  public OAuth2UserPrincipal(User user, Map<String, Object> attributes) {
    this.userId = user.getId();
    this.role = user.getRole();
    this.attributes = attributes;
  }


  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getName() {
    return userId.toString();
  }


}
