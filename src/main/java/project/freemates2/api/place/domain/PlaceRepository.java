package project.freemates2.api.place.domain;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, UUID> {


}
