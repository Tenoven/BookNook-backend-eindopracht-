package nl.tenoven.BookNook.Repositories;

import nl.tenoven.BookNook.Models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
