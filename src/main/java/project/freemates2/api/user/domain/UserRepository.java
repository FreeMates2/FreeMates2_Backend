package project.freemates2.api.user.domain;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findByProviderAndProviderUserId(AuthProvider provider, String providerUserId);

}
