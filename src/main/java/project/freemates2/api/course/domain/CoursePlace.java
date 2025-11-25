package project.freemates2.api.course.domain;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import project.freemates2.api.place.domain.Place;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CoursePlace {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(updatable = false, nullable = false)
  private UUID id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "course_id", nullable = false)
  private Course course;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "place_id", nullable = false)
  private Place place;

  private Integer orderIndex; // 코스 내에서의 장소 순서

  private Integer stayMinutes; // 해당 장소에서 머무는 시간 (분 단위)

  private Integer moveMinutes; // 이전 장소에서 해당 장소까지 이동하는 시간 (분 단위)






}
