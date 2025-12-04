package project.freemates2.api.tag.domain;

import static jakarta.persistence.FetchType.LAZY;

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
public class Tagging {

  @Id
  private Integer id;

  @ManyToOne(fetch = LAZY)
  private Tag tag;

  private TagCategory targetType;

  private UUID targetId;

}
