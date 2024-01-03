package telegram.secourier.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import telegram.secourier.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
