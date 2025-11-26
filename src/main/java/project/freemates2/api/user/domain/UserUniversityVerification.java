package project.freemates2.api.user.domain;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import project.freemates2.api.university.domain.University;
import project.freemates2.global.jpa.domain.entity.BaseEntity;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "university_id"}),
    @UniqueConstraint(columnNames = {"student_email", "university_id"})})
@AllArgsConstructor
public class UserUniversityVerification extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(updatable = false, nullable = false)
  private UUID id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "university_id", nullable = false)
  private University university;

  @Column(nullable = false)
  private String studentEmail;

  private LocalDateTime verifiedAt; // 인증 시점 (수동)




}
