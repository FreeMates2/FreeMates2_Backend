package project.freemates2.api.course.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import project.freemates2.api.university.domain.University;
import project.freemates2.api.user.domain.User;
import project.freemates2.global.common.BaseEntity;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Course extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(updatable = false, nullable = false)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  private User creator; // 코스 만든 유저

  @ManyToOne(fetch = FetchType.LAZY)
  private University university;

  private String title;

  private String description;

  private Integer durationMinutes; // 코스에 걸리는 전체 시간 (분 단위)

  @Builder.Default
  private Long likeCount = 0L; // 좋아요 수

  private boolean isPublic;

}
