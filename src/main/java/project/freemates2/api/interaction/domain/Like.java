package project.freemates2.api.interaction.domain;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import project.freemates2.api.user.domain.User;
import project.freemates2.global.jpa.domain.entity.BaseEntity;

@Entity
@Getter
@Table(name = "likes")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Like extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(updatable = false, nullable = false)
  private UUID id;

  @ManyToOne(fetch = LAZY)
  private User user;

  @Enumerated(EnumType.STRING)
  private TargetType targetType;

  private UUID targetId;          // placeId or courseId or reviewId


}
