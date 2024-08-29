package nl.tenoven.BookNook.Repositories;

import nl.tenoven.BookNook.Models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository <Review, Long> {
}
