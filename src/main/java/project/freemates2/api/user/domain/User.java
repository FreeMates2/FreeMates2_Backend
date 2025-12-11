package project.freemates2.api.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import project.freemates2.global.common.BaseEntity;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(
    name = "users",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"provider", "providerUserId"})
    }
)
public class User extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(updatable = false, nullable = false)
  private UUID id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Builder.Default
  private Role role = Role.ROLE_USER;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private AuthProvider provider;

  private String providerUserId; // OAuth2 제공자에서 받은 사용자 ID

  // 메인 화면 대표 학교 하나
  private Integer universityId;

  private String nickname; // 닉네임

  private Integer birthYear; // 생년

  @Enumerated(EnumType.STRING) // ★ 여기가 핵심!
  private Gender gender;

  private boolean isProfileCompleted; // 프로필 작성 완료 여부

  // 비즈니스 메서드

  public void completeProfile() {
    this.isProfileCompleted = true;
  }

  public void changeUniversity(Integer universityId) {
    this.universityId = universityId;
  }

  public void changeBirthYear(Integer birthYear) {
    this.birthYear = birthYear;
  }

  public void changeGender(Gender gender) {
    this.gender = gender;
  }

  public void changeNickname(String nickname) {
    this.nickname = nickname;
  }

  public void completeOnboarding(String nickname,
      Integer birthYear,
      Integer universityId,
      Gender gender) {
    this.nickname = nickname;
    this.birthYear = birthYear;
    this.universityId = universityId;
    this.gender = gender;
    this.isProfileCompleted = true;
  }


}
