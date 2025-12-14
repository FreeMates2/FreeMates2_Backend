package project.freemates2.api.place.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Place extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(updatable = false, nullable = false)
  private UUID id;

  private String kakaoPlaceId;

  private String placeName;

  private String addressName;

  private PlaceCategoryType category;

  private String roadAddressName;

  @Builder.Default
  private Long likeCount = 0L;

  @Builder.Default
  private Long reviewCount = 0L;

  @Builder.Default
  private Long viewCount = 0L;

  private Double x; // 위도

  private Double y; // 경도

  private String phone;

  private String placeUrl;

  private String imageUrl;

  private boolean isActive;

  private Double averageRating;

  private String distance;



}
