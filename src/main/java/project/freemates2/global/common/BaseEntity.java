package project.freemates2.global.common;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class BaseEntity {

  @CreatedDate
  @Column(updatable = false, nullable = false)
  private Instant createdAt;     // INSERT 시 자동 세팅(UTC)

  @LastModifiedDate
  @Column(nullable = false)
  private Instant modifiedAt;    // UPDATE 시 자동 세팅(UTC)
}
