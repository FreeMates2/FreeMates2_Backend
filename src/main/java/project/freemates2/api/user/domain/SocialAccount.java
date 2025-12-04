package project.freemates2.api.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SocialAccount {
  @Id
  private UUID id;

  @ManyToOne
  private User user;

  private AuthProvider provider;

  private String provider_user_id;

  private String email;

}
