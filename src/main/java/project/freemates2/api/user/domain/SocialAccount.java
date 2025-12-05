package project.freemates2.api.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(updatable = false, nullable = false)
  private UUID id;

  @ManyToOne
  private User user;

  @Enumerated(EnumType.STRING)
  private AuthProvider provider;

  private String providerUserId;

  private String email;

}
