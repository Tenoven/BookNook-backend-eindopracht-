package nl.tenoven.BookNook.Repositories;

import nl.tenoven.BookNook.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
