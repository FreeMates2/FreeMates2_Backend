package project.freemates2.api.university.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import project.freemates2.global.jpa.domain.entity.BaseEntity;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class University {

  @Id
  @Column(updatable = false, nullable = false)
  private Integer id;

  private String name;

  private String slug;

  private Double lat; // 위도

  private Double lng; // 경도

  private String iconUrl;

}
